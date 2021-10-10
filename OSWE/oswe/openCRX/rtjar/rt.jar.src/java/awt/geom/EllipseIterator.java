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
/*     */ class EllipseIterator
/*     */   implements PathIterator
/*     */ {
/*     */   double x;
/*     */   double y;
/*     */   double w;
/*     */   double h;
/*     */   AffineTransform affine;
/*     */   int index;
/*     */   public static final double CtrlVal = 0.5522847498307933D;
/*     */   private static final double pcv = 0.7761423749153966D;
/*     */   private static final double ncv = 0.22385762508460333D;
/*     */   
/*     */   EllipseIterator(Ellipse2D paramEllipse2D, AffineTransform paramAffineTransform) {
/*  42 */     this.x = paramEllipse2D.getX();
/*  43 */     this.y = paramEllipse2D.getY();
/*  44 */     this.w = paramEllipse2D.getWidth();
/*  45 */     this.h = paramEllipse2D.getHeight();
/*  46 */     this.affine = paramAffineTransform;
/*  47 */     if (this.w < 0.0D || this.h < 0.0D) {
/*  48 */       this.index = 6;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWindingRule() {
/*  59 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/*  67 */     return (this.index > 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void next() {
/*  76 */     this.index++;
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
/*  89 */   private static double[][] ctrlpts = new double[][] { { 1.0D, 0.7761423749153966D, 0.7761423749153966D, 1.0D, 0.5D, 1.0D }, { 0.22385762508460333D, 1.0D, 0.0D, 0.7761423749153966D, 0.0D, 0.5D }, { 0.0D, 0.22385762508460333D, 0.22385762508460333D, 0.0D, 0.5D, 0.0D }, { 0.7761423749153966D, 0.0D, 1.0D, 0.22385762508460333D, 1.0D, 0.5D } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 115 */     if (isDone()) {
/* 116 */       throw new NoSuchElementException("ellipse iterator out of bounds");
/*     */     }
/* 118 */     if (this.index == 5) {
/* 119 */       return 4;
/*     */     }
/* 121 */     if (this.index == 0) {
/* 122 */       double[] arrayOfDouble1 = ctrlpts[3];
/* 123 */       paramArrayOffloat[0] = (float)(this.x + arrayOfDouble1[4] * this.w);
/* 124 */       paramArrayOffloat[1] = (float)(this.y + arrayOfDouble1[5] * this.h);
/* 125 */       if (this.affine != null) {
/* 126 */         this.affine.transform(paramArrayOffloat, 0, paramArrayOffloat, 0, 1);
/*     */       }
/* 128 */       return 0;
/*     */     } 
/* 130 */     double[] arrayOfDouble = ctrlpts[this.index - 1];
/* 131 */     paramArrayOffloat[0] = (float)(this.x + arrayOfDouble[0] * this.w);
/* 132 */     paramArrayOffloat[1] = (float)(this.y + arrayOfDouble[1] * this.h);
/* 133 */     paramArrayOffloat[2] = (float)(this.x + arrayOfDouble[2] * this.w);
/* 134 */     paramArrayOffloat[3] = (float)(this.y + arrayOfDouble[3] * this.h);
/* 135 */     paramArrayOffloat[4] = (float)(this.x + arrayOfDouble[4] * this.w);
/* 136 */     paramArrayOffloat[5] = (float)(this.y + arrayOfDouble[5] * this.h);
/* 137 */     if (this.affine != null) {
/* 138 */       this.affine.transform(paramArrayOffloat, 0, paramArrayOffloat, 0, 3);
/*     */     }
/* 140 */     return 3;
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
/*     */   public int currentSegment(double[] paramArrayOfdouble) {
/* 162 */     if (isDone()) {
/* 163 */       throw new NoSuchElementException("ellipse iterator out of bounds");
/*     */     }
/* 165 */     if (this.index == 5) {
/* 166 */       return 4;
/*     */     }
/* 168 */     if (this.index == 0) {
/* 169 */       double[] arrayOfDouble1 = ctrlpts[3];
/* 170 */       paramArrayOfdouble[0] = this.x + arrayOfDouble1[4] * this.w;
/* 171 */       paramArrayOfdouble[1] = this.y + arrayOfDouble1[5] * this.h;
/* 172 */       if (this.affine != null) {
/* 173 */         this.affine.transform(paramArrayOfdouble, 0, paramArrayOfdouble, 0, 1);
/*     */       }
/* 175 */       return 0;
/*     */     } 
/* 177 */     double[] arrayOfDouble = ctrlpts[this.index - 1];
/* 178 */     paramArrayOfdouble[0] = this.x + arrayOfDouble[0] * this.w;
/* 179 */     paramArrayOfdouble[1] = this.y + arrayOfDouble[1] * this.h;
/* 180 */     paramArrayOfdouble[2] = this.x + arrayOfDouble[2] * this.w;
/* 181 */     paramArrayOfdouble[3] = this.y + arrayOfDouble[3] * this.h;
/* 182 */     paramArrayOfdouble[4] = this.x + arrayOfDouble[4] * this.w;
/* 183 */     paramArrayOfdouble[5] = this.y + arrayOfDouble[5] * this.h;
/* 184 */     if (this.affine != null) {
/* 185 */       this.affine.transform(paramArrayOfdouble, 0, paramArrayOfdouble, 0, 3);
/*     */     }
/* 187 */     return 3;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/geom/EllipseIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */