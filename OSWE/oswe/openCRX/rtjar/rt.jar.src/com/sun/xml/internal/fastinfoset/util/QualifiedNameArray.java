/*     */ package com.sun.xml.internal.fastinfoset.util;
/*     */ 
/*     */ import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
/*     */ import com.sun.xml.internal.fastinfoset.QualifiedName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QualifiedNameArray
/*     */   extends ValueArray
/*     */ {
/*     */   public QualifiedName[] _array;
/*     */   private QualifiedNameArray _readOnlyArray;
/*     */   
/*     */   public QualifiedNameArray(int initialCapacity, int maximumCapacity) {
/*  40 */     this._array = new QualifiedName[initialCapacity];
/*  41 */     this._maximumCapacity = maximumCapacity;
/*     */   }
/*     */   
/*     */   public QualifiedNameArray() {
/*  45 */     this(10, 2147483647);
/*     */   }
/*     */   
/*     */   public final void clear() {
/*  49 */     this._size = this._readOnlyArraySize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QualifiedName[] getArray() {
/*  57 */     if (this._array == null) return null;
/*     */     
/*  59 */     QualifiedName[] clonedArray = new QualifiedName[this._array.length];
/*  60 */     System.arraycopy(this._array, 0, clonedArray, 0, this._array.length);
/*  61 */     return clonedArray;
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(ValueArray readOnlyArray, boolean clear) {
/*  65 */     if (!(readOnlyArray instanceof QualifiedNameArray)) {
/*  66 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance()
/*  67 */           .getString("message.illegalClass", new Object[] { readOnlyArray }));
/*     */     }
/*     */     
/*  70 */     setReadOnlyArray((QualifiedNameArray)readOnlyArray, clear);
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(QualifiedNameArray readOnlyArray, boolean clear) {
/*  74 */     if (readOnlyArray != null) {
/*  75 */       this._readOnlyArray = readOnlyArray;
/*  76 */       this._readOnlyArraySize = readOnlyArray.getSize();
/*     */       
/*  78 */       if (clear) {
/*  79 */         clear();
/*     */       }
/*     */       
/*  82 */       this._array = getCompleteArray();
/*  83 */       this._size = this._readOnlyArraySize;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final QualifiedName[] getCompleteArray() {
/*  88 */     if (this._readOnlyArray == null)
/*     */     {
/*  90 */       return getArray();
/*     */     }
/*     */     
/*  93 */     QualifiedName[] ra = this._readOnlyArray.getCompleteArray();
/*  94 */     QualifiedName[] a = new QualifiedName[this._readOnlyArraySize + this._array.length];
/*  95 */     System.arraycopy(ra, 0, a, 0, this._readOnlyArraySize);
/*  96 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public final QualifiedName getNext() {
/* 101 */     return (this._size == this._array.length) ? null : this._array[this._size];
/*     */   }
/*     */   
/*     */   public final void add(QualifiedName s) {
/* 105 */     if (this._size == this._array.length) {
/* 106 */       resize();
/*     */     }
/*     */     
/* 109 */     this._array[this._size++] = s;
/*     */   }
/*     */   
/*     */   protected final void resize() {
/* 113 */     if (this._size == this._maximumCapacity) {
/* 114 */       throw new ValueArrayResourceException(CommonResourceBundle.getInstance().getString("message.arrayMaxCapacity"));
/*     */     }
/*     */     
/* 117 */     int newSize = this._size * 3 / 2 + 1;
/* 118 */     if (newSize > this._maximumCapacity) {
/* 119 */       newSize = this._maximumCapacity;
/*     */     }
/*     */     
/* 122 */     QualifiedName[] newArray = new QualifiedName[newSize];
/* 123 */     System.arraycopy(this._array, 0, newArray, 0, this._size);
/* 124 */     this._array = newArray;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/util/QualifiedNameArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */