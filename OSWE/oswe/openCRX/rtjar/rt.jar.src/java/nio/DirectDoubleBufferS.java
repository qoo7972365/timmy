/*     */ package java.nio;
/*     */ 
/*     */ import sun.misc.Cleaner;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.nio.ch.DirectBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DirectDoubleBufferS
/*     */   extends DoubleBuffer
/*     */   implements DirectBuffer
/*     */ {
/*  49 */   protected static final Unsafe unsafe = Bits.unsafe();
/*     */ 
/*     */   
/*  52 */   private static final long arrayBaseOffset = unsafe.arrayBaseOffset(double[].class);
/*     */ 
/*     */   
/*  55 */   protected static final boolean unaligned = Bits.unaligned();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Object att;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object attachment() {
/*  67 */     return this.att;
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
/*     */   public Cleaner cleaner() {
/* 107 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DirectDoubleBufferS(DirectBuffer paramDirectBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 195 */     super(paramInt1, paramInt2, paramInt3, paramInt4);
/* 196 */     this.address = paramDirectBuffer.address() + paramInt5;
/*     */ 
/*     */ 
/*     */     
/* 200 */     this.att = paramDirectBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleBuffer slice() {
/* 207 */     int i = position();
/* 208 */     int j = limit();
/* 209 */     assert i <= j;
/* 210 */     boolean bool = (i <= j) ? (j - i) : false;
/* 211 */     int k = i << 3;
/* 212 */     assert k >= 0;
/* 213 */     return new DirectDoubleBufferS(this, -1, 0, bool, bool, k);
/*     */   }
/*     */   
/*     */   public DoubleBuffer duplicate() {
/* 217 */     return new DirectDoubleBufferS(this, 
/* 218 */         markValue(), 
/* 219 */         position(), 
/* 220 */         limit(), 
/* 221 */         capacity(), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleBuffer asReadOnlyBuffer() {
/* 227 */     return new DirectDoubleBufferRS(this, 
/* 228 */         markValue(), 
/* 229 */         position(), 
/* 230 */         limit(), 
/* 231 */         capacity(), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long address() {
/* 241 */     return this.address;
/*     */   }
/*     */   
/*     */   private long ix(int paramInt) {
/* 245 */     return this.address + (paramInt << 3L);
/*     */   }
/*     */   
/*     */   public double get() {
/* 249 */     return Double.longBitsToDouble(Bits.swap(unsafe.getLong(ix(nextGetIndex()))));
/*     */   }
/*     */   
/*     */   public double get(int paramInt) {
/* 253 */     return Double.longBitsToDouble(Bits.swap(unsafe.getLong(ix(checkIndex(paramInt)))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleBuffer get(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
/* 264 */     if (paramInt2 << 3L > 6L) {
/* 265 */       checkBounds(paramInt1, paramInt2, paramArrayOfdouble.length);
/* 266 */       int i = position();
/* 267 */       int j = limit();
/* 268 */       assert i <= j;
/* 269 */       byte b = (i <= j) ? (j - i) : 0;
/* 270 */       if (paramInt2 > b) {
/* 271 */         throw new BufferUnderflowException();
/*     */       }
/*     */       
/* 274 */       if (order() != ByteOrder.nativeOrder()) {
/* 275 */         Bits.copyToLongArray(ix(i), paramArrayOfdouble, paramInt1 << 3L, paramInt2 << 3L);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 280 */         Bits.copyToArray(ix(i), paramArrayOfdouble, arrayBaseOffset, paramInt1 << 3L, paramInt2 << 3L);
/*     */       } 
/*     */       
/* 283 */       position(i + paramInt2);
/*     */     } else {
/* 285 */       super.get(paramArrayOfdouble, paramInt1, paramInt2);
/*     */     } 
/* 287 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleBuffer put(double paramDouble) {
/* 297 */     unsafe.putLong(ix(nextPutIndex()), Bits.swap(Double.doubleToRawLongBits(paramDouble)));
/* 298 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleBuffer put(int paramInt, double paramDouble) {
/* 306 */     unsafe.putLong(ix(checkIndex(paramInt)), Bits.swap(Double.doubleToRawLongBits(paramDouble)));
/* 307 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleBuffer put(DoubleBuffer paramDoubleBuffer) {
/* 315 */     if (paramDoubleBuffer instanceof DirectDoubleBufferS) {
/* 316 */       if (paramDoubleBuffer == this)
/* 317 */         throw new IllegalArgumentException(); 
/* 318 */       DirectDoubleBufferS directDoubleBufferS = (DirectDoubleBufferS)paramDoubleBuffer;
/*     */       
/* 320 */       int i = directDoubleBufferS.position();
/* 321 */       int j = directDoubleBufferS.limit();
/* 322 */       assert i <= j;
/* 323 */       byte b1 = (i <= j) ? (j - i) : 0;
/*     */       
/* 325 */       int k = position();
/* 326 */       int m = limit();
/* 327 */       assert k <= m;
/* 328 */       byte b2 = (k <= m) ? (m - k) : 0;
/*     */       
/* 330 */       if (b1 > b2)
/* 331 */         throw new BufferOverflowException(); 
/* 332 */       unsafe.copyMemory(directDoubleBufferS.ix(i), ix(k), b1 << 3L);
/* 333 */       directDoubleBufferS.position(i + b1);
/* 334 */       position(k + b1);
/* 335 */     } else if (paramDoubleBuffer.hb != null) {
/*     */       
/* 337 */       int i = paramDoubleBuffer.position();
/* 338 */       int j = paramDoubleBuffer.limit();
/* 339 */       assert i <= j;
/* 340 */       byte b = (i <= j) ? (j - i) : 0;
/*     */       
/* 342 */       put(paramDoubleBuffer.hb, paramDoubleBuffer.offset + i, b);
/* 343 */       paramDoubleBuffer.position(i + b);
/*     */     } else {
/*     */       
/* 346 */       super.put(paramDoubleBuffer);
/*     */     } 
/* 348 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleBuffer put(double[] paramArrayOfdouble, int paramInt1, int paramInt2) {
/* 356 */     if (paramInt2 << 3L > 6L) {
/* 357 */       checkBounds(paramInt1, paramInt2, paramArrayOfdouble.length);
/* 358 */       int i = position();
/* 359 */       int j = limit();
/* 360 */       assert i <= j;
/* 361 */       byte b = (i <= j) ? (j - i) : 0;
/* 362 */       if (paramInt2 > b) {
/* 363 */         throw new BufferOverflowException();
/*     */       }
/*     */       
/* 366 */       if (order() != ByteOrder.nativeOrder()) {
/* 367 */         Bits.copyFromLongArray(paramArrayOfdouble, paramInt1 << 3L, 
/*     */             
/* 369 */             ix(i), paramInt2 << 3L);
/*     */       }
/*     */       else {
/*     */         
/* 373 */         Bits.copyFromArray(paramArrayOfdouble, arrayBaseOffset, paramInt1 << 3L, 
/*     */             
/* 375 */             ix(i), paramInt2 << 3L);
/*     */       } 
/* 377 */       position(i + paramInt2);
/*     */     } else {
/* 379 */       super.put(paramArrayOfdouble, paramInt1, paramInt2);
/*     */     } 
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleBuffer compact() {
/* 389 */     int i = position();
/* 390 */     int j = limit();
/* 391 */     assert i <= j;
/* 392 */     boolean bool = (i <= j) ? (j - i) : false;
/*     */     
/* 394 */     unsafe.copyMemory(ix(i), ix(0), bool << 3L);
/* 395 */     position(bool);
/* 396 */     limit(capacity());
/* 397 */     discardMark();
/* 398 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/* 405 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isReadOnly() {
/* 409 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOrder order() {
/* 460 */     return (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/DirectDoubleBufferS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */