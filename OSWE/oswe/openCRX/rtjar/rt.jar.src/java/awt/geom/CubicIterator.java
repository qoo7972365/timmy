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
/*     */ class CubicIterator
/*     */   implements PathIterator
/*     */ {
/*     */   CubicCurve2D cubic;
/*     */   AffineTransform affine;
/*     */   int index;
/*     */   
/*     */   CubicIterator(CubicCurve2D paramCubicCurve2D, AffineTransform paramAffineTransform) {
/*  42 */     this.cubic = paramCubicCurve2D;
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
/*  93 */       throw new NoSuchElementException("cubic iterator iterator out of bounds");
/*     */     }
/*     */     
/*  96 */     if (this.index == 0) {
/*  97 */       paramArrayOffloat[0] = (float)this.cubic.getX1();
/*  98 */       paramArrayOffloat[1] = (float)this.cubic.getY1();
/*  99 */       b = 0;
/*     */     } else {
/* 101 */       paramArrayOffloat[0] = (float)this.cubic.getCtrlX1();
/* 102 */       paramArrayOffloat[1] = (float)this.cubic.getCtrlY1();
/* 103 */       paramArrayOffloat[2] = (float)this.cubic.getCtrlX2();
/* 104 */       paramArrayOffloat[3] = (float)this.cubic.getCtrlY2();
/* 105 */       paramArrayOffloat[4] = (float)this.cubic.getX2();
/* 106 */       paramArrayOffloat[5] = (float)this.cubic.getY2();
/* 107 */       b = 3;
/*     */     } 
/* 109 */     if (this.affine != null) {
/* 110 */       this.affine.transform(paramArrayOffloat, 0, paramArrayOffloat, 0, (this.index == 0) ? 1 : 3);
/*     */     }
/* 112 */     return b;
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
/* 134 */     if (isDone()) {
/* 135 */       throw new NoSuchElementException("cubic iterator iterator out of bounds");
/*     */     }
/*     */     
/* 138 */     if (this.index == 0) {
/* 139 */       paramArrayOfdouble[0] = this.cubic.getX1();
/* 140 */       paramArrayOfdouble[1] = this.cubic.getY1();
/* 141 */       b = 0;
/*     */     } else {
/* 143 */       paramArrayOfdouble[0] = this.cubic.getCtrlX1();
/* 144 */       paramArrayOfdouble[1] = this.cubic.getCtrlY1();
/* 145 */       paramArrayOfdouble[2] = this.cubic.getCtrlX2();
/* 146 */       paramArrayOfdouble[3] = this.cubic.getCtrlY2();
/* 147 */       paramArrayOfdouble[4] = this.cubic.getX2();
/* 148 */       paramArrayOfdouble[5] = this.cubic.getY2();
/* 149 */       b = 3;
/*     */     } 
/* 151 */     if (this.affine != null) {
/* 152 */       this.affine.transform(paramArrayOfdouble, 0, paramArrayOfdouble, 0, (this.index == 0) ? 1 : 3);
/*     */     }
/* 154 */     return b;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/geom/CubicIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */