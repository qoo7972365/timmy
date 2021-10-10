/*     */ package sun.management;
/*     */ 
/*     */ import java.lang.management.MemoryManagerMXBean;
/*     */ import java.lang.management.MemoryPoolMXBean;
/*     */ import java.lang.management.MemoryType;
/*     */ import java.lang.management.MemoryUsage;
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
/*     */ 
/*     */ 
/*     */ class MemoryPoolImpl
/*     */   implements MemoryPoolMXBean
/*     */ {
/*     */   private final String name;
/*     */   private final boolean isHeap;
/*     */   private final boolean isValid;
/*     */   private final boolean collectionThresholdSupported;
/*     */   private final boolean usageThresholdSupported;
/*     */   private MemoryManagerMXBean[] managers;
/*     */   private long usageThreshold;
/*     */   private long collectionThreshold;
/*     */   private boolean usageSensorRegistered;
/*     */   private boolean gcSensorRegistered;
/*     */   private Sensor usageSensor;
/*     */   private Sensor gcSensor;
/*     */   
/*     */   MemoryPoolImpl(String paramString, boolean paramBoolean, long paramLong1, long paramLong2) {
/*  65 */     this.name = paramString;
/*  66 */     this.isHeap = paramBoolean;
/*  67 */     this.isValid = true;
/*  68 */     this.managers = null;
/*  69 */     this.usageThreshold = paramLong1;
/*  70 */     this.collectionThreshold = paramLong2;
/*  71 */     this.usageThresholdSupported = (paramLong1 >= 0L);
/*  72 */     this.collectionThresholdSupported = (paramLong2 >= 0L);
/*  73 */     this.usageSensor = new PoolSensor(this, paramString + " usage sensor");
/*  74 */     this.gcSensor = new CollectionSensor(this, paramString + " collection sensor");
/*  75 */     this.usageSensorRegistered = false;
/*  76 */     this.gcSensorRegistered = false;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  80 */     return this.name;
/*     */   }
/*     */   
/*     */   public boolean isValid() {
/*  84 */     return this.isValid;
/*     */   }
/*     */   
/*     */   public MemoryType getType() {
/*  88 */     if (this.isHeap) {
/*  89 */       return MemoryType.HEAP;
/*     */     }
/*  91 */     return MemoryType.NON_HEAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public MemoryUsage getUsage() {
/*  96 */     return getUsage0();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized MemoryUsage getPeakUsage() {
/* 101 */     return getPeakUsage0();
/*     */   }
/*     */   
/*     */   public synchronized long getUsageThreshold() {
/* 105 */     if (!isUsageThresholdSupported()) {
/* 106 */       throw new UnsupportedOperationException("Usage threshold is not supported");
/*     */     }
/*     */     
/* 109 */     return this.usageThreshold;
/*     */   }
/*     */   
/*     */   public void setUsageThreshold(long paramLong) {
/* 113 */     if (!isUsageThresholdSupported()) {
/* 114 */       throw new UnsupportedOperationException("Usage threshold is not supported");
/*     */     }
/*     */ 
/*     */     
/* 118 */     Util.checkControlAccess();
/*     */     
/* 120 */     MemoryUsage memoryUsage = getUsage0();
/* 121 */     if (paramLong < 0L) {
/* 122 */       throw new IllegalArgumentException("Invalid threshold: " + paramLong);
/*     */     }
/*     */ 
/*     */     
/* 126 */     if (memoryUsage.getMax() != -1L && paramLong > memoryUsage.getMax()) {
/* 127 */       throw new IllegalArgumentException("Invalid threshold: " + paramLong + " must be <= maxSize. Committed = " + memoryUsage
/*     */ 
/*     */           
/* 130 */           .getCommitted() + " Max = " + memoryUsage
/* 131 */           .getMax());
/*     */     }
/*     */     
/* 134 */     synchronized (this) {
/* 135 */       if (!this.usageSensorRegistered) {
/*     */         
/* 137 */         this.usageSensorRegistered = true;
/* 138 */         setPoolUsageSensor(this.usageSensor);
/*     */       } 
/* 140 */       setUsageThreshold0(this.usageThreshold, paramLong);
/* 141 */       this.usageThreshold = paramLong;
/*     */     } 
/*     */   }
/*     */   
/*     */   private synchronized MemoryManagerMXBean[] getMemoryManagers() {
/* 146 */     if (this.managers == null) {
/* 147 */       this.managers = getMemoryManagers0();
/*     */     }
/* 149 */     return this.managers;
/*     */   }
/*     */   
/*     */   public String[] getMemoryManagerNames() {
/* 153 */     MemoryManagerMXBean[] arrayOfMemoryManagerMXBean = getMemoryManagers();
/*     */     
/* 155 */     String[] arrayOfString = new String[arrayOfMemoryManagerMXBean.length];
/* 156 */     for (byte b = 0; b < arrayOfMemoryManagerMXBean.length; b++) {
/* 157 */       arrayOfString[b] = arrayOfMemoryManagerMXBean[b].getName();
/*     */     }
/* 159 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   public void resetPeakUsage() {
/* 163 */     Util.checkControlAccess();
/*     */     
/* 165 */     synchronized (this) {
/*     */       
/* 167 */       resetPeakUsage0();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isUsageThresholdExceeded() {
/* 172 */     if (!isUsageThresholdSupported()) {
/* 173 */       throw new UnsupportedOperationException("Usage threshold is not supported");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 178 */     if (this.usageThreshold == 0L) {
/* 179 */       return false;
/*     */     }
/*     */     
/* 182 */     MemoryUsage memoryUsage = getUsage0();
/* 183 */     return (memoryUsage.getUsed() >= this.usageThreshold || this.usageSensor
/* 184 */       .isOn());
/*     */   }
/*     */   
/*     */   public long getUsageThresholdCount() {
/* 188 */     if (!isUsageThresholdSupported()) {
/* 189 */       throw new UnsupportedOperationException("Usage threshold is not supported");
/*     */     }
/*     */ 
/*     */     
/* 193 */     return this.usageSensor.getCount();
/*     */   }
/*     */   
/*     */   public boolean isUsageThresholdSupported() {
/* 197 */     return this.usageThresholdSupported;
/*     */   }
/*     */   
/*     */   public synchronized long getCollectionUsageThreshold() {
/* 201 */     if (!isCollectionUsageThresholdSupported()) {
/* 202 */       throw new UnsupportedOperationException("CollectionUsage threshold is not supported");
/*     */     }
/*     */ 
/*     */     
/* 206 */     return this.collectionThreshold;
/*     */   }
/*     */   
/*     */   public void setCollectionUsageThreshold(long paramLong) {
/* 210 */     if (!isCollectionUsageThresholdSupported()) {
/* 211 */       throw new UnsupportedOperationException("CollectionUsage threshold is not supported");
/*     */     }
/*     */ 
/*     */     
/* 215 */     Util.checkControlAccess();
/*     */     
/* 217 */     MemoryUsage memoryUsage = getUsage0();
/* 218 */     if (paramLong < 0L) {
/* 219 */       throw new IllegalArgumentException("Invalid threshold: " + paramLong);
/*     */     }
/*     */ 
/*     */     
/* 223 */     if (memoryUsage.getMax() != -1L && paramLong > memoryUsage.getMax()) {
/* 224 */       throw new IllegalArgumentException("Invalid threshold: " + paramLong + " > max (" + memoryUsage
/*     */           
/* 226 */           .getMax() + ").");
/*     */     }
/*     */     
/* 229 */     synchronized (this) {
/* 230 */       if (!this.gcSensorRegistered) {
/*     */         
/* 232 */         this.gcSensorRegistered = true;
/* 233 */         setPoolCollectionSensor(this.gcSensor);
/*     */       } 
/* 235 */       setCollectionThreshold0(this.collectionThreshold, paramLong);
/* 236 */       this.collectionThreshold = paramLong;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isCollectionUsageThresholdExceeded() {
/* 241 */     if (!isCollectionUsageThresholdSupported()) {
/* 242 */       throw new UnsupportedOperationException("CollectionUsage threshold is not supported");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 247 */     if (this.collectionThreshold == 0L) {
/* 248 */       return false;
/*     */     }
/*     */     
/* 251 */     MemoryUsage memoryUsage = getCollectionUsage0();
/* 252 */     return (this.gcSensor.isOn() || (memoryUsage != null && memoryUsage
/* 253 */       .getUsed() >= this.collectionThreshold));
/*     */   }
/*     */   
/*     */   public long getCollectionUsageThresholdCount() {
/* 257 */     if (!isCollectionUsageThresholdSupported()) {
/* 258 */       throw new UnsupportedOperationException("CollectionUsage threshold is not supported");
/*     */     }
/*     */ 
/*     */     
/* 262 */     return this.gcSensor.getCount();
/*     */   }
/*     */   
/*     */   public MemoryUsage getCollectionUsage() {
/* 266 */     return getCollectionUsage0();
/*     */   }
/*     */   
/*     */   public boolean isCollectionUsageThresholdSupported() {
/* 270 */     return this.collectionThresholdSupported;
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
/*     */   class PoolSensor
/*     */     extends Sensor
/*     */   {
/*     */     MemoryPoolImpl pool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     PoolSensor(MemoryPoolImpl param1MemoryPoolImpl1, String param1String) {
/* 296 */       super(param1String);
/* 297 */       this.pool = param1MemoryPoolImpl1;
/*     */     }
/*     */     
/*     */     void triggerAction(MemoryUsage param1MemoryUsage) {
/* 301 */       MemoryImpl.createNotification("java.management.memory.threshold.exceeded", this.pool
/* 302 */           .getName(), param1MemoryUsage, 
/*     */           
/* 304 */           getCount());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void triggerAction() {}
/*     */ 
/*     */ 
/*     */     
/*     */     void clearAction() {}
/*     */   }
/*     */ 
/*     */   
/*     */   class CollectionSensor
/*     */     extends Sensor
/*     */   {
/*     */     MemoryPoolImpl pool;
/*     */ 
/*     */     
/*     */     CollectionSensor(MemoryPoolImpl param1MemoryPoolImpl1, String param1String) {
/* 324 */       super(param1String);
/* 325 */       this.pool = param1MemoryPoolImpl1;
/*     */     }
/*     */     void triggerAction(MemoryUsage param1MemoryUsage) {
/* 328 */       MemoryImpl.createNotification("java.management.memory.collection.threshold.exceeded", this.pool
/* 329 */           .getName(), param1MemoryUsage, MemoryPoolImpl.this
/*     */           
/* 331 */           .gcSensor.getCount());
/*     */     }
/*     */ 
/*     */     
/*     */     void triggerAction() {}
/*     */ 
/*     */     
/*     */     void clearAction() {}
/*     */   }
/*     */   
/*     */   public ObjectName getObjectName() {
/* 342 */     return Util.newObjectName("java.lang:type=MemoryPool", getName());
/*     */   }
/*     */   
/*     */   private native MemoryUsage getUsage0();
/*     */   
/*     */   private native MemoryUsage getPeakUsage0();
/*     */   
/*     */   private native MemoryUsage getCollectionUsage0();
/*     */   
/*     */   private native void setUsageThreshold0(long paramLong1, long paramLong2);
/*     */   
/*     */   private native void setCollectionThreshold0(long paramLong1, long paramLong2);
/*     */   
/*     */   private native void resetPeakUsage0();
/*     */   
/*     */   private native MemoryManagerMXBean[] getMemoryManagers0();
/*     */   
/*     */   private native void setPoolUsageSensor(Sensor paramSensor);
/*     */   
/*     */   private native void setPoolCollectionSensor(Sensor paramSensor);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/MemoryPoolImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */