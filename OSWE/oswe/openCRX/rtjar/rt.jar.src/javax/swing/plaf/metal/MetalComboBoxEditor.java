/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.plaf.basic.BasicComboBoxEditor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalComboBoxEditor
/*     */   extends BasicComboBoxEditor
/*     */ {
/*     */   public MetalComboBoxEditor() {
/*  55 */     this.editor = new JTextField("", 9)
/*     */       {
/*     */         public void setText(String param1String) {
/*  58 */           if (getText().equals(param1String)) {
/*     */             return;
/*     */           }
/*  61 */           super.setText(param1String);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public Dimension getPreferredSize() {
/*  67 */           Dimension dimension = super.getPreferredSize();
/*  68 */           dimension.height += 4;
/*  69 */           return dimension;
/*     */         }
/*     */         public Dimension getMinimumSize() {
/*  72 */           Dimension dimension = super.getMinimumSize();
/*  73 */           dimension.height += 4;
/*  74 */           return dimension;
/*     */         }
/*     */       };
/*     */     
/*  78 */     this.editor.setBorder(new EditorBorder());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   protected static Insets editorBorderInsets = new Insets(2, 2, 2, 0);
/*     */   
/*     */   class EditorBorder extends AbstractBorder {
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  90 */       param1Graphics.translate(param1Int1, param1Int2);
/*     */       
/*  92 */       if (MetalLookAndFeel.usingOcean()) {
/*  93 */         param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/*  94 */         param1Graphics.drawRect(0, 0, param1Int3, param1Int4 - 1);
/*  95 */         param1Graphics.setColor(MetalLookAndFeel.getControlShadow());
/*  96 */         param1Graphics.drawRect(1, 1, param1Int3 - 2, param1Int4 - 3);
/*     */       } else {
/*     */         
/*  99 */         param1Graphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 100 */         param1Graphics.drawLine(0, 0, param1Int3 - 1, 0);
/* 101 */         param1Graphics.drawLine(0, 0, 0, param1Int4 - 2);
/* 102 */         param1Graphics.drawLine(0, param1Int4 - 2, param1Int3 - 1, param1Int4 - 2);
/* 103 */         param1Graphics.setColor(MetalLookAndFeel.getControlHighlight());
/* 104 */         param1Graphics.drawLine(1, 1, param1Int3 - 1, 1);
/* 105 */         param1Graphics.drawLine(1, 1, 1, param1Int4 - 1);
/* 106 */         param1Graphics.drawLine(1, param1Int4 - 1, param1Int3 - 1, param1Int4 - 1);
/* 107 */         param1Graphics.setColor(MetalLookAndFeel.getControl());
/* 108 */         param1Graphics.drawLine(1, param1Int4 - 2, 1, param1Int4 - 2);
/*     */       } 
/*     */       
/* 111 */       param1Graphics.translate(-param1Int1, -param1Int2);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 115 */       param1Insets.set(2, 2, 2, 0);
/* 116 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class UIResource extends MetalComboBoxEditor implements javax.swing.plaf.UIResource {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalComboBoxEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */