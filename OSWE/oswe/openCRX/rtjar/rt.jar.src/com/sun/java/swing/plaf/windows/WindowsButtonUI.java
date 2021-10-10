/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.CompoundBorder;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicButtonUI;
/*     */ import javax.swing.plaf.basic.BasicGraphicsUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsButtonUI
/*     */   extends BasicButtonUI
/*     */ {
/*     */   protected int dashedRectGapX;
/*     */   protected int dashedRectGapY;
/*     */   protected int dashedRectGapWidth;
/*     */   protected int dashedRectGapHeight;
/*     */   protected Color focusColor;
/*     */   private boolean defaults_initialized = false;
/*  65 */   private static final Object WINDOWS_BUTTON_UI_KEY = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  71 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/*  73 */     WindowsButtonUI windowsButtonUI = (WindowsButtonUI)appContext.get(WINDOWS_BUTTON_UI_KEY);
/*  74 */     if (windowsButtonUI == null) {
/*  75 */       windowsButtonUI = new WindowsButtonUI();
/*  76 */       appContext.put(WINDOWS_BUTTON_UI_KEY, windowsButtonUI);
/*     */     } 
/*  78 */     return windowsButtonUI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults(AbstractButton paramAbstractButton) {
/*  86 */     super.installDefaults(paramAbstractButton);
/*  87 */     if (!this.defaults_initialized) {
/*  88 */       String str = getPropertyPrefix();
/*  89 */       this.dashedRectGapX = UIManager.getInt(str + "dashedRectGapX");
/*  90 */       this.dashedRectGapY = UIManager.getInt(str + "dashedRectGapY");
/*  91 */       this.dashedRectGapWidth = UIManager.getInt(str + "dashedRectGapWidth");
/*  92 */       this.dashedRectGapHeight = UIManager.getInt(str + "dashedRectGapHeight");
/*  93 */       this.focusColor = UIManager.getColor(str + "focus");
/*  94 */       this.defaults_initialized = true;
/*     */     } 
/*     */     
/*  97 */     XPStyle xPStyle = XPStyle.getXP();
/*  98 */     if (xPStyle != null) {
/*  99 */       paramAbstractButton.setBorder(xPStyle.getBorder(paramAbstractButton, getXPButtonType(paramAbstractButton)));
/* 100 */       LookAndFeel.installProperty(paramAbstractButton, "rolloverEnabled", Boolean.TRUE);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults(AbstractButton paramAbstractButton) {
/* 105 */     super.uninstallDefaults(paramAbstractButton);
/* 106 */     this.defaults_initialized = false;
/*     */   }
/*     */   
/*     */   protected Color getFocusColor() {
/* 110 */     return this.focusColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintText(Graphics paramGraphics, AbstractButton paramAbstractButton, Rectangle paramRectangle, String paramString) {
/* 121 */     WindowsGraphicsUtils.paintText(paramGraphics, paramAbstractButton, paramRectangle, paramString, getTextShiftOffset());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintFocus(Graphics paramGraphics, AbstractButton paramAbstractButton, Rectangle paramRectangle1, Rectangle paramRectangle2, Rectangle paramRectangle3) {
/* 127 */     int i = paramAbstractButton.getWidth();
/* 128 */     int j = paramAbstractButton.getHeight();
/* 129 */     paramGraphics.setColor(getFocusColor());
/* 130 */     BasicGraphicsUtils.drawDashedRect(paramGraphics, this.dashedRectGapX, this.dashedRectGapY, i - this.dashedRectGapWidth, j - this.dashedRectGapHeight);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintButtonPressed(Graphics paramGraphics, AbstractButton paramAbstractButton) {
/* 135 */     setTextShiftOffset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 142 */     Dimension dimension = super.getPreferredSize(paramJComponent);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 148 */     if (dimension != null && abstractButton.isFocusPainted()) {
/* 149 */       if (dimension.width % 2 == 0) dimension.width++; 
/* 150 */       if (dimension.height % 2 == 0) dimension.height++; 
/*     */     } 
/* 152 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 161 */   private Rectangle viewRect = new Rectangle();
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 164 */     if (XPStyle.getXP() != null) {
/* 165 */       paintXPButtonBackground(paramGraphics, paramJComponent);
/*     */     }
/* 167 */     super.paint(paramGraphics, paramJComponent);
/*     */   }
/*     */   
/*     */   static TMSchema.Part getXPButtonType(AbstractButton paramAbstractButton) {
/* 171 */     if (paramAbstractButton instanceof javax.swing.JCheckBox) {
/* 172 */       return TMSchema.Part.BP_CHECKBOX;
/*     */     }
/* 174 */     if (paramAbstractButton instanceof javax.swing.JRadioButton) {
/* 175 */       return TMSchema.Part.BP_RADIOBUTTON;
/*     */     }
/* 177 */     boolean bool = paramAbstractButton.getParent() instanceof javax.swing.JToolBar;
/* 178 */     return bool ? TMSchema.Part.TP_BUTTON : TMSchema.Part.BP_PUSHBUTTON;
/*     */   }
/*     */   static TMSchema.State getXPButtonState(AbstractButton paramAbstractButton) {
/*     */     boolean bool;
/* 182 */     TMSchema.Part part = getXPButtonType(paramAbstractButton);
/* 183 */     ButtonModel buttonModel = paramAbstractButton.getModel();
/* 184 */     TMSchema.State state = TMSchema.State.NORMAL;
/* 185 */     switch (part)
/*     */     
/*     */     { case BP_RADIOBUTTON:
/*     */       case BP_CHECKBOX:
/* 189 */         if (!buttonModel.isEnabled()) {
/* 190 */           state = buttonModel.isSelected() ? TMSchema.State.CHECKEDDISABLED : TMSchema.State.UNCHECKEDDISABLED;
/*     */         }
/* 192 */         else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 193 */           state = buttonModel.isSelected() ? TMSchema.State.CHECKEDPRESSED : TMSchema.State.UNCHECKEDPRESSED;
/*     */         }
/* 195 */         else if (buttonModel.isRollover()) {
/* 196 */           state = buttonModel.isSelected() ? TMSchema.State.CHECKEDHOT : TMSchema.State.UNCHECKEDHOT;
/*     */         } else {
/*     */           
/* 199 */           state = buttonModel.isSelected() ? TMSchema.State.CHECKEDNORMAL : TMSchema.State.UNCHECKEDNORMAL;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 241 */         return state;case BP_PUSHBUTTON: case TP_BUTTON: bool = paramAbstractButton.getParent() instanceof javax.swing.JToolBar; if (bool) { if (buttonModel.isArmed() && buttonModel.isPressed()) { state = TMSchema.State.PRESSED; } else if (!buttonModel.isEnabled()) { state = TMSchema.State.DISABLED; } else if (buttonModel.isSelected() && buttonModel.isRollover()) { state = TMSchema.State.HOTCHECKED; } else if (buttonModel.isSelected()) { state = TMSchema.State.CHECKED; } else if (buttonModel.isRollover()) { state = TMSchema.State.HOT; } else if (paramAbstractButton.hasFocus()) { state = TMSchema.State.HOT; }  } else if ((buttonModel.isArmed() && buttonModel.isPressed()) || buttonModel.isSelected()) { state = TMSchema.State.PRESSED; } else if (!buttonModel.isEnabled()) { state = TMSchema.State.DISABLED; } else if (buttonModel.isRollover() || buttonModel.isPressed()) { state = TMSchema.State.HOT; } else if (paramAbstractButton instanceof JButton && ((JButton)paramAbstractButton).isDefaultButton()) { state = TMSchema.State.DEFAULTED; } else if (paramAbstractButton.hasFocus()) { state = TMSchema.State.HOT; }  return state; }  state = TMSchema.State.NORMAL; return state;
/*     */   }
/*     */   
/*     */   static void paintXPButtonBackground(Graphics paramGraphics, JComponent paramJComponent) {
/* 245 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/*     */     
/* 247 */     XPStyle xPStyle = XPStyle.getXP();
/*     */     
/* 249 */     TMSchema.Part part = getXPButtonType(abstractButton);
/*     */     
/* 251 */     if (abstractButton.isContentAreaFilled() && xPStyle != null) {
/*     */       Insets insets;
/* 253 */       XPStyle.Skin skin = xPStyle.getSkin(abstractButton, part);
/*     */       
/* 255 */       TMSchema.State state = getXPButtonState(abstractButton);
/* 256 */       Dimension dimension = paramJComponent.getSize();
/* 257 */       int i = 0;
/* 258 */       int j = 0;
/* 259 */       int k = dimension.width;
/* 260 */       int m = dimension.height;
/*     */       
/* 262 */       Border border = paramJComponent.getBorder();
/*     */       
/* 264 */       if (border != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 270 */         insets = getOpaqueInsets(border, paramJComponent);
/*     */       } else {
/* 272 */         insets = paramJComponent.getInsets();
/*     */       } 
/* 274 */       if (insets != null) {
/* 275 */         i += insets.left;
/* 276 */         j += insets.top;
/* 277 */         k -= insets.left + insets.right;
/* 278 */         m -= insets.top + insets.bottom;
/*     */       } 
/* 280 */       skin.paintSkin(paramGraphics, i, j, k, m, state);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Insets getOpaqueInsets(Border paramBorder, Component paramComponent) {
/* 291 */     if (paramBorder == null) {
/* 292 */       return null;
/*     */     }
/* 294 */     if (paramBorder.isBorderOpaque())
/* 295 */       return paramBorder.getBorderInsets(paramComponent); 
/* 296 */     if (paramBorder instanceof CompoundBorder) {
/* 297 */       CompoundBorder compoundBorder = (CompoundBorder)paramBorder;
/* 298 */       Insets insets = getOpaqueInsets(compoundBorder.getOutsideBorder(), paramComponent);
/* 299 */       if (insets != null && insets.equals(compoundBorder.getOutsideBorder().getBorderInsets(paramComponent))) {
/*     */         
/* 301 */         Insets insets1 = getOpaqueInsets(compoundBorder.getInsideBorder(), paramComponent);
/* 302 */         if (insets1 == null)
/*     */         {
/* 304 */           return insets;
/*     */         }
/*     */ 
/*     */         
/* 308 */         return new Insets(insets.top + insets1.top, insets.left + insets1.left, insets.bottom + insets1.bottom, insets.right + insets1.right);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 314 */       return insets;
/*     */     } 
/*     */     
/* 317 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsButtonUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */