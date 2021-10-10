/*     */ package sun.security.rsa;
/*     */ 
/*     */ import java.security.DigestException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.spec.MGF1ParameterSpec;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.spec.OAEPParameterSpec;
/*     */ import javax.crypto.spec.PSource;
/*     */ import sun.security.jca.JCAUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RSAPadding
/*     */ {
/*     */   public static final int PAD_BLOCKTYPE_1 = 1;
/*     */   public static final int PAD_BLOCKTYPE_2 = 2;
/*     */   public static final int PAD_NONE = 3;
/*     */   public static final int PAD_OAEP_MGF1 = 4;
/*     */   private final int type;
/*     */   private final int paddedSize;
/*     */   private SecureRandom random;
/*     */   private final int maxDataSize;
/*     */   private MessageDigest md;
/*     */   private MessageDigest mgfMd;
/*     */   private byte[] lHash;
/*     */   
/*     */   public static RSAPadding getInstance(int paramInt1, int paramInt2) throws InvalidKeyException, InvalidAlgorithmParameterException {
/* 123 */     return new RSAPadding(paramInt1, paramInt2, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RSAPadding getInstance(int paramInt1, int paramInt2, SecureRandom paramSecureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
/* 133 */     return new RSAPadding(paramInt1, paramInt2, paramSecureRandom, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RSAPadding getInstance(int paramInt1, int paramInt2, SecureRandom paramSecureRandom, OAEPParameterSpec paramOAEPParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
/* 143 */     return new RSAPadding(paramInt1, paramInt2, paramSecureRandom, paramOAEPParameterSpec);
/*     */   }
/*     */   
/*     */   private RSAPadding(int paramInt1, int paramInt2, SecureRandom paramSecureRandom, OAEPParameterSpec paramOAEPParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
/*     */     String str1, str2;
/*     */     byte[] arrayOfByte;
/*     */     int i;
/* 150 */     this.type = paramInt1;
/* 151 */     this.paddedSize = paramInt2;
/* 152 */     this.random = paramSecureRandom;
/* 153 */     if (paramInt2 < 64)
/*     */     {
/* 155 */       throw new InvalidKeyException("Padded size must be at least 64");
/*     */     }
/* 157 */     switch (paramInt1) {
/*     */       case 1:
/*     */       case 2:
/* 160 */         this.maxDataSize = paramInt2 - 11;
/*     */         return;
/*     */       case 3:
/* 163 */         this.maxDataSize = paramInt2;
/*     */         return;
/*     */       case 4:
/* 166 */         str1 = "SHA-1";
/* 167 */         str2 = "SHA-1";
/* 168 */         arrayOfByte = null;
/*     */         try {
/* 170 */           if (paramOAEPParameterSpec != null) {
/* 171 */             str1 = paramOAEPParameterSpec.getDigestAlgorithm();
/* 172 */             String str3 = paramOAEPParameterSpec.getMGFAlgorithm();
/* 173 */             if (!str3.equalsIgnoreCase("MGF1")) {
/* 174 */               throw new InvalidAlgorithmParameterException("Unsupported MGF algo: " + str3);
/*     */             }
/*     */ 
/*     */             
/* 178 */             str2 = ((MGF1ParameterSpec)paramOAEPParameterSpec.getMGFParameters()).getDigestAlgorithm();
/* 179 */             PSource pSource = paramOAEPParameterSpec.getPSource();
/* 180 */             String str4 = pSource.getAlgorithm();
/* 181 */             if (!str4.equalsIgnoreCase("PSpecified")) {
/* 182 */               throw new InvalidAlgorithmParameterException("Unsupported pSource algo: " + str4);
/*     */             }
/*     */             
/* 185 */             arrayOfByte = ((PSource.PSpecified)pSource).getValue();
/*     */           } 
/* 187 */           this.md = MessageDigest.getInstance(str1);
/* 188 */           this.mgfMd = MessageDigest.getInstance(str2);
/* 189 */         } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/* 190 */           throw new InvalidKeyException("Digest " + str1 + " not available", noSuchAlgorithmException);
/*     */         } 
/*     */         
/* 193 */         this.lHash = getInitialHash(this.md, arrayOfByte);
/* 194 */         i = this.lHash.length;
/* 195 */         this.maxDataSize = paramInt2 - 2 - 2 * i;
/* 196 */         if (this.maxDataSize <= 0) {
/* 197 */           throw new InvalidKeyException("Key is too short for encryption using OAEPPadding with " + str1 + " and MGF1" + str2);
/*     */         }
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 203 */     throw new InvalidKeyException("Invalid padding: " + paramInt1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 209 */   private static final Map<String, byte[]> emptyHashes = (Map)Collections.synchronizedMap(new HashMap<>());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] getInitialHash(MessageDigest paramMessageDigest, byte[] paramArrayOfbyte) {
/*     */     byte[] arrayOfByte;
/* 221 */     if (paramArrayOfbyte == null || paramArrayOfbyte.length == 0) {
/* 222 */       String str = paramMessageDigest.getAlgorithm();
/* 223 */       arrayOfByte = emptyHashes.get(str);
/* 224 */       if (arrayOfByte == null) {
/* 225 */         arrayOfByte = paramMessageDigest.digest();
/* 226 */         emptyHashes.put(str, arrayOfByte);
/*     */       } 
/*     */     } else {
/* 229 */       arrayOfByte = paramMessageDigest.digest(paramArrayOfbyte);
/*     */     } 
/* 231 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxDataSize() {
/* 239 */     return this.maxDataSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] pad(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws BadPaddingException {
/* 247 */     return pad(RSACore.convert(paramArrayOfbyte, paramInt1, paramInt2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] pad(byte[] paramArrayOfbyte) throws BadPaddingException {
/* 254 */     if (paramArrayOfbyte.length > this.maxDataSize) {
/* 255 */       throw new BadPaddingException("Data must be shorter than " + (this.maxDataSize + 1) + " bytes but received " + paramArrayOfbyte.length + " bytes.");
/*     */     }
/*     */ 
/*     */     
/* 259 */     switch (this.type) {
/*     */       case 3:
/* 261 */         return paramArrayOfbyte;
/*     */       case 1:
/*     */       case 2:
/* 264 */         return padV15(paramArrayOfbyte);
/*     */       case 4:
/* 266 */         return padOAEP(paramArrayOfbyte);
/*     */     } 
/* 268 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] unpad(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws BadPaddingException {
/* 277 */     return unpad(RSACore.convert(paramArrayOfbyte, paramInt1, paramInt2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] unpad(byte[] paramArrayOfbyte) throws BadPaddingException {
/* 284 */     if (paramArrayOfbyte.length != this.paddedSize) {
/* 285 */       throw new BadPaddingException("Decryption error.The padded array length (" + paramArrayOfbyte.length + ") is not the specified padded size (" + this.paddedSize + ")");
/*     */     }
/*     */ 
/*     */     
/* 289 */     switch (this.type) {
/*     */       case 3:
/* 291 */         return paramArrayOfbyte;
/*     */       case 1:
/*     */       case 2:
/* 294 */         return unpadV15(paramArrayOfbyte);
/*     */       case 4:
/* 296 */         return unpadOAEP(paramArrayOfbyte);
/*     */     } 
/* 298 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] padV15(byte[] paramArrayOfbyte) throws BadPaddingException {
/* 306 */     byte[] arrayOfByte = new byte[this.paddedSize];
/* 307 */     System.arraycopy(paramArrayOfbyte, 0, arrayOfByte, this.paddedSize - paramArrayOfbyte.length, paramArrayOfbyte.length);
/*     */     
/* 309 */     int i = this.paddedSize - 3 - paramArrayOfbyte.length;
/* 310 */     byte b = 0;
/* 311 */     arrayOfByte[b++] = 0;
/* 312 */     arrayOfByte[b++] = (byte)this.type;
/* 313 */     if (this.type == 1) {
/*     */       
/* 315 */       while (i-- > 0) {
/* 316 */         arrayOfByte[b++] = -1;
/*     */       }
/*     */     } else {
/*     */       
/* 320 */       if (this.random == null) {
/* 321 */         this.random = JCAUtil.getSecureRandom();
/*     */       }
/*     */ 
/*     */       
/* 325 */       byte[] arrayOfByte1 = new byte[64];
/* 326 */       int j = -1;
/* 327 */       while (i-- > 0) {
/*     */         
/*     */         while (true) {
/* 330 */           if (j < 0) {
/* 331 */             this.random.nextBytes(arrayOfByte1);
/* 332 */             j = arrayOfByte1.length - 1;
/*     */           } 
/* 334 */           int k = arrayOfByte1[j--] & 0xFF;
/* 335 */           if (k != 0)
/* 336 */             arrayOfByte[b++] = (byte)k; 
/*     */         } 
/*     */       } 
/* 339 */     }  return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] unpadV15(byte[] paramArrayOfbyte) throws BadPaddingException {
/* 348 */     byte b1 = 0;
/* 349 */     boolean bool = false;
/*     */     
/* 351 */     if (paramArrayOfbyte[b1++] != 0) {
/* 352 */       bool = true;
/*     */     }
/* 354 */     if (paramArrayOfbyte[b1++] != this.type) {
/* 355 */       bool = true;
/*     */     }
/* 357 */     byte b2 = 0;
/* 358 */     while (b1 < paramArrayOfbyte.length) {
/* 359 */       int j = paramArrayOfbyte[b1++] & 0xFF;
/* 360 */       if (j == 0 && !b2) {
/* 361 */         b2 = b1;
/*     */       }
/* 363 */       if (b1 == paramArrayOfbyte.length && b2 == 0) {
/* 364 */         bool = true;
/*     */       }
/* 366 */       if (this.type == 1 && j != 255 && b2 == 0)
/*     */       {
/* 368 */         bool = true;
/*     */       }
/*     */     } 
/* 371 */     int i = paramArrayOfbyte.length - b2;
/* 372 */     if (i > this.maxDataSize) {
/* 373 */       bool = true;
/*     */     }
/*     */ 
/*     */     
/* 377 */     byte[] arrayOfByte1 = new byte[b2];
/* 378 */     System.arraycopy(paramArrayOfbyte, 0, arrayOfByte1, 0, b2);
/*     */     
/* 380 */     byte[] arrayOfByte2 = new byte[i];
/* 381 */     System.arraycopy(paramArrayOfbyte, b2, arrayOfByte2, 0, i);
/*     */     
/* 383 */     BadPaddingException badPaddingException = new BadPaddingException("Decryption error");
/*     */     
/* 385 */     if (bool) {
/* 386 */       throw badPaddingException;
/*     */     }
/* 388 */     return arrayOfByte2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] padOAEP(byte[] paramArrayOfbyte) throws BadPaddingException {
/* 397 */     if (this.random == null) {
/* 398 */       this.random = JCAUtil.getSecureRandom();
/*     */     }
/* 400 */     int i = this.lHash.length;
/*     */ 
/*     */ 
/*     */     
/* 404 */     byte[] arrayOfByte1 = new byte[i];
/* 405 */     this.random.nextBytes(arrayOfByte1);
/*     */ 
/*     */     
/* 408 */     byte[] arrayOfByte2 = new byte[this.paddedSize];
/*     */ 
/*     */     
/* 411 */     boolean bool = true;
/* 412 */     int j = i;
/*     */ 
/*     */     
/* 415 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, bool, j);
/*     */ 
/*     */ 
/*     */     
/* 419 */     int k = i + 1;
/* 420 */     int m = arrayOfByte2.length - k;
/*     */ 
/*     */     
/* 423 */     int n = this.paddedSize - paramArrayOfbyte.length;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 430 */     System.arraycopy(this.lHash, 0, arrayOfByte2, k, i);
/* 431 */     arrayOfByte2[n - 1] = 1;
/* 432 */     System.arraycopy(paramArrayOfbyte, 0, arrayOfByte2, n, paramArrayOfbyte.length);
/*     */ 
/*     */     
/* 435 */     mgf1(arrayOfByte2, bool, j, arrayOfByte2, k, m);
/*     */ 
/*     */     
/* 438 */     mgf1(arrayOfByte2, k, m, arrayOfByte2, bool, j);
/*     */     
/* 440 */     return arrayOfByte2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] unpadOAEP(byte[] paramArrayOfbyte) throws BadPaddingException {
/* 447 */     byte[] arrayOfByte1 = paramArrayOfbyte;
/* 448 */     boolean bool1 = false;
/* 449 */     int i = this.lHash.length;
/*     */     
/* 451 */     if (arrayOfByte1[0] != 0) {
/* 452 */       bool1 = true;
/*     */     }
/*     */     
/* 455 */     boolean bool2 = true;
/* 456 */     int j = i;
/*     */     
/* 458 */     int k = i + 1;
/* 459 */     int m = arrayOfByte1.length - k;
/*     */     
/* 461 */     mgf1(arrayOfByte1, k, m, arrayOfByte1, bool2, j);
/* 462 */     mgf1(arrayOfByte1, bool2, j, arrayOfByte1, k, m);
/*     */     
/*     */     int n;
/* 465 */     for (n = 0; n < i; n++) {
/* 466 */       if (this.lHash[n] != arrayOfByte1[k + n]) {
/* 467 */         bool1 = true;
/*     */       }
/*     */     } 
/*     */     
/* 471 */     n = k + i;
/* 472 */     int i1 = -1;
/*     */     int i2;
/* 474 */     for (i2 = n; i2 < arrayOfByte1.length; i2++) {
/* 475 */       byte b = arrayOfByte1[i2];
/* 476 */       if (i1 == -1 && 
/* 477 */         b != 0)
/*     */       {
/* 479 */         if (b == 1) {
/* 480 */           i1 = i2;
/*     */         } else {
/* 482 */           bool1 = true;
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 488 */     if (i1 == -1) {
/* 489 */       bool1 = true;
/* 490 */       i1 = arrayOfByte1.length - 1;
/*     */     } 
/*     */     
/* 493 */     i2 = i1 + 1;
/*     */ 
/*     */     
/* 496 */     byte[] arrayOfByte2 = new byte[i2 - n];
/* 497 */     System.arraycopy(arrayOfByte1, n, arrayOfByte2, 0, arrayOfByte2.length);
/*     */     
/* 499 */     byte[] arrayOfByte3 = new byte[arrayOfByte1.length - i2];
/* 500 */     System.arraycopy(arrayOfByte1, i2, arrayOfByte3, 0, arrayOfByte3.length);
/*     */     
/* 502 */     BadPaddingException badPaddingException = new BadPaddingException("Decryption error");
/*     */     
/* 504 */     if (bool1) {
/* 505 */       throw badPaddingException;
/*     */     }
/* 507 */     return arrayOfByte3;
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
/*     */   private void mgf1(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4) throws BadPaddingException {
/* 521 */     byte[] arrayOfByte1 = new byte[4];
/* 522 */     byte[] arrayOfByte2 = new byte[this.mgfMd.getDigestLength()];
/* 523 */     while (paramInt4 > 0) {
/* 524 */       this.mgfMd.update(paramArrayOfbyte1, paramInt1, paramInt2);
/* 525 */       this.mgfMd.update(arrayOfByte1);
/*     */       try {
/* 527 */         this.mgfMd.digest(arrayOfByte2, 0, arrayOfByte2.length);
/* 528 */       } catch (DigestException digestException) {
/*     */         
/* 530 */         throw new BadPaddingException(digestException.toString());
/*     */       }  int i;
/* 532 */       for (i = 0; i < arrayOfByte2.length && paramInt4 > 0; paramInt4--) {
/* 533 */         paramArrayOfbyte2[paramInt3++] = (byte)(paramArrayOfbyte2[paramInt3++] ^ arrayOfByte2[i++]);
/*     */       }
/* 535 */       if (paramInt4 > 0)
/*     */       {
/* 537 */         for (i = arrayOfByte1.length - 1, arrayOfByte1[i] = (byte)(arrayOfByte1[i] + 1); (byte)(arrayOfByte1[i] + 1) == 0 && i > 0; i--);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/rsa/RSAPadding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */