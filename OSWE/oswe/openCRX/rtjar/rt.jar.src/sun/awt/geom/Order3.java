/*     */ package sun.awt.geom;
/*     */ 
/*     */ import java.awt.geom.QuadCurve2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Order3
/*     */   extends Curve
/*     */ {
/*     */   private double x0;
/*     */   private double y0;
/*     */   private double cx0;
/*     */   private double cy0;
/*     */   private double cx1;
/*     */   private double cy1;
/*     */   private double x1;
/*     */   private double y1;
/*     */   private double xmin;
/*     */   private double xmax;
/*     */   private double xcoeff0;
/*     */   private double xcoeff1;
/*     */   private double xcoeff2;
/*     */   private double xcoeff3;
/*     */   private double ycoeff0;
/*     */   private double ycoeff1;
/*     */   private double ycoeff2;
/*     */   private double ycoeff3;
/*     */   private double TforY1;
/*     */   private double YforT1;
/*     */   private double TforY2;
/*     */   private double YforT2;
/*     */   private double TforY3;
/*     */   private double YforT3;
/*     */   
/*     */   public static void insert(Vector paramVector, double[] paramArrayOfdouble, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, int paramInt) {
/*  63 */     int i = getHorizontalParams(paramDouble2, paramDouble4, paramDouble6, paramDouble8, paramArrayOfdouble);
/*  64 */     if (i == 0) {
/*     */ 
/*     */       
/*  67 */       addInstance(paramVector, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramInt);
/*     */       
/*     */       return;
/*     */     } 
/*  71 */     paramArrayOfdouble[3] = paramDouble1; paramArrayOfdouble[4] = paramDouble2;
/*  72 */     paramArrayOfdouble[5] = paramDouble3; paramArrayOfdouble[6] = paramDouble4;
/*  73 */     paramArrayOfdouble[7] = paramDouble5; paramArrayOfdouble[8] = paramDouble6;
/*  74 */     paramArrayOfdouble[9] = paramDouble7; paramArrayOfdouble[10] = paramDouble8;
/*  75 */     double d = paramArrayOfdouble[0];
/*  76 */     if (i > 1 && d > paramArrayOfdouble[1]) {
/*     */       
/*  78 */       paramArrayOfdouble[0] = paramArrayOfdouble[1];
/*  79 */       paramArrayOfdouble[1] = d;
/*  80 */       d = paramArrayOfdouble[0];
/*     */     } 
/*  82 */     split(paramArrayOfdouble, 3, d);
/*  83 */     if (i > 1) {
/*     */       
/*  85 */       d = (paramArrayOfdouble[1] - d) / (1.0D - d);
/*  86 */       split(paramArrayOfdouble, 9, d);
/*     */     } 
/*  88 */     int j = 3;
/*  89 */     if (paramInt == -1) {
/*  90 */       j += i * 6;
/*     */     }
/*  92 */     while (i >= 0) {
/*  93 */       addInstance(paramVector, paramArrayOfdouble[j + 0], paramArrayOfdouble[j + 1], paramArrayOfdouble[j + 2], paramArrayOfdouble[j + 3], paramArrayOfdouble[j + 4], paramArrayOfdouble[j + 5], paramArrayOfdouble[j + 6], paramArrayOfdouble[j + 7], paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  99 */       i--;
/* 100 */       if (paramInt == 1) {
/* 101 */         j += 6; continue;
/*     */       } 
/* 103 */       j -= 6;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addInstance(Vector<Order3> paramVector, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, int paramInt) {
/* 114 */     if (paramDouble2 > paramDouble8) {
/* 115 */       paramVector.add(new Order3(paramDouble7, paramDouble8, paramDouble5, paramDouble6, paramDouble3, paramDouble4, paramDouble1, paramDouble2, -paramInt));
/*     */     }
/* 117 */     else if (paramDouble8 > paramDouble2) {
/* 118 */       paramVector.add(new Order3(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramInt));
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
/*     */   public static int getHorizontalParams(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double[] paramArrayOfdouble) {
/* 164 */     if (paramDouble1 <= paramDouble2 && paramDouble2 <= paramDouble3 && paramDouble3 <= paramDouble4) {
/* 165 */       return 0;
/*     */     }
/* 167 */     paramDouble4 -= paramDouble3;
/* 168 */     paramDouble3 -= paramDouble2;
/* 169 */     paramDouble2 -= paramDouble1;
/* 170 */     paramArrayOfdouble[0] = paramDouble2;
/* 171 */     paramArrayOfdouble[1] = (paramDouble3 - paramDouble2) * 2.0D;
/* 172 */     paramArrayOfdouble[2] = paramDouble4 - paramDouble3 - paramDouble3 + paramDouble2;
/* 173 */     int i = QuadCurve2D.solveQuadratic(paramArrayOfdouble, paramArrayOfdouble);
/* 174 */     byte b1 = 0;
/* 175 */     for (byte b2 = 0; b2 < i; b2++) {
/* 176 */       double d = paramArrayOfdouble[b2];
/*     */       
/* 178 */       if (d > 0.0D && d < 1.0D) {
/* 179 */         if (b1 < b2) {
/* 180 */           paramArrayOfdouble[b1] = d;
/*     */         }
/* 182 */         b1++;
/*     */       } 
/*     */     } 
/* 185 */     return b1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void split(double[] paramArrayOfdouble, int paramInt, double paramDouble) {
/* 196 */     double d7 = paramArrayOfdouble[paramInt + 6];
/* 197 */     double d8 = paramArrayOfdouble[paramInt + 7];
/* 198 */     double d5 = paramArrayOfdouble[paramInt + 4];
/* 199 */     double d6 = paramArrayOfdouble[paramInt + 5];
/* 200 */     d7 = d5 + (d7 - d5) * paramDouble;
/* 201 */     d8 = d6 + (d8 - d6) * paramDouble;
/* 202 */     double d1 = paramArrayOfdouble[paramInt + 0];
/* 203 */     double d2 = paramArrayOfdouble[paramInt + 1];
/* 204 */     double d3 = paramArrayOfdouble[paramInt + 2];
/* 205 */     double d4 = paramArrayOfdouble[paramInt + 3];
/* 206 */     d1 += (d3 - d1) * paramDouble;
/* 207 */     d2 += (d4 - d2) * paramDouble;
/* 208 */     d3 += (d5 - d3) * paramDouble;
/* 209 */     d4 += (d6 - d4) * paramDouble;
/* 210 */     d5 = d3 + (d7 - d3) * paramDouble;
/* 211 */     d6 = d4 + (d8 - d4) * paramDouble;
/* 212 */     d3 = d1 + (d3 - d1) * paramDouble;
/* 213 */     d4 = d2 + (d4 - d2) * paramDouble;
/* 214 */     paramArrayOfdouble[paramInt + 2] = d1;
/* 215 */     paramArrayOfdouble[paramInt + 3] = d2;
/* 216 */     paramArrayOfdouble[paramInt + 4] = d3;
/* 217 */     paramArrayOfdouble[paramInt + 5] = d4;
/* 218 */     paramArrayOfdouble[paramInt + 6] = d3 + (d5 - d3) * paramDouble;
/* 219 */     paramArrayOfdouble[paramInt + 7] = d4 + (d6 - d4) * paramDouble;
/* 220 */     paramArrayOfdouble[paramInt + 8] = d5;
/* 221 */     paramArrayOfdouble[paramInt + 9] = d6;
/* 222 */     paramArrayOfdouble[paramInt + 10] = d7;
/* 223 */     paramArrayOfdouble[paramInt + 11] = d8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Order3(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, int paramInt) {
/* 232 */     super(paramInt);
/*     */ 
/*     */ 
/*     */     
/* 236 */     if (paramDouble4 < paramDouble2) paramDouble4 = paramDouble2; 
/* 237 */     if (paramDouble6 > paramDouble8) paramDouble6 = paramDouble8; 
/* 238 */     this.x0 = paramDouble1;
/* 239 */     this.y0 = paramDouble2;
/* 240 */     this.cx0 = paramDouble3;
/* 241 */     this.cy0 = paramDouble4;
/* 242 */     this.cx1 = paramDouble5;
/* 243 */     this.cy1 = paramDouble6;
/* 244 */     this.x1 = paramDouble7;
/* 245 */     this.y1 = paramDouble8;
/* 246 */     this.xmin = Math.min(Math.min(paramDouble1, paramDouble7), Math.min(paramDouble3, paramDouble5));
/* 247 */     this.xmax = Math.max(Math.max(paramDouble1, paramDouble7), Math.max(paramDouble3, paramDouble5));
/* 248 */     this.xcoeff0 = paramDouble1;
/* 249 */     this.xcoeff1 = (paramDouble3 - paramDouble1) * 3.0D;
/* 250 */     this.xcoeff2 = (paramDouble5 - paramDouble3 - paramDouble3 + paramDouble1) * 3.0D;
/* 251 */     this.xcoeff3 = paramDouble7 - (paramDouble5 - paramDouble3) * 3.0D - paramDouble1;
/* 252 */     this.ycoeff0 = paramDouble2;
/* 253 */     this.ycoeff1 = (paramDouble4 - paramDouble2) * 3.0D;
/* 254 */     this.ycoeff2 = (paramDouble6 - paramDouble4 - paramDouble4 + paramDouble2) * 3.0D;
/* 255 */     this.ycoeff3 = paramDouble8 - (paramDouble6 - paramDouble4) * 3.0D - paramDouble2;
/* 256 */     this.YforT1 = this.YforT2 = this.YforT3 = paramDouble2;
/*     */   }
/*     */   
/*     */   public int getOrder() {
/* 260 */     return 3;
/*     */   }
/*     */   
/*     */   public double getXTop() {
/* 264 */     return this.x0;
/*     */   }
/*     */   
/*     */   public double getYTop() {
/* 268 */     return this.y0;
/*     */   }
/*     */   
/*     */   public double getXBot() {
/* 272 */     return this.x1;
/*     */   }
/*     */   
/*     */   public double getYBot() {
/* 276 */     return this.y1;
/*     */   }
/*     */   
/*     */   public double getXMin() {
/* 280 */     return this.xmin;
/*     */   }
/*     */   
/*     */   public double getXMax() {
/* 284 */     return this.xmax;
/*     */   }
/*     */   
/*     */   public double getX0() {
/* 288 */     return (this.direction == 1) ? this.x0 : this.x1;
/*     */   }
/*     */   
/*     */   public double getY0() {
/* 292 */     return (this.direction == 1) ? this.y0 : this.y1;
/*     */   }
/*     */   
/*     */   public double getCX0() {
/* 296 */     return (this.direction == 1) ? this.cx0 : this.cx1;
/*     */   }
/*     */   
/*     */   public double getCY0() {
/* 300 */     return (this.direction == 1) ? this.cy0 : this.cy1;
/*     */   }
/*     */   
/*     */   public double getCX1() {
/* 304 */     return (this.direction == -1) ? this.cx0 : this.cx1;
/*     */   }
/*     */   
/*     */   public double getCY1() {
/* 308 */     return (this.direction == -1) ? this.cy0 : this.cy1;
/*     */   }
/*     */   
/*     */   public double getX1() {
/* 312 */     return (this.direction == -1) ? this.x0 : this.x1;
/*     */   }
/*     */   
/*     */   public double getY1() {
/* 316 */     return (this.direction == -1) ? this.y0 : this.y1;
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
/*     */   public double TforY(double paramDouble) {
/*     */     double d9;
/* 334 */     if (paramDouble <= this.y0) return 0.0D; 
/* 335 */     if (paramDouble >= this.y1) return 1.0D; 
/* 336 */     if (paramDouble == this.YforT1) return this.TforY1; 
/* 337 */     if (paramDouble == this.YforT2) return this.TforY2; 
/* 338 */     if (paramDouble == this.YforT3) return this.TforY3;
/*     */     
/* 340 */     if (this.ycoeff3 == 0.0D)
/*     */     {
/* 342 */       return Order2.TforY(paramDouble, this.ycoeff0, this.ycoeff1, this.ycoeff2);
/*     */     }
/* 344 */     double d1 = this.ycoeff2 / this.ycoeff3;
/* 345 */     double d2 = this.ycoeff1 / this.ycoeff3;
/* 346 */     double d3 = (this.ycoeff0 - paramDouble) / this.ycoeff3;
/* 347 */     boolean bool = false;
/* 348 */     double d4 = (d1 * d1 - 3.0D * d2) / 9.0D;
/* 349 */     double d5 = (2.0D * d1 * d1 * d1 - 9.0D * d1 * d2 + 27.0D * d3) / 54.0D;
/* 350 */     double d6 = d5 * d5;
/* 351 */     double d7 = d4 * d4 * d4;
/* 352 */     double d8 = d1 / 3.0D;
/*     */     
/* 354 */     if (d6 < d7) {
/* 355 */       double d = Math.acos(d5 / Math.sqrt(d7));
/* 356 */       d4 = -2.0D * Math.sqrt(d4);
/* 357 */       d9 = refine(d1, d2, d3, paramDouble, d4 * Math.cos(d / 3.0D) - d8);
/* 358 */       if (d9 < 0.0D) {
/* 359 */         d9 = refine(d1, d2, d3, paramDouble, d4 * 
/* 360 */             Math.cos((d + 6.283185307179586D) / 3.0D) - d8);
/*     */       }
/* 362 */       if (d9 < 0.0D) {
/* 363 */         d9 = refine(d1, d2, d3, paramDouble, d4 * 
/* 364 */             Math.cos((d - 6.283185307179586D) / 3.0D) - d8);
/*     */       }
/*     */     } else {
/* 367 */       boolean bool1 = (d5 < 0.0D) ? true : false;
/* 368 */       double d10 = Math.sqrt(d6 - d7);
/* 369 */       if (bool1) {
/* 370 */         d5 = -d5;
/*     */       }
/* 372 */       double d11 = Math.pow(d5 + d10, 0.3333333333333333D);
/* 373 */       if (!bool1) {
/* 374 */         d11 = -d11;
/*     */       }
/* 376 */       double d12 = (d11 == 0.0D) ? 0.0D : (d4 / d11);
/* 377 */       d9 = refine(d1, d2, d3, paramDouble, d11 + d12 - d8);
/*     */     } 
/* 379 */     if (d9 < 0.0D) {
/*     */       
/* 381 */       double d10 = 0.0D;
/* 382 */       double d11 = 1.0D;
/*     */       while (true) {
/* 384 */         d9 = (d10 + d11) / 2.0D;
/* 385 */         if (d9 == d10 || d9 == d11) {
/*     */           break;
/*     */         }
/* 388 */         double d = YforT(d9);
/* 389 */         if (d < paramDouble) {
/* 390 */           d10 = d9; continue;
/* 391 */         }  if (d > paramDouble) {
/* 392 */           d11 = d9;
/*     */           continue;
/*     */         } 
/*     */         break;
/*     */       } 
/*     */     } 
/* 398 */     if (d9 >= 0.0D) {
/* 399 */       this.TforY3 = this.TforY2;
/* 400 */       this.YforT3 = this.YforT2;
/* 401 */       this.TforY2 = this.TforY1;
/* 402 */       this.YforT2 = this.YforT1;
/* 403 */       this.TforY1 = d9;
/* 404 */       this.YforT1 = paramDouble;
/*     */     } 
/* 406 */     return d9;
/*     */   }
/*     */ 
/*     */   
/*     */   public double refine(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5) {
/*     */     double d2, d3;
/* 412 */     if (paramDouble5 < -0.1D || paramDouble5 > 1.1D) {
/* 413 */       return -1.0D;
/*     */     }
/* 415 */     double d1 = YforT(paramDouble5);
/*     */     
/* 417 */     if (d1 < paramDouble4) {
/* 418 */       d2 = paramDouble5;
/* 419 */       d3 = 1.0D;
/*     */     } else {
/* 421 */       d2 = 0.0D;
/* 422 */       d3 = paramDouble5;
/*     */     } 
/* 424 */     double d4 = paramDouble5;
/* 425 */     double d5 = d1;
/* 426 */     boolean bool1 = true;
/* 427 */     while (d1 != paramDouble4) {
/* 428 */       if (!bool1) {
/* 429 */         double d = (d2 + d3) / 2.0D;
/* 430 */         if (d == d2 || d == d3) {
/*     */           break;
/*     */         }
/* 433 */         paramDouble5 = d;
/*     */       } else {
/* 435 */         double d6 = dYforT(paramDouble5, 1);
/* 436 */         if (d6 == 0.0D) {
/* 437 */           bool1 = false;
/*     */           continue;
/*     */         } 
/* 440 */         double d7 = paramDouble5 + (paramDouble4 - d1) / d6;
/* 441 */         if (d7 == paramDouble5 || d7 <= d2 || d7 >= d3) {
/* 442 */           bool1 = false;
/*     */           continue;
/*     */         } 
/* 445 */         paramDouble5 = d7;
/*     */       } 
/* 447 */       d1 = YforT(paramDouble5);
/* 448 */       if (d1 < paramDouble4) {
/* 449 */         d2 = paramDouble5; continue;
/* 450 */       }  if (d1 > paramDouble4) {
/* 451 */         d3 = paramDouble5;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 456 */     boolean bool2 = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 480 */     return (paramDouble5 > 1.0D) ? -1.0D : paramDouble5;
/*     */   }
/*     */   
/*     */   public double XforY(double paramDouble) {
/* 484 */     if (paramDouble <= this.y0) {
/* 485 */       return this.x0;
/*     */     }
/* 487 */     if (paramDouble >= this.y1) {
/* 488 */       return this.x1;
/*     */     }
/* 490 */     return XforT(TforY(paramDouble));
/*     */   }
/*     */   
/*     */   public double XforT(double paramDouble) {
/* 494 */     return ((this.xcoeff3 * paramDouble + this.xcoeff2) * paramDouble + this.xcoeff1) * paramDouble + this.xcoeff0;
/*     */   }
/*     */   
/*     */   public double YforT(double paramDouble) {
/* 498 */     return ((this.ycoeff3 * paramDouble + this.ycoeff2) * paramDouble + this.ycoeff1) * paramDouble + this.ycoeff0;
/*     */   }
/*     */   
/*     */   public double dXforT(double paramDouble, int paramInt) {
/* 502 */     switch (paramInt) {
/*     */       case 0:
/* 504 */         return ((this.xcoeff3 * paramDouble + this.xcoeff2) * paramDouble + this.xcoeff1) * paramDouble + this.xcoeff0;
/*     */       case 1:
/* 506 */         return (3.0D * this.xcoeff3 * paramDouble + 2.0D * this.xcoeff2) * paramDouble + this.xcoeff1;
/*     */       case 2:
/* 508 */         return 6.0D * this.xcoeff3 * paramDouble + 2.0D * this.xcoeff2;
/*     */       case 3:
/* 510 */         return 6.0D * this.xcoeff3;
/*     */     } 
/* 512 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double dYforT(double paramDouble, int paramInt) {
/* 517 */     switch (paramInt) {
/*     */       case 0:
/* 519 */         return ((this.ycoeff3 * paramDouble + this.ycoeff2) * paramDouble + this.ycoeff1) * paramDouble + this.ycoeff0;
/*     */       case 1:
/* 521 */         return (3.0D * this.ycoeff3 * paramDouble + 2.0D * this.ycoeff2) * paramDouble + this.ycoeff1;
/*     */       case 2:
/* 523 */         return 6.0D * this.ycoeff3 * paramDouble + 2.0D * this.ycoeff2;
/*     */       case 3:
/* 525 */         return 6.0D * this.ycoeff3;
/*     */     } 
/* 527 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double nextVertical(double paramDouble1, double paramDouble2) {
/* 532 */     double[] arrayOfDouble = { this.xcoeff1, 2.0D * this.xcoeff2, 3.0D * this.xcoeff3 };
/* 533 */     int i = QuadCurve2D.solveQuadratic(arrayOfDouble, arrayOfDouble);
/* 534 */     for (byte b = 0; b < i; b++) {
/* 535 */       if (arrayOfDouble[b] > paramDouble1 && arrayOfDouble[b] < paramDouble2) {
/* 536 */         paramDouble2 = arrayOfDouble[b];
/*     */       }
/*     */     } 
/* 539 */     return paramDouble2;
/*     */   }
/*     */   
/*     */   public void enlarge(Rectangle2D paramRectangle2D) {
/* 543 */     paramRectangle2D.add(this.x0, this.y0);
/* 544 */     double[] arrayOfDouble = { this.xcoeff1, 2.0D * this.xcoeff2, 3.0D * this.xcoeff3 };
/* 545 */     int i = QuadCurve2D.solveQuadratic(arrayOfDouble, arrayOfDouble);
/* 546 */     for (byte b = 0; b < i; b++) {
/* 547 */       double d = arrayOfDouble[b];
/* 548 */       if (d > 0.0D && d < 1.0D) {
/* 549 */         paramRectangle2D.add(XforT(d), YforT(d));
/*     */       }
/*     */     } 
/* 552 */     paramRectangle2D.add(this.x1, this.y1);
/*     */   }
/*     */   public Curve getSubCurve(double paramDouble1, double paramDouble2, int paramInt) {
/*     */     byte b;
/* 556 */     if (paramDouble1 <= this.y0 && paramDouble2 >= this.y1) {
/* 557 */       return getWithDirection(paramInt);
/*     */     }
/* 559 */     double[] arrayOfDouble = new double[14];
/*     */     
/* 561 */     double d1 = TforY(paramDouble1);
/* 562 */     double d2 = TforY(paramDouble2);
/* 563 */     arrayOfDouble[0] = this.x0;
/* 564 */     arrayOfDouble[1] = this.y0;
/* 565 */     arrayOfDouble[2] = this.cx0;
/* 566 */     arrayOfDouble[3] = this.cy0;
/* 567 */     arrayOfDouble[4] = this.cx1;
/* 568 */     arrayOfDouble[5] = this.cy1;
/* 569 */     arrayOfDouble[6] = this.x1;
/* 570 */     arrayOfDouble[7] = this.y1;
/* 571 */     if (d1 > d2) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 586 */       double d = d1;
/* 587 */       d1 = d2;
/* 588 */       d2 = d;
/*     */     } 
/* 590 */     if (d2 < 1.0D) {
/* 591 */       split(arrayOfDouble, 0, d2);
/*     */     }
/*     */     
/* 594 */     if (d1 <= 0.0D) {
/* 595 */       b = 0;
/*     */     } else {
/* 597 */       split(arrayOfDouble, 0, d1 / d2);
/* 598 */       b = 6;
/*     */     } 
/* 600 */     return new Order3(arrayOfDouble[b + 0], paramDouble1, arrayOfDouble[b + 2], arrayOfDouble[b + 3], arrayOfDouble[b + 4], arrayOfDouble[b + 5], arrayOfDouble[b + 6], paramDouble2, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Curve getReversedCurve() {
/* 608 */     return new Order3(this.x0, this.y0, this.cx0, this.cy0, this.cx1, this.cy1, this.x1, this.y1, -this.direction);
/*     */   }
/*     */   
/*     */   public int getSegment(double[] paramArrayOfdouble) {
/* 612 */     if (this.direction == 1) {
/* 613 */       paramArrayOfdouble[0] = this.cx0;
/* 614 */       paramArrayOfdouble[1] = this.cy0;
/* 615 */       paramArrayOfdouble[2] = this.cx1;
/* 616 */       paramArrayOfdouble[3] = this.cy1;
/* 617 */       paramArrayOfdouble[4] = this.x1;
/* 618 */       paramArrayOfdouble[5] = this.y1;
/*     */     } else {
/* 620 */       paramArrayOfdouble[0] = this.cx1;
/* 621 */       paramArrayOfdouble[1] = this.cy1;
/* 622 */       paramArrayOfdouble[2] = this.cx0;
/* 623 */       paramArrayOfdouble[3] = this.cy0;
/* 624 */       paramArrayOfdouble[4] = this.x0;
/* 625 */       paramArrayOfdouble[5] = this.y0;
/*     */     } 
/* 627 */     return 3;
/*     */   }
/*     */   
/*     */   public String controlPointString() {
/* 631 */     return "(" + round(getCX0()) + ", " + round(getCY0()) + "), " + "(" + 
/* 632 */       round(getCX1()) + ", " + round(getCY1()) + "), ";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/geom/Order3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */