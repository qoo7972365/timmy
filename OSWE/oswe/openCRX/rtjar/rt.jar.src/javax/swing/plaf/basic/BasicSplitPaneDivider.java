/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.MouseAdapter;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseMotionListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JSplitPane;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import sun.swing.DefaultLookup;
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
/*      */ public class BasicSplitPaneDivider
/*      */   extends Container
/*      */   implements PropertyChangeListener
/*      */ {
/*      */   protected static final int ONE_TOUCH_SIZE = 6;
/*      */   protected static final int ONE_TOUCH_OFFSET = 2;
/*      */   protected DragController dragger;
/*      */   protected BasicSplitPaneUI splitPaneUI;
/*   85 */   protected int dividerSize = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Component hiddenDivider;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JSplitPane splitPane;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MouseHandler mouseHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int orientation;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JButton leftButton;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JButton rightButton;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Border border;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean mouseOver;
/*      */ 
/*      */ 
/*      */   
/*      */   private int oneTouchSize;
/*      */ 
/*      */ 
/*      */   
/*      */   private int oneTouchOffset;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean centerOneTouchButtons;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BasicSplitPaneDivider(BasicSplitPaneUI paramBasicSplitPaneUI) {
/*  142 */     this.oneTouchSize = DefaultLookup.getInt(paramBasicSplitPaneUI.getSplitPane(), paramBasicSplitPaneUI, "SplitPane.oneTouchButtonSize", 6);
/*      */     
/*  144 */     this.oneTouchOffset = DefaultLookup.getInt(paramBasicSplitPaneUI.getSplitPane(), paramBasicSplitPaneUI, "SplitPane.oneTouchButtonOffset", 2);
/*      */     
/*  146 */     this.centerOneTouchButtons = DefaultLookup.getBoolean(paramBasicSplitPaneUI.getSplitPane(), paramBasicSplitPaneUI, "SplitPane.centerOneTouchButtons", true);
/*      */     
/*  148 */     setLayout(new DividerLayout());
/*  149 */     setBasicSplitPaneUI(paramBasicSplitPaneUI);
/*  150 */     this.orientation = this.splitPane.getOrientation();
/*  151 */     setCursor((this.orientation == 1) ? 
/*  152 */         Cursor.getPredefinedCursor(11) : 
/*  153 */         Cursor.getPredefinedCursor(9));
/*  154 */     setBackground(UIManager.getColor("SplitPane.background"));
/*      */   }
/*      */   
/*      */   private void revalidateSplitPane() {
/*  158 */     invalidate();
/*  159 */     if (this.splitPane != null) {
/*  160 */       this.splitPane.revalidate();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBasicSplitPaneUI(BasicSplitPaneUI paramBasicSplitPaneUI) {
/*  168 */     if (this.splitPane != null) {
/*  169 */       this.splitPane.removePropertyChangeListener(this);
/*  170 */       if (this.mouseHandler != null) {
/*  171 */         this.splitPane.removeMouseListener(this.mouseHandler);
/*  172 */         this.splitPane.removeMouseMotionListener(this.mouseHandler);
/*  173 */         removeMouseListener(this.mouseHandler);
/*  174 */         removeMouseMotionListener(this.mouseHandler);
/*  175 */         this.mouseHandler = null;
/*      */       } 
/*      */     } 
/*  178 */     this.splitPaneUI = paramBasicSplitPaneUI;
/*  179 */     if (paramBasicSplitPaneUI != null) {
/*  180 */       this.splitPane = paramBasicSplitPaneUI.getSplitPane();
/*  181 */       if (this.splitPane != null) {
/*  182 */         if (this.mouseHandler == null) this.mouseHandler = new MouseHandler(); 
/*  183 */         this.splitPane.addMouseListener(this.mouseHandler);
/*  184 */         this.splitPane.addMouseMotionListener(this.mouseHandler);
/*  185 */         addMouseListener(this.mouseHandler);
/*  186 */         addMouseMotionListener(this.mouseHandler);
/*  187 */         this.splitPane.addPropertyChangeListener(this);
/*  188 */         if (this.splitPane.isOneTouchExpandable()) {
/*  189 */           oneTouchExpandableChanged();
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/*  194 */       this.splitPane = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BasicSplitPaneUI getBasicSplitPaneUI() {
/*  204 */     return this.splitPaneUI;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDividerSize(int paramInt) {
/*  214 */     this.dividerSize = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDividerSize() {
/*  223 */     return this.dividerSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBorder(Border paramBorder) {
/*  232 */     Border border = this.border;
/*      */     
/*  234 */     this.border = paramBorder;
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
/*      */   public Border getBorder() {
/*  246 */     return this.border;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Insets getInsets() {
/*  257 */     Border border = getBorder();
/*      */     
/*  259 */     if (border != null) {
/*  260 */       return border.getBorderInsets(this);
/*      */     }
/*  262 */     return super.getInsets();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setMouseOver(boolean paramBoolean) {
/*  272 */     this.mouseOver = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMouseOver() {
/*  282 */     return this.mouseOver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getPreferredSize() {
/*  293 */     if (this.orientation == 1) {
/*  294 */       return new Dimension(getDividerSize(), 1);
/*      */     }
/*  296 */     return new Dimension(1, getDividerSize());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMinimumSize() {
/*  303 */     return getPreferredSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/*  312 */     if (paramPropertyChangeEvent.getSource() == this.splitPane) {
/*  313 */       if (paramPropertyChangeEvent.getPropertyName() == "orientation") {
/*  314 */         this.orientation = this.splitPane.getOrientation();
/*  315 */         setCursor((this.orientation == 1) ? 
/*  316 */             Cursor.getPredefinedCursor(11) : 
/*  317 */             Cursor.getPredefinedCursor(9));
/*  318 */         revalidateSplitPane();
/*      */       }
/*  320 */       else if (paramPropertyChangeEvent.getPropertyName() == "oneTouchExpandable") {
/*      */         
/*  322 */         oneTouchExpandableChanged();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paint(Graphics paramGraphics) {
/*  332 */     super.paint(paramGraphics);
/*      */ 
/*      */     
/*  335 */     Border border = getBorder();
/*      */     
/*  337 */     if (border != null) {
/*  338 */       Dimension dimension = getSize();
/*      */       
/*  340 */       border.paintBorder(this, paramGraphics, 0, 0, dimension.width, dimension.height);
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
/*      */   protected void oneTouchExpandableChanged() {
/*  352 */     if (!DefaultLookup.getBoolean(this.splitPane, this.splitPaneUI, "SplitPane.supportsOneTouchButtons", true)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  357 */     if (this.splitPane.isOneTouchExpandable() && this.leftButton == null && this.rightButton == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  362 */       this.leftButton = createLeftOneTouchButton();
/*  363 */       if (this.leftButton != null) {
/*  364 */         this.leftButton.addActionListener(new OneTouchActionHandler(true));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  369 */       this.rightButton = createRightOneTouchButton();
/*  370 */       if (this.rightButton != null) {
/*  371 */         this.rightButton.addActionListener(new OneTouchActionHandler(false));
/*      */       }
/*      */       
/*  374 */       if (this.leftButton != null && this.rightButton != null) {
/*  375 */         add(this.leftButton);
/*  376 */         add(this.rightButton);
/*      */       } 
/*      */     } 
/*  379 */     revalidateSplitPane();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JButton createLeftOneTouchButton() {
/*  388 */     JButton jButton = new JButton() {
/*      */         public void setBorder(Border param1Border) {}
/*      */         
/*      */         public void paint(Graphics param1Graphics) {
/*  392 */           if (BasicSplitPaneDivider.this.splitPane != null) {
/*  393 */             int[] arrayOfInt1 = new int[3];
/*  394 */             int[] arrayOfInt2 = new int[3];
/*      */ 
/*      */ 
/*      */             
/*  398 */             param1Graphics.setColor(getBackground());
/*  399 */             param1Graphics.fillRect(0, 0, getWidth(), 
/*  400 */                 getHeight());
/*      */ 
/*      */             
/*  403 */             param1Graphics.setColor(Color.black);
/*  404 */             if (BasicSplitPaneDivider.this.orientation == 0) {
/*  405 */               int i = Math.min(getHeight(), BasicSplitPaneDivider.this.oneTouchSize);
/*  406 */               arrayOfInt1[0] = i;
/*  407 */               arrayOfInt1[1] = 0;
/*  408 */               arrayOfInt1[2] = i << 1;
/*  409 */               arrayOfInt2[0] = 0;
/*  410 */               arrayOfInt2[2] = i; arrayOfInt2[1] = i;
/*  411 */               param1Graphics.drawPolygon(arrayOfInt1, arrayOfInt2, 3);
/*      */             }
/*      */             else {
/*      */               
/*  415 */               int i = Math.min(getWidth(), BasicSplitPaneDivider.this.oneTouchSize);
/*  416 */               arrayOfInt1[2] = i; arrayOfInt1[0] = i;
/*  417 */               arrayOfInt1[1] = 0;
/*  418 */               arrayOfInt2[0] = 0;
/*  419 */               arrayOfInt2[1] = i;
/*  420 */               arrayOfInt2[2] = i << 1;
/*      */             } 
/*  422 */             param1Graphics.fillPolygon(arrayOfInt1, arrayOfInt2, 3);
/*      */           } 
/*      */         }
/*      */         
/*      */         public boolean isFocusTraversable() {
/*  427 */           return false;
/*      */         }
/*      */       };
/*  430 */     jButton.setMinimumSize(new Dimension(this.oneTouchSize, this.oneTouchSize));
/*  431 */     jButton.setCursor(Cursor.getPredefinedCursor(0));
/*  432 */     jButton.setFocusPainted(false);
/*  433 */     jButton.setBorderPainted(false);
/*  434 */     jButton.setRequestFocusEnabled(false);
/*  435 */     return jButton;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JButton createRightOneTouchButton() {
/*  444 */     JButton jButton = new JButton() {
/*      */         public void setBorder(Border param1Border) {}
/*      */         
/*      */         public void paint(Graphics param1Graphics) {
/*  448 */           if (BasicSplitPaneDivider.this.splitPane != null) {
/*  449 */             int[] arrayOfInt1 = new int[3];
/*  450 */             int[] arrayOfInt2 = new int[3];
/*      */ 
/*      */ 
/*      */             
/*  454 */             param1Graphics.setColor(getBackground());
/*  455 */             param1Graphics.fillRect(0, 0, getWidth(), 
/*  456 */                 getHeight());
/*      */ 
/*      */             
/*  459 */             if (BasicSplitPaneDivider.this.orientation == 0) {
/*  460 */               int i = Math.min(getHeight(), BasicSplitPaneDivider.this.oneTouchSize);
/*  461 */               arrayOfInt1[0] = i;
/*  462 */               arrayOfInt1[1] = i << 1;
/*  463 */               arrayOfInt1[2] = 0;
/*  464 */               arrayOfInt2[0] = i;
/*  465 */               arrayOfInt2[2] = 0; arrayOfInt2[1] = 0;
/*      */             } else {
/*      */               
/*  468 */               int i = Math.min(getWidth(), BasicSplitPaneDivider.this.oneTouchSize);
/*  469 */               arrayOfInt1[2] = 0; arrayOfInt1[0] = 0;
/*  470 */               arrayOfInt1[1] = i;
/*  471 */               arrayOfInt2[0] = 0;
/*  472 */               arrayOfInt2[1] = i;
/*  473 */               arrayOfInt2[2] = i << 1;
/*      */             } 
/*  475 */             param1Graphics.setColor(Color.black);
/*  476 */             param1Graphics.fillPolygon(arrayOfInt1, arrayOfInt2, 3);
/*      */           } 
/*      */         }
/*      */         
/*      */         public boolean isFocusTraversable() {
/*  481 */           return false;
/*      */         }
/*      */       };
/*  484 */     jButton.setMinimumSize(new Dimension(this.oneTouchSize, this.oneTouchSize));
/*  485 */     jButton.setCursor(Cursor.getPredefinedCursor(0));
/*  486 */     jButton.setFocusPainted(false);
/*  487 */     jButton.setBorderPainted(false);
/*  488 */     jButton.setRequestFocusEnabled(false);
/*  489 */     return jButton;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void prepareForDragging() {
/*  498 */     this.splitPaneUI.startDragging();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dragDividerTo(int paramInt) {
/*  507 */     this.splitPaneUI.dragDividerTo(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void finishDraggingTo(int paramInt) {
/*  516 */     this.splitPaneUI.finishDraggingTo(paramInt);
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
/*      */   protected class MouseHandler
/*      */     extends MouseAdapter
/*      */     implements MouseMotionListener
/*      */   {
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/*  534 */       if ((param1MouseEvent.getSource() == BasicSplitPaneDivider.this || param1MouseEvent
/*  535 */         .getSource() == BasicSplitPaneDivider.this.splitPane) && BasicSplitPaneDivider.this.dragger == null && BasicSplitPaneDivider.this.splitPane
/*  536 */         .isEnabled()) {
/*      */         
/*  538 */         Component component = BasicSplitPaneDivider.this.splitPaneUI.getNonContinuousLayoutDivider();
/*      */         
/*  540 */         if (BasicSplitPaneDivider.this.hiddenDivider != component) {
/*  541 */           if (BasicSplitPaneDivider.this.hiddenDivider != null) {
/*  542 */             BasicSplitPaneDivider.this.hiddenDivider.removeMouseListener(this);
/*  543 */             BasicSplitPaneDivider.this.hiddenDivider.removeMouseMotionListener(this);
/*      */           } 
/*  545 */           BasicSplitPaneDivider.this.hiddenDivider = component;
/*  546 */           if (BasicSplitPaneDivider.this.hiddenDivider != null) {
/*  547 */             BasicSplitPaneDivider.this.hiddenDivider.addMouseMotionListener(this);
/*  548 */             BasicSplitPaneDivider.this.hiddenDivider.addMouseListener(this);
/*      */           } 
/*      */         } 
/*  551 */         if (BasicSplitPaneDivider.this.splitPane.getLeftComponent() != null && BasicSplitPaneDivider.this.splitPane
/*  552 */           .getRightComponent() != null) {
/*  553 */           if (BasicSplitPaneDivider.this.orientation == 1) {
/*  554 */             BasicSplitPaneDivider.this.dragger = new BasicSplitPaneDivider.DragController(param1MouseEvent);
/*      */           } else {
/*      */             
/*  557 */             BasicSplitPaneDivider.this.dragger = new BasicSplitPaneDivider.VerticalDragController(param1MouseEvent);
/*      */           } 
/*  559 */           if (!BasicSplitPaneDivider.this.dragger.isValid()) {
/*  560 */             BasicSplitPaneDivider.this.dragger = null;
/*      */           } else {
/*      */             
/*  563 */             BasicSplitPaneDivider.this.prepareForDragging();
/*  564 */             BasicSplitPaneDivider.this.dragger.continueDrag(param1MouseEvent);
/*      */           } 
/*      */         } 
/*  567 */         param1MouseEvent.consume();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/*  576 */       if (BasicSplitPaneDivider.this.dragger != null) {
/*  577 */         if (param1MouseEvent.getSource() == BasicSplitPaneDivider.this.splitPane) {
/*  578 */           BasicSplitPaneDivider.this.dragger.completeDrag(param1MouseEvent.getX(), param1MouseEvent.getY());
/*      */         }
/*  580 */         else if (param1MouseEvent.getSource() == BasicSplitPaneDivider.this) {
/*  581 */           Point point = BasicSplitPaneDivider.this.getLocation();
/*      */           
/*  583 */           BasicSplitPaneDivider.this.dragger.completeDrag(param1MouseEvent.getX() + point.x, param1MouseEvent
/*  584 */               .getY() + point.y);
/*      */         }
/*  586 */         else if (param1MouseEvent.getSource() == BasicSplitPaneDivider.this.hiddenDivider) {
/*  587 */           Point point = BasicSplitPaneDivider.this.hiddenDivider.getLocation();
/*  588 */           int i = param1MouseEvent.getX() + point.x;
/*  589 */           int j = param1MouseEvent.getY() + point.y;
/*      */           
/*  591 */           BasicSplitPaneDivider.this.dragger.completeDrag(i, j);
/*      */         } 
/*  593 */         BasicSplitPaneDivider.this.dragger = null;
/*  594 */         param1MouseEvent.consume();
/*      */       } 
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
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/*  607 */       if (BasicSplitPaneDivider.this.dragger != null) {
/*  608 */         if (param1MouseEvent.getSource() == BasicSplitPaneDivider.this.splitPane) {
/*  609 */           BasicSplitPaneDivider.this.dragger.continueDrag(param1MouseEvent.getX(), param1MouseEvent.getY());
/*      */         }
/*  611 */         else if (param1MouseEvent.getSource() == BasicSplitPaneDivider.this) {
/*  612 */           Point point = BasicSplitPaneDivider.this.getLocation();
/*      */           
/*  614 */           BasicSplitPaneDivider.this.dragger.continueDrag(param1MouseEvent.getX() + point.x, param1MouseEvent
/*  615 */               .getY() + point.y);
/*      */         }
/*  617 */         else if (param1MouseEvent.getSource() == BasicSplitPaneDivider.this.hiddenDivider) {
/*  618 */           Point point = BasicSplitPaneDivider.this.hiddenDivider.getLocation();
/*  619 */           int i = param1MouseEvent.getX() + point.x;
/*  620 */           int j = param1MouseEvent.getY() + point.y;
/*      */           
/*  622 */           BasicSplitPaneDivider.this.dragger.continueDrag(i, j);
/*      */         } 
/*  624 */         param1MouseEvent.consume();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {
/*  642 */       if (param1MouseEvent.getSource() == BasicSplitPaneDivider.this) {
/*  643 */         BasicSplitPaneDivider.this.setMouseOver(true);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {
/*  654 */       if (param1MouseEvent.getSource() == BasicSplitPaneDivider.this) {
/*  655 */         BasicSplitPaneDivider.this.setMouseOver(false);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class DragController
/*      */   {
/*      */     int initialX;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int maxX;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int minX;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int offset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected DragController(MouseEvent param1MouseEvent) {
/*  697 */       JSplitPane jSplitPane = BasicSplitPaneDivider.this.splitPaneUI.getSplitPane();
/*  698 */       Component component1 = jSplitPane.getLeftComponent();
/*  699 */       Component component2 = jSplitPane.getRightComponent();
/*      */       
/*  701 */       this.initialX = (BasicSplitPaneDivider.this.getLocation()).x;
/*  702 */       if (param1MouseEvent.getSource() == BasicSplitPaneDivider.this) {
/*  703 */         this.offset = param1MouseEvent.getX();
/*      */       } else {
/*      */         
/*  706 */         this.offset = param1MouseEvent.getX() - this.initialX;
/*      */       } 
/*  708 */       if (component1 == null || component2 == null || this.offset < -1 || this.offset >= 
/*  709 */         (BasicSplitPaneDivider.this.getSize()).width) {
/*      */         
/*  711 */         this.maxX = -1;
/*      */       } else {
/*      */         
/*  714 */         Insets insets = jSplitPane.getInsets();
/*      */         
/*  716 */         if (component1.isVisible()) {
/*  717 */           this.minX = (component1.getMinimumSize()).width;
/*  718 */           if (insets != null) {
/*  719 */             this.minX += insets.left;
/*      */           }
/*      */         } else {
/*      */           
/*  723 */           this.minX = 0;
/*      */         } 
/*  725 */         if (component2.isVisible()) {
/*  726 */           byte b = (insets != null) ? insets.right : 0;
/*  727 */           this.maxX = Math.max(0, (jSplitPane.getSize()).width - 
/*  728 */               (BasicSplitPaneDivider.this.getSize()).width + b - 
/*  729 */               (component2.getMinimumSize()).width);
/*      */         } else {
/*      */           
/*  732 */           byte b = (insets != null) ? insets.right : 0;
/*  733 */           this.maxX = Math.max(0, (jSplitPane.getSize()).width - 
/*  734 */               (BasicSplitPaneDivider.this.getSize()).width + b);
/*      */         } 
/*  736 */         if (this.maxX < this.minX) this.minX = this.maxX = 0;
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean isValid() {
/*  745 */       return (this.maxX > 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int positionForMouseEvent(MouseEvent param1MouseEvent) {
/*  755 */       int i = (param1MouseEvent.getSource() == BasicSplitPaneDivider.this) ? (param1MouseEvent.getX() + (BasicSplitPaneDivider.this.getLocation()).x) : param1MouseEvent.getX();
/*      */       
/*  757 */       i = Math.min(this.maxX, Math.max(this.minX, i - this.offset));
/*  758 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getNeededLocation(int param1Int1, int param1Int2) {
/*  769 */       return Math.min(this.maxX, Math.max(this.minX, param1Int1 - this.offset));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void continueDrag(int param1Int1, int param1Int2) {
/*  775 */       BasicSplitPaneDivider.this.dragDividerTo(getNeededLocation(param1Int1, param1Int2));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void continueDrag(MouseEvent param1MouseEvent) {
/*  784 */       BasicSplitPaneDivider.this.dragDividerTo(positionForMouseEvent(param1MouseEvent));
/*      */     }
/*      */ 
/*      */     
/*      */     protected void completeDrag(int param1Int1, int param1Int2) {
/*  789 */       BasicSplitPaneDivider.this.finishDraggingTo(getNeededLocation(param1Int1, param1Int2));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void completeDrag(MouseEvent param1MouseEvent) {
/*  798 */       BasicSplitPaneDivider.this.finishDraggingTo(positionForMouseEvent(param1MouseEvent));
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
/*      */   protected class VerticalDragController
/*      */     extends DragController
/*      */   {
/*      */     protected VerticalDragController(MouseEvent param1MouseEvent) {
/*  815 */       super(param1MouseEvent);
/*  816 */       JSplitPane jSplitPane = BasicSplitPaneDivider.this.splitPaneUI.getSplitPane();
/*  817 */       Component component1 = jSplitPane.getLeftComponent();
/*  818 */       Component component2 = jSplitPane.getRightComponent();
/*      */       
/*  820 */       this.initialX = (BasicSplitPaneDivider.this.getLocation()).y;
/*  821 */       if (param1MouseEvent.getSource() == BasicSplitPaneDivider.this) {
/*  822 */         this.offset = param1MouseEvent.getY();
/*      */       } else {
/*      */         
/*  825 */         this.offset = param1MouseEvent.getY() - this.initialX;
/*      */       } 
/*  827 */       if (component1 == null || component2 == null || this.offset < -1 || this.offset > 
/*  828 */         (BasicSplitPaneDivider.this.getSize()).height) {
/*      */         
/*  830 */         this.maxX = -1;
/*      */       } else {
/*      */         
/*  833 */         Insets insets = jSplitPane.getInsets();
/*      */         
/*  835 */         if (component1.isVisible()) {
/*  836 */           this.minX = (component1.getMinimumSize()).height;
/*  837 */           if (insets != null) {
/*  838 */             this.minX += insets.top;
/*      */           }
/*      */         } else {
/*      */           
/*  842 */           this.minX = 0;
/*      */         } 
/*  844 */         if (component2.isVisible()) {
/*  845 */           byte b = (insets != null) ? insets.bottom : 0;
/*      */           
/*  847 */           this.maxX = Math.max(0, (jSplitPane.getSize()).height - 
/*  848 */               (BasicSplitPaneDivider.this.getSize()).height + b - 
/*  849 */               (component2.getMinimumSize()).height);
/*      */         } else {
/*      */           
/*  852 */           byte b = (insets != null) ? insets.bottom : 0;
/*      */           
/*  854 */           this.maxX = Math.max(0, (jSplitPane.getSize()).height - 
/*  855 */               (BasicSplitPaneDivider.this.getSize()).height + b);
/*      */         } 
/*  857 */         if (this.maxX < this.minX) this.minX = this.maxX = 0;
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getNeededLocation(int param1Int1, int param1Int2) {
/*  869 */       return Math.min(this.maxX, Math.max(this.minX, param1Int2 - this.offset));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int positionForMouseEvent(MouseEvent param1MouseEvent) {
/*  880 */       int i = (param1MouseEvent.getSource() == BasicSplitPaneDivider.this) ? (param1MouseEvent.getY() + (BasicSplitPaneDivider.this.getLocation()).y) : param1MouseEvent.getY();
/*      */ 
/*      */       
/*  883 */       i = Math.min(this.maxX, Math.max(this.minX, i - this.offset));
/*  884 */       return i;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class DividerLayout
/*      */     implements LayoutManager
/*      */   {
/*      */     public void layoutContainer(Container param1Container) {
/*  898 */       if (BasicSplitPaneDivider.this.leftButton != null && BasicSplitPaneDivider.this.rightButton != null && param1Container == BasicSplitPaneDivider.this)
/*      */       {
/*  900 */         if (BasicSplitPaneDivider.this.splitPane.isOneTouchExpandable()) {
/*  901 */           Insets insets = BasicSplitPaneDivider.this.getInsets();
/*      */           
/*  903 */           if (BasicSplitPaneDivider.this.orientation == 0) {
/*  904 */             byte b = (insets != null) ? insets.left : 0;
/*  905 */             int i = BasicSplitPaneDivider.this.getHeight();
/*      */             
/*  907 */             if (insets != null) {
/*  908 */               i -= insets.top + insets.bottom;
/*  909 */               i = Math.max(i, 0);
/*      */             } 
/*  911 */             i = Math.min(i, BasicSplitPaneDivider.this.oneTouchSize);
/*      */             
/*  913 */             int j = ((param1Container.getSize()).height - i) / 2;
/*      */             
/*  915 */             if (!BasicSplitPaneDivider.this.centerOneTouchButtons) {
/*  916 */               j = (insets != null) ? insets.top : 0;
/*  917 */               b = 0;
/*      */             } 
/*  919 */             BasicSplitPaneDivider.this.leftButton.setBounds(b + BasicSplitPaneDivider.this.oneTouchOffset, j, i * 2, i);
/*      */             
/*  921 */             BasicSplitPaneDivider.this.rightButton.setBounds(b + BasicSplitPaneDivider.this.oneTouchOffset + BasicSplitPaneDivider.this
/*  922 */                 .oneTouchSize * 2, j, i * 2, i);
/*      */           }
/*      */           else {
/*      */             
/*  926 */             byte b = (insets != null) ? insets.top : 0;
/*  927 */             int i = BasicSplitPaneDivider.this.getWidth();
/*      */             
/*  929 */             if (insets != null) {
/*  930 */               i -= insets.left + insets.right;
/*  931 */               i = Math.max(i, 0);
/*      */             } 
/*  933 */             i = Math.min(i, BasicSplitPaneDivider.this.oneTouchSize);
/*      */             
/*  935 */             int j = ((param1Container.getSize()).width - i) / 2;
/*      */             
/*  937 */             if (!BasicSplitPaneDivider.this.centerOneTouchButtons) {
/*  938 */               j = (insets != null) ? insets.left : 0;
/*  939 */               b = 0;
/*      */             } 
/*      */             
/*  942 */             BasicSplitPaneDivider.this.leftButton.setBounds(j, b + BasicSplitPaneDivider.this.oneTouchOffset, i, i * 2);
/*      */             
/*  944 */             BasicSplitPaneDivider.this.rightButton.setBounds(j, b + BasicSplitPaneDivider.this.oneTouchOffset + BasicSplitPaneDivider.this
/*  945 */                 .oneTouchSize * 2, i, i * 2);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  950 */           BasicSplitPaneDivider.this.leftButton.setBounds(-5, -5, 1, 1);
/*  951 */           BasicSplitPaneDivider.this.rightButton.setBounds(-5, -5, 1, 1);
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Dimension minimumLayoutSize(Container param1Container) {
/*  961 */       if (param1Container != BasicSplitPaneDivider.this || BasicSplitPaneDivider.this.splitPane == null) {
/*  962 */         return new Dimension(0, 0);
/*      */       }
/*  964 */       Dimension dimension = null;
/*      */       
/*  966 */       if (BasicSplitPaneDivider.this.splitPane.isOneTouchExpandable() && BasicSplitPaneDivider.this.leftButton != null) {
/*  967 */         dimension = BasicSplitPaneDivider.this.leftButton.getMinimumSize();
/*      */       }
/*      */       
/*  970 */       Insets insets = BasicSplitPaneDivider.this.getInsets();
/*  971 */       int i = BasicSplitPaneDivider.this.getDividerSize();
/*  972 */       int j = i;
/*      */       
/*  974 */       if (BasicSplitPaneDivider.this.orientation == 0) {
/*  975 */         if (dimension != null) {
/*  976 */           int k = dimension.height;
/*  977 */           if (insets != null) {
/*  978 */             k += insets.top + insets.bottom;
/*      */           }
/*  980 */           j = Math.max(j, k);
/*      */         } 
/*  982 */         i = 1;
/*      */       } else {
/*      */         
/*  985 */         if (dimension != null) {
/*  986 */           int k = dimension.width;
/*  987 */           if (insets != null) {
/*  988 */             k += insets.left + insets.right;
/*      */           }
/*  990 */           i = Math.max(i, k);
/*      */         } 
/*  992 */         j = 1;
/*      */       } 
/*  994 */       return new Dimension(i, j);
/*      */     }
/*      */ 
/*      */     
/*      */     public Dimension preferredLayoutSize(Container param1Container) {
/*  999 */       return minimumLayoutSize(param1Container);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeLayoutComponent(Component param1Component) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void addLayoutComponent(String param1String, Component param1Component) {}
/*      */   }
/*      */ 
/*      */   
/*      */   private class OneTouchActionHandler
/*      */     implements ActionListener
/*      */   {
/*      */     private boolean toMinimum;
/*      */ 
/*      */     
/*      */     OneTouchActionHandler(boolean param1Boolean) {
/* 1019 */       this.toMinimum = param1Boolean;
/*      */     }
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*      */       int k;
/* 1023 */       Insets insets = BasicSplitPaneDivider.this.splitPane.getInsets();
/* 1024 */       int i = BasicSplitPaneDivider.this.splitPane.getLastDividerLocation();
/* 1025 */       int j = BasicSplitPaneDivider.this.splitPaneUI.getDividerLocation(BasicSplitPaneDivider.this.splitPane);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1030 */       if (this.toMinimum) {
/* 1031 */         if (BasicSplitPaneDivider.this.orientation == 0) {
/* 1032 */           if (j >= BasicSplitPaneDivider.this.splitPane.getHeight() - insets.bottom - BasicSplitPaneDivider.this
/* 1033 */             .getHeight()) {
/* 1034 */             int m = BasicSplitPaneDivider.this.splitPane.getMaximumDividerLocation();
/* 1035 */             k = Math.min(i, m);
/* 1036 */             BasicSplitPaneDivider.this.splitPaneUI.setKeepHidden(false);
/*      */           } else {
/*      */             
/* 1039 */             k = insets.top;
/* 1040 */             BasicSplitPaneDivider.this.splitPaneUI.setKeepHidden(true);
/*      */           }
/*      */         
/*      */         }
/* 1044 */         else if (j >= BasicSplitPaneDivider.this.splitPane.getWidth() - insets.right - BasicSplitPaneDivider.this
/* 1045 */           .getWidth()) {
/* 1046 */           int m = BasicSplitPaneDivider.this.splitPane.getMaximumDividerLocation();
/* 1047 */           k = Math.min(i, m);
/* 1048 */           BasicSplitPaneDivider.this.splitPaneUI.setKeepHidden(false);
/*      */         } else {
/*      */           
/* 1051 */           k = insets.left;
/* 1052 */           BasicSplitPaneDivider.this.splitPaneUI.setKeepHidden(true);
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 1057 */       else if (BasicSplitPaneDivider.this.orientation == 0) {
/* 1058 */         if (j == insets.top) {
/* 1059 */           int m = BasicSplitPaneDivider.this.splitPane.getMaximumDividerLocation();
/* 1060 */           k = Math.min(i, m);
/* 1061 */           BasicSplitPaneDivider.this.splitPaneUI.setKeepHidden(false);
/*      */         } else {
/*      */           
/* 1064 */           k = BasicSplitPaneDivider.this.splitPane.getHeight() - BasicSplitPaneDivider.this.getHeight() - insets.top;
/*      */           
/* 1066 */           BasicSplitPaneDivider.this.splitPaneUI.setKeepHidden(true);
/*      */         }
/*      */       
/*      */       }
/* 1070 */       else if (j == insets.left) {
/* 1071 */         int m = BasicSplitPaneDivider.this.splitPane.getMaximumDividerLocation();
/* 1072 */         k = Math.min(i, m);
/* 1073 */         BasicSplitPaneDivider.this.splitPaneUI.setKeepHidden(false);
/*      */       } else {
/*      */         
/* 1076 */         k = BasicSplitPaneDivider.this.splitPane.getWidth() - BasicSplitPaneDivider.this.getWidth() - insets.left;
/*      */         
/* 1078 */         BasicSplitPaneDivider.this.splitPaneUI.setKeepHidden(true);
/*      */       } 
/*      */ 
/*      */       
/* 1082 */       if (j != k) {
/* 1083 */         BasicSplitPaneDivider.this.splitPane.setDividerLocation(k);
/*      */ 
/*      */         
/* 1086 */         BasicSplitPaneDivider.this.splitPane.setLastDividerLocation(j);
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicSplitPaneDivider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */