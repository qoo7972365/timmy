/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.DimensionUIResource;
/*     */ import javax.swing.plaf.SeparatorUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthSeparatorUI
/*     */   extends SeparatorUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  57 */     return new SynthSeparatorUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  65 */     installDefaults((JSeparator)paramJComponent);
/*  66 */     installListeners((JSeparator)paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/*  74 */     uninstallListeners((JSeparator)paramJComponent);
/*  75 */     uninstallDefaults((JSeparator)paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installDefaults(JSeparator paramJSeparator) {
/*  83 */     updateStyle(paramJSeparator);
/*     */   }
/*     */   
/*     */   private void updateStyle(JSeparator paramJSeparator) {
/*  87 */     SynthContext synthContext = getContext(paramJSeparator, 1);
/*  88 */     SynthStyle synthStyle = this.style;
/*     */     
/*  90 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*     */     
/*  92 */     if (this.style != synthStyle && 
/*  93 */       paramJSeparator instanceof JToolBar.Separator) {
/*  94 */       Dimension dimension = ((JToolBar.Separator)paramJSeparator).getSeparatorSize();
/*  95 */       if (dimension == null || dimension instanceof javax.swing.plaf.UIResource) {
/*  96 */         dimension = (DimensionUIResource)this.style.get(synthContext, "ToolBar.separatorSize");
/*     */         
/*  98 */         if (dimension == null) {
/*  99 */           dimension = new DimensionUIResource(10, 10);
/*     */         }
/* 101 */         ((JToolBar.Separator)paramJSeparator).setSeparatorSize(dimension);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 106 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallDefaults(JSeparator paramJSeparator) {
/* 114 */     SynthContext synthContext = getContext(paramJSeparator, 1);
/*     */     
/* 116 */     this.style.uninstallDefaults(synthContext);
/* 117 */     synthContext.dispose();
/* 118 */     this.style = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installListeners(JSeparator paramJSeparator) {
/* 126 */     paramJSeparator.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallListeners(JSeparator paramJSeparator) {
/* 134 */     paramJSeparator.removePropertyChangeListener(this);
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
/* 153 */     JSeparator jSeparator = (JSeparator)synthContext.getComponent();
/* 154 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 155 */     synthContext.getPainter().paintSeparatorBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 156 */         .getWidth(), paramJComponent.getHeight(), jSeparator
/* 157 */         .getOrientation());
/* 158 */     paint(synthContext, paramGraphics);
/* 159 */     synthContext.dispose();
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
/* 173 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 175 */     paint(synthContext, paramGraphics);
/* 176 */     synthContext.dispose();
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
/* 187 */     JSeparator jSeparator = (JSeparator)paramSynthContext.getComponent();
/* 188 */     paramSynthContext.getPainter().paintSeparatorForeground(paramSynthContext, paramGraphics, 0, 0, jSeparator
/* 189 */         .getWidth(), jSeparator.getHeight(), jSeparator
/* 190 */         .getOrientation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 199 */     JSeparator jSeparator = (JSeparator)paramSynthContext.getComponent();
/* 200 */     paramSynthContext.getPainter().paintSeparatorBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, jSeparator
/* 201 */         .getOrientation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/*     */     Dimension dimension;
/* 209 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 211 */     int i = this.style.getInt(synthContext, "Separator.thickness", 2);
/* 212 */     Insets insets = paramJComponent.getInsets();
/*     */ 
/*     */     
/* 215 */     if (((JSeparator)paramJComponent).getOrientation() == 1) {
/* 216 */       dimension = new Dimension(insets.left + insets.right + i, insets.top + insets.bottom);
/*     */     } else {
/*     */       
/* 219 */       dimension = new Dimension(insets.left + insets.right, insets.top + insets.bottom + i);
/*     */     } 
/*     */     
/* 222 */     synthContext.dispose();
/* 223 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 231 */     return getPreferredSize(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 239 */     return new Dimension(32767, 32767);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 247 */     return getContext(paramJComponent, SynthLookAndFeel.getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 251 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 255 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent))
/* 256 */       updateStyle((JSeparator)paramPropertyChangeEvent.getSource()); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthSeparatorUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */