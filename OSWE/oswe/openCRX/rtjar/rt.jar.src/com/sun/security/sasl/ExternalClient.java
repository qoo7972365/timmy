/*     */ package com.sun.security.sasl;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import javax.security.sasl.SaslClient;
/*     */ import javax.security.sasl.SaslException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ExternalClient
/*     */   implements SaslClient
/*     */ {
/*     */   private byte[] username;
/*     */   private boolean completed = false;
/*     */   
/*     */   ExternalClient(String paramString) throws SaslException {
/*  50 */     if (paramString != null) {
/*     */       try {
/*  52 */         this.username = paramString.getBytes("UTF8");
/*  53 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*  54 */         throw new SaslException("Cannot convert " + paramString + " into UTF-8", unsupportedEncodingException);
/*     */       } 
/*     */     } else {
/*     */       
/*  58 */       this.username = new byte[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMechanismName() {
/*  69 */     return "EXTERNAL";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasInitialResponse() {
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() throws SaslException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] evaluateChallenge(byte[] paramArrayOfbyte) throws SaslException {
/*  95 */     if (this.completed) {
/*  96 */       throw new IllegalStateException("EXTERNAL authentication already completed");
/*     */     }
/*     */     
/*  99 */     this.completed = true;
/* 100 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComplete() {
/* 108 */     return this.completed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] unwrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException {
/* 118 */     if (this.completed) {
/* 119 */       throw new SaslException("EXTERNAL has no supported QOP");
/*     */     }
/* 121 */     throw new IllegalStateException("EXTERNAL authentication Not completed");
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
/*     */   public byte[] wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException {
/* 133 */     if (this.completed) {
/* 134 */       throw new SaslException("EXTERNAL has no supported QOP");
/*     */     }
/* 136 */     throw new IllegalStateException("EXTERNAL authentication not completed");
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
/*     */   public Object getNegotiatedProperty(String paramString) {
/* 152 */     if (this.completed) {
/* 153 */       return null;
/*     */     }
/* 155 */     throw new IllegalStateException("EXTERNAL authentication not completed");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/sasl/ExternalClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */