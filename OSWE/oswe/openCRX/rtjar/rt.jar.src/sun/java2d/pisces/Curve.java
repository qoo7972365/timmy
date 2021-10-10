/*     */ package sun.java2d.pisces;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Curve
/*     */ {
/*     */   float ax;
/*     */   float ay;
/*     */   float bx;
/*     */   float by;
/*     */   float cx;
/*     */   float cy;
/*     */   float dx;
/*     */   float dy;
/*     */   float dax;
/*     */   float day;
/*     */   float dbx;
/*     */   float dby;
/*     */   
/*     */   void set(float[] paramArrayOffloat, int paramInt) {
/*  39 */     switch (paramInt) {
/*     */       case 8:
/*  41 */         set(paramArrayOffloat[0], paramArrayOffloat[1], paramArrayOffloat[2], paramArrayOffloat[3], paramArrayOffloat[4], paramArrayOffloat[5], paramArrayOffloat[6], paramArrayOffloat[7]);
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case 6:
/*  47 */         set(paramArrayOffloat[0], paramArrayOffloat[1], paramArrayOffloat[2], paramArrayOffloat[3], paramArrayOffloat[4], paramArrayOffloat[5]);
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/*  52 */     throw new InternalError("Curves can only be cubic or quadratic");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8) {
/*  61 */     this.ax = 3.0F * (paramFloat3 - paramFloat5) + paramFloat7 - paramFloat1;
/*  62 */     this.ay = 3.0F * (paramFloat4 - paramFloat6) + paramFloat8 - paramFloat2;
/*  63 */     this.bx = 3.0F * (paramFloat1 - 2.0F * paramFloat3 + paramFloat5);
/*  64 */     this.by = 3.0F * (paramFloat2 - 2.0F * paramFloat4 + paramFloat6);
/*  65 */     this.cx = 3.0F * (paramFloat3 - paramFloat1);
/*  66 */     this.cy = 3.0F * (paramFloat4 - paramFloat2);
/*  67 */     this.dx = paramFloat1;
/*  68 */     this.dy = paramFloat2;
/*  69 */     this.dax = 3.0F * this.ax; this.day = 3.0F * this.ay;
/*  70 */     this.dbx = 2.0F * this.bx; this.dby = 2.0F * this.by;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
/*  77 */     this.ax = this.ay = 0.0F;
/*     */     
/*  79 */     this.bx = paramFloat1 - 2.0F * paramFloat3 + paramFloat5;
/*  80 */     this.by = paramFloat2 - 2.0F * paramFloat4 + paramFloat6;
/*  81 */     this.cx = 2.0F * (paramFloat3 - paramFloat1);
/*  82 */     this.cy = 2.0F * (paramFloat4 - paramFloat2);
/*  83 */     this.dx = paramFloat1;
/*  84 */     this.dy = paramFloat2;
/*  85 */     this.dax = 0.0F; this.day = 0.0F;
/*  86 */     this.dbx = 2.0F * this.bx; this.dby = 2.0F * this.by;
/*     */   }
/*     */   
/*     */   float xat(float paramFloat) {
/*  90 */     return paramFloat * (paramFloat * (paramFloat * this.ax + this.bx) + this.cx) + this.dx;
/*     */   }
/*     */   float yat(float paramFloat) {
/*  93 */     return paramFloat * (paramFloat * (paramFloat * this.ay + this.by) + this.cy) + this.dy;
/*     */   }
/*     */   
/*     */   float dxat(float paramFloat) {
/*  97 */     return paramFloat * (paramFloat * this.dax + this.dbx) + this.cx;
/*     */   }
/*     */   
/*     */   float dyat(float paramFloat) {
/* 101 */     return paramFloat * (paramFloat * this.day + this.dby) + this.cy;
/*     */   }
/*     */   
/*     */   int dxRoots(float[] paramArrayOffloat, int paramInt) {
/* 105 */     return Helpers.quadraticRoots(this.dax, this.dbx, this.cx, paramArrayOffloat, paramInt);
/*     */   }
/*     */   
/*     */   int dyRoots(float[] paramArrayOffloat, int paramInt) {
/* 109 */     return Helpers.quadraticRoots(this.day, this.dby, this.cy, paramArrayOffloat, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int infPoints(float[] paramArrayOffloat, int paramInt) {
/* 116 */     float f1 = this.dax * this.dby - this.dbx * this.day;
/* 117 */     float f2 = 2.0F * (this.cy * this.dax - this.day * this.cx);
/* 118 */     float f3 = this.cy * this.dbx - this.cx * this.dby;
/*     */     
/* 120 */     return Helpers.quadraticRoots(f1, f2, f3, paramArrayOffloat, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int perpendiculardfddf(float[] paramArrayOffloat, int paramInt) {
/* 127 */     assert paramArrayOffloat.length >= paramInt + 4;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     float f1 = 2.0F * (this.dax * this.dax + this.day * this.day);
/* 133 */     float f2 = 3.0F * (this.dax * this.dbx + this.day * this.dby);
/* 134 */     float f3 = 2.0F * (this.dax * this.cx + this.day * this.cy) + this.dbx * this.dbx + this.dby * this.dby;
/* 135 */     float f4 = this.dbx * this.cx + this.dby * this.cy;
/* 136 */     return Helpers.cubicRootsInAB(f1, f2, f3, f4, paramArrayOffloat, paramInt, 0.0F, 1.0F);
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
/*     */   int rootsOfROCMinusW(float[] paramArrayOffloat, int paramInt, float paramFloat1, float paramFloat2) {
/* 154 */     assert paramInt <= 6 && paramArrayOffloat.length >= 10;
/* 155 */     int i = paramInt;
/* 156 */     int j = perpendiculardfddf(paramArrayOffloat, paramInt);
/* 157 */     float f1 = 0.0F, f2 = ROCsq(f1) - paramFloat1 * paramFloat1;
/* 158 */     paramArrayOffloat[paramInt + j] = 1.0F;
/* 159 */     j++;
/* 160 */     for (int k = paramInt; k < paramInt + j; k++) {
/* 161 */       float f3 = paramArrayOffloat[k], f4 = ROCsq(f3) - paramFloat1 * paramFloat1;
/* 162 */       if (f2 == 0.0F) {
/* 163 */         paramArrayOffloat[i++] = f1;
/* 164 */       } else if (f4 * f2 < 0.0F) {
/*     */ 
/*     */         
/* 167 */         paramArrayOffloat[i++] = falsePositionROCsqMinusX(f1, f3, paramFloat1 * paramFloat1, paramFloat2);
/*     */       } 
/* 169 */       f1 = f3;
/* 170 */       f2 = f4;
/*     */     } 
/*     */     
/* 173 */     return i - paramInt;
/*     */   }
/*     */   
/*     */   private static float eliminateInf(float paramFloat) {
/* 177 */     return (paramFloat == Float.POSITIVE_INFINITY) ? Float.MAX_VALUE : ((paramFloat == Float.NEGATIVE_INFINITY) ? Float.MIN_VALUE : paramFloat);
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
/*     */   private float falsePositionROCsqMinusX(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 192 */     byte b = 0;
/* 193 */     float f1 = paramFloat2, f2 = eliminateInf(ROCsq(f1) - paramFloat3);
/* 194 */     float f3 = paramFloat1, f4 = eliminateInf(ROCsq(f3) - paramFloat3);
/* 195 */     float f5 = f3;
/* 196 */     for (byte b1 = 0; b1 < 100 && Math.abs(f1 - f3) > paramFloat4 * Math.abs(f1 + f3); b1++) {
/* 197 */       f5 = (f4 * f1 - f2 * f3) / (f4 - f2);
/* 198 */       float f = ROCsq(f5) - paramFloat3;
/* 199 */       if (sameSign(f, f2)) {
/* 200 */         f2 = f; f1 = f5;
/* 201 */         if (b) {
/* 202 */           f4 /= (1 << -b);
/* 203 */           b--;
/*     */         } else {
/* 205 */           b = -1;
/*     */         } 
/* 207 */       } else if (f * f4 > 0.0F) {
/* 208 */         f4 = f; f3 = f5;
/* 209 */         if (b > 0) {
/* 210 */           f2 /= (1 << b);
/* 211 */           b++;
/*     */         } else {
/* 213 */           b = 1;
/*     */         } 
/*     */       } else {
/*     */         break;
/*     */       } 
/*     */     } 
/* 219 */     return f5;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean sameSign(double paramDouble1, double paramDouble2) {
/* 224 */     return ((paramDouble1 < 0.0D && paramDouble2 < 0.0D) || (paramDouble1 > 0.0D && paramDouble2 > 0.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float ROCsq(float paramFloat) {
/* 231 */     float f1 = paramFloat * (paramFloat * this.dax + this.dbx) + this.cx;
/* 232 */     float f2 = paramFloat * (paramFloat * this.day + this.dby) + this.cy;
/* 233 */     float f3 = 2.0F * this.dax * paramFloat + this.dbx;
/* 234 */     float f4 = 2.0F * this.day * paramFloat + this.dby;
/* 235 */     float f5 = f1 * f1 + f2 * f2;
/* 236 */     float f6 = f3 * f3 + f4 * f4;
/* 237 */     float f7 = f3 * f1 + f4 * f2;
/* 238 */     return f5 * f5 * f5 / (f5 * f6 - f7 * f7);
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
/*     */   static Iterator<Integer> breakPtsAtTs(final float[] pts, final int type, final float[] Ts, final int numTs) {
/* 252 */     assert pts.length >= 2 * type && numTs <= Ts.length;
/* 253 */     return new Iterator<Integer>()
/*     */       {
/*     */ 
/*     */         
/* 257 */         final Integer i0 = Integer.valueOf(0);
/* 258 */         final Integer itype = Integer.valueOf(type);
/* 259 */         int nextCurveIdx = 0;
/* 260 */         Integer curCurveOff = this.i0;
/* 261 */         float prevT = 0.0F;
/*     */         
/*     */         public boolean hasNext() {
/* 264 */           return (this.nextCurveIdx < numTs + 1);
/*     */         }
/*     */         
/*     */         public Integer next() {
/*     */           Integer integer;
/* 269 */           if (this.nextCurveIdx < numTs) {
/* 270 */             float f1 = Ts[this.nextCurveIdx];
/* 271 */             float f2 = (f1 - this.prevT) / (1.0F - this.prevT);
/* 272 */             Helpers.subdivideAt(f2, pts, this.curCurveOff
/* 273 */                 .intValue(), pts, 0, pts, type, type);
/*     */ 
/*     */             
/* 276 */             this.prevT = f1;
/* 277 */             integer = this.i0;
/* 278 */             this.curCurveOff = this.itype;
/*     */           } else {
/* 280 */             integer = this.curCurveOff;
/*     */           } 
/* 282 */           this.nextCurveIdx++;
/* 283 */           return integer;
/*     */         }
/*     */         
/*     */         public void remove() {}
/*     */       };
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pisces/Curve.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */