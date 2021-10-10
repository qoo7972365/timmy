/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.SystemColor;
/*     */ import java.awt.Window;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.lang.ref.WeakReference;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.AWTIcon32_security_icon_bw16_png;
/*     */ import sun.awt.AWTIcon32_security_icon_bw24_png;
/*     */ import sun.awt.AWTIcon32_security_icon_bw32_png;
/*     */ import sun.awt.AWTIcon32_security_icon_bw48_png;
/*     */ import sun.awt.AWTIcon32_security_icon_interim16_png;
/*     */ import sun.awt.AWTIcon32_security_icon_interim24_png;
/*     */ import sun.awt.AWTIcon32_security_icon_interim32_png;
/*     */ import sun.awt.AWTIcon32_security_icon_interim48_png;
/*     */ import sun.awt.AWTIcon32_security_icon_yellow16_png;
/*     */ import sun.awt.AWTIcon32_security_icon_yellow24_png;
/*     */ import sun.awt.AWTIcon32_security_icon_yellow32_png;
/*     */ import sun.awt.AWTIcon32_security_icon_yellow48_png;
/*     */ import sun.awt.AWTIcon64_security_icon_bw16_png;
/*     */ import sun.awt.AWTIcon64_security_icon_bw24_png;
/*     */ import sun.awt.AWTIcon64_security_icon_bw32_png;
/*     */ import sun.awt.AWTIcon64_security_icon_bw48_png;
/*     */ import sun.awt.AWTIcon64_security_icon_interim16_png;
/*     */ import sun.awt.AWTIcon64_security_icon_interim24_png;
/*     */ import sun.awt.AWTIcon64_security_icon_interim32_png;
/*     */ import sun.awt.AWTIcon64_security_icon_interim48_png;
/*     */ import sun.awt.AWTIcon64_security_icon_yellow16_png;
/*     */ import sun.awt.AWTIcon64_security_icon_yellow24_png;
/*     */ import sun.awt.AWTIcon64_security_icon_yellow32_png;
/*     */ import sun.awt.AWTIcon64_security_icon_yellow48_png;
/*     */ import sun.awt.IconInfo;
/*     */ import sun.awt.SunToolkit;
/*     */ 
/*     */ class XWarningWindow
/*     */   extends XWindow {
/*     */   private static final int SHOWING_DELAY = 330;
/*     */   private static final int HIDING_DELAY = 2000;
/*     */   private final Window ownerWindow;
/*     */   private WeakReference<XWindowPeer> ownerPeer;
/*     */   private long parentWindow;
/*     */   private static final String OWNER = "OWNER";
/*     */   private InfoWindow.Tooltip tooltip;
/*  49 */   private volatile int currentIcon = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private int currentSize = -1; private static IconInfo[][] icons; private final Runnable hidingTask; private final Runnable showingTask;
/*     */   
/*     */   private static IconInfo getSecurityIconInfo(int paramInt1, int paramInt2) {
/*  60 */     synchronized (XWarningWindow.class) {
/*  61 */       if (icons == null) {
/*  62 */         icons = new IconInfo[4][3];
/*  63 */         if (XlibWrapper.dataModel == 32) {
/*  64 */           icons[0][0] = new IconInfo(AWTIcon32_security_icon_bw16_png.security_icon_bw16_png);
/*  65 */           icons[0][1] = new IconInfo(AWTIcon32_security_icon_interim16_png.security_icon_interim16_png);
/*  66 */           icons[0][2] = new IconInfo(AWTIcon32_security_icon_yellow16_png.security_icon_yellow16_png);
/*  67 */           icons[1][0] = new IconInfo(AWTIcon32_security_icon_bw24_png.security_icon_bw24_png);
/*  68 */           icons[1][1] = new IconInfo(AWTIcon32_security_icon_interim24_png.security_icon_interim24_png);
/*  69 */           icons[1][2] = new IconInfo(AWTIcon32_security_icon_yellow24_png.security_icon_yellow24_png);
/*  70 */           icons[2][0] = new IconInfo(AWTIcon32_security_icon_bw32_png.security_icon_bw32_png);
/*  71 */           icons[2][1] = new IconInfo(AWTIcon32_security_icon_interim32_png.security_icon_interim32_png);
/*  72 */           icons[2][2] = new IconInfo(AWTIcon32_security_icon_yellow32_png.security_icon_yellow32_png);
/*  73 */           icons[3][0] = new IconInfo(AWTIcon32_security_icon_bw48_png.security_icon_bw48_png);
/*  74 */           icons[3][1] = new IconInfo(AWTIcon32_security_icon_interim48_png.security_icon_interim48_png);
/*  75 */           icons[3][2] = new IconInfo(AWTIcon32_security_icon_yellow48_png.security_icon_yellow48_png);
/*     */         } else {
/*  77 */           icons[0][0] = new IconInfo(AWTIcon64_security_icon_bw16_png.security_icon_bw16_png);
/*  78 */           icons[0][1] = new IconInfo(AWTIcon64_security_icon_interim16_png.security_icon_interim16_png);
/*  79 */           icons[0][2] = new IconInfo(AWTIcon64_security_icon_yellow16_png.security_icon_yellow16_png);
/*  80 */           icons[1][0] = new IconInfo(AWTIcon64_security_icon_bw24_png.security_icon_bw24_png);
/*  81 */           icons[1][1] = new IconInfo(AWTIcon64_security_icon_interim24_png.security_icon_interim24_png);
/*  82 */           icons[1][2] = new IconInfo(AWTIcon64_security_icon_yellow24_png.security_icon_yellow24_png);
/*  83 */           icons[2][0] = new IconInfo(AWTIcon64_security_icon_bw32_png.security_icon_bw32_png);
/*  84 */           icons[2][1] = new IconInfo(AWTIcon64_security_icon_interim32_png.security_icon_interim32_png);
/*  85 */           icons[2][2] = new IconInfo(AWTIcon64_security_icon_yellow32_png.security_icon_yellow32_png);
/*  86 */           icons[3][0] = new IconInfo(AWTIcon64_security_icon_bw48_png.security_icon_bw48_png);
/*  87 */           icons[3][1] = new IconInfo(AWTIcon64_security_icon_interim48_png.security_icon_interim48_png);
/*  88 */           icons[3][2] = new IconInfo(AWTIcon64_security_icon_yellow48_png.security_icon_yellow48_png);
/*     */         } 
/*     */       } 
/*     */     } 
/*  92 */     int i = paramInt1 % icons.length;
/*  93 */     return icons[i][paramInt2 % (icons[i]).length];
/*     */   }
/*     */   
/*     */   private void updateIconSize() {
/*  97 */     byte b = -1;
/*     */     
/*  99 */     if (this.ownerWindow != null) {
/* 100 */       Insets insets = this.ownerWindow.getInsets();
/* 101 */       int i = Math.max(insets.top, Math.max(insets.bottom, 
/* 102 */             Math.max(insets.left, insets.right)));
/* 103 */       if (i < 24) {
/* 104 */         b = 0;
/* 105 */       } else if (i < 32) {
/* 106 */         b = 1;
/* 107 */       } else if (i < 48) {
/* 108 */         b = 2;
/*     */       } else {
/* 110 */         b = 3;
/*     */       } 
/*     */     } 
/*     */     
/* 114 */     if (b == -1) {
/* 115 */       b = 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     XToolkit.awtLock();
/*     */     try {
/* 125 */       if (b != this.currentSize) {
/* 126 */         this.currentSize = b;
/* 127 */         IconInfo iconInfo = getSecurityIconInfo(this.currentSize, 0);
/* 128 */         XlibWrapper.SetBitmapShape(XToolkit.getDisplay(), getWindow(), iconInfo
/* 129 */             .getWidth(), iconInfo.getHeight(), iconInfo.getIntData());
/* 130 */         AWTAccessor.getWindowAccessor().setSecurityWarningSize(this.ownerWindow, iconInfo
/* 131 */             .getWidth(), iconInfo.getHeight());
/*     */       } 
/*     */     } finally {
/* 134 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private IconInfo getSecurityIconInfo() {
/* 139 */     updateIconSize();
/* 140 */     return getSecurityIconInfo(this.currentSize, this.currentIcon);
/*     */   }
/*     */   
/*     */   XWarningWindow(Window paramWindow, long paramLong, XWindowPeer paramXWindowPeer) {
/* 144 */     super(new XCreateWindowParams(new Object[] { "target", paramWindow, "OWNER", 
/*     */             
/* 146 */             Long.valueOf(paramLong) }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 365 */     this.hidingTask = new Runnable() {
/*     */         public void run() {
/* 367 */           XWarningWindow.this.xSetVisible(false);
/*     */         }
/*     */       };
/*     */     
/* 371 */     this.showingTask = new Runnable()
/*     */       {
/* 373 */         public boolean isDisposed() { return XWarningWindow.this.isDisposed(); } public void run() { if (!XWarningWindow.this.isVisible()) {
/* 374 */             XWarningWindow.this.xSetVisible(true);
/* 375 */             XWarningWindow.this.updateIconSize();
/* 376 */             XWindowPeer xWindowPeer = XWarningWindow.this.ownerPeer.get();
/* 377 */             if (xWindowPeer != null) {
/* 378 */               xWindowPeer.repositionSecurityWarning();
/*     */             }
/*     */           } 
/* 381 */           XWarningWindow.this.repaint();
/* 382 */           if (XWarningWindow.this.currentIcon > 0) {
/* 383 */             XWarningWindow.this.currentIcon--;
/* 384 */             XToolkit.schedule(XWarningWindow.this.showingTask, 330L);
/*     */           }  }
/*     */       }; this.ownerWindow = paramWindow;
/*     */     this.parentWindow = paramLong;
/*     */     this.tooltip = new InfoWindow.Tooltip(null, getTarget(), new InfoWindow.Tooltip.LiveArguments() { public Rectangle getBounds() { return XWarningWindow.this.getBounds(); } public String getTooltipString() { return XWarningWindow.this.ownerWindow.getWarningString(); } });
/*     */     this.ownerPeer = new WeakReference<>(paramXWindowPeer);
/* 390 */   } public void setSecurityWarningVisible(boolean paramBoolean1, boolean paramBoolean2) { if (paramBoolean1) {
/* 391 */       XToolkit.remove(this.hidingTask);
/* 392 */       XToolkit.remove(this.showingTask);
/* 393 */       if (isVisible()) {
/* 394 */         this.currentIcon = 0;
/*     */       } else {
/* 396 */         this.currentIcon = 3;
/*     */       } 
/* 398 */       if (paramBoolean2) {
/* 399 */         XToolkit.schedule(this.showingTask, 1L);
/*     */       } else {
/* 401 */         this.showingTask.run();
/*     */       } 
/*     */     } else {
/* 404 */       XToolkit.remove(this.showingTask);
/* 405 */       XToolkit.remove(this.hidingTask);
/* 406 */       if (!isVisible()) {
/*     */         return;
/*     */       }
/* 409 */       if (paramBoolean2) {
/* 410 */         XToolkit.schedule(this.hidingTask, 2000L);
/*     */       } else {
/* 412 */         this.hidingTask.run();
/*     */       } 
/*     */     }  }
/*     */ 
/*     */   
/*     */   private void requestNoTaskbar() {
/*     */     XNETProtocol xNETProtocol = XWM.getWM().getNETProtocol();
/*     */     if (xNETProtocol != null)
/*     */       xNETProtocol.requestState(this, xNETProtocol.XA_NET_WM_STATE_SKIP_TASKBAR, true); 
/*     */   }
/*     */   
/*     */   void postInit(XCreateWindowParams paramXCreateWindowParams) {
/*     */     super.postInit(paramXCreateWindowParams);
/*     */     XToolkit.awtLock();
/*     */     try {
/*     */       XWM.setMotifDecor(this, false, 0, 0);
/*     */       XWM.setOLDecor(this, false, 0);
/*     */       long l = ((Long)paramXCreateWindowParams.get("OWNER")).longValue();
/*     */       XlibWrapper.XSetTransientFor(XToolkit.getDisplay(), getWindow(), l);
/*     */       XWMHints xWMHints = getWMHints();
/*     */       xWMHints.set_flags(xWMHints.get_flags() | 0x1L | 0x2L);
/*     */       xWMHints.set_input(false);
/*     */       xWMHints.set_initial_state(1);
/*     */       XlibWrapper.XSetWMHints(XToolkit.getDisplay(), getWindow(), xWMHints.pData);
/*     */       initWMProtocols();
/*     */       requestNoTaskbar();
/*     */     } finally {
/*     */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reposition(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*     */     Point2D point2D = AWTAccessor.getWindowAccessor().calculateSecurityWarningPosition(this.ownerWindow, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     reshape((int)point2D.getX(), (int)point2D.getY(), getWidth(), getHeight());
/*     */   }
/*     */   
/*     */   protected String getWMName() {
/*     */     return "Warning window";
/*     */   }
/*     */   
/*     */   public Graphics getGraphics() {
/*     */     if (this.surfaceData == null || this.ownerWindow == null)
/*     */       return null; 
/*     */     return getGraphics(this.surfaceData, getColor(), getBackground(), getFont());
/*     */   }
/*     */   
/*     */   void paint(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*     */     paramGraphics.drawImage(getSecurityIconInfo().getImage(), 0, 0, null);
/*     */   }
/*     */   
/*     */   String getWarningString() {
/*     */     return this.ownerWindow.getWarningString();
/*     */   }
/*     */   
/*     */   int getWidth() {
/*     */     return getSecurityIconInfo().getWidth();
/*     */   }
/*     */   
/*     */   int getHeight() {
/*     */     return getSecurityIconInfo().getHeight();
/*     */   }
/*     */   
/*     */   Color getBackground() {
/*     */     return SystemColor.window;
/*     */   }
/*     */   
/*     */   Color getColor() {
/*     */     return Color.black;
/*     */   }
/*     */   
/*     */   Font getFont() {
/*     */     return this.ownerWindow.getFont();
/*     */   }
/*     */   
/*     */   public void repaint() {
/*     */     Rectangle rectangle = getBounds();
/*     */     Graphics graphics = getGraphics();
/*     */     if (graphics != null)
/*     */       try {
/*     */         paint(graphics, 0, 0, rectangle.width, rectangle.height);
/*     */       } finally {
/*     */         graphics.dispose();
/*     */       }  
/*     */   }
/*     */   
/*     */   public void handleExposeEvent(XEvent paramXEvent) {
/*     */     super.handleExposeEvent(paramXEvent);
/*     */     XExposeEvent xExposeEvent = paramXEvent.get_xexpose();
/*     */     final int x = xExposeEvent.get_x();
/*     */     final int y = xExposeEvent.get_y();
/*     */     final int width = xExposeEvent.get_width();
/*     */     final int height = xExposeEvent.get_height();
/*     */     SunToolkit.executeOnEventHandlerThread(this.target, new Runnable() {
/*     */           public void run() {
/*     */             Graphics graphics = XWarningWindow.this.getGraphics();
/*     */             if (graphics != null)
/*     */               try {
/*     */                 XWarningWindow.this.paint(graphics, x, y, width, height);
/*     */               } finally {
/*     */                 graphics.dispose();
/*     */               }  
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   protected boolean isEventDisabled(XEvent paramXEvent) {
/*     */     return true;
/*     */   }
/*     */   
/*     */   private void withdraw() {
/*     */     XEvent xEvent = new XEvent();
/*     */     try {
/*     */       long l;
/*     */       XToolkit.awtLock();
/*     */       try {
/*     */         l = XlibWrapper.RootWindow(XToolkit.getDisplay(), getScreenNumber());
/*     */       } finally {
/*     */         XToolkit.awtUnlock();
/*     */       } 
/*     */       xEvent.set_type(18);
/*     */       XUnmapEvent xUnmapEvent = xEvent.get_xunmap();
/*     */       xUnmapEvent.set_event(l);
/*     */       xUnmapEvent.set_window(getWindow());
/*     */       xUnmapEvent.set_from_configure(false);
/*     */       XToolkit.awtLock();
/*     */       try {
/*     */         XlibWrapper.XSendEvent(XToolkit.getDisplay(), l, false, 1572864L, xEvent.pData);
/*     */       } finally {
/*     */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } finally {
/*     */       xEvent.dispose();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void stateChanged(long paramLong, int paramInt1, int paramInt2) {
/*     */     if (paramInt2 == 3) {
/*     */       super.xSetVisible(false);
/*     */       withdraw();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setMouseAbove(boolean paramBoolean) {
/*     */     super.setMouseAbove(paramBoolean);
/*     */     XWindowPeer xWindowPeer = this.ownerPeer.get();
/*     */     if (xWindowPeer != null)
/*     */       xWindowPeer.updateSecurityWarningVisibility(); 
/*     */   }
/*     */   
/*     */   protected void enterNotify(long paramLong) {
/*     */     super.enterNotify(paramLong);
/*     */     if (paramLong == getWindow())
/*     */       this.tooltip.enter(); 
/*     */   }
/*     */   
/*     */   protected void leaveNotify(long paramLong) {
/*     */     super.leaveNotify(paramLong);
/*     */     if (paramLong == getWindow())
/*     */       this.tooltip.exit(); 
/*     */   }
/*     */   
/*     */   public void xSetVisible(boolean paramBoolean) {
/*     */     super.xSetVisible(paramBoolean);
/*     */     requestNoTaskbar();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XWarningWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */