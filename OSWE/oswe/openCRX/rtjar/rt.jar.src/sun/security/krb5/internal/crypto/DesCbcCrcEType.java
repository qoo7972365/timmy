/*    */ package sun.security.krb5.internal.crypto;
/*    */ 
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
/*    */ public class DesCbcCrcEType
/*    */   extends DesCbcEType
/*    */ {
/*    */   public int eType() {
/* 44 */     return 1;
/*    */   }
/*    */   
/*    */   public int minimumPadSize() {
/* 48 */     return 4;
/*    */   }
/*    */   
/*    */   public int confounderSize() {
/* 52 */     return 8;
/*    */   }
/*    */   
/*    */   public int checksumType() {
/* 56 */     return 7;
/*    */   }
/*    */   
/*    */   public int checksumSize() {
/* 60 */     return 4;
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
/*    */   public byte[] encrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) throws KrbCryptoException {
/* 73 */     return encrypt(paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte2, paramInt);
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
/*    */   public byte[] decrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) throws KrbApErrException, KrbCryptoException {
/* 85 */     return decrypt(paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte2, paramInt);
/*    */   }
/*    */   
/*    */   protected byte[] calculateChecksum(byte[] paramArrayOfbyte, int paramInt) {
/* 89 */     return crc32.byte2crc32sum_bytes(paramArrayOfbyte, paramInt);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/DesCbcCrcEType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */