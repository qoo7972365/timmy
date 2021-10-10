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
/*     */ class QuadIterator
/*     */   implements PathIterator
/*     */ {
/*     */   QuadCurve2D quad;
/*     */   AffineTransform affine;
/*     */   int index;
/*     */   
/*     */   QuadIterator(QuadCurve2D paramQuadCurve2D, AffineTransform paramAffineTransform) {
/*  42 */     this.quad = paramQuadCurve2D;
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
/*     */     byte b;
/*  92 */     if (isDone()) {
/*  93 */       throw new NoSuchElementException("quad iterator iterator out of bounds");
/*     */     }
/*     */     
/*  96 */     if (this.index == 0) {
/*  97 */       paramArrayOffloat[0] = (float)this.quad.getX1();
/*  98 */       paramArrayOffloat[1] = (float)this.quad.getY1();
/*  99 */       b = 0;
/*     */     } else {
/* 101 */       paramArrayOffloat[0] = (float)this.quad.getCtrlX();
/* 102 */       paramArrayOffloat[1] = (float)this.quad.getCtrlY();
/* 103 */       paramArrayOffloat[2] = (float)this.quad.getX2();
/* 104 */       paramArrayOffloat[3] = (float)this.quad.getY2();
/* 105 */       b = 2;
/*     */     } 
/* 107 */     if (this.affine != null) {
/* 108 */       this.affine.transform(paramArrayOffloat, 0, paramArrayOffloat, 0, (this.index == 0) ? 1 : 2);
/*     */     }
/* 110 */     return b;
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
/*     */     byte b;
/* 132 */     if (isDone()) {
/* 133 */       throw new NoSuchElementException("quad iterator iterator out of bounds");
/*     */     }
/*     */     
/* 136 */     if (this.index == 0) {
/* 137 */       paramArrayOfdouble[0] = this.quad.getX1();
/* 138 */       paramArrayOfdouble[1] = this.quad.getY1();
/* 139 */       b = 0;
/*     */     } else {
/* 141 */       paramArrayOfdouble[0] = this.quad.getCtrlX();
/* 142 */       paramArrayOfdouble[1] = this.quad.getCtrlY();
/* 143 */       paramArrayOfdouble[2] = this.quad.getX2();
/* 144 */       paramArrayOfdouble[3] = this.quad.getY2();
/* 145 */       b = 2;
/*     */     } 
/* 147 */     if (this.affine != null) {
/* 148 */       this.affine.transform(paramArrayOfdouble, 0, paramArrayOfdouble, 0, (this.index == 0) ? 1 : 2);
/*     */     }
/* 150 */     return b;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/geom/QuadIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */