/*     */ package com.sun.net.httpserver;
/*     */ 
/*     */ import java.security.Principal;
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
/*     */ @Exported
/*     */ public class HttpPrincipal
/*     */   implements Principal
/*     */ {
/*     */   private String username;
/*     */   private String realm;
/*     */   
/*     */   public HttpPrincipal(String paramString1, String paramString2) {
/*  47 */     if (paramString1 == null || paramString2 == null) {
/*  48 */       throw new NullPointerException();
/*     */     }
/*  50 */     this.username = paramString1;
/*  51 */     this.realm = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  61 */     if (!(paramObject instanceof HttpPrincipal)) {
/*  62 */       return false;
/*     */     }
/*  64 */     HttpPrincipal httpPrincipal = (HttpPrincipal)paramObject;
/*  65 */     return (this.username.equals(httpPrincipal.username) && this.realm
/*  66 */       .equals(httpPrincipal.realm));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  74 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsername() {
/*  81 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRealm() {
/*  88 */     return this.realm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  96 */     return (this.username + this.realm).hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 103 */     return getName();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/httpserver/HttpPrincipal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */