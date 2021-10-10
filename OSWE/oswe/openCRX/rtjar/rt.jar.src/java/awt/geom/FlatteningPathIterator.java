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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlatteningPathIterator
/*     */   implements PathIterator
/*     */ {
/*     */   static final int GROW_SIZE = 24;
/*     */   PathIterator src;
/*     */   double squareflat;
/*     */   int limit;
/*  48 */   double[] hold = new double[14];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double curx;
/*     */ 
/*     */ 
/*     */   
/*     */   double cury;
/*     */ 
/*     */ 
/*     */   
/*     */   double movx;
/*     */ 
/*     */ 
/*     */   
/*     */   double movy;
/*     */ 
/*     */ 
/*     */   
/*     */   int holdType;
/*     */ 
/*     */ 
/*     */   
/*     */   int holdEnd;
/*     */ 
/*     */ 
/*     */   
/*     */   int holdIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   int[] levels;
/*     */ 
/*     */ 
/*     */   
/*     */   int levelIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean done;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FlatteningPathIterator(PathIterator paramPathIterator, double paramDouble) {
/*  95 */     this(paramPathIterator, paramDouble, 10);
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
/*     */   public FlatteningPathIterator(PathIterator paramPathIterator, double paramDouble, int paramInt) {
/* 118 */     if (paramDouble < 0.0D) {
/* 119 */       throw new IllegalArgumentException("flatness must be >= 0");
/*     */     }
/* 121 */     if (paramInt < 0) {
/* 122 */       throw new IllegalArgumentException("limit must be >= 0");
/*     */     }
/* 124 */     this.src = paramPathIterator;
/* 125 */     this.squareflat = paramDouble * paramDouble;
/* 126 */     this.limit = paramInt;
/* 127 */     this.levels = new int[paramInt + 1];
/*     */     
/* 129 */     next(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFlatness() {
/* 137 */     return Math.sqrt(this.squareflat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRecursionLimit() {
/* 146 */     return this.limit;
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
/*     */   public int getWindingRule() {
/* 158 */     return this.src.getWindingRule();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/* 167 */     return this.done;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void ensureHoldCapacity(int paramInt) {
/* 175 */     if (this.holdIndex - paramInt < 0) {
/* 176 */       int i = this.hold.length - this.holdIndex;
/* 177 */       int j = this.hold.length + 24;
/* 178 */       double[] arrayOfDouble = new double[j];
/* 179 */       System.arraycopy(this.hold, this.holdIndex, arrayOfDouble, this.holdIndex + 24, i);
/*     */ 
/*     */       
/* 182 */       this.hold = arrayOfDouble;
/* 183 */       this.holdIndex += 24;
/* 184 */       this.holdEnd += 24;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void next() {
/* 194 */     next(true);
/*     */   }
/*     */ 
/*     */   
/*     */   private void next(boolean paramBoolean) {
/*     */     int i;
/* 200 */     if (this.holdIndex >= this.holdEnd) {
/* 201 */       if (paramBoolean) {
/* 202 */         this.src.next();
/*     */       }
/* 204 */       if (this.src.isDone()) {
/* 205 */         this.done = true;
/*     */         return;
/*     */       } 
/* 208 */       this.holdType = this.src.currentSegment(this.hold);
/* 209 */       this.levelIndex = 0;
/* 210 */       this.levels[0] = 0;
/*     */     } 
/*     */     
/* 213 */     switch (this.holdType) {
/*     */       case 0:
/*     */       case 1:
/* 216 */         this.curx = this.hold[0];
/* 217 */         this.cury = this.hold[1];
/* 218 */         if (this.holdType == 0) {
/* 219 */           this.movx = this.curx;
/* 220 */           this.movy = this.cury;
/*     */         } 
/* 222 */         this.holdIndex = 0;
/* 223 */         this.holdEnd = 0;
/*     */         break;
/*     */       case 4:
/* 226 */         this.curx = this.movx;
/* 227 */         this.cury = this.movy;
/* 228 */         this.holdIndex = 0;
/* 229 */         this.holdEnd = 0;
/*     */         break;
/*     */       case 2:
/* 232 */         if (this.holdIndex >= this.holdEnd) {
/*     */           
/* 234 */           this.holdIndex = this.hold.length - 6;
/* 235 */           this.holdEnd = this.hold.length - 2;
/* 236 */           this.hold[this.holdIndex + 0] = this.curx;
/* 237 */           this.hold[this.holdIndex + 1] = this.cury;
/* 238 */           this.hold[this.holdIndex + 2] = this.hold[0];
/* 239 */           this.hold[this.holdIndex + 3] = this.hold[1];
/* 240 */           this.hold[this.holdIndex + 4] = this.curx = this.hold[2];
/* 241 */           this.hold[this.holdIndex + 5] = this.cury = this.hold[3];
/*     */         } 
/*     */         
/* 244 */         i = this.levels[this.levelIndex];
/* 245 */         while (i < this.limit && 
/* 246 */           QuadCurve2D.getFlatnessSq(this.hold, this.holdIndex) >= this.squareflat) {
/*     */ 
/*     */ 
/*     */           
/* 250 */           ensureHoldCapacity(4);
/* 251 */           QuadCurve2D.subdivide(this.hold, this.holdIndex, this.hold, this.holdIndex - 4, this.hold, this.holdIndex);
/*     */ 
/*     */           
/* 254 */           this.holdIndex -= 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 262 */           i++;
/* 263 */           this.levels[this.levelIndex] = i;
/* 264 */           this.levelIndex++;
/* 265 */           this.levels[this.levelIndex] = i;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 273 */         this.holdIndex += 4;
/* 274 */         this.levelIndex--;
/*     */         break;
/*     */       case 3:
/* 277 */         if (this.holdIndex >= this.holdEnd) {
/*     */           
/* 279 */           this.holdIndex = this.hold.length - 8;
/* 280 */           this.holdEnd = this.hold.length - 2;
/* 281 */           this.hold[this.holdIndex + 0] = this.curx;
/* 282 */           this.hold[this.holdIndex + 1] = this.cury;
/* 283 */           this.hold[this.holdIndex + 2] = this.hold[0];
/* 284 */           this.hold[this.holdIndex + 3] = this.hold[1];
/* 285 */           this.hold[this.holdIndex + 4] = this.hold[2];
/* 286 */           this.hold[this.holdIndex + 5] = this.hold[3];
/* 287 */           this.hold[this.holdIndex + 6] = this.curx = this.hold[4];
/* 288 */           this.hold[this.holdIndex + 7] = this.cury = this.hold[5];
/*     */         } 
/*     */         
/* 291 */         i = this.levels[this.levelIndex];
/* 292 */         while (i < this.limit && 
/* 293 */           CubicCurve2D.getFlatnessSq(this.hold, this.holdIndex) >= this.squareflat) {
/*     */ 
/*     */ 
/*     */           
/* 297 */           ensureHoldCapacity(6);
/* 298 */           CubicCurve2D.subdivide(this.hold, this.holdIndex, this.hold, this.holdIndex - 6, this.hold, this.holdIndex);
/*     */ 
/*     */           
/* 301 */           this.holdIndex -= 6;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 309 */           i++;
/* 310 */           this.levels[this.levelIndex] = i;
/* 311 */           this.levelIndex++;
/* 312 */           this.levels[this.levelIndex] = i;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 320 */         this.holdIndex += 6;
/* 321 */         this.levelIndex--;
/*     */         break;
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
/*     */   public int currentSegment(float[] paramArrayOffloat) {
/* 347 */     if (isDone()) {
/* 348 */       throw new NoSuchElementException("flattening iterator out of bounds");
/*     */     }
/* 350 */     int i = this.holdType;
/* 351 */     if (i != 4) {
/* 352 */       paramArrayOffloat[0] = (float)this.hold[this.holdIndex + 0];
/* 353 */       paramArrayOffloat[1] = (float)this.hold[this.holdIndex + 1];
/* 354 */       if (i != 0) {
/* 355 */         i = 1;
/*     */       }
/*     */     } 
/* 358 */     return i;
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
/*     */   public int currentSegment(double[] paramArrayOfdouble) {
/* 382 */     if (isDone()) {
/* 383 */       throw new NoSuchElementException("flattening iterator out of bounds");
/*     */     }
/* 385 */     int i = this.holdType;
/* 386 */     if (i != 4) {
/* 387 */       paramArrayOfdouble[0] = this.hold[this.holdIndex + 0];
/* 388 */       paramArrayOfdouble[1] = this.hold[this.holdIndex + 1];
/* 389 */       if (i != 0) {
/* 390 */         i = 1;
/*     */       }
/*     */     } 
/* 393 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/geom/FlatteningPathIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */