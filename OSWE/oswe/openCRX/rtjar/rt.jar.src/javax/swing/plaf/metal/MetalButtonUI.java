/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicButtonListener;
/*     */ import javax.swing.plaf.basic.BasicButtonUI;
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
/*     */ public class MetalButtonUI
/*     */   extends BasicButtonUI
/*     */ {
/*     */   protected Color focusColor;
/*     */   protected Color selectColor;
/*     */   protected Color disabledTextColor;
/*  60 */   private static final Object METAL_BUTTON_UI_KEY = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  66 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/*  68 */     MetalButtonUI metalButtonUI = (MetalButtonUI)appContext.get(METAL_BUTTON_UI_KEY);
/*  69 */     if (metalButtonUI == null) {
/*  70 */       metalButtonUI = new MetalButtonUI();
/*  71 */       appContext.put(METAL_BUTTON_UI_KEY, metalButtonUI);
/*     */     } 
/*  73 */     return metalButtonUI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installDefaults(AbstractButton paramAbstractButton) {
/*  80 */     super.installDefaults(paramAbstractButton);
/*     */   }
/*     */   
/*     */   public void uninstallDefaults(AbstractButton paramAbstractButton) {
/*  84 */     super.uninstallDefaults(paramAbstractButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BasicButtonListener createButtonListener(AbstractButton paramAbstractButton) {
/*  91 */     return super.createButtonListener(paramAbstractButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Color getSelectColor() {
/*  99 */     this.selectColor = UIManager.getColor(getPropertyPrefix() + "select");
/* 100 */     return this.selectColor;
/*     */   }
/*     */   
/*     */   protected Color getDisabledTextColor() {
/* 104 */     this.disabledTextColor = UIManager.getColor(getPropertyPrefix() + "disabledText");
/*     */     
/* 106 */     return this.disabledTextColor;
/*     */   }
/*     */   
/*     */   protected Color getFocusColor() {
/* 110 */     this.focusColor = UIManager.getColor(getPropertyPrefix() + "focus");
/* 111 */     return this.focusColor;
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
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 130 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 131 */     if (paramJComponent.getBackground() instanceof javax.swing.plaf.UIResource && abstractButton
/* 132 */       .isContentAreaFilled() && paramJComponent.isEnabled()) {
/* 133 */       ButtonModel buttonModel = abstractButton.getModel();
/* 134 */       if (!MetalUtils.isToolBarButton(paramJComponent)) {
/* 135 */         if (!buttonModel.isArmed() && !buttonModel.isPressed() && 
/* 136 */           MetalUtils.drawGradient(paramJComponent, paramGraphics, "Button.gradient", 0, 0, paramJComponent
/* 137 */             .getWidth(), paramJComponent
/* 138 */             .getHeight(), true)) {
/* 139 */           paint(paramGraphics, paramJComponent);
/*     */           
/*     */           return;
/*     */         } 
/* 143 */       } else if (buttonModel.isRollover() && MetalUtils.drawGradient(paramJComponent, paramGraphics, "Button.gradient", 0, 0, paramJComponent
/* 144 */           .getWidth(), paramJComponent
/* 145 */           .getHeight(), true)) {
/* 146 */         paint(paramGraphics, paramJComponent);
/*     */         return;
/*     */       } 
/*     */     } 
/* 150 */     super.update(paramGraphics, paramJComponent);
/*     */   }
/*     */   
/*     */   protected void paintButtonPressed(Graphics paramGraphics, AbstractButton paramAbstractButton) {
/* 154 */     if (paramAbstractButton.isContentAreaFilled()) {
/* 155 */       Dimension dimension = paramAbstractButton.getSize();
/* 156 */       paramGraphics.setColor(getSelectColor());
/* 157 */       paramGraphics.fillRect(0, 0, dimension.width, dimension.height);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintFocus(Graphics paramGraphics, AbstractButton paramAbstractButton, Rectangle paramRectangle1, Rectangle paramRectangle2, Rectangle paramRectangle3) {
/* 164 */     Rectangle rectangle = new Rectangle();
/* 165 */     String str = paramAbstractButton.getText();
/* 166 */     boolean bool = (paramAbstractButton.getIcon() != null) ? true : false;
/*     */ 
/*     */     
/* 169 */     if (str != null && !str.equals("")) {
/* 170 */       if (!bool) {
/* 171 */         rectangle.setBounds(paramRectangle2);
/*     */       } else {
/*     */         
/* 174 */         rectangle.setBounds(paramRectangle3.union(paramRectangle2));
/*     */       }
/*     */     
/*     */     }
/* 178 */     else if (bool) {
/* 179 */       rectangle.setBounds(paramRectangle3);
/*     */     } 
/*     */     
/* 182 */     paramGraphics.setColor(getFocusColor());
/* 183 */     paramGraphics.drawRect(rectangle.x - 1, rectangle.y - 1, rectangle.width + 1, rectangle.height + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintText(Graphics paramGraphics, JComponent paramJComponent, Rectangle paramRectangle, String paramString) {
/* 190 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 191 */     ButtonModel buttonModel = abstractButton.getModel();
/* 192 */     FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(paramJComponent, paramGraphics);
/* 193 */     int i = abstractButton.getDisplayedMnemonicIndex();
/*     */ 
/*     */     
/* 196 */     if (buttonModel.isEnabled()) {
/*     */       
/* 198 */       paramGraphics.setColor(abstractButton.getForeground());
/*     */     }
/*     */     else {
/*     */       
/* 202 */       paramGraphics.setColor(getDisabledTextColor());
/*     */     } 
/* 204 */     SwingUtilities2.drawStringUnderlineCharAt(paramJComponent, paramGraphics, paramString, i, paramRectangle.x, paramRectangle.y + fontMetrics
/* 205 */         .getAscent());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalButtonUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */