/*     */ package com.sun.media.sound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SoftLinearResampler2
/*     */   extends SoftAbstractResampler
/*     */ {
/*     */   public int getPadding() {
/*  37 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(float[] paramArrayOffloat1, float[] paramArrayOffloat2, float paramFloat1, float[] paramArrayOffloat3, float paramFloat2, float[] paramArrayOffloat4, int[] paramArrayOfint, int paramInt) {
/*  44 */     float f1 = paramArrayOffloat3[0];
/*  45 */     float f2 = paramArrayOffloat2[0];
/*  46 */     int i = paramArrayOfint[0];
/*  47 */     float f3 = paramFloat1;
/*  48 */     int j = paramInt;
/*     */ 
/*     */     
/*  51 */     if (f2 >= f3 || i >= j) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  56 */     int k = (int)(f2 * 32768.0F);
/*  57 */     int m = (int)(f3 * 32768.0F);
/*  58 */     int n = (int)(f1 * 32768.0F);
/*     */ 
/*     */     
/*  61 */     f1 = n * 3.0517578E-5F;
/*     */     
/*  63 */     if (paramFloat2 == 0.0F) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  70 */       int i1 = m - k;
/*  71 */       int i2 = i1 % n;
/*  72 */       if (i2 != 0)
/*  73 */         i1 += n - i2; 
/*  74 */       int i3 = i + i1 / n;
/*  75 */       if (i3 < j) {
/*  76 */         j = i3;
/*     */       }
/*  78 */       while (i < j) {
/*  79 */         int i4 = k >> 15;
/*  80 */         float f4 = f2 - i4;
/*  81 */         float f5 = paramArrayOffloat1[i4];
/*  82 */         paramArrayOffloat4[i++] = f5 + (paramArrayOffloat1[i4 + 1] - f5) * f4;
/*  83 */         k += n;
/*  84 */         f2 += f1;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  89 */       int i1 = (int)(paramFloat2 * 32768.0F);
/*  90 */       paramFloat2 = i1 * 3.0517578E-5F;
/*     */       
/*  92 */       while (k < m && i < j) {
/*  93 */         int i2 = k >> 15;
/*  94 */         float f4 = f2 - i2;
/*  95 */         float f5 = paramArrayOffloat1[i2];
/*  96 */         paramArrayOffloat4[i++] = f5 + (paramArrayOffloat1[i2 + 1] - f5) * f4;
/*  97 */         f2 += f1;
/*  98 */         k += n;
/*  99 */         f1 += paramFloat2;
/* 100 */         n += i1;
/*     */       } 
/*     */     } 
/* 103 */     paramArrayOffloat2[0] = f2;
/* 104 */     paramArrayOfint[0] = i;
/* 105 */     paramArrayOffloat3[0] = f1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftLinearResampler2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */