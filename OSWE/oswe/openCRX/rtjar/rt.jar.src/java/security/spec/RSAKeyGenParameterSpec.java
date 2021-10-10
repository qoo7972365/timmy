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
/*    */ public class RSAKeyGenParameterSpec
/*    */   implements AlgorithmParameterSpec
/*    */ {
/*    */   private int keysize;
/*    */   private BigInteger publicExponent;
/* 50 */   public static final BigInteger F0 = BigInteger.valueOf(3L);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public static final BigInteger F4 = BigInteger.valueOf(65537L);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RSAKeyGenParameterSpec(int paramInt, BigInteger paramBigInteger) {
/* 65 */     this.keysize = paramInt;
/* 66 */     this.publicExponent = paramBigInteger;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getKeysize() {
/* 75 */     return this.keysize;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BigInteger getPublicExponent() {
/* 84 */     return this.publicExponent;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/spec/RSAKeyGenParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */