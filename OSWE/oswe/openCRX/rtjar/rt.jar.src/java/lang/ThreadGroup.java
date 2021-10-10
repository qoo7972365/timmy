/*      */ package java.lang;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import java.util.Arrays;
/*      */ import sun.misc.VM;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ThreadGroup
/*      */   implements Thread.UncaughtExceptionHandler
/*      */ {
/*      */   private final ThreadGroup parent;
/*      */   String name;
/*      */   int maxPriority;
/*      */   boolean destroyed;
/*      */   boolean daemon;
/*      */   boolean vmAllowSuspension;
/*   65 */   int nUnstartedThreads = 0;
/*      */   
/*      */   int nthreads;
/*      */   
/*      */   Thread[] threads;
/*      */   
/*      */   int ngroups;
/*      */   
/*      */   ThreadGroup[] groups;
/*      */ 
/*      */   
/*      */   private ThreadGroup() {
/*   77 */     this.name = "system";
/*   78 */     this.maxPriority = 10;
/*   79 */     this.parent = null;
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
/*      */   public ThreadGroup(String paramString) {
/*   96 */     this(Thread.currentThread().getThreadGroup(), paramString);
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
/*      */   public ThreadGroup(ThreadGroup paramThreadGroup, String paramString) {
/*  117 */     this(checkParentAccess(paramThreadGroup), paramThreadGroup, paramString);
/*      */   }
/*      */   
/*      */   private ThreadGroup(Void paramVoid, ThreadGroup paramThreadGroup, String paramString) {
/*  121 */     this.name = paramString;
/*  122 */     this.maxPriority = paramThreadGroup.maxPriority;
/*  123 */     this.daemon = paramThreadGroup.daemon;
/*  124 */     this.vmAllowSuspension = paramThreadGroup.vmAllowSuspension;
/*  125 */     this.parent = paramThreadGroup;
/*  126 */     paramThreadGroup.add(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Void checkParentAccess(ThreadGroup paramThreadGroup) {
/*  135 */     paramThreadGroup.checkAccess();
/*  136 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getName() {
/*  146 */     return this.name;
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
/*      */   public final ThreadGroup getParent() {
/*  166 */     if (this.parent != null)
/*  167 */       this.parent.checkAccess(); 
/*  168 */     return this.parent;
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
/*      */   public final int getMaxPriority() {
/*  182 */     return this.maxPriority;
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
/*      */   public final boolean isDaemon() {
/*  195 */     return this.daemon;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean isDestroyed() {
/*  205 */     return this.destroyed;
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
/*      */   public final void setDaemon(boolean paramBoolean) {
/*  227 */     checkAccess();
/*  228 */     this.daemon = paramBoolean;
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
/*      */   public final void setMaxPriority(int paramInt) {
/*      */     int i;
/*      */     Object object;
/*  262 */     synchronized (this) {
/*  263 */       checkAccess();
/*  264 */       if (paramInt < 1 || paramInt > 10) {
/*      */         return;
/*      */       }
/*  267 */       this.maxPriority = (this.parent != null) ? Math.min(paramInt, this.parent.maxPriority) : paramInt;
/*  268 */       i = this.ngroups;
/*  269 */       if (this.groups != null) {
/*  270 */         object = Arrays.<ThreadGroup>copyOf(this.groups, i);
/*      */       } else {
/*  272 */         object = null;
/*      */       } 
/*      */     } 
/*  275 */     for (byte b = 0; b < i; b++) {
/*  276 */       object[b].setMaxPriority(paramInt);
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
/*      */   public final boolean parentOf(ThreadGroup paramThreadGroup) {
/*  291 */     for (; paramThreadGroup != null; paramThreadGroup = paramThreadGroup.parent) {
/*  292 */       if (paramThreadGroup == this) {
/*  293 */         return true;
/*      */       }
/*      */     } 
/*  296 */     return false;
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
/*      */   public final void checkAccess() {
/*  313 */     SecurityManager securityManager = System.getSecurityManager();
/*  314 */     if (securityManager != null) {
/*  315 */       securityManager.checkAccess(this);
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
/*      */ 
/*      */   
/*      */   public int activeCount() {
/*      */     int i;
/*      */     int j;
/*      */     Object object;
/*  342 */     synchronized (this) {
/*  343 */       if (this.destroyed) {
/*  344 */         return 0;
/*      */       }
/*  346 */       i = this.nthreads;
/*  347 */       j = this.ngroups;
/*  348 */       if (this.groups != null) {
/*  349 */         object = Arrays.<ThreadGroup>copyOf(this.groups, j);
/*      */       } else {
/*  351 */         object = null;
/*      */       } 
/*      */     } 
/*  354 */     for (byte b = 0; b < j; b++) {
/*  355 */       i += object[b].activeCount();
/*      */     }
/*  357 */     return i;
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
/*      */   public int enumerate(Thread[] paramArrayOfThread) {
/*  383 */     checkAccess();
/*  384 */     return enumerate(paramArrayOfThread, 0, true);
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
/*      */   public int enumerate(Thread[] paramArrayOfThread, boolean paramBoolean) {
/*  421 */     checkAccess();
/*  422 */     return enumerate(paramArrayOfThread, 0, paramBoolean);
/*      */   }
/*      */   
/*      */   private int enumerate(Thread[] paramArrayOfThread, int paramInt, boolean paramBoolean) {
/*  426 */     int i = 0;
/*  427 */     ThreadGroup[] arrayOfThreadGroup = null;
/*  428 */     synchronized (this) {
/*  429 */       if (this.destroyed) {
/*  430 */         return 0;
/*      */       }
/*  432 */       int j = this.nthreads;
/*  433 */       if (j > paramArrayOfThread.length - paramInt) {
/*  434 */         j = paramArrayOfThread.length - paramInt;
/*      */       }
/*  436 */       for (byte b = 0; b < j; b++) {
/*  437 */         if (this.threads[b].isAlive()) {
/*  438 */           paramArrayOfThread[paramInt++] = this.threads[b];
/*      */         }
/*      */       } 
/*  441 */       if (paramBoolean) {
/*  442 */         i = this.ngroups;
/*  443 */         if (this.groups != null) {
/*  444 */           arrayOfThreadGroup = Arrays.<ThreadGroup>copyOf(this.groups, i);
/*      */         } else {
/*  446 */           arrayOfThreadGroup = null;
/*      */         } 
/*      */       } 
/*      */     } 
/*  450 */     if (paramBoolean) {
/*  451 */       for (byte b = 0; b < i; b++) {
/*  452 */         paramInt = arrayOfThreadGroup[b].enumerate(paramArrayOfThread, paramInt, true);
/*      */       }
/*      */     }
/*  455 */     return paramInt;
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
/*      */   public int activeGroupCount() {
/*      */     int i;
/*      */     Object object;
/*  476 */     synchronized (this) {
/*  477 */       if (this.destroyed) {
/*  478 */         return 0;
/*      */       }
/*  480 */       i = this.ngroups;
/*  481 */       if (this.groups != null) {
/*  482 */         object = Arrays.<ThreadGroup>copyOf(this.groups, i);
/*      */       } else {
/*  484 */         object = null;
/*      */       } 
/*      */     } 
/*  487 */     int j = i;
/*  488 */     for (byte b = 0; b < i; b++) {
/*  489 */       j += object[b].activeGroupCount();
/*      */     }
/*  491 */     return j;
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
/*      */   public int enumerate(ThreadGroup[] paramArrayOfThreadGroup) {
/*  517 */     checkAccess();
/*  518 */     return enumerate(paramArrayOfThreadGroup, 0, true);
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
/*      */   public int enumerate(ThreadGroup[] paramArrayOfThreadGroup, boolean paramBoolean) {
/*  555 */     checkAccess();
/*  556 */     return enumerate(paramArrayOfThreadGroup, 0, paramBoolean);
/*      */   }
/*      */   
/*      */   private int enumerate(ThreadGroup[] paramArrayOfThreadGroup, int paramInt, boolean paramBoolean) {
/*  560 */     int i = 0;
/*  561 */     ThreadGroup[] arrayOfThreadGroup = null;
/*  562 */     synchronized (this) {
/*  563 */       if (this.destroyed) {
/*  564 */         return 0;
/*      */       }
/*  566 */       int j = this.ngroups;
/*  567 */       if (j > paramArrayOfThreadGroup.length - paramInt) {
/*  568 */         j = paramArrayOfThreadGroup.length - paramInt;
/*      */       }
/*  570 */       if (j > 0) {
/*  571 */         System.arraycopy(this.groups, 0, paramArrayOfThreadGroup, paramInt, j);
/*  572 */         paramInt += j;
/*      */       } 
/*  574 */       if (paramBoolean) {
/*  575 */         i = this.ngroups;
/*  576 */         if (this.groups != null) {
/*  577 */           arrayOfThreadGroup = Arrays.<ThreadGroup>copyOf(this.groups, i);
/*      */         } else {
/*  579 */           arrayOfThreadGroup = null;
/*      */         } 
/*      */       } 
/*      */     } 
/*  583 */     if (paramBoolean) {
/*  584 */       for (byte b = 0; b < i; b++) {
/*  585 */         paramInt = arrayOfThreadGroup[b].enumerate(paramArrayOfThreadGroup, paramInt, true);
/*      */       }
/*      */     }
/*  588 */     return paramInt;
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
/*      */   @Deprecated
/*      */   public final void stop() {
/*  612 */     if (stopOrSuspend(false)) {
/*  613 */       Thread.currentThread().stop();
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
/*      */   public final void interrupt() {
/*      */     int i;
/*      */     Object object;
/*  636 */     synchronized (this) {
/*  637 */       checkAccess();
/*  638 */       for (byte b1 = 0; b1 < this.nthreads; b1++) {
/*  639 */         this.threads[b1].interrupt();
/*      */       }
/*  641 */       i = this.ngroups;
/*  642 */       if (this.groups != null) {
/*  643 */         object = Arrays.<ThreadGroup>copyOf(this.groups, i);
/*      */       } else {
/*  645 */         object = null;
/*      */       } 
/*      */     } 
/*  648 */     for (byte b = 0; b < i; b++) {
/*  649 */       object[b].interrupt();
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
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public final void suspend() {
/*  675 */     if (stopOrSuspend(true)) {
/*  676 */       Thread.currentThread().suspend();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean stopOrSuspend(boolean paramBoolean) {
/*      */     int i;
/*  688 */     boolean bool = false;
/*  689 */     Thread thread = Thread.currentThread();
/*      */     
/*  691 */     ThreadGroup[] arrayOfThreadGroup = null;
/*  692 */     synchronized (this) {
/*  693 */       checkAccess();
/*  694 */       for (byte b1 = 0; b1 < this.nthreads; b1++) {
/*  695 */         if (this.threads[b1] == thread) {
/*  696 */           bool = true;
/*  697 */         } else if (paramBoolean) {
/*  698 */           this.threads[b1].suspend();
/*      */         } else {
/*  700 */           this.threads[b1].stop();
/*      */         } 
/*      */       } 
/*  703 */       i = this.ngroups;
/*  704 */       if (this.groups != null) {
/*  705 */         arrayOfThreadGroup = Arrays.<ThreadGroup>copyOf(this.groups, i);
/*      */       }
/*      */     } 
/*  708 */     for (byte b = 0; b < i; b++) {
/*  709 */       bool = (arrayOfThreadGroup[b].stopOrSuspend(paramBoolean) || bool) ? true : false;
/*      */     }
/*  711 */     return bool;
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
/*      */   @Deprecated
/*      */   public final void resume() {
/*      */     int i;
/*      */     Object object;
/*  740 */     synchronized (this) {
/*  741 */       checkAccess();
/*  742 */       for (byte b1 = 0; b1 < this.nthreads; b1++) {
/*  743 */         this.threads[b1].resume();
/*      */       }
/*  745 */       i = this.ngroups;
/*  746 */       if (this.groups != null) {
/*  747 */         object = Arrays.<ThreadGroup>copyOf(this.groups, i);
/*      */       } else {
/*  749 */         object = null;
/*      */       } 
/*      */     } 
/*  752 */     for (byte b = 0; b < i; b++) {
/*  753 */       object[b].resume();
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
/*      */   public final void destroy() {
/*      */     int i;
/*      */     Object object;
/*  775 */     synchronized (this) {
/*  776 */       checkAccess();
/*  777 */       if (this.destroyed || this.nthreads > 0) {
/*  778 */         throw new IllegalThreadStateException();
/*      */       }
/*  780 */       i = this.ngroups;
/*  781 */       if (this.groups != null) {
/*  782 */         object = Arrays.<ThreadGroup>copyOf(this.groups, i);
/*      */       } else {
/*  784 */         object = null;
/*      */       } 
/*  786 */       if (this.parent != null) {
/*  787 */         this.destroyed = true;
/*  788 */         this.ngroups = 0;
/*  789 */         this.groups = null;
/*  790 */         this.nthreads = 0;
/*  791 */         this.threads = null;
/*      */       } 
/*      */     } 
/*  794 */     for (byte b = 0; b < i; b++) {
/*  795 */       object[b].destroy();
/*      */     }
/*  797 */     if (this.parent != null) {
/*  798 */       this.parent.remove(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void add(ThreadGroup paramThreadGroup) {
/*  808 */     synchronized (this) {
/*  809 */       if (this.destroyed) {
/*  810 */         throw new IllegalThreadStateException();
/*      */       }
/*  812 */       if (this.groups == null) {
/*  813 */         this.groups = new ThreadGroup[4];
/*  814 */       } else if (this.ngroups == this.groups.length) {
/*  815 */         this.groups = Arrays.<ThreadGroup>copyOf(this.groups, this.ngroups * 2);
/*      */       } 
/*  817 */       this.groups[this.ngroups] = paramThreadGroup;
/*      */ 
/*      */ 
/*      */       
/*  821 */       this.ngroups++;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void remove(ThreadGroup paramThreadGroup) {
/*  831 */     synchronized (this) {
/*  832 */       if (this.destroyed) {
/*      */         return;
/*      */       }
/*  835 */       for (byte b = 0; b < this.ngroups; b++) {
/*  836 */         if (this.groups[b] == paramThreadGroup) {
/*  837 */           this.ngroups--;
/*  838 */           System.arraycopy(this.groups, b + 1, this.groups, b, this.ngroups - b);
/*      */ 
/*      */           
/*  841 */           this.groups[this.ngroups] = null;
/*      */           break;
/*      */         } 
/*      */       } 
/*  845 */       if (this.nthreads == 0) {
/*  846 */         notifyAll();
/*      */       }
/*  848 */       if (this.daemon && this.nthreads == 0 && this.nUnstartedThreads == 0 && this.ngroups == 0)
/*      */       {
/*      */         
/*  851 */         destroy();
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
/*      */ 
/*      */   
/*      */   void addUnstarted() {
/*  865 */     synchronized (this) {
/*  866 */       if (this.destroyed) {
/*  867 */         throw new IllegalThreadStateException();
/*      */       }
/*  869 */       this.nUnstartedThreads++;
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
/*      */   void add(Thread paramThread) {
/*  887 */     synchronized (this) {
/*  888 */       if (this.destroyed) {
/*  889 */         throw new IllegalThreadStateException();
/*      */       }
/*  891 */       if (this.threads == null) {
/*  892 */         this.threads = new Thread[4];
/*  893 */       } else if (this.nthreads == this.threads.length) {
/*  894 */         this.threads = Arrays.<Thread>copyOf(this.threads, this.nthreads * 2);
/*      */       } 
/*  896 */       this.threads[this.nthreads] = paramThread;
/*      */ 
/*      */ 
/*      */       
/*  900 */       this.nthreads++;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  906 */       this.nUnstartedThreads--;
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
/*      */   void threadStartFailed(Thread paramThread) {
/*  923 */     synchronized (this) {
/*  924 */       remove(paramThread);
/*  925 */       this.nUnstartedThreads++;
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
/*      */   void threadTerminated(Thread paramThread) {
/*  941 */     synchronized (this) {
/*  942 */       remove(paramThread);
/*      */       
/*  944 */       if (this.nthreads == 0) {
/*  945 */         notifyAll();
/*      */       }
/*  947 */       if (this.daemon && this.nthreads == 0 && this.nUnstartedThreads == 0 && this.ngroups == 0)
/*      */       {
/*      */         
/*  950 */         destroy();
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
/*      */   
/*      */   private void remove(Thread paramThread) {
/*  963 */     synchronized (this) {
/*  964 */       if (this.destroyed) {
/*      */         return;
/*      */       }
/*  967 */       for (byte b = 0; b < this.nthreads; b++) {
/*  968 */         if (this.threads[b] == paramThread) {
/*  969 */           System.arraycopy(this.threads, b + 1, this.threads, b, --this.nthreads - b);
/*      */ 
/*      */           
/*  972 */           this.threads[this.nthreads] = null;
/*      */           break;
/*      */         } 
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
/*      */   public void list() {
/*  986 */     list(System.out, 0);
/*      */   }
/*      */   void list(PrintStream paramPrintStream, int paramInt) {
/*      */     int i;
/*      */     Object object;
/*  991 */     synchronized (this) {
/*  992 */       byte b1; for (b1 = 0; b1 < paramInt; b1++) {
/*  993 */         paramPrintStream.print(" ");
/*      */       }
/*  995 */       paramPrintStream.println(this);
/*  996 */       paramInt += 4;
/*  997 */       for (b1 = 0; b1 < this.nthreads; b1++) {
/*  998 */         for (byte b2 = 0; b2 < paramInt; b2++) {
/*  999 */           paramPrintStream.print(" ");
/*      */         }
/* 1001 */         paramPrintStream.println(this.threads[b1]);
/*      */       } 
/* 1003 */       i = this.ngroups;
/* 1004 */       if (this.groups != null) {
/* 1005 */         object = Arrays.<ThreadGroup>copyOf(this.groups, i);
/*      */       } else {
/* 1007 */         object = null;
/*      */       } 
/*      */     } 
/* 1010 */     for (byte b = 0; b < i; b++) {
/* 1011 */       object[b].list(paramPrintStream, paramInt);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
/* 1051 */     if (this.parent != null) {
/* 1052 */       this.parent.uncaughtException(paramThread, paramThrowable);
/*      */     } else {
/*      */       
/* 1055 */       Thread.UncaughtExceptionHandler uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
/* 1056 */       if (uncaughtExceptionHandler != null) {
/* 1057 */         uncaughtExceptionHandler.uncaughtException(paramThread, paramThrowable);
/* 1058 */       } else if (!(paramThrowable instanceof ThreadDeath)) {
/* 1059 */         System.err.print("Exception in thread \"" + paramThread
/* 1060 */             .getName() + "\" ");
/* 1061 */         paramThrowable.printStackTrace(System.err);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean allowThreadSuspension(boolean paramBoolean) {
/* 1078 */     this.vmAllowSuspension = paramBoolean;
/* 1079 */     if (!paramBoolean) {
/* 1080 */       VM.unsuspendSomeThreads();
/*      */     }
/* 1082 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1092 */     return getClass().getName() + "[name=" + getName() + ",maxpri=" + this.maxPriority + "]";
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/ThreadGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */