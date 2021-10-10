/*     */ package sun.security.krb5.internal.crypto.dk;
/*     */ 
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.Mac;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.SecretKeyFactory;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.PBEKeySpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import sun.security.krb5.Confounder;
/*     */ import sun.security.krb5.KrbCryptoException;
/*     */ import sun.security.krb5.internal.crypto.KeyUsage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AesDkCrypto
/*     */   extends DkCrypto
/*     */ {
/*     */   private static final boolean debug = false;
/*     */   private static final int BLOCK_SIZE = 16;
/*     */   private static final int DEFAULT_ITERATION_COUNT = 4096;
/*  89 */   private static final byte[] ZERO_IV = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*     */   
/*     */   private static final int hashSize = 12;
/*     */   private final int keyLength;
/*     */   
/*     */   public AesDkCrypto(int paramInt) {
/*  95 */     this.keyLength = paramInt;
/*     */   }
/*     */   
/*     */   protected int getKeySeedLength() {
/*  99 */     return this.keyLength;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] stringToKey(char[] paramArrayOfchar, String paramString, byte[] paramArrayOfbyte) throws GeneralSecurityException {
/* 105 */     byte[] arrayOfByte = null;
/*     */     try {
/* 107 */       arrayOfByte = paramString.getBytes("UTF-8");
/* 108 */       return stringToKey(paramArrayOfchar, arrayOfByte, paramArrayOfbyte);
/* 109 */     } catch (Exception exception) {
/* 110 */       return null;
/*     */     } finally {
/* 112 */       if (arrayOfByte != null) {
/* 113 */         Arrays.fill(arrayOfByte, (byte)0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] stringToKey(char[] paramArrayOfchar, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws GeneralSecurityException {
/* 121 */     int i = 4096;
/* 122 */     if (paramArrayOfbyte2 != null) {
/* 123 */       if (paramArrayOfbyte2.length != 4) {
/* 124 */         throw new RuntimeException("Invalid parameter to stringToKey");
/*     */       }
/* 126 */       i = readBigEndian(paramArrayOfbyte2, 0, 4);
/*     */     } 
/*     */     
/* 129 */     byte[] arrayOfByte = randomToKey(PBKDF2(paramArrayOfchar, paramArrayOfbyte1, i, 
/* 130 */           getKeySeedLength()));
/* 131 */     return dk(arrayOfByte, KERBEROS_CONSTANT);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] randomToKey(byte[] paramArrayOfbyte) {
/* 137 */     return paramArrayOfbyte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Cipher getCipher(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) throws GeneralSecurityException {
/* 144 */     if (paramArrayOfbyte2 == null) {
/* 145 */       paramArrayOfbyte2 = ZERO_IV;
/*     */     }
/* 147 */     SecretKeySpec secretKeySpec = new SecretKeySpec(paramArrayOfbyte1, "AES");
/* 148 */     Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
/* 149 */     IvParameterSpec ivParameterSpec = new IvParameterSpec(paramArrayOfbyte2, 0, paramArrayOfbyte2.length);
/* 150 */     cipher.init(paramInt, secretKeySpec, ivParameterSpec);
/* 151 */     return cipher;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChecksumLength() {
/* 156 */     return 12;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] getHmac(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws GeneralSecurityException {
/* 165 */     SecretKeySpec secretKeySpec = new SecretKeySpec(paramArrayOfbyte1, "HMAC");
/* 166 */     Mac mac = Mac.getInstance("HmacSHA1");
/* 167 */     mac.init(secretKeySpec);
/*     */ 
/*     */     
/* 170 */     byte[] arrayOfByte1 = mac.doFinal(paramArrayOfbyte2);
/*     */ 
/*     */     
/* 173 */     byte[] arrayOfByte2 = new byte[12];
/* 174 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, 12);
/* 175 */     return arrayOfByte2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] calculateChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3) throws GeneralSecurityException {
/* 184 */     if (!KeyUsage.isValid(paramInt1)) {
/* 185 */       throw new GeneralSecurityException("Invalid key usage number: " + paramInt1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 190 */     byte[] arrayOfByte1 = new byte[5];
/* 191 */     arrayOfByte1[0] = (byte)(paramInt1 >> 24 & 0xFF);
/* 192 */     arrayOfByte1[1] = (byte)(paramInt1 >> 16 & 0xFF);
/* 193 */     arrayOfByte1[2] = (byte)(paramInt1 >> 8 & 0xFF);
/* 194 */     arrayOfByte1[3] = (byte)(paramInt1 & 0xFF);
/*     */     
/* 196 */     arrayOfByte1[4] = -103;
/*     */     
/* 198 */     byte[] arrayOfByte2 = dk(paramArrayOfbyte1, arrayOfByte1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 210 */       byte[] arrayOfByte = getHmac(arrayOfByte2, paramArrayOfbyte2);
/*     */ 
/*     */ 
/*     */       
/* 214 */       if (arrayOfByte.length == getChecksumLength())
/* 215 */         return arrayOfByte; 
/* 216 */       if (arrayOfByte.length > getChecksumLength()) {
/* 217 */         byte[] arrayOfByte3 = new byte[getChecksumLength()];
/* 218 */         System.arraycopy(arrayOfByte, 0, arrayOfByte3, 0, arrayOfByte3.length);
/* 219 */         return arrayOfByte3;
/*     */       } 
/* 221 */       throw new GeneralSecurityException("checksum size too short: " + arrayOfByte.length + "; expecting : " + 
/* 222 */           getChecksumLength());
/*     */     } finally {
/*     */       
/* 225 */       Arrays.fill(arrayOfByte2, 0, arrayOfByte2.length, (byte)0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, int paramInt2, int paramInt3) throws GeneralSecurityException, KrbCryptoException {
/* 236 */     if (!KeyUsage.isValid(paramInt1)) {
/* 237 */       throw new GeneralSecurityException("Invalid key usage number: " + paramInt1);
/*     */     }
/*     */     
/* 240 */     return encryptCTS(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramArrayOfbyte4, paramInt2, paramInt3, true);
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
/*     */   public byte[] encryptRaw(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException, KrbCryptoException {
/* 252 */     if (!KeyUsage.isValid(paramInt1)) {
/* 253 */       throw new GeneralSecurityException("Invalid key usage number: " + paramInt1);
/*     */     }
/*     */     
/* 256 */     return encryptCTS(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, (byte[])null, paramArrayOfbyte3, paramInt2, paramInt3, false);
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
/*     */   public byte[] decrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException {
/* 268 */     if (!KeyUsage.isValid(paramInt1)) {
/* 269 */       throw new GeneralSecurityException("Invalid key usage number: " + paramInt1);
/*     */     }
/*     */     
/* 272 */     return decryptCTS(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt2, paramInt3, true);
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
/*     */   public byte[] decryptRaw(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException {
/* 287 */     if (!KeyUsage.isValid(paramInt1)) {
/* 288 */       throw new GeneralSecurityException("Invalid key usage number: " + paramInt1);
/*     */     }
/*     */     
/* 291 */     return decryptCTS(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt2, paramInt3, false);
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
/*     */   private byte[] encryptCTS(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, int paramInt2, int paramInt3, boolean paramBoolean) throws GeneralSecurityException, KrbCryptoException {
/* 304 */     byte[] arrayOfByte1 = null;
/* 305 */     byte[] arrayOfByte2 = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 318 */       byte[] arrayOfByte3 = new byte[5];
/* 319 */       arrayOfByte3[0] = (byte)(paramInt1 >> 24 & 0xFF);
/* 320 */       arrayOfByte3[1] = (byte)(paramInt1 >> 16 & 0xFF);
/* 321 */       arrayOfByte3[2] = (byte)(paramInt1 >> 8 & 0xFF);
/* 322 */       arrayOfByte3[3] = (byte)(paramInt1 & 0xFF);
/* 323 */       arrayOfByte3[4] = -86;
/* 324 */       arrayOfByte1 = dk(paramArrayOfbyte1, arrayOfByte3);
/*     */       
/* 326 */       byte[] arrayOfByte4 = null;
/* 327 */       if (paramBoolean) {
/* 328 */         byte[] arrayOfByte = Confounder.bytes(16);
/* 329 */         arrayOfByte4 = new byte[arrayOfByte.length + paramInt3];
/* 330 */         System.arraycopy(arrayOfByte, 0, arrayOfByte4, 0, arrayOfByte.length);
/*     */         
/* 332 */         System.arraycopy(paramArrayOfbyte4, paramInt2, arrayOfByte4, arrayOfByte.length, paramInt3);
/*     */       } else {
/*     */         
/* 335 */         arrayOfByte4 = new byte[paramInt3];
/* 336 */         System.arraycopy(paramArrayOfbyte4, paramInt2, arrayOfByte4, 0, paramInt3);
/*     */       } 
/*     */ 
/*     */       
/* 340 */       byte[] arrayOfByte5 = new byte[arrayOfByte4.length + 12];
/*     */ 
/*     */       
/* 343 */       Cipher cipher = Cipher.getInstance("AES/CTS/NoPadding");
/* 344 */       SecretKeySpec secretKeySpec = new SecretKeySpec(arrayOfByte1, "AES");
/* 345 */       IvParameterSpec ivParameterSpec = new IvParameterSpec(paramArrayOfbyte2, 0, paramArrayOfbyte2.length);
/* 346 */       cipher.init(1, secretKeySpec, ivParameterSpec);
/* 347 */       cipher.doFinal(arrayOfByte4, 0, arrayOfByte4.length, arrayOfByte5);
/*     */ 
/*     */       
/* 350 */       arrayOfByte3[4] = 85;
/* 351 */       arrayOfByte2 = dk(paramArrayOfbyte1, arrayOfByte3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 359 */       byte[] arrayOfByte6 = getHmac(arrayOfByte2, arrayOfByte4);
/*     */ 
/*     */       
/* 362 */       System.arraycopy(arrayOfByte6, 0, arrayOfByte5, arrayOfByte4.length, arrayOfByte6.length);
/*     */       
/* 364 */       return arrayOfByte5;
/*     */     } finally {
/* 366 */       if (arrayOfByte1 != null) {
/* 367 */         Arrays.fill(arrayOfByte1, 0, arrayOfByte1.length, (byte)0);
/*     */       }
/* 369 */       if (arrayOfByte2 != null) {
/* 370 */         Arrays.fill(arrayOfByte2, 0, arrayOfByte2.length, (byte)0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] decryptCTS(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3, boolean paramBoolean) throws GeneralSecurityException {
/* 382 */     byte[] arrayOfByte1 = null;
/* 383 */     byte[] arrayOfByte2 = null;
/*     */ 
/*     */     
/*     */     try {
/* 387 */       byte[] arrayOfByte3 = new byte[5];
/* 388 */       arrayOfByte3[0] = (byte)(paramInt1 >> 24 & 0xFF);
/* 389 */       arrayOfByte3[1] = (byte)(paramInt1 >> 16 & 0xFF);
/* 390 */       arrayOfByte3[2] = (byte)(paramInt1 >> 8 & 0xFF);
/* 391 */       arrayOfByte3[3] = (byte)(paramInt1 & 0xFF);
/*     */       
/* 393 */       arrayOfByte3[4] = -86;
/* 394 */       arrayOfByte1 = dk(paramArrayOfbyte1, arrayOfByte3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 410 */       Cipher cipher = Cipher.getInstance("AES/CTS/NoPadding");
/* 411 */       SecretKeySpec secretKeySpec = new SecretKeySpec(arrayOfByte1, "AES");
/* 412 */       IvParameterSpec ivParameterSpec = new IvParameterSpec(paramArrayOfbyte2, 0, paramArrayOfbyte2.length);
/* 413 */       cipher.init(2, secretKeySpec, ivParameterSpec);
/* 414 */       byte[] arrayOfByte4 = cipher.doFinal(paramArrayOfbyte3, paramInt2, paramInt3 - 12);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 422 */       arrayOfByte3[4] = 85;
/* 423 */       arrayOfByte2 = dk(paramArrayOfbyte1, arrayOfByte3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 431 */       byte[] arrayOfByte5 = getHmac(arrayOfByte2, arrayOfByte4);
/* 432 */       int i = paramInt2 + paramInt3 - 12;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 438 */       boolean bool = false;
/* 439 */       if (arrayOfByte5.length >= 12) {
/* 440 */         for (byte b = 0; b < 12; b++) {
/* 441 */           if (arrayOfByte5[b] != paramArrayOfbyte3[i + b]) {
/* 442 */             bool = true;
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 450 */       if (bool) {
/* 451 */         throw new GeneralSecurityException("Checksum failed");
/*     */       }
/*     */       
/* 454 */       if (paramBoolean) {
/*     */ 
/*     */         
/* 457 */         byte[] arrayOfByte = new byte[arrayOfByte4.length - 16];
/* 458 */         System.arraycopy(arrayOfByte4, 16, arrayOfByte, 0, arrayOfByte.length);
/*     */         
/* 460 */         return arrayOfByte;
/*     */       } 
/* 462 */       return arrayOfByte4;
/*     */     } finally {
/*     */       
/* 465 */       if (arrayOfByte1 != null) {
/* 466 */         Arrays.fill(arrayOfByte1, 0, arrayOfByte1.length, (byte)0);
/*     */       }
/* 468 */       if (arrayOfByte2 != null) {
/* 469 */         Arrays.fill(arrayOfByte2, 0, arrayOfByte2.length, (byte)0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] PBKDF2(char[] paramArrayOfchar, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws GeneralSecurityException {
/* 480 */     PBEKeySpec pBEKeySpec = new PBEKeySpec(paramArrayOfchar, paramArrayOfbyte, paramInt1, paramInt2);
/*     */     
/* 482 */     SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
/* 483 */     SecretKey secretKey = secretKeyFactory.generateSecret(pBEKeySpec);
/* 484 */     return secretKey.getEncoded();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int readBigEndian(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 490 */     int i = 0;
/* 491 */     int j = (paramInt2 - 1) * 8;
/* 492 */     while (paramInt2 > 0) {
/* 493 */       i += (paramArrayOfbyte[paramInt1] & 0xFF) << j;
/* 494 */       j -= 8;
/* 495 */       paramInt1++;
/* 496 */       paramInt2--;
/*     */     } 
/* 498 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/dk/AesDkCrypto.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */