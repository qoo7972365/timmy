/*     */ package sun.security.krb5.internal.crypto;
/*     */ 
/*     */ import java.security.MessageDigest;
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
/*     */ public final class RsaMd5CksumType
/*     */   extends CksumType
/*     */ {
/*     */   public int confounderSize() {
/*  43 */     return 0;
/*     */   }
/*     */   
/*     */   public int cksumType() {
/*  47 */     return 7;
/*     */   }
/*     */   
/*     */   public boolean isKeyed() {
/*  51 */     return false;
/*     */   }
/*     */   
/*     */   public int cksumSize() {
/*  55 */     return 16;
/*     */   }
/*     */   
/*     */   public int keyType() {
/*  59 */     return 0;
/*     */   }
/*     */   
/*     */   public int keySize() {
/*  63 */     return 0;
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
/*     */   public byte[] calculateChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws KrbCryptoException {
/*     */     MessageDigest messageDigest;
/*  78 */     byte[] arrayOfByte = null;
/*     */     try {
/*  80 */       messageDigest = MessageDigest.getInstance("MD5");
/*  81 */     } catch (Exception exception) {
/*  82 */       throw new KrbCryptoException("JCE provider may not be installed. " + exception.getMessage());
/*     */     } 
/*     */     try {
/*  85 */       messageDigest.update(paramArrayOfbyte1);
/*  86 */       arrayOfByte = messageDigest.digest();
/*  87 */     } catch (Exception exception) {
/*  88 */       throw new KrbCryptoException(exception.getMessage());
/*     */     } 
/*  90 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verifyChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2) throws KrbCryptoException {
/*     */     try {
/*  98 */       byte[] arrayOfByte = MessageDigest.getInstance("MD5").digest(paramArrayOfbyte1);
/*  99 */       return CksumType.isChecksumEqual(arrayOfByte, paramArrayOfbyte3);
/* 100 */     } catch (Exception exception) {
/* 101 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/RsaMd5CksumType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */