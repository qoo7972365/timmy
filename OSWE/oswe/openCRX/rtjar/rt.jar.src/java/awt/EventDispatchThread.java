/*     */ package java.awt;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import sun.awt.EventQueueDelegate;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.dnd.SunDragSourceContextPeer;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EventDispatchThread
/*     */   extends Thread
/*     */ {
/*  59 */   private static final PlatformLogger eventLog = PlatformLogger.getLogger("java.awt.event.EventDispatchThread");
/*     */   
/*     */   private EventQueue theQueue;
/*     */   
/*     */   private volatile boolean doDispatch = true;
/*     */   
/*     */   private static final int ANY_EVENT = -1;
/*  66 */   private ArrayList<EventFilter> eventFilters = new ArrayList<>();
/*     */   
/*     */   EventDispatchThread(ThreadGroup paramThreadGroup, String paramString, EventQueue paramEventQueue) {
/*  69 */     super(paramThreadGroup, paramString);
/*  70 */     setEventQueue(paramEventQueue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopDispatching() {
/*  77 */     this.doDispatch = false;
/*     */   }
/*     */   
/*     */   public void run() {
/*     */     try {
/*  82 */       pumpEvents(new Conditional() {
/*     */             public boolean evaluate() {
/*  84 */               return true;
/*     */             }
/*     */           });
/*     */     } finally {
/*  88 */       getEventQueue().detachDispatchThread(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   void pumpEvents(Conditional paramConditional) {
/*  93 */     pumpEvents(-1, paramConditional);
/*     */   }
/*     */   
/*     */   void pumpEventsForHierarchy(Conditional paramConditional, Component paramComponent) {
/*  97 */     pumpEventsForHierarchy(-1, paramConditional, paramComponent);
/*     */   }
/*     */   
/*     */   void pumpEvents(int paramInt, Conditional paramConditional) {
/* 101 */     pumpEventsForHierarchy(paramInt, paramConditional, (Component)null);
/*     */   }
/*     */   
/*     */   void pumpEventsForHierarchy(int paramInt, Conditional paramConditional, Component paramComponent) {
/* 105 */     pumpEventsForFilter(paramInt, paramConditional, new HierarchyEventFilter(paramComponent));
/*     */   }
/*     */   
/*     */   void pumpEventsForFilter(Conditional paramConditional, EventFilter paramEventFilter) {
/* 109 */     pumpEventsForFilter(-1, paramConditional, paramEventFilter);
/*     */   }
/*     */   
/*     */   void pumpEventsForFilter(int paramInt, Conditional paramConditional, EventFilter paramEventFilter) {
/* 113 */     addEventFilter(paramEventFilter);
/* 114 */     this.doDispatch = true;
/* 115 */     while (this.doDispatch && !isInterrupted() && paramConditional.evaluate()) {
/* 116 */       pumpOneEventForFilters(paramInt);
/*     */     }
/* 118 */     removeEventFilter(paramEventFilter);
/*     */   }
/*     */   
/*     */   void addEventFilter(EventFilter paramEventFilter) {
/* 122 */     if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 123 */       eventLog.finest("adding the event filter: " + paramEventFilter);
/*     */     }
/* 125 */     synchronized (this.eventFilters) {
/* 126 */       if (!this.eventFilters.contains(paramEventFilter)) {
/* 127 */         if (paramEventFilter instanceof ModalEventFilter) {
/* 128 */           ModalEventFilter modalEventFilter = (ModalEventFilter)paramEventFilter;
/* 129 */           byte b = 0;
/* 130 */           for (b = 0; b < this.eventFilters.size(); b++) {
/* 131 */             EventFilter eventFilter = this.eventFilters.get(b);
/* 132 */             if (eventFilter instanceof ModalEventFilter) {
/* 133 */               ModalEventFilter modalEventFilter1 = (ModalEventFilter)eventFilter;
/* 134 */               if (modalEventFilter1.compareTo(modalEventFilter) > 0) {
/*     */                 break;
/*     */               }
/*     */             } 
/*     */           } 
/* 139 */           this.eventFilters.add(b, paramEventFilter);
/*     */         } else {
/* 141 */           this.eventFilters.add(paramEventFilter);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void removeEventFilter(EventFilter paramEventFilter) {
/* 148 */     if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 149 */       eventLog.finest("removing the event filter: " + paramEventFilter);
/*     */     }
/* 151 */     synchronized (this.eventFilters) {
/* 152 */       this.eventFilters.remove(paramEventFilter);
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean filterAndCheckEvent(AWTEvent paramAWTEvent) {
/* 157 */     boolean bool = true;
/* 158 */     synchronized (this.eventFilters) {
/* 159 */       for (int i = this.eventFilters.size() - 1; i >= 0; i--) {
/* 160 */         EventFilter eventFilter = this.eventFilters.get(i);
/* 161 */         EventFilter.FilterAction filterAction = eventFilter.acceptEvent(paramAWTEvent);
/* 162 */         if (filterAction == EventFilter.FilterAction.REJECT) {
/* 163 */           bool = false; break;
/*     */         } 
/* 165 */         if (filterAction == EventFilter.FilterAction.ACCEPT_IMMEDIATELY) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/* 170 */     return (bool && SunDragSourceContextPeer.checkEvent(paramAWTEvent));
/*     */   }
/*     */   
/*     */   void pumpOneEventForFilters(int paramInt) {
/* 174 */     AWTEvent aWTEvent = null;
/* 175 */     boolean bool = false;
/*     */     try {
/* 177 */       EventQueue eventQueue = null;
/* 178 */       EventQueueDelegate.Delegate delegate = null;
/*     */       
/*     */       do {
/* 181 */         eventQueue = getEventQueue();
/* 182 */         delegate = EventQueueDelegate.getDelegate();
/*     */         
/* 184 */         if (delegate != null && paramInt == -1) {
/* 185 */           aWTEvent = delegate.getNextEvent(eventQueue);
/*     */         } else {
/* 187 */           aWTEvent = (paramInt == -1) ? eventQueue.getNextEvent() : eventQueue.getNextEvent(paramInt);
/*     */         } 
/*     */         
/* 190 */         bool = filterAndCheckEvent(aWTEvent);
/* 191 */         if (bool)
/* 192 */           continue;  aWTEvent.consume();
/*     */       
/*     */       }
/* 195 */       while (!bool);
/*     */       
/* 197 */       if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 198 */         eventLog.finest("Dispatching: " + aWTEvent);
/*     */       }
/*     */       
/* 201 */       Object object = null;
/* 202 */       if (delegate != null) {
/* 203 */         object = delegate.beforeDispatch(aWTEvent);
/*     */       }
/* 205 */       eventQueue.dispatchEvent(aWTEvent);
/* 206 */       if (delegate != null) {
/* 207 */         delegate.afterDispatch(aWTEvent, object);
/*     */       }
/*     */     }
/* 210 */     catch (ThreadDeath threadDeath) {
/* 211 */       this.doDispatch = false;
/* 212 */       throw threadDeath;
/*     */     }
/* 214 */     catch (InterruptedException interruptedException) {
/* 215 */       this.doDispatch = false;
/*     */     
/*     */     }
/* 218 */     catch (Throwable throwable) {
/* 219 */       processException(throwable);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processException(Throwable paramThrowable) {
/* 224 */     if (eventLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 225 */       eventLog.fine("Processing exception: " + paramThrowable);
/*     */     }
/* 227 */     getUncaughtExceptionHandler().uncaughtException(this, paramThrowable);
/*     */   }
/*     */   
/*     */   public synchronized EventQueue getEventQueue() {
/* 231 */     return this.theQueue;
/*     */   }
/*     */   public synchronized void setEventQueue(EventQueue paramEventQueue) {
/* 234 */     this.theQueue = paramEventQueue;
/*     */   }
/*     */   
/*     */   private static class HierarchyEventFilter implements EventFilter { private Component modalComponent;
/*     */     
/*     */     public HierarchyEventFilter(Component param1Component) {
/* 240 */       this.modalComponent = param1Component;
/*     */     }
/*     */     public EventFilter.FilterAction acceptEvent(AWTEvent param1AWTEvent) {
/* 243 */       if (this.modalComponent != null) {
/* 244 */         int i = param1AWTEvent.getID();
/* 245 */         boolean bool1 = (i >= 500 && i <= 507) ? true : false;
/*     */         
/* 247 */         boolean bool2 = (i >= 1001 && i <= 1001) ? true : false;
/*     */         
/* 249 */         boolean bool3 = (i == 201) ? true : false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 256 */         if (Component.isInstanceOf(this.modalComponent, "javax.swing.JInternalFrame"))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 263 */           return bool3 ? EventFilter.FilterAction.REJECT : EventFilter.FilterAction.ACCEPT;
/*     */         }
/* 265 */         if (bool1 || bool2 || bool3) {
/* 266 */           Object object = param1AWTEvent.getSource();
/* 267 */           if (object instanceof sun.awt.ModalExclude)
/*     */           {
/*     */             
/* 270 */             return EventFilter.FilterAction.ACCEPT; } 
/* 271 */           if (object instanceof Component) {
/* 272 */             Component component = (Component)object;
/*     */             
/* 274 */             boolean bool = false;
/* 275 */             if (this.modalComponent instanceof Container) {
/* 276 */               while (component != this.modalComponent && component != null) {
/* 277 */                 if (component instanceof Window && 
/* 278 */                   SunToolkit.isModalExcluded((Window)component)) {
/*     */ 
/*     */                   
/* 281 */                   bool = true;
/*     */                   break;
/*     */                 } 
/* 284 */                 component = component.getParent();
/*     */               } 
/*     */             }
/* 287 */             if (!bool && component != this.modalComponent) {
/* 288 */               return EventFilter.FilterAction.REJECT;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 293 */       return EventFilter.FilterAction.ACCEPT;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/EventDispatchThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */