/*      */ package sun.security.jgss.krb5;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.security.GeneralSecurityException;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import javax.crypto.Cipher;
/*      */ import javax.crypto.CipherInputStream;
/*      */ import javax.crypto.CipherOutputStream;
/*      */ import javax.crypto.spec.IvParameterSpec;
/*      */ import javax.crypto.spec.SecretKeySpec;
/*      */ import org.ietf.jgss.GSSException;
/*      */ import sun.security.krb5.EncryptionKey;
/*      */ import sun.security.krb5.internal.crypto.Aes128;
/*      */ import sun.security.krb5.internal.crypto.Aes256;
/*      */ import sun.security.krb5.internal.crypto.ArcFourHmac;
/*      */ import sun.security.krb5.internal.crypto.Des3;
/*      */ import sun.security.krb5.internal.crypto.EType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class CipherHelper
/*      */ {
/*      */   private static final int KG_USAGE_SEAL = 22;
/*      */   private static final int KG_USAGE_SIGN = 23;
/*      */   private static final int KG_USAGE_SEQ = 24;
/*      */   private static final int DES_CHECKSUM_SIZE = 8;
/*      */   private static final int DES_IV_SIZE = 8;
/*      */   private static final int AES_IV_SIZE = 16;
/*      */   private static final int HMAC_CHECKSUM_SIZE = 8;
/*      */   private static final int KG_USAGE_SIGN_MS = 15;
/*   68 */   private static final boolean DEBUG = Krb5Util.DEBUG;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   74 */   private static final byte[] ZERO_IV = new byte[8];
/*   75 */   private static final byte[] ZERO_IV_AES = new byte[16];
/*      */   private int etype;
/*      */   private int sgnAlg;
/*      */   private int sealAlg;
/*      */   private byte[] keybytes;
/*      */   
/*      */   CipherHelper(EncryptionKey paramEncryptionKey) throws GSSException {
/*   82 */     this.etype = paramEncryptionKey.getEType();
/*   83 */     this.keybytes = paramEncryptionKey.getBytes();
/*      */     
/*   85 */     switch (this.etype) {
/*      */       case 1:
/*      */       case 3:
/*   88 */         this.sgnAlg = 0;
/*   89 */         this.sealAlg = 0;
/*      */         return;
/*      */       
/*      */       case 16:
/*   93 */         this.sgnAlg = 1024;
/*   94 */         this.sealAlg = 512;
/*      */         return;
/*      */       
/*      */       case 23:
/*   98 */         this.sgnAlg = 4352;
/*   99 */         this.sealAlg = 4096;
/*      */         return;
/*      */       
/*      */       case 17:
/*      */       case 18:
/*  104 */         this.sgnAlg = -1;
/*  105 */         this.sealAlg = -1;
/*      */         return;
/*      */     } 
/*      */     
/*  109 */     throw new GSSException(11, -1, "Unsupported encryption type: " + this.etype);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   int getSgnAlg() {
/*  115 */     return this.sgnAlg;
/*      */   }
/*      */   
/*      */   int getSealAlg() {
/*  119 */     return this.sealAlg;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   int getProto() {
/*  125 */     return EType.isNewer(this.etype) ? 1 : 0;
/*      */   }
/*      */   
/*      */   int getEType() {
/*  129 */     return this.etype;
/*      */   }
/*      */   
/*      */   boolean isArcFour() {
/*  133 */     boolean bool = false;
/*  134 */     if (this.etype == 23) {
/*  135 */       bool = true;
/*      */     }
/*  137 */     return bool; } byte[] calculateChecksum(int paramInt1, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int paramInt3, int paramInt4) throws GSSException {
/*      */     byte[] arrayOfByte1;
/*      */     boolean bool1;
/*      */     int i;
/*      */     byte[] arrayOfByte2;
/*      */     boolean bool2;
/*      */     int j;
/*  144 */     switch (paramInt1) {
/*      */       
/*      */       case 0:
/*      */         
/*      */         try {
/*      */ 
/*      */           
/*  151 */           MessageDigest messageDigest = MessageDigest.getInstance("MD5");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  157 */           messageDigest.update(paramArrayOfbyte1);
/*      */ 
/*      */           
/*  160 */           messageDigest.update(paramArrayOfbyte3, paramInt2, paramInt3);
/*      */           
/*  162 */           if (paramArrayOfbyte2 != null)
/*      */           {
/*      */ 
/*      */             
/*  166 */             messageDigest.update(paramArrayOfbyte2);
/*      */           }
/*      */ 
/*      */           
/*  170 */           paramArrayOfbyte3 = messageDigest.digest();
/*  171 */           paramInt2 = 0;
/*  172 */           paramInt3 = paramArrayOfbyte3.length;
/*      */ 
/*      */           
/*  175 */           paramArrayOfbyte1 = null;
/*  176 */           paramArrayOfbyte2 = null;
/*  177 */         } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*      */           
/*  179 */           GSSException gSSException = new GSSException(11, -1, "Could not get MD5 Message Digest - " + noSuchAlgorithmException.getMessage());
/*  180 */           gSSException.initCause(noSuchAlgorithmException);
/*  181 */           throw gSSException;
/*      */         } 
/*      */ 
/*      */       
/*      */       case 512:
/*  186 */         return getDesCbcChecksum(this.keybytes, paramArrayOfbyte1, paramArrayOfbyte3, paramInt2, paramInt3);
/*      */ 
/*      */ 
/*      */       
/*      */       case 1024:
/*  191 */         if (paramArrayOfbyte1 == null && paramArrayOfbyte2 == null) {
/*  192 */           arrayOfByte1 = paramArrayOfbyte3;
/*  193 */           i = paramInt3;
/*  194 */           bool1 = paramInt2;
/*      */         } else {
/*  196 */           i = ((paramArrayOfbyte1 != null) ? paramArrayOfbyte1.length : 0) + paramInt3 + ((paramArrayOfbyte2 != null) ? paramArrayOfbyte2.length : 0);
/*      */ 
/*      */           
/*  199 */           arrayOfByte1 = new byte[i];
/*  200 */           int k = 0;
/*  201 */           if (paramArrayOfbyte1 != null) {
/*  202 */             System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte1, 0, paramArrayOfbyte1.length);
/*  203 */             k = paramArrayOfbyte1.length;
/*      */           } 
/*  205 */           System.arraycopy(paramArrayOfbyte3, paramInt2, arrayOfByte1, k, paramInt3);
/*  206 */           k += paramInt3;
/*  207 */           if (paramArrayOfbyte2 != null) {
/*  208 */             System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte1, k, paramArrayOfbyte2.length);
/*      */           }
/*      */           
/*  211 */           bool1 = false;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  229 */           arrayOfByte2 = Des3.calculateChecksum(this.keybytes, 23, arrayOfByte1, bool1, i);
/*      */ 
/*      */ 
/*      */           
/*  233 */           return arrayOfByte2;
/*  234 */         } catch (GeneralSecurityException generalSecurityException) {
/*      */ 
/*      */           
/*  237 */           GSSException gSSException = new GSSException(11, -1, "Could not use HMAC-SHA1-DES3-KD signing algorithm - " + generalSecurityException.getMessage());
/*  238 */           gSSException.initCause(generalSecurityException);
/*  239 */           throw gSSException;
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4352:
/*  245 */         if (paramArrayOfbyte1 == null && paramArrayOfbyte2 == null) {
/*  246 */           arrayOfByte2 = paramArrayOfbyte3;
/*  247 */           j = paramInt3;
/*  248 */           bool2 = paramInt2;
/*      */         } else {
/*  250 */           j = ((paramArrayOfbyte1 != null) ? paramArrayOfbyte1.length : 0) + paramInt3 + ((paramArrayOfbyte2 != null) ? paramArrayOfbyte2.length : 0);
/*      */ 
/*      */           
/*  253 */           arrayOfByte2 = new byte[j];
/*  254 */           int k = 0;
/*      */           
/*  256 */           if (paramArrayOfbyte1 != null) {
/*  257 */             System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte2, 0, paramArrayOfbyte1.length);
/*  258 */             k = paramArrayOfbyte1.length;
/*      */           } 
/*  260 */           System.arraycopy(paramArrayOfbyte3, paramInt2, arrayOfByte2, k, paramInt3);
/*  261 */           k += paramInt3;
/*  262 */           if (paramArrayOfbyte2 != null) {
/*  263 */             System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte2, k, paramArrayOfbyte2.length);
/*      */           }
/*      */           
/*  266 */           bool2 = false;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  288 */           byte b = 23;
/*  289 */           if (paramInt4 == 257) {
/*  290 */             b = 15;
/*      */           }
/*  292 */           byte[] arrayOfByte3 = ArcFourHmac.calculateChecksum(this.keybytes, b, arrayOfByte2, bool2, j);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  298 */           byte[] arrayOfByte4 = new byte[getChecksumLength()];
/*  299 */           System.arraycopy(arrayOfByte3, 0, arrayOfByte4, 0, arrayOfByte4.length);
/*      */ 
/*      */           
/*  302 */           return arrayOfByte4;
/*  303 */         } catch (GeneralSecurityException generalSecurityException) {
/*      */ 
/*      */           
/*  306 */           GSSException gSSException = new GSSException(11, -1, "Could not use HMAC_MD5_ARCFOUR signing algorithm - " + generalSecurityException.getMessage());
/*  307 */           gSSException.initCause(generalSecurityException);
/*  308 */           throw gSSException;
/*      */         } 
/*      */     } 
/*      */     
/*  312 */     throw new GSSException(11, -1, "Unsupported signing algorithm: " + this.sgnAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   byte[] calculateChecksum(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2, int paramInt3) throws GSSException {
/*  322 */     int i = ((paramArrayOfbyte1 != null) ? paramArrayOfbyte1.length : 0) + paramInt2;
/*      */ 
/*      */     
/*  325 */     byte[] arrayOfByte = new byte[i];
/*      */ 
/*      */     
/*  328 */     System.arraycopy(paramArrayOfbyte2, paramInt1, arrayOfByte, 0, paramInt2);
/*      */ 
/*      */     
/*  331 */     if (paramArrayOfbyte1 != null) {
/*  332 */       System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte, paramInt2, paramArrayOfbyte1.length);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  337 */     switch (this.etype) {
/*      */       case 17:
/*      */         try {
/*  340 */           return Aes128.calculateChecksum(this.keybytes, paramInt3, arrayOfByte, 0, i);
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  345 */         catch (GeneralSecurityException generalSecurityException) {
/*      */ 
/*      */           
/*  348 */           GSSException gSSException = new GSSException(11, -1, "Could not use AES128 signing algorithm - " + generalSecurityException.getMessage());
/*  349 */           gSSException.initCause(generalSecurityException);
/*  350 */           throw gSSException;
/*      */         } 
/*      */       
/*      */       case 18:
/*      */         try {
/*  355 */           return Aes256.calculateChecksum(this.keybytes, paramInt3, arrayOfByte, 0, i);
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  360 */         catch (GeneralSecurityException generalSecurityException) {
/*      */ 
/*      */           
/*  363 */           GSSException gSSException = new GSSException(11, -1, "Could not use AES256 signing algorithm - " + generalSecurityException.getMessage());
/*  364 */           gSSException.initCause(generalSecurityException);
/*  365 */           throw gSSException;
/*      */         } 
/*      */     } 
/*      */     
/*  369 */     throw new GSSException(11, -1, "Unsupported encryption type: " + this.etype);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   byte[] encryptSeq(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2) throws GSSException {
/*      */     byte[] arrayOfByte1;
/*      */     byte[] arrayOfByte2;
/*  377 */     switch (this.sgnAlg) {
/*      */       case 0:
/*      */       case 512:
/*      */         try {
/*  381 */           Cipher cipher = getInitializedDes(true, this.keybytes, paramArrayOfbyte1);
/*  382 */           return cipher.doFinal(paramArrayOfbyte2, paramInt1, paramInt2);
/*      */         }
/*  384 */         catch (GeneralSecurityException generalSecurityException) {
/*      */ 
/*      */           
/*  387 */           GSSException gSSException = new GSSException(11, -1, "Could not encrypt sequence number using DES - " + generalSecurityException.getMessage());
/*  388 */           gSSException.initCause(generalSecurityException);
/*  389 */           throw gSSException;
/*      */         } 
/*      */ 
/*      */       
/*      */       case 1024:
/*  394 */         if (paramArrayOfbyte1.length == 8) {
/*  395 */           arrayOfByte1 = paramArrayOfbyte1;
/*      */         } else {
/*  397 */           arrayOfByte1 = new byte[8];
/*  398 */           System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte1, 0, 8);
/*      */         } 
/*      */         try {
/*  401 */           return Des3.encryptRaw(this.keybytes, 24, arrayOfByte1, paramArrayOfbyte2, paramInt1, paramInt2);
/*      */         }
/*  403 */         catch (Exception exception) {
/*      */ 
/*      */ 
/*      */           
/*  407 */           GSSException gSSException = new GSSException(11, -1, "Could not encrypt sequence number using DES3-KD - " + exception.getMessage());
/*  408 */           gSSException.initCause(exception);
/*  409 */           throw gSSException;
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4352:
/*  415 */         if (paramArrayOfbyte1.length == 8) {
/*  416 */           arrayOfByte2 = paramArrayOfbyte1;
/*      */         } else {
/*  418 */           arrayOfByte2 = new byte[8];
/*  419 */           System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte2, 0, 8);
/*      */         } 
/*      */         
/*      */         try {
/*  423 */           return ArcFourHmac.encryptSeq(this.keybytes, 24, arrayOfByte2, paramArrayOfbyte2, paramInt1, paramInt2);
/*      */         }
/*  425 */         catch (Exception exception) {
/*      */ 
/*      */ 
/*      */           
/*  429 */           GSSException gSSException = new GSSException(11, -1, "Could not encrypt sequence number using RC4-HMAC - " + exception.getMessage());
/*  430 */           gSSException.initCause(exception);
/*  431 */           throw gSSException;
/*      */         } 
/*      */     } 
/*      */     
/*  435 */     throw new GSSException(11, -1, "Unsupported signing algorithm: " + this.sgnAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   byte[] decryptSeq(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2) throws GSSException {
/*      */     byte[] arrayOfByte1;
/*      */     byte[] arrayOfByte2;
/*  443 */     switch (this.sgnAlg) {
/*      */       case 0:
/*      */       case 512:
/*      */         try {
/*  447 */           Cipher cipher = getInitializedDes(false, this.keybytes, paramArrayOfbyte1);
/*  448 */           return cipher.doFinal(paramArrayOfbyte2, paramInt1, paramInt2);
/*  449 */         } catch (GeneralSecurityException generalSecurityException) {
/*      */ 
/*      */           
/*  452 */           GSSException gSSException = new GSSException(11, -1, "Could not decrypt sequence number using DES - " + generalSecurityException.getMessage());
/*  453 */           gSSException.initCause(generalSecurityException);
/*  454 */           throw gSSException;
/*      */         } 
/*      */ 
/*      */       
/*      */       case 1024:
/*  459 */         if (paramArrayOfbyte1.length == 8) {
/*  460 */           arrayOfByte1 = paramArrayOfbyte1;
/*      */         } else {
/*  462 */           arrayOfByte1 = new byte[8];
/*  463 */           System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte1, 0, 8);
/*      */         } 
/*      */         
/*      */         try {
/*  467 */           return Des3.decryptRaw(this.keybytes, 24, arrayOfByte1, paramArrayOfbyte2, paramInt1, paramInt2);
/*      */         }
/*  469 */         catch (Exception exception) {
/*      */ 
/*      */ 
/*      */           
/*  473 */           GSSException gSSException = new GSSException(11, -1, "Could not decrypt sequence number using DES3-KD - " + exception.getMessage());
/*  474 */           gSSException.initCause(exception);
/*  475 */           throw gSSException;
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4352:
/*  481 */         if (paramArrayOfbyte1.length == 8) {
/*  482 */           arrayOfByte2 = paramArrayOfbyte1;
/*      */         } else {
/*  484 */           arrayOfByte2 = new byte[8];
/*  485 */           System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte2, 0, 8);
/*      */         } 
/*      */         
/*      */         try {
/*  489 */           return ArcFourHmac.decryptSeq(this.keybytes, 24, arrayOfByte2, paramArrayOfbyte2, paramInt1, paramInt2);
/*      */         }
/*  491 */         catch (Exception exception) {
/*      */ 
/*      */ 
/*      */           
/*  495 */           GSSException gSSException = new GSSException(11, -1, "Could not decrypt sequence number using RC4-HMAC - " + exception.getMessage());
/*  496 */           gSSException.initCause(exception);
/*  497 */           throw gSSException;
/*      */         } 
/*      */     } 
/*      */     
/*  501 */     throw new GSSException(11, -1, "Unsupported signing algorithm: " + this.sgnAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   int getChecksumLength() throws GSSException {
/*  507 */     switch (this.etype) {
/*      */       case 1:
/*      */       case 3:
/*  510 */         return 8;
/*      */       
/*      */       case 16:
/*  513 */         return Des3.getChecksumLength();
/*      */       
/*      */       case 17:
/*  516 */         return Aes128.getChecksumLength();
/*      */       case 18:
/*  518 */         return Aes256.getChecksumLength();
/*      */ 
/*      */       
/*      */       case 23:
/*  522 */         return 8;
/*      */     } 
/*      */     
/*  525 */     throw new GSSException(11, -1, "Unsupported encryption type: " + this.etype);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void decryptData(WrapToken paramWrapToken, byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3) throws GSSException {
/*  538 */     switch (this.sealAlg) {
/*      */       case 0:
/*  540 */         desCbcDecrypt(paramWrapToken, getDesEncryptionKey(this.keybytes), paramArrayOfbyte1, paramInt1, paramInt2, paramArrayOfbyte2, paramInt3);
/*      */         return;
/*      */ 
/*      */       
/*      */       case 512:
/*  545 */         des3KdDecrypt(paramWrapToken, paramArrayOfbyte1, paramInt1, paramInt2, paramArrayOfbyte2, paramInt3);
/*      */         return;
/*      */       
/*      */       case 4096:
/*  549 */         arcFourDecrypt(paramWrapToken, paramArrayOfbyte1, paramInt1, paramInt2, paramArrayOfbyte2, paramInt3);
/*      */         return;
/*      */     } 
/*      */     
/*  553 */     throw new GSSException(11, -1, "Unsupported seal algorithm: " + this.sealAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void decryptData(WrapToken_v2 paramWrapToken_v2, byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4) throws GSSException {
/*  568 */     switch (this.etype) {
/*      */       case 17:
/*  570 */         aes128Decrypt(paramWrapToken_v2, paramArrayOfbyte1, paramInt1, paramInt2, paramArrayOfbyte2, paramInt3, paramInt4);
/*      */         return;
/*      */       
/*      */       case 18:
/*  574 */         aes256Decrypt(paramWrapToken_v2, paramArrayOfbyte1, paramInt1, paramInt2, paramArrayOfbyte2, paramInt3, paramInt4);
/*      */         return;
/*      */     } 
/*      */     
/*  578 */     throw new GSSException(11, -1, "Unsupported etype: " + this.etype);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void decryptData(WrapToken paramWrapToken, InputStream paramInputStream, int paramInt1, byte[] paramArrayOfbyte, int paramInt2) throws GSSException, IOException {
/*      */     byte[] arrayOfByte1;
/*      */     byte[] arrayOfByte2;
/*  587 */     switch (this.sealAlg) {
/*      */       case 0:
/*  589 */         desCbcDecrypt(paramWrapToken, getDesEncryptionKey(this.keybytes), paramInputStream, paramInt1, paramArrayOfbyte, paramInt2);
/*      */         return;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 512:
/*  596 */         arrayOfByte1 = new byte[paramInt1];
/*      */         try {
/*  598 */           Krb5Token.readFully(paramInputStream, arrayOfByte1, 0, paramInt1);
/*  599 */         } catch (IOException iOException) {
/*  600 */           GSSException gSSException = new GSSException(10, -1, "Cannot read complete token");
/*      */ 
/*      */           
/*  603 */           gSSException.initCause(iOException);
/*  604 */           throw gSSException;
/*      */         } 
/*      */         
/*  607 */         des3KdDecrypt(paramWrapToken, arrayOfByte1, 0, paramInt1, paramArrayOfbyte, paramInt2);
/*      */         return;
/*      */ 
/*      */ 
/*      */       
/*      */       case 4096:
/*  613 */         arrayOfByte2 = new byte[paramInt1];
/*      */         try {
/*  615 */           Krb5Token.readFully(paramInputStream, arrayOfByte2, 0, paramInt1);
/*  616 */         } catch (IOException iOException) {
/*  617 */           GSSException gSSException = new GSSException(10, -1, "Cannot read complete token");
/*      */ 
/*      */           
/*  620 */           gSSException.initCause(iOException);
/*  621 */           throw gSSException;
/*      */         } 
/*      */         
/*  624 */         arcFourDecrypt(paramWrapToken, arrayOfByte2, 0, paramInt1, paramArrayOfbyte, paramInt2);
/*      */         return;
/*      */     } 
/*      */     
/*  628 */     throw new GSSException(11, -1, "Unsupported seal algorithm: " + this.sealAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void decryptData(WrapToken_v2 paramWrapToken_v2, InputStream paramInputStream, int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) throws GSSException, IOException {
/*  638 */     byte[] arrayOfByte = new byte[paramInt1];
/*      */     try {
/*  640 */       Krb5Token.readFully(paramInputStream, arrayOfByte, 0, paramInt1);
/*  641 */     } catch (IOException iOException) {
/*  642 */       GSSException gSSException = new GSSException(10, -1, "Cannot read complete token");
/*      */ 
/*      */       
/*  645 */       gSSException.initCause(iOException);
/*  646 */       throw gSSException;
/*      */     } 
/*  648 */     switch (this.etype) {
/*      */       case 17:
/*  650 */         aes128Decrypt(paramWrapToken_v2, arrayOfByte, 0, paramInt1, paramArrayOfbyte, paramInt2, paramInt3);
/*      */         return;
/*      */       
/*      */       case 18:
/*  654 */         aes256Decrypt(paramWrapToken_v2, arrayOfByte, 0, paramInt1, paramArrayOfbyte, paramInt2, paramInt3);
/*      */         return;
/*      */     } 
/*      */     
/*  658 */     throw new GSSException(11, -1, "Unsupported etype: " + this.etype);
/*      */   }
/*      */ 
/*      */   
/*      */   void encryptData(WrapToken paramWrapToken, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2, byte[] paramArrayOfbyte3, OutputStream paramOutputStream) throws GSSException, IOException {
/*      */     Cipher cipher;
/*      */     CipherOutputStream cipherOutputStream;
/*      */     byte[] arrayOfByte1;
/*      */     byte[] arrayOfByte2;
/*  667 */     switch (this.sealAlg) {
/*      */       
/*      */       case 0:
/*  670 */         cipher = getInitializedDes(true, getDesEncryptionKey(this.keybytes), ZERO_IV);
/*      */         
/*  672 */         cipherOutputStream = new CipherOutputStream(paramOutputStream, cipher);
/*      */         
/*  674 */         cipherOutputStream.write(paramArrayOfbyte1);
/*      */         
/*  676 */         cipherOutputStream.write(paramArrayOfbyte2, paramInt1, paramInt2);
/*      */         
/*  678 */         cipherOutputStream.write(paramArrayOfbyte3);
/*      */         return;
/*      */       
/*      */       case 512:
/*  682 */         arrayOfByte1 = des3KdEncrypt(paramArrayOfbyte1, paramArrayOfbyte2, paramInt1, paramInt2, paramArrayOfbyte3);
/*      */ 
/*      */ 
/*      */         
/*  686 */         paramOutputStream.write(arrayOfByte1);
/*      */         return;
/*      */       
/*      */       case 4096:
/*  690 */         arrayOfByte2 = arcFourEncrypt(paramWrapToken, paramArrayOfbyte1, paramArrayOfbyte2, paramInt1, paramInt2, paramArrayOfbyte3);
/*      */ 
/*      */ 
/*      */         
/*  694 */         paramOutputStream.write(arrayOfByte2);
/*      */         return;
/*      */     } 
/*      */     
/*  698 */     throw new GSSException(11, -1, "Unsupported seal algorithm: " + this.sealAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   byte[] encryptData(WrapToken_v2 paramWrapToken_v2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt1, int paramInt2, int paramInt3) throws GSSException {
/*  716 */     switch (this.etype) {
/*      */       case 17:
/*  718 */         return aes128Encrypt(paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt1, paramInt2, paramInt3);
/*      */       
/*      */       case 18:
/*  721 */         return aes256Encrypt(paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt1, paramInt2, paramInt3);
/*      */     } 
/*      */     
/*  724 */     throw new GSSException(11, -1, "Unsupported etype: " + this.etype);
/*      */   }
/*      */ 
/*      */   
/*      */   void encryptData(WrapToken paramWrapToken, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, int paramInt3) throws GSSException {
/*      */     int i;
/*      */     Cipher cipher;
/*      */     byte[] arrayOfByte1;
/*      */     byte[] arrayOfByte2;
/*  733 */     switch (this.sealAlg) {
/*      */       case 0:
/*  735 */         i = paramInt3;
/*      */         
/*  737 */         cipher = getInitializedDes(true, getDesEncryptionKey(this.keybytes), ZERO_IV);
/*      */ 
/*      */         
/*      */         try {
/*  741 */           i += cipher.update(paramArrayOfbyte1, 0, paramArrayOfbyte1.length, paramArrayOfbyte4, i);
/*      */ 
/*      */           
/*  744 */           i += cipher.update(paramArrayOfbyte2, paramInt1, paramInt2, paramArrayOfbyte4, i);
/*      */ 
/*      */           
/*  747 */           cipher.update(paramArrayOfbyte3, 0, paramArrayOfbyte3.length, paramArrayOfbyte4, i);
/*      */           
/*  749 */           cipher.doFinal();
/*  750 */         } catch (GeneralSecurityException generalSecurityException) {
/*      */           
/*  752 */           GSSException gSSException = new GSSException(11, -1, "Could not use DES Cipher - " + generalSecurityException.getMessage());
/*  753 */           gSSException.initCause(generalSecurityException);
/*  754 */           throw gSSException;
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 512:
/*  759 */         arrayOfByte1 = des3KdEncrypt(paramArrayOfbyte1, paramArrayOfbyte2, paramInt1, paramInt2, paramArrayOfbyte3);
/*      */         
/*  761 */         System.arraycopy(arrayOfByte1, 0, paramArrayOfbyte4, paramInt3, arrayOfByte1.length);
/*      */         return;
/*      */       
/*      */       case 4096:
/*  765 */         arrayOfByte2 = arcFourEncrypt(paramWrapToken, paramArrayOfbyte1, paramArrayOfbyte2, paramInt1, paramInt2, paramArrayOfbyte3);
/*      */         
/*  767 */         System.arraycopy(arrayOfByte2, 0, paramArrayOfbyte4, paramInt3, arrayOfByte2.length);
/*      */         return;
/*      */     } 
/*      */     
/*  771 */     throw new GSSException(11, -1, "Unsupported seal algorithm: " + this.sealAlg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int encryptData(WrapToken_v2 paramWrapToken_v2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt1, int paramInt2, byte[] paramArrayOfbyte4, int paramInt3, int paramInt4) throws GSSException {
/*  789 */     byte[] arrayOfByte = null;
/*  790 */     switch (this.etype) {
/*      */       case 17:
/*  792 */         arrayOfByte = aes128Encrypt(paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt1, paramInt2, paramInt4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  803 */         System.arraycopy(arrayOfByte, 0, paramArrayOfbyte4, paramInt3, arrayOfByte.length);
/*  804 */         return arrayOfByte.length;case 18: arrayOfByte = aes256Encrypt(paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3, paramInt1, paramInt2, paramInt4); System.arraycopy(arrayOfByte, 0, paramArrayOfbyte4, paramInt3, arrayOfByte.length); return arrayOfByte.length;
/*      */     } 
/*      */     throw new GSSException(11, -1, "Unsupported etype: " + this.etype);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] getDesCbcChecksum(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt1, int paramInt2) throws GSSException {
/*  828 */     Cipher cipher = getInitializedDes(true, paramArrayOfbyte1, ZERO_IV);
/*      */     
/*  830 */     int i = cipher.getBlockSize();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  838 */     byte[] arrayOfByte = new byte[i];
/*      */     
/*  840 */     int j = paramInt2 / i;
/*  841 */     int k = paramInt2 % i;
/*  842 */     if (k == 0) {
/*      */       
/*  844 */       j--;
/*  845 */       System.arraycopy(paramArrayOfbyte3, paramInt1 + j * i, arrayOfByte, 0, i);
/*      */     } else {
/*      */       
/*  848 */       System.arraycopy(paramArrayOfbyte3, paramInt1 + j * i, arrayOfByte, 0, k);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  854 */       byte[] arrayOfByte1 = new byte[Math.max(i, (paramArrayOfbyte2 == null) ? i : paramArrayOfbyte2.length)];
/*      */ 
/*      */       
/*  857 */       if (paramArrayOfbyte2 != null)
/*      */       {
/*  859 */         cipher.update(paramArrayOfbyte2, 0, paramArrayOfbyte2.length, arrayOfByte1, 0);
/*      */       }
/*      */ 
/*      */       
/*  863 */       for (byte b = 0; b < j; b++) {
/*  864 */         cipher.update(paramArrayOfbyte3, paramInt1, i, arrayOfByte1, 0);
/*      */         
/*  866 */         paramInt1 += i;
/*      */       } 
/*      */ 
/*      */       
/*  870 */       byte[] arrayOfByte2 = new byte[i];
/*  871 */       cipher.update(arrayOfByte, 0, i, arrayOfByte2, 0);
/*  872 */       cipher.doFinal();
/*      */       
/*  874 */       return arrayOfByte2;
/*  875 */     } catch (GeneralSecurityException generalSecurityException) {
/*      */       
/*  877 */       GSSException gSSException = new GSSException(11, -1, "Could not use DES Cipher - " + generalSecurityException.getMessage());
/*  878 */       gSSException.initCause(generalSecurityException);
/*  879 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Cipher getInitializedDes(boolean paramBoolean, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) throws GSSException {
/*      */     try {
/*  897 */       IvParameterSpec ivParameterSpec = new IvParameterSpec(paramArrayOfbyte2);
/*  898 */       SecretKeySpec secretKeySpec = new SecretKeySpec(paramArrayOfbyte1, "DES");
/*      */       
/*  900 */       Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
/*  901 */       cipher.init(paramBoolean ? 1 : 2, secretKeySpec, ivParameterSpec);
/*      */ 
/*      */       
/*  904 */       return cipher;
/*  905 */     } catch (GeneralSecurityException generalSecurityException) {
/*      */       
/*  907 */       GSSException gSSException = new GSSException(11, -1, generalSecurityException.getMessage());
/*  908 */       gSSException.initCause(generalSecurityException);
/*  909 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void desCbcDecrypt(WrapToken paramWrapToken, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2, byte[] paramArrayOfbyte3, int paramInt3) throws GSSException {
/*      */     try {
/*  935 */       int i = 0;
/*      */       
/*  937 */       Cipher cipher = getInitializedDes(false, paramArrayOfbyte1, ZERO_IV);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  943 */       i = cipher.update(paramArrayOfbyte2, paramInt1, 8, paramWrapToken.confounder);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  949 */       paramInt1 += 8;
/*  950 */       paramInt2 -= 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  959 */       int j = cipher.getBlockSize();
/*  960 */       int k = paramInt2 / j - 1;
/*      */ 
/*      */       
/*  963 */       for (byte b = 0; b < k; b++) {
/*  964 */         i = cipher.update(paramArrayOfbyte2, paramInt1, j, paramArrayOfbyte3, paramInt3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  970 */         paramInt1 += j;
/*  971 */         paramInt3 += j;
/*      */       } 
/*      */ 
/*      */       
/*  975 */       byte[] arrayOfByte = new byte[j];
/*  976 */       cipher.update(paramArrayOfbyte2, paramInt1, j, arrayOfByte);
/*      */       
/*  978 */       cipher.doFinal();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  985 */       byte b1 = arrayOfByte[j - 1];
/*  986 */       if (b1 < 1 || b1 > 8) {
/*  987 */         throw new GSSException(10, -1, "Invalid padding on Wrap Token");
/*      */       }
/*  989 */       paramWrapToken.padding = WrapToken.pads[b1];
/*  990 */       j -= b1;
/*      */ 
/*      */       
/*  993 */       System.arraycopy(arrayOfByte, 0, paramArrayOfbyte3, paramInt3, j);
/*      */     
/*      */     }
/*  996 */     catch (GeneralSecurityException generalSecurityException) {
/*      */       
/*  998 */       GSSException gSSException = new GSSException(11, -1, "Could not use DES cipher - " + generalSecurityException.getMessage());
/*  999 */       gSSException.initCause(generalSecurityException);
/* 1000 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void desCbcDecrypt(WrapToken paramWrapToken, byte[] paramArrayOfbyte1, InputStream paramInputStream, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws GSSException, IOException {
/* 1024 */     int i = 0;
/*      */     
/* 1026 */     Cipher cipher = getInitializedDes(false, paramArrayOfbyte1, ZERO_IV);
/*      */     
/* 1028 */     WrapTokenInputStream wrapTokenInputStream = new WrapTokenInputStream(paramInputStream, paramInt1);
/*      */     
/* 1030 */     CipherInputStream cipherInputStream = new CipherInputStream(wrapTokenInputStream, cipher);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1036 */     i = cipherInputStream.read(paramWrapToken.confounder);
/*      */     
/* 1038 */     paramInt1 -= i;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1052 */     int j = cipher.getBlockSize();
/* 1053 */     int k = paramInt1 / j - 1;
/*      */ 
/*      */     
/* 1056 */     for (byte b = 0; b < k; b++) {
/*      */       
/* 1058 */       i = cipherInputStream.read(paramArrayOfbyte2, paramInt2, j);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1065 */       paramInt2 += j;
/*      */     } 
/*      */ 
/*      */     
/* 1069 */     byte[] arrayOfByte = new byte[j];
/*      */     
/* 1071 */     i = cipherInputStream.read(arrayOfByte);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1081 */       cipher.doFinal();
/* 1082 */     } catch (GeneralSecurityException generalSecurityException) {
/*      */       
/* 1084 */       GSSException gSSException = new GSSException(11, -1, "Could not use DES cipher - " + generalSecurityException.getMessage());
/* 1085 */       gSSException.initCause(generalSecurityException);
/* 1086 */       throw gSSException;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1094 */     byte b1 = arrayOfByte[j - 1];
/* 1095 */     if (b1 < 1 || b1 > 8) {
/* 1096 */       throw new GSSException(10, -1, "Invalid padding on Wrap Token");
/*      */     }
/* 1098 */     paramWrapToken.padding = WrapToken.pads[b1];
/* 1099 */     j -= b1;
/*      */ 
/*      */     
/* 1102 */     System.arraycopy(arrayOfByte, 0, paramArrayOfbyte2, paramInt2, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static byte[] getDesEncryptionKey(byte[] paramArrayOfbyte) throws GSSException {
/* 1118 */     if (paramArrayOfbyte.length > 8) {
/* 1119 */       throw new GSSException(11, -100, "Invalid DES Key!");
/*      */     }
/*      */     
/* 1122 */     byte[] arrayOfByte = new byte[paramArrayOfbyte.length];
/* 1123 */     for (byte b = 0; b < paramArrayOfbyte.length; b++)
/* 1124 */       arrayOfByte[b] = (byte)(paramArrayOfbyte[b] ^ 0xF0); 
/* 1125 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void des3KdDecrypt(WrapToken paramWrapToken, byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3) throws GSSException {
/*      */     byte[] arrayOfByte;
/*      */     try {
/* 1134 */       arrayOfByte = Des3.decryptRaw(this.keybytes, 22, ZERO_IV, paramArrayOfbyte1, paramInt1, paramInt2);
/*      */     }
/* 1136 */     catch (GeneralSecurityException generalSecurityException) {
/*      */       
/* 1138 */       GSSException gSSException = new GSSException(11, -1, "Could not use DES3-KD Cipher - " + generalSecurityException.getMessage());
/* 1139 */       gSSException.initCause(generalSecurityException);
/* 1140 */       throw gSSException;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1155 */     byte b = arrayOfByte[arrayOfByte.length - 1];
/* 1156 */     if (b < 1 || b > 8) {
/* 1157 */       throw new GSSException(10, -1, "Invalid padding on Wrap Token");
/*      */     }
/*      */     
/* 1160 */     paramWrapToken.padding = WrapToken.pads[b];
/* 1161 */     int i = arrayOfByte.length - 8 - b;
/*      */     
/* 1163 */     System.arraycopy(arrayOfByte, 8, paramArrayOfbyte2, paramInt3, i);
/*      */ 
/*      */ 
/*      */     
/* 1167 */     System.arraycopy(arrayOfByte, 0, paramWrapToken.confounder, 0, 8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] des3KdEncrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2, byte[] paramArrayOfbyte3) throws GSSException {
/* 1176 */     byte[] arrayOfByte = new byte[paramArrayOfbyte1.length + paramInt2 + paramArrayOfbyte3.length];
/* 1177 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte, 0, paramArrayOfbyte1.length);
/* 1178 */     System.arraycopy(paramArrayOfbyte2, paramInt1, arrayOfByte, paramArrayOfbyte1.length, paramInt2);
/* 1179 */     System.arraycopy(paramArrayOfbyte3, 0, arrayOfByte, paramArrayOfbyte1.length + paramInt2, paramArrayOfbyte3.length);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1186 */       return Des3.encryptRaw(this.keybytes, 22, ZERO_IV, arrayOfByte, 0, arrayOfByte.length);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1191 */     catch (Exception exception) {
/*      */ 
/*      */       
/* 1194 */       GSSException gSSException = new GSSException(11, -1, "Could not use DES3-KD Cipher - " + exception.getMessage());
/* 1195 */       gSSException.initCause(exception);
/* 1196 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void arcFourDecrypt(WrapToken paramWrapToken, byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3) throws GSSException {
/* 1207 */     byte[] arrayOfByte2, arrayOfByte1 = decryptSeq(paramWrapToken.getChecksum(), paramWrapToken
/* 1208 */         .getEncSeqNumber(), 0, 8);
/*      */ 
/*      */     
/*      */     try {
/* 1212 */       arrayOfByte2 = ArcFourHmac.decryptRaw(this.keybytes, 22, ZERO_IV, paramArrayOfbyte1, paramInt1, paramInt2, arrayOfByte1);
/*      */     }
/* 1214 */     catch (GeneralSecurityException generalSecurityException) {
/*      */       
/* 1216 */       GSSException gSSException = new GSSException(11, -1, "Could not use ArcFour Cipher - " + generalSecurityException.getMessage());
/* 1217 */       gSSException.initCause(generalSecurityException);
/* 1218 */       throw gSSException;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1233 */     byte b = arrayOfByte2[arrayOfByte2.length - 1];
/* 1234 */     if (b < 1) {
/* 1235 */       throw new GSSException(10, -1, "Invalid padding on Wrap Token");
/*      */     }
/*      */     
/* 1238 */     paramWrapToken.padding = WrapToken.pads[b];
/* 1239 */     int i = arrayOfByte2.length - 8 - b;
/*      */     
/* 1241 */     System.arraycopy(arrayOfByte2, 8, paramArrayOfbyte2, paramInt3, i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1248 */     System.arraycopy(arrayOfByte2, 0, paramWrapToken.confounder, 0, 8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] arcFourEncrypt(WrapToken paramWrapToken, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2, byte[] paramArrayOfbyte3) throws GSSException {
/* 1257 */     byte[] arrayOfByte1 = new byte[paramArrayOfbyte1.length + paramInt2 + paramArrayOfbyte3.length];
/* 1258 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte1, 0, paramArrayOfbyte1.length);
/* 1259 */     System.arraycopy(paramArrayOfbyte2, paramInt1, arrayOfByte1, paramArrayOfbyte1.length, paramInt2);
/* 1260 */     System.arraycopy(paramArrayOfbyte3, 0, arrayOfByte1, paramArrayOfbyte1.length + paramInt2, paramArrayOfbyte3.length);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1266 */     byte[] arrayOfByte2 = new byte[4];
/* 1267 */     WrapToken.writeBigEndian(paramWrapToken.getSequenceNumber(), arrayOfByte2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1273 */       return ArcFourHmac.encryptRaw(this.keybytes, 22, arrayOfByte2, arrayOfByte1, 0, arrayOfByte1.length);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1278 */     catch (Exception exception) {
/*      */ 
/*      */       
/* 1281 */       GSSException gSSException = new GSSException(11, -1, "Could not use ArcFour Cipher - " + exception.getMessage());
/* 1282 */       gSSException.initCause(exception);
/* 1283 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] aes128Encrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt1, int paramInt2, int paramInt3) throws GSSException {
/* 1297 */     byte[] arrayOfByte = new byte[paramArrayOfbyte1.length + paramInt2 + paramArrayOfbyte2.length];
/* 1298 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte, 0, paramArrayOfbyte1.length);
/* 1299 */     System.arraycopy(paramArrayOfbyte3, paramInt1, arrayOfByte, paramArrayOfbyte1.length, paramInt2);
/* 1300 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte, paramArrayOfbyte1.length + paramInt2, paramArrayOfbyte2.length);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1305 */       return Aes128.encryptRaw(this.keybytes, paramInt3, ZERO_IV_AES, arrayOfByte, 0, arrayOfByte.length);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1311 */     catch (Exception exception) {
/*      */ 
/*      */       
/* 1314 */       GSSException gSSException = new GSSException(11, -1, "Could not use AES128 Cipher - " + exception.getMessage());
/* 1315 */       gSSException.initCause(exception);
/* 1316 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void aes128Decrypt(WrapToken_v2 paramWrapToken_v2, byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4) throws GSSException {
/* 1324 */     byte[] arrayOfByte = null;
/*      */     
/*      */     try {
/* 1327 */       arrayOfByte = Aes128.decryptRaw(this.keybytes, paramInt4, ZERO_IV_AES, paramArrayOfbyte1, paramInt1, paramInt2);
/*      */     }
/* 1329 */     catch (GeneralSecurityException generalSecurityException) {
/*      */       
/* 1331 */       GSSException gSSException = new GSSException(11, -1, "Could not use AES128 Cipher - " + generalSecurityException.getMessage());
/* 1332 */       gSSException.initCause(generalSecurityException);
/* 1333 */       throw gSSException;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1346 */     int i = arrayOfByte.length - 16 - 16;
/*      */     
/* 1348 */     System.arraycopy(arrayOfByte, 16, paramArrayOfbyte2, paramInt3, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] aes256Encrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt1, int paramInt2, int paramInt3) throws GSSException {
/* 1366 */     byte[] arrayOfByte = new byte[paramArrayOfbyte1.length + paramInt2 + paramArrayOfbyte2.length];
/* 1367 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte, 0, paramArrayOfbyte1.length);
/* 1368 */     System.arraycopy(paramArrayOfbyte3, paramInt1, arrayOfByte, paramArrayOfbyte1.length, paramInt2);
/* 1369 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte, paramArrayOfbyte1.length + paramInt2, paramArrayOfbyte2.length);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1375 */       return Aes256.encryptRaw(this.keybytes, paramInt3, ZERO_IV_AES, arrayOfByte, 0, arrayOfByte.length);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1380 */     catch (Exception exception) {
/*      */ 
/*      */       
/* 1383 */       GSSException gSSException = new GSSException(11, -1, "Could not use AES256 Cipher - " + exception.getMessage());
/* 1384 */       gSSException.initCause(exception);
/* 1385 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void aes256Decrypt(WrapToken_v2 paramWrapToken_v2, byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4) throws GSSException {
/*      */     byte[] arrayOfByte;
/*      */     try {
/* 1395 */       arrayOfByte = Aes256.decryptRaw(this.keybytes, paramInt4, ZERO_IV_AES, paramArrayOfbyte1, paramInt1, paramInt2);
/*      */     }
/* 1397 */     catch (GeneralSecurityException generalSecurityException) {
/*      */       
/* 1399 */       GSSException gSSException = new GSSException(11, -1, "Could not use AES128 Cipher - " + generalSecurityException.getMessage());
/* 1400 */       gSSException.initCause(generalSecurityException);
/* 1401 */       throw gSSException;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1414 */     int i = arrayOfByte.length - 16 - 16;
/*      */     
/* 1416 */     System.arraycopy(arrayOfByte, 16, paramArrayOfbyte2, paramInt3, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class WrapTokenInputStream
/*      */     extends InputStream
/*      */   {
/*      */     private InputStream is;
/*      */ 
/*      */ 
/*      */     
/*      */     private int length;
/*      */ 
/*      */ 
/*      */     
/*      */     private int remaining;
/*      */ 
/*      */     
/*      */     private int temp;
/*      */ 
/*      */ 
/*      */     
/*      */     public WrapTokenInputStream(InputStream param1InputStream, int param1Int) {
/* 1441 */       this.is = param1InputStream;
/* 1442 */       this.length = param1Int;
/* 1443 */       this.remaining = param1Int;
/*      */     }
/*      */     
/*      */     public final int read() throws IOException {
/* 1447 */       if (this.remaining == 0) {
/* 1448 */         return -1;
/*      */       }
/* 1450 */       this.temp = this.is.read();
/* 1451 */       if (this.temp != -1)
/* 1452 */         this.remaining -= this.temp; 
/* 1453 */       return this.temp;
/*      */     }
/*      */ 
/*      */     
/*      */     public final int read(byte[] param1ArrayOfbyte) throws IOException {
/* 1458 */       if (this.remaining == 0) {
/* 1459 */         return -1;
/*      */       }
/* 1461 */       this.temp = Math.min(this.remaining, param1ArrayOfbyte.length);
/* 1462 */       this.temp = this.is.read(param1ArrayOfbyte, 0, this.temp);
/* 1463 */       if (this.temp != -1)
/* 1464 */         this.remaining -= this.temp; 
/* 1465 */       return this.temp;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 1472 */       if (this.remaining == 0) {
/* 1473 */         return -1;
/*      */       }
/* 1475 */       this.temp = Math.min(this.remaining, param1Int2);
/* 1476 */       this.temp = this.is.read(param1ArrayOfbyte, param1Int1, this.temp);
/* 1477 */       if (this.temp != -1)
/* 1478 */         this.remaining -= this.temp; 
/* 1479 */       return this.temp;
/*      */     }
/*      */ 
/*      */     
/*      */     public final long skip(long param1Long) throws IOException {
/* 1484 */       if (this.remaining == 0) {
/* 1485 */         return 0L;
/*      */       }
/* 1487 */       this.temp = (int)Math.min(this.remaining, param1Long);
/* 1488 */       this.temp = (int)this.is.skip(this.temp);
/* 1489 */       this.remaining -= this.temp;
/* 1490 */       return this.temp;
/*      */     }
/*      */ 
/*      */     
/*      */     public final int available() throws IOException {
/* 1495 */       return Math.min(this.remaining, this.is.available());
/*      */     }
/*      */     
/*      */     public final void close() throws IOException {
/* 1499 */       this.remaining = 0;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/jgss/krb5/CipherHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */