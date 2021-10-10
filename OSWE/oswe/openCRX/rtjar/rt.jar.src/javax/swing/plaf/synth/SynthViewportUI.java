/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.ViewportUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthViewportUI
/*     */   extends ViewportUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  51 */     return new SynthViewportUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  59 */     super.installUI(paramJComponent);
/*  60 */     installDefaults(paramJComponent);
/*  61 */     installListeners(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/*  69 */     super.uninstallUI(paramJComponent);
/*  70 */     uninstallListeners(paramJComponent);
/*  71 */     uninstallDefaults(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults(JComponent paramJComponent) {
/*  80 */     updateStyle(paramJComponent);
/*     */   }
/*     */   
/*     */   private void updateStyle(JComponent paramJComponent) {
/*  84 */     SynthContext synthContext = getContext(paramJComponent, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     SynthStyle synthStyle1 = SynthLookAndFeel.getStyle(synthContext.getComponent(), synthContext
/*  91 */         .getRegion());
/*  92 */     SynthStyle synthStyle2 = synthContext.getStyle();
/*     */     
/*  94 */     if (synthStyle1 != synthStyle2) {
/*  95 */       if (synthStyle2 != null) {
/*  96 */         synthStyle2.uninstallDefaults(synthContext);
/*     */       }
/*  98 */       synthContext.setStyle(synthStyle1);
/*  99 */       synthStyle1.installDefaults(synthContext);
/*     */     } 
/* 101 */     this.style = synthStyle1;
/* 102 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners(JComponent paramJComponent) {
/* 111 */     paramJComponent.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners(JComponent paramJComponent) {
/* 120 */     paramJComponent.removePropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults(JComponent paramJComponent) {
/* 129 */     SynthContext synthContext = getContext(paramJComponent, 1);
/* 130 */     this.style.uninstallDefaults(synthContext);
/* 131 */     synthContext.dispose();
/* 132 */     this.style = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 140 */     return getContext(paramJComponent, SynthLookAndFeel.getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 144 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private Region getRegion(JComponent paramJComponent) {
/* 148 */     return SynthLookAndFeel.getRegion(paramJComponent);
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
/* 165 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 167 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 168 */     synthContext.getPainter().paintViewportBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 169 */         .getWidth(), paramJComponent.getHeight());
/* 170 */     paint(synthContext, paramGraphics);
/* 171 */     synthContext.dispose();
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
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 202 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 204 */     paint(synthContext, paramGraphics);
/* 205 */     synthContext.dispose();
/*     */   }
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
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 223 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent))
/* 224 */       updateStyle((JComponent)paramPropertyChangeEvent.getSource()); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthViewportUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */