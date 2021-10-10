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
/*     */ public abstract class RoundRectangle2D
/*     */   extends RectangularShape
/*     */ {
/*     */   public abstract double getArcWidth();
/*     */   
/*     */   public abstract double getArcHeight();
/*     */   
/*     */   public abstract void setRoundRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */   
/*     */   public static class Float
/*     */     extends RoundRectangle2D
/*     */     implements Serializable
/*     */   {
/*     */     public float x;
/*     */     public float y;
/*     */     public float width;
/*     */     public float height;
/*     */     public float arcwidth;
/*     */     public float archeight;
/*     */     private static final long serialVersionUID = -3423150618393866922L;
/*     */     
/*     */     public Float() {}
/*     */     
/*     */     public Float(float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6) {
/* 128 */       setRoundRect(param1Float1, param1Float2, param1Float3, param1Float4, param1Float5, param1Float6);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getX() {
/* 136 */       return this.x;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getY() {
/* 144 */       return this.y;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getWidth() {
/* 152 */       return this.width;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getHeight() {
/* 160 */       return this.height;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getArcWidth() {
/* 168 */       return this.arcwidth;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getArcHeight() {
/* 176 */       return this.archeight;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 184 */       return (this.width <= 0.0F || this.height <= 0.0F);
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
/*     */ 
/*     */     
/*     */     public void setRoundRect(float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6) {
/* 209 */       this.x = param1Float1;
/* 210 */       this.y = param1Float2;
/* 211 */       this.width = param1Float3;
/* 212 */       this.height = param1Float4;
/* 213 */       this.arcwidth = param1Float5;
/* 214 */       this.archeight = param1Float6;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setRoundRect(double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6) {
/* 224 */       this.x = (float)param1Double1;
/* 225 */       this.y = (float)param1Double2;
/* 226 */       this.width = (float)param1Double3;
/* 227 */       this.height = (float)param1Double4;
/* 228 */       this.arcwidth = (float)param1Double5;
/* 229 */       this.archeight = (float)param1Double6;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setRoundRect(RoundRectangle2D param1RoundRectangle2D) {
/* 237 */       this.x = (float)param1RoundRectangle2D.getX();
/* 238 */       this.y = (float)param1RoundRectangle2D.getY();
/* 239 */       this.width = (float)param1RoundRectangle2D.getWidth();
/* 240 */       this.height = (float)param1RoundRectangle2D.getHeight();
/* 241 */       this.arcwidth = (float)param1RoundRectangle2D.getArcWidth();
/* 242 */       this.archeight = (float)param1RoundRectangle2D.getArcHeight();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Rectangle2D getBounds2D() {
/* 250 */       return new Rectangle2D.Float(this.x, this.y, this.width, this.height);
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
/*     */     extends RoundRectangle2D
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
/*     */     
/*     */     public double arcwidth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double archeight;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final long serialVersionUID = 1048939333485206117L;
/*     */ 
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
/*     */     
/*     */     public Double(double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6) {
/* 341 */       setRoundRect(param1Double1, param1Double2, param1Double3, param1Double4, param1Double5, param1Double6);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getX() {
/* 349 */       return this.x;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getY() {
/* 357 */       return this.y;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getWidth() {
/* 365 */       return this.width;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getHeight() {
/* 373 */       return this.height;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getArcWidth() {
/* 381 */       return this.arcwidth;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double getArcHeight() {
/* 389 */       return this.archeight;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 397 */       return (this.width <= 0.0D || this.height <= 0.0D);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setRoundRect(double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6) {
/* 407 */       this.x = param1Double1;
/* 408 */       this.y = param1Double2;
/* 409 */       this.width = param1Double3;
/* 410 */       this.height = param1Double4;
/* 411 */       this.arcwidth = param1Double5;
/* 412 */       this.archeight = param1Double6;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setRoundRect(RoundRectangle2D param1RoundRectangle2D) {
/* 420 */       this.x = param1RoundRectangle2D.getX();
/* 421 */       this.y = param1RoundRectangle2D.getY();
/* 422 */       this.width = param1RoundRectangle2D.getWidth();
/* 423 */       this.height = param1RoundRectangle2D.getHeight();
/* 424 */       this.arcwidth = param1RoundRectangle2D.getArcWidth();
/* 425 */       this.archeight = param1RoundRectangle2D.getArcHeight();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Rectangle2D getBounds2D() {
/* 433 */       return new Rectangle2D.Double(this.x, this.y, this.width, this.height);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRoundRect(RoundRectangle2D paramRoundRectangle2D) {
/* 501 */     setRoundRect(paramRoundRectangle2D.getX(), paramRoundRectangle2D.getY(), paramRoundRectangle2D.getWidth(), paramRoundRectangle2D.getHeight(), paramRoundRectangle2D
/* 502 */         .getArcWidth(), paramRoundRectangle2D.getArcHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFrame(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 510 */     setRoundRect(paramDouble1, paramDouble2, paramDouble3, paramDouble4, getArcWidth(), getArcHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(double paramDouble1, double paramDouble2) {
/* 518 */     if (isEmpty()) {
/* 519 */       return false;
/*     */     }
/* 521 */     double d1 = getX();
/* 522 */     double d2 = getY();
/* 523 */     double d3 = d1 + getWidth();
/* 524 */     double d4 = d2 + getHeight();
/*     */     
/* 526 */     if (paramDouble1 < d1 || paramDouble2 < d2 || paramDouble1 >= d3 || paramDouble2 >= d4) {
/* 527 */       return false;
/*     */     }
/* 529 */     double d5 = Math.min(getWidth(), Math.abs(getArcWidth())) / 2.0D;
/* 530 */     double d6 = Math.min(getHeight(), Math.abs(getArcHeight())) / 2.0D;
/*     */ 
/*     */     
/* 533 */     if (paramDouble1 >= (d1 += d5) && paramDouble1 < (d1 = d3 - d5)) {
/* 534 */       return true;
/*     */     }
/* 536 */     if (paramDouble2 >= (d2 += d6) && paramDouble2 < (d2 = d4 - d6)) {
/* 537 */       return true;
/*     */     }
/* 539 */     paramDouble1 = (paramDouble1 - d1) / d5;
/* 540 */     paramDouble2 = (paramDouble2 - d2) / d6;
/* 541 */     return (paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2 <= 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int classify(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 547 */     if (paramDouble1 < paramDouble2)
/* 548 */       return 0; 
/* 549 */     if (paramDouble1 < paramDouble2 + paramDouble4)
/* 550 */       return 1; 
/* 551 */     if (paramDouble1 < paramDouble3 - paramDouble4)
/* 552 */       return 2; 
/* 553 */     if (paramDouble1 < paramDouble3) {
/* 554 */       return 3;
/*     */     }
/* 556 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 565 */     if (isEmpty() || paramDouble3 <= 0.0D || paramDouble4 <= 0.0D) {
/* 566 */       return false;
/*     */     }
/* 568 */     double d1 = getX();
/* 569 */     double d2 = getY();
/* 570 */     double d3 = d1 + getWidth();
/* 571 */     double d4 = d2 + getHeight();
/*     */     
/* 573 */     if (paramDouble1 + paramDouble3 <= d1 || paramDouble1 >= d3 || paramDouble2 + paramDouble4 <= d2 || paramDouble2 >= d4) {
/* 574 */       return false;
/*     */     }
/* 576 */     double d5 = Math.min(getWidth(), Math.abs(getArcWidth())) / 2.0D;
/* 577 */     double d6 = Math.min(getHeight(), Math.abs(getArcHeight())) / 2.0D;
/* 578 */     int i = classify(paramDouble1, d1, d3, d5);
/* 579 */     int j = classify(paramDouble1 + paramDouble3, d1, d3, d5);
/* 580 */     int k = classify(paramDouble2, d2, d4, d6);
/* 581 */     int m = classify(paramDouble2 + paramDouble4, d2, d4, d6);
/*     */     
/* 583 */     if (i == 2 || j == 2 || k == 2 || m == 2) {
/* 584 */       return true;
/*     */     }
/*     */     
/* 587 */     if ((i < 2 && j > 2) || (k < 2 && m > 2)) {
/* 588 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 596 */     paramDouble1 = (j == 1) ? (paramDouble1 = paramDouble1 + paramDouble3 - d1 + d5) : (paramDouble1 -= d3 - d5);
/* 597 */     paramDouble2 = (m == 1) ? (paramDouble2 = paramDouble2 + paramDouble4 - d2 + d6) : (paramDouble2 -= d4 - d6);
/* 598 */     paramDouble1 /= d5;
/* 599 */     paramDouble2 /= d6;
/* 600 */     return (paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2 <= 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 608 */     if (isEmpty() || paramDouble3 <= 0.0D || paramDouble4 <= 0.0D) {
/* 609 */       return false;
/*     */     }
/* 611 */     return (contains(paramDouble1, paramDouble2) && 
/* 612 */       contains(paramDouble1 + paramDouble3, paramDouble2) && 
/* 613 */       contains(paramDouble1, paramDouble2 + paramDouble4) && 
/* 614 */       contains(paramDouble1 + paramDouble3, paramDouble2 + paramDouble4));
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
/* 634 */     return new RoundRectIterator(this, paramAffineTransform);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 643 */     long l = Double.doubleToLongBits(getX());
/* 644 */     l += Double.doubleToLongBits(getY()) * 37L;
/* 645 */     l += Double.doubleToLongBits(getWidth()) * 43L;
/* 646 */     l += Double.doubleToLongBits(getHeight()) * 47L;
/* 647 */     l += Double.doubleToLongBits(getArcWidth()) * 53L;
/* 648 */     l += Double.doubleToLongBits(getArcHeight()) * 59L;
/* 649 */     return (int)l ^ (int)(l >> 32L);
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
/*     */   public boolean equals(Object paramObject) {
/* 667 */     if (paramObject == this) {
/* 668 */       return true;
/*     */     }
/* 670 */     if (paramObject instanceof RoundRectangle2D) {
/* 671 */       RoundRectangle2D roundRectangle2D = (RoundRectangle2D)paramObject;
/* 672 */       return (getX() == roundRectangle2D.getX() && 
/* 673 */         getY() == roundRectangle2D.getY() && 
/* 674 */         getWidth() == roundRectangle2D.getWidth() && 
/* 675 */         getHeight() == roundRectangle2D.getHeight() && 
/* 676 */         getArcWidth() == roundRectangle2D.getArcWidth() && 
/* 677 */         getArcHeight() == roundRectangle2D.getArcHeight());
/*     */     } 
/* 679 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/geom/RoundRectangle2D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */