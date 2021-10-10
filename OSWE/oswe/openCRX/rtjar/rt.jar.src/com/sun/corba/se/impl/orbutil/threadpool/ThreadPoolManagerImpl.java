/*     */ package com.sun.corba.se.impl.orbutil.threadpool;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.NoSuchThreadPoolException;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.ThreadPoolChooser;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.ThreadPoolManager;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadPoolManagerImpl
/*     */   implements ThreadPoolManager
/*     */ {
/*  56 */   private static final ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.transport");
/*     */ 
/*     */   
/*  59 */   private ThreadGroup threadGroup = getThreadGroup();
/*  60 */   private ThreadPool threadPool = new ThreadPoolImpl(this.threadGroup, "default-threadpool");
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static AtomicInteger tgCount = new AtomicInteger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ThreadGroup getThreadGroup() {
/*     */     ThreadGroup threadGroup;
/*     */     try {
/*  88 */       threadGroup = AccessController.<ThreadGroup>doPrivileged(new PrivilegedAction<ThreadGroup>()
/*     */           {
/*     */             public ThreadGroup run() {
/*  91 */               ThreadGroup threadGroup1 = Thread.currentThread().getThreadGroup();
/*  92 */               ThreadGroup threadGroup2 = threadGroup1;
/*     */               try {
/*  94 */                 while (threadGroup2 != null) {
/*  95 */                   threadGroup1 = threadGroup2;
/*  96 */                   threadGroup2 = threadGroup1.getParent();
/*     */                 } 
/*  98 */               } catch (SecurityException securityException) {}
/*     */ 
/*     */               
/* 101 */               return new ThreadGroup(threadGroup1, "ORB ThreadGroup " + ThreadPoolManagerImpl.tgCount.getAndIncrement());
/*     */             }
/*     */           });
/*     */     }
/* 105 */     catch (SecurityException securityException) {
/*     */       
/* 107 */       threadGroup = Thread.currentThread().getThreadGroup();
/*     */     } 
/*     */     
/* 110 */     return threadGroup;
/*     */   }
/*     */   
/*     */   public void close() {
/*     */     try {
/* 115 */       this.threadPool.close();
/* 116 */     } catch (IOException iOException) {
/* 117 */       wrapper.threadPoolCloseError();
/*     */     } 
/*     */     
/*     */     try {
/* 121 */       boolean bool = this.threadGroup.isDestroyed();
/* 122 */       int i = this.threadGroup.activeCount();
/* 123 */       int j = this.threadGroup.activeGroupCount();
/*     */       
/* 125 */       if (bool) {
/* 126 */         wrapper.threadGroupIsDestroyed(this.threadGroup);
/*     */       } else {
/* 128 */         if (i > 0) {
/* 129 */           wrapper.threadGroupHasActiveThreadsInClose(this.threadGroup, Integer.valueOf(i));
/*     */         }
/* 131 */         if (j > 0) {
/* 132 */           wrapper.threadGroupHasSubGroupsInClose(this.threadGroup, Integer.valueOf(j));
/*     */         }
/* 134 */         this.threadGroup.destroy();
/*     */       } 
/* 136 */     } catch (IllegalThreadStateException illegalThreadStateException) {
/* 137 */       wrapper.threadGroupDestroyFailed(illegalThreadStateException, this.threadGroup);
/*     */     } 
/*     */     
/* 140 */     this.threadGroup = null;
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
/*     */   public ThreadPool getThreadPool(String paramString) throws NoSuchThreadPoolException {
/* 153 */     return this.threadPool;
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
/*     */   public ThreadPool getThreadPool(int paramInt) throws NoSuchThreadPoolException {
/* 167 */     return this.threadPool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getThreadPoolNumericId(String paramString) {
/* 177 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getThreadPoolStringId(int paramInt) {
/* 185 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThreadPool getDefaultThreadPool() {
/* 192 */     return this.threadPool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThreadPoolChooser getThreadPoolChooser(String paramString) {
/* 202 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThreadPoolChooser getThreadPoolChooser(int paramInt) {
/* 212 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThreadPoolChooser(String paramString, ThreadPoolChooser paramThreadPoolChooser) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getThreadPoolChooserNumericId(String paramString) {
/* 232 */     return 0;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/threadpool/ThreadPoolManagerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */