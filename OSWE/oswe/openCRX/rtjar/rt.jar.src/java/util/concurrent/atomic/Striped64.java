/*     */ package java.util.concurrent.atomic;
/*     */ 
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.function.DoubleBinaryOperator;
/*     */ import java.util.function.LongBinaryOperator;
/*     */ import sun.misc.Contended;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class Striped64
/*     */   extends Number
/*     */ {
/*     */   @Contended
/*     */   static final class Cell
/*     */   {
/*     */     volatile long value;
/*     */     private static final Unsafe UNSAFE;
/*     */     private static final long valueOffset;
/*     */     
/*     */     Cell(long param1Long) {
/* 122 */       this.value = param1Long;
/*     */     } final boolean cas(long param1Long1, long param1Long2) {
/* 124 */       return UNSAFE.compareAndSwapLong(this, valueOffset, param1Long1, param1Long2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       try {
/* 132 */         UNSAFE = Unsafe.getUnsafe();
/* 133 */         Class<Cell> clazz = Cell.class;
/*     */         
/* 135 */         valueOffset = UNSAFE.objectFieldOffset(clazz.getDeclaredField("value"));
/* 136 */       } catch (Exception exception) {
/* 137 */         throw new Error(exception);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 143 */   static final int NCPU = Runtime.getRuntime().availableProcessors();
/*     */ 
/*     */ 
/*     */   
/*     */   volatile transient Cell[] cells;
/*     */ 
/*     */ 
/*     */   
/*     */   volatile transient long base;
/*     */ 
/*     */   
/*     */   volatile transient int cellsBusy;
/*     */ 
/*     */   
/*     */   private static final Unsafe UNSAFE;
/*     */ 
/*     */   
/*     */   private static final long BASE;
/*     */ 
/*     */   
/*     */   private static final long CELLSBUSY;
/*     */ 
/*     */   
/*     */   private static final long PROBE;
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean casBase(long paramLong1, long paramLong2) {
/* 171 */     return UNSAFE.compareAndSwapLong(this, BASE, paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean casCellsBusy() {
/* 178 */     return UNSAFE.compareAndSwapInt(this, CELLSBUSY, 0, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int getProbe() {
/* 186 */     return UNSAFE.getInt(Thread.currentThread(), PROBE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int advanceProbe(int paramInt) {
/* 195 */     paramInt ^= paramInt << 13;
/* 196 */     paramInt ^= paramInt >>> 17;
/* 197 */     paramInt ^= paramInt << 5;
/* 198 */     UNSAFE.putInt(Thread.currentThread(), PROBE, paramInt);
/* 199 */     return paramInt;
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
/*     */   final void longAccumulate(long paramLong, LongBinaryOperator paramLongBinaryOperator, boolean paramBoolean) {
/*     */     int i;
/* 217 */     if ((i = getProbe()) == 0) {
/* 218 */       ThreadLocalRandom.current();
/* 219 */       i = getProbe();
/* 220 */       paramBoolean = true;
/*     */     } 
/* 222 */     boolean bool = false; while (true) {
/*     */       Cell[] arrayOfCell;
/*     */       int j;
/* 225 */       if ((arrayOfCell = this.cells) != null && (j = arrayOfCell.length) > 0) {
/* 226 */         Cell cell; if ((cell = arrayOfCell[j - 1 & i]) == null)
/* 227 */         { if (this.cellsBusy == 0) {
/* 228 */             Cell cell1 = new Cell(paramLong);
/* 229 */             if (this.cellsBusy == 0 && casCellsBusy()) {
/* 230 */               boolean bool1 = false; try {
/*     */                 Cell[] arrayOfCell1; int k;
/*     */                 int m;
/* 233 */                 if ((arrayOfCell1 = this.cells) != null && (k = arrayOfCell1.length) > 0 && arrayOfCell1[m = k - 1 & i] == null) {
/*     */ 
/*     */                   
/* 236 */                   arrayOfCell1[m] = cell1;
/* 237 */                   bool1 = true;
/*     */                 } 
/*     */               } finally {
/* 240 */                 this.cellsBusy = 0;
/*     */               } 
/* 242 */               if (bool1)
/*     */                 break; 
/*     */               continue;
/*     */             } 
/*     */           } 
/* 247 */           bool = false; }
/*     */         
/* 249 */         else if (!paramBoolean)
/* 250 */         { paramBoolean = true; }
/* 251 */         else { long l1; if (cell.cas(l1 = cell.value, (paramLongBinaryOperator == null) ? (l1 + paramLong) : paramLongBinaryOperator
/* 252 */               .applyAsLong(l1, paramLong)))
/*     */             break; 
/* 254 */           if (j >= NCPU || this.cells != arrayOfCell) {
/* 255 */             bool = false;
/* 256 */           } else if (!bool) {
/* 257 */             bool = true;
/* 258 */           } else if (this.cellsBusy == 0 && casCellsBusy()) {
/*     */             try {
/* 260 */               if (this.cells == arrayOfCell) {
/* 261 */                 Cell[] arrayOfCell1 = new Cell[j << 1];
/* 262 */                 for (byte b = 0; b < j; b++)
/* 263 */                   arrayOfCell1[b] = arrayOfCell[b]; 
/* 264 */                 this.cells = arrayOfCell1;
/*     */               } 
/*     */             } finally {
/* 267 */               this.cellsBusy = 0;
/*     */             } 
/* 269 */             bool = false; continue;
/*     */           }  }
/*     */         
/* 272 */         i = advanceProbe(i); continue;
/*     */       } 
/* 274 */       if (this.cellsBusy == 0 && this.cells == arrayOfCell && casCellsBusy()) {
/* 275 */         boolean bool1 = false;
/*     */         try {
/* 277 */           if (this.cells == arrayOfCell) {
/* 278 */             Cell[] arrayOfCell1 = new Cell[2];
/* 279 */             arrayOfCell1[i & 0x1] = new Cell(paramLong);
/* 280 */             this.cells = arrayOfCell1;
/* 281 */             bool1 = true;
/*     */           } 
/*     */         } finally {
/* 284 */           this.cellsBusy = 0;
/*     */         } 
/* 286 */         if (bool1)
/*     */           break;  continue;
/*     */       }  long l;
/* 289 */       if (casBase(l = this.base, (paramLongBinaryOperator == null) ? (l + paramLong) : paramLongBinaryOperator
/* 290 */           .applyAsLong(l, paramLong))) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void doubleAccumulate(double paramDouble, DoubleBinaryOperator paramDoubleBinaryOperator, boolean paramBoolean) {
/*     */     int i;
/* 304 */     if ((i = getProbe()) == 0) {
/* 305 */       ThreadLocalRandom.current();
/* 306 */       i = getProbe();
/* 307 */       paramBoolean = true;
/*     */     } 
/* 309 */     boolean bool = false; while (true) {
/*     */       Cell[] arrayOfCell;
/*     */       int j;
/* 312 */       if ((arrayOfCell = this.cells) != null && (j = arrayOfCell.length) > 0) {
/* 313 */         Cell cell; if ((cell = arrayOfCell[j - 1 & i]) == null)
/* 314 */         { if (this.cellsBusy == 0) {
/* 315 */             Cell cell1 = new Cell(Double.doubleToRawLongBits(paramDouble));
/* 316 */             if (this.cellsBusy == 0 && casCellsBusy()) {
/* 317 */               boolean bool1 = false; try {
/*     */                 Cell[] arrayOfCell1; int k;
/*     */                 int m;
/* 320 */                 if ((arrayOfCell1 = this.cells) != null && (k = arrayOfCell1.length) > 0 && arrayOfCell1[m = k - 1 & i] == null) {
/*     */ 
/*     */                   
/* 323 */                   arrayOfCell1[m] = cell1;
/* 324 */                   bool1 = true;
/*     */                 } 
/*     */               } finally {
/* 327 */                 this.cellsBusy = 0;
/*     */               } 
/* 329 */               if (bool1)
/*     */                 break; 
/*     */               continue;
/*     */             } 
/*     */           } 
/* 334 */           bool = false; }
/*     */         
/* 336 */         else if (!paramBoolean)
/* 337 */         { paramBoolean = true; }
/* 338 */         else { long l1; if (cell.cas(l1 = cell.value, (paramDoubleBinaryOperator == null) ? 
/*     */ 
/*     */               
/* 341 */               Double.doubleToRawLongBits(Double.longBitsToDouble(l1) + paramDouble) : 
/*     */               
/* 343 */               Double.doubleToRawLongBits(paramDoubleBinaryOperator
/* 344 */                 .applyAsDouble(Double.longBitsToDouble(l1), paramDouble))))
/*     */             break; 
/* 346 */           if (j >= NCPU || this.cells != arrayOfCell) {
/* 347 */             bool = false;
/* 348 */           } else if (!bool) {
/* 349 */             bool = true;
/* 350 */           } else if (this.cellsBusy == 0 && casCellsBusy()) {
/*     */             try {
/* 352 */               if (this.cells == arrayOfCell) {
/* 353 */                 Cell[] arrayOfCell1 = new Cell[j << 1];
/* 354 */                 for (byte b = 0; b < j; b++)
/* 355 */                   arrayOfCell1[b] = arrayOfCell[b]; 
/* 356 */                 this.cells = arrayOfCell1;
/*     */               } 
/*     */             } finally {
/* 359 */               this.cellsBusy = 0;
/*     */             } 
/* 361 */             bool = false; continue;
/*     */           }  }
/*     */         
/* 364 */         i = advanceProbe(i); continue;
/*     */       } 
/* 366 */       if (this.cellsBusy == 0 && this.cells == arrayOfCell && casCellsBusy()) {
/* 367 */         boolean bool1 = false;
/*     */         try {
/* 369 */           if (this.cells == arrayOfCell) {
/* 370 */             Cell[] arrayOfCell1 = new Cell[2];
/* 371 */             arrayOfCell1[i & 0x1] = new Cell(Double.doubleToRawLongBits(paramDouble));
/* 372 */             this.cells = arrayOfCell1;
/* 373 */             bool1 = true;
/*     */           } 
/*     */         } finally {
/* 376 */           this.cellsBusy = 0;
/*     */         } 
/* 378 */         if (bool1)
/*     */           break;  continue;
/*     */       }  long l;
/* 381 */       if (casBase(l = this.base, (paramDoubleBinaryOperator == null) ? 
/*     */ 
/*     */           
/* 384 */           Double.doubleToRawLongBits(Double.longBitsToDouble(l) + paramDouble) : 
/*     */           
/* 386 */           Double.doubleToRawLongBits(paramDoubleBinaryOperator
/* 387 */             .applyAsDouble(Double.longBitsToDouble(l), paramDouble)))) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 399 */       UNSAFE = Unsafe.getUnsafe();
/* 400 */       Class<Striped64> clazz = Striped64.class;
/*     */       
/* 402 */       BASE = UNSAFE.objectFieldOffset(clazz.getDeclaredField("base"));
/*     */       
/* 404 */       CELLSBUSY = UNSAFE.objectFieldOffset(clazz.getDeclaredField("cellsBusy"));
/* 405 */       Class<Thread> clazz1 = Thread.class;
/*     */       
/* 407 */       PROBE = UNSAFE.objectFieldOffset(clazz1.getDeclaredField("threadLocalRandomProbe"));
/* 408 */     } catch (Exception exception) {
/* 409 */       throw new Error(exception);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/atomic/Striped64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */