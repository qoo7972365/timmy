/*     */ package sun.management;
/*     */ 
/*     */ import com.sun.management.ThreadMXBean;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import java.util.Arrays;
/*     */ import javax.management.ObjectName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ThreadImpl
/*     */   implements ThreadMXBean
/*     */ {
/*     */   private final VMManagement jvm;
/*     */   private boolean contentionMonitoringEnabled = false;
/*     */   private boolean cpuTimeEnabled;
/*     */   private boolean allocatedMemoryEnabled;
/*     */   
/*     */   ThreadImpl(VMManagement paramVMManagement) {
/*  54 */     this.jvm = paramVMManagement;
/*  55 */     this.cpuTimeEnabled = this.jvm.isThreadCpuTimeEnabled();
/*  56 */     this.allocatedMemoryEnabled = this.jvm.isThreadAllocatedMemoryEnabled();
/*     */   }
/*     */   
/*     */   public int getThreadCount() {
/*  60 */     return this.jvm.getLiveThreadCount();
/*     */   }
/*     */   
/*     */   public int getPeakThreadCount() {
/*  64 */     return this.jvm.getPeakThreadCount();
/*     */   }
/*     */   
/*     */   public long getTotalStartedThreadCount() {
/*  68 */     return this.jvm.getTotalThreadCount();
/*     */   }
/*     */   
/*     */   public int getDaemonThreadCount() {
/*  72 */     return this.jvm.getDaemonThreadCount();
/*     */   }
/*     */   
/*     */   public boolean isThreadContentionMonitoringSupported() {
/*  76 */     return this.jvm.isThreadContentionMonitoringSupported();
/*     */   }
/*     */   
/*     */   public synchronized boolean isThreadContentionMonitoringEnabled() {
/*  80 */     if (!isThreadContentionMonitoringSupported()) {
/*  81 */       throw new UnsupportedOperationException("Thread contention monitoring is not supported.");
/*     */     }
/*     */     
/*  84 */     return this.contentionMonitoringEnabled;
/*     */   }
/*     */   
/*     */   public boolean isThreadCpuTimeSupported() {
/*  88 */     return this.jvm.isOtherThreadCpuTimeSupported();
/*     */   }
/*     */   
/*     */   public boolean isCurrentThreadCpuTimeSupported() {
/*  92 */     return this.jvm.isCurrentThreadCpuTimeSupported();
/*     */   }
/*     */   
/*     */   public boolean isThreadAllocatedMemorySupported() {
/*  96 */     return this.jvm.isThreadAllocatedMemorySupported();
/*     */   }
/*     */   
/*     */   public boolean isThreadCpuTimeEnabled() {
/* 100 */     if (!isThreadCpuTimeSupported() && 
/* 101 */       !isCurrentThreadCpuTimeSupported()) {
/* 102 */       throw new UnsupportedOperationException("Thread CPU time measurement is not supported");
/*     */     }
/*     */     
/* 105 */     return this.cpuTimeEnabled;
/*     */   }
/*     */   
/*     */   public boolean isThreadAllocatedMemoryEnabled() {
/* 109 */     if (!isThreadAllocatedMemorySupported()) {
/* 110 */       throw new UnsupportedOperationException("Thread allocated memory measurement is not supported");
/*     */     }
/*     */     
/* 113 */     return this.allocatedMemoryEnabled;
/*     */   }
/*     */   
/*     */   public long[] getAllThreadIds() {
/* 117 */     Util.checkMonitorAccess();
/*     */     
/* 119 */     Thread[] arrayOfThread = getThreads();
/* 120 */     int i = arrayOfThread.length;
/* 121 */     long[] arrayOfLong = new long[i];
/* 122 */     for (byte b = 0; b < i; b++) {
/* 123 */       Thread thread = arrayOfThread[b];
/* 124 */       arrayOfLong[b] = thread.getId();
/*     */     } 
/* 126 */     return arrayOfLong;
/*     */   }
/*     */   
/*     */   public ThreadInfo getThreadInfo(long paramLong) {
/* 130 */     long[] arrayOfLong = new long[1];
/* 131 */     arrayOfLong[0] = paramLong;
/* 132 */     ThreadInfo[] arrayOfThreadInfo = getThreadInfo(arrayOfLong, 0);
/* 133 */     return arrayOfThreadInfo[0];
/*     */   }
/*     */   
/*     */   public ThreadInfo getThreadInfo(long paramLong, int paramInt) {
/* 137 */     long[] arrayOfLong = new long[1];
/* 138 */     arrayOfLong[0] = paramLong;
/* 139 */     ThreadInfo[] arrayOfThreadInfo = getThreadInfo(arrayOfLong, paramInt);
/* 140 */     return arrayOfThreadInfo[0];
/*     */   }
/*     */   
/*     */   public ThreadInfo[] getThreadInfo(long[] paramArrayOflong) {
/* 144 */     return getThreadInfo(paramArrayOflong, 0);
/*     */   }
/*     */   
/*     */   private void verifyThreadIds(long[] paramArrayOflong) {
/* 148 */     if (paramArrayOflong == null) {
/* 149 */       throw new NullPointerException("Null ids parameter.");
/*     */     }
/*     */     
/* 152 */     for (byte b = 0; b < paramArrayOflong.length; b++) {
/* 153 */       if (paramArrayOflong[b] <= 0L) {
/* 154 */         throw new IllegalArgumentException("Invalid thread ID parameter: " + paramArrayOflong[b]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ThreadInfo[] getThreadInfo(long[] paramArrayOflong, int paramInt) {
/* 161 */     verifyThreadIds(paramArrayOflong);
/*     */     
/* 163 */     if (paramInt < 0) {
/* 164 */       throw new IllegalArgumentException("Invalid maxDepth parameter: " + paramInt);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     if (paramArrayOflong.length == 0) return new ThreadInfo[0];
/*     */     
/* 172 */     Util.checkMonitorAccess();
/*     */     
/* 174 */     ThreadInfo[] arrayOfThreadInfo = new ThreadInfo[paramArrayOflong.length];
/* 175 */     if (paramInt == Integer.MAX_VALUE) {
/* 176 */       getThreadInfo1(paramArrayOflong, -1, arrayOfThreadInfo);
/*     */     } else {
/* 178 */       getThreadInfo1(paramArrayOflong, paramInt, arrayOfThreadInfo);
/*     */     } 
/* 180 */     return arrayOfThreadInfo;
/*     */   }
/*     */   
/*     */   public void setThreadContentionMonitoringEnabled(boolean paramBoolean) {
/* 184 */     if (!isThreadContentionMonitoringSupported()) {
/* 185 */       throw new UnsupportedOperationException("Thread contention monitoring is not supported");
/*     */     }
/*     */ 
/*     */     
/* 189 */     Util.checkControlAccess();
/*     */     
/* 191 */     synchronized (this) {
/* 192 */       if (this.contentionMonitoringEnabled != paramBoolean) {
/* 193 */         if (paramBoolean)
/*     */         {
/*     */           
/* 196 */           resetContentionTimes0(0L);
/*     */         }
/*     */ 
/*     */         
/* 200 */         setThreadContentionMonitoringEnabled0(paramBoolean);
/*     */         
/* 202 */         this.contentionMonitoringEnabled = paramBoolean;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean verifyCurrentThreadCpuTime() {
/* 209 */     if (!isCurrentThreadCpuTimeSupported()) {
/* 210 */       throw new UnsupportedOperationException("Current thread CPU time measurement is not supported.");
/*     */     }
/*     */     
/* 213 */     return isThreadCpuTimeEnabled();
/*     */   }
/*     */   
/*     */   public long getCurrentThreadCpuTime() {
/* 217 */     if (verifyCurrentThreadCpuTime()) {
/* 218 */       return getThreadTotalCpuTime0(0L);
/*     */     }
/* 220 */     return -1L;
/*     */   }
/*     */   
/*     */   public long getThreadCpuTime(long paramLong) {
/* 224 */     long[] arrayOfLong1 = new long[1];
/* 225 */     arrayOfLong1[0] = paramLong;
/* 226 */     long[] arrayOfLong2 = getThreadCpuTime(arrayOfLong1);
/* 227 */     return arrayOfLong2[0];
/*     */   }
/*     */   
/*     */   private boolean verifyThreadCpuTime(long[] paramArrayOflong) {
/* 231 */     verifyThreadIds(paramArrayOflong);
/*     */ 
/*     */     
/* 234 */     if (!isThreadCpuTimeSupported() && 
/* 235 */       !isCurrentThreadCpuTimeSupported()) {
/* 236 */       throw new UnsupportedOperationException("Thread CPU time measurement is not supported.");
/*     */     }
/*     */ 
/*     */     
/* 240 */     if (!isThreadCpuTimeSupported())
/*     */     {
/* 242 */       for (byte b = 0; b < paramArrayOflong.length; b++) {
/* 243 */         if (paramArrayOflong[b] != Thread.currentThread().getId()) {
/* 244 */           throw new UnsupportedOperationException("Thread CPU time measurement is only supported for the current thread.");
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 251 */     return isThreadCpuTimeEnabled();
/*     */   }
/*     */   
/*     */   public long[] getThreadCpuTime(long[] paramArrayOflong) {
/* 255 */     boolean bool = verifyThreadCpuTime(paramArrayOflong);
/*     */     
/* 257 */     int i = paramArrayOflong.length;
/* 258 */     long[] arrayOfLong = new long[i];
/* 259 */     Arrays.fill(arrayOfLong, -1L);
/*     */     
/* 261 */     if (bool) {
/* 262 */       if (i == 1) {
/* 263 */         long l = paramArrayOflong[0];
/* 264 */         if (l == Thread.currentThread().getId()) {
/* 265 */           l = 0L;
/*     */         }
/* 267 */         arrayOfLong[0] = getThreadTotalCpuTime0(l);
/*     */       } else {
/* 269 */         getThreadTotalCpuTime1(paramArrayOflong, arrayOfLong);
/*     */       } 
/*     */     }
/* 272 */     return arrayOfLong;
/*     */   }
/*     */   
/*     */   public long getCurrentThreadUserTime() {
/* 276 */     if (verifyCurrentThreadCpuTime()) {
/* 277 */       return getThreadUserCpuTime0(0L);
/*     */     }
/* 279 */     return -1L;
/*     */   }
/*     */   
/*     */   public long getThreadUserTime(long paramLong) {
/* 283 */     long[] arrayOfLong1 = new long[1];
/* 284 */     arrayOfLong1[0] = paramLong;
/* 285 */     long[] arrayOfLong2 = getThreadUserTime(arrayOfLong1);
/* 286 */     return arrayOfLong2[0];
/*     */   }
/*     */   
/*     */   public long[] getThreadUserTime(long[] paramArrayOflong) {
/* 290 */     boolean bool = verifyThreadCpuTime(paramArrayOflong);
/*     */     
/* 292 */     int i = paramArrayOflong.length;
/* 293 */     long[] arrayOfLong = new long[i];
/* 294 */     Arrays.fill(arrayOfLong, -1L);
/*     */     
/* 296 */     if (bool) {
/* 297 */       if (i == 1) {
/* 298 */         long l = paramArrayOflong[0];
/* 299 */         if (l == Thread.currentThread().getId()) {
/* 300 */           l = 0L;
/*     */         }
/* 302 */         arrayOfLong[0] = getThreadUserCpuTime0(l);
/*     */       } else {
/* 304 */         getThreadUserCpuTime1(paramArrayOflong, arrayOfLong);
/*     */       } 
/*     */     }
/* 307 */     return arrayOfLong;
/*     */   }
/*     */   
/*     */   public void setThreadCpuTimeEnabled(boolean paramBoolean) {
/* 311 */     if (!isThreadCpuTimeSupported() && 
/* 312 */       !isCurrentThreadCpuTimeSupported()) {
/* 313 */       throw new UnsupportedOperationException("Thread CPU time measurement is not supported");
/*     */     }
/*     */ 
/*     */     
/* 317 */     Util.checkControlAccess();
/* 318 */     synchronized (this) {
/* 319 */       if (this.cpuTimeEnabled != paramBoolean) {
/*     */         
/* 321 */         setThreadCpuTimeEnabled0(paramBoolean);
/* 322 */         this.cpuTimeEnabled = paramBoolean;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getThreadAllocatedBytes(long paramLong) {
/* 328 */     long[] arrayOfLong1 = new long[1];
/* 329 */     arrayOfLong1[0] = paramLong;
/* 330 */     long[] arrayOfLong2 = getThreadAllocatedBytes(arrayOfLong1);
/* 331 */     return arrayOfLong2[0];
/*     */   }
/*     */   
/*     */   private boolean verifyThreadAllocatedMemory(long[] paramArrayOflong) {
/* 335 */     verifyThreadIds(paramArrayOflong);
/*     */ 
/*     */     
/* 338 */     if (!isThreadAllocatedMemorySupported()) {
/* 339 */       throw new UnsupportedOperationException("Thread allocated memory measurement is not supported.");
/*     */     }
/*     */ 
/*     */     
/* 343 */     return isThreadAllocatedMemoryEnabled();
/*     */   }
/*     */   
/*     */   public long[] getThreadAllocatedBytes(long[] paramArrayOflong) {
/* 347 */     boolean bool = verifyThreadAllocatedMemory(paramArrayOflong);
/*     */     
/* 349 */     long[] arrayOfLong = new long[paramArrayOflong.length];
/* 350 */     Arrays.fill(arrayOfLong, -1L);
/*     */     
/* 352 */     if (bool) {
/* 353 */       getThreadAllocatedMemory1(paramArrayOflong, arrayOfLong);
/*     */     }
/* 355 */     return arrayOfLong;
/*     */   }
/*     */   
/*     */   public void setThreadAllocatedMemoryEnabled(boolean paramBoolean) {
/* 359 */     if (!isThreadAllocatedMemorySupported()) {
/* 360 */       throw new UnsupportedOperationException("Thread allocated memory measurement is not supported.");
/*     */     }
/*     */ 
/*     */     
/* 364 */     Util.checkControlAccess();
/* 365 */     synchronized (this) {
/* 366 */       if (this.allocatedMemoryEnabled != paramBoolean) {
/*     */         
/* 368 */         setThreadAllocatedMemoryEnabled0(paramBoolean);
/* 369 */         this.allocatedMemoryEnabled = paramBoolean;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public long[] findMonitorDeadlockedThreads() {
/* 375 */     Util.checkMonitorAccess();
/*     */     
/* 377 */     Thread[] arrayOfThread = findMonitorDeadlockedThreads0();
/* 378 */     if (arrayOfThread == null) {
/* 379 */       return null;
/*     */     }
/*     */     
/* 382 */     long[] arrayOfLong = new long[arrayOfThread.length];
/* 383 */     for (byte b = 0; b < arrayOfThread.length; b++) {
/* 384 */       Thread thread = arrayOfThread[b];
/* 385 */       arrayOfLong[b] = thread.getId();
/*     */     } 
/* 387 */     return arrayOfLong;
/*     */   }
/*     */   
/*     */   public long[] findDeadlockedThreads() {
/* 391 */     if (!isSynchronizerUsageSupported()) {
/* 392 */       throw new UnsupportedOperationException("Monitoring of Synchronizer Usage is not supported.");
/*     */     }
/*     */ 
/*     */     
/* 396 */     Util.checkMonitorAccess();
/*     */     
/* 398 */     Thread[] arrayOfThread = findDeadlockedThreads0();
/* 399 */     if (arrayOfThread == null) {
/* 400 */       return null;
/*     */     }
/*     */     
/* 403 */     long[] arrayOfLong = new long[arrayOfThread.length];
/* 404 */     for (byte b = 0; b < arrayOfThread.length; b++) {
/* 405 */       Thread thread = arrayOfThread[b];
/* 406 */       arrayOfLong[b] = thread.getId();
/*     */     } 
/* 408 */     return arrayOfLong;
/*     */   }
/*     */   
/*     */   public void resetPeakThreadCount() {
/* 412 */     Util.checkControlAccess();
/* 413 */     resetPeakThreadCount0();
/*     */   }
/*     */   
/*     */   public boolean isObjectMonitorUsageSupported() {
/* 417 */     return this.jvm.isObjectMonitorUsageSupported();
/*     */   }
/*     */   
/*     */   public boolean isSynchronizerUsageSupported() {
/* 421 */     return this.jvm.isSynchronizerUsageSupported();
/*     */   }
/*     */ 
/*     */   
/*     */   private void verifyDumpThreads(boolean paramBoolean1, boolean paramBoolean2) {
/* 426 */     if (paramBoolean1 && !isObjectMonitorUsageSupported()) {
/* 427 */       throw new UnsupportedOperationException("Monitoring of Object Monitor Usage is not supported.");
/*     */     }
/*     */ 
/*     */     
/* 431 */     if (paramBoolean2 && !isSynchronizerUsageSupported()) {
/* 432 */       throw new UnsupportedOperationException("Monitoring of Synchronizer Usage is not supported.");
/*     */     }
/*     */ 
/*     */     
/* 436 */     Util.checkMonitorAccess();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ThreadInfo[] getThreadInfo(long[] paramArrayOflong, boolean paramBoolean1, boolean paramBoolean2) {
/* 442 */     verifyThreadIds(paramArrayOflong);
/*     */ 
/*     */     
/* 445 */     if (paramArrayOflong.length == 0) return new ThreadInfo[0];
/*     */     
/* 447 */     verifyDumpThreads(paramBoolean1, paramBoolean2);
/* 448 */     return dumpThreads0(paramArrayOflong, paramBoolean1, paramBoolean2);
/*     */   }
/*     */ 
/*     */   
/*     */   public ThreadInfo[] dumpAllThreads(boolean paramBoolean1, boolean paramBoolean2) {
/* 453 */     verifyDumpThreads(paramBoolean1, paramBoolean2);
/* 454 */     return dumpThreads0(null, paramBoolean1, paramBoolean2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native Thread[] getThreads();
/*     */ 
/*     */   
/*     */   private static native void getThreadInfo1(long[] paramArrayOflong, int paramInt, ThreadInfo[] paramArrayOfThreadInfo);
/*     */ 
/*     */   
/*     */   private static native long getThreadTotalCpuTime0(long paramLong);
/*     */ 
/*     */   
/*     */   private static native void getThreadTotalCpuTime1(long[] paramArrayOflong1, long[] paramArrayOflong2);
/*     */ 
/*     */   
/*     */   private static native long getThreadUserCpuTime0(long paramLong);
/*     */ 
/*     */   
/*     */   private static native void getThreadUserCpuTime1(long[] paramArrayOflong1, long[] paramArrayOflong2);
/*     */ 
/*     */   
/*     */   private static native void getThreadAllocatedMemory1(long[] paramArrayOflong1, long[] paramArrayOflong2);
/*     */ 
/*     */   
/*     */   public ObjectName getObjectName() {
/* 481 */     return Util.newObjectName("java.lang:type=Threading");
/*     */   }
/*     */   
/*     */   private static native void setThreadCpuTimeEnabled0(boolean paramBoolean);
/*     */   
/*     */   private static native void setThreadAllocatedMemoryEnabled0(boolean paramBoolean);
/*     */   
/*     */   private static native void setThreadContentionMonitoringEnabled0(boolean paramBoolean);
/*     */   
/*     */   private static native Thread[] findMonitorDeadlockedThreads0();
/*     */   
/*     */   private static native Thread[] findDeadlockedThreads0();
/*     */   
/*     */   private static native void resetPeakThreadCount0();
/*     */   
/*     */   private static native ThreadInfo[] dumpThreads0(long[] paramArrayOflong, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   private static native void resetContentionTimes0(long paramLong);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/ThreadImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */