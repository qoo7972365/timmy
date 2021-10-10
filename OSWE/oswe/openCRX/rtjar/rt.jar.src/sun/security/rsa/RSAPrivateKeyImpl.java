/*    */ package sun.security.rsa;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.math.BigInteger;
/*    */ import java.security.InvalidKeyException;
/*    */ import java.security.interfaces.RSAPrivateKey;
/*    */ import sun.security.pkcs.PKCS8Key;
/*    */ import sun.security.util.DerOutputStream;
/*    */ import sun.security.util.DerValue;
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
/*    */ public final class RSAPrivateKeyImpl
/*    */   extends PKCS8Key
/*    */   implements RSAPrivateKey
/*    */ {
/*    */   private static final long serialVersionUID = -33106691987952810L;
/*    */   private final BigInteger n;
/*    */   private final BigInteger d;
/*    */   
/*    */   RSAPrivateKeyImpl(BigInteger paramBigInteger1, BigInteger paramBigInteger2) throws InvalidKeyException {
/* 62 */     this.n = paramBigInteger1;
/* 63 */     this.d = paramBigInteger2;
/* 64 */     RSAKeyFactory.checkRSAProviderKeyLengths(paramBigInteger1.bitLength(), null);
/*    */     
/* 66 */     this.algid = RSAPrivateCrtKeyImpl.rsaId;
/*    */     try {
/* 68 */       DerOutputStream derOutputStream = new DerOutputStream();
/* 69 */       derOutputStream.putInteger(0);
/* 70 */       derOutputStream.putInteger(paramBigInteger1);
/* 71 */       derOutputStream.putInteger(0);
/* 72 */       derOutputStream.putInteger(paramBigInteger2);
/* 73 */       derOutputStream.putInteger(0);
/* 74 */       derOutputStream.putInteger(0);
/* 75 */       derOutputStream.putInteger(0);
/* 76 */       derOutputStream.putInteger(0);
/* 77 */       derOutputStream.putInteger(0);
/*    */       
/* 79 */       DerValue derValue = new DerValue((byte)48, derOutputStream.toByteArray());
/* 80 */       this.key = derValue.toByteArray();
/* 81 */     } catch (IOException iOException) {
/*    */       
/* 83 */       throw new InvalidKeyException(iOException);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAlgorithm() {
/* 89 */     return "RSA";
/*    */   }
/*    */ 
/*    */   
/*    */   public BigInteger getModulus() {
/* 94 */     return this.n;
/*    */   }
/*    */ 
/*    */   
/*    */   public BigInteger getPrivateExponent() {
/* 99 */     return this.d;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/rsa/RSAPrivateKeyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */