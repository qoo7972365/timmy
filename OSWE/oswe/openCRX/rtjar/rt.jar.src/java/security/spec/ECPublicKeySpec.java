/*    */ package java.security.spec;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ECPublicKeySpec
/*    */   implements KeySpec
/*    */ {
/*    */   private ECPoint w;
/*    */   private ECParameterSpec params;
/*    */   
/*    */   public ECPublicKeySpec(ECPoint paramECPoint, ECParameterSpec paramECParameterSpec) {
/* 56 */     if (paramECPoint == null) {
/* 57 */       throw new NullPointerException("w is null");
/*    */     }
/* 59 */     if (paramECParameterSpec == null) {
/* 60 */       throw new NullPointerException("params is null");
/*    */     }
/* 62 */     if (paramECPoint == ECPoint.POINT_INFINITY) {
/* 63 */       throw new IllegalArgumentException("w is ECPoint.POINT_INFINITY");
/*    */     }
/* 65 */     this.w = paramECPoint;
/* 66 */     this.params = paramECParameterSpec;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ECPoint getW() {
/* 74 */     return this.w;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ECParameterSpec getParams() {
/* 83 */     return this.params;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/spec/ECPublicKeySpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */