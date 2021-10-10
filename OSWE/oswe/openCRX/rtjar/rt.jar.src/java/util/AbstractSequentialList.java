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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSequentialList<E>
/*     */   extends AbstractList<E>
/*     */ {
/*     */   public E get(int paramInt) {
/*     */     try {
/*  88 */       return listIterator(paramInt).next();
/*  89 */     } catch (NoSuchElementException noSuchElementException) {
/*  90 */       throw new IndexOutOfBoundsException("Index: " + paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E set(int paramInt, E paramE) {
/*     */     try {
/* 115 */       ListIterator<E> listIterator = listIterator(paramInt);
/* 116 */       E e = listIterator.next();
/* 117 */       listIterator.set(paramE);
/* 118 */       return e;
/* 119 */     } catch (NoSuchElementException noSuchElementException) {
/* 120 */       throw new IndexOutOfBoundsException("Index: " + paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int paramInt, E paramE) {
/*     */     try {
/* 146 */       listIterator(paramInt).add(paramE);
/* 147 */     } catch (NoSuchElementException noSuchElementException) {
/* 148 */       throw new IndexOutOfBoundsException("Index: " + paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E remove(int paramInt) {
/*     */     try {
/* 171 */       ListIterator<E> listIterator = listIterator(paramInt);
/* 172 */       E e = listIterator.next();
/* 173 */       listIterator.remove();
/* 174 */       return e;
/* 175 */     } catch (NoSuchElementException noSuchElementException) {
/* 176 */       throw new IndexOutOfBoundsException("Index: " + paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(int paramInt, Collection<? extends E> paramCollection) {
/*     */     try {
/* 214 */       boolean bool = false;
/* 215 */       ListIterator<E> listIterator = listIterator(paramInt);
/* 216 */       Iterator<? extends E> iterator = paramCollection.iterator();
/* 217 */       while (iterator.hasNext()) {
/* 218 */         listIterator.add(iterator.next());
/* 219 */         bool = true;
/*     */       } 
/* 221 */       return bool;
/* 222 */     } catch (NoSuchElementException noSuchElementException) {
/* 223 */       throw new IndexOutOfBoundsException("Index: " + paramInt);
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
/*     */   public Iterator<E> iterator() {
/* 239 */     return listIterator();
/*     */   }
/*     */   
/*     */   public abstract ListIterator<E> listIterator(int paramInt);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/AbstractSequentialList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */