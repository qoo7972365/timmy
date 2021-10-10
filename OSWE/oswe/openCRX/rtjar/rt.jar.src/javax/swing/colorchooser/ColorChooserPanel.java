/*     */ package javax.swing.colorchooser;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import javax.swing.JLabel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ColorChooserPanel
/*     */   extends AbstractColorChooserPanel
/*     */   implements PropertyChangeListener
/*     */ {
/*     */   private static final int MASK = -16777216;
/*     */   private final ColorModel model;
/*     */   private final ColorPanel panel;
/*     */   private final DiagramComponent slider;
/*     */   private final DiagramComponent diagram;
/*     */   private final JFormattedTextField text;
/*     */   private final JLabel label;
/*     */   
/*     */   ColorChooserPanel(ColorModel paramColorModel) {
/*  52 */     this.model = paramColorModel;
/*  53 */     this.panel = new ColorPanel(this.model);
/*  54 */     this.slider = new DiagramComponent(this.panel, false);
/*  55 */     this.diagram = new DiagramComponent(this.panel, true);
/*  56 */     this.text = new JFormattedTextField();
/*  57 */     this.label = new JLabel(null, null, 4);
/*  58 */     ValueFormatter.init(6, true, this.text);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean paramBoolean) {
/*  63 */     super.setEnabled(paramBoolean);
/*  64 */     setEnabled(this, paramBoolean);
/*     */   }
/*     */   
/*     */   private static void setEnabled(Container paramContainer, boolean paramBoolean) {
/*  68 */     for (Component component : paramContainer.getComponents()) {
/*  69 */       component.setEnabled(paramBoolean);
/*  70 */       if (component instanceof Container) {
/*  71 */         setEnabled((Container)component, paramBoolean);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateChooser() {
/*  78 */     Color color = getColorFromModel();
/*  79 */     if (color != null) {
/*  80 */       this.panel.setColor(color);
/*  81 */       this.text.setValue(Integer.valueOf(color.getRGB()));
/*  82 */       this.slider.repaint();
/*  83 */       this.diagram.repaint();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void buildChooser() {
/*  89 */     if (0 == getComponentCount()) {
/*  90 */       setLayout(new GridBagLayout());
/*     */       
/*  92 */       GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*     */       
/*  94 */       gridBagConstraints.gridx = 3;
/*  95 */       gridBagConstraints.gridwidth = 2;
/*  96 */       gridBagConstraints.weighty = 1.0D;
/*  97 */       gridBagConstraints.anchor = 11;
/*  98 */       gridBagConstraints.fill = 2;
/*  99 */       gridBagConstraints.insets.top = 10;
/* 100 */       gridBagConstraints.insets.right = 10;
/* 101 */       add(this.panel, gridBagConstraints);
/*     */       
/* 103 */       gridBagConstraints.gridwidth = 1;
/* 104 */       gridBagConstraints.weightx = 1.0D;
/* 105 */       gridBagConstraints.weighty = 0.0D;
/* 106 */       gridBagConstraints.anchor = 10;
/* 107 */       gridBagConstraints.insets.right = 5;
/* 108 */       gridBagConstraints.insets.bottom = 10;
/* 109 */       add(this.label, gridBagConstraints);
/*     */       
/* 111 */       gridBagConstraints.gridx = 4;
/* 112 */       gridBagConstraints.weightx = 0.0D;
/* 113 */       gridBagConstraints.insets.right = 10;
/* 114 */       add(this.text, gridBagConstraints);
/*     */       
/* 116 */       gridBagConstraints.gridx = 2;
/* 117 */       gridBagConstraints.gridheight = 2;
/* 118 */       gridBagConstraints.anchor = 11;
/* 119 */       gridBagConstraints.ipadx = (this.text.getPreferredSize()).height;
/* 120 */       gridBagConstraints.ipady = (getPreferredSize()).height;
/* 121 */       add(this.slider, gridBagConstraints);
/*     */       
/* 123 */       gridBagConstraints.gridx = 1;
/* 124 */       gridBagConstraints.insets.left = 10;
/* 125 */       gridBagConstraints.ipadx = gridBagConstraints.ipady;
/* 126 */       add(this.diagram, gridBagConstraints);
/*     */       
/* 128 */       this.label.setLabelFor(this.text);
/* 129 */       this.text.addPropertyChangeListener("value", this);
/* 130 */       this.slider.setBorder(this.text.getBorder());
/* 131 */       this.diagram.setBorder(this.text.getBorder());
/*     */       
/* 133 */       setInheritsPopupMenu(this, true);
/*     */     } 
/* 135 */     String str = this.model.getText(this, "HexCode");
/* 136 */     boolean bool = (str != null) ? true : false;
/* 137 */     this.text.setVisible(bool);
/* 138 */     this.text.getAccessibleContext().setAccessibleDescription(str);
/* 139 */     this.label.setVisible(bool);
/* 140 */     if (bool) {
/* 141 */       this.label.setText(str);
/* 142 */       int i = this.model.getInteger(this, "HexCodeMnemonic");
/* 143 */       if (i > 0) {
/* 144 */         this.label.setDisplayedMnemonic(i);
/* 145 */         i = this.model.getInteger(this, "HexCodeMnemonicIndex");
/* 146 */         if (i >= 0) {
/* 147 */           this.label.setDisplayedMnemonicIndex(i);
/*     */         }
/*     */       } 
/*     */     } 
/* 151 */     this.panel.buildPanel();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/* 156 */     return this.model.getText(this, "Name");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMnemonic() {
/* 161 */     return this.model.getInteger(this, "Mnemonic");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDisplayedMnemonicIndex() {
/* 166 */     return this.model.getInteger(this, "DisplayedMnemonicIndex");
/*     */   }
/*     */ 
/*     */   
/*     */   public Icon getSmallDisplayIcon() {
/* 171 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Icon getLargeDisplayIcon() {
/* 176 */     return null;
/*     */   }
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 180 */     ColorSelectionModel colorSelectionModel = getColorSelectionModel();
/* 181 */     if (colorSelectionModel != null) {
/* 182 */       Object object = paramPropertyChangeEvent.getNewValue();
/* 183 */       if (object instanceof Integer) {
/* 184 */         int i = 0xFF000000 & colorSelectionModel.getSelectedColor().getRGB() | ((Integer)object).intValue();
/* 185 */         colorSelectionModel.setSelectedColor(new Color(i, true));
/*     */       } 
/*     */     } 
/* 188 */     this.text.selectAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setInheritsPopupMenu(JComponent paramJComponent, boolean paramBoolean) {
/* 198 */     paramJComponent.setInheritsPopupMenu(paramBoolean);
/* 199 */     for (Component component : paramJComponent.getComponents()) {
/* 200 */       if (component instanceof JComponent)
/* 201 */         setInheritsPopupMenu((JComponent)component, paramBoolean); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/colorchooser/ColorChooserPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */