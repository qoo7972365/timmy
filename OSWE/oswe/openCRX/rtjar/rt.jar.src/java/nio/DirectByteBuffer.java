/*      */ package java.nio;
/*      */ 
/*      */ import java.io.FileDescriptor;
/*      */ import sun.misc.Cleaner;
/*      */ import sun.misc.Unsafe;
/*      */ import sun.misc.VM;
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
/*      */ class DirectByteBuffer
/*      */   extends MappedByteBuffer
/*      */   implements DirectBuffer
/*      */ {
/*   49 */   protected static final Unsafe unsafe = Bits.unsafe();
/*      */ 
/*      */   
/*   52 */   private static final long arrayBaseOffset = unsafe.arrayBaseOffset(byte[].class);
/*      */ 
/*      */   
/*   55 */   protected static final boolean unaligned = Bits.unaligned();
/*      */ 
/*      */ 
/*      */   
/*      */   private final Object att;
/*      */ 
/*      */   
/*      */   private final Cleaner cleaner;
/*      */ 
/*      */ 
/*      */   
/*      */   public Object attachment() {
/*   67 */     return this.att;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Deallocator
/*      */     implements Runnable
/*      */   {
/*   76 */     private static Unsafe unsafe = Unsafe.getUnsafe();
/*      */     
/*      */     private long address;
/*      */     private long size;
/*      */     private int capacity;
/*      */     
/*      */     private Deallocator(long param1Long1, long param1Long2, int param1Int) {
/*   83 */       assert param1Long1 != 0L;
/*   84 */       this.address = param1Long1;
/*   85 */       this.size = param1Long2;
/*   86 */       this.capacity = param1Int;
/*      */     }
/*      */     
/*      */     public void run() {
/*   90 */       if (this.address == 0L) {
/*      */         return;
/*      */       }
/*      */       
/*   94 */       unsafe.freeMemory(this.address);
/*   95 */       this.address = 0L;
/*   96 */       Bits.unreserveMemory(this.size, this.capacity);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Cleaner cleaner() {
/*  103 */     return this.cleaner;
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
/*      */   DirectByteBuffer(int paramInt) {
/*  119 */     super(-1, 0, paramInt, paramInt);
/*  120 */     boolean bool = VM.isDirectMemoryPageAligned();
/*  121 */     int i = Bits.pageSize();
/*  122 */     long l1 = Math.max(1L, paramInt + (bool ? i : 0L));
/*  123 */     Bits.reserveMemory(l1, paramInt);
/*      */     
/*  125 */     long l2 = 0L;
/*      */     try {
/*  127 */       l2 = unsafe.allocateMemory(l1);
/*  128 */     } catch (OutOfMemoryError outOfMemoryError) {
/*  129 */       Bits.unreserveMemory(l1, paramInt);
/*  130 */       throw outOfMemoryError;
/*      */     } 
/*  132 */     unsafe.setMemory(l2, l1, (byte)0);
/*  133 */     if (bool && l2 % i != 0L) {
/*      */       
/*  135 */       this.address = l2 + i - (l2 & (i - 1));
/*      */     } else {
/*  137 */       this.address = l2;
/*      */     } 
/*  139 */     this.cleaner = Cleaner.create(this, new Deallocator(l2, l1, paramInt));
/*  140 */     this.att = null;
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
/*      */   DirectByteBuffer(long paramLong, int paramInt, Object paramObject) {
/*  152 */     super(-1, 0, paramInt, paramInt);
/*  153 */     this.address = paramLong;
/*  154 */     this.cleaner = null;
/*  155 */     this.att = paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DirectByteBuffer(long paramLong, int paramInt) {
/*  162 */     super(-1, 0, paramInt, paramInt);
/*  163 */     this.address = paramLong;
/*  164 */     this.cleaner = null;
/*  165 */     this.att = null;
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
/*      */   protected DirectByteBuffer(int paramInt, long paramLong, FileDescriptor paramFileDescriptor, Runnable paramRunnable) {
/*  177 */     super(-1, 0, paramInt, paramInt, paramFileDescriptor);
/*  178 */     this.address = paramLong;
/*  179 */     this.cleaner = Cleaner.create(this, paramRunnable);
/*  180 */     this.att = null;
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
/*      */   DirectByteBuffer(DirectBuffer paramDirectBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  195 */     super(paramInt1, paramInt2, paramInt3, paramInt4);
/*  196 */     this.address = paramDirectBuffer.address() + paramInt5;
/*      */     
/*  198 */     this.cleaner = null;
/*      */     
/*  200 */     this.att = paramDirectBuffer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer slice() {
/*  207 */     int i = position();
/*  208 */     int j = limit();
/*  209 */     assert i <= j;
/*  210 */     boolean bool = (i <= j) ? (j - i) : false;
/*  211 */     int k = i << 0;
/*  212 */     assert k >= 0;
/*  213 */     return new DirectByteBuffer(this, -1, 0, bool, bool, k);
/*      */   }
/*      */   
/*      */   public ByteBuffer duplicate() {
/*  217 */     return new DirectByteBuffer(this, 
/*  218 */         markValue(), 
/*  219 */         position(), 
/*  220 */         limit(), 
/*  221 */         capacity(), 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer asReadOnlyBuffer() {
/*  227 */     return new DirectByteBufferR(this, 
/*  228 */         markValue(), 
/*  229 */         position(), 
/*  230 */         limit(), 
/*  231 */         capacity(), 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long address() {
/*  241 */     return this.address;
/*      */   }
/*      */   
/*      */   private long ix(int paramInt) {
/*  245 */     return this.address + (paramInt << 0L);
/*      */   }
/*      */   
/*      */   public byte get() {
/*  249 */     return unsafe.getByte(ix(nextGetIndex()));
/*      */   }
/*      */   
/*      */   public byte get(int paramInt) {
/*  253 */     return unsafe.getByte(ix(checkIndex(paramInt)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer get(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  264 */     if (paramInt2 << 0L > 6L) {
/*  265 */       checkBounds(paramInt1, paramInt2, paramArrayOfbyte.length);
/*  266 */       int i = position();
/*  267 */       int j = limit();
/*  268 */       assert i <= j;
/*  269 */       byte b = (i <= j) ? (j - i) : 0;
/*  270 */       if (paramInt2 > b) {
/*  271 */         throw new BufferUnderflowException();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  280 */       Bits.copyToArray(ix(i), paramArrayOfbyte, arrayBaseOffset, paramInt1 << 0L, paramInt2 << 0L);
/*      */ 
/*      */       
/*  283 */       position(i + paramInt2);
/*      */     } else {
/*  285 */       super.get(paramArrayOfbyte, paramInt1, paramInt2);
/*      */     } 
/*  287 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer put(byte paramByte) {
/*  297 */     unsafe.putByte(ix(nextPutIndex()), paramByte);
/*  298 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer put(int paramInt, byte paramByte) {
/*  306 */     unsafe.putByte(ix(checkIndex(paramInt)), paramByte);
/*  307 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer put(ByteBuffer paramByteBuffer) {
/*  315 */     if (paramByteBuffer instanceof DirectByteBuffer) {
/*  316 */       if (paramByteBuffer == this)
/*  317 */         throw new IllegalArgumentException(); 
/*  318 */       DirectByteBuffer directByteBuffer = (DirectByteBuffer)paramByteBuffer;
/*      */       
/*  320 */       int i = directByteBuffer.position();
/*  321 */       int j = directByteBuffer.limit();
/*  322 */       assert i <= j;
/*  323 */       byte b1 = (i <= j) ? (j - i) : 0;
/*      */       
/*  325 */       int k = position();
/*  326 */       int m = limit();
/*  327 */       assert k <= m;
/*  328 */       byte b2 = (k <= m) ? (m - k) : 0;
/*      */       
/*  330 */       if (b1 > b2)
/*  331 */         throw new BufferOverflowException(); 
/*  332 */       unsafe.copyMemory(directByteBuffer.ix(i), ix(k), b1 << 0L);
/*  333 */       directByteBuffer.position(i + b1);
/*  334 */       position(k + b1);
/*  335 */     } else if (paramByteBuffer.hb != null) {
/*      */       
/*  337 */       int i = paramByteBuffer.position();
/*  338 */       int j = paramByteBuffer.limit();
/*  339 */       assert i <= j;
/*  340 */       byte b = (i <= j) ? (j - i) : 0;
/*      */       
/*  342 */       put(paramByteBuffer.hb, paramByteBuffer.offset + i, b);
/*  343 */       paramByteBuffer.position(i + b);
/*      */     } else {
/*      */       
/*  346 */       super.put(paramByteBuffer);
/*      */     } 
/*  348 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer put(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  356 */     if (paramInt2 << 0L > 6L) {
/*  357 */       checkBounds(paramInt1, paramInt2, paramArrayOfbyte.length);
/*  358 */       int i = position();
/*  359 */       int j = limit();
/*  360 */       assert i <= j;
/*  361 */       byte b = (i <= j) ? (j - i) : 0;
/*  362 */       if (paramInt2 > b) {
/*  363 */         throw new BufferOverflowException();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  373 */       Bits.copyFromArray(paramArrayOfbyte, arrayBaseOffset, paramInt1 << 0L, 
/*      */           
/*  375 */           ix(i), paramInt2 << 0L);
/*      */       
/*  377 */       position(i + paramInt2);
/*      */     } else {
/*  379 */       super.put(paramArrayOfbyte, paramInt1, paramInt2);
/*      */     } 
/*  381 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer compact() {
/*  389 */     int i = position();
/*  390 */     int j = limit();
/*  391 */     assert i <= j;
/*  392 */     boolean bool = (i <= j) ? (j - i) : false;
/*      */     
/*  394 */     unsafe.copyMemory(ix(i), ix(0), bool << 0L);
/*  395 */     position(bool);
/*  396 */     limit(capacity());
/*  397 */     discardMark();
/*  398 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDirect() {
/*  405 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isReadOnly() {
/*  409 */     return false;
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
/*      */   void _put(int paramInt, byte paramByte) {
/*  481 */     unsafe.putByte(this.address + paramInt, paramByte);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private char getChar(long paramLong) {
/*  491 */     if (unaligned) {
/*  492 */       char c = unsafe.getChar(paramLong);
/*  493 */       return this.nativeByteOrder ? c : Bits.swap(c);
/*      */     } 
/*  495 */     return Bits.getChar(paramLong, this.bigEndian);
/*      */   }
/*      */   
/*      */   public char getChar() {
/*  499 */     return getChar(ix(nextGetIndex(2)));
/*      */   }
/*      */   
/*      */   public char getChar(int paramInt) {
/*  503 */     return getChar(ix(checkIndex(paramInt, 2)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuffer putChar(long paramLong, char paramChar) {
/*  510 */     if (unaligned) {
/*  511 */       char c = paramChar;
/*  512 */       unsafe.putChar(paramLong, this.nativeByteOrder ? c : Bits.swap(c));
/*      */     } else {
/*  514 */       Bits.putChar(paramLong, paramChar, this.bigEndian);
/*      */     } 
/*  516 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putChar(char paramChar) {
/*  524 */     putChar(ix(nextPutIndex(2)), paramChar);
/*  525 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putChar(int paramInt, char paramChar) {
/*  533 */     putChar(ix(checkIndex(paramInt, 2)), paramChar);
/*  534 */     return this;
/*      */   }
/*      */ 
/*      */ 
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
/*  548 */       return this.bigEndian ? new ByteBufferAsCharBufferB(this, -1, 0, k, k, i) : new ByteBufferAsCharBufferL(this, -1, 0, k, k, i);
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
/*  562 */     return this.nativeByteOrder ? new DirectCharBufferU(this, -1, 0, k, k, i) : new DirectCharBufferS(this, -1, 0, k, k, i);
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
/*      */   private short getShort(long paramLong) {
/*  582 */     if (unaligned) {
/*  583 */       short s = unsafe.getShort(paramLong);
/*  584 */       return this.nativeByteOrder ? s : Bits.swap(s);
/*      */     } 
/*  586 */     return Bits.getShort(paramLong, this.bigEndian);
/*      */   }
/*      */   
/*      */   public short getShort() {
/*  590 */     return getShort(ix(nextGetIndex(2)));
/*      */   }
/*      */   
/*      */   public short getShort(int paramInt) {
/*  594 */     return getShort(ix(checkIndex(paramInt, 2)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuffer putShort(long paramLong, short paramShort) {
/*  601 */     if (unaligned) {
/*  602 */       short s = paramShort;
/*  603 */       unsafe.putShort(paramLong, this.nativeByteOrder ? s : Bits.swap(s));
/*      */     } else {
/*  605 */       Bits.putShort(paramLong, paramShort, this.bigEndian);
/*      */     } 
/*  607 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putShort(short paramShort) {
/*  615 */     putShort(ix(nextPutIndex(2)), paramShort);
/*  616 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putShort(int paramInt, short paramShort) {
/*  624 */     putShort(ix(checkIndex(paramInt, 2)), paramShort);
/*  625 */     return this;
/*      */   }
/*      */ 
/*      */ 
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
/*  639 */       return this.bigEndian ? new ByteBufferAsShortBufferB(this, -1, 0, k, k, i) : new ByteBufferAsShortBufferL(this, -1, 0, k, k, i);
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
/*  653 */     return this.nativeByteOrder ? new DirectShortBufferU(this, -1, 0, k, k, i) : new DirectShortBufferS(this, -1, 0, k, k, i);
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
/*      */   private int getInt(long paramLong) {
/*  673 */     if (unaligned) {
/*  674 */       int i = unsafe.getInt(paramLong);
/*  675 */       return this.nativeByteOrder ? i : Bits.swap(i);
/*      */     } 
/*  677 */     return Bits.getInt(paramLong, this.bigEndian);
/*      */   }
/*      */   
/*      */   public int getInt() {
/*  681 */     return getInt(ix(nextGetIndex(4)));
/*      */   }
/*      */   
/*      */   public int getInt(int paramInt) {
/*  685 */     return getInt(ix(checkIndex(paramInt, 4)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuffer putInt(long paramLong, int paramInt) {
/*  692 */     if (unaligned) {
/*  693 */       int i = paramInt;
/*  694 */       unsafe.putInt(paramLong, this.nativeByteOrder ? i : Bits.swap(i));
/*      */     } else {
/*  696 */       Bits.putInt(paramLong, paramInt, this.bigEndian);
/*      */     } 
/*  698 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putInt(int paramInt) {
/*  706 */     putInt(ix(nextPutIndex(4)), paramInt);
/*  707 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putInt(int paramInt1, int paramInt2) {
/*  715 */     putInt(ix(checkIndex(paramInt1, 4)), paramInt2);
/*  716 */     return this;
/*      */   }
/*      */ 
/*      */ 
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
/*  730 */       return this.bigEndian ? new ByteBufferAsIntBufferB(this, -1, 0, k, k, i) : new ByteBufferAsIntBufferL(this, -1, 0, k, k, i);
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
/*  744 */     return this.nativeByteOrder ? new DirectIntBufferU(this, -1, 0, k, k, i) : new DirectIntBufferS(this, -1, 0, k, k, i);
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
/*      */   private long getLong(long paramLong) {
/*  764 */     if (unaligned) {
/*  765 */       long l = unsafe.getLong(paramLong);
/*  766 */       return this.nativeByteOrder ? l : Bits.swap(l);
/*      */     } 
/*  768 */     return Bits.getLong(paramLong, this.bigEndian);
/*      */   }
/*      */   
/*      */   public long getLong() {
/*  772 */     return getLong(ix(nextGetIndex(8)));
/*      */   }
/*      */   
/*      */   public long getLong(int paramInt) {
/*  776 */     return getLong(ix(checkIndex(paramInt, 8)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuffer putLong(long paramLong1, long paramLong2) {
/*  783 */     if (unaligned) {
/*  784 */       long l = paramLong2;
/*  785 */       unsafe.putLong(paramLong1, this.nativeByteOrder ? l : Bits.swap(l));
/*      */     } else {
/*  787 */       Bits.putLong(paramLong1, paramLong2, this.bigEndian);
/*      */     } 
/*  789 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putLong(long paramLong) {
/*  797 */     putLong(ix(nextPutIndex(8)), paramLong);
/*  798 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putLong(int paramInt, long paramLong) {
/*  806 */     putLong(ix(checkIndex(paramInt, 8)), paramLong);
/*  807 */     return this;
/*      */   }
/*      */ 
/*      */ 
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
/*  821 */       return this.bigEndian ? new ByteBufferAsLongBufferB(this, -1, 0, k, k, i) : new ByteBufferAsLongBufferL(this, -1, 0, k, k, i);
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
/*  835 */     return this.nativeByteOrder ? new DirectLongBufferU(this, -1, 0, k, k, i) : new DirectLongBufferS(this, -1, 0, k, k, i);
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
/*      */   private float getFloat(long paramLong) {
/*  855 */     if (unaligned) {
/*  856 */       int i = unsafe.getInt(paramLong);
/*  857 */       return Float.intBitsToFloat(this.nativeByteOrder ? i : Bits.swap(i));
/*      */     } 
/*  859 */     return Bits.getFloat(paramLong, this.bigEndian);
/*      */   }
/*      */   
/*      */   public float getFloat() {
/*  863 */     return getFloat(ix(nextGetIndex(4)));
/*      */   }
/*      */   
/*      */   public float getFloat(int paramInt) {
/*  867 */     return getFloat(ix(checkIndex(paramInt, 4)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuffer putFloat(long paramLong, float paramFloat) {
/*  874 */     if (unaligned) {
/*  875 */       int i = Float.floatToRawIntBits(paramFloat);
/*  876 */       unsafe.putInt(paramLong, this.nativeByteOrder ? i : Bits.swap(i));
/*      */     } else {
/*  878 */       Bits.putFloat(paramLong, paramFloat, this.bigEndian);
/*      */     } 
/*  880 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putFloat(float paramFloat) {
/*  888 */     putFloat(ix(nextPutIndex(4)), paramFloat);
/*  889 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putFloat(int paramInt, float paramFloat) {
/*  897 */     putFloat(ix(checkIndex(paramInt, 4)), paramFloat);
/*  898 */     return this;
/*      */   }
/*      */ 
/*      */ 
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
/*  912 */       return this.bigEndian ? new ByteBufferAsFloatBufferB(this, -1, 0, k, k, i) : new ByteBufferAsFloatBufferL(this, -1, 0, k, k, i);
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
/*  926 */     return this.nativeByteOrder ? new DirectFloatBufferU(this, -1, 0, k, k, i) : new DirectFloatBufferS(this, -1, 0, k, k, i);
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
/*      */   private double getDouble(long paramLong) {
/*  946 */     if (unaligned) {
/*  947 */       long l = unsafe.getLong(paramLong);
/*  948 */       return Double.longBitsToDouble(this.nativeByteOrder ? l : Bits.swap(l));
/*      */     } 
/*  950 */     return Bits.getDouble(paramLong, this.bigEndian);
/*      */   }
/*      */   
/*      */   public double getDouble() {
/*  954 */     return getDouble(ix(nextGetIndex(8)));
/*      */   }
/*      */   
/*      */   public double getDouble(int paramInt) {
/*  958 */     return getDouble(ix(checkIndex(paramInt, 8)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuffer putDouble(long paramLong, double paramDouble) {
/*  965 */     if (unaligned) {
/*  966 */       long l = Double.doubleToRawLongBits(paramDouble);
/*  967 */       unsafe.putLong(paramLong, this.nativeByteOrder ? l : Bits.swap(l));
/*      */     } else {
/*  969 */       Bits.putDouble(paramLong, paramDouble, this.bigEndian);
/*      */     } 
/*  971 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putDouble(double paramDouble) {
/*  979 */     putDouble(ix(nextPutIndex(8)), paramDouble);
/*  980 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer putDouble(int paramInt, double paramDouble) {
/*  988 */     putDouble(ix(checkIndex(paramInt, 8)), paramDouble);
/*  989 */     return this;
/*      */   }
/*      */ 
/*      */ 
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
/* 1003 */       return this.bigEndian ? new ByteBufferAsDoubleBufferB(this, -1, 0, k, k, i) : new ByteBufferAsDoubleBufferL(this, -1, 0, k, k, i);
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
/* 1017 */     return this.nativeByteOrder ? new DirectDoubleBufferU(this, -1, 0, k, k, i) : new DirectDoubleBufferS(this, -1, 0, k, k, i);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/DirectByteBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */