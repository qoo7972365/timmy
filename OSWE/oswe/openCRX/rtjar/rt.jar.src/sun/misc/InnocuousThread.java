/*     */ package sun.misc;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.ProtectionDomain;
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
/*     */ public final class InnocuousThread
/*     */   extends Thread
/*     */ {
/*     */   private static final Unsafe UNSAFE;
/*     */   private static final long THREAD_LOCALS;
/*     */   private static final long INHERITABLE_THREAD_LOCALS;
/*     */   private static final ThreadGroup INNOCUOUSTHREADGROUP;
/*     */   private static final AccessControlContext ACC;
/*     */   private static final long INHERITEDACCESSCONTROLCONTEXT;
/*     */   private static final long CONTEXTCLASSLOADER;
/*  49 */   private static final AtomicInteger threadNumber = new AtomicInteger(1);
/*     */   private static String newName() {
/*  51 */     return "InnocuousThread-" + threadNumber.getAndIncrement();
/*     */   }
/*     */ 
/*     */   
/*     */   private volatile boolean hasRun;
/*     */ 
/*     */   
/*     */   public static Thread newSystemThread(Runnable paramRunnable) {
/*  59 */     return newSystemThread(newName(), paramRunnable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Thread newSystemThread(String paramString, Runnable paramRunnable) {
/*  66 */     return new InnocuousThread(INNOCUOUSTHREADGROUP, paramRunnable, paramString, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public InnocuousThread(Runnable paramRunnable) {
/*  71 */     super(INNOCUOUSTHREADGROUP, paramRunnable, newName());
/*  72 */     UNSAFE.putOrderedObject(this, INHERITEDACCESSCONTROLCONTEXT, ACC);
/*  73 */     eraseThreadLocals();
/*     */   }
/*     */   
/*     */   private InnocuousThread(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString, ClassLoader paramClassLoader) {
/*  77 */     super(paramThreadGroup, paramRunnable, paramString, 0L);
/*  78 */     UNSAFE.putOrderedObject(this, INHERITEDACCESSCONTROLCONTEXT, ACC);
/*  79 */     UNSAFE.putOrderedObject(this, CONTEXTCLASSLOADER, paramClassLoader);
/*  80 */     eraseThreadLocals();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassLoader getContextClassLoader() {
/*  86 */     return ClassLoader.getSystemClassLoader();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContextClassLoader(ClassLoader paramClassLoader) {
/*  96 */     throw new SecurityException("setContextClassLoader");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 104 */     if (Thread.currentThread() == this && !this.hasRun) {
/* 105 */       this.hasRun = true;
/* 106 */       super.run();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void eraseThreadLocals() {
/* 114 */     UNSAFE.putObject(this, THREAD_LOCALS, (Object)null);
/* 115 */     UNSAFE.putObject(this, INHERITABLE_THREAD_LOCALS, (Object)null);
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 121 */       ACC = new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, null) });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 126 */       UNSAFE = Unsafe.getUnsafe();
/* 127 */       Class<Thread> clazz = Thread.class;
/* 128 */       Class<ThreadGroup> clazz1 = ThreadGroup.class;
/*     */ 
/*     */       
/* 131 */       THREAD_LOCALS = UNSAFE.objectFieldOffset(clazz.getDeclaredField("threadLocals"));
/*     */       
/* 133 */       INHERITABLE_THREAD_LOCALS = UNSAFE.objectFieldOffset(clazz.getDeclaredField("inheritableThreadLocals"));
/*     */       
/* 135 */       INHERITEDACCESSCONTROLCONTEXT = UNSAFE.objectFieldOffset(clazz.getDeclaredField("inheritedAccessControlContext"));
/*     */       
/* 137 */       CONTEXTCLASSLOADER = UNSAFE.objectFieldOffset(clazz.getDeclaredField("contextClassLoader"));
/*     */       
/* 139 */       long l1 = UNSAFE.objectFieldOffset(clazz.getDeclaredField("group"));
/* 140 */       long l2 = UNSAFE.objectFieldOffset(clazz1.getDeclaredField("parent"));
/*     */       
/* 142 */       ThreadGroup threadGroup1 = (ThreadGroup)UNSAFE.getObject(Thread.currentThread(), l1);
/*     */       
/* 144 */       while (threadGroup1 != null) {
/* 145 */         ThreadGroup threadGroup = (ThreadGroup)UNSAFE.getObject(threadGroup1, l2);
/* 146 */         if (threadGroup == null)
/*     */           break; 
/* 148 */         threadGroup1 = threadGroup;
/*     */       } 
/* 150 */       final ThreadGroup root = threadGroup1;
/* 151 */       INNOCUOUSTHREADGROUP = AccessController.<ThreadGroup>doPrivileged(new PrivilegedAction<ThreadGroup>()
/*     */           {
/*     */             public ThreadGroup run()
/*     */             {
/* 155 */               return new ThreadGroup(root, "InnocuousThreadGroup");
/*     */             }
/*     */           });
/* 158 */     } catch (Exception exception) {
/* 159 */       throw new Error(exception);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/InnocuousThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */