/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.AlgorithmParameterGeneratorSpi;
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidParameterException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.ProviderException;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.security.spec.DSAGenParameterSpec;
/*     */ import java.security.spec.DSAParameterSpec;
/*     */ import java.security.spec.InvalidParameterSpecException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DSAParameterGenerator
/*     */   extends AlgorithmParameterGeneratorSpi
/*     */ {
/*  63 */   private int valueL = -1;
/*  64 */   private int valueN = -1;
/*  65 */   private int seedLen = -1;
/*     */ 
/*     */   
/*     */   private SecureRandom random;
/*     */ 
/*     */   
/*  71 */   private static final BigInteger TWO = BigInteger.valueOf(2L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void engineInit(int paramInt, SecureRandom paramSecureRandom) {
/*  85 */     if (paramInt != 2048 && paramInt != 3072 && (paramInt < 512 || paramInt > 1024 || paramInt % 64 != 0))
/*     */     {
/*  87 */       throw new InvalidParameterException("Unexpected strength (size of prime): " + paramInt + ". Prime size should be 512-1024, 2048, or 3072");
/*     */     }
/*     */ 
/*     */     
/*  91 */     this.valueL = paramInt;
/*  92 */     this.valueN = SecurityProviderConstants.getDefDSASubprimeSize(paramInt);
/*  93 */     this.seedLen = this.valueN;
/*  94 */     this.random = paramSecureRandom;
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
/*     */   protected void engineInit(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom) throws InvalidAlgorithmParameterException {
/* 111 */     if (!(paramAlgorithmParameterSpec instanceof DSAGenParameterSpec)) {
/* 112 */       throw new InvalidAlgorithmParameterException("Invalid parameter");
/*     */     }
/* 114 */     DSAGenParameterSpec dSAGenParameterSpec = (DSAGenParameterSpec)paramAlgorithmParameterSpec;
/*     */ 
/*     */     
/* 117 */     this.valueL = dSAGenParameterSpec.getPrimePLength();
/* 118 */     this.valueN = dSAGenParameterSpec.getSubprimeQLength();
/* 119 */     this.seedLen = dSAGenParameterSpec.getSeedLength();
/* 120 */     this.random = paramSecureRandom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AlgorithmParameters engineGenerateParameters() {
/* 130 */     AlgorithmParameters algorithmParameters = null;
/*     */     try {
/* 132 */       if (this.random == null) {
/* 133 */         this.random = new SecureRandom();
/*     */       }
/* 135 */       if (this.valueL == -1) {
/* 136 */         engineInit(SecurityProviderConstants.DEF_DSA_KEY_SIZE, this.random);
/*     */       }
/* 138 */       BigInteger[] arrayOfBigInteger = generatePandQ(this.random, this.valueL, this.valueN, this.seedLen);
/*     */       
/* 140 */       BigInteger bigInteger1 = arrayOfBigInteger[0];
/* 141 */       BigInteger bigInteger2 = arrayOfBigInteger[1];
/* 142 */       BigInteger bigInteger3 = generateG(bigInteger1, bigInteger2);
/*     */       
/* 144 */       DSAParameterSpec dSAParameterSpec = new DSAParameterSpec(bigInteger1, bigInteger2, bigInteger3);
/*     */       
/* 146 */       algorithmParameters = AlgorithmParameters.getInstance("DSA", "SUN");
/* 147 */       algorithmParameters.init(dSAParameterSpec);
/* 148 */     } catch (InvalidParameterSpecException invalidParameterSpecException) {
/*     */       
/* 150 */       throw new RuntimeException(invalidParameterSpecException.getMessage());
/* 151 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*     */       
/* 153 */       throw new RuntimeException(noSuchAlgorithmException.getMessage());
/* 154 */     } catch (NoSuchProviderException noSuchProviderException) {
/*     */       
/* 156 */       throw new RuntimeException(noSuchProviderException.getMessage());
/*     */     } 
/*     */     
/* 159 */     return algorithmParameters;
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
/*     */   private static BigInteger[] generatePandQ(SecureRandom paramSecureRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 180 */     String str = null;
/* 181 */     if (paramInt2 == 160) {
/* 182 */       str = "SHA";
/* 183 */     } else if (paramInt2 == 224) {
/* 184 */       str = "SHA-224";
/* 185 */     } else if (paramInt2 == 256) {
/* 186 */       str = "SHA-256";
/*     */     } 
/* 188 */     MessageDigest messageDigest = null;
/*     */     try {
/* 190 */       messageDigest = MessageDigest.getInstance(str);
/* 191 */     } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*     */       
/* 193 */       noSuchAlgorithmException.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 197 */     int i = messageDigest.getDigestLength() * 8;
/* 198 */     int j = (paramInt1 - 1) / i;
/* 199 */     int k = (paramInt1 - 1) % i;
/* 200 */     byte[] arrayOfByte = new byte[paramInt3 / 8];
/* 201 */     BigInteger bigInteger1 = TWO.pow(paramInt3);
/* 202 */     short s = -1;
/* 203 */     if (paramInt1 <= 1024) {
/* 204 */       s = 80;
/* 205 */     } else if (paramInt1 == 2048) {
/* 206 */       s = 112;
/* 207 */     } else if (paramInt1 == 3072) {
/* 208 */       s = 128;
/*     */     } 
/* 210 */     if (s < 0) {
/* 211 */       throw new ProviderException("Invalid valueL: " + paramInt1);
/*     */     }
/* 213 */     BigInteger bigInteger2 = null;
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 218 */       paramSecureRandom.nextBytes(arrayOfByte);
/* 219 */       bigInteger2 = new BigInteger(1, arrayOfByte);
/*     */ 
/*     */ 
/*     */       
/* 223 */       BigInteger bigInteger4 = (new BigInteger(1, messageDigest.digest(arrayOfByte))).mod(TWO.pow(paramInt2 - 1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 229 */       BigInteger bigInteger3 = TWO.pow(paramInt2 - 1).add(bigInteger4).add(BigInteger.ONE).subtract(bigInteger4.mod(TWO));
/* 230 */       if (bigInteger3.isProbablePrime(s)) {
/*     */ 
/*     */         
/* 233 */         bigInteger4 = BigInteger.ONE;
/*     */         
/* 235 */         for (byte b = 0; b < 4 * paramInt1; b++) {
/* 236 */           BigInteger[] arrayOfBigInteger = new BigInteger[j + 1];
/*     */           
/* 238 */           for (byte b1 = 0; b1 <= j; b1++) {
/* 239 */             BigInteger bigInteger10 = BigInteger.valueOf(b1);
/* 240 */             BigInteger bigInteger11 = bigInteger2.add(bigInteger4).add(bigInteger10).mod(bigInteger1);
/* 241 */             byte[] arrayOfByte1 = messageDigest.digest(toByteArray(bigInteger11));
/* 242 */             arrayOfBigInteger[b1] = new BigInteger(1, arrayOfByte1);
/*     */           } 
/*     */           
/* 245 */           BigInteger bigInteger6 = arrayOfBigInteger[0];
/* 246 */           for (byte b2 = 1; b2 < j; b2++) {
/* 247 */             bigInteger6 = bigInteger6.add(arrayOfBigInteger[b2].multiply(TWO.pow(b2 * i)));
/*     */           }
/* 249 */           bigInteger6 = bigInteger6.add(arrayOfBigInteger[j].mod(TWO.pow(k))
/* 250 */               .multiply(TWO.pow(j * i)));
/*     */           
/* 252 */           BigInteger bigInteger7 = TWO.pow(paramInt1 - 1);
/* 253 */           BigInteger bigInteger8 = bigInteger6.add(bigInteger7);
/*     */           
/* 255 */           BigInteger bigInteger9 = bigInteger8.mod(bigInteger3.multiply(TWO));
/* 256 */           BigInteger bigInteger5 = bigInteger8.subtract(bigInteger9.subtract(BigInteger.ONE));
/*     */           
/* 258 */           if (bigInteger5.compareTo(bigInteger7) > -1 && bigInteger5
/* 259 */             .isProbablePrime(s))
/*     */           {
/* 261 */             return new BigInteger[] { bigInteger5, bigInteger3, bigInteger2, 
/* 262 */                 BigInteger.valueOf(b) };
/*     */           }
/*     */ 
/*     */           
/* 266 */           bigInteger4 = bigInteger4.add(BigInteger.valueOf(j)).add(BigInteger.ONE);
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
/*     */   private static BigInteger generateG(BigInteger paramBigInteger1, BigInteger paramBigInteger2) {
/* 281 */     BigInteger bigInteger1 = BigInteger.ONE;
/*     */     
/* 283 */     BigInteger bigInteger2 = paramBigInteger1.subtract(BigInteger.ONE).divide(paramBigInteger2);
/* 284 */     BigInteger bigInteger3 = BigInteger.ONE;
/* 285 */     while (bigInteger3.compareTo(TWO) < 0) {
/*     */       
/* 287 */       bigInteger3 = bigInteger1.modPow(bigInteger2, paramBigInteger1);
/* 288 */       bigInteger1 = bigInteger1.add(BigInteger.ONE);
/*     */     } 
/* 290 */     return bigInteger3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] toByteArray(BigInteger paramBigInteger) {
/* 298 */     byte[] arrayOfByte = paramBigInteger.toByteArray();
/* 299 */     if (arrayOfByte[0] == 0) {
/* 300 */       byte[] arrayOfByte1 = new byte[arrayOfByte.length - 1];
/* 301 */       System.arraycopy(arrayOfByte, 1, arrayOfByte1, 0, arrayOfByte1.length);
/* 302 */       arrayOfByte = arrayOfByte1;
/*     */     } 
/* 304 */     return arrayOfByte;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/DSAParameterGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */