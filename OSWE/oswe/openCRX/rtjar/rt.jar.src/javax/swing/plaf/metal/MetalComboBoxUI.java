/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.ComboBoxEditor;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicComboBoxUI;
/*     */ import javax.swing.plaf.basic.BasicComboPopup;
/*     */ import javax.swing.plaf.basic.ComboPopup;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalComboBoxUI
/*     */   extends BasicComboBoxUI
/*     */ {
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  57 */     return new MetalComboBoxUI();
/*     */   }
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/*  61 */     if (MetalLookAndFeel.usingOcean()) {
/*  62 */       super.paint(paramGraphics, paramJComponent);
/*     */     }
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
/*     */   public void paintCurrentValue(Graphics paramGraphics, Rectangle paramRectangle, boolean paramBoolean) {
/*  78 */     if (MetalLookAndFeel.usingOcean()) {
/*  79 */       paramRectangle.x += 2;
/*  80 */       paramRectangle.width -= 3;
/*  81 */       if (this.arrowButton != null) {
/*  82 */         Insets insets = this.arrowButton.getInsets();
/*  83 */         paramRectangle.y += insets.top;
/*  84 */         paramRectangle.height -= insets.top + insets.bottom;
/*     */       } else {
/*     */         
/*  87 */         paramRectangle.y += 2;
/*  88 */         paramRectangle.height -= 4;
/*     */       } 
/*  90 */       super.paintCurrentValue(paramGraphics, paramRectangle, paramBoolean);
/*     */     }
/*  92 */     else if (paramGraphics == null || paramRectangle == null) {
/*  93 */       throw new NullPointerException("Must supply a non-null Graphics and Rectangle");
/*     */     } 
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
/*     */   public void paintCurrentValueBackground(Graphics paramGraphics, Rectangle paramRectangle, boolean paramBoolean) {
/* 110 */     if (MetalLookAndFeel.usingOcean()) {
/* 111 */       paramGraphics.setColor(MetalLookAndFeel.getControlDarkShadow());
/* 112 */       paramGraphics.drawRect(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height - 1);
/* 113 */       paramGraphics.setColor(MetalLookAndFeel.getControlShadow());
/* 114 */       paramGraphics.drawRect(paramRectangle.x + 1, paramRectangle.y + 1, paramRectangle.width - 2, paramRectangle.height - 3);
/*     */       
/* 116 */       if (paramBoolean && !isPopupVisible(this.comboBox) && this.arrowButton != null)
/*     */       {
/* 118 */         paramGraphics.setColor(this.listBox.getSelectionBackground());
/* 119 */         Insets insets = this.arrowButton.getInsets();
/* 120 */         if (insets.top > 2) {
/* 121 */           paramGraphics.fillRect(paramRectangle.x + 2, paramRectangle.y + 2, paramRectangle.width - 3, insets.top - 2);
/*     */         }
/*     */         
/* 124 */         if (insets.bottom > 2) {
/* 125 */           paramGraphics.fillRect(paramRectangle.x + 2, paramRectangle.y + paramRectangle.height - insets.bottom, paramRectangle.width - 3, insets.bottom - 2);
/*     */         
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 131 */     else if (paramGraphics == null || paramRectangle == null) {
/* 132 */       throw new NullPointerException("Must supply a non-null Graphics and Rectangle");
/*     */     } 
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
/*     */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/*     */     int i;
/* 147 */     if (MetalLookAndFeel.usingOcean() && paramInt2 >= 4) {
/* 148 */       paramInt2 -= 4;
/* 149 */       i = super.getBaseline(paramJComponent, paramInt1, paramInt2);
/* 150 */       if (i >= 0) {
/* 151 */         i += 2;
/*     */       }
/*     */     } else {
/*     */       
/* 155 */       i = super.getBaseline(paramJComponent, paramInt1, paramInt2);
/*     */     } 
/* 157 */     return i;
/*     */   }
/*     */   
/*     */   protected ComboBoxEditor createEditor() {
/* 161 */     return new MetalComboBoxEditor.UIResource();
/*     */   }
/*     */   
/*     */   protected ComboPopup createPopup() {
/* 165 */     return super.createPopup();
/*     */   }
/*     */ 
/*     */   
/*     */   protected JButton createArrowButton() {
/* 170 */     boolean bool = (this.comboBox.isEditable() || MetalLookAndFeel.usingOcean()) ? true : false;
/* 171 */     MetalComboBoxButton metalComboBoxButton = new MetalComboBoxButton(this.comboBox, new MetalComboBoxIcon(), bool, this.currentValuePane, this.listBox);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     metalComboBoxButton.setMargin(new Insets(0, 1, 1, 3));
/* 177 */     if (MetalLookAndFeel.usingOcean())
/*     */     {
/* 179 */       metalComboBoxButton.putClientProperty(MetalBorders.NO_BUTTON_ROLLOVER, Boolean.TRUE);
/*     */     }
/*     */     
/* 182 */     updateButtonForOcean(metalComboBoxButton);
/* 183 */     return metalComboBoxButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateButtonForOcean(JButton paramJButton) {
/* 190 */     if (MetalLookAndFeel.usingOcean())
/*     */     {
/*     */       
/* 193 */       paramJButton.setFocusPainted(this.comboBox.isEditable());
/*     */     }
/*     */   }
/*     */   
/*     */   public PropertyChangeListener createPropertyChangeListener() {
/* 198 */     return new MetalPropertyChangeListener();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class MetalPropertyChangeListener
/*     */     extends BasicComboBoxUI.PropertyChangeHandler
/*     */   {
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 207 */       super.propertyChange(param1PropertyChangeEvent);
/* 208 */       String str = param1PropertyChangeEvent.getPropertyName();
/*     */       
/* 210 */       if (str == "editable") {
/* 211 */         if (MetalComboBoxUI.this.arrowButton instanceof MetalComboBoxButton) {
/* 212 */           MetalComboBoxButton metalComboBoxButton = (MetalComboBoxButton)MetalComboBoxUI.this.arrowButton;
/* 213 */           metalComboBoxButton.setIconOnly((MetalComboBoxUI.this.comboBox.isEditable() || 
/* 214 */               MetalLookAndFeel.usingOcean()));
/*     */         } 
/* 216 */         MetalComboBoxUI.this.comboBox.repaint();
/* 217 */         MetalComboBoxUI.this.updateButtonForOcean(MetalComboBoxUI.this.arrowButton);
/* 218 */       } else if (str == "background") {
/* 219 */         Color color = (Color)param1PropertyChangeEvent.getNewValue();
/* 220 */         MetalComboBoxUI.this.arrowButton.setBackground(color);
/* 221 */         MetalComboBoxUI.this.listBox.setBackground(color);
/*     */       }
/* 223 */       else if (str == "foreground") {
/* 224 */         Color color = (Color)param1PropertyChangeEvent.getNewValue();
/* 225 */         MetalComboBoxUI.this.arrowButton.setForeground(color);
/* 226 */         MetalComboBoxUI.this.listBox.setForeground(color);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void editablePropertyChanged(PropertyChangeEvent paramPropertyChangeEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LayoutManager createLayoutManager() {
/* 242 */     return new MetalComboBoxLayoutManager();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class MetalComboBoxLayoutManager
/*     */     extends BasicComboBoxUI.ComboBoxLayoutManager
/*     */   {
/*     */     public void layoutContainer(Container param1Container) {
/* 251 */       MetalComboBoxUI.this.layoutComboBox(param1Container, this);
/*     */     }
/*     */     public void superLayout(Container param1Container) {
/* 254 */       super.layoutContainer(param1Container);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void layoutComboBox(Container paramContainer, MetalComboBoxLayoutManager paramMetalComboBoxLayoutManager) {
/* 262 */     if (this.comboBox.isEditable() && !MetalLookAndFeel.usingOcean()) {
/* 263 */       paramMetalComboBoxLayoutManager.superLayout(paramContainer);
/*     */       
/*     */       return;
/*     */     } 
/* 267 */     if (this.arrowButton != null) {
/* 268 */       if (MetalLookAndFeel.usingOcean()) {
/* 269 */         Insets insets = this.comboBox.getInsets();
/* 270 */         int i = (this.arrowButton.getMinimumSize()).width;
/* 271 */         this.arrowButton.setBounds(MetalUtils.isLeftToRight(this.comboBox) ? (this.comboBox
/* 272 */             .getWidth() - insets.right - i) : insets.left, insets.top, i, this.comboBox
/*     */ 
/*     */             
/* 275 */             .getHeight() - insets.top - insets.bottom);
/*     */       } else {
/*     */         
/* 278 */         Insets insets = this.comboBox.getInsets();
/* 279 */         int i = this.comboBox.getWidth();
/* 280 */         int j = this.comboBox.getHeight();
/* 281 */         this.arrowButton.setBounds(insets.left, insets.top, i - insets.left + insets.right, j - insets.top + insets.bottom);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 287 */     if (this.editor != null && MetalLookAndFeel.usingOcean()) {
/* 288 */       Rectangle rectangle = rectangleForCurrentValue();
/* 289 */       this.editor.setBounds(rectangle);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void removeListeners() {
/* 301 */     if (this.propertyChangeListener != null) {
/* 302 */       this.comboBox.removePropertyChangeListener(this.propertyChangeListener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configureEditor() {
/* 312 */     super.configureEditor();
/*     */   }
/*     */   
/*     */   public void unconfigureEditor() {
/* 316 */     super.unconfigureEditor();
/*     */   }
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 320 */     if (!this.isMinimumSizeDirty) {
/* 321 */       return new Dimension(this.cachedMinimumSize);
/*     */     }
/*     */     
/* 324 */     Dimension dimension = null;
/*     */     
/* 326 */     if (!this.comboBox.isEditable() && this.arrowButton != null) {
/*     */       
/* 328 */       Insets insets1 = this.arrowButton.getInsets();
/* 329 */       Insets insets2 = this.comboBox.getInsets();
/*     */       
/* 331 */       dimension = getDisplaySize();
/* 332 */       dimension.width += insets2.left + insets2.right;
/* 333 */       dimension.width += insets1.right;
/* 334 */       dimension.width += (this.arrowButton.getMinimumSize()).width;
/* 335 */       dimension.height += insets2.top + insets2.bottom;
/* 336 */       dimension.height += insets1.top + insets1.bottom;
/*     */     }
/* 338 */     else if (this.comboBox.isEditable() && this.arrowButton != null && this.editor != null) {
/*     */ 
/*     */       
/* 341 */       dimension = super.getMinimumSize(paramJComponent);
/* 342 */       Insets insets = this.arrowButton.getMargin();
/* 343 */       dimension.height += insets.top + insets.bottom;
/* 344 */       dimension.width += insets.left + insets.right;
/*     */     } else {
/*     */       
/* 347 */       dimension = super.getMinimumSize(paramJComponent);
/*     */     } 
/*     */     
/* 350 */     this.cachedMinimumSize.setSize(dimension.width, dimension.height);
/* 351 */     this.isMinimumSizeDirty = false;
/*     */     
/* 353 */     return new Dimension(this.cachedMinimumSize);
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
/*     */   @Deprecated
/*     */   public class MetalComboPopup
/*     */     extends BasicComboPopup
/*     */   {
/*     */     public MetalComboPopup(JComboBox<Object> param1JComboBox) {
/* 370 */       super(param1JComboBox);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void delegateFocus(MouseEvent param1MouseEvent) {
/* 379 */       super.delegateFocus(param1MouseEvent);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalComboBoxUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */