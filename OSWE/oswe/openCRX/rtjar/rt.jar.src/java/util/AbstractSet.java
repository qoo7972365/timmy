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
/*     */ public abstract class AbstractSet<E>
/*     */   extends AbstractCollection<E>
/*     */   implements Set<E>
/*     */ {
/*     */   public boolean equals(Object paramObject) {
/*  86 */     if (paramObject == this) {
/*  87 */       return true;
/*     */     }
/*  89 */     if (!(paramObject instanceof Set))
/*  90 */       return false; 
/*  91 */     Collection<?> collection = (Collection)paramObject;
/*  92 */     if (collection.size() != size())
/*  93 */       return false; 
/*     */     try {
/*  95 */       return containsAll(collection);
/*  96 */     } catch (ClassCastException classCastException) {
/*  97 */       return false;
/*  98 */     } catch (NullPointerException nullPointerException) {
/*  99 */       return false;
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
/*     */   public int hashCode() {
/* 121 */     int i = 0;
/* 122 */     Iterator<E> iterator = iterator();
/* 123 */     while (iterator.hasNext()) {
/* 124 */       E e = iterator.next();
/* 125 */       if (e != null)
/* 126 */         i += e.hashCode(); 
/*     */     } 
/* 128 */     return i;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> paramCollection) {
/* 169 */     Objects.requireNonNull(paramCollection);
/* 170 */     boolean bool = false;
/*     */     
/* 172 */     if (size() > paramCollection.size()) {
/* 173 */       for (Iterator<?> iterator = paramCollection.iterator(); iterator.hasNext();)
/* 174 */         bool |= remove(iterator.next()); 
/*     */     } else {
/* 176 */       for (Iterator<E> iterator = iterator(); iterator.hasNext();) {
/* 177 */         if (paramCollection.contains(iterator.next())) {
/* 178 */           iterator.remove();
/* 179 */           bool = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 183 */     return bool;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/AbstractSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */