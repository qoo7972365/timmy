/*     */ package java.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class JumboEnumSet<E extends Enum<E>>
/*     */   extends EnumSet<E>
/*     */ {
/*     */   private static final long serialVersionUID = 334349849919042784L;
/*     */   private long[] elements;
/*  47 */   private int size = 0;
/*     */   
/*     */   JumboEnumSet(Class<E> paramClass, Enum<?>[] paramArrayOfEnum) {
/*  50 */     super(paramClass, paramArrayOfEnum);
/*  51 */     this.elements = new long[paramArrayOfEnum.length + 63 >>> 6];
/*     */   }
/*     */   
/*     */   void addRange(E paramE1, E paramE2) {
/*  55 */     int i = paramE1.ordinal() >>> 6;
/*  56 */     int j = paramE2.ordinal() >>> 6;
/*     */     
/*  58 */     if (i == j) {
/*  59 */       this.elements[i] = -1L >>> paramE1.ordinal() - paramE2.ordinal() - 1 << paramE1
/*  60 */         .ordinal();
/*     */     } else {
/*  62 */       this.elements[i] = -1L << paramE1.ordinal();
/*  63 */       for (int k = i + 1; k < j; k++)
/*  64 */         this.elements[k] = -1L; 
/*  65 */       this.elements[j] = -1L >>> 63 - paramE2.ordinal();
/*     */     } 
/*  67 */     this.size = paramE2.ordinal() - paramE1.ordinal() + 1;
/*     */   }
/*     */   
/*     */   void addAll() {
/*  71 */     for (byte b = 0; b < this.elements.length; b++)
/*  72 */       this.elements[b] = -1L; 
/*  73 */     this.elements[this.elements.length - 1] = this.elements[this.elements.length - 1] >>> -this.universe.length;
/*  74 */     this.size = this.universe.length;
/*     */   }
/*     */   
/*     */   void complement() {
/*  78 */     for (byte b = 0; b < this.elements.length; b++)
/*  79 */       this.elements[b] = this.elements[b] ^ 0xFFFFFFFFFFFFFFFFL; 
/*  80 */     this.elements[this.elements.length - 1] = this.elements[this.elements.length - 1] & -1L >>> -this.universe.length;
/*  81 */     this.size = this.universe.length - this.size;
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
/*     */   public Iterator<E> iterator() {
/*  94 */     return new EnumSetIterator<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class EnumSetIterator<E extends Enum<E>>
/*     */     implements Iterator<E>
/*     */   {
/*     */     long unseen;
/*     */ 
/*     */ 
/*     */     
/* 107 */     int unseenIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     long lastReturned = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     int lastReturnedIndex = 0;
/*     */     
/*     */     EnumSetIterator() {
/* 121 */       this.unseen = JumboEnumSet.this.elements[0];
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 126 */       while (this.unseen == 0L && this.unseenIndex < JumboEnumSet.this.elements.length - 1)
/* 127 */         this.unseen = JumboEnumSet.this.elements[++this.unseenIndex]; 
/* 128 */       return (this.unseen != 0L);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public E next() {
/* 134 */       if (!hasNext())
/* 135 */         throw new NoSuchElementException(); 
/* 136 */       this.lastReturned = this.unseen & -this.unseen;
/* 137 */       this.lastReturnedIndex = this.unseenIndex;
/* 138 */       this.unseen -= this.lastReturned;
/* 139 */       return 
/* 140 */         (E)JumboEnumSet.this.universe[(this.lastReturnedIndex << 6) + Long.numberOfTrailingZeros(this.lastReturned)];
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 145 */       if (this.lastReturned == 0L)
/* 146 */         throw new IllegalStateException(); 
/* 147 */       long l = JumboEnumSet.this.elements[this.lastReturnedIndex];
/* 148 */       JumboEnumSet.this.elements[this.lastReturnedIndex] = JumboEnumSet.this.elements[this.lastReturnedIndex] & (this.lastReturned ^ 0xFFFFFFFFFFFFFFFFL);
/* 149 */       if (l != JumboEnumSet.this.elements[this.lastReturnedIndex]) {
/* 150 */         JumboEnumSet.this.size--;
/*     */       }
/* 152 */       this.lastReturned = 0L;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 162 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 171 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Object paramObject) {
/* 181 */     if (paramObject == null)
/* 182 */       return false; 
/* 183 */     Class<?> clazz = paramObject.getClass();
/* 184 */     if (clazz != this.elementType && clazz.getSuperclass() != this.elementType) {
/* 185 */       return false;
/*     */     }
/* 187 */     int i = ((Enum)paramObject).ordinal();
/* 188 */     return ((this.elements[i >>> 6] & 1L << i) != 0L);
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
/*     */   public boolean add(E paramE) {
/* 202 */     typeCheck(paramE);
/*     */     
/* 204 */     int i = paramE.ordinal();
/* 205 */     int j = i >>> 6;
/*     */     
/* 207 */     long l = this.elements[j];
/* 208 */     this.elements[j] = this.elements[j] | 1L << i;
/* 209 */     boolean bool = (this.elements[j] != l) ? true : false;
/* 210 */     if (bool)
/* 211 */       this.size++; 
/* 212 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Object paramObject) {
/* 222 */     if (paramObject == null)
/* 223 */       return false; 
/* 224 */     Class<?> clazz = paramObject.getClass();
/* 225 */     if (clazz != this.elementType && clazz.getSuperclass() != this.elementType)
/* 226 */       return false; 
/* 227 */     int i = ((Enum)paramObject).ordinal();
/* 228 */     int j = i >>> 6;
/*     */     
/* 230 */     long l = this.elements[j];
/* 231 */     this.elements[j] = this.elements[j] & (1L << i ^ 0xFFFFFFFFFFFFFFFFL);
/* 232 */     boolean bool = (this.elements[j] != l) ? true : false;
/* 233 */     if (bool)
/* 234 */       this.size--; 
/* 235 */     return bool;
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
/*     */   public boolean containsAll(Collection<?> paramCollection) {
/* 250 */     if (!(paramCollection instanceof JumboEnumSet)) {
/* 251 */       return super.containsAll(paramCollection);
/*     */     }
/* 253 */     JumboEnumSet jumboEnumSet = (JumboEnumSet)paramCollection;
/* 254 */     if (jumboEnumSet.elementType != this.elementType) {
/* 255 */       return jumboEnumSet.isEmpty();
/*     */     }
/* 257 */     for (byte b = 0; b < this.elements.length; b++) {
/* 258 */       if ((jumboEnumSet.elements[b] & (this.elements[b] ^ 0xFFFFFFFFFFFFFFFFL)) != 0L)
/* 259 */         return false; 
/* 260 */     }  return true;
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
/*     */   public boolean addAll(Collection<? extends E> paramCollection) {
/* 272 */     if (!(paramCollection instanceof JumboEnumSet)) {
/* 273 */       return super.addAll(paramCollection);
/*     */     }
/* 275 */     JumboEnumSet jumboEnumSet = (JumboEnumSet)paramCollection;
/* 276 */     if (jumboEnumSet.elementType != this.elementType) {
/* 277 */       if (jumboEnumSet.isEmpty()) {
/* 278 */         return false;
/*     */       }
/* 280 */       throw new ClassCastException(jumboEnumSet.elementType + " != " + this.elementType);
/*     */     } 
/*     */ 
/*     */     
/* 284 */     for (byte b = 0; b < this.elements.length; b++)
/* 285 */       this.elements[b] = this.elements[b] | jumboEnumSet.elements[b]; 
/* 286 */     return recalculateSize();
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
/*     */   public boolean removeAll(Collection<?> paramCollection) {
/* 298 */     if (!(paramCollection instanceof JumboEnumSet)) {
/* 299 */       return super.removeAll(paramCollection);
/*     */     }
/* 301 */     JumboEnumSet jumboEnumSet = (JumboEnumSet)paramCollection;
/* 302 */     if (jumboEnumSet.elementType != this.elementType) {
/* 303 */       return false;
/*     */     }
/* 305 */     for (byte b = 0; b < this.elements.length; b++)
/* 306 */       this.elements[b] = this.elements[b] & (jumboEnumSet.elements[b] ^ 0xFFFFFFFFFFFFFFFFL); 
/* 307 */     return recalculateSize();
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
/*     */   public boolean retainAll(Collection<?> paramCollection) {
/* 319 */     if (!(paramCollection instanceof JumboEnumSet)) {
/* 320 */       return super.retainAll(paramCollection);
/*     */     }
/* 322 */     JumboEnumSet jumboEnumSet = (JumboEnumSet)paramCollection;
/* 323 */     if (jumboEnumSet.elementType != this.elementType) {
/* 324 */       boolean bool = (this.size != 0) ? true : false;
/* 325 */       clear();
/* 326 */       return bool;
/*     */     } 
/*     */     
/* 329 */     for (byte b = 0; b < this.elements.length; b++)
/* 330 */       this.elements[b] = this.elements[b] & jumboEnumSet.elements[b]; 
/* 331 */     return recalculateSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 338 */     Arrays.fill(this.elements, 0L);
/* 339 */     this.size = 0;
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
/*     */   public boolean equals(Object paramObject) {
/* 352 */     if (!(paramObject instanceof JumboEnumSet)) {
/* 353 */       return super.equals(paramObject);
/*     */     }
/* 355 */     JumboEnumSet jumboEnumSet = (JumboEnumSet)paramObject;
/* 356 */     if (jumboEnumSet.elementType != this.elementType) {
/* 357 */       return (this.size == 0 && jumboEnumSet.size == 0);
/*     */     }
/* 359 */     return Arrays.equals(jumboEnumSet.elements, this.elements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean recalculateSize() {
/* 366 */     int i = this.size;
/* 367 */     this.size = 0;
/* 368 */     for (long l : this.elements) {
/* 369 */       this.size += Long.bitCount(l);
/*     */     }
/* 371 */     return (this.size != i);
/*     */   }
/*     */   
/*     */   public EnumSet<E> clone() {
/* 375 */     JumboEnumSet<E> jumboEnumSet = (JumboEnumSet)super.clone();
/* 376 */     jumboEnumSet.elements = (long[])jumboEnumSet.elements.clone();
/* 377 */     return jumboEnumSet;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/JumboEnumSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */