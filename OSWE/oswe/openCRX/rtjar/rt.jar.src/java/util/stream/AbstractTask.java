/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.Spliterator;
/*     */ import java.util.concurrent.CountedCompleter;
/*     */ import java.util.concurrent.ForkJoinPool;
/*     */ import java.util.concurrent.ForkJoinWorkerThread;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractTask<P_IN, P_OUT, R, K extends AbstractTask<P_IN, P_OUT, R, K>>
/*     */   extends CountedCompleter<R>
/*     */ {
/*  92 */   private static final int LEAF_TARGET = ForkJoinPool.getCommonPoolParallelism() << 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final PipelineHelper<P_OUT> helper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Spliterator<P_IN> spliterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long targetSize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected K leftChild;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected K rightChild;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private R localResult;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractTask(PipelineHelper<P_OUT> paramPipelineHelper, Spliterator<P_IN> paramSpliterator) {
/* 133 */     super((CountedCompleter<?>)null);
/* 134 */     this.helper = paramPipelineHelper;
/* 135 */     this.spliterator = paramSpliterator;
/* 136 */     this.targetSize = 0L;
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
/*     */   protected AbstractTask(K paramK, Spliterator<P_IN> paramSpliterator) {
/* 148 */     super((CountedCompleter<?>)paramK);
/* 149 */     this.spliterator = paramSpliterator;
/* 150 */     this.helper = ((AbstractTask)paramK).helper;
/* 151 */     this.targetSize = ((AbstractTask)paramK).targetSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLeafTarget() {
/* 161 */     Thread thread = Thread.currentThread();
/* 162 */     if (thread instanceof ForkJoinWorkerThread) {
/* 163 */       return ((ForkJoinWorkerThread)thread).getPool().getParallelism() << 2;
/*     */     }
/*     */     
/* 166 */     return LEAF_TARGET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract K makeChild(Spliterator<P_IN> paramSpliterator);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract R doLeaf();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long suggestTargetSize(long paramLong) {
/* 195 */     long l = paramLong / getLeafTarget();
/* 196 */     return (l > 0L) ? l : 1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final long getTargetSize(long paramLong) {
/*     */     long l;
/* 205 */     return ((l = this.targetSize) != 0L) ? l : (this
/* 206 */       .targetSize = suggestTargetSize(paramLong));
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
/*     */   public R getRawResult() {
/* 220 */     return this.localResult;
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
/*     */   protected void setRawResult(R paramR) {
/* 233 */     if (paramR != null) {
/* 234 */       throw new IllegalStateException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected R getLocalResult() {
/* 244 */     return this.localResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setLocalResult(R paramR) {
/* 254 */     this.localResult = paramR;
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
/*     */   protected boolean isLeaf() {
/* 266 */     return (this.leftChild == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isRoot() {
/* 275 */     return (getParent() == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected K getParent() {
/* 285 */     return (K)getCompleter();
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
/*     */   public void compute() {
/* 303 */     Spliterator<P_IN> spliterator1 = this.spliterator;
/* 304 */     long l1 = spliterator1.estimateSize();
/* 305 */     long l2 = getTargetSize(l1);
/* 306 */     boolean bool = false;
/* 307 */     Object object = this; Spliterator<P_IN> spliterator2;
/* 308 */     while (l1 > l2 && (spliterator2 = spliterator1.trySplit()) != null) {
/*     */       
/* 310 */       Object object3, object1 = object.makeChild(spliterator2);
/* 311 */       Object object2 = object.makeChild(spliterator1);
/* 312 */       object.setPendingCount(1);
/* 313 */       if (bool) {
/* 314 */         bool = false;
/* 315 */         spliterator1 = spliterator2;
/* 316 */         Object object4 = object1;
/* 317 */         object3 = object2;
/*     */       } else {
/*     */         
/* 320 */         bool = true;
/* 321 */         object = object2;
/* 322 */         object3 = object1;
/*     */       } 
/* 324 */       object3.fork();
/* 325 */       l1 = spliterator1.estimateSize();
/*     */     } 
/* 327 */     object.setLocalResult(object.doLeaf());
/* 328 */     object.tryComplete();
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
/*     */   public void onCompletion(CountedCompleter<?> paramCountedCompleter) {
/* 341 */     this.spliterator = null;
/* 342 */     this.leftChild = this.rightChild = null;
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
/*     */   protected boolean isLeftmostNode() {
/* 354 */     AbstractTask abstractTask = this;
/* 355 */     while (abstractTask != null) {
/* 356 */       Object object2 = abstractTask.getParent();
/* 357 */       if (object2 != null && ((AbstractTask)object2).leftChild != abstractTask)
/* 358 */         return false; 
/* 359 */       Object object1 = object2;
/*     */     } 
/* 361 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/stream/AbstractTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */