/*     */ package java.security;
/*     */ 
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.KeySpec;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import sun.security.jca.GetInstance;
/*     */ import sun.security.util.Debug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyFactory
/*     */ {
/*  99 */   private static final Debug debug = Debug.getInstance("jca", "KeyFactory");
/*     */ 
/*     */   
/*     */   private final String algorithm;
/*     */ 
/*     */   
/*     */   private Provider provider;
/*     */ 
/*     */   
/*     */   private volatile KeyFactorySpi spi;
/*     */ 
/*     */   
/* 111 */   private final Object lock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Iterator<Provider.Service> serviceIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected KeyFactory(KeyFactorySpi paramKeyFactorySpi, Provider paramProvider, String paramString) {
/* 127 */     this.spi = paramKeyFactorySpi;
/* 128 */     this.provider = paramProvider;
/* 129 */     this.algorithm = paramString;
/*     */   }
/*     */   
/*     */   private KeyFactory(String paramString) throws NoSuchAlgorithmException {
/* 133 */     this.algorithm = paramString;
/* 134 */     List<Provider.Service> list = GetInstance.getServices("KeyFactory", paramString);
/* 135 */     this.serviceIterator = list.iterator();
/*     */     
/* 137 */     if (nextSpi(null) == null) {
/* 138 */       throw new NoSuchAlgorithmException(paramString + " KeyFactory not available");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyFactory getInstance(String paramString) throws NoSuchAlgorithmException {
/* 172 */     return new KeyFactory(paramString);
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
/*     */   public static KeyFactory getInstance(String paramString1, String paramString2) throws NoSuchAlgorithmException, NoSuchProviderException {
/* 211 */     GetInstance.Instance instance = GetInstance.getInstance("KeyFactory", KeyFactorySpi.class, paramString1, paramString2);
/*     */     
/* 213 */     return new KeyFactory((KeyFactorySpi)instance.impl, instance.provider, paramString1);
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
/*     */   public static KeyFactory getInstance(String paramString, Provider paramProvider) throws NoSuchAlgorithmException {
/* 248 */     GetInstance.Instance instance = GetInstance.getInstance("KeyFactory", KeyFactorySpi.class, paramString, paramProvider);
/*     */     
/* 250 */     return new KeyFactory((KeyFactorySpi)instance.impl, instance.provider, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Provider getProvider() {
/* 260 */     synchronized (this.lock) {
/*     */       
/* 262 */       this.serviceIterator = null;
/* 263 */       return this.provider;
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
/*     */   public final String getAlgorithm() {
/* 275 */     return this.algorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private KeyFactorySpi nextSpi(KeyFactorySpi paramKeyFactorySpi) {
/* 285 */     synchronized (this.lock) {
/*     */ 
/*     */       
/* 288 */       if (paramKeyFactorySpi != null && paramKeyFactorySpi != this.spi) {
/* 289 */         return this.spi;
/*     */       }
/* 291 */       if (this.serviceIterator == null) {
/* 292 */         return null;
/*     */       }
/* 294 */       while (this.serviceIterator.hasNext()) {
/* 295 */         Provider.Service service = this.serviceIterator.next();
/*     */         try {
/* 297 */           Object object = service.newInstance(null);
/* 298 */           if (!(object instanceof KeyFactorySpi)) {
/*     */             continue;
/*     */           }
/* 301 */           KeyFactorySpi keyFactorySpi = (KeyFactorySpi)object;
/* 302 */           this.provider = service.getProvider();
/* 303 */           this.spi = keyFactorySpi;
/* 304 */           return keyFactorySpi;
/* 305 */         } catch (NoSuchAlgorithmException noSuchAlgorithmException) {}
/*     */       } 
/*     */ 
/*     */       
/* 309 */       this.serviceIterator = null;
/* 310 */       return null;
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
/*     */   public final PublicKey generatePublic(KeySpec paramKeySpec) throws InvalidKeySpecException {
/* 327 */     if (this.serviceIterator == null) {
/* 328 */       return this.spi.engineGeneratePublic(paramKeySpec);
/*     */     }
/* 330 */     Exception exception = null;
/* 331 */     KeyFactorySpi keyFactorySpi = this.spi;
/*     */     while (true) {
/*     */       try {
/* 334 */         return keyFactorySpi.engineGeneratePublic(paramKeySpec);
/* 335 */       } catch (Exception exception1) {
/* 336 */         if (exception == null) {
/* 337 */           exception = exception1;
/*     */         }
/* 339 */         keyFactorySpi = nextSpi(keyFactorySpi);
/*     */         
/* 341 */         if (keyFactorySpi == null) {
/* 342 */           if (exception instanceof RuntimeException) {
/* 343 */             throw (RuntimeException)exception;
/*     */           }
/* 345 */           if (exception instanceof InvalidKeySpecException) {
/* 346 */             throw (InvalidKeySpecException)exception;
/*     */           }
/* 348 */           throw new InvalidKeySpecException("Could not generate public key", exception);
/*     */         } 
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
/*     */   public final PrivateKey generatePrivate(KeySpec paramKeySpec) throws InvalidKeySpecException {
/* 365 */     if (this.serviceIterator == null) {
/* 366 */       return this.spi.engineGeneratePrivate(paramKeySpec);
/*     */     }
/* 368 */     Exception exception = null;
/* 369 */     KeyFactorySpi keyFactorySpi = this.spi;
/*     */     while (true) {
/*     */       try {
/* 372 */         return keyFactorySpi.engineGeneratePrivate(paramKeySpec);
/* 373 */       } catch (Exception exception1) {
/* 374 */         if (exception == null) {
/* 375 */           exception = exception1;
/*     */         }
/* 377 */         keyFactorySpi = nextSpi(keyFactorySpi);
/*     */         
/* 379 */         if (keyFactorySpi == null) {
/* 380 */           if (exception instanceof RuntimeException) {
/* 381 */             throw (RuntimeException)exception;
/*     */           }
/* 383 */           if (exception instanceof InvalidKeySpecException) {
/* 384 */             throw (InvalidKeySpecException)exception;
/*     */           }
/* 386 */           throw new InvalidKeySpecException("Could not generate private key", exception);
/*     */         } 
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
/*     */ 
/*     */   
/*     */   public final <T extends KeySpec> T getKeySpec(Key paramKey, Class<T> paramClass) throws InvalidKeySpecException {
/* 414 */     if (this.serviceIterator == null) {
/* 415 */       return this.spi.engineGetKeySpec(paramKey, paramClass);
/*     */     }
/* 417 */     Exception exception = null;
/* 418 */     KeyFactorySpi keyFactorySpi = this.spi;
/*     */     while (true) {
/*     */       try {
/* 421 */         return keyFactorySpi.engineGetKeySpec(paramKey, paramClass);
/* 422 */       } catch (Exception exception1) {
/* 423 */         if (exception == null) {
/* 424 */           exception = exception1;
/*     */         }
/* 426 */         keyFactorySpi = nextSpi(keyFactorySpi);
/*     */         
/* 428 */         if (keyFactorySpi == null) {
/* 429 */           if (exception instanceof RuntimeException) {
/* 430 */             throw (RuntimeException)exception;
/*     */           }
/* 432 */           if (exception instanceof InvalidKeySpecException) {
/* 433 */             throw (InvalidKeySpecException)exception;
/*     */           }
/* 435 */           throw new InvalidKeySpecException("Could not get key spec", exception);
/*     */         } 
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
/*     */   public final Key translateKey(Key paramKey) throws InvalidKeyException {
/* 451 */     if (this.serviceIterator == null) {
/* 452 */       return this.spi.engineTranslateKey(paramKey);
/*     */     }
/* 454 */     Exception exception = null;
/* 455 */     KeyFactorySpi keyFactorySpi = this.spi;
/*     */     while (true) {
/*     */       try {
/* 458 */         return keyFactorySpi.engineTranslateKey(paramKey);
/* 459 */       } catch (Exception exception1) {
/* 460 */         if (exception == null) {
/* 461 */           exception = exception1;
/*     */         }
/* 463 */         keyFactorySpi = nextSpi(keyFactorySpi);
/*     */         
/* 465 */         if (keyFactorySpi == null) {
/* 466 */           if (exception instanceof RuntimeException) {
/* 467 */             throw (RuntimeException)exception;
/*     */           }
/* 469 */           if (exception instanceof InvalidKeyException) {
/* 470 */             throw (InvalidKeyException)exception;
/*     */           }
/* 472 */           throw new InvalidKeyException("Could not translate key", exception);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/KeyFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */