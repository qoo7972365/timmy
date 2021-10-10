/*     */ package sun.security.krb5.internal.crypto;
/*     */ 
/*     */ import sun.security.krb5.KrbCryptoException;
/*     */ import sun.security.krb5.internal.KdcErrException;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CksumType
/*     */ {
/*  40 */   private static boolean DEBUG = Krb5.DEBUG; public static CksumType getInstance(int paramInt) throws KdcErrException { DesMacCksumType desMacCksumType; DesMacKCksumType desMacKCksumType; RsaMd5CksumType rsaMd5CksumType; RsaMd5DesCksumType rsaMd5DesCksumType; HmacSha1Des3KdCksumType hmacSha1Des3KdCksumType;
/*     */     HmacSha1Aes128CksumType hmacSha1Aes128CksumType;
/*     */     HmacSha1Aes256CksumType hmacSha1Aes256CksumType;
/*     */     HmacMd5ArcFourCksumType hmacMd5ArcFourCksumType;
/*  44 */     Crc32CksumType crc32CksumType = null;
/*  45 */     String str = null;
/*  46 */     switch (paramInt) {
/*     */       case 1:
/*  48 */         crc32CksumType = new Crc32CksumType();
/*  49 */         str = "sun.security.krb5.internal.crypto.Crc32CksumType";
/*     */         break;
/*     */       case 4:
/*  52 */         desMacCksumType = new DesMacCksumType();
/*  53 */         str = "sun.security.krb5.internal.crypto.DesMacCksumType";
/*     */         break;
/*     */       case 5:
/*  56 */         desMacKCksumType = new DesMacKCksumType();
/*  57 */         str = "sun.security.krb5.internal.crypto.DesMacKCksumType";
/*     */         break;
/*     */       
/*     */       case 7:
/*  61 */         rsaMd5CksumType = new RsaMd5CksumType();
/*  62 */         str = "sun.security.krb5.internal.crypto.RsaMd5CksumType";
/*     */         break;
/*     */       case 8:
/*  65 */         rsaMd5DesCksumType = new RsaMd5DesCksumType();
/*  66 */         str = "sun.security.krb5.internal.crypto.RsaMd5DesCksumType";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 12:
/*  71 */         hmacSha1Des3KdCksumType = new HmacSha1Des3KdCksumType();
/*  72 */         str = "sun.security.krb5.internal.crypto.HmacSha1Des3KdCksumType";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 15:
/*  77 */         hmacSha1Aes128CksumType = new HmacSha1Aes128CksumType();
/*  78 */         str = "sun.security.krb5.internal.crypto.HmacSha1Aes128CksumType";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 16:
/*  83 */         hmacSha1Aes256CksumType = new HmacSha1Aes256CksumType();
/*  84 */         str = "sun.security.krb5.internal.crypto.HmacSha1Aes256CksumType";
/*     */         break;
/*     */ 
/*     */       
/*     */       case -138:
/*  89 */         hmacMd5ArcFourCksumType = new HmacMd5ArcFourCksumType();
/*  90 */         str = "sun.security.krb5.internal.crypto.HmacMd5ArcFourCksumType";
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 110 */         throw new KdcErrException(15);
/*     */     } 
/* 112 */     if (DEBUG) {
/* 113 */       System.out.println(">>> CksumType: " + str);
/*     */     }
/* 115 */     return hmacMd5ArcFourCksumType; }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int confounderSize();
/*     */ 
/*     */   
/*     */   public abstract int cksumType();
/*     */ 
/*     */   
/*     */   public abstract boolean isKeyed();
/*     */ 
/*     */   
/*     */   public abstract int cksumSize();
/*     */   
/*     */   public abstract int keyType();
/*     */   
/*     */   public abstract int keySize();
/*     */   
/*     */   public abstract byte[] calculateChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) throws KrbCryptoException;
/*     */   
/*     */   public abstract boolean verifyChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2) throws KrbCryptoException;
/*     */   
/*     */   public static boolean isChecksumEqual(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 139 */     if (paramArrayOfbyte1 == paramArrayOfbyte2)
/* 140 */       return true; 
/* 141 */     if ((paramArrayOfbyte1 == null && paramArrayOfbyte2 != null) || (paramArrayOfbyte1 != null && paramArrayOfbyte2 == null))
/*     */     {
/* 143 */       return false; } 
/* 144 */     if (paramArrayOfbyte1.length != paramArrayOfbyte2.length)
/* 145 */       return false; 
/* 146 */     for (byte b = 0; b < paramArrayOfbyte1.length; b++) {
/* 147 */       if (paramArrayOfbyte1[b] != paramArrayOfbyte2[b])
/* 148 */         return false; 
/* 149 */     }  return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/CksumType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */