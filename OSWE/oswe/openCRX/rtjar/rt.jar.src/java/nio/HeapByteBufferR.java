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
/*     */ class HeapByteBufferR
/*     */   extends HeapByteBuffer
/*     */ {
/*     */   HeapByteBufferR(int paramInt1, int paramInt2) {
/*  63 */     super(paramInt1, paramInt2);
/*  64 */     this.isReadOnly = true;
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
/*     */   HeapByteBufferR(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  76 */     super(paramArrayOfbyte, paramInt1, paramInt2);
/*  77 */     this.isReadOnly = true;
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
/*     */   protected HeapByteBufferR(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  92 */     super(paramArrayOfbyte, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*  93 */     this.isReadOnly = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer slice() {
/*  98 */     return new HeapByteBufferR(this.hb, -1, 0, 
/*     */ 
/*     */         
/* 101 */         remaining(), 
/* 102 */         remaining(), 
/* 103 */         position() + this.offset);
/*     */   }
/*     */   
/*     */   public ByteBuffer duplicate() {
/* 107 */     return new HeapByteBufferR(this.hb, 
/* 108 */         markValue(), 
/* 109 */         position(), 
/* 110 */         limit(), 
/* 111 */         capacity(), this.offset);
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
/*     */   public ByteBuffer asReadOnlyBuffer() {
/* 124 */     return duplicate();
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
/*     */   public boolean isReadOnly() {
/* 164 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer put(byte paramByte) {
/* 172 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer put(int paramInt, byte paramByte) {
/* 181 */     throw new ReadOnlyBufferException();
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
/*     */   public ByteBuffer put(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 194 */     throw new ReadOnlyBufferException();
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
/*     */   public ByteBuffer put(ByteBuffer paramByteBuffer) {
/* 222 */     throw new ReadOnlyBufferException();
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
/*     */   public ByteBuffer compact() {
/* 234 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte _get(int paramInt) {
/* 243 */     return this.hb[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void _put(int paramInt, byte paramByte) {
/* 250 */     throw new ReadOnlyBufferException();
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
/*     */   public ByteBuffer putChar(char paramChar) {
/* 273 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putChar(int paramInt, char paramChar) {
/* 282 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public CharBuffer asCharBuffer() {
/* 287 */     int i = remaining() >> 1;
/* 288 */     int j = this.offset + position();
/* 289 */     return this.bigEndian ? new ByteBufferAsCharBufferRB(this, -1, 0, i, i, j) : new ByteBufferAsCharBufferRL(this, -1, 0, i, i, j);
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
/*     */   public ByteBuffer putShort(short paramShort) {
/* 324 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putShort(int paramInt, short paramShort) {
/* 333 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public ShortBuffer asShortBuffer() {
/* 338 */     int i = remaining() >> 1;
/* 339 */     int j = this.offset + position();
/* 340 */     return this.bigEndian ? new ByteBufferAsShortBufferRB(this, -1, 0, i, i, j) : new ByteBufferAsShortBufferRL(this, -1, 0, i, i, j);
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
/*     */   public ByteBuffer putInt(int paramInt) {
/* 375 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putInt(int paramInt1, int paramInt2) {
/* 384 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public IntBuffer asIntBuffer() {
/* 389 */     int i = remaining() >> 2;
/* 390 */     int j = this.offset + position();
/* 391 */     return this.bigEndian ? new ByteBufferAsIntBufferRB(this, -1, 0, i, i, j) : new ByteBufferAsIntBufferRL(this, -1, 0, i, i, j);
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
/*     */   public ByteBuffer putLong(long paramLong) {
/* 426 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putLong(int paramInt, long paramLong) {
/* 435 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public LongBuffer asLongBuffer() {
/* 440 */     int i = remaining() >> 3;
/* 441 */     int j = this.offset + position();
/* 442 */     return this.bigEndian ? new ByteBufferAsLongBufferRB(this, -1, 0, i, i, j) : new ByteBufferAsLongBufferRL(this, -1, 0, i, i, j);
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
/*     */   public ByteBuffer putFloat(float paramFloat) {
/* 477 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putFloat(int paramInt, float paramFloat) {
/* 486 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public FloatBuffer asFloatBuffer() {
/* 491 */     int i = remaining() >> 2;
/* 492 */     int j = this.offset + position();
/* 493 */     return this.bigEndian ? new ByteBufferAsFloatBufferRB(this, -1, 0, i, i, j) : new ByteBufferAsFloatBufferRL(this, -1, 0, i, i, j);
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
/*     */   public ByteBuffer putDouble(double paramDouble) {
/* 528 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putDouble(int paramInt, double paramDouble) {
/* 537 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */ 
/*     */   
/*     */   public DoubleBuffer asDoubleBuffer() {
/* 542 */     int i = remaining() >> 3;
/* 543 */     int j = this.offset + position();
/* 544 */     return this.bigEndian ? new ByteBufferAsDoubleBufferRB(this, -1, 0, i, i, j) : new ByteBufferAsDoubleBufferRL(this, -1, 0, i, i, j);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/HeapByteBufferR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */