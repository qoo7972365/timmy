/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.AWTKeyStroke;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.MenuBar;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Window;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import sun.awt.EmbeddedFrame;
/*     */ import sun.awt.IconInfo;
/*     */ import sun.awt.SunToolkit;
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
/*     */ public class XEmbeddedFramePeer
/*     */   extends XFramePeer
/*     */ {
/*  42 */   private static final PlatformLogger xembedLog = PlatformLogger.getLogger("sun.awt.X11.xembed.XEmbeddedFramePeer");
/*     */ 
/*     */   
/*     */   LinkedList<AWTKeyStroke> strokes;
/*     */   
/*     */   XEmbedClientHelper embedder;
/*     */ 
/*     */   
/*     */   public XEmbeddedFramePeer(EmbeddedFrame paramEmbeddedFrame) {
/*  51 */     super(new XCreateWindowParams(new Object[] { "target", paramEmbeddedFrame, "visible", Boolean.TRUE, "embedded", Boolean.TRUE }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preInit(XCreateWindowParams paramXCreateWindowParams) {
/*  58 */     super.preInit(paramXCreateWindowParams);
/*  59 */     this.strokes = new LinkedList<>();
/*  60 */     if (supportsXEmbed())
/*  61 */       this.embedder = new XEmbedClientHelper(); 
/*     */   }
/*     */   
/*     */   void postInit(XCreateWindowParams paramXCreateWindowParams) {
/*  65 */     super.postInit(paramXCreateWindowParams);
/*  66 */     if (this.embedder != null) {
/*     */       
/*  68 */       this.embedder.setClient(this);
/*     */       
/*  70 */       this.embedder.install();
/*  71 */     } else if (getParentWindowHandle() != 0L) {
/*  72 */       XToolkit.awtLock();
/*     */       try {
/*  74 */         XlibWrapper.XReparentWindow(XToolkit.getDisplay(), 
/*  75 */             getWindow(), 
/*  76 */             getParentWindowHandle(), 0, 0);
/*     */       } finally {
/*     */         
/*  79 */         XToolkit.awtUnlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/*  86 */     if (this.embedder != null)
/*     */     {
/*  88 */       this.embedder.setClient(null);
/*     */     }
/*  90 */     super.dispose();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateMinimumSize() {}
/*     */   
/*     */   protected String getWMName() {
/*  97 */     return "JavaEmbeddedFrame";
/*     */   }
/*     */   
/*     */   final long getParentWindowHandle() {
/* 101 */     return ((XEmbeddedFrame)this.target).handle;
/*     */   }
/*     */   
/*     */   boolean supportsXEmbed() {
/* 105 */     return ((EmbeddedFrame)this.target).supportsXEmbed();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requestWindowFocus(long paramLong, boolean paramBoolean) {
/* 110 */     if (this.embedder != null && this.embedder.isActive()) {
/* 111 */       xembedLog.fine("Requesting focus from embedding host");
/* 112 */       return this.embedder.requestFocus();
/*     */     } 
/* 114 */     xembedLog.fine("Requesting focus from X");
/* 115 */     return super.requestWindowFocus(paramLong, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void requestInitialFocus() {
/* 120 */     if (this.embedder != null && supportsXEmbed()) {
/* 121 */       this.embedder.requestFocus();
/*     */     } else {
/* 123 */       super.requestInitialFocus();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isEventDisabled(XEvent paramXEvent) {
/* 128 */     if (this.embedder != null && this.embedder.isActive()) {
/* 129 */       switch (paramXEvent.get_type()) {
/*     */         case 9:
/*     */         case 10:
/* 132 */           return true;
/*     */       } 
/*     */     }
/* 135 */     return super.isEventDisabled(paramXEvent);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleConfigureNotifyEvent(XEvent paramXEvent) {
/* 140 */     assert SunToolkit.isAWTLockHeldByCurrentThread();
/* 141 */     XConfigureEvent xConfigureEvent = paramXEvent.get_xconfigure();
/* 142 */     if (xembedLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 143 */       xembedLog.fine(xConfigureEvent.toString());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     checkIfOnNewScreen(toGlobal(new Rectangle(xConfigureEvent.get_x(), xConfigureEvent
/* 150 */             .get_y(), xConfigureEvent
/* 151 */             .get_width(), xConfigureEvent
/* 152 */             .get_height())));
/*     */     
/* 154 */     Rectangle rectangle = getBounds();
/*     */     
/* 156 */     synchronized (getStateLock()) {
/* 157 */       this.x = xConfigureEvent.get_x();
/* 158 */       this.y = xConfigureEvent.get_y();
/* 159 */       this.width = xConfigureEvent.get_width();
/* 160 */       this.height = xConfigureEvent.get_height();
/*     */       
/* 162 */       this.dimensions.setClientSize(this.width, this.height);
/* 163 */       this.dimensions.setLocation(this.x, this.y);
/*     */     } 
/*     */     
/* 166 */     if (!getLocation().equals(rectangle.getLocation())) {
/* 167 */       handleMoved(this.dimensions);
/*     */     }
/* 169 */     reconfigureContentWindow(this.dimensions);
/*     */   }
/*     */   
/*     */   protected void traverseOutForward() {
/* 173 */     if (this.embedder != null && this.embedder.isActive() && 
/* 174 */       this.embedder.isApplicationActive()) {
/* 175 */       xembedLog.fine("Traversing out Forward");
/* 176 */       this.embedder.traverseOutForward();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void traverseOutBackward() {
/* 182 */     if (this.embedder != null && this.embedder.isActive() && 
/* 183 */       this.embedder.isApplicationActive()) {
/* 184 */       xembedLog.fine("Traversing out Backward");
/* 185 */       this.embedder.traverseOutBackward();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getLocationOnScreen() {
/* 192 */     XToolkit.awtLock();
/*     */     try {
/* 194 */       return toGlobal(0, 0);
/*     */     } finally {
/* 196 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Rectangle getBounds() {
/* 202 */     return new Rectangle(this.x, this.y, this.width, this.height);
/*     */   }
/*     */   
/*     */   public void setBoundsPrivate(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 206 */     setBounds(paramInt1, paramInt2, paramInt3, paramInt4, 16387);
/*     */   }
/*     */   
/*     */   public Rectangle getBoundsPrivate() {
/* 210 */     int i = 0, j = 0;
/* 211 */     int k = 0, m = 0;
/* 212 */     XWindowAttributes xWindowAttributes = new XWindowAttributes();
/*     */     
/* 214 */     XToolkit.awtLock();
/*     */     try {
/* 216 */       XlibWrapper.XGetWindowAttributes(XToolkit.getDisplay(), 
/* 217 */           getWindow(), xWindowAttributes.pData);
/* 218 */       i = xWindowAttributes.get_x();
/* 219 */       j = xWindowAttributes.get_y();
/* 220 */       k = xWindowAttributes.get_width();
/* 221 */       m = xWindowAttributes.get_height();
/*     */     } finally {
/* 223 */       XToolkit.awtUnlock();
/*     */     } 
/* 225 */     xWindowAttributes.dispose();
/*     */     
/* 227 */     return new Rectangle(i, j, k, m);
/*     */   }
/*     */   void registerAccelerator(AWTKeyStroke paramAWTKeyStroke) {
/* 230 */     if (paramAWTKeyStroke == null)
/* 231 */       return;  this.strokes.add(paramAWTKeyStroke);
/* 232 */     if (this.embedder != null && this.embedder.isActive()) {
/* 233 */       this.embedder.registerAccelerator(paramAWTKeyStroke, this.strokes.size() - 1);
/*     */     }
/*     */   }
/*     */   
/*     */   void unregisterAccelerator(AWTKeyStroke paramAWTKeyStroke) {
/* 238 */     if (paramAWTKeyStroke == null)
/* 239 */       return;  if (this.embedder != null && this.embedder.isActive()) {
/* 240 */       int i = this.strokes.indexOf(paramAWTKeyStroke);
/* 241 */       this.embedder.unregisterAccelerator(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void notifyStarted() {
/* 247 */     if (this.embedder != null && this.embedder.isActive()) {
/* 248 */       byte b = 0;
/* 249 */       Iterator<AWTKeyStroke> iterator = this.strokes.iterator();
/* 250 */       while (iterator.hasNext()) {
/* 251 */         this.embedder.registerAccelerator(iterator.next(), b++);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 257 */     updateDropTarget();
/*     */   }
/*     */   void notifyStopped() {
/* 260 */     if (this.embedder != null && this.embedder.isActive()) {
/* 261 */       for (int i = this.strokes.size() - 1; i >= 0; i--) {
/* 262 */         this.embedder.unregisterAccelerator(i);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   long getFocusTargetWindow() {
/* 268 */     return getWindow();
/*     */   }
/*     */   
/*     */   boolean isXEmbedActive() {
/* 272 */     return (this.embedder != null && this.embedder.isActive());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAbsoluteX() {
/* 277 */     Point point = XlibUtil.translateCoordinates(getWindow(), 
/* 278 */         XToolkit.getDefaultRootWindow(), new Point(0, 0));
/*     */     
/* 280 */     return (point != null) ? point.x : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAbsoluteY() {
/* 285 */     Point point = XlibUtil.translateCoordinates(getWindow(), 
/* 286 */         XToolkit.getDefaultRootWindow(), new Point(0, 0));
/*     */     
/* 288 */     return (point != null) ? point.y : 0;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 292 */     return this.width;
/*     */   }
/*     */   public int getHeight() {
/* 295 */     return this.height;
/*     */   }
/*     */   
/*     */   public Dimension getSize() {
/* 299 */     return new Dimension(this.width, this.height);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModalBlocked(Dialog paramDialog, boolean paramBoolean) {
/* 305 */     super.setModalBlocked(paramDialog, paramBoolean);
/*     */     
/* 307 */     EmbeddedFrame embeddedFrame = (EmbeddedFrame)this.target;
/* 308 */     embeddedFrame.notifyModalBlocked(paramDialog, paramBoolean);
/*     */   }
/*     */   
/*     */   public void synthesizeFocusInOut(boolean paramBoolean) {
/* 312 */     XFocusChangeEvent xFocusChangeEvent = new XFocusChangeEvent();
/*     */     
/* 314 */     XToolkit.awtLock();
/*     */     try {
/* 316 */       xFocusChangeEvent.set_type(paramBoolean ? 9 : 10);
/* 317 */       xFocusChangeEvent.set_window(getFocusProxy().getWindow());
/* 318 */       xFocusChangeEvent.set_mode(0);
/* 319 */       XlibWrapper.XSendEvent(XToolkit.getDisplay(), getFocusProxy().getWindow(), false, 0L, xFocusChangeEvent.pData);
/*     */     } finally {
/*     */       
/* 322 */       XToolkit.awtUnlock();
/* 323 */       xFocusChangeEvent.dispose();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XEmbeddedFramePeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */