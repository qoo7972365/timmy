/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Panel;
/*     */ import java.awt.Window;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import java.awt.peer.KeyboardFocusManagerPeer;
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
/*     */ public abstract class KeyboardFocusManagerPeerImpl
/*     */   implements KeyboardFocusManagerPeer
/*     */ {
/*  46 */   private static final PlatformLogger focusLog = PlatformLogger.getLogger("sun.awt.focus.KeyboardFocusManagerPeerImpl");
/*     */ 
/*     */   
/*  49 */   private static AWTAccessor.KeyboardFocusManagerAccessor kfmAccessor = AWTAccessor.getKeyboardFocusManagerAccessor();
/*     */   
/*     */   public static final int SNFH_FAILURE = 0;
/*     */   
/*     */   public static final int SNFH_SUCCESS_HANDLED = 1;
/*     */   
/*     */   public static final int SNFH_SUCCESS_PROCEED = 2;
/*     */   
/*     */   public void clearGlobalFocusOwner(Window paramWindow) {
/*  58 */     if (paramWindow != null) {
/*  59 */       Component component = paramWindow.getFocusOwner();
/*  60 */       if (focusLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  61 */         focusLog.fine("Clearing global focus owner " + component);
/*     */       }
/*  63 */       if (component != null) {
/*  64 */         CausedFocusEvent causedFocusEvent = new CausedFocusEvent(component, 1005, false, null, CausedFocusEvent.Cause.CLEAR_GLOBAL_FOCUS_OWNER);
/*     */         
/*  66 */         SunToolkit.postPriorityEvent(causedFocusEvent);
/*     */       } 
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
/*     */   public static boolean shouldFocusOnClick(Component paramComponent) {
/*  80 */     boolean bool = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     if (paramComponent instanceof java.awt.Canvas || paramComponent instanceof java.awt.Scrollbar) {
/*     */ 
/*     */       
/*  90 */       bool = true;
/*     */     
/*     */     }
/*  93 */     else if (paramComponent instanceof Panel) {
/*  94 */       bool = (((Panel)paramComponent).getComponentCount() == 0) ? true : false;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  99 */       ComponentPeer componentPeer = (paramComponent != null) ? paramComponent.getPeer() : null;
/* 100 */       bool = (componentPeer != null) ? componentPeer.isFocusable() : false;
/*     */     } 
/* 102 */     return (bool && 
/* 103 */       AWTAccessor.getComponentAccessor().canBeFocusOwner(paramComponent));
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
/*     */   public static boolean deliverFocus(Component paramComponent1, Component paramComponent2, boolean paramBoolean1, boolean paramBoolean2, long paramLong, CausedFocusEvent.Cause paramCause, Component paramComponent3) {
/* 118 */     if (paramComponent1 == null) {
/* 119 */       paramComponent1 = paramComponent2;
/*     */     }
/*     */     
/* 122 */     Component component = paramComponent3;
/* 123 */     if (component != null && component.getPeer() == null) {
/* 124 */       component = null;
/*     */     }
/* 126 */     if (component != null) {
/* 127 */       CausedFocusEvent causedFocusEvent1 = new CausedFocusEvent(component, 1005, false, paramComponent1, paramCause);
/*     */ 
/*     */       
/* 130 */       if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 131 */         focusLog.finer("Posting focus event: " + causedFocusEvent1);
/*     */       }
/* 133 */       SunToolkit.postEvent(SunToolkit.targetToAppContext(component), causedFocusEvent1);
/*     */     } 
/*     */     
/* 136 */     CausedFocusEvent causedFocusEvent = new CausedFocusEvent(paramComponent1, 1004, false, component, paramCause);
/*     */ 
/*     */     
/* 139 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 140 */       focusLog.finer("Posting focus event: " + causedFocusEvent);
/*     */     }
/* 142 */     SunToolkit.postEvent(SunToolkit.targetToAppContext(paramComponent1), causedFocusEvent);
/* 143 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean requestFocusFor(Component paramComponent, CausedFocusEvent.Cause paramCause) {
/* 148 */     return AWTAccessor.getComponentAccessor().requestFocus(paramComponent, paramCause);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int shouldNativelyFocusHeavyweight(Component paramComponent1, Component paramComponent2, boolean paramBoolean1, boolean paramBoolean2, long paramLong, CausedFocusEvent.Cause paramCause) {
/* 159 */     return kfmAccessor.shouldNativelyFocusHeavyweight(paramComponent1, paramComponent2, paramBoolean1, paramBoolean2, paramLong, paramCause);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void removeLastFocusRequest(Component paramComponent) {
/* 164 */     kfmAccessor.removeLastFocusRequest(paramComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean processSynchronousLightweightTransfer(Component paramComponent1, Component paramComponent2, boolean paramBoolean1, boolean paramBoolean2, long paramLong) {
/* 174 */     return kfmAccessor.processSynchronousLightweightTransfer(paramComponent1, paramComponent2, paramBoolean1, paramBoolean2, paramLong);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/KeyboardFocusManagerPeerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */