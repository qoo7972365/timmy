/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.BoundedRangeModel;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.TextUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicTextFieldUI;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Caret;
/*     */ import javax.swing.text.DefaultCaret;
/*     */ import javax.swing.text.Highlighter;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.text.Position;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsTextFieldUI
/*     */   extends BasicTextFieldUI
/*     */ {
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  75 */     return new WindowsTextFieldUI();
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
/*     */   protected void paintBackground(Graphics paramGraphics) {
/*  87 */     super.paintBackground(paramGraphics);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Caret createCaret() {
/*  96 */     return new WindowsFieldCaret();
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
/*     */   static class WindowsFieldCaret
/*     */     extends DefaultCaret
/*     */     implements UIResource
/*     */   {
/*     */     protected void adjustVisibility(Rectangle param1Rectangle) {
/* 116 */       SwingUtilities.invokeLater(new SafeScroller(param1Rectangle));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Highlighter.HighlightPainter getSelectionPainter() {
/* 125 */       return WindowsTextUI.WindowsPainter;
/*     */     }
/*     */     
/*     */     private class SafeScroller
/*     */       implements Runnable {
/*     */       SafeScroller(Rectangle param2Rectangle) {
/* 131 */         this.r = param2Rectangle;
/*     */       }
/*     */       private Rectangle r;
/*     */       public void run() {
/* 135 */         JTextField jTextField = (JTextField)WindowsTextFieldUI.WindowsFieldCaret.this.getComponent();
/* 136 */         if (jTextField != null) {
/* 137 */           TextUI textUI = jTextField.getUI();
/* 138 */           int i = WindowsTextFieldUI.WindowsFieldCaret.this.getDot();
/*     */           
/* 140 */           Position.Bias bias = Position.Bias.Forward;
/* 141 */           Rectangle rectangle = null;
/*     */           try {
/* 143 */             rectangle = textUI.modelToView(jTextField, i, bias);
/* 144 */           } catch (BadLocationException badLocationException) {}
/*     */           
/* 146 */           Insets insets = jTextField.getInsets();
/* 147 */           BoundedRangeModel boundedRangeModel = jTextField.getHorizontalVisibility();
/* 148 */           int j = this.r.x + boundedRangeModel.getValue() - insets.left;
/* 149 */           int k = boundedRangeModel.getExtent() / 4;
/* 150 */           if (this.r.x < insets.left) {
/* 151 */             boundedRangeModel.setValue(j - k);
/* 152 */           } else if (this.r.x + this.r.width > insets.left + boundedRangeModel.getExtent()) {
/* 153 */             boundedRangeModel.setValue(j - 3 * k);
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 159 */           if (rectangle != null)
/*     */             
/*     */             try {
/* 162 */               Rectangle rectangle1 = textUI.modelToView(jTextField, i, bias);
/* 163 */               if (rectangle1 != null && !rectangle1.equals(rectangle)) {
/* 164 */                 WindowsTextFieldUI.WindowsFieldCaret.this.damage(rectangle1);
/*     */               }
/* 166 */             } catch (BadLocationException badLocationException) {} 
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsTextFieldUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */