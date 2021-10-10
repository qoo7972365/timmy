/*     */ package javax.swing.plaf.basic;
/*     */ 
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
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.text.View;
/*     */ import sun.awt.AppContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicToggleButtonUI
/*     */   extends BasicButtonUI
/*     */ {
/*  48 */   private static final Object BASIC_TOGGLE_BUTTON_UI_KEY = new Object();
/*     */ 
/*     */   
/*     */   private static final String propertyPrefix = "ToggleButton.";
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  56 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/*  58 */     BasicToggleButtonUI basicToggleButtonUI = (BasicToggleButtonUI)appContext.get(BASIC_TOGGLE_BUTTON_UI_KEY);
/*  59 */     if (basicToggleButtonUI == null) {
/*  60 */       basicToggleButtonUI = new BasicToggleButtonUI();
/*  61 */       appContext.put(BASIC_TOGGLE_BUTTON_UI_KEY, basicToggleButtonUI);
/*     */     } 
/*  63 */     return basicToggleButtonUI;
/*     */   }
/*     */   
/*     */   protected String getPropertyPrefix() {
/*  67 */     return "ToggleButton.";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/*  75 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/*  76 */     ButtonModel buttonModel = abstractButton.getModel();
/*     */     
/*  78 */     Dimension dimension = abstractButton.getSize();
/*  79 */     FontMetrics fontMetrics = paramGraphics.getFontMetrics();
/*     */     
/*  81 */     Insets insets = paramJComponent.getInsets();
/*     */     
/*  83 */     Rectangle rectangle1 = new Rectangle(dimension);
/*     */     
/*  85 */     rectangle1.x += insets.left;
/*  86 */     rectangle1.y += insets.top;
/*  87 */     rectangle1.width -= insets.right + rectangle1.x;
/*  88 */     rectangle1.height -= insets.bottom + rectangle1.y;
/*     */     
/*  90 */     Rectangle rectangle2 = new Rectangle();
/*  91 */     Rectangle rectangle3 = new Rectangle();
/*     */     
/*  93 */     Font font = paramJComponent.getFont();
/*  94 */     paramGraphics.setFont(font);
/*     */ 
/*     */     
/*  97 */     String str = SwingUtilities.layoutCompoundLabel(paramJComponent, fontMetrics, abstractButton
/*  98 */         .getText(), abstractButton.getIcon(), abstractButton
/*  99 */         .getVerticalAlignment(), abstractButton.getHorizontalAlignment(), abstractButton
/* 100 */         .getVerticalTextPosition(), abstractButton.getHorizontalTextPosition(), rectangle1, rectangle2, rectangle3, 
/*     */         
/* 102 */         (abstractButton.getText() == null) ? 0 : abstractButton.getIconTextGap());
/*     */     
/* 104 */     paramGraphics.setColor(abstractButton.getBackground());
/*     */     
/* 106 */     if ((buttonModel.isArmed() && buttonModel.isPressed()) || buttonModel.isSelected()) {
/* 107 */       paintButtonPressed(paramGraphics, abstractButton);
/*     */     }
/*     */ 
/*     */     
/* 111 */     if (abstractButton.getIcon() != null) {
/* 112 */       paintIcon(paramGraphics, abstractButton, rectangle2);
/*     */     }
/*     */ 
/*     */     
/* 116 */     if (str != null && !str.equals("")) {
/* 117 */       View view = (View)paramJComponent.getClientProperty("html");
/* 118 */       if (view != null) {
/* 119 */         view.paint(paramGraphics, rectangle3);
/*     */       } else {
/* 121 */         paintText(paramGraphics, abstractButton, rectangle3, str);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 126 */     if (abstractButton.isFocusPainted() && abstractButton.hasFocus()) {
/* 127 */       paintFocus(paramGraphics, abstractButton, rectangle1, rectangle3, rectangle2);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void paintIcon(Graphics paramGraphics, AbstractButton paramAbstractButton, Rectangle paramRectangle) {
/* 132 */     ButtonModel buttonModel = paramAbstractButton.getModel();
/* 133 */     Icon icon = null;
/*     */     
/* 135 */     if (!buttonModel.isEnabled()) {
/* 136 */       if (buttonModel.isSelected()) {
/* 137 */         icon = paramAbstractButton.getDisabledSelectedIcon();
/*     */       } else {
/* 139 */         icon = paramAbstractButton.getDisabledIcon();
/*     */       } 
/* 141 */     } else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 142 */       icon = paramAbstractButton.getPressedIcon();
/* 143 */       if (icon == null)
/*     */       {
/* 145 */         icon = paramAbstractButton.getSelectedIcon();
/*     */       }
/* 147 */     } else if (buttonModel.isSelected()) {
/* 148 */       if (paramAbstractButton.isRolloverEnabled() && buttonModel.isRollover()) {
/* 149 */         icon = paramAbstractButton.getRolloverSelectedIcon();
/* 150 */         if (icon == null) {
/* 151 */           icon = paramAbstractButton.getSelectedIcon();
/*     */         }
/*     */       } else {
/* 154 */         icon = paramAbstractButton.getSelectedIcon();
/*     */       } 
/* 156 */     } else if (paramAbstractButton.isRolloverEnabled() && buttonModel.isRollover()) {
/* 157 */       icon = paramAbstractButton.getRolloverIcon();
/*     */     } 
/*     */     
/* 160 */     if (icon == null) {
/* 161 */       icon = paramAbstractButton.getIcon();
/*     */     }
/*     */     
/* 164 */     icon.paintIcon(paramAbstractButton, paramGraphics, paramRectangle.x, paramRectangle.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getTextShiftOffset() {
/* 172 */     return 0;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicToggleButtonUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */