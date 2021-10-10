/*     */ package javax.management;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.security.BasicPermission;
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
/*     */ public class MBeanTrustPermission
/*     */   extends BasicPermission
/*     */ {
/*     */   private static final long serialVersionUID = -2952178077029018140L;
/*     */   
/*     */   public MBeanTrustPermission(String paramString) {
/*  65 */     this(paramString, null);
/*     */   }
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
/*     */   public MBeanTrustPermission(String paramString1, String paramString2) {
/*  80 */     super(paramString1, paramString2);
/*  81 */     validate(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void validate(String paramString1, String paramString2) {
/*  86 */     if (paramString2 != null && paramString2.length() > 0) {
/*  87 */       throw new IllegalArgumentException("MBeanTrustPermission actions must be null: " + paramString2);
/*     */     }
/*     */ 
/*     */     
/*  91 */     if (!paramString1.equals("register") && !paramString1.equals("*")) {
/*  92 */       throw new IllegalArgumentException("MBeanTrustPermission: Unknown target name [" + paramString1 + "]");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 101 */     paramObjectInputStream.defaultReadObject();
/*     */     try {
/* 103 */       validate(getName(), getActions());
/* 104 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 105 */       throw new InvalidObjectException(illegalArgumentException.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/MBeanTrustPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */