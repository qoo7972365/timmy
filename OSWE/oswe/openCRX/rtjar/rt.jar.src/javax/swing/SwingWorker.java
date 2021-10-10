/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.FutureTask;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.RunnableFuture;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import sun.awt.AppContext;
/*     */ import sun.swing.AccumulativeRunnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SwingWorker<T, V>
/*     */   implements RunnableFuture<T>
/*     */ {
/*     */   private static final int MAX_WORKER_THREADS = 10;
/*     */   private volatile int progress;
/*     */   private volatile StateValue state;
/*     */   private final FutureTask<T> future;
/*     */   private final PropertyChangeSupport propertyChangeSupport;
/*     */   private AccumulativeRunnable<V> doProcess;
/*     */   private AccumulativeRunnable<Integer> doNotifyProgressChange;
/* 262 */   private final AccumulativeRunnable<Runnable> doSubmit = getDoSubmit();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum StateValue
/*     */   {
/* 272 */     PENDING,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     STARTED,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     DONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SwingWorker() {
/* 291 */     Callable<T> callable = new Callable<T>()
/*     */       {
/*     */         public T call() throws Exception {
/* 294 */           SwingWorker.this.setState(SwingWorker.StateValue.STARTED);
/* 295 */           return (T)SwingWorker.this.doInBackground();
/*     */         }
/*     */       };
/*     */     
/* 299 */     this.future = new FutureTask<T>(callable)
/*     */       {
/*     */         protected void done() {
/* 302 */           SwingWorker.this.doneEDT();
/* 303 */           SwingWorker.this.setState(SwingWorker.StateValue.DONE);
/*     */         }
/*     */       };
/*     */     
/* 307 */     this.state = StateValue.PENDING;
/* 308 */     this.propertyChangeSupport = new SwingWorkerPropertyChangeSupport(this);
/* 309 */     this.doProcess = null;
/* 310 */     this.doNotifyProgressChange = null;
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
/*     */   public final void run() {
/* 334 */     this.future.run();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SafeVarargs
/*     */   protected final void publish(V... paramVarArgs) {
/* 409 */     synchronized (this) {
/* 410 */       if (this.doProcess == null) {
/* 411 */         this.doProcess = new AccumulativeRunnable<V>()
/*     */           {
/*     */             public void run(List<V> param1List) {
/* 414 */               SwingWorker.this.process(param1List);
/*     */             }
/*     */             
/*     */             protected void submit() {
/* 418 */               SwingWorker.this.doSubmit.add(new Runnable[] { this });
/*     */             }
/*     */           };
/*     */       }
/*     */     } 
/* 423 */     this.doProcess.add(paramVarArgs);
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
/*     */   protected void process(List<V> paramList) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void done() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setProgress(int paramInt) {
/* 484 */     if (paramInt < 0 || paramInt > 100) {
/* 485 */       throw new IllegalArgumentException("the value should be from 0 to 100");
/*     */     }
/* 487 */     if (this.progress == paramInt) {
/*     */       return;
/*     */     }
/* 490 */     int i = this.progress;
/* 491 */     this.progress = paramInt;
/* 492 */     if (!getPropertyChangeSupport().hasListeners("progress")) {
/*     */       return;
/*     */     }
/* 495 */     synchronized (this) {
/* 496 */       if (this.doNotifyProgressChange == null) {
/* 497 */         this.doNotifyProgressChange = new AccumulativeRunnable<Integer>()
/*     */           {
/*     */             public void run(List<Integer> param1List)
/*     */             {
/* 501 */               SwingWorker.this.firePropertyChange("progress", param1List
/* 502 */                   .get(0), param1List
/* 503 */                   .get(param1List.size() - 1));
/*     */             }
/*     */             
/*     */             protected void submit() {
/* 507 */               SwingWorker.this.doSubmit.add(new Runnable[] { this });
/*     */             }
/*     */           };
/*     */       }
/*     */     } 
/* 512 */     this.doNotifyProgressChange.add(new Integer[] { Integer.valueOf(i), Integer.valueOf(paramInt) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getProgress() {
/* 521 */     return this.progress;
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
/*     */   public final void execute() {
/* 538 */     getWorkersExecutorService().execute(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean cancel(boolean paramBoolean) {
/* 546 */     return this.future.cancel(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isCancelled() {
/* 553 */     return this.future.isCancelled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isDone() {
/* 560 */     return this.future.isDone();
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
/*     */   public final T get() throws InterruptedException, ExecutionException {
/* 602 */     return this.future.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T get(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ExecutionException, TimeoutException {
/* 612 */     return this.future.get(paramLong, paramTimeUnit);
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
/*     */   public final void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 631 */     getPropertyChangeSupport().addPropertyChangeListener(paramPropertyChangeListener);
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
/*     */   public final void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 649 */     getPropertyChangeSupport().removePropertyChangeListener(paramPropertyChangeListener);
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
/*     */   public final void firePropertyChange(String paramString, Object paramObject1, Object paramObject2) {
/* 676 */     getPropertyChangeSupport().firePropertyChange(paramString, paramObject1, paramObject2);
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
/*     */   public final PropertyChangeSupport getPropertyChangeSupport() {
/* 698 */     return this.propertyChangeSupport;
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
/*     */   public final StateValue getState() {
/* 713 */     if (isDone()) {
/* 714 */       return StateValue.DONE;
/*     */     }
/* 716 */     return this.state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setState(StateValue paramStateValue) {
/* 725 */     StateValue stateValue = this.state;
/* 726 */     this.state = paramStateValue;
/* 727 */     firePropertyChange("state", stateValue, paramStateValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doneEDT() {
/* 734 */     Runnable runnable = new Runnable()
/*     */       {
/*     */         public void run() {
/* 737 */           SwingWorker.this.done();
/*     */         }
/*     */       };
/* 740 */     if (SwingUtilities.isEventDispatchThread()) {
/* 741 */       runnable.run();
/*     */     } else {
/* 743 */       this.doSubmit.add(new Runnable[] { runnable });
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
/*     */   private static synchronized ExecutorService getWorkersExecutorService() {
/* 757 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/* 759 */     ExecutorService executorService = (ExecutorService)appContext.get(SwingWorker.class);
/* 760 */     if (executorService == null) {
/*     */       
/* 762 */       ThreadFactory threadFactory = new ThreadFactory()
/*     */         {
/*     */           
/* 765 */           final ThreadFactory defaultFactory = Executors.defaultThreadFactory();
/*     */           
/*     */           public Thread newThread(Runnable param1Runnable) {
/* 768 */             Thread thread = this.defaultFactory.newThread(param1Runnable);
/* 769 */             thread.setName("SwingWorker-" + thread
/* 770 */                 .getName());
/* 771 */             thread.setDaemon(true);
/* 772 */             return thread;
/*     */           }
/*     */         };
/*     */       
/* 776 */       executorService = new ThreadPoolExecutor(10, 10, 10L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), threadFactory);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 781 */       appContext.put(SwingWorker.class, executorService);
/*     */ 
/*     */ 
/*     */       
/* 785 */       final ExecutorService es = executorService;
/* 786 */       appContext.addPropertyChangeListener("disposed", new PropertyChangeListener()
/*     */           {
/*     */             public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent)
/*     */             {
/* 790 */               boolean bool = ((Boolean)param1PropertyChangeEvent.getNewValue()).booleanValue();
/* 791 */               if (bool) {
/* 792 */                 WeakReference<ExecutorService> weakReference = new WeakReference<>(es);
/*     */ 
/*     */                 
/* 795 */                 final ExecutorService executorService = weakReference.get();
/* 796 */                 if (executorService != null) {
/* 797 */                   AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */                       {
/*     */                         public Void run() {
/* 800 */                           executorService.shutdown();
/* 801 */                           return null;
/*     */                         }
/*     */                       });
/*     */                 }
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */ 
/*     */     
/* 811 */     return executorService;
/*     */   }
/*     */   
/* 814 */   private static final Object DO_SUBMIT_KEY = new StringBuilder("doSubmit");
/*     */   private static AccumulativeRunnable<Runnable> getDoSubmit() {
/* 816 */     synchronized (DO_SUBMIT_KEY) {
/* 817 */       AppContext appContext = AppContext.getAppContext();
/* 818 */       Object object = appContext.get(DO_SUBMIT_KEY);
/* 819 */       if (object == null) {
/* 820 */         object = new DoSubmitAccumulativeRunnable();
/* 821 */         appContext.put(DO_SUBMIT_KEY, object);
/*     */       } 
/* 823 */       return (AccumulativeRunnable<Runnable>)object;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract T doInBackground() throws Exception;
/*     */   
/*     */   private static class DoSubmitAccumulativeRunnable extends AccumulativeRunnable<Runnable> implements ActionListener {
/*     */     protected void run(List<Runnable> param1List) {
/* 831 */       for (Runnable runnable : param1List)
/* 832 */         runnable.run(); 
/*     */     }
/*     */     private static final int DELAY = 33;
/*     */     private DoSubmitAccumulativeRunnable() {}
/*     */     protected void submit() {
/* 837 */       Timer timer = new Timer(33, this);
/* 838 */       timer.setRepeats(false);
/* 839 */       timer.start();
/*     */     }
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 842 */       run();
/*     */     }
/*     */   }
/*     */   
/*     */   private class SwingWorkerPropertyChangeSupport
/*     */     extends PropertyChangeSupport {
/*     */     SwingWorkerPropertyChangeSupport(Object param1Object) {
/* 849 */       super(param1Object);
/*     */     }
/*     */     
/*     */     public void firePropertyChange(final PropertyChangeEvent evt) {
/* 853 */       if (SwingUtilities.isEventDispatchThread()) {
/* 854 */         super.firePropertyChange(evt);
/*     */       } else {
/* 856 */         SwingWorker.this.doSubmit.add(new Runnable[] { new Runnable()
/*     */               {
/*     */                 public void run() {
/* 859 */                   SwingWorker.SwingWorkerPropertyChangeSupport.this
/* 860 */                     .firePropertyChange(evt);
/*     */                 }
/*     */               } });
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/SwingWorker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */