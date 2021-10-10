/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicEditorPaneUI;
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
/*     */ public class SynthEditorPaneUI
/*     */   extends BasicEditorPaneUI
/*     */   implements SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*  48 */   private Boolean localTrue = Boolean.TRUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  57 */     return new SynthEditorPaneUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  66 */     super.installDefaults();
/*  67 */     JTextComponent jTextComponent = getComponent();
/*     */     
/*  69 */     Object object = jTextComponent.getClientProperty("JEditorPane.honorDisplayProperties");
/*  70 */     if (object == null) {
/*  71 */       jTextComponent.putClientProperty("JEditorPane.honorDisplayProperties", this.localTrue);
/*     */     }
/*  73 */     updateStyle(getComponent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/*  81 */     SynthContext synthContext = getContext(getComponent(), 1);
/*  82 */     JTextComponent jTextComponent = getComponent();
/*  83 */     jTextComponent.putClientProperty("caretAspectRatio", null);
/*     */     
/*  85 */     this.style.uninstallDefaults(synthContext);
/*  86 */     synthContext.dispose();
/*  87 */     this.style = null;
/*     */ 
/*     */     
/*  90 */     Object object = jTextComponent.getClientProperty("JEditorPane.honorDisplayProperties");
/*  91 */     if (object == this.localTrue) {
/*  92 */       jTextComponent.putClientProperty("JEditorPane.honorDisplayProperties", Boolean.FALSE);
/*     */     }
/*     */     
/*  95 */     super.uninstallDefaults();
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
/* 110 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/* 111 */       updateStyle((JTextComponent)paramPropertyChangeEvent.getSource());
/*     */     }
/* 113 */     super.propertyChange(paramPropertyChangeEvent);
/*     */   }
/*     */   
/*     */   private void updateStyle(JTextComponent paramJTextComponent) {
/* 117 */     SynthContext synthContext = getContext(paramJTextComponent, 1);
/* 118 */     SynthStyle synthStyle = this.style;
/*     */     
/* 120 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*     */     
/* 122 */     if (this.style != synthStyle) {
/* 123 */       SynthTextFieldUI.updateStyle(paramJTextComponent, synthContext, getPropertyPrefix());
/*     */       
/* 125 */       if (synthStyle != null) {
/* 126 */         uninstallKeyboardActions();
/* 127 */         installKeyboardActions();
/*     */       } 
/*     */     } 
/* 130 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 138 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 142 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/* 146 */     return SynthLookAndFeel.getComponentState(paramJComponent);
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
/* 163 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 165 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 166 */     paintBackground(synthContext, paramGraphics, paramJComponent);
/* 167 */     paint(synthContext, paramGraphics);
/* 168 */     synthContext.dispose();
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
/* 179 */     paint(paramGraphics, getComponent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintBackground(Graphics paramGraphics) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void paintBackground(SynthContext paramSynthContext, Graphics paramGraphics, JComponent paramJComponent) {
/* 191 */     paramSynthContext.getPainter().paintEditorPaneBackground(paramSynthContext, paramGraphics, 0, 0, paramJComponent
/* 192 */         .getWidth(), paramJComponent.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 201 */     paramSynthContext.getPainter().paintEditorPaneBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthEditorPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */