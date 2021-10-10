/*     */ package sun.misc;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Queue<T>
/*     */ {
/*  40 */   int length = 0;
/*     */   
/*  42 */   QueueElement<T> head = null;
/*  43 */   QueueElement<T> tail = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void enqueue(T paramT) {
/*  53 */     QueueElement<T> queueElement = new QueueElement<>(paramT);
/*     */     
/*  55 */     if (this.head == null) {
/*  56 */       this.head = queueElement;
/*  57 */       this.tail = queueElement;
/*  58 */       this.length = 1;
/*     */     } else {
/*  60 */       queueElement.next = this.head;
/*  61 */       this.head.prev = queueElement;
/*  62 */       this.head = queueElement;
/*  63 */       this.length++;
/*     */     } 
/*  65 */     notify();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T dequeue() throws InterruptedException {
/*  76 */     return dequeue(0L);
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
/*     */   public synchronized T dequeue(long paramLong) throws InterruptedException {
/*  91 */     while (this.tail == null) {
/*  92 */       wait(paramLong);
/*     */     }
/*  94 */     QueueElement<T> queueElement = this.tail;
/*  95 */     this.tail = queueElement.prev;
/*  96 */     if (this.tail == null) {
/*  97 */       this.head = null;
/*     */     } else {
/*  99 */       this.tail.next = null;
/*     */     } 
/* 101 */     this.length--;
/* 102 */     return queueElement.obj;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isEmpty() {
/* 110 */     return (this.tail == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized Enumeration<T> elements() {
/* 119 */     return new LIFOQueueEnumerator<>(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized Enumeration<T> reverseElements() {
/* 128 */     return new FIFOQueueEnumerator<>(this);
/*     */   }
/*     */   
/*     */   public synchronized void dump(String paramString) {
/* 132 */     System.err.println(">> " + paramString);
/* 133 */     System.err.println("[" + this.length + " elt(s); head = " + ((this.head == null) ? "null" : (new StringBuilder()).append(this.head.obj).append("").toString()) + " tail = " + ((this.tail == null) ? "null" : (new StringBuilder()).append(this.tail.obj).append("").toString()));
/*     */ 
/*     */     
/* 136 */     QueueElement<T> queueElement1 = this.head;
/* 137 */     QueueElement<T> queueElement2 = null;
/* 138 */     while (queueElement1 != null) {
/* 139 */       System.err.println("  " + queueElement1);
/* 140 */       queueElement2 = queueElement1;
/* 141 */       queueElement1 = queueElement1.next;
/*     */     } 
/* 143 */     if (queueElement2 != this.tail) {
/* 144 */       System.err.println("  tail != last: " + this.tail + ", " + queueElement2);
/*     */     }
/* 146 */     System.err.println("]");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/Queue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */