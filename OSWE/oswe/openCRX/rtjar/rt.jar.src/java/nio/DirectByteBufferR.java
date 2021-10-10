/*      */ package java.nio;
/*      */ 
/*      */ import java.io.FileDescriptor;
/*      */ import sun.nio.ch.DirectBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class DirectByteBufferR
/*      */   extends DirectByteBuffer
/*      */   implements DirectBuffer
/*      */ {
/*      */   DirectByteBufferR(int paramInt) {
/*  142 */     super(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DirectByteBufferR(int paramInt, long paramLong, FileDescriptor paramFileDescriptor, Runnable paramRunnable) {
/*  182 */     super(paramInt, paramLong, paramFileDescriptor, paramRunnable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   DirectByteBufferR(DirectBuffer paramDirectBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  202 */     super(paramDirectBuffer, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer slice() {
/*  207 */     int i = position();
/*  208 */     int j = limit();
/*  209 */     assert i <= j;
/*  210 */     boolean bool = (i <= j) ? (j - i) : false;
/*  211 */     int k = i << 0;
/*  212 */     assert k >= 0;
/*  213 */     return new DirectByteBufferR(this, -1, 0, bool, bool, k);
/*      */   }
/*      */   
/*      */   public ByteBuffer duplicate() {
/*  217 */     return new DirectByteBufferR(this, 
/*  218 */         markValue(), 
/*  219 */         position(), 
/*  220 */         limit(), 
/*  221 */         capacity(), 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer asReadOnlyBuffer() {
/*  234 */     return duplicate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer put(byte paramByte) {
/*  300 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer put(int paramInt, byte paramByte) {
/*  309 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer put(ByteBuffer paramByteBuffer) {
/*  350 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer put(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  383 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer compact() {
/*  400 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDirect() {
/*  405 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isReadOnly() {
/*  409 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   byte _get(int paramInt) {
/*  476 */     return unsafe.getByte(this.address + paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void _put(int paramInt, byte paramByte) {
/*  483 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuffer putChar(long paramLong, char paramChar) {
/*  518 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putChar(char paramChar) {
/*  527 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putChar(int paramInt, char paramChar) {
/*  536 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */   
/*      */   public CharBuffer asCharBuffer() {
/*  541 */     int i = position();
/*  542 */     int j = limit();
/*  543 */     assert i <= j;
/*  544 */     boolean bool = (i <= j) ? (j - i) : false;
/*      */     
/*  546 */     int k = bool >> 1;
/*  547 */     if (!unaligned && (this.address + i) % 2L != 0L) {
/*  548 */       return this.bigEndian ? new ByteBufferAsCharBufferRB(this, -1, 0, k, k, i) : new ByteBufferAsCharBufferRL(this, -1, 0, k, k, i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  562 */     return this.nativeByteOrder ? new DirectCharBufferRU(this, -1, 0, k, k, i) : new DirectCharBufferRS(this, -1, 0, k, k, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuffer putShort(long paramLong, short paramShort) {
/*  609 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putShort(short paramShort) {
/*  618 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putShort(int paramInt, short paramShort) {
/*  627 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ShortBuffer asShortBuffer() {
/*  632 */     int i = position();
/*  633 */     int j = limit();
/*  634 */     assert i <= j;
/*  635 */     boolean bool = (i <= j) ? (j - i) : false;
/*      */     
/*  637 */     int k = bool >> 1;
/*  638 */     if (!unaligned && (this.address + i) % 2L != 0L) {
/*  639 */       return this.bigEndian ? new ByteBufferAsShortBufferRB(this, -1, 0, k, k, i) : new ByteBufferAsShortBufferRL(this, -1, 0, k, k, i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  653 */     return this.nativeByteOrder ? new DirectShortBufferRU(this, -1, 0, k, k, i) : new DirectShortBufferRS(this, -1, 0, k, k, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuffer putInt(long paramLong, int paramInt) {
/*  700 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putInt(int paramInt) {
/*  709 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putInt(int paramInt1, int paramInt2) {
/*  718 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */   
/*      */   public IntBuffer asIntBuffer() {
/*  723 */     int i = position();
/*  724 */     int j = limit();
/*  725 */     assert i <= j;
/*  726 */     boolean bool = (i <= j) ? (j - i) : false;
/*      */     
/*  728 */     int k = bool >> 2;
/*  729 */     if (!unaligned && (this.address + i) % 4L != 0L) {
/*  730 */       return this.bigEndian ? new ByteBufferAsIntBufferRB(this, -1, 0, k, k, i) : new ByteBufferAsIntBufferRL(this, -1, 0, k, k, i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  744 */     return this.nativeByteOrder ? new DirectIntBufferRU(this, -1, 0, k, k, i) : new DirectIntBufferRS(this, -1, 0, k, k, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuffer putLong(long paramLong1, long paramLong2) {
/*  791 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putLong(long paramLong) {
/*  800 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putLong(int paramInt, long paramLong) {
/*  809 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */   
/*      */   public LongBuffer asLongBuffer() {
/*  814 */     int i = position();
/*  815 */     int j = limit();
/*  816 */     assert i <= j;
/*  817 */     boolean bool = (i <= j) ? (j - i) : false;
/*      */     
/*  819 */     int k = bool >> 3;
/*  820 */     if (!unaligned && (this.address + i) % 8L != 0L) {
/*  821 */       return this.bigEndian ? new ByteBufferAsLongBufferRB(this, -1, 0, k, k, i) : new ByteBufferAsLongBufferRL(this, -1, 0, k, k, i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  835 */     return this.nativeByteOrder ? new DirectLongBufferRU(this, -1, 0, k, k, i) : new DirectLongBufferRS(this, -1, 0, k, k, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuffer putFloat(long paramLong, float paramFloat) {
/*  882 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putFloat(float paramFloat) {
/*  891 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putFloat(int paramInt, float paramFloat) {
/*  900 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */   
/*      */   public FloatBuffer asFloatBuffer() {
/*  905 */     int i = position();
/*  906 */     int j = limit();
/*  907 */     assert i <= j;
/*  908 */     boolean bool = (i <= j) ? (j - i) : false;
/*      */     
/*  910 */     int k = bool >> 2;
/*  911 */     if (!unaligned && (this.address + i) % 4L != 0L) {
/*  912 */       return this.bigEndian ? new ByteBufferAsFloatBufferRB(this, -1, 0, k, k, i) : new ByteBufferAsFloatBufferRL(this, -1, 0, k, k, i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  926 */     return this.nativeByteOrder ? new DirectFloatBufferRU(this, -1, 0, k, k, i) : new DirectFloatBufferRS(this, -1, 0, k, k, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuffer putDouble(long paramLong, double paramDouble) {
/*  973 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putDouble(double paramDouble) {
/*  982 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putDouble(int paramInt, double paramDouble) {
/*  991 */     throw new ReadOnlyBufferException();
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleBuffer asDoubleBuffer() {
/*  996 */     int i = position();
/*  997 */     int j = limit();
/*  998 */     assert i <= j;
/*  999 */     boolean bool = (i <= j) ? (j - i) : false;
/*      */     
/* 1001 */     int k = bool >> 3;
/* 1002 */     if (!unaligned && (this.address + i) % 8L != 0L) {
/* 1003 */       return this.bigEndian ? new ByteBufferAsDoubleBufferRB(this, -1, 0, k, k, i) : new ByteBufferAsDoubleBufferRL(this, -1, 0, k, k, i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1017 */     return this.nativeByteOrder ? new DirectDoubleBufferRU(this, -1, 0, k, k, i) : new DirectDoubleBufferRS(this, -1, 0, k, k, i);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/DirectByteBufferR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */