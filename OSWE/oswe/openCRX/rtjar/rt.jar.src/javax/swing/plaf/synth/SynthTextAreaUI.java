/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicTextAreaUI;
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
/*     */ 
/*     */ public class SynthTextAreaUI
/*     */   extends BasicTextAreaUI
/*     */   implements SynthUI
/*     */ {
/*  55 */   private Handler handler = new Handler();
/*     */ 
/*     */ 
/*     */   
/*     */   private SynthStyle style;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  65 */     return new SynthTextAreaUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  74 */     super.installDefaults();
/*  75 */     updateStyle(getComponent());
/*  76 */     getComponent().addFocusListener(this.handler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/*  84 */     SynthContext synthContext = getContext(getComponent(), 1);
/*     */     
/*  86 */     getComponent().putClientProperty("caretAspectRatio", (Object)null);
/*  87 */     getComponent().removeFocusListener(this.handler);
/*     */     
/*  89 */     this.style.uninstallDefaults(synthContext);
/*  90 */     synthContext.dispose();
/*  91 */     this.style = null;
/*  92 */     super.uninstallDefaults();
/*     */   }
/*     */   
/*     */   private void updateStyle(JTextComponent paramJTextComponent) {
/*  96 */     SynthContext synthContext = getContext(paramJTextComponent, 1);
/*  97 */     SynthStyle synthStyle = this.style;
/*     */     
/*  99 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*     */     
/* 101 */     if (this.style != synthStyle) {
/* 102 */       SynthTextFieldUI.updateStyle(paramJTextComponent, synthContext, getPropertyPrefix());
/*     */       
/* 104 */       if (synthStyle != null) {
/* 105 */         uninstallKeyboardActions();
/* 106 */         installKeyboardActions();
/*     */       } 
/*     */     } 
/* 109 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 117 */     return getContext(paramJComponent, SynthLookAndFeel.getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 121 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
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
/* 138 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 140 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 141 */     synthContext.getPainter().paintTextAreaBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 142 */         .getWidth(), paramJComponent.getHeight());
/* 143 */     paint(synthContext, paramGraphics);
/* 144 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {
/* 155 */     paint(paramGraphics, getComponent());
/*     */   }
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
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 174 */     paramSynthContext.getPainter().paintTextAreaBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
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
/*     */   protected void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 189 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/* 190 */       updateStyle((JTextComponent)paramPropertyChangeEvent.getSource());
/*     */     }
/* 192 */     super.propertyChange(paramPropertyChangeEvent);
/*     */   }
/*     */   
/*     */   private final class Handler implements FocusListener {
/*     */     public void focusGained(FocusEvent param1FocusEvent) {
/* 197 */       SynthTextAreaUI.this.getComponent().repaint();
/*     */     }
/*     */     private Handler() {}
/*     */     public void focusLost(FocusEvent param1FocusEvent) {
/* 201 */       SynthTextAreaUI.this.getComponent().repaint();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthTextAreaUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */