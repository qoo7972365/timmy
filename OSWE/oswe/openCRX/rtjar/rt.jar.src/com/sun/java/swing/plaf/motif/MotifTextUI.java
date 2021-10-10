/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.FocusEvent;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.plaf.TextUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Caret;
/*     */ import javax.swing.text.DefaultCaret;
/*     */ import javax.swing.text.JTextComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MotifTextUI
/*     */ {
/*     */   public static Caret createCaret() {
/*  56 */     return new MotifCaret();
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
/*     */   public static class MotifCaret
/*     */     extends DefaultCaret
/*     */     implements UIResource
/*     */   {
/*     */     static final int IBeamOverhang = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void focusGained(FocusEvent param1FocusEvent) {
/*  81 */       super.focusGained(param1FocusEvent);
/*  82 */       getComponent().repaint();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void focusLost(FocusEvent param1FocusEvent) {
/*  94 */       super.focusLost(param1FocusEvent);
/*  95 */       getComponent().repaint();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void damage(Rectangle param1Rectangle) {
/* 107 */       if (param1Rectangle != null) {
/* 108 */         this.x = param1Rectangle.x - 2 - 1;
/* 109 */         this.y = param1Rectangle.y;
/* 110 */         this.width = param1Rectangle.width + 4 + 3;
/* 111 */         this.height = param1Rectangle.height;
/* 112 */         repaint();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void paint(Graphics param1Graphics) {
/* 127 */       if (isVisible()) {
/*     */         try {
/* 129 */           JTextComponent jTextComponent = getComponent();
/*     */           
/* 131 */           Color color = jTextComponent.hasFocus() ? jTextComponent.getCaretColor() : jTextComponent.getDisabledTextColor();
/* 132 */           TextUI textUI = jTextComponent.getUI();
/* 133 */           int i = getDot();
/* 134 */           Rectangle rectangle = textUI.modelToView(jTextComponent, i);
/* 135 */           int j = rectangle.x - 2;
/* 136 */           int k = rectangle.x + 2;
/* 137 */           int m = rectangle.y + 1;
/* 138 */           int n = rectangle.y + rectangle.height - 2;
/* 139 */           param1Graphics.setColor(color);
/* 140 */           param1Graphics.drawLine(rectangle.x, m, rectangle.x, n);
/* 141 */           param1Graphics.drawLine(j, m, k, m);
/* 142 */           param1Graphics.drawLine(j, n, k, n);
/* 143 */         } catch (BadLocationException badLocationException) {}
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
/* 156 */   static final JTextComponent.KeyBinding[] defaultBindings = new JTextComponent.KeyBinding[] { new JTextComponent.KeyBinding(
/* 157 */         KeyStroke.getKeyStroke(155, 2), "copy-to-clipboard"), new JTextComponent.KeyBinding(
/*     */ 
/*     */         
/* 160 */         KeyStroke.getKeyStroke(155, 1), "paste-from-clipboard"), new JTextComponent.KeyBinding(
/*     */ 
/*     */         
/* 163 */         KeyStroke.getKeyStroke(127, 1), "cut-to-clipboard"), new JTextComponent.KeyBinding(
/*     */ 
/*     */         
/* 166 */         KeyStroke.getKeyStroke(37, 1), "selection-backward"), new JTextComponent.KeyBinding(
/*     */ 
/*     */         
/* 169 */         KeyStroke.getKeyStroke(39, 1), "selection-forward") };
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifTextUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */