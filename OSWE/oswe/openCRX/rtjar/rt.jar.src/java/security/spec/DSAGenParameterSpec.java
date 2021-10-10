/*     */ package java.security.spec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DSAGenParameterSpec
/*     */   implements AlgorithmParameterSpec
/*     */ {
/*     */   private final int pLen;
/*     */   private final int qLen;
/*     */   private final int seedLen;
/*     */   
/*     */   public DSAGenParameterSpec(int paramInt1, int paramInt2) {
/*  54 */     this(paramInt1, paramInt2, paramInt2);
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
/*     */   public DSAGenParameterSpec(int paramInt1, int paramInt2, int paramInt3) {
/*  70 */     switch (paramInt1) {
/*     */       case 1024:
/*  72 */         if (paramInt2 != 160) {
/*  73 */           throw new IllegalArgumentException("subprimeQLen must be 160 when primePLen=1024");
/*     */         }
/*     */         break;
/*     */       
/*     */       case 2048:
/*  78 */         if (paramInt2 != 224 && paramInt2 != 256) {
/*  79 */           throw new IllegalArgumentException("subprimeQLen must be 224 or 256 when primePLen=2048");
/*     */         }
/*     */         break;
/*     */       
/*     */       case 3072:
/*  84 */         if (paramInt2 != 256) {
/*  85 */           throw new IllegalArgumentException("subprimeQLen must be 256 when primePLen=3072");
/*     */         }
/*     */         break;
/*     */       
/*     */       default:
/*  90 */         throw new IllegalArgumentException("primePLen must be 1024, 2048, or 3072");
/*     */     } 
/*     */     
/*  93 */     if (paramInt3 < paramInt2) {
/*  94 */       throw new IllegalArgumentException("seedLen must be equal to or greater than subprimeQLen");
/*     */     }
/*     */     
/*  97 */     this.pLen = paramInt1;
/*  98 */     this.qLen = paramInt2;
/*  99 */     this.seedLen = paramInt3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrimePLength() {
/* 108 */     return this.pLen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSubprimeQLength() {
/* 117 */     return this.qLen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeedLength() {
/* 125 */     return this.seedLen;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/spec/DSAGenParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */