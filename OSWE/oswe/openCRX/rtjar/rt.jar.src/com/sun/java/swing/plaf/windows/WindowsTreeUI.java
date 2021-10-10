/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicTreeUI;
/*     */ import javax.swing.tree.DefaultTreeCellRenderer;
/*     */ import javax.swing.tree.TreeCellRenderer;
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
/*     */ public class WindowsTreeUI
/*     */   extends BasicTreeUI
/*     */ {
/*     */   protected static final int HALF_SIZE = 4;
/*     */   protected static final int SIZE = 9;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  60 */     return new WindowsTreeUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void ensureRowsAreVisible(int paramInt1, int paramInt2) {
/*  69 */     if (this.tree != null && paramInt1 >= 0 && paramInt2 < getRowCount(this.tree)) {
/*  70 */       Rectangle rectangle = this.tree.getVisibleRect();
/*  71 */       if (paramInt1 == paramInt2) {
/*  72 */         Rectangle rectangle1 = getPathBounds(this.tree, 
/*  73 */             getPathForRow(this.tree, paramInt1));
/*     */         
/*  75 */         if (rectangle1 != null) {
/*  76 */           rectangle1.x = rectangle.x;
/*  77 */           rectangle1.width = rectangle.width;
/*  78 */           this.tree.scrollRectToVisible(rectangle1);
/*     */         } 
/*     */       } else {
/*     */         
/*  82 */         Rectangle rectangle1 = getPathBounds(this.tree, 
/*  83 */             getPathForRow(this.tree, paramInt1));
/*  84 */         if (rectangle1 != null) {
/*  85 */           Rectangle rectangle2 = rectangle1;
/*  86 */           int i = rectangle1.y;
/*  87 */           int j = i + rectangle.height;
/*     */           
/*  89 */           for (int k = paramInt1 + 1; k <= paramInt2; k++) {
/*  90 */             rectangle2 = getPathBounds(this.tree, 
/*  91 */                 getPathForRow(this.tree, k));
/*  92 */             if (rectangle2 != null && rectangle2.y + rectangle2.height > j) {
/*  93 */               k = paramInt2;
/*     */             }
/*     */           } 
/*     */           
/*  97 */           if (rectangle2 == null) {
/*     */             return;
/*     */           }
/*     */           
/* 101 */           this.tree.scrollRectToVisible(new Rectangle(rectangle.x, i, 1, rectangle2.y + rectangle2.height - i));
/*     */         } 
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
/*     */ 
/*     */   
/*     */   protected TreeCellRenderer createDefaultCellRenderer() {
/* 117 */     return new WindowsTreeCellRenderer();
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
/*     */   public static class ExpandedIcon
/*     */     implements Icon, Serializable
/*     */   {
/*     */     public static Icon createExpandedIcon() {
/* 133 */       return new ExpandedIcon();
/*     */     }
/*     */     
/*     */     XPStyle.Skin getSkin(Component param1Component) {
/* 137 */       XPStyle xPStyle = XPStyle.getXP();
/* 138 */       return (xPStyle != null) ? xPStyle.getSkin(param1Component, TMSchema.Part.TVP_GLYPH) : null;
/*     */     }
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 142 */       XPStyle.Skin skin = getSkin(param1Component);
/* 143 */       if (skin != null) {
/* 144 */         skin.paintSkin(param1Graphics, param1Int1, param1Int2, TMSchema.State.OPENED);
/*     */         
/*     */         return;
/*     */       } 
/* 148 */       Color color = param1Component.getBackground();
/*     */       
/* 150 */       if (color != null) {
/* 151 */         param1Graphics.setColor(color);
/*     */       } else {
/* 153 */         param1Graphics.setColor(Color.white);
/* 154 */       }  param1Graphics.fillRect(param1Int1, param1Int2, 8, 8);
/* 155 */       param1Graphics.setColor(Color.gray);
/* 156 */       param1Graphics.drawRect(param1Int1, param1Int2, 8, 8);
/* 157 */       param1Graphics.setColor(Color.black);
/* 158 */       param1Graphics.drawLine(param1Int1 + 2, param1Int2 + 4, param1Int1 + 6, param1Int2 + 4);
/*     */     }
/*     */     
/*     */     public int getIconWidth() {
/* 162 */       XPStyle.Skin skin = getSkin(null);
/* 163 */       return (skin != null) ? skin.getWidth() : 9;
/*     */     }
/*     */     
/*     */     public int getIconHeight() {
/* 167 */       XPStyle.Skin skin = getSkin(null);
/* 168 */       return (skin != null) ? skin.getHeight() : 9;
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
/*     */   public static class CollapsedIcon
/*     */     extends ExpandedIcon
/*     */   {
/*     */     public static Icon createCollapsedIcon() {
/* 184 */       return new CollapsedIcon();
/*     */     }
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 188 */       XPStyle.Skin skin = getSkin(param1Component);
/* 189 */       if (skin != null) {
/* 190 */         skin.paintSkin(param1Graphics, param1Int1, param1Int2, TMSchema.State.CLOSED);
/*     */       } else {
/* 192 */         super.paintIcon(param1Component, param1Graphics, param1Int1, param1Int2);
/* 193 */         param1Graphics.drawLine(param1Int1 + 4, param1Int2 + 2, param1Int1 + 4, param1Int2 + 6);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class WindowsTreeCellRenderer
/*     */     extends DefaultTreeCellRenderer
/*     */   {
/*     */     public Component getTreeCellRendererComponent(JTree param1JTree, Object param1Object, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3, int param1Int, boolean param1Boolean4) {
/* 213 */       super.getTreeCellRendererComponent(param1JTree, param1Object, param1Boolean1, param1Boolean2, param1Boolean3, param1Int, param1Boolean4);
/*     */ 
/*     */ 
/*     */       
/* 217 */       if (!param1JTree.isEnabled()) {
/* 218 */         setEnabled(false);
/* 219 */         if (param1Boolean3) {
/* 220 */           setDisabledIcon(getLeafIcon());
/* 221 */         } else if (param1Boolean1) {
/* 222 */           setDisabledIcon(getOpenIcon());
/*     */         } else {
/* 224 */           setDisabledIcon(getClosedIcon());
/*     */         } 
/*     */       } else {
/*     */         
/* 228 */         setEnabled(true);
/* 229 */         if (param1Boolean3) {
/* 230 */           setIcon(getLeafIcon());
/* 231 */         } else if (param1Boolean1) {
/* 232 */           setIcon(getOpenIcon());
/*     */         } else {
/* 234 */           setIcon(getClosedIcon());
/*     */         } 
/*     */       } 
/* 237 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsTreeUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */