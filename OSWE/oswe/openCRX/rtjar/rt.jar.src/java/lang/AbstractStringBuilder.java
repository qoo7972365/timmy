/*      */ package java.lang;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.util.Arrays;
/*      */ import sun.misc.FloatingDecimal;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ abstract class AbstractStringBuilder
/*      */   implements Appendable, CharSequence
/*      */ {
/*      */   char[] value;
/*      */   int count;
/*      */   private static final int MAX_ARRAY_SIZE = 2147483639;
/*      */   
/*      */   AbstractStringBuilder() {}
/*      */   
/*      */   AbstractStringBuilder(int paramInt) {
/*   68 */     this.value = new char[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int length() {
/*   79 */     return this.count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int capacity() {
/*   90 */     return this.value.length;
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
/*      */   public void ensureCapacity(int paramInt) {
/*  110 */     if (paramInt > 0) {
/*  111 */       ensureCapacityInternal(paramInt);
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
/*      */   private void ensureCapacityInternal(int paramInt) {
/*  123 */     if (paramInt - this.value.length > 0) {
/*  124 */       this.value = Arrays.copyOf(this.value, 
/*  125 */           newCapacity(paramInt));
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
/*      */   private int newCapacity(int paramInt) {
/*  150 */     int i = (this.value.length << 1) + 2;
/*  151 */     if (i - paramInt < 0) {
/*  152 */       i = paramInt;
/*      */     }
/*  154 */     return (i <= 0 || 2147483639 - i < 0) ? 
/*  155 */       hugeCapacity(paramInt) : i;
/*      */   }
/*      */ 
/*      */   
/*      */   private int hugeCapacity(int paramInt) {
/*  160 */     if (Integer.MAX_VALUE - paramInt < 0) {
/*  161 */       throw new OutOfMemoryError();
/*      */     }
/*  163 */     return (paramInt > 2147483639) ? paramInt : 2147483639;
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
/*      */   public void trimToSize() {
/*  175 */     if (this.count < this.value.length) {
/*  176 */       this.value = Arrays.copyOf(this.value, this.count);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLength(int paramInt) {
/*  206 */     if (paramInt < 0)
/*  207 */       throw new StringIndexOutOfBoundsException(paramInt); 
/*  208 */     ensureCapacityInternal(paramInt);
/*      */     
/*  210 */     if (this.count < paramInt) {
/*  211 */       Arrays.fill(this.value, this.count, paramInt, false);
/*      */     }
/*      */     
/*  214 */     this.count = paramInt;
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
/*      */   public char charAt(int paramInt) {
/*  236 */     if (paramInt < 0 || paramInt >= this.count)
/*  237 */       throw new StringIndexOutOfBoundsException(paramInt); 
/*  238 */     return this.value[paramInt];
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
/*      */   public int codePointAt(int paramInt) {
/*  263 */     if (paramInt < 0 || paramInt >= this.count) {
/*  264 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/*  266 */     return Character.codePointAtImpl(this.value, paramInt, this.count);
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
/*      */   public int codePointBefore(int paramInt) {
/*  291 */     int i = paramInt - 1;
/*  292 */     if (i < 0 || i >= this.count) {
/*  293 */       throw new StringIndexOutOfBoundsException(paramInt);
/*      */     }
/*  295 */     return Character.codePointBeforeImpl(this.value, paramInt, 0);
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
/*      */   public int codePointCount(int paramInt1, int paramInt2) {
/*  319 */     if (paramInt1 < 0 || paramInt2 > this.count || paramInt1 > paramInt2) {
/*  320 */       throw new IndexOutOfBoundsException();
/*      */     }
/*  322 */     return Character.codePointCountImpl(this.value, paramInt1, paramInt2 - paramInt1);
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
/*      */   public int offsetByCodePoints(int paramInt1, int paramInt2) {
/*  345 */     if (paramInt1 < 0 || paramInt1 > this.count) {
/*  346 */       throw new IndexOutOfBoundsException();
/*      */     }
/*  348 */     return Character.offsetByCodePointsImpl(this.value, 0, this.count, paramInt1, paramInt2);
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
/*      */   public void getChars(int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3) {
/*  382 */     if (paramInt1 < 0)
/*  383 */       throw new StringIndexOutOfBoundsException(paramInt1); 
/*  384 */     if (paramInt2 < 0 || paramInt2 > this.count)
/*  385 */       throw new StringIndexOutOfBoundsException(paramInt2); 
/*  386 */     if (paramInt1 > paramInt2)
/*  387 */       throw new StringIndexOutOfBoundsException("srcBegin > srcEnd"); 
/*  388 */     System.arraycopy(this.value, paramInt1, paramArrayOfchar, paramInt3, paramInt2 - paramInt1);
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
/*      */   public void setCharAt(int paramInt, char paramChar) {
/*  406 */     if (paramInt < 0 || paramInt >= this.count)
/*  407 */       throw new StringIndexOutOfBoundsException(paramInt); 
/*  408 */     this.value[paramInt] = paramChar;
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
/*      */   public AbstractStringBuilder append(Object paramObject) {
/*  423 */     return append(String.valueOf(paramObject));
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
/*      */   public AbstractStringBuilder append(String paramString) {
/*  445 */     if (paramString == null)
/*  446 */       return appendNull(); 
/*  447 */     int i = paramString.length();
/*  448 */     ensureCapacityInternal(this.count + i);
/*  449 */     paramString.getChars(0, i, this.value, this.count);
/*  450 */     this.count += i;
/*  451 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public AbstractStringBuilder append(StringBuffer paramStringBuffer) {
/*  456 */     if (paramStringBuffer == null)
/*  457 */       return appendNull(); 
/*  458 */     int i = paramStringBuffer.length();
/*  459 */     ensureCapacityInternal(this.count + i);
/*  460 */     paramStringBuffer.getChars(0, i, this.value, this.count);
/*  461 */     this.count += i;
/*  462 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   AbstractStringBuilder append(AbstractStringBuilder paramAbstractStringBuilder) {
/*  469 */     if (paramAbstractStringBuilder == null)
/*  470 */       return appendNull(); 
/*  471 */     int i = paramAbstractStringBuilder.length();
/*  472 */     ensureCapacityInternal(this.count + i);
/*  473 */     paramAbstractStringBuilder.getChars(0, i, this.value, this.count);
/*  474 */     this.count += i;
/*  475 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AbstractStringBuilder append(CharSequence paramCharSequence) {
/*  481 */     if (paramCharSequence == null)
/*  482 */       return appendNull(); 
/*  483 */     if (paramCharSequence instanceof String)
/*  484 */       return append((String)paramCharSequence); 
/*  485 */     if (paramCharSequence instanceof AbstractStringBuilder) {
/*  486 */       return append((AbstractStringBuilder)paramCharSequence);
/*      */     }
/*  488 */     return append(paramCharSequence, 0, paramCharSequence.length());
/*      */   }
/*      */   
/*      */   private AbstractStringBuilder appendNull() {
/*  492 */     int i = this.count;
/*  493 */     ensureCapacityInternal(i + 4);
/*  494 */     char[] arrayOfChar = this.value;
/*  495 */     arrayOfChar[i++] = 'n';
/*  496 */     arrayOfChar[i++] = 'u';
/*  497 */     arrayOfChar[i++] = 'l';
/*  498 */     arrayOfChar[i++] = 'l';
/*  499 */     this.count = i;
/*  500 */     return this;
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
/*      */   public AbstractStringBuilder append(CharSequence paramCharSequence, int paramInt1, int paramInt2) {
/*  534 */     if (paramCharSequence == null)
/*  535 */       paramCharSequence = "null"; 
/*  536 */     if (paramInt1 < 0 || paramInt1 > paramInt2 || paramInt2 > paramCharSequence.length())
/*  537 */       throw new IndexOutOfBoundsException("start " + paramInt1 + ", end " + paramInt2 + ", s.length() " + paramCharSequence
/*      */           
/*  539 */           .length()); 
/*  540 */     int i = paramInt2 - paramInt1;
/*  541 */     ensureCapacityInternal(this.count + i);
/*  542 */     for (int j = paramInt1, k = this.count; j < paramInt2; j++, k++)
/*  543 */       this.value[k] = paramCharSequence.charAt(j); 
/*  544 */     this.count += i;
/*  545 */     return this;
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
/*      */   public AbstractStringBuilder append(char[] paramArrayOfchar) {
/*  565 */     int i = paramArrayOfchar.length;
/*  566 */     ensureCapacityInternal(this.count + i);
/*  567 */     System.arraycopy(paramArrayOfchar, 0, this.value, this.count, i);
/*  568 */     this.count += i;
/*  569 */     return this;
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
/*      */   public AbstractStringBuilder append(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  595 */     if (paramInt2 > 0)
/*  596 */       ensureCapacityInternal(this.count + paramInt2); 
/*  597 */     System.arraycopy(paramArrayOfchar, paramInt1, this.value, this.count, paramInt2);
/*  598 */     this.count += paramInt2;
/*  599 */     return this;
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
/*      */   public AbstractStringBuilder append(boolean paramBoolean) {
/*  615 */     if (paramBoolean) {
/*  616 */       ensureCapacityInternal(this.count + 4);
/*  617 */       this.value[this.count++] = 't';
/*  618 */       this.value[this.count++] = 'r';
/*  619 */       this.value[this.count++] = 'u';
/*  620 */       this.value[this.count++] = 'e';
/*      */     } else {
/*  622 */       ensureCapacityInternal(this.count + 5);
/*  623 */       this.value[this.count++] = 'f';
/*  624 */       this.value[this.count++] = 'a';
/*  625 */       this.value[this.count++] = 'l';
/*  626 */       this.value[this.count++] = 's';
/*  627 */       this.value[this.count++] = 'e';
/*      */     } 
/*  629 */     return this;
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
/*      */   public AbstractStringBuilder append(char paramChar) {
/*  649 */     ensureCapacityInternal(this.count + 1);
/*  650 */     this.value[this.count++] = paramChar;
/*  651 */     return this;
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
/*      */   public AbstractStringBuilder append(int paramInt) {
/*  667 */     if (paramInt == Integer.MIN_VALUE) {
/*  668 */       append("-2147483648");
/*  669 */       return this;
/*      */     } 
/*      */     
/*  672 */     int i = (paramInt < 0) ? (Integer.stringSize(-paramInt) + 1) : Integer.stringSize(paramInt);
/*  673 */     int j = this.count + i;
/*  674 */     ensureCapacityInternal(j);
/*  675 */     Integer.getChars(paramInt, j, this.value);
/*  676 */     this.count = j;
/*  677 */     return this;
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
/*      */   public AbstractStringBuilder append(long paramLong) {
/*  693 */     if (paramLong == Long.MIN_VALUE) {
/*  694 */       append("-9223372036854775808");
/*  695 */       return this;
/*      */     } 
/*      */     
/*  698 */     int i = (paramLong < 0L) ? (Long.stringSize(-paramLong) + 1) : Long.stringSize(paramLong);
/*  699 */     int j = this.count + i;
/*  700 */     ensureCapacityInternal(j);
/*  701 */     Long.getChars(paramLong, j, this.value);
/*  702 */     this.count = j;
/*  703 */     return this;
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
/*      */   public AbstractStringBuilder append(float paramFloat) {
/*  719 */     FloatingDecimal.appendTo(paramFloat, this);
/*  720 */     return this;
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
/*      */   public AbstractStringBuilder append(double paramDouble) {
/*  736 */     FloatingDecimal.appendTo(paramDouble, this);
/*  737 */     return this;
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
/*      */   public AbstractStringBuilder delete(int paramInt1, int paramInt2) {
/*  755 */     if (paramInt1 < 0)
/*  756 */       throw new StringIndexOutOfBoundsException(paramInt1); 
/*  757 */     if (paramInt2 > this.count)
/*  758 */       paramInt2 = this.count; 
/*  759 */     if (paramInt1 > paramInt2)
/*  760 */       throw new StringIndexOutOfBoundsException(); 
/*  761 */     int i = paramInt2 - paramInt1;
/*  762 */     if (i > 0) {
/*  763 */       System.arraycopy(this.value, paramInt1 + i, this.value, paramInt1, this.count - paramInt2);
/*  764 */       this.count -= i;
/*      */     } 
/*  766 */     return this;
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
/*      */   public AbstractStringBuilder appendCodePoint(int paramInt) {
/*  789 */     int i = this.count;
/*      */     
/*  791 */     if (Character.isBmpCodePoint(paramInt)) {
/*  792 */       ensureCapacityInternal(i + 1);
/*  793 */       this.value[i] = (char)paramInt;
/*  794 */       this.count = i + 1;
/*  795 */     } else if (Character.isValidCodePoint(paramInt)) {
/*  796 */       ensureCapacityInternal(i + 2);
/*  797 */       Character.toSurrogates(paramInt, this.value, i);
/*  798 */       this.count = i + 2;
/*      */     } else {
/*  800 */       throw new IllegalArgumentException();
/*      */     } 
/*  802 */     return this;
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
/*      */   public AbstractStringBuilder deleteCharAt(int paramInt) {
/*  823 */     if (paramInt < 0 || paramInt >= this.count)
/*  824 */       throw new StringIndexOutOfBoundsException(paramInt); 
/*  825 */     System.arraycopy(this.value, paramInt + 1, this.value, paramInt, this.count - paramInt - 1);
/*  826 */     this.count--;
/*  827 */     return this;
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
/*      */   public AbstractStringBuilder replace(int paramInt1, int paramInt2, String paramString) {
/*  850 */     if (paramInt1 < 0)
/*  851 */       throw new StringIndexOutOfBoundsException(paramInt1); 
/*  852 */     if (paramInt1 > this.count)
/*  853 */       throw new StringIndexOutOfBoundsException("start > length()"); 
/*  854 */     if (paramInt1 > paramInt2) {
/*  855 */       throw new StringIndexOutOfBoundsException("start > end");
/*      */     }
/*  857 */     if (paramInt2 > this.count)
/*  858 */       paramInt2 = this.count; 
/*  859 */     int i = paramString.length();
/*  860 */     int j = this.count + i - paramInt2 - paramInt1;
/*  861 */     ensureCapacityInternal(j);
/*      */     
/*  863 */     System.arraycopy(this.value, paramInt2, this.value, paramInt1 + i, this.count - paramInt2);
/*  864 */     paramString.getChars(this.value, paramInt1);
/*  865 */     this.count = j;
/*  866 */     return this;
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
/*      */   public String substring(int paramInt) {
/*  881 */     return substring(paramInt, this.count);
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
/*      */   public CharSequence subSequence(int paramInt1, int paramInt2) {
/*  912 */     return substring(paramInt1, paramInt2);
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
/*      */   public String substring(int paramInt1, int paramInt2) {
/*  930 */     if (paramInt1 < 0)
/*  931 */       throw new StringIndexOutOfBoundsException(paramInt1); 
/*  932 */     if (paramInt2 > this.count)
/*  933 */       throw new StringIndexOutOfBoundsException(paramInt2); 
/*  934 */     if (paramInt1 > paramInt2)
/*  935 */       throw new StringIndexOutOfBoundsException(paramInt2 - paramInt1); 
/*  936 */     return new String(this.value, paramInt1, paramInt2 - paramInt1);
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
/*      */   public AbstractStringBuilder insert(int paramInt1, char[] paramArrayOfchar, int paramInt2, int paramInt3) {
/*  963 */     if (paramInt1 < 0 || paramInt1 > length())
/*  964 */       throw new StringIndexOutOfBoundsException(paramInt1); 
/*  965 */     if (paramInt2 < 0 || paramInt3 < 0 || paramInt2 > paramArrayOfchar.length - paramInt3) {
/*  966 */       throw new StringIndexOutOfBoundsException("offset " + paramInt2 + ", len " + paramInt3 + ", str.length " + paramArrayOfchar.length);
/*      */     }
/*      */     
/*  969 */     ensureCapacityInternal(this.count + paramInt3);
/*  970 */     System.arraycopy(this.value, paramInt1, this.value, paramInt1 + paramInt3, this.count - paramInt1);
/*  971 */     System.arraycopy(paramArrayOfchar, paramInt2, this.value, paramInt1, paramInt3);
/*  972 */     this.count += paramInt3;
/*  973 */     return this;
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
/*      */   public AbstractStringBuilder insert(int paramInt, Object paramObject) {
/*  996 */     return insert(paramInt, String.valueOf(paramObject));
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
/*      */   public AbstractStringBuilder insert(int paramInt, String paramString) {
/* 1031 */     if (paramInt < 0 || paramInt > length())
/* 1032 */       throw new StringIndexOutOfBoundsException(paramInt); 
/* 1033 */     if (paramString == null)
/* 1034 */       paramString = "null"; 
/* 1035 */     int i = paramString.length();
/* 1036 */     ensureCapacityInternal(this.count + i);
/* 1037 */     System.arraycopy(this.value, paramInt, this.value, paramInt + i, this.count - paramInt);
/* 1038 */     paramString.getChars(this.value, paramInt);
/* 1039 */     this.count += i;
/* 1040 */     return this;
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
/*      */   public AbstractStringBuilder insert(int paramInt, char[] paramArrayOfchar) {
/* 1068 */     if (paramInt < 0 || paramInt > length())
/* 1069 */       throw new StringIndexOutOfBoundsException(paramInt); 
/* 1070 */     int i = paramArrayOfchar.length;
/* 1071 */     ensureCapacityInternal(this.count + i);
/* 1072 */     System.arraycopy(this.value, paramInt, this.value, paramInt + i, this.count - paramInt);
/* 1073 */     System.arraycopy(paramArrayOfchar, 0, this.value, paramInt, i);
/* 1074 */     this.count += i;
/* 1075 */     return this;
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
/*      */   public AbstractStringBuilder insert(int paramInt, CharSequence paramCharSequence) {
/* 1100 */     if (paramCharSequence == null)
/* 1101 */       paramCharSequence = "null"; 
/* 1102 */     if (paramCharSequence instanceof String)
/* 1103 */       return insert(paramInt, (String)paramCharSequence); 
/* 1104 */     return insert(paramInt, paramCharSequence, 0, paramCharSequence.length());
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
/*      */   public AbstractStringBuilder insert(int paramInt1, CharSequence paramCharSequence, int paramInt2, int paramInt3) {
/* 1153 */     if (paramCharSequence == null)
/* 1154 */       paramCharSequence = "null"; 
/* 1155 */     if (paramInt1 < 0 || paramInt1 > length())
/* 1156 */       throw new IndexOutOfBoundsException("dstOffset " + paramInt1); 
/* 1157 */     if (paramInt2 < 0 || paramInt3 < 0 || paramInt2 > paramInt3 || paramInt3 > paramCharSequence.length())
/* 1158 */       throw new IndexOutOfBoundsException("start " + paramInt2 + ", end " + paramInt3 + ", s.length() " + paramCharSequence
/*      */           
/* 1160 */           .length()); 
/* 1161 */     int i = paramInt3 - paramInt2;
/* 1162 */     ensureCapacityInternal(this.count + i);
/* 1163 */     System.arraycopy(this.value, paramInt1, this.value, paramInt1 + i, this.count - paramInt1);
/*      */     
/* 1165 */     for (int j = paramInt2; j < paramInt3; j++)
/* 1166 */       this.value[paramInt1++] = paramCharSequence.charAt(j); 
/* 1167 */     this.count += i;
/* 1168 */     return this;
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
/*      */   public AbstractStringBuilder insert(int paramInt, boolean paramBoolean) {
/* 1191 */     return insert(paramInt, String.valueOf(paramBoolean));
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
/*      */   public AbstractStringBuilder insert(int paramInt, char paramChar) {
/* 1214 */     ensureCapacityInternal(this.count + 1);
/* 1215 */     System.arraycopy(this.value, paramInt, this.value, paramInt + 1, this.count - paramInt);
/* 1216 */     this.value[paramInt] = paramChar;
/* 1217 */     this.count++;
/* 1218 */     return this;
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
/*      */   public AbstractStringBuilder insert(int paramInt1, int paramInt2) {
/* 1241 */     return insert(paramInt1, String.valueOf(paramInt2));
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
/*      */   public AbstractStringBuilder insert(int paramInt, long paramLong) {
/* 1264 */     return insert(paramInt, String.valueOf(paramLong));
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
/*      */   public AbstractStringBuilder insert(int paramInt, float paramFloat) {
/* 1287 */     return insert(paramInt, String.valueOf(paramFloat));
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
/*      */   public AbstractStringBuilder insert(int paramInt, double paramDouble) {
/* 1310 */     return insert(paramInt, String.valueOf(paramDouble));
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
/*      */   public int indexOf(String paramString) {
/* 1329 */     return indexOf(paramString, 0);
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
/*      */   public int indexOf(String paramString, int paramInt) {
/* 1348 */     return String.indexOf(this.value, 0, this.count, paramString, paramInt);
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
/*      */   public int lastIndexOf(String paramString) {
/* 1368 */     return lastIndexOf(paramString, this.count);
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
/*      */   public int lastIndexOf(String paramString, int paramInt) {
/* 1387 */     return String.lastIndexOf(this.value, 0, this.count, paramString, paramInt);
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
/*      */   public AbstractStringBuilder reverse() {
/* 1413 */     boolean bool = false;
/* 1414 */     int i = this.count - 1;
/* 1415 */     for (int j = i - 1 >> 1; j >= 0; j--) {
/* 1416 */       int k = i - j;
/* 1417 */       char c1 = this.value[j];
/* 1418 */       char c2 = this.value[k];
/* 1419 */       this.value[j] = c2;
/* 1420 */       this.value[k] = c1;
/* 1421 */       if (Character.isSurrogate(c1) || 
/* 1422 */         Character.isSurrogate(c2)) {
/* 1423 */         bool = true;
/*      */       }
/*      */     } 
/* 1426 */     if (bool) {
/* 1427 */       reverseAllValidSurrogatePairs();
/*      */     }
/* 1429 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   private void reverseAllValidSurrogatePairs() {
/* 1434 */     for (byte b = 0; b < this.count - 1; b++) {
/* 1435 */       char c = this.value[b];
/* 1436 */       if (Character.isLowSurrogate(c)) {
/* 1437 */         char c1 = this.value[b + 1];
/* 1438 */         if (Character.isHighSurrogate(c1)) {
/* 1439 */           this.value[b++] = c1;
/* 1440 */           this.value[b] = c;
/*      */         } 
/*      */       } 
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
/*      */   final char[] getValue() {
/* 1463 */     return this.value;
/*      */   }
/*      */   
/*      */   public abstract String toString();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/AbstractStringBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */