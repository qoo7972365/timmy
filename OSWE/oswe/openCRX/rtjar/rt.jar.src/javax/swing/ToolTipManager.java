/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Insets;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.FocusAdapter;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import javax.swing.event.MenuKeyEvent;
/*     */ import javax.swing.event.MenuKeyListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ToolTipManager
/*     */   extends MouseAdapter
/*     */   implements MouseMotionListener
/*     */ {
/*     */   Timer enterTimer;
/*     */   Timer exitTimer;
/*     */   Timer insideTimer;
/*     */   String toolTipText;
/*     */   Point preferredLocation;
/*     */   JComponent insideComponent;
/*     */   MouseEvent mouseEvent;
/*     */   boolean showImmediately;
/*  62 */   private static final Object TOOL_TIP_MANAGER_KEY = new Object();
/*     */   
/*     */   transient Popup tipWindow;
/*     */   
/*     */   private Window window;
/*     */   
/*     */   JToolTip tip;
/*     */   
/*  70 */   private Rectangle popupRect = null;
/*  71 */   private Rectangle popupFrameRect = null;
/*     */   
/*     */   boolean enabled = true;
/*     */   
/*     */   private boolean tipShowing = false;
/*  76 */   private FocusListener focusChangeListener = null;
/*  77 */   private MouseMotionListener moveBeforeEnterListener = null;
/*  78 */   private KeyListener accessibilityKeyListener = null;
/*     */   
/*     */   private KeyStroke postTip;
/*     */   
/*     */   private KeyStroke hideTip;
/*     */   
/*     */   protected boolean lightWeightPopupEnabled = true;
/*     */   protected boolean heavyWeightPopupEnabled = false;
/*     */   
/*     */   ToolTipManager() {
/*  88 */     this.enterTimer = new Timer(750, new insideTimerAction());
/*  89 */     this.enterTimer.setRepeats(false);
/*  90 */     this.exitTimer = new Timer(500, new outsideTimerAction());
/*  91 */     this.exitTimer.setRepeats(false);
/*  92 */     this.insideTimer = new Timer(4000, new stillInsideTimerAction());
/*  93 */     this.insideTimer.setRepeats(false);
/*     */     
/*  95 */     this.moveBeforeEnterListener = new MoveBeforeEnterListener();
/*  96 */     this.accessibilityKeyListener = new AccessibilityKeyListener();
/*     */     
/*  98 */     this.postTip = KeyStroke.getKeyStroke(112, 2);
/*  99 */     this.hideTip = KeyStroke.getKeyStroke(27, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean paramBoolean) {
/* 108 */     this.enabled = paramBoolean;
/* 109 */     if (!paramBoolean) {
/* 110 */       hideTipWindow();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 120 */     return this.enabled;
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
/*     */   public void setLightWeightPopupEnabled(boolean paramBoolean) {
/* 134 */     this.lightWeightPopupEnabled = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLightWeightPopupEnabled() {
/* 145 */     return this.lightWeightPopupEnabled;
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
/*     */   public void setInitialDelay(int paramInt) {
/* 158 */     this.enterTimer.setInitialDelay(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInitialDelay() {
/* 169 */     return this.enterTimer.getInitialDelay();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDismissDelay(int paramInt) {
/* 180 */     this.insideTimer.setInitialDelay(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDismissDelay() {
/* 191 */     return this.insideTimer.getInitialDelay();
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
/*     */   public void setReshowDelay(int paramInt) {
/* 209 */     this.exitTimer.setInitialDelay(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getReshowDelay() {
/* 219 */     return this.exitTimer.getInitialDelay();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GraphicsConfiguration getDrawingGC(Point paramPoint) {
/* 226 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 227 */     GraphicsDevice[] arrayOfGraphicsDevice = graphicsEnvironment.getScreenDevices();
/* 228 */     for (GraphicsDevice graphicsDevice : arrayOfGraphicsDevice) {
/* 229 */       GraphicsConfiguration[] arrayOfGraphicsConfiguration = graphicsDevice.getConfigurations();
/* 230 */       for (GraphicsConfiguration graphicsConfiguration : arrayOfGraphicsConfiguration) {
/* 231 */         Rectangle rectangle = graphicsConfiguration.getBounds();
/* 232 */         if (rectangle.contains(paramPoint)) {
/* 233 */           return graphicsConfiguration;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 238 */     return null;
/*     */   }
/*     */   
/*     */   void showTipWindow() {
/* 242 */     if (this.insideComponent == null || !this.insideComponent.isShowing())
/*     */       return; 
/* 244 */     String str = UIManager.getString("ToolTipManager.enableToolTipMode");
/* 245 */     if ("activeApplication".equals(str)) {
/*     */       
/* 247 */       KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/* 248 */       if (keyboardFocusManager.getFocusedWindow() == null) {
/*     */         return;
/*     */       }
/*     */     } 
/* 252 */     if (this.enabled) {
/*     */       
/* 254 */       Point point2, point3, point1 = this.insideComponent.getLocationOnScreen();
/*     */ 
/*     */ 
/*     */       
/* 258 */       if (this.preferredLocation != null) {
/* 259 */         point3 = new Point(point1.x + this.preferredLocation.x, point1.y + this.preferredLocation.y);
/*     */       } else {
/*     */         
/* 262 */         point3 = this.mouseEvent.getLocationOnScreen();
/*     */       } 
/*     */       
/* 265 */       GraphicsConfiguration graphicsConfiguration = getDrawingGC(point3);
/* 266 */       if (graphicsConfiguration == null) {
/* 267 */         point3 = this.mouseEvent.getLocationOnScreen();
/* 268 */         graphicsConfiguration = getDrawingGC(point3);
/* 269 */         if (graphicsConfiguration == null) {
/* 270 */           graphicsConfiguration = this.insideComponent.getGraphicsConfiguration();
/*     */         }
/*     */       } 
/*     */       
/* 274 */       Rectangle rectangle = graphicsConfiguration.getBounds();
/*     */       
/* 276 */       Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(graphicsConfiguration);
/*     */       
/* 278 */       rectangle.x += insets.left;
/* 279 */       rectangle.y += insets.top;
/* 280 */       rectangle.width -= insets.left + insets.right;
/* 281 */       rectangle.height -= insets.top + insets.bottom;
/*     */       
/* 283 */       boolean bool = SwingUtilities.isLeftToRight(this.insideComponent);
/*     */ 
/*     */       
/* 286 */       hideTipWindow();
/*     */       
/* 288 */       this.tip = this.insideComponent.createToolTip();
/* 289 */       this.tip.setTipText(this.toolTipText);
/* 290 */       Dimension dimension = this.tip.getPreferredSize();
/*     */       
/* 292 */       if (this.preferredLocation != null) {
/* 293 */         point2 = point3;
/* 294 */         if (!bool) {
/* 295 */           point2.x -= dimension.width;
/*     */         }
/*     */       } else {
/*     */         
/* 299 */         point2 = new Point(point1.x + this.mouseEvent.getX(), point1.y + this.mouseEvent.getY() + 20);
/* 300 */         if (!bool && 
/* 301 */           point2.x - dimension.width >= 0) {
/* 302 */           point2.x -= dimension.width;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 309 */       if (this.popupRect == null) {
/* 310 */         this.popupRect = new Rectangle();
/*     */       }
/* 312 */       this.popupRect.setBounds(point2.x, point2.y, dimension.width, dimension.height);
/*     */ 
/*     */ 
/*     */       
/* 316 */       if (point2.x < rectangle.x) {
/* 317 */         point2.x = rectangle.x;
/*     */       }
/* 319 */       else if (point2.x - rectangle.x + dimension.width > rectangle.width) {
/* 320 */         point2.x = rectangle.x + Math.max(0, rectangle.width - dimension.width);
/*     */       } 
/*     */       
/* 323 */       if (point2.y < rectangle.y) {
/* 324 */         point2.y = rectangle.y;
/*     */       }
/* 326 */       else if (point2.y - rectangle.y + dimension.height > rectangle.height) {
/* 327 */         point2.y = rectangle.y + Math.max(0, rectangle.height - dimension.height);
/*     */       } 
/*     */       
/* 330 */       PopupFactory popupFactory = PopupFactory.getSharedInstance();
/*     */       
/* 332 */       if (this.lightWeightPopupEnabled) {
/* 333 */         int i = getPopupFitHeight(this.popupRect, this.insideComponent);
/* 334 */         int j = getPopupFitWidth(this.popupRect, this.insideComponent);
/* 335 */         if (j > 0 || i > 0) {
/* 336 */           popupFactory.setPopupType(1);
/*     */         } else {
/* 338 */           popupFactory.setPopupType(0);
/*     */         } 
/*     */       } else {
/*     */         
/* 342 */         popupFactory.setPopupType(1);
/*     */       } 
/* 344 */       this.tipWindow = popupFactory.getPopup(this.insideComponent, this.tip, point2.x, point2.y);
/*     */ 
/*     */       
/* 347 */       popupFactory.setPopupType(0);
/*     */       
/* 349 */       this.tipWindow.show();
/*     */       
/* 351 */       Window window = SwingUtilities.windowForComponent(this.insideComponent);
/*     */ 
/*     */       
/* 354 */       this.window = SwingUtilities.windowForComponent(this.tip);
/* 355 */       if (this.window != null && this.window != window) {
/* 356 */         this.window.addMouseListener(this);
/*     */       } else {
/*     */         
/* 359 */         this.window = null;
/*     */       } 
/*     */       
/* 362 */       this.insideTimer.start();
/* 363 */       this.tipShowing = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   void hideTipWindow() {
/* 368 */     if (this.tipWindow != null) {
/* 369 */       if (this.window != null) {
/* 370 */         this.window.removeMouseListener(this);
/* 371 */         this.window = null;
/*     */       } 
/* 373 */       this.tipWindow.hide();
/* 374 */       this.tipWindow = null;
/* 375 */       this.tipShowing = false;
/* 376 */       this.tip = null;
/* 377 */       this.insideTimer.stop();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ToolTipManager sharedInstance() {
/* 387 */     Object object = SwingUtilities.appContextGet(TOOL_TIP_MANAGER_KEY);
/* 388 */     if (object instanceof ToolTipManager) {
/* 389 */       return (ToolTipManager)object;
/*     */     }
/* 391 */     ToolTipManager toolTipManager = new ToolTipManager();
/* 392 */     SwingUtilities.appContextPut(TOOL_TIP_MANAGER_KEY, toolTipManager);
/* 393 */     return toolTipManager;
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
/*     */   public void registerComponent(JComponent paramJComponent) {
/* 410 */     paramJComponent.removeMouseListener(this);
/* 411 */     paramJComponent.addMouseListener(this);
/* 412 */     paramJComponent.removeMouseMotionListener(this.moveBeforeEnterListener);
/* 413 */     paramJComponent.addMouseMotionListener(this.moveBeforeEnterListener);
/*     */     
/* 415 */     if (paramJComponent instanceof JMenuItem) {
/* 416 */       ((JMenuItem)paramJComponent).removeMenuKeyListener((MenuKeyListener)this.accessibilityKeyListener);
/* 417 */       ((JMenuItem)paramJComponent).addMenuKeyListener((MenuKeyListener)this.accessibilityKeyListener);
/*     */     } else {
/* 419 */       paramJComponent.removeKeyListener(this.accessibilityKeyListener);
/* 420 */       paramJComponent.addKeyListener(this.accessibilityKeyListener);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterComponent(JComponent paramJComponent) {
/* 430 */     paramJComponent.removeMouseListener(this);
/* 431 */     paramJComponent.removeMouseMotionListener(this.moveBeforeEnterListener);
/* 432 */     if (paramJComponent instanceof JMenuItem) {
/* 433 */       ((JMenuItem)paramJComponent).removeMenuKeyListener((MenuKeyListener)this.accessibilityKeyListener);
/*     */     } else {
/* 435 */       paramJComponent.removeKeyListener(this.accessibilityKeyListener);
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
/*     */   public void mouseEntered(MouseEvent paramMouseEvent) {
/* 447 */     initiateToolTip(paramMouseEvent);
/*     */   }
/*     */   
/*     */   private void initiateToolTip(MouseEvent paramMouseEvent) {
/* 451 */     if (paramMouseEvent.getSource() == this.window) {
/*     */       return;
/*     */     }
/* 454 */     JComponent jComponent = (JComponent)paramMouseEvent.getSource();
/* 455 */     jComponent.removeMouseMotionListener(this.moveBeforeEnterListener);
/*     */     
/* 457 */     this.exitTimer.stop();
/*     */     
/* 459 */     Point point = paramMouseEvent.getPoint();
/*     */     
/* 461 */     if (point.x < 0 || point.x >= jComponent
/* 462 */       .getWidth() || point.y < 0 || point.y >= jComponent
/*     */       
/* 464 */       .getHeight()) {
/*     */       return;
/*     */     }
/*     */     
/* 468 */     if (this.insideComponent != null) {
/* 469 */       this.enterTimer.stop();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 474 */     jComponent.removeMouseMotionListener(this);
/* 475 */     jComponent.addMouseMotionListener(this);
/*     */     
/* 477 */     boolean bool = (this.insideComponent == jComponent) ? true : false;
/*     */     
/* 479 */     this.insideComponent = jComponent;
/* 480 */     if (this.tipWindow != null) {
/* 481 */       this.mouseEvent = paramMouseEvent;
/* 482 */       if (this.showImmediately) {
/* 483 */         String str = jComponent.getToolTipText(paramMouseEvent);
/* 484 */         Point point1 = jComponent.getToolTipLocation(paramMouseEvent);
/*     */ 
/*     */         
/* 487 */         boolean bool1 = (this.preferredLocation != null) ? this.preferredLocation.equals(point1) : ((point1 == null) ? true : false);
/*     */ 
/*     */         
/* 490 */         if (!bool || !this.toolTipText.equals(str) || !bool1) {
/*     */           
/* 492 */           this.toolTipText = str;
/* 493 */           this.preferredLocation = point1;
/* 494 */           showTipWindow();
/*     */         } 
/*     */       } else {
/* 497 */         this.enterTimer.start();
/*     */       } 
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
/*     */   public void mouseExited(MouseEvent paramMouseEvent) {
/* 510 */     boolean bool = true;
/* 511 */     if (this.insideComponent == null);
/*     */ 
/*     */     
/* 514 */     if (this.window != null && paramMouseEvent.getSource() == this.window && this.insideComponent != null) {
/*     */ 
/*     */       
/* 517 */       Container container = this.insideComponent.getTopLevelAncestor();
/*     */       
/* 519 */       if (container != null) {
/* 520 */         Point point = paramMouseEvent.getPoint();
/* 521 */         SwingUtilities.convertPointToScreen(point, this.window);
/*     */         
/* 523 */         point.x -= container.getX();
/* 524 */         point.y -= container.getY();
/*     */         
/* 526 */         point = SwingUtilities.convertPoint(null, point, this.insideComponent);
/* 527 */         if (point.x >= 0 && point.x < this.insideComponent.getWidth() && point.y >= 0 && point.y < this.insideComponent
/* 528 */           .getHeight()) {
/* 529 */           bool = false;
/*     */         } else {
/* 531 */           bool = true;
/*     */         } 
/*     */       } 
/* 534 */     } else if (paramMouseEvent.getSource() == this.insideComponent && this.tipWindow != null) {
/* 535 */       Window window = SwingUtilities.getWindowAncestor(this.insideComponent);
/* 536 */       if (window != null) {
/* 537 */         Point point1 = SwingUtilities.convertPoint(this.insideComponent, paramMouseEvent
/* 538 */             .getPoint(), window);
/*     */         
/* 540 */         Rectangle rectangle = this.insideComponent.getTopLevelAncestor().getBounds();
/* 541 */         point1.x += rectangle.x;
/* 542 */         point1.y += rectangle.y;
/*     */         
/* 544 */         Point point2 = new Point(0, 0);
/* 545 */         SwingUtilities.convertPointToScreen(point2, this.tip);
/* 546 */         rectangle.x = point2.x;
/* 547 */         rectangle.y = point2.y;
/* 548 */         rectangle.width = this.tip.getWidth();
/* 549 */         rectangle.height = this.tip.getHeight();
/*     */         
/* 551 */         if (point1.x >= rectangle.x && point1.x < rectangle.x + rectangle.width && point1.y >= rectangle.y && point1.y < rectangle.y + rectangle.height) {
/*     */           
/* 553 */           bool = false;
/*     */         } else {
/* 555 */           bool = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 560 */     if (bool) {
/* 561 */       this.enterTimer.stop();
/* 562 */       if (this.insideComponent != null) {
/* 563 */         this.insideComponent.removeMouseMotionListener(this);
/*     */       }
/* 565 */       this.insideComponent = null;
/* 566 */       this.toolTipText = null;
/* 567 */       this.mouseEvent = null;
/* 568 */       hideTipWindow();
/* 569 */       this.exitTimer.restart();
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
/*     */   public void mousePressed(MouseEvent paramMouseEvent) {
/* 581 */     hideTipWindow();
/* 582 */     this.enterTimer.stop();
/* 583 */     this.showImmediately = false;
/* 584 */     this.insideComponent = null;
/* 585 */     this.mouseEvent = null;
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
/*     */   public void mouseDragged(MouseEvent paramMouseEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(MouseEvent paramMouseEvent) {
/* 606 */     if (this.tipShowing) {
/* 607 */       checkForTipChange(paramMouseEvent);
/*     */     }
/* 609 */     else if (this.showImmediately) {
/* 610 */       JComponent jComponent = (JComponent)paramMouseEvent.getSource();
/* 611 */       this.toolTipText = jComponent.getToolTipText(paramMouseEvent);
/* 612 */       if (this.toolTipText != null) {
/* 613 */         this.preferredLocation = jComponent.getToolTipLocation(paramMouseEvent);
/* 614 */         this.mouseEvent = paramMouseEvent;
/* 615 */         this.insideComponent = jComponent;
/* 616 */         this.exitTimer.stop();
/* 617 */         showTipWindow();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 622 */       this.insideComponent = (JComponent)paramMouseEvent.getSource();
/* 623 */       this.mouseEvent = paramMouseEvent;
/* 624 */       this.toolTipText = null;
/* 625 */       this.enterTimer.restart();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkForTipChange(MouseEvent paramMouseEvent) {
/* 634 */     JComponent jComponent = (JComponent)paramMouseEvent.getSource();
/* 635 */     String str = jComponent.getToolTipText(paramMouseEvent);
/* 636 */     Point point = jComponent.getToolTipLocation(paramMouseEvent);
/*     */     
/* 638 */     if (str != null || point != null) {
/* 639 */       this.mouseEvent = paramMouseEvent;
/* 640 */       if (((str != null && str.equals(this.toolTipText)) || str == null) && ((point != null && point
/* 641 */         .equals(this.preferredLocation)) || point == null)) {
/*     */         
/* 643 */         if (this.tipWindow != null) {
/* 644 */           this.insideTimer.restart();
/*     */         } else {
/* 646 */           this.enterTimer.restart();
/*     */         } 
/*     */       } else {
/* 649 */         this.toolTipText = str;
/* 650 */         this.preferredLocation = point;
/* 651 */         if (this.showImmediately) {
/* 652 */           hideTipWindow();
/* 653 */           showTipWindow();
/* 654 */           this.exitTimer.stop();
/*     */         } else {
/* 656 */           this.enterTimer.restart();
/*     */         } 
/*     */       } 
/*     */     } else {
/* 660 */       this.toolTipText = null;
/* 661 */       this.preferredLocation = null;
/* 662 */       this.mouseEvent = null;
/* 663 */       this.insideComponent = null;
/* 664 */       hideTipWindow();
/* 665 */       this.enterTimer.stop();
/* 666 */       this.exitTimer.restart();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected class insideTimerAction implements ActionListener {
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 672 */       if (ToolTipManager.this.insideComponent != null && ToolTipManager.this.insideComponent.isShowing()) {
/*     */         
/* 674 */         if (ToolTipManager.this.toolTipText == null && ToolTipManager.this.mouseEvent != null) {
/* 675 */           ToolTipManager.this.toolTipText = ToolTipManager.this.insideComponent.getToolTipText(ToolTipManager.this.mouseEvent);
/* 676 */           ToolTipManager.this.preferredLocation = ToolTipManager.this.insideComponent.getToolTipLocation(ToolTipManager.this.mouseEvent);
/*     */         } 
/*     */         
/* 679 */         if (ToolTipManager.this.toolTipText != null) {
/* 680 */           ToolTipManager.this.showImmediately = true;
/* 681 */           ToolTipManager.this.showTipWindow();
/*     */         } else {
/*     */           
/* 684 */           ToolTipManager.this.insideComponent = null;
/* 685 */           ToolTipManager.this.toolTipText = null;
/* 686 */           ToolTipManager.this.preferredLocation = null;
/* 687 */           ToolTipManager.this.mouseEvent = null;
/* 688 */           ToolTipManager.this.hideTipWindow();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected class outsideTimerAction implements ActionListener {
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 696 */       ToolTipManager.this.showImmediately = false;
/*     */     }
/*     */   }
/*     */   
/*     */   protected class stillInsideTimerAction implements ActionListener {
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 702 */       ToolTipManager.this.hideTipWindow();
/* 703 */       ToolTipManager.this.enterTimer.stop();
/* 704 */       ToolTipManager.this.showImmediately = false;
/* 705 */       ToolTipManager.this.insideComponent = null;
/* 706 */       ToolTipManager.this.mouseEvent = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class MoveBeforeEnterListener
/*     */     extends MouseMotionAdapter
/*     */   {
/*     */     private MoveBeforeEnterListener() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseMoved(MouseEvent param1MouseEvent) {
/* 720 */       ToolTipManager.this.initiateToolTip(param1MouseEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   static Frame frameForComponent(Component paramComponent) {
/* 725 */     while (!(paramComponent instanceof Frame)) {
/* 726 */       paramComponent = paramComponent.getParent();
/*     */     }
/* 728 */     return (Frame)paramComponent;
/*     */   }
/*     */   
/*     */   private FocusListener createFocusChangeListener() {
/* 732 */     return new FocusAdapter() {
/*     */         public void focusLost(FocusEvent param1FocusEvent) {
/* 734 */           ToolTipManager.this.hideTipWindow();
/* 735 */           ToolTipManager.this.insideComponent = null;
/* 736 */           JComponent jComponent = (JComponent)param1FocusEvent.getSource();
/* 737 */           jComponent.removeFocusListener(ToolTipManager.this.focusChangeListener);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPopupFitWidth(Rectangle paramRectangle, Component paramComponent) {
/* 746 */     if (paramComponent != null)
/*     */     {
/* 748 */       for (Container container = paramComponent.getParent(); container != null; container = container.getParent()) {
/*     */         
/* 750 */         if (container instanceof JFrame || container instanceof JDialog || container instanceof JWindow)
/*     */         {
/* 752 */           return getWidthAdjust(container.getBounds(), paramRectangle); } 
/* 753 */         if (container instanceof JApplet || container instanceof JInternalFrame) {
/* 754 */           if (this.popupFrameRect == null) {
/* 755 */             this.popupFrameRect = new Rectangle();
/*     */           }
/* 757 */           Point point = container.getLocationOnScreen();
/* 758 */           this.popupFrameRect.setBounds(point.x, point.y, 
/* 759 */               (container.getBounds()).width, 
/* 760 */               (container.getBounds()).height);
/* 761 */           return getWidthAdjust(this.popupFrameRect, paramRectangle);
/*     */         } 
/*     */       } 
/*     */     }
/* 765 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPopupFitHeight(Rectangle paramRectangle, Component paramComponent) {
/* 771 */     if (paramComponent != null)
/*     */     {
/* 773 */       for (Container container = paramComponent.getParent(); container != null; container = container.getParent()) {
/* 774 */         if (container instanceof JFrame || container instanceof JDialog || container instanceof JWindow)
/*     */         {
/* 776 */           return getHeightAdjust(container.getBounds(), paramRectangle); } 
/* 777 */         if (container instanceof JApplet || container instanceof JInternalFrame) {
/* 778 */           if (this.popupFrameRect == null) {
/* 779 */             this.popupFrameRect = new Rectangle();
/*     */           }
/* 781 */           Point point = container.getLocationOnScreen();
/* 782 */           this.popupFrameRect.setBounds(point.x, point.y, 
/* 783 */               (container.getBounds()).width, 
/* 784 */               (container.getBounds()).height);
/* 785 */           return getHeightAdjust(this.popupFrameRect, paramRectangle);
/*     */         } 
/*     */       } 
/*     */     }
/* 789 */     return 0;
/*     */   }
/*     */   
/*     */   private int getHeightAdjust(Rectangle paramRectangle1, Rectangle paramRectangle2) {
/* 793 */     if (paramRectangle2.y >= paramRectangle1.y && paramRectangle2.y + paramRectangle2.height <= paramRectangle1.y + paramRectangle1.height) {
/* 794 */       return 0;
/*     */     }
/* 796 */     return paramRectangle2.y + paramRectangle2.height - paramRectangle1.y + paramRectangle1.height + 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getWidthAdjust(Rectangle paramRectangle1, Rectangle paramRectangle2) {
/* 805 */     if (paramRectangle2.x >= paramRectangle1.x && paramRectangle2.x + paramRectangle2.width <= paramRectangle1.x + paramRectangle1.width) {
/* 806 */       return 0;
/*     */     }
/*     */     
/* 809 */     return paramRectangle2.x + paramRectangle2.width - paramRectangle1.x + paramRectangle1.width + 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void show(JComponent paramJComponent) {
/* 818 */     if (this.tipWindow != null) {
/* 819 */       hideTipWindow();
/* 820 */       this.insideComponent = null;
/*     */     } else {
/*     */       
/* 823 */       hideTipWindow();
/* 824 */       this.enterTimer.stop();
/* 825 */       this.exitTimer.stop();
/* 826 */       this.insideTimer.stop();
/* 827 */       this.insideComponent = paramJComponent;
/* 828 */       if (this.insideComponent != null) {
/* 829 */         this.toolTipText = this.insideComponent.getToolTipText();
/* 830 */         this.preferredLocation = new Point(10, this.insideComponent.getHeight() + 10);
/*     */         
/* 832 */         showTipWindow();
/*     */         
/* 834 */         if (this.focusChangeListener == null) {
/* 835 */           this.focusChangeListener = createFocusChangeListener();
/*     */         }
/* 837 */         this.insideComponent.addFocusListener(this.focusChangeListener);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void hide(JComponent paramJComponent) {
/* 843 */     hideTipWindow();
/* 844 */     paramJComponent.removeFocusListener(this.focusChangeListener);
/* 845 */     this.preferredLocation = null;
/* 846 */     this.insideComponent = null;
/*     */   }
/*     */ 
/*     */   
/*     */   private class AccessibilityKeyListener
/*     */     extends KeyAdapter
/*     */     implements MenuKeyListener
/*     */   {
/*     */     private AccessibilityKeyListener() {}
/*     */ 
/*     */     
/*     */     public void keyPressed(KeyEvent param1KeyEvent) {
/* 858 */       if (!param1KeyEvent.isConsumed()) {
/* 859 */         JComponent jComponent = (JComponent)param1KeyEvent.getComponent();
/* 860 */         KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(param1KeyEvent);
/* 861 */         if (ToolTipManager.this.hideTip.equals(keyStroke)) {
/* 862 */           if (ToolTipManager.this.tipWindow != null) {
/* 863 */             ToolTipManager.this.hide(jComponent);
/* 864 */             param1KeyEvent.consume();
/*     */           } 
/* 866 */         } else if (ToolTipManager.this.postTip.equals(keyStroke)) {
/*     */           
/* 868 */           ToolTipManager.this.show(jComponent);
/* 869 */           param1KeyEvent.consume();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void menuKeyTyped(MenuKeyEvent param1MenuKeyEvent) {}
/*     */ 
/*     */     
/*     */     public void menuKeyPressed(MenuKeyEvent param1MenuKeyEvent) {
/* 879 */       if (ToolTipManager.this.postTip.equals(KeyStroke.getKeyStrokeForEvent(param1MenuKeyEvent))) {
/*     */         
/* 881 */         MenuElement[] arrayOfMenuElement1 = param1MenuKeyEvent.getPath();
/* 882 */         MenuElement menuElement1 = arrayOfMenuElement1[arrayOfMenuElement1.length - 1];
/*     */ 
/*     */         
/* 885 */         MenuSelectionManager menuSelectionManager = param1MenuKeyEvent.getMenuSelectionManager();
/* 886 */         MenuElement[] arrayOfMenuElement2 = menuSelectionManager.getSelectedPath();
/* 887 */         MenuElement menuElement2 = arrayOfMenuElement2[arrayOfMenuElement2.length - 1];
/*     */         
/* 889 */         if (menuElement1.equals(menuElement2)) {
/*     */           
/* 891 */           JComponent jComponent = (JComponent)menuElement1.getComponent();
/* 892 */           ToolTipManager.this.show(jComponent);
/* 893 */           param1MenuKeyEvent.consume();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void menuKeyReleased(MenuKeyEvent param1MenuKeyEvent) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/ToolTipManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */