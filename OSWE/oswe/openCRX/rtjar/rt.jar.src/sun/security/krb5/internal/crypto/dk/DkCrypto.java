/*     */ package sun.security.krb5.internal.crypto.dk;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.Cipher;
/*     */ import sun.misc.HexDumpEncoder;
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
/*     */ public abstract class DkCrypto
/*     */ {
/*     */   protected static final boolean debug = false;
/*  61 */   static final byte[] KERBEROS_CONSTANT = new byte[] { 107, 101, 114, 98, 101, 114, 111, 115 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int getKeySeedLength();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract byte[] randomToKey(byte[] paramArrayOfbyte);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Cipher getCipher(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) throws GeneralSecurityException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getChecksumLength();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract byte[] getHmac(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws GeneralSecurityException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, int paramInt2, int paramInt3) throws GeneralSecurityException, KrbCryptoException {
/*  98 */     if (!KeyUsage.isValid(paramInt1)) {
/*  99 */       throw new GeneralSecurityException("Invalid key usage number: " + paramInt1);
/*     */     }
/*     */ 
/*     */     
/* 103 */     byte[] arrayOfByte1 = null;
/* 104 */     byte[] arrayOfByte2 = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 109 */       byte[] arrayOfByte3 = new byte[5];
/* 110 */       arrayOfByte3[0] = (byte)(paramInt1 >> 24 & 0xFF);
/* 111 */       arrayOfByte3[1] = (byte)(paramInt1 >> 16 & 0xFF);
/* 112 */       arrayOfByte3[2] = (byte)(paramInt1 >> 8 & 0xFF);
/* 113 */       arrayOfByte3[3] = (byte)(paramInt1 & 0xFF);
/*     */       
/* 115 */       arrayOfByte3[4] = -86;
/*     */       
/* 117 */       arrayOfByte1 = dk(paramArrayOfbyte1, arrayOfByte3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 131 */       Cipher cipher = getCipher(arrayOfByte1, paramArrayOfbyte2, 1);
/* 132 */       int i = cipher.getBlockSize();
/* 133 */       byte[] arrayOfByte4 = Confounder.bytes(i);
/*     */       
/* 135 */       int j = roundup(arrayOfByte4.length + paramInt3, i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 144 */       byte[] arrayOfByte5 = new byte[j];
/* 145 */       System.arraycopy(arrayOfByte4, 0, arrayOfByte5, 0, arrayOfByte4.length);
/*     */       
/* 147 */       System.arraycopy(paramArrayOfbyte4, paramInt2, arrayOfByte5, arrayOfByte4.length, paramInt3);
/*     */ 
/*     */ 
/*     */       
/* 151 */       Arrays.fill(arrayOfByte5, arrayOfByte4.length + paramInt3, j, (byte)0);
/*     */ 
/*     */       
/* 154 */       int k = cipher.getOutputSize(j);
/* 155 */       int m = k + getChecksumLength();
/*     */       
/* 157 */       byte[] arrayOfByte6 = new byte[m];
/*     */       
/* 159 */       cipher.doFinal(arrayOfByte5, 0, j, arrayOfByte6, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 164 */       if (paramArrayOfbyte3 != null && paramArrayOfbyte3.length == i) {
/* 165 */         System.arraycopy(arrayOfByte6, k - i, paramArrayOfbyte3, 0, i);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 173 */       arrayOfByte3[4] = 85;
/* 174 */       arrayOfByte2 = dk(paramArrayOfbyte1, arrayOfByte3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 182 */       byte[] arrayOfByte7 = getHmac(arrayOfByte2, arrayOfByte5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 191 */       System.arraycopy(arrayOfByte7, 0, arrayOfByte6, k, 
/* 192 */           getChecksumLength());
/* 193 */       return arrayOfByte6;
/*     */     } finally {
/* 195 */       if (arrayOfByte1 != null) {
/* 196 */         Arrays.fill(arrayOfByte1, 0, arrayOfByte1.length, (byte)0);
/*     */       }
/* 198 */       if (arrayOfByte2 != null) {
/* 199 */         Arrays.fill(arrayOfByte2, 0, arrayOfByte2.length, (byte)0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 224 */     Cipher cipher = getCipher(paramArrayOfbyte1, paramArrayOfbyte2, 1);
/* 225 */     int i = cipher.getBlockSize();
/*     */     
/* 227 */     if (paramInt3 % i != 0) {
/* 228 */       throw new GeneralSecurityException("length of data to be encrypted (" + paramInt3 + ") is not a multiple of the blocksize (" + i + ")");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 233 */     int j = cipher.getOutputSize(paramInt3);
/* 234 */     byte[] arrayOfByte = new byte[j];
/*     */     
/* 236 */     cipher.doFinal(paramArrayOfbyte3, 0, paramInt3, arrayOfByte, 0);
/* 237 */     return arrayOfByte;
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
/*     */   
/*     */   public byte[] decryptRaw(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException {
/* 259 */     Cipher cipher = getCipher(paramArrayOfbyte1, paramArrayOfbyte2, 2);
/*     */     
/* 261 */     int i = cipher.getBlockSize();
/*     */     
/* 263 */     if (paramInt3 % i != 0) {
/* 264 */       throw new GeneralSecurityException("length of data to be decrypted (" + paramInt3 + ") is not a multiple of the blocksize (" + i + ")");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 269 */     return cipher.doFinal(paramArrayOfbyte3, paramInt2, paramInt3);
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
/*     */   public byte[] decrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException {
/* 286 */     if (!KeyUsage.isValid(paramInt1)) {
/* 287 */       throw new GeneralSecurityException("Invalid key usage number: " + paramInt1);
/*     */     }
/*     */ 
/*     */     
/* 291 */     byte[] arrayOfByte1 = null;
/* 292 */     byte[] arrayOfByte2 = null;
/*     */ 
/*     */     
/*     */     try {
/* 296 */       byte[] arrayOfByte3 = new byte[5];
/* 297 */       arrayOfByte3[0] = (byte)(paramInt1 >> 24 & 0xFF);
/* 298 */       arrayOfByte3[1] = (byte)(paramInt1 >> 16 & 0xFF);
/* 299 */       arrayOfByte3[2] = (byte)(paramInt1 >> 8 & 0xFF);
/* 300 */       arrayOfByte3[3] = (byte)(paramInt1 & 0xFF);
/*     */       
/* 302 */       arrayOfByte3[4] = -86;
/*     */       
/* 304 */       arrayOfByte1 = dk(paramArrayOfbyte1, arrayOfByte3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 317 */       Cipher cipher = getCipher(arrayOfByte1, paramArrayOfbyte2, 2);
/* 318 */       int i = cipher.getBlockSize();
/*     */ 
/*     */       
/* 321 */       int j = getChecksumLength();
/* 322 */       int k = paramInt3 - j;
/* 323 */       byte[] arrayOfByte4 = cipher.doFinal(paramArrayOfbyte3, paramInt2, k);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 333 */       arrayOfByte3[4] = 85;
/* 334 */       arrayOfByte2 = dk(paramArrayOfbyte1, arrayOfByte3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 342 */       byte[] arrayOfByte5 = getHmac(arrayOfByte2, arrayOfByte4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 351 */       boolean bool = false;
/* 352 */       if (arrayOfByte5.length >= j) {
/* 353 */         for (byte b = 0; b < j; b++) {
/* 354 */           if (arrayOfByte5[b] != paramArrayOfbyte3[k + b]) {
/* 355 */             bool = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 361 */       if (bool) {
/* 362 */         throw new GeneralSecurityException("Checksum failed");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 367 */       if (paramArrayOfbyte2 != null && paramArrayOfbyte2.length == i) {
/* 368 */         System.arraycopy(paramArrayOfbyte3, paramInt2 + k - i, paramArrayOfbyte2, 0, i);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 377 */       byte[] arrayOfByte6 = new byte[arrayOfByte4.length - i];
/* 378 */       System.arraycopy(arrayOfByte4, i, arrayOfByte6, 0, arrayOfByte6.length);
/*     */       
/* 380 */       return arrayOfByte6;
/*     */     } finally {
/* 382 */       if (arrayOfByte1 != null) {
/* 383 */         Arrays.fill(arrayOfByte1, 0, arrayOfByte1.length, (byte)0);
/*     */       }
/* 385 */       if (arrayOfByte2 != null) {
/* 386 */         Arrays.fill(arrayOfByte2, 0, arrayOfByte2.length, (byte)0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   int roundup(int paramInt1, int paramInt2) {
/* 393 */     return (paramInt1 + paramInt2 - 1) / paramInt2 * paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] calculateChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3) throws GeneralSecurityException {
/* 399 */     if (!KeyUsage.isValid(paramInt1)) {
/* 400 */       throw new GeneralSecurityException("Invalid key usage number: " + paramInt1);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 405 */     byte[] arrayOfByte1 = new byte[5];
/* 406 */     arrayOfByte1[0] = (byte)(paramInt1 >> 24 & 0xFF);
/* 407 */     arrayOfByte1[1] = (byte)(paramInt1 >> 16 & 0xFF);
/* 408 */     arrayOfByte1[2] = (byte)(paramInt1 >> 8 & 0xFF);
/* 409 */     arrayOfByte1[3] = (byte)(paramInt1 & 0xFF);
/*     */     
/* 411 */     arrayOfByte1[4] = -103;
/*     */     
/* 413 */     byte[] arrayOfByte2 = dk(paramArrayOfbyte1, arrayOfByte1);
/*     */ 
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
/* 425 */       byte[] arrayOfByte = getHmac(arrayOfByte2, paramArrayOfbyte2);
/*     */ 
/*     */ 
/*     */       
/* 429 */       if (arrayOfByte.length == getChecksumLength())
/* 430 */         return arrayOfByte; 
/* 431 */       if (arrayOfByte.length > getChecksumLength()) {
/* 432 */         byte[] arrayOfByte3 = new byte[getChecksumLength()];
/* 433 */         System.arraycopy(arrayOfByte, 0, arrayOfByte3, 0, arrayOfByte3.length);
/* 434 */         return arrayOfByte3;
/*     */       } 
/* 436 */       throw new GeneralSecurityException("checksum size too short: " + arrayOfByte.length + "; expecting : " + 
/* 437 */           getChecksumLength());
/*     */     } finally {
/*     */       
/* 440 */       Arrays.fill(arrayOfByte2, 0, arrayOfByte2.length, (byte)0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] dk(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws GeneralSecurityException {
/* 447 */     return randomToKey(dr(paramArrayOfbyte1, paramArrayOfbyte2));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] dr(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws GeneralSecurityException {
/* 484 */     Cipher cipher = getCipher(paramArrayOfbyte1, null, 1);
/* 485 */     int i = cipher.getBlockSize();
/*     */     
/* 487 */     if (paramArrayOfbyte2.length != i) {
/* 488 */       paramArrayOfbyte2 = nfold(paramArrayOfbyte2, i * 8);
/*     */     }
/* 490 */     byte[] arrayOfByte1 = paramArrayOfbyte2;
/*     */     
/* 492 */     int j = getKeySeedLength() >> 3;
/* 493 */     byte[] arrayOfByte2 = new byte[j];
/* 494 */     boolean bool = false;
/*     */ 
/*     */     
/* 497 */     int k = 0;
/* 498 */     while (k < j) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 504 */       byte[] arrayOfByte = cipher.doFinal(arrayOfByte1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 510 */       int m = (j - k <= arrayOfByte.length) ? (j - k) : arrayOfByte.length;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 515 */       System.arraycopy(arrayOfByte, 0, arrayOfByte2, k, m);
/* 516 */       k += m;
/* 517 */       arrayOfByte1 = arrayOfByte;
/*     */     } 
/* 519 */     return arrayOfByte2;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] nfold(byte[] paramArrayOfbyte, int paramInt) {
/* 544 */     int i = paramArrayOfbyte.length;
/* 545 */     paramInt >>= 3;
/*     */ 
/*     */ 
/*     */     
/* 549 */     int j = paramInt;
/* 550 */     int k = i;
/*     */     
/* 552 */     while (k != 0) {
/* 553 */       int i2 = k;
/* 554 */       k = j % k;
/* 555 */       j = i2;
/*     */     } 
/* 557 */     int m = paramInt * i / j;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 566 */     byte[] arrayOfByte = new byte[paramInt];
/* 567 */     Arrays.fill(arrayOfByte, (byte)0);
/*     */     
/* 569 */     int n = 0;
/*     */ 
/*     */     
/*     */     int i1;
/*     */     
/* 574 */     for (i1 = m - 1; i1 >= 0; i1--) {
/*     */       
/* 576 */       int i2 = ((i << 3) - 1 + ((i << 3) + 13) * i1 / i + (i - i1 % i << 3)) % (i << 3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 586 */       int i3 = ((paramArrayOfbyte[(i - 1 - (i2 >>> 3)) % i] & 0xFF) << 8 | paramArrayOfbyte[(i - (i2 >>> 3)) % i] & 0xFF) >>> (i2 & 0x7) + 1 & 0xFF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 597 */       n += i3;
/*     */ 
/*     */ 
/*     */       
/* 601 */       int i4 = arrayOfByte[i1 % paramInt] & 0xFF;
/* 602 */       n += i4;
/* 603 */       arrayOfByte[i1 % paramInt] = (byte)(n & 0xFF);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 614 */       n >>>= 8;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 622 */     if (n != 0) {
/* 623 */       for (i1 = paramInt - 1; i1 >= 0; i1--) {
/*     */         
/* 625 */         n += arrayOfByte[i1] & 0xFF;
/* 626 */         arrayOfByte[i1] = (byte)(n & 0xFF);
/*     */ 
/*     */         
/* 629 */         n >>>= 8;
/*     */       } 
/*     */     }
/*     */     
/* 633 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static String bytesToString(byte[] paramArrayOfbyte) {
/* 639 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 641 */     for (byte b = 0; b < paramArrayOfbyte.length; b++) {
/* 642 */       if ((paramArrayOfbyte[b] & 0xFF) < 16) {
/* 643 */         stringBuffer.append("0" + 
/* 644 */             Integer.toHexString(paramArrayOfbyte[b] & 0xFF));
/*     */       } else {
/* 646 */         stringBuffer.append(
/* 647 */             Integer.toHexString(paramArrayOfbyte[b] & 0xFF));
/*     */       } 
/*     */     } 
/* 650 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   private static byte[] binaryStringToBytes(String paramString) {
/* 654 */     char[] arrayOfChar = paramString.toCharArray();
/* 655 */     byte[] arrayOfByte = new byte[arrayOfChar.length / 2];
/* 656 */     for (byte b = 0; b < arrayOfByte.length; b++) {
/* 657 */       byte b1 = Byte.parseByte(new String(arrayOfChar, b * 2, 1), 16);
/* 658 */       byte b2 = Byte.parseByte(new String(arrayOfChar, b * 2 + 1, 1), 16);
/* 659 */       arrayOfByte[b] = (byte)(b1 << 4 | b2);
/*     */     } 
/* 661 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   
/*     */   static void traceOutput(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*     */     try {
/* 667 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(paramInt2);
/* 668 */       (new HexDumpEncoder()).encodeBuffer(new ByteArrayInputStream(paramArrayOfbyte, paramInt1, paramInt2), byteArrayOutputStream);
/*     */ 
/*     */       
/* 671 */       System.err.println(paramString + ":" + byteArrayOutputStream.toString());
/* 672 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] charToUtf8(char[] paramArrayOfchar) {
/* 679 */     Charset charset = Charset.forName("UTF-8");
/*     */     
/* 681 */     CharBuffer charBuffer = CharBuffer.wrap(paramArrayOfchar);
/* 682 */     ByteBuffer byteBuffer = charset.encode(charBuffer);
/* 683 */     int i = byteBuffer.limit();
/* 684 */     byte[] arrayOfByte = new byte[i];
/* 685 */     byteBuffer.get(arrayOfByte, 0, i);
/* 686 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   static byte[] charToUtf16(char[] paramArrayOfchar) {
/* 690 */     Charset charset = Charset.forName("UTF-16LE");
/*     */     
/* 692 */     CharBuffer charBuffer = CharBuffer.wrap(paramArrayOfchar);
/* 693 */     ByteBuffer byteBuffer = charset.encode(charBuffer);
/* 694 */     int i = byteBuffer.limit();
/* 695 */     byte[] arrayOfByte = new byte[i];
/* 696 */     byteBuffer.get(arrayOfByte, 0, i);
/* 697 */     return arrayOfByte;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/dk/DkCrypto.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */