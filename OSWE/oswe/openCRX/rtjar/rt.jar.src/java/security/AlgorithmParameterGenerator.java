/*     */ package java.security;
/*     */ 
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AlgorithmParameterGenerator
/*     */ {
/*     */   private Provider provider;
/*     */   private AlgorithmParameterGeneratorSpi paramGenSpi;
/*     */   private String algorithm;
/*     */   
/*     */   protected AlgorithmParameterGenerator(AlgorithmParameterGeneratorSpi paramAlgorithmParameterGeneratorSpi, Provider paramProvider, String paramString) {
/* 113 */     this.paramGenSpi = paramAlgorithmParameterGeneratorSpi;
/* 114 */     this.provider = paramProvider;
/* 115 */     this.algorithm = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getAlgorithm() {
/* 125 */     return this.algorithm;
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
/*     */   public static AlgorithmParameterGenerator getInstance(String paramString) throws NoSuchAlgorithmException {
/*     */     try {
/* 159 */       Object[] arrayOfObject = Security.getImpl(paramString, "AlgorithmParameterGenerator", (String)null);
/*     */ 
/*     */       
/* 162 */       return new AlgorithmParameterGenerator((AlgorithmParameterGeneratorSpi)arrayOfObject[0], (Provider)arrayOfObject[1], paramString);
/*     */ 
/*     */     
/*     */     }
/* 166 */     catch (NoSuchProviderException noSuchProviderException) {
/* 167 */       throw new NoSuchAlgorithmException(paramString + " not found");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AlgorithmParameterGenerator getInstance(String paramString1, String paramString2) throws NoSuchAlgorithmException, NoSuchProviderException {
/* 210 */     if (paramString2 == null || paramString2.length() == 0)
/* 211 */       throw new IllegalArgumentException("missing provider"); 
/* 212 */     Object[] arrayOfObject = Security.getImpl(paramString1, "AlgorithmParameterGenerator", paramString2);
/*     */ 
/*     */     
/* 215 */     return new AlgorithmParameterGenerator((AlgorithmParameterGeneratorSpi)arrayOfObject[0], (Provider)arrayOfObject[1], paramString1);
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
/*     */   public static AlgorithmParameterGenerator getInstance(String paramString, Provider paramProvider) throws NoSuchAlgorithmException {
/* 254 */     if (paramProvider == null)
/* 255 */       throw new IllegalArgumentException("missing provider"); 
/* 256 */     Object[] arrayOfObject = Security.getImpl(paramString, "AlgorithmParameterGenerator", paramProvider);
/*     */ 
/*     */     
/* 259 */     return new AlgorithmParameterGenerator((AlgorithmParameterGeneratorSpi)arrayOfObject[0], (Provider)arrayOfObject[1], paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Provider getProvider() {
/* 270 */     return this.provider;
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
/*     */   public final void init(int paramInt) {
/* 285 */     this.paramGenSpi.engineInit(paramInt, new SecureRandom());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void init(int paramInt, SecureRandom paramSecureRandom) {
/* 296 */     this.paramGenSpi.engineInit(paramInt, paramSecureRandom);
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
/*     */   public final void init(AlgorithmParameterSpec paramAlgorithmParameterSpec) throws InvalidAlgorithmParameterException {
/* 316 */     this.paramGenSpi.engineInit(paramAlgorithmParameterSpec, new SecureRandom());
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
/*     */   public final void init(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom) throws InvalidAlgorithmParameterException {
/* 332 */     this.paramGenSpi.engineInit(paramAlgorithmParameterSpec, paramSecureRandom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final AlgorithmParameters generateParameters() {
/* 341 */     return this.paramGenSpi.engineGenerateParameters();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/AlgorithmParameterGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */