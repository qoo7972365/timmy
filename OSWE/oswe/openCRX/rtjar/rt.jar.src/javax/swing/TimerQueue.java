/*     */ package javax.swing;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.concurrent.DelayQueue;
/*     */ import java.util.concurrent.Delayed;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import sun.awt.AppContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TimerQueue
/*     */   implements Runnable
/*     */ {
/*  50 */   private static final Object sharedInstanceKey = new StringBuffer("TimerQueue.sharedInstanceKey");
/*     */   
/*  52 */   private static final Object expiredTimersKey = new StringBuffer("TimerQueue.expiredTimersKey");
/*     */ 
/*     */   
/*     */   private final DelayQueue<DelayedTimer> queue;
/*     */ 
/*     */   
/*     */   private volatile boolean running;
/*     */   
/*     */   private final Lock runningLock;
/*     */   
/*  62 */   private static final Object classLock = new Object();
/*     */ 
/*     */   
/*  65 */   private static final long NANO_ORIGIN = System.nanoTime();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimerQueue() {
/*  72 */     this.queue = new DelayQueue<>();
/*     */     
/*  74 */     this.runningLock = new ReentrantLock();
/*  75 */     startIfNeeded();
/*     */   }
/*     */ 
/*     */   
/*     */   public static TimerQueue sharedInstance() {
/*  80 */     synchronized (classLock) {
/*     */       
/*  82 */       TimerQueue timerQueue = (TimerQueue)SwingUtilities.appContextGet(sharedInstanceKey);
/*     */       
/*  84 */       if (timerQueue == null) {
/*  85 */         timerQueue = new TimerQueue();
/*  86 */         SwingUtilities.appContextPut(sharedInstanceKey, timerQueue);
/*     */       } 
/*  88 */       return timerQueue;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void startIfNeeded() {
/*  94 */     if (!this.running) {
/*  95 */       this.runningLock.lock();
/*  96 */       if (this.running) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 101 */         final ThreadGroup threadGroup = AppContext.getAppContext().getThreadGroup();
/* 102 */         AccessController.doPrivileged(new PrivilegedAction()
/*     */             {
/*     */               public Object run() {
/* 105 */                 Thread thread = new Thread(threadGroup, TimerQueue.this, "TimerQueue");
/*     */                 
/* 107 */                 thread.setDaemon(true);
/* 108 */                 thread.setPriority(5);
/* 109 */                 thread.start();
/* 110 */                 return null;
/*     */               }
/*     */             });
/* 113 */         this.running = true;
/*     */       } finally {
/* 115 */         this.runningLock.unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void addTimer(Timer paramTimer, long paramLong) {
/* 121 */     paramTimer.getLock().lock();
/*     */     
/*     */     try {
/* 124 */       if (!containsTimer(paramTimer)) {
/* 125 */         addTimer(new DelayedTimer(paramTimer, TimeUnit.MILLISECONDS
/* 126 */               .toNanos(paramLong) + 
/* 127 */               now()));
/*     */       }
/*     */     } finally {
/* 130 */       paramTimer.getLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addTimer(DelayedTimer paramDelayedTimer) {
/* 135 */     assert paramDelayedTimer != null && !containsTimer(paramDelayedTimer.getTimer());
/*     */     
/* 137 */     Timer timer = paramDelayedTimer.getTimer();
/* 138 */     timer.getLock().lock();
/*     */     try {
/* 140 */       timer.delayedTimer = paramDelayedTimer;
/* 141 */       this.queue.add(paramDelayedTimer);
/*     */     } finally {
/* 143 */       timer.getLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   void removeTimer(Timer paramTimer) {
/* 148 */     paramTimer.getLock().lock();
/*     */     try {
/* 150 */       if (paramTimer.delayedTimer != null) {
/* 151 */         this.queue.remove(paramTimer.delayedTimer);
/* 152 */         paramTimer.delayedTimer = null;
/*     */       } 
/*     */     } finally {
/* 155 */       paramTimer.getLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean containsTimer(Timer paramTimer) {
/* 160 */     paramTimer.getLock().lock();
/*     */     try {
/* 162 */       return (paramTimer.delayedTimer != null);
/*     */     } finally {
/* 164 */       paramTimer.getLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 170 */     this.runningLock.lock();
/*     */     try {
/* 172 */       while (this.running) {
/*     */         try {
/* 174 */           DelayedTimer delayedTimer = this.queue.take();
/* 175 */           Timer timer = delayedTimer.getTimer();
/* 176 */           timer.getLock().lock();
/*     */           
/* 178 */           try { DelayedTimer delayedTimer1 = timer.delayedTimer;
/* 179 */             if (delayedTimer1 == delayedTimer) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 186 */               timer.post();
/* 187 */               timer.delayedTimer = null;
/* 188 */               if (timer.isRepeats()) {
/* 189 */                 delayedTimer1.setTime(now() + TimeUnit.MILLISECONDS
/* 190 */                     .toNanos(timer
/* 191 */                       .getDelay()));
/* 192 */                 addTimer(delayedTimer1);
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/* 197 */             timer.getLock().newCondition().awaitNanos(1L); }
/* 198 */           catch (SecurityException securityException) {  }
/*     */           finally
/* 200 */           { timer.getLock().unlock(); }
/*     */         
/* 202 */         } catch (InterruptedException interruptedException) {
/*     */ 
/*     */           
/* 205 */           if (AppContext.getAppContext().isDisposed()) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/* 211 */     } catch (ThreadDeath threadDeath) {
/*     */       
/* 213 */       for (DelayedTimer delayedTimer : this.queue) {
/* 214 */         delayedTimer.getTimer().cancelEvent();
/*     */       }
/* 216 */       throw threadDeath;
/*     */     } finally {
/* 218 */       this.running = false;
/* 219 */       this.runningLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 225 */     StringBuilder stringBuilder = new StringBuilder();
/* 226 */     stringBuilder.append("TimerQueue (");
/* 227 */     boolean bool = true;
/* 228 */     for (DelayedTimer delayedTimer : this.queue) {
/* 229 */       if (!bool) {
/* 230 */         stringBuilder.append(", ");
/*     */       }
/* 232 */       stringBuilder.append(delayedTimer.getTimer().toString());
/* 233 */       bool = false;
/*     */     } 
/* 235 */     stringBuilder.append(")");
/* 236 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long now() {
/* 243 */     return System.nanoTime() - NANO_ORIGIN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class DelayedTimer
/*     */     implements Delayed
/*     */   {
/* 254 */     private static final AtomicLong sequencer = new AtomicLong(0L);
/*     */ 
/*     */     
/*     */     private final long sequenceNumber;
/*     */ 
/*     */     
/*     */     private volatile long time;
/*     */     
/*     */     private final Timer timer;
/*     */ 
/*     */     
/*     */     DelayedTimer(Timer param1Timer, long param1Long) {
/* 266 */       this.timer = param1Timer;
/* 267 */       this.time = param1Long;
/* 268 */       this.sequenceNumber = sequencer.getAndIncrement();
/*     */     }
/*     */ 
/*     */     
/*     */     public final long getDelay(TimeUnit param1TimeUnit) {
/* 273 */       return param1TimeUnit.convert(this.time - TimerQueue.now(), TimeUnit.NANOSECONDS);
/*     */     }
/*     */     
/*     */     final void setTime(long param1Long) {
/* 277 */       this.time = param1Long;
/*     */     }
/*     */     
/*     */     final Timer getTimer() {
/* 281 */       return this.timer;
/*     */     }
/*     */     
/*     */     public int compareTo(Delayed param1Delayed) {
/* 285 */       if (param1Delayed == this) {
/* 286 */         return 0;
/*     */       }
/* 288 */       if (param1Delayed instanceof DelayedTimer) {
/* 289 */         DelayedTimer delayedTimer = (DelayedTimer)param1Delayed;
/* 290 */         long l1 = this.time - delayedTimer.time;
/* 291 */         if (l1 < 0L)
/* 292 */           return -1; 
/* 293 */         if (l1 > 0L)
/* 294 */           return 1; 
/* 295 */         if (this.sequenceNumber < delayedTimer.sequenceNumber) {
/* 296 */           return -1;
/*     */         }
/* 298 */         return 1;
/*     */       } 
/*     */ 
/*     */       
/* 302 */       long l = getDelay(TimeUnit.NANOSECONDS) - param1Delayed.getDelay(TimeUnit.NANOSECONDS);
/* 303 */       return (l == 0L) ? 0 : ((l < 0L) ? -1 : 1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/TimerQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */