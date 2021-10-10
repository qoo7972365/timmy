/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class GifImageDecoder
/*     */   extends ImageDecoder
/*     */ {
/*     */   private static final boolean verbose = false;
/*     */   private static final int IMAGESEP = 44;
/*     */   private static final int EXBLOCK = 33;
/*     */   private static final int EX_GRAPHICS_CONTROL = 249;
/*     */   private static final int EX_COMMENT = 254;
/*     */   private static final int EX_APPLICATION = 255;
/*     */   private static final int TERMINATOR = 59;
/*     */   private static final int TRANSPARENCYMASK = 1;
/*     */   private static final int INTERLACEMASK = 64;
/*     */   private static final int COLORMAPMASK = 128;
/*     */   int num_global_colors;
/*     */   byte[] global_colormap;
/*  61 */   int trans_pixel = -1;
/*     */   
/*     */   IndexColorModel global_model;
/*  64 */   Hashtable props = new Hashtable<>(); byte[] saved_image;
/*     */   IndexColorModel saved_model;
/*     */   int global_width;
/*     */   int global_height;
/*     */   int global_bgpixel;
/*     */   GifFrame curframe;
/*     */   private static final int normalflags = 30;
/*     */   private static final int interlaceflags = 29;
/*     */   private short[] prefix;
/*     */   private byte[] suffix;
/*     */   private byte[] outCode;
/*     */   
/*  76 */   public GifImageDecoder(InputStreamImageSource paramInputStreamImageSource, InputStream paramInputStream) { super(paramInputStreamImageSource, paramInputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 342 */     this.prefix = new short[4096];
/* 343 */     this.suffix = new byte[4096];
/* 344 */     this.outCode = new byte[4097]; } private static void error(String paramString) throws ImageFormatException { throw new ImageFormatException(paramString); }
/*     */   private int readBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) { while (paramInt2 > 0) { try { int i = this.input.read(paramArrayOfbyte, paramInt1, paramInt2); if (i < 0)
/*     */           break;  paramInt1 += i; paramInt2 -= i; } catch (IOException iOException) { break; }
/*     */        }
/*     */      return paramInt2; }
/*     */   private static final int ExtractByte(byte[] paramArrayOfbyte, int paramInt) { return paramArrayOfbyte[paramInt] & 0xFF; }
/* 350 */   static { NativeLibLoader.loadLibraries();
/* 351 */     initIDs(); }
/*     */ 
/*     */   
/*     */   private static final int ExtractWord(byte[] paramArrayOfbyte, int paramInt) {
/*     */     return paramArrayOfbyte[paramInt] & 0xFF | (paramArrayOfbyte[paramInt + 1] & 0xFF) << 8;
/*     */   }
/*     */   
/*     */   private int sendPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfbyte, ColorModel paramColorModel)
/*     */   {
/*     */     byte b;
/*     */     int j;
/* 362 */     if (paramInt2 < 0) {
/* 363 */       paramInt4 += paramInt2;
/* 364 */       paramInt2 = 0;
/*     */     } 
/* 366 */     if (paramInt2 + paramInt4 > this.global_height) {
/* 367 */       paramInt4 = this.global_height - paramInt2;
/*     */     }
/* 369 */     if (paramInt4 <= 0) {
/* 370 */       return 1;
/*     */     }
/*     */ 
/*     */     
/* 374 */     if (paramInt1 < 0) {
/* 375 */       b = -paramInt1;
/* 376 */       paramInt3 += paramInt1;
/* 377 */       j = 0;
/*     */     } else {
/* 379 */       b = 0;
/*     */       
/* 381 */       j = paramInt1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 386 */     if (j + paramInt3 > this.global_width) {
/* 387 */       paramInt3 = this.global_width - j;
/*     */     }
/* 389 */     if (paramInt3 <= 0) {
/* 390 */       return 1;
/*     */     }
/* 392 */     int i = b + paramInt3;
/*     */ 
/*     */     
/* 395 */     int k = paramInt2 * this.global_width + j;
/* 396 */     boolean bool = (this.curframe.disposal_method == 1) ? true : false;
/* 397 */     if (this.trans_pixel >= 0 && !this.curframe.initialframe) {
/* 398 */       if (this.saved_image != null && paramColorModel.equals(this.saved_model)) {
/* 399 */         for (byte b1 = b; b1 < i; b1++, k++) {
/* 400 */           byte b2 = paramArrayOfbyte[b1];
/* 401 */           if ((b2 & 0xFF) == this.trans_pixel) {
/* 402 */             paramArrayOfbyte[b1] = this.saved_image[k];
/* 403 */           } else if (bool) {
/* 404 */             this.saved_image[k] = b2;
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 412 */         byte b1 = -1;
/* 413 */         int m = 1;
/* 414 */         for (byte b2 = b; b2 < i; b2++, k++) {
/* 415 */           byte b3 = paramArrayOfbyte[b2];
/* 416 */           if ((b3 & 0xFF) == this.trans_pixel) {
/* 417 */             if (b1 >= 0) {
/* 418 */               m = setPixels(paramInt1 + b1, paramInt2, b2 - b1, 1, paramColorModel, paramArrayOfbyte, b1, 0);
/*     */ 
/*     */ 
/*     */               
/* 422 */               if (m == 0) {
/*     */                 break;
/*     */               }
/*     */             } 
/* 426 */             b1 = -1;
/*     */           } else {
/* 428 */             if (b1 < 0) {
/* 429 */               b1 = b2;
/*     */             }
/* 431 */             if (bool) {
/* 432 */               this.saved_image[k] = b3;
/*     */             }
/*     */           } 
/*     */         } 
/* 436 */         if (b1 >= 0) {
/* 437 */           m = setPixels(paramInt1 + b1, paramInt2, i - b1, 1, paramColorModel, paramArrayOfbyte, b1, 0);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 442 */         return m;
/*     */       } 
/* 444 */     } else if (bool) {
/* 445 */       System.arraycopy(paramArrayOfbyte, b, this.saved_image, k, paramInt3);
/*     */     } 
/* 447 */     return setPixels(j, paramInt2, paramInt3, paramInt4, paramColorModel, paramArrayOfbyte, b, 0); }
/*     */   public void produceImage() throws IOException, ImageFormatException { try { readHeader(); byte b1 = 0; byte b2 = 0; int i = -1; int j = 0; int k = -1; boolean bool1 = false; boolean bool2 = false; while (!this.aborted) { byte[] arrayOfByte; boolean bool; String str; int m; switch (m = this.input.read()) { case 33: switch (m = this.input.read()) { case 249: arrayOfByte = new byte[6]; if (readBytes(arrayOfByte, 0, 6) != 0)
/*     */                   return;  if (arrayOfByte[0] != 4 || arrayOfByte[5] != 0)
/*     */                   return;  k = ExtractWord(arrayOfByte, 2) * 10; if (k > 0 && !bool2) { bool2 = true; ImageFetcher.startingAnimation(); }  j = arrayOfByte[1] >> 2 & 0x7; if ((arrayOfByte[1] & 0x1) != 0) { this.trans_pixel = ExtractByte(arrayOfByte, 4); continue; }  this.trans_pixel = -1; continue;default: bool = false; str = ""; while (true) { int n = this.input.read(); if (n <= 0)
/*     */                     break;  byte[] arrayOfByte1 = new byte[n]; if (readBytes(arrayOfByte1, 0, n) != 0)
/*     */                     return;  if (m == 254) { str = str + new String(arrayOfByte1, 0); continue; }  if (m == 255) { if (bool)
/*     */                       if (n == 3 && arrayOfByte1[0] == 1) { if (bool1) { ExtractWord(arrayOfByte1, 1); } else { i = ExtractWord(arrayOfByte1, 1); bool1 = true; }  } else { bool = false; }   if ("NETSCAPE2.0".equals(new String(arrayOfByte1, 0)))
/*     */                       bool = true;  }  }  if (m == 254)
/*     */                   this.props.put("comment", str);  if (bool && !bool2) { bool2 = true; ImageFetcher.startingAnimation(); }  continue;case -1: break; }  return;case 44: if (!bool2)
/*     */               this.input.mark(0);  try { if (!readImage(!b1, j, k))
/*     */                 return;  } catch (Exception exception) { return; }  b2++; b1++; continue;default: if (b2 == 0)
/* 458 */               return;  break;case 59: break; }  if (i == 0 || i-- >= 0) { try { if (this.curframe != null) { this.curframe.dispose(); this.curframe = null; }  this.input.reset(); this.saved_image = null; this.saved_model = null; b2 = 0; } catch (IOException iOException) { return; }  continue; }  imageComplete(3, true); return; }  } finally { close(); }  } private boolean readImage(boolean paramBoolean, int paramInt1, int paramInt2) throws IOException { if (this.curframe != null && !this.curframe.dispose()) {
/* 459 */       abort();
/* 460 */       return false;
/*     */     } 
/*     */     
/* 463 */     long l = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 470 */     byte[] arrayOfByte1 = new byte[259];
/*     */ 
/*     */     
/* 473 */     if (readBytes(arrayOfByte1, 0, 10) != 0) {
/* 474 */       throw new IOException();
/*     */     }
/* 476 */     int i = ExtractWord(arrayOfByte1, 0);
/* 477 */     int j = ExtractWord(arrayOfByte1, 2);
/* 478 */     int k = ExtractWord(arrayOfByte1, 4);
/* 479 */     int m = ExtractWord(arrayOfByte1, 6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 492 */     if (k == 0 && this.global_width != 0) {
/* 493 */       k = this.global_width - i;
/*     */     }
/* 495 */     if (m == 0 && this.global_height != 0) {
/* 496 */       m = this.global_height - j;
/*     */     }
/*     */     
/* 499 */     boolean bool = ((arrayOfByte1[8] & 0x40) != 0) ? true : false;
/*     */     
/* 501 */     IndexColorModel indexColorModel = this.global_model;
/*     */     
/* 503 */     if ((arrayOfByte1[8] & 0x80) != 0) {
/*     */ 
/*     */ 
/*     */       
/* 507 */       int i1 = 1 << (arrayOfByte1[8] & 0x7) + 1;
/*     */ 
/*     */       
/* 510 */       byte[] arrayOfByte = new byte[i1 * 3];
/* 511 */       arrayOfByte[0] = arrayOfByte1[9];
/* 512 */       if (readBytes(arrayOfByte, 1, i1 * 3 - 1) != 0) {
/* 513 */         throw new IOException();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 518 */       if (readBytes(arrayOfByte1, 9, 1) != 0) {
/* 519 */         throw new IOException();
/*     */       }
/* 521 */       if (this.trans_pixel >= i1) {
/*     */         
/* 523 */         i1 = this.trans_pixel + 1;
/* 524 */         arrayOfByte = grow_colormap(arrayOfByte, i1);
/*     */       } 
/* 526 */       indexColorModel = new IndexColorModel(8, i1, arrayOfByte, 0, false, this.trans_pixel);
/*     */     }
/* 528 */     else if (indexColorModel == null || this.trans_pixel != indexColorModel
/* 529 */       .getTransparentPixel()) {
/* 530 */       if (this.trans_pixel >= this.num_global_colors) {
/*     */         
/* 532 */         this.num_global_colors = this.trans_pixel + 1;
/* 533 */         this.global_colormap = grow_colormap(this.global_colormap, this.num_global_colors);
/*     */       } 
/* 535 */       indexColorModel = new IndexColorModel(8, this.num_global_colors, this.global_colormap, 0, false, this.trans_pixel);
/*     */       
/* 537 */       this.global_model = indexColorModel;
/*     */     } 
/*     */ 
/*     */     
/* 541 */     if (paramBoolean) {
/* 542 */       if (this.global_width == 0) this.global_width = k; 
/* 543 */       if (this.global_height == 0) this.global_height = m;
/*     */       
/* 545 */       setDimensions(this.global_width, this.global_height);
/* 546 */       setProperties(this.props);
/* 547 */       setColorModel(indexColorModel);
/* 548 */       headerComplete();
/*     */     } 
/*     */     
/* 551 */     if (paramInt1 == 1 && this.saved_image == null) {
/* 552 */       this.saved_image = new byte[this.global_width * this.global_height];
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 557 */       if (m < this.global_height && indexColorModel != null) {
/* 558 */         byte b1 = (byte)indexColorModel.getTransparentPixel();
/* 559 */         if (b1 >= 0) {
/* 560 */           byte[] arrayOfByte = new byte[this.global_width];
/* 561 */           for (byte b2 = 0; b2 < this.global_width; b2++) {
/* 562 */             arrayOfByte[b2] = b1;
/*     */           }
/*     */           
/* 565 */           setPixels(0, 0, this.global_width, j, indexColorModel, arrayOfByte, 0, 0);
/*     */           
/* 567 */           setPixels(0, j + m, this.global_width, this.global_height - m - j, indexColorModel, arrayOfByte, 0, 0);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 574 */     byte b = bool ? 29 : 30;
/* 575 */     setHints(b);
/*     */     
/* 577 */     this.curframe = new GifFrame(this, paramInt1, paramInt2, (this.curframe == null), indexColorModel, i, j, k, m);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 582 */     byte[] arrayOfByte2 = new byte[k];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 588 */     int n = ExtractByte(arrayOfByte1, 9);
/* 589 */     if (n >= 12)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 594 */       return false;
/*     */     }
/* 596 */     boolean bool1 = parseImage(i, j, k, m, bool, n, arrayOfByte1, arrayOfByte2, indexColorModel);
/*     */ 
/*     */ 
/*     */     
/* 600 */     if (!bool1) {
/* 601 */       abort();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 610 */     return bool1; }
/*     */   private void readHeader() throws IOException, ImageFormatException { byte[] arrayOfByte = new byte[13]; if (readBytes(arrayOfByte, 0, 13) != 0) throw new IOException();  if (arrayOfByte[0] != 71 || arrayOfByte[1] != 73 || arrayOfByte[2] != 70)
/*     */       error("not a GIF file.");  this.global_width = ExtractWord(arrayOfByte, 6); this.global_height = ExtractWord(arrayOfByte, 8); int i = ExtractByte(arrayOfByte, 10); if ((i & 0x80) == 0) { this.num_global_colors = 2; this.global_bgpixel = 0; this.global_colormap = new byte[6]; this.global_colormap[2] = 0; this.global_colormap[1] = 0; this.global_colormap[0] = 0; this.global_colormap[5] = -1; this.global_colormap[4] = -1; this.global_colormap[3] = -1; } else { this.num_global_colors = 1 << (i & 0x7) + 1; this.global_bgpixel = ExtractByte(arrayOfByte, 11); if (arrayOfByte[12] != 0)
/*     */         this.props.put("aspectratio", "" + ((ExtractByte(arrayOfByte, 12) + 15) / 64.0D));  this.global_colormap = new byte[this.num_global_colors * 3]; if (readBytes(this.global_colormap, 0, this.num_global_colors * 3) != 0)
/* 614 */         throw new IOException();  }  this.input.mark(2147483647); } public static byte[] grow_colormap(byte[] paramArrayOfbyte, int paramInt) { byte[] arrayOfByte = new byte[paramInt * 3];
/* 615 */     System.arraycopy(paramArrayOfbyte, 0, arrayOfByte, 0, paramArrayOfbyte.length);
/* 616 */     return arrayOfByte; }
/*     */ 
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   private native boolean parseImage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, int paramInt5, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, IndexColorModel paramIndexColorModel);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/GifImageDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */