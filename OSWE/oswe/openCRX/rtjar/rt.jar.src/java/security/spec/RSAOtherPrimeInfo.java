/*     */ package java.security.spec;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RSAOtherPrimeInfo
/*     */ {
/*     */   private BigInteger prime;
/*     */   private BigInteger primeExponent;
/*     */   private BigInteger crtCoefficient;
/*     */   
/*     */   public RSAOtherPrimeInfo(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3) {
/*  77 */     if (paramBigInteger1 == null) {
/*  78 */       throw new NullPointerException("the prime parameter must be non-null");
/*     */     }
/*     */     
/*  81 */     if (paramBigInteger2 == null) {
/*  82 */       throw new NullPointerException("the primeExponent parameter must be non-null");
/*     */     }
/*     */     
/*  85 */     if (paramBigInteger3 == null) {
/*  86 */       throw new NullPointerException("the crtCoefficient parameter must be non-null");
/*     */     }
/*     */     
/*  89 */     this.prime = paramBigInteger1;
/*  90 */     this.primeExponent = paramBigInteger2;
/*  91 */     this.crtCoefficient = paramBigInteger3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final BigInteger getPrime() {
/* 100 */     return this.prime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final BigInteger getExponent() {
/* 109 */     return this.primeExponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final BigInteger getCrtCoefficient() {
/* 118 */     return this.crtCoefficient;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/spec/RSAOtherPrimeInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */