/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.NoninvertibleTransformException;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.lang.ref.WeakReference;
/*     */ import sun.awt.image.ByteInterleavedRaster;
/*     */ import sun.awt.image.IntegerInterleavedRaster;
/*     */ import sun.awt.image.SunWritableRaster;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class TexturePaintContext
/*     */   implements PaintContext
/*     */ {
/*  42 */   public static ColorModel xrgbmodel = new DirectColorModel(24, 16711680, 65280, 255);
/*     */   
/*  44 */   public static ColorModel argbmodel = ColorModel.getRGBdefault();
/*     */   
/*     */   ColorModel colorModel;
/*     */   
/*     */   int bWidth;
/*     */   
/*     */   int bHeight;
/*     */   
/*     */   int maxWidth;
/*     */   WritableRaster outRas;
/*     */   double xOrg;
/*     */   double yOrg;
/*     */   double incXAcross;
/*     */   double incYAcross;
/*     */   double incXDown;
/*     */   double incYDown;
/*     */   int colincx;
/*     */   int colincy;
/*     */   int colincxerr;
/*     */   int colincyerr;
/*     */   int rowincx;
/*     */   int rowincy;
/*     */   int rowincxerr;
/*     */   int rowincyerr;
/*     */   private static WeakReference<Raster> xrgbRasRef;
/*     */   private static WeakReference<Raster> argbRasRef;
/*     */   private static WeakReference<Raster> byteRasRef;
/*     */   
/*     */   public static PaintContext getContext(BufferedImage paramBufferedImage, AffineTransform paramAffineTransform, RenderingHints paramRenderingHints, Rectangle paramRectangle) {
/*  73 */     WritableRaster writableRaster = paramBufferedImage.getRaster();
/*  74 */     ColorModel colorModel = paramBufferedImage.getColorModel();
/*  75 */     int i = paramRectangle.width;
/*  76 */     Object object = paramRenderingHints.get(RenderingHints.KEY_INTERPOLATION);
/*     */ 
/*     */     
/*  79 */     boolean bool = (object == null) ? ((paramRenderingHints.get(RenderingHints.KEY_RENDERING) == RenderingHints.VALUE_RENDER_QUALITY) ? true : false) : ((object != RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR) ? true : false);
/*     */     
/*  81 */     if (writableRaster instanceof IntegerInterleavedRaster && (!bool || 
/*  82 */       isFilterableDCM(colorModel))) {
/*     */       
/*  84 */       IntegerInterleavedRaster integerInterleavedRaster = (IntegerInterleavedRaster)writableRaster;
/*  85 */       if (integerInterleavedRaster.getNumDataElements() == 1 && integerInterleavedRaster.getPixelStride() == 1) {
/*  86 */         return new Int(integerInterleavedRaster, colorModel, paramAffineTransform, i, bool);
/*     */       }
/*  88 */     } else if (writableRaster instanceof ByteInterleavedRaster) {
/*  89 */       ByteInterleavedRaster byteInterleavedRaster = (ByteInterleavedRaster)writableRaster;
/*  90 */       if (byteInterleavedRaster.getNumDataElements() == 1 && byteInterleavedRaster.getPixelStride() == 1) {
/*  91 */         if (bool) {
/*  92 */           if (isFilterableICM(colorModel)) {
/*  93 */             return new ByteFilter(byteInterleavedRaster, colorModel, paramAffineTransform, i);
/*     */           }
/*     */         } else {
/*  96 */           return new Byte(byteInterleavedRaster, colorModel, paramAffineTransform, i);
/*     */         } 
/*     */       }
/*     */     } 
/* 100 */     return new Any(writableRaster, colorModel, paramAffineTransform, i, bool);
/*     */   }
/*     */   
/*     */   public static boolean isFilterableICM(ColorModel paramColorModel) {
/* 104 */     if (paramColorModel instanceof IndexColorModel) {
/* 105 */       IndexColorModel indexColorModel = (IndexColorModel)paramColorModel;
/* 106 */       if (indexColorModel.getMapSize() <= 256) {
/* 107 */         return true;
/*     */       }
/*     */     } 
/* 110 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isFilterableDCM(ColorModel paramColorModel) {
/* 114 */     if (paramColorModel instanceof DirectColorModel) {
/* 115 */       DirectColorModel directColorModel = (DirectColorModel)paramColorModel;
/* 116 */       return (isMaskOK(directColorModel.getAlphaMask(), true) && 
/* 117 */         isMaskOK(directColorModel.getRedMask(), false) && 
/* 118 */         isMaskOK(directColorModel.getGreenMask(), false) && 
/* 119 */         isMaskOK(directColorModel.getBlueMask(), false));
/*     */     } 
/* 121 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isMaskOK(int paramInt, boolean paramBoolean) {
/* 125 */     if (paramBoolean && paramInt == 0) {
/* 126 */       return true;
/*     */     }
/* 128 */     return (paramInt == 255 || paramInt == 65280 || paramInt == 16711680 || paramInt == -16777216);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ColorModel getInternedColorModel(ColorModel paramColorModel) {
/* 135 */     if (xrgbmodel == paramColorModel || xrgbmodel.equals(paramColorModel)) {
/* 136 */       return xrgbmodel;
/*     */     }
/* 138 */     if (argbmodel == paramColorModel || argbmodel.equals(paramColorModel)) {
/* 139 */       return argbmodel;
/*     */     }
/* 141 */     return paramColorModel;
/*     */   }
/*     */ 
/*     */   
/*     */   TexturePaintContext(ColorModel paramColorModel, AffineTransform paramAffineTransform, int paramInt1, int paramInt2, int paramInt3) {
/* 146 */     this.colorModel = getInternedColorModel(paramColorModel);
/* 147 */     this.bWidth = paramInt1;
/* 148 */     this.bHeight = paramInt2;
/* 149 */     this.maxWidth = paramInt3;
/*     */     
/*     */     try {
/* 152 */       paramAffineTransform = paramAffineTransform.createInverse();
/* 153 */     } catch (NoninvertibleTransformException noninvertibleTransformException) {
/* 154 */       paramAffineTransform.setToScale(0.0D, 0.0D);
/*     */     } 
/* 156 */     this.incXAcross = mod(paramAffineTransform.getScaleX(), paramInt1);
/* 157 */     this.incYAcross = mod(paramAffineTransform.getShearY(), paramInt2);
/* 158 */     this.incXDown = mod(paramAffineTransform.getShearX(), paramInt1);
/* 159 */     this.incYDown = mod(paramAffineTransform.getScaleY(), paramInt2);
/* 160 */     this.xOrg = paramAffineTransform.getTranslateX();
/* 161 */     this.yOrg = paramAffineTransform.getTranslateY();
/* 162 */     this.colincx = (int)this.incXAcross;
/* 163 */     this.colincy = (int)this.incYAcross;
/* 164 */     this.colincxerr = fractAsInt(this.incXAcross);
/* 165 */     this.colincyerr = fractAsInt(this.incYAcross);
/* 166 */     this.rowincx = (int)this.incXDown;
/* 167 */     this.rowincy = (int)this.incYDown;
/* 168 */     this.rowincxerr = fractAsInt(this.incXDown);
/* 169 */     this.rowincyerr = fractAsInt(this.incYDown);
/*     */   }
/*     */ 
/*     */   
/*     */   static int fractAsInt(double paramDouble) {
/* 174 */     return (int)(paramDouble % 1.0D * 2.147483647E9D);
/*     */   }
/*     */   
/*     */   static double mod(double paramDouble1, double paramDouble2) {
/* 178 */     paramDouble1 %= paramDouble2;
/* 179 */     if (paramDouble1 < 0.0D) {
/* 180 */       paramDouble1 += paramDouble2;
/* 181 */       if (paramDouble1 >= paramDouble2)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 188 */         paramDouble1 = 0.0D;
/*     */       }
/*     */     } 
/* 191 */     return paramDouble1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 198 */     dropRaster(this.colorModel, this.outRas);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorModel getColorModel() {
/* 205 */     return this.colorModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Raster getRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 215 */     if (this.outRas == null || this.outRas
/* 216 */       .getWidth() < paramInt3 || this.outRas
/* 217 */       .getHeight() < paramInt4)
/*     */     {
/*     */       
/* 220 */       this.outRas = makeRaster((paramInt4 == 1) ? Math.max(paramInt3, this.maxWidth) : paramInt3, paramInt4);
/*     */     }
/* 222 */     double d1 = mod(this.xOrg + paramInt1 * this.incXAcross + paramInt2 * this.incXDown, this.bWidth);
/* 223 */     double d2 = mod(this.yOrg + paramInt1 * this.incYAcross + paramInt2 * this.incYDown, this.bHeight);
/*     */     
/* 225 */     setRaster((int)d1, (int)d2, fractAsInt(d1), fractAsInt(d2), paramInt3, paramInt4, this.bWidth, this.bHeight, this.colincx, this.colincxerr, this.colincy, this.colincyerr, this.rowincx, this.rowincxerr, this.rowincy, this.rowincyerr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     SunWritableRaster.markDirty(this.outRas);
/*     */     
/* 234 */     return this.outRas;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized WritableRaster makeRaster(ColorModel paramColorModel, Raster paramRaster, int paramInt1, int paramInt2) {
/* 244 */     if (xrgbmodel == paramColorModel) {
/* 245 */       if (xrgbRasRef != null) {
/* 246 */         WritableRaster writableRaster = (WritableRaster)xrgbRasRef.get();
/* 247 */         if (writableRaster != null && writableRaster.getWidth() >= paramInt1 && writableRaster.getHeight() >= paramInt2) {
/* 248 */           xrgbRasRef = null;
/* 249 */           return writableRaster;
/*     */         } 
/*     */       } 
/*     */       
/* 253 */       if (paramInt1 <= 32 && paramInt2 <= 32) {
/* 254 */         paramInt1 = paramInt2 = 32;
/*     */       }
/* 256 */     } else if (argbmodel == paramColorModel) {
/* 257 */       if (argbRasRef != null) {
/* 258 */         WritableRaster writableRaster = (WritableRaster)argbRasRef.get();
/* 259 */         if (writableRaster != null && writableRaster.getWidth() >= paramInt1 && writableRaster.getHeight() >= paramInt2) {
/* 260 */           argbRasRef = null;
/* 261 */           return writableRaster;
/*     */         } 
/*     */       } 
/*     */       
/* 265 */       if (paramInt1 <= 32 && paramInt2 <= 32) {
/* 266 */         paramInt1 = paramInt2 = 32;
/*     */       }
/*     */     } 
/* 269 */     if (paramRaster != null) {
/* 270 */       return paramRaster.createCompatibleWritableRaster(paramInt1, paramInt2);
/*     */     }
/* 272 */     return paramColorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   static synchronized void dropRaster(ColorModel paramColorModel, Raster paramRaster) {
/* 277 */     if (paramRaster == null) {
/*     */       return;
/*     */     }
/* 280 */     if (xrgbmodel == paramColorModel) {
/* 281 */       xrgbRasRef = new WeakReference<>(paramRaster);
/* 282 */     } else if (argbmodel == paramColorModel) {
/* 283 */       argbRasRef = new WeakReference<>(paramRaster);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized WritableRaster makeByteRaster(Raster paramRaster, int paramInt1, int paramInt2) {
/* 292 */     if (byteRasRef != null) {
/* 293 */       WritableRaster writableRaster = (WritableRaster)byteRasRef.get();
/* 294 */       if (writableRaster != null && writableRaster.getWidth() >= paramInt1 && writableRaster.getHeight() >= paramInt2) {
/* 295 */         byteRasRef = null;
/* 296 */         return writableRaster;
/*     */       } 
/*     */     } 
/*     */     
/* 300 */     if (paramInt1 <= 32 && paramInt2 <= 32) {
/* 301 */       paramInt1 = paramInt2 = 32;
/*     */     }
/* 303 */     return paramRaster.createCompatibleWritableRaster(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   static synchronized void dropByteRaster(Raster paramRaster) {
/* 307 */     if (paramRaster == null) {
/*     */       return;
/*     */     }
/* 310 */     byteRasRef = new WeakReference<>(paramRaster);
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
/*     */   public abstract WritableRaster makeRaster(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13, int paramInt14, int paramInt15, int paramInt16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int blend(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 341 */     paramInt1 >>>= 19;
/* 342 */     paramInt2 >>>= 19;
/*     */     
/* 344 */     int m = 0, k = m, j = k, i = j;
/* 345 */     for (byte b = 0; b < 4; b++) {
/* 346 */       int n = paramArrayOfint[b];
/*     */ 
/*     */ 
/*     */       
/* 350 */       paramInt1 = 4096 - paramInt1;
/* 351 */       if ((b & 0x1) == 0) {
/* 352 */         paramInt2 = 4096 - paramInt2;
/*     */       }
/*     */ 
/*     */       
/* 356 */       int i1 = paramInt1 * paramInt2;
/* 357 */       if (i1 != 0) {
/*     */ 
/*     */ 
/*     */         
/* 361 */         i += (n >>> 24) * i1;
/* 362 */         j += (n >>> 16 & 0xFF) * i1;
/* 363 */         k += (n >>> 8 & 0xFF) * i1;
/* 364 */         m += (n & 0xFF) * i1;
/*     */       } 
/*     */     } 
/* 367 */     return i + 8388608 >>> 24 << 24 | j + 8388608 >>> 24 << 16 | k + 8388608 >>> 24 << 8 | m + 8388608 >>> 24;
/*     */   }
/*     */ 
/*     */   
/*     */   static class Int
/*     */     extends TexturePaintContext
/*     */   {
/*     */     IntegerInterleavedRaster srcRas;
/*     */     
/*     */     int[] inData;
/*     */     
/*     */     int inOff;
/*     */     int inSpan;
/*     */     int[] outData;
/*     */     int outOff;
/*     */     int outSpan;
/*     */     boolean filter;
/*     */     
/*     */     public Int(IntegerInterleavedRaster param1IntegerInterleavedRaster, ColorModel param1ColorModel, AffineTransform param1AffineTransform, int param1Int, boolean param1Boolean) {
/* 386 */       super(param1ColorModel, param1AffineTransform, param1IntegerInterleavedRaster.getWidth(), param1IntegerInterleavedRaster.getHeight(), param1Int);
/* 387 */       this.srcRas = param1IntegerInterleavedRaster;
/* 388 */       this.inData = param1IntegerInterleavedRaster.getDataStorage();
/* 389 */       this.inSpan = param1IntegerInterleavedRaster.getScanlineStride();
/* 390 */       this.inOff = param1IntegerInterleavedRaster.getDataOffset(0);
/* 391 */       this.filter = param1Boolean;
/*     */     }
/*     */     
/*     */     public WritableRaster makeRaster(int param1Int1, int param1Int2) {
/* 395 */       WritableRaster writableRaster = makeRaster(this.colorModel, this.srcRas, param1Int1, param1Int2);
/* 396 */       IntegerInterleavedRaster integerInterleavedRaster = (IntegerInterleavedRaster)writableRaster;
/* 397 */       this.outData = integerInterleavedRaster.getDataStorage();
/* 398 */       this.outSpan = integerInterleavedRaster.getScanlineStride();
/* 399 */       this.outOff = integerInterleavedRaster.getDataOffset(0);
/* 400 */       return writableRaster;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setRaster(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, int param1Int8, int param1Int9, int param1Int10, int param1Int11, int param1Int12, int param1Int13, int param1Int14, int param1Int15, int param1Int16) {
/* 409 */       int[] arrayOfInt1 = this.inData;
/* 410 */       int[] arrayOfInt2 = this.outData;
/* 411 */       int i = this.outOff;
/* 412 */       int j = this.inSpan;
/* 413 */       int k = this.inOff;
/* 414 */       int m = this.outSpan;
/* 415 */       boolean bool = this.filter;
/* 416 */       boolean bool1 = (param1Int9 == 1 && param1Int10 == 0 && param1Int11 == 0 && param1Int12 == 0 && !bool) ? true : false;
/*     */       
/* 418 */       int n = param1Int1;
/* 419 */       int i1 = param1Int2;
/* 420 */       int i2 = param1Int3;
/* 421 */       int i3 = param1Int4;
/* 422 */       if (bool1) {
/* 423 */         m -= param1Int5;
/*     */       }
/* 425 */       int[] arrayOfInt3 = bool ? new int[4] : null;
/* 426 */       for (byte b = 0; b < param1Int6; b++) {
/* 427 */         if (bool1) {
/* 428 */           int i4 = k + i1 * j + param1Int7;
/* 429 */           param1Int1 = param1Int7 - n;
/* 430 */           i += param1Int5;
/* 431 */           if (param1Int7 >= 32) {
/* 432 */             int i5 = param1Int5;
/* 433 */             while (i5 > 0) {
/* 434 */               int i6 = (i5 < param1Int1) ? i5 : param1Int1;
/* 435 */               System.arraycopy(arrayOfInt1, i4 - param1Int1, arrayOfInt2, i - i5, i6);
/*     */ 
/*     */               
/* 438 */               i5 -= i6;
/* 439 */               if ((param1Int1 -= i6) == 0) {
/* 440 */                 param1Int1 = param1Int7;
/*     */               }
/*     */             } 
/*     */           } else {
/* 444 */             for (int i5 = param1Int5; i5 > 0; i5--) {
/* 445 */               arrayOfInt2[i - i5] = arrayOfInt1[i4 - param1Int1];
/* 446 */               if (--param1Int1 == 0) {
/* 447 */                 param1Int1 = param1Int7;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } else {
/* 452 */           param1Int1 = n;
/* 453 */           param1Int2 = i1;
/* 454 */           param1Int3 = i2;
/* 455 */           param1Int4 = i3;
/* 456 */           for (byte b1 = 0; b1 < param1Int5; b1++) {
/* 457 */             if (bool) {
/*     */               int i4;
/* 459 */               if ((i4 = param1Int1 + 1) >= param1Int7)
/* 460 */                 i4 = 0; 
/*     */               int i5;
/* 462 */               if ((i5 = param1Int2 + 1) >= param1Int8) {
/* 463 */                 i5 = 0;
/*     */               }
/* 465 */               arrayOfInt3[0] = arrayOfInt1[k + param1Int2 * j + param1Int1];
/* 466 */               arrayOfInt3[1] = arrayOfInt1[k + param1Int2 * j + i4];
/* 467 */               arrayOfInt3[2] = arrayOfInt1[k + i5 * j + param1Int1];
/* 468 */               arrayOfInt3[3] = arrayOfInt1[k + i5 * j + i4];
/* 469 */               arrayOfInt2[i + b1] = 
/* 470 */                 TexturePaintContext.blend(arrayOfInt3, param1Int3, param1Int4);
/*     */             } else {
/* 472 */               arrayOfInt2[i + b1] = arrayOfInt1[k + param1Int2 * j + param1Int1];
/*     */             } 
/* 474 */             if ((param1Int3 += param1Int10) < 0) {
/* 475 */               param1Int3 &= Integer.MAX_VALUE;
/* 476 */               param1Int1++;
/*     */             } 
/* 478 */             if ((param1Int1 += param1Int9) >= param1Int7) {
/* 479 */               param1Int1 -= param1Int7;
/*     */             }
/* 481 */             if ((param1Int4 += param1Int12) < 0) {
/* 482 */               param1Int4 &= Integer.MAX_VALUE;
/* 483 */               param1Int2++;
/*     */             } 
/* 485 */             if ((param1Int2 += param1Int11) >= param1Int8) {
/* 486 */               param1Int2 -= param1Int8;
/*     */             }
/*     */           } 
/*     */         } 
/* 490 */         if ((i2 += param1Int14) < 0) {
/* 491 */           i2 &= Integer.MAX_VALUE;
/* 492 */           n++;
/*     */         } 
/* 494 */         if ((n += param1Int13) >= param1Int7) {
/* 495 */           n -= param1Int7;
/*     */         }
/* 497 */         if ((i3 += param1Int16) < 0) {
/* 498 */           i3 &= Integer.MAX_VALUE;
/* 499 */           i1++;
/*     */         } 
/* 501 */         if ((i1 += param1Int15) >= param1Int8) {
/* 502 */           i1 -= param1Int8;
/*     */         }
/* 504 */         i += m;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class Byte
/*     */     extends TexturePaintContext
/*     */   {
/*     */     ByteInterleavedRaster srcRas;
/*     */     byte[] inData;
/*     */     int inOff;
/*     */     int inSpan;
/*     */     byte[] outData;
/*     */     int outOff;
/*     */     int outSpan;
/*     */     
/*     */     public Byte(ByteInterleavedRaster param1ByteInterleavedRaster, ColorModel param1ColorModel, AffineTransform param1AffineTransform, int param1Int) {
/* 521 */       super(param1ColorModel, param1AffineTransform, param1ByteInterleavedRaster.getWidth(), param1ByteInterleavedRaster.getHeight(), param1Int);
/* 522 */       this.srcRas = param1ByteInterleavedRaster;
/* 523 */       this.inData = param1ByteInterleavedRaster.getDataStorage();
/* 524 */       this.inSpan = param1ByteInterleavedRaster.getScanlineStride();
/* 525 */       this.inOff = param1ByteInterleavedRaster.getDataOffset(0);
/*     */     }
/*     */     
/*     */     public WritableRaster makeRaster(int param1Int1, int param1Int2) {
/* 529 */       WritableRaster writableRaster = makeByteRaster(this.srcRas, param1Int1, param1Int2);
/* 530 */       ByteInterleavedRaster byteInterleavedRaster = (ByteInterleavedRaster)writableRaster;
/* 531 */       this.outData = byteInterleavedRaster.getDataStorage();
/* 532 */       this.outSpan = byteInterleavedRaster.getScanlineStride();
/* 533 */       this.outOff = byteInterleavedRaster.getDataOffset(0);
/* 534 */       return writableRaster;
/*     */     }
/*     */     
/*     */     public void dispose() {
/* 538 */       dropByteRaster(this.outRas);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setRaster(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, int param1Int8, int param1Int9, int param1Int10, int param1Int11, int param1Int12, int param1Int13, int param1Int14, int param1Int15, int param1Int16) {
/* 547 */       byte[] arrayOfByte1 = this.inData;
/* 548 */       byte[] arrayOfByte2 = this.outData;
/* 549 */       int i = this.outOff;
/* 550 */       int j = this.inSpan;
/* 551 */       int k = this.inOff;
/* 552 */       int m = this.outSpan;
/* 553 */       boolean bool = (param1Int9 == 1 && param1Int10 == 0 && param1Int11 == 0 && param1Int12 == 0) ? true : false;
/*     */       
/* 555 */       int n = param1Int1;
/* 556 */       int i1 = param1Int2;
/* 557 */       int i2 = param1Int3;
/* 558 */       int i3 = param1Int4;
/* 559 */       if (bool) {
/* 560 */         m -= param1Int5;
/*     */       }
/* 562 */       for (byte b = 0; b < param1Int6; b++) {
/* 563 */         if (bool) {
/* 564 */           int i4 = k + i1 * j + param1Int7;
/* 565 */           param1Int1 = param1Int7 - n;
/* 566 */           i += param1Int5;
/* 567 */           if (param1Int7 >= 32) {
/* 568 */             int i5 = param1Int5;
/* 569 */             while (i5 > 0) {
/* 570 */               int i6 = (i5 < param1Int1) ? i5 : param1Int1;
/* 571 */               System.arraycopy(arrayOfByte1, i4 - param1Int1, arrayOfByte2, i - i5, i6);
/*     */ 
/*     */               
/* 574 */               i5 -= i6;
/* 575 */               if ((param1Int1 -= i6) == 0) {
/* 576 */                 param1Int1 = param1Int7;
/*     */               }
/*     */             } 
/*     */           } else {
/* 580 */             for (int i5 = param1Int5; i5 > 0; i5--) {
/* 581 */               arrayOfByte2[i - i5] = arrayOfByte1[i4 - param1Int1];
/* 582 */               if (--param1Int1 == 0) {
/* 583 */                 param1Int1 = param1Int7;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } else {
/* 588 */           param1Int1 = n;
/* 589 */           param1Int2 = i1;
/* 590 */           param1Int3 = i2;
/* 591 */           param1Int4 = i3;
/* 592 */           for (byte b1 = 0; b1 < param1Int5; b1++) {
/* 593 */             arrayOfByte2[i + b1] = arrayOfByte1[k + param1Int2 * j + param1Int1];
/* 594 */             if ((param1Int3 += param1Int10) < 0) {
/* 595 */               param1Int3 &= Integer.MAX_VALUE;
/* 596 */               param1Int1++;
/*     */             } 
/* 598 */             if ((param1Int1 += param1Int9) >= param1Int7) {
/* 599 */               param1Int1 -= param1Int7;
/*     */             }
/* 601 */             if ((param1Int4 += param1Int12) < 0) {
/* 602 */               param1Int4 &= Integer.MAX_VALUE;
/* 603 */               param1Int2++;
/*     */             } 
/* 605 */             if ((param1Int2 += param1Int11) >= param1Int8) {
/* 606 */               param1Int2 -= param1Int8;
/*     */             }
/*     */           } 
/*     */         } 
/* 610 */         if ((i2 += param1Int14) < 0) {
/* 611 */           i2 &= Integer.MAX_VALUE;
/* 612 */           n++;
/*     */         } 
/* 614 */         if ((n += param1Int13) >= param1Int7) {
/* 615 */           n -= param1Int7;
/*     */         }
/* 617 */         if ((i3 += param1Int16) < 0) {
/* 618 */           i3 &= Integer.MAX_VALUE;
/* 619 */           i1++;
/*     */         } 
/* 621 */         if ((i1 += param1Int15) >= param1Int8) {
/* 622 */           i1 -= param1Int8;
/*     */         }
/* 624 */         i += m;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class ByteFilter
/*     */     extends TexturePaintContext
/*     */   {
/*     */     ByteInterleavedRaster srcRas;
/*     */     int[] inPalette;
/*     */     byte[] inData;
/*     */     int inOff;
/*     */     int inSpan;
/*     */     int[] outData;
/*     */     int outOff;
/*     */     int outSpan;
/*     */     
/*     */     public ByteFilter(ByteInterleavedRaster param1ByteInterleavedRaster, ColorModel param1ColorModel, AffineTransform param1AffineTransform, int param1Int) {
/* 642 */       super((param1ColorModel.getTransparency() == 1) ? xrgbmodel : argbmodel, param1AffineTransform, param1ByteInterleavedRaster
/*     */           
/* 644 */           .getWidth(), param1ByteInterleavedRaster.getHeight(), param1Int);
/* 645 */       this.inPalette = new int[256];
/* 646 */       ((IndexColorModel)param1ColorModel).getRGBs(this.inPalette);
/* 647 */       this.srcRas = param1ByteInterleavedRaster;
/* 648 */       this.inData = param1ByteInterleavedRaster.getDataStorage();
/* 649 */       this.inSpan = param1ByteInterleavedRaster.getScanlineStride();
/* 650 */       this.inOff = param1ByteInterleavedRaster.getDataOffset(0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public WritableRaster makeRaster(int param1Int1, int param1Int2) {
/* 656 */       WritableRaster writableRaster = makeRaster(this.colorModel, null, param1Int1, param1Int2);
/* 657 */       IntegerInterleavedRaster integerInterleavedRaster = (IntegerInterleavedRaster)writableRaster;
/* 658 */       this.outData = integerInterleavedRaster.getDataStorage();
/* 659 */       this.outSpan = integerInterleavedRaster.getScanlineStride();
/* 660 */       this.outOff = integerInterleavedRaster.getDataOffset(0);
/* 661 */       return writableRaster;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setRaster(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, int param1Int8, int param1Int9, int param1Int10, int param1Int11, int param1Int12, int param1Int13, int param1Int14, int param1Int15, int param1Int16) {
/* 670 */       byte[] arrayOfByte = this.inData;
/* 671 */       int[] arrayOfInt1 = this.outData;
/* 672 */       int i = this.outOff;
/* 673 */       int j = this.inSpan;
/* 674 */       int k = this.inOff;
/* 675 */       int m = this.outSpan;
/* 676 */       int n = param1Int1;
/* 677 */       int i1 = param1Int2;
/* 678 */       int i2 = param1Int3;
/* 679 */       int i3 = param1Int4;
/* 680 */       int[] arrayOfInt2 = new int[4];
/* 681 */       for (byte b = 0; b < param1Int6; b++) {
/* 682 */         param1Int1 = n;
/* 683 */         param1Int2 = i1;
/* 684 */         param1Int3 = i2;
/* 685 */         param1Int4 = i3;
/* 686 */         for (byte b1 = 0; b1 < param1Int5; b1++) {
/*     */           int i4;
/* 688 */           if ((i4 = param1Int1 + 1) >= param1Int7)
/* 689 */             i4 = 0; 
/*     */           int i5;
/* 691 */           if ((i5 = param1Int2 + 1) >= param1Int8) {
/* 692 */             i5 = 0;
/*     */           }
/* 694 */           arrayOfInt2[0] = this.inPalette[0xFF & arrayOfByte[k + param1Int1 + j * param1Int2]];
/*     */           
/* 696 */           arrayOfInt2[1] = this.inPalette[0xFF & arrayOfByte[k + i4 + j * param1Int2]];
/*     */           
/* 698 */           arrayOfInt2[2] = this.inPalette[0xFF & arrayOfByte[k + param1Int1 + j * i5]];
/*     */           
/* 700 */           arrayOfInt2[3] = this.inPalette[0xFF & arrayOfByte[k + i4 + j * i5]];
/*     */           
/* 702 */           arrayOfInt1[i + b1] = 
/* 703 */             TexturePaintContext.blend(arrayOfInt2, param1Int3, param1Int4);
/* 704 */           if ((param1Int3 += param1Int10) < 0) {
/* 705 */             param1Int3 &= Integer.MAX_VALUE;
/* 706 */             param1Int1++;
/*     */           } 
/* 708 */           if ((param1Int1 += param1Int9) >= param1Int7) {
/* 709 */             param1Int1 -= param1Int7;
/*     */           }
/* 711 */           if ((param1Int4 += param1Int12) < 0) {
/* 712 */             param1Int4 &= Integer.MAX_VALUE;
/* 713 */             param1Int2++;
/*     */           } 
/* 715 */           if ((param1Int2 += param1Int11) >= param1Int8) {
/* 716 */             param1Int2 -= param1Int8;
/*     */           }
/*     */         } 
/* 719 */         if ((i2 += param1Int14) < 0) {
/* 720 */           i2 &= Integer.MAX_VALUE;
/* 721 */           n++;
/*     */         } 
/* 723 */         if ((n += param1Int13) >= param1Int7) {
/* 724 */           n -= param1Int7;
/*     */         }
/* 726 */         if ((i3 += param1Int16) < 0) {
/* 727 */           i3 &= Integer.MAX_VALUE;
/* 728 */           i1++;
/*     */         } 
/* 730 */         if ((i1 += param1Int15) >= param1Int8) {
/* 731 */           i1 -= param1Int8;
/*     */         }
/* 733 */         i += m;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class Any
/*     */     extends TexturePaintContext
/*     */   {
/*     */     WritableRaster srcRas;
/*     */     boolean filter;
/*     */     
/*     */     public Any(WritableRaster param1WritableRaster, ColorModel param1ColorModel, AffineTransform param1AffineTransform, int param1Int, boolean param1Boolean) {
/* 745 */       super(param1ColorModel, param1AffineTransform, param1WritableRaster.getWidth(), param1WritableRaster.getHeight(), param1Int);
/* 746 */       this.srcRas = param1WritableRaster;
/* 747 */       this.filter = param1Boolean;
/*     */     }
/*     */     
/*     */     public WritableRaster makeRaster(int param1Int1, int param1Int2) {
/* 751 */       return makeRaster(this.colorModel, this.srcRas, param1Int1, param1Int2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setRaster(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, int param1Int8, int param1Int9, int param1Int10, int param1Int11, int param1Int12, int param1Int13, int param1Int14, int param1Int15, int param1Int16) {
/* 760 */       Object object = null;
/* 761 */       int i = param1Int1;
/* 762 */       int j = param1Int2;
/* 763 */       int k = param1Int3;
/* 764 */       int m = param1Int4;
/* 765 */       WritableRaster writableRaster1 = this.srcRas;
/* 766 */       WritableRaster writableRaster2 = this.outRas;
/* 767 */       int[] arrayOfInt = this.filter ? new int[4] : null;
/* 768 */       for (byte b = 0; b < param1Int6; b++) {
/* 769 */         param1Int1 = i;
/* 770 */         param1Int2 = j;
/* 771 */         param1Int3 = k;
/* 772 */         param1Int4 = m;
/* 773 */         for (byte b1 = 0; b1 < param1Int5; b1++) {
/* 774 */           object = writableRaster1.getDataElements(param1Int1, param1Int2, object);
/* 775 */           if (this.filter) {
/*     */             int n;
/* 777 */             if ((n = param1Int1 + 1) >= param1Int7)
/* 778 */               n = 0; 
/*     */             int i1;
/* 780 */             if ((i1 = param1Int2 + 1) >= param1Int8) {
/* 781 */               i1 = 0;
/*     */             }
/* 783 */             arrayOfInt[0] = this.colorModel.getRGB(object);
/* 784 */             object = writableRaster1.getDataElements(n, param1Int2, object);
/* 785 */             arrayOfInt[1] = this.colorModel.getRGB(object);
/* 786 */             object = writableRaster1.getDataElements(param1Int1, i1, object);
/* 787 */             arrayOfInt[2] = this.colorModel.getRGB(object);
/* 788 */             object = writableRaster1.getDataElements(n, i1, object);
/* 789 */             arrayOfInt[3] = this.colorModel.getRGB(object);
/*     */             
/* 791 */             int i2 = TexturePaintContext.blend(arrayOfInt, param1Int3, param1Int4);
/* 792 */             object = this.colorModel.getDataElements(i2, object);
/*     */           } 
/* 794 */           writableRaster2.setDataElements(b1, b, object);
/* 795 */           if ((param1Int3 += param1Int10) < 0) {
/* 796 */             param1Int3 &= Integer.MAX_VALUE;
/* 797 */             param1Int1++;
/*     */           } 
/* 799 */           if ((param1Int1 += param1Int9) >= param1Int7) {
/* 800 */             param1Int1 -= param1Int7;
/*     */           }
/* 802 */           if ((param1Int4 += param1Int12) < 0) {
/* 803 */             param1Int4 &= Integer.MAX_VALUE;
/* 804 */             param1Int2++;
/*     */           } 
/* 806 */           if ((param1Int2 += param1Int11) >= param1Int8) {
/* 807 */             param1Int2 -= param1Int8;
/*     */           }
/*     */         } 
/* 810 */         if ((k += param1Int14) < 0) {
/* 811 */           k &= Integer.MAX_VALUE;
/* 812 */           i++;
/*     */         } 
/* 814 */         if ((i += param1Int13) >= param1Int7) {
/* 815 */           i -= param1Int7;
/*     */         }
/* 817 */         if ((m += param1Int16) < 0) {
/* 818 */           m &= Integer.MAX_VALUE;
/* 819 */           j++;
/*     */         } 
/* 821 */         if ((j += param1Int15) >= param1Int8)
/* 822 */           j -= param1Int8; 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/TexturePaintContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */