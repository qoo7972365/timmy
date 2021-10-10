/*    */ package sun.security.krb5.internal.crypto;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Crc32CksumType
/*    */   extends CksumType
/*    */ {
/*    */   public int confounderSize() {
/* 42 */     return 0;
/*    */   }
/*    */   
/*    */   public int cksumType() {
/* 46 */     return 1;
/*    */   }
/*    */   
/*    */   public boolean isKeyed() {
/* 50 */     return false;
/*    */   }
/*    */   
/*    */   public int cksumSize() {
/* 54 */     return 4;
/*    */   }
/*    */   
/*    */   public int keyType() {
/* 58 */     return 0;
/*    */   }
/*    */   
/*    */   public int keySize() {
/* 62 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] calculateChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2) {
/* 67 */     return crc32.byte2crc32sum_bytes(paramArrayOfbyte1, paramInt1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean verifyChecksum(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2) {
/* 72 */     return CksumType.isChecksumEqual(paramArrayOfbyte3, 
/* 73 */         crc32.byte2crc32sum_bytes(paramArrayOfbyte1));
/*    */   }
/*    */   
/*    */   public static byte[] int2quad(long paramLong) {
/* 77 */     byte[] arrayOfByte = new byte[4];
/* 78 */     for (byte b = 0; b < 4; b++) {
/* 79 */       arrayOfByte[b] = (byte)(int)(paramLong >>> b * 8 & 0xFFL);
/*    */     }
/* 81 */     return arrayOfByte;
/*    */   }
/*    */   
/*    */   public static long bytes2long(byte[] paramArrayOfbyte) {
/* 85 */     long l = 0L;
/*    */     
/* 87 */     l |= (paramArrayOfbyte[0] & 0xFFL) << 24L;
/* 88 */     l |= (paramArrayOfbyte[1] & 0xFFL) << 16L;
/* 89 */     l |= (paramArrayOfbyte[2] & 0xFFL) << 8L;
/* 90 */     l |= paramArrayOfbyte[3] & 0xFFL;
/* 91 */     return l;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/crypto/Crc32CksumType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */