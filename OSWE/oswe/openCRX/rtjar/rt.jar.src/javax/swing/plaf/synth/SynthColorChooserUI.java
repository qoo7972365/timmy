/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.colorchooser.AbstractColorChooserPanel;
/*     */ import javax.swing.colorchooser.ColorChooserComponentFactory;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicColorChooserUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthColorChooserUI
/*     */   extends BasicColorChooserUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  57 */     return new SynthColorChooserUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractColorChooserPanel[] createDefaultChoosers() {
/*  65 */     SynthContext synthContext = getContext(this.chooser, 1);
/*     */     
/*  67 */     AbstractColorChooserPanel[] arrayOfAbstractColorChooserPanel = (AbstractColorChooserPanel[])synthContext.getStyle().get(synthContext, "ColorChooser.panels");
/*  68 */     synthContext.dispose();
/*     */     
/*  70 */     if (arrayOfAbstractColorChooserPanel == null) {
/*  71 */       arrayOfAbstractColorChooserPanel = ColorChooserComponentFactory.getDefaultChooserPanels();
/*     */     }
/*  73 */     return arrayOfAbstractColorChooserPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  81 */     super.installDefaults();
/*  82 */     updateStyle(this.chooser);
/*     */   }
/*     */   
/*     */   private void updateStyle(JComponent paramJComponent) {
/*  86 */     SynthContext synthContext = getContext(paramJComponent, 1);
/*  87 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*  88 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/*  96 */     SynthContext synthContext = getContext(this.chooser, 1);
/*     */     
/*  98 */     this.style.uninstallDefaults(synthContext);
/*  99 */     synthContext.dispose();
/* 100 */     this.style = null;
/* 101 */     super.uninstallDefaults();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/* 109 */     super.installListeners();
/* 110 */     this.chooser.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 118 */     this.chooser.removePropertyChangeListener(this);
/* 119 */     super.uninstallListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 127 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 131 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/* 135 */     return SynthLookAndFeel.getComponentState(paramJComponent);
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
/* 152 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 154 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 155 */     synthContext.getPainter().paintColorChooserBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 156 */         .getWidth(), paramJComponent.getHeight());
/* 157 */     paint(synthContext, paramGraphics);
/* 158 */     synthContext.dispose();
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
/* 172 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 174 */     paint(synthContext, paramGraphics);
/* 175 */     synthContext.dispose();
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
/* 195 */     paramSynthContext.getPainter().paintColorChooserBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 203 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent))
/* 204 */       updateStyle((JColorChooser)paramPropertyChangeEvent.getSource()); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthColorChooserUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */