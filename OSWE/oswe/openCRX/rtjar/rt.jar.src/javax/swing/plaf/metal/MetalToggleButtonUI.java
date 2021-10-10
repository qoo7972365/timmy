/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicToggleButtonUI;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalToggleButtonUI
/*     */   extends BasicToggleButtonUI
/*     */ {
/*  60 */   private static final Object METAL_TOGGLE_BUTTON_UI_KEY = new Object();
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
/*  72 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/*  74 */     MetalToggleButtonUI metalToggleButtonUI = (MetalToggleButtonUI)appContext.get(METAL_TOGGLE_BUTTON_UI_KEY);
/*  75 */     if (metalToggleButtonUI == null) {
/*  76 */       metalToggleButtonUI = new MetalToggleButtonUI();
/*  77 */       appContext.put(METAL_TOGGLE_BUTTON_UI_KEY, metalToggleButtonUI);
/*     */     } 
/*  79 */     return metalToggleButtonUI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installDefaults(AbstractButton paramAbstractButton) {
/*  86 */     super.installDefaults(paramAbstractButton);
/*  87 */     if (!this.defaults_initialized) {
/*  88 */       this.focusColor = UIManager.getColor(getPropertyPrefix() + "focus");
/*  89 */       this.selectColor = UIManager.getColor(getPropertyPrefix() + "select");
/*  90 */       this.disabledTextColor = UIManager.getColor(getPropertyPrefix() + "disabledText");
/*  91 */       this.defaults_initialized = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults(AbstractButton paramAbstractButton) {
/*  96 */     super.uninstallDefaults(paramAbstractButton);
/*  97 */     this.defaults_initialized = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Color getSelectColor() {
/* 104 */     return this.selectColor;
/*     */   }
/*     */   
/*     */   protected Color getDisabledTextColor() {
/* 108 */     return this.disabledTextColor;
/*     */   }
/*     */   
/*     */   protected Color getFocusColor() {
/* 112 */     return this.focusColor;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 132 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 133 */     if (paramJComponent.getBackground() instanceof javax.swing.plaf.UIResource && abstractButton
/* 134 */       .isContentAreaFilled() && paramJComponent.isEnabled()) {
/* 135 */       ButtonModel buttonModel = abstractButton.getModel();
/* 136 */       if (!MetalUtils.isToolBarButton(paramJComponent)) {
/* 137 */         if (!buttonModel.isArmed() && !buttonModel.isPressed() && 
/* 138 */           MetalUtils.drawGradient(paramJComponent, paramGraphics, "ToggleButton.gradient", 0, 0, paramJComponent
/* 139 */             .getWidth(), paramJComponent
/* 140 */             .getHeight(), true)) {
/* 141 */           paint(paramGraphics, paramJComponent);
/*     */           
/*     */           return;
/*     */         } 
/* 145 */       } else if ((buttonModel.isRollover() || buttonModel.isSelected()) && 
/* 146 */         MetalUtils.drawGradient(paramJComponent, paramGraphics, "ToggleButton.gradient", 0, 0, paramJComponent
/* 147 */           .getWidth(), paramJComponent.getHeight(), true)) {
/* 148 */         paint(paramGraphics, paramJComponent);
/*     */         return;
/*     */       } 
/*     */     } 
/* 152 */     super.update(paramGraphics, paramJComponent);
/*     */   }
/*     */   
/*     */   protected void paintButtonPressed(Graphics paramGraphics, AbstractButton paramAbstractButton) {
/* 156 */     if (paramAbstractButton.isContentAreaFilled()) {
/* 157 */       paramGraphics.setColor(getSelectColor());
/* 158 */       paramGraphics.fillRect(0, 0, paramAbstractButton.getWidth(), paramAbstractButton.getHeight());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void paintText(Graphics paramGraphics, JComponent paramJComponent, Rectangle paramRectangle, String paramString) {
/* 163 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 164 */     ButtonModel buttonModel = abstractButton.getModel();
/* 165 */     FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(abstractButton, paramGraphics);
/* 166 */     int i = abstractButton.getDisplayedMnemonicIndex();
/*     */ 
/*     */     
/* 169 */     if (buttonModel.isEnabled()) {
/*     */       
/* 171 */       paramGraphics.setColor(abstractButton.getForeground());
/*     */ 
/*     */     
/*     */     }
/* 175 */     else if (buttonModel.isSelected()) {
/* 176 */       paramGraphics.setColor(paramJComponent.getBackground());
/*     */     } else {
/* 178 */       paramGraphics.setColor(getDisabledTextColor());
/*     */     } 
/*     */     
/* 181 */     SwingUtilities2.drawStringUnderlineCharAt(paramJComponent, paramGraphics, paramString, i, paramRectangle.x, paramRectangle.y + fontMetrics
/* 182 */         .getAscent());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintFocus(Graphics paramGraphics, AbstractButton paramAbstractButton, Rectangle paramRectangle1, Rectangle paramRectangle2, Rectangle paramRectangle3) {
/* 188 */     Rectangle rectangle = new Rectangle();
/* 189 */     String str = paramAbstractButton.getText();
/* 190 */     boolean bool = (paramAbstractButton.getIcon() != null) ? true : false;
/*     */ 
/*     */     
/* 193 */     if (str != null && !str.equals("")) {
/* 194 */       if (!bool) {
/* 195 */         rectangle.setBounds(paramRectangle2);
/*     */       } else {
/*     */         
/* 198 */         rectangle.setBounds(paramRectangle3.union(paramRectangle2));
/*     */       }
/*     */     
/*     */     }
/* 202 */     else if (bool) {
/* 203 */       rectangle.setBounds(paramRectangle3);
/*     */     } 
/*     */     
/* 206 */     paramGraphics.setColor(getFocusColor());
/* 207 */     paramGraphics.drawRect(rectangle.x - 1, rectangle.y - 1, rectangle.width + 1, rectangle.height + 1);
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
/*     */   protected void paintIcon(Graphics paramGraphics, AbstractButton paramAbstractButton, Rectangle paramRectangle) {
/* 223 */     super.paintIcon(paramGraphics, paramAbstractButton, paramRectangle);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalToggleButtonUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */