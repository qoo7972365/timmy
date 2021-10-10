/*      */ package javax.management.timer;
/*      */ 
/*      */ import com.sun.jmx.defaults.JmxProperties;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.TreeSet;
/*      */ import java.util.Vector;
/*      */ import java.util.logging.Level;
/*      */ import javax.management.InstanceNotFoundException;
/*      */ import javax.management.MBeanNotificationInfo;
/*      */ import javax.management.MBeanRegistration;
/*      */ import javax.management.MBeanServer;
/*      */ import javax.management.NotificationBroadcasterSupport;
/*      */ import javax.management.ObjectName;
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
/*      */ public class Timer
/*      */   extends NotificationBroadcasterSupport
/*      */   implements TimerMBean, MBeanRegistration
/*      */ {
/*      */   public static final long ONE_SECOND = 1000L;
/*      */   public static final long ONE_MINUTE = 60000L;
/*      */   public static final long ONE_HOUR = 3600000L;
/*      */   public static final long ONE_DAY = 86400000L;
/*      */   public static final long ONE_WEEK = 604800000L;
/*  130 */   private final Map<Integer, Object[]> timerTable = (Map)new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sendPastNotifications = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean isActive = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  150 */   private transient long sequenceNumber = 0L;
/*      */ 
/*      */   
/*      */   private static final int TIMER_NOTIF_INDEX = 0;
/*      */   
/*      */   private static final int TIMER_DATE_INDEX = 1;
/*      */   
/*      */   private static final int TIMER_PERIOD_INDEX = 2;
/*      */   
/*      */   private static final int TIMER_NB_OCCUR_INDEX = 3;
/*      */   
/*      */   private static final int ALARM_CLOCK_INDEX = 4;
/*      */   
/*      */   private static final int FIXED_RATE_INDEX = 5;
/*      */   
/*  165 */   private volatile int counterID = 0;
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
/*      */   private java.util.Timer timer;
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
/*  202 */     return paramObjectName;
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
/*  224 */     JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "preDeregister", "stop the timer");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  229 */     stop();
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
/*      */   public void postDeregister() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized MBeanNotificationInfo[] getNotificationInfo() {
/*  250 */     TreeSet<String> treeSet = new TreeSet();
/*  251 */     for (Object[] arrayOfObject : this.timerTable.values()) {
/*  252 */       TimerNotification timerNotification = (TimerNotification)arrayOfObject[0];
/*      */       
/*  254 */       treeSet.add(timerNotification.getType());
/*      */     } 
/*      */     
/*  257 */     String[] arrayOfString = treeSet.<String>toArray(new String[0]);
/*  258 */     return new MBeanNotificationInfo[] { new MBeanNotificationInfo(arrayOfString, TimerNotification.class
/*      */           
/*  260 */           .getName(), "Notification sent by Timer MBean") };
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
/*      */   public synchronized void start() {
/*  276 */     JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "start", "starting the timer");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  281 */     if (!this.isActive) {
/*      */       
/*  283 */       this.timer = new java.util.Timer();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  288 */       Date date = new Date();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  294 */       sendPastNotifications(date, this.sendPastNotifications);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  299 */       for (Object[] arrayOfObject : this.timerTable.values()) {
/*      */ 
/*      */ 
/*      */         
/*  303 */         Date date1 = (Date)arrayOfObject[1];
/*      */ 
/*      */ 
/*      */         
/*  307 */         boolean bool = ((Boolean)arrayOfObject[5]).booleanValue();
/*  308 */         if (bool) {
/*      */           
/*  310 */           TimerAlarmClock timerAlarmClock1 = new TimerAlarmClock(this, date1);
/*  311 */           arrayOfObject[4] = timerAlarmClock1;
/*  312 */           this.timer.schedule(timerAlarmClock1, timerAlarmClock1.next);
/*      */           
/*      */           continue;
/*      */         } 
/*  316 */         TimerAlarmClock timerAlarmClock = new TimerAlarmClock(this, date1.getTime() - date.getTime());
/*  317 */         arrayOfObject[4] = timerAlarmClock;
/*  318 */         this.timer.schedule(timerAlarmClock, timerAlarmClock.timeout);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  324 */       this.isActive = true;
/*      */       
/*  326 */       JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "start", "timer started");
/*      */     } else {
/*      */       
/*  329 */       JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "start", "the timer is already activated");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void stop() {
/*  339 */     JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "stop", "stopping the timer");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  344 */     if (this.isActive == true) {
/*      */       
/*  346 */       for (Object[] arrayOfObject : this.timerTable.values()) {
/*      */ 
/*      */ 
/*      */         
/*  350 */         TimerAlarmClock timerAlarmClock = (TimerAlarmClock)arrayOfObject[4];
/*  351 */         if (timerAlarmClock != null)
/*      */         {
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
/*  363 */           timerAlarmClock.cancel();
/*      */         }
/*      */       } 
/*      */       
/*  367 */       this.timer.cancel();
/*      */ 
/*      */ 
/*      */       
/*  371 */       this.isActive = false;
/*      */       
/*  373 */       JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "stop", "timer stopped");
/*      */     } else {
/*      */       
/*  376 */       JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "stop", "the timer is already deactivated");
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
/*      */   public synchronized Integer addNotification(String paramString1, String paramString2, Object paramObject, Date paramDate, long paramLong1, long paramLong2, boolean paramBoolean) throws IllegalArgumentException {
/*      */     TimerAlarmClock timerAlarmClock;
/*  426 */     if (paramDate == null) {
/*  427 */       throw new IllegalArgumentException("Timer notification date cannot be null.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  436 */     if (paramLong1 < 0L || paramLong2 < 0L) {
/*  437 */       throw new IllegalArgumentException("Negative values for the periodicity");
/*      */     }
/*      */     
/*  440 */     Date date1 = new Date();
/*      */ 
/*      */ 
/*      */     
/*  444 */     if (date1.after(paramDate)) {
/*      */       
/*  446 */       paramDate.setTime(date1.getTime());
/*  447 */       if (JmxProperties.TIMER_LOGGER.isLoggable(Level.FINER)) {
/*  448 */         JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "addNotification", "update timer notification to add with:\n\tNotification date = " + paramDate);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  457 */     Integer integer = Integer.valueOf(++this.counterID);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  462 */     TimerNotification timerNotification = new TimerNotification(paramString1, this, 0L, 0L, paramString2, integer);
/*  463 */     timerNotification.setUserData(paramObject);
/*      */     
/*  465 */     Object[] arrayOfObject = new Object[6];
/*      */ 
/*      */     
/*  468 */     if (paramBoolean) {
/*      */       
/*  470 */       timerAlarmClock = new TimerAlarmClock(this, paramDate);
/*      */     }
/*      */     else {
/*      */       
/*  474 */       timerAlarmClock = new TimerAlarmClock(this, paramDate.getTime() - date1.getTime());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  480 */     Date date2 = new Date(paramDate.getTime());
/*      */     
/*  482 */     arrayOfObject[0] = timerNotification;
/*  483 */     arrayOfObject[1] = date2;
/*  484 */     arrayOfObject[2] = Long.valueOf(paramLong1);
/*  485 */     arrayOfObject[3] = Long.valueOf(paramLong2);
/*  486 */     arrayOfObject[4] = timerAlarmClock;
/*  487 */     arrayOfObject[5] = Boolean.valueOf(paramBoolean);
/*      */     
/*  489 */     if (JmxProperties.TIMER_LOGGER.isLoggable(Level.FINER)) {
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
/*  505 */       StringBuilder stringBuilder = (new StringBuilder()).append("adding timer notification:\n\t").append("Notification source = ").append(timerNotification.getSource()).append("\n\tNotification type = ").append(timerNotification.getType()).append("\n\tNotification ID = ").append(integer).append("\n\tNotification date = ").append(date2).append("\n\tNotification period = ").append(paramLong1).append("\n\tNotification nb of occurrences = ").append(paramLong2).append("\n\tNotification executes at fixed rate = ").append(paramBoolean);
/*  506 */       JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "addNotification", stringBuilder
/*  507 */           .toString());
/*      */     } 
/*      */     
/*  510 */     this.timerTable.put(integer, arrayOfObject);
/*      */ 
/*      */ 
/*      */     
/*  514 */     if (this.isActive == true) {
/*  515 */       if (paramBoolean) {
/*      */         
/*  517 */         this.timer.schedule(timerAlarmClock, timerAlarmClock.next);
/*      */       }
/*      */       else {
/*      */         
/*  521 */         this.timer.schedule(timerAlarmClock, timerAlarmClock.timeout);
/*      */       } 
/*      */     }
/*      */     
/*  525 */     JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "addNotification", "timer notification added");
/*      */     
/*  527 */     return integer;
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
/*      */   public synchronized Integer addNotification(String paramString1, String paramString2, Object paramObject, Date paramDate, long paramLong1, long paramLong2) throws IllegalArgumentException {
/*  571 */     return addNotification(paramString1, paramString2, paramObject, paramDate, paramLong1, paramLong2, false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Integer addNotification(String paramString1, String paramString2, Object paramObject, Date paramDate, long paramLong) throws IllegalArgumentException {
/*  609 */     return addNotification(paramString1, paramString2, paramObject, paramDate, paramLong, 0L);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Integer addNotification(String paramString1, String paramString2, Object paramObject, Date paramDate) throws IllegalArgumentException {
/*  641 */     return addNotification(paramString1, paramString2, paramObject, paramDate, 0L, 0L);
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
/*      */   public synchronized void removeNotification(Integer paramInteger) throws InstanceNotFoundException {
/*  656 */     if (!this.timerTable.containsKey(paramInteger)) {
/*  657 */       throw new InstanceNotFoundException("Timer notification to remove not in the list of notifications");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  662 */     Object[] arrayOfObject = this.timerTable.get(paramInteger);
/*  663 */     TimerAlarmClock timerAlarmClock = (TimerAlarmClock)arrayOfObject[4];
/*  664 */     if (timerAlarmClock != null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  675 */       timerAlarmClock.cancel();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  680 */     if (JmxProperties.TIMER_LOGGER.isLoggable(Level.FINER)) {
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
/*  696 */       StringBuilder stringBuilder = (new StringBuilder()).append("removing timer notification:").append("\n\tNotification source = ").append(((TimerNotification)arrayOfObject[0]).getSource()).append("\n\tNotification type = ").append(((TimerNotification)arrayOfObject[0]).getType()).append("\n\tNotification ID = ").append(((TimerNotification)arrayOfObject[0]).getNotificationID()).append("\n\tNotification date = ").append(arrayOfObject[1]).append("\n\tNotification period = ").append(arrayOfObject[2]).append("\n\tNotification nb of occurrences = ").append(arrayOfObject[3]).append("\n\tNotification executes at fixed rate = ").append(arrayOfObject[5]);
/*  697 */       JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "removeNotification", stringBuilder
/*  698 */           .toString());
/*      */     } 
/*      */     
/*  701 */     this.timerTable.remove(paramInteger);
/*      */     
/*  703 */     JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "removeNotification", "timer notification removed");
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
/*      */   public synchronized void removeNotifications(String paramString) throws InstanceNotFoundException {
/*  717 */     Vector<Integer> vector = getNotificationIDs(paramString);
/*      */     
/*  719 */     if (vector.isEmpty()) {
/*  720 */       throw new InstanceNotFoundException("Timer notifications to remove not in the list of notifications");
/*      */     }
/*  722 */     for (Integer integer : vector) {
/*  723 */       removeNotification(integer);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void removeAllNotifications() {
/*  734 */     for (Object[] arrayOfObject : this.timerTable.values()) {
/*      */ 
/*      */ 
/*      */       
/*  738 */       TimerAlarmClock timerAlarmClock = (TimerAlarmClock)arrayOfObject[4];
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
/*  751 */       timerAlarmClock.cancel();
/*      */     } 
/*      */ 
/*      */     
/*  755 */     JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "removeAllNotifications", "removing all timer notifications");
/*      */ 
/*      */     
/*  758 */     this.timerTable.clear();
/*      */     
/*  760 */     JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "removeAllNotifications", "all timer notifications removed");
/*      */ 
/*      */ 
/*      */     
/*  764 */     this.counterID = 0;
/*      */     
/*  766 */     JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "removeAllNotifications", "timer notification counter ID reset");
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
/*      */   public synchronized int getNbNotifications() {
/*  779 */     return this.timerTable.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Vector<Integer> getAllNotificationIDs() {
/*  789 */     return new Vector<>(this.timerTable.keySet());
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
/*      */   public synchronized Vector<Integer> getNotificationIDs(String paramString) {
/*  806 */     Vector<Integer> vector = new Vector();
/*      */     
/*  808 */     for (Map.Entry<Integer, Object> entry : this.timerTable.entrySet()) {
/*  809 */       Object[] arrayOfObject = (Object[])entry.getValue();
/*  810 */       String str = ((TimerNotification)arrayOfObject[0]).getType();
/*  811 */       if ((paramString == null) ? (str == null) : paramString.equals(str))
/*  812 */         vector.addElement(entry.getKey()); 
/*      */     } 
/*  814 */     return vector;
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
/*      */   public synchronized String getNotificationType(Integer paramInteger) {
/*  828 */     Object[] arrayOfObject = this.timerTable.get(paramInteger);
/*  829 */     if (arrayOfObject != null) {
/*  830 */       return ((TimerNotification)arrayOfObject[0]).getType();
/*      */     }
/*  832 */     return null;
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
/*      */   public synchronized String getNotificationMessage(Integer paramInteger) {
/*  845 */     Object[] arrayOfObject = this.timerTable.get(paramInteger);
/*  846 */     if (arrayOfObject != null) {
/*  847 */       return ((TimerNotification)arrayOfObject[0]).getMessage();
/*      */     }
/*  849 */     return null;
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
/*      */   public synchronized Object getNotificationUserData(Integer paramInteger) {
/*  865 */     Object[] arrayOfObject = this.timerTable.get(paramInteger);
/*  866 */     if (arrayOfObject != null) {
/*  867 */       return ((TimerNotification)arrayOfObject[0]).getUserData();
/*      */     }
/*  869 */     return null;
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
/*      */   public synchronized Date getDate(Integer paramInteger) {
/*  882 */     Object[] arrayOfObject = this.timerTable.get(paramInteger);
/*  883 */     if (arrayOfObject != null) {
/*  884 */       Date date = (Date)arrayOfObject[1];
/*  885 */       return new Date(date.getTime());
/*      */     } 
/*  887 */     return null;
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
/*      */   public synchronized Long getPeriod(Integer paramInteger) {
/*  900 */     Object[] arrayOfObject = this.timerTable.get(paramInteger);
/*  901 */     if (arrayOfObject != null) {
/*  902 */       return (Long)arrayOfObject[2];
/*      */     }
/*  904 */     return null;
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
/*      */   public synchronized Long getNbOccurences(Integer paramInteger) {
/*  917 */     Object[] arrayOfObject = this.timerTable.get(paramInteger);
/*  918 */     if (arrayOfObject != null) {
/*  919 */       return (Long)arrayOfObject[3];
/*      */     }
/*  921 */     return null;
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
/*      */   public synchronized Boolean getFixedRate(Integer paramInteger) {
/*  935 */     Object[] arrayOfObject = this.timerTable.get(paramInteger);
/*  936 */     if (arrayOfObject != null) {
/*  937 */       Boolean bool = (Boolean)arrayOfObject[5];
/*  938 */       return Boolean.valueOf(bool.booleanValue());
/*      */     } 
/*  940 */     return null;
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
/*      */   public boolean getSendPastNotifications() {
/*  952 */     return this.sendPastNotifications;
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
/*      */   public void setSendPastNotifications(boolean paramBoolean) {
/*  964 */     this.sendPastNotifications = paramBoolean;
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
/*      */   public boolean isActive() {
/*  976 */     return this.isActive;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean isEmpty() {
/*  985 */     return this.timerTable.isEmpty();
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
/*      */   private synchronized void sendPastNotifications(Date paramDate, boolean paramBoolean) {
/* 1007 */     ArrayList arrayList = new ArrayList(this.timerTable.values());
/*      */     
/* 1009 */     for (Object[] arrayOfObject : arrayList) {
/*      */ 
/*      */ 
/*      */       
/* 1013 */       TimerNotification timerNotification = (TimerNotification)arrayOfObject[0];
/* 1014 */       Integer integer = timerNotification.getNotificationID();
/* 1015 */       Date date = (Date)arrayOfObject[1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1021 */       while (paramDate.after(date) && this.timerTable.containsKey(integer)) {
/*      */         
/* 1023 */         if (paramBoolean == true) {
/* 1024 */           if (JmxProperties.TIMER_LOGGER.isLoggable(Level.FINER)) {
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
/* 1040 */             StringBuilder stringBuilder = (new StringBuilder()).append("sending past timer notification:").append("\n\tNotification source = ").append(timerNotification.getSource()).append("\n\tNotification type = ").append(timerNotification.getType()).append("\n\tNotification ID = ").append(timerNotification.getNotificationID()).append("\n\tNotification date = ").append(date).append("\n\tNotification period = ").append(arrayOfObject[2]).append("\n\tNotification nb of occurrences = ").append(arrayOfObject[3]).append("\n\tNotification executes at fixed rate = ").append(arrayOfObject[5]);
/* 1041 */             JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "sendPastNotifications", stringBuilder
/* 1042 */                 .toString());
/*      */           } 
/* 1044 */           sendNotification(date, timerNotification);
/*      */           
/* 1046 */           JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "sendPastNotifications", "past timer notification sent");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1052 */         updateTimerTable(timerNotification.getNotificationID());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void updateTimerTable(Integer paramInteger) {
/* 1074 */     Object[] arrayOfObject = this.timerTable.get(paramInteger);
/* 1075 */     Date date = (Date)arrayOfObject[1];
/* 1076 */     Long long_1 = (Long)arrayOfObject[2];
/* 1077 */     Long long_2 = (Long)arrayOfObject[3];
/* 1078 */     Boolean bool = (Boolean)arrayOfObject[5];
/* 1079 */     TimerAlarmClock timerAlarmClock = (TimerAlarmClock)arrayOfObject[4];
/*      */     
/* 1081 */     if (long_1.longValue() != 0L) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1090 */       if (long_2.longValue() == 0L || long_2.longValue() > 1L) {
/*      */         
/* 1092 */         date.setTime(date.getTime() + long_1.longValue());
/* 1093 */         arrayOfObject[3] = Long.valueOf(Math.max(0L, long_2.longValue() - 1L));
/* 1094 */         long_2 = (Long)arrayOfObject[3];
/*      */         
/* 1096 */         if (this.isActive == true) {
/* 1097 */           if (bool.booleanValue()) {
/*      */             
/* 1099 */             timerAlarmClock = new TimerAlarmClock(this, date);
/* 1100 */             arrayOfObject[4] = timerAlarmClock;
/* 1101 */             this.timer.schedule(timerAlarmClock, timerAlarmClock.next);
/*      */           }
/*      */           else {
/*      */             
/* 1105 */             timerAlarmClock = new TimerAlarmClock(this, long_1.longValue());
/* 1106 */             arrayOfObject[4] = timerAlarmClock;
/* 1107 */             this.timer.schedule(timerAlarmClock, timerAlarmClock.timeout);
/*      */           } 
/*      */         }
/* 1110 */         if (JmxProperties.TIMER_LOGGER.isLoggable(Level.FINER)) {
/* 1111 */           TimerNotification timerNotification = (TimerNotification)arrayOfObject[0];
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
/* 1127 */           StringBuilder stringBuilder = (new StringBuilder()).append("update timer notification with:").append("\n\tNotification source = ").append(timerNotification.getSource()).append("\n\tNotification type = ").append(timerNotification.getType()).append("\n\tNotification ID = ").append(paramInteger).append("\n\tNotification date = ").append(date).append("\n\tNotification period = ").append(long_1).append("\n\tNotification nb of occurrences = ").append(long_2).append("\n\tNotification executes at fixed rate = ").append(bool);
/* 1128 */           JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "updateTimerTable", stringBuilder
/* 1129 */               .toString());
/*      */         } 
/*      */       } else {
/*      */         
/* 1133 */         if (timerAlarmClock != null)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1142 */           timerAlarmClock.cancel();
/*      */         }
/* 1144 */         this.timerTable.remove(paramInteger);
/*      */       } 
/*      */     } else {
/*      */       
/* 1148 */       if (timerAlarmClock != null)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1158 */         timerAlarmClock.cancel();
/*      */       }
/* 1160 */       this.timerTable.remove(paramInteger);
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
/*      */   void notifyAlarmClock(TimerAlarmClockNotification paramTimerAlarmClockNotification) {
/* 1179 */     TimerNotification timerNotification = null;
/* 1180 */     Date date = null;
/*      */ 
/*      */ 
/*      */     
/* 1184 */     TimerAlarmClock timerAlarmClock = (TimerAlarmClock)paramTimerAlarmClockNotification.getSource();
/*      */     
/* 1186 */     synchronized (this) {
/* 1187 */       for (Object[] arrayOfObject : this.timerTable.values()) {
/* 1188 */         if (arrayOfObject[4] == timerAlarmClock) {
/* 1189 */           timerNotification = (TimerNotification)arrayOfObject[0];
/* 1190 */           date = (Date)arrayOfObject[1];
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1198 */     sendNotification(date, timerNotification);
/*      */ 
/*      */ 
/*      */     
/* 1202 */     updateTimerTable(timerNotification.getNotificationID());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void sendNotification(Date paramDate, TimerNotification paramTimerNotification) {
/*      */     long l;
/* 1214 */     if (JmxProperties.TIMER_LOGGER.isLoggable(Level.FINER)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1224 */       StringBuilder stringBuilder = (new StringBuilder()).append("sending timer notification:").append("\n\tNotification source = ").append(paramTimerNotification.getSource()).append("\n\tNotification type = ").append(paramTimerNotification.getType()).append("\n\tNotification ID = ").append(paramTimerNotification.getNotificationID()).append("\n\tNotification date = ").append(paramDate);
/* 1225 */       JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "sendNotification", stringBuilder
/* 1226 */           .toString());
/*      */     } 
/*      */     
/* 1229 */     synchronized (this) {
/*      */       
/* 1231 */       l = ++this.sequenceNumber;
/*      */     } 
/* 1233 */     synchronized (paramTimerNotification) {
/* 1234 */       paramTimerNotification.setTimeStamp(paramDate.getTime());
/* 1235 */       paramTimerNotification.setSequenceNumber(l);
/* 1236 */       sendNotification((TimerNotification)paramTimerNotification.cloneTimerNotification());
/*      */     } 
/*      */     
/* 1239 */     JmxProperties.TIMER_LOGGER.logp(Level.FINER, Timer.class.getName(), "sendNotification", "timer notification sent");
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/timer/Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */