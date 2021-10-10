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
/*     */ public final class SoftLanczosResampler
/*     */   extends SoftAbstractResampler
/*     */ {
/*     */   float[][] sinc_table;
/*  35 */   int sinc_table_fsize = 2000;
/*  36 */   int sinc_table_size = 5;
/*  37 */   int sinc_table_center = this.sinc_table_size / 2;
/*     */ 
/*     */   
/*     */   public SoftLanczosResampler() {
/*  41 */     this.sinc_table = new float[this.sinc_table_fsize][];
/*  42 */     for (byte b = 0; b < this.sinc_table_fsize; b++) {
/*  43 */       this.sinc_table[b] = sincTable(this.sinc_table_size, -b / this.sinc_table_fsize);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double sinc(double paramDouble) {
/*  50 */     return (paramDouble == 0.0D) ? 1.0D : (Math.sin(Math.PI * paramDouble) / Math.PI * paramDouble);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] sincTable(int paramInt, float paramFloat) {
/*  55 */     int i = paramInt / 2;
/*  56 */     float[] arrayOfFloat = new float[paramInt];
/*  57 */     for (byte b = 0; b < paramInt; b++) {
/*  58 */       float f = (-i + b) + paramFloat;
/*  59 */       if (f < -2.0F || f > 2.0F) {
/*  60 */         arrayOfFloat[b] = 0.0F;
/*  61 */       } else if (f == 0.0F) {
/*  62 */         arrayOfFloat[b] = 1.0F;
/*     */       } else {
/*  64 */         arrayOfFloat[b] = 
/*  65 */           (float)(2.0D * Math.sin(Math.PI * f) * Math.sin(Math.PI * f / 2.0D) / Math.PI * f * Math.PI * f);
/*     */       } 
/*     */     } 
/*     */     
/*  69 */     return arrayOfFloat;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPadding() {
/*  74 */     return this.sinc_table_size / 2 + 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(float[] paramArrayOffloat1, float[] paramArrayOffloat2, float paramFloat1, float[] paramArrayOffloat3, float paramFloat2, float[] paramArrayOffloat4, int[] paramArrayOfint, int paramInt) {
/*  80 */     float f1 = paramArrayOffloat3[0];
/*  81 */     float f2 = paramArrayOffloat2[0];
/*  82 */     int i = paramArrayOfint[0];
/*  83 */     float f3 = paramFloat1;
/*  84 */     int j = paramInt;
/*     */     
/*  86 */     if (paramFloat2 == 0.0F) {
/*  87 */       while (f2 < f3 && i < j) {
/*  88 */         int k = (int)f2;
/*  89 */         float[] arrayOfFloat = this.sinc_table[(int)((f2 - k) * this.sinc_table_fsize)];
/*     */         
/*  91 */         int m = k - this.sinc_table_center;
/*  92 */         float f = 0.0F;
/*  93 */         for (byte b = 0; b < this.sinc_table_size; b++, m++)
/*  94 */           f += paramArrayOffloat1[m] * arrayOfFloat[b]; 
/*  95 */         paramArrayOffloat4[i++] = f;
/*  96 */         f2 += f1;
/*     */       } 
/*     */     } else {
/*  99 */       while (f2 < f3 && i < j) {
/* 100 */         int k = (int)f2;
/* 101 */         float[] arrayOfFloat = this.sinc_table[(int)((f2 - k) * this.sinc_table_fsize)];
/*     */         
/* 103 */         int m = k - this.sinc_table_center;
/* 104 */         float f = 0.0F;
/* 105 */         for (byte b = 0; b < this.sinc_table_size; b++, m++)
/* 106 */           f += paramArrayOffloat1[m] * arrayOfFloat[b]; 
/* 107 */         paramArrayOffloat4[i++] = f;
/*     */         
/* 109 */         f2 += f1;
/* 110 */         f1 += paramFloat2;
/*     */       } 
/*     */     } 
/* 113 */     paramArrayOffloat2[0] = f2;
/* 114 */     paramArrayOfint[0] = i;
/* 115 */     paramArrayOffloat3[0] = f1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftLanczosResampler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */