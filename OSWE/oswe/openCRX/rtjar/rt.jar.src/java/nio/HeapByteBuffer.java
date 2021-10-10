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
/*     */ class HeapByteBuffer
/*     */   extends ByteBuffer
/*     */ {
/*     */   HeapByteBuffer(int paramInt1, int paramInt2) {
/*  57 */     super(-1, 0, paramInt2, paramInt1, new byte[paramInt1], 0);
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
/*     */   HeapByteBuffer(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  70 */     super(-1, paramInt1, paramInt1 + paramInt2, paramArrayOfbyte.length, paramArrayOfbyte, 0);
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
/*     */   protected HeapByteBuffer(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  86 */     super(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfbyte, paramInt5);
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
/*     */   public ByteBuffer slice() {
/*  98 */     return new HeapByteBuffer(this.hb, -1, 0, 
/*     */ 
/*     */         
/* 101 */         remaining(), 
/* 102 */         remaining(), 
/* 103 */         position() + this.offset);
/*     */   }
/*     */   
/*     */   public ByteBuffer duplicate() {
/* 107 */     return new HeapByteBuffer(this.hb, 
/* 108 */         markValue(), 
/* 109 */         position(), 
/* 110 */         limit(), 
/* 111 */         capacity(), this.offset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer asReadOnlyBuffer() {
/* 117 */     return new HeapByteBufferR(this.hb, 
/* 118 */         markValue(), 
/* 119 */         position(), 
/* 120 */         limit(), 
/* 121 */         capacity(), this.offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int ix(int paramInt) {
/* 131 */     return paramInt + this.offset;
/*     */   }
/*     */   
/*     */   public byte get() {
/* 135 */     return this.hb[ix(nextGetIndex())];
/*     */   }
/*     */   
/*     */   public byte get(int paramInt) {
/* 139 */     return this.hb[ix(checkIndex(paramInt))];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer get(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 149 */     checkBounds(paramInt1, paramInt2, paramArrayOfbyte.length);
/* 150 */     if (paramInt2 > remaining())
/* 151 */       throw new BufferUnderflowException(); 
/* 152 */     System.arraycopy(this.hb, ix(position()), paramArrayOfbyte, paramInt1, paramInt2);
/* 153 */     position(position() + paramInt2);
/* 154 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isDirect() {
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReadOnly() {
/* 164 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer put(byte paramByte) {
/* 169 */     this.hb[ix(nextPutIndex())] = paramByte;
/* 170 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer put(int paramInt, byte paramByte) {
/* 178 */     this.hb[ix(checkIndex(paramInt))] = paramByte;
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer put(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 187 */     checkBounds(paramInt1, paramInt2, paramArrayOfbyte.length);
/* 188 */     if (paramInt2 > remaining())
/* 189 */       throw new BufferOverflowException(); 
/* 190 */     System.arraycopy(paramArrayOfbyte, paramInt1, this.hb, ix(position()), paramInt2);
/* 191 */     position(position() + paramInt2);
/* 192 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer put(ByteBuffer paramByteBuffer) {
/* 200 */     if (paramByteBuffer instanceof HeapByteBuffer) {
/* 201 */       if (paramByteBuffer == this)
/* 202 */         throw new IllegalArgumentException(); 
/* 203 */       HeapByteBuffer heapByteBuffer = (HeapByteBuffer)paramByteBuffer;
/* 204 */       int i = heapByteBuffer.remaining();
/* 205 */       if (i > remaining())
/* 206 */         throw new BufferOverflowException(); 
/* 207 */       System.arraycopy(heapByteBuffer.hb, heapByteBuffer.ix(heapByteBuffer.position()), this.hb, 
/* 208 */           ix(position()), i);
/* 209 */       heapByteBuffer.position(heapByteBuffer.position() + i);
/* 210 */       position(position() + i);
/* 211 */     } else if (paramByteBuffer.isDirect()) {
/* 212 */       int i = paramByteBuffer.remaining();
/* 213 */       if (i > remaining())
/* 214 */         throw new BufferOverflowException(); 
/* 215 */       paramByteBuffer.get(this.hb, ix(position()), i);
/* 216 */       position(position() + i);
/*     */     } else {
/* 218 */       super.put(paramByteBuffer);
/*     */     } 
/* 220 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer compact() {
/* 228 */     System.arraycopy(this.hb, ix(position()), this.hb, ix(0), remaining());
/* 229 */     position(remaining());
/* 230 */     limit(capacity());
/* 231 */     discardMark();
/* 232 */     return this;
/*     */   }
/*     */ 
/*     */ 
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
/*     */   void _put(int paramInt, byte paramByte) {
/* 248 */     this.hb[paramInt] = paramByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getChar() {
/* 259 */     return Bits.getChar(this, ix(nextGetIndex(2)), this.bigEndian);
/*     */   }
/*     */   
/*     */   public char getChar(int paramInt) {
/* 263 */     return Bits.getChar(this, ix(checkIndex(paramInt, 2)), this.bigEndian);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putChar(char paramChar) {
/* 270 */     Bits.putChar(this, ix(nextPutIndex(2)), paramChar, this.bigEndian);
/* 271 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putChar(int paramInt, char paramChar) {
/* 279 */     Bits.putChar(this, ix(checkIndex(paramInt, 2)), paramChar, this.bigEndian);
/* 280 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharBuffer asCharBuffer() {
/* 287 */     int i = remaining() >> 1;
/* 288 */     int j = this.offset + position();
/* 289 */     return this.bigEndian ? new ByteBufferAsCharBufferB(this, -1, 0, i, i, j) : new ByteBufferAsCharBufferL(this, -1, 0, i, i, j);
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
/*     */   public short getShort() {
/* 310 */     return Bits.getShort(this, ix(nextGetIndex(2)), this.bigEndian);
/*     */   }
/*     */   
/*     */   public short getShort(int paramInt) {
/* 314 */     return Bits.getShort(this, ix(checkIndex(paramInt, 2)), this.bigEndian);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putShort(short paramShort) {
/* 321 */     Bits.putShort(this, ix(nextPutIndex(2)), paramShort, this.bigEndian);
/* 322 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putShort(int paramInt, short paramShort) {
/* 330 */     Bits.putShort(this, ix(checkIndex(paramInt, 2)), paramShort, this.bigEndian);
/* 331 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortBuffer asShortBuffer() {
/* 338 */     int i = remaining() >> 1;
/* 339 */     int j = this.offset + position();
/* 340 */     return this.bigEndian ? new ByteBufferAsShortBufferB(this, -1, 0, i, i, j) : new ByteBufferAsShortBufferL(this, -1, 0, i, i, j);
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
/*     */   public int getInt() {
/* 361 */     return Bits.getInt(this, ix(nextGetIndex(4)), this.bigEndian);
/*     */   }
/*     */   
/*     */   public int getInt(int paramInt) {
/* 365 */     return Bits.getInt(this, ix(checkIndex(paramInt, 4)), this.bigEndian);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putInt(int paramInt) {
/* 372 */     Bits.putInt(this, ix(nextPutIndex(4)), paramInt, this.bigEndian);
/* 373 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putInt(int paramInt1, int paramInt2) {
/* 381 */     Bits.putInt(this, ix(checkIndex(paramInt1, 4)), paramInt2, this.bigEndian);
/* 382 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntBuffer asIntBuffer() {
/* 389 */     int i = remaining() >> 2;
/* 390 */     int j = this.offset + position();
/* 391 */     return this.bigEndian ? new ByteBufferAsIntBufferB(this, -1, 0, i, i, j) : new ByteBufferAsIntBufferL(this, -1, 0, i, i, j);
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
/*     */   public long getLong() {
/* 412 */     return Bits.getLong(this, ix(nextGetIndex(8)), this.bigEndian);
/*     */   }
/*     */   
/*     */   public long getLong(int paramInt) {
/* 416 */     return Bits.getLong(this, ix(checkIndex(paramInt, 8)), this.bigEndian);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putLong(long paramLong) {
/* 423 */     Bits.putLong(this, ix(nextPutIndex(8)), paramLong, this.bigEndian);
/* 424 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putLong(int paramInt, long paramLong) {
/* 432 */     Bits.putLong(this, ix(checkIndex(paramInt, 8)), paramLong, this.bigEndian);
/* 433 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongBuffer asLongBuffer() {
/* 440 */     int i = remaining() >> 3;
/* 441 */     int j = this.offset + position();
/* 442 */     return this.bigEndian ? new ByteBufferAsLongBufferB(this, -1, 0, i, i, j) : new ByteBufferAsLongBufferL(this, -1, 0, i, i, j);
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
/*     */   public float getFloat() {
/* 463 */     return Bits.getFloat(this, ix(nextGetIndex(4)), this.bigEndian);
/*     */   }
/*     */   
/*     */   public float getFloat(int paramInt) {
/* 467 */     return Bits.getFloat(this, ix(checkIndex(paramInt, 4)), this.bigEndian);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putFloat(float paramFloat) {
/* 474 */     Bits.putFloat(this, ix(nextPutIndex(4)), paramFloat, this.bigEndian);
/* 475 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putFloat(int paramInt, float paramFloat) {
/* 483 */     Bits.putFloat(this, ix(checkIndex(paramInt, 4)), paramFloat, this.bigEndian);
/* 484 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatBuffer asFloatBuffer() {
/* 491 */     int i = remaining() >> 2;
/* 492 */     int j = this.offset + position();
/* 493 */     return this.bigEndian ? new ByteBufferAsFloatBufferB(this, -1, 0, i, i, j) : new ByteBufferAsFloatBufferL(this, -1, 0, i, i, j);
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
/*     */   public double getDouble() {
/* 514 */     return Bits.getDouble(this, ix(nextGetIndex(8)), this.bigEndian);
/*     */   }
/*     */   
/*     */   public double getDouble(int paramInt) {
/* 518 */     return Bits.getDouble(this, ix(checkIndex(paramInt, 8)), this.bigEndian);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putDouble(double paramDouble) {
/* 525 */     Bits.putDouble(this, ix(nextPutIndex(8)), paramDouble, this.bigEndian);
/* 526 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer putDouble(int paramInt, double paramDouble) {
/* 534 */     Bits.putDouble(this, ix(checkIndex(paramInt, 8)), paramDouble, this.bigEndian);
/* 535 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleBuffer asDoubleBuffer() {
/* 542 */     int i = remaining() >> 3;
/* 543 */     int j = this.offset + position();
/* 544 */     return this.bigEndian ? new ByteBufferAsDoubleBufferB(this, -1, 0, i, i, j) : new ByteBufferAsDoubleBufferL(this, -1, 0, i, i, j);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/HeapByteBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */