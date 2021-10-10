/*     */ package sun.security.krb5.internal.crypto;
/*     */ 
/*     */ import sun.security.krb5.Confounder;
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
/*     */ abstract class DesCbcEType
/*     */   extends EType
/*     */ {
/*     */   protected abstract byte[] calculateChecksum(byte[] paramArrayOfbyte, int paramInt) throws KrbCryptoException;
/*     */   
/*     */   public int blockSize() {
/*  43 */     return 8;
/*     */   }
/*     */   
/*     */   public int keyType() {
/*  47 */     return 1;
/*     */   }
/*     */   
/*     */   public int keySize() {
/*  51 */     return 8;
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
/*     */   public byte[] encrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) throws KrbCryptoException {
/*  65 */     byte[] arrayOfByte = new byte[keySize()];
/*  66 */     return encrypt(paramArrayOfbyte1, paramArrayOfbyte2, arrayOfByte, paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt) throws KrbCryptoException {
/*     */     byte arrayOfByte1[], b;
/*  90 */     if (paramArrayOfbyte2.length > 8) {
/*  91 */       throw new KrbCryptoException("Invalid DES Key!");
/*     */     }
/*  93 */     int i = paramArrayOfbyte1.length + confounderSize() + checksumSize();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     if (i % blockSize() == 0) {
/* 104 */       arrayOfByte1 = new byte[i + blockSize()];
/* 105 */       b = 8;
/*     */     } else {
/*     */       
/* 108 */       arrayOfByte1 = new byte[i + blockSize() - i % blockSize()];
/* 109 */       b = (byte)(blockSize() - i % blockSize());
/*     */     } 
/* 111 */     for (int j = i; j < arrayOfByte1.length; j++) {
/* 112 */       arrayOfByte1[j] = b;
/*     */     }
/* 114 */     byte[] arrayOfByte2 = Confounder.bytes(confounderSize());
/* 115 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, confounderSize());
/* 116 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte1, startOfData(), paramArrayOfbyte1.length);
/* 117 */     byte[] arrayOfByte3 = calculateChecksum(arrayOfByte1, arrayOfByte1.length);
/* 118 */     System.arraycopy(arrayOfByte3, 0, arrayOfByte1, startOfChecksum(), 
/* 119 */         checksumSize());
/* 120 */     byte[] arrayOfByte4 = new byte[arrayOfByte1.length];
/* 121 */     Des.cbc_encrypt(arrayOfByte1, arrayOfByte4, paramArrayOfbyte2, paramArrayOfbyte3, true);
/* 122 */     return arrayOfByte4;
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
/*     */   public byte[] decrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) throws KrbApErrException, KrbCryptoException {
/* 134 */     byte[] arrayOfByte = new byte[keySize()];
/* 135 */     return decrypt(paramArrayOfbyte1, paramArrayOfbyte2, arrayOfByte, paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt) throws KrbApErrException, KrbCryptoException {
/* 158 */     if (paramArrayOfbyte2.length > 8) {
/* 159 */       throw new KrbCryptoException("Invalid DES Key!");
/*     */     }
/* 161 */     byte[] arrayOfByte = new byte[paramArrayOfbyte1.length];
/* 162 */     Des.cbc_encrypt(paramArrayOfbyte1, arrayOfByte, paramArrayOfbyte2, paramArrayOfbyte3, false);
/* 163 */     if (!isChecksumValid(arrayOfByte))
/* 164 */       throw new KrbApErrException(31); 
/* 165 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   private void copyChecksumField(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 169 */     for (byte b = 0; b < checksumSize(); b++)
/* 170 */       paramArrayOfbyte1[startOfChecksum() + b] = paramArrayOfbyte2[b]; 
/*     */   }
/*     */   
/*     */   private byte[] checksumField(byte[] paramArrayOfbyte) {
/* 174 */     byte[] arrayOfByte = new byte[checksumSize()];
/* 175 */     for (byte b = 0; b < checksumSize(); b++)
/* 176 */       arrayOfByte[b] = paramArrayOfbyte[startOfChecksum() + b]; 
/* 177 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   private void resetChecksumField(byte[] paramArrayOfbyte) {
/* 181 */     int i = startOfChecksum(); for (; i < startOfChecksum() + 
/* 182 */       checksumSize(); i++) {
/* 183 */       paramArrayOfbyte[i] = 0;
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
/*     */   private byte[] generateChecksum(byte[] paramArrayOfbyte) throws KrbCryptoException {
/* 196 */     byte[] arrayOfByte1 = checksumField(paramArrayOfbyte);
/* 197 */     resetChecksumField(paramArrayOfbyte);
/* 198 */     byte[] arrayOfByte2 = calculateChecksum(paramArrayOfbyte, paramArrayOfbyte.length);
/* 199 */     copyChecksumField(paramArrayOfbyte, arrayOfByte1);
/* 200 */     return arrayOfByte2;
/*     */   }
/*     */   
/*     */   private boolean isChecksumEqual(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 204 */     if (paramArrayOfbyte1 == paramArrayOfbyte2)
/* 205 */       return true; 
/* 206 */     if ((paramArrayOfbyte1 == null && paramArrayOfbyte2 != null) || (paramArrayOfbyte1 != null && paramArrayOfbyte2 == null))
/*     */     {
/* 208 */       return false; } 
/* 209 */     if (paramArrayOfbyte1.length != paramArrayOfbyte2.length)
/* 210 */       return false; 
/* 211 */     for (byte b = 0; b < paramArrayOfbyte1.length; b++) {
/* 212 */       if (paramArrayOfbyte1[b] != paramArrayOfbyte2[b])
/* 213 */         return false; 
/* 214 */     }  return true;
/*     */   }
/*     */   
/*     */   protected boolean isChecksumValid(byte[] paramArrayOfbyte) throws KrbCryptoException {
/* 218 */     byte[] arrayOfByte1 = checksumField(paramArrayOfbyte);
/* 219 */     byte[] arrayOfByte2 = generateChecksum(paramArrayOfbyte);
/* 220 */     return isChecksumEqual(arrayOfByte1, arrayOfByte2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/DesCbcEType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */