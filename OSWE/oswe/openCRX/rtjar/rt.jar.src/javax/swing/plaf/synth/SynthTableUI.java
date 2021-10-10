/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.text.DateFormat;
/*     */ import java.text.Format;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Date;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.TransferHandler;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicTableUI;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.JTableHeader;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthTableUI
/*     */   extends BasicTableUI
/*     */   implements SynthUI, PropertyChangeListener
/*     */ {
/*     */   private SynthStyle style;
/*     */   private boolean useTableColors;
/*     */   private boolean useUIBorder;
/*     */   private Color alternateColor;
/*     */   private TableCellRenderer dateRenderer;
/*     */   private TableCellRenderer numberRenderer;
/*     */   private TableCellRenderer doubleRender;
/*     */   private TableCellRenderer floatRenderer;
/*     */   private TableCellRenderer iconRenderer;
/*     */   private TableCellRenderer imageIconRenderer;
/*     */   private TableCellRenderer booleanRenderer;
/*     */   private TableCellRenderer objectRenderer;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  97 */     return new SynthTableUI();
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
/*     */   protected void installDefaults() {
/* 110 */     this.dateRenderer = installRendererIfPossible(Date.class, (TableCellRenderer)null);
/* 111 */     this.numberRenderer = installRendererIfPossible(Number.class, (TableCellRenderer)null);
/* 112 */     this.doubleRender = installRendererIfPossible(Double.class, (TableCellRenderer)null);
/* 113 */     this.floatRenderer = installRendererIfPossible(Float.class, (TableCellRenderer)null);
/* 114 */     this.iconRenderer = installRendererIfPossible(Icon.class, (TableCellRenderer)null);
/* 115 */     this.imageIconRenderer = installRendererIfPossible(ImageIcon.class, (TableCellRenderer)null);
/* 116 */     this.booleanRenderer = installRendererIfPossible(Boolean.class, new SynthBooleanTableCellRenderer());
/*     */     
/* 118 */     this.objectRenderer = installRendererIfPossible(Object.class, new SynthTableCellRenderer());
/*     */     
/* 120 */     updateStyle(this.table);
/*     */   }
/*     */ 
/*     */   
/*     */   private TableCellRenderer installRendererIfPossible(Class<?> paramClass, TableCellRenderer paramTableCellRenderer) {
/* 125 */     TableCellRenderer tableCellRenderer = this.table.getDefaultRenderer(paramClass);
/*     */     
/* 127 */     if (tableCellRenderer instanceof javax.swing.plaf.UIResource) {
/* 128 */       this.table.setDefaultRenderer(paramClass, paramTableCellRenderer);
/*     */     }
/* 130 */     return tableCellRenderer;
/*     */   }
/*     */   
/*     */   private void updateStyle(JTable paramJTable) {
/* 134 */     SynthContext synthContext = getContext(paramJTable, 1);
/* 135 */     SynthStyle synthStyle = this.style;
/* 136 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/* 137 */     if (this.style != synthStyle) {
/* 138 */       synthContext.setComponentState(513);
/*     */       
/* 140 */       Color color1 = this.table.getSelectionBackground();
/* 141 */       if (color1 == null || color1 instanceof javax.swing.plaf.UIResource) {
/* 142 */         this.table.setSelectionBackground(this.style.getColor(synthContext, ColorType.TEXT_BACKGROUND));
/*     */       }
/*     */ 
/*     */       
/* 146 */       Color color2 = this.table.getSelectionForeground();
/* 147 */       if (color2 == null || color2 instanceof javax.swing.plaf.UIResource) {
/* 148 */         this.table.setSelectionForeground(this.style.getColor(synthContext, ColorType.TEXT_FOREGROUND));
/*     */       }
/*     */ 
/*     */       
/* 152 */       synthContext.setComponentState(1);
/*     */       
/* 154 */       Color color3 = this.table.getGridColor();
/* 155 */       if (color3 == null || color3 instanceof javax.swing.plaf.UIResource) {
/* 156 */         color3 = (Color)this.style.get(synthContext, "Table.gridColor");
/* 157 */         if (color3 == null) {
/* 158 */           color3 = this.style.getColor(synthContext, ColorType.FOREGROUND);
/*     */         }
/* 160 */         this.table.setGridColor((color3 == null) ? new ColorUIResource(Color.GRAY) : color3);
/*     */       } 
/*     */       
/* 163 */       this.useTableColors = this.style.getBoolean(synthContext, "Table.rendererUseTableColors", true);
/*     */       
/* 165 */       this.useUIBorder = this.style.getBoolean(synthContext, "Table.rendererUseUIBorder", true);
/*     */ 
/*     */       
/* 168 */       Object object = this.style.get(synthContext, "Table.rowHeight");
/* 169 */       if (object != null) {
/* 170 */         LookAndFeel.installProperty(this.table, "rowHeight", object);
/*     */       }
/* 172 */       boolean bool = this.style.getBoolean(synthContext, "Table.showGrid", true);
/* 173 */       if (!bool) {
/* 174 */         this.table.setShowGrid(false);
/*     */       }
/* 176 */       Dimension dimension = this.table.getIntercellSpacing();
/*     */       
/* 178 */       if (dimension != null) {
/* 179 */         dimension = (Dimension)this.style.get(synthContext, "Table.intercellSpacing");
/*     */       }
/* 181 */       this.alternateColor = (Color)this.style.get(synthContext, "Table.alternateRowColor");
/* 182 */       if (dimension != null) {
/* 183 */         this.table.setIntercellSpacing(dimension);
/*     */       }
/*     */ 
/*     */       
/* 187 */       if (synthStyle != null) {
/* 188 */         uninstallKeyboardActions();
/* 189 */         installKeyboardActions();
/*     */       } 
/*     */     } 
/* 192 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/* 200 */     super.installListeners();
/* 201 */     this.table.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 209 */     this.table.setDefaultRenderer(Date.class, this.dateRenderer);
/* 210 */     this.table.setDefaultRenderer(Number.class, this.numberRenderer);
/* 211 */     this.table.setDefaultRenderer(Double.class, this.doubleRender);
/* 212 */     this.table.setDefaultRenderer(Float.class, this.floatRenderer);
/* 213 */     this.table.setDefaultRenderer(Icon.class, this.iconRenderer);
/* 214 */     this.table.setDefaultRenderer(ImageIcon.class, this.imageIconRenderer);
/* 215 */     this.table.setDefaultRenderer(Boolean.class, this.booleanRenderer);
/* 216 */     this.table.setDefaultRenderer(Object.class, this.objectRenderer);
/*     */     
/* 218 */     if (this.table.getTransferHandler() instanceof javax.swing.plaf.UIResource) {
/* 219 */       this.table.setTransferHandler((TransferHandler)null);
/*     */     }
/* 221 */     SynthContext synthContext = getContext(this.table, 1);
/* 222 */     this.style.uninstallDefaults(synthContext);
/* 223 */     synthContext.dispose();
/* 224 */     this.style = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 232 */     this.table.removePropertyChangeListener(this);
/* 233 */     super.uninstallListeners();
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
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 245 */     return getContext(paramJComponent, SynthLookAndFeel.getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 249 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
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
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 270 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 272 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 273 */     synthContext.getPainter().paintTableBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 274 */         .getWidth(), paramJComponent.getHeight());
/* 275 */     paint(synthContext, paramGraphics);
/* 276 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 285 */     paramSynthContext.getPainter().paintTableBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
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
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 299 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 301 */     paint(synthContext, paramGraphics);
/* 302 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {
/* 313 */     Rectangle rectangle1 = paramGraphics.getClipBounds();
/*     */     
/* 315 */     Rectangle rectangle2 = this.table.getBounds();
/*     */ 
/*     */     
/* 318 */     rectangle2.x = rectangle2.y = 0;
/*     */     
/* 320 */     if (this.table.getRowCount() <= 0 || this.table.getColumnCount() <= 0 || 
/*     */ 
/*     */       
/* 323 */       !rectangle2.intersects(rectangle1)) {
/*     */       
/* 325 */       paintDropLines(paramSynthContext, paramGraphics);
/*     */       
/*     */       return;
/*     */     } 
/* 329 */     boolean bool = this.table.getComponentOrientation().isLeftToRight();
/*     */     
/* 331 */     Point point1 = rectangle1.getLocation();
/*     */     
/* 333 */     Point point2 = new Point(rectangle1.x + rectangle1.width - 1, rectangle1.y + rectangle1.height - 1);
/*     */ 
/*     */     
/* 336 */     int i = this.table.rowAtPoint(point1);
/* 337 */     int j = this.table.rowAtPoint(point2);
/*     */ 
/*     */     
/* 340 */     if (i == -1) {
/* 341 */       i = 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 347 */     if (j == -1) {
/* 348 */       j = this.table.getRowCount() - 1;
/*     */     }
/*     */     
/* 351 */     int k = this.table.columnAtPoint(bool ? point1 : point2);
/* 352 */     int m = this.table.columnAtPoint(bool ? point2 : point1);
/*     */     
/* 354 */     if (k == -1) {
/* 355 */       k = 0;
/*     */     }
/*     */ 
/*     */     
/* 359 */     if (m == -1) {
/* 360 */       m = this.table.getColumnCount() - 1;
/*     */     }
/*     */ 
/*     */     
/* 364 */     paintCells(paramSynthContext, paramGraphics, i, j, k, m);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 369 */     paintGrid(paramSynthContext, paramGraphics, i, j, k, m);
/*     */     
/* 371 */     paintDropLines(paramSynthContext, paramGraphics);
/*     */   }
/*     */   
/*     */   private void paintDropLines(SynthContext paramSynthContext, Graphics paramGraphics) {
/* 375 */     JTable.DropLocation dropLocation = this.table.getDropLocation();
/* 376 */     if (dropLocation == null) {
/*     */       return;
/*     */     }
/*     */     
/* 380 */     Color color1 = (Color)this.style.get(paramSynthContext, "Table.dropLineColor");
/* 381 */     Color color2 = (Color)this.style.get(paramSynthContext, "Table.dropLineShortColor");
/* 382 */     if (color1 == null && color2 == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 388 */     Rectangle rectangle = getHDropLineRect(dropLocation);
/* 389 */     if (rectangle != null) {
/* 390 */       int i = rectangle.x;
/* 391 */       int j = rectangle.width;
/* 392 */       if (color1 != null) {
/* 393 */         extendRect(rectangle, true);
/* 394 */         paramGraphics.setColor(color1);
/* 395 */         paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */       } 
/* 397 */       if (!dropLocation.isInsertColumn() && color2 != null) {
/* 398 */         paramGraphics.setColor(color2);
/* 399 */         paramGraphics.fillRect(i, rectangle.y, j, rectangle.height);
/*     */       } 
/*     */     } 
/*     */     
/* 403 */     rectangle = getVDropLineRect(dropLocation);
/* 404 */     if (rectangle != null) {
/* 405 */       int i = rectangle.y;
/* 406 */       int j = rectangle.height;
/* 407 */       if (color1 != null) {
/* 408 */         extendRect(rectangle, false);
/* 409 */         paramGraphics.setColor(color1);
/* 410 */         paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */       } 
/* 412 */       if (!dropLocation.isInsertRow() && color2 != null) {
/* 413 */         paramGraphics.setColor(color2);
/* 414 */         paramGraphics.fillRect(rectangle.x, i, rectangle.width, j);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private Rectangle getHDropLineRect(JTable.DropLocation paramDropLocation) {
/* 420 */     if (!paramDropLocation.isInsertRow()) {
/* 421 */       return null;
/*     */     }
/*     */     
/* 424 */     int i = paramDropLocation.getRow();
/* 425 */     int j = paramDropLocation.getColumn();
/* 426 */     if (j >= this.table.getColumnCount()) {
/* 427 */       j--;
/*     */     }
/*     */     
/* 430 */     Rectangle rectangle = this.table.getCellRect(i, j, true);
/*     */     
/* 432 */     if (i >= this.table.getRowCount()) {
/* 433 */       i--;
/* 434 */       Rectangle rectangle1 = this.table.getCellRect(i, j, true);
/* 435 */       rectangle1.y += rectangle1.height;
/*     */     } 
/*     */     
/* 438 */     if (rectangle.y == 0) {
/* 439 */       rectangle.y = -1;
/*     */     } else {
/* 441 */       rectangle.y -= 2;
/*     */     } 
/*     */     
/* 444 */     rectangle.height = 3;
/*     */     
/* 446 */     return rectangle;
/*     */   }
/*     */   
/*     */   private Rectangle getVDropLineRect(JTable.DropLocation paramDropLocation) {
/* 450 */     if (!paramDropLocation.isInsertColumn()) {
/* 451 */       return null;
/*     */     }
/*     */     
/* 454 */     boolean bool = this.table.getComponentOrientation().isLeftToRight();
/* 455 */     int i = paramDropLocation.getColumn();
/* 456 */     Rectangle rectangle = this.table.getCellRect(paramDropLocation.getRow(), i, true);
/*     */     
/* 458 */     if (i >= this.table.getColumnCount()) {
/* 459 */       i--;
/* 460 */       rectangle = this.table.getCellRect(paramDropLocation.getRow(), i, true);
/* 461 */       if (bool) {
/* 462 */         rectangle.x += rectangle.width;
/*     */       }
/* 464 */     } else if (!bool) {
/* 465 */       rectangle.x += rectangle.width;
/*     */     } 
/*     */     
/* 468 */     if (rectangle.x == 0) {
/* 469 */       rectangle.x = -1;
/*     */     } else {
/* 471 */       rectangle.x -= 2;
/*     */     } 
/*     */     
/* 474 */     rectangle.width = 3;
/*     */     
/* 476 */     return rectangle;
/*     */   }
/*     */   
/*     */   private Rectangle extendRect(Rectangle paramRectangle, boolean paramBoolean) {
/* 480 */     if (paramRectangle == null) {
/* 481 */       return paramRectangle;
/*     */     }
/*     */     
/* 484 */     if (paramBoolean) {
/* 485 */       paramRectangle.x = 0;
/* 486 */       paramRectangle.width = this.table.getWidth();
/*     */     } else {
/* 488 */       paramRectangle.y = 0;
/*     */       
/* 490 */       if (this.table.getRowCount() != 0) {
/* 491 */         Rectangle rectangle = this.table.getCellRect(this.table.getRowCount() - 1, 0, true);
/* 492 */         paramRectangle.height = rectangle.y + rectangle.height;
/*     */       } else {
/* 494 */         paramRectangle.height = this.table.getHeight();
/*     */       } 
/*     */     } 
/*     */     
/* 498 */     return paramRectangle;
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
/*     */   private void paintGrid(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 510 */     paramGraphics.setColor(this.table.getGridColor());
/*     */     
/* 512 */     Rectangle rectangle1 = this.table.getCellRect(paramInt1, paramInt3, true);
/* 513 */     Rectangle rectangle2 = this.table.getCellRect(paramInt2, paramInt4, true);
/* 514 */     Rectangle rectangle3 = rectangle1.union(rectangle2);
/* 515 */     SynthGraphicsUtils synthGraphicsUtils = paramSynthContext.getStyle().getGraphicsUtils(paramSynthContext);
/*     */ 
/*     */     
/* 518 */     if (this.table.getShowHorizontalLines()) {
/* 519 */       int i = rectangle3.x + rectangle3.width;
/* 520 */       int j = rectangle3.y;
/* 521 */       for (int k = paramInt1; k <= paramInt2; k++) {
/* 522 */         j += this.table.getRowHeight(k);
/* 523 */         synthGraphicsUtils.drawLine(paramSynthContext, "Table.grid", paramGraphics, rectangle3.x, j - 1, i - 1, j - 1);
/*     */       } 
/*     */     } 
/*     */     
/* 527 */     if (this.table.getShowVerticalLines()) {
/* 528 */       TableColumnModel tableColumnModel = this.table.getColumnModel();
/* 529 */       int i = rectangle3.y + rectangle3.height;
/*     */       
/* 531 */       if (this.table.getComponentOrientation().isLeftToRight()) {
/* 532 */         int j = rectangle3.x;
/* 533 */         for (int k = paramInt3; k <= paramInt4; k++) {
/* 534 */           int m = tableColumnModel.getColumn(k).getWidth();
/* 535 */           j += m;
/* 536 */           synthGraphicsUtils.drawLine(paramSynthContext, "Table.grid", paramGraphics, j - 1, 0, j - 1, i - 1);
/*     */         } 
/*     */       } else {
/*     */         
/* 540 */         int j = rectangle3.x;
/* 541 */         for (int k = paramInt4; k >= paramInt3; k--) {
/* 542 */           int m = tableColumnModel.getColumn(k).getWidth();
/* 543 */           j += m;
/* 544 */           synthGraphicsUtils.drawLine(paramSynthContext, "Table.grid", paramGraphics, j - 1, 0, j - 1, i - 1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int viewIndexForColumn(TableColumn paramTableColumn) {
/* 552 */     TableColumnModel tableColumnModel = this.table.getColumnModel();
/* 553 */     for (byte b = 0; b < tableColumnModel.getColumnCount(); b++) {
/* 554 */       if (tableColumnModel.getColumn(b) == paramTableColumn) {
/* 555 */         return b;
/*     */       }
/*     */     } 
/* 558 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintCells(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 563 */     JTableHeader jTableHeader = this.table.getTableHeader();
/* 564 */     TableColumn tableColumn = (jTableHeader == null) ? null : jTableHeader.getDraggedColumn();
/*     */     
/* 566 */     TableColumnModel tableColumnModel = this.table.getColumnModel();
/* 567 */     int i = tableColumnModel.getColumnMargin();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 572 */     if (this.table.getComponentOrientation().isLeftToRight()) {
/* 573 */       for (int j = paramInt1; j <= paramInt2; j++) {
/* 574 */         Rectangle rectangle = this.table.getCellRect(j, paramInt3, false);
/* 575 */         for (int k = paramInt3; k <= paramInt4; k++) {
/* 576 */           TableColumn tableColumn1 = tableColumnModel.getColumn(k);
/* 577 */           int m = tableColumn1.getWidth();
/* 578 */           rectangle.width = m - i;
/* 579 */           if (tableColumn1 != tableColumn) {
/* 580 */             paintCell(paramSynthContext, paramGraphics, rectangle, j, k);
/*     */           }
/* 582 */           rectangle.x += m;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 586 */       for (int j = paramInt1; j <= paramInt2; j++) {
/* 587 */         Rectangle rectangle = this.table.getCellRect(j, paramInt3, false);
/* 588 */         TableColumn tableColumn1 = tableColumnModel.getColumn(paramInt3);
/* 589 */         if (tableColumn1 != tableColumn) {
/* 590 */           int m = tableColumn1.getWidth();
/* 591 */           rectangle.width = m - i;
/* 592 */           paintCell(paramSynthContext, paramGraphics, rectangle, j, paramInt3);
/*     */         } 
/* 594 */         for (int k = paramInt3 + 1; k <= paramInt4; k++) {
/* 595 */           tableColumn1 = tableColumnModel.getColumn(k);
/* 596 */           int m = tableColumn1.getWidth();
/* 597 */           rectangle.width = m - i;
/* 598 */           rectangle.x -= m;
/* 599 */           if (tableColumn1 != tableColumn) {
/* 600 */             paintCell(paramSynthContext, paramGraphics, rectangle, j, k);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 607 */     if (tableColumn != null) {
/* 608 */       paintDraggedArea(paramSynthContext, paramGraphics, paramInt1, paramInt2, tableColumn, jTableHeader.getDraggedDistance());
/*     */     }
/*     */ 
/*     */     
/* 612 */     this.rendererPane.removeAll();
/*     */   }
/*     */   
/*     */   private void paintDraggedArea(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, TableColumn paramTableColumn, int paramInt3) {
/* 616 */     int i = viewIndexForColumn(paramTableColumn);
/*     */     
/* 618 */     Rectangle rectangle1 = this.table.getCellRect(paramInt1, i, true);
/* 619 */     Rectangle rectangle2 = this.table.getCellRect(paramInt2, i, true);
/*     */     
/* 621 */     Rectangle rectangle3 = rectangle1.union(rectangle2);
/*     */ 
/*     */     
/* 624 */     paramGraphics.setColor(this.table.getParent().getBackground());
/* 625 */     paramGraphics.fillRect(rectangle3.x, rectangle3.y, rectangle3.width, rectangle3.height);
/*     */ 
/*     */ 
/*     */     
/* 629 */     rectangle3.x += paramInt3;
/*     */ 
/*     */     
/* 632 */     paramGraphics.setColor(paramSynthContext.getStyle().getColor(paramSynthContext, ColorType.BACKGROUND));
/* 633 */     paramGraphics.fillRect(rectangle3.x, rectangle3.y, rectangle3.width, rectangle3.height);
/*     */ 
/*     */     
/* 636 */     SynthGraphicsUtils synthGraphicsUtils = paramSynthContext.getStyle().getGraphicsUtils(paramSynthContext);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 641 */     if (this.table.getShowVerticalLines()) {
/* 642 */       paramGraphics.setColor(this.table.getGridColor());
/* 643 */       int k = rectangle3.x;
/* 644 */       int m = rectangle3.y;
/* 645 */       int n = k + rectangle3.width - 1;
/* 646 */       int i1 = m + rectangle3.height - 1;
/*     */       
/* 648 */       synthGraphicsUtils.drawLine(paramSynthContext, "Table.grid", paramGraphics, k - 1, m, k - 1, i1);
/*     */       
/* 650 */       synthGraphicsUtils.drawLine(paramSynthContext, "Table.grid", paramGraphics, n, m, n, i1);
/*     */     } 
/*     */     
/* 653 */     for (int j = paramInt1; j <= paramInt2; j++) {
/*     */       
/* 655 */       Rectangle rectangle = this.table.getCellRect(j, i, false);
/* 656 */       rectangle.x += paramInt3;
/* 657 */       paintCell(paramSynthContext, paramGraphics, rectangle, j, i);
/*     */ 
/*     */       
/* 660 */       if (this.table.getShowHorizontalLines()) {
/* 661 */         paramGraphics.setColor(this.table.getGridColor());
/* 662 */         Rectangle rectangle4 = this.table.getCellRect(j, i, true);
/* 663 */         rectangle4.x += paramInt3;
/* 664 */         int k = rectangle4.x;
/* 665 */         int m = rectangle4.y;
/* 666 */         int n = k + rectangle4.width - 1;
/* 667 */         int i1 = m + rectangle4.height - 1;
/* 668 */         synthGraphicsUtils.drawLine(paramSynthContext, "Table.grid", paramGraphics, k, i1, n, i1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintCell(SynthContext paramSynthContext, Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 675 */     if (this.table.isEditing() && this.table.getEditingRow() == paramInt1 && this.table
/* 676 */       .getEditingColumn() == paramInt2) {
/* 677 */       Component component = this.table.getEditorComponent();
/* 678 */       component.setBounds(paramRectangle);
/* 679 */       component.validate();
/*     */     } else {
/*     */       
/* 682 */       TableCellRenderer tableCellRenderer = this.table.getCellRenderer(paramInt1, paramInt2);
/* 683 */       Component component = this.table.prepareRenderer(tableCellRenderer, paramInt1, paramInt2);
/* 684 */       Color color = component.getBackground();
/* 685 */       if ((color == null || color instanceof javax.swing.plaf.UIResource || component instanceof SynthBooleanTableCellRenderer) && 
/*     */         
/* 687 */         !this.table.isCellSelected(paramInt1, paramInt2) && 
/* 688 */         this.alternateColor != null && paramInt1 % 2 != 0) {
/* 689 */         component.setBackground(this.alternateColor);
/*     */       }
/*     */       
/* 692 */       this.rendererPane.paintComponent(paramGraphics, component, this.table, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 702 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent))
/* 703 */       updateStyle((JTable)paramPropertyChangeEvent.getSource()); 
/*     */   }
/*     */   
/*     */   private class SynthBooleanTableCellRenderer
/*     */     extends JCheckBox
/*     */     implements TableCellRenderer
/*     */   {
/*     */     private boolean isRowSelected;
/*     */     
/*     */     public SynthBooleanTableCellRenderer() {
/* 713 */       setHorizontalAlignment(0);
/* 714 */       setName("Table.cellRenderer");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Component getTableCellRendererComponent(JTable param1JTable, Object param1Object, boolean param1Boolean1, boolean param1Boolean2, int param1Int1, int param1Int2) {
/* 720 */       this.isRowSelected = param1Boolean1;
/*     */       
/* 722 */       if (param1Boolean1) {
/* 723 */         setForeground(unwrap(param1JTable.getSelectionForeground()));
/* 724 */         setBackground(unwrap(param1JTable.getSelectionBackground()));
/*     */       } else {
/* 726 */         setForeground(unwrap(param1JTable.getForeground()));
/* 727 */         setBackground(unwrap(param1JTable.getBackground()));
/*     */       } 
/*     */       
/* 730 */       setSelected((param1Object != null && ((Boolean)param1Object).booleanValue()));
/* 731 */       return this;
/*     */     }
/*     */     
/*     */     private Color unwrap(Color param1Color) {
/* 735 */       if (param1Color instanceof javax.swing.plaf.UIResource) {
/* 736 */         return new Color(param1Color.getRGB());
/*     */       }
/* 738 */       return param1Color;
/*     */     }
/*     */     
/*     */     public boolean isOpaque() {
/* 742 */       return this.isRowSelected ? true : super.isOpaque();
/*     */     }
/*     */   }
/*     */   
/*     */   private class SynthTableCellRenderer
/*     */     extends DefaultTableCellRenderer {
/*     */     private Object numberFormat;
/*     */     private Object dateFormat;
/*     */     
/*     */     public void setOpaque(boolean param1Boolean) {
/* 752 */       this.opaque = param1Boolean;
/*     */     } private boolean opaque;
/*     */     private SynthTableCellRenderer() {}
/*     */     public boolean isOpaque() {
/* 756 */       return this.opaque;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 760 */       String str = super.getName();
/* 761 */       if (str == null) {
/* 762 */         return "Table.cellRenderer";
/*     */       }
/* 764 */       return str;
/*     */     }
/*     */     
/*     */     public void setBorder(Border param1Border) {
/* 768 */       if (SynthTableUI.this.useUIBorder || param1Border instanceof SynthBorder) {
/* 769 */         super.setBorder(param1Border);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Component getTableCellRendererComponent(JTable param1JTable, Object param1Object, boolean param1Boolean1, boolean param1Boolean2, int param1Int1, int param1Int2) {
/* 776 */       if (!SynthTableUI.this.useTableColors && (param1Boolean1 || param1Boolean2)) {
/* 777 */         SynthLookAndFeel.setSelectedUI(
/* 778 */             (SynthLabelUI)SynthLookAndFeel.getUIOfType(getUI(), SynthLabelUI.class), param1Boolean1, param1Boolean2, param1JTable
/* 779 */             .isEnabled(), false);
/*     */       } else {
/*     */         
/* 782 */         SynthLookAndFeel.resetSelectedUI();
/*     */       } 
/* 784 */       super.getTableCellRendererComponent(param1JTable, param1Object, param1Boolean1, param1Boolean2, param1Int1, param1Int2);
/*     */ 
/*     */       
/* 787 */       setIcon((Icon)null);
/* 788 */       if (param1JTable != null) {
/* 789 */         configureValue(param1Object, param1JTable.getColumnClass(param1Int2));
/*     */       }
/* 791 */       return this;
/*     */     }
/*     */     
/*     */     private void configureValue(Object param1Object, Class<Object> param1Class) {
/* 795 */       if (param1Class == Object.class || param1Class == null) {
/* 796 */         setHorizontalAlignment(10);
/* 797 */       } else if (param1Class == Float.class || param1Class == Double.class) {
/* 798 */         if (this.numberFormat == null) {
/* 799 */           this.numberFormat = NumberFormat.getInstance();
/*     */         }
/* 801 */         setHorizontalAlignment(11);
/* 802 */         setText((param1Object == null) ? "" : ((NumberFormat)this.numberFormat).format(param1Object));
/*     */       }
/* 804 */       else if (param1Class == Number.class) {
/* 805 */         setHorizontalAlignment(11);
/*     */       
/*     */       }
/* 808 */       else if (param1Class == Icon.class || param1Class == ImageIcon.class) {
/* 809 */         setHorizontalAlignment(0);
/* 810 */         setIcon((param1Object instanceof Icon) ? (Icon)param1Object : null);
/* 811 */         setText("");
/*     */       }
/* 813 */       else if (param1Class == Date.class) {
/* 814 */         if (this.dateFormat == null) {
/* 815 */           this.dateFormat = DateFormat.getDateInstance();
/*     */         }
/* 817 */         setHorizontalAlignment(10);
/* 818 */         setText((param1Object == null) ? "" : ((Format)this.dateFormat).format(param1Object));
/*     */       } else {
/*     */         
/* 821 */         configureValue(param1Object, param1Class.getSuperclass());
/*     */       } 
/*     */     }
/*     */     
/*     */     public void paint(Graphics param1Graphics) {
/* 826 */       super.paint(param1Graphics);
/* 827 */       SynthLookAndFeel.resetSelectedUI();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthTableUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */