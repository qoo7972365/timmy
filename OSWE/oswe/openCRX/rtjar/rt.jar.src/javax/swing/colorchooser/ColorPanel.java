/*     */ package javax.swing.colorchooser;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.ContainerOrderFocusTraversalPolicy;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ColorPanel
/*     */   extends JPanel
/*     */   implements ActionListener
/*     */ {
/*  44 */   private final SlidingSpinner[] spinners = new SlidingSpinner[5];
/*  45 */   private final float[] values = new float[this.spinners.length];
/*     */   
/*     */   private final ColorModel model;
/*     */   private Color color;
/*  49 */   private int x = 1;
/*  50 */   private int y = 2;
/*     */   private int z;
/*     */   
/*     */   ColorPanel(ColorModel paramColorModel) {
/*  54 */     super(new GridBagLayout());
/*     */     
/*  56 */     GridBagConstraints gridBagConstraints = new GridBagConstraints();
/*  57 */     gridBagConstraints.fill = 2;
/*     */     
/*  59 */     gridBagConstraints.gridx = 1;
/*  60 */     ButtonGroup buttonGroup = new ButtonGroup();
/*  61 */     EmptyBorder emptyBorder = null;
/*  62 */     for (byte b = 0; b < this.spinners.length; b++) {
/*  63 */       if (b < 3) {
/*  64 */         JRadioButton jRadioButton = new JRadioButton();
/*  65 */         if (b == 0) {
/*  66 */           Insets insets = jRadioButton.getInsets();
/*  67 */           insets.left = (jRadioButton.getPreferredSize()).width;
/*  68 */           emptyBorder = new EmptyBorder(insets);
/*  69 */           jRadioButton.setSelected(true);
/*  70 */           gridBagConstraints.insets.top = 5;
/*     */         } 
/*  72 */         add(jRadioButton, gridBagConstraints);
/*  73 */         buttonGroup.add(jRadioButton);
/*  74 */         jRadioButton.setActionCommand(Integer.toString(b));
/*  75 */         jRadioButton.addActionListener(this);
/*  76 */         this.spinners[b] = new SlidingSpinner(this, jRadioButton);
/*     */       } else {
/*     */         
/*  79 */         JLabel jLabel = new JLabel();
/*  80 */         add(jLabel, gridBagConstraints);
/*  81 */         jLabel.setBorder(emptyBorder);
/*  82 */         jLabel.setFocusable(false);
/*  83 */         this.spinners[b] = new SlidingSpinner(this, jLabel);
/*     */       } 
/*     */     } 
/*  86 */     gridBagConstraints.gridx = 2;
/*  87 */     gridBagConstraints.weightx = 1.0D;
/*  88 */     gridBagConstraints.insets.top = 0;
/*  89 */     gridBagConstraints.insets.left = 5;
/*  90 */     for (SlidingSpinner slidingSpinner : this.spinners) {
/*  91 */       add(slidingSpinner.getSlider(), gridBagConstraints);
/*  92 */       gridBagConstraints.insets.top = 5;
/*     */     } 
/*  94 */     gridBagConstraints.gridx = 3;
/*  95 */     gridBagConstraints.weightx = 0.0D;
/*  96 */     gridBagConstraints.insets.top = 0;
/*  97 */     for (SlidingSpinner slidingSpinner : this.spinners) {
/*  98 */       add(slidingSpinner.getSpinner(), gridBagConstraints);
/*  99 */       gridBagConstraints.insets.top = 5;
/*     */     } 
/* 101 */     setFocusTraversalPolicy(new ContainerOrderFocusTraversalPolicy());
/* 102 */     setFocusTraversalPolicyProvider(true);
/* 103 */     setFocusable(false);
/*     */     
/* 105 */     this.model = paramColorModel;
/*     */   }
/*     */   
/*     */   public void actionPerformed(ActionEvent paramActionEvent) {
/*     */     try {
/* 110 */       this.z = Integer.parseInt(paramActionEvent.getActionCommand());
/* 111 */       this.y = (this.z != 2) ? 2 : 1;
/* 112 */       this.x = (this.z != 0) ? 0 : 1;
/* 113 */       getParent().repaint();
/*     */     }
/* 115 */     catch (NumberFormatException numberFormatException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   void buildPanel() {
/* 120 */     int i = this.model.getCount();
/* 121 */     this.spinners[4].setVisible((i > 4));
/* 122 */     for (byte b = 0; b < i; b++) {
/* 123 */       String str = this.model.getLabel(this, b);
/* 124 */       JComponent jComponent = this.spinners[b].getLabel();
/* 125 */       if (jComponent instanceof JRadioButton) {
/* 126 */         JRadioButton jRadioButton = (JRadioButton)jComponent;
/* 127 */         jRadioButton.setText(str);
/* 128 */         jRadioButton.getAccessibleContext().setAccessibleDescription(str);
/*     */       }
/* 130 */       else if (jComponent instanceof JLabel) {
/* 131 */         JLabel jLabel = (JLabel)jComponent;
/* 132 */         jLabel.setText(str);
/*     */       } 
/* 134 */       this.spinners[b].setRange(this.model.getMinimum(b), this.model.getMaximum(b));
/* 135 */       this.spinners[b].setValue(this.values[b]);
/* 136 */       this.spinners[b].getSlider().getAccessibleContext().setAccessibleName(str);
/* 137 */       this.spinners[b].getSpinner().getAccessibleContext().setAccessibleName(str);
/* 138 */       JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor)this.spinners[b].getSpinner().getEditor();
/* 139 */       defaultEditor.getTextField().getAccessibleContext().setAccessibleName(str);
/* 140 */       this.spinners[b].getSlider().getAccessibleContext().setAccessibleDescription(str);
/* 141 */       this.spinners[b].getSpinner().getAccessibleContext().setAccessibleDescription(str);
/* 142 */       defaultEditor.getTextField().getAccessibleContext().setAccessibleDescription(str);
/*     */     } 
/*     */   }
/*     */   
/*     */   void colorChanged() {
/* 147 */     this.color = new Color(getColor(0), true);
/* 148 */     Container container = getParent();
/* 149 */     if (container instanceof ColorChooserPanel) {
/* 150 */       ColorChooserPanel colorChooserPanel = (ColorChooserPanel)container;
/* 151 */       colorChooserPanel.setSelectedColor(this.color);
/* 152 */       colorChooserPanel.repaint();
/*     */     } 
/*     */   }
/*     */   
/*     */   float getValueX() {
/* 157 */     return this.spinners[this.x].getValue();
/*     */   }
/*     */   
/*     */   float getValueY() {
/* 161 */     return 1.0F - this.spinners[this.y].getValue();
/*     */   }
/*     */   
/*     */   float getValueZ() {
/* 165 */     return 1.0F - this.spinners[this.z].getValue();
/*     */   }
/*     */   
/*     */   void setValue(float paramFloat) {
/* 169 */     this.spinners[this.z].setValue(1.0F - paramFloat);
/* 170 */     colorChanged();
/*     */   }
/*     */   
/*     */   void setValue(float paramFloat1, float paramFloat2) {
/* 174 */     this.spinners[this.x].setValue(paramFloat1);
/* 175 */     this.spinners[this.y].setValue(1.0F - paramFloat2);
/* 176 */     colorChanged();
/*     */   }
/*     */   
/*     */   int getColor(float paramFloat) {
/* 180 */     setDefaultValue(this.x);
/* 181 */     setDefaultValue(this.y);
/* 182 */     this.values[this.z] = 1.0F - paramFloat;
/* 183 */     return getColor(3);
/*     */   }
/*     */   
/*     */   int getColor(float paramFloat1, float paramFloat2) {
/* 187 */     this.values[this.x] = paramFloat1;
/* 188 */     this.values[this.y] = 1.0F - paramFloat2;
/* 189 */     setValue(this.z);
/* 190 */     return getColor(3);
/*     */   }
/*     */   
/*     */   void setColor(Color paramColor) {
/* 194 */     if (!paramColor.equals(this.color)) {
/* 195 */       this.color = paramColor;
/* 196 */       this.model.setColor(paramColor.getRGB(), this.values);
/* 197 */       for (byte b = 0; b < this.model.getCount(); b++) {
/* 198 */         this.spinners[b].setValue(this.values[b]);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getColor(int paramInt) {
/* 204 */     while (paramInt < this.model.getCount()) {
/* 205 */       setValue(paramInt++);
/*     */     }
/* 207 */     return this.model.getColor(this.values);
/*     */   }
/*     */   
/*     */   private void setValue(int paramInt) {
/* 211 */     this.values[paramInt] = this.spinners[paramInt].getValue();
/*     */   }
/*     */   
/*     */   private void setDefaultValue(int paramInt) {
/* 215 */     float f = this.model.getDefault(paramInt);
/* 216 */     this.values[paramInt] = (f < 0.0F) ? this.spinners[paramInt]
/* 217 */       .getValue() : f;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/colorchooser/ColorPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */