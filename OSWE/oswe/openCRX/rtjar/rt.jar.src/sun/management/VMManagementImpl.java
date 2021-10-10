/*     */ package sun.management;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import sun.management.counter.Counter;
/*     */ import sun.management.counter.perf.PerfInstrumentation;
/*     */ import sun.misc.Perf;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class VMManagementImpl
/*     */   implements VMManagement
/*     */ {
/*     */   private static boolean compTimeMonitoringSupport;
/*     */   private static boolean threadContentionMonitoringSupport;
/*     */   private static boolean currentThreadCpuTimeSupport;
/*     */   private static boolean otherThreadCpuTimeSupport;
/*     */   private static boolean bootClassPathSupport;
/*  64 */   private static String version = getVersion0(); static {
/*  65 */     if (version == null) {
/*  66 */       throw new AssertionError("Invalid Management Version");
/*     */     }
/*  68 */     initOptionalSupportFields();
/*     */   }
/*     */   
/*     */   private static boolean objectMonitorUsageSupport;
/*     */   private static boolean synchronizerUsageSupport;
/*     */   
/*     */   public boolean isCompilationTimeMonitoringSupported() {
/*  75 */     return compTimeMonitoringSupport;
/*     */   }
/*     */   private static boolean threadAllocatedMemorySupport; private static boolean gcNotificationSupport; private static boolean remoteDiagnosticCommandsSupport;
/*     */   public boolean isThreadContentionMonitoringSupported() {
/*  79 */     return threadContentionMonitoringSupport;
/*     */   }
/*     */   
/*     */   public boolean isCurrentThreadCpuTimeSupported() {
/*  83 */     return currentThreadCpuTimeSupport;
/*     */   }
/*     */   
/*     */   public boolean isOtherThreadCpuTimeSupported() {
/*  87 */     return otherThreadCpuTimeSupport;
/*     */   }
/*     */   
/*     */   public boolean isBootClassPathSupported() {
/*  91 */     return bootClassPathSupport;
/*     */   }
/*     */   
/*     */   public boolean isObjectMonitorUsageSupported() {
/*  95 */     return objectMonitorUsageSupport;
/*     */   }
/*     */   
/*     */   public boolean isSynchronizerUsageSupported() {
/*  99 */     return synchronizerUsageSupport;
/*     */   }
/*     */   
/*     */   public boolean isThreadAllocatedMemorySupported() {
/* 103 */     return threadAllocatedMemorySupport;
/*     */   }
/*     */   
/*     */   public boolean isGcNotificationSupported() {
/* 107 */     return gcNotificationSupport;
/*     */   }
/*     */   
/*     */   public boolean isRemoteDiagnosticCommandsSupported() {
/* 111 */     return remoteDiagnosticCommandsSupport;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLoadedClassCount() {
/* 120 */     long l = getTotalClassCount() - getUnloadedClassCount();
/* 121 */     return (int)l;
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
/*     */   public String getManagementVersion() {
/* 133 */     return version;
/*     */   }
/*     */   
/*     */   public String getVmId() {
/* 137 */     int i = getProcessId();
/* 138 */     String str = "localhost";
/*     */     try {
/* 140 */       str = InetAddress.getLocalHost().getHostName();
/* 141 */     } catch (UnknownHostException unknownHostException) {}
/*     */ 
/*     */ 
/*     */     
/* 145 */     return i + "@" + str;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getVmName() {
/* 150 */     return System.getProperty("java.vm.name");
/*     */   }
/*     */   
/*     */   public String getVmVendor() {
/* 154 */     return System.getProperty("java.vm.vendor");
/*     */   }
/*     */   public String getVmVersion() {
/* 157 */     return System.getProperty("java.vm.version");
/*     */   }
/*     */   public String getVmSpecName() {
/* 160 */     return System.getProperty("java.vm.specification.name");
/*     */   }
/*     */   public String getVmSpecVendor() {
/* 163 */     return System.getProperty("java.vm.specification.vendor");
/*     */   }
/*     */   public String getVmSpecVersion() {
/* 166 */     return System.getProperty("java.vm.specification.version");
/*     */   }
/*     */   public String getClassPath() {
/* 169 */     return System.getProperty("java.class.path");
/*     */   }
/*     */   public String getLibraryPath() {
/* 172 */     return System.getProperty("java.library.path");
/*     */   }
/*     */   
/*     */   public String getBootClassPath() {
/* 176 */     GetPropertyAction getPropertyAction = new GetPropertyAction("sun.boot.class.path");
/*     */     
/* 178 */     return AccessController.<String>doPrivileged(getPropertyAction);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getUptime() {
/* 183 */     return getUptime0();
/*     */   }
/*     */   
/* 186 */   private List<String> vmArgs = null;
/*     */   public synchronized List<String> getVmArguments() {
/* 188 */     if (this.vmArgs == null) {
/* 189 */       String[] arrayOfString = getVmArguments0();
/*     */       
/* 191 */       List<T> list = (arrayOfString != null && arrayOfString.length != 0) ? Arrays.<T>asList((T[])arrayOfString) : Collections.<T>emptyList();
/* 192 */       this.vmArgs = Collections.unmodifiableList((List)list);
/*     */     } 
/* 194 */     return this.vmArgs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCompilerName() {
/* 204 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run() {
/* 207 */             return System.getProperty("sun.management.compiler");
/*     */           }
/*     */         });
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
/*     */   public String getOsName() {
/* 222 */     return System.getProperty("os.name");
/*     */   }
/*     */   public String getOsArch() {
/* 225 */     return System.getProperty("os.arch");
/*     */   }
/*     */   public String getOsVersion() {
/* 228 */     return System.getProperty("os.version");
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
/* 246 */   private PerfInstrumentation perfInstr = null;
/*     */   private boolean noPerfData = false;
/*     */   
/*     */   private synchronized PerfInstrumentation getPerfInstrumentation() {
/* 250 */     if (this.noPerfData || this.perfInstr != null) {
/* 251 */       return this.perfInstr;
/*     */     }
/*     */ 
/*     */     
/* 255 */     Perf perf = AccessController.<Perf>doPrivileged((PrivilegedAction<Perf>)new Perf.GetPerfAction());
/*     */     try {
/* 257 */       ByteBuffer byteBuffer = perf.attach(0, "r");
/* 258 */       if (byteBuffer.capacity() == 0) {
/* 259 */         this.noPerfData = true;
/* 260 */         return null;
/*     */       } 
/* 262 */       this.perfInstr = new PerfInstrumentation(byteBuffer);
/* 263 */     } catch (IllegalArgumentException illegalArgumentException) {
/*     */ 
/*     */       
/* 266 */       this.noPerfData = true;
/* 267 */     } catch (IOException iOException) {
/* 268 */       throw new AssertionError(iOException);
/*     */     } 
/* 270 */     return this.perfInstr;
/*     */   }
/*     */   
/*     */   public List<Counter> getInternalCounters(String paramString) {
/* 274 */     PerfInstrumentation perfInstrumentation = getPerfInstrumentation();
/* 275 */     if (perfInstrumentation != null) {
/* 276 */       return perfInstrumentation.findByPattern(paramString);
/*     */     }
/* 278 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   private static native String getVersion0();
/*     */   
/*     */   private static native void initOptionalSupportFields();
/*     */   
/*     */   public native boolean isThreadContentionMonitoringEnabled();
/*     */   
/*     */   public native boolean isThreadCpuTimeEnabled();
/*     */   
/*     */   public native boolean isThreadAllocatedMemoryEnabled();
/*     */   
/*     */   public native long getTotalClassCount();
/*     */   
/*     */   public native long getUnloadedClassCount();
/*     */   
/*     */   public native boolean getVerboseClass();
/*     */   
/*     */   public native boolean getVerboseGC();
/*     */   
/*     */   private native int getProcessId();
/*     */   
/*     */   public native String[] getVmArguments0();
/*     */   
/*     */   public native long getStartupTime();
/*     */   
/*     */   private native long getUptime0();
/*     */   
/*     */   public native int getAvailableProcessors();
/*     */   
/*     */   public native long getTotalCompileTime();
/*     */   
/*     */   public native long getTotalThreadCount();
/*     */   
/*     */   public native int getLiveThreadCount();
/*     */   
/*     */   public native int getPeakThreadCount();
/*     */   
/*     */   public native int getDaemonThreadCount();
/*     */   
/*     */   public native long getSafepointCount();
/*     */   
/*     */   public native long getTotalSafepointTime();
/*     */   
/*     */   public native long getSafepointSyncTime();
/*     */   
/*     */   public native long getTotalApplicationNonStoppedTime();
/*     */   
/*     */   public native long getLoadedClassSize();
/*     */   
/*     */   public native long getUnloadedClassSize();
/*     */   
/*     */   public native long getClassLoadingTime();
/*     */   
/*     */   public native long getMethodDataSize();
/*     */   
/*     */   public native long getInitializedClassCount();
/*     */   
/*     */   public native long getClassInitializationTime();
/*     */   
/*     */   public native long getClassVerificationTime();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/VMManagementImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */