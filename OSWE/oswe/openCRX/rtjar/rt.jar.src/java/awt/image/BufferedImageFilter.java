/*     */ package java.awt.image;
/*     */ 
/*     */ import java.awt.Point;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BufferedImageFilter
/*     */   extends ImageFilter
/*     */   implements Cloneable
/*     */ {
/*     */   BufferedImageOp bufferedImageOp;
/*     */   ColorModel model;
/*     */   int width;
/*     */   int height;
/*     */   byte[] bytePixels;
/*     */   int[] intPixels;
/*     */   
/*     */   public BufferedImageFilter(BufferedImageOp paramBufferedImageOp) {
/*  63 */     if (paramBufferedImageOp == null) {
/*  64 */       throw new NullPointerException("Operation cannot be null");
/*     */     }
/*  66 */     this.bufferedImageOp = paramBufferedImageOp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedImageOp getBufferedImageOp() {
/*  74 */     return this.bufferedImageOp;
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
/*     */   public void setDimensions(int paramInt1, int paramInt2) {
/*  96 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/*  97 */       imageComplete(3);
/*     */       return;
/*     */     } 
/* 100 */     this.width = paramInt1;
/* 101 */     this.height = paramInt2;
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
/*     */   public void setColorModel(ColorModel paramColorModel) {
/* 125 */     this.model = paramColorModel;
/*     */   }
/*     */   
/*     */   private void convertToRGB() {
/* 129 */     int i = this.width * this.height;
/* 130 */     int[] arrayOfInt = new int[i];
/* 131 */     if (this.bytePixels != null) {
/* 132 */       for (byte b = 0; b < i; b++) {
/* 133 */         arrayOfInt[b] = this.model.getRGB(this.bytePixels[b] & 0xFF);
/*     */       }
/* 135 */     } else if (this.intPixels != null) {
/* 136 */       for (byte b = 0; b < i; b++) {
/* 137 */         arrayOfInt[b] = this.model.getRGB(this.intPixels[b]);
/*     */       }
/*     */     } 
/* 140 */     this.bytePixels = null;
/* 141 */     this.intPixels = arrayOfInt;
/* 142 */     this.model = ColorModel.getRGBdefault();
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
/* 165 */     if (paramInt3 < 0 || paramInt4 < 0) {
/* 166 */       throw new IllegalArgumentException("Width (" + paramInt3 + ") and height (" + paramInt4 + ") must be > 0");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 171 */     if (paramInt3 == 0 || paramInt4 == 0) {
/*     */       return;
/*     */     }
/* 174 */     if (paramInt2 < 0) {
/* 175 */       int j = -paramInt2;
/* 176 */       if (j >= paramInt4) {
/*     */         return;
/*     */       }
/* 179 */       paramInt5 += paramInt6 * j;
/* 180 */       paramInt2 += j;
/* 181 */       paramInt4 -= j;
/*     */     } 
/* 183 */     if (paramInt2 + paramInt4 > this.height) {
/* 184 */       paramInt4 = this.height - paramInt2;
/* 185 */       if (paramInt4 <= 0) {
/*     */         return;
/*     */       }
/*     */     } 
/* 189 */     if (paramInt1 < 0) {
/* 190 */       int j = -paramInt1;
/* 191 */       if (j >= paramInt3) {
/*     */         return;
/*     */       }
/* 194 */       paramInt5 += j;
/* 195 */       paramInt1 += j;
/* 196 */       paramInt3 -= j;
/*     */     } 
/* 198 */     if (paramInt1 + paramInt3 > this.width) {
/* 199 */       paramInt3 = this.width - paramInt1;
/* 200 */       if (paramInt3 <= 0) {
/*     */         return;
/*     */       }
/*     */     } 
/* 204 */     int i = paramInt2 * this.width + paramInt1;
/* 205 */     if (this.intPixels == null) {
/* 206 */       if (this.bytePixels == null) {
/* 207 */         this.bytePixels = new byte[this.width * this.height];
/* 208 */         this.model = paramColorModel;
/* 209 */       } else if (this.model != paramColorModel) {
/* 210 */         convertToRGB();
/*     */       } 
/* 212 */       if (this.bytePixels != null) {
/* 213 */         for (int j = paramInt4; j > 0; j--) {
/* 214 */           System.arraycopy(paramArrayOfbyte, paramInt5, this.bytePixels, i, paramInt3);
/* 215 */           paramInt5 += paramInt6;
/* 216 */           i += this.width;
/*     */         } 
/*     */       }
/*     */     } 
/* 220 */     if (this.intPixels != null) {
/* 221 */       int j = this.width - paramInt3;
/* 222 */       int k = paramInt6 - paramInt3;
/* 223 */       for (int m = paramInt4; m > 0; m--) {
/* 224 */         for (int n = paramInt3; n > 0; n--) {
/* 225 */           this.intPixels[i++] = paramColorModel.getRGB(paramArrayOfbyte[paramInt5++] & 0xFF);
/*     */         }
/* 227 */         paramInt5 += k;
/* 228 */         i += j;
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
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorModel paramColorModel, int[] paramArrayOfint, int paramInt5, int paramInt6) {
/* 252 */     if (paramInt3 < 0 || paramInt4 < 0) {
/* 253 */       throw new IllegalArgumentException("Width (" + paramInt3 + ") and height (" + paramInt4 + ") must be > 0");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 258 */     if (paramInt3 == 0 || paramInt4 == 0) {
/*     */       return;
/*     */     }
/* 261 */     if (paramInt2 < 0) {
/* 262 */       int j = -paramInt2;
/* 263 */       if (j >= paramInt4) {
/*     */         return;
/*     */       }
/* 266 */       paramInt5 += paramInt6 * j;
/* 267 */       paramInt2 += j;
/* 268 */       paramInt4 -= j;
/*     */     } 
/* 270 */     if (paramInt2 + paramInt4 > this.height) {
/* 271 */       paramInt4 = this.height - paramInt2;
/* 272 */       if (paramInt4 <= 0) {
/*     */         return;
/*     */       }
/*     */     } 
/* 276 */     if (paramInt1 < 0) {
/* 277 */       int j = -paramInt1;
/* 278 */       if (j >= paramInt3) {
/*     */         return;
/*     */       }
/* 281 */       paramInt5 += j;
/* 282 */       paramInt1 += j;
/* 283 */       paramInt3 -= j;
/*     */     } 
/* 285 */     if (paramInt1 + paramInt3 > this.width) {
/* 286 */       paramInt3 = this.width - paramInt1;
/* 287 */       if (paramInt3 <= 0) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 292 */     if (this.intPixels == null) {
/* 293 */       if (this.bytePixels == null) {
/* 294 */         this.intPixels = new int[this.width * this.height];
/* 295 */         this.model = paramColorModel;
/*     */       } else {
/* 297 */         convertToRGB();
/*     */       } 
/*     */     }
/* 300 */     int i = paramInt2 * this.width + paramInt1;
/* 301 */     if (this.model == paramColorModel) {
/* 302 */       for (int j = paramInt4; j > 0; j--) {
/* 303 */         System.arraycopy(paramArrayOfint, paramInt5, this.intPixels, i, paramInt3);
/* 304 */         paramInt5 += paramInt6;
/* 305 */         i += this.width;
/*     */       } 
/*     */     } else {
/* 308 */       if (this.model != ColorModel.getRGBdefault()) {
/* 309 */         convertToRGB();
/*     */       }
/* 311 */       int j = this.width - paramInt3;
/* 312 */       int k = paramInt6 - paramInt3;
/* 313 */       for (int m = paramInt4; m > 0; m--) {
/* 314 */         for (int n = paramInt3; n > 0; n--) {
/* 315 */           this.intPixels[i++] = paramColorModel.getRGB(paramArrayOfint[paramInt5++]);
/*     */         }
/* 317 */         paramInt5 += k;
/* 318 */         i += j;
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
/*     */   public void imageComplete(int paramInt) {
/*     */     WritableRaster writableRaster1;
/*     */     BufferedImage bufferedImage;
/*     */     WritableRaster writableRaster2;
/*     */     ColorModel colorModel;
/*     */     int i, j;
/* 341 */     switch (paramInt) {
/*     */       
/*     */       case 1:
/*     */       case 4:
/* 345 */         this.model = null;
/* 346 */         this.width = -1;
/* 347 */         this.height = -1;
/* 348 */         this.intPixels = null;
/* 349 */         this.bytePixels = null;
/*     */         break;
/*     */       
/*     */       case 2:
/*     */       case 3:
/* 354 */         if (this.width <= 0 || this.height <= 0)
/* 355 */           break;  if (this.model instanceof DirectColorModel) {
/* 356 */           if (this.intPixels == null)
/* 357 */             break;  writableRaster1 = createDCMraster();
/*     */         }
/* 359 */         else if (this.model instanceof IndexColorModel) {
/* 360 */           int[] arrayOfInt = { 0 };
/* 361 */           if (this.bytePixels == null)
/* 362 */             break;  DataBufferByte dataBufferByte = new DataBufferByte(this.bytePixels, this.width * this.height);
/*     */           
/* 364 */           writableRaster1 = Raster.createInterleavedRaster(dataBufferByte, this.width, this.height, this.width, 1, arrayOfInt, (Point)null);
/*     */         }
/*     */         else {
/*     */           
/* 368 */           convertToRGB();
/* 369 */           if (this.intPixels == null)
/* 370 */             break;  writableRaster1 = createDCMraster();
/*     */         } 
/*     */         
/* 373 */         bufferedImage = new BufferedImage(this.model, writableRaster1, this.model.isAlphaPremultiplied(), null);
/*     */         
/* 375 */         bufferedImage = this.bufferedImageOp.filter(bufferedImage, null);
/* 376 */         writableRaster2 = bufferedImage.getRaster();
/* 377 */         colorModel = bufferedImage.getColorModel();
/* 378 */         i = writableRaster2.getWidth();
/* 379 */         j = writableRaster2.getHeight();
/* 380 */         this.consumer.setDimensions(i, j);
/* 381 */         this.consumer.setColorModel(colorModel);
/* 382 */         if (colorModel instanceof DirectColorModel) {
/* 383 */           DataBufferInt dataBufferInt = (DataBufferInt)writableRaster2.getDataBuffer();
/* 384 */           this.consumer.setPixels(0, 0, i, j, colorModel, dataBufferInt
/* 385 */               .getData(), 0, i); break;
/*     */         } 
/* 387 */         if (colorModel instanceof IndexColorModel) {
/* 388 */           DataBufferByte dataBufferByte = (DataBufferByte)writableRaster2.getDataBuffer();
/* 389 */           this.consumer.setPixels(0, 0, i, j, colorModel, dataBufferByte
/* 390 */               .getData(), 0, i);
/*     */           break;
/*     */         } 
/* 393 */         throw new InternalError("Unknown color model " + colorModel);
/*     */     } 
/*     */ 
/*     */     
/* 397 */     this.consumer.imageComplete(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   private final WritableRaster createDCMraster() {
/* 402 */     DirectColorModel directColorModel = (DirectColorModel)this.model;
/* 403 */     boolean bool = this.model.hasAlpha();
/* 404 */     int[] arrayOfInt = new int[3 + (bool ? 1 : 0)];
/* 405 */     arrayOfInt[0] = directColorModel.getRedMask();
/* 406 */     arrayOfInt[1] = directColorModel.getGreenMask();
/* 407 */     arrayOfInt[2] = directColorModel.getBlueMask();
/* 408 */     if (bool) {
/* 409 */       arrayOfInt[3] = directColorModel.getAlphaMask();
/*     */     }
/* 411 */     DataBufferInt dataBufferInt = new DataBufferInt(this.intPixels, this.width * this.height);
/* 412 */     return Raster.createPackedRaster(dataBufferInt, this.width, this.height, this.width, arrayOfInt, (Point)null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/BufferedImageFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */