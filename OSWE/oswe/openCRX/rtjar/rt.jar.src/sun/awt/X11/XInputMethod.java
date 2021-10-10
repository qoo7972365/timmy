/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.im.spi.InputMethod;
/*     */ import java.awt.im.spi.InputMethodContext;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import sun.awt.X11InputMethod;
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
/*     */ public class XInputMethod
/*     */   extends X11InputMethod
/*     */ {
/*  44 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XInputMethod");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInputMethodContext(InputMethodContext paramInputMethodContext) {
/*  51 */     paramInputMethodContext.enableClientWindowNotification((InputMethod)this, true);
/*     */   }
/*     */   
/*     */   public void notifyClientWindowChange(Rectangle paramRectangle) {
/*  55 */     XComponentPeer xComponentPeer = (XComponentPeer)getPeer(this.clientComponentWindow);
/*  56 */     if (xComponentPeer != null) {
/*  57 */       adjustStatusWindow(xComponentPeer.getContentWindow());
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean openXIM() {
/*  62 */     return openXIMNative(XToolkit.getDisplay());
/*     */   }
/*     */   
/*     */   protected boolean createXIC() {
/*  66 */     XComponentPeer xComponentPeer = (XComponentPeer)getPeer(this.clientComponentWindow);
/*  67 */     if (xComponentPeer == null) {
/*  68 */       return false;
/*     */     }
/*  70 */     return createXICNative(xComponentPeer.getContentWindow());
/*     */   }
/*     */ 
/*     */   
/*  74 */   private static volatile long xicFocus = 0L;
/*     */ 
/*     */   
/*     */   protected void setXICFocus(ComponentPeer paramComponentPeer, boolean paramBoolean1, boolean paramBoolean2) {
/*  78 */     if (paramComponentPeer == null) {
/*     */       return;
/*     */     }
/*  81 */     xicFocus = ((XComponentPeer)paramComponentPeer).getContentWindow();
/*  82 */     setXICFocusNative(((XComponentPeer)paramComponentPeer).getContentWindow(), paramBoolean1, paramBoolean2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getXICFocus() {
/*  88 */     return xicFocus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Container getParent(Component paramComponent) {
/*  95 */     return paramComponent.getParent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ComponentPeer getPeer(Component paramComponent) {
/* 105 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 106 */       log.fine("Client is " + paramComponent);
/*     */     }
/* 108 */     XComponentPeer xComponentPeer = (XComponentPeer)XToolkit.targetToPeer(paramComponent);
/* 109 */     while (paramComponent != null && xComponentPeer == null) {
/* 110 */       paramComponent = getParent(paramComponent);
/* 111 */       xComponentPeer = (XComponentPeer)XToolkit.targetToPeer(paramComponent);
/*     */     } 
/* 113 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 114 */       log.fine("Peer is {0}, client is {1}", new Object[] { xComponentPeer, paramComponent });
/*     */     }
/*     */     
/* 117 */     if (xComponentPeer != null) {
/* 118 */       return xComponentPeer;
/*     */     }
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void disposeImpl() {
/* 128 */     super.disposeImpl();
/* 129 */     this.clientComponentWindow = null;
/*     */   }
/*     */   
/*     */   protected void awtLock() {
/* 133 */     XToolkit.awtLock();
/*     */   }
/*     */   
/*     */   protected void awtUnlock() {
/* 137 */     XToolkit.awtUnlock();
/*     */   }
/*     */   
/*     */   long getCurrentParentWindow() {
/* 141 */     return ((XWindow)this.clientComponentWindow.getPeer()).getContentWindow();
/*     */   }
/*     */   
/*     */   private native boolean openXIMNative(long paramLong);
/*     */   
/*     */   private native boolean createXICNative(long paramLong);
/*     */   
/*     */   private native void setXICFocusNative(long paramLong, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   private native void adjustStatusWindow(long paramLong);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XInputMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */