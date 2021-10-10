/*     */ package java.util;
/*     */ 
/*     */ import java.lang.reflect.Array;
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
/*     */ public abstract class AbstractCollection<E>
/*     */   implements Collection<E>
/*     */ {
/*     */   private static final int MAX_ARRAY_SIZE = 2147483639;
/*     */   
/*     */   public abstract Iterator<E> iterator();
/*     */   
/*     */   public abstract int size();
/*     */   
/*     */   public boolean isEmpty() {
/*  86 */     return (size() == 0);
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
/*     */   public boolean contains(Object paramObject) {
/*  99 */     Iterator<E> iterator = iterator();
/* 100 */     if (paramObject == null)
/* 101 */     { while (iterator.hasNext()) {
/* 102 */         if (iterator.next() == null)
/* 103 */           return true; 
/*     */       }  }
/* 105 */     else { while (iterator.hasNext()) {
/* 106 */         if (paramObject.equals(iterator.next()))
/* 107 */           return true; 
/*     */       }  }
/* 109 */      return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 136 */     Object[] arrayOfObject = new Object[size()];
/* 137 */     Iterator<E> iterator = iterator();
/* 138 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/* 139 */       if (!iterator.hasNext())
/* 140 */         return Arrays.copyOf(arrayOfObject, b); 
/* 141 */       arrayOfObject[b] = iterator.next();
/*     */     } 
/* 143 */     return iterator.hasNext() ? finishToArray(arrayOfObject, iterator) : arrayOfObject;
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
/*     */   public <T> T[] toArray(T[] paramArrayOfT) {
/* 176 */     int i = size();
/*     */ 
/*     */     
/* 179 */     T[] arrayOfT = (T[])((paramArrayOfT.length >= i) ? (Object)paramArrayOfT : Array.newInstance(paramArrayOfT.getClass().getComponentType(), i));
/* 180 */     Iterator<E> iterator = iterator();
/*     */     
/* 182 */     for (byte b = 0; b < arrayOfT.length; b++) {
/* 183 */       if (!iterator.hasNext()) {
/* 184 */         if (paramArrayOfT == arrayOfT)
/* 185 */         { arrayOfT[b] = null; }
/* 186 */         else { if (paramArrayOfT.length < b) {
/* 187 */             return Arrays.copyOf(arrayOfT, b);
/*     */           }
/* 189 */           System.arraycopy(arrayOfT, 0, paramArrayOfT, 0, b);
/* 190 */           if (paramArrayOfT.length > b) {
/* 191 */             paramArrayOfT[b] = null;
/*     */           } }
/*     */         
/* 194 */         return paramArrayOfT;
/*     */       } 
/* 196 */       arrayOfT[b] = (T)iterator.next();
/*     */     } 
/*     */     
/* 199 */     return iterator.hasNext() ? finishToArray(arrayOfT, iterator) : arrayOfT;
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
/*     */ 
/*     */   
/*     */   private static <T> T[] finishToArray(T[] paramArrayOfT, Iterator<?> paramIterator) {
/* 222 */     int i = paramArrayOfT.length;
/* 223 */     while (paramIterator.hasNext()) {
/* 224 */       int j = paramArrayOfT.length;
/* 225 */       if (i == j) {
/* 226 */         int k = j + (j >> 1) + 1;
/*     */         
/* 228 */         if (k - 2147483639 > 0)
/* 229 */           k = hugeCapacity(j + 1); 
/* 230 */         paramArrayOfT = Arrays.copyOf(paramArrayOfT, k);
/*     */       } 
/* 232 */       paramArrayOfT[i++] = (T)paramIterator.next();
/*     */     } 
/*     */     
/* 235 */     return (i == paramArrayOfT.length) ? paramArrayOfT : Arrays.<T>copyOf(paramArrayOfT, i);
/*     */   }
/*     */   
/*     */   private static int hugeCapacity(int paramInt) {
/* 239 */     if (paramInt < 0) {
/* 240 */       throw new OutOfMemoryError("Required array size too large");
/*     */     }
/* 242 */     return (paramInt > 2147483639) ? Integer.MAX_VALUE : 2147483639;
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
/*     */   public boolean add(E paramE) {
/* 262 */     throw new UnsupportedOperationException();
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
/*     */   public boolean remove(Object paramObject) {
/* 282 */     Iterator<E> iterator = iterator();
/* 283 */     if (paramObject == null) {
/* 284 */       while (iterator.hasNext()) {
/* 285 */         if (iterator.next() == null) {
/* 286 */           iterator.remove();
/* 287 */           return true;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 291 */       while (iterator.hasNext()) {
/* 292 */         if (paramObject.equals(iterator.next())) {
/* 293 */           iterator.remove();
/* 294 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 298 */     return false;
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
/*     */   public boolean containsAll(Collection<?> paramCollection) {
/* 317 */     for (Object object : paramCollection) {
/* 318 */       if (!contains(object))
/* 319 */         return false; 
/* 320 */     }  return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends E> paramCollection) {
/*     */     // Byte code:
/*     */     //   0: iconst_0
/*     */     //   1: istore_2
/*     */     //   2: aload_1
/*     */     //   3: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   8: astore_3
/*     */     //   9: aload_3
/*     */     //   10: invokeinterface hasNext : ()Z
/*     */     //   15: ifeq -> 40
/*     */     //   18: aload_3
/*     */     //   19: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   24: astore #4
/*     */     //   26: aload_0
/*     */     //   27: aload #4
/*     */     //   29: invokevirtual add : (Ljava/lang/Object;)Z
/*     */     //   32: ifeq -> 37
/*     */     //   35: iconst_1
/*     */     //   36: istore_2
/*     */     //   37: goto -> 9
/*     */     //   40: iload_2
/*     */     //   41: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #342	-> 0
/*     */     //   #343	-> 2
/*     */     //   #344	-> 26
/*     */     //   #345	-> 35
/*     */     //   #344	-> 37
/*     */     //   #346	-> 40
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> paramCollection) {
/* 371 */     Objects.requireNonNull(paramCollection);
/* 372 */     boolean bool = false;
/* 373 */     Iterator<E> iterator = iterator();
/* 374 */     while (iterator.hasNext()) {
/* 375 */       if (paramCollection.contains(iterator.next())) {
/* 376 */         iterator.remove();
/* 377 */         bool = true;
/*     */       } 
/*     */     } 
/* 380 */     return bool;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> paramCollection) {
/* 405 */     Objects.requireNonNull(paramCollection);
/* 406 */     boolean bool = false;
/* 407 */     Iterator<E> iterator = iterator();
/* 408 */     while (iterator.hasNext()) {
/* 409 */       if (!paramCollection.contains(iterator.next())) {
/* 410 */         iterator.remove();
/* 411 */         bool = true;
/*     */       } 
/*     */     } 
/* 414 */     return bool;
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
/*     */   public void clear() {
/* 433 */     Iterator<E> iterator = iterator();
/* 434 */     while (iterator.hasNext()) {
/* 435 */       iterator.next();
/* 436 */       iterator.remove();
/*     */     } 
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
/*     */   public String toString() {
/* 454 */     Iterator<E> iterator = iterator();
/* 455 */     if (!iterator.hasNext()) {
/* 456 */       return "[]";
/*     */     }
/* 458 */     StringBuilder stringBuilder = new StringBuilder();
/* 459 */     stringBuilder.append('[');
/*     */     while (true) {
/* 461 */       E e = iterator.next();
/* 462 */       stringBuilder.append((e == this) ? "(this Collection)" : e);
/* 463 */       if (!iterator.hasNext())
/* 464 */         return stringBuilder.append(']').toString(); 
/* 465 */       stringBuilder.append(',').append(' ');
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/AbstractCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */