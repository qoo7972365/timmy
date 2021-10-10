/*      */ package java.util.concurrent;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.locks.ReentrantLock;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ForkJoinTask<V>
/*      */   implements Future<V>, Serializable
/*      */ {
/*      */   volatile int status;
/*      */   static final int DONE_MASK = -268435456;
/*      */   static final int NORMAL = -268435456;
/*      */   static final int CANCELLED = -1073741824;
/*      */   static final int EXCEPTIONAL = -2147483648;
/*      */   static final int SIGNAL = 65536;
/*      */   static final int SMASK = 65535;
/*      */   private static final ExceptionNode[] exceptionTable;
/*      */   
/*      */   private int setCompletion(int paramInt) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield status : I
/*      */     //   4: dup
/*      */     //   5: istore_2
/*      */     //   6: ifge -> 11
/*      */     //   9: iload_2
/*      */     //   10: ireturn
/*      */     //   11: getstatic java/util/concurrent/ForkJoinTask.U : Lsun/misc/Unsafe;
/*      */     //   14: aload_0
/*      */     //   15: getstatic java/util/concurrent/ForkJoinTask.STATUS : J
/*      */     //   18: iload_2
/*      */     //   19: iload_2
/*      */     //   20: iload_1
/*      */     //   21: ior
/*      */     //   22: invokevirtual compareAndSwapInt : (Ljava/lang/Object;JII)Z
/*      */     //   25: ifeq -> 0
/*      */     //   28: iload_2
/*      */     //   29: bipush #16
/*      */     //   31: iushr
/*      */     //   32: ifeq -> 55
/*      */     //   35: aload_0
/*      */     //   36: dup
/*      */     //   37: astore_3
/*      */     //   38: monitorenter
/*      */     //   39: aload_0
/*      */     //   40: invokevirtual notifyAll : ()V
/*      */     //   43: aload_3
/*      */     //   44: monitorexit
/*      */     //   45: goto -> 55
/*      */     //   48: astore #4
/*      */     //   50: aload_3
/*      */     //   51: monitorexit
/*      */     //   52: aload #4
/*      */     //   54: athrow
/*      */     //   55: iload_1
/*      */     //   56: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #268	-> 0
/*      */     //   #269	-> 9
/*      */     //   #270	-> 11
/*      */     //   #271	-> 28
/*      */     //   #272	-> 35
/*      */     //   #273	-> 55
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   39	45	48	finally
/*      */     //   48	52	48	finally
/*      */   }
/*      */   
/*      */   final int doExec() {
/*      */     int i;
/*  287 */     if ((i = this.status) >= 0) {
/*      */       boolean bool; try {
/*  289 */         bool = exec();
/*  290 */       } catch (Throwable throwable) {
/*  291 */         return setExceptionalCompletion(throwable);
/*      */       } 
/*  293 */       if (bool)
/*  294 */         i = setCompletion(-268435456); 
/*      */     } 
/*  296 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void internalWait(long paramLong) {
/*      */     int i;
/*  307 */     if ((i = this.status) >= 0 && U
/*  308 */       .compareAndSwapInt(this, STATUS, i, i | 0x10000)) {
/*  309 */       synchronized (this) {
/*  310 */         if (this.status >= 0) { 
/*  311 */           try { wait(paramLong); } catch (InterruptedException interruptedException) {} }
/*      */         else
/*  313 */         { notifyAll(); }
/*      */       
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int externalAwaitDone() {
/*  326 */     int i = (this instanceof CountedCompleter) ? ForkJoinPool.common.externalHelpComplete((CountedCompleter)this, 0) : (ForkJoinPool.common.tryExternalUnpush(this) ? doExec() : 0);
/*  327 */     if (i && (i = this.status) >= 0) {
/*  328 */       boolean bool = false;
/*      */       while (true) {
/*  330 */         if (U.compareAndSwapInt(this, STATUS, i, i | 0x10000))
/*  331 */           synchronized (this) {
/*  332 */             if (this.status >= 0) {
/*      */               try {
/*  334 */                 wait(0L);
/*  335 */               } catch (InterruptedException interruptedException) {
/*  336 */                 bool = true;
/*      */               } 
/*      */             } else {
/*      */               
/*  340 */               notifyAll();
/*      */             } 
/*      */           }  
/*  343 */         if ((i = this.status) < 0)
/*  344 */         { if (bool)
/*  345 */             Thread.currentThread().interrupt();  break; } 
/*      */       } 
/*  347 */     }  return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int externalInterruptibleAwaitDone() throws InterruptedException {
/*  355 */     if (Thread.interrupted())
/*  356 */       throw new InterruptedException();  int i;
/*  357 */     if ((i = this.status) >= 0)
/*      */     {
/*      */ 
/*      */       
/*  361 */       if ((i = (this instanceof CountedCompleter) ? ForkJoinPool.common.externalHelpComplete((CountedCompleter)this, 0) : (ForkJoinPool.common.tryExternalUnpush(this) ? doExec() : 0)) >= 0)
/*      */       {
/*  363 */         while ((i = this.status) >= 0) {
/*  364 */           if (U.compareAndSwapInt(this, STATUS, i, i | 0x10000))
/*  365 */             synchronized (this) {
/*  366 */               if (this.status >= 0) {
/*  367 */                 wait(0L);
/*      */               } else {
/*  369 */                 notifyAll();
/*      */               } 
/*      */             }  
/*      */         }  } 
/*      */     }
/*  374 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int doJoin() {
/*      */     int i;
/*      */     Thread thread;
/*      */     ForkJoinWorkerThread forkJoinWorkerThread;
/*      */     ForkJoinPool.WorkQueue workQueue;
/*  386 */     return ((i = this.status) < 0) ? i : (
/*  387 */       (thread = Thread.currentThread() instanceof ForkJoinWorkerThread) ? (((workQueue = (forkJoinWorkerThread = (ForkJoinWorkerThread)thread).workQueue)
/*      */       
/*  389 */       .tryUnpush(this) && (i = doExec()) < 0) ? i : forkJoinWorkerThread.pool
/*  390 */       .awaitJoin(workQueue, this, 0L)) : 
/*  391 */       externalAwaitDone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int doInvoke() {
/*      */     int i;
/*      */     Thread thread;
/*      */     ForkJoinWorkerThread forkJoinWorkerThread;
/*  401 */     return ((i = doExec()) < 0) ? i : (
/*  402 */       (thread = Thread.currentThread() instanceof ForkJoinWorkerThread) ? (forkJoinWorkerThread = (ForkJoinWorkerThread)thread).pool
/*      */       
/*  404 */       .awaitJoin(forkJoinWorkerThread.workQueue, this, 0L) : 
/*  405 */       externalAwaitDone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class ExceptionNode
/*      */     extends WeakReference<ForkJoinTask<?>>
/*      */   {
/*      */     final Throwable ex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ExceptionNode next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final long thrower;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int hashCode;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ExceptionNode(ForkJoinTask<?> param1ForkJoinTask, Throwable param1Throwable, ExceptionNode param1ExceptionNode) {
/*  446 */       super(param1ForkJoinTask, (ReferenceQueue)ForkJoinTask.exceptionTableRefQueue);
/*  447 */       this.ex = param1Throwable;
/*  448 */       this.next = param1ExceptionNode;
/*  449 */       this.thrower = Thread.currentThread().getId();
/*  450 */       this.hashCode = System.identityHashCode(param1ForkJoinTask);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int recordExceptionalCompletion(Throwable paramThrowable) {
/*      */     int i;
/*  461 */     if ((i = this.status) >= 0) {
/*  462 */       int j = System.identityHashCode(this);
/*  463 */       ReentrantLock reentrantLock = exceptionTableLock;
/*  464 */       reentrantLock.lock();
/*      */       try {
/*  466 */         expungeStaleExceptions();
/*  467 */         ExceptionNode[] arrayOfExceptionNode = exceptionTable;
/*  468 */         int k = j & arrayOfExceptionNode.length - 1;
/*  469 */         for (ExceptionNode exceptionNode = arrayOfExceptionNode[k];; exceptionNode = exceptionNode.next) {
/*  470 */           if (exceptionNode == null) {
/*  471 */             arrayOfExceptionNode[k] = new ExceptionNode(this, paramThrowable, arrayOfExceptionNode[k]);
/*      */             break;
/*      */           } 
/*  474 */           if (exceptionNode.get() == this)
/*      */             break; 
/*      */         } 
/*      */       } finally {
/*  478 */         reentrantLock.unlock();
/*      */       } 
/*  480 */       i = setCompletion(-2147483648);
/*      */     } 
/*  482 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int setExceptionalCompletion(Throwable paramThrowable) {
/*  491 */     int i = recordExceptionalCompletion(paramThrowable);
/*  492 */     if ((i & 0xF0000000) == Integer.MIN_VALUE)
/*  493 */       internalPropagateException(paramThrowable); 
/*  494 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void internalPropagateException(Throwable paramThrowable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final void cancelIgnoringExceptions(ForkJoinTask<?> paramForkJoinTask) {
/*  510 */     if (paramForkJoinTask != null && paramForkJoinTask.status >= 0) {
/*      */       try {
/*  512 */         paramForkJoinTask.cancel(false);
/*  513 */       } catch (Throwable throwable) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void clearExceptionalCompletion() {
/*  522 */     int i = System.identityHashCode(this);
/*  523 */     ReentrantLock reentrantLock = exceptionTableLock;
/*  524 */     reentrantLock.lock();
/*      */     try {
/*  526 */       ExceptionNode[] arrayOfExceptionNode = exceptionTable;
/*  527 */       int j = i & arrayOfExceptionNode.length - 1;
/*  528 */       ExceptionNode exceptionNode1 = arrayOfExceptionNode[j];
/*  529 */       ExceptionNode exceptionNode2 = null;
/*  530 */       while (exceptionNode1 != null) {
/*  531 */         ExceptionNode exceptionNode = exceptionNode1.next;
/*  532 */         if (exceptionNode1.get() == this) {
/*  533 */           if (exceptionNode2 == null) {
/*  534 */             arrayOfExceptionNode[j] = exceptionNode; break;
/*      */           } 
/*  536 */           exceptionNode2.next = exceptionNode;
/*      */           break;
/*      */         } 
/*  539 */         exceptionNode2 = exceptionNode1;
/*  540 */         exceptionNode1 = exceptionNode;
/*      */       } 
/*  542 */       expungeStaleExceptions();
/*  543 */       this.status = 0;
/*      */     } finally {
/*  545 */       reentrantLock.unlock();
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
/*      */   private Throwable getThrowableException() {
/*      */     ExceptionNode exceptionNode;
/*  564 */     if ((this.status & 0xF0000000) != Integer.MIN_VALUE)
/*  565 */       return null; 
/*  566 */     int i = System.identityHashCode(this);
/*      */     
/*  568 */     ReentrantLock reentrantLock = exceptionTableLock;
/*  569 */     reentrantLock.lock();
/*      */     try {
/*  571 */       expungeStaleExceptions();
/*  572 */       ExceptionNode[] arrayOfExceptionNode = exceptionTable;
/*  573 */       exceptionNode = arrayOfExceptionNode[i & arrayOfExceptionNode.length - 1];
/*  574 */       while (exceptionNode != null && exceptionNode.get() != this)
/*  575 */         exceptionNode = exceptionNode.next; 
/*      */     } finally {
/*  577 */       reentrantLock.unlock();
/*      */     } 
/*      */     Throwable throwable;
/*  580 */     if (exceptionNode == null || (throwable = exceptionNode.ex) == null)
/*  581 */       return null; 
/*  582 */     if (exceptionNode.thrower != Thread.currentThread().getId()) {
/*  583 */       Class<?> clazz = throwable.getClass();
/*      */       try {
/*  585 */         Constructor<Throwable> constructor = null;
/*  586 */         Constructor[] arrayOfConstructor = (Constructor[])clazz.getConstructors();
/*  587 */         for (byte b = 0; b < arrayOfConstructor.length; b++) {
/*  588 */           Constructor<Throwable> constructor1 = arrayOfConstructor[b];
/*  589 */           Class[] arrayOfClass = constructor1.getParameterTypes();
/*  590 */           if (arrayOfClass.length == 0) {
/*  591 */             constructor = constructor1;
/*  592 */           } else if (arrayOfClass.length == 1 && arrayOfClass[0] == Throwable.class) {
/*  593 */             Throwable throwable1 = constructor1.newInstance(new Object[] { throwable });
/*  594 */             return (throwable1 == null) ? throwable : throwable1;
/*      */           } 
/*      */         } 
/*  597 */         if (constructor != null) {
/*  598 */           Throwable throwable1 = constructor.newInstance(new Object[0]);
/*  599 */           if (throwable1 != null) {
/*  600 */             throwable1.initCause(throwable);
/*  601 */             return throwable1;
/*      */           } 
/*      */         } 
/*  604 */       } catch (Exception exception) {}
/*      */     } 
/*      */     
/*  607 */     return throwable;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void expungeStaleExceptions() {
/*      */     Reference<?> reference;
/*  614 */     while ((reference = exceptionTableRefQueue.poll()) != null) {
/*  615 */       if (reference instanceof ExceptionNode) {
/*  616 */         int i = ((ExceptionNode)reference).hashCode;
/*  617 */         ExceptionNode[] arrayOfExceptionNode = exceptionTable;
/*  618 */         int j = i & arrayOfExceptionNode.length - 1;
/*  619 */         ExceptionNode exceptionNode1 = arrayOfExceptionNode[j];
/*  620 */         ExceptionNode exceptionNode2 = null;
/*  621 */         while (exceptionNode1 != null) {
/*  622 */           ExceptionNode exceptionNode = exceptionNode1.next;
/*  623 */           if (exceptionNode1 == reference) {
/*  624 */             if (exceptionNode2 == null) {
/*  625 */               arrayOfExceptionNode[j] = exceptionNode; break;
/*      */             } 
/*  627 */             exceptionNode2.next = exceptionNode;
/*      */             break;
/*      */           } 
/*  630 */           exceptionNode2 = exceptionNode1;
/*  631 */           exceptionNode1 = exceptionNode;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final void helpExpungeStaleExceptions() {
/*  642 */     ReentrantLock reentrantLock = exceptionTableLock;
/*  643 */     if (reentrantLock.tryLock()) {
/*      */       try {
/*  645 */         expungeStaleExceptions();
/*      */       } finally {
/*  647 */         reentrantLock.unlock();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void rethrow(Throwable paramThrowable) {
/*  656 */     if (paramThrowable != null) {
/*  657 */       uncheckedThrow(paramThrowable);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T extends Throwable> void uncheckedThrow(Throwable paramThrowable) throws T {
/*  667 */     throw (T)paramThrowable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void reportException(int paramInt) {
/*  674 */     if (paramInt == -1073741824)
/*  675 */       throw new CancellationException(); 
/*  676 */     if (paramInt == Integer.MIN_VALUE) {
/*  677 */       rethrow(getThrowableException());
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
/*      */   public final ForkJoinTask<V> fork() {
/*      */     Thread thread;
/*  699 */     if (thread = Thread.currentThread() instanceof ForkJoinWorkerThread) {
/*  700 */       ((ForkJoinWorkerThread)thread).workQueue.push(this);
/*      */     } else {
/*  702 */       ForkJoinPool.common.externalPush(this);
/*  703 */     }  return this;
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
/*      */   public final V join() {
/*      */     int i;
/*  719 */     if ((i = doJoin() & 0xF0000000) != -268435456)
/*  720 */       reportException(i); 
/*  721 */     return getRawResult();
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
/*      */   public final V invoke() {
/*      */     int i;
/*  734 */     if ((i = doInvoke() & 0xF0000000) != -268435456)
/*  735 */       reportException(i); 
/*  736 */     return getRawResult();
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
/*      */   public static void invokeAll(ForkJoinTask<?> paramForkJoinTask1, ForkJoinTask<?> paramForkJoinTask2) {
/*  758 */     paramForkJoinTask2.fork(); int i;
/*  759 */     if ((i = paramForkJoinTask1.doInvoke() & 0xF0000000) != -268435456)
/*  760 */       paramForkJoinTask1.reportException(i);  int j;
/*  761 */     if ((j = paramForkJoinTask2.doJoin() & 0xF0000000) != -268435456) {
/*  762 */       paramForkJoinTask2.reportException(j);
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
/*      */   public static void invokeAll(ForkJoinTask<?>... paramVarArgs) {
/*  781 */     Throwable throwable = null;
/*  782 */     int i = paramVarArgs.length - 1; int j;
/*  783 */     for (j = i; j >= 0; j--) {
/*  784 */       ForkJoinTask<?> forkJoinTask = paramVarArgs[j];
/*  785 */       if (forkJoinTask == null) {
/*  786 */         if (throwable == null) {
/*  787 */           throwable = new NullPointerException();
/*      */         }
/*  789 */       } else if (j != 0) {
/*  790 */         forkJoinTask.fork();
/*  791 */       } else if (forkJoinTask.doInvoke() < -268435456 && throwable == null) {
/*  792 */         throwable = forkJoinTask.getException();
/*      */       } 
/*  794 */     }  for (j = 1; j <= i; j++) {
/*  795 */       ForkJoinTask<?> forkJoinTask = paramVarArgs[j];
/*  796 */       if (forkJoinTask != null)
/*  797 */         if (throwable != null) {
/*  798 */           forkJoinTask.cancel(false);
/*  799 */         } else if (forkJoinTask.doJoin() < -268435456) {
/*  800 */           throwable = forkJoinTask.getException();
/*      */         }  
/*      */     } 
/*  803 */     if (throwable != null) {
/*  804 */       rethrow(throwable);
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
/*      */   public static <T extends ForkJoinTask<?>> Collection<T> invokeAll(Collection<T> paramCollection) {
/*  826 */     if (!(paramCollection instanceof java.util.RandomAccess) || !(paramCollection instanceof List)) {
/*  827 */       invokeAll((ForkJoinTask<?>[])paramCollection.<ForkJoinTask>toArray(new ForkJoinTask[paramCollection.size()]));
/*  828 */       return paramCollection;
/*      */     } 
/*      */     
/*  831 */     List<ForkJoinTask> list = (List)paramCollection;
/*      */     
/*  833 */     Throwable throwable = null;
/*  834 */     int i = list.size() - 1; int j;
/*  835 */     for (j = i; j >= 0; j--) {
/*  836 */       ForkJoinTask forkJoinTask = list.get(j);
/*  837 */       if (forkJoinTask == null) {
/*  838 */         if (throwable == null) {
/*  839 */           throwable = new NullPointerException();
/*      */         }
/*  841 */       } else if (j != 0) {
/*  842 */         forkJoinTask.fork();
/*  843 */       } else if (forkJoinTask.doInvoke() < -268435456 && throwable == null) {
/*  844 */         throwable = forkJoinTask.getException();
/*      */       } 
/*  846 */     }  for (j = 1; j <= i; j++) {
/*  847 */       ForkJoinTask forkJoinTask = list.get(j);
/*  848 */       if (forkJoinTask != null)
/*  849 */         if (throwable != null) {
/*  850 */           forkJoinTask.cancel(false);
/*  851 */         } else if (forkJoinTask.doJoin() < -268435456) {
/*  852 */           throwable = forkJoinTask.getException();
/*      */         }  
/*      */     } 
/*  855 */     if (throwable != null)
/*  856 */       rethrow(throwable); 
/*  857 */     return paramCollection;
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
/*      */ 
/*      */   
/*      */   public boolean cancel(boolean paramBoolean) {
/*  888 */     return ((setCompletion(-1073741824) & 0xF0000000) == -1073741824);
/*      */   }
/*      */   
/*      */   public final boolean isDone() {
/*  892 */     return (this.status < 0);
/*      */   }
/*      */   
/*      */   public final boolean isCancelled() {
/*  896 */     return ((this.status & 0xF0000000) == -1073741824);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isCompletedAbnormally() {
/*  905 */     return (this.status < -268435456);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isCompletedNormally() {
/*  916 */     return ((this.status & 0xF0000000) == -268435456);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Throwable getException() {
/*  927 */     int i = this.status & 0xF0000000;
/*  928 */     return (i >= -268435456) ? null : ((i == -1073741824) ? new CancellationException() : 
/*      */       
/*  930 */       getThrowableException());
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
/*      */   public void completeExceptionally(Throwable paramThrowable) {
/*  948 */     setExceptionalCompletion((paramThrowable instanceof RuntimeException || paramThrowable instanceof Error) ? paramThrowable : new RuntimeException(paramThrowable));
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
/*      */   public void complete(V paramV) {
/*      */     try {
/*  968 */       setRawResult(paramV);
/*  969 */     } catch (Throwable throwable) {
/*  970 */       setExceptionalCompletion(throwable);
/*      */       return;
/*      */     } 
/*  973 */     setCompletion(-268435456);
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
/*      */   public final void quietlyComplete() {
/*  985 */     setCompletion(-268435456);
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
/*      */   public final V get() throws InterruptedException, ExecutionException {
/* 1001 */     int i = (Thread.currentThread() instanceof ForkJoinWorkerThread) ? doJoin() : externalInterruptibleAwaitDone();
/*      */     
/* 1003 */     if ((i &= 0xF0000000) == -1073741824)
/* 1004 */       throw new CancellationException();  Throwable throwable;
/* 1005 */     if (i == Integer.MIN_VALUE && (throwable = getThrowableException()) != null)
/* 1006 */       throw new ExecutionException(throwable); 
/* 1007 */     return getRawResult();
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
/*      */   public final V get(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ExecutionException, TimeoutException {
/* 1027 */     long l = paramTimeUnit.toNanos(paramLong);
/* 1028 */     if (Thread.interrupted())
/* 1029 */       throw new InterruptedException();  int i;
/* 1030 */     if ((i = this.status) >= 0 && l > 0L) {
/* 1031 */       long l1 = System.nanoTime() + l;
/* 1032 */       long l2 = (l1 == 0L) ? 1L : l1;
/* 1033 */       Thread thread = Thread.currentThread();
/* 1034 */       if (thread instanceof ForkJoinWorkerThread) {
/* 1035 */         ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread)thread;
/* 1036 */         i = forkJoinWorkerThread.pool.awaitJoin(forkJoinWorkerThread.workQueue, this, l2);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1042 */       else if ((i = (this instanceof CountedCompleter) ? ForkJoinPool.common.externalHelpComplete((CountedCompleter)this, 0) : (ForkJoinPool.common.tryExternalUnpush(this) ? doExec() : 0)) >= 0) {
/*      */         long l3;
/* 1044 */         while ((i = this.status) >= 0 && (
/* 1045 */           l3 = l2 - System.nanoTime()) > 0L) {
/* 1046 */           long l4; if ((l4 = TimeUnit.NANOSECONDS.toMillis(l3)) > 0L && U
/* 1047 */             .compareAndSwapInt(this, STATUS, i, i | 0x10000))
/* 1048 */             synchronized (this) {
/* 1049 */               if (this.status >= 0) {
/* 1050 */                 wait(l4);
/*      */               } else {
/* 1052 */                 notifyAll();
/*      */               } 
/*      */             }  
/*      */         } 
/*      */       } 
/*      */     } 
/* 1058 */     if (i >= 0)
/* 1059 */       i = this.status; 
/* 1060 */     if ((i &= 0xF0000000) != -268435456) {
/*      */       
/* 1062 */       if (i == -1073741824)
/* 1063 */         throw new CancellationException(); 
/* 1064 */       if (i != Integer.MIN_VALUE)
/* 1065 */         throw new TimeoutException();  Throwable throwable;
/* 1066 */       if ((throwable = getThrowableException()) != null)
/* 1067 */         throw new ExecutionException(throwable); 
/*      */     } 
/* 1069 */     return getRawResult();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void quietlyJoin() {
/* 1079 */     doJoin();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void quietlyInvoke() {
/* 1088 */     doInvoke();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void helpQuiesce() {
/*      */     Thread thread;
/* 1100 */     if (thread = Thread.currentThread() instanceof ForkJoinWorkerThread) {
/* 1101 */       ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread)thread;
/* 1102 */       forkJoinWorkerThread.pool.helpQuiescePool(forkJoinWorkerThread.workQueue);
/*      */     } else {
/*      */       
/* 1105 */       ForkJoinPool.quiesceCommonPool();
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
/*      */   public void reinitialize() {
/* 1125 */     if ((this.status & 0xF0000000) == Integer.MIN_VALUE) {
/* 1126 */       clearExceptionalCompletion();
/*      */     } else {
/* 1128 */       this.status = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ForkJoinPool getPool() {
/* 1139 */     Thread thread = Thread.currentThread();
/* 1140 */     return (thread instanceof ForkJoinWorkerThread) ? ((ForkJoinWorkerThread)thread).pool : null;
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
/*      */   public static boolean inForkJoinPool() {
/* 1153 */     return Thread.currentThread() instanceof ForkJoinWorkerThread;
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
/*      */   public boolean tryUnfork() {
/*      */     Thread thread;
/* 1168 */     return (thread = Thread.currentThread() instanceof ForkJoinWorkerThread) ? ((ForkJoinWorkerThread)thread).workQueue
/* 1169 */       .tryUnpush(this) : ForkJoinPool.common
/* 1170 */       .tryExternalUnpush(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getQueuedTaskCount() {
/*      */     ForkJoinPool.WorkQueue workQueue;
/*      */     Thread thread;
/* 1183 */     if (thread = Thread.currentThread() instanceof ForkJoinWorkerThread) {
/* 1184 */       workQueue = ((ForkJoinWorkerThread)thread).workQueue;
/*      */     } else {
/* 1186 */       workQueue = ForkJoinPool.commonSubmitterQueue();
/* 1187 */     }  return (workQueue == null) ? 0 : workQueue.queueSize();
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
/*      */   public static int getSurplusQueuedTaskCount() {
/* 1204 */     return ForkJoinPool.getSurplusQueuedTaskCount();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static ForkJoinTask<?> peekNextLocalTask() {
/*      */     ForkJoinPool.WorkQueue workQueue;
/*      */     Thread thread;
/* 1259 */     if (thread = Thread.currentThread() instanceof ForkJoinWorkerThread) {
/* 1260 */       workQueue = ((ForkJoinWorkerThread)thread).workQueue;
/*      */     } else {
/* 1262 */       workQueue = ForkJoinPool.commonSubmitterQueue();
/* 1263 */     }  return (workQueue == null) ? null : workQueue.peek();
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
/*      */   protected static ForkJoinTask<?> pollNextLocalTask() {
/*      */     Thread thread;
/* 1277 */     return (thread = Thread.currentThread() instanceof ForkJoinWorkerThread) ? ((ForkJoinWorkerThread)thread).workQueue
/* 1278 */       .nextLocalTask() : null;
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
/*      */   protected static ForkJoinTask<?> pollTask() {
/*      */     Thread thread;
/*      */     ForkJoinWorkerThread forkJoinWorkerThread;
/* 1297 */     return (thread = Thread.currentThread() instanceof ForkJoinWorkerThread) ? (forkJoinWorkerThread = (ForkJoinWorkerThread)thread).pool
/* 1298 */       .nextTaskFor(forkJoinWorkerThread.workQueue) : null;
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
/*      */   public final short getForkJoinTaskTag() {
/* 1311 */     return (short)this.status;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final short setForkJoinTaskTag(short paramShort) {
/*      */     int i;
/*      */     do {
/*      */     
/* 1323 */     } while (!U.compareAndSwapInt(this, STATUS, i = this.status, i & 0xFFFF0000 | paramShort & 0xFFFF));
/*      */     
/* 1325 */     return (short)i;
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
/*      */   public final boolean compareAndSetForkJoinTaskTag(short paramShort1, short paramShort2) {
/*      */     int i;
/*      */     do {
/* 1345 */       if ((short)(i = this.status) != paramShort1)
/* 1346 */         return false; 
/* 1347 */     } while (!U.compareAndSwapInt(this, STATUS, i, i & 0xFFFF0000 | paramShort2 & 0xFFFF));
/*      */     
/* 1349 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   static final class AdaptedRunnable<T>
/*      */     extends ForkJoinTask<T>
/*      */     implements RunnableFuture<T>
/*      */   {
/*      */     final Runnable runnable;
/*      */     
/*      */     T result;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     AdaptedRunnable(Runnable param1Runnable, T param1T) {
/* 1363 */       if (param1Runnable == null) throw new NullPointerException(); 
/* 1364 */       this.runnable = param1Runnable;
/* 1365 */       this.result = param1T;
/*      */     }
/* 1367 */     public final T getRawResult() { return this.result; }
/* 1368 */     public final void setRawResult(T param1T) { this.result = param1T; }
/* 1369 */     public final boolean exec() { this.runnable.run(); return true; } public final void run() {
/* 1370 */       invoke();
/*      */     }
/*      */   }
/*      */   
/*      */   static final class AdaptedRunnableAction
/*      */     extends ForkJoinTask<Void>
/*      */     implements RunnableFuture<Void> {
/*      */     final Runnable runnable;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     AdaptedRunnableAction(Runnable param1Runnable) {
/* 1381 */       if (param1Runnable == null) throw new NullPointerException(); 
/* 1382 */       this.runnable = param1Runnable;
/*      */     } public final void setRawResult(Void param1Void) {} public final Void getRawResult() {
/* 1384 */       return null;
/*      */     } public final boolean exec() {
/* 1386 */       this.runnable.run(); return true; } public final void run() {
/* 1387 */       invoke();
/*      */     }
/*      */   }
/*      */   
/*      */   static final class RunnableExecuteAction
/*      */     extends ForkJoinTask<Void> {
/*      */     final Runnable runnable;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     RunnableExecuteAction(Runnable param1Runnable) {
/* 1397 */       if (param1Runnable == null) throw new NullPointerException(); 
/* 1398 */       this.runnable = param1Runnable;
/*      */     } public final Void getRawResult() {
/* 1400 */       return null;
/*      */     } public final void setRawResult(Void param1Void) {} public final boolean exec() {
/* 1402 */       this.runnable.run(); return true;
/*      */     } void internalPropagateException(Throwable param1Throwable) {
/* 1404 */       rethrow(param1Throwable);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class AdaptedCallable<T>
/*      */     extends ForkJoinTask<T>
/*      */     implements RunnableFuture<T>
/*      */   {
/*      */     final Callable<? extends T> callable;
/*      */     T result;
/*      */     private static final long serialVersionUID = 2838392045355241008L;
/*      */     
/*      */     AdaptedCallable(Callable<? extends T> param1Callable) {
/* 1417 */       if (param1Callable == null) throw new NullPointerException(); 
/* 1418 */       this.callable = param1Callable;
/*      */     }
/* 1420 */     public final T getRawResult() { return this.result; } public final void setRawResult(T param1T) {
/* 1421 */       this.result = param1T;
/*      */     } public final boolean exec() {
/*      */       try {
/* 1424 */         this.result = this.callable.call();
/* 1425 */         return true;
/* 1426 */       } catch (Error error) {
/* 1427 */         throw error;
/* 1428 */       } catch (RuntimeException runtimeException) {
/* 1429 */         throw runtimeException;
/* 1430 */       } catch (Exception exception) {
/* 1431 */         throw new RuntimeException(exception);
/*      */       } 
/*      */     } public final void run() {
/* 1434 */       invoke();
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
/*      */   public static ForkJoinTask<?> adapt(Runnable paramRunnable) {
/* 1447 */     return new AdaptedRunnableAction(paramRunnable);
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
/*      */   public static <T> ForkJoinTask<T> adapt(Runnable paramRunnable, T paramT) {
/* 1461 */     return new AdaptedRunnable<>(paramRunnable, paramT);
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
/*      */   public static <T> ForkJoinTask<T> adapt(Callable<? extends T> paramCallable) {
/* 1475 */     return new AdaptedCallable<>(paramCallable);
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1492 */     paramObjectOutputStream.defaultWriteObject();
/* 1493 */     paramObjectOutputStream.writeObject(getException());
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1505 */     paramObjectInputStream.defaultReadObject();
/* 1506 */     Object object = paramObjectInputStream.readObject();
/* 1507 */     if (object != null) {
/* 1508 */       setExceptionalCompletion((Throwable)object);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1516 */   private static final ReentrantLock exceptionTableLock = new ReentrantLock();
/* 1517 */   private static final ReferenceQueue<Object> exceptionTableRefQueue = new ReferenceQueue(); private static final int EXCEPTION_MAP_CAPACITY = 32; private static final long serialVersionUID = -7721805057305804111L; static {
/* 1518 */     exceptionTable = new ExceptionNode[32];
/*      */     try {
/* 1520 */       U = Unsafe.getUnsafe();
/* 1521 */       Class<ForkJoinTask> clazz = ForkJoinTask.class;
/*      */       
/* 1523 */       STATUS = U.objectFieldOffset(clazz.getDeclaredField("status"));
/* 1524 */     } catch (Exception exception) {
/* 1525 */       throw new Error(exception);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static final Unsafe U;
/*      */   private static final long STATUS;
/*      */   
/*      */   public abstract V getRawResult();
/*      */   
/*      */   protected abstract void setRawResult(V paramV);
/*      */   
/*      */   protected abstract boolean exec();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/ForkJoinTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */