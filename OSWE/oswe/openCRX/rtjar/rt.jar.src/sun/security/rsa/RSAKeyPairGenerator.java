/*     */ package sun.security.rsa;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.InvalidParameterException;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGeneratorSpi;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.security.spec.RSAKeyGenParameterSpec;
/*     */ import sun.security.jca.JCAUtil;
/*     */ import sun.security.util.SecurityProviderConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RSAKeyPairGenerator
/*     */   extends KeyPairGeneratorSpi
/*     */ {
/*     */   private BigInteger publicExponent;
/*     */   private int keySize;
/*     */   private SecureRandom random;
/*     */   
/*     */   public RSAKeyPairGenerator() {
/*  59 */     initialize(SecurityProviderConstants.DEF_RSA_KEY_SIZE, (SecureRandom)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(int paramInt, SecureRandom paramSecureRandom) {
/*     */     try {
/*  68 */       RSAKeyFactory.checkKeyLengths(paramInt, RSAKeyGenParameterSpec.F4, 512, 65536);
/*     */     }
/*  70 */     catch (InvalidKeyException invalidKeyException) {
/*  71 */       throw new InvalidParameterException(invalidKeyException.getMessage());
/*     */     } 
/*     */     
/*  74 */     this.keySize = paramInt;
/*  75 */     this.random = paramSecureRandom;
/*  76 */     this.publicExponent = RSAKeyGenParameterSpec.F4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom) throws InvalidAlgorithmParameterException {
/*  83 */     if (!(paramAlgorithmParameterSpec instanceof RSAKeyGenParameterSpec)) {
/*  84 */       throw new InvalidAlgorithmParameterException("Params must be instance of RSAKeyGenParameterSpec");
/*     */     }
/*     */ 
/*     */     
/*  88 */     RSAKeyGenParameterSpec rSAKeyGenParameterSpec = (RSAKeyGenParameterSpec)paramAlgorithmParameterSpec;
/*  89 */     int i = rSAKeyGenParameterSpec.getKeysize();
/*  90 */     BigInteger bigInteger = rSAKeyGenParameterSpec.getPublicExponent();
/*     */     
/*  92 */     if (bigInteger == null) {
/*  93 */       bigInteger = RSAKeyGenParameterSpec.F4;
/*     */     } else {
/*  95 */       if (bigInteger.compareTo(RSAKeyGenParameterSpec.F0) < 0) {
/*  96 */         throw new InvalidAlgorithmParameterException("Public exponent must be 3 or larger");
/*     */       }
/*     */       
/*  99 */       if (bigInteger.bitLength() > i) {
/* 100 */         throw new InvalidAlgorithmParameterException("Public exponent must be smaller than key size");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 107 */       RSAKeyFactory.checkKeyLengths(i, bigInteger, 512, 65536);
/*     */     }
/* 109 */     catch (InvalidKeyException invalidKeyException) {
/* 110 */       throw new InvalidAlgorithmParameterException("Invalid key sizes", invalidKeyException);
/*     */     } 
/*     */ 
/*     */     
/* 114 */     this.keySize = i;
/* 115 */     this.publicExponent = bigInteger;
/* 116 */     this.random = paramSecureRandom;
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyPair generateKeyPair() {
/*     */     BigInteger bigInteger2, bigInteger3, bigInteger4, bigInteger5, bigInteger6, bigInteger7;
/* 122 */     int i = this.keySize + 1 >> 1;
/* 123 */     int j = this.keySize - i;
/* 124 */     if (this.random == null) {
/* 125 */       this.random = JCAUtil.getSecureRandom();
/*     */     }
/* 127 */     BigInteger bigInteger1 = this.publicExponent;
/*     */     
/*     */     while (true) {
/* 130 */       bigInteger2 = BigInteger.probablePrime(i, this.random);
/*     */       
/*     */       do {
/* 133 */         bigInteger3 = BigInteger.probablePrime(j, this.random);
/*     */         
/* 135 */         if (bigInteger2.compareTo(bigInteger3) < 0) {
/* 136 */           BigInteger bigInteger = bigInteger2;
/* 137 */           bigInteger2 = bigInteger3;
/* 138 */           bigInteger3 = bigInteger;
/*     */         } 
/*     */         
/* 141 */         bigInteger4 = bigInteger2.multiply(bigInteger3);
/*     */       
/*     */       }
/* 144 */       while (bigInteger4.bitLength() < this.keySize);
/*     */ 
/*     */ 
/*     */       
/* 148 */       bigInteger5 = bigInteger2.subtract(BigInteger.ONE);
/* 149 */       bigInteger6 = bigInteger3.subtract(BigInteger.ONE);
/* 150 */       bigInteger7 = bigInteger5.multiply(bigInteger6);
/*     */ 
/*     */       
/* 153 */       if (!bigInteger1.gcd(bigInteger7).equals(BigInteger.ONE)) {
/*     */         continue;
/*     */       }
/*     */       break;
/*     */     } 
/* 158 */     BigInteger bigInteger8 = bigInteger1.modInverse(bigInteger7);
/*     */ 
/*     */     
/* 161 */     BigInteger bigInteger9 = bigInteger8.mod(bigInteger5);
/*     */     
/* 163 */     BigInteger bigInteger10 = bigInteger8.mod(bigInteger6);
/*     */ 
/*     */     
/* 166 */     BigInteger bigInteger11 = bigInteger3.modInverse(bigInteger2);
/*     */     
/*     */     try {
/* 169 */       RSAPublicKeyImpl rSAPublicKeyImpl = new RSAPublicKeyImpl(bigInteger4, bigInteger1);
/* 170 */       RSAPrivateCrtKeyImpl rSAPrivateCrtKeyImpl = new RSAPrivateCrtKeyImpl(bigInteger4, bigInteger1, bigInteger8, bigInteger2, bigInteger3, bigInteger9, bigInteger10, bigInteger11);
/*     */       
/* 172 */       return new KeyPair(rSAPublicKeyImpl, rSAPrivateCrtKeyImpl);
/* 173 */     } catch (InvalidKeyException invalidKeyException) {
/*     */ 
/*     */       
/* 176 */       throw new RuntimeException(invalidKeyException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/rsa/RSAKeyPairGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */