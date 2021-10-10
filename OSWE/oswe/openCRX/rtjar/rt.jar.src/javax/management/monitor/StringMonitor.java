/*     */ package javax.management.monitor;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import java.util.logging.Level;
/*     */ import javax.management.MBeanNotificationInfo;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringMonitor
/*     */   extends Monitor
/*     */   implements StringMonitorMBean
/*     */ {
/*     */   static class StringMonitorObservedObject
/*     */     extends Monitor.ObservedObject
/*     */   {
/*     */     private int status;
/*     */     
/*     */     public StringMonitorObservedObject(ObjectName param1ObjectName) {
/*  70 */       super(param1ObjectName);
/*     */     }
/*     */     
/*     */     public final synchronized int getStatus() {
/*  74 */       return this.status;
/*     */     }
/*     */     public final synchronized void setStatus(int param1Int) {
/*  77 */       this.status = param1Int;
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
/*  93 */   private String stringToCompare = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean notifyMatch = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean notifyDiffer = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private static final String[] types = new String[] { "jmx.monitor.error.runtime", "jmx.monitor.error.mbean", "jmx.monitor.error.attribute", "jmx.monitor.error.type", "jmx.monitor.string.matches", "jmx.monitor.string.differs" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   private static final MBeanNotificationInfo[] notifsInfo = new MBeanNotificationInfo[] { new MBeanNotificationInfo(types, "javax.management.monitor.MonitorNotification", "Notifications sent by the StringMonitor MBean") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MATCHING = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int DIFFERING = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MATCHING_OR_DIFFERING = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void start() {
/* 153 */     if (isActive()) {
/* 154 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINER, StringMonitor.class.getName(), "start", "the monitor is already active");
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 160 */     for (Monitor.ObservedObject observedObject : this.observedObjects) {
/* 161 */       StringMonitorObservedObject stringMonitorObservedObject = (StringMonitorObservedObject)observedObject;
/*     */       
/* 163 */       stringMonitorObservedObject.setStatus(2);
/*     */     } 
/* 165 */     doStart();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void stop() {
/* 172 */     doStop();
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
/*     */   public synchronized String getDerivedGauge(ObjectName paramObjectName) {
/* 189 */     return (String)super.getDerivedGauge(paramObjectName);
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
/*     */   public synchronized long getDerivedGaugeTimeStamp(ObjectName paramObjectName) {
/* 205 */     return super.getDerivedGaugeTimeStamp(paramObjectName);
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
/*     */   public synchronized String getDerivedGauge() {
/* 219 */     if (this.observedObjects.isEmpty()) {
/* 220 */       return null;
/*     */     }
/* 222 */     return (String)((Monitor.ObservedObject)this.observedObjects.get(0)).getDerivedGauge();
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
/*     */   @Deprecated
/*     */   public synchronized long getDerivedGaugeTimeStamp() {
/* 237 */     if (this.observedObjects.isEmpty()) {
/* 238 */       return 0L;
/*     */     }
/* 240 */     return ((Monitor.ObservedObject)this.observedObjects.get(0)).getDerivedGaugeTimeStamp();
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
/*     */   public synchronized String getStringToCompare() {
/* 253 */     return this.stringToCompare;
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
/*     */   public synchronized void setStringToCompare(String paramString) throws IllegalArgumentException {
/* 270 */     if (paramString == null) {
/* 271 */       throw new IllegalArgumentException("Null string to compare");
/*     */     }
/*     */     
/* 274 */     if (this.stringToCompare.equals(paramString))
/*     */       return; 
/* 276 */     this.stringToCompare = paramString;
/*     */ 
/*     */ 
/*     */     
/* 280 */     for (Monitor.ObservedObject observedObject : this.observedObjects) {
/* 281 */       StringMonitorObservedObject stringMonitorObservedObject = (StringMonitorObservedObject)observedObject;
/*     */       
/* 283 */       stringMonitorObservedObject.setStatus(2);
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
/*     */   public synchronized boolean getNotifyMatch() {
/* 297 */     return this.notifyMatch;
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
/*     */   public synchronized void setNotifyMatch(boolean paramBoolean) {
/* 309 */     if (this.notifyMatch == paramBoolean)
/*     */       return; 
/* 311 */     this.notifyMatch = paramBoolean;
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
/*     */   public synchronized boolean getNotifyDiffer() {
/* 324 */     return this.notifyDiffer;
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
/*     */   public synchronized void setNotifyDiffer(boolean paramBoolean) {
/* 336 */     if (this.notifyDiffer == paramBoolean)
/*     */       return; 
/* 338 */     this.notifyDiffer = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MBeanNotificationInfo[] getNotificationInfo() {
/* 348 */     return (MBeanNotificationInfo[])notifsInfo.clone();
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
/*     */   Monitor.ObservedObject createObservedObject(ObjectName paramObjectName) {
/* 364 */     StringMonitorObservedObject stringMonitorObservedObject = new StringMonitorObservedObject(paramObjectName);
/*     */     
/* 366 */     stringMonitorObservedObject.setStatus(2);
/* 367 */     return stringMonitorObservedObject;
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
/*     */   synchronized boolean isComparableTypeValid(ObjectName paramObjectName, String paramString, Comparable<?> paramComparable) {
/* 380 */     if (paramComparable instanceof String) {
/* 381 */       return true;
/*     */     }
/* 383 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void onErrorNotification(MonitorNotification paramMonitorNotification) {
/* 389 */     StringMonitorObservedObject stringMonitorObservedObject = (StringMonitorObservedObject)getObservedObject(paramMonitorNotification.getObservedObject());
/* 390 */     if (stringMonitorObservedObject == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 395 */     stringMonitorObservedObject.setStatus(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized MonitorNotification buildAlarmNotification(ObjectName paramObjectName, String paramString, Comparable<?> paramComparable) {
/* 403 */     String str1 = null;
/* 404 */     String str2 = null;
/* 405 */     String str3 = null;
/*     */ 
/*     */     
/* 408 */     StringMonitorObservedObject stringMonitorObservedObject = (StringMonitorObservedObject)getObservedObject(paramObjectName);
/* 409 */     if (stringMonitorObservedObject == null) {
/* 410 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 415 */     if (stringMonitorObservedObject.getStatus() == 2) {
/* 416 */       if (stringMonitorObservedObject.getDerivedGauge().equals(this.stringToCompare)) {
/* 417 */         if (this.notifyMatch) {
/* 418 */           str1 = "jmx.monitor.string.matches";
/* 419 */           str2 = "";
/* 420 */           str3 = this.stringToCompare;
/*     */         } 
/* 422 */         stringMonitorObservedObject.setStatus(1);
/*     */       } else {
/* 424 */         if (this.notifyDiffer) {
/* 425 */           str1 = "jmx.monitor.string.differs";
/* 426 */           str2 = "";
/* 427 */           str3 = this.stringToCompare;
/*     */         } 
/* 429 */         stringMonitorObservedObject.setStatus(0);
/*     */       }
/*     */     
/* 432 */     } else if (stringMonitorObservedObject.getStatus() == 0) {
/* 433 */       if (stringMonitorObservedObject.getDerivedGauge().equals(this.stringToCompare)) {
/* 434 */         if (this.notifyMatch) {
/* 435 */           str1 = "jmx.monitor.string.matches";
/* 436 */           str2 = "";
/* 437 */           str3 = this.stringToCompare;
/*     */         } 
/* 439 */         stringMonitorObservedObject.setStatus(1);
/*     */       } 
/* 441 */     } else if (stringMonitorObservedObject.getStatus() == 1 && 
/* 442 */       !stringMonitorObservedObject.getDerivedGauge().equals(this.stringToCompare)) {
/* 443 */       if (this.notifyDiffer) {
/* 444 */         str1 = "jmx.monitor.string.differs";
/* 445 */         str2 = "";
/* 446 */         str3 = this.stringToCompare;
/*     */       } 
/* 448 */       stringMonitorObservedObject.setStatus(0);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 453 */     return new MonitorNotification(str1, this, 0L, 0L, str2, null, null, null, str3);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/monitor/StringMonitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */