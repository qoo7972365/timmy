/*    */ package sun.security.krb5.internal.crypto;
/*    */ 
/*    */ import java.security.GeneralSecurityException;
/*    */ import sun.security.krb5.KrbCryptoException;
/*    */ import sun.security.krb5.internal.crypto.dk.Des3DkCrypto;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Des3
/*    */ {
/* 37 */   private static final Des3DkCrypto CRYPTO = new Des3DkCrypto();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] stringToKey(char[] paramArrayOfchar) throws GeneralSecurityException {
/* 44 */     return CRYPTO.stringToKey(paramArrayOfchar);
/*    */   }
/*    */ 
/*    */   
/*    */   public static byte[] parityFix(byte[] paramArrayOfbyte) throws GeneralSecurityException {
/* 49 */     return CRYPTO.parityFix(paramArrayOfbyte);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getChecksumLength() {
/* 54 */     return CRYPTO.getChecksumLength();
/*    */   }
/*    */ 
/*    */   
/*    */   public static byte[] calculateChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3) throws GeneralSecurityException {
/* 59 */     return CRYPTO.calculateChecksum(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] encrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException, KrbCryptoException {
/* 65 */     return CRYPTO.encrypt(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, null, paramArrayOfbyte3, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] encryptRaw(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException, KrbCryptoException {
/* 73 */     return CRYPTO.encryptRaw(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] decrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException {
/* 79 */     return CRYPTO.decrypt(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] decryptRaw(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException {
/* 89 */     return CRYPTO.decryptRaw(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt2, paramInt3);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/Des3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */