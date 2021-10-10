/*     */ package java.awt.image;
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
/*     */ public class PixelInterleavedSampleModel
/*     */   extends ComponentSampleModel
/*     */ {
/*     */   public PixelInterleavedSampleModel(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint) {
/*  87 */     super(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramArrayOfint);
/*  88 */     int i = this.bandOffsets[0];
/*  89 */     int j = this.bandOffsets[0];
/*  90 */     for (byte b = 1; b < this.bandOffsets.length; b++) {
/*  91 */       i = Math.min(i, this.bandOffsets[b]);
/*  92 */       j = Math.max(j, this.bandOffsets[b]);
/*     */     } 
/*  94 */     j -= i;
/*  95 */     if (j > paramInt5) {
/*  96 */       throw new IllegalArgumentException("Offsets between bands must be less than the scanline  stride");
/*     */     }
/*     */ 
/*     */     
/* 100 */     if (paramInt4 * paramInt2 > paramInt5) {
/* 101 */       throw new IllegalArgumentException("Pixel stride times width must be less than or equal to the scanline stride");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 106 */     if (paramInt4 < j) {
/* 107 */       throw new IllegalArgumentException("Pixel stride must be greater than or equal to the offsets between bands");
/*     */     }
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
/*     */ 
/*     */   
/*     */   public SampleModel createCompatibleSampleModel(int paramInt1, int paramInt2) {
/* 127 */     int arrayOfInt[], i = this.bandOffsets[0];
/* 128 */     int j = this.bandOffsets.length;
/* 129 */     for (byte b = 1; b < j; b++) {
/* 130 */       if (this.bandOffsets[b] < i) {
/* 131 */         i = this.bandOffsets[b];
/*     */       }
/*     */     } 
/*     */     
/* 135 */     if (i > 0) {
/* 136 */       arrayOfInt = new int[j];
/* 137 */       for (byte b1 = 0; b1 < j; b1++) {
/* 138 */         arrayOfInt[b1] = this.bandOffsets[b1] - i;
/*     */       }
/*     */     } else {
/*     */       
/* 142 */       arrayOfInt = this.bandOffsets;
/*     */     } 
/* 144 */     return new PixelInterleavedSampleModel(this.dataType, paramInt1, paramInt2, this.pixelStride, this.pixelStride * paramInt1, arrayOfInt);
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
/*     */   public SampleModel createSubsetSampleModel(int[] paramArrayOfint) {
/* 158 */     int[] arrayOfInt = new int[paramArrayOfint.length];
/* 159 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 160 */       arrayOfInt[b] = this.bandOffsets[paramArrayOfint[b]];
/*     */     }
/* 162 */     return new PixelInterleavedSampleModel(this.dataType, this.width, this.height, this.pixelStride, this.scanlineStride, arrayOfInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 169 */     return super.hashCode() ^ 0x1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/PixelInterleavedSampleModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */