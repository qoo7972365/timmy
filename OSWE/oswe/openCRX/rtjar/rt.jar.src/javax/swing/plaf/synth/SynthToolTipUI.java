/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JToolTip;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicHTML;
/*     */ import javax.swing.plaf.basic.BasicToolTipUI;
/*     */ import javax.swing.text.View;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthToolTipUI
/*     */   extends BasicToolTipUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  57 */     return new SynthToolTipUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults(JComponent paramJComponent) {
/*  65 */     updateStyle(paramJComponent);
/*     */   }
/*     */   
/*     */   private void updateStyle(JComponent paramJComponent) {
/*  69 */     SynthContext synthContext = getContext(paramJComponent, 1);
/*  70 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*  71 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults(JComponent paramJComponent) {
/*  79 */     SynthContext synthContext = getContext(paramJComponent, 1);
/*  80 */     this.style.uninstallDefaults(synthContext);
/*  81 */     synthContext.dispose();
/*  82 */     this.style = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners(JComponent paramJComponent) {
/*  90 */     paramJComponent.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners(JComponent paramJComponent) {
/*  98 */     paramJComponent.removePropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 106 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 110 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/* 114 */     JComponent jComponent = ((JToolTip)paramJComponent).getComponent();
/*     */     
/* 116 */     if (jComponent != null && !jComponent.isEnabled()) {
/* 117 */       return 8;
/*     */     }
/* 119 */     return SynthLookAndFeel.getComponentState(paramJComponent);
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
/* 136 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 138 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 139 */     synthContext.getPainter().paintToolTipBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 140 */         .getWidth(), paramJComponent.getHeight());
/* 141 */     paint(synthContext, paramGraphics);
/* 142 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 151 */     paramSynthContext.getPainter().paintToolTipBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
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
/* 165 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
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
/* 179 */     JToolTip jToolTip = (JToolTip)paramSynthContext.getComponent();
/*     */     
/* 181 */     Insets insets = jToolTip.getInsets();
/* 182 */     View view = (View)jToolTip.getClientProperty("html");
/* 183 */     if (view != null) {
/*     */ 
/*     */       
/* 186 */       Rectangle rectangle = new Rectangle(insets.left, insets.top, jToolTip.getWidth() - insets.left + insets.right, jToolTip.getHeight() - insets.top + insets.bottom);
/* 187 */       view.paint(paramGraphics, rectangle);
/*     */     } else {
/* 189 */       paramGraphics.setColor(paramSynthContext.getStyle().getColor(paramSynthContext, ColorType.TEXT_FOREGROUND));
/*     */       
/* 191 */       paramGraphics.setFont(this.style.getFont(paramSynthContext));
/* 192 */       paramSynthContext.getStyle().getGraphicsUtils(paramSynthContext).paintText(paramSynthContext, paramGraphics, jToolTip
/* 193 */           .getTipText(), insets.left, insets.top, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 202 */     SynthContext synthContext = getContext(paramJComponent);
/* 203 */     Insets insets = paramJComponent.getInsets();
/* 204 */     Dimension dimension = new Dimension(insets.left + insets.right, insets.top + insets.bottom);
/*     */     
/* 206 */     String str = ((JToolTip)paramJComponent).getTipText();
/*     */     
/* 208 */     if (str != null) {
/* 209 */       View view = (paramJComponent != null) ? (View)paramJComponent.getClientProperty("html") : null;
/* 210 */       if (view != null) {
/* 211 */         dimension.width += (int)view.getPreferredSpan(0);
/* 212 */         dimension.height += (int)view.getPreferredSpan(1);
/*     */       } else {
/* 214 */         Font font = synthContext.getStyle().getFont(synthContext);
/* 215 */         FontMetrics fontMetrics = paramJComponent.getFontMetrics(font);
/* 216 */         dimension.width += synthContext.getStyle().getGraphicsUtils(synthContext)
/* 217 */           .computeStringWidth(synthContext, font, fontMetrics, str);
/* 218 */         dimension.height += fontMetrics.getHeight();
/*     */       } 
/*     */     } 
/* 221 */     synthContext.dispose();
/* 222 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 230 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/* 231 */       updateStyle((JToolTip)paramPropertyChangeEvent.getSource());
/*     */     }
/* 233 */     String str = paramPropertyChangeEvent.getPropertyName();
/* 234 */     if (str.equals("tiptext") || "font".equals(str) || "foreground"
/* 235 */       .equals(str)) {
/*     */ 
/*     */ 
/*     */       
/* 239 */       JToolTip jToolTip = (JToolTip)paramPropertyChangeEvent.getSource();
/* 240 */       String str1 = jToolTip.getTipText();
/* 241 */       BasicHTML.updateRenderer(jToolTip, str1);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthToolTipUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */