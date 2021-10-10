/*    */ package sun.security.krb5.internal.crypto;
/*    */ 
/*    */ import java.security.InvalidKeyException;
/*    */ import javax.crypto.spec.DESKeySpec;
/*    */ import sun.security.krb5.KrbCryptoException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DesMacKCksumType
/*    */   extends CksumType
/*    */ {
/*    */   public int confounderSize() {
/* 44 */     return 0;
/*    */   }
/*    */   
/*    */   public int cksumType() {
/* 48 */     return 5;
/*    */   }
/*    */   
/*    */   public boolean isKeyed() {
/* 52 */     return true;
/*    */   }
/*    */   
/*    */   public int cksumSize() {
/* 56 */     return 16;
/*    */   }
/*    */   
/*    */   public int keyType() {
/* 60 */     return 1;
/*    */   }
/*    */   
/*    */   public int keySize() {
/* 64 */     return 8;
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
/*    */ 
/*    */   
/*    */   public byte[] calculateChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws KrbCryptoException {
/*    */     try {
/* 80 */       if (DESKeySpec.isWeak(paramArrayOfbyte2, 0)) {
/* 81 */         paramArrayOfbyte2[7] = (byte)(paramArrayOfbyte2[7] ^ 0xF0);
/*    */       }
/* 83 */     } catch (InvalidKeyException invalidKeyException) {}
/*    */ 
/*    */     
/* 86 */     byte[] arrayOfByte = new byte[paramArrayOfbyte2.length];
/* 87 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte, 0, paramArrayOfbyte2.length);
/* 88 */     return Des.des_cksum(arrayOfByte, paramArrayOfbyte1, paramArrayOfbyte2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean verifyChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2) throws KrbCryptoException {
/* 94 */     byte[] arrayOfByte = calculateChecksum(paramArrayOfbyte1, paramArrayOfbyte1.length, paramArrayOfbyte2, paramInt2);
/* 95 */     return isChecksumEqual(paramArrayOfbyte3, arrayOfByte);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/DesMacKCksumType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */