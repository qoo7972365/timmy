/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.datatransfer.Transferable;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.KeyListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.util.Enumeration;
/*      */ import java.util.EventObject;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.CellRendererPane;
/*      */ import javax.swing.DefaultListSelectionModel;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.ListSelectionModel;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.Timer;
/*      */ import javax.swing.TransferHandler;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import javax.swing.event.MouseInputListener;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.TableHeaderUI;
/*      */ import javax.swing.plaf.TableUI;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.table.DefaultTableCellRenderer;
/*      */ import javax.swing.table.JTableHeader;
/*      */ import javax.swing.table.TableCellEditor;
/*      */ import javax.swing.table.TableCellRenderer;
/*      */ import javax.swing.table.TableColumn;
/*      */ import javax.swing.table.TableColumnModel;
/*      */ import sun.swing.DefaultLookup;
/*      */ import sun.swing.SwingUtilities2;
/*      */ import sun.swing.UIAction;
/*      */ 
/*      */ public class BasicTableUI
/*      */   extends TableUI
/*      */ {
/*   59 */   private static final StringBuilder BASELINE_COMPONENT_KEY = new StringBuilder("Table.baselineComponent");
/*      */ 
/*      */   
/*      */   protected JTable table;
/*      */ 
/*      */   
/*      */   protected CellRendererPane rendererPane;
/*      */ 
/*      */   
/*      */   protected KeyListener keyListener;
/*      */ 
/*      */   
/*      */   protected FocusListener focusListener;
/*      */   
/*      */   protected MouseInputListener mouseInputListener;
/*      */   
/*      */   private Handler handler;
/*      */   
/*      */   private boolean isFileList = false;
/*      */ 
/*      */   
/*      */   private static class Actions
/*      */     extends UIAction
/*      */   {
/*      */     private static final String CANCEL_EDITING = "cancel";
/*      */     
/*      */     private static final String SELECT_ALL = "selectAll";
/*      */     
/*      */     private static final String CLEAR_SELECTION = "clearSelection";
/*      */     
/*      */     private static final String START_EDITING = "startEditing";
/*      */     
/*      */     private static final String NEXT_ROW = "selectNextRow";
/*      */     
/*      */     private static final String NEXT_ROW_CELL = "selectNextRowCell";
/*      */     
/*      */     private static final String NEXT_ROW_EXTEND_SELECTION = "selectNextRowExtendSelection";
/*      */     
/*      */     private static final String NEXT_ROW_CHANGE_LEAD = "selectNextRowChangeLead";
/*      */     
/*      */     private static final String PREVIOUS_ROW = "selectPreviousRow";
/*      */     
/*      */     private static final String PREVIOUS_ROW_CELL = "selectPreviousRowCell";
/*      */     
/*      */     private static final String PREVIOUS_ROW_EXTEND_SELECTION = "selectPreviousRowExtendSelection";
/*      */     
/*      */     private static final String PREVIOUS_ROW_CHANGE_LEAD = "selectPreviousRowChangeLead";
/*      */     
/*      */     private static final String NEXT_COLUMN = "selectNextColumn";
/*      */     
/*      */     private static final String NEXT_COLUMN_CELL = "selectNextColumnCell";
/*      */     
/*      */     private static final String NEXT_COLUMN_EXTEND_SELECTION = "selectNextColumnExtendSelection";
/*      */     
/*      */     private static final String NEXT_COLUMN_CHANGE_LEAD = "selectNextColumnChangeLead";
/*      */     
/*      */     private static final String PREVIOUS_COLUMN = "selectPreviousColumn";
/*      */     
/*      */     private static final String PREVIOUS_COLUMN_CELL = "selectPreviousColumnCell";
/*      */     
/*      */     private static final String PREVIOUS_COLUMN_EXTEND_SELECTION = "selectPreviousColumnExtendSelection";
/*      */     
/*      */     private static final String PREVIOUS_COLUMN_CHANGE_LEAD = "selectPreviousColumnChangeLead";
/*      */     
/*      */     private static final String SCROLL_LEFT_CHANGE_SELECTION = "scrollLeftChangeSelection";
/*      */     
/*      */     private static final String SCROLL_LEFT_EXTEND_SELECTION = "scrollLeftExtendSelection";
/*      */     
/*      */     private static final String SCROLL_RIGHT_CHANGE_SELECTION = "scrollRightChangeSelection";
/*      */     
/*      */     private static final String SCROLL_RIGHT_EXTEND_SELECTION = "scrollRightExtendSelection";
/*      */     
/*      */     private static final String SCROLL_UP_CHANGE_SELECTION = "scrollUpChangeSelection";
/*      */     
/*      */     private static final String SCROLL_UP_EXTEND_SELECTION = "scrollUpExtendSelection";
/*      */     
/*      */     private static final String SCROLL_DOWN_CHANGE_SELECTION = "scrollDownChangeSelection";
/*      */     
/*      */     private static final String SCROLL_DOWN_EXTEND_SELECTION = "scrollDownExtendSelection";
/*      */     
/*      */     private static final String FIRST_COLUMN = "selectFirstColumn";
/*      */     
/*      */     private static final String FIRST_COLUMN_EXTEND_SELECTION = "selectFirstColumnExtendSelection";
/*      */     
/*      */     private static final String LAST_COLUMN = "selectLastColumn";
/*      */     
/*      */     private static final String LAST_COLUMN_EXTEND_SELECTION = "selectLastColumnExtendSelection";
/*      */     
/*      */     private static final String FIRST_ROW = "selectFirstRow";
/*      */     
/*      */     private static final String FIRST_ROW_EXTEND_SELECTION = "selectFirstRowExtendSelection";
/*      */     
/*      */     private static final String LAST_ROW = "selectLastRow";
/*      */     
/*      */     private static final String LAST_ROW_EXTEND_SELECTION = "selectLastRowExtendSelection";
/*      */     
/*      */     private static final String ADD_TO_SELECTION = "addToSelection";
/*      */     
/*      */     private static final String TOGGLE_AND_ANCHOR = "toggleAndAnchor";
/*      */     
/*      */     private static final String EXTEND_TO = "extendTo";
/*      */     
/*      */     private static final String MOVE_SELECTION_TO = "moveSelectionTo";
/*      */     
/*      */     private static final String FOCUS_HEADER = "focusHeader";
/*      */     
/*      */     protected int dx;
/*      */     
/*      */     protected int dy;
/*      */     
/*      */     protected boolean extend;
/*      */     
/*      */     protected boolean inSelection;
/*      */     
/*      */     protected boolean forwards;
/*      */     
/*      */     protected boolean vertically;
/*      */     
/*      */     protected boolean toLimit;
/*      */     
/*      */     protected int leadRow;
/*      */     
/*      */     protected int leadColumn;
/*      */ 
/*      */     
/*      */     Actions(String param1String) {
/*  185 */       super(param1String);
/*      */     }
/*      */ 
/*      */     
/*      */     Actions(String param1String, int param1Int1, int param1Int2, boolean param1Boolean1, boolean param1Boolean2) {
/*  190 */       super(param1String);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  198 */       if (param1Boolean2) {
/*  199 */         this.inSelection = true;
/*      */ 
/*      */         
/*  202 */         param1Int1 = sign(param1Int1);
/*  203 */         param1Int2 = sign(param1Int2);
/*      */ 
/*      */         
/*  206 */         assert (param1Int1 == 0 || param1Int2 == 0) && (param1Int1 != 0 || param1Int2 != 0);
/*      */       } 
/*      */       
/*  209 */       this.dx = param1Int1;
/*  210 */       this.dy = param1Int2;
/*  211 */       this.extend = param1Boolean1;
/*      */     }
/*      */ 
/*      */     
/*      */     Actions(String param1String, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3, boolean param1Boolean4) {
/*  216 */       this(param1String, 0, 0, param1Boolean1, false);
/*  217 */       this.forwards = param1Boolean2;
/*  218 */       this.vertically = param1Boolean3;
/*  219 */       this.toLimit = param1Boolean4;
/*      */     }
/*      */     
/*      */     private static int clipToRange(int param1Int1, int param1Int2, int param1Int3) {
/*  223 */       return Math.min(Math.max(param1Int1, param1Int2), param1Int3 - 1);
/*      */     }
/*      */     
/*      */     private void moveWithinTableRange(JTable param1JTable, int param1Int1, int param1Int2) {
/*  227 */       this.leadRow = clipToRange(this.leadRow + param1Int2, 0, param1JTable.getRowCount());
/*  228 */       this.leadColumn = clipToRange(this.leadColumn + param1Int1, 0, param1JTable.getColumnCount());
/*      */     }
/*      */     
/*      */     private static int sign(int param1Int) {
/*  232 */       return (param1Int < 0) ? -1 : ((param1Int == 0) ? 0 : 1);
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
/*      */     private boolean moveWithinSelectedRange(JTable param1JTable, int param1Int1, int param1Int2, ListSelectionModel param1ListSelectionModel1, ListSelectionModel param1ListSelectionModel2) {
/*      */       boolean bool1;
/*      */       int i, j, k, m;
/*      */       boolean bool4;
/*  258 */       boolean bool2 = param1JTable.getRowSelectionAllowed();
/*  259 */       boolean bool3 = param1JTable.getColumnSelectionAllowed();
/*      */ 
/*      */       
/*  262 */       if (bool2 && bool3) {
/*  263 */         bool1 = param1JTable.getSelectedRowCount() * param1JTable.getSelectedColumnCount();
/*  264 */         i = param1ListSelectionModel2.getMinSelectionIndex();
/*  265 */         j = param1ListSelectionModel2.getMaxSelectionIndex();
/*  266 */         k = param1ListSelectionModel1.getMinSelectionIndex();
/*  267 */         m = param1ListSelectionModel1.getMaxSelectionIndex();
/*      */       }
/*  269 */       else if (bool2) {
/*  270 */         bool1 = param1JTable.getSelectedRowCount();
/*  271 */         i = 0;
/*  272 */         j = param1JTable.getColumnCount() - 1;
/*  273 */         k = param1ListSelectionModel1.getMinSelectionIndex();
/*  274 */         m = param1ListSelectionModel1.getMaxSelectionIndex();
/*      */       }
/*  276 */       else if (bool3) {
/*  277 */         bool1 = param1JTable.getSelectedColumnCount();
/*  278 */         i = param1ListSelectionModel2.getMinSelectionIndex();
/*  279 */         j = param1ListSelectionModel2.getMaxSelectionIndex();
/*  280 */         k = 0;
/*  281 */         m = param1JTable.getRowCount() - 1;
/*      */       } else {
/*      */         
/*  284 */         bool1 = false;
/*      */ 
/*      */ 
/*      */         
/*  288 */         i = j = k = m = 0;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  297 */       if (!bool1 || (bool1 == true && param1JTable
/*      */         
/*  299 */         .isCellSelected(this.leadRow, this.leadColumn))) {
/*      */         
/*  301 */         bool4 = false;
/*      */         
/*  303 */         j = param1JTable.getColumnCount() - 1;
/*  304 */         m = param1JTable.getRowCount() - 1;
/*      */ 
/*      */         
/*  307 */         i = Math.min(0, j);
/*  308 */         k = Math.min(0, m);
/*      */       } else {
/*  310 */         bool4 = true;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  315 */       if (param1Int2 == 1 && this.leadColumn == -1) {
/*  316 */         this.leadColumn = i;
/*  317 */         this.leadRow = -1;
/*  318 */       } else if (param1Int1 == 1 && this.leadRow == -1) {
/*  319 */         this.leadRow = k;
/*  320 */         this.leadColumn = -1;
/*  321 */       } else if (param1Int2 == -1 && this.leadColumn == -1) {
/*  322 */         this.leadColumn = j;
/*  323 */         this.leadRow = m + 1;
/*  324 */       } else if (param1Int1 == -1 && this.leadRow == -1) {
/*  325 */         this.leadRow = m;
/*  326 */         this.leadColumn = j + 1;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  332 */       this.leadRow = Math.min(Math.max(this.leadRow, k - 1), m + 1);
/*  333 */       this.leadColumn = Math.min(Math.max(this.leadColumn, i - 1), j + 1);
/*      */ 
/*      */       
/*      */       do {
/*  337 */         calcNextPos(param1Int1, i, j, param1Int2, k, m);
/*  338 */       } while (bool4 && !param1JTable.isCellSelected(this.leadRow, this.leadColumn));
/*      */       
/*  340 */       return bool4;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void calcNextPos(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/*  350 */       if (param1Int1 != 0) {
/*  351 */         this.leadColumn += param1Int1;
/*  352 */         if (this.leadColumn > param1Int3) {
/*  353 */           this.leadColumn = param1Int2;
/*  354 */           this.leadRow++;
/*  355 */           if (this.leadRow > param1Int6) {
/*  356 */             this.leadRow = param1Int5;
/*      */           }
/*  358 */         } else if (this.leadColumn < param1Int2) {
/*  359 */           this.leadColumn = param1Int3;
/*  360 */           this.leadRow--;
/*  361 */           if (this.leadRow < param1Int5) {
/*  362 */             this.leadRow = param1Int6;
/*      */           }
/*      */         } 
/*      */       } else {
/*  366 */         this.leadRow += param1Int4;
/*  367 */         if (this.leadRow > param1Int6) {
/*  368 */           this.leadRow = param1Int5;
/*  369 */           this.leadColumn++;
/*  370 */           if (this.leadColumn > param1Int3) {
/*  371 */             this.leadColumn = param1Int2;
/*      */           }
/*  373 */         } else if (this.leadRow < param1Int5) {
/*  374 */           this.leadRow = param1Int6;
/*  375 */           this.leadColumn--;
/*  376 */           if (this.leadColumn < param1Int2) {
/*  377 */             this.leadColumn = param1Int3;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  384 */       String str = getName();
/*  385 */       JTable jTable = (JTable)param1ActionEvent.getSource();
/*      */       
/*  387 */       ListSelectionModel listSelectionModel1 = jTable.getSelectionModel();
/*  388 */       this.leadRow = BasicTableUI.getAdjustedLead(jTable, true, listSelectionModel1);
/*      */       
/*  390 */       ListSelectionModel listSelectionModel2 = jTable.getColumnModel().getSelectionModel();
/*  391 */       this.leadColumn = BasicTableUI.getAdjustedLead(jTable, false, listSelectionModel2);
/*      */       
/*  393 */       if (str == "scrollLeftChangeSelection" || str == "scrollLeftExtendSelection" || str == "scrollRightChangeSelection" || str == "scrollRightExtendSelection" || str == "scrollUpChangeSelection" || str == "scrollUpExtendSelection" || str == "scrollDownChangeSelection" || str == "scrollDownExtendSelection" || str == "selectFirstColumn" || str == "selectFirstColumnExtendSelection" || str == "selectFirstRow" || str == "selectFirstRowExtendSelection" || str == "selectLastColumn" || str == "selectLastColumnExtendSelection" || str == "selectLastRow" || str == "selectLastRowExtendSelection")
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  409 */         if (this.toLimit) {
/*  410 */           if (this.vertically) {
/*  411 */             int i = jTable.getRowCount();
/*  412 */             this.dx = 0;
/*  413 */             this.dy = this.forwards ? i : -i;
/*      */           } else {
/*      */             
/*  416 */             int i = jTable.getColumnCount();
/*  417 */             this.dx = this.forwards ? i : -i;
/*  418 */             this.dy = 0;
/*      */           } 
/*      */         } else {
/*      */           
/*  422 */           if (!(SwingUtilities.getUnwrappedParent(jTable).getParent() instanceof JScrollPane)) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/*  427 */           Dimension dimension = jTable.getParent().getSize();
/*      */           
/*  429 */           if (this.vertically) {
/*  430 */             Rectangle rectangle = jTable.getCellRect(this.leadRow, 0, true);
/*  431 */             if (this.forwards) {
/*      */               
/*  433 */               rectangle.y += Math.max(dimension.height, rectangle.height);
/*      */             } else {
/*  435 */               rectangle.y -= dimension.height;
/*      */             } 
/*      */             
/*  438 */             this.dx = 0;
/*  439 */             int i = jTable.rowAtPoint(rectangle.getLocation());
/*  440 */             if (i == -1 && this.forwards) {
/*  441 */               i = jTable.getRowCount();
/*      */             }
/*  443 */             this.dy = i - this.leadRow;
/*      */           } else {
/*      */             
/*  446 */             Rectangle rectangle = jTable.getCellRect(0, this.leadColumn, true);
/*      */             
/*  448 */             if (this.forwards) {
/*      */               
/*  450 */               rectangle.x += Math.max(dimension.width, rectangle.width);
/*      */             } else {
/*  452 */               rectangle.x -= dimension.width;
/*      */             } 
/*      */             
/*  455 */             int i = jTable.columnAtPoint(rectangle.getLocation());
/*  456 */             if (i == -1) {
/*  457 */               boolean bool = jTable.getComponentOrientation().isLeftToRight();
/*      */ 
/*      */               
/*  460 */               i = this.forwards ? (bool ? jTable.getColumnCount() : 0) : (bool ? 0 : jTable.getColumnCount());
/*      */             } 
/*      */             
/*  463 */             this.dx = i - this.leadColumn;
/*  464 */             this.dy = 0;
/*      */           } 
/*      */         } 
/*      */       }
/*  468 */       if (str == "selectNextRow" || str == "selectNextRowCell" || str == "selectNextRowExtendSelection" || str == "selectNextRowChangeLead" || str == "selectNextColumn" || str == "selectNextColumnCell" || str == "selectNextColumnExtendSelection" || str == "selectNextColumnChangeLead" || str == "selectPreviousRow" || str == "selectPreviousRowCell" || str == "selectPreviousRowExtendSelection" || str == "selectPreviousRowChangeLead" || str == "selectPreviousColumn" || str == "selectPreviousColumnCell" || str == "selectPreviousColumnExtendSelection" || str == "selectPreviousColumnChangeLead" || str == "scrollLeftChangeSelection" || str == "scrollLeftExtendSelection" || str == "scrollRightChangeSelection" || str == "scrollRightExtendSelection" || str == "scrollUpChangeSelection" || str == "scrollUpExtendSelection" || str == "scrollDownChangeSelection" || str == "scrollDownExtendSelection" || str == "selectFirstColumn" || str == "selectFirstColumnExtendSelection" || str == "selectFirstRow" || str == "selectFirstRowExtendSelection" || str == "selectLastColumn" || str == "selectLastColumnExtendSelection" || str == "selectLastRow" || str == "selectLastRowExtendSelection") {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  502 */         if (jTable.isEditing() && 
/*  503 */           !jTable.getCellEditor().stopCellEditing()) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  522 */         boolean bool = false;
/*  523 */         if (str == "selectNextRowChangeLead" || str == "selectPreviousRowChangeLead") {
/*  524 */           bool = (listSelectionModel1.getSelectionMode() == 2) ? true : false;
/*      */         }
/*  526 */         else if (str == "selectNextColumnChangeLead" || str == "selectPreviousColumnChangeLead") {
/*  527 */           bool = (listSelectionModel2.getSelectionMode() == 2) ? true : false;
/*      */         } 
/*      */ 
/*      */         
/*  531 */         if (bool) {
/*  532 */           moveWithinTableRange(jTable, this.dx, this.dy);
/*  533 */           if (this.dy != 0) {
/*      */ 
/*      */             
/*  536 */             ((DefaultListSelectionModel)listSelectionModel1).moveLeadSelectionIndex(this.leadRow);
/*  537 */             if (BasicTableUI.getAdjustedLead(jTable, false, listSelectionModel2) == -1 && jTable
/*  538 */               .getColumnCount() > 0)
/*      */             {
/*  540 */               ((DefaultListSelectionModel)listSelectionModel2).moveLeadSelectionIndex(0);
/*      */             }
/*      */           }
/*      */           else {
/*      */             
/*  545 */             ((DefaultListSelectionModel)listSelectionModel2).moveLeadSelectionIndex(this.leadColumn);
/*  546 */             if (BasicTableUI.getAdjustedLead(jTable, true, listSelectionModel1) == -1 && jTable
/*  547 */               .getRowCount() > 0)
/*      */             {
/*  549 */               ((DefaultListSelectionModel)listSelectionModel1).moveLeadSelectionIndex(0);
/*      */             }
/*      */           } 
/*      */           
/*  553 */           Rectangle rectangle = jTable.getCellRect(this.leadRow, this.leadColumn, false);
/*  554 */           if (rectangle != null) {
/*  555 */             jTable.scrollRectToVisible(rectangle);
/*      */           }
/*  557 */         } else if (!this.inSelection) {
/*  558 */           moveWithinTableRange(jTable, this.dx, this.dy);
/*  559 */           jTable.changeSelection(this.leadRow, this.leadColumn, false, this.extend);
/*      */         } else {
/*      */           
/*  562 */           if (jTable.getRowCount() <= 0 || jTable.getColumnCount() <= 0) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/*  567 */           if (moveWithinSelectedRange(jTable, this.dx, this.dy, listSelectionModel1, listSelectionModel2)) {
/*      */ 
/*      */             
/*  570 */             if (listSelectionModel1.isSelectedIndex(this.leadRow)) {
/*  571 */               listSelectionModel1.addSelectionInterval(this.leadRow, this.leadRow);
/*      */             } else {
/*  573 */               listSelectionModel1.removeSelectionInterval(this.leadRow, this.leadRow);
/*      */             } 
/*      */             
/*  576 */             if (listSelectionModel2.isSelectedIndex(this.leadColumn)) {
/*  577 */               listSelectionModel2.addSelectionInterval(this.leadColumn, this.leadColumn);
/*      */             } else {
/*  579 */               listSelectionModel2.removeSelectionInterval(this.leadColumn, this.leadColumn);
/*      */             } 
/*      */             
/*  582 */             Rectangle rectangle = jTable.getCellRect(this.leadRow, this.leadColumn, false);
/*  583 */             if (rectangle != null) {
/*  584 */               jTable.scrollRectToVisible(rectangle);
/*      */             }
/*      */           } else {
/*      */             
/*  588 */             jTable.changeSelection(this.leadRow, this.leadColumn, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  606 */       else if (str == "cancel") {
/*  607 */         jTable.removeEditor();
/*  608 */       } else if (str == "selectAll") {
/*  609 */         jTable.selectAll();
/*  610 */       } else if (str == "clearSelection") {
/*  611 */         jTable.clearSelection();
/*  612 */       } else if (str == "startEditing") {
/*  613 */         if (!jTable.hasFocus()) {
/*  614 */           TableCellEditor tableCellEditor = jTable.getCellEditor();
/*  615 */           if (tableCellEditor != null && !tableCellEditor.stopCellEditing()) {
/*      */             return;
/*      */           }
/*  618 */           jTable.requestFocus();
/*      */           return;
/*      */         } 
/*  621 */         jTable.editCellAt(this.leadRow, this.leadColumn, param1ActionEvent);
/*  622 */         Component component = jTable.getEditorComponent();
/*  623 */         if (component != null) {
/*  624 */           component.requestFocus();
/*      */         }
/*  626 */       } else if (str == "addToSelection") {
/*  627 */         if (!jTable.isCellSelected(this.leadRow, this.leadColumn)) {
/*  628 */           int i = listSelectionModel1.getAnchorSelectionIndex();
/*  629 */           int j = listSelectionModel2.getAnchorSelectionIndex();
/*  630 */           listSelectionModel1.setValueIsAdjusting(true);
/*  631 */           listSelectionModel2.setValueIsAdjusting(true);
/*  632 */           jTable.changeSelection(this.leadRow, this.leadColumn, true, false);
/*  633 */           listSelectionModel1.setAnchorSelectionIndex(i);
/*  634 */           listSelectionModel2.setAnchorSelectionIndex(j);
/*  635 */           listSelectionModel1.setValueIsAdjusting(false);
/*  636 */           listSelectionModel2.setValueIsAdjusting(false);
/*      */         } 
/*  638 */       } else if (str == "toggleAndAnchor") {
/*  639 */         jTable.changeSelection(this.leadRow, this.leadColumn, true, false);
/*  640 */       } else if (str == "extendTo") {
/*  641 */         jTable.changeSelection(this.leadRow, this.leadColumn, false, true);
/*  642 */       } else if (str == "moveSelectionTo") {
/*  643 */         jTable.changeSelection(this.leadRow, this.leadColumn, false, false);
/*  644 */       } else if (str == "focusHeader") {
/*  645 */         JTableHeader jTableHeader = jTable.getTableHeader();
/*  646 */         if (jTableHeader != null) {
/*      */           
/*  648 */           int i = jTable.getSelectedColumn();
/*  649 */           if (i >= 0) {
/*  650 */             TableHeaderUI tableHeaderUI = jTableHeader.getUI();
/*  651 */             if (tableHeaderUI instanceof BasicTableHeaderUI) {
/*  652 */               ((BasicTableHeaderUI)tableHeaderUI).selectColumn(i);
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/*  657 */           jTableHeader.requestFocusInWindow();
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean isEnabled(Object param1Object) {
/*  663 */       String str = getName();
/*      */       
/*  665 */       if (param1Object instanceof JTable && Boolean.TRUE
/*  666 */         .equals(((JTable)param1Object).getClientProperty("Table.isFileList")) && (
/*  667 */         str == "selectNextColumn" || str == "selectNextColumnCell" || str == "selectNextColumnExtendSelection" || str == "selectNextColumnChangeLead" || str == "selectPreviousColumn" || str == "selectPreviousColumnCell" || str == "selectPreviousColumnExtendSelection" || str == "selectPreviousColumnChangeLead" || str == "scrollLeftChangeSelection" || str == "scrollLeftExtendSelection" || str == "scrollRightChangeSelection" || str == "scrollRightExtendSelection" || str == "selectFirstColumn" || str == "selectFirstColumnExtendSelection" || str == "selectLastColumn" || str == "selectLastColumnExtendSelection" || str == "selectNextRowCell" || str == "selectPreviousRowCell"))
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  686 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  690 */       if (str == "cancel" && param1Object instanceof JTable)
/*  691 */         return ((JTable)param1Object).isEditing(); 
/*  692 */       if (str == "selectNextRowChangeLead" || str == "selectPreviousRowChangeLead")
/*      */       {
/*      */ 
/*      */         
/*  696 */         return (param1Object != null && ((JTable)param1Object)
/*  697 */           .getSelectionModel() instanceof DefaultListSelectionModel);
/*      */       }
/*  699 */       if (str == "selectNextColumnChangeLead" || str == "selectPreviousColumnChangeLead")
/*      */       {
/*      */ 
/*      */         
/*  703 */         return (param1Object != null && ((JTable)param1Object)
/*  704 */           .getColumnModel().getSelectionModel() instanceof DefaultListSelectionModel);
/*      */       }
/*  706 */       if (str == "addToSelection" && param1Object instanceof JTable) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  714 */         JTable jTable = (JTable)param1Object;
/*  715 */         int i = BasicTableUI.getAdjustedLead(jTable, true);
/*  716 */         int j = BasicTableUI.getAdjustedLead(jTable, false);
/*  717 */         return (!jTable.isEditing() && !jTable.isCellSelected(i, j));
/*  718 */       }  if (str == "focusHeader" && param1Object instanceof JTable) {
/*  719 */         JTable jTable = (JTable)param1Object;
/*  720 */         return (jTable.getTableHeader() != null);
/*      */       } 
/*      */       
/*  723 */       return true;
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
/*      */   public class KeyHandler
/*      */     implements KeyListener
/*      */   {
/*      */     public void keyPressed(KeyEvent param1KeyEvent) {
/*  746 */       BasicTableUI.this.getHandler().keyPressed(param1KeyEvent);
/*      */     }
/*      */     
/*      */     public void keyReleased(KeyEvent param1KeyEvent) {
/*  750 */       BasicTableUI.this.getHandler().keyReleased(param1KeyEvent);
/*      */     }
/*      */     
/*      */     public void keyTyped(KeyEvent param1KeyEvent) {
/*  754 */       BasicTableUI.this.getHandler().keyTyped(param1KeyEvent);
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
/*      */   public class FocusHandler
/*      */     implements FocusListener
/*      */   {
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/*  772 */       BasicTableUI.this.getHandler().focusGained(param1FocusEvent);
/*      */     }
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/*  776 */       BasicTableUI.this.getHandler().focusLost(param1FocusEvent);
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
/*      */   public class MouseInputHandler
/*      */     implements MouseInputListener
/*      */   {
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {
/*  794 */       BasicTableUI.this.getHandler().mouseClicked(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/*  798 */       BasicTableUI.this.getHandler().mousePressed(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/*  802 */       BasicTableUI.this.getHandler().mouseReleased(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {
/*  806 */       BasicTableUI.this.getHandler().mouseEntered(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {
/*  810 */       BasicTableUI.this.getHandler().mouseExited(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {
/*  814 */       BasicTableUI.this.getHandler().mouseMoved(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/*  818 */       BasicTableUI.this.getHandler().mouseDragged(param1MouseEvent);
/*      */     } }
/*      */   
/*      */   private class Handler implements FocusListener, MouseInputListener, PropertyChangeListener, ListSelectionListener, ActionListener, DragRecognitionSupport.BeforeDrag {
/*      */     private Component dispatchComponent;
/*      */     private int pressedRow;
/*      */     private int pressedCol;
/*      */     private MouseEvent pressedEvent;
/*      */     
/*      */     private void repaintLeadCell() {
/*  828 */       int i = BasicTableUI.getAdjustedLead(BasicTableUI.this.table, true);
/*  829 */       int j = BasicTableUI.getAdjustedLead(BasicTableUI.this.table, false);
/*      */       
/*  831 */       if (i < 0 || j < 0) {
/*      */         return;
/*      */       }
/*      */       
/*  835 */       Rectangle rectangle = BasicTableUI.this.table.getCellRect(i, j, false);
/*  836 */       BasicTableUI.this.table.repaint(rectangle);
/*      */     }
/*      */     private boolean dragPressDidSelection; private boolean dragStarted;
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/*  840 */       repaintLeadCell();
/*      */     }
/*      */     private boolean shouldStartTimer; private boolean outsidePrefSize;
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/*  844 */       repaintLeadCell();
/*      */     }
/*      */ 
/*      */     
/*      */     public void keyPressed(KeyEvent param1KeyEvent) {}
/*      */ 
/*      */     
/*      */     public void keyReleased(KeyEvent param1KeyEvent) {}
/*      */     
/*      */     public void keyTyped(KeyEvent param1KeyEvent) {
/*  854 */       KeyStroke keyStroke = KeyStroke.getKeyStroke(param1KeyEvent.getKeyChar(), param1KeyEvent
/*  855 */           .getModifiers());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  862 */       InputMap inputMap = BasicTableUI.this.table.getInputMap(0);
/*  863 */       if (inputMap != null && inputMap.get(keyStroke) != null) {
/*      */         return;
/*      */       }
/*  866 */       inputMap = BasicTableUI.this.table.getInputMap(1);
/*      */       
/*  868 */       if (inputMap != null && inputMap.get(keyStroke) != null) {
/*      */         return;
/*      */       }
/*      */       
/*  872 */       keyStroke = KeyStroke.getKeyStrokeForEvent(param1KeyEvent);
/*      */ 
/*      */ 
/*      */       
/*  876 */       if (param1KeyEvent.getKeyChar() == '\r') {
/*      */         return;
/*      */       }
/*      */       
/*  880 */       int i = BasicTableUI.getAdjustedLead(BasicTableUI.this.table, true);
/*  881 */       int j = BasicTableUI.getAdjustedLead(BasicTableUI.this.table, false);
/*  882 */       if (i != -1 && j != -1 && !BasicTableUI.this.table.isEditing() && 
/*  883 */         !BasicTableUI.this.table.editCellAt(i, j)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  896 */       Component component = BasicTableUI.this.table.getEditorComponent();
/*  897 */       if (BasicTableUI.this.table.isEditing() && component != null && 
/*  898 */         component instanceof JComponent) {
/*  899 */         JComponent jComponent = (JComponent)component;
/*  900 */         inputMap = jComponent.getInputMap(0);
/*  901 */         Object object = (inputMap != null) ? inputMap.get(keyStroke) : null;
/*  902 */         if (object == null) {
/*  903 */           inputMap = jComponent.getInputMap(1);
/*      */           
/*  905 */           object = (inputMap != null) ? inputMap.get(keyStroke) : null;
/*      */         } 
/*  907 */         if (object != null) {
/*  908 */           ActionMap actionMap = jComponent.getActionMap();
/*  909 */           Action action = (actionMap != null) ? actionMap.get(object) : null;
/*  910 */           if (action != null && 
/*  911 */             SwingUtilities.notifyAction(action, keyStroke, param1KeyEvent, jComponent, param1KeyEvent
/*  912 */               .getModifiers())) {
/*  913 */             param1KeyEvent.consume();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setDispatchComponent(MouseEvent param1MouseEvent) {
/*  930 */       Component component = BasicTableUI.this.table.getEditorComponent();
/*  931 */       Point point1 = param1MouseEvent.getPoint();
/*  932 */       Point point2 = SwingUtilities.convertPoint(BasicTableUI.this.table, point1, component);
/*  933 */       this
/*  934 */         .dispatchComponent = SwingUtilities.getDeepestComponentAt(component, point2.x, point2.y);
/*      */       
/*  936 */       SwingUtilities2.setSkipClickCount(this.dispatchComponent, param1MouseEvent
/*  937 */           .getClickCount() - 1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean repostEvent(MouseEvent param1MouseEvent) {
/*  943 */       if (this.dispatchComponent == null || !BasicTableUI.this.table.isEditing()) {
/*  944 */         return false;
/*      */       }
/*  946 */       MouseEvent mouseEvent = SwingUtilities.convertMouseEvent(BasicTableUI.this.table, param1MouseEvent, this.dispatchComponent);
/*      */       
/*  948 */       this.dispatchComponent.dispatchEvent(mouseEvent);
/*  949 */       return true;
/*      */     }
/*      */     
/*      */     private void setValueIsAdjusting(boolean param1Boolean) {
/*  953 */       BasicTableUI.this.table.getSelectionModel().setValueIsAdjusting(param1Boolean);
/*  954 */       BasicTableUI.this.table.getColumnModel().getSelectionModel()
/*  955 */         .setValueIsAdjusting(param1Boolean);
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
/*      */ 
/*      */     
/*  982 */     private Timer timer = null;
/*      */     
/*      */     private boolean canStartDrag() {
/*  985 */       if (this.pressedRow == -1 || this.pressedCol == -1) {
/*  986 */         return false;
/*      */       }
/*      */       
/*  989 */       if (BasicTableUI.this.isFileList) {
/*  990 */         return !this.outsidePrefSize;
/*      */       }
/*      */ 
/*      */       
/*  994 */       if (BasicTableUI.this.table.getSelectionModel().getSelectionMode() == 0 && BasicTableUI.this.table
/*      */         
/*  996 */         .getColumnModel().getSelectionModel().getSelectionMode() == 0)
/*      */       {
/*      */         
/*  999 */         return true;
/*      */       }
/*      */       
/* 1002 */       return BasicTableUI.this.table.isCellSelected(this.pressedRow, this.pressedCol);
/*      */     }
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 1006 */       if (SwingUtilities2.shouldIgnore(param1MouseEvent, BasicTableUI.this.table)) {
/*      */         return;
/*      */       }
/*      */       
/* 1010 */       if (BasicTableUI.this.table.isEditing() && !BasicTableUI.this.table.getCellEditor().stopCellEditing()) {
/* 1011 */         Component component = BasicTableUI.this.table.getEditorComponent();
/* 1012 */         if (component != null && !component.hasFocus()) {
/* 1013 */           SwingUtilities2.compositeRequestFocus(component);
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/* 1018 */       Point point = param1MouseEvent.getPoint();
/* 1019 */       this.pressedRow = BasicTableUI.this.table.rowAtPoint(point);
/* 1020 */       this.pressedCol = BasicTableUI.this.table.columnAtPoint(point);
/* 1021 */       this.outsidePrefSize = BasicTableUI.this.pointOutsidePrefSize(this.pressedRow, this.pressedCol, point);
/*      */       
/* 1023 */       if (BasicTableUI.this.isFileList) {
/* 1024 */         this
/*      */ 
/*      */           
/* 1027 */           .shouldStartTimer = (BasicTableUI.this.table.isCellSelected(this.pressedRow, this.pressedCol) && !param1MouseEvent.isShiftDown() && !BasicGraphicsUtils.isMenuShortcutKeyDown(param1MouseEvent) && !this.outsidePrefSize);
/*      */       }
/*      */ 
/*      */       
/* 1031 */       if (BasicTableUI.this.table.getDragEnabled()) {
/* 1032 */         mousePressedDND(param1MouseEvent);
/*      */       } else {
/* 1034 */         SwingUtilities2.adjustFocus(BasicTableUI.this.table);
/* 1035 */         if (!BasicTableUI.this.isFileList) {
/* 1036 */           setValueIsAdjusting(true);
/*      */         }
/* 1038 */         adjustSelection(param1MouseEvent);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void mousePressedDND(MouseEvent param1MouseEvent) {
/* 1043 */       this.pressedEvent = param1MouseEvent;
/* 1044 */       boolean bool = true;
/* 1045 */       this.dragStarted = false;
/*      */       
/* 1047 */       if (canStartDrag() && DragRecognitionSupport.mousePressed(param1MouseEvent)) {
/*      */         
/* 1049 */         this.dragPressDidSelection = false;
/*      */         
/* 1051 */         if (BasicGraphicsUtils.isMenuShortcutKeyDown(param1MouseEvent) && BasicTableUI.this.isFileList) {
/*      */           return;
/*      */         }
/*      */         
/* 1055 */         if (!param1MouseEvent.isShiftDown() && BasicTableUI.this.table.isCellSelected(this.pressedRow, this.pressedCol)) {
/*      */ 
/*      */           
/* 1058 */           BasicTableUI.this.table.getSelectionModel().addSelectionInterval(this.pressedRow, this.pressedRow);
/*      */           
/* 1060 */           BasicTableUI.this.table.getColumnModel().getSelectionModel()
/* 1061 */             .addSelectionInterval(this.pressedCol, this.pressedCol);
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/* 1066 */         this.dragPressDidSelection = true;
/*      */ 
/*      */         
/* 1069 */         bool = false;
/* 1070 */       } else if (!BasicTableUI.this.isFileList) {
/*      */ 
/*      */         
/* 1073 */         setValueIsAdjusting(true);
/*      */       } 
/*      */       
/* 1076 */       if (bool) {
/* 1077 */         SwingUtilities2.adjustFocus(BasicTableUI.this.table);
/*      */       }
/*      */       
/* 1080 */       adjustSelection(param1MouseEvent);
/*      */     }
/*      */ 
/*      */     
/*      */     private void adjustSelection(MouseEvent param1MouseEvent) {
/* 1085 */       if (this.outsidePrefSize) {
/*      */ 
/*      */         
/* 1088 */         if (param1MouseEvent.getID() == 501 && (
/* 1089 */           !param1MouseEvent.isShiftDown() || BasicTableUI.this.table
/* 1090 */           .getSelectionModel().getSelectionMode() == 0)) {
/*      */           
/* 1092 */           BasicTableUI.this.table.clearSelection();
/* 1093 */           TableCellEditor tableCellEditor1 = BasicTableUI.this.table.getCellEditor();
/* 1094 */           if (tableCellEditor1 != null) {
/* 1095 */             tableCellEditor1.stopCellEditing();
/*      */           }
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1102 */       if (this.pressedCol == -1 || this.pressedRow == -1) {
/*      */         return;
/*      */       }
/*      */       
/* 1106 */       boolean bool = BasicTableUI.this.table.getDragEnabled();
/*      */       
/* 1108 */       if (!bool && !BasicTableUI.this.isFileList && BasicTableUI.this.table.editCellAt(this.pressedRow, this.pressedCol, param1MouseEvent)) {
/* 1109 */         setDispatchComponent(param1MouseEvent);
/* 1110 */         repostEvent(param1MouseEvent);
/*      */       } 
/*      */       
/* 1113 */       TableCellEditor tableCellEditor = BasicTableUI.this.table.getCellEditor();
/* 1114 */       if (bool || tableCellEditor == null || tableCellEditor.shouldSelectCell(param1MouseEvent)) {
/* 1115 */         BasicTableUI.this.table.changeSelection(this.pressedRow, this.pressedCol, 
/* 1116 */             BasicGraphicsUtils.isMenuShortcutKeyDown(param1MouseEvent), param1MouseEvent
/* 1117 */             .isShiftDown());
/*      */       }
/*      */     }
/*      */     
/*      */     public void valueChanged(ListSelectionEvent param1ListSelectionEvent) {
/* 1122 */       if (this.timer != null) {
/* 1123 */         this.timer.stop();
/* 1124 */         this.timer = null;
/*      */       } 
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1129 */       BasicTableUI.this.table.editCellAt(this.pressedRow, this.pressedCol, (EventObject)null);
/* 1130 */       Component component = BasicTableUI.this.table.getEditorComponent();
/* 1131 */       if (component != null && !component.hasFocus()) {
/* 1132 */         SwingUtilities2.compositeRequestFocus(component);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private void maybeStartTimer() {
/* 1138 */       if (!this.shouldStartTimer) {
/*      */         return;
/*      */       }
/*      */       
/* 1142 */       if (this.timer == null) {
/* 1143 */         this.timer = new Timer(1200, this);
/* 1144 */         this.timer.setRepeats(false);
/*      */       } 
/*      */       
/* 1147 */       this.timer.start();
/*      */     }
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 1151 */       if (SwingUtilities2.shouldIgnore(param1MouseEvent, BasicTableUI.this.table)) {
/*      */         return;
/*      */       }
/*      */       
/* 1155 */       if (BasicTableUI.this.table.getDragEnabled()) {
/* 1156 */         mouseReleasedDND(param1MouseEvent);
/*      */       }
/* 1158 */       else if (BasicTableUI.this.isFileList) {
/* 1159 */         maybeStartTimer();
/*      */       } 
/*      */ 
/*      */       
/* 1163 */       this.pressedEvent = null;
/* 1164 */       repostEvent(param1MouseEvent);
/* 1165 */       this.dispatchComponent = null;
/* 1166 */       setValueIsAdjusting(false);
/*      */     }
/*      */     
/*      */     private void mouseReleasedDND(MouseEvent param1MouseEvent) {
/* 1170 */       MouseEvent mouseEvent = DragRecognitionSupport.mouseReleased(param1MouseEvent);
/* 1171 */       if (mouseEvent != null) {
/* 1172 */         SwingUtilities2.adjustFocus(BasicTableUI.this.table);
/* 1173 */         if (!this.dragPressDidSelection) {
/* 1174 */           adjustSelection(mouseEvent);
/*      */         }
/*      */       } 
/*      */       
/* 1178 */       if (!this.dragStarted) {
/* 1179 */         if (BasicTableUI.this.isFileList) {
/* 1180 */           maybeStartTimer();
/*      */           
/*      */           return;
/*      */         } 
/* 1184 */         Point point = param1MouseEvent.getPoint();
/*      */         
/* 1186 */         if (this.pressedEvent != null && BasicTableUI.this.table
/* 1187 */           .rowAtPoint(point) == this.pressedRow && BasicTableUI.this.table
/* 1188 */           .columnAtPoint(point) == this.pressedCol && BasicTableUI.this.table
/* 1189 */           .editCellAt(this.pressedRow, this.pressedCol, this.pressedEvent)) {
/*      */           
/* 1191 */           setDispatchComponent(this.pressedEvent);
/* 1192 */           repostEvent(this.pressedEvent);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1197 */           TableCellEditor tableCellEditor = BasicTableUI.this.table.getCellEditor();
/* 1198 */           if (tableCellEditor != null) {
/* 1199 */             tableCellEditor.shouldSelectCell(this.pressedEvent);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {}
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {}
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {}
/*      */     
/*      */     public void dragStarting(MouseEvent param1MouseEvent) {
/* 1212 */       this.dragStarted = true;
/*      */       
/* 1214 */       if (BasicGraphicsUtils.isMenuShortcutKeyDown(param1MouseEvent) && BasicTableUI.this.isFileList) {
/* 1215 */         BasicTableUI.this.table.getSelectionModel().addSelectionInterval(this.pressedRow, this.pressedRow);
/*      */         
/* 1217 */         BasicTableUI.this.table.getColumnModel().getSelectionModel()
/* 1218 */           .addSelectionInterval(this.pressedCol, this.pressedCol);
/*      */       } 
/*      */       
/* 1221 */       this.pressedEvent = null;
/*      */     }
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 1225 */       if (SwingUtilities2.shouldIgnore(param1MouseEvent, BasicTableUI.this.table)) {
/*      */         return;
/*      */       }
/*      */       
/* 1229 */       if (BasicTableUI.this.table.getDragEnabled() && (
/* 1230 */         DragRecognitionSupport.mouseDragged(param1MouseEvent, this) || this.dragStarted)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1235 */       repostEvent(param1MouseEvent);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1240 */       if (BasicTableUI.this.isFileList || BasicTableUI.this.table.isEditing()) {
/*      */         return;
/*      */       }
/*      */       
/* 1244 */       Point point = param1MouseEvent.getPoint();
/* 1245 */       int i = BasicTableUI.this.table.rowAtPoint(point);
/* 1246 */       int j = BasicTableUI.this.table.columnAtPoint(point);
/*      */ 
/*      */       
/* 1249 */       if (j == -1 || i == -1) {
/*      */         return;
/*      */       }
/*      */       
/* 1253 */       BasicTableUI.this.table.changeSelection(i, j, 
/* 1254 */           BasicGraphicsUtils.isMenuShortcutKeyDown(param1MouseEvent), true);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1260 */       String str = param1PropertyChangeEvent.getPropertyName();
/*      */       
/* 1262 */       if ("componentOrientation" == str) {
/* 1263 */         InputMap inputMap = BasicTableUI.this.getInputMap(1);
/*      */ 
/*      */         
/* 1266 */         SwingUtilities.replaceUIInputMap(BasicTableUI.this.table, 1, inputMap);
/*      */ 
/*      */ 
/*      */         
/* 1270 */         JTableHeader jTableHeader = BasicTableUI.this.table.getTableHeader();
/* 1271 */         if (jTableHeader != null) {
/* 1272 */           jTableHeader.setComponentOrientation((ComponentOrientation)param1PropertyChangeEvent
/* 1273 */               .getNewValue());
/*      */         }
/* 1275 */       } else if ("dropLocation" == str) {
/* 1276 */         JTable.DropLocation dropLocation = (JTable.DropLocation)param1PropertyChangeEvent.getOldValue();
/* 1277 */         repaintDropLocation(dropLocation);
/* 1278 */         repaintDropLocation(BasicTableUI.this.table.getDropLocation());
/* 1279 */       } else if ("Table.isFileList" == str) {
/* 1280 */         BasicTableUI.this.isFileList = Boolean.TRUE.equals(BasicTableUI.this.table.getClientProperty("Table.isFileList"));
/* 1281 */         BasicTableUI.this.table.revalidate();
/* 1282 */         BasicTableUI.this.table.repaint();
/* 1283 */         if (BasicTableUI.this.isFileList) {
/* 1284 */           BasicTableUI.this.table.getSelectionModel().addListSelectionListener(BasicTableUI.this.getHandler());
/*      */         } else {
/* 1286 */           BasicTableUI.this.table.getSelectionModel().removeListSelectionListener(BasicTableUI.this.getHandler());
/* 1287 */           this.timer = null;
/*      */         } 
/* 1289 */       } else if ("selectionModel" == str && 
/* 1290 */         BasicTableUI.this.isFileList) {
/* 1291 */         ListSelectionModel listSelectionModel = (ListSelectionModel)param1PropertyChangeEvent.getOldValue();
/* 1292 */         listSelectionModel.removeListSelectionListener(BasicTableUI.this.getHandler());
/* 1293 */         BasicTableUI.this.table.getSelectionModel().addListSelectionListener(BasicTableUI.this.getHandler());
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void repaintDropLocation(JTable.DropLocation param1DropLocation) {
/* 1299 */       if (param1DropLocation == null) {
/*      */         return;
/*      */       }
/*      */       
/* 1303 */       if (!param1DropLocation.isInsertRow() && !param1DropLocation.isInsertColumn()) {
/* 1304 */         Rectangle rectangle = BasicTableUI.this.table.getCellRect(param1DropLocation.getRow(), param1DropLocation.getColumn(), false);
/* 1305 */         if (rectangle != null) {
/* 1306 */           BasicTableUI.this.table.repaint(rectangle);
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/* 1311 */       if (param1DropLocation.isInsertRow()) {
/* 1312 */         Rectangle rectangle = BasicTableUI.this.extendRect(BasicTableUI.this.getHDropLineRect(param1DropLocation), true);
/* 1313 */         if (rectangle != null) {
/* 1314 */           BasicTableUI.this.table.repaint(rectangle);
/*      */         }
/*      */       } 
/*      */       
/* 1318 */       if (param1DropLocation.isInsertColumn()) {
/* 1319 */         Rectangle rectangle = BasicTableUI.this.extendRect(BasicTableUI.this.getVDropLineRect(param1DropLocation), false);
/* 1320 */         if (rectangle != null) {
/* 1321 */           BasicTableUI.this.table.repaint(rectangle);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private Handler() {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean pointOutsidePrefSize(int paramInt1, int paramInt2, Point paramPoint) {
/* 1334 */     if (!this.isFileList) {
/* 1335 */       return false;
/*      */     }
/*      */     
/* 1338 */     return SwingUtilities2.pointOutsidePrefSize(this.table, paramInt1, paramInt2, paramPoint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Handler getHandler() {
/* 1346 */     if (this.handler == null) {
/* 1347 */       this.handler = new Handler();
/*      */     }
/* 1349 */     return this.handler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected KeyListener createKeyListener() {
/* 1356 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected FocusListener createFocusListener() {
/* 1363 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MouseInputListener createMouseInputListener() {
/* 1370 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 1378 */     return new BasicTableUI();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/* 1384 */     this.table = (JTable)paramJComponent;
/*      */     
/* 1386 */     this.rendererPane = new CellRendererPane();
/* 1387 */     this.table.add(this.rendererPane);
/* 1388 */     installDefaults();
/* 1389 */     installDefaults2();
/* 1390 */     installListeners();
/* 1391 */     installKeyboardActions();
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
/*      */   protected void installDefaults() {
/* 1403 */     LookAndFeel.installColorsAndFont(this.table, "Table.background", "Table.foreground", "Table.font");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1413 */     LookAndFeel.installProperty(this.table, "opaque", Boolean.TRUE);
/*      */     
/* 1415 */     Color color1 = this.table.getSelectionBackground();
/* 1416 */     if (color1 == null || color1 instanceof UIResource) {
/* 1417 */       color1 = UIManager.getColor("Table.selectionBackground");
/* 1418 */       this.table.setSelectionBackground((color1 != null) ? color1 : UIManager.getColor("textHighlight"));
/*      */     } 
/*      */     
/* 1421 */     Color color2 = this.table.getSelectionForeground();
/* 1422 */     if (color2 == null || color2 instanceof UIResource) {
/* 1423 */       color2 = UIManager.getColor("Table.selectionForeground");
/* 1424 */       this.table.setSelectionForeground((color2 != null) ? color2 : UIManager.getColor("textHighlightText"));
/*      */     } 
/*      */     
/* 1427 */     Color color3 = this.table.getGridColor();
/* 1428 */     if (color3 == null || color3 instanceof UIResource) {
/* 1429 */       color3 = UIManager.getColor("Table.gridColor");
/* 1430 */       this.table.setGridColor((color3 != null) ? color3 : Color.GRAY);
/*      */     } 
/*      */ 
/*      */     
/* 1434 */     Container container = SwingUtilities.getUnwrappedParent(this.table);
/* 1435 */     if (container != null) {
/* 1436 */       container = container.getParent();
/* 1437 */       if (container != null && container instanceof JScrollPane) {
/* 1438 */         LookAndFeel.installBorder((JScrollPane)container, "Table.scrollPaneBorder");
/*      */       }
/*      */     } 
/*      */     
/* 1442 */     this.isFileList = Boolean.TRUE.equals(this.table.getClientProperty("Table.isFileList"));
/*      */   }
/*      */   
/*      */   private void installDefaults2() {
/* 1446 */     TransferHandler transferHandler = this.table.getTransferHandler();
/* 1447 */     if (transferHandler == null || transferHandler instanceof UIResource) {
/* 1448 */       this.table.setTransferHandler(defaultTransferHandler);
/*      */ 
/*      */       
/* 1451 */       if (this.table.getDropTarget() instanceof UIResource) {
/* 1452 */         this.table.setDropTarget(null);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installListeners() {
/* 1461 */     this.focusListener = createFocusListener();
/* 1462 */     this.keyListener = createKeyListener();
/* 1463 */     this.mouseInputListener = createMouseInputListener();
/*      */     
/* 1465 */     this.table.addFocusListener(this.focusListener);
/* 1466 */     this.table.addKeyListener(this.keyListener);
/* 1467 */     this.table.addMouseListener(this.mouseInputListener);
/* 1468 */     this.table.addMouseMotionListener(this.mouseInputListener);
/* 1469 */     this.table.addPropertyChangeListener(getHandler());
/* 1470 */     if (this.isFileList) {
/* 1471 */       this.table.getSelectionModel().addListSelectionListener(getHandler());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installKeyboardActions() {
/* 1479 */     LazyActionMap.installLazyActionMap(this.table, BasicTableUI.class, "Table.actionMap");
/*      */ 
/*      */     
/* 1482 */     InputMap inputMap = getInputMap(1);
/*      */     
/* 1484 */     SwingUtilities.replaceUIInputMap(this.table, 1, inputMap);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   InputMap getInputMap(int paramInt) {
/* 1490 */     if (paramInt == 1) {
/*      */       
/* 1492 */       InputMap inputMap1 = (InputMap)DefaultLookup.get(this.table, this, "Table.ancestorInputMap");
/*      */       
/*      */       InputMap inputMap2;
/*      */       
/* 1496 */       if (this.table.getComponentOrientation().isLeftToRight() || (
/* 1497 */         inputMap2 = (InputMap)DefaultLookup.get(this.table, this, "Table.ancestorInputMap.RightToLeft")) == null)
/*      */       {
/* 1499 */         return inputMap1;
/*      */       }
/* 1501 */       inputMap2.setParent(inputMap1);
/* 1502 */       return inputMap2;
/*      */     } 
/*      */     
/* 1505 */     return null;
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
/*      */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/* 1521 */     paramLazyActionMap.put(new Actions("selectNextColumn", 1, 0, false, false));
/*      */     
/* 1523 */     paramLazyActionMap.put(new Actions("selectNextColumnChangeLead", 1, 0, false, false));
/*      */     
/* 1525 */     paramLazyActionMap.put(new Actions("selectPreviousColumn", -1, 0, false, false));
/*      */     
/* 1527 */     paramLazyActionMap.put(new Actions("selectPreviousColumnChangeLead", -1, 0, false, false));
/*      */     
/* 1529 */     paramLazyActionMap.put(new Actions("selectNextRow", 0, 1, false, false));
/*      */     
/* 1531 */     paramLazyActionMap.put(new Actions("selectNextRowChangeLead", 0, 1, false, false));
/*      */     
/* 1533 */     paramLazyActionMap.put(new Actions("selectPreviousRow", 0, -1, false, false));
/*      */     
/* 1535 */     paramLazyActionMap.put(new Actions("selectPreviousRowChangeLead", 0, -1, false, false));
/*      */     
/* 1537 */     paramLazyActionMap.put(new Actions("selectNextColumnExtendSelection", 1, 0, true, false));
/*      */     
/* 1539 */     paramLazyActionMap.put(new Actions("selectPreviousColumnExtendSelection", -1, 0, true, false));
/*      */     
/* 1541 */     paramLazyActionMap.put(new Actions("selectNextRowExtendSelection", 0, 1, true, false));
/*      */     
/* 1543 */     paramLazyActionMap.put(new Actions("selectPreviousRowExtendSelection", 0, -1, true, false));
/*      */     
/* 1545 */     paramLazyActionMap.put(new Actions("scrollUpChangeSelection", false, false, true, false));
/*      */     
/* 1547 */     paramLazyActionMap.put(new Actions("scrollDownChangeSelection", false, true, true, false));
/*      */     
/* 1549 */     paramLazyActionMap.put(new Actions("selectFirstColumn", false, false, false, true));
/*      */     
/* 1551 */     paramLazyActionMap.put(new Actions("selectLastColumn", false, true, false, true));
/*      */ 
/*      */     
/* 1554 */     paramLazyActionMap.put(new Actions("scrollUpExtendSelection", true, false, true, false));
/*      */     
/* 1556 */     paramLazyActionMap.put(new Actions("scrollDownExtendSelection", true, true, true, false));
/*      */     
/* 1558 */     paramLazyActionMap.put(new Actions("selectFirstColumnExtendSelection", true, false, false, true));
/*      */     
/* 1560 */     paramLazyActionMap.put(new Actions("selectLastColumnExtendSelection", true, true, false, true));
/*      */ 
/*      */     
/* 1563 */     paramLazyActionMap.put(new Actions("selectFirstRow", false, false, true, true));
/* 1564 */     paramLazyActionMap.put(new Actions("selectLastRow", false, true, true, true));
/*      */     
/* 1566 */     paramLazyActionMap.put(new Actions("selectFirstRowExtendSelection", true, false, true, true));
/*      */     
/* 1568 */     paramLazyActionMap.put(new Actions("selectLastRowExtendSelection", true, true, true, true));
/*      */ 
/*      */     
/* 1571 */     paramLazyActionMap.put(new Actions("selectNextColumnCell", 1, 0, false, true));
/*      */     
/* 1573 */     paramLazyActionMap.put(new Actions("selectPreviousColumnCell", -1, 0, false, true));
/*      */     
/* 1575 */     paramLazyActionMap.put(new Actions("selectNextRowCell", 0, 1, false, true));
/* 1576 */     paramLazyActionMap.put(new Actions("selectPreviousRowCell", 0, -1, false, true));
/*      */ 
/*      */     
/* 1579 */     paramLazyActionMap.put(new Actions("selectAll"));
/* 1580 */     paramLazyActionMap.put(new Actions("clearSelection"));
/* 1581 */     paramLazyActionMap.put(new Actions("cancel"));
/* 1582 */     paramLazyActionMap.put(new Actions("startEditing"));
/*      */     
/* 1584 */     paramLazyActionMap.put(TransferHandler.getCutAction().getValue("Name"), 
/* 1585 */         TransferHandler.getCutAction());
/* 1586 */     paramLazyActionMap.put(TransferHandler.getCopyAction().getValue("Name"), 
/* 1587 */         TransferHandler.getCopyAction());
/* 1588 */     paramLazyActionMap.put(TransferHandler.getPasteAction().getValue("Name"), 
/* 1589 */         TransferHandler.getPasteAction());
/*      */     
/* 1591 */     paramLazyActionMap.put(new Actions("scrollLeftChangeSelection", false, false, false, false));
/*      */     
/* 1593 */     paramLazyActionMap.put(new Actions("scrollRightChangeSelection", false, true, false, false));
/*      */     
/* 1595 */     paramLazyActionMap.put(new Actions("scrollLeftExtendSelection", true, false, false, false));
/*      */     
/* 1597 */     paramLazyActionMap.put(new Actions("scrollRightExtendSelection", true, true, false, false));
/*      */ 
/*      */     
/* 1600 */     paramLazyActionMap.put(new Actions("addToSelection"));
/* 1601 */     paramLazyActionMap.put(new Actions("toggleAndAnchor"));
/* 1602 */     paramLazyActionMap.put(new Actions("extendTo"));
/* 1603 */     paramLazyActionMap.put(new Actions("moveSelectionTo"));
/* 1604 */     paramLazyActionMap.put(new Actions("focusHeader"));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/* 1610 */     uninstallDefaults();
/* 1611 */     uninstallListeners();
/* 1612 */     uninstallKeyboardActions();
/*      */     
/* 1614 */     this.table.remove(this.rendererPane);
/* 1615 */     this.rendererPane = null;
/* 1616 */     this.table = null;
/*      */   }
/*      */   
/*      */   protected void uninstallDefaults() {
/* 1620 */     if (this.table.getTransferHandler() instanceof UIResource) {
/* 1621 */       this.table.setTransferHandler((TransferHandler)null);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void uninstallListeners() {
/* 1626 */     this.table.removeFocusListener(this.focusListener);
/* 1627 */     this.table.removeKeyListener(this.keyListener);
/* 1628 */     this.table.removeMouseListener(this.mouseInputListener);
/* 1629 */     this.table.removeMouseMotionListener(this.mouseInputListener);
/* 1630 */     this.table.removePropertyChangeListener(getHandler());
/* 1631 */     if (this.isFileList) {
/* 1632 */       this.table.getSelectionModel().removeListSelectionListener(getHandler());
/*      */     }
/*      */     
/* 1635 */     this.focusListener = null;
/* 1636 */     this.keyListener = null;
/* 1637 */     this.mouseInputListener = null;
/* 1638 */     this.handler = null;
/*      */   }
/*      */   
/*      */   protected void uninstallKeyboardActions() {
/* 1642 */     SwingUtilities.replaceUIInputMap(this.table, 1, null);
/*      */     
/* 1644 */     SwingUtilities.replaceUIActionMap(this.table, null);
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
/* 1656 */     super.getBaseline(paramJComponent, paramInt1, paramInt2);
/* 1657 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/* 1658 */     Component component = (Component)uIDefaults.get(BASELINE_COMPONENT_KEY);
/*      */     
/* 1660 */     if (component == null) {
/* 1661 */       DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
/* 1662 */       component = defaultTableCellRenderer.getTableCellRendererComponent(this.table, "a", false, false, -1, -1);
/*      */       
/* 1664 */       uIDefaults.put(BASELINE_COMPONENT_KEY, component);
/*      */     } 
/* 1666 */     component.setFont(this.table.getFont());
/* 1667 */     int i = this.table.getRowMargin();
/* 1668 */     return component.getBaseline(2147483647, this.table.getRowHeight() - i) + i / 2;
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
/*      */   public Component.BaselineResizeBehavior getBaselineResizeBehavior(JComponent paramJComponent) {
/* 1682 */     super.getBaselineResizeBehavior(paramJComponent);
/* 1683 */     return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Dimension createTableSize(long paramLong) {
/* 1691 */     int i = 0;
/* 1692 */     int j = this.table.getRowCount();
/* 1693 */     if (j > 0 && this.table.getColumnCount() > 0) {
/* 1694 */       Rectangle rectangle = this.table.getCellRect(j - 1, 0, true);
/* 1695 */       i = rectangle.y + rectangle.height;
/*      */     } 
/*      */ 
/*      */     
/* 1699 */     long l = Math.abs(paramLong);
/* 1700 */     if (l > 2147483647L) {
/* 1701 */       l = 2147483647L;
/*      */     }
/* 1703 */     return new Dimension((int)l, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 1712 */     long l = 0L;
/* 1713 */     Enumeration<TableColumn> enumeration = this.table.getColumnModel().getColumns();
/* 1714 */     while (enumeration.hasMoreElements()) {
/* 1715 */       TableColumn tableColumn = enumeration.nextElement();
/* 1716 */       l += tableColumn.getMinWidth();
/*      */     } 
/* 1718 */     return createTableSize(l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 1727 */     long l = 0L;
/* 1728 */     Enumeration<TableColumn> enumeration = this.table.getColumnModel().getColumns();
/* 1729 */     while (enumeration.hasMoreElements()) {
/* 1730 */       TableColumn tableColumn = enumeration.nextElement();
/* 1731 */       l += tableColumn.getPreferredWidth();
/*      */     } 
/* 1733 */     return createTableSize(l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 1742 */     long l = 0L;
/* 1743 */     Enumeration<TableColumn> enumeration = this.table.getColumnModel().getColumns();
/* 1744 */     while (enumeration.hasMoreElements()) {
/* 1745 */       TableColumn tableColumn = enumeration.nextElement();
/* 1746 */       l += tableColumn.getMaxWidth();
/*      */     } 
/* 1748 */     return createTableSize(l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 1759 */     Rectangle rectangle1 = paramGraphics.getClipBounds();
/*      */     
/* 1761 */     Rectangle rectangle2 = this.table.getBounds();
/*      */ 
/*      */     
/* 1764 */     rectangle2.x = rectangle2.y = 0;
/*      */     
/* 1766 */     if (this.table.getRowCount() <= 0 || this.table.getColumnCount() <= 0 || 
/*      */ 
/*      */       
/* 1769 */       !rectangle2.intersects(rectangle1)) {
/*      */       
/* 1771 */       paintDropLines(paramGraphics);
/*      */       
/*      */       return;
/*      */     } 
/* 1775 */     boolean bool = this.table.getComponentOrientation().isLeftToRight();
/*      */     
/* 1777 */     Point point1 = rectangle1.getLocation();
/* 1778 */     Point point2 = new Point(rectangle1.x + rectangle1.width - 1, rectangle1.y + rectangle1.height - 1);
/*      */ 
/*      */     
/* 1781 */     int i = this.table.rowAtPoint(point1);
/* 1782 */     int j = this.table.rowAtPoint(point2);
/*      */ 
/*      */     
/* 1785 */     if (i == -1) {
/* 1786 */       i = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1792 */     if (j == -1) {
/* 1793 */       j = this.table.getRowCount() - 1;
/*      */     }
/*      */     
/* 1796 */     int k = this.table.columnAtPoint(bool ? point1 : point2);
/* 1797 */     int m = this.table.columnAtPoint(bool ? point2 : point1);
/*      */     
/* 1799 */     if (k == -1) {
/* 1800 */       k = 0;
/*      */     }
/*      */ 
/*      */     
/* 1804 */     if (m == -1) {
/* 1805 */       m = this.table.getColumnCount() - 1;
/*      */     }
/*      */ 
/*      */     
/* 1809 */     paintGrid(paramGraphics, i, j, k, m);
/*      */ 
/*      */     
/* 1812 */     paintCells(paramGraphics, i, j, k, m);
/*      */     
/* 1814 */     paintDropLines(paramGraphics);
/*      */   }
/*      */   
/*      */   private void paintDropLines(Graphics paramGraphics) {
/* 1818 */     JTable.DropLocation dropLocation = this.table.getDropLocation();
/* 1819 */     if (dropLocation == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1823 */     Color color1 = UIManager.getColor("Table.dropLineColor");
/* 1824 */     Color color2 = UIManager.getColor("Table.dropLineShortColor");
/* 1825 */     if (color1 == null && color2 == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1831 */     Rectangle rectangle = getHDropLineRect(dropLocation);
/* 1832 */     if (rectangle != null) {
/* 1833 */       int i = rectangle.x;
/* 1834 */       int j = rectangle.width;
/* 1835 */       if (color1 != null) {
/* 1836 */         extendRect(rectangle, true);
/* 1837 */         paramGraphics.setColor(color1);
/* 1838 */         paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */       } 
/* 1840 */       if (!dropLocation.isInsertColumn() && color2 != null) {
/* 1841 */         paramGraphics.setColor(color2);
/* 1842 */         paramGraphics.fillRect(i, rectangle.y, j, rectangle.height);
/*      */       } 
/*      */     } 
/*      */     
/* 1846 */     rectangle = getVDropLineRect(dropLocation);
/* 1847 */     if (rectangle != null) {
/* 1848 */       int i = rectangle.y;
/* 1849 */       int j = rectangle.height;
/* 1850 */       if (color1 != null) {
/* 1851 */         extendRect(rectangle, false);
/* 1852 */         paramGraphics.setColor(color1);
/* 1853 */         paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */       } 
/* 1855 */       if (!dropLocation.isInsertRow() && color2 != null) {
/* 1856 */         paramGraphics.setColor(color2);
/* 1857 */         paramGraphics.fillRect(rectangle.x, i, rectangle.width, j);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private Rectangle getHDropLineRect(JTable.DropLocation paramDropLocation) {
/* 1863 */     if (!paramDropLocation.isInsertRow()) {
/* 1864 */       return null;
/*      */     }
/*      */     
/* 1867 */     int i = paramDropLocation.getRow();
/* 1868 */     int j = paramDropLocation.getColumn();
/* 1869 */     if (j >= this.table.getColumnCount()) {
/* 1870 */       j--;
/*      */     }
/*      */     
/* 1873 */     Rectangle rectangle = this.table.getCellRect(i, j, true);
/*      */     
/* 1875 */     if (i >= this.table.getRowCount()) {
/* 1876 */       i--;
/* 1877 */       Rectangle rectangle1 = this.table.getCellRect(i, j, true);
/* 1878 */       rectangle1.y += rectangle1.height;
/*      */     } 
/*      */     
/* 1881 */     if (rectangle.y == 0) {
/* 1882 */       rectangle.y = -1;
/*      */     } else {
/* 1884 */       rectangle.y -= 2;
/*      */     } 
/*      */     
/* 1887 */     rectangle.height = 3;
/*      */     
/* 1889 */     return rectangle;
/*      */   }
/*      */   
/*      */   private Rectangle getVDropLineRect(JTable.DropLocation paramDropLocation) {
/* 1893 */     if (!paramDropLocation.isInsertColumn()) {
/* 1894 */       return null;
/*      */     }
/*      */     
/* 1897 */     boolean bool = this.table.getComponentOrientation().isLeftToRight();
/* 1898 */     int i = paramDropLocation.getColumn();
/* 1899 */     Rectangle rectangle = this.table.getCellRect(paramDropLocation.getRow(), i, true);
/*      */     
/* 1901 */     if (i >= this.table.getColumnCount()) {
/* 1902 */       i--;
/* 1903 */       rectangle = this.table.getCellRect(paramDropLocation.getRow(), i, true);
/* 1904 */       if (bool) {
/* 1905 */         rectangle.x += rectangle.width;
/*      */       }
/* 1907 */     } else if (!bool) {
/* 1908 */       rectangle.x += rectangle.width;
/*      */     } 
/*      */     
/* 1911 */     if (rectangle.x == 0) {
/* 1912 */       rectangle.x = -1;
/*      */     } else {
/* 1914 */       rectangle.x -= 2;
/*      */     } 
/*      */     
/* 1917 */     rectangle.width = 3;
/*      */     
/* 1919 */     return rectangle;
/*      */   }
/*      */   
/*      */   private Rectangle extendRect(Rectangle paramRectangle, boolean paramBoolean) {
/* 1923 */     if (paramRectangle == null) {
/* 1924 */       return paramRectangle;
/*      */     }
/*      */     
/* 1927 */     if (paramBoolean) {
/* 1928 */       paramRectangle.x = 0;
/* 1929 */       paramRectangle.width = this.table.getWidth();
/*      */     } else {
/* 1931 */       paramRectangle.y = 0;
/*      */       
/* 1933 */       if (this.table.getRowCount() != 0) {
/* 1934 */         Rectangle rectangle = this.table.getCellRect(this.table.getRowCount() - 1, 0, true);
/* 1935 */         paramRectangle.height = rectangle.y + rectangle.height;
/*      */       } else {
/* 1937 */         paramRectangle.height = this.table.getHeight();
/*      */       } 
/*      */     } 
/*      */     
/* 1941 */     return paramRectangle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void paintGrid(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1952 */     paramGraphics.setColor(this.table.getGridColor());
/*      */     
/* 1954 */     Rectangle rectangle1 = this.table.getCellRect(paramInt1, paramInt3, true);
/* 1955 */     Rectangle rectangle2 = this.table.getCellRect(paramInt2, paramInt4, true);
/* 1956 */     Rectangle rectangle3 = rectangle1.union(rectangle2);
/*      */     
/* 1958 */     if (this.table.getShowHorizontalLines()) {
/* 1959 */       int i = rectangle3.x + rectangle3.width;
/* 1960 */       int j = rectangle3.y;
/* 1961 */       for (int k = paramInt1; k <= paramInt2; k++) {
/* 1962 */         j += this.table.getRowHeight(k);
/* 1963 */         paramGraphics.drawLine(rectangle3.x, j - 1, i - 1, j - 1);
/*      */       } 
/*      */     } 
/* 1966 */     if (this.table.getShowVerticalLines()) {
/* 1967 */       TableColumnModel tableColumnModel = this.table.getColumnModel();
/* 1968 */       int i = rectangle3.y + rectangle3.height;
/*      */       
/* 1970 */       if (this.table.getComponentOrientation().isLeftToRight()) {
/* 1971 */         int j = rectangle3.x;
/* 1972 */         for (int k = paramInt3; k <= paramInt4; k++) {
/* 1973 */           int m = tableColumnModel.getColumn(k).getWidth();
/* 1974 */           j += m;
/* 1975 */           paramGraphics.drawLine(j - 1, 0, j - 1, i - 1);
/*      */         } 
/*      */       } else {
/* 1978 */         int j = rectangle3.x;
/* 1979 */         for (int k = paramInt4; k >= paramInt3; k--) {
/* 1980 */           int m = tableColumnModel.getColumn(k).getWidth();
/* 1981 */           j += m;
/* 1982 */           paramGraphics.drawLine(j - 1, 0, j - 1, i - 1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private int viewIndexForColumn(TableColumn paramTableColumn) {
/* 1989 */     TableColumnModel tableColumnModel = this.table.getColumnModel();
/* 1990 */     for (byte b = 0; b < tableColumnModel.getColumnCount(); b++) {
/* 1991 */       if (tableColumnModel.getColumn(b) == paramTableColumn) {
/* 1992 */         return b;
/*      */       }
/*      */     } 
/* 1995 */     return -1;
/*      */   }
/*      */   
/*      */   private void paintCells(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1999 */     JTableHeader jTableHeader = this.table.getTableHeader();
/* 2000 */     TableColumn tableColumn = (jTableHeader == null) ? null : jTableHeader.getDraggedColumn();
/*      */     
/* 2002 */     TableColumnModel tableColumnModel = this.table.getColumnModel();
/* 2003 */     int i = tableColumnModel.getColumnMargin();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2008 */     if (this.table.getComponentOrientation().isLeftToRight()) {
/* 2009 */       for (int j = paramInt1; j <= paramInt2; j++) {
/* 2010 */         Rectangle rectangle = this.table.getCellRect(j, paramInt3, false);
/* 2011 */         for (int k = paramInt3; k <= paramInt4; k++) {
/* 2012 */           TableColumn tableColumn1 = tableColumnModel.getColumn(k);
/* 2013 */           int m = tableColumn1.getWidth();
/* 2014 */           rectangle.width = m - i;
/* 2015 */           if (tableColumn1 != tableColumn) {
/* 2016 */             paintCell(paramGraphics, rectangle, j, k);
/*      */           }
/* 2018 */           rectangle.x += m;
/*      */         } 
/*      */       } 
/*      */     } else {
/* 2022 */       for (int j = paramInt1; j <= paramInt2; j++) {
/* 2023 */         Rectangle rectangle = this.table.getCellRect(j, paramInt3, false);
/* 2024 */         TableColumn tableColumn1 = tableColumnModel.getColumn(paramInt3);
/* 2025 */         if (tableColumn1 != tableColumn) {
/* 2026 */           int m = tableColumn1.getWidth();
/* 2027 */           rectangle.width = m - i;
/* 2028 */           paintCell(paramGraphics, rectangle, j, paramInt3);
/*      */         } 
/* 2030 */         for (int k = paramInt3 + 1; k <= paramInt4; k++) {
/* 2031 */           tableColumn1 = tableColumnModel.getColumn(k);
/* 2032 */           int m = tableColumn1.getWidth();
/* 2033 */           rectangle.width = m - i;
/* 2034 */           rectangle.x -= m;
/* 2035 */           if (tableColumn1 != tableColumn) {
/* 2036 */             paintCell(paramGraphics, rectangle, j, k);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2043 */     if (tableColumn != null) {
/* 2044 */       paintDraggedArea(paramGraphics, paramInt1, paramInt2, tableColumn, jTableHeader.getDraggedDistance());
/*      */     }
/*      */ 
/*      */     
/* 2048 */     this.rendererPane.removeAll();
/*      */   }
/*      */   
/*      */   private void paintDraggedArea(Graphics paramGraphics, int paramInt1, int paramInt2, TableColumn paramTableColumn, int paramInt3) {
/* 2052 */     int i = viewIndexForColumn(paramTableColumn);
/*      */     
/* 2054 */     Rectangle rectangle1 = this.table.getCellRect(paramInt1, i, true);
/* 2055 */     Rectangle rectangle2 = this.table.getCellRect(paramInt2, i, true);
/*      */     
/* 2057 */     Rectangle rectangle3 = rectangle1.union(rectangle2);
/*      */ 
/*      */     
/* 2060 */     paramGraphics.setColor(this.table.getParent().getBackground());
/* 2061 */     paramGraphics.fillRect(rectangle3.x, rectangle3.y, rectangle3.width, rectangle3.height);
/*      */ 
/*      */ 
/*      */     
/* 2065 */     rectangle3.x += paramInt3;
/*      */ 
/*      */     
/* 2068 */     paramGraphics.setColor(this.table.getBackground());
/* 2069 */     paramGraphics.fillRect(rectangle3.x, rectangle3.y, rectangle3.width, rectangle3.height);
/*      */ 
/*      */ 
/*      */     
/* 2073 */     if (this.table.getShowVerticalLines()) {
/* 2074 */       paramGraphics.setColor(this.table.getGridColor());
/* 2075 */       int k = rectangle3.x;
/* 2076 */       int m = rectangle3.y;
/* 2077 */       int n = k + rectangle3.width - 1;
/* 2078 */       int i1 = m + rectangle3.height - 1;
/*      */       
/* 2080 */       paramGraphics.drawLine(k - 1, m, k - 1, i1);
/*      */       
/* 2082 */       paramGraphics.drawLine(n, m, n, i1);
/*      */     } 
/*      */     
/* 2085 */     for (int j = paramInt1; j <= paramInt2; j++) {
/*      */       
/* 2087 */       Rectangle rectangle = this.table.getCellRect(j, i, false);
/* 2088 */       rectangle.x += paramInt3;
/* 2089 */       paintCell(paramGraphics, rectangle, j, i);
/*      */ 
/*      */       
/* 2092 */       if (this.table.getShowHorizontalLines()) {
/* 2093 */         paramGraphics.setColor(this.table.getGridColor());
/* 2094 */         Rectangle rectangle4 = this.table.getCellRect(j, i, true);
/* 2095 */         rectangle4.x += paramInt3;
/* 2096 */         int k = rectangle4.x;
/* 2097 */         int m = rectangle4.y;
/* 2098 */         int n = k + rectangle4.width - 1;
/* 2099 */         int i1 = m + rectangle4.height - 1;
/* 2100 */         paramGraphics.drawLine(k, i1, n, i1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void paintCell(Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 2106 */     if (this.table.isEditing() && this.table.getEditingRow() == paramInt1 && this.table
/* 2107 */       .getEditingColumn() == paramInt2) {
/* 2108 */       Component component = this.table.getEditorComponent();
/* 2109 */       component.setBounds(paramRectangle);
/* 2110 */       component.validate();
/*      */     } else {
/*      */       
/* 2113 */       TableCellRenderer tableCellRenderer = this.table.getCellRenderer(paramInt1, paramInt2);
/* 2114 */       Component component = this.table.prepareRenderer(tableCellRenderer, paramInt1, paramInt2);
/* 2115 */       this.rendererPane.paintComponent(paramGraphics, component, this.table, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getAdjustedLead(JTable paramJTable, boolean paramBoolean, ListSelectionModel paramListSelectionModel) {
/* 2124 */     int i = paramListSelectionModel.getLeadSelectionIndex();
/* 2125 */     int j = paramBoolean ? paramJTable.getRowCount() : paramJTable.getColumnCount();
/* 2126 */     return (i < j) ? i : -1;
/*      */   }
/*      */   
/*      */   private static int getAdjustedLead(JTable paramJTable, boolean paramBoolean) {
/* 2130 */     return paramBoolean ? getAdjustedLead(paramJTable, paramBoolean, paramJTable.getSelectionModel()) : 
/* 2131 */       getAdjustedLead(paramJTable, paramBoolean, paramJTable.getColumnModel().getSelectionModel());
/*      */   }
/*      */ 
/*      */   
/* 2135 */   private static final TransferHandler defaultTransferHandler = new TableTransferHandler();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class TableTransferHandler
/*      */     extends TransferHandler
/*      */     implements UIResource
/*      */   {
/*      */     protected Transferable createTransferable(JComponent param1JComponent) {
/* 2149 */       if (param1JComponent instanceof JTable) {
/* 2150 */         int[] arrayOfInt1, arrayOfInt2; JTable jTable = (JTable)param1JComponent;
/*      */ 
/*      */ 
/*      */         
/* 2154 */         if (!jTable.getRowSelectionAllowed() && !jTable.getColumnSelectionAllowed()) {
/* 2155 */           return null;
/*      */         }
/*      */         
/* 2158 */         if (!jTable.getRowSelectionAllowed()) {
/* 2159 */           int i = jTable.getRowCount();
/*      */           
/* 2161 */           arrayOfInt1 = new int[i];
/* 2162 */           for (byte b1 = 0; b1 < i; b1++) {
/* 2163 */             arrayOfInt1[b1] = b1;
/*      */           }
/*      */         } else {
/* 2166 */           arrayOfInt1 = jTable.getSelectedRows();
/*      */         } 
/*      */         
/* 2169 */         if (!jTable.getColumnSelectionAllowed()) {
/* 2170 */           int i = jTable.getColumnCount();
/*      */           
/* 2172 */           arrayOfInt2 = new int[i];
/* 2173 */           for (byte b1 = 0; b1 < i; b1++) {
/* 2174 */             arrayOfInt2[b1] = b1;
/*      */           }
/*      */         } else {
/* 2177 */           arrayOfInt2 = jTable.getSelectedColumns();
/*      */         } 
/*      */         
/* 2180 */         if (arrayOfInt1 == null || arrayOfInt2 == null || arrayOfInt1.length == 0 || arrayOfInt2.length == 0) {
/* 2181 */           return null;
/*      */         }
/*      */         
/* 2184 */         StringBuffer stringBuffer1 = new StringBuffer();
/* 2185 */         StringBuffer stringBuffer2 = new StringBuffer();
/*      */         
/* 2187 */         stringBuffer2.append("<html>\n<body>\n<table>\n");
/*      */         
/* 2189 */         for (byte b = 0; b < arrayOfInt1.length; b++) {
/* 2190 */           stringBuffer2.append("<tr>\n");
/* 2191 */           for (byte b1 = 0; b1 < arrayOfInt2.length; b1++) {
/* 2192 */             Object object = jTable.getValueAt(arrayOfInt1[b], arrayOfInt2[b1]);
/* 2193 */             String str = (object == null) ? "" : object.toString();
/* 2194 */             stringBuffer1.append(str + "\t");
/* 2195 */             stringBuffer2.append("  <td>" + str + "</td>\n");
/*      */           } 
/*      */           
/* 2198 */           stringBuffer1.deleteCharAt(stringBuffer1.length() - 1).append("\n");
/* 2199 */           stringBuffer2.append("</tr>\n");
/*      */         } 
/*      */ 
/*      */         
/* 2203 */         stringBuffer1.deleteCharAt(stringBuffer1.length() - 1);
/* 2204 */         stringBuffer2.append("</table>\n</body>\n</html>");
/*      */         
/* 2206 */         return new BasicTransferable(stringBuffer1.toString(), stringBuffer2.toString());
/*      */       } 
/*      */       
/* 2209 */       return null;
/*      */     }
/*      */     
/*      */     public int getSourceActions(JComponent param1JComponent) {
/* 2213 */       return 1;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicTableUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */