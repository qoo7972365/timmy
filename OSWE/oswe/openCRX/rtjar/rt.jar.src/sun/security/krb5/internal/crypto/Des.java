/*     */ package sun.security.krb5.internal.crypto;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKeyFactory;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.security.krb5.KrbCryptoException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Des
/*     */ {
/*  57 */   private static final String CHARSET = AccessController.<String>doPrivileged(new GetPropertyAction("sun.security.krb5.msinterop.des.s2kcharset"));
/*     */ 
/*     */   
/*  60 */   private static final long[] bad_keys = new long[] { 72340172838076673L, -72340172838076674L, 2242545357980376863L, -2242545357980376864L, 143554428589179390L, -143554428589179391L, 2296870857142767345L, -2296870857142767346L, 135110050437988849L, -2305315235293957887L, 2305315235293957886L, -135110050437988850L, 80784550989267214L, 2234100979542855169L, -2234100979542855170L, -80784550989267215L };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private static final byte[] good_parity = new byte[] { 1, 1, 2, 2, 4, 4, 7, 7, 8, 8, 11, 11, 13, 13, 14, 14, 16, 16, 19, 19, 21, 21, 22, 22, 25, 25, 26, 26, 28, 28, 31, 31, 32, 32, 35, 35, 37, 37, 38, 38, 41, 41, 42, 42, 44, 44, 47, 47, 49, 49, 50, 50, 52, 52, 55, 55, 56, 56, 59, 59, 61, 61, 62, 62, 64, 64, 67, 67, 69, 69, 70, 70, 73, 73, 74, 74, 76, 76, 79, 79, 81, 81, 82, 82, 84, 84, 87, 87, 88, 88, 91, 91, 93, 93, 94, 94, 97, 97, 98, 98, 100, 100, 103, 103, 104, 104, 107, 107, 109, 109, 110, 110, 112, 112, 115, 115, 117, 117, 118, 118, 121, 121, 122, 122, 124, 124, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MIN_VALUE, Byte.MIN_VALUE, -125, -125, -123, -123, -122, -122, -119, -119, -118, -118, -116, -116, -113, -113, -111, -111, -110, -110, -108, -108, -105, -105, -104, -104, -101, -101, -99, -99, -98, -98, -95, -95, -94, -94, -92, -92, -89, -89, -88, -88, -85, -85, -83, -83, -82, -82, -80, -80, -77, -77, -75, -75, -74, -74, -71, -71, -70, -70, -68, -68, -65, -65, -63, -63, -62, -62, -60, -60, -57, -57, -56, -56, -53, -53, -51, -51, -50, -50, -48, -48, -45, -45, -43, -43, -42, -42, -39, -39, -38, -38, -36, -36, -33, -33, -32, -32, -29, -29, -27, -27, -26, -26, -23, -23, -22, -22, -20, -20, -17, -17, -15, -15, -14, -14, -12, -12, -9, -9, -8, -8, -5, -5, -3, -3, -2, -2 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final byte[] set_parity(byte[] paramArrayOfbyte) {
/* 123 */     for (byte b = 0; b < 8; b++) {
/* 124 */       paramArrayOfbyte[b] = good_parity[paramArrayOfbyte[b] & 0xFF];
/*     */     }
/* 126 */     return paramArrayOfbyte;
/*     */   }
/*     */   
/*     */   public static final long set_parity(long paramLong) {
/* 130 */     return octet2long(set_parity(long2octet(paramLong)));
/*     */   }
/*     */   
/*     */   public static final boolean bad_key(long paramLong) {
/* 134 */     for (byte b = 0; b < bad_keys.length; b++) {
/* 135 */       if (bad_keys[b] == paramLong) {
/* 136 */         return true;
/*     */       }
/*     */     } 
/* 139 */     return false;
/*     */   }
/*     */   
/*     */   public static final boolean bad_key(byte[] paramArrayOfbyte) {
/* 143 */     return bad_key(octet2long(paramArrayOfbyte));
/*     */   }
/*     */   
/*     */   public static long octet2long(byte[] paramArrayOfbyte) {
/* 147 */     return octet2long(paramArrayOfbyte, 0);
/*     */   }
/*     */   
/*     */   public static long octet2long(byte[] paramArrayOfbyte, int paramInt) {
/* 151 */     long l = 0L;
/* 152 */     for (byte b = 0; b < 8; b++) {
/* 153 */       if (b + paramInt < paramArrayOfbyte.length) {
/* 154 */         l |= (paramArrayOfbyte[b + paramInt] & 0xFFL) << (7 - b) * 8;
/*     */       }
/*     */     } 
/* 157 */     return l;
/*     */   }
/*     */   
/*     */   public static byte[] long2octet(long paramLong) {
/* 161 */     byte[] arrayOfByte = new byte[8];
/* 162 */     for (byte b = 0; b < 8; b++) {
/* 163 */       arrayOfByte[b] = (byte)(int)(paramLong >>> (7 - b) * 8 & 0xFFL);
/*     */     }
/* 165 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public static void long2octet(long paramLong, byte[] paramArrayOfbyte) {
/* 169 */     long2octet(paramLong, paramArrayOfbyte, 0);
/*     */   }
/*     */   
/*     */   public static void long2octet(long paramLong, byte[] paramArrayOfbyte, int paramInt) {
/* 173 */     for (byte b = 0; b < 8; b++) {
/* 174 */       if (b + paramInt < paramArrayOfbyte.length) {
/* 175 */         paramArrayOfbyte[b + paramInt] = (byte)(int)(paramLong >>> (7 - b) * 8 & 0xFFL);
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
/*     */   public static void cbc_encrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, boolean paramBoolean) throws KrbCryptoException {
/* 197 */     Cipher cipher = null;
/*     */     
/*     */     try {
/* 200 */       cipher = Cipher.getInstance("DES/CBC/NoPadding");
/* 201 */     } catch (GeneralSecurityException generalSecurityException) {
/*     */       
/* 203 */       KrbCryptoException krbCryptoException = new KrbCryptoException("JCE provider may not be installed. " + generalSecurityException.getMessage());
/* 204 */       krbCryptoException.initCause(generalSecurityException);
/* 205 */       throw krbCryptoException;
/*     */     } 
/* 207 */     IvParameterSpec ivParameterSpec = new IvParameterSpec(paramArrayOfbyte4);
/* 208 */     SecretKeySpec secretKeySpec = new SecretKeySpec(paramArrayOfbyte3, "DES");
/*     */     try {
/* 210 */       SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
/*     */       
/* 212 */       SecretKeySpec secretKeySpec1 = secretKeySpec;
/* 213 */       if (paramBoolean) {
/* 214 */         cipher.init(1, secretKeySpec1, ivParameterSpec);
/*     */       } else {
/* 216 */         cipher.init(2, secretKeySpec1, ivParameterSpec);
/*     */       } 
/* 218 */       byte[] arrayOfByte = cipher.doFinal(paramArrayOfbyte1);
/* 219 */       System.arraycopy(arrayOfByte, 0, paramArrayOfbyte2, 0, arrayOfByte.length);
/* 220 */     } catch (GeneralSecurityException generalSecurityException) {
/* 221 */       KrbCryptoException krbCryptoException = new KrbCryptoException(generalSecurityException.getMessage());
/* 222 */       krbCryptoException.initCause(generalSecurityException);
/* 223 */       throw krbCryptoException;
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
/*     */   public static long char_to_key(char[] paramArrayOfchar) throws KrbCryptoException {
/* 235 */     long l1 = 0L;
/* 236 */     long l2 = 0L;
/* 237 */     byte[] arrayOfByte1 = null;
/*     */ 
/*     */     
/*     */     try {
/* 241 */       if (CHARSET == null) {
/* 242 */         arrayOfByte1 = (new String(paramArrayOfchar)).getBytes();
/*     */       } else {
/* 244 */         arrayOfByte1 = (new String(paramArrayOfchar)).getBytes(CHARSET);
/*     */       } 
/* 246 */     } catch (Exception exception) {
/*     */       
/* 248 */       if (arrayOfByte1 != null) {
/* 249 */         Arrays.fill(arrayOfByte1, 0, arrayOfByte1.length, (byte)0);
/*     */       }
/* 251 */       KrbCryptoException krbCryptoException = new KrbCryptoException("Unable to convert passwd, " + exception);
/*     */       
/* 253 */       krbCryptoException.initCause(exception);
/* 254 */       throw krbCryptoException;
/*     */     } 
/*     */ 
/*     */     
/* 258 */     byte[] arrayOfByte2 = pad(arrayOfByte1);
/*     */     
/* 260 */     byte[] arrayOfByte3 = new byte[8];
/* 261 */     int i = arrayOfByte2.length / 8 + ((arrayOfByte2.length % 8 == 0) ? 0 : 1);
/* 262 */     for (byte b = 0; b < i; b++) {
/* 263 */       long l = octet2long(arrayOfByte2, b * 8) & 0x7F7F7F7F7F7F7F7FL;
/* 264 */       if (b % 2 == 1) {
/* 265 */         long l3 = 0L;
/* 266 */         for (byte b1 = 0; b1 < 64; b1++) {
/* 267 */           l3 |= (l & 1L << b1) >>> b1 << 63 - b1;
/*     */         }
/* 269 */         l = l3 >>> 1L;
/*     */       } 
/* 271 */       l1 ^= l << 1L;
/*     */     } 
/* 273 */     l1 = set_parity(l1);
/* 274 */     if (bad_key(l1)) {
/* 275 */       byte[] arrayOfByte = long2octet(l1);
/* 276 */       arrayOfByte[7] = (byte)(arrayOfByte[7] ^ 0xF0);
/* 277 */       l1 = octet2long(arrayOfByte);
/*     */     } 
/*     */     
/* 280 */     arrayOfByte3 = des_cksum(long2octet(l1), arrayOfByte2, long2octet(l1));
/* 281 */     l1 = octet2long(set_parity(arrayOfByte3));
/* 282 */     if (bad_key(l1)) {
/* 283 */       byte[] arrayOfByte = long2octet(l1);
/* 284 */       arrayOfByte[7] = (byte)(arrayOfByte[7] ^ 0xF0);
/* 285 */       l1 = octet2long(arrayOfByte);
/*     */     } 
/*     */ 
/*     */     
/* 289 */     if (arrayOfByte1 != null) {
/* 290 */       Arrays.fill(arrayOfByte1, 0, arrayOfByte1.length, (byte)0);
/*     */     }
/* 292 */     if (arrayOfByte2 != null) {
/* 293 */       Arrays.fill(arrayOfByte2, 0, arrayOfByte2.length, (byte)0);
/*     */     }
/*     */     
/* 296 */     return l1;
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
/*     */   public static byte[] des_cksum(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) throws KrbCryptoException {
/* 310 */     Cipher cipher = null;
/*     */     
/* 312 */     byte[] arrayOfByte = new byte[8];
/*     */     try {
/* 314 */       cipher = Cipher.getInstance("DES/CBC/NoPadding");
/* 315 */     } catch (Exception exception) {
/*     */       
/* 317 */       KrbCryptoException krbCryptoException = new KrbCryptoException("JCE provider may not be installed. " + exception.getMessage());
/* 318 */       krbCryptoException.initCause(exception);
/* 319 */       throw krbCryptoException;
/*     */     } 
/* 321 */     IvParameterSpec ivParameterSpec = new IvParameterSpec(paramArrayOfbyte1);
/* 322 */     SecretKeySpec secretKeySpec = new SecretKeySpec(paramArrayOfbyte3, "DES");
/*     */     try {
/* 324 */       SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
/*     */       
/* 326 */       SecretKeySpec secretKeySpec1 = secretKeySpec;
/* 327 */       cipher.init(1, secretKeySpec1, ivParameterSpec);
/* 328 */       for (byte b = 0; b < paramArrayOfbyte2.length / 8; b++) {
/* 329 */         arrayOfByte = cipher.doFinal(paramArrayOfbyte2, b * 8, 8);
/* 330 */         cipher.init(1, secretKeySpec1, new IvParameterSpec(arrayOfByte));
/*     */       }
/*     */     
/* 333 */     } catch (GeneralSecurityException generalSecurityException) {
/* 334 */       KrbCryptoException krbCryptoException = new KrbCryptoException(generalSecurityException.getMessage());
/* 335 */       krbCryptoException.initCause(generalSecurityException);
/* 336 */       throw krbCryptoException;
/*     */     } 
/* 338 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] pad(byte[] paramArrayOfbyte) {
/*     */     int i;
/* 350 */     if (paramArrayOfbyte.length < 8) { i = paramArrayOfbyte.length; }
/* 351 */     else { i = paramArrayOfbyte.length % 8; }
/* 352 */      if (i == 0) return paramArrayOfbyte;
/*     */     
/* 354 */     byte[] arrayOfByte = new byte[8 - i + paramArrayOfbyte.length];
/* 355 */     for (int j = arrayOfByte.length - 1; j > paramArrayOfbyte.length - 1; j--) {
/* 356 */       arrayOfByte[j] = 0;
/*     */     }
/* 358 */     System.arraycopy(paramArrayOfbyte, 0, arrayOfByte, 0, paramArrayOfbyte.length);
/* 359 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] string_to_key_bytes(char[] paramArrayOfchar) throws KrbCryptoException {
/* 366 */     return long2octet(char_to_key(paramArrayOfchar));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/Des.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */