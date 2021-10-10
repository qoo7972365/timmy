/*      */ package java.awt.geom;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Arc2D
/*      */   extends RectangularShape
/*      */ {
/*      */   public static final int OPEN = 0;
/*      */   public static final int CHORD = 1;
/*      */   public static final int PIE = 2;
/*      */   private int type;
/*      */   
/*      */   public static class Float
/*      */     extends Arc2D
/*      */     implements Serializable
/*      */   {
/*      */     public float x;
/*      */     public float y;
/*      */     public float width;
/*      */     public float height;
/*      */     public float start;
/*      */     public float extent;
/*      */     private static final long serialVersionUID = 9130893014586380278L;
/*      */     
/*      */     public Float() {
/*  140 */       super(0);
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
/*      */     public Float(int param1Int) {
/*  153 */       super(param1Int);
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
/*      */     public Float(float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6, int param1Int) {
/*  176 */       super(param1Int);
/*  177 */       this.x = param1Float1;
/*  178 */       this.y = param1Float2;
/*  179 */       this.width = param1Float3;
/*  180 */       this.height = param1Float4;
/*  181 */       this.start = param1Float5;
/*  182 */       this.extent = param1Float6;
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
/*      */     public Float(Rectangle2D param1Rectangle2D, float param1Float1, float param1Float2, int param1Int) {
/*  200 */       super(param1Int);
/*  201 */       this.x = (float)param1Rectangle2D.getX();
/*  202 */       this.y = (float)param1Rectangle2D.getY();
/*  203 */       this.width = (float)param1Rectangle2D.getWidth();
/*  204 */       this.height = (float)param1Rectangle2D.getHeight();
/*  205 */       this.start = param1Float1;
/*  206 */       this.extent = param1Float2;
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
/*      */     public double getX() {
/*  218 */       return this.x;
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
/*      */     public double getY() {
/*  230 */       return this.y;
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
/*      */     public double getWidth() {
/*  242 */       return this.width;
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
/*      */     public double getHeight() {
/*  254 */       return this.height;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getAngleStart() {
/*  262 */       return this.start;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getAngleExtent() {
/*  270 */       return this.extent;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/*  278 */       return (this.width <= 0.0D || this.height <= 0.0D);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setArc(double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, int param1Int) {
/*  287 */       setArcType(param1Int);
/*  288 */       this.x = (float)param1Double1;
/*  289 */       this.y = (float)param1Double2;
/*  290 */       this.width = (float)param1Double3;
/*  291 */       this.height = (float)param1Double4;
/*  292 */       this.start = (float)param1Double5;
/*  293 */       this.extent = (float)param1Double6;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setAngleStart(double param1Double) {
/*  301 */       this.start = (float)param1Double;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setAngleExtent(double param1Double) {
/*  309 */       this.extent = (float)param1Double;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Rectangle2D makeBounds(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/*  318 */       return new Rectangle2D.Float((float)param1Double1, (float)param1Double2, (float)param1Double3, (float)param1Double4);
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
/*      */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/*  344 */       param1ObjectOutputStream.defaultWriteObject();
/*      */       
/*  346 */       param1ObjectOutputStream.writeByte(getArcType());
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
/*      */     private void readObject(ObjectInputStream param1ObjectInputStream) throws ClassNotFoundException, IOException {
/*  366 */       param1ObjectInputStream.defaultReadObject();
/*      */       
/*      */       try {
/*  369 */         setArcType(param1ObjectInputStream.readByte());
/*  370 */       } catch (IllegalArgumentException illegalArgumentException) {
/*  371 */         throw new InvalidObjectException(illegalArgumentException.getMessage());
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
/*      */   public static class Double
/*      */     extends Arc2D
/*      */     implements Serializable
/*      */   {
/*      */     public double x;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double y;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double width;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double height;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double start;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double extent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final long serialVersionUID = 728264085846882001L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double() {
/*  433 */       super(0);
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
/*      */     public Double(int param1Int) {
/*  446 */       super(param1Int);
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
/*      */     public Double(double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, int param1Int) {
/*  469 */       super(param1Int);
/*  470 */       this.x = param1Double1;
/*  471 */       this.y = param1Double2;
/*  472 */       this.width = param1Double3;
/*  473 */       this.height = param1Double4;
/*  474 */       this.start = param1Double5;
/*  475 */       this.extent = param1Double6;
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
/*      */     public Double(Rectangle2D param1Rectangle2D, double param1Double1, double param1Double2, int param1Int) {
/*  493 */       super(param1Int);
/*  494 */       this.x = param1Rectangle2D.getX();
/*  495 */       this.y = param1Rectangle2D.getY();
/*  496 */       this.width = param1Rectangle2D.getWidth();
/*  497 */       this.height = param1Rectangle2D.getHeight();
/*  498 */       this.start = param1Double1;
/*  499 */       this.extent = param1Double2;
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
/*      */     public double getX() {
/*  511 */       return this.x;
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
/*      */     public double getY() {
/*  523 */       return this.y;
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
/*      */     public double getWidth() {
/*  535 */       return this.width;
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
/*      */     public double getHeight() {
/*  547 */       return this.height;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getAngleStart() {
/*  555 */       return this.start;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getAngleExtent() {
/*  563 */       return this.extent;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/*  571 */       return (this.width <= 0.0D || this.height <= 0.0D);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setArc(double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, int param1Int) {
/*  580 */       setArcType(param1Int);
/*  581 */       this.x = param1Double1;
/*  582 */       this.y = param1Double2;
/*  583 */       this.width = param1Double3;
/*  584 */       this.height = param1Double4;
/*  585 */       this.start = param1Double5;
/*  586 */       this.extent = param1Double6;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setAngleStart(double param1Double) {
/*  594 */       this.start = param1Double;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setAngleExtent(double param1Double) {
/*  602 */       this.extent = param1Double;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Rectangle2D makeBounds(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/*  611 */       return new Rectangle2D.Double(param1Double1, param1Double2, param1Double3, param1Double4);
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
/*      */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/*  636 */       param1ObjectOutputStream.defaultWriteObject();
/*      */       
/*  638 */       param1ObjectOutputStream.writeByte(getArcType());
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
/*      */     private void readObject(ObjectInputStream param1ObjectInputStream) throws ClassNotFoundException, IOException {
/*  658 */       param1ObjectInputStream.defaultReadObject();
/*      */       
/*      */       try {
/*  661 */         setArcType(param1ObjectInputStream.readByte());
/*  662 */       } catch (IllegalArgumentException illegalArgumentException) {
/*  663 */         throw new InvalidObjectException(illegalArgumentException.getMessage());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Arc2D() {
/*  685 */     this(0);
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
/*      */   protected Arc2D(int paramInt) {
/*  702 */     setArcType(paramInt);
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
/*      */   public abstract double getAngleStart();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract double getAngleExtent();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getArcType() {
/*  734 */     return this.type;
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
/*      */   public Point2D getStartPoint() {
/*  747 */     double d1 = Math.toRadians(-getAngleStart());
/*  748 */     double d2 = getX() + (Math.cos(d1) * 0.5D + 0.5D) * getWidth();
/*  749 */     double d3 = getY() + (Math.sin(d1) * 0.5D + 0.5D) * getHeight();
/*  750 */     return new Point2D.Double(d2, d3);
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
/*      */   public Point2D getEndPoint() {
/*  764 */     double d1 = Math.toRadians(-getAngleStart() - getAngleExtent());
/*  765 */     double d2 = getX() + (Math.cos(d1) * 0.5D + 0.5D) * getWidth();
/*  766 */     double d3 = getY() + (Math.sin(d1) * 0.5D + 0.5D) * getHeight();
/*  767 */     return new Point2D.Double(d2, d3);
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
/*      */   public abstract void setArc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setArc(Point2D paramPoint2D, Dimension2D paramDimension2D, double paramDouble1, double paramDouble2, int paramInt) {
/*  806 */     setArc(paramPoint2D.getX(), paramPoint2D.getY(), paramDimension2D.getWidth(), paramDimension2D.getHeight(), paramDouble1, paramDouble2, paramInt);
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
/*      */   public void setArc(Rectangle2D paramRectangle2D, double paramDouble1, double paramDouble2, int paramInt) {
/*  825 */     setArc(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight(), paramDouble1, paramDouble2, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setArc(Arc2D paramArc2D) {
/*  836 */     setArc(paramArc2D.getX(), paramArc2D.getY(), paramArc2D.getWidth(), paramArc2D.getHeight(), paramArc2D
/*  837 */         .getAngleStart(), paramArc2D.getAngleExtent(), paramArc2D.type);
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
/*      */   public void setArcByCenter(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, int paramInt) {
/*  856 */     setArc(paramDouble1 - paramDouble3, paramDouble2 - paramDouble3, paramDouble3 * 2.0D, paramDouble3 * 2.0D, paramDouble4, paramDouble5, paramInt);
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
/*      */   public void setArcByTangent(Point2D paramPoint2D1, Point2D paramPoint2D2, Point2D paramPoint2D3, double paramDouble) {
/*  880 */     double d1 = Math.atan2(paramPoint2D1.getY() - paramPoint2D2.getY(), paramPoint2D1
/*  881 */         .getX() - paramPoint2D2.getX());
/*  882 */     double d2 = Math.atan2(paramPoint2D3.getY() - paramPoint2D2.getY(), paramPoint2D3
/*  883 */         .getX() - paramPoint2D2.getX());
/*  884 */     double d3 = d2 - d1;
/*  885 */     if (d3 > Math.PI) {
/*  886 */       d2 -= 6.283185307179586D;
/*  887 */     } else if (d3 < -3.141592653589793D) {
/*  888 */       d2 += 6.283185307179586D;
/*      */     } 
/*  890 */     double d4 = (d1 + d2) / 2.0D;
/*  891 */     double d5 = Math.abs(d2 - d4);
/*  892 */     double d6 = paramDouble / Math.sin(d5);
/*  893 */     double d7 = paramPoint2D2.getX() + d6 * Math.cos(d4);
/*  894 */     double d8 = paramPoint2D2.getY() + d6 * Math.sin(d4);
/*      */     
/*  896 */     if (d1 < d2) {
/*  897 */       d1 -= 1.5707963267948966D;
/*  898 */       d2 += 1.5707963267948966D;
/*      */     } else {
/*  900 */       d1 += 1.5707963267948966D;
/*  901 */       d2 -= 1.5707963267948966D;
/*      */     } 
/*  903 */     d1 = Math.toDegrees(-d1);
/*  904 */     d2 = Math.toDegrees(-d2);
/*  905 */     d3 = d2 - d1;
/*  906 */     if (d3 < 0.0D) {
/*  907 */       d3 += 360.0D;
/*      */     } else {
/*  909 */       d3 -= 360.0D;
/*      */     } 
/*  911 */     setArcByCenter(d7, d8, paramDouble, d1, d3, this.type);
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
/*      */   public abstract void setAngleStart(double paramDouble);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void setAngleExtent(double paramDouble);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAngleStart(Point2D paramPoint2D) {
/*  945 */     double d1 = getHeight() * (paramPoint2D.getX() - getCenterX());
/*  946 */     double d2 = getWidth() * (paramPoint2D.getY() - getCenterY());
/*  947 */     setAngleStart(-Math.toDegrees(Math.atan2(d2, d1)));
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
/*      */   public void setAngles(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  966 */     double d1 = getCenterX();
/*  967 */     double d2 = getCenterY();
/*  968 */     double d3 = getWidth();
/*  969 */     double d4 = getHeight();
/*      */ 
/*      */ 
/*      */     
/*  973 */     double d5 = Math.atan2(d3 * (d2 - paramDouble2), d4 * (paramDouble1 - d1));
/*  974 */     double d6 = Math.atan2(d3 * (d2 - paramDouble4), d4 * (paramDouble3 - d1));
/*  975 */     d6 -= d5;
/*  976 */     if (d6 <= 0.0D) {
/*  977 */       d6 += 6.283185307179586D;
/*      */     }
/*  979 */     setAngleStart(Math.toDegrees(d5));
/*  980 */     setAngleExtent(Math.toDegrees(d6));
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
/*      */   public void setAngles(Point2D paramPoint2D1, Point2D paramPoint2D2) {
/*  999 */     setAngles(paramPoint2D1.getX(), paramPoint2D1.getY(), paramPoint2D2.getX(), paramPoint2D2.getY());
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
/*      */   public void setArcType(int paramInt) {
/* 1016 */     if (paramInt < 0 || paramInt > 2) {
/* 1017 */       throw new IllegalArgumentException("invalid type for Arc: " + paramInt);
/*      */     }
/* 1019 */     this.type = paramInt;
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
/*      */   public void setFrame(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 1031 */     setArc(paramDouble1, paramDouble2, paramDouble3, paramDouble4, getAngleStart(), getAngleExtent(), this.type);
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
/*      */   public Rectangle2D getBounds2D() {
/* 1051 */     if (isEmpty()) {
/* 1052 */       return makeBounds(getX(), getY(), getWidth(), getHeight());
/*      */     }
/*      */ 
/*      */     
/* 1056 */     double d4 = 0.0D, d3 = d4, d2 = d3, d1 = d2;
/*      */     
/* 1058 */     d1 = d2 = 1.0D;
/* 1059 */     d3 = d4 = -1.0D;
/*      */     
/* 1061 */     double d5 = 0.0D;
/* 1062 */     for (byte b = 0; b < 6; b++) {
/* 1063 */       if (b < 4) {
/*      */         
/* 1065 */         d5 += 90.0D;
/* 1066 */         if (!containsAngle(d5)) {
/*      */           continue;
/*      */         }
/* 1069 */       } else if (b == 4) {
/*      */         
/* 1071 */         d5 = getAngleStart();
/*      */       } else {
/*      */         
/* 1074 */         d5 += getAngleExtent();
/*      */       } 
/* 1076 */       double d8 = Math.toRadians(-d5);
/* 1077 */       double d9 = Math.cos(d8);
/* 1078 */       double d10 = Math.sin(d8);
/* 1079 */       d1 = Math.min(d1, d9);
/* 1080 */       d2 = Math.min(d2, d10);
/* 1081 */       d3 = Math.max(d3, d9);
/* 1082 */       d4 = Math.max(d4, d10); continue;
/*      */     } 
/* 1084 */     double d6 = getWidth();
/* 1085 */     double d7 = getHeight();
/* 1086 */     d3 = (d3 - d1) * 0.5D * d6;
/* 1087 */     d4 = (d4 - d2) * 0.5D * d7;
/* 1088 */     d1 = getX() + (d1 * 0.5D + 0.5D) * d6;
/* 1089 */     d2 = getY() + (d2 * 0.5D + 0.5D) * d7;
/* 1090 */     return makeBounds(d1, d2, d3, d4);
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
/*      */   protected abstract Rectangle2D makeBounds(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static double normalizeDegrees(double paramDouble) {
/* 1115 */     if (paramDouble > 180.0D) {
/* 1116 */       if (paramDouble <= 540.0D) {
/* 1117 */         paramDouble -= 360.0D;
/*      */       } else {
/* 1119 */         paramDouble = Math.IEEEremainder(paramDouble, 360.0D);
/*      */         
/* 1121 */         if (paramDouble == -180.0D) {
/* 1122 */           paramDouble = 180.0D;
/*      */         }
/*      */       } 
/* 1125 */     } else if (paramDouble <= -180.0D) {
/* 1126 */       if (paramDouble > -540.0D) {
/* 1127 */         paramDouble += 360.0D;
/*      */       } else {
/* 1129 */         paramDouble = Math.IEEEremainder(paramDouble, 360.0D);
/*      */         
/* 1131 */         if (paramDouble == -180.0D) {
/* 1132 */           paramDouble = 180.0D;
/*      */         }
/*      */       } 
/*      */     } 
/* 1136 */     return paramDouble;
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
/*      */   public boolean containsAngle(double paramDouble) {
/* 1150 */     double d = getAngleExtent();
/* 1151 */     boolean bool = (d < 0.0D) ? true : false;
/* 1152 */     if (bool) {
/* 1153 */       d = -d;
/*      */     }
/* 1155 */     if (d >= 360.0D) {
/* 1156 */       return true;
/*      */     }
/* 1158 */     paramDouble = normalizeDegrees(paramDouble) - normalizeDegrees(getAngleStart());
/* 1159 */     if (bool) {
/* 1160 */       paramDouble = -paramDouble;
/*      */     }
/* 1162 */     if (paramDouble < 0.0D) {
/* 1163 */       paramDouble += 360.0D;
/*      */     }
/*      */ 
/*      */     
/* 1167 */     return (paramDouble >= 0.0D && paramDouble < d);
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
/*      */   public boolean contains(double paramDouble1, double paramDouble2) {
/* 1185 */     double d1 = getWidth();
/* 1186 */     if (d1 <= 0.0D) {
/* 1187 */       return false;
/*      */     }
/* 1189 */     double d2 = (paramDouble1 - getX()) / d1 - 0.5D;
/* 1190 */     double d3 = getHeight();
/* 1191 */     if (d3 <= 0.0D) {
/* 1192 */       return false;
/*      */     }
/* 1194 */     double d4 = (paramDouble2 - getY()) / d3 - 0.5D;
/* 1195 */     double d5 = d2 * d2 + d4 * d4;
/* 1196 */     if (d5 >= 0.25D) {
/* 1197 */       return false;
/*      */     }
/* 1199 */     double d6 = Math.abs(getAngleExtent());
/* 1200 */     if (d6 >= 360.0D) {
/* 1201 */       return true;
/*      */     }
/* 1203 */     boolean bool = containsAngle(-Math.toDegrees(Math.atan2(d4, d2)));
/*      */     
/* 1205 */     if (this.type == 2) {
/* 1206 */       return bool;
/*      */     }
/*      */     
/* 1209 */     if (bool) {
/* 1210 */       if (d6 >= 180.0D) {
/* 1211 */         return true;
/*      */       
/*      */       }
/*      */     }
/* 1215 */     else if (d6 <= 180.0D) {
/* 1216 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1222 */     double d7 = Math.toRadians(-getAngleStart());
/* 1223 */     double d8 = Math.cos(d7);
/* 1224 */     double d9 = Math.sin(d7);
/* 1225 */     d7 += Math.toRadians(-getAngleExtent());
/* 1226 */     double d10 = Math.cos(d7);
/* 1227 */     double d11 = Math.sin(d7);
/*      */     
/* 1229 */     boolean bool1 = (Line2D.relativeCCW(d8, d9, d10, d11, 2.0D * d2, 2.0D * d4) * Line2D.relativeCCW(d8, d9, d10, d11, 0.0D, 0.0D) >= 0) ? true : false;
/* 1230 */     return bool ? (!bool1) : bool1;
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
/*      */   public boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 1248 */     double d1 = getWidth();
/* 1249 */     double d2 = getHeight();
/*      */     
/* 1251 */     if (paramDouble3 <= 0.0D || paramDouble4 <= 0.0D || d1 <= 0.0D || d2 <= 0.0D) {
/* 1252 */       return false;
/*      */     }
/* 1254 */     double d3 = getAngleExtent();
/* 1255 */     if (d3 == 0.0D) {
/* 1256 */       return false;
/*      */     }
/*      */     
/* 1259 */     double d4 = getX();
/* 1260 */     double d5 = getY();
/* 1261 */     double d6 = d4 + d1;
/* 1262 */     double d7 = d5 + d2;
/* 1263 */     double d8 = paramDouble1 + paramDouble3;
/* 1264 */     double d9 = paramDouble2 + paramDouble4;
/*      */ 
/*      */     
/* 1267 */     if (paramDouble1 >= d6 || paramDouble2 >= d7 || d8 <= d4 || d9 <= d5) {
/* 1268 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1272 */     double d10 = getCenterX();
/* 1273 */     double d11 = getCenterY();
/* 1274 */     Point2D point2D1 = getStartPoint();
/* 1275 */     Point2D point2D2 = getEndPoint();
/* 1276 */     double d12 = point2D1.getX();
/* 1277 */     double d13 = point2D1.getY();
/* 1278 */     double d14 = point2D2.getX();
/* 1279 */     double d15 = point2D2.getY();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1291 */     if (d11 >= paramDouble2 && d11 <= d9 && ((
/* 1292 */       d12 < d8 && d14 < d8 && d10 < d8 && d6 > paramDouble1 && 
/* 1293 */       containsAngle(0.0D)) || (d12 > paramDouble1 && d14 > paramDouble1 && d10 > paramDouble1 && d4 < d8 && 
/*      */       
/* 1295 */       containsAngle(180.0D)))) {
/* 1296 */       return true;
/*      */     }
/*      */     
/* 1299 */     if (d10 >= paramDouble1 && d10 <= d8 && ((
/* 1300 */       d13 > paramDouble2 && d15 > paramDouble2 && d11 > paramDouble2 && d5 < d9 && 
/* 1301 */       containsAngle(90.0D)) || (d13 < d9 && d15 < d9 && d11 < d9 && d7 > paramDouble2 && 
/*      */       
/* 1303 */       containsAngle(270.0D)))) {
/* 1304 */       return true;
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
/* 1315 */     Rectangle2D.Double double_ = new Rectangle2D.Double(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/* 1316 */     if (this.type == 2 || Math.abs(d3) > 180.0D) {
/*      */       
/* 1318 */       if (double_.intersectsLine(d10, d11, d12, d13) || double_
/* 1319 */         .intersectsLine(d10, d11, d14, d15)) {
/* 1320 */         return true;
/*      */       
/*      */       }
/*      */     }
/* 1324 */     else if (double_.intersectsLine(d12, d13, d14, d15)) {
/* 1325 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1330 */     if (contains(paramDouble1, paramDouble2) || contains(paramDouble1 + paramDouble3, paramDouble2) || 
/* 1331 */       contains(paramDouble1, paramDouble2 + paramDouble4) || contains(paramDouble1 + paramDouble3, paramDouble2 + paramDouble4)) {
/* 1332 */       return true;
/*      */     }
/*      */     
/* 1335 */     return false;
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
/*      */   public boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 1352 */     return contains(paramDouble1, paramDouble2, paramDouble3, paramDouble4, (Rectangle2D)null);
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
/*      */   public boolean contains(Rectangle2D paramRectangle2D) {
/* 1366 */     return contains(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight(), paramRectangle2D);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, Rectangle2D paramRectangle2D) {
/* 1371 */     if (!contains(paramDouble1, paramDouble2) || 
/* 1372 */       !contains(paramDouble1 + paramDouble3, paramDouble2) || 
/* 1373 */       !contains(paramDouble1, paramDouble2 + paramDouble4) || 
/* 1374 */       !contains(paramDouble1 + paramDouble3, paramDouble2 + paramDouble4)) {
/* 1375 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1380 */     if (this.type != 2 || Math.abs(getAngleExtent()) <= 180.0D) {
/* 1381 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1389 */     if (paramRectangle2D == null) {
/* 1390 */       paramRectangle2D = new Rectangle2D.Double(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*      */     }
/* 1392 */     double d1 = getWidth() / 2.0D;
/* 1393 */     double d2 = getHeight() / 2.0D;
/* 1394 */     double d3 = getX() + d1;
/* 1395 */     double d4 = getY() + d2;
/* 1396 */     double d5 = Math.toRadians(-getAngleStart());
/* 1397 */     double d6 = d3 + d1 * Math.cos(d5);
/* 1398 */     double d7 = d4 + d2 * Math.sin(d5);
/* 1399 */     if (paramRectangle2D.intersectsLine(d3, d4, d6, d7)) {
/* 1400 */       return false;
/*      */     }
/* 1402 */     d5 += Math.toRadians(-getAngleExtent());
/* 1403 */     d6 = d3 + d1 * Math.cos(d5);
/* 1404 */     d7 = d4 + d2 * Math.sin(d5);
/* 1405 */     return !paramRectangle2D.intersectsLine(d3, d4, d6, d7);
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
/* 1425 */     return new ArcIterator(this, paramAffineTransform);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1434 */     long l = Double.doubleToLongBits(getX());
/* 1435 */     l += Double.doubleToLongBits(getY()) * 37L;
/* 1436 */     l += Double.doubleToLongBits(getWidth()) * 43L;
/* 1437 */     l += Double.doubleToLongBits(getHeight()) * 47L;
/* 1438 */     l += Double.doubleToLongBits(getAngleStart()) * 53L;
/* 1439 */     l += Double.doubleToLongBits(getAngleExtent()) * 59L;
/* 1440 */     l += (getArcType() * 61);
/* 1441 */     return (int)l ^ (int)(l >> 32L);
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
/*      */   public boolean equals(Object paramObject) {
/* 1459 */     if (paramObject == this) {
/* 1460 */       return true;
/*      */     }
/* 1462 */     if (paramObject instanceof Arc2D) {
/* 1463 */       Arc2D arc2D = (Arc2D)paramObject;
/* 1464 */       return (getX() == arc2D.getX() && 
/* 1465 */         getY() == arc2D.getY() && 
/* 1466 */         getWidth() == arc2D.getWidth() && 
/* 1467 */         getHeight() == arc2D.getHeight() && 
/* 1468 */         getAngleStart() == arc2D.getAngleStart() && 
/* 1469 */         getAngleExtent() == arc2D.getAngleExtent() && 
/* 1470 */         getArcType() == arc2D.getArcType());
/*      */     } 
/* 1472 */     return false;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/geom/Arc2D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */