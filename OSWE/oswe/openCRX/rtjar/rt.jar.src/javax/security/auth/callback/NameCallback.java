/*     */ package javax.security.auth.callback;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class NameCallback
/*     */   implements Callback, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3770938795909392253L;
/*     */   private String prompt;
/*     */   private String defaultName;
/*     */   private String inputName;
/*     */   
/*     */   public NameCallback(String paramString) {
/*  66 */     if (paramString == null || paramString.length() == 0)
/*  67 */       throw new IllegalArgumentException(); 
/*  68 */     this.prompt = paramString;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NameCallback(String paramString1, String paramString2) {
/*  88 */     if (paramString1 == null || paramString1.length() == 0 || paramString2 == null || paramString2
/*  89 */       .length() == 0) {
/*  90 */       throw new IllegalArgumentException();
/*     */     }
/*  92 */     this.prompt = paramString1;
/*  93 */     this.defaultName = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrompt() {
/* 104 */     return this.prompt;
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
/*     */   public String getDefaultName() {
/* 116 */     return this.defaultName;
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
/*     */   public void setName(String paramString) {
/* 129 */     this.inputName = paramString;
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
/*     */   public String getName() {
/* 142 */     return this.inputName;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/callback/NameCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */