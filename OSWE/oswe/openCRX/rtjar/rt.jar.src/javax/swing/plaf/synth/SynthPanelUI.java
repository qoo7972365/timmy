/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicPanelUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthPanelUI
/*     */   extends BasicPanelUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  52 */     return new SynthPanelUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  60 */     JPanel jPanel = (JPanel)paramJComponent;
/*     */     
/*  62 */     super.installUI(paramJComponent);
/*  63 */     installListeners(jPanel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/*  71 */     JPanel jPanel = (JPanel)paramJComponent;
/*     */     
/*  73 */     uninstallListeners(jPanel);
/*  74 */     super.uninstallUI(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners(JPanel paramJPanel) {
/*  83 */     paramJPanel.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners(JPanel paramJPanel) {
/*  92 */     paramJPanel.removePropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults(JPanel paramJPanel) {
/* 100 */     updateStyle(paramJPanel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults(JPanel paramJPanel) {
/* 108 */     SynthContext synthContext = getContext(paramJPanel, 1);
/*     */     
/* 110 */     this.style.uninstallDefaults(synthContext);
/* 111 */     synthContext.dispose();
/* 112 */     this.style = null;
/*     */   }
/*     */   
/*     */   private void updateStyle(JPanel paramJPanel) {
/* 116 */     SynthContext synthContext = getContext(paramJPanel, 1);
/* 117 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/* 118 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 126 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 130 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/* 134 */     return SynthLookAndFeel.getComponentState(paramJComponent);
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
/* 151 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 153 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 154 */     synthContext.getPainter().paintPanelBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 155 */         .getWidth(), paramJComponent.getHeight());
/* 156 */     paint(synthContext, paramGraphics);
/* 157 */     synthContext.dispose();
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
/* 171 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 173 */     paint(synthContext, paramGraphics);
/* 174 */     synthContext.dispose();
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
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 194 */     paramSynthContext.getPainter().paintPanelBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 202 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent))
/* 203 */       updateStyle((JPanel)paramPropertyChangeEvent.getSource()); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthPanelUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */