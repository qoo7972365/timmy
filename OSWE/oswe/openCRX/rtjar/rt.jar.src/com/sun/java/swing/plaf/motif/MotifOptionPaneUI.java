/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicOptionPaneUI;
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
/*     */ public class MotifOptionPaneUI
/*     */   extends BasicOptionPaneUI
/*     */ {
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  57 */     return new MotifOptionPaneUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Container createButtonArea() {
/*  65 */     Container container = super.createButtonArea();
/*     */     
/*  67 */     if (container != null && container.getLayout() instanceof BasicOptionPaneUI.ButtonAreaLayout) {
/*  68 */       ((BasicOptionPaneUI.ButtonAreaLayout)container.getLayout()).setCentersChildren(false);
/*     */     }
/*  70 */     return container;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumOptionPaneSize() {
/*  77 */     return null;
/*     */   }
/*     */   
/*     */   protected Container createSeparator() {
/*  81 */     return new JPanel()
/*     */       {
/*     */         public Dimension getPreferredSize() {
/*  84 */           return new Dimension(10, 2);
/*     */         }
/*     */         
/*     */         public void paint(Graphics param1Graphics) {
/*  88 */           int i = getWidth();
/*  89 */           param1Graphics.setColor(Color.darkGray);
/*  90 */           param1Graphics.drawLine(0, 0, i, 0);
/*  91 */           param1Graphics.setColor(Color.white);
/*  92 */           param1Graphics.drawLine(0, 1, i, 1);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addIcon(Container paramContainer) {
/* 104 */     Icon icon = getIcon();
/*     */     
/* 106 */     if (icon != null) {
/* 107 */       JLabel jLabel = new JLabel(icon);
/*     */       
/* 109 */       jLabel.setVerticalAlignment(0);
/* 110 */       paramContainer.add(jLabel, "West");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifOptionPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */