/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.PathIterator;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import sun.awt.geom.Crossings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Polygon
/*     */   implements Shape, Serializable
/*     */ {
/*     */   public int npoints;
/*     */   public int[] xpoints;
/*     */   public int[] ypoints;
/*     */   protected Rectangle bounds;
/*     */   private static final long serialVersionUID = -6460061437900069969L;
/*     */   private static final int MIN_LENGTH = 4;
/*     */   
/*     */   public Polygon() {
/* 127 */     this.xpoints = new int[4];
/* 128 */     this.ypoints = new int[4];
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
/*     */   public Polygon(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 150 */     if (paramInt > paramArrayOfint1.length || paramInt > paramArrayOfint2.length) {
/* 151 */       throw new IndexOutOfBoundsException("npoints > xpoints.length || npoints > ypoints.length");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 156 */     if (paramInt < 0) {
/* 157 */       throw new NegativeArraySizeException("npoints < 0");
/*     */     }
/*     */ 
/*     */     
/* 161 */     this.npoints = paramInt;
/* 162 */     this.xpoints = Arrays.copyOf(paramArrayOfint1, paramInt);
/* 163 */     this.ypoints = Arrays.copyOf(paramArrayOfint2, paramInt);
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
/*     */   public void reset() {
/* 184 */     this.npoints = 0;
/* 185 */     this.bounds = null;
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
/*     */   public void invalidate() {
/* 201 */     this.bounds = null;
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
/*     */   public void translate(int paramInt1, int paramInt2) {
/* 213 */     for (byte b = 0; b < this.npoints; b++) {
/* 214 */       this.xpoints[b] = this.xpoints[b] + paramInt1;
/* 215 */       this.ypoints[b] = this.ypoints[b] + paramInt2;
/*     */     } 
/* 217 */     if (this.bounds != null) {
/* 218 */       this.bounds.translate(paramInt1, paramInt2);
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
/*     */   void calculateBounds(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 230 */     int i = Integer.MAX_VALUE;
/* 231 */     int j = Integer.MAX_VALUE;
/* 232 */     int k = Integer.MIN_VALUE;
/* 233 */     int m = Integer.MIN_VALUE;
/*     */     
/* 235 */     for (byte b = 0; b < paramInt; b++) {
/* 236 */       int n = paramArrayOfint1[b];
/* 237 */       i = Math.min(i, n);
/* 238 */       k = Math.max(k, n);
/* 239 */       int i1 = paramArrayOfint2[b];
/* 240 */       j = Math.min(j, i1);
/* 241 */       m = Math.max(m, i1);
/*     */     } 
/* 243 */     this.bounds = new Rectangle(i, j, k - i, m - j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updateBounds(int paramInt1, int paramInt2) {
/* 253 */     if (paramInt1 < this.bounds.x) {
/* 254 */       this.bounds.width += this.bounds.x - paramInt1;
/* 255 */       this.bounds.x = paramInt1;
/*     */     } else {
/*     */       
/* 258 */       this.bounds.width = Math.max(this.bounds.width, paramInt1 - this.bounds.x);
/*     */     } 
/*     */ 
/*     */     
/* 262 */     if (paramInt2 < this.bounds.y) {
/* 263 */       this.bounds.height += this.bounds.y - paramInt2;
/* 264 */       this.bounds.y = paramInt2;
/*     */     } else {
/*     */       
/* 267 */       this.bounds.height = Math.max(this.bounds.height, paramInt2 - this.bounds.y);
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
/*     */   public void addPoint(int paramInt1, int paramInt2) {
/* 286 */     if (this.npoints >= this.xpoints.length || this.npoints >= this.ypoints.length) {
/* 287 */       int i = this.npoints * 2;
/*     */ 
/*     */       
/* 290 */       if (i < 4) {
/* 291 */         i = 4;
/* 292 */       } else if ((i & i - 1) != 0) {
/* 293 */         i = Integer.highestOneBit(i);
/*     */       } 
/*     */       
/* 296 */       this.xpoints = Arrays.copyOf(this.xpoints, i);
/* 297 */       this.ypoints = Arrays.copyOf(this.ypoints, i);
/*     */     } 
/* 299 */     this.xpoints[this.npoints] = paramInt1;
/* 300 */     this.ypoints[this.npoints] = paramInt2;
/* 301 */     this.npoints++;
/* 302 */     if (this.bounds != null) {
/* 303 */       updateBounds(paramInt1, paramInt2);
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
/*     */   public Rectangle getBounds() {
/* 317 */     return getBoundingBox();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Rectangle getBoundingBox() {
/* 329 */     if (this.npoints == 0) {
/* 330 */       return new Rectangle();
/*     */     }
/* 332 */     if (this.bounds == null) {
/* 333 */       calculateBounds(this.xpoints, this.ypoints, this.npoints);
/*     */     }
/* 335 */     return this.bounds.getBounds();
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
/*     */   public boolean contains(Point paramPoint) {
/* 348 */     return contains(paramPoint.x, paramPoint.y);
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
/*     */   public boolean contains(int paramInt1, int paramInt2) {
/* 364 */     return contains(paramInt1, paramInt2);
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
/*     */   @Deprecated
/*     */   public boolean inside(int paramInt1, int paramInt2) {
/* 382 */     return contains(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle2D getBounds2D() {
/* 390 */     return getBounds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(double paramDouble1, double paramDouble2) {
/* 398 */     if (this.npoints <= 2 || !getBoundingBox().contains(paramDouble1, paramDouble2)) {
/* 399 */       return false;
/*     */     }
/* 401 */     byte b1 = 0;
/*     */     
/* 403 */     int i = this.xpoints[this.npoints - 1];
/* 404 */     int j = this.ypoints[this.npoints - 1];
/*     */     
/*     */     Object object1, object2;
/*     */     
/* 408 */     for (byte b2 = 0; b2 < this.npoints; object1 = SYNTHETIC_LOCAL_VARIABLE_8, object2 = SYNTHETIC_LOCAL_VARIABLE_9, b2++) {
/* 409 */       Object object; int k = this.xpoints[b2];
/* 410 */       int m = this.ypoints[b2];
/*     */       
/* 412 */       if (m == object2) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 417 */       if (k < object1) {
/* 418 */         if (paramDouble1 >= object1) {
/*     */           continue;
/*     */         }
/* 421 */         int n = k;
/*     */       } else {
/* 423 */         if (paramDouble1 >= k) {
/*     */           continue;
/*     */         }
/* 426 */         object = object1;
/*     */       } 
/*     */ 
/*     */       
/* 430 */       if (m < object2)
/* 431 */       { if (paramDouble2 >= m && paramDouble2 < object2)
/*     */         {
/*     */           
/* 434 */           if (paramDouble1 < object)
/* 435 */           { b1++; }
/*     */           else
/*     */           
/* 438 */           { double d1 = paramDouble1 - k;
/* 439 */             double d2 = paramDouble2 - m;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 452 */             if (d1 < d2 / (object2 - m) * (object1 - k))
/* 453 */               b1++;  }  }  } else if (paramDouble2 >= object2 && paramDouble2 < m) { if (paramDouble1 < object) { b1++; } else { double d1 = paramDouble1 - object1; double d2 = paramDouble2 - object2; if (d1 < d2 / (object2 - m) * (object1 - k)) b1++;  }
/*     */          }
/*     */        continue;
/*     */     } 
/* 457 */     return ((b1 & 0x1) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Crossings getCrossings(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 463 */     Crossings.EvenOdd evenOdd = new Crossings.EvenOdd(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/* 464 */     int i = this.xpoints[this.npoints - 1];
/* 465 */     int j = this.ypoints[this.npoints - 1];
/*     */ 
/*     */ 
/*     */     
/* 469 */     for (byte b = 0; b < this.npoints; b++) {
/* 470 */       int k = this.xpoints[b];
/* 471 */       int m = this.ypoints[b];
/* 472 */       if (evenOdd.accumulateLine(i, j, k, m)) {
/* 473 */         return null;
/*     */       }
/* 475 */       i = k;
/* 476 */       j = m;
/*     */     } 
/*     */     
/* 479 */     return evenOdd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Point2D paramPoint2D) {
/* 487 */     return contains(paramPoint2D.getX(), paramPoint2D.getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 495 */     if (this.npoints <= 0 || !getBoundingBox().intersects(paramDouble1, paramDouble2, paramDouble3, paramDouble4)) {
/* 496 */       return false;
/*     */     }
/*     */     
/* 499 */     Crossings crossings = getCrossings(paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4);
/* 500 */     return (crossings == null || !crossings.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersects(Rectangle2D paramRectangle2D) {
/* 508 */     return intersects(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 516 */     if (this.npoints <= 0 || !getBoundingBox().intersects(paramDouble1, paramDouble2, paramDouble3, paramDouble4)) {
/* 517 */       return false;
/*     */     }
/*     */     
/* 520 */     Crossings crossings = getCrossings(paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4);
/* 521 */     return (crossings != null && crossings.covers(paramDouble2, paramDouble2 + paramDouble4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Rectangle2D paramRectangle2D) {
/* 529 */     return contains(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
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
/*     */   public PathIterator getPathIterator(AffineTransform paramAffineTransform) {
/* 546 */     return new PolygonPathIterator(this, paramAffineTransform);
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
/*     */   public PathIterator getPathIterator(AffineTransform paramAffineTransform, double paramDouble) {
/* 571 */     return getPathIterator(paramAffineTransform);
/*     */   }
/*     */   
/*     */   class PolygonPathIterator implements PathIterator {
/*     */     Polygon poly;
/*     */     AffineTransform transform;
/*     */     int index;
/*     */     
/*     */     public PolygonPathIterator(Polygon param1Polygon1, AffineTransform param1AffineTransform) {
/* 580 */       this.poly = param1Polygon1;
/* 581 */       this.transform = param1AffineTransform;
/* 582 */       if (param1Polygon1.npoints == 0)
/*     */       {
/* 584 */         this.index = 1;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getWindingRule() {
/* 595 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isDone() {
/* 604 */       return (this.index > this.poly.npoints);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void next() {
/* 613 */       this.index++;
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
/*     */     public int currentSegment(float[] param1ArrayOffloat) {
/* 635 */       if (this.index >= this.poly.npoints) {
/* 636 */         return 4;
/*     */       }
/* 638 */       param1ArrayOffloat[0] = this.poly.xpoints[this.index];
/* 639 */       param1ArrayOffloat[1] = this.poly.ypoints[this.index];
/* 640 */       if (this.transform != null) {
/* 641 */         this.transform.transform(param1ArrayOffloat, 0, param1ArrayOffloat, 0, 1);
/*     */       }
/* 643 */       return (this.index == 0) ? 0 : 1;
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
/*     */     public int currentSegment(double[] param1ArrayOfdouble) {
/* 666 */       if (this.index >= this.poly.npoints) {
/* 667 */         return 4;
/*     */       }
/* 669 */       param1ArrayOfdouble[0] = this.poly.xpoints[this.index];
/* 670 */       param1ArrayOfdouble[1] = this.poly.ypoints[this.index];
/* 671 */       if (this.transform != null) {
/* 672 */         this.transform.transform(param1ArrayOfdouble, 0, param1ArrayOfdouble, 0, 1);
/*     */       }
/* 674 */       return (this.index == 0) ? 0 : 1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/Polygon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */