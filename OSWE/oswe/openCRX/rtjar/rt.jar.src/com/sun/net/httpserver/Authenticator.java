/*     */ package com.sun.net.httpserver;
/*     */ 
/*     */ import jdk.Exported;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Exported
/*     */ public abstract class Authenticator
/*     */ {
/*     */   public abstract Result authenticate(HttpExchange paramHttpExchange);
/*     */   
/*     */   public static abstract class Result {}
/*     */   
/*     */   @Exported
/*     */   public static class Failure
/*     */     extends Result
/*     */   {
/*     */     private int responseCode;
/*     */     
/*     */     public Failure(int param1Int) {
/*  57 */       this.responseCode = param1Int;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getResponseCode() {
/*  64 */       return this.responseCode;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Exported
/*     */   public static class Success
/*     */     extends Result
/*     */   {
/*     */     private HttpPrincipal principal;
/*     */ 
/*     */     
/*     */     public Success(HttpPrincipal param1HttpPrincipal) {
/*  78 */       this.principal = param1HttpPrincipal;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public HttpPrincipal getPrincipal() {
/*  84 */       return this.principal;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Exported
/*     */   public static class Retry
/*     */     extends Result
/*     */   {
/*     */     private int responseCode;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Retry(int param1Int) {
/* 101 */       this.responseCode = param1Int;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getResponseCode() {
/* 108 */       return this.responseCode;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/httpserver/Authenticator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */