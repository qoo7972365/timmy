/*     */ package com.sun.java.swing;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.EventQueue;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.Callable;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.RepaintManager;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.EventQueueDelegate;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SwingUtilities3
/*     */ {
/*  61 */   private static final Object DELEGATE_REPAINT_MANAGER_KEY = new StringBuilder("DelegateRepaintManagerKey");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDelegateRepaintManager(JComponent paramJComponent, RepaintManager paramRepaintManager) {
/*  72 */     AppContext.getAppContext().put(DELEGATE_REPAINT_MANAGER_KEY, Boolean.TRUE);
/*     */ 
/*     */     
/*  75 */     paramJComponent.putClientProperty(DELEGATE_REPAINT_MANAGER_KEY, paramRepaintManager);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  80 */   private static final Map<Container, Boolean> vsyncedMap = Collections.synchronizedMap(new WeakHashMap<>());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setVsyncRequested(Container paramContainer, boolean paramBoolean) {
/*  97 */     assert paramContainer instanceof java.applet.Applet || paramContainer instanceof java.awt.Window;
/*  98 */     if (paramBoolean) {
/*  99 */       vsyncedMap.put(paramContainer, Boolean.TRUE);
/*     */     } else {
/* 101 */       vsyncedMap.remove(paramContainer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isVsyncRequested(Container paramContainer) {
/* 112 */     assert paramContainer instanceof java.applet.Applet || paramContainer instanceof java.awt.Window;
/* 113 */     return (Boolean.TRUE == vsyncedMap.get(paramContainer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RepaintManager getDelegateRepaintManager(Component paramComponent) {
/* 121 */     RepaintManager repaintManager = null;
/* 122 */     if (Boolean.TRUE == SunToolkit.targetToAppContext(paramComponent)
/* 123 */       .get(DELEGATE_REPAINT_MANAGER_KEY)) {
/* 124 */       while (repaintManager == null && paramComponent != null) {
/* 125 */         while (paramComponent != null && !(paramComponent instanceof JComponent))
/*     */         {
/* 127 */           paramComponent = paramComponent.getParent();
/*     */         }
/* 129 */         if (paramComponent != null) {
/*     */ 
/*     */           
/* 132 */           repaintManager = (RepaintManager)((JComponent)paramComponent).getClientProperty(DELEGATE_REPAINT_MANAGER_KEY);
/* 133 */           paramComponent = paramComponent.getParent();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 138 */     return repaintManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setEventQueueDelegate(Map<String, Map<String, Object>> paramMap) {
/* 147 */     EventQueueDelegate.setDelegate(new EventQueueDelegateFromMap(paramMap));
/*     */   }
/*     */ 
/*     */   
/*     */   private static class EventQueueDelegateFromMap
/*     */     implements EventQueueDelegate.Delegate
/*     */   {
/*     */     private final AWTEvent[] afterDispatchEventArgument;
/*     */     
/*     */     private final Object[] afterDispatchHandleArgument;
/*     */     private final Callable<Void> afterDispatchCallable;
/*     */     private final AWTEvent[] beforeDispatchEventArgument;
/*     */     private final Callable<Object> beforeDispatchCallable;
/*     */     private final EventQueue[] getNextEventEventQueueArgument;
/*     */     private final Callable<AWTEvent> getNextEventCallable;
/*     */     
/*     */     public EventQueueDelegateFromMap(Map<String, Map<String, Object>> param1Map) {
/* 164 */       Map map = param1Map.get("afterDispatch");
/* 165 */       this.afterDispatchEventArgument = (AWTEvent[])map.get("event");
/* 166 */       this.afterDispatchHandleArgument = (Object[])map.get("handle");
/* 167 */       this.afterDispatchCallable = (Callable<Void>)map.get("method");
/*     */       
/* 169 */       map = param1Map.get("beforeDispatch");
/* 170 */       this.beforeDispatchEventArgument = (AWTEvent[])map.get("event");
/* 171 */       this.beforeDispatchCallable = (Callable<Object>)map.get("method");
/*     */       
/* 173 */       map = param1Map.get("getNextEvent");
/* 174 */       this
/* 175 */         .getNextEventEventQueueArgument = (EventQueue[])map.get("eventQueue");
/* 176 */       this.getNextEventCallable = (Callable<AWTEvent>)map.get("method");
/*     */     }
/*     */ 
/*     */     
/*     */     public void afterDispatch(AWTEvent param1AWTEvent, Object param1Object) throws InterruptedException {
/* 181 */       this.afterDispatchEventArgument[0] = param1AWTEvent;
/* 182 */       this.afterDispatchHandleArgument[0] = param1Object;
/*     */       try {
/* 184 */         this.afterDispatchCallable.call();
/* 185 */       } catch (InterruptedException interruptedException) {
/* 186 */         throw interruptedException;
/* 187 */       } catch (RuntimeException runtimeException) {
/* 188 */         throw runtimeException;
/* 189 */       } catch (Exception exception) {
/* 190 */         throw new RuntimeException(exception);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Object beforeDispatch(AWTEvent param1AWTEvent) throws InterruptedException {
/* 196 */       this.beforeDispatchEventArgument[0] = param1AWTEvent;
/*     */       try {
/* 198 */         return this.beforeDispatchCallable.call();
/* 199 */       } catch (InterruptedException interruptedException) {
/* 200 */         throw interruptedException;
/* 201 */       } catch (RuntimeException runtimeException) {
/* 202 */         throw runtimeException;
/* 203 */       } catch (Exception exception) {
/* 204 */         throw new RuntimeException(exception);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public AWTEvent getNextEvent(EventQueue param1EventQueue) throws InterruptedException {
/* 210 */       this.getNextEventEventQueueArgument[0] = param1EventQueue;
/*     */       try {
/* 212 */         return this.getNextEventCallable.call();
/* 213 */       } catch (InterruptedException interruptedException) {
/* 214 */         throw interruptedException;
/* 215 */       } catch (RuntimeException runtimeException) {
/* 216 */         throw runtimeException;
/* 217 */       } catch (Exception exception) {
/* 218 */         throw new RuntimeException(exception);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/SwingUtilities3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */