/*     */ package java.util.zip;
/*     */ 
/*     */ import java.nio.file.attribute.FileTime;
/*     */ import java.util.Date;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ZipUtils
/*     */ {
/*     */   private static final long WINDOWS_EPOCH_IN_MICROSECONDS = -11644473600000000L;
/*     */   
/*     */   public static final FileTime winTimeToFileTime(long paramLong) {
/*  41 */     return FileTime.from(paramLong / 10L + -11644473600000000L, TimeUnit.MICROSECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long fileTimeToWinTime(FileTime paramFileTime) {
/*  49 */     return (paramFileTime.to(TimeUnit.MICROSECONDS) - -11644473600000000L) * 10L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final FileTime unixTimeToFileTime(long paramLong) {
/*  56 */     return FileTime.from(paramLong, TimeUnit.SECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long fileTimeToUnixTime(FileTime paramFileTime) {
/*  63 */     return paramFileTime.to(TimeUnit.SECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long dosToJavaTime(long paramLong) {
/*  71 */     Date date = new Date((int)((paramLong >> 25L & 0x7FL) + 80L), (int)((paramLong >> 21L & 0xFL) - 1L), (int)(paramLong >> 16L & 0x1FL), (int)(paramLong >> 11L & 0x1FL), (int)(paramLong >> 5L & 0x3FL), (int)(paramLong << 1L & 0x3EL));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     return date.getTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long extendedDosToJavaTime(long paramLong) {
/*  88 */     long l = dosToJavaTime(paramLong);
/*  89 */     return l + (paramLong >> 32L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long javaToDosTime(long paramLong) {
/*  97 */     Date date = new Date(paramLong);
/*  98 */     int i = date.getYear() + 1900;
/*  99 */     if (i < 1980) {
/* 100 */       return 2162688L;
/*     */     }
/* 102 */     return (i - 1980 << 25 | date.getMonth() + 1 << 21 | date
/* 103 */       .getDate() << 16 | date.getHours() << 11 | date.getMinutes() << 5 | date
/* 104 */       .getSeconds() >> 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long javaToExtendedDosTime(long paramLong) {
/* 115 */     if (paramLong < 0L) {
/* 116 */       return 2162688L;
/*     */     }
/* 118 */     long l = javaToDosTime(paramLong);
/* 119 */     return (l != 2162688L) ? (l + (paramLong % 2000L << 32L)) : 2162688L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int get16(byte[] paramArrayOfbyte, int paramInt) {
/* 129 */     return Byte.toUnsignedInt(paramArrayOfbyte[paramInt]) | Byte.toUnsignedInt(paramArrayOfbyte[paramInt + 1]) << 8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long get32(byte[] paramArrayOfbyte, int paramInt) {
/* 137 */     return (get16(paramArrayOfbyte, paramInt) | get16(paramArrayOfbyte, paramInt + 2) << 16L) & 0xFFFFFFFFL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final long get64(byte[] paramArrayOfbyte, int paramInt) {
/* 145 */     return get32(paramArrayOfbyte, paramInt) | get32(paramArrayOfbyte, paramInt + 4) << 32L;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/zip/ZipUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */