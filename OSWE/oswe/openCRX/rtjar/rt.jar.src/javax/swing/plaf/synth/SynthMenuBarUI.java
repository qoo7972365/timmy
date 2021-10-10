/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicMenuBarUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthMenuBarUI
/*     */   extends BasicMenuBarUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  52 */     return new SynthMenuBarUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  60 */     if (this.menuBar.getLayout() == null || this.menuBar
/*  61 */       .getLayout() instanceof javax.swing.plaf.UIResource) {
/*  62 */       this.menuBar.setLayout(new SynthMenuLayout(this.menuBar, 2));
/*     */     }
/*  64 */     updateStyle(this.menuBar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/*  72 */     super.installListeners();
/*  73 */     this.menuBar.addPropertyChangeListener(this);
/*     */   }
/*     */   
/*     */   private void updateStyle(JMenuBar paramJMenuBar) {
/*  77 */     SynthContext synthContext = getContext(paramJMenuBar, 1);
/*  78 */     SynthStyle synthStyle = this.style;
/*  79 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*  80 */     if (this.style != synthStyle && 
/*  81 */       synthStyle != null) {
/*  82 */       uninstallKeyboardActions();
/*  83 */       installKeyboardActions();
/*     */     } 
/*     */     
/*  86 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/*  94 */     SynthContext synthContext = getContext(this.menuBar, 1);
/*     */     
/*  96 */     this.style.uninstallDefaults(synthContext);
/*  97 */     synthContext.dispose();
/*  98 */     this.style = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 106 */     super.uninstallListeners();
/* 107 */     this.menuBar.removePropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 115 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 119 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/* 123 */     return SynthLookAndFeel.getComponentState(paramJComponent);
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
/* 140 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 142 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 143 */     synthContext.getPainter().paintMenuBarBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 144 */         .getWidth(), paramJComponent.getHeight());
/* 145 */     paint(synthContext, paramGraphics);
/* 146 */     synthContext.dispose();
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
/* 160 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 162 */     paint(synthContext, paramGraphics);
/* 163 */     synthContext.dispose();
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
/* 182 */     paramSynthContext.getPainter().paintMenuBarBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 190 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent))
/* 191 */       updateStyle((JMenuBar)paramPropertyChangeEvent.getSource()); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthMenuBarUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */