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
/*     */ class RoundRectIterator
/*     */   implements PathIterator
/*     */ {
/*     */   double x;
/*     */   double y;
/*     */   double w;
/*     */   double h;
/*     */   double aw;
/*     */   double ah;
/*     */   AffineTransform affine;
/*     */   int index;
/*     */   private static final double angle = 0.7853981633974483D;
/*     */   
/*     */   RoundRectIterator(RoundRectangle2D paramRoundRectangle2D, AffineTransform paramAffineTransform) {
/*  42 */     this.x = paramRoundRectangle2D.getX();
/*  43 */     this.y = paramRoundRectangle2D.getY();
/*  44 */     this.w = paramRoundRectangle2D.getWidth();
/*  45 */     this.h = paramRoundRectangle2D.getHeight();
/*  46 */     this.aw = Math.min(this.w, Math.abs(paramRoundRectangle2D.getArcWidth()));
/*  47 */     this.ah = Math.min(this.h, Math.abs(paramRoundRectangle2D.getArcHeight()));
/*  48 */     this.affine = paramAffineTransform;
/*  49 */     if (this.aw < 0.0D || this.ah < 0.0D)
/*     */     {
/*  51 */       this.index = ctrlpts.length;
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
/*  62 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/*  70 */     return (this.index >= ctrlpts.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void next() {
/*  79 */     this.index++;
/*     */   }
/*     */ 
/*     */   
/*  83 */   private static final double a = 1.0D - Math.cos(0.7853981633974483D);
/*  84 */   private static final double b = Math.tan(0.7853981633974483D);
/*  85 */   private static final double c = Math.sqrt(1.0D + b * b) - 1.0D + a;
/*  86 */   private static final double cv = 1.3333333333333333D * a * b / c;
/*  87 */   private static final double acv = (1.0D - cv) / 2.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   private static double[][] ctrlpts = new double[][] { { 0.0D, 0.0D, 0.0D, 0.5D }, { 0.0D, 0.0D, 1.0D, -0.5D }, { 0.0D, 0.0D, 1.0D, -acv, 0.0D, acv, 1.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.0D }, { 1.0D, -0.5D, 1.0D, 0.0D }, { 1.0D, -acv, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D, -acv, 1.0D, 0.0D, 1.0D, -0.5D }, { 1.0D, 0.0D, 0.0D, 0.5D }, { 1.0D, 0.0D, 0.0D, acv, 1.0D, -acv, 0.0D, 0.0D, 1.0D, -0.5D, 0.0D, 0.0D }, { 0.0D, 0.5D, 0.0D, 0.0D }, { 0.0D, acv, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, acv, 0.0D, 0.0D, 0.0D, 0.5D }, {} };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   private static int[] types = new int[] { 0, 1, 3, 1, 3, 1, 3, 1, 3, 4 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 141 */     if (isDone()) {
/* 142 */       throw new NoSuchElementException("roundrect iterator out of bounds");
/*     */     }
/* 144 */     double[] arrayOfDouble = ctrlpts[this.index];
/* 145 */     byte b1 = 0;
/* 146 */     for (byte b2 = 0; b2 < arrayOfDouble.length; b2 += 4) {
/* 147 */       paramArrayOffloat[b1++] = (float)(this.x + arrayOfDouble[b2 + 0] * this.w + arrayOfDouble[b2 + 1] * this.aw);
/* 148 */       paramArrayOffloat[b1++] = (float)(this.y + arrayOfDouble[b2 + 2] * this.h + arrayOfDouble[b2 + 3] * this.ah);
/*     */     } 
/* 150 */     if (this.affine != null) {
/* 151 */       this.affine.transform(paramArrayOffloat, 0, paramArrayOffloat, 0, b1 / 2);
/*     */     }
/* 153 */     return types[this.index];
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
/* 175 */     if (isDone()) {
/* 176 */       throw new NoSuchElementException("roundrect iterator out of bounds");
/*     */     }
/* 178 */     double[] arrayOfDouble = ctrlpts[this.index];
/* 179 */     byte b1 = 0;
/* 180 */     for (byte b2 = 0; b2 < arrayOfDouble.length; b2 += 4) {
/* 181 */       paramArrayOfdouble[b1++] = this.x + arrayOfDouble[b2 + 0] * this.w + arrayOfDouble[b2 + 1] * this.aw;
/* 182 */       paramArrayOfdouble[b1++] = this.y + arrayOfDouble[b2 + 2] * this.h + arrayOfDouble[b2 + 3] * this.ah;
/*     */     } 
/* 184 */     if (this.affine != null) {
/* 185 */       this.affine.transform(paramArrayOfdouble, 0, paramArrayOfdouble, 0, b1 / 2);
/*     */     }
/* 187 */     return types[this.index];
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/geom/RoundRectIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */