/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.text.Style;
/*     */ import javax.swing.text.StyleConstants;
/*     */ import javax.swing.text.StyledDocument;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthTextPaneUI
/*     */   extends SynthEditorPaneUI
/*     */ {
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  59 */     return new SynthTextPaneUI();
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
/*     */   protected String getPropertyPrefix() {
/*  71 */     return "TextPane";
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
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 100 */     super.installUI(paramJComponent);
/* 101 */     updateForeground(paramJComponent.getForeground());
/* 102 */     updateFont(paramJComponent.getFont());
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
/*     */   protected void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 118 */     super.propertyChange(paramPropertyChangeEvent);
/*     */     
/* 120 */     String str = paramPropertyChangeEvent.getPropertyName();
/*     */     
/* 122 */     if (str.equals("foreground")) {
/* 123 */       updateForeground((Color)paramPropertyChangeEvent.getNewValue());
/* 124 */     } else if (str.equals("font")) {
/* 125 */       updateFont((Font)paramPropertyChangeEvent.getNewValue());
/* 126 */     } else if (str.equals("document")) {
/* 127 */       JTextComponent jTextComponent = getComponent();
/* 128 */       updateForeground(jTextComponent.getForeground());
/* 129 */       updateFont(jTextComponent.getFont());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateForeground(Color paramColor) {
/* 140 */     StyledDocument styledDocument = (StyledDocument)getComponent().getDocument();
/* 141 */     Style style = styledDocument.getStyle("default");
/*     */     
/* 143 */     if (style == null) {
/*     */       return;
/*     */     }
/*     */     
/* 147 */     if (paramColor == null) {
/* 148 */       style.removeAttribute(StyleConstants.Foreground);
/*     */     } else {
/* 150 */       StyleConstants.setForeground(style, paramColor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateFont(Font paramFont) {
/* 161 */     StyledDocument styledDocument = (StyledDocument)getComponent().getDocument();
/* 162 */     Style style = styledDocument.getStyle("default");
/*     */     
/* 164 */     if (style == null) {
/*     */       return;
/*     */     }
/*     */     
/* 168 */     if (paramFont == null) {
/* 169 */       style.removeAttribute(StyleConstants.FontFamily);
/* 170 */       style.removeAttribute(StyleConstants.FontSize);
/* 171 */       style.removeAttribute(StyleConstants.Bold);
/* 172 */       style.removeAttribute(StyleConstants.Italic);
/*     */     } else {
/* 174 */       StyleConstants.setFontFamily(style, paramFont.getName());
/* 175 */       StyleConstants.setFontSize(style, paramFont.getSize());
/* 176 */       StyleConstants.setBold(style, paramFont.isBold());
/* 177 */       StyleConstants.setItalic(style, paramFont.isItalic());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void paintBackground(SynthContext paramSynthContext, Graphics paramGraphics, JComponent paramJComponent) {
/* 183 */     paramSynthContext.getPainter().paintTextPaneBackground(paramSynthContext, paramGraphics, 0, 0, paramJComponent
/* 184 */         .getWidth(), paramJComponent.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 193 */     paramSynthContext.getPainter().paintTextPaneBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthTextPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */