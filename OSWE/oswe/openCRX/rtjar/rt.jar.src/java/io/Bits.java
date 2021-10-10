/*     */ package java.io;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Bits
/*     */ {
/*     */   static boolean getBoolean(byte[] paramArrayOfbyte, int paramInt) {
/*  40 */     return (paramArrayOfbyte[paramInt] != 0);
/*     */   }
/*     */   
/*     */   static char getChar(byte[] paramArrayOfbyte, int paramInt) {
/*  44 */     return (char)((paramArrayOfbyte[paramInt + 1] & 0xFF) + (paramArrayOfbyte[paramInt] << 8));
/*     */   }
/*     */ 
/*     */   
/*     */   static short getShort(byte[] paramArrayOfbyte, int paramInt) {
/*  49 */     return (short)((paramArrayOfbyte[paramInt + 1] & 0xFF) + (paramArrayOfbyte[paramInt] << 8));
/*     */   }
/*     */ 
/*     */   
/*     */   static int getInt(byte[] paramArrayOfbyte, int paramInt) {
/*  54 */     return (paramArrayOfbyte[paramInt + 3] & 0xFF) + ((paramArrayOfbyte[paramInt + 2] & 0xFF) << 8) + ((paramArrayOfbyte[paramInt + 1] & 0xFF) << 16) + (paramArrayOfbyte[paramInt] << 24);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static float getFloat(byte[] paramArrayOfbyte, int paramInt) {
/*  61 */     return Float.intBitsToFloat(getInt(paramArrayOfbyte, paramInt));
/*     */   }
/*     */   
/*     */   static long getLong(byte[] paramArrayOfbyte, int paramInt) {
/*  65 */     return (paramArrayOfbyte[paramInt + 7] & 0xFFL) + ((paramArrayOfbyte[paramInt + 6] & 0xFFL) << 8L) + ((paramArrayOfbyte[paramInt + 5] & 0xFFL) << 16L) + ((paramArrayOfbyte[paramInt + 4] & 0xFFL) << 24L) + ((paramArrayOfbyte[paramInt + 3] & 0xFFL) << 32L) + ((paramArrayOfbyte[paramInt + 2] & 0xFFL) << 40L) + ((paramArrayOfbyte[paramInt + 1] & 0xFFL) << 48L) + (paramArrayOfbyte[paramInt] << 56L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static double getDouble(byte[] paramArrayOfbyte, int paramInt) {
/*  76 */     return Double.longBitsToDouble(getLong(paramArrayOfbyte, paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void putBoolean(byte[] paramArrayOfbyte, int paramInt, boolean paramBoolean) {
/*  85 */     paramArrayOfbyte[paramInt] = (byte)(paramBoolean ? 1 : 0);
/*     */   }
/*     */   
/*     */   static void putChar(byte[] paramArrayOfbyte, int paramInt, char paramChar) {
/*  89 */     paramArrayOfbyte[paramInt + 1] = (byte)paramChar;
/*  90 */     paramArrayOfbyte[paramInt] = (byte)(paramChar >>> 8);
/*     */   }
/*     */   
/*     */   static void putShort(byte[] paramArrayOfbyte, int paramInt, short paramShort) {
/*  94 */     paramArrayOfbyte[paramInt + 1] = (byte)paramShort;
/*  95 */     paramArrayOfbyte[paramInt] = (byte)(paramShort >>> 8);
/*     */   }
/*     */   
/*     */   static void putInt(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  99 */     paramArrayOfbyte[paramInt1 + 3] = (byte)paramInt2;
/* 100 */     paramArrayOfbyte[paramInt1 + 2] = (byte)(paramInt2 >>> 8);
/* 101 */     paramArrayOfbyte[paramInt1 + 1] = (byte)(paramInt2 >>> 16);
/* 102 */     paramArrayOfbyte[paramInt1] = (byte)(paramInt2 >>> 24);
/*     */   }
/*     */   
/*     */   static void putFloat(byte[] paramArrayOfbyte, int paramInt, float paramFloat) {
/* 106 */     putInt(paramArrayOfbyte, paramInt, Float.floatToIntBits(paramFloat));
/*     */   }
/*     */   
/*     */   static void putLong(byte[] paramArrayOfbyte, int paramInt, long paramLong) {
/* 110 */     paramArrayOfbyte[paramInt + 7] = (byte)(int)paramLong;
/* 111 */     paramArrayOfbyte[paramInt + 6] = (byte)(int)(paramLong >>> 8L);
/* 112 */     paramArrayOfbyte[paramInt + 5] = (byte)(int)(paramLong >>> 16L);
/* 113 */     paramArrayOfbyte[paramInt + 4] = (byte)(int)(paramLong >>> 24L);
/* 114 */     paramArrayOfbyte[paramInt + 3] = (byte)(int)(paramLong >>> 32L);
/* 115 */     paramArrayOfbyte[paramInt + 2] = (byte)(int)(paramLong >>> 40L);
/* 116 */     paramArrayOfbyte[paramInt + 1] = (byte)(int)(paramLong >>> 48L);
/* 117 */     paramArrayOfbyte[paramInt] = (byte)(int)(paramLong >>> 56L);
/*     */   }
/*     */   
/*     */   static void putDouble(byte[] paramArrayOfbyte, int paramInt, double paramDouble) {
/* 121 */     putLong(paramArrayOfbyte, paramInt, Double.doubleToLongBits(paramDouble));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/Bits.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */