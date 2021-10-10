/*     */ package java.awt.image;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.AffineTransform;
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
/*     */ public class AffineTransformOp
/*     */   implements BufferedImageOp, RasterOp
/*     */ {
/*     */   private AffineTransform xform;
/*     */   RenderingHints hints;
/*     */   public static final int TYPE_NEAREST_NEIGHBOR = 1;
/*     */   public static final int TYPE_BILINEAR = 2;
/*     */   public static final int TYPE_BICUBIC = 3;
/*  85 */   int interpolationType = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AffineTransformOp(AffineTransform paramAffineTransform, RenderingHints paramRenderingHints) {
/* 108 */     validateTransform(paramAffineTransform);
/* 109 */     this.xform = (AffineTransform)paramAffineTransform.clone();
/* 110 */     this.hints = paramRenderingHints;
/*     */     
/* 112 */     if (paramRenderingHints != null) {
/* 113 */       Object object = paramRenderingHints.get(RenderingHints.KEY_INTERPOLATION);
/* 114 */       if (object == null) {
/* 115 */         object = paramRenderingHints.get(RenderingHints.KEY_RENDERING);
/* 116 */         if (object == RenderingHints.VALUE_RENDER_SPEED) {
/* 117 */           this.interpolationType = 1;
/*     */         }
/* 119 */         else if (object == RenderingHints.VALUE_RENDER_QUALITY) {
/* 120 */           this.interpolationType = 2;
/*     */         }
/*     */       
/* 123 */       } else if (object == RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR) {
/* 124 */         this.interpolationType = 1;
/*     */       }
/* 126 */       else if (object == RenderingHints.VALUE_INTERPOLATION_BILINEAR) {
/* 127 */         this.interpolationType = 2;
/*     */       }
/* 129 */       else if (object == RenderingHints.VALUE_INTERPOLATION_BICUBIC) {
/* 130 */         this.interpolationType = 3;
/*     */       } 
/*     */     } else {
/*     */       
/* 134 */       this.interpolationType = 1;
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
/*     */   public AffineTransformOp(AffineTransform paramAffineTransform, int paramInt) {
/* 151 */     validateTransform(paramAffineTransform);
/* 152 */     this.xform = (AffineTransform)paramAffineTransform.clone();
/* 153 */     switch (paramInt) {
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */         break;
/*     */       default:
/* 159 */         throw new IllegalArgumentException("Unknown interpolation type: " + paramInt);
/*     */     } 
/*     */     
/* 162 */     this.interpolationType = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getInterpolationType() {
/* 173 */     return this.interpolationType;
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
/*     */ 
/*     */ 
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
/* 213 */     if (paramBufferedImage1 == null) {
/* 214 */       throw new NullPointerException("src image is null");
/*     */     }
/* 216 */     if (paramBufferedImage1 == paramBufferedImage2) {
/* 217 */       throw new IllegalArgumentException("src image cannot be the same as the dst image");
/*     */     }
/*     */ 
/*     */     
/* 221 */     boolean bool = false;
/* 222 */     ColorModel colorModel = paramBufferedImage1.getColorModel();
/*     */     
/* 224 */     BufferedImage bufferedImage = paramBufferedImage2;
/*     */     
/* 226 */     if (paramBufferedImage2 == null) {
/* 227 */       paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/* 228 */       ColorModel colorModel1 = colorModel;
/* 229 */       bufferedImage = paramBufferedImage2;
/*     */     } else {
/*     */       
/* 232 */       ColorModel colorModel1 = paramBufferedImage2.getColorModel();
/* 233 */       if (colorModel.getColorSpace().getType() != colorModel1
/* 234 */         .getColorSpace().getType()) {
/*     */         
/* 236 */         int i = this.xform.getType();
/* 237 */         boolean bool1 = ((i & (0x18 | 0x20)) != 0) ? true : false;
/*     */ 
/*     */ 
/*     */         
/* 241 */         if (!bool1 && i != 1 && i != 0) {
/*     */           
/* 243 */           double[] arrayOfDouble = new double[4];
/* 244 */           this.xform.getMatrix(arrayOfDouble);
/*     */ 
/*     */           
/* 247 */           bool1 = (arrayOfDouble[0] != (int)arrayOfDouble[0] || arrayOfDouble[3] != (int)arrayOfDouble[3]) ? true : false;
/*     */         } 
/*     */         
/* 250 */         if (bool1 && colorModel
/* 251 */           .getTransparency() == 1) {
/*     */ 
/*     */           
/* 254 */           ColorConvertOp colorConvertOp = new ColorConvertOp(this.hints);
/* 255 */           BufferedImage bufferedImage1 = null;
/* 256 */           int j = paramBufferedImage1.getWidth();
/* 257 */           int k = paramBufferedImage1.getHeight();
/* 258 */           if (colorModel1.getTransparency() == 1) {
/* 259 */             bufferedImage1 = new BufferedImage(j, k, 2);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 264 */             WritableRaster writableRaster = colorModel1.createCompatibleWritableRaster(j, k);
/*     */             
/* 266 */             bufferedImage1 = new BufferedImage(colorModel1, writableRaster, colorModel1.isAlphaPremultiplied(), null);
/*     */           } 
/*     */           
/* 269 */           paramBufferedImage1 = colorConvertOp.filter(paramBufferedImage1, bufferedImage1);
/*     */         } else {
/*     */           
/* 272 */           bool = true;
/* 273 */           paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 279 */     if (this.interpolationType != 1 && paramBufferedImage2
/* 280 */       .getColorModel() instanceof IndexColorModel) {
/* 281 */       paramBufferedImage2 = new BufferedImage(paramBufferedImage2.getWidth(), paramBufferedImage2.getHeight(), 2);
/*     */     }
/*     */     
/* 284 */     if (ImagingLib.filter(this, paramBufferedImage1, paramBufferedImage2) == null) {
/* 285 */       throw new ImagingOpException("Unable to transform src image");
/*     */     }
/*     */     
/* 288 */     if (bool) {
/* 289 */       ColorConvertOp colorConvertOp = new ColorConvertOp(this.hints);
/* 290 */       colorConvertOp.filter(paramBufferedImage2, bufferedImage);
/*     */     }
/* 292 */     else if (bufferedImage != paramBufferedImage2) {
/* 293 */       Graphics2D graphics2D = bufferedImage.createGraphics();
/*     */       try {
/* 295 */         graphics2D.setComposite(AlphaComposite.Src);
/* 296 */         graphics2D.drawImage(paramBufferedImage2, 0, 0, (ImageObserver)null);
/*     */       } finally {
/* 298 */         graphics2D.dispose();
/*     */       } 
/*     */     } 
/*     */     
/* 302 */     return bufferedImage;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final WritableRaster filter(Raster paramRaster, WritableRaster paramWritableRaster) {
/* 339 */     if (paramRaster == null) {
/* 340 */       throw new NullPointerException("src image is null");
/*     */     }
/* 342 */     if (paramWritableRaster == null) {
/* 343 */       paramWritableRaster = createCompatibleDestRaster(paramRaster);
/*     */     }
/* 345 */     if (paramRaster == paramWritableRaster) {
/* 346 */       throw new IllegalArgumentException("src image cannot be the same as the dst image");
/*     */     }
/*     */     
/* 349 */     if (paramRaster.getNumBands() != paramWritableRaster.getNumBands()) {
/* 350 */       throw new IllegalArgumentException("Number of src bands (" + paramRaster
/* 351 */           .getNumBands() + ") does not match number of  dst bands (" + paramWritableRaster
/*     */ 
/*     */           
/* 354 */           .getNumBands() + ")");
/*     */     }
/*     */     
/* 357 */     if (ImagingLib.filter(this, paramRaster, paramWritableRaster) == null) {
/* 358 */       throw new ImagingOpException("Unable to transform src image");
/*     */     }
/* 360 */     return paramWritableRaster;
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
/*     */   public final Rectangle2D getBounds2D(BufferedImage paramBufferedImage) {
/* 375 */     return getBounds2D(paramBufferedImage.getRaster());
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
/*     */   public final Rectangle2D getBounds2D(Raster paramRaster) {
/* 390 */     int i = paramRaster.getWidth();
/* 391 */     int j = paramRaster.getHeight();
/*     */ 
/*     */     
/* 394 */     float[] arrayOfFloat = { 0.0F, 0.0F, i, 0.0F, i, j, 0.0F, j };
/* 395 */     this.xform.transform(arrayOfFloat, 0, arrayOfFloat, 0, 4);
/*     */ 
/*     */     
/* 398 */     float f1 = arrayOfFloat[0];
/* 399 */     float f2 = arrayOfFloat[1];
/* 400 */     float f3 = arrayOfFloat[0];
/* 401 */     float f4 = arrayOfFloat[1];
/* 402 */     for (byte b = 2; b < 8; b += 2) {
/* 403 */       if (arrayOfFloat[b] > f1) {
/* 404 */         f1 = arrayOfFloat[b];
/*     */       }
/* 406 */       else if (arrayOfFloat[b] < f3) {
/* 407 */         f3 = arrayOfFloat[b];
/*     */       } 
/* 409 */       if (arrayOfFloat[b + 1] > f2) {
/* 410 */         f2 = arrayOfFloat[b + 1];
/*     */       }
/* 412 */       else if (arrayOfFloat[b + 1] < f4) {
/* 413 */         f4 = arrayOfFloat[b + 1];
/*     */       } 
/*     */     } 
/*     */     
/* 417 */     return new Rectangle2D.Float(f3, f4, f1 - f3, f2 - f4);
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
/*     */   public BufferedImage createCompatibleDestImage(BufferedImage paramBufferedImage, ColorModel paramColorModel) {
/*     */     BufferedImage bufferedImage;
/* 439 */     Rectangle rectangle = getBounds2D(paramBufferedImage).getBounds();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 445 */     int i = rectangle.x + rectangle.width;
/* 446 */     int j = rectangle.y + rectangle.height;
/* 447 */     if (i <= 0) {
/* 448 */       throw new RasterFormatException("Transformed width (" + i + ") is less than or equal to 0.");
/*     */     }
/*     */     
/* 451 */     if (j <= 0) {
/* 452 */       throw new RasterFormatException("Transformed height (" + j + ") is less than or equal to 0.");
/*     */     }
/*     */ 
/*     */     
/* 456 */     if (paramColorModel == null) {
/* 457 */       ColorModel colorModel = paramBufferedImage.getColorModel();
/* 458 */       if (this.interpolationType != 1 && (colorModel instanceof IndexColorModel || colorModel
/*     */         
/* 460 */         .getTransparency() == 1))
/*     */       {
/* 462 */         bufferedImage = new BufferedImage(i, j, 2);
/*     */       
/*     */       }
/*     */       else
/*     */       {
/*     */         
/* 468 */         bufferedImage = new BufferedImage(colorModel, paramBufferedImage.getRaster().createCompatibleWritableRaster(i, j), colorModel.isAlphaPremultiplied(), null);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 474 */       bufferedImage = new BufferedImage(paramColorModel, paramColorModel.createCompatibleWritableRaster(i, j), paramColorModel.isAlphaPremultiplied(), null);
/*     */     } 
/*     */     
/* 477 */     return bufferedImage;
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
/*     */   public WritableRaster createCompatibleDestRaster(Raster paramRaster) {
/* 490 */     Rectangle2D rectangle2D = getBounds2D(paramRaster);
/*     */     
/* 492 */     return paramRaster.createCompatibleWritableRaster((int)rectangle2D.getX(), 
/* 493 */         (int)rectangle2D.getY(), 
/* 494 */         (int)rectangle2D.getWidth(), 
/* 495 */         (int)rectangle2D.getHeight());
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
/*     */   public final Point2D getPoint2D(Point2D paramPoint2D1, Point2D paramPoint2D2) {
/* 511 */     return this.xform.transform(paramPoint2D1, paramPoint2D2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final AffineTransform getTransform() {
/* 520 */     return (AffineTransform)this.xform.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final RenderingHints getRenderingHints() {
/* 529 */     if (this.hints == null) {
/*     */       Object object;
/* 531 */       switch (this.interpolationType) {
/*     */         case 1:
/* 533 */           object = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
/*     */           break;
/*     */         case 2:
/* 536 */           object = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
/*     */           break;
/*     */         case 3:
/* 539 */           object = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
/*     */           break;
/*     */         
/*     */         default:
/* 543 */           throw new InternalError("Unknown interpolation type " + this.interpolationType);
/*     */       } 
/*     */ 
/*     */       
/* 547 */       this.hints = new RenderingHints(RenderingHints.KEY_INTERPOLATION, object);
/*     */     } 
/*     */     
/* 550 */     return this.hints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void validateTransform(AffineTransform paramAffineTransform) {
/* 557 */     if (Math.abs(paramAffineTransform.getDeterminant()) <= Double.MIN_VALUE)
/* 558 */       throw new ImagingOpException("Unable to invert transform " + paramAffineTransform); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/AffineTransformOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */