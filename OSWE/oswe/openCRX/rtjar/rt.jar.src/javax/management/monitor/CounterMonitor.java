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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CounterMonitor
/*     */   extends Monitor
/*     */   implements CounterMonitorMBean
/*     */ {
/*     */   static class CounterMonitorObservedObject
/*     */     extends Monitor.ObservedObject
/*     */   {
/*     */     private Number threshold;
/*     */     private Number previousScanCounter;
/*     */     private boolean modulusExceeded;
/*     */     private Number derivedGaugeExceeded;
/*     */     private boolean derivedGaugeValid;
/*     */     private boolean eventAlreadyNotified;
/*     */     private Monitor.NumericalType type;
/*     */     
/*     */     public CounterMonitorObservedObject(ObjectName param1ObjectName) {
/*  90 */       super(param1ObjectName);
/*     */     }
/*     */     
/*     */     public final synchronized Number getThreshold() {
/*  94 */       return this.threshold;
/*     */     }
/*     */     public final synchronized void setThreshold(Number param1Number) {
/*  97 */       this.threshold = param1Number;
/*     */     }
/*     */     public final synchronized Number getPreviousScanCounter() {
/* 100 */       return this.previousScanCounter;
/*     */     }
/*     */     
/*     */     public final synchronized void setPreviousScanCounter(Number param1Number) {
/* 104 */       this.previousScanCounter = param1Number;
/*     */     }
/*     */     public final synchronized boolean getModulusExceeded() {
/* 107 */       return this.modulusExceeded;
/*     */     }
/*     */     
/*     */     public final synchronized void setModulusExceeded(boolean param1Boolean) {
/* 111 */       this.modulusExceeded = param1Boolean;
/*     */     }
/*     */     public final synchronized Number getDerivedGaugeExceeded() {
/* 114 */       return this.derivedGaugeExceeded;
/*     */     }
/*     */     
/*     */     public final synchronized void setDerivedGaugeExceeded(Number param1Number) {
/* 118 */       this.derivedGaugeExceeded = param1Number;
/*     */     }
/*     */     public final synchronized boolean getDerivedGaugeValid() {
/* 121 */       return this.derivedGaugeValid;
/*     */     }
/*     */     
/*     */     public final synchronized void setDerivedGaugeValid(boolean param1Boolean) {
/* 125 */       this.derivedGaugeValid = param1Boolean;
/*     */     }
/*     */     public final synchronized boolean getEventAlreadyNotified() {
/* 128 */       return this.eventAlreadyNotified;
/*     */     }
/*     */     
/*     */     public final synchronized void setEventAlreadyNotified(boolean param1Boolean) {
/* 132 */       this.eventAlreadyNotified = param1Boolean;
/*     */     }
/*     */     public final synchronized Monitor.NumericalType getType() {
/* 135 */       return this.type;
/*     */     }
/*     */     public final synchronized void setType(Monitor.NumericalType param1NumericalType) {
/* 138 */       this.type = param1NumericalType;
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
/* 160 */   private Number modulus = INTEGER_ZERO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   private Number offset = INTEGER_ZERO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean notify = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean differenceMode = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   private Number initThreshold = INTEGER_ZERO;
/*     */   
/* 192 */   private static final String[] types = new String[] { "jmx.monitor.error.runtime", "jmx.monitor.error.mbean", "jmx.monitor.error.attribute", "jmx.monitor.error.type", "jmx.monitor.error.threshold", "jmx.monitor.counter.threshold" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 201 */   private static final MBeanNotificationInfo[] notifsInfo = new MBeanNotificationInfo[] { new MBeanNotificationInfo(types, "javax.management.monitor.MonitorNotification", "Notifications sent by the CounterMonitor MBean") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void start() {
/* 230 */     if (isActive()) {
/* 231 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINER, CounterMonitor.class.getName(), "start", "the monitor is already active");
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 237 */     for (Monitor.ObservedObject observedObject : this.observedObjects) {
/* 238 */       CounterMonitorObservedObject counterMonitorObservedObject = (CounterMonitorObservedObject)observedObject;
/*     */       
/* 240 */       counterMonitorObservedObject.setThreshold(this.initThreshold);
/* 241 */       counterMonitorObservedObject.setModulusExceeded(false);
/* 242 */       counterMonitorObservedObject.setEventAlreadyNotified(false);
/* 243 */       counterMonitorObservedObject.setPreviousScanCounter((Number)null);
/*     */     } 
/* 245 */     doStart();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void stop() {
/* 252 */     doStop();
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
/*     */   public synchronized Number getDerivedGauge(ObjectName paramObjectName) {
/* 270 */     return (Number)super.getDerivedGauge(paramObjectName);
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
/* 286 */     return super.getDerivedGaugeTimeStamp(paramObjectName);
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
/*     */   public synchronized Number getThreshold(ObjectName paramObjectName) {
/* 302 */     CounterMonitorObservedObject counterMonitorObservedObject = (CounterMonitorObservedObject)getObservedObject(paramObjectName);
/* 303 */     if (counterMonitorObservedObject == null) {
/* 304 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 314 */     if (this.offset.longValue() > 0L && this.modulus
/* 315 */       .longValue() > 0L && counterMonitorObservedObject
/* 316 */       .getThreshold().longValue() > this.modulus.longValue()) {
/* 317 */       return this.initThreshold;
/*     */     }
/* 319 */     return counterMonitorObservedObject.getThreshold();
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
/*     */   public synchronized Number getInitThreshold() {
/* 332 */     return this.initThreshold;
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
/*     */   public synchronized void setInitThreshold(Number paramNumber) throws IllegalArgumentException {
/* 352 */     if (paramNumber == null) {
/* 353 */       throw new IllegalArgumentException("Null threshold");
/*     */     }
/* 355 */     if (paramNumber.longValue() < 0L) {
/* 356 */       throw new IllegalArgumentException("Negative threshold");
/*     */     }
/*     */     
/* 359 */     if (this.initThreshold.equals(paramNumber))
/*     */       return; 
/* 361 */     this.initThreshold = paramNumber;
/*     */ 
/*     */ 
/*     */     
/* 365 */     byte b = 0;
/* 366 */     for (Monitor.ObservedObject observedObject : this.observedObjects) {
/* 367 */       resetAlreadyNotified(observedObject, b++, 16);
/* 368 */       CounterMonitorObservedObject counterMonitorObservedObject = (CounterMonitorObservedObject)observedObject;
/*     */       
/* 370 */       counterMonitorObservedObject.setThreshold(paramNumber);
/* 371 */       counterMonitorObservedObject.setModulusExceeded(false);
/* 372 */       counterMonitorObservedObject.setEventAlreadyNotified(false);
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
/*     */   @Deprecated
/*     */   public synchronized Number getDerivedGauge() {
/* 387 */     if (this.observedObjects.isEmpty()) {
/* 388 */       return null;
/*     */     }
/* 390 */     return (Number)((Monitor.ObservedObject)this.observedObjects.get(0)).getDerivedGauge();
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
/* 405 */     if (this.observedObjects.isEmpty()) {
/* 406 */       return 0L;
/*     */     }
/* 408 */     return ((Monitor.ObservedObject)this.observedObjects.get(0)).getDerivedGaugeTimeStamp();
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
/*     */   @Deprecated
/*     */   public synchronized Number getThreshold() {
/* 424 */     return getThreshold(getObservedObject());
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
/*     */   @Deprecated
/*     */   public synchronized void setThreshold(Number paramNumber) throws IllegalArgumentException {
/* 442 */     setInitThreshold(paramNumber);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Number getOffset() {
/* 453 */     return this.offset;
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
/*     */   public synchronized void setOffset(Number paramNumber) throws IllegalArgumentException {
/* 469 */     if (paramNumber == null) {
/* 470 */       throw new IllegalArgumentException("Null offset");
/*     */     }
/* 472 */     if (paramNumber.longValue() < 0L) {
/* 473 */       throw new IllegalArgumentException("Negative offset");
/*     */     }
/*     */     
/* 476 */     if (this.offset.equals(paramNumber))
/*     */       return; 
/* 478 */     this.offset = paramNumber;
/*     */     
/* 480 */     byte b = 0;
/* 481 */     for (Monitor.ObservedObject observedObject : this.observedObjects) {
/* 482 */       resetAlreadyNotified(observedObject, b++, 16);
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
/*     */   public synchronized Number getModulus() {
/* 494 */     return this.modulus;
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
/*     */   public synchronized void setModulus(Number paramNumber) throws IllegalArgumentException {
/* 510 */     if (paramNumber == null) {
/* 511 */       throw new IllegalArgumentException("Null modulus");
/*     */     }
/* 513 */     if (paramNumber.longValue() < 0L) {
/* 514 */       throw new IllegalArgumentException("Negative modulus");
/*     */     }
/*     */     
/* 517 */     if (this.modulus.equals(paramNumber))
/*     */       return; 
/* 519 */     this.modulus = paramNumber;
/*     */ 
/*     */ 
/*     */     
/* 523 */     byte b = 0;
/* 524 */     for (Monitor.ObservedObject observedObject : this.observedObjects) {
/* 525 */       resetAlreadyNotified(observedObject, b++, 16);
/* 526 */       CounterMonitorObservedObject counterMonitorObservedObject = (CounterMonitorObservedObject)observedObject;
/*     */       
/* 528 */       counterMonitorObservedObject.setModulusExceeded(false);
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
/*     */   public synchronized boolean getNotify() {
/* 542 */     return this.notify;
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
/*     */   public synchronized void setNotify(boolean paramBoolean) {
/* 554 */     if (this.notify == paramBoolean)
/*     */       return; 
/* 556 */     this.notify = paramBoolean;
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
/*     */   public synchronized boolean getDifferenceMode() {
/* 568 */     return this.differenceMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setDifferenceMode(boolean paramBoolean) {
/* 579 */     if (this.differenceMode == paramBoolean)
/*     */       return; 
/* 581 */     this.differenceMode = paramBoolean;
/*     */ 
/*     */ 
/*     */     
/* 585 */     for (Monitor.ObservedObject observedObject : this.observedObjects) {
/* 586 */       CounterMonitorObservedObject counterMonitorObservedObject = (CounterMonitorObservedObject)observedObject;
/*     */       
/* 588 */       counterMonitorObservedObject.setThreshold(this.initThreshold);
/* 589 */       counterMonitorObservedObject.setModulusExceeded(false);
/* 590 */       counterMonitorObservedObject.setEventAlreadyNotified(false);
/* 591 */       counterMonitorObservedObject.setPreviousScanCounter((Number)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MBeanNotificationInfo[] getNotificationInfo() {
/* 602 */     return (MBeanNotificationInfo[])notifsInfo.clone();
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
/*     */   private synchronized boolean updateDerivedGauge(Object paramObject, CounterMonitorObservedObject paramCounterMonitorObservedObject) {
/*     */     boolean bool;
/* 629 */     if (this.differenceMode) {
/*     */ 
/*     */ 
/*     */       
/* 633 */       if (paramCounterMonitorObservedObject.getPreviousScanCounter() != null) {
/* 634 */         setDerivedGaugeWithDifference((Number)paramObject, (Number)null, paramCounterMonitorObservedObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 640 */         if (((Number)paramCounterMonitorObservedObject.getDerivedGauge()).longValue() < 0L) {
/* 641 */           if (this.modulus.longValue() > 0L) {
/* 642 */             setDerivedGaugeWithDifference((Number)paramObject, this.modulus, paramCounterMonitorObservedObject);
/*     */           }
/*     */           
/* 645 */           paramCounterMonitorObservedObject.setThreshold(this.initThreshold);
/* 646 */           paramCounterMonitorObservedObject.setEventAlreadyNotified(false);
/*     */         } 
/* 648 */         bool = true;
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 654 */         bool = false;
/*     */       } 
/* 656 */       paramCounterMonitorObservedObject.setPreviousScanCounter((Number)paramObject);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 661 */       paramCounterMonitorObservedObject.setDerivedGauge(paramObject);
/* 662 */       bool = true;
/*     */     } 
/* 664 */     return bool;
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
/*     */   private synchronized MonitorNotification updateNotifications(CounterMonitorObservedObject paramCounterMonitorObservedObject) {
/* 676 */     MonitorNotification monitorNotification = null;
/*     */ 
/*     */ 
/*     */     
/* 680 */     if (!paramCounterMonitorObservedObject.getEventAlreadyNotified()) {
/* 681 */       if (((Number)paramCounterMonitorObservedObject.getDerivedGauge()).longValue() >= paramCounterMonitorObservedObject
/* 682 */         .getThreshold().longValue()) {
/* 683 */         if (this.notify)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 692 */           monitorNotification = new MonitorNotification("jmx.monitor.counter.threshold", this, 0L, 0L, "", null, null, null, paramCounterMonitorObservedObject.getThreshold());
/*     */         }
/* 694 */         if (!this.differenceMode) {
/* 695 */           paramCounterMonitorObservedObject.setEventAlreadyNotified(true);
/*     */         }
/*     */       }
/*     */     
/* 699 */     } else if (JmxProperties.MONITOR_LOGGER.isLoggable(Level.FINER)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 710 */       StringBuilder stringBuilder = (new StringBuilder()).append("The notification:").append("\n\tNotification observed object = ").append(paramCounterMonitorObservedObject.getObservedObject()).append("\n\tNotification observed attribute = ").append(getObservedAttribute()).append("\n\tNotification threshold level = ").append(paramCounterMonitorObservedObject.getThreshold()).append("\n\tNotification derived gauge = ").append(paramCounterMonitorObservedObject.getDerivedGauge()).append("\nhas already been sent");
/* 711 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINER, CounterMonitor.class.getName(), "updateNotifications", stringBuilder
/* 712 */           .toString());
/*     */     } 
/*     */ 
/*     */     
/* 716 */     return monitorNotification;
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
/*     */   private synchronized void updateThreshold(CounterMonitorObservedObject paramCounterMonitorObservedObject) {
/* 728 */     if (((Number)paramCounterMonitorObservedObject.getDerivedGauge()).longValue() >= paramCounterMonitorObservedObject
/* 729 */       .getThreshold().longValue())
/*     */     {
/* 731 */       if (this.offset.longValue() > 0L) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 736 */         long l = paramCounterMonitorObservedObject.getThreshold().longValue();
/* 737 */         while (((Number)paramCounterMonitorObservedObject.getDerivedGauge()).longValue() >= l)
/*     */         {
/* 739 */           l += this.offset.longValue();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 744 */         switch (paramCounterMonitorObservedObject.getType()) {
/*     */           case INTEGER:
/* 746 */             paramCounterMonitorObservedObject.setThreshold(Integer.valueOf((int)l));
/*     */             break;
/*     */           case BYTE:
/* 749 */             paramCounterMonitorObservedObject.setThreshold(Byte.valueOf((byte)(int)l));
/*     */             break;
/*     */           case SHORT:
/* 752 */             paramCounterMonitorObservedObject.setThreshold(Short.valueOf((short)(int)l));
/*     */             break;
/*     */           case LONG:
/* 755 */             paramCounterMonitorObservedObject.setThreshold(Long.valueOf(l));
/*     */             break;
/*     */           
/*     */           default:
/* 759 */             JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, CounterMonitor.class
/* 760 */                 .getName(), "updateThreshold", "the threshold type is invalid");
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 771 */         if (!this.differenceMode && 
/* 772 */           this.modulus.longValue() > 0L && 
/* 773 */           paramCounterMonitorObservedObject.getThreshold().longValue() > this.modulus
/* 774 */           .longValue()) {
/* 775 */           paramCounterMonitorObservedObject.setModulusExceeded(true);
/* 776 */           paramCounterMonitorObservedObject.setDerivedGaugeExceeded((Number)paramCounterMonitorObservedObject
/* 777 */               .getDerivedGauge());
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 784 */         paramCounterMonitorObservedObject.setEventAlreadyNotified(false);
/*     */       } else {
/* 786 */         paramCounterMonitorObservedObject.setModulusExceeded(true);
/* 787 */         paramCounterMonitorObservedObject.setDerivedGaugeExceeded((Number)paramCounterMonitorObservedObject.getDerivedGauge());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void setDerivedGaugeWithDifference(Number paramNumber1, Number paramNumber2, CounterMonitorObservedObject paramCounterMonitorObservedObject) {
/* 811 */     long l = paramNumber1.longValue() - paramCounterMonitorObservedObject.getPreviousScanCounter().longValue();
/* 812 */     if (paramNumber2 != null) {
/* 813 */       l += this.modulus.longValue();
/*     */     }
/* 815 */     switch (paramCounterMonitorObservedObject.getType()) { case INTEGER:
/* 816 */         paramCounterMonitorObservedObject.setDerivedGauge(Integer.valueOf((int)l)); return;
/* 817 */       case BYTE: paramCounterMonitorObservedObject.setDerivedGauge(Byte.valueOf((byte)(int)l)); return;
/* 818 */       case SHORT: paramCounterMonitorObservedObject.setDerivedGauge(Short.valueOf((short)(int)l)); return;
/* 819 */       case LONG: paramCounterMonitorObservedObject.setDerivedGauge(Long.valueOf(l));
/*     */         return; }
/*     */     
/* 822 */     JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, CounterMonitor.class.getName(), "setDerivedGaugeWithDifference", "the threshold type is invalid");
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
/*     */   Monitor.ObservedObject createObservedObject(ObjectName paramObjectName) {
/* 842 */     CounterMonitorObservedObject counterMonitorObservedObject = new CounterMonitorObservedObject(paramObjectName);
/*     */     
/* 844 */     counterMonitorObservedObject.setThreshold(this.initThreshold);
/* 845 */     counterMonitorObservedObject.setModulusExceeded(false);
/* 846 */     counterMonitorObservedObject.setEventAlreadyNotified(false);
/* 847 */     counterMonitorObservedObject.setPreviousScanCounter((Number)null);
/* 848 */     return counterMonitorObservedObject;
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
/*     */   synchronized boolean isComparableTypeValid(ObjectName paramObjectName, String paramString, Comparable<?> paramComparable) {
/* 862 */     CounterMonitorObservedObject counterMonitorObservedObject = (CounterMonitorObservedObject)getObservedObject(paramObjectName);
/* 863 */     if (counterMonitorObservedObject == null) {
/* 864 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 868 */     if (paramComparable instanceof Integer) {
/* 869 */       counterMonitorObservedObject.setType(Monitor.NumericalType.INTEGER);
/* 870 */     } else if (paramComparable instanceof Byte) {
/* 871 */       counterMonitorObservedObject.setType(Monitor.NumericalType.BYTE);
/* 872 */     } else if (paramComparable instanceof Short) {
/* 873 */       counterMonitorObservedObject.setType(Monitor.NumericalType.SHORT);
/* 874 */     } else if (paramComparable instanceof Long) {
/* 875 */       counterMonitorObservedObject.setType(Monitor.NumericalType.LONG);
/*     */     } else {
/* 877 */       return false;
/*     */     } 
/* 879 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized Comparable<?> getDerivedGaugeFromComparable(ObjectName paramObjectName, String paramString, Comparable<?> paramComparable) {
/* 888 */     CounterMonitorObservedObject counterMonitorObservedObject = (CounterMonitorObservedObject)getObservedObject(paramObjectName);
/* 889 */     if (counterMonitorObservedObject == null) {
/* 890 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 894 */     if (counterMonitorObservedObject.getModulusExceeded() && (
/* 895 */       (Number)counterMonitorObservedObject.getDerivedGauge()).longValue() < counterMonitorObservedObject
/* 896 */       .getDerivedGaugeExceeded().longValue()) {
/* 897 */       counterMonitorObservedObject.setThreshold(this.initThreshold);
/* 898 */       counterMonitorObservedObject.setModulusExceeded(false);
/* 899 */       counterMonitorObservedObject.setEventAlreadyNotified(false);
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
/* 910 */     counterMonitorObservedObject.setDerivedGaugeValid(updateDerivedGauge(paramComparable, counterMonitorObservedObject));
/*     */     
/* 912 */     return (Comparable)counterMonitorObservedObject.getDerivedGauge();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void onErrorNotification(MonitorNotification paramMonitorNotification) {
/* 918 */     CounterMonitorObservedObject counterMonitorObservedObject = (CounterMonitorObservedObject)getObservedObject(paramMonitorNotification.getObservedObject());
/* 919 */     if (counterMonitorObservedObject == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 924 */     counterMonitorObservedObject.setModulusExceeded(false);
/* 925 */     counterMonitorObservedObject.setEventAlreadyNotified(false);
/* 926 */     counterMonitorObservedObject.setPreviousScanCounter((Number)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized MonitorNotification buildAlarmNotification(ObjectName paramObjectName, String paramString, Comparable<?> paramComparable) {
/*     */     MonitorNotification monitorNotification;
/* 935 */     CounterMonitorObservedObject counterMonitorObservedObject = (CounterMonitorObservedObject)getObservedObject(paramObjectName);
/* 936 */     if (counterMonitorObservedObject == null) {
/* 937 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 943 */     if (counterMonitorObservedObject.getDerivedGaugeValid()) {
/* 944 */       monitorNotification = updateNotifications(counterMonitorObservedObject);
/* 945 */       updateThreshold(counterMonitorObservedObject);
/*     */     } else {
/* 947 */       monitorNotification = null;
/*     */     } 
/* 949 */     return monitorNotification;
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
/*     */   synchronized boolean isThresholdTypeValid(ObjectName paramObjectName, String paramString, Comparable<?> paramComparable) {
/* 972 */     CounterMonitorObservedObject counterMonitorObservedObject = (CounterMonitorObservedObject)getObservedObject(paramObjectName);
/* 973 */     if (counterMonitorObservedObject == null) {
/* 974 */       return false;
/*     */     }
/* 976 */     Class<? extends Number> clazz = classForType(counterMonitorObservedObject.getType());
/* 977 */     return (clazz.isInstance(counterMonitorObservedObject.getThreshold()) && 
/* 978 */       isValidForType(this.offset, clazz) && 
/* 979 */       isValidForType(this.modulus, clazz));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/monitor/CounterMonitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */