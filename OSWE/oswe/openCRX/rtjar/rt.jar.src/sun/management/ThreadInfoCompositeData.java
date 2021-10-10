/*     */ package sun.management;
/*     */ 
/*     */ import java.lang.management.LockInfo;
/*     */ import java.lang.management.MonitorInfo;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import javax.management.openmbean.CompositeDataSupport;
/*     */ import javax.management.openmbean.CompositeType;
/*     */ import javax.management.openmbean.OpenDataException;
/*     */ import javax.management.openmbean.OpenType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadInfoCompositeData
/*     */   extends LazyCompositeData
/*     */ {
/*     */   private final ThreadInfo threadInfo;
/*     */   private final CompositeData cdata;
/*     */   private final boolean currentVersion;
/*     */   private static final String THREAD_ID = "threadId";
/*     */   private static final String THREAD_NAME = "threadName";
/*     */   private static final String THREAD_STATE = "threadState";
/*     */   private static final String BLOCKED_TIME = "blockedTime";
/*     */   private static final String BLOCKED_COUNT = "blockedCount";
/*     */   private static final String WAITED_TIME = "waitedTime";
/*     */   private static final String WAITED_COUNT = "waitedCount";
/*     */   private static final String LOCK_INFO = "lockInfo";
/*     */   private static final String LOCK_NAME = "lockName";
/*     */   private static final String LOCK_OWNER_ID = "lockOwnerId";
/*     */   private static final String LOCK_OWNER_NAME = "lockOwnerName";
/*     */   private static final String STACK_TRACE = "stackTrace";
/*     */   private static final String SUSPENDED = "suspended";
/*     */   private static final String IN_NATIVE = "inNative";
/*     */   private static final String LOCKED_MONITORS = "lockedMonitors";
/*     */   private static final String LOCKED_SYNCS = "lockedSynchronizers";
/*     */   
/*     */   private ThreadInfoCompositeData(ThreadInfo paramThreadInfo) {
/*  48 */     this.threadInfo = paramThreadInfo;
/*  49 */     this.currentVersion = true;
/*  50 */     this.cdata = null;
/*     */   }
/*     */   
/*     */   private ThreadInfoCompositeData(CompositeData paramCompositeData) {
/*  54 */     this.threadInfo = null;
/*  55 */     this.currentVersion = isCurrentVersion(paramCompositeData);
/*  56 */     this.cdata = paramCompositeData;
/*     */   }
/*     */   
/*     */   public ThreadInfo getThreadInfo() {
/*  60 */     return this.threadInfo;
/*     */   }
/*     */   
/*     */   public boolean isCurrentVersion() {
/*  64 */     return this.currentVersion;
/*     */   }
/*     */   
/*     */   public static ThreadInfoCompositeData getInstance(CompositeData paramCompositeData) {
/*  68 */     validateCompositeData(paramCompositeData);
/*  69 */     return new ThreadInfoCompositeData(paramCompositeData);
/*     */   }
/*     */   
/*     */   public static CompositeData toCompositeData(ThreadInfo paramThreadInfo) {
/*  73 */     ThreadInfoCompositeData threadInfoCompositeData = new ThreadInfoCompositeData(paramThreadInfo);
/*  74 */     return threadInfoCompositeData.getCompositeData();
/*     */   }
/*     */ 
/*     */   
/*     */   protected CompositeData getCompositeData() {
/*  79 */     StackTraceElement[] arrayOfStackTraceElement = this.threadInfo.getStackTrace();
/*  80 */     CompositeData[] arrayOfCompositeData1 = new CompositeData[arrayOfStackTraceElement.length];
/*     */     
/*  82 */     for (byte b1 = 0; b1 < arrayOfStackTraceElement.length; b1++) {
/*  83 */       StackTraceElement stackTraceElement = arrayOfStackTraceElement[b1];
/*  84 */       arrayOfCompositeData1[b1] = StackTraceElementCompositeData.toCompositeData(stackTraceElement);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  89 */     CompositeData compositeData = LockInfoCompositeData.toCompositeData(this.threadInfo.getLockInfo());
/*     */ 
/*     */     
/*  92 */     LockInfo[] arrayOfLockInfo = this.threadInfo.getLockedSynchronizers();
/*  93 */     CompositeData[] arrayOfCompositeData2 = new CompositeData[arrayOfLockInfo.length];
/*     */     
/*  95 */     for (byte b2 = 0; b2 < arrayOfLockInfo.length; b2++) {
/*  96 */       LockInfo lockInfo = arrayOfLockInfo[b2];
/*  97 */       arrayOfCompositeData2[b2] = LockInfoCompositeData.toCompositeData(lockInfo);
/*     */     } 
/*     */     
/* 100 */     MonitorInfo[] arrayOfMonitorInfo = this.threadInfo.getLockedMonitors();
/* 101 */     CompositeData[] arrayOfCompositeData3 = new CompositeData[arrayOfMonitorInfo.length];
/*     */     
/* 103 */     for (byte b3 = 0; b3 < arrayOfMonitorInfo.length; b3++) {
/* 104 */       MonitorInfo monitorInfo = arrayOfMonitorInfo[b3];
/* 105 */       arrayOfCompositeData3[b3] = MonitorInfoCompositeData.toCompositeData(monitorInfo);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     Object[] arrayOfObject = { new Long(this.threadInfo.getThreadId()), this.threadInfo.getThreadName(), this.threadInfo.getThreadState().name(), new Long(this.threadInfo.getBlockedTime()), new Long(this.threadInfo.getBlockedCount()), new Long(this.threadInfo.getWaitedTime()), new Long(this.threadInfo.getWaitedCount()), compositeData, this.threadInfo.getLockName(), new Long(this.threadInfo.getLockOwnerId()), this.threadInfo.getLockOwnerName(), arrayOfCompositeData1, new Boolean(this.threadInfo.isSuspended()), new Boolean(this.threadInfo.isInNative()), arrayOfCompositeData3, arrayOfCompositeData2 };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 130 */       return new CompositeDataSupport(threadInfoCompositeType, threadInfoItemNames, arrayOfObject);
/*     */     
/*     */     }
/* 133 */     catch (OpenDataException openDataException) {
/*     */       
/* 135 */       throw new AssertionError(openDataException);
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
/* 157 */   private static final String[] threadInfoItemNames = new String[] { "threadId", "threadName", "threadState", "blockedTime", "blockedCount", "waitedTime", "waitedCount", "lockInfo", "lockName", "lockOwnerId", "lockOwnerName", "stackTrace", "suspended", "inNative", "lockedMonitors", "lockedSynchronizers" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 177 */   private static final String[] threadInfoV6Attributes = new String[] { "lockInfo", "lockedMonitors", "lockedSynchronizers" };
/*     */ 
/*     */   
/*     */   private static final CompositeType threadInfoCompositeType;
/*     */   
/*     */   private static final CompositeType threadInfoV5CompositeType;
/*     */   
/*     */   private static final CompositeType lockInfoCompositeType;
/*     */   
/*     */   private static final long serialVersionUID = 2464378539119753175L;
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 191 */       threadInfoCompositeType = (CompositeType)MappedMXBeanType.toOpenType(ThreadInfo.class);
/*     */ 
/*     */       
/* 194 */       String[] arrayOfString1 = threadInfoCompositeType.keySet().<String>toArray(new String[0]);
/* 195 */       int i = threadInfoItemNames.length - threadInfoV6Attributes.length;
/*     */       
/* 197 */       String[] arrayOfString2 = new String[i];
/* 198 */       String[] arrayOfString3 = new String[i];
/* 199 */       OpenType[] arrayOfOpenType = new OpenType[i];
/* 200 */       byte b = 0;
/* 201 */       for (String str : arrayOfString1) {
/* 202 */         if (isV5Attribute(str)) {
/* 203 */           arrayOfString2[b] = str;
/* 204 */           arrayOfString3[b] = threadInfoCompositeType.getDescription(str);
/* 205 */           arrayOfOpenType[b] = threadInfoCompositeType.getType(str);
/* 206 */           b++;
/*     */         } 
/*     */       } 
/*     */       
/* 210 */       threadInfoV5CompositeType = new CompositeType("java.lang.management.ThreadInfo", "J2SE 5.0 java.lang.management.ThreadInfo", arrayOfString2, arrayOfString3, (OpenType<?>[])arrayOfOpenType);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 216 */     catch (OpenDataException openDataException) {
/*     */       
/* 218 */       throw new AssertionError(openDataException);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 226 */     Object object = new Object();
/*     */     
/* 228 */     LockInfo lockInfo = new LockInfo(object.getClass().getName(), System.identityHashCode(object));
/* 229 */     CompositeData compositeData = LockInfoCompositeData.toCompositeData(lockInfo);
/* 230 */     lockInfoCompositeType = compositeData.getCompositeType();
/*     */   }
/*     */   
/*     */   private static boolean isV5Attribute(String paramString) {
/* 234 */     for (String str : threadInfoV6Attributes) {
/* 235 */       if (paramString.equals(str)) {
/* 236 */         return false;
/*     */       }
/*     */     } 
/* 239 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isCurrentVersion(CompositeData paramCompositeData) {
/* 243 */     if (paramCompositeData == null) {
/* 244 */       throw new NullPointerException("Null CompositeData");
/*     */     }
/*     */     
/* 247 */     return isTypeMatched(threadInfoCompositeType, paramCompositeData.getCompositeType());
/*     */   }
/*     */   
/*     */   public long threadId() {
/* 251 */     return getLong(this.cdata, "threadId");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String threadName() {
/* 257 */     String str = getString(this.cdata, "threadName");
/* 258 */     if (str == null) {
/* 259 */       throw new IllegalArgumentException("Invalid composite data: Attribute threadName has null value");
/*     */     }
/*     */     
/* 262 */     return str;
/*     */   }
/*     */   
/*     */   public Thread.State threadState() {
/* 266 */     return Thread.State.valueOf(getString(this.cdata, "threadState"));
/*     */   }
/*     */   
/*     */   public long blockedTime() {
/* 270 */     return getLong(this.cdata, "blockedTime");
/*     */   }
/*     */   
/*     */   public long blockedCount() {
/* 274 */     return getLong(this.cdata, "blockedCount");
/*     */   }
/*     */   
/*     */   public long waitedTime() {
/* 278 */     return getLong(this.cdata, "waitedTime");
/*     */   }
/*     */   
/*     */   public long waitedCount() {
/* 282 */     return getLong(this.cdata, "waitedCount");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String lockName() {
/* 288 */     return getString(this.cdata, "lockName");
/*     */   }
/*     */   
/*     */   public long lockOwnerId() {
/* 292 */     return getLong(this.cdata, "lockOwnerId");
/*     */   }
/*     */   
/*     */   public String lockOwnerName() {
/* 296 */     return getString(this.cdata, "lockOwnerName");
/*     */   }
/*     */   
/*     */   public boolean suspended() {
/* 300 */     return getBoolean(this.cdata, "suspended");
/*     */   }
/*     */   
/*     */   public boolean inNative() {
/* 304 */     return getBoolean(this.cdata, "inNative");
/*     */   }
/*     */ 
/*     */   
/*     */   public StackTraceElement[] stackTrace() {
/* 309 */     CompositeData[] arrayOfCompositeData = (CompositeData[])this.cdata.get("stackTrace");
/*     */ 
/*     */ 
/*     */     
/* 313 */     StackTraceElement[] arrayOfStackTraceElement = new StackTraceElement[arrayOfCompositeData.length];
/*     */     
/* 315 */     for (byte b = 0; b < arrayOfCompositeData.length; b++) {
/* 316 */       CompositeData compositeData = arrayOfCompositeData[b];
/* 317 */       arrayOfStackTraceElement[b] = StackTraceElementCompositeData.from(compositeData);
/*     */     } 
/* 319 */     return arrayOfStackTraceElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public LockInfo lockInfo() {
/* 324 */     CompositeData compositeData = (CompositeData)this.cdata.get("lockInfo");
/* 325 */     return LockInfo.from(compositeData);
/*     */   }
/*     */ 
/*     */   
/*     */   public MonitorInfo[] lockedMonitors() {
/* 330 */     CompositeData[] arrayOfCompositeData = (CompositeData[])this.cdata.get("lockedMonitors");
/*     */ 
/*     */ 
/*     */     
/* 334 */     MonitorInfo[] arrayOfMonitorInfo = new MonitorInfo[arrayOfCompositeData.length];
/*     */     
/* 336 */     for (byte b = 0; b < arrayOfCompositeData.length; b++) {
/* 337 */       CompositeData compositeData = arrayOfCompositeData[b];
/* 338 */       arrayOfMonitorInfo[b] = MonitorInfo.from(compositeData);
/*     */     } 
/* 340 */     return arrayOfMonitorInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public LockInfo[] lockedSynchronizers() {
/* 345 */     CompositeData[] arrayOfCompositeData = (CompositeData[])this.cdata.get("lockedSynchronizers");
/*     */ 
/*     */ 
/*     */     
/* 349 */     LockInfo[] arrayOfLockInfo = new LockInfo[arrayOfCompositeData.length];
/* 350 */     for (byte b = 0; b < arrayOfCompositeData.length; b++) {
/* 351 */       CompositeData compositeData = arrayOfCompositeData[b];
/* 352 */       arrayOfLockInfo[b] = LockInfo.from(compositeData);
/*     */     } 
/* 354 */     return arrayOfLockInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validateCompositeData(CompositeData paramCompositeData) {
/* 362 */     if (paramCompositeData == null) {
/* 363 */       throw new NullPointerException("Null CompositeData");
/*     */     }
/*     */     
/* 366 */     CompositeType compositeType = paramCompositeData.getCompositeType();
/* 367 */     boolean bool = true;
/* 368 */     if (!isTypeMatched(threadInfoCompositeType, compositeType)) {
/* 369 */       bool = false;
/*     */       
/* 371 */       if (!isTypeMatched(threadInfoV5CompositeType, compositeType)) {
/* 372 */         throw new IllegalArgumentException("Unexpected composite type for ThreadInfo");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 378 */     CompositeData[] arrayOfCompositeData = (CompositeData[])paramCompositeData.get("stackTrace");
/* 379 */     if (arrayOfCompositeData == null) {
/* 380 */       throw new IllegalArgumentException("StackTraceElement[] is missing");
/*     */     }
/*     */     
/* 383 */     if (arrayOfCompositeData.length > 0) {
/* 384 */       StackTraceElementCompositeData.validateCompositeData(arrayOfCompositeData[0]);
/*     */     }
/*     */ 
/*     */     
/* 388 */     if (bool) {
/* 389 */       CompositeData compositeData = (CompositeData)paramCompositeData.get("lockInfo");
/* 390 */       if (compositeData != null && 
/* 391 */         !isTypeMatched(lockInfoCompositeType, compositeData
/* 392 */           .getCompositeType())) {
/* 393 */         throw new IllegalArgumentException("Unexpected composite type for \"lockInfo\" attribute.");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 399 */       CompositeData[] arrayOfCompositeData1 = (CompositeData[])paramCompositeData.get("lockedMonitors");
/* 400 */       if (arrayOfCompositeData1 == null) {
/* 401 */         throw new IllegalArgumentException("MonitorInfo[] is null");
/*     */       }
/* 403 */       if (arrayOfCompositeData1.length > 0) {
/* 404 */         MonitorInfoCompositeData.validateCompositeData(arrayOfCompositeData1[0]);
/*     */       }
/*     */       
/* 407 */       CompositeData[] arrayOfCompositeData2 = (CompositeData[])paramCompositeData.get("lockedSynchronizers");
/* 408 */       if (arrayOfCompositeData2 == null) {
/* 409 */         throw new IllegalArgumentException("LockInfo[] is null");
/*     */       }
/* 411 */       if (arrayOfCompositeData2.length > 0 && 
/* 412 */         !isTypeMatched(lockInfoCompositeType, arrayOfCompositeData2[0]
/* 413 */           .getCompositeType()))
/* 414 */         throw new IllegalArgumentException("Unexpected composite type for \"lockedSynchronizers\" attribute."); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/ThreadInfoCompositeData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */