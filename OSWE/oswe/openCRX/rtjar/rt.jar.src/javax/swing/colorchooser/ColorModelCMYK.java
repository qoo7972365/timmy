/*    */ package javax.swing.colorchooser;
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
/*    */ final class ColorModelCMYK
/*    */   extends ColorModel
/*    */ {
/*    */   ColorModelCMYK() {
/* 31 */     super("cmyk", new String[] { "Cyan", "Magenta", "Yellow", "Black", "Alpha" });
/*    */   }
/*    */ 
/*    */   
/*    */   void setColor(int paramInt, float[] paramArrayOffloat) {
/* 36 */     super.setColor(paramInt, paramArrayOffloat);
/* 37 */     paramArrayOffloat[4] = paramArrayOffloat[3];
/* 38 */     RGBtoCMYK(paramArrayOffloat, paramArrayOffloat);
/*    */   }
/*    */ 
/*    */   
/*    */   int getColor(float[] paramArrayOffloat) {
/* 43 */     CMYKtoRGB(paramArrayOffloat, paramArrayOffloat);
/* 44 */     paramArrayOffloat[3] = paramArrayOffloat[4];
/* 45 */     return super.getColor(paramArrayOffloat);
/*    */   }
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
/*    */   private static float[] CMYKtoRGB(float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
/* 58 */     if (paramArrayOffloat2 == null) {
/* 59 */       paramArrayOffloat2 = new float[3];
/*    */     }
/* 61 */     paramArrayOffloat2[0] = 1.0F + paramArrayOffloat1[0] * paramArrayOffloat1[3] - paramArrayOffloat1[3] - paramArrayOffloat1[0];
/* 62 */     paramArrayOffloat2[1] = 1.0F + paramArrayOffloat1[1] * paramArrayOffloat1[3] - paramArrayOffloat1[3] - paramArrayOffloat1[1];
/* 63 */     paramArrayOffloat2[2] = 1.0F + paramArrayOffloat1[2] * paramArrayOffloat1[3] - paramArrayOffloat1[3] - paramArrayOffloat1[2];
/* 64 */     return paramArrayOffloat2;
/*    */   }
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
/*    */   private static float[] RGBtoCMYK(float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
/* 77 */     if (paramArrayOffloat2 == null) {
/* 78 */       paramArrayOffloat2 = new float[4];
/*    */     }
/* 80 */     float f = ColorModelHSL.max(paramArrayOffloat1[0], paramArrayOffloat1[1], paramArrayOffloat1[2]);
/* 81 */     if (f > 0.0F) {
/* 82 */       paramArrayOffloat2[0] = 1.0F - paramArrayOffloat1[0] / f;
/* 83 */       paramArrayOffloat2[1] = 1.0F - paramArrayOffloat1[1] / f;
/* 84 */       paramArrayOffloat2[2] = 1.0F - paramArrayOffloat1[2] / f;
/*    */     } else {
/*    */       
/* 87 */       paramArrayOffloat2[0] = 0.0F;
/* 88 */       paramArrayOffloat2[1] = 0.0F;
/* 89 */       paramArrayOffloat2[2] = 0.0F;
/*    */     } 
/* 91 */     paramArrayOffloat2[3] = 1.0F - f;
/* 92 */     return paramArrayOffloat2;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/colorchooser/ColorModelCMYK.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */