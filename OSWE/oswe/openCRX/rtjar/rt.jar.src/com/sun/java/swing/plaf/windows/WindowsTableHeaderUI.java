/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.SortOrder;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicTableHeaderUI;
/*     */ import javax.swing.table.JTableHeader;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import javax.swing.table.TableColumn;
/*     */ import sun.swing.SwingUtilities2;
/*     */ import sun.swing.table.DefaultTableCellHeaderRenderer;
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
/*     */ public class WindowsTableHeaderUI
/*     */   extends BasicTableHeaderUI
/*     */ {
/*     */   private TableCellRenderer originalHeaderRenderer;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  45 */     return new WindowsTableHeaderUI();
/*     */   }
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  49 */     super.installUI(paramJComponent);
/*     */     
/*  51 */     if (XPStyle.getXP() != null) {
/*  52 */       this.originalHeaderRenderer = this.header.getDefaultRenderer();
/*  53 */       if (this.originalHeaderRenderer instanceof UIResource) {
/*  54 */         this.header.setDefaultRenderer(new XPDefaultRenderer());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/*  60 */     if (this.header.getDefaultRenderer() instanceof XPDefaultRenderer) {
/*  61 */       this.header.setDefaultRenderer(this.originalHeaderRenderer);
/*     */     }
/*  63 */     super.uninstallUI(paramJComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void rolloverColumnUpdated(int paramInt1, int paramInt2) {
/*  68 */     if (XPStyle.getXP() != null) {
/*  69 */       this.header.repaint(this.header.getHeaderRect(paramInt1));
/*  70 */       this.header.repaint(this.header.getHeaderRect(paramInt2));
/*     */     } 
/*     */   }
/*     */   
/*     */   private class XPDefaultRenderer
/*     */     extends DefaultTableCellHeaderRenderer {
/*     */     XPStyle.Skin skin;
/*     */     boolean isSelected;
/*     */     
/*     */     XPDefaultRenderer() {
/*  80 */       setHorizontalAlignment(10);
/*     */     }
/*     */     boolean hasFocus; boolean hasRollover; int column;
/*     */     
/*     */     public Component getTableCellRendererComponent(JTable param1JTable, Object param1Object, boolean param1Boolean1, boolean param1Boolean2, int param1Int1, int param1Int2) {
/*     */       EmptyBorder emptyBorder;
/*  86 */       super.getTableCellRendererComponent(param1JTable, param1Object, param1Boolean1, param1Boolean2, param1Int1, param1Int2);
/*     */       
/*  88 */       this.isSelected = param1Boolean1;
/*  89 */       this.hasFocus = param1Boolean2;
/*  90 */       this.column = param1Int2;
/*  91 */       this.hasRollover = (param1Int2 == WindowsTableHeaderUI.this.getRolloverColumn());
/*  92 */       if (this.skin == null) {
/*  93 */         XPStyle xPStyle = XPStyle.getXP();
/*  94 */         this.skin = (xPStyle != null) ? xPStyle.getSkin(WindowsTableHeaderUI.this.header, TMSchema.Part.HP_HEADERITEM) : null;
/*     */       } 
/*  96 */       Insets insets = (this.skin != null) ? this.skin.getContentMargin() : null;
/*  97 */       WindowsTableHeaderUI.IconBorder iconBorder = null;
/*  98 */       int i = 0;
/*  99 */       int j = 0;
/* 100 */       int k = 0;
/* 101 */       int m = 0;
/* 102 */       if (insets != null) {
/* 103 */         i = insets.top;
/* 104 */         j = insets.left;
/* 105 */         k = insets.bottom;
/* 106 */         m = insets.right;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 114 */       j += 5;
/* 115 */       k += 4;
/* 116 */       m += 5;
/*     */ 
/*     */       
/*     */       Icon icon;
/*     */ 
/*     */       
/* 122 */       if (WindowsLookAndFeel.isOnVista() && (
/* 123 */         icon = getIcon() instanceof UIResource || icon == null)) {
/*     */         
/* 125 */         i++;
/* 126 */         setIcon((Icon)null);
/* 127 */         icon = null;
/*     */         
/* 129 */         SortOrder sortOrder = getColumnSortOrder(param1JTable, param1Int2);
/* 130 */         if (sortOrder != null) {
/* 131 */           switch (sortOrder) {
/*     */             
/*     */             case NORMAL:
/* 134 */               icon = UIManager.getIcon("Table.ascendingSortIcon");
/*     */               break;
/*     */             
/*     */             case PRESSED:
/* 138 */               icon = UIManager.getIcon("Table.descendingSortIcon");
/*     */               break;
/*     */           } 
/*     */         }
/* 142 */         if (icon != null) {
/* 143 */           k = icon.getIconHeight();
/* 144 */           iconBorder = new WindowsTableHeaderUI.IconBorder(icon, i, j, k, m);
/*     */         }
/*     */         else {
/*     */           
/* 148 */           icon = UIManager.getIcon("Table.ascendingSortIcon");
/*     */           
/* 150 */           byte b = (icon != null) ? icon.getIconHeight() : 0;
/* 151 */           if (b) {
/* 152 */             k = b;
/*     */           }
/* 154 */           emptyBorder = new EmptyBorder(b + i, j, k, m);
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 160 */         i += 3;
/* 161 */         emptyBorder = new EmptyBorder(i, j, k, m);
/*     */       } 
/*     */       
/* 164 */       setBorder(emptyBorder);
/* 165 */       return this;
/*     */     }
/*     */     
/*     */     public void paint(Graphics param1Graphics) {
/* 169 */       Dimension dimension = getSize();
/* 170 */       TMSchema.State state = TMSchema.State.NORMAL;
/* 171 */       TableColumn tableColumn = WindowsTableHeaderUI.this.header.getDraggedColumn();
/* 172 */       if (tableColumn != null && this.column == 
/* 173 */         SwingUtilities2.convertColumnIndexToView(WindowsTableHeaderUI.this
/* 174 */           .header.getColumnModel(), tableColumn.getModelIndex())) {
/* 175 */         state = TMSchema.State.PRESSED;
/* 176 */       } else if (this.isSelected || this.hasFocus || this.hasRollover) {
/* 177 */         state = TMSchema.State.HOT;
/*     */       } 
/*     */       
/* 180 */       if (WindowsLookAndFeel.isOnVista()) {
/* 181 */         SortOrder sortOrder = getColumnSortOrder(WindowsTableHeaderUI.this.header.getTable(), this.column);
/* 182 */         if (sortOrder != null) {
/* 183 */           switch (sortOrder) {
/*     */             
/*     */             case NORMAL:
/*     */             case PRESSED:
/* 187 */               switch (state) {
/*     */                 case NORMAL:
/* 189 */                   state = TMSchema.State.SORTEDNORMAL;
/*     */                   break;
/*     */                 case PRESSED:
/* 192 */                   state = TMSchema.State.SORTEDPRESSED;
/*     */                   break;
/*     */                 case HOT:
/* 195 */                   state = TMSchema.State.SORTEDHOT;
/*     */                   break;
/*     */               } 
/*     */ 
/*     */               
/*     */               break;
/*     */           } 
/*     */         
/*     */         }
/*     */       } 
/* 205 */       this.skin.paintSkin(param1Graphics, 0, 0, dimension.width - 1, dimension.height - 1, state);
/* 206 */       super.paint(param1Graphics);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class IconBorder
/*     */     implements Border, UIResource
/*     */   {
/*     */     private final Icon icon;
/*     */     
/*     */     private final int top;
/*     */     
/*     */     private final int left;
/*     */     
/*     */     private final int bottom;
/*     */     
/*     */     private final int right;
/*     */ 
/*     */     
/*     */     public IconBorder(Icon param1Icon, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 227 */       this.icon = param1Icon;
/* 228 */       this.top = param1Int1;
/* 229 */       this.left = param1Int2;
/* 230 */       this.bottom = param1Int3;
/* 231 */       this.right = param1Int4;
/*     */     }
/*     */     public Insets getBorderInsets(Component param1Component) {
/* 234 */       return new Insets(this.icon.getIconHeight() + this.top, this.left, this.bottom, this.right);
/*     */     }
/*     */     public boolean isBorderOpaque() {
/* 237 */       return false;
/*     */     }
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 241 */       this.icon.paintIcon(param1Component, param1Graphics, param1Int1 + this.left + (param1Int3 - this.left - this.right - this.icon
/* 242 */           .getIconWidth()) / 2, param1Int2 + this.top);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsTableHeaderUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */