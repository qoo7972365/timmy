/*      */ package sun.awt.X11;
/*      */ 
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.util.HashSet;
/*      */ import java.util.Set;
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
/*      */ public class XBaseWindow
/*      */ {
/*   34 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11.XBaseWindow");
/*   35 */   private static final PlatformLogger insLog = PlatformLogger.getLogger("sun.awt.X11.insets.XBaseWindow");
/*   36 */   private static final PlatformLogger eventLog = PlatformLogger.getLogger("sun.awt.X11.event.XBaseWindow");
/*   37 */   private static final PlatformLogger focusLog = PlatformLogger.getLogger("sun.awt.X11.focus.XBaseWindow");
/*   38 */   private static final PlatformLogger grabLog = PlatformLogger.getLogger("sun.awt.X11.grab.XBaseWindow"); public static final String PARENT_WINDOW = "parent window"; public static final String BOUNDS = "bounds"; public static final String OVERRIDE_REDIRECT = "overrideRedirect"; public static final String EVENT_MASK = "event mask"; public static final String VALUE_MASK = "value mask"; public static final String BORDER_PIXEL = "border pixel"; public static final String COLORMAP = "color map";
/*      */   public static final String DEPTH = "visual depth";
/*      */   public static final String VISUAL_CLASS = "visual class";
/*      */   public static final String VISUAL = "visual";
/*      */   public static final String EMBEDDED = "embedded";
/*      */   public static final String DELAYED = "delayed";
/*      */   public static final String PARENT = "parent";
/*      */   public static final String BACKGROUND_PIXMAP = "pixmap";
/*      */   public static final String VISIBLE = "visible";
/*      */   public static final String SAVE_UNDER = "save under";
/*      */   public static final String BACKING_STORE = "backing store";
/*      */   public static final String BIT_GRAVITY = "bit gravity";
/*      */   private XCreateWindowParams delayedParams;
/*      */   long window;
/*      */   boolean visible;
/*      */   boolean mapped;
/*      */   boolean embedded;
/*      */   Rectangle maxBounds;
/*      */   volatile XBaseWindow parentWindow;
/*      */   private boolean disposed;
/*      */   private long screen;
/*      */   private XSizeHints hints;
/*      */   private XWMHints wmHints;
/*   61 */   Set<Long> children = new HashSet<>();
/*      */   
/*      */   static final int MIN_SIZE = 1;
/*      */   
/*      */   static final int DEF_LOCATION = 1;
/*      */   
/*      */   private static XAtom wm_client_leader;
/*      */   
/*      */   private InitialiseState initialising;
/*      */   
/*      */   int x;
/*      */   
/*      */   int y;
/*      */   
/*      */   int width;
/*      */   int height;
/*      */   protected StateLock state_lock;
/*      */   
/*      */   enum InitialiseState
/*      */   {
/*   81 */     INITIALISING,
/*   82 */     INITIALISED,
/*   83 */     FAILED_INITIALISATION;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void awtLock() {
/*   94 */     XToolkit.awtLock();
/*      */   }
/*      */   
/*      */   void awtUnlock() {
/*   98 */     XToolkit.awtUnlock();
/*      */   }
/*      */   
/*      */   void awtLockNotifyAll() {
/*  102 */     XToolkit.awtLockNotifyAll();
/*      */   }
/*      */   
/*      */   void awtLockWait() throws InterruptedException {
/*  106 */     XToolkit.awtLockWait();
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void init(long paramLong, Rectangle paramRectangle) {}
/*      */ 
/*      */   
/*      */   protected final void preInit() {}
/*      */ 
/*      */   
/*      */   protected final void postInit() {}
/*      */ 
/*      */   
/*      */   static class StateLock {}
/*      */ 
/*      */   
/*      */   void instantPreInit(XCreateWindowParams paramXCreateWindowParams) {
/*  123 */     this.state_lock = new StateLock();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void preInit(XCreateWindowParams paramXCreateWindowParams) {
/*  131 */     this.state_lock = new StateLock();
/*  132 */     this.embedded = Boolean.TRUE.equals(paramXCreateWindowParams.get("embedded"));
/*  133 */     this.visible = Boolean.TRUE.equals(paramXCreateWindowParams.get("visible"));
/*      */     
/*  135 */     V v = paramXCreateWindowParams.get("parent");
/*  136 */     if (v instanceof XBaseWindow) {
/*  137 */       this.parentWindow = (XBaseWindow)v;
/*      */     } else {
/*  139 */       Long long_1 = (Long)paramXCreateWindowParams.get("parent window");
/*  140 */       if (long_1 != null) {
/*  141 */         this.parentWindow = XToolkit.windowToXWindow(long_1.longValue());
/*      */       }
/*      */     } 
/*      */     
/*  145 */     Long long_ = (Long)paramXCreateWindowParams.get("event mask");
/*  146 */     if (long_ != null) {
/*  147 */       long l = long_.longValue();
/*  148 */       l |= 0x80000L;
/*  149 */       paramXCreateWindowParams.put((K)"event mask", (V)Long.valueOf(l));
/*      */     } 
/*      */     
/*  152 */     this.screen = -1L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void postInit(XCreateWindowParams paramXCreateWindowParams) {
/*  160 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  161 */       log.fine("WM name is " + getWMName());
/*      */     }
/*  163 */     updateWMName();
/*      */ 
/*      */     
/*  166 */     initClientLeader();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void init(XCreateWindowParams paramXCreateWindowParams) {
/*  176 */     awtLock();
/*  177 */     this.initialising = InitialiseState.INITIALISING;
/*  178 */     awtUnlock();
/*      */     
/*      */     try {
/*  181 */       if (!Boolean.TRUE.equals(paramXCreateWindowParams.get("delayed"))) {
/*  182 */         preInit(paramXCreateWindowParams);
/*  183 */         create(paramXCreateWindowParams);
/*  184 */         postInit(paramXCreateWindowParams);
/*      */       } else {
/*  186 */         instantPreInit(paramXCreateWindowParams);
/*  187 */         this.delayedParams = paramXCreateWindowParams;
/*      */       } 
/*  189 */       awtLock();
/*  190 */       this.initialising = InitialiseState.INITIALISED;
/*  191 */       awtLockNotifyAll();
/*  192 */       awtUnlock();
/*  193 */     } catch (RuntimeException runtimeException) {
/*  194 */       awtLock();
/*  195 */       this.initialising = InitialiseState.FAILED_INITIALISATION;
/*  196 */       awtLockNotifyAll();
/*  197 */       awtUnlock();
/*  198 */       throw runtimeException;
/*  199 */     } catch (Throwable throwable) {
/*  200 */       log.warning("Exception during peer initialization", throwable);
/*  201 */       awtLock();
/*  202 */       this.initialising = InitialiseState.FAILED_INITIALISATION;
/*  203 */       awtLockNotifyAll();
/*  204 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean checkInitialised() {
/*  209 */     awtLock();
/*      */     try {
/*  211 */       switch (this.initialising) {
/*      */         case INITIALISED:
/*  213 */           bool = true; return bool;
/*      */         case INITIALISING:
/*      */           try {
/*  216 */             while (this.initialising != InitialiseState.INITIALISED) {
/*  217 */               awtLockWait();
/*      */             }
/*  219 */           } catch (InterruptedException interruptedException) {
/*  220 */             return false;
/*      */           } 
/*  222 */           bool = true; return bool;
/*      */         case FAILED_INITIALISATION:
/*  224 */           bool = false; return bool;
/*      */       } 
/*  226 */       boolean bool = false; return bool;
/*      */     } finally {
/*      */       
/*  229 */       awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XBaseWindow() {
/*  237 */     this(new XCreateWindowParams());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XBaseWindow(long paramLong, Rectangle paramRectangle) {
/*  244 */     this(new XCreateWindowParams(new Object[] { "bounds", paramRectangle, "parent window", 
/*      */             
/*  246 */             Long.valueOf(paramLong) }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XBaseWindow(Rectangle paramRectangle) {
/*  253 */     this(new XCreateWindowParams(new Object[] { "bounds", paramRectangle }));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public XBaseWindow(XCreateWindowParams paramXCreateWindowParams) {
/*  259 */     init(paramXCreateWindowParams);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   XBaseWindow(long paramLong) {
/*  265 */     this(new XCreateWindowParams(new Object[] { "parent window", 
/*  266 */             Long.valueOf(paramLong), "embedded", Boolean.TRUE }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkParams(XCreateWindowParams paramXCreateWindowParams) {
/*  277 */     if (paramXCreateWindowParams == null) {
/*  278 */       throw new IllegalArgumentException("Window creation parameters are null");
/*      */     }
/*  280 */     paramXCreateWindowParams.putIfNull("parent window", Long.valueOf(XToolkit.getDefaultRootWindow()));
/*  281 */     paramXCreateWindowParams.putIfNull("bounds", new Rectangle(1, 1, 1, 1));
/*  282 */     paramXCreateWindowParams.putIfNull("visual depth", Integer.valueOf(0));
/*  283 */     paramXCreateWindowParams.putIfNull("visual", Long.valueOf(0L));
/*  284 */     paramXCreateWindowParams.putIfNull("visual class", Integer.valueOf(2));
/*  285 */     paramXCreateWindowParams.putIfNull("value mask", Long.valueOf(2048L));
/*  286 */     Rectangle rectangle = (Rectangle)paramXCreateWindowParams.get("bounds");
/*  287 */     rectangle.width = Math.max(1, rectangle.width);
/*  288 */     rectangle.height = Math.max(1, rectangle.height);
/*      */     
/*  290 */     Long long_ = (Long)paramXCreateWindowParams.get("event mask");
/*  291 */     long l = (long_ != null) ? long_.longValue() : 0L;
/*      */ 
/*      */     
/*  294 */     l |= 0x1400000L;
/*  295 */     paramXCreateWindowParams.put((K)"event mask", (V)Long.valueOf(l));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void create(XCreateWindowParams paramXCreateWindowParams) {
/*  303 */     XToolkit.awtLock();
/*      */     try {
/*  305 */       XSetWindowAttributes xSetWindowAttributes = new XSetWindowAttributes();
/*      */       try {
/*  307 */         checkParams(paramXCreateWindowParams);
/*      */         
/*  309 */         long l = ((Long)paramXCreateWindowParams.get("value mask")).longValue();
/*      */         
/*  311 */         Long long_1 = (Long)paramXCreateWindowParams.get("event mask");
/*  312 */         xSetWindowAttributes.set_event_mask(long_1.longValue());
/*  313 */         l |= 0x800L;
/*      */         
/*  315 */         Long long_2 = (Long)paramXCreateWindowParams.get("border pixel");
/*  316 */         if (long_2 != null) {
/*  317 */           xSetWindowAttributes.set_border_pixel(long_2.longValue());
/*  318 */           l |= 0x8L;
/*      */         } 
/*      */         
/*  321 */         Long long_3 = (Long)paramXCreateWindowParams.get("color map");
/*  322 */         if (long_3 != null) {
/*  323 */           xSetWindowAttributes.set_colormap(long_3.longValue());
/*  324 */           l |= 0x2000L;
/*      */         } 
/*  326 */         Long long_4 = (Long)paramXCreateWindowParams.get("pixmap");
/*  327 */         if (long_4 != null) {
/*  328 */           xSetWindowAttributes.set_background_pixmap(long_4.longValue());
/*  329 */           l |= 0x1L;
/*      */         } 
/*      */         
/*  332 */         Long long_5 = (Long)paramXCreateWindowParams.get("parent window");
/*  333 */         Rectangle rectangle = (Rectangle)paramXCreateWindowParams.get("bounds");
/*  334 */         Integer integer1 = (Integer)paramXCreateWindowParams.get("visual depth");
/*  335 */         Integer integer2 = (Integer)paramXCreateWindowParams.get("visual class");
/*  336 */         Long long_6 = (Long)paramXCreateWindowParams.get("visual");
/*  337 */         Boolean bool1 = (Boolean)paramXCreateWindowParams.get("overrideRedirect");
/*  338 */         if (bool1 != null) {
/*  339 */           xSetWindowAttributes.set_override_redirect(bool1.booleanValue());
/*  340 */           l |= 0x200L;
/*      */         } 
/*      */         
/*  343 */         Boolean bool2 = (Boolean)paramXCreateWindowParams.get("save under");
/*  344 */         if (bool2 != null) {
/*  345 */           xSetWindowAttributes.set_save_under(bool2.booleanValue());
/*  346 */           l |= 0x400L;
/*      */         } 
/*      */         
/*  349 */         Integer integer3 = (Integer)paramXCreateWindowParams.get("backing store");
/*  350 */         if (integer3 != null) {
/*  351 */           xSetWindowAttributes.set_backing_store(integer3.intValue());
/*  352 */           l |= 0x40L;
/*      */         } 
/*      */         
/*  355 */         Integer integer4 = (Integer)paramXCreateWindowParams.get("bit gravity");
/*  356 */         if (integer4 != null) {
/*  357 */           xSetWindowAttributes.set_bit_gravity(integer4.intValue());
/*  358 */           l |= 0x10L;
/*      */         } 
/*      */         
/*  361 */         if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  362 */           log.fine("Creating window for " + this + " with the following attributes: \n" + paramXCreateWindowParams);
/*      */         }
/*  364 */         this.window = XlibWrapper.XCreateWindow(XToolkit.getDisplay(), long_5
/*  365 */             .longValue(), rectangle.x, rectangle.y, rectangle.width, rectangle.height, 0, integer1
/*      */ 
/*      */ 
/*      */             
/*  369 */             .intValue(), integer2
/*  370 */             .intValue(), long_6
/*  371 */             .longValue(), l, xSetWindowAttributes.pData);
/*      */ 
/*      */ 
/*      */         
/*  375 */         if (this.window == 0L) {
/*  376 */           throw new IllegalStateException("Couldn't create window because of wrong parameters. Run with NOISY_AWT to see details");
/*      */         }
/*  378 */         XToolkit.addToWinMap(this.window, this);
/*      */       } finally {
/*  380 */         xSetWindowAttributes.dispose();
/*      */       } 
/*      */     } finally {
/*  383 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public XCreateWindowParams getDelayedParams() {
/*  388 */     return this.delayedParams;
/*      */   }
/*      */   
/*      */   protected String getWMName() {
/*  392 */     return XToolkit.getCorrectXIDString(getClass().getName());
/*      */   }
/*      */   
/*      */   protected void initClientLeader() {
/*  396 */     XToolkit.awtLock();
/*      */     try {
/*  398 */       if (wm_client_leader == null) {
/*  399 */         wm_client_leader = XAtom.get("WM_CLIENT_LEADER");
/*      */       }
/*  401 */       wm_client_leader.setWindowProperty(this, getXAWTRootWindow());
/*      */     } finally {
/*  403 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   static XRootWindow getXAWTRootWindow() {
/*  408 */     return XRootWindow.getInstance();
/*      */   }
/*      */   
/*      */   void destroy() {
/*  412 */     XToolkit.awtLock();
/*      */     try {
/*  414 */       if (this.hints != null) {
/*  415 */         XlibWrapper.XFree(this.hints.pData);
/*  416 */         this.hints = null;
/*      */       } 
/*  418 */       XToolkit.removeFromWinMap(getWindow(), this);
/*  419 */       XlibWrapper.XDestroyWindow(XToolkit.getDisplay(), getWindow());
/*  420 */       if (XPropertyCache.isCachingSupported()) {
/*  421 */         XPropertyCache.clearCache(this.window);
/*      */       }
/*  423 */       this.window = -1L;
/*  424 */       if (!isDisposed()) {
/*  425 */         setDisposed(true);
/*      */       }
/*      */       
/*  428 */       XAwtState.getGrabWindow();
/*      */     } finally {
/*  430 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   void flush() {
/*  435 */     XToolkit.awtLock();
/*      */     try {
/*  437 */       XlibWrapper.XFlush(XToolkit.getDisplay());
/*      */     } finally {
/*  439 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setWMHints(XWMHints paramXWMHints) {
/*  447 */     XToolkit.awtLock();
/*      */     try {
/*  449 */       XlibWrapper.XSetWMHints(XToolkit.getDisplay(), getWindow(), paramXWMHints.pData);
/*      */     } finally {
/*  451 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public XWMHints getWMHints() {
/*  456 */     if (this.wmHints == null) {
/*  457 */       this.wmHints = new XWMHints(XlibWrapper.XAllocWMHints());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  462 */     return this.wmHints;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XSizeHints getHints() {
/*  471 */     if (this.hints == null) {
/*  472 */       long l = XlibWrapper.XAllocSizeHints();
/*  473 */       this.hints = new XSizeHints(l);
/*      */     } 
/*      */ 
/*      */     
/*  477 */     return this.hints;
/*      */   }
/*      */   
/*      */   public void setSizeHints(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  481 */     if (insLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  482 */       insLog.finer("Setting hints, flags " + XlibWrapper.hintsToString(paramLong));
/*      */     }
/*  484 */     XToolkit.awtLock();
/*      */     try {
/*  486 */       XSizeHints xSizeHints = getHints();
/*      */ 
/*      */ 
/*      */       
/*  490 */       if ((paramLong & 0x4L) != 0L) {
/*  491 */         xSizeHints.set_x(paramInt1);
/*  492 */         xSizeHints.set_y(paramInt2);
/*      */       } 
/*  494 */       if ((paramLong & 0x8L) != 0L) {
/*  495 */         xSizeHints.set_width(paramInt3);
/*  496 */         xSizeHints.set_height(paramInt4);
/*  497 */       } else if ((xSizeHints.get_flags() & 0x8L) != 0L) {
/*  498 */         paramLong |= 0x8L;
/*      */       } 
/*  500 */       if ((paramLong & 0x10L) != 0L) {
/*  501 */         xSizeHints.set_min_width(paramInt3);
/*  502 */         xSizeHints.set_min_height(paramInt4);
/*  503 */       } else if ((xSizeHints.get_flags() & 0x10L) != 0L) {
/*  504 */         paramLong |= 0x10L;
/*      */       } 
/*      */ 
/*      */       
/*  508 */       if ((paramLong & 0x20L) != 0L) {
/*  509 */         if (this.maxBounds != null) {
/*  510 */           if (this.maxBounds.width != Integer.MAX_VALUE) {
/*  511 */             xSizeHints.set_max_width(this.maxBounds.width);
/*      */           } else {
/*  513 */             xSizeHints.set_max_width(XToolkit.getDefaultScreenWidth());
/*      */           } 
/*  515 */           if (this.maxBounds.height != Integer.MAX_VALUE) {
/*  516 */             xSizeHints.set_max_height(this.maxBounds.height);
/*      */           } else {
/*  518 */             xSizeHints.set_max_height(XToolkit.getDefaultScreenHeight());
/*      */           } 
/*      */         } else {
/*  521 */           xSizeHints.set_max_width(paramInt3);
/*  522 */           xSizeHints.set_max_height(paramInt4);
/*      */         } 
/*  524 */       } else if ((xSizeHints.get_flags() & 0x20L) != 0L) {
/*  525 */         paramLong |= 0x20L;
/*  526 */         if (this.maxBounds != null) {
/*  527 */           if (this.maxBounds.width != Integer.MAX_VALUE) {
/*  528 */             xSizeHints.set_max_width(this.maxBounds.width);
/*      */           } else {
/*  530 */             xSizeHints.set_max_width(XToolkit.getDefaultScreenWidth());
/*      */           } 
/*  532 */           if (this.maxBounds.height != Integer.MAX_VALUE) {
/*  533 */             xSizeHints.set_max_height(this.maxBounds.height);
/*      */           } else {
/*  535 */             xSizeHints.set_max_height(XToolkit.getDefaultScreenHeight());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  541 */       paramLong |= 0x200L;
/*  542 */       xSizeHints.set_flags(paramLong);
/*  543 */       xSizeHints.set_win_gravity(1);
/*  544 */       if (insLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  545 */         insLog.finer("Setting hints, resulted flags " + XlibWrapper.hintsToString(paramLong) + ", values " + xSizeHints);
/*      */       }
/*      */       
/*  548 */       XlibWrapper.XSetWMNormalHints(XToolkit.getDisplay(), getWindow(), xSizeHints.pData);
/*      */     } finally {
/*  550 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isMinSizeSet() {
/*  555 */     XSizeHints xSizeHints = getHints();
/*  556 */     long l = xSizeHints.get_flags();
/*  557 */     return ((l & 0x10L) == 16L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Object getStateLock() {
/*  565 */     return this.state_lock;
/*      */   }
/*      */   
/*      */   public long getWindow() {
/*  569 */     return this.window;
/*      */   }
/*      */   public long getContentWindow() {
/*  572 */     return this.window;
/*      */   }
/*      */   
/*      */   public XBaseWindow getContentXWindow() {
/*  576 */     return XToolkit.windowToXWindow(getContentWindow());
/*      */   }
/*      */   
/*      */   public Rectangle getBounds() {
/*  580 */     return new Rectangle(this.x, this.y, this.width, this.height);
/*      */   }
/*      */   public Dimension getSize() {
/*  583 */     return new Dimension(this.width, this.height);
/*      */   }
/*      */ 
/*      */   
/*      */   public void toFront() {
/*  588 */     XToolkit.awtLock();
/*      */     try {
/*  590 */       XlibWrapper.XRaiseWindow(XToolkit.getDisplay(), getWindow());
/*      */     } finally {
/*  592 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   public void xRequestFocus(long paramLong) {
/*  596 */     XToolkit.awtLock();
/*      */     try {
/*  598 */       if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  599 */         focusLog.finer("XSetInputFocus on " + Long.toHexString(getWindow()) + " with time " + paramLong);
/*      */       }
/*  601 */       XlibWrapper.XSetInputFocus2(XToolkit.getDisplay(), getWindow(), paramLong);
/*      */     } finally {
/*  603 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   public void xRequestFocus() {
/*  607 */     XToolkit.awtLock();
/*      */     try {
/*  609 */       if (focusLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  610 */         focusLog.finer("XSetInputFocus on " + Long.toHexString(getWindow()));
/*      */       }
/*  612 */       XlibWrapper.XSetInputFocus(XToolkit.getDisplay(), getWindow());
/*      */     } finally {
/*  614 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static long xGetInputFocus() {
/*  619 */     XToolkit.awtLock();
/*      */     try {
/*  621 */       return XlibWrapper.XGetInputFocus(XToolkit.getDisplay());
/*      */     } finally {
/*  623 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void xSetVisible(boolean paramBoolean) {
/*  628 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*  629 */       log.fine("Setting visible on " + this + " to " + paramBoolean);
/*      */     }
/*  631 */     XToolkit.awtLock();
/*      */     try {
/*  633 */       this.visible = paramBoolean;
/*  634 */       if (paramBoolean) {
/*  635 */         XlibWrapper.XMapWindow(XToolkit.getDisplay(), getWindow());
/*      */       } else {
/*      */         
/*  638 */         XlibWrapper.XUnmapWindow(XToolkit.getDisplay(), getWindow());
/*      */       } 
/*  640 */       XlibWrapper.XFlush(XToolkit.getDisplay());
/*      */     } finally {
/*  642 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   boolean isMapped() {
/*  647 */     return this.mapped;
/*      */   }
/*      */   
/*      */   void updateWMName() {
/*  651 */     String str = getWMName();
/*  652 */     XToolkit.awtLock();
/*      */     try {
/*  654 */       if (str == null) {
/*  655 */         str = " ";
/*      */       }
/*  657 */       XAtom xAtom1 = XAtom.get(39L);
/*  658 */       xAtom1.setProperty(getWindow(), str);
/*  659 */       XAtom xAtom2 = XAtom.get("_NET_WM_NAME");
/*  660 */       xAtom2.setPropertyUTF8(getWindow(), str);
/*      */     } finally {
/*  662 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   void setWMClass(String[] paramArrayOfString) {
/*  666 */     if (paramArrayOfString.length != 2) {
/*  667 */       throw new IllegalArgumentException("WM_CLASS_NAME consists of exactly two strings");
/*      */     }
/*  669 */     XToolkit.awtLock();
/*      */     try {
/*  671 */       XAtom xAtom = XAtom.get(67L);
/*  672 */       xAtom.setProperty8(getWindow(), paramArrayOfString[0] + Character.MIN_VALUE + paramArrayOfString[1] + Character.MIN_VALUE);
/*      */     } finally {
/*  674 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   boolean isVisible() {
/*  679 */     return this.visible;
/*      */   }
/*      */   
/*      */   static long getScreenOfWindow(long paramLong) {
/*  683 */     XToolkit.awtLock();
/*      */     try {
/*  685 */       return XlibWrapper.getScreenOfWindow(XToolkit.getDisplay(), paramLong);
/*      */     } finally {
/*  687 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   long getScreenNumber() {
/*  691 */     XToolkit.awtLock();
/*      */     try {
/*  693 */       return XlibWrapper.XScreenNumberOfScreen(getScreen());
/*      */     } finally {
/*  695 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   long getScreen() {
/*  700 */     if (this.screen == -1L) {
/*  701 */       this.screen = getScreenOfWindow(this.window);
/*      */     }
/*  703 */     return this.screen;
/*      */   }
/*      */   
/*      */   public void xSetBounds(Rectangle paramRectangle) {
/*  707 */     xSetBounds(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*      */   }
/*      */   
/*      */   public void xSetBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  711 */     if (getWindow() == 0L) {
/*  712 */       insLog.warning("Attempt to resize uncreated window");
/*  713 */       throw new IllegalStateException("Attempt to resize uncreated window");
/*      */     } 
/*  715 */     if (insLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  716 */       insLog.fine("Setting bounds on " + this + " to (" + paramInt1 + ", " + paramInt2 + "), " + paramInt3 + "x" + paramInt4);
/*      */     }
/*  718 */     paramInt3 = Math.max(1, paramInt3);
/*  719 */     paramInt4 = Math.max(1, paramInt4);
/*  720 */     XToolkit.awtLock();
/*      */     try {
/*  722 */       XlibWrapper.XMoveResizeWindow(XToolkit.getDisplay(), getWindow(), paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     } finally {
/*  724 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Point toOtherWindow(long paramLong1, long paramLong2, int paramInt1, int paramInt2) {
/*  735 */     Point point = new Point(0, 0);
/*      */ 
/*      */ 
/*      */     
/*  739 */     XBaseWindow xBaseWindow1 = XToolkit.windowToXWindow(paramLong1);
/*  740 */     XBaseWindow xBaseWindow2 = XToolkit.windowToXWindow(paramLong2);
/*      */     
/*  742 */     if (xBaseWindow1 != null && xBaseWindow2 != null) {
/*      */       
/*  744 */       point.x = paramInt1 + xBaseWindow1.getAbsoluteX() - xBaseWindow2.getAbsoluteX();
/*  745 */       point.y = paramInt2 + xBaseWindow1.getAbsoluteY() - xBaseWindow2.getAbsoluteY();
/*  746 */     } else if (xBaseWindow2 != null && XlibUtil.isRoot(paramLong1, xBaseWindow2.getScreenNumber())) {
/*      */       
/*  748 */       point.x = paramInt1 - xBaseWindow2.getAbsoluteX();
/*  749 */       point.y = paramInt2 - xBaseWindow2.getAbsoluteY();
/*  750 */     } else if (xBaseWindow1 != null && XlibUtil.isRoot(paramLong2, xBaseWindow1.getScreenNumber())) {
/*      */       
/*  752 */       point.x = paramInt1 + xBaseWindow1.getAbsoluteX();
/*  753 */       point.y = paramInt2 + xBaseWindow1.getAbsoluteY();
/*      */     } else {
/*  755 */       point = XlibUtil.translateCoordinates(paramLong1, paramLong2, new Point(paramInt1, paramInt2));
/*      */     } 
/*  757 */     return point;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Rectangle toGlobal(Rectangle paramRectangle) {
/*  764 */     Point point = toGlobal(paramRectangle.getLocation());
/*  765 */     Rectangle rectangle = new Rectangle(paramRectangle);
/*  766 */     if (point != null) {
/*  767 */       rectangle.setLocation(point);
/*      */     }
/*  769 */     return rectangle;
/*      */   }
/*      */   
/*      */   Point toGlobal(Point paramPoint) {
/*  773 */     Point point = toGlobal(paramPoint.x, paramPoint.y);
/*  774 */     if (point != null) {
/*  775 */       return point;
/*      */     }
/*  777 */     return new Point(paramPoint);
/*      */   }
/*      */ 
/*      */   
/*      */   Point toGlobal(int paramInt1, int paramInt2) {
/*      */     long l;
/*  783 */     XToolkit.awtLock();
/*      */     try {
/*  785 */       l = XlibWrapper.RootWindow(XToolkit.getDisplay(), 
/*  786 */           getScreenNumber());
/*      */     } finally {
/*  788 */       XToolkit.awtUnlock();
/*      */     } 
/*  790 */     Point point = toOtherWindow(getContentWindow(), l, paramInt1, paramInt2);
/*  791 */     if (point != null) {
/*  792 */       return point;
/*      */     }
/*  794 */     return new Point(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Point toLocal(Point paramPoint) {
/*  802 */     Point point = toLocal(paramPoint.x, paramPoint.y);
/*  803 */     if (point != null) {
/*  804 */       return point;
/*      */     }
/*  806 */     return new Point(paramPoint);
/*      */   }
/*      */ 
/*      */   
/*      */   Point toLocal(int paramInt1, int paramInt2) {
/*      */     long l;
/*  812 */     XToolkit.awtLock();
/*      */     try {
/*  814 */       l = XlibWrapper.RootWindow(XToolkit.getDisplay(), 
/*  815 */           getScreenNumber());
/*      */     } finally {
/*  817 */       XToolkit.awtUnlock();
/*      */     } 
/*  819 */     Point point = toOtherWindow(l, getContentWindow(), paramInt1, paramInt2);
/*  820 */     if (point != null) {
/*  821 */       return point;
/*      */     }
/*  823 */     return new Point(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean grabInput() {
/*  833 */     if (grabLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  834 */       grabLog.fine("Grab input on {0}", new Object[] { this });
/*      */     }
/*      */     
/*  837 */     XToolkit.awtLock();
/*      */     try {
/*  839 */       if (XAwtState.getGrabWindow() == this && 
/*  840 */         XAwtState.isManualGrab()) {
/*      */         
/*  842 */         grabLog.fine("    Already Grabbed");
/*  843 */         return true;
/*      */       } 
/*      */ 
/*      */       
/*  847 */       XBaseWindow xBaseWindow = XAwtState.getGrabWindow();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  859 */       if (!XToolkit.getSunAwtDisableGrab()) {
/*  860 */         int i = XlibWrapper.XGrabPointer(XToolkit.getDisplay(), 
/*  861 */             getContentWindow(), 1, 8316, 1, 1, 0L, 
/*  862 */             XWM.isMotif() ? XToolkit.arrowCursor : 0L, 0L);
/*      */ 
/*      */         
/*  865 */         if (i != 0) {
/*  866 */           XlibWrapper.XUngrabPointer(XToolkit.getDisplay(), 0L);
/*  867 */           XAwtState.setGrabWindow(null);
/*  868 */           grabLog.fine("    Grab Failure - mouse");
/*  869 */           return false;
/*      */         } 
/*      */         
/*  872 */         int j = XlibWrapper.XGrabKeyboard(XToolkit.getDisplay(), 
/*  873 */             getContentWindow(), 1, 1, 1, 0L);
/*      */         
/*  875 */         if (j != 0) {
/*  876 */           XlibWrapper.XUngrabPointer(XToolkit.getDisplay(), 0L);
/*  877 */           XlibWrapper.XUngrabKeyboard(XToolkit.getDisplay(), 0L);
/*  878 */           XAwtState.setGrabWindow(null);
/*  879 */           grabLog.fine("    Grab Failure - keyboard");
/*  880 */           return false;
/*      */         } 
/*      */       } 
/*  883 */       if (xBaseWindow != null) {
/*  884 */         xBaseWindow.ungrabInputImpl();
/*      */       }
/*  886 */       XAwtState.setGrabWindow(this);
/*  887 */       grabLog.fine("    Grab - success");
/*  888 */       return true;
/*      */     } finally {
/*  890 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   static void ungrabInput() {
/*  895 */     XToolkit.awtLock();
/*      */     try {
/*  897 */       XBaseWindow xBaseWindow = XAwtState.getGrabWindow();
/*  898 */       if (grabLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  899 */         grabLog.fine("UnGrab input on {0}", new Object[] { xBaseWindow });
/*      */       }
/*  901 */       if (xBaseWindow != null) {
/*  902 */         xBaseWindow.ungrabInputImpl();
/*  903 */         if (!XToolkit.getSunAwtDisableGrab()) {
/*  904 */           XlibWrapper.XUngrabPointer(XToolkit.getDisplay(), 0L);
/*  905 */           XlibWrapper.XUngrabKeyboard(XToolkit.getDisplay(), 0L);
/*      */         } 
/*  907 */         XAwtState.setGrabWindow(null);
/*      */ 
/*      */         
/*  910 */         XlibWrapper.XFlush(XToolkit.getDisplay());
/*      */       } 
/*      */     } finally {
/*  913 */       XToolkit.awtUnlock();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void ungrabInputImpl() {}
/*      */ 
/*      */   
/*      */   static void checkSecurity() {
/*  922 */     if (XToolkit.isSecurityWarningEnabled() && XToolkit.isToolkitThread()) {
/*  923 */       StackTraceElement[] arrayOfStackTraceElement = (new Throwable()).getStackTrace();
/*  924 */       log.warning(arrayOfStackTraceElement[1] + ": Security violation: calling user code on toolkit thread");
/*      */     } 
/*      */   }
/*      */   
/*      */   public Set<Long> getChildren() {
/*  929 */     synchronized (getStateLock()) {
/*  930 */       return new HashSet<>(this.children);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleMapNotifyEvent(XEvent paramXEvent) {
/*  936 */     this.mapped = true;
/*      */   }
/*      */   public void handleUnmapNotifyEvent(XEvent paramXEvent) {
/*  939 */     this.mapped = false;
/*      */   }
/*      */   public void handleReparentNotifyEvent(XEvent paramXEvent) {
/*  942 */     if (eventLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  943 */       XReparentEvent xReparentEvent = paramXEvent.get_xreparent();
/*  944 */       eventLog.finer(xReparentEvent.toString());
/*      */     } 
/*      */   }
/*      */   public void handlePropertyNotify(XEvent paramXEvent) {
/*  948 */     XPropertyEvent xPropertyEvent = paramXEvent.get_xproperty();
/*  949 */     if (XPropertyCache.isCachingSupported()) {
/*  950 */       XPropertyCache.clearCache(this.window, XAtom.get(xPropertyEvent.get_atom()));
/*      */     }
/*  952 */     if (eventLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  953 */       eventLog.finer("{0}", new Object[] { xPropertyEvent });
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleDestroyNotify(XEvent paramXEvent) {
/*  958 */     XAnyEvent xAnyEvent = paramXEvent.get_xany();
/*  959 */     if (xAnyEvent.get_window() == getWindow()) {
/*  960 */       XToolkit.removeFromWinMap(getWindow(), this);
/*  961 */       if (XPropertyCache.isCachingSupported()) {
/*  962 */         XPropertyCache.clearCache(getWindow());
/*      */       }
/*      */     } 
/*  965 */     if (xAnyEvent.get_window() != getWindow()) {
/*  966 */       synchronized (getStateLock()) {
/*  967 */         this.children.remove(Long.valueOf(xAnyEvent.get_window()));
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleCreateNotify(XEvent paramXEvent) {
/*  973 */     XAnyEvent xAnyEvent = paramXEvent.get_xany();
/*  974 */     if (xAnyEvent.get_window() != getWindow()) {
/*  975 */       synchronized (getStateLock()) {
/*  976 */         this.children.add(Long.valueOf(xAnyEvent.get_window()));
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleClientMessage(XEvent paramXEvent) {
/*  982 */     if (eventLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  983 */       XClientMessageEvent xClientMessageEvent = paramXEvent.get_xclient();
/*  984 */       eventLog.finer(xClientMessageEvent.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleVisibilityEvent(XEvent paramXEvent) {}
/*      */ 
/*      */   
/*      */   public void handleKeyPress(XEvent paramXEvent) {}
/*      */ 
/*      */   
/*      */   public void handleKeyRelease(XEvent paramXEvent) {}
/*      */ 
/*      */   
/*      */   public void handleExposeEvent(XEvent paramXEvent) {}
/*      */   
/*      */   public void handleButtonPressRelease(XEvent paramXEvent) {
/* 1001 */     XButtonEvent xButtonEvent = paramXEvent.get_xbutton();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1007 */     if (xButtonEvent.get_button() > 20) {
/*      */       return;
/*      */     }
/* 1010 */     int i = 0;
/* 1011 */     i = xButtonEvent.get_state() & 0x1F00;
/* 1012 */     switch (paramXEvent.get_type()) {
/*      */       case 4:
/* 1014 */         if (i == 0) {
/* 1015 */           XWindowPeer xWindowPeer = getToplevelXWindow();
/*      */           
/* 1017 */           if (xWindowPeer != null && xWindowPeer.isFocusableWindow()) {
/*      */             
/* 1019 */             xWindowPeer.setActualFocusedWindow(null);
/* 1020 */             xWindowPeer.requestWindowFocus(xButtonEvent.get_time(), true);
/*      */           } 
/* 1022 */           XAwtState.setAutoGrabWindow(this);
/*      */         } 
/*      */         break;
/*      */       case 5:
/* 1026 */         if (isFullRelease(i, xButtonEvent.get_button()))
/* 1027 */           XAwtState.setAutoGrabWindow(null); 
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleMotionNotify(XEvent paramXEvent) {}
/*      */   
/*      */   public void handleXCrossingEvent(XEvent paramXEvent) {}
/*      */   
/*      */   public void handleConfigureNotifyEvent(XEvent paramXEvent) {
/* 1037 */     XConfigureEvent xConfigureEvent = paramXEvent.get_xconfigure();
/* 1038 */     if (insLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 1039 */       insLog.finer("Configure, {0}", new Object[] { xConfigureEvent });
/*      */     }
/* 1041 */     this.x = xConfigureEvent.get_x();
/* 1042 */     this.y = xConfigureEvent.get_y();
/* 1043 */     this.width = xConfigureEvent.get_width();
/* 1044 */     this.height = xConfigureEvent.get_height();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isFullRelease(int paramInt1, int paramInt2) {
/* 1050 */     int i = XToolkit.getNumberOfButtonsForMask();
/*      */     
/* 1052 */     if (paramInt2 < 0 || paramInt2 > i) {
/* 1053 */       return (paramInt1 == 0);
/*      */     }
/* 1055 */     return (paramInt1 == XlibUtil.getButtonMask(paramInt2));
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean isGrabbedEvent(XEvent paramXEvent, XBaseWindow paramXBaseWindow) {
/* 1060 */     switch (paramXEvent.get_type()) {
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/* 1066 */         return true;
/*      */ 
/*      */       
/*      */       case 7:
/*      */       case 8:
/* 1071 */         return paramXBaseWindow instanceof XWindowPeer;
/*      */     } 
/* 1073 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void dispatchToWindow(XEvent paramXEvent) {
/* 1081 */     XBaseWindow xBaseWindow = XAwtState.getGrabWindow();
/* 1082 */     if (xBaseWindow == null || !isGrabbedEvent(paramXEvent, xBaseWindow)) {
/* 1083 */       xBaseWindow = XToolkit.windowToXWindow(paramXEvent.get_xany().get_window());
/*      */     }
/* 1085 */     if (xBaseWindow != null && xBaseWindow.checkInitialised()) {
/* 1086 */       xBaseWindow.dispatchEvent(paramXEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   public void dispatchEvent(XEvent paramXEvent) {
/* 1091 */     if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 1092 */       eventLog.finest(paramXEvent.toString());
/*      */     }
/* 1094 */     int i = paramXEvent.get_type();
/*      */     
/* 1096 */     if (isDisposed()) {
/*      */       return;
/*      */     }
/*      */     
/* 1100 */     switch (i) {
/*      */       
/*      */       case 15:
/* 1103 */         handleVisibilityEvent(paramXEvent);
/*      */         break;
/*      */       case 33:
/* 1106 */         handleClientMessage(paramXEvent);
/*      */         break;
/*      */       case 12:
/*      */       case 13:
/* 1110 */         handleExposeEvent(paramXEvent);
/*      */         break;
/*      */       case 4:
/*      */       case 5:
/* 1114 */         handleButtonPressRelease(paramXEvent);
/*      */         break;
/*      */       
/*      */       case 6:
/* 1118 */         handleMotionNotify(paramXEvent);
/*      */         break;
/*      */       case 2:
/* 1121 */         handleKeyPress(paramXEvent);
/*      */         break;
/*      */       case 3:
/* 1124 */         handleKeyRelease(paramXEvent);
/*      */         break;
/*      */       case 7:
/*      */       case 8:
/* 1128 */         handleXCrossingEvent(paramXEvent);
/*      */         break;
/*      */       case 22:
/* 1131 */         handleConfigureNotifyEvent(paramXEvent);
/*      */         break;
/*      */       case 19:
/* 1134 */         handleMapNotifyEvent(paramXEvent);
/*      */         break;
/*      */       case 18:
/* 1137 */         handleUnmapNotifyEvent(paramXEvent);
/*      */         break;
/*      */       case 21:
/* 1140 */         handleReparentNotifyEvent(paramXEvent);
/*      */         break;
/*      */       case 28:
/* 1143 */         handlePropertyNotify(paramXEvent);
/*      */         break;
/*      */       case 17:
/* 1146 */         handleDestroyNotify(paramXEvent);
/*      */         break;
/*      */       case 16:
/* 1149 */         handleCreateNotify(paramXEvent);
/*      */         break;
/*      */     } 
/*      */   }
/*      */   protected boolean isEventDisabled(XEvent paramXEvent) {
/* 1154 */     return false;
/*      */   }
/*      */   
/*      */   int getX() {
/* 1158 */     return this.x;
/*      */   }
/*      */   
/*      */   int getY() {
/* 1162 */     return this.y;
/*      */   }
/*      */   
/*      */   int getWidth() {
/* 1166 */     return this.width;
/*      */   }
/*      */   
/*      */   int getHeight() {
/* 1170 */     return this.height;
/*      */   }
/*      */   
/*      */   void setDisposed(boolean paramBoolean) {
/* 1174 */     this.disposed = paramBoolean;
/*      */   }
/*      */   
/*      */   boolean isDisposed() {
/* 1178 */     return this.disposed;
/*      */   }
/*      */   
/*      */   public int getAbsoluteX() {
/* 1182 */     XBaseWindow xBaseWindow = getParentWindow();
/* 1183 */     if (xBaseWindow != null) {
/* 1184 */       return xBaseWindow.getAbsoluteX() + getX();
/*      */     }
/*      */     
/* 1187 */     return getX();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getAbsoluteY() {
/* 1192 */     XBaseWindow xBaseWindow = getParentWindow();
/* 1193 */     if (xBaseWindow != null) {
/* 1194 */       return xBaseWindow.getAbsoluteY() + getY();
/*      */     }
/* 1196 */     return getY();
/*      */   }
/*      */ 
/*      */   
/*      */   public XBaseWindow getParentWindow() {
/* 1201 */     return this.parentWindow;
/*      */   }
/*      */   
/*      */   public XWindowPeer getToplevelXWindow() {
/* 1205 */     XBaseWindow xBaseWindow = this;
/* 1206 */     while (xBaseWindow != null && !(xBaseWindow instanceof XWindowPeer)) {
/* 1207 */       xBaseWindow = xBaseWindow.getParentWindow();
/*      */     }
/* 1209 */     return (XWindowPeer)xBaseWindow;
/*      */   }
/*      */   public String toString() {
/* 1212 */     return super.toString() + "(" + Long.toString(getWindow(), 16) + ")";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(int paramInt1, int paramInt2) {
/* 1219 */     return (paramInt1 >= 0 && paramInt2 >= 0 && paramInt1 < getWidth() && paramInt2 < getHeight());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsGlobal(int paramInt1, int paramInt2) {
/* 1226 */     return (paramInt1 >= getAbsoluteX() && paramInt2 >= getAbsoluteY() && paramInt1 < getAbsoluteX() + getWidth() && paramInt2 < getAbsoluteY() + getHeight());
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XBaseWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */