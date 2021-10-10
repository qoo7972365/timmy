/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import sun.misc.InnocuousThread;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadPool
/*     */ {
/*     */   private static final String DEFAULT_THREAD_POOL_THREAD_FACTORY = "java.nio.channels.DefaultThreadPool.threadFactory";
/*     */   private static final String DEFAULT_THREAD_POOL_INITIAL_SIZE = "java.nio.channels.DefaultThreadPool.initialSize";
/*     */   private final ExecutorService executor;
/*     */   private final boolean isFixed;
/*     */   private final int poolSize;
/*     */   
/*     */   private ThreadPool(ExecutorService paramExecutorService, boolean paramBoolean, int paramInt) {
/*  57 */     this.executor = paramExecutorService;
/*  58 */     this.isFixed = paramBoolean;
/*  59 */     this.poolSize = paramInt;
/*     */   }
/*     */   
/*     */   ExecutorService executor() {
/*  63 */     return this.executor;
/*     */   }
/*     */   
/*     */   boolean isFixedThreadPool() {
/*  67 */     return this.isFixed;
/*     */   }
/*     */   
/*     */   int poolSize() {
/*  71 */     return this.poolSize;
/*     */   }
/*     */   
/*     */   static ThreadFactory defaultThreadFactory() {
/*  75 */     if (System.getSecurityManager() == null) {
/*  76 */       return paramRunnable -> {
/*     */           Thread thread = new Thread(paramRunnable);
/*     */           thread.setDaemon(true);
/*     */           return thread;
/*     */         };
/*     */     }
/*  82 */     return paramRunnable -> {
/*     */         PrivilegedAction<Thread> privilegedAction = ();
/*     */         return AccessController.<Thread>doPrivileged(privilegedAction);
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class DefaultThreadPoolHolder
/*     */   {
/*  94 */     static final ThreadPool defaultThreadPool = ThreadPool.createDefault();
/*     */   }
/*     */ 
/*     */   
/*     */   static ThreadPool getDefault() {
/*  99 */     return DefaultThreadPoolHolder.defaultThreadPool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static ThreadPool createDefault() {
/* 105 */     int i = getDefaultThreadPoolInitialSize();
/* 106 */     if (i < 0) {
/* 107 */       i = Runtime.getRuntime().availableProcessors();
/*     */     }
/* 109 */     ThreadFactory threadFactory = getDefaultThreadPoolThreadFactory();
/* 110 */     if (threadFactory == null) {
/* 111 */       threadFactory = defaultThreadFactory();
/*     */     }
/* 113 */     ExecutorService executorService = Executors.newCachedThreadPool(threadFactory);
/* 114 */     return new ThreadPool(executorService, false, i);
/*     */   }
/*     */ 
/*     */   
/*     */   static ThreadPool create(int paramInt, ThreadFactory paramThreadFactory) {
/* 119 */     if (paramInt <= 0)
/* 120 */       throw new IllegalArgumentException("'nThreads' must be > 0"); 
/* 121 */     ExecutorService executorService = Executors.newFixedThreadPool(paramInt, paramThreadFactory);
/* 122 */     return new ThreadPool(executorService, true, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ThreadPool wrap(ExecutorService paramExecutorService, int paramInt) {
/* 127 */     if (paramExecutorService == null) {
/* 128 */       throw new NullPointerException("'executor' is null");
/*     */     }
/* 130 */     if (paramExecutorService instanceof ThreadPoolExecutor) {
/* 131 */       int i = ((ThreadPoolExecutor)paramExecutorService).getMaximumPoolSize();
/* 132 */       if (i == Integer.MAX_VALUE) {
/* 133 */         if (paramInt < 0) {
/* 134 */           paramInt = Runtime.getRuntime().availableProcessors();
/*     */         } else {
/*     */           
/* 137 */           paramInt = 0;
/*     */         }
/*     */       
/*     */       }
/*     */     }
/* 142 */     else if (paramInt < 0) {
/* 143 */       paramInt = 0;
/*     */     } 
/* 145 */     return new ThreadPool(paramExecutorService, false, paramInt);
/*     */   }
/*     */   
/*     */   private static int getDefaultThreadPoolInitialSize() {
/* 149 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("java.nio.channels.DefaultThreadPool.initialSize"));
/*     */     
/* 151 */     if (str != null) {
/*     */       try {
/* 153 */         return Integer.parseInt(str);
/* 154 */       } catch (NumberFormatException numberFormatException) {
/* 155 */         throw new Error("Value of property 'java.nio.channels.DefaultThreadPool.initialSize' is invalid: " + numberFormatException);
/*     */       } 
/*     */     }
/*     */     
/* 159 */     return -1;
/*     */   }
/*     */   
/*     */   private static ThreadFactory getDefaultThreadPoolThreadFactory() {
/* 163 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("java.nio.channels.DefaultThreadPool.threadFactory"));
/*     */     
/* 165 */     if (str != null) {
/*     */       
/*     */       try {
/* 168 */         Class<?> clazz = Class.forName(str, true, ClassLoader.getSystemClassLoader());
/* 169 */         return (ThreadFactory)clazz.newInstance();
/* 170 */       } catch (ClassNotFoundException classNotFoundException) {
/* 171 */         throw new Error(classNotFoundException);
/* 172 */       } catch (InstantiationException instantiationException) {
/* 173 */         throw new Error(instantiationException);
/* 174 */       } catch (IllegalAccessException illegalAccessException) {
/* 175 */         throw new Error(illegalAccessException);
/*     */       } 
/*     */     }
/* 178 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/ThreadPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */