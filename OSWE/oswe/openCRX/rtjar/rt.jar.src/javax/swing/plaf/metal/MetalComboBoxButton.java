/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.CellRendererPane;
/*     */ import javax.swing.DefaultButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.ListCellRenderer;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalComboBoxButton
/*     */   extends JButton
/*     */ {
/*     */   protected JComboBox comboBox;
/*     */   protected JList listBox;
/*     */   protected CellRendererPane rendererPane;
/*     */   protected Icon comboIcon;
/*     */   protected boolean iconOnly = false;
/*     */   
/*     */   public final JComboBox getComboBox() {
/*  58 */     return this.comboBox; } public final void setComboBox(JComboBox paramJComboBox) {
/*  59 */     this.comboBox = paramJComboBox;
/*     */   }
/*  61 */   public final Icon getComboIcon() { return this.comboIcon; } public final void setComboIcon(Icon paramIcon) {
/*  62 */     this.comboIcon = paramIcon;
/*     */   }
/*  64 */   public final boolean isIconOnly() { return this.iconOnly; } public final void setIconOnly(boolean paramBoolean) {
/*  65 */     this.iconOnly = paramBoolean;
/*     */   }
/*     */   MetalComboBoxButton() {
/*  68 */     super("");
/*  69 */     DefaultButtonModel defaultButtonModel = new DefaultButtonModel() {
/*     */         public void setArmed(boolean param1Boolean) {
/*  71 */           super.setArmed(isPressed() ? true : param1Boolean);
/*     */         }
/*     */       };
/*  74 */     setModel(defaultButtonModel);
/*     */   }
/*     */ 
/*     */   
/*     */   public MetalComboBoxButton(JComboBox paramJComboBox, Icon paramIcon, CellRendererPane paramCellRendererPane, JList paramJList) {
/*  79 */     this();
/*  80 */     this.comboBox = paramJComboBox;
/*  81 */     this.comboIcon = paramIcon;
/*  82 */     this.rendererPane = paramCellRendererPane;
/*  83 */     this.listBox = paramJList;
/*  84 */     setEnabled(this.comboBox.isEnabled());
/*     */   }
/*     */ 
/*     */   
/*     */   public MetalComboBoxButton(JComboBox paramJComboBox, Icon paramIcon, boolean paramBoolean, CellRendererPane paramCellRendererPane, JList paramJList) {
/*  89 */     this(paramJComboBox, paramIcon, paramCellRendererPane, paramJList);
/*  90 */     this.iconOnly = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean isFocusTraversable() {
/*  94 */     return false;
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean paramBoolean) {
/*  98 */     super.setEnabled(paramBoolean);
/*     */ 
/*     */     
/* 101 */     if (paramBoolean) {
/* 102 */       setBackground(this.comboBox.getBackground());
/* 103 */       setForeground(this.comboBox.getForeground());
/*     */     } else {
/* 105 */       setBackground(UIManager.getColor("ComboBox.disabledBackground"));
/* 106 */       setForeground(UIManager.getColor("ComboBox.disabledForeground"));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void paintComponent(Graphics paramGraphics) {
/* 111 */     boolean bool = MetalUtils.isLeftToRight(this.comboBox);
/*     */ 
/*     */     
/* 114 */     super.paintComponent(paramGraphics);
/*     */     
/* 116 */     Insets insets = getInsets();
/*     */     
/* 118 */     int i = getWidth() - insets.left + insets.right;
/* 119 */     int j = getHeight() - insets.top + insets.bottom;
/*     */     
/* 121 */     if (j <= 0 || i <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 125 */     int k = insets.left;
/* 126 */     int m = insets.top;
/* 127 */     int n = k + i - 1;
/* 128 */     int i1 = m + j - 1;
/*     */     
/* 130 */     int i2 = 0;
/* 131 */     int i3 = bool ? n : k;
/*     */ 
/*     */     
/* 134 */     if (this.comboIcon != null) {
/* 135 */       i2 = this.comboIcon.getIconWidth();
/* 136 */       int i4 = this.comboIcon.getIconHeight();
/* 137 */       int i5 = 0;
/*     */       
/* 139 */       if (this.iconOnly) {
/* 140 */         i3 = getWidth() / 2 - i2 / 2;
/* 141 */         i5 = getHeight() / 2 - i4 / 2;
/*     */       } else {
/*     */         
/* 144 */         if (bool) {
/* 145 */           i3 = k + i - 1 - i2;
/*     */         } else {
/*     */           
/* 148 */           i3 = k;
/*     */         } 
/* 150 */         i5 = m + (i1 - m) / 2 - i4 / 2;
/*     */       } 
/*     */       
/* 153 */       this.comboIcon.paintIcon(this, paramGraphics, i3, i5);
/*     */ 
/*     */       
/* 156 */       if (this.comboBox.hasFocus() && (!MetalLookAndFeel.usingOcean() || this.comboBox
/* 157 */         .isEditable())) {
/* 158 */         paramGraphics.setColor(MetalLookAndFeel.getFocusColor());
/* 159 */         paramGraphics.drawRect(k - 1, m - 1, i + 3, j + 1);
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     if (MetalLookAndFeel.usingOcean()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 169 */     if (!this.iconOnly && this.comboBox != null) {
/* 170 */       ListCellRenderer<Object> listCellRenderer = this.comboBox.getRenderer();
/*     */       
/* 172 */       boolean bool1 = getModel().isPressed();
/* 173 */       Component component = listCellRenderer.getListCellRendererComponent(this.listBox, this.comboBox
/* 174 */           .getSelectedItem(), -1, bool1, false);
/*     */ 
/*     */ 
/*     */       
/* 178 */       component.setFont(this.rendererPane.getFont());
/*     */       
/* 180 */       if (this.model.isArmed() && this.model.isPressed()) {
/* 181 */         if (isOpaque()) {
/* 182 */           component.setBackground(UIManager.getColor("Button.select"));
/*     */         }
/* 184 */         component.setForeground(this.comboBox.getForeground());
/*     */       }
/* 186 */       else if (!this.comboBox.isEnabled()) {
/* 187 */         if (isOpaque()) {
/* 188 */           component.setBackground(UIManager.getColor("ComboBox.disabledBackground"));
/*     */         }
/* 190 */         component.setForeground(UIManager.getColor("ComboBox.disabledForeground"));
/*     */       } else {
/*     */         
/* 193 */         component.setForeground(this.comboBox.getForeground());
/* 194 */         component.setBackground(this.comboBox.getBackground());
/*     */       } 
/*     */ 
/*     */       
/* 198 */       int i4 = i - insets.right + i2;
/*     */ 
/*     */       
/* 201 */       boolean bool2 = false;
/* 202 */       if (component instanceof javax.swing.JPanel) {
/* 203 */         bool2 = true;
/*     */       }
/*     */       
/* 206 */       if (bool) {
/* 207 */         this.rendererPane.paintComponent(paramGraphics, component, this, k, m, i4, j, bool2);
/*     */       }
/*     */       else {
/*     */         
/* 211 */         this.rendererPane.paintComponent(paramGraphics, component, this, k + i2, m, i4, j, bool2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize() {
/* 218 */     Dimension dimension = new Dimension();
/* 219 */     Insets insets = getInsets();
/* 220 */     dimension.width = insets.left + getComboIcon().getIconWidth() + insets.right;
/* 221 */     dimension.height = insets.bottom + getComboIcon().getIconHeight() + insets.top;
/* 222 */     return dimension;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalComboBoxButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */