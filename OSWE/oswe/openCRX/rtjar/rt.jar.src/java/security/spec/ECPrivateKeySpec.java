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
/*    */ 
/*    */ public class ECPrivateKeySpec
/*    */   implements KeySpec
/*    */ {
/*    */   private BigInteger s;
/*    */   private ECParameterSpec params;
/*    */   
/*    */   public ECPrivateKeySpec(BigInteger paramBigInteger, ECParameterSpec paramECParameterSpec) {
/* 55 */     if (paramBigInteger == null) {
/* 56 */       throw new NullPointerException("s is null");
/*    */     }
/* 58 */     if (paramECParameterSpec == null) {
/* 59 */       throw new NullPointerException("params is null");
/*    */     }
/* 61 */     this.s = paramBigInteger;
/* 62 */     this.params = paramECParameterSpec;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BigInteger getS() {
/* 70 */     return this.s;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ECParameterSpec getParams() {
/* 79 */     return this.params;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/spec/ECPrivateKeySpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */