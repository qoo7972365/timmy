/*     */ package sun.rmi.runtime;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.security.util.SecurityConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NewThreadAction
/*     */   implements PrivilegedAction<Thread>
/*     */ {
/*  55 */   static final ThreadGroup systemThreadGroup = AccessController.<ThreadGroup>doPrivileged(new PrivilegedAction<ThreadGroup>() {
/*     */         public ThreadGroup run() {
/*  57 */           ThreadGroup threadGroup1 = Thread.currentThread().getThreadGroup();
/*     */           ThreadGroup threadGroup2;
/*  59 */           while ((threadGroup2 = threadGroup1.getParent()) != null) {
/*  60 */             threadGroup1 = threadGroup2;
/*     */           }
/*  62 */           return threadGroup1;
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   static final ThreadGroup userThreadGroup = AccessController.<ThreadGroup>doPrivileged(new PrivilegedAction<ThreadGroup>() {
/*     */         public ThreadGroup run() {
/*  74 */           return new ThreadGroup(NewThreadAction.systemThreadGroup, "RMI Runtime");
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   private final ThreadGroup group;
/*     */   
/*     */   private final Runnable runnable;
/*     */   private final String name;
/*     */   private final boolean daemon;
/*     */   
/*     */   NewThreadAction(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString, boolean paramBoolean) {
/*  86 */     this.group = paramThreadGroup;
/*  87 */     this.runnable = paramRunnable;
/*  88 */     this.name = paramString;
/*  89 */     this.daemon = paramBoolean;
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
/*     */   public NewThreadAction(Runnable paramRunnable, String paramString, boolean paramBoolean) {
/* 104 */     this(systemThreadGroup, paramRunnable, paramString, paramBoolean);
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
/*     */   public NewThreadAction(Runnable paramRunnable, String paramString, boolean paramBoolean1, boolean paramBoolean2) {
/* 124 */     this(paramBoolean2 ? userThreadGroup : systemThreadGroup, paramRunnable, paramString, paramBoolean1);
/*     */   }
/*     */ 
/*     */   
/*     */   public Thread run() {
/* 129 */     SecurityManager securityManager = System.getSecurityManager();
/* 130 */     if (securityManager != null) {
/* 131 */       securityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
/*     */     }
/* 133 */     Thread thread = new Thread(this.group, this.runnable, "RMI " + this.name);
/* 134 */     thread.setContextClassLoader(ClassLoader.getSystemClassLoader());
/* 135 */     thread.setDaemon(this.daemon);
/* 136 */     return thread;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/runtime/NewThreadAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */