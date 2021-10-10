/*      */ package javax.swing.table;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.beans.Transient;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.util.Locale;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleAction;
/*      */ import javax.accessibility.AccessibleComponent;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleSelection;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.accessibility.AccessibleText;
/*      */ import javax.accessibility.AccessibleValue;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.ToolTipManager;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.TableColumnModelEvent;
/*      */ import javax.swing.event.TableColumnModelListener;
/*      */ import javax.swing.plaf.TableHeaderUI;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.swing.table.DefaultTableCellHeaderRenderer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JTableHeader
/*      */   extends JComponent
/*      */   implements TableColumnModelListener, Accessible
/*      */ {
/*      */   private static final String uiClassID = "TableHeaderUI";
/*      */   protected JTable table;
/*      */   protected TableColumnModel columnModel;
/*      */   protected boolean reorderingAllowed;
/*      */   protected boolean resizingAllowed;
/*      */   protected boolean updateTableInRealTime;
/*      */   protected transient TableColumn resizingColumn;
/*      */   protected transient TableColumn draggedColumn;
/*      */   protected transient int draggedDistance;
/*      */   private TableCellRenderer defaultRenderer;
/*      */   
/*      */   public JTableHeader() {
/*  135 */     this((TableColumnModel)null);
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
/*      */   public JTableHeader(TableColumnModel paramTableColumnModel) {
/*  153 */     if (paramTableColumnModel == null)
/*  154 */       paramTableColumnModel = createDefaultColumnModel(); 
/*  155 */     setColumnModel(paramTableColumnModel);
/*      */ 
/*      */     
/*  158 */     initializeLocalVars();
/*      */ 
/*      */     
/*  161 */     updateUI();
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
/*      */   public void setTable(JTable paramJTable) {
/*  176 */     JTable jTable = this.table;
/*  177 */     this.table = paramJTable;
/*  178 */     firePropertyChange("table", jTable, paramJTable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JTable getTable() {
/*  186 */     return this.table;
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
/*      */   public void setReorderingAllowed(boolean paramBoolean) {
/*  200 */     boolean bool = this.reorderingAllowed;
/*  201 */     this.reorderingAllowed = paramBoolean;
/*  202 */     firePropertyChange("reorderingAllowed", bool, paramBoolean);
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
/*      */   public boolean getReorderingAllowed() {
/*  214 */     return this.reorderingAllowed;
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
/*      */   public void setResizingAllowed(boolean paramBoolean) {
/*  228 */     boolean bool = this.resizingAllowed;
/*  229 */     this.resizingAllowed = paramBoolean;
/*  230 */     firePropertyChange("resizingAllowed", bool, paramBoolean);
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
/*      */   public boolean getResizingAllowed() {
/*  242 */     return this.resizingAllowed;
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
/*      */   public TableColumn getDraggedColumn() {
/*  254 */     return this.draggedColumn;
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
/*      */   public int getDraggedDistance() {
/*  268 */     return this.draggedDistance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TableColumn getResizingColumn() {
/*  279 */     return this.resizingColumn;
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
/*      */   public void setUpdateTableInRealTime(boolean paramBoolean) {
/*  295 */     this.updateTableInRealTime = paramBoolean;
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
/*      */   public boolean getUpdateTableInRealTime() {
/*  312 */     return this.updateTableInRealTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultRenderer(TableCellRenderer paramTableCellRenderer) {
/*  322 */     this.defaultRenderer = paramTableCellRenderer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Transient
/*      */   public TableCellRenderer getDefaultRenderer() {
/*  333 */     return this.defaultRenderer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int columnAtPoint(Point paramPoint) {
/*  344 */     int i = paramPoint.x;
/*  345 */     if (!getComponentOrientation().isLeftToRight()) {
/*  346 */       i = getWidthInRightToLeft() - i - 1;
/*      */     }
/*  348 */     return getColumnModel().getColumnIndexAtX(i);
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
/*      */   public Rectangle getHeaderRect(int paramInt) {
/*  360 */     Rectangle rectangle = new Rectangle();
/*  361 */     TableColumnModel tableColumnModel = getColumnModel();
/*      */     
/*  363 */     rectangle.height = getHeight();
/*      */     
/*  365 */     if (paramInt < 0) {
/*      */       
/*  367 */       if (!getComponentOrientation().isLeftToRight()) {
/*  368 */         rectangle.x = getWidthInRightToLeft();
/*      */       }
/*      */     }
/*  371 */     else if (paramInt >= tableColumnModel.getColumnCount()) {
/*  372 */       if (getComponentOrientation().isLeftToRight()) {
/*  373 */         rectangle.x = getWidth();
/*      */       }
/*      */     } else {
/*      */       
/*  377 */       for (byte b = 0; b < paramInt; b++) {
/*  378 */         rectangle.x += tableColumnModel.getColumn(b).getWidth();
/*      */       }
/*  380 */       if (!getComponentOrientation().isLeftToRight()) {
/*  381 */         rectangle.x = getWidthInRightToLeft() - rectangle.x - tableColumnModel.getColumn(paramInt).getWidth();
/*      */       }
/*      */       
/*  384 */       rectangle.width = tableColumnModel.getColumn(paramInt).getWidth();
/*      */     } 
/*  386 */     return rectangle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getToolTipText(MouseEvent paramMouseEvent) {
/*  397 */     String str = null;
/*  398 */     Point point = paramMouseEvent.getPoint();
/*      */     
/*      */     int i;
/*      */     
/*  402 */     if ((i = columnAtPoint(point)) != -1) {
/*  403 */       TableColumn tableColumn = this.columnModel.getColumn(i);
/*  404 */       TableCellRenderer tableCellRenderer = tableColumn.getHeaderRenderer();
/*  405 */       if (tableCellRenderer == null) {
/*  406 */         tableCellRenderer = this.defaultRenderer;
/*      */       }
/*  408 */       Component component = tableCellRenderer.getTableCellRendererComponent(
/*  409 */           getTable(), tableColumn.getHeaderValue(), false, false, -1, i);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  414 */       if (component instanceof JComponent) {
/*      */ 
/*      */         
/*  417 */         Rectangle rectangle = getHeaderRect(i);
/*      */         
/*  419 */         point.translate(-rectangle.x, -rectangle.y);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  424 */         MouseEvent mouseEvent = new MouseEvent(component, paramMouseEvent.getID(), paramMouseEvent.getWhen(), paramMouseEvent.getModifiers(), point.x, point.y, paramMouseEvent.getXOnScreen(), paramMouseEvent.getYOnScreen(), paramMouseEvent.getClickCount(), paramMouseEvent.isPopupTrigger(), 0);
/*  425 */         AWTAccessor.MouseEventAccessor mouseEventAccessor = AWTAccessor.getMouseEventAccessor();
/*  426 */         mouseEventAccessor.setCausedByTouchEvent(mouseEvent, mouseEventAccessor
/*  427 */             .isCausedByTouchEvent(paramMouseEvent));
/*      */         
/*  429 */         str = ((JComponent)component).getToolTipText(mouseEvent);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  434 */     if (str == null) {
/*  435 */       str = getToolTipText();
/*      */     }
/*  437 */     return str;
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
/*      */   public TableHeaderUI getUI() {
/*  450 */     return (TableHeaderUI)this.ui;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUI(TableHeaderUI paramTableHeaderUI) {
/*  460 */     if (this.ui != paramTableHeaderUI) {
/*  461 */       setUI(paramTableHeaderUI);
/*  462 */       repaint();
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
/*  475 */     setUI((TableHeaderUI)UIManager.getUI(this));
/*      */     
/*  477 */     TableCellRenderer tableCellRenderer = getDefaultRenderer();
/*  478 */     if (tableCellRenderer instanceof Component) {
/*  479 */       SwingUtilities.updateComponentTreeUI((Component)tableCellRenderer);
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
/*      */   public String getUIClassID() {
/*  494 */     return "TableHeaderUI";
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
/*      */   public void setColumnModel(TableColumnModel paramTableColumnModel) {
/*  516 */     if (paramTableColumnModel == null) {
/*  517 */       throw new IllegalArgumentException("Cannot set a null ColumnModel");
/*      */     }
/*  519 */     TableColumnModel tableColumnModel = this.columnModel;
/*  520 */     if (paramTableColumnModel != tableColumnModel) {
/*  521 */       if (tableColumnModel != null) {
/*  522 */         tableColumnModel.removeColumnModelListener(this);
/*      */       }
/*  524 */       this.columnModel = paramTableColumnModel;
/*  525 */       paramTableColumnModel.addColumnModelListener(this);
/*      */       
/*  527 */       firePropertyChange("columnModel", tableColumnModel, paramTableColumnModel);
/*  528 */       resizeAndRepaint();
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
/*  540 */     return this.columnModel;
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
/*      */   public void columnAdded(TableColumnModelEvent paramTableColumnModelEvent) {
/*  556 */     resizeAndRepaint();
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
/*      */   public void columnRemoved(TableColumnModelEvent paramTableColumnModelEvent) {
/*  568 */     resizeAndRepaint();
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
/*      */   public void columnMoved(TableColumnModelEvent paramTableColumnModelEvent) {
/*  580 */     repaint();
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
/*      */   public void columnMarginChanged(ChangeEvent paramChangeEvent) {
/*  592 */     resizeAndRepaint();
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
/*      */   public void columnSelectionChanged(ListSelectionEvent paramListSelectionEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  623 */     return new DefaultTableColumnModel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TableCellRenderer createDefaultRenderer() {
/*  634 */     return new DefaultTableCellHeaderRenderer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initializeLocalVars() {
/*  643 */     setOpaque(true);
/*  644 */     this.table = null;
/*  645 */     this.reorderingAllowed = true;
/*  646 */     this.resizingAllowed = true;
/*  647 */     this.draggedColumn = null;
/*  648 */     this.draggedDistance = 0;
/*  649 */     this.resizingColumn = null;
/*  650 */     this.updateTableInRealTime = true;
/*      */ 
/*      */ 
/*      */     
/*  654 */     ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
/*  655 */     toolTipManager.registerComponent(this);
/*  656 */     setDefaultRenderer(createDefaultRenderer());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resizeAndRepaint() {
/*  664 */     revalidate();
/*  665 */     repaint();
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
/*      */   public void setDraggedColumn(TableColumn paramTableColumn) {
/*  678 */     this.draggedColumn = paramTableColumn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDraggedDistance(int paramInt) {
/*  686 */     this.draggedDistance = paramInt;
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
/*      */   public void setResizingColumn(TableColumn paramTableColumn) {
/*  699 */     this.resizingColumn = paramTableColumn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*  708 */     paramObjectOutputStream.defaultWriteObject();
/*  709 */     if (this.ui != null && getUIClassID().equals("TableHeaderUI")) {
/*  710 */       this.ui.installUI(this);
/*      */     }
/*      */   }
/*      */   
/*      */   private int getWidthInRightToLeft() {
/*  715 */     if (this.table != null && this.table
/*  716 */       .getAutoResizeMode() != 0) {
/*  717 */       return this.table.getWidth();
/*      */     }
/*  719 */     return getWidth();
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
/*      */   protected String paramString() {
/*  735 */     String str1 = this.reorderingAllowed ? "true" : "false";
/*      */     
/*  737 */     String str2 = this.resizingAllowed ? "true" : "false";
/*      */     
/*  739 */     String str3 = this.updateTableInRealTime ? "true" : "false";
/*      */ 
/*      */     
/*  742 */     return super.paramString() + ",draggedDistance=" + this.draggedDistance + ",reorderingAllowed=" + str1 + ",resizingAllowed=" + str2 + ",updateTableInRealTime=" + str3;
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
/*      */   public AccessibleContext getAccessibleContext() {
/*  763 */     if (this.accessibleContext == null) {
/*  764 */       this.accessibleContext = new AccessibleJTableHeader();
/*      */     }
/*  766 */     return this.accessibleContext;
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
/*      */   protected class AccessibleJTableHeader
/*      */     extends JComponent.AccessibleJComponent
/*      */   {
/*      */     public AccessibleRole getAccessibleRole() {
/*  798 */       return AccessibleRole.PANEL;
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
/*      */     public Accessible getAccessibleAt(Point param1Point) {
/*      */       int i;
/*  814 */       if ((i = JTableHeader.this.columnAtPoint(param1Point)) != -1) {
/*  815 */         TableColumn tableColumn = JTableHeader.this.columnModel.getColumn(i);
/*  816 */         TableCellRenderer tableCellRenderer = tableColumn.getHeaderRenderer();
/*  817 */         if (tableCellRenderer == null) {
/*  818 */           if (JTableHeader.this.defaultRenderer != null) {
/*  819 */             tableCellRenderer = JTableHeader.this.defaultRenderer;
/*      */           } else {
/*  821 */             return null;
/*      */           } 
/*      */         }
/*  824 */         Component component = tableCellRenderer.getTableCellRendererComponent(JTableHeader.this
/*  825 */             .getTable(), tableColumn
/*  826 */             .getHeaderValue(), false, false, -1, i);
/*      */ 
/*      */         
/*  829 */         return new AccessibleJTableHeaderEntry(i, JTableHeader.this, JTableHeader.this.table);
/*      */       } 
/*  831 */       return null;
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
/*      */     public int getAccessibleChildrenCount() {
/*  843 */       return JTableHeader.this.columnModel.getColumnCount();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleChild(int param1Int) {
/*  853 */       if (param1Int < 0 || param1Int >= getAccessibleChildrenCount()) {
/*  854 */         return null;
/*      */       }
/*  856 */       TableColumn tableColumn = JTableHeader.this.columnModel.getColumn(param1Int);
/*      */       
/*  858 */       TableCellRenderer tableCellRenderer = tableColumn.getHeaderRenderer();
/*  859 */       if (tableCellRenderer == null) {
/*  860 */         if (JTableHeader.this.defaultRenderer != null) {
/*  861 */           tableCellRenderer = JTableHeader.this.defaultRenderer;
/*      */         } else {
/*  863 */           return null;
/*      */         } 
/*      */       }
/*  866 */       Component component = tableCellRenderer.getTableCellRendererComponent(JTableHeader.this
/*  867 */           .getTable(), tableColumn
/*  868 */           .getHeaderValue(), false, false, -1, param1Int);
/*      */ 
/*      */       
/*  871 */       return new AccessibleJTableHeaderEntry(param1Int, JTableHeader.this, JTableHeader.this.table);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected class AccessibleJTableHeaderEntry
/*      */       extends AccessibleContext
/*      */       implements Accessible, AccessibleComponent
/*      */     {
/*      */       private JTableHeader parent;
/*      */ 
/*      */       
/*      */       private int column;
/*      */ 
/*      */       
/*      */       private JTable table;
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleJTableHeaderEntry(int param2Int, JTableHeader param2JTableHeader, JTable param2JTable) {
/*  891 */         this.parent = param2JTableHeader;
/*  892 */         this.column = param2Int;
/*  893 */         this.table = param2JTable;
/*  894 */         setAccessibleParent(this.parent);
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
/*  906 */         return this;
/*      */       }
/*      */       
/*      */       private AccessibleContext getCurrentAccessibleContext() {
/*  910 */         TableColumnModel tableColumnModel = this.table.getColumnModel();
/*  911 */         if (tableColumnModel != null) {
/*      */ 
/*      */           
/*  914 */           if (this.column < 0 || this.column >= tableColumnModel.getColumnCount()) {
/*  915 */             return null;
/*      */           }
/*  917 */           TableColumn tableColumn = tableColumnModel.getColumn(this.column);
/*  918 */           TableCellRenderer tableCellRenderer = tableColumn.getHeaderRenderer();
/*  919 */           if (tableCellRenderer == null) {
/*  920 */             if (JTableHeader.this.defaultRenderer != null) {
/*  921 */               tableCellRenderer = JTableHeader.this.defaultRenderer;
/*      */             } else {
/*  923 */               return null;
/*      */             } 
/*      */           }
/*  926 */           Component component = tableCellRenderer.getTableCellRendererComponent(JTableHeader.this
/*  927 */               .getTable(), tableColumn
/*  928 */               .getHeaderValue(), false, false, -1, this.column);
/*      */           
/*  930 */           if (component instanceof Accessible) {
/*  931 */             return ((Accessible)component).getAccessibleContext();
/*      */           }
/*      */         } 
/*  934 */         return null;
/*      */       }
/*      */       
/*      */       private Component getCurrentComponent() {
/*  938 */         TableColumnModel tableColumnModel = this.table.getColumnModel();
/*  939 */         if (tableColumnModel != null) {
/*      */ 
/*      */           
/*  942 */           if (this.column < 0 || this.column >= tableColumnModel.getColumnCount()) {
/*  943 */             return null;
/*      */           }
/*  945 */           TableColumn tableColumn = tableColumnModel.getColumn(this.column);
/*  946 */           TableCellRenderer tableCellRenderer = tableColumn.getHeaderRenderer();
/*  947 */           if (tableCellRenderer == null) {
/*  948 */             if (JTableHeader.this.defaultRenderer != null) {
/*  949 */               tableCellRenderer = JTableHeader.this.defaultRenderer;
/*      */             } else {
/*  951 */               return null;
/*      */             } 
/*      */           }
/*  954 */           return tableCellRenderer.getTableCellRendererComponent(JTableHeader.this
/*  955 */               .getTable(), tableColumn
/*  956 */               .getHeaderValue(), false, false, -1, this.column);
/*      */         } 
/*      */         
/*  959 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String getAccessibleName() {
/*  966 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/*  967 */         if (accessibleContext != null) {
/*  968 */           String str1 = accessibleContext.getAccessibleName();
/*  969 */           if (str1 != null && str1 != "")
/*      */           {
/*  971 */             return str1;
/*      */           }
/*      */         } 
/*  974 */         if (this.accessibleName != null && this.accessibleName != "") {
/*  975 */           return this.accessibleName;
/*      */         }
/*      */         
/*  978 */         String str = (String)JTableHeader.this.getClientProperty("AccessibleName");
/*  979 */         if (str != null) {
/*  980 */           return str;
/*      */         }
/*  982 */         return this.table.getColumnName(this.column);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleName(String param2String) {
/*  988 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/*  989 */         if (accessibleContext != null) {
/*  990 */           accessibleContext.setAccessibleName(param2String);
/*      */         } else {
/*  992 */           super.setAccessibleName(param2String);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String getAccessibleDescription() {
/* 1000 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1001 */         if (accessibleContext != null) {
/* 1002 */           return accessibleContext.getAccessibleDescription();
/*      */         }
/* 1004 */         return super.getAccessibleDescription();
/*      */       }
/*      */ 
/*      */       
/*      */       public void setAccessibleDescription(String param2String) {
/* 1009 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1010 */         if (accessibleContext != null) {
/* 1011 */           accessibleContext.setAccessibleDescription(param2String);
/*      */         } else {
/* 1013 */           super.setAccessibleDescription(param2String);
/*      */         } 
/*      */       }
/*      */       
/*      */       public AccessibleRole getAccessibleRole() {
/* 1018 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1019 */         if (accessibleContext != null) {
/* 1020 */           return accessibleContext.getAccessibleRole();
/*      */         }
/* 1022 */         return AccessibleRole.COLUMN_HEADER;
/*      */       }
/*      */ 
/*      */       
/*      */       public AccessibleStateSet getAccessibleStateSet() {
/* 1027 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1028 */         if (accessibleContext != null) {
/* 1029 */           AccessibleStateSet accessibleStateSet = accessibleContext.getAccessibleStateSet();
/* 1030 */           if (isShowing()) {
/* 1031 */             accessibleStateSet.add(AccessibleState.SHOWING);
/*      */           }
/* 1033 */           return accessibleStateSet;
/*      */         } 
/* 1035 */         return new AccessibleStateSet();
/*      */       }
/*      */ 
/*      */       
/*      */       public int getAccessibleIndexInParent() {
/* 1040 */         return this.column;
/*      */       }
/*      */       
/*      */       public int getAccessibleChildrenCount() {
/* 1044 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1045 */         if (accessibleContext != null) {
/* 1046 */           return accessibleContext.getAccessibleChildrenCount();
/*      */         }
/* 1048 */         return 0;
/*      */       }
/*      */ 
/*      */       
/*      */       public Accessible getAccessibleChild(int param2Int) {
/* 1053 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1054 */         if (accessibleContext != null) {
/* 1055 */           Accessible accessible = accessibleContext.getAccessibleChild(param2Int);
/* 1056 */           accessibleContext.setAccessibleParent(this);
/* 1057 */           return accessible;
/*      */         } 
/* 1059 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public Locale getLocale() {
/* 1064 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1065 */         if (accessibleContext != null) {
/* 1066 */           return accessibleContext.getLocale();
/*      */         }
/* 1068 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public void addPropertyChangeListener(PropertyChangeListener param2PropertyChangeListener) {
/* 1073 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1074 */         if (accessibleContext != null) {
/* 1075 */           accessibleContext.addPropertyChangeListener(param2PropertyChangeListener);
/*      */         } else {
/* 1077 */           super.addPropertyChangeListener(param2PropertyChangeListener);
/*      */         } 
/*      */       }
/*      */       
/*      */       public void removePropertyChangeListener(PropertyChangeListener param2PropertyChangeListener) {
/* 1082 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1083 */         if (accessibleContext != null) {
/* 1084 */           accessibleContext.removePropertyChangeListener(param2PropertyChangeListener);
/*      */         } else {
/* 1086 */           super.removePropertyChangeListener(param2PropertyChangeListener);
/*      */         } 
/*      */       }
/*      */       
/*      */       public AccessibleAction getAccessibleAction() {
/* 1091 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1092 */         if (accessibleContext != null) {
/* 1093 */           return accessibleContext.getAccessibleAction();
/*      */         }
/* 1095 */         return null;
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
/*      */       public AccessibleComponent getAccessibleComponent() {
/* 1108 */         return this;
/*      */       }
/*      */       
/*      */       public AccessibleSelection getAccessibleSelection() {
/* 1112 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1113 */         if (accessibleContext != null) {
/* 1114 */           return accessibleContext.getAccessibleSelection();
/*      */         }
/* 1116 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public AccessibleText getAccessibleText() {
/* 1121 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1122 */         if (accessibleContext != null) {
/* 1123 */           return accessibleContext.getAccessibleText();
/*      */         }
/* 1125 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public AccessibleValue getAccessibleValue() {
/* 1130 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1131 */         if (accessibleContext != null) {
/* 1132 */           return accessibleContext.getAccessibleValue();
/*      */         }
/* 1134 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Color getBackground() {
/* 1142 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1143 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1144 */           return ((AccessibleComponent)accessibleContext).getBackground();
/*      */         }
/* 1146 */         Component component = getCurrentComponent();
/* 1147 */         if (component != null) {
/* 1148 */           return component.getBackground();
/*      */         }
/* 1150 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setBackground(Color param2Color) {
/* 1156 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1157 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1158 */           ((AccessibleComponent)accessibleContext).setBackground(param2Color);
/*      */         } else {
/* 1160 */           Component component = getCurrentComponent();
/* 1161 */           if (component != null) {
/* 1162 */             component.setBackground(param2Color);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Color getForeground() {
/* 1168 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1169 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1170 */           return ((AccessibleComponent)accessibleContext).getForeground();
/*      */         }
/* 1172 */         Component component = getCurrentComponent();
/* 1173 */         if (component != null) {
/* 1174 */           return component.getForeground();
/*      */         }
/* 1176 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setForeground(Color param2Color) {
/* 1182 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1183 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1184 */           ((AccessibleComponent)accessibleContext).setForeground(param2Color);
/*      */         } else {
/* 1186 */           Component component = getCurrentComponent();
/* 1187 */           if (component != null) {
/* 1188 */             component.setForeground(param2Color);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Cursor getCursor() {
/* 1194 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1195 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1196 */           return ((AccessibleComponent)accessibleContext).getCursor();
/*      */         }
/* 1198 */         Component component = getCurrentComponent();
/* 1199 */         if (component != null) {
/* 1200 */           return component.getCursor();
/*      */         }
/* 1202 */         Accessible accessible = getAccessibleParent();
/* 1203 */         if (accessible instanceof AccessibleComponent) {
/* 1204 */           return ((AccessibleComponent)accessible).getCursor();
/*      */         }
/* 1206 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setCursor(Cursor param2Cursor) {
/* 1213 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1214 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1215 */           ((AccessibleComponent)accessibleContext).setCursor(param2Cursor);
/*      */         } else {
/* 1217 */           Component component = getCurrentComponent();
/* 1218 */           if (component != null) {
/* 1219 */             component.setCursor(param2Cursor);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Font getFont() {
/* 1225 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1226 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1227 */           return ((AccessibleComponent)accessibleContext).getFont();
/*      */         }
/* 1229 */         Component component = getCurrentComponent();
/* 1230 */         if (component != null) {
/* 1231 */           return component.getFont();
/*      */         }
/* 1233 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setFont(Font param2Font) {
/* 1239 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1240 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1241 */           ((AccessibleComponent)accessibleContext).setFont(param2Font);
/*      */         } else {
/* 1243 */           Component component = getCurrentComponent();
/* 1244 */           if (component != null) {
/* 1245 */             component.setFont(param2Font);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public FontMetrics getFontMetrics(Font param2Font) {
/* 1251 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1252 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1253 */           return ((AccessibleComponent)accessibleContext).getFontMetrics(param2Font);
/*      */         }
/* 1255 */         Component component = getCurrentComponent();
/* 1256 */         if (component != null) {
/* 1257 */           return component.getFontMetrics(param2Font);
/*      */         }
/* 1259 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean isEnabled() {
/* 1265 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1266 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1267 */           return ((AccessibleComponent)accessibleContext).isEnabled();
/*      */         }
/* 1269 */         Component component = getCurrentComponent();
/* 1270 */         if (component != null) {
/* 1271 */           return component.isEnabled();
/*      */         }
/* 1273 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setEnabled(boolean param2Boolean) {
/* 1279 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1280 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1281 */           ((AccessibleComponent)accessibleContext).setEnabled(param2Boolean);
/*      */         } else {
/* 1283 */           Component component = getCurrentComponent();
/* 1284 */           if (component != null) {
/* 1285 */             component.setEnabled(param2Boolean);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public boolean isVisible() {
/* 1291 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1292 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1293 */           return ((AccessibleComponent)accessibleContext).isVisible();
/*      */         }
/* 1295 */         Component component = getCurrentComponent();
/* 1296 */         if (component != null) {
/* 1297 */           return component.isVisible();
/*      */         }
/* 1299 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setVisible(boolean param2Boolean) {
/* 1305 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1306 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1307 */           ((AccessibleComponent)accessibleContext).setVisible(param2Boolean);
/*      */         } else {
/* 1309 */           Component component = getCurrentComponent();
/* 1310 */           if (component != null) {
/* 1311 */             component.setVisible(param2Boolean);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public boolean isShowing() {
/* 1317 */         if (isVisible() && JTableHeader.this.isShowing()) {
/* 1318 */           return true;
/*      */         }
/* 1320 */         return false;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean contains(Point param2Point) {
/* 1325 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1326 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1327 */           Rectangle rectangle = ((AccessibleComponent)accessibleContext).getBounds();
/* 1328 */           return rectangle.contains(param2Point);
/*      */         } 
/* 1330 */         Component component = getCurrentComponent();
/* 1331 */         if (component != null) {
/* 1332 */           Rectangle rectangle = component.getBounds();
/* 1333 */           return rectangle.contains(param2Point);
/*      */         } 
/* 1335 */         return getBounds().contains(param2Point);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public Point getLocationOnScreen() {
/* 1341 */         if (this.parent != null) {
/* 1342 */           Point point1 = this.parent.getLocationOnScreen();
/* 1343 */           Point point2 = getLocation();
/* 1344 */           point2.translate(point1.x, point1.y);
/* 1345 */           return point2;
/*      */         } 
/* 1347 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public Point getLocation() {
/* 1352 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1353 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1354 */           Rectangle rectangle = ((AccessibleComponent)accessibleContext).getBounds();
/* 1355 */           return rectangle.getLocation();
/*      */         } 
/* 1357 */         Component component = getCurrentComponent();
/* 1358 */         if (component != null) {
/* 1359 */           Rectangle rectangle = component.getBounds();
/* 1360 */           return rectangle.getLocation();
/*      */         } 
/* 1362 */         return getBounds().getLocation();
/*      */       }
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
/* 1374 */         Rectangle rectangle = this.table.getCellRect(-1, this.column, false);
/* 1375 */         rectangle.y = 0;
/* 1376 */         return rectangle;
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
/*      */       public void setBounds(Rectangle param2Rectangle) {
/* 1394 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1395 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1396 */           ((AccessibleComponent)accessibleContext).setBounds(param2Rectangle);
/*      */         } else {
/* 1398 */           Component component = getCurrentComponent();
/* 1399 */           if (component != null) {
/* 1400 */             component.setBounds(param2Rectangle);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Dimension getSize() {
/* 1406 */         return getBounds().getSize();
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
/*      */       public void setSize(Dimension param2Dimension) {
/* 1423 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1424 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1425 */           ((AccessibleComponent)accessibleContext).setSize(param2Dimension);
/*      */         } else {
/* 1427 */           Component component = getCurrentComponent();
/* 1428 */           if (component != null) {
/* 1429 */             component.setSize(param2Dimension);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Accessible getAccessibleAt(Point param2Point) {
/* 1435 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1436 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1437 */           return ((AccessibleComponent)accessibleContext).getAccessibleAt(param2Point);
/*      */         }
/* 1439 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean isFocusTraversable() {
/* 1444 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1445 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1446 */           return ((AccessibleComponent)accessibleContext).isFocusTraversable();
/*      */         }
/* 1448 */         Component component = getCurrentComponent();
/* 1449 */         if (component != null) {
/* 1450 */           return component.isFocusTraversable();
/*      */         }
/* 1452 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void requestFocus() {
/* 1458 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1459 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1460 */           ((AccessibleComponent)accessibleContext).requestFocus();
/*      */         } else {
/* 1462 */           Component component = getCurrentComponent();
/* 1463 */           if (component != null) {
/* 1464 */             component.requestFocus();
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public void addFocusListener(FocusListener param2FocusListener) {
/* 1470 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1471 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1472 */           ((AccessibleComponent)accessibleContext).addFocusListener(param2FocusListener);
/*      */         } else {
/* 1474 */           Component component = getCurrentComponent();
/* 1475 */           if (component != null) {
/* 1476 */             component.addFocusListener(param2FocusListener);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public void removeFocusListener(FocusListener param2FocusListener) {
/* 1482 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1483 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1484 */           ((AccessibleComponent)accessibleContext).removeFocusListener(param2FocusListener);
/*      */         } else {
/* 1486 */           Component component = getCurrentComponent();
/* 1487 */           if (component != null)
/* 1488 */             component.removeFocusListener(param2FocusListener); 
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/table/JTableHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */