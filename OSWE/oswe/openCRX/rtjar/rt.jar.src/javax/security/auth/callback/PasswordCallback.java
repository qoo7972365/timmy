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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PasswordCallback
/*     */   implements Callback, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2267422647454909926L;
/*     */   private String prompt;
/*     */   private boolean echoOn;
/*     */   private char[] inputPassword;
/*     */   
/*     */   public PasswordCallback(String paramString, boolean paramBoolean) {
/*  71 */     if (paramString == null || paramString.length() == 0) {
/*  72 */       throw new IllegalArgumentException();
/*     */     }
/*  74 */     this.prompt = paramString;
/*  75 */     this.echoOn = paramBoolean;
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
/*  86 */     return this.prompt;
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
/*     */   public boolean isEchoOn() {
/*  99 */     return this.echoOn;
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
/*     */   public void setPassword(char[] paramArrayOfchar) {
/* 115 */     this.inputPassword = (paramArrayOfchar == null) ? null : (char[])paramArrayOfchar.clone();
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
/*     */   public char[] getPassword() {
/* 130 */     return (this.inputPassword == null) ? null : (char[])this.inputPassword.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearPassword() {
/* 137 */     if (this.inputPassword != null)
/* 138 */       for (byte b = 0; b < this.inputPassword.length; b++)
/* 139 */         this.inputPassword[b] = ' ';  
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/security/auth/callback/PasswordCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */