/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.InvalidParameterException;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.ProviderException;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.interfaces.DSAParams;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.security.spec.DSAParameterSpec;
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
/*     */ 
/*     */ 
/*     */ class DSAKeyPairGenerator
/*     */   extends KeyPairGenerator
/*     */ {
/*     */   private int plen;
/*     */   private int qlen;
/*     */   boolean forceNewParameters;
/*     */   private DSAParameterSpec params;
/*     */   private SecureRandom random;
/*     */   
/*     */   DSAKeyPairGenerator(int paramInt) {
/*  66 */     super("DSA");
/*  67 */     initialize(paramInt, (SecureRandom)null);
/*     */   }
/*     */   
/*     */   private static void checkStrength(int paramInt1, int paramInt2) {
/*  71 */     if (paramInt1 < 512 || paramInt1 > 1024 || paramInt1 % 64 != 0 || paramInt2 != 160)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/*  76 */       if (paramInt1 != 2048 || (paramInt2 != 224 && paramInt2 != 256))
/*     */       {
/*  78 */         if (paramInt1 != 3072 || paramInt2 != 256)
/*     */         {
/*     */           
/*  81 */           throw new InvalidParameterException("Unsupported prime and subprime size combination: " + paramInt1 + ", " + paramInt2);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void initialize(int paramInt, SecureRandom paramSecureRandom) {
/*  88 */     init(paramInt, paramSecureRandom, false);
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
/*     */   public void initialize(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom) throws InvalidAlgorithmParameterException {
/* 103 */     if (!(paramAlgorithmParameterSpec instanceof DSAParameterSpec)) {
/* 104 */       throw new InvalidAlgorithmParameterException("Inappropriate parameter");
/*     */     }
/*     */     
/* 107 */     init((DSAParameterSpec)paramAlgorithmParameterSpec, paramSecureRandom, false);
/*     */   }
/*     */   
/*     */   void init(int paramInt, SecureRandom paramSecureRandom, boolean paramBoolean) {
/* 111 */     int i = SecurityProviderConstants.getDefDSASubprimeSize(paramInt);
/* 112 */     checkStrength(paramInt, i);
/* 113 */     this.plen = paramInt;
/* 114 */     this.qlen = i;
/* 115 */     this.params = null;
/* 116 */     this.random = paramSecureRandom;
/* 117 */     this.forceNewParameters = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   void init(DSAParameterSpec paramDSAParameterSpec, SecureRandom paramSecureRandom, boolean paramBoolean) {
/* 122 */     int i = paramDSAParameterSpec.getP().bitLength();
/* 123 */     int j = paramDSAParameterSpec.getQ().bitLength();
/* 124 */     checkStrength(i, j);
/* 125 */     this.plen = i;
/* 126 */     this.qlen = j;
/* 127 */     this.params = paramDSAParameterSpec;
/* 128 */     this.random = paramSecureRandom;
/* 129 */     this.forceNewParameters = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyPair generateKeyPair() {
/*     */     DSAParameterSpec dSAParameterSpec;
/* 137 */     if (this.random == null) {
/* 138 */       this.random = JCAUtil.getSecureRandom();
/*     */     }
/*     */     
/*     */     try {
/* 142 */       if (this.forceNewParameters) {
/*     */         
/* 144 */         dSAParameterSpec = ParameterCache.getNewDSAParameterSpec(this.plen, this.qlen, this.random);
/*     */       } else {
/* 146 */         if (this.params == null) {
/* 147 */           this
/* 148 */             .params = ParameterCache.getDSAParameterSpec(this.plen, this.qlen, this.random);
/*     */         }
/* 150 */         dSAParameterSpec = this.params;
/*     */       } 
/* 152 */     } catch (GeneralSecurityException generalSecurityException) {
/* 153 */       throw new ProviderException(generalSecurityException);
/*     */     } 
/* 155 */     return generateKeyPair(dSAParameterSpec.getP(), dSAParameterSpec.getQ(), dSAParameterSpec.getG(), this.random);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private KeyPair generateKeyPair(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, SecureRandom paramSecureRandom) {
/* 161 */     BigInteger bigInteger1 = generateX(paramSecureRandom, paramBigInteger2);
/* 162 */     BigInteger bigInteger2 = generateY(bigInteger1, paramBigInteger1, paramBigInteger3);
/*     */ 
/*     */     
/*     */     try {
/*     */       DSAPublicKey dSAPublicKey;
/*     */ 
/*     */       
/* 169 */       if (DSAKeyFactory.SERIAL_INTEROP) {
/* 170 */         dSAPublicKey = new DSAPublicKey(bigInteger2, paramBigInteger1, paramBigInteger2, paramBigInteger3);
/*     */       } else {
/* 172 */         dSAPublicKey = new DSAPublicKeyImpl(bigInteger2, paramBigInteger1, paramBigInteger2, paramBigInteger3);
/*     */       } 
/* 174 */       DSAPrivateKey dSAPrivateKey = new DSAPrivateKey(bigInteger1, paramBigInteger1, paramBigInteger2, paramBigInteger3);
/*     */       
/* 176 */       return new KeyPair(dSAPublicKey, dSAPrivateKey);
/*     */     }
/* 178 */     catch (InvalidKeyException invalidKeyException) {
/* 179 */       throw new ProviderException(invalidKeyException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BigInteger generateX(SecureRandom paramSecureRandom, BigInteger paramBigInteger) {
/* 190 */     BigInteger bigInteger = null;
/* 191 */     byte[] arrayOfByte = new byte[this.qlen];
/*     */     while (true) {
/* 193 */       paramSecureRandom.nextBytes(arrayOfByte);
/* 194 */       bigInteger = (new BigInteger(1, arrayOfByte)).mod(paramBigInteger);
/* 195 */       if (bigInteger.signum() > 0 && bigInteger.compareTo(paramBigInteger) < 0) {
/* 196 */         return bigInteger;
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
/*     */   BigInteger generateY(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3) {
/* 209 */     return paramBigInteger3.modPow(paramBigInteger1, paramBigInteger2);
/*     */   }
/*     */   
/*     */   public static final class Current
/*     */     extends DSAKeyPairGenerator {
/*     */     public Current() {
/* 215 */       super(SecurityProviderConstants.DEF_DSA_KEY_SIZE);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Legacy
/*     */     extends DSAKeyPairGenerator
/*     */     implements java.security.interfaces.DSAKeyPairGenerator {
/*     */     public Legacy() {
/* 223 */       super(1024);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void initialize(int param1Int, boolean param1Boolean, SecureRandom param1SecureRandom) throws InvalidParameterException {
/* 233 */       if (param1Boolean) {
/* 234 */         init(param1Int, param1SecureRandom, true);
/*     */       } else {
/*     */         
/* 237 */         DSAParameterSpec dSAParameterSpec = ParameterCache.getCachedDSAParameterSpec(param1Int, 
/* 238 */             SecurityProviderConstants.getDefDSASubprimeSize(param1Int));
/* 239 */         if (dSAParameterSpec == null) {
/* 240 */           throw new InvalidParameterException("No precomputed parameters for requested modulus size available");
/*     */         }
/*     */ 
/*     */         
/* 244 */         init(dSAParameterSpec, param1SecureRandom, false);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void initialize(DSAParams param1DSAParams, SecureRandom param1SecureRandom) throws InvalidParameterException {
/* 256 */       if (param1DSAParams == null) {
/* 257 */         throw new InvalidParameterException("Params must not be null");
/*     */       }
/*     */       
/* 260 */       DSAParameterSpec dSAParameterSpec = new DSAParameterSpec(param1DSAParams.getP(), param1DSAParams.getQ(), param1DSAParams.getG());
/* 261 */       init(dSAParameterSpec, param1SecureRandom, false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/DSAKeyPairGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */