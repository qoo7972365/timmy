/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.AWTKeyStroke;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.KeyEventPostProcessor;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.awt.dnd.DropTarget;
/*     */ import java.awt.dnd.DropTargetListener;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.InputEvent;
/*     */ import java.awt.event.InvocationEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.awt.event.WindowFocusListener;
/*     */ import java.security.AccessController;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TooManyListenersException;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.CausedFocusEvent;
/*     */ import sun.awt.ModalityEvent;
/*     */ import sun.awt.ModalityListener;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.WindowIDProvider;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ public class XEmbedCanvasPeer
/*     */   extends XCanvasPeer
/*     */   implements WindowFocusListener, KeyEventPostProcessor, ModalityListener, WindowIDProvider
/*     */ {
/*  42 */   private static final PlatformLogger xembedLog = PlatformLogger.getLogger("sun.awt.X11.xembed.XEmbedCanvasPeer");
/*     */   
/*     */   boolean applicationActive;
/*  45 */   XEmbedServer xembed = new XEmbedServer();
/*  46 */   Map<Long, AWTKeyStroke> accelerators = new HashMap<>();
/*  47 */   Map<AWTKeyStroke, Long> accel_lookup = new HashMap<>();
/*  48 */   Set<GrabbedKey> grabbed_keys = new HashSet<>();
/*  49 */   Object ACCEL_LOCK = this.accelerators;
/*  50 */   Object GRAB_LOCK = this.grabbed_keys;
/*     */ 
/*     */ 
/*     */   
/*     */   XEmbedCanvasPeer(XCreateWindowParams paramXCreateWindowParams) {
/*  55 */     super(paramXCreateWindowParams);
/*     */   }
/*     */   
/*     */   XEmbedCanvasPeer(Component paramComponent) {
/*  59 */     super(paramComponent);
/*     */   }
/*     */   
/*     */   protected void postInit(XCreateWindowParams paramXCreateWindowParams) {
/*  63 */     super.postInit(paramXCreateWindowParams);
/*     */     
/*  65 */     installActivateListener();
/*  66 */     installAcceleratorListener();
/*  67 */     installModalityListener();
/*     */ 
/*     */ 
/*     */     
/*  71 */     this.target.setFocusTraversalKeysEnabled(false);
/*     */   }
/*     */   
/*     */   protected void preInit(XCreateWindowParams paramXCreateWindowParams) {
/*  75 */     super.preInit(paramXCreateWindowParams);
/*     */     
/*  77 */     paramXCreateWindowParams.put((K)"event mask", 
/*  78 */         (V)Long.valueOf(2793599L));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void installModalityListener() {
/*  86 */     ((SunToolkit)Toolkit.getDefaultToolkit()).addModalityListener(this);
/*     */   }
/*     */   
/*     */   void deinstallModalityListener() {
/*  90 */     ((SunToolkit)Toolkit.getDefaultToolkit()).removeModalityListener(this);
/*     */   }
/*     */   
/*     */   void installAcceleratorListener() {
/*  94 */     KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(this);
/*     */   }
/*     */   
/*     */   void deinstallAcceleratorListener() {
/*  98 */     KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventPostProcessor(this);
/*     */   }
/*     */ 
/*     */   
/*     */   void installActivateListener() {
/* 103 */     Window window = getTopLevel(this.target);
/* 104 */     if (window != null) {
/* 105 */       window.addWindowFocusListener(this);
/* 106 */       this.applicationActive = window.isFocused();
/*     */     } 
/*     */   }
/*     */   
/*     */   void deinstallActivateListener() {
/* 111 */     Window window = getTopLevel(this.target);
/* 112 */     if (window != null) {
/* 113 */       window.removeWindowFocusListener(this);
/*     */     }
/*     */   }
/*     */   
/*     */   boolean isXEmbedActive() {
/* 118 */     return (this.xembed.handle != 0L);
/*     */   }
/*     */   
/*     */   boolean isApplicationActive() {
/* 122 */     return this.applicationActive;
/*     */   }
/*     */   
/*     */   void initDispatching() {
/* 126 */     if (xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 127 */       xembedLog.fine("Init embedding for " + Long.toHexString(this.xembed.handle));
/*     */     }
/* 129 */     XToolkit.awtLock();
/*     */     try {
/* 131 */       XToolkit.addEventDispatcher(this.xembed.handle, this.xembed);
/* 132 */       XlibWrapper.XSelectInput(XToolkit.getDisplay(), this.xembed.handle, 4325376L);
/*     */ 
/*     */       
/* 135 */       XDropTargetRegistry.getRegistry().registerXEmbedClient(getWindow(), this.xembed.handle);
/*     */     } finally {
/* 137 */       XToolkit.awtUnlock();
/*     */     } 
/* 139 */     this.xembed.processXEmbedInfo();
/*     */     
/* 141 */     notifyChildEmbedded();
/*     */   }
/*     */   
/*     */   void endDispatching() {
/* 145 */     if (xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 146 */       xembedLog.fine("End dispatching for " + Long.toHexString(this.xembed.handle));
/*     */     }
/* 148 */     XToolkit.awtLock();
/*     */     try {
/* 150 */       XDropTargetRegistry.getRegistry().unregisterXEmbedClient(getWindow(), this.xembed.handle);
/*     */       
/* 152 */       XToolkit.removeEventDispatcher(this.xembed.handle, this.xembed);
/*     */     } finally {
/* 154 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   void embedChild(long paramLong) {
/* 159 */     if (this.xembed.handle != 0L) {
/* 160 */       detachChild();
/*     */     }
/* 162 */     this.xembed.handle = paramLong;
/* 163 */     initDispatching();
/*     */   }
/*     */   
/*     */   void childDestroyed() {
/* 167 */     if (xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 168 */       xembedLog.fine("Child " + Long.toHexString(this.xembed.handle) + " has self-destroyed.");
/*     */     }
/* 170 */     endDispatching();
/* 171 */     this.xembed.handle = 0L;
/*     */   }
/*     */   
/*     */   public void handleEvent(AWTEvent paramAWTEvent) {
/* 175 */     super.handleEvent(paramAWTEvent);
/* 176 */     if (isXEmbedActive())
/* 177 */       switch (paramAWTEvent.getID()) {
/*     */         case 1004:
/* 179 */           canvasFocusGained((FocusEvent)paramAWTEvent);
/*     */           break;
/*     */         case 1005:
/* 182 */           canvasFocusLost((FocusEvent)paramAWTEvent);
/*     */           break;
/*     */         case 401:
/*     */         case 402:
/* 186 */           if (!((InputEvent)paramAWTEvent).isConsumed())
/* 187 */             forwardKeyEvent((KeyEvent)paramAWTEvent); 
/*     */           break;
/*     */       }  
/*     */   }
/*     */   public void dispatchEvent(XEvent paramXEvent) {
/*     */     XCreateWindowEvent xCreateWindowEvent;
/*     */     XDestroyWindowEvent xDestroyWindowEvent;
/*     */     XReparentEvent xReparentEvent;
/* 195 */     super.dispatchEvent(paramXEvent);
/* 196 */     switch (paramXEvent.get_type()) {
/*     */       case 16:
/* 198 */         xCreateWindowEvent = paramXEvent.get_xcreatewindow();
/* 199 */         if (xembedLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 200 */           xembedLog.finest("Message on embedder: " + xCreateWindowEvent);
/*     */         }
/* 202 */         if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 203 */           xembedLog.finer("Create notify for parent " + Long.toHexString(xCreateWindowEvent.get_parent()) + ", window " + 
/* 204 */               Long.toHexString(xCreateWindowEvent.get_window()));
/*     */         }
/* 206 */         embedChild(xCreateWindowEvent.get_window());
/*     */         break;
/*     */       case 17:
/* 209 */         xDestroyWindowEvent = paramXEvent.get_xdestroywindow();
/* 210 */         if (xembedLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 211 */           xembedLog.finest("Message on embedder: " + xDestroyWindowEvent);
/*     */         }
/* 213 */         if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 214 */           xembedLog.finer("Destroy notify for parent: " + xDestroyWindowEvent);
/*     */         }
/* 216 */         childDestroyed();
/*     */         break;
/*     */       case 21:
/* 219 */         xReparentEvent = paramXEvent.get_xreparent();
/* 220 */         if (xembedLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 221 */           xembedLog.finest("Message on embedder: " + xReparentEvent);
/*     */         }
/* 223 */         if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 224 */           xembedLog.finer("Reparent notify for parent " + Long.toHexString(xReparentEvent.get_parent()) + ", window " + 
/* 225 */               Long.toHexString(xReparentEvent.get_window()) + ", event " + 
/* 226 */               Long.toHexString(xReparentEvent.get_event()));
/*     */         }
/* 228 */         if (xReparentEvent.get_parent() == getWindow()) {
/*     */           
/* 230 */           embedChild(xReparentEvent.get_window());
/*     */           break;
/*     */         } 
/* 233 */         childDestroyed();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 240 */     if (isXEmbedActive()) {
/* 241 */       XToolkit.awtLock();
/*     */       try {
/* 243 */         long l = XlibWrapper.XAllocSizeHints();
/* 244 */         XSizeHints xSizeHints = new XSizeHints(l);
/* 245 */         XlibWrapper.XGetWMNormalHints(XToolkit.getDisplay(), this.xembed.handle, l, XlibWrapper.larg1);
/* 246 */         Dimension dimension = new Dimension(xSizeHints.get_width(), xSizeHints.get_height());
/* 247 */         XlibWrapper.XFree(l);
/* 248 */         return dimension;
/*     */       } finally {
/* 250 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } 
/* 253 */     return super.getPreferredSize();
/*     */   }
/*     */   
/*     */   public Dimension getMinimumSize() {
/* 257 */     if (isXEmbedActive()) {
/* 258 */       XToolkit.awtLock();
/*     */       try {
/* 260 */         long l = XlibWrapper.XAllocSizeHints();
/* 261 */         XSizeHints xSizeHints = new XSizeHints(l);
/* 262 */         XlibWrapper.XGetWMNormalHints(XToolkit.getDisplay(), this.xembed.handle, l, XlibWrapper.larg1);
/* 263 */         Dimension dimension = new Dimension(xSizeHints.get_min_width(), xSizeHints.get_min_height());
/* 264 */         XlibWrapper.XFree(l);
/* 265 */         return dimension;
/*     */       } finally {
/* 267 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } 
/* 270 */     return super.getMinimumSize();
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 274 */     if (isXEmbedActive()) {
/* 275 */       detachChild();
/*     */     }
/* 277 */     deinstallActivateListener();
/* 278 */     deinstallModalityListener();
/* 279 */     deinstallAcceleratorListener();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     super.dispose();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFocusable() {
/* 289 */     return true;
/*     */   }
/*     */   
/*     */   Window getTopLevel(Component paramComponent) {
/* 293 */     while (paramComponent != null && !(paramComponent instanceof Window)) {
/* 294 */       paramComponent = paramComponent.getParent();
/*     */     }
/* 296 */     return (Window)paramComponent;
/*     */   }
/*     */   
/*     */   Rectangle getClientBounds() {
/* 300 */     XToolkit.awtLock();
/*     */     try {
/* 302 */       XWindowAttributes xWindowAttributes = new XWindowAttributes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 321 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   void childResized() {
/* 326 */     if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 327 */       Rectangle rectangle = getClientBounds();
/* 328 */       xembedLog.finer("Child resized: " + rectangle);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     XToolkit.postEvent(XToolkit.targetToAppContext(this.target), new ComponentEvent(this.target, 101));
/*     */   }
/*     */   
/*     */   void focusNext() {
/* 340 */     if (isXEmbedActive()) {
/* 341 */       xembedLog.fine("Requesting focus for the next component after embedder");
/* 342 */       postEvent(new InvocationEvent(this.target, new Runnable() {
/*     */               public void run() {
/* 344 */                 KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent(XEmbedCanvasPeer.this.target);
/*     */               }
/*     */             }));
/*     */     } else {
/* 348 */       xembedLog.fine("XEmbed is not active - denying focus next");
/*     */     } 
/*     */   }
/*     */   
/*     */   void focusPrev() {
/* 353 */     if (isXEmbedActive()) {
/* 354 */       xembedLog.fine("Requesting focus for the next component after embedder");
/* 355 */       postEvent(new InvocationEvent(this.target, new Runnable() {
/*     */               public void run() {
/* 357 */                 KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent(XEmbedCanvasPeer.this.target);
/*     */               }
/*     */             }));
/*     */     } else {
/* 361 */       xembedLog.fine("XEmbed is not active - denying focus prev");
/*     */     } 
/*     */   }
/*     */   
/*     */   void requestXEmbedFocus() {
/* 366 */     if (isXEmbedActive()) {
/* 367 */       xembedLog.fine("Requesting focus for client");
/* 368 */       postEvent(new InvocationEvent(this.target, new Runnable() {
/*     */               public void run() {
/* 370 */                 XEmbedCanvasPeer.this.target.requestFocus();
/*     */               }
/*     */             }));
/*     */     } else {
/* 374 */       xembedLog.fine("XEmbed is not active - denying request focus");
/*     */     } 
/*     */   }
/*     */   
/*     */   void notifyChildEmbedded() {
/* 379 */     this.xembed.sendMessage(this.xembed.handle, 0, getWindow(), Math.min(this.xembed.version, 0L), 0L);
/* 380 */     if (isApplicationActive()) {
/* 381 */       xembedLog.fine("Sending WINDOW_ACTIVATE during initialization");
/* 382 */       this.xembed.sendMessage(this.xembed.handle, 1);
/* 383 */       if (hasFocus()) {
/* 384 */         xembedLog.fine("Sending FOCUS_GAINED during initialization");
/* 385 */         this.xembed.sendMessage(this.xembed.handle, 4, 0L, 0L, 0L);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void detachChild() {
/* 391 */     if (xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 392 */       xembedLog.fine("Detaching child " + Long.toHexString(this.xembed.handle));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 402 */     XToolkit.awtLock();
/*     */     try {
/* 404 */       XlibWrapper.XUnmapWindow(XToolkit.getDisplay(), this.xembed.handle);
/* 405 */       XlibWrapper.XReparentWindow(XToolkit.getDisplay(), this.xembed.handle, XToolkit.getDefaultRootWindow(), 0, 0);
/*     */     } finally {
/* 407 */       XToolkit.awtUnlock();
/*     */     } 
/* 409 */     endDispatching();
/* 410 */     this.xembed.handle = 0L;
/*     */   }
/*     */   
/*     */   public void windowGainedFocus(WindowEvent paramWindowEvent) {
/* 414 */     this.applicationActive = true;
/* 415 */     if (isXEmbedActive()) {
/* 416 */       xembedLog.fine("Sending WINDOW_ACTIVATE");
/* 417 */       this.xembed.sendMessage(this.xembed.handle, 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void windowLostFocus(WindowEvent paramWindowEvent) {
/* 422 */     this.applicationActive = false;
/* 423 */     if (isXEmbedActive()) {
/* 424 */       xembedLog.fine("Sending WINDOW_DEACTIVATE");
/* 425 */       this.xembed.sendMessage(this.xembed.handle, 2);
/*     */     } 
/*     */   }
/*     */   
/*     */   void canvasFocusGained(FocusEvent paramFocusEvent) {
/* 430 */     if (isXEmbedActive()) {
/* 431 */       xembedLog.fine("Forwarding FOCUS_GAINED");
/* 432 */       byte b = 0;
/* 433 */       if (paramFocusEvent instanceof CausedFocusEvent) {
/* 434 */         CausedFocusEvent causedFocusEvent = (CausedFocusEvent)paramFocusEvent;
/* 435 */         if (causedFocusEvent.getCause() == CausedFocusEvent.Cause.TRAVERSAL_FORWARD) {
/* 436 */           b = 1;
/* 437 */         } else if (causedFocusEvent.getCause() == CausedFocusEvent.Cause.TRAVERSAL_BACKWARD) {
/* 438 */           b = 2;
/*     */         } 
/*     */       } 
/* 441 */       this.xembed.sendMessage(this.xembed.handle, 4, b, 0L, 0L);
/*     */     } 
/*     */   }
/*     */   
/*     */   void canvasFocusLost(FocusEvent paramFocusEvent) {
/* 446 */     if (isXEmbedActive() && !paramFocusEvent.isTemporary()) {
/* 447 */       xembedLog.fine("Forwarding FOCUS_LOST");
/* 448 */       int i = 0;
/* 449 */       if (((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.awt.xembed.testing"))).booleanValue()) {
/* 450 */         Component component = paramFocusEvent.getOppositeComponent();
/*     */         try {
/* 452 */           i = Integer.parseInt(component.getName());
/* 453 */         } catch (NumberFormatException numberFormatException) {}
/*     */       } 
/*     */       
/* 456 */       this.xembed.sendMessage(this.xembed.handle, 5, i, 0L, 0L);
/*     */     } 
/*     */   }
/*     */   
/*     */   static byte[] getBData(KeyEvent paramKeyEvent) {
/* 461 */     return AWTAccessor.getAWTEventAccessor().getBData(paramKeyEvent);
/*     */   }
/*     */   
/*     */   void forwardKeyEvent(KeyEvent paramKeyEvent) {
/* 465 */     xembedLog.fine("Try to forward key event");
/* 466 */     byte[] arrayOfByte = getBData(paramKeyEvent);
/* 467 */     long l = Native.toData(arrayOfByte);
/* 468 */     if (l == 0L) {
/*     */       return;
/*     */     }
/*     */     try {
/* 472 */       XKeyEvent xKeyEvent = new XKeyEvent(l);
/* 473 */       xKeyEvent.set_window(this.xembed.handle);
/* 474 */       if (xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 475 */         xembedLog.fine("Forwarding native key event: " + xKeyEvent);
/*     */       }
/* 477 */       XToolkit.awtLock();
/*     */       try {
/* 479 */         XlibWrapper.XSendEvent(XToolkit.getDisplay(), this.xembed.handle, false, 0L, l);
/*     */       } finally {
/* 481 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } finally {
/* 484 */       XlibWrapper.unsafe.freeMemory(l);
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
/*     */   void grabKey(final long keysym, final long modifiers) {
/* 504 */     postEvent(new InvocationEvent(this.target, new Runnable() {
/*     */             public void run() {
/* 506 */               XEmbedCanvasPeer.GrabbedKey grabbedKey = new XEmbedCanvasPeer.GrabbedKey(keysym, modifiers);
/* 507 */               if (XEmbedCanvasPeer.xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 508 */                 XEmbedCanvasPeer.xembedLog.fine("Grabbing key: " + grabbedKey);
/*     */               }
/* 510 */               synchronized (XEmbedCanvasPeer.this.GRAB_LOCK) {
/* 511 */                 XEmbedCanvasPeer.this.grabbed_keys.add(grabbedKey);
/*     */               } 
/*     */             }
/*     */           }));
/*     */   }
/*     */   
/*     */   void ungrabKey(final long keysym, final long modifiers) {
/* 518 */     postEvent(new InvocationEvent(this.target, new Runnable() {
/*     */             public void run() {
/* 520 */               XEmbedCanvasPeer.GrabbedKey grabbedKey = new XEmbedCanvasPeer.GrabbedKey(keysym, modifiers);
/* 521 */               if (XEmbedCanvasPeer.xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 522 */                 XEmbedCanvasPeer.xembedLog.fine("UnGrabbing key: " + grabbedKey);
/*     */               }
/* 524 */               synchronized (XEmbedCanvasPeer.this.GRAB_LOCK) {
/* 525 */                 XEmbedCanvasPeer.this.grabbed_keys.remove(grabbedKey);
/*     */               } 
/*     */             }
/*     */           }));
/*     */   }
/*     */   
/*     */   void registerAccelerator(final long accel_id, final long keysym, final long modifiers) {
/* 532 */     postEvent(new InvocationEvent(this.target, new Runnable() {
/*     */             public void run() {
/* 534 */               AWTKeyStroke aWTKeyStroke = XEmbedCanvasPeer.this.xembed.getKeyStrokeForKeySym(keysym, modifiers);
/* 535 */               if (aWTKeyStroke != null) {
/* 536 */                 if (XEmbedCanvasPeer.xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 537 */                   XEmbedCanvasPeer.xembedLog.fine("Registering accelerator " + accel_id + " for " + aWTKeyStroke);
/*     */                 }
/* 539 */                 synchronized (XEmbedCanvasPeer.this.ACCEL_LOCK) {
/* 540 */                   XEmbedCanvasPeer.this.accelerators.put(Long.valueOf(accel_id), aWTKeyStroke);
/* 541 */                   XEmbedCanvasPeer.this.accel_lookup.put(aWTKeyStroke, Long.valueOf(accel_id));
/*     */                 } 
/*     */               } 
/* 544 */               XEmbedCanvasPeer.this.propogateRegisterAccelerator(aWTKeyStroke);
/*     */             }
/*     */           }));
/*     */   }
/*     */   
/*     */   void unregisterAccelerator(final long accel_id) {
/* 550 */     postEvent(new InvocationEvent(this.target, new Runnable() {
/*     */             public void run() {
/* 552 */               AWTKeyStroke aWTKeyStroke = null;
/* 553 */               synchronized (XEmbedCanvasPeer.this.ACCEL_LOCK) {
/* 554 */                 aWTKeyStroke = XEmbedCanvasPeer.this.accelerators.get(Long.valueOf(accel_id));
/* 555 */                 if (aWTKeyStroke != null) {
/* 556 */                   if (XEmbedCanvasPeer.xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 557 */                     XEmbedCanvasPeer.xembedLog.fine("Unregistering accelerator: " + accel_id);
/*     */                   }
/* 559 */                   XEmbedCanvasPeer.this.accelerators.remove(Long.valueOf(accel_id));
/* 560 */                   XEmbedCanvasPeer.this.accel_lookup.remove(aWTKeyStroke);
/*     */                 } 
/*     */               } 
/* 563 */               XEmbedCanvasPeer.this.propogateUnRegisterAccelerator(aWTKeyStroke);
/*     */             }
/*     */           }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void propogateRegisterAccelerator(AWTKeyStroke paramAWTKeyStroke) {
/* 571 */     XWindowPeer xWindowPeer = getToplevelXWindow();
/* 572 */     if (xWindowPeer != null && xWindowPeer instanceof XEmbeddedFramePeer) {
/* 573 */       XEmbeddedFramePeer xEmbeddedFramePeer = (XEmbeddedFramePeer)xWindowPeer;
/* 574 */       xEmbeddedFramePeer.registerAccelerator(paramAWTKeyStroke);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void propogateUnRegisterAccelerator(AWTKeyStroke paramAWTKeyStroke) {
/* 581 */     XWindowPeer xWindowPeer = getToplevelXWindow();
/* 582 */     if (xWindowPeer != null && xWindowPeer instanceof XEmbeddedFramePeer) {
/* 583 */       XEmbeddedFramePeer xEmbeddedFramePeer = (XEmbeddedFramePeer)xWindowPeer;
/* 584 */       xEmbeddedFramePeer.unregisterAccelerator(paramAWTKeyStroke);
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
/*     */   public boolean postProcessKeyEvent(KeyEvent paramKeyEvent) {
/* 597 */     XWindowPeer xWindowPeer = getToplevelXWindow();
/* 598 */     if (xWindowPeer == null || !((Window)xWindowPeer.getTarget()).isFocused() || this.target.isFocusOwner()) {
/* 599 */       return false;
/*     */     }
/*     */     
/* 602 */     boolean bool = false;
/*     */     
/* 604 */     if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 605 */       xembedLog.finer("Post-processing event " + paramKeyEvent);
/*     */     }
/*     */ 
/*     */     
/* 609 */     AWTKeyStroke aWTKeyStroke = AWTKeyStroke.getAWTKeyStrokeForEvent(paramKeyEvent);
/* 610 */     long l = 0L;
/* 611 */     boolean bool1 = false;
/* 612 */     synchronized (this.ACCEL_LOCK) {
/* 613 */       bool1 = this.accel_lookup.containsKey(aWTKeyStroke);
/* 614 */       if (bool1) {
/* 615 */         l = ((Long)this.accel_lookup.get(aWTKeyStroke)).longValue();
/*     */       }
/*     */     } 
/* 618 */     if (bool1) {
/* 619 */       if (xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 620 */         xembedLog.fine("Activating accelerator " + l);
/*     */       }
/* 622 */       this.xembed.sendMessage(this.xembed.handle, 14, l, 0L, 0L);
/* 623 */       bool = true;
/*     */     } 
/*     */ 
/*     */     
/* 627 */     bool1 = false;
/* 628 */     GrabbedKey grabbedKey = new GrabbedKey(paramKeyEvent);
/* 629 */     synchronized (this.GRAB_LOCK) {
/* 630 */       bool1 = this.grabbed_keys.contains(grabbedKey);
/*     */     } 
/* 632 */     if (bool1) {
/* 633 */       if (xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 634 */         xembedLog.fine("Forwarding grabbed key " + paramKeyEvent);
/*     */       }
/* 636 */       forwardKeyEvent(paramKeyEvent);
/* 637 */       bool = true;
/*     */     } 
/*     */     
/* 640 */     return bool;
/*     */   }
/*     */   
/*     */   public void modalityPushed(ModalityEvent paramModalityEvent) {
/* 644 */     this.xembed.sendMessage(this.xembed.handle, 10);
/*     */   }
/*     */   
/*     */   public void modalityPopped(ModalityEvent paramModalityEvent) {
/* 648 */     this.xembed.sendMessage(this.xembed.handle, 11);
/*     */   }
/*     */   
/*     */   public void handleClientMessage(XEvent paramXEvent) {
/* 652 */     super.handleClientMessage(paramXEvent);
/* 653 */     XClientMessageEvent xClientMessageEvent = paramXEvent.get_xclient();
/* 654 */     if (xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 655 */       xembedLog.finer("Client message to embedder: " + xClientMessageEvent);
/*     */     }
/* 657 */     if (xClientMessageEvent.get_message_type() == XEmbedServer.XEmbed.getAtom() && 
/* 658 */       xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 659 */       xembedLog.fine(XEmbedServer.XEmbedMessageToString(xClientMessageEvent));
/*     */     }
/*     */     
/* 662 */     if (isXEmbedActive()) {
/* 663 */       switch ((int)xClientMessageEvent.get_data(1)) {
/*     */         case 3:
/* 665 */           requestXEmbedFocus();
/*     */           break;
/*     */         case 6:
/* 668 */           focusNext();
/*     */           break;
/*     */         case 7:
/* 671 */           focusPrev();
/*     */           break;
/*     */         case 12:
/* 674 */           registerAccelerator(xClientMessageEvent.get_data(2), xClientMessageEvent.get_data(3), xClientMessageEvent.get_data(4));
/*     */           break;
/*     */         case 13:
/* 677 */           unregisterAccelerator(xClientMessageEvent.get_data(2));
/*     */           break;
/*     */         case 108:
/* 680 */           grabKey(xClientMessageEvent.get_data(3), xClientMessageEvent.get_data(4));
/*     */           break;
/*     */         case 109:
/* 683 */           ungrabKey(xClientMessageEvent.get_data(3), xClientMessageEvent.get_data(4));
/*     */           break;
/*     */       } 
/*     */     } else {
/* 687 */       xembedLog.finer("But XEmbed is not Active!");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class XEmbedDropTarget
/*     */     extends DropTarget
/*     */   {
/*     */     private XEmbedDropTarget() {}
/*     */ 
/*     */     
/*     */     public void addDropTargetListener(DropTargetListener param1DropTargetListener) throws TooManyListenersException {
/* 699 */       throw new TooManyListenersException();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXEmbedDropTarget() {
/* 705 */     Runnable runnable = new Runnable() {
/*     */         public void run() {
/* 707 */           XEmbedCanvasPeer.this.target.setDropTarget(new XEmbedCanvasPeer.XEmbedDropTarget());
/*     */         }
/*     */       };
/* 710 */     SunToolkit.executeOnEventHandlerThread(this.target, runnable);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeXEmbedDropTarget() {
/* 715 */     Runnable runnable = new Runnable() {
/*     */         public void run() {
/* 717 */           if (XEmbedCanvasPeer.this.target.getDropTarget() instanceof XEmbedCanvasPeer.XEmbedDropTarget) {
/* 718 */             XEmbedCanvasPeer.this.target.setDropTarget(null);
/*     */           }
/*     */         }
/*     */       };
/* 722 */     SunToolkit.executeOnEventHandlerThread(this.target, runnable);
/*     */   }
/*     */   
/*     */   public boolean processXEmbedDnDEvent(long paramLong, int paramInt) {
/* 726 */     if (xembedLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 727 */       xembedLog.finest("     Drop target=" + this.target.getDropTarget());
/*     */     }
/* 729 */     if (this.target.getDropTarget() instanceof XEmbedDropTarget) {
/* 730 */       AppContext appContext = XToolkit.targetToAppContext(getTarget());
/*     */       
/* 732 */       XDropTargetContextPeer xDropTargetContextPeer = XDropTargetContextPeer.getPeer(appContext);
/* 733 */       xDropTargetContextPeer.forwardEventToEmbedded(this.xembed.handle, paramLong, paramInt);
/* 734 */       return true;
/*     */     } 
/* 736 */     return false;
/*     */   }
/*     */   
/*     */   XEmbedCanvasPeer() {}
/*     */   
/*     */   class XEmbedServer extends XEmbedHelper implements XEventDispatcher { long handle;
/*     */     long version;
/*     */     long flags;
/*     */     
/*     */     boolean processXEmbedInfo() {
/* 746 */       long l = Native.allocateLongArray(2);
/*     */       try {
/* 748 */         if (!XEmbedInfo.getAtomData(this.handle, l, 2)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 753 */           XEmbedCanvasPeer.xembedLog.finer("Unable to get XEMBED_INFO atom data");
/* 754 */           return false;
/*     */         } 
/* 756 */         this.version = Native.getCard32(l, 0);
/* 757 */         this.flags = Native.getCard32(l, 1);
/* 758 */         boolean bool1 = ((this.flags & 0x1L) != 0L) ? true : false;
/* 759 */         boolean bool2 = (XlibUtil.getWindowMapState(this.handle) != 0) ? true : false;
/* 760 */         if (bool1 != bool2) {
/* 761 */           if (XEmbedCanvasPeer.xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 762 */             XEmbedCanvasPeer.xembedLog.finer("Mapping state of the client has changed, old state: " + bool2 + ", new state: " + bool1);
/*     */           }
/* 764 */           if (bool1) {
/* 765 */             XToolkit.awtLock();
/*     */             try {
/* 767 */               XlibWrapper.XMapWindow(XToolkit.getDisplay(), this.handle);
/*     */             } finally {
/* 769 */               XToolkit.awtUnlock();
/*     */             } 
/*     */           } else {
/* 772 */             XToolkit.awtLock();
/*     */             try {
/* 774 */               XlibWrapper.XUnmapWindow(XToolkit.getDisplay(), this.handle);
/*     */             } finally {
/* 776 */               XToolkit.awtUnlock();
/*     */             }
/*     */           
/*     */           } 
/* 780 */         } else if (XEmbedCanvasPeer.xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 781 */           XEmbedCanvasPeer.xembedLog.finer("Mapping state didn't change, mapped: " + bool2);
/*     */         } 
/*     */         
/* 784 */         return true;
/*     */       } finally {
/* 786 */         XlibWrapper.unsafe.freeMemory(l);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void handlePropertyNotify(XEvent param1XEvent) {
/* 791 */       if (XEmbedCanvasPeer.this.isXEmbedActive()) {
/* 792 */         XPropertyEvent xPropertyEvent = param1XEvent.get_xproperty();
/* 793 */         if (XEmbedCanvasPeer.xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 794 */           XEmbedCanvasPeer.xembedLog.finer("Property change on client: " + xPropertyEvent);
/*     */         }
/* 796 */         if (xPropertyEvent.get_atom() == 40L) {
/* 797 */           XEmbedCanvasPeer.this.childResized();
/* 798 */         } else if (xPropertyEvent.get_atom() == XEmbedInfo.getAtom()) {
/* 799 */           processXEmbedInfo();
/* 800 */         } else if (xPropertyEvent.get_atom() == XDnDConstants.XA_XdndAware
/* 801 */           .getAtom()) {
/* 802 */           XDropTargetRegistry.getRegistry().unregisterXEmbedClient(XEmbedCanvasPeer.this.getWindow(), XEmbedCanvasPeer.this.xembed.handle);
/*     */           
/* 804 */           if (xPropertyEvent.get_state() == 0) {
/* 805 */             XDropTargetRegistry.getRegistry().registerXEmbedClient(XEmbedCanvasPeer.this.getWindow(), XEmbedCanvasPeer.this.xembed.handle);
/*     */           }
/*     */         } 
/*     */       } else {
/*     */         
/* 810 */         XEmbedCanvasPeer.xembedLog.finer("XEmbed is not active");
/*     */       } 
/*     */     }
/*     */     void handleConfigureNotify(XEvent param1XEvent) {
/* 814 */       if (XEmbedCanvasPeer.this.isXEmbedActive()) {
/* 815 */         XConfigureEvent xConfigureEvent = param1XEvent.get_xconfigure();
/* 816 */         if (XEmbedCanvasPeer.xembedLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 817 */           XEmbedCanvasPeer.xembedLog.finer("Bounds change on client: " + xConfigureEvent);
/*     */         }
/* 819 */         if (param1XEvent.get_xany().get_window() == this.handle)
/* 820 */           XEmbedCanvasPeer.this.childResized(); 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void dispatchEvent(XEvent param1XEvent) {
/* 825 */       int i = param1XEvent.get_type();
/* 826 */       switch (i) {
/*     */         case 28:
/* 828 */           handlePropertyNotify(param1XEvent);
/*     */           break;
/*     */         case 22:
/* 831 */           handleConfigureNotify(param1XEvent);
/*     */           break;
/*     */         case 33:
/* 834 */           XEmbedCanvasPeer.this.handleClientMessage(param1XEvent);
/*     */           break;
/*     */       } 
/*     */     } }
/*     */   
/*     */   static class GrabbedKey {
/*     */     long keysym;
/*     */     long modifiers;
/*     */     
/*     */     GrabbedKey(long param1Long1, long param1Long2) {
/* 844 */       this.keysym = param1Long1;
/* 845 */       this.modifiers = param1Long2;
/*     */     }
/*     */     
/*     */     GrabbedKey(KeyEvent param1KeyEvent) {
/* 849 */       init(param1KeyEvent);
/*     */     }
/*     */     
/*     */     private void init(KeyEvent param1KeyEvent) {
/* 853 */       byte[] arrayOfByte = XEmbedCanvasPeer.getBData(param1KeyEvent);
/* 854 */       long l = Native.toData(arrayOfByte);
/* 855 */       if (l == 0L) {
/*     */         return;
/*     */       }
/*     */       try {
/* 859 */         XToolkit.awtLock();
/*     */         try {
/* 861 */           this.keysym = XWindow.getKeySymForAWTKeyCode(param1KeyEvent.getKeyCode());
/*     */         } finally {
/* 863 */           XToolkit.awtUnlock();
/*     */         } 
/* 865 */         XKeyEvent xKeyEvent = new XKeyEvent(l);
/*     */ 
/*     */         
/* 868 */         this.modifiers = (xKeyEvent.get_state() & 0x7);
/* 869 */         if (XEmbedCanvasPeer.xembedLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 870 */           XEmbedCanvasPeer.xembedLog.finest("Mapped " + param1KeyEvent + " to " + this);
/*     */         }
/*     */       } finally {
/* 873 */         XlibWrapper.unsafe.freeMemory(l);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 878 */       return (int)this.keysym & 0xFFFFFFFF;
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 882 */       if (!(param1Object instanceof GrabbedKey)) {
/* 883 */         return false;
/*     */       }
/* 885 */       GrabbedKey grabbedKey = (GrabbedKey)param1Object;
/* 886 */       return (this.keysym == grabbedKey.keysym && this.modifiers == grabbedKey.modifiers);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 890 */       return "Key combination[keysym=" + this.keysym + ", mods=" + this.modifiers + "]";
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XEmbedCanvasPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */