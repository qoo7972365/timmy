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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractQueue<E>
/*     */   extends AbstractCollection<E>
/*     */   implements Queue<E>
/*     */ {
/*     */   public boolean add(E paramE) {
/*  95 */     if (offer(paramE)) {
/*  96 */       return true;
/*     */     }
/*  98 */     throw new IllegalStateException("Queue full");
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
/*     */   public E remove() {
/* 113 */     E e = poll();
/* 114 */     if (e != null) {
/* 115 */       return e;
/*     */     }
/* 117 */     throw new NoSuchElementException();
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
/*     */   public E element() {
/* 132 */     E e = peek();
/* 133 */     if (e != null) {
/* 134 */       return e;
/*     */     }
/* 136 */     throw new NoSuchElementException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 147 */     while (poll() != null);
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends E> paramCollection) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: ifnonnull -> 12
/*     */     //   4: new java/lang/NullPointerException
/*     */     //   7: dup
/*     */     //   8: invokespecial <init> : ()V
/*     */     //   11: athrow
/*     */     //   12: aload_1
/*     */     //   13: aload_0
/*     */     //   14: if_acmpne -> 25
/*     */     //   17: new java/lang/IllegalArgumentException
/*     */     //   20: dup
/*     */     //   21: invokespecial <init> : ()V
/*     */     //   24: athrow
/*     */     //   25: iconst_0
/*     */     //   26: istore_2
/*     */     //   27: aload_1
/*     */     //   28: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   33: astore_3
/*     */     //   34: aload_3
/*     */     //   35: invokeinterface hasNext : ()Z
/*     */     //   40: ifeq -> 65
/*     */     //   43: aload_3
/*     */     //   44: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   49: astore #4
/*     */     //   51: aload_0
/*     */     //   52: aload #4
/*     */     //   54: invokevirtual add : (Ljava/lang/Object;)Z
/*     */     //   57: ifeq -> 62
/*     */     //   60: iconst_1
/*     */     //   61: istore_2
/*     */     //   62: goto -> 34
/*     */     //   65: iload_2
/*     */     //   66: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #181	-> 0
/*     */     //   #182	-> 4
/*     */     //   #183	-> 12
/*     */     //   #184	-> 17
/*     */     //   #185	-> 25
/*     */     //   #186	-> 27
/*     */     //   #187	-> 51
/*     */     //   #188	-> 60
/*     */     //   #187	-> 62
/*     */     //   #189	-> 65
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/AbstractQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */