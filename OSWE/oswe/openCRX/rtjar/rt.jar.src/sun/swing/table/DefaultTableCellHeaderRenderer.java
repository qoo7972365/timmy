/*     */ package sun.swing.table;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.RowSorter;
/*     */ import javax.swing.SortOrder;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.JTableHeader;
/*     */ import sun.swing.DefaultLookup;
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
/*     */ public class DefaultTableCellHeaderRenderer
/*     */   extends DefaultTableCellRenderer
/*     */   implements UIResource
/*     */ {
/*     */   private boolean horizontalTextPositionSet;
/*     */   private Icon sortArrow;
/*  46 */   private EmptyIcon emptyIcon = new EmptyIcon();
/*     */   
/*     */   public DefaultTableCellHeaderRenderer() {
/*  49 */     setHorizontalAlignment(0);
/*     */   }
/*     */   
/*     */   public void setHorizontalTextPosition(int paramInt) {
/*  53 */     this.horizontalTextPositionSet = true;
/*  54 */     super.setHorizontalTextPosition(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
/*  59 */     Icon icon = null;
/*     */     
/*  61 */     boolean bool = false;
/*     */     
/*  63 */     if (paramJTable != null) {
/*  64 */       JTableHeader jTableHeader = paramJTable.getTableHeader();
/*  65 */       if (jTableHeader != null) {
/*  66 */         Color color1 = null;
/*  67 */         Color color2 = null;
/*  68 */         if (paramBoolean2) {
/*  69 */           color1 = DefaultLookup.getColor(this, this.ui, "TableHeader.focusCellForeground");
/*  70 */           color2 = DefaultLookup.getColor(this, this.ui, "TableHeader.focusCellBackground");
/*     */         } 
/*  72 */         if (color1 == null) {
/*  73 */           color1 = jTableHeader.getForeground();
/*     */         }
/*  75 */         if (color2 == null) {
/*  76 */           color2 = jTableHeader.getBackground();
/*     */         }
/*  78 */         setForeground(color1);
/*  79 */         setBackground(color2);
/*     */         
/*  81 */         setFont(jTableHeader.getFont());
/*     */         
/*  83 */         bool = jTableHeader.isPaintingForPrint();
/*     */       } 
/*     */       
/*  86 */       if (!bool && paramJTable.getRowSorter() != null) {
/*  87 */         if (!this.horizontalTextPositionSet)
/*     */         {
/*     */           
/*  90 */           setHorizontalTextPosition(10);
/*     */         }
/*  92 */         SortOrder sortOrder = getColumnSortOrder(paramJTable, paramInt2);
/*  93 */         if (sortOrder != null) {
/*  94 */           switch (sortOrder) {
/*     */             case ASCENDING:
/*  96 */               icon = DefaultLookup.getIcon(this, this.ui, "Table.ascendingSortIcon");
/*     */               break;
/*     */             
/*     */             case DESCENDING:
/* 100 */               icon = DefaultLookup.getIcon(this, this.ui, "Table.descendingSortIcon");
/*     */               break;
/*     */             
/*     */             case UNSORTED:
/* 104 */               icon = DefaultLookup.getIcon(this, this.ui, "Table.naturalSortIcon");
/*     */               break;
/*     */           } 
/*     */ 
/*     */         
/*     */         }
/*     */       } 
/*     */     } 
/* 112 */     setText((paramObject == null) ? "" : paramObject.toString());
/* 113 */     setIcon(icon);
/* 114 */     this.sortArrow = icon;
/*     */     
/* 116 */     Border border = null;
/* 117 */     if (paramBoolean2) {
/* 118 */       border = DefaultLookup.getBorder(this, this.ui, "TableHeader.focusCellBorder");
/*     */     }
/* 120 */     if (border == null) {
/* 121 */       border = DefaultLookup.getBorder(this, this.ui, "TableHeader.cellBorder");
/*     */     }
/* 123 */     setBorder(border);
/*     */     
/* 125 */     return this;
/*     */   }
/*     */   
/*     */   public static SortOrder getColumnSortOrder(JTable paramJTable, int paramInt) {
/* 129 */     SortOrder sortOrder = null;
/* 130 */     if (paramJTable == null || paramJTable.getRowSorter() == null) {
/* 131 */       return sortOrder;
/*     */     }
/*     */     
/* 134 */     List<? extends RowSorter.SortKey> list = paramJTable.getRowSorter().getSortKeys();
/* 135 */     if (list.size() > 0 && ((RowSorter.SortKey)list.get(0)).getColumn() == paramJTable
/* 136 */       .convertColumnIndexToModel(paramInt)) {
/* 137 */       sortOrder = ((RowSorter.SortKey)list.get(0)).getSortOrder();
/*     */     }
/* 139 */     return sortOrder;
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintComponent(Graphics paramGraphics) {
/* 144 */     boolean bool = DefaultLookup.getBoolean(this, this.ui, "TableHeader.rightAlignSortArrow", false);
/*     */     
/* 146 */     if (bool && this.sortArrow != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 151 */       this.emptyIcon.width = this.sortArrow.getIconWidth();
/* 152 */       this.emptyIcon.height = this.sortArrow.getIconHeight();
/* 153 */       setIcon(this.emptyIcon);
/* 154 */       super.paintComponent(paramGraphics);
/* 155 */       Point point = computeIconPosition(paramGraphics);
/* 156 */       this.sortArrow.paintIcon(this, paramGraphics, point.x, point.y);
/*     */     } else {
/* 158 */       super.paintComponent(paramGraphics);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Point computeIconPosition(Graphics paramGraphics) {
/* 163 */     FontMetrics fontMetrics = paramGraphics.getFontMetrics();
/* 164 */     Rectangle rectangle1 = new Rectangle();
/* 165 */     Rectangle rectangle2 = new Rectangle();
/* 166 */     Rectangle rectangle3 = new Rectangle();
/* 167 */     Insets insets = getInsets();
/* 168 */     rectangle1.x = insets.left;
/* 169 */     rectangle1.y = insets.top;
/* 170 */     rectangle1.width = getWidth() - insets.left + insets.right;
/* 171 */     rectangle1.height = getHeight() - insets.top + insets.bottom;
/* 172 */     SwingUtilities.layoutCompoundLabel(this, fontMetrics, 
/*     */ 
/*     */         
/* 175 */         getText(), this.sortArrow, 
/*     */         
/* 177 */         getVerticalAlignment(), 
/* 178 */         getHorizontalAlignment(), 
/* 179 */         getVerticalTextPosition(), 
/* 180 */         getHorizontalTextPosition(), rectangle1, rectangle3, rectangle2, 
/*     */ 
/*     */ 
/*     */         
/* 184 */         getIconTextGap());
/* 185 */     int i = getWidth() - insets.right - this.sortArrow.getIconWidth();
/* 186 */     int j = rectangle3.y;
/* 187 */     return new Point(i, j);
/*     */   }
/*     */   
/*     */   private class EmptyIcon implements Icon, Serializable {
/* 191 */     int width = 0; public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {}
/* 192 */     int height = 0;
/*     */     public int getIconWidth() {
/* 194 */       return this.width; } public int getIconHeight() {
/* 195 */       return this.height;
/*     */     }
/*     */     
/*     */     private EmptyIcon() {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/table/DefaultTableCellHeaderRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */