/*     */ package java.awt.geom;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Point2D
/*     */   implements Cloneable
/*     */ {
/*     */   public abstract double getX();
/*     */   
/*     */   public abstract double getY();
/*     */   
/*     */   public abstract void setLocation(double paramDouble1, double paramDouble2);
/*     */   
/*     */   public static class Float
/*     */     extends Point2D
/*     */     implements Serializable
/*     */   {
/*     */     public float x;
/*     */     public float y;
/*     */     private static final long serialVersionUID = -2870572449815403710L;
/*     */     
/*     */     public Float() {}
/*     */     
/*     */     public Float(float param1Float1, float param1Float2) {
/*  83 */       this.x = param1Float1;
/*  84 */       this.y = param1Float2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getX() {
/*  92 */       return this.x;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getY() {
/* 100 */       return this.y;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setLocation(double param1Double1, double param1Double2) {
/* 108 */       this.x = (float)param1Double1;
/* 109 */       this.y = (float)param1Double2;
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
/*     */     public void setLocation(float param1Float1, float param1Float2) {
/* 121 */       this.x = param1Float1;
/* 122 */       this.y = param1Float2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 132 */       return "Point2D.Float[" + this.x + ", " + this.y + "]";
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
/*     */   public static class Double
/*     */     extends Point2D
/*     */     implements Serializable
/*     */   {
/*     */     public double x;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double y;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final long serialVersionUID = 6150783262733311327L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Double() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Double(double param1Double1, double param1Double2) {
/* 180 */       this.x = param1Double1;
/* 181 */       this.y = param1Double2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getX() {
/* 189 */       return this.x;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getY() {
/* 197 */       return this.y;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setLocation(double param1Double1, double param1Double2) {
/* 205 */       this.x = param1Double1;
/* 206 */       this.y = param1Double2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 216 */       return "Point2D.Double[" + this.x + ", " + this.y + "]";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocation(Point2D paramPoint2D) {
/* 274 */     setLocation(paramPoint2D.getX(), paramPoint2D.getY());
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
/*     */   public static double distanceSq(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 291 */     paramDouble1 -= paramDouble3;
/* 292 */     paramDouble2 -= paramDouble4;
/* 293 */     return paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2;
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
/*     */   public static double distance(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 310 */     paramDouble1 -= paramDouble3;
/* 311 */     paramDouble2 -= paramDouble4;
/* 312 */     return Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2);
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
/*     */   public double distanceSq(double paramDouble1, double paramDouble2) {
/* 328 */     paramDouble1 -= getX();
/* 329 */     paramDouble2 -= getY();
/* 330 */     return paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2;
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
/*     */   public double distanceSq(Point2D paramPoint2D) {
/* 344 */     double d1 = paramPoint2D.getX() - getX();
/* 345 */     double d2 = paramPoint2D.getY() - getY();
/* 346 */     return d1 * d1 + d2 * d2;
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
/*     */   public double distance(double paramDouble1, double paramDouble2) {
/* 362 */     paramDouble1 -= getX();
/* 363 */     paramDouble2 -= getY();
/* 364 */     return Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2);
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
/*     */   public double distance(Point2D paramPoint2D) {
/* 378 */     double d1 = paramPoint2D.getX() - getX();
/* 379 */     double d2 = paramPoint2D.getY() - getY();
/* 380 */     return Math.sqrt(d1 * d1 + d2 * d2);
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
/*     */   public Object clone() {
/*     */     try {
/* 393 */       return super.clone();
/* 394 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 396 */       throw new InternalError(cloneNotSupportedException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 405 */     long l = Double.doubleToLongBits(getX());
/* 406 */     l ^= Double.doubleToLongBits(getY()) * 31L;
/* 407 */     return (int)l ^ (int)(l >> 32L);
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
/*     */   public boolean equals(Object paramObject) {
/* 422 */     if (paramObject instanceof Point2D) {
/* 423 */       Point2D point2D = (Point2D)paramObject;
/* 424 */       return (getX() == point2D.getX() && getY() == point2D.getY());
/*     */     } 
/* 426 */     return super.equals(paramObject);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/geom/Point2D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */