/*     */ package com.sun.net.httpserver;
/*     */ 
/*     */ import java.net.InetSocketAddress;
/*     */ import javax.net.ssl.SSLParameters;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public abstract class HttpsParameters
/*     */ {
/*     */   private String[] cipherSuites;
/*     */   private String[] protocols;
/*     */   private boolean wantClientAuth;
/*     */   private boolean needClientAuth;
/*     */   
/*     */   public abstract HttpsConfigurator getHttpsConfigurator();
/*     */   
/*     */   public abstract InetSocketAddress getClientAddress();
/*     */   
/*     */   public abstract void setSSLParameters(SSLParameters paramSSLParameters);
/*     */   
/*     */   public String[] getCipherSuites() {
/*  96 */     return (this.cipherSuites != null) ? (String[])this.cipherSuites.clone() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCipherSuites(String[] paramArrayOfString) {
/* 105 */     this.cipherSuites = (paramArrayOfString != null) ? (String[])paramArrayOfString.clone() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getProtocols() {
/* 116 */     return (this.protocols != null) ? (String[])this.protocols.clone() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProtocols(String[] paramArrayOfString) {
/* 125 */     this.protocols = (paramArrayOfString != null) ? (String[])paramArrayOfString.clone() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getWantClientAuth() {
/* 134 */     return this.wantClientAuth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWantClientAuth(boolean paramBoolean) {
/* 144 */     this.wantClientAuth = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getNeedClientAuth() {
/* 153 */     return this.needClientAuth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNeedClientAuth(boolean paramBoolean) {
/* 163 */     this.needClientAuth = paramBoolean;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/net/httpserver/HttpsParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */