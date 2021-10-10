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
/*     */ public final class LinearGradientPaint
/*     */   extends MultipleGradientPaint
/*     */ {
/*     */   private final Point2D start;
/*     */   private final Point2D end;
/*     */   
/*     */   public LinearGradientPaint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOffloat, Color[] paramArrayOfColor) {
/* 139 */     this(new Point2D.Float(paramFloat1, paramFloat2), new Point2D.Float(paramFloat3, paramFloat4), paramArrayOffloat, paramArrayOfColor, MultipleGradientPaint.CycleMethod.NO_CYCLE);
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
/*     */   public LinearGradientPaint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOffloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod) {
/* 180 */     this(new Point2D.Float(paramFloat1, paramFloat2), new Point2D.Float(paramFloat3, paramFloat4), paramArrayOffloat, paramArrayOfColor, paramCycleMethod);
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
/*     */   public LinearGradientPaint(Point2D paramPoint2D1, Point2D paramPoint2D2, float[] paramArrayOffloat, Color[] paramArrayOfColor) {
/* 211 */     this(paramPoint2D1, paramPoint2D2, paramArrayOffloat, paramArrayOfColor, MultipleGradientPaint.CycleMethod.NO_CYCLE);
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
/*     */   public LinearGradientPaint(Point2D paramPoint2D1, Point2D paramPoint2D2, float[] paramArrayOffloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod) {
/* 244 */     this(paramPoint2D1, paramPoint2D2, paramArrayOffloat, paramArrayOfColor, paramCycleMethod, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform());
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
/*     */   @ConstructorProperties({"startPoint", "endPoint", "fractions", "colors", "cycleMethod", "colorSpace", "transform"})
/*     */   public LinearGradientPaint(Point2D paramPoint2D1, Point2D paramPoint2D2, float[] paramArrayOffloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod, MultipleGradientPaint.ColorSpaceType paramColorSpaceType, AffineTransform paramAffineTransform) {
/* 286 */     super(paramArrayOffloat, paramArrayOfColor, paramCycleMethod, paramColorSpaceType, paramAffineTransform);
/*     */ 
/*     */     
/* 289 */     if (paramPoint2D1 == null || paramPoint2D2 == null) {
/* 290 */       throw new NullPointerException("Start and end points must benon-null");
/*     */     }
/*     */ 
/*     */     
/* 294 */     if (paramPoint2D1.equals(paramPoint2D2)) {
/* 295 */       throw new IllegalArgumentException("Start point cannot equalendpoint");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 300 */     this.start = new Point2D.Double(paramPoint2D1.getX(), paramPoint2D1.getY());
/* 301 */     this.end = new Point2D.Double(paramPoint2D2.getX(), paramPoint2D2.getY());
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
/*     */   public PaintContext createContext(ColorModel paramColorModel, Rectangle paramRectangle, Rectangle2D paramRectangle2D, AffineTransform paramAffineTransform, RenderingHints paramRenderingHints) {
/* 339 */     paramAffineTransform = new AffineTransform(paramAffineTransform);
/*     */     
/* 341 */     paramAffineTransform.concatenate(this.gradientTransform);
/*     */     
/* 343 */     if (this.fractions.length == 2 && this.cycleMethod != MultipleGradientPaint.CycleMethod.REPEAT && this.colorSpace == MultipleGradientPaint.ColorSpaceType.SRGB) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 349 */       boolean bool = (this.cycleMethod != MultipleGradientPaint.CycleMethod.NO_CYCLE) ? true : false;
/* 350 */       return new GradientPaintContext(paramColorModel, this.start, this.end, paramAffineTransform, this.colors[0], this.colors[1], bool);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 355 */     return new LinearGradientPaintContext(this, paramColorModel, paramRectangle, paramRectangle2D, paramAffineTransform, paramRenderingHints, this.start, this.end, this.fractions, this.colors, this.cycleMethod, this.colorSpace);
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
/*     */   public Point2D getStartPoint() {
/* 371 */     return new Point2D.Double(this.start.getX(), this.start.getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2D getEndPoint() {
/* 381 */     return new Point2D.Double(this.end.getX(), this.end.getY());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/LinearGradientPaint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */