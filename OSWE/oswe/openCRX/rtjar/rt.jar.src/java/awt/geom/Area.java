/*     */ package java.awt.geom;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import sun.awt.geom.AreaOp;
/*     */ import sun.awt.geom.Crossings;
/*     */ import sun.awt.geom.Curve;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Area
/*     */   implements Shape, Cloneable
/*     */ {
/* 100 */   private static Vector EmptyCurves = new Vector();
/*     */ 
/*     */   
/*     */   private Vector curves;
/*     */   
/*     */   private Rectangle2D cachedBounds;
/*     */ 
/*     */   
/*     */   public Area() {
/* 109 */     this.curves = EmptyCurves;
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
/*     */   public Area(Shape paramShape) {
/* 123 */     if (paramShape instanceof Area) {
/* 124 */       this.curves = ((Area)paramShape).curves;
/*     */     } else {
/* 126 */       this.curves = pathToCurves(paramShape.getPathIterator(null));
/*     */     } 
/*     */   }
/*     */   private static Vector pathToCurves(PathIterator paramPathIterator) {
/*     */     AreaOp.NZWindOp nZWindOp;
/* 131 */     Vector<Curve> vector = new Vector();
/* 132 */     int i = paramPathIterator.getWindingRule();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     double[] arrayOfDouble = new double[23];
/* 148 */     double d1 = 0.0D, d2 = 0.0D;
/* 149 */     double d3 = 0.0D, d4 = 0.0D;
/*     */     
/* 151 */     while (!paramPathIterator.isDone()) {
/* 152 */       double d5, d6; switch (paramPathIterator.currentSegment(arrayOfDouble)) {
/*     */         case 0:
/* 154 */           Curve.insertLine(vector, d3, d4, d1, d2);
/* 155 */           d3 = d1 = arrayOfDouble[0];
/* 156 */           d4 = d2 = arrayOfDouble[1];
/* 157 */           Curve.insertMove(vector, d1, d2);
/*     */         
/*     */         case 1:
/* 160 */           d5 = arrayOfDouble[0];
/* 161 */           d6 = arrayOfDouble[1];
/* 162 */           Curve.insertLine(vector, d3, d4, d5, d6);
/* 163 */           d3 = d5;
/* 164 */           d4 = d6;
/*     */           break;
/*     */         case 2:
/* 167 */           d5 = arrayOfDouble[2];
/* 168 */           d6 = arrayOfDouble[3];
/* 169 */           Curve.insertQuad(vector, d3, d4, arrayOfDouble);
/* 170 */           d3 = d5;
/* 171 */           d4 = d6;
/*     */           break;
/*     */         case 3:
/* 174 */           d5 = arrayOfDouble[4];
/* 175 */           d6 = arrayOfDouble[5];
/* 176 */           Curve.insertCubic(vector, d3, d4, arrayOfDouble);
/* 177 */           d3 = d5;
/* 178 */           d4 = d6;
/*     */           break;
/*     */         case 4:
/* 181 */           Curve.insertLine(vector, d3, d4, d1, d2);
/* 182 */           d3 = d1;
/* 183 */           d4 = d2;
/*     */           break;
/*     */       } 
/* 186 */       paramPathIterator.next();
/*     */     } 
/* 188 */     Curve.insertLine(vector, d3, d4, d1, d2);
/*     */     
/* 190 */     if (i == 0) {
/* 191 */       AreaOp.EOWindOp eOWindOp = new AreaOp.EOWindOp();
/*     */     } else {
/* 193 */       nZWindOp = new AreaOp.NZWindOp();
/*     */     } 
/* 195 */     return nZWindOp.calculate(vector, EmptyCurves);
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
/*     */   public void add(Area paramArea) {
/* 227 */     this.curves = (new AreaOp.AddOp()).calculate(this.curves, paramArea.curves);
/* 228 */     invalidateBounds();
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
/*     */   public void subtract(Area paramArea) {
/* 260 */     this.curves = (new AreaOp.SubOp()).calculate(this.curves, paramArea.curves);
/* 261 */     invalidateBounds();
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
/*     */   public void intersect(Area paramArea) {
/* 293 */     this.curves = (new AreaOp.IntOp()).calculate(this.curves, paramArea.curves);
/* 294 */     invalidateBounds();
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
/*     */   public void exclusiveOr(Area paramArea) {
/* 327 */     this.curves = (new AreaOp.XorOp()).calculate(this.curves, paramArea.curves);
/* 328 */     invalidateBounds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 337 */     this.curves = new Vector();
/* 338 */     invalidateBounds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 348 */     return (this.curves.size() == 0);
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
/*     */   public boolean isPolygonal() {
/* 360 */     Enumeration enumeration = this.curves.elements();
/* 361 */     while (enumeration.hasMoreElements()) {
/* 362 */       if (((Curve)enumeration.nextElement()).getOrder() > 1) {
/* 363 */         return false;
/*     */       }
/*     */     } 
/* 366 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRectangular() {
/* 377 */     int i = this.curves.size();
/* 378 */     if (i == 0) {
/* 379 */       return true;
/*     */     }
/* 381 */     if (i > 3) {
/* 382 */       return false;
/*     */     }
/* 384 */     Curve curve1 = this.curves.get(1);
/* 385 */     Curve curve2 = this.curves.get(2);
/* 386 */     if (curve1.getOrder() != 1 || curve2.getOrder() != 1) {
/* 387 */       return false;
/*     */     }
/* 389 */     if (curve1.getXTop() != curve1.getXBot() || curve2.getXTop() != curve2.getXBot()) {
/* 390 */       return false;
/*     */     }
/* 392 */     if (curve1.getYTop() != curve2.getYTop() || curve1.getYBot() != curve2.getYBot())
/*     */     {
/* 394 */       return false;
/*     */     }
/* 396 */     return true;
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
/*     */   public boolean isSingular() {
/* 411 */     if (this.curves.size() < 3) {
/* 412 */       return true;
/*     */     }
/* 414 */     Enumeration<Curve> enumeration = this.curves.elements();
/* 415 */     enumeration.nextElement();
/* 416 */     while (enumeration.hasMoreElements()) {
/* 417 */       if (((Curve)enumeration.nextElement()).getOrder() == 0) {
/* 418 */         return false;
/*     */       }
/*     */     } 
/* 421 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void invalidateBounds() {
/* 426 */     this.cachedBounds = null;
/*     */   }
/*     */   private Rectangle2D getCachedBounds() {
/* 429 */     if (this.cachedBounds != null) {
/* 430 */       return this.cachedBounds;
/*     */     }
/* 432 */     Rectangle2D.Double double_ = new Rectangle2D.Double();
/* 433 */     if (this.curves.size() > 0) {
/* 434 */       Curve curve = this.curves.get(0);
/*     */       
/* 436 */       double_.setRect(curve.getX0(), curve.getY0(), 0.0D, 0.0D);
/* 437 */       for (byte b = 1; b < this.curves.size(); b++) {
/* 438 */         ((Curve)this.curves.get(b)).enlarge(double_);
/*     */       }
/*     */     } 
/* 441 */     return this.cachedBounds = double_;
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
/*     */   public Rectangle2D getBounds2D() {
/* 458 */     return getCachedBounds().getBounds2D();
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
/*     */   public Rectangle getBounds() {
/* 478 */     return getCachedBounds().getBounds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 487 */     return new Area(this);
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
/*     */   public boolean equals(Area paramArea) {
/* 504 */     if (paramArea == this) {
/* 505 */       return true;
/*     */     }
/* 507 */     if (paramArea == null) {
/* 508 */       return false;
/*     */     }
/* 510 */     Vector<Curve> vector = (new AreaOp.XorOp()).calculate(this.curves, paramArea.curves);
/* 511 */     return vector.isEmpty();
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
/*     */   public void transform(AffineTransform paramAffineTransform) {
/* 523 */     if (paramAffineTransform == null) {
/* 524 */       throw new NullPointerException("transform must not be null");
/*     */     }
/*     */ 
/*     */     
/* 528 */     this.curves = pathToCurves(getPathIterator(paramAffineTransform));
/* 529 */     invalidateBounds();
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
/*     */   public Area createTransformedArea(AffineTransform paramAffineTransform) {
/* 545 */     Area area = new Area(this);
/* 546 */     area.transform(paramAffineTransform);
/* 547 */     return area;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(double paramDouble1, double paramDouble2) {
/* 555 */     if (!getCachedBounds().contains(paramDouble1, paramDouble2)) {
/* 556 */       return false;
/*     */     }
/* 558 */     Enumeration<Curve> enumeration = this.curves.elements();
/* 559 */     int i = 0;
/* 560 */     while (enumeration.hasMoreElements()) {
/* 561 */       Curve curve = enumeration.nextElement();
/* 562 */       i += curve.crossingsFor(paramDouble1, paramDouble2);
/*     */     } 
/* 564 */     return ((i & 0x1) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Point2D paramPoint2D) {
/* 572 */     return contains(paramPoint2D.getX(), paramPoint2D.getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 580 */     if (paramDouble3 < 0.0D || paramDouble4 < 0.0D) {
/* 581 */       return false;
/*     */     }
/* 583 */     if (!getCachedBounds().contains(paramDouble1, paramDouble2, paramDouble3, paramDouble4)) {
/* 584 */       return false;
/*     */     }
/* 586 */     Crossings crossings = Crossings.findCrossings(this.curves, paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4);
/* 587 */     return (crossings != null && crossings.covers(paramDouble2, paramDouble2 + paramDouble4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Rectangle2D paramRectangle2D) {
/* 595 */     return contains(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 603 */     if (paramDouble3 < 0.0D || paramDouble4 < 0.0D) {
/* 604 */       return false;
/*     */     }
/* 606 */     if (!getCachedBounds().intersects(paramDouble1, paramDouble2, paramDouble3, paramDouble4)) {
/* 607 */       return false;
/*     */     }
/* 609 */     Crossings crossings = Crossings.findCrossings(this.curves, paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4);
/* 610 */     return (crossings == null || !crossings.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersects(Rectangle2D paramRectangle2D) {
/* 618 */     return intersects(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
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
/*     */   public PathIterator getPathIterator(AffineTransform paramAffineTransform) {
/* 633 */     return new AreaIterator(this.curves, paramAffineTransform);
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
/*     */   public PathIterator getPathIterator(AffineTransform paramAffineTransform, double paramDouble) {
/* 655 */     return new FlatteningPathIterator(getPathIterator(paramAffineTransform), paramDouble);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/geom/Area.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */