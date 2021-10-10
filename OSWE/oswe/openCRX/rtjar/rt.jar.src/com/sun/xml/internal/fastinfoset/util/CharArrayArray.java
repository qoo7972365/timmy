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
/*     */ public class CharArrayArray
/*     */   extends ValueArray
/*     */ {
/*     */   private CharArray[] _array;
/*     */   private CharArrayArray _readOnlyArray;
/*     */   
/*     */   public CharArrayArray(int initialCapacity, int maximumCapacity) {
/*  39 */     this._array = new CharArray[initialCapacity];
/*  40 */     this._maximumCapacity = maximumCapacity;
/*     */   }
/*     */   
/*     */   public CharArrayArray() {
/*  44 */     this(10, 2147483647);
/*     */   }
/*     */   
/*     */   public final void clear() {
/*  48 */     for (int i = 0; i < this._size; i++) {
/*  49 */       this._array[i] = null;
/*     */     }
/*  51 */     this._size = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final CharArray[] getArray() {
/*  59 */     if (this._array == null) return null;
/*     */     
/*  61 */     CharArray[] clonedArray = new CharArray[this._array.length];
/*  62 */     System.arraycopy(this._array, 0, clonedArray, 0, this._array.length);
/*  63 */     return clonedArray;
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(ValueArray readOnlyArray, boolean clear) {
/*  67 */     if (!(readOnlyArray instanceof CharArrayArray)) {
/*  68 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.illegalClass", new Object[] { readOnlyArray }));
/*     */     }
/*     */     
/*  71 */     setReadOnlyArray((CharArrayArray)readOnlyArray, clear);
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(CharArrayArray readOnlyArray, boolean clear) {
/*  75 */     if (readOnlyArray != null) {
/*  76 */       this._readOnlyArray = readOnlyArray;
/*  77 */       this._readOnlyArraySize = readOnlyArray.getSize();
/*     */       
/*  79 */       if (clear) {
/*  80 */         clear();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public final CharArray get(int i) {
/*  86 */     if (this._readOnlyArray == null) {
/*  87 */       return this._array[i];
/*     */     }
/*  89 */     if (i < this._readOnlyArraySize) {
/*  90 */       return this._readOnlyArray.get(i);
/*     */     }
/*  92 */     return this._array[i - this._readOnlyArraySize];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(CharArray s) {
/*  98 */     if (this._size == this._array.length) {
/*  99 */       resize();
/*     */     }
/*     */     
/* 102 */     this._array[this._size++] = s;
/*     */   }
/*     */   
/*     */   protected final void resize() {
/* 106 */     if (this._size == this._maximumCapacity) {
/* 107 */       throw new ValueArrayResourceException(CommonResourceBundle.getInstance().getString("message.arrayMaxCapacity"));
/*     */     }
/*     */     
/* 110 */     int newSize = this._size * 3 / 2 + 1;
/* 111 */     if (newSize > this._maximumCapacity) {
/* 112 */       newSize = this._maximumCapacity;
/*     */     }
/*     */     
/* 115 */     CharArray[] newArray = new CharArray[newSize];
/* 116 */     System.arraycopy(this._array, 0, newArray, 0, this._size);
/* 117 */     this._array = newArray;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/util/CharArrayArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */