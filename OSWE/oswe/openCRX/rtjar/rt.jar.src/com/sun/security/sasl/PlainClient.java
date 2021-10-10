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
/*     */ final class PlainClient
/*     */   implements SaslClient
/*     */ {
/*     */   private boolean completed = false;
/*     */   private byte[] pw;
/*     */   private String authorizationID;
/*     */   private String authenticationID;
/*  42 */   private static byte SEP = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PlainClient(String paramString1, String paramString2, byte[] paramArrayOfbyte) throws SaslException {
/*  57 */     if (paramString2 == null || paramArrayOfbyte == null) {
/*  58 */       throw new SaslException("PLAIN: authorization ID and password must be specified");
/*     */     }
/*     */ 
/*     */     
/*  62 */     this.authorizationID = paramString1;
/*  63 */     this.authenticationID = paramString2;
/*  64 */     this.pw = paramArrayOfbyte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMechanismName() {
/*  74 */     return "PLAIN";
/*     */   }
/*     */   
/*     */   public boolean hasInitialResponse() {
/*  78 */     return true;
/*     */   }
/*     */   
/*     */   public void dispose() throws SaslException {
/*  82 */     clearPassword();
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
/*     */   public byte[] evaluateChallenge(byte[] paramArrayOfbyte) throws SaslException {
/*  96 */     if (this.completed) {
/*  97 */       throw new IllegalStateException("PLAIN authentication already completed");
/*     */     }
/*     */     
/* 100 */     this.completed = true;
/*     */ 
/*     */     
/*     */     try {
/* 104 */       byte[] arrayOfByte1 = (this.authorizationID != null) ? this.authorizationID.getBytes("UTF8") : null;
/*     */       
/* 106 */       byte[] arrayOfByte2 = this.authenticationID.getBytes("UTF8");
/*     */       
/* 108 */       byte[] arrayOfByte3 = new byte[this.pw.length + arrayOfByte2.length + 2 + ((arrayOfByte1 == null) ? 0 : arrayOfByte1.length)];
/*     */ 
/*     */       
/* 111 */       int i = 0;
/* 112 */       if (arrayOfByte1 != null) {
/* 113 */         System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, arrayOfByte1.length);
/* 114 */         i = arrayOfByte1.length;
/*     */       } 
/* 116 */       arrayOfByte3[i++] = SEP;
/* 117 */       System.arraycopy(arrayOfByte2, 0, arrayOfByte3, i, arrayOfByte2.length);
/*     */       
/* 119 */       i += arrayOfByte2.length;
/* 120 */       arrayOfByte3[i++] = SEP;
/*     */       
/* 122 */       System.arraycopy(this.pw, 0, arrayOfByte3, i, this.pw.length);
/*     */       
/* 124 */       clearPassword();
/* 125 */       return arrayOfByte3;
/* 126 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 127 */       throw new SaslException("Cannot get UTF-8 encoding of ids", unsupportedEncodingException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComplete() {
/* 138 */     return this.completed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] unwrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException {
/* 148 */     if (this.completed) {
/* 149 */       throw new SaslException("PLAIN supports neither integrity nor privacy");
/*     */     }
/*     */     
/* 152 */     throw new IllegalStateException("PLAIN authentication not completed");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException {
/* 162 */     if (this.completed) {
/* 163 */       throw new SaslException("PLAIN supports neither integrity nor privacy");
/*     */     }
/*     */     
/* 166 */     throw new IllegalStateException("PLAIN authentication not completed");
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
/*     */   public Object getNegotiatedProperty(String paramString) {
/* 181 */     if (this.completed) {
/* 182 */       if (paramString.equals("javax.security.sasl.qop")) {
/* 183 */         return "auth";
/*     */       }
/* 185 */       return null;
/*     */     } 
/*     */     
/* 188 */     throw new IllegalStateException("PLAIN authentication not completed");
/*     */   }
/*     */ 
/*     */   
/*     */   private void clearPassword() {
/* 193 */     if (this.pw != null) {
/*     */       
/* 195 */       for (byte b = 0; b < this.pw.length; b++) {
/* 196 */         this.pw[b] = 0;
/*     */       }
/* 198 */       this.pw = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void finalize() {
/* 203 */     clearPassword();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/sasl/PlainClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */