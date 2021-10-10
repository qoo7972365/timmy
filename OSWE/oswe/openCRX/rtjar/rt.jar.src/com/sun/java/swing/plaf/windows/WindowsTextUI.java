/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import javax.swing.plaf.TextUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicTextUI;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Caret;
/*     */ import javax.swing.text.DefaultCaret;
/*     */ import javax.swing.text.DefaultHighlighter;
/*     */ import javax.swing.text.Highlighter;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.text.LayeredHighlighter;
/*     */ import javax.swing.text.Position;
/*     */ import javax.swing.text.View;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WindowsTextUI
/*     */   extends BasicTextUI
/*     */ {
/*     */   protected Caret createCaret() {
/*  58 */     return new WindowsCaret();
/*     */   }
/*     */ 
/*     */   
/*  62 */   static LayeredHighlighter.LayerPainter WindowsPainter = new WindowsHighlightPainter(null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class WindowsCaret
/*     */     extends DefaultCaret
/*     */     implements UIResource
/*     */   {
/*     */     protected Highlighter.HighlightPainter getSelectionPainter() {
/*  73 */       return WindowsTextUI.WindowsPainter;
/*     */     }
/*     */   }
/*     */   
/*     */   static class WindowsHighlightPainter
/*     */     extends DefaultHighlighter.DefaultHighlightPainter
/*     */   {
/*     */     WindowsHighlightPainter(Color param1Color) {
/*  81 */       super(param1Color);
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
/*     */     
/*     */     public void paint(Graphics param1Graphics, int param1Int1, int param1Int2, Shape param1Shape, JTextComponent param1JTextComponent) {
/*  96 */       Rectangle rectangle = param1Shape.getBounds();
/*     */       
/*     */       try {
/*  99 */         TextUI textUI = param1JTextComponent.getUI();
/* 100 */         Rectangle rectangle1 = textUI.modelToView(param1JTextComponent, param1Int1);
/* 101 */         Rectangle rectangle2 = textUI.modelToView(param1JTextComponent, param1Int2);
/*     */ 
/*     */         
/* 104 */         Color color = getColor();
/*     */         
/* 106 */         if (color == null) {
/* 107 */           param1Graphics.setColor(param1JTextComponent.getSelectionColor());
/*     */         } else {
/*     */           
/* 110 */           param1Graphics.setColor(color);
/*     */         } 
/* 112 */         boolean bool1 = false;
/* 113 */         boolean bool2 = false;
/* 114 */         if (param1JTextComponent.isEditable()) {
/* 115 */           int i = param1JTextComponent.getCaretPosition();
/* 116 */           bool1 = (param1Int1 == i) ? true : false;
/* 117 */           bool2 = (param1Int2 == i) ? true : false;
/*     */         } 
/* 119 */         if (rectangle1.y == rectangle2.y) {
/*     */           
/* 121 */           Rectangle rectangle3 = rectangle1.union(rectangle2);
/* 122 */           if (rectangle3.width > 0) {
/* 123 */             if (bool1) {
/* 124 */               rectangle3.x++;
/* 125 */               rectangle3.width--;
/*     */             }
/* 127 */             else if (bool2) {
/* 128 */               rectangle3.width--;
/*     */             } 
/*     */           }
/* 131 */           param1Graphics.fillRect(rectangle3.x, rectangle3.y, rectangle3.width, rectangle3.height);
/*     */         } else {
/*     */           
/* 134 */           int i = rectangle.x + rectangle.width - rectangle1.x;
/* 135 */           if (bool1 && i > 0) {
/* 136 */             rectangle1.x++;
/* 137 */             i--;
/*     */           } 
/* 139 */           param1Graphics.fillRect(rectangle1.x, rectangle1.y, i, rectangle1.height);
/* 140 */           if (rectangle1.y + rectangle1.height != rectangle2.y) {
/* 141 */             param1Graphics.fillRect(rectangle.x, rectangle1.y + rectangle1.height, rectangle.width, rectangle2.y - rectangle1.y + rectangle1.height);
/*     */           }
/*     */           
/* 144 */           if (bool2 && rectangle2.x > rectangle.x) {
/* 145 */             rectangle2.x--;
/*     */           }
/* 147 */           param1Graphics.fillRect(rectangle.x, rectangle2.y, rectangle2.x - rectangle.x, rectangle2.height);
/*     */         } 
/* 149 */       } catch (BadLocationException badLocationException) {}
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Shape paintLayer(Graphics param1Graphics, int param1Int1, int param1Int2, Shape param1Shape, JTextComponent param1JTextComponent, View param1View) {
/* 169 */       Color color = getColor();
/*     */       
/* 171 */       if (color == null) {
/* 172 */         param1Graphics.setColor(param1JTextComponent.getSelectionColor());
/*     */       } else {
/*     */         
/* 175 */         param1Graphics.setColor(color);
/*     */       } 
/* 177 */       boolean bool1 = false;
/* 178 */       boolean bool2 = false;
/* 179 */       if (param1JTextComponent.isEditable()) {
/* 180 */         int i = param1JTextComponent.getCaretPosition();
/* 181 */         bool1 = (param1Int1 == i) ? true : false;
/* 182 */         bool2 = (param1Int2 == i) ? true : false;
/*     */       } 
/* 184 */       if (param1Int1 == param1View.getStartOffset() && param1Int2 == param1View
/* 185 */         .getEndOffset()) {
/*     */         Rectangle rectangle;
/*     */         
/* 188 */         if (param1Shape instanceof Rectangle) {
/* 189 */           rectangle = (Rectangle)param1Shape;
/*     */         } else {
/*     */           
/* 192 */           rectangle = param1Shape.getBounds();
/*     */         } 
/* 194 */         if (bool1 && rectangle.width > 0) {
/* 195 */           param1Graphics.fillRect(rectangle.x + 1, rectangle.y, rectangle.width - 1, rectangle.height);
/*     */         
/*     */         }
/* 198 */         else if (bool2 && rectangle.width > 0) {
/* 199 */           param1Graphics.fillRect(rectangle.x, rectangle.y, rectangle.width - 1, rectangle.height);
/*     */         }
/*     */         else {
/*     */           
/* 203 */           param1Graphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */         } 
/* 205 */         return rectangle;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 211 */         Shape shape = param1View.modelToView(param1Int1, Position.Bias.Forward, param1Int2, Position.Bias.Backward, param1Shape);
/*     */ 
/*     */ 
/*     */         
/* 215 */         Rectangle rectangle = (shape instanceof Rectangle) ? (Rectangle)shape : shape.getBounds();
/* 216 */         if (bool1 && rectangle.width > 0) {
/* 217 */           param1Graphics.fillRect(rectangle.x + 1, rectangle.y, rectangle.width - 1, rectangle.height);
/*     */         }
/* 219 */         else if (bool2 && rectangle.width > 0) {
/* 220 */           param1Graphics.fillRect(rectangle.x, rectangle.y, rectangle.width - 1, rectangle.height);
/*     */         } else {
/*     */           
/* 223 */           param1Graphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */         } 
/* 225 */         return rectangle;
/* 226 */       } catch (BadLocationException badLocationException) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 231 */         return null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsTextUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */