/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicHTML;
/*     */ import javax.swing.plaf.basic.BasicLabelUI;
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
/*     */ public class SynthLabelUI
/*     */   extends BasicLabelUI
/*     */   implements SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  56 */     return new SynthLabelUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults(JLabel paramJLabel) {
/*  64 */     updateStyle(paramJLabel);
/*     */   }
/*     */   
/*     */   void updateStyle(JLabel paramJLabel) {
/*  68 */     SynthContext synthContext = getContext(paramJLabel, 1);
/*  69 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*  70 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults(JLabel paramJLabel) {
/*  78 */     SynthContext synthContext = getContext(paramJLabel, 1);
/*     */     
/*  80 */     this.style.uninstallDefaults(synthContext);
/*  81 */     synthContext.dispose();
/*  82 */     this.style = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/*  90 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/*  94 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/*  98 */     int i = SynthLookAndFeel.getComponentState(paramJComponent);
/*  99 */     if (SynthLookAndFeel.getSelectedUI() == this && i == 1)
/*     */     {
/* 101 */       i = SynthLookAndFeel.getSelectedUIState() | 0x1;
/*     */     }
/* 103 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/*     */     int i;
/* 111 */     if (paramJComponent == null) {
/* 112 */       throw new NullPointerException("Component must be non-null");
/*     */     }
/* 114 */     if (paramInt1 < 0 || paramInt2 < 0) {
/* 115 */       throw new IllegalArgumentException("Width and height must be >= 0");
/*     */     }
/*     */     
/* 118 */     JLabel jLabel = (JLabel)paramJComponent;
/* 119 */     String str = jLabel.getText();
/* 120 */     if (str == null || "".equals(str)) {
/* 121 */       return -1;
/*     */     }
/* 123 */     Insets insets = jLabel.getInsets();
/* 124 */     Rectangle rectangle1 = new Rectangle();
/* 125 */     Rectangle rectangle2 = new Rectangle();
/* 126 */     Rectangle rectangle3 = new Rectangle();
/* 127 */     rectangle1.x = insets.left;
/* 128 */     rectangle1.y = insets.top;
/* 129 */     rectangle1.width = paramInt1 - insets.right + rectangle1.x;
/* 130 */     rectangle1.height = paramInt2 - insets.bottom + rectangle1.y;
/*     */ 
/*     */     
/* 133 */     SynthContext synthContext = getContext(jLabel);
/* 134 */     FontMetrics fontMetrics = synthContext.getComponent().getFontMetrics(synthContext
/* 135 */         .getStyle().getFont(synthContext));
/* 136 */     synthContext.getStyle().getGraphicsUtils(synthContext).layoutText(synthContext, fontMetrics, jLabel
/* 137 */         .getText(), jLabel.getIcon(), jLabel
/* 138 */         .getHorizontalAlignment(), jLabel.getVerticalAlignment(), jLabel
/* 139 */         .getHorizontalTextPosition(), jLabel.getVerticalTextPosition(), rectangle1, rectangle3, rectangle2, jLabel
/* 140 */         .getIconTextGap());
/* 141 */     View view = (View)jLabel.getClientProperty("html");
/*     */     
/* 143 */     if (view != null) {
/* 144 */       i = BasicHTML.getHTMLBaseline(view, rectangle2.width, rectangle2.height);
/*     */       
/* 146 */       if (i >= 0) {
/* 147 */         i += rectangle2.y;
/*     */       }
/*     */     } else {
/*     */       
/* 151 */       i = rectangle2.y + fontMetrics.getAscent();
/*     */     } 
/* 153 */     synthContext.dispose();
/* 154 */     return i;
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
/* 171 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 173 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 174 */     synthContext.getPainter().paintLabelBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 175 */         .getWidth(), paramJComponent.getHeight());
/* 176 */     paint(synthContext, paramGraphics);
/* 177 */     synthContext.dispose();
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
/* 191 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 193 */     paint(synthContext, paramGraphics);
/* 194 */     synthContext.dispose();
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
/* 205 */     JLabel jLabel = (JLabel)paramSynthContext.getComponent();
/*     */     
/* 207 */     Icon icon = jLabel.isEnabled() ? jLabel.getIcon() : jLabel.getDisabledIcon();
/*     */     
/* 209 */     paramGraphics.setColor(paramSynthContext.getStyle().getColor(paramSynthContext, ColorType.TEXT_FOREGROUND));
/*     */     
/* 211 */     paramGraphics.setFont(this.style.getFont(paramSynthContext));
/* 212 */     paramSynthContext.getStyle().getGraphicsUtils(paramSynthContext).paintText(paramSynthContext, paramGraphics, jLabel
/* 213 */         .getText(), icon, jLabel
/* 214 */         .getHorizontalAlignment(), jLabel.getVerticalAlignment(), jLabel
/* 215 */         .getHorizontalTextPosition(), jLabel.getVerticalTextPosition(), jLabel
/* 216 */         .getIconTextGap(), jLabel.getDisplayedMnemonicIndex(), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 225 */     paramSynthContext.getPainter().paintLabelBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 233 */     JLabel jLabel = (JLabel)paramJComponent;
/*     */     
/* 235 */     Icon icon = jLabel.isEnabled() ? jLabel.getIcon() : jLabel.getDisabledIcon();
/* 236 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 238 */     Dimension dimension = synthContext.getStyle().getGraphicsUtils(synthContext).getPreferredSize(synthContext, synthContext
/* 239 */         .getStyle().getFont(synthContext), jLabel.getText(), icon, jLabel
/* 240 */         .getHorizontalAlignment(), jLabel
/* 241 */         .getVerticalAlignment(), jLabel.getHorizontalTextPosition(), jLabel
/* 242 */         .getVerticalTextPosition(), jLabel.getIconTextGap(), jLabel
/* 243 */         .getDisplayedMnemonicIndex());
/*     */     
/* 245 */     synthContext.dispose();
/* 246 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 254 */     JLabel jLabel = (JLabel)paramJComponent;
/*     */     
/* 256 */     Icon icon = jLabel.isEnabled() ? jLabel.getIcon() : jLabel.getDisabledIcon();
/* 257 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 259 */     Dimension dimension = synthContext.getStyle().getGraphicsUtils(synthContext).getMinimumSize(synthContext, synthContext
/* 260 */         .getStyle().getFont(synthContext), jLabel.getText(), icon, jLabel
/* 261 */         .getHorizontalAlignment(), jLabel
/* 262 */         .getVerticalAlignment(), jLabel.getHorizontalTextPosition(), jLabel
/* 263 */         .getVerticalTextPosition(), jLabel.getIconTextGap(), jLabel
/* 264 */         .getDisplayedMnemonicIndex());
/*     */     
/* 266 */     synthContext.dispose();
/* 267 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 275 */     JLabel jLabel = (JLabel)paramJComponent;
/*     */     
/* 277 */     Icon icon = jLabel.isEnabled() ? jLabel.getIcon() : jLabel.getDisabledIcon();
/* 278 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 280 */     Dimension dimension = synthContext.getStyle().getGraphicsUtils(synthContext).getMaximumSize(synthContext, synthContext
/* 281 */         .getStyle().getFont(synthContext), jLabel.getText(), icon, jLabel
/* 282 */         .getHorizontalAlignment(), jLabel
/* 283 */         .getVerticalAlignment(), jLabel.getHorizontalTextPosition(), jLabel
/* 284 */         .getVerticalTextPosition(), jLabel.getIconTextGap(), jLabel
/* 285 */         .getDisplayedMnemonicIndex());
/*     */     
/* 287 */     synthContext.dispose();
/* 288 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 296 */     super.propertyChange(paramPropertyChangeEvent);
/* 297 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent))
/* 298 */       updateStyle((JLabel)paramPropertyChangeEvent.getSource()); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthLabelUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */