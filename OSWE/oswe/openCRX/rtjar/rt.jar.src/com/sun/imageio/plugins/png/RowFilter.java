/*     */ package com.sun.imageio.plugins.png;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RowFilter
/*     */ {
/*     */   private static final int abs(int paramInt) {
/*  31 */     return (paramInt < 0) ? -paramInt : paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int subFilter(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2) {
/*  39 */     int i = 0;
/*  40 */     for (int j = paramInt1; j < paramInt2 + paramInt1; j++) {
/*  41 */       int k = paramArrayOfbyte1[j] & 0xFF;
/*  42 */       int m = paramArrayOfbyte1[j - paramInt1] & 0xFF;
/*  43 */       int n = k - m;
/*  44 */       paramArrayOfbyte2[j] = (byte)n;
/*     */       
/*  46 */       i += abs(n);
/*     */     } 
/*     */     
/*  49 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int upFilter(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt1, int paramInt2) {
/*  58 */     int i = 0;
/*  59 */     for (int j = paramInt1; j < paramInt2 + paramInt1; j++) {
/*  60 */       int k = paramArrayOfbyte1[j] & 0xFF;
/*  61 */       int m = paramArrayOfbyte2[j] & 0xFF;
/*  62 */       int n = k - m;
/*  63 */       paramArrayOfbyte3[j] = (byte)n;
/*     */       
/*  65 */       i += abs(n);
/*     */     } 
/*     */     
/*  68 */     return i;
/*     */   }
/*     */   
/*     */   protected final int paethPredictor(int paramInt1, int paramInt2, int paramInt3) {
/*  72 */     int i = paramInt1 + paramInt2 - paramInt3;
/*  73 */     int j = abs(i - paramInt1);
/*  74 */     int k = abs(i - paramInt2);
/*  75 */     int m = abs(i - paramInt3);
/*     */     
/*  77 */     if (j <= k && j <= m)
/*  78 */       return paramInt1; 
/*  79 */     if (k <= m) {
/*  80 */       return paramInt2;
/*     */     }
/*  82 */     return paramInt3;
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
/*     */   public int filterRow(int paramInt1, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[][] paramArrayOfbyte, int paramInt2, int paramInt3) {
/*  94 */     if (paramInt1 != 3) {
/*  95 */       System.arraycopy(paramArrayOfbyte1, paramInt3, paramArrayOfbyte[0], paramInt3, paramInt2);
/*     */ 
/*     */       
/*  98 */       return 0;
/*     */     } 
/*     */     
/* 101 */     int[] arrayOfInt = new int[5]; int j;
/* 102 */     for (j = 0; j < 5; j++) {
/* 103 */       arrayOfInt[j] = Integer.MAX_VALUE;
/*     */     }
/*     */ 
/*     */     
/* 107 */     j = 0;
/*     */     int k;
/* 109 */     for (k = paramInt3; k < paramInt2 + paramInt3; k++) {
/* 110 */       int n = paramArrayOfbyte1[k] & 0xFF;
/* 111 */       j += n;
/*     */     } 
/*     */     
/* 114 */     arrayOfInt[0] = j;
/*     */ 
/*     */ 
/*     */     
/* 118 */     byte[] arrayOfByte = paramArrayOfbyte[1];
/* 119 */     k = subFilter(paramArrayOfbyte1, arrayOfByte, paramInt3, paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     arrayOfInt[1] = k;
/*     */ 
/*     */ 
/*     */     
/* 128 */     arrayOfByte = paramArrayOfbyte[2];
/* 129 */     k = upFilter(paramArrayOfbyte1, paramArrayOfbyte2, arrayOfByte, paramInt3, paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     arrayOfInt[2] = k;
/*     */ 
/*     */ 
/*     */     
/* 139 */     arrayOfByte = paramArrayOfbyte[3];
/* 140 */     k = 0;
/*     */     int m;
/* 142 */     for (m = paramInt3; m < paramInt2 + paramInt3; m++) {
/* 143 */       int n = paramArrayOfbyte1[m] & 0xFF;
/* 144 */       int i1 = paramArrayOfbyte1[m - paramInt3] & 0xFF;
/* 145 */       int i2 = paramArrayOfbyte2[m] & 0xFF;
/* 146 */       int i3 = n - (i1 + i2) / 2;
/* 147 */       arrayOfByte[m] = (byte)i3;
/*     */       
/* 149 */       k += abs(i3);
/*     */     } 
/*     */     
/* 152 */     arrayOfInt[3] = k;
/*     */ 
/*     */ 
/*     */     
/* 156 */     arrayOfByte = paramArrayOfbyte[4];
/* 157 */     k = 0;
/*     */     
/* 159 */     for (m = paramInt3; m < paramInt2 + paramInt3; m++) {
/* 160 */       int n = paramArrayOfbyte1[m] & 0xFF;
/* 161 */       int i1 = paramArrayOfbyte1[m - paramInt3] & 0xFF;
/* 162 */       int i2 = paramArrayOfbyte2[m] & 0xFF;
/* 163 */       int i3 = paramArrayOfbyte2[m - paramInt3] & 0xFF;
/* 164 */       int i4 = paethPredictor(i1, i2, i3);
/* 165 */       int i5 = n - i4;
/* 166 */       arrayOfByte[m] = (byte)i5;
/*     */       
/* 168 */       k += abs(i5);
/*     */     } 
/*     */     
/* 171 */     arrayOfInt[4] = k;
/*     */ 
/*     */     
/* 174 */     int i = arrayOfInt[0];
/* 175 */     k = 0;
/*     */     
/* 177 */     for (m = 1; m < 5; m++) {
/* 178 */       if (arrayOfInt[m] < i) {
/* 179 */         i = arrayOfInt[m];
/* 180 */         k = m;
/*     */       } 
/*     */     } 
/*     */     
/* 184 */     if (k == 0) {
/* 185 */       System.arraycopy(paramArrayOfbyte1, paramInt3, paramArrayOfbyte[0], paramInt3, paramInt2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 190 */     return k;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/png/RowFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */