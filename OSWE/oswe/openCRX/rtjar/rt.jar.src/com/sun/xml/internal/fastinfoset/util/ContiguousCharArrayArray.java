/*     */ package com.sun.xml.internal.fastinfoset.util;
/*     */ 
/*     */ import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContiguousCharArrayArray
/*     */   extends ValueArray
/*     */ {
/*     */   public static final int INITIAL_CHARACTER_SIZE = 512;
/*     */   public static final int MAXIMUM_CHARACTER_SIZE = 2147483647;
/*     */   protected int _maximumCharacterSize;
/*     */   public int[] _offset;
/*     */   public int[] _length;
/*     */   public char[] _array;
/*     */   public int _arrayIndex;
/*     */   public int _readOnlyArrayIndex;
/*     */   private String[] _cachedStrings;
/*     */   public int _cachedIndex;
/*     */   private ContiguousCharArrayArray _readOnlyArray;
/*     */   
/*     */   public ContiguousCharArrayArray(int initialCapacity, int maximumCapacity, int initialCharacterSize, int maximumCharacterSize) {
/*  52 */     this._offset = new int[initialCapacity];
/*  53 */     this._length = new int[initialCapacity];
/*  54 */     this._array = new char[initialCharacterSize];
/*  55 */     this._maximumCapacity = maximumCapacity;
/*  56 */     this._maximumCharacterSize = maximumCharacterSize;
/*     */   }
/*     */   
/*     */   public ContiguousCharArrayArray() {
/*  60 */     this(10, 2147483647, 512, 2147483647);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void clear() {
/*  65 */     this._arrayIndex = this._readOnlyArrayIndex;
/*  66 */     this._size = this._readOnlyArraySize;
/*     */     
/*  68 */     if (this._cachedStrings != null) {
/*  69 */       for (int i = this._readOnlyArraySize; i < this._cachedStrings.length; i++) {
/*  70 */         this._cachedStrings[i] = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public final int getArrayIndex() {
/*  76 */     return this._arrayIndex;
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(ValueArray readOnlyArray, boolean clear) {
/*  80 */     if (!(readOnlyArray instanceof ContiguousCharArrayArray)) {
/*  81 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.illegalClass", new Object[] { readOnlyArray }));
/*     */     }
/*     */     
/*  84 */     setReadOnlyArray((ContiguousCharArrayArray)readOnlyArray, clear);
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(ContiguousCharArrayArray readOnlyArray, boolean clear) {
/*  88 */     if (readOnlyArray != null) {
/*  89 */       this._readOnlyArray = readOnlyArray;
/*  90 */       this._readOnlyArraySize = readOnlyArray.getSize();
/*  91 */       this._readOnlyArrayIndex = readOnlyArray.getArrayIndex();
/*     */       
/*  93 */       if (clear) {
/*  94 */         clear();
/*     */       }
/*     */       
/*  97 */       this._array = getCompleteCharArray();
/*  98 */       this._offset = getCompleteOffsetArray();
/*  99 */       this._length = getCompleteLengthArray();
/* 100 */       this._size = this._readOnlyArraySize;
/* 101 */       this._arrayIndex = this._readOnlyArrayIndex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final char[] getCompleteCharArray() {
/* 106 */     if (this._readOnlyArray == null) {
/* 107 */       if (this._array == null) return null;
/*     */ 
/*     */       
/* 110 */       char[] clonedArray = new char[this._array.length];
/* 111 */       System.arraycopy(this._array, 0, clonedArray, 0, this._array.length);
/* 112 */       return clonedArray;
/*     */     } 
/*     */     
/* 115 */     char[] ra = this._readOnlyArray.getCompleteCharArray();
/* 116 */     char[] a = new char[this._readOnlyArrayIndex + this._array.length];
/* 117 */     System.arraycopy(ra, 0, a, 0, this._readOnlyArrayIndex);
/* 118 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int[] getCompleteOffsetArray() {
/* 123 */     if (this._readOnlyArray == null) {
/* 124 */       if (this._offset == null) return null;
/*     */ 
/*     */       
/* 127 */       int[] clonedArray = new int[this._offset.length];
/* 128 */       System.arraycopy(this._offset, 0, clonedArray, 0, this._offset.length);
/* 129 */       return clonedArray;
/*     */     } 
/*     */     
/* 132 */     int[] ra = this._readOnlyArray.getCompleteOffsetArray();
/* 133 */     int[] a = new int[this._readOnlyArraySize + this._offset.length];
/* 134 */     System.arraycopy(ra, 0, a, 0, this._readOnlyArraySize);
/* 135 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int[] getCompleteLengthArray() {
/* 140 */     if (this._readOnlyArray == null) {
/* 141 */       if (this._length == null) return null;
/*     */ 
/*     */       
/* 144 */       int[] clonedArray = new int[this._length.length];
/* 145 */       System.arraycopy(this._length, 0, clonedArray, 0, this._length.length);
/* 146 */       return clonedArray;
/*     */     } 
/*     */     
/* 149 */     int[] ra = this._readOnlyArray.getCompleteLengthArray();
/* 150 */     int[] a = new int[this._readOnlyArraySize + this._length.length];
/* 151 */     System.arraycopy(ra, 0, a, 0, this._readOnlyArraySize);
/* 152 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getString(int i) {
/* 157 */     if (this._cachedStrings != null && i < this._cachedStrings.length) {
/* 158 */       String s = this._cachedStrings[i];
/* 159 */       return (s != null) ? s : (this._cachedStrings[i] = new String(this._array, this._offset[i], this._length[i]));
/*     */     } 
/*     */     
/* 162 */     String[] newCachedStrings = new String[this._offset.length];
/* 163 */     if (this._cachedStrings != null && i >= this._cachedStrings.length) {
/* 164 */       System.arraycopy(this._cachedStrings, 0, newCachedStrings, 0, this._cachedStrings.length);
/*     */     }
/* 166 */     this._cachedStrings = newCachedStrings;
/*     */     
/* 168 */     this._cachedStrings[i] = new String(this._array, this._offset[i], this._length[i]); return new String(this._array, this._offset[i], this._length[i]);
/*     */   }
/*     */   
/*     */   public final void ensureSize(int l) {
/* 172 */     if (this._arrayIndex + l >= this._array.length) {
/* 173 */       resizeArray(this._arrayIndex + l);
/*     */     }
/*     */   }
/*     */   
/*     */   public final void add(int l) {
/* 178 */     if (this._size == this._offset.length) {
/* 179 */       resize();
/*     */     }
/*     */     
/* 182 */     this._cachedIndex = this._size;
/* 183 */     this._offset[this._size] = this._arrayIndex;
/* 184 */     this._length[this._size++] = l;
/*     */     
/* 186 */     this._arrayIndex += l;
/*     */   }
/*     */   
/*     */   public final int add(char[] c, int l) {
/* 190 */     if (this._size == this._offset.length) {
/* 191 */       resize();
/*     */     }
/*     */     
/* 194 */     int oldArrayIndex = this._arrayIndex;
/* 195 */     int arrayIndex = oldArrayIndex + l;
/*     */     
/* 197 */     this._cachedIndex = this._size;
/* 198 */     this._offset[this._size] = oldArrayIndex;
/* 199 */     this._length[this._size++] = l;
/*     */     
/* 201 */     if (arrayIndex >= this._array.length) {
/* 202 */       resizeArray(arrayIndex);
/*     */     }
/*     */     
/* 205 */     System.arraycopy(c, 0, this._array, oldArrayIndex, l);
/*     */     
/* 207 */     this._arrayIndex = arrayIndex;
/* 208 */     return oldArrayIndex;
/*     */   }
/*     */   
/*     */   protected final void resize() {
/* 212 */     if (this._size == this._maximumCapacity) {
/* 213 */       throw new ValueArrayResourceException(CommonResourceBundle.getInstance().getString("message.arrayMaxCapacity"));
/*     */     }
/*     */     
/* 216 */     int newSize = this._size * 3 / 2 + 1;
/* 217 */     if (newSize > this._maximumCapacity) {
/* 218 */       newSize = this._maximumCapacity;
/*     */     }
/*     */     
/* 221 */     int[] offset = new int[newSize];
/* 222 */     System.arraycopy(this._offset, 0, offset, 0, this._size);
/* 223 */     this._offset = offset;
/*     */     
/* 225 */     int[] length = new int[newSize];
/* 226 */     System.arraycopy(this._length, 0, length, 0, this._size);
/* 227 */     this._length = length;
/*     */   }
/*     */   
/*     */   protected final void resizeArray(int requestedSize) {
/* 231 */     if (this._arrayIndex == this._maximumCharacterSize) {
/* 232 */       throw new ValueArrayResourceException(CommonResourceBundle.getInstance().getString("message.maxNumberOfCharacters"));
/*     */     }
/*     */     
/* 235 */     int newSize = requestedSize * 3 / 2 + 1;
/* 236 */     if (newSize > this._maximumCharacterSize) {
/* 237 */       newSize = this._maximumCharacterSize;
/*     */     }
/*     */     
/* 240 */     char[] array = new char[newSize];
/* 241 */     System.arraycopy(this._array, 0, array, 0, this._arrayIndex);
/* 242 */     this._array = array;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/util/ContiguousCharArrayArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */