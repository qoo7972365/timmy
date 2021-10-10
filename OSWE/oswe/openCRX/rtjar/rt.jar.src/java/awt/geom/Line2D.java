/*      */ package java.awt.geom;
/*      */ 
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.io.Serializable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Line2D
/*      */   implements Shape, Cloneable
/*      */ {
/*      */   public abstract double getX1();
/*      */   
/*      */   public abstract double getY1();
/*      */   
/*      */   public abstract Point2D getP1();
/*      */   
/*      */   public abstract double getX2();
/*      */   
/*      */   public abstract double getY2();
/*      */   
/*      */   public abstract Point2D getP2();
/*      */   
/*      */   public abstract void setLine(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*      */   
/*      */   public static class Float
/*      */     extends Line2D
/*      */     implements Serializable
/*      */   {
/*      */     public float x1;
/*      */     public float y1;
/*      */     public float x2;
/*      */     public float y2;
/*      */     private static final long serialVersionUID = 6161772511649436349L;
/*      */     
/*      */     public Float() {}
/*      */     
/*      */     public Float(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/*  100 */       setLine(param1Float1, param1Float2, param1Float3, param1Float4);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float(Point2D param1Point2D1, Point2D param1Point2D2) {
/*  111 */       setLine(param1Point2D1, param1Point2D2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getX1() {
/*  119 */       return this.x1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getY1() {
/*  127 */       return this.y1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Point2D getP1() {
/*  135 */       return new Point2D.Float(this.x1, this.y1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getX2() {
/*  143 */       return this.x2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getY2() {
/*  151 */       return this.y2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Point2D getP2() {
/*  159 */       return new Point2D.Float(this.x2, this.y2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setLine(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/*  167 */       this.x1 = (float)param1Double1;
/*  168 */       this.y1 = (float)param1Double2;
/*  169 */       this.x2 = (float)param1Double3;
/*  170 */       this.y2 = (float)param1Double4;
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
/*      */     public void setLine(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/*  183 */       this.x1 = param1Float1;
/*  184 */       this.y1 = param1Float2;
/*  185 */       this.x2 = param1Float3;
/*  186 */       this.y2 = param1Float4;
/*      */     }
/*      */ 
/*      */     
/*      */     public Rectangle2D getBounds2D() {
/*      */       float f1;
/*      */       float f2;
/*      */       float f3;
/*      */       float f4;
/*  195 */       if (this.x1 < this.x2) {
/*  196 */         f1 = this.x1;
/*  197 */         f3 = this.x2 - this.x1;
/*      */       } else {
/*  199 */         f1 = this.x2;
/*  200 */         f3 = this.x1 - this.x2;
/*      */       } 
/*  202 */       if (this.y1 < this.y2) {
/*  203 */         f2 = this.y1;
/*  204 */         f4 = this.y2 - this.y1;
/*      */       } else {
/*  206 */         f2 = this.y2;
/*  207 */         f4 = this.y1 - this.y2;
/*      */       } 
/*  209 */       return new Rectangle2D.Float(f1, f2, f3, f4);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Double
/*      */     extends Line2D
/*      */     implements Serializable
/*      */   {
/*      */     public double x1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double y1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double x2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double y2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final long serialVersionUID = 7979627399746467499L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/*  268 */       setLine(param1Double1, param1Double2, param1Double3, param1Double4);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double(Point2D param1Point2D1, Point2D param1Point2D2) {
/*  279 */       setLine(param1Point2D1, param1Point2D2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getX1() {
/*  287 */       return this.x1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getY1() {
/*  295 */       return this.y1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Point2D getP1() {
/*  303 */       return new Point2D.Double(this.x1, this.y1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getX2() {
/*  311 */       return this.x2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getY2() {
/*  319 */       return this.y2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Point2D getP2() {
/*  327 */       return new Point2D.Double(this.x2, this.y2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setLine(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/*  335 */       this.x1 = param1Double1;
/*  336 */       this.y1 = param1Double2;
/*  337 */       this.x2 = param1Double3;
/*  338 */       this.y2 = param1Double4;
/*      */     }
/*      */ 
/*      */     
/*      */     public Rectangle2D getBounds2D() {
/*      */       double d1;
/*      */       double d2;
/*      */       double d3;
/*      */       double d4;
/*  347 */       if (this.x1 < this.x2) {
/*  348 */         d1 = this.x1;
/*  349 */         d3 = this.x2 - this.x1;
/*      */       } else {
/*  351 */         d1 = this.x2;
/*  352 */         d3 = this.x1 - this.x2;
/*      */       } 
/*  354 */       if (this.y1 < this.y2) {
/*  355 */         d2 = this.y1;
/*  356 */         d4 = this.y2 - this.y1;
/*      */       } else {
/*  358 */         d2 = this.y2;
/*  359 */         d4 = this.y1 - this.y2;
/*      */       } 
/*  361 */       return new Rectangle2D.Double(d1, d2, d3, d4);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLine(Point2D paramPoint2D1, Point2D paramPoint2D2) {
/*  449 */     setLine(paramPoint2D1.getX(), paramPoint2D1.getY(), paramPoint2D2.getX(), paramPoint2D2.getY());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLine(Line2D paramLine2D) {
/*  459 */     setLine(paramLine2D.getX1(), paramLine2D.getY1(), paramLine2D.getX2(), paramLine2D.getY2());
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
/*      */   public static int relativeCCW(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/*  508 */     paramDouble3 -= paramDouble1;
/*  509 */     paramDouble4 -= paramDouble2;
/*  510 */     paramDouble5 -= paramDouble1;
/*  511 */     paramDouble6 -= paramDouble2;
/*  512 */     double d = paramDouble5 * paramDouble4 - paramDouble6 * paramDouble3;
/*  513 */     if (d == 0.0D) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  520 */       d = paramDouble5 * paramDouble3 + paramDouble6 * paramDouble4;
/*  521 */       if (d > 0.0D) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  529 */         paramDouble5 -= paramDouble3;
/*  530 */         paramDouble6 -= paramDouble4;
/*  531 */         d = paramDouble5 * paramDouble3 + paramDouble6 * paramDouble4;
/*  532 */         if (d < 0.0D) {
/*  533 */           d = 0.0D;
/*      */         }
/*      */       } 
/*      */     } 
/*  537 */     return (d < 0.0D) ? -1 : ((d > 0.0D) ? 1 : 0);
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
/*      */   public int relativeCCW(double paramDouble1, double paramDouble2) {
/*  556 */     return relativeCCW(getX1(), getY1(), getX2(), getY2(), paramDouble1, paramDouble2);
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
/*      */   public int relativeCCW(Point2D paramPoint2D) {
/*  573 */     return relativeCCW(getX1(), getY1(), getX2(), getY2(), paramPoint2D
/*  574 */         .getX(), paramPoint2D.getY());
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
/*      */   public static boolean linesIntersect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8) {
/*  608 */     return (relativeCCW(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6) * 
/*  609 */       relativeCCW(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble7, paramDouble8) <= 0 && 
/*  610 */       relativeCCW(paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble1, paramDouble2) * 
/*  611 */       relativeCCW(paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble3, paramDouble4) <= 0);
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
/*      */   public boolean intersectsLine(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  631 */     return linesIntersect(paramDouble1, paramDouble2, paramDouble3, paramDouble4, 
/*  632 */         getX1(), getY1(), getX2(), getY2());
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
/*      */   public boolean intersectsLine(Line2D paramLine2D) {
/*  644 */     return linesIntersect(paramLine2D.getX1(), paramLine2D.getY1(), paramLine2D.getX2(), paramLine2D.getY2(), 
/*  645 */         getX1(), getY1(), getX2(), getY2());
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
/*      */   public static double ptSegDistSq(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/*      */     double d2;
/*  678 */     paramDouble3 -= paramDouble1;
/*  679 */     paramDouble4 -= paramDouble2;
/*      */     
/*  681 */     paramDouble5 -= paramDouble1;
/*  682 */     paramDouble6 -= paramDouble2;
/*  683 */     double d1 = paramDouble5 * paramDouble3 + paramDouble6 * paramDouble4;
/*      */     
/*  685 */     if (d1 <= 0.0D) {
/*      */ 
/*      */ 
/*      */       
/*  689 */       d2 = 0.0D;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  696 */       paramDouble5 = paramDouble3 - paramDouble5;
/*  697 */       paramDouble6 = paramDouble4 - paramDouble6;
/*  698 */       d1 = paramDouble5 * paramDouble3 + paramDouble6 * paramDouble4;
/*  699 */       if (d1 <= 0.0D) {
/*      */ 
/*      */ 
/*      */         
/*  703 */         d2 = 0.0D;
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  709 */         d2 = d1 * d1 / (paramDouble3 * paramDouble3 + paramDouble4 * paramDouble4);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  716 */     double d3 = paramDouble5 * paramDouble5 + paramDouble6 * paramDouble6 - d2;
/*  717 */     if (d3 < 0.0D) {
/*  718 */       d3 = 0.0D;
/*      */     }
/*  720 */     return d3;
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
/*      */   public static double ptSegDist(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/*  751 */     return Math.sqrt(ptSegDistSq(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6));
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
/*      */   public double ptSegDistSq(double paramDouble1, double paramDouble2) {
/*  771 */     return ptSegDistSq(getX1(), getY1(), getX2(), getY2(), paramDouble1, paramDouble2);
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
/*      */   public double ptSegDistSq(Point2D paramPoint2D) {
/*  790 */     return ptSegDistSq(getX1(), getY1(), getX2(), getY2(), paramPoint2D
/*  791 */         .getX(), paramPoint2D.getY());
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
/*      */   public double ptSegDist(double paramDouble1, double paramDouble2) {
/*  811 */     return ptSegDist(getX1(), getY1(), getX2(), getY2(), paramDouble1, paramDouble2);
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
/*      */   public double ptSegDist(Point2D paramPoint2D) {
/*  830 */     return ptSegDist(getX1(), getY1(), getX2(), getY2(), paramPoint2D
/*  831 */         .getX(), paramPoint2D.getY());
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
/*      */   public static double ptLineDistSq(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/*  860 */     paramDouble3 -= paramDouble1;
/*  861 */     paramDouble4 -= paramDouble2;
/*      */     
/*  863 */     paramDouble5 -= paramDouble1;
/*  864 */     paramDouble6 -= paramDouble2;
/*  865 */     double d1 = paramDouble5 * paramDouble3 + paramDouble6 * paramDouble4;
/*      */ 
/*      */ 
/*      */     
/*  869 */     double d2 = d1 * d1 / (paramDouble3 * paramDouble3 + paramDouble4 * paramDouble4);
/*      */ 
/*      */     
/*  872 */     double d3 = paramDouble5 * paramDouble5 + paramDouble6 * paramDouble6 - d2;
/*  873 */     if (d3 < 0.0D) {
/*  874 */       d3 = 0.0D;
/*      */     }
/*  876 */     return d3;
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
/*      */   public static double ptLineDist(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/*  903 */     return Math.sqrt(ptLineDistSq(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6));
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
/*      */   public double ptLineDistSq(double paramDouble1, double paramDouble2) {
/*  923 */     return ptLineDistSq(getX1(), getY1(), getX2(), getY2(), paramDouble1, paramDouble2);
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
/*      */   public double ptLineDistSq(Point2D paramPoint2D) {
/*  942 */     return ptLineDistSq(getX1(), getY1(), getX2(), getY2(), paramPoint2D
/*  943 */         .getX(), paramPoint2D.getY());
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
/*      */   public double ptLineDist(double paramDouble1, double paramDouble2) {
/*  963 */     return ptLineDist(getX1(), getY1(), getX2(), getY2(), paramDouble1, paramDouble2);
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
/*      */   public double ptLineDist(Point2D paramPoint2D) {
/*  979 */     return ptLineDist(getX1(), getY1(), getX2(), getY2(), paramPoint2D
/*  980 */         .getX(), paramPoint2D.getY());
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
/*      */   public boolean contains(double paramDouble1, double paramDouble2) {
/*  996 */     return false;
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
/*      */   public boolean contains(Point2D paramPoint2D) {
/* 1011 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 1019 */     return intersects(new Rectangle2D.Double(paramDouble1, paramDouble2, paramDouble3, paramDouble4));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean intersects(Rectangle2D paramRectangle2D) {
/* 1027 */     return paramRectangle2D.intersectsLine(getX1(), getY1(), getX2(), getY2());
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
/*      */   public boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 1047 */     return false;
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
/*      */   public boolean contains(Rectangle2D paramRectangle2D) {
/* 1062 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getBounds() {
/* 1070 */     return getBounds2D().getBounds();
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
/*      */   public PathIterator getPathIterator(AffineTransform paramAffineTransform) {
/* 1087 */     return new LineIterator(this, paramAffineTransform);
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
/*      */   public PathIterator getPathIterator(AffineTransform paramAffineTransform, double paramDouble) {
/* 1109 */     return new LineIterator(this, paramAffineTransform);
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
/*      */   public Object clone() {
/*      */     try {
/* 1122 */       return super.clone();
/* 1123 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/* 1125 */       throw new InternalError(cloneNotSupportedException);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/geom/Line2D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */