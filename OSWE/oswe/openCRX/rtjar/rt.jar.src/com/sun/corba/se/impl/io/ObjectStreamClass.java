/*      */ package com.sun.corba.se.impl.io;
/*      */ 
/*      */ import com.sun.corba.se.impl.util.RepositoryId;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.Externalizable;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidClassException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Member;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.lang.reflect.Proxy;
/*      */ import java.lang.reflect.UndeclaredThrowableException;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.DigestOutputStream;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.Permissions;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashSet;
/*      */ import org.omg.CORBA.ValueMember;
/*      */ import sun.corba.Bridge;
/*      */ import sun.misc.JavaSecurityAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ObjectStreamClass
/*      */   implements Serializable
/*      */ {
/*      */   private static final boolean DEBUG_SVUID = false;
/*      */   public static final long kDefaultUID = -1L;
/*   93 */   private static Object[] noArgsList = new Object[0];
/*   94 */   private static Class<?>[] noTypesList = new Class[0];
/*      */ 
/*      */   
/*      */   private boolean isEnum;
/*      */ 
/*      */   
/*  100 */   private static final Bridge bridge = AccessController.<Bridge>doPrivileged(new PrivilegedAction<Bridge>()
/*      */       {
/*      */         public Bridge run() {
/*  103 */           return Bridge.get();
/*      */         }
/*      */       });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final ObjectStreamClass lookup(Class<?> paramClass) {
/*  114 */     ObjectStreamClass objectStreamClass = lookupInternal(paramClass);
/*  115 */     if (objectStreamClass.isSerializable() || objectStreamClass.isExternalizable())
/*  116 */       return objectStreamClass; 
/*  117 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static ObjectStreamClass lookupInternal(Class<?> paramClass) {
/*  129 */     ObjectStreamClass objectStreamClass = null;
/*  130 */     synchronized (descriptorFor) {
/*      */       
/*  132 */       objectStreamClass = findDescriptorFor(paramClass);
/*  133 */       if (objectStreamClass == null) {
/*      */         
/*  135 */         boolean bool = Serializable.class.isAssignableFrom(paramClass);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  140 */         ObjectStreamClass objectStreamClass1 = null;
/*  141 */         if (bool) {
/*  142 */           Class<?> clazz = paramClass.getSuperclass();
/*  143 */           if (clazz != null) {
/*  144 */             objectStreamClass1 = lookup(clazz);
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  151 */         boolean bool1 = false;
/*  152 */         if (bool) {
/*      */ 
/*      */           
/*  155 */           bool1 = ((objectStreamClass1 != null && objectStreamClass1.isExternalizable()) || Externalizable.class.isAssignableFrom(paramClass)) ? true : false;
/*  156 */           if (bool1) {
/*  157 */             bool = false;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  164 */         objectStreamClass = new ObjectStreamClass(paramClass, objectStreamClass1, bool, bool1);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  181 */       objectStreamClass.init();
/*      */     } 
/*  183 */     return objectStreamClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getName() {
/*  190 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final long getSerialVersionUID(Class<?> paramClass) {
/*  200 */     ObjectStreamClass objectStreamClass = lookup(paramClass);
/*  201 */     if (objectStreamClass != null)
/*      */     {
/*  203 */       return objectStreamClass.getSerialVersionUID();
/*      */     }
/*  205 */     return 0L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final long getSerialVersionUID() {
/*  215 */     return this.suid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getSerialVersionUIDStr() {
/*  225 */     if (this.suidStr == null)
/*  226 */       this.suidStr = Long.toHexString(this.suid).toUpperCase(); 
/*  227 */     return this.suidStr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final long getActualSerialVersionUID(Class<?> paramClass) {
/*  235 */     ObjectStreamClass objectStreamClass = lookup(paramClass);
/*  236 */     if (objectStreamClass != null)
/*      */     {
/*  238 */       return objectStreamClass.getActualSerialVersionUID();
/*      */     }
/*  240 */     return 0L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final long getActualSerialVersionUID() {
/*  247 */     return this.actualSuid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getActualSerialVersionUIDStr() {
/*  254 */     if (this.actualSuidStr == null)
/*  255 */       this.actualSuidStr = Long.toHexString(this.actualSuid).toUpperCase(); 
/*  256 */     return this.actualSuidStr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Class<?> forClass() {
/*  264 */     return this.ofClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectStreamField[] getFields() {
/*  276 */     if (this.fields.length > 0) {
/*  277 */       ObjectStreamField[] arrayOfObjectStreamField = new ObjectStreamField[this.fields.length];
/*  278 */       System.arraycopy(this.fields, 0, arrayOfObjectStreamField, 0, this.fields.length);
/*  279 */       return arrayOfObjectStreamField;
/*      */     } 
/*  281 */     return this.fields;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasField(ValueMember paramValueMember) {
/*      */     try {
/*  288 */       for (byte b = 0; b < this.fields.length; b++) {
/*  289 */         if (this.fields[b].getName().equals(paramValueMember.name) && 
/*  290 */           this.fields[b].getSignature().equals(
/*  291 */             ValueUtility.getSignature(paramValueMember))) {
/*  292 */           return true;
/*      */         }
/*      */       } 
/*  295 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  300 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   final ObjectStreamField[] getFieldsNoCopy() {
/*  305 */     return this.fields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final ObjectStreamField getField(String paramString) {
/*  316 */     for (int i = this.fields.length - 1; i >= 0; i--) {
/*  317 */       if (paramString.equals(this.fields[i].getName())) {
/*  318 */         return this.fields[i];
/*      */       }
/*      */     } 
/*  321 */     return null;
/*      */   }
/*      */   
/*      */   public Serializable writeReplace(Serializable paramSerializable) {
/*  325 */     if (this.writeReplaceObjectMethod != null) {
/*      */       try {
/*  327 */         return (Serializable)this.writeReplaceObjectMethod.invoke(paramSerializable, noArgsList);
/*  328 */       } catch (Throwable throwable) {
/*  329 */         throw new RuntimeException(throwable);
/*      */       } 
/*      */     }
/*  332 */     return paramSerializable;
/*      */   }
/*      */   
/*      */   public Object readResolve(Object paramObject) {
/*  336 */     if (this.readResolveObjectMethod != null) {
/*      */       try {
/*  338 */         return this.readResolveObjectMethod.invoke(paramObject, noArgsList);
/*  339 */       } catch (Throwable throwable) {
/*  340 */         throw new RuntimeException(throwable);
/*      */       } 
/*      */     }
/*  343 */     return paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String toString() {
/*  350 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/*  352 */     stringBuffer.append(this.name);
/*  353 */     stringBuffer.append(": static final long serialVersionUID = ");
/*  354 */     stringBuffer.append(Long.toString(this.suid));
/*  355 */     stringBuffer.append("L;");
/*  356 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ObjectStreamClass(Class<?> paramClass, ObjectStreamClass paramObjectStreamClass, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/* 1597 */     this.suid = -1L;
/* 1598 */     this.suidStr = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1603 */     this.actualSuid = -1L;
/* 1604 */     this.actualSuidStr = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1619 */     this.initialized = false;
/*      */ 
/*      */     
/* 1622 */     this.lock = new Object();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1646 */     this.rmiiiopOptionalDataRepId = null; this.ofClass = paramClass; if (Proxy.isProxyClass(paramClass)) this.forProxyClass = true;  this.name = paramClass.getName(); this.isEnum = Enum.class.isAssignableFrom(paramClass); this.superclass = paramObjectStreamClass; this.serializable = paramBoolean1; if (!this.forProxyClass) this.externalizable = paramBoolean2;  insertDescriptorFor(this); } private static final class PersistentFieldsValue extends ClassValue<ObjectStreamField[]> { protected ObjectStreamField[] computeValue(Class<?> param1Class) { try { Field field = param1Class.getDeclaredField("serialPersistentFields"); int i = field.getModifiers(); if (Modifier.isPrivate(i) && Modifier.isStatic(i) && Modifier.isFinal(i)) { field.setAccessible(true); ObjectStreamField[] arrayOfObjectStreamField = (ObjectStreamField[])field.get(param1Class); return translateFields(arrayOfObjectStreamField); }  } catch (NoSuchFieldException|IllegalAccessException|IllegalArgumentException|ClassCastException noSuchFieldException) {} return null; } private static ObjectStreamField[] translateFields(ObjectStreamField[] param1ArrayOfObjectStreamField) { ObjectStreamField[] arrayOfObjectStreamField = new ObjectStreamField[param1ArrayOfObjectStreamField.length]; for (byte b = 0; b < param1ArrayOfObjectStreamField.length; b++) arrayOfObjectStreamField[b] = new ObjectStreamField(param1ArrayOfObjectStreamField[b].getName(), param1ArrayOfObjectStreamField[b].getType());  return arrayOfObjectStreamField; } } private static final PersistentFieldsValue persistentFieldsValue = new PersistentFieldsValue(); public static final int CLASS_MASK = 1553; public static final int FIELD_MASK = 223; public static final int METHOD_MASK = 3391; private ProtectionDomain noPermissionsDomain() { Permissions permissions = new Permissions(); permissions.setReadOnly(); return new ProtectionDomain(null, permissions); } private ProtectionDomain[] getProtectionDomains(Constructor<?> paramConstructor, Class<?> paramClass) { ProtectionDomain[] arrayOfProtectionDomain = null; if (paramConstructor != null && paramClass.getClassLoader() != null && System.getSecurityManager() != null) { Class<?> clazz1 = paramClass; Class<?> clazz2 = paramConstructor.getDeclaringClass(); HashSet<ProtectionDomain> hashSet = null; while (clazz1 != clazz2) { ProtectionDomain protectionDomain = clazz1.getProtectionDomain(); if (protectionDomain != null) { if (hashSet == null) hashSet = new HashSet();  hashSet.add(protectionDomain); }  clazz1 = clazz1.getSuperclass(); if (clazz1 == null) { if (hashSet == null) { hashSet = new HashSet<>(); } else { hashSet.clear(); }  hashSet.add(noPermissionsDomain()); break; }  }  if (hashSet != null) arrayOfProtectionDomain = hashSet.<ProtectionDomain>toArray(new ProtectionDomain[0]);  }  return arrayOfProtectionDomain; } private void init() { synchronized (this.lock) { if (this.initialized) return;  final Class<?> cl = this.ofClass; if (!this.serializable || this.externalizable || this.forProxyClass || this.name.equals("java.lang.String")) { this.fields = NO_FIELDS; } else if (this.serializable) { AccessController.doPrivileged(new PrivilegedAction() { public Object run() { ObjectStreamClass.this.fields = ObjectStreamClass.persistentFieldsValue.get(cl); if (ObjectStreamClass.this.fields == null) { Field[] arrayOfField = cl.getDeclaredFields(); byte b1 = 0; ObjectStreamField[] arrayOfObjectStreamField = new ObjectStreamField[arrayOfField.length]; for (byte b2 = 0; b2 < arrayOfField.length; b2++) { Field field = arrayOfField[b2]; int i = field.getModifiers(); if (!Modifier.isStatic(i) && !Modifier.isTransient(i)) { field.setAccessible(true); arrayOfObjectStreamField[b1++] = new ObjectStreamField(field); }  }  ObjectStreamClass.this.fields = new ObjectStreamField[b1]; System.arraycopy(arrayOfObjectStreamField, 0, ObjectStreamClass.this.fields, 0, b1); } else { for (int i = ObjectStreamClass.this.fields.length - 1; i >= 0; i--) { try { Field field = cl.getDeclaredField(ObjectStreamClass.this.fields[i].getName()); if (ObjectStreamClass.this.fields[i].getType() == field.getType()) { field.setAccessible(true); ObjectStreamClass.this.fields[i].setField(field); }  } catch (NoSuchFieldException noSuchFieldException) {} }  }  return null; } }); if (this.fields.length > 1) Arrays.sort((Object[])this.fields);  computeFieldInfo(); }  if (isNonSerializable() || this.isEnum) { this.suid = 0L; } else { AccessController.doPrivileged(new PrivilegedAction() { public Object run() { if (ObjectStreamClass.this.forProxyClass) { ObjectStreamClass.this.suid = 0L; } else { try { Field field = cl.getDeclaredField("serialVersionUID"); int i = field.getModifiers(); if (Modifier.isStatic(i) && Modifier.isFinal(i)) { field.setAccessible(true); ObjectStreamClass.this.suid = field.getLong(cl); } else { ObjectStreamClass.this.suid = ObjectStreamClass._computeSerialVersionUID(cl); }  } catch (NoSuchFieldException noSuchFieldException) { ObjectStreamClass.this.suid = ObjectStreamClass._computeSerialVersionUID(cl); } catch (IllegalAccessException illegalAccessException) { ObjectStreamClass.this.suid = ObjectStreamClass._computeSerialVersionUID(cl); }  }  ObjectStreamClass.this.writeReplaceObjectMethod = ObjectStreamClass.getInheritableMethod(cl, "writeReplace", ObjectStreamClass.noTypesList, Object.class); ObjectStreamClass.this.readResolveObjectMethod = ObjectStreamClass.getInheritableMethod(cl, "readResolve", ObjectStreamClass.noTypesList, Object.class); ObjectStreamClass.this.domains = new ProtectionDomain[] { ObjectStreamClass.access$900(this.this$0) }; if (ObjectStreamClass.this.externalizable) { ObjectStreamClass.this.cons = ObjectStreamClass.getExternalizableConstructor(cl); } else { ObjectStreamClass.this.cons = ObjectStreamClass.getSerializableConstructor(cl); }  ObjectStreamClass.this.domains = ObjectStreamClass.this.getProtectionDomains(ObjectStreamClass.this.cons, cl); if (ObjectStreamClass.this.serializable && !ObjectStreamClass.this.forProxyClass) { ObjectStreamClass.this.writeObjectMethod = ObjectStreamClass.getPrivateMethod(cl, "writeObject", new Class[] { ObjectOutputStream.class }, void.class); ObjectStreamClass.this.readObjectMethod = ObjectStreamClass.getPrivateMethod(cl, "readObject", new Class[] { ObjectInputStream.class }, void.class); }  return null; } }); }  this.actualSuid = computeStructuralUID(this, clazz); if (hasWriteObject()) this.rmiiiopOptionalDataRepId = computeRMIIIOPOptionalDataRepId();  this.initialized = true; }  } private static Method getPrivateMethod(Class<?> paramClass1, String paramString, Class<?>[] paramArrayOfClass, Class<?> paramClass2) { try { Method method = paramClass1.getDeclaredMethod(paramString, paramArrayOfClass); method.setAccessible(true); int i = method.getModifiers(); return (method.getReturnType() == paramClass2 && (i & 0x8) == 0 && (i & 0x2) != 0) ? method : null; } catch (NoSuchMethodException noSuchMethodException) { return null; }  } private String computeRMIIIOPOptionalDataRepId() { StringBuffer stringBuffer = new StringBuffer("RMI:org.omg.custom."); stringBuffer.append(RepositoryId.convertToISOLatin1(getName())); stringBuffer.append(':'); stringBuffer.append(getActualSerialVersionUIDStr()); stringBuffer.append(':'); stringBuffer.append(getSerialVersionUIDStr()); return stringBuffer.toString(); } public final String getRMIIIOPOptionalDataRepId() { return this.rmiiiopOptionalDataRepId; } ObjectStreamClass(String paramString, long paramLong) { this.suid = -1L; this.suidStr = null; this.actualSuid = -1L; this.actualSuidStr = null; this.initialized = false; this.lock = new Object(); this.rmiiiopOptionalDataRepId = null; this.name = paramString; this.suid = paramLong; this.superclass = null; }
/*      */   final void setClass(Class<?> paramClass) throws InvalidClassException { if (paramClass == null) { this.localClassDesc = null; this.ofClass = null; computeFieldInfo(); return; }  this.localClassDesc = lookupInternal(paramClass); if (this.localClassDesc == null) throw new InvalidClassException(paramClass.getName(), "Local class not compatible");  if (this.suid != this.localClassDesc.suid) { boolean bool1 = (isNonSerializable() || this.localClassDesc.isNonSerializable()) ? true : false; boolean bool2 = (paramClass.isArray() && !paramClass.getName().equals(this.name)) ? true : false; if (!bool2 && !bool1) throw new InvalidClassException(paramClass.getName(), "Local class not compatible: stream classdesc serialVersionUID=" + this.suid + " local class serialVersionUID=" + this.localClassDesc.suid);  }  if (!compareClassNames(this.name, paramClass.getName(), '.')) throw new InvalidClassException(paramClass.getName(), "Incompatible local class name. Expected class name compatible with " + this.name);  if (this.serializable != this.localClassDesc.serializable || this.externalizable != this.localClassDesc.externalizable || (!this.serializable && !this.externalizable)) throw new InvalidClassException(paramClass.getName(), "Serialization incompatible with Externalization");  ObjectStreamField[] arrayOfObjectStreamField1 = this.localClassDesc.fields; ObjectStreamField[] arrayOfObjectStreamField2 = this.fields; byte b1 = 0; for (byte b2 = 0; b2 < arrayOfObjectStreamField2.length; b2++) { for (byte b = b1; b < arrayOfObjectStreamField1.length; b++) { if (arrayOfObjectStreamField2[b2].getName().equals(arrayOfObjectStreamField1[b].getName())) { if (arrayOfObjectStreamField2[b2].isPrimitive() && !arrayOfObjectStreamField2[b2].typeEquals(arrayOfObjectStreamField1[b])) throw new InvalidClassException(paramClass.getName(), "The type of field " + arrayOfObjectStreamField2[b2].getName() + " of class " + this.name + " is incompatible.");  b1 = b; arrayOfObjectStreamField2[b2].setField(arrayOfObjectStreamField1[b1].getField()); break; }  }  }  computeFieldInfo(); this.ofClass = paramClass; this.readObjectMethod = this.localClassDesc.readObjectMethod; this.readResolveObjectMethod = this.localClassDesc.readResolveObjectMethod; }
/*      */   static boolean compareClassNames(String paramString1, String paramString2, char paramChar) { int i = paramString1.lastIndexOf(paramChar); if (i < 0) i = 0;  int j = paramString2.lastIndexOf(paramChar); if (j < 0) j = 0;  return paramString1.regionMatches(false, i, paramString2, j, paramString1.length() - i); }
/*      */   final boolean typeEquals(ObjectStreamClass paramObjectStreamClass) { return (this.suid == paramObjectStreamClass.suid && compareClassNames(this.name, paramObjectStreamClass.name, '.')); }
/*      */   final void setSuperclass(ObjectStreamClass paramObjectStreamClass) { this.superclass = paramObjectStreamClass; }
/*      */   final ObjectStreamClass getSuperclass() { return this.superclass; }
/*      */   final boolean hasReadObject() { return (this.readObjectMethod != null); } final boolean hasWriteObject() { return (this.writeObjectMethod != null); } final boolean isCustomMarshaled() { return (hasWriteObject() || isExternalizable() || (this.superclass != null && this.superclass.isCustomMarshaled())); } boolean hasExternalizableBlockDataMode() { return this.hasExternalizableBlockData; } Object newInstance() throws InstantiationException, InvocationTargetException, UnsupportedOperationException { if (!this.initialized) throw new InternalError("Unexpected call when not initialized");  if (this.cons != null) try { if (this.domains == null || this.domains.length == 0) return this.cons.newInstance(new Object[0]);  JavaSecurityAccess javaSecurityAccess = SharedSecrets.getJavaSecurityAccess(); PrivilegedAction privilegedAction = new PrivilegedAction() {
/*      */             public Object run() { try { return ObjectStreamClass.this.cons.newInstance(new Object[0]); } catch (InstantiationException|InvocationTargetException|IllegalAccessException instantiationException) { throw new UndeclaredThrowableException(instantiationException); }  }
/* 1654 */           }; try { return javaSecurityAccess.doIntersectionPrivilege(privilegedAction, AccessController.getContext(), new AccessControlContext(this.domains)); } catch (UndeclaredThrowableException undeclaredThrowableException) { Throwable throwable = undeclaredThrowableException.getCause(); if (throwable instanceof InstantiationException) throw (InstantiationException)throwable;  if (throwable instanceof InvocationTargetException) throw (InvocationTargetException)throwable;  if (throwable instanceof IllegalAccessException) throw (IllegalAccessException)throwable;  throw undeclaredThrowableException; }  } catch (IllegalAccessException illegalAccessException) { InternalError internalError = new InternalError(); internalError.initCause(illegalAccessException); throw internalError; }   throw new UnsupportedOperationException(); } private static Constructor getExternalizableConstructor(Class<?> paramClass) { try { Constructor<?> constructor = paramClass.getDeclaredConstructor(new Class[0]); constructor.setAccessible(true); return ((constructor.getModifiers() & 0x1) != 0) ? constructor : null; } catch (NoSuchMethodException noSuchMethodException) { return null; }  } private static Constructor getSerializableConstructor(Class<?> paramClass) { Class<?> clazz = paramClass; while (Serializable.class.isAssignableFrom(clazz)) { if ((clazz = clazz.getSuperclass()) == null) return null;  }  try { Constructor<?> constructor = clazz.getDeclaredConstructor(new Class[0]); int i = constructor.getModifiers(); if ((i & 0x2) != 0 || ((i & 0x5) == 0 && !packageEquals(paramClass, clazz))) return null;  constructor = bridge.newConstructorForSerialization(paramClass, constructor); constructor.setAccessible(true); return constructor; } catch (NoSuchMethodException noSuchMethodException) { return null; }  } private static Method hasStaticInitializerMethod = null;
/*      */   final ObjectStreamClass localClassDescriptor() { return this.localClassDesc; }
/*      */   boolean isSerializable() { return this.serializable; }
/*      */   boolean isExternalizable() { return this.externalizable; }
/*      */   boolean isNonSerializable() { return (!this.externalizable && !this.serializable); } private void computeFieldInfo() { this.primBytes = 0; this.objFields = 0; for (byte b = 0; b < this.fields.length; b++) { switch (this.fields[b].getTypeCode()) { case 'B': case 'Z': this.primBytes++; break;case 'C': case 'S': this.primBytes += 2; break;case 'F': case 'I': this.primBytes += 4; break;case 'D': case 'J': this.primBytes += 8; break;case 'L': case '[': this.objFields++; break; }  }  } private static void msg(String paramString) { System.out.println(paramString); } private static long _computeSerialVersionUID(Class<?> paramClass) { ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512); long l = 0L; try { MessageDigest messageDigest = MessageDigest.getInstance("SHA"); DigestOutputStream digestOutputStream = new DigestOutputStream(byteArrayOutputStream, messageDigest); DataOutputStream dataOutputStream = new DataOutputStream(digestOutputStream); dataOutputStream.writeUTF(paramClass.getName()); int i = paramClass.getModifiers(); i &= 0x611; Method[] arrayOfMethod = paramClass.getDeclaredMethods(); if ((i & 0x200) != 0) { i &= 0xFFFFFBFF; if (arrayOfMethod.length > 0) i |= 0x400;  }  i &= 0x611; dataOutputStream.writeInt(i); if (!paramClass.isArray()) { Class[] arrayOfClass = paramClass.getInterfaces(); Arrays.sort((Class<?>[][])arrayOfClass, compareClassByName); for (byte b = 0; b < arrayOfClass.length; b++) dataOutputStream.writeUTF(arrayOfClass[b].getName());  }  Field[] arrayOfField = paramClass.getDeclaredFields(); Arrays.sort(arrayOfField, compareMemberByName); for (byte b1 = 0; b1 < arrayOfField.length; b1++) { Field field = arrayOfField[b1]; int j = field.getModifiers(); if (!Modifier.isPrivate(j) || (!Modifier.isTransient(j) && !Modifier.isStatic(j))) { dataOutputStream.writeUTF(field.getName()); j &= 0xDF; dataOutputStream.writeInt(j); dataOutputStream.writeUTF(getSignature(field.getType())); }  }  if (hasStaticInitializer(paramClass)) { dataOutputStream.writeUTF("<clinit>"); dataOutputStream.writeInt(8); dataOutputStream.writeUTF("()V"); }  MethodSignature[] arrayOfMethodSignature1 = MethodSignature.removePrivateAndSort((Member[])paramClass.getDeclaredConstructors()); for (byte b2 = 0; b2 < arrayOfMethodSignature1.length; b2++) { MethodSignature methodSignature = arrayOfMethodSignature1[b2]; String str1 = "<init>"; String str2 = methodSignature.signature; str2 = str2.replace('/', '.'); dataOutputStream.writeUTF(str1); int j = methodSignature.member.getModifiers() & 0xD3F; dataOutputStream.writeInt(j); dataOutputStream.writeUTF(str2); }  MethodSignature[] arrayOfMethodSignature2 = MethodSignature.removePrivateAndSort((Member[])arrayOfMethod); for (byte b3 = 0; b3 < arrayOfMethodSignature2.length; b3++) { MethodSignature methodSignature = arrayOfMethodSignature2[b3]; String str = methodSignature.signature; str = str.replace('/', '.'); dataOutputStream.writeUTF(methodSignature.member.getName()); int j = methodSignature.member.getModifiers() & 0xD3F; dataOutputStream.writeInt(j); dataOutputStream.writeUTF(str); }  dataOutputStream.flush(); byte[] arrayOfByte = messageDigest.digest(); for (byte b4 = 0; b4 < Math.min(8, arrayOfByte.length); b4++) l += (arrayOfByte[b4] & 0xFF) << b4 * 8;  } catch (IOException iOException) { l = -1L; } catch (NoSuchAlgorithmException noSuchAlgorithmException) { SecurityException securityException = new SecurityException(); securityException.initCause(noSuchAlgorithmException); throw securityException; }  return l; } private static long computeStructuralUID(ObjectStreamClass paramObjectStreamClass, Class<?> paramClass) { ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512); long l = 0L; try { if (!Serializable.class.isAssignableFrom(paramClass) || paramClass.isInterface()) return 0L;  if (Externalizable.class.isAssignableFrom(paramClass)) return 1L;  MessageDigest messageDigest = MessageDigest.getInstance("SHA"); DigestOutputStream digestOutputStream = new DigestOutputStream(byteArrayOutputStream, messageDigest); DataOutputStream dataOutputStream = new DataOutputStream(digestOutputStream); Class<?> clazz = paramClass.getSuperclass(); if (clazz != null) dataOutputStream.writeLong(computeStructuralUID(lookup(clazz), clazz));  if (paramObjectStreamClass.hasWriteObject()) { dataOutputStream.writeInt(2); } else { dataOutputStream.writeInt(1); }  ObjectStreamField[] arrayOfObjectStreamField = paramObjectStreamClass.getFields(); if (arrayOfObjectStreamField.length > 1) Arrays.sort(arrayOfObjectStreamField, compareObjStrFieldsByName);  for (byte b1 = 0; b1 < arrayOfObjectStreamField.length; b1++) { dataOutputStream.writeUTF(arrayOfObjectStreamField[b1].getName()); dataOutputStream.writeUTF(arrayOfObjectStreamField[b1].getSignature()); }  dataOutputStream.flush(); byte[] arrayOfByte = messageDigest.digest(); for (byte b2 = 0; b2 < Math.min(8, arrayOfByte.length); b2++) l += (arrayOfByte[b2] & 0xFF) << b2 * 8;  } catch (IOException iOException) { l = -1L; } catch (NoSuchAlgorithmException noSuchAlgorithmException) { SecurityException securityException = new SecurityException(); securityException.initCause(noSuchAlgorithmException); throw securityException; }  return l; } static String getSignature(Class<?> paramClass) { String str = null; if (paramClass.isArray()) { Class<?> clazz = paramClass; byte b1 = 0; while (clazz.isArray()) { b1++; clazz = clazz.getComponentType(); }  StringBuffer stringBuffer = new StringBuffer(); for (byte b2 = 0; b2 < b1; b2++) stringBuffer.append("[");  stringBuffer.append(getSignature(clazz)); str = stringBuffer.toString(); } else if (paramClass.isPrimitive()) { if (paramClass == int.class) { str = "I"; } else if (paramClass == byte.class) { str = "B"; } else if (paramClass == long.class) { str = "J"; } else if (paramClass == float.class) { str = "F"; } else if (paramClass == double.class) { str = "D"; } else if (paramClass == short.class) { str = "S"; } else if (paramClass == char.class) { str = "C"; } else if (paramClass == boolean.class) { str = "Z"; } else if (paramClass == void.class) { str = "V"; }  } else { str = "L" + paramClass.getName().replace('.', '/') + ";"; }  return str; } static String getSignature(Method paramMethod) { StringBuffer stringBuffer = new StringBuffer(); stringBuffer.append("("); Class[] arrayOfClass = paramMethod.getParameterTypes(); for (byte b = 0; b < arrayOfClass.length; b++) stringBuffer.append(getSignature(arrayOfClass[b]));  stringBuffer.append(")"); stringBuffer.append(getSignature(paramMethod.getReturnType())); return stringBuffer.toString(); } static String getSignature(Constructor paramConstructor) { StringBuffer stringBuffer = new StringBuffer(); stringBuffer.append("("); Class[] arrayOfClass = paramConstructor.getParameterTypes(); for (byte b = 0; b < arrayOfClass.length; b++) stringBuffer.append(getSignature(arrayOfClass[b]));  stringBuffer.append(")V"); return stringBuffer.toString(); } private static ObjectStreamClassEntry[] descriptorFor = new ObjectStreamClassEntry[61]; private String name; private ObjectStreamClass superclass; private boolean serializable; private boolean externalizable; private ObjectStreamField[] fields; private Class<?> ofClass; boolean forProxyClass; private long suid; private String suidStr; private long actualSuid; private String actualSuidStr; int primBytes; int objFields; private boolean initialized; private Object lock; private boolean hasExternalizableBlockData; Method writeObjectMethod; Method readObjectMethod; private transient Method writeReplaceObjectMethod; private transient Method readResolveObjectMethod; private Constructor<?> cons; private transient ProtectionDomain[] domains; private String rmiiiopOptionalDataRepId; private ObjectStreamClass localClassDesc; private static ObjectStreamClass findDescriptorFor(Class<?> paramClass) { int i = paramClass.hashCode(); int j = (i & Integer.MAX_VALUE) % descriptorFor.length; ObjectStreamClassEntry objectStreamClassEntry1; while ((objectStreamClassEntry1 = descriptorFor[j]) != null && objectStreamClassEntry1.get() == null) descriptorFor[j] = objectStreamClassEntry1.next;  ObjectStreamClassEntry objectStreamClassEntry2 = objectStreamClassEntry1; while (objectStreamClassEntry1 != null) { ObjectStreamClass objectStreamClass = (ObjectStreamClass)objectStreamClassEntry1.get(); if (objectStreamClass == null) { objectStreamClassEntry2.next = objectStreamClassEntry1.next; } else { if (objectStreamClass.ofClass == paramClass) return objectStreamClass;  objectStreamClassEntry2 = objectStreamClassEntry1; }  objectStreamClassEntry1 = objectStreamClassEntry1.next; }  return null; } private static void insertDescriptorFor(ObjectStreamClass paramObjectStreamClass) { if (findDescriptorFor(paramObjectStreamClass.ofClass) != null) return;  int i = paramObjectStreamClass.ofClass.hashCode(); int j = (i & Integer.MAX_VALUE) % descriptorFor.length; ObjectStreamClassEntry objectStreamClassEntry = new ObjectStreamClassEntry(paramObjectStreamClass); objectStreamClassEntry.next = descriptorFor[j]; descriptorFor[j] = objectStreamClassEntry; } private static Field[] getDeclaredFields(final Class<?> clz) { return AccessController.<Field[]>doPrivileged(new PrivilegedAction<Field>() {
/*      */           public Object run() { return clz.getDeclaredFields(); }
/* 1660 */         }); } private static final long serialVersionUID = -6120832682080437368L; private static boolean hasStaticInitializer(Class<?> paramClass) { if (hasStaticInitializerMethod == null) {
/* 1661 */       Class<java.io.ObjectStreamClass> clazz = null;
/*      */       
/*      */       try {
/* 1664 */         if (clazz == null) {
/* 1665 */           clazz = java.io.ObjectStreamClass.class;
/*      */         }
/*      */         
/* 1668 */         hasStaticInitializerMethod = clazz.getDeclaredMethod("hasStaticInitializer", new Class[] { Class.class });
/*      */       }
/* 1670 */       catch (NoSuchMethodException noSuchMethodException) {}
/*      */ 
/*      */       
/* 1673 */       if (hasStaticInitializerMethod == null)
/*      */       {
/* 1675 */         throw new InternalError("Can't find hasStaticInitializer method on " + clazz
/* 1676 */             .getName());
/*      */       }
/* 1678 */       hasStaticInitializerMethod.setAccessible(true);
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 1683 */       Boolean bool = (Boolean)hasStaticInitializerMethod.invoke(null, new Object[] { paramClass });
/* 1684 */       return bool.booleanValue();
/* 1685 */     } catch (Exception exception) {
/*      */       
/* 1687 */       InternalError internalError = new InternalError("Error invoking hasStaticInitializer");
/* 1688 */       internalError.initCause(exception);
/* 1689 */       throw internalError;
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1701 */   public static final ObjectStreamField[] NO_FIELDS = new ObjectStreamField[0];
/*      */ 
/*      */   
/*      */   private static class ObjectStreamClassEntry
/*      */   {
/*      */     ObjectStreamClassEntry next;
/*      */     
/*      */     private ObjectStreamClass c;
/*      */ 
/*      */     
/*      */     ObjectStreamClassEntry(ObjectStreamClass param1ObjectStreamClass) {
/* 1712 */       this.c = param1ObjectStreamClass;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Object get() {
/* 1718 */       return this.c;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1726 */   private static Comparator compareClassByName = new CompareClassByName();
/*      */   
/*      */   private static class CompareClassByName implements Comparator { private CompareClassByName() {}
/*      */     
/*      */     public int compare(Object param1Object1, Object param1Object2) {
/* 1731 */       Class clazz1 = (Class)param1Object1;
/* 1732 */       Class clazz2 = (Class)param1Object2;
/* 1733 */       return clazz1.getName().compareTo(clazz2.getName());
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1740 */   private static final Comparator compareObjStrFieldsByName = new CompareObjStrFieldsByName();
/*      */   
/*      */   private static class CompareObjStrFieldsByName implements Comparator { private CompareObjStrFieldsByName() {}
/*      */     
/*      */     public int compare(Object param1Object1, Object param1Object2) {
/* 1745 */       ObjectStreamField objectStreamField1 = (ObjectStreamField)param1Object1;
/* 1746 */       ObjectStreamField objectStreamField2 = (ObjectStreamField)param1Object2;
/*      */       
/* 1748 */       return objectStreamField1.getName().compareTo(objectStreamField2.getName());
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1755 */   private static Comparator compareMemberByName = new CompareMemberByName();
/*      */   
/*      */   private static class CompareMemberByName implements Comparator { private CompareMemberByName() {}
/*      */     
/*      */     public int compare(Object param1Object1, Object param1Object2) {
/* 1760 */       String str1 = ((Member)param1Object1).getName();
/* 1761 */       String str2 = ((Member)param1Object2).getName();
/*      */       
/* 1763 */       if (param1Object1 instanceof Method) {
/* 1764 */         str1 = str1 + ObjectStreamClass.getSignature((Method)param1Object1);
/* 1765 */         str2 = str2 + ObjectStreamClass.getSignature((Method)param1Object2);
/* 1766 */       } else if (param1Object1 instanceof Constructor) {
/* 1767 */         str1 = str1 + ObjectStreamClass.getSignature((Constructor)param1Object1);
/* 1768 */         str2 = str2 + ObjectStreamClass.getSignature((Constructor)param1Object2);
/*      */       } 
/* 1770 */       return str1.compareTo(str2);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class MethodSignature
/*      */     implements Comparator
/*      */   {
/*      */     Member member;
/*      */     
/*      */     String signature;
/*      */ 
/*      */     
/*      */     static MethodSignature[] removePrivateAndSort(Member[] param1ArrayOfMember) {
/* 1785 */       byte b1 = 0;
/* 1786 */       for (byte b2 = 0; b2 < param1ArrayOfMember.length; b2++) {
/* 1787 */         if (!Modifier.isPrivate(param1ArrayOfMember[b2].getModifiers())) {
/* 1788 */           b1++;
/*      */         }
/*      */       } 
/* 1791 */       MethodSignature[] arrayOfMethodSignature = new MethodSignature[b1];
/* 1792 */       byte b3 = 0;
/* 1793 */       for (byte b4 = 0; b4 < param1ArrayOfMember.length; b4++) {
/* 1794 */         if (!Modifier.isPrivate(param1ArrayOfMember[b4].getModifiers())) {
/* 1795 */           arrayOfMethodSignature[b3] = new MethodSignature(param1ArrayOfMember[b4]);
/* 1796 */           b3++;
/*      */         } 
/*      */       } 
/* 1799 */       if (b3 > 0)
/* 1800 */         Arrays.sort(arrayOfMethodSignature, arrayOfMethodSignature[0]); 
/* 1801 */       return arrayOfMethodSignature;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int compare(Object param1Object1, Object param1Object2) {
/*      */       int i;
/* 1808 */       if (param1Object1 == param1Object2) {
/* 1809 */         return 0;
/*      */       }
/* 1811 */       MethodSignature methodSignature1 = (MethodSignature)param1Object1;
/* 1812 */       MethodSignature methodSignature2 = (MethodSignature)param1Object2;
/*      */ 
/*      */       
/* 1815 */       if (isConstructor()) {
/* 1816 */         i = methodSignature1.signature.compareTo(methodSignature2.signature);
/*      */       } else {
/* 1818 */         i = methodSignature1.member.getName().compareTo(methodSignature2.member.getName());
/* 1819 */         if (i == 0)
/* 1820 */           i = methodSignature1.signature.compareTo(methodSignature2.signature); 
/*      */       } 
/* 1822 */       return i;
/*      */     }
/*      */     
/*      */     private final boolean isConstructor() {
/* 1826 */       return this.member instanceof Constructor;
/*      */     }
/*      */     private MethodSignature(Member param1Member) {
/* 1829 */       this.member = param1Member;
/* 1830 */       if (isConstructor()) {
/* 1831 */         this.signature = ObjectStreamClass.getSignature((Constructor)param1Member);
/*      */       } else {
/* 1833 */         this.signature = ObjectStreamClass.getSignature((Method)param1Member);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Method getInheritableMethod(Class<?> paramClass1, String paramString, Class<?>[] paramArrayOfClass, Class<?> paramClass2) {
/* 1850 */     Method method = null;
/* 1851 */     Class<?> clazz = paramClass1;
/* 1852 */     while (clazz != null) {
/*      */       try {
/* 1854 */         method = clazz.getDeclaredMethod(paramString, paramArrayOfClass);
/*      */         break;
/* 1856 */       } catch (NoSuchMethodException noSuchMethodException) {
/* 1857 */         clazz = clazz.getSuperclass();
/*      */       } 
/*      */     } 
/*      */     
/* 1861 */     if (method == null || method.getReturnType() != paramClass2) {
/* 1862 */       return null;
/*      */     }
/* 1864 */     method.setAccessible(true);
/* 1865 */     int i = method.getModifiers();
/* 1866 */     if ((i & 0x408) != 0)
/* 1867 */       return null; 
/* 1868 */     if ((i & 0x5) != 0)
/* 1869 */       return method; 
/* 1870 */     if ((i & 0x2) != 0) {
/* 1871 */       return (paramClass1 == clazz) ? method : null;
/*      */     }
/* 1873 */     return packageEquals(paramClass1, clazz) ? method : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean packageEquals(Class<?> paramClass1, Class<?> paramClass2) {
/* 1884 */     Package package_1 = paramClass1.getPackage(), package_2 = paramClass2.getPackage();
/* 1885 */     return (package_1 == package_2 || (package_1 != null && package_1.equals(package_2)));
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/io/ObjectStreamClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */