/*     */ package java.nio.file;
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
/*     */ public final class LinkPermission
/*     */   extends BasicPermission
/*     */ {
/*     */   static final long serialVersionUID = -1441492453772213220L;
/*     */   
/*     */   private void checkName(String paramString) {
/*  69 */     if (!paramString.equals("hard") && !paramString.equals("symbolic")) {
/*  70 */       throw new IllegalArgumentException("name: " + paramString);
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
/*     */   public LinkPermission(String paramString) {
/*  84 */     super(paramString);
/*  85 */     checkName(paramString);
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
/*     */   
/*     */   public LinkPermission(String paramString1, String paramString2) {
/* 101 */     super(paramString1);
/* 102 */     checkName(paramString1);
/* 103 */     if (paramString2 != null && paramString2.length() > 0)
/* 104 */       throw new IllegalArgumentException("actions: " + paramString2); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/LinkPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */