/*    */ package java.security.spec;
/*    */ 
/*    */ import java.math.BigInteger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ECFieldFp
/*    */   implements ECField
/*    */ {
/*    */   private BigInteger p;
/*    */   
/*    */   public ECFieldFp(BigInteger paramBigInteger) {
/* 53 */     if (paramBigInteger.signum() != 1) {
/* 54 */       throw new IllegalArgumentException("p is not positive");
/*    */     }
/* 56 */     this.p = paramBigInteger;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFieldSize() {
/* 65 */     return this.p.bitLength();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BigInteger getP() {
/* 73 */     return this.p;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 84 */     if (this == paramObject) return true; 
/* 85 */     if (paramObject instanceof ECFieldFp) {
/* 86 */       return this.p.equals(((ECFieldFp)paramObject).p);
/*    */     }
/* 88 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 96 */     return this.p.hashCode();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/spec/ECFieldFp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */