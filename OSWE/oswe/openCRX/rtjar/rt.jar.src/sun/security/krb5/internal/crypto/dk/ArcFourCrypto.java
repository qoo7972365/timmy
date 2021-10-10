/*     */ package sun.security.krb5.internal.crypto.dk;
/*     */ 
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.Mac;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import sun.security.krb5.Confounder;
/*     */ import sun.security.krb5.KrbCryptoException;
/*     */ import sun.security.krb5.internal.crypto.KeyUsage;
/*     */ import sun.security.provider.MD4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArcFourCrypto
/*     */   extends DkCrypto
/*     */ {
/*     */   private static final boolean debug = false;
/*     */   private static final int confounderSize = 8;
/*  50 */   private static final byte[] ZERO_IV = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
/*     */   private static final int hashSize = 16;
/*     */   private final int keyLength;
/*     */   
/*     */   public ArcFourCrypto(int paramInt) {
/*  55 */     this.keyLength = paramInt;
/*     */   }
/*     */   
/*     */   protected int getKeySeedLength() {
/*  59 */     return this.keyLength;
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte[] randomToKey(byte[] paramArrayOfbyte) {
/*  64 */     return paramArrayOfbyte;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] stringToKey(char[] paramArrayOfchar) throws GeneralSecurityException {
/*  69 */     return stringToKey(paramArrayOfchar, (byte[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] stringToKey(char[] paramArrayOfchar, byte[] paramArrayOfbyte) throws GeneralSecurityException {
/*  79 */     if (paramArrayOfbyte != null && paramArrayOfbyte.length > 0) {
/*  80 */       throw new RuntimeException("Invalid parameter to stringToKey");
/*     */     }
/*     */     
/*  83 */     byte[] arrayOfByte1 = null;
/*  84 */     byte[] arrayOfByte2 = null;
/*     */     
/*     */     try {
/*  87 */       arrayOfByte1 = charToUtf16(paramArrayOfchar);
/*     */ 
/*     */       
/*  90 */       MessageDigest messageDigest = MD4.getInstance();
/*  91 */       messageDigest.update(arrayOfByte1);
/*  92 */       arrayOfByte2 = messageDigest.digest();
/*  93 */     } catch (Exception exception) {
/*  94 */       return null;
/*     */     } finally {
/*  96 */       if (arrayOfByte1 != null) {
/*  97 */         Arrays.fill(arrayOfByte1, (byte)0);
/*     */       }
/*     */     } 
/*     */     
/* 101 */     return arrayOfByte2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Cipher getCipher(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) throws GeneralSecurityException {
/* 108 */     if (paramArrayOfbyte2 == null) {
/* 109 */       paramArrayOfbyte2 = ZERO_IV;
/*     */     }
/* 111 */     SecretKeySpec secretKeySpec = new SecretKeySpec(paramArrayOfbyte1, "ARCFOUR");
/* 112 */     Cipher cipher = Cipher.getInstance("ARCFOUR");
/* 113 */     IvParameterSpec ivParameterSpec = new IvParameterSpec(paramArrayOfbyte2, 0, paramArrayOfbyte2.length);
/* 114 */     cipher.init(paramInt, secretKeySpec, ivParameterSpec);
/* 115 */     return cipher;
/*     */   }
/*     */   
/*     */   public int getChecksumLength() {
/* 119 */     return 16;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] getHmac(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws GeneralSecurityException {
/* 128 */     SecretKeySpec secretKeySpec = new SecretKeySpec(paramArrayOfbyte1, "HmacMD5");
/* 129 */     Mac mac = Mac.getInstance("HmacMD5");
/* 130 */     mac.init(secretKeySpec);
/*     */ 
/*     */     
/* 133 */     return mac.doFinal(paramArrayOfbyte2);
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
/*     */   public byte[] calculateChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3) throws GeneralSecurityException {
/* 148 */     if (!KeyUsage.isValid(paramInt1)) {
/* 149 */       throw new GeneralSecurityException("Invalid key usage number: " + paramInt1);
/*     */     }
/*     */ 
/*     */     
/* 153 */     byte[] arrayOfByte1 = null;
/*     */     
/*     */     try {
/* 156 */       byte[] arrayOfByte5 = "signaturekey".getBytes();
/*     */       
/* 158 */       byte[] arrayOfByte6 = new byte[arrayOfByte5.length + 1];
/* 159 */       System.arraycopy(arrayOfByte5, 0, arrayOfByte6, 0, arrayOfByte5.length);
/* 160 */       arrayOfByte1 = getHmac(paramArrayOfbyte1, arrayOfByte6);
/* 161 */     } catch (Exception exception) {
/* 162 */       GeneralSecurityException generalSecurityException = new GeneralSecurityException("Calculate Checkum Failed!");
/*     */       
/* 164 */       generalSecurityException.initCause(exception);
/* 165 */       throw generalSecurityException;
/*     */     } 
/*     */ 
/*     */     
/* 169 */     byte[] arrayOfByte2 = getSalt(paramInt1);
/*     */ 
/*     */     
/* 172 */     MessageDigest messageDigest = null;
/*     */     try {
/* 174 */       messageDigest = MessageDigest.getInstance("MD5");
/* 175 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 176 */       GeneralSecurityException generalSecurityException = new GeneralSecurityException("Calculate Checkum Failed!");
/*     */       
/* 178 */       generalSecurityException.initCause(noSuchAlgorithmException);
/* 179 */       throw generalSecurityException;
/*     */     } 
/* 181 */     messageDigest.update(arrayOfByte2);
/* 182 */     messageDigest.update(paramArrayOfbyte2, paramInt2, paramInt3);
/* 183 */     byte[] arrayOfByte3 = messageDigest.digest();
/*     */ 
/*     */     
/* 186 */     byte[] arrayOfByte4 = getHmac(arrayOfByte1, arrayOfByte3);
/*     */ 
/*     */ 
/*     */     
/* 190 */     if (arrayOfByte4.length == getChecksumLength())
/* 191 */       return arrayOfByte4; 
/* 192 */     if (arrayOfByte4.length > getChecksumLength()) {
/* 193 */       byte[] arrayOfByte = new byte[getChecksumLength()];
/* 194 */       System.arraycopy(arrayOfByte4, 0, arrayOfByte, 0, arrayOfByte.length);
/* 195 */       return arrayOfByte;
/*     */     } 
/* 197 */     throw new GeneralSecurityException("checksum size too short: " + arrayOfByte4.length + "; expecting : " + 
/* 198 */         getChecksumLength());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encryptSeq(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException, KrbCryptoException {
/* 209 */     if (!KeyUsage.isValid(paramInt1)) {
/* 210 */       throw new GeneralSecurityException("Invalid key usage number: " + paramInt1);
/*     */     }
/*     */ 
/*     */     
/* 214 */     byte[] arrayOfByte1 = new byte[4];
/* 215 */     byte[] arrayOfByte2 = getHmac(paramArrayOfbyte1, arrayOfByte1);
/*     */ 
/*     */     
/* 218 */     arrayOfByte2 = getHmac(arrayOfByte2, paramArrayOfbyte2);
/*     */     
/* 220 */     Cipher cipher = Cipher.getInstance("ARCFOUR");
/* 221 */     SecretKeySpec secretKeySpec = new SecretKeySpec(arrayOfByte2, "ARCFOUR");
/* 222 */     cipher.init(1, secretKeySpec);
/* 223 */     return cipher.doFinal(paramArrayOfbyte3, paramInt2, paramInt3);
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
/*     */   public byte[] decryptSeq(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException, KrbCryptoException {
/* 235 */     if (!KeyUsage.isValid(paramInt1)) {
/* 236 */       throw new GeneralSecurityException("Invalid key usage number: " + paramInt1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 241 */     byte[] arrayOfByte1 = new byte[4];
/* 242 */     byte[] arrayOfByte2 = getHmac(paramArrayOfbyte1, arrayOfByte1);
/*     */ 
/*     */     
/* 245 */     arrayOfByte2 = getHmac(arrayOfByte2, paramArrayOfbyte2);
/*     */     
/* 247 */     Cipher cipher = Cipher.getInstance("ARCFOUR");
/* 248 */     SecretKeySpec secretKeySpec = new SecretKeySpec(arrayOfByte2, "ARCFOUR");
/* 249 */     cipher.init(2, secretKeySpec);
/* 250 */     return cipher.doFinal(paramArrayOfbyte3, paramInt2, paramInt3);
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
/*     */   public byte[] encrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, int paramInt2, int paramInt3) throws GeneralSecurityException, KrbCryptoException {
/* 262 */     if (!KeyUsage.isValid(paramInt1)) {
/* 263 */       throw new GeneralSecurityException("Invalid key usage number: " + paramInt1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     byte[] arrayOfByte1 = Confounder.bytes(8);
/*     */ 
/*     */     
/* 275 */     int i = roundup(arrayOfByte1.length + paramInt3, 1);
/* 276 */     byte[] arrayOfByte2 = new byte[i];
/* 277 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte1.length);
/* 278 */     System.arraycopy(paramArrayOfbyte4, paramInt2, arrayOfByte2, arrayOfByte1.length, paramInt3);
/*     */ 
/*     */ 
/*     */     
/* 282 */     byte[] arrayOfByte3 = new byte[paramArrayOfbyte1.length];
/* 283 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte3, 0, paramArrayOfbyte1.length);
/*     */ 
/*     */     
/* 286 */     byte[] arrayOfByte4 = getSalt(paramInt1);
/*     */ 
/*     */     
/* 289 */     byte[] arrayOfByte5 = getHmac(arrayOfByte3, arrayOfByte4);
/*     */ 
/*     */     
/* 292 */     byte[] arrayOfByte6 = getHmac(arrayOfByte5, arrayOfByte2);
/*     */ 
/*     */     
/* 295 */     byte[] arrayOfByte7 = getHmac(arrayOfByte5, arrayOfByte6);
/*     */     
/* 297 */     Cipher cipher = Cipher.getInstance("ARCFOUR");
/* 298 */     SecretKeySpec secretKeySpec = new SecretKeySpec(arrayOfByte7, "ARCFOUR");
/* 299 */     cipher.init(1, secretKeySpec);
/* 300 */     byte[] arrayOfByte8 = cipher.doFinal(arrayOfByte2, 0, arrayOfByte2.length);
/*     */ 
/*     */     
/* 303 */     byte[] arrayOfByte9 = new byte[16 + arrayOfByte8.length];
/* 304 */     System.arraycopy(arrayOfByte6, 0, arrayOfByte9, 0, 16);
/* 305 */     System.arraycopy(arrayOfByte8, 0, arrayOfByte9, 16, arrayOfByte8.length);
/*     */     
/* 307 */     return arrayOfByte9;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encryptRaw(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException, KrbCryptoException {
/* 317 */     if (!KeyUsage.isValid(paramInt1)) {
/* 318 */       throw new GeneralSecurityException("Invalid key usage number: " + paramInt1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 328 */     byte[] arrayOfByte1 = new byte[paramArrayOfbyte1.length];
/* 329 */     for (byte b = 0; b <= 15; b++) {
/* 330 */       arrayOfByte1[b] = (byte)(paramArrayOfbyte1[b] ^ 0xF0);
/*     */     }
/* 332 */     byte[] arrayOfByte2 = new byte[4];
/* 333 */     byte[] arrayOfByte3 = getHmac(arrayOfByte1, arrayOfByte2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 339 */     arrayOfByte3 = getHmac(arrayOfByte3, paramArrayOfbyte2);
/*     */     
/* 341 */     Cipher cipher = Cipher.getInstance("ARCFOUR");
/* 342 */     SecretKeySpec secretKeySpec = new SecretKeySpec(arrayOfByte3, "ARCFOUR");
/* 343 */     cipher.init(1, secretKeySpec);
/* 344 */     return cipher.doFinal(paramArrayOfbyte3, paramInt2, paramInt3);
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
/*     */   public byte[] decrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException {
/* 357 */     if (!KeyUsage.isValid(paramInt1)) {
/* 358 */       throw new GeneralSecurityException("Invalid key usage number: " + paramInt1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 366 */     byte[] arrayOfByte1 = new byte[paramArrayOfbyte1.length];
/* 367 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte1, 0, paramArrayOfbyte1.length);
/*     */ 
/*     */     
/* 370 */     byte[] arrayOfByte2 = getSalt(paramInt1);
/*     */ 
/*     */     
/* 373 */     byte[] arrayOfByte3 = getHmac(arrayOfByte1, arrayOfByte2);
/*     */ 
/*     */     
/* 376 */     byte[] arrayOfByte4 = new byte[16];
/* 377 */     System.arraycopy(paramArrayOfbyte3, paramInt2, arrayOfByte4, 0, 16);
/* 378 */     byte[] arrayOfByte5 = getHmac(arrayOfByte3, arrayOfByte4);
/*     */ 
/*     */     
/* 381 */     Cipher cipher = Cipher.getInstance("ARCFOUR");
/* 382 */     SecretKeySpec secretKeySpec = new SecretKeySpec(arrayOfByte5, "ARCFOUR");
/* 383 */     cipher.init(2, secretKeySpec);
/* 384 */     byte[] arrayOfByte6 = cipher.doFinal(paramArrayOfbyte3, paramInt2 + 16, paramInt3 - 16);
/*     */ 
/*     */ 
/*     */     
/* 388 */     byte[] arrayOfByte7 = getHmac(arrayOfByte3, arrayOfByte6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 395 */     boolean bool = false;
/* 396 */     if (arrayOfByte7.length >= 16) {
/* 397 */       for (byte b = 0; b < 16; b++) {
/* 398 */         if (arrayOfByte7[b] != paramArrayOfbyte3[b]) {
/* 399 */           bool = true;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 407 */     if (bool) {
/* 408 */       throw new GeneralSecurityException("Checksum failed");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 413 */     byte[] arrayOfByte8 = new byte[arrayOfByte6.length - 8];
/* 414 */     System.arraycopy(arrayOfByte6, 8, arrayOfByte8, 0, arrayOfByte8.length);
/*     */     
/* 416 */     return arrayOfByte8;
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
/*     */   public byte[] decryptRaw(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3, byte[] paramArrayOfbyte4) throws GeneralSecurityException {
/* 429 */     if (!KeyUsage.isValid(paramInt1)) {
/* 430 */       throw new GeneralSecurityException("Invalid key usage number: " + paramInt1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 439 */     byte[] arrayOfByte1 = new byte[paramArrayOfbyte1.length];
/* 440 */     for (byte b = 0; b <= 15; b++) {
/* 441 */       arrayOfByte1[b] = (byte)(paramArrayOfbyte1[b] ^ 0xF0);
/*     */     }
/* 443 */     byte[] arrayOfByte2 = new byte[4];
/* 444 */     byte[] arrayOfByte3 = getHmac(arrayOfByte1, arrayOfByte2);
/*     */ 
/*     */     
/* 447 */     byte[] arrayOfByte4 = new byte[4];
/* 448 */     System.arraycopy(paramArrayOfbyte4, 0, arrayOfByte4, 0, arrayOfByte4.length);
/*     */ 
/*     */     
/* 451 */     arrayOfByte3 = getHmac(arrayOfByte3, arrayOfByte4);
/*     */     
/* 453 */     Cipher cipher = Cipher.getInstance("ARCFOUR");
/* 454 */     SecretKeySpec secretKeySpec = new SecretKeySpec(arrayOfByte3, "ARCFOUR");
/* 455 */     cipher.init(2, secretKeySpec);
/* 456 */     return cipher.doFinal(paramArrayOfbyte3, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] getSalt(int paramInt) {
/* 463 */     int i = arcfour_translate_usage(paramInt);
/* 464 */     byte[] arrayOfByte = new byte[4];
/* 465 */     arrayOfByte[0] = (byte)(i & 0xFF);
/* 466 */     arrayOfByte[1] = (byte)(i >> 8 & 0xFF);
/* 467 */     arrayOfByte[2] = (byte)(i >> 16 & 0xFF);
/* 468 */     arrayOfByte[3] = (byte)(i >> 24 & 0xFF);
/* 469 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   
/*     */   private int arcfour_translate_usage(int paramInt) {
/* 474 */     switch (paramInt) { case 3:
/* 475 */         return 8;
/* 476 */       case 9: return 8;
/* 477 */       case 23: return 13; }
/* 478 */      return paramInt;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/dk/ArcFourCrypto.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */