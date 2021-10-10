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
/*     */ class InnerShadowEffect
/*     */   extends ShadowEffect
/*     */ {
/*     */   Effect.EffectType getEffectType() {
/*  51 */     return Effect.EffectType.OVER;
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
/*     */   BufferedImage applyEffect(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2, int paramInt1, int paramInt2) {
/*  67 */     if (paramBufferedImage1 == null || paramBufferedImage1.getType() != 2) {
/*  68 */       throw new IllegalArgumentException("Effect only works with source images of type BufferedImage.TYPE_INT_ARGB.");
/*     */     }
/*     */     
/*  71 */     if (paramBufferedImage2 != null && paramBufferedImage2.getType() != 2) {
/*  72 */       throw new IllegalArgumentException("Effect only works with destination images of type BufferedImage.TYPE_INT_ARGB.");
/*     */     }
/*     */ 
/*     */     
/*  76 */     double d = Math.toRadians((this.angle - 90));
/*  77 */     int i = (int)(Math.sin(d) * this.distance);
/*  78 */     int j = (int)(Math.cos(d) * this.distance);
/*     */     
/*  80 */     int k = i + this.size;
/*  81 */     int m = i + this.size;
/*  82 */     int n = paramInt1 + i + this.size + this.size;
/*  83 */     int i1 = paramInt2 + i + this.size;
/*     */     
/*  85 */     int[] arrayOfInt = getArrayCache().getTmpIntArray(paramInt1);
/*  86 */     byte[] arrayOfByte1 = getArrayCache().getTmpByteArray1(n * i1);
/*  87 */     Arrays.fill(arrayOfByte1, (byte)-1);
/*  88 */     byte[] arrayOfByte2 = getArrayCache().getTmpByteArray2(n * i1);
/*  89 */     byte[] arrayOfByte3 = getArrayCache().getTmpByteArray3(n * i1);
/*     */     
/*  91 */     WritableRaster writableRaster1 = paramBufferedImage1.getRaster();
/*  92 */     for (byte b1 = 0; b1 < paramInt2; b1++) {
/*  93 */       int i5 = b1 + m;
/*  94 */       int i6 = i5 * n;
/*  95 */       writableRaster1.getDataElements(0, b1, paramInt1, 1, arrayOfInt);
/*  96 */       for (byte b = 0; b < paramInt1; b++) {
/*  97 */         int i7 = b + k;
/*  98 */         arrayOfByte1[i6 + i7] = (byte)(255 - ((arrayOfInt[b] & 0xFF000000) >>> 24) & 0xFF);
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     float[] arrayOfFloat = EffectUtils.createGaussianKernel(this.size * 2);
/* 103 */     EffectUtils.blur(arrayOfByte1, arrayOfByte3, n, i1, arrayOfFloat, this.size * 2);
/* 104 */     EffectUtils.blur(arrayOfByte3, arrayOfByte2, i1, n, arrayOfFloat, this.size * 2);
/*     */     
/* 106 */     float f = Math.min(1.0F / (1.0F - 0.01F * this.spread), 255.0F);
/* 107 */     for (byte b2 = 0; b2 < arrayOfByte2.length; b2++) {
/* 108 */       int i5 = (int)((arrayOfByte2[b2] & 0xFF) * f);
/* 109 */       arrayOfByte2[b2] = (i5 > 255) ? -1 : (byte)i5;
/*     */     } 
/*     */     
/* 112 */     if (paramBufferedImage2 == null) paramBufferedImage2 = new BufferedImage(paramInt1, paramInt2, 2);
/*     */     
/* 114 */     WritableRaster writableRaster2 = paramBufferedImage2.getRaster();
/* 115 */     int i2 = this.color.getRed(), i3 = this.color.getGreen(), i4 = this.color.getBlue();
/* 116 */     for (byte b3 = 0; b3 < paramInt2; b3++) {
/* 117 */       int i5 = b3 + m;
/* 118 */       int i6 = i5 * n;
/* 119 */       int i7 = (i5 - j) * n;
/* 120 */       for (byte b = 0; b < paramInt1; b++) {
/* 121 */         int i8 = b + k;
/* 122 */         int i9 = 255 - (arrayOfByte1[i6 + i8] & 0xFF);
/* 123 */         int i10 = arrayOfByte2[i7 + i8 - i] & 0xFF;
/* 124 */         int i11 = Math.min(i9, i10);
/* 125 */         arrayOfInt[b] = ((byte)i11 & 0xFF) << 24 | i2 << 16 | i3 << 8 | i4;
/*     */       } 
/* 127 */       writableRaster2.setDataElements(0, b3, paramInt1, 1, arrayOfInt);
/*     */     } 
/* 129 */     return paramBufferedImage2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/InnerShadowEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */