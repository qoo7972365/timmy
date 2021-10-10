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
/*     */ class DirectLongBufferU
/*     */   extends LongBuffer
/*     */   implements DirectBuffer
/*     */ {
/*  49 */   protected static final Unsafe unsafe = Bits.unsafe();
/*     */ 
/*     */   
/*  52 */   private static final long arrayBaseOffset = unsafe.arrayBaseOffset(long[].class);
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
/*     */   DirectLongBufferU(DirectBuffer paramDirectBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
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
/*     */   public LongBuffer slice() {
/* 207 */     int i = position();
/* 208 */     int j = limit();
/* 209 */     assert i <= j;
/* 210 */     boolean bool = (i <= j) ? (j - i) : false;
/* 211 */     int k = i << 3;
/* 212 */     assert k >= 0;
/* 213 */     return new DirectLongBufferU(this, -1, 0, bool, bool, k);
/*     */   }
/*     */   
/*     */   public LongBuffer duplicate() {
/* 217 */     return new DirectLongBufferU(this, 
/* 218 */         markValue(), 
/* 219 */         position(), 
/* 220 */         limit(), 
/* 221 */         capacity(), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LongBuffer asReadOnlyBuffer() {
/* 227 */     return new DirectLongBufferRU(this, 
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
/*     */   public long get() {
/* 249 */     return unsafe.getLong(ix(nextGetIndex()));
/*     */   }
/*     */   
/*     */   public long get(int paramInt) {
/* 253 */     return unsafe.getLong(ix(checkIndex(paramInt)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongBuffer get(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 264 */     if (paramInt2 << 3L > 6L) {
/* 265 */       checkBounds(paramInt1, paramInt2, paramArrayOflong.length);
/* 266 */       int i = position();
/* 267 */       int j = limit();
/* 268 */       assert i <= j;
/* 269 */       byte b = (i <= j) ? (j - i) : 0;
/* 270 */       if (paramInt2 > b) {
/* 271 */         throw new BufferUnderflowException();
/*     */       }
/*     */       
/* 274 */       if (order() != ByteOrder.nativeOrder()) {
/* 275 */         Bits.copyToLongArray(ix(i), paramArrayOflong, paramInt1 << 3L, paramInt2 << 3L);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 280 */         Bits.copyToArray(ix(i), paramArrayOflong, arrayBaseOffset, paramInt1 << 3L, paramInt2 << 3L);
/*     */       } 
/*     */       
/* 283 */       position(i + paramInt2);
/*     */     } else {
/* 285 */       super.get(paramArrayOflong, paramInt1, paramInt2);
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
/*     */   public LongBuffer put(long paramLong) {
/* 297 */     unsafe.putLong(ix(nextPutIndex()), paramLong);
/* 298 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongBuffer put(int paramInt, long paramLong) {
/* 306 */     unsafe.putLong(ix(checkIndex(paramInt)), paramLong);
/* 307 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongBuffer put(LongBuffer paramLongBuffer) {
/* 315 */     if (paramLongBuffer instanceof DirectLongBufferU) {
/* 316 */       if (paramLongBuffer == this)
/* 317 */         throw new IllegalArgumentException(); 
/* 318 */       DirectLongBufferU directLongBufferU = (DirectLongBufferU)paramLongBuffer;
/*     */       
/* 320 */       int i = directLongBufferU.position();
/* 321 */       int j = directLongBufferU.limit();
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
/* 332 */       unsafe.copyMemory(directLongBufferU.ix(i), ix(k), b1 << 3L);
/* 333 */       directLongBufferU.position(i + b1);
/* 334 */       position(k + b1);
/* 335 */     } else if (paramLongBuffer.hb != null) {
/*     */       
/* 337 */       int i = paramLongBuffer.position();
/* 338 */       int j = paramLongBuffer.limit();
/* 339 */       assert i <= j;
/* 340 */       byte b = (i <= j) ? (j - i) : 0;
/*     */       
/* 342 */       put(paramLongBuffer.hb, paramLongBuffer.offset + i, b);
/* 343 */       paramLongBuffer.position(i + b);
/*     */     } else {
/*     */       
/* 346 */       super.put(paramLongBuffer);
/*     */     } 
/* 348 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongBuffer put(long[] paramArrayOflong, int paramInt1, int paramInt2) {
/* 356 */     if (paramInt2 << 3L > 6L) {
/* 357 */       checkBounds(paramInt1, paramInt2, paramArrayOflong.length);
/* 358 */       int i = position();
/* 359 */       int j = limit();
/* 360 */       assert i <= j;
/* 361 */       byte b = (i <= j) ? (j - i) : 0;
/* 362 */       if (paramInt2 > b) {
/* 363 */         throw new BufferOverflowException();
/*     */       }
/*     */       
/* 366 */       if (order() != ByteOrder.nativeOrder()) {
/* 367 */         Bits.copyFromLongArray(paramArrayOflong, paramInt1 << 3L, 
/*     */             
/* 369 */             ix(i), paramInt2 << 3L);
/*     */       }
/*     */       else {
/*     */         
/* 373 */         Bits.copyFromArray(paramArrayOflong, arrayBaseOffset, paramInt1 << 3L, 
/*     */             
/* 375 */             ix(i), paramInt2 << 3L);
/*     */       } 
/* 377 */       position(i + paramInt2);
/*     */     } else {
/* 379 */       super.put(paramArrayOflong, paramInt1, paramInt2);
/*     */     } 
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongBuffer compact() {
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOrder order() {
/* 464 */     return (ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN) ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/DirectLongBufferU.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */