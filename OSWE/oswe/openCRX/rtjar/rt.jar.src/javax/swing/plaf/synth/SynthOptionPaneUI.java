/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicOptionPaneUI;
/*     */ import sun.swing.DefaultLookup;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthOptionPaneUI
/*     */   extends BasicOptionPaneUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  55 */     return new SynthOptionPaneUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  63 */     updateStyle(this.optionPane);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/*  71 */     super.installListeners();
/*  72 */     this.optionPane.addPropertyChangeListener(this);
/*     */   }
/*     */   
/*     */   private void updateStyle(JComponent paramJComponent) {
/*  76 */     SynthContext synthContext = getContext(paramJComponent, 1);
/*  77 */     SynthStyle synthStyle = this.style;
/*     */     
/*  79 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*  80 */     if (this.style != synthStyle) {
/*  81 */       this.minimumSize = (Dimension)this.style.get(synthContext, "OptionPane.minimumSize");
/*     */       
/*  83 */       if (this.minimumSize == null) {
/*  84 */         this.minimumSize = new Dimension(262, 90);
/*     */       }
/*  86 */       if (synthStyle != null) {
/*  87 */         uninstallKeyboardActions();
/*  88 */         installKeyboardActions();
/*     */       } 
/*     */     } 
/*  91 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/*  99 */     SynthContext synthContext = getContext(this.optionPane, 1);
/*     */     
/* 101 */     this.style.uninstallDefaults(synthContext);
/* 102 */     synthContext.dispose();
/* 103 */     this.style = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 111 */     super.uninstallListeners();
/* 112 */     this.optionPane.removePropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installComponents() {
/* 120 */     this.optionPane.add(createMessageArea());
/*     */     
/* 122 */     Container container = createSeparator();
/* 123 */     if (container != null) {
/* 124 */       this.optionPane.add(container);
/* 125 */       SynthContext synthContext = getContext(this.optionPane, 1);
/* 126 */       this.optionPane.add(Box.createVerticalStrut(synthContext.getStyle()
/* 127 */             .getInt(synthContext, "OptionPane.separatorPadding", 6)));
/* 128 */       synthContext.dispose();
/*     */     } 
/* 130 */     this.optionPane.add(createButtonArea());
/* 131 */     this.optionPane.applyComponentOrientation(this.optionPane.getComponentOrientation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 139 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 143 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/* 147 */     return SynthLookAndFeel.getComponentState(paramJComponent);
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
/* 164 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 166 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 167 */     synthContext.getPainter().paintOptionPaneBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 168 */         .getWidth(), paramJComponent.getHeight());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 184 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 186 */     paint(synthContext, paramGraphics);
/* 187 */     synthContext.dispose();
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
/* 206 */     paramSynthContext.getPainter().paintOptionPaneBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 214 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/* 215 */       updateStyle((JOptionPane)paramPropertyChangeEvent.getSource());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean getSizeButtonsToSameWidth() {
/* 224 */     return DefaultLookup.getBoolean(this.optionPane, this, "OptionPane.sameSizeButtons", true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Container createMessageArea() {
/* 235 */     JPanel jPanel1 = new JPanel();
/* 236 */     jPanel1.setName("OptionPane.messageArea");
/* 237 */     jPanel1.setLayout(new BorderLayout());
/*     */ 
/*     */     
/* 240 */     JPanel jPanel2 = new JPanel(new GridBagLayout());
/* 241 */     JPanel jPanel3 = new JPanel(new BorderLayout());
/*     */     
/* 243 */     jPanel2.setName("OptionPane.body");
/* 244 */     jPanel3.setName("OptionPane.realBody");
/*     */     
/* 246 */     if (getIcon() != null) {
/* 247 */       JPanel jPanel = new JPanel();
/* 248 */       jPanel.setName("OptionPane.separator");
/* 249 */       jPanel.setPreferredSize(new Dimension(15, 1));
/* 250 */       jPanel3.add(jPanel, "Before");
/*     */     } 
/* 252 */     jPanel3.add(jPanel2, "Center");
/*     */     
/* 254 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/* 255 */     gridBagConstraints.gridx = gridBagConstraints.gridy = 0;
/* 256 */     gridBagConstraints.gridwidth = 0;
/* 257 */     gridBagConstraints.gridheight = 1;
/*     */     
/* 259 */     SynthContext synthContext = getContext(this.optionPane, 1);
/* 260 */     gridBagConstraints.anchor = synthContext.getStyle().getInt(synthContext, "OptionPane.messageAnchor", 10);
/*     */     
/* 262 */     synthContext.dispose();
/*     */     
/* 264 */     gridBagConstraints.insets = new Insets(0, 0, 3, 0);
/*     */     
/* 266 */     addMessageComponents(jPanel2, gridBagConstraints, getMessage(), 
/* 267 */         getMaxCharactersPerLineCount(), false);
/* 268 */     jPanel1.add(jPanel3, "Center");
/*     */     
/* 270 */     addIcon(jPanel1);
/* 271 */     return jPanel1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Container createSeparator() {
/* 279 */     JSeparator jSeparator = new JSeparator(0);
/*     */     
/* 281 */     jSeparator.setName("OptionPane.separator");
/* 282 */     return jSeparator;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthOptionPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */