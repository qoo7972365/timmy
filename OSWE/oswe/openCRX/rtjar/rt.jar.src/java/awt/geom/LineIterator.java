/*     */ package java.awt.geom;
/*     */ 
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class LineIterator
/*     */   implements PathIterator
/*     */ {
/*     */   Line2D line;
/*     */   AffineTransform affine;
/*     */   int index;
/*     */   
/*     */   LineIterator(Line2D paramLine2D, AffineTransform paramAffineTransform) {
/*  42 */     this.line = paramLine2D;
/*  43 */     this.affine = paramAffineTransform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWindingRule() {
/*  53 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/*  61 */     return (this.index > 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void next() {
/*  70 */     this.index++;
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
/*     */   public int currentSegment(float[] paramArrayOffloat) {
/*     */     boolean bool;
/*  92 */     if (isDone()) {
/*  93 */       throw new NoSuchElementException("line iterator out of bounds");
/*     */     }
/*     */     
/*  96 */     if (this.index == 0) {
/*  97 */       paramArrayOffloat[0] = (float)this.line.getX1();
/*  98 */       paramArrayOffloat[1] = (float)this.line.getY1();
/*  99 */       bool = false;
/*     */     } else {
/* 101 */       paramArrayOffloat[0] = (float)this.line.getX2();
/* 102 */       paramArrayOffloat[1] = (float)this.line.getY2();
/* 103 */       bool = true;
/*     */     } 
/* 105 */     if (this.affine != null) {
/* 106 */       this.affine.transform(paramArrayOffloat, 0, paramArrayOffloat, 0, 1);
/*     */     }
/* 108 */     return bool;
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
/*     */   public int currentSegment(double[] paramArrayOfdouble) {
/*     */     boolean bool;
/* 130 */     if (isDone()) {
/* 131 */       throw new NoSuchElementException("line iterator out of bounds");
/*     */     }
/*     */     
/* 134 */     if (this.index == 0) {
/* 135 */       paramArrayOfdouble[0] = this.line.getX1();
/* 136 */       paramArrayOfdouble[1] = this.line.getY1();
/* 137 */       bool = false;
/*     */     } else {
/* 139 */       paramArrayOfdouble[0] = this.line.getX2();
/* 140 */       paramArrayOfdouble[1] = this.line.getY2();
/* 141 */       bool = true;
/*     */     } 
/* 143 */     if (this.affine != null) {
/* 144 */       this.affine.transform(paramArrayOfdouble, 0, paramArrayOfdouble, 0, 1);
/*     */     }
/* 146 */     return bool;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/geom/LineIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */