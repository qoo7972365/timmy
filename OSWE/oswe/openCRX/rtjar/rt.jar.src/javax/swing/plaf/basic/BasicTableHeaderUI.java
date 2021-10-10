/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.Enumeration;
/*     */ import javax.swing.CellRendererPane;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.RowSorter;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.TableHeaderUI;
/*     */ import javax.swing.table.JTableHeader;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ import javax.swing.table.TableModel;
/*     */ import sun.swing.DefaultLookup;
/*     */ import sun.swing.SwingUtilities2;
/*     */ import sun.swing.UIAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicTableHeaderUI
/*     */   extends TableHeaderUI
/*     */ {
/*  46 */   private static Cursor resizeCursor = Cursor.getPredefinedCursor(11);
/*     */ 
/*     */ 
/*     */   
/*     */   protected JTableHeader header;
/*     */ 
/*     */ 
/*     */   
/*     */   protected CellRendererPane rendererPane;
/*     */ 
/*     */   
/*     */   protected MouseInputListener mouseInputListener;
/*     */ 
/*     */   
/*  60 */   private int rolloverColumn = -1;
/*     */ 
/*     */   
/*  63 */   private int selectedColumnIndex = 0;
/*     */   
/*  65 */   private static FocusListener focusListener = new FocusListener() {
/*     */       public void focusGained(FocusEvent param1FocusEvent) {
/*  67 */         repaintHeader(param1FocusEvent.getSource());
/*     */       }
/*     */       
/*     */       public void focusLost(FocusEvent param1FocusEvent) {
/*  71 */         repaintHeader(param1FocusEvent.getSource());
/*     */       }
/*     */       
/*     */       private void repaintHeader(Object param1Object) {
/*  75 */         if (param1Object instanceof JTableHeader) {
/*  76 */           JTableHeader jTableHeader = (JTableHeader)param1Object;
/*     */ 
/*     */           
/*  79 */           BasicTableHeaderUI basicTableHeaderUI = (BasicTableHeaderUI)BasicLookAndFeel.getUIOfType(jTableHeader.getUI(), BasicTableHeaderUI.class);
/*     */           
/*  81 */           if (basicTableHeaderUI == null) {
/*     */             return;
/*     */           }
/*     */           
/*  85 */           jTableHeader.repaint(jTableHeader.getHeaderRect(basicTableHeaderUI.getSelectedColumnIndex()));
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   public class MouseInputHandler
/*     */     implements MouseInputListener
/*     */   {
/*     */     private int mouseXOffset;
/*     */     
/*  97 */     private Cursor otherCursor = BasicTableHeaderUI.resizeCursor;
/*     */     
/*     */     public void mouseClicked(MouseEvent param1MouseEvent) {
/* 100 */       if (!BasicTableHeaderUI.this.header.isEnabled()) {
/*     */         return;
/*     */       }
/* 103 */       if (param1MouseEvent.getClickCount() % 2 == 1 && 
/* 104 */         SwingUtilities.isLeftMouseButton(param1MouseEvent)) {
/* 105 */         JTable jTable = BasicTableHeaderUI.this.header.getTable();
/*     */         RowSorter<? extends TableModel> rowSorter;
/* 107 */         if (jTable != null && (rowSorter = jTable.getRowSorter()) != null) {
/* 108 */           int i = BasicTableHeaderUI.this.header.columnAtPoint(param1MouseEvent.getPoint());
/* 109 */           if (i != -1) {
/* 110 */             i = jTable.convertColumnIndexToModel(i);
/*     */             
/* 112 */             rowSorter.toggleSortOrder(i);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private TableColumn getResizingColumn(Point param1Point) {
/* 119 */       return getResizingColumn(param1Point, BasicTableHeaderUI.this.header.columnAtPoint(param1Point));
/*     */     }
/*     */     private TableColumn getResizingColumn(Point param1Point, int param1Int) {
/*     */       int j;
/* 123 */       if (param1Int == -1) {
/* 124 */         return null;
/*     */       }
/* 126 */       Rectangle rectangle = BasicTableHeaderUI.this.header.getHeaderRect(param1Int);
/* 127 */       rectangle.grow(-3, 0);
/* 128 */       if (rectangle.contains(param1Point)) {
/* 129 */         return null;
/*     */       }
/* 131 */       int i = rectangle.x + rectangle.width / 2;
/*     */       
/* 133 */       if (BasicTableHeaderUI.this.header.getComponentOrientation().isLeftToRight()) {
/* 134 */         j = (param1Point.x < i) ? (param1Int - 1) : param1Int;
/*     */       } else {
/* 136 */         j = (param1Point.x < i) ? param1Int : (param1Int - 1);
/*     */       } 
/* 138 */       if (j == -1) {
/* 139 */         return null;
/*     */       }
/* 141 */       return BasicTableHeaderUI.this.header.getColumnModel().getColumn(j);
/*     */     }
/*     */     
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 145 */       if (!BasicTableHeaderUI.this.header.isEnabled()) {
/*     */         return;
/*     */       }
/* 148 */       BasicTableHeaderUI.this.header.setDraggedColumn((TableColumn)null);
/* 149 */       BasicTableHeaderUI.this.header.setResizingColumn((TableColumn)null);
/* 150 */       BasicTableHeaderUI.this.header.setDraggedDistance(0);
/*     */       
/* 152 */       Point point = param1MouseEvent.getPoint();
/*     */ 
/*     */       
/* 155 */       TableColumnModel tableColumnModel = BasicTableHeaderUI.this.header.getColumnModel();
/* 156 */       int i = BasicTableHeaderUI.this.header.columnAtPoint(point);
/*     */       
/* 158 */       if (i != -1) {
/*     */         
/* 160 */         TableColumn tableColumn = getResizingColumn(point, i);
/* 161 */         if (BasicTableHeaderUI.canResize(tableColumn, BasicTableHeaderUI.this.header)) {
/* 162 */           BasicTableHeaderUI.this.header.setResizingColumn(tableColumn);
/* 163 */           if (BasicTableHeaderUI.this.header.getComponentOrientation().isLeftToRight()) {
/* 164 */             this.mouseXOffset = point.x - tableColumn.getWidth();
/*     */           } else {
/* 166 */             this.mouseXOffset = point.x + tableColumn.getWidth();
/*     */           }
/*     */         
/* 169 */         } else if (BasicTableHeaderUI.this.header.getReorderingAllowed()) {
/* 170 */           TableColumn tableColumn1 = tableColumnModel.getColumn(i);
/* 171 */           BasicTableHeaderUI.this.header.setDraggedColumn(tableColumn1);
/* 172 */           this.mouseXOffset = point.x;
/*     */         } 
/*     */       } 
/*     */       
/* 176 */       if (BasicTableHeaderUI.this.header.getReorderingAllowed()) {
/* 177 */         int j = BasicTableHeaderUI.this.rolloverColumn;
/* 178 */         BasicTableHeaderUI.this.rolloverColumn = -1;
/* 179 */         BasicTableHeaderUI.this.rolloverColumnUpdated(j, BasicTableHeaderUI.this.rolloverColumn);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void swapCursor() {
/* 184 */       Cursor cursor = BasicTableHeaderUI.this.header.getCursor();
/* 185 */       BasicTableHeaderUI.this.header.setCursor(this.otherCursor);
/* 186 */       this.otherCursor = cursor;
/*     */     }
/*     */     
/*     */     public void mouseMoved(MouseEvent param1MouseEvent) {
/* 190 */       if (!BasicTableHeaderUI.this.header.isEnabled()) {
/*     */         return;
/*     */       }
/* 193 */       if (BasicTableHeaderUI.canResize(getResizingColumn(param1MouseEvent.getPoint()), BasicTableHeaderUI.this.header) != (
/* 194 */         (BasicTableHeaderUI.this.header.getCursor() == BasicTableHeaderUI.resizeCursor))) {
/* 195 */         swapCursor();
/*     */       }
/* 197 */       BasicTableHeaderUI.this.updateRolloverColumn(param1MouseEvent);
/*     */     }
/*     */     
/*     */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 201 */       if (!BasicTableHeaderUI.this.header.isEnabled()) {
/*     */         return;
/*     */       }
/* 204 */       int i = param1MouseEvent.getX();
/*     */       
/* 206 */       TableColumn tableColumn1 = BasicTableHeaderUI.this.header.getResizingColumn();
/* 207 */       TableColumn tableColumn2 = BasicTableHeaderUI.this.header.getDraggedColumn();
/*     */       
/* 209 */       boolean bool = BasicTableHeaderUI.this.header.getComponentOrientation().isLeftToRight();
/*     */       
/* 211 */       if (tableColumn1 != null) {
/* 212 */         int k, j = tableColumn1.getWidth();
/*     */         
/* 214 */         if (bool) {
/* 215 */           k = i - this.mouseXOffset;
/*     */         } else {
/* 217 */           k = this.mouseXOffset - i;
/*     */         } 
/* 219 */         this.mouseXOffset += BasicTableHeaderUI.this.changeColumnWidth(tableColumn1, BasicTableHeaderUI.this.header, j, k);
/*     */       
/*     */       }
/* 222 */       else if (tableColumn2 != null) {
/* 223 */         TableColumnModel tableColumnModel = BasicTableHeaderUI.this.header.getColumnModel();
/* 224 */         int j = i - this.mouseXOffset;
/* 225 */         byte b = (j < 0) ? -1 : 1;
/* 226 */         int k = BasicTableHeaderUI.this.viewIndexForColumn(tableColumn2);
/* 227 */         int m = k + (bool ? b : -b);
/* 228 */         if (0 <= m && m < tableColumnModel.getColumnCount()) {
/* 229 */           int n = tableColumnModel.getColumn(m).getWidth();
/* 230 */           if (Math.abs(j) > n / 2) {
/*     */             
/* 232 */             this.mouseXOffset += b * n;
/* 233 */             BasicTableHeaderUI.this.header.setDraggedDistance(j - b * n);
/*     */ 
/*     */ 
/*     */             
/* 237 */             int i1 = SwingUtilities2.convertColumnIndexToModel(BasicTableHeaderUI.this.header
/* 238 */                 .getColumnModel(), BasicTableHeaderUI.this
/* 239 */                 .getSelectedColumnIndex());
/*     */ 
/*     */             
/* 242 */             tableColumnModel.moveColumn(k, m);
/*     */ 
/*     */             
/* 245 */             BasicTableHeaderUI.this.selectColumn(
/* 246 */                 SwingUtilities2.convertColumnIndexToView(BasicTableHeaderUI.this.header
/* 247 */                   .getColumnModel(), i1), false);
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */         
/* 253 */         setDraggedDistance(j, k);
/*     */       } 
/*     */       
/* 256 */       BasicTableHeaderUI.this.updateRolloverColumn(param1MouseEvent);
/*     */     }
/*     */     
/*     */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 260 */       if (!BasicTableHeaderUI.this.header.isEnabled()) {
/*     */         return;
/*     */       }
/* 263 */       setDraggedDistance(0, BasicTableHeaderUI.this.viewIndexForColumn(BasicTableHeaderUI.this.header.getDraggedColumn()));
/*     */       
/* 265 */       BasicTableHeaderUI.this.header.setResizingColumn((TableColumn)null);
/* 266 */       BasicTableHeaderUI.this.header.setDraggedColumn((TableColumn)null);
/*     */       
/* 268 */       BasicTableHeaderUI.this.updateRolloverColumn(param1MouseEvent);
/*     */     }
/*     */     
/*     */     public void mouseEntered(MouseEvent param1MouseEvent) {
/* 272 */       if (!BasicTableHeaderUI.this.header.isEnabled()) {
/*     */         return;
/*     */       }
/* 275 */       BasicTableHeaderUI.this.updateRolloverColumn(param1MouseEvent);
/*     */     }
/*     */     
/*     */     public void mouseExited(MouseEvent param1MouseEvent) {
/* 279 */       if (!BasicTableHeaderUI.this.header.isEnabled()) {
/*     */         return;
/*     */       }
/* 282 */       int i = BasicTableHeaderUI.this.rolloverColumn;
/* 283 */       BasicTableHeaderUI.this.rolloverColumn = -1;
/* 284 */       BasicTableHeaderUI.this.rolloverColumnUpdated(i, BasicTableHeaderUI.this.rolloverColumn);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void setDraggedDistance(int param1Int1, int param1Int2) {
/* 291 */       BasicTableHeaderUI.this.header.setDraggedDistance(param1Int1);
/* 292 */       if (param1Int2 != -1) {
/* 293 */         BasicTableHeaderUI.this.header.getColumnModel().moveColumn(param1Int2, param1Int2);
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
/*     */   protected MouseInputListener createMouseInputListener() {
/* 306 */     return new MouseInputHandler();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 314 */     return new BasicTableHeaderUI();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 320 */     this.header = (JTableHeader)paramJComponent;
/*     */     
/* 322 */     this.rendererPane = new CellRendererPane();
/* 323 */     this.header.add(this.rendererPane);
/*     */     
/* 325 */     installDefaults();
/* 326 */     installListeners();
/* 327 */     installKeyboardActions();
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
/*     */   protected void installDefaults() {
/* 339 */     LookAndFeel.installColorsAndFont(this.header, "TableHeader.background", "TableHeader.foreground", "TableHeader.font");
/*     */     
/* 341 */     LookAndFeel.installProperty(this.header, "opaque", Boolean.TRUE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/* 348 */     this.mouseInputListener = createMouseInputListener();
/*     */     
/* 350 */     this.header.addMouseListener(this.mouseInputListener);
/* 351 */     this.header.addMouseMotionListener(this.mouseInputListener);
/* 352 */     this.header.addFocusListener(focusListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installKeyboardActions() {
/* 359 */     InputMap inputMap = (InputMap)DefaultLookup.get(this.header, this, "TableHeader.ancestorInputMap");
/*     */     
/* 361 */     SwingUtilities.replaceUIInputMap(this.header, 1, inputMap);
/*     */     
/* 363 */     LazyActionMap.installLazyActionMap(this.header, BasicTableHeaderUI.class, "TableHeader.actionMap");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 370 */     uninstallDefaults();
/* 371 */     uninstallListeners();
/* 372 */     uninstallKeyboardActions();
/*     */     
/* 374 */     this.header.remove(this.rendererPane);
/* 375 */     this.rendererPane = null;
/* 376 */     this.header = null;
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults() {}
/*     */   
/*     */   protected void uninstallListeners() {
/* 382 */     this.header.removeMouseListener(this.mouseInputListener);
/* 383 */     this.header.removeMouseMotionListener(this.mouseInputListener);
/*     */     
/* 385 */     this.mouseInputListener = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallKeyboardActions() {
/* 392 */     SwingUtilities.replaceUIInputMap(this.header, 0, null);
/* 393 */     SwingUtilities.replaceUIActionMap(this.header, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/* 400 */     paramLazyActionMap.put(new Actions("toggleSortOrder"));
/* 401 */     paramLazyActionMap.put(new Actions("selectColumnToLeft"));
/* 402 */     paramLazyActionMap.put(new Actions("selectColumnToRight"));
/* 403 */     paramLazyActionMap.put(new Actions("moveColumnLeft"));
/* 404 */     paramLazyActionMap.put(new Actions("moveColumnRight"));
/* 405 */     paramLazyActionMap.put(new Actions("resizeLeft"));
/* 406 */     paramLazyActionMap.put(new Actions("resizeRight"));
/* 407 */     paramLazyActionMap.put(new Actions("focusTable"));
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
/*     */   protected int getRolloverColumn() {
/* 424 */     return this.rolloverColumn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rolloverColumnUpdated(int paramInt1, int paramInt2) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateRolloverColumn(MouseEvent paramMouseEvent) {
/* 444 */     if (this.header.getDraggedColumn() == null && this.header
/* 445 */       .contains(paramMouseEvent.getPoint())) {
/*     */       
/* 447 */       int i = this.header.columnAtPoint(paramMouseEvent.getPoint());
/* 448 */       if (i != this.rolloverColumn) {
/* 449 */         int j = this.rolloverColumn;
/* 450 */         this.rolloverColumn = i;
/* 451 */         rolloverColumnUpdated(j, this.rolloverColumn);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int selectNextColumn(boolean paramBoolean) {
/* 460 */     int i = getSelectedColumnIndex();
/* 461 */     if (i < this.header.getColumnModel().getColumnCount() - 1) {
/* 462 */       i++;
/* 463 */       if (paramBoolean) {
/* 464 */         selectColumn(i);
/*     */       }
/*     */     } 
/* 467 */     return i;
/*     */   }
/*     */   
/*     */   private int selectPreviousColumn(boolean paramBoolean) {
/* 471 */     int i = getSelectedColumnIndex();
/* 472 */     if (i > 0) {
/* 473 */       i--;
/* 474 */       if (paramBoolean) {
/* 475 */         selectColumn(i);
/*     */       }
/*     */     } 
/* 478 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void selectColumn(int paramInt) {
/* 486 */     selectColumn(paramInt, true);
/*     */   }
/*     */   
/*     */   void selectColumn(int paramInt, boolean paramBoolean) {
/* 490 */     Rectangle rectangle = this.header.getHeaderRect(this.selectedColumnIndex);
/* 491 */     this.header.repaint(rectangle);
/* 492 */     this.selectedColumnIndex = paramInt;
/* 493 */     rectangle = this.header.getHeaderRect(paramInt);
/* 494 */     this.header.repaint(rectangle);
/* 495 */     if (paramBoolean) {
/* 496 */       scrollToColumn(paramInt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void scrollToColumn(int paramInt) {
/*     */     Container container;
/*     */     JTable jTable;
/* 509 */     if (this.header.getParent() == null || (
/* 510 */       container = this.header.getParent().getParent()) == null || !(container instanceof JScrollPane) || (
/*     */       
/* 512 */       jTable = this.header.getTable()) == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 517 */     Rectangle rectangle1 = jTable.getVisibleRect();
/* 518 */     Rectangle rectangle2 = jTable.getCellRect(0, paramInt, true);
/* 519 */     rectangle1.x = rectangle2.x;
/* 520 */     rectangle1.width = rectangle2.width;
/* 521 */     jTable.scrollRectToVisible(rectangle1);
/*     */   }
/*     */   
/*     */   private int getSelectedColumnIndex() {
/* 525 */     int i = this.header.getColumnModel().getColumnCount();
/* 526 */     if (this.selectedColumnIndex >= i && i > 0) {
/* 527 */       this.selectedColumnIndex = i - 1;
/*     */     }
/* 529 */     return this.selectedColumnIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean canResize(TableColumn paramTableColumn, JTableHeader paramJTableHeader) {
/* 534 */     return (paramTableColumn != null && paramJTableHeader.getResizingAllowed() && paramTableColumn
/* 535 */       .getResizable());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int changeColumnWidth(TableColumn paramTableColumn, JTableHeader paramJTableHeader, int paramInt1, int paramInt2) {
/* 541 */     paramTableColumn.setWidth(paramInt2);
/*     */     
/*     */     Container container;
/*     */     
/*     */     JTable jTable;
/* 546 */     if (paramJTableHeader.getParent() == null || (
/* 547 */       container = paramJTableHeader.getParent().getParent()) == null || !(container instanceof JScrollPane) || (
/*     */       
/* 549 */       jTable = paramJTableHeader.getTable()) == null) {
/* 550 */       return 0;
/*     */     }
/*     */     
/* 553 */     if (!container.getComponentOrientation().isLeftToRight() && 
/* 554 */       !paramJTableHeader.getComponentOrientation().isLeftToRight()) {
/* 555 */       JViewport jViewport = ((JScrollPane)container).getViewport();
/* 556 */       int i = jViewport.getWidth();
/* 557 */       int j = paramInt2 - paramInt1;
/* 558 */       int k = jTable.getWidth() + j;
/*     */ 
/*     */       
/* 561 */       Dimension dimension = jTable.getSize();
/* 562 */       dimension.width += j;
/* 563 */       jTable.setSize(dimension);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 569 */       if (k >= i && jTable
/* 570 */         .getAutoResizeMode() == 0) {
/* 571 */         Point point = jViewport.getViewPosition();
/* 572 */         point.x = Math.max(0, Math.min(k - i, point.x + j));
/*     */         
/* 574 */         jViewport.setViewPosition(point);
/* 575 */         return j;
/*     */       } 
/*     */     } 
/* 578 */     return 0;
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
/*     */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/* 594 */     super.getBaseline(paramJComponent, paramInt1, paramInt2);
/* 595 */     int i = -1;
/* 596 */     TableColumnModel tableColumnModel = this.header.getColumnModel();
/* 597 */     for (byte b = 0; b < tableColumnModel.getColumnCount(); 
/* 598 */       b++) {
/* 599 */       TableColumn tableColumn = tableColumnModel.getColumn(b);
/* 600 */       Component component = getHeaderRenderer(b);
/* 601 */       Dimension dimension = component.getPreferredSize();
/* 602 */       int j = component.getBaseline(dimension.width, paramInt2);
/* 603 */       if (j >= 0) {
/* 604 */         if (i == -1) {
/* 605 */           i = j;
/*     */         }
/* 607 */         else if (i != j) {
/* 608 */           i = -1;
/*     */           break;
/*     */         } 
/*     */       }
/*     */     } 
/* 613 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 621 */     if (this.header.getColumnModel().getColumnCount() <= 0) {
/*     */       return;
/*     */     }
/* 624 */     boolean bool = this.header.getComponentOrientation().isLeftToRight();
/*     */     
/* 626 */     Rectangle rectangle1 = paramGraphics.getClipBounds();
/* 627 */     Point point1 = rectangle1.getLocation();
/* 628 */     Point point2 = new Point(rectangle1.x + rectangle1.width - 1, rectangle1.y);
/* 629 */     TableColumnModel tableColumnModel = this.header.getColumnModel();
/* 630 */     int i = this.header.columnAtPoint(bool ? point1 : point2);
/* 631 */     int j = this.header.columnAtPoint(bool ? point2 : point1);
/*     */     
/* 633 */     if (i == -1) {
/* 634 */       i = 0;
/*     */     }
/*     */ 
/*     */     
/* 638 */     if (j == -1) {
/* 639 */       j = tableColumnModel.getColumnCount() - 1;
/*     */     }
/*     */     
/* 642 */     TableColumn tableColumn = this.header.getDraggedColumn();
/*     */     
/* 644 */     Rectangle rectangle2 = this.header.getHeaderRect(bool ? i : j);
/*     */     
/* 646 */     if (bool) {
/* 647 */       for (int k = i; k <= j; k++) {
/* 648 */         TableColumn tableColumn1 = tableColumnModel.getColumn(k);
/* 649 */         int m = tableColumn1.getWidth();
/* 650 */         rectangle2.width = m;
/* 651 */         if (tableColumn1 != tableColumn) {
/* 652 */           paintCell(paramGraphics, rectangle2, k);
/*     */         }
/* 654 */         rectangle2.x += m;
/*     */       } 
/*     */     } else {
/* 657 */       for (int k = j; k >= i; k--) {
/* 658 */         TableColumn tableColumn1 = tableColumnModel.getColumn(k);
/* 659 */         int m = tableColumn1.getWidth();
/* 660 */         rectangle2.width = m;
/* 661 */         if (tableColumn1 != tableColumn) {
/* 662 */           paintCell(paramGraphics, rectangle2, k);
/*     */         }
/* 664 */         rectangle2.x += m;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 669 */     if (tableColumn != null) {
/* 670 */       int k = viewIndexForColumn(tableColumn);
/* 671 */       Rectangle rectangle = this.header.getHeaderRect(k);
/*     */ 
/*     */       
/* 674 */       paramGraphics.setColor(this.header.getParent().getBackground());
/* 675 */       paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */ 
/*     */       
/* 678 */       rectangle.x += this.header.getDraggedDistance();
/*     */ 
/*     */       
/* 681 */       paramGraphics.setColor(this.header.getBackground());
/* 682 */       paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */ 
/*     */       
/* 685 */       paintCell(paramGraphics, rectangle, k);
/*     */     } 
/*     */ 
/*     */     
/* 689 */     this.rendererPane.removeAll();
/*     */   }
/*     */   
/*     */   private Component getHeaderRenderer(int paramInt) {
/* 693 */     TableColumn tableColumn = this.header.getColumnModel().getColumn(paramInt);
/* 694 */     TableCellRenderer tableCellRenderer = tableColumn.getHeaderRenderer();
/* 695 */     if (tableCellRenderer == null) {
/* 696 */       tableCellRenderer = this.header.getDefaultRenderer();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 701 */     boolean bool = (!this.header.isPaintingForPrint() && paramInt == getSelectedColumnIndex() && this.header.hasFocus()) ? true : false;
/* 702 */     return tableCellRenderer.getTableCellRendererComponent(this.header.getTable(), tableColumn
/* 703 */         .getHeaderValue(), false, bool, -1, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintCell(Graphics paramGraphics, Rectangle paramRectangle, int paramInt) {
/* 709 */     Component component = getHeaderRenderer(paramInt);
/* 710 */     this.rendererPane.paintComponent(paramGraphics, component, this.header, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, true);
/*     */   }
/*     */ 
/*     */   
/*     */   private int viewIndexForColumn(TableColumn paramTableColumn) {
/* 715 */     TableColumnModel tableColumnModel = this.header.getColumnModel();
/* 716 */     for (byte b = 0; b < tableColumnModel.getColumnCount(); b++) {
/* 717 */       if (tableColumnModel.getColumn(b) == paramTableColumn) {
/* 718 */         return b;
/*     */       }
/*     */     } 
/* 721 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getHeaderHeight() {
/* 729 */     int i = 0;
/* 730 */     boolean bool = false;
/* 731 */     TableColumnModel tableColumnModel = this.header.getColumnModel();
/* 732 */     for (byte b = 0; b < tableColumnModel.getColumnCount(); b++) {
/* 733 */       TableColumn tableColumn = tableColumnModel.getColumn(b);
/* 734 */       boolean bool1 = (tableColumn.getHeaderRenderer() == null) ? true : false;
/*     */       
/* 736 */       if (!bool1 || !bool) {
/* 737 */         Component component = getHeaderRenderer(b);
/* 738 */         int j = (component.getPreferredSize()).height;
/* 739 */         i = Math.max(i, j);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 745 */         if (bool1 && j > 0) {
/* 746 */           Object object = tableColumn.getHeaderValue();
/* 747 */           if (object != null) {
/* 748 */             object = object.toString();
/*     */             
/* 750 */             if (object != null && !object.equals("")) {
/* 751 */               bool = true;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 757 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private Dimension createHeaderSize(long paramLong) {
/* 762 */     if (paramLong > 2147483647L) {
/* 763 */       paramLong = 2147483647L;
/*     */     }
/* 765 */     return new Dimension((int)paramLong, getHeaderHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 774 */     long l = 0L;
/* 775 */     Enumeration<TableColumn> enumeration = this.header.getColumnModel().getColumns();
/* 776 */     while (enumeration.hasMoreElements()) {
/* 777 */       TableColumn tableColumn = enumeration.nextElement();
/* 778 */       l += tableColumn.getMinWidth();
/*     */     } 
/* 780 */     return createHeaderSize(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 790 */     long l = 0L;
/* 791 */     Enumeration<TableColumn> enumeration = this.header.getColumnModel().getColumns();
/* 792 */     while (enumeration.hasMoreElements()) {
/* 793 */       TableColumn tableColumn = enumeration.nextElement();
/* 794 */       l += tableColumn.getPreferredWidth();
/*     */     } 
/* 796 */     return createHeaderSize(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 804 */     long l = 0L;
/* 805 */     Enumeration<TableColumn> enumeration = this.header.getColumnModel().getColumns();
/* 806 */     while (enumeration.hasMoreElements()) {
/* 807 */       TableColumn tableColumn = enumeration.nextElement();
/* 808 */       l += tableColumn.getMaxWidth();
/*     */     } 
/* 810 */     return createHeaderSize(l);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Actions
/*     */     extends UIAction
/*     */   {
/*     */     public static final String TOGGLE_SORT_ORDER = "toggleSortOrder";
/*     */     
/*     */     public static final String SELECT_COLUMN_TO_LEFT = "selectColumnToLeft";
/*     */     
/*     */     public static final String SELECT_COLUMN_TO_RIGHT = "selectColumnToRight";
/*     */     
/*     */     public static final String MOVE_COLUMN_LEFT = "moveColumnLeft";
/*     */     
/*     */     public static final String MOVE_COLUMN_RIGHT = "moveColumnRight";
/*     */     
/*     */     public static final String RESIZE_LEFT = "resizeLeft";
/*     */     public static final String RESIZE_RIGHT = "resizeRight";
/*     */     public static final String FOCUS_TABLE = "focusTable";
/*     */     
/*     */     public Actions(String param1String) {
/* 832 */       super(param1String);
/*     */     }
/*     */     
/*     */     public boolean isEnabled(Object param1Object) {
/* 836 */       if (param1Object instanceof JTableHeader) {
/* 837 */         JTableHeader jTableHeader = (JTableHeader)param1Object;
/* 838 */         TableColumnModel tableColumnModel = jTableHeader.getColumnModel();
/* 839 */         if (tableColumnModel.getColumnCount() <= 0) {
/* 840 */           return false;
/*     */         }
/*     */         
/* 843 */         String str = getName();
/*     */         
/* 845 */         BasicTableHeaderUI basicTableHeaderUI = (BasicTableHeaderUI)BasicLookAndFeel.getUIOfType(jTableHeader.getUI(), BasicTableHeaderUI.class);
/*     */         
/* 847 */         if (basicTableHeaderUI != null) {
/* 848 */           if (str == "moveColumnLeft")
/* 849 */             return (jTableHeader.getReorderingAllowed() && 
/* 850 */               maybeMoveColumn(true, jTableHeader, basicTableHeaderUI, false)); 
/* 851 */           if (str == "moveColumnRight")
/* 852 */             return (jTableHeader.getReorderingAllowed() && 
/* 853 */               maybeMoveColumn(false, jTableHeader, basicTableHeaderUI, false)); 
/* 854 */           if (str == "resizeLeft" || str == "resizeRight")
/*     */           {
/* 856 */             return BasicTableHeaderUI.canResize(tableColumnModel.getColumn(basicTableHeaderUI.getSelectedColumnIndex()), jTableHeader); } 
/* 857 */           if (str == "focusTable") {
/* 858 */             return (jTableHeader.getTable() != null);
/*     */           }
/*     */         } 
/*     */       } 
/* 862 */       return true;
/*     */     }
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 866 */       JTableHeader jTableHeader = (JTableHeader)param1ActionEvent.getSource();
/*     */ 
/*     */       
/* 869 */       BasicTableHeaderUI basicTableHeaderUI = (BasicTableHeaderUI)BasicLookAndFeel.getUIOfType(jTableHeader.getUI(), BasicTableHeaderUI.class);
/*     */       
/* 871 */       if (basicTableHeaderUI == null) {
/*     */         return;
/*     */       }
/*     */       
/* 875 */       String str = getName();
/* 876 */       if ("toggleSortOrder" == str) {
/* 877 */         JTable jTable = jTableHeader.getTable();
/* 878 */         RowSorter<? extends TableModel> rowSorter = (jTable == null) ? null : jTable.getRowSorter();
/* 879 */         if (rowSorter != null) {
/* 880 */           int i = basicTableHeaderUI.getSelectedColumnIndex();
/* 881 */           i = jTable.convertColumnIndexToModel(i);
/*     */           
/* 883 */           rowSorter.toggleSortOrder(i);
/*     */         } 
/* 885 */       } else if ("selectColumnToLeft" == str) {
/* 886 */         if (jTableHeader.getComponentOrientation().isLeftToRight()) {
/* 887 */           basicTableHeaderUI.selectPreviousColumn(true);
/*     */         } else {
/* 889 */           basicTableHeaderUI.selectNextColumn(true);
/*     */         } 
/* 891 */       } else if ("selectColumnToRight" == str) {
/* 892 */         if (jTableHeader.getComponentOrientation().isLeftToRight()) {
/* 893 */           basicTableHeaderUI.selectNextColumn(true);
/*     */         } else {
/* 895 */           basicTableHeaderUI.selectPreviousColumn(true);
/*     */         } 
/* 897 */       } else if ("moveColumnLeft" == str) {
/* 898 */         moveColumn(true, jTableHeader, basicTableHeaderUI);
/* 899 */       } else if ("moveColumnRight" == str) {
/* 900 */         moveColumn(false, jTableHeader, basicTableHeaderUI);
/* 901 */       } else if ("resizeLeft" == str) {
/* 902 */         resize(true, jTableHeader, basicTableHeaderUI);
/* 903 */       } else if ("resizeRight" == str) {
/* 904 */         resize(false, jTableHeader, basicTableHeaderUI);
/* 905 */       } else if ("focusTable" == str) {
/* 906 */         JTable jTable = jTableHeader.getTable();
/* 907 */         if (jTable != null) {
/* 908 */           jTable.requestFocusInWindow();
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void moveColumn(boolean param1Boolean, JTableHeader param1JTableHeader, BasicTableHeaderUI param1BasicTableHeaderUI) {
/* 915 */       maybeMoveColumn(param1Boolean, param1JTableHeader, param1BasicTableHeaderUI, true);
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean maybeMoveColumn(boolean param1Boolean1, JTableHeader param1JTableHeader, BasicTableHeaderUI param1BasicTableHeaderUI, boolean param1Boolean2) {
/* 920 */       int j, i = param1BasicTableHeaderUI.getSelectedColumnIndex();
/*     */ 
/*     */       
/* 923 */       if (param1JTableHeader.getComponentOrientation().isLeftToRight()) {
/*     */         
/* 925 */         j = param1Boolean1 ? param1BasicTableHeaderUI.selectPreviousColumn(param1Boolean2) : param1BasicTableHeaderUI.selectNextColumn(param1Boolean2);
/*     */       } else {
/*     */         
/* 928 */         j = param1Boolean1 ? param1BasicTableHeaderUI.selectNextColumn(param1Boolean2) : param1BasicTableHeaderUI.selectPreviousColumn(param1Boolean2);
/*     */       } 
/*     */       
/* 931 */       if (j != i) {
/* 932 */         if (param1Boolean2) {
/* 933 */           param1JTableHeader.getColumnModel().moveColumn(i, j);
/*     */         } else {
/* 935 */           return true;
/*     */         } 
/*     */       }
/*     */       
/* 939 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     private void resize(boolean param1Boolean, JTableHeader param1JTableHeader, BasicTableHeaderUI param1BasicTableHeaderUI) {
/* 944 */       int i = param1BasicTableHeaderUI.getSelectedColumnIndex();
/*     */       
/* 946 */       TableColumn tableColumn = param1JTableHeader.getColumnModel().getColumn(i);
/*     */       
/* 948 */       param1JTableHeader.setResizingColumn(tableColumn);
/* 949 */       int j = tableColumn.getWidth();
/* 950 */       int k = j;
/*     */       
/* 952 */       if (param1JTableHeader.getComponentOrientation().isLeftToRight()) {
/* 953 */         k += param1Boolean ? -1 : 1;
/*     */       } else {
/* 955 */         k += param1Boolean ? 1 : -1;
/*     */       } 
/*     */       
/* 958 */       param1BasicTableHeaderUI.changeColumnWidth(tableColumn, param1JTableHeader, j, k);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicTableHeaderUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */