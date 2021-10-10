/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.datatransfer.Transferable;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.KeyListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import javax.swing.CellRendererPane;
/*      */ import javax.swing.DefaultListCellRenderer;
/*      */ import javax.swing.DefaultListSelectionModel;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.ListCellRenderer;
/*      */ import javax.swing.ListModel;
/*      */ import javax.swing.ListSelectionModel;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.TransferHandler;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ListDataEvent;
/*      */ import javax.swing.event.ListDataListener;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import javax.swing.event.MouseInputListener;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.ListUI;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.text.Position;
/*      */ import sun.swing.DefaultLookup;
/*      */ import sun.swing.SwingUtilities2;
/*      */ import sun.swing.UIAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BasicListUI
/*      */   extends ListUI
/*      */ {
/*   59 */   private static final StringBuilder BASELINE_COMPONENT_KEY = new StringBuilder("List.baselineComponent");
/*      */ 
/*      */   
/*   62 */   protected JList list = null;
/*      */   
/*      */   protected CellRendererPane rendererPane;
/*      */   
/*      */   protected FocusListener focusListener;
/*      */   
/*      */   protected MouseInputListener mouseInputListener;
/*      */   protected ListSelectionListener listSelectionListener;
/*      */   protected ListDataListener listDataListener;
/*      */   protected PropertyChangeListener propertyChangeListener;
/*      */   private Handler handler;
/*   73 */   protected int[] cellHeights = null;
/*   74 */   protected int cellHeight = -1;
/*   75 */   protected int cellWidth = -1;
/*   76 */   protected int updateLayoutStateNeeded = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int listHeight;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int listWidth;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int layoutOrientation;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int columnCount;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int preferredHeight;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int rowsPerColumn;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  115 */   private long timeFactor = 1000L;
/*      */   
/*      */   private boolean isFileList = false;
/*      */   
/*      */   private boolean isLeftToRight = true;
/*      */   
/*      */   protected static final int modelChanged = 1;
/*      */   
/*      */   protected static final int selectionModelChanged = 2;
/*      */   
/*      */   protected static final int fontChanged = 4;
/*      */   
/*      */   protected static final int fixedCellWidthChanged = 8;
/*      */   
/*      */   protected static final int fixedCellHeightChanged = 16;
/*      */   
/*      */   protected static final int prototypeCellValueChanged = 32;
/*      */   
/*      */   protected static final int cellRendererChanged = 64;
/*      */   
/*      */   private static final int layoutOrientationChanged = 128;
/*      */   
/*      */   private static final int heightChanged = 256;
/*      */   
/*      */   private static final int widthChanged = 512;
/*      */   
/*      */   private static final int componentOrientationChanged = 1024;
/*      */   
/*      */   private static final int DROP_LINE_THICKNESS = 2;
/*      */   private static final int CHANGE_LEAD = 0;
/*      */   private static final int CHANGE_SELECTION = 1;
/*      */   private static final int EXTEND_SELECTION = 2;
/*      */   
/*      */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*  149 */     paramLazyActionMap.put(new Actions("selectPreviousColumn"));
/*  150 */     paramLazyActionMap.put(new Actions("selectPreviousColumnExtendSelection"));
/*  151 */     paramLazyActionMap.put(new Actions("selectPreviousColumnChangeLead"));
/*  152 */     paramLazyActionMap.put(new Actions("selectNextColumn"));
/*  153 */     paramLazyActionMap.put(new Actions("selectNextColumnExtendSelection"));
/*  154 */     paramLazyActionMap.put(new Actions("selectNextColumnChangeLead"));
/*  155 */     paramLazyActionMap.put(new Actions("selectPreviousRow"));
/*  156 */     paramLazyActionMap.put(new Actions("selectPreviousRowExtendSelection"));
/*  157 */     paramLazyActionMap.put(new Actions("selectPreviousRowChangeLead"));
/*  158 */     paramLazyActionMap.put(new Actions("selectNextRow"));
/*  159 */     paramLazyActionMap.put(new Actions("selectNextRowExtendSelection"));
/*  160 */     paramLazyActionMap.put(new Actions("selectNextRowChangeLead"));
/*  161 */     paramLazyActionMap.put(new Actions("selectFirstRow"));
/*  162 */     paramLazyActionMap.put(new Actions("selectFirstRowExtendSelection"));
/*  163 */     paramLazyActionMap.put(new Actions("selectFirstRowChangeLead"));
/*  164 */     paramLazyActionMap.put(new Actions("selectLastRow"));
/*  165 */     paramLazyActionMap.put(new Actions("selectLastRowExtendSelection"));
/*  166 */     paramLazyActionMap.put(new Actions("selectLastRowChangeLead"));
/*  167 */     paramLazyActionMap.put(new Actions("scrollUp"));
/*  168 */     paramLazyActionMap.put(new Actions("scrollUpExtendSelection"));
/*  169 */     paramLazyActionMap.put(new Actions("scrollUpChangeLead"));
/*  170 */     paramLazyActionMap.put(new Actions("scrollDown"));
/*  171 */     paramLazyActionMap.put(new Actions("scrollDownExtendSelection"));
/*  172 */     paramLazyActionMap.put(new Actions("scrollDownChangeLead"));
/*  173 */     paramLazyActionMap.put(new Actions("selectAll"));
/*  174 */     paramLazyActionMap.put(new Actions("clearSelection"));
/*  175 */     paramLazyActionMap.put(new Actions("addToSelection"));
/*  176 */     paramLazyActionMap.put(new Actions("toggleAndAnchor"));
/*  177 */     paramLazyActionMap.put(new Actions("extendTo"));
/*  178 */     paramLazyActionMap.put(new Actions("moveSelectionTo"));
/*      */     
/*  180 */     paramLazyActionMap.put(TransferHandler.getCutAction().getValue("Name"), 
/*  181 */         TransferHandler.getCutAction());
/*  182 */     paramLazyActionMap.put(TransferHandler.getCopyAction().getValue("Name"), 
/*  183 */         TransferHandler.getCopyAction());
/*  184 */     paramLazyActionMap.put(TransferHandler.getPasteAction().getValue("Name"), 
/*  185 */         TransferHandler.getPasteAction());
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
/*      */   protected void paintCell(Graphics paramGraphics, int paramInt1, Rectangle paramRectangle, ListCellRenderer paramListCellRenderer, ListModel<Object> paramListModel, ListSelectionModel paramListSelectionModel, int paramInt2) {
/*  204 */     Object object = paramListModel.getElementAt(paramInt1);
/*  205 */     boolean bool = (this.list.hasFocus() && paramInt1 == paramInt2) ? true : false;
/*  206 */     boolean bool1 = paramListSelectionModel.isSelectedIndex(paramInt1);
/*      */ 
/*      */     
/*  209 */     Component component = paramListCellRenderer.getListCellRendererComponent(this.list, object, paramInt1, bool1, bool);
/*      */     
/*  211 */     int i = paramRectangle.x;
/*  212 */     int j = paramRectangle.y;
/*  213 */     int k = paramRectangle.width;
/*  214 */     int m = paramRectangle.height;
/*      */     
/*  216 */     if (this.isFileList) {
/*      */ 
/*      */ 
/*      */       
/*  220 */       int n = Math.min(k, (component.getPreferredSize()).width + 4);
/*  221 */       if (!this.isLeftToRight) {
/*  222 */         i += k - n;
/*      */       }
/*  224 */       k = n;
/*      */     } 
/*      */     
/*  227 */     this.rendererPane.paintComponent(paramGraphics, component, this.list, i, j, k, m, true);
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
/*      */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/*  239 */     Shape shape = paramGraphics.getClip();
/*  240 */     paintImpl(paramGraphics, paramJComponent);
/*  241 */     paramGraphics.setClip(shape);
/*      */     
/*  243 */     paintDropLine(paramGraphics);
/*      */   }
/*      */   
/*      */   private void paintImpl(Graphics paramGraphics, JComponent paramJComponent) {
/*      */     int j, k;
/*  248 */     switch (this.layoutOrientation) {
/*      */       case 1:
/*  250 */         if (this.list.getHeight() != this.listHeight) {
/*  251 */           this.updateLayoutStateNeeded |= 0x100;
/*  252 */           redrawList();
/*      */         } 
/*      */         break;
/*      */       case 2:
/*  256 */         if (this.list.getWidth() != this.listWidth) {
/*  257 */           this.updateLayoutStateNeeded |= 0x200;
/*  258 */           redrawList();
/*      */         } 
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  264 */     maybeUpdateLayoutState();
/*      */     
/*  266 */     ListCellRenderer listCellRenderer = this.list.getCellRenderer();
/*  267 */     ListModel listModel = this.list.getModel();
/*  268 */     ListSelectionModel listSelectionModel = this.list.getSelectionModel();
/*      */     
/*      */     int i;
/*  271 */     if (listCellRenderer == null || (i = listModel.getSize()) == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  276 */     Rectangle rectangle = paramGraphics.getClipBounds();
/*      */ 
/*      */     
/*  279 */     if (paramJComponent.getComponentOrientation().isLeftToRight()) {
/*  280 */       j = convertLocationToColumn(rectangle.x, rectangle.y);
/*      */       
/*  282 */       k = convertLocationToColumn(rectangle.x + rectangle.width, rectangle.y);
/*      */     }
/*      */     else {
/*      */       
/*  286 */       j = convertLocationToColumn(rectangle.x + rectangle.width, rectangle.y);
/*      */ 
/*      */       
/*  289 */       k = convertLocationToColumn(rectangle.x, rectangle.y);
/*      */     } 
/*      */     
/*  292 */     int m = rectangle.y + rectangle.height;
/*  293 */     int n = adjustIndex(this.list.getLeadSelectionIndex(), this.list);
/*  294 */     byte b = (this.layoutOrientation == 2) ? this.columnCount : 1;
/*      */ 
/*      */ 
/*      */     
/*  298 */     for (int i1 = j; i1 <= k; 
/*  299 */       i1++) {
/*      */       
/*  301 */       int i2 = convertLocationToRowInColumn(rectangle.y, i1);
/*  302 */       int i3 = getRowCount(i1);
/*  303 */       int i4 = getModelIndex(i1, i2);
/*  304 */       Rectangle rectangle1 = getCellBounds(this.list, i4, i4);
/*      */       
/*  306 */       if (rectangle1 == null) {
/*      */         return;
/*      */       }
/*      */       
/*  310 */       while (i2 < i3 && rectangle1.y < m && i4 < i) {
/*      */         
/*  312 */         rectangle1.height = getHeight(i1, i2);
/*  313 */         paramGraphics.setClip(rectangle1.x, rectangle1.y, rectangle1.width, rectangle1.height);
/*      */         
/*  315 */         paramGraphics.clipRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */         
/*  317 */         paintCell(paramGraphics, i4, rectangle1, listCellRenderer, listModel, listSelectionModel, n);
/*      */         
/*  319 */         rectangle1.y += rectangle1.height;
/*  320 */         i4 += b;
/*  321 */         i2++;
/*      */       } 
/*      */     } 
/*      */     
/*  325 */     this.rendererPane.removeAll();
/*      */   }
/*      */   
/*      */   private void paintDropLine(Graphics paramGraphics) {
/*  329 */     JList.DropLocation dropLocation = this.list.getDropLocation();
/*  330 */     if (dropLocation == null || !dropLocation.isInsert()) {
/*      */       return;
/*      */     }
/*      */     
/*  334 */     Color color = DefaultLookup.getColor(this.list, this, "List.dropLineColor", null);
/*  335 */     if (color != null) {
/*  336 */       paramGraphics.setColor(color);
/*  337 */       Rectangle rectangle = getDropLineRect(dropLocation);
/*  338 */       paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */     } 
/*      */   }
/*      */   
/*      */   private Rectangle getDropLineRect(JList.DropLocation paramDropLocation) {
/*  343 */     int i = this.list.getModel().getSize();
/*      */     
/*  345 */     if (i == 0) {
/*  346 */       Insets insets = this.list.getInsets();
/*  347 */       if (this.layoutOrientation == 2) {
/*  348 */         if (this.isLeftToRight) {
/*  349 */           return new Rectangle(insets.left, insets.top, 2, 20);
/*      */         }
/*  351 */         return new Rectangle(this.list.getWidth() - 2 - insets.right, insets.top, 2, 20);
/*      */       } 
/*      */ 
/*      */       
/*  355 */       return new Rectangle(insets.left, insets.top, this.list
/*  356 */           .getWidth() - insets.left - insets.right, 2);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  361 */     Rectangle rectangle = null;
/*  362 */     int j = paramDropLocation.getIndex();
/*  363 */     boolean bool = false;
/*      */     
/*  365 */     if (this.layoutOrientation == 2) {
/*  366 */       if (j == i) {
/*  367 */         bool = true;
/*  368 */       } else if (j != 0 && convertModelToRow(j) != 
/*  369 */         convertModelToRow(j - 1)) {
/*      */         
/*  371 */         Rectangle rectangle1 = getCellBounds(this.list, j - 1);
/*  372 */         Rectangle rectangle2 = getCellBounds(this.list, j);
/*  373 */         Point point = paramDropLocation.getDropPoint();
/*      */         
/*  375 */         if (this.isLeftToRight) {
/*      */ 
/*      */ 
/*      */           
/*  379 */           bool = (Point2D.distance((rectangle1.x + rectangle1.width), (rectangle1.y + (int)(rectangle1.height / 2.0D)), point.x, point.y) < Point2D.distance(rectangle2.x, (rectangle2.y + (int)(rectangle2.height / 2.0D)), point.x, point.y)) ? true : false;
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  386 */           bool = (Point2D.distance(rectangle1.x, (rectangle1.y + (int)(rectangle1.height / 2.0D)), point.x, point.y) < Point2D.distance((rectangle2.x + rectangle2.width), (rectangle2.y + (int)(rectangle1.height / 2.0D)), point.x, point.y)) ? true : false;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  392 */       if (bool) {
/*  393 */         j--;
/*  394 */         rectangle = getCellBounds(this.list, j);
/*  395 */         if (this.isLeftToRight) {
/*  396 */           rectangle.x += rectangle.width;
/*      */         } else {
/*  398 */           rectangle.x -= 2;
/*      */         } 
/*      */       } else {
/*  401 */         rectangle = getCellBounds(this.list, j);
/*  402 */         if (!this.isLeftToRight) {
/*  403 */           rectangle.x += rectangle.width - 2;
/*      */         }
/*      */       } 
/*      */       
/*  407 */       if (rectangle.x >= this.list.getWidth()) {
/*  408 */         rectangle.x = this.list.getWidth() - 2;
/*  409 */       } else if (rectangle.x < 0) {
/*  410 */         rectangle.x = 0;
/*      */       } 
/*      */       
/*  413 */       rectangle.width = 2;
/*  414 */     } else if (this.layoutOrientation == 1) {
/*  415 */       if (j == i) {
/*  416 */         j--;
/*  417 */         rectangle = getCellBounds(this.list, j);
/*  418 */         rectangle.y += rectangle.height;
/*  419 */       } else if (j != 0 && convertModelToColumn(j) != 
/*  420 */         convertModelToColumn(j - 1)) {
/*      */         
/*  422 */         Rectangle rectangle1 = getCellBounds(this.list, j - 1);
/*  423 */         Rectangle rectangle2 = getCellBounds(this.list, j);
/*  424 */         Point point = paramDropLocation.getDropPoint();
/*  425 */         if (Point2D.distance((rectangle1.x + (int)(rectangle1.width / 2.0D)), (rectangle1.y + rectangle1.height), point.x, point.y) < 
/*      */ 
/*      */           
/*  428 */           Point2D.distance((rectangle2.x + (int)(rectangle2.width / 2.0D)), rectangle2.y, point.x, point.y)) {
/*      */ 
/*      */ 
/*      */           
/*  432 */           j--;
/*  433 */           rectangle = getCellBounds(this.list, j);
/*  434 */           rectangle.y += rectangle.height;
/*      */         } else {
/*  436 */           rectangle = getCellBounds(this.list, j);
/*      */         } 
/*      */       } else {
/*  439 */         rectangle = getCellBounds(this.list, j);
/*      */       } 
/*      */       
/*  442 */       if (rectangle.y >= this.list.getHeight()) {
/*  443 */         rectangle.y = this.list.getHeight() - 2;
/*      */       }
/*      */       
/*  446 */       rectangle.height = 2;
/*      */     } else {
/*  448 */       if (j == i) {
/*  449 */         j--;
/*  450 */         rectangle = getCellBounds(this.list, j);
/*  451 */         rectangle.y += rectangle.height;
/*      */       } else {
/*  453 */         rectangle = getCellBounds(this.list, j);
/*      */       } 
/*      */       
/*  456 */       if (rectangle.y >= this.list.getHeight()) {
/*  457 */         rectangle.y = this.list.getHeight() - 2;
/*      */       }
/*      */       
/*  460 */       rectangle.height = 2;
/*      */     } 
/*      */     
/*  463 */     return rectangle;
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
/*      */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/*  475 */     super.getBaseline(paramJComponent, paramInt1, paramInt2);
/*  476 */     int i = this.list.getFixedCellHeight();
/*  477 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/*  478 */     Component component = (Component)uIDefaults.get(BASELINE_COMPONENT_KEY);
/*      */     
/*  480 */     if (component == null) {
/*  481 */       ListCellRenderer<String> listCellRenderer = (ListCellRenderer)UIManager.get("List.cellRenderer");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  486 */       if (listCellRenderer == null) {
/*  487 */         listCellRenderer = new DefaultListCellRenderer();
/*      */       }
/*  489 */       component = listCellRenderer.getListCellRendererComponent(this.list, "a", -1, false, false);
/*      */       
/*  491 */       uIDefaults.put(BASELINE_COMPONENT_KEY, component);
/*      */     } 
/*  493 */     component.setFont(this.list.getFont());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  501 */     if (i == -1) {
/*  502 */       i = (component.getPreferredSize()).height;
/*      */     }
/*  504 */     return component.getBaseline(2147483647, i) + 
/*  505 */       (this.list.getInsets()).top;
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
/*      */   public Component.BaselineResizeBehavior getBaselineResizeBehavior(JComponent paramJComponent) {
/*  518 */     super.getBaselineResizeBehavior(paramJComponent);
/*  519 */     return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getPreferredSize(JComponent paramJComponent) {
/*      */     boolean bool;
/*  578 */     maybeUpdateLayoutState();
/*      */     
/*  580 */     int i = this.list.getModel().getSize() - 1;
/*  581 */     if (i < 0) {
/*  582 */       return new Dimension(0, 0);
/*      */     }
/*      */     
/*  585 */     Insets insets = this.list.getInsets();
/*  586 */     int j = this.cellWidth * this.columnCount + insets.left + insets.right;
/*      */ 
/*      */     
/*  589 */     if (this.layoutOrientation != 0) {
/*  590 */       bool = this.preferredHeight;
/*      */     } else {
/*      */       
/*  593 */       Rectangle rectangle = getCellBounds(this.list, i);
/*      */       
/*  595 */       if (rectangle != null) {
/*  596 */         bool = rectangle.y + rectangle.height + insets.bottom;
/*      */       } else {
/*      */         
/*  599 */         bool = false;
/*      */       } 
/*      */     } 
/*  602 */     return new Dimension(j, bool);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void selectPreviousIndex() {
/*  612 */     int i = this.list.getSelectedIndex();
/*  613 */     if (i > 0) {
/*  614 */       i--;
/*  615 */       this.list.setSelectedIndex(i);
/*  616 */       this.list.ensureIndexIsVisible(i);
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
/*      */   protected void selectNextIndex() {
/*  628 */     int i = this.list.getSelectedIndex();
/*  629 */     if (i + 1 < this.list.getModel().getSize()) {
/*  630 */       i++;
/*  631 */       this.list.setSelectedIndex(i);
/*  632 */       this.list.ensureIndexIsVisible(i);
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
/*      */   protected void installKeyboardActions() {
/*  645 */     InputMap inputMap = getInputMap(0);
/*      */     
/*  647 */     SwingUtilities.replaceUIInputMap(this.list, 0, inputMap);
/*      */ 
/*      */     
/*  650 */     LazyActionMap.installLazyActionMap(this.list, BasicListUI.class, "List.actionMap");
/*      */   }
/*      */ 
/*      */   
/*      */   InputMap getInputMap(int paramInt) {
/*  655 */     if (paramInt == 0) {
/*  656 */       InputMap inputMap1 = (InputMap)DefaultLookup.get(this.list, this, "List.focusInputMap");
/*      */       
/*      */       InputMap inputMap2;
/*      */       
/*  660 */       if (this.isLeftToRight || (
/*  661 */         inputMap2 = (InputMap)DefaultLookup.get(this.list, this, "List.focusInputMap.RightToLeft")) == null)
/*      */       {
/*  663 */         return inputMap1;
/*      */       }
/*  665 */       inputMap2.setParent(inputMap1);
/*  666 */       return inputMap2;
/*      */     } 
/*      */     
/*  669 */     return null;
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
/*      */   protected void uninstallKeyboardActions() {
/*  682 */     SwingUtilities.replaceUIActionMap(this.list, null);
/*  683 */     SwingUtilities.replaceUIInputMap(this.list, 0, null);
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
/*      */   protected void installListeners() {
/*  696 */     TransferHandler transferHandler = this.list.getTransferHandler();
/*  697 */     if (transferHandler == null || transferHandler instanceof UIResource) {
/*  698 */       this.list.setTransferHandler(defaultTransferHandler);
/*      */ 
/*      */       
/*  701 */       if (this.list.getDropTarget() instanceof UIResource) {
/*  702 */         this.list.setDropTarget(null);
/*      */       }
/*      */     } 
/*      */     
/*  706 */     this.focusListener = createFocusListener();
/*  707 */     this.mouseInputListener = createMouseInputListener();
/*  708 */     this.propertyChangeListener = createPropertyChangeListener();
/*  709 */     this.listSelectionListener = createListSelectionListener();
/*  710 */     this.listDataListener = createListDataListener();
/*      */     
/*  712 */     this.list.addFocusListener(this.focusListener);
/*  713 */     this.list.addMouseListener(this.mouseInputListener);
/*  714 */     this.list.addMouseMotionListener(this.mouseInputListener);
/*  715 */     this.list.addPropertyChangeListener(this.propertyChangeListener);
/*  716 */     this.list.addKeyListener(getHandler());
/*      */     
/*  718 */     ListModel listModel = this.list.getModel();
/*  719 */     if (listModel != null) {
/*  720 */       listModel.addListDataListener(this.listDataListener);
/*      */     }
/*      */     
/*  723 */     ListSelectionModel listSelectionModel = this.list.getSelectionModel();
/*  724 */     if (listSelectionModel != null) {
/*  725 */       listSelectionModel.addListSelectionListener(this.listSelectionListener);
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
/*      */   protected void uninstallListeners() {
/*  741 */     this.list.removeFocusListener(this.focusListener);
/*  742 */     this.list.removeMouseListener(this.mouseInputListener);
/*  743 */     this.list.removeMouseMotionListener(this.mouseInputListener);
/*  744 */     this.list.removePropertyChangeListener(this.propertyChangeListener);
/*  745 */     this.list.removeKeyListener(getHandler());
/*      */     
/*  747 */     ListModel listModel = this.list.getModel();
/*  748 */     if (listModel != null) {
/*  749 */       listModel.removeListDataListener(this.listDataListener);
/*      */     }
/*      */     
/*  752 */     ListSelectionModel listSelectionModel = this.list.getSelectionModel();
/*  753 */     if (listSelectionModel != null) {
/*  754 */       listSelectionModel.removeListSelectionListener(this.listSelectionListener);
/*      */     }
/*      */     
/*  757 */     this.focusListener = null;
/*  758 */     this.mouseInputListener = null;
/*  759 */     this.listSelectionListener = null;
/*  760 */     this.listDataListener = null;
/*  761 */     this.propertyChangeListener = null;
/*  762 */     this.handler = null;
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
/*      */   protected void installDefaults() {
/*  779 */     this.list.setLayout((LayoutManager)null);
/*      */     
/*  781 */     LookAndFeel.installBorder(this.list, "List.border");
/*      */     
/*  783 */     LookAndFeel.installColorsAndFont(this.list, "List.background", "List.foreground", "List.font");
/*      */     
/*  785 */     LookAndFeel.installProperty(this.list, "opaque", Boolean.TRUE);
/*      */     
/*  787 */     if (this.list.getCellRenderer() == null) {
/*  788 */       this.list.setCellRenderer((ListCellRenderer)UIManager.get("List.cellRenderer"));
/*      */     }
/*      */     
/*  791 */     Color color1 = this.list.getSelectionBackground();
/*  792 */     if (color1 == null || color1 instanceof UIResource) {
/*  793 */       this.list.setSelectionBackground(UIManager.getColor("List.selectionBackground"));
/*      */     }
/*      */     
/*  796 */     Color color2 = this.list.getSelectionForeground();
/*  797 */     if (color2 == null || color2 instanceof UIResource) {
/*  798 */       this.list.setSelectionForeground(UIManager.getColor("List.selectionForeground"));
/*      */     }
/*      */     
/*  801 */     Long long_ = (Long)UIManager.get("List.timeFactor");
/*  802 */     this.timeFactor = (long_ != null) ? long_.longValue() : 1000L;
/*      */     
/*  804 */     updateIsFileList();
/*      */   }
/*      */   
/*      */   private void updateIsFileList() {
/*  808 */     boolean bool = Boolean.TRUE.equals(this.list.getClientProperty("List.isFileList"));
/*  809 */     if (bool != this.isFileList) {
/*  810 */       this.isFileList = bool;
/*  811 */       Font font = this.list.getFont();
/*  812 */       if (font == null || font instanceof UIResource) {
/*  813 */         Font font1 = UIManager.getFont(bool ? "FileChooser.listFont" : "List.font");
/*  814 */         if (font1 != null && font1 != font) {
/*  815 */           this.list.setFont(font1);
/*      */         }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallDefaults() {
/*  833 */     LookAndFeel.uninstallBorder(this.list);
/*  834 */     if (this.list.getFont() instanceof UIResource) {
/*  835 */       this.list.setFont((Font)null);
/*      */     }
/*  837 */     if (this.list.getForeground() instanceof UIResource) {
/*  838 */       this.list.setForeground((Color)null);
/*      */     }
/*  840 */     if (this.list.getBackground() instanceof UIResource) {
/*  841 */       this.list.setBackground((Color)null);
/*      */     }
/*  843 */     if (this.list.getSelectionBackground() instanceof UIResource) {
/*  844 */       this.list.setSelectionBackground((Color)null);
/*      */     }
/*  846 */     if (this.list.getSelectionForeground() instanceof UIResource) {
/*  847 */       this.list.setSelectionForeground((Color)null);
/*      */     }
/*  849 */     if (this.list.getCellRenderer() instanceof UIResource) {
/*  850 */       this.list.setCellRenderer((ListCellRenderer)null);
/*      */     }
/*  852 */     if (this.list.getTransferHandler() instanceof UIResource) {
/*  853 */       this.list.setTransferHandler((TransferHandler)null);
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
/*      */   public void installUI(JComponent paramJComponent) {
/*  869 */     this.list = (JList)paramJComponent;
/*      */     
/*  871 */     this.layoutOrientation = this.list.getLayoutOrientation();
/*      */     
/*  873 */     this.rendererPane = new CellRendererPane();
/*  874 */     this.list.add(this.rendererPane);
/*      */     
/*  876 */     this.columnCount = 1;
/*      */     
/*  878 */     this.updateLayoutStateNeeded = 1;
/*  879 */     this.isLeftToRight = this.list.getComponentOrientation().isLeftToRight();
/*      */     
/*  881 */     installDefaults();
/*  882 */     installListeners();
/*  883 */     installKeyboardActions();
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
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  898 */     uninstallListeners();
/*  899 */     uninstallDefaults();
/*  900 */     uninstallKeyboardActions();
/*      */     
/*  902 */     this.cellWidth = this.cellHeight = -1;
/*  903 */     this.cellHeights = null;
/*      */     
/*  905 */     this.listWidth = this.listHeight = -1;
/*      */     
/*  907 */     this.list.remove(this.rendererPane);
/*  908 */     this.rendererPane = null;
/*  909 */     this.list = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  920 */     return new BasicListUI();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int locationToIndex(JList paramJList, Point paramPoint) {
/*  929 */     maybeUpdateLayoutState();
/*  930 */     return convertLocationToModel(paramPoint.x, paramPoint.y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point indexToLocation(JList paramJList, int paramInt) {
/*  938 */     maybeUpdateLayoutState();
/*  939 */     Rectangle rectangle = getCellBounds(paramJList, paramInt, paramInt);
/*      */     
/*  941 */     if (rectangle != null) {
/*  942 */       return new Point(rectangle.x, rectangle.y);
/*      */     }
/*  944 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getCellBounds(JList paramJList, int paramInt1, int paramInt2) {
/*  952 */     maybeUpdateLayoutState();
/*      */     
/*  954 */     int i = Math.min(paramInt1, paramInt2);
/*  955 */     int j = Math.max(paramInt1, paramInt2);
/*      */     
/*  957 */     if (i >= paramJList.getModel().getSize()) {
/*  958 */       return null;
/*      */     }
/*      */     
/*  961 */     Rectangle rectangle1 = getCellBounds(paramJList, i);
/*      */     
/*  963 */     if (rectangle1 == null) {
/*  964 */       return null;
/*      */     }
/*  966 */     if (i == j) {
/*  967 */       return rectangle1;
/*      */     }
/*  969 */     Rectangle rectangle2 = getCellBounds(paramJList, j);
/*      */     
/*  971 */     if (rectangle2 != null) {
/*  972 */       if (this.layoutOrientation == 2) {
/*  973 */         int k = convertModelToRow(i);
/*  974 */         int m = convertModelToRow(j);
/*      */         
/*  976 */         if (k != m) {
/*  977 */           rectangle1.x = 0;
/*  978 */           rectangle1.width = paramJList.getWidth();
/*      */         }
/*      */       
/*  981 */       } else if (rectangle1.x != rectangle2.x) {
/*      */         
/*  983 */         rectangle1.y = 0;
/*  984 */         rectangle1.height = paramJList.getHeight();
/*      */       } 
/*  986 */       rectangle1.add(rectangle2);
/*      */     } 
/*  988 */     return rectangle1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Rectangle getCellBounds(JList paramJList, int paramInt) {
/*  996 */     maybeUpdateLayoutState();
/*      */     
/*  998 */     int i = convertModelToRow(paramInt);
/*  999 */     int j = convertModelToColumn(paramInt);
/*      */     
/* 1001 */     if (i == -1 || j == -1) {
/* 1002 */       return null;
/*      */     }
/*      */     
/* 1005 */     Insets insets = paramJList.getInsets();
/*      */     
/* 1007 */     int m = this.cellWidth;
/* 1008 */     int n = insets.top;
/*      */     
/* 1010 */     switch (this.layoutOrientation)
/*      */     { case 1:
/*      */       case 2:
/* 1013 */         if (this.isLeftToRight) {
/* 1014 */           k = insets.left + j * this.cellWidth;
/*      */         } else {
/* 1016 */           k = paramJList.getWidth() - insets.right - (j + 1) * this.cellWidth;
/*      */         } 
/* 1018 */         n += this.cellHeight * i;
/* 1019 */         i1 = this.cellHeight;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1038 */         return new Rectangle(k, n, m, i1); }  int k = insets.left; if (this.cellHeights == null) { n += this.cellHeight * i; } else if (i >= this.cellHeights.length) { n = 0; } else { for (byte b = 0; b < i; b++) n += this.cellHeights[b];  }  m = paramJList.getWidth() - insets.left + insets.right; int i1 = getRowHeight(paramInt); return new Rectangle(k, n, m, i1);
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
/*      */   protected int getRowHeight(int paramInt) {
/* 1051 */     return getHeight(0, paramInt);
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
/*      */   protected int convertYToRow(int paramInt) {
/* 1066 */     return convertLocationToRow(0, paramInt, false);
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
/*      */   protected int convertRowToY(int paramInt) {
/* 1080 */     if (paramInt >= getRowCount(0) || paramInt < 0) {
/* 1081 */       return -1;
/*      */     }
/* 1083 */     Rectangle rectangle = getCellBounds(this.list, paramInt, paramInt);
/* 1084 */     return rectangle.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getHeight(int paramInt1, int paramInt2) {
/* 1091 */     if (paramInt1 < 0 || paramInt1 > this.columnCount || paramInt2 < 0) {
/* 1092 */       return -1;
/*      */     }
/* 1094 */     if (this.layoutOrientation != 0) {
/* 1095 */       return this.cellHeight;
/*      */     }
/* 1097 */     if (paramInt2 >= this.list.getModel().getSize()) {
/* 1098 */       return -1;
/*      */     }
/* 1100 */     return (this.cellHeights == null) ? this.cellHeight : ((paramInt2 < this.cellHeights.length) ? this.cellHeights[paramInt2] : -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int convertLocationToRow(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 1111 */     int i = this.list.getModel().getSize();
/*      */     
/* 1113 */     if (i <= 0) {
/* 1114 */       return -1;
/*      */     }
/* 1116 */     Insets insets = this.list.getInsets();
/* 1117 */     if (this.cellHeights == null) {
/* 1118 */       int k = (this.cellHeight == 0) ? 0 : ((paramInt2 - insets.top) / this.cellHeight);
/*      */       
/* 1120 */       if (paramBoolean) {
/* 1121 */         if (k) {
/* 1122 */           k = 0;
/*      */         }
/* 1124 */         else if (k >= i) {
/* 1125 */           k = i - 1;
/*      */         } 
/*      */       }
/* 1128 */       return k;
/*      */     } 
/* 1130 */     if (i > this.cellHeights.length) {
/* 1131 */       return -1;
/*      */     }
/*      */     
/* 1134 */     int j = insets.top;
/* 1135 */     byte b1 = 0;
/*      */     
/* 1137 */     if (paramBoolean && paramInt2 < j) {
/* 1138 */       return 0;
/*      */     }
/*      */     byte b2;
/* 1141 */     for (b2 = 0; b2 < i; b2++) {
/* 1142 */       if (paramInt2 >= j && paramInt2 < j + this.cellHeights[b2]) {
/* 1143 */         return b1;
/*      */       }
/* 1145 */       j += this.cellHeights[b2];
/* 1146 */       b1++;
/*      */     } 
/* 1148 */     return b2 - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int convertLocationToRowInColumn(int paramInt1, int paramInt2) {
/* 1157 */     int i = 0;
/*      */     
/* 1159 */     if (this.layoutOrientation != 0) {
/* 1160 */       if (this.isLeftToRight) {
/* 1161 */         i = paramInt2 * this.cellWidth;
/*      */       } else {
/* 1163 */         i = this.list.getWidth() - (paramInt2 + 1) * this.cellWidth - (this.list.getInsets()).right;
/*      */       } 
/*      */     }
/* 1166 */     return convertLocationToRow(i, paramInt1, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int convertLocationToModel(int paramInt1, int paramInt2) {
/* 1174 */     int i = convertLocationToRow(paramInt1, paramInt2, true);
/* 1175 */     int j = convertLocationToColumn(paramInt1, paramInt2);
/*      */     
/* 1177 */     if (i >= 0 && j >= 0) {
/* 1178 */       return getModelIndex(j, i);
/*      */     }
/* 1180 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getRowCount(int paramInt) {
/* 1187 */     if (paramInt < 0 || paramInt >= this.columnCount) {
/* 1188 */       return -1;
/*      */     }
/* 1190 */     if (this.layoutOrientation == 0 || (paramInt == 0 && this.columnCount == 1))
/*      */     {
/* 1192 */       return this.list.getModel().getSize();
/*      */     }
/* 1194 */     if (paramInt >= this.columnCount) {
/* 1195 */       return -1;
/*      */     }
/* 1197 */     if (this.layoutOrientation == 1) {
/* 1198 */       if (paramInt < this.columnCount - 1) {
/* 1199 */         return this.rowsPerColumn;
/*      */       }
/* 1201 */       return this.list.getModel().getSize() - (this.columnCount - 1) * this.rowsPerColumn;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1206 */     int i = this.columnCount - this.columnCount * this.rowsPerColumn - this.list.getModel().getSize();
/*      */     
/* 1208 */     if (paramInt >= i) {
/* 1209 */       return Math.max(0, this.rowsPerColumn - 1);
/*      */     }
/* 1211 */     return this.rowsPerColumn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getModelIndex(int paramInt1, int paramInt2) {
/* 1220 */     switch (this.layoutOrientation) {
/*      */       case 1:
/* 1222 */         return Math.min(this.list.getModel().getSize() - 1, this.rowsPerColumn * paramInt1 + 
/* 1223 */             Math.min(paramInt2, this.rowsPerColumn - 1));
/*      */       case 2:
/* 1225 */         return Math.min(this.list.getModel().getSize() - 1, paramInt2 * this.columnCount + paramInt1);
/*      */     } 
/*      */     
/* 1228 */     return paramInt2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int convertLocationToColumn(int paramInt1, int paramInt2) {
/* 1236 */     if (this.cellWidth > 0) {
/* 1237 */       int i; if (this.layoutOrientation == 0) {
/* 1238 */         return 0;
/*      */       }
/* 1240 */       Insets insets = this.list.getInsets();
/*      */       
/* 1242 */       if (this.isLeftToRight) {
/* 1243 */         i = (paramInt1 - insets.left) / this.cellWidth;
/*      */       } else {
/* 1245 */         i = (this.list.getWidth() - paramInt1 - insets.right - 1) / this.cellWidth;
/*      */       } 
/* 1247 */       if (i < 0) {
/* 1248 */         return 0;
/*      */       }
/* 1250 */       if (i >= this.columnCount) {
/* 1251 */         return this.columnCount - 1;
/*      */       }
/* 1253 */       return i;
/*      */     } 
/* 1255 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int convertModelToRow(int paramInt) {
/* 1263 */     int i = this.list.getModel().getSize();
/*      */     
/* 1265 */     if (paramInt < 0 || paramInt >= i) {
/* 1266 */       return -1;
/*      */     }
/*      */     
/* 1269 */     if (this.layoutOrientation != 0 && this.columnCount > 1 && this.rowsPerColumn > 0) {
/*      */       
/* 1271 */       if (this.layoutOrientation == 1) {
/* 1272 */         return paramInt % this.rowsPerColumn;
/*      */       }
/* 1274 */       return paramInt / this.columnCount;
/*      */     } 
/* 1276 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int convertModelToColumn(int paramInt) {
/* 1284 */     int i = this.list.getModel().getSize();
/*      */     
/* 1286 */     if (paramInt < 0 || paramInt >= i) {
/* 1287 */       return -1;
/*      */     }
/*      */     
/* 1290 */     if (this.layoutOrientation != 0 && this.rowsPerColumn > 0 && this.columnCount > 1) {
/*      */       
/* 1292 */       if (this.layoutOrientation == 1) {
/* 1293 */         return paramInt / this.rowsPerColumn;
/*      */       }
/* 1295 */       return paramInt % this.columnCount;
/*      */     } 
/* 1297 */     return 0;
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
/*      */   protected void maybeUpdateLayoutState() {
/* 1310 */     if (this.updateLayoutStateNeeded != 0) {
/* 1311 */       updateLayoutState();
/* 1312 */       this.updateLayoutStateNeeded = 0;
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
/*      */   
/*      */   protected void updateLayoutState() {
/* 1331 */     int i = this.list.getFixedCellHeight();
/* 1332 */     int j = this.list.getFixedCellWidth();
/*      */     
/* 1334 */     this.cellWidth = (j != -1) ? j : -1;
/*      */     
/* 1336 */     if (i != -1) {
/* 1337 */       this.cellHeight = i;
/* 1338 */       this.cellHeights = null;
/*      */     } else {
/*      */       
/* 1341 */       this.cellHeight = -1;
/* 1342 */       this.cellHeights = new int[this.list.getModel().getSize()];
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1352 */     if (j == -1 || i == -1) {
/*      */       
/* 1354 */       ListModel<Object> listModel = this.list.getModel();
/* 1355 */       int k = listModel.getSize();
/* 1356 */       ListCellRenderer listCellRenderer = this.list.getCellRenderer();
/*      */       
/* 1358 */       if (listCellRenderer != null) {
/* 1359 */         for (byte b = 0; b < k; b++) {
/* 1360 */           Object object = listModel.getElementAt(b);
/* 1361 */           Component component = listCellRenderer.getListCellRendererComponent(this.list, object, b, false, false);
/* 1362 */           this.rendererPane.add(component);
/* 1363 */           Dimension dimension = component.getPreferredSize();
/* 1364 */           if (j == -1) {
/* 1365 */             this.cellWidth = Math.max(dimension.width, this.cellWidth);
/*      */           }
/* 1367 */           if (i == -1) {
/* 1368 */             this.cellHeights[b] = dimension.height;
/*      */           }
/*      */         } 
/*      */       } else {
/*      */         
/* 1373 */         if (this.cellWidth == -1) {
/* 1374 */           this.cellWidth = 0;
/*      */         }
/* 1376 */         if (this.cellHeights == null) {
/* 1377 */           this.cellHeights = new int[k];
/*      */         }
/* 1379 */         for (byte b = 0; b < k; b++) {
/* 1380 */           this.cellHeights[b] = 0;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1385 */     this.columnCount = 1;
/* 1386 */     if (this.layoutOrientation != 0) {
/* 1387 */       updateHorizontalLayoutState(j, i);
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
/*      */   private void updateHorizontalLayoutState(int paramInt1, int paramInt2) {
/* 1401 */     int k, i = this.list.getVisibleRowCount();
/* 1402 */     int j = this.list.getModel().getSize();
/* 1403 */     Insets insets = this.list.getInsets();
/*      */     
/* 1405 */     this.listHeight = this.list.getHeight();
/* 1406 */     this.listWidth = this.list.getWidth();
/*      */     
/* 1408 */     if (j == 0) {
/* 1409 */       this.rowsPerColumn = this.columnCount = 0;
/* 1410 */       this.preferredHeight = insets.top + insets.bottom;
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1416 */     if (paramInt2 != -1) {
/* 1417 */       k = paramInt2;
/*      */     }
/*      */     else {
/*      */       
/* 1421 */       int m = 0;
/* 1422 */       if (this.cellHeights.length > 0) {
/* 1423 */         m = this.cellHeights[this.cellHeights.length - 1];
/* 1424 */         int n = this.cellHeights.length - 2;
/* 1425 */         for (; n >= 0; n--) {
/* 1426 */           m = Math.max(m, this.cellHeights[n]);
/*      */         }
/*      */       } 
/* 1429 */       k = this.cellHeight = m;
/* 1430 */       this.cellHeights = null;
/*      */     } 
/*      */ 
/*      */     
/* 1434 */     this.rowsPerColumn = j;
/* 1435 */     if (i > 0) {
/* 1436 */       this.rowsPerColumn = i;
/* 1437 */       this.columnCount = Math.max(1, j / this.rowsPerColumn);
/* 1438 */       if (j > 0 && j > this.rowsPerColumn && j % this.rowsPerColumn != 0)
/*      */       {
/* 1440 */         this.columnCount++;
/*      */       }
/* 1442 */       if (this.layoutOrientation == 2)
/*      */       {
/*      */         
/* 1445 */         this.rowsPerColumn = j / this.columnCount;
/* 1446 */         if (j % this.columnCount > 0) {
/* 1447 */           this.rowsPerColumn++;
/*      */         }
/*      */       }
/*      */     
/* 1451 */     } else if (this.layoutOrientation == 1 && k != 0) {
/* 1452 */       this.rowsPerColumn = Math.max(1, (this.listHeight - insets.top - insets.bottom) / k);
/*      */       
/* 1454 */       this.columnCount = Math.max(1, j / this.rowsPerColumn);
/* 1455 */       if (j > 0 && j > this.rowsPerColumn && j % this.rowsPerColumn != 0)
/*      */       {
/* 1457 */         this.columnCount++;
/*      */       }
/*      */     }
/* 1460 */     else if (this.layoutOrientation == 2 && this.cellWidth > 0 && this.listWidth > 0) {
/*      */       
/* 1462 */       this.columnCount = Math.max(1, (this.listWidth - insets.left - insets.right) / this.cellWidth);
/*      */       
/* 1464 */       this.rowsPerColumn = j / this.columnCount;
/* 1465 */       if (j % this.columnCount > 0) {
/* 1466 */         this.rowsPerColumn++;
/*      */       }
/*      */     } 
/* 1469 */     this.preferredHeight = this.rowsPerColumn * this.cellHeight + insets.top + insets.bottom;
/*      */   }
/*      */ 
/*      */   
/*      */   private Handler getHandler() {
/* 1474 */     if (this.handler == null) {
/* 1475 */       this.handler = new Handler();
/*      */     }
/* 1477 */     return this.handler;
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
/*      */   public class MouseInputHandler
/*      */     implements MouseInputListener
/*      */   {
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {
/* 1502 */       BasicListUI.this.getHandler().mouseClicked(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {
/* 1506 */       BasicListUI.this.getHandler().mouseEntered(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {
/* 1510 */       BasicListUI.this.getHandler().mouseExited(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 1514 */       BasicListUI.this.getHandler().mousePressed(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 1518 */       BasicListUI.this.getHandler().mouseDragged(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {
/* 1522 */       BasicListUI.this.getHandler().mouseMoved(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 1526 */       BasicListUI.this.getHandler().mouseReleased(param1MouseEvent);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MouseInputListener createMouseInputListener() {
/* 1554 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class FocusHandler
/*      */     implements FocusListener
/*      */   {
/*      */     protected void repaintCellFocus() {
/* 1565 */       BasicListUI.this.getHandler().repaintCellFocus();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/* 1573 */       BasicListUI.this.getHandler().focusGained(param1FocusEvent);
/*      */     }
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/* 1577 */       BasicListUI.this.getHandler().focusLost(param1FocusEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   protected FocusListener createFocusListener() {
/* 1582 */     return getHandler();
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
/*      */   public class ListSelectionHandler
/*      */     implements ListSelectionListener
/*      */   {
/*      */     public void valueChanged(ListSelectionEvent param1ListSelectionEvent) {
/* 1607 */       BasicListUI.this.getHandler().valueChanged(param1ListSelectionEvent);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ListSelectionListener createListSelectionListener() {
/* 1634 */     return getHandler();
/*      */   }
/*      */ 
/*      */   
/*      */   private void redrawList() {
/* 1639 */     this.list.revalidate();
/* 1640 */     this.list.repaint();
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
/*      */   public class ListDataHandler
/*      */     implements ListDataListener
/*      */   {
/*      */     public void intervalAdded(ListDataEvent param1ListDataEvent) {
/* 1665 */       BasicListUI.this.getHandler().intervalAdded(param1ListDataEvent);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void intervalRemoved(ListDataEvent param1ListDataEvent) {
/* 1671 */       BasicListUI.this.getHandler().intervalRemoved(param1ListDataEvent);
/*      */     }
/*      */ 
/*      */     
/*      */     public void contentsChanged(ListDataEvent param1ListDataEvent) {
/* 1676 */       BasicListUI.this.getHandler().contentsChanged(param1ListDataEvent);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ListDataListener createListDataListener() {
/* 1704 */     return getHandler();
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
/*      */   public class PropertyChangeHandler
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1732 */       BasicListUI.this.getHandler().propertyChange(param1PropertyChangeEvent);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertyChangeListener createPropertyChangeListener() {
/* 1761 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Actions
/*      */     extends UIAction
/*      */   {
/*      */     private static final String SELECT_PREVIOUS_COLUMN = "selectPreviousColumn";
/*      */ 
/*      */     
/*      */     private static final String SELECT_PREVIOUS_COLUMN_EXTEND = "selectPreviousColumnExtendSelection";
/*      */ 
/*      */     
/*      */     private static final String SELECT_PREVIOUS_COLUMN_CHANGE_LEAD = "selectPreviousColumnChangeLead";
/*      */     
/*      */     private static final String SELECT_NEXT_COLUMN = "selectNextColumn";
/*      */     
/*      */     private static final String SELECT_NEXT_COLUMN_EXTEND = "selectNextColumnExtendSelection";
/*      */     
/*      */     private static final String SELECT_NEXT_COLUMN_CHANGE_LEAD = "selectNextColumnChangeLead";
/*      */     
/*      */     private static final String SELECT_PREVIOUS_ROW = "selectPreviousRow";
/*      */     
/*      */     private static final String SELECT_PREVIOUS_ROW_EXTEND = "selectPreviousRowExtendSelection";
/*      */     
/*      */     private static final String SELECT_PREVIOUS_ROW_CHANGE_LEAD = "selectPreviousRowChangeLead";
/*      */     
/*      */     private static final String SELECT_NEXT_ROW = "selectNextRow";
/*      */     
/*      */     private static final String SELECT_NEXT_ROW_EXTEND = "selectNextRowExtendSelection";
/*      */     
/*      */     private static final String SELECT_NEXT_ROW_CHANGE_LEAD = "selectNextRowChangeLead";
/*      */     
/*      */     private static final String SELECT_FIRST_ROW = "selectFirstRow";
/*      */     
/*      */     private static final String SELECT_FIRST_ROW_EXTEND = "selectFirstRowExtendSelection";
/*      */     
/*      */     private static final String SELECT_FIRST_ROW_CHANGE_LEAD = "selectFirstRowChangeLead";
/*      */     
/*      */     private static final String SELECT_LAST_ROW = "selectLastRow";
/*      */     
/*      */     private static final String SELECT_LAST_ROW_EXTEND = "selectLastRowExtendSelection";
/*      */     
/*      */     private static final String SELECT_LAST_ROW_CHANGE_LEAD = "selectLastRowChangeLead";
/*      */     
/*      */     private static final String SCROLL_UP = "scrollUp";
/*      */     
/*      */     private static final String SCROLL_UP_EXTEND = "scrollUpExtendSelection";
/*      */     
/*      */     private static final String SCROLL_UP_CHANGE_LEAD = "scrollUpChangeLead";
/*      */     
/*      */     private static final String SCROLL_DOWN = "scrollDown";
/*      */     
/*      */     private static final String SCROLL_DOWN_EXTEND = "scrollDownExtendSelection";
/*      */     
/*      */     private static final String SCROLL_DOWN_CHANGE_LEAD = "scrollDownChangeLead";
/*      */     
/*      */     private static final String SELECT_ALL = "selectAll";
/*      */     
/*      */     private static final String CLEAR_SELECTION = "clearSelection";
/*      */     
/*      */     private static final String ADD_TO_SELECTION = "addToSelection";
/*      */     
/*      */     private static final String TOGGLE_AND_ANCHOR = "toggleAndAnchor";
/*      */     
/*      */     private static final String EXTEND_TO = "extendTo";
/*      */     
/*      */     private static final String MOVE_SELECTION_TO = "moveSelectionTo";
/*      */ 
/*      */     
/*      */     Actions(String param1String) {
/* 1833 */       super(param1String);
/*      */     }
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1836 */       String str = getName();
/* 1837 */       JList jList = (JList)param1ActionEvent.getSource();
/* 1838 */       BasicListUI basicListUI = (BasicListUI)BasicLookAndFeel.getUIOfType(jList
/* 1839 */           .getUI(), BasicListUI.class);
/*      */       
/* 1841 */       if (str == "selectPreviousColumn") {
/* 1842 */         changeSelection(jList, 1, 
/* 1843 */             getNextColumnIndex(jList, basicListUI, -1), -1);
/*      */       }
/* 1845 */       else if (str == "selectPreviousColumnExtendSelection") {
/* 1846 */         changeSelection(jList, 2, 
/* 1847 */             getNextColumnIndex(jList, basicListUI, -1), -1);
/*      */       }
/* 1849 */       else if (str == "selectPreviousColumnChangeLead") {
/* 1850 */         changeSelection(jList, 0, 
/* 1851 */             getNextColumnIndex(jList, basicListUI, -1), -1);
/*      */       }
/* 1853 */       else if (str == "selectNextColumn") {
/* 1854 */         changeSelection(jList, 1, 
/* 1855 */             getNextColumnIndex(jList, basicListUI, 1), 1);
/*      */       }
/* 1857 */       else if (str == "selectNextColumnExtendSelection") {
/* 1858 */         changeSelection(jList, 2, 
/* 1859 */             getNextColumnIndex(jList, basicListUI, 1), 1);
/*      */       }
/* 1861 */       else if (str == "selectNextColumnChangeLead") {
/* 1862 */         changeSelection(jList, 0, 
/* 1863 */             getNextColumnIndex(jList, basicListUI, 1), 1);
/*      */       }
/* 1865 */       else if (str == "selectPreviousRow") {
/* 1866 */         changeSelection(jList, 1, 
/* 1867 */             getNextIndex(jList, basicListUI, -1), -1);
/*      */       }
/* 1869 */       else if (str == "selectPreviousRowExtendSelection") {
/* 1870 */         changeSelection(jList, 2, 
/* 1871 */             getNextIndex(jList, basicListUI, -1), -1);
/*      */       }
/* 1873 */       else if (str == "selectPreviousRowChangeLead") {
/* 1874 */         changeSelection(jList, 0, 
/* 1875 */             getNextIndex(jList, basicListUI, -1), -1);
/*      */       }
/* 1877 */       else if (str == "selectNextRow") {
/* 1878 */         changeSelection(jList, 1, 
/* 1879 */             getNextIndex(jList, basicListUI, 1), 1);
/*      */       }
/* 1881 */       else if (str == "selectNextRowExtendSelection") {
/* 1882 */         changeSelection(jList, 2, 
/* 1883 */             getNextIndex(jList, basicListUI, 1), 1);
/*      */       }
/* 1885 */       else if (str == "selectNextRowChangeLead") {
/* 1886 */         changeSelection(jList, 0, 
/* 1887 */             getNextIndex(jList, basicListUI, 1), 1);
/*      */       }
/* 1889 */       else if (str == "selectFirstRow") {
/* 1890 */         changeSelection(jList, 1, 0, -1);
/*      */       }
/* 1892 */       else if (str == "selectFirstRowExtendSelection") {
/* 1893 */         changeSelection(jList, 2, 0, -1);
/*      */       }
/* 1895 */       else if (str == "selectFirstRowChangeLead") {
/* 1896 */         changeSelection(jList, 0, 0, -1);
/*      */       }
/* 1898 */       else if (str == "selectLastRow") {
/* 1899 */         changeSelection(jList, 1, jList
/* 1900 */             .getModel().getSize() - 1, 1);
/*      */       }
/* 1902 */       else if (str == "selectLastRowExtendSelection") {
/* 1903 */         changeSelection(jList, 2, jList
/* 1904 */             .getModel().getSize() - 1, 1);
/*      */       }
/* 1906 */       else if (str == "selectLastRowChangeLead") {
/* 1907 */         changeSelection(jList, 0, jList
/* 1908 */             .getModel().getSize() - 1, 1);
/*      */       }
/* 1910 */       else if (str == "scrollUp") {
/* 1911 */         changeSelection(jList, 1, 
/* 1912 */             getNextPageIndex(jList, -1), -1);
/*      */       }
/* 1914 */       else if (str == "scrollUpExtendSelection") {
/* 1915 */         changeSelection(jList, 2, 
/* 1916 */             getNextPageIndex(jList, -1), -1);
/*      */       }
/* 1918 */       else if (str == "scrollUpChangeLead") {
/* 1919 */         changeSelection(jList, 0, 
/* 1920 */             getNextPageIndex(jList, -1), -1);
/*      */       }
/* 1922 */       else if (str == "scrollDown") {
/* 1923 */         changeSelection(jList, 1, 
/* 1924 */             getNextPageIndex(jList, 1), 1);
/*      */       }
/* 1926 */       else if (str == "scrollDownExtendSelection") {
/* 1927 */         changeSelection(jList, 2, 
/* 1928 */             getNextPageIndex(jList, 1), 1);
/*      */       }
/* 1930 */       else if (str == "scrollDownChangeLead") {
/* 1931 */         changeSelection(jList, 0, 
/* 1932 */             getNextPageIndex(jList, 1), 1);
/*      */       }
/* 1934 */       else if (str == "selectAll") {
/* 1935 */         selectAll(jList);
/*      */       }
/* 1937 */       else if (str == "clearSelection") {
/* 1938 */         clearSelection(jList);
/*      */       }
/* 1940 */       else if (str == "addToSelection") {
/* 1941 */         int i = BasicListUI.adjustIndex(jList
/* 1942 */             .getSelectionModel().getLeadSelectionIndex(), jList);
/*      */         
/* 1944 */         if (!jList.isSelectedIndex(i)) {
/* 1945 */           int j = jList.getSelectionModel().getAnchorSelectionIndex();
/* 1946 */           jList.setValueIsAdjusting(true);
/* 1947 */           jList.addSelectionInterval(i, i);
/* 1948 */           jList.getSelectionModel().setAnchorSelectionIndex(j);
/* 1949 */           jList.setValueIsAdjusting(false);
/*      */         }
/*      */       
/* 1952 */       } else if (str == "toggleAndAnchor") {
/* 1953 */         int i = BasicListUI.adjustIndex(jList
/* 1954 */             .getSelectionModel().getLeadSelectionIndex(), jList);
/*      */         
/* 1956 */         if (jList.isSelectedIndex(i)) {
/* 1957 */           jList.removeSelectionInterval(i, i);
/*      */         } else {
/* 1959 */           jList.addSelectionInterval(i, i);
/*      */         }
/*      */       
/* 1962 */       } else if (str == "extendTo") {
/* 1963 */         changeSelection(jList, 2, BasicListUI
/*      */             
/* 1965 */             .adjustIndex(jList.getSelectionModel().getLeadSelectionIndex(), jList), 0);
/*      */       
/*      */       }
/* 1968 */       else if (str == "moveSelectionTo") {
/* 1969 */         changeSelection(jList, 1, BasicListUI
/*      */             
/* 1971 */             .adjustIndex(jList.getSelectionModel().getLeadSelectionIndex(), jList), 0);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEnabled(Object param1Object) {
/* 1977 */       String str = getName();
/* 1978 */       if (str == "selectPreviousColumnChangeLead" || str == "selectNextColumnChangeLead" || str == "selectPreviousRowChangeLead" || str == "selectNextRowChangeLead" || str == "selectFirstRowChangeLead" || str == "selectLastRowChangeLead" || str == "scrollUpChangeLead" || str == "scrollDownChangeLead")
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1989 */         return (param1Object != null && ((JList)param1Object).getSelectionModel() instanceof DefaultListSelectionModel);
/*      */       }
/*      */ 
/*      */       
/* 1993 */       return true;
/*      */     }
/*      */     
/*      */     private void clearSelection(JList param1JList) {
/* 1997 */       param1JList.clearSelection();
/*      */     }
/*      */     
/*      */     private void selectAll(JList param1JList) {
/* 2001 */       int i = param1JList.getModel().getSize();
/* 2002 */       if (i > 0) {
/* 2003 */         ListSelectionModel listSelectionModel = param1JList.getSelectionModel();
/* 2004 */         int j = BasicListUI.adjustIndex(listSelectionModel.getLeadSelectionIndex(), param1JList);
/*      */         
/* 2006 */         if (listSelectionModel.getSelectionMode() == 0) {
/* 2007 */           if (j == -1) {
/* 2008 */             int k = BasicListUI.adjustIndex(param1JList.getMinSelectionIndex(), param1JList);
/* 2009 */             j = (k == -1) ? 0 : k;
/*      */           } 
/*      */           
/* 2012 */           param1JList.setSelectionInterval(j, j);
/* 2013 */           param1JList.ensureIndexIsVisible(j);
/*      */         } else {
/* 2015 */           param1JList.setValueIsAdjusting(true);
/*      */           
/* 2017 */           int k = BasicListUI.adjustIndex(listSelectionModel.getAnchorSelectionIndex(), param1JList);
/*      */           
/* 2019 */           param1JList.setSelectionInterval(0, i - 1);
/*      */ 
/*      */           
/* 2022 */           SwingUtilities2.setLeadAnchorWithoutSelection(listSelectionModel, k, j);
/*      */           
/* 2024 */           param1JList.setValueIsAdjusting(false);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private int getNextPageIndex(JList param1JList, int param1Int) {
/* 2030 */       if (param1JList.getModel().getSize() == 0) {
/* 2031 */         return -1;
/*      */       }
/*      */       
/* 2034 */       int i = -1;
/* 2035 */       Rectangle rectangle1 = param1JList.getVisibleRect();
/* 2036 */       ListSelectionModel listSelectionModel = param1JList.getSelectionModel();
/* 2037 */       int j = BasicListUI.adjustIndex(listSelectionModel.getLeadSelectionIndex(), param1JList);
/*      */       
/* 2039 */       Rectangle rectangle2 = (j == -1) ? new Rectangle() : param1JList.getCellBounds(j, j);
/*      */       
/* 2041 */       if (param1JList.getLayoutOrientation() == 1 && param1JList
/* 2042 */         .getVisibleRowCount() <= 0) {
/* 2043 */         if (!param1JList.getComponentOrientation().isLeftToRight()) {
/* 2044 */           param1Int = -param1Int;
/*      */         }
/*      */ 
/*      */         
/* 2048 */         if (param1Int < 0) {
/*      */           
/* 2050 */           rectangle1.x = rectangle2.x + rectangle2.width - rectangle1.width;
/* 2051 */           Point point = new Point(rectangle1.x - 1, rectangle2.y);
/* 2052 */           i = param1JList.locationToIndex(point);
/* 2053 */           Rectangle rectangle = param1JList.getCellBounds(i, i);
/* 2054 */           if (rectangle1.intersects(rectangle)) {
/* 2055 */             point.x = rectangle.x - 1;
/* 2056 */             i = param1JList.locationToIndex(point);
/* 2057 */             rectangle = param1JList.getCellBounds(i, i);
/*      */           } 
/*      */           
/* 2060 */           if (rectangle.y != rectangle2.y) {
/* 2061 */             point.x = rectangle.x + rectangle.width;
/* 2062 */             i = param1JList.locationToIndex(point);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 2067 */           rectangle1.x = rectangle2.x;
/* 2068 */           Point point = new Point(rectangle1.x + rectangle1.width, rectangle2.y);
/* 2069 */           i = param1JList.locationToIndex(point);
/* 2070 */           Rectangle rectangle = param1JList.getCellBounds(i, i);
/* 2071 */           if (rectangle1.intersects(rectangle)) {
/* 2072 */             point.x = rectangle.x + rectangle.width;
/* 2073 */             i = param1JList.locationToIndex(point);
/* 2074 */             rectangle = param1JList.getCellBounds(i, i);
/*      */           } 
/* 2076 */           if (rectangle.y != rectangle2.y) {
/* 2077 */             point.x = rectangle.x - 1;
/* 2078 */             i = param1JList.locationToIndex(point);
/*      */           }
/*      */         
/*      */         }
/*      */       
/* 2083 */       } else if (param1Int < 0) {
/*      */ 
/*      */         
/* 2086 */         Point point = new Point(rectangle2.x, rectangle1.y);
/* 2087 */         i = param1JList.locationToIndex(point);
/* 2088 */         if (j <= i)
/*      */         {
/*      */           
/* 2091 */           rectangle1.y = rectangle2.y + rectangle2.height - rectangle1.height;
/* 2092 */           point.y = rectangle1.y;
/* 2093 */           i = param1JList.locationToIndex(point);
/* 2094 */           Rectangle rectangle = param1JList.getCellBounds(i, i);
/*      */ 
/*      */           
/* 2097 */           if (rectangle.y < rectangle1.y) {
/* 2098 */             point.y = rectangle.y + rectangle.height;
/* 2099 */             i = param1JList.locationToIndex(point);
/* 2100 */             rectangle = param1JList.getCellBounds(i, i);
/*      */           } 
/*      */ 
/*      */           
/* 2104 */           if (rectangle.y >= rectangle2.y) {
/* 2105 */             point.y = rectangle2.y - 1;
/* 2106 */             i = param1JList.locationToIndex(point);
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 2113 */         Point point = new Point(rectangle2.x, rectangle1.y + rectangle1.height - 1);
/*      */         
/* 2115 */         i = param1JList.locationToIndex(point);
/* 2116 */         Rectangle rectangle = param1JList.getCellBounds(i, i);
/*      */ 
/*      */         
/* 2119 */         if (rectangle.y + rectangle.height > rectangle1.y + rectangle1.height) {
/*      */           
/* 2121 */           point.y = rectangle.y - 1;
/* 2122 */           i = param1JList.locationToIndex(point);
/* 2123 */           rectangle = param1JList.getCellBounds(i, i);
/* 2124 */           i = Math.max(i, j);
/*      */         } 
/*      */         
/* 2127 */         if (j >= i) {
/*      */ 
/*      */           
/* 2130 */           rectangle1.y = rectangle2.y;
/* 2131 */           point.y = rectangle1.y + rectangle1.height - 1;
/* 2132 */           i = param1JList.locationToIndex(point);
/* 2133 */           rectangle = param1JList.getCellBounds(i, i);
/*      */ 
/*      */           
/* 2136 */           if (rectangle.y + rectangle.height > rectangle1.y + rectangle1.height) {
/*      */             
/* 2138 */             point.y = rectangle.y - 1;
/* 2139 */             i = param1JList.locationToIndex(point);
/* 2140 */             rectangle = param1JList.getCellBounds(i, i);
/*      */           } 
/*      */ 
/*      */           
/* 2144 */           if (rectangle.y <= rectangle2.y) {
/* 2145 */             point.y = rectangle2.y + rectangle2.height;
/* 2146 */             i = param1JList.locationToIndex(point);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2151 */       return i;
/*      */     }
/*      */ 
/*      */     
/*      */     private void changeSelection(JList param1JList, int param1Int1, int param1Int2, int param1Int3) {
/* 2156 */       if (param1Int2 >= 0 && param1Int2 < param1JList.getModel().getSize()) {
/* 2157 */         ListSelectionModel listSelectionModel = param1JList.getSelectionModel();
/*      */ 
/*      */         
/* 2160 */         if (param1Int1 == 0 && param1JList
/* 2161 */           .getSelectionMode() != 2)
/*      */         {
/*      */           
/* 2164 */           param1Int1 = 1;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2171 */         adjustScrollPositionIfNecessary(param1JList, param1Int2, param1Int3);
/*      */         
/* 2173 */         if (param1Int1 == 2) {
/* 2174 */           int i = BasicListUI.adjustIndex(listSelectionModel.getAnchorSelectionIndex(), param1JList);
/* 2175 */           if (i == -1) {
/* 2176 */             i = 0;
/*      */           }
/*      */           
/* 2179 */           param1JList.setSelectionInterval(i, param1Int2);
/*      */         }
/* 2181 */         else if (param1Int1 == 1) {
/* 2182 */           param1JList.setSelectedIndex(param1Int2);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 2187 */           ((DefaultListSelectionModel)listSelectionModel).moveLeadSelectionIndex(param1Int2);
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
/*      */     private void adjustScrollPositionIfNecessary(JList param1JList, int param1Int1, int param1Int2) {
/* 2199 */       if (param1Int2 == 0) {
/*      */         return;
/*      */       }
/* 2202 */       Rectangle rectangle1 = param1JList.getCellBounds(param1Int1, param1Int1);
/* 2203 */       Rectangle rectangle2 = param1JList.getVisibleRect();
/* 2204 */       if (rectangle1 != null && !rectangle2.contains(rectangle1)) {
/* 2205 */         if (param1JList.getLayoutOrientation() == 1 && param1JList
/* 2206 */           .getVisibleRowCount() <= 0) {
/*      */           
/* 2208 */           if (param1JList.getComponentOrientation().isLeftToRight()) {
/* 2209 */             if (param1Int2 > 0) {
/*      */               
/* 2211 */               int i = Math.max(0, rectangle1.x + rectangle1.width - rectangle2.width);
/*      */ 
/*      */               
/* 2214 */               int j = param1JList.locationToIndex(new Point(i, rectangle1.y));
/* 2215 */               Rectangle rectangle = param1JList.getCellBounds(j, j);
/*      */               
/* 2217 */               if (rectangle.x < i && rectangle.x < rectangle1.x) {
/* 2218 */                 rectangle.x += rectangle.width;
/*      */                 
/* 2220 */                 j = param1JList.locationToIndex(rectangle.getLocation());
/* 2221 */                 rectangle = param1JList.getCellBounds(j, j);
/*      */               } 
/*      */               
/* 2224 */               rectangle1 = rectangle;
/*      */             } 
/* 2226 */             rectangle1.width = rectangle2.width;
/*      */           
/*      */           }
/* 2229 */           else if (param1Int2 > 0) {
/*      */             
/* 2231 */             int i = rectangle1.x + rectangle2.width;
/*      */             
/* 2233 */             int j = param1JList.locationToIndex(new Point(i, rectangle1.y));
/* 2234 */             Rectangle rectangle = param1JList.getCellBounds(j, j);
/*      */             
/* 2236 */             if (rectangle.x + rectangle.width > i && rectangle.x > rectangle1.x)
/*      */             {
/* 2238 */               rectangle.width = 0;
/*      */             }
/* 2240 */             rectangle1.x = Math.max(0, rectangle.x + rectangle.width - rectangle2.width);
/*      */             
/* 2242 */             rectangle1.width = rectangle2.width;
/*      */           } else {
/*      */             
/* 2245 */             rectangle1.x += Math.max(0, rectangle1.width - rectangle2.width);
/*      */ 
/*      */             
/* 2248 */             rectangle1.width = Math.min(rectangle1.width, rectangle2.width);
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/* 2255 */         else if (param1Int2 > 0 && (rectangle1.y < rectangle2.y || rectangle1.y + rectangle1.height > rectangle2.y + rectangle2.height)) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2260 */           int i = Math.max(0, rectangle1.y + rectangle1.height - rectangle2.height);
/*      */ 
/*      */           
/* 2263 */           int j = param1JList.locationToIndex(new Point(rectangle1.x, i));
/* 2264 */           Rectangle rectangle = param1JList.getCellBounds(j, j);
/*      */           
/* 2266 */           if (rectangle.y < i && rectangle.y < rectangle1.y) {
/* 2267 */             rectangle.y += rectangle.height;
/*      */             
/* 2269 */             j = param1JList.locationToIndex(rectangle.getLocation());
/*      */             
/* 2271 */             rectangle = param1JList.getCellBounds(j, j);
/*      */           } 
/* 2273 */           rectangle1 = rectangle;
/* 2274 */           rectangle1.height = rectangle2.height;
/*      */         }
/*      */         else {
/*      */           
/* 2278 */           rectangle1.height = Math.min(rectangle1.height, rectangle2.height);
/*      */         } 
/*      */         
/* 2281 */         param1JList.scrollRectToVisible(rectangle1);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private int getNextColumnIndex(JList param1JList, BasicListUI param1BasicListUI, int param1Int) {
/* 2287 */       if (param1JList.getLayoutOrientation() != 0) {
/* 2288 */         int i = BasicListUI.adjustIndex(param1JList.getLeadSelectionIndex(), param1JList);
/* 2289 */         int j = param1JList.getModel().getSize();
/*      */         
/* 2291 */         if (i == -1)
/* 2292 */           return 0; 
/* 2293 */         if (j == 1)
/*      */         {
/* 2295 */           return 0; } 
/* 2296 */         if (param1BasicListUI == null || param1BasicListUI.columnCount <= 1) {
/* 2297 */           return -1;
/*      */         }
/*      */         
/* 2300 */         int k = param1BasicListUI.convertModelToColumn(i);
/* 2301 */         int m = param1BasicListUI.convertModelToRow(i);
/*      */         
/* 2303 */         k += param1Int;
/* 2304 */         if (k >= param1BasicListUI.columnCount || k < 0)
/*      */         {
/* 2306 */           return -1;
/*      */         }
/* 2308 */         int n = param1BasicListUI.getRowCount(k);
/* 2309 */         if (m >= n) {
/* 2310 */           return -1;
/*      */         }
/* 2312 */         return param1BasicListUI.getModelIndex(k, m);
/*      */       } 
/*      */       
/* 2315 */       return -1;
/*      */     }
/*      */     
/*      */     private int getNextIndex(JList param1JList, BasicListUI param1BasicListUI, int param1Int) {
/* 2319 */       int i = BasicListUI.adjustIndex(param1JList.getLeadSelectionIndex(), param1JList);
/* 2320 */       int j = param1JList.getModel().getSize();
/*      */       
/* 2322 */       if (i == -1) {
/* 2323 */         if (j > 0) {
/* 2324 */           if (param1Int > 0) {
/* 2325 */             i = 0;
/*      */           } else {
/*      */             
/* 2328 */             i = j - 1;
/*      */           } 
/*      */         }
/* 2331 */       } else if (j == 1) {
/*      */         
/* 2333 */         i = 0;
/* 2334 */       } else if (param1JList.getLayoutOrientation() == 2) {
/* 2335 */         if (param1BasicListUI != null) {
/* 2336 */           i += param1BasicListUI.columnCount * param1Int;
/*      */         }
/*      */       } else {
/* 2339 */         i += param1Int;
/*      */       } 
/*      */       
/* 2342 */       return i;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class Handler
/*      */     implements FocusListener, KeyListener, ListDataListener, ListSelectionListener, MouseInputListener, PropertyChangeListener, DragRecognitionSupport.BeforeDrag
/*      */   {
/* 2354 */     private String prefix = "";
/* 2355 */     private String typedString = "";
/* 2356 */     private long lastTime = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean dragPressDidSelection;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void keyTyped(KeyEvent param1KeyEvent) {
/* 2370 */       JList jList = (JList)param1KeyEvent.getSource();
/* 2371 */       ListModel listModel = jList.getModel();
/*      */       
/* 2373 */       if (listModel.getSize() == 0 || param1KeyEvent.isAltDown() || 
/* 2374 */         BasicGraphicsUtils.isMenuShortcutKeyDown(param1KeyEvent) || 
/* 2375 */         isNavigationKey(param1KeyEvent)) {
/*      */         return;
/*      */       }
/*      */       
/* 2379 */       boolean bool = true;
/*      */       
/* 2381 */       char c = param1KeyEvent.getKeyChar();
/*      */       
/* 2383 */       long l = param1KeyEvent.getWhen();
/* 2384 */       int i = BasicListUI.adjustIndex(jList.getLeadSelectionIndex(), BasicListUI.this.list);
/* 2385 */       if (l - this.lastTime < BasicListUI.this.timeFactor) {
/* 2386 */         this.typedString += c;
/* 2387 */         if (this.prefix.length() == 1 && c == this.prefix.charAt(0)) {
/*      */ 
/*      */           
/* 2390 */           i++;
/*      */         } else {
/* 2392 */           this.prefix = this.typedString;
/*      */         } 
/*      */       } else {
/* 2395 */         i++;
/* 2396 */         this.typedString = "" + c;
/* 2397 */         this.prefix = this.typedString;
/*      */       } 
/* 2399 */       this.lastTime = l;
/*      */       
/* 2401 */       if (i < 0 || i >= listModel.getSize()) {
/* 2402 */         bool = false;
/* 2403 */         i = 0;
/*      */       } 
/* 2405 */       int j = jList.getNextMatch(this.prefix, i, Position.Bias.Forward);
/*      */       
/* 2407 */       if (j >= 0) {
/* 2408 */         jList.setSelectedIndex(j);
/* 2409 */         jList.ensureIndexIsVisible(j);
/* 2410 */       } else if (bool) {
/* 2411 */         j = jList.getNextMatch(this.prefix, 0, Position.Bias.Forward);
/*      */         
/* 2413 */         if (j >= 0) {
/* 2414 */           jList.setSelectedIndex(j);
/* 2415 */           jList.ensureIndexIsVisible(j);
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
/*      */     public void keyPressed(KeyEvent param1KeyEvent) {
/* 2427 */       if (isNavigationKey(param1KeyEvent)) {
/* 2428 */         this.prefix = "";
/* 2429 */         this.typedString = "";
/* 2430 */         this.lastTime = 0L;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void keyReleased(KeyEvent param1KeyEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isNavigationKey(KeyEvent param1KeyEvent) {
/* 2448 */       InputMap inputMap = BasicListUI.this.list.getInputMap(1);
/* 2449 */       KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(param1KeyEvent);
/*      */       
/* 2451 */       if (inputMap != null && inputMap.get(keyStroke) != null) {
/* 2452 */         return true;
/*      */       }
/* 2454 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 2461 */       String str = param1PropertyChangeEvent.getPropertyName();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2466 */       if (str == "model") {
/* 2467 */         ListModel listModel1 = (ListModel)param1PropertyChangeEvent.getOldValue();
/* 2468 */         ListModel listModel2 = (ListModel)param1PropertyChangeEvent.getNewValue();
/* 2469 */         if (listModel1 != null) {
/* 2470 */           listModel1.removeListDataListener(BasicListUI.this.listDataListener);
/*      */         }
/* 2472 */         if (listModel2 != null) {
/* 2473 */           listModel2.addListDataListener(BasicListUI.this.listDataListener);
/*      */         }
/* 2475 */         BasicListUI.this.updateLayoutStateNeeded |= 0x1;
/* 2476 */         BasicListUI.this.redrawList();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 2482 */       else if (str == "selectionModel") {
/* 2483 */         ListSelectionModel listSelectionModel1 = (ListSelectionModel)param1PropertyChangeEvent.getOldValue();
/* 2484 */         ListSelectionModel listSelectionModel2 = (ListSelectionModel)param1PropertyChangeEvent.getNewValue();
/* 2485 */         if (listSelectionModel1 != null) {
/* 2486 */           listSelectionModel1.removeListSelectionListener(BasicListUI.this.listSelectionListener);
/*      */         }
/* 2488 */         if (listSelectionModel2 != null) {
/* 2489 */           listSelectionModel2.addListSelectionListener(BasicListUI.this.listSelectionListener);
/*      */         }
/* 2491 */         BasicListUI.this.updateLayoutStateNeeded |= 0x1;
/* 2492 */         BasicListUI.this.redrawList();
/*      */       }
/* 2494 */       else if (str == "cellRenderer") {
/* 2495 */         BasicListUI.this.updateLayoutStateNeeded |= 0x40;
/* 2496 */         BasicListUI.this.redrawList();
/*      */       }
/* 2498 */       else if (str == "font") {
/* 2499 */         BasicListUI.this.updateLayoutStateNeeded |= 0x4;
/* 2500 */         BasicListUI.this.redrawList();
/*      */       }
/* 2502 */       else if (str == "prototypeCellValue") {
/* 2503 */         BasicListUI.this.updateLayoutStateNeeded |= 0x20;
/* 2504 */         BasicListUI.this.redrawList();
/*      */       }
/* 2506 */       else if (str == "fixedCellHeight") {
/* 2507 */         BasicListUI.this.updateLayoutStateNeeded |= 0x10;
/* 2508 */         BasicListUI.this.redrawList();
/*      */       }
/* 2510 */       else if (str == "fixedCellWidth") {
/* 2511 */         BasicListUI.this.updateLayoutStateNeeded |= 0x8;
/* 2512 */         BasicListUI.this.redrawList();
/*      */       }
/* 2514 */       else if (str == "selectionForeground") {
/* 2515 */         BasicListUI.this.list.repaint();
/*      */       }
/* 2517 */       else if (str == "selectionBackground") {
/* 2518 */         BasicListUI.this.list.repaint();
/*      */       }
/* 2520 */       else if ("layoutOrientation" == str) {
/* 2521 */         BasicListUI.this.updateLayoutStateNeeded |= 0x80;
/* 2522 */         BasicListUI.this.layoutOrientation = BasicListUI.this.list.getLayoutOrientation();
/* 2523 */         BasicListUI.this.redrawList();
/*      */       }
/* 2525 */       else if ("visibleRowCount" == str) {
/* 2526 */         if (BasicListUI.this.layoutOrientation != 0) {
/* 2527 */           BasicListUI.this.updateLayoutStateNeeded |= 0x80;
/* 2528 */           BasicListUI.this.redrawList();
/*      */         }
/*      */       
/* 2531 */       } else if ("componentOrientation" == str) {
/* 2532 */         BasicListUI.this.isLeftToRight = BasicListUI.this.list.getComponentOrientation().isLeftToRight();
/* 2533 */         BasicListUI.this.updateLayoutStateNeeded |= 0x400;
/* 2534 */         BasicListUI.this.redrawList();
/*      */         
/* 2536 */         InputMap inputMap = BasicListUI.this.getInputMap(0);
/* 2537 */         SwingUtilities.replaceUIInputMap(BasicListUI.this.list, 0, inputMap);
/*      */       }
/* 2539 */       else if ("List.isFileList" == str) {
/* 2540 */         BasicListUI.this.updateIsFileList();
/* 2541 */         BasicListUI.this.redrawList();
/* 2542 */       } else if ("dropLocation" == str) {
/* 2543 */         JList.DropLocation dropLocation = (JList.DropLocation)param1PropertyChangeEvent.getOldValue();
/* 2544 */         repaintDropLocation(dropLocation);
/* 2545 */         repaintDropLocation(BasicListUI.this.list.getDropLocation());
/*      */       } 
/*      */     }
/*      */     private void repaintDropLocation(JList.DropLocation param1DropLocation) {
/*      */       Rectangle rectangle;
/* 2550 */       if (param1DropLocation == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2556 */       if (param1DropLocation.isInsert()) {
/* 2557 */         rectangle = BasicListUI.this.getDropLineRect(param1DropLocation);
/*      */       } else {
/* 2559 */         rectangle = BasicListUI.this.getCellBounds(BasicListUI.this.list, param1DropLocation.getIndex());
/*      */       } 
/*      */       
/* 2562 */       if (rectangle != null) {
/* 2563 */         BasicListUI.this.list.repaint(rectangle);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void intervalAdded(ListDataEvent param1ListDataEvent) {
/* 2571 */       BasicListUI.this.updateLayoutStateNeeded = 1;
/*      */       
/* 2573 */       int i = Math.min(param1ListDataEvent.getIndex0(), param1ListDataEvent.getIndex1());
/* 2574 */       int j = Math.max(param1ListDataEvent.getIndex0(), param1ListDataEvent.getIndex1());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2579 */       ListSelectionModel listSelectionModel = BasicListUI.this.list.getSelectionModel();
/* 2580 */       if (listSelectionModel != null) {
/* 2581 */         listSelectionModel.insertIndexInterval(i, j - i + 1, true);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2588 */       BasicListUI.this.redrawList();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void intervalRemoved(ListDataEvent param1ListDataEvent) {
/* 2594 */       BasicListUI.this.updateLayoutStateNeeded = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2599 */       ListSelectionModel listSelectionModel = BasicListUI.this.list.getSelectionModel();
/* 2600 */       if (listSelectionModel != null) {
/* 2601 */         listSelectionModel.removeIndexInterval(param1ListDataEvent.getIndex0(), param1ListDataEvent.getIndex1());
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2609 */       BasicListUI.this.redrawList();
/*      */     }
/*      */ 
/*      */     
/*      */     public void contentsChanged(ListDataEvent param1ListDataEvent) {
/* 2614 */       BasicListUI.this.updateLayoutStateNeeded = 1;
/* 2615 */       BasicListUI.this.redrawList();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void valueChanged(ListSelectionEvent param1ListSelectionEvent) {
/* 2623 */       BasicListUI.this.maybeUpdateLayoutState();
/*      */       
/* 2625 */       int i = BasicListUI.this.list.getModel().getSize();
/* 2626 */       int j = Math.min(i - 1, Math.max(param1ListSelectionEvent.getFirstIndex(), 0));
/* 2627 */       int k = Math.min(i - 1, Math.max(param1ListSelectionEvent.getLastIndex(), 0));
/*      */       
/* 2629 */       Rectangle rectangle = BasicListUI.this.getCellBounds(BasicListUI.this.list, j, k);
/*      */       
/* 2631 */       if (rectangle != null) {
/* 2632 */         BasicListUI.this.list.repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 2654 */       if (SwingUtilities2.shouldIgnore(param1MouseEvent, BasicListUI.this.list)) {
/*      */         return;
/*      */       }
/*      */       
/* 2658 */       boolean bool = BasicListUI.this.list.getDragEnabled();
/* 2659 */       boolean bool1 = true;
/*      */ 
/*      */       
/* 2662 */       if (bool) {
/* 2663 */         int i = SwingUtilities2.loc2IndexFileList(BasicListUI.this.list, param1MouseEvent.getPoint());
/*      */         
/* 2665 */         if (i != -1 && DragRecognitionSupport.mousePressed(param1MouseEvent)) {
/* 2666 */           this.dragPressDidSelection = false;
/*      */           
/* 2668 */           if (BasicGraphicsUtils.isMenuShortcutKeyDown(param1MouseEvent)) {
/*      */             return;
/*      */           }
/*      */           
/* 2672 */           if (!param1MouseEvent.isShiftDown() && BasicListUI.this.list.isSelectedIndex(i)) {
/*      */ 
/*      */             
/* 2675 */             BasicListUI.this.list.addSelectionInterval(i, i);
/*      */             
/*      */             return;
/*      */           } 
/*      */           
/* 2680 */           bool1 = false;
/*      */           
/* 2682 */           this.dragPressDidSelection = true;
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 2688 */         BasicListUI.this.list.setValueIsAdjusting(true);
/*      */       } 
/*      */       
/* 2691 */       if (bool1) {
/* 2692 */         SwingUtilities2.adjustFocus(BasicListUI.this.list);
/*      */       }
/*      */       
/* 2695 */       adjustSelection(param1MouseEvent);
/*      */     }
/*      */     
/*      */     private void adjustSelection(MouseEvent param1MouseEvent) {
/* 2699 */       int i = SwingUtilities2.loc2IndexFileList(BasicListUI.this.list, param1MouseEvent.getPoint());
/* 2700 */       if (i < 0) {
/*      */ 
/*      */         
/* 2703 */         if (BasicListUI.this.isFileList && param1MouseEvent
/* 2704 */           .getID() == 501 && (
/* 2705 */           !param1MouseEvent.isShiftDown() || BasicListUI.this.list
/* 2706 */           .getSelectionMode() == 0)) {
/* 2707 */           BasicListUI.this.list.clearSelection();
/*      */         }
/*      */       } else {
/*      */         boolean bool;
/* 2711 */         int j = BasicListUI.adjustIndex(BasicListUI.this.list.getAnchorSelectionIndex(), BasicListUI.this.list);
/*      */         
/* 2713 */         if (j == -1) {
/* 2714 */           j = 0;
/* 2715 */           bool = false;
/*      */         } else {
/* 2717 */           bool = BasicListUI.this.list.isSelectedIndex(j);
/*      */         } 
/*      */         
/* 2720 */         if (BasicGraphicsUtils.isMenuShortcutKeyDown(param1MouseEvent)) {
/* 2721 */           if (param1MouseEvent.isShiftDown()) {
/* 2722 */             if (bool) {
/* 2723 */               BasicListUI.this.list.addSelectionInterval(j, i);
/*      */             } else {
/* 2725 */               BasicListUI.this.list.removeSelectionInterval(j, i);
/* 2726 */               if (BasicListUI.this.isFileList) {
/* 2727 */                 BasicListUI.this.list.addSelectionInterval(i, i);
/* 2728 */                 BasicListUI.this.list.getSelectionModel().setAnchorSelectionIndex(j);
/*      */               } 
/*      */             } 
/* 2731 */           } else if (BasicListUI.this.list.isSelectedIndex(i)) {
/* 2732 */             BasicListUI.this.list.removeSelectionInterval(i, i);
/*      */           } else {
/* 2734 */             BasicListUI.this.list.addSelectionInterval(i, i);
/*      */           } 
/* 2736 */         } else if (param1MouseEvent.isShiftDown()) {
/* 2737 */           BasicListUI.this.list.setSelectionInterval(j, i);
/*      */         } else {
/* 2739 */           BasicListUI.this.list.setSelectionInterval(i, i);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void dragStarting(MouseEvent param1MouseEvent) {
/* 2745 */       if (BasicGraphicsUtils.isMenuShortcutKeyDown(param1MouseEvent)) {
/* 2746 */         int i = SwingUtilities2.loc2IndexFileList(BasicListUI.this.list, param1MouseEvent.getPoint());
/* 2747 */         BasicListUI.this.list.addSelectionInterval(i, i);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 2752 */       if (SwingUtilities2.shouldIgnore(param1MouseEvent, BasicListUI.this.list)) {
/*      */         return;
/*      */       }
/*      */       
/* 2756 */       if (BasicListUI.this.list.getDragEnabled()) {
/* 2757 */         DragRecognitionSupport.mouseDragged(param1MouseEvent, this);
/*      */         
/*      */         return;
/*      */       } 
/* 2761 */       if (param1MouseEvent.isShiftDown() || BasicGraphicsUtils.isMenuShortcutKeyDown(param1MouseEvent)) {
/*      */         return;
/*      */       }
/*      */       
/* 2765 */       int i = BasicListUI.this.locationToIndex(BasicListUI.this.list, param1MouseEvent.getPoint());
/* 2766 */       if (i != -1) {
/*      */         
/* 2768 */         if (BasicListUI.this.isFileList) {
/*      */           return;
/*      */         }
/* 2771 */         Rectangle rectangle = BasicListUI.this.getCellBounds(BasicListUI.this.list, i, i);
/* 2772 */         if (rectangle != null) {
/* 2773 */           BasicListUI.this.list.scrollRectToVisible(rectangle);
/* 2774 */           BasicListUI.this.list.setSelectionInterval(i, i);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {}
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 2783 */       if (SwingUtilities2.shouldIgnore(param1MouseEvent, BasicListUI.this.list)) {
/*      */         return;
/*      */       }
/*      */       
/* 2787 */       if (BasicListUI.this.list.getDragEnabled()) {
/* 2788 */         MouseEvent mouseEvent = DragRecognitionSupport.mouseReleased(param1MouseEvent);
/* 2789 */         if (mouseEvent != null) {
/* 2790 */           SwingUtilities2.adjustFocus(BasicListUI.this.list);
/* 2791 */           if (!this.dragPressDidSelection) {
/* 2792 */             adjustSelection(mouseEvent);
/*      */           }
/*      */         } 
/*      */       } else {
/* 2796 */         BasicListUI.this.list.setValueIsAdjusting(false);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void repaintCellFocus() {
/* 2805 */       int i = BasicListUI.adjustIndex(BasicListUI.this.list.getLeadSelectionIndex(), BasicListUI.this.list);
/* 2806 */       if (i != -1) {
/* 2807 */         Rectangle rectangle = BasicListUI.this.getCellBounds(BasicListUI.this.list, i, i);
/* 2808 */         if (rectangle != null) {
/* 2809 */           BasicListUI.this.list.repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/* 2819 */       repaintCellFocus();
/*      */     }
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/* 2823 */       repaintCellFocus();
/*      */     }
/*      */     private Handler() {} }
/*      */   
/*      */   private static int adjustIndex(int paramInt, JList paramJList) {
/* 2828 */     return (paramInt < paramJList.getModel().getSize()) ? paramInt : -1;
/*      */   }
/*      */   
/* 2831 */   private static final TransferHandler defaultTransferHandler = new ListTransferHandler();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class ListTransferHandler
/*      */     extends TransferHandler
/*      */     implements UIResource
/*      */   {
/*      */     protected Transferable createTransferable(JComponent param1JComponent) {
/* 2845 */       if (param1JComponent instanceof JList) {
/* 2846 */         JList jList = (JList)param1JComponent;
/* 2847 */         Object[] arrayOfObject = jList.getSelectedValues();
/*      */         
/* 2849 */         if (arrayOfObject == null || arrayOfObject.length == 0) {
/* 2850 */           return null;
/*      */         }
/*      */         
/* 2853 */         StringBuffer stringBuffer1 = new StringBuffer();
/* 2854 */         StringBuffer stringBuffer2 = new StringBuffer();
/*      */         
/* 2856 */         stringBuffer2.append("<html>\n<body>\n<ul>\n");
/*      */         
/* 2858 */         for (byte b = 0; b < arrayOfObject.length; b++) {
/* 2859 */           Object object = arrayOfObject[b];
/* 2860 */           String str = (object == null) ? "" : object.toString();
/* 2861 */           stringBuffer1.append(str + "\n");
/* 2862 */           stringBuffer2.append("  <li>" + str + "\n");
/*      */         } 
/*      */ 
/*      */         
/* 2866 */         stringBuffer1.deleteCharAt(stringBuffer1.length() - 1);
/* 2867 */         stringBuffer2.append("</ul>\n</body>\n</html>");
/*      */         
/* 2869 */         return new BasicTransferable(stringBuffer1.toString(), stringBuffer2.toString());
/*      */       } 
/*      */       
/* 2872 */       return null;
/*      */     }
/*      */     
/*      */     public int getSourceActions(JComponent param1JComponent) {
/* 2876 */       return 1;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicListUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */