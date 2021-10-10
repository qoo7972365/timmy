/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.LayoutManager2;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLayeredPane;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicRootPaneUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalRootPaneUI
/*     */   extends BasicRootPaneUI
/*     */ {
/*  71 */   private static final String[] borderKeys = new String[] { null, "RootPane.frameBorder", "RootPane.plainDialogBorder", "RootPane.informationDialogBorder", "RootPane.errorDialogBorder", "RootPane.colorChooserDialogBorder", "RootPane.fileChooserDialogBorder", "RootPane.questionDialogBorder", "RootPane.warningDialogBorder" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int CORNER_DRAG_WIDTH = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int BORDER_DRAG_THICKNESS = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Window window;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JComponent titlePane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MouseInputListener mouseInputListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LayoutManager layoutManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LayoutManager savedOldLayout;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JRootPane root;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   private Cursor lastCursor = Cursor.getPredefinedCursor(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 136 */     return new MetalRootPaneUI();
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
/*     */   public void installUI(JComponent paramJComponent) {
/* 154 */     super.installUI(paramJComponent);
/* 155 */     this.root = (JRootPane)paramJComponent;
/* 156 */     int i = this.root.getWindowDecorationStyle();
/* 157 */     if (i != 0) {
/* 158 */       installClientDecorations(this.root);
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
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 175 */     super.uninstallUI(paramJComponent);
/* 176 */     uninstallClientDecorations(this.root);
/*     */     
/* 178 */     this.layoutManager = null;
/* 179 */     this.mouseInputListener = null;
/* 180 */     this.root = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void installBorder(JRootPane paramJRootPane) {
/* 188 */     int i = paramJRootPane.getWindowDecorationStyle();
/*     */     
/* 190 */     if (i == 0) {
/* 191 */       LookAndFeel.uninstallBorder(paramJRootPane);
/*     */     } else {
/*     */       
/* 194 */       LookAndFeel.installBorder(paramJRootPane, borderKeys[i]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void uninstallBorder(JRootPane paramJRootPane) {
/* 202 */     LookAndFeel.uninstallBorder(paramJRootPane);
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
/*     */   private void installWindowListeners(JRootPane paramJRootPane, Component paramComponent) {
/* 216 */     if (paramComponent instanceof Window) {
/* 217 */       this.window = (Window)paramComponent;
/*     */     } else {
/*     */       
/* 220 */       this.window = SwingUtilities.getWindowAncestor(paramComponent);
/*     */     } 
/* 222 */     if (this.window != null) {
/* 223 */       if (this.mouseInputListener == null) {
/* 224 */         this.mouseInputListener = createWindowMouseInputListener(paramJRootPane);
/*     */       }
/* 226 */       this.window.addMouseListener(this.mouseInputListener);
/* 227 */       this.window.addMouseMotionListener(this.mouseInputListener);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void uninstallWindowListeners(JRootPane paramJRootPane) {
/* 236 */     if (this.window != null) {
/* 237 */       this.window.removeMouseListener(this.mouseInputListener);
/* 238 */       this.window.removeMouseMotionListener(this.mouseInputListener);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void installLayout(JRootPane paramJRootPane) {
/* 247 */     if (this.layoutManager == null) {
/* 248 */       this.layoutManager = createLayoutManager();
/*     */     }
/* 250 */     this.savedOldLayout = paramJRootPane.getLayout();
/* 251 */     paramJRootPane.setLayout(this.layoutManager);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void uninstallLayout(JRootPane paramJRootPane) {
/* 258 */     if (this.savedOldLayout != null) {
/* 259 */       paramJRootPane.setLayout(this.savedOldLayout);
/* 260 */       this.savedOldLayout = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void installClientDecorations(JRootPane paramJRootPane) {
/* 270 */     installBorder(paramJRootPane);
/*     */     
/* 272 */     JComponent jComponent = createTitlePane(paramJRootPane);
/*     */     
/* 274 */     setTitlePane(paramJRootPane, jComponent);
/* 275 */     installWindowListeners(paramJRootPane, paramJRootPane.getParent());
/* 276 */     installLayout(paramJRootPane);
/* 277 */     if (this.window != null) {
/* 278 */       paramJRootPane.revalidate();
/* 279 */       paramJRootPane.repaint();
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
/*     */   private void uninstallClientDecorations(JRootPane paramJRootPane) {
/* 291 */     uninstallBorder(paramJRootPane);
/* 292 */     uninstallWindowListeners(paramJRootPane);
/* 293 */     setTitlePane(paramJRootPane, null);
/* 294 */     uninstallLayout(paramJRootPane);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 300 */     int i = paramJRootPane.getWindowDecorationStyle();
/* 301 */     if (i == 0) {
/* 302 */       paramJRootPane.repaint();
/* 303 */       paramJRootPane.revalidate();
/*     */     } 
/*     */     
/* 306 */     if (this.window != null) {
/* 307 */       this.window.setCursor(
/* 308 */           Cursor.getPredefinedCursor(0));
/*     */     }
/* 310 */     this.window = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JComponent createTitlePane(JRootPane paramJRootPane) {
/* 318 */     return new MetalTitlePane(paramJRootPane, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MouseInputListener createWindowMouseInputListener(JRootPane paramJRootPane) {
/* 326 */     return new MouseInputHandler();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LayoutManager createLayoutManager() {
/* 334 */     return new MetalRootLayout();
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
/*     */   private void setTitlePane(JRootPane paramJRootPane, JComponent paramJComponent) {
/* 347 */     JLayeredPane jLayeredPane = paramJRootPane.getLayeredPane();
/* 348 */     JComponent jComponent = getTitlePane();
/*     */     
/* 350 */     if (jComponent != null) {
/* 351 */       jComponent.setVisible(false);
/* 352 */       jLayeredPane.remove(jComponent);
/*     */     } 
/* 354 */     if (paramJComponent != null) {
/* 355 */       jLayeredPane.add(paramJComponent, JLayeredPane.FRAME_CONTENT_LAYER);
/* 356 */       paramJComponent.setVisible(true);
/*     */     } 
/* 358 */     this.titlePane = paramJComponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JComponent getTitlePane() {
/* 369 */     return this.titlePane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JRootPane getRootPane() {
/* 377 */     return this.root;
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
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 399 */     super.propertyChange(paramPropertyChangeEvent);
/*     */     
/* 401 */     String str = paramPropertyChangeEvent.getPropertyName();
/* 402 */     if (str == null) {
/*     */       return;
/*     */     }
/*     */     
/* 406 */     if (str.equals("windowDecorationStyle")) {
/* 407 */       JRootPane jRootPane = (JRootPane)paramPropertyChangeEvent.getSource();
/* 408 */       int i = jRootPane.getWindowDecorationStyle();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 414 */       uninstallClientDecorations(jRootPane);
/* 415 */       if (i != 0) {
/* 416 */         installClientDecorations(jRootPane);
/*     */       }
/*     */     }
/* 419 */     else if (str.equals("ancestor")) {
/* 420 */       uninstallWindowListeners(this.root);
/* 421 */       if (((JRootPane)paramPropertyChangeEvent.getSource()).getWindowDecorationStyle() != 0)
/*     */       {
/* 423 */         installWindowListeners(this.root, this.root.getParent());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class MetalRootLayout
/*     */     implements LayoutManager2
/*     */   {
/*     */     private MetalRootLayout() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Dimension preferredLayoutSize(Container param1Container) {
/*     */       Dimension dimension;
/* 445 */       int i = 0;
/* 446 */       int j = 0;
/* 447 */       int k = 0;
/* 448 */       int m = 0;
/* 449 */       int n = 0;
/* 450 */       int i1 = 0;
/* 451 */       Insets insets = param1Container.getInsets();
/* 452 */       JRootPane jRootPane = (JRootPane)param1Container;
/*     */       
/* 454 */       if (jRootPane.getContentPane() != null) {
/* 455 */         dimension = jRootPane.getContentPane().getPreferredSize();
/*     */       } else {
/* 457 */         dimension = jRootPane.getSize();
/*     */       } 
/* 459 */       if (dimension != null) {
/* 460 */         i = dimension.width;
/* 461 */         j = dimension.height;
/*     */       } 
/*     */       
/* 464 */       if (jRootPane.getMenuBar() != null) {
/* 465 */         Dimension dimension1 = jRootPane.getMenuBar().getPreferredSize();
/* 466 */         if (dimension1 != null) {
/* 467 */           k = dimension1.width;
/* 468 */           m = dimension1.height;
/*     */         } 
/*     */       } 
/*     */       
/* 472 */       if (jRootPane.getWindowDecorationStyle() != 0 && jRootPane
/* 473 */         .getUI() instanceof MetalRootPaneUI) {
/* 474 */         JComponent jComponent = ((MetalRootPaneUI)jRootPane.getUI()).getTitlePane();
/*     */         
/* 476 */         if (jComponent != null) {
/* 477 */           Dimension dimension1 = jComponent.getPreferredSize();
/* 478 */           if (dimension1 != null) {
/* 479 */             n = dimension1.width;
/* 480 */             i1 = dimension1.height;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 485 */       return new Dimension(Math.max(Math.max(i, k), n) + insets.left + insets.right, j + m + n + insets.top + insets.bottom);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Dimension minimumLayoutSize(Container param1Container) {
/*     */       Dimension dimension;
/* 497 */       int i = 0;
/* 498 */       int j = 0;
/* 499 */       int k = 0;
/* 500 */       int m = 0;
/* 501 */       int n = 0;
/* 502 */       int i1 = 0;
/* 503 */       Insets insets = param1Container.getInsets();
/* 504 */       JRootPane jRootPane = (JRootPane)param1Container;
/*     */       
/* 506 */       if (jRootPane.getContentPane() != null) {
/* 507 */         dimension = jRootPane.getContentPane().getMinimumSize();
/*     */       } else {
/* 509 */         dimension = jRootPane.getSize();
/*     */       } 
/* 511 */       if (dimension != null) {
/* 512 */         i = dimension.width;
/* 513 */         j = dimension.height;
/*     */       } 
/*     */       
/* 516 */       if (jRootPane.getMenuBar() != null) {
/* 517 */         Dimension dimension1 = jRootPane.getMenuBar().getMinimumSize();
/* 518 */         if (dimension1 != null) {
/* 519 */           k = dimension1.width;
/* 520 */           m = dimension1.height;
/*     */         } 
/*     */       } 
/* 523 */       if (jRootPane.getWindowDecorationStyle() != 0 && jRootPane
/* 524 */         .getUI() instanceof MetalRootPaneUI) {
/* 525 */         JComponent jComponent = ((MetalRootPaneUI)jRootPane.getUI()).getTitlePane();
/*     */         
/* 527 */         if (jComponent != null) {
/* 528 */           Dimension dimension1 = jComponent.getMinimumSize();
/* 529 */           if (dimension1 != null) {
/* 530 */             n = dimension1.width;
/* 531 */             i1 = dimension1.height;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 536 */       return new Dimension(Math.max(Math.max(i, k), n) + insets.left + insets.right, j + m + n + insets.top + insets.bottom);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Dimension maximumLayoutSize(Container param1Container) {
/* 548 */       int i = Integer.MAX_VALUE;
/* 549 */       int j = Integer.MAX_VALUE;
/* 550 */       int k = Integer.MAX_VALUE;
/* 551 */       int m = Integer.MAX_VALUE;
/* 552 */       int n = Integer.MAX_VALUE;
/* 553 */       int i1 = Integer.MAX_VALUE;
/* 554 */       Insets insets = param1Container.getInsets();
/* 555 */       JRootPane jRootPane = (JRootPane)param1Container;
/*     */       
/* 557 */       if (jRootPane.getContentPane() != null) {
/* 558 */         Dimension dimension = jRootPane.getContentPane().getMaximumSize();
/* 559 */         if (dimension != null) {
/* 560 */           i = dimension.width;
/* 561 */           j = dimension.height;
/*     */         } 
/*     */       } 
/*     */       
/* 565 */       if (jRootPane.getMenuBar() != null) {
/* 566 */         Dimension dimension = jRootPane.getMenuBar().getMaximumSize();
/* 567 */         if (dimension != null) {
/* 568 */           k = dimension.width;
/* 569 */           m = dimension.height;
/*     */         } 
/*     */       } 
/*     */       
/* 573 */       if (jRootPane.getWindowDecorationStyle() != 0 && jRootPane
/* 574 */         .getUI() instanceof MetalRootPaneUI) {
/* 575 */         JComponent jComponent = ((MetalRootPaneUI)jRootPane.getUI()).getTitlePane();
/*     */         
/* 577 */         if (jComponent != null) {
/*     */           
/* 579 */           Dimension dimension = jComponent.getMaximumSize();
/* 580 */           if (dimension != null) {
/* 581 */             n = dimension.width;
/* 582 */             i1 = dimension.height;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 587 */       int i2 = Math.max(Math.max(j, m), i1);
/*     */ 
/*     */       
/* 590 */       if (i2 != Integer.MAX_VALUE) {
/* 591 */         i2 = j + m + i1 + insets.top + insets.bottom;
/*     */       }
/*     */       
/* 594 */       int i3 = Math.max(Math.max(i, k), n);
/*     */       
/* 596 */       if (i3 != Integer.MAX_VALUE) {
/* 597 */         i3 += insets.left + insets.right;
/*     */       }
/*     */       
/* 600 */       return new Dimension(i3, i2);
/*     */     }
/*     */ 
/*     */     
/*     */     public void addLayoutComponent(String param1String, Component param1Component) {}
/*     */ 
/*     */     
/*     */     public void removeLayoutComponent(Component param1Component) {}
/*     */     
/*     */     public void layoutContainer(Container param1Container) {
/* 610 */       JRootPane jRootPane = (JRootPane)param1Container;
/* 611 */       Rectangle rectangle = jRootPane.getBounds();
/* 612 */       Insets insets = jRootPane.getInsets();
/* 613 */       int i = 0;
/* 614 */       int j = rectangle.width - insets.right - insets.left;
/* 615 */       int k = rectangle.height - insets.top - insets.bottom;
/*     */       
/* 617 */       if (jRootPane.getLayeredPane() != null) {
/* 618 */         jRootPane.getLayeredPane().setBounds(insets.left, insets.top, j, k);
/*     */       }
/* 620 */       if (jRootPane.getGlassPane() != null) {
/* 621 */         jRootPane.getGlassPane().setBounds(insets.left, insets.top, j, k);
/*     */       }
/*     */ 
/*     */       
/* 625 */       if (jRootPane.getWindowDecorationStyle() != 0 && jRootPane
/* 626 */         .getUI() instanceof MetalRootPaneUI) {
/* 627 */         JComponent jComponent = ((MetalRootPaneUI)jRootPane.getUI()).getTitlePane();
/*     */         
/* 629 */         if (jComponent != null) {
/* 630 */           Dimension dimension = jComponent.getPreferredSize();
/* 631 */           if (dimension != null) {
/* 632 */             int m = dimension.height;
/* 633 */             jComponent.setBounds(0, 0, j, m);
/* 634 */             i += m;
/*     */           } 
/*     */         } 
/*     */       } 
/* 638 */       if (jRootPane.getMenuBar() != null) {
/* 639 */         Dimension dimension = jRootPane.getMenuBar().getPreferredSize();
/* 640 */         jRootPane.getMenuBar().setBounds(0, i, j, dimension.height);
/* 641 */         i += dimension.height;
/*     */       } 
/* 643 */       if (jRootPane.getContentPane() != null) {
/* 644 */         Dimension dimension = jRootPane.getContentPane().getPreferredSize();
/* 645 */         jRootPane.getContentPane().setBounds(0, i, j, (k < i) ? 0 : (k - i));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void addLayoutComponent(Component param1Component, Object param1Object) {}
/*     */     
/*     */     public float getLayoutAlignmentX(Container param1Container) {
/* 653 */       return 0.0F; } public float getLayoutAlignmentY(Container param1Container) {
/* 654 */       return 0.0F;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void invalidateLayout(Container param1Container) {}
/*     */   }
/*     */ 
/*     */   
/* 663 */   private static final int[] cursorMapping = new int[] { 6, 6, 8, 7, 7, 6, 0, 0, 0, 7, 10, 0, 0, 0, 11, 4, 0, 0, 0, 5, 4, 4, 9, 5, 5 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class MouseInputHandler
/*     */     implements MouseInputListener
/*     */   {
/*     */     private boolean isMovingWindow;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int dragCursor;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int dragOffsetX;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int dragOffsetY;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int dragWidth;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int dragHeight;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private MouseInputHandler() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 710 */       JRootPane jRootPane = MetalRootPaneUI.this.getRootPane();
/*     */       
/* 712 */       if (jRootPane.getWindowDecorationStyle() == 0) {
/*     */         return;
/*     */       }
/* 715 */       Point point1 = param1MouseEvent.getPoint();
/* 716 */       Window window = (Window)param1MouseEvent.getSource();
/* 717 */       if (window != null) {
/* 718 */         window.toFront();
/*     */       }
/* 720 */       Point point2 = SwingUtilities.convertPoint(window, point1, MetalRootPaneUI.this
/* 721 */           .getTitlePane());
/*     */       
/* 723 */       Frame frame = null;
/* 724 */       Dialog dialog = null;
/*     */       
/* 726 */       if (window instanceof Frame) {
/* 727 */         frame = (Frame)window;
/* 728 */       } else if (window instanceof Dialog) {
/* 729 */         dialog = (Dialog)window;
/*     */       } 
/*     */       
/* 732 */       boolean bool = (frame != null) ? frame.getExtendedState() : false;
/*     */       
/* 734 */       if (MetalRootPaneUI.this.getTitlePane() != null && MetalRootPaneUI.this
/* 735 */         .getTitlePane().contains(point2)) {
/* 736 */         if (((frame != null && (bool & 0x6) == 0) || dialog != null) && point1.y >= 5 && point1.x >= 5 && point1.x < window
/*     */ 
/*     */ 
/*     */           
/* 740 */           .getWidth() - 5)
/*     */         {
/* 742 */           this.isMovingWindow = true;
/* 743 */           this.dragOffsetX = point1.x;
/* 744 */           this.dragOffsetY = point1.y;
/*     */         }
/*     */       
/* 747 */       } else if ((frame != null && frame.isResizable() && (bool & 0x6) == 0) || (dialog != null && dialog
/*     */         
/* 749 */         .isResizable())) {
/* 750 */         this.dragOffsetX = point1.x;
/* 751 */         this.dragOffsetY = point1.y;
/* 752 */         this.dragWidth = window.getWidth();
/* 753 */         this.dragHeight = window.getHeight();
/* 754 */         this.dragCursor = getCursor(calculateCorner(window, point1.x, point1.y));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 760 */       if (this.dragCursor != 0 && MetalRootPaneUI.this.window != null && !MetalRootPaneUI.this.window.isValid()) {
/*     */ 
/*     */         
/* 763 */         MetalRootPaneUI.this.window.validate();
/* 764 */         MetalRootPaneUI.this.getRootPane().repaint();
/*     */       } 
/* 766 */       this.isMovingWindow = false;
/* 767 */       this.dragCursor = 0;
/*     */     }
/*     */     
/*     */     public void mouseMoved(MouseEvent param1MouseEvent) {
/* 771 */       JRootPane jRootPane = MetalRootPaneUI.this.getRootPane();
/*     */       
/* 773 */       if (jRootPane.getWindowDecorationStyle() == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 777 */       Window window = (Window)param1MouseEvent.getSource();
/*     */       
/* 779 */       Frame frame = null;
/* 780 */       Dialog dialog = null;
/*     */       
/* 782 */       if (window instanceof Frame) {
/* 783 */         frame = (Frame)window;
/* 784 */       } else if (window instanceof Dialog) {
/* 785 */         dialog = (Dialog)window;
/*     */       } 
/*     */ 
/*     */       
/* 789 */       int i = getCursor(calculateCorner(window, param1MouseEvent.getX(), param1MouseEvent.getY()));
/*     */       
/* 791 */       if (i != 0 && ((frame != null && frame.isResizable() && (frame
/* 792 */         .getExtendedState() & 0x6) == 0) || (dialog != null && dialog
/* 793 */         .isResizable()))) {
/* 794 */         window.setCursor(Cursor.getPredefinedCursor(i));
/*     */       } else {
/*     */         
/* 797 */         window.setCursor(MetalRootPaneUI.this.lastCursor);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void adjust(Rectangle param1Rectangle, Dimension param1Dimension, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 803 */       param1Rectangle.x += param1Int1;
/* 804 */       param1Rectangle.y += param1Int2;
/* 805 */       param1Rectangle.width += param1Int3;
/* 806 */       param1Rectangle.height += param1Int4;
/* 807 */       if (param1Dimension != null) {
/* 808 */         if (param1Rectangle.width < param1Dimension.width) {
/* 809 */           int i = param1Dimension.width - param1Rectangle.width;
/* 810 */           if (param1Int1 != 0) {
/* 811 */             param1Rectangle.x -= i;
/*     */           }
/* 813 */           param1Rectangle.width = param1Dimension.width;
/*     */         } 
/* 815 */         if (param1Rectangle.height < param1Dimension.height) {
/* 816 */           int i = param1Dimension.height - param1Rectangle.height;
/* 817 */           if (param1Int2 != 0) {
/* 818 */             param1Rectangle.y -= i;
/*     */           }
/* 820 */           param1Rectangle.height = param1Dimension.height;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 826 */       Window window = (Window)param1MouseEvent.getSource();
/* 827 */       Point point = param1MouseEvent.getPoint();
/*     */       
/* 829 */       if (this.isMovingWindow) {
/* 830 */         Point point1 = param1MouseEvent.getLocationOnScreen();
/* 831 */         window.setLocation(point1.x - this.dragOffsetX, point1.y - this.dragOffsetY);
/*     */       
/*     */       }
/* 834 */       else if (this.dragCursor != 0) {
/* 835 */         Rectangle rectangle1 = window.getBounds();
/* 836 */         Rectangle rectangle2 = new Rectangle(rectangle1);
/* 837 */         Dimension dimension = window.getMinimumSize();
/*     */         
/* 839 */         switch (this.dragCursor) {
/*     */           case 11:
/* 841 */             adjust(rectangle1, dimension, 0, 0, point.x + this.dragWidth - this.dragOffsetX - rectangle1.width, 0);
/*     */             break;
/*     */           
/*     */           case 9:
/* 845 */             adjust(rectangle1, dimension, 0, 0, 0, point.y + this.dragHeight - this.dragOffsetY - rectangle1.height);
/*     */             break;
/*     */           
/*     */           case 8:
/* 849 */             adjust(rectangle1, dimension, 0, point.y - this.dragOffsetY, 0, -(point.y - this.dragOffsetY));
/*     */             break;
/*     */           
/*     */           case 10:
/* 853 */             adjust(rectangle1, dimension, point.x - this.dragOffsetX, 0, -(point.x - this.dragOffsetX), 0);
/*     */             break;
/*     */           
/*     */           case 7:
/* 857 */             adjust(rectangle1, dimension, 0, point.y - this.dragOffsetY, point.x + this.dragWidth - this.dragOffsetX - rectangle1.width, -(point.y - this.dragOffsetY));
/*     */             break;
/*     */ 
/*     */           
/*     */           case 5:
/* 862 */             adjust(rectangle1, dimension, 0, 0, point.x + this.dragWidth - this.dragOffsetX - rectangle1.width, point.y + this.dragHeight - this.dragOffsetY - rectangle1.height);
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case 6:
/* 868 */             adjust(rectangle1, dimension, point.x - this.dragOffsetX, point.y - this.dragOffsetY, -(point.x - this.dragOffsetX), -(point.y - this.dragOffsetY));
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case 4:
/* 874 */             adjust(rectangle1, dimension, point.x - this.dragOffsetX, 0, -(point.x - this.dragOffsetX), point.y + this.dragHeight - this.dragOffsetY - rectangle1.height);
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 881 */         if (!rectangle1.equals(rectangle2)) {
/* 882 */           window.setBounds(rectangle1);
/*     */ 
/*     */           
/* 885 */           if (Toolkit.getDefaultToolkit().isDynamicLayoutActive()) {
/* 886 */             window.validate();
/* 887 */             MetalRootPaneUI.this.getRootPane().repaint();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void mouseEntered(MouseEvent param1MouseEvent) {
/* 894 */       Window window = (Window)param1MouseEvent.getSource();
/* 895 */       MetalRootPaneUI.this.lastCursor = window.getCursor();
/* 896 */       mouseMoved(param1MouseEvent);
/*     */     }
/*     */     
/*     */     public void mouseExited(MouseEvent param1MouseEvent) {
/* 900 */       Window window = (Window)param1MouseEvent.getSource();
/* 901 */       window.setCursor(MetalRootPaneUI.this.lastCursor);
/*     */     }
/*     */     
/*     */     public void mouseClicked(MouseEvent param1MouseEvent) {
/* 905 */       Window window = (Window)param1MouseEvent.getSource();
/* 906 */       Frame frame = null;
/*     */       
/* 908 */       if (window instanceof Frame) {
/* 909 */         frame = (Frame)window;
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */       
/* 914 */       Point point = SwingUtilities.convertPoint(window, param1MouseEvent
/* 915 */           .getPoint(), MetalRootPaneUI.this.getTitlePane());
/*     */       
/* 917 */       int i = frame.getExtendedState();
/* 918 */       if (MetalRootPaneUI.this.getTitlePane() != null && MetalRootPaneUI.this
/* 919 */         .getTitlePane().contains(point) && 
/* 920 */         param1MouseEvent.getClickCount() % 2 == 0 && (param1MouseEvent
/* 921 */         .getModifiers() & 0x10) != 0 && 
/* 922 */         frame.isResizable()) {
/* 923 */         if ((i & 0x6) != 0) {
/* 924 */           frame.setExtendedState(i & 0xFFFFFFF9);
/*     */         } else {
/*     */           
/* 927 */           frame.setExtendedState(i | 0x6);
/*     */         } 
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int calculateCorner(Window param1Window, int param1Int1, int param1Int2) {
/* 940 */       Insets insets = param1Window.getInsets();
/* 941 */       int i = calculatePosition(param1Int1 - insets.left, param1Window
/* 942 */           .getWidth() - insets.left - insets.right);
/* 943 */       int j = calculatePosition(param1Int2 - insets.top, param1Window
/* 944 */           .getHeight() - insets.top - insets.bottom);
/*     */       
/* 946 */       if (i == -1 || j == -1) {
/* 947 */         return -1;
/*     */       }
/* 949 */       return j * 5 + i;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int getCursor(int param1Int) {
/* 957 */       if (param1Int == -1) {
/* 958 */         return 0;
/*     */       }
/* 960 */       return MetalRootPaneUI.cursorMapping[param1Int];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int calculatePosition(int param1Int1, int param1Int2) {
/* 974 */       if (param1Int1 < 5) {
/* 975 */         return 0;
/*     */       }
/* 977 */       if (param1Int1 < 16) {
/* 978 */         return 1;
/*     */       }
/* 980 */       if (param1Int1 >= param1Int2 - 5) {
/* 981 */         return 4;
/*     */       }
/* 983 */       if (param1Int1 >= param1Int2 - 16) {
/* 984 */         return 3;
/*     */       }
/* 986 */       return 2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalRootPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */