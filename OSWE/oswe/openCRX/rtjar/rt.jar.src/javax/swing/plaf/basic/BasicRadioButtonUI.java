/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FocusTraversalPolicy;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.KeyboardFocusManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.DefaultButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
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
/*     */ public class BasicRadioButtonUI
/*     */   extends BasicToggleButtonUI
/*     */ {
/*  46 */   private static final Object BASIC_RADIO_BUTTON_UI_KEY = new Object();
/*     */ 
/*     */   
/*     */   protected Icon icon;
/*     */ 
/*     */   
/*     */   private boolean defaults_initialized = false;
/*     */ 
/*     */   
/*     */   private static final String propertyPrefix = "RadioButton.";
/*     */   
/*  57 */   private KeyListener keyListener = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  70 */     AppContext appContext = AppContext.getAppContext();
/*     */     
/*  72 */     BasicRadioButtonUI basicRadioButtonUI = (BasicRadioButtonUI)appContext.get(BASIC_RADIO_BUTTON_UI_KEY);
/*  73 */     if (basicRadioButtonUI == null) {
/*  74 */       basicRadioButtonUI = new BasicRadioButtonUI();
/*  75 */       appContext.put(BASIC_RADIO_BUTTON_UI_KEY, basicRadioButtonUI);
/*     */     } 
/*  77 */     return basicRadioButtonUI;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getPropertyPrefix() {
/*  82 */     return "RadioButton.";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults(AbstractButton paramAbstractButton) {
/*  90 */     super.installDefaults(paramAbstractButton);
/*  91 */     if (!this.defaults_initialized) {
/*  92 */       this.icon = UIManager.getIcon(getPropertyPrefix() + "icon");
/*  93 */       this.defaults_initialized = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults(AbstractButton paramAbstractButton) {
/* 102 */     super.uninstallDefaults(paramAbstractButton);
/* 103 */     this.defaults_initialized = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getDefaultIcon() {
/* 112 */     return this.icon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners(AbstractButton paramAbstractButton) {
/* 120 */     super.installListeners(paramAbstractButton);
/*     */ 
/*     */     
/* 123 */     if (!(paramAbstractButton instanceof JRadioButton)) {
/*     */       return;
/*     */     }
/* 126 */     this.keyListener = createKeyListener();
/* 127 */     paramAbstractButton.addKeyListener(this.keyListener);
/*     */ 
/*     */     
/* 130 */     paramAbstractButton.setFocusTraversalKeysEnabled(false);
/*     */ 
/*     */     
/* 133 */     paramAbstractButton.getActionMap().put("Previous", new SelectPreviousBtn());
/* 134 */     paramAbstractButton.getActionMap().put("Next", new SelectNextBtn());
/*     */     
/* 136 */     paramAbstractButton.getInputMap(1)
/* 137 */       .put(KeyStroke.getKeyStroke("UP"), "Previous");
/* 138 */     paramAbstractButton.getInputMap(1)
/* 139 */       .put(KeyStroke.getKeyStroke("DOWN"), "Next");
/* 140 */     paramAbstractButton.getInputMap(1)
/* 141 */       .put(KeyStroke.getKeyStroke("LEFT"), "Previous");
/* 142 */     paramAbstractButton.getInputMap(1)
/* 143 */       .put(KeyStroke.getKeyStroke("RIGHT"), "Next");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners(AbstractButton paramAbstractButton) {
/* 151 */     super.uninstallListeners(paramAbstractButton);
/*     */ 
/*     */     
/* 154 */     if (!(paramAbstractButton instanceof JRadioButton)) {
/*     */       return;
/*     */     }
/*     */     
/* 158 */     paramAbstractButton.getActionMap().remove("Previous");
/* 159 */     paramAbstractButton.getActionMap().remove("Next");
/* 160 */     paramAbstractButton.getInputMap(1)
/* 161 */       .remove(KeyStroke.getKeyStroke("UP"));
/* 162 */     paramAbstractButton.getInputMap(1)
/* 163 */       .remove(KeyStroke.getKeyStroke("DOWN"));
/* 164 */     paramAbstractButton.getInputMap(1)
/* 165 */       .remove(KeyStroke.getKeyStroke("LEFT"));
/* 166 */     paramAbstractButton.getInputMap(1)
/* 167 */       .remove(KeyStroke.getKeyStroke("RIGHT"));
/*     */     
/* 169 */     if (this.keyListener != null) {
/* 170 */       paramAbstractButton.removeKeyListener(this.keyListener);
/* 171 */       this.keyListener = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   private static Dimension size = new Dimension();
/* 182 */   private static Rectangle viewRect = new Rectangle();
/* 183 */   private static Rectangle iconRect = new Rectangle();
/* 184 */   private static Rectangle textRect = new Rectangle();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 191 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 192 */     ButtonModel buttonModel = abstractButton.getModel();
/*     */     
/* 194 */     Font font = paramJComponent.getFont();
/* 195 */     paramGraphics.setFont(font);
/* 196 */     FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(paramJComponent, paramGraphics, font);
/*     */     
/* 198 */     Insets insets = paramJComponent.getInsets();
/* 199 */     size = abstractButton.getSize(size);
/* 200 */     viewRect.x = insets.left;
/* 201 */     viewRect.y = insets.top;
/* 202 */     viewRect.width = size.width - insets.right + viewRect.x;
/* 203 */     viewRect.height = size.height - insets.bottom + viewRect.y;
/* 204 */     iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;
/* 205 */     textRect.x = textRect.y = textRect.width = textRect.height = 0;
/*     */     
/* 207 */     Icon icon = abstractButton.getIcon();
/* 208 */     Object object1 = null;
/* 209 */     Object object2 = null;
/*     */     
/* 211 */     String str = SwingUtilities.layoutCompoundLabel(paramJComponent, fontMetrics, abstractButton
/* 212 */         .getText(), (icon != null) ? icon : getDefaultIcon(), abstractButton
/* 213 */         .getVerticalAlignment(), abstractButton.getHorizontalAlignment(), abstractButton
/* 214 */         .getVerticalTextPosition(), abstractButton.getHorizontalTextPosition(), viewRect, iconRect, textRect, 
/*     */         
/* 216 */         (abstractButton.getText() == null) ? 0 : abstractButton.getIconTextGap());
/*     */ 
/*     */     
/* 219 */     if (paramJComponent.isOpaque()) {
/* 220 */       paramGraphics.setColor(abstractButton.getBackground());
/* 221 */       paramGraphics.fillRect(0, 0, size.width, size.height);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 226 */     if (icon != null) {
/*     */       
/* 228 */       if (!buttonModel.isEnabled()) {
/* 229 */         if (buttonModel.isSelected()) {
/* 230 */           icon = abstractButton.getDisabledSelectedIcon();
/*     */         } else {
/* 232 */           icon = abstractButton.getDisabledIcon();
/*     */         } 
/* 234 */       } else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 235 */         icon = abstractButton.getPressedIcon();
/* 236 */         if (icon == null)
/*     */         {
/* 238 */           icon = abstractButton.getSelectedIcon();
/*     */         }
/* 240 */       } else if (buttonModel.isSelected()) {
/* 241 */         if (abstractButton.isRolloverEnabled() && buttonModel.isRollover()) {
/* 242 */           icon = abstractButton.getRolloverSelectedIcon();
/* 243 */           if (icon == null) {
/* 244 */             icon = abstractButton.getSelectedIcon();
/*     */           }
/*     */         } else {
/* 247 */           icon = abstractButton.getSelectedIcon();
/*     */         } 
/* 249 */       } else if (abstractButton.isRolloverEnabled() && buttonModel.isRollover()) {
/* 250 */         icon = abstractButton.getRolloverIcon();
/*     */       } 
/*     */       
/* 253 */       if (icon == null) {
/* 254 */         icon = abstractButton.getIcon();
/*     */       }
/*     */       
/* 257 */       icon.paintIcon(paramJComponent, paramGraphics, iconRect.x, iconRect.y);
/*     */     } else {
/*     */       
/* 260 */       getDefaultIcon().paintIcon(paramJComponent, paramGraphics, iconRect.x, iconRect.y);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 265 */     if (str != null) {
/* 266 */       View view = (View)paramJComponent.getClientProperty("html");
/* 267 */       if (view != null) {
/* 268 */         view.paint(paramGraphics, textRect);
/*     */       } else {
/* 270 */         paintText(paramGraphics, abstractButton, textRect, str);
/*     */       } 
/* 272 */       if (abstractButton.hasFocus() && abstractButton.isFocusPainted() && textRect.width > 0 && textRect.height > 0)
/*     */       {
/* 274 */         paintFocus(paramGraphics, textRect, size);
/*     */       }
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
/*     */   protected void paintFocus(Graphics paramGraphics, Rectangle paramRectangle, Dimension paramDimension) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 296 */   private static Rectangle prefViewRect = new Rectangle();
/* 297 */   private static Rectangle prefIconRect = new Rectangle();
/* 298 */   private static Rectangle prefTextRect = new Rectangle();
/* 299 */   private static Insets prefInsets = new Insets(0, 0, 0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 306 */     if (paramJComponent.getComponentCount() > 0) {
/* 307 */       return null;
/*     */     }
/*     */     
/* 310 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/*     */     
/* 312 */     String str = abstractButton.getText();
/*     */     
/* 314 */     Icon icon = abstractButton.getIcon();
/* 315 */     if (icon == null) {
/* 316 */       icon = getDefaultIcon();
/*     */     }
/*     */     
/* 319 */     Font font = abstractButton.getFont();
/* 320 */     FontMetrics fontMetrics = abstractButton.getFontMetrics(font);
/*     */     
/* 322 */     prefViewRect.x = prefViewRect.y = 0;
/* 323 */     prefViewRect.width = 32767;
/* 324 */     prefViewRect.height = 32767;
/* 325 */     prefIconRect.x = prefIconRect.y = prefIconRect.width = prefIconRect.height = 0;
/* 326 */     prefTextRect.x = prefTextRect.y = prefTextRect.width = prefTextRect.height = 0;
/*     */     
/* 328 */     SwingUtilities.layoutCompoundLabel(paramJComponent, fontMetrics, str, icon, abstractButton
/*     */         
/* 330 */         .getVerticalAlignment(), abstractButton.getHorizontalAlignment(), abstractButton
/* 331 */         .getVerticalTextPosition(), abstractButton.getHorizontalTextPosition(), prefViewRect, prefIconRect, prefTextRect, (str == null) ? 0 : abstractButton
/*     */         
/* 333 */         .getIconTextGap());
/*     */ 
/*     */     
/* 336 */     int i = Math.min(prefIconRect.x, prefTextRect.x);
/* 337 */     int j = Math.max(prefIconRect.x + prefIconRect.width, prefTextRect.x + prefTextRect.width);
/*     */     
/* 339 */     int k = Math.min(prefIconRect.y, prefTextRect.y);
/* 340 */     int m = Math.max(prefIconRect.y + prefIconRect.height, prefTextRect.y + prefTextRect.height);
/*     */     
/* 342 */     int n = j - i;
/* 343 */     int i1 = m - k;
/*     */     
/* 345 */     prefInsets = abstractButton.getInsets(prefInsets);
/* 346 */     n += prefInsets.left + prefInsets.right;
/* 347 */     i1 += prefInsets.top + prefInsets.bottom;
/* 348 */     return new Dimension(n, i1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private KeyListener createKeyListener() {
/* 356 */     if (this.keyListener == null) {
/* 357 */       this.keyListener = new KeyHandler();
/*     */     }
/* 359 */     return this.keyListener;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidRadioButtonObj(Object paramObject) {
/* 364 */     return (paramObject instanceof JRadioButton && ((JRadioButton)paramObject)
/* 365 */       .isVisible() && ((JRadioButton)paramObject)
/* 366 */       .isEnabled());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void selectRadioButton(ActionEvent paramActionEvent, boolean paramBoolean) {
/* 377 */     Object object = paramActionEvent.getSource();
/*     */ 
/*     */     
/* 380 */     if (!isValidRadioButtonObj(object)) {
/*     */       return;
/*     */     }
/* 383 */     ButtonGroupInfo buttonGroupInfo = new ButtonGroupInfo((JRadioButton)object);
/* 384 */     buttonGroupInfo.selectNewButton(paramBoolean);
/*     */   }
/*     */   
/*     */   private class SelectPreviousBtn
/*     */     extends AbstractAction
/*     */   {
/*     */     public SelectPreviousBtn() {
/* 391 */       super("Previous");
/*     */     }
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 395 */       BasicRadioButtonUI.this.selectRadioButton(param1ActionEvent, false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SelectNextBtn
/*     */     extends AbstractAction {
/*     */     public SelectNextBtn() {
/* 402 */       super("Next");
/*     */     }
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 406 */       BasicRadioButtonUI.this.selectRadioButton(param1ActionEvent, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class ButtonGroupInfo
/*     */   {
/* 416 */     JRadioButton activeBtn = null;
/*     */     
/* 418 */     JRadioButton firstBtn = null;
/* 419 */     JRadioButton lastBtn = null;
/*     */     
/* 421 */     JRadioButton previousBtn = null;
/* 422 */     JRadioButton nextBtn = null;
/*     */     
/* 424 */     HashSet<JRadioButton> btnsInGroup = null;
/*     */     boolean srcFound = false;
/*     */     
/*     */     public ButtonGroupInfo(JRadioButton param1JRadioButton) {
/* 428 */       this.activeBtn = param1JRadioButton;
/* 429 */       this.btnsInGroup = new HashSet<>();
/*     */     }
/*     */ 
/*     */     
/*     */     boolean containsInGroup(Object param1Object) {
/* 434 */       return this.btnsInGroup.contains(param1Object);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Component getFocusTransferBaseComponent(boolean param1Boolean) {
/* 440 */       JRadioButton jRadioButton = this.activeBtn;
/* 441 */       Container container = jRadioButton.getFocusCycleRootAncestor();
/* 442 */       if (container != null) {
/* 443 */         FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy();
/*     */         
/* 445 */         Component component = param1Boolean ? focusTraversalPolicy.getComponentAfter(container, this.activeBtn) : focusTraversalPolicy.getComponentBefore(container, this.activeBtn);
/*     */ 
/*     */ 
/*     */         
/* 449 */         if (containsInGroup(component)) {
/* 450 */           jRadioButton = param1Boolean ? this.lastBtn : this.firstBtn;
/*     */         }
/*     */       } 
/*     */       
/* 454 */       return jRadioButton;
/*     */     }
/*     */     
/*     */     boolean getButtonGroupInfo() {
/* 458 */       if (this.activeBtn == null) {
/* 459 */         return false;
/*     */       }
/* 461 */       this.btnsInGroup.clear();
/*     */ 
/*     */       
/* 464 */       ButtonModel buttonModel = this.activeBtn.getModel();
/* 465 */       if (!(buttonModel instanceof DefaultButtonModel)) {
/* 466 */         return false;
/*     */       }
/*     */       
/* 469 */       DefaultButtonModel defaultButtonModel = (DefaultButtonModel)buttonModel;
/*     */ 
/*     */       
/* 472 */       ButtonGroup buttonGroup = defaultButtonModel.getGroup();
/* 473 */       if (buttonGroup == null) {
/* 474 */         return false;
/*     */       }
/*     */       
/* 477 */       Enumeration<AbstractButton> enumeration = buttonGroup.getElements();
/* 478 */       if (enumeration == null) {
/* 479 */         return false;
/*     */       }
/* 481 */       while (enumeration.hasMoreElements()) {
/* 482 */         AbstractButton abstractButton = enumeration.nextElement();
/* 483 */         if (!BasicRadioButtonUI.this.isValidRadioButtonObj(abstractButton)) {
/*     */           continue;
/*     */         }
/* 486 */         this.btnsInGroup.add((JRadioButton)abstractButton);
/*     */ 
/*     */         
/* 489 */         if (null == this.firstBtn) {
/* 490 */           this.firstBtn = (JRadioButton)abstractButton;
/*     */         }
/* 492 */         if (this.activeBtn == abstractButton) {
/* 493 */           this.srcFound = true;
/* 494 */         } else if (!this.srcFound) {
/*     */ 
/*     */           
/* 497 */           this.previousBtn = (JRadioButton)abstractButton;
/* 498 */         } else if (this.nextBtn == null) {
/*     */ 
/*     */           
/* 501 */           this.nextBtn = (JRadioButton)abstractButton;
/*     */         } 
/*     */ 
/*     */         
/* 505 */         this.lastBtn = (JRadioButton)abstractButton;
/*     */       } 
/*     */       
/* 508 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void selectNewButton(boolean param1Boolean) {
/* 518 */       if (!getButtonGroupInfo()) {
/*     */         return;
/*     */       }
/* 521 */       if (this.srcFound) {
/* 522 */         JRadioButton jRadioButton = null;
/* 523 */         if (param1Boolean) {
/*     */ 
/*     */           
/* 526 */           jRadioButton = (null == this.nextBtn) ? this.firstBtn : this.nextBtn;
/*     */         }
/*     */         else {
/*     */           
/* 530 */           jRadioButton = (null == this.previousBtn) ? this.lastBtn : this.previousBtn;
/*     */         } 
/* 532 */         if (jRadioButton != null && jRadioButton != this.activeBtn) {
/*     */           
/* 534 */           jRadioButton.requestFocusInWindow();
/* 535 */           jRadioButton.setSelected(true);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void jumpToNextComponent(boolean param1Boolean) {
/* 548 */       if (!getButtonGroupInfo())
/*     */       {
/*     */         
/* 551 */         if (this.activeBtn != null) {
/* 552 */           this.lastBtn = this.activeBtn;
/* 553 */           this.firstBtn = this.activeBtn;
/*     */         } else {
/*     */           return;
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 561 */       JRadioButton jRadioButton = this.activeBtn;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 567 */       Component component = getFocusTransferBaseComponent(param1Boolean);
/* 568 */       if (component != null) {
/* 569 */         if (param1Boolean) {
/*     */           
/* 571 */           KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent(component);
/*     */         } else {
/*     */           
/* 574 */           KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent(component);
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class KeyHandler
/*     */     implements KeyListener
/*     */   {
/*     */     private KeyHandler() {}
/*     */ 
/*     */     
/*     */     public void keyPressed(KeyEvent param1KeyEvent) {
/* 589 */       if (param1KeyEvent.getKeyCode() == 9) {
/*     */         
/* 591 */         Object object = param1KeyEvent.getSource();
/*     */ 
/*     */         
/* 594 */         if (BasicRadioButtonUI.this.isValidRadioButtonObj(object)) {
/* 595 */           param1KeyEvent.consume();
/* 596 */           BasicRadioButtonUI.ButtonGroupInfo buttonGroupInfo = new BasicRadioButtonUI.ButtonGroupInfo((JRadioButton)object);
/* 597 */           buttonGroupInfo.jumpToNextComponent(!param1KeyEvent.isShiftDown());
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void keyReleased(KeyEvent param1KeyEvent) {}
/*     */     
/*     */     public void keyTyped(KeyEvent param1KeyEvent) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicRadioButtonUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */