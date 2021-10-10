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
/*     */ public abstract class RGBImageFilter
/*     */   extends ImageFilter
/*     */ {
/*     */   protected ColorModel origmodel;
/*     */   protected ColorModel newmodel;
/*     */   protected boolean canFilterIndexColorModel;
/*     */   
/*     */   public void setColorModel(ColorModel paramColorModel) {
/* 115 */     if (this.canFilterIndexColorModel && paramColorModel instanceof IndexColorModel) {
/* 116 */       IndexColorModel indexColorModel = filterIndexColorModel((IndexColorModel)paramColorModel);
/* 117 */       substituteColorModel(paramColorModel, indexColorModel);
/* 118 */       this.consumer.setColorModel(indexColorModel);
/*     */     } else {
/* 120 */       this.consumer.setColorModel(ColorModel.getRGBdefault());
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
/*     */   public void substituteColorModel(ColorModel paramColorModel1, ColorModel paramColorModel2) {
/* 133 */     this.origmodel = paramColorModel1;
/* 134 */     this.newmodel = paramColorModel2;
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
/*     */   public IndexColorModel filterIndexColorModel(IndexColorModel paramIndexColorModel) {
/* 148 */     int i = paramIndexColorModel.getMapSize();
/* 149 */     byte[] arrayOfByte1 = new byte[i];
/* 150 */     byte[] arrayOfByte2 = new byte[i];
/* 151 */     byte[] arrayOfByte3 = new byte[i];
/* 152 */     byte[] arrayOfByte4 = new byte[i];
/* 153 */     paramIndexColorModel.getReds(arrayOfByte1);
/* 154 */     paramIndexColorModel.getGreens(arrayOfByte2);
/* 155 */     paramIndexColorModel.getBlues(arrayOfByte3);
/* 156 */     paramIndexColorModel.getAlphas(arrayOfByte4);
/* 157 */     int j = paramIndexColorModel.getTransparentPixel();
/* 158 */     boolean bool = false;
/* 159 */     for (byte b = 0; b < i; b++) {
/* 160 */       int k = filterRGB(-1, -1, paramIndexColorModel.getRGB(b));
/* 161 */       arrayOfByte4[b] = (byte)(k >> 24);
/* 162 */       if (arrayOfByte4[b] != -1 && b != j) {
/* 163 */         bool = true;
/*     */       }
/* 165 */       arrayOfByte1[b] = (byte)(k >> 16);
/* 166 */       arrayOfByte2[b] = (byte)(k >> 8);
/* 167 */       arrayOfByte3[b] = (byte)(k >> 0);
/*     */     } 
/* 169 */     if (bool) {
/* 170 */       return new IndexColorModel(paramIndexColorModel.getPixelSize(), i, arrayOfByte1, arrayOfByte2, arrayOfByte3, arrayOfByte4);
/*     */     }
/*     */     
/* 173 */     return new IndexColorModel(paramIndexColorModel.getPixelSize(), i, arrayOfByte1, arrayOfByte2, arrayOfByte3, j);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void filterRGBPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, int paramInt5, int paramInt6) {
/* 196 */     int i = paramInt5;
/* 197 */     for (byte b = 0; b < paramInt4; b++) {
/* 198 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/* 199 */         paramArrayOfint[i] = filterRGB(paramInt1 + b1, paramInt2 + b, paramArrayOfint[i]);
/* 200 */         i++;
/*     */       } 
/* 202 */       i += paramInt6 - paramInt3;
/*     */     } 
/* 204 */     this.consumer.setPixels(paramInt1, paramInt2, paramInt3, paramInt4, ColorModel.getRGBdefault(), paramArrayOfint, paramInt5, paramInt6);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorModel paramColorModel, byte[] paramArrayOfbyte, int paramInt5, int paramInt6) {
/* 227 */     if (paramColorModel == this.origmodel) {
/* 228 */       this.consumer.setPixels(paramInt1, paramInt2, paramInt3, paramInt4, this.newmodel, paramArrayOfbyte, paramInt5, paramInt6);
/*     */     } else {
/* 230 */       int[] arrayOfInt = new int[paramInt3];
/* 231 */       int i = paramInt5;
/* 232 */       for (byte b = 0; b < paramInt4; b++) {
/* 233 */         for (byte b1 = 0; b1 < paramInt3; b1++) {
/* 234 */           arrayOfInt[b1] = paramColorModel.getRGB(paramArrayOfbyte[i] & 0xFF);
/* 235 */           i++;
/*     */         } 
/* 237 */         i += paramInt6 - paramInt3;
/* 238 */         filterRGBPixels(paramInt1, paramInt2 + b, paramInt3, 1, arrayOfInt, 0, paramInt3);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorModel paramColorModel, int[] paramArrayOfint, int paramInt5, int paramInt6) {
/* 264 */     if (paramColorModel == this.origmodel) {
/* 265 */       this.consumer.setPixels(paramInt1, paramInt2, paramInt3, paramInt4, this.newmodel, paramArrayOfint, paramInt5, paramInt6);
/*     */     } else {
/* 267 */       int[] arrayOfInt = new int[paramInt3];
/* 268 */       int i = paramInt5;
/* 269 */       for (byte b = 0; b < paramInt4; b++) {
/* 270 */         for (byte b1 = 0; b1 < paramInt3; b1++) {
/* 271 */           arrayOfInt[b1] = paramColorModel.getRGB(paramArrayOfint[i]);
/* 272 */           i++;
/*     */         } 
/* 274 */         i += paramInt6 - paramInt3;
/* 275 */         filterRGBPixels(paramInt1, paramInt2 + b, paramInt3, 1, arrayOfInt, 0, paramInt3);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract int filterRGB(int paramInt1, int paramInt2, int paramInt3);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/RGBImageFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */