/*    */ package sun.security.krb5.internal.crypto;
/*    */ 
/*    */ import java.security.MessageDigest;
/*    */ import sun.security.krb5.KrbCryptoException;
/*    */ import sun.security.krb5.internal.KrbApErrException;
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
/*    */ public final class DesCbcMd5EType
/*    */   extends DesCbcEType
/*    */ {
/*    */   public int eType() {
/* 47 */     return 3;
/*    */   }
/*    */   
/*    */   public int minimumPadSize() {
/* 51 */     return 0;
/*    */   }
/*    */   
/*    */   public int confounderSize() {
/* 55 */     return 8;
/*    */   }
/*    */   
/*    */   public int checksumType() {
/* 59 */     return 7;
/*    */   }
/*    */   
/*    */   public int checksumSize() {
/* 63 */     return 16;
/*    */   }
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
/*    */   protected byte[] calculateChecksum(byte[] paramArrayOfbyte, int paramInt) throws KrbCryptoException {
/* 76 */     MessageDigest messageDigest = null;
/*    */     try {
/* 78 */       messageDigest = MessageDigest.getInstance("MD5");
/* 79 */     } catch (Exception exception) {
/* 80 */       throw new KrbCryptoException("JCE provider may not be installed. " + exception.getMessage());
/*    */     } 
/*    */     try {
/* 83 */       messageDigest.update(paramArrayOfbyte);
/* 84 */       return messageDigest.digest();
/* 85 */     } catch (Exception exception) {
/* 86 */       throw new KrbCryptoException(exception.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/DesCbcMd5EType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */