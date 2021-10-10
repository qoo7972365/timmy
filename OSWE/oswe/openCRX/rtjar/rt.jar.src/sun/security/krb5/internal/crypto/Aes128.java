/*    */ package sun.security.krb5.internal.crypto;
/*    */ 
/*    */ import java.security.GeneralSecurityException;
/*    */ import sun.security.krb5.KrbCryptoException;
/*    */ import sun.security.krb5.internal.crypto.dk.AesDkCrypto;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Aes128
/*    */ {
/* 39 */   private static final AesDkCrypto CRYPTO = new AesDkCrypto(128);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] stringToKey(char[] paramArrayOfchar, String paramString, byte[] paramArrayOfbyte) throws GeneralSecurityException {
/* 46 */     return CRYPTO.stringToKey(paramArrayOfchar, paramString, paramArrayOfbyte);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getChecksumLength() {
/* 51 */     return CRYPTO.getChecksumLength();
/*    */   }
/*    */ 
/*    */   
/*    */   public static byte[] calculateChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3) throws GeneralSecurityException {
/* 56 */     return CRYPTO.calculateChecksum(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] encrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException, KrbCryptoException {
/* 62 */     return CRYPTO.encrypt(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, null, paramArrayOfbyte3, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] encryptRaw(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException, KrbCryptoException {
/* 70 */     return CRYPTO.encryptRaw(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] decrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException {
/* 76 */     return CRYPTO.decrypt(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] decryptRaw(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException {
/* 83 */     return CRYPTO.decryptRaw(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt2, paramInt3);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/Aes128.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */