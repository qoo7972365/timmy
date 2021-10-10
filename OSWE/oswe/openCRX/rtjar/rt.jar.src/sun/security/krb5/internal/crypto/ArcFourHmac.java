/*    */ package sun.security.krb5.internal.crypto;
/*    */ 
/*    */ import java.security.GeneralSecurityException;
/*    */ import sun.security.krb5.KrbCryptoException;
/*    */ import sun.security.krb5.internal.crypto.dk.ArcFourCrypto;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArcFourHmac
/*    */ {
/* 40 */   private static final ArcFourCrypto CRYPTO = new ArcFourCrypto(128);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] stringToKey(char[] paramArrayOfchar) throws GeneralSecurityException {
/* 47 */     return CRYPTO.stringToKey(paramArrayOfchar);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getChecksumLength() {
/* 52 */     return CRYPTO.getChecksumLength();
/*    */   }
/*    */ 
/*    */   
/*    */   public static byte[] calculateChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3) throws GeneralSecurityException {
/* 57 */     return CRYPTO.calculateChecksum(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] encryptSeq(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException, KrbCryptoException {
/* 64 */     return CRYPTO.encryptSeq(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] decryptSeq(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException, KrbCryptoException {
/* 71 */     return CRYPTO.decryptSeq(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] encrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException, KrbCryptoException {
/* 77 */     return CRYPTO.encrypt(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, null, paramArrayOfbyte3, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] encryptRaw(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException, KrbCryptoException {
/* 85 */     return CRYPTO.encryptRaw(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] decrypt(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3) throws GeneralSecurityException {
/* 91 */     return CRYPTO.decrypt(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] decryptRaw(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3, byte[] paramArrayOfbyte4) throws GeneralSecurityException {
/* 98 */     return CRYPTO.decryptRaw(paramArrayOfbyte1, paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt2, paramInt3, paramArrayOfbyte4);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/ArcFourHmac.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */