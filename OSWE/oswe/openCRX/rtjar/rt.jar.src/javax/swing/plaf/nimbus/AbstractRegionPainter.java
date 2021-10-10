/*     */ package javax.swing.plaf.nimbus;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LinearGradientPaint;
/*     */ import java.awt.RadialGradientPaint;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.VolatileImage;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.Painter;
/*     */ import javax.swing.UIManager;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractRegionPainter
/*     */   implements Painter<JComponent>
/*     */ {
/*     */   private PaintContext ctx;
/*     */   private float f;
/*     */   private float leftWidth;
/*     */   private float topHeight;
/*     */   private float centerWidth;
/*     */   private float centerHeight;
/*     */   private float rightWidth;
/*     */   private float bottomHeight;
/*     */   private float leftScale;
/*     */   private float topScale;
/*     */   private float centerHScale;
/*     */   private float centerVScale;
/*     */   private float rightScale;
/*     */   private float bottomScale;
/*     */   
/*     */   public final void paint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2) {
/* 146 */     if (paramInt1 <= 0 || paramInt2 <= 0)
/*     */       return; 
/* 148 */     Object[] arrayOfObject = getExtendedCacheKeys(paramJComponent);
/* 149 */     this.ctx = getPaintContext();
/* 150 */     PaintContext.CacheMode cacheMode = (this.ctx == null) ? PaintContext.CacheMode.NO_CACHING : this.ctx.cacheMode;
/* 151 */     if (cacheMode == PaintContext.CacheMode.NO_CACHING || 
/* 152 */       !ImageCache.getInstance().isImageCachable(paramInt1, paramInt2) || paramGraphics2D instanceof java.awt.print.PrinterGraphics) {
/*     */ 
/*     */       
/* 155 */       paint0(paramGraphics2D, paramJComponent, paramInt1, paramInt2, arrayOfObject);
/* 156 */     } else if (cacheMode == PaintContext.CacheMode.FIXED_SIZES) {
/* 157 */       paintWithFixedSizeCaching(paramGraphics2D, paramJComponent, paramInt1, paramInt2, arrayOfObject);
/*     */     } else {
/*     */       
/* 160 */       paintWith9SquareCaching(paramGraphics2D, this.ctx, paramJComponent, paramInt1, paramInt2, arrayOfObject);
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
/*     */   protected Object[] getExtendedCacheKeys(JComponent paramJComponent) {
/* 173 */     return null;
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
/*     */   protected void configureGraphics(Graphics2D paramGraphics2D) {
/* 200 */     paramGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
/*     */   protected final float decodeX(float paramFloat) {
/* 235 */     if (paramFloat >= 0.0F && paramFloat <= 1.0F)
/* 236 */       return paramFloat * this.leftWidth; 
/* 237 */     if (paramFloat > 1.0F && paramFloat < 2.0F)
/* 238 */       return (paramFloat - 1.0F) * this.centerWidth + this.leftWidth; 
/* 239 */     if (paramFloat >= 2.0F && paramFloat <= 3.0F) {
/* 240 */       return (paramFloat - 2.0F) * this.rightWidth + this.leftWidth + this.centerWidth;
/*     */     }
/* 242 */     throw new IllegalArgumentException("Invalid x");
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
/*     */   protected final float decodeY(float paramFloat) {
/* 256 */     if (paramFloat >= 0.0F && paramFloat <= 1.0F)
/* 257 */       return paramFloat * this.topHeight; 
/* 258 */     if (paramFloat > 1.0F && paramFloat < 2.0F)
/* 259 */       return (paramFloat - 1.0F) * this.centerHeight + this.topHeight; 
/* 260 */     if (paramFloat >= 2.0F && paramFloat <= 3.0F) {
/* 261 */       return (paramFloat - 2.0F) * this.bottomHeight + this.topHeight + this.centerHeight;
/*     */     }
/* 263 */     throw new IllegalArgumentException("Invalid y");
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
/*     */   protected final float decodeAnchorX(float paramFloat1, float paramFloat2) {
/* 279 */     if (paramFloat1 >= 0.0F && paramFloat1 <= 1.0F)
/* 280 */       return decodeX(paramFloat1) + paramFloat2 * this.leftScale; 
/* 281 */     if (paramFloat1 > 1.0F && paramFloat1 < 2.0F)
/* 282 */       return decodeX(paramFloat1) + paramFloat2 * this.centerHScale; 
/* 283 */     if (paramFloat1 >= 2.0F && paramFloat1 <= 3.0F) {
/* 284 */       return decodeX(paramFloat1) + paramFloat2 * this.rightScale;
/*     */     }
/* 286 */     throw new IllegalArgumentException("Invalid x");
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
/*     */   protected final float decodeAnchorY(float paramFloat1, float paramFloat2) {
/* 302 */     if (paramFloat1 >= 0.0F && paramFloat1 <= 1.0F)
/* 303 */       return decodeY(paramFloat1) + paramFloat2 * this.topScale; 
/* 304 */     if (paramFloat1 > 1.0F && paramFloat1 < 2.0F)
/* 305 */       return decodeY(paramFloat1) + paramFloat2 * this.centerVScale; 
/* 306 */     if (paramFloat1 >= 2.0F && paramFloat1 <= 3.0F) {
/* 307 */       return decodeY(paramFloat1) + paramFloat2 * this.bottomScale;
/*     */     }
/* 309 */     throw new IllegalArgumentException("Invalid y");
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
/*     */   protected final Color decodeColor(String paramString, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt) {
/* 328 */     if (UIManager.getLookAndFeel() instanceof NimbusLookAndFeel) {
/* 329 */       NimbusLookAndFeel nimbusLookAndFeel = (NimbusLookAndFeel)UIManager.getLookAndFeel();
/* 330 */       return nimbusLookAndFeel.getDerivedColor(paramString, paramFloat1, paramFloat2, paramFloat3, paramInt, true);
/*     */     } 
/*     */ 
/*     */     
/* 334 */     return Color.getHSBColor(paramFloat1, paramFloat2, paramFloat3);
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
/*     */   protected final Color decodeColor(Color paramColor1, Color paramColor2, float paramFloat) {
/* 350 */     return new Color(NimbusLookAndFeel.deriveARGB(paramColor1, paramColor2, paramFloat));
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
/*     */   protected final LinearGradientPaint decodeGradient(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOffloat, Color[] paramArrayOfColor) {
/* 378 */     if (paramFloat1 == paramFloat3 && paramFloat2 == paramFloat4) {
/* 379 */       paramFloat4 += 1.0E-5F;
/*     */     }
/* 381 */     return new LinearGradientPaint(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramArrayOffloat, paramArrayOfColor);
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
/*     */   protected final RadialGradientPaint decodeRadialGradient(float paramFloat1, float paramFloat2, float paramFloat3, float[] paramArrayOffloat, Color[] paramArrayOfColor) {
/* 408 */     if (paramFloat3 == 0.0F) {
/* 409 */       paramFloat3 = 1.0E-5F;
/*     */     }
/* 411 */     return new RadialGradientPaint(paramFloat1, paramFloat2, paramFloat3, paramArrayOffloat, paramArrayOfColor);
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
/*     */   protected final Color getComponentColor(JComponent paramJComponent, String paramString, Color paramColor, float paramFloat1, float paramFloat2, int paramInt) {
/* 431 */     Color color = null;
/* 432 */     if (paramJComponent != null)
/*     */     {
/* 434 */       if ("background".equals(paramString)) {
/* 435 */         color = paramJComponent.getBackground();
/* 436 */       } else if ("foreground".equals(paramString)) {
/* 437 */         color = paramJComponent.getForeground();
/* 438 */       } else if (paramJComponent instanceof JList && "selectionForeground".equals(paramString)) {
/* 439 */         color = ((JList)paramJComponent).getSelectionForeground();
/* 440 */       } else if (paramJComponent instanceof JList && "selectionBackground".equals(paramString)) {
/* 441 */         color = ((JList)paramJComponent).getSelectionBackground();
/* 442 */       } else if (paramJComponent instanceof JTable && "selectionForeground".equals(paramString)) {
/* 443 */         color = ((JTable)paramJComponent).getSelectionForeground();
/* 444 */       } else if (paramJComponent instanceof JTable && "selectionBackground".equals(paramString)) {
/* 445 */         color = ((JTable)paramJComponent).getSelectionBackground();
/*     */       } else {
/* 447 */         String str = "get" + Character.toUpperCase(paramString.charAt(0)) + paramString.substring(1);
/*     */         try {
/* 449 */           Method method = MethodUtil.getMethod(paramJComponent.getClass(), str, null);
/* 450 */           color = (Color)MethodUtil.invoke(method, paramJComponent, null);
/* 451 */         } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 457 */         if (color == null) {
/* 458 */           Object object = paramJComponent.getClientProperty(paramString);
/* 459 */           if (object instanceof Color) {
/* 460 */             color = (Color)object;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 471 */     if (color == null || color instanceof javax.swing.plaf.UIResource)
/* 472 */       return paramColor; 
/* 473 */     if (paramFloat1 != 0.0F || paramFloat2 != 0.0F || paramInt != 0) {
/* 474 */       float[] arrayOfFloat = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
/* 475 */       arrayOfFloat[1] = clamp(arrayOfFloat[1] + paramFloat1);
/* 476 */       arrayOfFloat[2] = clamp(arrayOfFloat[2] + paramFloat2);
/* 477 */       int i = clamp(color.getAlpha() + paramInt);
/* 478 */       return new Color(Color.HSBtoRGB(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2]) & 0xFFFFFF | i << 24);
/*     */     } 
/* 480 */     return color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class PaintContext
/*     */   {
/*     */     protected enum CacheMode
/*     */     {
/* 492 */       NO_CACHING, FIXED_SIZES, NINE_SQUARE_SCALE;
/*     */     }
/*     */     
/* 495 */     private static Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
/*     */     
/*     */     private Insets stretchingInsets;
/*     */     
/*     */     private Dimension canvasSize;
/*     */     
/*     */     private boolean inverted;
/*     */     
/*     */     private CacheMode cacheMode;
/*     */     
/*     */     private double maxHorizontalScaleFactor;
/*     */     
/*     */     private double maxVerticalScaleFactor;
/*     */     
/*     */     private float a;
/*     */     
/*     */     private float b;
/*     */     
/*     */     private float c;
/*     */     
/*     */     private float d;
/*     */     
/*     */     private float aPercent;
/*     */     
/*     */     private float bPercent;
/*     */     private float cPercent;
/*     */     private float dPercent;
/*     */     
/*     */     public PaintContext(Insets param1Insets, Dimension param1Dimension, boolean param1Boolean) {
/* 524 */       this(param1Insets, param1Dimension, param1Boolean, null, 1.0D, 1.0D);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PaintContext(Insets param1Insets, Dimension param1Dimension, boolean param1Boolean, CacheMode param1CacheMode, double param1Double1, double param1Double2) {
/* 547 */       if (param1Double1 < 1.0D || param1Double1 < 1.0D) {
/* 548 */         throw new IllegalArgumentException("Both maxH and maxV must be >= 1");
/*     */       }
/*     */       
/* 551 */       this.stretchingInsets = (param1Insets == null) ? EMPTY_INSETS : param1Insets;
/* 552 */       this.canvasSize = param1Dimension;
/* 553 */       this.inverted = param1Boolean;
/* 554 */       this.cacheMode = (param1CacheMode == null) ? CacheMode.NO_CACHING : param1CacheMode;
/* 555 */       this.maxHorizontalScaleFactor = param1Double1;
/* 556 */       this.maxVerticalScaleFactor = param1Double2;
/*     */       
/* 558 */       if (param1Dimension != null) {
/* 559 */         this.a = this.stretchingInsets.left;
/* 560 */         this.b = (param1Dimension.width - this.stretchingInsets.right);
/* 561 */         this.c = this.stretchingInsets.top;
/* 562 */         this.d = (param1Dimension.height - this.stretchingInsets.bottom);
/* 563 */         this.canvasSize = param1Dimension;
/* 564 */         this.inverted = param1Boolean;
/* 565 */         if (param1Boolean) {
/* 566 */           float f = param1Dimension.width - this.b - this.a;
/* 567 */           this.aPercent = (f > 0.0F) ? (this.a / f) : 0.0F;
/* 568 */           this.bPercent = (f > 0.0F) ? (this.b / f) : 0.0F;
/* 569 */           f = param1Dimension.height - this.d - this.c;
/* 570 */           this.cPercent = (f > 0.0F) ? (this.c / f) : 0.0F;
/* 571 */           this.dPercent = (f > 0.0F) ? (this.d / f) : 0.0F;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected enum CacheMode
/*     */   {
/*     */     NO_CACHING, FIXED_SIZES, NINE_SQUARE_SCALE;
/*     */   }
/*     */   
/*     */   private void prepare(float paramFloat1, float paramFloat2) {
/* 583 */     if (this.ctx == null || this.ctx.canvasSize == null) {
/* 584 */       this.f = 1.0F;
/* 585 */       this.leftWidth = this.centerWidth = this.rightWidth = 0.0F;
/* 586 */       this.topHeight = this.centerHeight = this.bottomHeight = 0.0F;
/* 587 */       this.leftScale = this.centerHScale = this.rightScale = 0.0F;
/* 588 */       this.topScale = this.centerVScale = this.bottomScale = 0.0F;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 593 */     Number number = (Number)UIManager.get("scale");
/* 594 */     this.f = (number == null) ? 1.0F : number.floatValue();
/*     */     
/* 596 */     if (this.ctx.inverted) {
/* 597 */       this.centerWidth = (this.ctx.b - this.ctx.a) * this.f;
/* 598 */       float f = paramFloat1 - this.centerWidth;
/* 599 */       this.leftWidth = f * this.ctx.aPercent;
/* 600 */       this.rightWidth = f * this.ctx.bPercent;
/* 601 */       this.centerHeight = (this.ctx.d - this.ctx.c) * this.f;
/* 602 */       f = paramFloat2 - this.centerHeight;
/* 603 */       this.topHeight = f * this.ctx.cPercent;
/* 604 */       this.bottomHeight = f * this.ctx.dPercent;
/*     */     } else {
/* 606 */       this.leftWidth = this.ctx.a * this.f;
/* 607 */       this.rightWidth = (float)(this.ctx.canvasSize.getWidth() - this.ctx.b) * this.f;
/* 608 */       this.centerWidth = paramFloat1 - this.leftWidth - this.rightWidth;
/* 609 */       this.topHeight = this.ctx.c * this.f;
/* 610 */       this.bottomHeight = (float)(this.ctx.canvasSize.getHeight() - this.ctx.d) * this.f;
/* 611 */       this.centerHeight = paramFloat2 - this.topHeight - this.bottomHeight;
/*     */     } 
/*     */     
/* 614 */     this.leftScale = (this.ctx.a == 0.0F) ? 0.0F : (this.leftWidth / this.ctx.a);
/* 615 */     this.centerHScale = (this.ctx.b - this.ctx.a == 0.0F) ? 0.0F : (this.centerWidth / (this.ctx.b - this.ctx.a));
/* 616 */     this.rightScale = (this.ctx.canvasSize.width - this.ctx.b == 0.0F) ? 0.0F : (this.rightWidth / (this.ctx.canvasSize.width - this.ctx.b));
/* 617 */     this.topScale = (this.ctx.c == 0.0F) ? 0.0F : (this.topHeight / this.ctx.c);
/* 618 */     this.centerVScale = (this.ctx.d - this.ctx.c == 0.0F) ? 0.0F : (this.centerHeight / (this.ctx.d - this.ctx.c));
/* 619 */     this.bottomScale = (this.ctx.canvasSize.height - this.ctx.d == 0.0F) ? 0.0F : (this.bottomHeight / (this.ctx.canvasSize.height - this.ctx.d));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintWith9SquareCaching(Graphics2D paramGraphics2D, PaintContext paramPaintContext, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 626 */     Dimension dimension = paramPaintContext.canvasSize;
/* 627 */     Insets insets = paramPaintContext.stretchingInsets;
/* 628 */     if (paramInt1 <= dimension.width * paramPaintContext.maxHorizontalScaleFactor && paramInt2 <= dimension.height * paramPaintContext.maxVerticalScaleFactor) {
/*     */       
/* 630 */       VolatileImage volatileImage = getImage(paramGraphics2D.getDeviceConfiguration(), paramJComponent, dimension.width, dimension.height, paramArrayOfObject);
/* 631 */       if (volatileImage != null) {
/*     */         Insets insets1;
/*     */ 
/*     */         
/* 635 */         if (paramPaintContext.inverted) {
/* 636 */           int i = (paramInt1 - dimension.width - insets.left + insets.right) / 2;
/* 637 */           int j = (paramInt2 - dimension.height - insets.top + insets.bottom) / 2;
/* 638 */           insets1 = new Insets(j, i, j, i);
/*     */         } else {
/* 640 */           insets1 = insets;
/*     */         } 
/*     */         
/* 643 */         Object object = paramGraphics2D.getRenderingHint(RenderingHints.KEY_INTERPOLATION);
/* 644 */         paramGraphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 645 */         ImageScalingHelper.paint(paramGraphics2D, 0, 0, paramInt1, paramInt2, volatileImage, insets, insets1, ImageScalingHelper.PaintType.PAINT9_STRETCH, 512);
/*     */         
/* 647 */         paramGraphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, (object != null) ? object : RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
/*     */       }
/*     */       else {
/*     */         
/* 651 */         paint0(paramGraphics2D, paramJComponent, paramInt1, paramInt2, paramArrayOfObject);
/*     */       } 
/*     */     } else {
/*     */       
/* 655 */       paint0(paramGraphics2D, paramJComponent, paramInt1, paramInt2, paramArrayOfObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintWithFixedSizeCaching(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 661 */     VolatileImage volatileImage = getImage(paramGraphics2D.getDeviceConfiguration(), paramJComponent, paramInt1, paramInt2, paramArrayOfObject);
/* 662 */     if (volatileImage != null) {
/*     */       
/* 664 */       paramGraphics2D.drawImage(volatileImage, 0, 0, (ImageObserver)null);
/*     */     } else {
/*     */       
/* 667 */       paint0(paramGraphics2D, paramJComponent, paramInt1, paramInt2, paramArrayOfObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private VolatileImage getImage(GraphicsConfiguration paramGraphicsConfiguration, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 674 */     ImageCache imageCache = ImageCache.getInstance();
/*     */     
/* 676 */     VolatileImage volatileImage = (VolatileImage)imageCache.getImage(paramGraphicsConfiguration, paramInt1, paramInt2, new Object[] { this, paramArrayOfObject });
/*     */     
/* 678 */     byte b = 0;
/*     */     
/*     */     do {
/* 681 */       int i = 2;
/* 682 */       if (volatileImage != null) {
/* 683 */         i = volatileImage.validate(paramGraphicsConfiguration);
/*     */       }
/*     */ 
/*     */       
/* 687 */       if (i != 2 && i != 1) {
/*     */         continue;
/*     */       }
/* 690 */       if (volatileImage == null || volatileImage.getWidth() != paramInt1 || volatileImage.getHeight() != paramInt2 || i == 2) {
/*     */ 
/*     */         
/* 693 */         if (volatileImage != null) {
/* 694 */           volatileImage.flush();
/* 695 */           volatileImage = null;
/*     */         } 
/*     */         
/* 698 */         volatileImage = paramGraphicsConfiguration.createCompatibleVolatileImage(paramInt1, paramInt2, 3);
/*     */ 
/*     */         
/* 701 */         imageCache.setImage(volatileImage, paramGraphicsConfiguration, paramInt1, paramInt2, new Object[] { this, paramArrayOfObject });
/*     */       } 
/*     */       
/* 704 */       Graphics2D graphics2D = volatileImage.createGraphics();
/*     */       
/* 706 */       graphics2D.setComposite(AlphaComposite.Clear);
/* 707 */       graphics2D.fillRect(0, 0, paramInt1, paramInt2);
/* 708 */       graphics2D.setComposite(AlphaComposite.SrcOver);
/* 709 */       configureGraphics(graphics2D);
/*     */       
/* 711 */       paint0(graphics2D, paramJComponent, paramInt1, paramInt2, paramArrayOfObject);
/*     */       
/* 713 */       graphics2D.dispose();
/*     */     }
/* 715 */     while (volatileImage.contentsLost() && b++ < 3);
/*     */     
/* 717 */     if (b == 3) return null;
/*     */     
/* 719 */     return volatileImage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void paint0(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 728 */     prepare(paramInt1, paramInt2);
/* 729 */     paramGraphics2D = (Graphics2D)paramGraphics2D.create();
/* 730 */     configureGraphics(paramGraphics2D);
/* 731 */     doPaint(paramGraphics2D, paramJComponent, paramInt1, paramInt2, paramArrayOfObject);
/* 732 */     paramGraphics2D.dispose();
/*     */   }
/*     */   
/*     */   private float clamp(float paramFloat) {
/* 736 */     if (paramFloat < 0.0F) {
/* 737 */       paramFloat = 0.0F;
/* 738 */     } else if (paramFloat > 1.0F) {
/* 739 */       paramFloat = 1.0F;
/*     */     } 
/* 741 */     return paramFloat;
/*     */   }
/*     */   
/*     */   private int clamp(int paramInt) {
/* 745 */     if (paramInt < 0) {
/* 746 */       paramInt = 0;
/* 747 */     } else if (paramInt > 255) {
/* 748 */       paramInt = 255;
/*     */     } 
/* 750 */     return paramInt;
/*     */   }
/*     */   
/*     */   protected abstract PaintContext getPaintContext();
/*     */   
/*     */   protected abstract void doPaint(Graphics2D paramGraphics2D, JComponent paramJComponent, int paramInt1, int paramInt2, Object[] paramArrayOfObject);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/AbstractRegionPainter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */