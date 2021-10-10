/*     */ package sun.security.krb5.internal.crypto.dk;
/*     */ 
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.Mac;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.SecretKeyFactory;
/*     */ import javax.crypto.spec.DESKeySpec;
/*     */ import javax.crypto.spec.DESedeKeySpec;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Des3DkCrypto
/*     */   extends DkCrypto
/*     */ {
/*  43 */   private static final byte[] ZERO_IV = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getKeySeedLength() {
/*  49 */     return 168;
/*     */   }
/*     */   
/*     */   public byte[] stringToKey(char[] paramArrayOfchar) throws GeneralSecurityException {
/*  53 */     byte[] arrayOfByte = null;
/*     */     try {
/*  55 */       arrayOfByte = charToUtf8(paramArrayOfchar);
/*  56 */       return stringToKey(arrayOfByte, (byte[])null);
/*     */     } finally {
/*  58 */       if (arrayOfByte != null) {
/*  59 */         Arrays.fill(arrayOfByte, (byte)0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] stringToKey(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws GeneralSecurityException {
/*  68 */     if (paramArrayOfbyte2 != null && paramArrayOfbyte2.length > 0) {
/*  69 */       throw new RuntimeException("Invalid parameter to stringToKey");
/*     */     }
/*     */     
/*  72 */     byte[] arrayOfByte = randomToKey(nfold(paramArrayOfbyte1, getKeySeedLength()));
/*  73 */     return dk(arrayOfByte, KERBEROS_CONSTANT);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] parityFix(byte[] paramArrayOfbyte) throws GeneralSecurityException {
/*  79 */     setParityBit(paramArrayOfbyte);
/*  80 */     return paramArrayOfbyte;
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
/*     */   protected byte[] randomToKey(byte[] paramArrayOfbyte) {
/*  92 */     if (paramArrayOfbyte.length != 21) {
/*  93 */       throw new IllegalArgumentException("input must be 168 bits");
/*     */     }
/*     */     
/*  96 */     byte[] arrayOfByte1 = keyCorrection(des3Expand(paramArrayOfbyte, 0, 7));
/*  97 */     byte[] arrayOfByte2 = keyCorrection(des3Expand(paramArrayOfbyte, 7, 14));
/*  98 */     byte[] arrayOfByte3 = keyCorrection(des3Expand(paramArrayOfbyte, 14, 21));
/*     */     
/* 100 */     byte[] arrayOfByte4 = new byte[24];
/* 101 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte4, 0, 8);
/* 102 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte4, 8, 8);
/* 103 */     System.arraycopy(arrayOfByte3, 0, arrayOfByte4, 16, 8);
/*     */     
/* 105 */     return arrayOfByte4;
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] keyCorrection(byte[] paramArrayOfbyte) {
/*     */     try {
/* 111 */       if (DESKeySpec.isWeak(paramArrayOfbyte, 0)) {
/* 112 */         paramArrayOfbyte[7] = (byte)(paramArrayOfbyte[7] ^ 0xF0);
/*     */       }
/* 114 */     } catch (InvalidKeyException invalidKeyException) {}
/*     */ 
/*     */     
/* 117 */     return paramArrayOfbyte;
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
/*     */ 
/*     */   
/*     */   private static byte[] des3Expand(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 142 */     if (paramInt2 - paramInt1 != 7) {
/* 143 */       throw new IllegalArgumentException("Invalid length of DES Key Value:" + paramInt1 + "," + paramInt2);
/*     */     }
/*     */     
/* 146 */     byte[] arrayOfByte = new byte[8];
/* 147 */     byte b1 = 0;
/* 148 */     System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, 0, 7);
/* 149 */     byte b2 = 0;
/*     */ 
/*     */     
/* 152 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 153 */       byte b = (byte)(paramArrayOfbyte[i] & 0x1);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 158 */       b2 = (byte)(b2 + 1);
/* 159 */       if (b != 0) {
/* 160 */         b1 = (byte)(b1 | b << b2);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     arrayOfByte[7] = b1;
/* 168 */     setParityBit(arrayOfByte);
/* 169 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setParityBit(byte[] paramArrayOfbyte) {
/* 177 */     for (byte b = 0; b < paramArrayOfbyte.length; b++) {
/* 178 */       int i = paramArrayOfbyte[b] & 0xFE;
/* 179 */       i |= Integer.bitCount(i) & 0x1 ^ 0x1;
/* 180 */       paramArrayOfbyte[b] = (byte)i;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Cipher getCipher(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) throws GeneralSecurityException {
/* 187 */     SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("desede");
/*     */ 
/*     */     
/* 190 */     DESedeKeySpec dESedeKeySpec = new DESedeKeySpec(paramArrayOfbyte1, 0);
/*     */ 
/*     */     
/* 193 */     SecretKey secretKey = secretKeyFactory.generateSecret(dESedeKeySpec);
/*     */ 
/*     */     
/* 196 */     if (paramArrayOfbyte2 == null) {
/* 197 */       paramArrayOfbyte2 = ZERO_IV;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 202 */     Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
/* 203 */     IvParameterSpec ivParameterSpec = new IvParameterSpec(paramArrayOfbyte2, 0, paramArrayOfbyte2.length);
/*     */ 
/*     */     
/* 206 */     cipher.init(paramInt, secretKey, ivParameterSpec);
/*     */     
/* 208 */     return cipher;
/*     */   }
/*     */   
/*     */   public int getChecksumLength() {
/* 212 */     return 20;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] getHmac(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws GeneralSecurityException {
/* 218 */     SecretKeySpec secretKeySpec = new SecretKeySpec(paramArrayOfbyte1, "HmacSHA1");
/* 219 */     Mac mac = Mac.getInstance("HmacSHA1");
/* 220 */     mac.init(secretKeySpec);
/* 221 */     return mac.doFinal(paramArrayOfbyte2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/dk/Des3DkCrypto.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */