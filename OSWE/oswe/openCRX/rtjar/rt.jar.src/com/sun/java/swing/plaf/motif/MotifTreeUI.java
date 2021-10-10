/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicTreeUI;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MotifTreeUI
/*     */   extends BasicTreeUI
/*     */ {
/*     */   static final int HALF_SIZE = 7;
/*     */   static final int SIZE = 14;
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  64 */     super.installUI(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintVerticalLine(Graphics paramGraphics, JComponent paramJComponent, int paramInt1, int paramInt2, int paramInt3) {
/*  71 */     if (this.tree.getComponentOrientation().isLeftToRight()) {
/*  72 */       paramGraphics.fillRect(paramInt1, paramInt2, 2, paramInt3 - paramInt2 + 2);
/*     */     } else {
/*  74 */       paramGraphics.fillRect(paramInt1 - 1, paramInt2, 2, paramInt3 - paramInt2 + 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintHorizontalLine(Graphics paramGraphics, JComponent paramJComponent, int paramInt1, int paramInt2, int paramInt3) {
/*  80 */     paramGraphics.fillRect(paramInt2, paramInt1, paramInt3 - paramInt2 + 1, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MotifExpandedIcon
/*     */     implements Icon, Serializable
/*     */   {
/*     */     static Color bg;
/*     */ 
/*     */     
/*     */     static Color fg;
/*     */ 
/*     */     
/*     */     static Color highlight;
/*     */ 
/*     */     
/*     */     static Color shadow;
/*     */ 
/*     */     
/*     */     public MotifExpandedIcon() {
/* 101 */       bg = UIManager.getColor("Tree.iconBackground");
/* 102 */       fg = UIManager.getColor("Tree.iconForeground");
/* 103 */       highlight = UIManager.getColor("Tree.iconHighlight");
/* 104 */       shadow = UIManager.getColor("Tree.iconShadow");
/*     */     }
/*     */     
/*     */     public static Icon createExpandedIcon() {
/* 108 */       return new MotifExpandedIcon();
/*     */     }
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 112 */       param1Graphics.setColor(highlight);
/* 113 */       param1Graphics.drawLine(param1Int1, param1Int2, param1Int1 + 14 - 1, param1Int2);
/* 114 */       param1Graphics.drawLine(param1Int1, param1Int2 + 1, param1Int1, param1Int2 + 14 - 1);
/*     */       
/* 116 */       param1Graphics.setColor(shadow);
/* 117 */       param1Graphics.drawLine(param1Int1 + 14 - 1, param1Int2 + 1, param1Int1 + 14 - 1, param1Int2 + 14 - 1);
/* 118 */       param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 14 - 1, param1Int1 + 14 - 1, param1Int2 + 14 - 1);
/*     */       
/* 120 */       param1Graphics.setColor(bg);
/* 121 */       param1Graphics.fillRect(param1Int1 + 1, param1Int2 + 1, 12, 12);
/*     */       
/* 123 */       param1Graphics.setColor(fg);
/* 124 */       param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 7 - 1, param1Int1 + 14 - 4, param1Int2 + 7 - 1);
/* 125 */       param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 7, param1Int1 + 14 - 4, param1Int2 + 7);
/*     */     }
/*     */     
/* 128 */     public int getIconWidth() { return 14; } public int getIconHeight() {
/* 129 */       return 14;
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
/*     */   public static class MotifCollapsedIcon
/*     */     extends MotifExpandedIcon
/*     */   {
/*     */     public static Icon createCollapsedIcon() {
/* 144 */       return new MotifCollapsedIcon();
/*     */     }
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 148 */       super.paintIcon(param1Component, param1Graphics, param1Int1, param1Int2);
/* 149 */       param1Graphics.drawLine(param1Int1 + 7 - 1, param1Int2 + 3, param1Int1 + 7 - 1, param1Int2 + 10);
/* 150 */       param1Graphics.drawLine(param1Int1 + 7, param1Int2 + 3, param1Int1 + 7, param1Int2 + 10);
/*     */     }
/*     */   }
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 155 */     return new MotifTreeUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeCellRenderer createDefaultCellRenderer() {
/* 163 */     return new MotifTreeCellRenderer();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifTreeUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */