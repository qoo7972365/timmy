/*     */ package jdk.internal.org.objectweb.asm.tree.analysis;
/*     */ 
/*     */ import java.util.AbstractSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SmallSet<E>
/*     */   extends AbstractSet<E>
/*     */   implements Iterator<E>
/*     */ {
/*     */   E e1;
/*     */   E e2;
/*     */   
/*     */   static final <T> Set<T> emptySet() {
/*  79 */     return new SmallSet<>(null, null);
/*     */   }
/*     */   
/*     */   SmallSet(E paramE1, E paramE2) {
/*  83 */     this.e1 = paramE1;
/*  84 */     this.e2 = paramE2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<E> iterator() {
/*  93 */     return new SmallSet(this.e1, this.e2);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  98 */     return (this.e1 == null) ? 0 : ((this.e2 == null) ? 1 : 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/* 106 */     return (this.e1 != null);
/*     */   }
/*     */   
/*     */   public E next() {
/* 110 */     if (this.e1 == null) {
/* 111 */       throw new NoSuchElementException();
/*     */     }
/* 113 */     E e = this.e1;
/* 114 */     this.e1 = this.e2;
/* 115 */     this.e2 = null;
/* 116 */     return e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove() {}
/*     */ 
/*     */ 
/*     */   
/*     */   Set<E> union(SmallSet<E> paramSmallSet) {
/* 127 */     if ((paramSmallSet.e1 == this.e1 && paramSmallSet.e2 == this.e2) || (paramSmallSet.e1 == this.e2 && paramSmallSet.e2 == this.e1)) {
/* 128 */       return this;
/*     */     }
/* 130 */     if (paramSmallSet.e1 == null) {
/* 131 */       return this;
/*     */     }
/* 133 */     if (this.e1 == null) {
/* 134 */       return paramSmallSet;
/*     */     }
/* 136 */     if (paramSmallSet.e2 == null) {
/* 137 */       if (this.e2 == null)
/* 138 */         return new SmallSet(this.e1, paramSmallSet.e1); 
/* 139 */       if (paramSmallSet.e1 == this.e1 || paramSmallSet.e1 == this.e2) {
/* 140 */         return this;
/*     */       }
/*     */     } 
/* 143 */     if (this.e2 == null)
/*     */     {
/*     */ 
/*     */       
/* 147 */       if (this.e1 == paramSmallSet.e1 || this.e1 == paramSmallSet.e2) {
/* 148 */         return paramSmallSet;
/*     */       }
/*     */     }
/*     */     
/* 152 */     HashSet<E> hashSet = new HashSet(4);
/* 153 */     hashSet.add(this.e1);
/* 154 */     if (this.e2 != null) {
/* 155 */       hashSet.add(this.e2);
/*     */     }
/* 157 */     hashSet.add(paramSmallSet.e1);
/* 158 */     if (paramSmallSet.e2 != null) {
/* 159 */       hashSet.add(paramSmallSet.e2);
/*     */     }
/* 161 */     return hashSet;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/analysis/SmallSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */