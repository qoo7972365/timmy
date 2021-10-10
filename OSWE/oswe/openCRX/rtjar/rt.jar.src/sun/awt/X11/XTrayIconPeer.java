/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.AWTException;
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.PopupMenu;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.SystemTray;
/*     */ import java.awt.TrayIcon;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.peer.TrayIconPeer;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.awt.AWTAccessor;
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
/*     */ public class XTrayIconPeer
/*     */   implements TrayIconPeer, InfoWindow.Balloon.LiveArguments, InfoWindow.Tooltip.LiveArguments
/*     */ {
/*  44 */   private static final PlatformLogger ctrLog = PlatformLogger.getLogger("sun.awt.X11.XTrayIconPeer.centering");
/*     */   TrayIcon target;
/*     */   TrayIconEventProxy eventProxy;
/*     */   XTrayIconEmbeddedFrame eframe;
/*     */   TrayIconCanvas canvas;
/*     */   InfoWindow.Balloon balloon;
/*     */   InfoWindow.Tooltip tooltip;
/*     */   PopupMenu popup;
/*     */   String tooltipString;
/*     */   boolean isTrayIconDisplayed;
/*     */   long eframeParentID;
/*     */   final XEventDispatcher parentXED;
/*     */   final XEventDispatcher eframeXED;
/*     */   
/*  58 */   static final XEventDispatcher dummyXED = new XEventDispatcher()
/*     */     {
/*     */       public void dispatchEvent(XEvent param1XEvent) {}
/*     */     };
/*     */ 
/*     */   
/*     */   volatile boolean isDisposed;
/*     */   boolean isParentWindowLocated;
/*     */   int old_x;
/*     */   int old_y;
/*     */   int ex_width;
/*     */   int ex_height;
/*     */   static final int TRAY_ICON_WIDTH = 24;
/*     */   static final int TRAY_ICON_HEIGHT = 24;
/*     */   
/*     */   XTrayIconPeer(TrayIcon paramTrayIcon) throws AWTException {
/*  74 */     this.target = paramTrayIcon;
/*     */     
/*  76 */     this.eventProxy = new TrayIconEventProxy(this);
/*     */     
/*  78 */     this.canvas = new TrayIconCanvas(paramTrayIcon, 24, 24);
/*     */     
/*  80 */     this.eframe = new XTrayIconEmbeddedFrame();
/*     */     
/*  82 */     this.eframe.setSize(24, 24);
/*  83 */     this.eframe.add(this.canvas);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */           public Object run() {
/*  90 */             XTrayIconPeer.this.eframe.setModalExclusionType(Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);
/*  91 */             return null;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  96 */     if (XWM.getWMID() != 11) {
/*  97 */       this.parentXED = dummyXED;
/*     */     } else {
/*     */       
/* 100 */       this.parentXED = new XEventDispatcher()
/*     */         {
/*     */           public void dispatchEvent(XEvent param1XEvent) {
/* 103 */             if (XTrayIconPeer.this.isDisposed() || param1XEvent.get_type() != 22) {
/*     */               return;
/*     */             }
/*     */             
/* 107 */             XConfigureEvent xConfigureEvent = param1XEvent.get_xconfigure();
/*     */             
/* 109 */             if (XTrayIconPeer.ctrLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 110 */               XTrayIconPeer.ctrLog.fine("ConfigureNotify on parent of {0}: {1}x{2}+{3}+{4} (old: {5}+{6})", new Object[] { this.this$0, 
/* 111 */                     Integer.valueOf(xConfigureEvent.get_width()), Integer.valueOf(xConfigureEvent.get_height()), 
/* 112 */                     Integer.valueOf(xConfigureEvent.get_x()), Integer.valueOf(xConfigureEvent.get_y()), Integer.valueOf(this.this$0.old_x), Integer.valueOf(this.this$0.old_y) });
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 128 */             if (xConfigureEvent.get_height() != 24 && xConfigureEvent.get_width() != 24) {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 133 */               if (XTrayIconPeer.ctrLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 134 */                 XTrayIconPeer.ctrLog.fine("ConfigureNotify on parent of {0}. Skipping as intermediate resizing.", new Object[] { this.this$0 });
/*     */               }
/*     */               
/*     */               return;
/*     */             } 
/* 139 */             if (xConfigureEvent.get_height() > 24) {
/*     */               
/* 141 */               if (XTrayIconPeer.ctrLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 142 */                 XTrayIconPeer.ctrLog.fine("ConfigureNotify on parent of {0}. Centering by \"Y\".", new Object[] { this.this$0 });
/*     */               }
/*     */ 
/*     */               
/* 146 */               XlibWrapper.XMoveResizeWindow(XToolkit.getDisplay(), XTrayIconPeer.this.eframeParentID, xConfigureEvent
/* 147 */                   .get_x(), xConfigureEvent
/* 148 */                   .get_y() + xConfigureEvent.get_height() / 2 - 12, 24, 24);
/*     */ 
/*     */               
/* 151 */               XTrayIconPeer.this.ex_height = xConfigureEvent.get_height();
/* 152 */               XTrayIconPeer.this.ex_width = 0;
/*     */             }
/* 154 */             else if (xConfigureEvent.get_width() > 24) {
/*     */               
/* 156 */               if (XTrayIconPeer.ctrLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 157 */                 XTrayIconPeer.ctrLog.fine("ConfigureNotify on parent of {0}. Centering by \"X\".", new Object[] { this.this$0 });
/*     */               }
/*     */ 
/*     */               
/* 161 */               XlibWrapper.XMoveResizeWindow(XToolkit.getDisplay(), XTrayIconPeer.this.eframeParentID, xConfigureEvent
/* 162 */                   .get_x() + xConfigureEvent.get_width() / 2 - 12, xConfigureEvent
/* 163 */                   .get_y(), 24, 24);
/*     */ 
/*     */               
/* 166 */               XTrayIconPeer.this.ex_width = xConfigureEvent.get_width();
/* 167 */               XTrayIconPeer.this.ex_height = 0;
/*     */             }
/* 169 */             else if (XTrayIconPeer.this.isParentWindowLocated && xConfigureEvent.get_x() != XTrayIconPeer.this.old_x && xConfigureEvent.get_y() != XTrayIconPeer.this.old_y) {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 174 */               if (XTrayIconPeer.this.ex_height != 0) {
/*     */                 
/* 176 */                 if (XTrayIconPeer.ctrLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 177 */                   XTrayIconPeer.ctrLog.fine("ConfigureNotify on parent of {0}. Move detected. Centering by \"Y\".", new Object[] { this.this$0 });
/*     */                 }
/*     */ 
/*     */                 
/* 181 */                 XlibWrapper.XMoveWindow(XToolkit.getDisplay(), XTrayIconPeer.this.eframeParentID, xConfigureEvent
/* 182 */                     .get_x(), xConfigureEvent
/* 183 */                     .get_y() + XTrayIconPeer.this.ex_height / 2 - 12);
/*     */               }
/* 185 */               else if (XTrayIconPeer.this.ex_width != 0) {
/*     */                 
/* 187 */                 if (XTrayIconPeer.ctrLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 188 */                   XTrayIconPeer.ctrLog.fine("ConfigureNotify on parent of {0}. Move detected. Centering by \"X\".", new Object[] { this.this$0 });
/*     */                 }
/*     */ 
/*     */                 
/* 192 */                 XlibWrapper.XMoveWindow(XToolkit.getDisplay(), XTrayIconPeer.this.eframeParentID, xConfigureEvent
/* 193 */                     .get_x() + XTrayIconPeer.this.ex_width / 2 - 12, xConfigureEvent
/* 194 */                     .get_y());
/*     */               }
/* 196 */               else if (XTrayIconPeer.ctrLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 197 */                 XTrayIconPeer.ctrLog.fine("ConfigureNotify on parent of {0}. Move detected. Skipping.", new Object[] { this.this$0 });
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/* 202 */             XTrayIconPeer.this.old_x = xConfigureEvent.get_x();
/* 203 */             XTrayIconPeer.this.old_y = xConfigureEvent.get_y();
/* 204 */             XTrayIconPeer.this.isParentWindowLocated = true;
/*     */           }
/*     */         };
/*     */     } 
/* 208 */     this.eframeXED = new XEventDispatcher()
/*     */       {
/* 210 */         XTrayIconPeer xtiPeer = XTrayIconPeer.this;
/*     */         
/*     */         public void dispatchEvent(XEvent param1XEvent) {
/* 213 */           if (XTrayIconPeer.this.isDisposed() || param1XEvent.get_type() != 21) {
/*     */             return;
/*     */           }
/*     */           
/* 217 */           XReparentEvent xReparentEvent = param1XEvent.get_xreparent();
/* 218 */           XTrayIconPeer.this.eframeParentID = xReparentEvent.get_parent();
/*     */           
/* 220 */           if (XTrayIconPeer.this.eframeParentID == XToolkit.getDefaultRootWindow()) {
/*     */             
/* 222 */             if (XTrayIconPeer.this.isTrayIconDisplayed) {
/* 223 */               SunToolkit.executeOnEventHandlerThread(this.xtiPeer.target, new Runnable() {
/*     */                     public void run() {
/* 225 */                       SystemTray.getSystemTray().remove(XTrayIconPeer.null.this.xtiPeer.target);
/*     */                     }
/*     */                   });
/*     */             }
/*     */             
/*     */             return;
/*     */           } 
/* 232 */           if (!XTrayIconPeer.this.isTrayIconDisplayed) {
/* 233 */             XTrayIconPeer.this.addXED(XTrayIconPeer.this.eframeParentID, XTrayIconPeer.this.parentXED, 131072L);
/*     */             
/* 235 */             XTrayIconPeer.this.isTrayIconDisplayed = true;
/* 236 */             XToolkit.awtLockNotifyAll();
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 241 */     addXED(getWindow(), this.eframeXED, 131072L);
/*     */     
/* 243 */     XSystemTrayPeer.getPeerInstance().addTrayIcon(this);
/*     */ 
/*     */     
/* 246 */     long l1 = System.currentTimeMillis();
/* 247 */     long l2 = XToolkit.getTrayIconDisplayTimeout();
/* 248 */     XToolkit.awtLock();
/*     */     try {
/* 250 */       while (!this.isTrayIconDisplayed) {
/*     */         try {
/* 252 */           XToolkit.awtLockWait(l2);
/* 253 */         } catch (InterruptedException interruptedException) {
/*     */           break;
/*     */         } 
/* 256 */         if (System.currentTimeMillis() - l1 > l2) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } finally {
/* 261 */       XToolkit.awtUnlock();
/*     */     } 
/*     */ 
/*     */     
/* 265 */     if (!this.isTrayIconDisplayed || this.eframeParentID == 0L || this.eframeParentID == 
/* 266 */       XToolkit.getDefaultRootWindow())
/*     */     {
/* 268 */       throw new AWTException("TrayIcon couldn't be displayed.");
/*     */     }
/*     */     
/* 271 */     this.eframe.setVisible(true);
/* 272 */     updateImage();
/*     */     
/* 274 */     this.balloon = new InfoWindow.Balloon(this.eframe, paramTrayIcon, this);
/* 275 */     this.tooltip = new InfoWindow.Tooltip(this.eframe, paramTrayIcon, this);
/*     */     
/* 277 */     addListeners();
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 281 */     if (SunToolkit.isDispatchThreadForAppContext(this.target)) {
/* 282 */       disposeOnEDT();
/*     */     } else {
/*     */       
/* 285 */       try { SunToolkit.executeOnEDTAndWait(this.target, new Runnable() {
/*     */               public void run() {
/* 287 */                 XTrayIconPeer.this.disposeOnEDT();
/*     */               }
/*     */             }); }
/* 290 */       catch (InterruptedException interruptedException) {  }
/* 291 */       catch (InvocationTargetException invocationTargetException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void disposeOnEDT() {
/* 299 */     XToolkit.awtLock();
/* 300 */     this.isDisposed = true;
/* 301 */     XToolkit.awtUnlock();
/*     */     
/* 303 */     removeXED(getWindow(), this.eframeXED);
/* 304 */     removeXED(this.eframeParentID, this.parentXED);
/* 305 */     this.eframe.realDispose();
/* 306 */     this.balloon.dispose();
/* 307 */     this.isTrayIconDisplayed = false;
/* 308 */     XToolkit.targetDisposedPeer(this.target, this);
/*     */   }
/*     */   
/*     */   public static void suppressWarningString(Window paramWindow) {
/* 312 */     AWTAccessor.getWindowAccessor().setTrayIconWindow(paramWindow, true);
/*     */   }
/*     */   
/*     */   public void setToolTip(String paramString) {
/* 316 */     this.tooltipString = paramString;
/*     */   }
/*     */   
/*     */   public String getTooltipString() {
/* 320 */     return this.tooltipString;
/*     */   }
/*     */   
/*     */   public void updateImage() {
/* 324 */     Runnable runnable = new Runnable() {
/*     */         public void run() {
/* 326 */           XTrayIconPeer.this.canvas.updateImage(XTrayIconPeer.this.target.getImage());
/*     */         }
/*     */       };
/*     */     
/* 330 */     if (!SunToolkit.isDispatchThreadForAppContext(this.target)) {
/* 331 */       SunToolkit.executeOnEventHandlerThread(this.target, runnable);
/*     */     } else {
/* 333 */       runnable.run();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void displayMessage(String paramString1, String paramString2, String paramString3) {
/* 338 */     Point point = getLocationOnScreen();
/* 339 */     Rectangle rectangle = this.eframe.getGraphicsConfiguration().getBounds();
/*     */ 
/*     */     
/* 342 */     if (point.x >= rectangle.x && point.x < rectangle.x + rectangle.width && point.y >= rectangle.y && point.y < rectangle.y + rectangle.height)
/*     */     {
/*     */       
/* 345 */       this.balloon.display(paramString1, paramString2, paramString3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void showPopupMenu(int paramInt1, int paramInt2) {
/* 351 */     if (isDisposed()) {
/*     */       return;
/*     */     }
/* 354 */     assert SunToolkit.isDispatchThreadForAppContext(this.target);
/*     */     
/* 356 */     PopupMenu popupMenu = this.target.getPopupMenu();
/* 357 */     if (this.popup != popupMenu) {
/* 358 */       if (this.popup != null) {
/* 359 */         this.eframe.remove(this.popup);
/*     */       }
/* 361 */       if (popupMenu != null) {
/* 362 */         this.eframe.add(popupMenu);
/*     */       }
/* 364 */       this.popup = popupMenu;
/*     */     } 
/*     */     
/* 367 */     if (this.popup != null) {
/* 368 */       Point point = ((XBaseWindow)this.eframe.getPeer()).toLocal(new Point(paramInt1, paramInt2));
/* 369 */       this.popup.show(this.eframe, point.x, point.y);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addXED(long paramLong1, XEventDispatcher paramXEventDispatcher, long paramLong2) {
/* 379 */     if (paramLong1 == 0L) {
/*     */       return;
/*     */     }
/* 382 */     XToolkit.awtLock();
/*     */     try {
/* 384 */       XlibWrapper.XSelectInput(XToolkit.getDisplay(), paramLong1, paramLong2);
/*     */     } finally {
/* 386 */       XToolkit.awtUnlock();
/*     */     } 
/* 388 */     XToolkit.addEventDispatcher(paramLong1, paramXEventDispatcher);
/*     */   }
/*     */   
/*     */   private void removeXED(long paramLong, XEventDispatcher paramXEventDispatcher) {
/* 392 */     if (paramLong == 0L) {
/*     */       return;
/*     */     }
/* 395 */     XToolkit.awtLock();
/*     */     try {
/* 397 */       XToolkit.removeEventDispatcher(paramLong, paramXEventDispatcher);
/*     */     } finally {
/* 399 */       XToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Point getLocationOnScreen() {
/* 405 */     return this.eframe.getLocationOnScreen();
/*     */   }
/*     */   
/*     */   public Rectangle getBounds() {
/* 409 */     Point point = getLocationOnScreen();
/* 410 */     return new Rectangle(point.x, point.y, point.x + 24, point.y + 24);
/*     */   }
/*     */   
/*     */   void addListeners() {
/* 414 */     this.canvas.addMouseListener(this.eventProxy);
/* 415 */     this.canvas.addMouseMotionListener(this.eventProxy);
/*     */   }
/*     */   
/*     */   long getWindow() {
/* 419 */     return ((XEmbeddedFramePeer)this.eframe.getPeer()).getWindow();
/*     */   }
/*     */   
/*     */   public boolean isDisposed() {
/* 423 */     return this.isDisposed;
/*     */   }
/*     */   
/*     */   public String getActionCommand() {
/* 427 */     return this.target.getActionCommand();
/*     */   }
/*     */   
/*     */   static class TrayIconEventProxy implements MouseListener, MouseMotionListener {
/*     */     XTrayIconPeer xtiPeer;
/*     */     
/*     */     TrayIconEventProxy(XTrayIconPeer param1XTrayIconPeer) {
/* 434 */       this.xtiPeer = param1XTrayIconPeer;
/*     */     }
/*     */ 
/*     */     
/*     */     public void handleEvent(MouseEvent param1MouseEvent) {
/* 439 */       if (param1MouseEvent.getID() == 506) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 444 */       if (this.xtiPeer.isDisposed()) {
/*     */         return;
/*     */       }
/* 447 */       Point point = XBaseWindow.toOtherWindow(this.xtiPeer.getWindow(), 
/* 448 */           XToolkit.getDefaultRootWindow(), param1MouseEvent
/* 449 */           .getX(), param1MouseEvent.getY());
/*     */       
/* 451 */       if (param1MouseEvent.isPopupTrigger()) {
/* 452 */         this.xtiPeer.showPopupMenu(point.x, point.y);
/*     */       }
/*     */       
/* 455 */       param1MouseEvent.translatePoint(point.x - param1MouseEvent.getX(), point.y - param1MouseEvent.getY());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 461 */       param1MouseEvent.setSource(this.xtiPeer.target);
/* 462 */       XToolkit.postEvent(XToolkit.targetToAppContext(param1MouseEvent.getSource()), param1MouseEvent);
/*     */     }
/*     */     public void mouseClicked(MouseEvent param1MouseEvent) {
/* 465 */       if ((param1MouseEvent.getClickCount() > 1 || this.xtiPeer.balloon.isVisible()) && param1MouseEvent
/* 466 */         .getButton() == 1) {
/*     */ 
/*     */ 
/*     */         
/* 470 */         ActionEvent actionEvent = new ActionEvent(this.xtiPeer.target, 1001, this.xtiPeer.target.getActionCommand(), param1MouseEvent.getWhen(), param1MouseEvent.getModifiers());
/* 471 */         XToolkit.postEvent(XToolkit.targetToAppContext(actionEvent.getSource()), actionEvent);
/*     */       } 
/* 473 */       if (this.xtiPeer.balloon.isVisible()) {
/* 474 */         this.xtiPeer.balloon.hide();
/*     */       }
/* 476 */       handleEvent(param1MouseEvent);
/*     */     }
/*     */     public void mouseEntered(MouseEvent param1MouseEvent) {
/* 479 */       this.xtiPeer.tooltip.enter();
/* 480 */       handleEvent(param1MouseEvent);
/*     */     }
/*     */     public void mouseExited(MouseEvent param1MouseEvent) {
/* 483 */       this.xtiPeer.tooltip.exit();
/* 484 */       handleEvent(param1MouseEvent);
/*     */     }
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 487 */       handleEvent(param1MouseEvent);
/*     */     }
/*     */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 490 */       handleEvent(param1MouseEvent);
/*     */     }
/*     */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 493 */       handleEvent(param1MouseEvent);
/*     */     }
/*     */     public void mouseMoved(MouseEvent param1MouseEvent) {
/* 496 */       handleEvent(param1MouseEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class XTrayIconEmbeddedFrame
/*     */     extends XEmbeddedFrame
/*     */   {
/*     */     public XTrayIconEmbeddedFrame() {
/* 506 */       super(XToolkit.getDefaultRootWindow(), true, true);
/*     */     }
/*     */     
/*     */     public boolean isUndecorated() {
/* 510 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isResizable() {
/* 514 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void dispose() {}
/*     */ 
/*     */     
/*     */     public void realDispose() {
/* 522 */       super.dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class TrayIconCanvas
/*     */     extends IconCanvas
/*     */   {
/*     */     TrayIcon target;
/*     */     
/*     */     boolean autosize;
/*     */     
/*     */     TrayIconCanvas(TrayIcon param1TrayIcon, int param1Int1, int param1Int2) {
/* 535 */       super(param1Int1, param1Int2);
/* 536 */       this.target = param1TrayIcon;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void repaintImage(boolean param1Boolean) {
/* 541 */       boolean bool = this.autosize;
/* 542 */       this.autosize = this.target.isImageAutoSize();
/*     */       
/* 544 */       this.curW = this.autosize ? this.width : this.image.getWidth(this.observer);
/* 545 */       this.curH = this.autosize ? this.height : this.image.getHeight(this.observer);
/*     */       
/* 547 */       super.repaintImage((param1Boolean || bool != this.autosize));
/*     */     }
/*     */   }
/*     */   
/*     */   public static class IconCanvas
/*     */     extends Canvas {
/*     */     volatile Image image;
/*     */     IconObserver observer;
/*     */     int width;
/*     */     
/*     */     IconCanvas(int param1Int1, int param1Int2) {
/* 558 */       this.width = this.curW = param1Int1;
/* 559 */       this.height = this.curH = param1Int2;
/*     */     }
/*     */     int height; int curW; int curH;
/*     */     
/*     */     public void updateImage(Image param1Image) {
/* 564 */       this.image = param1Image;
/* 565 */       if (this.observer == null) {
/* 566 */         this.observer = new IconObserver();
/*     */       }
/* 568 */       repaintImage(true);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void repaintImage(boolean param1Boolean) {
/* 573 */       Graphics graphics = getGraphics();
/* 574 */       if (graphics != null) {
/*     */         try {
/* 576 */           if (isVisible()) {
/* 577 */             if (param1Boolean) {
/* 578 */               update(graphics);
/*     */             } else {
/* 580 */               paint(graphics);
/*     */             } 
/*     */           }
/*     */         } finally {
/* 584 */           graphics.dispose();
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void paint(Graphics param1Graphics) {
/* 591 */       if (param1Graphics != null && this.curW > 0 && this.curH > 0) {
/* 592 */         BufferedImage bufferedImage = new BufferedImage(this.curW, this.curH, 2);
/* 593 */         Graphics2D graphics2D = bufferedImage.createGraphics();
/* 594 */         if (graphics2D != null)
/*     */           try {
/* 596 */             graphics2D.setColor(getBackground());
/* 597 */             graphics2D.fillRect(0, 0, this.curW, this.curH);
/* 598 */             graphics2D.drawImage(this.image, 0, 0, this.curW, this.curH, this.observer);
/* 599 */             graphics2D.dispose();
/*     */             
/* 601 */             param1Graphics.drawImage(bufferedImage, 0, 0, this.curW, this.curH, null);
/*     */           } finally {
/* 603 */             graphics2D.dispose();
/*     */           }  
/*     */       } 
/*     */     }
/*     */     
/*     */     class IconObserver
/*     */       implements ImageObserver {
/*     */       public boolean imageUpdate(Image param2Image, int param2Int1, int param2Int2, int param2Int3, int param2Int4, int param2Int5) {
/* 611 */         if (param2Image != XTrayIconPeer.IconCanvas.this.image || 
/* 612 */           !XTrayIconPeer.IconCanvas.this.isVisible())
/*     */         {
/* 614 */           return false;
/*     */         }
/* 616 */         if ((param2Int1 & 0x33) != 0)
/*     */         {
/*     */           
/* 619 */           SunToolkit.executeOnEventHandlerThread(XTrayIconPeer.IconCanvas.this, new Runnable() {
/*     */                 public void run() {
/* 621 */                   XTrayIconPeer.IconCanvas.this.repaintImage(false);
/*     */                 }
/*     */               });
/*     */         }
/* 625 */         return ((param2Int1 & 0x20) == 0);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XTrayIconPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */