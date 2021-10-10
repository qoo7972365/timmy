/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.MenuBar;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Window;
/*     */ import java.awt.peer.FramePeer;
/*     */ import sun.awt.AWTAccessor;
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
/*     */ class XFramePeer
/*     */   extends XDecoratedPeer
/*     */   implements FramePeer
/*     */ {
/*  41 */   private static PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XFramePeer");
/*  42 */   private static PlatformLogger stateLog = PlatformLogger.getLogger("sun.awt.X11.states");
/*  43 */   private static PlatformLogger insLog = PlatformLogger.getLogger("sun.awt.X11.insets.XFramePeer");
/*     */   
/*     */   XMenuBarPeer menubarPeer;
/*     */   
/*     */   MenuBar menubar;
/*     */   int state;
/*     */   private Boolean undecorated;
/*     */   private static final int MENUBAR_HEIGHT_IF_NO_MENUBAR = 0;
/*  51 */   private int lastAppliedMenubarHeight = 0; static final int CROSSHAIR_INSET = 5; static final int BUTTON_Y = 6; static final int BUTTON_W = 17; static final int BUTTON_H = 17; static final int SYS_MENU_X = 6; static final int SYS_MENU_CONTAINED_X = 11; static final int SYS_MENU_CONTAINED_Y = 13; static final int SYS_MENU_CONTAINED_W = 8; static final int SYS_MENU_CONTAINED_H = 3; static final int MAXIMIZE_X_DIFF = 22; static final int MAXIMIZE_CONTAINED_X_DIFF = 17; static final int MAXIMIZE_CONTAINED_Y = 11; static final int MAXIMIZE_CONTAINED_W = 8;
/*     */   
/*     */   XFramePeer(Frame paramFrame) {
/*  54 */     super(paramFrame);
/*     */   }
/*     */   static final int MAXIMIZE_CONTAINED_H = 8; static final int MINIMIZE_X_DIFF = 39; static final int MINIMIZE_CONTAINED_X_DIFF = 32; static final int MINIMIZE_CONTAINED_Y = 13; static final int MINIMIZE_CONTAINED_W = 3; static final int MINIMIZE_CONTAINED_H = 3; static final int TITLE_X = 23; static final int TITLE_W_DIFF = 60; static final int TITLE_MID_Y = 14; static final int MENUBAR_X = 6; static final int MENUBAR_Y = 23; static final int HORIZ_RESIZE_INSET = 22; static final int VERT_RESIZE_INSET = 22;
/*     */   XFramePeer(XCreateWindowParams paramXCreateWindowParams) {
/*  58 */     super(paramXCreateWindowParams);
/*     */   }
/*     */   
/*     */   void preInit(XCreateWindowParams paramXCreateWindowParams) {
/*  62 */     super.preInit(paramXCreateWindowParams);
/*  63 */     Frame frame = (Frame)this.target;
/*     */     
/*  65 */     this.winAttr.initialState = frame.getExtendedState();
/*  66 */     this.state = 0;
/*  67 */     this.undecorated = Boolean.valueOf(frame.isUndecorated());
/*  68 */     this.winAttr.nativeDecor = !frame.isUndecorated();
/*  69 */     if (this.winAttr.nativeDecor) {
/*  70 */       this.winAttr.decorations = XWindowAttributesData.AWT_DECOR_ALL;
/*     */     } else {
/*  72 */       this.winAttr.decorations = XWindowAttributesData.AWT_DECOR_NONE;
/*     */     } 
/*  74 */     this.winAttr.functions = 1;
/*  75 */     this.winAttr.isResizable = true;
/*  76 */     this.winAttr.title = frame.getTitle();
/*  77 */     this.winAttr.initialResizability = frame.isResizable();
/*  78 */     if (log.isLoggable(PlatformLogger.Level.FINE))
/*  79 */       log.fine("Frame''s initial attributes: decor {0}, resizable {1}, undecorated {2}, initial state {3}", new Object[] {
/*  80 */             Integer.valueOf(this.winAttr.decorations), Boolean.valueOf(this.winAttr.initialResizability), 
/*  81 */             Boolean.valueOf(!this.winAttr.nativeDecor), Integer.valueOf(this.winAttr.initialState)
/*     */           }); 
/*     */   }
/*     */   
/*     */   void postInit(XCreateWindowParams paramXCreateWindowParams) {
/*  86 */     super.postInit(paramXCreateWindowParams);
/*  87 */     setupState(true);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isTargetUndecorated() {
/*  92 */     if (this.undecorated != null) {
/*  93 */       return this.undecorated.booleanValue();
/*     */     }
/*  95 */     return ((Frame)this.target).isUndecorated();
/*     */   }
/*     */ 
/*     */   
/*     */   void setupState(boolean paramBoolean) {
/* 100 */     if (paramBoolean) {
/* 101 */       this.state = this.winAttr.initialState;
/*     */     }
/* 103 */     if ((this.state & 0x1) != 0) {
/* 104 */       setInitialState(3);
/*     */     } else {
/* 106 */       setInitialState(1);
/*     */     } 
/* 108 */     setExtendedState(this.state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMenuBar(MenuBar paramMenuBar) {
/* 113 */     XToolkit.awtLock();
/*     */     try {
/* 115 */       synchronized (getStateLock()) {
/* 116 */         if (paramMenuBar == this.menubar)
/* 117 */           return;  if (paramMenuBar == null) {
/* 118 */           if (this.menubar != null) {
/* 119 */             this.menubarPeer.xSetVisible(false);
/* 120 */             this.menubar = null;
/* 121 */             this.menubarPeer.dispose();
/* 122 */             this.menubarPeer = null;
/*     */           } 
/*     */         } else {
/* 125 */           this.menubar = paramMenuBar;
/* 126 */           this.menubarPeer = (XMenuBarPeer)paramMenuBar.getPeer();
/* 127 */           if (this.menubarPeer != null) {
/* 128 */             this.menubarPeer.init((Frame)this.target);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 133 */       XToolkit.awtUnlock();
/*     */     } 
/*     */     
/* 136 */     reshapeMenubarPeer();
/*     */   }
/*     */   
/*     */   XMenuBarPeer getMenubarPeer() {
/* 140 */     return this.menubarPeer;
/*     */   }
/*     */   
/*     */   int getMenuBarHeight() {
/* 144 */     if (this.menubarPeer != null) {
/* 145 */       return this.menubarPeer.getDesiredHeight();
/*     */     }
/* 147 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   void updateChildrenSizes() {
/* 152 */     super.updateChildrenSizes();
/* 153 */     int i = getMenuBarHeight();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     XToolkit.awtLock();
/*     */     try {
/* 161 */       synchronized (getStateLock()) {
/* 162 */         int j = (this.dimensions.getClientSize()).width;
/* 163 */         if (this.menubarPeer != null) {
/* 164 */           this.menubarPeer.reshape(0, 0, j, i);
/*     */         }
/*     */       } 
/*     */     } finally {
/* 168 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void reshapeMenubarPeer() {
/* 178 */     XToolkit.executeOnEventHandlerThread(this.target, new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 182 */             XFramePeer.this.updateChildrenSizes();
/* 183 */             boolean bool = false;
/*     */             
/* 185 */             int i = XFramePeer.this.getMenuBarHeight();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 191 */             synchronized (XFramePeer.this.getStateLock()) {
/* 192 */               if (i != XFramePeer.this.lastAppliedMenubarHeight) {
/* 193 */                 XFramePeer.this.lastAppliedMenubarHeight = i;
/* 194 */                 bool = true;
/*     */               } 
/*     */             } 
/* 197 */             if (bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 203 */               XFramePeer.this.target.invalidate();
/* 204 */               XFramePeer.this.target.validate();
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaximizedBounds(Rectangle paramRectangle) {
/* 212 */     if (insLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 213 */       insLog.fine("Setting maximized bounds to " + paramRectangle);
/*     */     }
/* 215 */     if (paramRectangle == null)
/* 216 */       return;  this.maxBounds = new Rectangle(paramRectangle);
/* 217 */     XToolkit.awtLock();
/*     */     try {
/* 219 */       XSizeHints xSizeHints = getHints();
/* 220 */       xSizeHints.set_flags(xSizeHints.get_flags() | 0x20L);
/* 221 */       if (paramRectangle.width != Integer.MAX_VALUE) {
/* 222 */         xSizeHints.set_max_width(paramRectangle.width);
/*     */       } else {
/* 224 */         xSizeHints.set_max_width((int)XlibWrapper.DisplayWidth(XToolkit.getDisplay(), XlibWrapper.DefaultScreen(XToolkit.getDisplay())));
/*     */       } 
/* 226 */       if (paramRectangle.height != Integer.MAX_VALUE) {
/* 227 */         xSizeHints.set_max_height(paramRectangle.height);
/*     */       } else {
/* 229 */         xSizeHints.set_max_height((int)XlibWrapper.DisplayHeight(XToolkit.getDisplay(), XlibWrapper.DefaultScreen(XToolkit.getDisplay())));
/*     */       } 
/* 231 */       if (insLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 232 */         insLog.finer("Setting hints, flags " + XlibWrapper.hintsToString(xSizeHints.get_flags()));
/*     */       }
/* 234 */       XlibWrapper.XSetWMNormalHints(XToolkit.getDisplay(), this.window, xSizeHints.pData);
/*     */     } finally {
/* 236 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getState() {
/* 241 */     synchronized (getStateLock()) {
/* 242 */       return this.state;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setState(int paramInt) {
/* 247 */     synchronized (getStateLock()) {
/* 248 */       if (!isShowing()) {
/* 249 */         stateLog.finer("Frame is not showing");
/* 250 */         this.state = paramInt;
/*     */         return;
/*     */       } 
/*     */     } 
/* 254 */     changeState(paramInt);
/*     */   }
/*     */   
/*     */   void changeState(int paramInt) {
/* 258 */     int i = this.state ^ paramInt;
/* 259 */     int j = i & 0x1;
/* 260 */     boolean bool = ((paramInt & 0x1) != 0) ? true : false;
/* 261 */     if (stateLog.isLoggable(PlatformLogger.Level.FINER))
/* 262 */       stateLog.finer("Changing state, old state {0}, new state {1}(iconic {2})", new Object[] {
/* 263 */             Integer.valueOf(this.state), Integer.valueOf(paramInt), Boolean.valueOf(bool)
/*     */           }); 
/* 265 */     if (j != 0 && bool) {
/* 266 */       if (stateLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 267 */         stateLog.finer("Iconifying shell " + getShell() + ", this " + this + ", screen " + getScreenNumber());
/*     */       }
/* 269 */       XToolkit.awtLock();
/*     */       try {
/* 271 */         int k = XlibWrapper.XIconifyWindow(XToolkit.getDisplay(), getShell(), getScreenNumber());
/* 272 */         if (stateLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 273 */           stateLog.finer("XIconifyWindow returned " + k);
/*     */         }
/*     */       } finally {
/*     */         
/* 277 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } 
/* 280 */     if ((i & 0xFFFFFFFE) != 0) {
/* 281 */       setExtendedState(paramInt);
/*     */     }
/* 283 */     if (j != 0 && !bool) {
/* 284 */       if (stateLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 285 */         stateLog.finer("DeIconifying " + this);
/*     */       }
/*     */       
/* 288 */       XNETProtocol xNETProtocol = XWM.getWM().getNETProtocol();
/* 289 */       if (xNETProtocol != null) {
/* 290 */         xNETProtocol.setActiveWindow(this);
/*     */       }
/* 292 */       xSetVisible(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   void setExtendedState(int paramInt) {
/* 297 */     XWM.getWM().setExtendedState(this, paramInt);
/*     */   }
/*     */   
/*     */   public void handlePropertyNotify(XEvent paramXEvent) {
/* 301 */     super.handlePropertyNotify(paramXEvent);
/* 302 */     XPropertyEvent xPropertyEvent = paramXEvent.get_xproperty();
/*     */     
/* 304 */     if (log.isLoggable(PlatformLogger.Level.FINER)) {
/* 305 */       log.finer("Property change {0}", new Object[] { xPropertyEvent });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 311 */     if (!XWM.getWM().isStateChange(this, xPropertyEvent)) {
/* 312 */       stateLog.finer("either not a state atom or state has not been changed");
/*     */       
/*     */       return;
/*     */     } 
/* 316 */     int i = XWM.getWM().getState(this);
/* 317 */     int j = this.state ^ i;
/* 318 */     if (j == 0) {
/* 319 */       if (stateLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 320 */         stateLog.finer("State is the same: " + this.state);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 325 */     int k = this.state;
/* 326 */     this.state = i;
/*     */ 
/*     */     
/* 329 */     AWTAccessor.getFrameAccessor().setExtendedState((Frame)this.target, this.state);
/*     */     
/* 331 */     if ((j & 0x1) != 0) {
/* 332 */       if ((this.state & 0x1) != 0) {
/* 333 */         stateLog.finer("Iconified");
/* 334 */         handleIconify();
/*     */       } else {
/* 336 */         stateLog.finer("DeIconified");
/* 337 */         this.content.purgeIconifiedExposeEvents();
/* 338 */         handleDeiconify();
/*     */       } 
/*     */     }
/* 341 */     handleStateChange(k, this.state);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleStateChange(int paramInt1, int paramInt2) {
/* 347 */     super.handleStateChange(paramInt1, paramInt2);
/* 348 */     for (ToplevelStateListener toplevelStateListener : this.toplevelStateListeners) {
/* 349 */       toplevelStateListener.stateChangedJava(paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setVisible(boolean paramBoolean) {
/* 354 */     if (paramBoolean) {
/* 355 */       setupState(false);
/*     */     }
/* 357 */     else if ((this.state & 0x6) != 0) {
/* 358 */       XWM.getWM().setExtendedState(this, this.state & 0xFFFFFFF9);
/*     */     } 
/*     */     
/* 361 */     super.setVisible(paramBoolean);
/* 362 */     if (paramBoolean && this.maxBounds != null) {
/* 363 */       setMaximizedBounds(this.maxBounds);
/*     */     }
/*     */   }
/*     */   
/*     */   void setInitialState(int paramInt) {
/* 368 */     XToolkit.awtLock();
/*     */     try {
/* 370 */       XWMHints xWMHints = getWMHints();
/* 371 */       xWMHints.set_flags(0x2L | xWMHints.get_flags());
/* 372 */       xWMHints.set_initial_state(paramInt);
/* 373 */       if (stateLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 374 */         stateLog.fine("Setting initial WM state on " + this + " to " + paramInt);
/*     */       }
/* 376 */       XlibWrapper.XSetWMHints(XToolkit.getDisplay(), getWindow(), xWMHints.pData);
/*     */     } finally {
/*     */       
/* 379 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 384 */     if (this.menubarPeer != null) {
/* 385 */       this.menubarPeer.dispose();
/*     */     }
/* 387 */     super.dispose();
/*     */   }
/*     */   
/*     */   boolean isMaximized() {
/* 391 */     return ((this.state & 0x6) != 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void print(Graphics paramGraphics) {
/* 439 */     super.print(paramGraphics);
/*     */     
/* 441 */     Frame frame = (Frame)this.target;
/* 442 */     Insets insets = frame.getInsets();
/* 443 */     Dimension dimension = frame.getSize();
/*     */     
/* 445 */     Color color1 = frame.getBackground();
/* 446 */     Color color2 = frame.getForeground();
/* 447 */     Color color3 = color1.brighter();
/* 448 */     Color color4 = color1.darker();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 454 */     if (hasDecorations(XWindowAttributesData.AWT_DECOR_BORDER)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 459 */       if (color3.equals(Color.white)) {
/* 460 */         paramGraphics.setColor(new Color(230, 230, 230));
/*     */       } else {
/*     */         
/* 463 */         paramGraphics.setColor(color3);
/*     */       } 
/* 465 */       paramGraphics.drawLine(0, 0, dimension.width, 0);
/* 466 */       paramGraphics.drawLine(0, 1, dimension.width - 1, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 475 */       paramGraphics.drawLine(0, 0, 0, dimension.height);
/* 476 */       paramGraphics.drawLine(1, 0, 1, dimension.height - 1);
/*     */ 
/*     */       
/* 479 */       paramGraphics.setColor(color3);
/* 480 */       paramGraphics.drawLine(6, dimension.height - 5, dimension.width - 5, dimension.height - 5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 486 */       paramGraphics.drawLine(dimension.width - 5, 6, dimension.width - 5, dimension.height - 5);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 491 */       paramGraphics.setColor(color4);
/* 492 */       paramGraphics.drawLine(1, dimension.height, dimension.width, dimension.height);
/* 493 */       paramGraphics.drawLine(2, dimension.height - 1, dimension.width, dimension.height - 1);
/*     */ 
/*     */ 
/*     */       
/* 497 */       paramGraphics.drawLine(dimension.width, 1, dimension.width, dimension.height);
/* 498 */       paramGraphics.drawLine(dimension.width - 1, 2, dimension.width - 1, dimension.height);
/*     */ 
/*     */ 
/*     */       
/* 502 */       paramGraphics.drawLine(5, 5, dimension.width - 5, 5);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 507 */       paramGraphics.drawLine(5, 5, 5, dimension.height - 5);
/*     */     } 
/*     */ 
/*     */     
/* 511 */     if (hasDecorations(XWindowAttributesData.AWT_DECOR_TITLE)) {
/*     */       
/* 513 */       if (hasDecorations(XWindowAttributesData.AWT_DECOR_MENU)) {
/*     */ 
/*     */         
/* 516 */         paramGraphics.setColor(color1);
/* 517 */         paramGraphics.fill3DRect(6, 6, 17, 17, true);
/* 518 */         paramGraphics.fill3DRect(11, 13, 8, 3, true);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 524 */       paramGraphics.fill3DRect(23, 6, dimension.width - 60, 17, true);
/*     */ 
/*     */       
/* 527 */       if (hasDecorations(XWindowAttributesData.AWT_DECOR_MINIMIZE)) {
/*     */ 
/*     */ 
/*     */         
/* 531 */         paramGraphics.fill3DRect(dimension.width - 39, 6, 17, 17, true);
/*     */         
/* 533 */         paramGraphics.fill3DRect(dimension.width - 32, 13, 3, 3, true);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 538 */       if (hasDecorations(XWindowAttributesData.AWT_DECOR_MAXIMIZE)) {
/*     */ 
/*     */ 
/*     */         
/* 542 */         paramGraphics.fill3DRect(dimension.width - 22, 6, 17, 17, true);
/*     */         
/* 544 */         paramGraphics.fill3DRect(dimension.width - 17, 11, 8, 8, true);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 550 */       paramGraphics.setColor(color2);
/* 551 */       Font font = new Font("SansSerif", 0, 10);
/* 552 */       paramGraphics.setFont(font);
/* 553 */       FontMetrics fontMetrics = paramGraphics.getFontMetrics();
/* 554 */       String str = frame.getTitle();
/* 555 */       paramGraphics.drawString(str, (46 + dimension.width - 60) / 2 - fontMetrics
/*     */           
/* 557 */           .stringWidth(str) / 2, 14 + fontMetrics
/* 558 */           .getMaxDescent());
/*     */     } 
/*     */     
/* 561 */     if (frame.isResizable() && 
/* 562 */       hasDecorations(XWindowAttributesData.AWT_DECOR_RESIZEH)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 567 */       paramGraphics.setColor(color4);
/* 568 */       paramGraphics.drawLine(1, 22, 5, 22);
/*     */ 
/*     */ 
/*     */       
/* 572 */       paramGraphics.drawLine(22, 1, 22, 5);
/*     */ 
/*     */       
/* 575 */       paramGraphics.drawLine(dimension.width - 5 + 1, 22, dimension.width, 22);
/*     */ 
/*     */ 
/*     */       
/* 579 */       paramGraphics.drawLine(dimension.width - 22 - 1, 2, dimension.width - 22 - 1, 6);
/*     */ 
/*     */ 
/*     */       
/* 583 */       paramGraphics.drawLine(1, dimension.height - 22 - 1, 5, dimension.height - 22 - 1);
/*     */ 
/*     */ 
/*     */       
/* 587 */       paramGraphics.drawLine(22, dimension.height - 5 + 1, 22, dimension.height);
/*     */ 
/*     */ 
/*     */       
/* 591 */       paramGraphics.drawLine(dimension.width - 5 + 1, dimension.height - 22 - 1, dimension.width, dimension.height - 22 - 1);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 596 */       paramGraphics.drawLine(dimension.width - 22 - 1, dimension.height - 5 + 1, dimension.width - 22 - 1, dimension.height);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 601 */       paramGraphics.setColor(color3);
/* 602 */       paramGraphics.drawLine(2, 23, 5, 23);
/*     */ 
/*     */ 
/*     */       
/* 606 */       paramGraphics.drawLine(23, 2, 23, 5);
/*     */ 
/*     */ 
/*     */       
/* 610 */       paramGraphics.drawLine(dimension.width - 5 + 1, 23, dimension.width - 1, 23);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 615 */       paramGraphics.drawLine(dimension.width - 22, 2, dimension.width - 22, 5);
/*     */ 
/*     */ 
/*     */       
/* 619 */       paramGraphics.drawLine(2, dimension.height - 22, 5, dimension.height - 22);
/*     */ 
/*     */ 
/*     */       
/* 623 */       paramGraphics.drawLine(23, dimension.height - 5 + 1, 23, dimension.height - 1);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 628 */       paramGraphics.drawLine(dimension.width - 5 + 1, dimension.height - 22, dimension.width - 1, dimension.height - 22);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 633 */       paramGraphics.drawLine(dimension.width - 22, dimension.height - 5 + 1, dimension.width - 22, dimension.height - 1);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 638 */     XMenuBarPeer xMenuBarPeer = this.menubarPeer;
/* 639 */     if (xMenuBarPeer != null) {
/* 640 */       Insets insets1 = getInsets();
/* 641 */       Graphics graphics = paramGraphics.create();
/* 642 */       boolean bool1 = false;
/* 643 */       boolean bool2 = false;
/* 644 */       if (hasDecorations(XWindowAttributesData.AWT_DECOR_BORDER)) {
/* 645 */         bool1 += true;
/* 646 */         bool2 += true;
/*     */       } 
/* 648 */       if (hasDecorations(XWindowAttributesData.AWT_DECOR_TITLE)) {
/* 649 */         bool2 += true;
/*     */       }
/*     */       try {
/* 652 */         graphics.translate(bool1, bool2);
/* 653 */         xMenuBarPeer.print(graphics);
/*     */       } finally {
/* 655 */         graphics.dispose();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setBoundsPrivate(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 661 */     setBounds(paramInt1, paramInt2, paramInt3, paramInt4, 3);
/*     */   }
/*     */   
/*     */   public Rectangle getBoundsPrivate() {
/* 665 */     return getBounds();
/*     */   }
/*     */   
/*     */   public void emulateActivation(boolean paramBoolean) {
/* 669 */     if (paramBoolean) {
/* 670 */       handleWindowFocusIn(0L);
/*     */     } else {
/* 672 */       handleWindowFocusOut((Window)null, 0L);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XFramePeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */