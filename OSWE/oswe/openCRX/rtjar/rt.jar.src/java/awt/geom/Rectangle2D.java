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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Rectangle2D
/*     */   extends RectangularShape
/*     */ {
/*     */   public static final int OUT_LEFT = 1;
/*     */   public static final int OUT_TOP = 2;
/*     */   public static final int OUT_RIGHT = 4;
/*     */   public static final int OUT_BOTTOM = 8;
/*     */   
/*     */   public abstract void setRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */   
/*     */   public static class Float
/*     */     extends Rectangle2D
/*     */     implements Serializable
/*     */   {
/*     */     public float x;
/*     */     public float y;
/*     */     public float width;
/*     */     public float height;
/*     */     private static final long serialVersionUID = 3798716824173675777L;
/*     */     
/*     */     public Float() {}
/*     */     
/*     */     public Float(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/* 129 */       setRect(param1Float1, param1Float2, param1Float3, param1Float4);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getX() {
/* 137 */       return this.x;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getY() {
/* 145 */       return this.y;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getWidth() {
/* 153 */       return this.width;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getHeight() {
/* 161 */       return this.height;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 169 */       return (this.width <= 0.0F || this.height <= 0.0F);
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
/*     */     public void setRect(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/* 185 */       this.x = param1Float1;
/* 186 */       this.y = param1Float2;
/* 187 */       this.width = param1Float3;
/* 188 */       this.height = param1Float4;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setRect(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/* 196 */       this.x = (float)param1Double1;
/* 197 */       this.y = (float)param1Double2;
/* 198 */       this.width = (float)param1Double3;
/* 199 */       this.height = (float)param1Double4;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setRect(Rectangle2D param1Rectangle2D) {
/* 207 */       this.x = (float)param1Rectangle2D.getX();
/* 208 */       this.y = (float)param1Rectangle2D.getY();
/* 209 */       this.width = (float)param1Rectangle2D.getWidth();
/* 210 */       this.height = (float)param1Rectangle2D.getHeight();
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
/*     */     public int outcode(double param1Double1, double param1Double2) {
/* 228 */       int i = 0;
/* 229 */       if (this.width <= 0.0F) {
/* 230 */         i |= 0x5;
/* 231 */       } else if (param1Double1 < this.x) {
/* 232 */         i |= 0x1;
/* 233 */       } else if (param1Double1 > this.x + this.width) {
/* 234 */         i |= 0x4;
/*     */       } 
/* 236 */       if (this.height <= 0.0F) {
/* 237 */         i |= 0xA;
/* 238 */       } else if (param1Double2 < this.y) {
/* 239 */         i |= 0x2;
/* 240 */       } else if (param1Double2 > this.y + this.height) {
/* 241 */         i |= 0x8;
/*     */       } 
/* 243 */       return i;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Rectangle2D getBounds2D() {
/* 251 */       return new Float(this.x, this.y, this.width, this.height);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Rectangle2D createIntersection(Rectangle2D param1Rectangle2D) {
/*     */       Rectangle2D.Double double_;
/* 260 */       if (param1Rectangle2D instanceof Float) {
/* 261 */         Float float_ = new Float();
/*     */       } else {
/* 263 */         double_ = new Rectangle2D.Double();
/*     */       } 
/* 265 */       Rectangle2D.intersect(this, param1Rectangle2D, double_);
/* 266 */       return double_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Rectangle2D createUnion(Rectangle2D param1Rectangle2D) {
/*     */       Rectangle2D.Double double_;
/* 275 */       if (param1Rectangle2D instanceof Float) {
/* 276 */         Float float_ = new Float();
/*     */       } else {
/* 278 */         double_ = new Rectangle2D.Double();
/*     */       } 
/* 280 */       Rectangle2D.union(this, param1Rectangle2D, double_);
/* 281 */       return double_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 292 */       return getClass().getName() + "[x=" + this.x + ",y=" + this.y + ",w=" + this.width + ",h=" + this.height + "]";
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
/*     */   public static class Double
/*     */     extends Rectangle2D
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
/*     */     
/*     */     public double y;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double width;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double height;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final long serialVersionUID = 7771313791441850493L;
/*     */ 
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
/*     */     
/*     */     public Double(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/* 362 */       setRect(param1Double1, param1Double2, param1Double3, param1Double4);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getX() {
/* 370 */       return this.x;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getY() {
/* 378 */       return this.y;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getWidth() {
/* 386 */       return this.width;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getHeight() {
/* 394 */       return this.height;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 402 */       return (this.width <= 0.0D || this.height <= 0.0D);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setRect(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/* 410 */       this.x = param1Double1;
/* 411 */       this.y = param1Double2;
/* 412 */       this.width = param1Double3;
/* 413 */       this.height = param1Double4;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setRect(Rectangle2D param1Rectangle2D) {
/* 421 */       this.x = param1Rectangle2D.getX();
/* 422 */       this.y = param1Rectangle2D.getY();
/* 423 */       this.width = param1Rectangle2D.getWidth();
/* 424 */       this.height = param1Rectangle2D.getHeight();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int outcode(double param1Double1, double param1Double2) {
/* 432 */       int i = 0;
/* 433 */       if (this.width <= 0.0D) {
/* 434 */         i |= 0x5;
/* 435 */       } else if (param1Double1 < this.x) {
/* 436 */         i |= 0x1;
/* 437 */       } else if (param1Double1 > this.x + this.width) {
/* 438 */         i |= 0x4;
/*     */       } 
/* 440 */       if (this.height <= 0.0D) {
/* 441 */         i |= 0xA;
/* 442 */       } else if (param1Double2 < this.y) {
/* 443 */         i |= 0x2;
/* 444 */       } else if (param1Double2 > this.y + this.height) {
/* 445 */         i |= 0x8;
/*     */       } 
/* 447 */       return i;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Rectangle2D getBounds2D() {
/* 455 */       return new Double(this.x, this.y, this.width, this.height);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Rectangle2D createIntersection(Rectangle2D param1Rectangle2D) {
/* 463 */       Double double_ = new Double();
/* 464 */       Rectangle2D.intersect(this, param1Rectangle2D, double_);
/* 465 */       return double_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Rectangle2D createUnion(Rectangle2D param1Rectangle2D) {
/* 473 */       Double double_ = new Double();
/* 474 */       Rectangle2D.union(this, param1Rectangle2D, double_);
/* 475 */       return double_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 486 */       return getClass().getName() + "[x=" + this.x + ",y=" + this.y + ",w=" + this.width + ",h=" + this.height + "]";
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
/*     */   public void setRect(Rectangle2D paramRectangle2D) {
/* 535 */     setRect(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
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
/*     */   public boolean intersectsLine(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*     */     int j;
/* 557 */     if ((j = outcode(paramDouble3, paramDouble4)) == 0)
/* 558 */       return true; 
/*     */     int i;
/* 560 */     while ((i = outcode(paramDouble1, paramDouble2)) != 0) {
/* 561 */       if ((i & j) != 0) {
/* 562 */         return false;
/*     */       }
/* 564 */       if ((i & 0x5) != 0) {
/* 565 */         double d1 = getX();
/* 566 */         if ((i & 0x4) != 0) {
/* 567 */           d1 += getWidth();
/*     */         }
/* 569 */         paramDouble2 += (d1 - paramDouble1) * (paramDouble4 - paramDouble2) / (paramDouble3 - paramDouble1);
/* 570 */         paramDouble1 = d1; continue;
/*     */       } 
/* 572 */       double d = getY();
/* 573 */       if ((i & 0x8) != 0) {
/* 574 */         d += getHeight();
/*     */       }
/* 576 */       paramDouble1 += (d - paramDouble2) * (paramDouble3 - paramDouble1) / (paramDouble4 - paramDouble2);
/* 577 */       paramDouble2 = d;
/*     */     } 
/*     */     
/* 580 */     return true;
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
/*     */   public boolean intersectsLine(Line2D paramLine2D) {
/* 594 */     return intersectsLine(paramLine2D.getX1(), paramLine2D.getY1(), paramLine2D.getX2(), paramLine2D.getY2());
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
/*     */   public abstract int outcode(double paramDouble1, double paramDouble2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int outcode(Point2D paramPoint2D) {
/* 631 */     return outcode(paramPoint2D.getX(), paramPoint2D.getY());
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
/*     */   public void setFrame(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 647 */     setRect(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle2D getBounds2D() {
/* 655 */     return (Rectangle2D)clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(double paramDouble1, double paramDouble2) {
/* 663 */     double d1 = getX();
/* 664 */     double d2 = getY();
/* 665 */     return (paramDouble1 >= d1 && paramDouble2 >= d2 && paramDouble1 < d1 + 
/*     */       
/* 667 */       getWidth() && paramDouble2 < d2 + 
/* 668 */       getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 676 */     if (isEmpty() || paramDouble3 <= 0.0D || paramDouble4 <= 0.0D) {
/* 677 */       return false;
/*     */     }
/* 679 */     double d1 = getX();
/* 680 */     double d2 = getY();
/* 681 */     return (paramDouble1 + paramDouble3 > d1 && paramDouble2 + paramDouble4 > d2 && paramDouble1 < d1 + 
/*     */       
/* 683 */       getWidth() && paramDouble2 < d2 + 
/* 684 */       getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 692 */     if (isEmpty() || paramDouble3 <= 0.0D || paramDouble4 <= 0.0D) {
/* 693 */       return false;
/*     */     }
/* 695 */     double d1 = getX();
/* 696 */     double d2 = getY();
/* 697 */     return (paramDouble1 >= d1 && paramDouble2 >= d2 && paramDouble1 + paramDouble3 <= d1 + 
/*     */       
/* 699 */       getWidth() && paramDouble2 + paramDouble4 <= d2 + 
/* 700 */       getHeight());
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
/*     */   public abstract Rectangle2D createIntersection(Rectangle2D paramRectangle2D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void intersect(Rectangle2D paramRectangle2D1, Rectangle2D paramRectangle2D2, Rectangle2D paramRectangle2D3) {
/* 735 */     double d1 = Math.max(paramRectangle2D1.getMinX(), paramRectangle2D2.getMinX());
/* 736 */     double d2 = Math.max(paramRectangle2D1.getMinY(), paramRectangle2D2.getMinY());
/* 737 */     double d3 = Math.min(paramRectangle2D1.getMaxX(), paramRectangle2D2.getMaxX());
/* 738 */     double d4 = Math.min(paramRectangle2D1.getMaxY(), paramRectangle2D2.getMaxY());
/* 739 */     paramRectangle2D3.setFrame(d1, d2, d3 - d1, d4 - d2);
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
/*     */   public abstract Rectangle2D createUnion(Rectangle2D paramRectangle2D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void union(Rectangle2D paramRectangle2D1, Rectangle2D paramRectangle2D2, Rectangle2D paramRectangle2D3) {
/* 774 */     double d1 = Math.min(paramRectangle2D1.getMinX(), paramRectangle2D2.getMinX());
/* 775 */     double d2 = Math.min(paramRectangle2D1.getMinY(), paramRectangle2D2.getMinY());
/* 776 */     double d3 = Math.max(paramRectangle2D1.getMaxX(), paramRectangle2D2.getMaxX());
/* 777 */     double d4 = Math.max(paramRectangle2D1.getMaxY(), paramRectangle2D2.getMaxY());
/* 778 */     paramRectangle2D3.setFrameFromDiagonal(d1, d2, d3, d4);
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
/*     */   public void add(double paramDouble1, double paramDouble2) {
/* 801 */     double d1 = Math.min(getMinX(), paramDouble1);
/* 802 */     double d2 = Math.max(getMaxX(), paramDouble1);
/* 803 */     double d3 = Math.min(getMinY(), paramDouble2);
/* 804 */     double d4 = Math.max(getMaxY(), paramDouble2);
/* 805 */     setRect(d1, d3, d2 - d1, d4 - d3);
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
/*     */   public void add(Point2D paramPoint2D) {
/* 827 */     add(paramPoint2D.getX(), paramPoint2D.getY());
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
/*     */   public void add(Rectangle2D paramRectangle2D) {
/* 839 */     double d1 = Math.min(getMinX(), paramRectangle2D.getMinX());
/* 840 */     double d2 = Math.max(getMaxX(), paramRectangle2D.getMaxX());
/* 841 */     double d3 = Math.min(getMinY(), paramRectangle2D.getMinY());
/* 842 */     double d4 = Math.max(getMaxY(), paramRectangle2D.getMaxY());
/* 843 */     setRect(d1, d3, d2 - d1, d4 - d3);
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
/*     */   public PathIterator getPathIterator(AffineTransform paramAffineTransform) {
/* 863 */     return new RectIterator(this, paramAffineTransform);
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
/* 888 */     return new RectIterator(this, paramAffineTransform);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 897 */     long l = Double.doubleToLongBits(getX());
/* 898 */     l += Double.doubleToLongBits(getY()) * 37L;
/* 899 */     l += Double.doubleToLongBits(getWidth()) * 43L;
/* 900 */     l += Double.doubleToLongBits(getHeight()) * 47L;
/* 901 */     return (int)l ^ (int)(l >> 32L);
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
/*     */   public boolean equals(Object paramObject) {
/* 918 */     if (paramObject == this) {
/* 919 */       return true;
/*     */     }
/* 921 */     if (paramObject instanceof Rectangle2D) {
/* 922 */       Rectangle2D rectangle2D = (Rectangle2D)paramObject;
/* 923 */       return (getX() == rectangle2D.getX() && 
/* 924 */         getY() == rectangle2D.getY() && 
/* 925 */         getWidth() == rectangle2D.getWidth() && 
/* 926 */         getHeight() == rectangle2D.getHeight());
/*     */     } 
/* 928 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/geom/Rectangle2D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */