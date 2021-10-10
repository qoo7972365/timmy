/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.LongBuffer;
/*      */ import java.util.stream.IntStream;
/*      */ import java.util.stream.StreamSupport;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BitSet
/*      */   implements Cloneable, Serializable
/*      */ {
/*      */   private static final int ADDRESS_BITS_PER_WORD = 6;
/*      */   private static final int BITS_PER_WORD = 64;
/*      */   private static final int BIT_INDEX_MASK = 63;
/*      */   private static final long WORD_MASK = -1L;
/*   85 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("bits", long[].class) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long[] words;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   97 */   private transient int wordsInUse = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean sizeIsSticky = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 7997698588986878753L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int wordIndex(int paramInt) {
/*  112 */     return paramInt >> 6;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkInvariants() {
/*  119 */     assert this.wordsInUse == 0 || this.words[this.wordsInUse - 1] != 0L;
/*  120 */     assert this.wordsInUse >= 0 && this.wordsInUse <= this.words.length;
/*  121 */     assert this.wordsInUse == this.words.length || this.words[this.wordsInUse] == 0L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void recalculateWordsInUse() {
/*      */     int i;
/*  132 */     for (i = this.wordsInUse - 1; i >= 0 && 
/*  133 */       this.words[i] == 0L; i--);
/*      */ 
/*      */     
/*  136 */     this.wordsInUse = i + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BitSet() {
/*  143 */     initWords(64);
/*  144 */     this.sizeIsSticky = false;
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
/*      */   public BitSet(int paramInt) {
/*  158 */     if (paramInt < 0) {
/*  159 */       throw new NegativeArraySizeException("nbits < 0: " + paramInt);
/*      */     }
/*  161 */     initWords(paramInt);
/*  162 */     this.sizeIsSticky = true;
/*      */   }
/*      */   
/*      */   private void initWords(int paramInt) {
/*  166 */     this.words = new long[wordIndex(paramInt - 1) + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BitSet(long[] paramArrayOflong) {
/*  174 */     this.words = paramArrayOflong;
/*  175 */     this.wordsInUse = paramArrayOflong.length;
/*  176 */     checkInvariants();
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
/*      */   public static BitSet valueOf(long[] paramArrayOflong) {
/*      */     int i;
/*  197 */     for (i = paramArrayOflong.length; i > 0 && paramArrayOflong[i - 1] == 0L; i--);
/*      */     
/*  199 */     return new BitSet(Arrays.copyOf(paramArrayOflong, i));
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
/*      */   public static BitSet valueOf(LongBuffer paramLongBuffer) {
/*  221 */     paramLongBuffer = paramLongBuffer.slice();
/*      */     int i;
/*  223 */     for (i = paramLongBuffer.remaining(); i > 0 && paramLongBuffer.get(i - 1) == 0L; i--);
/*      */     
/*  225 */     long[] arrayOfLong = new long[i];
/*  226 */     paramLongBuffer.get(arrayOfLong);
/*  227 */     return new BitSet(arrayOfLong);
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
/*      */   public static BitSet valueOf(byte[] paramArrayOfbyte) {
/*  247 */     return valueOf(ByteBuffer.wrap(paramArrayOfbyte));
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
/*      */   public static BitSet valueOf(ByteBuffer paramByteBuffer) {
/*  269 */     paramByteBuffer = paramByteBuffer.slice().order(ByteOrder.LITTLE_ENDIAN);
/*      */     int i;
/*  271 */     for (i = paramByteBuffer.remaining(); i > 0 && paramByteBuffer.get(i - 1) == 0; i--);
/*      */     
/*  273 */     long[] arrayOfLong = new long[(i + 7) / 8];
/*  274 */     paramByteBuffer.limit(i);
/*  275 */     byte b1 = 0;
/*  276 */     while (paramByteBuffer.remaining() >= 8)
/*  277 */       arrayOfLong[b1++] = paramByteBuffer.getLong();  int j; byte b2;
/*  278 */     for (j = paramByteBuffer.remaining(), b2 = 0; b2 < j; b2++)
/*  279 */       arrayOfLong[b1] = arrayOfLong[b1] | (paramByteBuffer.get() & 0xFFL) << 8 * b2; 
/*  280 */     return new BitSet(arrayOfLong);
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
/*      */   public byte[] toByteArray() {
/*  297 */     int i = this.wordsInUse;
/*  298 */     if (i == 0)
/*  299 */       return new byte[0]; 
/*  300 */     int j = 8 * (i - 1);
/*  301 */     for (long l1 = this.words[i - 1]; l1 != 0L; l1 >>>= 8L)
/*  302 */       j++; 
/*  303 */     byte[] arrayOfByte = new byte[j];
/*  304 */     ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte).order(ByteOrder.LITTLE_ENDIAN);
/*  305 */     for (byte b = 0; b < i - 1; b++)
/*  306 */       byteBuffer.putLong(this.words[b]);  long l2;
/*  307 */     for (l2 = this.words[i - 1]; l2 != 0L; l2 >>>= 8L)
/*  308 */       byteBuffer.put((byte)(int)(l2 & 0xFFL)); 
/*  309 */     return arrayOfByte;
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
/*      */   public long[] toLongArray() {
/*  326 */     return Arrays.copyOf(this.words, this.wordsInUse);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void ensureCapacity(int paramInt) {
/*  334 */     if (this.words.length < paramInt) {
/*      */       
/*  336 */       int i = Math.max(2 * this.words.length, paramInt);
/*  337 */       this.words = Arrays.copyOf(this.words, i);
/*  338 */       this.sizeIsSticky = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void expandTo(int paramInt) {
/*  350 */     int i = paramInt + 1;
/*  351 */     if (this.wordsInUse < i) {
/*  352 */       ensureCapacity(i);
/*  353 */       this.wordsInUse = i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkRange(int paramInt1, int paramInt2) {
/*  361 */     if (paramInt1 < 0)
/*  362 */       throw new IndexOutOfBoundsException("fromIndex < 0: " + paramInt1); 
/*  363 */     if (paramInt2 < 0)
/*  364 */       throw new IndexOutOfBoundsException("toIndex < 0: " + paramInt2); 
/*  365 */     if (paramInt1 > paramInt2) {
/*  366 */       throw new IndexOutOfBoundsException("fromIndex: " + paramInt1 + " > toIndex: " + paramInt2);
/*      */     }
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
/*      */   public void flip(int paramInt) {
/*  379 */     if (paramInt < 0) {
/*  380 */       throw new IndexOutOfBoundsException("bitIndex < 0: " + paramInt);
/*      */     }
/*  382 */     int i = wordIndex(paramInt);
/*  383 */     expandTo(i);
/*      */     
/*  385 */     this.words[i] = this.words[i] ^ 1L << paramInt;
/*      */     
/*  387 */     recalculateWordsInUse();
/*  388 */     checkInvariants();
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
/*      */   public void flip(int paramInt1, int paramInt2) {
/*  404 */     checkRange(paramInt1, paramInt2);
/*      */     
/*  406 */     if (paramInt1 == paramInt2) {
/*      */       return;
/*      */     }
/*  409 */     int i = wordIndex(paramInt1);
/*  410 */     int j = wordIndex(paramInt2 - 1);
/*  411 */     expandTo(j);
/*      */     
/*  413 */     long l1 = -1L << paramInt1;
/*  414 */     long l2 = -1L >>> -paramInt2;
/*  415 */     if (i == j) {
/*      */       
/*  417 */       this.words[i] = this.words[i] ^ l1 & l2;
/*      */     }
/*      */     else {
/*      */       
/*  421 */       this.words[i] = this.words[i] ^ l1;
/*      */ 
/*      */       
/*  424 */       for (int k = i + 1; k < j; k++) {
/*  425 */         this.words[k] = this.words[k] ^ 0xFFFFFFFFFFFFFFFFL;
/*      */       }
/*      */       
/*  428 */       this.words[j] = this.words[j] ^ l2;
/*      */     } 
/*      */     
/*  431 */     recalculateWordsInUse();
/*  432 */     checkInvariants();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set(int paramInt) {
/*  443 */     if (paramInt < 0) {
/*  444 */       throw new IndexOutOfBoundsException("bitIndex < 0: " + paramInt);
/*      */     }
/*  446 */     int i = wordIndex(paramInt);
/*  447 */     expandTo(i);
/*      */     
/*  449 */     this.words[i] = this.words[i] | 1L << paramInt;
/*      */     
/*  451 */     checkInvariants();
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
/*      */   public void set(int paramInt, boolean paramBoolean) {
/*  463 */     if (paramBoolean) {
/*  464 */       set(paramInt);
/*      */     } else {
/*  466 */       clear(paramInt);
/*      */     } 
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
/*      */   public void set(int paramInt1, int paramInt2) {
/*  481 */     checkRange(paramInt1, paramInt2);
/*      */     
/*  483 */     if (paramInt1 == paramInt2) {
/*      */       return;
/*      */     }
/*      */     
/*  487 */     int i = wordIndex(paramInt1);
/*  488 */     int j = wordIndex(paramInt2 - 1);
/*  489 */     expandTo(j);
/*      */     
/*  491 */     long l1 = -1L << paramInt1;
/*  492 */     long l2 = -1L >>> -paramInt2;
/*  493 */     if (i == j) {
/*      */       
/*  495 */       this.words[i] = this.words[i] | l1 & l2;
/*      */     }
/*      */     else {
/*      */       
/*  499 */       this.words[i] = this.words[i] | l1;
/*      */ 
/*      */       
/*  502 */       for (int k = i + 1; k < j; k++) {
/*  503 */         this.words[k] = -1L;
/*      */       }
/*      */       
/*  506 */       this.words[j] = this.words[j] | l2;
/*      */     } 
/*      */     
/*  509 */     checkInvariants();
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
/*      */   public void set(int paramInt1, int paramInt2, boolean paramBoolean) {
/*  525 */     if (paramBoolean) {
/*  526 */       set(paramInt1, paramInt2);
/*      */     } else {
/*  528 */       clear(paramInt1, paramInt2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear(int paramInt) {
/*  539 */     if (paramInt < 0) {
/*  540 */       throw new IndexOutOfBoundsException("bitIndex < 0: " + paramInt);
/*      */     }
/*  542 */     int i = wordIndex(paramInt);
/*  543 */     if (i >= this.wordsInUse) {
/*      */       return;
/*      */     }
/*  546 */     this.words[i] = this.words[i] & (1L << paramInt ^ 0xFFFFFFFFFFFFFFFFL);
/*      */     
/*  548 */     recalculateWordsInUse();
/*  549 */     checkInvariants();
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
/*      */   public void clear(int paramInt1, int paramInt2) {
/*  564 */     checkRange(paramInt1, paramInt2);
/*      */     
/*  566 */     if (paramInt1 == paramInt2) {
/*      */       return;
/*      */     }
/*  569 */     int i = wordIndex(paramInt1);
/*  570 */     if (i >= this.wordsInUse) {
/*      */       return;
/*      */     }
/*  573 */     int j = wordIndex(paramInt2 - 1);
/*  574 */     if (j >= this.wordsInUse) {
/*  575 */       paramInt2 = length();
/*  576 */       j = this.wordsInUse - 1;
/*      */     } 
/*      */     
/*  579 */     long l1 = -1L << paramInt1;
/*  580 */     long l2 = -1L >>> -paramInt2;
/*  581 */     if (i == j) {
/*      */       
/*  583 */       this.words[i] = this.words[i] & (l1 & l2 ^ 0xFFFFFFFFFFFFFFFFL);
/*      */     }
/*      */     else {
/*      */       
/*  587 */       this.words[i] = this.words[i] & (l1 ^ 0xFFFFFFFFFFFFFFFFL);
/*      */ 
/*      */       
/*  590 */       for (int k = i + 1; k < j; k++) {
/*  591 */         this.words[k] = 0L;
/*      */       }
/*      */       
/*  594 */       this.words[j] = this.words[j] & (l2 ^ 0xFFFFFFFFFFFFFFFFL);
/*      */     } 
/*      */     
/*  597 */     recalculateWordsInUse();
/*  598 */     checkInvariants();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  607 */     while (this.wordsInUse > 0) {
/*  608 */       this.words[--this.wordsInUse] = 0L;
/*      */     }
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
/*      */   public boolean get(int paramInt) {
/*  622 */     if (paramInt < 0) {
/*  623 */       throw new IndexOutOfBoundsException("bitIndex < 0: " + paramInt);
/*      */     }
/*  625 */     checkInvariants();
/*      */     
/*  627 */     int i = wordIndex(paramInt);
/*  628 */     return (i < this.wordsInUse && (this.words[i] & 1L << paramInt) != 0L);
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
/*      */   public BitSet get(int paramInt1, int paramInt2) {
/*  645 */     checkRange(paramInt1, paramInt2);
/*      */     
/*  647 */     checkInvariants();
/*      */     
/*  649 */     int i = length();
/*      */ 
/*      */     
/*  652 */     if (i <= paramInt1 || paramInt1 == paramInt2) {
/*  653 */       return new BitSet(0);
/*      */     }
/*      */     
/*  656 */     if (paramInt2 > i) {
/*  657 */       paramInt2 = i;
/*      */     }
/*  659 */     BitSet bitSet = new BitSet(paramInt2 - paramInt1);
/*  660 */     int j = wordIndex(paramInt2 - paramInt1 - 1) + 1;
/*  661 */     int k = wordIndex(paramInt1);
/*  662 */     boolean bool = ((paramInt1 & 0x3F) == 0) ? true : false;
/*      */ 
/*      */     
/*  665 */     for (byte b = 0; b < j - 1; b++, k++) {
/*  666 */       bitSet.words[b] = bool ? this.words[k] : (this.words[k] >>> paramInt1 | this.words[k + 1] << -paramInt1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  671 */     long l = -1L >>> -paramInt2;
/*  672 */     bitSet.words[j - 1] = ((paramInt2 - 1 & 0x3F) < (paramInt1 & 0x3F)) ? (this.words[k] >>> paramInt1 | (this.words[k + 1] & l) << -paramInt1) : ((this.words[k] & l) >>> paramInt1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  681 */     bitSet.wordsInUse = j;
/*  682 */     bitSet.recalculateWordsInUse();
/*  683 */     bitSet.checkInvariants();
/*      */     
/*  685 */     return bitSet;
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
/*      */   public int nextSetBit(int paramInt) {
/*  711 */     if (paramInt < 0) {
/*  712 */       throw new IndexOutOfBoundsException("fromIndex < 0: " + paramInt);
/*      */     }
/*  714 */     checkInvariants();
/*      */     
/*  716 */     int i = wordIndex(paramInt);
/*  717 */     if (i >= this.wordsInUse) {
/*  718 */       return -1;
/*      */     }
/*  720 */     long l = this.words[i] & -1L << paramInt;
/*      */     
/*      */     while (true) {
/*  723 */       if (l != 0L)
/*  724 */         return i * 64 + Long.numberOfTrailingZeros(l); 
/*  725 */       if (++i == this.wordsInUse)
/*  726 */         return -1; 
/*  727 */       l = this.words[i];
/*      */     } 
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
/*      */   public int nextClearBit(int paramInt) {
/*  743 */     if (paramInt < 0) {
/*  744 */       throw new IndexOutOfBoundsException("fromIndex < 0: " + paramInt);
/*      */     }
/*  746 */     checkInvariants();
/*      */     
/*  748 */     int i = wordIndex(paramInt);
/*  749 */     if (i >= this.wordsInUse) {
/*  750 */       return paramInt;
/*      */     }
/*  752 */     long l = (this.words[i] ^ 0xFFFFFFFFFFFFFFFFL) & -1L << paramInt;
/*      */     
/*      */     while (true) {
/*  755 */       if (l != 0L)
/*  756 */         return i * 64 + Long.numberOfTrailingZeros(l); 
/*  757 */       if (++i == this.wordsInUse)
/*  758 */         return this.wordsInUse * 64; 
/*  759 */       l = this.words[i] ^ 0xFFFFFFFFFFFFFFFFL;
/*      */     } 
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
/*      */   public int previousSetBit(int paramInt) {
/*  785 */     if (paramInt < 0) {
/*  786 */       if (paramInt == -1)
/*  787 */         return -1; 
/*  788 */       throw new IndexOutOfBoundsException("fromIndex < -1: " + paramInt);
/*      */     } 
/*      */ 
/*      */     
/*  792 */     checkInvariants();
/*      */     
/*  794 */     int i = wordIndex(paramInt);
/*  795 */     if (i >= this.wordsInUse) {
/*  796 */       return length() - 1;
/*      */     }
/*  798 */     long l = this.words[i] & -1L >>> -(paramInt + 1);
/*      */     
/*      */     while (true) {
/*  801 */       if (l != 0L)
/*  802 */         return (i + 1) * 64 - 1 - Long.numberOfLeadingZeros(l); 
/*  803 */       if (i-- == 0)
/*  804 */         return -1; 
/*  805 */       l = this.words[i];
/*      */     } 
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
/*      */   public int previousClearBit(int paramInt) {
/*  823 */     if (paramInt < 0) {
/*  824 */       if (paramInt == -1)
/*  825 */         return -1; 
/*  826 */       throw new IndexOutOfBoundsException("fromIndex < -1: " + paramInt);
/*      */     } 
/*      */ 
/*      */     
/*  830 */     checkInvariants();
/*      */     
/*  832 */     int i = wordIndex(paramInt);
/*  833 */     if (i >= this.wordsInUse) {
/*  834 */       return paramInt;
/*      */     }
/*  836 */     long l = (this.words[i] ^ 0xFFFFFFFFFFFFFFFFL) & -1L >>> -(paramInt + 1);
/*      */     
/*      */     while (true) {
/*  839 */       if (l != 0L)
/*  840 */         return (i + 1) * 64 - 1 - Long.numberOfLeadingZeros(l); 
/*  841 */       if (i-- == 0)
/*  842 */         return -1; 
/*  843 */       l = this.words[i] ^ 0xFFFFFFFFFFFFFFFFL;
/*      */     } 
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
/*      */   public int length() {
/*  856 */     if (this.wordsInUse == 0) {
/*  857 */       return 0;
/*      */     }
/*  859 */     return 64 * (this.wordsInUse - 1) + 64 - 
/*  860 */       Long.numberOfLeadingZeros(this.words[this.wordsInUse - 1]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  871 */     return (this.wordsInUse == 0);
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
/*      */   public boolean intersects(BitSet paramBitSet) {
/*  884 */     for (int i = Math.min(this.wordsInUse, paramBitSet.wordsInUse) - 1; i >= 0; i--) {
/*  885 */       if ((this.words[i] & paramBitSet.words[i]) != 0L)
/*  886 */         return true; 
/*  887 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int cardinality() {
/*  897 */     int i = 0;
/*  898 */     for (byte b = 0; b < this.wordsInUse; b++)
/*  899 */       i += Long.bitCount(this.words[b]); 
/*  900 */     return i;
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
/*      */   public void and(BitSet paramBitSet) {
/*  913 */     if (this == paramBitSet) {
/*      */       return;
/*      */     }
/*  916 */     while (this.wordsInUse > paramBitSet.wordsInUse) {
/*  917 */       this.words[--this.wordsInUse] = 0L;
/*      */     }
/*      */     
/*  920 */     for (byte b = 0; b < this.wordsInUse; b++) {
/*  921 */       this.words[b] = this.words[b] & paramBitSet.words[b];
/*      */     }
/*  923 */     recalculateWordsInUse();
/*  924 */     checkInvariants();
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
/*      */   public void or(BitSet paramBitSet) {
/*  937 */     if (this == paramBitSet) {
/*      */       return;
/*      */     }
/*  940 */     int i = Math.min(this.wordsInUse, paramBitSet.wordsInUse);
/*      */     
/*  942 */     if (this.wordsInUse < paramBitSet.wordsInUse) {
/*  943 */       ensureCapacity(paramBitSet.wordsInUse);
/*  944 */       this.wordsInUse = paramBitSet.wordsInUse;
/*      */     } 
/*      */ 
/*      */     
/*  948 */     for (byte b = 0; b < i; b++) {
/*  949 */       this.words[b] = this.words[b] | paramBitSet.words[b];
/*      */     }
/*      */     
/*  952 */     if (i < paramBitSet.wordsInUse) {
/*  953 */       System.arraycopy(paramBitSet.words, i, this.words, i, this.wordsInUse - i);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  958 */     checkInvariants();
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
/*      */   public void xor(BitSet paramBitSet) {
/*  976 */     int i = Math.min(this.wordsInUse, paramBitSet.wordsInUse);
/*      */     
/*  978 */     if (this.wordsInUse < paramBitSet.wordsInUse) {
/*  979 */       ensureCapacity(paramBitSet.wordsInUse);
/*  980 */       this.wordsInUse = paramBitSet.wordsInUse;
/*      */     } 
/*      */ 
/*      */     
/*  984 */     for (byte b = 0; b < i; b++) {
/*  985 */       this.words[b] = this.words[b] ^ paramBitSet.words[b];
/*      */     }
/*      */     
/*  988 */     if (i < paramBitSet.wordsInUse) {
/*  989 */       System.arraycopy(paramBitSet.words, i, this.words, i, paramBitSet.wordsInUse - i);
/*      */     }
/*      */ 
/*      */     
/*  993 */     recalculateWordsInUse();
/*  994 */     checkInvariants();
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
/*      */   public void andNot(BitSet paramBitSet) {
/* 1007 */     for (int i = Math.min(this.wordsInUse, paramBitSet.wordsInUse) - 1; i >= 0; i--) {
/* 1008 */       this.words[i] = this.words[i] & (paramBitSet.words[i] ^ 0xFFFFFFFFFFFFFFFFL);
/*      */     }
/* 1010 */     recalculateWordsInUse();
/* 1011 */     checkInvariants();
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
/*      */   public int hashCode() {
/* 1033 */     long l = 1234L;
/* 1034 */     for (int i = this.wordsInUse; --i >= 0;) {
/* 1035 */       l ^= this.words[i] * (i + 1);
/*      */     }
/* 1037 */     return (int)(l >> 32L ^ l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/* 1048 */     return this.words.length * 64;
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
/*      */   public boolean equals(Object paramObject) {
/* 1066 */     if (!(paramObject instanceof BitSet))
/* 1067 */       return false; 
/* 1068 */     if (this == paramObject) {
/* 1069 */       return true;
/*      */     }
/* 1071 */     BitSet bitSet = (BitSet)paramObject;
/*      */     
/* 1073 */     checkInvariants();
/* 1074 */     bitSet.checkInvariants();
/*      */     
/* 1076 */     if (this.wordsInUse != bitSet.wordsInUse) {
/* 1077 */       return false;
/*      */     }
/*      */     
/* 1080 */     for (byte b = 0; b < this.wordsInUse; b++) {
/* 1081 */       if (this.words[b] != bitSet.words[b])
/* 1082 */         return false; 
/*      */     } 
/* 1084 */     return true;
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
/*      */   public Object clone() {
/* 1097 */     if (!this.sizeIsSticky) {
/* 1098 */       trimToSize();
/*      */     }
/*      */     try {
/* 1101 */       BitSet bitSet = (BitSet)super.clone();
/* 1102 */       bitSet.words = (long[])this.words.clone();
/* 1103 */       bitSet.checkInvariants();
/* 1104 */       return bitSet;
/* 1105 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 1106 */       throw new InternalError(cloneNotSupportedException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void trimToSize() {
/* 1116 */     if (this.wordsInUse != this.words.length) {
/* 1117 */       this.words = Arrays.copyOf(this.words, this.wordsInUse);
/* 1118 */       checkInvariants();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1129 */     checkInvariants();
/*      */     
/* 1131 */     if (!this.sizeIsSticky) {
/* 1132 */       trimToSize();
/*      */     }
/* 1134 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 1135 */     putField.put("bits", this.words);
/* 1136 */     paramObjectOutputStream.writeFields();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1146 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/* 1147 */     this.words = (long[])getField.get("bits", (Object)null);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1152 */     this.wordsInUse = this.words.length;
/* 1153 */     recalculateWordsInUse();
/* 1154 */     this.sizeIsSticky = (this.words.length > 0 && this.words[this.words.length - 1] == 0L);
/* 1155 */     checkInvariants();
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
/*      */   public String toString() {
/* 1182 */     checkInvariants();
/*      */ 
/*      */     
/* 1185 */     int i = (this.wordsInUse > 128) ? cardinality() : (this.wordsInUse * 64);
/* 1186 */     StringBuilder stringBuilder = new StringBuilder(6 * i + 2);
/* 1187 */     stringBuilder.append('{');
/*      */     
/* 1189 */     int j = nextSetBit(0);
/* 1190 */     if (j != -1) {
/* 1191 */       stringBuilder.append(j);
/*      */       
/* 1193 */       label20: while (++j >= 0 && (
/* 1194 */         j = nextSetBit(j)) >= 0) {
/* 1195 */         int k = nextClearBit(j); while (true) {
/* 1196 */           stringBuilder.append(", ").append(j);
/* 1197 */           if (++j == k)
/*      */             continue label20; 
/*      */         } 
/*      */       } 
/* 1201 */     }  stringBuilder.append('}');
/* 1202 */     return stringBuilder.toString();
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
/*      */   public IntStream stream() {
/*      */     class BitSetIterator
/*      */       implements PrimitiveIterator.OfInt
/*      */     {
/* 1221 */       int next = BitSet.this.nextSetBit(0);
/*      */ 
/*      */       
/*      */       public boolean hasNext() {
/* 1225 */         return (this.next != -1);
/*      */       }
/*      */ 
/*      */       
/*      */       public int nextInt() {
/* 1230 */         if (this.next != -1) {
/* 1231 */           int i = this.next;
/* 1232 */           this.next = BitSet.this.nextSetBit(this.next + 1);
/* 1233 */           return i;
/*      */         } 
/* 1235 */         throw new NoSuchElementException();
/*      */       }
/*      */     };
/*      */ 
/*      */     
/* 1240 */     return StreamSupport.intStream(() -> Spliterators.spliterator(new BitSetIterator(), cardinality(), 21), 16469, false);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/BitSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */