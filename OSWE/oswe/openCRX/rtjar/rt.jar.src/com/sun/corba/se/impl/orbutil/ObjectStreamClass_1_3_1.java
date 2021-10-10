/*      */ package com.sun.corba.se.impl.orbutil;
/*      */ 
/*      */ import com.sun.corba.se.impl.io.ObjectStreamClass;
/*      */ import com.sun.corba.se.impl.io.ValueUtility;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.Externalizable;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Member;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.lang.reflect.Proxy;
/*      */ import java.security.AccessController;
/*      */ import java.security.DigestOutputStream;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.Hashtable;
/*      */ import org.omg.CORBA.ValueMember;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ObjectStreamClass_1_3_1
/*      */   implements Serializable
/*      */ {
/*      */   public static final long kDefaultUID = -1L;
/*   91 */   private static Object[] noArgsList = new Object[0];
/*   92 */   private static Class<?>[] noTypesList = new Class[0];
/*      */ 
/*      */ 
/*      */   
/*      */   private static Hashtable translatedFields;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final ObjectStreamClass_1_3_1 lookup(Class<?> paramClass) {
/*  102 */     ObjectStreamClass_1_3_1 objectStreamClass_1_3_1 = lookupInternal(paramClass);
/*  103 */     if (objectStreamClass_1_3_1.isSerializable() || objectStreamClass_1_3_1.isExternalizable())
/*  104 */       return objectStreamClass_1_3_1; 
/*  105 */     return null;
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
/*      */   static ObjectStreamClass_1_3_1 lookupInternal(Class<?> paramClass) {
/*  117 */     ObjectStreamClass_1_3_1 objectStreamClass_1_3_1 = null;
/*  118 */     synchronized (descriptorFor) {
/*      */       
/*  120 */       objectStreamClass_1_3_1 = findDescriptorFor(paramClass);
/*  121 */       if (objectStreamClass_1_3_1 != null) {
/*  122 */         return objectStreamClass_1_3_1;
/*      */       }
/*      */ 
/*      */       
/*  126 */       boolean bool = Serializable.class.isAssignableFrom(paramClass);
/*      */ 
/*      */ 
/*      */       
/*  130 */       ObjectStreamClass_1_3_1 objectStreamClass_1_3_11 = null;
/*  131 */       if (bool) {
/*  132 */         Class<?> clazz = paramClass.getSuperclass();
/*  133 */         if (clazz != null) {
/*  134 */           objectStreamClass_1_3_11 = lookup(clazz);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  141 */       boolean bool1 = false;
/*  142 */       if (bool) {
/*      */ 
/*      */         
/*  145 */         bool1 = ((objectStreamClass_1_3_11 != null && objectStreamClass_1_3_11.isExternalizable()) || Externalizable.class.isAssignableFrom(paramClass)) ? true : false;
/*  146 */         if (bool1) {
/*  147 */           bool = false;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  154 */       objectStreamClass_1_3_1 = new ObjectStreamClass_1_3_1(paramClass, objectStreamClass_1_3_11, bool, bool1);
/*      */     } 
/*      */     
/*  157 */     objectStreamClass_1_3_1.init();
/*  158 */     return objectStreamClass_1_3_1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getName() {
/*  165 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final long getSerialVersionUID(Class<?> paramClass) {
/*  175 */     ObjectStreamClass_1_3_1 objectStreamClass_1_3_1 = lookup(paramClass);
/*  176 */     if (objectStreamClass_1_3_1 != null)
/*      */     {
/*  178 */       return objectStreamClass_1_3_1.getSerialVersionUID();
/*      */     }
/*  180 */     return 0L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final long getSerialVersionUID() {
/*  190 */     return this.suid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getSerialVersionUIDStr() {
/*  200 */     if (this.suidStr == null)
/*  201 */       this.suidStr = Long.toHexString(this.suid).toUpperCase(); 
/*  202 */     return this.suidStr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final long getActualSerialVersionUID(Class<?> paramClass) {
/*  210 */     ObjectStreamClass_1_3_1 objectStreamClass_1_3_1 = lookup(paramClass);
/*  211 */     if (objectStreamClass_1_3_1 != null)
/*      */     {
/*  213 */       return objectStreamClass_1_3_1.getActualSerialVersionUID();
/*      */     }
/*  215 */     return 0L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final long getActualSerialVersionUID() {
/*  222 */     return this.actualSuid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getActualSerialVersionUIDStr() {
/*  229 */     if (this.actualSuidStr == null)
/*  230 */       this.actualSuidStr = Long.toHexString(this.actualSuid).toUpperCase(); 
/*  231 */     return this.actualSuidStr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Class<?> forClass() {
/*  239 */     return this.ofClass;
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
/*  251 */     if (this.fields.length > 0) {
/*  252 */       ObjectStreamField[] arrayOfObjectStreamField = new ObjectStreamField[this.fields.length];
/*  253 */       System.arraycopy(this.fields, 0, arrayOfObjectStreamField, 0, this.fields.length);
/*  254 */       return arrayOfObjectStreamField;
/*      */     } 
/*  256 */     return this.fields;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasField(ValueMember paramValueMember) {
/*  262 */     for (byte b = 0; b < this.fields.length; b++) {
/*      */       try {
/*  264 */         if (this.fields[b].getName().equals(paramValueMember.name))
/*      */         {
/*  266 */           if (this.fields[b].getSignature().equals(ValueUtility.getSignature(paramValueMember))) {
/*  267 */             return true;
/*      */           }
/*      */         }
/*  270 */       } catch (Throwable throwable) {}
/*      */     } 
/*  272 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   final ObjectStreamField[] getFieldsNoCopy() {
/*  277 */     return this.fields;
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
/*  288 */     for (int i = this.fields.length - 1; i >= 0; i--) {
/*  289 */       if (paramString.equals(this.fields[i].getName())) {
/*  290 */         return this.fields[i];
/*      */       }
/*      */     } 
/*  293 */     return null;
/*      */   }
/*      */   
/*      */   public Serializable writeReplace(Serializable paramSerializable) {
/*  297 */     if (this.writeReplaceObjectMethod != null) {
/*      */       try {
/*  299 */         return (Serializable)this.writeReplaceObjectMethod.invoke(paramSerializable, noArgsList);
/*      */       }
/*  301 */       catch (Throwable throwable) {
/*  302 */         throw new RuntimeException(throwable.getMessage());
/*      */       } 
/*      */     }
/*  305 */     return paramSerializable;
/*      */   }
/*      */   
/*      */   public Object readResolve(Object paramObject) {
/*  309 */     if (this.readResolveObjectMethod != null) {
/*      */       try {
/*  311 */         return this.readResolveObjectMethod.invoke(paramObject, noArgsList);
/*      */       }
/*  313 */       catch (Throwable throwable) {
/*  314 */         throw new RuntimeException(throwable.getMessage());
/*      */       } 
/*      */     }
/*  317 */     return paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String toString() {
/*  324 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/*  326 */     stringBuffer.append(this.name);
/*  327 */     stringBuffer.append(": static final long serialVersionUID = ");
/*  328 */     stringBuffer.append(Long.toString(this.suid));
/*  329 */     stringBuffer.append("L;");
/*  330 */     return stringBuffer.toString();
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
/*      */   private ObjectStreamClass_1_3_1(Class<?> paramClass, ObjectStreamClass_1_3_1 paramObjectStreamClass_1_3_1, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/* 1092 */     this.suid = -1L;
/* 1093 */     this.suidStr = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1098 */     this.actualSuid = -1L;
/* 1099 */     this.actualSuidStr = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1109 */     this.lock = new Object(); this.ofClass = paramClass; if (Proxy.isProxyClass(paramClass)) this.forProxyClass = true;  this.name = paramClass.getName(); this.superclass = paramObjectStreamClass_1_3_1; this.serializable = paramBoolean1; if (!this.forProxyClass) this.externalizable = paramBoolean2;  insertDescriptorFor(this); } private void init() { synchronized (this.lock) { final Class<?> cl = this.ofClass; if (this.fields != null) return;  if (!this.serializable || this.externalizable || this.forProxyClass || this.name.equals("java.lang.String")) { this.fields = NO_FIELDS; } else if (this.serializable) { AccessController.doPrivileged(new PrivilegedAction() { public Object run() { try { Field field = cl.getDeclaredField("serialPersistentFields"); field.setAccessible(true); ObjectStreamField[] arrayOfObjectStreamField = (ObjectStreamField[])field.get(cl); int i = field.getModifiers(); if (Modifier.isPrivate(i) && Modifier.isStatic(i) && Modifier.isFinal(i)) ObjectStreamClass_1_3_1.this.fields = (ObjectStreamField[])ObjectStreamClass_1_3_1.translateFields((Object[])field.get(cl));  } catch (NoSuchFieldException noSuchFieldException) { ObjectStreamClass_1_3_1.this.fields = null; } catch (IllegalAccessException illegalAccessException) { ObjectStreamClass_1_3_1.this.fields = null; } catch (IllegalArgumentException illegalArgumentException) { ObjectStreamClass_1_3_1.this.fields = null; } catch (ClassCastException classCastException) { ObjectStreamClass_1_3_1.this.fields = null; }  if (ObjectStreamClass_1_3_1.this.fields == null) { Field[] arrayOfField = cl.getDeclaredFields(); byte b1 = 0; ObjectStreamField[] arrayOfObjectStreamField = new ObjectStreamField[arrayOfField.length]; for (byte b2 = 0; b2 < arrayOfField.length; b2++) { int i = arrayOfField[b2].getModifiers(); if (!Modifier.isStatic(i) && !Modifier.isTransient(i)) arrayOfObjectStreamField[b1++] = new ObjectStreamField(arrayOfField[b2]);  }  ObjectStreamClass_1_3_1.this.fields = new ObjectStreamField[b1]; System.arraycopy(arrayOfObjectStreamField, 0, ObjectStreamClass_1_3_1.this.fields, 0, b1); } else { for (int i = ObjectStreamClass_1_3_1.this.fields.length - 1; i >= 0; i--) { try { Field field = cl.getDeclaredField(ObjectStreamClass_1_3_1.this.fields[i].getName()); if (ObjectStreamClass_1_3_1.this.fields[i].getType() == field.getType()) ObjectStreamClass_1_3_1.this.fields[i].setField(field);  } catch (NoSuchFieldException noSuchFieldException) {} }  }  return null; } }); if (this.fields.length > 1) Arrays.sort((Object[])this.fields);  computeFieldInfo(); }  if (isNonSerializable()) { this.suid = 0L; } else { AccessController.doPrivileged(new PrivilegedAction() { public Object run() { if (ObjectStreamClass_1_3_1.this.forProxyClass) { ObjectStreamClass_1_3_1.this.suid = 0L; } else { try { Field field = cl.getDeclaredField("serialVersionUID"); int i = field.getModifiers(); if (Modifier.isStatic(i) && Modifier.isFinal(i)) { field.setAccessible(true); ObjectStreamClass_1_3_1.this.suid = field.getLong(cl); } else { ObjectStreamClass_1_3_1.this.suid = ObjectStreamClass.getSerialVersionUID(cl); }  } catch (NoSuchFieldException noSuchFieldException) { ObjectStreamClass_1_3_1.this.suid = ObjectStreamClass.getSerialVersionUID(cl); } catch (IllegalAccessException illegalAccessException) { ObjectStreamClass_1_3_1.this.suid = ObjectStreamClass.getSerialVersionUID(cl); }  }  try { ObjectStreamClass_1_3_1.this.writeReplaceObjectMethod = cl.getDeclaredMethod("writeReplace", ObjectStreamClass_1_3_1.noTypesList); if (Modifier.isStatic(ObjectStreamClass_1_3_1.this.writeReplaceObjectMethod.getModifiers())) { ObjectStreamClass_1_3_1.this.writeReplaceObjectMethod = null; } else { ObjectStreamClass_1_3_1.this.writeReplaceObjectMethod.setAccessible(true); }  } catch (NoSuchMethodException noSuchMethodException) {} try { ObjectStreamClass_1_3_1.this.readResolveObjectMethod = cl.getDeclaredMethod("readResolve", ObjectStreamClass_1_3_1.noTypesList); if (Modifier.isStatic(ObjectStreamClass_1_3_1.this.readResolveObjectMethod.getModifiers())) { ObjectStreamClass_1_3_1.this.readResolveObjectMethod = null; } else { ObjectStreamClass_1_3_1.this.readResolveObjectMethod.setAccessible(true); }  } catch (NoSuchMethodException noSuchMethodException) {} if (ObjectStreamClass_1_3_1.this.serializable && !ObjectStreamClass_1_3_1.this.forProxyClass) { try { Class[] arrayOfClass = { ObjectOutputStream.class }; ObjectStreamClass_1_3_1.this.writeObjectMethod = cl.getDeclaredMethod("writeObject", arrayOfClass); ObjectStreamClass_1_3_1.this.hasWriteObjectMethod = true; int i = ObjectStreamClass_1_3_1.this.writeObjectMethod.getModifiers(); if (!Modifier.isPrivate(i) || Modifier.isStatic(i)) { ObjectStreamClass_1_3_1.this.writeObjectMethod = null; ObjectStreamClass_1_3_1.this.hasWriteObjectMethod = false; }  } catch (NoSuchMethodException noSuchMethodException) {} try { Class[] arrayOfClass = { ObjectInputStream.class }; ObjectStreamClass_1_3_1.this.readObjectMethod = cl.getDeclaredMethod("readObject", arrayOfClass); int i = ObjectStreamClass_1_3_1.this.readObjectMethod.getModifiers(); if (!Modifier.isPrivate(i) || Modifier.isStatic(i)) ObjectStreamClass_1_3_1.this.readObjectMethod = null;  } catch (NoSuchMethodException noSuchMethodException) {} }  return null; } }); }  this.actualSuid = computeStructuralUID(this, clazz); }  } ObjectStreamClass_1_3_1(String paramString, long paramLong) { this.suid = -1L; this.suidStr = null; this.actualSuid = -1L; this.actualSuidStr = null; this.lock = new Object(); this.name = paramString; this.suid = paramLong; this.superclass = null; }
/*      */   private static Object[] translateFields(Object[] paramArrayOfObject) throws NoSuchFieldException { try {
/*      */       ObjectStreamField[] arrayOfObjectStreamField = (ObjectStreamField[])paramArrayOfObject; Object[] arrayOfObject1 = null; if (translatedFields == null)
/*      */         translatedFields = new Hashtable<>();  arrayOfObject1 = (Object[])translatedFields.get(arrayOfObjectStreamField); if (arrayOfObject1 != null)
/*      */         return arrayOfObject1;  Class<ObjectStreamField> clazz = ObjectStreamField.class; arrayOfObject1 = (Object[])Array.newInstance(clazz, paramArrayOfObject.length);
/*      */       Object[] arrayOfObject2 = new Object[2];
/*      */       Class[] arrayOfClass = { String.class, Class.class };
/*      */       Constructor<ObjectStreamField> constructor = clazz.getDeclaredConstructor(arrayOfClass);
/*      */       for (int i = arrayOfObjectStreamField.length - 1; i >= 0; i--) {
/*      */         arrayOfObject2[0] = arrayOfObjectStreamField[i].getName();
/*      */         arrayOfObject2[1] = arrayOfObjectStreamField[i].getType();
/*      */         arrayOfObject1[i] = constructor.newInstance(arrayOfObject2);
/*      */       } 
/*      */       translatedFields.put(arrayOfObjectStreamField, arrayOfObject1);
/*      */       return arrayOfObject1;
/*      */     } catch (Throwable throwable) {
/*      */       throw new NoSuchFieldException();
/*      */     }  }
/*      */   static boolean compareClassNames(String paramString1, String paramString2, char paramChar) { int i = paramString1.lastIndexOf(paramChar);
/*      */     if (i < 0)
/*      */       i = 0; 
/*      */     int j = paramString2.lastIndexOf(paramChar);
/*      */     if (j < 0)
/*      */       j = 0; 
/*      */     return paramString1.regionMatches(false, i, paramString2, j, paramString1.length() - i); }
/*      */   final boolean typeEquals(ObjectStreamClass_1_3_1 paramObjectStreamClass_1_3_1) { return (this.suid == paramObjectStreamClass_1_3_1.suid && compareClassNames(this.name, paramObjectStreamClass_1_3_1.name, '.')); }
/*      */   final void setSuperclass(ObjectStreamClass_1_3_1 paramObjectStreamClass_1_3_1) { this.superclass = paramObjectStreamClass_1_3_1; }
/*      */   final ObjectStreamClass_1_3_1 getSuperclass() { return this.superclass; }
/*      */   final boolean hasWriteObject() { return this.hasWriteObjectMethod; }
/*      */   final boolean isCustomMarshaled() { return (hasWriteObject() || isExternalizable()); }
/*      */   boolean hasExternalizableBlockDataMode() { return this.hasExternalizableBlockData; }
/*      */   final ObjectStreamClass_1_3_1 localClassDescriptor() { return this.localClassDesc; }
/* 1141 */   boolean isSerializable() { return this.serializable; } public static final ObjectStreamField[] NO_FIELDS = new ObjectStreamField[0];
/*      */   boolean isExternalizable() { return this.externalizable; }
/*      */   boolean isNonSerializable() { return (!this.externalizable && !this.serializable); }
/*      */   private void computeFieldInfo() { this.primBytes = 0; this.objFields = 0; for (byte b = 0; b < this.fields.length; b++) { switch (this.fields[b].getTypeCode()) { case 'B': case 'Z': this.primBytes++; break;case 'C': case 'S': this.primBytes += 2; break;case 'F': case 'I': this.primBytes += 4; break;case 'D': case 'J': this.primBytes += 8; break;case 'L': case '[': this.objFields++; break; }  }  }
/*      */   private static long computeStructuralUID(ObjectStreamClass_1_3_1 paramObjectStreamClass_1_3_1, Class<?> paramClass) { ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512); long l = 0L; try { if (!Serializable.class.isAssignableFrom(paramClass) || paramClass.isInterface()) return 0L;  if (Externalizable.class.isAssignableFrom(paramClass)) return 1L;  MessageDigest messageDigest = MessageDigest.getInstance("SHA"); DigestOutputStream digestOutputStream = new DigestOutputStream(byteArrayOutputStream, messageDigest); DataOutputStream dataOutputStream = new DataOutputStream(digestOutputStream); Class<?> clazz = paramClass.getSuperclass(); if (clazz != null) dataOutputStream.writeLong(computeStructuralUID(lookup(clazz), clazz));  if (paramObjectStreamClass_1_3_1.hasWriteObject()) { dataOutputStream.writeInt(2); } else { dataOutputStream.writeInt(1); }  ObjectStreamField[] arrayOfObjectStreamField = paramObjectStreamClass_1_3_1.getFields(); byte b1 = 0; for (byte b2 = 0; b2 < arrayOfObjectStreamField.length; b2++) { if (arrayOfObjectStreamField[b2].getField() != null) b1++;  }  Field[] arrayOfField = new Field[b1]; byte b3, b4; for (b3 = 0, b4 = 0; b3 < arrayOfObjectStreamField.length; b3++) { if (arrayOfObjectStreamField[b3].getField() != null) arrayOfField[b4++] = arrayOfObjectStreamField[b3].getField();  }  if (arrayOfField.length > 1) Arrays.sort(arrayOfField, compareMemberByName);  for (b3 = 0; b3 < arrayOfField.length; b3++) { Field field = arrayOfField[b3]; int i = field.getModifiers(); dataOutputStream.writeUTF(field.getName()); dataOutputStream.writeUTF(getSignature(field.getType())); }  dataOutputStream.flush(); byte[] arrayOfByte = messageDigest.digest(); for (b4 = 0; b4 < Math.min(8, arrayOfByte.length); b4++) l += (arrayOfByte[b4] & 0xFF) << b4 * 8;  } catch (IOException iOException) { l = -1L; } catch (NoSuchAlgorithmException noSuchAlgorithmException) { throw new SecurityException(noSuchAlgorithmException.getMessage()); }  return l; }
/*      */   static String getSignature(Class<?> paramClass) { String str = null; if (paramClass.isArray()) { Class<?> clazz = paramClass; byte b1 = 0; while (clazz.isArray()) { b1++; clazz = clazz.getComponentType(); }  StringBuffer stringBuffer = new StringBuffer(); for (byte b2 = 0; b2 < b1; b2++) stringBuffer.append("[");  stringBuffer.append(getSignature(clazz)); str = stringBuffer.toString(); } else if (paramClass.isPrimitive()) { if (paramClass == int.class) { str = "I"; } else if (paramClass == byte.class) { str = "B"; } else if (paramClass == long.class) { str = "J"; } else if (paramClass == float.class) { str = "F"; } else if (paramClass == double.class) { str = "D"; } else if (paramClass == short.class) { str = "S"; } else if (paramClass == char.class) { str = "C"; } else if (paramClass == boolean.class) { str = "Z"; } else if (paramClass == void.class) { str = "V"; }  } else { str = "L" + paramClass.getName().replace('.', '/') + ";"; }  return str; }
/*      */   static String getSignature(Method paramMethod) { StringBuffer stringBuffer = new StringBuffer(); stringBuffer.append("("); Class[] arrayOfClass = paramMethod.getParameterTypes(); for (byte b = 0; b < arrayOfClass.length; b++) stringBuffer.append(getSignature(arrayOfClass[b]));  stringBuffer.append(")"); stringBuffer.append(getSignature(paramMethod.getReturnType())); return stringBuffer.toString(); }
/*      */   static String getSignature(Constructor paramConstructor) { StringBuffer stringBuffer = new StringBuffer(); stringBuffer.append("("); Class[] arrayOfClass = paramConstructor.getParameterTypes(); for (byte b = 0; b < arrayOfClass.length; b++) stringBuffer.append(getSignature(arrayOfClass[b]));  stringBuffer.append(")V"); return stringBuffer.toString(); } private static ObjectStreamClassEntry[] descriptorFor = new ObjectStreamClassEntry[61]; private String name; private ObjectStreamClass_1_3_1 superclass; private boolean serializable; private boolean externalizable; private ObjectStreamField[] fields; private Class<?> ofClass; boolean forProxyClass; private long suid; private String suidStr; private long actualSuid; private String actualSuidStr; int primBytes; int objFields; private Object lock; private boolean hasWriteObjectMethod; private boolean hasExternalizableBlockData; Method writeObjectMethod; Method readObjectMethod; private transient Method writeReplaceObjectMethod; private transient Method readResolveObjectMethod; private ObjectStreamClass_1_3_1 localClassDesc; private static final long serialVersionUID = -6120832682080437368L; private static ObjectStreamClass_1_3_1 findDescriptorFor(Class<?> paramClass) { int i = paramClass.hashCode(); int j = (i & Integer.MAX_VALUE) % descriptorFor.length; ObjectStreamClassEntry objectStreamClassEntry1; while ((objectStreamClassEntry1 = descriptorFor[j]) != null && objectStreamClassEntry1.get() == null) descriptorFor[j] = objectStreamClassEntry1.next;  ObjectStreamClassEntry objectStreamClassEntry2 = objectStreamClassEntry1; while (objectStreamClassEntry1 != null) { ObjectStreamClass_1_3_1 objectStreamClass_1_3_1 = (ObjectStreamClass_1_3_1)objectStreamClassEntry1.get(); if (objectStreamClass_1_3_1 == null) { objectStreamClassEntry2.next = objectStreamClassEntry1.next; } else { if (objectStreamClass_1_3_1.ofClass == paramClass) return objectStreamClass_1_3_1;  objectStreamClassEntry2 = objectStreamClassEntry1; }  objectStreamClassEntry1 = objectStreamClassEntry1.next; }  return null; } private static void insertDescriptorFor(ObjectStreamClass_1_3_1 paramObjectStreamClass_1_3_1) { if (findDescriptorFor(paramObjectStreamClass_1_3_1.ofClass) != null) return;  int i = paramObjectStreamClass_1_3_1.ofClass.hashCode(); int j = (i & Integer.MAX_VALUE) % descriptorFor.length; ObjectStreamClassEntry objectStreamClassEntry = new ObjectStreamClassEntry(paramObjectStreamClass_1_3_1); objectStreamClassEntry.next = descriptorFor[j]; descriptorFor[j] = objectStreamClassEntry; } private static Field[] getDeclaredFields(final Class clz) { return AccessController.<Field[]>doPrivileged(new PrivilegedAction<Field>() {
/*      */           public Object run() { return clz.getDeclaredFields(); }
/*      */         }); } private static class ObjectStreamClassEntry
/*      */   {
/* 1152 */     ObjectStreamClassEntry next; private ObjectStreamClass_1_3_1 c; ObjectStreamClassEntry(ObjectStreamClass_1_3_1 param1ObjectStreamClass_1_3_1) { this.c = param1ObjectStreamClass_1_3_1; }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object get() {
/* 1158 */       return this.c;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1166 */   private static Comparator compareClassByName = new CompareClassByName();
/*      */   
/*      */   private static class CompareClassByName implements Comparator { private CompareClassByName() {}
/*      */     
/*      */     public int compare(Object param1Object1, Object param1Object2) {
/* 1171 */       Class clazz1 = (Class)param1Object1;
/* 1172 */       Class clazz2 = (Class)param1Object2;
/* 1173 */       return clazz1.getName().compareTo(clazz2.getName());
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1180 */   private static Comparator compareMemberByName = new CompareMemberByName();
/*      */   
/*      */   private static class CompareMemberByName implements Comparator { private CompareMemberByName() {}
/*      */     
/*      */     public int compare(Object param1Object1, Object param1Object2) {
/* 1185 */       String str1 = ((Member)param1Object1).getName();
/* 1186 */       String str2 = ((Member)param1Object2).getName();
/*      */       
/* 1188 */       if (param1Object1 instanceof Method) {
/* 1189 */         str1 = str1 + ObjectStreamClass_1_3_1.getSignature((Method)param1Object1);
/* 1190 */         str2 = str2 + ObjectStreamClass_1_3_1.getSignature((Method)param1Object2);
/* 1191 */       } else if (param1Object1 instanceof Constructor) {
/* 1192 */         str1 = str1 + ObjectStreamClass_1_3_1.getSignature((Constructor)param1Object1);
/* 1193 */         str2 = str2 + ObjectStreamClass_1_3_1.getSignature((Constructor)param1Object2);
/*      */       } 
/* 1195 */       return str1.compareTo(str2);
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
/* 1210 */       byte b1 = 0;
/* 1211 */       for (byte b2 = 0; b2 < param1ArrayOfMember.length; b2++) {
/* 1212 */         if (!Modifier.isPrivate(param1ArrayOfMember[b2].getModifiers())) {
/* 1213 */           b1++;
/*      */         }
/*      */       } 
/* 1216 */       MethodSignature[] arrayOfMethodSignature = new MethodSignature[b1];
/* 1217 */       byte b3 = 0;
/* 1218 */       for (byte b4 = 0; b4 < param1ArrayOfMember.length; b4++) {
/* 1219 */         if (!Modifier.isPrivate(param1ArrayOfMember[b4].getModifiers())) {
/* 1220 */           arrayOfMethodSignature[b3] = new MethodSignature(param1ArrayOfMember[b4]);
/* 1221 */           b3++;
/*      */         } 
/*      */       } 
/* 1224 */       if (b3 > 0)
/* 1225 */         Arrays.sort(arrayOfMethodSignature, arrayOfMethodSignature[0]); 
/* 1226 */       return arrayOfMethodSignature;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int compare(Object param1Object1, Object param1Object2) {
/*      */       int i;
/* 1233 */       if (param1Object1 == param1Object2) {
/* 1234 */         return 0;
/*      */       }
/* 1236 */       MethodSignature methodSignature1 = (MethodSignature)param1Object1;
/* 1237 */       MethodSignature methodSignature2 = (MethodSignature)param1Object2;
/*      */ 
/*      */       
/* 1240 */       if (isConstructor()) {
/* 1241 */         i = methodSignature1.signature.compareTo(methodSignature2.signature);
/*      */       } else {
/* 1243 */         i = methodSignature1.member.getName().compareTo(methodSignature2.member.getName());
/* 1244 */         if (i == 0)
/* 1245 */           i = methodSignature1.signature.compareTo(methodSignature2.signature); 
/*      */       } 
/* 1247 */       return i;
/*      */     }
/*      */     
/*      */     private final boolean isConstructor() {
/* 1251 */       return this.member instanceof Constructor;
/*      */     }
/*      */     private MethodSignature(Member param1Member) {
/* 1254 */       this.member = param1Member;
/* 1255 */       if (isConstructor()) {
/* 1256 */         this.signature = ObjectStreamClass_1_3_1.getSignature((Constructor)param1Member);
/*      */       } else {
/* 1258 */         this.signature = ObjectStreamClass_1_3_1.getSignature((Method)param1Member);
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/ObjectStreamClass_1_3_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */