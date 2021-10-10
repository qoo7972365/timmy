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
/*     */ class DirectCharBufferS
/*     */   extends CharBuffer
/*     */   implements DirectBuffer
/*     */ {
/*  49 */   protected static final Unsafe unsafe = Bits.unsafe();
/*     */ 
/*     */   
/*  52 */   private static final long arrayBaseOffset = unsafe.arrayBaseOffset(char[].class);
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
/*     */   DirectCharBufferS(DirectBuffer paramDirectBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
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
/*     */   public CharBuffer slice() {
/* 207 */     int i = position();
/* 208 */     int j = limit();
/* 209 */     assert i <= j;
/* 210 */     boolean bool = (i <= j) ? (j - i) : false;
/* 211 */     int k = i << 1;
/* 212 */     assert k >= 0;
/* 213 */     return new DirectCharBufferS(this, -1, 0, bool, bool, k);
/*     */   }
/*     */   
/*     */   public CharBuffer duplicate() {
/* 217 */     return new DirectCharBufferS(this, 
/* 218 */         markValue(), 
/* 219 */         position(), 
/* 220 */         limit(), 
/* 221 */         capacity(), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CharBuffer asReadOnlyBuffer() {
/* 227 */     return new DirectCharBufferRS(this, 
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
/* 245 */     return this.address + (paramInt << 1L);
/*     */   }
/*     */   
/*     */   public char get() {
/* 249 */     return Bits.swap(unsafe.getChar(ix(nextGetIndex())));
/*     */   }
/*     */   
/*     */   public char get(int paramInt) {
/* 253 */     return Bits.swap(unsafe.getChar(ix(checkIndex(paramInt))));
/*     */   }
/*     */ 
/*     */   
/*     */   char getUnchecked(int paramInt) {
/* 258 */     return Bits.swap(unsafe.getChar(ix(paramInt)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CharBuffer get(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 264 */     if (paramInt2 << 1L > 6L) {
/* 265 */       checkBounds(paramInt1, paramInt2, paramArrayOfchar.length);
/* 266 */       int i = position();
/* 267 */       int j = limit();
/* 268 */       assert i <= j;
/* 269 */       byte b = (i <= j) ? (j - i) : 0;
/* 270 */       if (paramInt2 > b) {
/* 271 */         throw new BufferUnderflowException();
/*     */       }
/*     */       
/* 274 */       if (order() != ByteOrder.nativeOrder()) {
/* 275 */         Bits.copyToCharArray(ix(i), paramArrayOfchar, paramInt1 << 1L, paramInt2 << 1L);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 280 */         Bits.copyToArray(ix(i), paramArrayOfchar, arrayBaseOffset, paramInt1 << 1L, paramInt2 << 1L);
/*     */       } 
/*     */       
/* 283 */       position(i + paramInt2);
/*     */     } else {
/* 285 */       super.get(paramArrayOfchar, paramInt1, paramInt2);
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
/*     */   public CharBuffer put(char paramChar) {
/* 297 */     unsafe.putChar(ix(nextPutIndex()), Bits.swap(paramChar));
/* 298 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharBuffer put(int paramInt, char paramChar) {
/* 306 */     unsafe.putChar(ix(checkIndex(paramInt)), Bits.swap(paramChar));
/* 307 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharBuffer put(CharBuffer paramCharBuffer) {
/* 315 */     if (paramCharBuffer instanceof DirectCharBufferS) {
/* 316 */       if (paramCharBuffer == this)
/* 317 */         throw new IllegalArgumentException(); 
/* 318 */       DirectCharBufferS directCharBufferS = (DirectCharBufferS)paramCharBuffer;
/*     */       
/* 320 */       int i = directCharBufferS.position();
/* 321 */       int j = directCharBufferS.limit();
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
/* 332 */       unsafe.copyMemory(directCharBufferS.ix(i), ix(k), b1 << 1L);
/* 333 */       directCharBufferS.position(i + b1);
/* 334 */       position(k + b1);
/* 335 */     } else if (paramCharBuffer.hb != null) {
/*     */       
/* 337 */       int i = paramCharBuffer.position();
/* 338 */       int j = paramCharBuffer.limit();
/* 339 */       assert i <= j;
/* 340 */       byte b = (i <= j) ? (j - i) : 0;
/*     */       
/* 342 */       put(paramCharBuffer.hb, paramCharBuffer.offset + i, b);
/* 343 */       paramCharBuffer.position(i + b);
/*     */     } else {
/*     */       
/* 346 */       super.put(paramCharBuffer);
/*     */     } 
/* 348 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharBuffer put(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 356 */     if (paramInt2 << 1L > 6L) {
/* 357 */       checkBounds(paramInt1, paramInt2, paramArrayOfchar.length);
/* 358 */       int i = position();
/* 359 */       int j = limit();
/* 360 */       assert i <= j;
/* 361 */       byte b = (i <= j) ? (j - i) : 0;
/* 362 */       if (paramInt2 > b) {
/* 363 */         throw new BufferOverflowException();
/*     */       }
/*     */       
/* 366 */       if (order() != ByteOrder.nativeOrder()) {
/* 367 */         Bits.copyFromCharArray(paramArrayOfchar, paramInt1 << 1L, 
/*     */             
/* 369 */             ix(i), paramInt2 << 1L);
/*     */       }
/*     */       else {
/*     */         
/* 373 */         Bits.copyFromArray(paramArrayOfchar, arrayBaseOffset, paramInt1 << 1L, 
/*     */             
/* 375 */             ix(i), paramInt2 << 1L);
/*     */       } 
/* 377 */       position(i + paramInt2);
/*     */     } else {
/* 379 */       super.put(paramArrayOfchar, paramInt1, paramInt2);
/*     */     } 
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharBuffer compact() {
/* 389 */     int i = position();
/* 390 */     int j = limit();
/* 391 */     assert i <= j;
/* 392 */     boolean bool = (i <= j) ? (j - i) : false;
/*     */     
/* 394 */     unsafe.copyMemory(ix(i), ix(0), bool << 1L);
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
/*     */   public String toString(int paramInt1, int paramInt2) {
/* 416 */     if (paramInt2 > limit() || paramInt1 > paramInt2)
/* 417 */       throw new IndexOutOfBoundsException(); 
/*     */     try {
/* 419 */       int i = paramInt2 - paramInt1;
/* 420 */       char[] arrayOfChar = new char[i];
/* 421 */       CharBuffer charBuffer1 = CharBuffer.wrap(arrayOfChar);
/* 422 */       CharBuffer charBuffer2 = duplicate();
/* 423 */       charBuffer2.position(paramInt1);
/* 424 */       charBuffer2.limit(paramInt2);
/* 425 */       charBuffer1.put(charBuffer2);
/* 426 */       return new String(arrayOfChar);
/* 427 */     } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
/* 428 */       throw new IndexOutOfBoundsException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharBuffer subSequence(int paramInt1, int paramInt2) {
/* 436 */     int i = position();
/* 437 */     int j = limit();
/* 438 */     assert i <= j;
/* 439 */     i = (i <= j) ? i : j;
/* 440 */     int k = j - i;
/*     */     
/* 442 */     if (paramInt1 < 0 || paramInt2 > k || paramInt1 > paramInt2)
/* 443 */       throw new IndexOutOfBoundsException(); 
/* 444 */     return new DirectCharBufferS(this, -1, i + paramInt1, i + paramInt2, 
/*     */ 
/*     */ 
/*     */         
/* 448 */         capacity(), this.offset);
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
/*     */   public ByteOrder order() {
/* 460 */     return (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/DirectCharBufferS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */