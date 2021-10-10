/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.peer.ComponentPeer;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Set;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.CausedFocusEvent;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.awt.TimedWindowEvent;
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
/*      */ public class DefaultKeyboardFocusManager
/*      */   extends KeyboardFocusManager
/*      */ {
/*   65 */   private static final PlatformLogger focusLog = PlatformLogger.getLogger("java.awt.focus.DefaultKeyboardFocusManager");
/*      */ 
/*      */   
/*   68 */   private static final WeakReference<Window> NULL_WINDOW_WR = new WeakReference<>(null);
/*      */   
/*   70 */   private static final WeakReference<Component> NULL_COMPONENT_WR = new WeakReference<>(null);
/*      */   
/*   72 */   private WeakReference<Window> realOppositeWindowWR = NULL_WINDOW_WR;
/*   73 */   private WeakReference<Component> realOppositeComponentWR = NULL_COMPONENT_WR;
/*      */   private int inSendMessage;
/*   75 */   private LinkedList<KeyEvent> enqueuedKeyEvents = new LinkedList<>();
/*   76 */   private LinkedList<TypeAheadMarker> typeAheadMarkers = new LinkedList<>();
/*      */   private boolean consumeNextKeyTyped;
/*      */   private Component restoreFocusTo;
/*      */   
/*      */   static {
/*   81 */     AWTAccessor.setDefaultKeyboardFocusManagerAccessor(new AWTAccessor.DefaultKeyboardFocusManagerAccessor()
/*      */         {
/*      */           public void consumeNextKeyTyped(DefaultKeyboardFocusManager param1DefaultKeyboardFocusManager, KeyEvent param1KeyEvent) {
/*   84 */             param1DefaultKeyboardFocusManager.consumeNextKeyTyped(param1KeyEvent);
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   private static class TypeAheadMarker {
/*      */     long after;
/*      */     Component untilFocused;
/*      */     
/*      */     TypeAheadMarker(long param1Long, Component param1Component) {
/*   94 */       this.after = param1Long;
/*   95 */       this.untilFocused = param1Component;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  101 */       return ">>> Marker after " + this.after + " on " + this.untilFocused;
/*      */     }
/*      */   }
/*      */   
/*      */   private Window getOwningFrameDialog(Window paramWindow) {
/*  106 */     while (paramWindow != null && !(paramWindow instanceof Frame) && !(paramWindow instanceof Dialog))
/*      */     {
/*  108 */       paramWindow = (Window)paramWindow.getParent();
/*      */     }
/*  110 */     return paramWindow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void restoreFocus(FocusEvent paramFocusEvent, Window paramWindow) {
/*  119 */     Component component1 = this.realOppositeComponentWR.get();
/*  120 */     Component component2 = paramFocusEvent.getComponent();
/*      */     
/*  122 */     if (paramWindow == null || !restoreFocus(paramWindow, component2, false))
/*      */     {
/*      */       
/*  125 */       if ((component1 == null || 
/*  126 */         !doRestoreFocus(component1, component2, false)) && (
/*  127 */         paramFocusEvent.getOppositeComponent() == null || 
/*  128 */         !doRestoreFocus(paramFocusEvent.getOppositeComponent(), component2, false)))
/*      */       {
/*  130 */         clearGlobalFocusOwnerPriv(); }  } 
/*      */   }
/*      */   
/*      */   private void restoreFocus(WindowEvent paramWindowEvent) {
/*  134 */     Window window = this.realOppositeWindowWR.get();
/*  135 */     if (window == null || 
/*  136 */       !restoreFocus(window, (Component)null, false))
/*      */     {
/*      */       
/*  139 */       if (paramWindowEvent.getOppositeWindow() == null || 
/*  140 */         !restoreFocus(paramWindowEvent.getOppositeWindow(), (Component)null, false))
/*      */       {
/*      */ 
/*      */         
/*  144 */         clearGlobalFocusOwnerPriv(); } 
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean restoreFocus(Window paramWindow, Component paramComponent, boolean paramBoolean) {
/*  149 */     this.restoreFocusTo = null;
/*      */     
/*  151 */     Component component = KeyboardFocusManager.getMostRecentFocusOwner(paramWindow);
/*      */     
/*  153 */     if (component != null && component != paramComponent) {
/*  154 */       if (getHeavyweight(paramWindow) != getNativeFocusOwner()) {
/*      */         
/*  156 */         if (!component.isShowing() || !component.canBeFocusOwner()) {
/*  157 */           component = component.getNextFocusCandidate();
/*      */         }
/*  159 */         if (component != null && component != paramComponent) {
/*  160 */           if (!component.requestFocus(false, CausedFocusEvent.Cause.ROLLBACK))
/*      */           {
/*  162 */             this.restoreFocusTo = component;
/*      */           }
/*  164 */           return true;
/*      */         } 
/*  166 */       } else if (doRestoreFocus(component, paramComponent, false)) {
/*  167 */         return true;
/*      */       } 
/*      */     }
/*  170 */     if (paramBoolean) {
/*  171 */       clearGlobalFocusOwnerPriv();
/*  172 */       return true;
/*      */     } 
/*  174 */     return false;
/*      */   }
/*      */   
/*      */   private boolean restoreFocus(Component paramComponent, boolean paramBoolean) {
/*  178 */     return doRestoreFocus(paramComponent, (Component)null, paramBoolean);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean doRestoreFocus(Component paramComponent1, Component paramComponent2, boolean paramBoolean) {
/*  183 */     boolean bool = true;
/*  184 */     if (paramComponent1 != paramComponent2 && paramComponent1.isShowing() && paramComponent1.canBeFocusOwner() && (
/*  185 */       bool = paramComponent1.requestFocus(false, CausedFocusEvent.Cause.ROLLBACK)))
/*      */     {
/*  187 */       return true;
/*      */     }
/*  189 */     if (!bool && getGlobalFocusedWindow() != SunToolkit.getContainingWindow(paramComponent1)) {
/*  190 */       this.restoreFocusTo = paramComponent1;
/*  191 */       return true;
/*      */     } 
/*  193 */     Component component = paramComponent1.getNextFocusCandidate();
/*  194 */     if (component != null && component != paramComponent2 && component
/*  195 */       .requestFocusInWindow(CausedFocusEvent.Cause.ROLLBACK))
/*      */     {
/*  197 */       return true; } 
/*  198 */     if (paramBoolean) {
/*  199 */       clearGlobalFocusOwnerPriv();
/*  200 */       return true;
/*      */     } 
/*  202 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class DefaultKeyboardFocusManagerSentEvent
/*      */     extends SentEvent
/*      */   {
/*      */     private static final long serialVersionUID = -2924743257508701758L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DefaultKeyboardFocusManagerSentEvent(AWTEvent param1AWTEvent, AppContext param1AppContext) {
/*  222 */       super(param1AWTEvent, param1AppContext);
/*      */     }
/*      */     
/*      */     public final void dispatch() {
/*  226 */       KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/*  227 */       DefaultKeyboardFocusManager defaultKeyboardFocusManager = (keyboardFocusManager instanceof DefaultKeyboardFocusManager) ? (DefaultKeyboardFocusManager)keyboardFocusManager : null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  232 */       if (defaultKeyboardFocusManager != null) {
/*  233 */         synchronized (defaultKeyboardFocusManager) {
/*  234 */           defaultKeyboardFocusManager.inSendMessage++;
/*      */         } 
/*      */       }
/*      */       
/*  238 */       super.dispatch();
/*      */       
/*  240 */       if (defaultKeyboardFocusManager != null) {
/*  241 */         synchronized (defaultKeyboardFocusManager) {
/*  242 */           defaultKeyboardFocusManager.inSendMessage--;
/*      */         } 
/*      */       }
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
/*      */   static boolean sendMessage(Component paramComponent, AWTEvent paramAWTEvent) {
/*  258 */     paramAWTEvent.isPosted = true;
/*  259 */     AppContext appContext1 = AppContext.getAppContext();
/*  260 */     final AppContext targetAppContext = paramComponent.appContext;
/*  261 */     final DefaultKeyboardFocusManagerSentEvent se = new DefaultKeyboardFocusManagerSentEvent(paramAWTEvent, appContext1);
/*      */ 
/*      */     
/*  264 */     if (appContext1 == appContext2) {
/*  265 */       defaultKeyboardFocusManagerSentEvent.dispatch();
/*      */     } else {
/*  267 */       if (appContext2.isDisposed()) {
/*  268 */         return false;
/*      */       }
/*  270 */       SunToolkit.postEvent(appContext2, defaultKeyboardFocusManagerSentEvent);
/*  271 */       if (EventQueue.isDispatchThread()) {
/*      */         
/*  273 */         EventDispatchThread eventDispatchThread = (EventDispatchThread)Thread.currentThread();
/*  274 */         eventDispatchThread.pumpEvents(1007, new Conditional() {
/*      */               public boolean evaluate() {
/*  276 */                 return (!se.dispatched && !targetAppContext.isDisposed());
/*      */               }
/*      */             });
/*      */       } else {
/*  280 */         synchronized (defaultKeyboardFocusManagerSentEvent) {
/*  281 */           while (!defaultKeyboardFocusManagerSentEvent.dispatched && !appContext2.isDisposed()) {
/*      */             try {
/*  283 */               defaultKeyboardFocusManagerSentEvent.wait(1000L);
/*  284 */             } catch (InterruptedException interruptedException) {
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  291 */     return defaultKeyboardFocusManagerSentEvent.dispatched;
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
/*      */   private boolean repostIfFollowsKeyEvents(WindowEvent paramWindowEvent) {
/*  303 */     if (!(paramWindowEvent instanceof TimedWindowEvent)) {
/*  304 */       return false;
/*      */     }
/*  306 */     TimedWindowEvent timedWindowEvent = (TimedWindowEvent)paramWindowEvent;
/*  307 */     long l = timedWindowEvent.getWhen();
/*  308 */     synchronized (this) {
/*  309 */       KeyEvent keyEvent = this.enqueuedKeyEvents.isEmpty() ? null : this.enqueuedKeyEvents.getFirst();
/*  310 */       if (keyEvent != null && l >= keyEvent.getWhen()) {
/*  311 */         TypeAheadMarker typeAheadMarker = this.typeAheadMarkers.isEmpty() ? null : this.typeAheadMarkers.getFirst();
/*  312 */         if (typeAheadMarker != null) {
/*  313 */           Window window = typeAheadMarker.untilFocused.getContainingWindow();
/*      */ 
/*      */           
/*  316 */           if (window != null && window.isFocused()) {
/*  317 */             SunToolkit.postEvent(AppContext.getAppContext(), new SequencedEvent(paramWindowEvent));
/*  318 */             return true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  323 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean dispatchEvent(AWTEvent paramAWTEvent) {
/*      */     WindowEvent windowEvent2;
/*      */     FocusEvent focusEvent;
/*      */     WindowEvent windowEvent1;
/*      */     Window window1;
/*      */     CausedFocusEvent.Cause cause;
/*      */     Component component1;
/*      */     Window window2;
/*      */     Component component2;
/*      */     Component component3;
/*  341 */     if (focusLog.isLoggable(PlatformLogger.Level.FINE) && (paramAWTEvent instanceof WindowEvent || paramAWTEvent instanceof FocusEvent)) {
/*  342 */       focusLog.fine("" + paramAWTEvent);
/*      */     }
/*  344 */     switch (paramAWTEvent.getID()) {
/*      */       case 207:
/*  346 */         if (!repostIfFollowsKeyEvents((WindowEvent)paramAWTEvent))
/*      */         
/*      */         { 
/*      */           
/*  350 */           WindowEvent windowEvent = (WindowEvent)paramAWTEvent;
/*  351 */           Window window3 = getGlobalFocusedWindow();
/*  352 */           Window window4 = windowEvent.getWindow();
/*  353 */           if (window4 != window3)
/*      */           {
/*      */ 
/*      */             
/*  357 */             if (!window4.isFocusableWindow() || 
/*  358 */               !window4.isVisible() || 
/*  359 */               !window4.isDisplayable())
/*      */             
/*      */             { 
/*  362 */               restoreFocus(windowEvent);
/*      */                }
/*      */             
/*      */             else
/*      */             
/*  367 */             { if (window3 != null) {
/*      */                 
/*  369 */                 boolean bool = sendMessage(window3, new WindowEvent(window3, 208, window4));
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  374 */                 if (!bool) {
/*  375 */                   setGlobalFocusOwner(null);
/*  376 */                   setGlobalFocusedWindow(null);
/*      */                 } 
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  384 */               Window window5 = getOwningFrameDialog(window4);
/*  385 */               Window window6 = getGlobalActiveWindow();
/*  386 */               if (window5 != window6)
/*  387 */               { sendMessage(window5, new WindowEvent(window5, 205, window6));
/*      */ 
/*      */ 
/*      */                 
/*  391 */                 if (window5 != getGlobalActiveWindow())
/*      */                 
/*      */                 { 
/*  394 */                   restoreFocus(windowEvent);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  805 */                   return true; }  }  setGlobalFocusedWindow(window4); if (window4 != getGlobalFocusedWindow()) { restoreFocus(windowEvent); } else { if (this.inSendMessage == 0) { Component component4 = KeyboardFocusManager.getMostRecentFocusOwner(window4); boolean bool = (this.restoreFocusTo != null && component4 == this.restoreFocusTo) ? true : false; if (component4 == null && window4.isFocusableWindow()) component4 = window4.getFocusTraversalPolicy().getInitialComponent(window4);  Component component5 = null; synchronized (KeyboardFocusManager.class) { component5 = window4.setTemporaryLostComponent((Component)null); }  if (focusLog.isLoggable(PlatformLogger.Level.FINER)) focusLog.finer("tempLost {0}, toFocus {1}", new Object[] { component5, component4 });  if (component5 != null) component5.requestFocusInWindow((bool && component5 == component4) ? CausedFocusEvent.Cause.ROLLBACK : CausedFocusEvent.Cause.ACTIVATION);  if (component4 != null && component4 != component5) component4.requestFocusInWindow(CausedFocusEvent.Cause.ACTIVATION);  }  this.restoreFocusTo = null; Window window = this.realOppositeWindowWR.get(); if (window != windowEvent.getOppositeWindow()) windowEvent = new WindowEvent(window4, 207, window);  return typeAheadAssertions(window4, windowEvent); }  }  }  }  return true;case 205: windowEvent2 = (WindowEvent)paramAWTEvent; window1 = getGlobalActiveWindow(); window2 = windowEvent2.getWindow(); if (window1 != window2) { if (window1 != null) { boolean bool = sendMessage(window1, new WindowEvent(window1, 206, window2)); if (!bool) setGlobalActiveWindow(null);  if (getGlobalActiveWindow() != null) return true;  }  setGlobalActiveWindow(window2); if (window2 == getGlobalActiveWindow()) return typeAheadAssertions(window2, windowEvent2);  }  return true;case 1004: this.restoreFocusTo = null; focusEvent = (FocusEvent)paramAWTEvent; cause = (focusEvent instanceof CausedFocusEvent) ? ((CausedFocusEvent)focusEvent).getCause() : CausedFocusEvent.Cause.UNKNOWN; component2 = getGlobalFocusOwner(); component3 = focusEvent.getComponent(); if (component2 == component3) { if (focusLog.isLoggable(PlatformLogger.Level.FINE)) focusLog.fine("Skipping {0} because focus owner is the same", new Object[] { paramAWTEvent });  dequeueKeyEvents(-1L, component3); } else { if (component2 != null) { boolean bool = sendMessage(component2, new CausedFocusEvent(component2, 1005, focusEvent.isTemporary(), component3, cause)); if (!bool) { setGlobalFocusOwner(null); if (!focusEvent.isTemporary()) setGlobalPermanentFocusOwner(null);  }  }  Window window3 = SunToolkit.getContainingWindow(component3); Window window4 = getGlobalFocusedWindow(); if (window3 != null && window3 != window4) { sendMessage(window3, new WindowEvent(window3, 207, window4)); if (window3 != getGlobalFocusedWindow()) { dequeueKeyEvents(-1L, component3); return true; }  }  if (!component3.isFocusable() || !component3.isShowing() || (!component3.isEnabled() && !cause.equals(CausedFocusEvent.Cause.UNKNOWN))) { dequeueKeyEvents(-1L, component3); if (KeyboardFocusManager.isAutoFocusTransferEnabled()) { if (window3 == null) { restoreFocus(focusEvent, window4); } else { restoreFocus(focusEvent, window3); }  setMostRecentFocusOwner(window3, null); }  } else { setGlobalFocusOwner(component3); if (component3 != getGlobalFocusOwner()) { dequeueKeyEvents(-1L, component3); if (KeyboardFocusManager.isAutoFocusTransferEnabled()) restoreFocus(focusEvent, window3);  } else { if (!focusEvent.isTemporary()) { setGlobalPermanentFocusOwner(component3); if (component3 != getGlobalPermanentFocusOwner()) { dequeueKeyEvents(-1L, component3); if (KeyboardFocusManager.isAutoFocusTransferEnabled()) restoreFocus(focusEvent, window3);  return true; }  }  setNativeFocusOwner(getHeavyweight(component3)); Component component = this.realOppositeComponentWR.get(); if (component != null && component != focusEvent.getOppositeComponent()) { focusEvent = new CausedFocusEvent(component3, 1004, focusEvent.isTemporary(), component, cause); focusEvent.isPosted = true; }  return typeAheadAssertions(component3, focusEvent); }  }  }  return true;case 1005: focusEvent = (FocusEvent)paramAWTEvent; component1 = getGlobalFocusOwner(); if (component1 == null) { if (focusLog.isLoggable(PlatformLogger.Level.FINE)) focusLog.fine("Skipping {0} because focus owner is null", new Object[] { paramAWTEvent });  } else if (component1 == focusEvent.getOppositeComponent()) { if (focusLog.isLoggable(PlatformLogger.Level.FINE)) focusLog.fine("Skipping {0} because current focus owner is equal to opposite", new Object[] { paramAWTEvent });  } else { setGlobalFocusOwner(null); if (getGlobalFocusOwner() != null) { restoreFocus(component1, true); } else { if (!focusEvent.isTemporary()) { setGlobalPermanentFocusOwner(null); if (getGlobalPermanentFocusOwner() != null) { restoreFocus(component1, true); return true; }  } else { component2 = component1.getContainingWindow(); if (component2 != null) component2.setTemporaryLostComponent(component1);  }  setNativeFocusOwner(null); focusEvent.setSource(component1); this.realOppositeComponentWR = (focusEvent.getOppositeComponent() != null) ? new WeakReference<>(component1) : NULL_COMPONENT_WR; return typeAheadAssertions(component1, focusEvent); }  }  return true;case 206: windowEvent1 = (WindowEvent)paramAWTEvent; component1 = getGlobalActiveWindow(); if (component1 != null) if (component1 == paramAWTEvent.getSource()) { setGlobalActiveWindow(null); if (getGlobalActiveWindow() == null) { windowEvent1.setSource(component1); return typeAheadAssertions(component1, windowEvent1); }  }   return true;case 208: if (!repostIfFollowsKeyEvents((WindowEvent)paramAWTEvent)) { windowEvent1 = (WindowEvent)paramAWTEvent; component1 = getGlobalFocusedWindow(); component2 = windowEvent1.getWindow(); component3 = getGlobalActiveWindow(); Window window = windowEvent1.getOppositeWindow(); if (focusLog.isLoggable(PlatformLogger.Level.FINE)) focusLog.fine("Active {0}, Current focused {1}, losing focus {2} opposite {3}", new Object[] { component3, component1, component2, window });  if (component1 != null) if (this.inSendMessage != 0 || component2 != component3 || window != component1) { Component component = getGlobalFocusOwner(); if (component != null) { Component component4 = null; if (window != null) { component4 = window.getTemporaryLostComponent(); if (component4 == null) component4 = window.getMostRecentFocusOwner();  }  if (component4 == null) component4 = window;  sendMessage(component, new CausedFocusEvent(component, 1005, true, component4, CausedFocusEvent.Cause.ACTIVATION)); }  setGlobalFocusedWindow(null); if (getGlobalFocusedWindow() != null) { restoreFocus((Window)component1, (Component)null, true); } else { windowEvent1.setSource(component1); this.realOppositeWindowWR = (window != null) ? (WeakReference)new WeakReference<>(component1) : NULL_WINDOW_WR; typeAheadAssertions(component1, windowEvent1); if (window == null && component3 != null) { sendMessage(component3, new WindowEvent((Window)component3, 206, null)); if (getGlobalActiveWindow() != null) restoreFocus((Window)component1, (Component)null, true);  }  }  }   }  return true;
/*      */       case 400:
/*      */       case 401:
/*      */       case 402:
/*      */         return typeAheadAssertions((Component)null, paramAWTEvent);
/*      */     } 
/*      */     return false;
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
/*      */   public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
/*  828 */     Component component1 = paramKeyEvent.isPosted ? getFocusOwner() : paramKeyEvent.getComponent();
/*      */     
/*  830 */     if (component1 != null && component1.isShowing() && component1.canBeFocusOwner() && 
/*  831 */       !paramKeyEvent.isConsumed()) {
/*  832 */       Component component = paramKeyEvent.getComponent();
/*  833 */       if (component != null && component.isEnabled()) {
/*  834 */         redispatchEvent(component, paramKeyEvent);
/*      */       }
/*      */     } 
/*      */     
/*  838 */     boolean bool = false;
/*  839 */     List<KeyEventPostProcessor> list = getKeyEventPostProcessors();
/*  840 */     if (list != null) {
/*  841 */       Iterator<KeyEventPostProcessor> iterator = list.iterator();
/*  842 */       while (!bool && iterator.hasNext())
/*      */       {
/*      */         
/*  845 */         bool = ((KeyEventPostProcessor)iterator.next()).postProcessKeyEvent(paramKeyEvent);
/*      */       }
/*      */     } 
/*  848 */     if (!bool) {
/*  849 */       postProcessKeyEvent(paramKeyEvent);
/*      */     }
/*      */ 
/*      */     
/*  853 */     Component component2 = paramKeyEvent.getComponent();
/*  854 */     ComponentPeer componentPeer = component2.getPeer();
/*      */     
/*  856 */     if (componentPeer == null || componentPeer instanceof java.awt.peer.LightweightPeer) {
/*      */ 
/*      */       
/*  859 */       Container container = component2.getNativeContainer();
/*  860 */       if (container != null) {
/*  861 */         componentPeer = container.getPeer();
/*      */       }
/*      */     } 
/*  864 */     if (componentPeer != null) {
/*  865 */       componentPeer.handleEvent(paramKeyEvent);
/*      */     }
/*      */     
/*  868 */     return true;
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
/*      */   public boolean postProcessKeyEvent(KeyEvent paramKeyEvent) {
/*  883 */     if (!paramKeyEvent.isConsumed()) {
/*  884 */       Component component = paramKeyEvent.getComponent();
/*      */       
/*  886 */       Container container = (component instanceof Container) ? (Container)component : component.getParent();
/*  887 */       if (container != null) {
/*  888 */         container.postProcessKeyEvent(paramKeyEvent);
/*      */       }
/*      */     } 
/*  891 */     return true;
/*      */   }
/*      */   
/*      */   private void pumpApprovedKeyEvents() {
/*      */     KeyEvent keyEvent;
/*      */     do {
/*  897 */       keyEvent = null;
/*  898 */       synchronized (this) {
/*  899 */         if (this.enqueuedKeyEvents.size() != 0) {
/*  900 */           keyEvent = this.enqueuedKeyEvents.getFirst();
/*  901 */           if (this.typeAheadMarkers.size() != 0) {
/*  902 */             TypeAheadMarker typeAheadMarker = this.typeAheadMarkers.getFirst();
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  907 */             if (keyEvent.getWhen() > typeAheadMarker.after) {
/*  908 */               keyEvent = null;
/*      */             }
/*      */           } 
/*  911 */           if (keyEvent != null) {
/*  912 */             if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  913 */               focusLog.finer("Pumping approved event {0}", new Object[] { keyEvent });
/*      */             }
/*  915 */             this.enqueuedKeyEvents.removeFirst();
/*      */           } 
/*      */         } 
/*      */       } 
/*  919 */       if (keyEvent == null)
/*  920 */         continue;  preDispatchKeyEvent(keyEvent);
/*      */     }
/*  922 */     while (keyEvent != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void dumpMarkers() {
/*  929 */     if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  930 */       focusLog.finest(">>> Markers dump, time: {0}", new Object[] { Long.valueOf(System.currentTimeMillis()) });
/*  931 */       synchronized (this) {
/*  932 */         if (this.typeAheadMarkers.size() != 0) {
/*  933 */           Iterator<TypeAheadMarker> iterator = this.typeAheadMarkers.iterator();
/*  934 */           while (iterator.hasNext()) {
/*  935 */             TypeAheadMarker typeAheadMarker = iterator.next();
/*  936 */             focusLog.finest("    {0}", new Object[] { typeAheadMarker });
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean typeAheadAssertions(Component paramComponent, AWTEvent paramAWTEvent) {
/*      */     KeyEvent keyEvent;
/*  948 */     pumpApprovedKeyEvents();
/*      */     
/*  950 */     switch (paramAWTEvent.getID()) {
/*      */       case 400:
/*      */       case 401:
/*      */       case 402:
/*  954 */         keyEvent = (KeyEvent)paramAWTEvent;
/*  955 */         synchronized (this) {
/*  956 */           if (paramAWTEvent.isPosted && this.typeAheadMarkers.size() != 0) {
/*  957 */             TypeAheadMarker typeAheadMarker = this.typeAheadMarkers.getFirst();
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  962 */             if (keyEvent.getWhen() > typeAheadMarker.after) {
/*  963 */               if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  964 */                 focusLog.finer("Storing event {0} because of marker {1}", new Object[] { keyEvent, typeAheadMarker });
/*      */               }
/*  966 */               this.enqueuedKeyEvents.addLast(keyEvent);
/*  967 */               return true;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  973 */         return preDispatchKeyEvent(keyEvent);
/*      */ 
/*      */       
/*      */       case 1004:
/*  977 */         if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  978 */           focusLog.finest("Markers before FOCUS_GAINED on {0}", new Object[] { paramComponent });
/*      */         }
/*  980 */         dumpMarkers();
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
/*  991 */         synchronized (this) {
/*  992 */           boolean bool = false;
/*  993 */           if (hasMarker(paramComponent)) {
/*  994 */             Iterator<TypeAheadMarker> iterator = this.typeAheadMarkers.iterator();
/*  995 */             while (iterator.hasNext())
/*      */             {
/*  997 */               if (((TypeAheadMarker)iterator.next()).untilFocused == paramComponent) {
/*  998 */                 bool = true;
/*  999 */               } else if (bool) {
/*      */                 break;
/*      */               } 
/* 1002 */               iterator.remove();
/*      */             }
/*      */           
/*      */           }
/* 1006 */           else if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1007 */             focusLog.finer("Event without marker {0}", new Object[] { paramAWTEvent });
/*      */           } 
/*      */         } 
/*      */         
/* 1011 */         focusLog.finest("Markers after FOCUS_GAINED");
/* 1012 */         dumpMarkers();
/*      */         
/* 1014 */         redispatchEvent(paramComponent, paramAWTEvent);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1019 */         pumpApprovedKeyEvents();
/* 1020 */         return true;
/*      */     } 
/*      */     
/* 1023 */     redispatchEvent(paramComponent, paramAWTEvent);
/* 1024 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasMarker(Component paramComponent) {
/* 1034 */     for (Iterator<TypeAheadMarker> iterator = this.typeAheadMarkers.iterator(); iterator.hasNext();) {
/* 1035 */       if (((TypeAheadMarker)iterator.next()).untilFocused == paramComponent) {
/* 1036 */         return true;
/*      */       }
/*      */     } 
/* 1039 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void clearMarkers() {
/* 1047 */     synchronized (this) {
/* 1048 */       this.typeAheadMarkers.clear();
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean preDispatchKeyEvent(KeyEvent paramKeyEvent) {
/* 1053 */     if (paramKeyEvent.isPosted) {
/* 1054 */       Component component = getFocusOwner();
/* 1055 */       paramKeyEvent.setSource((component != null) ? component : getFocusedWindow());
/*      */     } 
/* 1057 */     if (paramKeyEvent.getSource() == null) {
/* 1058 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1065 */     EventQueue.setCurrentEventAndMostRecentTime(paramKeyEvent);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1074 */     if (KeyboardFocusManager.isProxyActive(paramKeyEvent)) {
/* 1075 */       Component component = (Component)paramKeyEvent.getSource();
/* 1076 */       Container container = component.getNativeContainer();
/* 1077 */       if (container != null) {
/* 1078 */         ComponentPeer componentPeer = container.getPeer();
/* 1079 */         if (componentPeer != null) {
/* 1080 */           componentPeer.handleEvent(paramKeyEvent);
/*      */ 
/*      */ 
/*      */           
/* 1084 */           paramKeyEvent.consume();
/*      */         } 
/*      */       } 
/* 1087 */       return true;
/*      */     } 
/*      */     
/* 1090 */     List<KeyEventDispatcher> list = getKeyEventDispatchers();
/* 1091 */     if (list != null) {
/* 1092 */       Iterator<KeyEventDispatcher> iterator = list.iterator();
/* 1093 */       while (iterator.hasNext()) {
/*      */         
/* 1095 */         if (((KeyEventDispatcher)iterator.next())
/* 1096 */           .dispatchKeyEvent(paramKeyEvent))
/*      */         {
/* 1098 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/* 1102 */     return dispatchKeyEvent(paramKeyEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void consumeNextKeyTyped(KeyEvent paramKeyEvent) {
/* 1110 */     this.consumeNextKeyTyped = true;
/*      */   }
/*      */   
/*      */   private void consumeTraversalKey(KeyEvent paramKeyEvent) {
/* 1114 */     paramKeyEvent.consume();
/* 1115 */     this
/* 1116 */       .consumeNextKeyTyped = (paramKeyEvent.getID() == 401 && !paramKeyEvent.isActionKey());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean consumeProcessedKeyEvent(KeyEvent paramKeyEvent) {
/* 1123 */     if (paramKeyEvent.getID() == 400 && this.consumeNextKeyTyped) {
/* 1124 */       paramKeyEvent.consume();
/* 1125 */       this.consumeNextKeyTyped = false;
/* 1126 */       return true;
/*      */     } 
/* 1128 */     return false;
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
/*      */   public void processKeyEvent(Component paramComponent, KeyEvent paramKeyEvent) {
/* 1146 */     if (consumeProcessedKeyEvent(paramKeyEvent)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1151 */     if (paramKeyEvent.getID() == 400) {
/*      */       return;
/*      */     }
/*      */     
/* 1155 */     if (paramComponent.getFocusTraversalKeysEnabled() && 
/* 1156 */       !paramKeyEvent.isConsumed()) {
/*      */       
/* 1158 */       AWTKeyStroke aWTKeyStroke1 = AWTKeyStroke.getAWTKeyStrokeForEvent(paramKeyEvent);
/* 1159 */       AWTKeyStroke aWTKeyStroke2 = AWTKeyStroke.getAWTKeyStroke(aWTKeyStroke1.getKeyCode(), aWTKeyStroke1
/* 1160 */           .getModifiers(), 
/* 1161 */           !aWTKeyStroke1.isOnKeyRelease());
/*      */ 
/*      */ 
/*      */       
/* 1165 */       Set<AWTKeyStroke> set = paramComponent.getFocusTraversalKeys(0);
/*      */       
/* 1167 */       boolean bool1 = set.contains(aWTKeyStroke1);
/* 1168 */       boolean bool2 = set.contains(aWTKeyStroke2);
/*      */       
/* 1170 */       if (bool1 || bool2) {
/* 1171 */         consumeTraversalKey(paramKeyEvent);
/* 1172 */         if (bool1)
/* 1173 */           focusNextComponent(paramComponent); 
/*      */         return;
/*      */       } 
/* 1176 */       if (paramKeyEvent.getID() == 401)
/*      */       {
/* 1178 */         this.consumeNextKeyTyped = false;
/*      */       }
/*      */       
/* 1181 */       set = paramComponent.getFocusTraversalKeys(1);
/*      */       
/* 1183 */       bool1 = set.contains(aWTKeyStroke1);
/* 1184 */       bool2 = set.contains(aWTKeyStroke2);
/*      */       
/* 1186 */       if (bool1 || bool2) {
/* 1187 */         consumeTraversalKey(paramKeyEvent);
/* 1188 */         if (bool1) {
/* 1189 */           focusPreviousComponent(paramComponent);
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/* 1194 */       set = paramComponent.getFocusTraversalKeys(2);
/*      */       
/* 1196 */       bool1 = set.contains(aWTKeyStroke1);
/* 1197 */       bool2 = set.contains(aWTKeyStroke2);
/*      */       
/* 1199 */       if (bool1 || bool2) {
/* 1200 */         consumeTraversalKey(paramKeyEvent);
/* 1201 */         if (bool1) {
/* 1202 */           upFocusCycle(paramComponent);
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/* 1207 */       if (!(paramComponent instanceof Container) || 
/* 1208 */         !((Container)paramComponent).isFocusCycleRoot()) {
/*      */         return;
/*      */       }
/*      */       
/* 1212 */       set = paramComponent.getFocusTraversalKeys(3);
/*      */       
/* 1214 */       bool1 = set.contains(aWTKeyStroke1);
/* 1215 */       bool2 = set.contains(aWTKeyStroke2);
/*      */       
/* 1217 */       if (bool1 || bool2) {
/* 1218 */         consumeTraversalKey(paramKeyEvent);
/* 1219 */         if (bool1) {
/* 1220 */           downFocusCycle((Container)paramComponent);
/*      */         }
/*      */       } 
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
/*      */   protected synchronized void enqueueKeyEvents(long paramLong, Component paramComponent) {
/* 1243 */     if (paramComponent == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1247 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1248 */       focusLog.finer("Enqueue at {0} for {1}", new Object[] {
/* 1249 */             Long.valueOf(paramLong), paramComponent
/*      */           });
/*      */     }
/* 1252 */     int i = 0;
/* 1253 */     int j = this.typeAheadMarkers.size();
/* 1254 */     ListIterator<TypeAheadMarker> listIterator = this.typeAheadMarkers.listIterator(j);
/*      */     
/* 1256 */     for (; j > 0; j--) {
/* 1257 */       TypeAheadMarker typeAheadMarker = listIterator.previous();
/* 1258 */       if (typeAheadMarker.after <= paramLong) {
/* 1259 */         i = j;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1264 */     this.typeAheadMarkers.add(i, new TypeAheadMarker(paramLong, paramComponent));
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
/*      */   protected synchronized void dequeueKeyEvents(long paramLong, Component paramComponent) {
/* 1285 */     if (paramComponent == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1289 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1290 */       focusLog.finer("Dequeue at {0} for {1}", new Object[] {
/* 1291 */             Long.valueOf(paramLong), paramComponent
/*      */           });
/*      */     }
/*      */ 
/*      */     
/* 1296 */     ListIterator<TypeAheadMarker> listIterator = this.typeAheadMarkers.listIterator((paramLong >= 0L) ? this.typeAheadMarkers.size() : 0);
/*      */     
/* 1298 */     if (paramLong < 0L) {
/* 1299 */       while (listIterator.hasNext()) {
/* 1300 */         TypeAheadMarker typeAheadMarker = listIterator.next();
/* 1301 */         if (typeAheadMarker.untilFocused == paramComponent) {
/*      */           
/* 1303 */           listIterator.remove();
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1308 */       while (listIterator.hasPrevious()) {
/* 1309 */         TypeAheadMarker typeAheadMarker = listIterator.previous();
/* 1310 */         if (typeAheadMarker.untilFocused == paramComponent && typeAheadMarker.after == paramLong) {
/*      */ 
/*      */           
/* 1313 */           listIterator.remove();
/*      */           return;
/*      */         } 
/*      */       } 
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
/*      */   protected synchronized void discardKeyEvents(Component paramComponent) {
/* 1331 */     if (paramComponent == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1335 */     long l = -1L;
/*      */     
/* 1337 */     for (Iterator<TypeAheadMarker> iterator = this.typeAheadMarkers.iterator(); iterator.hasNext(); ) {
/* 1338 */       TypeAheadMarker typeAheadMarker = iterator.next();
/* 1339 */       Component component = typeAheadMarker.untilFocused;
/* 1340 */       boolean bool = (component == paramComponent) ? true : false;
/* 1341 */       while (!bool && component != null && !(component instanceof Window)) {
/* 1342 */         component = component.getParent();
/* 1343 */         bool = (component == paramComponent) ? true : false;
/*      */       } 
/* 1345 */       if (bool) {
/* 1346 */         if (l < 0L) {
/* 1347 */           l = typeAheadMarker.after;
/*      */         }
/* 1349 */         iterator.remove(); continue;
/* 1350 */       }  if (l >= 0L) {
/* 1351 */         purgeStampedEvents(l, typeAheadMarker.after);
/* 1352 */         l = -1L;
/*      */       } 
/*      */     } 
/*      */     
/* 1356 */     purgeStampedEvents(l, -1L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void purgeStampedEvents(long paramLong1, long paramLong2) {
/* 1365 */     if (paramLong1 < 0L) {
/*      */       return;
/*      */     }
/*      */     
/* 1369 */     for (Iterator<KeyEvent> iterator = this.enqueuedKeyEvents.iterator(); iterator.hasNext(); ) {
/* 1370 */       KeyEvent keyEvent = iterator.next();
/* 1371 */       long l = keyEvent.getWhen();
/*      */       
/* 1373 */       if (paramLong1 < l && (paramLong2 < 0L || l <= paramLong2)) {
/* 1374 */         iterator.remove();
/*      */       }
/*      */       
/* 1377 */       if (paramLong2 >= 0L && l > paramLong2) {
/*      */         break;
/*      */       }
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
/*      */   public void focusPreviousComponent(Component paramComponent) {
/* 1393 */     if (paramComponent != null) {
/* 1394 */       paramComponent.transferFocusBackward();
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
/*      */   public void focusNextComponent(Component paramComponent) {
/* 1408 */     if (paramComponent != null) {
/* 1409 */       paramComponent.transferFocus();
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
/*      */   public void upFocusCycle(Component paramComponent) {
/* 1426 */     if (paramComponent != null) {
/* 1427 */       paramComponent.transferFocusUpCycle();
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
/*      */   public void downFocusCycle(Container paramContainer) {
/* 1443 */     if (paramContainer != null && paramContainer.isFocusCycleRoot())
/* 1444 */       paramContainer.transferFocusDownCycle(); 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/DefaultKeyboardFocusManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */