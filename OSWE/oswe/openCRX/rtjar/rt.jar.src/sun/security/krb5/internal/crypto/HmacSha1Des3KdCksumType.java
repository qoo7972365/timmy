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
/*     */ public class HmacSha1Des3KdCksumType
/*     */   extends CksumType
/*     */ {
/*     */   public int confounderSize() {
/*  39 */     return 8;
/*     */   }
/*     */   
/*     */   public int cksumType() {
/*  43 */     return 12;
/*     */   }
/*     */   
/*     */   public boolean isKeyed() {
/*  47 */     return true;
/*     */   }
/*     */   
/*     */   public int cksumSize() {
/*  51 */     return 20;
/*     */   }
/*     */   
/*     */   public int keyType() {
/*  55 */     return 2;
/*     */   }
/*     */   
/*     */   public int keySize() {
/*  59 */     return 24;
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
/*  73 */       return Des3.calculateChecksum(paramArrayOfbyte2, paramInt2, paramArrayOfbyte1, 0, paramInt1);
/*  74 */     } catch (GeneralSecurityException generalSecurityException) {
/*  75 */       KrbCryptoException krbCryptoException = new KrbCryptoException(generalSecurityException.getMessage());
/*  76 */       krbCryptoException.initCause(generalSecurityException);
/*  77 */       throw krbCryptoException;
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
/*  93 */       byte[] arrayOfByte = Des3.calculateChecksum(paramArrayOfbyte2, paramInt2, paramArrayOfbyte1, 0, paramInt1);
/*     */ 
/*     */       
/*  96 */       return isChecksumEqual(paramArrayOfbyte3, arrayOfByte);
/*  97 */     } catch (GeneralSecurityException generalSecurityException) {
/*  98 */       KrbCryptoException krbCryptoException = new KrbCryptoException(generalSecurityException.getMessage());
/*  99 */       krbCryptoException.initCause(generalSecurityException);
/* 100 */       throw krbCryptoException;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/HmacSha1Des3KdCksumType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */