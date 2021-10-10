/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Rectangle;
/*      */ import java.io.Serializable;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.plaf.UIResource;
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
/*      */ public class ScrollPaneLayout
/*      */   implements LayoutManager, ScrollPaneConstants, Serializable
/*      */ {
/*      */   protected JViewport viewport;
/*      */   protected JScrollBar vsb;
/*      */   protected JScrollBar hsb;
/*      */   protected JViewport rowHead;
/*      */   protected JViewport colHead;
/*      */   protected Component lowerLeft;
/*      */   protected Component lowerRight;
/*      */   protected Component upperLeft;
/*      */   protected Component upperRight;
/*  142 */   protected int vsbPolicy = 20;
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
/*  153 */   protected int hsbPolicy = 30;
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
/*      */   public void syncWithScrollPane(JScrollPane paramJScrollPane) {
/*  172 */     this.viewport = paramJScrollPane.getViewport();
/*  173 */     this.vsb = paramJScrollPane.getVerticalScrollBar();
/*  174 */     this.hsb = paramJScrollPane.getHorizontalScrollBar();
/*  175 */     this.rowHead = paramJScrollPane.getRowHeader();
/*  176 */     this.colHead = paramJScrollPane.getColumnHeader();
/*  177 */     this.lowerLeft = paramJScrollPane.getCorner("LOWER_LEFT_CORNER");
/*  178 */     this.lowerRight = paramJScrollPane.getCorner("LOWER_RIGHT_CORNER");
/*  179 */     this.upperLeft = paramJScrollPane.getCorner("UPPER_LEFT_CORNER");
/*  180 */     this.upperRight = paramJScrollPane.getCorner("UPPER_RIGHT_CORNER");
/*  181 */     this.vsbPolicy = paramJScrollPane.getVerticalScrollBarPolicy();
/*  182 */     this.hsbPolicy = paramJScrollPane.getHorizontalScrollBarPolicy();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Component addSingletonComponent(Component paramComponent1, Component paramComponent2) {
/*  201 */     if (paramComponent1 != null && paramComponent1 != paramComponent2) {
/*  202 */       paramComponent1.getParent().remove(paramComponent1);
/*      */     }
/*  204 */     return paramComponent2;
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
/*      */   public void addLayoutComponent(String paramString, Component paramComponent) {
/*  229 */     if (paramString.equals("VIEWPORT")) {
/*  230 */       this.viewport = (JViewport)addSingletonComponent(this.viewport, paramComponent);
/*      */     }
/*  232 */     else if (paramString.equals("VERTICAL_SCROLLBAR")) {
/*  233 */       this.vsb = (JScrollBar)addSingletonComponent(this.vsb, paramComponent);
/*      */     }
/*  235 */     else if (paramString.equals("HORIZONTAL_SCROLLBAR")) {
/*  236 */       this.hsb = (JScrollBar)addSingletonComponent(this.hsb, paramComponent);
/*      */     }
/*  238 */     else if (paramString.equals("ROW_HEADER")) {
/*  239 */       this.rowHead = (JViewport)addSingletonComponent(this.rowHead, paramComponent);
/*      */     }
/*  241 */     else if (paramString.equals("COLUMN_HEADER")) {
/*  242 */       this.colHead = (JViewport)addSingletonComponent(this.colHead, paramComponent);
/*      */     }
/*  244 */     else if (paramString.equals("LOWER_LEFT_CORNER")) {
/*  245 */       this.lowerLeft = addSingletonComponent(this.lowerLeft, paramComponent);
/*      */     }
/*  247 */     else if (paramString.equals("LOWER_RIGHT_CORNER")) {
/*  248 */       this.lowerRight = addSingletonComponent(this.lowerRight, paramComponent);
/*      */     }
/*  250 */     else if (paramString.equals("UPPER_LEFT_CORNER")) {
/*  251 */       this.upperLeft = addSingletonComponent(this.upperLeft, paramComponent);
/*      */     }
/*  253 */     else if (paramString.equals("UPPER_RIGHT_CORNER")) {
/*  254 */       this.upperRight = addSingletonComponent(this.upperRight, paramComponent);
/*      */     } else {
/*      */       
/*  257 */       throw new IllegalArgumentException("invalid layout key " + paramString);
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
/*      */   public void removeLayoutComponent(Component paramComponent) {
/*  269 */     if (paramComponent == this.viewport) {
/*  270 */       this.viewport = null;
/*      */     }
/*  272 */     else if (paramComponent == this.vsb) {
/*  273 */       this.vsb = null;
/*      */     }
/*  275 */     else if (paramComponent == this.hsb) {
/*  276 */       this.hsb = null;
/*      */     }
/*  278 */     else if (paramComponent == this.rowHead) {
/*  279 */       this.rowHead = null;
/*      */     }
/*  281 */     else if (paramComponent == this.colHead) {
/*  282 */       this.colHead = null;
/*      */     }
/*  284 */     else if (paramComponent == this.lowerLeft) {
/*  285 */       this.lowerLeft = null;
/*      */     }
/*  287 */     else if (paramComponent == this.lowerRight) {
/*  288 */       this.lowerRight = null;
/*      */     }
/*  290 */     else if (paramComponent == this.upperLeft) {
/*  291 */       this.upperLeft = null;
/*      */     }
/*  293 */     else if (paramComponent == this.upperRight) {
/*  294 */       this.upperRight = null;
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
/*      */   public int getVerticalScrollBarPolicy() {
/*  306 */     return this.vsbPolicy;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVerticalScrollBarPolicy(int paramInt) {
/*  327 */     switch (paramInt) {
/*      */       case 20:
/*      */       case 21:
/*      */       case 22:
/*  331 */         this.vsbPolicy = paramInt;
/*      */         return;
/*      */     } 
/*  334 */     throw new IllegalArgumentException("invalid verticalScrollBarPolicy");
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
/*      */   public int getHorizontalScrollBarPolicy() {
/*  346 */     return this.hsbPolicy;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHorizontalScrollBarPolicy(int paramInt) {
/*  365 */     switch (paramInt) {
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*  369 */         this.hsbPolicy = paramInt;
/*      */         return;
/*      */     } 
/*  372 */     throw new IllegalArgumentException("invalid horizontalScrollBarPolicy");
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
/*      */   public JViewport getViewport() {
/*  384 */     return this.viewport;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JScrollBar getHorizontalScrollBar() {
/*  394 */     return this.hsb;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JScrollBar getVerticalScrollBar() {
/*  403 */     return this.vsb;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JViewport getRowHeader() {
/*  413 */     return this.rowHead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JViewport getColumnHeader() {
/*  423 */     return this.colHead;
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
/*      */   public Component getCorner(String paramString) {
/*  436 */     if (paramString.equals("LOWER_LEFT_CORNER")) {
/*  437 */       return this.lowerLeft;
/*      */     }
/*  439 */     if (paramString.equals("LOWER_RIGHT_CORNER")) {
/*  440 */       return this.lowerRight;
/*      */     }
/*  442 */     if (paramString.equals("UPPER_LEFT_CORNER")) {
/*  443 */       return this.upperLeft;
/*      */     }
/*  445 */     if (paramString.equals("UPPER_RIGHT_CORNER")) {
/*  446 */       return this.upperRight;
/*      */     }
/*      */     
/*  449 */     return null;
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
/*      */   public Dimension preferredLayoutSize(Container paramContainer) {
/*  474 */     JScrollPane jScrollPane = (JScrollPane)paramContainer;
/*  475 */     this.vsbPolicy = jScrollPane.getVerticalScrollBarPolicy();
/*  476 */     this.hsbPolicy = jScrollPane.getHorizontalScrollBarPolicy();
/*      */     
/*  478 */     Insets insets = paramContainer.getInsets();
/*  479 */     int i = insets.left + insets.right;
/*  480 */     int j = insets.top + insets.bottom;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  487 */     Dimension dimension1 = null;
/*  488 */     Dimension dimension2 = null;
/*  489 */     Component component = null;
/*      */     
/*  491 */     if (this.viewport != null) {
/*  492 */       dimension1 = this.viewport.getPreferredSize();
/*  493 */       component = this.viewport.getView();
/*  494 */       if (component != null) {
/*  495 */         dimension2 = component.getPreferredSize();
/*      */       } else {
/*  497 */         dimension2 = new Dimension(0, 0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  504 */     if (dimension1 != null) {
/*  505 */       i += dimension1.width;
/*  506 */       j += dimension1.height;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  512 */     Border border = jScrollPane.getViewportBorder();
/*  513 */     if (border != null) {
/*  514 */       Insets insets1 = border.getBorderInsets(paramContainer);
/*  515 */       i += insets1.left + insets1.right;
/*  516 */       j += insets1.top + insets1.bottom;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  523 */     if (this.rowHead != null && this.rowHead.isVisible()) {
/*  524 */       i += (this.rowHead.getPreferredSize()).width;
/*      */     }
/*      */     
/*  527 */     if (this.colHead != null && this.colHead.isVisible()) {
/*  528 */       j += (this.colHead.getPreferredSize()).height;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  548 */     if (this.vsb != null && this.vsbPolicy != 21) {
/*  549 */       if (this.vsbPolicy == 22) {
/*  550 */         i += (this.vsb.getPreferredSize()).width;
/*      */       }
/*  552 */       else if (dimension2 != null && dimension1 != null) {
/*  553 */         boolean bool = true;
/*  554 */         if (component instanceof Scrollable) {
/*  555 */           bool = !((Scrollable)component).getScrollableTracksViewportHeight() ? true : false;
/*      */         }
/*  557 */         if (bool && dimension2.height > dimension1.height) {
/*  558 */           i += (this.vsb.getPreferredSize()).width;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  563 */     if (this.hsb != null && this.hsbPolicy != 31) {
/*  564 */       if (this.hsbPolicy == 32) {
/*  565 */         j += (this.hsb.getPreferredSize()).height;
/*      */       }
/*  567 */       else if (dimension2 != null && dimension1 != null) {
/*  568 */         boolean bool = true;
/*  569 */         if (component instanceof Scrollable) {
/*  570 */           bool = !((Scrollable)component).getScrollableTracksViewportWidth() ? true : false;
/*      */         }
/*  572 */         if (bool && dimension2.width > dimension1.width) {
/*  573 */           j += (this.hsb.getPreferredSize()).height;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  578 */     return new Dimension(i, j);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension minimumLayoutSize(Container paramContainer) {
/*  597 */     JScrollPane jScrollPane = (JScrollPane)paramContainer;
/*  598 */     this.vsbPolicy = jScrollPane.getVerticalScrollBarPolicy();
/*  599 */     this.hsbPolicy = jScrollPane.getHorizontalScrollBarPolicy();
/*      */     
/*  601 */     Insets insets = paramContainer.getInsets();
/*  602 */     int i = insets.left + insets.right;
/*  603 */     int j = insets.top + insets.bottom;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  608 */     if (this.viewport != null) {
/*  609 */       Dimension dimension = this.viewport.getMinimumSize();
/*  610 */       i += dimension.width;
/*  611 */       j += dimension.height;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  617 */     Border border = jScrollPane.getViewportBorder();
/*  618 */     if (border != null) {
/*  619 */       Insets insets1 = border.getBorderInsets(paramContainer);
/*  620 */       i += insets1.left + insets1.right;
/*  621 */       j += insets1.top + insets1.bottom;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  628 */     if (this.rowHead != null && this.rowHead.isVisible()) {
/*  629 */       Dimension dimension = this.rowHead.getMinimumSize();
/*  630 */       i += dimension.width;
/*  631 */       j = Math.max(j, dimension.height);
/*      */     } 
/*      */     
/*  634 */     if (this.colHead != null && this.colHead.isVisible()) {
/*  635 */       Dimension dimension = this.colHead.getMinimumSize();
/*  636 */       i = Math.max(i, dimension.width);
/*  637 */       j += dimension.height;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  644 */     if (this.vsb != null && this.vsbPolicy != 21) {
/*  645 */       Dimension dimension = this.vsb.getMinimumSize();
/*  646 */       i += dimension.width;
/*  647 */       j = Math.max(j, dimension.height);
/*      */     } 
/*      */     
/*  650 */     if (this.hsb != null && this.hsbPolicy != 31) {
/*  651 */       Dimension dimension = this.hsb.getMinimumSize();
/*  652 */       i = Math.max(i, dimension.width);
/*  653 */       j += dimension.height;
/*      */     } 
/*      */     
/*  656 */     return new Dimension(i, j);
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
/*      */   public void layoutContainer(Container paramContainer) {
/*      */     Insets insets2;
/*      */     Scrollable scrollable;
/*      */     boolean bool5, bool6;
/*  698 */     JScrollPane jScrollPane = (JScrollPane)paramContainer;
/*  699 */     this.vsbPolicy = jScrollPane.getVerticalScrollBarPolicy();
/*  700 */     this.hsbPolicy = jScrollPane.getHorizontalScrollBarPolicy();
/*      */     
/*  702 */     Rectangle rectangle1 = jScrollPane.getBounds();
/*  703 */     rectangle1.x = rectangle1.y = 0;
/*      */     
/*  705 */     Insets insets1 = paramContainer.getInsets();
/*  706 */     rectangle1.x = insets1.left;
/*  707 */     rectangle1.y = insets1.top;
/*  708 */     rectangle1.width -= insets1.left + insets1.right;
/*  709 */     rectangle1.height -= insets1.top + insets1.bottom;
/*      */ 
/*      */ 
/*      */     
/*  713 */     boolean bool1 = SwingUtilities.isLeftToRight(jScrollPane);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  720 */     Rectangle rectangle2 = new Rectangle(0, rectangle1.y, 0, 0);
/*      */     
/*  722 */     if (this.colHead != null && this.colHead.isVisible()) {
/*  723 */       int i = Math.min(rectangle1.height, 
/*  724 */           (this.colHead.getPreferredSize()).height);
/*  725 */       rectangle2.height = i;
/*  726 */       rectangle1.y += i;
/*  727 */       rectangle1.height -= i;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  735 */     Rectangle rectangle3 = new Rectangle(0, 0, 0, 0);
/*      */     
/*  737 */     if (this.rowHead != null && this.rowHead.isVisible()) {
/*  738 */       int i = Math.min(rectangle1.width, 
/*  739 */           (this.rowHead.getPreferredSize()).width);
/*  740 */       rectangle3.width = i;
/*  741 */       rectangle1.width -= i;
/*  742 */       if (bool1) {
/*  743 */         rectangle3.x = rectangle1.x;
/*  744 */         rectangle1.x += i;
/*      */       } else {
/*  746 */         rectangle1.x += rectangle1.width;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  754 */     Border border = jScrollPane.getViewportBorder();
/*      */     
/*  756 */     if (border != null) {
/*  757 */       insets2 = border.getBorderInsets(paramContainer);
/*  758 */       rectangle1.x += insets2.left;
/*  759 */       rectangle1.y += insets2.top;
/*  760 */       rectangle1.width -= insets2.left + insets2.right;
/*  761 */       rectangle1.height -= insets2.top + insets2.bottom;
/*      */     } else {
/*      */       
/*  764 */       insets2 = new Insets(0, 0, 0, 0);
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
/*  789 */     Component component = (this.viewport != null) ? this.viewport.getView() : null;
/*      */     
/*  791 */     Dimension dimension1 = (component != null) ? component.getPreferredSize() : new Dimension(0, 0);
/*      */ 
/*      */ 
/*      */     
/*  795 */     Dimension dimension2 = (this.viewport != null) ? this.viewport.toViewCoordinates(rectangle1.getSize()) : new Dimension(0, 0);
/*      */ 
/*      */     
/*  798 */     boolean bool2 = false;
/*  799 */     boolean bool3 = false;
/*  800 */     boolean bool4 = (rectangle1.width < 0 || rectangle1.height < 0) ? true : false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  805 */     if (!bool4 && component instanceof Scrollable) {
/*  806 */       scrollable = (Scrollable)component;
/*  807 */       bool2 = scrollable.getScrollableTracksViewportWidth();
/*  808 */       bool3 = scrollable.getScrollableTracksViewportHeight();
/*      */     } else {
/*      */       
/*  811 */       scrollable = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  819 */     Rectangle rectangle4 = new Rectangle(0, rectangle1.y - insets2.top, 0, 0);
/*      */ 
/*      */     
/*  822 */     if (bool4) {
/*  823 */       bool5 = false;
/*      */     }
/*  825 */     else if (this.vsbPolicy == 22) {
/*  826 */       bool5 = true;
/*      */     }
/*  828 */     else if (this.vsbPolicy == 21) {
/*  829 */       bool5 = false;
/*      */     } else {
/*      */       
/*  832 */       bool5 = (!bool3 && dimension1.height > dimension2.height) ? true : false;
/*      */     } 
/*      */ 
/*      */     
/*  836 */     if (this.vsb != null && bool5) {
/*  837 */       adjustForVSB(true, rectangle1, rectangle4, insets2, bool1);
/*  838 */       dimension2 = this.viewport.toViewCoordinates(rectangle1.getSize());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  846 */     Rectangle rectangle5 = new Rectangle(rectangle1.x - insets2.left, 0, 0, 0);
/*      */     
/*  848 */     if (bool4) {
/*  849 */       bool6 = false;
/*      */     }
/*  851 */     else if (this.hsbPolicy == 32) {
/*  852 */       bool6 = true;
/*      */     }
/*  854 */     else if (this.hsbPolicy == 31) {
/*  855 */       bool6 = false;
/*      */     } else {
/*      */       
/*  858 */       bool6 = (!bool2 && dimension1.width > dimension2.width) ? true : false;
/*      */     } 
/*      */     
/*  861 */     if (this.hsb != null && bool6) {
/*  862 */       adjustForHSB(true, rectangle1, rectangle5, insets2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  870 */       if (this.vsb != null && !bool5 && this.vsbPolicy != 21) {
/*      */ 
/*      */         
/*  873 */         dimension2 = this.viewport.toViewCoordinates(rectangle1.getSize());
/*  874 */         bool5 = (dimension1.height > dimension2.height) ? true : false;
/*      */         
/*  876 */         if (bool5) {
/*  877 */           adjustForVSB(true, rectangle1, rectangle4, insets2, bool1);
/*      */         }
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
/*  889 */     if (this.viewport != null) {
/*  890 */       this.viewport.setBounds(rectangle1);
/*      */       
/*  892 */       if (scrollable != null) {
/*  893 */         dimension2 = this.viewport.toViewCoordinates(rectangle1.getSize());
/*      */         
/*  895 */         boolean bool7 = bool6;
/*  896 */         boolean bool8 = bool5;
/*      */         
/*  898 */         bool2 = scrollable.getScrollableTracksViewportWidth();
/*      */         
/*  900 */         bool3 = scrollable.getScrollableTracksViewportHeight();
/*  901 */         if (this.vsb != null && this.vsbPolicy == 20) {
/*  902 */           boolean bool = (!bool3 && dimension1.height > dimension2.height) ? true : false;
/*      */           
/*  904 */           if (bool != bool5) {
/*  905 */             bool5 = bool;
/*  906 */             adjustForVSB(bool5, rectangle1, rectangle4, insets2, bool1);
/*      */ 
/*      */             
/*  909 */             dimension2 = this.viewport.toViewCoordinates(rectangle1.getSize());
/*      */           } 
/*      */         } 
/*  912 */         if (this.hsb != null && this.hsbPolicy == 30) {
/*  913 */           boolean bool = (!bool2 && dimension1.width > dimension2.width) ? true : false;
/*      */           
/*  915 */           if (bool != bool6) {
/*  916 */             bool6 = bool;
/*  917 */             adjustForHSB(bool6, rectangle1, rectangle5, insets2);
/*  918 */             if (this.vsb != null && !bool5 && this.vsbPolicy != 21) {
/*      */ 
/*      */ 
/*      */               
/*  922 */               dimension2 = this.viewport.toViewCoordinates(rectangle1.getSize());
/*  923 */               bool5 = (dimension1.height > dimension2.height) ? true : false;
/*      */ 
/*      */               
/*  926 */               if (bool5) {
/*  927 */                 adjustForVSB(true, rectangle1, rectangle4, insets2, bool1);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  933 */         if (bool7 != bool6 || bool8 != bool5)
/*      */         {
/*  935 */           this.viewport.setBounds(rectangle1);
/*      */         }
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
/*  947 */     rectangle4.height = rectangle1.height + insets2.top + insets2.bottom;
/*  948 */     rectangle5.width = rectangle1.width + insets2.left + insets2.right;
/*  949 */     rectangle3.height = rectangle1.height + insets2.top + insets2.bottom;
/*  950 */     rectangle1.y -= insets2.top;
/*  951 */     rectangle2.width = rectangle1.width + insets2.left + insets2.right;
/*  952 */     rectangle1.x -= insets2.left;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  958 */     if (this.rowHead != null) {
/*  959 */       this.rowHead.setBounds(rectangle3);
/*      */     }
/*      */     
/*  962 */     if (this.colHead != null) {
/*  963 */       this.colHead.setBounds(rectangle2);
/*      */     }
/*      */     
/*  966 */     if (this.vsb != null) {
/*  967 */       if (bool5) {
/*  968 */         if (this.colHead != null && 
/*  969 */           UIManager.getBoolean("ScrollPane.fillUpperCorner"))
/*      */         {
/*  971 */           if ((bool1 && this.upperRight == null) || (!bool1 && this.upperLeft == null)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  979 */             rectangle4.y = rectangle2.y;
/*  980 */             rectangle4.height += rectangle2.height;
/*      */           } 
/*      */         }
/*  983 */         this.vsb.setVisible(true);
/*  984 */         this.vsb.setBounds(rectangle4);
/*      */       } else {
/*      */         
/*  987 */         this.vsb.setVisible(false);
/*      */       } 
/*      */     }
/*      */     
/*  991 */     if (this.hsb != null) {
/*  992 */       if (bool6) {
/*  993 */         if (this.rowHead != null && 
/*  994 */           UIManager.getBoolean("ScrollPane.fillLowerCorner"))
/*      */         {
/*  996 */           if ((bool1 && this.lowerLeft == null) || (!bool1 && this.lowerRight == null)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1004 */             if (bool1) {
/* 1005 */               rectangle5.x = rectangle3.x;
/*      */             }
/* 1007 */             rectangle5.width += rectangle3.width;
/*      */           } 
/*      */         }
/* 1010 */         this.hsb.setVisible(true);
/* 1011 */         this.hsb.setBounds(rectangle5);
/*      */       } else {
/*      */         
/* 1014 */         this.hsb.setVisible(false);
/*      */       } 
/*      */     }
/*      */     
/* 1018 */     if (this.lowerLeft != null) {
/* 1019 */       this.lowerLeft.setBounds(bool1 ? rectangle3.x : rectangle4.x, rectangle5.y, bool1 ? rectangle3.width : rectangle4.width, rectangle5.height);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1025 */     if (this.lowerRight != null) {
/* 1026 */       this.lowerRight.setBounds(bool1 ? rectangle4.x : rectangle3.x, rectangle5.y, bool1 ? rectangle4.width : rectangle3.width, rectangle5.height);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1032 */     if (this.upperLeft != null) {
/* 1033 */       this.upperLeft.setBounds(bool1 ? rectangle3.x : rectangle4.x, rectangle2.y, bool1 ? rectangle3.width : rectangle4.width, rectangle2.height);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1039 */     if (this.upperRight != null) {
/* 1040 */       this.upperRight.setBounds(bool1 ? rectangle4.x : rectangle3.x, rectangle2.y, bool1 ? rectangle4.width : rectangle3.width, rectangle2.height);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void adjustForVSB(boolean paramBoolean1, Rectangle paramRectangle1, Rectangle paramRectangle2, Insets paramInsets, boolean paramBoolean2) {
/* 1058 */     int i = paramRectangle2.width;
/* 1059 */     if (paramBoolean1) {
/* 1060 */       int j = Math.max(0, Math.min((this.vsb.getPreferredSize()).width, paramRectangle1.width));
/*      */ 
/*      */       
/* 1063 */       paramRectangle1.width -= j;
/* 1064 */       paramRectangle2.width = j;
/*      */       
/* 1066 */       if (paramBoolean2) {
/* 1067 */         paramRectangle2.x = paramRectangle1.x + paramRectangle1.width + paramInsets.right;
/*      */       } else {
/* 1069 */         paramRectangle1.x -= paramInsets.left;
/* 1070 */         paramRectangle1.x += j;
/*      */       } 
/*      */     } else {
/*      */       
/* 1074 */       paramRectangle1.width += i;
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
/*      */   private void adjustForHSB(boolean paramBoolean, Rectangle paramRectangle1, Rectangle paramRectangle2, Insets paramInsets) {
/* 1088 */     int i = paramRectangle2.height;
/* 1089 */     if (paramBoolean) {
/* 1090 */       int j = Math.max(0, Math.min(paramRectangle1.height, 
/* 1091 */             (this.hsb.getPreferredSize()).height));
/*      */       
/* 1093 */       paramRectangle1.height -= j;
/* 1094 */       paramRectangle2.y = paramRectangle1.y + paramRectangle1.height + paramInsets.bottom;
/* 1095 */       paramRectangle2.height = j;
/*      */     } else {
/*      */       
/* 1098 */       paramRectangle1.height += i;
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
/*      */   
/*      */   @Deprecated
/*      */   public Rectangle getViewportBorderBounds(JScrollPane paramJScrollPane) {
/* 1114 */     return paramJScrollPane.getViewportBorderBounds();
/*      */   }
/*      */   
/*      */   public static class UIResource extends ScrollPaneLayout implements UIResource {}
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/ScrollPaneLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */