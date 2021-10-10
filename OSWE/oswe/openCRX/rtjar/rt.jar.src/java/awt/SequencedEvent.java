/*     */ package java.awt;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.SunToolkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SequencedEvent
/*     */   extends AWTEvent
/*     */   implements ActiveEvent
/*     */ {
/*     */   private static final long serialVersionUID = 547742659238625067L;
/*     */   private static final int ID = 1006;
/*  53 */   private static final LinkedList<SequencedEvent> list = new LinkedList<>();
/*     */   
/*     */   private final AWTEvent nested;
/*     */   private AppContext appContext;
/*     */   private boolean disposed;
/*  58 */   private final LinkedList<AWTEvent> pendingEvents = new LinkedList<>();
/*     */   
/*     */   static {
/*  61 */     AWTAccessor.setSequencedEventAccessor(new AWTAccessor.SequencedEventAccessor() {
/*     */           public AWTEvent getNested(AWTEvent param1AWTEvent) {
/*  63 */             return ((SequencedEvent)param1AWTEvent).nested;
/*     */           }
/*     */           public boolean isSequencedEvent(AWTEvent param1AWTEvent) {
/*  66 */             return param1AWTEvent instanceof SequencedEvent;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static final class SequencedEventsFilter implements EventFilter { private final SequencedEvent currentSequencedEvent;
/*     */     
/*     */     private SequencedEventsFilter(SequencedEvent param1SequencedEvent) {
/*  74 */       this.currentSequencedEvent = param1SequencedEvent;
/*     */     }
/*     */     
/*     */     public EventFilter.FilterAction acceptEvent(AWTEvent param1AWTEvent) {
/*  78 */       if (param1AWTEvent.getID() == 1006) {
/*     */ 
/*     */         
/*  81 */         synchronized (SequencedEvent.class) {
/*  82 */           Iterator<SequencedEvent> iterator = SequencedEvent.list.iterator();
/*  83 */           while (iterator.hasNext()) {
/*  84 */             SequencedEvent sequencedEvent = iterator.next();
/*  85 */             if (sequencedEvent.equals(this.currentSequencedEvent))
/*     */               break; 
/*  87 */             if (sequencedEvent.equals(param1AWTEvent)) {
/*  88 */               return EventFilter.FilterAction.ACCEPT;
/*     */             }
/*     */           } 
/*     */         } 
/*  92 */       } else if (param1AWTEvent.getID() == 1007) {
/*  93 */         return EventFilter.FilterAction.ACCEPT;
/*     */       } 
/*  95 */       this.currentSequencedEvent.pendingEvents.add(param1AWTEvent);
/*  96 */       return EventFilter.FilterAction.REJECT;
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
/*     */   public SequencedEvent(AWTEvent paramAWTEvent) {
/* 108 */     super(paramAWTEvent.getSource(), 1006);
/* 109 */     this.nested = paramAWTEvent;
/*     */ 
/*     */     
/* 112 */     SunToolkit.setSystemGenerated(paramAWTEvent);
/* 113 */     synchronized (SequencedEvent.class) {
/* 114 */       list.add(this);
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
/*     */   public final void dispatch() {
/*     */     try {
/* 132 */       this.appContext = AppContext.getAppContext();
/*     */       
/* 134 */       if (getFirst() != this) {
/* 135 */         if (EventQueue.isDispatchThread()) {
/*     */           
/* 137 */           EventDispatchThread eventDispatchThread = (EventDispatchThread)Thread.currentThread();
/* 138 */           eventDispatchThread.pumpEventsForFilter(() -> !isFirstOrDisposed(), new SequencedEventsFilter(this));
/*     */         } else {
/*     */           
/* 141 */           while (!isFirstOrDisposed()) {
/* 142 */             synchronized (SequencedEvent.class) {
/*     */               try {
/* 144 */                 SequencedEvent.class.wait(1000L);
/* 145 */               } catch (InterruptedException interruptedException) {
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 153 */       if (!this.disposed) {
/* 154 */         KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 155 */           .setCurrentSequencedEvent(this);
/* 156 */         Toolkit.getEventQueue().dispatchEvent(this.nested);
/*     */       } 
/*     */     } finally {
/* 159 */       dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean isOwnerAppContextDisposed(SequencedEvent paramSequencedEvent) {
/* 167 */     if (paramSequencedEvent != null) {
/* 168 */       Object object = paramSequencedEvent.nested.getSource();
/* 169 */       if (object instanceof Component) {
/* 170 */         return ((Component)object).appContext.isDisposed();
/*     */       }
/*     */     } 
/* 173 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isFirstOrDisposed() {
/* 183 */     if (this.disposed) {
/* 184 */       return true;
/*     */     }
/*     */     
/* 187 */     return (this == getFirstWithContext() || this.disposed);
/*     */   }
/*     */   
/*     */   private static final synchronized SequencedEvent getFirst() {
/* 191 */     return list.getFirst();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final SequencedEvent getFirstWithContext() {
/* 198 */     SequencedEvent sequencedEvent = getFirst();
/* 199 */     while (isOwnerAppContextDisposed(sequencedEvent)) {
/* 200 */       sequencedEvent.dispose();
/* 201 */       sequencedEvent = getFirst();
/*     */     } 
/* 203 */     return sequencedEvent;
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
/*     */   final void dispose() {
/* 217 */     synchronized (SequencedEvent.class) {
/* 218 */       if (this.disposed) {
/*     */         return;
/*     */       }
/* 221 */       if (KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 222 */         .getCurrentSequencedEvent() == this) {
/* 223 */         KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 224 */           .setCurrentSequencedEvent(null);
/*     */       }
/* 226 */       this.disposed = true;
/*     */     } 
/*     */     
/* 229 */     SequencedEvent sequencedEvent = null;
/*     */     
/* 231 */     synchronized (SequencedEvent.class) {
/* 232 */       SequencedEvent.class.notifyAll();
/*     */       
/* 234 */       if (list.getFirst() == this) {
/* 235 */         list.removeFirst();
/*     */         
/* 237 */         if (!list.isEmpty()) {
/* 238 */           sequencedEvent = list.getFirst();
/*     */         }
/*     */       } else {
/* 241 */         list.remove(this);
/*     */       } 
/*     */     } 
/*     */     
/* 245 */     if (sequencedEvent != null && sequencedEvent.appContext != null) {
/* 246 */       SunToolkit.postEvent(sequencedEvent.appContext, new SentEvent());
/*     */     }
/*     */     
/* 249 */     for (AWTEvent aWTEvent : this.pendingEvents)
/* 250 */       SunToolkit.postEvent(this.appContext, aWTEvent); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/SequencedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */