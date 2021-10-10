/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.AWTException;
/*     */ import java.awt.BufferCapabilities;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.InputEvent;
/*     */ import java.awt.event.InvocationEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.PaintEvent;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.ImageProducer;
/*     */ import java.awt.image.VolatileImage;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import java.awt.peer.ContainerPeer;
/*     */ import sun.awt.CausedFocusEvent;
/*     */ import sun.java2d.pipe.Region;
/*     */ 
/*     */ 
/*     */ public class XEmbedChildProxyPeer
/*     */   implements ComponentPeer, XEventDispatcher
/*     */ {
/*     */   XEmbeddingContainer container;
/*     */   XEmbedChildProxy proxy;
/*     */   long handle;
/*     */   
/*     */   XEmbedChildProxyPeer(XEmbedChildProxy paramXEmbedChildProxy) {
/*  43 */     this.container = paramXEmbedChildProxy.getEmbeddingContainer();
/*  44 */     this.handle = paramXEmbedChildProxy.getHandle();
/*  45 */     this.proxy = paramXEmbedChildProxy;
/*  46 */     initDispatching();
/*     */   }
/*     */   
/*     */   void initDispatching() {
/*  50 */     XToolkit.awtLock();
/*     */     try {
/*  52 */       XToolkit.addEventDispatcher(this.handle, this);
/*  53 */       XlibWrapper.XSelectInput(XToolkit.getDisplay(), this.handle, 4325376L);
/*     */     }
/*     */     finally {
/*     */       
/*  57 */       XToolkit.awtUnlock();
/*     */     } 
/*  59 */     this.container.notifyChildEmbedded(this.handle);
/*     */   }
/*  61 */   public boolean isObscured() { return false; } public boolean canDetermineObscurity() {
/*  62 */     return false;
/*     */   } public void setVisible(boolean paramBoolean) {
/*  64 */     if (!paramBoolean) {
/*  65 */       XToolkit.awtLock();
/*     */       try {
/*  67 */         XlibWrapper.XUnmapWindow(XToolkit.getDisplay(), this.handle);
/*     */       } finally {
/*     */         
/*  70 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } else {
/*  73 */       XToolkit.awtLock();
/*     */       try {
/*  75 */         XlibWrapper.XMapWindow(XToolkit.getDisplay(), this.handle);
/*     */       } finally {
/*     */         
/*  78 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean paramBoolean) {}
/*     */   
/*     */   public void paint(Graphics paramGraphics) {}
/*     */   
/*     */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  88 */     XToolkit.awtLock();
/*     */     try {
/*  90 */       XlibWrapper.XMoveResizeWindow(XToolkit.getDisplay(), this.handle, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } finally {
/*     */       
/*  93 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   } public void repaint(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {} public void print(Graphics paramGraphics) {}
/*     */   public void handleEvent(AWTEvent paramAWTEvent) {
/*  97 */     switch (paramAWTEvent.getID()) {
/*     */       case 1004:
/*  99 */         XKeyboardFocusManagerPeer.getInstance().setCurrentFocusOwner(this.proxy);
/* 100 */         this.container.focusGained(this.handle);
/*     */         break;
/*     */       case 1005:
/* 103 */         XKeyboardFocusManagerPeer.getInstance().setCurrentFocusOwner((Component)null);
/* 104 */         this.container.focusLost(this.handle);
/*     */         break;
/*     */       case 401:
/*     */       case 402:
/* 108 */         if (!((InputEvent)paramAWTEvent).isConsumed())
/* 109 */           this.container.forwardKeyEvent(this.handle, (KeyEvent)paramAWTEvent); 
/*     */         break;
/*     */     } 
/*     */   }
/*     */   public void coalescePaintEvent(PaintEvent paramPaintEvent) {}
/*     */   
/*     */   public Point getLocationOnScreen() {
/* 116 */     XWindowAttributes xWindowAttributes = new XWindowAttributes();
/* 117 */     XToolkit.awtLock();
/*     */     try {
/* 119 */       XlibWrapper.XGetWindowAttributes(XToolkit.getDisplay(), this.handle, xWindowAttributes.pData);
/* 120 */       return new Point(xWindowAttributes.get_x(), xWindowAttributes.get_y());
/*     */     } finally {
/* 122 */       XToolkit.awtUnlock();
/* 123 */       xWindowAttributes.dispose();
/*     */     } 
/*     */   }
/*     */   public Dimension getPreferredSize() {
/* 127 */     XToolkit.awtLock();
/* 128 */     long l = XlibWrapper.XAllocSizeHints();
/*     */     try {
/* 130 */       XSizeHints xSizeHints = new XSizeHints(l);
/* 131 */       XlibWrapper.XGetWMNormalHints(XToolkit.getDisplay(), this.handle, l, XlibWrapper.larg1);
/* 132 */       Dimension dimension = new Dimension(xSizeHints.get_width(), xSizeHints.get_height());
/* 133 */       return dimension;
/*     */     } finally {
/* 135 */       XlibWrapper.XFree(l);
/* 136 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   public Dimension getMinimumSize() {
/* 140 */     XToolkit.awtLock();
/* 141 */     long l = XlibWrapper.XAllocSizeHints();
/*     */     try {
/* 143 */       XSizeHints xSizeHints = new XSizeHints(l);
/* 144 */       XlibWrapper.XGetWMNormalHints(XToolkit.getDisplay(), this.handle, l, XlibWrapper.larg1);
/* 145 */       Dimension dimension = new Dimension(xSizeHints.get_min_width(), xSizeHints.get_min_height());
/* 146 */       return dimension;
/*     */     } finally {
/* 148 */       XlibWrapper.XFree(l);
/* 149 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/* 152 */   public ColorModel getColorModel() { return null; } public Toolkit getToolkit() {
/* 153 */     return Toolkit.getDefaultToolkit();
/*     */   }
/* 155 */   public Graphics getGraphics() { return null; } public FontMetrics getFontMetrics(Font paramFont) {
/* 156 */     return null;
/*     */   } public void dispose() {
/* 158 */     this.container.detachChild(this.handle);
/*     */   }
/*     */   public void setForeground(Color paramColor) {}
/*     */   public void setBackground(Color paramColor) {}
/*     */   public void setFont(Font paramFont) {}
/*     */   public void updateCursorImmediately() {}
/*     */   
/*     */   void postEvent(AWTEvent paramAWTEvent) {
/* 166 */     XToolkit.postEvent(XToolkit.targetToAppContext(this.proxy), paramAWTEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean simulateMotifRequestFocus(Component paramComponent, boolean paramBoolean1, boolean paramBoolean2, long paramLong) {
/* 172 */     if (paramComponent == null) {
/* 173 */       paramComponent = this.proxy;
/*     */     }
/* 175 */     Component component = XKeyboardFocusManagerPeer.getInstance().getCurrentFocusOwner();
/* 176 */     if (component != null && component.getPeer() == null) {
/* 177 */       component = null;
/*     */     }
/* 179 */     FocusEvent focusEvent1 = new FocusEvent(paramComponent, 1004, false, component);
/* 180 */     FocusEvent focusEvent2 = null;
/* 181 */     if (component != null) {
/* 182 */       focusEvent2 = new FocusEvent(component, 1005, false, paramComponent);
/*     */     }
/*     */ 
/*     */     
/* 186 */     if (focusEvent2 != null) {
/* 187 */       postEvent(XComponentPeer.wrapInSequenced(focusEvent2));
/*     */     }
/* 189 */     postEvent(XComponentPeer.wrapInSequenced(focusEvent1));
/*     */     
/* 191 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean requestFocus(Component paramComponent, boolean paramBoolean1, boolean paramBoolean2, long paramLong, CausedFocusEvent.Cause paramCause) {
/*     */     Container container;
/* 201 */     int i = XKeyboardFocusManagerPeer.shouldNativelyFocusHeavyweight(this.proxy, paramComponent, paramBoolean1, false, paramLong, paramCause);
/*     */ 
/*     */     
/* 204 */     switch (i) {
/*     */       case 0:
/* 206 */         return false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 2:
/* 219 */         container = this.proxy.getParent();
/*     */         
/* 221 */         while (container != null && !(container instanceof Window)) {
/* 222 */           container = container.getParent();
/*     */         }
/* 224 */         if (container != null) {
/* 225 */           Window window = (Window)container;
/*     */           
/* 227 */           if (!window.isFocused() && 
/* 228 */             XKeyboardFocusManagerPeer.getInstance().getCurrentFocusedWindow() == window)
/*     */           {
/*     */             
/* 231 */             return true;
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 240 */         return simulateMotifRequestFocus(paramComponent, paramBoolean1, paramBoolean2, paramLong);
/*     */ 
/*     */       
/*     */       case 1:
/* 244 */         return true;
/*     */     } 
/* 246 */     return false;
/*     */   }
/*     */   public boolean isFocusable() {
/* 249 */     return true;
/*     */   }
/*     */   
/* 252 */   public Image createImage(ImageProducer paramImageProducer) { return null; }
/* 253 */   public Image createImage(int paramInt1, int paramInt2) { return null; }
/* 254 */   public VolatileImage createVolatileImage(int paramInt1, int paramInt2) { return null; }
/* 255 */   public boolean prepareImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) { return false; }
/* 256 */   public int checkImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) { return 0; }
/* 257 */   public GraphicsConfiguration getGraphicsConfiguration() { return null; } public boolean handlesWheelScrolling() {
/* 258 */     return true;
/*     */   } public void createBuffers(int paramInt, BufferCapabilities paramBufferCapabilities) throws AWTException {}
/*     */   public Image getBackBuffer() {
/* 261 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void flip(int paramInt1, int paramInt2, int paramInt3, int paramInt4, BufferCapabilities.FlipContents paramFlipContents) {}
/*     */ 
/*     */   
/*     */   public void destroyBuffers() {}
/*     */ 
/*     */   
/*     */   public void layout() {}
/*     */ 
/*     */   
/*     */   public Dimension preferredSize() {
/* 276 */     return getPreferredSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension minimumSize() {
/* 283 */     return getMinimumSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void show() {
/* 290 */     setVisible(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void hide() {
/* 297 */     setVisible(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enable() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disable() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 314 */     setBounds(paramInt1, paramInt2, paramInt3, paramInt4, 3);
/*     */   }
/*     */   
/*     */   Window getTopLevel(Component paramComponent) {
/* 318 */     while (paramComponent != null && !(paramComponent instanceof Window)) {
/* 319 */       paramComponent = paramComponent.getParent();
/*     */     }
/* 321 */     return (Window)paramComponent;
/*     */   }
/*     */   
/*     */   void childResized() {
/* 325 */     XToolkit.postEvent(XToolkit.targetToAppContext(this.proxy), new ComponentEvent(this.proxy, 101));
/* 326 */     this.container.childResized(this.proxy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void handlePropertyNotify(XEvent paramXEvent) {
/* 335 */     XPropertyEvent xPropertyEvent = paramXEvent.get_xproperty();
/* 336 */     if (xPropertyEvent.get_atom() == 40L)
/* 337 */       childResized(); 
/*     */   }
/*     */   
/*     */   void handleConfigureNotify(XEvent paramXEvent) {
/* 341 */     childResized();
/*     */   }
/*     */   public void dispatchEvent(XEvent paramXEvent) {
/* 344 */     int i = paramXEvent.get_type();
/* 345 */     switch (i) {
/*     */       case 28:
/* 347 */         handlePropertyNotify(paramXEvent);
/*     */         break;
/*     */       case 22:
/* 350 */         handleConfigureNotify(paramXEvent);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   void requestXEmbedFocus() {
/* 356 */     postEvent(new InvocationEvent(this.proxy, new Runnable() {
/*     */             public void run() {
/* 358 */               XEmbedChildProxyPeer.this.proxy.requestFocusInWindow();
/*     */             }
/*     */           }));
/*     */   }
/*     */   
/*     */   public void reparent(ContainerPeer paramContainerPeer) {}
/*     */   
/*     */   public boolean isReparentSupported() {
/* 366 */     return false;
/*     */   }
/*     */   public Rectangle getBounds() {
/* 369 */     XWindowAttributes xWindowAttributes = new XWindowAttributes();
/* 370 */     XToolkit.awtLock();
/*     */     try {
/* 372 */       XlibWrapper.XGetWindowAttributes(XToolkit.getDisplay(), this.handle, xWindowAttributes.pData);
/* 373 */       return new Rectangle(xWindowAttributes.get_x(), xWindowAttributes.get_y(), xWindowAttributes.get_width(), xWindowAttributes.get_height());
/*     */     } finally {
/* 375 */       XToolkit.awtUnlock();
/* 376 */       xWindowAttributes.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBoundsOperation(int paramInt) {}
/*     */ 
/*     */   
/*     */   public void applyShape(Region paramRegion) {}
/*     */   
/*     */   public void setZOrder(ComponentPeer paramComponentPeer) {}
/*     */   
/*     */   public boolean updateGraphicsData(GraphicsConfiguration paramGraphicsConfiguration) {
/* 389 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XEmbedChildProxyPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */