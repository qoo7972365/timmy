/*     */ package javax.management;
/*     */ 
/*     */ import com.sun.jmx.remote.util.ClassLogger;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.concurrent.Executor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NotificationBroadcasterSupport
/*     */   implements NotificationEmitter
/*     */ {
/*     */   private List<ListenerInfo> listenerList;
/*     */   private final Executor executor;
/*     */   private final MBeanNotificationInfo[] notifInfo;
/*     */   
/*     */   public NotificationBroadcasterSupport() {
/*  70 */     this(null, (MBeanNotificationInfo[])null);
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
/*     */   public NotificationBroadcasterSupport(Executor paramExecutor) {
/*  92 */     this(paramExecutor, (MBeanNotificationInfo[])null);
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
/*     */   public NotificationBroadcasterSupport(MBeanNotificationInfo... paramVarArgs) {
/* 117 */     this(null, paramVarArgs);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NotificationBroadcasterSupport(Executor paramExecutor, MBeanNotificationInfo... paramVarArgs) {
/* 327 */     this.listenerList = new CopyOnWriteArrayList<>(); this.executor = (paramExecutor != null) ? paramExecutor : defaultExecutor; this.notifInfo = (paramVarArgs == null) ? NO_NOTIFICATION_INFO : (MBeanNotificationInfo[])paramVarArgs.clone();
/*     */   }
/*     */   public void addNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) { if (paramNotificationListener == null) throw new IllegalArgumentException("Listener can't be null");  this.listenerList.add(new ListenerInfo(paramNotificationListener, paramNotificationFilter, paramObject)); }
/*     */   public void removeNotificationListener(NotificationListener paramNotificationListener) throws ListenerNotFoundException { WildcardListenerInfo wildcardListenerInfo = new WildcardListenerInfo(paramNotificationListener); boolean bool = this.listenerList.removeAll(Collections.singleton(wildcardListenerInfo)); if (!bool)
/*     */       throw new ListenerNotFoundException("Listener not registered");  }
/*     */   public void removeNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws ListenerNotFoundException { ListenerInfo listenerInfo = new ListenerInfo(paramNotificationListener, paramNotificationFilter, paramObject); boolean bool = this.listenerList.remove(listenerInfo); if (!bool)
/*     */       throw new ListenerNotFoundException("Listener not registered (with this filter and handback)");  } public MBeanNotificationInfo[] getNotificationInfo() { if (this.notifInfo.length == 0)
/* 334 */       return this.notifInfo;  return (MBeanNotificationInfo[])this.notifInfo.clone(); } private static final Executor defaultExecutor = new Executor()
/*     */     {
/*     */       public void execute(Runnable param1Runnable) {
/* 337 */         param1Runnable.run();
/*     */       }
/*     */     }; public void sendNotification(Notification paramNotification) { if (paramNotification == null) return;  for (ListenerInfo listenerInfo : this.listenerList) { boolean bool; try { bool = (listenerInfo.filter == null || listenerInfo.filter.isNotificationEnabled(paramNotification)) ? true : false; } catch (Exception exception) { if (logger.debugOn()) logger.debug("sendNotification", exception);  continue; }  if (bool) this.executor.execute(new SendNotifJob(paramNotification, listenerInfo));  }  } protected void handleNotification(NotificationListener paramNotificationListener, Notification paramNotification, Object paramObject) { paramNotificationListener.handleNotification(paramNotification, paramObject); } private static class ListenerInfo {
/*     */     NotificationListener listener; NotificationFilter filter; Object handback; ListenerInfo(NotificationListener param1NotificationListener, NotificationFilter param1NotificationFilter, Object param1Object) { this.listener = param1NotificationListener; this.filter = param1NotificationFilter; this.handback = param1Object; } public boolean equals(Object param1Object) { if (!(param1Object instanceof ListenerInfo)) return false;  ListenerInfo listenerInfo = (ListenerInfo)param1Object; if (listenerInfo instanceof NotificationBroadcasterSupport.WildcardListenerInfo) return (listenerInfo.listener == this.listener);  return (listenerInfo.listener == this.listener && listenerInfo.filter == this.filter && listenerInfo.handback == this.handback); } public int hashCode() { return Objects.hashCode(this.listener); } } private static class WildcardListenerInfo extends ListenerInfo {
/* 341 */     WildcardListenerInfo(NotificationListener param1NotificationListener) { super(param1NotificationListener, null, null); } public boolean equals(Object param1Object) { assert !(param1Object instanceof WildcardListenerInfo); return param1Object.equals(this); } public int hashCode() { return super.hashCode(); } } private static final MBeanNotificationInfo[] NO_NOTIFICATION_INFO = new MBeanNotificationInfo[0];
/*     */   private class SendNotifJob implements Runnable { private final Notification notif;
/*     */     private final NotificationBroadcasterSupport.ListenerInfo listenerInfo;
/*     */     
/*     */     public SendNotifJob(Notification param1Notification, NotificationBroadcasterSupport.ListenerInfo param1ListenerInfo) {
/* 346 */       this.notif = param1Notification;
/* 347 */       this.listenerInfo = param1ListenerInfo;
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       try {
/* 352 */         NotificationBroadcasterSupport.this.handleNotification(this.listenerInfo.listener, this.notif, this.listenerInfo.handback);
/*     */       }
/* 354 */       catch (Exception exception) {
/* 355 */         if (NotificationBroadcasterSupport.logger.debugOn()) {
/* 356 */           NotificationBroadcasterSupport.logger.debug("SendNotifJob-run", exception);
/*     */         }
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 365 */   private static final ClassLogger logger = new ClassLogger("javax.management", "NotificationBroadcasterSupport");
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/NotificationBroadcasterSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */