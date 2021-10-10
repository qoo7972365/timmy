/*     */ package sun.rmi.runtime;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Level;
/*     */ import sun.security.action.GetIntegerAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RuntimeUtil
/*     */ {
/*  53 */   private static final Log runtimeLog = Log.getLog("sun.rmi.runtime", (String)null, false);
/*     */ 
/*     */ 
/*     */   
/*  57 */   private static final int schedulerThreads = ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction("sun.rmi.runtime.schedulerThreads", 1))).intValue();
/*     */ 
/*     */ 
/*     */   
/*  61 */   private static final Permission GET_INSTANCE_PERMISSION = new RuntimePermission("sun.rmi.runtime.RuntimeUtil.getInstance");
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static final RuntimeUtil instance = new RuntimeUtil();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private final ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(schedulerThreads, new ThreadFactory()
/*     */       {
/*     */         
/*  74 */         private final AtomicInteger count = new AtomicInteger(0);
/*     */         public Thread newThread(Runnable param1Runnable) {
/*     */           try {
/*  77 */             return AccessController.<Thread>doPrivileged(new NewThreadAction(param1Runnable, "Scheduler(" + this.count
/*     */                   
/*  79 */                   .getAndIncrement() + ")", true));
/*     */           }
/*  81 */           catch (Throwable throwable) {
/*  82 */             RuntimeUtil.runtimeLog.log(Level.WARNING, "scheduler thread factory throws", throwable);
/*     */             
/*  84 */             return null;
/*     */           } 
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class GetInstanceAction
/*     */     implements PrivilegedAction<RuntimeUtil>
/*     */   {
/*     */     public RuntimeUtil run() {
/* 110 */       return RuntimeUtil.getInstance();
/*     */     }
/*     */   }
/*     */   
/*     */   private static RuntimeUtil getInstance() {
/* 115 */     SecurityManager securityManager = System.getSecurityManager();
/* 116 */     if (securityManager != null) {
/* 117 */       securityManager.checkPermission(GET_INSTANCE_PERMISSION);
/*     */     }
/* 119 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledThreadPoolExecutor getScheduler() {
/* 129 */     return this.scheduler;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/runtime/RuntimeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */