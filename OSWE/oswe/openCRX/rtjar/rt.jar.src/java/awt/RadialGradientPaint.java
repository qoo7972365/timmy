/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.beans.ConstructorProperties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RadialGradientPaint
/*     */   extends MultipleGradientPaint
/*     */ {
/*     */   private final Point2D focus;
/*     */   private final Point2D center;
/*     */   private final float radius;
/*     */   
/*     */   public RadialGradientPaint(float paramFloat1, float paramFloat2, float paramFloat3, float[] paramArrayOffloat, Color[] paramArrayOfColor) {
/* 195 */     this(paramFloat1, paramFloat2, paramFloat3, paramFloat1, paramFloat2, paramArrayOffloat, paramArrayOfColor, MultipleGradientPaint.CycleMethod.NO_CYCLE);
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
/*     */   public RadialGradientPaint(Point2D paramPoint2D, float paramFloat, float[] paramArrayOffloat, Color[] paramArrayOfColor) {
/* 232 */     this(paramPoint2D, paramFloat, paramPoint2D, paramArrayOffloat, paramArrayOfColor, MultipleGradientPaint.CycleMethod.NO_CYCLE);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public RadialGradientPaint(float paramFloat1, float paramFloat2, float paramFloat3, float[] paramArrayOffloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod) {
/* 275 */     this(paramFloat1, paramFloat2, paramFloat3, paramFloat1, paramFloat2, paramArrayOffloat, paramArrayOfColor, paramCycleMethod);
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
/*     */   public RadialGradientPaint(Point2D paramPoint2D, float paramFloat, float[] paramArrayOffloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod) {
/* 315 */     this(paramPoint2D, paramFloat, paramPoint2D, paramArrayOffloat, paramArrayOfColor, paramCycleMethod);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RadialGradientPaint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float[] paramArrayOffloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod) {
/* 363 */     this(new Point2D.Float(paramFloat1, paramFloat2), paramFloat3, new Point2D.Float(paramFloat4, paramFloat5), paramArrayOffloat, paramArrayOfColor, paramCycleMethod);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public RadialGradientPaint(Point2D paramPoint2D1, float paramFloat, Point2D paramPoint2D2, float[] paramArrayOffloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod) {
/* 406 */     this(paramPoint2D1, paramFloat, paramPoint2D2, paramArrayOffloat, paramArrayOfColor, paramCycleMethod, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ConstructorProperties({"centerPoint", "radius", "focusPoint", "fractions", "colors", "cycleMethod", "colorSpace", "transform"})
/*     */   public RadialGradientPaint(Point2D paramPoint2D1, float paramFloat, Point2D paramPoint2D2, float[] paramArrayOffloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod, MultipleGradientPaint.ColorSpaceType paramColorSpaceType, AffineTransform paramAffineTransform) {
/* 459 */     super(paramArrayOffloat, paramArrayOfColor, paramCycleMethod, paramColorSpaceType, paramAffineTransform);
/*     */ 
/*     */     
/* 462 */     if (paramPoint2D1 == null) {
/* 463 */       throw new NullPointerException("Center point must be non-null");
/*     */     }
/*     */     
/* 466 */     if (paramPoint2D2 == null) {
/* 467 */       throw new NullPointerException("Focus point must be non-null");
/*     */     }
/*     */     
/* 470 */     if (paramFloat <= 0.0F) {
/* 471 */       throw new IllegalArgumentException("Radius must be greater than zero");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 476 */     this.center = new Point2D.Double(paramPoint2D1.getX(), paramPoint2D1.getY());
/* 477 */     this.focus = new Point2D.Double(paramPoint2D2.getX(), paramPoint2D2.getY());
/* 478 */     this.radius = paramFloat;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RadialGradientPaint(Rectangle2D paramRectangle2D, float[] paramArrayOffloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod) {
/* 539 */     this(new Point2D.Double(paramRectangle2D.getCenterX(), paramRectangle2D
/* 540 */           .getCenterY()), 1.0F, new Point2D.Double(paramRectangle2D
/*     */           
/* 542 */           .getCenterX(), paramRectangle2D
/* 543 */           .getCenterY()), paramArrayOffloat, paramArrayOfColor, paramCycleMethod, MultipleGradientPaint.ColorSpaceType.SRGB, 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 548 */         createGradientTransform(paramRectangle2D));
/*     */     
/* 550 */     if (paramRectangle2D.isEmpty()) {
/* 551 */       throw new IllegalArgumentException("Gradient bounds must be non-empty");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static AffineTransform createGradientTransform(Rectangle2D paramRectangle2D) {
/* 557 */     double d1 = paramRectangle2D.getCenterX();
/* 558 */     double d2 = paramRectangle2D.getCenterY();
/* 559 */     AffineTransform affineTransform = AffineTransform.getTranslateInstance(d1, d2);
/* 560 */     affineTransform.scale(paramRectangle2D.getWidth() / 2.0D, paramRectangle2D.getHeight() / 2.0D);
/* 561 */     affineTransform.translate(-d1, -d2);
/* 562 */     return affineTransform;
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
/*     */   public PaintContext createContext(ColorModel paramColorModel, Rectangle paramRectangle, Rectangle2D paramRectangle2D, AffineTransform paramAffineTransform, RenderingHints paramRenderingHints) {
/* 599 */     paramAffineTransform = new AffineTransform(paramAffineTransform);
/*     */     
/* 601 */     paramAffineTransform.concatenate(this.gradientTransform);
/*     */     
/* 603 */     return new RadialGradientPaintContext(this, paramColorModel, paramRectangle, paramRectangle2D, paramAffineTransform, paramRenderingHints, 
/*     */ 
/*     */         
/* 606 */         (float)this.center.getX(), 
/* 607 */         (float)this.center.getY(), this.radius, 
/*     */         
/* 609 */         (float)this.focus.getX(), 
/* 610 */         (float)this.focus.getY(), this.fractions, this.colors, this.cycleMethod, this.colorSpace);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2D getCenterPoint() {
/* 621 */     return new Point2D.Double(this.center.getX(), this.center.getY());
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
/*     */   public Point2D getFocusPoint() {
/* 635 */     return new Point2D.Double(this.focus.getX(), this.focus.getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRadius() {
/* 644 */     return this.radius;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/RadialGradientPaint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */