/*     */ package javax.swing.colorchooser;
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
/*     */ final class ColorModelHSL
/*     */   extends ColorModel
/*     */ {
/*     */   ColorModelHSL() {
/*  31 */     super("hsl", new String[] { "Hue", "Saturation", "Lightness", "Transparency" });
/*     */   }
/*     */ 
/*     */   
/*     */   void setColor(int paramInt, float[] paramArrayOffloat) {
/*  36 */     super.setColor(paramInt, paramArrayOffloat);
/*  37 */     RGBtoHSL(paramArrayOffloat, paramArrayOffloat);
/*  38 */     paramArrayOffloat[3] = 1.0F - paramArrayOffloat[3];
/*     */   }
/*     */ 
/*     */   
/*     */   int getColor(float[] paramArrayOffloat) {
/*  43 */     paramArrayOffloat[3] = 1.0F - paramArrayOffloat[3];
/*  44 */     HSLtoRGB(paramArrayOffloat, paramArrayOffloat);
/*  45 */     return super.getColor(paramArrayOffloat);
/*     */   }
/*     */ 
/*     */   
/*     */   int getMaximum(int paramInt) {
/*  50 */     return (paramInt == 0) ? 360 : 100;
/*     */   }
/*     */ 
/*     */   
/*     */   float getDefault(int paramInt) {
/*  55 */     return (paramInt == 0) ? -1.0F : ((paramInt == 2) ? 0.5F : 1.0F);
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
/*     */   private static float[] HSLtoRGB(float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
/*  68 */     if (paramArrayOffloat2 == null) {
/*  69 */       paramArrayOffloat2 = new float[3];
/*     */     }
/*  71 */     float f1 = paramArrayOffloat1[0];
/*  72 */     float f2 = paramArrayOffloat1[1];
/*  73 */     float f3 = paramArrayOffloat1[2];
/*     */     
/*  75 */     if (f2 > 0.0F) {
/*  76 */       f1 = (f1 < 1.0F) ? (f1 * 6.0F) : 0.0F;
/*  77 */       float f4 = f3 + f2 * ((f3 > 0.5F) ? (1.0F - f3) : f3);
/*  78 */       float f5 = 2.0F * f3 - f4;
/*  79 */       paramArrayOffloat2[0] = normalize(f4, f5, (f1 < 4.0F) ? (f1 + 2.0F) : (f1 - 4.0F));
/*  80 */       paramArrayOffloat2[1] = normalize(f4, f5, f1);
/*  81 */       paramArrayOffloat2[2] = normalize(f4, f5, (f1 < 2.0F) ? (f1 + 4.0F) : (f1 - 2.0F));
/*     */     } else {
/*     */       
/*  84 */       paramArrayOffloat2[0] = f3;
/*  85 */       paramArrayOffloat2[1] = f3;
/*  86 */       paramArrayOffloat2[2] = f3;
/*     */     } 
/*  88 */     return paramArrayOffloat2;
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
/*     */   private static float[] RGBtoHSL(float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
/* 101 */     if (paramArrayOffloat2 == null) {
/* 102 */       paramArrayOffloat2 = new float[3];
/*     */     }
/* 104 */     float f1 = max(paramArrayOffloat1[0], paramArrayOffloat1[1], paramArrayOffloat1[2]);
/* 105 */     float f2 = min(paramArrayOffloat1[0], paramArrayOffloat1[1], paramArrayOffloat1[2]);
/*     */     
/* 107 */     float f3 = f1 + f2;
/* 108 */     float f4 = f1 - f2;
/* 109 */     if (f4 > 0.0F) {
/* 110 */       f4 /= (f3 > 1.0F) ? (2.0F - f3) : f3;
/*     */     }
/*     */ 
/*     */     
/* 114 */     paramArrayOffloat2[0] = getHue(paramArrayOffloat1[0], paramArrayOffloat1[1], paramArrayOffloat1[2], f1, f2);
/* 115 */     paramArrayOffloat2[1] = f4;
/* 116 */     paramArrayOffloat2[2] = f3 / 2.0F;
/* 117 */     return paramArrayOffloat2;
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
/*     */   static float min(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 129 */     float f = (paramFloat1 < paramFloat2) ? paramFloat1 : paramFloat2;
/* 130 */     return (f < paramFloat3) ? f : paramFloat3;
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
/*     */   static float max(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 142 */     float f = (paramFloat1 > paramFloat2) ? paramFloat1 : paramFloat2;
/* 143 */     return (f > paramFloat3) ? f : paramFloat3;
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
/*     */   static float getHue(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
/* 157 */     float f = paramFloat4 - paramFloat5;
/* 158 */     if (f > 0.0F) {
/* 159 */       if (paramFloat4 == paramFloat1) {
/* 160 */         f = (paramFloat2 - paramFloat3) / f;
/* 161 */         if (f < 0.0F) {
/* 162 */           f += 6.0F;
/*     */         }
/*     */       }
/* 165 */       else if (paramFloat4 == paramFloat2) {
/* 166 */         f = 2.0F + (paramFloat3 - paramFloat1) / f;
/*     */       } else {
/*     */         
/* 169 */         f = 4.0F + (paramFloat1 - paramFloat2) / f;
/*     */       } 
/* 171 */       f /= 6.0F;
/*     */     } 
/* 173 */     return f;
/*     */   }
/*     */   
/*     */   private static float normalize(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 177 */     if (paramFloat3 < 1.0F) {
/* 178 */       return paramFloat2 + (paramFloat1 - paramFloat2) * paramFloat3;
/*     */     }
/* 180 */     if (paramFloat3 < 3.0F) {
/* 181 */       return paramFloat1;
/*     */     }
/* 183 */     if (paramFloat3 < 4.0F) {
/* 184 */       return paramFloat2 + (paramFloat1 - paramFloat2) * (4.0F - paramFloat3);
/*     */     }
/* 186 */     return paramFloat2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/colorchooser/ColorModelHSL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */