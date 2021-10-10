/*    */ package javax.management.timer;
/*    */ 
/*    */ import com.sun.jmx.defaults.JmxProperties;
/*    */ import java.util.Date;
/*    */ import java.util.TimerTask;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class TimerAlarmClock
/*    */   extends TimerTask
/*    */ {
/* 40 */   Timer listener = null;
/* 41 */   long timeout = 10000L;
/* 42 */   Date next = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TimerAlarmClock(Timer paramTimer, long paramLong) {
/* 51 */     this.listener = paramTimer;
/* 52 */     this.timeout = Math.max(0L, paramLong);
/*    */   }
/*    */   
/*    */   public TimerAlarmClock(Timer paramTimer, Date paramDate) {
/* 56 */     this.listener = paramTimer;
/* 57 */     this.next = paramDate;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 73 */       TimerAlarmClockNotification timerAlarmClockNotification = new TimerAlarmClockNotification(this);
/* 74 */       this.listener.notifyAlarmClock(timerAlarmClockNotification);
/* 75 */     } catch (Exception exception) {
/* 76 */       JmxProperties.TIMER_LOGGER.logp(Level.FINEST, Timer.class.getName(), "run", "Got unexpected exception when sending a notification", exception);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/timer/TimerAlarmClock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */