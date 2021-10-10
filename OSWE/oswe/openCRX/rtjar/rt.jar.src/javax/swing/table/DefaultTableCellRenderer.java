/*     */ package javax.swing.table;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.EmptyBorder;
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
/*     */ public class DefaultTableCellRenderer
/*     */   extends JLabel
/*     */   implements TableCellRenderer, Serializable
/*     */ {
/*  95 */   private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
/*  96 */   private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
/*  97 */   protected static Border noFocusBorder = DEFAULT_NO_FOCUS_BORDER;
/*     */ 
/*     */ 
/*     */   
/*     */   private Color unselectedForeground;
/*     */ 
/*     */ 
/*     */   
/*     */   private Color unselectedBackground;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultTableCellRenderer() {
/* 111 */     setOpaque(true);
/* 112 */     setBorder(getNoFocusBorder());
/* 113 */     setName("Table.cellRenderer");
/*     */   }
/*     */   
/*     */   private Border getNoFocusBorder() {
/* 117 */     Border border = DefaultLookup.getBorder(this, this.ui, "Table.cellNoFocusBorder");
/* 118 */     if (System.getSecurityManager() != null) {
/* 119 */       if (border != null) return border; 
/* 120 */       return SAFE_NO_FOCUS_BORDER;
/* 121 */     }  if (border != null && (
/* 122 */       noFocusBorder == null || noFocusBorder == DEFAULT_NO_FOCUS_BORDER)) {
/* 123 */       return border;
/*     */     }
/*     */     
/* 126 */     return noFocusBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setForeground(Color paramColor) {
/* 136 */     super.setForeground(paramColor);
/* 137 */     this.unselectedForeground = paramColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackground(Color paramColor) {
/* 147 */     super.setBackground(paramColor);
/* 148 */     this.unselectedBackground = paramColor;
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
/*     */   public void updateUI() {
/* 160 */     super.updateUI();
/* 161 */     setForeground((Color)null);
/* 162 */     setBackground((Color)null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getTableCellRendererComponent(JTable paramJTable, Object paramObject, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
/* 189 */     if (paramJTable == null) {
/* 190 */       return this;
/*     */     }
/*     */     
/* 193 */     Color color1 = null;
/* 194 */     Color color2 = null;
/*     */     
/* 196 */     JTable.DropLocation dropLocation = paramJTable.getDropLocation();
/* 197 */     if (dropLocation != null && 
/* 198 */       !dropLocation.isInsertRow() && 
/* 199 */       !dropLocation.isInsertColumn() && dropLocation
/* 200 */       .getRow() == paramInt1 && dropLocation
/* 201 */       .getColumn() == paramInt2) {
/*     */       
/* 203 */       color1 = DefaultLookup.getColor(this, this.ui, "Table.dropCellForeground");
/* 204 */       color2 = DefaultLookup.getColor(this, this.ui, "Table.dropCellBackground");
/*     */       
/* 206 */       paramBoolean1 = true;
/*     */     } 
/*     */     
/* 209 */     if (paramBoolean1) {
/* 210 */       super.setForeground((color1 == null) ? paramJTable.getSelectionForeground() : color1);
/*     */       
/* 212 */       super.setBackground((color2 == null) ? paramJTable.getSelectionBackground() : color2);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 217 */       Color color = (this.unselectedBackground != null) ? this.unselectedBackground : paramJTable.getBackground();
/* 218 */       if (color == null || color instanceof javax.swing.plaf.UIResource) {
/* 219 */         Color color3 = DefaultLookup.getColor(this, this.ui, "Table.alternateRowColor");
/* 220 */         if (color3 != null && paramInt1 % 2 != 0) {
/* 221 */           color = color3;
/*     */         }
/*     */       } 
/* 224 */       super.setForeground((this.unselectedForeground != null) ? this.unselectedForeground : paramJTable
/*     */           
/* 226 */           .getForeground());
/* 227 */       super.setBackground(color);
/*     */     } 
/*     */     
/* 230 */     setFont(paramJTable.getFont());
/*     */     
/* 232 */     if (paramBoolean2) {
/* 233 */       Border border = null;
/* 234 */       if (paramBoolean1) {
/* 235 */         border = DefaultLookup.getBorder(this, this.ui, "Table.focusSelectedCellHighlightBorder");
/*     */       }
/* 237 */       if (border == null) {
/* 238 */         border = DefaultLookup.getBorder(this, this.ui, "Table.focusCellHighlightBorder");
/*     */       }
/* 240 */       setBorder(border);
/*     */       
/* 242 */       if (!paramBoolean1 && paramJTable.isCellEditable(paramInt1, paramInt2)) {
/*     */         
/* 244 */         Color color = DefaultLookup.getColor(this, this.ui, "Table.focusCellForeground");
/* 245 */         if (color != null) {
/* 246 */           super.setForeground(color);
/*     */         }
/* 248 */         color = DefaultLookup.getColor(this, this.ui, "Table.focusCellBackground");
/* 249 */         if (color != null) {
/* 250 */           super.setBackground(color);
/*     */         }
/*     */       } 
/*     */     } else {
/* 254 */       setBorder(getNoFocusBorder());
/*     */     } 
/*     */     
/* 257 */     setValue(paramObject);
/*     */     
/* 259 */     return this;
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
/*     */   public boolean isOpaque() {
/* 276 */     Color color = getBackground();
/* 277 */     Container container = getParent();
/* 278 */     if (container != null) {
/* 279 */       container = container.getParent();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 285 */     boolean bool = (color != null && container != null && color.equals(container.getBackground()) && container.isOpaque()) ? true : false;
/* 286 */     return (!bool && super.isOpaque());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void revalidate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void repaint(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void repaint(Rectangle paramRectangle) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void repaint() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void firePropertyChange(String paramString, Object paramObject1, Object paramObject2) {
/* 343 */     if (paramString == "text" || paramString == "labelFor" || paramString == "displayedMnemonic" || ((paramString == "font" || paramString == "foreground") && paramObject1 != paramObject2 && 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 348 */       getClientProperty("html") != null))
/*     */     {
/* 350 */       super.firePropertyChange(paramString, paramObject1, paramObject2);
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
/*     */   
/*     */   public void firePropertyChange(String paramString, boolean paramBoolean1, boolean paramBoolean2) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setValue(Object paramObject) {
/* 372 */     setText((paramObject == null) ? "" : paramObject.toString());
/*     */   }
/*     */   
/*     */   public static class UIResource extends DefaultTableCellRenderer implements javax.swing.plaf.UIResource {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/table/DefaultTableCellRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */