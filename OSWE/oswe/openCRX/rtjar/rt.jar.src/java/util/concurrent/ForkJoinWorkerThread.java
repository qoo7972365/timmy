/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.ProtectionDomain;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ForkJoinWorkerThread
/*     */   extends Thread
/*     */ {
/*     */   final ForkJoinPool pool;
/*     */   final ForkJoinPool.WorkQueue workQueue;
/*     */   private static final Unsafe U;
/*     */   private static final long THREADLOCALS;
/*     */   private static final long INHERITABLETHREADLOCALS;
/*     */   private static final long INHERITEDACCESSCONTROLCONTEXT;
/*     */   
/*     */   protected ForkJoinWorkerThread(ForkJoinPool paramForkJoinPool) {
/*  84 */     super("aForkJoinWorkerThread");
/*  85 */     this.pool = paramForkJoinPool;
/*  86 */     this.workQueue = paramForkJoinPool.registerWorker(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ForkJoinWorkerThread(ForkJoinPool paramForkJoinPool, ThreadGroup paramThreadGroup, AccessControlContext paramAccessControlContext) {
/*  94 */     super(paramThreadGroup, null, "aForkJoinWorkerThread");
/*  95 */     U.putOrderedObject(this, INHERITEDACCESSCONTROLCONTEXT, paramAccessControlContext);
/*  96 */     eraseThreadLocals();
/*  97 */     this.pool = paramForkJoinPool;
/*  98 */     this.workQueue = paramForkJoinPool.registerWorker(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ForkJoinPool getPool() {
/* 107 */     return this.pool;
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
/*     */   public int getPoolIndex() {
/* 121 */     return this.workQueue.getPoolIndex();
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
/*     */   protected void onStart() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTermination(Throwable paramThrowable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 153 */     if (this.workQueue.array == null) {
/* 154 */       Throwable throwable = null;
/*     */       try {
/* 156 */         onStart();
/* 157 */         this.pool.runWorker(this.workQueue);
/* 158 */       } catch (Throwable throwable1) {
/* 159 */         throwable = throwable1;
/*     */       } finally {
/*     */         try {
/* 162 */           onTermination(throwable);
/* 163 */         } catch (Throwable throwable1) {
/* 164 */           if (throwable == null)
/* 165 */             throwable = throwable1; 
/*     */         } finally {
/* 167 */           this.pool.deregisterWorker(this, throwable);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void eraseThreadLocals() {
/* 177 */     U.putObject(this, THREADLOCALS, null);
/* 178 */     U.putObject(this, INHERITABLETHREADLOCALS, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void afterTopLevelExec() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 194 */       U = Unsafe.getUnsafe();
/* 195 */       Class<Thread> clazz = Thread.class;
/*     */       
/* 197 */       THREADLOCALS = U.objectFieldOffset(clazz.getDeclaredField("threadLocals"));
/*     */       
/* 199 */       INHERITABLETHREADLOCALS = U.objectFieldOffset(clazz.getDeclaredField("inheritableThreadLocals"));
/*     */       
/* 201 */       INHERITEDACCESSCONTROLCONTEXT = U.objectFieldOffset(clazz.getDeclaredField("inheritedAccessControlContext"));
/*     */     }
/* 203 */     catch (Exception exception) {
/* 204 */       throw new Error(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class InnocuousForkJoinWorkerThread
/*     */     extends ForkJoinWorkerThread
/*     */   {
/* 216 */     private static final ThreadGroup innocuousThreadGroup = createThreadGroup();
/*     */ 
/*     */     
/* 219 */     private static final AccessControlContext INNOCUOUS_ACC = new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, null) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     InnocuousForkJoinWorkerThread(ForkJoinPool param1ForkJoinPool) {
/* 226 */       super(param1ForkJoinPool, innocuousThreadGroup, INNOCUOUS_ACC);
/*     */     }
/*     */ 
/*     */     
/*     */     void afterTopLevelExec() {
/* 231 */       eraseThreadLocals();
/*     */     }
/*     */ 
/*     */     
/*     */     public ClassLoader getContextClassLoader() {
/* 236 */       return ClassLoader.getSystemClassLoader();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler param1UncaughtExceptionHandler) {}
/*     */ 
/*     */     
/*     */     public void setContextClassLoader(ClassLoader param1ClassLoader) {
/* 244 */       throw new SecurityException("setContextClassLoader");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static ThreadGroup createThreadGroup() {
/*     */       try {
/* 254 */         Unsafe unsafe = Unsafe.getUnsafe();
/* 255 */         Class<Thread> clazz = Thread.class;
/* 256 */         Class<ThreadGroup> clazz1 = ThreadGroup.class;
/* 257 */         long l1 = unsafe.objectFieldOffset(clazz.getDeclaredField("group"));
/* 258 */         long l2 = unsafe.objectFieldOffset(clazz1.getDeclaredField("parent"));
/*     */         
/* 260 */         ThreadGroup threadGroup = (ThreadGroup)unsafe.getObject(Thread.currentThread(), l1);
/* 261 */         while (threadGroup != null) {
/* 262 */           ThreadGroup threadGroup1 = (ThreadGroup)unsafe.getObject(threadGroup, l2);
/* 263 */           if (threadGroup1 == null) {
/* 264 */             return new ThreadGroup(threadGroup, "InnocuousForkJoinWorkerThreadGroup");
/*     */           }
/* 266 */           threadGroup = threadGroup1;
/*     */         } 
/* 268 */       } catch (Exception exception) {
/* 269 */         throw new Error(exception);
/*     */       } 
/*     */       
/* 272 */       throw new Error("Cannot create ThreadGroup");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/concurrent/ForkJoinWorkerThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */