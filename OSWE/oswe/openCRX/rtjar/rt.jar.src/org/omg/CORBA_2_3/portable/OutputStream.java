/*     */ package org.omg.CORBA_2_3.portable;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.io.SerializablePermission;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import org.omg.CORBA.NO_IMPLEMENT;
/*     */ import org.omg.CORBA.portable.BoxedValueHelper;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class OutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   private static final String ALLOW_SUBCLASS_PROP = "jdk.corba.allowOutputStreamSubclass";
/*     */   
/*  51 */   private static final boolean allowSubclass = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */       {
/*     */         public Boolean run()
/*     */         {
/*  55 */           String str = System.getProperty("jdk.corba.allowOutputStreamSubclass");
/*  56 */           return Boolean.valueOf((str == null) ? false : (
/*  57 */               !str.equalsIgnoreCase("false")));
/*     */         }
/*     */       })).booleanValue();
/*     */   
/*     */   private static Void checkPermission() {
/*  62 */     SecurityManager securityManager = System.getSecurityManager();
/*  63 */     if (securityManager != null && 
/*  64 */       !allowSubclass) {
/*  65 */       securityManager.checkPermission(new SerializablePermission("enableSubclassImplementation"));
/*     */     }
/*     */     
/*  68 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OutputStream(Void paramVoid) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream() {
/*  81 */     this(checkPermission());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write_value(Serializable paramSerializable) {
/*  89 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write_value(Serializable paramSerializable, Class paramClass) {
/*  98 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write_value(Serializable paramSerializable, String paramString) {
/* 108 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write_value(Serializable paramSerializable, BoxedValueHelper paramBoxedValueHelper) {
/* 118 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write_abstract_interface(Object paramObject) {
/* 126 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA_2_3/portable/OutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */