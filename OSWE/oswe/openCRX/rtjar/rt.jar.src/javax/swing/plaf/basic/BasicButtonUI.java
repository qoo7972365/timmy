/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ButtonUI;
/*     */ import javax.swing.plaf.ComponentUI;
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
/*     */ public class BasicButtonUI
/*     */   extends ButtonUI
/*     */ {
/*     */   protected int defaultTextIconGap;
/*  56 */   private int shiftOffset = 0;
/*     */ 
/*     */   
/*     */   protected int defaultTextShiftOffset;
/*     */   
/*     */   private static final String propertyPrefix = "Button.";
/*     */   
/*  63 */   private static final Object BASIC_BUTTON_UI_KEY = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  69 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/*  71 */     BasicButtonUI basicButtonUI = (BasicButtonUI)appContext.get(BASIC_BUTTON_UI_KEY);
/*  72 */     if (basicButtonUI == null) {
/*  73 */       basicButtonUI = new BasicButtonUI();
/*  74 */       appContext.put(BASIC_BUTTON_UI_KEY, basicButtonUI);
/*     */     } 
/*  76 */     return basicButtonUI;
/*     */   }
/*     */   
/*     */   protected String getPropertyPrefix() {
/*  80 */     return "Button.";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  88 */     installDefaults((AbstractButton)paramJComponent);
/*  89 */     installListeners((AbstractButton)paramJComponent);
/*  90 */     installKeyboardActions((AbstractButton)paramJComponent);
/*  91 */     BasicHTML.updateRenderer(paramJComponent, ((AbstractButton)paramJComponent).getText());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void installDefaults(AbstractButton paramAbstractButton) {
/*  96 */     String str = getPropertyPrefix();
/*     */     
/*  98 */     this.defaultTextShiftOffset = UIManager.getInt(str + "textShiftOffset");
/*     */ 
/*     */     
/* 101 */     if (paramAbstractButton.isContentAreaFilled()) {
/* 102 */       LookAndFeel.installProperty(paramAbstractButton, "opaque", Boolean.TRUE);
/*     */     } else {
/* 104 */       LookAndFeel.installProperty(paramAbstractButton, "opaque", Boolean.FALSE);
/*     */     } 
/*     */     
/* 107 */     if (paramAbstractButton.getMargin() == null || paramAbstractButton.getMargin() instanceof javax.swing.plaf.UIResource) {
/* 108 */       paramAbstractButton.setMargin(UIManager.getInsets(str + "margin"));
/*     */     }
/*     */     
/* 111 */     LookAndFeel.installColorsAndFont(paramAbstractButton, str + "background", str + "foreground", str + "font");
/*     */     
/* 113 */     LookAndFeel.installBorder(paramAbstractButton, str + "border");
/*     */     
/* 115 */     Object object = UIManager.get(str + "rollover");
/* 116 */     if (object != null) {
/* 117 */       LookAndFeel.installProperty(paramAbstractButton, "rolloverEnabled", object);
/*     */     }
/*     */     
/* 120 */     LookAndFeel.installProperty(paramAbstractButton, "iconTextGap", Integer.valueOf(4));
/*     */   }
/*     */   
/*     */   protected void installListeners(AbstractButton paramAbstractButton) {
/* 124 */     BasicButtonListener basicButtonListener = createButtonListener(paramAbstractButton);
/* 125 */     if (basicButtonListener != null) {
/* 126 */       paramAbstractButton.addMouseListener(basicButtonListener);
/* 127 */       paramAbstractButton.addMouseMotionListener(basicButtonListener);
/* 128 */       paramAbstractButton.addFocusListener(basicButtonListener);
/* 129 */       paramAbstractButton.addPropertyChangeListener(basicButtonListener);
/* 130 */       paramAbstractButton.addChangeListener(basicButtonListener);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void installKeyboardActions(AbstractButton paramAbstractButton) {
/* 135 */     BasicButtonListener basicButtonListener = getButtonListener(paramAbstractButton);
/*     */     
/* 137 */     if (basicButtonListener != null) {
/* 138 */       basicButtonListener.installKeyboardActions(paramAbstractButton);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 147 */     uninstallKeyboardActions((AbstractButton)paramJComponent);
/* 148 */     uninstallListeners((AbstractButton)paramJComponent);
/* 149 */     uninstallDefaults((AbstractButton)paramJComponent);
/* 150 */     BasicHTML.updateRenderer(paramJComponent, "");
/*     */   }
/*     */   
/*     */   protected void uninstallKeyboardActions(AbstractButton paramAbstractButton) {
/* 154 */     BasicButtonListener basicButtonListener = getButtonListener(paramAbstractButton);
/* 155 */     if (basicButtonListener != null) {
/* 156 */       basicButtonListener.uninstallKeyboardActions(paramAbstractButton);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void uninstallListeners(AbstractButton paramAbstractButton) {
/* 161 */     BasicButtonListener basicButtonListener = getButtonListener(paramAbstractButton);
/* 162 */     if (basicButtonListener != null) {
/* 163 */       paramAbstractButton.removeMouseListener(basicButtonListener);
/* 164 */       paramAbstractButton.removeMouseMotionListener(basicButtonListener);
/* 165 */       paramAbstractButton.removeFocusListener(basicButtonListener);
/* 166 */       paramAbstractButton.removeChangeListener(basicButtonListener);
/* 167 */       paramAbstractButton.removePropertyChangeListener(basicButtonListener);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults(AbstractButton paramAbstractButton) {
/* 172 */     LookAndFeel.uninstallBorder(paramAbstractButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BasicButtonListener createButtonListener(AbstractButton paramAbstractButton) {
/* 179 */     return new BasicButtonListener(paramAbstractButton);
/*     */   }
/*     */   
/*     */   public int getDefaultTextIconGap(AbstractButton paramAbstractButton) {
/* 183 */     return this.defaultTextIconGap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 191 */   private static Rectangle viewRect = new Rectangle();
/* 192 */   private static Rectangle textRect = new Rectangle();
/* 193 */   private static Rectangle iconRect = new Rectangle();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 201 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 202 */     ButtonModel buttonModel = abstractButton.getModel();
/*     */     
/* 204 */     String str = layout(abstractButton, SwingUtilities2.getFontMetrics(abstractButton, paramGraphics), abstractButton
/* 205 */         .getWidth(), abstractButton.getHeight());
/*     */     
/* 207 */     clearTextShiftOffset();
/*     */ 
/*     */     
/* 210 */     if (buttonModel.isArmed() && buttonModel.isPressed()) {
/* 211 */       paintButtonPressed(paramGraphics, abstractButton);
/*     */     }
/*     */ 
/*     */     
/* 215 */     if (abstractButton.getIcon() != null) {
/* 216 */       paintIcon(paramGraphics, paramJComponent, iconRect);
/*     */     }
/*     */     
/* 219 */     if (str != null && !str.equals("")) {
/* 220 */       View view = (View)paramJComponent.getClientProperty("html");
/* 221 */       if (view != null) {
/* 222 */         view.paint(paramGraphics, textRect);
/*     */       } else {
/* 224 */         paintText(paramGraphics, abstractButton, textRect, str);
/*     */       } 
/*     */     } 
/*     */     
/* 228 */     if (abstractButton.isFocusPainted() && abstractButton.hasFocus())
/*     */     {
/* 230 */       paintFocus(paramGraphics, abstractButton, viewRect, textRect, iconRect);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void paintIcon(Graphics paramGraphics, JComponent paramJComponent, Rectangle paramRectangle) {
/* 235 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 236 */     ButtonModel buttonModel = abstractButton.getModel();
/* 237 */     Icon icon1 = abstractButton.getIcon();
/* 238 */     Icon icon2 = null;
/*     */     
/* 240 */     if (icon1 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 244 */     Icon icon3 = null;
/*     */ 
/*     */     
/* 247 */     if (buttonModel.isSelected()) {
/* 248 */       icon3 = abstractButton.getSelectedIcon();
/* 249 */       if (icon3 != null) {
/* 250 */         icon1 = icon3;
/*     */       }
/*     */     } 
/*     */     
/* 254 */     if (!buttonModel.isEnabled()) {
/* 255 */       if (buttonModel.isSelected()) {
/* 256 */         icon2 = abstractButton.getDisabledSelectedIcon();
/* 257 */         if (icon2 == null) {
/* 258 */           icon2 = icon3;
/*     */         }
/*     */       } 
/*     */       
/* 262 */       if (icon2 == null) {
/* 263 */         icon2 = abstractButton.getDisabledIcon();
/*     */       }
/* 265 */     } else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 266 */       icon2 = abstractButton.getPressedIcon();
/* 267 */       if (icon2 != null)
/*     */       {
/* 269 */         clearTextShiftOffset();
/*     */       }
/* 271 */     } else if (abstractButton.isRolloverEnabled() && buttonModel.isRollover()) {
/* 272 */       if (buttonModel.isSelected()) {
/* 273 */         icon2 = abstractButton.getRolloverSelectedIcon();
/* 274 */         if (icon2 == null) {
/* 275 */           icon2 = icon3;
/*     */         }
/*     */       } 
/*     */       
/* 279 */       if (icon2 == null) {
/* 280 */         icon2 = abstractButton.getRolloverIcon();
/*     */       }
/*     */     } 
/*     */     
/* 284 */     if (icon2 != null) {
/* 285 */       icon1 = icon2;
/*     */     }
/*     */     
/* 288 */     if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 289 */       icon1.paintIcon(paramJComponent, paramGraphics, paramRectangle.x + getTextShiftOffset(), paramRectangle.y + 
/* 290 */           getTextShiftOffset());
/*     */     } else {
/* 292 */       icon1.paintIcon(paramJComponent, paramGraphics, paramRectangle.x, paramRectangle.y);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintText(Graphics paramGraphics, JComponent paramJComponent, Rectangle paramRectangle, String paramString) {
/* 302 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 303 */     ButtonModel buttonModel = abstractButton.getModel();
/* 304 */     FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(paramJComponent, paramGraphics);
/* 305 */     int i = abstractButton.getDisplayedMnemonicIndex();
/*     */ 
/*     */     
/* 308 */     if (buttonModel.isEnabled()) {
/*     */       
/* 310 */       paramGraphics.setColor(abstractButton.getForeground());
/* 311 */       SwingUtilities2.drawStringUnderlineCharAt(paramJComponent, paramGraphics, paramString, i, paramRectangle.x + 
/* 312 */           getTextShiftOffset(), paramRectangle.y + fontMetrics
/* 313 */           .getAscent() + getTextShiftOffset());
/*     */     }
/*     */     else {
/*     */       
/* 317 */       paramGraphics.setColor(abstractButton.getBackground().brighter());
/* 318 */       SwingUtilities2.drawStringUnderlineCharAt(paramJComponent, paramGraphics, paramString, i, paramRectangle.x, paramRectangle.y + fontMetrics
/* 319 */           .getAscent());
/* 320 */       paramGraphics.setColor(abstractButton.getBackground().darker());
/* 321 */       SwingUtilities2.drawStringUnderlineCharAt(paramJComponent, paramGraphics, paramString, i, paramRectangle.x - 1, paramRectangle.y + fontMetrics
/* 322 */           .getAscent() - 1);
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
/*     */   protected void paintText(Graphics paramGraphics, AbstractButton paramAbstractButton, Rectangle paramRectangle, String paramString) {
/* 336 */     paintText(paramGraphics, paramAbstractButton, paramRectangle, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintFocus(Graphics paramGraphics, AbstractButton paramAbstractButton, Rectangle paramRectangle1, Rectangle paramRectangle2, Rectangle paramRectangle3) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintButtonPressed(Graphics paramGraphics, AbstractButton paramAbstractButton) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clearTextShiftOffset() {
/* 351 */     this.shiftOffset = 0;
/*     */   }
/*     */   
/*     */   protected void setTextShiftOffset() {
/* 355 */     this.shiftOffset = this.defaultTextShiftOffset;
/*     */   }
/*     */   
/*     */   protected int getTextShiftOffset() {
/* 359 */     return this.shiftOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 366 */     Dimension dimension = getPreferredSize(paramJComponent);
/* 367 */     View view = (View)paramJComponent.getClientProperty("html");
/* 368 */     if (view != null) {
/* 369 */       dimension.width = (int)(dimension.width - view.getPreferredSpan(0) - view.getMinimumSpan(0));
/*     */     }
/* 371 */     return dimension;
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 375 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 376 */     return BasicGraphicsUtils.getPreferredButtonSize(abstractButton, abstractButton.getIconTextGap());
/*     */   }
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 380 */     Dimension dimension = getPreferredSize(paramJComponent);
/* 381 */     View view = (View)paramJComponent.getClientProperty("html");
/* 382 */     if (view != null) {
/* 383 */       dimension.width = (int)(dimension.width + view.getMaximumSpan(0) - view.getPreferredSpan(0));
/*     */     }
/* 385 */     return dimension;
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
/*     */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/* 397 */     super.getBaseline(paramJComponent, paramInt1, paramInt2);
/* 398 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 399 */     String str = abstractButton.getText();
/* 400 */     if (str == null || "".equals(str)) {
/* 401 */       return -1;
/*     */     }
/* 403 */     FontMetrics fontMetrics = abstractButton.getFontMetrics(abstractButton.getFont());
/* 404 */     layout(abstractButton, fontMetrics, paramInt1, paramInt2);
/* 405 */     return BasicHTML.getBaseline(abstractButton, textRect.y, fontMetrics.getAscent(), textRect.width, textRect.height);
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
/*     */   public Component.BaselineResizeBehavior getBaselineResizeBehavior(JComponent paramJComponent) {
/* 419 */     super.getBaselineResizeBehavior(paramJComponent);
/* 420 */     if (paramJComponent.getClientProperty("html") != null) {
/* 421 */       return Component.BaselineResizeBehavior.OTHER;
/*     */     }
/* 423 */     switch (((AbstractButton)paramJComponent).getVerticalAlignment()) {
/*     */       case 1:
/* 425 */         return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
/*     */       case 3:
/* 427 */         return Component.BaselineResizeBehavior.CONSTANT_DESCENT;
/*     */       case 0:
/* 429 */         return Component.BaselineResizeBehavior.CENTER_OFFSET;
/*     */     } 
/* 431 */     return Component.BaselineResizeBehavior.OTHER;
/*     */   }
/*     */ 
/*     */   
/*     */   private String layout(AbstractButton paramAbstractButton, FontMetrics paramFontMetrics, int paramInt1, int paramInt2) {
/* 436 */     Insets insets = paramAbstractButton.getInsets();
/* 437 */     viewRect.x = insets.left;
/* 438 */     viewRect.y = insets.top;
/* 439 */     viewRect.width = paramInt1 - insets.right + viewRect.x;
/* 440 */     viewRect.height = paramInt2 - insets.bottom + viewRect.y;
/*     */     
/* 442 */     textRect.x = textRect.y = textRect.width = textRect.height = 0;
/* 443 */     iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;
/*     */ 
/*     */     
/* 446 */     return SwingUtilities.layoutCompoundLabel(paramAbstractButton, paramFontMetrics, paramAbstractButton
/* 447 */         .getText(), paramAbstractButton.getIcon(), paramAbstractButton
/* 448 */         .getVerticalAlignment(), paramAbstractButton.getHorizontalAlignment(), paramAbstractButton
/* 449 */         .getVerticalTextPosition(), paramAbstractButton.getHorizontalTextPosition(), viewRect, iconRect, textRect, 
/*     */         
/* 451 */         (paramAbstractButton.getText() == null) ? 0 : paramAbstractButton.getIconTextGap());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BasicButtonListener getButtonListener(AbstractButton paramAbstractButton) {
/* 459 */     MouseMotionListener[] arrayOfMouseMotionListener = paramAbstractButton.getMouseMotionListeners();
/*     */     
/* 461 */     if (arrayOfMouseMotionListener != null) {
/* 462 */       for (MouseMotionListener mouseMotionListener : arrayOfMouseMotionListener) {
/* 463 */         if (mouseMotionListener instanceof BasicButtonListener) {
/* 464 */           return (BasicButtonListener)mouseMotionListener;
/*     */         }
/*     */       } 
/*     */     }
/* 468 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicButtonUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */