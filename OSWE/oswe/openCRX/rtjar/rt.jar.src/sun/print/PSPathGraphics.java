/*     */ package sun.print;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.Shape;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.TextLayout;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Area;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.PathIterator;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Printable;
/*     */ import java.awt.print.PrinterException;
/*     */ import java.awt.print.PrinterJob;
/*     */ import sun.awt.image.ByteComponentRaster;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PSPathGraphics
/*     */   extends PathGraphics
/*     */ {
/*     */   private static final int DEFAULT_USER_RES = 72;
/*     */   
/*     */   PSPathGraphics(Graphics2D paramGraphics2D, PrinterJob paramPrinterJob, Printable paramPrintable, PageFormat paramPageFormat, int paramInt, boolean paramBoolean) {
/*  71 */     super(paramGraphics2D, paramPrinterJob, paramPrintable, paramPageFormat, paramInt, paramBoolean);
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
/*     */   public Graphics create() {
/*  83 */     return new PSPathGraphics((Graphics2D)getDelegate().create(), 
/*  84 */         getPrinterJob(), 
/*  85 */         getPrintable(), 
/*  86 */         getPageFormat(), 
/*  87 */         getPageIndex(), 
/*  88 */         canDoRedraws());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fill(Shape paramShape, Color paramColor) {
/*  98 */     deviceFill(paramShape.getPathIterator(new AffineTransform()), paramColor);
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
/*     */   public void drawString(String paramString, int paramInt1, int paramInt2) {
/* 114 */     drawString(paramString, paramInt1, paramInt2);
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
/*     */   public void drawString(String paramString, float paramFloat1, float paramFloat2) {
/* 140 */     drawString(paramString, paramFloat1, paramFloat2, getFont(), getFontRenderContext(), 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canDrawStringToWidth() {
/* 145 */     return true;
/*     */   }
/*     */   
/*     */   protected int platformFontCount(Font paramFont, String paramString) {
/* 149 */     PSPrinterJob pSPrinterJob = (PSPrinterJob)getPrinterJob();
/* 150 */     return pSPrinterJob.platformFontCount(paramFont, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawString(String paramString, float paramFloat1, float paramFloat2, Font paramFont, FontRenderContext paramFontRenderContext, float paramFloat3) {
/* 155 */     if (paramString.length() == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     if (paramFont.hasLayoutAttributes() && !this.printingGlyphVector) {
/* 168 */       TextLayout textLayout = new TextLayout(paramString, paramFont, paramFontRenderContext);
/* 169 */       textLayout.draw(this, paramFloat1, paramFloat2);
/*     */       
/*     */       return;
/*     */     } 
/* 173 */     Font font = getFont();
/* 174 */     if (!font.equals(paramFont)) {
/* 175 */       setFont(paramFont);
/*     */     } else {
/* 177 */       font = null;
/*     */     } 
/*     */     
/* 180 */     boolean bool1 = false;
/*     */     
/* 182 */     float f1 = 0.0F, f2 = 0.0F;
/* 183 */     boolean bool2 = getFont().isTransformed();
/*     */     
/* 185 */     if (bool2) {
/* 186 */       AffineTransform affineTransform = getFont().getTransform();
/* 187 */       int i = affineTransform.getType();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 192 */       if (i == 1) {
/* 193 */         f1 = (float)affineTransform.getTranslateX();
/* 194 */         f2 = (float)affineTransform.getTranslateY();
/* 195 */         if (Math.abs(f1) < 1.0E-5D) f1 = 0.0F; 
/* 196 */         if (Math.abs(f2) < 1.0E-5D) f2 = 0.0F; 
/* 197 */         bool2 = false;
/*     */       } 
/*     */     } 
/*     */     
/* 201 */     boolean bool = !bool2 ? true : false;
/*     */     
/* 203 */     if (!PSPrinterJob.shapeTextProp && bool) {
/*     */       
/* 205 */       PSPrinterJob pSPrinterJob = (PSPrinterJob)getPrinterJob();
/* 206 */       if (pSPrinterJob.setFont(getFont())) {
/*     */ 
/*     */         
/*     */         try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 216 */           pSPrinterJob.setColor((Color)getPaint());
/* 217 */         } catch (ClassCastException classCastException) {
/* 218 */           if (font != null) {
/* 219 */             setFont(font);
/*     */           }
/* 221 */           throw new IllegalArgumentException("Expected a Color instance");
/*     */         } 
/*     */ 
/*     */         
/* 225 */         pSPrinterJob.setTransform(getTransform());
/* 226 */         pSPrinterJob.setClip(getClip());
/*     */         
/* 228 */         bool1 = pSPrinterJob.textOut(this, paramString, paramFloat1 + f1, paramFloat2 + f2, paramFont, paramFontRenderContext, paramFloat3);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     if (!bool1) {
/* 238 */       if (font != null) {
/* 239 */         setFont(font);
/* 240 */         font = null;
/*     */       } 
/* 242 */       super.drawString(paramString, paramFloat1, paramFloat2, paramFont, paramFontRenderContext, paramFloat3);
/*     */     } 
/*     */     
/* 245 */     if (font != null) {
/* 246 */       setFont(font);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean drawImageToPlatform(Image paramImage, AffineTransform paramAffineTransform, Color paramColor, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/* 285 */     BufferedImage bufferedImage = getBufferedImage(paramImage);
/* 286 */     if (bufferedImage == null) {
/* 287 */       return true;
/*     */     }
/*     */     
/* 290 */     PSPrinterJob pSPrinterJob = (PSPrinterJob)getPrinterJob();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 298 */     AffineTransform affineTransform = getTransform();
/* 299 */     if (paramAffineTransform == null) {
/* 300 */       paramAffineTransform = new AffineTransform();
/*     */     }
/* 302 */     affineTransform.concatenate(paramAffineTransform);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 320 */     double[] arrayOfDouble = new double[6];
/* 321 */     affineTransform.getMatrix(arrayOfDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 332 */     Point2D.Float float_1 = new Point2D.Float(1.0F, 0.0F);
/* 333 */     Point2D.Float float_2 = new Point2D.Float(0.0F, 1.0F);
/* 334 */     affineTransform.deltaTransform(float_1, float_1);
/* 335 */     affineTransform.deltaTransform(float_2, float_2);
/*     */     
/* 337 */     Point2D.Float float_3 = new Point2D.Float(0.0F, 0.0F);
/* 338 */     double d1 = float_1.distance(float_3);
/* 339 */     double d2 = float_2.distance(float_3);
/*     */     
/* 341 */     double d3 = pSPrinterJob.getXRes();
/* 342 */     double d4 = pSPrinterJob.getYRes();
/* 343 */     double d5 = d3 / 72.0D;
/* 344 */     double d6 = d4 / 72.0D;
/*     */ 
/*     */     
/* 347 */     int i = affineTransform.getType();
/* 348 */     boolean bool = ((i & 0x30) != 0) ? true : false;
/*     */ 
/*     */     
/* 351 */     if (bool) {
/* 352 */       if (d1 > d5) d1 = d5; 
/* 353 */       if (d2 > d6) d2 = d6;
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 359 */     if (d1 != 0.0D && d2 != 0.0D) {
/*     */ 
/*     */ 
/*     */       
/* 363 */       AffineTransform affineTransform1 = new AffineTransform(arrayOfDouble[0] / d1, arrayOfDouble[1] / d2, arrayOfDouble[2] / d1, arrayOfDouble[3] / d2, arrayOfDouble[4] / d1, arrayOfDouble[5] / d2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 391 */       Rectangle2D.Float float_ = new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */ 
/*     */ 
/*     */       
/* 395 */       Shape shape = affineTransform1.createTransformedShape(float_);
/* 396 */       Rectangle2D rectangle2D = shape.getBounds2D();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 402 */       rectangle2D.setRect(rectangle2D.getX(), rectangle2D.getY(), rectangle2D
/* 403 */           .getWidth() + 0.001D, rectangle2D
/* 404 */           .getHeight() + 0.001D);
/*     */       
/* 406 */       int j = (int)rectangle2D.getWidth();
/* 407 */       int k = (int)rectangle2D.getHeight();
/*     */       
/* 409 */       if (j > 0 && k > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 425 */         boolean bool1 = true;
/* 426 */         if (!paramBoolean && hasTransparentPixels(bufferedImage)) {
/* 427 */           bool1 = false;
/* 428 */           if (isBitmaskTransparency(bufferedImage)) {
/* 429 */             if (paramColor == null) {
/* 430 */               if (drawBitmaskImage(bufferedImage, paramAffineTransform, paramColor, paramInt1, paramInt2, paramInt3, paramInt4))
/*     */               {
/*     */ 
/*     */                 
/* 434 */                 return true;
/*     */               }
/* 436 */             } else if (paramColor.getTransparency() == 1) {
/*     */               
/* 438 */               bool1 = true;
/*     */             } 
/*     */           }
/* 441 */           if (!canDoRedraws()) {
/* 442 */             bool1 = true;
/*     */           
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 448 */           paramColor = null;
/*     */         } 
/*     */ 
/*     */         
/* 452 */         if ((paramInt1 + paramInt3 > bufferedImage.getWidth(null) || paramInt2 + paramInt4 > bufferedImage
/* 453 */           .getHeight(null)) && 
/* 454 */           canDoRedraws()) {
/* 455 */           bool1 = false;
/*     */         }
/* 457 */         if (!bool1) {
/*     */           
/* 459 */           affineTransform.getMatrix(arrayOfDouble);
/* 460 */           AffineTransform affineTransform3 = new AffineTransform(arrayOfDouble[0] / d5, arrayOfDouble[1] / d6, arrayOfDouble[2] / d5, arrayOfDouble[3] / d6, arrayOfDouble[4] / d5, arrayOfDouble[5] / d6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 469 */           Rectangle2D.Float float_5 = new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */ 
/*     */           
/* 472 */           Shape shape4 = affineTransform.createTransformedShape(float_5);
/*     */ 
/*     */           
/* 475 */           Rectangle2D rectangle2D1 = shape4.getBounds2D();
/*     */           
/* 477 */           rectangle2D1.setRect(rectangle2D1.getX(), rectangle2D1.getY(), rectangle2D1
/* 478 */               .getWidth() + 0.001D, rectangle2D1
/* 479 */               .getHeight() + 0.001D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 486 */           int m = (int)rectangle2D1.getWidth();
/* 487 */           int n = (int)rectangle2D1.getHeight();
/* 488 */           int i1 = m * n * 3;
/* 489 */           int i2 = 8388608;
/* 490 */           double d7 = (d3 < d4) ? d3 : d4;
/* 491 */           int i3 = (int)d7;
/* 492 */           double d8 = 1.0D;
/*     */           
/* 494 */           double d9 = m / j;
/* 495 */           double d10 = n / k;
/* 496 */           double d11 = (d9 > d10) ? d10 : d9;
/* 497 */           int i4 = (int)(i3 / d11);
/* 498 */           if (i4 < 72) i4 = 72;
/*     */           
/* 500 */           while (i1 > i2 && i3 > i4) {
/* 501 */             d8 *= 2.0D;
/* 502 */             i3 /= 2;
/* 503 */             i1 /= 4;
/*     */           } 
/* 505 */           if (i3 < i4) {
/* 506 */             d8 = d7 / i4;
/*     */           }
/*     */           
/* 509 */           rectangle2D1.setRect(rectangle2D1.getX() / d8, rectangle2D1
/* 510 */               .getY() / d8, rectangle2D1
/* 511 */               .getWidth() / d8, rectangle2D1
/* 512 */               .getHeight() / d8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 523 */           pSPrinterJob.saveState(getTransform(), getClip(), rectangle2D1, d8, d8);
/*     */           
/* 525 */           return true;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 539 */         BufferedImage bufferedImage1 = new BufferedImage((int)rectangle2D.getWidth(), (int)rectangle2D.getHeight(), 5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 545 */         Graphics2D graphics2D = bufferedImage1.createGraphics();
/* 546 */         graphics2D.clipRect(0, 0, bufferedImage1
/* 547 */             .getWidth(), bufferedImage1
/* 548 */             .getHeight());
/*     */         
/* 550 */         graphics2D.translate(-rectangle2D.getX(), 
/* 551 */             -rectangle2D.getY());
/* 552 */         graphics2D.transform(affineTransform1);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 557 */         if (paramColor == null) {
/* 558 */           paramColor = Color.white;
/*     */         }
/*     */ 
/*     */         
/* 562 */         graphics2D.drawImage(bufferedImage, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4, paramColor, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 577 */         Shape shape1 = getClip();
/*     */         
/* 579 */         Shape shape2 = getTransform().createTransformedShape(shape1);
/* 580 */         AffineTransform affineTransform2 = AffineTransform.getScaleInstance(d1, d2);
/*     */         
/* 582 */         Shape shape3 = affineTransform2.createTransformedShape(shape);
/* 583 */         Area area1 = new Area(shape3);
/* 584 */         Area area2 = new Area(shape2);
/* 585 */         area1.intersect(area2);
/* 586 */         pSPrinterJob.setClip(area1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 600 */         Rectangle2D.Float float_4 = new Rectangle2D.Float((float)(rectangle2D.getX() * d1), (float)(rectangle2D.getY() * d2), (float)(rectangle2D.getWidth() * d1), (float)(rectangle2D.getHeight() * d2));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 607 */         ByteComponentRaster byteComponentRaster = (ByteComponentRaster)bufferedImage1.getRaster();
/*     */         
/* 609 */         pSPrinterJob.drawImageBGR(byteComponentRaster.getDataStorage(), float_4.x, float_4.y, 
/*     */             
/* 611 */             (float)Math.rint(float_4.width + 0.5D), 
/* 612 */             (float)Math.rint(float_4.height + 0.5D), 0.0F, 0.0F, bufferedImage1
/*     */             
/* 614 */             .getWidth(), bufferedImage1.getHeight(), bufferedImage1
/* 615 */             .getWidth(), bufferedImage1.getHeight());
/*     */ 
/*     */         
/* 618 */         pSPrinterJob.setClip(
/* 619 */             getTransform().createTransformedShape(shape1));
/*     */ 
/*     */         
/* 622 */         graphics2D.dispose();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 628 */     return true;
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
/*     */   public void redrawRegion(Rectangle2D paramRectangle2D, double paramDouble1, double paramDouble2, Shape paramShape, AffineTransform paramAffineTransform) throws PrinterException {
/* 642 */     PSPrinterJob pSPrinterJob = (PSPrinterJob)getPrinterJob();
/* 643 */     Printable printable = getPrintable();
/* 644 */     PageFormat pageFormat = getPageFormat();
/* 645 */     int i = getPageIndex();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 652 */     BufferedImage bufferedImage = new BufferedImage((int)paramRectangle2D.getWidth(), (int)paramRectangle2D.getHeight(), 5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 661 */     Graphics2D graphics2D = bufferedImage.createGraphics();
/* 662 */     ProxyGraphics2D proxyGraphics2D = new ProxyGraphics2D(graphics2D, pSPrinterJob);
/* 663 */     proxyGraphics2D.setColor(Color.white);
/* 664 */     proxyGraphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
/* 665 */     proxyGraphics2D.clipRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
/*     */     
/* 667 */     proxyGraphics2D.translate(-paramRectangle2D.getX(), -paramRectangle2D.getY());
/*     */ 
/*     */ 
/*     */     
/* 671 */     float f1 = (float)(pSPrinterJob.getXRes() / paramDouble1);
/* 672 */     float f2 = (float)(pSPrinterJob.getYRes() / paramDouble2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 678 */     proxyGraphics2D.scale((f1 / 72.0F), (f2 / 72.0F));
/*     */     
/* 680 */     proxyGraphics2D.translate(
/* 681 */         -pSPrinterJob.getPhysicalPrintableX(pageFormat.getPaper()) / pSPrinterJob
/* 682 */         .getXRes() * 72.0D, 
/* 683 */         -pSPrinterJob.getPhysicalPrintableY(pageFormat.getPaper()) / pSPrinterJob
/* 684 */         .getYRes() * 72.0D);
/*     */     
/* 686 */     proxyGraphics2D.transform(new AffineTransform(getPageFormat().getMatrix()));
/*     */     
/* 688 */     proxyGraphics2D.setPaint(Color.black);
/*     */     
/* 690 */     printable.print(proxyGraphics2D, pageFormat, i);
/*     */     
/* 692 */     graphics2D.dispose();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 697 */     pSPrinterJob.setClip(paramAffineTransform.createTransformedShape(paramShape));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 712 */     Rectangle2D.Float float_ = new Rectangle2D.Float((float)(paramRectangle2D.getX() * paramDouble1), (float)(paramRectangle2D.getY() * paramDouble2), (float)(paramRectangle2D.getWidth() * paramDouble1), (float)(paramRectangle2D.getHeight() * paramDouble2));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 718 */     ByteComponentRaster byteComponentRaster = (ByteComponentRaster)bufferedImage.getRaster();
/*     */     
/* 720 */     pSPrinterJob.drawImageBGR(byteComponentRaster.getDataStorage(), float_.x, float_.y, float_.width, float_.height, 0.0F, 0.0F, bufferedImage
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 725 */         .getWidth(), bufferedImage.getHeight(), bufferedImage
/* 726 */         .getWidth(), bufferedImage.getHeight());
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
/*     */   protected void deviceFill(PathIterator paramPathIterator, Color paramColor) {
/* 739 */     PSPrinterJob pSPrinterJob = (PSPrinterJob)getPrinterJob();
/* 740 */     pSPrinterJob.deviceFill(paramPathIterator, paramColor, getTransform(), getClip());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deviceFrameRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor) {
/* 750 */     draw(new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deviceDrawLine(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor) {
/* 760 */     draw(new Line2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deviceFillRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor) {
/* 768 */     fill(new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */   
/*     */   protected void deviceClip(PathIterator paramPathIterator) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/PSPathGraphics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */