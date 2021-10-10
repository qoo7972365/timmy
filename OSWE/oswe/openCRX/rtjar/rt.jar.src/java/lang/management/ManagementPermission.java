/*     */ package java.lang.management;
/*     */ 
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
/*     */ public final class ManagementPermission
/*     */   extends BasicPermission
/*     */ {
/*     */   private static final long serialVersionUID = 1897496590799378737L;
/*     */   
/*     */   public ManagementPermission(String paramString) {
/* 100 */     super(paramString);
/* 101 */     if (!paramString.equals("control") && !paramString.equals("monitor")) {
/* 102 */       throw new IllegalArgumentException("name: " + paramString);
/*     */     }
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
/*     */   public ManagementPermission(String paramString1, String paramString2) throws IllegalArgumentException {
/* 118 */     super(paramString1);
/* 119 */     if (!paramString1.equals("control") && !paramString1.equals("monitor")) {
/* 120 */       throw new IllegalArgumentException("name: " + paramString1);
/*     */     }
/* 122 */     if (paramString2 != null && paramString2.length() > 0)
/* 123 */       throw new IllegalArgumentException("actions: " + paramString2); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/management/ManagementPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */