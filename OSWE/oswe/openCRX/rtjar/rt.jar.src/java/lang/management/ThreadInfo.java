/*     */ package java.lang.management;
/*     */ 
/*     */ import java.lang.management.LockInfo;
/*     */ import java.lang.management.MonitorInfo;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import sun.management.ManagementFactoryHelper;
/*     */ import sun.management.ThreadInfoCompositeData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadInfo
/*     */ {
/*     */   private String threadName;
/*     */   private long threadId;
/*     */   private long blockedTime;
/*     */   private long blockedCount;
/*     */   private long waitedTime;
/*     */   private long waitedCount;
/*     */   private LockInfo lock;
/*     */   private String lockName;
/*     */   private long lockOwnerId;
/*     */   private String lockOwnerName;
/*     */   private boolean inNative;
/*     */   private boolean suspended;
/*     */   private Thread.State threadState;
/*     */   private StackTraceElement[] stackTrace;
/*     */   private MonitorInfo[] lockedMonitors;
/*     */   private LockInfo[] lockedSynchronizers;
/* 110 */   private static MonitorInfo[] EMPTY_MONITORS = new MonitorInfo[0];
/* 111 */   private static LockInfo[] EMPTY_SYNCS = new LockInfo[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_FRAMES = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ThreadInfo(Thread paramThread1, int paramInt, Object paramObject, Thread paramThread2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, StackTraceElement[] paramArrayOfStackTraceElement) {
/* 130 */     initialize(paramThread1, paramInt, paramObject, paramThread2, paramLong1, paramLong2, paramLong3, paramLong4, paramArrayOfStackTraceElement, EMPTY_MONITORS, EMPTY_SYNCS);
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
/*     */   private ThreadInfo(Thread paramThread1, int paramInt, Object paramObject, Thread paramThread2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, StackTraceElement[] paramArrayOfStackTraceElement, Object[] paramArrayOfObject1, int[] paramArrayOfint, Object[] paramArrayOfObject2) {
/*     */     MonitorInfo[] arrayOfMonitorInfo;
/*     */     Object object;
/* 161 */     byte b1 = (paramArrayOfObject1 == null) ? 0 : paramArrayOfObject1.length;
/*     */     
/* 163 */     if (!b1) {
/* 164 */       arrayOfMonitorInfo = EMPTY_MONITORS;
/*     */     } else {
/* 166 */       arrayOfMonitorInfo = new MonitorInfo[b1];
/* 167 */       for (byte b = 0; b < b1; b++) {
/* 168 */         object = paramArrayOfObject1[b];
/* 169 */         String str = object.getClass().getName();
/* 170 */         int i = System.identityHashCode(object);
/* 171 */         int j = paramArrayOfint[b];
/* 172 */         StackTraceElement stackTraceElement = (j >= 0) ? paramArrayOfStackTraceElement[j] : null;
/*     */         
/* 174 */         arrayOfMonitorInfo[b] = new MonitorInfo(str, i, j, stackTraceElement);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     byte b2 = (paramArrayOfObject2 == null) ? 0 : paramArrayOfObject2.length;
/*     */     
/* 183 */     if (!b2) {
/* 184 */       object = EMPTY_SYNCS;
/*     */     } else {
/* 186 */       object = new LockInfo[b2];
/* 187 */       for (byte b = 0; b < b2; b++) {
/* 188 */         Object object1 = paramArrayOfObject2[b];
/* 189 */         String str = object1.getClass().getName();
/* 190 */         int i = System.identityHashCode(object1);
/* 191 */         object[b] = new LockInfo(str, i);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 196 */     initialize(paramThread1, paramInt, paramObject, paramThread2, paramLong1, paramLong2, paramLong3, paramLong4, paramArrayOfStackTraceElement, arrayOfMonitorInfo, (LockInfo[])object);
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
/*     */   private void initialize(Thread paramThread1, int paramInt, Object paramObject, Thread paramThread2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, StackTraceElement[] paramArrayOfStackTraceElement, MonitorInfo[] paramArrayOfMonitorInfo, LockInfo[] paramArrayOfLockInfo) {
/* 223 */     this.threadId = paramThread1.getId();
/* 224 */     this.threadName = paramThread1.getName();
/* 225 */     this.threadState = ManagementFactoryHelper.toThreadState(paramInt);
/* 226 */     this.suspended = ManagementFactoryHelper.isThreadSuspended(paramInt);
/* 227 */     this.inNative = ManagementFactoryHelper.isThreadRunningNative(paramInt);
/* 228 */     this.blockedCount = paramLong1;
/* 229 */     this.blockedTime = paramLong2;
/* 230 */     this.waitedCount = paramLong3;
/* 231 */     this.waitedTime = paramLong4;
/*     */     
/* 233 */     if (paramObject == null) {
/* 234 */       this.lock = null;
/* 235 */       this.lockName = null;
/*     */     } else {
/* 237 */       this.lock = new LockInfo(paramObject);
/* 238 */       this
/*     */         
/* 240 */         .lockName = this.lock.getClassName() + '@' + Integer.toHexString(this.lock.getIdentityHashCode());
/*     */     } 
/* 242 */     if (paramThread2 == null) {
/* 243 */       this.lockOwnerId = -1L;
/* 244 */       this.lockOwnerName = null;
/*     */     } else {
/* 246 */       this.lockOwnerId = paramThread2.getId();
/* 247 */       this.lockOwnerName = paramThread2.getName();
/*     */     } 
/* 249 */     if (paramArrayOfStackTraceElement == null) {
/* 250 */       this.stackTrace = NO_STACK_TRACE;
/*     */     } else {
/* 252 */       this.stackTrace = paramArrayOfStackTraceElement;
/*     */     } 
/* 254 */     this.lockedMonitors = paramArrayOfMonitorInfo;
/* 255 */     this.lockedSynchronizers = paramArrayOfLockInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ThreadInfo(CompositeData paramCompositeData) {
/* 263 */     ThreadInfoCompositeData threadInfoCompositeData = ThreadInfoCompositeData.getInstance(paramCompositeData);
/*     */     
/* 265 */     this.threadId = threadInfoCompositeData.threadId();
/* 266 */     this.threadName = threadInfoCompositeData.threadName();
/* 267 */     this.blockedTime = threadInfoCompositeData.blockedTime();
/* 268 */     this.blockedCount = threadInfoCompositeData.blockedCount();
/* 269 */     this.waitedTime = threadInfoCompositeData.waitedTime();
/* 270 */     this.waitedCount = threadInfoCompositeData.waitedCount();
/* 271 */     this.lockName = threadInfoCompositeData.lockName();
/* 272 */     this.lockOwnerId = threadInfoCompositeData.lockOwnerId();
/* 273 */     this.lockOwnerName = threadInfoCompositeData.lockOwnerName();
/* 274 */     this.threadState = threadInfoCompositeData.threadState();
/* 275 */     this.suspended = threadInfoCompositeData.suspended();
/* 276 */     this.inNative = threadInfoCompositeData.inNative();
/* 277 */     this.stackTrace = threadInfoCompositeData.stackTrace();
/*     */ 
/*     */     
/* 280 */     if (threadInfoCompositeData.isCurrentVersion()) {
/* 281 */       this.lock = threadInfoCompositeData.lockInfo();
/* 282 */       this.lockedMonitors = threadInfoCompositeData.lockedMonitors();
/* 283 */       this.lockedSynchronizers = threadInfoCompositeData.lockedSynchronizers();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 288 */       if (this.lockName != null) {
/* 289 */         String[] arrayOfString = this.lockName.split("@");
/* 290 */         if (arrayOfString.length == 2) {
/* 291 */           int i = Integer.parseInt(arrayOfString[1], 16);
/* 292 */           this.lock = new LockInfo(arrayOfString[0], i);
/*     */         } else {
/* 294 */           assert arrayOfString.length == 2;
/* 295 */           this.lock = null;
/*     */         } 
/*     */       } else {
/* 298 */         this.lock = null;
/*     */       } 
/* 300 */       this.lockedMonitors = EMPTY_MONITORS;
/* 301 */       this.lockedSynchronizers = EMPTY_SYNCS;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getThreadId() {
/* 311 */     return this.threadId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getThreadName() {
/* 320 */     return this.threadName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Thread.State getThreadState() {
/* 329 */     return this.threadState;
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
/*     */   public long getBlockedTime() {
/* 358 */     return this.blockedTime;
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
/*     */   public long getBlockedCount() {
/* 372 */     return this.blockedCount;
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
/*     */   public long getWaitedTime() {
/* 403 */     return this.waitedTime;
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
/*     */   public long getWaitedCount() {
/* 418 */     return this.waitedCount;
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
/*     */   public LockInfo getLockInfo() {
/* 459 */     return this.lock;
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
/*     */   public String getLockName() {
/* 482 */     return this.lockName;
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
/*     */   public long getLockOwnerId() {
/* 500 */     return this.lockOwnerId;
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
/*     */   public String getLockOwnerName() {
/* 518 */     return this.lockOwnerName;
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
/*     */   public StackTraceElement[] getStackTrace() {
/* 541 */     return this.stackTrace;
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
/*     */   public boolean isSuspended() {
/* 553 */     return this.suspended;
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
/*     */   public boolean isInNative() {
/* 567 */     return this.inNative;
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
/*     */   public String toString() {
/* 584 */     StringBuilder stringBuilder = new StringBuilder("\"" + getThreadName() + "\" Id=" + getThreadId() + " " + getThreadState());
/* 585 */     if (getLockName() != null) {
/* 586 */       stringBuilder.append(" on " + getLockName());
/*     */     }
/* 588 */     if (getLockOwnerName() != null) {
/* 589 */       stringBuilder.append(" owned by \"" + getLockOwnerName() + "\" Id=" + 
/* 590 */           getLockOwnerId());
/*     */     }
/* 592 */     if (isSuspended()) {
/* 593 */       stringBuilder.append(" (suspended)");
/*     */     }
/* 595 */     if (isInNative()) {
/* 596 */       stringBuilder.append(" (in native)");
/*     */     }
/* 598 */     stringBuilder.append('\n');
/* 599 */     byte b = 0;
/* 600 */     for (; b < this.stackTrace.length && b < 8; b++) {
/* 601 */       StackTraceElement stackTraceElement = this.stackTrace[b];
/* 602 */       stringBuilder.append("\tat " + stackTraceElement.toString());
/* 603 */       stringBuilder.append('\n');
/* 604 */       if (b == 0 && getLockInfo() != null) {
/* 605 */         Thread.State state = getThreadState();
/* 606 */         switch (state) {
/*     */           case BLOCKED:
/* 608 */             stringBuilder.append("\t-  blocked on " + getLockInfo());
/* 609 */             stringBuilder.append('\n');
/*     */             break;
/*     */           case WAITING:
/* 612 */             stringBuilder.append("\t-  waiting on " + getLockInfo());
/* 613 */             stringBuilder.append('\n');
/*     */             break;
/*     */           case TIMED_WAITING:
/* 616 */             stringBuilder.append("\t-  waiting on " + getLockInfo());
/* 617 */             stringBuilder.append('\n');
/*     */             break;
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 623 */       for (MonitorInfo monitorInfo : this.lockedMonitors) {
/* 624 */         if (monitorInfo.getLockedStackDepth() == b) {
/* 625 */           stringBuilder.append("\t-  locked " + monitorInfo);
/* 626 */           stringBuilder.append('\n');
/*     */         } 
/*     */       } 
/*     */     } 
/* 630 */     if (b < this.stackTrace.length) {
/* 631 */       stringBuilder.append("\t...");
/* 632 */       stringBuilder.append('\n');
/*     */     } 
/*     */     
/* 635 */     LockInfo[] arrayOfLockInfo = getLockedSynchronizers();
/* 636 */     if (arrayOfLockInfo.length > 0) {
/* 637 */       stringBuilder.append("\n\tNumber of locked synchronizers = " + arrayOfLockInfo.length);
/* 638 */       stringBuilder.append('\n');
/* 639 */       for (LockInfo lockInfo : arrayOfLockInfo) {
/* 640 */         stringBuilder.append("\t- " + lockInfo);
/* 641 */         stringBuilder.append('\n');
/*     */       } 
/*     */     } 
/* 644 */     stringBuilder.append('\n');
/* 645 */     return stringBuilder.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ThreadInfo from(CompositeData paramCompositeData) {
/* 787 */     if (paramCompositeData == null) {
/* 788 */       return null;
/*     */     }
/*     */     
/* 791 */     if (paramCompositeData instanceof ThreadInfoCompositeData) {
/* 792 */       return ((ThreadInfoCompositeData)paramCompositeData).getThreadInfo();
/*     */     }
/* 794 */     return new ThreadInfo(paramCompositeData);
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
/*     */   public MonitorInfo[] getLockedMonitors() {
/* 812 */     return this.lockedMonitors;
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
/*     */   public LockInfo[] getLockedSynchronizers() {
/* 829 */     return this.lockedSynchronizers;
/*     */   }
/*     */   
/* 832 */   private static final StackTraceElement[] NO_STACK_TRACE = new StackTraceElement[0];
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/management/ThreadInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */