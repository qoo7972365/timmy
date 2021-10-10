/*     */ package java.awt.image;
/*     */ 
/*     */ import java.awt.Image;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PixelGrabber
/*     */   implements ImageConsumer
/*     */ {
/*     */   ImageProducer producer;
/*     */   int dstX;
/*     */   int dstY;
/*     */   int dstW;
/*     */   int dstH;
/*     */   ColorModel imageModel;
/*     */   byte[] bytePixels;
/*     */   int[] intPixels;
/*     */   int dstOff;
/*     */   int dstScan;
/*     */   private boolean grabbing;
/*     */   private int flags;
/*     */   private static final int GRABBEDBITS = 48;
/*     */   private static final int DONEBITS = 112;
/*     */   
/*     */   public PixelGrabber(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, int paramInt5, int paramInt6) {
/* 120 */     this(paramImage.getSource(), paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint, paramInt5, paramInt6);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PixelGrabber(ImageProducer paramImageProducer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, int paramInt5, int paramInt6) {
/* 149 */     this.producer = paramImageProducer;
/* 150 */     this.dstX = paramInt1;
/* 151 */     this.dstY = paramInt2;
/* 152 */     this.dstW = paramInt3;
/* 153 */     this.dstH = paramInt4;
/* 154 */     this.dstOff = paramInt5;
/* 155 */     this.dstScan = paramInt6;
/* 156 */     this.intPixels = paramArrayOfint;
/* 157 */     this.imageModel = ColorModel.getRGBdefault();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public PixelGrabber(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/* 185 */     this.producer = paramImage.getSource();
/* 186 */     this.dstX = paramInt1;
/* 187 */     this.dstY = paramInt2;
/* 188 */     this.dstW = paramInt3;
/* 189 */     this.dstH = paramInt4;
/* 190 */     if (paramBoolean) {
/* 191 */       this.imageModel = ColorModel.getRGBdefault();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void startGrabbing() {
/* 199 */     if ((this.flags & 0x70) != 0) {
/*     */       return;
/*     */     }
/* 202 */     if (!this.grabbing) {
/* 203 */       this.grabbing = true;
/* 204 */       this.flags &= 0xFFFFFF7F;
/* 205 */       this.producer.startProduction(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void abortGrabbing() {
/* 213 */     imageComplete(4);
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
/*     */   public boolean grabPixels() throws InterruptedException {
/* 226 */     return grabPixels(0L);
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
/*     */   
/*     */   public synchronized boolean grabPixels(long paramLong) throws InterruptedException {
/* 252 */     if ((this.flags & 0x70) != 0) {
/* 253 */       return ((this.flags & 0x30) != 0);
/*     */     }
/* 255 */     long l = paramLong + System.currentTimeMillis();
/* 256 */     if (!this.grabbing) {
/* 257 */       this.grabbing = true;
/* 258 */       this.flags &= 0xFFFFFF7F;
/* 259 */       this.producer.startProduction(this);
/*     */     } 
/* 261 */     while (this.grabbing) {
/*     */       long l1;
/* 263 */       if (paramLong == 0L) {
/* 264 */         l1 = 0L;
/*     */       } else {
/* 266 */         l1 = l - System.currentTimeMillis();
/* 267 */         if (l1 <= 0L) {
/*     */           break;
/*     */         }
/*     */       } 
/* 271 */       wait(l1);
/*     */     } 
/* 273 */     return ((this.flags & 0x30) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int getStatus() {
/* 283 */     return this.flags;
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
/*     */   public synchronized int getWidth() {
/* 296 */     return (this.dstW < 0) ? -1 : this.dstW;
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
/*     */   public synchronized int getHeight() {
/* 309 */     return (this.dstH < 0) ? -1 : this.dstH;
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
/*     */   public synchronized Object getPixels() {
/* 328 */     return (this.bytePixels == null) ? this.intPixels : this.bytePixels;
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
/*     */   public synchronized ColorModel getColorModel() {
/* 351 */     return this.imageModel;
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
/*     */   public void setDimensions(int paramInt1, int paramInt2) {
/* 367 */     if (this.dstW < 0) {
/* 368 */       this.dstW = paramInt1 - this.dstX;
/*     */     }
/* 370 */     if (this.dstH < 0) {
/* 371 */       this.dstH = paramInt2 - this.dstY;
/*     */     }
/* 373 */     if (this.dstW <= 0 || this.dstH <= 0) {
/* 374 */       imageComplete(3);
/* 375 */     } else if (this.intPixels == null && this.imageModel == 
/* 376 */       ColorModel.getRGBdefault()) {
/* 377 */       this.intPixels = new int[this.dstW * this.dstH];
/* 378 */       this.dstScan = this.dstW;
/* 379 */       this.dstOff = 0;
/*     */     } 
/* 381 */     this.flags |= 0x3;
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
/*     */   public void setHints(int paramInt) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperties(Hashtable<?, ?> paramHashtable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColorModel(ColorModel paramColorModel) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void convertToRGB() {
/* 431 */     int i = this.dstW * this.dstH;
/* 432 */     int[] arrayOfInt = new int[i];
/* 433 */     if (this.bytePixels != null) {
/* 434 */       for (byte b = 0; b < i; b++) {
/* 435 */         arrayOfInt[b] = this.imageModel.getRGB(this.bytePixels[b] & 0xFF);
/*     */       }
/* 437 */     } else if (this.intPixels != null) {
/* 438 */       for (byte b = 0; b < i; b++) {
/* 439 */         arrayOfInt[b] = this.imageModel.getRGB(this.intPixels[b]);
/*     */       }
/*     */     } 
/* 442 */     this.bytePixels = null;
/* 443 */     this.intPixels = arrayOfInt;
/* 444 */     this.dstScan = this.dstW;
/* 445 */     this.dstOff = 0;
/* 446 */     this.imageModel = ColorModel.getRGBdefault();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorModel paramColorModel, byte[] paramArrayOfbyte, int paramInt5, int paramInt6) {
/* 474 */     if (paramInt2 < this.dstY) {
/* 475 */       int j = this.dstY - paramInt2;
/* 476 */       if (j >= paramInt4) {
/*     */         return;
/*     */       }
/* 479 */       paramInt5 += paramInt6 * j;
/* 480 */       paramInt2 += j;
/* 481 */       paramInt4 -= j;
/*     */     } 
/* 483 */     if (paramInt2 + paramInt4 > this.dstY + this.dstH) {
/* 484 */       paramInt4 = this.dstY + this.dstH - paramInt2;
/* 485 */       if (paramInt4 <= 0) {
/*     */         return;
/*     */       }
/*     */     } 
/* 489 */     if (paramInt1 < this.dstX) {
/* 490 */       int j = this.dstX - paramInt1;
/* 491 */       if (j >= paramInt3) {
/*     */         return;
/*     */       }
/* 494 */       paramInt5 += j;
/* 495 */       paramInt1 += j;
/* 496 */       paramInt3 -= j;
/*     */     } 
/* 498 */     if (paramInt1 + paramInt3 > this.dstX + this.dstW) {
/* 499 */       paramInt3 = this.dstX + this.dstW - paramInt1;
/* 500 */       if (paramInt3 <= 0) {
/*     */         return;
/*     */       }
/*     */     } 
/* 504 */     int i = this.dstOff + (paramInt2 - this.dstY) * this.dstScan + paramInt1 - this.dstX;
/* 505 */     if (this.intPixels == null) {
/* 506 */       if (this.bytePixels == null) {
/* 507 */         this.bytePixels = new byte[this.dstW * this.dstH];
/* 508 */         this.dstScan = this.dstW;
/* 509 */         this.dstOff = 0;
/* 510 */         this.imageModel = paramColorModel;
/* 511 */       } else if (this.imageModel != paramColorModel) {
/* 512 */         convertToRGB();
/*     */       } 
/* 514 */       if (this.bytePixels != null) {
/* 515 */         for (int j = paramInt4; j > 0; j--) {
/* 516 */           System.arraycopy(paramArrayOfbyte, paramInt5, this.bytePixels, i, paramInt3);
/* 517 */           paramInt5 += paramInt6;
/* 518 */           i += this.dstScan;
/*     */         } 
/*     */       }
/*     */     } 
/* 522 */     if (this.intPixels != null) {
/* 523 */       int j = this.dstScan - paramInt3;
/* 524 */       int k = paramInt6 - paramInt3;
/* 525 */       for (int m = paramInt4; m > 0; m--) {
/* 526 */         for (int n = paramInt3; n > 0; n--) {
/* 527 */           this.intPixels[i++] = paramColorModel.getRGB(paramArrayOfbyte[paramInt5++] & 0xFF);
/*     */         }
/* 529 */         paramInt5 += k;
/* 530 */         i += j;
/*     */       } 
/*     */     } 
/* 533 */     this.flags |= 0x8;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ColorModel paramColorModel, int[] paramArrayOfint, int paramInt5, int paramInt6) {
/* 561 */     if (paramInt2 < this.dstY) {
/* 562 */       int j = this.dstY - paramInt2;
/* 563 */       if (j >= paramInt4) {
/*     */         return;
/*     */       }
/* 566 */       paramInt5 += paramInt6 * j;
/* 567 */       paramInt2 += j;
/* 568 */       paramInt4 -= j;
/*     */     } 
/* 570 */     if (paramInt2 + paramInt4 > this.dstY + this.dstH) {
/* 571 */       paramInt4 = this.dstY + this.dstH - paramInt2;
/* 572 */       if (paramInt4 <= 0) {
/*     */         return;
/*     */       }
/*     */     } 
/* 576 */     if (paramInt1 < this.dstX) {
/* 577 */       int j = this.dstX - paramInt1;
/* 578 */       if (j >= paramInt3) {
/*     */         return;
/*     */       }
/* 581 */       paramInt5 += j;
/* 582 */       paramInt1 += j;
/* 583 */       paramInt3 -= j;
/*     */     } 
/* 585 */     if (paramInt1 + paramInt3 > this.dstX + this.dstW) {
/* 586 */       paramInt3 = this.dstX + this.dstW - paramInt1;
/* 587 */       if (paramInt3 <= 0) {
/*     */         return;
/*     */       }
/*     */     } 
/* 591 */     if (this.intPixels == null) {
/* 592 */       if (this.bytePixels == null) {
/* 593 */         this.intPixels = new int[this.dstW * this.dstH];
/* 594 */         this.dstScan = this.dstW;
/* 595 */         this.dstOff = 0;
/* 596 */         this.imageModel = paramColorModel;
/*     */       } else {
/* 598 */         convertToRGB();
/*     */       } 
/*     */     }
/* 601 */     int i = this.dstOff + (paramInt2 - this.dstY) * this.dstScan + paramInt1 - this.dstX;
/* 602 */     if (this.imageModel == paramColorModel) {
/* 603 */       for (int j = paramInt4; j > 0; j--) {
/* 604 */         System.arraycopy(paramArrayOfint, paramInt5, this.intPixels, i, paramInt3);
/* 605 */         paramInt5 += paramInt6;
/* 606 */         i += this.dstScan;
/*     */       } 
/*     */     } else {
/* 609 */       if (this.imageModel != ColorModel.getRGBdefault()) {
/* 610 */         convertToRGB();
/*     */       }
/* 612 */       int j = this.dstScan - paramInt3;
/* 613 */       int k = paramInt6 - paramInt3;
/* 614 */       for (int m = paramInt4; m > 0; m--) {
/* 615 */         for (int n = paramInt3; n > 0; n--) {
/* 616 */           this.intPixels[i++] = paramColorModel.getRGB(paramArrayOfint[paramInt5++]);
/*     */         }
/* 618 */         paramInt5 += k;
/* 619 */         i += j;
/*     */       } 
/*     */     } 
/* 622 */     this.flags |= 0x8;
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
/*     */   public synchronized void imageComplete(int paramInt) {
/* 637 */     this.grabbing = false;
/* 638 */     switch (paramInt) {
/*     */       
/*     */       default:
/* 641 */         this.flags |= 0xC0;
/*     */         break;
/*     */       case 4:
/* 644 */         this.flags |= 0x80;
/*     */         break;
/*     */       case 3:
/* 647 */         this.flags |= 0x20;
/*     */         break;
/*     */       case 2:
/* 650 */         this.flags |= 0x10;
/*     */         break;
/*     */     } 
/* 653 */     this.producer.removeConsumer(this);
/* 654 */     notifyAll();
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
/*     */   public synchronized int status() {
/* 670 */     return this.flags;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/PixelGrabber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */