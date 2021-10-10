/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.KeyboardFocusManager;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.print.PageFormat;
/*      */ import java.awt.print.Printable;
/*      */ import java.awt.print.PrinterException;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.text.DateFormat;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.EventObject;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Locale;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleAction;
/*      */ import javax.accessibility.AccessibleComponent;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleExtendedTable;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleSelection;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.accessibility.AccessibleTable;
/*      */ import javax.accessibility.AccessibleTableModelChange;
/*      */ import javax.accessibility.AccessibleText;
/*      */ import javax.accessibility.AccessibleValue;
/*      */ import javax.print.PrintService;
/*      */ import javax.print.attribute.HashPrintRequestAttributeSet;
/*      */ import javax.print.attribute.PrintRequestAttributeSet;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import javax.swing.border.LineBorder;
/*      */ import javax.swing.event.CellEditorListener;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import javax.swing.event.RowSorterEvent;
/*      */ import javax.swing.event.RowSorterListener;
/*      */ import javax.swing.event.TableColumnModelEvent;
/*      */ import javax.swing.event.TableColumnModelListener;
/*      */ import javax.swing.event.TableModelEvent;
/*      */ import javax.swing.event.TableModelListener;
/*      */ import javax.swing.plaf.TableUI;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.table.AbstractTableModel;
/*      */ import javax.swing.table.DefaultTableCellRenderer;
/*      */ import javax.swing.table.DefaultTableColumnModel;
/*      */ import javax.swing.table.DefaultTableModel;
/*      */ import javax.swing.table.JTableHeader;
/*      */ import javax.swing.table.TableCellEditor;
/*      */ import javax.swing.table.TableCellRenderer;
/*      */ import javax.swing.table.TableColumn;
/*      */ import javax.swing.table.TableColumnModel;
/*      */ import javax.swing.table.TableModel;
/*      */ import javax.swing.table.TableRowSorter;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ import sun.swing.PrintingStatus;
/*      */ import sun.swing.SwingUtilities2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JTable
/*      */   extends JComponent
/*      */   implements TableModelListener, Scrollable, TableColumnModelListener, ListSelectionListener, CellEditorListener, Accessible, RowSorterListener
/*      */ {
/*      */   private static final String uiClassID = "TableUI";
/*      */   public static final int AUTO_RESIZE_OFF = 0;
/*      */   public static final int AUTO_RESIZE_NEXT_COLUMN = 1;
/*      */   public static final int AUTO_RESIZE_SUBSEQUENT_COLUMNS = 2;
/*      */   public static final int AUTO_RESIZE_LAST_COLUMN = 3;
/*      */   public static final int AUTO_RESIZE_ALL_COLUMNS = 4;
/*      */   protected TableModel dataModel;
/*      */   protected TableColumnModel columnModel;
/*      */   protected ListSelectionModel selectionModel;
/*      */   protected JTableHeader tableHeader;
/*      */   protected int rowHeight;
/*      */   protected int rowMargin;
/*      */   protected Color gridColor;
/*      */   protected boolean showHorizontalLines;
/*      */   protected boolean showVerticalLines;
/*      */   protected int autoResizeMode;
/*      */   protected boolean autoCreateColumnsFromModel;
/*      */   protected Dimension preferredViewportSize;
/*      */   protected boolean rowSelectionAllowed;
/*      */   protected boolean cellSelectionEnabled;
/*      */   protected transient Component editorComp;
/*      */   protected transient TableCellEditor cellEditor;
/*      */   protected transient int editingColumn;
/*      */   protected transient int editingRow;
/*      */   protected transient Hashtable defaultRenderersByColumnClass;
/*      */   protected transient Hashtable defaultEditorsByColumnClass;
/*      */   protected Color selectionForeground;
/*      */   protected Color selectionBackground;
/*      */   private SizeSequence rowModel;
/*      */   private boolean dragEnabled;
/*      */   private boolean surrendersFocusOnKeystroke;
/*      */   
/*      */   public enum PrintMode
/*      */   {
/*  267 */     NORMAL,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  274 */     FIT_WIDTH;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  387 */   private PropertyChangeListener editorRemover = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean columnSelectionAdjusting;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean rowSelectionAdjusting;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Throwable printError;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isRowHeightSet;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean updateSelectionOnSort;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient SortManager sortManager;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ignoreSortChange;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sorterChanged;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean autoCreateRowSorter;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fillsViewportHeight;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  445 */   private DropMode dropMode = DropMode.USE_SELECTION;
/*      */ 
/*      */   
/*      */   private transient DropLocation dropLocation;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class DropLocation
/*      */     extends TransferHandler.DropLocation
/*      */   {
/*      */     private final int row;
/*      */ 
/*      */     
/*      */     private final int col;
/*      */ 
/*      */     
/*      */     private final boolean isInsertRow;
/*      */ 
/*      */     
/*      */     private final boolean isInsertCol;
/*      */ 
/*      */     
/*      */     private DropLocation(Point param1Point, int param1Int1, int param1Int2, boolean param1Boolean1, boolean param1Boolean2) {
/*  468 */       super(param1Point);
/*  469 */       this.row = param1Int1;
/*  470 */       this.col = param1Int2;
/*  471 */       this.isInsertRow = param1Boolean1;
/*  472 */       this.isInsertCol = param1Boolean2;
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
/*      */     public int getRow() {
/*  490 */       return this.row;
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
/*      */     public int getColumn() {
/*  508 */       return this.col;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isInsertRow() {
/*  518 */       return this.isInsertRow;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isInsertColumn() {
/*  528 */       return this.isInsertCol;
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
/*      */     public String toString() {
/*  540 */       return getClass().getName() + "[dropPoint=" + 
/*  541 */         getDropPoint() + ",row=" + this.row + ",column=" + this.col + ",insertRow=" + this.isInsertRow + ",insertColumn=" + this.isInsertCol + "]";
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
/*      */   public JTable() {
/*  563 */     this((TableModel)null, (TableColumnModel)null, (ListSelectionModel)null);
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
/*      */   public JTable(TableModel paramTableModel) {
/*  576 */     this(paramTableModel, (TableColumnModel)null, (ListSelectionModel)null);
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
/*      */   public JTable(TableModel paramTableModel, TableColumnModel paramTableColumnModel) {
/*  589 */     this(paramTableModel, paramTableColumnModel, (ListSelectionModel)null);
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
/*      */   public JTable(TableModel paramTableModel, TableColumnModel paramTableColumnModel, ListSelectionModel paramListSelectionModel) {
/*  612 */     setLayout((LayoutManager)null);
/*      */     
/*  614 */     setFocusTraversalKeys(0, 
/*  615 */         (Set)JComponent.getManagingFocusForwardTraversalKeys());
/*  616 */     setFocusTraversalKeys(1, 
/*  617 */         (Set)JComponent.getManagingFocusBackwardTraversalKeys());
/*  618 */     if (paramTableColumnModel == null) {
/*  619 */       paramTableColumnModel = createDefaultColumnModel();
/*  620 */       this.autoCreateColumnsFromModel = true;
/*      */     } 
/*  622 */     setColumnModel(paramTableColumnModel);
/*      */     
/*  624 */     if (paramListSelectionModel == null) {
/*  625 */       paramListSelectionModel = createDefaultSelectionModel();
/*      */     }
/*  627 */     setSelectionModel(paramListSelectionModel);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  632 */     if (paramTableModel == null) {
/*  633 */       paramTableModel = createDefaultDataModel();
/*      */     }
/*  635 */     setModel(paramTableModel);
/*      */     
/*  637 */     initializeLocalVars();
/*  638 */     updateUI();
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
/*      */   public JTable(int paramInt1, int paramInt2) {
/*  652 */     this(new DefaultTableModel(paramInt1, paramInt2));
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
/*      */   public JTable(Vector<? extends Vector> paramVector1, Vector<?> paramVector2) {
/*  670 */     this(new DefaultTableModel(paramVector1, paramVector2));
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
/*      */   public JTable(Object[][] paramArrayOfObject, Object[] paramArrayOfObject1) {
/*  687 */     this(new AbstractTableModel(paramArrayOfObject1, paramArrayOfObject) {
/*  688 */           public String getColumnName(int param1Int) { return columnNames[param1Int].toString(); }
/*  689 */           public int getRowCount() { return rowData.length; }
/*  690 */           public int getColumnCount() { return columnNames.length; }
/*  691 */           public Object getValueAt(int param1Int1, int param1Int2) { return rowData[param1Int1][param1Int2]; } public boolean isCellEditable(int param1Int1, int param1Int2) {
/*  692 */             return true;
/*      */           } public void setValueAt(Object param1Object, int param1Int1, int param1Int2) {
/*  694 */             rowData[param1Int1][param1Int2] = param1Object;
/*  695 */             fireTableCellUpdated(param1Int1, param1Int2);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addNotify() {
/*  706 */     super.addNotify();
/*  707 */     configureEnclosingScrollPane();
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
/*      */   protected void configureEnclosingScrollPane() {
/*  724 */     Container container = SwingUtilities.getUnwrappedParent(this);
/*  725 */     if (container instanceof JViewport) {
/*  726 */       JViewport jViewport = (JViewport)container;
/*  727 */       Container container1 = jViewport.getParent();
/*  728 */       if (container1 instanceof JScrollPane) {
/*  729 */         JScrollPane jScrollPane = (JScrollPane)container1;
/*      */ 
/*      */ 
/*      */         
/*  733 */         JViewport jViewport1 = jScrollPane.getViewport();
/*  734 */         if (jViewport1 == null || 
/*  735 */           SwingUtilities.getUnwrappedView(jViewport1) != this) {
/*      */           return;
/*      */         }
/*  738 */         jScrollPane.setColumnHeaderView(getTableHeader());
/*      */         
/*  740 */         configureEnclosingScrollPaneUI();
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
/*      */   
/*      */   private void configureEnclosingScrollPaneUI() {
/*  758 */     Container container = SwingUtilities.getUnwrappedParent(this);
/*  759 */     if (container instanceof JViewport) {
/*  760 */       JViewport jViewport = (JViewport)container;
/*  761 */       Container container1 = jViewport.getParent();
/*  762 */       if (container1 instanceof JScrollPane) {
/*  763 */         JScrollPane jScrollPane = (JScrollPane)container1;
/*      */ 
/*      */ 
/*      */         
/*  767 */         JViewport jViewport1 = jScrollPane.getViewport();
/*  768 */         if (jViewport1 == null || 
/*  769 */           SwingUtilities.getUnwrappedView(jViewport1) != this) {
/*      */           return;
/*      */         }
/*      */         
/*  773 */         Border border = jScrollPane.getBorder();
/*  774 */         if (border == null || border instanceof UIResource) {
/*      */           
/*  776 */           Border border1 = UIManager.getBorder("Table.scrollPaneBorder");
/*  777 */           if (border1 != null) {
/*  778 */             jScrollPane.setBorder(border1);
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  783 */         Component component = jScrollPane.getCorner("UPPER_TRAILING_CORNER");
/*  784 */         if (component == null || component instanceof UIResource) {
/*  785 */           component = null;
/*      */           try {
/*  787 */             component = (Component)UIManager.get("Table.scrollPaneCornerComponent");
/*      */           }
/*  789 */           catch (Exception exception) {}
/*      */ 
/*      */           
/*  792 */           jScrollPane.setCorner("UPPER_TRAILING_CORNER", component);
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
/*      */   public void removeNotify() {
/*  805 */     KeyboardFocusManager.getCurrentKeyboardFocusManager()
/*  806 */       .removePropertyChangeListener("permanentFocusOwner", this.editorRemover);
/*  807 */     this.editorRemover = null;
/*  808 */     unconfigureEnclosingScrollPane();
/*  809 */     super.removeNotify();
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
/*      */   protected void unconfigureEnclosingScrollPane() {
/*  825 */     Container container = SwingUtilities.getUnwrappedParent(this);
/*  826 */     if (container instanceof JViewport) {
/*  827 */       JViewport jViewport = (JViewport)container;
/*  828 */       Container container1 = jViewport.getParent();
/*  829 */       if (container1 instanceof JScrollPane) {
/*  830 */         JScrollPane jScrollPane = (JScrollPane)container1;
/*      */ 
/*      */ 
/*      */         
/*  834 */         JViewport jViewport1 = jScrollPane.getViewport();
/*  835 */         if (jViewport1 == null || 
/*  836 */           SwingUtilities.getUnwrappedView(jViewport1) != this) {
/*      */           return;
/*      */         }
/*  839 */         jScrollPane.setColumnHeaderView((Component)null);
/*      */ 
/*      */         
/*  842 */         Component component = jScrollPane.getCorner("UPPER_TRAILING_CORNER");
/*  843 */         if (component instanceof UIResource) {
/*  844 */           jScrollPane.setCorner("UPPER_TRAILING_CORNER", (Component)null);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void setUIProperty(String paramString, Object paramObject) {
/*  852 */     if (paramString == "rowHeight") {
/*  853 */       if (!this.isRowHeightSet) {
/*  854 */         setRowHeight(((Number)paramObject).intValue());
/*  855 */         this.isRowHeightSet = false;
/*      */       } 
/*      */       return;
/*      */     } 
/*  859 */     super.setUIProperty(paramString, paramObject);
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
/*      */   public static JScrollPane createScrollPaneForTable(JTable paramJTable) {
/*  874 */     return new JScrollPane(paramJTable);
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
/*      */   public void setTableHeader(JTableHeader paramJTableHeader) {
/*  892 */     if (this.tableHeader != paramJTableHeader) {
/*  893 */       JTableHeader jTableHeader = this.tableHeader;
/*      */       
/*  895 */       if (jTableHeader != null) {
/*  896 */         jTableHeader.setTable((JTable)null);
/*      */       }
/*  898 */       this.tableHeader = paramJTableHeader;
/*  899 */       if (paramJTableHeader != null) {
/*  900 */         paramJTableHeader.setTable(this);
/*      */       }
/*  902 */       firePropertyChange("tableHeader", jTableHeader, paramJTableHeader);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JTableHeader getTableHeader() {
/*  913 */     return this.tableHeader;
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
/*      */   public void setRowHeight(int paramInt) {
/*  931 */     if (paramInt <= 0) {
/*  932 */       throw new IllegalArgumentException("New row height less than 1");
/*      */     }
/*  934 */     int i = this.rowHeight;
/*  935 */     this.rowHeight = paramInt;
/*  936 */     this.rowModel = null;
/*  937 */     if (this.sortManager != null) {
/*  938 */       this.sortManager.modelRowSizes = null;
/*      */     }
/*  940 */     this.isRowHeightSet = true;
/*  941 */     resizeAndRepaint();
/*  942 */     firePropertyChange("rowHeight", i, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRowHeight() {
/*  952 */     return this.rowHeight;
/*      */   }
/*      */   
/*      */   private SizeSequence getRowModel() {
/*  956 */     if (this.rowModel == null) {
/*  957 */       this.rowModel = new SizeSequence(getRowCount(), getRowHeight());
/*      */     }
/*  959 */     return this.rowModel;
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
/*      */   public void setRowHeight(int paramInt1, int paramInt2) {
/*  978 */     if (paramInt2 <= 0) {
/*  979 */       throw new IllegalArgumentException("New row height less than 1");
/*      */     }
/*  981 */     getRowModel().setSize(paramInt1, paramInt2);
/*  982 */     if (this.sortManager != null) {
/*  983 */       this.sortManager.setViewRowHeight(paramInt1, paramInt2);
/*      */     }
/*  985 */     resizeAndRepaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRowHeight(int paramInt) {
/*  995 */     return (this.rowModel == null) ? getRowHeight() : this.rowModel.getSize(paramInt);
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
/*      */   public void setRowMargin(int paramInt) {
/* 1008 */     int i = this.rowMargin;
/* 1009 */     this.rowMargin = paramInt;
/* 1010 */     resizeAndRepaint();
/* 1011 */     firePropertyChange("rowMargin", i, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRowMargin() {
/* 1022 */     return this.rowMargin;
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
/*      */   public void setIntercellSpacing(Dimension paramDimension) {
/* 1040 */     setRowMargin(paramDimension.height);
/* 1041 */     getColumnModel().setColumnMargin(paramDimension.width);
/*      */     
/* 1043 */     resizeAndRepaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getIntercellSpacing() {
/* 1054 */     return new Dimension(getColumnModel().getColumnMargin(), this.rowMargin);
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
/*      */   public void setGridColor(Color paramColor) {
/* 1069 */     if (paramColor == null) {
/* 1070 */       throw new IllegalArgumentException("New color is null");
/*      */     }
/* 1072 */     Color color = this.gridColor;
/* 1073 */     this.gridColor = paramColor;
/* 1074 */     firePropertyChange("gridColor", color, paramColor);
/*      */     
/* 1076 */     repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getGridColor() {
/* 1087 */     return this.gridColor;
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
/*      */   public void setShowGrid(boolean paramBoolean) {
/* 1105 */     setShowHorizontalLines(paramBoolean);
/* 1106 */     setShowVerticalLines(paramBoolean);
/*      */ 
/*      */     
/* 1109 */     repaint();
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
/*      */   public void setShowHorizontalLines(boolean paramBoolean) {
/* 1125 */     boolean bool = this.showHorizontalLines;
/* 1126 */     this.showHorizontalLines = paramBoolean;
/* 1127 */     firePropertyChange("showHorizontalLines", bool, paramBoolean);
/*      */ 
/*      */     
/* 1130 */     repaint();
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
/*      */   public void setShowVerticalLines(boolean paramBoolean) {
/* 1146 */     boolean bool = this.showVerticalLines;
/* 1147 */     this.showVerticalLines = paramBoolean;
/* 1148 */     firePropertyChange("showVerticalLines", bool, paramBoolean);
/*      */     
/* 1150 */     repaint();
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
/*      */   public boolean getShowHorizontalLines() {
/* 1162 */     return this.showHorizontalLines;
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
/*      */   public boolean getShowVerticalLines() {
/* 1174 */     return this.showVerticalLines;
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
/*      */   public void setAutoResizeMode(int paramInt) {
/* 1201 */     if (paramInt == 0 || paramInt == 1 || paramInt == 2 || paramInt == 3 || paramInt == 4) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1206 */       int i = this.autoResizeMode;
/* 1207 */       this.autoResizeMode = paramInt;
/* 1208 */       resizeAndRepaint();
/* 1209 */       if (this.tableHeader != null) {
/* 1210 */         this.tableHeader.resizeAndRepaint();
/*      */       }
/* 1212 */       firePropertyChange("autoResizeMode", i, this.autoResizeMode);
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
/*      */   public int getAutoResizeMode() {
/* 1226 */     return this.autoResizeMode;
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
/*      */   public void setAutoCreateColumnsFromModel(boolean paramBoolean) {
/* 1242 */     if (this.autoCreateColumnsFromModel != paramBoolean) {
/* 1243 */       boolean bool = this.autoCreateColumnsFromModel;
/* 1244 */       this.autoCreateColumnsFromModel = paramBoolean;
/* 1245 */       if (paramBoolean) {
/* 1246 */         createDefaultColumnsFromModel();
/*      */       }
/* 1248 */       firePropertyChange("autoCreateColumnsFromModel", bool, paramBoolean);
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
/*      */   public boolean getAutoCreateColumnsFromModel() {
/* 1265 */     return this.autoCreateColumnsFromModel;
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
/*      */   public void createDefaultColumnsFromModel() {
/* 1279 */     TableModel tableModel = getModel();
/* 1280 */     if (tableModel != null) {
/*      */       
/* 1282 */       TableColumnModel tableColumnModel = getColumnModel();
/* 1283 */       while (tableColumnModel.getColumnCount() > 0) {
/* 1284 */         tableColumnModel.removeColumn(tableColumnModel.getColumn(0));
/*      */       }
/*      */ 
/*      */       
/* 1288 */       for (byte b = 0; b < tableModel.getColumnCount(); b++) {
/* 1289 */         TableColumn tableColumn = new TableColumn(b);
/* 1290 */         addColumn(tableColumn);
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
/*      */   public void setDefaultRenderer(Class<?> paramClass, TableCellRenderer paramTableCellRenderer) {
/* 1307 */     if (paramTableCellRenderer != null) {
/* 1308 */       this.defaultRenderersByColumnClass.put(paramClass, paramTableCellRenderer);
/*      */     } else {
/*      */       
/* 1311 */       this.defaultRenderersByColumnClass.remove(paramClass);
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
/*      */   public TableCellRenderer getDefaultRenderer(Class<?> paramClass) {
/* 1331 */     if (paramClass == null) {
/* 1332 */       return null;
/*      */     }
/*      */     
/* 1335 */     Object object = this.defaultRenderersByColumnClass.get(paramClass);
/* 1336 */     if (object != null) {
/* 1337 */       return (TableCellRenderer)object;
/*      */     }
/*      */     
/* 1340 */     Class<?> clazz = paramClass.getSuperclass();
/* 1341 */     if (clazz == null && paramClass != Object.class) {
/* 1342 */       clazz = Object.class;
/*      */     }
/* 1344 */     return getDefaultRenderer(clazz);
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
/*      */   public void setDefaultEditor(Class<?> paramClass, TableCellEditor paramTableCellEditor) {
/* 1365 */     if (paramTableCellEditor != null) {
/* 1366 */       this.defaultEditorsByColumnClass.put(paramClass, paramTableCellEditor);
/*      */     } else {
/*      */       
/* 1369 */       this.defaultEditorsByColumnClass.remove(paramClass);
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
/*      */   public TableCellEditor getDefaultEditor(Class<?> paramClass) {
/* 1388 */     if (paramClass == null) {
/* 1389 */       return null;
/*      */     }
/*      */     
/* 1392 */     Object object = this.defaultEditorsByColumnClass.get(paramClass);
/* 1393 */     if (object != null) {
/* 1394 */       return (TableCellEditor)object;
/*      */     }
/*      */     
/* 1397 */     return getDefaultEditor(paramClass.getSuperclass());
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
/*      */   public void setDragEnabled(boolean paramBoolean) {
/* 1437 */     if (paramBoolean && GraphicsEnvironment.isHeadless()) {
/* 1438 */       throw new HeadlessException();
/*      */     }
/* 1440 */     this.dragEnabled = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDragEnabled() {
/* 1451 */     return this.dragEnabled;
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
/*      */   public final void setDropMode(DropMode paramDropMode) {
/* 1487 */     if (paramDropMode != null) {
/* 1488 */       switch (paramDropMode) {
/*      */         case USE_SELECTION:
/*      */         case ON:
/*      */         case INSERT:
/*      */         case INSERT_ROWS:
/*      */         case INSERT_COLS:
/*      */         case ON_OR_INSERT:
/*      */         case ON_OR_INSERT_ROWS:
/*      */         case ON_OR_INSERT_COLS:
/* 1497 */           this.dropMode = paramDropMode;
/*      */           return;
/*      */       } 
/*      */     
/*      */     }
/* 1502 */     throw new IllegalArgumentException(paramDropMode + ": Unsupported drop mode for table");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final DropMode getDropMode() {
/* 1513 */     return this.dropMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   DropLocation dropLocationForPoint(Point paramPoint) {
/* 1524 */     DropLocation dropLocation = null;
/*      */     
/* 1526 */     int i = rowAtPoint(paramPoint);
/* 1527 */     int j = columnAtPoint(paramPoint);
/*      */     
/* 1529 */     boolean bool1 = (Boolean.TRUE == getClientProperty("Table.isFileList") && SwingUtilities2.pointOutsidePrefSize(this, i, j, paramPoint)) ? true : false;
/*      */     
/* 1531 */     Rectangle rectangle = getCellRect(i, j, true);
/*      */     
/* 1533 */     boolean bool2 = false;
/* 1534 */     boolean bool = getComponentOrientation().isLeftToRight();
/*      */     
/* 1536 */     switch (this.dropMode)
/*      */     { case USE_SELECTION:
/*      */       case ON:
/* 1539 */         if (i == -1 || j == -1 || bool1) {
/* 1540 */           dropLocation = new DropLocation(paramPoint, -1, -1, false, false);
/*      */         } else {
/* 1542 */           dropLocation = new DropLocation(paramPoint, i, j, false, false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1696 */         return dropLocation;case INSERT: if (i == -1 && j == -1) { dropLocation = new DropLocation(paramPoint, 0, 0, true, true); } else { SwingUtilities2.Section section = SwingUtilities2.liesInHorizontal(rectangle, paramPoint, bool, true); if (i == -1) { if (section == SwingUtilities2.Section.LEADING) { dropLocation = new DropLocation(paramPoint, getRowCount(), j, true, true); } else if (section == SwingUtilities2.Section.TRAILING) { dropLocation = new DropLocation(paramPoint, getRowCount(), j + 1, true, true); } else { dropLocation = new DropLocation(paramPoint, getRowCount(), j, true, false); }  } else if (section == SwingUtilities2.Section.LEADING || section == SwingUtilities2.Section.TRAILING) { SwingUtilities2.Section section1 = SwingUtilities2.liesInVertical(rectangle, paramPoint, true); if (section1 == SwingUtilities2.Section.LEADING) { bool2 = true; } else if (section1 == SwingUtilities2.Section.TRAILING) { i++; bool2 = true; }  dropLocation = new DropLocation(paramPoint, i, (section == SwingUtilities2.Section.TRAILING) ? (j + 1) : j, bool2, true); } else { if (SwingUtilities2.liesInVertical(rectangle, paramPoint, false) == SwingUtilities2.Section.TRAILING) i++;  dropLocation = new DropLocation(paramPoint, i, j, true, false); }  }  return dropLocation;case INSERT_ROWS: if (i == -1 && j == -1) { dropLocation = new DropLocation(paramPoint, -1, -1, false, false); } else if (i == -1) { dropLocation = new DropLocation(paramPoint, getRowCount(), j, true, false); } else { if (SwingUtilities2.liesInVertical(rectangle, paramPoint, false) == SwingUtilities2.Section.TRAILING) i++;  dropLocation = new DropLocation(paramPoint, i, j, true, false); }  return dropLocation;case ON_OR_INSERT_ROWS: if (i == -1 && j == -1) { dropLocation = new DropLocation(paramPoint, -1, -1, false, false); } else if (i == -1) { dropLocation = new DropLocation(paramPoint, getRowCount(), j, true, false); } else { SwingUtilities2.Section section = SwingUtilities2.liesInVertical(rectangle, paramPoint, true); if (section == SwingUtilities2.Section.LEADING) { bool2 = true; } else if (section == SwingUtilities2.Section.TRAILING) { i++; bool2 = true; }  dropLocation = new DropLocation(paramPoint, i, j, bool2, false); }  return dropLocation;case INSERT_COLS: if (i == -1) { dropLocation = new DropLocation(paramPoint, -1, -1, false, false); } else if (j == -1) { dropLocation = new DropLocation(paramPoint, getColumnCount(), j, false, true); } else { if (SwingUtilities2.liesInHorizontal(rectangle, paramPoint, bool, false) == SwingUtilities2.Section.TRAILING) j++;  dropLocation = new DropLocation(paramPoint, i, j, false, true); }  return dropLocation;case ON_OR_INSERT_COLS: if (i == -1) { dropLocation = new DropLocation(paramPoint, -1, -1, false, false); } else if (j == -1) { dropLocation = new DropLocation(paramPoint, i, getColumnCount(), false, true); } else { SwingUtilities2.Section section = SwingUtilities2.liesInHorizontal(rectangle, paramPoint, bool, true); if (section == SwingUtilities2.Section.LEADING) { bool2 = true; } else if (section == SwingUtilities2.Section.TRAILING) { j++; bool2 = true; }  dropLocation = new DropLocation(paramPoint, i, j, false, bool2); }  return dropLocation;case ON_OR_INSERT: if (i == -1 && j == -1) { dropLocation = new DropLocation(paramPoint, 0, 0, true, true); } else { SwingUtilities2.Section section = SwingUtilities2.liesInHorizontal(rectangle, paramPoint, bool, true); if (i == -1) { if (section == SwingUtilities2.Section.LEADING) { dropLocation = new DropLocation(paramPoint, getRowCount(), j, true, true); } else if (section == SwingUtilities2.Section.TRAILING) { dropLocation = new DropLocation(paramPoint, getRowCount(), j + 1, true, true); } else { dropLocation = new DropLocation(paramPoint, getRowCount(), j, true, false); }  } else { SwingUtilities2.Section section1 = SwingUtilities2.liesInVertical(rectangle, paramPoint, true); if (section1 == SwingUtilities2.Section.LEADING) { bool2 = true; } else if (section1 == SwingUtilities2.Section.TRAILING) { i++; bool2 = true; }  dropLocation = new DropLocation(paramPoint, i, (section == SwingUtilities2.Section.TRAILING) ? (j + 1) : j, bool2, (section != SwingUtilities2.Section.MIDDLE)); }  }  return dropLocation; }  assert false : "Unexpected drop mode"; return dropLocation;
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
/*      */   Object setDropLocation(TransferHandler.DropLocation paramDropLocation, Object paramObject, boolean paramBoolean) {
/* 1736 */     Object object = null;
/* 1737 */     DropLocation dropLocation1 = (DropLocation)paramDropLocation;
/*      */     
/* 1739 */     if (this.dropMode == DropMode.USE_SELECTION) {
/* 1740 */       if (dropLocation1 == null) {
/* 1741 */         if (!paramBoolean && paramObject != null) {
/* 1742 */           clearSelection();
/*      */           
/* 1744 */           int[] arrayOfInt1 = ((int[][])paramObject)[0];
/* 1745 */           int[] arrayOfInt2 = ((int[][])paramObject)[1];
/* 1746 */           int[] arrayOfInt3 = ((int[][])paramObject)[2];
/*      */           
/* 1748 */           for (int i : arrayOfInt1) {
/* 1749 */             addRowSelectionInterval(i, i);
/*      */           }
/*      */           
/* 1752 */           for (int i : arrayOfInt2) {
/* 1753 */             addColumnSelectionInterval(i, i);
/*      */           }
/*      */           
/* 1756 */           SwingUtilities2.setLeadAnchorWithoutSelection(
/* 1757 */               getSelectionModel(), arrayOfInt3[1], arrayOfInt3[0]);
/*      */           
/* 1759 */           SwingUtilities2.setLeadAnchorWithoutSelection(
/* 1760 */               getColumnModel().getSelectionModel(), arrayOfInt3[3], arrayOfInt3[2]);
/*      */         } 
/*      */       } else {
/*      */         
/* 1764 */         if (this.dropLocation == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1774 */           object = new int[][] { getSelectedRows(), getSelectedColumns(), { getAdjustedIndex(getSelectionModel().getAnchorSelectionIndex(), true), getAdjustedIndex(getSelectionModel().getLeadSelectionIndex(), true), getAdjustedIndex(getColumnModel().getSelectionModel().getAnchorSelectionIndex(), false), getAdjustedIndex(getColumnModel().getSelectionModel()
/* 1775 */                   .getLeadSelectionIndex(), false) } };
/*      */         } else {
/* 1777 */           object = paramObject;
/*      */         } 
/*      */         
/* 1780 */         if (dropLocation1.getRow() == -1) {
/* 1781 */           clearSelectionAndLeadAnchor();
/*      */         } else {
/* 1783 */           setRowSelectionInterval(dropLocation1.getRow(), dropLocation1
/* 1784 */               .getRow());
/* 1785 */           setColumnSelectionInterval(dropLocation1.getColumn(), dropLocation1
/* 1786 */               .getColumn());
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1791 */     DropLocation dropLocation2 = this.dropLocation;
/* 1792 */     this.dropLocation = dropLocation1;
/* 1793 */     firePropertyChange("dropLocation", dropLocation2, this.dropLocation);
/*      */     
/* 1795 */     return object;
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
/*      */   public final DropLocation getDropLocation() {
/* 1817 */     return this.dropLocation;
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
/*      */   public void setAutoCreateRowSorter(boolean paramBoolean) {
/* 1842 */     boolean bool = this.autoCreateRowSorter;
/* 1843 */     this.autoCreateRowSorter = paramBoolean;
/* 1844 */     if (paramBoolean) {
/* 1845 */       setRowSorter(new TableRowSorter<>(getModel()));
/*      */     }
/* 1847 */     firePropertyChange("autoCreateRowSorter", bool, paramBoolean);
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
/*      */   public boolean getAutoCreateRowSorter() {
/* 1861 */     return this.autoCreateRowSorter;
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
/*      */   public void setUpdateSelectionOnSort(boolean paramBoolean) {
/* 1878 */     if (this.updateSelectionOnSort != paramBoolean) {
/* 1879 */       this.updateSelectionOnSort = paramBoolean;
/* 1880 */       firePropertyChange("updateSelectionOnSort", !paramBoolean, paramBoolean);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUpdateSelectionOnSort() {
/* 1891 */     return this.updateSelectionOnSort;
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
/*      */   public void setRowSorter(RowSorter<? extends TableModel> paramRowSorter) {
/* 1917 */     RowSorter<? extends TableModel> rowSorter = null;
/* 1918 */     if (this.sortManager != null) {
/* 1919 */       rowSorter = this.sortManager.sorter;
/* 1920 */       this.sortManager.dispose();
/* 1921 */       this.sortManager = null;
/*      */     } 
/* 1923 */     this.rowModel = null;
/* 1924 */     clearSelectionAndLeadAnchor();
/* 1925 */     if (paramRowSorter != null) {
/* 1926 */       this.sortManager = new SortManager(paramRowSorter);
/*      */     }
/* 1928 */     resizeAndRepaint();
/* 1929 */     firePropertyChange("rowSorter", rowSorter, paramRowSorter);
/* 1930 */     firePropertyChange("sorter", rowSorter, paramRowSorter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RowSorter<? extends TableModel> getRowSorter() {
/* 1940 */     return (this.sortManager != null) ? this.sortManager.sorter : null;
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
/*      */   public void setSelectionMode(int paramInt) {
/* 1973 */     clearSelection();
/* 1974 */     getSelectionModel().setSelectionMode(paramInt);
/* 1975 */     getColumnModel().getSelectionModel().setSelectionMode(paramInt);
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
/*      */   public void setRowSelectionAllowed(boolean paramBoolean) {
/* 1989 */     boolean bool = this.rowSelectionAllowed;
/* 1990 */     this.rowSelectionAllowed = paramBoolean;
/* 1991 */     if (bool != paramBoolean) {
/* 1992 */       repaint();
/*      */     }
/* 1994 */     firePropertyChange("rowSelectionAllowed", bool, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getRowSelectionAllowed() {
/* 2004 */     return this.rowSelectionAllowed;
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
/*      */   public void setColumnSelectionAllowed(boolean paramBoolean) {
/* 2018 */     boolean bool = this.columnModel.getColumnSelectionAllowed();
/* 2019 */     this.columnModel.setColumnSelectionAllowed(paramBoolean);
/* 2020 */     if (bool != paramBoolean) {
/* 2021 */       repaint();
/*      */     }
/* 2023 */     firePropertyChange("columnSelectionAllowed", bool, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getColumnSelectionAllowed() {
/* 2033 */     return this.columnModel.getColumnSelectionAllowed();
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
/*      */   public void setCellSelectionEnabled(boolean paramBoolean) {
/* 2057 */     setRowSelectionAllowed(paramBoolean);
/* 2058 */     setColumnSelectionAllowed(paramBoolean);
/* 2059 */     boolean bool = this.cellSelectionEnabled;
/* 2060 */     this.cellSelectionEnabled = paramBoolean;
/* 2061 */     firePropertyChange("cellSelectionEnabled", bool, paramBoolean);
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
/*      */   public boolean getCellSelectionEnabled() {
/* 2074 */     return (getRowSelectionAllowed() && getColumnSelectionAllowed());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void selectAll() {
/* 2082 */     if (isEditing()) {
/* 2083 */       removeEditor();
/*      */     }
/* 2085 */     if (getRowCount() > 0 && getColumnCount() > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2090 */       ListSelectionModel listSelectionModel = this.selectionModel;
/* 2091 */       listSelectionModel.setValueIsAdjusting(true);
/* 2092 */       int i = getAdjustedIndex(listSelectionModel.getLeadSelectionIndex(), true);
/* 2093 */       int j = getAdjustedIndex(listSelectionModel.getAnchorSelectionIndex(), true);
/*      */       
/* 2095 */       setRowSelectionInterval(0, getRowCount() - 1);
/*      */ 
/*      */       
/* 2098 */       SwingUtilities2.setLeadAnchorWithoutSelection(listSelectionModel, i, j);
/*      */       
/* 2100 */       listSelectionModel.setValueIsAdjusting(false);
/*      */       
/* 2102 */       listSelectionModel = this.columnModel.getSelectionModel();
/* 2103 */       listSelectionModel.setValueIsAdjusting(true);
/* 2104 */       i = getAdjustedIndex(listSelectionModel.getLeadSelectionIndex(), false);
/* 2105 */       j = getAdjustedIndex(listSelectionModel.getAnchorSelectionIndex(), false);
/*      */       
/* 2107 */       setColumnSelectionInterval(0, getColumnCount() - 1);
/*      */ 
/*      */       
/* 2110 */       SwingUtilities2.setLeadAnchorWithoutSelection(listSelectionModel, i, j);
/*      */       
/* 2112 */       listSelectionModel.setValueIsAdjusting(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearSelection() {
/* 2120 */     this.selectionModel.clearSelection();
/* 2121 */     this.columnModel.getSelectionModel().clearSelection();
/*      */   }
/*      */   
/*      */   private void clearSelectionAndLeadAnchor() {
/* 2125 */     this.selectionModel.setValueIsAdjusting(true);
/* 2126 */     this.columnModel.getSelectionModel().setValueIsAdjusting(true);
/*      */     
/* 2128 */     clearSelection();
/*      */     
/* 2130 */     this.selectionModel.setAnchorSelectionIndex(-1);
/* 2131 */     this.selectionModel.setLeadSelectionIndex(-1);
/* 2132 */     this.columnModel.getSelectionModel().setAnchorSelectionIndex(-1);
/* 2133 */     this.columnModel.getSelectionModel().setLeadSelectionIndex(-1);
/*      */     
/* 2135 */     this.selectionModel.setValueIsAdjusting(false);
/* 2136 */     this.columnModel.getSelectionModel().setValueIsAdjusting(false);
/*      */   }
/*      */   
/*      */   private int getAdjustedIndex(int paramInt, boolean paramBoolean) {
/* 2140 */     int i = paramBoolean ? getRowCount() : getColumnCount();
/* 2141 */     return (paramInt < i) ? paramInt : -1;
/*      */   }
/*      */   
/*      */   private int boundRow(int paramInt) throws IllegalArgumentException {
/* 2145 */     if (paramInt < 0 || paramInt >= getRowCount()) {
/* 2146 */       throw new IllegalArgumentException("Row index out of range");
/*      */     }
/* 2148 */     return paramInt;
/*      */   }
/*      */   
/*      */   private int boundColumn(int paramInt) {
/* 2152 */     if (paramInt < 0 || paramInt >= getColumnCount()) {
/* 2153 */       throw new IllegalArgumentException("Column index out of range");
/*      */     }
/* 2155 */     return paramInt;
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
/*      */   public void setRowSelectionInterval(int paramInt1, int paramInt2) {
/* 2169 */     this.selectionModel.setSelectionInterval(boundRow(paramInt1), boundRow(paramInt2));
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
/*      */   public void setColumnSelectionInterval(int paramInt1, int paramInt2) {
/* 2183 */     this.columnModel.getSelectionModel().setSelectionInterval(boundColumn(paramInt1), boundColumn(paramInt2));
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
/*      */   public void addRowSelectionInterval(int paramInt1, int paramInt2) {
/* 2196 */     this.selectionModel.addSelectionInterval(boundRow(paramInt1), boundRow(paramInt2));
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
/*      */   public void addColumnSelectionInterval(int paramInt1, int paramInt2) {
/* 2210 */     this.columnModel.getSelectionModel().addSelectionInterval(boundColumn(paramInt1), boundColumn(paramInt2));
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
/*      */   public void removeRowSelectionInterval(int paramInt1, int paramInt2) {
/* 2223 */     this.selectionModel.removeSelectionInterval(boundRow(paramInt1), boundRow(paramInt2));
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
/*      */   public void removeColumnSelectionInterval(int paramInt1, int paramInt2) {
/* 2236 */     this.columnModel.getSelectionModel().removeSelectionInterval(boundColumn(paramInt1), boundColumn(paramInt2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSelectedRow() {
/* 2244 */     return this.selectionModel.getMinSelectionIndex();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSelectedColumn() {
/* 2253 */     return this.columnModel.getSelectionModel().getMinSelectionIndex();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getSelectedRows() {
/* 2264 */     int i = this.selectionModel.getMinSelectionIndex();
/* 2265 */     int j = this.selectionModel.getMaxSelectionIndex();
/*      */     
/* 2267 */     if (i == -1 || j == -1) {
/* 2268 */       return new int[0];
/*      */     }
/*      */     
/* 2271 */     int[] arrayOfInt1 = new int[1 + j - i];
/* 2272 */     byte b = 0;
/* 2273 */     for (int k = i; k <= j; k++) {
/* 2274 */       if (this.selectionModel.isSelectedIndex(k)) {
/* 2275 */         arrayOfInt1[b++] = k;
/*      */       }
/*      */     } 
/* 2278 */     int[] arrayOfInt2 = new int[b];
/* 2279 */     System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, b);
/* 2280 */     return arrayOfInt2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getSelectedColumns() {
/* 2291 */     return this.columnModel.getSelectedColumns();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSelectedRowCount() {
/* 2300 */     int i = this.selectionModel.getMinSelectionIndex();
/* 2301 */     int j = this.selectionModel.getMaxSelectionIndex();
/* 2302 */     byte b = 0;
/*      */     
/* 2304 */     for (int k = i; k <= j; k++) {
/* 2305 */       if (this.selectionModel.isSelectedIndex(k)) {
/* 2306 */         b++;
/*      */       }
/*      */     } 
/* 2309 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSelectedColumnCount() {
/* 2318 */     return this.columnModel.getSelectedColumnCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRowSelected(int paramInt) {
/* 2329 */     return this.selectionModel.isSelectedIndex(paramInt);
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
/*      */   public boolean isColumnSelected(int paramInt) {
/* 2341 */     return this.columnModel.getSelectionModel().isSelectedIndex(paramInt);
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
/*      */   public boolean isCellSelected(int paramInt1, int paramInt2) {
/* 2355 */     if (!getRowSelectionAllowed() && !getColumnSelectionAllowed()) {
/* 2356 */       return false;
/*      */     }
/* 2358 */     return ((!getRowSelectionAllowed() || isRowSelected(paramInt1)) && (
/* 2359 */       !getColumnSelectionAllowed() || isColumnSelected(paramInt2)));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void changeSelectionModel(ListSelectionModel paramListSelectionModel, int paramInt1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt2, boolean paramBoolean4) {
/* 2365 */     if (paramBoolean2) {
/* 2366 */       if (paramBoolean1) {
/* 2367 */         if (paramBoolean4) {
/* 2368 */           paramListSelectionModel.addSelectionInterval(paramInt2, paramInt1);
/*      */         } else {
/* 2370 */           paramListSelectionModel.removeSelectionInterval(paramInt2, paramInt1);
/*      */           
/* 2372 */           if (Boolean.TRUE == getClientProperty("Table.isFileList")) {
/* 2373 */             paramListSelectionModel.addSelectionInterval(paramInt1, paramInt1);
/* 2374 */             paramListSelectionModel.setAnchorSelectionIndex(paramInt2);
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/* 2379 */         paramListSelectionModel.setSelectionInterval(paramInt2, paramInt1);
/*      */       }
/*      */     
/*      */     }
/* 2383 */     else if (paramBoolean1) {
/* 2384 */       if (paramBoolean3) {
/* 2385 */         paramListSelectionModel.removeSelectionInterval(paramInt1, paramInt1);
/*      */       } else {
/*      */         
/* 2388 */         paramListSelectionModel.addSelectionInterval(paramInt1, paramInt1);
/*      */       } 
/*      */     } else {
/*      */       
/* 2392 */       paramListSelectionModel.setSelectionInterval(paramInt1, paramInt1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void changeSelection(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {
/* 2427 */     ListSelectionModel listSelectionModel1 = getSelectionModel();
/* 2428 */     ListSelectionModel listSelectionModel2 = getColumnModel().getSelectionModel();
/*      */     
/* 2430 */     int i = getAdjustedIndex(listSelectionModel1.getAnchorSelectionIndex(), true);
/* 2431 */     int j = getAdjustedIndex(listSelectionModel2.getAnchorSelectionIndex(), false);
/*      */     
/* 2433 */     boolean bool = true;
/*      */     
/* 2435 */     if (i == -1) {
/* 2436 */       if (getRowCount() > 0) {
/* 2437 */         i = 0;
/*      */       }
/* 2439 */       bool = false;
/*      */     } 
/*      */     
/* 2442 */     if (j == -1) {
/* 2443 */       if (getColumnCount() > 0) {
/* 2444 */         j = 0;
/*      */       }
/* 2446 */       bool = false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2456 */     boolean bool1 = isCellSelected(paramInt1, paramInt2);
/* 2457 */     bool = (bool && isCellSelected(i, j)) ? true : false;
/*      */     
/* 2459 */     changeSelectionModel(listSelectionModel2, paramInt2, paramBoolean1, paramBoolean2, bool1, j, bool);
/*      */     
/* 2461 */     changeSelectionModel(listSelectionModel1, paramInt1, paramBoolean1, paramBoolean2, bool1, i, bool);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2467 */     if (getAutoscrolls()) {
/* 2468 */       Rectangle rectangle = getCellRect(paramInt1, paramInt2, false);
/* 2469 */       if (rectangle != null) {
/* 2470 */         scrollRectToVisible(rectangle);
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
/*      */   public Color getSelectionForeground() {
/* 2483 */     return this.selectionForeground;
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
/*      */   public void setSelectionForeground(Color paramColor) {
/* 2508 */     Color color = this.selectionForeground;
/* 2509 */     this.selectionForeground = paramColor;
/* 2510 */     firePropertyChange("selectionForeground", color, paramColor);
/* 2511 */     repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getSelectionBackground() {
/* 2522 */     return this.selectionBackground;
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
/*      */   public void setSelectionBackground(Color paramColor) {
/* 2546 */     Color color = this.selectionBackground;
/* 2547 */     this.selectionBackground = paramColor;
/* 2548 */     firePropertyChange("selectionBackground", color, paramColor);
/* 2549 */     repaint();
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
/*      */   public TableColumn getColumn(Object paramObject) {
/* 2563 */     TableColumnModel tableColumnModel = getColumnModel();
/* 2564 */     int i = tableColumnModel.getColumnIndex(paramObject);
/* 2565 */     return tableColumnModel.getColumn(i);
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
/*      */   public int convertColumnIndexToModel(int paramInt) {
/* 2585 */     return SwingUtilities2.convertColumnIndexToModel(
/* 2586 */         getColumnModel(), paramInt);
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
/*      */   public int convertColumnIndexToView(int paramInt) {
/* 2603 */     return SwingUtilities2.convertColumnIndexToView(
/* 2604 */         getColumnModel(), paramInt);
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
/*      */   public int convertRowIndexToView(int paramInt) {
/* 2621 */     RowSorter<? extends TableModel> rowSorter = getRowSorter();
/* 2622 */     if (rowSorter != null) {
/* 2623 */       return rowSorter.convertRowIndexToView(paramInt);
/*      */     }
/* 2625 */     return paramInt;
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
/*      */   public int convertRowIndexToModel(int paramInt) {
/* 2643 */     RowSorter<? extends TableModel> rowSorter = getRowSorter();
/* 2644 */     if (rowSorter != null) {
/* 2645 */       return rowSorter.convertRowIndexToModel(paramInt);
/*      */     }
/* 2647 */     return paramInt;
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
/*      */   public int getRowCount() {
/* 2661 */     RowSorter<? extends TableModel> rowSorter = getRowSorter();
/* 2662 */     if (rowSorter != null) {
/* 2663 */       return rowSorter.getViewRowCount();
/*      */     }
/* 2665 */     return getModel().getRowCount();
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
/*      */   public int getColumnCount() {
/* 2677 */     return getColumnModel().getColumnCount();
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
/*      */   public String getColumnName(int paramInt) {
/* 2689 */     return getModel().getColumnName(convertColumnIndexToModel(paramInt));
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
/*      */   public Class<?> getColumnClass(int paramInt) {
/* 2701 */     return getModel().getColumnClass(convertColumnIndexToModel(paramInt));
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
/*      */   public Object getValueAt(int paramInt1, int paramInt2) {
/* 2720 */     return getModel().getValueAt(convertRowIndexToModel(paramInt1), 
/* 2721 */         convertColumnIndexToModel(paramInt2));
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
/*      */   public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
/* 2744 */     getModel().setValueAt(paramObject, convertRowIndexToModel(paramInt1), 
/* 2745 */         convertColumnIndexToModel(paramInt2));
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
/*      */   public boolean isCellEditable(int paramInt1, int paramInt2) {
/* 2768 */     return getModel().isCellEditable(convertRowIndexToModel(paramInt1), 
/* 2769 */         convertColumnIndexToModel(paramInt2));
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
/*      */   public void addColumn(TableColumn paramTableColumn) {
/* 2802 */     if (paramTableColumn.getHeaderValue() == null) {
/* 2803 */       int i = paramTableColumn.getModelIndex();
/* 2804 */       String str = getModel().getColumnName(i);
/* 2805 */       paramTableColumn.setHeaderValue(str);
/*      */     } 
/* 2807 */     getColumnModel().addColumn(paramTableColumn);
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
/*      */   public void removeColumn(TableColumn paramTableColumn) {
/* 2820 */     getColumnModel().removeColumn(paramTableColumn);
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
/*      */   public void moveColumn(int paramInt1, int paramInt2) {
/* 2833 */     getColumnModel().moveColumn(paramInt1, paramInt2);
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
/*      */   public int columnAtPoint(Point paramPoint) {
/* 2852 */     int i = paramPoint.x;
/* 2853 */     if (!getComponentOrientation().isLeftToRight()) {
/* 2854 */       i = getWidth() - i - 1;
/*      */     }
/* 2856 */     return getColumnModel().getColumnIndexAtX(i);
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
/*      */   public int rowAtPoint(Point paramPoint) {
/* 2871 */     int i = paramPoint.y;
/* 2872 */     int j = (this.rowModel == null) ? (i / getRowHeight()) : this.rowModel.getIndex(i);
/* 2873 */     if (j < 0) {
/* 2874 */       return -1;
/*      */     }
/* 2876 */     if (j >= getRowCount()) {
/* 2877 */       return -1;
/*      */     }
/*      */     
/* 2880 */     return j;
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
/*      */   public Rectangle getCellRect(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 2929 */     Rectangle rectangle = new Rectangle();
/* 2930 */     boolean bool = true;
/* 2931 */     if (paramInt1 < 0) {
/*      */       
/* 2933 */       bool = false;
/*      */     }
/* 2935 */     else if (paramInt1 >= getRowCount()) {
/* 2936 */       rectangle.y = getHeight();
/* 2937 */       bool = false;
/*      */     } else {
/*      */       
/* 2940 */       rectangle.height = getRowHeight(paramInt1);
/* 2941 */       rectangle.y = (this.rowModel == null) ? (paramInt1 * rectangle.height) : this.rowModel.getPosition(paramInt1);
/*      */     } 
/*      */     
/* 2944 */     if (paramInt2 < 0) {
/* 2945 */       if (!getComponentOrientation().isLeftToRight()) {
/* 2946 */         rectangle.x = getWidth();
/*      */       }
/*      */       
/* 2949 */       bool = false;
/*      */     }
/* 2951 */     else if (paramInt2 >= getColumnCount()) {
/* 2952 */       if (getComponentOrientation().isLeftToRight()) {
/* 2953 */         rectangle.x = getWidth();
/*      */       }
/*      */       
/* 2956 */       bool = false;
/*      */     } else {
/*      */       
/* 2959 */       TableColumnModel tableColumnModel = getColumnModel();
/* 2960 */       if (getComponentOrientation().isLeftToRight()) {
/* 2961 */         for (byte b = 0; b < paramInt2; b++) {
/* 2962 */           rectangle.x += tableColumnModel.getColumn(b).getWidth();
/*      */         }
/*      */       } else {
/* 2965 */         for (int i = tableColumnModel.getColumnCount() - 1; i > paramInt2; i--) {
/* 2966 */           rectangle.x += tableColumnModel.getColumn(i).getWidth();
/*      */         }
/*      */       } 
/* 2969 */       rectangle.width = tableColumnModel.getColumn(paramInt2).getWidth();
/*      */     } 
/*      */     
/* 2972 */     if (bool && !paramBoolean) {
/*      */ 
/*      */       
/* 2975 */       int i = Math.min(getRowMargin(), rectangle.height);
/* 2976 */       int j = Math.min(getColumnModel().getColumnMargin(), rectangle.width);
/*      */       
/* 2978 */       rectangle.setBounds(rectangle.x + j / 2, rectangle.y + i / 2, rectangle.width - j, rectangle.height - i);
/*      */     } 
/* 2980 */     return rectangle;
/*      */   }
/*      */   
/*      */   private int viewIndexForColumn(TableColumn paramTableColumn) {
/* 2984 */     TableColumnModel tableColumnModel = getColumnModel();
/* 2985 */     for (byte b = 0; b < tableColumnModel.getColumnCount(); b++) {
/* 2986 */       if (tableColumnModel.getColumn(b) == paramTableColumn) {
/* 2987 */         return b;
/*      */       }
/*      */     } 
/* 2990 */     return -1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void doLayout() {
/* 3129 */     TableColumn tableColumn = getResizingColumn();
/* 3130 */     if (tableColumn == null) {
/* 3131 */       setWidthsFromPreferredWidths(false);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 3140 */       int i = viewIndexForColumn(tableColumn);
/* 3141 */       int j = getWidth() - getColumnModel().getTotalColumnWidth();
/* 3142 */       accommodateDelta(i, j);
/* 3143 */       j = getWidth() - getColumnModel().getTotalColumnWidth();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3155 */       if (j != 0) {
/* 3156 */         tableColumn.setWidth(tableColumn.getWidth() + j);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3165 */       setWidthsFromPreferredWidths(true);
/*      */     } 
/*      */     
/* 3168 */     super.doLayout();
/*      */   }
/*      */   
/*      */   private TableColumn getResizingColumn() {
/* 3172 */     return (this.tableHeader == null) ? null : this.tableHeader
/* 3173 */       .getResizingColumn();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void sizeColumnsToFit(boolean paramBoolean) {
/* 3184 */     int i = this.autoResizeMode;
/* 3185 */     setAutoResizeMode(paramBoolean ? 3 : 4);
/*      */     
/* 3187 */     sizeColumnsToFit(-1);
/* 3188 */     setAutoResizeMode(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sizeColumnsToFit(int paramInt) {
/* 3199 */     if (paramInt == -1) {
/* 3200 */       setWidthsFromPreferredWidths(false);
/*      */     
/*      */     }
/* 3203 */     else if (this.autoResizeMode == 0) {
/* 3204 */       TableColumn tableColumn = getColumnModel().getColumn(paramInt);
/* 3205 */       tableColumn.setPreferredWidth(tableColumn.getWidth());
/*      */     } else {
/*      */       
/* 3208 */       int i = getWidth() - getColumnModel().getTotalColumnWidth();
/* 3209 */       accommodateDelta(paramInt, i);
/* 3210 */       setWidthsFromPreferredWidths(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setWidthsFromPreferredWidths(final boolean inverse) {
/* 3216 */     int i = getWidth();
/* 3217 */     int j = (getPreferredSize()).width;
/* 3218 */     int k = !inverse ? i : j;
/*      */     
/* 3220 */     final TableColumnModel cm = this.columnModel;
/* 3221 */     Resizable3 resizable3 = new Resizable3() {
/* 3222 */         public int getElementCount() { return cm.getColumnCount(); }
/* 3223 */         public int getLowerBoundAt(int param1Int) { return cm.getColumn(param1Int).getMinWidth(); } public int getUpperBoundAt(int param1Int) {
/* 3224 */           return cm.getColumn(param1Int).getMaxWidth();
/*      */         } public int getMidPointAt(int param1Int) {
/* 3226 */           if (!inverse) {
/* 3227 */             return cm.getColumn(param1Int).getPreferredWidth();
/*      */           }
/*      */           
/* 3230 */           return cm.getColumn(param1Int).getWidth();
/*      */         }
/*      */         
/*      */         public void setSizeAt(int param1Int1, int param1Int2) {
/* 3234 */           if (!inverse) {
/* 3235 */             cm.getColumn(param1Int2).setWidth(param1Int1);
/*      */           } else {
/*      */             
/* 3238 */             cm.getColumn(param1Int2).setPreferredWidth(param1Int1);
/*      */           } 
/*      */         }
/*      */       };
/*      */     
/* 3243 */     adjustSizes(k, resizable3, inverse);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void accommodateDelta(int paramInt1, int paramInt2) {
/* 3249 */     int k, i = getColumnCount();
/* 3250 */     int j = paramInt1;
/*      */ 
/*      */ 
/*      */     
/* 3254 */     switch (this.autoResizeMode) {
/*      */       case 1:
/* 3256 */         j++;
/* 3257 */         k = Math.min(j + 1, i); break;
/*      */       case 2:
/* 3259 */         j++;
/* 3260 */         k = i; break;
/*      */       case 3:
/* 3262 */         j = i - 1;
/* 3263 */         k = j + 1; break;
/*      */       case 4:
/* 3265 */         j = 0;
/* 3266 */         k = i;
/*      */         break;
/*      */       default:
/*      */         return;
/*      */     } 
/* 3271 */     final int start = j;
/* 3272 */     final int end = k;
/* 3273 */     final TableColumnModel cm = this.columnModel;
/* 3274 */     Resizable3 resizable3 = new Resizable3() {
/* 3275 */         public int getElementCount() { return end - start; }
/* 3276 */         public int getLowerBoundAt(int param1Int) { return cm.getColumn(param1Int + start).getMinWidth(); }
/* 3277 */         public int getUpperBoundAt(int param1Int) { return cm.getColumn(param1Int + start).getMaxWidth(); }
/* 3278 */         public int getMidPointAt(int param1Int) { return cm.getColumn(param1Int + start).getWidth(); } public void setSizeAt(int param1Int1, int param1Int2) {
/* 3279 */           cm.getColumn(param1Int2 + start).setWidth(param1Int1);
/*      */         }
/*      */       };
/* 3282 */     int i1 = 0;
/* 3283 */     for (int i2 = j; i2 < k; i2++) {
/* 3284 */       TableColumn tableColumn = this.columnModel.getColumn(i2);
/* 3285 */       int i3 = tableColumn.getWidth();
/* 3286 */       i1 += i3;
/*      */     } 
/*      */     
/* 3289 */     adjustSizes((i1 + paramInt2), resizable3, false);
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
/*      */   private void adjustSizes(long paramLong, final Resizable3 r, boolean paramBoolean) {
/*      */     Resizable2 resizable2;
/* 3305 */     int i = r.getElementCount();
/* 3306 */     long l = 0L;
/* 3307 */     for (byte b = 0; b < i; b++) {
/* 3308 */       l += r.getMidPointAt(b);
/*      */     }
/*      */     
/* 3311 */     if (((paramLong < l) ? true : false) == (!paramBoolean ? true : false)) {
/* 3312 */       resizable2 = new Resizable2() {
/* 3313 */           public int getElementCount() { return r.getElementCount(); }
/* 3314 */           public int getLowerBoundAt(int param1Int) { return r.getLowerBoundAt(param1Int); }
/* 3315 */           public int getUpperBoundAt(int param1Int) { return r.getMidPointAt(param1Int); } public void setSizeAt(int param1Int1, int param1Int2) {
/* 3316 */             r.setSizeAt(param1Int1, param1Int2);
/*      */           }
/*      */         };
/*      */     } else {
/*      */       
/* 3321 */       resizable2 = new Resizable2() {
/* 3322 */           public int getElementCount() { return r.getElementCount(); }
/* 3323 */           public int getLowerBoundAt(int param1Int) { return r.getMidPointAt(param1Int); }
/* 3324 */           public int getUpperBoundAt(int param1Int) { return r.getUpperBoundAt(param1Int); } public void setSizeAt(int param1Int1, int param1Int2) {
/* 3325 */             r.setSizeAt(param1Int1, param1Int2);
/*      */           }
/*      */         };
/*      */     } 
/* 3329 */     adjustSizes(paramLong, resizable2, !paramBoolean);
/*      */   }
/*      */   
/*      */   private void adjustSizes(long paramLong, Resizable2 paramResizable2, boolean paramBoolean) {
/* 3333 */     long l1 = 0L;
/* 3334 */     long l2 = 0L; byte b;
/* 3335 */     for (b = 0; b < paramResizable2.getElementCount(); b++) {
/* 3336 */       l1 += paramResizable2.getLowerBoundAt(b);
/* 3337 */       l2 += paramResizable2.getUpperBoundAt(b);
/*      */     } 
/*      */     
/* 3340 */     if (paramBoolean) {
/* 3341 */       paramLong = Math.min(Math.max(l1, paramLong), l2);
/*      */     }
/*      */     
/* 3344 */     for (b = 0; b < paramResizable2.getElementCount(); b++) {
/* 3345 */       int k, i = paramResizable2.getLowerBoundAt(b);
/* 3346 */       int j = paramResizable2.getUpperBoundAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3351 */       if (l1 == l2) {
/* 3352 */         k = i;
/*      */       } else {
/*      */         
/* 3355 */         double d = (paramLong - l1) / (l2 - l1);
/* 3356 */         k = (int)Math.round(i + d * (j - i));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 3361 */       paramResizable2.setSizeAt(k, b);
/* 3362 */       paramLong -= k;
/* 3363 */       l1 -= i;
/* 3364 */       l2 -= j;
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
/*      */   public String getToolTipText(MouseEvent paramMouseEvent) {
/* 3385 */     String str = null;
/* 3386 */     Point point = paramMouseEvent.getPoint();
/*      */ 
/*      */     
/* 3389 */     int i = columnAtPoint(point);
/* 3390 */     int j = rowAtPoint(point);
/*      */     
/* 3392 */     if (i != -1 && j != -1) {
/* 3393 */       TableCellRenderer tableCellRenderer = getCellRenderer(j, i);
/* 3394 */       Component component = prepareRenderer(tableCellRenderer, j, i);
/*      */ 
/*      */ 
/*      */       
/* 3398 */       if (component instanceof JComponent) {
/*      */         
/* 3400 */         Rectangle rectangle = getCellRect(j, i, false);
/* 3401 */         point.translate(-rectangle.x, -rectangle.y);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3408 */         MouseEvent mouseEvent = new MouseEvent(component, paramMouseEvent.getID(), paramMouseEvent.getWhen(), paramMouseEvent.getModifiers(), point.x, point.y, paramMouseEvent.getXOnScreen(), paramMouseEvent.getYOnScreen(), paramMouseEvent.getClickCount(), paramMouseEvent.isPopupTrigger(), 0);
/*      */         
/* 3410 */         AWTAccessor.MouseEventAccessor mouseEventAccessor = AWTAccessor.getMouseEventAccessor();
/* 3411 */         mouseEventAccessor.setCausedByTouchEvent(mouseEvent, mouseEventAccessor
/* 3412 */             .isCausedByTouchEvent(paramMouseEvent));
/*      */         
/* 3414 */         str = ((JComponent)component).getToolTipText(mouseEvent);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3419 */     if (str == null) {
/* 3420 */       str = getToolTipText();
/*      */     }
/* 3422 */     return str;
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
/*      */   public void setSurrendersFocusOnKeystroke(boolean paramBoolean) {
/* 3445 */     this.surrendersFocusOnKeystroke = paramBoolean;
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
/*      */   public boolean getSurrendersFocusOnKeystroke() {
/* 3460 */     return this.surrendersFocusOnKeystroke;
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
/*      */   public boolean editCellAt(int paramInt1, int paramInt2) {
/* 3476 */     return editCellAt(paramInt1, paramInt2, (EventObject)null);
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
/*      */   public boolean editCellAt(int paramInt1, int paramInt2, EventObject paramEventObject) {
/* 3497 */     if (this.cellEditor != null && !this.cellEditor.stopCellEditing()) {
/* 3498 */       return false;
/*      */     }
/*      */     
/* 3501 */     if (paramInt1 < 0 || paramInt1 >= getRowCount() || paramInt2 < 0 || paramInt2 >= 
/* 3502 */       getColumnCount()) {
/* 3503 */       return false;
/*      */     }
/*      */     
/* 3506 */     if (!isCellEditable(paramInt1, paramInt2)) {
/* 3507 */       return false;
/*      */     }
/* 3509 */     if (this.editorRemover == null) {
/*      */       
/* 3511 */       KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/* 3512 */       this.editorRemover = new CellEditorRemover(keyboardFocusManager);
/* 3513 */       keyboardFocusManager.addPropertyChangeListener("permanentFocusOwner", this.editorRemover);
/*      */     } 
/*      */     
/* 3516 */     TableCellEditor tableCellEditor = getCellEditor(paramInt1, paramInt2);
/* 3517 */     if (tableCellEditor != null && tableCellEditor.isCellEditable(paramEventObject)) {
/* 3518 */       this.editorComp = prepareEditor(tableCellEditor, paramInt1, paramInt2);
/* 3519 */       if (this.editorComp == null) {
/* 3520 */         removeEditor();
/* 3521 */         return false;
/*      */       } 
/* 3523 */       this.editorComp.setBounds(getCellRect(paramInt1, paramInt2, false));
/* 3524 */       add(this.editorComp);
/* 3525 */       this.editorComp.validate();
/* 3526 */       this.editorComp.repaint();
/*      */       
/* 3528 */       setCellEditor(tableCellEditor);
/* 3529 */       setEditingRow(paramInt1);
/* 3530 */       setEditingColumn(paramInt2);
/* 3531 */       tableCellEditor.addCellEditorListener(this);
/*      */       
/* 3533 */       return true;
/*      */     } 
/* 3535 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEditing() {
/* 3546 */     return (this.cellEditor != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component getEditorComponent() {
/* 3556 */     return this.editorComp;
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
/*      */   public int getEditingColumn() {
/* 3568 */     return this.editingColumn;
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
/*      */   public int getEditingRow() {
/* 3580 */     return this.editingRow;
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
/*      */   public TableUI getUI() {
/* 3593 */     return (TableUI)this.ui;
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
/*      */   public void setUI(TableUI paramTableUI) {
/* 3608 */     if (this.ui != paramTableUI) {
/* 3609 */       setUI(paramTableUI);
/* 3610 */       repaint();
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
/*      */   public void updateUI() {
/* 3623 */     TableColumnModel tableColumnModel = getColumnModel();
/* 3624 */     for (byte b = 0; b < tableColumnModel.getColumnCount(); b++) {
/* 3625 */       TableColumn tableColumn = tableColumnModel.getColumn(b);
/* 3626 */       SwingUtilities.updateRendererOrEditorUI(tableColumn.getCellRenderer());
/* 3627 */       SwingUtilities.updateRendererOrEditorUI(tableColumn.getCellEditor());
/* 3628 */       SwingUtilities.updateRendererOrEditorUI(tableColumn.getHeaderRenderer());
/*      */     } 
/*      */ 
/*      */     
/* 3632 */     Enumeration enumeration1 = this.defaultRenderersByColumnClass.elements();
/* 3633 */     while (enumeration1.hasMoreElements()) {
/* 3634 */       SwingUtilities.updateRendererOrEditorUI(enumeration1.nextElement());
/*      */     }
/*      */ 
/*      */     
/* 3638 */     Enumeration enumeration2 = this.defaultEditorsByColumnClass.elements();
/* 3639 */     while (enumeration2.hasMoreElements()) {
/* 3640 */       SwingUtilities.updateRendererOrEditorUI(enumeration2.nextElement());
/*      */     }
/*      */ 
/*      */     
/* 3644 */     if (this.tableHeader != null && this.tableHeader.getParent() == null) {
/* 3645 */       this.tableHeader.updateUI();
/*      */     }
/*      */ 
/*      */     
/* 3649 */     configureEnclosingScrollPaneUI();
/*      */     
/* 3651 */     setUI((TableUI)UIManager.getUI(this));
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
/*      */   public String getUIClassID() {
/* 3663 */     return "TableUI";
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
/*      */   public void setModel(TableModel paramTableModel) {
/* 3683 */     if (paramTableModel == null) {
/* 3684 */       throw new IllegalArgumentException("Cannot set a null TableModel");
/*      */     }
/* 3686 */     if (this.dataModel != paramTableModel) {
/* 3687 */       TableModel tableModel = this.dataModel;
/* 3688 */       if (tableModel != null) {
/* 3689 */         tableModel.removeTableModelListener(this);
/*      */       }
/* 3691 */       this.dataModel = paramTableModel;
/* 3692 */       paramTableModel.addTableModelListener(this);
/*      */       
/* 3694 */       tableChanged(new TableModelEvent(paramTableModel, -1));
/*      */       
/* 3696 */       firePropertyChange("model", tableModel, paramTableModel);
/*      */       
/* 3698 */       if (getAutoCreateRowSorter()) {
/* 3699 */         setRowSorter(new TableRowSorter<>(paramTableModel));
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
/*      */   public TableModel getModel() {
/* 3712 */     return this.dataModel;
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
/*      */   public void setColumnModel(TableColumnModel paramTableColumnModel) {
/* 3728 */     if (paramTableColumnModel == null) {
/* 3729 */       throw new IllegalArgumentException("Cannot set a null ColumnModel");
/*      */     }
/* 3731 */     TableColumnModel tableColumnModel = this.columnModel;
/* 3732 */     if (paramTableColumnModel != tableColumnModel) {
/* 3733 */       if (tableColumnModel != null) {
/* 3734 */         tableColumnModel.removeColumnModelListener(this);
/*      */       }
/* 3736 */       this.columnModel = paramTableColumnModel;
/* 3737 */       paramTableColumnModel.addColumnModelListener(this);
/*      */ 
/*      */       
/* 3740 */       if (this.tableHeader != null) {
/* 3741 */         this.tableHeader.setColumnModel(paramTableColumnModel);
/*      */       }
/*      */       
/* 3744 */       firePropertyChange("columnModel", tableColumnModel, paramTableColumnModel);
/* 3745 */       resizeAndRepaint();
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
/*      */   public TableColumnModel getColumnModel() {
/* 3757 */     return this.columnModel;
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
/*      */   public void setSelectionModel(ListSelectionModel paramListSelectionModel) {
/* 3772 */     if (paramListSelectionModel == null) {
/* 3773 */       throw new IllegalArgumentException("Cannot set a null SelectionModel");
/*      */     }
/*      */     
/* 3776 */     ListSelectionModel listSelectionModel = this.selectionModel;
/*      */     
/* 3778 */     if (paramListSelectionModel != listSelectionModel) {
/* 3779 */       if (listSelectionModel != null) {
/* 3780 */         listSelectionModel.removeListSelectionListener(this);
/*      */       }
/*      */       
/* 3783 */       this.selectionModel = paramListSelectionModel;
/* 3784 */       paramListSelectionModel.addListSelectionListener(this);
/*      */       
/* 3786 */       firePropertyChange("selectionModel", listSelectionModel, paramListSelectionModel);
/* 3787 */       repaint();
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
/*      */   public ListSelectionModel getSelectionModel() {
/* 3800 */     return this.selectionModel;
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
/*      */   public void sorterChanged(RowSorterEvent paramRowSorterEvent) {
/* 3816 */     if (paramRowSorterEvent.getType() == RowSorterEvent.Type.SORT_ORDER_CHANGED) {
/* 3817 */       JTableHeader jTableHeader = getTableHeader();
/* 3818 */       if (jTableHeader != null) {
/* 3819 */         jTableHeader.repaint();
/*      */       }
/*      */     }
/* 3822 */     else if (paramRowSorterEvent.getType() == RowSorterEvent.Type.SORTED) {
/* 3823 */       this.sorterChanged = true;
/* 3824 */       if (!this.ignoreSortChange) {
/* 3825 */         sortedTableChanged(paramRowSorterEvent, (TableModelEvent)null);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class SortManager
/*      */   {
/*      */     RowSorter<? extends TableModel> sorter;
/*      */ 
/*      */     
/*      */     private ListSelectionModel modelSelection;
/*      */ 
/*      */     
/*      */     private int modelLeadIndex;
/*      */ 
/*      */     
/*      */     private boolean syncingSelection;
/*      */ 
/*      */     
/*      */     private int[] lastModelSelection;
/*      */ 
/*      */     
/*      */     private SizeSequence modelRowSizes;
/*      */ 
/*      */ 
/*      */     
/*      */     SortManager(RowSorter<? extends TableModel> param1RowSorter) {
/* 3855 */       this.sorter = param1RowSorter;
/* 3856 */       param1RowSorter.addRowSorterListener(JTable.this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void dispose() {
/* 3863 */       if (this.sorter != null) {
/* 3864 */         this.sorter.removeRowSorterListener(JTable.this);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setViewRowHeight(int param1Int1, int param1Int2) {
/* 3872 */       if (this.modelRowSizes == null) {
/* 3873 */         this
/* 3874 */           .modelRowSizes = new SizeSequence(JTable.this.getModel().getRowCount(), JTable.this.getRowHeight());
/*      */       }
/* 3876 */       this.modelRowSizes.setSize(JTable.this.convertRowIndexToModel(param1Int1), param1Int2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void allChanged() {
/* 3883 */       this.modelLeadIndex = -1;
/* 3884 */       this.modelSelection = null;
/* 3885 */       this.modelRowSizes = null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void viewSelectionChanged(ListSelectionEvent param1ListSelectionEvent) {
/* 3892 */       if (!this.syncingSelection && this.modelSelection != null) {
/* 3893 */         this.modelSelection = null;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void prepareForChange(RowSorterEvent param1RowSorterEvent, JTable.ModelChange param1ModelChange) {
/* 3904 */       if (JTable.this.getUpdateSelectionOnSort()) {
/* 3905 */         cacheSelection(param1RowSorterEvent, param1ModelChange);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void cacheSelection(RowSorterEvent param1RowSorterEvent, JTable.ModelChange param1ModelChange) {
/* 3914 */       if (param1RowSorterEvent != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3919 */         if (this.modelSelection == null && this.sorter
/* 3920 */           .getViewRowCount() != JTable.this.getModel().getRowCount()) {
/* 3921 */           this.modelSelection = new DefaultListSelectionModel();
/* 3922 */           ListSelectionModel listSelectionModel = JTable.this.getSelectionModel();
/* 3923 */           int i = listSelectionModel.getMinSelectionIndex();
/* 3924 */           int j = listSelectionModel.getMaxSelectionIndex();
/*      */           
/* 3926 */           for (int m = i; m <= j; m++) {
/* 3927 */             if (listSelectionModel.isSelectedIndex(m)) {
/* 3928 */               int n = JTable.this.convertRowIndexToModel(param1RowSorterEvent, m);
/*      */               
/* 3930 */               if (n != -1) {
/* 3931 */                 this.modelSelection.addSelectionInterval(n, n);
/*      */               }
/*      */             } 
/*      */           } 
/*      */           
/* 3936 */           int k = JTable.this.convertRowIndexToModel(param1RowSorterEvent, listSelectionModel
/* 3937 */               .getLeadSelectionIndex());
/* 3938 */           SwingUtilities2.setLeadAnchorWithoutSelection(this.modelSelection, k, k);
/*      */         }
/* 3940 */         else if (this.modelSelection == null) {
/*      */ 
/*      */           
/* 3943 */           cacheModelSelection(param1RowSorterEvent);
/*      */         } 
/* 3945 */       } else if (param1ModelChange.allRowsChanged) {
/*      */         
/* 3947 */         this.modelSelection = null;
/* 3948 */       } else if (this.modelSelection != null) {
/*      */         
/* 3950 */         switch (param1ModelChange.type) {
/*      */           case -1:
/* 3952 */             this.modelSelection.removeIndexInterval(param1ModelChange.startModelIndex, param1ModelChange.endModelIndex);
/*      */             break;
/*      */           
/*      */           case 1:
/* 3956 */             this.modelSelection.insertIndexInterval(param1ModelChange.startModelIndex, param1ModelChange.length, true);
/*      */             break;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       } else {
/* 3966 */         cacheModelSelection(null);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void cacheModelSelection(RowSorterEvent param1RowSorterEvent) {
/* 3971 */       this.lastModelSelection = JTable.this.convertSelectionToModel(param1RowSorterEvent);
/* 3972 */       this.modelLeadIndex = JTable.this.convertRowIndexToModel(param1RowSorterEvent, JTable.this.selectionModel
/* 3973 */           .getLeadSelectionIndex());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void processChange(RowSorterEvent param1RowSorterEvent, JTable.ModelChange param1ModelChange, boolean param1Boolean) {
/* 3984 */       if (param1ModelChange != null) {
/* 3985 */         if (param1ModelChange.allRowsChanged) {
/* 3986 */           this.modelRowSizes = null;
/* 3987 */           JTable.this.rowModel = null;
/* 3988 */         } else if (this.modelRowSizes != null) {
/* 3989 */           if (param1ModelChange.type == 1) {
/* 3990 */             this.modelRowSizes.insertEntries(param1ModelChange.startModelIndex, param1ModelChange.endModelIndex - param1ModelChange.startModelIndex + 1, JTable.this
/*      */ 
/*      */                 
/* 3993 */                 .getRowHeight());
/* 3994 */           } else if (param1ModelChange.type == -1) {
/* 3995 */             this.modelRowSizes.removeEntries(param1ModelChange.startModelIndex, param1ModelChange.endModelIndex - param1ModelChange.startModelIndex + 1);
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 4001 */       if (param1Boolean) {
/* 4002 */         setViewRowHeightsFromModel();
/* 4003 */         restoreSelection(param1ModelChange);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setViewRowHeightsFromModel() {
/* 4012 */       if (this.modelRowSizes != null) {
/* 4013 */         JTable.this.rowModel.setSizes(JTable.this.getRowCount(), JTable.this.getRowHeight());
/* 4014 */         for (int i = JTable.this.getRowCount() - 1; i >= 0; 
/* 4015 */           i--) {
/* 4016 */           int j = JTable.this.convertRowIndexToModel(i);
/* 4017 */           JTable.this.rowModel.setSize(i, this.modelRowSizes
/* 4018 */               .getSize(j));
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void restoreSelection(JTable.ModelChange param1ModelChange) {
/* 4027 */       this.syncingSelection = true;
/* 4028 */       if (this.lastModelSelection != null) {
/* 4029 */         JTable.this.restoreSortingSelection(this.lastModelSelection, this.modelLeadIndex, param1ModelChange);
/*      */         
/* 4031 */         this.lastModelSelection = null;
/* 4032 */       } else if (this.modelSelection != null) {
/* 4033 */         ListSelectionModel listSelectionModel = JTable.this.getSelectionModel();
/* 4034 */         listSelectionModel.setValueIsAdjusting(true);
/* 4035 */         listSelectionModel.clearSelection();
/* 4036 */         int i = this.modelSelection.getMinSelectionIndex();
/* 4037 */         int j = this.modelSelection.getMaxSelectionIndex();
/*      */         int k;
/* 4039 */         for (k = i; k <= j; k++) {
/* 4040 */           if (this.modelSelection.isSelectedIndex(k)) {
/* 4041 */             int m = JTable.this.convertRowIndexToView(k);
/* 4042 */             if (m != -1) {
/* 4043 */               listSelectionModel.addSelectionInterval(m, m);
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 4049 */         k = this.modelSelection.getLeadSelectionIndex();
/* 4050 */         if (k != -1 && !this.modelSelection.isSelectionEmpty()) {
/* 4051 */           k = JTable.this.convertRowIndexToView(k);
/*      */         }
/* 4053 */         SwingUtilities2.setLeadAnchorWithoutSelection(listSelectionModel, k, k);
/*      */         
/* 4055 */         listSelectionModel.setValueIsAdjusting(false);
/*      */       } 
/* 4057 */       this.syncingSelection = false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ModelChange
/*      */   {
/*      */     int startModelIndex;
/*      */ 
/*      */ 
/*      */     
/*      */     int endModelIndex;
/*      */ 
/*      */     
/*      */     int type;
/*      */ 
/*      */     
/*      */     int modelRowCount;
/*      */ 
/*      */     
/*      */     TableModelEvent event;
/*      */ 
/*      */     
/*      */     int length;
/*      */ 
/*      */     
/*      */     boolean allRowsChanged;
/*      */ 
/*      */ 
/*      */     
/*      */     ModelChange(TableModelEvent param1TableModelEvent) {
/* 4090 */       this.startModelIndex = Math.max(0, param1TableModelEvent.getFirstRow());
/* 4091 */       this.endModelIndex = param1TableModelEvent.getLastRow();
/* 4092 */       this.modelRowCount = JTable.this.getModel().getRowCount();
/* 4093 */       if (this.endModelIndex < 0) {
/* 4094 */         this.endModelIndex = Math.max(0, this.modelRowCount - 1);
/*      */       }
/* 4096 */       this.length = this.endModelIndex - this.startModelIndex + 1;
/* 4097 */       this.type = param1TableModelEvent.getType();
/* 4098 */       this.event = param1TableModelEvent;
/* 4099 */       this.allRowsChanged = (param1TableModelEvent.getLastRow() == Integer.MAX_VALUE);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sortedTableChanged(RowSorterEvent paramRowSorterEvent, TableModelEvent paramTableModelEvent) {
/* 4109 */     int i = -1;
/* 4110 */     ModelChange modelChange = (paramTableModelEvent != null) ? new ModelChange(paramTableModelEvent) : null;
/*      */     
/* 4112 */     if ((modelChange == null || !modelChange.allRowsChanged) && this.editingRow != -1)
/*      */     {
/* 4114 */       i = convertRowIndexToModel(paramRowSorterEvent, this.editingRow);
/*      */     }
/*      */ 
/*      */     
/* 4118 */     this.sortManager.prepareForChange(paramRowSorterEvent, modelChange);
/*      */     
/* 4120 */     if (paramTableModelEvent != null) {
/* 4121 */       if (modelChange.type == 0) {
/* 4122 */         repaintSortedRows(modelChange);
/*      */       }
/* 4124 */       notifySorter(modelChange);
/* 4125 */       if (modelChange.type != 0)
/*      */       {
/*      */         
/* 4128 */         this.sorterChanged = true;
/*      */       }
/*      */     } else {
/*      */       
/* 4132 */       this.sorterChanged = true;
/*      */     } 
/*      */     
/* 4135 */     this.sortManager.processChange(paramRowSorterEvent, modelChange, this.sorterChanged);
/*      */     
/* 4137 */     if (this.sorterChanged) {
/*      */       
/* 4139 */       if (this.editingRow != -1) {
/*      */         
/* 4141 */         boolean bool = (i == -1) ? true : convertRowIndexToView(i, modelChange);
/* 4142 */         restoreSortingEditingRow(bool);
/*      */       } 
/*      */ 
/*      */       
/* 4146 */       if (paramTableModelEvent == null || modelChange.type != 0) {
/* 4147 */         resizeAndRepaint();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 4152 */     if (modelChange != null && modelChange.allRowsChanged) {
/* 4153 */       clearSelectionAndLeadAnchor();
/* 4154 */       resizeAndRepaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void repaintSortedRows(ModelChange paramModelChange) {
/* 4162 */     if (paramModelChange.startModelIndex > paramModelChange.endModelIndex || paramModelChange.startModelIndex + 10 < paramModelChange.endModelIndex) {
/*      */ 
/*      */       
/* 4165 */       repaint();
/*      */       return;
/*      */     } 
/* 4168 */     int i = paramModelChange.event.getColumn();
/* 4169 */     int j = i;
/* 4170 */     if (j == -1) {
/* 4171 */       j = 0;
/*      */     } else {
/*      */       
/* 4174 */       j = convertColumnIndexToView(j);
/* 4175 */       if (j == -1) {
/*      */         return;
/*      */       }
/*      */     } 
/* 4179 */     int k = paramModelChange.startModelIndex;
/* 4180 */     while (k <= paramModelChange.endModelIndex) {
/* 4181 */       int m = convertRowIndexToView(k++);
/* 4182 */       if (m != -1) {
/* 4183 */         Rectangle rectangle = getCellRect(m, j, false);
/*      */         
/* 4185 */         int n = rectangle.x;
/* 4186 */         int i1 = rectangle.width;
/* 4187 */         if (i == -1) {
/* 4188 */           n = 0;
/* 4189 */           i1 = getWidth();
/*      */         } 
/* 4191 */         repaint(n, rectangle.y, i1, rectangle.height);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void restoreSortingSelection(int[] paramArrayOfint, int paramInt, ModelChange paramModelChange) {
/*      */     int i;
/* 4203 */     for (i = paramArrayOfint.length - 1; i >= 0; i--) {
/* 4204 */       paramArrayOfint[i] = convertRowIndexToView(paramArrayOfint[i], paramModelChange);
/*      */     }
/* 4206 */     paramInt = convertRowIndexToView(paramInt, paramModelChange);
/*      */ 
/*      */     
/* 4209 */     if (paramArrayOfint.length == 0 || (paramArrayOfint.length == 1 && paramArrayOfint[0] == 
/* 4210 */       getSelectedRow())) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 4215 */     this.selectionModel.setValueIsAdjusting(true);
/* 4216 */     this.selectionModel.clearSelection();
/* 4217 */     for (i = paramArrayOfint.length - 1; i >= 0; i--) {
/* 4218 */       if (paramArrayOfint[i] != -1) {
/* 4219 */         this.selectionModel.addSelectionInterval(paramArrayOfint[i], paramArrayOfint[i]);
/*      */       }
/*      */     } 
/*      */     
/* 4223 */     SwingUtilities2.setLeadAnchorWithoutSelection(this.selectionModel, paramInt, paramInt);
/*      */     
/* 4225 */     this.selectionModel.setValueIsAdjusting(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void restoreSortingEditingRow(int paramInt) {
/* 4234 */     if (paramInt == -1) {
/*      */       
/* 4236 */       TableCellEditor tableCellEditor = getCellEditor();
/* 4237 */       if (tableCellEditor != null)
/*      */       {
/* 4239 */         tableCellEditor.cancelCellEditing();
/* 4240 */         if (getCellEditor() != null)
/*      */         {
/*      */           
/* 4243 */           removeEditor();
/*      */         }
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 4249 */       this.editingRow = paramInt;
/* 4250 */       repaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void notifySorter(ModelChange paramModelChange) {
/*      */     try {
/* 4259 */       this.ignoreSortChange = true;
/* 4260 */       this.sorterChanged = false;
/* 4261 */       switch (paramModelChange.type) {
/*      */         case 0:
/* 4263 */           if (paramModelChange.event.getLastRow() == Integer.MAX_VALUE) {
/* 4264 */             this.sortManager.sorter.allRowsChanged(); break;
/* 4265 */           }  if (paramModelChange.event.getColumn() == -1) {
/*      */             
/* 4267 */             this.sortManager.sorter.rowsUpdated(paramModelChange.startModelIndex, paramModelChange.endModelIndex);
/*      */             break;
/*      */           } 
/* 4270 */           this.sortManager.sorter.rowsUpdated(paramModelChange.startModelIndex, paramModelChange.endModelIndex, paramModelChange.event
/*      */               
/* 4272 */               .getColumn());
/*      */           break;
/*      */         
/*      */         case 1:
/* 4276 */           this.sortManager.sorter.rowsInserted(paramModelChange.startModelIndex, paramModelChange.endModelIndex);
/*      */           break;
/*      */         
/*      */         case -1:
/* 4280 */           this.sortManager.sorter.rowsDeleted(paramModelChange.startModelIndex, paramModelChange.endModelIndex);
/*      */           break;
/*      */       } 
/*      */     
/*      */     } finally {
/* 4285 */       this.ignoreSortChange = false;
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
/*      */   private int convertRowIndexToView(int paramInt, ModelChange paramModelChange) {
/* 4297 */     if (paramInt < 0) {
/* 4298 */       return -1;
/*      */     }
/* 4300 */     if (paramModelChange != null && paramInt >= paramModelChange.startModelIndex) {
/* 4301 */       if (paramModelChange.type == 1) {
/* 4302 */         if (paramInt + paramModelChange.length >= paramModelChange.modelRowCount) {
/* 4303 */           return -1;
/*      */         }
/* 4305 */         return this.sortManager.sorter.convertRowIndexToView(paramInt + paramModelChange.length);
/*      */       } 
/*      */       
/* 4308 */       if (paramModelChange.type == -1) {
/* 4309 */         if (paramInt <= paramModelChange.endModelIndex)
/*      */         {
/* 4311 */           return -1;
/*      */         }
/*      */         
/* 4314 */         if (paramInt - paramModelChange.length >= paramModelChange.modelRowCount) {
/* 4315 */           return -1;
/*      */         }
/* 4317 */         return this.sortManager.sorter.convertRowIndexToView(paramInt - paramModelChange.length);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4323 */     if (paramInt >= getModel().getRowCount()) {
/* 4324 */       return -1;
/*      */     }
/* 4326 */     return this.sortManager.sorter.convertRowIndexToView(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] convertSelectionToModel(RowSorterEvent paramRowSorterEvent) {
/* 4334 */     int[] arrayOfInt = getSelectedRows();
/* 4335 */     for (int i = arrayOfInt.length - 1; i >= 0; i--) {
/* 4336 */       arrayOfInt[i] = convertRowIndexToModel(paramRowSorterEvent, arrayOfInt[i]);
/*      */     }
/* 4338 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */   private int convertRowIndexToModel(RowSorterEvent paramRowSorterEvent, int paramInt) {
/* 4342 */     if (paramRowSorterEvent != null) {
/* 4343 */       if (paramRowSorterEvent.getPreviousRowCount() == 0) {
/* 4344 */         return paramInt;
/*      */       }
/*      */       
/* 4347 */       return paramRowSorterEvent.convertPreviousRowIndexToModel(paramInt);
/*      */     } 
/*      */     
/* 4350 */     if (paramInt < 0 || paramInt >= getRowCount()) {
/* 4351 */       return -1;
/*      */     }
/* 4353 */     return convertRowIndexToModel(paramInt);
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
/*      */   public void tableChanged(TableModelEvent paramTableModelEvent) {
/*      */     Rectangle rectangle;
/* 4374 */     if (paramTableModelEvent == null || paramTableModelEvent.getFirstRow() == -1) {
/*      */       
/* 4376 */       clearSelectionAndLeadAnchor();
/*      */       
/* 4378 */       this.rowModel = null;
/*      */       
/* 4380 */       if (this.sortManager != null) {
/*      */         try {
/* 4382 */           this.ignoreSortChange = true;
/* 4383 */           this.sortManager.sorter.modelStructureChanged();
/*      */         } finally {
/* 4385 */           this.ignoreSortChange = false;
/*      */         } 
/* 4387 */         this.sortManager.allChanged();
/*      */       } 
/*      */       
/* 4390 */       if (getAutoCreateColumnsFromModel()) {
/*      */         
/* 4392 */         createDefaultColumnsFromModel();
/*      */         
/*      */         return;
/*      */       } 
/* 4396 */       resizeAndRepaint();
/*      */       
/*      */       return;
/*      */     } 
/* 4400 */     if (this.sortManager != null) {
/* 4401 */       sortedTableChanged((RowSorterEvent)null, paramTableModelEvent);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 4408 */     if (this.rowModel != null) {
/* 4409 */       repaint();
/*      */     }
/*      */     
/* 4412 */     if (paramTableModelEvent.getType() == 1) {
/* 4413 */       tableRowsInserted(paramTableModelEvent);
/*      */       
/*      */       return;
/*      */     } 
/* 4417 */     if (paramTableModelEvent.getType() == -1) {
/* 4418 */       tableRowsDeleted(paramTableModelEvent);
/*      */       
/*      */       return;
/*      */     } 
/* 4422 */     int i = paramTableModelEvent.getColumn();
/* 4423 */     int j = paramTableModelEvent.getFirstRow();
/* 4424 */     int k = paramTableModelEvent.getLastRow();
/*      */ 
/*      */     
/* 4427 */     if (i == -1) {
/*      */ 
/*      */       
/* 4430 */       rectangle = new Rectangle(0, j * getRowHeight(), getColumnModel().getTotalColumnWidth(), 0);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 4438 */       int m = convertColumnIndexToView(i);
/* 4439 */       rectangle = getCellRect(j, m, false);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4444 */     if (k != Integer.MAX_VALUE) {
/* 4445 */       rectangle.height = (k - j + 1) * getRowHeight();
/* 4446 */       repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 4451 */       clearSelectionAndLeadAnchor();
/* 4452 */       resizeAndRepaint();
/* 4453 */       this.rowModel = null;
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
/*      */   private void tableRowsInserted(TableModelEvent paramTableModelEvent) {
/* 4466 */     int i = paramTableModelEvent.getFirstRow();
/* 4467 */     int j = paramTableModelEvent.getLastRow();
/* 4468 */     if (i < 0) {
/* 4469 */       i = 0;
/*      */     }
/* 4471 */     if (j < 0) {
/* 4472 */       j = getRowCount() - 1;
/*      */     }
/*      */ 
/*      */     
/* 4476 */     int k = j - i + 1;
/* 4477 */     this.selectionModel.insertIndexInterval(i, k, true);
/*      */ 
/*      */     
/* 4480 */     if (this.rowModel != null) {
/* 4481 */       this.rowModel.insertEntries(i, k, getRowHeight());
/*      */     }
/* 4483 */     int m = getRowHeight();
/*      */ 
/*      */     
/* 4486 */     Rectangle rectangle = new Rectangle(0, i * m, getColumnModel().getTotalColumnWidth(), (getRowCount() - i) * m);
/*      */     
/* 4488 */     revalidate();
/*      */ 
/*      */     
/* 4491 */     repaint(rectangle);
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
/*      */   private void tableRowsDeleted(TableModelEvent paramTableModelEvent) {
/* 4503 */     int i = paramTableModelEvent.getFirstRow();
/* 4504 */     int j = paramTableModelEvent.getLastRow();
/* 4505 */     if (i < 0) {
/* 4506 */       i = 0;
/*      */     }
/* 4508 */     if (j < 0) {
/* 4509 */       j = getRowCount() - 1;
/*      */     }
/*      */     
/* 4512 */     int k = j - i + 1;
/* 4513 */     int m = getRowCount() + k;
/*      */     
/* 4515 */     this.selectionModel.removeIndexInterval(i, j);
/*      */ 
/*      */     
/* 4518 */     if (this.rowModel != null) {
/* 4519 */       this.rowModel.removeEntries(i, k);
/*      */     }
/*      */     
/* 4522 */     int n = getRowHeight();
/*      */     
/* 4524 */     Rectangle rectangle = new Rectangle(0, i * n, getColumnModel().getTotalColumnWidth(), (m - i) * n);
/*      */ 
/*      */     
/* 4527 */     revalidate();
/*      */ 
/*      */     
/* 4530 */     repaint(rectangle);
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
/*      */   public void columnAdded(TableColumnModelEvent paramTableColumnModelEvent) {
/* 4547 */     if (isEditing()) {
/* 4548 */       removeEditor();
/*      */     }
/* 4550 */     resizeAndRepaint();
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
/*      */   public void columnRemoved(TableColumnModelEvent paramTableColumnModelEvent) {
/* 4563 */     if (isEditing()) {
/* 4564 */       removeEditor();
/*      */     }
/* 4566 */     resizeAndRepaint();
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
/*      */   public void columnMoved(TableColumnModelEvent paramTableColumnModelEvent) {
/* 4580 */     if (isEditing() && !getCellEditor().stopCellEditing()) {
/* 4581 */       getCellEditor().cancelCellEditing();
/*      */     }
/* 4583 */     repaint();
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
/*      */   public void columnMarginChanged(ChangeEvent paramChangeEvent) {
/* 4598 */     if (isEditing() && !getCellEditor().stopCellEditing()) {
/* 4599 */       getCellEditor().cancelCellEditing();
/*      */     }
/* 4601 */     TableColumn tableColumn = getResizingColumn();
/*      */ 
/*      */     
/* 4604 */     if (tableColumn != null && this.autoResizeMode == 0) {
/* 4605 */       tableColumn.setPreferredWidth(tableColumn.getWidth());
/*      */     }
/* 4607 */     resizeAndRepaint();
/*      */   }
/*      */   
/*      */   private int limit(int paramInt1, int paramInt2, int paramInt3) {
/* 4611 */     return Math.min(paramInt3, Math.max(paramInt1, paramInt2));
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
/*      */   public void columnSelectionChanged(ListSelectionEvent paramListSelectionEvent) {
/* 4625 */     boolean bool = paramListSelectionEvent.getValueIsAdjusting();
/* 4626 */     if (this.columnSelectionAdjusting && !bool) {
/*      */ 
/*      */ 
/*      */       
/* 4630 */       this.columnSelectionAdjusting = false;
/*      */       return;
/*      */     } 
/* 4633 */     this.columnSelectionAdjusting = bool;
/*      */     
/* 4635 */     if (getRowCount() <= 0 || getColumnCount() <= 0) {
/*      */       return;
/*      */     }
/* 4638 */     int i = limit(paramListSelectionEvent.getFirstIndex(), 0, getColumnCount() - 1);
/* 4639 */     int j = limit(paramListSelectionEvent.getLastIndex(), 0, getColumnCount() - 1);
/* 4640 */     int k = 0;
/* 4641 */     int m = getRowCount() - 1;
/* 4642 */     if (getRowSelectionAllowed()) {
/* 4643 */       k = this.selectionModel.getMinSelectionIndex();
/* 4644 */       m = this.selectionModel.getMaxSelectionIndex();
/* 4645 */       int n = getAdjustedIndex(this.selectionModel.getLeadSelectionIndex(), true);
/*      */       
/* 4647 */       if (k == -1 || m == -1) {
/* 4648 */         if (n == -1) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 4654 */         k = m = n;
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 4659 */       else if (n != -1) {
/* 4660 */         k = Math.min(k, n);
/* 4661 */         m = Math.max(m, n);
/*      */       } 
/*      */     } 
/*      */     
/* 4665 */     Rectangle rectangle1 = getCellRect(k, i, false);
/* 4666 */     Rectangle rectangle2 = getCellRect(m, j, false);
/* 4667 */     Rectangle rectangle3 = rectangle1.union(rectangle2);
/* 4668 */     repaint(rectangle3);
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
/*      */   public void valueChanged(ListSelectionEvent paramListSelectionEvent) {
/* 4686 */     if (this.sortManager != null) {
/* 4687 */       this.sortManager.viewSelectionChanged(paramListSelectionEvent);
/*      */     }
/* 4689 */     boolean bool = paramListSelectionEvent.getValueIsAdjusting();
/* 4690 */     if (this.rowSelectionAdjusting && !bool) {
/*      */ 
/*      */ 
/*      */       
/* 4694 */       this.rowSelectionAdjusting = false;
/*      */       return;
/*      */     } 
/* 4697 */     this.rowSelectionAdjusting = bool;
/*      */     
/* 4699 */     if (getRowCount() <= 0 || getColumnCount() <= 0) {
/*      */       return;
/*      */     }
/* 4702 */     int i = limit(paramListSelectionEvent.getFirstIndex(), 0, getRowCount() - 1);
/* 4703 */     int j = limit(paramListSelectionEvent.getLastIndex(), 0, getRowCount() - 1);
/* 4704 */     Rectangle rectangle1 = getCellRect(i, 0, false);
/* 4705 */     Rectangle rectangle2 = getCellRect(j, getColumnCount() - 1, false);
/* 4706 */     Rectangle rectangle3 = rectangle1.union(rectangle2);
/* 4707 */     repaint(rectangle3);
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
/*      */   public void editingStopped(ChangeEvent paramChangeEvent) {
/* 4726 */     TableCellEditor tableCellEditor = getCellEditor();
/* 4727 */     if (tableCellEditor != null) {
/* 4728 */       Object object = tableCellEditor.getCellEditorValue();
/* 4729 */       setValueAt(object, this.editingRow, this.editingColumn);
/* 4730 */       removeEditor();
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
/*      */   public void editingCanceled(ChangeEvent paramChangeEvent) {
/* 4745 */     removeEditor();
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
/*      */   public void setPreferredScrollableViewportSize(Dimension paramDimension) {
/* 4762 */     this.preferredViewportSize = paramDimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getPreferredScrollableViewportSize() {
/* 4773 */     return this.preferredViewportSize;
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
/*      */   public int getScrollableUnitIncrement(Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 4801 */     int n, i = getLeadingRow(paramRectangle);
/* 4802 */     int j = getLeadingCol(paramRectangle);
/* 4803 */     if (paramInt1 == 1 && i < 0)
/*      */     {
/* 4805 */       return getRowHeight();
/*      */     }
/* 4807 */     if (paramInt1 == 0 && j < 0)
/*      */     {
/* 4809 */       return 100;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4815 */     Rectangle rectangle = getCellRect(i, j, true);
/* 4816 */     int k = leadingEdge(paramRectangle, paramInt1);
/* 4817 */     int m = leadingEdge(rectangle, paramInt1);
/*      */     
/* 4819 */     if (paramInt1 == 1) {
/* 4820 */       n = rectangle.height;
/*      */     }
/*      */     else {
/*      */       
/* 4824 */       n = rectangle.width;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4833 */     if (k == m) {
/*      */ 
/*      */       
/* 4836 */       if (paramInt2 < 0) {
/* 4837 */         int i3 = 0;
/*      */         
/* 4839 */         if (paramInt1 == 1) {
/*      */           
/* 4841 */           while (--i >= 0) {
/* 4842 */             i3 = getRowHeight(i);
/* 4843 */             if (i3 != 0) {
/*      */               break;
/*      */             }
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 4850 */           while (--j >= 0) {
/* 4851 */             i3 = (getCellRect(i, j, true)).width;
/* 4852 */             if (i3 != 0) {
/*      */               break;
/*      */             }
/*      */           } 
/*      */         } 
/* 4857 */         return i3;
/*      */       } 
/*      */       
/* 4860 */       return n;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4865 */     int i1 = Math.abs(k - m);
/* 4866 */     int i2 = n - i1;
/*      */     
/* 4868 */     if (paramInt2 > 0)
/*      */     {
/* 4870 */       return i2;
/*      */     }
/*      */     
/* 4873 */     return i1;
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
/*      */   public int getScrollableBlockIncrement(Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 4894 */     if (getRowCount() == 0) {
/*      */       
/* 4896 */       if (1 == paramInt1) {
/* 4897 */         int i = getRowHeight();
/* 4898 */         return (i > 0) ? Math.max(i, paramRectangle.height / i * i) : paramRectangle.height;
/*      */       } 
/*      */ 
/*      */       
/* 4902 */       return paramRectangle.width;
/*      */     } 
/*      */ 
/*      */     
/* 4906 */     if (null == this.rowModel && 1 == paramInt1) {
/* 4907 */       int i = rowAtPoint(paramRectangle.getLocation());
/* 4908 */       assert i != -1;
/* 4909 */       int j = columnAtPoint(paramRectangle.getLocation());
/* 4910 */       Rectangle rectangle = getCellRect(i, j, true);
/*      */       
/* 4912 */       if (rectangle.y == paramRectangle.y) {
/* 4913 */         int k = getRowHeight();
/* 4914 */         assert k > 0;
/* 4915 */         return Math.max(k, paramRectangle.height / k * k);
/*      */       } 
/*      */     } 
/* 4918 */     if (paramInt2 < 0) {
/* 4919 */       return getPreviousBlockIncrement(paramRectangle, paramInt1);
/*      */     }
/*      */     
/* 4922 */     return getNextBlockIncrement(paramRectangle, paramInt1);
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
/*      */   private int getPreviousBlockIncrement(Rectangle paramRectangle, int paramInt) {
/*      */     int k;
/*      */     Point point;
/* 4943 */     int n, m = leadingEdge(paramRectangle, paramInt);
/* 4944 */     boolean bool = getComponentOrientation().isLeftToRight();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4950 */     if (paramInt == 1) {
/* 4951 */       k = m - paramRectangle.height;
/* 4952 */       int i1 = paramRectangle.x + (bool ? 0 : paramRectangle.width);
/* 4953 */       point = new Point(i1, k);
/*      */     }
/* 4955 */     else if (bool) {
/* 4956 */       k = m - paramRectangle.width;
/* 4957 */       point = new Point(k, paramRectangle.y);
/*      */     } else {
/*      */       
/* 4960 */       k = m + paramRectangle.width;
/* 4961 */       point = new Point(k - 1, paramRectangle.y);
/*      */     } 
/* 4963 */     int i = rowAtPoint(point);
/* 4964 */     int j = columnAtPoint(point);
/*      */ 
/*      */ 
/*      */     
/* 4968 */     if ((((paramInt == 1) ? 1 : 0) & ((i < 0) ? 1 : 0)) != 0) {
/* 4969 */       n = 0;
/*      */     }
/* 4971 */     else if ((((paramInt == 0) ? 1 : 0) & ((j < 0) ? 1 : 0)) != 0) {
/* 4972 */       if (bool) {
/* 4973 */         n = 0;
/*      */       } else {
/*      */         
/* 4976 */         n = getWidth();
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 4981 */       Rectangle rectangle = getCellRect(i, j, true);
/* 4982 */       int i1 = leadingEdge(rectangle, paramInt);
/* 4983 */       int i2 = trailingEdge(rectangle, paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4995 */       if ((paramInt == 1 || bool) && i2 >= m) {
/*      */         
/* 4997 */         n = i1;
/*      */       }
/* 4999 */       else if (paramInt == 0 && !bool && i2 <= m) {
/*      */ 
/*      */         
/* 5002 */         n = i1;
/*      */       
/*      */       }
/* 5005 */       else if (k == i1) {
/* 5006 */         n = i1;
/*      */       }
/*      */       else {
/*      */         
/* 5010 */         n = i2;
/*      */       } 
/*      */     } 
/* 5013 */     return Math.abs(m - n);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getNextBlockIncrement(Rectangle paramRectangle, int paramInt) {
/*      */     boolean bool;
/* 5025 */     int n, i = getTrailingRow(paramRectangle);
/* 5026 */     int j = getTrailingCol(paramRectangle);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5034 */     int i1 = leadingEdge(paramRectangle, paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5042 */     if (paramInt == 1 && i < 0) {
/* 5043 */       return paramRectangle.height;
/*      */     }
/* 5045 */     if (paramInt == 0 && j < 0) {
/* 5046 */       return paramRectangle.width;
/*      */     }
/* 5048 */     Rectangle rectangle = getCellRect(i, j, true);
/* 5049 */     int k = leadingEdge(rectangle, paramInt);
/* 5050 */     int m = trailingEdge(rectangle, paramInt);
/*      */     
/* 5052 */     if (paramInt == 1 || 
/* 5053 */       getComponentOrientation().isLeftToRight()) {
/* 5054 */       bool = (k <= i1) ? true : false;
/*      */     } else {
/*      */       
/* 5057 */       bool = (k >= i1) ? true : false;
/*      */     } 
/*      */     
/* 5060 */     if (bool) {
/*      */ 
/*      */       
/* 5063 */       n = m;
/*      */     }
/* 5065 */     else if (m == trailingEdge(paramRectangle, paramInt)) {
/*      */ 
/*      */       
/* 5068 */       n = m;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 5074 */       n = k;
/*      */     } 
/* 5076 */     return Math.abs(n - i1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getLeadingRow(Rectangle paramRectangle) {
/*      */     Point point;
/* 5087 */     if (getComponentOrientation().isLeftToRight()) {
/* 5088 */       point = new Point(paramRectangle.x, paramRectangle.y);
/*      */     } else {
/*      */       
/* 5091 */       point = new Point(paramRectangle.x + paramRectangle.width - 1, paramRectangle.y);
/*      */     } 
/*      */     
/* 5094 */     return rowAtPoint(point);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getLeadingCol(Rectangle paramRectangle) {
/*      */     Point point;
/* 5105 */     if (getComponentOrientation().isLeftToRight()) {
/* 5106 */       point = new Point(paramRectangle.x, paramRectangle.y);
/*      */     } else {
/*      */       
/* 5109 */       point = new Point(paramRectangle.x + paramRectangle.width - 1, paramRectangle.y);
/*      */     } 
/*      */     
/* 5112 */     return columnAtPoint(point);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getTrailingRow(Rectangle paramRectangle) {
/*      */     Point point;
/* 5123 */     if (getComponentOrientation().isLeftToRight()) {
/* 5124 */       point = new Point(paramRectangle.x, paramRectangle.y + paramRectangle.height - 1);
/*      */     }
/*      */     else {
/*      */       
/* 5128 */       point = new Point(paramRectangle.x + paramRectangle.width - 1, paramRectangle.y + paramRectangle.height - 1);
/*      */     } 
/*      */     
/* 5131 */     return rowAtPoint(point);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getTrailingCol(Rectangle paramRectangle) {
/*      */     Point point;
/* 5142 */     if (getComponentOrientation().isLeftToRight()) {
/* 5143 */       point = new Point(paramRectangle.x + paramRectangle.width - 1, paramRectangle.y);
/*      */     }
/*      */     else {
/*      */       
/* 5147 */       point = new Point(paramRectangle.x, paramRectangle.y);
/*      */     } 
/* 5149 */     return columnAtPoint(point);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int leadingEdge(Rectangle paramRectangle, int paramInt) {
/* 5158 */     if (paramInt == 1) {
/* 5159 */       return paramRectangle.y;
/*      */     }
/* 5161 */     if (getComponentOrientation().isLeftToRight()) {
/* 5162 */       return paramRectangle.x;
/*      */     }
/*      */     
/* 5165 */     return paramRectangle.x + paramRectangle.width;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int trailingEdge(Rectangle paramRectangle, int paramInt) {
/* 5175 */     if (paramInt == 1) {
/* 5176 */       return paramRectangle.y + paramRectangle.height;
/*      */     }
/* 5178 */     if (getComponentOrientation().isLeftToRight()) {
/* 5179 */       return paramRectangle.x + paramRectangle.width;
/*      */     }
/*      */     
/* 5182 */     return paramRectangle.x;
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
/*      */   public boolean getScrollableTracksViewportWidth() {
/* 5197 */     return (this.autoResizeMode != 0);
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
/*      */   public boolean getScrollableTracksViewportHeight() {
/* 5214 */     Container container = SwingUtilities.getUnwrappedParent(this);
/* 5215 */     return (getFillsViewportHeight() && container instanceof JViewport && container
/*      */       
/* 5217 */       .getHeight() > (getPreferredSize()).height);
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
/*      */   public void setFillsViewportHeight(boolean paramBoolean) {
/* 5240 */     boolean bool = this.fillsViewportHeight;
/* 5241 */     this.fillsViewportHeight = paramBoolean;
/* 5242 */     resizeAndRepaint();
/* 5243 */     firePropertyChange("fillsViewportHeight", bool, paramBoolean);
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
/*      */   public boolean getFillsViewportHeight() {
/* 5256 */     return this.fillsViewportHeight;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean processKeyBinding(KeyStroke paramKeyStroke, KeyEvent paramKeyEvent, int paramInt, boolean paramBoolean) {
/* 5265 */     boolean bool = super.processKeyBinding(paramKeyStroke, paramKeyEvent, paramInt, paramBoolean);
/*      */ 
/*      */ 
/*      */     
/* 5269 */     if (!bool && paramInt == 1 && 
/* 5270 */       isFocusOwner() && 
/* 5271 */       !Boolean.FALSE.equals(getClientProperty("JTable.autoStartsEdit"))) {
/*      */       
/* 5273 */       Component component = getEditorComponent();
/* 5274 */       if (component == null) {
/*      */         
/* 5276 */         if (paramKeyEvent == null || paramKeyEvent.getID() != 401) {
/* 5277 */           return false;
/*      */         }
/*      */         
/* 5280 */         int i = paramKeyEvent.getKeyCode();
/* 5281 */         if (i == 16 || i == 17 || i == 18)
/*      */         {
/* 5283 */           return false;
/*      */         }
/*      */         
/* 5286 */         int j = getSelectionModel().getLeadSelectionIndex();
/*      */         
/* 5288 */         int k = getColumnModel().getSelectionModel().getLeadSelectionIndex();
/* 5289 */         if (j != -1 && k != -1 && !isEditing() && 
/* 5290 */           !editCellAt(j, k, paramKeyEvent)) {
/* 5291 */           return false;
/*      */         }
/*      */         
/* 5294 */         component = getEditorComponent();
/* 5295 */         if (component == null) {
/* 5296 */           return false;
/*      */         }
/*      */       } 
/*      */       
/* 5300 */       if (component instanceof JComponent) {
/*      */         
/* 5302 */         bool = ((JComponent)component).processKeyBinding(paramKeyStroke, paramKeyEvent, 0, paramBoolean);
/*      */ 
/*      */ 
/*      */         
/* 5306 */         if (getSurrendersFocusOnKeystroke()) {
/* 5307 */           component.requestFocus();
/*      */         }
/*      */       } 
/*      */     } 
/* 5311 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void createDefaultRenderers() {
/* 5321 */     this.defaultRenderersByColumnClass = new UIDefaults(8, 0.75F);
/*      */ 
/*      */     
/* 5324 */     this.defaultRenderersByColumnClass.put(Object.class, paramUIDefaults -> new DefaultTableCellRenderer.UIResource());
/*      */ 
/*      */     
/* 5327 */     this.defaultRenderersByColumnClass.put(Number.class, paramUIDefaults -> new NumberRenderer());
/*      */ 
/*      */     
/* 5330 */     this.defaultRenderersByColumnClass.put(Float.class, paramUIDefaults -> new DoubleRenderer());
/* 5331 */     this.defaultRenderersByColumnClass.put(Double.class, paramUIDefaults -> new DoubleRenderer());
/*      */ 
/*      */     
/* 5334 */     this.defaultRenderersByColumnClass.put(Date.class, paramUIDefaults -> new DateRenderer());
/*      */ 
/*      */     
/* 5337 */     this.defaultRenderersByColumnClass.put(Icon.class, paramUIDefaults -> new IconRenderer());
/* 5338 */     this.defaultRenderersByColumnClass.put(ImageIcon.class, paramUIDefaults -> new IconRenderer());
/*      */ 
/*      */     
/* 5341 */     this.defaultRenderersByColumnClass.put(Boolean.class, paramUIDefaults -> new BooleanRenderer());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class NumberRenderer
/*      */     extends DefaultTableCellRenderer.UIResource
/*      */   {
/*      */     public NumberRenderer() {
/* 5350 */       setHorizontalAlignment(4);
/*      */     }
/*      */   }
/*      */   
/*      */   static class DoubleRenderer
/*      */     extends NumberRenderer {
/*      */     NumberFormat formatter;
/*      */     
/*      */     public void setValue(Object param1Object) {
/* 5359 */       if (this.formatter == null) {
/* 5360 */         this.formatter = NumberFormat.getInstance();
/*      */       }
/* 5362 */       setText((param1Object == null) ? "" : this.formatter.format(param1Object));
/*      */     }
/*      */   }
/*      */   
/*      */   static class DateRenderer
/*      */     extends DefaultTableCellRenderer.UIResource {
/*      */     DateFormat formatter;
/*      */     
/*      */     public void setValue(Object param1Object) {
/* 5371 */       if (this.formatter == null) {
/* 5372 */         this.formatter = DateFormat.getDateInstance();
/*      */       }
/* 5374 */       setText((param1Object == null) ? "" : this.formatter.format(param1Object));
/*      */     }
/*      */   }
/*      */   
/*      */   static class IconRenderer
/*      */     extends DefaultTableCellRenderer.UIResource {
/*      */     public IconRenderer() {
/* 5381 */       setHorizontalAlignment(0);
/*      */     } public void setValue(Object param1Object) {
/* 5383 */       setIcon((param1Object instanceof Icon) ? (Icon)param1Object : null);
/*      */     }
/*      */   }
/*      */   
/*      */   static class BooleanRenderer
/*      */     extends JCheckBox implements TableCellRenderer, UIResource {
/* 5389 */     private static final Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
/*      */ 
/*      */     
/*      */     public BooleanRenderer() {
/* 5393 */       setHorizontalAlignment(0);
/* 5394 */       setBorderPainted(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public Component getTableCellRendererComponent(JTable param1JTable, Object param1Object, boolean param1Boolean1, boolean param1Boolean2, int param1Int1, int param1Int2) {
/* 5399 */       if (param1Boolean1) {
/* 5400 */         setForeground(param1JTable.getSelectionForeground());
/* 5401 */         setBackground(param1JTable.getSelectionBackground());
/*      */       } else {
/*      */         
/* 5404 */         setForeground(param1JTable.getForeground());
/* 5405 */         setBackground(param1JTable.getBackground());
/*      */       } 
/* 5407 */       setSelected((param1Object != null && ((Boolean)param1Object).booleanValue()));
/*      */       
/* 5409 */       if (param1Boolean2) {
/* 5410 */         setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
/*      */       } else {
/* 5412 */         setBorder(noFocusBorder);
/*      */       } 
/*      */       
/* 5415 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void createDefaultEditors() {
/* 5424 */     this.defaultEditorsByColumnClass = new UIDefaults(3, 0.75F);
/*      */ 
/*      */     
/* 5427 */     this.defaultEditorsByColumnClass.put(Object.class, paramUIDefaults -> new GenericEditor());
/*      */ 
/*      */     
/* 5430 */     this.defaultEditorsByColumnClass.put(Number.class, paramUIDefaults -> new NumberEditor());
/*      */ 
/*      */     
/* 5433 */     this.defaultEditorsByColumnClass.put(Boolean.class, paramUIDefaults -> new BooleanEditor());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class GenericEditor
/*      */     extends DefaultCellEditor
/*      */   {
/* 5441 */     Class[] argTypes = new Class[] { String.class };
/*      */     Constructor constructor;
/*      */     Object value;
/*      */     
/*      */     public GenericEditor() {
/* 5446 */       super(new JTextField());
/* 5447 */       getComponent().setName("Table.editor");
/*      */     }
/*      */     
/*      */     public boolean stopCellEditing() {
/* 5451 */       String str = (String)super.getCellEditorValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 5459 */         if ("".equals(str)) {
/* 5460 */           if (this.constructor.getDeclaringClass() == String.class) {
/* 5461 */             this.value = str;
/*      */           }
/* 5463 */           return super.stopCellEditing();
/*      */         } 
/*      */         
/* 5466 */         SwingUtilities2.checkAccess(this.constructor.getModifiers());
/* 5467 */         this.value = this.constructor.newInstance(new Object[] { str });
/*      */       }
/* 5469 */       catch (Exception exception) {
/* 5470 */         ((JComponent)getComponent()).setBorder(new LineBorder(Color.red));
/* 5471 */         return false;
/*      */       } 
/* 5473 */       return super.stopCellEditing();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Component getTableCellEditorComponent(JTable param1JTable, Object param1Object, boolean param1Boolean, int param1Int1, int param1Int2) {
/* 5479 */       this.value = null;
/* 5480 */       ((JComponent)getComponent()).setBorder(new LineBorder(Color.black));
/*      */       try {
/* 5482 */         Class<?> clazz = param1JTable.getColumnClass(param1Int2);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5487 */         if (clazz == Object.class) {
/* 5488 */           clazz = String.class;
/*      */         }
/* 5490 */         ReflectUtil.checkPackageAccess(clazz);
/* 5491 */         SwingUtilities2.checkAccess(clazz.getModifiers());
/* 5492 */         this.constructor = clazz.getConstructor(this.argTypes);
/*      */       }
/* 5494 */       catch (Exception exception) {
/* 5495 */         return null;
/*      */       } 
/* 5497 */       return super.getTableCellEditorComponent(param1JTable, param1Object, param1Boolean, param1Int1, param1Int2);
/*      */     }
/*      */     
/*      */     public Object getCellEditorValue() {
/* 5501 */       return this.value;
/*      */     }
/*      */   }
/*      */   
/*      */   static class NumberEditor
/*      */     extends GenericEditor {
/*      */     public NumberEditor() {
/* 5508 */       ((JTextField)getComponent()).setHorizontalAlignment(4);
/*      */     }
/*      */   }
/*      */   
/*      */   static class BooleanEditor extends DefaultCellEditor {
/*      */     public BooleanEditor() {
/* 5514 */       super(new JCheckBox());
/* 5515 */       JCheckBox jCheckBox = (JCheckBox)getComponent();
/* 5516 */       jCheckBox.setHorizontalAlignment(0);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initializeLocalVars() {
/* 5524 */     this.updateSelectionOnSort = true;
/* 5525 */     setOpaque(true);
/* 5526 */     createDefaultRenderers();
/* 5527 */     createDefaultEditors();
/*      */     
/* 5529 */     setTableHeader(createDefaultTableHeader());
/*      */     
/* 5531 */     setShowGrid(true);
/* 5532 */     setAutoResizeMode(2);
/* 5533 */     setRowHeight(16);
/* 5534 */     this.isRowHeightSet = false;
/* 5535 */     setRowMargin(1);
/* 5536 */     setRowSelectionAllowed(true);
/* 5537 */     setCellEditor((TableCellEditor)null);
/* 5538 */     setEditingColumn(-1);
/* 5539 */     setEditingRow(-1);
/* 5540 */     setSurrendersFocusOnKeystroke(false);
/* 5541 */     setPreferredScrollableViewportSize(new Dimension(450, 400));
/*      */ 
/*      */     
/* 5544 */     ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
/* 5545 */     toolTipManager.registerComponent(this);
/*      */     
/* 5547 */     setAutoscrolls(true);
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
/*      */   protected TableModel createDefaultDataModel() {
/* 5559 */     return new DefaultTableModel();
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
/*      */   protected TableColumnModel createDefaultColumnModel() {
/* 5571 */     return new DefaultTableColumnModel();
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
/*      */   protected ListSelectionModel createDefaultSelectionModel() {
/* 5583 */     return new DefaultListSelectionModel();
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
/*      */   protected JTableHeader createDefaultTableHeader() {
/* 5595 */     return new JTableHeader(this.columnModel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resizeAndRepaint() {
/* 5602 */     revalidate();
/* 5603 */     repaint();
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
/*      */   public TableCellEditor getCellEditor() {
/* 5616 */     return this.cellEditor;
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
/*      */   public void setCellEditor(TableCellEditor paramTableCellEditor) {
/* 5629 */     TableCellEditor tableCellEditor = this.cellEditor;
/* 5630 */     this.cellEditor = paramTableCellEditor;
/* 5631 */     firePropertyChange("tableCellEditor", tableCellEditor, paramTableCellEditor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEditingColumn(int paramInt) {
/* 5641 */     this.editingColumn = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEditingRow(int paramInt) {
/* 5651 */     this.editingRow = paramInt;
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
/*      */   public TableCellRenderer getCellRenderer(int paramInt1, int paramInt2) {
/* 5677 */     TableColumn tableColumn = getColumnModel().getColumn(paramInt2);
/* 5678 */     TableCellRenderer tableCellRenderer = tableColumn.getCellRenderer();
/* 5679 */     if (tableCellRenderer == null) {
/* 5680 */       tableCellRenderer = getDefaultRenderer(getColumnClass(paramInt2));
/*      */     }
/* 5682 */     return tableCellRenderer;
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
/*      */   public Component prepareRenderer(TableCellRenderer paramTableCellRenderer, int paramInt1, int paramInt2) {
/* 5712 */     Object object = getValueAt(paramInt1, paramInt2);
/*      */     
/* 5714 */     boolean bool = false;
/* 5715 */     boolean bool1 = false;
/*      */ 
/*      */     
/* 5718 */     if (!isPaintingForPrint()) {
/* 5719 */       bool = isCellSelected(paramInt1, paramInt2);
/*      */ 
/*      */       
/* 5722 */       boolean bool2 = (this.selectionModel.getLeadSelectionIndex() == paramInt1) ? true : false;
/*      */       
/* 5724 */       boolean bool3 = (this.columnModel.getSelectionModel().getLeadSelectionIndex() == paramInt2) ? true : false;
/*      */       
/* 5726 */       bool1 = (bool2 && bool3 && isFocusOwner()) ? true : false;
/*      */     } 
/*      */     
/* 5729 */     return paramTableCellRenderer.getTableCellRendererComponent(this, object, bool, bool1, paramInt1, paramInt2);
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
/*      */   public TableCellEditor getCellEditor(int paramInt1, int paramInt2) {
/* 5756 */     TableColumn tableColumn = getColumnModel().getColumn(paramInt2);
/* 5757 */     TableCellEditor tableCellEditor = tableColumn.getCellEditor();
/* 5758 */     if (tableCellEditor == null) {
/* 5759 */       tableCellEditor = getDefaultEditor(getColumnClass(paramInt2));
/*      */     }
/* 5761 */     return tableCellEditor;
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
/*      */   public Component prepareEditor(TableCellEditor paramTableCellEditor, int paramInt1, int paramInt2) {
/* 5782 */     Object object = getValueAt(paramInt1, paramInt2);
/* 5783 */     boolean bool = isCellSelected(paramInt1, paramInt2);
/* 5784 */     Component component = paramTableCellEditor.getTableCellEditorComponent(this, object, bool, paramInt1, paramInt2);
/*      */     
/* 5786 */     if (component instanceof JComponent) {
/* 5787 */       JComponent jComponent = (JComponent)component;
/* 5788 */       if (jComponent.getNextFocusableComponent() == null) {
/* 5789 */         jComponent.setNextFocusableComponent(this);
/*      */       }
/*      */     } 
/* 5792 */     return component;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEditor() {
/* 5800 */     KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 5801 */       .removePropertyChangeListener("permanentFocusOwner", this.editorRemover);
/* 5802 */     this.editorRemover = null;
/*      */     
/* 5804 */     TableCellEditor tableCellEditor = getCellEditor();
/* 5805 */     if (tableCellEditor != null) {
/* 5806 */       tableCellEditor.removeCellEditorListener(this);
/* 5807 */       if (this.editorComp != null) {
/*      */         
/* 5809 */         Component component = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/*      */         
/* 5811 */         boolean bool = (component != null) ? SwingUtilities.isDescendingFrom(component, this) : false;
/* 5812 */         remove(this.editorComp);
/* 5813 */         if (bool) {
/* 5814 */           requestFocusInWindow();
/*      */         }
/*      */       } 
/*      */       
/* 5818 */       Rectangle rectangle = getCellRect(this.editingRow, this.editingColumn, false);
/*      */       
/* 5820 */       setCellEditor((TableCellEditor)null);
/* 5821 */       setEditingColumn(-1);
/* 5822 */       setEditingRow(-1);
/* 5823 */       this.editorComp = null;
/*      */       
/* 5825 */       repaint(rectangle);
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 5838 */     paramObjectOutputStream.defaultWriteObject();
/* 5839 */     if (getUIClassID().equals("TableUI")) {
/* 5840 */       byte b = JComponent.getWriteObjCounter(this);
/* 5841 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 5842 */       if (b == 0 && this.ui != null) {
/* 5843 */         this.ui.installUI(this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 5851 */     paramObjectInputStream.defaultReadObject();
/* 5852 */     if (this.ui != null && getUIClassID().equals("TableUI")) {
/* 5853 */       this.ui.installUI(this);
/*      */     }
/* 5855 */     createDefaultRenderers();
/* 5856 */     createDefaultEditors();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5861 */     if (getToolTipText() == null) {
/* 5862 */       ToolTipManager.sharedInstance().registerComponent(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void compWriteObjectNotify() {
/* 5870 */     super.compWriteObjectNotify();
/*      */ 
/*      */     
/* 5873 */     if (getToolTipText() == null) {
/* 5874 */       ToolTipManager.sharedInstance().unregisterComponent(this);
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
/*      */   protected String paramString() {
/* 5889 */     String str4, str1 = (this.gridColor != null) ? this.gridColor.toString() : "";
/* 5890 */     String str2 = this.showHorizontalLines ? "true" : "false";
/*      */     
/* 5892 */     String str3 = this.showVerticalLines ? "true" : "false";
/*      */ 
/*      */     
/* 5895 */     if (this.autoResizeMode == 0)
/* 5896 */     { str4 = "AUTO_RESIZE_OFF"; }
/* 5897 */     else if (this.autoResizeMode == 1)
/* 5898 */     { str4 = "AUTO_RESIZE_NEXT_COLUMN"; }
/* 5899 */     else if (this.autoResizeMode == 2)
/* 5900 */     { str4 = "AUTO_RESIZE_SUBSEQUENT_COLUMNS"; }
/* 5901 */     else if (this.autoResizeMode == 3)
/* 5902 */     { str4 = "AUTO_RESIZE_LAST_COLUMN"; }
/* 5903 */     else if (this.autoResizeMode == 4)
/* 5904 */     { str4 = "AUTO_RESIZE_ALL_COLUMNS"; }
/* 5905 */     else { str4 = ""; }
/* 5906 */      String str5 = this.autoCreateColumnsFromModel ? "true" : "false";
/*      */ 
/*      */     
/* 5909 */     String str6 = (this.preferredViewportSize != null) ? this.preferredViewportSize.toString() : "";
/*      */     
/* 5911 */     String str7 = this.rowSelectionAllowed ? "true" : "false";
/*      */     
/* 5913 */     String str8 = this.cellSelectionEnabled ? "true" : "false";
/*      */ 
/*      */     
/* 5916 */     String str9 = (this.selectionForeground != null) ? this.selectionForeground.toString() : "";
/*      */ 
/*      */     
/* 5919 */     String str10 = (this.selectionBackground != null) ? this.selectionBackground.toString() : "";
/*      */ 
/*      */     
/* 5922 */     return super.paramString() + ",autoCreateColumnsFromModel=" + str5 + ",autoResizeMode=" + str4 + ",cellSelectionEnabled=" + str8 + ",editingColumn=" + this.editingColumn + ",editingRow=" + this.editingRow + ",gridColor=" + str1 + ",preferredViewportSize=" + str6 + ",rowHeight=" + this.rowHeight + ",rowMargin=" + this.rowMargin + ",rowSelectionAllowed=" + str7 + ",selectionBackground=" + str10 + ",selectionForeground=" + str9 + ",showHorizontalLines=" + str2 + ",showVerticalLines=" + str3;
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
/*      */   class CellEditorRemover
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     KeyboardFocusManager focusManager;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CellEditorRemover(KeyboardFocusManager param1KeyboardFocusManager) {
/* 5947 */       this.focusManager = param1KeyboardFocusManager;
/*      */     }
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 5951 */       if (!JTable.this.isEditing() || JTable.this.getClientProperty("terminateEditOnFocusLost") != Boolean.TRUE) {
/*      */         return;
/*      */       }
/*      */       
/* 5955 */       Component component = this.focusManager.getPermanentFocusOwner();
/* 5956 */       while (component != null) {
/* 5957 */         if (component == JTable.this) {
/*      */           return;
/*      */         }
/* 5960 */         if (component instanceof java.awt.Window || (component instanceof java.applet.Applet && component
/* 5961 */           .getParent() == null)) {
/* 5962 */           if (component == SwingUtilities.getRoot(JTable.this) && 
/* 5963 */             !JTable.this.getCellEditor().stopCellEditing()) {
/* 5964 */             JTable.this.getCellEditor().cancelCellEditing();
/*      */           }
/*      */           
/*      */           break;
/*      */         } 
/* 5969 */         component = component.getParent();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean print() throws PrinterException {
/* 6000 */     return print(PrintMode.FIT_WIDTH);
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
/*      */   public boolean print(PrintMode paramPrintMode) throws PrinterException {
/* 6026 */     return print(paramPrintMode, (MessageFormat)null, (MessageFormat)null);
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
/*      */   public boolean print(PrintMode paramPrintMode, MessageFormat paramMessageFormat1, MessageFormat paramMessageFormat2) throws PrinterException {
/* 6060 */     boolean bool = !GraphicsEnvironment.isHeadless() ? true : false;
/* 6061 */     return print(paramPrintMode, paramMessageFormat1, paramMessageFormat2, bool, (PrintRequestAttributeSet)null, bool);
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
/*      */   public boolean print(PrintMode paramPrintMode, MessageFormat paramMessageFormat1, MessageFormat paramMessageFormat2, boolean paramBoolean1, PrintRequestAttributeSet paramPrintRequestAttributeSet, boolean paramBoolean2) throws PrinterException, HeadlessException {
/* 6106 */     return print(paramPrintMode, paramMessageFormat1, paramMessageFormat2, paramBoolean1, paramPrintRequestAttributeSet, paramBoolean2, (PrintService)null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean print(PrintMode paramPrintMode, MessageFormat paramMessageFormat1, MessageFormat paramMessageFormat2, boolean paramBoolean1, PrintRequestAttributeSet paramPrintRequestAttributeSet, boolean paramBoolean2, PrintService paramPrintService) throws PrinterException, HeadlessException {
/*      */     final PrintingStatus printingStatus;
/*      */     Throwable throwable;
/* 6203 */     boolean bool = GraphicsEnvironment.isHeadless();
/* 6204 */     if (bool) {
/* 6205 */       if (paramBoolean1) {
/* 6206 */         throw new HeadlessException("Can't show print dialog.");
/*      */       }
/*      */       
/* 6209 */       if (paramBoolean2) {
/* 6210 */         throw new HeadlessException("Can't run interactively.");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6217 */     final PrinterJob job = PrinterJob.getPrinterJob();
/*      */     
/* 6219 */     if (isEditing())
/*      */     {
/* 6221 */       if (!getCellEditor().stopCellEditing()) {
/* 6222 */         getCellEditor().cancelCellEditing();
/*      */       }
/*      */     }
/*      */     
/* 6226 */     if (paramPrintRequestAttributeSet == null) {
/* 6227 */       paramPrintRequestAttributeSet = new HashPrintRequestAttributeSet();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6234 */     Printable printable = getPrintable(paramPrintMode, paramMessageFormat1, paramMessageFormat2);
/*      */     
/* 6236 */     if (paramBoolean2) {
/*      */       
/* 6238 */       printable = new ThreadSafePrintable(printable);
/* 6239 */       printingStatus = PrintingStatus.createPrintingStatus(this, printerJob);
/* 6240 */       printable = printingStatus.createNotificationPrintable(printable);
/*      */     } else {
/*      */       
/* 6243 */       printingStatus = null;
/*      */     } 
/*      */ 
/*      */     
/* 6247 */     printerJob.setPrintable(printable);
/*      */ 
/*      */     
/* 6250 */     if (paramPrintService != null) {
/* 6251 */       printerJob.setPrintService(paramPrintService);
/*      */     }
/*      */ 
/*      */     
/* 6255 */     if (paramBoolean1 && !printerJob.printDialog(paramPrintRequestAttributeSet))
/*      */     {
/* 6257 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 6261 */     if (!paramBoolean2) {
/*      */       
/* 6263 */       printerJob.print(paramPrintRequestAttributeSet);
/*      */ 
/*      */       
/* 6266 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 6270 */     this.printError = null;
/*      */ 
/*      */     
/* 6273 */     final Object lock = new Object();
/*      */ 
/*      */     
/* 6276 */     final PrintRequestAttributeSet copyAttr = paramPrintRequestAttributeSet;
/*      */ 
/*      */ 
/*      */     
/* 6280 */     Runnable runnable = new Runnable()
/*      */       {
/*      */         public void run() {
/*      */           try {
/* 6284 */             job.print(copyAttr);
/* 6285 */           } catch (Throwable throwable) {
/*      */             
/* 6287 */             synchronized (lock) {
/* 6288 */               JTable.this.printError = throwable;
/*      */             } 
/*      */           } finally {
/*      */             
/* 6292 */             printingStatus.dispose();
/*      */           } 
/*      */         }
/*      */       };
/*      */ 
/*      */     
/* 6298 */     Thread thread = new Thread(runnable);
/* 6299 */     thread.start();
/*      */     
/* 6301 */     printingStatus.showModal(true);
/*      */ 
/*      */ 
/*      */     
/* 6305 */     synchronized (object) {
/* 6306 */       throwable = this.printError;
/* 6307 */       this.printError = null;
/*      */     } 
/*      */ 
/*      */     
/* 6311 */     if (throwable != null) {
/*      */ 
/*      */       
/* 6314 */       if (throwable instanceof java.awt.print.PrinterAbortException)
/* 6315 */         return false; 
/* 6316 */       if (throwable instanceof PrinterException)
/* 6317 */         throw (PrinterException)throwable; 
/* 6318 */       if (throwable instanceof RuntimeException)
/* 6319 */         throw (RuntimeException)throwable; 
/* 6320 */       if (throwable instanceof Error) {
/* 6321 */         throw (Error)throwable;
/*      */       }
/*      */ 
/*      */       
/* 6325 */       throw new AssertionError(throwable);
/*      */     } 
/*      */     
/* 6328 */     return true;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Printable getPrintable(PrintMode paramPrintMode, MessageFormat paramMessageFormat1, MessageFormat paramMessageFormat2) {
/* 6436 */     return new TablePrintable(this, paramPrintMode, paramMessageFormat1, paramMessageFormat2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class ThreadSafePrintable
/*      */     implements Printable
/*      */   {
/*      */     private Printable printDelegate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int retVal;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Throwable retThrowable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ThreadSafePrintable(Printable param1Printable) {
/* 6466 */       this.printDelegate = param1Printable;
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
/*      */     public int print(final Graphics graphics, final PageFormat pageFormat, final int pageIndex) throws PrinterException {
/* 6488 */       Runnable runnable = new Runnable()
/*      */         {
/*      */           public synchronized void run() {
/*      */             try {
/* 6492 */               JTable.ThreadSafePrintable.this.retVal = JTable.ThreadSafePrintable.this.printDelegate.print(graphics, pageFormat, pageIndex);
/* 6493 */             } catch (Throwable throwable) {
/*      */               
/* 6495 */               JTable.ThreadSafePrintable.this.retThrowable = throwable;
/*      */             } finally {
/*      */               
/* 6498 */               notifyAll();
/*      */             } 
/*      */           }
/*      */         };
/*      */       
/* 6503 */       synchronized (runnable) {
/*      */         
/* 6505 */         this.retVal = -1;
/* 6506 */         this.retThrowable = null;
/*      */ 
/*      */         
/* 6509 */         SwingUtilities.invokeLater(runnable);
/*      */ 
/*      */         
/* 6512 */         while (this.retVal == -1 && this.retThrowable == null) {
/*      */           try {
/* 6514 */             runnable.wait();
/* 6515 */           } catch (InterruptedException interruptedException) {}
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 6521 */         if (this.retThrowable != null) {
/* 6522 */           if (this.retThrowable instanceof PrinterException)
/* 6523 */             throw (PrinterException)this.retThrowable; 
/* 6524 */           if (this.retThrowable instanceof RuntimeException)
/* 6525 */             throw (RuntimeException)this.retThrowable; 
/* 6526 */           if (this.retThrowable instanceof Error) {
/* 6527 */             throw (Error)this.retThrowable;
/*      */           }
/*      */ 
/*      */           
/* 6531 */           throw new AssertionError(this.retThrowable);
/*      */         } 
/*      */         
/* 6534 */         return this.retVal;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public AccessibleContext getAccessibleContext() {
/* 6554 */     if (this.accessibleContext == null) {
/* 6555 */       this.accessibleContext = new AccessibleJTable();
/*      */     }
/* 6557 */     return this.accessibleContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class AccessibleJTable
/*      */     extends JComponent.AccessibleJComponent
/*      */     implements AccessibleSelection, ListSelectionListener, TableModelListener, TableColumnModelListener, CellEditorListener, PropertyChangeListener, AccessibleExtendedTable
/*      */   {
/*      */     int previousFocusedRow;
/*      */ 
/*      */ 
/*      */     
/*      */     int previousFocusedCol;
/*      */ 
/*      */ 
/*      */     
/*      */     private Accessible caption;
/*      */ 
/*      */ 
/*      */     
/*      */     private Accessible summary;
/*      */ 
/*      */ 
/*      */     
/*      */     private Accessible[] rowDescription;
/*      */ 
/*      */ 
/*      */     
/*      */     private Accessible[] columnDescription;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected AccessibleJTable() {
/* 6593 */       JTable.this.addPropertyChangeListener(this);
/* 6594 */       JTable.this.getSelectionModel().addListSelectionListener(this);
/* 6595 */       TableColumnModel tableColumnModel = JTable.this.getColumnModel();
/* 6596 */       tableColumnModel.addColumnModelListener(this);
/* 6597 */       tableColumnModel.getSelectionModel().addListSelectionListener(this);
/* 6598 */       JTable.this.getModel().addTableModelListener(this);
/* 6599 */       this
/* 6600 */         .previousFocusedRow = JTable.this.getSelectionModel().getLeadSelectionIndex();
/* 6601 */       this
/* 6602 */         .previousFocusedCol = JTable.this.getColumnModel().getSelectionModel().getLeadSelectionIndex();
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
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 6614 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 6615 */       Object object1 = param1PropertyChangeEvent.getOldValue();
/* 6616 */       Object object2 = param1PropertyChangeEvent.getNewValue();
/*      */ 
/*      */       
/* 6619 */       if (str.compareTo("model") == 0) {
/*      */         
/* 6621 */         if (object1 != null && object1 instanceof TableModel) {
/* 6622 */           ((TableModel)object1).removeTableModelListener(this);
/*      */         }
/* 6624 */         if (object2 != null && object2 instanceof TableModel) {
/* 6625 */           ((TableModel)object2).addTableModelListener(this);
/*      */         
/*      */         }
/*      */       }
/* 6629 */       else if (str.compareTo("selectionModel") == 0) {
/*      */         
/* 6631 */         Object object = param1PropertyChangeEvent.getSource();
/* 6632 */         if (object == JTable.this)
/*      */         {
/* 6634 */           if (object1 != null && object1 instanceof ListSelectionModel)
/*      */           {
/* 6636 */             ((ListSelectionModel)object1).removeListSelectionListener(this);
/*      */           }
/* 6638 */           if (object2 != null && object2 instanceof ListSelectionModel)
/*      */           {
/* 6640 */             ((ListSelectionModel)object2).addListSelectionListener(this);
/*      */           }
/*      */         }
/* 6643 */         else if (object == JTable.this.getColumnModel())
/*      */         {
/* 6645 */           if (object1 != null && object1 instanceof ListSelectionModel)
/*      */           {
/* 6647 */             ((ListSelectionModel)object1).removeListSelectionListener(this);
/*      */           }
/* 6649 */           if (object2 != null && object2 instanceof ListSelectionModel)
/*      */           {
/* 6651 */             ((ListSelectionModel)object2).addListSelectionListener(this);
/*      */ 
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/* 6660 */       else if (str.compareTo("columnModel") == 0) {
/*      */         
/* 6662 */         if (object1 != null && object1 instanceof TableColumnModel) {
/* 6663 */           TableColumnModel tableColumnModel = (TableColumnModel)object1;
/* 6664 */           tableColumnModel.removeColumnModelListener(this);
/* 6665 */           tableColumnModel.getSelectionModel().removeListSelectionListener(this);
/*      */         } 
/* 6667 */         if (object2 != null && object2 instanceof TableColumnModel) {
/* 6668 */           TableColumnModel tableColumnModel = (TableColumnModel)object2;
/* 6669 */           tableColumnModel.addColumnModelListener(this);
/* 6670 */           tableColumnModel.getSelectionModel().addListSelectionListener(this);
/*      */         }
/*      */       
/*      */       }
/* 6674 */       else if (str.compareTo("tableCellEditor") == 0) {
/*      */         
/* 6676 */         if (object1 != null && object1 instanceof TableCellEditor) {
/* 6677 */           ((TableCellEditor)object1).removeCellEditorListener(this);
/*      */         }
/* 6679 */         if (object2 != null && object2 instanceof TableCellEditor) {
/* 6680 */           ((TableCellEditor)object2).addCellEditorListener(this);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected class AccessibleJTableModelChange
/*      */       implements AccessibleTableModelChange
/*      */     {
/*      */       protected int type;
/*      */ 
/*      */       
/*      */       protected int firstRow;
/*      */       
/*      */       protected int lastRow;
/*      */       
/*      */       protected int firstColumn;
/*      */       
/*      */       protected int lastColumn;
/*      */ 
/*      */       
/*      */       protected AccessibleJTableModelChange(int param2Int1, int param2Int2, int param2Int3, int param2Int4, int param2Int5) {
/* 6703 */         this.type = param2Int1;
/* 6704 */         this.firstRow = param2Int2;
/* 6705 */         this.lastRow = param2Int3;
/* 6706 */         this.firstColumn = param2Int4;
/* 6707 */         this.lastColumn = param2Int5;
/*      */       }
/*      */       
/*      */       public int getType() {
/* 6711 */         return this.type;
/*      */       }
/*      */       
/*      */       public int getFirstRow() {
/* 6715 */         return this.firstRow;
/*      */       }
/*      */       
/*      */       public int getLastRow() {
/* 6719 */         return this.lastRow;
/*      */       }
/*      */       
/*      */       public int getFirstColumn() {
/* 6723 */         return this.firstColumn;
/*      */       }
/*      */       
/*      */       public int getLastColumn() {
/* 6727 */         return this.lastColumn;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void tableChanged(TableModelEvent param1TableModelEvent) {
/* 6735 */       firePropertyChange("AccessibleVisibleData", null, null);
/*      */       
/* 6737 */       if (param1TableModelEvent != null) {
/* 6738 */         int i = param1TableModelEvent.getColumn();
/* 6739 */         int j = param1TableModelEvent.getColumn();
/* 6740 */         if (i == -1) {
/* 6741 */           i = 0;
/* 6742 */           j = JTable.this.getColumnCount() - 1;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 6750 */         AccessibleJTableModelChange accessibleJTableModelChange = new AccessibleJTableModelChange(param1TableModelEvent.getType(), param1TableModelEvent.getFirstRow(), param1TableModelEvent.getLastRow(), i, j);
/*      */ 
/*      */         
/* 6753 */         firePropertyChange("accessibleTableModelChanged", null, accessibleJTableModelChange);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void tableRowsInserted(TableModelEvent param1TableModelEvent) {
/* 6762 */       firePropertyChange("AccessibleVisibleData", null, null);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6767 */       int i = param1TableModelEvent.getColumn();
/* 6768 */       int j = param1TableModelEvent.getColumn();
/* 6769 */       if (i == -1) {
/* 6770 */         i = 0;
/* 6771 */         j = JTable.this.getColumnCount() - 1;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 6776 */       AccessibleJTableModelChange accessibleJTableModelChange = new AccessibleJTableModelChange(param1TableModelEvent.getType(), param1TableModelEvent.getFirstRow(), param1TableModelEvent.getLastRow(), i, j);
/*      */ 
/*      */       
/* 6779 */       firePropertyChange("accessibleTableModelChanged", null, accessibleJTableModelChange);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void tableRowsDeleted(TableModelEvent param1TableModelEvent) {
/* 6787 */       firePropertyChange("AccessibleVisibleData", null, null);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6792 */       int i = param1TableModelEvent.getColumn();
/* 6793 */       int j = param1TableModelEvent.getColumn();
/* 6794 */       if (i == -1) {
/* 6795 */         i = 0;
/* 6796 */         j = JTable.this.getColumnCount() - 1;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 6801 */       AccessibleJTableModelChange accessibleJTableModelChange = new AccessibleJTableModelChange(param1TableModelEvent.getType(), param1TableModelEvent.getFirstRow(), param1TableModelEvent.getLastRow(), i, j);
/*      */ 
/*      */       
/* 6804 */       firePropertyChange("accessibleTableModelChanged", null, accessibleJTableModelChange);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void columnAdded(TableColumnModelEvent param1TableColumnModelEvent) {
/* 6812 */       firePropertyChange("AccessibleVisibleData", null, null);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6817 */       boolean bool = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6823 */       AccessibleJTableModelChange accessibleJTableModelChange = new AccessibleJTableModelChange(bool, 0, 0, param1TableColumnModelEvent.getFromIndex(), param1TableColumnModelEvent.getToIndex());
/* 6824 */       firePropertyChange("accessibleTableModelChanged", null, accessibleJTableModelChange);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void columnRemoved(TableColumnModelEvent param1TableColumnModelEvent) {
/* 6832 */       firePropertyChange("AccessibleVisibleData", null, null);
/*      */ 
/*      */ 
/*      */       
/* 6836 */       byte b = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6842 */       AccessibleJTableModelChange accessibleJTableModelChange = new AccessibleJTableModelChange(b, 0, 0, param1TableColumnModelEvent.getFromIndex(), param1TableColumnModelEvent.getToIndex());
/* 6843 */       firePropertyChange("accessibleTableModelChanged", null, accessibleJTableModelChange);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void columnMoved(TableColumnModelEvent param1TableColumnModelEvent) {
/* 6853 */       firePropertyChange("AccessibleVisibleData", null, null);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6858 */       byte b = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6864 */       AccessibleJTableModelChange accessibleJTableModelChange1 = new AccessibleJTableModelChange(b, 0, 0, param1TableColumnModelEvent.getFromIndex(), param1TableColumnModelEvent.getFromIndex());
/* 6865 */       firePropertyChange("accessibleTableModelChanged", null, accessibleJTableModelChange1);
/*      */ 
/*      */       
/* 6868 */       boolean bool = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 6874 */       AccessibleJTableModelChange accessibleJTableModelChange2 = new AccessibleJTableModelChange(bool, 0, 0, param1TableColumnModelEvent.getToIndex(), param1TableColumnModelEvent.getToIndex());
/* 6875 */       firePropertyChange("accessibleTableModelChanged", null, accessibleJTableModelChange2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void columnMarginChanged(ChangeEvent param1ChangeEvent) {
/* 6885 */       firePropertyChange("AccessibleVisibleData", null, null);
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
/*      */     public void columnSelectionChanged(ListSelectionEvent param1ListSelectionEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void editingStopped(ChangeEvent param1ChangeEvent) {
/* 6909 */       firePropertyChange("AccessibleVisibleData", null, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void editingCanceled(ChangeEvent param1ChangeEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void valueChanged(ListSelectionEvent param1ListSelectionEvent) {
/* 6927 */       firePropertyChange("AccessibleSelection", 
/* 6928 */           Boolean.valueOf(false), Boolean.valueOf(true));
/*      */ 
/*      */ 
/*      */       
/* 6932 */       int i = JTable.this.getSelectionModel().getLeadSelectionIndex();
/*      */       
/* 6934 */       int j = JTable.this.getColumnModel().getSelectionModel().getLeadSelectionIndex();
/*      */       
/* 6936 */       if (i != this.previousFocusedRow || j != this.previousFocusedCol) {
/*      */         
/* 6938 */         Accessible accessible1 = getAccessibleAt(this.previousFocusedRow, this.previousFocusedCol);
/* 6939 */         Accessible accessible2 = getAccessibleAt(i, j);
/* 6940 */         firePropertyChange("AccessibleActiveDescendant", accessible1, accessible2);
/*      */         
/* 6942 */         this.previousFocusedRow = i;
/* 6943 */         this.previousFocusedCol = j;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleSelection getAccessibleSelection() {
/* 6961 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleRole getAccessibleRole() {
/* 6972 */       return AccessibleRole.TABLE;
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
/*      */     public Accessible getAccessibleAt(Point param1Point) {
/* 6986 */       int i = JTable.this.columnAtPoint(param1Point);
/* 6987 */       int j = JTable.this.rowAtPoint(param1Point);
/*      */       
/* 6989 */       if (i != -1 && j != -1) {
/* 6990 */         TableColumn tableColumn = JTable.this.getColumnModel().getColumn(i);
/* 6991 */         TableCellRenderer tableCellRenderer = tableColumn.getCellRenderer();
/* 6992 */         if (tableCellRenderer == null) {
/* 6993 */           Class<?> clazz = JTable.this.getColumnClass(i);
/* 6994 */           tableCellRenderer = JTable.this.getDefaultRenderer(clazz);
/*      */         } 
/* 6996 */         Component component = tableCellRenderer.getTableCellRendererComponent(JTable.this, null, false, false, j, i);
/*      */ 
/*      */         
/* 6999 */         return new AccessibleJTableCell(JTable.this, j, i, 
/* 7000 */             getAccessibleIndexAt(j, i));
/*      */       } 
/* 7002 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleChildrenCount() {
/* 7013 */       return JTable.this.getColumnCount() * JTable.this.getRowCount();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleChild(int param1Int) {
/* 7023 */       if (param1Int < 0 || param1Int >= getAccessibleChildrenCount()) {
/* 7024 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 7028 */       int i = getAccessibleColumnAtIndex(param1Int);
/* 7029 */       int j = getAccessibleRowAtIndex(param1Int);
/*      */       
/* 7031 */       TableColumn tableColumn = JTable.this.getColumnModel().getColumn(i);
/* 7032 */       TableCellRenderer tableCellRenderer = tableColumn.getCellRenderer();
/* 7033 */       if (tableCellRenderer == null) {
/* 7034 */         Class<?> clazz = JTable.this.getColumnClass(i);
/* 7035 */         tableCellRenderer = JTable.this.getDefaultRenderer(clazz);
/*      */       } 
/* 7037 */       Component component = tableCellRenderer.getTableCellRendererComponent(JTable.this, null, false, false, j, i);
/*      */ 
/*      */       
/* 7040 */       return new AccessibleJTableCell(JTable.this, j, i, 
/* 7041 */           getAccessibleIndexAt(j, i));
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
/*      */     public int getAccessibleSelectionCount() {
/* 7055 */       int i = JTable.this.getSelectedRowCount();
/* 7056 */       int j = JTable.this.getSelectedColumnCount();
/*      */       
/* 7058 */       if (JTable.this.cellSelectionEnabled) {
/* 7059 */         return i * j;
/*      */       }
/*      */ 
/*      */       
/* 7063 */       if (JTable.this.getRowSelectionAllowed() && JTable.this
/* 7064 */         .getColumnSelectionAllowed()) {
/* 7065 */         return i * JTable.this.getColumnCount() + j * JTable.this
/* 7066 */           .getRowCount() - i * j;
/*      */       }
/*      */ 
/*      */       
/* 7070 */       if (JTable.this.getRowSelectionAllowed()) {
/* 7071 */         return i * JTable.this.getColumnCount();
/*      */       }
/*      */       
/* 7074 */       if (JTable.this.getColumnSelectionAllowed()) {
/* 7075 */         return j * JTable.this.getRowCount();
/*      */       }
/*      */       
/* 7078 */       return 0;
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
/*      */     public Accessible getAccessibleSelection(int param1Int) {
/* 7097 */       if (param1Int < 0 || param1Int > getAccessibleSelectionCount()) {
/* 7098 */         return null;
/*      */       }
/*      */       
/* 7101 */       int i = JTable.this.getSelectedRowCount();
/* 7102 */       int j = JTable.this.getSelectedColumnCount();
/* 7103 */       int[] arrayOfInt1 = JTable.this.getSelectedRows();
/* 7104 */       int[] arrayOfInt2 = JTable.this.getSelectedColumns();
/* 7105 */       int k = JTable.this.getColumnCount();
/* 7106 */       int m = JTable.this.getRowCount();
/*      */ 
/*      */ 
/*      */       
/* 7110 */       if (JTable.this.cellSelectionEnabled) {
/* 7111 */         int n = arrayOfInt1[param1Int / j];
/* 7112 */         int i1 = arrayOfInt2[param1Int % j];
/* 7113 */         return getAccessibleChild(n * k + i1);
/*      */       } 
/*      */ 
/*      */       
/* 7117 */       if (JTable.this.getRowSelectionAllowed() && JTable.this
/* 7118 */         .getColumnSelectionAllowed()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 7141 */         int n = param1Int;
/*      */ 
/*      */         
/* 7144 */         boolean bool = (arrayOfInt1[0] == 0) ? false : true;
/* 7145 */         byte b = 0;
/* 7146 */         int i1 = -1;
/* 7147 */         while (b < arrayOfInt1.length) {
/* 7148 */           switch (bool) {
/*      */             
/*      */             case false:
/* 7151 */               if (n < k) {
/* 7152 */                 int i3 = n % k;
/* 7153 */                 int i2 = arrayOfInt1[b];
/* 7154 */                 return getAccessibleChild(i2 * k + i3);
/*      */               } 
/* 7156 */               n -= k;
/*      */ 
/*      */               
/* 7159 */               if (b + 1 == arrayOfInt1.length || arrayOfInt1[b] != arrayOfInt1[b + 1] - 1) {
/*      */                 
/* 7161 */                 bool = true;
/* 7162 */                 i1 = arrayOfInt1[b];
/*      */               } 
/* 7164 */               b++;
/*      */ 
/*      */             
/*      */             case true:
/* 7168 */               if (n < j * (arrayOfInt1[b] - ((i1 == -1) ? 0 : (i1 + 1)))) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 7173 */                 int i3 = arrayOfInt2[n % j];
/* 7174 */                 int i2 = ((b > 0) ? (arrayOfInt1[b - 1] + 1) : 0) + n / j;
/*      */                 
/* 7176 */                 return getAccessibleChild(i2 * k + i3);
/*      */               } 
/* 7178 */               n -= j * (arrayOfInt1[b] - ((i1 == -1) ? 0 : (i1 + 1)));
/*      */ 
/*      */               
/* 7181 */               bool = false;
/*      */           } 
/*      */ 
/*      */ 
/*      */         
/*      */         } 
/* 7187 */         if (n < j * (m - ((i1 == -1) ? 0 : (i1 + 1))))
/*      */         {
/*      */           
/* 7190 */           int i3 = arrayOfInt2[n % j];
/* 7191 */           int i2 = arrayOfInt1[b - 1] + n / j + 1;
/* 7192 */           return getAccessibleChild(i2 * k + i3);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 7199 */         if (JTable.this.getRowSelectionAllowed()) {
/* 7200 */           int i1 = param1Int % k;
/* 7201 */           int n = arrayOfInt1[param1Int / k];
/* 7202 */           return getAccessibleChild(n * k + i1);
/*      */         } 
/*      */         
/* 7205 */         if (JTable.this.getColumnSelectionAllowed()) {
/* 7206 */           int i1 = arrayOfInt2[param1Int % j];
/* 7207 */           int n = param1Int / j;
/* 7208 */           return getAccessibleChild(n * k + i1);
/*      */         } 
/*      */       } 
/* 7211 */       return null;
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
/*      */     public boolean isAccessibleChildSelected(int param1Int) {
/* 7223 */       int i = getAccessibleColumnAtIndex(param1Int);
/* 7224 */       int j = getAccessibleRowAtIndex(param1Int);
/* 7225 */       return JTable.this.isCellSelected(j, i);
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
/*      */     public void addAccessibleSelection(int param1Int) {
/* 7244 */       int i = getAccessibleColumnAtIndex(param1Int);
/* 7245 */       int j = getAccessibleRowAtIndex(param1Int);
/* 7246 */       JTable.this.changeSelection(j, i, true, false);
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
/*      */     public void removeAccessibleSelection(int param1Int) {
/* 7261 */       if (JTable.this.cellSelectionEnabled) {
/* 7262 */         int i = getAccessibleColumnAtIndex(param1Int);
/* 7263 */         int j = getAccessibleRowAtIndex(param1Int);
/* 7264 */         JTable.this.removeRowSelectionInterval(j, j);
/* 7265 */         JTable.this.removeColumnSelectionInterval(i, i);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void clearAccessibleSelection() {
/* 7274 */       JTable.this.clearSelection();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void selectAllAccessibleSelection() {
/* 7283 */       if (JTable.this.cellSelectionEnabled) {
/* 7284 */         JTable.this.selectAll();
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
/*      */ 
/*      */     
/*      */     public int getAccessibleRow(int param1Int) {
/* 7299 */       return getAccessibleRowAtIndex(param1Int);
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
/*      */     public int getAccessibleColumn(int param1Int) {
/* 7311 */       return getAccessibleColumnAtIndex(param1Int);
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
/*      */     public int getAccessibleIndex(int param1Int1, int param1Int2) {
/* 7324 */       return getAccessibleIndexAt(param1Int1, param1Int2);
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
/*      */     public AccessibleTable getAccessibleTable() {
/* 7347 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleCaption() {
/* 7357 */       return this.caption;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setAccessibleCaption(Accessible param1Accessible) {
/* 7367 */       Accessible accessible = this.caption;
/* 7368 */       this.caption = param1Accessible;
/* 7369 */       firePropertyChange("accessibleTableCaptionChanged", accessible, this.caption);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleSummary() {
/* 7380 */       return this.summary;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setAccessibleSummary(Accessible param1Accessible) {
/* 7390 */       Accessible accessible = this.summary;
/* 7391 */       this.summary = param1Accessible;
/* 7392 */       firePropertyChange("accessibleTableSummaryChanged", accessible, this.summary);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleRowCount() {
/* 7402 */       return JTable.this.getRowCount();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleColumnCount() {
/* 7411 */       return JTable.this.getColumnCount();
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
/*      */     public Accessible getAccessibleAt(int param1Int1, int param1Int2) {
/* 7424 */       return getAccessibleChild(param1Int1 * getAccessibleColumnCount() + param1Int2);
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
/*      */     public int getAccessibleRowExtentAt(int param1Int1, int param1Int2) {
/* 7436 */       return 1;
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
/*      */     public int getAccessibleColumnExtentAt(int param1Int1, int param1Int2) {
/* 7448 */       return 1;
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
/*      */     public AccessibleTable getAccessibleRowHeader() {
/* 7460 */       return null;
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
/*      */     public void setAccessibleRowHeader(AccessibleTable param1AccessibleTable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleTable getAccessibleColumnHeader() {
/* 7483 */       JTableHeader jTableHeader = JTable.this.getTableHeader();
/* 7484 */       return (jTableHeader == null) ? null : new AccessibleTableHeader(jTableHeader);
/*      */     }
/*      */ 
/*      */     
/*      */     private class AccessibleTableHeader
/*      */       implements AccessibleTable
/*      */     {
/*      */       private JTableHeader header;
/*      */       private TableColumnModel headerModel;
/*      */       
/*      */       AccessibleTableHeader(JTableHeader param2JTableHeader) {
/* 7495 */         this.header = param2JTableHeader;
/* 7496 */         this.headerModel = param2JTableHeader.getColumnModel();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Accessible getAccessibleCaption() {
/* 7504 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleCaption(Accessible param2Accessible) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Accessible getAccessibleSummary() {
/* 7519 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleSummary(Accessible param2Accessible) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleRowCount() {
/* 7533 */         return 1;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleColumnCount() {
/* 7541 */         return this.headerModel.getColumnCount();
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
/*      */ 
/*      */       
/*      */       public Accessible getAccessibleAt(int param2Int1, int param2Int2) {
/* 7556 */         TableColumn tableColumn = this.headerModel.getColumn(param2Int2);
/* 7557 */         TableCellRenderer tableCellRenderer = tableColumn.getHeaderRenderer();
/* 7558 */         if (tableCellRenderer == null) {
/* 7559 */           tableCellRenderer = this.header.getDefaultRenderer();
/*      */         }
/* 7561 */         Component component = tableCellRenderer.getTableCellRendererComponent(this.header
/* 7562 */             .getTable(), tableColumn
/* 7563 */             .getHeaderValue(), false, false, -1, param2Int2);
/*      */ 
/*      */         
/* 7566 */         return new JTable.AccessibleJTable.AccessibleJTableHeaderCell(param2Int1, param2Int2, JTable.this
/* 7567 */             .getTableHeader(), component);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleRowExtentAt(int param2Int1, int param2Int2) {
/* 7578 */         return 1;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleColumnExtentAt(int param2Int1, int param2Int2) {
/* 7587 */         return 1;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleTable getAccessibleRowHeader() {
/* 7595 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleRowHeader(AccessibleTable param2AccessibleTable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleTable getAccessibleColumnHeader() {
/* 7611 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleColumnHeader(AccessibleTable param2AccessibleTable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Accessible getAccessibleRowDescription(int param2Int) {
/* 7629 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleRowDescription(int param2Int, Accessible param2Accessible) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Accessible getAccessibleColumnDescription(int param2Int) {
/* 7647 */         return null;
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
/*      */       public void setAccessibleColumnDescription(int param2Int, Accessible param2Accessible) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean isAccessibleSelected(int param2Int1, int param2Int2) {
/* 7669 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean isAccessibleRowSelected(int param2Int) {
/* 7680 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean isAccessibleColumnSelected(int param2Int) {
/* 7691 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int[] getSelectedAccessibleRows() {
/* 7700 */         return new int[0];
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int[] getSelectedAccessibleColumns() {
/* 7709 */         return new int[0];
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
/*      */     public void setAccessibleColumnHeader(AccessibleTable param1AccessibleTable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleRowDescription(int param1Int) {
/* 7732 */       if (param1Int < 0 || param1Int >= getAccessibleRowCount()) {
/* 7733 */         throw new IllegalArgumentException(Integer.toString(param1Int));
/*      */       }
/* 7735 */       if (this.rowDescription == null) {
/* 7736 */         return null;
/*      */       }
/* 7738 */       return this.rowDescription[param1Int];
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
/*      */     public void setAccessibleRowDescription(int param1Int, Accessible param1Accessible) {
/* 7750 */       if (param1Int < 0 || param1Int >= getAccessibleRowCount()) {
/* 7751 */         throw new IllegalArgumentException(Integer.toString(param1Int));
/*      */       }
/* 7753 */       if (this.rowDescription == null) {
/* 7754 */         int i = getAccessibleRowCount();
/* 7755 */         this.rowDescription = new Accessible[i];
/*      */       } 
/* 7757 */       this.rowDescription[param1Int] = param1Accessible;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleColumnDescription(int param1Int) {
/* 7768 */       if (param1Int < 0 || param1Int >= getAccessibleColumnCount()) {
/* 7769 */         throw new IllegalArgumentException(Integer.toString(param1Int));
/*      */       }
/* 7771 */       if (this.columnDescription == null) {
/* 7772 */         return null;
/*      */       }
/* 7774 */       return this.columnDescription[param1Int];
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
/*      */     public void setAccessibleColumnDescription(int param1Int, Accessible param1Accessible) {
/* 7786 */       if (param1Int < 0 || param1Int >= getAccessibleColumnCount()) {
/* 7787 */         throw new IllegalArgumentException(Integer.toString(param1Int));
/*      */       }
/* 7789 */       if (this.columnDescription == null) {
/* 7790 */         int i = getAccessibleColumnCount();
/* 7791 */         this.columnDescription = new Accessible[i];
/*      */       } 
/* 7793 */       this.columnDescription[param1Int] = param1Accessible;
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
/*      */     public boolean isAccessibleSelected(int param1Int1, int param1Int2) {
/* 7807 */       return JTable.this.isCellSelected(param1Int1, param1Int2);
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
/*      */     public boolean isAccessibleRowSelected(int param1Int) {
/* 7820 */       return JTable.this.isRowSelected(param1Int);
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
/*      */     public boolean isAccessibleColumnSelected(int param1Int) {
/* 7833 */       return JTable.this.isColumnSelected(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int[] getSelectedAccessibleRows() {
/* 7844 */       return JTable.this.getSelectedRows();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int[] getSelectedAccessibleColumns() {
/* 7855 */       return JTable.this.getSelectedColumns();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleRowAtIndex(int param1Int) {
/* 7866 */       int i = getAccessibleColumnCount();
/* 7867 */       if (i == 0) {
/* 7868 */         return -1;
/*      */       }
/* 7870 */       return param1Int / i;
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
/*      */     public int getAccessibleColumnAtIndex(int param1Int) {
/* 7882 */       int i = getAccessibleColumnCount();
/* 7883 */       if (i == 0) {
/* 7884 */         return -1;
/*      */       }
/* 7886 */       return param1Int % i;
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
/*      */     public int getAccessibleIndexAt(int param1Int1, int param1Int2) {
/* 7899 */       return param1Int1 * getAccessibleColumnCount() + param1Int2;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected class AccessibleJTableCell
/*      */       extends AccessibleContext
/*      */       implements Accessible, AccessibleComponent
/*      */     {
/*      */       private JTable parent;
/*      */ 
/*      */       
/*      */       private int row;
/*      */ 
/*      */       
/*      */       private int column;
/*      */ 
/*      */       
/*      */       private int index;
/*      */ 
/*      */       
/*      */       public AccessibleJTableCell(JTable param2JTable, int param2Int1, int param2Int2, int param2Int3) {
/* 7921 */         this.parent = param2JTable;
/* 7922 */         this.row = param2Int1;
/* 7923 */         this.column = param2Int2;
/* 7924 */         this.index = param2Int3;
/* 7925 */         setAccessibleParent(this.parent);
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
/*      */       public AccessibleContext getAccessibleContext() {
/* 7937 */         return this;
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
/*      */       protected AccessibleContext getCurrentAccessibleContext() {
/* 7949 */         TableColumn tableColumn = JTable.this.getColumnModel().getColumn(this.column);
/* 7950 */         TableCellRenderer tableCellRenderer = tableColumn.getCellRenderer();
/* 7951 */         if (tableCellRenderer == null) {
/* 7952 */           Class<?> clazz = JTable.this.getColumnClass(this.column);
/* 7953 */           tableCellRenderer = JTable.this.getDefaultRenderer(clazz);
/*      */         } 
/* 7955 */         Component component = tableCellRenderer.getTableCellRendererComponent(JTable.this, JTable.this
/* 7956 */             .getValueAt(this.row, this.column), false, false, this.row, this.column);
/*      */         
/* 7958 */         if (component instanceof Accessible) {
/* 7959 */           return component.getAccessibleContext();
/*      */         }
/* 7961 */         return null;
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
/*      */       protected Component getCurrentComponent() {
/* 7973 */         TableColumn tableColumn = JTable.this.getColumnModel().getColumn(this.column);
/* 7974 */         TableCellRenderer tableCellRenderer = tableColumn.getCellRenderer();
/* 7975 */         if (tableCellRenderer == null) {
/* 7976 */           Class<?> clazz = JTable.this.getColumnClass(this.column);
/* 7977 */           tableCellRenderer = JTable.this.getDefaultRenderer(clazz);
/*      */         } 
/* 7979 */         return tableCellRenderer.getTableCellRendererComponent(JTable.this, null, false, false, this.row, this.column);
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
/*      */       
/*      */       public String getAccessibleName() {
/* 7993 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 7994 */         if (accessibleContext != null) {
/* 7995 */           String str = accessibleContext.getAccessibleName();
/* 7996 */           if (str != null && str != "")
/*      */           {
/* 7998 */             return str;
/*      */           }
/*      */         } 
/* 8001 */         if (this.accessibleName != null && this.accessibleName != "") {
/* 8002 */           return this.accessibleName;
/*      */         }
/*      */         
/* 8005 */         return (String)JTable.this.getClientProperty("AccessibleName");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleName(String param2String) {
/* 8015 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8016 */         if (accessibleContext != null) {
/* 8017 */           accessibleContext.setAccessibleName(param2String);
/*      */         } else {
/* 8019 */           super.setAccessibleName(param2String);
/*      */         } 
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
/*      */       
/*      */       public String getAccessibleDescription() {
/* 8034 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8035 */         if (accessibleContext != null) {
/* 8036 */           return accessibleContext.getAccessibleDescription();
/*      */         }
/* 8038 */         return super.getAccessibleDescription();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleDescription(String param2String) {
/* 8048 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8049 */         if (accessibleContext != null) {
/* 8050 */           accessibleContext.setAccessibleDescription(param2String);
/*      */         } else {
/* 8052 */           super.setAccessibleDescription(param2String);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleRole getAccessibleRole() {
/* 8064 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8065 */         if (accessibleContext != null) {
/* 8066 */           return accessibleContext.getAccessibleRole();
/*      */         }
/* 8068 */         return AccessibleRole.UNKNOWN;
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
/*      */       public AccessibleStateSet getAccessibleStateSet() {
/* 8080 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8081 */         AccessibleStateSet accessibleStateSet = null;
/*      */         
/* 8083 */         if (accessibleContext != null) {
/* 8084 */           accessibleStateSet = accessibleContext.getAccessibleStateSet();
/*      */         }
/* 8086 */         if (accessibleStateSet == null) {
/* 8087 */           accessibleStateSet = new AccessibleStateSet();
/*      */         }
/* 8089 */         Rectangle rectangle1 = JTable.this.getVisibleRect();
/* 8090 */         Rectangle rectangle2 = JTable.this.getCellRect(this.row, this.column, false);
/* 8091 */         if (rectangle1.intersects(rectangle2)) {
/* 8092 */           accessibleStateSet.add(AccessibleState.SHOWING);
/*      */         }
/* 8094 */         else if (accessibleStateSet.contains(AccessibleState.SHOWING)) {
/* 8095 */           accessibleStateSet.remove(AccessibleState.SHOWING);
/*      */         } 
/*      */         
/* 8098 */         if (this.parent.isCellSelected(this.row, this.column)) {
/* 8099 */           accessibleStateSet.add(AccessibleState.SELECTED);
/* 8100 */         } else if (accessibleStateSet.contains(AccessibleState.SELECTED)) {
/* 8101 */           accessibleStateSet.remove(AccessibleState.SELECTED);
/*      */         } 
/* 8103 */         if (this.row == JTable.this.getSelectedRow() && this.column == JTable.this.getSelectedColumn()) {
/* 8104 */           accessibleStateSet.add(AccessibleState.ACTIVE);
/*      */         }
/* 8106 */         accessibleStateSet.add(AccessibleState.TRANSIENT);
/* 8107 */         return accessibleStateSet;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Accessible getAccessibleParent() {
/* 8118 */         return this.parent;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleIndexInParent() {
/* 8129 */         return this.index;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleChildrenCount() {
/* 8138 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8139 */         if (accessibleContext != null) {
/* 8140 */           return accessibleContext.getAccessibleChildrenCount();
/*      */         }
/* 8142 */         return 0;
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
/*      */       public Accessible getAccessibleChild(int param2Int) {
/* 8154 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8155 */         if (accessibleContext != null) {
/* 8156 */           Accessible accessible = accessibleContext.getAccessibleChild(param2Int);
/* 8157 */           accessibleContext.setAccessibleParent(this);
/* 8158 */           return accessible;
/*      */         } 
/* 8160 */         return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Locale getLocale() {
/* 8179 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8180 */         if (accessibleContext != null) {
/* 8181 */           return accessibleContext.getLocale();
/*      */         }
/* 8183 */         return null;
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
/*      */       public void addPropertyChangeListener(PropertyChangeListener param2PropertyChangeListener) {
/* 8195 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8196 */         if (accessibleContext != null) {
/* 8197 */           accessibleContext.addPropertyChangeListener(param2PropertyChangeListener);
/*      */         } else {
/* 8199 */           super.addPropertyChangeListener(param2PropertyChangeListener);
/*      */         } 
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
/*      */       public void removePropertyChangeListener(PropertyChangeListener param2PropertyChangeListener) {
/* 8212 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8213 */         if (accessibleContext != null) {
/* 8214 */           accessibleContext.removePropertyChangeListener(param2PropertyChangeListener);
/*      */         } else {
/* 8216 */           super.removePropertyChangeListener(param2PropertyChangeListener);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleAction getAccessibleAction() {
/* 8227 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8228 */         if (accessibleContext != null) {
/* 8229 */           return accessibleContext.getAccessibleAction();
/*      */         }
/* 8231 */         return null;
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
/*      */       public AccessibleComponent getAccessibleComponent() {
/* 8243 */         return this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleSelection getAccessibleSelection() {
/* 8254 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8255 */         if (accessibleContext != null) {
/* 8256 */           return accessibleContext.getAccessibleSelection();
/*      */         }
/* 8258 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleText getAccessibleText() {
/* 8269 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8270 */         if (accessibleContext != null) {
/* 8271 */           return accessibleContext.getAccessibleText();
/*      */         }
/* 8273 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleValue getAccessibleValue() {
/* 8284 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8285 */         if (accessibleContext != null) {
/* 8286 */           return accessibleContext.getAccessibleValue();
/*      */         }
/* 8288 */         return null;
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
/*      */       
/*      */       public Color getBackground() {
/* 8302 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8303 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8304 */           return ((AccessibleComponent)accessibleContext).getBackground();
/*      */         }
/* 8306 */         Component component = getCurrentComponent();
/* 8307 */         if (component != null) {
/* 8308 */           return component.getBackground();
/*      */         }
/* 8310 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setBackground(Color param2Color) {
/* 8321 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8322 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8323 */           ((AccessibleComponent)accessibleContext).setBackground(param2Color);
/*      */         } else {
/* 8325 */           Component component = getCurrentComponent();
/* 8326 */           if (component != null) {
/* 8327 */             component.setBackground(param2Color);
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Color getForeground() {
/* 8339 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8340 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8341 */           return ((AccessibleComponent)accessibleContext).getForeground();
/*      */         }
/* 8343 */         Component component = getCurrentComponent();
/* 8344 */         if (component != null) {
/* 8345 */           return component.getForeground();
/*      */         }
/* 8347 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setForeground(Color param2Color) {
/* 8358 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8359 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8360 */           ((AccessibleComponent)accessibleContext).setForeground(param2Color);
/*      */         } else {
/* 8362 */           Component component = getCurrentComponent();
/* 8363 */           if (component != null) {
/* 8364 */             component.setForeground(param2Color);
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Cursor getCursor() {
/* 8376 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8377 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8378 */           return ((AccessibleComponent)accessibleContext).getCursor();
/*      */         }
/* 8380 */         Component component = getCurrentComponent();
/* 8381 */         if (component != null) {
/* 8382 */           return component.getCursor();
/*      */         }
/* 8384 */         Accessible accessible = getAccessibleParent();
/* 8385 */         if (accessible instanceof AccessibleComponent) {
/* 8386 */           return ((AccessibleComponent)accessible).getCursor();
/*      */         }
/* 8388 */         return null;
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
/*      */       public void setCursor(Cursor param2Cursor) {
/* 8400 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8401 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8402 */           ((AccessibleComponent)accessibleContext).setCursor(param2Cursor);
/*      */         } else {
/* 8404 */           Component component = getCurrentComponent();
/* 8405 */           if (component != null) {
/* 8406 */             component.setCursor(param2Cursor);
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Font getFont() {
/* 8418 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8419 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8420 */           return ((AccessibleComponent)accessibleContext).getFont();
/*      */         }
/* 8422 */         Component component = getCurrentComponent();
/* 8423 */         if (component != null) {
/* 8424 */           return component.getFont();
/*      */         }
/* 8426 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setFont(Font param2Font) {
/* 8437 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8438 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8439 */           ((AccessibleComponent)accessibleContext).setFont(param2Font);
/*      */         } else {
/* 8441 */           Component component = getCurrentComponent();
/* 8442 */           if (component != null) {
/* 8443 */             component.setFont(param2Font);
/*      */           }
/*      */         } 
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
/*      */       public FontMetrics getFontMetrics(Font param2Font) {
/* 8457 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8458 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8459 */           return ((AccessibleComponent)accessibleContext).getFontMetrics(param2Font);
/*      */         }
/* 8461 */         Component component = getCurrentComponent();
/* 8462 */         if (component != null) {
/* 8463 */           return component.getFontMetrics(param2Font);
/*      */         }
/* 8465 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean isEnabled() {
/* 8476 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8477 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8478 */           return ((AccessibleComponent)accessibleContext).isEnabled();
/*      */         }
/* 8480 */         Component component = getCurrentComponent();
/* 8481 */         if (component != null) {
/* 8482 */           return component.isEnabled();
/*      */         }
/* 8484 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setEnabled(boolean param2Boolean) {
/* 8495 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8496 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8497 */           ((AccessibleComponent)accessibleContext).setEnabled(param2Boolean);
/*      */         } else {
/* 8499 */           Component component = getCurrentComponent();
/* 8500 */           if (component != null) {
/* 8501 */             component.setEnabled(param2Boolean);
/*      */           }
/*      */         } 
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
/*      */       public boolean isVisible() {
/* 8516 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8517 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8518 */           return ((AccessibleComponent)accessibleContext).isVisible();
/*      */         }
/* 8520 */         Component component = getCurrentComponent();
/* 8521 */         if (component != null) {
/* 8522 */           return component.isVisible();
/*      */         }
/* 8524 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setVisible(boolean param2Boolean) {
/* 8535 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8536 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8537 */           ((AccessibleComponent)accessibleContext).setVisible(param2Boolean);
/*      */         } else {
/* 8539 */           Component component = getCurrentComponent();
/* 8540 */           if (component != null) {
/* 8541 */             component.setVisible(param2Boolean);
/*      */           }
/*      */         } 
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
/*      */       public boolean isShowing() {
/* 8556 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8557 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8558 */           if (accessibleContext.getAccessibleParent() != null) {
/* 8559 */             return ((AccessibleComponent)accessibleContext).isShowing();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 8564 */           return isVisible();
/*      */         } 
/*      */         
/* 8567 */         Component component = getCurrentComponent();
/* 8568 */         if (component != null) {
/* 8569 */           return component.isShowing();
/*      */         }
/* 8571 */         return false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean contains(Point param2Point) {
/* 8588 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8589 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8590 */           Rectangle rectangle = ((AccessibleComponent)accessibleContext).getBounds();
/* 8591 */           return rectangle.contains(param2Point);
/*      */         } 
/* 8593 */         Component component = getCurrentComponent();
/* 8594 */         if (component != null) {
/* 8595 */           Rectangle rectangle = component.getBounds();
/* 8596 */           return rectangle.contains(param2Point);
/*      */         } 
/* 8598 */         return getBounds().contains(param2Point);
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
/*      */       public Point getLocationOnScreen() {
/* 8610 */         if (this.parent != null && this.parent.isShowing()) {
/* 8611 */           Point point1 = this.parent.getLocationOnScreen();
/* 8612 */           Point point2 = getLocation();
/* 8613 */           point2.translate(point1.x, point1.y);
/* 8614 */           return point2;
/*      */         } 
/* 8616 */         return null;
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
/*      */ 
/*      */       
/*      */       public Point getLocation() {
/* 8631 */         if (this.parent != null) {
/* 8632 */           Rectangle rectangle = this.parent.getCellRect(this.row, this.column, false);
/* 8633 */           if (rectangle != null) {
/* 8634 */             return rectangle.getLocation();
/*      */           }
/*      */         } 
/* 8637 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setLocation(Point param2Point) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Rectangle getBounds() {
/* 8650 */         if (this.parent != null) {
/* 8651 */           return this.parent.getCellRect(this.row, this.column, false);
/*      */         }
/* 8653 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setBounds(Rectangle param2Rectangle) {
/* 8658 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8659 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8660 */           ((AccessibleComponent)accessibleContext).setBounds(param2Rectangle);
/*      */         } else {
/* 8662 */           Component component = getCurrentComponent();
/* 8663 */           if (component != null) {
/* 8664 */             component.setBounds(param2Rectangle);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Dimension getSize() {
/* 8670 */         if (this.parent != null) {
/* 8671 */           Rectangle rectangle = this.parent.getCellRect(this.row, this.column, false);
/* 8672 */           if (rectangle != null) {
/* 8673 */             return rectangle.getSize();
/*      */           }
/*      */         } 
/* 8676 */         return null;
/*      */       }
/*      */       
/*      */       public void setSize(Dimension param2Dimension) {
/* 8680 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8681 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8682 */           ((AccessibleComponent)accessibleContext).setSize(param2Dimension);
/*      */         } else {
/* 8684 */           Component component = getCurrentComponent();
/* 8685 */           if (component != null) {
/* 8686 */             component.setSize(param2Dimension);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Accessible getAccessibleAt(Point param2Point) {
/* 8692 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8693 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8694 */           return ((AccessibleComponent)accessibleContext).getAccessibleAt(param2Point);
/*      */         }
/* 8696 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean isFocusTraversable() {
/* 8701 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8702 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8703 */           return ((AccessibleComponent)accessibleContext).isFocusTraversable();
/*      */         }
/* 8705 */         Component component = getCurrentComponent();
/* 8706 */         if (component != null) {
/* 8707 */           return component.isFocusTraversable();
/*      */         }
/* 8709 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void requestFocus() {
/* 8715 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8716 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8717 */           ((AccessibleComponent)accessibleContext).requestFocus();
/*      */         } else {
/* 8719 */           Component component = getCurrentComponent();
/* 8720 */           if (component != null) {
/* 8721 */             component.requestFocus();
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public void addFocusListener(FocusListener param2FocusListener) {
/* 8727 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8728 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8729 */           ((AccessibleComponent)accessibleContext).addFocusListener(param2FocusListener);
/*      */         } else {
/* 8731 */           Component component = getCurrentComponent();
/* 8732 */           if (component != null) {
/* 8733 */             component.addFocusListener(param2FocusListener);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public void removeFocusListener(FocusListener param2FocusListener) {
/* 8739 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8740 */         if (accessibleContext instanceof AccessibleComponent) {
/* 8741 */           ((AccessibleComponent)accessibleContext).removeFocusListener(param2FocusListener);
/*      */         } else {
/* 8743 */           Component component = getCurrentComponent();
/* 8744 */           if (component != null) {
/* 8745 */             component.removeFocusListener(param2FocusListener);
/*      */           }
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class AccessibleJTableHeaderCell
/*      */       extends AccessibleContext
/*      */       implements Accessible, AccessibleComponent
/*      */     {
/*      */       private int row;
/*      */ 
/*      */ 
/*      */       
/*      */       private int column;
/*      */ 
/*      */ 
/*      */       
/*      */       private JTableHeader parent;
/*      */ 
/*      */ 
/*      */       
/*      */       private Component rendererComponent;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleJTableHeaderCell(int param2Int1, int param2Int2, JTableHeader param2JTableHeader, Component param2Component) {
/* 8776 */         this.row = param2Int1;
/* 8777 */         this.column = param2Int2;
/* 8778 */         this.parent = param2JTableHeader;
/* 8779 */         this.rendererComponent = param2Component;
/* 8780 */         setAccessibleParent(param2JTableHeader);
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
/*      */       public AccessibleContext getAccessibleContext() {
/* 8792 */         return this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private AccessibleContext getCurrentAccessibleContext() {
/* 8800 */         return this.rendererComponent.getAccessibleContext();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private Component getCurrentComponent() {
/* 8807 */         return this.rendererComponent;
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
/*      */       public String getAccessibleName() {
/* 8819 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8820 */         if (accessibleContext != null) {
/* 8821 */           String str = accessibleContext.getAccessibleName();
/* 8822 */           if (str != null && str != "") {
/* 8823 */             return accessibleContext.getAccessibleName();
/*      */           }
/*      */         } 
/* 8826 */         if (this.accessibleName != null && this.accessibleName != "") {
/* 8827 */           return this.accessibleName;
/*      */         }
/* 8829 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleName(String param2String) {
/* 8839 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8840 */         if (accessibleContext != null) {
/* 8841 */           accessibleContext.setAccessibleName(param2String);
/*      */         } else {
/* 8843 */           super.setAccessibleName(param2String);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String getAccessibleDescription() {
/* 8855 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8856 */         if (accessibleContext != null) {
/* 8857 */           return accessibleContext.getAccessibleDescription();
/*      */         }
/* 8859 */         return super.getAccessibleDescription();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleDescription(String param2String) {
/* 8869 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8870 */         if (accessibleContext != null) {
/* 8871 */           accessibleContext.setAccessibleDescription(param2String);
/*      */         } else {
/* 8873 */           super.setAccessibleDescription(param2String);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleRole getAccessibleRole() {
/* 8885 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8886 */         if (accessibleContext != null) {
/* 8887 */           return accessibleContext.getAccessibleRole();
/*      */         }
/* 8889 */         return AccessibleRole.UNKNOWN;
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
/*      */       public AccessibleStateSet getAccessibleStateSet() {
/* 8901 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8902 */         AccessibleStateSet accessibleStateSet = null;
/*      */         
/* 8904 */         if (accessibleContext != null) {
/* 8905 */           accessibleStateSet = accessibleContext.getAccessibleStateSet();
/*      */         }
/* 8907 */         if (accessibleStateSet == null) {
/* 8908 */           accessibleStateSet = new AccessibleStateSet();
/*      */         }
/* 8910 */         Rectangle rectangle1 = JTable.this.getVisibleRect();
/* 8911 */         Rectangle rectangle2 = JTable.this.getCellRect(this.row, this.column, false);
/* 8912 */         if (rectangle1.intersects(rectangle2)) {
/* 8913 */           accessibleStateSet.add(AccessibleState.SHOWING);
/*      */         }
/* 8915 */         else if (accessibleStateSet.contains(AccessibleState.SHOWING)) {
/* 8916 */           accessibleStateSet.remove(AccessibleState.SHOWING);
/*      */         } 
/*      */         
/* 8919 */         if (JTable.this.isCellSelected(this.row, this.column)) {
/* 8920 */           accessibleStateSet.add(AccessibleState.SELECTED);
/* 8921 */         } else if (accessibleStateSet.contains(AccessibleState.SELECTED)) {
/* 8922 */           accessibleStateSet.remove(AccessibleState.SELECTED);
/*      */         } 
/* 8924 */         if (this.row == JTable.this.getSelectedRow() && this.column == JTable.this.getSelectedColumn()) {
/* 8925 */           accessibleStateSet.add(AccessibleState.ACTIVE);
/*      */         }
/* 8927 */         accessibleStateSet.add(AccessibleState.TRANSIENT);
/* 8928 */         return accessibleStateSet;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Accessible getAccessibleParent() {
/* 8939 */         return this.parent;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleIndexInParent() {
/* 8950 */         return this.column;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleChildrenCount() {
/* 8959 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8960 */         if (accessibleContext != null) {
/* 8961 */           return accessibleContext.getAccessibleChildrenCount();
/*      */         }
/* 8963 */         return 0;
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
/*      */       public Accessible getAccessibleChild(int param2Int) {
/* 8975 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 8976 */         if (accessibleContext != null) {
/* 8977 */           Accessible accessible = accessibleContext.getAccessibleChild(param2Int);
/* 8978 */           accessibleContext.setAccessibleParent(this);
/* 8979 */           return accessible;
/*      */         } 
/* 8981 */         return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Locale getLocale() {
/* 9000 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9001 */         if (accessibleContext != null) {
/* 9002 */           return accessibleContext.getLocale();
/*      */         }
/* 9004 */         return null;
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
/*      */       public void addPropertyChangeListener(PropertyChangeListener param2PropertyChangeListener) {
/* 9016 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9017 */         if (accessibleContext != null) {
/* 9018 */           accessibleContext.addPropertyChangeListener(param2PropertyChangeListener);
/*      */         } else {
/* 9020 */           super.addPropertyChangeListener(param2PropertyChangeListener);
/*      */         } 
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
/*      */       public void removePropertyChangeListener(PropertyChangeListener param2PropertyChangeListener) {
/* 9033 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9034 */         if (accessibleContext != null) {
/* 9035 */           accessibleContext.removePropertyChangeListener(param2PropertyChangeListener);
/*      */         } else {
/* 9037 */           super.removePropertyChangeListener(param2PropertyChangeListener);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleAction getAccessibleAction() {
/* 9048 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9049 */         if (accessibleContext != null) {
/* 9050 */           return accessibleContext.getAccessibleAction();
/*      */         }
/* 9052 */         return null;
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
/*      */       public AccessibleComponent getAccessibleComponent() {
/* 9064 */         return this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleSelection getAccessibleSelection() {
/* 9075 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9076 */         if (accessibleContext != null) {
/* 9077 */           return accessibleContext.getAccessibleSelection();
/*      */         }
/* 9079 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleText getAccessibleText() {
/* 9090 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9091 */         if (accessibleContext != null) {
/* 9092 */           return accessibleContext.getAccessibleText();
/*      */         }
/* 9094 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleValue getAccessibleValue() {
/* 9105 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9106 */         if (accessibleContext != null) {
/* 9107 */           return accessibleContext.getAccessibleValue();
/*      */         }
/* 9109 */         return null;
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
/*      */       
/*      */       public Color getBackground() {
/* 9123 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9124 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9125 */           return ((AccessibleComponent)accessibleContext).getBackground();
/*      */         }
/* 9127 */         Component component = getCurrentComponent();
/* 9128 */         if (component != null) {
/* 9129 */           return component.getBackground();
/*      */         }
/* 9131 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setBackground(Color param2Color) {
/* 9142 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9143 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9144 */           ((AccessibleComponent)accessibleContext).setBackground(param2Color);
/*      */         } else {
/* 9146 */           Component component = getCurrentComponent();
/* 9147 */           if (component != null) {
/* 9148 */             component.setBackground(param2Color);
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Color getForeground() {
/* 9160 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9161 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9162 */           return ((AccessibleComponent)accessibleContext).getForeground();
/*      */         }
/* 9164 */         Component component = getCurrentComponent();
/* 9165 */         if (component != null) {
/* 9166 */           return component.getForeground();
/*      */         }
/* 9168 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setForeground(Color param2Color) {
/* 9179 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9180 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9181 */           ((AccessibleComponent)accessibleContext).setForeground(param2Color);
/*      */         } else {
/* 9183 */           Component component = getCurrentComponent();
/* 9184 */           if (component != null) {
/* 9185 */             component.setForeground(param2Color);
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Cursor getCursor() {
/* 9197 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9198 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9199 */           return ((AccessibleComponent)accessibleContext).getCursor();
/*      */         }
/* 9201 */         Component component = getCurrentComponent();
/* 9202 */         if (component != null) {
/* 9203 */           return component.getCursor();
/*      */         }
/* 9205 */         Accessible accessible = getAccessibleParent();
/* 9206 */         if (accessible instanceof AccessibleComponent) {
/* 9207 */           return ((AccessibleComponent)accessible).getCursor();
/*      */         }
/* 9209 */         return null;
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
/*      */       public void setCursor(Cursor param2Cursor) {
/* 9221 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9222 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9223 */           ((AccessibleComponent)accessibleContext).setCursor(param2Cursor);
/*      */         } else {
/* 9225 */           Component component = getCurrentComponent();
/* 9226 */           if (component != null) {
/* 9227 */             component.setCursor(param2Cursor);
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Font getFont() {
/* 9239 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9240 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9241 */           return ((AccessibleComponent)accessibleContext).getFont();
/*      */         }
/* 9243 */         Component component = getCurrentComponent();
/* 9244 */         if (component != null) {
/* 9245 */           return component.getFont();
/*      */         }
/* 9247 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setFont(Font param2Font) {
/* 9258 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9259 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9260 */           ((AccessibleComponent)accessibleContext).setFont(param2Font);
/*      */         } else {
/* 9262 */           Component component = getCurrentComponent();
/* 9263 */           if (component != null) {
/* 9264 */             component.setFont(param2Font);
/*      */           }
/*      */         } 
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
/*      */       public FontMetrics getFontMetrics(Font param2Font) {
/* 9278 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9279 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9280 */           return ((AccessibleComponent)accessibleContext).getFontMetrics(param2Font);
/*      */         }
/* 9282 */         Component component = getCurrentComponent();
/* 9283 */         if (component != null) {
/* 9284 */           return component.getFontMetrics(param2Font);
/*      */         }
/* 9286 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean isEnabled() {
/* 9297 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9298 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9299 */           return ((AccessibleComponent)accessibleContext).isEnabled();
/*      */         }
/* 9301 */         Component component = getCurrentComponent();
/* 9302 */         if (component != null) {
/* 9303 */           return component.isEnabled();
/*      */         }
/* 9305 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setEnabled(boolean param2Boolean) {
/* 9316 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9317 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9318 */           ((AccessibleComponent)accessibleContext).setEnabled(param2Boolean);
/*      */         } else {
/* 9320 */           Component component = getCurrentComponent();
/* 9321 */           if (component != null) {
/* 9322 */             component.setEnabled(param2Boolean);
/*      */           }
/*      */         } 
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
/*      */       public boolean isVisible() {
/* 9337 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9338 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9339 */           return ((AccessibleComponent)accessibleContext).isVisible();
/*      */         }
/* 9341 */         Component component = getCurrentComponent();
/* 9342 */         if (component != null) {
/* 9343 */           return component.isVisible();
/*      */         }
/* 9345 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setVisible(boolean param2Boolean) {
/* 9356 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9357 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9358 */           ((AccessibleComponent)accessibleContext).setVisible(param2Boolean);
/*      */         } else {
/* 9360 */           Component component = getCurrentComponent();
/* 9361 */           if (component != null) {
/* 9362 */             component.setVisible(param2Boolean);
/*      */           }
/*      */         } 
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
/*      */       public boolean isShowing() {
/* 9377 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9378 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9379 */           if (accessibleContext.getAccessibleParent() != null) {
/* 9380 */             return ((AccessibleComponent)accessibleContext).isShowing();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 9385 */           return isVisible();
/*      */         } 
/*      */         
/* 9388 */         Component component = getCurrentComponent();
/* 9389 */         if (component != null) {
/* 9390 */           return component.isShowing();
/*      */         }
/* 9392 */         return false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean contains(Point param2Point) {
/* 9409 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9410 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9411 */           Rectangle rectangle = ((AccessibleComponent)accessibleContext).getBounds();
/* 9412 */           return rectangle.contains(param2Point);
/*      */         } 
/* 9414 */         Component component = getCurrentComponent();
/* 9415 */         if (component != null) {
/* 9416 */           Rectangle rectangle = component.getBounds();
/* 9417 */           return rectangle.contains(param2Point);
/*      */         } 
/* 9419 */         return getBounds().contains(param2Point);
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
/*      */       public Point getLocationOnScreen() {
/* 9431 */         if (this.parent != null && this.parent.isShowing()) {
/* 9432 */           Point point1 = this.parent.getLocationOnScreen();
/* 9433 */           Point point2 = getLocation();
/* 9434 */           point2.translate(point1.x, point1.y);
/* 9435 */           return point2;
/*      */         } 
/* 9437 */         return null;
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
/*      */ 
/*      */       
/*      */       public Point getLocation() {
/* 9452 */         if (this.parent != null) {
/* 9453 */           Rectangle rectangle = this.parent.getHeaderRect(this.column);
/* 9454 */           if (rectangle != null) {
/* 9455 */             return rectangle.getLocation();
/*      */           }
/*      */         } 
/* 9458 */         return null;
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
/*      */       public void setLocation(Point param2Point) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Rectangle getBounds() {
/* 9479 */         if (this.parent != null) {
/* 9480 */           return this.parent.getHeaderRect(this.column);
/*      */         }
/* 9482 */         return null;
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
/*      */       public void setBounds(Rectangle param2Rectangle) {
/* 9495 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9496 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9497 */           ((AccessibleComponent)accessibleContext).setBounds(param2Rectangle);
/*      */         } else {
/* 9499 */           Component component = getCurrentComponent();
/* 9500 */           if (component != null) {
/* 9501 */             component.setBounds(param2Rectangle);
/*      */           }
/*      */         } 
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
/*      */       
/*      */       public Dimension getSize() {
/* 9517 */         if (this.parent != null) {
/* 9518 */           Rectangle rectangle = this.parent.getHeaderRect(this.column);
/* 9519 */           if (rectangle != null) {
/* 9520 */             return rectangle.getSize();
/*      */           }
/*      */         } 
/* 9523 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setSize(Dimension param2Dimension) {
/* 9533 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9534 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9535 */           ((AccessibleComponent)accessibleContext).setSize(param2Dimension);
/*      */         } else {
/* 9537 */           Component component = getCurrentComponent();
/* 9538 */           if (component != null) {
/* 9539 */             component.setSize(param2Dimension);
/*      */           }
/*      */         } 
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
/*      */       public Accessible getAccessibleAt(Point param2Point) {
/* 9553 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9554 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9555 */           return ((AccessibleComponent)accessibleContext).getAccessibleAt(param2Point);
/*      */         }
/* 9557 */         return null;
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
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean isFocusTraversable() {
/* 9573 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9574 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9575 */           return ((AccessibleComponent)accessibleContext).isFocusTraversable();
/*      */         }
/* 9577 */         Component component = getCurrentComponent();
/* 9578 */         if (component != null) {
/* 9579 */           return component.isFocusTraversable();
/*      */         }
/* 9581 */         return false;
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
/*      */       public void requestFocus() {
/* 9593 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9594 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9595 */           ((AccessibleComponent)accessibleContext).requestFocus();
/*      */         } else {
/* 9597 */           Component component = getCurrentComponent();
/* 9598 */           if (component != null) {
/* 9599 */             component.requestFocus();
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void addFocusListener(FocusListener param2FocusListener) {
/* 9612 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9613 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9614 */           ((AccessibleComponent)accessibleContext).addFocusListener(param2FocusListener);
/*      */         } else {
/* 9616 */           Component component = getCurrentComponent();
/* 9617 */           if (component != null) {
/* 9618 */             component.addFocusListener(param2FocusListener);
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void removeFocusListener(FocusListener param2FocusListener) {
/* 9631 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 9632 */         if (accessibleContext instanceof AccessibleComponent) {
/* 9633 */           ((AccessibleComponent)accessibleContext).removeFocusListener(param2FocusListener);
/*      */         } else {
/* 9635 */           Component component = getCurrentComponent();
/* 9636 */           if (component != null)
/* 9637 */             component.removeFocusListener(param2FocusListener); 
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static interface Resizable3 extends Resizable2 {
/*      */     int getMidPointAt(int param1Int);
/*      */   }
/*      */   
/*      */   private static interface Resizable2 {
/*      */     int getElementCount();
/*      */     
/*      */     int getLowerBoundAt(int param1Int);
/*      */     
/*      */     int getUpperBoundAt(int param1Int);
/*      */     
/*      */     void setSizeAt(int param1Int1, int param1Int2);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */