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
/*     */ public class StringArray
/*     */   extends ValueArray
/*     */ {
/*     */   public String[] _array;
/*     */   private StringArray _readOnlyArray;
/*     */   private boolean _clear;
/*     */   
/*     */   public StringArray(int initialCapacity, int maximumCapacity, boolean clear) {
/*  40 */     this._array = new String[initialCapacity];
/*  41 */     this._maximumCapacity = maximumCapacity;
/*  42 */     this._clear = clear;
/*     */   }
/*     */   
/*     */   public StringArray() {
/*  46 */     this(10, 2147483647, false);
/*     */   }
/*     */   
/*     */   public final void clear() {
/*  50 */     if (this._clear) for (int i = this._readOnlyArraySize; i < this._size; i++) {
/*  51 */         this._array[i] = null;
/*     */       } 
/*  53 */     this._size = this._readOnlyArraySize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String[] getArray() {
/*  61 */     if (this._array == null) return null;
/*     */     
/*  63 */     String[] clonedArray = new String[this._array.length];
/*  64 */     System.arraycopy(this._array, 0, clonedArray, 0, this._array.length);
/*  65 */     return clonedArray;
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(ValueArray readOnlyArray, boolean clear) {
/*  69 */     if (!(readOnlyArray instanceof StringArray)) {
/*  70 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance()
/*  71 */           .getString("message.illegalClass", new Object[] { readOnlyArray }));
/*     */     }
/*     */     
/*  74 */     setReadOnlyArray((StringArray)readOnlyArray, clear);
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(StringArray readOnlyArray, boolean clear) {
/*  78 */     if (readOnlyArray != null) {
/*  79 */       this._readOnlyArray = readOnlyArray;
/*  80 */       this._readOnlyArraySize = readOnlyArray.getSize();
/*     */       
/*  82 */       if (clear) {
/*  83 */         clear();
/*     */       }
/*     */       
/*  86 */       this._array = getCompleteArray();
/*  87 */       this._size = this._readOnlyArraySize;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final String[] getCompleteArray() {
/*  92 */     if (this._readOnlyArray == null)
/*     */     {
/*  94 */       return getArray();
/*     */     }
/*     */     
/*  97 */     String[] ra = this._readOnlyArray.getCompleteArray();
/*  98 */     String[] a = new String[this._readOnlyArraySize + this._array.length];
/*  99 */     System.arraycopy(ra, 0, a, 0, this._readOnlyArraySize);
/* 100 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String get(int i) {
/* 105 */     return this._array[i];
/*     */   }
/*     */   
/*     */   public final int add(String s) {
/* 109 */     if (this._size == this._array.length) {
/* 110 */       resize();
/*     */     }
/*     */     
/* 113 */     this._array[this._size++] = s;
/* 114 */     return this._size;
/*     */   }
/*     */   
/*     */   protected final void resize() {
/* 118 */     if (this._size == this._maximumCapacity) {
/* 119 */       throw new ValueArrayResourceException(CommonResourceBundle.getInstance().getString("message.arrayMaxCapacity"));
/*     */     }
/*     */     
/* 122 */     int newSize = this._size * 3 / 2 + 1;
/* 123 */     if (newSize > this._maximumCapacity) {
/* 124 */       newSize = this._maximumCapacity;
/*     */     }
/*     */     
/* 127 */     String[] newArray = new String[newSize];
/* 128 */     System.arraycopy(this._array, 0, newArray, 0, this._size);
/* 129 */     this._array = newArray;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/util/StringArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */