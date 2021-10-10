/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicTextFieldUI;
/*     */ import javax.swing.text.Caret;
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
/*     */ public class SynthTextFieldUI
/*     */   extends BasicTextFieldUI
/*     */   implements SynthUI
/*     */ {
/*  54 */   private Handler handler = new Handler();
/*     */ 
/*     */ 
/*     */   
/*     */   private SynthStyle style;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  64 */     return new SynthTextFieldUI();
/*     */   }
/*     */   
/*     */   private void updateStyle(JTextComponent paramJTextComponent) {
/*  68 */     SynthContext synthContext = getContext(paramJTextComponent, 1);
/*  69 */     SynthStyle synthStyle = this.style;
/*     */     
/*  71 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*     */     
/*  73 */     if (this.style != synthStyle) {
/*  74 */       updateStyle(paramJTextComponent, synthContext, getPropertyPrefix());
/*     */       
/*  76 */       if (synthStyle != null) {
/*  77 */         uninstallKeyboardActions();
/*  78 */         installKeyboardActions();
/*     */       } 
/*     */     } 
/*  81 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */   
/*     */   static void updateStyle(JTextComponent paramJTextComponent, SynthContext paramSynthContext, String paramString) {
/*  86 */     SynthStyle synthStyle = paramSynthContext.getStyle();
/*     */     
/*  88 */     Color color1 = paramJTextComponent.getCaretColor();
/*  89 */     if (color1 == null || color1 instanceof javax.swing.plaf.UIResource) {
/*  90 */       paramJTextComponent.setCaretColor((Color)synthStyle
/*  91 */           .get(paramSynthContext, paramString + ".caretForeground"));
/*     */     }
/*     */     
/*  94 */     Color color2 = paramJTextComponent.getForeground();
/*  95 */     if (color2 == null || color2 instanceof javax.swing.plaf.UIResource) {
/*  96 */       color2 = synthStyle.getColorForState(paramSynthContext, ColorType.TEXT_FOREGROUND);
/*  97 */       if (color2 != null) {
/*  98 */         paramJTextComponent.setForeground(color2);
/*     */       }
/*     */     } 
/*     */     
/* 102 */     Object object = synthStyle.get(paramSynthContext, paramString + ".caretAspectRatio");
/* 103 */     if (object instanceof Number) {
/* 104 */       paramJTextComponent.putClientProperty("caretAspectRatio", object);
/*     */     }
/*     */     
/* 107 */     paramSynthContext.setComponentState(768);
/*     */     
/* 109 */     Color color3 = paramJTextComponent.getSelectionColor();
/* 110 */     if (color3 == null || color3 instanceof javax.swing.plaf.UIResource) {
/* 111 */       paramJTextComponent.setSelectionColor(synthStyle
/* 112 */           .getColor(paramSynthContext, ColorType.TEXT_BACKGROUND));
/*     */     }
/*     */     
/* 115 */     Color color4 = paramJTextComponent.getSelectedTextColor();
/* 116 */     if (color4 == null || color4 instanceof javax.swing.plaf.UIResource) {
/* 117 */       paramJTextComponent.setSelectedTextColor(synthStyle
/* 118 */           .getColor(paramSynthContext, ColorType.TEXT_FOREGROUND));
/*     */     }
/*     */     
/* 121 */     paramSynthContext.setComponentState(8);
/*     */     
/* 123 */     Color color5 = paramJTextComponent.getDisabledTextColor();
/* 124 */     if (color5 == null || color5 instanceof javax.swing.plaf.UIResource) {
/* 125 */       paramJTextComponent.setDisabledTextColor(synthStyle
/* 126 */           .getColor(paramSynthContext, ColorType.TEXT_FOREGROUND));
/*     */     }
/*     */     
/* 129 */     Insets insets = paramJTextComponent.getMargin();
/* 130 */     if (insets == null || insets instanceof javax.swing.plaf.UIResource) {
/* 131 */       insets = (Insets)synthStyle.get(paramSynthContext, paramString + ".margin");
/*     */       
/* 133 */       if (insets == null)
/*     */       {
/* 135 */         insets = SynthLookAndFeel.EMPTY_UIRESOURCE_INSETS;
/*     */       }
/* 137 */       paramJTextComponent.setMargin(insets);
/*     */     } 
/*     */     
/* 140 */     Caret caret = paramJTextComponent.getCaret();
/* 141 */     if (caret instanceof javax.swing.plaf.UIResource) {
/* 142 */       Object object1 = synthStyle.get(paramSynthContext, paramString + ".caretBlinkRate");
/* 143 */       if (object1 != null && object1 instanceof Integer) {
/* 144 */         Integer integer = (Integer)object1;
/* 145 */         caret.setBlinkRate(integer.intValue());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 155 */     return getContext(paramJComponent, SynthLookAndFeel.getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 159 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
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
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 176 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 178 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 179 */     paintBackground(synthContext, paramGraphics, paramJComponent);
/* 180 */     paint(synthContext, paramGraphics);
/* 181 */     synthContext.dispose();
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
/*     */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {
/* 197 */     paint(paramGraphics, getComponent());
/*     */   }
/*     */   
/*     */   void paintBackground(SynthContext paramSynthContext, Graphics paramGraphics, JComponent paramJComponent) {
/* 201 */     paramSynthContext.getPainter().paintTextFieldBackground(paramSynthContext, paramGraphics, 0, 0, paramJComponent
/* 202 */         .getWidth(), paramJComponent.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 211 */     paramSynthContext.getPainter().paintTextFieldBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
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
/*     */   protected void paintBackground(Graphics paramGraphics) {}
/*     */ 
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
/* 236 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/* 237 */       updateStyle((JTextComponent)paramPropertyChangeEvent.getSource());
/*     */     }
/* 239 */     super.propertyChange(paramPropertyChangeEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/* 248 */     super.installDefaults();
/* 249 */     updateStyle(getComponent());
/* 250 */     getComponent().addFocusListener(this.handler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 258 */     SynthContext synthContext = getContext(getComponent(), 1);
/*     */     
/* 260 */     getComponent().putClientProperty("caretAspectRatio", (Object)null);
/* 261 */     getComponent().removeFocusListener(this.handler);
/*     */     
/* 263 */     this.style.uninstallDefaults(synthContext);
/* 264 */     synthContext.dispose();
/* 265 */     this.style = null;
/* 266 */     super.uninstallDefaults();
/*     */   }
/*     */   
/*     */   private final class Handler implements FocusListener {
/*     */     public void focusGained(FocusEvent param1FocusEvent) {
/* 271 */       SynthTextFieldUI.this.getComponent().repaint();
/*     */     }
/*     */     private Handler() {}
/*     */     public void focusLost(FocusEvent param1FocusEvent) {
/* 275 */       SynthTextFieldUI.this.getComponent().repaint();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthTextFieldUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */