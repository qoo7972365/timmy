/*     */ package java.awt;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import sun.awt.PeerEvent;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WaitDispatchSupport
/*     */   implements SecondaryLoop
/*     */ {
/*  51 */   private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.event.WaitDispatchSupport");
/*     */   
/*     */   private EventDispatchThread dispatchThread;
/*     */   
/*     */   private EventFilter filter;
/*     */   
/*     */   private volatile Conditional extCondition;
/*     */   
/*     */   private volatile Conditional condition;
/*     */   
/*     */   private long interval;
/*     */   
/*     */   private static Timer timer;
/*     */   
/*     */   private TimerTask timerTask;
/*  66 */   private AtomicBoolean keepBlockingEDT = new AtomicBoolean(false);
/*  67 */   private AtomicBoolean keepBlockingCT = new AtomicBoolean(false);
/*  68 */   private AtomicBoolean afterExit = new AtomicBoolean(false); private final Runnable wakingRunnable;
/*     */   
/*     */   private static synchronized void initializeTimer() {
/*  71 */     if (timer == null) {
/*  72 */       timer = new Timer("AWT-WaitDispatchSupport-Timer", true);
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
/*     */   public WaitDispatchSupport(EventDispatchThread paramEventDispatchThread) {
/*  86 */     this(paramEventDispatchThread, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WaitDispatchSupport(EventDispatchThread paramEventDispatchThread, Conditional paramConditional, EventFilter paramEventFilter, long paramLong) {
/* 153 */     this(paramEventDispatchThread, paramConditional);
/* 154 */     this.filter = paramEventFilter;
/* 155 */     if (paramLong < 0L) {
/* 156 */       throw new IllegalArgumentException("The interval value must be >= 0");
/*     */     }
/* 158 */     this.interval = paramLong;
/* 159 */     if (paramLong != 0L) {
/* 160 */       initializeTimer();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enter() {
/* 169 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 170 */       log.fine("enter(): blockingEDT=" + this.keepBlockingEDT.get() + ", blockingCT=" + this.keepBlockingCT
/* 171 */           .get());
/*     */     }
/*     */     
/* 174 */     if (!this.keepBlockingEDT.compareAndSet(false, true)) {
/* 175 */       log.fine("The secondary loop is already running, aborting");
/* 176 */       return false;
/*     */     } 
/*     */     try {
/* 179 */       if (this.afterExit.get()) {
/* 180 */         log.fine("Exit was called already, aborting");
/* 181 */         return false;
/*     */       } 
/*     */       
/* 184 */       final Runnable run = new Runnable() {
/*     */           public void run() {
/* 186 */             WaitDispatchSupport.log.fine("Starting a new event pump");
/* 187 */             if (WaitDispatchSupport.this.filter == null) {
/* 188 */               WaitDispatchSupport.this.dispatchThread.pumpEvents(WaitDispatchSupport.this.condition);
/*     */             } else {
/* 190 */               WaitDispatchSupport.this.dispatchThread.pumpEventsForFilter(WaitDispatchSupport.this.condition, WaitDispatchSupport.this.filter);
/*     */             } 
/*     */           }
/*     */         };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 199 */       Thread thread = Thread.currentThread();
/* 200 */       if (thread == this.dispatchThread) {
/* 201 */         if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 202 */           log.finest("On dispatch thread: " + this.dispatchThread);
/*     */         }
/* 204 */         if (this.interval != 0L) {
/* 205 */           if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 206 */             log.finest("scheduling the timer for " + this.interval + " ms");
/*     */           }
/* 208 */           timer.schedule(this.timerTask = new TimerTask()
/*     */               {
/*     */                 public void run() {
/* 211 */                   if (WaitDispatchSupport.this.keepBlockingEDT.compareAndSet(true, false)) {
/* 212 */                     WaitDispatchSupport.this.wakeupEDT();
/*     */                   }
/*     */                 }
/*     */               }this.interval);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 220 */         SequencedEvent sequencedEvent = KeyboardFocusManager.getCurrentKeyboardFocusManager().getCurrentSequencedEvent();
/* 221 */         if (sequencedEvent != null) {
/* 222 */           if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 223 */             log.fine("Dispose current SequencedEvent: " + sequencedEvent);
/*     */           }
/* 225 */           sequencedEvent.dispose();
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 233 */         AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */               public Void run() {
/* 235 */                 run.run();
/* 236 */                 return null;
/*     */               }
/*     */             });
/*     */       } else {
/* 240 */         if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 241 */           log.finest("On non-dispatch thread: " + thread);
/*     */         }
/* 243 */         this.keepBlockingCT.set(true);
/* 244 */         synchronized (getTreeLock()) {
/* 245 */           if (this.afterExit.get()) return false; 
/* 246 */           if (this.filter != null) {
/* 247 */             this.dispatchThread.addEventFilter(this.filter);
/*     */           }
/*     */           try {
/* 250 */             EventQueue eventQueue = this.dispatchThread.getEventQueue();
/* 251 */             eventQueue.postEvent(new PeerEvent(this, runnable, 1L));
/* 252 */             if (this.interval > 0L) {
/* 253 */               long l = System.currentTimeMillis();
/* 254 */               while (this.keepBlockingCT.get() && (this.extCondition == null || this.extCondition
/* 255 */                 .evaluate()) && l + this.interval > 
/* 256 */                 System.currentTimeMillis())
/*     */               {
/* 258 */                 getTreeLock().wait(this.interval);
/*     */               }
/*     */             } else {
/* 261 */               while (this.keepBlockingCT.get() && (this.extCondition == null || this.extCondition
/* 262 */                 .evaluate()))
/*     */               {
/* 264 */                 getTreeLock().wait();
/*     */               }
/*     */             } 
/* 267 */             if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 268 */               log.fine("waitDone " + this.keepBlockingEDT.get() + " " + this.keepBlockingCT.get());
/*     */             }
/* 270 */           } catch (InterruptedException interruptedException) {
/* 271 */             if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 272 */               log.fine("Exception caught while waiting: " + interruptedException);
/*     */             }
/*     */           } finally {
/* 275 */             if (this.filter != null) {
/* 276 */               this.dispatchThread.removeEventFilter(this.filter);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 281 */       return true;
/*     */     } finally {
/*     */       
/* 284 */       this.keepBlockingEDT.set(false);
/* 285 */       this.keepBlockingCT.set(false);
/* 286 */       this.afterExit.set(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exit() {
/* 294 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 295 */       log.fine("exit(): blockingEDT=" + this.keepBlockingEDT.get() + ", blockingCT=" + this.keepBlockingCT
/* 296 */           .get());
/*     */     }
/* 298 */     this.afterExit.set(true);
/* 299 */     if (this.keepBlockingEDT.getAndSet(false)) {
/* 300 */       wakeupEDT();
/* 301 */       return true;
/*     */     } 
/* 303 */     return false;
/*     */   }
/*     */   
/*     */   private static final Object getTreeLock() {
/* 307 */     return Component.LOCK;
/*     */   }
/*     */   
/* 310 */   public WaitDispatchSupport(EventDispatchThread paramEventDispatchThread, Conditional paramConditional) { this.wakingRunnable = new Runnable()
/*     */       {
/* 312 */         public void run() { WaitDispatchSupport.log.fine("Wake up EDT");
/* 313 */           synchronized (WaitDispatchSupport.getTreeLock()) {
/* 314 */             WaitDispatchSupport.this.keepBlockingCT.set(false);
/* 315 */             WaitDispatchSupport.getTreeLock().notifyAll();
/*     */           } 
/* 317 */           WaitDispatchSupport.log.fine("Wake up EDT done"); }
/*     */       }; if (paramEventDispatchThread == null)
/*     */       throw new IllegalArgumentException("The dispatchThread can not be null");  this.dispatchThread = paramEventDispatchThread; this.extCondition = paramConditional; this.condition = new Conditional() { public boolean evaluate() { if (WaitDispatchSupport.log.isLoggable(PlatformLogger.Level.FINEST))
/*     */             WaitDispatchSupport.log.finest("evaluate(): blockingEDT=" + WaitDispatchSupport.this.keepBlockingEDT.get() + ", blockingCT=" + WaitDispatchSupport.this.keepBlockingCT.get());  boolean bool = (WaitDispatchSupport.this.extCondition != null) ? WaitDispatchSupport.this.extCondition.evaluate() : true; if (!WaitDispatchSupport.this.keepBlockingEDT.get() || !bool) { if (WaitDispatchSupport.this.timerTask != null) { WaitDispatchSupport.this.timerTask.cancel(); WaitDispatchSupport.this.timerTask = null; }
/*     */              return false; }
/* 322 */            return true; } }; } private void wakeupEDT() { if (log.isLoggable(PlatformLogger.Level.FINEST)) {
/* 323 */       log.finest("wakeupEDT(): EDT == " + this.dispatchThread);
/*     */     }
/* 325 */     EventQueue eventQueue = this.dispatchThread.getEventQueue();
/* 326 */     eventQueue.postEvent(new PeerEvent(this, this.wakingRunnable, 1L)); }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/WaitDispatchSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */