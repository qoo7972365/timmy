/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import sun.security.krb5.internal.KdcErrException;
/*     */ import sun.security.krb5.internal.KrbApErrException;
/*     */ import sun.security.krb5.internal.crypto.EType;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptedData
/*     */   implements Cloneable
/*     */ {
/*     */   int eType;
/*     */   Integer kvno;
/*     */   byte[] cipher;
/*     */   byte[] plain;
/*     */   public static final int ETYPE_NULL = 0;
/*     */   public static final int ETYPE_DES_CBC_CRC = 1;
/*     */   public static final int ETYPE_DES_CBC_MD4 = 2;
/*     */   public static final int ETYPE_DES_CBC_MD5 = 3;
/*     */   public static final int ETYPE_ARCFOUR_HMAC = 23;
/*     */   public static final int ETYPE_ARCFOUR_HMAC_EXP = 24;
/*     */   public static final int ETYPE_DES3_CBC_HMAC_SHA1_KD = 16;
/*     */   public static final int ETYPE_AES128_CTS_HMAC_SHA1_96 = 17;
/*     */   public static final int ETYPE_AES256_CTS_HMAC_SHA1_96 = 18;
/*     */   
/*     */   private EncryptedData() {}
/*     */   
/*     */   public Object clone() {
/*  87 */     EncryptedData encryptedData = new EncryptedData();
/*  88 */     encryptedData.eType = this.eType;
/*  89 */     if (this.kvno != null) {
/*  90 */       encryptedData.kvno = new Integer(this.kvno.intValue());
/*     */     }
/*  92 */     if (this.cipher != null) {
/*  93 */       encryptedData.cipher = new byte[this.cipher.length];
/*  94 */       System.arraycopy(this.cipher, 0, encryptedData.cipher, 0, this.cipher.length);
/*     */     } 
/*     */     
/*  97 */     return encryptedData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptedData(int paramInt, Integer paramInteger, byte[] paramArrayOfbyte) {
/* 105 */     this.eType = paramInt;
/* 106 */     this.kvno = paramInteger;
/* 107 */     this.cipher = paramArrayOfbyte;
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
/*     */   public EncryptedData(EncryptionKey paramEncryptionKey, byte[] paramArrayOfbyte, int paramInt) throws KdcErrException, KrbCryptoException {
/* 130 */     EType eType = EType.getInstance(paramEncryptionKey.getEType());
/* 131 */     this.cipher = eType.encrypt(paramArrayOfbyte, paramEncryptionKey.getBytes(), paramInt);
/* 132 */     this.eType = paramEncryptionKey.getEType();
/* 133 */     this.kvno = paramEncryptionKey.getKeyVersionNumber();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(EncryptionKey paramEncryptionKey, int paramInt) throws KdcErrException, KrbApErrException, KrbCryptoException {
/* 166 */     if (this.eType != paramEncryptionKey.getEType()) {
/* 167 */       throw new KrbCryptoException("EncryptedData is encrypted using keytype " + 
/*     */           
/* 169 */           EType.toString(this.eType) + " but decryption key is of type " + 
/*     */           
/* 171 */           EType.toString(paramEncryptionKey.getEType()));
/*     */     }
/*     */     
/* 174 */     EType eType = EType.getInstance(this.eType);
/* 175 */     this.plain = eType.decrypt(this.cipher, paramEncryptionKey.getBytes(), paramInt);
/*     */ 
/*     */ 
/*     */     
/* 179 */     return eType.decryptedData(this.plain);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] decryptedData() throws KdcErrException {
/* 210 */     if (this.plain != null) {
/* 211 */       EType eType = EType.getInstance(this.eType);
/* 212 */       return eType.decryptedData(this.plain);
/*     */     } 
/* 214 */     return null;
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
/*     */   private EncryptedData(DerValue paramDerValue) throws Asn1Exception, IOException {
/* 230 */     DerValue derValue = null;
/* 231 */     if (paramDerValue.getTag() != 48) {
/* 232 */       throw new Asn1Exception(906);
/*     */     }
/* 234 */     derValue = paramDerValue.getData().getDerValue();
/* 235 */     if ((derValue.getTag() & 0x1F) == 0) {
/* 236 */       this.eType = derValue.getData().getBigInteger().intValue();
/*     */     } else {
/* 238 */       throw new Asn1Exception(906);
/*     */     } 
/*     */     
/* 241 */     if ((paramDerValue.getData().peekByte() & 0x1F) == 1) {
/* 242 */       derValue = paramDerValue.getData().getDerValue();
/* 243 */       int i = derValue.getData().getBigInteger().intValue();
/* 244 */       this.kvno = new Integer(i);
/*     */     } else {
/* 246 */       this.kvno = null;
/*     */     } 
/* 248 */     derValue = paramDerValue.getData().getDerValue();
/* 249 */     if ((derValue.getTag() & 0x1F) == 2) {
/* 250 */       this.cipher = derValue.getData().getOctetString();
/*     */     } else {
/* 252 */       throw new Asn1Exception(906);
/*     */     } 
/* 254 */     if (paramDerValue.getData().available() > 0) {
/* 255 */       throw new Asn1Exception(906);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 284 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 285 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 286 */     derOutputStream2.putInteger(BigInteger.valueOf(this.eType));
/* 287 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/*     */     
/* 289 */     derOutputStream2 = new DerOutputStream();
/* 290 */     if (this.kvno != null) {
/*     */       
/* 292 */       derOutputStream2.putInteger(BigInteger.valueOf(this.kvno.longValue()));
/* 293 */       derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/*     */       
/* 295 */       derOutputStream2 = new DerOutputStream();
/*     */     } 
/* 297 */     derOutputStream2.putOctetString(this.cipher);
/* 298 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)2), derOutputStream2);
/*     */     
/* 300 */     derOutputStream2 = new DerOutputStream();
/* 301 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 302 */     return derOutputStream2.toByteArray();
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
/*     */   public static EncryptedData parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 326 */     if (paramBoolean && (
/* 327 */       (byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/* 328 */       return null; 
/* 329 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 330 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 331 */       throw new Asn1Exception(906);
/*     */     }
/* 333 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 334 */     return new EncryptedData(derValue2);
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
/*     */   public byte[] reset(byte[] paramArrayOfbyte) {
/* 346 */     byte[] arrayOfByte = null;
/*     */ 
/*     */     
/* 349 */     if ((paramArrayOfbyte[1] & 0xFF) < 128) {
/* 350 */       arrayOfByte = new byte[paramArrayOfbyte[1] + 2];
/* 351 */       System.arraycopy(paramArrayOfbyte, 0, arrayOfByte, 0, paramArrayOfbyte[1] + 2);
/*     */     }
/* 353 */     else if ((paramArrayOfbyte[1] & 0xFF) > 128) {
/* 354 */       int i = paramArrayOfbyte[1] & Byte.MAX_VALUE;
/* 355 */       int j = 0;
/* 356 */       for (byte b = 0; b < i; b++) {
/* 357 */         j |= (paramArrayOfbyte[b + 2] & 0xFF) << 8 * (i - b - 1);
/*     */       }
/* 359 */       arrayOfByte = new byte[j + i + 2];
/* 360 */       System.arraycopy(paramArrayOfbyte, 0, arrayOfByte, 0, j + i + 2);
/*     */     } 
/*     */     
/* 363 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public int getEType() {
/* 367 */     return this.eType;
/*     */   }
/*     */   
/*     */   public Integer getKeyVersionNumber() {
/* 371 */     return this.kvno;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBytes() {
/* 378 */     return this.cipher;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/EncryptedData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */