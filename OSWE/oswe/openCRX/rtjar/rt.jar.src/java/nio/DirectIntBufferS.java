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
/*     */ class DirectIntBufferS
/*     */   extends IntBuffer
/*     */   implements DirectBuffer
/*     */ {
/*  49 */   protected static final Unsafe unsafe = Bits.unsafe();
/*     */ 
/*     */   
/*  52 */   private static final long arrayBaseOffset = unsafe.arrayBaseOffset(int[].class);
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
/*     */   DirectIntBufferS(DirectBuffer paramDirectBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
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
/*     */   public IntBuffer slice() {
/* 207 */     int i = position();
/* 208 */     int j = limit();
/* 209 */     assert i <= j;
/* 210 */     boolean bool = (i <= j) ? (j - i) : false;
/* 211 */     int k = i << 2;
/* 212 */     assert k >= 0;
/* 213 */     return new DirectIntBufferS(this, -1, 0, bool, bool, k);
/*     */   }
/*     */   
/*     */   public IntBuffer duplicate() {
/* 217 */     return new DirectIntBufferS(this, 
/* 218 */         markValue(), 
/* 219 */         position(), 
/* 220 */         limit(), 
/* 221 */         capacity(), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IntBuffer asReadOnlyBuffer() {
/* 227 */     return new DirectIntBufferRS(this, 
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
/* 245 */     return this.address + (paramInt << 2L);
/*     */   }
/*     */   
/*     */   public int get() {
/* 249 */     return Bits.swap(unsafe.getInt(ix(nextGetIndex())));
/*     */   }
/*     */   
/*     */   public int get(int paramInt) {
/* 253 */     return Bits.swap(unsafe.getInt(ix(checkIndex(paramInt))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntBuffer get(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 264 */     if (paramInt2 << 2L > 6L) {
/* 265 */       checkBounds(paramInt1, paramInt2, paramArrayOfint.length);
/* 266 */       int i = position();
/* 267 */       int j = limit();
/* 268 */       assert i <= j;
/* 269 */       byte b = (i <= j) ? (j - i) : 0;
/* 270 */       if (paramInt2 > b) {
/* 271 */         throw new BufferUnderflowException();
/*     */       }
/*     */       
/* 274 */       if (order() != ByteOrder.nativeOrder()) {
/* 275 */         Bits.copyToIntArray(ix(i), paramArrayOfint, paramInt1 << 2L, paramInt2 << 2L);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 280 */         Bits.copyToArray(ix(i), paramArrayOfint, arrayBaseOffset, paramInt1 << 2L, paramInt2 << 2L);
/*     */       } 
/*     */       
/* 283 */       position(i + paramInt2);
/*     */     } else {
/* 285 */       super.get(paramArrayOfint, paramInt1, paramInt2);
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
/*     */   public IntBuffer put(int paramInt) {
/* 297 */     unsafe.putInt(ix(nextPutIndex()), Bits.swap(paramInt));
/* 298 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntBuffer put(int paramInt1, int paramInt2) {
/* 306 */     unsafe.putInt(ix(checkIndex(paramInt1)), Bits.swap(paramInt2));
/* 307 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntBuffer put(IntBuffer paramIntBuffer) {
/* 315 */     if (paramIntBuffer instanceof DirectIntBufferS) {
/* 316 */       if (paramIntBuffer == this)
/* 317 */         throw new IllegalArgumentException(); 
/* 318 */       DirectIntBufferS directIntBufferS = (DirectIntBufferS)paramIntBuffer;
/*     */       
/* 320 */       int i = directIntBufferS.position();
/* 321 */       int j = directIntBufferS.limit();
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
/* 332 */       unsafe.copyMemory(directIntBufferS.ix(i), ix(k), b1 << 2L);
/* 333 */       directIntBufferS.position(i + b1);
/* 334 */       position(k + b1);
/* 335 */     } else if (paramIntBuffer.hb != null) {
/*     */       
/* 337 */       int i = paramIntBuffer.position();
/* 338 */       int j = paramIntBuffer.limit();
/* 339 */       assert i <= j;
/* 340 */       byte b = (i <= j) ? (j - i) : 0;
/*     */       
/* 342 */       put(paramIntBuffer.hb, paramIntBuffer.offset + i, b);
/* 343 */       paramIntBuffer.position(i + b);
/*     */     } else {
/*     */       
/* 346 */       super.put(paramIntBuffer);
/*     */     } 
/* 348 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntBuffer put(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 356 */     if (paramInt2 << 2L > 6L) {
/* 357 */       checkBounds(paramInt1, paramInt2, paramArrayOfint.length);
/* 358 */       int i = position();
/* 359 */       int j = limit();
/* 360 */       assert i <= j;
/* 361 */       byte b = (i <= j) ? (j - i) : 0;
/* 362 */       if (paramInt2 > b) {
/* 363 */         throw new BufferOverflowException();
/*     */       }
/*     */       
/* 366 */       if (order() != ByteOrder.nativeOrder()) {
/* 367 */         Bits.copyFromIntArray(paramArrayOfint, paramInt1 << 2L, 
/*     */             
/* 369 */             ix(i), paramInt2 << 2L);
/*     */       }
/*     */       else {
/*     */         
/* 373 */         Bits.copyFromArray(paramArrayOfint, arrayBaseOffset, paramInt1 << 2L, 
/*     */             
/* 375 */             ix(i), paramInt2 << 2L);
/*     */       } 
/* 377 */       position(i + paramInt2);
/*     */     } else {
/* 379 */       super.put(paramArrayOfint, paramInt1, paramInt2);
/*     */     } 
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntBuffer compact() {
/* 389 */     int i = position();
/* 390 */     int j = limit();
/* 391 */     assert i <= j;
/* 392 */     boolean bool = (i <= j) ? (j - i) : false;
/*     */     
/* 394 */     unsafe.copyMemory(ix(i), ix(0), bool << 2L);
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


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/DirectIntBufferS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */