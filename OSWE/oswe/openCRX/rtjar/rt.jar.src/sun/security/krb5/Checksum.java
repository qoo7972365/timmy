/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Arrays;
/*     */ import sun.security.krb5.internal.KdcErrException;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.KrbApErrException;
/*     */ import sun.security.krb5.internal.crypto.CksumType;
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
/*     */ public class Checksum
/*     */ {
/*     */   private int cksumType;
/*     */   private byte[] checksum;
/*     */   public static final int CKSUMTYPE_NULL = 0;
/*     */   public static final int CKSUMTYPE_CRC32 = 1;
/*     */   public static final int CKSUMTYPE_RSA_MD4 = 2;
/*     */   public static final int CKSUMTYPE_RSA_MD4_DES = 3;
/*     */   public static final int CKSUMTYPE_DES_MAC = 4;
/*     */   public static final int CKSUMTYPE_DES_MAC_K = 5;
/*     */   public static final int CKSUMTYPE_RSA_MD4_DES_K = 6;
/*     */   public static final int CKSUMTYPE_RSA_MD5 = 7;
/*     */   public static final int CKSUMTYPE_RSA_MD5_DES = 8;
/*     */   public static final int CKSUMTYPE_HMAC_SHA1_DES3_KD = 12;
/*     */   public static final int CKSUMTYPE_HMAC_SHA1_96_AES128 = 15;
/*     */   public static final int CKSUMTYPE_HMAC_SHA1_96_AES256 = 16;
/*     */   public static final int CKSUMTYPE_HMAC_MD5_ARCFOUR = -138;
/*     */   static int CKSUMTYPE_DEFAULT;
/*     */   static int SAFECKSUMTYPE_DEFAULT;
/*  76 */   private static boolean DEBUG = Krb5.DEBUG;
/*     */   static {
/*  78 */     initStatic();
/*     */   }
/*     */   
/*     */   public static void initStatic() {
/*  82 */     String str = null;
/*  83 */     Config config = null;
/*     */     try {
/*  85 */       config = Config.getInstance();
/*  86 */       str = config.get(new String[] { "libdefaults", "default_checksum" });
/*  87 */       if (str != null) {
/*  88 */         CKSUMTYPE_DEFAULT = Config.getType(str);
/*     */       } else {
/*  90 */         CKSUMTYPE_DEFAULT = -1;
/*     */       } 
/*  92 */     } catch (Exception exception) {
/*  93 */       if (DEBUG) {
/*  94 */         System.out.println("Exception in getting default checksum value from the configuration. No default checksum set.");
/*     */ 
/*     */         
/*  97 */         exception.printStackTrace();
/*     */       } 
/*  99 */       CKSUMTYPE_DEFAULT = -1;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 104 */       str = config.get(new String[] { "libdefaults", "safe_checksum_type" });
/* 105 */       if (str != null) {
/*     */         
/* 107 */         SAFECKSUMTYPE_DEFAULT = Config.getType(str);
/*     */       } else {
/* 109 */         SAFECKSUMTYPE_DEFAULT = -1;
/*     */       } 
/* 111 */     } catch (Exception exception) {
/* 112 */       if (DEBUG) {
/* 113 */         System.out.println("Exception in getting safe default checksum value from the configuration Setting.  No safe default checksum set.");
/*     */ 
/*     */ 
/*     */         
/* 117 */         exception.printStackTrace();
/*     */       } 
/* 119 */       SAFECKSUMTYPE_DEFAULT = -1;
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
/*     */   public Checksum(byte[] paramArrayOfbyte, int paramInt) {
/* 140 */     this.cksumType = paramInt;
/* 141 */     this.checksum = paramArrayOfbyte;
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
/*     */   public Checksum(int paramInt1, byte[] paramArrayOfbyte, EncryptionKey paramEncryptionKey, int paramInt2) throws KdcErrException, KrbApErrException, KrbCryptoException {
/* 159 */     if (paramInt1 == -1) {
/* 160 */       this.cksumType = EType.getInstance(paramEncryptionKey.getEType()).checksumType();
/*     */     } else {
/* 162 */       this.cksumType = paramInt1;
/*     */     } 
/* 164 */     this.checksum = CksumType.getInstance(this.cksumType).calculateChecksum(paramArrayOfbyte, paramArrayOfbyte.length, paramEncryptionKey
/* 165 */         .getBytes(), paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verifyKeyedChecksum(byte[] paramArrayOfbyte, EncryptionKey paramEncryptionKey, int paramInt) throws KdcErrException, KrbApErrException, KrbCryptoException {
/* 173 */     CksumType cksumType = CksumType.getInstance(this.cksumType);
/* 174 */     if (!cksumType.isKeyed()) {
/* 175 */       throw new KrbApErrException(50);
/*     */     }
/* 177 */     return cksumType.verifyChecksum(paramArrayOfbyte, paramArrayOfbyte.length, paramEncryptionKey
/* 178 */         .getBytes(), this.checksum, paramInt);
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
/*     */   public boolean verifyAnyChecksum(byte[] paramArrayOfbyte, EncryptionKey paramEncryptionKey, int paramInt) throws KdcErrException, KrbCryptoException {
/* 195 */     return CksumType.getInstance(this.cksumType).verifyChecksum(paramArrayOfbyte, paramArrayOfbyte.length, paramEncryptionKey
/* 196 */         .getBytes(), this.checksum, paramInt);
/*     */   }
/*     */   
/*     */   boolean isEqual(Checksum paramChecksum) throws KdcErrException {
/* 200 */     if (this.cksumType != paramChecksum.cksumType) {
/* 201 */       return false;
/*     */     }
/* 203 */     return CksumType.isChecksumEqual(this.checksum, paramChecksum.checksum);
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
/*     */   public Checksum(DerValue paramDerValue) throws Asn1Exception, IOException {
/* 216 */     if (paramDerValue.getTag() != 48) {
/* 217 */       throw new Asn1Exception(906);
/*     */     }
/* 219 */     DerValue derValue = paramDerValue.getData().getDerValue();
/* 220 */     if ((derValue.getTag() & 0x1F) == 0) {
/* 221 */       this.cksumType = derValue.getData().getBigInteger().intValue();
/*     */     } else {
/*     */       
/* 224 */       throw new Asn1Exception(906);
/* 225 */     }  derValue = paramDerValue.getData().getDerValue();
/* 226 */     if ((derValue.getTag() & 0x1F) == 1) {
/* 227 */       this.checksum = derValue.getData().getOctetString();
/*     */     } else {
/*     */       
/* 230 */       throw new Asn1Exception(906);
/* 231 */     }  if (paramDerValue.getData().available() > 0) {
/* 232 */       throw new Asn1Exception(906);
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
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 258 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 259 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 260 */     derOutputStream2.putInteger(BigInteger.valueOf(this.cksumType));
/* 261 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/*     */     
/* 263 */     derOutputStream2 = new DerOutputStream();
/* 264 */     derOutputStream2.putOctetString(this.checksum);
/* 265 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/*     */     
/* 267 */     derOutputStream2 = new DerOutputStream();
/* 268 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 269 */     return derOutputStream2.toByteArray();
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
/*     */   public static Checksum parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 293 */     if (paramBoolean && (
/* 294 */       (byte)paramDerInputStream.peekByte() & 0x1F) != paramByte) {
/* 295 */       return null;
/*     */     }
/* 297 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 298 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 299 */       throw new Asn1Exception(906);
/*     */     }
/* 301 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 302 */     return new Checksum(derValue2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] getBytes() {
/* 310 */     return this.checksum;
/*     */   }
/*     */   
/*     */   public final int getType() {
/* 314 */     return this.cksumType;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 318 */     if (this == paramObject) {
/* 319 */       return true;
/*     */     }
/* 321 */     if (!(paramObject instanceof Checksum)) {
/* 322 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 326 */       return isEqual((Checksum)paramObject);
/* 327 */     } catch (KdcErrException kdcErrException) {
/* 328 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 333 */     int i = 17;
/* 334 */     i = 37 * i + this.cksumType;
/* 335 */     if (this.checksum != null) {
/* 336 */       i = 37 * i + Arrays.hashCode(this.checksum);
/*     */     }
/* 338 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/Checksum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */