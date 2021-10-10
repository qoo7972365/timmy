/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Spliterator;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractShortCircuitTask<P_IN, P_OUT, R, K extends AbstractShortCircuitTask<P_IN, P_OUT, R, K>>
/*     */   extends AbstractTask<P_IN, P_OUT, R, K>
/*     */ {
/*     */   protected final AtomicReference<R> sharedResult;
/*     */   protected volatile boolean canceled;
/*     */   
/*     */   protected AbstractShortCircuitTask(PipelineHelper<P_OUT> paramPipelineHelper, Spliterator<P_IN> paramSpliterator) {
/*  70 */     super(paramPipelineHelper, paramSpliterator);
/*  71 */     this.sharedResult = new AtomicReference<>(null);
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
/*     */   protected AbstractShortCircuitTask(K paramK, Spliterator<P_IN> paramSpliterator) {
/*  83 */     super(paramK, paramSpliterator);
/*  84 */     this.sharedResult = ((AbstractShortCircuitTask)paramK).sharedResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract R getEmptyResult();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compute() {
/* 102 */     Spliterator<P_IN> spliterator = this.spliterator;
/* 103 */     long l1 = spliterator.estimateSize();
/* 104 */     long l2 = getTargetSize(l1);
/* 105 */     boolean bool = false;
/* 106 */     AbstractShortCircuitTask abstractShortCircuitTask = this;
/* 107 */     AtomicReference<R> atomicReference = this.sharedResult;
/*     */     R r;
/* 109 */     while ((r = atomicReference.get()) == null) {
/* 110 */       AbstractShortCircuitTask abstractShortCircuitTask3; if (abstractShortCircuitTask.taskCanceled()) {
/* 111 */         r = (R)abstractShortCircuitTask.getEmptyResult(); break;
/*     */       } 
/*     */       Spliterator<P_IN> spliterator1;
/* 114 */       if (l1 <= l2 || (spliterator1 = spliterator.trySplit()) == null) {
/* 115 */         r = (R)abstractShortCircuitTask.doLeaf();
/*     */         
/*     */         break;
/*     */       } 
/* 119 */       AbstractShortCircuitTask abstractShortCircuitTask1 = (AbstractShortCircuitTask)abstractShortCircuitTask.makeChild(spliterator1);
/* 120 */       AbstractShortCircuitTask abstractShortCircuitTask2 = (AbstractShortCircuitTask)abstractShortCircuitTask.makeChild(spliterator);
/* 121 */       abstractShortCircuitTask.setPendingCount(1);
/* 122 */       if (bool) {
/* 123 */         bool = false;
/* 124 */         spliterator = spliterator1;
/* 125 */         abstractShortCircuitTask = abstractShortCircuitTask1;
/* 126 */         abstractShortCircuitTask3 = abstractShortCircuitTask2;
/*     */       } else {
/*     */         
/* 129 */         bool = true;
/* 130 */         abstractShortCircuitTask = abstractShortCircuitTask2;
/* 131 */         abstractShortCircuitTask3 = abstractShortCircuitTask1;
/*     */       } 
/* 133 */       abstractShortCircuitTask3.fork();
/* 134 */       l1 = spliterator.estimateSize();
/*     */     } 
/* 136 */     abstractShortCircuitTask.setLocalResult(r);
/* 137 */     abstractShortCircuitTask.tryComplete();
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
/*     */   protected void shortCircuit(R paramR) {
/* 151 */     if (paramR != null) {
/* 152 */       this.sharedResult.compareAndSet(null, paramR);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setLocalResult(R paramR) {
/* 163 */     if (isRoot()) {
/* 164 */       if (paramR != null) {
/* 165 */         this.sharedResult.compareAndSet(null, paramR);
/*     */       }
/*     */     } else {
/* 168 */       super.setLocalResult(paramR);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R getRawResult() {
/* 176 */     return getLocalResult();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public R getLocalResult() {
/* 185 */     if (isRoot()) {
/* 186 */       R r = this.sharedResult.get();
/* 187 */       return (r == null) ? getEmptyResult() : r;
/*     */     } 
/*     */     
/* 190 */     return super.getLocalResult();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void cancel() {
/* 197 */     this.canceled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean taskCanceled() {
/* 207 */     boolean bool = this.canceled;
/* 208 */     if (!bool) {
/* 209 */       for (AbstractShortCircuitTask abstractShortCircuitTask = (AbstractShortCircuitTask)getParent(); !bool && abstractShortCircuitTask != null; abstractShortCircuitTask = (AbstractShortCircuitTask)abstractShortCircuitTask.getParent()) {
/* 210 */         bool = abstractShortCircuitTask.canceled;
/*     */       }
/*     */     }
/* 213 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void cancelLaterNodes() {
/* 223 */     AbstractShortCircuitTask abstractShortCircuitTask1 = (AbstractShortCircuitTask)getParent(), abstractShortCircuitTask2 = this;
/* 224 */     for (; abstractShortCircuitTask1 != null; 
/* 225 */       abstractShortCircuitTask2 = abstractShortCircuitTask1, abstractShortCircuitTask1 = (AbstractShortCircuitTask)abstractShortCircuitTask1.getParent()) {
/*     */       
/* 227 */       if (abstractShortCircuitTask1.leftChild == abstractShortCircuitTask2) {
/* 228 */         AbstractShortCircuitTask abstractShortCircuitTask = (AbstractShortCircuitTask)abstractShortCircuitTask1.rightChild;
/* 229 */         if (!abstractShortCircuitTask.canceled)
/* 230 */           abstractShortCircuitTask.cancel(); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/AbstractShortCircuitTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */