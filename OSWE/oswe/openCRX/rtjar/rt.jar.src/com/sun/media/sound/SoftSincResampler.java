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
/*     */ public final class SoftSincResampler
/*     */   extends SoftAbstractResampler
/*     */ {
/*     */   float[][][] sinc_table;
/*  37 */   int sinc_scale_size = 100;
/*  38 */   int sinc_table_fsize = 800;
/*  39 */   int sinc_table_size = 30;
/*  40 */   int sinc_table_center = this.sinc_table_size / 2;
/*     */ 
/*     */   
/*     */   public SoftSincResampler() {
/*  44 */     this.sinc_table = new float[this.sinc_scale_size][this.sinc_table_fsize][];
/*  45 */     for (byte b = 0; b < this.sinc_scale_size; b++) {
/*  46 */       float f = (float)(1.0D / (1.0D + Math.pow(b, 1.1D) / 10.0D));
/*  47 */       for (byte b1 = 0; b1 < this.sinc_table_fsize; b1++) {
/*  48 */         this.sinc_table[b][b1] = sincTable(this.sinc_table_size, -b1 / this.sinc_table_fsize, f);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double sinc(double paramDouble) {
/*  56 */     return (paramDouble == 0.0D) ? 1.0D : (Math.sin(Math.PI * paramDouble) / Math.PI * paramDouble);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] wHanning(int paramInt, float paramFloat) {
/*  61 */     float[] arrayOfFloat = new float[paramInt];
/*  62 */     for (byte b = 0; b < paramInt; b++) {
/*  63 */       arrayOfFloat[b] = 
/*  64 */         (float)(-0.5D * Math.cos(6.283185307179586D * (b + paramFloat) / paramInt) + 0.5D);
/*     */     }
/*     */     
/*  67 */     return arrayOfFloat;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] sincTable(int paramInt, float paramFloat1, float paramFloat2) {
/*  72 */     int i = paramInt / 2;
/*  73 */     float[] arrayOfFloat = wHanning(paramInt, paramFloat1);
/*  74 */     for (byte b = 0; b < paramInt; b++)
/*  75 */       arrayOfFloat[b] = (float)(arrayOfFloat[b] * sinc((((-i + b) + paramFloat1) * paramFloat2)) * paramFloat2); 
/*  76 */     return arrayOfFloat;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPadding() {
/*  81 */     return this.sinc_table_size / 2 + 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(float[] paramArrayOffloat1, float[] paramArrayOffloat2, float paramFloat1, float[] paramArrayOffloat3, float paramFloat2, float[] paramArrayOffloat4, int[] paramArrayOfint, int paramInt) {
/*  87 */     float f1 = paramArrayOffloat3[0];
/*  88 */     float f2 = paramArrayOffloat2[0];
/*  89 */     int i = paramArrayOfint[0];
/*  90 */     float f3 = paramFloat1;
/*  91 */     int j = paramInt;
/*  92 */     int k = this.sinc_scale_size - 1;
/*  93 */     if (paramFloat2 == 0.0F) {
/*     */       
/*  95 */       int m = (int)((f1 - 1.0F) * 10.0F);
/*  96 */       if (m < 0) {
/*  97 */         m = 0;
/*  98 */       } else if (m > k) {
/*  99 */         m = k;
/* 100 */       }  float[][] arrayOfFloat = this.sinc_table[m];
/* 101 */       while (f2 < f3 && i < j) {
/* 102 */         int n = (int)f2;
/* 103 */         float[] arrayOfFloat1 = arrayOfFloat[(int)((f2 - n) * this.sinc_table_fsize)];
/*     */         
/* 105 */         int i1 = n - this.sinc_table_center;
/* 106 */         float f = 0.0F;
/* 107 */         for (byte b = 0; b < this.sinc_table_size; b++, i1++)
/* 108 */           f += paramArrayOffloat1[i1] * arrayOfFloat1[b]; 
/* 109 */         paramArrayOffloat4[i++] = f;
/* 110 */         f2 += f1;
/*     */       } 
/*     */     } else {
/* 113 */       while (f2 < f3 && i < j) {
/* 114 */         int m = (int)f2;
/* 115 */         int n = (int)((f1 - 1.0F) * 10.0F);
/* 116 */         if (n < 0) {
/* 117 */           n = 0;
/* 118 */         } else if (n > k) {
/* 119 */           n = k;
/* 120 */         }  float[][] arrayOfFloat = this.sinc_table[n];
/*     */         
/* 122 */         float[] arrayOfFloat1 = arrayOfFloat[(int)((f2 - m) * this.sinc_table_fsize)];
/*     */         
/* 124 */         int i1 = m - this.sinc_table_center;
/* 125 */         float f = 0.0F;
/* 126 */         for (byte b = 0; b < this.sinc_table_size; b++, i1++)
/* 127 */           f += paramArrayOffloat1[i1] * arrayOfFloat1[b]; 
/* 128 */         paramArrayOffloat4[i++] = f;
/*     */         
/* 130 */         f2 += f1;
/* 131 */         f1 += paramFloat2;
/*     */       } 
/*     */     } 
/* 134 */     paramArrayOffloat2[0] = f2;
/* 135 */     paramArrayOfint[0] = i;
/* 136 */     paramArrayOffloat3[0] = f1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftSincResampler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */