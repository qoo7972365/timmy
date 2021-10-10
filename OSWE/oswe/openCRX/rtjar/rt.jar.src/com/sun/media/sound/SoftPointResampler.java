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
/*    */ public final class SoftPointResampler
/*    */   extends SoftAbstractResampler
/*    */ {
/*    */   public int getPadding() {
/* 35 */     return 100;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void interpolate(float[] paramArrayOffloat1, float[] paramArrayOffloat2, float paramFloat1, float[] paramArrayOffloat3, float paramFloat2, float[] paramArrayOffloat4, int[] paramArrayOfint, int paramInt) {
/* 41 */     float f1 = paramArrayOffloat3[0];
/* 42 */     float f2 = paramArrayOffloat2[0];
/* 43 */     int i = paramArrayOfint[0];
/* 44 */     float f3 = paramFloat1;
/* 45 */     float f4 = paramInt;
/* 46 */     if (paramFloat2 == 0.0F) {
/* 47 */       while (f2 < f3 && i < f4) {
/* 48 */         paramArrayOffloat4[i++] = paramArrayOffloat1[(int)f2];
/* 49 */         f2 += f1;
/*    */       } 
/*    */     } else {
/* 52 */       while (f2 < f3 && i < f4) {
/* 53 */         paramArrayOffloat4[i++] = paramArrayOffloat1[(int)f2];
/* 54 */         f2 += f1;
/* 55 */         f1 += paramFloat2;
/*    */       } 
/*    */     } 
/* 58 */     paramArrayOffloat2[0] = f2;
/* 59 */     paramArrayOfint[0] = i;
/* 60 */     paramArrayOffloat3[0] = f1;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftPointResampler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */