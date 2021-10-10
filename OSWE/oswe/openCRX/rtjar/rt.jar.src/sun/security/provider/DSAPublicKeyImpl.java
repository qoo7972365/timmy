/*    */ package sun.security.provider;
/*    */ 
/*    */ import java.io.ObjectStreamException;
/*    */ import java.math.BigInteger;
/*    */ import java.security.InvalidKeyException;
/*    */ import java.security.KeyRep;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DSAPublicKeyImpl
/*    */   extends DSAPublicKey
/*    */ {
/*    */   private static final long serialVersionUID = 7819830118247182730L;
/*    */   
/*    */   public DSAPublicKeyImpl(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, BigInteger paramBigInteger4) throws InvalidKeyException {
/* 63 */     super(paramBigInteger1, paramBigInteger2, paramBigInteger3, paramBigInteger4);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DSAPublicKeyImpl(byte[] paramArrayOfbyte) throws InvalidKeyException {
/* 70 */     super(paramArrayOfbyte);
/*    */   }
/*    */   
/*    */   protected Object writeReplace() throws ObjectStreamException {
/* 74 */     return new KeyRep(KeyRep.Type.PUBLIC, 
/* 75 */         getAlgorithm(), 
/* 76 */         getFormat(), 
/* 77 */         getEncoded());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/DSAPublicKeyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */