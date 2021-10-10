/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.InputEvent;
/*      */ import java.awt.event.InputMethodEvent;
/*      */ import java.awt.event.InvocationEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.PaintEvent;
/*      */ import java.awt.peer.ComponentPeer;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.EmptyStackException;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.locks.Condition;
/*      */ import java.util.concurrent.locks.Lock;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AWTAutoShutdown;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.EventQueueItem;
/*      */ import sun.awt.FwDispatcher;
/*      */ import sun.awt.PeerEvent;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.misc.JavaSecurityAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class EventQueue
/*      */ {
/*   98 */   private static final AtomicInteger threadInitNumber = new AtomicInteger(0);
/*      */ 
/*      */   
/*      */   private static final int LOW_PRIORITY = 0;
/*      */ 
/*      */   
/*      */   private static final int NORM_PRIORITY = 1;
/*      */ 
/*      */   
/*      */   private static final int HIGH_PRIORITY = 2;
/*      */ 
/*      */   
/*      */   private static final int ULTIMATE_PRIORITY = 3;
/*      */ 
/*      */   
/*      */   private static final int NUM_PRIORITIES = 4;
/*      */   
/*  115 */   private Queue[] queues = new Queue[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EventQueue nextQueue;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EventQueue previousQueue;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Lock pushPopLock;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Condition pushPopCond;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  142 */   private static final Runnable dummyRunnable = new Runnable()
/*      */     {
/*      */       public void run() {}
/*      */     };
/*      */ 
/*      */   
/*      */   private EventDispatchThread dispatchThread;
/*      */   
/*  150 */   private final ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
/*      */   
/*  152 */   private final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  157 */   private long mostRecentEventTime = System.currentTimeMillis();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  162 */   private long mostRecentKeyEventTime = System.currentTimeMillis();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private WeakReference<AWTEvent> currentEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile int waitForID;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final AppContext appContext;
/*      */ 
/*      */ 
/*      */   
/*  181 */   private final String name = "AWT-EventQueue-" + threadInitNumber.getAndIncrement();
/*      */   private FwDispatcher fwDispatcher;
/*      */   private static volatile PlatformLogger eventLog;
/*      */   private static final int PAINT = 0;
/*      */   private static final int UPDATE = 1;
/*      */   
/*      */   private static final PlatformLogger getEventLog() {
/*  188 */     if (eventLog == null) {
/*  189 */       eventLog = PlatformLogger.getLogger("java.awt.event.EventQueue");
/*      */     }
/*  191 */     return eventLog;
/*      */   }
/*      */   private static final int MOVE = 2; private static final int DRAG = 3; private static final int PEER = 4; private static final int CACHE_LENGTH = 5;
/*      */   static {
/*  195 */     AWTAccessor.setEventQueueAccessor(new AWTAccessor.EventQueueAccessor()
/*      */         {
/*      */           public Thread getDispatchThread(EventQueue param1EventQueue) {
/*  198 */             return param1EventQueue.getDispatchThread();
/*      */           }
/*      */           public boolean isDispatchThreadImpl(EventQueue param1EventQueue) {
/*  201 */             return param1EventQueue.isDispatchThreadImpl();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void removeSourceEvents(EventQueue param1EventQueue, Object param1Object, boolean param1Boolean) {
/*  207 */             param1EventQueue.removeSourceEvents(param1Object, param1Boolean);
/*      */           }
/*      */           public boolean noEvents(EventQueue param1EventQueue) {
/*  210 */             return param1EventQueue.noEvents();
/*      */           }
/*      */           public void wakeup(EventQueue param1EventQueue, boolean param1Boolean) {
/*  213 */             param1EventQueue.wakeup(param1Boolean);
/*      */           }
/*      */ 
/*      */           
/*      */           public void invokeAndWait(Object param1Object, Runnable param1Runnable) throws InterruptedException, InvocationTargetException {
/*  218 */             EventQueue.invokeAndWait(param1Object, param1Runnable);
/*      */           }
/*      */           
/*      */           public void setFwDispatcher(EventQueue param1EventQueue, FwDispatcher param1FwDispatcher) {
/*  222 */             param1EventQueue.setFwDispatcher(param1FwDispatcher);
/*      */           }
/*      */ 
/*      */           
/*      */           public long getMostRecentEventTime(EventQueue param1EventQueue) {
/*  227 */             return param1EventQueue.getMostRecentEventTimeImpl();
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   public EventQueue() {
/*  233 */     for (byte b = 0; b < 4; b++) {
/*  234 */       this.queues[b] = new Queue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  245 */     this.appContext = AppContext.getAppContext();
/*  246 */     this.pushPopLock = (Lock)this.appContext.get(AppContext.EVENT_QUEUE_LOCK_KEY);
/*  247 */     this.pushPopCond = (Condition)this.appContext.get(AppContext.EVENT_QUEUE_COND_KEY);
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
/*      */   public void postEvent(AWTEvent paramAWTEvent) {
/*  261 */     SunToolkit.flushPendingEvents(this.appContext);
/*  262 */     postEventPrivate(paramAWTEvent);
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
/*      */   private final void postEventPrivate(AWTEvent paramAWTEvent) {
/*  275 */     paramAWTEvent.isPosted = true;
/*  276 */     this.pushPopLock.lock();
/*      */     try {
/*  278 */       if (this.nextQueue != null) {
/*      */         
/*  280 */         this.nextQueue.postEventPrivate(paramAWTEvent);
/*      */         return;
/*      */       } 
/*  283 */       if (this.dispatchThread == null) {
/*  284 */         if (paramAWTEvent.getSource() == AWTAutoShutdown.getInstance()) {
/*      */           return;
/*      */         }
/*  287 */         initDispatchThread();
/*      */       } 
/*      */       
/*  290 */       postEvent(paramAWTEvent, getPriority(paramAWTEvent));
/*      */     } finally {
/*  292 */       this.pushPopLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int getPriority(AWTEvent paramAWTEvent) {
/*  297 */     if (paramAWTEvent instanceof PeerEvent) {
/*  298 */       PeerEvent peerEvent = (PeerEvent)paramAWTEvent;
/*  299 */       if ((peerEvent.getFlags() & 0x2L) != 0L) {
/*  300 */         return 3;
/*      */       }
/*  302 */       if ((peerEvent.getFlags() & 0x1L) != 0L) {
/*  303 */         return 2;
/*      */       }
/*  305 */       if ((peerEvent.getFlags() & 0x4L) != 0L) {
/*  306 */         return 0;
/*      */       }
/*      */     } 
/*  309 */     int i = paramAWTEvent.getID();
/*  310 */     if (i >= 800 && i <= 801) {
/*  311 */       return 0;
/*      */     }
/*  313 */     return 1;
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
/*      */   private void postEvent(AWTEvent paramAWTEvent, int paramInt) {
/*  325 */     if (coalesceEvent(paramAWTEvent, paramInt)) {
/*      */       return;
/*      */     }
/*      */     
/*  329 */     EventQueueItem eventQueueItem = new EventQueueItem(paramAWTEvent);
/*      */     
/*  331 */     cacheEQItem(eventQueueItem);
/*      */     
/*  333 */     boolean bool = (paramAWTEvent.getID() == this.waitForID) ? true : false;
/*      */     
/*  335 */     if ((this.queues[paramInt]).head == null) {
/*  336 */       boolean bool1 = noEvents();
/*  337 */       (this.queues[paramInt]).head = (this.queues[paramInt]).tail = eventQueueItem;
/*      */       
/*  339 */       if (bool1) {
/*  340 */         if (paramAWTEvent.getSource() != AWTAutoShutdown.getInstance()) {
/*  341 */           AWTAutoShutdown.getInstance().notifyThreadBusy(this.dispatchThread);
/*      */         }
/*  343 */         this.pushPopCond.signalAll();
/*  344 */       } else if (bool) {
/*  345 */         this.pushPopCond.signalAll();
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  350 */       (this.queues[paramInt]).tail.next = eventQueueItem;
/*  351 */       (this.queues[paramInt]).tail = eventQueueItem;
/*  352 */       if (bool) {
/*  353 */         this.pushPopCond.signalAll();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean coalescePaintEvent(PaintEvent paramPaintEvent) {
/*  359 */     ComponentPeer componentPeer = ((Component)paramPaintEvent.getSource()).peer;
/*  360 */     if (componentPeer != null) {
/*  361 */       componentPeer.coalescePaintEvent(paramPaintEvent);
/*      */     }
/*  363 */     EventQueueItem[] arrayOfEventQueueItem = ((Component)paramPaintEvent.getSource()).eventCache;
/*  364 */     if (arrayOfEventQueueItem == null) {
/*  365 */       return false;
/*      */     }
/*  367 */     int i = eventToCacheIndex(paramPaintEvent);
/*      */     
/*  369 */     if (i != -1 && arrayOfEventQueueItem[i] != null) {
/*  370 */       PaintEvent paintEvent = mergePaintEvents(paramPaintEvent, (PaintEvent)(arrayOfEventQueueItem[i]).event);
/*  371 */       if (paintEvent != null) {
/*  372 */         (arrayOfEventQueueItem[i]).event = paintEvent;
/*  373 */         return true;
/*      */       } 
/*      */     } 
/*  376 */     return false;
/*      */   }
/*      */   
/*      */   private PaintEvent mergePaintEvents(PaintEvent paramPaintEvent1, PaintEvent paramPaintEvent2) {
/*  380 */     Rectangle rectangle1 = paramPaintEvent1.getUpdateRect();
/*  381 */     Rectangle rectangle2 = paramPaintEvent2.getUpdateRect();
/*  382 */     if (rectangle2.contains(rectangle1)) {
/*  383 */       return paramPaintEvent2;
/*      */     }
/*  385 */     if (rectangle1.contains(rectangle2)) {
/*  386 */       return paramPaintEvent1;
/*      */     }
/*  388 */     return null;
/*      */   }
/*      */   
/*      */   private boolean coalesceMouseEvent(MouseEvent paramMouseEvent) {
/*  392 */     EventQueueItem[] arrayOfEventQueueItem = ((Component)paramMouseEvent.getSource()).eventCache;
/*  393 */     if (arrayOfEventQueueItem == null) {
/*  394 */       return false;
/*      */     }
/*  396 */     int i = eventToCacheIndex(paramMouseEvent);
/*  397 */     if (i != -1 && arrayOfEventQueueItem[i] != null) {
/*  398 */       (arrayOfEventQueueItem[i]).event = paramMouseEvent;
/*  399 */       return true;
/*      */     } 
/*  401 */     return false;
/*      */   }
/*      */   
/*      */   private boolean coalescePeerEvent(PeerEvent paramPeerEvent) {
/*  405 */     EventQueueItem[] arrayOfEventQueueItem = ((Component)paramPeerEvent.getSource()).eventCache;
/*  406 */     if (arrayOfEventQueueItem == null) {
/*  407 */       return false;
/*      */     }
/*  409 */     int i = eventToCacheIndex(paramPeerEvent);
/*  410 */     if (i != -1 && arrayOfEventQueueItem[i] != null) {
/*  411 */       paramPeerEvent = paramPeerEvent.coalesceEvents((PeerEvent)(arrayOfEventQueueItem[i]).event);
/*  412 */       if (paramPeerEvent != null) {
/*  413 */         (arrayOfEventQueueItem[i]).event = paramPeerEvent;
/*  414 */         return true;
/*      */       } 
/*  416 */       arrayOfEventQueueItem[i] = null;
/*      */     } 
/*      */     
/*  419 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean coalesceOtherEvent(AWTEvent paramAWTEvent, int paramInt) {
/*  430 */     int i = paramAWTEvent.getID();
/*  431 */     Component component = (Component)paramAWTEvent.getSource();
/*  432 */     EventQueueItem eventQueueItem = (this.queues[paramInt]).head;
/*  433 */     for (; eventQueueItem != null; eventQueueItem = eventQueueItem.next) {
/*      */ 
/*      */       
/*  436 */       if (eventQueueItem.event.getSource() == component && eventQueueItem.event.getID() == i) {
/*  437 */         AWTEvent aWTEvent = component.coalesceEvents(eventQueueItem.event, paramAWTEvent);
/*      */         
/*  439 */         if (aWTEvent != null) {
/*  440 */           eventQueueItem.event = aWTEvent;
/*  441 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*  445 */     return false;
/*      */   }
/*      */   
/*      */   private boolean coalesceEvent(AWTEvent paramAWTEvent, int paramInt) {
/*  449 */     if (!(paramAWTEvent.getSource() instanceof Component)) {
/*  450 */       return false;
/*      */     }
/*  452 */     if (paramAWTEvent instanceof PeerEvent) {
/*  453 */       return coalescePeerEvent((PeerEvent)paramAWTEvent);
/*      */     }
/*      */     
/*  456 */     if (((Component)paramAWTEvent.getSource()).isCoalescingEnabled() && 
/*  457 */       coalesceOtherEvent(paramAWTEvent, paramInt))
/*      */     {
/*  459 */       return true;
/*      */     }
/*  461 */     if (paramAWTEvent instanceof PaintEvent) {
/*  462 */       return coalescePaintEvent((PaintEvent)paramAWTEvent);
/*      */     }
/*  464 */     if (paramAWTEvent instanceof MouseEvent) {
/*  465 */       return coalesceMouseEvent((MouseEvent)paramAWTEvent);
/*      */     }
/*  467 */     return false;
/*      */   }
/*      */   
/*      */   private void cacheEQItem(EventQueueItem paramEventQueueItem) {
/*  471 */     int i = eventToCacheIndex(paramEventQueueItem.event);
/*  472 */     if (i != -1 && paramEventQueueItem.event.getSource() instanceof Component) {
/*  473 */       Component component = (Component)paramEventQueueItem.event.getSource();
/*  474 */       if (component.eventCache == null) {
/*  475 */         component.eventCache = new EventQueueItem[5];
/*      */       }
/*  477 */       component.eventCache[i] = paramEventQueueItem;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void uncacheEQItem(EventQueueItem paramEventQueueItem) {
/*  482 */     int i = eventToCacheIndex(paramEventQueueItem.event);
/*  483 */     if (i != -1 && paramEventQueueItem.event.getSource() instanceof Component) {
/*  484 */       Component component = (Component)paramEventQueueItem.event.getSource();
/*  485 */       if (component.eventCache == null) {
/*      */         return;
/*      */       }
/*  488 */       component.eventCache[i] = null;
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
/*      */   private static int eventToCacheIndex(AWTEvent paramAWTEvent) {
/*  500 */     switch (paramAWTEvent.getID()) {
/*      */       case 800:
/*  502 */         return 0;
/*      */       case 801:
/*  504 */         return 1;
/*      */       case 503:
/*  506 */         return 2;
/*      */ 
/*      */       
/*      */       case 506:
/*  510 */         return (paramAWTEvent instanceof sun.awt.dnd.SunDropTargetEvent) ? -1 : 3;
/*      */     } 
/*  512 */     return (paramAWTEvent instanceof PeerEvent) ? 4 : -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean noEvents() {
/*  522 */     for (byte b = 0; b < 4; b++) {
/*  523 */       if ((this.queues[b]).head != null) {
/*  524 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  528 */     return true;
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
/*      */   public AWTEvent getNextEvent() throws InterruptedException {
/*      */     while (true) {
/*  546 */       SunToolkit.flushPendingEvents(this.appContext);
/*  547 */       this.pushPopLock.lock();
/*      */       try {
/*  549 */         AWTEvent aWTEvent = getNextEventPrivate();
/*  550 */         if (aWTEvent != null) {
/*  551 */           return aWTEvent;
/*      */         }
/*  553 */         AWTAutoShutdown.getInstance().notifyThreadFree(this.dispatchThread);
/*  554 */         this.pushPopCond.await();
/*      */       } finally {
/*  556 */         this.pushPopLock.unlock();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   AWTEvent getNextEventPrivate() throws InterruptedException {
/*  565 */     for (byte b = 3; b >= 0; b--) {
/*  566 */       if ((this.queues[b]).head != null) {
/*  567 */         EventQueueItem eventQueueItem = (this.queues[b]).head;
/*  568 */         (this.queues[b]).head = eventQueueItem.next;
/*  569 */         if (eventQueueItem.next == null) {
/*  570 */           (this.queues[b]).tail = null;
/*      */         }
/*  572 */         uncacheEQItem(eventQueueItem);
/*  573 */         return eventQueueItem.event;
/*      */       } 
/*      */     } 
/*  576 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   AWTEvent getNextEvent(int paramInt) throws InterruptedException {
/*      */     while (true) {
/*  586 */       SunToolkit.flushPendingEvents(this.appContext);
/*  587 */       this.pushPopLock.lock();
/*      */       try {
/*  589 */         for (byte b = 0; b < 4; b++) {
/*  590 */           EventQueueItem eventQueueItem1 = (this.queues[b]).head, eventQueueItem2 = null;
/*  591 */           for (; eventQueueItem1 != null; eventQueueItem2 = eventQueueItem1, eventQueueItem1 = eventQueueItem1.next) {
/*      */             
/*  593 */             if (eventQueueItem1.event.getID() == paramInt) {
/*  594 */               if (eventQueueItem2 == null) {
/*  595 */                 (this.queues[b]).head = eventQueueItem1.next;
/*      */               } else {
/*  597 */                 eventQueueItem2.next = eventQueueItem1.next;
/*      */               } 
/*  599 */               if ((this.queues[b]).tail == eventQueueItem1) {
/*  600 */                 (this.queues[b]).tail = eventQueueItem2;
/*      */               }
/*  602 */               uncacheEQItem(eventQueueItem1);
/*  603 */               return eventQueueItem1.event;
/*      */             } 
/*      */           } 
/*      */         } 
/*  607 */         this.waitForID = paramInt;
/*  608 */         this.pushPopCond.await();
/*  609 */         this.waitForID = 0;
/*      */       } finally {
/*  611 */         this.pushPopLock.unlock();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AWTEvent peekEvent() {
/*  622 */     this.pushPopLock.lock();
/*      */     try {
/*  624 */       for (byte b = 3; b >= 0; b--) {
/*  625 */         if ((this.queues[b]).head != null) {
/*  626 */           return (this.queues[b]).head.event;
/*      */         }
/*      */       } 
/*      */     } finally {
/*  630 */       this.pushPopLock.unlock();
/*      */     } 
/*      */     
/*  633 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AWTEvent peekEvent(int paramInt) {
/*  643 */     this.pushPopLock.lock();
/*      */     try {
/*  645 */       for (byte b = 3; b >= 0; b--) {
/*  646 */         EventQueueItem eventQueueItem = (this.queues[b]).head;
/*  647 */         for (; eventQueueItem != null; eventQueueItem = eventQueueItem.next) {
/*  648 */           if (eventQueueItem.event.getID() == paramInt) {
/*  649 */             return eventQueueItem.event;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  654 */       this.pushPopLock.unlock();
/*      */     } 
/*      */     
/*  657 */     return null;
/*      */   }
/*      */ 
/*      */   
/*  661 */   private static final JavaSecurityAccess javaSecurityAccess = SharedSecrets.getJavaSecurityAccess();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dispatchEvent(final AWTEvent event) {
/*  702 */     final Object src = event.getSource();
/*  703 */     final PrivilegedAction<Void> action = new PrivilegedAction<Void>()
/*      */       {
/*      */         
/*      */         public Void run()
/*      */         {
/*  708 */           if (EventQueue.this.fwDispatcher == null || EventQueue.this.isDispatchThreadImpl()) {
/*  709 */             EventQueue.this.dispatchEventImpl(event, src);
/*      */           } else {
/*  711 */             EventQueue.this.fwDispatcher.scheduleDispatch(new Runnable()
/*      */                 {
/*      */                   public void run() {
/*  714 */                     if (EventQueue.this.dispatchThread.filterAndCheckEvent(event)) {
/*  715 */                       EventQueue.this.dispatchEventImpl(event, src);
/*      */                     }
/*      */                   }
/*      */                 });
/*      */           } 
/*  720 */           return null;
/*      */         }
/*      */       };
/*      */     
/*  724 */     AccessControlContext accessControlContext1 = AccessController.getContext();
/*  725 */     AccessControlContext accessControlContext2 = getAccessControlContextFrom(object);
/*  726 */     final AccessControlContext eventAcc = event.getAccessControlContext();
/*  727 */     if (accessControlContext2 == null) {
/*  728 */       javaSecurityAccess.doIntersectionPrivilege(privilegedAction, accessControlContext1, accessControlContext3);
/*      */     } else {
/*  730 */       javaSecurityAccess.doIntersectionPrivilege(new PrivilegedAction<Void>()
/*      */           {
/*      */             public Void run() {
/*  733 */               EventQueue.javaSecurityAccess.doIntersectionPrivilege(action, eventAcc);
/*  734 */               return null;
/*      */             }
/*      */           }accessControlContext1, accessControlContext2);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static AccessControlContext getAccessControlContextFrom(Object paramObject) {
/*  741 */     return (paramObject instanceof Component) ? ((Component)paramObject)
/*  742 */       .getAccessControlContext() : ((paramObject instanceof MenuComponent) ? ((MenuComponent)paramObject)
/*      */       
/*  744 */       .getAccessControlContext() : ((paramObject instanceof TrayIcon) ? ((TrayIcon)paramObject)
/*      */       
/*  746 */       .getAccessControlContext() : null));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void dispatchEventImpl(AWTEvent paramAWTEvent, Object paramObject) {
/*  754 */     paramAWTEvent.isPosted = true;
/*  755 */     if (paramAWTEvent instanceof ActiveEvent) {
/*      */       
/*  757 */       setCurrentEventAndMostRecentTimeImpl(paramAWTEvent);
/*  758 */       ((ActiveEvent)paramAWTEvent).dispatch();
/*  759 */     } else if (paramObject instanceof Component) {
/*  760 */       ((Component)paramObject).dispatchEvent(paramAWTEvent);
/*  761 */       paramAWTEvent.dispatched();
/*  762 */     } else if (paramObject instanceof MenuComponent) {
/*  763 */       ((MenuComponent)paramObject).dispatchEvent(paramAWTEvent);
/*  764 */     } else if (paramObject instanceof TrayIcon) {
/*  765 */       ((TrayIcon)paramObject).dispatchEvent(paramAWTEvent);
/*  766 */     } else if (paramObject instanceof AWTAutoShutdown) {
/*  767 */       if (noEvents()) {
/*  768 */         this.dispatchThread.stopDispatching();
/*      */       }
/*      */     }
/*  771 */     else if (getEventLog().isLoggable(PlatformLogger.Level.FINE)) {
/*  772 */       getEventLog().fine("Unable to dispatch event: " + paramAWTEvent);
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
/*      */   public static long getMostRecentEventTime() {
/*  806 */     return Toolkit.getEventQueue().getMostRecentEventTimeImpl();
/*      */   }
/*      */   private long getMostRecentEventTimeImpl() {
/*  809 */     this.pushPopLock.lock();
/*      */     try {
/*  811 */       return (Thread.currentThread() == this.dispatchThread) ? this.mostRecentEventTime : 
/*      */         
/*  813 */         System.currentTimeMillis();
/*      */     } finally {
/*  815 */       this.pushPopLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   long getMostRecentEventTimeEx() {
/*  823 */     this.pushPopLock.lock();
/*      */     try {
/*  825 */       return this.mostRecentEventTime;
/*      */     } finally {
/*  827 */       this.pushPopLock.unlock();
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
/*      */   public static AWTEvent getCurrentEvent() {
/*  844 */     return Toolkit.getEventQueue().getCurrentEventImpl();
/*      */   }
/*      */   private AWTEvent getCurrentEventImpl() {
/*  847 */     this.pushPopLock.lock();
/*      */     try {
/*  849 */       return (Thread.currentThread() == this.dispatchThread) ? this.currentEvent
/*  850 */         .get() : null;
/*      */     } finally {
/*      */       
/*  853 */       this.pushPopLock.unlock();
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
/*      */   public void push(EventQueue paramEventQueue) {
/*  869 */     if (getEventLog().isLoggable(PlatformLogger.Level.FINE)) {
/*  870 */       getEventLog().fine("EventQueue.push(" + paramEventQueue + ")");
/*      */     }
/*      */     
/*  873 */     this.pushPopLock.lock();
/*      */     try {
/*  875 */       EventQueue eventQueue = this;
/*  876 */       while (eventQueue.nextQueue != null) {
/*  877 */         eventQueue = eventQueue.nextQueue;
/*      */       }
/*  879 */       if (eventQueue.fwDispatcher != null) {
/*  880 */         throw new RuntimeException("push() to queue with fwDispatcher");
/*      */       }
/*  882 */       if (eventQueue.dispatchThread != null && eventQueue.dispatchThread
/*  883 */         .getEventQueue() == this) {
/*      */         
/*  885 */         paramEventQueue.dispatchThread = eventQueue.dispatchThread;
/*  886 */         eventQueue.dispatchThread.setEventQueue(paramEventQueue);
/*      */       } 
/*      */ 
/*      */       
/*  890 */       while (eventQueue.peekEvent() != null) {
/*      */         
/*      */         try {
/*  893 */           paramEventQueue.postEventPrivate(eventQueue.getNextEventPrivate());
/*  894 */         } catch (InterruptedException interruptedException) {
/*  895 */           if (getEventLog().isLoggable(PlatformLogger.Level.FINE)) {
/*  896 */             getEventLog().fine("Interrupted push", interruptedException);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  901 */       if (eventQueue.dispatchThread != null)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  906 */         eventQueue.postEventPrivate(new InvocationEvent(eventQueue, dummyRunnable));
/*      */       }
/*      */       
/*  909 */       paramEventQueue.previousQueue = eventQueue;
/*  910 */       eventQueue.nextQueue = paramEventQueue;
/*      */       
/*  912 */       if (this.appContext.get(AppContext.EVENT_QUEUE_KEY) == eventQueue) {
/*  913 */         this.appContext.put(AppContext.EVENT_QUEUE_KEY, paramEventQueue);
/*      */       }
/*      */       
/*  916 */       this.pushPopCond.signalAll();
/*      */     } finally {
/*  918 */       this.pushPopLock.unlock();
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
/*      */   protected void pop() throws EmptyStackException {
/*  936 */     if (getEventLog().isLoggable(PlatformLogger.Level.FINE)) {
/*  937 */       getEventLog().fine("EventQueue.pop(" + this + ")");
/*      */     }
/*      */     
/*  940 */     this.pushPopLock.lock();
/*      */     try {
/*  942 */       EventQueue eventQueue1 = this;
/*  943 */       while (eventQueue1.nextQueue != null) {
/*  944 */         eventQueue1 = eventQueue1.nextQueue;
/*      */       }
/*  946 */       EventQueue eventQueue2 = eventQueue1.previousQueue;
/*  947 */       if (eventQueue2 == null) {
/*  948 */         throw new EmptyStackException();
/*      */       }
/*      */       
/*  951 */       eventQueue1.previousQueue = null;
/*  952 */       eventQueue2.nextQueue = null;
/*      */ 
/*      */       
/*  955 */       while (eventQueue1.peekEvent() != null) {
/*      */         try {
/*  957 */           eventQueue2.postEventPrivate(eventQueue1.getNextEventPrivate());
/*  958 */         } catch (InterruptedException interruptedException) {
/*  959 */           if (getEventLog().isLoggable(PlatformLogger.Level.FINE)) {
/*  960 */             getEventLog().fine("Interrupted pop", interruptedException);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  965 */       if (eventQueue1.dispatchThread != null && eventQueue1.dispatchThread
/*  966 */         .getEventQueue() == this) {
/*      */         
/*  968 */         eventQueue2.dispatchThread = eventQueue1.dispatchThread;
/*  969 */         eventQueue1.dispatchThread.setEventQueue(eventQueue2);
/*      */       } 
/*      */       
/*  972 */       if (this.appContext.get(AppContext.EVENT_QUEUE_KEY) == this) {
/*  973 */         this.appContext.put(AppContext.EVENT_QUEUE_KEY, eventQueue2);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  978 */       eventQueue1.postEventPrivate(new InvocationEvent(eventQueue1, dummyRunnable));
/*      */       
/*  980 */       this.pushPopCond.signalAll();
/*      */     } finally {
/*  982 */       this.pushPopLock.unlock();
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
/*      */   public SecondaryLoop createSecondaryLoop() {
/* 1002 */     return createSecondaryLoop(null, null, 0L);
/*      */   }
/*      */   
/*      */   private class FwSecondaryLoopWrapper implements SecondaryLoop {
/*      */     private final SecondaryLoop loop;
/*      */     private final EventFilter filter;
/*      */     
/*      */     public FwSecondaryLoopWrapper(SecondaryLoop param1SecondaryLoop, EventFilter param1EventFilter) {
/* 1010 */       this.loop = param1SecondaryLoop;
/* 1011 */       this.filter = param1EventFilter;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean enter() {
/* 1016 */       if (this.filter != null) {
/* 1017 */         EventQueue.this.dispatchThread.addEventFilter(this.filter);
/*      */       }
/* 1019 */       return this.loop.enter();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean exit() {
/* 1024 */       if (this.filter != null) {
/* 1025 */         EventQueue.this.dispatchThread.removeEventFilter(this.filter);
/*      */       }
/* 1027 */       return this.loop.exit();
/*      */     }
/*      */   }
/*      */   
/*      */   SecondaryLoop createSecondaryLoop(Conditional paramConditional, EventFilter paramEventFilter, long paramLong) {
/* 1032 */     this.pushPopLock.lock();
/*      */     try {
/* 1034 */       if (this.nextQueue != null)
/*      */       {
/* 1036 */         return this.nextQueue.createSecondaryLoop(paramConditional, paramEventFilter, paramLong);
/*      */       }
/* 1038 */       if (this.fwDispatcher != null) {
/* 1039 */         return new FwSecondaryLoopWrapper(this.fwDispatcher.createSecondaryLoop(), paramEventFilter);
/*      */       }
/* 1041 */       if (this.dispatchThread == null) {
/* 1042 */         initDispatchThread();
/*      */       }
/* 1044 */       return new WaitDispatchSupport(this.dispatchThread, paramConditional, paramEventFilter, paramLong);
/*      */     } finally {
/* 1046 */       this.pushPopLock.unlock();
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
/*      */   public static boolean isDispatchThread() {
/* 1071 */     EventQueue eventQueue = Toolkit.getEventQueue();
/* 1072 */     return eventQueue.isDispatchThreadImpl();
/*      */   }
/*      */   
/*      */   final boolean isDispatchThreadImpl() {
/* 1076 */     EventQueue eventQueue = this;
/* 1077 */     this.pushPopLock.lock();
/*      */     try {
/* 1079 */       EventQueue eventQueue1 = eventQueue.nextQueue;
/* 1080 */       while (eventQueue1 != null) {
/* 1081 */         eventQueue = eventQueue1;
/* 1082 */         eventQueue1 = eventQueue.nextQueue;
/*      */       } 
/* 1084 */       if (eventQueue.fwDispatcher != null) {
/* 1085 */         return eventQueue.fwDispatcher.isDispatchThread();
/*      */       }
/* 1087 */       return (Thread.currentThread() == eventQueue.dispatchThread);
/*      */     } finally {
/* 1089 */       this.pushPopLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   final void initDispatchThread() {
/* 1094 */     this.pushPopLock.lock();
/*      */     try {
/* 1096 */       if (this.dispatchThread == null && !this.threadGroup.isDestroyed() && !this.appContext.isDisposed()) {
/* 1097 */         this.dispatchThread = AccessController.<EventDispatchThread>doPrivileged(new PrivilegedAction<EventDispatchThread>()
/*      */             {
/*      */               
/*      */               public EventDispatchThread run()
/*      */               {
/* 1102 */                 EventDispatchThread eventDispatchThread = new EventDispatchThread(EventQueue.this.threadGroup, EventQueue.this.name, EventQueue.this);
/*      */                 
/* 1104 */                 eventDispatchThread.setContextClassLoader(EventQueue.this.classLoader);
/* 1105 */                 eventDispatchThread.setPriority(6);
/* 1106 */                 eventDispatchThread.setDaemon(false);
/* 1107 */                 AWTAutoShutdown.getInstance().notifyThreadBusy(eventDispatchThread);
/* 1108 */                 return eventDispatchThread;
/*      */               }
/*      */             });
/*      */         
/* 1112 */         this.dispatchThread.start();
/*      */       } 
/*      */     } finally {
/* 1115 */       this.pushPopLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void detachDispatchThread(EventDispatchThread paramEventDispatchThread) {
/* 1123 */     SunToolkit.flushPendingEvents(this.appContext);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1132 */     this.pushPopLock.lock();
/*      */     try {
/* 1134 */       if (paramEventDispatchThread == this.dispatchThread) {
/* 1135 */         this.dispatchThread = null;
/*      */       }
/* 1137 */       AWTAutoShutdown.getInstance().notifyThreadFree(paramEventDispatchThread);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1142 */       if (peekEvent() != null) {
/* 1143 */         initDispatchThread();
/*      */       }
/*      */     } finally {
/* 1146 */       this.pushPopLock.unlock();
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
/*      */   final EventDispatchThread getDispatchThread() {
/* 1160 */     this.pushPopLock.lock();
/*      */     try {
/* 1162 */       return this.dispatchThread;
/*      */     } finally {
/* 1164 */       this.pushPopLock.unlock();
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
/*      */   final void removeSourceEvents(Object paramObject, boolean paramBoolean) {
/* 1181 */     SunToolkit.flushPendingEvents(this.appContext);
/* 1182 */     this.pushPopLock.lock();
/*      */     try {
/* 1184 */       for (byte b = 0; b < 4; b++) {
/* 1185 */         EventQueueItem eventQueueItem1 = (this.queues[b]).head;
/* 1186 */         EventQueueItem eventQueueItem2 = null;
/* 1187 */         while (eventQueueItem1 != null) {
/* 1188 */           if (eventQueueItem1.event.getSource() == paramObject && (paramBoolean || (!(eventQueueItem1.event instanceof SequencedEvent) && !(eventQueueItem1.event instanceof SentEvent) && !(eventQueueItem1.event instanceof java.awt.event.FocusEvent) && !(eventQueueItem1.event instanceof java.awt.event.WindowEvent) && !(eventQueueItem1.event instanceof java.awt.event.KeyEvent) && !(eventQueueItem1.event instanceof InputMethodEvent)))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1197 */             if (eventQueueItem1.event instanceof SequencedEvent) {
/* 1198 */               ((SequencedEvent)eventQueueItem1.event).dispose();
/*      */             }
/* 1200 */             if (eventQueueItem1.event instanceof SentEvent) {
/* 1201 */               ((SentEvent)eventQueueItem1.event).dispose();
/*      */             }
/* 1203 */             if (eventQueueItem1.event instanceof InvocationEvent) {
/* 1204 */               AWTAccessor.getInvocationEventAccessor()
/* 1205 */                 .dispose((InvocationEvent)eventQueueItem1.event);
/*      */             }
/* 1207 */             if (eventQueueItem2 == null) {
/* 1208 */               (this.queues[b]).head = eventQueueItem1.next;
/*      */             } else {
/* 1210 */               eventQueueItem2.next = eventQueueItem1.next;
/*      */             } 
/* 1212 */             uncacheEQItem(eventQueueItem1);
/*      */           } else {
/* 1214 */             eventQueueItem2 = eventQueueItem1;
/*      */           } 
/* 1216 */           eventQueueItem1 = eventQueueItem1.next;
/*      */         } 
/* 1218 */         (this.queues[b]).tail = eventQueueItem2;
/*      */       } 
/*      */     } finally {
/* 1221 */       this.pushPopLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   synchronized long getMostRecentKeyEventTime() {
/* 1226 */     this.pushPopLock.lock();
/*      */     try {
/* 1228 */       return this.mostRecentKeyEventTime;
/*      */     } finally {
/* 1230 */       this.pushPopLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   static void setCurrentEventAndMostRecentTime(AWTEvent paramAWTEvent) {
/* 1235 */     Toolkit.getEventQueue().setCurrentEventAndMostRecentTimeImpl(paramAWTEvent);
/*      */   }
/*      */   private void setCurrentEventAndMostRecentTimeImpl(AWTEvent paramAWTEvent) {
/* 1238 */     this.pushPopLock.lock();
/*      */     try {
/* 1240 */       if (Thread.currentThread() != this.dispatchThread) {
/*      */         return;
/*      */       }
/*      */       
/* 1244 */       this.currentEvent = new WeakReference<>(paramAWTEvent);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1254 */       long l = Long.MIN_VALUE;
/* 1255 */       if (paramAWTEvent instanceof InputEvent) {
/* 1256 */         InputEvent inputEvent = (InputEvent)paramAWTEvent;
/* 1257 */         l = inputEvent.getWhen();
/* 1258 */         if (paramAWTEvent instanceof java.awt.event.KeyEvent) {
/* 1259 */           this.mostRecentKeyEventTime = inputEvent.getWhen();
/*      */         }
/* 1261 */       } else if (paramAWTEvent instanceof InputMethodEvent) {
/* 1262 */         InputMethodEvent inputMethodEvent = (InputMethodEvent)paramAWTEvent;
/* 1263 */         l = inputMethodEvent.getWhen();
/* 1264 */       } else if (paramAWTEvent instanceof ActionEvent) {
/* 1265 */         ActionEvent actionEvent = (ActionEvent)paramAWTEvent;
/* 1266 */         l = actionEvent.getWhen();
/* 1267 */       } else if (paramAWTEvent instanceof InvocationEvent) {
/* 1268 */         InvocationEvent invocationEvent = (InvocationEvent)paramAWTEvent;
/* 1269 */         l = invocationEvent.getWhen();
/*      */       } 
/* 1271 */       this.mostRecentEventTime = Math.max(this.mostRecentEventTime, l);
/*      */     } finally {
/* 1273 */       this.pushPopLock.unlock();
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
/*      */   public static void invokeLater(Runnable paramRunnable) {
/* 1294 */     Toolkit.getEventQueue().postEvent(new InvocationEvent(
/* 1295 */           Toolkit.getDefaultToolkit(), paramRunnable));
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
/*      */   public static void invokeAndWait(Runnable paramRunnable) throws InterruptedException, InvocationTargetException {
/* 1324 */     invokeAndWait(Toolkit.getDefaultToolkit(), paramRunnable);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void invokeAndWait(Object paramObject, Runnable paramRunnable) throws InterruptedException, InvocationTargetException {
/* 1330 */     if (isDispatchThread()) {
/* 1331 */       throw new Error("Cannot call invokeAndWait from the event dispatcher thread");
/*      */     }
/*      */     
/*      */     class AWTInvocationLock {};
/* 1335 */     AWTInvocationLock aWTInvocationLock = new AWTInvocationLock();
/*      */     
/* 1337 */     InvocationEvent invocationEvent = new InvocationEvent(paramObject, paramRunnable, aWTInvocationLock, true);
/*      */ 
/*      */     
/* 1340 */     synchronized (aWTInvocationLock) {
/* 1341 */       Toolkit.getEventQueue().postEvent(invocationEvent);
/* 1342 */       while (!invocationEvent.isDispatched()) {
/* 1343 */         aWTInvocationLock.wait();
/*      */       }
/*      */     } 
/*      */     
/* 1347 */     Throwable throwable = invocationEvent.getThrowable();
/* 1348 */     if (throwable != null) {
/* 1349 */       throw new InvocationTargetException(throwable);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void wakeup(boolean paramBoolean) {
/* 1360 */     this.pushPopLock.lock();
/*      */     try {
/* 1362 */       if (this.nextQueue != null) {
/*      */         
/* 1364 */         this.nextQueue.wakeup(paramBoolean);
/* 1365 */       } else if (this.dispatchThread != null) {
/* 1366 */         this.pushPopCond.signalAll();
/* 1367 */       } else if (!paramBoolean) {
/* 1368 */         initDispatchThread();
/*      */       } 
/*      */     } finally {
/* 1371 */       this.pushPopLock.unlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setFwDispatcher(FwDispatcher paramFwDispatcher) {
/* 1377 */     if (this.nextQueue != null) {
/* 1378 */       this.nextQueue.setFwDispatcher(paramFwDispatcher);
/*      */     } else {
/* 1380 */       this.fwDispatcher = paramFwDispatcher;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/EventQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */