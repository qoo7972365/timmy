/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Window;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.CausedFocusEvent;
/*     */ import sun.awt.KeyboardFocusManagerPeerImpl;
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
/*     */ public class XKeyboardFocusManagerPeer
/*     */   extends KeyboardFocusManagerPeerImpl
/*     */ {
/*  36 */   private static final PlatformLogger focusLog = PlatformLogger.getLogger("sun.awt.X11.focus.XKeyboardFocusManagerPeer");
/*  37 */   private static final XKeyboardFocusManagerPeer inst = new XKeyboardFocusManagerPeer();
/*     */   
/*     */   private Component currentFocusOwner;
/*     */   private Window currentFocusedWindow;
/*     */   
/*     */   public static XKeyboardFocusManagerPeer getInstance() {
/*  43 */     return inst;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentFocusOwner(Component paramComponent) {
/*  51 */     synchronized (this) {
/*  52 */       this.currentFocusOwner = paramComponent;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getCurrentFocusOwner() {
/*  58 */     synchronized (this) {
/*  59 */       return this.currentFocusOwner;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentFocusedWindow(Window paramWindow) {
/*  65 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  66 */       focusLog.finer("Setting current focused window " + paramWindow);
/*     */     }
/*     */     
/*  69 */     XWindowPeer xWindowPeer1 = null, xWindowPeer2 = null;
/*     */     
/*  71 */     synchronized (this) {
/*  72 */       if (this.currentFocusedWindow != null) {
/*  73 */         xWindowPeer1 = AWTAccessor.getComponentAccessor().<XWindowPeer>getPeer(this.currentFocusedWindow);
/*     */       }
/*     */       
/*  76 */       this.currentFocusedWindow = paramWindow;
/*     */       
/*  78 */       if (this.currentFocusedWindow != null) {
/*  79 */         xWindowPeer2 = AWTAccessor.getComponentAccessor().<XWindowPeer>getPeer(this.currentFocusedWindow);
/*     */       }
/*     */     } 
/*     */     
/*  83 */     if (xWindowPeer1 != null) {
/*  84 */       xWindowPeer1.updateSecurityWarningVisibility();
/*     */     }
/*  86 */     if (xWindowPeer2 != null) {
/*  87 */       xWindowPeer2.updateSecurityWarningVisibility();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Window getCurrentFocusedWindow() {
/*  93 */     synchronized (this) {
/*  94 */       return this.currentFocusedWindow;
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
/*     */   public static boolean deliverFocus(Component paramComponent1, Component paramComponent2, boolean paramBoolean1, boolean paramBoolean2, long paramLong, CausedFocusEvent.Cause paramCause) {
/* 106 */     return KeyboardFocusManagerPeerImpl.deliverFocus(paramComponent1, paramComponent2, paramBoolean1, paramBoolean2, paramLong, paramCause, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 112 */         getInstance().getCurrentFocusOwner());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XKeyboardFocusManagerPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */