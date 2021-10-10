/*     */ package java.awt.print;
/*     */ 
/*     */ import java.awt.geom.Rectangle2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Paper
/*     */   implements Cloneable
/*     */ {
/*     */   private static final int INCH = 72;
/*     */   private static final double LETTER_WIDTH = 612.0D;
/*     */   private static final double LETTER_HEIGHT = 792.0D;
/*  88 */   private double mHeight = 792.0D;
/*  89 */   private double mWidth = 612.0D;
/*  90 */   private Rectangle2D mImageableArea = new Rectangle2D.Double(72.0D, 72.0D, this.mWidth - 144.0D, this.mHeight - 144.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */     Object object;
/*     */     try {
/* 111 */       object = super.clone();
/*     */     }
/* 113 */     catch (CloneNotSupportedException cloneNotSupportedException) {
/* 114 */       cloneNotSupportedException.printStackTrace();
/* 115 */       object = null;
/*     */     } 
/*     */     
/* 118 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getHeight() {
/* 127 */     return this.mHeight;
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
/*     */   public void setSize(double paramDouble1, double paramDouble2) {
/* 142 */     this.mWidth = paramDouble1;
/* 143 */     this.mHeight = paramDouble2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getWidth() {
/* 153 */     return this.mWidth;
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
/*     */   public void setImageableArea(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 171 */     this.mImageableArea = new Rectangle2D.Double(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getImageableX() {
/* 180 */     return this.mImageableArea.getX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getImageableY() {
/* 189 */     return this.mImageableArea.getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getImageableWidth() {
/* 198 */     return this.mImageableArea.getWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getImageableHeight() {
/* 207 */     return this.mImageableArea.getHeight();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/print/Paper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */