/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.IllegalComponentStateException;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.beans.Transient;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Vector;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleAction;
/*      */ import javax.accessibility.AccessibleComponent;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleIcon;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleSelection;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.accessibility.AccessibleText;
/*      */ import javax.accessibility.AccessibleValue;
/*      */ import javax.swing.event.ListDataEvent;
/*      */ import javax.swing.event.ListDataListener;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import javax.swing.plaf.ListUI;
/*      */ import javax.swing.text.Position;
/*      */ import sun.awt.AWTAccessor;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JList<E>
/*      */   extends JComponent
/*      */   implements Scrollable, Accessible
/*      */ {
/*      */   private static final String uiClassID = "ListUI";
/*      */   public static final int VERTICAL = 0;
/*      */   public static final int VERTICAL_WRAP = 1;
/*      */   public static final int HORIZONTAL_WRAP = 2;
/*  314 */   private int fixedCellWidth = -1;
/*  315 */   private int fixedCellHeight = -1;
/*  316 */   private int horizontalScrollIncrement = -1;
/*      */   private E prototypeCellValue;
/*  318 */   private int visibleRowCount = 8;
/*      */   
/*      */   private Color selectionForeground;
/*      */   
/*      */   private Color selectionBackground;
/*      */   
/*      */   private boolean dragEnabled;
/*      */   
/*      */   private ListSelectionModel selectionModel;
/*      */   
/*      */   private ListModel<E> dataModel;
/*      */   
/*      */   private ListCellRenderer<? super E> cellRenderer;
/*      */   
/*      */   private ListSelectionListener selectionListener;
/*      */   
/*      */   private int layoutOrientation;
/*      */   
/*  336 */   private DropMode dropMode = DropMode.USE_SELECTION;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient DropLocation dropLocation;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class DropLocation
/*      */     extends TransferHandler.DropLocation
/*      */   {
/*      */     private final int index;
/*      */ 
/*      */     
/*      */     private final boolean isInsert;
/*      */ 
/*      */ 
/*      */     
/*      */     private DropLocation(Point param1Point, int param1Int, boolean param1Boolean) {
/*  355 */       super(param1Point);
/*  356 */       this.index = param1Int;
/*  357 */       this.isInsert = param1Boolean;
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
/*      */     public int getIndex() {
/*  378 */       return this.index;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isInsert() {
/*  388 */       return this.isInsert;
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
/*  400 */       return getClass().getName() + "[dropPoint=" + 
/*  401 */         getDropPoint() + ",index=" + this.index + ",insert=" + this.isInsert + "]";
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
/*      */   public JList(ListModel<E> paramListModel) {
/*  420 */     if (paramListModel == null) {
/*  421 */       throw new IllegalArgumentException("dataModel must be non null");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  426 */     ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
/*  427 */     toolTipManager.registerComponent(this);
/*      */     
/*  429 */     this.layoutOrientation = 0;
/*      */     
/*  431 */     this.dataModel = paramListModel;
/*  432 */     this.selectionModel = createSelectionModel();
/*  433 */     setAutoscrolls(true);
/*  434 */     setOpaque(true);
/*  435 */     updateUI();
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
/*      */   public JList(E[] paramArrayOfE) {
/*  455 */     this(new AbstractListModel<E>((Object[])paramArrayOfE) {
/*      */           public int getSize() {
/*  457 */             return listData.length; } public E getElementAt(int param1Int) {
/*  458 */             return (E)listData[param1Int];
/*      */           }
/*      */         });
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
/*      */   public JList(Vector<? extends E> paramVector) {
/*  479 */     this(new AbstractListModel<E>(paramVector) {
/*      */           public int getSize() {
/*  481 */             return listData.size(); } public E getElementAt(int param1Int) {
/*  482 */             return listData.elementAt(param1Int);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JList() {
/*  492 */     this(new AbstractListModel<E>() {
/*      */           public int getSize() {
/*  494 */             return 0; } public E getElementAt(int param1Int) {
/*  495 */             throw new IndexOutOfBoundsException("No Data Model");
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ListUI getUI() {
/*  508 */     return (ListUI)this.ui;
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
/*      */   public void setUI(ListUI paramListUI) {
/*  525 */     setUI(paramListUI);
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
/*      */   public void updateUI() {
/*  540 */     setUI((ListUI)UIManager.getUI(this));
/*      */     
/*  542 */     ListCellRenderer<? super E> listCellRenderer = getCellRenderer();
/*  543 */     if (listCellRenderer instanceof Component) {
/*  544 */       SwingUtilities.updateComponentTreeUI((Component)listCellRenderer);
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
/*  559 */     return "ListUI";
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
/*      */   private void updateFixedCellSize() {
/*  576 */     ListCellRenderer<? super E> listCellRenderer = getCellRenderer();
/*  577 */     E e = getPrototypeCellValue();
/*      */     
/*  579 */     if (listCellRenderer != null && e != null) {
/*  580 */       Component component = listCellRenderer.getListCellRendererComponent(this, e, 0, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  588 */       Font font = component.getFont();
/*  589 */       component.setFont(getFont());
/*      */       
/*  591 */       Dimension dimension = component.getPreferredSize();
/*  592 */       this.fixedCellWidth = dimension.width;
/*  593 */       this.fixedCellHeight = dimension.height;
/*      */       
/*  595 */       component.setFont(font);
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
/*      */   public E getPrototypeCellValue() {
/*  609 */     return this.prototypeCellValue;
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
/*      */   public void setPrototypeCellValue(E paramE) {
/*  649 */     E e = this.prototypeCellValue;
/*  650 */     this.prototypeCellValue = paramE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  656 */     if (paramE != null && !paramE.equals(e)) {
/*  657 */       updateFixedCellSize();
/*      */     }
/*      */     
/*  660 */     firePropertyChange("prototypeCellValue", e, paramE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFixedCellWidth() {
/*  671 */     return this.fixedCellWidth;
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
/*      */   public void setFixedCellWidth(int paramInt) {
/*  694 */     int i = this.fixedCellWidth;
/*  695 */     this.fixedCellWidth = paramInt;
/*  696 */     firePropertyChange("fixedCellWidth", i, this.fixedCellWidth);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFixedCellHeight() {
/*  707 */     return this.fixedCellHeight;
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
/*      */   public void setFixedCellHeight(int paramInt) {
/*  730 */     int i = this.fixedCellHeight;
/*  731 */     this.fixedCellHeight = paramInt;
/*  732 */     firePropertyChange("fixedCellHeight", i, this.fixedCellHeight);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Transient
/*      */   public ListCellRenderer<? super E> getCellRenderer() {
/*  744 */     return this.cellRenderer;
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
/*      */   public void setCellRenderer(ListCellRenderer<? super E> paramListCellRenderer) {
/*  772 */     ListCellRenderer<? super E> listCellRenderer = this.cellRenderer;
/*  773 */     this.cellRenderer = paramListCellRenderer;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  778 */     if (paramListCellRenderer != null && !paramListCellRenderer.equals(listCellRenderer)) {
/*  779 */       updateFixedCellSize();
/*      */     }
/*      */     
/*  782 */     firePropertyChange("cellRenderer", listCellRenderer, paramListCellRenderer);
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
/*      */   public Color getSelectionForeground() {
/*  797 */     return this.selectionForeground;
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
/*      */   public void setSelectionForeground(Color paramColor) {
/*  827 */     Color color = this.selectionForeground;
/*  828 */     this.selectionForeground = paramColor;
/*  829 */     firePropertyChange("selectionForeground", color, paramColor);
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
/*      */   public Color getSelectionBackground() {
/*  844 */     return this.selectionBackground;
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
/*      */   public void setSelectionBackground(Color paramColor) {
/*  874 */     Color color = this.selectionBackground;
/*  875 */     this.selectionBackground = paramColor;
/*  876 */     firePropertyChange("selectionBackground", color, paramColor);
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
/*      */   public int getVisibleRowCount() {
/*  889 */     return this.visibleRowCount;
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
/*      */   public void setVisibleRowCount(int paramInt) {
/*  930 */     int i = this.visibleRowCount;
/*  931 */     this.visibleRowCount = Math.max(0, paramInt);
/*  932 */     firePropertyChange("visibleRowCount", i, paramInt);
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
/*      */   public int getLayoutOrientation() {
/*  948 */     return this.layoutOrientation;
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
/*      */   public void setLayoutOrientation(int paramInt) {
/* 1011 */     int i = this.layoutOrientation;
/* 1012 */     switch (paramInt) {
/*      */       case 0:
/*      */       case 1:
/*      */       case 2:
/* 1016 */         this.layoutOrientation = paramInt;
/* 1017 */         firePropertyChange("layoutOrientation", i, paramInt);
/*      */         return;
/*      */     } 
/* 1020 */     throw new IllegalArgumentException("layoutOrientation must be one of: VERTICAL, HORIZONTAL_WRAP or VERTICAL_WRAP");
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
/*      */   public int getFirstVisibleIndex() {
/*      */     int i;
/* 1038 */     Rectangle rectangle = getVisibleRect();
/*      */     
/* 1040 */     if (getComponentOrientation().isLeftToRight()) {
/* 1041 */       i = locationToIndex(rectangle.getLocation());
/*      */     } else {
/* 1043 */       i = locationToIndex(new Point(rectangle.x + rectangle.width - 1, rectangle.y));
/*      */     } 
/* 1045 */     if (i != -1) {
/* 1046 */       Rectangle rectangle1 = getCellBounds(i, i);
/* 1047 */       if (rectangle1 != null) {
/* 1048 */         SwingUtilities.computeIntersection(rectangle.x, rectangle.y, rectangle.width, rectangle.height, rectangle1);
/* 1049 */         if (rectangle1.width == 0 || rectangle1.height == 0) {
/* 1050 */           i = -1;
/*      */         }
/*      */       } 
/*      */     } 
/* 1054 */     return i;
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
/*      */   public int getLastVisibleIndex() {
/*      */     Point point;
/* 1068 */     boolean bool = getComponentOrientation().isLeftToRight();
/* 1069 */     Rectangle rectangle = getVisibleRect();
/*      */     
/* 1071 */     if (bool) {
/* 1072 */       point = new Point(rectangle.x + rectangle.width - 1, rectangle.y + rectangle.height - 1);
/*      */     } else {
/* 1074 */       point = new Point(rectangle.x, rectangle.y + rectangle.height - 1);
/*      */     } 
/* 1076 */     int i = locationToIndex(point);
/*      */     
/* 1078 */     if (i != -1) {
/* 1079 */       Rectangle rectangle1 = getCellBounds(i, i);
/*      */       
/* 1081 */       if (rectangle1 != null) {
/* 1082 */         SwingUtilities.computeIntersection(rectangle.x, rectangle.y, rectangle.width, rectangle.height, rectangle1);
/* 1083 */         if (rectangle1.width == 0 || rectangle1.height == 0) {
/*      */           int j;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1089 */           boolean bool1 = (getLayoutOrientation() == 2) ? true : false;
/* 1090 */           Point point1 = bool1 ? new Point(point.x, rectangle.y) : new Point(rectangle.x, point.y);
/*      */ 
/*      */ 
/*      */           
/* 1094 */           int k = -1;
/* 1095 */           int m = i;
/* 1096 */           i = -1;
/*      */           
/*      */           do {
/* 1099 */             j = k;
/* 1100 */             k = locationToIndex(point1);
/*      */             
/* 1102 */             if (k == -1)
/* 1103 */               continue;  rectangle1 = getCellBounds(k, k);
/* 1104 */             if (k != m && rectangle1 != null && rectangle1
/* 1105 */               .contains(point1)) {
/* 1106 */               i = k;
/* 1107 */               if (bool1) {
/* 1108 */                 point1.y = rectangle1.y + rectangle1.height;
/* 1109 */                 if (point1.y >= point.y)
/*      */                 {
/* 1111 */                   j = k;
/*      */                 }
/*      */               } else {
/*      */                 
/* 1115 */                 point1.x = rectangle1.x + rectangle1.width;
/* 1116 */                 if (point1.x >= point.x)
/*      */                 {
/* 1118 */                   j = k;
/*      */                 }
/*      */               }
/*      */             
/*      */             } else {
/*      */               
/* 1124 */               j = k;
/*      */             }
/*      */           
/* 1127 */           } while (k != -1 && j != k);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1131 */     return i;
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
/*      */   public void ensureIndexIsVisible(int paramInt) {
/* 1149 */     Rectangle rectangle = getCellBounds(paramInt, paramInt);
/* 1150 */     if (rectangle != null) {
/* 1151 */       scrollRectToVisible(rectangle);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDragEnabled(boolean paramBoolean) {
/* 1189 */     if (paramBoolean && GraphicsEnvironment.isHeadless()) {
/* 1190 */       throw new HeadlessException();
/*      */     }
/* 1192 */     this.dragEnabled = paramBoolean;
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
/* 1203 */     return this.dragEnabled;
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
/*      */   public final void setDropMode(DropMode paramDropMode) {
/* 1234 */     if (paramDropMode != null) {
/* 1235 */       switch (paramDropMode) {
/*      */         case USE_SELECTION:
/*      */         case ON:
/*      */         case INSERT:
/*      */         case ON_OR_INSERT:
/* 1240 */           this.dropMode = paramDropMode;
/*      */           return;
/*      */       } 
/*      */     
/*      */     }
/* 1245 */     throw new IllegalArgumentException(paramDropMode + ": Unsupported drop mode for list");
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
/* 1256 */     return this.dropMode;
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
/* 1267 */     DropLocation dropLocation = null;
/* 1268 */     Rectangle rectangle = null;
/*      */     
/* 1270 */     int i = locationToIndex(paramPoint);
/* 1271 */     if (i != -1) {
/* 1272 */       rectangle = getCellBounds(i, i);
/*      */     }
/*      */     
/* 1275 */     switch (this.dropMode)
/*      */     
/*      */     { case USE_SELECTION:
/*      */       case ON:
/* 1279 */         dropLocation = new DropLocation(paramPoint, (rectangle != null && rectangle.contains(paramPoint)) ? i : -1, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1346 */         return dropLocation;case INSERT: if (i == -1) { dropLocation = new DropLocation(paramPoint, getModel().getSize(), true); } else { if (this.layoutOrientation == 2) { boolean bool = getComponentOrientation().isLeftToRight(); if (SwingUtilities2.liesInHorizontal(rectangle, paramPoint, bool, false) == SwingUtilities2.Section.TRAILING) { i++; } else if (i == getModel().getSize() - 1 && paramPoint.y >= rectangle.y + rectangle.height) { i++; }  } else if (SwingUtilities2.liesInVertical(rectangle, paramPoint, false) == SwingUtilities2.Section.TRAILING) { i++; }  dropLocation = new DropLocation(paramPoint, i, true); }  return dropLocation;case ON_OR_INSERT: if (i == -1) { dropLocation = new DropLocation(paramPoint, getModel().getSize(), true); } else { boolean bool = false; if (this.layoutOrientation == 2) { boolean bool1 = getComponentOrientation().isLeftToRight(); SwingUtilities2.Section section = SwingUtilities2.liesInHorizontal(rectangle, paramPoint, bool1, true); if (section == SwingUtilities2.Section.TRAILING) { i++; bool = true; } else if (i == getModel().getSize() - 1 && paramPoint.y >= rectangle.y + rectangle.height) { i++; bool = true; } else if (section == SwingUtilities2.Section.LEADING) { bool = true; }  } else { SwingUtilities2.Section section = SwingUtilities2.liesInVertical(rectangle, paramPoint, true); if (section == SwingUtilities2.Section.LEADING) { bool = true; } else if (section == SwingUtilities2.Section.TRAILING) { i++; bool = true; }  }  dropLocation = new DropLocation(paramPoint, i, bool); }  return dropLocation; }  assert false : "Unexpected drop mode"; return dropLocation;
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
/* 1386 */     Object object = null;
/* 1387 */     DropLocation dropLocation1 = (DropLocation)paramDropLocation;
/*      */     
/* 1389 */     if (this.dropMode == DropMode.USE_SELECTION) {
/* 1390 */       if (dropLocation1 == null) {
/* 1391 */         if (!paramBoolean && paramObject != null) {
/* 1392 */           setSelectedIndices(((int[][])paramObject)[0]);
/*      */           
/* 1394 */           int i = ((int[][])paramObject)[1][0];
/* 1395 */           int j = ((int[][])paramObject)[1][1];
/*      */           
/* 1397 */           SwingUtilities2.setLeadAnchorWithoutSelection(
/* 1398 */               getSelectionModel(), j, i);
/*      */         } 
/*      */       } else {
/* 1401 */         if (this.dropLocation == null) {
/* 1402 */           int[] arrayOfInt = getSelectedIndices();
/*      */           
/* 1404 */           object = new int[][] { arrayOfInt, { getAnchorSelectionIndex(), getLeadSelectionIndex() } };
/*      */         } else {
/* 1406 */           object = paramObject;
/*      */         } 
/*      */         
/* 1409 */         int i = dropLocation1.getIndex();
/* 1410 */         if (i == -1) {
/* 1411 */           clearSelection();
/* 1412 */           getSelectionModel().setAnchorSelectionIndex(-1);
/* 1413 */           getSelectionModel().setLeadSelectionIndex(-1);
/*      */         } else {
/* 1415 */           setSelectionInterval(i, i);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1420 */     DropLocation dropLocation2 = this.dropLocation;
/* 1421 */     this.dropLocation = dropLocation1;
/* 1422 */     firePropertyChange("dropLocation", dropLocation2, this.dropLocation);
/*      */     
/* 1424 */     return object;
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
/*      */   public final DropLocation getDropLocation() {
/* 1453 */     return this.dropLocation;
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
/*      */   public int getNextMatch(String paramString, int paramInt, Position.Bias paramBias) {
/* 1471 */     ListModel<E> listModel = getModel();
/* 1472 */     int i = listModel.getSize();
/* 1473 */     if (paramString == null) {
/* 1474 */       throw new IllegalArgumentException();
/*      */     }
/* 1476 */     if (paramInt < 0 || paramInt >= i) {
/* 1477 */       throw new IllegalArgumentException();
/*      */     }
/* 1479 */     paramString = paramString.toUpperCase();
/*      */ 
/*      */     
/* 1482 */     byte b = (paramBias == Position.Bias.Forward) ? 1 : -1;
/* 1483 */     int j = paramInt;
/*      */     while (true) {
/* 1485 */       E e = listModel.getElementAt(j);
/*      */       
/* 1487 */       if (e != null) {
/*      */         String str;
/*      */         
/* 1490 */         if (e instanceof String) {
/* 1491 */           str = ((String)e).toUpperCase();
/*      */         } else {
/*      */           
/* 1494 */           str = e.toString();
/* 1495 */           if (str != null) {
/* 1496 */             str = str.toUpperCase();
/*      */           }
/*      */         } 
/*      */         
/* 1500 */         if (str != null && str.startsWith(paramString)) {
/* 1501 */           return j;
/*      */         }
/*      */       } 
/* 1504 */       j = (j + b + i) % i;
/* 1505 */       if (j == paramInt) {
/* 1506 */         return -1;
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
/*      */   public String getToolTipText(MouseEvent paramMouseEvent) {
/* 1529 */     if (paramMouseEvent != null) {
/* 1530 */       Point point = paramMouseEvent.getPoint();
/* 1531 */       int i = locationToIndex(point);
/* 1532 */       ListCellRenderer<? super E> listCellRenderer = getCellRenderer();
/*      */       
/*      */       Rectangle rectangle;
/* 1535 */       if (i != -1 && listCellRenderer != null && (
/* 1536 */         rectangle = getCellBounds(i, i)) != null && rectangle
/* 1537 */         .contains(point.x, point.y)) {
/* 1538 */         ListSelectionModel listSelectionModel = getSelectionModel();
/* 1539 */         Component component = listCellRenderer.getListCellRendererComponent(this, 
/* 1540 */             getModel().getElementAt(i), i, listSelectionModel
/* 1541 */             .isSelectedIndex(i), (
/* 1542 */             hasFocus() && listSelectionModel.getLeadSelectionIndex() == i));
/*      */ 
/*      */         
/* 1545 */         if (component instanceof JComponent) {
/*      */ 
/*      */           
/* 1548 */           point.translate(-rectangle.x, -rectangle.y);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1556 */           MouseEvent mouseEvent = new MouseEvent(component, paramMouseEvent.getID(), paramMouseEvent.getWhen(), paramMouseEvent.getModifiers(), point.x, point.y, paramMouseEvent.getXOnScreen(), paramMouseEvent.getYOnScreen(), paramMouseEvent.getClickCount(), paramMouseEvent.isPopupTrigger(), 0);
/*      */ 
/*      */           
/* 1559 */           AWTAccessor.MouseEventAccessor mouseEventAccessor = AWTAccessor.getMouseEventAccessor();
/* 1560 */           mouseEventAccessor.setCausedByTouchEvent(mouseEvent, mouseEventAccessor
/* 1561 */               .isCausedByTouchEvent(paramMouseEvent));
/*      */           
/* 1563 */           String str = ((JComponent)component).getToolTipText(mouseEvent);
/*      */ 
/*      */           
/* 1566 */           if (str != null) {
/* 1567 */             return str;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1572 */     return getToolTipText();
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
/*      */   public int locationToIndex(Point paramPoint) {
/* 1595 */     ListUI listUI = getUI();
/* 1596 */     return (listUI != null) ? listUI.locationToIndex(this, paramPoint) : -1;
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
/*      */   public Point indexToLocation(int paramInt) {
/* 1612 */     ListUI listUI = getUI();
/* 1613 */     return (listUI != null) ? listUI.indexToLocation(this, paramInt) : null;
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
/*      */   public Rectangle getCellBounds(int paramInt1, int paramInt2) {
/* 1636 */     ListUI listUI = getUI();
/* 1637 */     return (listUI != null) ? listUI.getCellBounds(this, paramInt1, paramInt2) : null;
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
/*      */   public ListModel<E> getModel() {
/* 1655 */     return this.dataModel;
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
/*      */   public void setModel(ListModel<E> paramListModel) {
/* 1677 */     if (paramListModel == null) {
/* 1678 */       throw new IllegalArgumentException("model must be non null");
/*      */     }
/* 1680 */     ListModel<E> listModel = this.dataModel;
/* 1681 */     this.dataModel = paramListModel;
/* 1682 */     firePropertyChange("model", listModel, this.dataModel);
/* 1683 */     clearSelection();
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
/*      */   public void setListData(final E[] listData) {
/* 1701 */     setModel(new AbstractListModel<E>() {
/*      */           public int getSize() {
/* 1703 */             return listData.length; } public E getElementAt(int param1Int) {
/* 1704 */             return (E)listData[param1Int];
/*      */           }
/*      */         });
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
/*      */   public void setListData(final Vector<? extends E> listData) {
/* 1724 */     setModel(new AbstractListModel<E>() {
/*      */           public int getSize() {
/* 1726 */             return listData.size(); } public E getElementAt(int param1Int) {
/* 1727 */             return listData.elementAt(param1Int);
/*      */           }
/*      */         });
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
/*      */   protected ListSelectionModel createSelectionModel() {
/* 1749 */     return new DefaultListSelectionModel();
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
/*      */   public ListSelectionModel getSelectionModel() {
/* 1765 */     return this.selectionModel;
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
/*      */   protected void fireSelectionValueChanged(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 1793 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 1794 */     ListSelectionEvent listSelectionEvent = null;
/*      */     
/* 1796 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1797 */       if (arrayOfObject[i] == ListSelectionListener.class) {
/* 1798 */         if (listSelectionEvent == null) {
/* 1799 */           listSelectionEvent = new ListSelectionEvent(this, paramInt1, paramInt2, paramBoolean);
/*      */         }
/*      */         
/* 1802 */         ((ListSelectionListener)arrayOfObject[i + 1]).valueChanged(listSelectionEvent);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class ListSelectionHandler
/*      */     implements ListSelectionListener, Serializable
/*      */   {
/*      */     private ListSelectionHandler() {}
/*      */ 
/*      */     
/*      */     public void valueChanged(ListSelectionEvent param1ListSelectionEvent) {
/* 1816 */       JList.this.fireSelectionValueChanged(param1ListSelectionEvent.getFirstIndex(), param1ListSelectionEvent
/* 1817 */           .getLastIndex(), param1ListSelectionEvent
/* 1818 */           .getValueIsAdjusting());
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
/*      */   public void addListSelectionListener(ListSelectionListener paramListSelectionListener) {
/* 1837 */     if (this.selectionListener == null) {
/* 1838 */       this.selectionListener = new ListSelectionHandler();
/* 1839 */       getSelectionModel().addListSelectionListener(this.selectionListener);
/*      */     } 
/*      */     
/* 1842 */     this.listenerList.add(ListSelectionListener.class, paramListSelectionListener);
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
/*      */   public void removeListSelectionListener(ListSelectionListener paramListSelectionListener) {
/* 1854 */     this.listenerList.remove(ListSelectionListener.class, paramListSelectionListener);
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
/*      */   public ListSelectionListener[] getListSelectionListeners() {
/* 1868 */     return this.listenerList.<ListSelectionListener>getListeners(ListSelectionListener.class);
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
/*      */   public void setSelectionModel(ListSelectionModel paramListSelectionModel) {
/* 1891 */     if (paramListSelectionModel == null) {
/* 1892 */       throw new IllegalArgumentException("selectionModel must be non null");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1898 */     if (this.selectionListener != null) {
/* 1899 */       this.selectionModel.removeListSelectionListener(this.selectionListener);
/* 1900 */       paramListSelectionModel.addListSelectionListener(this.selectionListener);
/*      */     } 
/*      */     
/* 1903 */     ListSelectionModel listSelectionModel = this.selectionModel;
/* 1904 */     this.selectionModel = paramListSelectionModel;
/* 1905 */     firePropertyChange("selectionModel", listSelectionModel, paramListSelectionModel);
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
/*      */   public void setSelectionMode(int paramInt) {
/* 1942 */     getSelectionModel().setSelectionMode(paramInt);
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
/*      */   public int getSelectionMode() {
/* 1954 */     return getSelectionModel().getSelectionMode();
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
/*      */   public int getAnchorSelectionIndex() {
/* 1966 */     return getSelectionModel().getAnchorSelectionIndex();
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
/*      */   public int getLeadSelectionIndex() {
/* 1980 */     return getSelectionModel().getLeadSelectionIndex();
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
/*      */   public int getMinSelectionIndex() {
/* 1993 */     return getSelectionModel().getMinSelectionIndex();
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
/*      */   public int getMaxSelectionIndex() {
/* 2006 */     return getSelectionModel().getMaxSelectionIndex();
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
/*      */   public boolean isSelectedIndex(int paramInt) {
/* 2022 */     return getSelectionModel().isSelectedIndex(paramInt);
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
/*      */   public boolean isSelectionEmpty() {
/* 2036 */     return getSelectionModel().isSelectionEmpty();
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
/*      */   public void clearSelection() {
/* 2049 */     getSelectionModel().clearSelection();
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
/*      */   public void setSelectionInterval(int paramInt1, int paramInt2) {
/* 2071 */     getSelectionModel().setSelectionInterval(paramInt1, paramInt2);
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
/*      */   public void addSelectionInterval(int paramInt1, int paramInt2) {
/* 2094 */     getSelectionModel().addSelectionInterval(paramInt1, paramInt2);
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
/*      */   public void removeSelectionInterval(int paramInt1, int paramInt2) {
/* 2117 */     getSelectionModel().removeSelectionInterval(paramInt1, paramInt2);
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
/*      */   public void setValueIsAdjusting(boolean paramBoolean) {
/* 2146 */     getSelectionModel().setValueIsAdjusting(paramBoolean);
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
/*      */   public boolean getValueIsAdjusting() {
/* 2162 */     return getSelectionModel().getValueIsAdjusting();
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
/*      */   @Transient
/*      */   public int[] getSelectedIndices() {
/* 2177 */     ListSelectionModel listSelectionModel = getSelectionModel();
/* 2178 */     int i = listSelectionModel.getMinSelectionIndex();
/* 2179 */     int j = listSelectionModel.getMaxSelectionIndex();
/*      */     
/* 2181 */     if (i < 0 || j < 0) {
/* 2182 */       return new int[0];
/*      */     }
/*      */     
/* 2185 */     int[] arrayOfInt1 = new int[1 + j - i];
/* 2186 */     byte b = 0;
/* 2187 */     for (int k = i; k <= j; k++) {
/* 2188 */       if (listSelectionModel.isSelectedIndex(k)) {
/* 2189 */         arrayOfInt1[b++] = k;
/*      */       }
/*      */     } 
/* 2192 */     int[] arrayOfInt2 = new int[b];
/* 2193 */     System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, b);
/* 2194 */     return arrayOfInt2;
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
/*      */   public void setSelectedIndex(int paramInt) {
/* 2213 */     if (paramInt >= getModel().getSize()) {
/*      */       return;
/*      */     }
/* 2216 */     getSelectionModel().setSelectionInterval(paramInt, paramInt);
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
/*      */   public void setSelectedIndices(int[] paramArrayOfint) {
/* 2236 */     ListSelectionModel listSelectionModel = getSelectionModel();
/* 2237 */     listSelectionModel.clearSelection();
/* 2238 */     int i = getModel().getSize();
/* 2239 */     for (int j : paramArrayOfint) {
/* 2240 */       if (j < i) {
/* 2241 */         listSelectionModel.addSelectionInterval(j, j);
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
/*      */   @Deprecated
/*      */   public Object[] getSelectedValues() {
/* 2260 */     ListSelectionModel listSelectionModel = getSelectionModel();
/* 2261 */     ListModel<E> listModel = getModel();
/*      */     
/* 2263 */     int i = listSelectionModel.getMinSelectionIndex();
/* 2264 */     int j = listSelectionModel.getMaxSelectionIndex();
/*      */     
/* 2266 */     if (i < 0 || j < 0) {
/* 2267 */       return new Object[0];
/*      */     }
/*      */     
/* 2270 */     Object[] arrayOfObject1 = new Object[1 + j - i];
/* 2271 */     byte b = 0;
/* 2272 */     for (int k = i; k <= j; k++) {
/* 2273 */       if (listSelectionModel.isSelectedIndex(k)) {
/* 2274 */         arrayOfObject1[b++] = listModel.getElementAt(k);
/*      */       }
/*      */     } 
/* 2277 */     Object[] arrayOfObject2 = new Object[b];
/* 2278 */     System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, b);
/* 2279 */     return arrayOfObject2;
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
/*      */   public List<E> getSelectedValuesList() {
/* 2294 */     ListSelectionModel listSelectionModel = getSelectionModel();
/* 2295 */     ListModel<E> listModel = getModel();
/*      */     
/* 2297 */     int i = listSelectionModel.getMinSelectionIndex();
/* 2298 */     int j = listSelectionModel.getMaxSelectionIndex();
/*      */     
/* 2300 */     if (i < 0 || j < 0) {
/* 2301 */       return Collections.emptyList();
/*      */     }
/*      */     
/* 2304 */     ArrayList<E> arrayList = new ArrayList();
/* 2305 */     for (int k = i; k <= j; k++) {
/* 2306 */       if (listSelectionModel.isSelectedIndex(k)) {
/* 2307 */         arrayList.add(listModel.getElementAt(k));
/*      */       }
/*      */     } 
/* 2310 */     return arrayList;
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
/*      */   public int getSelectedIndex() {
/* 2327 */     return getMinSelectionIndex();
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
/*      */   public E getSelectedValue() {
/* 2346 */     int i = getMinSelectionIndex();
/* 2347 */     return (i == -1) ? null : getModel().getElementAt(i);
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
/*      */   public void setSelectedValue(Object paramObject, boolean paramBoolean) {
/* 2359 */     if (paramObject == null) {
/* 2360 */       setSelectedIndex(-1);
/* 2361 */     } else if (!paramObject.equals(getSelectedValue())) {
/*      */       
/* 2363 */       ListModel<E> listModel = getModel(); byte b; int i;
/* 2364 */       for (b = 0, i = listModel.getSize(); b < i; b++) {
/* 2365 */         if (paramObject.equals(listModel.getElementAt(b))) {
/* 2366 */           setSelectedIndex(b);
/* 2367 */           if (paramBoolean)
/* 2368 */             ensureIndexIsVisible(b); 
/* 2369 */           repaint(); return;
/*      */         } 
/*      */       } 
/* 2372 */       setSelectedIndex(-1);
/*      */     } 
/* 2374 */     repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkScrollableParameters(Rectangle paramRectangle, int paramInt) {
/* 2384 */     if (paramRectangle == null) {
/* 2385 */       throw new IllegalArgumentException("visibleRect must be non-null");
/*      */     }
/* 2387 */     switch (paramInt) {
/*      */       case 0:
/*      */       case 1:
/*      */         return;
/*      */     } 
/* 2392 */     throw new IllegalArgumentException("orientation must be one of: VERTICAL, HORIZONTAL");
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
/*      */   public Dimension getPreferredScrollableViewportSize() {
/* 2436 */     if (getLayoutOrientation() != 0) {
/* 2437 */       return getPreferredSize();
/*      */     }
/* 2439 */     Insets insets = getInsets();
/* 2440 */     int i = insets.left + insets.right;
/* 2441 */     int j = insets.top + insets.bottom;
/*      */     
/* 2443 */     int k = getVisibleRowCount();
/* 2444 */     int m = getFixedCellWidth();
/* 2445 */     int n = getFixedCellHeight();
/*      */     
/* 2447 */     if (m > 0 && n > 0) {
/* 2448 */       int i1 = m + i;
/* 2449 */       int i2 = k * n + j;
/* 2450 */       return new Dimension(i1, i2);
/*      */     } 
/* 2452 */     if (getModel().getSize() > 0) {
/* 2453 */       boolean bool; int i1 = (getPreferredSize()).width;
/*      */       
/* 2455 */       Rectangle rectangle = getCellBounds(0, 0);
/* 2456 */       if (rectangle != null) {
/* 2457 */         bool = k * rectangle.height + j;
/*      */       }
/*      */       else {
/*      */         
/* 2461 */         bool = true;
/*      */       } 
/* 2463 */       return new Dimension(i1, bool);
/*      */     } 
/*      */     
/* 2466 */     m = (m > 0) ? m : 256;
/* 2467 */     n = (n > 0) ? n : 16;
/* 2468 */     return new Dimension(m, n * k);
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
/* 2496 */     checkScrollableParameters(paramRectangle, paramInt1);
/*      */     
/* 2498 */     if (paramInt1 == 1) {
/* 2499 */       int i = locationToIndex(paramRectangle.getLocation());
/*      */       
/* 2501 */       if (i == -1) {
/* 2502 */         return 0;
/*      */       }
/*      */ 
/*      */       
/* 2506 */       if (paramInt2 > 0) {
/* 2507 */         Rectangle rectangle1 = getCellBounds(i, i);
/* 2508 */         return (rectangle1 == null) ? 0 : (rectangle1.height - paramRectangle.y - rectangle1.y);
/*      */       } 
/*      */ 
/*      */       
/* 2512 */       Rectangle rectangle = getCellBounds(i, i);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2517 */       if (rectangle.y == paramRectangle.y && i == 0) {
/* 2518 */         return 0;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2524 */       if (rectangle.y == paramRectangle.y) {
/* 2525 */         Point point = rectangle.getLocation();
/* 2526 */         point.y--;
/* 2527 */         int j = locationToIndex(point);
/* 2528 */         Rectangle rectangle1 = getCellBounds(j, j);
/*      */         
/* 2530 */         if (rectangle1 == null || rectangle1.y >= rectangle.y) {
/* 2531 */           return 0;
/*      */         }
/* 2533 */         return rectangle1.height;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2539 */       return paramRectangle.y - rectangle.y;
/*      */     } 
/*      */ 
/*      */     
/* 2543 */     if (paramInt1 == 0 && 
/* 2544 */       getLayoutOrientation() != 0) {
/* 2545 */       Point point; boolean bool = getComponentOrientation().isLeftToRight();
/*      */ 
/*      */ 
/*      */       
/* 2549 */       if (bool) {
/* 2550 */         point = paramRectangle.getLocation();
/*      */       } else {
/*      */         
/* 2553 */         point = new Point(paramRectangle.x + paramRectangle.width - 1, paramRectangle.y);
/*      */       } 
/*      */       
/* 2556 */       int i = locationToIndex(point);
/*      */       
/* 2558 */       if (i != -1) {
/* 2559 */         Rectangle rectangle = getCellBounds(i, i);
/* 2560 */         if (rectangle != null && rectangle.contains(point)) {
/*      */           int j;
/*      */           
/*      */           int k;
/* 2564 */           if (bool) {
/* 2565 */             j = paramRectangle.x;
/* 2566 */             k = rectangle.x;
/*      */           } else {
/*      */             
/* 2569 */             j = paramRectangle.x + paramRectangle.width;
/* 2570 */             k = rectangle.x + rectangle.width;
/*      */           } 
/*      */           
/* 2573 */           if (k != j) {
/* 2574 */             if (paramInt2 < 0)
/*      */             {
/* 2576 */               return Math.abs(j - k);
/*      */             }
/*      */             
/* 2579 */             if (bool)
/*      */             {
/* 2581 */               return k + rectangle.width - j;
/*      */             }
/*      */ 
/*      */             
/* 2585 */             return j - rectangle.x;
/*      */           } 
/*      */ 
/*      */           
/* 2589 */           return rectangle.width;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2593 */     Font font = getFont();
/* 2594 */     return (font != null) ? font.getSize() : 1;
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
/*      */   public int getScrollableBlockIncrement(Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 2641 */     checkScrollableParameters(paramRectangle, paramInt1);
/* 2642 */     if (paramInt1 == 1) {
/* 2643 */       int i = paramRectangle.height;
/*      */       
/* 2645 */       if (paramInt2 > 0) {
/*      */         
/* 2647 */         int j = locationToIndex(new Point(paramRectangle.x, paramRectangle.y + paramRectangle.height - 1));
/* 2648 */         if (j != -1) {
/* 2649 */           Rectangle rectangle = getCellBounds(j, j);
/* 2650 */           if (rectangle != null) {
/* 2651 */             i = rectangle.y - paramRectangle.y;
/* 2652 */             if (i == 0 && j < getModel().getSize() - 1) {
/* 2653 */               i = rectangle.height;
/*      */             }
/*      */           }
/*      */         
/*      */         } 
/*      */       } else {
/*      */         
/* 2660 */         int j = locationToIndex(new Point(paramRectangle.x, paramRectangle.y - paramRectangle.height));
/* 2661 */         int k = getFirstVisibleIndex();
/* 2662 */         if (j != -1) {
/* 2663 */           if (k == -1) {
/* 2664 */             k = locationToIndex(paramRectangle.getLocation());
/*      */           }
/* 2666 */           Rectangle rectangle1 = getCellBounds(j, j);
/* 2667 */           Rectangle rectangle2 = getCellBounds(k, k);
/* 2668 */           if (rectangle1 != null && rectangle2 != null) {
/* 2669 */             while (rectangle1.y + paramRectangle.height < rectangle2.y + rectangle2.height && rectangle1.y < rectangle2.y) {
/*      */ 
/*      */               
/* 2672 */               j++;
/* 2673 */               rectangle1 = getCellBounds(j, j);
/*      */             } 
/* 2675 */             i = paramRectangle.y - rectangle1.y;
/* 2676 */             if (i <= 0 && rectangle1.y > 0) {
/* 2677 */               j--;
/* 2678 */               rectangle1 = getCellBounds(j, j);
/* 2679 */               if (rectangle1 != null) {
/* 2680 */                 i = paramRectangle.y - rectangle1.y;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 2686 */       return i;
/*      */     } 
/* 2688 */     if (paramInt1 == 0 && 
/* 2689 */       getLayoutOrientation() != 0) {
/* 2690 */       boolean bool = getComponentOrientation().isLeftToRight();
/* 2691 */       int i = paramRectangle.width;
/*      */       
/* 2693 */       if (paramInt2 > 0) {
/*      */         
/* 2695 */         int j = paramRectangle.x + (bool ? (paramRectangle.width - 1) : 0);
/* 2696 */         int k = locationToIndex(new Point(j, paramRectangle.y));
/*      */         
/* 2698 */         if (k != -1) {
/* 2699 */           Rectangle rectangle = getCellBounds(k, k);
/* 2700 */           if (rectangle != null) {
/* 2701 */             if (bool) {
/* 2702 */               i = rectangle.x - paramRectangle.x;
/*      */             } else {
/* 2704 */               i = paramRectangle.x + paramRectangle.width - rectangle.x + rectangle.width;
/*      */             } 
/*      */             
/* 2707 */             if (i < 0) {
/* 2708 */               i += rectangle.width;
/* 2709 */             } else if (i == 0 && k < getModel().getSize() - 1) {
/* 2710 */               i = rectangle.width;
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 2720 */         int j = paramRectangle.x + (bool ? -paramRectangle.width : (paramRectangle.width - 1 + paramRectangle.width));
/*      */ 
/*      */         
/* 2723 */         int k = locationToIndex(new Point(j, paramRectangle.y));
/*      */         
/* 2725 */         if (k != -1) {
/* 2726 */           Rectangle rectangle = getCellBounds(k, k);
/* 2727 */           if (rectangle != null) {
/*      */             
/* 2729 */             int m = rectangle.x + rectangle.width;
/*      */             
/* 2731 */             if (bool) {
/* 2732 */               if (rectangle.x < paramRectangle.x - paramRectangle.width && m < paramRectangle.x) {
/*      */                 
/* 2734 */                 i = paramRectangle.x - m;
/*      */               } else {
/* 2736 */                 i = paramRectangle.x - rectangle.x;
/*      */               } 
/*      */             } else {
/* 2739 */               int n = paramRectangle.x + paramRectangle.width;
/*      */               
/* 2741 */               if (m > n + paramRectangle.width && rectangle.x > n) {
/*      */                 
/* 2743 */                 i = rectangle.x - n;
/*      */               } else {
/* 2745 */                 i = m - n;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 2751 */       return i;
/*      */     } 
/* 2753 */     return paramRectangle.width;
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
/*      */   public boolean getScrollableTracksViewportWidth() {
/* 2772 */     if (getLayoutOrientation() == 2 && 
/* 2773 */       getVisibleRowCount() <= 0) {
/* 2774 */       return true;
/*      */     }
/* 2776 */     Container container = SwingUtilities.getUnwrappedParent(this);
/* 2777 */     if (container instanceof JViewport) {
/* 2778 */       return (container.getWidth() > (getPreferredSize()).width);
/*      */     }
/* 2780 */     return false;
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
/*      */   public boolean getScrollableTracksViewportHeight() {
/* 2798 */     if (getLayoutOrientation() == 1 && 
/* 2799 */       getVisibleRowCount() <= 0) {
/* 2800 */       return true;
/*      */     }
/* 2802 */     Container container = SwingUtilities.getUnwrappedParent(this);
/* 2803 */     if (container instanceof JViewport) {
/* 2804 */       return (container.getHeight() > (getPreferredSize()).height);
/*      */     }
/* 2806 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 2815 */     paramObjectOutputStream.defaultWriteObject();
/* 2816 */     if (getUIClassID().equals("ListUI")) {
/* 2817 */       byte b = JComponent.getWriteObjCounter(this);
/* 2818 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 2819 */       if (b == 0 && this.ui != null) {
/* 2820 */         this.ui.installUI(this);
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
/*      */   protected String paramString() {
/* 2837 */     String str1 = (this.selectionForeground != null) ? this.selectionForeground.toString() : "";
/*      */ 
/*      */     
/* 2840 */     String str2 = (this.selectionBackground != null) ? this.selectionBackground.toString() : "";
/*      */ 
/*      */     
/* 2843 */     return super.paramString() + ",fixedCellHeight=" + this.fixedCellHeight + ",fixedCellWidth=" + this.fixedCellWidth + ",horizontalScrollIncrement=" + this.horizontalScrollIncrement + ",selectionBackground=" + str2 + ",selectionForeground=" + str1 + ",visibleRowCount=" + this.visibleRowCount + ",layoutOrientation=" + this.layoutOrientation;
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
/*      */   public AccessibleContext getAccessibleContext() {
/* 2869 */     if (this.accessibleContext == null) {
/* 2870 */       this.accessibleContext = new AccessibleJList();
/*      */     }
/* 2872 */     return this.accessibleContext;
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
/*      */   protected class AccessibleJList
/*      */     extends JComponent.AccessibleJComponent
/*      */     implements AccessibleSelection, PropertyChangeListener, ListSelectionListener, ListDataListener
/*      */   {
/*      */     int leadSelectionIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleJList() {
/* 2898 */       JList.this.addPropertyChangeListener(this);
/* 2899 */       JList.this.getSelectionModel().addListSelectionListener(this);
/* 2900 */       JList.this.getModel().addListDataListener(this);
/* 2901 */       this.leadSelectionIndex = JList.this.getLeadSelectionIndex();
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
/* 2913 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 2914 */       Object object1 = param1PropertyChangeEvent.getOldValue();
/* 2915 */       Object object2 = param1PropertyChangeEvent.getNewValue();
/*      */ 
/*      */       
/* 2918 */       if (str.compareTo("model") == 0) {
/*      */         
/* 2920 */         if (object1 != null && object1 instanceof ListModel) {
/* 2921 */           ((ListModel)object1).removeListDataListener(this);
/*      */         }
/* 2923 */         if (object2 != null && object2 instanceof ListModel) {
/* 2924 */           ((ListModel)object2).addListDataListener(this);
/*      */         
/*      */         }
/*      */       }
/* 2928 */       else if (str.compareTo("selectionModel") == 0) {
/*      */         
/* 2930 */         if (object1 != null && object1 instanceof ListSelectionModel) {
/* 2931 */           ((ListSelectionModel)object1).removeListSelectionListener(this);
/*      */         }
/* 2933 */         if (object2 != null && object2 instanceof ListSelectionModel) {
/* 2934 */           ((ListSelectionModel)object2).addListSelectionListener(this);
/*      */         }
/*      */         
/* 2937 */         firePropertyChange("AccessibleSelection", 
/*      */             
/* 2939 */             Boolean.valueOf(false), Boolean.valueOf(true));
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
/*      */     public void valueChanged(ListSelectionEvent param1ListSelectionEvent) {
/* 2951 */       int i = this.leadSelectionIndex;
/* 2952 */       this.leadSelectionIndex = JList.this.getLeadSelectionIndex();
/* 2953 */       if (i != this.leadSelectionIndex) {
/*      */ 
/*      */         
/* 2956 */         Accessible accessible1 = (i >= 0) ? getAccessibleChild(i) : null;
/*      */ 
/*      */         
/* 2959 */         Accessible accessible2 = (this.leadSelectionIndex >= 0) ? getAccessibleChild(this.leadSelectionIndex) : null;
/*      */         
/* 2961 */         firePropertyChange("AccessibleActiveDescendant", accessible1, accessible2);
/*      */       } 
/*      */ 
/*      */       
/* 2965 */       firePropertyChange("AccessibleVisibleData", 
/* 2966 */           Boolean.valueOf(false), Boolean.valueOf(true));
/* 2967 */       firePropertyChange("AccessibleSelection", 
/* 2968 */           Boolean.valueOf(false), Boolean.valueOf(true));
/*      */ 
/*      */       
/* 2971 */       AccessibleStateSet accessibleStateSet = getAccessibleStateSet();
/* 2972 */       ListSelectionModel listSelectionModel = JList.this.getSelectionModel();
/* 2973 */       if (listSelectionModel.getSelectionMode() != 0) {
/* 2974 */         if (!accessibleStateSet.contains(AccessibleState.MULTISELECTABLE)) {
/* 2975 */           accessibleStateSet.add(AccessibleState.MULTISELECTABLE);
/* 2976 */           firePropertyChange("AccessibleState", null, AccessibleState.MULTISELECTABLE);
/*      */         }
/*      */       
/*      */       }
/* 2980 */       else if (accessibleStateSet.contains(AccessibleState.MULTISELECTABLE)) {
/* 2981 */         accessibleStateSet.remove(AccessibleState.MULTISELECTABLE);
/* 2982 */         firePropertyChange("AccessibleState", AccessibleState.MULTISELECTABLE, null);
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
/*      */     public void intervalAdded(ListDataEvent param1ListDataEvent) {
/* 2995 */       firePropertyChange("AccessibleVisibleData", 
/* 2996 */           Boolean.valueOf(false), Boolean.valueOf(true));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void intervalRemoved(ListDataEvent param1ListDataEvent) {
/* 3006 */       firePropertyChange("AccessibleVisibleData", 
/* 3007 */           Boolean.valueOf(false), Boolean.valueOf(true));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void contentsChanged(ListDataEvent param1ListDataEvent) {
/* 3017 */       firePropertyChange("AccessibleVisibleData", 
/* 3018 */           Boolean.valueOf(false), Boolean.valueOf(true));
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
/*      */     public AccessibleStateSet getAccessibleStateSet() {
/* 3031 */       AccessibleStateSet accessibleStateSet = super.getAccessibleStateSet();
/* 3032 */       if (JList.this.selectionModel.getSelectionMode() != 0)
/*      */       {
/* 3034 */         accessibleStateSet.add(AccessibleState.MULTISELECTABLE);
/*      */       }
/* 3036 */       return accessibleStateSet;
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
/* 3047 */       return AccessibleRole.LIST;
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
/*      */     public Accessible getAccessibleAt(Point param1Point) {
/* 3059 */       int i = JList.this.locationToIndex(param1Point);
/* 3060 */       if (i >= 0) {
/* 3061 */         return new ActionableAccessibleJListChild(JList.this, i);
/*      */       }
/* 3063 */       return null;
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
/* 3075 */       return JList.this.getModel().getSize();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleChild(int param1Int) {
/* 3085 */       if (param1Int >= JList.this.getModel().getSize()) {
/* 3086 */         return null;
/*      */       }
/* 3088 */       return new ActionableAccessibleJListChild(JList.this, param1Int);
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
/*      */     public AccessibleSelection getAccessibleSelection() {
/* 3101 */       return this;
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
/*      */     public int getAccessibleSelectionCount() {
/* 3114 */       return (JList.this.getSelectedIndices()).length;
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
/*      */     public Accessible getAccessibleSelection(int param1Int) {
/* 3127 */       int i = getAccessibleSelectionCount();
/* 3128 */       if (param1Int < 0 || param1Int >= i) {
/* 3129 */         return null;
/*      */       }
/* 3131 */       return getAccessibleChild(JList.this.getSelectedIndices()[param1Int]);
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
/* 3143 */       return JList.this.isSelectedIndex(param1Int);
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
/*      */     public void addAccessibleSelection(int param1Int) {
/* 3156 */       JList.this.addSelectionInterval(param1Int, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeAccessibleSelection(int param1Int) {
/* 3167 */       JList.this.removeSelectionInterval(param1Int, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void clearAccessibleSelection() {
/* 3175 */       JList.this.clearSelection();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void selectAllAccessibleSelection() {
/* 3183 */       JList.this.addSelectionInterval(0, getAccessibleChildrenCount() - 1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected class AccessibleJListChild
/*      */       extends AccessibleContext
/*      */       implements Accessible, AccessibleComponent
/*      */     {
/* 3192 */       private JList<E> parent = null;
/*      */       int indexInParent;
/* 3194 */       private Component component = null;
/* 3195 */       private AccessibleContext accessibleContext = null;
/*      */       private ListModel<E> listModel;
/* 3197 */       private ListCellRenderer<? super E> cellRenderer = null;
/*      */       
/*      */       public AccessibleJListChild(JList<E> param2JList, int param2Int) {
/* 3200 */         this.parent = param2JList;
/* 3201 */         setAccessibleParent(param2JList);
/* 3202 */         this.indexInParent = param2Int;
/* 3203 */         if (param2JList != null) {
/* 3204 */           this.listModel = param2JList.getModel();
/* 3205 */           this.cellRenderer = param2JList.getCellRenderer();
/*      */         } 
/*      */       }
/*      */       
/*      */       private Component getCurrentComponent() {
/* 3210 */         return getComponentAtIndex(this.indexInParent);
/*      */       }
/*      */       
/*      */       AccessibleContext getCurrentAccessibleContext() {
/* 3214 */         Component component = getComponentAtIndex(this.indexInParent);
/* 3215 */         if (component instanceof Accessible) {
/* 3216 */           return component.getAccessibleContext();
/*      */         }
/* 3218 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       private Component getComponentAtIndex(int param2Int) {
/* 3223 */         if (param2Int < 0 || param2Int >= this.listModel.getSize()) {
/* 3224 */           return null;
/*      */         }
/* 3226 */         if (this.parent != null && this.listModel != null && this.cellRenderer != null) {
/*      */ 
/*      */           
/* 3229 */           E e = this.listModel.getElementAt(param2Int);
/* 3230 */           boolean bool = this.parent.isSelectedIndex(param2Int);
/*      */           
/* 3232 */           boolean bool1 = (this.parent.isFocusOwner() && param2Int == this.parent.getLeadSelectionIndex()) ? true : false;
/* 3233 */           return this.cellRenderer.getListCellRendererComponent(this.parent, e, param2Int, bool, bool1);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3240 */         return null;
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
/*      */       public AccessibleContext getAccessibleContext() {
/* 3254 */         return this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String getAccessibleName() {
/* 3261 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3262 */         if (accessibleContext != null) {
/* 3263 */           return accessibleContext.getAccessibleName();
/*      */         }
/* 3265 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setAccessibleName(String param2String) {
/* 3270 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3271 */         if (accessibleContext != null) {
/* 3272 */           accessibleContext.setAccessibleName(param2String);
/*      */         }
/*      */       }
/*      */       
/*      */       public String getAccessibleDescription() {
/* 3277 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3278 */         if (accessibleContext != null) {
/* 3279 */           return accessibleContext.getAccessibleDescription();
/*      */         }
/* 3281 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setAccessibleDescription(String param2String) {
/* 3286 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3287 */         if (accessibleContext != null) {
/* 3288 */           accessibleContext.setAccessibleDescription(param2String);
/*      */         }
/*      */       }
/*      */       
/*      */       public AccessibleRole getAccessibleRole() {
/* 3293 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3294 */         if (accessibleContext != null) {
/* 3295 */           return accessibleContext.getAccessibleRole();
/*      */         }
/* 3297 */         return null;
/*      */       }
/*      */       
/*      */       public AccessibleStateSet getAccessibleStateSet() {
/*      */         AccessibleStateSet accessibleStateSet;
/* 3302 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/*      */         
/* 3304 */         if (accessibleContext != null) {
/* 3305 */           accessibleStateSet = accessibleContext.getAccessibleStateSet();
/*      */         } else {
/* 3307 */           accessibleStateSet = new AccessibleStateSet();
/*      */         } 
/*      */         
/* 3310 */         accessibleStateSet.add(AccessibleState.SELECTABLE);
/* 3311 */         if (this.parent.isFocusOwner() && this.indexInParent == this.parent
/* 3312 */           .getLeadSelectionIndex()) {
/* 3313 */           accessibleStateSet.add(AccessibleState.ACTIVE);
/*      */         }
/* 3315 */         if (this.parent.isSelectedIndex(this.indexInParent)) {
/* 3316 */           accessibleStateSet.add(AccessibleState.SELECTED);
/*      */         }
/* 3318 */         if (isShowing()) {
/* 3319 */           accessibleStateSet.add(AccessibleState.SHOWING);
/* 3320 */         } else if (accessibleStateSet.contains(AccessibleState.SHOWING)) {
/* 3321 */           accessibleStateSet.remove(AccessibleState.SHOWING);
/*      */         } 
/* 3323 */         if (isVisible()) {
/* 3324 */           accessibleStateSet.add(AccessibleState.VISIBLE);
/* 3325 */         } else if (accessibleStateSet.contains(AccessibleState.VISIBLE)) {
/* 3326 */           accessibleStateSet.remove(AccessibleState.VISIBLE);
/*      */         } 
/* 3328 */         accessibleStateSet.add(AccessibleState.TRANSIENT);
/* 3329 */         return accessibleStateSet;
/*      */       }
/*      */       
/*      */       public int getAccessibleIndexInParent() {
/* 3333 */         return this.indexInParent;
/*      */       }
/*      */       
/*      */       public int getAccessibleChildrenCount() {
/* 3337 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3338 */         if (accessibleContext != null) {
/* 3339 */           return accessibleContext.getAccessibleChildrenCount();
/*      */         }
/* 3341 */         return 0;
/*      */       }
/*      */ 
/*      */       
/*      */       public Accessible getAccessibleChild(int param2Int) {
/* 3346 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3347 */         if (accessibleContext != null) {
/* 3348 */           Accessible accessible = accessibleContext.getAccessibleChild(param2Int);
/* 3349 */           accessibleContext.setAccessibleParent(this);
/* 3350 */           return accessible;
/*      */         } 
/* 3352 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public Locale getLocale() {
/* 3357 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3358 */         if (accessibleContext != null) {
/* 3359 */           return accessibleContext.getLocale();
/*      */         }
/* 3361 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public void addPropertyChangeListener(PropertyChangeListener param2PropertyChangeListener) {
/* 3366 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3367 */         if (accessibleContext != null) {
/* 3368 */           accessibleContext.addPropertyChangeListener(param2PropertyChangeListener);
/*      */         }
/*      */       }
/*      */       
/*      */       public void removePropertyChangeListener(PropertyChangeListener param2PropertyChangeListener) {
/* 3373 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3374 */         if (accessibleContext != null) {
/* 3375 */           accessibleContext.removePropertyChangeListener(param2PropertyChangeListener);
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
/*      */       public AccessibleComponent getAccessibleComponent() {
/* 3388 */         return this;
/*      */       }
/*      */       
/*      */       public AccessibleSelection getAccessibleSelection() {
/* 3392 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3393 */         return (accessibleContext != null) ? accessibleContext.getAccessibleSelection() : null;
/*      */       }
/*      */       
/*      */       public AccessibleText getAccessibleText() {
/* 3397 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3398 */         return (accessibleContext != null) ? accessibleContext.getAccessibleText() : null;
/*      */       }
/*      */       
/*      */       public AccessibleValue getAccessibleValue() {
/* 3402 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3403 */         return (accessibleContext != null) ? accessibleContext.getAccessibleValue() : null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Color getBackground() {
/* 3410 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3411 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3412 */           return ((AccessibleComponent)accessibleContext).getBackground();
/*      */         }
/* 3414 */         Component component = getCurrentComponent();
/* 3415 */         if (component != null) {
/* 3416 */           return component.getBackground();
/*      */         }
/* 3418 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setBackground(Color param2Color) {
/* 3424 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3425 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3426 */           ((AccessibleComponent)accessibleContext).setBackground(param2Color);
/*      */         } else {
/* 3428 */           Component component = getCurrentComponent();
/* 3429 */           if (component != null) {
/* 3430 */             component.setBackground(param2Color);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Color getForeground() {
/* 3436 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3437 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3438 */           return ((AccessibleComponent)accessibleContext).getForeground();
/*      */         }
/* 3440 */         Component component = getCurrentComponent();
/* 3441 */         if (component != null) {
/* 3442 */           return component.getForeground();
/*      */         }
/* 3444 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setForeground(Color param2Color) {
/* 3450 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3451 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3452 */           ((AccessibleComponent)accessibleContext).setForeground(param2Color);
/*      */         } else {
/* 3454 */           Component component = getCurrentComponent();
/* 3455 */           if (component != null) {
/* 3456 */             component.setForeground(param2Color);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Cursor getCursor() {
/* 3462 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3463 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3464 */           return ((AccessibleComponent)accessibleContext).getCursor();
/*      */         }
/* 3466 */         Component component = getCurrentComponent();
/* 3467 */         if (component != null) {
/* 3468 */           return component.getCursor();
/*      */         }
/* 3470 */         Accessible accessible = getAccessibleParent();
/* 3471 */         if (accessible instanceof AccessibleComponent) {
/* 3472 */           return ((AccessibleComponent)accessible).getCursor();
/*      */         }
/* 3474 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setCursor(Cursor param2Cursor) {
/* 3481 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3482 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3483 */           ((AccessibleComponent)accessibleContext).setCursor(param2Cursor);
/*      */         } else {
/* 3485 */           Component component = getCurrentComponent();
/* 3486 */           if (component != null) {
/* 3487 */             component.setCursor(param2Cursor);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Font getFont() {
/* 3493 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3494 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3495 */           return ((AccessibleComponent)accessibleContext).getFont();
/*      */         }
/* 3497 */         Component component = getCurrentComponent();
/* 3498 */         if (component != null) {
/* 3499 */           return component.getFont();
/*      */         }
/* 3501 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setFont(Font param2Font) {
/* 3507 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3508 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3509 */           ((AccessibleComponent)accessibleContext).setFont(param2Font);
/*      */         } else {
/* 3511 */           Component component = getCurrentComponent();
/* 3512 */           if (component != null) {
/* 3513 */             component.setFont(param2Font);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public FontMetrics getFontMetrics(Font param2Font) {
/* 3519 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3520 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3521 */           return ((AccessibleComponent)accessibleContext).getFontMetrics(param2Font);
/*      */         }
/* 3523 */         Component component = getCurrentComponent();
/* 3524 */         if (component != null) {
/* 3525 */           return component.getFontMetrics(param2Font);
/*      */         }
/* 3527 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean isEnabled() {
/* 3533 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3534 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3535 */           return ((AccessibleComponent)accessibleContext).isEnabled();
/*      */         }
/* 3537 */         Component component = getCurrentComponent();
/* 3538 */         if (component != null) {
/* 3539 */           return component.isEnabled();
/*      */         }
/* 3541 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setEnabled(boolean param2Boolean) {
/* 3547 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3548 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3549 */           ((AccessibleComponent)accessibleContext).setEnabled(param2Boolean);
/*      */         } else {
/* 3551 */           Component component = getCurrentComponent();
/* 3552 */           if (component != null) {
/* 3553 */             component.setEnabled(param2Boolean);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public boolean isVisible() {
/* 3559 */         int i = this.parent.getFirstVisibleIndex();
/* 3560 */         int j = this.parent.getLastVisibleIndex();
/*      */ 
/*      */ 
/*      */         
/* 3564 */         if (j == -1) {
/* 3565 */           j = this.parent.getModel().getSize() - 1;
/*      */         }
/* 3567 */         return (this.indexInParent >= i && this.indexInParent <= j);
/*      */       }
/*      */ 
/*      */       
/*      */       public void setVisible(boolean param2Boolean) {}
/*      */ 
/*      */       
/*      */       public boolean isShowing() {
/* 3575 */         return (this.parent.isShowing() && isVisible());
/*      */       }
/*      */       
/*      */       public boolean contains(Point param2Point) {
/* 3579 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3580 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3581 */           Rectangle rectangle = ((AccessibleComponent)accessibleContext).getBounds();
/* 3582 */           return rectangle.contains(param2Point);
/*      */         } 
/* 3584 */         Component component = getCurrentComponent();
/* 3585 */         if (component != null) {
/* 3586 */           Rectangle rectangle = component.getBounds();
/* 3587 */           return rectangle.contains(param2Point);
/*      */         } 
/* 3589 */         return getBounds().contains(param2Point);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public Point getLocationOnScreen() {
/* 3595 */         if (this.parent != null) {
/*      */           Point point1;
/*      */           try {
/* 3598 */             point1 = this.parent.getLocationOnScreen();
/* 3599 */           } catch (IllegalComponentStateException illegalComponentStateException) {
/*      */             
/* 3601 */             return null;
/*      */           } 
/* 3603 */           Point point2 = this.parent.indexToLocation(this.indexInParent);
/* 3604 */           if (point2 != null) {
/* 3605 */             point2.translate(point1.x, point1.y);
/* 3606 */             return point2;
/*      */           } 
/* 3608 */           return null;
/*      */         } 
/*      */         
/* 3611 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public Point getLocation() {
/* 3616 */         if (this.parent != null) {
/* 3617 */           return this.parent.indexToLocation(this.indexInParent);
/*      */         }
/* 3619 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setLocation(Point param2Point) {
/* 3624 */         if (this.parent != null && this.parent.contains(param2Point)) {
/* 3625 */           JList.this.ensureIndexIsVisible(this.indexInParent);
/*      */         }
/*      */       }
/*      */       
/*      */       public Rectangle getBounds() {
/* 3630 */         if (this.parent != null) {
/* 3631 */           return this.parent.getCellBounds(this.indexInParent, this.indexInParent);
/*      */         }
/* 3633 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setBounds(Rectangle param2Rectangle) {
/* 3638 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3639 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3640 */           ((AccessibleComponent)accessibleContext).setBounds(param2Rectangle);
/*      */         }
/*      */       }
/*      */       
/*      */       public Dimension getSize() {
/* 3645 */         Rectangle rectangle = getBounds();
/* 3646 */         if (rectangle != null) {
/* 3647 */           return rectangle.getSize();
/*      */         }
/* 3649 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setSize(Dimension param2Dimension) {
/* 3654 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3655 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3656 */           ((AccessibleComponent)accessibleContext).setSize(param2Dimension);
/*      */         } else {
/* 3658 */           Component component = getCurrentComponent();
/* 3659 */           if (component != null) {
/* 3660 */             component.setSize(param2Dimension);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Accessible getAccessibleAt(Point param2Point) {
/* 3666 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3667 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3668 */           return ((AccessibleComponent)accessibleContext).getAccessibleAt(param2Point);
/*      */         }
/* 3670 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean isFocusTraversable() {
/* 3675 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3676 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3677 */           return ((AccessibleComponent)accessibleContext).isFocusTraversable();
/*      */         }
/* 3679 */         Component component = getCurrentComponent();
/* 3680 */         if (component != null) {
/* 3681 */           return component.isFocusTraversable();
/*      */         }
/* 3683 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void requestFocus() {
/* 3689 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3690 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3691 */           ((AccessibleComponent)accessibleContext).requestFocus();
/*      */         } else {
/* 3693 */           Component component = getCurrentComponent();
/* 3694 */           if (component != null) {
/* 3695 */             component.requestFocus();
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public void addFocusListener(FocusListener param2FocusListener) {
/* 3701 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3702 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3703 */           ((AccessibleComponent)accessibleContext).addFocusListener(param2FocusListener);
/*      */         } else {
/* 3705 */           Component component = getCurrentComponent();
/* 3706 */           if (component != null) {
/* 3707 */             component.addFocusListener(param2FocusListener);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public void removeFocusListener(FocusListener param2FocusListener) {
/* 3713 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3714 */         if (accessibleContext instanceof AccessibleComponent) {
/* 3715 */           ((AccessibleComponent)accessibleContext).removeFocusListener(param2FocusListener);
/*      */         } else {
/* 3717 */           Component component = getCurrentComponent();
/* 3718 */           if (component != null) {
/* 3719 */             component.removeFocusListener(param2FocusListener);
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
/*      */       public AccessibleIcon[] getAccessibleIcon() {
/* 3735 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3736 */         if (accessibleContext != null) {
/* 3737 */           return accessibleContext.getAccessibleIcon();
/*      */         }
/* 3739 */         return null;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private class ActionableAccessibleJListChild
/*      */       extends AccessibleJListChild
/*      */       implements AccessibleAction
/*      */     {
/*      */       ActionableAccessibleJListChild(JList<E> param2JList, int param2Int) {
/* 3750 */         super(param2JList, param2Int);
/*      */       }
/*      */ 
/*      */       
/*      */       public AccessibleAction getAccessibleAction() {
/* 3755 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 3756 */         if (accessibleContext == null) {
/* 3757 */           return null;
/*      */         }
/* 3759 */         AccessibleAction accessibleAction = accessibleContext.getAccessibleAction();
/* 3760 */         if (accessibleAction != null) {
/* 3761 */           return accessibleAction;
/*      */         }
/* 3763 */         return this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean doAccessibleAction(int param2Int) {
/* 3770 */         if (param2Int == 0) {
/* 3771 */           JList.this.setSelectedIndex(this.indexInParent);
/* 3772 */           return true;
/*      */         } 
/* 3774 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public String getAccessibleActionDescription(int param2Int) {
/* 3780 */         if (param2Int == 0) {
/* 3781 */           return UIManager.getString("AbstractButton.clickText");
/*      */         }
/* 3783 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleActionCount() {
/* 3789 */         return 1;
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */