/*     */ package java.math;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SignedMutableBigInteger
/*     */   extends MutableBigInteger
/*     */ {
/*  52 */   int sign = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SignedMutableBigInteger() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SignedMutableBigInteger(int paramInt) {
/*  69 */     super(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SignedMutableBigInteger(MutableBigInteger paramMutableBigInteger) {
/*  77 */     super(paramMutableBigInteger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void signedAdd(SignedMutableBigInteger paramSignedMutableBigInteger) {
/*  86 */     if (this.sign == paramSignedMutableBigInteger.sign) {
/*  87 */       add(paramSignedMutableBigInteger);
/*     */     } else {
/*  89 */       this.sign *= subtract(paramSignedMutableBigInteger);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void signedAdd(MutableBigInteger paramMutableBigInteger) {
/*  97 */     if (this.sign == 1) {
/*  98 */       add(paramMutableBigInteger);
/*     */     } else {
/* 100 */       this.sign *= subtract(paramMutableBigInteger);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void signedSubtract(SignedMutableBigInteger paramSignedMutableBigInteger) {
/* 108 */     if (this.sign == paramSignedMutableBigInteger.sign) {
/* 109 */       this.sign *= subtract(paramSignedMutableBigInteger);
/*     */     } else {
/* 111 */       add(paramSignedMutableBigInteger);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void signedSubtract(MutableBigInteger paramMutableBigInteger) {
/* 119 */     if (this.sign == 1) {
/* 120 */       this.sign *= subtract(paramMutableBigInteger);
/*     */     } else {
/* 122 */       add(paramMutableBigInteger);
/* 123 */     }  if (this.intLen == 0) {
/* 124 */       this.sign = 1;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 132 */     return toBigInteger(this.sign).toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/math/SignedMutableBigInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */