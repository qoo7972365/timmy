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
/*    */ public final class SoftLinearResampler
/*    */   extends SoftAbstractResampler
/*    */ {
/*    */   public int getPadding() {
/* 35 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void interpolate(float[] paramArrayOffloat1, float[] paramArrayOffloat2, float paramFloat1, float[] paramArrayOffloat3, float paramFloat2, float[] paramArrayOffloat4, int[] paramArrayOfint, int paramInt) {
/* 42 */     float f1 = paramArrayOffloat3[0];
/* 43 */     float f2 = paramArrayOffloat2[0];
/* 44 */     int i = paramArrayOfint[0];
/* 45 */     float f3 = paramFloat1;
/* 46 */     int j = paramInt;
/* 47 */     if (paramFloat2 == 0.0F) {
/* 48 */       while (f2 < f3 && i < j) {
/* 49 */         int k = (int)f2;
/* 50 */         float f4 = f2 - k;
/* 51 */         float f5 = paramArrayOffloat1[k];
/* 52 */         paramArrayOffloat4[i++] = f5 + (paramArrayOffloat1[k + 1] - f5) * f4;
/* 53 */         f2 += f1;
/*    */       } 
/*    */     } else {
/* 56 */       while (f2 < f3 && i < j) {
/* 57 */         int k = (int)f2;
/* 58 */         float f4 = f2 - k;
/* 59 */         float f5 = paramArrayOffloat1[k];
/* 60 */         paramArrayOffloat4[i++] = f5 + (paramArrayOffloat1[k + 1] - f5) * f4;
/* 61 */         f2 += f1;
/* 62 */         f1 += paramFloat2;
/*    */       } 
/*    */     } 
/* 65 */     paramArrayOffloat2[0] = f2;
/* 66 */     paramArrayOfint[0] = i;
/* 67 */     paramArrayOffloat3[0] = f1;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftLinearResampler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */