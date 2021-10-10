/*     */ package sun.awt;
/*     */ 
/*     */ import java.applet.Applet;
/*     */ import java.awt.AWTKeyStroke;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Image;
/*     */ import java.awt.KeyEventDispatcher;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.MenuBar;
/*     */ import java.awt.MenuComponent;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import java.awt.peer.FramePeer;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EmbeddedFrame
/*     */   extends Frame
/*     */   implements KeyEventDispatcher, PropertyChangeListener
/*     */ {
/*     */   private boolean isCursorAllowed = true;
/*     */   private boolean supportsXEmbed = false;
/*     */   private KeyboardFocusManager appletKFM;
/*     */   private static final long serialVersionUID = 2967042741780317130L;
/*     */   protected static final boolean FORWARD = true;
/*     */   protected static final boolean BACKWARD = false;
/*     */   
/*     */   public boolean supportsXEmbed() {
/*  73 */     return (this.supportsXEmbed && SunToolkit.needsXEmbed());
/*     */   }
/*     */   
/*     */   protected EmbeddedFrame(boolean paramBoolean) {
/*  77 */     this(0L, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   protected EmbeddedFrame() {
/*  82 */     this(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected EmbeddedFrame(int paramInt) {
/*  90 */     this(paramInt);
/*     */   }
/*     */   
/*     */   protected EmbeddedFrame(long paramLong) {
/*  94 */     this(paramLong, false);
/*     */   }
/*     */   
/*     */   protected EmbeddedFrame(long paramLong, boolean paramBoolean) {
/*  98 */     this.supportsXEmbed = paramBoolean;
/*  99 */     registerListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Container getParent() {
/* 106 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 115 */     if (!paramPropertyChangeEvent.getPropertyName().equals("managingFocus")) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 121 */     if (paramPropertyChangeEvent.getNewValue() == Boolean.TRUE) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 126 */     removeTraversingOutListeners((KeyboardFocusManager)paramPropertyChangeEvent.getSource());
/*     */     
/* 128 */     this.appletKFM = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/* 129 */     if (isVisible()) {
/* 130 */       addTraversingOutListeners(this.appletKFM);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addTraversingOutListeners(KeyboardFocusManager paramKeyboardFocusManager) {
/* 138 */     paramKeyboardFocusManager.addKeyEventDispatcher(this);
/* 139 */     paramKeyboardFocusManager.addPropertyChangeListener("managingFocus", this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeTraversingOutListeners(KeyboardFocusManager paramKeyboardFocusManager) {
/* 146 */     paramKeyboardFocusManager.removeKeyEventDispatcher(this);
/* 147 */     paramKeyboardFocusManager.removePropertyChangeListener("managingFocus", this);
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
/*     */   public void registerListeners() {
/* 160 */     if (this.appletKFM != null) {
/* 161 */       removeTraversingOutListeners(this.appletKFM);
/*     */     }
/* 163 */     this.appletKFM = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/* 164 */     if (isVisible()) {
/* 165 */       addTraversingOutListeners(this.appletKFM);
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
/*     */   public void show() {
/* 177 */     if (this.appletKFM != null) {
/* 178 */       addTraversingOutListeners(this.appletKFM);
/*     */     }
/* 180 */     super.show();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void hide() {
/* 191 */     if (this.appletKFM != null) {
/* 192 */       removeTraversingOutListeners(this.appletKFM);
/*     */     }
/* 194 */     super.hide();
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
/*     */   public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
/* 206 */     Container container = AWTAccessor.getKeyboardFocusManagerAccessor().getCurrentFocusCycleRoot();
/*     */ 
/*     */     
/* 209 */     if (this != container) {
/* 210 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 214 */     if (paramKeyEvent.getID() == 400) {
/* 215 */       return false;
/*     */     }
/*     */     
/* 218 */     if (!getFocusTraversalKeysEnabled() || paramKeyEvent.isConsumed()) {
/* 219 */       return false;
/*     */     }
/*     */     
/* 222 */     AWTKeyStroke aWTKeyStroke = AWTKeyStroke.getAWTKeyStrokeForEvent(paramKeyEvent);
/*     */     
/* 224 */     Component component = paramKeyEvent.getComponent();
/*     */     
/* 226 */     Set<AWTKeyStroke> set = getFocusTraversalKeys(0);
/* 227 */     if (set.contains(aWTKeyStroke)) {
/*     */       
/* 229 */       Component component1 = getFocusTraversalPolicy().getLastComponent(this);
/* 230 */       if ((component == component1 || component1 == null) && 
/* 231 */         traverseOut(true)) {
/* 232 */         paramKeyEvent.consume();
/* 233 */         return true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 238 */     set = getFocusTraversalKeys(1);
/* 239 */     if (set.contains(aWTKeyStroke)) {
/*     */       
/* 241 */       Component component1 = getFocusTraversalPolicy().getFirstComponent(this);
/* 242 */       if ((component == component1 || component1 == null) && 
/* 243 */         traverseOut(false)) {
/* 244 */         paramKeyEvent.consume();
/* 245 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 249 */     return false;
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
/*     */   public boolean traverseIn(boolean paramBoolean) {
/* 271 */     Component component = null;
/*     */     
/* 273 */     if (paramBoolean == true) {
/* 274 */       component = getFocusTraversalPolicy().getFirstComponent(this);
/*     */     } else {
/* 276 */       component = getFocusTraversalPolicy().getLastComponent(this);
/*     */     } 
/* 278 */     if (component != null) {
/*     */ 
/*     */       
/* 281 */       AWTAccessor.getKeyboardFocusManagerAccessor().setMostRecentFocusOwner(this, component);
/* 282 */       synthesizeWindowActivation(true);
/*     */     } 
/* 284 */     return (null != component);
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
/*     */   protected boolean traverseOut(boolean paramBoolean) {
/* 305 */     return false;
/*     */   }
/*     */   public void setTitle(String paramString) {}
/*     */   
/*     */   public void setIconImage(Image paramImage) {}
/*     */   
/*     */   public void setIconImages(List<? extends Image> paramList) {}
/*     */   
/*     */   public void setMenuBar(MenuBar paramMenuBar) {}
/*     */   
/*     */   public void setResizable(boolean paramBoolean) {}
/*     */   
/*     */   public void remove(MenuComponent paramMenuComponent) {}
/*     */   
/*     */   public boolean isResizable() {
/* 320 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addNotify() {
/* 325 */     synchronized (getTreeLock()) {
/* 326 */       if (getPeer() == null) {
/* 327 */         setPeer(new NullEmbeddedFramePeer());
/*     */       }
/* 329 */       super.addNotify();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorAllowed(boolean paramBoolean) {
/* 336 */     this.isCursorAllowed = paramBoolean;
/* 337 */     getPeer().updateCursorImmediately();
/*     */   }
/*     */   public boolean isCursorAllowed() {
/* 340 */     return this.isCursorAllowed;
/*     */   }
/*     */   public Cursor getCursor() {
/* 343 */     return this.isCursorAllowed ? super
/* 344 */       .getCursor() : 
/* 345 */       Cursor.getPredefinedCursor(0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setPeer(ComponentPeer paramComponentPeer) {
/* 350 */     AWTAccessor.getComponentAccessor().setPeer(this, paramComponentPeer);
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
/*     */   public void synthesizeWindowActivation(boolean paramBoolean) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setLocationPrivate(int paramInt1, int paramInt2) {
/* 390 */     Dimension dimension = getSize();
/* 391 */     setBoundsPrivate(paramInt1, paramInt2, dimension.width, dimension.height);
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
/*     */   protected Point getLocationPrivate() {
/* 419 */     Rectangle rectangle = getBoundsPrivate();
/* 420 */     return new Point(rectangle.x, rectangle.y);
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
/*     */   protected void setBoundsPrivate(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 454 */     FramePeer framePeer = (FramePeer)getPeer();
/* 455 */     if (framePeer != null) {
/* 456 */       framePeer.setBoundsPrivate(paramInt1, paramInt2, paramInt3, paramInt4);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Rectangle getBoundsPrivate() {
/* 486 */     FramePeer framePeer = (FramePeer)getPeer();
/* 487 */     if (framePeer != null) {
/* 488 */       return framePeer.getBoundsPrivate();
/*     */     }
/*     */     
/* 491 */     return getBounds();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void toFront() {}
/*     */ 
/*     */   
/*     */   public void toBack() {}
/*     */ 
/*     */   
/*     */   public abstract void registerAccelerator(AWTKeyStroke paramAWTKeyStroke);
/*     */ 
/*     */   
/*     */   public abstract void unregisterAccelerator(AWTKeyStroke paramAWTKeyStroke);
/*     */ 
/*     */   
/*     */   public static Applet getAppletIfAncestorOf(Component paramComponent) {
/* 509 */     Container container = paramComponent.getParent();
/* 510 */     Applet applet = null;
/* 511 */     while (container != null && !(container instanceof EmbeddedFrame)) {
/* 512 */       if (container instanceof Applet) {
/* 513 */         applet = (Applet)container;
/*     */       }
/* 515 */       container = container.getParent();
/*     */     } 
/* 517 */     return (container == null) ? null : applet;
/*     */   }
/*     */   public void notifyModalBlocked(Dialog paramDialog, boolean paramBoolean) {}
/*     */   
/*     */   private static class NullEmbeddedFramePeer extends NullComponentPeer implements FramePeer { private NullEmbeddedFramePeer() {}
/*     */     
/*     */     public void setTitle(String param1String) {}
/*     */     
/*     */     public void setIconImage(Image param1Image) {}
/*     */     
/*     */     public void updateIconImages() {}
/*     */     
/*     */     public void setMenuBar(MenuBar param1MenuBar) {}
/*     */     
/*     */     public void setResizable(boolean param1Boolean) {}
/*     */     
/*     */     public void setState(int param1Int) {}
/*     */     
/*     */     public int getState() {
/* 536 */       return 0;
/*     */     }
/*     */     public void setMaximizedBounds(Rectangle param1Rectangle) {}
/*     */     public void toFront() {}
/*     */     public void toBack() {}
/*     */     
/*     */     public Component getGlobalHeavyweightFocusOwner() {
/* 543 */       return null;
/*     */     } public void updateFocusableWindowState() {} public void updateAlwaysOnTop() {} public void updateAlwaysOnTopState() {} public void setBoundsPrivate(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 545 */       setBounds(param1Int1, param1Int2, param1Int3, param1Int4, 3);
/*     */     }
/*     */     public Rectangle getBoundsPrivate() {
/* 548 */       return getBounds();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setModalBlocked(Dialog param1Dialog, boolean param1Boolean) {}
/*     */ 
/*     */     
/*     */     public void restack() {
/* 556 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isRestackSupported() {
/* 563 */       return false;
/*     */     }
/*     */     public boolean requestWindowFocus() {
/* 566 */       return false;
/*     */     }
/*     */     
/*     */     public void updateMinimumSize() {}
/*     */     
/*     */     public void setOpacity(float param1Float) {}
/*     */     
/*     */     public void setOpaque(boolean param1Boolean) {}
/*     */     
/*     */     public void updateWindow() {}
/*     */     
/*     */     public void repositionSecurityWarning() {}
/*     */     
/*     */     public void emulateActivation(boolean param1Boolean) {} }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/EmbeddedFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */