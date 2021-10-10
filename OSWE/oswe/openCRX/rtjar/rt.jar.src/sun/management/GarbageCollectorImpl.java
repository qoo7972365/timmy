/*     */ package sun.management;
/*     */ 
/*     */ import com.sun.management.GarbageCollectionNotificationInfo;
/*     */ import com.sun.management.GarbageCollectorMXBean;
/*     */ import com.sun.management.GcInfo;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.MemoryPoolMXBean;
/*     */ import java.util.List;
/*     */ import javax.management.ListenerNotFoundException;
/*     */ import javax.management.MBeanNotificationInfo;
/*     */ import javax.management.Notification;
/*     */ import javax.management.NotificationFilter;
/*     */ import javax.management.NotificationListener;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.openmbean.CompositeData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class GarbageCollectorImpl
/*     */   extends MemoryManagerImpl
/*     */   implements GarbageCollectorMXBean
/*     */ {
/*     */   private String[] poolNames;
/*     */   private GcInfoBuilder gcInfoBuilder;
/*     */   private static final String notifName = "javax.management.Notification";
/*     */   
/*     */   GarbageCollectorImpl(String paramString) {
/*  60 */     super(paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     this.poolNames = null;
/*     */   } synchronized String[] getAllPoolNames() {
/*  72 */     if (this.poolNames == null) {
/*  73 */       List<MemoryPoolMXBean> list = ManagementFactory.getMemoryPoolMXBeans();
/*  74 */       this.poolNames = new String[list.size()];
/*  75 */       byte b = 0;
/*  76 */       for (MemoryPoolMXBean memoryPoolMXBean : list) {
/*  77 */         this.poolNames[b++] = memoryPoolMXBean.getName();
/*     */       }
/*     */     } 
/*  80 */     return this.poolNames;
/*     */   }
/*     */   public native long getCollectionCount();
/*     */   
/*     */   public native long getCollectionTime();
/*     */   
/*     */   private synchronized GcInfoBuilder getGcInfoBuilder() {
/*  87 */     if (this.gcInfoBuilder == null) {
/*  88 */       this.gcInfoBuilder = new GcInfoBuilder(this, getAllPoolNames());
/*     */     }
/*  90 */     return this.gcInfoBuilder;
/*     */   }
/*     */   
/*     */   public GcInfo getLastGcInfo() {
/*  94 */     return getGcInfoBuilder().getLastGcInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   private static final String[] gcNotifTypes = new String[] { "com.sun.management.gc.notification" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MBeanNotificationInfo[] getNotificationInfo() {
/* 107 */     return new MBeanNotificationInfo[] { new MBeanNotificationInfo(gcNotifTypes, "javax.management.Notification", "GC Notification") };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   private static long seqNumber = 0L;
/*     */   private static long getNextSeqNumber() {
/* 116 */     return ++seqNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void createGCNotification(long paramLong, String paramString1, String paramString2, String paramString3, GcInfo paramGcInfo) {
/* 125 */     if (!hasListeners()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 131 */     Notification notification = new Notification("com.sun.management.gc.notification", getObjectName(), getNextSeqNumber(), paramLong, paramString1);
/*     */ 
/*     */     
/* 134 */     GarbageCollectionNotificationInfo garbageCollectionNotificationInfo = new GarbageCollectionNotificationInfo(paramString1, paramString2, paramString3, paramGcInfo);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     CompositeData compositeData = GarbageCollectionNotifInfoCompositeData.toCompositeData(garbageCollectionNotificationInfo);
/* 142 */     notification.setUserData(compositeData);
/* 143 */     sendNotification(notification);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) {
/* 150 */     boolean bool1 = hasListeners();
/* 151 */     super.addNotificationListener(paramNotificationListener, paramNotificationFilter, paramObject);
/* 152 */     boolean bool2 = hasListeners();
/* 153 */     if (!bool1 && bool2) {
/* 154 */       setNotificationEnabled(this, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void removeNotificationListener(NotificationListener paramNotificationListener) throws ListenerNotFoundException {
/* 160 */     boolean bool1 = hasListeners();
/* 161 */     super.removeNotificationListener(paramNotificationListener);
/* 162 */     boolean bool2 = hasListeners();
/* 163 */     if (bool1 && !bool2) {
/* 164 */       setNotificationEnabled(this, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws ListenerNotFoundException {
/* 173 */     boolean bool1 = hasListeners();
/* 174 */     super.removeNotificationListener(paramNotificationListener, paramNotificationFilter, paramObject);
/* 175 */     boolean bool2 = hasListeners();
/* 176 */     if (bool1 && !bool2) {
/* 177 */       setNotificationEnabled(this, false);
/*     */     }
/*     */   }
/*     */   
/*     */   public ObjectName getObjectName() {
/* 182 */     return Util.newObjectName("java.lang:type=GarbageCollector", getName());
/*     */   }
/*     */   
/*     */   native void setNotificationEnabled(GarbageCollectorMXBean paramGarbageCollectorMXBean, boolean paramBoolean);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/GarbageCollectorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */