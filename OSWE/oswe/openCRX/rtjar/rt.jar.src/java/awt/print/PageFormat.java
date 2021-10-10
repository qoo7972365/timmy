/*     */ package java.awt.print;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PageFormat
/*     */   implements Cloneable
/*     */ {
/*     */   public static final int LANDSCAPE = 0;
/*     */   public static final int PORTRAIT = 1;
/*     */   public static final int REVERSE_LANDSCAPE = 2;
/*     */   private Paper mPaper;
/*  77 */   private int mOrientation = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageFormat() {
/*  87 */     this.mPaper = new Paper();
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
/*     */     Object object;
/*     */     try {
/* 101 */       object = super.clone();
/* 102 */       ((PageFormat)object).mPaper = (Paper)this.mPaper.clone();
/*     */     }
/* 104 */     catch (CloneNotSupportedException cloneNotSupportedException) {
/* 105 */       cloneNotSupportedException.printStackTrace();
/* 106 */       object = null;
/*     */     } 
/*     */     
/* 109 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getWidth() {
/*     */     double d;
/* 121 */     int i = getOrientation();
/*     */     
/* 123 */     if (i == 1) {
/* 124 */       d = this.mPaper.getWidth();
/*     */     } else {
/* 126 */       d = this.mPaper.getHeight();
/*     */     } 
/*     */     
/* 129 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getHeight() {
/*     */     double d;
/* 140 */     int i = getOrientation();
/*     */     
/* 142 */     if (i == 1) {
/* 143 */       d = this.mPaper.getHeight();
/*     */     } else {
/* 145 */       d = this.mPaper.getWidth();
/*     */     } 
/*     */     
/* 148 */     return d;
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
/*     */   public double getImageableX() {
/*     */     double d;
/* 164 */     switch (getOrientation()) {
/*     */ 
/*     */       
/*     */       case 0:
/* 168 */         d = this.mPaper.getHeight() - this.mPaper.getImageableY() + this.mPaper.getImageableHeight();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 187 */         return d;case 1: d = this.mPaper.getImageableX(); return d;case 2: d = this.mPaper.getImageableY(); return d;
/*     */     } 
/*     */     throw new InternalError("unrecognized orientation");
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
/*     */   public double getImageableY() {
/*     */     double d;
/* 203 */     switch (getOrientation()) {
/*     */       
/*     */       case 0:
/* 206 */         d = this.mPaper.getImageableX();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 226 */         return d;case 1: d = this.mPaper.getImageableY(); return d;case 2: d = this.mPaper.getWidth() - this.mPaper.getImageableX() + this.mPaper.getImageableWidth(); return d;
/*     */     } 
/*     */     throw new InternalError("unrecognized orientation");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getImageableWidth() {
/*     */     double d;
/* 238 */     if (getOrientation() == 1) {
/* 239 */       d = this.mPaper.getImageableWidth();
/*     */     } else {
/* 241 */       d = this.mPaper.getImageableHeight();
/*     */     } 
/*     */     
/* 244 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getImageableHeight() {
/*     */     double d;
/* 256 */     if (getOrientation() == 1) {
/* 257 */       d = this.mPaper.getImageableHeight();
/*     */     } else {
/* 259 */       d = this.mPaper.getImageableWidth();
/*     */     } 
/*     */     
/* 262 */     return d;
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
/*     */   public Paper getPaper() {
/* 281 */     return (Paper)this.mPaper.clone();
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
/*     */   public void setPaper(Paper paramPaper) {
/* 294 */     this.mPaper = (Paper)paramPaper.clone();
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
/*     */   public void setOrientation(int paramInt) throws IllegalArgumentException {
/* 308 */     if (0 <= paramInt && paramInt <= 2) {
/* 309 */       this.mOrientation = paramInt;
/*     */     } else {
/* 311 */       throw new IllegalArgumentException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOrientation() {
/* 321 */     return this.mOrientation;
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
/*     */   public double[] getMatrix() {
/* 337 */     double[] arrayOfDouble = new double[6];
/*     */     
/* 339 */     switch (this.mOrientation) {
/*     */       
/*     */       case 0:
/* 342 */         arrayOfDouble[0] = 0.0D; arrayOfDouble[1] = -1.0D;
/* 343 */         arrayOfDouble[2] = 1.0D; arrayOfDouble[3] = 0.0D;
/* 344 */         arrayOfDouble[4] = 0.0D; arrayOfDouble[5] = this.mPaper.getHeight();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 363 */         return arrayOfDouble;case 1: arrayOfDouble[0] = 1.0D; arrayOfDouble[1] = 0.0D; arrayOfDouble[2] = 0.0D; arrayOfDouble[3] = 1.0D; arrayOfDouble[4] = 0.0D; arrayOfDouble[5] = 0.0D; return arrayOfDouble;case 2: arrayOfDouble[0] = 0.0D; arrayOfDouble[1] = 1.0D; arrayOfDouble[2] = -1.0D; arrayOfDouble[3] = 0.0D; arrayOfDouble[4] = this.mPaper.getWidth(); arrayOfDouble[5] = 0.0D; return arrayOfDouble;
/*     */     } 
/*     */     throw new IllegalArgumentException();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/print/PageFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */