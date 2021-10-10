/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicPopupMenuUI;
/*     */ import sun.swing.SwingUtilities2;
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
/*     */ public class MotifPopupMenuUI
/*     */   extends BasicPopupMenuUI
/*     */ {
/*  63 */   private static Border border = null;
/*  64 */   private Font titleFont = null;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  67 */     return new MotifPopupMenuUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/*  74 */     LayoutManager layoutManager = paramJComponent.getLayout();
/*  75 */     Dimension dimension = layoutManager.preferredLayoutSize(paramJComponent);
/*  76 */     String str = ((JPopupMenu)paramJComponent).getLabel();
/*  77 */     if (this.titleFont == null) {
/*  78 */       UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/*  79 */       this.titleFont = uIDefaults.getFont("PopupMenu.font");
/*     */     } 
/*  81 */     FontMetrics fontMetrics = paramJComponent.getFontMetrics(this.titleFont);
/*  82 */     int i = 0;
/*     */     
/*  84 */     if (str != null) {
/*  85 */       i += SwingUtilities2.stringWidth(paramJComponent, fontMetrics, str);
/*     */     }
/*     */     
/*  88 */     if (dimension.width < i) {
/*  89 */       dimension.width = i + 8;
/*  90 */       Insets insets = paramJComponent.getInsets();
/*  91 */       if (insets != null) {
/*  92 */         dimension.width += insets.left + insets.right;
/*     */       }
/*  94 */       if (border != null) {
/*  95 */         insets = border.getBorderInsets(paramJComponent);
/*  96 */         dimension.width += insets.left + insets.right;
/*     */       } 
/*     */       
/*  99 */       return dimension;
/*     */     } 
/* 101 */     return null;
/*     */   }
/*     */   
/*     */   protected ChangeListener createChangeListener(JPopupMenu paramJPopupMenu) {
/* 105 */     return new ChangeListener() {
/*     */         public void stateChanged(ChangeEvent param1ChangeEvent) {}
/*     */       };
/*     */   }
/*     */   
/*     */   public boolean isPopupTrigger(MouseEvent paramMouseEvent) {
/* 111 */     return (paramMouseEvent.getID() == 501 && (paramMouseEvent
/* 112 */       .getModifiers() & 0x4) != 0);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifPopupMenuUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */