/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.CompoundBorder;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicScrollPaneUI;
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
/*     */ public class MotifScrollPaneUI
/*     */   extends BasicScrollPaneUI
/*     */ {
/*  50 */   private static final Border vsbMarginBorderR = new EmptyBorder(0, 4, 0, 0);
/*  51 */   private static final Border vsbMarginBorderL = new EmptyBorder(0, 0, 0, 4);
/*  52 */   private static final Border hsbMarginBorder = new EmptyBorder(4, 0, 0, 0);
/*     */   
/*     */   private CompoundBorder vsbBorder;
/*     */   
/*     */   private CompoundBorder hsbBorder;
/*     */   
/*     */   private PropertyChangeListener propertyChangeHandler;
/*     */   
/*     */   protected void installListeners(JScrollPane paramJScrollPane) {
/*  61 */     super.installListeners(paramJScrollPane);
/*  62 */     this.propertyChangeHandler = createPropertyChangeHandler();
/*  63 */     paramJScrollPane.addPropertyChangeListener(this.propertyChangeHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void uninstallListeners(JComponent paramJComponent) {
/*  68 */     super.uninstallListeners(paramJComponent);
/*  69 */     paramJComponent.removePropertyChangeListener(this.propertyChangeHandler);
/*     */   }
/*     */   
/*     */   private PropertyChangeListener createPropertyChangeHandler() {
/*  73 */     return new PropertyChangeListener()
/*     */       {
/*     */         public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  76 */           String str = param1PropertyChangeEvent.getPropertyName();
/*     */           
/*  78 */           if (str.equals("componentOrientation")) {
/*  79 */             JScrollPane jScrollPane = (JScrollPane)param1PropertyChangeEvent.getSource();
/*  80 */             JScrollBar jScrollBar = jScrollPane.getVerticalScrollBar();
/*  81 */             if (jScrollBar != null && MotifScrollPaneUI.this.vsbBorder != null && jScrollBar
/*  82 */               .getBorder() == MotifScrollPaneUI.this.vsbBorder) {
/*     */ 
/*     */               
/*  85 */               if (MotifGraphicsUtils.isLeftToRight(jScrollPane)) {
/*  86 */                 MotifScrollPaneUI.this.vsbBorder = new CompoundBorder(MotifScrollPaneUI.vsbMarginBorderR, MotifScrollPaneUI.this
/*  87 */                     .vsbBorder.getInsideBorder());
/*     */               } else {
/*  89 */                 MotifScrollPaneUI.this.vsbBorder = new CompoundBorder(MotifScrollPaneUI.vsbMarginBorderL, MotifScrollPaneUI.this
/*  90 */                     .vsbBorder.getInsideBorder());
/*     */               } 
/*  92 */               jScrollBar.setBorder(MotifScrollPaneUI.this.vsbBorder);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected void installDefaults(JScrollPane paramJScrollPane) {
/* 100 */     super.installDefaults(paramJScrollPane);
/*     */     
/* 102 */     JScrollBar jScrollBar1 = paramJScrollPane.getVerticalScrollBar();
/* 103 */     if (jScrollBar1 != null) {
/* 104 */       if (MotifGraphicsUtils.isLeftToRight(paramJScrollPane)) {
/* 105 */         this
/* 106 */           .vsbBorder = new CompoundBorder(vsbMarginBorderR, jScrollBar1.getBorder());
/*     */       } else {
/*     */         
/* 109 */         this
/* 110 */           .vsbBorder = new CompoundBorder(vsbMarginBorderL, jScrollBar1.getBorder());
/*     */       } 
/* 112 */       jScrollBar1.setBorder(this.vsbBorder);
/*     */     } 
/*     */     
/* 115 */     JScrollBar jScrollBar2 = paramJScrollPane.getHorizontalScrollBar();
/* 116 */     if (jScrollBar2 != null) {
/* 117 */       this.hsbBorder = new CompoundBorder(hsbMarginBorder, jScrollBar2.getBorder());
/* 118 */       jScrollBar2.setBorder(this.hsbBorder);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults(JScrollPane paramJScrollPane) {
/* 124 */     super.uninstallDefaults(paramJScrollPane);
/*     */     
/* 126 */     JScrollBar jScrollBar1 = this.scrollpane.getVerticalScrollBar();
/* 127 */     if (jScrollBar1 != null) {
/* 128 */       if (jScrollBar1.getBorder() == this.vsbBorder) {
/* 129 */         jScrollBar1.setBorder((Border)null);
/*     */       }
/* 131 */       this.vsbBorder = null;
/*     */     } 
/*     */     
/* 134 */     JScrollBar jScrollBar2 = this.scrollpane.getHorizontalScrollBar();
/* 135 */     if (jScrollBar2 != null) {
/* 136 */       if (jScrollBar2.getBorder() == this.hsbBorder) {
/* 137 */         jScrollBar2.setBorder((Border)null);
/*     */       }
/* 139 */       this.hsbBorder = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 145 */     return new MotifScrollPaneUI();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifScrollPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */