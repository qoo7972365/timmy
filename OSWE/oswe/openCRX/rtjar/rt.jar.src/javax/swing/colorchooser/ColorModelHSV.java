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
/*     */ final class ColorModelHSV
/*     */   extends ColorModel
/*     */ {
/*     */   ColorModelHSV() {
/*  31 */     super("hsv", new String[] { "Hue", "Saturation", "Value", "Transparency" });
/*     */   }
/*     */ 
/*     */   
/*     */   void setColor(int paramInt, float[] paramArrayOffloat) {
/*  36 */     super.setColor(paramInt, paramArrayOffloat);
/*  37 */     RGBtoHSV(paramArrayOffloat, paramArrayOffloat);
/*  38 */     paramArrayOffloat[3] = 1.0F - paramArrayOffloat[3];
/*     */   }
/*     */ 
/*     */   
/*     */   int getColor(float[] paramArrayOffloat) {
/*  43 */     paramArrayOffloat[3] = 1.0F - paramArrayOffloat[3];
/*  44 */     HSVtoRGB(paramArrayOffloat, paramArrayOffloat);
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
/*  55 */     return (paramInt == 0) ? -1.0F : 1.0F;
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
/*     */   private static float[] HSVtoRGB(float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
/*  68 */     if (paramArrayOffloat2 == null) {
/*  69 */       paramArrayOffloat2 = new float[3];
/*     */     }
/*  71 */     float f1 = paramArrayOffloat1[0];
/*  72 */     float f2 = paramArrayOffloat1[1];
/*  73 */     float f3 = paramArrayOffloat1[2];
/*     */     
/*  75 */     paramArrayOffloat2[0] = f3;
/*  76 */     paramArrayOffloat2[1] = f3;
/*  77 */     paramArrayOffloat2[2] = f3;
/*     */     
/*  79 */     if (f2 > 0.0F) {
/*  80 */       f1 = (f1 < 1.0F) ? (f1 * 6.0F) : 0.0F;
/*  81 */       int i = (int)f1;
/*  82 */       float f = f1 - i;
/*  83 */       switch (i) {
/*     */         case 0:
/*  85 */           paramArrayOffloat2[1] = paramArrayOffloat2[1] * (1.0F - f2 * (1.0F - f));
/*  86 */           paramArrayOffloat2[2] = paramArrayOffloat2[2] * (1.0F - f2);
/*     */           break;
/*     */         case 1:
/*  89 */           paramArrayOffloat2[0] = paramArrayOffloat2[0] * (1.0F - f2 * f);
/*  90 */           paramArrayOffloat2[2] = paramArrayOffloat2[2] * (1.0F - f2);
/*     */           break;
/*     */         case 2:
/*  93 */           paramArrayOffloat2[0] = paramArrayOffloat2[0] * (1.0F - f2);
/*  94 */           paramArrayOffloat2[2] = paramArrayOffloat2[2] * (1.0F - f2 * (1.0F - f));
/*     */           break;
/*     */         case 3:
/*  97 */           paramArrayOffloat2[0] = paramArrayOffloat2[0] * (1.0F - f2);
/*  98 */           paramArrayOffloat2[1] = paramArrayOffloat2[1] * (1.0F - f2 * f);
/*     */           break;
/*     */         case 4:
/* 101 */           paramArrayOffloat2[0] = paramArrayOffloat2[0] * (1.0F - f2 * (1.0F - f));
/* 102 */           paramArrayOffloat2[1] = paramArrayOffloat2[1] * (1.0F - f2);
/*     */           break;
/*     */         case 5:
/* 105 */           paramArrayOffloat2[1] = paramArrayOffloat2[1] * (1.0F - f2);
/* 106 */           paramArrayOffloat2[2] = paramArrayOffloat2[2] * (1.0F - f2 * f);
/*     */           break;
/*     */       } 
/*     */     } 
/* 110 */     return paramArrayOffloat2;
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
/*     */   private static float[] RGBtoHSV(float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
/* 123 */     if (paramArrayOffloat2 == null) {
/* 124 */       paramArrayOffloat2 = new float[3];
/*     */     }
/* 126 */     float f1 = ColorModelHSL.max(paramArrayOffloat1[0], paramArrayOffloat1[1], paramArrayOffloat1[2]);
/* 127 */     float f2 = ColorModelHSL.min(paramArrayOffloat1[0], paramArrayOffloat1[1], paramArrayOffloat1[2]);
/*     */     
/* 129 */     float f3 = f1 - f2;
/* 130 */     if (f3 > 0.0F) {
/* 131 */       f3 /= f1;
/*     */     }
/* 133 */     paramArrayOffloat2[0] = ColorModelHSL.getHue(paramArrayOffloat1[0], paramArrayOffloat1[1], paramArrayOffloat1[2], f1, f2);
/* 134 */     paramArrayOffloat2[1] = f3;
/* 135 */     paramArrayOffloat2[2] = f1;
/* 136 */     return paramArrayOffloat2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/colorchooser/ColorModelHSV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */