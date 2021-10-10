/*     */ package sun.management;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.management.ListenerNotFoundException;
/*     */ import javax.management.MBeanNotificationInfo;
/*     */ import javax.management.Notification;
/*     */ import javax.management.NotificationEmitter;
/*     */ import javax.management.NotificationFilter;
/*     */ import javax.management.NotificationListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class NotificationEmitterSupport
/*     */   implements NotificationEmitter
/*     */ {
/*  48 */   private Object listenerLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) {
/*  56 */     if (paramNotificationListener == null) {
/*  57 */       throw new IllegalArgumentException("Listener can't be null");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     synchronized (this.listenerLock) {
/*  74 */       ArrayList<ListenerInfo> arrayList = new ArrayList(this.listenerList.size() + 1);
/*  75 */       arrayList.addAll(this.listenerList);
/*  76 */       arrayList.add(new ListenerInfo(paramNotificationListener, paramNotificationFilter, paramObject));
/*  77 */       this.listenerList = arrayList;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNotificationListener(NotificationListener paramNotificationListener) throws ListenerNotFoundException {
/*  84 */     synchronized (this.listenerLock) {
/*  85 */       ArrayList<ListenerInfo> arrayList = new ArrayList<>(this.listenerList);
/*     */ 
/*     */ 
/*     */       
/*  89 */       for (int i = arrayList.size() - 1; i >= 0; i--) {
/*  90 */         ListenerInfo listenerInfo = arrayList.get(i);
/*     */         
/*  92 */         if (listenerInfo.listener == paramNotificationListener)
/*  93 */           arrayList.remove(i); 
/*     */       } 
/*  95 */       if (arrayList.size() == this.listenerList.size())
/*  96 */         throw new ListenerNotFoundException("Listener not registered"); 
/*  97 */       this.listenerList = arrayList;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws ListenerNotFoundException {
/* 106 */     boolean bool = false;
/*     */     
/* 108 */     synchronized (this.listenerLock) {
/* 109 */       ArrayList<ListenerInfo> arrayList = new ArrayList<>(this.listenerList);
/* 110 */       int i = arrayList.size();
/* 111 */       for (byte b = 0; b < i; b++) {
/* 112 */         ListenerInfo listenerInfo = arrayList.get(b);
/*     */         
/* 114 */         if (listenerInfo.listener == paramNotificationListener) {
/* 115 */           bool = true;
/* 116 */           if (listenerInfo.filter == paramNotificationFilter && listenerInfo.handback == paramObject) {
/*     */             
/* 118 */             arrayList.remove(b);
/* 119 */             this.listenerList = arrayList;
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 126 */     if (bool)
/*     */     {
/*     */ 
/*     */       
/* 130 */       throw new ListenerNotFoundException("Listener not registered with this filter and handback");
/*     */     }
/*     */ 
/*     */     
/* 134 */     throw new ListenerNotFoundException("Listener not registered");
/*     */   }
/*     */ 
/*     */   
/*     */   void sendNotification(Notification paramNotification) {
/*     */     List<ListenerInfo> list;
/* 140 */     if (paramNotification == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 145 */     synchronized (this.listenerLock) {
/* 146 */       list = this.listenerList;
/*     */     } 
/*     */     
/* 149 */     int i = list.size();
/* 150 */     for (byte b = 0; b < i; b++) {
/* 151 */       ListenerInfo listenerInfo = list.get(b);
/*     */       
/* 153 */       if (listenerInfo.filter == null || listenerInfo.filter
/* 154 */         .isNotificationEnabled(paramNotification)) {
/*     */         try {
/* 156 */           listenerInfo.listener.handleNotification(paramNotification, listenerInfo.handback);
/* 157 */         } catch (Exception exception) {
/* 158 */           exception.printStackTrace();
/* 159 */           throw new AssertionError("Error in invoking listener");
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean hasListeners() {
/* 166 */     synchronized (this.listenerLock) {
/* 167 */       return !this.listenerList.isEmpty();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private class ListenerInfo
/*     */   {
/*     */     public NotificationListener listener;
/*     */     NotificationFilter filter;
/*     */     Object handback;
/*     */     
/*     */     public ListenerInfo(NotificationListener param1NotificationListener, NotificationFilter param1NotificationFilter, Object param1Object) {
/* 179 */       this.listener = param1NotificationListener;
/* 180 */       this.filter = param1NotificationFilter;
/* 181 */       this.handback = param1Object;
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
/* 195 */   private List<ListenerInfo> listenerList = Collections.emptyList();
/*     */   
/*     */   public abstract MBeanNotificationInfo[] getNotificationInfo();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/NotificationEmitterSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */