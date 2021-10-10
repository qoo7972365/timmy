/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicRootPaneUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthRootPaneUI
/*     */   extends BasicRootPaneUI
/*     */   implements SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  51 */     return new SynthRootPaneUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults(JRootPane paramJRootPane) {
/*  59 */     updateStyle(paramJRootPane);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults(JRootPane paramJRootPane) {
/*  67 */     SynthContext synthContext = getContext(paramJRootPane, 1);
/*     */     
/*  69 */     this.style.uninstallDefaults(synthContext);
/*  70 */     synthContext.dispose();
/*  71 */     this.style = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/*  79 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/*  83 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/*  87 */     return SynthLookAndFeel.getComponentState(paramJComponent);
/*     */   }
/*     */   
/*     */   private void updateStyle(JComponent paramJComponent) {
/*  91 */     SynthContext synthContext = getContext(paramJComponent, 1);
/*  92 */     SynthStyle synthStyle = this.style;
/*  93 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*  94 */     if (this.style != synthStyle && 
/*  95 */       synthStyle != null) {
/*  96 */       uninstallKeyboardActions((JRootPane)paramJComponent);
/*  97 */       installKeyboardActions((JRootPane)paramJComponent);
/*     */     } 
/*     */     
/* 100 */     synthContext.dispose();
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
/* 117 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 119 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 120 */     synthContext.getPainter().paintRootPaneBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 121 */         .getWidth(), paramJComponent.getHeight());
/* 122 */     paint(synthContext, paramGraphics);
/* 123 */     synthContext.dispose();
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
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 137 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 139 */     paint(synthContext, paramGraphics);
/* 140 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 159 */     paramSynthContext.getPainter().paintRootPaneBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 169 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/* 170 */       updateStyle((JRootPane)paramPropertyChangeEvent.getSource());
/*     */     }
/* 172 */     super.propertyChange(paramPropertyChangeEvent);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthRootPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */