/*     */ package sun.security.util;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.interfaces.DSAKey;
/*     */ import java.security.interfaces.DSAParams;
/*     */ import java.security.interfaces.ECKey;
/*     */ import java.security.interfaces.RSAKey;
/*     */ import java.security.spec.ECParameterSpec;
/*     */ import java.security.spec.InvalidParameterSpecException;
/*     */ import java.security.spec.KeySpec;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.interfaces.DHKey;
/*     */ import javax.crypto.interfaces.DHPublicKey;
/*     */ import javax.crypto.spec.DHParameterSpec;
/*     */ import javax.crypto.spec.DHPublicKeySpec;
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
/*     */ public final class KeyUtil
/*     */ {
/*     */   public static final int getKeySize(Key paramKey) {
/*  63 */     int i = -1;
/*     */     
/*  65 */     if (paramKey instanceof Length) {
/*     */       try {
/*  67 */         Length length = (Length)paramKey;
/*  68 */         i = length.length();
/*  69 */       } catch (UnsupportedOperationException unsupportedOperationException) {}
/*     */ 
/*     */ 
/*     */       
/*  73 */       if (i >= 0) {
/*  74 */         return i;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  79 */     if (paramKey instanceof SecretKey) {
/*  80 */       SecretKey secretKey = (SecretKey)paramKey;
/*  81 */       String str = secretKey.getFormat();
/*  82 */       if ("RAW".equals(str) && secretKey.getEncoded() != null) {
/*  83 */         i = (secretKey.getEncoded()).length * 8;
/*     */       }
/*     */     }
/*  86 */     else if (paramKey instanceof RSAKey) {
/*  87 */       RSAKey rSAKey = (RSAKey)paramKey;
/*  88 */       i = rSAKey.getModulus().bitLength();
/*  89 */     } else if (paramKey instanceof ECKey) {
/*  90 */       ECKey eCKey = (ECKey)paramKey;
/*  91 */       i = eCKey.getParams().getOrder().bitLength();
/*  92 */     } else if (paramKey instanceof DSAKey) {
/*  93 */       DSAKey dSAKey = (DSAKey)paramKey;
/*  94 */       DSAParams dSAParams = dSAKey.getParams();
/*  95 */       i = (dSAParams != null) ? dSAParams.getP().bitLength() : -1;
/*  96 */     } else if (paramKey instanceof DHKey) {
/*  97 */       DHKey dHKey = (DHKey)paramKey;
/*  98 */       i = dHKey.getParams().getP().bitLength();
/*     */     } 
/*     */ 
/*     */     
/* 102 */     return i;
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
/*     */   public static final int getKeySize(AlgorithmParameters paramAlgorithmParameters) {
/* 114 */     String str = paramAlgorithmParameters.getAlgorithm();
/* 115 */     switch (str) {
/*     */       case "EC":
/*     */         try {
/* 118 */           ECKeySizeParameterSpec eCKeySizeParameterSpec = paramAlgorithmParameters.<ECKeySizeParameterSpec>getParameterSpec(ECKeySizeParameterSpec.class);
/*     */           
/* 120 */           if (eCKeySizeParameterSpec != null) {
/* 121 */             return eCKeySizeParameterSpec.getKeySize();
/*     */           }
/* 123 */         } catch (InvalidParameterSpecException invalidParameterSpecException) {}
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 128 */           ECParameterSpec eCParameterSpec = paramAlgorithmParameters.<ECParameterSpec>getParameterSpec(ECParameterSpec.class);
/*     */           
/* 130 */           if (eCParameterSpec != null) {
/* 131 */             return eCParameterSpec.getOrder().bitLength();
/*     */           }
/* 133 */         } catch (InvalidParameterSpecException invalidParameterSpecException) {}
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case "DiffieHellman":
/*     */         try {
/* 144 */           DHParameterSpec dHParameterSpec = paramAlgorithmParameters.<DHParameterSpec>getParameterSpec(DHParameterSpec.class);
/*     */           
/* 146 */           if (dHParameterSpec != null) {
/* 147 */             return dHParameterSpec.getP().bitLength();
/*     */           }
/* 149 */         } catch (InvalidParameterSpecException invalidParameterSpecException) {}
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     return -1;
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
/*     */   public static final void validate(Key paramKey) throws InvalidKeyException {
/* 173 */     if (paramKey == null) {
/* 174 */       throw new NullPointerException("The key to be validated cannot be null");
/*     */     }
/*     */ 
/*     */     
/* 178 */     if (paramKey instanceof DHPublicKey) {
/* 179 */       validateDHPublicKey((DHPublicKey)paramKey);
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
/*     */   public static final void validate(KeySpec paramKeySpec) throws InvalidKeyException {
/* 197 */     if (paramKeySpec == null) {
/* 198 */       throw new NullPointerException("The key spec to be validated cannot be null");
/*     */     }
/*     */ 
/*     */     
/* 202 */     if (paramKeySpec instanceof DHPublicKeySpec) {
/* 203 */       validateDHPublicKey((DHPublicKeySpec)paramKeySpec);
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
/*     */   public static final boolean isOracleJCEProvider(String paramString) {
/* 216 */     return (paramString != null && (paramString
/* 217 */       .equals("SunJCE") || paramString
/* 218 */       .equals("SunMSCAPI") || paramString
/* 219 */       .equals("OracleUcrypto") || paramString
/* 220 */       .startsWith("SunPKCS11")));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] checkTlsPreMasterSecretKey(int paramInt1, int paramInt2, SecureRandom paramSecureRandom, byte[] paramArrayOfbyte, boolean paramBoolean) {
/* 264 */     if (paramSecureRandom == null) {
/* 265 */       paramSecureRandom = JCAUtil.getSecureRandom();
/*     */     }
/* 267 */     byte[] arrayOfByte = new byte[48];
/* 268 */     paramSecureRandom.nextBytes(arrayOfByte);
/*     */     
/* 270 */     if (!paramBoolean && paramArrayOfbyte != null) {
/*     */       
/* 272 */       if (paramArrayOfbyte.length != 48)
/*     */       {
/* 274 */         return arrayOfByte;
/*     */       }
/*     */       
/* 277 */       int i = (paramArrayOfbyte[0] & 0xFF) << 8 | paramArrayOfbyte[1] & 0xFF;
/*     */       
/* 279 */       if (paramInt1 != i && (
/* 280 */         paramInt1 > 769 || paramInt2 != i))
/*     */       {
/* 282 */         paramArrayOfbyte = arrayOfByte;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 289 */       return paramArrayOfbyte;
/*     */     } 
/*     */ 
/*     */     
/* 293 */     return arrayOfByte;
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
/*     */   private static void validateDHPublicKey(DHPublicKey paramDHPublicKey) throws InvalidKeyException {
/* 308 */     DHParameterSpec dHParameterSpec = paramDHPublicKey.getParams();
/*     */     
/* 310 */     BigInteger bigInteger1 = dHParameterSpec.getP();
/* 311 */     BigInteger bigInteger2 = dHParameterSpec.getG();
/* 312 */     BigInteger bigInteger3 = paramDHPublicKey.getY();
/*     */     
/* 314 */     validateDHPublicKey(bigInteger1, bigInteger2, bigInteger3);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void validateDHPublicKey(DHPublicKeySpec paramDHPublicKeySpec) throws InvalidKeyException {
/* 319 */     validateDHPublicKey(paramDHPublicKeySpec.getP(), paramDHPublicKeySpec
/* 320 */         .getG(), paramDHPublicKeySpec.getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validateDHPublicKey(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3) throws InvalidKeyException {
/* 327 */     BigInteger bigInteger1 = BigInteger.ONE;
/* 328 */     BigInteger bigInteger2 = paramBigInteger1.subtract(BigInteger.ONE);
/* 329 */     if (paramBigInteger3.compareTo(bigInteger1) <= 0) {
/* 330 */       throw new InvalidKeyException("Diffie-Hellman public key is too small");
/*     */     }
/*     */     
/* 333 */     if (paramBigInteger3.compareTo(bigInteger2) >= 0) {
/* 334 */       throw new InvalidKeyException("Diffie-Hellman public key is too large");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 344 */     BigInteger bigInteger3 = paramBigInteger1.remainder(paramBigInteger3);
/* 345 */     if (bigInteger3.equals(BigInteger.ZERO)) {
/* 346 */       throw new InvalidKeyException("Invalid Diffie-Hellman parameters");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] trimZeroes(byte[] paramArrayOfbyte) {
/* 356 */     byte b = 0;
/* 357 */     while (b < paramArrayOfbyte.length - 1 && paramArrayOfbyte[b] == 0) {
/* 358 */       b++;
/*     */     }
/* 360 */     if (b == 0) {
/* 361 */       return paramArrayOfbyte;
/*     */     }
/* 363 */     byte[] arrayOfByte = new byte[paramArrayOfbyte.length - b];
/* 364 */     System.arraycopy(paramArrayOfbyte, b, arrayOfByte, 0, arrayOfByte.length);
/* 365 */     return arrayOfByte;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/KeyUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */