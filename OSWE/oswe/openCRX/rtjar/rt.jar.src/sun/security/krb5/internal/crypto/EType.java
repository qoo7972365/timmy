/*     */ package sun.security.krb5.internal.crypto;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.Cipher;
/*     */ import sun.security.krb5.Config;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KrbCryptoException;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.internal.KdcErrException;
/*     */ import sun.security.krb5.internal.Krb5;
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
/*     */ 
/*     */ 
/*     */ public abstract class EType
/*     */ {
/*  50 */   private static final boolean DEBUG = Krb5.DEBUG;
/*     */   private static boolean allowWeakCrypto;
/*     */   
/*     */   static {
/*  54 */     initStatic();
/*     */   }
/*     */   
/*     */   public static void initStatic() {
/*  58 */     boolean bool = false;
/*     */     try {
/*  60 */       Config config = Config.getInstance();
/*  61 */       String str = config.get(new String[] { "libdefaults", "allow_weak_crypto" });
/*  62 */       if (str != null && str.equals("true")) bool = true; 
/*  63 */     } catch (Exception exception) {
/*  64 */       if (DEBUG) {
/*  65 */         System.out.println("Exception in getting allow_weak_crypto, using default value " + exception
/*     */             
/*  67 */             .getMessage());
/*     */       }
/*     */     } 
/*  70 */     allowWeakCrypto = bool; } public static EType getInstance(int paramInt) throws KdcErrException { DesCbcCrcEType desCbcCrcEType; DesCbcMd5EType desCbcMd5EType; Des3CbcHmacSha1KdEType des3CbcHmacSha1KdEType;
/*     */     Aes128CtsHmacSha1EType aes128CtsHmacSha1EType;
/*     */     Aes256CtsHmacSha1EType aes256CtsHmacSha1EType;
/*     */     ArcFourHmacEType arcFourHmacEType;
/*     */     String str2;
/*  75 */     NullEType nullEType = null;
/*  76 */     String str1 = null;
/*  77 */     switch (paramInt) {
/*     */       case 0:
/*  79 */         nullEType = new NullEType();
/*  80 */         str1 = "sun.security.krb5.internal.crypto.NullEType";
/*     */         break;
/*     */       case 1:
/*  83 */         desCbcCrcEType = new DesCbcCrcEType();
/*  84 */         str1 = "sun.security.krb5.internal.crypto.DesCbcCrcEType";
/*     */         break;
/*     */       case 3:
/*  87 */         desCbcMd5EType = new DesCbcMd5EType();
/*  88 */         str1 = "sun.security.krb5.internal.crypto.DesCbcMd5EType";
/*     */         break;
/*     */       
/*     */       case 16:
/*  92 */         des3CbcHmacSha1KdEType = new Des3CbcHmacSha1KdEType();
/*  93 */         str1 = "sun.security.krb5.internal.crypto.Des3CbcHmacSha1KdEType";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 17:
/*  98 */         aes128CtsHmacSha1EType = new Aes128CtsHmacSha1EType();
/*  99 */         str1 = "sun.security.krb5.internal.crypto.Aes128CtsHmacSha1EType";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 18:
/* 104 */         aes256CtsHmacSha1EType = new Aes256CtsHmacSha1EType();
/* 105 */         str1 = "sun.security.krb5.internal.crypto.Aes256CtsHmacSha1EType";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 23:
/* 110 */         arcFourHmacEType = new ArcFourHmacEType();
/* 111 */         str1 = "sun.security.krb5.internal.crypto.ArcFourHmacEType";
/*     */         break;
/*     */       
/*     */       default:
/* 115 */         str2 = "encryption type = " + toString(paramInt) + " (" + paramInt + ")";
/*     */         
/* 117 */         throw new KdcErrException(14, str2);
/*     */     } 
/* 119 */     if (DEBUG) {
/* 120 */       System.out.println(">>> EType: " + str1);
/*     */     }
/* 122 */     return arcFourHmacEType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int dataSize(byte[] paramArrayOfbyte) {
/* 160 */     return paramArrayOfbyte.length - startOfData();
/*     */   }
/*     */   
/*     */   public int padSize(byte[] paramArrayOfbyte) {
/* 164 */     return paramArrayOfbyte.length - confounderSize() - checksumSize() - 
/* 165 */       dataSize(paramArrayOfbyte);
/*     */   }
/*     */   
/*     */   public int startOfChecksum() {
/* 169 */     return confounderSize();
/*     */   }
/*     */   
/*     */   public int startOfData() {
/* 173 */     return confounderSize() + checksumSize();
/*     */   }
/*     */   
/*     */   public int startOfPad(byte[] paramArrayOfbyte) {
/* 177 */     return confounderSize() + checksumSize() + dataSize(paramArrayOfbyte);
/*     */   }
/*     */   
/*     */   public byte[] decryptedData(byte[] paramArrayOfbyte) {
/* 181 */     int i = dataSize(paramArrayOfbyte);
/* 182 */     byte[] arrayOfByte = new byte[i];
/* 183 */     System.arraycopy(paramArrayOfbyte, startOfData(), arrayOfByte, 0, i);
/* 184 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 191 */   private static final int[] BUILTIN_ETYPES = new int[] { 18, 17, 16, 23, 1, 3 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   private static final int[] BUILTIN_ETYPES_NOAES256 = new int[] { 17, 16, 23, 1, 3 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getBuiltInDefaults() {
/* 211 */     int arrayOfInt[], i = 0;
/*     */     try {
/* 213 */       i = Cipher.getMaxAllowedKeyLength("AES");
/* 214 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 218 */     if (i < 256) {
/* 219 */       arrayOfInt = BUILTIN_ETYPES_NOAES256;
/*     */     } else {
/* 221 */       arrayOfInt = BUILTIN_ETYPES;
/*     */     } 
/* 223 */     if (!allowWeakCrypto)
/*     */     {
/* 225 */       return Arrays.copyOfRange(arrayOfInt, 0, arrayOfInt.length - 2);
/*     */     }
/* 227 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] getDefaults(String paramString) throws KrbException {
/* 238 */     Config config = null;
/*     */     try {
/* 240 */       config = Config.getInstance();
/* 241 */     } catch (KrbException krbException) {
/* 242 */       if (DEBUG) {
/* 243 */         System.out.println("Exception while getting " + paramString + krbException
/* 244 */             .getMessage());
/* 245 */         System.out.println("Using default builtin etypes");
/*     */       } 
/* 247 */       return getBuiltInDefaults();
/*     */     } 
/* 249 */     return config.defaultEtype(paramString);
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
/*     */   public static int[] getDefaults(String paramString, EncryptionKey[] paramArrayOfEncryptionKey) throws KrbException {
/* 262 */     int[] arrayOfInt = getDefaults(paramString);
/*     */     
/* 264 */     ArrayList<Integer> arrayList = new ArrayList(arrayOfInt.length); int i;
/* 265 */     for (i = 0; i < arrayOfInt.length; i++) {
/* 266 */       if (EncryptionKey.findKey(arrayOfInt[i], paramArrayOfEncryptionKey) != null) {
/* 267 */         arrayList.add(Integer.valueOf(arrayOfInt[i]));
/*     */       }
/*     */     } 
/* 270 */     i = arrayList.size();
/* 271 */     if (i <= 0) {
/* 272 */       StringBuffer stringBuffer = new StringBuffer();
/* 273 */       for (byte b1 = 0; b1 < paramArrayOfEncryptionKey.length; b1++) {
/* 274 */         stringBuffer.append(toString(paramArrayOfEncryptionKey[b1].getEType()));
/* 275 */         stringBuffer.append(" ");
/*     */       } 
/* 277 */       throw new KrbException("Do not have keys of types listed in " + paramString + " available; only have keys of following type: " + stringBuffer
/*     */ 
/*     */           
/* 280 */           .toString());
/*     */     } 
/* 282 */     arrayOfInt = new int[i];
/* 283 */     for (byte b = 0; b < i; b++) {
/* 284 */       arrayOfInt[b] = ((Integer)arrayList.get(b)).intValue();
/*     */     }
/* 286 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSupported(int paramInt, int[] paramArrayOfint) {
/* 291 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 292 */       if (paramInt == paramArrayOfint[b]) {
/* 293 */         return true;
/*     */       }
/*     */     } 
/* 296 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isSupported(int paramInt) {
/* 300 */     int[] arrayOfInt = getBuiltInDefaults();
/* 301 */     return isSupported(paramInt, arrayOfInt);
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
/*     */   public static boolean isNewer(int paramInt) {
/* 316 */     return (paramInt != 1 && paramInt != 2 && paramInt != 3 && paramInt != 16 && paramInt != 23 && paramInt != 24);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(int paramInt) {
/* 325 */     switch (paramInt) {
/*     */       case 0:
/* 327 */         return "NULL";
/*     */       case 1:
/* 329 */         return "DES CBC mode with CRC-32";
/*     */       case 2:
/* 331 */         return "DES CBC mode with MD4";
/*     */       case 3:
/* 333 */         return "DES CBC mode with MD5";
/*     */       case 4:
/* 335 */         return "reserved";
/*     */       case 5:
/* 337 */         return "DES3 CBC mode with MD5";
/*     */       case 6:
/* 339 */         return "reserved";
/*     */       case 7:
/* 341 */         return "DES3 CBC mode with SHA1";
/*     */       case 9:
/* 343 */         return "DSA with SHA1- Cms0ID";
/*     */       case 10:
/* 345 */         return "MD5 with RSA encryption - Cms0ID";
/*     */       case 11:
/* 347 */         return "SHA1 with RSA encryption - Cms0ID";
/*     */       case 12:
/* 349 */         return "RC2 CBC mode with Env0ID";
/*     */       case 13:
/* 351 */         return "RSA encryption with Env0ID";
/*     */       case 14:
/* 353 */         return "RSAES-0AEP-ENV-0ID";
/*     */       case 15:
/* 355 */         return "DES-EDE3-CBC-ENV-0ID";
/*     */       case 16:
/* 357 */         return "DES3 CBC mode with SHA1-KD";
/*     */       case 17:
/* 359 */         return "AES128 CTS mode with HMAC SHA1-96";
/*     */       case 18:
/* 361 */         return "AES256 CTS mode with HMAC SHA1-96";
/*     */       case 23:
/* 363 */         return "RC4 with HMAC";
/*     */       case 24:
/* 365 */         return "RC4 with HMAC EXP";
/*     */     } 
/*     */     
/* 368 */     return "Unknown (" + paramInt + ")";
/*     */   }
/*     */   
/*     */   public abstract int eType();
/*     */   
/*     */   public abstract int minimumPadSize();
/*     */   
/*     */   public abstract int confounderSize();
/*     */   
/*     */   public abstract int checksumType();
/*     */   
/*     */   public abstract int checksumSize();
/*     */   
/*     */   public abstract int blockSize();
/*     */   
/*     */   public abstract int keyType();
/*     */   
/*     */   public abstract int keySize();
/*     */   
/*     */   public abstract byte[] encrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) throws KrbCryptoException;
/*     */   
/*     */   public abstract byte[] encrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt) throws KrbCryptoException;
/*     */   
/*     */   public abstract byte[] decrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) throws KrbApErrException, KrbCryptoException;
/*     */   
/*     */   public abstract byte[] decrypt(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt) throws KrbApErrException, KrbCryptoException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/EType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */