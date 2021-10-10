/*    */ package javax.naming.ldap;
/*    */ 
/*    */ import java.util.EventObject;
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
/*    */ public class UnsolicitedNotificationEvent
/*    */   extends EventObject
/*    */ {
/*    */   private UnsolicitedNotification notice;
/*    */   private static final long serialVersionUID = -2382603380799883705L;
/*    */   
/*    */   public UnsolicitedNotificationEvent(Object paramObject, UnsolicitedNotification paramUnsolicitedNotification) {
/* 59 */     super(paramObject);
/* 60 */     this.notice = paramUnsolicitedNotification;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public UnsolicitedNotification getNotification() {
/* 70 */     return this.notice;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dispatch(UnsolicitedNotificationListener paramUnsolicitedNotificationListener) {
/* 80 */     paramUnsolicitedNotificationListener.notificationReceived(this);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/ldap/UnsolicitedNotificationEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */