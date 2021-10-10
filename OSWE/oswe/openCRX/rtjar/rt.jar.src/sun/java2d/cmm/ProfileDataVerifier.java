/*     */ package sun.java2d.cmm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProfileDataVerifier
/*     */ {
/*     */   private static final int MAX_TAG_COUNT = 100;
/*     */   private static final int HEADER_SIZE = 128;
/*     */   private static final int TOC_OFFSET = 132;
/*     */   private static final int TOC_RECORD_SIZE = 12;
/*     */   private static final int PROFILE_FILE_SIGNATURE = 1633907568;
/*     */   
/*     */   public static void verify(byte[] paramArrayOfbyte) {
/*  36 */     if (paramArrayOfbyte == null) {
/*  37 */       throw new IllegalArgumentException("Invalid ICC Profile Data");
/*     */     }
/*     */     
/*  40 */     if (paramArrayOfbyte.length < 132)
/*     */     {
/*  42 */       throw new IllegalArgumentException("Invalid ICC Profile Data");
/*     */     }
/*     */ 
/*     */     
/*  46 */     int i = readInt32(paramArrayOfbyte, 0);
/*  47 */     int j = readInt32(paramArrayOfbyte, 128);
/*     */     
/*  49 */     if (j < 0 || j > 100) {
/*  50 */       throw new IllegalArgumentException("Invalid ICC Profile Data");
/*     */     }
/*     */     
/*  53 */     if (i < 132 + j * 12 || i > paramArrayOfbyte.length)
/*     */     {
/*     */       
/*  56 */       throw new IllegalArgumentException("Invalid ICC Profile Data");
/*     */     }
/*     */     
/*  59 */     int k = readInt32(paramArrayOfbyte, 36);
/*     */     
/*  61 */     if (1633907568 != k) {
/*  62 */       throw new IllegalArgumentException("Invalid ICC Profile Data");
/*     */     }
/*     */ 
/*     */     
/*  66 */     for (byte b = 0; b < j; b++) {
/*  67 */       int m = getTagOffset(b, paramArrayOfbyte);
/*  68 */       int n = getTagSize(b, paramArrayOfbyte);
/*     */       
/*  70 */       if (m < 132 || m > i) {
/*  71 */         throw new IllegalArgumentException("Invalid ICC Profile Data");
/*     */       }
/*     */       
/*  74 */       if (n < 0 || n > Integer.MAX_VALUE - m || n + m > i)
/*     */       {
/*     */ 
/*     */         
/*  78 */         throw new IllegalArgumentException("Invalid ICC Profile Data");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int getTagOffset(int paramInt, byte[] paramArrayOfbyte) {
/*  84 */     int i = 132 + paramInt * 12 + 4;
/*  85 */     return readInt32(paramArrayOfbyte, i);
/*     */   }
/*     */   
/*     */   private static int getTagSize(int paramInt, byte[] paramArrayOfbyte) {
/*  89 */     int i = 132 + paramInt * 12 + 8;
/*  90 */     return readInt32(paramArrayOfbyte, i);
/*     */   }
/*     */   
/*     */   private static int readInt32(byte[] paramArrayOfbyte, int paramInt) {
/*  94 */     int i = 0;
/*  95 */     for (byte b = 0; b < 4; b++) {
/*  96 */       i <<= 8;
/*     */       
/*  98 */       i |= 0xFF & paramArrayOfbyte[paramInt++];
/*     */     } 
/* 100 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/cmm/ProfileDataVerifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */