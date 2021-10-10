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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GaugeMonitor
/*     */   extends Monitor
/*     */   implements GaugeMonitorMBean
/*     */ {
/*     */   static class GaugeMonitorObservedObject
/*     */     extends Monitor.ObservedObject
/*     */   {
/*     */     private boolean derivedGaugeValid;
/*     */     private Monitor.NumericalType type;
/*     */     private Number previousScanGauge;
/*     */     private int status;
/*     */     
/*     */     public GaugeMonitorObservedObject(ObjectName param1ObjectName) {
/*  98 */       super(param1ObjectName);
/*     */     }
/*     */     
/*     */     public final synchronized boolean getDerivedGaugeValid() {
/* 102 */       return this.derivedGaugeValid;
/*     */     }
/*     */     
/*     */     public final synchronized void setDerivedGaugeValid(boolean param1Boolean) {
/* 106 */       this.derivedGaugeValid = param1Boolean;
/*     */     }
/*     */     public final synchronized Monitor.NumericalType getType() {
/* 109 */       return this.type;
/*     */     }
/*     */     public final synchronized void setType(Monitor.NumericalType param1NumericalType) {
/* 112 */       this.type = param1NumericalType;
/*     */     }
/*     */     public final synchronized Number getPreviousScanGauge() {
/* 115 */       return this.previousScanGauge;
/*     */     }
/*     */     
/*     */     public final synchronized void setPreviousScanGauge(Number param1Number) {
/* 119 */       this.previousScanGauge = param1Number;
/*     */     }
/*     */     public final synchronized int getStatus() {
/* 122 */       return this.status;
/*     */     }
/*     */     public final synchronized void setStatus(int param1Int) {
/* 125 */       this.status = param1Int;
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
/* 145 */   private Number highThreshold = INTEGER_ZERO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   private Number lowThreshold = INTEGER_ZERO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean notifyHigh = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean notifyLow = false;
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
/* 181 */   private static final String[] types = new String[] { "jmx.monitor.error.runtime", "jmx.monitor.error.mbean", "jmx.monitor.error.attribute", "jmx.monitor.error.type", "jmx.monitor.error.threshold", "jmx.monitor.gauge.high", "jmx.monitor.gauge.low" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 191 */   private static final MBeanNotificationInfo[] notifsInfo = new MBeanNotificationInfo[] { new MBeanNotificationInfo(types, "javax.management.monitor.MonitorNotification", "Notifications sent by the GaugeMonitor MBean") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int RISING = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int FALLING = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int RISING_OR_FALLING = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void start() {
/* 226 */     if (isActive()) {
/* 227 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINER, GaugeMonitor.class.getName(), "start", "the monitor is already active");
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 233 */     for (Monitor.ObservedObject observedObject : this.observedObjects) {
/* 234 */       GaugeMonitorObservedObject gaugeMonitorObservedObject = (GaugeMonitorObservedObject)observedObject;
/*     */       
/* 236 */       gaugeMonitorObservedObject.setStatus(2);
/* 237 */       gaugeMonitorObservedObject.setPreviousScanGauge((Number)null);
/*     */     } 
/* 239 */     doStart();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void stop() {
/* 246 */     doStop();
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
/*     */   public synchronized Number getDerivedGauge(ObjectName paramObjectName) {
/* 263 */     return (Number)super.getDerivedGauge(paramObjectName);
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
/* 279 */     return super.getDerivedGaugeTimeStamp(paramObjectName);
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
/* 293 */     if (this.observedObjects.isEmpty()) {
/* 294 */       return null;
/*     */     }
/* 296 */     return (Number)((Monitor.ObservedObject)this.observedObjects.get(0)).getDerivedGauge();
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
/* 311 */     if (this.observedObjects.isEmpty()) {
/* 312 */       return 0L;
/*     */     }
/* 314 */     return ((Monitor.ObservedObject)this.observedObjects.get(0)).getDerivedGaugeTimeStamp();
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
/*     */   public synchronized Number getHighThreshold() {
/* 326 */     return this.highThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Number getLowThreshold() {
/* 337 */     return this.lowThreshold;
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
/*     */   public synchronized void setThresholds(Number paramNumber1, Number paramNumber2) throws IllegalArgumentException {
/* 358 */     if (paramNumber1 == null || paramNumber2 == null) {
/* 359 */       throw new IllegalArgumentException("Null threshold value");
/*     */     }
/*     */     
/* 362 */     if (paramNumber1.getClass() != paramNumber2.getClass()) {
/* 363 */       throw new IllegalArgumentException("Different type threshold values");
/*     */     }
/*     */ 
/*     */     
/* 367 */     if (isFirstStrictlyGreaterThanLast(paramNumber2, paramNumber1, paramNumber1
/* 368 */         .getClass().getName())) {
/* 369 */       throw new IllegalArgumentException("High threshold less than low threshold");
/*     */     }
/*     */ 
/*     */     
/* 373 */     if (this.highThreshold.equals(paramNumber1) && this.lowThreshold.equals(paramNumber2))
/*     */       return; 
/* 375 */     this.highThreshold = paramNumber1;
/* 376 */     this.lowThreshold = paramNumber2;
/*     */ 
/*     */ 
/*     */     
/* 380 */     byte b = 0;
/* 381 */     for (Monitor.ObservedObject observedObject : this.observedObjects) {
/* 382 */       resetAlreadyNotified(observedObject, b++, 16);
/* 383 */       GaugeMonitorObservedObject gaugeMonitorObservedObject = (GaugeMonitorObservedObject)observedObject;
/*     */       
/* 385 */       gaugeMonitorObservedObject.setStatus(2);
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
/*     */   public synchronized boolean getNotifyHigh() {
/* 399 */     return this.notifyHigh;
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
/*     */   public synchronized void setNotifyHigh(boolean paramBoolean) {
/* 411 */     if (this.notifyHigh == paramBoolean)
/*     */       return; 
/* 413 */     this.notifyHigh = paramBoolean;
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
/*     */   public synchronized boolean getNotifyLow() {
/* 426 */     return this.notifyLow;
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
/*     */   public synchronized void setNotifyLow(boolean paramBoolean) {
/* 438 */     if (this.notifyLow == paramBoolean)
/*     */       return; 
/* 440 */     this.notifyLow = paramBoolean;
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
/* 452 */     return this.differenceMode;
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
/* 463 */     if (this.differenceMode == paramBoolean)
/*     */       return; 
/* 465 */     this.differenceMode = paramBoolean;
/*     */ 
/*     */ 
/*     */     
/* 469 */     for (Monitor.ObservedObject observedObject : this.observedObjects) {
/* 470 */       GaugeMonitorObservedObject gaugeMonitorObservedObject = (GaugeMonitorObservedObject)observedObject;
/*     */       
/* 472 */       gaugeMonitorObservedObject.setStatus(2);
/* 473 */       gaugeMonitorObservedObject.setPreviousScanGauge((Number)null);
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
/* 484 */     return (MBeanNotificationInfo[])notifsInfo.clone();
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
/*     */   private synchronized boolean updateDerivedGauge(Object paramObject, GaugeMonitorObservedObject paramGaugeMonitorObservedObject) {
/*     */     boolean bool;
/* 511 */     if (this.differenceMode) {
/*     */ 
/*     */ 
/*     */       
/* 515 */       if (paramGaugeMonitorObservedObject.getPreviousScanGauge() != null) {
/* 516 */         setDerivedGaugeWithDifference((Number)paramObject, paramGaugeMonitorObservedObject);
/* 517 */         bool = true;
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 523 */         bool = false;
/*     */       } 
/* 525 */       paramGaugeMonitorObservedObject.setPreviousScanGauge((Number)paramObject);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 530 */       paramGaugeMonitorObservedObject.setDerivedGauge(paramObject);
/* 531 */       bool = true;
/*     */     } 
/*     */     
/* 534 */     return bool;
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
/*     */   private synchronized MonitorNotification updateNotifications(GaugeMonitorObservedObject paramGaugeMonitorObservedObject) {
/* 546 */     MonitorNotification monitorNotification = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 551 */     if (paramGaugeMonitorObservedObject.getStatus() == 2) {
/* 552 */       if (isFirstGreaterThanLast((Number)paramGaugeMonitorObservedObject.getDerivedGauge(), this.highThreshold, paramGaugeMonitorObservedObject
/*     */           
/* 554 */           .getType())) {
/* 555 */         if (this.notifyHigh) {
/* 556 */           monitorNotification = new MonitorNotification("jmx.monitor.gauge.high", this, 0L, 0L, "", null, null, null, this.highThreshold);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 567 */         paramGaugeMonitorObservedObject.setStatus(1);
/* 568 */       } else if (isFirstGreaterThanLast(this.lowThreshold, (Number)paramGaugeMonitorObservedObject
/* 569 */           .getDerivedGauge(), paramGaugeMonitorObservedObject
/* 570 */           .getType())) {
/* 571 */         if (this.notifyLow) {
/* 572 */           monitorNotification = new MonitorNotification("jmx.monitor.gauge.low", this, 0L, 0L, "", null, null, null, this.lowThreshold);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 583 */         paramGaugeMonitorObservedObject.setStatus(0);
/*     */       }
/*     */     
/* 586 */     } else if (paramGaugeMonitorObservedObject.getStatus() == 0) {
/* 587 */       if (isFirstGreaterThanLast((Number)paramGaugeMonitorObservedObject.getDerivedGauge(), this.highThreshold, paramGaugeMonitorObservedObject
/*     */           
/* 589 */           .getType())) {
/* 590 */         if (this.notifyHigh) {
/* 591 */           monitorNotification = new MonitorNotification("jmx.monitor.gauge.high", this, 0L, 0L, "", null, null, null, this.highThreshold);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 602 */         paramGaugeMonitorObservedObject.setStatus(1);
/*     */       } 
/* 604 */     } else if (paramGaugeMonitorObservedObject.getStatus() == 1 && 
/* 605 */       isFirstGreaterThanLast(this.lowThreshold, (Number)paramGaugeMonitorObservedObject
/* 606 */         .getDerivedGauge(), paramGaugeMonitorObservedObject
/* 607 */         .getType())) {
/* 608 */       if (this.notifyLow) {
/* 609 */         monitorNotification = new MonitorNotification("jmx.monitor.gauge.low", this, 0L, 0L, "", null, null, null, this.lowThreshold);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 620 */       paramGaugeMonitorObservedObject.setStatus(0);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 625 */     return monitorNotification;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void setDerivedGaugeWithDifference(Number paramNumber, GaugeMonitorObservedObject paramGaugeMonitorObservedObject) {
/*     */     Integer integer;
/*     */     Byte byte_;
/*     */     Short short_;
/*     */     Long long_;
/*     */     Float float_;
/*     */     Double double_;
/* 638 */     Number number = paramGaugeMonitorObservedObject.getPreviousScanGauge();
/*     */     
/* 640 */     switch (paramGaugeMonitorObservedObject.getType()) {
/*     */       case INTEGER:
/* 642 */         integer = Integer.valueOf(((Integer)paramNumber).intValue() - ((Integer)number)
/* 643 */             .intValue());
/*     */         break;
/*     */       case BYTE:
/* 646 */         byte_ = Byte.valueOf(
/* 647 */             (byte)(((Byte)paramNumber).byteValue() - ((Byte)number).byteValue()));
/*     */         break;
/*     */       case SHORT:
/* 650 */         short_ = Short.valueOf(
/* 651 */             (short)(((Short)paramNumber).shortValue() - ((Short)number).shortValue()));
/*     */         break;
/*     */       case LONG:
/* 654 */         long_ = Long.valueOf(((Long)paramNumber).longValue() - ((Long)number)
/* 655 */             .longValue());
/*     */         break;
/*     */       case FLOAT:
/* 658 */         float_ = Float.valueOf(((Float)paramNumber).floatValue() - ((Float)number)
/* 659 */             .floatValue());
/*     */         break;
/*     */       case DOUBLE:
/* 662 */         double_ = Double.valueOf(((Double)paramNumber).doubleValue() - ((Double)number)
/* 663 */             .doubleValue());
/*     */         break;
/*     */       
/*     */       default:
/* 667 */         JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, GaugeMonitor.class.getName(), "setDerivedGaugeWithDifference", "the threshold type is invalid");
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 672 */     paramGaugeMonitorObservedObject.setDerivedGauge(double_);
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
/*     */   private boolean isFirstGreaterThanLast(Number paramNumber1, Number paramNumber2, Monitor.NumericalType paramNumericalType) {
/* 690 */     switch (paramNumericalType) {
/*     */       case INTEGER:
/*     */       case BYTE:
/*     */       case SHORT:
/*     */       case LONG:
/* 695 */         return (paramNumber1.longValue() >= paramNumber2.longValue());
/*     */       case FLOAT:
/*     */       case DOUBLE:
/* 698 */         return (paramNumber1.doubleValue() >= paramNumber2.doubleValue());
/*     */     } 
/*     */     
/* 701 */     JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, GaugeMonitor.class.getName(), "isFirstGreaterThanLast", "the threshold type is invalid");
/*     */ 
/*     */     
/* 704 */     return false;
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
/*     */   private boolean isFirstStrictlyGreaterThanLast(Number paramNumber1, Number paramNumber2, String paramString) {
/* 722 */     if (paramString.equals("java.lang.Integer") || paramString
/* 723 */       .equals("java.lang.Byte") || paramString
/* 724 */       .equals("java.lang.Short") || paramString
/* 725 */       .equals("java.lang.Long"))
/*     */     {
/* 727 */       return (paramNumber1.longValue() > paramNumber2.longValue());
/*     */     }
/* 729 */     if (paramString.equals("java.lang.Float") || paramString
/* 730 */       .equals("java.lang.Double"))
/*     */     {
/* 732 */       return (paramNumber1.doubleValue() > paramNumber2.doubleValue());
/*     */     }
/*     */ 
/*     */     
/* 736 */     JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, GaugeMonitor.class.getName(), "isFirstStrictlyGreaterThanLast", "the threshold type is invalid");
/*     */ 
/*     */     
/* 739 */     return false;
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
/*     */   Monitor.ObservedObject createObservedObject(ObjectName paramObjectName) {
/* 756 */     GaugeMonitorObservedObject gaugeMonitorObservedObject = new GaugeMonitorObservedObject(paramObjectName);
/*     */     
/* 758 */     gaugeMonitorObservedObject.setStatus(2);
/* 759 */     gaugeMonitorObservedObject.setPreviousScanGauge((Number)null);
/* 760 */     return gaugeMonitorObservedObject;
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
/* 774 */     GaugeMonitorObservedObject gaugeMonitorObservedObject = (GaugeMonitorObservedObject)getObservedObject(paramObjectName);
/* 775 */     if (gaugeMonitorObservedObject == null) {
/* 776 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 781 */     if (paramComparable instanceof Integer) {
/* 782 */       gaugeMonitorObservedObject.setType(Monitor.NumericalType.INTEGER);
/* 783 */     } else if (paramComparable instanceof Byte) {
/* 784 */       gaugeMonitorObservedObject.setType(Monitor.NumericalType.BYTE);
/* 785 */     } else if (paramComparable instanceof Short) {
/* 786 */       gaugeMonitorObservedObject.setType(Monitor.NumericalType.SHORT);
/* 787 */     } else if (paramComparable instanceof Long) {
/* 788 */       gaugeMonitorObservedObject.setType(Monitor.NumericalType.LONG);
/* 789 */     } else if (paramComparable instanceof Float) {
/* 790 */       gaugeMonitorObservedObject.setType(Monitor.NumericalType.FLOAT);
/* 791 */     } else if (paramComparable instanceof Double) {
/* 792 */       gaugeMonitorObservedObject.setType(Monitor.NumericalType.DOUBLE);
/*     */     } else {
/* 794 */       return false;
/*     */     } 
/* 796 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized Comparable<?> getDerivedGaugeFromComparable(ObjectName paramObjectName, String paramString, Comparable<?> paramComparable) {
/* 805 */     GaugeMonitorObservedObject gaugeMonitorObservedObject = (GaugeMonitorObservedObject)getObservedObject(paramObjectName);
/* 806 */     if (gaugeMonitorObservedObject == null) {
/* 807 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 816 */     gaugeMonitorObservedObject.setDerivedGaugeValid(updateDerivedGauge(paramComparable, gaugeMonitorObservedObject));
/*     */     
/* 818 */     return (Comparable)gaugeMonitorObservedObject.getDerivedGauge();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void onErrorNotification(MonitorNotification paramMonitorNotification) {
/* 824 */     GaugeMonitorObservedObject gaugeMonitorObservedObject = (GaugeMonitorObservedObject)getObservedObject(paramMonitorNotification.getObservedObject());
/* 825 */     if (gaugeMonitorObservedObject == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 830 */     gaugeMonitorObservedObject.setStatus(2);
/* 831 */     gaugeMonitorObservedObject.setPreviousScanGauge((Number)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized MonitorNotification buildAlarmNotification(ObjectName paramObjectName, String paramString, Comparable<?> paramComparable) {
/*     */     MonitorNotification monitorNotification;
/* 840 */     GaugeMonitorObservedObject gaugeMonitorObservedObject = (GaugeMonitorObservedObject)getObservedObject(paramObjectName);
/* 841 */     if (gaugeMonitorObservedObject == null) {
/* 842 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 848 */     if (gaugeMonitorObservedObject.getDerivedGaugeValid()) {
/* 849 */       monitorNotification = updateNotifications(gaugeMonitorObservedObject);
/*     */     } else {
/* 851 */       monitorNotification = null;
/* 852 */     }  return monitorNotification;
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
/*     */   synchronized boolean isThresholdTypeValid(ObjectName paramObjectName, String paramString, Comparable<?> paramComparable) {
/* 876 */     GaugeMonitorObservedObject gaugeMonitorObservedObject = (GaugeMonitorObservedObject)getObservedObject(paramObjectName);
/* 877 */     if (gaugeMonitorObservedObject == null) {
/* 878 */       return false;
/*     */     }
/* 880 */     Class<? extends Number> clazz = classForType(gaugeMonitorObservedObject.getType());
/* 881 */     return (isValidForType(this.highThreshold, clazz) && 
/* 882 */       isValidForType(this.lowThreshold, clazz));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/monitor/GaugeMonitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */