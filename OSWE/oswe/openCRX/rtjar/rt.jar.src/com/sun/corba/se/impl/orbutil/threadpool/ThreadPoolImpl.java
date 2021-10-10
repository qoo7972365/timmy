/*     */ package com.sun.corba.se.impl.orbutil.threadpool;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.monitoring.LongMonitoredAttributeBase;
/*     */ import com.sun.corba.se.spi.monitoring.MonitoredAttribute;
/*     */ import com.sun.corba.se.spi.monitoring.MonitoredObject;
/*     */ import com.sun.corba.se.spi.monitoring.MonitoringFactories;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.NoSuchWorkQueueException;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.Work;
/*     */ import com.sun.corba.se.spi.orbutil.threadpool.WorkQueue;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadPoolImpl
/*     */   implements ThreadPool
/*     */ {
/*  61 */   private static AtomicInteger threadCounter = new AtomicInteger(0);
/*     */   
/*  63 */   private static final ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.transport");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WorkQueue workQueue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private int availableWorkerThreads = 0;
/*     */ 
/*     */   
/*  78 */   private int currentThreadCount = 0;
/*     */ 
/*     */   
/*  81 */   private int minWorkerThreads = 0;
/*     */ 
/*     */   
/*  84 */   private int maxWorkerThreads = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private long inactivityTimeout;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean boundedThreadPool = false;
/*     */ 
/*     */   
/*  95 */   private AtomicLong processedCount = new AtomicLong(1L);
/*     */ 
/*     */ 
/*     */   
/*  99 */   private AtomicLong totalTimeTaken = new AtomicLong(0L);
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */   
/*     */   private MonitoredObject threadpoolMonitoredObject;
/*     */ 
/*     */   
/*     */   private ThreadGroup threadGroup;
/*     */   
/* 110 */   Object workersLock = new Object();
/* 111 */   List<WorkerThread> workers = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThreadPoolImpl(ThreadGroup paramThreadGroup, String paramString) {
/* 117 */     this.inactivityTimeout = 120000L;
/* 118 */     this.maxWorkerThreads = Integer.MAX_VALUE;
/* 119 */     this.workQueue = new WorkQueueImpl(this);
/* 120 */     this.threadGroup = paramThreadGroup;
/* 121 */     this.name = paramString;
/* 122 */     initializeMonitoring();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThreadPoolImpl(String paramString) {
/* 130 */     this(Thread.currentThread().getThreadGroup(), paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ThreadPoolImpl(int paramInt1, int paramInt2, long paramLong, String paramString) {
/* 139 */     this.minWorkerThreads = paramInt1;
/* 140 */     this.maxWorkerThreads = paramInt2;
/* 141 */     this.inactivityTimeout = paramLong;
/* 142 */     this.boundedThreadPool = true;
/* 143 */     this.workQueue = new WorkQueueImpl(this);
/* 144 */     this.name = paramString;
/* 145 */     for (byte b = 0; b < this.minWorkerThreads; b++) {
/* 146 */       createWorkerThread();
/*     */     }
/* 148 */     initializeMonitoring();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 155 */     ArrayList<WorkerThread> arrayList = null;
/* 156 */     synchronized (this.workersLock) {
/* 157 */       arrayList = new ArrayList<>(this.workers);
/*     */     } 
/*     */     
/* 160 */     for (WorkerThread workerThread : arrayList) {
/* 161 */       workerThread.close();
/* 162 */       while (workerThread.getState() != Thread.State.TERMINATED) {
/*     */         try {
/* 164 */           workerThread.join();
/* 165 */         } catch (InterruptedException interruptedException) {
/* 166 */           wrapper.interruptedJoinCallWhileClosingThreadPool(interruptedException, workerThread, this);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 171 */     this.threadGroup = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeMonitoring() {
/* 180 */     MonitoredObject monitoredObject1 = MonitoringFactories.getMonitoringManagerFactory().createMonitoringManager("orb", null).getRootMonitoredObject();
/*     */ 
/*     */     
/* 183 */     MonitoredObject monitoredObject2 = monitoredObject1.getChild("threadpool");
/*     */     
/* 185 */     if (monitoredObject2 == null) {
/*     */       
/* 187 */       monitoredObject2 = MonitoringFactories.getMonitoredObjectFactory().createMonitoredObject("threadpool", "Monitoring for all ThreadPool instances");
/*     */ 
/*     */       
/* 190 */       monitoredObject1.addChild(monitoredObject2);
/*     */     } 
/* 192 */     this
/*     */       
/* 194 */       .threadpoolMonitoredObject = MonitoringFactories.getMonitoredObjectFactory().createMonitoredObject(this.name, "Monitoring for a ThreadPool");
/*     */ 
/*     */     
/* 197 */     monitoredObject2.addChild(this.threadpoolMonitoredObject);
/*     */     
/* 199 */     LongMonitoredAttributeBase longMonitoredAttributeBase1 = new LongMonitoredAttributeBase("currentNumberOfThreads", "Current number of total threads in the ThreadPool")
/*     */       {
/*     */         public Object getValue()
/*     */         {
/* 203 */           return new Long(ThreadPoolImpl.this.currentNumberOfThreads());
/*     */         }
/*     */       };
/* 206 */     this.threadpoolMonitoredObject.addAttribute((MonitoredAttribute)longMonitoredAttributeBase1);
/* 207 */     LongMonitoredAttributeBase longMonitoredAttributeBase2 = new LongMonitoredAttributeBase("numberOfAvailableThreads", "Current number of total threads in the ThreadPool")
/*     */       {
/*     */         public Object getValue()
/*     */         {
/* 211 */           return new Long(ThreadPoolImpl.this.numberOfAvailableThreads());
/*     */         }
/*     */       };
/* 214 */     this.threadpoolMonitoredObject.addAttribute((MonitoredAttribute)longMonitoredAttributeBase2);
/* 215 */     LongMonitoredAttributeBase longMonitoredAttributeBase3 = new LongMonitoredAttributeBase("numberOfBusyThreads", "Number of busy threads in the ThreadPool")
/*     */       {
/*     */         public Object getValue()
/*     */         {
/* 219 */           return new Long(ThreadPoolImpl.this.numberOfBusyThreads());
/*     */         }
/*     */       };
/* 222 */     this.threadpoolMonitoredObject.addAttribute((MonitoredAttribute)longMonitoredAttributeBase3);
/* 223 */     LongMonitoredAttributeBase longMonitoredAttributeBase4 = new LongMonitoredAttributeBase("averageWorkCompletionTime", "Average elapsed time taken to complete a work item by the ThreadPool")
/*     */       {
/*     */         public Object getValue()
/*     */         {
/* 227 */           return new Long(ThreadPoolImpl.this.averageWorkCompletionTime());
/*     */         }
/*     */       };
/* 230 */     this.threadpoolMonitoredObject.addAttribute((MonitoredAttribute)longMonitoredAttributeBase4);
/* 231 */     LongMonitoredAttributeBase longMonitoredAttributeBase5 = new LongMonitoredAttributeBase("currentProcessedCount", "Number of Work items processed by the ThreadPool")
/*     */       {
/*     */         public Object getValue()
/*     */         {
/* 235 */           return new Long(ThreadPoolImpl.this.currentProcessedCount());
/*     */         }
/*     */       };
/* 238 */     this.threadpoolMonitoredObject.addAttribute((MonitoredAttribute)longMonitoredAttributeBase5);
/*     */ 
/*     */ 
/*     */     
/* 242 */     this.threadpoolMonitoredObject.addChild(((WorkQueueImpl)this.workQueue)
/* 243 */         .getMonitoredObject());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   MonitoredObject getMonitoredObject() {
/* 249 */     return this.threadpoolMonitoredObject;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorkQueue getAnyWorkQueue() {
/* 254 */     return this.workQueue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WorkQueue getWorkQueue(int paramInt) throws NoSuchWorkQueueException {
/* 260 */     if (paramInt != 0)
/* 261 */       throw new NoSuchWorkQueueException(); 
/* 262 */     return this.workQueue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void notifyForAvailableWork(WorkQueue paramWorkQueue) {
/* 271 */     synchronized (paramWorkQueue) {
/* 272 */       if (this.availableWorkerThreads < paramWorkQueue.workItemsInQueue()) {
/* 273 */         createWorkerThread();
/*     */       } else {
/* 275 */         paramWorkQueue.notify();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Thread createWorkerThreadHelper(String paramString) {
/* 318 */     WorkerThread workerThread = new WorkerThread(this.threadGroup, paramString);
/* 319 */     synchronized (this.workersLock) {
/* 320 */       this.workers.add(workerThread);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 329 */     workerThread.setDaemon(true);
/*     */     
/* 331 */     wrapper.workerThreadCreated(workerThread, workerThread.getContextClassLoader());
/*     */     
/* 333 */     workerThread.start();
/* 334 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void createWorkerThread() {
/* 343 */     final String name = getName();
/* 344 */     synchronized (this.workQueue) {
/*     */       try {
/* 346 */         if (System.getSecurityManager() == null) {
/* 347 */           createWorkerThreadHelper(str);
/*     */         } else {
/*     */           
/* 350 */           AccessController.doPrivileged(new PrivilegedAction()
/*     */               {
/*     */                 public Object run() {
/* 353 */                   return ThreadPoolImpl.this.createWorkerThreadHelper(name);
/*     */                 }
/*     */               });
/*     */         }
/*     */       
/* 358 */       } catch (Throwable throwable) {
/*     */ 
/*     */         
/* 361 */         decrementCurrentNumberOfThreads();
/* 362 */         wrapper.workerThreadCreationFailure(throwable);
/*     */       } finally {
/* 364 */         incrementCurrentNumberOfThreads();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int minimumNumberOfThreads() {
/* 370 */     return this.minWorkerThreads;
/*     */   }
/*     */   
/*     */   public int maximumNumberOfThreads() {
/* 374 */     return this.maxWorkerThreads;
/*     */   }
/*     */   
/*     */   public long idleTimeoutForThreads() {
/* 378 */     return this.inactivityTimeout;
/*     */   }
/*     */   
/*     */   public int currentNumberOfThreads() {
/* 382 */     synchronized (this.workQueue) {
/* 383 */       return this.currentThreadCount;
/*     */     } 
/*     */   }
/*     */   
/*     */   void decrementCurrentNumberOfThreads() {
/* 388 */     synchronized (this.workQueue) {
/* 389 */       this.currentThreadCount--;
/*     */     } 
/*     */   }
/*     */   
/*     */   void incrementCurrentNumberOfThreads() {
/* 394 */     synchronized (this.workQueue) {
/* 395 */       this.currentThreadCount++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int numberOfAvailableThreads() {
/* 400 */     synchronized (this.workQueue) {
/* 401 */       return this.availableWorkerThreads;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int numberOfBusyThreads() {
/* 406 */     synchronized (this.workQueue) {
/* 407 */       return this.currentThreadCount - this.availableWorkerThreads;
/*     */     } 
/*     */   }
/*     */   
/*     */   public long averageWorkCompletionTime() {
/* 412 */     synchronized (this.workQueue) {
/* 413 */       return this.totalTimeTaken.get() / this.processedCount.get();
/*     */     } 
/*     */   }
/*     */   
/*     */   public long currentProcessedCount() {
/* 418 */     synchronized (this.workQueue) {
/* 419 */       return this.processedCount.get();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getName() {
/* 424 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int numberOfWorkQueues() {
/* 431 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static synchronized int getUniqueThreadId() {
/* 436 */     return threadCounter.incrementAndGet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void decrementNumberOfAvailableThreads() {
/* 445 */     synchronized (this.workQueue) {
/* 446 */       this.availableWorkerThreads--;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void incrementNumberOfAvailableThreads() {
/* 456 */     synchronized (this.workQueue) {
/* 457 */       this.availableWorkerThreads++;
/*     */     } 
/*     */   }
/*     */   
/*     */   private class WorkerThread
/*     */     extends Thread
/*     */     implements Closeable {
/*     */     private Work currentWork;
/* 465 */     private int threadId = 0;
/*     */     
/*     */     private volatile boolean closeCalled = false;
/*     */     private String threadPoolName;
/* 469 */     private StringBuffer workerThreadName = new StringBuffer();
/*     */     
/*     */     WorkerThread(ThreadGroup param1ThreadGroup, String param1String) {
/* 472 */       super(param1ThreadGroup, "Idle");
/* 473 */       this.threadId = ThreadPoolImpl.getUniqueThreadId();
/* 474 */       this.threadPoolName = param1String;
/* 475 */       setName(composeWorkerThreadName(param1String, "Idle"));
/*     */     }
/*     */     
/*     */     public synchronized void close() {
/* 479 */       this.closeCalled = true;
/* 480 */       interrupt();
/*     */     }
/*     */ 
/*     */     
/*     */     private void resetClassLoader() {}
/*     */ 
/*     */     
/*     */     private void performWork() {
/* 488 */       long l1 = System.currentTimeMillis();
/*     */       try {
/* 490 */         this.currentWork.doWork();
/* 491 */       } catch (Throwable throwable) {
/* 492 */         ThreadPoolImpl.wrapper.workerThreadDoWorkThrowable(this, throwable);
/*     */       } 
/* 494 */       long l2 = System.currentTimeMillis() - l1;
/* 495 */       ThreadPoolImpl.this.totalTimeTaken.addAndGet(l2);
/* 496 */       ThreadPoolImpl.this.processedCount.incrementAndGet();
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       try {
/* 501 */         while (!this.closeCalled) {
/*     */           try {
/* 503 */             this.currentWork = ((WorkQueueImpl)ThreadPoolImpl.this.workQueue).requestWork(ThreadPoolImpl.this
/* 504 */                 .inactivityTimeout);
/* 505 */             if (this.currentWork == null)
/*     */               continue; 
/* 507 */           } catch (InterruptedException interruptedException) {
/* 508 */             ThreadPoolImpl.wrapper.workQueueThreadInterrupted(interruptedException, getName(), 
/* 509 */                 Boolean.valueOf(this.closeCalled));
/*     */             
/*     */             continue;
/* 512 */           } catch (Throwable throwable) {
/* 513 */             ThreadPoolImpl.wrapper.workerThreadThrowableFromRequestWork(this, throwable, ThreadPoolImpl.this
/* 514 */                 .workQueue.getName());
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 519 */           performWork();
/*     */ 
/*     */ 
/*     */           
/* 523 */           this.currentWork = null;
/*     */           
/* 525 */           resetClassLoader();
/*     */         } 
/* 527 */       } catch (Throwable throwable) {
/*     */         
/* 529 */         ThreadPoolImpl.wrapper.workerThreadCaughtUnexpectedThrowable(this, throwable);
/*     */       } finally {
/* 531 */         synchronized (ThreadPoolImpl.this.workersLock) {
/* 532 */           ThreadPoolImpl.this.workers.remove(this);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private String composeWorkerThreadName(String param1String1, String param1String2) {
/* 538 */       this.workerThreadName.setLength(0);
/* 539 */       this.workerThreadName.append("p: ").append(param1String1);
/* 540 */       this.workerThreadName.append("; w: ").append(param1String2);
/* 541 */       return this.workerThreadName.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orbutil/threadpool/ThreadPoolImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */