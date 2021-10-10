/*     */ package java.nio;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Buffer
/*     */ {
/*     */   static final int SPLITERATOR_CHARACTERISTICS = 16464;
/* 185 */   private int mark = -1;
/* 186 */   private int position = 0;
/*     */ 
/*     */   
/*     */   private int limit;
/*     */ 
/*     */   
/*     */   private int capacity;
/*     */   
/*     */   long address;
/*     */ 
/*     */   
/*     */   Buffer(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 198 */     if (paramInt4 < 0)
/* 199 */       throw new IllegalArgumentException("Negative capacity: " + paramInt4); 
/* 200 */     this.capacity = paramInt4;
/* 201 */     limit(paramInt3);
/* 202 */     position(paramInt2);
/* 203 */     if (paramInt1 >= 0) {
/* 204 */       if (paramInt1 > paramInt2) {
/* 205 */         throw new IllegalArgumentException("mark > position: (" + paramInt1 + " > " + paramInt2 + ")");
/*     */       }
/* 207 */       this.mark = paramInt1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int capacity() {
/* 217 */     return this.capacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int position() {
/* 226 */     return this.position;
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
/*     */   public final Buffer position(int paramInt) {
/* 243 */     if (paramInt > this.limit || paramInt < 0)
/* 244 */       throw new IllegalArgumentException(); 
/* 245 */     this.position = paramInt;
/* 246 */     if (this.mark > this.position) this.mark = -1; 
/* 247 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int limit() {
/* 256 */     return this.limit;
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
/*     */   public final Buffer limit(int paramInt) {
/* 274 */     if (paramInt > this.capacity || paramInt < 0)
/* 275 */       throw new IllegalArgumentException(); 
/* 276 */     this.limit = paramInt;
/* 277 */     if (this.position > this.limit) this.position = this.limit; 
/* 278 */     if (this.mark > this.limit) this.mark = -1; 
/* 279 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Buffer mark() {
/* 288 */     this.mark = this.position;
/* 289 */     return this;
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
/*     */   public final Buffer reset() {
/* 304 */     int i = this.mark;
/* 305 */     if (i < 0)
/* 306 */       throw new InvalidMarkException(); 
/* 307 */     this.position = i;
/* 308 */     return this;
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
/*     */   public final Buffer clear() {
/* 329 */     this.position = 0;
/* 330 */     this.limit = this.capacity;
/* 331 */     this.mark = -1;
/* 332 */     return this;
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
/*     */   public final Buffer flip() {
/* 357 */     this.limit = this.position;
/* 358 */     this.position = 0;
/* 359 */     this.mark = -1;
/* 360 */     return this;
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
/*     */   public final Buffer rewind() {
/* 379 */     this.position = 0;
/* 380 */     this.mark = -1;
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int remaining() {
/* 391 */     return this.limit - this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean hasRemaining() {
/* 402 */     return (this.position < this.limit);
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
/*     */   public abstract boolean isReadOnly();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean hasArray();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Object array();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int arrayOffset();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isDirect();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int nextGetIndex() {
/* 499 */     if (this.position >= this.limit)
/* 500 */       throw new BufferUnderflowException(); 
/* 501 */     return this.position++;
/*     */   }
/*     */   
/*     */   final int nextGetIndex(int paramInt) {
/* 505 */     if (this.limit - this.position < paramInt)
/* 506 */       throw new BufferUnderflowException(); 
/* 507 */     int i = this.position;
/* 508 */     this.position += paramInt;
/* 509 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int nextPutIndex() {
/* 520 */     if (this.position >= this.limit)
/* 521 */       throw new BufferOverflowException(); 
/* 522 */     return this.position++;
/*     */   }
/*     */   
/*     */   final int nextPutIndex(int paramInt) {
/* 526 */     if (this.limit - this.position < paramInt)
/* 527 */       throw new BufferOverflowException(); 
/* 528 */     int i = this.position;
/* 529 */     this.position += paramInt;
/* 530 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int checkIndex(int paramInt) {
/* 539 */     if (paramInt < 0 || paramInt >= this.limit)
/* 540 */       throw new IndexOutOfBoundsException(); 
/* 541 */     return paramInt;
/*     */   }
/*     */   
/*     */   final int checkIndex(int paramInt1, int paramInt2) {
/* 545 */     if (paramInt1 < 0 || paramInt2 > this.limit - paramInt1)
/* 546 */       throw new IndexOutOfBoundsException(); 
/* 547 */     return paramInt1;
/*     */   }
/*     */   
/*     */   final int markValue() {
/* 551 */     return this.mark;
/*     */   }
/*     */   
/*     */   final void truncate() {
/* 555 */     this.mark = -1;
/* 556 */     this.position = 0;
/* 557 */     this.limit = 0;
/* 558 */     this.capacity = 0;
/*     */   }
/*     */   
/*     */   final void discardMark() {
/* 562 */     this.mark = -1;
/*     */   }
/*     */   
/*     */   static void checkBounds(int paramInt1, int paramInt2, int paramInt3) {
/* 566 */     if ((paramInt1 | paramInt2 | paramInt1 + paramInt2 | paramInt3 - paramInt1 + paramInt2) < 0)
/* 567 */       throw new IndexOutOfBoundsException(); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/Buffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */