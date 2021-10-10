/*     */ package sun.security.krb5.internal.crypto;
/*     */ 
/*     */ import java.security.InvalidKeyException;
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
/*     */ public class DesMacCksumType
/*     */   extends CksumType
/*     */ {
/*     */   public int confounderSize() {
/*  45 */     return 8;
/*     */   }
/*     */   
/*     */   public int cksumType() {
/*  49 */     return 4;
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
/*  61 */     return 1;
/*     */   }
/*     */   
/*     */   public int keySize() {
/*  65 */     return 8;
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
/*  79 */     byte[] arrayOfByte1 = new byte[paramInt1 + confounderSize()];
/*  80 */     byte[] arrayOfByte2 = Confounder.bytes(confounderSize());
/*  81 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, confounderSize());
/*  82 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte1, confounderSize(), paramInt1);
/*     */ 
/*     */     
/*     */     try {
/*  86 */       if (DESKeySpec.isWeak(paramArrayOfbyte2, 0)) {
/*  87 */         paramArrayOfbyte2[7] = (byte)(paramArrayOfbyte2[7] ^ 0xF0);
/*     */       }
/*  89 */     } catch (InvalidKeyException invalidKeyException) {}
/*     */ 
/*     */     
/*  92 */     byte[] arrayOfByte3 = new byte[paramArrayOfbyte2.length];
/*  93 */     byte[] arrayOfByte4 = Des.des_cksum(arrayOfByte3, arrayOfByte1, paramArrayOfbyte2);
/*  94 */     byte[] arrayOfByte5 = new byte[cksumSize()];
/*  95 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte5, 0, confounderSize());
/*  96 */     System.arraycopy(arrayOfByte4, 0, arrayOfByte5, confounderSize(), 
/*  97 */         cksumSize() - confounderSize());
/*     */     
/*  99 */     byte[] arrayOfByte6 = new byte[keySize()];
/* 100 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte6, 0, paramArrayOfbyte2.length);
/* 101 */     for (byte b = 0; b < arrayOfByte6.length; b++) {
/* 102 */       arrayOfByte6[b] = (byte)(arrayOfByte6[b] ^ 0xF0);
/*     */     }
/*     */     try {
/* 105 */       if (DESKeySpec.isWeak(arrayOfByte6, 0)) {
/* 106 */         arrayOfByte6[7] = (byte)(arrayOfByte6[7] ^ 0xF0);
/*     */       }
/* 108 */     } catch (InvalidKeyException invalidKeyException) {}
/*     */ 
/*     */     
/* 111 */     byte[] arrayOfByte7 = new byte[arrayOfByte6.length];
/*     */ 
/*     */     
/* 114 */     byte[] arrayOfByte8 = new byte[arrayOfByte5.length];
/* 115 */     Des.cbc_encrypt(arrayOfByte5, arrayOfByte8, arrayOfByte6, arrayOfByte7, true);
/* 116 */     return arrayOfByte8;
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
/*     */   public boolean verifyChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2) throws KrbCryptoException {
/* 131 */     byte[] arrayOfByte1 = decryptKeyedChecksum(paramArrayOfbyte3, paramArrayOfbyte2);
/*     */     
/* 133 */     byte[] arrayOfByte2 = new byte[paramInt1 + confounderSize()];
/* 134 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, confounderSize());
/* 135 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte2, confounderSize(), paramInt1);
/*     */ 
/*     */     
/*     */     try {
/* 139 */       if (DESKeySpec.isWeak(paramArrayOfbyte2, 0)) {
/* 140 */         paramArrayOfbyte2[7] = (byte)(paramArrayOfbyte2[7] ^ 0xF0);
/*     */       }
/* 142 */     } catch (InvalidKeyException invalidKeyException) {}
/*     */ 
/*     */     
/* 145 */     byte[] arrayOfByte3 = new byte[paramArrayOfbyte2.length];
/* 146 */     byte[] arrayOfByte4 = Des.des_cksum(arrayOfByte3, arrayOfByte2, paramArrayOfbyte2);
/* 147 */     byte[] arrayOfByte5 = new byte[cksumSize() - confounderSize()];
/* 148 */     System.arraycopy(arrayOfByte1, confounderSize(), arrayOfByte5, 0, 
/* 149 */         cksumSize() - confounderSize());
/* 150 */     return isChecksumEqual(arrayOfByte5, arrayOfByte4);
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
/*     */   private byte[] decryptKeyedChecksum(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws KrbCryptoException {
/* 162 */     byte[] arrayOfByte1 = new byte[keySize()];
/* 163 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte1, 0, paramArrayOfbyte2.length);
/* 164 */     for (byte b = 0; b < arrayOfByte1.length; b++) {
/* 165 */       arrayOfByte1[b] = (byte)(arrayOfByte1[b] ^ 0xF0);
/*     */     }
/*     */     try {
/* 168 */       if (DESKeySpec.isWeak(arrayOfByte1, 0)) {
/* 169 */         arrayOfByte1[7] = (byte)(arrayOfByte1[7] ^ 0xF0);
/*     */       }
/* 171 */     } catch (InvalidKeyException invalidKeyException) {}
/*     */ 
/*     */     
/* 174 */     byte[] arrayOfByte2 = new byte[arrayOfByte1.length];
/* 175 */     byte[] arrayOfByte3 = new byte[paramArrayOfbyte1.length];
/* 176 */     Des.cbc_encrypt(paramArrayOfbyte1, arrayOfByte3, arrayOfByte1, arrayOfByte2, false);
/* 177 */     return arrayOfByte3;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/DesMacCksumType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */