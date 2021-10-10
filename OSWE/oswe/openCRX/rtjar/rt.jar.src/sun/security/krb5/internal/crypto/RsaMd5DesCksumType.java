/*     */ package sun.security.krb5.internal.crypto;
/*     */ 
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.MessageDigest;
/*     */ import javax.crypto.spec.DESKeySpec;
/*     */ import sun.security.krb5.Confounder;
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
/*     */ public final class RsaMd5DesCksumType
/*     */   extends CksumType
/*     */ {
/*     */   public int confounderSize() {
/*  46 */     return 8;
/*     */   }
/*     */   
/*     */   public int cksumType() {
/*  50 */     return 8;
/*     */   }
/*     */   
/*     */   public boolean isKeyed() {
/*  54 */     return true;
/*     */   }
/*     */   
/*     */   public int cksumSize() {
/*  58 */     return 24;
/*     */   }
/*     */   
/*     */   public int keyType() {
/*  62 */     return 1;
/*     */   }
/*     */   
/*     */   public int keySize() {
/*  66 */     return 8;
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
/*     */   
/*     */   public byte[] calculateChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws KrbCryptoException {
/*  81 */     byte[] arrayOfByte1 = new byte[paramInt1 + confounderSize()];
/*  82 */     byte[] arrayOfByte2 = Confounder.bytes(confounderSize());
/*  83 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, confounderSize());
/*  84 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte1, confounderSize(), paramInt1);
/*     */ 
/*     */     
/*  87 */     byte[] arrayOfByte3 = calculateRawChecksum(arrayOfByte1, arrayOfByte1.length);
/*  88 */     byte[] arrayOfByte4 = new byte[cksumSize()];
/*  89 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte4, 0, confounderSize());
/*  90 */     System.arraycopy(arrayOfByte3, 0, arrayOfByte4, confounderSize(), 
/*  91 */         cksumSize() - confounderSize());
/*     */ 
/*     */     
/*  94 */     byte[] arrayOfByte5 = new byte[keySize()];
/*  95 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte5, 0, paramArrayOfbyte2.length);
/*  96 */     for (byte b = 0; b < arrayOfByte5.length; b++) {
/*  97 */       arrayOfByte5[b] = (byte)(arrayOfByte5[b] ^ 0xF0);
/*     */     }
/*     */     try {
/* 100 */       if (DESKeySpec.isWeak(arrayOfByte5, 0)) {
/* 101 */         arrayOfByte5[7] = (byte)(arrayOfByte5[7] ^ 0xF0);
/*     */       }
/* 103 */     } catch (InvalidKeyException invalidKeyException) {}
/*     */ 
/*     */     
/* 106 */     byte[] arrayOfByte6 = new byte[arrayOfByte5.length];
/*     */ 
/*     */     
/* 109 */     byte[] arrayOfByte7 = new byte[arrayOfByte4.length];
/* 110 */     Des.cbc_encrypt(arrayOfByte4, arrayOfByte7, arrayOfByte5, arrayOfByte6, true);
/* 111 */     return arrayOfByte7;
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
/*     */ 
/*     */   
/*     */   public boolean verifyChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2) throws KrbCryptoException {
/* 127 */     byte[] arrayOfByte1 = decryptKeyedChecksum(paramArrayOfbyte3, paramArrayOfbyte2);
/*     */ 
/*     */     
/* 130 */     byte[] arrayOfByte2 = new byte[paramInt1 + confounderSize()];
/* 131 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, confounderSize());
/* 132 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte2, confounderSize(), paramInt1);
/*     */     
/* 134 */     byte[] arrayOfByte3 = calculateRawChecksum(arrayOfByte2, arrayOfByte2.length);
/*     */     
/* 136 */     byte[] arrayOfByte4 = new byte[cksumSize() - confounderSize()];
/* 137 */     System.arraycopy(arrayOfByte1, confounderSize(), arrayOfByte4, 0, 
/* 138 */         cksumSize() - confounderSize());
/*     */     
/* 140 */     return isChecksumEqual(arrayOfByte4, arrayOfByte3);
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
/*     */   private byte[] decryptKeyedChecksum(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws KrbCryptoException {
/* 153 */     byte[] arrayOfByte1 = new byte[keySize()];
/* 154 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte1, 0, paramArrayOfbyte2.length);
/* 155 */     for (byte b = 0; b < arrayOfByte1.length; b++) {
/* 156 */       arrayOfByte1[b] = (byte)(arrayOfByte1[b] ^ 0xF0);
/*     */     }
/*     */     try {
/* 159 */       if (DESKeySpec.isWeak(arrayOfByte1, 0)) {
/* 160 */         arrayOfByte1[7] = (byte)(arrayOfByte1[7] ^ 0xF0);
/*     */       }
/* 162 */     } catch (InvalidKeyException invalidKeyException) {}
/*     */ 
/*     */     
/* 165 */     byte[] arrayOfByte2 = new byte[arrayOfByte1.length];
/*     */     
/* 167 */     byte[] arrayOfByte3 = new byte[paramArrayOfbyte1.length];
/* 168 */     Des.cbc_encrypt(paramArrayOfbyte1, arrayOfByte3, arrayOfByte1, arrayOfByte2, false);
/* 169 */     return arrayOfByte3;
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
/*     */   private byte[] calculateRawChecksum(byte[] paramArrayOfbyte, int paramInt) throws KrbCryptoException {
/*     */     MessageDigest messageDigest;
/* 182 */     byte[] arrayOfByte = null;
/*     */     try {
/* 184 */       messageDigest = MessageDigest.getInstance("MD5");
/* 185 */     } catch (Exception exception) {
/* 186 */       throw new KrbCryptoException("JCE provider may not be installed. " + exception.getMessage());
/*     */     } 
/*     */     try {
/* 189 */       messageDigest.update(paramArrayOfbyte);
/* 190 */       arrayOfByte = messageDigest.digest();
/* 191 */     } catch (Exception exception) {
/* 192 */       throw new KrbCryptoException(exception.getMessage());
/*     */     } 
/* 194 */     return arrayOfByte;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/RsaMd5DesCksumType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */