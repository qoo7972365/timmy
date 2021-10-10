/*      */ package sun.awt.X11;
/*      */ 
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.AWTException;
/*      */ import java.awt.BufferCapabilities;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.SystemColor;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.awt.dnd.DropTarget;
/*      */ import java.awt.dnd.peer.DropTargetPeer;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.InputEvent;
/*      */ import java.awt.event.InputMethodEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseWheelEvent;
/*      */ import java.awt.event.PaintEvent;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.awt.image.ImageProducer;
/*      */ import java.awt.image.VolatileImage;
/*      */ import java.awt.peer.ComponentPeer;
/*      */ import java.awt.peer.ContainerPeer;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.Collection;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.CausedFocusEvent;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.awt.X11GraphicsConfig;
/*      */ import sun.awt.image.SunVolatileImage;
/*      */ import sun.awt.image.ToolkitImage;
/*      */ import sun.font.FontDesignMetrics;
/*      */ import sun.java2d.BackBufferCapsProvider;
/*      */ import sun.java2d.SurfaceData;
/*      */ import sun.java2d.pipe.Region;
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
/*      */ public class XComponentPeer
/*      */   extends XWindow
/*      */   implements ComponentPeer, DropTargetPeer, BackBufferCapsProvider
/*      */ {
/*   78 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XComponentPeer");
/*   79 */   private static final PlatformLogger buffersLog = PlatformLogger.getLogger("sun.awt.X11.XComponentPeer.multibuffer");
/*   80 */   private static final PlatformLogger focusLog = PlatformLogger.getLogger("sun.awt.X11.focus.XComponentPeer");
/*   81 */   private static final PlatformLogger fontLog = PlatformLogger.getLogger("sun.awt.X11.font.XComponentPeer");
/*   82 */   private static final PlatformLogger enableLog = PlatformLogger.getLogger("sun.awt.X11.enable.XComponentPeer");
/*   83 */   private static final PlatformLogger shapeLog = PlatformLogger.getLogger("sun.awt.X11.shape.XComponentPeer");
/*      */   
/*      */   boolean paintPending = false;
/*      */   
/*      */   boolean isLayouting = false;
/*      */   
/*      */   private boolean enabled;
/*      */   
/*      */   protected int boundsOperation;
/*      */   
/*      */   Color foreground;
/*      */   
/*      */   Color background;
/*      */   
/*      */   Color darkShadow;
/*      */   
/*      */   Color lightShadow;
/*      */   
/*      */   Color selectColor;
/*      */   Font font;
/*  103 */   private long backBuffer = 0L;
/*  104 */   private VolatileImage xBackBuffer = null; static Color[] systemColors; boolean bHasFocus; private static Class seClass;
/*      */   private static Constructor seCtor;
/*      */   static final int BACKGROUND_COLOR = 0;
/*      */   static final int HIGHLIGHT_COLOR = 1;
/*      */   static final int SHADOW_COLOR = 2;
/*      */   static final int FOREGROUND_COLOR = 3;
/*      */   private BufferCapabilities backBufferCaps;
/*      */   
/*  112 */   XComponentPeer(XCreateWindowParams paramXCreateWindowParams) { super(paramXCreateWindowParams);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  204 */     this.bHasFocus = false; } XComponentPeer(Component paramComponent, long paramLong, Rectangle paramRectangle) { super(paramComponent, paramLong, paramRectangle); this.bHasFocus = false; } void preInit(XCreateWindowParams paramXCreateWindowParams) { super.preInit(paramXCreateWindowParams); this.boundsOperation = 3; } void postInit(XCreateWindowParams paramXCreateWindowParams) { super.postInit(paramXCreateWindowParams); pSetCursor(this.target.getCursor()); this.foreground = this.target.getForeground(); this.background = this.target.getBackground(); this.font = this.target.getFont(); if (isInitialReshape()) { Rectangle rectangle = this.target.getBounds(); reshape(rectangle.x, rectangle.y, rectangle.width, rectangle.height); }  setEnabled(this.target.isEnabled()); if (this.target.isVisible()) setVisible(true);  } XComponentPeer(Component paramComponent) { super(paramComponent); this.bHasFocus = false; } protected boolean isInitialReshape() { return true; } XComponentPeer() { this.bHasFocus = false; } public void reparent(ContainerPeer paramContainerPeer) { XComponentPeer xComponentPeer = (XComponentPeer)paramContainerPeer; XToolkit.awtLock(); try {
/*      */       XlibWrapper.XReparentWindow(XToolkit.getDisplay(), getWindow(), xComponentPeer.getContentWindow(), this.x, this.y);
/*      */       this.parentWindow = xComponentPeer;
/*      */     } finally {
/*      */       XToolkit.awtUnlock();
/*      */     }  }
/*      */   public boolean isReparentSupported() { return System.getProperty("sun.awt.X11.XComponentPeer.reparentNotSupported", "false").equals("false"); }
/*  211 */   public final boolean hasFocus() { return this.bHasFocus; } public boolean isObscured() { Container container1 = (this.target instanceof Container) ? (Container)this.target : this.target.getParent(); if (container1 == null)
/*      */       return true;  Container container2; while ((container2 = container1.getParent()) != null)
/*      */       container1 = container2;  if (container1 instanceof Window) {
/*      */       XWindowPeer xWindowPeer = (XWindowPeer)container1.getPeer(); if (xWindowPeer != null)
/*      */         return (xWindowPeer.winAttr.visibilityState != XWindowAttributesData.AWT_UNOBSCURED); 
/*      */     }  return true; }
/*      */   public boolean canDetermineObscurity() { return true; }
/*  218 */   public void focusGained(FocusEvent paramFocusEvent) { if (focusLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  219 */       focusLog.fine("{0}", new Object[] { paramFocusEvent });
/*      */     }
/*  221 */     this.bHasFocus = true; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void focusLost(FocusEvent paramFocusEvent) {
/*  228 */     if (focusLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  229 */       focusLog.fine("{0}", new Object[] { paramFocusEvent });
/*      */     }
/*  231 */     this.bHasFocus = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFocusable() {
/*  236 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final AWTEvent wrapInSequenced(AWTEvent paramAWTEvent) {
/*      */     try {
/*  244 */       if (seClass == null) {
/*  245 */         seClass = Class.forName("java.awt.SequencedEvent");
/*      */       }
/*      */       
/*  248 */       if (seCtor == null) {
/*  249 */         seCtor = AccessController.<Constructor>doPrivileged(new PrivilegedExceptionAction<Constructor>() {
/*      */               public Object run() throws Exception {
/*  251 */                 Constructor constructor = XComponentPeer.seClass.getConstructor(new Class[] { AWTEvent.class });
/*  252 */                 constructor.setAccessible(true);
/*  253 */                 return constructor;
/*      */               }
/*      */             });
/*      */       }
/*      */       
/*  258 */       return seCtor.newInstance(new Object[] { paramAWTEvent });
/*      */     }
/*  260 */     catch (ClassNotFoundException classNotFoundException) {
/*  261 */       throw new NoClassDefFoundError("java.awt.SequencedEvent.");
/*      */     }
/*  263 */     catch (PrivilegedActionException privilegedActionException) {
/*  264 */       throw new NoClassDefFoundError("java.awt.SequencedEvent.");
/*      */     }
/*  266 */     catch (InstantiationException instantiationException) {
/*      */       
/*      */       assert false;
/*  269 */     } catch (IllegalAccessException illegalAccessException) {
/*      */       
/*      */       assert false;
/*  272 */     } catch (InvocationTargetException invocationTargetException) {
/*      */       assert false;
/*      */     } 
/*      */     
/*  276 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean requestFocus(Component paramComponent, boolean paramBoolean1, boolean paramBoolean2, long paramLong, CausedFocusEvent.Cause paramCause) {
/*      */     Window window;
/*      */     XWindowPeer xWindowPeer;
/*      */     boolean bool;
/*  285 */     if (XKeyboardFocusManagerPeer.processSynchronousLightweightTransfer(this.target, paramComponent, paramBoolean1, paramBoolean2, paramLong))
/*      */     {
/*      */       
/*  288 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  292 */     int i = XKeyboardFocusManagerPeer.shouldNativelyFocusHeavyweight(this.target, paramComponent, paramBoolean1, paramBoolean2, paramLong, paramCause);
/*      */ 
/*      */ 
/*      */     
/*  296 */     switch (i) {
/*      */       case 0:
/*  298 */         return false;
/*      */ 
/*      */       
/*      */       case 2:
/*  302 */         if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  303 */           focusLog.finer("Proceeding with request to " + paramComponent + " in " + this.target);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  314 */         window = SunToolkit.getContainingWindow(this.target);
/*  315 */         if (window == null) {
/*  316 */           return rejectFocusRequestHelper("WARNING: Parent window is null");
/*      */         }
/*  318 */         xWindowPeer = (XWindowPeer)window.getPeer();
/*  319 */         if (xWindowPeer == null) {
/*  320 */           return rejectFocusRequestHelper("WARNING: Parent window's peer is null");
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  327 */         bool = xWindowPeer.requestWindowFocus((XWindowPeer)null);
/*      */         
/*  329 */         if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  330 */           focusLog.finer("Requested window focus: " + bool);
/*      */         }
/*      */ 
/*      */         
/*  334 */         if (!bool || !window.isFocused()) {
/*  335 */           return rejectFocusRequestHelper("Waiting for asynchronous processing of the request");
/*      */         }
/*  337 */         return XKeyboardFocusManagerPeer.deliverFocus(paramComponent, this.target, paramBoolean1, paramBoolean2, paramLong, paramCause);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  345 */         return true;
/*      */     } 
/*  347 */     return false;
/*      */   }
/*      */   
/*      */   private boolean rejectFocusRequestHelper(String paramString) {
/*  351 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  352 */       focusLog.finer(paramString);
/*      */     }
/*  354 */     XKeyboardFocusManagerPeer.removeLastFocusRequest(this.target);
/*  355 */     return false;
/*      */   }
/*      */   
/*      */   void handleJavaFocusEvent(AWTEvent paramAWTEvent) {
/*  359 */     if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  360 */       focusLog.finer(paramAWTEvent.toString());
/*      */     }
/*  362 */     if (paramAWTEvent.getID() == 1004) {
/*  363 */       focusGained((FocusEvent)paramAWTEvent);
/*      */     } else {
/*  365 */       focusLost((FocusEvent)paramAWTEvent);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void handleJavaWindowFocusEvent(AWTEvent paramAWTEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVisible(boolean paramBoolean) {
/*  379 */     xSetVisible(paramBoolean);
/*      */   }
/*      */   
/*      */   public void hide() {
/*  383 */     setVisible(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnabled(boolean paramBoolean) {
/*  390 */     if (enableLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  391 */       enableLog.fine("{0}ing {1}", new Object[] { paramBoolean ? "Enabl" : "Disabl", this });
/*      */     }
/*  393 */     boolean bool = paramBoolean;
/*      */ 
/*      */     
/*  396 */     Container container = SunToolkit.getNativeContainer(this.target);
/*  397 */     if (container != null) {
/*  398 */       bool &= ((XComponentPeer)container.getPeer()).isEnabled();
/*      */     }
/*  400 */     synchronized (getStateLock()) {
/*  401 */       if (this.enabled == bool) {
/*      */         return;
/*      */       }
/*  404 */       this.enabled = bool;
/*      */     } 
/*      */     
/*  407 */     if (this.target instanceof Container) {
/*  408 */       Component[] arrayOfComponent = ((Container)this.target).getComponents();
/*  409 */       for (Component component : arrayOfComponent) {
/*  410 */         ComponentPeer componentPeer = component.getPeer();
/*  411 */         if (componentPeer != null) {
/*  412 */           componentPeer.setEnabled((bool && component.isEnabled()));
/*      */         }
/*      */       } 
/*      */     } 
/*  416 */     repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isEnabled() {
/*  423 */     synchronized (getStateLock()) {
/*  424 */       return this.enabled;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void paint(Graphics paramGraphics) {
/*  430 */     super.paint(paramGraphics);
/*      */     
/*  432 */     this.target.paint(paramGraphics);
/*      */   }
/*      */   
/*      */   public Graphics getGraphics() {
/*  436 */     return getGraphics(this.surfaceData, getPeerForeground(), getPeerBackground(), getPeerFont());
/*      */   }
/*      */   
/*      */   public void print(Graphics paramGraphics) {
/*  440 */     paramGraphics.setColor(this.target.getBackground());
/*  441 */     paramGraphics.fillRect(0, 0, this.target.getWidth(), this.target.getHeight());
/*  442 */     paramGraphics.setColor(this.target.getForeground());
/*      */     
/*  444 */     paintPeer(paramGraphics);
/*      */     
/*  446 */     this.target.print(paramGraphics);
/*      */   }
/*      */   
/*      */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  450 */     this.x = paramInt1;
/*  451 */     this.y = paramInt2;
/*  452 */     this.width = paramInt3;
/*  453 */     this.height = paramInt4;
/*  454 */     xSetBounds(paramInt1, paramInt2, paramInt3, paramInt4);
/*  455 */     validateSurface();
/*  456 */     layout();
/*      */   }
/*      */   
/*      */   public void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  460 */     setBounds(paramInt1, paramInt2, paramInt3, paramInt4, 3);
/*      */   }
/*      */   
/*      */   public void coalescePaintEvent(PaintEvent paramPaintEvent) {
/*  464 */     Rectangle rectangle = paramPaintEvent.getUpdateRect();
/*  465 */     if (!(paramPaintEvent instanceof sun.awt.event.IgnorePaintEvent)) {
/*  466 */       this.paintArea.add(rectangle, paramPaintEvent.getID());
/*      */     }
/*      */     
/*  469 */     switch (paramPaintEvent.getID()) {
/*      */       case 801:
/*  471 */         if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*  472 */           log.finer("XCP coalescePaintEvent : UPDATE : add : x = " + rectangle.x + ", y = " + rectangle.y + ", width = " + rectangle.width + ",height = " + rectangle.height);
/*      */         }
/*      */         return;
/*      */       
/*      */       case 800:
/*  477 */         if (log.isLoggable(PlatformLogger.Level.FINER)) {
/*  478 */           log.finer("XCP coalescePaintEvent : PAINT : add : x = " + rectangle.x + ", y = " + rectangle.y + ", width = " + rectangle.width + ",height = " + rectangle.height);
/*      */         }
/*      */         return;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   XWindowPeer getParentTopLevel() {
/*  487 */     AWTAccessor.ComponentAccessor componentAccessor = AWTAccessor.getComponentAccessor();
/*  488 */     Container container = (this.target instanceof Container) ? (Container)this.target : componentAccessor.getParent(this.target);
/*      */     
/*  490 */     while (container != null && !(container instanceof Window)) {
/*  491 */       container = componentAccessor.getParent(container);
/*      */     }
/*  493 */     if (container != null) {
/*  494 */       return componentAccessor.<XWindowPeer>getPeer(container);
/*      */     }
/*  496 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void handleJavaMouseEvent(MouseEvent paramMouseEvent) {
/*  502 */     switch (paramMouseEvent.getID()) {
/*      */       case 501:
/*  504 */         if (this.target == paramMouseEvent.getSource() && 
/*  505 */           !this.target.isFocusOwner() && 
/*  506 */           XKeyboardFocusManagerPeer.shouldFocusOnClick(this.target)) {
/*      */           
/*  508 */           XWindowPeer xWindowPeer = getParentTopLevel();
/*  509 */           Window window = (Window)xWindowPeer.getTarget();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  520 */           XKeyboardFocusManagerPeer.requestFocusFor(this.target, CausedFocusEvent.Cause.MOUSE_EVENT);
/*      */         } 
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void handleJavaKeyEvent(KeyEvent paramKeyEvent) {}
/*      */ 
/*      */ 
/*      */   
/*      */   void handleJavaMouseWheelEvent(MouseWheelEvent paramMouseWheelEvent) {}
/*      */ 
/*      */ 
/*      */   
/*      */   void handleJavaInputMethodEvent(InputMethodEvent paramInputMethodEvent) {}
/*      */ 
/*      */   
/*      */   void handleF10JavaKeyEvent(KeyEvent paramKeyEvent) {
/*  540 */     if (paramKeyEvent.getID() == 401 && paramKeyEvent.getKeyCode() == 121) {
/*  541 */       XWindowPeer xWindowPeer = getToplevelXWindow();
/*  542 */       if (xWindowPeer instanceof XFramePeer) {
/*  543 */         XMenuBarPeer xMenuBarPeer = ((XFramePeer)xWindowPeer).getMenubarPeer();
/*  544 */         if (xMenuBarPeer != null) {
/*  545 */           xMenuBarPeer.handleF10KeyPress(paramKeyEvent);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleEvent(AWTEvent paramAWTEvent) {
/*  552 */     if (paramAWTEvent instanceof InputEvent && !((InputEvent)paramAWTEvent).isConsumed() && this.target.isEnabled()) {
/*  553 */       if (paramAWTEvent instanceof MouseEvent) {
/*  554 */         if (paramAWTEvent instanceof MouseWheelEvent) {
/*  555 */           handleJavaMouseWheelEvent((MouseWheelEvent)paramAWTEvent);
/*      */         } else {
/*      */           
/*  558 */           handleJavaMouseEvent((MouseEvent)paramAWTEvent);
/*      */         } 
/*  560 */       } else if (paramAWTEvent instanceof KeyEvent) {
/*  561 */         handleF10JavaKeyEvent((KeyEvent)paramAWTEvent);
/*  562 */         handleJavaKeyEvent((KeyEvent)paramAWTEvent);
/*      */       }
/*      */     
/*  565 */     } else if (paramAWTEvent instanceof KeyEvent && !((InputEvent)paramAWTEvent).isConsumed()) {
/*      */       
/*  567 */       handleF10JavaKeyEvent((KeyEvent)paramAWTEvent);
/*      */     }
/*  569 */     else if (paramAWTEvent instanceof InputMethodEvent) {
/*  570 */       handleJavaInputMethodEvent((InputMethodEvent)paramAWTEvent);
/*      */     } 
/*      */     
/*  573 */     int i = paramAWTEvent.getID();
/*      */     
/*  575 */     switch (i) {
/*      */       
/*      */       case 800:
/*  578 */         this.paintPending = false;
/*      */ 
/*      */ 
/*      */       
/*      */       case 801:
/*  583 */         if (!this.isLayouting && !this.paintPending) {
/*  584 */           this.paintArea.paint(this.target, false);
/*      */         }
/*      */         return;
/*      */       case 1004:
/*      */       case 1005:
/*  589 */         handleJavaFocusEvent(paramAWTEvent);
/*      */         break;
/*      */       case 207:
/*      */       case 208:
/*  593 */         handleJavaWindowFocusEvent(paramAWTEvent);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMinimumSize() {
/*  602 */     return this.target.getSize();
/*      */   }
/*      */   
/*      */   public Dimension getPreferredSize() {
/*  606 */     return getMinimumSize();
/*      */   }
/*      */   
/*      */   public void layout() {}
/*      */   
/*      */   void updateMotifColors(Color paramColor) {
/*  612 */     int i = paramColor.getRed();
/*  613 */     int j = paramColor.getGreen();
/*  614 */     int k = paramColor.getBlue();
/*      */     
/*  616 */     this.darkShadow = new Color(MotifColorUtilities.calculateBottomShadowFromBackground(i, j, k));
/*  617 */     this.lightShadow = new Color(MotifColorUtilities.calculateTopShadowFromBackground(i, j, k));
/*  618 */     this.selectColor = new Color(MotifColorUtilities.calculateSelectFromBackground(i, j, k));
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
/*      */   public void drawMotif3DRect(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/*  630 */     paramGraphics.setColor(paramBoolean ? this.darkShadow : this.lightShadow);
/*  631 */     paramGraphics.drawLine(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2);
/*  632 */     paramGraphics.drawLine(paramInt1, paramInt2 + paramInt4, paramInt1, paramInt2);
/*      */     
/*  634 */     paramGraphics.setColor(paramBoolean ? this.lightShadow : this.darkShadow);
/*  635 */     paramGraphics.drawLine(paramInt1 + 1, paramInt2 + paramInt4, paramInt1 + paramInt3, paramInt2 + paramInt4);
/*  636 */     paramGraphics.drawLine(paramInt1 + paramInt3, paramInt2 + paramInt4, paramInt1 + paramInt3, paramInt2 + 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBackground(Color paramColor) {
/*  641 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  642 */       log.fine("Set background to " + paramColor);
/*      */     }
/*  644 */     synchronized (getStateLock()) {
/*  645 */       if (Objects.equals(this.background, paramColor)) {
/*      */         return;
/*      */       }
/*  648 */       this.background = paramColor;
/*      */     } 
/*  650 */     super.setBackground(paramColor);
/*  651 */     repaint();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setForeground(Color paramColor) {
/*  656 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  657 */       log.fine("Set foreground to " + paramColor);
/*      */     }
/*  659 */     synchronized (getStateLock()) {
/*  660 */       if (Objects.equals(this.foreground, paramColor)) {
/*      */         return;
/*      */       }
/*  663 */       this.foreground = paramColor;
/*      */     } 
/*  665 */     repaint();
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
/*      */   public FontMetrics getFontMetrics(Font paramFont) {
/*  680 */     if (fontLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  681 */       fontLog.fine("Getting font metrics for " + paramFont);
/*      */     }
/*  683 */     return FontDesignMetrics.getMetrics(paramFont);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFont(Font paramFont) {
/*  688 */     if (paramFont == null) {
/*  689 */       paramFont = XWindow.getDefaultFont();
/*      */     }
/*  691 */     synchronized (getStateLock()) {
/*  692 */       if (paramFont.equals(this.font)) {
/*      */         return;
/*      */       }
/*  695 */       this.font = paramFont;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  700 */     repaint();
/*      */   }
/*      */   
/*      */   public Font getFont() {
/*  704 */     return this.font;
/*      */   }
/*      */   
/*      */   public void updateCursorImmediately() {
/*  708 */     XGlobalCursorManager.getCursorManager().updateCursorImmediately();
/*      */   }
/*      */   
/*      */   public final void pSetCursor(Cursor paramCursor) {
/*  712 */     pSetCursor(paramCursor, true);
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
/*      */   public void pSetCursor(Cursor paramCursor, boolean paramBoolean) {
/*  726 */     XToolkit.awtLock();
/*      */     try {
/*  728 */       long l1 = XGlobalCursorManager.getCursor(paramCursor);
/*      */       
/*  730 */       XSetWindowAttributes xSetWindowAttributes = new XSetWindowAttributes();
/*  731 */       xSetWindowAttributes.set_cursor(l1);
/*      */       
/*  733 */       long l2 = 16384L;
/*      */       
/*  735 */       XlibWrapper.XChangeWindowAttributes(XToolkit.getDisplay(), getWindow(), l2, xSetWindowAttributes.pData);
/*  736 */       XlibWrapper.XFlush(XToolkit.getDisplay());
/*  737 */       xSetWindowAttributes.dispose();
/*      */     } finally {
/*  739 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public Image createImage(ImageProducer paramImageProducer) {
/*  744 */     return new ToolkitImage(paramImageProducer);
/*      */   }
/*      */   
/*      */   public Image createImage(int paramInt1, int paramInt2) {
/*  748 */     return this.graphicsConfig.createAcceleratedImage(this.target, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public VolatileImage createVolatileImage(int paramInt1, int paramInt2) {
/*  752 */     return new SunVolatileImage(this.target, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public boolean prepareImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) {
/*  756 */     return Toolkit.getDefaultToolkit().prepareImage(paramImage, paramInt1, paramInt2, paramImageObserver);
/*      */   }
/*      */   
/*      */   public int checkImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) {
/*  760 */     return Toolkit.getDefaultToolkit().checkImage(paramImage, paramInt1, paramInt2, paramImageObserver);
/*      */   }
/*      */   
/*      */   public Dimension preferredSize() {
/*  764 */     return getPreferredSize();
/*      */   }
/*      */   
/*      */   public Dimension minimumSize() {
/*  768 */     return getMinimumSize();
/*      */   }
/*      */   
/*      */   public Insets getInsets() {
/*  772 */     return new Insets(0, 0, 0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void beginValidate() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void endValidate() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public Insets insets() {
/*  787 */     return getInsets();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPaintPending() {
/*  793 */     return (this.paintPending && this.isLayouting);
/*      */   }
/*      */   
/*      */   public boolean handlesWheelScrolling() {
/*  797 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void beginLayout() {
/*  802 */     this.isLayouting = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void endLayout() {
/*  807 */     if (!this.paintPending && !this.paintArea.isEmpty() && 
/*  808 */       !AWTAccessor.getComponentAccessor().getIgnoreRepaint(this.target))
/*      */     {
/*      */       
/*  811 */       postEvent(new PaintEvent(this.target, 800, new Rectangle()));
/*      */     }
/*      */     
/*  814 */     this.isLayouting = false;
/*      */   }
/*      */   
/*      */   public Color getWinBackground() {
/*  818 */     return getPeerBackground();
/*      */   }
/*      */ 
/*      */   
/*      */   static int[] getRGBvals(Color paramColor) {
/*  823 */     int[] arrayOfInt = new int[3];
/*      */     
/*  825 */     arrayOfInt[0] = paramColor.getRed();
/*  826 */     arrayOfInt[1] = paramColor.getGreen();
/*  827 */     arrayOfInt[2] = paramColor.getBlue();
/*      */     
/*  829 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color[] getGUIcolors() {
/*  838 */     Color[] arrayOfColor = new Color[4];
/*      */     
/*  840 */     arrayOfColor[0] = getWinBackground();
/*  841 */     if (arrayOfColor[0] == null) {
/*  842 */       arrayOfColor[0] = super.getWinBackground();
/*      */     }
/*  844 */     if (arrayOfColor[0] == null) {
/*  845 */       arrayOfColor[0] = Color.lightGray;
/*      */     }
/*      */     
/*  848 */     int[] arrayOfInt = getRGBvals(arrayOfColor[0]);
/*      */     
/*  850 */     float[] arrayOfFloat = Color.RGBtoHSB(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], null);
/*      */     
/*  852 */     float f4 = arrayOfFloat[0];
/*  853 */     float f5 = arrayOfFloat[1];
/*  854 */     float f1 = arrayOfFloat[2];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  859 */     float f2 = f1 + 0.2F;
/*  860 */     float f3 = f1 - 0.4F;
/*  861 */     if (f2 > 1.0D) {
/*  862 */       if (1.0D - f1 < 0.05D) {
/*  863 */         f2 = f3 + 0.25F;
/*      */       } else {
/*  865 */         f2 = 1.0F;
/*      */       }
/*      */     
/*  868 */     } else if (f3 < 0.0D) {
/*  869 */       if (f1 - 0.0D < 0.25D) {
/*  870 */         f2 = f1 + 0.75F;
/*  871 */         f3 = f2 - 0.2F;
/*      */       } else {
/*  873 */         f3 = 0.0F;
/*      */       } 
/*      */     } 
/*      */     
/*  877 */     arrayOfColor[1] = Color.getHSBColor(f4, f5, f2);
/*  878 */     arrayOfColor[2] = Color.getHSBColor(f4, f5, f3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  888 */     arrayOfColor[3] = getPeerForeground();
/*  889 */     if (arrayOfColor[3] == null) {
/*  890 */       arrayOfColor[3] = Color.black;
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
/*      */ 
/*      */ 
/*      */     
/*  904 */     if (!isEnabled()) {
/*  905 */       arrayOfColor[0] = arrayOfColor[0].darker();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  912 */       Color color1 = arrayOfColor[0];
/*  913 */       int i = color1.getRed() * 30 + color1.getGreen() * 59 + color1.getBlue() * 11;
/*      */       
/*  915 */       color1 = arrayOfColor[3];
/*  916 */       int j = color1.getRed() * 30 + color1.getGreen() * 59 + color1.getBlue() * 11;
/*      */       
/*  918 */       float f = (float)((j + i) / 51000.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  923 */       Color color2 = new Color((int)(color1.getRed() * f), (int)(color1.getGreen() * f), (int)(color1.getBlue() * f));
/*      */       
/*  925 */       if (color2.equals(arrayOfColor[3]))
/*      */       {
/*  927 */         color2 = new Color(f, f, f);
/*      */       }
/*  929 */       arrayOfColor[3] = color2;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  934 */     return arrayOfColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Color[] getSystemColors() {
/*  944 */     if (systemColors == null) {
/*  945 */       systemColors = new Color[4];
/*  946 */       systemColors[0] = SystemColor.window;
/*  947 */       systemColors[1] = SystemColor.controlLtHighlight;
/*  948 */       systemColors[2] = SystemColor.controlShadow;
/*  949 */       systemColors[3] = SystemColor.windowText;
/*      */     } 
/*  951 */     return systemColors;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw3DOval(Graphics paramGraphics, Color[] paramArrayOfColor, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/*  960 */     Color color = paramGraphics.getColor();
/*  961 */     paramGraphics.setColor(paramBoolean ? paramArrayOfColor[1] : paramArrayOfColor[2]);
/*  962 */     paramGraphics.drawArc(paramInt1, paramInt2, paramInt3, paramInt4, 45, 180);
/*  963 */     paramGraphics.setColor(paramBoolean ? paramArrayOfColor[2] : paramArrayOfColor[1]);
/*  964 */     paramGraphics.drawArc(paramInt1, paramInt2, paramInt3, paramInt4, 225, 180);
/*  965 */     paramGraphics.setColor(color);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw3DRect(Graphics paramGraphics, Color[] paramArrayOfColor, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/*  971 */     Color color = paramGraphics.getColor();
/*  972 */     paramGraphics.setColor(paramBoolean ? paramArrayOfColor[1] : paramArrayOfColor[2]);
/*  973 */     paramGraphics.drawLine(paramInt1, paramInt2, paramInt1, paramInt2 + paramInt4);
/*  974 */     paramGraphics.drawLine(paramInt1 + 1, paramInt2, paramInt1 + paramInt3 - 1, paramInt2);
/*  975 */     paramGraphics.setColor(paramBoolean ? paramArrayOfColor[2] : paramArrayOfColor[1]);
/*  976 */     paramGraphics.drawLine(paramInt1 + 1, paramInt2 + paramInt4, paramInt1 + paramInt3, paramInt2 + paramInt4);
/*  977 */     paramGraphics.drawLine(paramInt1 + paramInt3, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4 - 1);
/*  978 */     paramGraphics.setColor(color);
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
/*      */   void draw3DOval(Graphics paramGraphics, Color paramColor, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/*  990 */     Color color1 = paramGraphics.getColor();
/*  991 */     Color color2 = paramColor.darker();
/*  992 */     Color color3 = paramColor.brighter();
/*      */     
/*  994 */     paramGraphics.setColor(paramBoolean ? color3 : color2);
/*  995 */     paramGraphics.drawArc(paramInt1, paramInt2, paramInt3, paramInt4, 45, 180);
/*  996 */     paramGraphics.setColor(paramBoolean ? color2 : color3);
/*  997 */     paramGraphics.drawArc(paramInt1, paramInt2, paramInt3, paramInt4, 225, 180);
/*  998 */     paramGraphics.setColor(color1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void draw3DRect(Graphics paramGraphics, Color paramColor, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/* 1004 */     Color color1 = paramGraphics.getColor();
/* 1005 */     Color color2 = paramColor.darker();
/* 1006 */     Color color3 = paramColor.brighter();
/*      */     
/* 1008 */     paramGraphics.setColor(paramBoolean ? color3 : color2);
/* 1009 */     paramGraphics.drawLine(paramInt1, paramInt2, paramInt1, paramInt2 + paramInt4);
/* 1010 */     paramGraphics.drawLine(paramInt1 + 1, paramInt2, paramInt1 + paramInt3 - 1, paramInt2);
/* 1011 */     paramGraphics.setColor(paramBoolean ? color2 : color3);
/* 1012 */     paramGraphics.drawLine(paramInt1 + 1, paramInt2 + paramInt4, paramInt1 + paramInt3, paramInt2 + paramInt4);
/* 1013 */     paramGraphics.drawLine(paramInt1 + paramInt3, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4 - 1);
/* 1014 */     paramGraphics.setColor(color1);
/*      */   }
/*      */ 
/*      */   
/*      */   void drawScrollbar(Graphics paramGraphics, Color paramColor, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean) {
/* 1019 */     Color color1 = paramGraphics.getColor();
/* 1020 */     double d = (paramInt2 - 2 * (paramInt1 - 1)) / Math.max(1, paramInt4 - paramInt3 + paramInt6);
/* 1021 */     int i = paramInt1 + (int)(d * (paramInt5 - paramInt3));
/* 1022 */     int j = (int)(d * paramInt6);
/* 1023 */     int k = paramInt1 - 4;
/* 1024 */     int[] arrayOfInt1 = new int[3];
/* 1025 */     int[] arrayOfInt2 = new int[3];
/*      */     
/* 1027 */     if (paramInt2 < 3 * k) {
/* 1028 */       i = j = 0;
/* 1029 */       if (paramInt2 < 2 * k + 2) {
/* 1030 */         k = (paramInt2 - 2) / 2;
/*      */       }
/* 1032 */     } else if (j < 7) {
/*      */       
/* 1034 */       i = Math.max(0, i - (7 - j >> 1));
/* 1035 */       j = 7;
/*      */     } 
/*      */     
/* 1038 */     int m = paramInt1 / 2;
/* 1039 */     int n = m - k / 2;
/* 1040 */     int i1 = m + k / 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1046 */     Color color2 = new Color((int)(paramColor.getRed() * 0.85D), (int)(paramColor.getGreen() * 0.85D), (int)(paramColor.getBlue() * 0.85D));
/*      */     
/* 1048 */     paramGraphics.setColor(color2);
/* 1049 */     if (paramBoolean) {
/* 1050 */       paramGraphics.fillRect(0, 0, paramInt2, paramInt1);
/*      */     } else {
/* 1052 */       paramGraphics.fillRect(0, 0, paramInt1, paramInt2);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1057 */     paramGraphics.setColor(paramColor);
/* 1058 */     if (i > 0) {
/* 1059 */       if (paramBoolean) {
/* 1060 */         paramGraphics.fillRect(i, 3, j, paramInt1 - 3);
/*      */       } else {
/* 1062 */         paramGraphics.fillRect(3, i, paramInt1 - 3, j);
/*      */       } 
/*      */     }
/*      */     
/* 1066 */     arrayOfInt1[0] = m; arrayOfInt2[0] = 2;
/* 1067 */     arrayOfInt1[1] = n; arrayOfInt2[1] = k;
/* 1068 */     arrayOfInt1[2] = i1; arrayOfInt2[2] = k;
/* 1069 */     if (paramBoolean) {
/* 1070 */       paramGraphics.fillPolygon(arrayOfInt2, arrayOfInt1, 3);
/*      */     } else {
/* 1072 */       paramGraphics.fillPolygon(arrayOfInt1, arrayOfInt2, 3);
/*      */     } 
/*      */     
/* 1075 */     arrayOfInt2[0] = paramInt2 - 2;
/* 1076 */     arrayOfInt2[1] = paramInt2 - k;
/* 1077 */     arrayOfInt2[2] = paramInt2 - k;
/* 1078 */     if (paramBoolean) {
/* 1079 */       paramGraphics.fillPolygon(arrayOfInt2, arrayOfInt1, 3);
/*      */     } else {
/* 1081 */       paramGraphics.fillPolygon(arrayOfInt1, arrayOfInt2, 3);
/*      */     } 
/*      */     
/* 1084 */     color2 = paramColor.brighter();
/*      */ 
/*      */     
/* 1087 */     paramGraphics.setColor(color2);
/*      */ 
/*      */     
/* 1090 */     if (paramBoolean) {
/* 1091 */       paramGraphics.drawLine(1, paramInt1, paramInt2 - 1, paramInt1);
/* 1092 */       paramGraphics.drawLine(paramInt2 - 1, 1, paramInt2 - 1, paramInt1);
/*      */ 
/*      */       
/* 1095 */       paramGraphics.drawLine(1, m, k, n);
/* 1096 */       paramGraphics.drawLine(paramInt2 - k, n, paramInt2 - k, i1);
/* 1097 */       paramGraphics.drawLine(paramInt2 - k, n, paramInt2 - 2, m);
/*      */     } else {
/*      */       
/* 1100 */       paramGraphics.drawLine(paramInt1, 1, paramInt1, paramInt2 - 1);
/* 1101 */       paramGraphics.drawLine(1, paramInt2 - 1, paramInt1, paramInt2 - 1);
/*      */ 
/*      */       
/* 1104 */       paramGraphics.drawLine(m, 1, n, k);
/* 1105 */       paramGraphics.drawLine(n, paramInt2 - k, i1, paramInt2 - k);
/* 1106 */       paramGraphics.drawLine(n, paramInt2 - k, m, paramInt2 - 2);
/*      */     } 
/*      */ 
/*      */     
/* 1110 */     if (i > 0) {
/* 1111 */       if (paramBoolean) {
/* 1112 */         paramGraphics.drawLine(i, 2, i + j, 2);
/* 1113 */         paramGraphics.drawLine(i, 2, i, paramInt1 - 3);
/*      */       } else {
/* 1115 */         paramGraphics.drawLine(2, i, 2, i + j);
/* 1116 */         paramGraphics.drawLine(2, i, paramInt1 - 3, i);
/*      */       } 
/*      */     }
/*      */     
/* 1120 */     Color color3 = paramColor.darker();
/*      */ 
/*      */     
/* 1123 */     paramGraphics.setColor(color3);
/*      */ 
/*      */     
/* 1126 */     if (paramBoolean) {
/* 1127 */       paramGraphics.drawLine(0, 0, 0, paramInt1);
/* 1128 */       paramGraphics.drawLine(0, 0, paramInt2 - 1, 0);
/*      */ 
/*      */       
/* 1131 */       paramGraphics.drawLine(k, n, k, i1);
/* 1132 */       paramGraphics.drawLine(k, i1, 1, m);
/* 1133 */       paramGraphics.drawLine(paramInt2 - 2, m, paramInt2 - k, i1);
/*      */     } else {
/*      */       
/* 1136 */       paramGraphics.drawLine(0, 0, paramInt1, 0);
/* 1137 */       paramGraphics.drawLine(0, 0, 0, paramInt2 - 1);
/*      */ 
/*      */       
/* 1140 */       paramGraphics.drawLine(n, k, i1, k);
/* 1141 */       paramGraphics.drawLine(i1, k, m, 1);
/* 1142 */       paramGraphics.drawLine(m, paramInt2 - 2, i1, paramInt2 - k);
/*      */     } 
/*      */ 
/*      */     
/* 1146 */     if (i > 0) {
/* 1147 */       if (paramBoolean) {
/* 1148 */         paramGraphics.drawLine(i + j, 2, i + j, paramInt1 - 2);
/* 1149 */         paramGraphics.drawLine(i, paramInt1 - 2, i + j, paramInt1 - 2);
/*      */       } else {
/* 1151 */         paramGraphics.drawLine(2, i + j, paramInt1 - 2, i + j);
/* 1152 */         paramGraphics.drawLine(paramInt1 - 2, i, paramInt1 - 2, i + j);
/*      */       } 
/*      */     }
/* 1155 */     paramGraphics.setColor(color1);
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
/*      */   public void createBuffers(int paramInt, BufferCapabilities paramBufferCapabilities) throws AWTException {
/* 1169 */     if (buffersLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 1170 */       buffersLog.fine("createBuffers(" + paramInt + ", " + paramBufferCapabilities + ")");
/*      */     }
/*      */     
/* 1173 */     this.backBufferCaps = paramBufferCapabilities;
/* 1174 */     this.backBuffer = this.graphicsConfig.createBackBuffer(this, paramInt, paramBufferCapabilities);
/* 1175 */     this.xBackBuffer = this.graphicsConfig.createBackBufferImage(this.target, this.backBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BufferCapabilities getBackBufferCaps() {
/* 1181 */     return this.backBufferCaps;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void flip(int paramInt1, int paramInt2, int paramInt3, int paramInt4, BufferCapabilities.FlipContents paramFlipContents) {
/* 1187 */     if (buffersLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 1188 */       buffersLog.fine("flip(" + paramFlipContents + ")");
/*      */     }
/* 1190 */     if (this.backBuffer == 0L) {
/* 1191 */       throw new IllegalStateException("Buffers have not been created");
/*      */     }
/* 1193 */     this.graphicsConfig.flip(this, this.target, this.xBackBuffer, paramInt1, paramInt2, paramInt3, paramInt4, paramFlipContents);
/*      */   }
/*      */ 
/*      */   
/*      */   public Image getBackBuffer() {
/* 1198 */     if (buffersLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 1199 */       buffersLog.fine("getBackBuffer()");
/*      */     }
/* 1201 */     if (this.backBuffer == 0L) {
/* 1202 */       throw new IllegalStateException("Buffers have not been created");
/*      */     }
/* 1204 */     return this.xBackBuffer;
/*      */   }
/*      */   
/*      */   public void destroyBuffers() {
/* 1208 */     if (buffersLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 1209 */       buffersLog.fine("destroyBuffers()");
/*      */     }
/* 1211 */     this.graphicsConfig.destroyBackBuffer(this.backBuffer);
/* 1212 */     this.backBuffer = 0L;
/* 1213 */     this.xBackBuffer = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void notifyTextComponentChange(boolean paramBoolean) {
/* 1219 */     Container container = AWTAccessor.getComponentAccessor().getParent(this.target);
/* 1220 */     while (container != null && !(container instanceof java.awt.Frame) && !(container instanceof java.awt.Dialog))
/*      */     {
/*      */       
/* 1223 */       container = AWTAccessor.getComponentAccessor().getParent(container);
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
/*      */   protected boolean isEventDisabled(XEvent paramXEvent) {
/* 1243 */     if (enableLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1244 */       enableLog.finest("Component is {1}, checking for disabled event {0}", new Object[] { paramXEvent, isEnabled() ? "enabled" : "disable" });
/*      */     }
/* 1246 */     if (!isEnabled()) {
/* 1247 */       switch (paramXEvent.get_type()) {
/*      */         case 2:
/*      */         case 3:
/*      */         case 4:
/*      */         case 5:
/*      */         case 6:
/*      */         case 7:
/*      */         case 8:
/* 1255 */           if (enableLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1256 */             enableLog.finer("Event {0} is disable", new Object[] { paramXEvent });
/*      */           }
/* 1258 */           return true;
/*      */       } 
/*      */     }
/* 1261 */     switch (paramXEvent.get_type()) {
/*      */       case 18:
/*      */       case 19:
/* 1264 */         return true;
/*      */     } 
/* 1266 */     return super.isEventDisabled(paramXEvent);
/*      */   }
/*      */   
/*      */   Color getPeerBackground() {
/* 1270 */     return this.background;
/*      */   }
/*      */   
/*      */   Color getPeerForeground() {
/* 1274 */     return this.foreground;
/*      */   }
/*      */   
/*      */   Font getPeerFont() {
/* 1278 */     return this.font;
/*      */   }
/*      */   
/*      */   Dimension getPeerSize() {
/* 1282 */     return new Dimension(this.width, this.height);
/*      */   }
/*      */   
/*      */   public void setBoundsOperation(int paramInt) {
/* 1286 */     synchronized (getStateLock()) {
/* 1287 */       if (this.boundsOperation == 3) {
/* 1288 */         this.boundsOperation = paramInt;
/* 1289 */       } else if (paramInt == 5) {
/* 1290 */         this.boundsOperation = 3;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   static String operationToString(int paramInt) {
/* 1296 */     switch (paramInt) {
/*      */       case 1:
/* 1298 */         return "SET_LOCATION";
/*      */       case 2:
/* 1300 */         return "SET_SIZE";
/*      */       case 4:
/* 1302 */         return "SET_CLIENT_SIZE";
/*      */     } 
/*      */     
/* 1305 */     return "SET_BOUNDS";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setZOrder(ComponentPeer paramComponentPeer) {
/* 1314 */     long l = (paramComponentPeer != null) ? ((XComponentPeer)paramComponentPeer).getWindow() : 0L;
/*      */     
/* 1316 */     XToolkit.awtLock();
/*      */     try {
/* 1318 */       XlibWrapper.SetZOrder(XToolkit.getDisplay(), getWindow(), l);
/*      */     } finally {
/* 1320 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void addTree(Collection<Long> paramCollection, Set<Long> paramSet, Container paramContainer) {
/* 1325 */     for (byte b = 0; b < paramContainer.getComponentCount(); b++) {
/* 1326 */       Component component = paramContainer.getComponent(b);
/* 1327 */       ComponentPeer componentPeer = component.getPeer();
/* 1328 */       if (componentPeer instanceof XComponentPeer) {
/* 1329 */         Long long_ = Long.valueOf(((XComponentPeer)componentPeer).getWindow());
/* 1330 */         if (!paramSet.contains(long_)) {
/* 1331 */           paramSet.add(long_);
/* 1332 */           paramCollection.add(long_);
/*      */         } 
/* 1334 */       } else if (component instanceof Container) {
/*      */ 
/*      */         
/* 1337 */         addTree(paramCollection, paramSet, (Container)component);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDropTarget(DropTarget paramDropTarget) {
/* 1345 */     Component component = this.target;
/* 1346 */     while (component != null && !(component instanceof Window)) {
/* 1347 */       component = component.getParent();
/*      */     }
/*      */     
/* 1350 */     if (component instanceof Window) {
/* 1351 */       XWindowPeer xWindowPeer = (XWindowPeer)component.getPeer();
/* 1352 */       if (xWindowPeer != null) {
/* 1353 */         xWindowPeer.addDropTarget();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void removeDropTarget(DropTarget paramDropTarget) {
/* 1359 */     Component component = this.target;
/* 1360 */     while (component != null && !(component instanceof Window)) {
/* 1361 */       component = component.getParent();
/*      */     }
/*      */     
/* 1364 */     if (component instanceof Window) {
/* 1365 */       XWindowPeer xWindowPeer = (XWindowPeer)component.getPeer();
/* 1366 */       if (xWindowPeer != null) {
/* 1367 */         xWindowPeer.removeDropTarget();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyShape(Region paramRegion) {
/* 1377 */     if (XlibUtil.isShapingSupported()) {
/* 1378 */       if (shapeLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1379 */         shapeLog.finer("*** INFO: Setting shape: PEER: " + this + "; WINDOW: " + 
/*      */             
/* 1381 */             getWindow() + "; TARGET: " + this.target + "; SHAPE: " + paramRegion);
/*      */       }
/*      */ 
/*      */       
/* 1385 */       XToolkit.awtLock();
/*      */       try {
/* 1387 */         if (paramRegion != null) {
/* 1388 */           XlibWrapper.SetRectangularShape(
/* 1389 */               XToolkit.getDisplay(), 
/* 1390 */               getWindow(), paramRegion
/* 1391 */               .getLoX(), paramRegion.getLoY(), paramRegion
/* 1392 */               .getHiX(), paramRegion.getHiY(), 
/* 1393 */               paramRegion.isRectangular() ? null : paramRegion);
/*      */         } else {
/*      */           
/* 1396 */           XlibWrapper.SetRectangularShape(
/* 1397 */               XToolkit.getDisplay(), 
/* 1398 */               getWindow(), 0, 0, 0, 0, null);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       finally {
/*      */         
/* 1405 */         XToolkit.awtUnlock();
/*      */       }
/*      */     
/* 1408 */     } else if (shapeLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1409 */       shapeLog.finer("*** WARNING: Shaping is NOT supported!");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean updateGraphicsData(GraphicsConfiguration paramGraphicsConfiguration) {
/* 1415 */     int i = -1, j = -1;
/*      */     
/* 1417 */     if (this.graphicsConfig != null) {
/* 1418 */       i = this.graphicsConfig.getVisual();
/*      */     }
/* 1420 */     if (paramGraphicsConfiguration != null && paramGraphicsConfiguration instanceof X11GraphicsConfig) {
/* 1421 */       j = ((X11GraphicsConfig)paramGraphicsConfiguration).getVisual();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1428 */     if (i != -1 && i != j) {
/* 1429 */       return true;
/*      */     }
/*      */     
/* 1432 */     initGraphicsConfiguration();
/* 1433 */     doValidateSurface();
/* 1434 */     return false;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XComponentPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */