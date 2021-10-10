/*     */ package java.awt.image;
/*     */ 
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import sun.awt.image.ImagingLib;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LookupOp
/*     */   implements BufferedImageOp, RasterOp
/*     */ {
/*     */   private LookupTable ltable;
/*     */   private int numComponents;
/*     */   RenderingHints hints;
/*     */   
/*     */   public LookupOp(LookupTable paramLookupTable, RenderingHints paramRenderingHints) {
/*  91 */     this.ltable = paramLookupTable;
/*  92 */     this.hints = paramRenderingHints;
/*  93 */     this.numComponents = this.ltable.getNumComponents();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final LookupTable getTable() {
/* 102 */     return this.ltable;
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
/*     */   public final BufferedImage filter(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2) {
/* 126 */     ColorModel colorModel2, colorModel1 = paramBufferedImage1.getColorModel();
/* 127 */     int i = colorModel1.getNumColorComponents();
/*     */     
/* 129 */     if (colorModel1 instanceof IndexColorModel) {
/* 130 */       throw new IllegalArgumentException("LookupOp cannot be performed on an indexed image");
/*     */     }
/*     */ 
/*     */     
/* 134 */     int j = this.ltable.getNumComponents();
/* 135 */     if (j != 1 && j != colorModel1
/* 136 */       .getNumComponents() && j != colorModel1
/* 137 */       .getNumColorComponents())
/*     */     {
/* 139 */       throw new IllegalArgumentException("Number of arrays in the  lookup table (" + j + " is not compatible with the  src image: " + paramBufferedImage1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     boolean bool = false;
/*     */     
/* 149 */     int k = paramBufferedImage1.getWidth();
/* 150 */     int m = paramBufferedImage1.getHeight();
/*     */     
/* 152 */     if (paramBufferedImage2 == null) {
/* 153 */       paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/* 154 */       colorModel2 = colorModel1;
/*     */     } else {
/*     */       
/* 157 */       if (k != paramBufferedImage2.getWidth()) {
/* 158 */         throw new IllegalArgumentException("Src width (" + k + ") not equal to dst width (" + paramBufferedImage2
/*     */ 
/*     */             
/* 161 */             .getWidth() + ")");
/*     */       }
/* 163 */       if (m != paramBufferedImage2.getHeight()) {
/* 164 */         throw new IllegalArgumentException("Src height (" + m + ") not equal to dst height (" + paramBufferedImage2
/*     */ 
/*     */             
/* 167 */             .getHeight() + ")");
/*     */       }
/*     */       
/* 170 */       colorModel2 = paramBufferedImage2.getColorModel();
/* 171 */       if (colorModel1.getColorSpace().getType() != colorModel2
/* 172 */         .getColorSpace().getType()) {
/*     */         
/* 174 */         bool = true;
/* 175 */         paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 180 */     BufferedImage bufferedImage = paramBufferedImage2;
/*     */     
/* 182 */     if (ImagingLib.filter(this, paramBufferedImage1, paramBufferedImage2) == null) {
/*     */       
/* 184 */       WritableRaster writableRaster1 = paramBufferedImage1.getRaster();
/* 185 */       WritableRaster writableRaster2 = paramBufferedImage2.getRaster();
/*     */       
/* 187 */       if (colorModel1.hasAlpha() && (
/* 188 */         i - 1 == j || j == 1)) {
/* 189 */         int n = writableRaster1.getMinX();
/* 190 */         int i1 = writableRaster1.getMinY();
/* 191 */         int[] arrayOfInt = new int[i - 1];
/* 192 */         for (byte b = 0; b < i - 1; b++) {
/* 193 */           arrayOfInt[b] = b;
/*     */         }
/*     */         
/* 196 */         writableRaster1 = writableRaster1.createWritableChild(n, i1, writableRaster1
/* 197 */             .getWidth(), writableRaster1
/* 198 */             .getHeight(), n, i1, arrayOfInt);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 203 */       if (colorModel2.hasAlpha()) {
/* 204 */         int n = writableRaster2.getNumBands();
/* 205 */         if (n - 1 == j || j == 1) {
/* 206 */           int i1 = writableRaster2.getMinX();
/* 207 */           int i2 = writableRaster2.getMinY();
/* 208 */           int[] arrayOfInt = new int[i - 1];
/* 209 */           for (byte b = 0; b < i - 1; b++) {
/* 210 */             arrayOfInt[b] = b;
/*     */           }
/*     */           
/* 213 */           writableRaster2 = writableRaster2.createWritableChild(i1, i2, writableRaster2
/* 214 */               .getWidth(), writableRaster2
/* 215 */               .getHeight(), i1, i2, arrayOfInt);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 221 */       filter(writableRaster1, writableRaster2);
/*     */     } 
/*     */     
/* 224 */     if (bool) {
/*     */       
/* 226 */       ColorConvertOp colorConvertOp = new ColorConvertOp(this.hints);
/* 227 */       colorConvertOp.filter(paramBufferedImage2, bufferedImage);
/*     */     } 
/*     */     
/* 230 */     return bufferedImage;
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
/*     */   public final WritableRaster filter(Raster paramRaster, WritableRaster paramWritableRaster) {
/* 254 */     int i = paramRaster.getNumBands();
/* 255 */     int j = paramWritableRaster.getNumBands();
/* 256 */     int k = paramRaster.getHeight();
/* 257 */     int m = paramRaster.getWidth();
/* 258 */     int[] arrayOfInt = new int[i];
/*     */ 
/*     */ 
/*     */     
/* 262 */     if (paramWritableRaster == null) {
/* 263 */       paramWritableRaster = createCompatibleDestRaster(paramRaster);
/*     */     }
/* 265 */     else if (k != paramWritableRaster.getHeight() || m != paramWritableRaster.getWidth()) {
/* 266 */       throw new IllegalArgumentException("Width or height of Rasters do not match");
/*     */     } 
/*     */ 
/*     */     
/* 270 */     j = paramWritableRaster.getNumBands();
/*     */     
/* 272 */     if (i != j) {
/* 273 */       throw new IllegalArgumentException("Number of channels in the src (" + i + ") does not match number of channels in the destination (" + j + ")");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     int n = this.ltable.getNumComponents();
/* 281 */     if (n != 1 && n != paramRaster.getNumBands()) {
/* 282 */       throw new IllegalArgumentException("Number of arrays in the  lookup table (" + n + " is not compatible with the  src Raster: " + paramRaster);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 290 */     if (ImagingLib.filter(this, paramRaster, paramWritableRaster) != null) {
/* 291 */       return paramWritableRaster;
/*     */     }
/*     */ 
/*     */     
/* 295 */     if (this.ltable instanceof ByteLookupTable) {
/* 296 */       byteFilter((ByteLookupTable)this.ltable, paramRaster, paramWritableRaster, m, k, i);
/*     */     
/*     */     }
/* 299 */     else if (this.ltable instanceof ShortLookupTable) {
/* 300 */       shortFilter((ShortLookupTable)this.ltable, paramRaster, paramWritableRaster, m, k, i);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 305 */       int i1 = paramRaster.getMinX();
/* 306 */       int i2 = paramRaster.getMinY();
/* 307 */       int i3 = paramWritableRaster.getMinX();
/* 308 */       int i4 = paramWritableRaster.getMinY();
/* 309 */       for (byte b = 0; b < k; b++, i2++, i4++) {
/* 310 */         int i5 = i1;
/* 311 */         int i6 = i3;
/* 312 */         for (byte b1 = 0; b1 < m; b1++, i5++, i6++) {
/*     */           
/* 314 */           paramRaster.getPixel(i5, i2, arrayOfInt);
/*     */ 
/*     */           
/* 317 */           this.ltable.lookupPixel(arrayOfInt, arrayOfInt);
/*     */ 
/*     */           
/* 320 */           paramWritableRaster.setPixel(i6, i4, arrayOfInt);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 325 */     return paramWritableRaster;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Rectangle2D getBounds2D(BufferedImage paramBufferedImage) {
/* 336 */     return getBounds2D(paramBufferedImage.getRaster());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Rectangle2D getBounds2D(Raster paramRaster) {
/* 347 */     return paramRaster.getBounds();
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
/*     */   public BufferedImage createCompatibleDestImage(BufferedImage paramBufferedImage, ColorModel paramColorModel) {
/*     */     BufferedImage bufferedImage;
/* 363 */     int i = paramBufferedImage.getWidth();
/* 364 */     int j = paramBufferedImage.getHeight();
/* 365 */     boolean bool = false;
/* 366 */     if (paramColorModel == null) {
/* 367 */       ColorModel colorModel = paramBufferedImage.getColorModel();
/* 368 */       WritableRaster writableRaster = paramBufferedImage.getRaster();
/* 369 */       if (colorModel instanceof ComponentColorModel) {
/* 370 */         DataBuffer dataBuffer = writableRaster.getDataBuffer();
/* 371 */         boolean bool1 = colorModel.hasAlpha();
/* 372 */         boolean bool2 = colorModel.isAlphaPremultiplied();
/* 373 */         int k = colorModel.getTransparency();
/* 374 */         int[] arrayOfInt = null;
/* 375 */         if (this.ltable instanceof ByteLookupTable) {
/* 376 */           if (dataBuffer.getDataType() == 1)
/*     */           {
/* 378 */             if (bool1) {
/* 379 */               arrayOfInt = new int[2];
/* 380 */               if (k == 2) {
/* 381 */                 arrayOfInt[1] = 1;
/*     */               } else {
/*     */                 
/* 384 */                 arrayOfInt[1] = 8;
/*     */               } 
/*     */             } else {
/*     */               
/* 388 */               arrayOfInt = new int[1];
/*     */             } 
/* 390 */             arrayOfInt[0] = 8;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 395 */           bool = true;
/* 396 */           if (this.ltable instanceof ShortLookupTable && dataBuffer.getDataType() == 0) {
/* 397 */             if (bool1) {
/* 398 */               arrayOfInt = new int[2];
/* 399 */               if (k == 2) {
/* 400 */                 arrayOfInt[1] = 1;
/*     */               } else {
/*     */                 
/* 403 */                 arrayOfInt[1] = 16;
/*     */               } 
/*     */             } else {
/*     */               
/* 407 */               arrayOfInt = new int[1];
/*     */             } 
/* 409 */             arrayOfInt[0] = 16;
/*     */           } 
/*     */         } 
/* 412 */         if (arrayOfInt != null) {
/* 413 */           colorModel = new ComponentColorModel(colorModel.getColorSpace(), arrayOfInt, bool1, bool2, k, bool);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 420 */       bufferedImage = new BufferedImage(colorModel, colorModel.createCompatibleWritableRaster(i, j), colorModel.isAlphaPremultiplied(), null);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 427 */       bufferedImage = new BufferedImage(paramColorModel, paramColorModel.createCompatibleWritableRaster(i, j), paramColorModel.isAlphaPremultiplied(), null);
/*     */     } 
/*     */ 
/*     */     
/* 431 */     return bufferedImage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WritableRaster createCompatibleDestRaster(Raster paramRaster) {
/* 441 */     return paramRaster.createCompatibleWritableRaster();
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
/*     */   public final Point2D getPoint2D(Point2D paramPoint2D1, Point2D paramPoint2D2) {
/* 458 */     if (paramPoint2D2 == null) {
/* 459 */       paramPoint2D2 = new Point2D.Float();
/*     */     }
/* 461 */     paramPoint2D2.setLocation(paramPoint2D1.getX(), paramPoint2D1.getY());
/*     */     
/* 463 */     return paramPoint2D2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final RenderingHints getRenderingHints() {
/* 472 */     return this.hints;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final void byteFilter(ByteLookupTable paramByteLookupTable, Raster paramRaster, WritableRaster paramWritableRaster, int paramInt1, int paramInt2, int paramInt3) {
/* 478 */     int[] arrayOfInt = null;
/*     */ 
/*     */     
/* 481 */     byte[][] arrayOfByte = paramByteLookupTable.getTable();
/* 482 */     int i = paramByteLookupTable.getOffset();
/*     */     
/* 484 */     byte b1 = 1;
/*     */ 
/*     */     
/* 487 */     if (arrayOfByte.length == 1) {
/* 488 */       b1 = 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 494 */     int j = (arrayOfByte[0]).length;
/*     */ 
/*     */     
/* 497 */     for (byte b2 = 0; b2 < paramInt2; b2++) {
/* 498 */       int k = 0;
/* 499 */       for (byte b = 0; b < paramInt3; b++, k += b1) {
/*     */         
/* 501 */         arrayOfInt = paramRaster.getSamples(0, b2, paramInt1, 1, b, arrayOfInt);
/*     */         
/* 503 */         for (byte b3 = 0; b3 < paramInt1; b3++) {
/* 504 */           int m = arrayOfInt[b3] - i;
/* 505 */           if (m < 0 || m > j) {
/* 506 */             throw new IllegalArgumentException("index (" + m + "(out of range:  srcPix[" + b3 + "]=" + arrayOfInt[b3] + " offset=" + i);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 514 */           arrayOfInt[b3] = arrayOfByte[k][m];
/*     */         } 
/*     */         
/* 517 */         paramWritableRaster.setSamples(0, b2, paramInt1, 1, b, arrayOfInt);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final void shortFilter(ShortLookupTable paramShortLookupTable, Raster paramRaster, WritableRaster paramWritableRaster, int paramInt1, int paramInt2, int paramInt3) {
/* 526 */     int[] arrayOfInt = null;
/*     */ 
/*     */     
/* 529 */     short[][] arrayOfShort = paramShortLookupTable.getTable();
/* 530 */     int i = paramShortLookupTable.getOffset();
/*     */     
/* 532 */     byte b1 = 1;
/*     */ 
/*     */     
/* 535 */     if (arrayOfShort.length == 1) {
/* 536 */       b1 = 0;
/*     */     }
/*     */     
/* 539 */     byte b2 = 0;
/* 540 */     byte b3 = 0;
/*     */     
/* 542 */     char c = '￿';
/*     */     
/* 544 */     for (b3 = 0; b3 < paramInt2; b3++) {
/* 545 */       int j = 0;
/* 546 */       for (byte b = 0; b < paramInt3; b++, j += b1) {
/*     */         
/* 548 */         arrayOfInt = paramRaster.getSamples(0, b3, paramInt1, 1, b, arrayOfInt);
/*     */         
/* 550 */         for (b2 = 0; b2 < paramInt1; b2++) {
/* 551 */           int k = arrayOfInt[b2] - i;
/* 552 */           if (k < 0 || k > c) {
/* 553 */             throw new IllegalArgumentException("index out of range " + k + " x is " + b2 + "srcPix[x]=" + arrayOfInt[b2] + " offset=" + i);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 560 */           arrayOfInt[b2] = arrayOfShort[j][k];
/*     */         } 
/*     */         
/* 563 */         paramWritableRaster.setSamples(0, b3, paramInt1, 1, b, arrayOfInt);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/LookupOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */