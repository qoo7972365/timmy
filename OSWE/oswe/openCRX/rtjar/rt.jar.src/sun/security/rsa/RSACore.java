/*     */ package sun.security.rsa;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.interfaces.RSAKey;
/*     */ import java.security.interfaces.RSAPrivateCrtKey;
/*     */ import java.security.interfaces.RSAPrivateKey;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import javax.crypto.BadPaddingException;
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
/*     */ public final class RSACore
/*     */ {
/*     */   private static final boolean ENABLE_BLINDING = true;
/*  60 */   private static final Map<BigInteger, BlindingParameters> blindingCache = new WeakHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getByteLength(BigInteger paramBigInteger) {
/*  72 */     int i = paramBigInteger.bitLength();
/*  73 */     return i + 7 >> 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getByteLength(RSAKey paramRSAKey) {
/*  81 */     return getByteLength(paramRSAKey.getModulus());
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] convert(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  86 */     if (paramInt1 == 0 && paramInt2 == paramArrayOfbyte.length) {
/*  87 */       return paramArrayOfbyte;
/*     */     }
/*  89 */     byte[] arrayOfByte = new byte[paramInt2];
/*  90 */     System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, 0, paramInt2);
/*  91 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] rsa(byte[] paramArrayOfbyte, RSAPublicKey paramRSAPublicKey) throws BadPaddingException {
/* 100 */     return crypt(paramArrayOfbyte, paramRSAPublicKey.getModulus(), paramRSAPublicKey.getPublicExponent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static byte[] rsa(byte[] paramArrayOfbyte, RSAPrivateKey paramRSAPrivateKey) throws BadPaddingException {
/* 111 */     return rsa(paramArrayOfbyte, paramRSAPrivateKey, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] rsa(byte[] paramArrayOfbyte, RSAPrivateKey paramRSAPrivateKey, boolean paramBoolean) throws BadPaddingException {
/* 121 */     if (paramRSAPrivateKey instanceof RSAPrivateCrtKey) {
/* 122 */       return crtCrypt(paramArrayOfbyte, (RSAPrivateCrtKey)paramRSAPrivateKey, paramBoolean);
/*     */     }
/* 124 */     return priCrypt(paramArrayOfbyte, paramRSAPrivateKey.getModulus(), paramRSAPrivateKey.getPrivateExponent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] crypt(byte[] paramArrayOfbyte, BigInteger paramBigInteger1, BigInteger paramBigInteger2) throws BadPaddingException {
/* 133 */     BigInteger bigInteger1 = parseMsg(paramArrayOfbyte, paramBigInteger1);
/* 134 */     BigInteger bigInteger2 = bigInteger1.modPow(paramBigInteger2, paramBigInteger1);
/* 135 */     return toByteArray(bigInteger2, getByteLength(paramBigInteger1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] priCrypt(byte[] paramArrayOfbyte, BigInteger paramBigInteger1, BigInteger paramBigInteger2) throws BadPaddingException {
/* 144 */     BigInteger bigInteger1 = parseMsg(paramArrayOfbyte, paramBigInteger1);
/* 145 */     BlindingRandomPair blindingRandomPair = null;
/*     */ 
/*     */     
/* 148 */     blindingRandomPair = getBlindingRandomPair(null, paramBigInteger2, paramBigInteger1);
/* 149 */     bigInteger1 = bigInteger1.multiply(blindingRandomPair.u).mod(paramBigInteger1);
/* 150 */     BigInteger bigInteger2 = bigInteger1.modPow(paramBigInteger2, paramBigInteger1);
/* 151 */     bigInteger2 = bigInteger2.multiply(blindingRandomPair.v).mod(paramBigInteger1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     return toByteArray(bigInteger2, getByteLength(paramBigInteger1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] crtCrypt(byte[] paramArrayOfbyte, RSAPrivateCrtKey paramRSAPrivateCrtKey, boolean paramBoolean) throws BadPaddingException {
/* 165 */     BigInteger bigInteger1 = paramRSAPrivateCrtKey.getModulus();
/* 166 */     BigInteger bigInteger2 = parseMsg(paramArrayOfbyte, bigInteger1);
/* 167 */     BigInteger bigInteger3 = bigInteger2;
/* 168 */     BigInteger bigInteger4 = paramRSAPrivateCrtKey.getPrimeP();
/* 169 */     BigInteger bigInteger5 = paramRSAPrivateCrtKey.getPrimeQ();
/* 170 */     BigInteger bigInteger6 = paramRSAPrivateCrtKey.getPrimeExponentP();
/* 171 */     BigInteger bigInteger7 = paramRSAPrivateCrtKey.getPrimeExponentQ();
/* 172 */     BigInteger bigInteger8 = paramRSAPrivateCrtKey.getCrtCoefficient();
/* 173 */     BigInteger bigInteger9 = paramRSAPrivateCrtKey.getPublicExponent();
/* 174 */     BigInteger bigInteger10 = paramRSAPrivateCrtKey.getPrivateExponent();
/*     */ 
/*     */ 
/*     */     
/* 178 */     BlindingRandomPair blindingRandomPair = getBlindingRandomPair(bigInteger9, bigInteger10, bigInteger1);
/* 179 */     bigInteger3 = bigInteger3.multiply(blindingRandomPair.u).mod(bigInteger1);
/*     */ 
/*     */ 
/*     */     
/* 183 */     BigInteger bigInteger11 = bigInteger3.modPow(bigInteger6, bigInteger4);
/*     */     
/* 185 */     BigInteger bigInteger12 = bigInteger3.modPow(bigInteger7, bigInteger5);
/*     */ 
/*     */     
/* 188 */     BigInteger bigInteger13 = bigInteger11.subtract(bigInteger12);
/* 189 */     if (bigInteger13.signum() < 0) {
/* 190 */       bigInteger13 = bigInteger13.add(bigInteger4);
/*     */     }
/* 192 */     BigInteger bigInteger14 = bigInteger13.multiply(bigInteger8).mod(bigInteger4);
/*     */ 
/*     */     
/* 195 */     BigInteger bigInteger15 = bigInteger14.multiply(bigInteger5).add(bigInteger12);
/*     */ 
/*     */     
/* 198 */     bigInteger15 = bigInteger15.multiply(blindingRandomPair.v).mod(bigInteger1);
/*     */     
/* 200 */     if (paramBoolean && !bigInteger2.equals(bigInteger15.modPow(bigInteger9, bigInteger1))) {
/* 201 */       throw new BadPaddingException("RSA private key operation failed");
/*     */     }
/*     */     
/* 204 */     return toByteArray(bigInteger15, getByteLength(bigInteger1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BigInteger parseMsg(byte[] paramArrayOfbyte, BigInteger paramBigInteger) throws BadPaddingException {
/* 212 */     BigInteger bigInteger = new BigInteger(1, paramArrayOfbyte);
/* 213 */     if (bigInteger.compareTo(paramBigInteger) >= 0) {
/* 214 */       throw new BadPaddingException("Message is larger than modulus");
/*     */     }
/* 216 */     return bigInteger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] toByteArray(BigInteger paramBigInteger, int paramInt) {
/* 225 */     byte[] arrayOfByte1 = paramBigInteger.toByteArray();
/* 226 */     int i = arrayOfByte1.length;
/* 227 */     if (i == paramInt) {
/* 228 */       return arrayOfByte1;
/*     */     }
/*     */     
/* 231 */     if (i == paramInt + 1 && arrayOfByte1[0] == 0) {
/* 232 */       byte[] arrayOfByte = new byte[paramInt];
/* 233 */       System.arraycopy(arrayOfByte1, 1, arrayOfByte, 0, paramInt);
/* 234 */       return arrayOfByte;
/*     */     } 
/*     */     
/* 237 */     assert i < paramInt;
/* 238 */     byte[] arrayOfByte2 = new byte[paramInt];
/* 239 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, paramInt - i, i);
/* 240 */     return arrayOfByte2;
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
/*     */   private static final class BlindingRandomPair
/*     */   {
/*     */     final BigInteger u;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final BigInteger v;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     BlindingRandomPair(BigInteger param1BigInteger1, BigInteger param1BigInteger2) {
/* 319 */       this.u = param1BigInteger1;
/* 320 */       this.v = param1BigInteger2;
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
/*     */   private static final class BlindingParameters
/*     */   {
/* 336 */     private static final BigInteger BIG_TWO = BigInteger.valueOf(2L);
/*     */ 
/*     */ 
/*     */     
/*     */     private final BigInteger e;
/*     */ 
/*     */     
/*     */     private final BigInteger d;
/*     */ 
/*     */     
/*     */     private BigInteger u;
/*     */ 
/*     */     
/*     */     private BigInteger v;
/*     */ 
/*     */ 
/*     */     
/*     */     BlindingParameters(BigInteger param1BigInteger1, BigInteger param1BigInteger2, BigInteger param1BigInteger3) {
/* 354 */       this.u = null;
/* 355 */       this.v = null;
/* 356 */       this.e = param1BigInteger1;
/* 357 */       this.d = param1BigInteger2;
/*     */       
/* 359 */       int i = param1BigInteger3.bitLength();
/* 360 */       SecureRandom secureRandom = JCAUtil.getSecureRandom();
/* 361 */       this.u = (new BigInteger(i, secureRandom)).mod(param1BigInteger3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 370 */       if (this.u.equals(BigInteger.ZERO)) {
/* 371 */         this.u = BigInteger.ONE;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 378 */         this.v = this.u.modInverse(param1BigInteger3);
/* 379 */       } catch (ArithmeticException arithmeticException) {
/*     */         
/* 381 */         this.u = BigInteger.ONE;
/* 382 */         this.v = BigInteger.ONE;
/*     */       } 
/*     */       
/* 385 */       if (param1BigInteger1 != null) {
/* 386 */         this.u = this.u.modPow(param1BigInteger1, param1BigInteger3);
/*     */       }
/*     */       else {
/*     */         
/* 390 */         this.v = this.v.modPow(param1BigInteger2, param1BigInteger3);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     RSACore.BlindingRandomPair getBlindingRandomPair(BigInteger param1BigInteger1, BigInteger param1BigInteger2, BigInteger param1BigInteger3) {
/* 400 */       if ((this.e != null && this.e.equals(param1BigInteger1)) || (this.d != null && this.d
/* 401 */         .equals(param1BigInteger2))) {
/*     */         
/* 403 */         RSACore.BlindingRandomPair blindingRandomPair = null;
/* 404 */         synchronized (this) {
/* 405 */           if (!this.u.equals(BigInteger.ZERO) && 
/* 406 */             !this.v.equals(BigInteger.ZERO)) {
/*     */             
/* 408 */             blindingRandomPair = new RSACore.BlindingRandomPair(this.u, this.v);
/* 409 */             if (this.u.compareTo(BigInteger.ONE) <= 0 || this.v
/* 410 */               .compareTo(BigInteger.ONE) <= 0) {
/*     */ 
/*     */               
/* 413 */               this.u = BigInteger.ZERO;
/* 414 */               this.v = BigInteger.ZERO;
/*     */             } else {
/* 416 */               this.u = this.u.modPow(BIG_TWO, param1BigInteger3);
/* 417 */               this.v = this.v.modPow(BIG_TWO, param1BigInteger3);
/*     */             } 
/*     */           } 
/*     */         } 
/* 421 */         return blindingRandomPair;
/*     */       } 
/*     */       
/* 424 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BlindingRandomPair getBlindingRandomPair(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3) {
/* 431 */     BlindingParameters blindingParameters = null;
/* 432 */     synchronized (blindingCache) {
/* 433 */       blindingParameters = blindingCache.get(paramBigInteger3);
/*     */     } 
/*     */     
/* 436 */     if (blindingParameters == null) {
/* 437 */       blindingParameters = new BlindingParameters(paramBigInteger1, paramBigInteger2, paramBigInteger3);
/* 438 */       synchronized (blindingCache) {
/* 439 */         blindingCache.putIfAbsent(paramBigInteger3, blindingParameters);
/*     */       } 
/*     */     } 
/*     */     
/* 443 */     BlindingRandomPair blindingRandomPair = blindingParameters.getBlindingRandomPair(paramBigInteger1, paramBigInteger2, paramBigInteger3);
/* 444 */     if (blindingRandomPair == null) {
/*     */       
/* 446 */       blindingParameters = new BlindingParameters(paramBigInteger1, paramBigInteger2, paramBigInteger3);
/* 447 */       synchronized (blindingCache) {
/* 448 */         blindingCache.replace(paramBigInteger3, blindingParameters);
/*     */       } 
/* 450 */       blindingRandomPair = blindingParameters.getBlindingRandomPair(paramBigInteger1, paramBigInteger2, paramBigInteger3);
/*     */     } 
/*     */     
/* 453 */     return blindingRandomPair;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/rsa/RSACore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */