/*     */ package com.sun.security.sasl;
/*     */ 
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Arrays;
/*     */ import java.util.logging.Logger;
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
/*     */ abstract class CramMD5Base
/*     */ {
/*     */   protected boolean completed = false;
/*     */   protected boolean aborted = false;
/*     */   protected byte[] pw;
/*     */   private static final int MD5_BLOCKSIZE = 64;
/*     */   private static final String SASL_LOGGER_NAME = "javax.security.sasl";
/*     */   protected static Logger logger;
/*     */   
/*     */   protected CramMD5Base() {
/*  50 */     initLogger();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMechanismName() {
/*  59 */     return "CRAM-MD5";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComplete() {
/*  69 */     return this.completed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] unwrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException {
/*  79 */     if (this.completed) {
/*  80 */       throw new IllegalStateException("CRAM-MD5 supports neither integrity nor privacy");
/*     */     }
/*     */     
/*  83 */     throw new IllegalStateException("CRAM-MD5 authentication not completed");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SaslException {
/*  94 */     if (this.completed) {
/*  95 */       throw new IllegalStateException("CRAM-MD5 supports neither integrity nor privacy");
/*     */     }
/*     */     
/*  98 */     throw new IllegalStateException("CRAM-MD5 authentication not completed");
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
/* 113 */     if (this.completed) {
/* 114 */       if (paramString.equals("javax.security.sasl.qop")) {
/* 115 */         return "auth";
/*     */       }
/* 117 */       return null;
/*     */     } 
/*     */     
/* 120 */     throw new IllegalStateException("CRAM-MD5 authentication not completed");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() throws SaslException {
/* 126 */     clearPassword();
/*     */   }
/*     */   
/*     */   protected void clearPassword() {
/* 130 */     if (this.pw != null) {
/*     */       
/* 132 */       for (byte b = 0; b < this.pw.length; b++) {
/* 133 */         this.pw[b] = 0;
/*     */       }
/* 135 */       this.pw = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void finalize() {
/* 140 */     clearPassword();
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
/*     */   static final String HMAC_MD5(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws NoSuchAlgorithmException {
/* 160 */     MessageDigest messageDigest = MessageDigest.getInstance("MD5");
/*     */ 
/*     */     
/* 163 */     if (paramArrayOfbyte1.length > 64) {
/* 164 */       paramArrayOfbyte1 = messageDigest.digest(paramArrayOfbyte1);
/*     */     }
/*     */     
/* 167 */     byte[] arrayOfByte1 = new byte[64];
/* 168 */     byte[] arrayOfByte2 = new byte[64];
/*     */ 
/*     */     
/*     */     byte b;
/*     */     
/* 173 */     for (b = 0; b < paramArrayOfbyte1.length; b++) {
/* 174 */       arrayOfByte1[b] = paramArrayOfbyte1[b];
/* 175 */       arrayOfByte2[b] = paramArrayOfbyte1[b];
/*     */     } 
/*     */ 
/*     */     
/* 179 */     for (b = 0; b < 64; b++) {
/* 180 */       arrayOfByte1[b] = (byte)(arrayOfByte1[b] ^ 0x36);
/* 181 */       arrayOfByte2[b] = (byte)(arrayOfByte2[b] ^ 0x5C);
/*     */     } 
/*     */ 
/*     */     
/* 185 */     messageDigest.update(arrayOfByte1);
/* 186 */     messageDigest.update(paramArrayOfbyte2);
/* 187 */     byte[] arrayOfByte3 = messageDigest.digest();
/*     */ 
/*     */     
/* 190 */     messageDigest.update(arrayOfByte2);
/* 191 */     messageDigest.update(arrayOfByte3);
/* 192 */     arrayOfByte3 = messageDigest.digest();
/*     */ 
/*     */     
/* 195 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 197 */     for (b = 0; b < arrayOfByte3.length; b++) {
/* 198 */       if ((arrayOfByte3[b] & 0xFF) < 16) {
/* 199 */         stringBuffer.append("0" + 
/* 200 */             Integer.toHexString(arrayOfByte3[b] & 0xFF));
/*     */       } else {
/* 202 */         stringBuffer.append(
/* 203 */             Integer.toHexString(arrayOfByte3[b] & 0xFF));
/*     */       } 
/*     */     } 
/*     */     
/* 207 */     Arrays.fill(arrayOfByte1, (byte)0);
/* 208 */     Arrays.fill(arrayOfByte2, (byte)0);
/* 209 */     arrayOfByte1 = null;
/* 210 */     arrayOfByte2 = null;
/*     */     
/* 212 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized void initLogger() {
/* 219 */     if (logger == null)
/* 220 */       logger = Logger.getLogger("javax.security.sasl"); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/sasl/CramMD5Base.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */