/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.LayoutManager;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicPopupMenuUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthPopupMenuUI
/*     */   extends BasicPopupMenuUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  55 */     return new SynthPopupMenuUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installDefaults() {
/*  63 */     if (this.popupMenu.getLayout() == null || this.popupMenu
/*  64 */       .getLayout() instanceof javax.swing.plaf.UIResource) {
/*  65 */       this.popupMenu.setLayout(new SynthMenuLayout(this.popupMenu, 1));
/*     */     }
/*  67 */     updateStyle(this.popupMenu);
/*     */   }
/*     */   
/*     */   private void updateStyle(JComponent paramJComponent) {
/*  71 */     SynthContext synthContext = getContext(paramJComponent, 1);
/*  72 */     SynthStyle synthStyle = this.style;
/*  73 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*  74 */     if (this.style != synthStyle && 
/*  75 */       synthStyle != null) {
/*  76 */       uninstallKeyboardActions();
/*  77 */       installKeyboardActions();
/*     */     } 
/*     */     
/*  80 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/*  88 */     super.installListeners();
/*  89 */     this.popupMenu.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/*  97 */     SynthContext synthContext = getContext(this.popupMenu, 1);
/*     */     
/*  99 */     this.style.uninstallDefaults(synthContext);
/* 100 */     synthContext.dispose();
/* 101 */     this.style = null;
/*     */     
/* 103 */     if (this.popupMenu.getLayout() instanceof javax.swing.plaf.UIResource) {
/* 104 */       this.popupMenu.setLayout((LayoutManager)null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 113 */     super.uninstallListeners();
/* 114 */     this.popupMenu.removePropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 122 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 126 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/* 130 */     return SynthLookAndFeel.getComponentState(paramJComponent);
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
/* 147 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 149 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 150 */     synthContext.getPainter().paintPopupMenuBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 151 */         .getWidth(), paramJComponent.getHeight());
/* 152 */     paint(synthContext, paramGraphics);
/* 153 */     synthContext.dispose();
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
/* 167 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 169 */     paint(synthContext, paramGraphics);
/* 170 */     synthContext.dispose();
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
/* 189 */     paramSynthContext.getPainter().paintPopupMenuBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 197 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent))
/* 198 */       updateStyle(this.popupMenu); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthPopupMenuUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */