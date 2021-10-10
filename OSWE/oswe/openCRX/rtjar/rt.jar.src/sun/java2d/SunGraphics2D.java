/*      */ package sun.java2d;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.GradientPaint;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.Image;
/*      */ import java.awt.LinearGradientPaint;
/*      */ import java.awt.Paint;
/*      */ import java.awt.RadialGradientPaint;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.TexturePaint;
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.GlyphVector;
/*      */ import java.awt.font.TextLayout;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Area;
/*      */ import java.awt.geom.GeneralPath;
/*      */ import java.awt.geom.NoninvertibleTransformException;
/*      */ import java.awt.geom.PathIterator;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.BufferedImageOp;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.awt.image.Raster;
/*      */ import java.awt.image.RenderedImage;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.awt.image.renderable.RenderContext;
/*      */ import java.awt.image.renderable.RenderableImage;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import sun.awt.ConstrainableGraphics;
/*      */ import sun.awt.SunHints;
/*      */ import sun.awt.image.MultiResolutionImage;
/*      */ import sun.awt.image.MultiResolutionToolkitImage;
/*      */ import sun.awt.image.SurfaceManager;
/*      */ import sun.awt.image.ToolkitImage;
/*      */ import sun.font.FontDesignMetrics;
/*      */ import sun.font.FontUtilities;
/*      */ import sun.java2d.loops.Blit;
/*      */ import sun.java2d.loops.CompositeType;
/*      */ import sun.java2d.loops.FontInfo;
/*      */ import sun.java2d.loops.MaskFill;
/*      */ import sun.java2d.loops.RenderLoops;
/*      */ import sun.java2d.loops.SurfaceType;
/*      */ import sun.java2d.loops.XORComposite;
/*      */ import sun.java2d.pipe.DrawImagePipe;
/*      */ import sun.java2d.pipe.LoopPipe;
/*      */ import sun.java2d.pipe.PixelDrawPipe;
/*      */ import sun.java2d.pipe.PixelFillPipe;
/*      */ import sun.java2d.pipe.Region;
/*      */ import sun.java2d.pipe.RenderingEngine;
/*      */ import sun.java2d.pipe.ShapeDrawPipe;
/*      */ import sun.java2d.pipe.ShapeSpanIterator;
/*      */ import sun.java2d.pipe.TextPipe;
/*      */ import sun.java2d.pipe.ValidatePipe;
/*      */ import sun.misc.PerformanceLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class SunGraphics2D
/*      */   extends Graphics2D
/*      */   implements ConstrainableGraphics, Cloneable, DestSurfaceProvider
/*      */ {
/*      */   public static final int PAINT_CUSTOM = 6;
/*      */   public static final int PAINT_TEXTURE = 5;
/*      */   public static final int PAINT_RAD_GRADIENT = 4;
/*      */   public static final int PAINT_LIN_GRADIENT = 3;
/*      */   public static final int PAINT_GRADIENT = 2;
/*      */   public static final int PAINT_ALPHACOLOR = 1;
/*      */   public static final int PAINT_OPAQUECOLOR = 0;
/*      */   public static final int COMP_CUSTOM = 3;
/*      */   public static final int COMP_XOR = 2;
/*      */   public static final int COMP_ALPHA = 1;
/*      */   public static final int COMP_ISCOPY = 0;
/*      */   public static final int STROKE_CUSTOM = 3;
/*      */   public static final int STROKE_WIDE = 2;
/*      */   public static final int STROKE_THINDASHED = 1;
/*      */   public static final int STROKE_THIN = 0;
/*      */   public static final int TRANSFORM_GENERIC = 4;
/*      */   public static final int TRANSFORM_TRANSLATESCALE = 3;
/*      */   public static final int TRANSFORM_ANY_TRANSLATE = 2;
/*      */   public static final int TRANSFORM_INT_TRANSLATE = 1;
/*      */   public static final int TRANSFORM_ISIDENT = 0;
/*      */   public static final int CLIP_SHAPE = 2;
/*      */   public static final int CLIP_RECTANGULAR = 1;
/*      */   public static final int CLIP_DEVICE = 0;
/*      */   public int eargb;
/*      */   public int pixel;
/*      */   public SurfaceData surfaceData;
/*      */   public PixelDrawPipe drawpipe;
/*      */   public PixelFillPipe fillpipe;
/*      */   public DrawImagePipe imagepipe;
/*      */   public ShapeDrawPipe shapepipe;
/*      */   public TextPipe textpipe;
/*      */   public MaskFill alphafill;
/*      */   public RenderLoops loops;
/*      */   public CompositeType imageComp;
/*      */   public int paintState;
/*      */   public int compositeState;
/*      */   public int strokeState;
/*      */   public int transformState;
/*      */   public int clipState;
/*      */   public Color foregroundColor;
/*      */   public Color backgroundColor;
/*      */   public AffineTransform transform;
/*      */   public int transX;
/*      */   public int transY;
/*  211 */   protected static final Stroke defaultStroke = new BasicStroke();
/*  212 */   protected static final Composite defaultComposite = AlphaComposite.SrcOver;
/*  213 */   private static final Font defaultFont = new Font("Dialog", 0, 12);
/*      */   
/*      */   public Paint paint;
/*      */   
/*      */   public Stroke stroke;
/*      */   
/*      */   public Composite composite;
/*      */   
/*      */   protected Font font;
/*      */   
/*      */   protected FontMetrics fontMetrics;
/*      */   public int renderHint;
/*      */   public int antialiasHint;
/*      */   public int textAntialiasHint;
/*      */   protected int fractionalMetricsHint;
/*      */   public int lcdTextContrast;
/*  229 */   private static int lcdTextContrastDefaultValue = 140;
/*      */   
/*      */   private int interpolationHint;
/*      */   
/*      */   public int strokeHint;
/*      */   
/*      */   public int interpolationType;
/*      */   
/*      */   public RenderingHints hints;
/*      */   
/*      */   public Region constrainClip;
/*      */   
/*      */   public int constrainX;
/*      */   
/*      */   public int constrainY;
/*      */   
/*      */   public Region clipRegion;
/*      */   
/*      */   public Shape usrClip;
/*      */   
/*      */   protected Region devClip;
/*      */   
/*      */   private final int devScale;
/*      */   
/*      */   private int resolutionVariantHint;
/*      */   
/*      */   private boolean validFontInfo;
/*      */   private FontInfo fontInfo;
/*      */   private FontInfo glyphVectorFontInfo;
/*      */   private FontRenderContext glyphVectorFRC;
/*      */   private static final int slowTextTransformMask = 120;
/*      */   
/*      */   static {
/*  262 */     if (PerformanceLogger.loggingEnabled()) {
/*  263 */       PerformanceLogger.setTime("SunGraphics2D static initialization");
/*      */     }
/*      */   }
/*      */   
/*      */   public SunGraphics2D(SurfaceData paramSurfaceData, Color paramColor1, Color paramColor2, Font paramFont) {
/*  268 */     this.surfaceData = paramSurfaceData;
/*  269 */     this.foregroundColor = paramColor1;
/*  270 */     this.backgroundColor = paramColor2;
/*      */     
/*  272 */     this.transform = new AffineTransform();
/*  273 */     this.stroke = defaultStroke;
/*  274 */     this.composite = defaultComposite;
/*  275 */     this.paint = this.foregroundColor;
/*      */     
/*  277 */     this.imageComp = CompositeType.SrcOverNoEa;
/*      */     
/*  279 */     this.renderHint = 0;
/*  280 */     this.antialiasHint = 1;
/*  281 */     this.textAntialiasHint = 0;
/*  282 */     this.fractionalMetricsHint = 1;
/*  283 */     this.lcdTextContrast = lcdTextContrastDefaultValue;
/*  284 */     this.interpolationHint = -1;
/*  285 */     this.strokeHint = 0;
/*  286 */     this.resolutionVariantHint = 0;
/*      */     
/*  288 */     this.interpolationType = 1;
/*      */     
/*  290 */     validateColor();
/*      */     
/*  292 */     this.devScale = paramSurfaceData.getDefaultScale();
/*  293 */     if (this.devScale != 1) {
/*  294 */       this.transform.setToScale(this.devScale, this.devScale);
/*  295 */       invalidateTransform();
/*      */     } 
/*      */     
/*  298 */     this.font = paramFont;
/*  299 */     if (this.font == null) {
/*  300 */       this.font = defaultFont;
/*      */     }
/*      */     
/*  303 */     setDevClip(paramSurfaceData.getBounds());
/*  304 */     invalidatePipe();
/*      */   }
/*      */   
/*      */   protected Object clone() {
/*      */     try {
/*  309 */       SunGraphics2D sunGraphics2D = (SunGraphics2D)super.clone();
/*  310 */       sunGraphics2D.transform = new AffineTransform(this.transform);
/*  311 */       if (this.hints != null) {
/*  312 */         sunGraphics2D.hints = (RenderingHints)this.hints.clone();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  320 */       if (this.fontInfo != null) {
/*  321 */         if (this.validFontInfo) {
/*  322 */           sunGraphics2D.fontInfo = (FontInfo)this.fontInfo.clone();
/*      */         } else {
/*  324 */           sunGraphics2D.fontInfo = null;
/*      */         } 
/*      */       }
/*  327 */       if (this.glyphVectorFontInfo != null) {
/*  328 */         sunGraphics2D
/*  329 */           .glyphVectorFontInfo = (FontInfo)this.glyphVectorFontInfo.clone();
/*  330 */         sunGraphics2D.glyphVectorFRC = this.glyphVectorFRC;
/*      */       } 
/*      */       
/*  333 */       return sunGraphics2D;
/*  334 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/*  336 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Graphics create() {
/*  343 */     return (Graphics)clone();
/*      */   }
/*      */   
/*      */   public void setDevClip(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  347 */     Region region = this.constrainClip;
/*  348 */     if (region == null) {
/*  349 */       this.devClip = Region.getInstanceXYWH(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     } else {
/*  351 */       this.devClip = region.getIntersectionXYWH(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     } 
/*  353 */     validateCompClip();
/*      */   }
/*      */   
/*      */   public void setDevClip(Rectangle paramRectangle) {
/*  357 */     setDevClip(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void constrain(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Region paramRegion) {
/*  364 */     if ((paramInt1 | paramInt2) != 0) {
/*  365 */       translate(paramInt1, paramInt2);
/*      */     }
/*  367 */     if (this.transformState > 3) {
/*  368 */       clipRect(0, 0, paramInt3, paramInt4);
/*      */       
/*      */       return;
/*      */     } 
/*  372 */     double d1 = this.transform.getScaleX();
/*  373 */     double d2 = this.transform.getScaleY();
/*  374 */     paramInt1 = this.constrainX = (int)this.transform.getTranslateX();
/*  375 */     paramInt2 = this.constrainY = (int)this.transform.getTranslateY();
/*  376 */     paramInt3 = Region.dimAdd(paramInt1, Region.clipScale(paramInt3, d1));
/*  377 */     paramInt4 = Region.dimAdd(paramInt2, Region.clipScale(paramInt4, d2));
/*      */     
/*  379 */     Region region = this.constrainClip;
/*  380 */     if (region == null) {
/*  381 */       region = Region.getInstanceXYXY(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     } else {
/*  383 */       region = region.getIntersectionXYXY(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     } 
/*  385 */     if (paramRegion != null) {
/*  386 */       paramRegion = paramRegion.getScaledRegion(d1, d2);
/*  387 */       paramRegion = paramRegion.getTranslatedRegion(paramInt1, paramInt2);
/*  388 */       region = region.getIntersection(paramRegion);
/*      */     } 
/*      */     
/*  391 */     if (region == this.constrainClip) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  396 */     this.constrainClip = region;
/*  397 */     if (!this.devClip.isInsideQuickCheck(region)) {
/*  398 */       this.devClip = this.devClip.getIntersection(region);
/*  399 */       validateCompClip();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void constrain(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  417 */     constrain(paramInt1, paramInt2, paramInt3, paramInt4, null);
/*      */   }
/*      */   
/*  420 */   protected static ValidatePipe invalidpipe = new ValidatePipe();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void invalidatePipe() {
/*  426 */     this.drawpipe = invalidpipe;
/*  427 */     this.fillpipe = invalidpipe;
/*  428 */     this.shapepipe = invalidpipe;
/*  429 */     this.textpipe = invalidpipe;
/*  430 */     this.imagepipe = invalidpipe;
/*  431 */     this.loops = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void validatePipe() {
/*  442 */     if (!this.surfaceData.isValid()) {
/*  443 */       throw new InvalidPipeException("attempt to validate Pipe with invalid SurfaceData");
/*      */     }
/*      */     
/*  446 */     this.surfaceData.validatePipe(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Shape intersectShapes(Shape paramShape1, Shape paramShape2, boolean paramBoolean1, boolean paramBoolean2) {
/*  457 */     if (paramShape1 instanceof Rectangle && paramShape2 instanceof Rectangle) {
/*  458 */       return ((Rectangle)paramShape1).intersection((Rectangle)paramShape2);
/*      */     }
/*  460 */     if (paramShape1 instanceof Rectangle2D)
/*  461 */       return intersectRectShape((Rectangle2D)paramShape1, paramShape2, paramBoolean1, paramBoolean2); 
/*  462 */     if (paramShape2 instanceof Rectangle2D) {
/*  463 */       return intersectRectShape((Rectangle2D)paramShape2, paramShape1, paramBoolean2, paramBoolean1);
/*      */     }
/*  465 */     return intersectByArea(paramShape1, paramShape2, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Shape intersectRectShape(Rectangle2D paramRectangle2D, Shape paramShape, boolean paramBoolean1, boolean paramBoolean2) {
/*  477 */     if (paramShape instanceof Rectangle2D) {
/*  478 */       Rectangle2D rectangle2D2, rectangle2D1 = (Rectangle2D)paramShape;
/*      */       
/*  480 */       if (!paramBoolean1) {
/*  481 */         rectangle2D2 = paramRectangle2D;
/*  482 */       } else if (!paramBoolean2) {
/*  483 */         rectangle2D2 = rectangle2D1;
/*      */       } else {
/*  485 */         rectangle2D2 = new Rectangle2D.Float();
/*      */       } 
/*  487 */       double d1 = Math.max(paramRectangle2D.getX(), rectangle2D1.getX());
/*  488 */       double d2 = Math.min(paramRectangle2D.getX() + paramRectangle2D.getWidth(), rectangle2D1
/*  489 */           .getX() + rectangle2D1.getWidth());
/*  490 */       double d3 = Math.max(paramRectangle2D.getY(), rectangle2D1.getY());
/*  491 */       double d4 = Math.min(paramRectangle2D.getY() + paramRectangle2D.getHeight(), rectangle2D1
/*  492 */           .getY() + rectangle2D1.getHeight());
/*      */       
/*  494 */       if (d2 - d1 < 0.0D || d4 - d3 < 0.0D) {
/*      */         
/*  496 */         rectangle2D2.setFrameFromDiagonal(0.0D, 0.0D, 0.0D, 0.0D);
/*      */       } else {
/*  498 */         rectangle2D2.setFrameFromDiagonal(d1, d3, d2, d4);
/*  499 */       }  return rectangle2D2;
/*      */     } 
/*  501 */     if (paramRectangle2D.contains(paramShape.getBounds2D())) {
/*  502 */       if (paramBoolean2) {
/*  503 */         paramShape = cloneShape(paramShape);
/*      */       }
/*  505 */       return paramShape;
/*      */     } 
/*  507 */     return intersectByArea(paramRectangle2D, paramShape, paramBoolean1, paramBoolean2);
/*      */   }
/*      */   
/*      */   protected static Shape cloneShape(Shape paramShape) {
/*  511 */     return new GeneralPath(paramShape);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Shape intersectByArea(Shape paramShape1, Shape paramShape2, boolean paramBoolean1, boolean paramBoolean2) {
/*      */     Area area1, area2;
/*  528 */     if (!paramBoolean1 && paramShape1 instanceof Area) {
/*  529 */       area1 = (Area)paramShape1;
/*  530 */     } else if (!paramBoolean2 && paramShape2 instanceof Area) {
/*  531 */       area1 = (Area)paramShape2;
/*  532 */       paramShape2 = paramShape1;
/*      */     } else {
/*  534 */       area1 = new Area(paramShape1);
/*      */     } 
/*      */     
/*  537 */     if (paramShape2 instanceof Area) {
/*  538 */       area2 = (Area)paramShape2;
/*      */     } else {
/*  540 */       area2 = new Area(paramShape2);
/*      */     } 
/*      */     
/*  543 */     area1.intersect(area2);
/*  544 */     if (area1.isRectangular()) {
/*  545 */       return area1.getBounds();
/*      */     }
/*      */     
/*  548 */     return area1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Region getCompClip() {
/*  556 */     if (!this.surfaceData.isValid())
/*      */     {
/*  558 */       revalidateAll();
/*      */     }
/*      */     
/*  561 */     return this.clipRegion;
/*      */   }
/*      */   
/*      */   public Font getFont() {
/*  565 */     if (this.font == null) {
/*  566 */       this.font = defaultFont;
/*      */     }
/*  568 */     return this.font;
/*      */   }
/*      */   
/*  571 */   private static final double[] IDENT_MATRIX = new double[] { 1.0D, 0.0D, 0.0D, 1.0D };
/*  572 */   private static final AffineTransform IDENT_ATX = new AffineTransform();
/*      */   
/*      */   private static final int MINALLOCATED = 8;
/*      */   
/*      */   private static final int TEXTARRSIZE = 17;
/*  577 */   private static double[][] textTxArr = new double[17][];
/*  578 */   private static AffineTransform[] textAtArr = new AffineTransform[17];
/*      */   static final int NON_UNIFORM_SCALE_MASK = 36;
/*      */   
/*      */   static {
/*  582 */     for (byte b = 8; b < 17; b++) {
/*  583 */       (new double[4])[0] = b; (new double[4])[1] = 0.0D; (new double[4])[2] = 0.0D; (new double[4])[3] = b; textTxArr[b] = new double[4];
/*  584 */       textAtArr[b] = new AffineTransform(textTxArr[b]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FontInfo checkFontInfo(FontInfo paramFontInfo, Font paramFont, FontRenderContext paramFontRenderContext) {
/*      */     AffineTransform affineTransform1;
/*      */     int j;
/*  595 */     if (paramFontInfo == null) {
/*  596 */       paramFontInfo = new FontInfo();
/*      */     }
/*      */     
/*  599 */     float f = paramFont.getSize2D();
/*      */     
/*  601 */     AffineTransform affineTransform2 = null;
/*  602 */     if (paramFont.isTransformed()) {
/*  603 */       affineTransform2 = paramFont.getTransform();
/*  604 */       affineTransform2.scale(f, f);
/*  605 */       int k = affineTransform2.getType();
/*  606 */       paramFontInfo.originX = (float)affineTransform2.getTranslateX();
/*  607 */       paramFontInfo.originY = (float)affineTransform2.getTranslateY();
/*  608 */       affineTransform2.translate(-paramFontInfo.originX, -paramFontInfo.originY);
/*  609 */       if (this.transformState >= 3) {
/*  610 */         this.transform.getMatrix(paramFontInfo.devTx = new double[4]);
/*  611 */         affineTransform1 = new AffineTransform(paramFontInfo.devTx);
/*  612 */         affineTransform2.preConcatenate(affineTransform1);
/*      */       } else {
/*  614 */         paramFontInfo.devTx = IDENT_MATRIX;
/*  615 */         affineTransform1 = IDENT_ATX;
/*      */       } 
/*  617 */       affineTransform2.getMatrix(paramFontInfo.glyphTx = new double[4]);
/*  618 */       double d1 = affineTransform2.getShearX();
/*  619 */       double d2 = affineTransform2.getScaleY();
/*  620 */       if (d1 != 0.0D) {
/*  621 */         d2 = Math.sqrt(d1 * d1 + d2 * d2);
/*      */       }
/*  623 */       paramFontInfo.pixelHeight = (int)(Math.abs(d2) + 0.5D);
/*      */     } else {
/*  625 */       boolean bool = false;
/*  626 */       paramFontInfo.originX = paramFontInfo.originY = 0.0F;
/*  627 */       if (this.transformState >= 3) {
/*  628 */         this.transform.getMatrix(paramFontInfo.devTx = new double[4]);
/*  629 */         affineTransform1 = new AffineTransform(paramFontInfo.devTx);
/*  630 */         paramFontInfo.glyphTx = new double[4];
/*  631 */         for (byte b = 0; b < 4; b++) {
/*  632 */           paramFontInfo.glyphTx[b] = paramFontInfo.devTx[b] * f;
/*      */         }
/*  634 */         affineTransform2 = new AffineTransform(paramFontInfo.glyphTx);
/*  635 */         double d1 = this.transform.getShearX();
/*  636 */         double d2 = this.transform.getScaleY();
/*  637 */         if (d1 != 0.0D) {
/*  638 */           d2 = Math.sqrt(d1 * d1 + d2 * d2);
/*      */         }
/*  640 */         paramFontInfo.pixelHeight = (int)(Math.abs(d2 * f) + 0.5D);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  650 */         int k = (int)f;
/*  651 */         if (f == k && k >= 8 && k < 17) {
/*      */           
/*  653 */           paramFontInfo.glyphTx = textTxArr[k];
/*  654 */           affineTransform2 = textAtArr[k];
/*  655 */           paramFontInfo.pixelHeight = k;
/*      */         } else {
/*  657 */           paramFontInfo.pixelHeight = (int)(f + 0.5D);
/*      */         } 
/*  659 */         if (affineTransform2 == null) {
/*  660 */           paramFontInfo.glyphTx = new double[] { f, 0.0D, 0.0D, f };
/*  661 */           affineTransform2 = new AffineTransform(paramFontInfo.glyphTx);
/*      */         } 
/*      */         
/*  664 */         paramFontInfo.devTx = IDENT_MATRIX;
/*  665 */         affineTransform1 = IDENT_ATX;
/*      */       } 
/*      */     } 
/*      */     
/*  669 */     paramFontInfo.font2D = FontUtilities.getFont2D(paramFont);
/*      */     
/*  671 */     int i = this.fractionalMetricsHint;
/*  672 */     if (i == 0) {
/*  673 */       i = 1;
/*      */     }
/*  675 */     paramFontInfo.lcdSubPixPos = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  700 */     if (paramFontRenderContext == null) {
/*  701 */       j = this.textAntialiasHint;
/*      */     } else {
/*  703 */       j = ((SunHints.Value)paramFontRenderContext.getAntiAliasingHint()).getIndex();
/*      */     } 
/*  705 */     if (j == 0) {
/*  706 */       if (this.antialiasHint == 2) {
/*  707 */         j = 2;
/*      */       } else {
/*  709 */         j = 1;
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  718 */     else if (j == 3) {
/*  719 */       if (paramFontInfo.font2D.useAAForPtSize(paramFontInfo.pixelHeight)) {
/*  720 */         j = 2;
/*      */       } else {
/*  722 */         j = 1;
/*      */       } 
/*  724 */     } else if (j >= 4) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  741 */       if (!this.surfaceData.canRenderLCDText(this)) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  746 */         j = 2;
/*      */       } else {
/*  748 */         paramFontInfo.lcdRGBOrder = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  756 */         if (j == 5) {
/*  757 */           j = 4;
/*  758 */           paramFontInfo.lcdRGBOrder = false;
/*  759 */         } else if (j == 7) {
/*      */           
/*  761 */           j = 6;
/*  762 */           paramFontInfo.lcdRGBOrder = false;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  767 */         paramFontInfo.lcdSubPixPos = (i == 2 && j == 4);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  773 */     paramFontInfo.aaHint = j;
/*  774 */     paramFontInfo.fontStrike = paramFontInfo.font2D.getStrike(paramFont, affineTransform1, affineTransform2, j, i);
/*      */     
/*  776 */     return paramFontInfo;
/*      */   }
/*      */   
/*      */   public static boolean isRotated(double[] paramArrayOfdouble) {
/*  780 */     if (paramArrayOfdouble[0] == paramArrayOfdouble[3] && paramArrayOfdouble[1] == 0.0D && paramArrayOfdouble[2] == 0.0D && paramArrayOfdouble[0] > 0.0D)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  785 */       return false;
/*      */     }
/*      */     
/*  788 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFont(Font paramFont) {
/*  797 */     if (paramFont != null && paramFont != this.font) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  812 */       if (this.textAntialiasHint == 3 && this.textpipe != invalidpipe && (this.transformState > 2 || paramFont
/*      */ 
/*      */         
/*  815 */         .isTransformed() || this.fontInfo == null || ((this.fontInfo.aaHint == 2)) != 
/*      */ 
/*      */         
/*  818 */         FontUtilities.getFont2D(paramFont)
/*  819 */         .useAAForPtSize(paramFont.getSize()))) {
/*  820 */         this.textpipe = invalidpipe;
/*      */       }
/*  822 */       this.font = paramFont;
/*  823 */       this.fontMetrics = null;
/*  824 */       this.validFontInfo = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public FontInfo getFontInfo() {
/*  829 */     if (!this.validFontInfo) {
/*  830 */       this.fontInfo = checkFontInfo(this.fontInfo, this.font, null);
/*  831 */       this.validFontInfo = true;
/*      */     } 
/*  833 */     return this.fontInfo;
/*      */   }
/*      */ 
/*      */   
/*      */   public FontInfo getGVFontInfo(Font paramFont, FontRenderContext paramFontRenderContext) {
/*  838 */     if (this.glyphVectorFontInfo != null && this.glyphVectorFontInfo.font == paramFont && this.glyphVectorFRC == paramFontRenderContext)
/*      */     {
/*      */       
/*  841 */       return this.glyphVectorFontInfo;
/*      */     }
/*  843 */     this.glyphVectorFRC = paramFontRenderContext;
/*  844 */     return this
/*  845 */       .glyphVectorFontInfo = checkFontInfo(this.glyphVectorFontInfo, paramFont, paramFontRenderContext);
/*      */   }
/*      */ 
/*      */   
/*      */   public FontMetrics getFontMetrics() {
/*  850 */     if (this.fontMetrics != null) {
/*  851 */       return this.fontMetrics;
/*      */     }
/*      */     
/*  854 */     return this
/*  855 */       .fontMetrics = FontDesignMetrics.getMetrics(this.font, getFontRenderContext());
/*      */   }
/*      */   
/*      */   public FontMetrics getFontMetrics(Font paramFont) {
/*  859 */     if (this.fontMetrics != null && paramFont == this.font) {
/*  860 */       return this.fontMetrics;
/*      */     }
/*      */     
/*  863 */     FontDesignMetrics fontDesignMetrics = FontDesignMetrics.getMetrics(paramFont, getFontRenderContext());
/*      */     
/*  865 */     if (this.font == paramFont) {
/*  866 */       this.fontMetrics = fontDesignMetrics;
/*      */     }
/*  868 */     return fontDesignMetrics;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hit(Rectangle paramRectangle, Shape paramShape, boolean paramBoolean) {
/*  889 */     if (paramBoolean) {
/*  890 */       paramShape = this.stroke.createStrokedShape(paramShape);
/*      */     }
/*      */     
/*  893 */     paramShape = transformShape(paramShape);
/*  894 */     if ((this.constrainX | this.constrainY) != 0) {
/*  895 */       paramRectangle = new Rectangle(paramRectangle);
/*  896 */       paramRectangle.translate(this.constrainX, this.constrainY);
/*      */     } 
/*      */     
/*  899 */     return paramShape.intersects(paramRectangle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ColorModel getDeviceColorModel() {
/*  906 */     return this.surfaceData.getColorModel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GraphicsConfiguration getDeviceConfiguration() {
/*  913 */     return this.surfaceData.getDeviceConfiguration();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final SurfaceData getSurfaceData() {
/*  921 */     return this.surfaceData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setComposite(Composite paramComposite) {
/*      */     byte b;
/*      */     CompositeType compositeType;
/*  935 */     if (this.composite == paramComposite) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  940 */     if (paramComposite instanceof AlphaComposite)
/*  941 */     { AlphaComposite alphaComposite = (AlphaComposite)paramComposite;
/*  942 */       compositeType = CompositeType.forAlphaComposite(alphaComposite);
/*  943 */       if (compositeType == CompositeType.SrcOverNoEa) {
/*  944 */         if (this.paintState == 0 || (this.paintState > 1 && this.paint
/*      */           
/*  946 */           .getTransparency() == 1)) {
/*      */           
/*  948 */           b = 0;
/*      */         } else {
/*  950 */           b = 1;
/*      */         } 
/*  952 */       } else if (compositeType == CompositeType.SrcNoEa || compositeType == CompositeType.Src || compositeType == CompositeType.Clear) {
/*      */ 
/*      */ 
/*      */         
/*  956 */         b = 0;
/*  957 */       } else if (this.surfaceData.getTransparency() == 1 && compositeType == CompositeType.SrcIn) {
/*      */ 
/*      */         
/*  960 */         b = 0;
/*      */       } else {
/*  962 */         b = 1;
/*      */       }  }
/*  964 */     else if (paramComposite instanceof XORComposite)
/*  965 */     { b = 2;
/*  966 */       compositeType = CompositeType.Xor; }
/*  967 */     else { if (paramComposite == null) {
/*  968 */         throw new IllegalArgumentException("null Composite");
/*      */       }
/*  970 */       this.surfaceData.checkCustomComposite();
/*  971 */       b = 3;
/*  972 */       compositeType = CompositeType.General; }
/*      */     
/*  974 */     if (this.compositeState != b || this.imageComp != compositeType) {
/*      */ 
/*      */       
/*  977 */       this.compositeState = b;
/*  978 */       this.imageComp = compositeType;
/*  979 */       invalidatePipe();
/*  980 */       this.validFontInfo = false;
/*      */     } 
/*  982 */     this.composite = paramComposite;
/*  983 */     if (this.paintState <= 1) {
/*  984 */       validateColor();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPaint(Paint paramPaint) {
/*  997 */     if (paramPaint instanceof Color) {
/*  998 */       setColor((Color)paramPaint);
/*      */       return;
/*      */     } 
/* 1001 */     if (paramPaint == null || this.paint == paramPaint) {
/*      */       return;
/*      */     }
/* 1004 */     this.paint = paramPaint;
/* 1005 */     if (this.imageComp == CompositeType.SrcOverNoEa)
/*      */     {
/* 1007 */       if (paramPaint.getTransparency() == 1) {
/* 1008 */         if (this.compositeState != 0) {
/* 1009 */           this.compositeState = 0;
/*      */         }
/*      */       }
/* 1012 */       else if (this.compositeState == 0) {
/* 1013 */         this.compositeState = 1;
/*      */       } 
/*      */     }
/*      */     
/* 1017 */     Class<?> clazz = paramPaint.getClass();
/* 1018 */     if (clazz == GradientPaint.class) {
/* 1019 */       this.paintState = 2;
/* 1020 */     } else if (clazz == LinearGradientPaint.class) {
/* 1021 */       this.paintState = 3;
/* 1022 */     } else if (clazz == RadialGradientPaint.class) {
/* 1023 */       this.paintState = 4;
/* 1024 */     } else if (clazz == TexturePaint.class) {
/* 1025 */       this.paintState = 5;
/*      */     } else {
/* 1027 */       this.paintState = 6;
/*      */     } 
/* 1029 */     this.validFontInfo = false;
/* 1030 */     invalidatePipe();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1037 */   public static final double MinPenSizeAA = RenderingEngine.getInstance().getMinimumAAPenSize();
/* 1038 */   public static final double MinPenSizeAASquared = MinPenSizeAA * MinPenSizeAA;
/*      */   
/*      */   public static final double MinPenSizeSquared = 1.000000001D;
/*      */   
/*      */   static final int NON_RECTILINEAR_TRANSFORM_MASK = 48;
/*      */   Blit lastCAblit;
/*      */   Composite lastCAcomp;
/*      */   private FontRenderContext cachedFRC;
/*      */   
/*      */   private void validateBasicStroke(BasicStroke paramBasicStroke) {
/* 1048 */     boolean bool = (this.antialiasHint == 2) ? true : false;
/* 1049 */     if (this.transformState < 3) {
/* 1050 */       if (bool) {
/* 1051 */         if (paramBasicStroke.getLineWidth() <= MinPenSizeAA) {
/* 1052 */           if (paramBasicStroke.getDashArray() == null) {
/* 1053 */             this.strokeState = 0;
/*      */           } else {
/* 1055 */             this.strokeState = 1;
/*      */           } 
/*      */         } else {
/* 1058 */           this.strokeState = 2;
/*      */         }
/*      */       
/* 1061 */       } else if (paramBasicStroke == defaultStroke) {
/* 1062 */         this.strokeState = 0;
/* 1063 */       } else if (paramBasicStroke.getLineWidth() <= 1.0F) {
/* 1064 */         if (paramBasicStroke.getDashArray() == null) {
/* 1065 */           this.strokeState = 0;
/*      */         } else {
/* 1067 */           this.strokeState = 1;
/*      */         } 
/*      */       } else {
/* 1070 */         this.strokeState = 2;
/*      */       } 
/*      */     } else {
/*      */       double d;
/*      */       
/* 1075 */       if ((this.transform.getType() & 0x24) == 0) {
/*      */         
/* 1077 */         d = Math.abs(this.transform.getDeterminant());
/*      */       } else {
/*      */         
/* 1080 */         double d1 = this.transform.getScaleX();
/* 1081 */         double d2 = this.transform.getShearX();
/* 1082 */         double d3 = this.transform.getShearY();
/* 1083 */         double d4 = this.transform.getScaleY();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1099 */         double d5 = d1 * d1 + d3 * d3;
/* 1100 */         double d6 = 2.0D * (d1 * d2 + d3 * d4);
/* 1101 */         double d7 = d2 * d2 + d4 * d4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1125 */         double d8 = Math.sqrt(d6 * d6 + (d5 - d7) * (d5 - d7));
/*      */ 
/*      */         
/* 1128 */         d = (d5 + d7 + d8) / 2.0D;
/*      */       } 
/* 1130 */       if (paramBasicStroke != defaultStroke) {
/* 1131 */         d *= (paramBasicStroke.getLineWidth() * paramBasicStroke.getLineWidth());
/*      */       }
/* 1133 */       if (d <= (bool ? MinPenSizeAASquared : 1.000000001D)) {
/*      */ 
/*      */         
/* 1136 */         if (paramBasicStroke.getDashArray() == null) {
/* 1137 */           this.strokeState = 0;
/*      */         } else {
/* 1139 */           this.strokeState = 1;
/*      */         } 
/*      */       } else {
/* 1142 */         this.strokeState = 2;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStroke(Stroke paramStroke) {
/* 1154 */     if (paramStroke == null) {
/* 1155 */       throw new IllegalArgumentException("null Stroke");
/*      */     }
/* 1157 */     int i = this.strokeState;
/* 1158 */     this.stroke = paramStroke;
/* 1159 */     if (paramStroke instanceof BasicStroke) {
/* 1160 */       validateBasicStroke((BasicStroke)paramStroke);
/*      */     } else {
/* 1162 */       this.strokeState = 3;
/*      */     } 
/* 1164 */     if (this.strokeState != i) {
/* 1165 */       invalidatePipe();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderingHint(RenderingHints.Key paramKey, Object paramObject) {
/* 1186 */     if (!paramKey.isCompatibleValue(paramObject)) {
/* 1187 */       throw new IllegalArgumentException(paramObject + " is not compatible with " + paramKey);
/*      */     }
/*      */     
/* 1190 */     if (paramKey instanceof SunHints.Key) {
/*      */       boolean bool1; int i;
/* 1192 */       boolean bool2 = false;
/* 1193 */       boolean bool3 = true;
/* 1194 */       SunHints.Key key = (SunHints.Key)paramKey;
/*      */       
/* 1196 */       if (key == SunHints.KEY_TEXT_ANTIALIAS_LCD_CONTRAST) {
/* 1197 */         i = ((Integer)paramObject).intValue();
/*      */       } else {
/* 1199 */         i = ((SunHints.Value)paramObject).getIndex();
/*      */       } 
/* 1201 */       switch (key.getIndex()) {
/*      */         case 0:
/* 1203 */           bool1 = (this.renderHint != i) ? true : false;
/* 1204 */           if (bool1) {
/* 1205 */             this.renderHint = i;
/* 1206 */             if (this.interpolationHint == -1) {
/* 1207 */               this.interpolationType = (i == 2) ? 2 : 1;
/*      */             }
/*      */           } 
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 1:
/* 1215 */           bool1 = (this.antialiasHint != i) ? true : false;
/* 1216 */           this.antialiasHint = i;
/* 1217 */           if (bool1) {
/* 1218 */             bool2 = (this.textAntialiasHint == 0) ? true : false;
/*      */ 
/*      */             
/* 1221 */             if (this.strokeState != 3) {
/* 1222 */               validateBasicStroke((BasicStroke)this.stroke);
/*      */             }
/*      */           } 
/*      */           break;
/*      */         case 2:
/* 1227 */           bool1 = (this.textAntialiasHint != i) ? true : false;
/* 1228 */           bool2 = bool1;
/* 1229 */           this.textAntialiasHint = i;
/*      */           break;
/*      */         case 3:
/* 1232 */           bool1 = (this.fractionalMetricsHint != i) ? true : false;
/* 1233 */           bool2 = bool1;
/* 1234 */           this.fractionalMetricsHint = i;
/*      */           break;
/*      */         case 100:
/* 1237 */           bool1 = false;
/*      */           
/* 1239 */           this.lcdTextContrast = i;
/*      */           break;
/*      */         case 5:
/* 1242 */           this.interpolationHint = i;
/* 1243 */           switch (i) {
/*      */             case 2:
/* 1245 */               i = 3;
/*      */               break;
/*      */             case 1:
/* 1248 */               i = 2;
/*      */               break;
/*      */             
/*      */             default:
/* 1252 */               i = 1;
/*      */               break;
/*      */           } 
/* 1255 */           bool1 = (this.interpolationType != i) ? true : false;
/* 1256 */           this.interpolationType = i;
/*      */           break;
/*      */         case 8:
/* 1259 */           bool1 = (this.strokeHint != i) ? true : false;
/* 1260 */           this.strokeHint = i;
/*      */           break;
/*      */         case 9:
/* 1263 */           bool1 = (this.resolutionVariantHint != i) ? true : false;
/* 1264 */           this.resolutionVariantHint = i;
/*      */           break;
/*      */         default:
/* 1267 */           bool3 = false;
/* 1268 */           bool1 = false;
/*      */           break;
/*      */       } 
/* 1271 */       if (bool3) {
/* 1272 */         if (bool1) {
/* 1273 */           invalidatePipe();
/* 1274 */           if (bool2) {
/* 1275 */             this.fontMetrics = null;
/* 1276 */             this.cachedFRC = null;
/* 1277 */             this.validFontInfo = false;
/* 1278 */             this.glyphVectorFontInfo = null;
/*      */           } 
/*      */         } 
/* 1281 */         if (this.hints != null) {
/* 1282 */           this.hints.put(paramKey, paramObject);
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 1288 */     if (this.hints == null) {
/* 1289 */       this.hints = makeHints(null);
/*      */     }
/* 1291 */     this.hints.put(paramKey, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getRenderingHint(RenderingHints.Key paramKey) {
/* 1304 */     if (this.hints != null) {
/* 1305 */       return this.hints.get(paramKey);
/*      */     }
/* 1307 */     if (!(paramKey instanceof SunHints.Key)) {
/* 1308 */       return null;
/*      */     }
/* 1310 */     int i = ((SunHints.Key)paramKey).getIndex();
/* 1311 */     switch (i) {
/*      */       case 0:
/* 1313 */         return SunHints.Value.get(0, this.renderHint);
/*      */       
/*      */       case 1:
/* 1316 */         return SunHints.Value.get(1, this.antialiasHint);
/*      */       
/*      */       case 2:
/* 1319 */         return SunHints.Value.get(2, this.textAntialiasHint);
/*      */       
/*      */       case 3:
/* 1322 */         return SunHints.Value.get(3, this.fractionalMetricsHint);
/*      */       
/*      */       case 100:
/* 1325 */         return new Integer(this.lcdTextContrast);
/*      */       case 5:
/* 1327 */         switch (this.interpolationHint) {
/*      */           case 0:
/* 1329 */             return SunHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
/*      */           case 1:
/* 1331 */             return SunHints.VALUE_INTERPOLATION_BILINEAR;
/*      */           case 2:
/* 1333 */             return SunHints.VALUE_INTERPOLATION_BICUBIC;
/*      */         } 
/* 1335 */         return null;
/*      */       case 8:
/* 1337 */         return SunHints.Value.get(8, this.strokeHint);
/*      */       
/*      */       case 9:
/* 1340 */         return SunHints.Value.get(9, this.resolutionVariantHint);
/*      */     } 
/*      */     
/* 1343 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderingHints(Map<?, ?> paramMap) {
/* 1354 */     this.hints = null;
/* 1355 */     this.renderHint = 0;
/* 1356 */     this.antialiasHint = 1;
/* 1357 */     this.textAntialiasHint = 0;
/* 1358 */     this.fractionalMetricsHint = 1;
/* 1359 */     this.lcdTextContrast = lcdTextContrastDefaultValue;
/* 1360 */     this.interpolationHint = -1;
/* 1361 */     this.interpolationType = 1;
/* 1362 */     boolean bool = false;
/* 1363 */     Iterator<Object> iterator = paramMap.keySet().iterator();
/* 1364 */     while (iterator.hasNext()) {
/* 1365 */       SunHints.Key key = (SunHints.Key)iterator.next();
/* 1366 */       if (key == SunHints.KEY_RENDERING || key == SunHints.KEY_ANTIALIASING || key == SunHints.KEY_TEXT_ANTIALIASING || key == SunHints.KEY_FRACTIONALMETRICS || key == SunHints.KEY_TEXT_ANTIALIAS_LCD_CONTRAST || key == SunHints.KEY_STROKE_CONTROL || key == SunHints.KEY_INTERPOLATION) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1374 */         setRenderingHint(key, paramMap.get(key)); continue;
/*      */       } 
/* 1376 */       bool = true;
/*      */     } 
/*      */     
/* 1379 */     if (bool) {
/* 1380 */       this.hints = makeHints(paramMap);
/*      */     }
/* 1382 */     invalidatePipe();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRenderingHints(Map<?, ?> paramMap) {
/* 1393 */     boolean bool = false;
/* 1394 */     Iterator<Object> iterator = paramMap.keySet().iterator();
/* 1395 */     while (iterator.hasNext()) {
/* 1396 */       SunHints.Key key = (SunHints.Key)iterator.next();
/* 1397 */       if (key == SunHints.KEY_RENDERING || key == SunHints.KEY_ANTIALIASING || key == SunHints.KEY_TEXT_ANTIALIASING || key == SunHints.KEY_FRACTIONALMETRICS || key == SunHints.KEY_TEXT_ANTIALIAS_LCD_CONTRAST || key == SunHints.KEY_STROKE_CONTROL || key == SunHints.KEY_INTERPOLATION) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1405 */         setRenderingHint(key, paramMap.get(key)); continue;
/*      */       } 
/* 1407 */       bool = true;
/*      */     } 
/*      */     
/* 1410 */     if (bool) {
/* 1411 */       if (this.hints == null) {
/* 1412 */         this.hints = makeHints(paramMap);
/*      */       } else {
/* 1414 */         this.hints.putAll(paramMap);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RenderingHints getRenderingHints() {
/* 1426 */     if (this.hints == null) {
/* 1427 */       return makeHints(null);
/*      */     }
/* 1429 */     return (RenderingHints)this.hints.clone();
/*      */   }
/*      */   
/*      */   RenderingHints makeHints(Map<RenderingHints.Key, ?> paramMap) {
/*      */     Object object;
/* 1434 */     RenderingHints renderingHints = new RenderingHints(paramMap);
/* 1435 */     renderingHints.put(SunHints.KEY_RENDERING, 
/* 1436 */         SunHints.Value.get(0, this.renderHint));
/*      */     
/* 1438 */     renderingHints.put(SunHints.KEY_ANTIALIASING, 
/* 1439 */         SunHints.Value.get(1, this.antialiasHint));
/*      */     
/* 1441 */     renderingHints.put(SunHints.KEY_TEXT_ANTIALIASING, 
/* 1442 */         SunHints.Value.get(2, this.textAntialiasHint));
/*      */     
/* 1444 */     renderingHints.put(SunHints.KEY_FRACTIONALMETRICS, 
/* 1445 */         SunHints.Value.get(3, this.fractionalMetricsHint));
/*      */     
/* 1447 */     renderingHints.put(SunHints.KEY_TEXT_ANTIALIAS_LCD_CONTRAST, 
/* 1448 */         Integer.valueOf(this.lcdTextContrast));
/*      */     
/* 1450 */     switch (this.interpolationHint) {
/*      */       case 0:
/* 1452 */         object = SunHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
/*      */         break;
/*      */       case 1:
/* 1455 */         object = SunHints.VALUE_INTERPOLATION_BILINEAR;
/*      */         break;
/*      */       case 2:
/* 1458 */         object = SunHints.VALUE_INTERPOLATION_BICUBIC;
/*      */         break;
/*      */       default:
/* 1461 */         object = null;
/*      */         break;
/*      */     } 
/* 1464 */     if (object != null) {
/* 1465 */       renderingHints.put(SunHints.KEY_INTERPOLATION, object);
/*      */     }
/* 1467 */     renderingHints.put(SunHints.KEY_STROKE_CONTROL, 
/* 1468 */         SunHints.Value.get(8, this.strokeHint));
/*      */     
/* 1470 */     return renderingHints;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void translate(double paramDouble1, double paramDouble2) {
/* 1485 */     this.transform.translate(paramDouble1, paramDouble2);
/* 1486 */     invalidateTransform();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rotate(double paramDouble) {
/* 1504 */     this.transform.rotate(paramDouble);
/* 1505 */     invalidateTransform();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rotate(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 1524 */     this.transform.rotate(paramDouble1, paramDouble2, paramDouble3);
/* 1525 */     invalidateTransform();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void scale(double paramDouble1, double paramDouble2) {
/* 1540 */     this.transform.scale(paramDouble1, paramDouble2);
/* 1541 */     invalidateTransform();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void shear(double paramDouble1, double paramDouble2) {
/* 1560 */     this.transform.shear(paramDouble1, paramDouble2);
/* 1561 */     invalidateTransform();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transform(AffineTransform paramAffineTransform) {
/* 1582 */     this.transform.concatenate(paramAffineTransform);
/* 1583 */     invalidateTransform();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void translate(int paramInt1, int paramInt2) {
/* 1590 */     this.transform.translate(paramInt1, paramInt2);
/* 1591 */     if (this.transformState <= 1) {
/* 1592 */       this.transX += paramInt1;
/* 1593 */       this.transY += paramInt2;
/* 1594 */       this.transformState = ((this.transX | this.transY) == 0) ? 0 : 1;
/*      */     } else {
/*      */       
/* 1597 */       invalidateTransform();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransform(AffineTransform paramAffineTransform) {
/* 1610 */     if ((this.constrainX | this.constrainY) == 0 && this.devScale == 1) {
/* 1611 */       this.transform.setTransform(paramAffineTransform);
/*      */     } else {
/* 1613 */       this.transform.setTransform(this.devScale, 0.0D, 0.0D, this.devScale, this.constrainX, this.constrainY);
/*      */       
/* 1615 */       this.transform.concatenate(paramAffineTransform);
/*      */     } 
/* 1617 */     invalidateTransform();
/*      */   }
/*      */   
/*      */   protected void invalidateTransform() {
/* 1621 */     int i = this.transform.getType();
/* 1622 */     int j = this.transformState;
/* 1623 */     if (i == 0) {
/* 1624 */       this.transformState = 0;
/* 1625 */       this.transX = this.transY = 0;
/* 1626 */     } else if (i == 1) {
/* 1627 */       double d1 = this.transform.getTranslateX();
/* 1628 */       double d2 = this.transform.getTranslateY();
/* 1629 */       this.transX = (int)Math.floor(d1 + 0.5D);
/* 1630 */       this.transY = (int)Math.floor(d2 + 0.5D);
/* 1631 */       if (d1 == this.transX && d2 == this.transY) {
/* 1632 */         this.transformState = 1;
/*      */       } else {
/* 1634 */         this.transformState = 2;
/*      */       } 
/* 1636 */     } else if ((i & 0x78) == 0) {
/*      */ 
/*      */ 
/*      */       
/* 1640 */       this.transformState = 3;
/* 1641 */       this.transX = this.transY = 0;
/*      */     } else {
/* 1643 */       this.transformState = 4;
/* 1644 */       this.transX = this.transY = 0;
/*      */     } 
/*      */     
/* 1647 */     if (this.transformState >= 3 || j >= 3) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1653 */       this.cachedFRC = null;
/* 1654 */       this.validFontInfo = false;
/* 1655 */       this.fontMetrics = null;
/* 1656 */       this.glyphVectorFontInfo = null;
/*      */       
/* 1658 */       if (this.transformState != j) {
/* 1659 */         invalidatePipe();
/*      */       }
/*      */     } 
/* 1662 */     if (this.strokeState != 3) {
/* 1663 */       validateBasicStroke((BasicStroke)this.stroke);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AffineTransform getTransform() {
/* 1674 */     if ((this.constrainX | this.constrainY) == 0 && this.devScale == 1) {
/* 1675 */       return new AffineTransform(this.transform);
/*      */     }
/* 1677 */     double d = 1.0D / this.devScale;
/* 1678 */     AffineTransform affineTransform = new AffineTransform(d, 0.0D, 0.0D, d, -this.constrainX * d, -this.constrainY * d);
/*      */ 
/*      */     
/* 1681 */     affineTransform.concatenate(this.transform);
/* 1682 */     return affineTransform;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AffineTransform cloneTransform() {
/* 1690 */     return new AffineTransform(this.transform);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getPaint() {
/* 1699 */     return this.paint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Composite getComposite() {
/* 1707 */     return this.composite;
/*      */   }
/*      */   
/*      */   public Color getColor() {
/* 1711 */     return this.foregroundColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void validateColor() {
/*      */     int i;
/* 1736 */     if (this.imageComp == CompositeType.Clear) {
/* 1737 */       i = 0;
/*      */     } else {
/* 1739 */       i = this.foregroundColor.getRGB();
/* 1740 */       if (this.compositeState <= 1 && this.imageComp != CompositeType.SrcNoEa && this.imageComp != CompositeType.SrcOverNoEa) {
/*      */ 
/*      */ 
/*      */         
/* 1744 */         AlphaComposite alphaComposite = (AlphaComposite)this.composite;
/* 1745 */         int j = Math.round(alphaComposite.getAlpha() * (i >>> 24));
/* 1746 */         i = i & 0xFFFFFF | j << 24;
/*      */       } 
/*      */     } 
/* 1749 */     this.eargb = i;
/* 1750 */     this.pixel = this.surfaceData.pixelFor(i);
/*      */   }
/*      */   
/*      */   public void setColor(Color paramColor) {
/* 1754 */     if (paramColor == null || paramColor == this.paint) {
/*      */       return;
/*      */     }
/* 1757 */     this.paint = this.foregroundColor = paramColor;
/* 1758 */     validateColor();
/* 1759 */     if (this.eargb >> 24 == -1) {
/* 1760 */       if (this.paintState == 0) {
/*      */         return;
/*      */       }
/* 1763 */       this.paintState = 0;
/* 1764 */       if (this.imageComp == CompositeType.SrcOverNoEa)
/*      */       {
/* 1766 */         this.compositeState = 0;
/*      */       }
/*      */     } else {
/* 1769 */       if (this.paintState == 1) {
/*      */         return;
/*      */       }
/* 1772 */       this.paintState = 1;
/* 1773 */       if (this.imageComp == CompositeType.SrcOverNoEa)
/*      */       {
/* 1775 */         this.compositeState = 1;
/*      */       }
/*      */     } 
/* 1778 */     this.validFontInfo = false;
/* 1779 */     invalidatePipe();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBackground(Color paramColor) {
/* 1795 */     this.backgroundColor = paramColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getBackground() {
/* 1803 */     return this.backgroundColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getStroke() {
/* 1811 */     return this.stroke;
/*      */   }
/*      */   
/*      */   public Rectangle getClipBounds() {
/* 1815 */     if (this.clipState == 0) {
/* 1816 */       return null;
/*      */     }
/* 1818 */     return getClipBounds(new Rectangle());
/*      */   }
/*      */   
/*      */   public Rectangle getClipBounds(Rectangle paramRectangle) {
/* 1822 */     if (this.clipState != 0) {
/* 1823 */       if (this.transformState <= 1) {
/* 1824 */         if (this.usrClip instanceof Rectangle) {
/* 1825 */           paramRectangle.setBounds((Rectangle)this.usrClip);
/*      */         } else {
/* 1827 */           paramRectangle.setFrame(this.usrClip.getBounds2D());
/*      */         } 
/* 1829 */         paramRectangle.translate(-this.transX, -this.transY);
/*      */       } else {
/* 1831 */         paramRectangle.setFrame(getClip().getBounds2D());
/*      */       } 
/* 1833 */     } else if (paramRectangle == null) {
/* 1834 */       throw new NullPointerException("null rectangle parameter");
/*      */     } 
/* 1836 */     return paramRectangle;
/*      */   }
/*      */   
/*      */   public boolean hitClip(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1840 */     if (paramInt3 <= 0 || paramInt4 <= 0) {
/* 1841 */       return false;
/*      */     }
/* 1843 */     if (this.transformState > 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1861 */       double[] arrayOfDouble = { paramInt1, paramInt2, (paramInt1 + paramInt3), paramInt2, paramInt1, (paramInt2 + paramInt4), (paramInt1 + paramInt3), (paramInt2 + paramInt4) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1867 */       this.transform.transform(arrayOfDouble, 0, arrayOfDouble, 0, 4);
/* 1868 */       paramInt1 = (int)Math.floor(Math.min(Math.min(arrayOfDouble[0], arrayOfDouble[2]), 
/* 1869 */             Math.min(arrayOfDouble[4], arrayOfDouble[6])));
/* 1870 */       paramInt2 = (int)Math.floor(Math.min(Math.min(arrayOfDouble[1], arrayOfDouble[3]), 
/* 1871 */             Math.min(arrayOfDouble[5], arrayOfDouble[7])));
/* 1872 */       paramInt3 = (int)Math.ceil(Math.max(Math.max(arrayOfDouble[0], arrayOfDouble[2]), 
/* 1873 */             Math.max(arrayOfDouble[4], arrayOfDouble[6])));
/* 1874 */       paramInt4 = (int)Math.ceil(Math.max(Math.max(arrayOfDouble[1], arrayOfDouble[3]), 
/* 1875 */             Math.max(arrayOfDouble[5], arrayOfDouble[7])));
/*      */     } else {
/* 1877 */       paramInt1 += this.transX;
/* 1878 */       paramInt2 += this.transY;
/* 1879 */       paramInt3 += paramInt1;
/* 1880 */       paramInt4 += paramInt2;
/*      */     } 
/*      */     
/*      */     try {
/* 1884 */       if (!getCompClip().intersectsQuickCheckXYXY(paramInt1, paramInt2, paramInt3, paramInt4)) {
/* 1885 */         return false;
/*      */       }
/* 1887 */     } catch (InvalidPipeException invalidPipeException) {
/* 1888 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1895 */     return true;
/*      */   }
/*      */   
/*      */   protected void validateCompClip() {
/* 1899 */     int i = this.clipState;
/* 1900 */     if (this.usrClip == null) {
/* 1901 */       this.clipState = 0;
/* 1902 */       this.clipRegion = this.devClip;
/* 1903 */     } else if (this.usrClip instanceof Rectangle2D) {
/* 1904 */       this.clipState = 1;
/* 1905 */       if (this.usrClip instanceof Rectangle) {
/* 1906 */         this.clipRegion = this.devClip.getIntersection((Rectangle)this.usrClip);
/*      */       } else {
/* 1908 */         this.clipRegion = this.devClip.getIntersection(this.usrClip.getBounds());
/*      */       } 
/*      */     } else {
/* 1911 */       PathIterator pathIterator = this.usrClip.getPathIterator(null);
/* 1912 */       int[] arrayOfInt = new int[4];
/* 1913 */       ShapeSpanIterator shapeSpanIterator = LoopPipe.getFillSSI(this);
/*      */       try {
/* 1915 */         shapeSpanIterator.setOutputArea(this.devClip);
/* 1916 */         shapeSpanIterator.appendPath(pathIterator);
/* 1917 */         shapeSpanIterator.getPathBox(arrayOfInt);
/* 1918 */         Region region = Region.getInstance(arrayOfInt);
/* 1919 */         region.appendSpans(shapeSpanIterator);
/* 1920 */         this.clipRegion = region;
/* 1921 */         this
/* 1922 */           .clipState = region.isRectangular() ? 1 : 2;
/*      */       } finally {
/* 1924 */         shapeSpanIterator.dispose();
/*      */       } 
/*      */     } 
/* 1927 */     if (i != this.clipState && (this.clipState == 2 || i == 2)) {
/*      */ 
/*      */       
/* 1930 */       this.validFontInfo = false;
/* 1931 */       invalidatePipe();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Shape transformShape(Shape paramShape) {
/* 1940 */     if (paramShape == null) {
/* 1941 */       return null;
/*      */     }
/* 1943 */     if (this.transformState > 1) {
/* 1944 */       return transformShape(this.transform, paramShape);
/*      */     }
/* 1946 */     return transformShape(this.transX, this.transY, paramShape);
/*      */   }
/*      */ 
/*      */   
/*      */   public Shape untransformShape(Shape paramShape) {
/* 1951 */     if (paramShape == null) {
/* 1952 */       return null;
/*      */     }
/* 1954 */     if (this.transformState > 1) {
/*      */       try {
/* 1956 */         return transformShape(this.transform.createInverse(), paramShape);
/* 1957 */       } catch (NoninvertibleTransformException noninvertibleTransformException) {
/* 1958 */         return null;
/*      */       } 
/*      */     }
/* 1961 */     return transformShape(-this.transX, -this.transY, paramShape);
/*      */   }
/*      */ 
/*      */   
/*      */   protected static Shape transformShape(int paramInt1, int paramInt2, Shape paramShape) {
/* 1966 */     if (paramShape == null) {
/* 1967 */       return null;
/*      */     }
/*      */     
/* 1970 */     if (paramShape instanceof Rectangle) {
/* 1971 */       Rectangle rectangle = paramShape.getBounds();
/* 1972 */       rectangle.translate(paramInt1, paramInt2);
/* 1973 */       return rectangle;
/*      */     } 
/* 1975 */     if (paramShape instanceof Rectangle2D) {
/* 1976 */       Rectangle2D rectangle2D = (Rectangle2D)paramShape;
/* 1977 */       return new Rectangle2D.Double(rectangle2D.getX() + paramInt1, rectangle2D
/* 1978 */           .getY() + paramInt2, rectangle2D
/* 1979 */           .getWidth(), rectangle2D
/* 1980 */           .getHeight());
/*      */     } 
/*      */     
/* 1983 */     if (paramInt1 == 0 && paramInt2 == 0) {
/* 1984 */       return cloneShape(paramShape);
/*      */     }
/*      */     
/* 1987 */     AffineTransform affineTransform = AffineTransform.getTranslateInstance(paramInt1, paramInt2);
/* 1988 */     return affineTransform.createTransformedShape(paramShape);
/*      */   }
/*      */   
/*      */   protected static Shape transformShape(AffineTransform paramAffineTransform, Shape paramShape) {
/* 1992 */     if (paramShape == null) {
/* 1993 */       return null;
/*      */     }
/*      */     
/* 1996 */     if (paramShape instanceof Rectangle2D && (paramAffineTransform
/* 1997 */       .getType() & 0x30) == 0) {
/*      */       
/* 1999 */       Rectangle2D rectangle2D = (Rectangle2D)paramShape;
/* 2000 */       double[] arrayOfDouble = new double[4];
/* 2001 */       arrayOfDouble[0] = rectangle2D.getX();
/* 2002 */       arrayOfDouble[1] = rectangle2D.getY();
/* 2003 */       arrayOfDouble[2] = arrayOfDouble[0] + rectangle2D.getWidth();
/* 2004 */       arrayOfDouble[3] = arrayOfDouble[1] + rectangle2D.getHeight();
/* 2005 */       paramAffineTransform.transform(arrayOfDouble, 0, arrayOfDouble, 0, 2);
/* 2006 */       fixRectangleOrientation(arrayOfDouble, rectangle2D);
/* 2007 */       return new Rectangle2D.Double(arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2] - arrayOfDouble[0], arrayOfDouble[3] - arrayOfDouble[1]);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2012 */     if (paramAffineTransform.isIdentity()) {
/* 2013 */       return cloneShape(paramShape);
/*      */     }
/*      */     
/* 2016 */     return paramAffineTransform.createTransformedShape(paramShape);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void fixRectangleOrientation(double[] paramArrayOfdouble, Rectangle2D paramRectangle2D) {
/* 2023 */     if (((paramRectangle2D.getWidth() > 0.0D) ? true : false) != ((paramArrayOfdouble[2] - paramArrayOfdouble[0] > 0.0D) ? true : false)) {
/* 2024 */       double d = paramArrayOfdouble[0];
/* 2025 */       paramArrayOfdouble[0] = paramArrayOfdouble[2];
/* 2026 */       paramArrayOfdouble[2] = d;
/*      */     } 
/* 2028 */     if (((paramRectangle2D.getHeight() > 0.0D) ? true : false) != ((paramArrayOfdouble[3] - paramArrayOfdouble[1] > 0.0D) ? true : false)) {
/* 2029 */       double d = paramArrayOfdouble[1];
/* 2030 */       paramArrayOfdouble[1] = paramArrayOfdouble[3];
/* 2031 */       paramArrayOfdouble[3] = d;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void clipRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 2036 */     clip(new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */   }
/*      */   
/*      */   public void setClip(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 2040 */     setClip(new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */   }
/*      */   
/*      */   public Shape getClip() {
/* 2044 */     return untransformShape(this.usrClip);
/*      */   }
/*      */   
/*      */   public void setClip(Shape paramShape) {
/* 2048 */     this.usrClip = transformShape(paramShape);
/* 2049 */     validateCompClip();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clip(Shape paramShape) {
/* 2061 */     paramShape = transformShape(paramShape);
/* 2062 */     if (this.usrClip != null) {
/* 2063 */       paramShape = intersectShapes(this.usrClip, paramShape, true, true);
/*      */     }
/* 2065 */     this.usrClip = paramShape;
/* 2066 */     validateCompClip();
/*      */   }
/*      */   
/*      */   public void setPaintMode() {
/* 2070 */     setComposite(AlphaComposite.SrcOver);
/*      */   }
/*      */   
/*      */   public void setXORMode(Color paramColor) {
/* 2074 */     if (paramColor == null) {
/* 2075 */       throw new IllegalArgumentException("null XORColor");
/*      */     }
/* 2077 */     setComposite(new XORComposite(paramColor, this.surfaceData));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyArea(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*      */     try {
/* 2085 */       doCopyArea(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/* 2086 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2088 */         revalidateAll();
/* 2089 */         doCopyArea(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/* 2090 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2096 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void doCopyArea(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 2101 */     if (paramInt3 <= 0 || paramInt4 <= 0) {
/*      */       return;
/*      */     }
/* 2104 */     SurfaceData surfaceData = this.surfaceData;
/* 2105 */     if (surfaceData.copyArea(this, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6)) {
/*      */       return;
/*      */     }
/* 2108 */     if (this.transformState > 3) {
/* 2109 */       throw new InternalError("transformed copyArea not implemented yet");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2114 */     Region region = getCompClip();
/*      */     
/* 2116 */     Composite composite = this.composite;
/* 2117 */     if (this.lastCAcomp != composite) {
/* 2118 */       SurfaceType surfaceType = surfaceData.getSurfaceType();
/* 2119 */       CompositeType compositeType = this.imageComp;
/* 2120 */       if (CompositeType.SrcOverNoEa.equals(compositeType) && surfaceData
/* 2121 */         .getTransparency() == 1)
/*      */       {
/* 2123 */         compositeType = CompositeType.SrcNoEa;
/*      */       }
/* 2125 */       this.lastCAblit = Blit.locate(surfaceType, compositeType, surfaceType);
/* 2126 */       this.lastCAcomp = composite;
/*      */     } 
/*      */     
/* 2129 */     double[] arrayOfDouble = { paramInt1, paramInt2, (paramInt1 + paramInt3), (paramInt2 + paramInt4), (paramInt1 + paramInt5), (paramInt2 + paramInt6) };
/* 2130 */     this.transform.transform(arrayOfDouble, 0, arrayOfDouble, 0, 3);
/*      */     
/* 2132 */     paramInt1 = (int)Math.ceil(arrayOfDouble[0] - 0.5D);
/* 2133 */     paramInt2 = (int)Math.ceil(arrayOfDouble[1] - 0.5D);
/* 2134 */     paramInt3 = (int)Math.ceil(arrayOfDouble[2] - 0.5D) - paramInt1;
/* 2135 */     paramInt4 = (int)Math.ceil(arrayOfDouble[3] - 0.5D) - paramInt2;
/* 2136 */     paramInt5 = (int)Math.ceil(arrayOfDouble[4] - 0.5D) - paramInt1;
/* 2137 */     paramInt6 = (int)Math.ceil(arrayOfDouble[5] - 0.5D) - paramInt2;
/*      */ 
/*      */     
/* 2140 */     if (paramInt3 < 0) {
/* 2141 */       paramInt3 *= -1;
/* 2142 */       paramInt1 -= paramInt3;
/*      */     } 
/* 2144 */     if (paramInt4 < 0) {
/* 2145 */       paramInt4 *= -1;
/* 2146 */       paramInt2 -= paramInt4;
/*      */     } 
/*      */     
/* 2149 */     Blit blit = this.lastCAblit;
/* 2150 */     if (paramInt6 == 0 && paramInt5 > 0 && paramInt5 < paramInt3) {
/* 2151 */       while (paramInt3 > 0) {
/* 2152 */         int i = Math.min(paramInt3, paramInt5);
/* 2153 */         paramInt3 -= i;
/* 2154 */         int j = paramInt1 + paramInt3;
/* 2155 */         blit.Blit(surfaceData, surfaceData, composite, region, j, paramInt2, j + paramInt5, paramInt2 + paramInt6, i, paramInt4);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 2160 */     if (paramInt6 > 0 && paramInt6 < paramInt4 && paramInt5 > -paramInt3 && paramInt5 < paramInt3) {
/* 2161 */       while (paramInt4 > 0) {
/* 2162 */         int i = Math.min(paramInt4, paramInt6);
/* 2163 */         paramInt4 -= i;
/* 2164 */         int j = paramInt2 + paramInt4;
/* 2165 */         blit.Blit(surfaceData, surfaceData, composite, region, paramInt1, j, paramInt1 + paramInt5, j + paramInt6, paramInt3, i);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 2170 */     blit.Blit(surfaceData, surfaceData, composite, region, paramInt1, paramInt2, paramInt1 + paramInt5, paramInt2 + paramInt6, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawLine(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*      */     try {
/* 2234 */       this.drawpipe.drawLine(this, paramInt1, paramInt2, paramInt3, paramInt4);
/* 2235 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2237 */         revalidateAll();
/* 2238 */         this.drawpipe.drawLine(this, paramInt1, paramInt2, paramInt3, paramInt4);
/* 2239 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2245 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void drawRoundRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*      */     try {
/* 2251 */       this.drawpipe.drawRoundRect(this, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/* 2252 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2254 */         revalidateAll();
/* 2255 */         this.drawpipe.drawRoundRect(this, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/* 2256 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2262 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void fillRoundRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*      */     try {
/* 2268 */       this.fillpipe.fillRoundRect(this, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/* 2269 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2271 */         revalidateAll();
/* 2272 */         this.fillpipe.fillRoundRect(this, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/* 2273 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2279 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void drawOval(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*      */     try {
/* 2285 */       this.drawpipe.drawOval(this, paramInt1, paramInt2, paramInt3, paramInt4);
/* 2286 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2288 */         revalidateAll();
/* 2289 */         this.drawpipe.drawOval(this, paramInt1, paramInt2, paramInt3, paramInt4);
/* 2290 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2296 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void fillOval(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*      */     try {
/* 2302 */       this.fillpipe.fillOval(this, paramInt1, paramInt2, paramInt3, paramInt4);
/* 2303 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2305 */         revalidateAll();
/* 2306 */         this.fillpipe.fillOval(this, paramInt1, paramInt2, paramInt3, paramInt4);
/* 2307 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2313 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawArc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*      */     try {
/* 2320 */       this.drawpipe.drawArc(this, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/* 2321 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2323 */         revalidateAll();
/* 2324 */         this.drawpipe.drawArc(this, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/* 2325 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2331 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void fillArc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*      */     try {
/* 2338 */       this.fillpipe.fillArc(this, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/* 2339 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2341 */         revalidateAll();
/* 2342 */         this.fillpipe.fillArc(this, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/* 2343 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2349 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void drawPolyline(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/*      */     try {
/* 2355 */       this.drawpipe.drawPolyline(this, paramArrayOfint1, paramArrayOfint2, paramInt);
/* 2356 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2358 */         revalidateAll();
/* 2359 */         this.drawpipe.drawPolyline(this, paramArrayOfint1, paramArrayOfint2, paramInt);
/* 2360 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2366 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void drawPolygon(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/*      */     try {
/* 2372 */       this.drawpipe.drawPolygon(this, paramArrayOfint1, paramArrayOfint2, paramInt);
/* 2373 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2375 */         revalidateAll();
/* 2376 */         this.drawpipe.drawPolygon(this, paramArrayOfint1, paramArrayOfint2, paramInt);
/* 2377 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2383 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void fillPolygon(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/*      */     try {
/* 2389 */       this.fillpipe.fillPolygon(this, paramArrayOfint1, paramArrayOfint2, paramInt);
/* 2390 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2392 */         revalidateAll();
/* 2393 */         this.fillpipe.fillPolygon(this, paramArrayOfint1, paramArrayOfint2, paramInt);
/* 2394 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2400 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void drawRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*      */     try {
/* 2406 */       this.drawpipe.drawRect(this, paramInt1, paramInt2, paramInt3, paramInt4);
/* 2407 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2409 */         revalidateAll();
/* 2410 */         this.drawpipe.drawRect(this, paramInt1, paramInt2, paramInt3, paramInt4);
/* 2411 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2417 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void fillRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*      */     try {
/* 2423 */       this.fillpipe.fillRect(this, paramInt1, paramInt2, paramInt3, paramInt4);
/* 2424 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2426 */         revalidateAll();
/* 2427 */         this.fillpipe.fillRect(this, paramInt1, paramInt2, paramInt3, paramInt4);
/* 2428 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2434 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void revalidateAll() {
/* 2445 */     this.surfaceData = this.surfaceData.getReplacement();
/* 2446 */     if (this.surfaceData == null) {
/* 2447 */       this.surfaceData = NullSurfaceData.theInstance;
/*      */     }
/*      */     
/* 2450 */     invalidatePipe();
/*      */ 
/*      */     
/* 2453 */     setDevClip(this.surfaceData.getBounds());
/*      */     
/* 2455 */     if (this.paintState <= 1) {
/* 2456 */       validateColor();
/*      */     }
/* 2458 */     if (this.composite instanceof XORComposite) {
/* 2459 */       Color color = ((XORComposite)this.composite).getXorColor();
/* 2460 */       setComposite(new XORComposite(color, this.surfaceData));
/*      */     } 
/* 2462 */     validatePipe();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 2472 */     Composite composite = this.composite;
/* 2473 */     Paint paint = this.paint;
/* 2474 */     setComposite(AlphaComposite.Src);
/* 2475 */     setColor(getBackground());
/* 2476 */     fillRect(paramInt1, paramInt2, paramInt3, paramInt4);
/* 2477 */     setPaint(paint);
/* 2478 */     setComposite(composite);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(Shape paramShape) {
/*      */     try {
/* 2497 */       this.shapepipe.draw(this, paramShape);
/* 2498 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2500 */         revalidateAll();
/* 2501 */         this.shapepipe.draw(this, paramShape);
/* 2502 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2508 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fill(Shape paramShape) {
/*      */     try {
/* 2527 */       this.shapepipe.fill(this, paramShape);
/* 2528 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2530 */         revalidateAll();
/* 2531 */         this.shapepipe.fill(this, paramShape);
/* 2532 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2538 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isIntegerTranslation(AffineTransform paramAffineTransform) {
/* 2547 */     if (paramAffineTransform.isIdentity()) {
/* 2548 */       return true;
/*      */     }
/* 2550 */     if (paramAffineTransform.getType() == 1) {
/* 2551 */       double d1 = paramAffineTransform.getTranslateX();
/* 2552 */       double d2 = paramAffineTransform.getTranslateY();
/* 2553 */       return (d1 == (int)d1 && d2 == (int)d2);
/*      */     } 
/* 2555 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getTileIndex(int paramInt1, int paramInt2, int paramInt3) {
/* 2563 */     paramInt1 -= paramInt2;
/* 2564 */     if (paramInt1 < 0) {
/* 2565 */       paramInt1 += 1 - paramInt3;
/*      */     }
/* 2567 */     return paramInt1 / paramInt3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Rectangle getImageRegion(RenderedImage paramRenderedImage, Region paramRegion, AffineTransform paramAffineTransform1, AffineTransform paramAffineTransform2, int paramInt1, int paramInt2) {
/* 2584 */     Rectangle rectangle1 = new Rectangle(paramRenderedImage.getMinX(), paramRenderedImage.getMinY(), paramRenderedImage.getWidth(), paramRenderedImage.getHeight());
/*      */     
/* 2586 */     Rectangle rectangle2 = null;
/*      */     try {
/* 2588 */       double[] arrayOfDouble = new double[8];
/* 2589 */       arrayOfDouble[2] = paramRegion.getLoX(); arrayOfDouble[0] = paramRegion.getLoX();
/* 2590 */       arrayOfDouble[6] = paramRegion.getHiX(); arrayOfDouble[4] = paramRegion.getHiX();
/* 2591 */       arrayOfDouble[5] = paramRegion.getLoY(); arrayOfDouble[1] = paramRegion.getLoY();
/* 2592 */       arrayOfDouble[7] = paramRegion.getHiY(); arrayOfDouble[3] = paramRegion.getHiY();
/*      */ 
/*      */       
/* 2595 */       paramAffineTransform1.inverseTransform(arrayOfDouble, 0, arrayOfDouble, 0, 4);
/* 2596 */       paramAffineTransform2.inverseTransform(arrayOfDouble, 0, arrayOfDouble, 0, 4);
/*      */ 
/*      */ 
/*      */       
/* 2600 */       double d2 = arrayOfDouble[0], d1 = d2;
/* 2601 */       double d4 = arrayOfDouble[1], d3 = d4;
/*      */       int i;
/* 2603 */       for (i = 2; i < 8; ) {
/* 2604 */         double d = arrayOfDouble[i++];
/* 2605 */         if (d < d1) {
/* 2606 */           d1 = d;
/* 2607 */         } else if (d > d2) {
/* 2608 */           d2 = d;
/*      */         } 
/* 2610 */         d = arrayOfDouble[i++];
/* 2611 */         if (d < d3) {
/* 2612 */           d3 = d; continue;
/* 2613 */         }  if (d > d4) {
/* 2614 */           d4 = d;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2620 */       i = (int)d1 - paramInt1;
/* 2621 */       int j = (int)(d2 - d1 + (2 * paramInt1));
/* 2622 */       int k = (int)d3 - paramInt2;
/* 2623 */       int m = (int)(d4 - d3 + (2 * paramInt2));
/*      */       
/* 2625 */       Rectangle rectangle = new Rectangle(i, k, j, m);
/* 2626 */       rectangle2 = rectangle.intersection(rectangle1);
/* 2627 */     } catch (NoninvertibleTransformException noninvertibleTransformException) {
/*      */       
/* 2629 */       rectangle2 = rectangle1;
/*      */     } 
/*      */     
/* 2632 */     return rectangle2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawRenderedImage(RenderedImage paramRenderedImage, AffineTransform paramAffineTransform) {
/*      */     Region region;
/* 2656 */     if (paramRenderedImage == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 2661 */     if (paramRenderedImage instanceof BufferedImage) {
/* 2662 */       BufferedImage bufferedImage1 = (BufferedImage)paramRenderedImage;
/* 2663 */       drawImage(bufferedImage1, paramAffineTransform, null);
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2672 */     boolean bool1 = (this.transformState <= 1 && isIntegerTranslation(paramAffineTransform)) ? true : false;
/*      */ 
/*      */     
/* 2675 */     boolean bool2 = bool1 ? false : true;
/*      */ 
/*      */     
/*      */     try {
/* 2679 */       region = getCompClip();
/* 2680 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2686 */     Rectangle rectangle = getImageRegion(paramRenderedImage, region, this.transform, paramAffineTransform, bool2, bool2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2691 */     if (rectangle.width <= 0 || rectangle.height <= 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2700 */     if (bool1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2707 */       drawTranslatedRenderedImage(paramRenderedImage, rectangle, 
/* 2708 */           (int)paramAffineTransform.getTranslateX(), 
/* 2709 */           (int)paramAffineTransform.getTranslateY());
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 2714 */     Raster raster = paramRenderedImage.getData(rectangle);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2720 */     WritableRaster writableRaster = Raster.createWritableRaster(raster.getSampleModel(), raster
/* 2721 */         .getDataBuffer(), null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2729 */     int i = raster.getMinX();
/* 2730 */     int j = raster.getMinY();
/* 2731 */     int k = raster.getWidth();
/* 2732 */     int m = raster.getHeight();
/* 2733 */     int n = i - raster.getSampleModelTranslateX();
/* 2734 */     int i1 = j - raster.getSampleModelTranslateY();
/* 2735 */     if (n != 0 || i1 != 0 || k != writableRaster.getWidth() || m != writableRaster
/* 2736 */       .getHeight())
/*      */     {
/* 2738 */       writableRaster = writableRaster.createWritableChild(n, i1, k, m, 0, 0, (int[])null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2750 */     AffineTransform affineTransform = (AffineTransform)paramAffineTransform.clone();
/* 2751 */     affineTransform.translate(i, j);
/*      */     
/* 2753 */     ColorModel colorModel = paramRenderedImage.getColorModel();
/*      */ 
/*      */     
/* 2756 */     BufferedImage bufferedImage = new BufferedImage(colorModel, writableRaster, colorModel.isAlphaPremultiplied(), null);
/*      */     
/* 2758 */     drawImage(bufferedImage, affineTransform, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean clipTo(Rectangle paramRectangle1, Rectangle paramRectangle2) {
/* 2767 */     int i = Math.max(paramRectangle1.x, paramRectangle2.x);
/* 2768 */     int j = Math.min(paramRectangle1.x + paramRectangle1.width, paramRectangle2.x + paramRectangle2.width);
/* 2769 */     int k = Math.max(paramRectangle1.y, paramRectangle2.y);
/* 2770 */     int m = Math.min(paramRectangle1.y + paramRectangle1.height, paramRectangle2.y + paramRectangle2.height);
/* 2771 */     if (j - i < 0 || m - k < 0) {
/* 2772 */       paramRectangle1.width = -1;
/* 2773 */       paramRectangle1.height = -1;
/* 2774 */       return false;
/*      */     } 
/* 2776 */     paramRectangle1.x = i;
/* 2777 */     paramRectangle1.y = k;
/* 2778 */     paramRectangle1.width = j - i;
/* 2779 */     paramRectangle1.height = m - k;
/* 2780 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawTranslatedRenderedImage(RenderedImage paramRenderedImage, Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 2794 */     int i = paramRenderedImage.getTileGridXOffset();
/* 2795 */     int j = paramRenderedImage.getTileGridYOffset();
/* 2796 */     int k = paramRenderedImage.getTileWidth();
/* 2797 */     int m = paramRenderedImage.getTileHeight();
/*      */ 
/*      */ 
/*      */     
/* 2801 */     int n = getTileIndex(paramRectangle.x, i, k);
/*      */     
/* 2803 */     int i1 = getTileIndex(paramRectangle.y, j, m);
/*      */     
/* 2805 */     int i2 = getTileIndex(paramRectangle.x + paramRectangle.width - 1, i, k);
/*      */ 
/*      */     
/* 2808 */     int i3 = getTileIndex(paramRectangle.y + paramRectangle.height - 1, j, m);
/*      */ 
/*      */ 
/*      */     
/* 2812 */     ColorModel colorModel = paramRenderedImage.getColorModel();
/*      */ 
/*      */     
/* 2815 */     Rectangle rectangle = new Rectangle();
/*      */     
/* 2817 */     for (int i4 = i1; i4 <= i3; i4++) {
/* 2818 */       for (int i5 = n; i5 <= i2; i5++) {
/*      */         
/* 2820 */         Raster raster = paramRenderedImage.getTile(i5, i4);
/*      */ 
/*      */         
/* 2823 */         rectangle.x = i5 * k + i;
/* 2824 */         rectangle.y = i4 * m + j;
/* 2825 */         rectangle.width = k;
/* 2826 */         rectangle.height = m;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2831 */         clipTo(rectangle, paramRectangle);
/*      */ 
/*      */         
/* 2834 */         WritableRaster writableRaster = null;
/* 2835 */         if (raster instanceof WritableRaster) {
/* 2836 */           writableRaster = (WritableRaster)raster;
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 2841 */           writableRaster = Raster.createWritableRaster(raster.getSampleModel(), raster
/* 2842 */               .getDataBuffer(), null);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2848 */         writableRaster = writableRaster.createWritableChild(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 0, 0, (int[])null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2858 */         BufferedImage bufferedImage = new BufferedImage(colorModel, writableRaster, colorModel.isAlphaPremultiplied(), null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2866 */         copyImage(bufferedImage, rectangle.x + paramInt1, rectangle.y + paramInt2, 0, 0, rectangle.width, rectangle.height, null, null);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawRenderableImage(RenderableImage paramRenderableImage, AffineTransform paramAffineTransform) {
/*      */     AffineTransform affineTransform3;
/* 2876 */     if (paramRenderableImage == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2880 */     AffineTransform affineTransform1 = this.transform;
/* 2881 */     AffineTransform affineTransform2 = new AffineTransform(paramAffineTransform);
/* 2882 */     affineTransform2.concatenate(affineTransform1);
/*      */ 
/*      */     
/* 2885 */     RenderContext renderContext = new RenderContext(affineTransform2);
/*      */     
/*      */     try {
/* 2888 */       affineTransform3 = affineTransform1.createInverse();
/* 2889 */     } catch (NoninvertibleTransformException noninvertibleTransformException) {
/* 2890 */       renderContext = new RenderContext(affineTransform1);
/* 2891 */       affineTransform3 = new AffineTransform();
/*      */     } 
/*      */     
/* 2894 */     RenderedImage renderedImage = paramRenderableImage.createRendering(renderContext);
/* 2895 */     drawRenderedImage(renderedImage, affineTransform3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rectangle transformBounds(Rectangle paramRectangle, AffineTransform paramAffineTransform) {
/* 2905 */     if (paramAffineTransform.isIdentity()) {
/* 2906 */       return paramRectangle;
/*      */     }
/*      */     
/* 2909 */     Shape shape = transformShape(paramAffineTransform, paramRectangle);
/* 2910 */     return shape.getBounds();
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawString(String paramString, int paramInt1, int paramInt2) {
/* 2915 */     if (paramString == null) {
/* 2916 */       throw new NullPointerException("String is null");
/*      */     }
/*      */     
/* 2919 */     if (this.font.hasLayoutAttributes()) {
/* 2920 */       if (paramString.length() == 0) {
/*      */         return;
/*      */       }
/* 2923 */       (new TextLayout(paramString, this.font, getFontRenderContext())).draw(this, paramInt1, paramInt2);
/*      */       
/*      */       return;
/*      */     } 
/*      */     try {
/* 2928 */       this.textpipe.drawString(this, paramString, paramInt1, paramInt2);
/* 2929 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2931 */         revalidateAll();
/* 2932 */         this.textpipe.drawString(this, paramString, paramInt1, paramInt2);
/* 2933 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2939 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void drawString(String paramString, float paramFloat1, float paramFloat2) {
/* 2944 */     if (paramString == null) {
/* 2945 */       throw new NullPointerException("String is null");
/*      */     }
/*      */     
/* 2948 */     if (this.font.hasLayoutAttributes()) {
/* 2949 */       if (paramString.length() == 0) {
/*      */         return;
/*      */       }
/* 2952 */       (new TextLayout(paramString, this.font, getFontRenderContext())).draw(this, paramFloat1, paramFloat2);
/*      */       
/*      */       return;
/*      */     } 
/*      */     try {
/* 2957 */       this.textpipe.drawString(this, paramString, paramFloat1, paramFloat2);
/* 2958 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 2960 */         revalidateAll();
/* 2961 */         this.textpipe.drawString(this, paramString, paramFloat1, paramFloat2);
/* 2962 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 2968 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawString(AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt1, int paramInt2) {
/* 2974 */     if (paramAttributedCharacterIterator == null) {
/* 2975 */       throw new NullPointerException("AttributedCharacterIterator is null");
/*      */     }
/* 2977 */     if (paramAttributedCharacterIterator.getBeginIndex() == paramAttributedCharacterIterator.getEndIndex()) {
/*      */       return;
/*      */     }
/* 2980 */     TextLayout textLayout = new TextLayout(paramAttributedCharacterIterator, getFontRenderContext());
/* 2981 */     textLayout.draw(this, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawString(AttributedCharacterIterator paramAttributedCharacterIterator, float paramFloat1, float paramFloat2) {
/* 2986 */     if (paramAttributedCharacterIterator == null) {
/* 2987 */       throw new NullPointerException("AttributedCharacterIterator is null");
/*      */     }
/* 2989 */     if (paramAttributedCharacterIterator.getBeginIndex() == paramAttributedCharacterIterator.getEndIndex()) {
/*      */       return;
/*      */     }
/* 2992 */     TextLayout textLayout = new TextLayout(paramAttributedCharacterIterator, getFontRenderContext());
/* 2993 */     textLayout.draw(this, paramFloat1, paramFloat2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawGlyphVector(GlyphVector paramGlyphVector, float paramFloat1, float paramFloat2) {
/* 2998 */     if (paramGlyphVector == null) {
/* 2999 */       throw new NullPointerException("GlyphVector is null");
/*      */     }
/*      */     
/*      */     try {
/* 3003 */       this.textpipe.drawGlyphVector(this, paramGlyphVector, paramFloat1, paramFloat2);
/* 3004 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 3006 */         revalidateAll();
/* 3007 */         this.textpipe.drawGlyphVector(this, paramGlyphVector, paramFloat1, paramFloat2);
/* 3008 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 3014 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawChars(char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 3020 */     if (paramArrayOfchar == null) {
/* 3021 */       throw new NullPointerException("char data is null");
/*      */     }
/* 3023 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 < paramInt2 || paramInt1 + paramInt2 > paramArrayOfchar.length)
/*      */     {
/* 3025 */       throw new ArrayIndexOutOfBoundsException("bad offset/length");
/*      */     }
/* 3027 */     if (this.font.hasLayoutAttributes()) {
/* 3028 */       if (paramArrayOfchar.length == 0) {
/*      */         return;
/*      */       }
/* 3031 */       (new TextLayout(new String(paramArrayOfchar, paramInt1, paramInt2), this.font, 
/* 3032 */           getFontRenderContext())).draw(this, paramInt3, paramInt4);
/*      */       
/*      */       return;
/*      */     } 
/*      */     try {
/* 3037 */       this.textpipe.drawChars(this, paramArrayOfchar, paramInt1, paramInt2, paramInt3, paramInt4);
/* 3038 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 3040 */         revalidateAll();
/* 3041 */         this.textpipe.drawChars(this, paramArrayOfchar, paramInt1, paramInt2, paramInt3, paramInt4);
/* 3042 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 3048 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void drawBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 3053 */     if (paramArrayOfbyte == null) {
/* 3054 */       throw new NullPointerException("byte data is null");
/*      */     }
/* 3056 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 < paramInt2 || paramInt1 + paramInt2 > paramArrayOfbyte.length)
/*      */     {
/* 3058 */       throw new ArrayIndexOutOfBoundsException("bad offset/length");
/*      */     }
/*      */     
/* 3061 */     char[] arrayOfChar = new char[paramInt2];
/* 3062 */     for (int i = paramInt2; i-- > 0;) {
/* 3063 */       arrayOfChar[i] = (char)(paramArrayOfbyte[i + paramInt1] & 0xFF);
/*      */     }
/* 3065 */     if (this.font.hasLayoutAttributes()) {
/* 3066 */       if (paramArrayOfbyte.length == 0) {
/*      */         return;
/*      */       }
/* 3069 */       (new TextLayout(new String(arrayOfChar), this.font, 
/* 3070 */           getFontRenderContext())).draw(this, paramInt3, paramInt4);
/*      */       
/*      */       return;
/*      */     } 
/*      */     try {
/* 3075 */       this.textpipe.drawChars(this, arrayOfChar, 0, paramInt2, paramInt3, paramInt4);
/* 3076 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 3078 */         revalidateAll();
/* 3079 */         this.textpipe.drawChars(this, arrayOfChar, 0, paramInt2, paramInt3, paramInt4);
/* 3080 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 3086 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isHiDPIImage(Image paramImage) {
/* 3092 */     return (SurfaceManager.getImageScale(paramImage) != 1 || (this.resolutionVariantHint != 1 && paramImage instanceof MultiResolutionImage));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean drawHiDPIImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Color paramColor, ImageObserver paramImageObserver) {
/* 3101 */     if (SurfaceManager.getImageScale(paramImage) != 1) {
/* 3102 */       int i = SurfaceManager.getImageScale(paramImage);
/* 3103 */       paramInt5 = Region.clipScale(paramInt5, i);
/* 3104 */       paramInt7 = Region.clipScale(paramInt7, i);
/* 3105 */       paramInt6 = Region.clipScale(paramInt6, i);
/* 3106 */       paramInt8 = Region.clipScale(paramInt8, i);
/* 3107 */     } else if (paramImage instanceof MultiResolutionImage) {
/*      */ 
/*      */       
/* 3110 */       int i = paramImage.getWidth(paramImageObserver);
/* 3111 */       int j = paramImage.getHeight(paramImageObserver);
/*      */       
/* 3113 */       Image image = getResolutionVariant((MultiResolutionImage)paramImage, i, j, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8);
/*      */ 
/*      */ 
/*      */       
/* 3117 */       if (image != paramImage && image != null) {
/*      */ 
/*      */ 
/*      */         
/* 3121 */         ImageObserver imageObserver = MultiResolutionToolkitImage.getResolutionVariantObserver(paramImage, paramImageObserver, i, j, -1, -1);
/*      */ 
/*      */         
/* 3124 */         int k = image.getWidth(imageObserver);
/* 3125 */         int m = image.getHeight(imageObserver);
/*      */         
/* 3127 */         if (0 < i && 0 < j && 0 < k && 0 < m) {
/*      */           
/* 3129 */           float f1 = k / i;
/* 3130 */           float f2 = m / j;
/*      */           
/* 3132 */           paramInt5 = Region.clipScale(paramInt5, f1);
/* 3133 */           paramInt6 = Region.clipScale(paramInt6, f2);
/* 3134 */           paramInt7 = Region.clipScale(paramInt7, f1);
/* 3135 */           paramInt8 = Region.clipScale(paramInt8, f2);
/*      */           
/* 3137 */           paramImageObserver = imageObserver;
/* 3138 */           paramImage = image;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*      */     try {
/* 3144 */       return this.imagepipe.scaleImage(this, paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramColor, paramImageObserver);
/*      */     }
/* 3146 */     catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 3148 */         revalidateAll();
/* 3149 */         return this.imagepipe.scaleImage(this, paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramColor, paramImageObserver);
/*      */       }
/* 3151 */       catch (InvalidPipeException invalidPipeException1) {
/*      */ 
/*      */ 
/*      */         
/* 3155 */         return false;
/*      */       } 
/*      */     } finally {
/* 3158 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Image getResolutionVariant(MultiResolutionImage paramMultiResolutionImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10) {
/*      */     double d1, d2;
/* 3166 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/* 3167 */       return null;
/*      */     }
/*      */     
/* 3170 */     int i = paramInt9 - paramInt7;
/* 3171 */     int j = paramInt10 - paramInt8;
/*      */     
/* 3173 */     if (i == 0 || j == 0) {
/* 3174 */       return null;
/*      */     }
/*      */     
/* 3177 */     int k = this.transform.getType();
/* 3178 */     int m = paramInt5 - paramInt3;
/* 3179 */     int n = paramInt6 - paramInt4;
/*      */ 
/*      */ 
/*      */     
/* 3183 */     if ((k & 0xFFFFFFBE) == 0) {
/* 3184 */       d1 = m;
/* 3185 */       d2 = n;
/* 3186 */     } else if ((k & 0xFFFFFFB8) == 0) {
/* 3187 */       d1 = m * this.transform.getScaleX();
/* 3188 */       d2 = n * this.transform.getScaleY();
/*      */     } else {
/* 3190 */       d1 = m * Math.hypot(this.transform
/* 3191 */           .getScaleX(), this.transform.getShearY());
/* 3192 */       d2 = n * Math.hypot(this.transform
/* 3193 */           .getShearX(), this.transform.getScaleY());
/*      */     } 
/*      */     
/* 3196 */     int i1 = (int)Math.abs(paramInt1 * d1 / i);
/* 3197 */     int i2 = (int)Math.abs(paramInt2 * d2 / j);
/*      */ 
/*      */     
/* 3200 */     Image image = paramMultiResolutionImage.getResolutionVariant(i1, i2);
/*      */     
/* 3202 */     if (image instanceof ToolkitImage && ((ToolkitImage)image)
/* 3203 */       .hasError()) {
/* 3204 */       return null;
/*      */     }
/*      */     
/* 3207 */     return image;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, ImageObserver paramImageObserver) {
/* 3216 */     return drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, null, paramImageObserver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean copyImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Color paramColor, ImageObserver paramImageObserver) {
/*      */     try {
/* 3231 */       return this.imagepipe.copyImage(this, paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramColor, paramImageObserver);
/*      */     }
/* 3233 */     catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 3235 */         revalidateAll();
/* 3236 */         return this.imagepipe.copyImage(this, paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramColor, paramImageObserver);
/*      */       }
/* 3238 */       catch (InvalidPipeException invalidPipeException1) {
/*      */ 
/*      */ 
/*      */         
/* 3242 */         return false;
/*      */       } 
/*      */     } finally {
/* 3245 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor, ImageObserver paramImageObserver) {
/* 3256 */     if (paramImage == null) {
/* 3257 */       return true;
/*      */     }
/*      */     
/* 3260 */     if (paramInt3 == 0 || paramInt4 == 0) {
/* 3261 */       return true;
/*      */     }
/*      */     
/* 3264 */     int i = paramImage.getWidth(null);
/* 3265 */     int j = paramImage.getHeight(null);
/* 3266 */     if (isHiDPIImage(paramImage)) {
/* 3267 */       return drawHiDPIImage(paramImage, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4, 0, 0, i, j, paramColor, paramImageObserver);
/*      */     }
/*      */ 
/*      */     
/* 3271 */     if (paramInt3 == i && paramInt4 == j) {
/* 3272 */       return copyImage(paramImage, paramInt1, paramInt2, 0, 0, paramInt3, paramInt4, paramColor, paramImageObserver);
/*      */     }
/*      */     
/*      */     try {
/* 3276 */       return this.imagepipe.scaleImage(this, paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramColor, paramImageObserver);
/*      */     }
/* 3278 */     catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 3280 */         revalidateAll();
/* 3281 */         return this.imagepipe.scaleImage(this, paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramColor, paramImageObserver);
/*      */       }
/* 3283 */       catch (InvalidPipeException invalidPipeException1) {
/*      */ 
/*      */ 
/*      */         
/* 3287 */         return false;
/*      */       } 
/*      */     } finally {
/* 3290 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) {
/* 3298 */     return drawImage(paramImage, paramInt1, paramInt2, null, paramImageObserver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, Color paramColor, ImageObserver paramImageObserver) {
/* 3308 */     if (paramImage == null) {
/* 3309 */       return true;
/*      */     }
/*      */     
/* 3312 */     if (isHiDPIImage(paramImage)) {
/* 3313 */       int i = paramImage.getWidth(null);
/* 3314 */       int j = paramImage.getHeight(null);
/* 3315 */       return drawHiDPIImage(paramImage, paramInt1, paramInt2, paramInt1 + i, paramInt2 + j, 0, 0, i, j, paramColor, paramImageObserver);
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 3320 */       return this.imagepipe.copyImage(this, paramImage, paramInt1, paramInt2, paramColor, paramImageObserver);
/* 3321 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 3323 */         revalidateAll();
/* 3324 */         return this.imagepipe.copyImage(this, paramImage, paramInt1, paramInt2, paramColor, paramImageObserver);
/* 3325 */       } catch (InvalidPipeException invalidPipeException1) {
/*      */ 
/*      */ 
/*      */         
/* 3329 */         return false;
/*      */       } 
/*      */     } finally {
/* 3332 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, ImageObserver paramImageObserver) {
/* 3344 */     return drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, null, paramImageObserver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Color paramColor, ImageObserver paramImageObserver) {
/* 3357 */     if (paramImage == null) {
/* 3358 */       return true;
/*      */     }
/*      */     
/* 3361 */     if (paramInt1 == paramInt3 || paramInt2 == paramInt4 || paramInt5 == paramInt7 || paramInt6 == paramInt8)
/*      */     {
/*      */       
/* 3364 */       return true;
/*      */     }
/*      */     
/* 3367 */     if (isHiDPIImage(paramImage)) {
/* 3368 */       return drawHiDPIImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramColor, paramImageObserver);
/*      */     }
/*      */ 
/*      */     
/* 3372 */     if (paramInt7 - paramInt5 == paramInt3 - paramInt1 && paramInt8 - paramInt6 == paramInt4 - paramInt2) {
/*      */       int i; int j; int k;
/*      */       int m;
/*      */       int n;
/*      */       int i1;
/* 3377 */       if (paramInt7 > paramInt5) {
/* 3378 */         n = paramInt7 - paramInt5;
/* 3379 */         i = paramInt5;
/* 3380 */         k = paramInt1;
/*      */       } else {
/* 3382 */         n = paramInt5 - paramInt7;
/* 3383 */         i = paramInt7;
/* 3384 */         k = paramInt3;
/*      */       } 
/* 3386 */       if (paramInt8 > paramInt6) {
/* 3387 */         i1 = paramInt8 - paramInt6;
/* 3388 */         j = paramInt6;
/* 3389 */         m = paramInt2;
/*      */       } else {
/* 3391 */         i1 = paramInt6 - paramInt8;
/* 3392 */         j = paramInt8;
/* 3393 */         m = paramInt4;
/*      */       } 
/* 3395 */       return copyImage(paramImage, k, m, i, j, n, i1, paramColor, paramImageObserver);
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 3400 */       return this.imagepipe.scaleImage(this, paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramColor, paramImageObserver);
/*      */     
/*      */     }
/* 3403 */     catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 3405 */         revalidateAll();
/* 3406 */         return this.imagepipe.scaleImage(this, paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramColor, paramImageObserver);
/*      */       
/*      */       }
/* 3409 */       catch (InvalidPipeException invalidPipeException1) {
/*      */ 
/*      */ 
/*      */         
/* 3413 */         return false;
/*      */       } 
/*      */     } finally {
/* 3416 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean drawImage(Image paramImage, AffineTransform paramAffineTransform, ImageObserver paramImageObserver) {
/* 3442 */     if (paramImage == null) {
/* 3443 */       return true;
/*      */     }
/*      */     
/* 3446 */     if (paramAffineTransform == null || paramAffineTransform.isIdentity()) {
/* 3447 */       return drawImage(paramImage, 0, 0, null, paramImageObserver);
/*      */     }
/*      */     
/* 3450 */     if (isHiDPIImage(paramImage)) {
/* 3451 */       int i = paramImage.getWidth(null);
/* 3452 */       int j = paramImage.getHeight(null);
/* 3453 */       AffineTransform affineTransform = new AffineTransform(this.transform);
/* 3454 */       transform(paramAffineTransform);
/* 3455 */       boolean bool = drawHiDPIImage(paramImage, 0, 0, i, j, 0, 0, i, j, null, paramImageObserver);
/*      */       
/* 3457 */       this.transform.setTransform(affineTransform);
/* 3458 */       invalidateTransform();
/* 3459 */       return bool;
/*      */     } 
/*      */     
/*      */     try {
/* 3463 */       return this.imagepipe.transformImage(this, paramImage, paramAffineTransform, paramImageObserver);
/* 3464 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 3466 */         revalidateAll();
/* 3467 */         return this.imagepipe.transformImage(this, paramImage, paramAffineTransform, paramImageObserver);
/* 3468 */       } catch (InvalidPipeException invalidPipeException1) {
/*      */ 
/*      */ 
/*      */         
/* 3472 */         return false;
/*      */       } 
/*      */     } finally {
/* 3475 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawImage(BufferedImage paramBufferedImage, BufferedImageOp paramBufferedImageOp, int paramInt1, int paramInt2) {
/* 3484 */     if (paramBufferedImage == null) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/* 3489 */       this.imagepipe.transformImage(this, paramBufferedImage, paramBufferedImageOp, paramInt1, paramInt2);
/* 3490 */     } catch (InvalidPipeException invalidPipeException) {
/*      */       try {
/* 3492 */         revalidateAll();
/* 3493 */         this.imagepipe.transformImage(this, paramBufferedImage, paramBufferedImageOp, paramInt1, paramInt2);
/* 3494 */       } catch (InvalidPipeException invalidPipeException1) {}
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/* 3500 */       this.surfaceData.markDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FontRenderContext getFontRenderContext() {
/* 3509 */     if (this.cachedFRC == null) {
/* 3510 */       int i = this.textAntialiasHint;
/* 3511 */       if (i == 0 && this.antialiasHint == 2)
/*      */       {
/* 3513 */         i = 2;
/*      */       }
/*      */       
/* 3516 */       AffineTransform affineTransform = null;
/* 3517 */       if (this.transformState >= 3) {
/* 3518 */         if (this.transform.getTranslateX() == 0.0D && this.transform
/* 3519 */           .getTranslateY() == 0.0D) {
/* 3520 */           affineTransform = this.transform;
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 3525 */           affineTransform = new AffineTransform(this.transform.getScaleX(), this.transform.getShearY(), this.transform.getShearX(), this.transform.getScaleY(), 0.0D, 0.0D);
/*      */         } 
/*      */       }
/*      */       
/* 3529 */       this
/*      */         
/* 3531 */         .cachedFRC = new FontRenderContext(affineTransform, SunHints.Value.get(2, i), SunHints.Value.get(3, this.fractionalMetricsHint));
/*      */     } 
/*      */     
/* 3534 */     return this.cachedFRC;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dispose() {
/* 3548 */     this.surfaceData = NullSurfaceData.theInstance;
/* 3549 */     invalidatePipe();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void finalize() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getDestination() {
/* 3573 */     return this.surfaceData.getDestination();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Surface getDestSurface() {
/* 3583 */     return this.surfaceData;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/SunGraphics2D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */