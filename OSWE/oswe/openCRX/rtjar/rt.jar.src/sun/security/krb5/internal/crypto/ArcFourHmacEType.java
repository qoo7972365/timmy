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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ArcFourHmacEType
/*     */   extends EType
/*     */ {
/*     */   public int eType() {
/*  43 */     return 23;
/*     */   }
/*     */   
/*     */   public int minimumPadSize() {
/*  47 */     return 1;
/*     */   }
/*     */   
/*     */   public int confounderSize() {
/*  51 */     return 8;
/*     */   }
/*     */   
/*     */   public int checksumType() {
/*  55 */     return -138;
/*     */   }
/*     */   
/*     */   public int checksumSize() {
/*  59 */     return ArcFourHmac.getChecksumLength();
/*     */   }
/*     */   
/*     */   public int blockSize() {
/*  63 */     return 1;
/*     */   }
/*     */   
/*     */   public int keyType() {
/*  67 */     return 4;
/*     */   }
/*     */   
/*     */   public int keySize() {
/*  71 */     return 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) throws KrbCryptoException {
/*  76 */     byte[] arrayOfByte = new byte[blockSize()];
/*  77 */     return encrypt(paramArrayOfbyte1, paramArrayOfbyte2, arrayOfByte, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt) throws KrbCryptoException {
/*     */     try {
/*  83 */       return ArcFourHmac.encrypt(paramArrayOfbyte2, paramInt, paramArrayOfbyte3, paramArrayOfbyte1, 0, paramArrayOfbyte1.length);
/*  84 */     } catch (GeneralSecurityException generalSecurityException) {
/*  85 */       KrbCryptoException krbCryptoException = new KrbCryptoException(generalSecurityException.getMessage());
/*  86 */       krbCryptoException.initCause(generalSecurityException);
/*  87 */       throw krbCryptoException;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) throws KrbApErrException, KrbCryptoException {
/*  93 */     byte[] arrayOfByte = new byte[blockSize()];
/*  94 */     return decrypt(paramArrayOfbyte1, paramArrayOfbyte2, arrayOfByte, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt) throws KrbApErrException, KrbCryptoException {
/*     */     try {
/* 100 */       return ArcFourHmac.decrypt(paramArrayOfbyte2, paramInt, paramArrayOfbyte3, paramArrayOfbyte1, 0, paramArrayOfbyte1.length);
/* 101 */     } catch (GeneralSecurityException generalSecurityException) {
/* 102 */       KrbCryptoException krbCryptoException = new KrbCryptoException(generalSecurityException.getMessage());
/* 103 */       krbCryptoException.initCause(generalSecurityException);
/* 104 */       throw krbCryptoException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decryptedData(byte[] paramArrayOfbyte) {
/* 112 */     return paramArrayOfbyte;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/ArcFourHmacEType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */