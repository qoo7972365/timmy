/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import javax.swing.event.EventListenerList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Timer
/*     */   implements Serializable
/*     */ {
/* 156 */   protected EventListenerList listenerList = new EventListenerList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   private final transient AtomicBoolean notify = new AtomicBoolean(false);
/*     */   
/*     */   private volatile int initialDelay;
/*     */   
/*     */   private volatile int delay;
/*     */   private volatile boolean repeats = true;
/*     */   private volatile boolean coalesce = true;
/*     */   private final transient Runnable doPostEvent;
/*     */   private static volatile boolean logTimers;
/* 180 */   private final transient Lock lock = new ReentrantLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   transient TimerQueue.DelayedTimer delayedTimer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile String actionCommand;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile transient AccessControlContext acc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Timer(int paramInt, ActionListener paramActionListener) {
/* 219 */     this
/* 220 */       .acc = AccessController.getContext();
/*     */     this.delay = paramInt;
/*     */     this.initialDelay = paramInt;
/*     */     this.doPostEvent = new DoPostEvent();
/*     */     if (paramActionListener != null)
/*     */       addActionListener(paramActionListener);  } final AccessControlContext getAccessControlContext() {
/* 226 */     if (this.acc == null) {
/* 227 */       throw new SecurityException("Timer is missing AccessControlContext");
/*     */     }
/*     */     
/* 230 */     return this.acc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class DoPostEvent
/*     */     implements Runnable
/*     */   {
/*     */     public void run() {
/* 241 */       if (Timer.logTimers) {
/* 242 */         System.out.println("Timer ringing: " + Timer.this);
/*     */       }
/* 244 */       if (Timer.this.notify.get()) {
/* 245 */         Timer.this.fireActionPerformed(new ActionEvent(Timer.this, 0, Timer.this.getActionCommand(), 
/* 246 */               System.currentTimeMillis(), 0));
/*     */         
/* 248 */         if (Timer.this.coalesce) {
/* 249 */           Timer.this.cancelEvent();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     Timer getTimer() {
/* 255 */       return Timer.this;
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
/*     */   public void addActionListener(ActionListener paramActionListener) {
/* 267 */     this.listenerList.add(ActionListener.class, paramActionListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeActionListener(ActionListener paramActionListener) {
/* 277 */     this.listenerList.remove(ActionListener.class, paramActionListener);
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
/*     */   public ActionListener[] getActionListeners() {
/* 294 */     return this.listenerList.<ActionListener>getListeners(ActionListener.class);
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
/*     */   protected void fireActionPerformed(ActionEvent paramActionEvent) {
/* 307 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*     */ 
/*     */ 
/*     */     
/* 311 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 312 */       if (arrayOfObject[i] == ActionListener.class) {
/* 313 */         ((ActionListener)arrayOfObject[i + 1]).actionPerformed(paramActionEvent);
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
/*     */   public <T extends java.util.EventListener> T[] getListeners(Class<T> paramClass) {
/* 356 */     return this.listenerList.getListeners(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TimerQueue timerQueue() {
/* 363 */     return TimerQueue.sharedInstance();
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
/*     */   public static void setLogTimers(boolean paramBoolean) {
/* 375 */     logTimers = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getLogTimers() {
/* 386 */     return logTimers;
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
/*     */   public void setDelay(int paramInt) {
/* 399 */     if (paramInt < 0) {
/* 400 */       throw new IllegalArgumentException("Invalid delay: " + paramInt);
/*     */     }
/*     */     
/* 403 */     this.delay = paramInt;
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
/*     */   public int getDelay() {
/* 416 */     return this.delay;
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
/*     */   public void setInitialDelay(int paramInt) {
/* 432 */     if (paramInt < 0) {
/* 433 */       throw new IllegalArgumentException("Invalid initial delay: " + paramInt);
/*     */     }
/*     */ 
/*     */     
/* 437 */     this.initialDelay = paramInt;
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
/*     */   public int getInitialDelay() {
/* 449 */     return this.initialDelay;
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
/*     */   public void setRepeats(boolean paramBoolean) {
/* 462 */     this.repeats = paramBoolean;
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
/*     */   public boolean isRepeats() {
/* 475 */     return this.repeats;
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
/*     */   public void setCoalesce(boolean paramBoolean) {
/* 496 */     boolean bool = this.coalesce;
/* 497 */     this.coalesce = paramBoolean;
/* 498 */     if (!bool && this.coalesce)
/*     */     {
/*     */ 
/*     */       
/* 502 */       cancelEvent();
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
/*     */   public boolean isCoalesce() {
/* 514 */     return this.coalesce;
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
/*     */   public void setActionCommand(String paramString) {
/* 527 */     this.actionCommand = paramString;
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
/*     */   public String getActionCommand() {
/* 540 */     return this.actionCommand;
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
/*     */   public void start() {
/* 552 */     timerQueue().addTimer(this, getInitialDelay());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRunning() {
/* 562 */     return timerQueue().containsTimer(this);
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
/*     */   public void stop() {
/* 574 */     getLock().lock();
/*     */     try {
/* 576 */       cancelEvent();
/* 577 */       timerQueue().removeTimer(this);
/*     */     } finally {
/* 579 */       getLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restart() {
/* 590 */     getLock().lock();
/*     */     try {
/* 592 */       stop();
/* 593 */       start();
/*     */     } finally {
/* 595 */       getLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void cancelEvent() {
/* 606 */     this.notify.set(false);
/*     */   }
/*     */ 
/*     */   
/*     */   void post() {
/* 611 */     if (this.notify.compareAndSet(false, true) || !this.coalesce) {
/* 612 */       AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */             public Void run() {
/* 614 */               SwingUtilities.invokeLater(Timer.this.doPostEvent);
/* 615 */               return null;
/*     */             }
/* 617 */           },  getAccessControlContext());
/*     */     }
/*     */   }
/*     */   
/*     */   Lock getLock() {
/* 622 */     return this.lock;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 628 */     this.acc = AccessController.getContext();
/* 629 */     paramObjectInputStream.defaultReadObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 637 */     Timer timer = new Timer(getDelay(), null);
/* 638 */     timer.listenerList = this.listenerList;
/* 639 */     timer.initialDelay = this.initialDelay;
/* 640 */     timer.delay = this.delay;
/* 641 */     timer.repeats = this.repeats;
/* 642 */     timer.coalesce = this.coalesce;
/* 643 */     timer.actionCommand = this.actionCommand;
/* 644 */     return timer;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */