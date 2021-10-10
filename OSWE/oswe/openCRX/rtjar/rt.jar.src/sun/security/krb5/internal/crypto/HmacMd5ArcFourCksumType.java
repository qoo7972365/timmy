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
/*     */ public class HmacMd5ArcFourCksumType
/*     */   extends CksumType
/*     */ {
/*     */   public int confounderSize() {
/*  45 */     return 8;
/*     */   }
/*     */   
/*     */   public int cksumType() {
/*  49 */     return -138;
/*     */   }
/*     */   
/*     */   public boolean isKeyed() {
/*  53 */     return true;
/*     */   }
/*     */   
/*     */   public int cksumSize() {
/*  57 */     return 16;
/*     */   }
/*     */   
/*     */   public int keyType() {
/*  61 */     return 4;
/*     */   }
/*     */   
/*     */   public int keySize() {
/*  65 */     return 16;
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
/*  79 */       return ArcFourHmac.calculateChecksum(paramArrayOfbyte2, paramInt2, paramArrayOfbyte1, 0, paramInt1);
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
/*  99 */       byte[] arrayOfByte = ArcFourHmac.calculateChecksum(paramArrayOfbyte2, paramInt2, paramArrayOfbyte1, 0, paramInt1);
/*     */ 
/*     */       
/* 102 */       return isChecksumEqual(paramArrayOfbyte3, arrayOfByte);
/* 103 */     } catch (GeneralSecurityException generalSecurityException) {
/* 104 */       KrbCryptoException krbCryptoException = new KrbCryptoException(generalSecurityException.getMessage());
/* 105 */       krbCryptoException.initCause(generalSecurityException);
/* 106 */       throw krbCryptoException;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/HmacMd5ArcFourCksumType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */