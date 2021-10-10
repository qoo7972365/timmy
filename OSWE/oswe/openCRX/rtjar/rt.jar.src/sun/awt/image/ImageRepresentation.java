/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.ImageConsumer;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImageRepresentation
/*     */   extends ImageWatched
/*     */   implements ImageConsumer
/*     */ {
/*     */   InputStreamImageSource src;
/*     */   ToolkitImage image;
/*     */   int tag;
/*     */   long pData;
/*  58 */   int width = -1;
/*  59 */   int height = -1;
/*     */   
/*     */   int hints;
/*     */   
/*     */   int availinfo;
/*     */   
/*     */   Rectangle newbits;
/*     */   BufferedImage bimage;
/*     */   WritableRaster biRaster;
/*     */   protected ColorModel cmodel;
/*  69 */   ColorModel srcModel = null;
/*  70 */   int[] srcLUT = null;
/*  71 */   int srcLUTtransIndex = -1;
/*  72 */   int numSrcLUT = 0;
/*     */   
/*     */   boolean forceCMhint;
/*     */   
/*     */   int sstride;
/*     */   
/*     */   boolean isDefaultBI = false;
/*     */   boolean isSameCM = false;
/*     */   
/*     */   static {
/*  82 */     NativeLibLoader.loadLibraries();
/*  83 */     initIDs();
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
/*     */   
/*     */   public synchronized void reconstruct(int paramInt) {
/* 108 */     if (this.src != null) {
/* 109 */       this.src.checkSecurity(null, false);
/*     */     }
/* 111 */     int i = paramInt & (this.availinfo ^ 0xFFFFFFFF);
/* 112 */     if ((this.availinfo & 0x40) == 0 && i != 0) {
/* 113 */       this.numWaiters++;
/*     */       try {
/* 115 */         startProduction();
/* 116 */         i = paramInt & (this.availinfo ^ 0xFFFFFFFF);
/* 117 */         while ((this.availinfo & 0x40) == 0 && i != 0) {
/*     */ 
/*     */           
/*     */           try {
/* 121 */             wait();
/* 122 */           } catch (InterruptedException interruptedException) {
/* 123 */             Thread.currentThread().interrupt();
/*     */             return;
/*     */           } 
/* 126 */           i = paramInt & (this.availinfo ^ 0xFFFFFFFF);
/*     */         } 
/*     */       } finally {
/* 129 */         decrementWaiters();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDimensions(int paramInt1, int paramInt2) {
/* 135 */     if (this.src != null) {
/* 136 */       this.src.checkSecurity(null, false);
/*     */     }
/*     */     
/* 139 */     this.image.setDimensions(paramInt1, paramInt2);
/*     */     
/* 141 */     newInfo(this.image, 3, 0, 0, paramInt1, paramInt2);
/*     */ 
/*     */     
/* 144 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/* 145 */       imageComplete(1);
/*     */       
/*     */       return;
/*     */     } 
/* 149 */     if (this.width != paramInt1 || this.height != paramInt2)
/*     */     {
/* 151 */       this.bimage = null;
/*     */     }
/*     */     
/* 154 */     this.width = paramInt1;
/* 155 */     this.height = paramInt2;
/*     */     
/* 157 */     this.availinfo |= 0x3;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 161 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 165 */     return this.height;
/*     */   }
/*     */   
/*     */   ColorModel getColorModel() {
/* 169 */     return this.cmodel;
/*     */   }
/*     */   
/*     */   BufferedImage getBufferedImage() {
/* 173 */     return this.bimage;
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
/*     */   protected BufferedImage createImage(ColorModel paramColorModel, WritableRaster paramWritableRaster, boolean paramBoolean, Hashtable paramHashtable) {
/* 190 */     BufferedImage bufferedImage = new BufferedImage(paramColorModel, paramWritableRaster, paramBoolean, null);
/*     */     
/* 192 */     bufferedImage.setAccelerationPriority(this.image.getAccelerationPriority());
/* 193 */     return bufferedImage;
/*     */   }
/*     */   
/*     */   public void setProperties(Hashtable<?, ?> paramHashtable) {
/* 197 */     if (this.src != null) {
/* 198 */       this.src.checkSecurity(null, false);
/*     */     }
/* 200 */     this.image.setProperties(paramHashtable);
/* 201 */     newInfo(this.image, 4, 0, 0, 0, 0);
/*     */   }
/*     */   
/*     */   public void setColorModel(ColorModel paramColorModel) {
/* 205 */     if (this.src != null) {
/* 206 */       this.src.checkSecurity(null, false);
/*     */     }
/* 208 */     this.srcModel = paramColorModel;
/*     */ 
/*     */     
/* 211 */     if (paramColorModel instanceof IndexColorModel) {
/* 212 */       if (paramColorModel.getTransparency() == 3) {
/*     */ 
/*     */         
/* 215 */         this.cmodel = ColorModel.getRGBdefault();
/* 216 */         this.srcLUT = null;
/*     */       } else {
/*     */         
/* 219 */         IndexColorModel indexColorModel = (IndexColorModel)paramColorModel;
/* 220 */         this.numSrcLUT = indexColorModel.getMapSize();
/* 221 */         this.srcLUT = new int[Math.max(this.numSrcLUT, 256)];
/* 222 */         indexColorModel.getRGBs(this.srcLUT);
/* 223 */         this.srcLUTtransIndex = indexColorModel.getTransparentPixel();
/* 224 */         this.cmodel = paramColorModel;
/*     */       }
/*     */     
/*     */     }
/* 228 */     else if (this.cmodel == null) {
/* 229 */       this.cmodel = paramColorModel;
/* 230 */       this.srcLUT = null;
/*     */     }
/* 232 */     else if (paramColorModel instanceof DirectColorModel) {
/*     */       
/* 234 */       DirectColorModel directColorModel = (DirectColorModel)paramColorModel;
/* 235 */       if (directColorModel.getRedMask() == 16711680 && directColorModel
/* 236 */         .getGreenMask() == 65280 && directColorModel
/* 237 */         .getBlueMask() == 255) {
/* 238 */         this.cmodel = paramColorModel;
/* 239 */         this.srcLUT = null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 244 */     this.isSameCM = (this.cmodel == paramColorModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void createBufferedImage() {
/* 251 */     this.isDefaultBI = false;
/*     */     try {
/* 253 */       this.biRaster = this.cmodel.createCompatibleWritableRaster(this.width, this.height);
/* 254 */       this.bimage = createImage(this.cmodel, this.biRaster, this.cmodel
/* 255 */           .isAlphaPremultiplied(), (Hashtable)null);
/* 256 */     } catch (Exception exception) {
/*     */       
/* 258 */       this.cmodel = ColorModel.getRGBdefault();
/* 259 */       this.biRaster = this.cmodel.createCompatibleWritableRaster(this.width, this.height);
/* 260 */       this.bimage = createImage(this.cmodel, this.biRaster, false, (Hashtable)null);
/*     */     } 
/* 262 */     int i = this.bimage.getType();
/*     */     
/* 264 */     if (this.cmodel == ColorModel.getRGBdefault() || i == 1 || i == 3) {
/*     */ 
/*     */       
/* 267 */       this.isDefaultBI = true;
/*     */     }
/* 269 */     else if (this.cmodel instanceof DirectColorModel) {
/* 270 */       DirectColorModel directColorModel = (DirectColorModel)this.cmodel;
/* 271 */       if (directColorModel.getRedMask() == 16711680 && directColorModel
/* 272 */         .getGreenMask() == 65280 && directColorModel
/* 273 */         .getBlueMask() == 255) {
/* 274 */         this.isDefaultBI = true;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void convertToRGB() {
/* 280 */     int i = this.bimage.getWidth();
/* 281 */     int j = this.bimage.getHeight();
/* 282 */     int k = i * j;
/*     */     
/* 284 */     DataBufferInt dataBufferInt = new DataBufferInt(k);
/*     */ 
/*     */     
/* 287 */     int[] arrayOfInt1 = SunWritableRaster.stealData(dataBufferInt, 0);
/* 288 */     if (this.cmodel instanceof IndexColorModel && this.biRaster instanceof ByteComponentRaster && this.biRaster
/*     */       
/* 290 */       .getNumDataElements() == 1) {
/*     */       
/* 292 */       ByteComponentRaster byteComponentRaster = (ByteComponentRaster)this.biRaster;
/* 293 */       byte[] arrayOfByte = byteComponentRaster.getDataStorage();
/* 294 */       int m = byteComponentRaster.getDataOffset(0);
/* 295 */       for (byte b = 0; b < k; b++) {
/* 296 */         arrayOfInt1[b] = this.srcLUT[arrayOfByte[m + b] & 0xFF];
/*     */       }
/*     */     } else {
/*     */       
/* 300 */       Object object = null;
/* 301 */       byte b1 = 0;
/* 302 */       for (byte b2 = 0; b2 < j; b2++) {
/* 303 */         for (byte b = 0; b < i; b++) {
/* 304 */           object = this.biRaster.getDataElements(b, b2, object);
/* 305 */           arrayOfInt1[b1++] = this.cmodel.getRGB(object);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 310 */     SunWritableRaster.markDirty(dataBufferInt);
/*     */     
/* 312 */     this.isSameCM = false;
/* 313 */     this.cmodel = ColorModel.getRGBdefault();
/*     */     
/* 315 */     int[] arrayOfInt2 = { 16711680, 65280, 255, -16777216 };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 320 */     this.biRaster = Raster.createPackedRaster(dataBufferInt, i, j, i, arrayOfInt2, (Point)null);
/*     */ 
/*     */     
/* 323 */     this.bimage = createImage(this.cmodel, this.biRaster, this.cmodel
/* 324 */         .isAlphaPremultiplied(), (Hashtable)null);
/* 325 */     this.srcLUT = null;
/* 326 */     this.isDefaultBI = true;
/*     */   }
/*     */   
/*     */   public void setHints(int paramInt) {
/* 330 */     if (this.src != null) {
/* 331 */       this.src.checkSecurity(null, false);
/*     */     }
/* 333 */     this.hints = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean s_useNative = true;
/*     */ 
/*     */   
/*     */   private boolean consuming;
/*     */ 
/*     */   
/*     */   private int numWaiters;
/*     */ 
/*     */   
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorModel paramColorModel, byte[] paramArrayOfbyte, int paramInt5, int paramInt6) {
/* 348 */     int i = paramInt5;
/*     */     
/* 350 */     Object object = null;
/*     */     
/* 352 */     if (this.src != null) {
/* 353 */       this.src.checkSecurity(null, false);
/*     */     }
/*     */ 
/*     */     
/* 357 */     synchronized (this) {
/* 358 */       int i2; if (this.bimage == null) {
/* 359 */         if (this.cmodel == null) {
/* 360 */           this.cmodel = paramColorModel;
/*     */         }
/* 362 */         createBufferedImage();
/*     */       } 
/*     */       
/* 365 */       if (paramInt3 <= 0 || paramInt4 <= 0) {
/*     */         return;
/*     */       }
/*     */       
/* 369 */       int j = this.biRaster.getWidth();
/* 370 */       int k = this.biRaster.getHeight();
/*     */       
/* 372 */       int m = paramInt1 + paramInt3;
/* 373 */       int n = paramInt2 + paramInt4;
/* 374 */       if (paramInt1 < 0) {
/* 375 */         paramInt5 -= paramInt1;
/* 376 */         paramInt1 = 0;
/* 377 */       } else if (m < 0) {
/* 378 */         m = j;
/*     */       } 
/* 380 */       if (paramInt2 < 0) {
/* 381 */         paramInt5 -= paramInt2 * paramInt6;
/* 382 */         paramInt2 = 0;
/* 383 */       } else if (n < 0) {
/* 384 */         n = k;
/*     */       } 
/* 386 */       if (m > j) {
/* 387 */         m = j;
/*     */       }
/* 389 */       if (n > k) {
/* 390 */         n = k;
/*     */       }
/* 392 */       if (paramInt1 >= m || paramInt2 >= n) {
/*     */         return;
/*     */       }
/*     */       
/* 396 */       paramInt3 = m - paramInt1;
/* 397 */       paramInt4 = n - paramInt2;
/*     */       
/* 399 */       if (paramInt5 < 0 || paramInt5 >= paramArrayOfbyte.length)
/*     */       {
/* 401 */         throw new ArrayIndexOutOfBoundsException("Data offset out of bounds.");
/*     */       }
/*     */       
/* 404 */       int i1 = paramArrayOfbyte.length - paramInt5;
/* 405 */       if (i1 < paramInt3)
/*     */       {
/* 407 */         throw new ArrayIndexOutOfBoundsException("Data array is too short.");
/*     */       }
/*     */       
/* 410 */       if (paramInt6 < 0) {
/* 411 */         i2 = paramInt5 / -paramInt6 + 1;
/* 412 */       } else if (paramInt6 > 0) {
/* 413 */         i2 = (i1 - paramInt3) / paramInt6 + 1;
/*     */       } else {
/* 415 */         i2 = paramInt4;
/*     */       } 
/* 417 */       if (paramInt4 > i2)
/*     */       {
/* 419 */         throw new ArrayIndexOutOfBoundsException("Data array is too short.");
/*     */       }
/*     */       
/* 422 */       if (this.isSameCM && this.cmodel != paramColorModel && this.srcLUT != null && paramColorModel instanceof IndexColorModel && this.biRaster instanceof ByteComponentRaster) {
/*     */ 
/*     */ 
/*     */         
/* 426 */         IndexColorModel indexColorModel = (IndexColorModel)paramColorModel;
/* 427 */         ByteComponentRaster byteComponentRaster = (ByteComponentRaster)this.biRaster;
/* 428 */         int i3 = this.numSrcLUT;
/* 429 */         if (!setDiffICM(paramInt1, paramInt2, paramInt3, paramInt4, this.srcLUT, this.srcLUTtransIndex, this.numSrcLUT, indexColorModel, paramArrayOfbyte, paramInt5, paramInt6, byteComponentRaster, byteComponentRaster
/*     */ 
/*     */             
/* 432 */             .getDataOffset(0))) {
/* 433 */           convertToRGB();
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 438 */           byteComponentRaster.markDirty();
/* 439 */           if (i3 != this.numSrcLUT) {
/* 440 */             boolean bool = indexColorModel.hasAlpha();
/* 441 */             if (this.srcLUTtransIndex != -1) {
/* 442 */               bool = true;
/*     */             }
/* 444 */             int i4 = indexColorModel.getPixelSize();
/* 445 */             indexColorModel = new IndexColorModel(i4, this.numSrcLUT, this.srcLUT, 0, bool, this.srcLUTtransIndex, (i4 > 8) ? 1 : 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 452 */             this.cmodel = indexColorModel;
/* 453 */             this.bimage = createImage(indexColorModel, byteComponentRaster, false, (Hashtable)null);
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 459 */       if (this.isDefaultBI) {
/*     */         
/* 461 */         IntegerComponentRaster integerComponentRaster = (IntegerComponentRaster)this.biRaster;
/*     */         
/* 463 */         if (this.srcLUT != null && paramColorModel instanceof IndexColorModel) {
/* 464 */           if (paramColorModel != this.srcModel) {
/*     */             
/* 466 */             ((IndexColorModel)paramColorModel).getRGBs(this.srcLUT);
/* 467 */             this.srcModel = paramColorModel;
/*     */           } 
/*     */           
/* 470 */           if (s_useNative) {
/*     */ 
/*     */             
/* 473 */             if (setICMpixels(paramInt1, paramInt2, paramInt3, paramInt4, this.srcLUT, paramArrayOfbyte, paramInt5, paramInt6, integerComponentRaster)) {
/*     */ 
/*     */               
/* 476 */               integerComponentRaster.markDirty();
/*     */             } else {
/* 478 */               abort();
/*     */               
/*     */               return;
/*     */             } 
/*     */           } else {
/* 483 */             int[] arrayOfInt = new int[paramInt3 * paramInt4];
/* 484 */             byte b1 = 0;
/*     */             
/* 486 */             for (byte b2 = 0; b2 < paramInt4; b2++, 
/* 487 */               i += paramInt6) {
/* 488 */               int i3 = i;
/* 489 */               for (byte b = 0; b < paramInt3; b++) {
/* 490 */                 arrayOfInt[b1++] = this.srcLUT[paramArrayOfbyte[i3++] & 0xFF];
/*     */               }
/*     */             } 
/* 493 */             integerComponentRaster.setDataElements(paramInt1, paramInt2, paramInt3, paramInt4, arrayOfInt);
/*     */           } 
/*     */         } else {
/*     */           
/* 497 */           int[] arrayOfInt = new int[paramInt3];
/* 498 */           for (int i3 = paramInt2; i3 < paramInt2 + paramInt4; i3++, i += paramInt6) {
/* 499 */             int i4 = i;
/* 500 */             for (byte b = 0; b < paramInt3; b++) {
/* 501 */               arrayOfInt[b] = paramColorModel.getRGB(paramArrayOfbyte[i4++] & 0xFF);
/*     */             }
/* 503 */             integerComponentRaster.setDataElements(paramInt1, i3, paramInt3, 1, arrayOfInt);
/*     */           } 
/* 505 */           this.availinfo |= 0x8;
/*     */         }
/*     */       
/* 508 */       } else if (this.cmodel == paramColorModel && this.biRaster instanceof ByteComponentRaster && this.biRaster
/*     */         
/* 510 */         .getNumDataElements() == 1) {
/* 511 */         ByteComponentRaster byteComponentRaster = (ByteComponentRaster)this.biRaster;
/* 512 */         if (paramInt5 == 0 && paramInt6 == paramInt3) {
/* 513 */           byteComponentRaster.putByteData(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfbyte);
/*     */         } else {
/*     */           
/* 516 */           byte[] arrayOfByte = new byte[paramInt3];
/* 517 */           int i3 = paramInt5;
/* 518 */           for (int i4 = paramInt2; i4 < paramInt2 + paramInt4; i4++) {
/* 519 */             System.arraycopy(paramArrayOfbyte, i3, arrayOfByte, 0, paramInt3);
/* 520 */             byteComponentRaster.putByteData(paramInt1, i4, paramInt3, 1, arrayOfByte);
/* 521 */             i3 += paramInt6;
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 526 */         for (int i3 = paramInt2; i3 < paramInt2 + paramInt4; i3++, i += paramInt6) {
/* 527 */           int i4 = i;
/* 528 */           for (int i5 = paramInt1; i5 < paramInt1 + paramInt3; i5++) {
/* 529 */             this.bimage.setRGB(i5, i3, paramColorModel
/* 530 */                 .getRGB(paramArrayOfbyte[i4++] & 0xFF));
/*     */           }
/*     */         } 
/* 533 */         this.availinfo |= 0x8;
/*     */       } 
/*     */     } 
/*     */     
/* 537 */     if ((this.availinfo & 0x10) == 0) {
/* 538 */       newInfo(this.image, 8, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorModel paramColorModel, int[] paramArrayOfint, int paramInt5, int paramInt6) {
/* 546 */     int i = paramInt5;
/*     */ 
/*     */     
/* 549 */     if (this.src != null) {
/* 550 */       this.src.checkSecurity(null, false);
/*     */     }
/*     */ 
/*     */     
/* 554 */     synchronized (this) {
/* 555 */       if (this.bimage == null) {
/* 556 */         if (this.cmodel == null) {
/* 557 */           this.cmodel = paramColorModel;
/*     */         }
/* 559 */         createBufferedImage();
/*     */       } 
/*     */       
/* 562 */       int[] arrayOfInt = new int[paramInt3];
/*     */ 
/*     */ 
/*     */       
/* 566 */       if (this.cmodel instanceof IndexColorModel)
/*     */       {
/*     */         
/* 569 */         convertToRGB();
/*     */       }
/*     */       
/* 572 */       if (paramColorModel == this.cmodel && this.biRaster instanceof IntegerComponentRaster) {
/*     */         
/* 574 */         IntegerComponentRaster integerComponentRaster = (IntegerComponentRaster)this.biRaster;
/*     */ 
/*     */         
/* 577 */         if (paramInt5 == 0 && paramInt6 == paramInt3) {
/* 578 */           integerComponentRaster.setDataElements(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint);
/*     */         }
/*     */         else {
/*     */           
/* 582 */           for (int j = paramInt2; j < paramInt2 + paramInt4; j++, i += paramInt6) {
/* 583 */             System.arraycopy(paramArrayOfint, i, arrayOfInt, 0, paramInt3);
/* 584 */             integerComponentRaster.setDataElements(paramInt1, j, paramInt3, 1, arrayOfInt);
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 589 */         if (paramColorModel.getTransparency() != 1 && this.cmodel
/* 590 */           .getTransparency() == 1) {
/* 591 */           convertToRGB();
/*     */         }
/*     */         
/* 594 */         if (this.isDefaultBI) {
/* 595 */           IntegerComponentRaster integerComponentRaster = (IntegerComponentRaster)this.biRaster;
/*     */           
/* 597 */           int[] arrayOfInt1 = integerComponentRaster.getDataStorage();
/* 598 */           if (this.cmodel.equals(paramColorModel)) {
/* 599 */             int j = integerComponentRaster.getScanlineStride();
/* 600 */             int k = paramInt2 * j + paramInt1;
/* 601 */             for (byte b = 0; b < paramInt4; b++, i += paramInt6) {
/* 602 */               System.arraycopy(paramArrayOfint, i, arrayOfInt1, k, paramInt3);
/* 603 */               k += j;
/*     */             } 
/*     */ 
/*     */             
/* 607 */             integerComponentRaster.markDirty();
/*     */           } else {
/*     */             
/* 610 */             for (int j = paramInt2; j < paramInt2 + paramInt4; j++, i += paramInt6) {
/* 611 */               int k = i;
/* 612 */               for (byte b = 0; b < paramInt3; b++) {
/* 613 */                 arrayOfInt[b] = paramColorModel.getRGB(paramArrayOfint[k++]);
/*     */               }
/* 615 */               integerComponentRaster.setDataElements(paramInt1, j, paramInt3, 1, arrayOfInt);
/*     */             } 
/*     */           } 
/*     */           
/* 619 */           this.availinfo |= 0x8;
/*     */         } else {
/*     */           
/* 622 */           Object object = null;
/*     */           
/* 624 */           for (int j = paramInt2; j < paramInt2 + paramInt4; j++, i += paramInt6) {
/* 625 */             int k = i;
/* 626 */             for (int m = paramInt1; m < paramInt1 + paramInt3; m++) {
/* 627 */               int n = paramColorModel.getRGB(paramArrayOfint[k++]);
/* 628 */               object = this.cmodel.getDataElements(n, object);
/* 629 */               this.biRaster.setDataElements(m, j, object);
/*     */             } 
/*     */           } 
/* 632 */           this.availinfo |= 0x8;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 639 */     if ((this.availinfo & 0x10) == 0) {
/* 640 */       newInfo(this.image, 8, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*     */   }
/*     */   
/*     */   public BufferedImage getOpaqueRGBImage() {
/* 645 */     if (this.bimage.getType() == 2) {
/* 646 */       int i = this.bimage.getWidth();
/* 647 */       int j = this.bimage.getHeight();
/* 648 */       int k = i * j;
/*     */ 
/*     */       
/* 651 */       DataBufferInt dataBufferInt = (DataBufferInt)this.biRaster.getDataBuffer();
/* 652 */       int[] arrayOfInt1 = SunWritableRaster.stealData(dataBufferInt, 0);
/*     */       
/* 654 */       for (byte b = 0; b < k; b++) {
/* 655 */         if (arrayOfInt1[b] >>> 24 != 255) {
/* 656 */           return this.bimage;
/*     */         }
/*     */       } 
/*     */       
/* 660 */       DirectColorModel directColorModel = new DirectColorModel(24, 16711680, 65280, 255);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 665 */       int[] arrayOfInt2 = { 16711680, 65280, 255 };
/* 666 */       WritableRaster writableRaster = Raster.createPackedRaster(dataBufferInt, i, j, i, arrayOfInt2, (Point)null);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 671 */         return createImage(directColorModel, writableRaster, false, (Hashtable)null);
/*     */       
/*     */       }
/* 674 */       catch (Exception exception) {
/* 675 */         return this.bimage;
/*     */       } 
/*     */     } 
/* 678 */     return this.bimage;
/*     */   }
/*     */   
/* 681 */   public ImageRepresentation(ToolkitImage paramToolkitImage, ColorModel paramColorModel, boolean paramBoolean) { this.consuming = false; this.image = paramToolkitImage; if (this.image.getSource() instanceof InputStreamImageSource)
/*     */       this.src = (InputStreamImageSource)this.image.getSource();  setColorModel(paramColorModel); this.forceCMhint = paramBoolean; } public void imageComplete(int paramInt) { boolean bool;
/*     */     char c;
/* 684 */     if (this.src != null) {
/* 685 */       this.src.checkSecurity(null, false);
/*     */     }
/*     */ 
/*     */     
/* 689 */     switch (paramInt) {
/*     */       
/*     */       default:
/* 692 */         bool = true;
/* 693 */         c = '';
/*     */         break;
/*     */       case 1:
/* 696 */         this.image.addInfo(64);
/* 697 */         bool = true;
/* 698 */         c = '@';
/* 699 */         dispose();
/*     */         break;
/*     */       case 3:
/* 702 */         bool = true;
/* 703 */         c = ' ';
/*     */         break;
/*     */       case 2:
/* 706 */         bool = false;
/* 707 */         c = '\020';
/*     */         break;
/*     */     } 
/* 710 */     synchronized (this) {
/* 711 */       if (bool) {
/* 712 */         this.image.getSource().removeConsumer(this);
/* 713 */         this.consuming = false;
/* 714 */         this.newbits = null;
/*     */         
/* 716 */         if (this.bimage != null) {
/* 717 */           this.bimage = getOpaqueRGBImage();
/*     */         }
/*     */       } 
/* 720 */       this.availinfo |= c;
/* 721 */       notifyAll();
/*     */     } 
/*     */     
/* 724 */     newInfo(this.image, c, 0, 0, this.width, this.height);
/*     */     
/* 726 */     this.image.infoDone(paramInt); }
/*     */ 
/*     */   
/*     */   void startProduction() {
/* 730 */     if (!this.consuming) {
/* 731 */       this.consuming = true;
/* 732 */       this.image.getSource().startProduction(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void checkConsumption() {
/* 739 */     if (isWatcherListEmpty() && this.numWaiters == 0 && (this.availinfo & 0x20) == 0)
/*     */     {
/*     */       
/* 742 */       dispose();
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void notifyWatcherListEmpty() {
/* 747 */     checkConsumption();
/*     */   }
/*     */   
/*     */   private synchronized void decrementWaiters() {
/* 751 */     this.numWaiters--;
/* 752 */     checkConsumption();
/*     */   }
/*     */   
/*     */   public boolean prepare(ImageObserver paramImageObserver) {
/* 756 */     if (this.src != null) {
/* 757 */       this.src.checkSecurity(null, false);
/*     */     }
/* 759 */     if ((this.availinfo & 0x40) != 0) {
/* 760 */       if (paramImageObserver != null) {
/* 761 */         paramImageObserver.imageUpdate(this.image, 192, -1, -1, -1, -1);
/*     */       }
/*     */       
/* 764 */       return false;
/*     */     } 
/* 766 */     boolean bool = ((this.availinfo & 0x20) != 0) ? true : false;
/* 767 */     if (!bool) {
/* 768 */       addWatcher(paramImageObserver);
/* 769 */       startProduction();
/*     */       
/* 771 */       bool = ((this.availinfo & 0x20) != 0) ? true : false;
/*     */     } 
/* 773 */     return bool;
/*     */   }
/*     */ 
/*     */   
/*     */   public int check(ImageObserver paramImageObserver) {
/* 778 */     if (this.src != null) {
/* 779 */       this.src.checkSecurity(null, false);
/*     */     }
/* 781 */     if ((this.availinfo & 0x60) == 0) {
/* 782 */       addWatcher(paramImageObserver);
/*     */     }
/*     */     
/* 785 */     return this.availinfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean drawToBufImage(Graphics paramGraphics, ToolkitImage paramToolkitImage, int paramInt1, int paramInt2, Color paramColor, ImageObserver paramImageObserver) {
/* 792 */     if (this.src != null) {
/* 793 */       this.src.checkSecurity(null, false);
/*     */     }
/* 795 */     if ((this.availinfo & 0x40) != 0) {
/* 796 */       if (paramImageObserver != null) {
/* 797 */         paramImageObserver.imageUpdate(this.image, 192, -1, -1, -1, -1);
/*     */       }
/*     */       
/* 800 */       return false;
/*     */     } 
/* 802 */     boolean bool1 = ((this.availinfo & 0x20) != 0) ? true : false;
/* 803 */     boolean bool2 = ((this.availinfo & 0x80) != 0) ? true : false;
/*     */     
/* 805 */     if (!bool1 && !bool2) {
/* 806 */       addWatcher(paramImageObserver);
/* 807 */       startProduction();
/*     */       
/* 809 */       bool1 = ((this.availinfo & 0x20) != 0) ? true : false;
/*     */     } 
/*     */     
/* 812 */     if (bool1 || 0 != (this.availinfo & 0x10)) {
/* 813 */       paramGraphics.drawImage(this.bimage, paramInt1, paramInt2, paramColor, null);
/*     */     }
/*     */     
/* 816 */     return bool1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean drawToBufImage(Graphics paramGraphics, ToolkitImage paramToolkitImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor, ImageObserver paramImageObserver) {
/* 823 */     if (this.src != null) {
/* 824 */       this.src.checkSecurity(null, false);
/*     */     }
/* 826 */     if ((this.availinfo & 0x40) != 0) {
/* 827 */       if (paramImageObserver != null) {
/* 828 */         paramImageObserver.imageUpdate(this.image, 192, -1, -1, -1, -1);
/*     */       }
/*     */       
/* 831 */       return false;
/*     */     } 
/*     */     
/* 834 */     boolean bool1 = ((this.availinfo & 0x20) != 0) ? true : false;
/* 835 */     boolean bool2 = ((this.availinfo & 0x80) != 0) ? true : false;
/*     */     
/* 837 */     if (!bool1 && !bool2) {
/* 838 */       addWatcher(paramImageObserver);
/* 839 */       startProduction();
/*     */       
/* 841 */       bool1 = ((this.availinfo & 0x20) != 0) ? true : false;
/*     */     } 
/*     */     
/* 844 */     if (bool1 || 0 != (this.availinfo & 0x10)) {
/* 845 */       paramGraphics.drawImage(this.bimage, paramInt1, paramInt2, paramInt3, paramInt4, paramColor, null);
/*     */     }
/*     */     
/* 848 */     return bool1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean drawToBufImage(Graphics paramGraphics, ToolkitImage paramToolkitImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Color paramColor, ImageObserver paramImageObserver) {
/* 856 */     if (this.src != null) {
/* 857 */       this.src.checkSecurity(null, false);
/*     */     }
/* 859 */     if ((this.availinfo & 0x40) != 0) {
/* 860 */       if (paramImageObserver != null) {
/* 861 */         paramImageObserver.imageUpdate(this.image, 192, -1, -1, -1, -1);
/*     */       }
/*     */       
/* 864 */       return false;
/*     */     } 
/* 866 */     boolean bool1 = ((this.availinfo & 0x20) != 0) ? true : false;
/* 867 */     boolean bool2 = ((this.availinfo & 0x80) != 0) ? true : false;
/*     */     
/* 869 */     if (!bool1 && !bool2) {
/* 870 */       addWatcher(paramImageObserver);
/* 871 */       startProduction();
/*     */       
/* 873 */       bool1 = ((this.availinfo & 0x20) != 0) ? true : false;
/*     */     } 
/*     */     
/* 876 */     if (bool1 || 0 != (this.availinfo & 0x10)) {
/* 877 */       paramGraphics.drawImage(this.bimage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramColor, null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 883 */     return bool1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean drawToBufImage(Graphics paramGraphics, ToolkitImage paramToolkitImage, AffineTransform paramAffineTransform, ImageObserver paramImageObserver) {
/* 890 */     Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*     */     
/* 892 */     if (this.src != null) {
/* 893 */       this.src.checkSecurity(null, false);
/*     */     }
/* 895 */     if ((this.availinfo & 0x40) != 0) {
/* 896 */       if (paramImageObserver != null) {
/* 897 */         paramImageObserver.imageUpdate(this.image, 192, -1, -1, -1, -1);
/*     */       }
/*     */       
/* 900 */       return false;
/*     */     } 
/* 902 */     boolean bool1 = ((this.availinfo & 0x20) != 0) ? true : false;
/* 903 */     boolean bool2 = ((this.availinfo & 0x80) != 0) ? true : false;
/*     */     
/* 905 */     if (!bool1 && !bool2) {
/* 906 */       addWatcher(paramImageObserver);
/* 907 */       startProduction();
/*     */       
/* 909 */       bool1 = ((this.availinfo & 0x20) != 0) ? true : false;
/*     */     } 
/*     */     
/* 912 */     if (bool1 || 0 != (this.availinfo & 0x10)) {
/* 913 */       graphics2D.drawImage(this.bimage, paramAffineTransform, null);
/*     */     }
/*     */     
/* 916 */     return bool1;
/*     */   }
/*     */   
/*     */   synchronized void abort() {
/* 920 */     this.image.getSource().removeConsumer(this);
/* 921 */     this.consuming = false;
/* 922 */     this.newbits = null;
/* 923 */     this.bimage = null;
/* 924 */     this.biRaster = null;
/* 925 */     this.cmodel = null;
/* 926 */     this.srcLUT = null;
/* 927 */     this.isDefaultBI = false;
/* 928 */     this.isSameCM = false;
/*     */     
/* 930 */     newInfo(this.image, 128, -1, -1, -1, -1);
/* 931 */     this.availinfo &= 0xFFFFFF87;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void dispose() {
/* 938 */     this.image.getSource().removeConsumer(this);
/* 939 */     this.consuming = false;
/* 940 */     this.newbits = null;
/* 941 */     this.availinfo &= 0xFFFFFFC7;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAccelerationPriority(float paramFloat) {
/* 947 */     if (this.bimage != null)
/* 948 */       this.bimage.setAccelerationPriority(paramFloat); 
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   private native boolean setICMpixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, byte[] paramArrayOfbyte, int paramInt5, int paramInt6, IntegerComponentRaster paramIntegerComponentRaster);
/*     */   
/*     */   private native boolean setDiffICM(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, int paramInt5, int paramInt6, IndexColorModel paramIndexColorModel, byte[] paramArrayOfbyte, int paramInt7, int paramInt8, ByteComponentRaster paramByteComponentRaster, int paramInt9);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/ImageRepresentation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */