/*     */ package com.sun.jmx.remote.internal;
/*     */ 
/*     */ import com.sun.jmx.remote.util.ClassLogger;
/*     */ import com.sun.jmx.remote.util.EnvHelp;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.management.InstanceNotFoundException;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.MBeanServerDelegate;
/*     */ import javax.management.MBeanServerNotification;
/*     */ import javax.management.Notification;
/*     */ import javax.management.NotificationBroadcaster;
/*     */ import javax.management.NotificationFilter;
/*     */ import javax.management.NotificationFilterSupport;
/*     */ import javax.management.NotificationListener;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.QueryEval;
/*     */ import javax.management.QueryExp;
/*     */ import javax.management.remote.NotificationResult;
/*     */ import javax.management.remote.TargetedNotification;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArrayNotificationBuffer
/*     */   implements NotificationBuffer
/*     */ {
/*     */   private boolean disposed = false;
/* 116 */   private static final Object globalLock = new Object();
/*     */   
/* 118 */   private static final HashMap<MBeanServer, ArrayNotificationBuffer> mbsToBuffer = new HashMap<>(1);
/*     */   
/* 120 */   private final Collection<ShareBuffer> sharers = new HashSet<>(1); private final NotificationListener bufferListener;
/*     */   public static NotificationBuffer getNotificationBuffer(MBeanServer paramMBeanServer, Map<String, ?> paramMap) {
/*     */     ArrayNotificationBuffer arrayNotificationBuffer;
/*     */     boolean bool;
/*     */     ShareBuffer shareBuffer;
/* 125 */     if (paramMap == null) {
/* 126 */       paramMap = Collections.emptyMap();
/*     */     }
/*     */     
/* 129 */     int i = EnvHelp.getNotifBufferSize(paramMap);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     synchronized (globalLock) {
/* 135 */       arrayNotificationBuffer = mbsToBuffer.get(paramMBeanServer);
/* 136 */       bool = (arrayNotificationBuffer == null) ? true : false;
/* 137 */       if (bool) {
/* 138 */         arrayNotificationBuffer = new ArrayNotificationBuffer(paramMBeanServer, i);
/* 139 */         mbsToBuffer.put(paramMBeanServer, arrayNotificationBuffer);
/*     */       } 
/* 141 */       arrayNotificationBuffer.getClass(); shareBuffer = new ShareBuffer(i);
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
/* 152 */     if (bool)
/* 153 */       arrayNotificationBuffer.createListeners(); 
/* 154 */     return shareBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void removeNotificationBuffer(MBeanServer paramMBeanServer) {
/* 162 */     synchronized (globalLock) {
/* 163 */       mbsToBuffer.remove(paramMBeanServer);
/*     */     } 
/*     */   }
/*     */   
/*     */   void addSharer(ShareBuffer paramShareBuffer) {
/* 168 */     synchronized (globalLock) {
/* 169 */       synchronized (this) {
/* 170 */         if (paramShareBuffer.getSize() > this.queueSize)
/* 171 */           resize(paramShareBuffer.getSize()); 
/*     */       } 
/* 173 */       this.sharers.add(paramShareBuffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeSharer(ShareBuffer paramShareBuffer) {
/*     */     boolean bool;
/* 179 */     synchronized (globalLock) {
/* 180 */       this.sharers.remove(paramShareBuffer);
/* 181 */       bool = this.sharers.isEmpty();
/* 182 */       if (bool) {
/* 183 */         removeNotificationBuffer(this.mBeanServer);
/*     */       } else {
/* 185 */         int i = 0;
/* 186 */         for (ShareBuffer shareBuffer : this.sharers) {
/* 187 */           int j = shareBuffer.getSize();
/* 188 */           if (j > i)
/* 189 */             i = j; 
/*     */         } 
/* 191 */         if (i < this.queueSize)
/* 192 */           resize(i); 
/*     */       } 
/*     */     } 
/* 195 */     if (bool) {
/* 196 */       synchronized (this) {
/* 197 */         this.disposed = true;
/*     */         
/* 199 */         notifyAll();
/*     */       } 
/* 201 */       destroyListeners();
/*     */     } 
/*     */   }
/*     */   
/*     */   private synchronized void resize(int paramInt) {
/* 206 */     if (paramInt == this.queueSize)
/*     */       return; 
/* 208 */     while (this.queue.size() > paramInt)
/* 209 */       dropNotification(); 
/* 210 */     this.queue.resize(paramInt);
/* 211 */     this.queueSize = paramInt;
/*     */   }
/*     */   private class ShareBuffer implements NotificationBuffer { private final int size;
/*     */     
/*     */     ShareBuffer(int param1Int) {
/* 216 */       this.size = param1Int;
/* 217 */       ArrayNotificationBuffer.this.addSharer(this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public NotificationResult fetchNotifications(NotificationBufferFilter param1NotificationBufferFilter, long param1Long1, long param1Long2, int param1Int) throws InterruptedException {
/* 226 */       ArrayNotificationBuffer arrayNotificationBuffer = ArrayNotificationBuffer.this;
/* 227 */       return arrayNotificationBuffer.fetchNotifications(param1NotificationBufferFilter, param1Long1, param1Long2, param1Int);
/*     */     }
/*     */ 
/*     */     
/*     */     public void dispose() {
/* 232 */       ArrayNotificationBuffer.this.removeSharer(this);
/*     */     }
/*     */     
/*     */     int getSize() {
/* 236 */       return this.size;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized boolean isDisposed() {
/* 262 */     return this.disposed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 269 */     throw new UnsupportedOperationException();
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
/*     */   public NotificationResult fetchNotifications(NotificationBufferFilter paramNotificationBufferFilter, long paramLong1, long paramLong2, int paramInt) throws InterruptedException {
/* 306 */     logger.trace("fetchNotifications", "starts");
/*     */     
/* 308 */     if (paramLong1 < 0L || isDisposed()) {
/* 309 */       synchronized (this) {
/* 310 */         return new NotificationResult(earliestSequenceNumber(), 
/* 311 */             nextSequenceNumber(), new TargetedNotification[0]);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 317 */     if (paramNotificationBufferFilter == null || paramLong1 < 0L || paramLong2 < 0L || paramInt < 0) {
/*     */ 
/*     */       
/* 320 */       logger.trace("fetchNotifications", "Bad args");
/* 321 */       throw new IllegalArgumentException("Bad args to fetch");
/*     */     } 
/*     */     
/* 324 */     if (logger.debugOn()) {
/* 325 */       logger.trace("fetchNotifications", "filter=" + paramNotificationBufferFilter + "; startSeq=" + paramLong1 + "; timeout=" + paramLong2 + "; max=" + paramInt);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 331 */     if (paramLong1 > nextSequenceNumber()) {
/*     */       
/* 333 */       String str = "Start sequence number too big: " + paramLong1 + " > " + nextSequenceNumber();
/* 334 */       logger.trace("fetchNotifications", str);
/* 335 */       throw new IllegalArgumentException(str);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 343 */     long l1 = System.currentTimeMillis() + paramLong2;
/* 344 */     if (l1 < 0L) {
/* 345 */       l1 = Long.MAX_VALUE;
/*     */     }
/* 347 */     if (logger.debugOn()) {
/* 348 */       logger.debug("fetchNotifications", "endTime=" + l1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 354 */     long l2 = -1L;
/* 355 */     long l3 = paramLong1;
/* 356 */     ArrayList<TargetedNotification> arrayList = new ArrayList();
/*     */ 
/*     */     
/*     */     while (true) {
/*     */       NamedNotification namedNotification;
/*     */       
/* 362 */       logger.debug("fetchNotifications", "main loop starts");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 368 */       synchronized (this) {
/*     */ 
/*     */ 
/*     */         
/* 372 */         if (l2 < 0L) {
/* 373 */           l2 = earliestSequenceNumber();
/* 374 */           if (logger.debugOn()) {
/* 375 */             logger.debug("fetchNotifications", "earliestSeq=" + l2);
/*     */           }
/*     */           
/* 378 */           if (l3 < l2) {
/* 379 */             l3 = l2;
/* 380 */             logger.debug("fetchNotifications", "nextSeq=earliestSeq");
/*     */           } 
/*     */         } else {
/*     */           
/* 384 */           l2 = earliestSequenceNumber();
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 391 */         if (l3 < l2) {
/* 392 */           logger.trace("fetchNotifications", "nextSeq=" + l3 + " < earliestSeq=" + l2 + " so may have lost notifs");
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 398 */         if (l3 < nextSequenceNumber()) {
/* 399 */           namedNotification = notificationAt(l3);
/*     */           
/* 401 */           if (!(paramNotificationBufferFilter instanceof ServerNotifForwarder.NotifForwarderBufferFilter)) {
/*     */             try {
/* 403 */               ServerNotifForwarder.checkMBeanPermission(this.mBeanServer, namedNotification
/* 404 */                   .getObjectName(), "addNotificationListener");
/* 405 */             } catch (InstanceNotFoundException|SecurityException instanceNotFoundException) {
/* 406 */               if (logger.debugOn()) {
/* 407 */                 logger.debug("fetchNotifications", "candidate: " + namedNotification + " skipped. exception " + instanceNotFoundException);
/*     */               }
/* 409 */               l3++;
/*     */               
/*     */               continue;
/*     */             } 
/*     */           }
/* 414 */           if (logger.debugOn()) {
/* 415 */             logger.debug("fetchNotifications", "candidate: " + namedNotification);
/*     */             
/* 417 */             logger.debug("fetchNotifications", "nextSeq now " + l3);
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 425 */           if (arrayList.size() > 0) {
/* 426 */             logger.debug("fetchNotifications", "no more notifs but have some so don't wait");
/*     */             
/*     */             break;
/*     */           } 
/* 430 */           long l = l1 - System.currentTimeMillis();
/* 431 */           if (l <= 0L) {
/* 432 */             logger.debug("fetchNotifications", "timeout");
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 437 */           if (isDisposed()) {
/* 438 */             if (logger.debugOn()) {
/* 439 */               logger.debug("fetchNotifications", "dispose callled, no wait");
/*     */             }
/* 441 */             return new NotificationResult(earliestSequenceNumber(), 
/* 442 */                 nextSequenceNumber(), new TargetedNotification[0]);
/*     */           } 
/*     */ 
/*     */           
/* 446 */           if (logger.debugOn()) {
/* 447 */             logger.debug("fetchNotifications", "wait(" + l + ")");
/*     */           }
/* 449 */           wait(l);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 460 */       ObjectName objectName = namedNotification.getObjectName();
/* 461 */       Notification notification = namedNotification.getNotification();
/* 462 */       ArrayList<TargetedNotification> arrayList1 = new ArrayList();
/*     */       
/* 464 */       logger.debug("fetchNotifications", "applying filter to candidate");
/*     */       
/* 466 */       paramNotificationBufferFilter.apply(arrayList1, objectName, notification);
/*     */       
/* 468 */       if (arrayList1.size() > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 474 */         if (paramInt <= 0) {
/* 475 */           logger.debug("fetchNotifications", "reached maxNotifications");
/*     */           
/*     */           break;
/*     */         } 
/* 479 */         paramInt--;
/* 480 */         if (logger.debugOn()) {
/* 481 */           logger.debug("fetchNotifications", "add: " + arrayList1);
/*     */         }
/* 483 */         arrayList.addAll(arrayList1);
/*     */       } 
/*     */       
/* 486 */       l3++;
/*     */     } 
/*     */ 
/*     */     
/* 490 */     int i = arrayList.size();
/* 491 */     TargetedNotification[] arrayOfTargetedNotification = new TargetedNotification[i];
/*     */     
/* 493 */     arrayList.toArray(arrayOfTargetedNotification);
/* 494 */     NotificationResult notificationResult = new NotificationResult(l2, l3, arrayOfTargetedNotification);
/*     */     
/* 496 */     if (logger.debugOn())
/* 497 */       logger.debug("fetchNotifications", notificationResult.toString()); 
/* 498 */     logger.trace("fetchNotifications", "ends");
/*     */     
/* 500 */     return notificationResult;
/*     */   }
/*     */   
/*     */   synchronized long earliestSequenceNumber() {
/* 504 */     return this.earliestSequenceNumber;
/*     */   }
/*     */   
/*     */   synchronized long nextSequenceNumber() {
/* 508 */     return this.nextSequenceNumber;
/*     */   }
/*     */   
/*     */   synchronized void addNotification(NamedNotification paramNamedNotification) {
/* 512 */     if (logger.traceOn()) {
/* 513 */       logger.trace("addNotification", paramNamedNotification.toString());
/*     */     }
/* 515 */     while (this.queue.size() >= this.queueSize) {
/* 516 */       dropNotification();
/* 517 */       if (logger.debugOn()) {
/* 518 */         logger.debug("addNotification", "dropped oldest notif, earliestSeq=" + this.earliestSequenceNumber);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 523 */     this.queue.add(paramNamedNotification);
/* 524 */     this.nextSequenceNumber++;
/* 525 */     if (logger.debugOn())
/* 526 */       logger.debug("addNotification", "nextSeq=" + this.nextSequenceNumber); 
/* 527 */     notifyAll();
/*     */   }
/*     */   
/*     */   private void dropNotification() {
/* 531 */     this.queue.remove(0);
/* 532 */     this.earliestSequenceNumber++;
/*     */   }
/*     */   
/*     */   synchronized NamedNotification notificationAt(long paramLong) {
/* 536 */     long l = paramLong - this.earliestSequenceNumber;
/* 537 */     if (l < 0L || l > 2147483647L) {
/* 538 */       String str = "Bad sequence number: " + paramLong + " (earliest " + this.earliestSequenceNumber + ")";
/*     */       
/* 540 */       logger.trace("notificationAt", str);
/* 541 */       throw new IllegalArgumentException(str);
/*     */     } 
/* 543 */     return this.queue.get((int)l);
/*     */   }
/*     */   private static class NamedNotification { private final ObjectName sender; private final Notification notification;
/*     */     
/*     */     NamedNotification(ObjectName param1ObjectName, Notification param1Notification) {
/* 548 */       this.sender = param1ObjectName;
/* 549 */       this.notification = param1Notification;
/*     */     }
/*     */     
/*     */     ObjectName getObjectName() {
/* 553 */       return this.sender;
/*     */     }
/*     */     
/*     */     Notification getNotification() {
/* 557 */       return this.notification;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 561 */       return "NamedNotification(" + this.sender + ", " + this.notification + ")";
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createListeners() {
/* 601 */     logger.debug("createListeners", "starts");
/*     */     
/* 603 */     synchronized (this) {
/* 604 */       this.createdDuringQuery = new HashSet<>();
/*     */     } 
/*     */     
/*     */     try {
/* 608 */       addNotificationListener(MBeanServerDelegate.DELEGATE_NAME, this.creationListener, creationFilter, null);
/*     */       
/* 610 */       logger.debug("createListeners", "added creationListener");
/* 611 */     } catch (Exception exception) {
/*     */       
/* 613 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Can't add listener to MBean server delegate: " + exception);
/* 614 */       EnvHelp.initCause(illegalArgumentException, exception);
/* 615 */       logger.fine("createListeners", "Can't add listener to MBean server delegate: " + exception);
/* 616 */       logger.debug("createListeners", exception);
/* 617 */       throw illegalArgumentException;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 622 */     Set<ObjectName> set = queryNames(null, broadcasterQuery);
/* 623 */     set = new HashSet<>(set);
/*     */     
/* 625 */     synchronized (this) {
/* 626 */       set.addAll(this.createdDuringQuery);
/* 627 */       this.createdDuringQuery = null;
/*     */     } 
/*     */     
/* 630 */     for (ObjectName objectName : set)
/* 631 */       addBufferListener(objectName); 
/* 632 */     logger.debug("createListeners", "ends");
/*     */   }
/*     */   
/*     */   private void addBufferListener(ObjectName paramObjectName) {
/* 636 */     checkNoLocks();
/* 637 */     if (logger.debugOn())
/* 638 */       logger.debug("addBufferListener", paramObjectName.toString()); 
/*     */     try {
/* 640 */       addNotificationListener(paramObjectName, this.bufferListener, null, paramObjectName);
/* 641 */     } catch (Exception exception) {
/* 642 */       logger.trace("addBufferListener", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeBufferListener(ObjectName paramObjectName) {
/* 650 */     checkNoLocks();
/* 651 */     if (logger.debugOn())
/* 652 */       logger.debug("removeBufferListener", paramObjectName.toString()); 
/*     */     try {
/* 654 */       removeNotificationListener(paramObjectName, this.bufferListener);
/* 655 */     } catch (Exception exception) {
/* 656 */       logger.trace("removeBufferListener", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addNotificationListener(final ObjectName name, final NotificationListener listener, final NotificationFilter filter, final Object handback) throws Exception {
/*     */     try {
/* 666 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
/*     */             public Void run() throws InstanceNotFoundException {
/* 668 */               ArrayNotificationBuffer.this.mBeanServer.addNotificationListener(name, listener, filter, handback);
/*     */ 
/*     */ 
/*     */               
/* 672 */               return null;
/*     */             }
/*     */           });
/* 675 */     } catch (Exception exception) {
/* 676 */       throw extractException(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeNotificationListener(final ObjectName name, final NotificationListener listener) throws Exception {
/*     */     try {
/* 684 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
/*     */             public Void run() throws Exception {
/* 686 */               ArrayNotificationBuffer.this.mBeanServer.removeNotificationListener(name, listener);
/* 687 */               return null;
/*     */             }
/*     */           });
/* 690 */     } catch (Exception exception) {
/* 691 */       throw extractException(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<ObjectName> queryNames(final ObjectName name, final QueryExp query) {
/* 697 */     PrivilegedAction<Set<ObjectName>> privilegedAction = new PrivilegedAction<Set<ObjectName>>()
/*     */       {
/*     */         public Set<ObjectName> run() {
/* 700 */           return ArrayNotificationBuffer.this.mBeanServer.queryNames(name, query);
/*     */         }
/*     */       };
/*     */     try {
/* 704 */       return AccessController.<Set<ObjectName>>doPrivileged(privilegedAction);
/* 705 */     } catch (RuntimeException runtimeException) {
/* 706 */       logger.fine("queryNames", "Failed to query names: " + runtimeException);
/* 707 */       logger.debug("queryNames", runtimeException);
/* 708 */       throw runtimeException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isInstanceOf(final MBeanServer mbs, final ObjectName name, final String className) {
/* 715 */     PrivilegedExceptionAction<Boolean> privilegedExceptionAction = new PrivilegedExceptionAction<Boolean>()
/*     */       {
/*     */         public Boolean run() throws InstanceNotFoundException {
/* 718 */           return Boolean.valueOf(mbs.isInstanceOf(name, className));
/*     */         }
/*     */       };
/*     */     try {
/* 722 */       return ((Boolean)AccessController.<Boolean>doPrivileged(privilegedExceptionAction)).booleanValue();
/* 723 */     } catch (Exception exception) {
/* 724 */       logger.fine("isInstanceOf", "failed: " + exception);
/* 725 */       logger.debug("isInstanceOf", exception);
/* 726 */       return false;
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
/*     */   private void createdNotification(MBeanServerNotification paramMBeanServerNotification) {
/* 741 */     if (!paramMBeanServerNotification.getType().equals("JMX.mbean.registered")) {
/* 742 */       logger.warning("createNotification", "bad type: " + paramMBeanServerNotification.getType());
/*     */       
/*     */       return;
/*     */     } 
/* 746 */     ObjectName objectName = paramMBeanServerNotification.getMBeanName();
/* 747 */     if (logger.debugOn()) {
/* 748 */       logger.debug("createdNotification", "for: " + objectName);
/*     */     }
/* 750 */     synchronized (this) {
/* 751 */       if (this.createdDuringQuery != null) {
/* 752 */         this.createdDuringQuery.add(objectName);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 757 */     if (isInstanceOf(this.mBeanServer, objectName, broadcasterClass)) {
/* 758 */       addBufferListener(objectName);
/* 759 */       if (isDisposed())
/* 760 */         removeBufferListener(objectName); 
/*     */     } 
/*     */   }
/*     */   private class BufferListener implements NotificationListener { private BufferListener() {}
/*     */     
/*     */     public void handleNotification(Notification param1Notification, Object param1Object) {
/* 766 */       if (ArrayNotificationBuffer.logger.debugOn()) {
/* 767 */         ArrayNotificationBuffer.logger.debug("BufferListener.handleNotification", "notif=" + param1Notification + "; handback=" + param1Object);
/*     */       }
/*     */       
/* 770 */       ObjectName objectName = (ObjectName)param1Object;
/* 771 */       ArrayNotificationBuffer.this.addNotification(new ArrayNotificationBuffer.NamedNotification(objectName, param1Notification));
/*     */     } }
/*     */ 
/*     */   
/* 775 */   private ArrayNotificationBuffer(MBeanServer paramMBeanServer, int paramInt) { this.bufferListener = new BufferListener();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 795 */     this.creationListener = new NotificationListener()
/*     */       {
/*     */         public void handleNotification(Notification param1Notification, Object param1Object)
/*     */         {
/* 799 */           ArrayNotificationBuffer.logger.debug("creationListener", "handleNotification called");
/* 800 */           ArrayNotificationBuffer.this.createdNotification((MBeanServerNotification)param1Notification);
/*     */         }
/*     */       }; if (logger.traceOn())
/*     */       logger.trace("Constructor", "queueSize=" + paramInt);  if (paramMBeanServer == null || paramInt < 1)
/*     */       throw new IllegalArgumentException("Bad args");  this.mBeanServer = paramMBeanServer; this.queueSize = paramInt; this.queue = new ArrayQueue<>(paramInt); this.earliestSequenceNumber = System.currentTimeMillis(); this.nextSequenceNumber = this.earliestSequenceNumber; logger.trace("Constructor", "ends"); } private static class BroadcasterQuery extends QueryEval implements QueryExp { private static final long serialVersionUID = 7378487660587592048L; private BroadcasterQuery() {} public boolean apply(ObjectName param1ObjectName) { MBeanServer mBeanServer = QueryEval.getMBeanServer();
/* 805 */       return ArrayNotificationBuffer.isInstanceOf(mBeanServer, param1ObjectName, ArrayNotificationBuffer.broadcasterClass); } } private static final QueryExp broadcasterQuery = new BroadcasterQuery(); private void destroyListeners() { checkNoLocks();
/* 806 */     logger.debug("destroyListeners", "starts");
/*     */     try {
/* 808 */       removeNotificationListener(MBeanServerDelegate.DELEGATE_NAME, this.creationListener);
/*     */     }
/* 810 */     catch (Exception exception) {
/* 811 */       logger.warning("remove listener from MBeanServer delegate", exception);
/*     */     } 
/* 813 */     Set<ObjectName> set = queryNames(null, broadcasterQuery);
/* 814 */     for (ObjectName objectName : set) {
/* 815 */       if (logger.debugOn()) {
/* 816 */         logger.debug("destroyListeners", "remove listener from " + objectName);
/*     */       }
/* 818 */       removeBufferListener(objectName);
/*     */     } 
/* 820 */     logger.debug("destroyListeners", "ends"); }
/*     */   private static final NotificationFilter creationFilter; private final NotificationListener creationListener; static { NotificationFilterSupport notificationFilterSupport = new NotificationFilterSupport();
/*     */     notificationFilterSupport.enableType("JMX.mbean.registered");
/*     */     creationFilter = notificationFilterSupport; } private void checkNoLocks() {
/* 824 */     if (Thread.holdsLock(this) || Thread.holdsLock(globalLock)) {
/* 825 */       logger.warning("checkNoLocks", "lock protocol violation");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Exception extractException(Exception paramException) {
/* 833 */     while (paramException instanceof PrivilegedActionException) {
/* 834 */       paramException = ((PrivilegedActionException)paramException).getException();
/*     */     }
/* 836 */     return paramException;
/*     */   }
/*     */   
/* 839 */   private static final ClassLogger logger = new ClassLogger("javax.management.remote.misc", "ArrayNotificationBuffer");
/*     */   
/*     */   private final MBeanServer mBeanServer;
/*     */   
/*     */   private final ArrayQueue<NamedNotification> queue;
/*     */   
/*     */   private int queueSize;
/*     */   
/*     */   private long earliestSequenceNumber;
/*     */   private long nextSequenceNumber;
/*     */   private Set<ObjectName> createdDuringQuery;
/* 850 */   static final String broadcasterClass = NotificationBroadcaster.class
/* 851 */     .getName();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/internal/ArrayNotificationBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */