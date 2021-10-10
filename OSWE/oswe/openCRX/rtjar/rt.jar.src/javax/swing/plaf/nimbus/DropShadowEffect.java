/*     */ package javax.swing.plaf.nimbus;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.util.Arrays;
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
/*     */ class DropShadowEffect
/*     */   extends ShadowEffect
/*     */ {
/*     */   Effect.EffectType getEffectType() {
/*  52 */     return Effect.EffectType.UNDER;
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
/*     */ 
/*     */ 
/*     */   
/*     */   BufferedImage applyEffect(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2, int paramInt1, int paramInt2) {
/*  69 */     if (paramBufferedImage1 == null || paramBufferedImage1.getType() != 2) {
/*  70 */       throw new IllegalArgumentException("Effect only works with source images of type BufferedImage.TYPE_INT_ARGB.");
/*     */     }
/*     */     
/*  73 */     if (paramBufferedImage2 != null && paramBufferedImage2.getType() != 2) {
/*  74 */       throw new IllegalArgumentException("Effect only works with destination images of type BufferedImage.TYPE_INT_ARGB.");
/*     */     }
/*     */ 
/*     */     
/*  78 */     double d = Math.toRadians((this.angle - 90));
/*  79 */     int i = (int)(Math.sin(d) * this.distance);
/*  80 */     int j = (int)(Math.cos(d) * this.distance);
/*     */     
/*  82 */     int k = i + this.size;
/*  83 */     int m = i + this.size;
/*  84 */     int n = paramInt1 + i + this.size + this.size;
/*  85 */     int i1 = paramInt2 + i + this.size;
/*     */     
/*  87 */     int[] arrayOfInt = getArrayCache().getTmpIntArray(paramInt1);
/*  88 */     byte[] arrayOfByte1 = getArrayCache().getTmpByteArray1(n * i1);
/*  89 */     Arrays.fill(arrayOfByte1, (byte)0);
/*  90 */     byte[] arrayOfByte2 = getArrayCache().getTmpByteArray2(n * i1);
/*     */     
/*  92 */     WritableRaster writableRaster1 = paramBufferedImage1.getRaster();
/*  93 */     for (byte b1 = 0; b1 < paramInt2; b1++) {
/*  94 */       int i5 = b1 + m;
/*  95 */       int i6 = i5 * n;
/*  96 */       writableRaster1.getDataElements(0, b1, paramInt1, 1, arrayOfInt);
/*  97 */       for (byte b = 0; b < paramInt1; b++) {
/*  98 */         int i7 = b + k;
/*  99 */         arrayOfByte1[i6 + i7] = (byte)((arrayOfInt[b] & 0xFF000000) >>> 24);
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     float[] arrayOfFloat = EffectUtils.createGaussianKernel(this.size);
/* 104 */     EffectUtils.blur(arrayOfByte1, arrayOfByte2, n, i1, arrayOfFloat, this.size);
/* 105 */     EffectUtils.blur(arrayOfByte2, arrayOfByte1, i1, n, arrayOfFloat, this.size);
/*     */     
/* 107 */     float f = Math.min(1.0F / (1.0F - 0.01F * this.spread), 255.0F);
/* 108 */     for (byte b2 = 0; b2 < arrayOfByte1.length; b2++) {
/* 109 */       int i5 = (int)((arrayOfByte1[b2] & 0xFF) * f);
/* 110 */       arrayOfByte1[b2] = (i5 > 255) ? -1 : (byte)i5;
/*     */     } 
/*     */     
/* 113 */     if (paramBufferedImage2 == null) paramBufferedImage2 = new BufferedImage(paramInt1, paramInt2, 2);
/*     */     
/* 115 */     WritableRaster writableRaster2 = paramBufferedImage2.getRaster();
/* 116 */     int i2 = this.color.getRed(), i3 = this.color.getGreen(), i4 = this.color.getBlue();
/* 117 */     for (byte b3 = 0; b3 < paramInt2; b3++) {
/* 118 */       int i5 = b3 + m;
/* 119 */       int i6 = (i5 - j) * n;
/* 120 */       for (byte b = 0; b < paramInt1; b++) {
/* 121 */         int i7 = b + k;
/* 122 */         arrayOfInt[b] = arrayOfByte1[i6 + i7 - i] << 24 | i2 << 16 | i3 << 8 | i4;
/*     */       } 
/* 124 */       writableRaster2.setDataElements(0, b3, paramInt1, 1, arrayOfInt);
/*     */     } 
/* 126 */     return paramBufferedImage2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/DropShadowEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */