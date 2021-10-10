/*    */ package com.sun.media.sound;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SoftCubicResampler
/*    */   extends SoftAbstractResampler
/*    */ {
/*    */   public int getPadding() {
/* 35 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void interpolate(float[] paramArrayOffloat1, float[] paramArrayOffloat2, float paramFloat1, float[] paramArrayOffloat3, float paramFloat2, float[] paramArrayOffloat4, int[] paramArrayOfint, int paramInt) {
/* 41 */     float f1 = paramArrayOffloat3[0];
/* 42 */     float f2 = paramArrayOffloat2[0];
/* 43 */     int i = paramArrayOfint[0];
/* 44 */     float f3 = paramFloat1;
/* 45 */     int j = paramInt;
/* 46 */     if (paramFloat2 == 0.0F) {
/* 47 */       while (f2 < f3 && i < j) {
/* 48 */         int k = (int)f2;
/* 49 */         float f4 = f2 - k;
/* 50 */         float f5 = paramArrayOffloat1[k - 1];
/* 51 */         float f6 = paramArrayOffloat1[k];
/* 52 */         float f7 = paramArrayOffloat1[k + 1];
/* 53 */         float f8 = paramArrayOffloat1[k + 2];
/* 54 */         float f9 = f8 - f7 + f6 - f5;
/* 55 */         float f10 = f5 - f6 - f9;
/* 56 */         float f11 = f7 - f5;
/* 57 */         float f12 = f6;
/*    */ 
/*    */         
/* 60 */         paramArrayOffloat4[i++] = ((f9 * f4 + f10) * f4 + f11) * f4 + f12;
/* 61 */         f2 += f1;
/*    */       } 
/*    */     } else {
/* 64 */       while (f2 < f3 && i < j) {
/* 65 */         int k = (int)f2;
/* 66 */         float f4 = f2 - k;
/* 67 */         float f5 = paramArrayOffloat1[k - 1];
/* 68 */         float f6 = paramArrayOffloat1[k];
/* 69 */         float f7 = paramArrayOffloat1[k + 1];
/* 70 */         float f8 = paramArrayOffloat1[k + 2];
/* 71 */         float f9 = f8 - f7 + f6 - f5;
/* 72 */         float f10 = f5 - f6 - f9;
/* 73 */         float f11 = f7 - f5;
/* 74 */         float f12 = f6;
/*    */ 
/*    */         
/* 77 */         paramArrayOffloat4[i++] = ((f9 * f4 + f10) * f4 + f11) * f4 + f12;
/* 78 */         f2 += f1;
/* 79 */         f1 += paramFloat2;
/*    */       } 
/*    */     } 
/* 82 */     paramArrayOffloat2[0] = f2;
/* 83 */     paramArrayOfint[0] = i;
/* 84 */     paramArrayOffloat3[0] = f1;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftCubicResampler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */