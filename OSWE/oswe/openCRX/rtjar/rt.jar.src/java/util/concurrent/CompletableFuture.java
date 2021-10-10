/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.util.concurrent.locks.LockSupport;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Supplier;
/*      */ import sun.misc.Unsafe;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CompletableFuture<T>
/*      */   implements Future<T>, CompletionStage<T>
/*      */ {
/*      */   volatile Object result;
/*      */   volatile Completion stack;
/*      */   
/*      */   final boolean internalComplete(Object paramObject) {
/*  222 */     return UNSAFE.compareAndSwapObject(this, RESULT, null, paramObject);
/*      */   }
/*      */   
/*      */   final boolean casStack(Completion paramCompletion1, Completion paramCompletion2) {
/*  226 */     return UNSAFE.compareAndSwapObject(this, STACK, paramCompletion1, paramCompletion2);
/*      */   }
/*      */ 
/*      */   
/*      */   final boolean tryPushStack(Completion paramCompletion) {
/*  231 */     Completion completion = this.stack;
/*  232 */     lazySetNext(paramCompletion, completion);
/*  233 */     return UNSAFE.compareAndSwapObject(this, STACK, completion, paramCompletion);
/*      */   }
/*      */   final void pushStack(Completion paramCompletion) {
/*      */     do {
/*      */     
/*  238 */     } while (!tryPushStack(paramCompletion));
/*      */   }
/*      */   
/*      */   static final class AltResult {
/*      */     final Throwable ex;
/*      */     
/*      */     AltResult(Throwable param1Throwable) {
/*  245 */       this.ex = param1Throwable;
/*      */     }
/*      */   }
/*      */   
/*  249 */   static final AltResult NIL = new AltResult(null);
/*      */ 
/*      */   
/*      */   final boolean completeNull() {
/*  253 */     return UNSAFE.compareAndSwapObject(this, RESULT, null, NIL);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final Object encodeValue(T paramT) {
/*  259 */     return (paramT == null) ? NIL : paramT;
/*      */   }
/*      */ 
/*      */   
/*      */   final boolean completeValue(T paramT) {
/*  264 */     return UNSAFE.compareAndSwapObject(this, RESULT, null, (paramT == null) ? NIL : paramT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static AltResult encodeThrowable(Throwable paramThrowable) {
/*  273 */     return new AltResult((paramThrowable instanceof CompletionException) ? paramThrowable : new CompletionException(paramThrowable));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean completeThrowable(Throwable paramThrowable) {
/*  279 */     return UNSAFE.compareAndSwapObject(this, RESULT, null, 
/*  280 */         encodeThrowable(paramThrowable));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Object encodeThrowable(Throwable paramThrowable, Object paramObject) {
/*  291 */     if (!(paramThrowable instanceof CompletionException)) {
/*  292 */       paramThrowable = new CompletionException(paramThrowable);
/*  293 */     } else if (paramObject instanceof AltResult && paramThrowable == ((AltResult)paramObject).ex) {
/*  294 */       return paramObject;
/*  295 */     }  return new AltResult(paramThrowable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean completeThrowable(Throwable paramThrowable, Object paramObject) {
/*  307 */     return UNSAFE.compareAndSwapObject(this, RESULT, null, 
/*  308 */         encodeThrowable(paramThrowable, paramObject));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Object encodeOutcome(T paramT, Throwable paramThrowable) {
/*  317 */     return (paramThrowable == null) ? ((paramT == null) ? NIL : paramT) : encodeThrowable(paramThrowable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Object encodeRelay(Object paramObject) {
/*      */     Throwable throwable;
/*  326 */     return (paramObject instanceof AltResult && (throwable = ((AltResult)paramObject).ex) != null && !(throwable instanceof CompletionException)) ? new AltResult(new CompletionException(throwable)) : paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean completeRelay(Object paramObject) {
/*  337 */     return UNSAFE.compareAndSwapObject(this, RESULT, null, 
/*  338 */         encodeRelay(paramObject));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> T reportGet(Object paramObject) throws InterruptedException, ExecutionException {
/*  346 */     if (paramObject == null)
/*  347 */       throw new InterruptedException(); 
/*  348 */     if (paramObject instanceof AltResult) {
/*      */       Throwable throwable1;
/*  350 */       if ((throwable1 = ((AltResult)paramObject).ex) == null)
/*  351 */         return null; 
/*  352 */       if (throwable1 instanceof CancellationException)
/*  353 */         throw (CancellationException)throwable1;  Throwable throwable2;
/*  354 */       if (throwable1 instanceof CompletionException && (
/*  355 */         throwable2 = throwable1.getCause()) != null)
/*  356 */         throwable1 = throwable2; 
/*  357 */       throw new ExecutionException(throwable1);
/*      */     } 
/*  359 */     return (T)paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> T reportJoin(Object paramObject) {
/*  367 */     if (paramObject instanceof AltResult) {
/*      */       Throwable throwable;
/*  369 */       if ((throwable = ((AltResult)paramObject).ex) == null)
/*  370 */         return null; 
/*  371 */       if (throwable instanceof CancellationException)
/*  372 */         throw (CancellationException)throwable; 
/*  373 */       if (throwable instanceof CompletionException)
/*  374 */         throw (CompletionException)throwable; 
/*  375 */       throw new CompletionException(throwable);
/*      */     } 
/*  377 */     return (T)paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  394 */   private static final boolean useCommonPool = (ForkJoinPool.getCommonPoolParallelism() > 1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  400 */   private static final Executor asyncPool = useCommonPool ? 
/*  401 */     ForkJoinPool.commonPool() : new ThreadPerTaskExecutor(); static final int SYNC = 0; static final int ASYNC = 1; static final int NESTED = -1;
/*      */   public static interface AsynchronousCompletionTask {}
/*      */   
/*      */   static final class ThreadPerTaskExecutor implements Executor { public void execute(Runnable param1Runnable) {
/*  405 */       (new Thread(param1Runnable)).start();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Executor screenExecutor(Executor paramExecutor) {
/*  413 */     if (!useCommonPool && paramExecutor == ForkJoinPool.commonPool())
/*  414 */       return asyncPool; 
/*  415 */     if (paramExecutor == null) throw new NullPointerException(); 
/*  416 */     return paramExecutor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  435 */   private static final int SPINS = (Runtime.getRuntime().availableProcessors() > 1) ? 256 : 0;
/*      */ 
/*      */   
/*      */   private static final Unsafe UNSAFE;
/*      */ 
/*      */   
/*      */   private static final long RESULT;
/*      */   
/*      */   private static final long STACK;
/*      */   
/*      */   private static final long NEXT;
/*      */ 
/*      */   
/*      */   static abstract class Completion
/*      */     extends ForkJoinTask<Void>
/*      */     implements Runnable, AsynchronousCompletionTask
/*      */   {
/*      */     volatile Completion next;
/*      */ 
/*      */     
/*      */     public final void run() {
/*  456 */       tryFire(1);
/*  457 */     } public final boolean exec() { tryFire(1); return true; } public final Void getRawResult() {
/*  458 */       return null;
/*      */     } public final void setRawResult(Void param1Void) {}
/*      */     abstract CompletableFuture<?> tryFire(int param1Int);
/*      */     abstract boolean isLive(); }
/*      */   static void lazySetNext(Completion paramCompletion1, Completion paramCompletion2) {
/*  463 */     UNSAFE.putOrderedObject(paramCompletion1, NEXT, paramCompletion2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void postComplete() {
/*  476 */     CompletableFuture completableFuture = this; Completion completion;
/*  477 */     while ((completion = completableFuture.stack) != null || (completableFuture != this && (completion = (completableFuture = this).stack) != null)) {
/*      */       Completion completion1;
/*      */       
/*  480 */       if (completableFuture.casStack(completion, completion1 = completion.next)) {
/*  481 */         if (completion1 != null) {
/*  482 */           if (completableFuture != this) {
/*  483 */             pushStack(completion);
/*      */             continue;
/*      */           } 
/*  486 */           completion.next = null;
/*      */         }  CompletableFuture<?> completableFuture1;
/*  488 */         completableFuture = ((completableFuture1 = completion.tryFire(-1)) == null) ? this : completableFuture1;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   final void cleanStack() {
/*  495 */     for (Completion completion1 = null, completion2 = this.stack; completion2 != null; ) {
/*  496 */       Completion completion = completion2.next;
/*  497 */       if (completion2.isLive()) {
/*  498 */         completion1 = completion2;
/*  499 */         completion2 = completion; continue;
/*      */       } 
/*  501 */       if (completion1 == null) {
/*  502 */         casStack(completion2, completion);
/*  503 */         completion2 = this.stack;
/*      */         continue;
/*      */       } 
/*  506 */       completion1.next = completion;
/*  507 */       if (completion1.isLive()) {
/*  508 */         completion2 = completion; continue;
/*      */       } 
/*  510 */       completion1 = null;
/*  511 */       completion2 = this.stack;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class UniCompletion<T, V>
/*      */     extends Completion
/*      */   {
/*      */     Executor executor;
/*      */     
/*      */     CompletableFuture<V> dep;
/*      */     
/*      */     CompletableFuture<T> src;
/*      */ 
/*      */     
/*      */     UniCompletion(Executor param1Executor, CompletableFuture<V> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1) {
/*  528 */       this.executor = param1Executor; this.dep = param1CompletableFuture; this.src = param1CompletableFuture1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean claim() {
/*  538 */       Executor executor = this.executor;
/*  539 */       if (compareAndSetForkJoinTaskTag((short)0, (short)1)) {
/*  540 */         if (executor == null)
/*  541 */           return true; 
/*  542 */         this.executor = null;
/*  543 */         executor.execute(this);
/*      */       } 
/*  545 */       return false;
/*      */     }
/*      */     final boolean isLive() {
/*  548 */       return (this.dep != null);
/*      */     }
/*      */   }
/*      */   
/*      */   final void push(UniCompletion<?, ?> paramUniCompletion) {
/*  553 */     if (paramUniCompletion != null) {
/*  554 */       while (this.result == null && !tryPushStack(paramUniCompletion)) {
/*  555 */         lazySetNext(paramUniCompletion, null);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final CompletableFuture<T> postFire(CompletableFuture<?> paramCompletableFuture, int paramInt) {
/*  565 */     if (paramCompletableFuture != null && paramCompletableFuture.stack != null)
/*  566 */       if (paramInt < 0 || paramCompletableFuture.result == null) {
/*  567 */         paramCompletableFuture.cleanStack();
/*      */       } else {
/*  569 */         paramCompletableFuture.postComplete();
/*      */       }  
/*  571 */     if (this.result != null && this.stack != null) {
/*  572 */       if (paramInt < 0) {
/*  573 */         return this;
/*      */       }
/*  575 */       postComplete();
/*      */     } 
/*  577 */     return null;
/*      */   }
/*      */   
/*      */   static final class UniApply<T, V>
/*      */     extends UniCompletion<T, V>
/*      */   {
/*      */     Function<? super T, ? extends V> fn;
/*      */     
/*      */     UniApply(Executor param1Executor, CompletableFuture<V> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, Function<? super T, ? extends V> param1Function) {
/*  586 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1); this.fn = param1Function;
/*      */     } final CompletableFuture<V> tryFire(int param1Int) {
/*      */       CompletableFuture<V> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*  590 */       if ((completableFuture = this.dep) == null || 
/*  591 */         !completableFuture.uniApply(completableFuture1 = this.src, this.fn, (param1Int > 0) ? null : this))
/*  592 */         return null; 
/*  593 */       this.dep = null; this.src = null; this.fn = null;
/*  594 */       return completableFuture.postFire(completableFuture1, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final <S> boolean uniApply(CompletableFuture<S> paramCompletableFuture, Function<? super S, ? extends T> paramFunction, UniApply<S, T> paramUniApply) {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: ifnull -> 18
/*      */     //   4: aload_1
/*      */     //   5: getfield result : Ljava/lang/Object;
/*      */     //   8: dup
/*      */     //   9: astore #4
/*      */     //   11: ifnull -> 18
/*      */     //   14: aload_2
/*      */     //   15: ifnonnull -> 20
/*      */     //   18: iconst_0
/*      */     //   19: ireturn
/*      */     //   20: aload_0
/*      */     //   21: getfield result : Ljava/lang/Object;
/*      */     //   24: ifnonnull -> 106
/*      */     //   27: aload #4
/*      */     //   29: instanceof java/util/concurrent/CompletableFuture$AltResult
/*      */     //   32: ifeq -> 64
/*      */     //   35: aload #4
/*      */     //   37: checkcast java/util/concurrent/CompletableFuture$AltResult
/*      */     //   40: getfield ex : Ljava/lang/Throwable;
/*      */     //   43: dup
/*      */     //   44: astore #5
/*      */     //   46: ifnull -> 61
/*      */     //   49: aload_0
/*      */     //   50: aload #5
/*      */     //   52: aload #4
/*      */     //   54: invokevirtual completeThrowable : (Ljava/lang/Throwable;Ljava/lang/Object;)Z
/*      */     //   57: pop
/*      */     //   58: goto -> 106
/*      */     //   61: aconst_null
/*      */     //   62: astore #4
/*      */     //   64: aload_3
/*      */     //   65: ifnull -> 77
/*      */     //   68: aload_3
/*      */     //   69: invokevirtual claim : ()Z
/*      */     //   72: ifne -> 77
/*      */     //   75: iconst_0
/*      */     //   76: ireturn
/*      */     //   77: aload #4
/*      */     //   79: astore #6
/*      */     //   81: aload_0
/*      */     //   82: aload_2
/*      */     //   83: aload #6
/*      */     //   85: invokeinterface apply : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   90: invokevirtual completeValue : (Ljava/lang/Object;)Z
/*      */     //   93: pop
/*      */     //   94: goto -> 106
/*      */     //   97: astore #6
/*      */     //   99: aload_0
/*      */     //   100: aload #6
/*      */     //   102: invokevirtual completeThrowable : (Ljava/lang/Throwable;)Z
/*      */     //   105: pop
/*      */     //   106: iconst_1
/*      */     //   107: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #602	-> 0
/*      */     //   #603	-> 18
/*      */     //   #604	-> 20
/*      */     //   #605	-> 27
/*      */     //   #606	-> 35
/*      */     //   #607	-> 49
/*      */     //   #608	-> 58
/*      */     //   #610	-> 61
/*      */     //   #613	-> 64
/*      */     //   #614	-> 75
/*      */     //   #615	-> 77
/*      */     //   #616	-> 81
/*      */     //   #619	-> 94
/*      */     //   #617	-> 97
/*      */     //   #618	-> 99
/*      */     //   #621	-> 106
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   64	76	97	java/lang/Throwable
/*      */     //   77	94	97	java/lang/Throwable
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <V> CompletableFuture<V> uniApplyStage(Executor paramExecutor, Function<? super T, ? extends V> paramFunction) {
/*  626 */     if (paramFunction == null) throw new NullPointerException(); 
/*  627 */     CompletableFuture<V> completableFuture = new CompletableFuture();
/*  628 */     if (paramExecutor != null || !completableFuture.uniApply(this, paramFunction, null)) {
/*  629 */       UniApply<T, V> uniApply = new UniApply<>(paramExecutor, completableFuture, this, paramFunction);
/*  630 */       push(uniApply);
/*  631 */       uniApply.tryFire(0);
/*      */     } 
/*  633 */     return completableFuture;
/*      */   }
/*      */   
/*      */   static final class UniAccept<T>
/*      */     extends UniCompletion<T, Void> {
/*      */     Consumer<? super T> fn;
/*      */     
/*      */     UniAccept(Executor param1Executor, CompletableFuture<Void> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, Consumer<? super T> param1Consumer) {
/*  641 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1); this.fn = param1Consumer;
/*      */     } final CompletableFuture<Void> tryFire(int param1Int) {
/*      */       CompletableFuture<Void> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*  645 */       if ((completableFuture = this.dep) == null || 
/*  646 */         !completableFuture.uniAccept(completableFuture1 = this.src, this.fn, (param1Int > 0) ? null : this))
/*  647 */         return null; 
/*  648 */       this.dep = null; this.src = null; this.fn = null;
/*  649 */       return completableFuture.postFire(completableFuture1, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final <S> boolean uniAccept(CompletableFuture<S> paramCompletableFuture, Consumer<? super S> paramConsumer, UniAccept<S> paramUniAccept) {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: ifnull -> 18
/*      */     //   4: aload_1
/*      */     //   5: getfield result : Ljava/lang/Object;
/*      */     //   8: dup
/*      */     //   9: astore #4
/*      */     //   11: ifnull -> 18
/*      */     //   14: aload_2
/*      */     //   15: ifnonnull -> 20
/*      */     //   18: iconst_0
/*      */     //   19: ireturn
/*      */     //   20: aload_0
/*      */     //   21: getfield result : Ljava/lang/Object;
/*      */     //   24: ifnonnull -> 106
/*      */     //   27: aload #4
/*      */     //   29: instanceof java/util/concurrent/CompletableFuture$AltResult
/*      */     //   32: ifeq -> 64
/*      */     //   35: aload #4
/*      */     //   37: checkcast java/util/concurrent/CompletableFuture$AltResult
/*      */     //   40: getfield ex : Ljava/lang/Throwable;
/*      */     //   43: dup
/*      */     //   44: astore #5
/*      */     //   46: ifnull -> 61
/*      */     //   49: aload_0
/*      */     //   50: aload #5
/*      */     //   52: aload #4
/*      */     //   54: invokevirtual completeThrowable : (Ljava/lang/Throwable;Ljava/lang/Object;)Z
/*      */     //   57: pop
/*      */     //   58: goto -> 106
/*      */     //   61: aconst_null
/*      */     //   62: astore #4
/*      */     //   64: aload_3
/*      */     //   65: ifnull -> 77
/*      */     //   68: aload_3
/*      */     //   69: invokevirtual claim : ()Z
/*      */     //   72: ifne -> 77
/*      */     //   75: iconst_0
/*      */     //   76: ireturn
/*      */     //   77: aload #4
/*      */     //   79: astore #6
/*      */     //   81: aload_2
/*      */     //   82: aload #6
/*      */     //   84: invokeinterface accept : (Ljava/lang/Object;)V
/*      */     //   89: aload_0
/*      */     //   90: invokevirtual completeNull : ()Z
/*      */     //   93: pop
/*      */     //   94: goto -> 106
/*      */     //   97: astore #6
/*      */     //   99: aload_0
/*      */     //   100: aload #6
/*      */     //   102: invokevirtual completeThrowable : (Ljava/lang/Throwable;)Z
/*      */     //   105: pop
/*      */     //   106: iconst_1
/*      */     //   107: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #656	-> 0
/*      */     //   #657	-> 18
/*      */     //   #658	-> 20
/*      */     //   #659	-> 27
/*      */     //   #660	-> 35
/*      */     //   #661	-> 49
/*      */     //   #662	-> 58
/*      */     //   #664	-> 61
/*      */     //   #667	-> 64
/*      */     //   #668	-> 75
/*      */     //   #669	-> 77
/*      */     //   #670	-> 81
/*      */     //   #671	-> 89
/*      */     //   #674	-> 94
/*      */     //   #672	-> 97
/*      */     //   #673	-> 99
/*      */     //   #676	-> 106
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   64	76	97	java/lang/Throwable
/*      */     //   77	94	97	java/lang/Throwable
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CompletableFuture<Void> uniAcceptStage(Executor paramExecutor, Consumer<? super T> paramConsumer) {
/*  681 */     if (paramConsumer == null) throw new NullPointerException(); 
/*  682 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*  683 */     if (paramExecutor != null || !completableFuture.uniAccept(this, paramConsumer, null)) {
/*  684 */       UniAccept<T> uniAccept = new UniAccept<>(paramExecutor, completableFuture, this, paramConsumer);
/*  685 */       push(uniAccept);
/*  686 */       uniAccept.tryFire(0);
/*      */     } 
/*  688 */     return completableFuture;
/*      */   }
/*      */   
/*      */   static final class UniRun<T>
/*      */     extends UniCompletion<T, Void> {
/*      */     Runnable fn;
/*      */     
/*      */     UniRun(Executor param1Executor, CompletableFuture<Void> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, Runnable param1Runnable) {
/*  696 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1); this.fn = param1Runnable;
/*      */     } final CompletableFuture<Void> tryFire(int param1Int) {
/*      */       CompletableFuture<Void> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*  700 */       if ((completableFuture = this.dep) == null || 
/*  701 */         !completableFuture.uniRun(completableFuture1 = this.src, this.fn, (param1Int > 0) ? null : this))
/*  702 */         return null; 
/*  703 */       this.dep = null; this.src = null; this.fn = null;
/*  704 */       return completableFuture.postFire(completableFuture1, param1Int);
/*      */     }
/*      */   }
/*      */   
/*      */   final boolean uniRun(CompletableFuture<?> paramCompletableFuture, Runnable paramRunnable, UniRun<?> paramUniRun) {
/*      */     Object object;
/*  710 */     if (paramCompletableFuture == null || (object = paramCompletableFuture.result) == null || paramRunnable == null)
/*  711 */       return false; 
/*  712 */     if (this.result == null) {
/*  713 */       Throwable throwable; if (object instanceof AltResult && (throwable = ((AltResult)object).ex) != null) {
/*  714 */         completeThrowable(throwable, object);
/*      */       } else {
/*      */         try {
/*  717 */           if (paramUniRun != null && !paramUniRun.claim())
/*  718 */             return false; 
/*  719 */           paramRunnable.run();
/*  720 */           completeNull();
/*  721 */         } catch (Throwable throwable1) {
/*  722 */           completeThrowable(throwable1);
/*      */         } 
/*      */       } 
/*  725 */     }  return true;
/*      */   }
/*      */   
/*      */   private CompletableFuture<Void> uniRunStage(Executor paramExecutor, Runnable paramRunnable) {
/*  729 */     if (paramRunnable == null) throw new NullPointerException(); 
/*  730 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*  731 */     if (paramExecutor != null || !completableFuture.uniRun(this, paramRunnable, null)) {
/*  732 */       UniRun<?, ?> uniRun = new UniRun(paramExecutor, completableFuture, this, paramRunnable);
/*  733 */       push(uniRun);
/*  734 */       uniRun.tryFire(0);
/*      */     } 
/*  736 */     return completableFuture;
/*      */   }
/*      */   
/*      */   static final class UniWhenComplete<T>
/*      */     extends UniCompletion<T, T>
/*      */   {
/*      */     BiConsumer<? super T, ? super Throwable> fn;
/*      */     
/*      */     UniWhenComplete(Executor param1Executor, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<T> param1CompletableFuture2, BiConsumer<? super T, ? super Throwable> param1BiConsumer) {
/*  745 */       super(param1Executor, param1CompletableFuture1, param1CompletableFuture2); this.fn = param1BiConsumer;
/*      */     } final CompletableFuture<T> tryFire(int param1Int) {
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<T> completableFuture2;
/*  749 */       if ((completableFuture1 = this.dep) == null || 
/*  750 */         !completableFuture1.uniWhenComplete(completableFuture2 = this.src, this.fn, (param1Int > 0) ? null : this))
/*  751 */         return null; 
/*  752 */       this.dep = null; this.src = null; this.fn = null;
/*  753 */       return completableFuture1.postFire(completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean uniWhenComplete(CompletableFuture<T> paramCompletableFuture, BiConsumer<? super T, ? super Throwable> paramBiConsumer, UniWhenComplete<T> paramUniWhenComplete) {
/*  760 */     Throwable throwable = null; Object object;
/*  761 */     if (paramCompletableFuture == null || (object = paramCompletableFuture.result) == null || paramBiConsumer == null)
/*  762 */       return false; 
/*  763 */     if (this.result == null) {
/*      */       try {
/*  765 */         Object object1; if (paramUniWhenComplete != null && !paramUniWhenComplete.claim())
/*  766 */           return false; 
/*  767 */         if (object instanceof AltResult) {
/*  768 */           throwable = ((AltResult)object).ex;
/*  769 */           object1 = null;
/*      */         } else {
/*  771 */           Object object2 = object;
/*  772 */           object1 = object2;
/*      */         } 
/*  774 */         paramBiConsumer.accept((T)object1, throwable);
/*  775 */         if (throwable == null) {
/*  776 */           internalComplete(object);
/*  777 */           return true;
/*      */         } 
/*  779 */       } catch (Throwable throwable1) {
/*  780 */         if (throwable == null)
/*  781 */           throwable = throwable1; 
/*      */       } 
/*  783 */       completeThrowable(throwable, object);
/*      */     } 
/*  785 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private CompletableFuture<T> uniWhenCompleteStage(Executor paramExecutor, BiConsumer<? super T, ? super Throwable> paramBiConsumer) {
/*  790 */     if (paramBiConsumer == null) throw new NullPointerException(); 
/*  791 */     CompletableFuture<T> completableFuture = new CompletableFuture();
/*  792 */     if (paramExecutor != null || !completableFuture.uniWhenComplete(this, paramBiConsumer, null)) {
/*  793 */       UniWhenComplete<T> uniWhenComplete = new UniWhenComplete<>(paramExecutor, completableFuture, this, paramBiConsumer);
/*  794 */       push(uniWhenComplete);
/*  795 */       uniWhenComplete.tryFire(0);
/*      */     } 
/*  797 */     return completableFuture;
/*      */   }
/*      */   
/*      */   static final class UniHandle<T, V>
/*      */     extends UniCompletion<T, V>
/*      */   {
/*      */     BiFunction<? super T, Throwable, ? extends V> fn;
/*      */     
/*      */     UniHandle(Executor param1Executor, CompletableFuture<V> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, BiFunction<? super T, Throwable, ? extends V> param1BiFunction) {
/*  806 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1); this.fn = param1BiFunction;
/*      */     } final CompletableFuture<V> tryFire(int param1Int) {
/*      */       CompletableFuture<V> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*  810 */       if ((completableFuture = this.dep) == null || 
/*  811 */         !completableFuture.uniHandle(completableFuture1 = this.src, this.fn, (param1Int > 0) ? null : this))
/*  812 */         return null; 
/*  813 */       this.dep = null; this.src = null; this.fn = null;
/*  814 */       return completableFuture.postFire(completableFuture1, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final <S> boolean uniHandle(CompletableFuture<S> paramCompletableFuture, BiFunction<? super S, Throwable, ? extends T> paramBiFunction, UniHandle<S, T> paramUniHandle) {
/*      */     Object object;
/*  822 */     if (paramCompletableFuture == null || (object = paramCompletableFuture.result) == null || paramBiFunction == null)
/*  823 */       return false; 
/*  824 */     if (this.result == null) {
/*      */       try {
/*  826 */         Object object1; Throwable throwable; if (paramUniHandle != null && !paramUniHandle.claim())
/*  827 */           return false; 
/*  828 */         if (object instanceof AltResult) {
/*  829 */           throwable = ((AltResult)object).ex;
/*  830 */           object1 = null;
/*      */         } else {
/*  832 */           throwable = null;
/*  833 */           Object object2 = object;
/*  834 */           object1 = object2;
/*      */         } 
/*  836 */         completeValue(paramBiFunction.apply((S)object1, throwable));
/*  837 */       } catch (Throwable throwable) {
/*  838 */         completeThrowable(throwable);
/*      */       } 
/*      */     }
/*  841 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private <V> CompletableFuture<V> uniHandleStage(Executor paramExecutor, BiFunction<? super T, Throwable, ? extends V> paramBiFunction) {
/*  846 */     if (paramBiFunction == null) throw new NullPointerException(); 
/*  847 */     CompletableFuture<V> completableFuture = new CompletableFuture();
/*  848 */     if (paramExecutor != null || !completableFuture.uniHandle(this, paramBiFunction, null)) {
/*  849 */       UniHandle<T, V> uniHandle = new UniHandle<>(paramExecutor, completableFuture, this, paramBiFunction);
/*  850 */       push(uniHandle);
/*  851 */       uniHandle.tryFire(0);
/*      */     } 
/*  853 */     return completableFuture;
/*      */   }
/*      */   
/*      */   static final class UniExceptionally<T>
/*      */     extends UniCompletion<T, T> {
/*      */     Function<? super Throwable, ? extends T> fn;
/*      */     
/*      */     UniExceptionally(CompletableFuture<T> param1CompletableFuture1, CompletableFuture<T> param1CompletableFuture2, Function<? super Throwable, ? extends T> param1Function) {
/*  861 */       super((Executor)null, param1CompletableFuture1, param1CompletableFuture2); this.fn = param1Function;
/*      */     }
/*      */     final CompletableFuture<T> tryFire(int param1Int) {
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<T> completableFuture2;
/*  866 */       if ((completableFuture1 = this.dep) == null || !completableFuture1.uniExceptionally(completableFuture2 = this.src, this.fn, this))
/*  867 */         return null; 
/*  868 */       this.dep = null; this.src = null; this.fn = null;
/*  869 */       return completableFuture1.postFire(completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean uniExceptionally(CompletableFuture<T> paramCompletableFuture, Function<? super Throwable, ? extends T> paramFunction, UniExceptionally<T> paramUniExceptionally) {
/*      */     Object object;
/*  877 */     if (paramCompletableFuture == null || (object = paramCompletableFuture.result) == null || paramFunction == null)
/*  878 */       return false; 
/*  879 */     if (this.result == null) {
/*      */       try {
/*  881 */         Throwable throwable; if (object instanceof AltResult && (throwable = ((AltResult)object).ex) != null)
/*  882 */         { if (paramUniExceptionally != null && !paramUniExceptionally.claim())
/*  883 */             return false; 
/*  884 */           completeValue(paramFunction.apply(throwable)); }
/*      */         else
/*  886 */         { internalComplete(object); } 
/*  887 */       } catch (Throwable throwable) {
/*  888 */         completeThrowable(throwable);
/*      */       } 
/*      */     }
/*  891 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private CompletableFuture<T> uniExceptionallyStage(Function<Throwable, ? extends T> paramFunction) {
/*  896 */     if (paramFunction == null) throw new NullPointerException(); 
/*  897 */     CompletableFuture<T> completableFuture = new CompletableFuture();
/*  898 */     if (!completableFuture.uniExceptionally(this, paramFunction, null)) {
/*  899 */       UniExceptionally<T> uniExceptionally = new UniExceptionally<>(completableFuture, this, paramFunction);
/*  900 */       push(uniExceptionally);
/*  901 */       uniExceptionally.tryFire(0);
/*      */     } 
/*  903 */     return completableFuture;
/*      */   }
/*      */   
/*      */   static final class UniRelay<T>
/*      */     extends UniCompletion<T, T> {
/*      */     UniRelay(CompletableFuture<T> param1CompletableFuture1, CompletableFuture<T> param1CompletableFuture2) {
/*  909 */       super((Executor)null, param1CompletableFuture1, param1CompletableFuture2);
/*      */     } final CompletableFuture<T> tryFire(int param1Int) {
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<T> completableFuture2;
/*  913 */       if ((completableFuture1 = this.dep) == null || !completableFuture1.uniRelay(completableFuture2 = this.src))
/*  914 */         return null; 
/*  915 */       this.src = null; this.dep = null;
/*  916 */       return completableFuture1.postFire(completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */   
/*      */   final boolean uniRelay(CompletableFuture<T> paramCompletableFuture) {
/*      */     Object object;
/*  922 */     if (paramCompletableFuture == null || (object = paramCompletableFuture.result) == null)
/*  923 */       return false; 
/*  924 */     if (this.result == null)
/*  925 */       completeRelay(object); 
/*  926 */     return true;
/*      */   }
/*      */   
/*      */   static final class UniCompose<T, V>
/*      */     extends UniCompletion<T, V>
/*      */   {
/*      */     Function<? super T, ? extends CompletionStage<V>> fn;
/*      */     
/*      */     UniCompose(Executor param1Executor, CompletableFuture<V> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, Function<? super T, ? extends CompletionStage<V>> param1Function) {
/*  935 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1); this.fn = param1Function;
/*      */     } final CompletableFuture<V> tryFire(int param1Int) {
/*      */       CompletableFuture<V> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*  939 */       if ((completableFuture = this.dep) == null || 
/*  940 */         !completableFuture.uniCompose(completableFuture1 = this.src, this.fn, (param1Int > 0) ? null : this))
/*  941 */         return null; 
/*  942 */       this.dep = null; this.src = null; this.fn = null;
/*  943 */       return completableFuture.postFire(completableFuture1, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final <S> boolean uniCompose(CompletableFuture<S> paramCompletableFuture, Function<? super S, ? extends CompletionStage<T>> paramFunction, UniCompose<S, T> paramUniCompose) {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: ifnull -> 18
/*      */     //   4: aload_1
/*      */     //   5: getfield result : Ljava/lang/Object;
/*      */     //   8: dup
/*      */     //   9: astore #4
/*      */     //   11: ifnull -> 18
/*      */     //   14: aload_2
/*      */     //   15: ifnonnull -> 20
/*      */     //   18: iconst_0
/*      */     //   19: ireturn
/*      */     //   20: aload_0
/*      */     //   21: getfield result : Ljava/lang/Object;
/*      */     //   24: ifnonnull -> 163
/*      */     //   27: aload #4
/*      */     //   29: instanceof java/util/concurrent/CompletableFuture$AltResult
/*      */     //   32: ifeq -> 64
/*      */     //   35: aload #4
/*      */     //   37: checkcast java/util/concurrent/CompletableFuture$AltResult
/*      */     //   40: getfield ex : Ljava/lang/Throwable;
/*      */     //   43: dup
/*      */     //   44: astore #5
/*      */     //   46: ifnull -> 61
/*      */     //   49: aload_0
/*      */     //   50: aload #5
/*      */     //   52: aload #4
/*      */     //   54: invokevirtual completeThrowable : (Ljava/lang/Throwable;Ljava/lang/Object;)Z
/*      */     //   57: pop
/*      */     //   58: goto -> 163
/*      */     //   61: aconst_null
/*      */     //   62: astore #4
/*      */     //   64: aload_3
/*      */     //   65: ifnull -> 77
/*      */     //   68: aload_3
/*      */     //   69: invokevirtual claim : ()Z
/*      */     //   72: ifne -> 77
/*      */     //   75: iconst_0
/*      */     //   76: ireturn
/*      */     //   77: aload #4
/*      */     //   79: astore #6
/*      */     //   81: aload_2
/*      */     //   82: aload #6
/*      */     //   84: invokeinterface apply : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   89: checkcast java/util/concurrent/CompletionStage
/*      */     //   92: invokeinterface toCompletableFuture : ()Ljava/util/concurrent/CompletableFuture;
/*      */     //   97: astore #7
/*      */     //   99: aload #7
/*      */     //   101: getfield result : Ljava/lang/Object;
/*      */     //   104: ifnull -> 116
/*      */     //   107: aload_0
/*      */     //   108: aload #7
/*      */     //   110: invokevirtual uniRelay : (Ljava/util/concurrent/CompletableFuture;)Z
/*      */     //   113: ifne -> 151
/*      */     //   116: new java/util/concurrent/CompletableFuture$UniRelay
/*      */     //   119: dup
/*      */     //   120: aload_0
/*      */     //   121: aload #7
/*      */     //   123: invokespecial <init> : (Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;)V
/*      */     //   126: astore #8
/*      */     //   128: aload #7
/*      */     //   130: aload #8
/*      */     //   132: invokevirtual push : (Ljava/util/concurrent/CompletableFuture$UniCompletion;)V
/*      */     //   135: aload #8
/*      */     //   137: iconst_0
/*      */     //   138: invokevirtual tryFire : (I)Ljava/util/concurrent/CompletableFuture;
/*      */     //   141: pop
/*      */     //   142: aload_0
/*      */     //   143: getfield result : Ljava/lang/Object;
/*      */     //   146: ifnonnull -> 151
/*      */     //   149: iconst_0
/*      */     //   150: ireturn
/*      */     //   151: goto -> 163
/*      */     //   154: astore #6
/*      */     //   156: aload_0
/*      */     //   157: aload #6
/*      */     //   159: invokevirtual completeThrowable : (Ljava/lang/Throwable;)Z
/*      */     //   162: pop
/*      */     //   163: iconst_1
/*      */     //   164: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #952	-> 0
/*      */     //   #953	-> 18
/*      */     //   #954	-> 20
/*      */     //   #955	-> 27
/*      */     //   #956	-> 35
/*      */     //   #957	-> 49
/*      */     //   #958	-> 58
/*      */     //   #960	-> 61
/*      */     //   #963	-> 64
/*      */     //   #964	-> 75
/*      */     //   #965	-> 77
/*      */     //   #966	-> 81
/*      */     //   #967	-> 99
/*      */     //   #968	-> 116
/*      */     //   #969	-> 128
/*      */     //   #970	-> 135
/*      */     //   #971	-> 142
/*      */     //   #972	-> 149
/*      */     //   #976	-> 151
/*      */     //   #974	-> 154
/*      */     //   #975	-> 156
/*      */     //   #978	-> 163
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   64	76	154	java/lang/Throwable
/*      */     //   77	150	154	java/lang/Throwable
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <V> CompletableFuture<V> uniComposeStage(Executor paramExecutor, Function<? super T, ? extends CompletionStage<V>> paramFunction) {
/*  983 */     if (paramFunction == null) throw new NullPointerException(); 
/*      */     Object object;
/*  985 */     if (paramExecutor == null && (object = this.result) != null) {
/*      */       
/*  987 */       if (object instanceof AltResult) {
/*  988 */         Throwable throwable; if ((throwable = ((AltResult)object).ex) != null) {
/*  989 */           return new CompletableFuture(encodeThrowable(throwable, object));
/*      */         }
/*  991 */         object = null;
/*      */       } 
/*      */       try {
/*  994 */         Object object1 = object;
/*  995 */         CompletableFuture<?> completableFuture1 = ((CompletionStage)paramFunction.apply((T)object1)).toCompletableFuture();
/*  996 */         Object object2 = completableFuture1.result;
/*  997 */         if (object2 != null)
/*  998 */           return new CompletableFuture(encodeRelay(object2)); 
/*  999 */         CompletableFuture<?> completableFuture2 = new CompletableFuture();
/* 1000 */         UniRelay<?, ?> uniRelay = new UniRelay(completableFuture2, completableFuture1);
/* 1001 */         completableFuture1.push(uniRelay);
/* 1002 */         uniRelay.tryFire(0);
/* 1003 */         return (CompletableFuture)completableFuture2;
/* 1004 */       } catch (Throwable throwable) {
/* 1005 */         return new CompletableFuture(encodeThrowable(throwable));
/*      */       } 
/*      */     } 
/* 1008 */     CompletableFuture<V> completableFuture = new CompletableFuture();
/* 1009 */     UniCompose<T, V> uniCompose = new UniCompose<>(paramExecutor, completableFuture, this, paramFunction);
/* 1010 */     push(uniCompose);
/* 1011 */     uniCompose.tryFire(0);
/* 1012 */     return completableFuture;
/*      */   }
/*      */ 
/*      */   
/*      */   static abstract class BiCompletion<T, U, V>
/*      */     extends UniCompletion<T, V>
/*      */   {
/*      */     CompletableFuture<U> snd;
/*      */ 
/*      */     
/*      */     BiCompletion(Executor param1Executor, CompletableFuture<V> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2) {
/* 1023 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1); this.snd = param1CompletableFuture2;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class CoCompletion extends Completion {
/*      */     CompletableFuture.BiCompletion<?, ?, ?> base;
/*      */     
/*      */     CoCompletion(CompletableFuture.BiCompletion<?, ?, ?> param1BiCompletion) {
/* 1031 */       this.base = param1BiCompletion; } final CompletableFuture<?> tryFire(int param1Int) {
/*      */       CompletableFuture.BiCompletion<?, ?, ?> biCompletion;
/*      */       CompletableFuture<?> completableFuture;
/* 1034 */       if ((biCompletion = this.base) == null || (completableFuture = biCompletion.tryFire(param1Int)) == null)
/* 1035 */         return null; 
/* 1036 */       this.base = null;
/* 1037 */       return completableFuture;
/*      */     }
/*      */     final boolean isLive() {
/*      */       CompletableFuture.BiCompletion<?, ?, ?> biCompletion;
/* 1041 */       return ((biCompletion = this.base) != null && biCompletion.dep != null);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   final void bipush(CompletableFuture<?> paramCompletableFuture, BiCompletion<?, ?, ?> paramBiCompletion) {
/* 1047 */     if (paramBiCompletion != null) {
/*      */       Object object;
/* 1049 */       while ((object = this.result) == null && !tryPushStack(paramBiCompletion))
/* 1050 */         lazySetNext(paramBiCompletion, null); 
/* 1051 */       if (paramCompletableFuture != null && paramCompletableFuture != this && paramCompletableFuture.result == null) {
/* 1052 */         Completion completion = (Completion)((object != null) ? paramBiCompletion : new CoCompletion(paramBiCompletion));
/* 1053 */         while (paramCompletableFuture.result == null && !paramCompletableFuture.tryPushStack(completion)) {
/* 1054 */           lazySetNext(completion, null);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   final CompletableFuture<T> postFire(CompletableFuture<?> paramCompletableFuture1, CompletableFuture<?> paramCompletableFuture2, int paramInt) {
/* 1062 */     if (paramCompletableFuture2 != null && paramCompletableFuture2.stack != null)
/* 1063 */       if (paramInt < 0 || paramCompletableFuture2.result == null) {
/* 1064 */         paramCompletableFuture2.cleanStack();
/*      */       } else {
/* 1066 */         paramCompletableFuture2.postComplete();
/*      */       }  
/* 1068 */     return postFire(paramCompletableFuture1, paramInt);
/*      */   }
/*      */   
/*      */   static final class BiApply<T, U, V>
/*      */     extends BiCompletion<T, U, V>
/*      */   {
/*      */     BiFunction<? super T, ? super U, ? extends V> fn;
/*      */     
/*      */     BiApply(Executor param1Executor, CompletableFuture<V> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2, BiFunction<? super T, ? super U, ? extends V> param1BiFunction) {
/* 1077 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1, param1CompletableFuture2); this.fn = param1BiFunction;
/*      */     }
/*      */     final CompletableFuture<V> tryFire(int param1Int) {
/*      */       CompletableFuture<V> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<U> completableFuture2;
/* 1083 */       if ((completableFuture = this.dep) == null || 
/* 1084 */         !completableFuture.biApply(completableFuture1 = this.src, completableFuture2 = this.snd, this.fn, (param1Int > 0) ? null : this))
/* 1085 */         return null; 
/* 1086 */       this.dep = null; this.src = null; this.snd = null; this.fn = null;
/* 1087 */       return completableFuture.postFire(completableFuture1, completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final <R, S> boolean biApply(CompletableFuture<R> paramCompletableFuture, CompletableFuture<S> paramCompletableFuture1, BiFunction<? super R, ? super S, ? extends T> paramBiFunction, BiApply<R, S, T> paramBiApply) {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: ifnull -> 32
/*      */     //   4: aload_1
/*      */     //   5: getfield result : Ljava/lang/Object;
/*      */     //   8: dup
/*      */     //   9: astore #5
/*      */     //   11: ifnull -> 32
/*      */     //   14: aload_2
/*      */     //   15: ifnull -> 32
/*      */     //   18: aload_2
/*      */     //   19: getfield result : Ljava/lang/Object;
/*      */     //   22: dup
/*      */     //   23: astore #6
/*      */     //   25: ifnull -> 32
/*      */     //   28: aload_3
/*      */     //   29: ifnonnull -> 34
/*      */     //   32: iconst_0
/*      */     //   33: ireturn
/*      */     //   34: aload_0
/*      */     //   35: getfield result : Ljava/lang/Object;
/*      */     //   38: ifnonnull -> 165
/*      */     //   41: aload #5
/*      */     //   43: instanceof java/util/concurrent/CompletableFuture$AltResult
/*      */     //   46: ifeq -> 78
/*      */     //   49: aload #5
/*      */     //   51: checkcast java/util/concurrent/CompletableFuture$AltResult
/*      */     //   54: getfield ex : Ljava/lang/Throwable;
/*      */     //   57: dup
/*      */     //   58: astore #7
/*      */     //   60: ifnull -> 75
/*      */     //   63: aload_0
/*      */     //   64: aload #7
/*      */     //   66: aload #5
/*      */     //   68: invokevirtual completeThrowable : (Ljava/lang/Throwable;Ljava/lang/Object;)Z
/*      */     //   71: pop
/*      */     //   72: goto -> 165
/*      */     //   75: aconst_null
/*      */     //   76: astore #5
/*      */     //   78: aload #6
/*      */     //   80: instanceof java/util/concurrent/CompletableFuture$AltResult
/*      */     //   83: ifeq -> 115
/*      */     //   86: aload #6
/*      */     //   88: checkcast java/util/concurrent/CompletableFuture$AltResult
/*      */     //   91: getfield ex : Ljava/lang/Throwable;
/*      */     //   94: dup
/*      */     //   95: astore #7
/*      */     //   97: ifnull -> 112
/*      */     //   100: aload_0
/*      */     //   101: aload #7
/*      */     //   103: aload #6
/*      */     //   105: invokevirtual completeThrowable : (Ljava/lang/Throwable;Ljava/lang/Object;)Z
/*      */     //   108: pop
/*      */     //   109: goto -> 165
/*      */     //   112: aconst_null
/*      */     //   113: astore #6
/*      */     //   115: aload #4
/*      */     //   117: ifnull -> 130
/*      */     //   120: aload #4
/*      */     //   122: invokevirtual claim : ()Z
/*      */     //   125: ifne -> 130
/*      */     //   128: iconst_0
/*      */     //   129: ireturn
/*      */     //   130: aload #5
/*      */     //   132: astore #8
/*      */     //   134: aload #6
/*      */     //   136: astore #9
/*      */     //   138: aload_0
/*      */     //   139: aload_3
/*      */     //   140: aload #8
/*      */     //   142: aload #9
/*      */     //   144: invokeinterface apply : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   149: invokevirtual completeValue : (Ljava/lang/Object;)Z
/*      */     //   152: pop
/*      */     //   153: goto -> 165
/*      */     //   156: astore #8
/*      */     //   158: aload_0
/*      */     //   159: aload #8
/*      */     //   161: invokevirtual completeThrowable : (Ljava/lang/Throwable;)Z
/*      */     //   164: pop
/*      */     //   165: iconst_1
/*      */     //   166: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1096	-> 0
/*      */     //   #1098	-> 32
/*      */     //   #1099	-> 34
/*      */     //   #1100	-> 41
/*      */     //   #1101	-> 49
/*      */     //   #1102	-> 63
/*      */     //   #1103	-> 72
/*      */     //   #1105	-> 75
/*      */     //   #1107	-> 78
/*      */     //   #1108	-> 86
/*      */     //   #1109	-> 100
/*      */     //   #1110	-> 109
/*      */     //   #1112	-> 112
/*      */     //   #1115	-> 115
/*      */     //   #1116	-> 128
/*      */     //   #1117	-> 130
/*      */     //   #1118	-> 134
/*      */     //   #1119	-> 138
/*      */     //   #1122	-> 153
/*      */     //   #1120	-> 156
/*      */     //   #1121	-> 158
/*      */     //   #1124	-> 165
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   115	129	156	java/lang/Throwable
/*      */     //   130	153	156	java/lang/Throwable
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <U, V> CompletableFuture<V> biApplyStage(Executor paramExecutor, CompletionStage<U> paramCompletionStage, BiFunction<? super T, ? super U, ? extends V> paramBiFunction) {
/*      */     CompletableFuture<U> completableFuture;
/* 1131 */     if (paramBiFunction == null || (completableFuture = paramCompletionStage.toCompletableFuture()) == null)
/* 1132 */       throw new NullPointerException(); 
/* 1133 */     CompletableFuture<V> completableFuture1 = new CompletableFuture();
/* 1134 */     if (paramExecutor != null || !completableFuture1.biApply(this, completableFuture, paramBiFunction, null)) {
/* 1135 */       BiApply<T, U, V> biApply = new BiApply<>(paramExecutor, completableFuture1, this, completableFuture, paramBiFunction);
/* 1136 */       bipush(completableFuture, biApply);
/* 1137 */       biApply.tryFire(0);
/*      */     } 
/* 1139 */     return completableFuture1;
/*      */   }
/*      */   
/*      */   static final class BiAccept<T, U>
/*      */     extends BiCompletion<T, U, Void>
/*      */   {
/*      */     BiConsumer<? super T, ? super U> fn;
/*      */     
/*      */     BiAccept(Executor param1Executor, CompletableFuture<Void> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2, BiConsumer<? super T, ? super U> param1BiConsumer) {
/* 1148 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1, param1CompletableFuture2); this.fn = param1BiConsumer;
/*      */     }
/*      */     final CompletableFuture<Void> tryFire(int param1Int) {
/*      */       CompletableFuture<Void> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<U> completableFuture2;
/* 1154 */       if ((completableFuture = this.dep) == null || 
/* 1155 */         !completableFuture.biAccept(completableFuture1 = this.src, completableFuture2 = this.snd, this.fn, (param1Int > 0) ? null : this))
/* 1156 */         return null; 
/* 1157 */       this.dep = null; this.src = null; this.snd = null; this.fn = null;
/* 1158 */       return completableFuture.postFire(completableFuture1, completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final <R, S> boolean biAccept(CompletableFuture<R> paramCompletableFuture, CompletableFuture<S> paramCompletableFuture1, BiConsumer<? super R, ? super S> paramBiConsumer, BiAccept<R, S> paramBiAccept) {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: ifnull -> 32
/*      */     //   4: aload_1
/*      */     //   5: getfield result : Ljava/lang/Object;
/*      */     //   8: dup
/*      */     //   9: astore #5
/*      */     //   11: ifnull -> 32
/*      */     //   14: aload_2
/*      */     //   15: ifnull -> 32
/*      */     //   18: aload_2
/*      */     //   19: getfield result : Ljava/lang/Object;
/*      */     //   22: dup
/*      */     //   23: astore #6
/*      */     //   25: ifnull -> 32
/*      */     //   28: aload_3
/*      */     //   29: ifnonnull -> 34
/*      */     //   32: iconst_0
/*      */     //   33: ireturn
/*      */     //   34: aload_0
/*      */     //   35: getfield result : Ljava/lang/Object;
/*      */     //   38: ifnonnull -> 165
/*      */     //   41: aload #5
/*      */     //   43: instanceof java/util/concurrent/CompletableFuture$AltResult
/*      */     //   46: ifeq -> 78
/*      */     //   49: aload #5
/*      */     //   51: checkcast java/util/concurrent/CompletableFuture$AltResult
/*      */     //   54: getfield ex : Ljava/lang/Throwable;
/*      */     //   57: dup
/*      */     //   58: astore #7
/*      */     //   60: ifnull -> 75
/*      */     //   63: aload_0
/*      */     //   64: aload #7
/*      */     //   66: aload #5
/*      */     //   68: invokevirtual completeThrowable : (Ljava/lang/Throwable;Ljava/lang/Object;)Z
/*      */     //   71: pop
/*      */     //   72: goto -> 165
/*      */     //   75: aconst_null
/*      */     //   76: astore #5
/*      */     //   78: aload #6
/*      */     //   80: instanceof java/util/concurrent/CompletableFuture$AltResult
/*      */     //   83: ifeq -> 115
/*      */     //   86: aload #6
/*      */     //   88: checkcast java/util/concurrent/CompletableFuture$AltResult
/*      */     //   91: getfield ex : Ljava/lang/Throwable;
/*      */     //   94: dup
/*      */     //   95: astore #7
/*      */     //   97: ifnull -> 112
/*      */     //   100: aload_0
/*      */     //   101: aload #7
/*      */     //   103: aload #6
/*      */     //   105: invokevirtual completeThrowable : (Ljava/lang/Throwable;Ljava/lang/Object;)Z
/*      */     //   108: pop
/*      */     //   109: goto -> 165
/*      */     //   112: aconst_null
/*      */     //   113: astore #6
/*      */     //   115: aload #4
/*      */     //   117: ifnull -> 130
/*      */     //   120: aload #4
/*      */     //   122: invokevirtual claim : ()Z
/*      */     //   125: ifne -> 130
/*      */     //   128: iconst_0
/*      */     //   129: ireturn
/*      */     //   130: aload #5
/*      */     //   132: astore #8
/*      */     //   134: aload #6
/*      */     //   136: astore #9
/*      */     //   138: aload_3
/*      */     //   139: aload #8
/*      */     //   141: aload #9
/*      */     //   143: invokeinterface accept : (Ljava/lang/Object;Ljava/lang/Object;)V
/*      */     //   148: aload_0
/*      */     //   149: invokevirtual completeNull : ()Z
/*      */     //   152: pop
/*      */     //   153: goto -> 165
/*      */     //   156: astore #8
/*      */     //   158: aload_0
/*      */     //   159: aload #8
/*      */     //   161: invokevirtual completeThrowable : (Ljava/lang/Throwable;)Z
/*      */     //   164: pop
/*      */     //   165: iconst_1
/*      */     //   166: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1167	-> 0
/*      */     //   #1169	-> 32
/*      */     //   #1170	-> 34
/*      */     //   #1171	-> 41
/*      */     //   #1172	-> 49
/*      */     //   #1173	-> 63
/*      */     //   #1174	-> 72
/*      */     //   #1176	-> 75
/*      */     //   #1178	-> 78
/*      */     //   #1179	-> 86
/*      */     //   #1180	-> 100
/*      */     //   #1181	-> 109
/*      */     //   #1183	-> 112
/*      */     //   #1186	-> 115
/*      */     //   #1187	-> 128
/*      */     //   #1188	-> 130
/*      */     //   #1189	-> 134
/*      */     //   #1190	-> 138
/*      */     //   #1191	-> 148
/*      */     //   #1194	-> 153
/*      */     //   #1192	-> 156
/*      */     //   #1193	-> 158
/*      */     //   #1196	-> 165
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   115	129	156	java/lang/Throwable
/*      */     //   130	153	156	java/lang/Throwable
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <U> CompletableFuture<Void> biAcceptStage(Executor paramExecutor, CompletionStage<U> paramCompletionStage, BiConsumer<? super T, ? super U> paramBiConsumer) {
/*      */     CompletableFuture<U> completableFuture;
/* 1203 */     if (paramBiConsumer == null || (completableFuture = paramCompletionStage.toCompletableFuture()) == null)
/* 1204 */       throw new NullPointerException(); 
/* 1205 */     CompletableFuture<Void> completableFuture1 = new CompletableFuture();
/* 1206 */     if (paramExecutor != null || !completableFuture1.biAccept(this, completableFuture, paramBiConsumer, null)) {
/* 1207 */       BiAccept<T, U> biAccept = new BiAccept<>(paramExecutor, completableFuture1, this, completableFuture, paramBiConsumer);
/* 1208 */       bipush(completableFuture, biAccept);
/* 1209 */       biAccept.tryFire(0);
/*      */     } 
/* 1211 */     return completableFuture1;
/*      */   }
/*      */ 
/*      */   
/*      */   static final class BiRun<T, U>
/*      */     extends BiCompletion<T, U, Void>
/*      */   {
/*      */     Runnable fn;
/*      */     
/*      */     BiRun(Executor param1Executor, CompletableFuture<Void> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2, Runnable param1Runnable) {
/* 1221 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1, param1CompletableFuture2); this.fn = param1Runnable;
/*      */     }
/*      */     final CompletableFuture<Void> tryFire(int param1Int) {
/*      */       CompletableFuture<Void> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<U> completableFuture2;
/* 1227 */       if ((completableFuture = this.dep) == null || 
/* 1228 */         !completableFuture.biRun(completableFuture1 = this.src, completableFuture2 = this.snd, this.fn, (param1Int > 0) ? null : this))
/* 1229 */         return null; 
/* 1230 */       this.dep = null; this.src = null; this.snd = null; this.fn = null;
/* 1231 */       return completableFuture.postFire(completableFuture1, completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */   
/*      */   final boolean biRun(CompletableFuture<?> paramCompletableFuture1, CompletableFuture<?> paramCompletableFuture2, Runnable paramRunnable, BiRun<?, ?> paramBiRun) {
/*      */     Object object1;
/*      */     Object object2;
/* 1238 */     if (paramCompletableFuture1 == null || (object1 = paramCompletableFuture1.result) == null || paramCompletableFuture2 == null || (object2 = paramCompletableFuture2.result) == null || paramRunnable == null)
/*      */     {
/* 1240 */       return false; } 
/* 1241 */     if (this.result == null) {
/* 1242 */       Throwable throwable; if (object1 instanceof AltResult && (throwable = ((AltResult)object1).ex) != null) {
/* 1243 */         completeThrowable(throwable, object1);
/* 1244 */       } else if (object2 instanceof AltResult && (throwable = ((AltResult)object2).ex) != null) {
/* 1245 */         completeThrowable(throwable, object2);
/*      */       } else {
/*      */         try {
/* 1248 */           if (paramBiRun != null && !paramBiRun.claim())
/* 1249 */             return false; 
/* 1250 */           paramRunnable.run();
/* 1251 */           completeNull();
/* 1252 */         } catch (Throwable throwable1) {
/* 1253 */           completeThrowable(throwable1);
/*      */         } 
/*      */       } 
/* 1256 */     }  return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private CompletableFuture<Void> biRunStage(Executor paramExecutor, CompletionStage<?> paramCompletionStage, Runnable paramRunnable) {
/*      */     CompletableFuture<?> completableFuture;
/* 1262 */     if (paramRunnable == null || (completableFuture = paramCompletionStage.toCompletableFuture()) == null)
/* 1263 */       throw new NullPointerException(); 
/* 1264 */     CompletableFuture<Void> completableFuture1 = new CompletableFuture();
/* 1265 */     if (paramExecutor != null || !completableFuture1.biRun(this, completableFuture, paramRunnable, null)) {
/* 1266 */       BiRun<Object, Object> biRun = new BiRun<>(paramExecutor, completableFuture1, this, completableFuture, paramRunnable);
/* 1267 */       bipush(completableFuture, biRun);
/* 1268 */       biRun.tryFire(0);
/*      */     } 
/* 1270 */     return completableFuture1;
/*      */   }
/*      */ 
/*      */   
/*      */   static final class BiRelay<T, U>
/*      */     extends BiCompletion<T, U, Void>
/*      */   {
/*      */     BiRelay(CompletableFuture<Void> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2) {
/* 1278 */       super((Executor)null, param1CompletableFuture, param1CompletableFuture1, param1CompletableFuture2);
/*      */     }
/*      */     final CompletableFuture<Void> tryFire(int param1Int) {
/*      */       CompletableFuture<Void> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<U> completableFuture2;
/* 1284 */       if ((completableFuture = this.dep) == null || !completableFuture.biRelay(completableFuture1 = this.src, completableFuture2 = this.snd))
/* 1285 */         return null; 
/* 1286 */       this.src = null; this.snd = null; this.dep = null;
/* 1287 */       return completableFuture.postFire(completableFuture1, completableFuture2, param1Int);
/*      */     } }
/*      */   
/*      */   boolean biRelay(CompletableFuture<?> paramCompletableFuture1, CompletableFuture<?> paramCompletableFuture2) {
/*      */     Object object1;
/*      */     Object object2;
/* 1293 */     if (paramCompletableFuture1 == null || (object1 = paramCompletableFuture1.result) == null || paramCompletableFuture2 == null || (object2 = paramCompletableFuture2.result) == null)
/*      */     {
/* 1295 */       return false; } 
/* 1296 */     if (this.result == null) {
/* 1297 */       Throwable throwable; if (object1 instanceof AltResult && (throwable = ((AltResult)object1).ex) != null) {
/* 1298 */         completeThrowable(throwable, object1);
/* 1299 */       } else if (object2 instanceof AltResult && (throwable = ((AltResult)object2).ex) != null) {
/* 1300 */         completeThrowable(throwable, object2);
/*      */       } else {
/* 1302 */         completeNull();
/*      */       } 
/* 1304 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static CompletableFuture<Void> andTree(CompletableFuture<?>[] paramArrayOfCompletableFuture, int paramInt1, int paramInt2) {
/*      */     // Byte code:
/*      */     //   0: new java/util/concurrent/CompletableFuture
/*      */     //   3: dup
/*      */     //   4: invokespecial <init> : ()V
/*      */     //   7: astore_3
/*      */     //   8: iload_1
/*      */     //   9: iload_2
/*      */     //   10: if_icmple -> 23
/*      */     //   13: aload_3
/*      */     //   14: getstatic java/util/concurrent/CompletableFuture.NIL : Ljava/util/concurrent/CompletableFuture$AltResult;
/*      */     //   17: putfield result : Ljava/lang/Object;
/*      */     //   20: goto -> 143
/*      */     //   23: iload_1
/*      */     //   24: iload_2
/*      */     //   25: iadd
/*      */     //   26: iconst_1
/*      */     //   27: iushr
/*      */     //   28: istore #6
/*      */     //   30: iload_1
/*      */     //   31: iload #6
/*      */     //   33: if_icmpne -> 42
/*      */     //   36: aload_0
/*      */     //   37: iload_1
/*      */     //   38: aaload
/*      */     //   39: goto -> 49
/*      */     //   42: aload_0
/*      */     //   43: iload_1
/*      */     //   44: iload #6
/*      */     //   46: invokestatic andTree : ([Ljava/util/concurrent/CompletableFuture;II)Ljava/util/concurrent/CompletableFuture;
/*      */     //   49: dup
/*      */     //   50: astore #4
/*      */     //   52: ifnull -> 94
/*      */     //   55: iload_1
/*      */     //   56: iload_2
/*      */     //   57: if_icmpne -> 65
/*      */     //   60: aload #4
/*      */     //   62: goto -> 88
/*      */     //   65: iload_2
/*      */     //   66: iload #6
/*      */     //   68: iconst_1
/*      */     //   69: iadd
/*      */     //   70: if_icmpne -> 79
/*      */     //   73: aload_0
/*      */     //   74: iload_2
/*      */     //   75: aaload
/*      */     //   76: goto -> 88
/*      */     //   79: aload_0
/*      */     //   80: iload #6
/*      */     //   82: iconst_1
/*      */     //   83: iadd
/*      */     //   84: iload_2
/*      */     //   85: invokestatic andTree : ([Ljava/util/concurrent/CompletableFuture;II)Ljava/util/concurrent/CompletableFuture;
/*      */     //   88: dup
/*      */     //   89: astore #5
/*      */     //   91: ifnonnull -> 102
/*      */     //   94: new java/lang/NullPointerException
/*      */     //   97: dup
/*      */     //   98: invokespecial <init> : ()V
/*      */     //   101: athrow
/*      */     //   102: aload_3
/*      */     //   103: aload #4
/*      */     //   105: aload #5
/*      */     //   107: invokevirtual biRelay : (Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;)Z
/*      */     //   110: ifne -> 143
/*      */     //   113: new java/util/concurrent/CompletableFuture$BiRelay
/*      */     //   116: dup
/*      */     //   117: aload_3
/*      */     //   118: aload #4
/*      */     //   120: aload #5
/*      */     //   122: invokespecial <init> : (Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;)V
/*      */     //   125: astore #7
/*      */     //   127: aload #4
/*      */     //   129: aload #5
/*      */     //   131: aload #7
/*      */     //   133: invokevirtual bipush : (Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture$BiCompletion;)V
/*      */     //   136: aload #7
/*      */     //   138: iconst_0
/*      */     //   139: invokevirtual tryFire : (I)Ljava/util/concurrent/CompletableFuture;
/*      */     //   142: pop
/*      */     //   143: aload_3
/*      */     //   144: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1310	-> 0
/*      */     //   #1311	-> 8
/*      */     //   #1312	-> 13
/*      */     //   #1315	-> 23
/*      */     //   #1316	-> 30
/*      */     //   #1317	-> 46
/*      */     //   #1319	-> 85
/*      */     //   #1320	-> 94
/*      */     //   #1321	-> 102
/*      */     //   #1322	-> 113
/*      */     //   #1323	-> 127
/*      */     //   #1324	-> 136
/*      */     //   #1327	-> 143
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void orpush(CompletableFuture<?> paramCompletableFuture, BiCompletion<?, ?, ?> paramBiCompletion) {
/* 1334 */     if (paramBiCompletion != null) {
/* 1335 */       while ((paramCompletableFuture == null || paramCompletableFuture.result == null) && this.result == null) {
/* 1336 */         if (tryPushStack(paramBiCompletion)) {
/* 1337 */           if (paramCompletableFuture != null && paramCompletableFuture != this && paramCompletableFuture.result == null) {
/* 1338 */             CoCompletion coCompletion = new CoCompletion(paramBiCompletion);
/* 1339 */             while (this.result == null && paramCompletableFuture.result == null && 
/* 1340 */               !paramCompletableFuture.tryPushStack(coCompletion))
/* 1341 */               lazySetNext(coCompletion, null); 
/*      */           } 
/*      */           break;
/*      */         } 
/* 1345 */         lazySetNext(paramBiCompletion, null);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class OrApply<T, U extends T, V>
/*      */     extends BiCompletion<T, U, V>
/*      */   {
/*      */     Function<? super T, ? extends V> fn;
/*      */     
/*      */     OrApply(Executor param1Executor, CompletableFuture<V> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2, Function<? super T, ? extends V> param1Function) {
/* 1357 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1, param1CompletableFuture2); this.fn = param1Function;
/*      */     }
/*      */     final CompletableFuture<V> tryFire(int param1Int) {
/*      */       CompletableFuture<V> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<U> completableFuture2;
/* 1363 */       if ((completableFuture = this.dep) == null || 
/* 1364 */         !completableFuture.orApply(completableFuture1 = this.src, completableFuture2 = this.snd, this.fn, (param1Int > 0) ? null : this))
/* 1365 */         return null; 
/* 1366 */       this.dep = null; this.src = null; this.snd = null; this.fn = null;
/* 1367 */       return completableFuture.postFire(completableFuture1, completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final <R, S extends R> boolean orApply(CompletableFuture<R> paramCompletableFuture, CompletableFuture<S> paramCompletableFuture1, Function<? super R, ? extends T> paramFunction, OrApply<R, S, T> paramOrApply) {
/*      */     Object object;
/* 1376 */     if (paramCompletableFuture == null || paramCompletableFuture1 == null || ((object = paramCompletableFuture.result) == null && (object = paramCompletableFuture1.result) == null) || paramFunction == null)
/*      */     {
/* 1378 */       return false; } 
/* 1379 */     if (this.result == null)
/*      */       
/* 1381 */       try { if (paramOrApply != null && !paramOrApply.claim())
/* 1382 */           return false; 
/* 1383 */         if (object instanceof AltResult)
/* 1384 */         { Throwable throwable; if ((throwable = ((AltResult)object).ex) != null) {
/* 1385 */             completeThrowable(throwable, object);
/*      */           } else {
/*      */             
/* 1388 */             object = null;
/*      */             
/* 1390 */             Object object2 = object;
/* 1391 */             completeValue(paramFunction.apply((R)object2));
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1396 */           return true; }  Object object1 = object; completeValue(paramFunction.apply((R)object1)); } catch (Throwable throwable) { completeThrowable(throwable); }   return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private <U extends T, V> CompletableFuture<V> orApplyStage(Executor paramExecutor, CompletionStage<U> paramCompletionStage, Function<? super T, ? extends V> paramFunction) {
/*      */     CompletableFuture<U> completableFuture;
/* 1403 */     if (paramFunction == null || (completableFuture = paramCompletionStage.toCompletableFuture()) == null)
/* 1404 */       throw new NullPointerException(); 
/* 1405 */     CompletableFuture<V> completableFuture1 = new CompletableFuture();
/* 1406 */     if (paramExecutor != null || !completableFuture1.orApply(this, completableFuture, paramFunction, null)) {
/* 1407 */       OrApply<T, U, V> orApply = new OrApply<>(paramExecutor, completableFuture1, this, completableFuture, paramFunction);
/* 1408 */       orpush(completableFuture, orApply);
/* 1409 */       orApply.tryFire(0);
/*      */     } 
/* 1411 */     return completableFuture1;
/*      */   }
/*      */ 
/*      */   
/*      */   static final class OrAccept<T, U extends T>
/*      */     extends BiCompletion<T, U, Void>
/*      */   {
/*      */     Consumer<? super T> fn;
/*      */     
/*      */     OrAccept(Executor param1Executor, CompletableFuture<Void> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2, Consumer<? super T> param1Consumer) {
/* 1421 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1, param1CompletableFuture2); this.fn = param1Consumer;
/*      */     }
/*      */     final CompletableFuture<Void> tryFire(int param1Int) {
/*      */       CompletableFuture<Void> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<U> completableFuture2;
/* 1427 */       if ((completableFuture = this.dep) == null || 
/* 1428 */         !completableFuture.orAccept(completableFuture1 = this.src, completableFuture2 = this.snd, this.fn, (param1Int > 0) ? null : this))
/* 1429 */         return null; 
/* 1430 */       this.dep = null; this.src = null; this.snd = null; this.fn = null;
/* 1431 */       return completableFuture.postFire(completableFuture1, completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final <R, S extends R> boolean orAccept(CompletableFuture<R> paramCompletableFuture, CompletableFuture<S> paramCompletableFuture1, Consumer<? super R> paramConsumer, OrAccept<R, S> paramOrAccept) {
/*      */     Object object;
/* 1440 */     if (paramCompletableFuture == null || paramCompletableFuture1 == null || ((object = paramCompletableFuture.result) == null && (object = paramCompletableFuture1.result) == null) || paramConsumer == null)
/*      */     {
/* 1442 */       return false; } 
/* 1443 */     if (this.result == null)
/*      */       
/* 1445 */       try { if (paramOrAccept != null && !paramOrAccept.claim())
/* 1446 */           return false; 
/* 1447 */         if (object instanceof AltResult)
/* 1448 */         { Throwable throwable; if ((throwable = ((AltResult)object).ex) != null) {
/* 1449 */             completeThrowable(throwable, object);
/*      */           } else {
/*      */             
/* 1452 */             object = null;
/*      */             
/* 1454 */             Object object2 = object;
/* 1455 */             paramConsumer.accept((R)object2);
/* 1456 */             completeNull();
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1461 */           return true; }  Object object1 = object; paramConsumer.accept((R)object1); completeNull(); } catch (Throwable throwable) { completeThrowable(throwable); }   return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private <U extends T> CompletableFuture<Void> orAcceptStage(Executor paramExecutor, CompletionStage<U> paramCompletionStage, Consumer<? super T> paramConsumer) {
/*      */     CompletableFuture<U> completableFuture;
/* 1467 */     if (paramConsumer == null || (completableFuture = paramCompletionStage.toCompletableFuture()) == null)
/* 1468 */       throw new NullPointerException(); 
/* 1469 */     CompletableFuture<Void> completableFuture1 = new CompletableFuture();
/* 1470 */     if (paramExecutor != null || !completableFuture1.orAccept(this, completableFuture, paramConsumer, null)) {
/* 1471 */       OrAccept<T, U> orAccept = new OrAccept<>(paramExecutor, completableFuture1, this, completableFuture, paramConsumer);
/* 1472 */       orpush(completableFuture, orAccept);
/* 1473 */       orAccept.tryFire(0);
/*      */     } 
/* 1475 */     return completableFuture1;
/*      */   }
/*      */ 
/*      */   
/*      */   static final class OrRun<T, U>
/*      */     extends BiCompletion<T, U, Void>
/*      */   {
/*      */     Runnable fn;
/*      */     
/*      */     OrRun(Executor param1Executor, CompletableFuture<Void> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2, Runnable param1Runnable) {
/* 1485 */       super(param1Executor, param1CompletableFuture, param1CompletableFuture1, param1CompletableFuture2); this.fn = param1Runnable;
/*      */     }
/*      */     final CompletableFuture<Void> tryFire(int param1Int) {
/*      */       CompletableFuture<Void> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<U> completableFuture2;
/* 1491 */       if ((completableFuture = this.dep) == null || 
/* 1492 */         !completableFuture.orRun(completableFuture1 = this.src, completableFuture2 = this.snd, this.fn, (param1Int > 0) ? null : this))
/* 1493 */         return null; 
/* 1494 */       this.dep = null; this.src = null; this.snd = null; this.fn = null;
/* 1495 */       return completableFuture.postFire(completableFuture1, completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   final boolean orRun(CompletableFuture<?> paramCompletableFuture1, CompletableFuture<?> paramCompletableFuture2, Runnable paramRunnable, OrRun<?, ?> paramOrRun) {
/*      */     Object object;
/* 1502 */     if (paramCompletableFuture1 == null || paramCompletableFuture2 == null || ((object = paramCompletableFuture1.result) == null && (object = paramCompletableFuture2.result) == null) || paramRunnable == null)
/*      */     {
/* 1504 */       return false; } 
/* 1505 */     if (this.result == null) {
/*      */       try {
/* 1507 */         if (paramOrRun != null && !paramOrRun.claim())
/* 1508 */           return false;  Throwable throwable;
/* 1509 */         if (object instanceof AltResult && (throwable = ((AltResult)object).ex) != null) {
/* 1510 */           completeThrowable(throwable, object);
/*      */         } else {
/* 1512 */           paramRunnable.run();
/* 1513 */           completeNull();
/*      */         } 
/* 1515 */       } catch (Throwable throwable) {
/* 1516 */         completeThrowable(throwable);
/*      */       } 
/*      */     }
/* 1519 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private CompletableFuture<Void> orRunStage(Executor paramExecutor, CompletionStage<?> paramCompletionStage, Runnable paramRunnable) {
/*      */     CompletableFuture<?> completableFuture;
/* 1525 */     if (paramRunnable == null || (completableFuture = paramCompletionStage.toCompletableFuture()) == null)
/* 1526 */       throw new NullPointerException(); 
/* 1527 */     CompletableFuture<Void> completableFuture1 = new CompletableFuture();
/* 1528 */     if (paramExecutor != null || !completableFuture1.orRun(this, completableFuture, paramRunnable, null)) {
/* 1529 */       OrRun<Object, Object> orRun = new OrRun<>(paramExecutor, completableFuture1, this, completableFuture, paramRunnable);
/* 1530 */       orpush(completableFuture, orRun);
/* 1531 */       orRun.tryFire(0);
/*      */     } 
/* 1533 */     return completableFuture1;
/*      */   }
/*      */   
/*      */   static final class OrRelay<T, U>
/*      */     extends BiCompletion<T, U, Object>
/*      */   {
/*      */     OrRelay(CompletableFuture<Object> param1CompletableFuture, CompletableFuture<T> param1CompletableFuture1, CompletableFuture<U> param1CompletableFuture2) {
/* 1540 */       super((Executor)null, param1CompletableFuture, param1CompletableFuture1, param1CompletableFuture2);
/*      */     }
/*      */     final CompletableFuture<Object> tryFire(int param1Int) {
/*      */       CompletableFuture<Object> completableFuture;
/*      */       CompletableFuture<T> completableFuture1;
/*      */       CompletableFuture<U> completableFuture2;
/* 1546 */       if ((completableFuture = this.dep) == null || !completableFuture.orRelay(completableFuture1 = this.src, completableFuture2 = this.snd))
/* 1547 */         return null; 
/* 1548 */       this.src = null; this.snd = null; this.dep = null;
/* 1549 */       return completableFuture.postFire(completableFuture1, completableFuture2, param1Int);
/*      */     }
/*      */   }
/*      */   
/*      */   final boolean orRelay(CompletableFuture<?> paramCompletableFuture1, CompletableFuture<?> paramCompletableFuture2) {
/*      */     Object object;
/* 1555 */     if (paramCompletableFuture1 == null || paramCompletableFuture2 == null || ((object = paramCompletableFuture1.result) == null && (object = paramCompletableFuture2.result) == null))
/*      */     {
/* 1557 */       return false; } 
/* 1558 */     if (this.result == null)
/* 1559 */       completeRelay(object); 
/* 1560 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static CompletableFuture<Object> orTree(CompletableFuture<?>[] paramArrayOfCompletableFuture, int paramInt1, int paramInt2) {
/*      */     // Byte code:
/*      */     //   0: new java/util/concurrent/CompletableFuture
/*      */     //   3: dup
/*      */     //   4: invokespecial <init> : ()V
/*      */     //   7: astore_3
/*      */     //   8: iload_1
/*      */     //   9: iload_2
/*      */     //   10: if_icmpgt -> 133
/*      */     //   13: iload_1
/*      */     //   14: iload_2
/*      */     //   15: iadd
/*      */     //   16: iconst_1
/*      */     //   17: iushr
/*      */     //   18: istore #6
/*      */     //   20: iload_1
/*      */     //   21: iload #6
/*      */     //   23: if_icmpne -> 32
/*      */     //   26: aload_0
/*      */     //   27: iload_1
/*      */     //   28: aaload
/*      */     //   29: goto -> 39
/*      */     //   32: aload_0
/*      */     //   33: iload_1
/*      */     //   34: iload #6
/*      */     //   36: invokestatic orTree : ([Ljava/util/concurrent/CompletableFuture;II)Ljava/util/concurrent/CompletableFuture;
/*      */     //   39: dup
/*      */     //   40: astore #4
/*      */     //   42: ifnull -> 84
/*      */     //   45: iload_1
/*      */     //   46: iload_2
/*      */     //   47: if_icmpne -> 55
/*      */     //   50: aload #4
/*      */     //   52: goto -> 78
/*      */     //   55: iload_2
/*      */     //   56: iload #6
/*      */     //   58: iconst_1
/*      */     //   59: iadd
/*      */     //   60: if_icmpne -> 69
/*      */     //   63: aload_0
/*      */     //   64: iload_2
/*      */     //   65: aaload
/*      */     //   66: goto -> 78
/*      */     //   69: aload_0
/*      */     //   70: iload #6
/*      */     //   72: iconst_1
/*      */     //   73: iadd
/*      */     //   74: iload_2
/*      */     //   75: invokestatic orTree : ([Ljava/util/concurrent/CompletableFuture;II)Ljava/util/concurrent/CompletableFuture;
/*      */     //   78: dup
/*      */     //   79: astore #5
/*      */     //   81: ifnonnull -> 92
/*      */     //   84: new java/lang/NullPointerException
/*      */     //   87: dup
/*      */     //   88: invokespecial <init> : ()V
/*      */     //   91: athrow
/*      */     //   92: aload_3
/*      */     //   93: aload #4
/*      */     //   95: aload #5
/*      */     //   97: invokevirtual orRelay : (Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;)Z
/*      */     //   100: ifne -> 133
/*      */     //   103: new java/util/concurrent/CompletableFuture$OrRelay
/*      */     //   106: dup
/*      */     //   107: aload_3
/*      */     //   108: aload #4
/*      */     //   110: aload #5
/*      */     //   112: invokespecial <init> : (Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;)V
/*      */     //   115: astore #7
/*      */     //   117: aload #4
/*      */     //   119: aload #5
/*      */     //   121: aload #7
/*      */     //   123: invokevirtual orpush : (Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture$BiCompletion;)V
/*      */     //   126: aload #7
/*      */     //   128: iconst_0
/*      */     //   129: invokevirtual tryFire : (I)Ljava/util/concurrent/CompletableFuture;
/*      */     //   132: pop
/*      */     //   133: aload_3
/*      */     //   134: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1566	-> 0
/*      */     //   #1567	-> 8
/*      */     //   #1569	-> 13
/*      */     //   #1570	-> 20
/*      */     //   #1571	-> 36
/*      */     //   #1573	-> 75
/*      */     //   #1574	-> 84
/*      */     //   #1575	-> 92
/*      */     //   #1576	-> 103
/*      */     //   #1577	-> 117
/*      */     //   #1578	-> 126
/*      */     //   #1581	-> 133
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class AsyncSupply<T>
/*      */     extends ForkJoinTask<Void>
/*      */     implements Runnable, AsynchronousCompletionTask
/*      */   {
/*      */     CompletableFuture<T> dep;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Supplier<T> fn;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     AsyncSupply(CompletableFuture<T> param1CompletableFuture, Supplier<T> param1Supplier) {
/* 1591 */       this.dep = param1CompletableFuture; this.fn = param1Supplier;
/*      */     }
/*      */     public final Void getRawResult() {
/* 1594 */       return null;
/*      */     } public final void setRawResult(Void param1Void) {} public final boolean exec() {
/* 1596 */       run(); return true;
/*      */     } public void run() {
/*      */       CompletableFuture<T> completableFuture;
/*      */       Supplier<T> supplier;
/* 1600 */       if ((completableFuture = this.dep) != null && (supplier = this.fn) != null) {
/* 1601 */         this.dep = null; this.fn = null;
/* 1602 */         if (completableFuture.result == null) {
/*      */           try {
/* 1604 */             completableFuture.completeValue(supplier.get());
/* 1605 */           } catch (Throwable throwable) {
/* 1606 */             completableFuture.completeThrowable(throwable);
/*      */           } 
/*      */         }
/* 1609 */         completableFuture.postComplete();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static <U> CompletableFuture<U> asyncSupplyStage(Executor paramExecutor, Supplier<U> paramSupplier) {
/* 1616 */     if (paramSupplier == null) throw new NullPointerException(); 
/* 1617 */     CompletableFuture<U> completableFuture = new CompletableFuture();
/* 1618 */     paramExecutor.execute(new AsyncSupply<>(completableFuture, paramSupplier));
/* 1619 */     return completableFuture;
/*      */   }
/*      */   
/*      */   static final class AsyncRun extends ForkJoinTask<Void> implements Runnable, AsynchronousCompletionTask {
/*      */     CompletableFuture<Void> dep;
/*      */     Runnable fn;
/*      */     
/*      */     AsyncRun(CompletableFuture<Void> param1CompletableFuture, Runnable param1Runnable) {
/* 1627 */       this.dep = param1CompletableFuture; this.fn = param1Runnable;
/*      */     }
/*      */     public final Void getRawResult() {
/* 1630 */       return null;
/*      */     } public final void setRawResult(Void param1Void) {} public final boolean exec() {
/* 1632 */       run(); return true;
/*      */     } public void run() {
/*      */       CompletableFuture<Void> completableFuture;
/*      */       Runnable runnable;
/* 1636 */       if ((completableFuture = this.dep) != null && (runnable = this.fn) != null) {
/* 1637 */         this.dep = null; this.fn = null;
/* 1638 */         if (completableFuture.result == null) {
/*      */           try {
/* 1640 */             runnable.run();
/* 1641 */             completableFuture.completeNull();
/* 1642 */           } catch (Throwable throwable) {
/* 1643 */             completableFuture.completeThrowable(throwable);
/*      */           } 
/*      */         }
/* 1646 */         completableFuture.postComplete();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static CompletableFuture<Void> asyncRunStage(Executor paramExecutor, Runnable paramRunnable) {
/* 1652 */     if (paramRunnable == null) throw new NullPointerException(); 
/* 1653 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/* 1654 */     paramExecutor.execute(new AsyncRun(completableFuture, paramRunnable));
/* 1655 */     return completableFuture;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class Signaller
/*      */     extends Completion
/*      */     implements ForkJoinPool.ManagedBlocker
/*      */   {
/*      */     long nanos;
/*      */     
/*      */     final long deadline;
/*      */     
/*      */     volatile int interruptControl;
/*      */     
/*      */     volatile Thread thread;
/*      */ 
/*      */     
/*      */     Signaller(boolean param1Boolean, long param1Long1, long param1Long2) {
/* 1674 */       this.thread = Thread.currentThread();
/* 1675 */       this.interruptControl = param1Boolean ? 1 : 0;
/* 1676 */       this.nanos = param1Long1;
/* 1677 */       this.deadline = param1Long2;
/*      */     }
/*      */     final CompletableFuture<?> tryFire(int param1Int) {
/*      */       Thread thread;
/* 1681 */       if ((thread = this.thread) != null) {
/* 1682 */         this.thread = null;
/* 1683 */         LockSupport.unpark(thread);
/*      */       } 
/* 1685 */       return null;
/*      */     }
/*      */     public boolean isReleasable() {
/* 1688 */       if (this.thread == null)
/* 1689 */         return true; 
/* 1690 */       if (Thread.interrupted()) {
/* 1691 */         int i = this.interruptControl;
/* 1692 */         this.interruptControl = -1;
/* 1693 */         if (i > 0)
/* 1694 */           return true; 
/*      */       } 
/* 1696 */       if (this.deadline != 0L && (this.nanos <= 0L || (this
/* 1697 */         .nanos = this.deadline - System.nanoTime()) <= 0L)) {
/* 1698 */         this.thread = null;
/* 1699 */         return true;
/*      */       } 
/* 1701 */       return false;
/*      */     }
/*      */     public boolean block() {
/* 1704 */       if (isReleasable())
/* 1705 */         return true; 
/* 1706 */       if (this.deadline == 0L) {
/* 1707 */         LockSupport.park(this);
/* 1708 */       } else if (this.nanos > 0L) {
/* 1709 */         LockSupport.parkNanos(this, this.nanos);
/* 1710 */       }  return isReleasable();
/*      */     } final boolean isLive() {
/* 1712 */       return (this.thread != null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object waitingGet(boolean paramBoolean) {
/* 1720 */     Signaller signaller = null;
/* 1721 */     boolean bool = false;
/* 1722 */     int i = -1;
/*      */     Object object;
/* 1724 */     while ((object = this.result) == null) {
/* 1725 */       if (i < 0) {
/* 1726 */         i = SPINS; continue;
/* 1727 */       }  if (i > 0) {
/* 1728 */         if (ThreadLocalRandom.nextSecondarySeed() >= 0)
/* 1729 */           i--;  continue;
/*      */       } 
/* 1731 */       if (signaller == null) {
/* 1732 */         signaller = new Signaller(paramBoolean, 0L, 0L); continue;
/* 1733 */       }  if (!bool) {
/* 1734 */         bool = tryPushStack(signaller); continue;
/* 1735 */       }  if (paramBoolean && signaller.interruptControl < 0) {
/* 1736 */         signaller.thread = null;
/* 1737 */         cleanStack();
/* 1738 */         return null;
/*      */       } 
/* 1740 */       if (signaller.thread != null && this.result == null) {
/*      */         try {
/* 1742 */           ForkJoinPool.managedBlock(signaller);
/* 1743 */         } catch (InterruptedException interruptedException) {
/* 1744 */           signaller.interruptControl = -1;
/*      */         } 
/*      */       }
/*      */     } 
/* 1748 */     if (signaller != null) {
/* 1749 */       signaller.thread = null;
/* 1750 */       if (signaller.interruptControl < 0)
/* 1751 */         if (paramBoolean) {
/* 1752 */           object = null;
/*      */         } else {
/* 1754 */           Thread.currentThread().interrupt();
/*      */         }  
/*      */     } 
/* 1757 */     postComplete();
/* 1758 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object timedGet(long paramLong) throws TimeoutException {
/* 1766 */     if (Thread.interrupted())
/* 1767 */       return null; 
/* 1768 */     if (paramLong <= 0L)
/* 1769 */       throw new TimeoutException(); 
/* 1770 */     long l = System.nanoTime() + paramLong;
/* 1771 */     Signaller signaller = new Signaller(true, paramLong, (l == 0L) ? 1L : l);
/* 1772 */     boolean bool = false;
/*      */     
/*      */     Object object;
/*      */     
/* 1776 */     while ((object = this.result) == null) {
/* 1777 */       if (!bool) {
/* 1778 */         bool = tryPushStack(signaller); continue;
/* 1779 */       }  if (signaller.interruptControl < 0 || signaller.nanos <= 0L) {
/* 1780 */         signaller.thread = null;
/* 1781 */         cleanStack();
/* 1782 */         if (signaller.interruptControl < 0)
/* 1783 */           return null; 
/* 1784 */         throw new TimeoutException();
/*      */       } 
/* 1786 */       if (signaller.thread != null && this.result == null) {
/*      */         try {
/* 1788 */           ForkJoinPool.managedBlock(signaller);
/* 1789 */         } catch (InterruptedException interruptedException) {
/* 1790 */           signaller.interruptControl = -1;
/*      */         } 
/*      */       }
/*      */     } 
/* 1794 */     if (signaller.interruptControl < 0)
/* 1795 */       object = null; 
/* 1796 */     signaller.thread = null;
/* 1797 */     postComplete();
/* 1798 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompletableFuture() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CompletableFuture(Object paramObject) {
/* 1813 */     this.result = paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <U> CompletableFuture<U> supplyAsync(Supplier<U> paramSupplier) {
/* 1827 */     return asyncSupplyStage(asyncPool, paramSupplier);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <U> CompletableFuture<U> supplyAsync(Supplier<U> paramSupplier, Executor paramExecutor) {
/* 1843 */     return asyncSupplyStage(screenExecutor(paramExecutor), paramSupplier);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CompletableFuture<Void> runAsync(Runnable paramRunnable) {
/* 1856 */     return asyncRunStage(asyncPool, paramRunnable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CompletableFuture<Void> runAsync(Runnable paramRunnable, Executor paramExecutor) {
/* 1871 */     return asyncRunStage(screenExecutor(paramExecutor), paramRunnable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <U> CompletableFuture<U> completedFuture(U paramU) {
/* 1883 */     return new CompletableFuture<>((paramU == null) ? NIL : paramU);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDone() {
/* 1893 */     return (this.result != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T get() throws InterruptedException, ExecutionException {
/*      */     Object object;
/* 1908 */     return reportGet(((object = this.result) == null) ? waitingGet(true) : object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T get(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ExecutionException, TimeoutException {
/* 1927 */     long l = paramTimeUnit.toNanos(paramLong); Object object;
/* 1928 */     return reportGet(((object = this.result) == null) ? timedGet(l) : object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T join() {
/*      */     Object object;
/* 1947 */     return reportJoin(((object = this.result) == null) ? waitingGet(false) : object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T getNow(T paramT) {
/*      */     Object object;
/* 1962 */     return ((object = this.result) == null) ? paramT : reportJoin(object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean complete(T paramT) {
/* 1974 */     boolean bool = completeValue(paramT);
/* 1975 */     postComplete();
/* 1976 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean completeExceptionally(Throwable paramThrowable) {
/* 1988 */     if (paramThrowable == null) throw new NullPointerException(); 
/* 1989 */     boolean bool = internalComplete(new AltResult(paramThrowable));
/* 1990 */     postComplete();
/* 1991 */     return bool;
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> thenApply(Function<? super T, ? extends U> paramFunction) {
/* 1996 */     return uniApplyStage(null, paramFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> thenApplyAsync(Function<? super T, ? extends U> paramFunction) {
/* 2001 */     return uniApplyStage(asyncPool, paramFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> thenApplyAsync(Function<? super T, ? extends U> paramFunction, Executor paramExecutor) {
/* 2006 */     return uniApplyStage(screenExecutor(paramExecutor), paramFunction);
/*      */   }
/*      */   
/*      */   public CompletableFuture<Void> thenAccept(Consumer<? super T> paramConsumer) {
/* 2010 */     return uniAcceptStage(null, paramConsumer);
/*      */   }
/*      */   
/*      */   public CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> paramConsumer) {
/* 2014 */     return uniAcceptStage(asyncPool, paramConsumer);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> paramConsumer, Executor paramExecutor) {
/* 2019 */     return uniAcceptStage(screenExecutor(paramExecutor), paramConsumer);
/*      */   }
/*      */   
/*      */   public CompletableFuture<Void> thenRun(Runnable paramRunnable) {
/* 2023 */     return uniRunStage(null, paramRunnable);
/*      */   }
/*      */   
/*      */   public CompletableFuture<Void> thenRunAsync(Runnable paramRunnable) {
/* 2027 */     return uniRunStage(asyncPool, paramRunnable);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> thenRunAsync(Runnable paramRunnable, Executor paramExecutor) {
/* 2032 */     return uniRunStage(screenExecutor(paramExecutor), paramRunnable);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <U, V> CompletableFuture<V> thenCombine(CompletionStage<? extends U> paramCompletionStage, BiFunction<? super T, ? super U, ? extends V> paramBiFunction) {
/* 2038 */     return biApplyStage(null, paramCompletionStage, paramBiFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <U, V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> paramCompletionStage, BiFunction<? super T, ? super U, ? extends V> paramBiFunction) {
/* 2044 */     return biApplyStage(asyncPool, paramCompletionStage, paramBiFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <U, V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> paramCompletionStage, BiFunction<? super T, ? super U, ? extends V> paramBiFunction, Executor paramExecutor) {
/* 2050 */     return biApplyStage(screenExecutor(paramExecutor), paramCompletionStage, paramBiFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<Void> thenAcceptBoth(CompletionStage<? extends U> paramCompletionStage, BiConsumer<? super T, ? super U> paramBiConsumer) {
/* 2056 */     return biAcceptStage(null, paramCompletionStage, paramBiConsumer);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> paramCompletionStage, BiConsumer<? super T, ? super U> paramBiConsumer) {
/* 2062 */     return biAcceptStage(asyncPool, paramCompletionStage, paramBiConsumer);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> paramCompletionStage, BiConsumer<? super T, ? super U> paramBiConsumer, Executor paramExecutor) {
/* 2068 */     return biAcceptStage(screenExecutor(paramExecutor), paramCompletionStage, paramBiConsumer);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> runAfterBoth(CompletionStage<?> paramCompletionStage, Runnable paramRunnable) {
/* 2073 */     return biRunStage(null, paramCompletionStage, paramRunnable);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> paramCompletionStage, Runnable paramRunnable) {
/* 2078 */     return biRunStage(asyncPool, paramCompletionStage, paramRunnable);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> paramCompletionStage, Runnable paramRunnable, Executor paramExecutor) {
/* 2084 */     return biRunStage(screenExecutor(paramExecutor), paramCompletionStage, paramRunnable);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> applyToEither(CompletionStage<? extends T> paramCompletionStage, Function<? super T, U> paramFunction) {
/* 2089 */     return (CompletableFuture)orApplyStage(null, paramCompletionStage, paramFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> paramCompletionStage, Function<? super T, U> paramFunction) {
/* 2094 */     return (CompletableFuture)orApplyStage(asyncPool, paramCompletionStage, paramFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> paramCompletionStage, Function<? super T, U> paramFunction, Executor paramExecutor) {
/* 2100 */     return (CompletableFuture)orApplyStage(screenExecutor(paramExecutor), paramCompletionStage, paramFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> acceptEither(CompletionStage<? extends T> paramCompletionStage, Consumer<? super T> paramConsumer) {
/* 2105 */     return orAcceptStage(null, paramCompletionStage, paramConsumer);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> paramCompletionStage, Consumer<? super T> paramConsumer) {
/* 2110 */     return orAcceptStage(asyncPool, paramCompletionStage, paramConsumer);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> paramCompletionStage, Consumer<? super T> paramConsumer, Executor paramExecutor) {
/* 2116 */     return orAcceptStage(screenExecutor(paramExecutor), paramCompletionStage, paramConsumer);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> runAfterEither(CompletionStage<?> paramCompletionStage, Runnable paramRunnable) {
/* 2121 */     return orRunStage(null, paramCompletionStage, paramRunnable);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> paramCompletionStage, Runnable paramRunnable) {
/* 2126 */     return orRunStage(asyncPool, paramCompletionStage, paramRunnable);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> paramCompletionStage, Runnable paramRunnable, Executor paramExecutor) {
/* 2132 */     return orRunStage(screenExecutor(paramExecutor), paramCompletionStage, paramRunnable);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> paramFunction) {
/* 2137 */     return uniComposeStage(null, paramFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> paramFunction) {
/* 2142 */     return uniComposeStage(asyncPool, paramFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> paramFunction, Executor paramExecutor) {
/* 2148 */     return uniComposeStage(screenExecutor(paramExecutor), paramFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<T> whenComplete(BiConsumer<? super T, ? super Throwable> paramBiConsumer) {
/* 2153 */     return uniWhenCompleteStage(null, paramBiConsumer);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> paramBiConsumer) {
/* 2158 */     return uniWhenCompleteStage(asyncPool, paramBiConsumer);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> paramBiConsumer, Executor paramExecutor) {
/* 2163 */     return uniWhenCompleteStage(screenExecutor(paramExecutor), paramBiConsumer);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> handle(BiFunction<? super T, Throwable, ? extends U> paramBiFunction) {
/* 2168 */     return uniHandleStage(null, paramBiFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> paramBiFunction) {
/* 2173 */     return uniHandleStage(asyncPool, paramBiFunction);
/*      */   }
/*      */ 
/*      */   
/*      */   public <U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> paramBiFunction, Executor paramExecutor) {
/* 2178 */     return uniHandleStage(screenExecutor(paramExecutor), paramBiFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompletableFuture<T> toCompletableFuture() {
/* 2187 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompletableFuture<T> exceptionally(Function<Throwable, ? extends T> paramFunction) {
/* 2209 */     return uniExceptionallyStage(paramFunction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CompletableFuture<Void> allOf(CompletableFuture<?>... paramVarArgs) {
/* 2238 */     return andTree(paramVarArgs, 0, paramVarArgs.length - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CompletableFuture<Object> anyOf(CompletableFuture<?>... paramVarArgs) {
/* 2257 */     return orTree(paramVarArgs, 0, paramVarArgs.length - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean cancel(boolean paramBoolean) {
/* 2277 */     boolean bool = (this.result == null && internalComplete(new AltResult(new CancellationException()))) ? true : false;
/* 2278 */     postComplete();
/* 2279 */     return (bool || isCancelled());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCancelled() {
/*      */     Object object;
/* 2291 */     return (object = this.result instanceof AltResult && ((AltResult)object).ex instanceof CancellationException);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCompletedExceptionally() {
/*      */     Object object;
/* 2307 */     return (object = this.result instanceof AltResult && object != NIL);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void obtrudeValue(T paramT) {
/* 2321 */     this.result = (paramT == null) ? NIL : paramT;
/* 2322 */     postComplete();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void obtrudeException(Throwable paramThrowable) {
/* 2337 */     if (paramThrowable == null) throw new NullPointerException(); 
/* 2338 */     this.result = new AltResult(paramThrowable);
/* 2339 */     postComplete();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumberOfDependents() {
/* 2351 */     byte b = 0;
/* 2352 */     for (Completion completion = this.stack; completion != null; completion = completion.next)
/* 2353 */       b++; 
/* 2354 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2368 */     Object object = this.result;
/*      */     int i;
/* 2370 */     return super.toString() + ((object == null) ? (
/*      */       
/* 2372 */       ((i = getNumberOfDependents()) == 0) ? "[Not completed]" : ("[Not completed, " + i + " dependents]")) : ((object instanceof AltResult && ((AltResult)object).ex != null) ? "[Completed exceptionally]" : "[Completed normally]"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*      */     try {
/* 2388 */       Unsafe unsafe = Unsafe.getUnsafe();
/* 2389 */       Class<CompletableFuture> clazz = CompletableFuture.class;
/* 2390 */       RESULT = unsafe.objectFieldOffset(clazz.getDeclaredField("result"));
/* 2391 */       STACK = unsafe.objectFieldOffset(clazz.getDeclaredField("stack"));
/*      */       
/* 2393 */       NEXT = unsafe.objectFieldOffset(Completion.class.getDeclaredField("next"));
/* 2394 */     } catch (Exception exception) {
/* 2395 */       throw new Error(exception);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/CompletableFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */