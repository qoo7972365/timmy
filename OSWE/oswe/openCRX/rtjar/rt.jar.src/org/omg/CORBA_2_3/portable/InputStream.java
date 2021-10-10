/*     */ package org.omg.CORBA_2_3.portable;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.io.SerializablePermission;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import org.omg.CORBA.NO_IMPLEMENT;
/*     */ import org.omg.CORBA.portable.BoxedValueHelper;
/*     */ import org.omg.CORBA.portable.InputStream;
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
/*     */ 
/*     */ 
/*     */ public abstract class InputStream
/*     */   extends InputStream
/*     */ {
/*     */   private static final String ALLOW_SUBCLASS_PROP = "jdk.corba.allowInputStreamSubclass";
/*     */   
/*  53 */   private static final boolean allowSubclass = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */       {
/*     */         public Boolean run()
/*     */         {
/*  57 */           String str = System.getProperty("jdk.corba.allowInputStreamSubclass");
/*  58 */           return Boolean.valueOf((str == null) ? false : (
/*  59 */               !str.equalsIgnoreCase("false")));
/*     */         }
/*     */       })).booleanValue();
/*     */   
/*     */   private static Void checkPermission() {
/*  64 */     SecurityManager securityManager = System.getSecurityManager();
/*  65 */     if (securityManager != null && 
/*  66 */       !allowSubclass) {
/*  67 */       securityManager.checkPermission(new SerializablePermission("enableSubclassImplementation"));
/*     */     }
/*     */     
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InputStream(Void paramVoid) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream() {
/*  84 */     this(checkPermission());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Serializable read_value() {
/*  92 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Serializable read_value(Class paramClass) {
/* 101 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Serializable read_value(BoxedValueHelper paramBoxedValueHelper) {
/* 111 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Serializable read_value(String paramString) {
/* 120 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Serializable read_value(Serializable paramSerializable) {
/* 131 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object read_abstract_interface() {
/* 139 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object read_abstract_interface(Class paramClass) {
/* 149 */     throw new NO_IMPLEMENT();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA_2_3/portable/InputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */