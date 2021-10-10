/*     */ package javax.management.timer;
/*     */ 
/*     */ import javax.management.Notification;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TimerNotification
/*     */   extends Notification
/*     */ {
/*     */   private static final long serialVersionUID = 1798492029603825750L;
/*     */   private Integer notificationID;
/*     */   
/*     */   public TimerNotification(String paramString1, Object paramObject, long paramLong1, long paramLong2, String paramString2, Integer paramInteger) {
/*  75 */     super(paramString1, paramObject, paramLong1, paramLong2, paramString2);
/*  76 */     this.notificationID = paramInteger;
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
/*     */   public Integer getNotificationID() {
/*  94 */     return this.notificationID;
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
/*     */   Object cloneTimerNotification() {
/* 110 */     TimerNotification timerNotification = new TimerNotification(getType(), getSource(), getSequenceNumber(), getTimeStamp(), getMessage(), this.notificationID);
/* 111 */     timerNotification.setUserData(getUserData());
/* 112 */     return timerNotification;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/timer/TimerNotification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */