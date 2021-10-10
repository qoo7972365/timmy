/*     */ package com.sun.xml.internal.ws.api.pipe;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.api.server.ContainerResolver;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
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
/*     */ public class Engine
/*     */ {
/*     */   private volatile Executor threadPool;
/*     */   public final String id;
/*     */   private final Container container;
/*     */   
/*     */   String getId() {
/*  49 */     return this.id;
/*  50 */   } Container getContainer() { return this.container; } Executor getExecutor() {
/*  51 */     return this.threadPool;
/*     */   }
/*     */   public Engine(String id, Executor threadPool) {
/*  54 */     this(id, ContainerResolver.getDefault().getContainer(), threadPool);
/*     */   }
/*     */   
/*     */   public Engine(String id, Container container, Executor threadPool) {
/*  58 */     this(id, container);
/*  59 */     this.threadPool = (threadPool != null) ? wrap(threadPool) : null;
/*     */   }
/*     */   
/*     */   public Engine(String id) {
/*  63 */     this(id, ContainerResolver.getDefault().getContainer());
/*     */   }
/*     */   
/*     */   public Engine(String id, Container container) {
/*  67 */     this.id = id;
/*  68 */     this.container = container;
/*     */   }
/*     */   
/*     */   public void setExecutor(Executor threadPool) {
/*  72 */     this.threadPool = (threadPool != null) ? wrap(threadPool) : null;
/*     */   }
/*     */   
/*     */   void addRunnable(Fiber fiber) {
/*  76 */     if (this.threadPool == null) {
/*  77 */       synchronized (this) {
/*  78 */         this.threadPool = wrap(Executors.newCachedThreadPool(new DaemonThreadFactory()));
/*     */       } 
/*     */     }
/*  81 */     this.threadPool.execute(fiber);
/*     */   }
/*     */   
/*     */   private Executor wrap(Executor ex) {
/*  85 */     return ContainerResolver.getDefault().wrapExecutor(this.container, ex);
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
/*     */   public Fiber createFiber() {
/*  98 */     return new Fiber(this);
/*     */   }
/*     */   
/*     */   private static class DaemonThreadFactory implements ThreadFactory {
/* 102 */     static final AtomicInteger poolNumber = new AtomicInteger(1);
/* 103 */     final AtomicInteger threadNumber = new AtomicInteger(1);
/*     */     final String namePrefix;
/*     */     
/*     */     DaemonThreadFactory() {
/* 107 */       this.namePrefix = "jaxws-engine-" + poolNumber.getAndIncrement() + "-thread-";
/*     */     }
/*     */     
/*     */     public Thread newThread(Runnable r) {
/* 111 */       Thread t = new Thread(null, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
/* 112 */       if (!t.isDaemon()) {
/* 113 */         t.setDaemon(true);
/*     */       }
/* 115 */       if (t.getPriority() != 5) {
/* 116 */         t.setPriority(5);
/*     */       }
/* 118 */       return t;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/Engine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */