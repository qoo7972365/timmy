/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicRadioButtonUI;
/*     */ import javax.swing.text.View;
/*     */ import sun.awt.AppContext;
/*     */ import sun.swing.SwingUtilities2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalRadioButtonUI
/*     */   extends BasicRadioButtonUI
/*     */ {
/*  58 */   private static final Object METAL_RADIO_BUTTON_UI_KEY = new Object();
/*     */   
/*     */   protected Color focusColor;
/*     */   
/*     */   protected Color selectColor;
/*     */   
/*     */   protected Color disabledTextColor;
/*     */   
/*     */   private boolean defaults_initialized = false;
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  70 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/*  72 */     MetalRadioButtonUI metalRadioButtonUI = (MetalRadioButtonUI)appContext.get(METAL_RADIO_BUTTON_UI_KEY);
/*  73 */     if (metalRadioButtonUI == null) {
/*  74 */       metalRadioButtonUI = new MetalRadioButtonUI();
/*  75 */       appContext.put(METAL_RADIO_BUTTON_UI_KEY, metalRadioButtonUI);
/*     */     } 
/*  77 */     return metalRadioButtonUI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installDefaults(AbstractButton paramAbstractButton) {
/*  84 */     super.installDefaults(paramAbstractButton);
/*  85 */     if (!this.defaults_initialized) {
/*  86 */       this.focusColor = UIManager.getColor(getPropertyPrefix() + "focus");
/*  87 */       this.selectColor = UIManager.getColor(getPropertyPrefix() + "select");
/*  88 */       this.disabledTextColor = UIManager.getColor(getPropertyPrefix() + "disabledText");
/*  89 */       this.defaults_initialized = true;
/*     */     } 
/*  91 */     LookAndFeel.installProperty(paramAbstractButton, "opaque", Boolean.TRUE);
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults(AbstractButton paramAbstractButton) {
/*  95 */     super.uninstallDefaults(paramAbstractButton);
/*  96 */     this.defaults_initialized = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Color getSelectColor() {
/* 103 */     return this.selectColor;
/*     */   }
/*     */   
/*     */   protected Color getDisabledTextColor() {
/* 107 */     return this.disabledTextColor;
/*     */   }
/*     */   
/*     */   protected Color getFocusColor() {
/* 111 */     return this.focusColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 120 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 121 */     ButtonModel buttonModel = abstractButton.getModel();
/*     */     
/* 123 */     Dimension dimension = paramJComponent.getSize();
/*     */     
/* 125 */     int i = dimension.width;
/* 126 */     int j = dimension.height;
/*     */     
/* 128 */     Font font = paramJComponent.getFont();
/* 129 */     paramGraphics.setFont(font);
/* 130 */     FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(paramJComponent, paramGraphics, font);
/*     */     
/* 132 */     Rectangle rectangle1 = new Rectangle(dimension);
/* 133 */     Rectangle rectangle2 = new Rectangle();
/* 134 */     Rectangle rectangle3 = new Rectangle();
/*     */     
/* 136 */     Insets insets = paramJComponent.getInsets();
/* 137 */     rectangle1.x += insets.left;
/* 138 */     rectangle1.y += insets.top;
/* 139 */     rectangle1.width -= insets.right + rectangle1.x;
/* 140 */     rectangle1.height -= insets.bottom + rectangle1.y;
/*     */     
/* 142 */     Icon icon = abstractButton.getIcon();
/* 143 */     Object object1 = null;
/* 144 */     Object object2 = null;
/*     */     
/* 146 */     String str = SwingUtilities.layoutCompoundLabel(paramJComponent, fontMetrics, abstractButton
/* 147 */         .getText(), (icon != null) ? icon : getDefaultIcon(), abstractButton
/* 148 */         .getVerticalAlignment(), abstractButton.getHorizontalAlignment(), abstractButton
/* 149 */         .getVerticalTextPosition(), abstractButton.getHorizontalTextPosition(), rectangle1, rectangle2, rectangle3, abstractButton
/* 150 */         .getIconTextGap());
/*     */ 
/*     */     
/* 153 */     if (paramJComponent.isOpaque()) {
/* 154 */       paramGraphics.setColor(abstractButton.getBackground());
/* 155 */       paramGraphics.fillRect(0, 0, dimension.width, dimension.height);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 160 */     if (icon != null) {
/*     */       
/* 162 */       if (!buttonModel.isEnabled()) {
/* 163 */         if (buttonModel.isSelected()) {
/* 164 */           icon = abstractButton.getDisabledSelectedIcon();
/*     */         } else {
/* 166 */           icon = abstractButton.getDisabledIcon();
/*     */         } 
/* 168 */       } else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 169 */         icon = abstractButton.getPressedIcon();
/* 170 */         if (icon == null)
/*     */         {
/* 172 */           icon = abstractButton.getSelectedIcon();
/*     */         }
/* 174 */       } else if (buttonModel.isSelected()) {
/* 175 */         if (abstractButton.isRolloverEnabled() && buttonModel.isRollover()) {
/* 176 */           icon = abstractButton.getRolloverSelectedIcon();
/* 177 */           if (icon == null) {
/* 178 */             icon = abstractButton.getSelectedIcon();
/*     */           }
/*     */         } else {
/* 181 */           icon = abstractButton.getSelectedIcon();
/*     */         } 
/* 183 */       } else if (abstractButton.isRolloverEnabled() && buttonModel.isRollover()) {
/* 184 */         icon = abstractButton.getRolloverIcon();
/*     */       } 
/*     */       
/* 187 */       if (icon == null) {
/* 188 */         icon = abstractButton.getIcon();
/*     */       }
/*     */       
/* 191 */       icon.paintIcon(paramJComponent, paramGraphics, rectangle2.x, rectangle2.y);
/*     */     } else {
/*     */       
/* 194 */       getDefaultIcon().paintIcon(paramJComponent, paramGraphics, rectangle2.x, rectangle2.y);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 199 */     if (str != null) {
/* 200 */       View view = (View)paramJComponent.getClientProperty("html");
/* 201 */       if (view != null) {
/* 202 */         view.paint(paramGraphics, rectangle3);
/*     */       } else {
/* 204 */         int k = abstractButton.getDisplayedMnemonicIndex();
/* 205 */         if (buttonModel.isEnabled()) {
/*     */           
/* 207 */           paramGraphics.setColor(abstractButton.getForeground());
/*     */         } else {
/*     */           
/* 210 */           paramGraphics.setColor(getDisabledTextColor());
/*     */         } 
/* 212 */         SwingUtilities2.drawStringUnderlineCharAt(paramJComponent, paramGraphics, str, k, rectangle3.x, rectangle3.y + fontMetrics
/* 213 */             .getAscent());
/*     */       } 
/* 215 */       if (abstractButton.hasFocus() && abstractButton.isFocusPainted() && rectangle3.width > 0 && rectangle3.height > 0)
/*     */       {
/* 217 */         paintFocus(paramGraphics, rectangle3, dimension);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void paintFocus(Graphics paramGraphics, Rectangle paramRectangle, Dimension paramDimension) {
/* 223 */     paramGraphics.setColor(getFocusColor());
/* 224 */     paramGraphics.drawRect(paramRectangle.x - 1, paramRectangle.y - 1, paramRectangle.width + 1, paramRectangle.height + 1);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalRadioButtonUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */