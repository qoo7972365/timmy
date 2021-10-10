/*      */ package javax.management.monitor;
/*      */ 
/*      */ import com.sun.jmx.defaults.JmxProperties;
/*      */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*      */ import com.sun.jmx.mbeanserver.Introspector;
/*      */ import java.io.IOException;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.WeakHashMap;
/*      */ import java.util.concurrent.CopyOnWriteArrayList;
/*      */ import java.util.concurrent.Executors;
/*      */ import java.util.concurrent.Future;
/*      */ import java.util.concurrent.LinkedBlockingQueue;
/*      */ import java.util.concurrent.ScheduledExecutorService;
/*      */ import java.util.concurrent.ScheduledFuture;
/*      */ import java.util.concurrent.ThreadFactory;
/*      */ import java.util.concurrent.ThreadPoolExecutor;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import java.util.logging.Level;
/*      */ import javax.management.AttributeNotFoundException;
/*      */ import javax.management.InstanceNotFoundException;
/*      */ import javax.management.IntrospectionException;
/*      */ import javax.management.MBeanAttributeInfo;
/*      */ import javax.management.MBeanException;
/*      */ import javax.management.MBeanInfo;
/*      */ import javax.management.MBeanRegistration;
/*      */ import javax.management.MBeanServer;
/*      */ import javax.management.MBeanServerConnection;
/*      */ import javax.management.NotificationBroadcasterSupport;
/*      */ import javax.management.ObjectName;
/*      */ import javax.management.ReflectionException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Monitor
/*      */   extends NotificationBroadcasterSupport
/*      */   implements MonitorMBean, MBeanRegistration
/*      */ {
/*      */   private String observedAttribute;
/*      */   
/*      */   static class ObservedObject
/*      */   {
/*      */     private final ObjectName observedObject;
/*      */     private int alreadyNotified;
/*      */     private Object derivedGauge;
/*      */     private long derivedGaugeTimeStamp;
/*      */     
/*      */     public ObservedObject(ObjectName param1ObjectName) {
/*   88 */       this.observedObject = param1ObjectName;
/*      */     }
/*      */     
/*      */     public final ObjectName getObservedObject() {
/*   92 */       return this.observedObject;
/*      */     }
/*      */     public final synchronized int getAlreadyNotified() {
/*   95 */       return this.alreadyNotified;
/*      */     }
/*      */     public final synchronized void setAlreadyNotified(int param1Int) {
/*   98 */       this.alreadyNotified = param1Int;
/*      */     }
/*      */     public final synchronized Object getDerivedGauge() {
/*  101 */       return this.derivedGauge;
/*      */     }
/*      */     public final synchronized void setDerivedGauge(Object param1Object) {
/*  104 */       this.derivedGauge = param1Object;
/*      */     }
/*      */     public final synchronized long getDerivedGaugeTimeStamp() {
/*  107 */       return this.derivedGaugeTimeStamp;
/*      */     }
/*      */     
/*      */     public final synchronized void setDerivedGaugeTimeStamp(long param1Long) {
/*  111 */       this.derivedGaugeTimeStamp = param1Long;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  135 */   private long granularityPeriod = 10000L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isActive = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  147 */   private final AtomicLong sequenceNumber = new AtomicLong();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isComplexTypeAttribute = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String firstAttribute;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  163 */   private final List<String> remainingAttributes = new CopyOnWriteArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  169 */   private static final AccessControlContext noPermissionsACC = new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, null) });
/*      */ 
/*      */   
/*  172 */   private volatile AccessControlContext acc = noPermissionsACC;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  178 */   private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory("Scheduler"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  184 */   private static final Map<ThreadPoolExecutor, Void> executors = new WeakHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  190 */   private static final Object executorsLock = new Object();
/*      */   
/*      */   private static final int maximumPoolSize;
/*      */   
/*      */   private Future<?> monitorFuture;
/*      */ 
/*      */   
/*      */   static {
/*  198 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("jmx.x.monitor.maximum.pool.size"));
/*      */     
/*  200 */     if (str == null || str
/*  201 */       .trim().length() == 0) {
/*  202 */       maximumPoolSize = 10;
/*      */     } else {
/*  204 */       int i = 10;
/*      */       try {
/*  206 */         i = Integer.parseInt(str);
/*  207 */       } catch (NumberFormatException numberFormatException) {
/*  208 */         if (JmxProperties.MONITOR_LOGGER.isLoggable(Level.FINER)) {
/*  209 */           JmxProperties.MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(), "<static initializer>", "Wrong value for jmx.x.monitor.maximum.pool.size system property", numberFormatException);
/*      */ 
/*      */ 
/*      */           
/*  213 */           JmxProperties.MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(), "<static initializer>", "jmx.x.monitor.maximum.pool.size defaults to 10");
/*      */         } 
/*      */ 
/*      */         
/*  217 */         i = 10;
/*      */       } 
/*  219 */       if (i < 1) {
/*  220 */         maximumPoolSize = 1;
/*      */       } else {
/*  222 */         maximumPoolSize = i;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  235 */   private final SchedulerTask schedulerTask = new SchedulerTask();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ScheduledFuture<?> schedulerFuture;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int capacityIncrement = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  259 */   protected int elementCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*  265 */   protected int alreadyNotified = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  278 */   protected int[] alreadyNotifieds = new int[16];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MBeanServer server;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int RESET_FLAGS_ALREADY_NOTIFIED = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int OBSERVED_OBJECT_ERROR_NOTIFIED = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int OBSERVED_ATTRIBUTE_ERROR_NOTIFIED = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int OBSERVED_ATTRIBUTE_TYPE_ERROR_NOTIFIED = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int RUNTIME_ERROR_NOTIFIED = 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*  337 */   protected String dbgTag = Monitor.class
/*  338 */     .getName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  349 */   final List<ObservedObject> observedObjects = new CopyOnWriteArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int THRESHOLD_ERROR_NOTIFIED = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   enum NumericalType
/*      */   {
/*  363 */     BYTE, SHORT, INTEGER, LONG, FLOAT, DOUBLE;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  368 */   static final Integer INTEGER_ZERO = Integer.valueOf(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectName preRegister(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception {
/*  394 */     JmxProperties.MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(), "preRegister(MBeanServer, ObjectName)", "initialize the reference on the MBean server");
/*      */ 
/*      */ 
/*      */     
/*  398 */     this.server = paramMBeanServer;
/*  399 */     return paramObjectName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void postRegister(Boolean paramBoolean) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void preDeregister() throws Exception {
/*  422 */     JmxProperties.MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(), "preDeregister()", "stop the monitor");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  427 */     stop();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void postDeregister() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public synchronized ObjectName getObservedObject() {
/*  464 */     if (this.observedObjects.isEmpty()) {
/*  465 */       return null;
/*      */     }
/*  467 */     return ((ObservedObject)this.observedObjects.get(0)).getObservedObject();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public synchronized void setObservedObject(ObjectName paramObjectName) throws IllegalArgumentException {
/*  486 */     if (paramObjectName == null)
/*  487 */       throw new IllegalArgumentException("Null observed object"); 
/*  488 */     if (this.observedObjects.size() == 1 && containsObservedObject(paramObjectName))
/*      */       return; 
/*  490 */     this.observedObjects.clear();
/*  491 */     addObservedObject(paramObjectName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void addObservedObject(ObjectName paramObjectName) throws IllegalArgumentException {
/*  505 */     if (paramObjectName == null) {
/*  506 */       throw new IllegalArgumentException("Null observed object");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  511 */     if (containsObservedObject(paramObjectName)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  516 */     ObservedObject observedObject = createObservedObject(paramObjectName);
/*  517 */     observedObject.setAlreadyNotified(0);
/*  518 */     observedObject.setDerivedGauge(INTEGER_ZERO);
/*  519 */     observedObject.setDerivedGaugeTimeStamp(System.currentTimeMillis());
/*  520 */     this.observedObjects.add(observedObject);
/*      */ 
/*      */ 
/*      */     
/*  524 */     createAlreadyNotified();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void removeObservedObject(ObjectName paramObjectName) {
/*  536 */     if (paramObjectName == null) {
/*      */       return;
/*      */     }
/*  539 */     ObservedObject observedObject = getObservedObject(paramObjectName);
/*  540 */     if (observedObject != null) {
/*      */ 
/*      */       
/*  543 */       this.observedObjects.remove(observedObject);
/*      */ 
/*      */       
/*  546 */       createAlreadyNotified();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean containsObservedObject(ObjectName paramObjectName) {
/*  559 */     return (getObservedObject(paramObjectName) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized ObjectName[] getObservedObjects() {
/*  569 */     ObjectName[] arrayOfObjectName = new ObjectName[this.observedObjects.size()];
/*  570 */     for (byte b = 0; b < arrayOfObjectName.length; b++)
/*  571 */       arrayOfObjectName[b] = ((ObservedObject)this.observedObjects.get(b)).getObservedObject(); 
/*  572 */     return arrayOfObjectName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized String getObservedAttribute() {
/*  584 */     return this.observedAttribute;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setObservedAttribute(String paramString) throws IllegalArgumentException {
/*  600 */     if (paramString == null) {
/*  601 */       throw new IllegalArgumentException("Null observed attribute");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  606 */     synchronized (this) {
/*  607 */       if (this.observedAttribute != null && this.observedAttribute
/*  608 */         .equals(paramString))
/*      */         return; 
/*  610 */       this.observedAttribute = paramString;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  615 */       cleanupIsComplexTypeAttribute();
/*      */       
/*  617 */       byte b = 0;
/*  618 */       for (ObservedObject observedObject : this.observedObjects) {
/*  619 */         resetAlreadyNotified(observedObject, b++, 6);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized long getGranularityPeriod() {
/*  635 */     return this.granularityPeriod;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setGranularityPeriod(long paramLong) throws IllegalArgumentException {
/*  651 */     if (paramLong <= 0L) {
/*  652 */       throw new IllegalArgumentException("Nonpositive granularity period");
/*      */     }
/*      */ 
/*      */     
/*  656 */     if (this.granularityPeriod == paramLong)
/*      */       return; 
/*  658 */     this.granularityPeriod = paramLong;
/*      */ 
/*      */ 
/*      */     
/*  662 */     if (isActive()) {
/*  663 */       cleanupFutures();
/*  664 */       this.schedulerFuture = scheduler.schedule(this.schedulerTask, paramLong, TimeUnit.MILLISECONDS);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean isActive() {
/*  683 */     return this.isActive;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void doStart() {
/*  696 */     JmxProperties.MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(), "doStart()", "start the monitor");
/*      */ 
/*      */     
/*  699 */     synchronized (this) {
/*  700 */       if (isActive()) {
/*  701 */         JmxProperties.MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(), "doStart()", "the monitor is already active");
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  706 */       this.isActive = true;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  711 */       cleanupIsComplexTypeAttribute();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  716 */       this.acc = AccessController.getContext();
/*      */ 
/*      */ 
/*      */       
/*  720 */       cleanupFutures();
/*  721 */       this.schedulerTask.setMonitorTask(new MonitorTask());
/*  722 */       this.schedulerFuture = scheduler.schedule(this.schedulerTask, 
/*  723 */           getGranularityPeriod(), TimeUnit.MILLISECONDS);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void doStop() {
/*  732 */     JmxProperties.MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(), "doStop()", "stop the monitor");
/*      */ 
/*      */     
/*  735 */     synchronized (this) {
/*  736 */       if (!isActive()) {
/*  737 */         JmxProperties.MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(), "doStop()", "the monitor is not active");
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  742 */       this.isActive = false;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  747 */       cleanupFutures();
/*      */ 
/*      */ 
/*      */       
/*  751 */       this.acc = noPermissionsACC;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  756 */       cleanupIsComplexTypeAttribute();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized Object getDerivedGauge(ObjectName paramObjectName) {
/*  772 */     ObservedObject observedObject = getObservedObject(paramObjectName);
/*  773 */     return (observedObject == null) ? null : observedObject.getDerivedGauge();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized long getDerivedGaugeTimeStamp(ObjectName paramObjectName) {
/*  788 */     ObservedObject observedObject = getObservedObject(paramObjectName);
/*  789 */     return (observedObject == null) ? 0L : observedObject.getDerivedGaugeTimeStamp();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Object getAttribute(MBeanServerConnection paramMBeanServerConnection, ObjectName paramObjectName, String paramString) throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException {
/*      */     boolean bool;
/*      */     MBeanInfo mBeanInfo;
/*      */     String str;
/*  806 */     synchronized (this) {
/*  807 */       if (!isActive()) {
/*  808 */         throw new IllegalArgumentException("The monitor has been stopped");
/*      */       }
/*  810 */       if (!paramString.equals(getObservedAttribute())) {
/*  811 */         throw new IllegalArgumentException("The observed attribute has been changed");
/*      */       }
/*      */       
/*  814 */       bool = (this.firstAttribute == null && paramString.indexOf('.') != -1) ? true : false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  820 */     if (bool) {
/*      */       try {
/*  822 */         mBeanInfo = paramMBeanServerConnection.getMBeanInfo(paramObjectName);
/*  823 */       } catch (IntrospectionException introspectionException) {
/*  824 */         throw new IllegalArgumentException(introspectionException);
/*      */       } 
/*      */     } else {
/*  827 */       mBeanInfo = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  833 */     synchronized (this) {
/*  834 */       if (!isActive()) {
/*  835 */         throw new IllegalArgumentException("The monitor has been stopped");
/*      */       }
/*  837 */       if (!paramString.equals(getObservedAttribute())) {
/*  838 */         throw new IllegalArgumentException("The observed attribute has been changed");
/*      */       }
/*  840 */       if (this.firstAttribute == null) {
/*  841 */         if (paramString.indexOf('.') != -1) {
/*  842 */           MBeanAttributeInfo[] arrayOfMBeanAttributeInfo = mBeanInfo.getAttributes();
/*  843 */           for (MBeanAttributeInfo mBeanAttributeInfo : arrayOfMBeanAttributeInfo) {
/*  844 */             if (paramString.equals(mBeanAttributeInfo.getName())) {
/*  845 */               this.firstAttribute = paramString;
/*      */               break;
/*      */             } 
/*      */           } 
/*  849 */           if (this.firstAttribute == null) {
/*  850 */             String[] arrayOfString = paramString.split("\\.", -1);
/*  851 */             this.firstAttribute = arrayOfString[0];
/*  852 */             for (byte b = 1; b < arrayOfString.length; b++)
/*  853 */               this.remainingAttributes.add(arrayOfString[b]); 
/*  854 */             this.isComplexTypeAttribute = true;
/*      */           } 
/*      */         } else {
/*  857 */           this.firstAttribute = paramString;
/*      */         } 
/*      */       }
/*  860 */       str = this.firstAttribute;
/*      */     } 
/*  862 */     return paramMBeanServerConnection.getAttribute(paramObjectName, str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Comparable<?> getComparableFromAttribute(ObjectName paramObjectName, String paramString, Object paramObject) throws AttributeNotFoundException {
/*  869 */     if (this.isComplexTypeAttribute) {
/*  870 */       Object object = paramObject;
/*  871 */       for (String str : this.remainingAttributes)
/*  872 */         object = Introspector.elementFromComplex(object, str); 
/*  873 */       return (Comparable)object;
/*      */     } 
/*  875 */     return (Comparable)paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isComparableTypeValid(ObjectName paramObjectName, String paramString, Comparable<?> paramComparable) {
/*  882 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   String buildErrorNotification(ObjectName paramObjectName, String paramString, Comparable<?> paramComparable) {
/*  888 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void onErrorNotification(MonitorNotification paramMonitorNotification) {}
/*      */ 
/*      */   
/*      */   Comparable<?> getDerivedGaugeFromComparable(ObjectName paramObjectName, String paramString, Comparable<?> paramComparable) {
/*  897 */     return paramComparable;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   MonitorNotification buildAlarmNotification(ObjectName paramObjectName, String paramString, Comparable<?> paramComparable) {
/*  903 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isThresholdTypeValid(ObjectName paramObjectName, String paramString, Comparable<?> paramComparable) {
/*  909 */     return true;
/*      */   }
/*      */   
/*      */   static Class<? extends Number> classForType(NumericalType paramNumericalType) {
/*  913 */     switch (paramNumericalType) {
/*      */       case BYTE:
/*  915 */         return (Class)Byte.class;
/*      */       case SHORT:
/*  917 */         return (Class)Short.class;
/*      */       case INTEGER:
/*  919 */         return (Class)Integer.class;
/*      */       case LONG:
/*  921 */         return (Class)Long.class;
/*      */       case FLOAT:
/*  923 */         return (Class)Float.class;
/*      */       case DOUBLE:
/*  925 */         return (Class)Double.class;
/*      */     } 
/*  927 */     throw new IllegalArgumentException("Unsupported numerical type");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isValidForType(Object paramObject, Class<? extends Number> paramClass) {
/*  933 */     return (paramObject == INTEGER_ZERO || paramClass.isInstance(paramObject));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized ObservedObject getObservedObject(ObjectName paramObjectName) {
/*  949 */     for (ObservedObject observedObject : this.observedObjects) {
/*  950 */       if (observedObject.getObservedObject().equals(paramObjectName))
/*  951 */         return observedObject; 
/*  952 */     }  return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ObservedObject createObservedObject(ObjectName paramObjectName) {
/*  961 */     return new ObservedObject(paramObjectName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void createAlreadyNotified() {
/*  971 */     this.elementCount = this.observedObjects.size();
/*      */ 
/*      */ 
/*      */     
/*  975 */     this.alreadyNotifieds = new int[this.elementCount];
/*  976 */     for (byte b = 0; b < this.elementCount; b++) {
/*  977 */       this.alreadyNotifieds[b] = ((ObservedObject)this.observedObjects.get(b)).getAlreadyNotified();
/*      */     }
/*  979 */     updateDeprecatedAlreadyNotified();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void updateDeprecatedAlreadyNotified() {
/*  986 */     if (this.elementCount > 0) {
/*  987 */       this.alreadyNotified = this.alreadyNotifieds[0];
/*      */     } else {
/*  989 */       this.alreadyNotified = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void updateAlreadyNotified(ObservedObject paramObservedObject, int paramInt) {
/*  999 */     this.alreadyNotifieds[paramInt] = paramObservedObject.getAlreadyNotified();
/* 1000 */     if (paramInt == 0) {
/* 1001 */       updateDeprecatedAlreadyNotified();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized boolean isAlreadyNotified(ObservedObject paramObservedObject, int paramInt) {
/* 1009 */     return ((paramObservedObject.getAlreadyNotified() & paramInt) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void setAlreadyNotified(ObservedObject paramObservedObject, int paramInt1, int paramInt2, int[] paramArrayOfint) {
/* 1019 */     int i = computeAlreadyNotifiedIndex(paramObservedObject, paramInt1, paramArrayOfint);
/* 1020 */     if (i == -1)
/*      */       return; 
/* 1022 */     paramObservedObject.setAlreadyNotified(paramObservedObject.getAlreadyNotified() | paramInt2);
/* 1023 */     updateAlreadyNotified(paramObservedObject, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void resetAlreadyNotified(ObservedObject paramObservedObject, int paramInt1, int paramInt2) {
/* 1033 */     paramObservedObject.setAlreadyNotified(paramObservedObject.getAlreadyNotified() & (paramInt2 ^ 0xFFFFFFFF));
/* 1034 */     updateAlreadyNotified(paramObservedObject, paramInt1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void resetAllAlreadyNotified(ObservedObject paramObservedObject, int paramInt, int[] paramArrayOfint) {
/* 1044 */     int i = computeAlreadyNotifiedIndex(paramObservedObject, paramInt, paramArrayOfint);
/* 1045 */     if (i == -1)
/*      */       return; 
/* 1047 */     paramObservedObject.setAlreadyNotified(0);
/* 1048 */     updateAlreadyNotified(paramObservedObject, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized int computeAlreadyNotifiedIndex(ObservedObject paramObservedObject, int paramInt, int[] paramArrayOfint) {
/* 1057 */     if (paramArrayOfint == this.alreadyNotifieds) {
/* 1058 */       return paramInt;
/*      */     }
/* 1060 */     return this.observedObjects.indexOf(paramObservedObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sendNotification(String paramString1, long paramLong, String paramString2, Object paramObject1, Object paramObject2, ObjectName paramObjectName, boolean paramBoolean) {
/* 1089 */     if (!isActive()) {
/*      */       return;
/*      */     }
/* 1092 */     if (JmxProperties.MONITOR_LOGGER.isLoggable(Level.FINER)) {
/* 1093 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINER, Monitor.class.getName(), "sendNotification", "send notification: \n\tNotification observed object = " + paramObjectName + "\n\tNotification observed attribute = " + this.observedAttribute + "\n\tNotification derived gauge = " + paramObject1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1100 */     long l = this.sequenceNumber.getAndIncrement();
/*      */     
/* 1102 */     MonitorNotification monitorNotification = new MonitorNotification(paramString1, this, l, paramLong, paramString2, paramObjectName, this.observedAttribute, paramObject1, paramObject2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1112 */     if (paramBoolean)
/* 1113 */       onErrorNotification(monitorNotification); 
/* 1114 */     sendNotification(monitorNotification);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void monitor(ObservedObject paramObservedObject, int paramInt, int[] paramArrayOfint) {
/*      */     String str1;
/*      */     ObjectName objectName;
/* 1125 */     String str2 = null;
/* 1126 */     String str3 = null;
/* 1127 */     Comparable<?> comparable1 = null;
/* 1128 */     Object object = null;
/*      */     
/* 1130 */     Comparable<?> comparable2 = null;
/* 1131 */     MonitorNotification monitorNotification = null;
/*      */     
/* 1133 */     if (!isActive()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1142 */     synchronized (this) {
/* 1143 */       objectName = paramObservedObject.getObservedObject();
/* 1144 */       str1 = getObservedAttribute();
/* 1145 */       if (objectName == null || str1 == null) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1154 */     Object object1 = null;
/*      */     try {
/* 1156 */       object1 = getAttribute(this.server, objectName, str1);
/* 1157 */       if (object1 == null) {
/* 1158 */         if (isAlreadyNotified(paramObservedObject, 4)) {
/*      */           return;
/*      */         }
/*      */         
/* 1162 */         str2 = "jmx.monitor.error.type";
/* 1163 */         setAlreadyNotified(paramObservedObject, paramInt, 4, paramArrayOfint);
/*      */         
/* 1165 */         str3 = "The observed attribute value is null.";
/* 1166 */         JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(), "monitor", str3);
/*      */       }
/*      */     
/* 1169 */     } catch (NullPointerException nullPointerException) {
/* 1170 */       if (isAlreadyNotified(paramObservedObject, 8)) {
/*      */         return;
/*      */       }
/* 1173 */       str2 = "jmx.monitor.error.runtime";
/* 1174 */       setAlreadyNotified(paramObservedObject, paramInt, 8, paramArrayOfint);
/* 1175 */       str3 = "The monitor must be registered in the MBean server or an MBeanServerConnection must be explicitly supplied.";
/*      */ 
/*      */ 
/*      */       
/* 1179 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(), "monitor", str3);
/*      */       
/* 1181 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(), "monitor", nullPointerException
/* 1182 */           .toString());
/*      */     }
/* 1184 */     catch (InstanceNotFoundException instanceNotFoundException) {
/* 1185 */       if (isAlreadyNotified(paramObservedObject, 1)) {
/*      */         return;
/*      */       }
/* 1188 */       str2 = "jmx.monitor.error.mbean";
/* 1189 */       setAlreadyNotified(paramObservedObject, paramInt, 1, paramArrayOfint);
/*      */       
/* 1191 */       str3 = "The observed object must be accessible in the MBeanServerConnection.";
/*      */ 
/*      */       
/* 1194 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(), "monitor", str3);
/*      */       
/* 1196 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(), "monitor", instanceNotFoundException
/* 1197 */           .toString());
/*      */     }
/* 1199 */     catch (AttributeNotFoundException attributeNotFoundException) {
/* 1200 */       if (isAlreadyNotified(paramObservedObject, 2)) {
/*      */         return;
/*      */       }
/* 1203 */       str2 = "jmx.monitor.error.attribute";
/* 1204 */       setAlreadyNotified(paramObservedObject, paramInt, 2, paramArrayOfint);
/*      */       
/* 1206 */       str3 = "The observed attribute must be accessible in the observed object.";
/*      */ 
/*      */       
/* 1209 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(), "monitor", str3);
/*      */       
/* 1211 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(), "monitor", attributeNotFoundException
/* 1212 */           .toString());
/*      */     }
/* 1214 */     catch (MBeanException mBeanException) {
/* 1215 */       if (isAlreadyNotified(paramObservedObject, 8)) {
/*      */         return;
/*      */       }
/* 1218 */       str2 = "jmx.monitor.error.runtime";
/* 1219 */       setAlreadyNotified(paramObservedObject, paramInt, 8, paramArrayOfint);
/* 1220 */       str3 = (mBeanException.getMessage() == null) ? "" : mBeanException.getMessage();
/* 1221 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(), "monitor", str3);
/*      */       
/* 1223 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(), "monitor", mBeanException
/* 1224 */           .toString());
/*      */     }
/* 1226 */     catch (ReflectionException reflectionException) {
/* 1227 */       if (isAlreadyNotified(paramObservedObject, 8)) {
/*      */         return;
/*      */       }
/* 1230 */       str2 = "jmx.monitor.error.runtime";
/* 1231 */       setAlreadyNotified(paramObservedObject, paramInt, 8, paramArrayOfint);
/* 1232 */       str3 = (reflectionException.getMessage() == null) ? "" : reflectionException.getMessage();
/* 1233 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(), "monitor", str3);
/*      */       
/* 1235 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(), "monitor", reflectionException
/* 1236 */           .toString());
/*      */     }
/* 1238 */     catch (IOException iOException) {
/* 1239 */       if (isAlreadyNotified(paramObservedObject, 8)) {
/*      */         return;
/*      */       }
/* 1242 */       str2 = "jmx.monitor.error.runtime";
/* 1243 */       setAlreadyNotified(paramObservedObject, paramInt, 8, paramArrayOfint);
/* 1244 */       str3 = (iOException.getMessage() == null) ? "" : iOException.getMessage();
/* 1245 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(), "monitor", str3);
/*      */       
/* 1247 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(), "monitor", iOException
/* 1248 */           .toString());
/*      */     }
/* 1250 */     catch (RuntimeException runtimeException) {
/* 1251 */       if (isAlreadyNotified(paramObservedObject, 8)) {
/*      */         return;
/*      */       }
/* 1254 */       str2 = "jmx.monitor.error.runtime";
/* 1255 */       setAlreadyNotified(paramObservedObject, paramInt, 8, paramArrayOfint);
/* 1256 */       str3 = (runtimeException.getMessage() == null) ? "" : runtimeException.getMessage();
/* 1257 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(), "monitor", str3);
/*      */       
/* 1259 */       JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class.getName(), "monitor", runtimeException
/* 1260 */           .toString());
/*      */     } 
/*      */ 
/*      */     
/* 1264 */     synchronized (this) {
/*      */ 
/*      */ 
/*      */       
/* 1268 */       if (!isActive()) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1278 */       if (!str1.equals(getObservedAttribute())) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1284 */       if (str3 == null) {
/*      */         try {
/* 1286 */           comparable2 = getComparableFromAttribute(objectName, str1, object1);
/*      */         
/*      */         }
/* 1289 */         catch (ClassCastException classCastException) {
/* 1290 */           if (isAlreadyNotified(paramObservedObject, 4)) {
/*      */             return;
/*      */           }
/*      */           
/* 1294 */           str2 = "jmx.monitor.error.type";
/* 1295 */           setAlreadyNotified(paramObservedObject, paramInt, 4, paramArrayOfint);
/*      */           
/* 1297 */           str3 = "The observed attribute value does not implement the Comparable interface.";
/*      */ 
/*      */           
/* 1300 */           JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class
/* 1301 */               .getName(), "monitor", str3);
/* 1302 */           JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class
/* 1303 */               .getName(), "monitor", classCastException.toString());
/*      */         }
/* 1305 */         catch (AttributeNotFoundException attributeNotFoundException) {
/* 1306 */           if (isAlreadyNotified(paramObservedObject, 2)) {
/*      */             return;
/*      */           }
/* 1309 */           str2 = "jmx.monitor.error.attribute";
/* 1310 */           setAlreadyNotified(paramObservedObject, paramInt, 2, paramArrayOfint);
/*      */           
/* 1312 */           str3 = "The observed attribute must be accessible in the observed object.";
/*      */ 
/*      */           
/* 1315 */           JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class
/* 1316 */               .getName(), "monitor", str3);
/* 1317 */           JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class
/* 1318 */               .getName(), "monitor", attributeNotFoundException.toString());
/*      */         }
/* 1320 */         catch (RuntimeException runtimeException) {
/* 1321 */           if (isAlreadyNotified(paramObservedObject, 8)) {
/*      */             return;
/*      */           }
/* 1324 */           str2 = "jmx.monitor.error.runtime";
/* 1325 */           setAlreadyNotified(paramObservedObject, paramInt, 8, paramArrayOfint);
/*      */           
/* 1327 */           str3 = (runtimeException.getMessage() == null) ? "" : runtimeException.getMessage();
/* 1328 */           JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class
/* 1329 */               .getName(), "monitor", str3);
/* 1330 */           JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class
/* 1331 */               .getName(), "monitor", runtimeException.toString());
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1339 */       if (str3 == null && 
/* 1340 */         !isComparableTypeValid(objectName, str1, comparable2)) {
/* 1341 */         if (isAlreadyNotified(paramObservedObject, 4)) {
/*      */           return;
/*      */         }
/*      */         
/* 1345 */         str2 = "jmx.monitor.error.type";
/* 1346 */         setAlreadyNotified(paramObservedObject, paramInt, 4, paramArrayOfint);
/*      */         
/* 1348 */         str3 = "The observed attribute type is not valid.";
/* 1349 */         JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class
/* 1350 */             .getName(), "monitor", str3);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1357 */       if (str3 == null && 
/* 1358 */         !isThresholdTypeValid(objectName, str1, comparable2)) {
/* 1359 */         if (isAlreadyNotified(paramObservedObject, 16)) {
/*      */           return;
/*      */         }
/* 1362 */         str2 = "jmx.monitor.error.threshold";
/* 1363 */         setAlreadyNotified(paramObservedObject, paramInt, 16, paramArrayOfint);
/*      */         
/* 1365 */         str3 = "The threshold type is not valid.";
/* 1366 */         JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class
/* 1367 */             .getName(), "monitor", str3);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1375 */       if (str3 == null) {
/* 1376 */         str3 = buildErrorNotification(objectName, str1, comparable2);
/* 1377 */         if (str3 != null) {
/* 1378 */           if (isAlreadyNotified(paramObservedObject, 8)) {
/*      */             return;
/*      */           }
/* 1381 */           str2 = "jmx.monitor.error.runtime";
/* 1382 */           setAlreadyNotified(paramObservedObject, paramInt, 8, paramArrayOfint);
/*      */           
/* 1384 */           JmxProperties.MONITOR_LOGGER.logp(Level.FINEST, Monitor.class
/* 1385 */               .getName(), "monitor", str3);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1393 */       if (str3 == null) {
/*      */ 
/*      */         
/* 1396 */         resetAllAlreadyNotified(paramObservedObject, paramInt, paramArrayOfint);
/*      */ 
/*      */ 
/*      */         
/* 1400 */         comparable1 = getDerivedGaugeFromComparable(objectName, str1, comparable2);
/*      */ 
/*      */ 
/*      */         
/* 1404 */         paramObservedObject.setDerivedGauge(comparable1);
/* 1405 */         paramObservedObject.setDerivedGaugeTimeStamp(System.currentTimeMillis());
/*      */ 
/*      */ 
/*      */         
/* 1409 */         monitorNotification = buildAlarmNotification(objectName, str1, comparable1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1418 */     if (str3 != null) {
/* 1419 */       sendNotification(str2, 
/* 1420 */           System.currentTimeMillis(), str3, comparable1, object, objectName, true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1429 */     if (monitorNotification != null && monitorNotification.getType() != null) {
/* 1430 */       sendNotification(monitorNotification.getType(), 
/* 1431 */           System.currentTimeMillis(), monitorNotification
/* 1432 */           .getMessage(), comparable1, monitorNotification
/*      */           
/* 1434 */           .getTrigger(), objectName, false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void cleanupFutures() {
/* 1443 */     if (this.schedulerFuture != null) {
/* 1444 */       this.schedulerFuture.cancel(false);
/* 1445 */       this.schedulerFuture = null;
/*      */     } 
/* 1447 */     if (this.monitorFuture != null) {
/* 1448 */       this.monitorFuture.cancel(false);
/* 1449 */       this.monitorFuture = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void cleanupIsComplexTypeAttribute() {
/* 1457 */     this.firstAttribute = null;
/* 1458 */     this.remainingAttributes.clear();
/* 1459 */     this.isComplexTypeAttribute = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void start();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void stop();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class SchedulerTask
/*      */     implements Runnable
/*      */   {
/*      */     private Monitor.MonitorTask task;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setMonitorTask(Monitor.MonitorTask param1MonitorTask) {
/* 1488 */       this.task = param1MonitorTask;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void run() {
/* 1498 */       synchronized (Monitor.this) {
/* 1499 */         Monitor.this.monitorFuture = this.task.submit();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MonitorTask
/*      */     implements Runnable
/*      */   {
/*      */     private ThreadPoolExecutor executor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MonitorTask() {
/* 1527 */       SecurityManager securityManager = System.getSecurityManager();
/*      */       
/* 1529 */       ThreadGroup threadGroup = (securityManager != null) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
/* 1530 */       synchronized (Monitor.executorsLock) {
/* 1531 */         for (ThreadPoolExecutor threadPoolExecutor : Monitor.executors.keySet()) {
/*      */           
/* 1533 */           Monitor.DaemonThreadFactory daemonThreadFactory = (Monitor.DaemonThreadFactory)threadPoolExecutor.getThreadFactory();
/* 1534 */           ThreadGroup threadGroup1 = daemonThreadFactory.getThreadGroup();
/* 1535 */           if (threadGroup1 == threadGroup) {
/* 1536 */             this.executor = threadPoolExecutor;
/*      */             break;
/*      */           } 
/*      */         } 
/* 1540 */         if (this.executor == null) {
/* 1541 */           this
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1548 */             .executor = new ThreadPoolExecutor(Monitor.maximumPoolSize, Monitor.maximumPoolSize, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new Monitor.DaemonThreadFactory("ThreadGroup<" + threadGroup.getName() + "> Executor", threadGroup));
/* 1549 */           this.executor.allowCoreThreadTimeOut(true);
/* 1550 */           Monitor.executors.put(this.executor, null);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Future<?> submit() {
/* 1562 */       return this.executor.submit(this);
/*      */     }
/*      */     
/*      */     public void run() {
/*      */       ScheduledFuture scheduledFuture;
/*      */       AccessControlContext accessControlContext;
/* 1568 */       synchronized (Monitor.this) {
/* 1569 */         scheduledFuture = Monitor.this.schedulerFuture;
/* 1570 */         accessControlContext = Monitor.this.acc;
/*      */       } 
/* 1572 */       PrivilegedAction<Void> privilegedAction = new PrivilegedAction<Void>() {
/*      */           public Void run() {
/* 1574 */             if (Monitor.this.isActive()) {
/* 1575 */               int[] arrayOfInt = Monitor.this.alreadyNotifieds;
/* 1576 */               byte b = 0;
/* 1577 */               for (Monitor.ObservedObject observedObject : Monitor.this.observedObjects) {
/* 1578 */                 if (Monitor.this.isActive()) {
/* 1579 */                   Monitor.this.monitor(observedObject, b++, arrayOfInt);
/*      */                 }
/*      */               } 
/*      */             } 
/* 1583 */             return null;
/*      */           }
/*      */         };
/* 1586 */       if (accessControlContext == null) {
/* 1587 */         throw new SecurityException("AccessControlContext cannot be null");
/*      */       }
/* 1589 */       AccessController.doPrivileged(privilegedAction, accessControlContext);
/* 1590 */       synchronized (Monitor.this) {
/* 1591 */         if (Monitor.this.isActive() && Monitor.this
/* 1592 */           .schedulerFuture == scheduledFuture) {
/* 1593 */           Monitor.this.monitorFuture = null;
/* 1594 */           Monitor.this.schedulerFuture = Monitor
/* 1595 */             .scheduler.schedule(Monitor.this.schedulerTask, Monitor.this
/* 1596 */               .getGranularityPeriod(), TimeUnit.MILLISECONDS);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class DaemonThreadFactory
/*      */     implements ThreadFactory
/*      */   {
/*      */     final ThreadGroup group;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1618 */     final AtomicInteger threadNumber = new AtomicInteger(1);
/*      */     final String namePrefix;
/*      */     static final String nameSuffix = "]";
/*      */     
/*      */     public DaemonThreadFactory(String param1String) {
/* 1623 */       SecurityManager securityManager = System.getSecurityManager();
/* 1624 */       this
/* 1625 */         .group = (securityManager != null) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
/* 1626 */       this.namePrefix = "JMX Monitor " + param1String + " Pool [Thread-";
/*      */     }
/*      */     
/*      */     public DaemonThreadFactory(String param1String, ThreadGroup param1ThreadGroup) {
/* 1630 */       this.group = param1ThreadGroup;
/* 1631 */       this.namePrefix = "JMX Monitor " + param1String + " Pool [Thread-";
/*      */     }
/*      */     
/*      */     public ThreadGroup getThreadGroup() {
/* 1635 */       return this.group;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Thread newThread(Runnable param1Runnable) {
/* 1642 */       Thread thread = new Thread(this.group, param1Runnable, this.namePrefix + this.threadNumber.getAndIncrement() + "]", 0L);
/*      */ 
/*      */       
/* 1645 */       thread.setDaemon(true);
/* 1646 */       if (thread.getPriority() != 5)
/* 1647 */         thread.setPriority(5); 
/* 1648 */       return thread;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/monitor/Monitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */