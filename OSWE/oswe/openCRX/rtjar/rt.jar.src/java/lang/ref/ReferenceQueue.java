/*     */ package java.lang.ref;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.util.function.Consumer;
/*     */ import sun.misc.VM;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReferenceQueue<T>
/*     */ {
/*     */   private static class Null<S>
/*     */     extends ReferenceQueue<S>
/*     */   {
/*     */     private Null() {}
/*     */     
/*     */     boolean enqueue(Reference<? extends S> param1Reference) {
/*  47 */       return false;
/*     */     }
/*     */   }
/*     */   
/*  51 */   static ReferenceQueue<Object> NULL = new Null();
/*  52 */   static ReferenceQueue<Object> ENQUEUED = new Null();
/*     */ 
/*     */   
/*  55 */   private Lock lock = new Lock(); private static class Lock {
/*  56 */     private Lock() {} } private volatile Reference<? extends T> head = null;
/*  57 */   private long queueLength = 0L;
/*     */   
/*     */   boolean enqueue(Reference<? extends T> paramReference) {
/*  60 */     synchronized (this.lock) {
/*     */ 
/*     */       
/*  63 */       ReferenceQueue<? super T> referenceQueue = paramReference.queue;
/*  64 */       if (referenceQueue == NULL || referenceQueue == ENQUEUED) {
/*  65 */         return false;
/*     */       }
/*  67 */       assert referenceQueue == this;
/*  68 */       paramReference.queue = (ReferenceQueue)ENQUEUED;
/*  69 */       paramReference.next = (this.head == null) ? paramReference : this.head;
/*  70 */       this.head = paramReference;
/*  71 */       this.queueLength++;
/*  72 */       if (paramReference instanceof FinalReference) {
/*  73 */         VM.addFinalRefCount(1);
/*     */       }
/*  75 */       this.lock.notifyAll();
/*  76 */       return true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Reference<? extends T> reallyPoll() {
/*  81 */     Reference<? extends T> reference = this.head;
/*  82 */     if (reference != null) {
/*     */       
/*  84 */       Reference<? extends T> reference1 = reference.next;
/*  85 */       this.head = (reference1 == reference) ? null : reference1;
/*  86 */       reference.queue = (ReferenceQueue)NULL;
/*  87 */       reference.next = reference;
/*  88 */       this.queueLength--;
/*  89 */       if (reference instanceof FinalReference) {
/*  90 */         VM.addFinalRefCount(-1);
/*     */       }
/*  92 */       return reference;
/*     */     } 
/*  94 */     return null;
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
/*     */   public Reference<? extends T> poll() {
/* 106 */     if (this.head == null)
/* 107 */       return null; 
/* 108 */     synchronized (this.lock) {
/* 109 */       return reallyPoll();
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
/*     */   public Reference<? extends T> remove(long paramLong) throws IllegalArgumentException, InterruptedException {
/* 136 */     if (paramLong < 0L) {
/* 137 */       throw new IllegalArgumentException("Negative timeout value");
/*     */     }
/* 139 */     synchronized (this.lock) {
/* 140 */       Reference<? extends T> reference = reallyPoll();
/* 141 */       if (reference != null) return reference; 
/* 142 */       long l = (paramLong == 0L) ? 0L : System.nanoTime();
/*     */       while (true) {
/* 144 */         this.lock.wait(paramLong);
/* 145 */         reference = reallyPoll();
/* 146 */         if (reference != null) return reference; 
/* 147 */         if (paramLong != 0L) {
/* 148 */           long l1 = System.nanoTime();
/* 149 */           paramLong -= (l1 - l) / 1000000L;
/* 150 */           if (paramLong <= 0L) return null; 
/* 151 */           l = l1;
/*     */         } 
/*     */       } 
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
/*     */   public Reference<? extends T> remove() throws InterruptedException {
/* 165 */     return remove(0L);
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
/*     */   void forEach(Consumer<? super Reference<? extends T>> paramConsumer) {
/* 177 */     for (Reference<? extends T> reference = this.head; reference != null; ) {
/* 178 */       paramConsumer.accept(reference);
/*     */       
/* 180 */       Reference<? extends T> reference1 = reference.next;
/* 181 */       if (reference1 == reference) {
/* 182 */         if (reference.queue == ENQUEUED) {
/*     */           
/* 184 */           reference = null;
/*     */           
/*     */           continue;
/*     */         } 
/* 188 */         reference = this.head;
/*     */         
/*     */         continue;
/*     */       } 
/* 192 */       reference = reference1;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/ref/ReferenceQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */