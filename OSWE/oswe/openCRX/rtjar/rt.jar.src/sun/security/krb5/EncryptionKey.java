/*     */ package sun.security.krb5;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.spec.DESKeySpec;
/*     */ import javax.crypto.spec.DESedeKeySpec;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.PAData;
/*     */ import sun.security.krb5.internal.ccache.CCacheOutputStream;
/*     */ import sun.security.krb5.internal.crypto.Aes128;
/*     */ import sun.security.krb5.internal.crypto.Aes256;
/*     */ import sun.security.krb5.internal.crypto.ArcFourHmac;
/*     */ import sun.security.krb5.internal.crypto.Des;
/*     */ import sun.security.krb5.internal.crypto.Des3;
/*     */ import sun.security.krb5.internal.crypto.EType;
/*     */ import sun.security.krb5.internal.ktab.KeyTab;
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
/*     */ public class EncryptionKey
/*     */   implements Cloneable
/*     */ {
/*  70 */   public static final EncryptionKey NULL_KEY = new EncryptionKey(new byte[0], 0, null);
/*     */   
/*     */   private int keyType;
/*     */   
/*     */   private byte[] keyValue;
/*     */   
/*     */   private Integer kvno;
/*  77 */   private static final boolean DEBUG = Krb5.DEBUG;
/*     */   
/*     */   public synchronized int getEType() {
/*  80 */     return this.keyType;
/*     */   }
/*     */   
/*     */   public final Integer getKeyVersionNumber() {
/*  84 */     return this.kvno;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] getBytes() {
/*  93 */     return this.keyValue;
/*     */   }
/*     */   
/*     */   public synchronized Object clone() {
/*  97 */     return new EncryptionKey(this.keyValue, this.keyType, this.kvno);
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
/*     */   public static EncryptionKey[] acquireSecretKeys(PrincipalName paramPrincipalName, String paramString) {
/* 113 */     if (paramPrincipalName == null) {
/* 114 */       throw new IllegalArgumentException("Cannot have null pricipal name to look in keytab.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 119 */     KeyTab keyTab = KeyTab.getInstance(paramString);
/* 120 */     return keyTab.readServiceKeys(paramPrincipalName);
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
/*     */   public static EncryptionKey acquireSecretKey(PrincipalName paramPrincipalName, char[] paramArrayOfchar, int paramInt, PAData.SaltAndParams paramSaltAndParams) throws KrbException {
/*     */     String str;
/*     */     byte[] arrayOfByte;
/* 137 */     if (paramSaltAndParams != null) {
/* 138 */       str = (paramSaltAndParams.salt != null) ? paramSaltAndParams.salt : paramPrincipalName.getSalt();
/* 139 */       arrayOfByte = paramSaltAndParams.params;
/*     */     } else {
/* 141 */       str = paramPrincipalName.getSalt();
/* 142 */       arrayOfByte = null;
/*     */     } 
/* 144 */     return acquireSecretKey(paramArrayOfchar, str, paramInt, arrayOfByte);
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
/*     */   public static EncryptionKey acquireSecretKey(char[] paramArrayOfchar, String paramString, int paramInt, byte[] paramArrayOfbyte) throws KrbException {
/* 159 */     return new EncryptionKey(
/* 160 */         stringToKey(paramArrayOfchar, paramString, paramArrayOfbyte, paramInt), paramInt, null);
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
/*     */   public static EncryptionKey[] acquireSecretKeys(char[] paramArrayOfchar, String paramString) throws KrbException {
/* 180 */     int[] arrayOfInt = EType.getDefaults("default_tkt_enctypes");
/*     */     
/* 182 */     EncryptionKey[] arrayOfEncryptionKey = new EncryptionKey[arrayOfInt.length];
/* 183 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/* 184 */       if (EType.isSupported(arrayOfInt[b])) {
/* 185 */         arrayOfEncryptionKey[b] = new EncryptionKey(
/* 186 */             stringToKey(paramArrayOfchar, paramString, null, arrayOfInt[b]), arrayOfInt[b], null);
/*     */       
/*     */       }
/* 189 */       else if (DEBUG) {
/* 190 */         System.out.println("Encryption Type " + 
/* 191 */             EType.toString(arrayOfInt[b]) + " is not supported/enabled");
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 196 */     return arrayOfEncryptionKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionKey(byte[] paramArrayOfbyte, int paramInt, Integer paramInteger) {
/* 204 */     if (paramArrayOfbyte != null) {
/* 205 */       this.keyValue = new byte[paramArrayOfbyte.length];
/* 206 */       System.arraycopy(paramArrayOfbyte, 0, this.keyValue, 0, paramArrayOfbyte.length);
/*     */     } else {
/* 208 */       throw new IllegalArgumentException("EncryptionKey: Key bytes cannot be null!");
/*     */     } 
/*     */     
/* 211 */     this.keyType = paramInt;
/* 212 */     this.kvno = paramInteger;
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
/*     */   public EncryptionKey(int paramInt, byte[] paramArrayOfbyte) {
/* 225 */     this(paramArrayOfbyte, paramInt, (Integer)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] stringToKey(char[] paramArrayOfchar, String paramString, byte[] paramArrayOfbyte, int paramInt) throws KrbCryptoException {
/* 231 */     char[] arrayOfChar1 = paramString.toCharArray();
/* 232 */     char[] arrayOfChar2 = new char[paramArrayOfchar.length + arrayOfChar1.length];
/* 233 */     System.arraycopy(paramArrayOfchar, 0, arrayOfChar2, 0, paramArrayOfchar.length);
/* 234 */     System.arraycopy(arrayOfChar1, 0, arrayOfChar2, paramArrayOfchar.length, arrayOfChar1.length);
/* 235 */     Arrays.fill(arrayOfChar1, '0');
/*     */     try {
/*     */       byte[] arrayOfByte;
/* 238 */       switch (paramInt) {
/*     */         case 1:
/*     */         case 3:
/* 241 */           arrayOfByte = Des.string_to_key_bytes(arrayOfChar2); return arrayOfByte;
/*     */         
/*     */         case 16:
/* 244 */           arrayOfByte = Des3.stringToKey(arrayOfChar2); return arrayOfByte;
/*     */         
/*     */         case 23:
/* 247 */           arrayOfByte = ArcFourHmac.stringToKey(paramArrayOfchar); return arrayOfByte;
/*     */         
/*     */         case 17:
/* 250 */           arrayOfByte = Aes128.stringToKey(paramArrayOfchar, paramString, paramArrayOfbyte); return arrayOfByte;
/*     */         
/*     */         case 18:
/* 253 */           arrayOfByte = Aes256.stringToKey(paramArrayOfchar, paramString, paramArrayOfbyte); return arrayOfByte;
/*     */       } 
/*     */       
/* 256 */       throw new IllegalArgumentException("encryption type " + 
/* 257 */           EType.toString(paramInt) + " not supported");
/*     */     
/*     */     }
/* 260 */     catch (GeneralSecurityException generalSecurityException) {
/* 261 */       KrbCryptoException krbCryptoException = new KrbCryptoException(generalSecurityException.getMessage());
/* 262 */       krbCryptoException.initCause(generalSecurityException);
/* 263 */       throw krbCryptoException;
/*     */     } finally {
/* 265 */       Arrays.fill(arrayOfChar2, '0');
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionKey(char[] paramArrayOfchar, String paramString1, String paramString2) throws KrbCryptoException {
/* 274 */     if (paramString2 == null || paramString2.equalsIgnoreCase("DES")) {
/* 275 */       this.keyType = 3;
/* 276 */     } else if (paramString2.equalsIgnoreCase("DESede")) {
/* 277 */       this.keyType = 16;
/* 278 */     } else if (paramString2.equalsIgnoreCase("AES128")) {
/* 279 */       this.keyType = 17;
/* 280 */     } else if (paramString2.equalsIgnoreCase("ArcFourHmac")) {
/* 281 */       this.keyType = 23;
/* 282 */     } else if (paramString2.equalsIgnoreCase("AES256")) {
/* 283 */       this.keyType = 18;
/*     */       
/* 285 */       if (!EType.isSupported(this.keyType)) {
/* 286 */         throw new IllegalArgumentException("Algorithm " + paramString2 + " not enabled");
/*     */       }
/*     */     } else {
/*     */       
/* 290 */       throw new IllegalArgumentException("Algorithm " + paramString2 + " not supported");
/*     */     } 
/*     */ 
/*     */     
/* 294 */     this.keyValue = stringToKey(paramArrayOfchar, paramString1, null, this.keyType);
/* 295 */     this.kvno = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionKey(EncryptionKey paramEncryptionKey) throws KrbCryptoException {
/* 306 */     this.keyValue = Confounder.bytes(paramEncryptionKey.keyValue.length);
/* 307 */     for (byte b = 0; b < this.keyValue.length; b++) {
/* 308 */       this.keyValue[b] = (byte)(this.keyValue[b] ^ paramEncryptionKey.keyValue[b]);
/*     */     }
/* 310 */     this.keyType = paramEncryptionKey.keyType;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 315 */       if (this.keyType == 3 || this.keyType == 1) {
/*     */ 
/*     */         
/* 318 */         if (!DESKeySpec.isParityAdjusted(this.keyValue, 0)) {
/* 319 */           this.keyValue = Des.set_parity(this.keyValue);
/*     */         }
/*     */         
/* 322 */         if (DESKeySpec.isWeak(this.keyValue, 0)) {
/* 323 */           this.keyValue[7] = (byte)(this.keyValue[7] ^ 0xF0);
/*     */         }
/*     */       } 
/*     */       
/* 327 */       if (this.keyType == 16) {
/*     */         
/* 329 */         if (!DESedeKeySpec.isParityAdjusted(this.keyValue, 0)) {
/* 330 */           this.keyValue = Des3.parityFix(this.keyValue);
/*     */         }
/*     */         
/* 333 */         byte[] arrayOfByte = new byte[8];
/* 334 */         for (byte b1 = 0; b1 < this.keyValue.length; b1 += 8) {
/* 335 */           System.arraycopy(this.keyValue, b1, arrayOfByte, 0, 8);
/* 336 */           if (DESKeySpec.isWeak(arrayOfByte, 0)) {
/* 337 */             this.keyValue[b1 + 7] = (byte)(this.keyValue[b1 + 7] ^ 0xF0);
/*     */           }
/*     */         } 
/*     */       } 
/* 341 */     } catch (GeneralSecurityException generalSecurityException) {
/* 342 */       KrbCryptoException krbCryptoException = new KrbCryptoException(generalSecurityException.getMessage());
/* 343 */       krbCryptoException.initCause(generalSecurityException);
/* 344 */       throw krbCryptoException;
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
/*     */   public EncryptionKey(DerValue paramDerValue) throws Asn1Exception, IOException {
/* 361 */     if (paramDerValue.getTag() != 48) {
/* 362 */       throw new Asn1Exception(906);
/*     */     }
/* 364 */     DerValue derValue = paramDerValue.getData().getDerValue();
/* 365 */     if ((derValue.getTag() & 0x1F) == 0) {
/* 366 */       this.keyType = derValue.getData().getBigInteger().intValue();
/*     */     } else {
/*     */       
/* 369 */       throw new Asn1Exception(906);
/* 370 */     }  derValue = paramDerValue.getData().getDerValue();
/* 371 */     if ((derValue.getTag() & 0x1F) == 1) {
/* 372 */       this.keyValue = derValue.getData().getOctetString();
/*     */     } else {
/*     */       
/* 375 */       throw new Asn1Exception(906);
/* 376 */     }  if (derValue.getData().available() > 0) {
/* 377 */       throw new Asn1Exception(906);
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
/*     */   public synchronized byte[] asn1Encode() throws Asn1Exception, IOException {
/* 404 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 405 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 406 */     derOutputStream2.putInteger(this.keyType);
/* 407 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/*     */     
/* 409 */     derOutputStream2 = new DerOutputStream();
/* 410 */     derOutputStream2.putOctetString(this.keyValue);
/* 411 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/*     */     
/* 413 */     derOutputStream2 = new DerOutputStream();
/* 414 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 415 */     return derOutputStream2.toByteArray();
/*     */   }
/*     */   
/*     */   public synchronized void destroy() {
/* 419 */     if (this.keyValue != null) {
/* 420 */       for (byte b = 0; b < this.keyValue.length; b++) {
/* 421 */         this.keyValue[b] = 0;
/*     */       }
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
/*     */   public static EncryptionKey parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 444 */     if (paramBoolean && ((byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/*     */     {
/* 446 */       return null;
/*     */     }
/* 448 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 449 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 450 */       throw new Asn1Exception(906);
/*     */     }
/* 452 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 453 */     return new EncryptionKey(derValue2);
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
/*     */   public synchronized void writeKey(CCacheOutputStream paramCCacheOutputStream) throws IOException {
/* 468 */     paramCCacheOutputStream.write16(this.keyType);
/*     */     
/* 470 */     paramCCacheOutputStream.write16(this.keyType);
/* 471 */     paramCCacheOutputStream.write32(this.keyValue.length);
/* 472 */     for (byte b = 0; b < this.keyValue.length; b++) {
/* 473 */       paramCCacheOutputStream.write8(this.keyValue[b]);
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString() {
/* 478 */     return new String("EncryptionKey: keyType=" + this.keyType + " kvno=" + this.kvno + " keyValue (hex dump)=" + ((this.keyValue == null || this.keyValue.length == 0) ? " Empty Key" : ('\n' + Krb5.hexDumper
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 483 */         .encodeBuffer(this.keyValue) + '\n')));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncryptionKey findKey(int paramInt, EncryptionKey[] paramArrayOfEncryptionKey) throws KrbException {
/* 492 */     return findKey(paramInt, null, paramArrayOfEncryptionKey);
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
/*     */   private static boolean versionMatches(Integer paramInteger1, Integer paramInteger2) {
/* 506 */     if (paramInteger1 == null || paramInteger1.intValue() == 0 || paramInteger2 == null || paramInteger2.intValue() == 0) {
/* 507 */       return true;
/*     */     }
/* 509 */     return paramInteger1.equals(paramInteger2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EncryptionKey findKey(int paramInt, Integer paramInteger, EncryptionKey[] paramArrayOfEncryptionKey) throws KrbException {
/* 520 */     if (!EType.isSupported(paramInt)) {
/* 521 */       throw new KrbException("Encryption type " + 
/* 522 */           EType.toString(paramInt) + " is not supported/enabled");
/*     */     }
/*     */ 
/*     */     
/* 526 */     boolean bool = false;
/*     */ 
/*     */ 
/*     */     
/* 530 */     int i = 0;
/* 531 */     EncryptionKey encryptionKey = null;
/*     */     byte b;
/* 533 */     for (b = 0; b < paramArrayOfEncryptionKey.length; b++) {
/* 534 */       int j = paramArrayOfEncryptionKey[b].getEType();
/* 535 */       if (EType.isSupported(j)) {
/* 536 */         Integer integer = paramArrayOfEncryptionKey[b].getKeyVersionNumber();
/* 537 */         if (paramInt == j) {
/* 538 */           bool = true;
/* 539 */           if (versionMatches(paramInteger, integer))
/* 540 */             return paramArrayOfEncryptionKey[b]; 
/* 541 */           if (integer.intValue() > i) {
/*     */             
/* 543 */             encryptionKey = paramArrayOfEncryptionKey[b];
/* 544 */             i = integer.intValue();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 552 */     if (paramInt == 1 || paramInt == 3)
/*     */     {
/* 554 */       for (b = 0; b < paramArrayOfEncryptionKey.length; b++) {
/* 555 */         int j = paramArrayOfEncryptionKey[b].getEType();
/* 556 */         if (j == 1 || j == 3) {
/*     */           
/* 558 */           Integer integer = paramArrayOfEncryptionKey[b].getKeyVersionNumber();
/* 559 */           bool = true;
/* 560 */           if (versionMatches(paramInteger, integer))
/* 561 */             return new EncryptionKey(paramInt, paramArrayOfEncryptionKey[b].getBytes()); 
/* 562 */           if (integer.intValue() > i) {
/* 563 */             encryptionKey = new EncryptionKey(paramInt, paramArrayOfEncryptionKey[b].getBytes());
/* 564 */             i = integer.intValue();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 569 */     if (bool) {
/* 570 */       return encryptionKey;
/*     */     }
/*     */ 
/*     */     
/* 574 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/EncryptionKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */