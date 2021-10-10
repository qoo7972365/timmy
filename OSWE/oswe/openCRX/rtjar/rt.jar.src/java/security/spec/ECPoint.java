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
/*     */ public class ECPoint
/*     */ {
/*     */   private final BigInteger x;
/*     */   private final BigInteger y;
/*  47 */   public static final ECPoint POINT_INFINITY = new ECPoint();
/*     */ 
/*     */   
/*     */   private ECPoint() {
/*  51 */     this.x = null;
/*  52 */     this.y = null;
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
/*     */   public ECPoint(BigInteger paramBigInteger1, BigInteger paramBigInteger2) {
/*  64 */     if (paramBigInteger1 == null || paramBigInteger2 == null) {
/*  65 */       throw new NullPointerException("affine coordinate x or y is null");
/*     */     }
/*  67 */     this.x = paramBigInteger1;
/*  68 */     this.y = paramBigInteger2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getAffineX() {
/*  77 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getAffineY() {
/*  86 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  97 */     if (this == paramObject) return true; 
/*  98 */     if (this == POINT_INFINITY) return false; 
/*  99 */     if (paramObject instanceof ECPoint) {
/* 100 */       return (this.x.equals(((ECPoint)paramObject).x) && this.y
/* 101 */         .equals(((ECPoint)paramObject).y));
/*     */     }
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 111 */     if (this == POINT_INFINITY) return 0; 
/* 112 */     return this.x.hashCode() << 5 + this.y.hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/spec/ECPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */