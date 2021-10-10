/*     */ package sun.security.krb5.internal.crypto;
/*     */ 
/*     */ import java.security.GeneralSecurityException;
/*     */ import sun.security.krb5.KrbCryptoException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HmacSha1Aes256CksumType
/*     */   extends CksumType
/*     */ {
/*     */   public int confounderSize() {
/*  45 */     return 16;
/*     */   }
/*     */   
/*     */   public int cksumType() {
/*  49 */     return 16;
/*     */   }
/*     */   
/*     */   public boolean isKeyed() {
/*  53 */     return true;
/*     */   }
/*     */   
/*     */   public int cksumSize() {
/*  57 */     return 12;
/*     */   }
/*     */   
/*     */   public int keyType() {
/*  61 */     return 3;
/*     */   }
/*     */   
/*     */   public int keySize() {
/*  65 */     return 32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] calculateChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws KrbCryptoException {
/*     */     try {
/*  79 */       return Aes256.calculateChecksum(paramArrayOfbyte2, paramInt2, paramArrayOfbyte1, 0, paramInt1);
/*  80 */     } catch (GeneralSecurityException generalSecurityException) {
/*  81 */       KrbCryptoException krbCryptoException = new KrbCryptoException(generalSecurityException.getMessage());
/*  82 */       krbCryptoException.initCause(generalSecurityException);
/*  83 */       throw krbCryptoException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verifyChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2) throws KrbCryptoException {
/*     */     try {
/*  99 */       byte[] arrayOfByte = Aes256.calculateChecksum(paramArrayOfbyte2, paramInt2, paramArrayOfbyte1, 0, paramInt1);
/*     */       
/* 101 */       return isChecksumEqual(paramArrayOfbyte3, arrayOfByte);
/* 102 */     } catch (GeneralSecurityException generalSecurityException) {
/* 103 */       KrbCryptoException krbCryptoException = new KrbCryptoException(generalSecurityException.getMessage());
/* 104 */       krbCryptoException.initCause(generalSecurityException);
/* 105 */       throw krbCryptoException;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/HmacSha1Aes256CksumType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */