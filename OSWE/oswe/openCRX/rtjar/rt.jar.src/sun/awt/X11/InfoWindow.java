/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Button;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Image;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Label;
/*     */ import java.awt.MouseInfo;
/*     */ import java.awt.Panel;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.text.BreakIterator;
/*     */ import java.util.concurrent.ArrayBlockingQueue;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.UNIXToolkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InfoWindow
/*     */   extends Window
/*     */ {
/*     */   private Container container;
/*     */   private Closer closer;
/*     */   
/*     */   protected InfoWindow(Frame paramFrame, Color paramColor) {
/*  47 */     super(paramFrame);
/*  48 */     setType(Window.Type.POPUP);
/*  49 */     this.container = new Container()
/*     */       {
/*     */         public Insets getInsets() {
/*  52 */           return new Insets(1, 1, 1, 1);
/*     */         }
/*     */       };
/*  55 */     setLayout(new BorderLayout());
/*  56 */     setBackground(paramColor);
/*  57 */     add(this.container, "Center");
/*  58 */     this.container.setLayout(new BorderLayout());
/*     */     
/*  60 */     this.closer = new Closer();
/*     */   }
/*     */   
/*     */   public Component add(Component paramComponent) {
/*  64 */     this.container.add(paramComponent, "Center");
/*  65 */     return paramComponent;
/*     */   }
/*     */   
/*     */   protected void setCloser(Runnable paramRunnable, int paramInt) {
/*  69 */     this.closer.set(paramRunnable, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void show(Point paramPoint, int paramInt) {
/*  74 */     assert SunToolkit.isDispatchThreadForAppContext(this);
/*     */     
/*  76 */     pack();
/*     */     
/*  78 */     Dimension dimension1 = getSize();
/*     */ 
/*     */     
/*  81 */     Dimension dimension2 = Toolkit.getDefaultToolkit().getScreenSize();
/*     */     
/*  83 */     if (paramPoint.x < dimension2.width / 2 && paramPoint.y < dimension2.height / 2) {
/*  84 */       setLocation(paramPoint.x + paramInt, paramPoint.y + paramInt);
/*     */     }
/*  86 */     else if (paramPoint.x >= dimension2.width / 2 && paramPoint.y < dimension2.height / 2) {
/*  87 */       setLocation(paramPoint.x - paramInt - dimension1.width, paramPoint.y + paramInt);
/*     */     }
/*  89 */     else if (paramPoint.x < dimension2.width / 2 && paramPoint.y >= dimension2.height / 2) {
/*  90 */       setLocation(paramPoint.x + paramInt, paramPoint.y - paramInt - dimension1.height);
/*     */     }
/*  92 */     else if (paramPoint.x >= dimension2.width / 2 && paramPoint.y >= dimension2.height / 2) {
/*  93 */       setLocation(paramPoint.x - paramInt - dimension1.width, paramPoint.y - paramInt - dimension1.height);
/*     */     } 
/*     */     
/*  96 */     show();
/*  97 */     this.closer.schedule();
/*     */   }
/*     */   
/*     */   public void hide() {
/* 101 */     this.closer.close();
/*     */   }
/*     */   private class Closer implements Runnable { Runnable action;
/*     */     int time;
/*     */     
/*     */     private Closer() {}
/*     */     
/*     */     public void run() {
/* 109 */       doClose();
/*     */     }
/*     */     
/*     */     void set(Runnable param1Runnable, int param1Int) {
/* 113 */       this.action = param1Runnable;
/* 114 */       this.time = param1Int;
/*     */     }
/*     */     
/*     */     void schedule() {
/* 118 */       XToolkit.schedule(this, this.time);
/*     */     }
/*     */     
/*     */     void close() {
/* 122 */       XToolkit.remove(this);
/* 123 */       doClose();
/*     */     }
/*     */ 
/*     */     
/*     */     private void doClose() {
/* 128 */       SunToolkit.executeOnEventHandlerThread(InfoWindow.this, new Runnable() {
/*     */             public void run() {
/* 130 */               InfoWindow.this.hide();
/* 131 */               InfoWindow.this.invalidate();
/* 132 */               if (InfoWindow.Closer.this.action != null) {
/* 133 */                 InfoWindow.Closer.this.action.run();
/*     */               }
/*     */             }
/*     */           });
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static interface LiveArguments
/*     */   {
/*     */     boolean isDisposed();
/*     */ 
/*     */ 
/*     */     
/*     */     Rectangle getBounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Tooltip
/*     */     extends InfoWindow
/*     */   {
/*     */     private final Object target;
/*     */     
/*     */     private final LiveArguments liveArguments;
/*     */     
/* 159 */     private final Label textLabel = new Label("");
/* 160 */     private final Runnable starter = new Runnable() {
/*     */         public void run() {
/* 162 */           InfoWindow.Tooltip.this.display();
/*     */         }
/*     */       };
/*     */     private static final int TOOLTIP_SHOW_TIME = 10000;
/*     */     private static final int TOOLTIP_START_DELAY_TIME = 1000;
/*     */     private static final int TOOLTIP_MAX_LENGTH = 64;
/*     */     private static final int TOOLTIP_MOUSE_CURSOR_INDENT = 5;
/* 169 */     private static final Color TOOLTIP_BACKGROUND_COLOR = new Color(255, 255, 220);
/* 170 */     private static final Font TOOLTIP_TEXT_FONT = XWindow.getDefaultFont();
/*     */ 
/*     */ 
/*     */     
/*     */     public Tooltip(Frame param1Frame, Object param1Object, LiveArguments param1LiveArguments) {
/* 175 */       super(param1Frame, Color.black);
/*     */       
/* 177 */       this.target = param1Object;
/* 178 */       this.liveArguments = param1LiveArguments;
/*     */       
/* 180 */       XTrayIconPeer.suppressWarningString(this);
/*     */       
/* 182 */       setCloser((Runnable)null, 10000);
/* 183 */       this.textLabel.setBackground(TOOLTIP_BACKGROUND_COLOR);
/* 184 */       this.textLabel.setFont(TOOLTIP_TEXT_FONT);
/* 185 */       add(this.textLabel);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void display() {
/* 193 */       SunToolkit.executeOnEventHandlerThread(this.target, new Runnable() {
/*     */             public void run() {
/* 195 */               if (InfoWindow.Tooltip.this.liveArguments.isDisposed()) {
/*     */                 return;
/*     */               }
/*     */               
/* 199 */               String str = InfoWindow.Tooltip.this.liveArguments.getTooltipString();
/* 200 */               if (str == null)
/*     */                 return; 
/* 202 */               if (str.length() > 64) {
/* 203 */                 InfoWindow.Tooltip.this.textLabel.setText(str.substring(0, 64));
/*     */               } else {
/* 205 */                 InfoWindow.Tooltip.this.textLabel.setText(str);
/*     */               } 
/*     */               
/* 208 */               Point point = AccessController.<Point>doPrivileged(new PrivilegedAction<Point>() {
/*     */                     public Object run() {
/* 210 */                       if (!InfoWindow.Tooltip.this.isPointerOverTrayIcon(InfoWindow.Tooltip.this.liveArguments.getBounds())) {
/* 211 */                         return null;
/*     */                       }
/* 213 */                       return MouseInfo.getPointerInfo().getLocation();
/*     */                     }
/*     */                   });
/* 216 */               if (point == null) {
/*     */                 return;
/*     */               }
/* 219 */               InfoWindow.Tooltip.this.show(new Point(point.x, point.y), 5);
/*     */             }
/*     */           });
/*     */     }
/*     */     
/*     */     public void enter() {
/* 225 */       XToolkit.schedule(this.starter, 1000L);
/*     */     }
/*     */     
/*     */     public void exit() {
/* 229 */       XToolkit.remove(this.starter);
/* 230 */       if (isVisible()) {
/* 231 */         hide();
/*     */       }
/*     */     }
/*     */     
/*     */     private boolean isPointerOverTrayIcon(Rectangle param1Rectangle) {
/* 236 */       Point point = MouseInfo.getPointerInfo().getLocation();
/* 237 */       return (point.x >= param1Rectangle.x && point.x <= param1Rectangle.x + param1Rectangle.width && point.y >= param1Rectangle.y && point.y <= param1Rectangle.y + param1Rectangle.height);
/*     */     }
/*     */     
/*     */     public static interface LiveArguments
/*     */       extends InfoWindow.LiveArguments
/*     */     {
/*     */       String getTooltipString();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Balloon
/*     */     extends InfoWindow
/*     */   {
/*     */     private final LiveArguments liveArguments;
/*     */     private final Object target;
/*     */     private static final int BALLOON_SHOW_TIME = 10000;
/*     */     private static final int BALLOON_TEXT_MAX_LENGTH = 256;
/*     */     private static final int BALLOON_WORD_LINE_MAX_LENGTH = 16;
/*     */     private static final int BALLOON_WORD_LINE_MAX_COUNT = 4;
/*     */     private static final int BALLOON_ICON_WIDTH = 32;
/*     */     private static final int BALLOON_ICON_HEIGHT = 32;
/*     */     private static final int BALLOON_TRAY_ICON_INDENT = 0;
/* 259 */     private static final Color BALLOON_CAPTION_BACKGROUND_COLOR = new Color(200, 200, 255);
/* 260 */     private static final Font BALLOON_CAPTION_FONT = new Font("Dialog", 1, 12);
/*     */     
/* 262 */     private Panel mainPanel = new Panel();
/* 263 */     private Panel captionPanel = new Panel();
/* 264 */     private Label captionLabel = new Label("");
/* 265 */     private Button closeButton = new Button("X");
/* 266 */     private Panel textPanel = new Panel();
/* 267 */     private XTrayIconPeer.IconCanvas iconCanvas = new XTrayIconPeer.IconCanvas(32, 32);
/* 268 */     private Label[] lineLabels = new Label[4];
/* 269 */     private ActionPerformer ap = new ActionPerformer();
/*     */     
/*     */     private Image iconImage;
/*     */     
/*     */     private Image errorImage;
/*     */     private Image warnImage;
/*     */     private Image infoImage;
/*     */     private boolean gtkImagesLoaded;
/* 277 */     private Displayer displayer = new Displayer();
/*     */     
/*     */     public Balloon(Frame param1Frame, Object param1Object, LiveArguments param1LiveArguments) {
/* 280 */       super(param1Frame, new Color(90, 80, 190));
/* 281 */       this.liveArguments = param1LiveArguments;
/* 282 */       this.target = param1Object;
/*     */       
/* 284 */       XTrayIconPeer.suppressWarningString(this);
/*     */       
/* 286 */       setCloser(new Runnable() {
/*     */             public void run() {
/* 288 */               if (InfoWindow.Balloon.this.textPanel != null) {
/* 289 */                 InfoWindow.Balloon.this.textPanel.removeAll();
/* 290 */                 InfoWindow.Balloon.this.textPanel.setSize(0, 0);
/* 291 */                 InfoWindow.Balloon.this.iconCanvas.setSize(0, 0);
/* 292 */                 XToolkit.awtLock();
/*     */                 try {
/* 294 */                   InfoWindow.Balloon.this.displayer.isDisplayed = false;
/* 295 */                   XToolkit.awtLockNotifyAll();
/*     */                 } finally {
/* 297 */                   XToolkit.awtUnlock();
/*     */                 } 
/*     */               } 
/*     */             }
/*     */           }10000);
/*     */       
/* 303 */       add(this.mainPanel);
/*     */       
/* 305 */       this.captionLabel.setFont(BALLOON_CAPTION_FONT);
/* 306 */       this.captionLabel.addMouseListener(this.ap);
/*     */       
/* 308 */       this.captionPanel.setLayout(new BorderLayout());
/* 309 */       this.captionPanel.add(this.captionLabel, "West");
/* 310 */       this.captionPanel.add(this.closeButton, "East");
/* 311 */       this.captionPanel.setBackground(BALLOON_CAPTION_BACKGROUND_COLOR);
/* 312 */       this.captionPanel.addMouseListener(this.ap);
/*     */       
/* 314 */       this.closeButton.addActionListener(new ActionListener() {
/*     */             public void actionPerformed(ActionEvent param2ActionEvent) {
/* 316 */               InfoWindow.Balloon.this.hide();
/*     */             }
/*     */           });
/*     */       
/* 320 */       this.mainPanel.setLayout(new BorderLayout());
/* 321 */       this.mainPanel.setBackground(Color.white);
/* 322 */       this.mainPanel.add(this.captionPanel, "North");
/* 323 */       this.mainPanel.add(this.iconCanvas, "West");
/* 324 */       this.mainPanel.add(this.textPanel, "Center");
/*     */       
/* 326 */       this.iconCanvas.addMouseListener(this.ap);
/*     */       
/* 328 */       for (byte b = 0; b < 4; b++) {
/* 329 */         this.lineLabels[b] = new Label();
/* 330 */         this.lineLabels[b].addMouseListener(this.ap);
/* 331 */         this.lineLabels[b].setBackground(Color.white);
/*     */       } 
/*     */       
/* 334 */       this.displayer.start();
/*     */     }
/*     */     
/*     */     public void display(String param1String1, String param1String2, String param1String3) {
/* 338 */       if (!this.gtkImagesLoaded) {
/* 339 */         loadGtkImages();
/*     */       }
/* 341 */       this.displayer.display(param1String1, param1String2, param1String3);
/*     */     }
/*     */     
/*     */     private void _display(String param1String1, String param1String2, String param1String3) {
/* 345 */       this.captionLabel.setText(param1String1);
/*     */       
/* 347 */       BreakIterator breakIterator = BreakIterator.getWordInstance();
/* 348 */       if (param1String2 != null) {
/* 349 */         int j; breakIterator.setText(param1String2);
/* 350 */         int i = breakIterator.first();
/* 351 */         byte b = 0;
/*     */         
/*     */         do {
/* 354 */           j = breakIterator.next();
/*     */           
/* 356 */           if (j == -1 || param1String2
/* 357 */             .substring(i, j).length() >= 50) {
/*     */             
/* 359 */             this.lineLabels[b].setText(param1String2.substring(i, (j == -1) ? breakIterator
/* 360 */                   .last() : j));
/* 361 */             this.textPanel.add(this.lineLabels[b++]);
/* 362 */             i = j;
/*     */           } 
/* 364 */           if (b == 4) {
/* 365 */             if (j != -1) {
/* 366 */               this.lineLabels[b - 1].setText(new String(this.lineLabels[b - 1]
/* 367 */                     .getText() + " ..."));
/*     */             }
/*     */             break;
/*     */           } 
/* 371 */         } while (j != -1);
/*     */ 
/*     */         
/* 374 */         this.textPanel.setLayout(new GridLayout(b, 1));
/*     */       } 
/*     */       
/* 377 */       if ("ERROR".equals(param1String3)) {
/* 378 */         this.iconImage = this.errorImage;
/* 379 */       } else if ("WARNING".equals(param1String3)) {
/* 380 */         this.iconImage = this.warnImage;
/* 381 */       } else if ("INFO".equals(param1String3)) {
/* 382 */         this.iconImage = this.infoImage;
/*     */       } else {
/* 384 */         this.iconImage = null;
/*     */       } 
/*     */       
/* 387 */       if (this.iconImage != null) {
/* 388 */         Dimension dimension = this.textPanel.getSize();
/* 389 */         this.iconCanvas.setSize(32, (32 > dimension.height) ? 32 : dimension.height);
/*     */         
/* 391 */         this.iconCanvas.validate();
/*     */       } 
/*     */       
/* 394 */       SunToolkit.executeOnEventHandlerThread(this.target, new Runnable() {
/*     */             public void run() {
/* 396 */               if (InfoWindow.Balloon.this.liveArguments.isDisposed()) {
/*     */                 return;
/*     */               }
/* 399 */               Point point = InfoWindow.Balloon.this.getParent().getLocationOnScreen();
/* 400 */               Dimension dimension = InfoWindow.Balloon.this.getParent().getSize();
/* 401 */               InfoWindow.Balloon.this.show(new Point(point.x + dimension.width / 2, point.y + dimension.height / 2), 0);
/*     */               
/* 403 */               if (InfoWindow.Balloon.this.iconImage != null) {
/* 404 */                 InfoWindow.Balloon.this.iconCanvas.updateImage(InfoWindow.Balloon.this.iconImage);
/*     */               }
/*     */             }
/*     */           });
/*     */     }
/*     */     
/*     */     public void dispose() {
/* 411 */       this.displayer.interrupt();
/* 412 */       super.dispose();
/*     */     } public static interface LiveArguments extends InfoWindow.LiveArguments {
/*     */       String getActionCommand(); }
/*     */     private void loadGtkImages() {
/* 416 */       if (!this.gtkImagesLoaded) {
/*     */ 
/*     */         
/* 419 */         UNIXToolkit uNIXToolkit = (UNIXToolkit)Toolkit.getDefaultToolkit();
/* 420 */         if (uNIXToolkit.checkGtkVersion(3, 10, 0)) {
/* 421 */           this.errorImage = (Image)uNIXToolkit.getDesktopProperty("gtk.icon.dialog-error.6.rtl");
/*     */           
/* 423 */           this.warnImage = (Image)uNIXToolkit.getDesktopProperty("gtk.icon.dialog-warning.6.rtl");
/*     */           
/* 425 */           this.infoImage = (Image)uNIXToolkit.getDesktopProperty("gtk.icon.dialog-information.6.rtl");
/*     */         } else {
/*     */           
/* 428 */           this.errorImage = (Image)uNIXToolkit.getDesktopProperty("gtk.icon.gtk-dialog-error.6.rtl");
/*     */           
/* 430 */           this.warnImage = (Image)uNIXToolkit.getDesktopProperty("gtk.icon.gtk-dialog-warning.6.rtl");
/*     */           
/* 432 */           this.infoImage = (Image)uNIXToolkit.getDesktopProperty("gtk.icon.gtk-dialog-info.6.rtl");
/*     */         } 
/*     */         
/* 435 */         this.gtkImagesLoaded = true;
/*     */       } 
/*     */     }
/*     */     
/*     */     private class ActionPerformer extends MouseAdapter { private ActionPerformer() {}
/*     */       
/*     */       public void mouseClicked(MouseEvent param2MouseEvent) {
/* 442 */         InfoWindow.Balloon.this.hide();
/* 443 */         if (param2MouseEvent.getButton() == 1) {
/*     */ 
/*     */           
/* 446 */           ActionEvent actionEvent = new ActionEvent(InfoWindow.Balloon.this.target, 1001, InfoWindow.Balloon.this.liveArguments.getActionCommand(), param2MouseEvent.getWhen(), param2MouseEvent.getModifiers());
/* 447 */           XToolkit.postEvent(XToolkit.targetToAppContext(actionEvent.getSource()), actionEvent);
/*     */         } 
/*     */       } }
/*     */ 
/*     */     
/*     */     private class Displayer extends Thread {
/* 453 */       final int MAX_CONCURRENT_MSGS = 10;
/*     */       
/* 455 */       ArrayBlockingQueue<InfoWindow.Balloon.Message> messageQueue = new ArrayBlockingQueue<>(10);
/*     */       boolean isDisplayed;
/*     */       
/*     */       Displayer() {
/* 459 */         setDaemon(true);
/*     */       }
/*     */       
/*     */       public void run() {
/*     */         while (true) {
/* 464 */           InfoWindow.Balloon.Message message = null;
/*     */           try {
/* 466 */             message = this.messageQueue.take();
/* 467 */           } catch (InterruptedException interruptedException) {
/*     */             return;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 474 */           XToolkit.awtLock();
/*     */           try {
/* 476 */             while (this.isDisplayed) {
/*     */               try {
/* 478 */                 XToolkit.awtLockWait();
/* 479 */               } catch (InterruptedException interruptedException) {
/*     */                 return;
/*     */               } 
/*     */             } 
/* 483 */             this.isDisplayed = true;
/*     */           } finally {
/* 485 */             XToolkit.awtUnlock();
/*     */           } 
/* 487 */           InfoWindow.Balloon.this._display(message.caption, message.text, message.messageType);
/*     */         } 
/*     */       }
/*     */       
/*     */       void display(String param2String1, String param2String2, String param2String3) {
/* 492 */         this.messageQueue.offer(new InfoWindow.Balloon.Message(param2String1, param2String2, param2String3));
/*     */       } }
/*     */     
/*     */     private static class Message { String caption;
/*     */       String text;
/*     */       String messageType;
/*     */       
/*     */       Message(String param2String1, String param2String2, String param2String3) {
/* 500 */         this.caption = param2String1;
/* 501 */         this.text = param2String2;
/* 502 */         this.messageType = param2String3;
/*     */       } }
/*     */   
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/InfoWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */