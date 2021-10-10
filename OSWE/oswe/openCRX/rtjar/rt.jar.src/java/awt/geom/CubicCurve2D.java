/*      */ package java.awt.geom;
/*      */ 
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import sun.awt.geom.Curve;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class CubicCurve2D
/*      */   implements Shape, Cloneable
/*      */ {
/*      */   public abstract double getX1();
/*      */   
/*      */   public abstract double getY1();
/*      */   
/*      */   public abstract Point2D getP1();
/*      */   
/*      */   public abstract double getCtrlX1();
/*      */   
/*      */   public abstract double getCtrlY1();
/*      */   
/*      */   public abstract Point2D getCtrlP1();
/*      */   
/*      */   public abstract double getCtrlX2();
/*      */   
/*      */   public abstract double getCtrlY2();
/*      */   
/*      */   public abstract Point2D getCtrlP2();
/*      */   
/*      */   public abstract double getX2();
/*      */   
/*      */   public abstract double getY2();
/*      */   
/*      */   public abstract Point2D getP2();
/*      */   
/*      */   public abstract void setCurve(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8);
/*      */   
/*      */   public static class Float
/*      */     extends CubicCurve2D
/*      */     implements Serializable
/*      */   {
/*      */     public float x1;
/*      */     public float y1;
/*      */     public float ctrlx1;
/*      */     public float ctrly1;
/*      */     public float ctrlx2;
/*      */     public float ctrly2;
/*      */     public float x2;
/*      */     public float y2;
/*      */     private static final long serialVersionUID = -1272015596714244385L;
/*      */     
/*      */     public Float() {}
/*      */     
/*      */     public Float(float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6, float param1Float7, float param1Float8) {
/*  157 */       setCurve(param1Float1, param1Float2, param1Float3, param1Float4, param1Float5, param1Float6, param1Float7, param1Float8);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getX1() {
/*  165 */       return this.x1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getY1() {
/*  173 */       return this.y1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Point2D getP1() {
/*  181 */       return new Point2D.Float(this.x1, this.y1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getCtrlX1() {
/*  189 */       return this.ctrlx1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getCtrlY1() {
/*  197 */       return this.ctrly1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Point2D getCtrlP1() {
/*  205 */       return new Point2D.Float(this.ctrlx1, this.ctrly1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getCtrlX2() {
/*  213 */       return this.ctrlx2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getCtrlY2() {
/*  221 */       return this.ctrly2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Point2D getCtrlP2() {
/*  229 */       return new Point2D.Float(this.ctrlx2, this.ctrly2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getX2() {
/*  237 */       return this.x2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getY2() {
/*  245 */       return this.y2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Point2D getP2() {
/*  253 */       return new Point2D.Float(this.x2, this.y2);
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
/*      */     public void setCurve(double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8) {
/*  265 */       this.x1 = (float)param1Double1;
/*  266 */       this.y1 = (float)param1Double2;
/*  267 */       this.ctrlx1 = (float)param1Double3;
/*  268 */       this.ctrly1 = (float)param1Double4;
/*  269 */       this.ctrlx2 = (float)param1Double5;
/*  270 */       this.ctrly2 = (float)param1Double6;
/*  271 */       this.x2 = (float)param1Double7;
/*  272 */       this.y2 = (float)param1Double8;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setCurve(float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6, float param1Float7, float param1Float8) {
/*  302 */       this.x1 = param1Float1;
/*  303 */       this.y1 = param1Float2;
/*  304 */       this.ctrlx1 = param1Float3;
/*  305 */       this.ctrly1 = param1Float4;
/*  306 */       this.ctrlx2 = param1Float5;
/*  307 */       this.ctrly2 = param1Float6;
/*  308 */       this.x2 = param1Float7;
/*  309 */       this.y2 = param1Float8;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Rectangle2D getBounds2D() {
/*  317 */       float f1 = Math.min(Math.min(this.x1, this.x2), 
/*  318 */           Math.min(this.ctrlx1, this.ctrlx2));
/*  319 */       float f2 = Math.min(Math.min(this.y1, this.y2), 
/*  320 */           Math.min(this.ctrly1, this.ctrly2));
/*  321 */       float f3 = Math.max(Math.max(this.x1, this.x2), 
/*  322 */           Math.max(this.ctrlx1, this.ctrlx2));
/*  323 */       float f4 = Math.max(Math.max(this.y1, this.y2), 
/*  324 */           Math.max(this.ctrly1, this.ctrly2));
/*  325 */       return new Rectangle2D.Float(f1, f2, f3 - f1, f4 - f2);
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
/*      */   public static class Double
/*      */     extends CubicCurve2D
/*      */     implements Serializable
/*      */   {
/*      */     public double x1;
/*      */ 
/*      */ 
/*      */ 
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
/*      */ 
/*      */ 
/*      */     
/*      */     public double ctrlx1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double ctrly1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double ctrlx2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double ctrly2;
/*      */ 
/*      */ 
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
/*      */ 
/*      */     
/*      */     public double y2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final long serialVersionUID = -4202960122839707295L;
/*      */ 
/*      */ 
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
/*      */ 
/*      */     
/*      */     public Double(double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8) {
/*  440 */       setCurve(param1Double1, param1Double2, param1Double3, param1Double4, param1Double5, param1Double6, param1Double7, param1Double8);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getX1() {
/*  448 */       return this.x1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getY1() {
/*  456 */       return this.y1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Point2D getP1() {
/*  464 */       return new Point2D.Double(this.x1, this.y1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getCtrlX1() {
/*  472 */       return this.ctrlx1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getCtrlY1() {
/*  480 */       return this.ctrly1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Point2D getCtrlP1() {
/*  488 */       return new Point2D.Double(this.ctrlx1, this.ctrly1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getCtrlX2() {
/*  496 */       return this.ctrlx2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getCtrlY2() {
/*  504 */       return this.ctrly2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Point2D getCtrlP2() {
/*  512 */       return new Point2D.Double(this.ctrlx2, this.ctrly2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getX2() {
/*  520 */       return this.x2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getY2() {
/*  528 */       return this.y2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Point2D getP2() {
/*  536 */       return new Point2D.Double(this.x2, this.y2);
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
/*      */     public void setCurve(double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8) {
/*  548 */       this.x1 = param1Double1;
/*  549 */       this.y1 = param1Double2;
/*  550 */       this.ctrlx1 = param1Double3;
/*  551 */       this.ctrly1 = param1Double4;
/*  552 */       this.ctrlx2 = param1Double5;
/*  553 */       this.ctrly2 = param1Double6;
/*  554 */       this.x2 = param1Double7;
/*  555 */       this.y2 = param1Double8;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Rectangle2D getBounds2D() {
/*  563 */       double d1 = Math.min(Math.min(this.x1, this.x2), 
/*  564 */           Math.min(this.ctrlx1, this.ctrlx2));
/*  565 */       double d2 = Math.min(Math.min(this.y1, this.y2), 
/*  566 */           Math.min(this.ctrly1, this.ctrly2));
/*  567 */       double d3 = Math.max(Math.max(this.x1, this.x2), 
/*  568 */           Math.max(this.ctrlx1, this.ctrlx2));
/*  569 */       double d4 = Math.max(Math.max(this.y1, this.y2), 
/*  570 */           Math.max(this.ctrly1, this.ctrly2));
/*  571 */       return new Rectangle2D.Double(d1, d2, d3 - d1, d4 - d2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurve(double[] paramArrayOfdouble, int paramInt) {
/*  731 */     setCurve(paramArrayOfdouble[paramInt + 0], paramArrayOfdouble[paramInt + 1], paramArrayOfdouble[paramInt + 2], paramArrayOfdouble[paramInt + 3], paramArrayOfdouble[paramInt + 4], paramArrayOfdouble[paramInt + 5], paramArrayOfdouble[paramInt + 6], paramArrayOfdouble[paramInt + 7]);
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
/*      */   public void setCurve(Point2D paramPoint2D1, Point2D paramPoint2D2, Point2D paramPoint2D3, Point2D paramPoint2D4) {
/*  751 */     setCurve(paramPoint2D1.getX(), paramPoint2D1.getY(), paramPoint2D2.getX(), paramPoint2D2.getY(), paramPoint2D3
/*  752 */         .getX(), paramPoint2D3.getY(), paramPoint2D4.getX(), paramPoint2D4.getY());
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
/*      */   public void setCurve(Point2D[] paramArrayOfPoint2D, int paramInt) {
/*  766 */     setCurve(paramArrayOfPoint2D[paramInt + 0].getX(), paramArrayOfPoint2D[paramInt + 0].getY(), paramArrayOfPoint2D[paramInt + 1]
/*  767 */         .getX(), paramArrayOfPoint2D[paramInt + 1].getY(), paramArrayOfPoint2D[paramInt + 2]
/*  768 */         .getX(), paramArrayOfPoint2D[paramInt + 2].getY(), paramArrayOfPoint2D[paramInt + 3]
/*  769 */         .getX(), paramArrayOfPoint2D[paramInt + 3].getY());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurve(CubicCurve2D paramCubicCurve2D) {
/*  779 */     setCurve(paramCubicCurve2D.getX1(), paramCubicCurve2D.getY1(), paramCubicCurve2D.getCtrlX1(), paramCubicCurve2D.getCtrlY1(), paramCubicCurve2D
/*  780 */         .getCtrlX2(), paramCubicCurve2D.getCtrlY2(), paramCubicCurve2D.getX2(), paramCubicCurve2D.getY2());
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
/*      */   public static double getFlatnessSq(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8) {
/*  812 */     return Math.max(Line2D.ptSegDistSq(paramDouble1, paramDouble2, paramDouble7, paramDouble8, paramDouble3, paramDouble4), 
/*  813 */         Line2D.ptSegDistSq(paramDouble1, paramDouble2, paramDouble7, paramDouble8, paramDouble5, paramDouble6));
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
/*      */   public static double getFlatness(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8) {
/*  846 */     return Math.sqrt(getFlatnessSq(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8));
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
/*      */   public static double getFlatnessSq(double[] paramArrayOfdouble, int paramInt) {
/*  864 */     return getFlatnessSq(paramArrayOfdouble[paramInt + 0], paramArrayOfdouble[paramInt + 1], paramArrayOfdouble[paramInt + 2], paramArrayOfdouble[paramInt + 3], paramArrayOfdouble[paramInt + 4], paramArrayOfdouble[paramInt + 5], paramArrayOfdouble[paramInt + 6], paramArrayOfdouble[paramInt + 7]);
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
/*      */   public static double getFlatness(double[] paramArrayOfdouble, int paramInt) {
/*  884 */     return getFlatness(paramArrayOfdouble[paramInt + 0], paramArrayOfdouble[paramInt + 1], paramArrayOfdouble[paramInt + 2], paramArrayOfdouble[paramInt + 3], paramArrayOfdouble[paramInt + 4], paramArrayOfdouble[paramInt + 5], paramArrayOfdouble[paramInt + 6], paramArrayOfdouble[paramInt + 7]);
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
/*      */   public double getFlatnessSq() {
/*  898 */     return getFlatnessSq(getX1(), getY1(), getCtrlX1(), getCtrlY1(), 
/*  899 */         getCtrlX2(), getCtrlY2(), getX2(), getY2());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getFlatness() {
/*  910 */     return getFlatness(getX1(), getY1(), getCtrlX1(), getCtrlY1(), 
/*  911 */         getCtrlX2(), getCtrlY2(), getX2(), getY2());
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
/*      */   public void subdivide(CubicCurve2D paramCubicCurve2D1, CubicCurve2D paramCubicCurve2D2) {
/*  926 */     subdivide(this, paramCubicCurve2D1, paramCubicCurve2D2);
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
/*      */   public static void subdivide(CubicCurve2D paramCubicCurve2D1, CubicCurve2D paramCubicCurve2D2, CubicCurve2D paramCubicCurve2D3) {
/*  945 */     double d1 = paramCubicCurve2D1.getX1();
/*  946 */     double d2 = paramCubicCurve2D1.getY1();
/*  947 */     double d3 = paramCubicCurve2D1.getCtrlX1();
/*  948 */     double d4 = paramCubicCurve2D1.getCtrlY1();
/*  949 */     double d5 = paramCubicCurve2D1.getCtrlX2();
/*  950 */     double d6 = paramCubicCurve2D1.getCtrlY2();
/*  951 */     double d7 = paramCubicCurve2D1.getX2();
/*  952 */     double d8 = paramCubicCurve2D1.getY2();
/*  953 */     double d9 = (d3 + d5) / 2.0D;
/*  954 */     double d10 = (d4 + d6) / 2.0D;
/*  955 */     d3 = (d1 + d3) / 2.0D;
/*  956 */     d4 = (d2 + d4) / 2.0D;
/*  957 */     d5 = (d7 + d5) / 2.0D;
/*  958 */     d6 = (d8 + d6) / 2.0D;
/*  959 */     double d11 = (d3 + d9) / 2.0D;
/*  960 */     double d12 = (d4 + d10) / 2.0D;
/*  961 */     double d13 = (d5 + d9) / 2.0D;
/*  962 */     double d14 = (d6 + d10) / 2.0D;
/*  963 */     d9 = (d11 + d13) / 2.0D;
/*  964 */     d10 = (d12 + d14) / 2.0D;
/*  965 */     if (paramCubicCurve2D2 != null) {
/*  966 */       paramCubicCurve2D2.setCurve(d1, d2, d3, d4, d11, d12, d9, d10);
/*      */     }
/*      */     
/*  969 */     if (paramCubicCurve2D3 != null) {
/*  970 */       paramCubicCurve2D3.setCurve(d9, d10, d13, d14, d5, d6, d7, d8);
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
/*      */   public static void subdivide(double[] paramArrayOfdouble1, int paramInt1, double[] paramArrayOfdouble2, int paramInt2, double[] paramArrayOfdouble3, int paramInt3) {
/* 1006 */     double d1 = paramArrayOfdouble1[paramInt1 + 0];
/* 1007 */     double d2 = paramArrayOfdouble1[paramInt1 + 1];
/* 1008 */     double d3 = paramArrayOfdouble1[paramInt1 + 2];
/* 1009 */     double d4 = paramArrayOfdouble1[paramInt1 + 3];
/* 1010 */     double d5 = paramArrayOfdouble1[paramInt1 + 4];
/* 1011 */     double d6 = paramArrayOfdouble1[paramInt1 + 5];
/* 1012 */     double d7 = paramArrayOfdouble1[paramInt1 + 6];
/* 1013 */     double d8 = paramArrayOfdouble1[paramInt1 + 7];
/* 1014 */     if (paramArrayOfdouble2 != null) {
/* 1015 */       paramArrayOfdouble2[paramInt2 + 0] = d1;
/* 1016 */       paramArrayOfdouble2[paramInt2 + 1] = d2;
/*      */     } 
/* 1018 */     if (paramArrayOfdouble3 != null) {
/* 1019 */       paramArrayOfdouble3[paramInt3 + 6] = d7;
/* 1020 */       paramArrayOfdouble3[paramInt3 + 7] = d8;
/*      */     } 
/* 1022 */     d1 = (d1 + d3) / 2.0D;
/* 1023 */     d2 = (d2 + d4) / 2.0D;
/* 1024 */     d7 = (d7 + d5) / 2.0D;
/* 1025 */     d8 = (d8 + d6) / 2.0D;
/* 1026 */     double d9 = (d3 + d5) / 2.0D;
/* 1027 */     double d10 = (d4 + d6) / 2.0D;
/* 1028 */     d3 = (d1 + d9) / 2.0D;
/* 1029 */     d4 = (d2 + d10) / 2.0D;
/* 1030 */     d5 = (d7 + d9) / 2.0D;
/* 1031 */     d6 = (d8 + d10) / 2.0D;
/* 1032 */     d9 = (d3 + d5) / 2.0D;
/* 1033 */     d10 = (d4 + d6) / 2.0D;
/* 1034 */     if (paramArrayOfdouble2 != null) {
/* 1035 */       paramArrayOfdouble2[paramInt2 + 2] = d1;
/* 1036 */       paramArrayOfdouble2[paramInt2 + 3] = d2;
/* 1037 */       paramArrayOfdouble2[paramInt2 + 4] = d3;
/* 1038 */       paramArrayOfdouble2[paramInt2 + 5] = d4;
/* 1039 */       paramArrayOfdouble2[paramInt2 + 6] = d9;
/* 1040 */       paramArrayOfdouble2[paramInt2 + 7] = d10;
/*      */     } 
/* 1042 */     if (paramArrayOfdouble3 != null) {
/* 1043 */       paramArrayOfdouble3[paramInt3 + 0] = d9;
/* 1044 */       paramArrayOfdouble3[paramInt3 + 1] = d10;
/* 1045 */       paramArrayOfdouble3[paramInt3 + 2] = d5;
/* 1046 */       paramArrayOfdouble3[paramInt3 + 3] = d6;
/* 1047 */       paramArrayOfdouble3[paramInt3 + 4] = d7;
/* 1048 */       paramArrayOfdouble3[paramInt3 + 5] = d8;
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
/*      */   public static int solveCubic(double[] paramArrayOfdouble) {
/* 1069 */     return solveCubic(paramArrayOfdouble, paramArrayOfdouble);
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
/*      */   public static int solveCubic(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
/*      */     int i;
/* 1092 */     double d1 = paramArrayOfdouble1[3];
/* 1093 */     if (d1 == 0.0D) {
/* 1094 */       return QuadCurve2D.solveQuadratic(paramArrayOfdouble1, paramArrayOfdouble2);
/*      */     }
/*      */ 
/*      */     
/* 1098 */     double d2 = paramArrayOfdouble1[2] / d1;
/* 1099 */     double d3 = paramArrayOfdouble1[1] / d1;
/* 1100 */     double d4 = paramArrayOfdouble1[0] / d1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1111 */     double d5 = d2 * d2;
/* 1112 */     double d6 = 0.3333333333333333D * (-0.3333333333333333D * d5 + d3);
/* 1113 */     double d7 = 0.5D * (0.07407407407407407D * d2 * d5 - 0.3333333333333333D * d2 * d3 + d4);
/*      */ 
/*      */ 
/*      */     
/* 1117 */     double d8 = d6 * d6 * d6;
/* 1118 */     double d9 = d7 * d7 + d8;
/*      */     
/* 1120 */     double d10 = 0.3333333333333333D * d2;
/*      */ 
/*      */     
/* 1123 */     if (d9 < 0.0D) {
/*      */       
/* 1125 */       double d11 = 0.3333333333333333D * Math.acos(-d7 / Math.sqrt(-d8));
/* 1126 */       double d12 = 2.0D * Math.sqrt(-d6);
/*      */       
/* 1128 */       if (paramArrayOfdouble2 == paramArrayOfdouble1) {
/* 1129 */         paramArrayOfdouble1 = Arrays.copyOf(paramArrayOfdouble1, 4);
/*      */       }
/*      */       
/* 1132 */       paramArrayOfdouble2[0] = d12 * Math.cos(d11);
/* 1133 */       paramArrayOfdouble2[1] = -d12 * Math.cos(d11 + 1.0471975511965976D);
/* 1134 */       paramArrayOfdouble2[2] = -d12 * Math.cos(d11 - 1.0471975511965976D);
/* 1135 */       i = 3;
/*      */       
/* 1137 */       for (byte b = 0; b < i; b++) {
/* 1138 */         paramArrayOfdouble2[b] = paramArrayOfdouble2[b] - d10;
/*      */       
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1144 */       double d11 = Math.sqrt(d9);
/* 1145 */       double d12 = Math.cbrt(d11 - d7);
/* 1146 */       double d13 = -Math.cbrt(d11 + d7);
/* 1147 */       double d14 = d12 + d13;
/*      */       
/* 1149 */       i = 1;
/*      */       
/* 1151 */       double d15 = 1.2E9D * Math.ulp(Math.abs(d14) + Math.abs(d10));
/* 1152 */       if (iszero(d9, d15) || within(d12, d13, d15)) {
/* 1153 */         if (paramArrayOfdouble2 == paramArrayOfdouble1) {
/* 1154 */           paramArrayOfdouble1 = Arrays.copyOf(paramArrayOfdouble1, 4);
/*      */         }
/* 1156 */         paramArrayOfdouble2[1] = -(d14 / 2.0D) - d10;
/* 1157 */         i = 2;
/*      */       } 
/*      */       
/* 1160 */       paramArrayOfdouble2[0] = d14 - d10;
/*      */     } 
/*      */     
/* 1163 */     if (i > 1) {
/* 1164 */       i = fixRoots(paramArrayOfdouble1, paramArrayOfdouble2, i);
/*      */     }
/* 1166 */     if (i > 2 && (paramArrayOfdouble2[2] == paramArrayOfdouble2[1] || paramArrayOfdouble2[2] == paramArrayOfdouble2[0])) {
/* 1167 */       i--;
/*      */     }
/* 1169 */     if (i > 1 && paramArrayOfdouble2[1] == paramArrayOfdouble2[0]) {
/* 1170 */       paramArrayOfdouble2[1] = paramArrayOfdouble2[--i];
/*      */     }
/* 1172 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int fixRoots(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, int paramInt) {
/* 1183 */     double[] arrayOfDouble = { paramArrayOfdouble1[1], 2.0D * paramArrayOfdouble1[2], 3.0D * paramArrayOfdouble1[3] };
/* 1184 */     int i = QuadCurve2D.solveQuadratic(arrayOfDouble, arrayOfDouble);
/* 1185 */     if (i == 2 && arrayOfDouble[0] == arrayOfDouble[1]) {
/* 1186 */       i--;
/*      */     }
/* 1188 */     if (i == 2 && arrayOfDouble[0] > arrayOfDouble[1]) {
/* 1189 */       double d = arrayOfDouble[0];
/* 1190 */       arrayOfDouble[0] = arrayOfDouble[1];
/* 1191 */       arrayOfDouble[1] = d;
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
/*      */     
/* 1204 */     if (paramInt == 3) {
/* 1205 */       double d1 = getRootUpperBound(paramArrayOfdouble1);
/* 1206 */       double d2 = -d1;
/*      */       
/* 1208 */       Arrays.sort(paramArrayOfdouble2, 0, paramInt);
/* 1209 */       if (i == 2) {
/*      */ 
/*      */         
/* 1212 */         paramArrayOfdouble2[0] = refineRootWithHint(paramArrayOfdouble1, d2, arrayOfDouble[0], paramArrayOfdouble2[0]);
/* 1213 */         paramArrayOfdouble2[1] = refineRootWithHint(paramArrayOfdouble1, arrayOfDouble[0], arrayOfDouble[1], paramArrayOfdouble2[1]);
/* 1214 */         paramArrayOfdouble2[2] = refineRootWithHint(paramArrayOfdouble1, arrayOfDouble[1], d1, paramArrayOfdouble2[2]);
/* 1215 */         return 3;
/* 1216 */       }  if (i == 1) {
/*      */ 
/*      */ 
/*      */         
/* 1220 */         double d3 = paramArrayOfdouble1[3];
/* 1221 */         double d4 = -d3;
/*      */         
/* 1223 */         double d5 = arrayOfDouble[0];
/* 1224 */         double d6 = solveEqn(paramArrayOfdouble1, 3, d5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1236 */         if (oppositeSigns(d4, d6)) {
/* 1237 */           paramArrayOfdouble2[0] = bisectRootWithHint(paramArrayOfdouble1, d2, d5, paramArrayOfdouble2[0]);
/* 1238 */         } else if (oppositeSigns(d6, d3)) {
/* 1239 */           paramArrayOfdouble2[0] = bisectRootWithHint(paramArrayOfdouble1, d5, d1, paramArrayOfdouble2[2]);
/*      */         } else {
/* 1241 */           paramArrayOfdouble2[0] = d5;
/*      */         }
/*      */       
/* 1244 */       } else if (i == 0) {
/* 1245 */         paramArrayOfdouble2[0] = bisectRootWithHint(paramArrayOfdouble1, d2, d1, paramArrayOfdouble2[1]);
/*      */       }
/*      */     
/* 1248 */     } else if (paramInt == 2 && i == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1257 */       double d1 = paramArrayOfdouble2[0];
/* 1258 */       double d2 = paramArrayOfdouble2[1];
/* 1259 */       double d3 = arrayOfDouble[0];
/* 1260 */       double d4 = arrayOfDouble[1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1268 */       double d5 = (Math.abs(d3 - d1) > Math.abs(d4 - d1)) ? d3 : d4;
/* 1269 */       double d6 = solveEqn(paramArrayOfdouble1, 3, d5);
/*      */       
/* 1271 */       if (iszero(d6, 1.0E7D * Math.ulp(d5))) {
/* 1272 */         double d = solveEqn(paramArrayOfdouble1, 3, d2);
/* 1273 */         paramArrayOfdouble2[1] = (Math.abs(d) < Math.abs(d6)) ? d2 : d5;
/* 1274 */         return 2;
/*      */       } 
/*      */     } 
/*      */     
/* 1278 */     return 1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static double refineRootWithHint(double[] paramArrayOfdouble, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 1283 */     if (!inInterval(paramDouble3, paramDouble1, paramDouble2)) {
/* 1284 */       return paramDouble3;
/*      */     }
/* 1286 */     double[] arrayOfDouble = { paramArrayOfdouble[1], 2.0D * paramArrayOfdouble[2], 3.0D * paramArrayOfdouble[3] };
/* 1287 */     double d = paramDouble3;
/* 1288 */     for (byte b = 0; b < 3; b++) {
/* 1289 */       double d1 = solveEqn(arrayOfDouble, 2, paramDouble3);
/* 1290 */       double d2 = solveEqn(paramArrayOfdouble, 3, paramDouble3);
/* 1291 */       double d3 = -(d2 / d1);
/* 1292 */       double d4 = paramDouble3 + d3;
/*      */       
/* 1294 */       if (d1 == 0.0D || d2 == 0.0D || paramDouble3 == d4) {
/*      */         break;
/*      */       }
/*      */       
/* 1298 */       paramDouble3 = d4;
/*      */     } 
/* 1300 */     if (within(paramDouble3, d, 1000.0D * Math.ulp(d)) && inInterval(paramDouble3, paramDouble1, paramDouble2)) {
/* 1301 */       return paramDouble3;
/*      */     }
/* 1303 */     return d;
/*      */   }
/*      */   
/*      */   private static double bisectRootWithHint(double[] paramArrayOfdouble, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 1307 */     double d1 = Math.min(Math.abs(paramDouble3 - paramDouble1) / 64.0D, 0.0625D);
/* 1308 */     double d2 = Math.min(Math.abs(paramDouble3 - paramDouble2) / 64.0D, 0.0625D);
/* 1309 */     double d3 = paramDouble3 - d1;
/* 1310 */     double d4 = paramDouble3 + d2;
/* 1311 */     double d5 = solveEqn(paramArrayOfdouble, 3, d3);
/* 1312 */     double d6 = solveEqn(paramArrayOfdouble, 3, d4);
/* 1313 */     while (oppositeSigns(d5, d6)) {
/* 1314 */       if (d3 >= d4) {
/* 1315 */         return d3;
/*      */       }
/* 1317 */       paramDouble1 = d3;
/* 1318 */       paramDouble2 = d4;
/* 1319 */       d1 /= 64.0D;
/* 1320 */       d2 /= 64.0D;
/* 1321 */       d3 = paramDouble3 - d1;
/* 1322 */       d4 = paramDouble3 + d2;
/* 1323 */       d5 = solveEqn(paramArrayOfdouble, 3, d3);
/* 1324 */       d6 = solveEqn(paramArrayOfdouble, 3, d4);
/*      */     } 
/* 1326 */     if (d5 == 0.0D) {
/* 1327 */       return d3;
/*      */     }
/* 1329 */     if (d6 == 0.0D) {
/* 1330 */       return d4;
/*      */     }
/*      */     
/* 1333 */     return bisectRoot(paramArrayOfdouble, paramDouble1, paramDouble2);
/*      */   }
/*      */   
/*      */   private static double bisectRoot(double[] paramArrayOfdouble, double paramDouble1, double paramDouble2) {
/* 1337 */     double d1 = solveEqn(paramArrayOfdouble, 3, paramDouble1);
/* 1338 */     double d2 = paramDouble1 + (paramDouble2 - paramDouble1) / 2.0D;
/* 1339 */     while (d2 != paramDouble1 && d2 != paramDouble2) {
/* 1340 */       double d = solveEqn(paramArrayOfdouble, 3, d2);
/* 1341 */       if (d == 0.0D) {
/* 1342 */         return d2;
/*      */       }
/* 1344 */       if (oppositeSigns(d1, d)) {
/* 1345 */         paramDouble2 = d2;
/*      */       } else {
/* 1347 */         d1 = d;
/* 1348 */         paramDouble1 = d2;
/*      */       } 
/* 1350 */       d2 = paramDouble1 + (paramDouble2 - paramDouble1) / 2.0D;
/*      */     } 
/* 1352 */     return d2;
/*      */   }
/*      */   
/*      */   private static boolean inInterval(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 1356 */     return (paramDouble2 <= paramDouble1 && paramDouble1 <= paramDouble3);
/*      */   }
/*      */   
/*      */   private static boolean within(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 1360 */     double d = paramDouble2 - paramDouble1;
/* 1361 */     return (d <= paramDouble3 && d >= -paramDouble3);
/*      */   }
/*      */   
/*      */   private static boolean iszero(double paramDouble1, double paramDouble2) {
/* 1365 */     return within(paramDouble1, 0.0D, paramDouble2);
/*      */   }
/*      */   
/*      */   private static boolean oppositeSigns(double paramDouble1, double paramDouble2) {
/* 1369 */     return ((paramDouble1 < 0.0D && paramDouble2 > 0.0D) || (paramDouble1 > 0.0D && paramDouble2 < 0.0D));
/*      */   }
/*      */   
/*      */   private static double solveEqn(double[] paramArrayOfdouble, int paramInt, double paramDouble) {
/* 1373 */     double d = paramArrayOfdouble[paramInt];
/* 1374 */     while (--paramInt >= 0) {
/* 1375 */       d = d * paramDouble + paramArrayOfdouble[paramInt];
/*      */     }
/* 1377 */     return d;
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
/*      */   private static double getRootUpperBound(double[] paramArrayOfdouble) {
/* 1389 */     double d1 = paramArrayOfdouble[3];
/* 1390 */     double d2 = paramArrayOfdouble[2];
/* 1391 */     double d3 = paramArrayOfdouble[1];
/* 1392 */     double d4 = paramArrayOfdouble[0];
/*      */     
/* 1394 */     double d5 = 1.0D + Math.max(Math.max(Math.abs(d2), Math.abs(d3)), Math.abs(d4)) / Math.abs(d1);
/* 1395 */     d5 += Math.ulp(d5) + 1.0D;
/* 1396 */     return d5;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(double paramDouble1, double paramDouble2) {
/* 1405 */     if (paramDouble1 * 0.0D + paramDouble2 * 0.0D != 0.0D)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1411 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1415 */     double d1 = getX1();
/* 1416 */     double d2 = getY1();
/* 1417 */     double d3 = getX2();
/* 1418 */     double d4 = getY2();
/*      */ 
/*      */     
/* 1421 */     int i = Curve.pointCrossingsForLine(paramDouble1, paramDouble2, d1, d2, d3, d4) + Curve.pointCrossingsForCubic(paramDouble1, paramDouble2, d1, d2, 
/*      */         
/* 1423 */         getCtrlX1(), getCtrlY1(), 
/* 1424 */         getCtrlX2(), getCtrlY2(), d3, d4, 0);
/*      */     
/* 1426 */     return ((i & 0x1) == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(Point2D paramPoint2D) {
/* 1434 */     return contains(paramPoint2D.getX(), paramPoint2D.getY());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 1443 */     if (paramDouble3 <= 0.0D || paramDouble4 <= 0.0D) {
/* 1444 */       return false;
/*      */     }
/*      */     
/* 1447 */     int i = rectCrossings(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1453 */     return (i != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean intersects(Rectangle2D paramRectangle2D) {
/* 1461 */     return intersects(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 1469 */     if (paramDouble3 <= 0.0D || paramDouble4 <= 0.0D) {
/* 1470 */       return false;
/*      */     }
/*      */     
/* 1473 */     int i = rectCrossings(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/* 1474 */     return (i != 0 && i != Integer.MIN_VALUE);
/*      */   }
/*      */   
/*      */   private int rectCrossings(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 1478 */     int i = 0;
/* 1479 */     if (getX1() != getX2() || getY1() != getY2()) {
/* 1480 */       i = Curve.rectCrossingsForLine(i, paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4, 
/*      */ 
/*      */           
/* 1483 */           getX1(), getY1(), 
/* 1484 */           getX2(), getY2());
/* 1485 */       if (i == Integer.MIN_VALUE) {
/* 1486 */         return i;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1491 */     return Curve.rectCrossingsForCubic(i, paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4, 
/*      */ 
/*      */         
/* 1494 */         getX2(), getY2(), 
/* 1495 */         getCtrlX2(), getCtrlY2(), 
/* 1496 */         getCtrlX1(), getCtrlY1(), 
/* 1497 */         getX1(), getY1(), 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(Rectangle2D paramRectangle2D) {
/* 1505 */     return contains(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getBounds() {
/* 1513 */     return getBounds2D().getBounds();
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
/*      */   public PathIterator getPathIterator(AffineTransform paramAffineTransform) {
/* 1533 */     return new CubicIterator(this, paramAffineTransform);
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
/*      */   public PathIterator getPathIterator(AffineTransform paramAffineTransform, double paramDouble) {
/* 1556 */     return new FlatteningPathIterator(getPathIterator(paramAffineTransform), paramDouble);
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
/* 1569 */       return super.clone();
/* 1570 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/* 1572 */       throw new InternalError(cloneNotSupportedException);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/geom/CubicCurve2D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */