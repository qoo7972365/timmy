/*     */ package sun.awt.geom;
/*     */ 
/*     */ import java.awt.geom.PathIterator;
/*     */ import java.util.Enumeration;
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
/*     */ public abstract class Crossings
/*     */ {
/*     */   public static final boolean debug = false;
/*  35 */   int limit = 0;
/*  36 */   double[] yranges = new double[10];
/*     */   
/*     */   double xlo;
/*     */   
/*     */   double ylo;
/*     */   
/*     */   double xhi;
/*     */   
/*     */   double yhi;
/*     */   private Vector tmp;
/*     */   
/*     */   public final double getXLo() {
/*  48 */     return this.xlo;
/*     */   }
/*     */   
/*     */   public final double getYLo() {
/*  52 */     return this.ylo;
/*     */   }
/*     */   
/*     */   public final double getXHi() {
/*  56 */     return this.xhi;
/*     */   }
/*     */   
/*     */   public final double getYHi() {
/*  60 */     return this.yhi;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void print() {
/*  66 */     System.out.println("Crossings [");
/*  67 */     System.out.println("  bounds = [" + this.ylo + ", " + this.yhi + "]");
/*  68 */     for (byte b = 0; b < this.limit; b += 2) {
/*  69 */       System.out.println("  [" + this.yranges[b] + ", " + this.yranges[b + 1] + "]");
/*     */     }
/*  71 */     System.out.println("]");
/*     */   }
/*     */   
/*     */   public final boolean isEmpty() {
/*  75 */     return (this.limit == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Crossings findCrossings(Vector paramVector, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  84 */     EvenOdd evenOdd = new EvenOdd(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*  85 */     Enumeration<Curve> enumeration = paramVector.elements();
/*  86 */     while (enumeration.hasMoreElements()) {
/*  87 */       Curve curve = enumeration.nextElement();
/*  88 */       if (curve.accumulateCrossings(evenOdd)) {
/*  89 */         return null;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  95 */     return evenOdd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Crossings findCrossings(PathIterator paramPathIterator, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*     */     NonZero nonZero;
/* 103 */     if (paramPathIterator.getWindingRule() == 0) {
/* 104 */       EvenOdd evenOdd = new EvenOdd(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */     } else {
/* 106 */       nonZero = new NonZero(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
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
/* 122 */     double[] arrayOfDouble = new double[23];
/* 123 */     double d1 = 0.0D;
/* 124 */     double d2 = 0.0D;
/* 125 */     double d3 = 0.0D;
/* 126 */     double d4 = 0.0D;
/*     */     
/* 128 */     while (!paramPathIterator.isDone()) {
/* 129 */       double d5, d6; int i = paramPathIterator.currentSegment(arrayOfDouble);
/* 130 */       switch (i) {
/*     */         case 0:
/* 132 */           if (d2 != d4 && nonZero
/* 133 */             .accumulateLine(d3, d4, d1, d2))
/*     */           {
/* 135 */             return null;
/*     */           }
/* 137 */           d1 = d3 = arrayOfDouble[0];
/* 138 */           d2 = d4 = arrayOfDouble[1];
/*     */         
/*     */         case 1:
/* 141 */           d5 = arrayOfDouble[0];
/* 142 */           d6 = arrayOfDouble[1];
/* 143 */           if (nonZero.accumulateLine(d3, d4, d5, d6)) {
/* 144 */             return null;
/*     */           }
/* 146 */           d3 = d5;
/* 147 */           d4 = d6;
/*     */           break;
/*     */         case 2:
/* 150 */           d5 = arrayOfDouble[2];
/* 151 */           d6 = arrayOfDouble[3];
/* 152 */           if (nonZero.accumulateQuad(d3, d4, arrayOfDouble)) {
/* 153 */             return null;
/*     */           }
/* 155 */           d3 = d5;
/* 156 */           d4 = d6;
/*     */           break;
/*     */         case 3:
/* 159 */           d5 = arrayOfDouble[4];
/* 160 */           d6 = arrayOfDouble[5];
/* 161 */           if (nonZero.accumulateCubic(d3, d4, arrayOfDouble)) {
/* 162 */             return null;
/*     */           }
/* 164 */           d3 = d5;
/* 165 */           d4 = d6;
/*     */           break;
/*     */         case 4:
/* 168 */           if (d2 != d4 && nonZero
/* 169 */             .accumulateLine(d3, d4, d1, d2))
/*     */           {
/* 171 */             return null;
/*     */           }
/* 173 */           d3 = d1;
/* 174 */           d4 = d2;
/*     */           break;
/*     */       } 
/* 177 */       paramPathIterator.next();
/*     */     } 
/* 179 */     if (d2 != d4 && 
/* 180 */       nonZero.accumulateLine(d3, d4, d1, d2)) {
/* 181 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     return nonZero;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean accumulateLine(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 193 */     if (paramDouble2 <= paramDouble4) {
/* 194 */       return accumulateLine(paramDouble1, paramDouble2, paramDouble3, paramDouble4, 1);
/*     */     }
/* 196 */     return accumulateLine(paramDouble3, paramDouble4, paramDouble1, paramDouble2, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean accumulateLine(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt) {
/*     */     double d1, d2, d3, d4;
/* 204 */     if (this.yhi <= paramDouble2 || this.ylo >= paramDouble4) {
/* 205 */       return false;
/*     */     }
/* 207 */     if (paramDouble1 >= this.xhi && paramDouble3 >= this.xhi) {
/* 208 */       return false;
/*     */     }
/* 210 */     if (paramDouble2 == paramDouble4) {
/* 211 */       return (paramDouble1 >= this.xlo || paramDouble3 >= this.xlo);
/*     */     }
/*     */     
/* 214 */     double d5 = paramDouble3 - paramDouble1;
/* 215 */     double d6 = paramDouble4 - paramDouble2;
/* 216 */     if (paramDouble2 < this.ylo) {
/* 217 */       d1 = paramDouble1 + (this.ylo - paramDouble2) * d5 / d6;
/* 218 */       d2 = this.ylo;
/*     */     } else {
/* 220 */       d1 = paramDouble1;
/* 221 */       d2 = paramDouble2;
/*     */     } 
/* 223 */     if (this.yhi < paramDouble4) {
/* 224 */       d3 = paramDouble1 + (this.yhi - paramDouble2) * d5 / d6;
/* 225 */       d4 = this.yhi;
/*     */     } else {
/* 227 */       d3 = paramDouble3;
/* 228 */       d4 = paramDouble4;
/*     */     } 
/* 230 */     if (d1 >= this.xhi && d3 >= this.xhi) {
/* 231 */       return false;
/*     */     }
/* 233 */     if (d1 > this.xlo || d3 > this.xlo) {
/* 234 */       return true;
/*     */     }
/* 236 */     record(d2, d4, paramInt);
/* 237 */     return false;
/*     */   }
/*     */   
/* 240 */   public Crossings(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) { this.tmp = new Vector(); this.xlo = paramDouble1;
/*     */     this.ylo = paramDouble2;
/*     */     this.xhi = paramDouble3;
/* 243 */     this.yhi = paramDouble4; } public boolean accumulateQuad(double paramDouble1, double paramDouble2, double[] paramArrayOfdouble) { if (paramDouble2 < this.ylo && paramArrayOfdouble[1] < this.ylo && paramArrayOfdouble[3] < this.ylo) {
/* 244 */       return false;
/*     */     }
/* 246 */     if (paramDouble2 > this.yhi && paramArrayOfdouble[1] > this.yhi && paramArrayOfdouble[3] > this.yhi) {
/* 247 */       return false;
/*     */     }
/* 249 */     if (paramDouble1 > this.xhi && paramArrayOfdouble[0] > this.xhi && paramArrayOfdouble[2] > this.xhi) {
/* 250 */       return false;
/*     */     }
/* 252 */     if (paramDouble1 < this.xlo && paramArrayOfdouble[0] < this.xlo && paramArrayOfdouble[2] < this.xlo) {
/* 253 */       if (paramDouble2 < paramArrayOfdouble[3]) {
/* 254 */         record(Math.max(paramDouble2, this.ylo), Math.min(paramArrayOfdouble[3], this.yhi), 1);
/* 255 */       } else if (paramDouble2 > paramArrayOfdouble[3]) {
/* 256 */         record(Math.max(paramArrayOfdouble[3], this.ylo), Math.min(paramDouble2, this.yhi), -1);
/*     */       } 
/* 258 */       return false;
/*     */     } 
/* 260 */     Curve.insertQuad(this.tmp, paramDouble1, paramDouble2, paramArrayOfdouble);
/* 261 */     Enumeration<Curve> enumeration = this.tmp.elements();
/* 262 */     while (enumeration.hasMoreElements()) {
/* 263 */       Curve curve = enumeration.nextElement();
/* 264 */       if (curve.accumulateCrossings(this)) {
/* 265 */         return true;
/*     */       }
/*     */     } 
/* 268 */     this.tmp.clear();
/* 269 */     return false; }
/*     */ 
/*     */   
/*     */   public boolean accumulateCubic(double paramDouble1, double paramDouble2, double[] paramArrayOfdouble) {
/* 273 */     if (paramDouble2 < this.ylo && paramArrayOfdouble[1] < this.ylo && paramArrayOfdouble[3] < this.ylo && paramArrayOfdouble[5] < this.ylo)
/*     */     {
/*     */       
/* 276 */       return false;
/*     */     }
/* 278 */     if (paramDouble2 > this.yhi && paramArrayOfdouble[1] > this.yhi && paramArrayOfdouble[3] > this.yhi && paramArrayOfdouble[5] > this.yhi)
/*     */     {
/*     */       
/* 281 */       return false;
/*     */     }
/* 283 */     if (paramDouble1 > this.xhi && paramArrayOfdouble[0] > this.xhi && paramArrayOfdouble[2] > this.xhi && paramArrayOfdouble[4] > this.xhi)
/*     */     {
/*     */       
/* 286 */       return false;
/*     */     }
/* 288 */     if (paramDouble1 < this.xlo && paramArrayOfdouble[0] < this.xlo && paramArrayOfdouble[2] < this.xlo && paramArrayOfdouble[4] < this.xlo) {
/*     */ 
/*     */       
/* 291 */       if (paramDouble2 <= paramArrayOfdouble[5]) {
/* 292 */         record(Math.max(paramDouble2, this.ylo), Math.min(paramArrayOfdouble[5], this.yhi), 1);
/*     */       } else {
/* 294 */         record(Math.max(paramArrayOfdouble[5], this.ylo), Math.min(paramDouble2, this.yhi), -1);
/*     */       } 
/* 296 */       return false;
/*     */     } 
/* 298 */     Curve.insertCubic(this.tmp, paramDouble1, paramDouble2, paramArrayOfdouble);
/* 299 */     Enumeration<Curve> enumeration = this.tmp.elements();
/* 300 */     while (enumeration.hasMoreElements()) {
/* 301 */       Curve curve = enumeration.nextElement();
/* 302 */       if (curve.accumulateCrossings(this)) {
/* 303 */         return true;
/*     */       }
/*     */     } 
/* 306 */     this.tmp.clear();
/* 307 */     return false;
/*     */   }
/*     */   public abstract void record(double paramDouble1, double paramDouble2, int paramInt);
/*     */   public abstract boolean covers(double paramDouble1, double paramDouble2);
/*     */   public static final class EvenOdd extends Crossings { public EvenOdd(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/* 312 */       super(param1Double1, param1Double2, param1Double3, param1Double4);
/*     */     }
/*     */     
/*     */     public final boolean covers(double param1Double1, double param1Double2) {
/* 316 */       return (this.limit == 2 && this.yranges[0] <= param1Double1 && this.yranges[1] >= param1Double2);
/*     */     }
/*     */     
/*     */     public void record(double param1Double1, double param1Double2, int param1Int) {
/* 320 */       if (param1Double1 >= param1Double2) {
/*     */         return;
/*     */       }
/* 323 */       byte b = 0;
/*     */       
/* 325 */       while (b < this.limit && param1Double1 > this.yranges[b + 1]) {
/* 326 */         b += 2;
/*     */       }
/* 328 */       int i = b;
/* 329 */       while (b < this.limit) {
/* 330 */         double d3, d4, d5, d6, d1 = this.yranges[b++];
/* 331 */         double d2 = this.yranges[b++];
/* 332 */         if (param1Double2 < d1) {
/*     */           
/* 334 */           this.yranges[i++] = param1Double1;
/* 335 */           this.yranges[i++] = param1Double2;
/* 336 */           param1Double1 = d1;
/* 337 */           param1Double2 = d2;
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 342 */         if (param1Double1 < d1) {
/* 343 */           d3 = param1Double1;
/* 344 */           d4 = d1;
/*     */         } else {
/* 346 */           d3 = d1;
/* 347 */           d4 = param1Double1;
/*     */         } 
/* 349 */         if (param1Double2 < d2) {
/* 350 */           d5 = param1Double2;
/* 351 */           d6 = d2;
/*     */         } else {
/* 353 */           d5 = d2;
/* 354 */           d6 = param1Double2;
/*     */         } 
/* 356 */         if (d4 == d5) {
/* 357 */           param1Double1 = d3;
/* 358 */           param1Double2 = d6;
/*     */         } else {
/* 360 */           if (d4 > d5) {
/* 361 */             param1Double1 = d5;
/* 362 */             d5 = d4;
/* 363 */             d4 = param1Double1;
/*     */           } 
/* 365 */           if (d3 != d4) {
/* 366 */             this.yranges[i++] = d3;
/* 367 */             this.yranges[i++] = d4;
/*     */           } 
/* 369 */           param1Double1 = d5;
/* 370 */           param1Double2 = d6;
/*     */         } 
/* 372 */         if (param1Double1 >= param1Double2) {
/*     */           break;
/*     */         }
/*     */       } 
/* 376 */       if (i < b && b < this.limit) {
/* 377 */         System.arraycopy(this.yranges, b, this.yranges, i, this.limit - b);
/*     */       }
/* 379 */       i += this.limit - b;
/* 380 */       if (param1Double1 < param1Double2) {
/* 381 */         if (i >= this.yranges.length) {
/* 382 */           double[] arrayOfDouble = new double[i + 10];
/* 383 */           System.arraycopy(this.yranges, 0, arrayOfDouble, 0, i);
/* 384 */           this.yranges = arrayOfDouble;
/*     */         } 
/* 386 */         this.yranges[i++] = param1Double1;
/* 387 */         this.yranges[i++] = param1Double2;
/*     */       } 
/* 389 */       this.limit = i;
/*     */     } }
/*     */ 
/*     */   
/*     */   public static final class NonZero extends Crossings {
/*     */     private int[] crosscounts;
/*     */     
/*     */     public NonZero(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/* 397 */       super(param1Double1, param1Double2, param1Double3, param1Double4);
/* 398 */       this.crosscounts = new int[this.yranges.length / 2];
/*     */     }
/*     */     
/*     */     public final boolean covers(double param1Double1, double param1Double2) {
/* 402 */       byte b = 0;
/* 403 */       while (b < this.limit) {
/* 404 */         double d1 = this.yranges[b++];
/* 405 */         double d2 = this.yranges[b++];
/* 406 */         if (param1Double1 >= d2) {
/*     */           continue;
/*     */         }
/* 409 */         if (param1Double1 < d1) {
/* 410 */           return false;
/*     */         }
/* 412 */         if (param1Double2 <= d2) {
/* 413 */           return true;
/*     */         }
/* 415 */         param1Double1 = d2;
/*     */       } 
/* 417 */       return (param1Double1 >= param1Double2);
/*     */     }
/*     */     
/*     */     public void remove(int param1Int) {
/* 421 */       this.limit -= 2;
/* 422 */       int i = this.limit - param1Int;
/* 423 */       if (i > 0) {
/* 424 */         System.arraycopy(this.yranges, param1Int + 2, this.yranges, param1Int, i);
/* 425 */         System.arraycopy(this.crosscounts, param1Int / 2 + 1, this.crosscounts, param1Int / 2, i / 2);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void insert(int param1Int1, double param1Double1, double param1Double2, int param1Int2) {
/* 432 */       int i = this.limit - param1Int1;
/* 433 */       double[] arrayOfDouble = this.yranges;
/* 434 */       int[] arrayOfInt = this.crosscounts;
/* 435 */       if (this.limit >= this.yranges.length) {
/* 436 */         this.yranges = new double[this.limit + 10];
/* 437 */         System.arraycopy(arrayOfDouble, 0, this.yranges, 0, param1Int1);
/* 438 */         this.crosscounts = new int[(this.limit + 10) / 2];
/* 439 */         System.arraycopy(arrayOfInt, 0, this.crosscounts, 0, param1Int1 / 2);
/*     */       } 
/* 441 */       if (i > 0) {
/* 442 */         System.arraycopy(arrayOfDouble, param1Int1, this.yranges, param1Int1 + 2, i);
/* 443 */         System.arraycopy(arrayOfInt, param1Int1 / 2, this.crosscounts, param1Int1 / 2 + 1, i / 2);
/*     */       } 
/*     */ 
/*     */       
/* 447 */       this.yranges[param1Int1 + 0] = param1Double1;
/* 448 */       this.yranges[param1Int1 + 1] = param1Double2;
/* 449 */       this.crosscounts[param1Int1 / 2] = param1Int2;
/* 450 */       this.limit += 2;
/*     */     }
/*     */     
/*     */     public void record(double param1Double1, double param1Double2, int param1Int) {
/* 454 */       if (param1Double1 >= param1Double2) {
/*     */         return;
/*     */       }
/* 457 */       byte b = 0;
/*     */       
/* 459 */       while (b < this.limit && param1Double1 > this.yranges[b + 1]) {
/* 460 */         b += 2;
/*     */       }
/* 462 */       if (b < this.limit) {
/* 463 */         int i = this.crosscounts[b / 2];
/* 464 */         double d1 = this.yranges[b + 0];
/* 465 */         double d2 = this.yranges[b + 1];
/* 466 */         if (d2 == param1Double1 && i == param1Int) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 472 */           if (b + 2 == this.limit) {
/* 473 */             this.yranges[b + 1] = param1Double2;
/*     */             return;
/*     */           } 
/* 476 */           remove(b);
/* 477 */           param1Double1 = d1;
/* 478 */           i = this.crosscounts[b / 2];
/* 479 */           d1 = this.yranges[b + 0];
/* 480 */           d2 = this.yranges[b + 1];
/*     */         } 
/* 482 */         if (param1Double2 < d1) {
/*     */           
/* 484 */           insert(b, param1Double1, param1Double2, param1Int);
/*     */           return;
/*     */         } 
/* 487 */         if (param1Double2 == d1 && i == param1Int) {
/*     */           
/* 489 */           this.yranges[b] = param1Double1;
/*     */           
/*     */           return;
/*     */         } 
/* 493 */         if (param1Double1 < d1) {
/* 494 */           insert(b, param1Double1, d1, param1Int);
/* 495 */           b += 2;
/* 496 */           param1Double1 = d1;
/* 497 */         } else if (d1 < param1Double1) {
/* 498 */           insert(b, d1, param1Double1, i);
/* 499 */           b += 2;
/* 500 */           d1 = param1Double1;
/*     */         } 
/*     */         
/* 503 */         int j = i + param1Int;
/* 504 */         double d3 = Math.min(param1Double2, d2);
/* 505 */         if (j == 0) {
/* 506 */           remove(b);
/*     */         } else {
/* 508 */           this.crosscounts[b / 2] = j;
/* 509 */           this.yranges[b++] = param1Double1;
/* 510 */           this.yranges[b++] = d3;
/*     */         } 
/* 512 */         param1Double1 = d1 = d3;
/* 513 */         if (d1 < d2) {
/* 514 */           insert(b, d1, d2, i);
/*     */         }
/*     */       } 
/* 517 */       if (param1Double1 < param1Double2)
/* 518 */         insert(b, param1Double1, param1Double2, param1Int); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/geom/Crossings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */