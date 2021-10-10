/*     */ package sun.security.rsa;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.AccessController;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.KeyFactorySpi;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.interfaces.RSAKey;
/*     */ import java.security.interfaces.RSAPrivateCrtKey;
/*     */ import java.security.interfaces.RSAPrivateKey;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.KeySpec;
/*     */ import java.security.spec.PKCS8EncodedKeySpec;
/*     */ import java.security.spec.RSAPrivateCrtKeySpec;
/*     */ import java.security.spec.RSAPrivateKeySpec;
/*     */ import java.security.spec.RSAPublicKeySpec;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RSAKeyFactory
/*     */   extends KeyFactorySpi
/*     */ {
/*  63 */   private static final Class<?> rsaPublicKeySpecClass = RSAPublicKeySpec.class;
/*     */   
/*  65 */   private static final Class<?> rsaPrivateKeySpecClass = RSAPrivateKeySpec.class;
/*     */   
/*  67 */   private static final Class<?> rsaPrivateCrtKeySpecClass = RSAPrivateCrtKeySpec.class;
/*     */ 
/*     */   
/*  70 */   private static final Class<?> x509KeySpecClass = X509EncodedKeySpec.class;
/*  71 */   private static final Class<?> pkcs8KeySpecClass = PKCS8EncodedKeySpec.class;
/*     */ 
/*     */   
/*     */   public static final int MIN_MODLEN = 512;
/*     */ 
/*     */   
/*     */   public static final int MAX_MODLEN = 16384;
/*     */ 
/*     */   
/*     */   public static final int MAX_MODLEN_RESTRICT_EXP = 3072;
/*     */ 
/*     */   
/*     */   public static final int MAX_RESTRICTED_EXPLEN = 64;
/*     */ 
/*     */   
/*  86 */   private static final boolean restrictExpLen = "true"
/*  87 */     .equalsIgnoreCase(AccessController.<String>doPrivileged(new GetPropertyAction("sun.security.rsa.restrictRSAExponent", "true")));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private static final RSAKeyFactory INSTANCE = new RSAKeyFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RSAKey toRSAKey(Key paramKey) throws InvalidKeyException {
/* 106 */     if (paramKey instanceof RSAPrivateKeyImpl || paramKey instanceof RSAPrivateCrtKeyImpl || paramKey instanceof RSAPublicKeyImpl)
/*     */     {
/*     */       
/* 109 */       return (RSAKey)paramKey;
/*     */     }
/* 111 */     return (RSAKey)INSTANCE.engineTranslateKey(paramKey);
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
/*     */   static void checkRSAProviderKeyLengths(int paramInt, BigInteger paramBigInteger) throws InvalidKeyException {
/* 125 */     checkKeyLengths(paramInt + 7 & 0xFFFFFFF8, paramBigInteger, 512, 2147483647);
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
/*     */   public static void checkKeyLengths(int paramInt1, BigInteger paramBigInteger, int paramInt2, int paramInt3) throws InvalidKeyException {
/* 146 */     if (paramInt2 > 0 && paramInt1 < paramInt2) {
/* 147 */       throw new InvalidKeyException("RSA keys must be at least " + paramInt2 + " bits long");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     int i = Math.min(paramInt3, 16384);
/*     */ 
/*     */ 
/*     */     
/* 158 */     if (paramInt1 > i) {
/* 159 */       throw new InvalidKeyException("RSA keys must be no longer than " + i + " bits");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 164 */     if (restrictExpLen && paramBigInteger != null && paramInt1 > 3072 && paramBigInteger
/*     */       
/* 166 */       .bitLength() > 64) {
/* 167 */       throw new InvalidKeyException("RSA exponents can be no longer than 64 bits  if modulus is greater than 3072 bits");
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
/*     */   protected Key engineTranslateKey(Key paramKey) throws InvalidKeyException {
/* 181 */     if (paramKey == null) {
/* 182 */       throw new InvalidKeyException("Key must not be null");
/*     */     }
/* 184 */     String str = paramKey.getAlgorithm();
/* 185 */     if (!str.equals("RSA")) {
/* 186 */       throw new InvalidKeyException("Not an RSA key: " + str);
/*     */     }
/* 188 */     if (paramKey instanceof PublicKey)
/* 189 */       return translatePublicKey((PublicKey)paramKey); 
/* 190 */     if (paramKey instanceof PrivateKey) {
/* 191 */       return translatePrivateKey((PrivateKey)paramKey);
/*     */     }
/* 193 */     throw new InvalidKeyException("Neither a public nor a private key");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PublicKey engineGeneratePublic(KeySpec paramKeySpec) throws InvalidKeySpecException {
/*     */     try {
/* 201 */       return generatePublic(paramKeySpec);
/* 202 */     } catch (InvalidKeySpecException invalidKeySpecException) {
/* 203 */       throw invalidKeySpecException;
/* 204 */     } catch (GeneralSecurityException generalSecurityException) {
/* 205 */       throw new InvalidKeySpecException(generalSecurityException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PrivateKey engineGeneratePrivate(KeySpec paramKeySpec) throws InvalidKeySpecException {
/*     */     try {
/* 213 */       return generatePrivate(paramKeySpec);
/* 214 */     } catch (InvalidKeySpecException invalidKeySpecException) {
/* 215 */       throw invalidKeySpecException;
/* 216 */     } catch (GeneralSecurityException generalSecurityException) {
/* 217 */       throw new InvalidKeySpecException(generalSecurityException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PublicKey translatePublicKey(PublicKey paramPublicKey) throws InvalidKeyException {
/* 224 */     if (paramPublicKey instanceof RSAPublicKey) {
/* 225 */       if (paramPublicKey instanceof RSAPublicKeyImpl) {
/* 226 */         return paramPublicKey;
/*     */       }
/* 228 */       RSAPublicKey rSAPublicKey = (RSAPublicKey)paramPublicKey;
/*     */       try {
/* 230 */         return new RSAPublicKeyImpl(rSAPublicKey
/* 231 */             .getModulus(), rSAPublicKey
/* 232 */             .getPublicExponent());
/*     */       }
/* 234 */       catch (RuntimeException runtimeException) {
/*     */         
/* 236 */         throw new InvalidKeyException("Invalid key", runtimeException);
/*     */       } 
/* 238 */     }  if ("X.509".equals(paramPublicKey.getFormat())) {
/* 239 */       byte[] arrayOfByte = paramPublicKey.getEncoded();
/* 240 */       return new RSAPublicKeyImpl(arrayOfByte);
/*     */     } 
/* 242 */     throw new InvalidKeyException("Public keys must be instance of RSAPublicKey or have X.509 encoding");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PrivateKey translatePrivateKey(PrivateKey paramPrivateKey) throws InvalidKeyException {
/* 250 */     if (paramPrivateKey instanceof RSAPrivateCrtKey) {
/* 251 */       if (paramPrivateKey instanceof RSAPrivateCrtKeyImpl) {
/* 252 */         return paramPrivateKey;
/*     */       }
/* 254 */       RSAPrivateCrtKey rSAPrivateCrtKey = (RSAPrivateCrtKey)paramPrivateKey;
/*     */       try {
/* 256 */         return new RSAPrivateCrtKeyImpl(rSAPrivateCrtKey
/* 257 */             .getModulus(), rSAPrivateCrtKey
/* 258 */             .getPublicExponent(), rSAPrivateCrtKey
/* 259 */             .getPrivateExponent(), rSAPrivateCrtKey
/* 260 */             .getPrimeP(), rSAPrivateCrtKey
/* 261 */             .getPrimeQ(), rSAPrivateCrtKey
/* 262 */             .getPrimeExponentP(), rSAPrivateCrtKey
/* 263 */             .getPrimeExponentQ(), rSAPrivateCrtKey
/* 264 */             .getCrtCoefficient());
/*     */       }
/* 266 */       catch (RuntimeException runtimeException) {
/*     */         
/* 268 */         throw new InvalidKeyException("Invalid key", runtimeException);
/*     */       } 
/* 270 */     }  if (paramPrivateKey instanceof RSAPrivateKey) {
/* 271 */       if (paramPrivateKey instanceof RSAPrivateKeyImpl) {
/* 272 */         return paramPrivateKey;
/*     */       }
/* 274 */       RSAPrivateKey rSAPrivateKey = (RSAPrivateKey)paramPrivateKey;
/*     */       try {
/* 276 */         return new RSAPrivateKeyImpl(rSAPrivateKey
/* 277 */             .getModulus(), rSAPrivateKey
/* 278 */             .getPrivateExponent());
/*     */       }
/* 280 */       catch (RuntimeException runtimeException) {
/*     */         
/* 282 */         throw new InvalidKeyException("Invalid key", runtimeException);
/*     */       } 
/* 284 */     }  if ("PKCS#8".equals(paramPrivateKey.getFormat())) {
/* 285 */       byte[] arrayOfByte = paramPrivateKey.getEncoded();
/* 286 */       return RSAPrivateCrtKeyImpl.newKey(arrayOfByte);
/*     */     } 
/* 288 */     throw new InvalidKeyException("Private keys must be instance of RSAPrivate(Crt)Key or have PKCS#8 encoding");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PublicKey generatePublic(KeySpec paramKeySpec) throws GeneralSecurityException {
/* 296 */     if (paramKeySpec instanceof X509EncodedKeySpec) {
/* 297 */       X509EncodedKeySpec x509EncodedKeySpec = (X509EncodedKeySpec)paramKeySpec;
/* 298 */       return new RSAPublicKeyImpl(x509EncodedKeySpec.getEncoded());
/* 299 */     }  if (paramKeySpec instanceof RSAPublicKeySpec) {
/* 300 */       RSAPublicKeySpec rSAPublicKeySpec = (RSAPublicKeySpec)paramKeySpec;
/* 301 */       return new RSAPublicKeyImpl(rSAPublicKeySpec
/* 302 */           .getModulus(), rSAPublicKeySpec
/* 303 */           .getPublicExponent());
/*     */     } 
/*     */     
/* 306 */     throw new InvalidKeySpecException("Only RSAPublicKeySpec and X509EncodedKeySpec supported for RSA public keys");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PrivateKey generatePrivate(KeySpec paramKeySpec) throws GeneralSecurityException {
/* 314 */     if (paramKeySpec instanceof PKCS8EncodedKeySpec) {
/* 315 */       PKCS8EncodedKeySpec pKCS8EncodedKeySpec = (PKCS8EncodedKeySpec)paramKeySpec;
/* 316 */       return RSAPrivateCrtKeyImpl.newKey(pKCS8EncodedKeySpec.getEncoded());
/* 317 */     }  if (paramKeySpec instanceof RSAPrivateCrtKeySpec) {
/* 318 */       RSAPrivateCrtKeySpec rSAPrivateCrtKeySpec = (RSAPrivateCrtKeySpec)paramKeySpec;
/* 319 */       return new RSAPrivateCrtKeyImpl(rSAPrivateCrtKeySpec
/* 320 */           .getModulus(), rSAPrivateCrtKeySpec
/* 321 */           .getPublicExponent(), rSAPrivateCrtKeySpec
/* 322 */           .getPrivateExponent(), rSAPrivateCrtKeySpec
/* 323 */           .getPrimeP(), rSAPrivateCrtKeySpec
/* 324 */           .getPrimeQ(), rSAPrivateCrtKeySpec
/* 325 */           .getPrimeExponentP(), rSAPrivateCrtKeySpec
/* 326 */           .getPrimeExponentQ(), rSAPrivateCrtKeySpec
/* 327 */           .getCrtCoefficient());
/*     */     } 
/* 329 */     if (paramKeySpec instanceof RSAPrivateKeySpec) {
/* 330 */       RSAPrivateKeySpec rSAPrivateKeySpec = (RSAPrivateKeySpec)paramKeySpec;
/* 331 */       return new RSAPrivateKeyImpl(rSAPrivateKeySpec
/* 332 */           .getModulus(), rSAPrivateKeySpec
/* 333 */           .getPrivateExponent());
/*     */     } 
/*     */     
/* 336 */     throw new InvalidKeySpecException("Only RSAPrivate(Crt)KeySpec and PKCS8EncodedKeySpec supported for RSA private keys");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected <T extends KeySpec> T engineGetKeySpec(Key paramKey, Class<T> paramClass) throws InvalidKeySpecException {
/*     */     try {
/* 347 */       paramKey = engineTranslateKey(paramKey);
/* 348 */     } catch (InvalidKeyException invalidKeyException) {
/* 349 */       throw new InvalidKeySpecException(invalidKeyException);
/*     */     } 
/* 351 */     if (paramKey instanceof RSAPublicKey) {
/* 352 */       RSAPublicKey rSAPublicKey = (RSAPublicKey)paramKey;
/* 353 */       if (rsaPublicKeySpecClass.isAssignableFrom(paramClass)) {
/* 354 */         return paramClass.cast(new RSAPublicKeySpec(rSAPublicKey
/* 355 */               .getModulus(), rSAPublicKey
/* 356 */               .getPublicExponent()));
/*     */       }
/* 358 */       if (x509KeySpecClass.isAssignableFrom(paramClass)) {
/* 359 */         return paramClass.cast(new X509EncodedKeySpec(paramKey.getEncoded()));
/*     */       }
/* 361 */       throw new InvalidKeySpecException("KeySpec must be RSAPublicKeySpec or X509EncodedKeySpec for RSA public keys");
/*     */     } 
/*     */ 
/*     */     
/* 365 */     if (paramKey instanceof RSAPrivateKey) {
/* 366 */       if (pkcs8KeySpecClass.isAssignableFrom(paramClass))
/* 367 */         return paramClass.cast(new PKCS8EncodedKeySpec(paramKey.getEncoded())); 
/* 368 */       if (rsaPrivateCrtKeySpecClass.isAssignableFrom(paramClass)) {
/* 369 */         if (paramKey instanceof RSAPrivateCrtKey) {
/* 370 */           RSAPrivateCrtKey rSAPrivateCrtKey = (RSAPrivateCrtKey)paramKey;
/* 371 */           return paramClass.cast(new RSAPrivateCrtKeySpec(rSAPrivateCrtKey
/* 372 */                 .getModulus(), rSAPrivateCrtKey
/* 373 */                 .getPublicExponent(), rSAPrivateCrtKey
/* 374 */                 .getPrivateExponent(), rSAPrivateCrtKey
/* 375 */                 .getPrimeP(), rSAPrivateCrtKey
/* 376 */                 .getPrimeQ(), rSAPrivateCrtKey
/* 377 */                 .getPrimeExponentP(), rSAPrivateCrtKey
/* 378 */                 .getPrimeExponentQ(), rSAPrivateCrtKey
/* 379 */                 .getCrtCoefficient()));
/*     */         } 
/*     */         
/* 382 */         throw new InvalidKeySpecException("RSAPrivateCrtKeySpec can only be used with CRT keys");
/*     */       } 
/*     */       
/* 385 */       if (rsaPrivateKeySpecClass.isAssignableFrom(paramClass)) {
/* 386 */         RSAPrivateKey rSAPrivateKey = (RSAPrivateKey)paramKey;
/* 387 */         return paramClass.cast(new RSAPrivateKeySpec(rSAPrivateKey
/* 388 */               .getModulus(), rSAPrivateKey
/* 389 */               .getPrivateExponent()));
/*     */       } 
/*     */       
/* 392 */       throw new InvalidKeySpecException("KeySpec must be RSAPrivate(Crt)KeySpec or PKCS8EncodedKeySpec for RSA private keys");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 398 */     throw new InvalidKeySpecException("Neither public nor private key");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/rsa/RSAKeyFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */