/*     */ package com.sun.security.sasl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.logging.Level;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.NameCallback;
/*     */ import javax.security.auth.callback.PasswordCallback;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import javax.security.sasl.AuthorizeCallback;
/*     */ import javax.security.sasl.SaslException;
/*     */ import javax.security.sasl.SaslServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CramMD5Server
/*     */   extends CramMD5Base
/*     */   implements SaslServer
/*     */ {
/*     */   private String fqdn;
/*  56 */   private byte[] challengeData = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private String authzid;
/*     */ 
/*     */ 
/*     */   
/*     */   private CallbackHandler cbh;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CramMD5Server(String paramString1, String paramString2, Map<String, ?> paramMap, CallbackHandler paramCallbackHandler) throws SaslException {
/*  70 */     if (paramString2 == null) {
/*  71 */       throw new SaslException("CRAM-MD5: fully qualified server name must be specified");
/*     */     }
/*     */ 
/*     */     
/*  75 */     this.fqdn = paramString2;
/*  76 */     this.cbh = paramCallbackHandler;
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
/*     */   
/*     */   public byte[] evaluateResponse(byte[] paramArrayOfbyte) throws SaslException {
/*  97 */     if (this.completed) {
/*  98 */       throw new IllegalStateException("CRAM-MD5 authentication already completed");
/*     */     }
/*     */ 
/*     */     
/* 102 */     if (this.aborted) {
/* 103 */       throw new IllegalStateException("CRAM-MD5 authentication previously aborted due to error");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 108 */       if (this.challengeData == null) {
/* 109 */         if (paramArrayOfbyte.length != 0) {
/* 110 */           this.aborted = true;
/* 111 */           throw new SaslException("CRAM-MD5 does not expect any initial response");
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 116 */         Random random = new Random();
/* 117 */         long l1 = random.nextLong();
/* 118 */         long l2 = System.currentTimeMillis();
/*     */         
/* 120 */         StringBuffer stringBuffer = new StringBuffer();
/* 121 */         stringBuffer.append('<');
/* 122 */         stringBuffer.append(l1);
/* 123 */         stringBuffer.append('.');
/* 124 */         stringBuffer.append(l2);
/* 125 */         stringBuffer.append('@');
/* 126 */         stringBuffer.append(this.fqdn);
/* 127 */         stringBuffer.append('>');
/* 128 */         String str = stringBuffer.toString();
/*     */         
/* 130 */         logger.log(Level.FINE, "CRAMSRV01:Generated challenge: {0}", str);
/*     */ 
/*     */         
/* 133 */         this.challengeData = str.getBytes("UTF8");
/* 134 */         return (byte[])this.challengeData.clone();
/*     */       } 
/*     */ 
/*     */       
/* 138 */       if (logger.isLoggable(Level.FINE)) {
/* 139 */         logger.log(Level.FINE, "CRAMSRV02:Received response: {0}", new String(paramArrayOfbyte, "UTF8"));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 145 */       byte b1 = 0;
/* 146 */       for (byte b2 = 0; b2 < paramArrayOfbyte.length; b2++) {
/* 147 */         if (paramArrayOfbyte[b2] == 32) {
/* 148 */           b1 = b2;
/*     */           break;
/*     */         } 
/*     */       } 
/* 152 */       if (b1 == 0) {
/* 153 */         this.aborted = true;
/* 154 */         throw new SaslException("CRAM-MD5: Invalid response; space missing");
/*     */       } 
/*     */       
/* 157 */       String str1 = new String(paramArrayOfbyte, 0, b1, "UTF8");
/*     */       
/* 159 */       logger.log(Level.FINE, "CRAMSRV03:Extracted username: {0}", str1);
/*     */ 
/*     */ 
/*     */       
/* 163 */       NameCallback nameCallback = new NameCallback("CRAM-MD5 authentication ID: ", str1);
/*     */       
/* 165 */       PasswordCallback passwordCallback = new PasswordCallback("CRAM-MD5 password: ", false);
/*     */       
/* 167 */       this.cbh.handle(new Callback[] { nameCallback, passwordCallback });
/* 168 */       char[] arrayOfChar = passwordCallback.getPassword();
/* 169 */       if (arrayOfChar == null || arrayOfChar.length == 0) {
/*     */         
/* 171 */         this.aborted = true;
/* 172 */         throw new SaslException("CRAM-MD5: username not found: " + str1);
/*     */       } 
/*     */       
/* 175 */       passwordCallback.clearPassword();
/* 176 */       String str2 = new String(arrayOfChar);
/* 177 */       for (byte b3 = 0; b3 < arrayOfChar.length; b3++) {
/* 178 */         arrayOfChar[b3] = Character.MIN_VALUE;
/*     */       }
/* 180 */       this.pw = str2.getBytes("UTF8");
/*     */ 
/*     */ 
/*     */       
/* 184 */       String str3 = HMAC_MD5(this.pw, this.challengeData);
/*     */       
/* 186 */       logger.log(Level.FINE, "CRAMSRV04:Expecting digest: {0}", str3);
/*     */ 
/*     */ 
/*     */       
/* 190 */       clearPassword();
/*     */ 
/*     */       
/* 193 */       byte[] arrayOfByte = str3.getBytes("UTF8");
/* 194 */       int i = paramArrayOfbyte.length - b1 - 1;
/* 195 */       if (arrayOfByte.length != i) {
/* 196 */         this.aborted = true;
/* 197 */         throw new SaslException("Invalid response");
/*     */       } 
/* 199 */       byte b4 = 0;
/* 200 */       for (int j = b1 + 1; j < paramArrayOfbyte.length; j++) {
/* 201 */         if (arrayOfByte[b4++] != paramArrayOfbyte[j]) {
/* 202 */           this.aborted = true;
/* 203 */           throw new SaslException("Invalid response");
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 208 */       AuthorizeCallback authorizeCallback = new AuthorizeCallback(str1, str1);
/* 209 */       this.cbh.handle(new Callback[] { authorizeCallback });
/* 210 */       if (authorizeCallback.isAuthorized()) {
/* 211 */         this.authzid = authorizeCallback.getAuthorizedID();
/*     */       } else {
/*     */         
/* 214 */         this.aborted = true;
/* 215 */         throw new SaslException("CRAM-MD5: user not authorized: " + str1);
/*     */       } 
/*     */ 
/*     */       
/* 219 */       logger.log(Level.FINE, "CRAMSRV05:Authorization id: {0}", this.authzid);
/*     */ 
/*     */       
/* 222 */       this.completed = true;
/* 223 */       return null;
/*     */     }
/* 225 */     catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 226 */       this.aborted = true;
/* 227 */       throw new SaslException("UTF8 not available on platform", unsupportedEncodingException);
/* 228 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 229 */       this.aborted = true;
/* 230 */       throw new SaslException("MD5 algorithm not available on platform", noSuchAlgorithmException);
/* 231 */     } catch (UnsupportedCallbackException unsupportedCallbackException) {
/* 232 */       this.aborted = true;
/* 233 */       throw new SaslException("CRAM-MD5 authentication failed", unsupportedCallbackException);
/* 234 */     } catch (SaslException saslException) {
/* 235 */       throw saslException;
/* 236 */     } catch (IOException iOException) {
/* 237 */       this.aborted = true;
/* 238 */       throw new SaslException("CRAM-MD5 authentication failed", iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getAuthorizationID() {
/* 243 */     if (this.completed) {
/* 244 */       return this.authzid;
/*     */     }
/* 246 */     throw new IllegalStateException("CRAM-MD5 authentication not completed");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/sasl/CramMD5Server.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */