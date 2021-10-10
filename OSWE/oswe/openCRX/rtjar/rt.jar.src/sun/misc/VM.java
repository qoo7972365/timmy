/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VM
/*     */ {
/*     */   private static boolean suspended = false;
/*     */   @Deprecated
/*     */   public static final int STATE_GREEN = 1;
/*     */   @Deprecated
/*     */   public static final int STATE_YELLOW = 2;
/*     */   @Deprecated
/*     */   public static final int STATE_RED = 3;
/*     */   
/*     */   @Deprecated
/*     */   public static boolean threadsSuspended() {
/*  50 */     return suspended;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean allowThreadSuspension(ThreadGroup paramThreadGroup, boolean paramBoolean) {
/*  55 */     return paramThreadGroup.allowThreadSuspension(paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static boolean suspendThreads() {
/*  61 */     suspended = true;
/*  62 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void unsuspendThreads() {
/*  69 */     suspended = false;
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
/*     */   @Deprecated
/*     */   public static void unsuspendSomeThreads() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static final int getState() {
/*  95 */     return 1;
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
/*     */   @Deprecated
/*     */   public static void registerVMNotification(VMNotification paramVMNotification) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void asChange(int paramInt1, int paramInt2) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void asChange_otherthread(int paramInt1, int paramInt2) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static volatile boolean booted = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   private static final Object lock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void booted() {
/* 161 */     synchronized (lock) {
/* 162 */       booted = true;
/* 163 */       lock.notifyAll();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isBooted() {
/* 168 */     return booted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void awaitBooted() throws InterruptedException {
/* 175 */     synchronized (lock) {
/* 176 */       while (!booted) {
/* 177 */         lock.wait();
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
/* 190 */   private static long directMemory = 67108864L;
/*     */ 
/*     */   
/*     */   private static boolean pageAlignDirectMemory;
/*     */ 
/*     */   
/*     */   public static long maxDirectMemory() {
/* 197 */     return directMemory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDirectMemoryPageAligned() {
/* 208 */     return pageAlignDirectMemory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean defaultAllowArraySyntax = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 221 */   private static boolean allowArraySyntax = defaultAllowArraySyntax;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean allowArraySyntax() {
/* 234 */     return allowArraySyntax;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSystemDomainLoader(ClassLoader paramClassLoader) {
/* 242 */     return (paramClassLoader == null);
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
/*     */   public static String getSavedProperty(String paramString) {
/* 258 */     if (savedProps.isEmpty()) {
/* 259 */       throw new IllegalStateException("Should be non-empty if initialized");
/*     */     }
/* 261 */     return savedProps.getProperty(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 267 */   private static final Properties savedProps = new Properties();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveAndRemoveProperties(Properties paramProperties) {
/* 274 */     if (booted) {
/* 275 */       throw new IllegalStateException("System initialization has completed");
/*     */     }
/* 277 */     savedProps.putAll(paramProperties);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     String str = (String)paramProperties.remove("sun.nio.MaxDirectMemorySize");
/* 285 */     if (str != null) {
/* 286 */       if (str.equals("-1")) {
/*     */         
/* 288 */         directMemory = Runtime.getRuntime().maxMemory();
/*     */       } else {
/* 290 */         long l = Long.parseLong(str);
/* 291 */         if (l > -1L) {
/* 292 */           directMemory = l;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 297 */     str = (String)paramProperties.remove("sun.nio.PageAlignDirectMemory");
/* 298 */     if ("true".equals(str)) {
/* 299 */       pageAlignDirectMemory = true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 304 */     str = paramProperties.getProperty("sun.lang.ClassLoader.allowArraySyntax");
/*     */ 
/*     */     
/* 307 */     allowArraySyntax = (str == null) ? defaultAllowArraySyntax : Boolean.parseBoolean(str);
/*     */ 
/*     */ 
/*     */     
/* 311 */     paramProperties.remove("java.lang.Integer.IntegerCache.high");
/*     */ 
/*     */     
/* 314 */     paramProperties.remove("sun.zip.disableMemoryMapping");
/*     */ 
/*     */     
/* 317 */     paramProperties.remove("sun.java.launcher.diag");
/*     */ 
/*     */     
/* 320 */     paramProperties.remove("sun.cds.enableSharedLookupCache");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initializeOSEnvironment() {
/* 327 */     if (!booted) {
/* 328 */       OSEnvironment.initialize();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 333 */   private static volatile int finalRefCount = 0;
/*     */ 
/*     */   
/* 336 */   private static volatile int peakFinalRefCount = 0;
/*     */   
/*     */   private static final int JVMTI_THREAD_STATE_ALIVE = 1;
/*     */   
/*     */   private static final int JVMTI_THREAD_STATE_TERMINATED = 2;
/*     */   private static final int JVMTI_THREAD_STATE_RUNNABLE = 4;
/*     */   
/*     */   public static int getFinalRefCount() {
/* 344 */     return finalRefCount;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final int JVMTI_THREAD_STATE_BLOCKED_ON_MONITOR_ENTER = 1024;
/*     */   private static final int JVMTI_THREAD_STATE_WAITING_INDEFINITELY = 16;
/*     */   private static final int JVMTI_THREAD_STATE_WAITING_WITH_TIMEOUT = 32;
/*     */   
/*     */   public static int getPeakFinalRefCount() {
/* 353 */     return peakFinalRefCount;
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
/*     */   public static void addFinalRefCount(int paramInt) {
/* 365 */     finalRefCount += paramInt;
/* 366 */     if (finalRefCount > peakFinalRefCount) {
/* 367 */       peakFinalRefCount = finalRefCount;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Thread.State toThreadState(int paramInt) {
/* 375 */     if ((paramInt & 0x4) != 0)
/* 376 */       return Thread.State.RUNNABLE; 
/* 377 */     if ((paramInt & 0x400) != 0)
/* 378 */       return Thread.State.BLOCKED; 
/* 379 */     if ((paramInt & 0x10) != 0)
/* 380 */       return Thread.State.WAITING; 
/* 381 */     if ((paramInt & 0x20) != 0)
/* 382 */       return Thread.State.TIMED_WAITING; 
/* 383 */     if ((paramInt & 0x2) != 0)
/* 384 */       return Thread.State.TERMINATED; 
/* 385 */     if ((paramInt & 0x1) == 0) {
/* 386 */       return Thread.State.NEW;
/*     */     }
/* 388 */     return Thread.State.RUNNABLE;
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
/*     */   public static native ClassLoader latestUserDefinedLoader0();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ClassLoader latestUserDefinedLoader() {
/* 411 */     ClassLoader classLoader = latestUserDefinedLoader0();
/* 412 */     if (classLoader != null) {
/* 413 */       return classLoader;
/*     */     }
/*     */     try {
/* 416 */       return Launcher.ExtClassLoader.getExtClassLoader();
/* 417 */     } catch (IOException iOException) {
/* 418 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   static {
/* 423 */     initialize();
/*     */   }
/*     */   
/*     */   private static native void initialize();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/VM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */