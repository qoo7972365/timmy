/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.util.EventObject;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.directory.SearchControls;
/*     */ import javax.naming.event.NamingExceptionEvent;
/*     */ import javax.naming.event.NamingListener;
/*     */ import javax.naming.ldap.UnsolicitedNotification;
/*     */ import javax.naming.ldap.UnsolicitedNotificationEvent;
/*     */ import javax.naming.ldap.UnsolicitedNotificationListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class EventSupport
/*     */ {
/*     */   private static final boolean debug = false;
/*     */   private LdapCtx ctx;
/* 123 */   private Hashtable<NotifierArgs, NamingEventNotifier> notifiers = new Hashtable<>(11);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   private Vector<UnsolicitedNotificationListener> unsolicited = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private EventQueue eventQueue;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   EventSupport(LdapCtx paramLdapCtx) {
/* 139 */     this.ctx = paramLdapCtx;
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
/*     */   synchronized void addNamingListener(String paramString, int paramInt, NamingListener paramNamingListener) throws NamingException {
/* 155 */     if (paramNamingListener instanceof javax.naming.event.ObjectChangeListener || paramNamingListener instanceof javax.naming.event.NamespaceChangeListener) {
/*     */       
/* 157 */       NotifierArgs notifierArgs = new NotifierArgs(paramString, paramInt, paramNamingListener);
/*     */       
/* 159 */       NamingEventNotifier namingEventNotifier = this.notifiers.get(notifierArgs);
/* 160 */       if (namingEventNotifier == null) {
/* 161 */         namingEventNotifier = new NamingEventNotifier(this, this.ctx, notifierArgs, paramNamingListener);
/* 162 */         this.notifiers.put(notifierArgs, namingEventNotifier);
/*     */       } else {
/* 164 */         namingEventNotifier.addNamingListener(paramNamingListener);
/*     */       } 
/*     */     } 
/* 167 */     if (paramNamingListener instanceof UnsolicitedNotificationListener) {
/*     */       
/* 169 */       if (this.unsolicited == null) {
/* 170 */         this.unsolicited = new Vector<>(3);
/*     */       }
/*     */       
/* 173 */       this.unsolicited.addElement((UnsolicitedNotificationListener)paramNamingListener);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void addNamingListener(String paramString1, String paramString2, SearchControls paramSearchControls, NamingListener paramNamingListener) throws NamingException {
/* 184 */     if (paramNamingListener instanceof javax.naming.event.ObjectChangeListener || paramNamingListener instanceof javax.naming.event.NamespaceChangeListener) {
/*     */       
/* 186 */       NotifierArgs notifierArgs = new NotifierArgs(paramString1, paramString2, paramSearchControls, paramNamingListener);
/*     */       
/* 188 */       NamingEventNotifier namingEventNotifier = this.notifiers.get(notifierArgs);
/* 189 */       if (namingEventNotifier == null) {
/* 190 */         namingEventNotifier = new NamingEventNotifier(this, this.ctx, notifierArgs, paramNamingListener);
/* 191 */         this.notifiers.put(notifierArgs, namingEventNotifier);
/*     */       } else {
/* 193 */         namingEventNotifier.addNamingListener(paramNamingListener);
/*     */       } 
/*     */     } 
/* 196 */     if (paramNamingListener instanceof UnsolicitedNotificationListener) {
/*     */       
/* 198 */       if (this.unsolicited == null) {
/* 199 */         this.unsolicited = new Vector<>(3);
/*     */       }
/* 201 */       this.unsolicited.addElement((UnsolicitedNotificationListener)paramNamingListener);
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
/*     */   synchronized void removeNamingListener(NamingListener paramNamingListener) {
/* 214 */     Iterator<NamingEventNotifier> iterator = this.notifiers.values().iterator();
/* 215 */     while (iterator.hasNext()) {
/* 216 */       NamingEventNotifier namingEventNotifier = iterator.next();
/* 217 */       if (namingEventNotifier != null) {
/*     */ 
/*     */ 
/*     */         
/* 221 */         namingEventNotifier.removeNamingListener(paramNamingListener);
/* 222 */         if (!namingEventNotifier.hasNamingListeners()) {
/*     */ 
/*     */ 
/*     */           
/* 226 */           namingEventNotifier.stop();
/* 227 */           iterator.remove();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     if (this.unsolicited != null) {
/* 236 */       this.unsolicited.removeElement(paramNamingListener);
/*     */     }
/*     */   }
/*     */   
/*     */   synchronized boolean hasUnsolicited() {
/* 241 */     return (this.unsolicited != null && this.unsolicited.size() > 0);
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
/*     */   synchronized void removeDeadNotifier(NotifierArgs paramNotifierArgs) {
/* 253 */     this.notifiers.remove(paramNotifierArgs);
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
/*     */   synchronized void fireUnsolicited(Object paramObject) {
/* 266 */     if (this.unsolicited == null || this.unsolicited.size() == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 273 */     if (paramObject instanceof UnsolicitedNotification) {
/*     */ 
/*     */ 
/*     */       
/* 277 */       UnsolicitedNotificationEvent unsolicitedNotificationEvent = new UnsolicitedNotificationEvent(this.ctx, (UnsolicitedNotification)paramObject);
/*     */       
/* 279 */       queueEvent(unsolicitedNotificationEvent, (Vector)this.unsolicited);
/*     */     }
/* 281 */     else if (paramObject instanceof NamingException) {
/*     */ 
/*     */ 
/*     */       
/* 285 */       NamingExceptionEvent namingExceptionEvent = new NamingExceptionEvent(this.ctx, (NamingException)paramObject);
/*     */       
/* 287 */       queueEvent(namingExceptionEvent, (Vector)this.unsolicited);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 295 */       this.unsolicited = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void cleanup() {
/* 306 */     if (this.notifiers != null) {
/* 307 */       for (NamingEventNotifier namingEventNotifier : this.notifiers.values()) {
/* 308 */         namingEventNotifier.stop();
/*     */       }
/* 310 */       this.notifiers = null;
/*     */     } 
/* 312 */     if (this.eventQueue != null) {
/* 313 */       this.eventQueue.stop();
/* 314 */       this.eventQueue = null;
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
/*     */   synchronized void queueEvent(EventObject paramEventObject, Vector<? extends NamingListener> paramVector) {
/* 332 */     if (this.eventQueue == null) {
/* 333 */       this.eventQueue = new EventQueue();
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
/* 345 */     Vector<NamingListener> vector = (Vector)paramVector.clone();
/* 346 */     this.eventQueue.enqueue(paramEventObject, vector);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/EventSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */