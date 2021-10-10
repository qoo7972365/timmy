/*     */ package com.sun.security.sasl;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.logging.Level;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CramMD5Client
/*     */   extends CramMD5Base
/*     */   implements SaslClient
/*     */ {
/*     */   private String username;
/*     */   
/*     */   CramMD5Client(String paramString, byte[] paramArrayOfbyte) throws SaslException {
/*  59 */     if (paramString == null || paramArrayOfbyte == null) {
/*  60 */       throw new SaslException("CRAM-MD5: authentication ID and password must be specified");
/*     */     }
/*     */ 
/*     */     
/*  64 */     this.username = paramString;
/*  65 */     this.pw = paramArrayOfbyte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasInitialResponse() {
/*  72 */     return false;
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
/*     */   public byte[] evaluateChallenge(byte[] paramArrayOfbyte) throws SaslException {
/*  92 */     if (this.completed) {
/*  93 */       throw new IllegalStateException("CRAM-MD5 authentication already completed");
/*     */     }
/*     */ 
/*     */     
/*  97 */     if (this.aborted) {
/*  98 */       throw new IllegalStateException("CRAM-MD5 authentication previously aborted due to error");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 104 */       if (logger.isLoggable(Level.FINE)) {
/* 105 */         logger.log(Level.FINE, "CRAMCLNT01:Received challenge: {0}", new String(paramArrayOfbyte, "UTF8"));
/*     */       }
/*     */ 
/*     */       
/* 109 */       String str1 = HMAC_MD5(this.pw, paramArrayOfbyte);
/*     */ 
/*     */       
/* 112 */       clearPassword();
/*     */ 
/*     */       
/* 115 */       String str2 = this.username + " " + str1;
/*     */       
/* 117 */       logger.log(Level.FINE, "CRAMCLNT02:Sending response: {0}", str2);
/*     */       
/* 119 */       this.completed = true;
/*     */       
/* 121 */       return str2.getBytes("UTF8");
/* 122 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 123 */       this.aborted = true;
/* 124 */       throw new SaslException("MD5 algorithm not available on platform", noSuchAlgorithmException);
/* 125 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 126 */       this.aborted = true;
/* 127 */       throw new SaslException("UTF8 not available on platform", unsupportedEncodingException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/sasl/CramMD5Client.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */