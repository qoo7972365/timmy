/*     */ package sun.security.krb5.internal.crypto;
/*     */ 
/*     */ import java.security.GeneralSecurityException;
/*     */ import sun.security.krb5.KrbCryptoException;
/*     */ import sun.security.krb5.internal.KrbApErrException;
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
/*     */ public final class Des3CbcHmacSha1KdEType
/*     */   extends EType
/*     */ {
/*     */   public int eType() {
/*  37 */     return 16;
/*     */   }
/*     */   
/*     */   public int minimumPadSize() {
/*  41 */     return 0;
/*     */   }
/*     */   
/*     */   public int confounderSize() {
/*  45 */     return blockSize();
/*     */   }
/*     */   
/*     */   public int checksumType() {
/*  49 */     return 12;
/*     */   }
/*     */   
/*     */   public int checksumSize() {
/*  53 */     return Des3.getChecksumLength();
/*     */   }
/*     */   
/*     */   public int blockSize() {
/*  57 */     return 8;
/*     */   }
/*     */   
/*     */   public int keyType() {
/*  61 */     return 2;
/*     */   }
/*     */   
/*     */   public int keySize() {
/*  65 */     return 24;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) throws KrbCryptoException {
/*  70 */     byte[] arrayOfByte = new byte[blockSize()];
/*  71 */     return encrypt(paramArrayOfbyte1, paramArrayOfbyte2, arrayOfByte, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt) throws KrbCryptoException {
/*     */     try {
/*  77 */       return Des3.encrypt(paramArrayOfbyte2, paramInt, paramArrayOfbyte3, paramArrayOfbyte1, 0, paramArrayOfbyte1.length);
/*  78 */     } catch (GeneralSecurityException generalSecurityException) {
/*  79 */       KrbCryptoException krbCryptoException = new KrbCryptoException(generalSecurityException.getMessage());
/*  80 */       krbCryptoException.initCause(generalSecurityException);
/*  81 */       throw krbCryptoException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) throws KrbApErrException, KrbCryptoException {
/*  87 */     byte[] arrayOfByte = new byte[blockSize()];
/*  88 */     return decrypt(paramArrayOfbyte1, paramArrayOfbyte2, arrayOfByte, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt) throws KrbApErrException, KrbCryptoException {
/*     */     try {
/*  94 */       return Des3.decrypt(paramArrayOfbyte2, paramInt, paramArrayOfbyte3, paramArrayOfbyte1, 0, paramArrayOfbyte1.length);
/*  95 */     } catch (GeneralSecurityException generalSecurityException) {
/*  96 */       KrbCryptoException krbCryptoException = new KrbCryptoException(generalSecurityException.getMessage());
/*  97 */       krbCryptoException.initCause(generalSecurityException);
/*  98 */       throw krbCryptoException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decryptedData(byte[] paramArrayOfbyte) {
/* 106 */     return paramArrayOfbyte;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/Des3CbcHmacSha1KdEType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */