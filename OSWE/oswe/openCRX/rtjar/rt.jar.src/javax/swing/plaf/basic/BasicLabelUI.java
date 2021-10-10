/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.plaf.ComponentInputMapUIResource;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.InputMapUIResource;
/*     */ import javax.swing.plaf.LabelUI;
/*     */ import javax.swing.text.View;
/*     */ import sun.awt.AppContext;
/*     */ import sun.swing.SwingUtilities2;
/*     */ import sun.swing.UIAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicLabelUI
/*     */   extends LabelUI
/*     */   implements PropertyChangeListener
/*     */ {
/*  67 */   protected static BasicLabelUI labelUI = new BasicLabelUI();
/*  68 */   private static final Object BASIC_LABEL_UI_KEY = new Object();
/*     */   
/*  70 */   private Rectangle paintIconR = new Rectangle();
/*  71 */   private Rectangle paintTextR = new Rectangle();
/*     */   
/*     */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*  74 */     paramLazyActionMap.put(new Actions("press"));
/*  75 */     paramLazyActionMap.put(new Actions("release"));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String layoutCL(JLabel paramJLabel, FontMetrics paramFontMetrics, String paramString, Icon paramIcon, Rectangle paramRectangle1, Rectangle paramRectangle2, Rectangle paramRectangle3) {
/* 102 */     return SwingUtilities.layoutCompoundLabel(paramJLabel, paramFontMetrics, paramString, paramIcon, paramJLabel
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 107 */         .getVerticalAlignment(), paramJLabel
/* 108 */         .getHorizontalAlignment(), paramJLabel
/* 109 */         .getVerticalTextPosition(), paramJLabel
/* 110 */         .getHorizontalTextPosition(), paramRectangle1, paramRectangle2, paramRectangle3, paramJLabel
/*     */ 
/*     */ 
/*     */         
/* 114 */         .getIconTextGap());
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
/*     */   protected void paintEnabledText(JLabel paramJLabel, Graphics paramGraphics, String paramString, int paramInt1, int paramInt2) {
/* 130 */     int i = paramJLabel.getDisplayedMnemonicIndex();
/* 131 */     paramGraphics.setColor(paramJLabel.getForeground());
/* 132 */     SwingUtilities2.drawStringUnderlineCharAt(paramJLabel, paramGraphics, paramString, i, paramInt1, paramInt2);
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
/*     */   protected void paintDisabledText(JLabel paramJLabel, Graphics paramGraphics, String paramString, int paramInt1, int paramInt2) {
/* 151 */     int i = paramJLabel.getDisplayedMnemonicIndex();
/* 152 */     Color color = paramJLabel.getBackground();
/* 153 */     paramGraphics.setColor(color.brighter());
/* 154 */     SwingUtilities2.drawStringUnderlineCharAt(paramJLabel, paramGraphics, paramString, i, paramInt1 + 1, paramInt2 + 1);
/*     */     
/* 156 */     paramGraphics.setColor(color.darker());
/* 157 */     SwingUtilities2.drawStringUnderlineCharAt(paramJLabel, paramGraphics, paramString, i, paramInt1, paramInt2);
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
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 173 */     JLabel jLabel = (JLabel)paramJComponent;
/* 174 */     String str1 = jLabel.getText();
/* 175 */     Icon icon = jLabel.isEnabled() ? jLabel.getIcon() : jLabel.getDisabledIcon();
/*     */     
/* 177 */     if (icon == null && str1 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 181 */     FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(jLabel, paramGraphics);
/* 182 */     String str2 = layout(jLabel, fontMetrics, paramJComponent.getWidth(), paramJComponent.getHeight());
/*     */     
/* 184 */     if (icon != null) {
/* 185 */       icon.paintIcon(paramJComponent, paramGraphics, this.paintIconR.x, this.paintIconR.y);
/*     */     }
/*     */     
/* 188 */     if (str1 != null) {
/* 189 */       View view = (View)paramJComponent.getClientProperty("html");
/* 190 */       if (view != null) {
/* 191 */         view.paint(paramGraphics, this.paintTextR);
/*     */       } else {
/* 193 */         int i = this.paintTextR.x;
/* 194 */         int j = this.paintTextR.y + fontMetrics.getAscent();
/*     */         
/* 196 */         if (jLabel.isEnabled()) {
/* 197 */           paintEnabledText(jLabel, paramGraphics, str2, i, j);
/*     */         } else {
/*     */           
/* 200 */           paintDisabledText(jLabel, paramGraphics, str2, i, j);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String layout(JLabel paramJLabel, FontMetrics paramFontMetrics, int paramInt1, int paramInt2) {
/* 208 */     Insets insets = paramJLabel.getInsets((Insets)null);
/* 209 */     String str = paramJLabel.getText();
/*     */     
/* 211 */     Icon icon = paramJLabel.isEnabled() ? paramJLabel.getIcon() : paramJLabel.getDisabledIcon();
/* 212 */     Rectangle rectangle = new Rectangle();
/* 213 */     rectangle.x = insets.left;
/* 214 */     rectangle.y = insets.top;
/* 215 */     rectangle.width = paramInt1 - insets.left + insets.right;
/* 216 */     rectangle.height = paramInt2 - insets.top + insets.bottom;
/* 217 */     this.paintIconR.x = this.paintIconR.y = this.paintIconR.width = this.paintIconR.height = 0;
/* 218 */     this.paintTextR.x = this.paintTextR.y = this.paintTextR.width = this.paintTextR.height = 0;
/* 219 */     return layoutCL(paramJLabel, paramFontMetrics, str, icon, rectangle, this.paintIconR, this.paintTextR);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 225 */     JLabel jLabel = (JLabel)paramJComponent;
/* 226 */     String str = jLabel.getText();
/*     */     
/* 228 */     Icon icon = jLabel.isEnabled() ? jLabel.getIcon() : jLabel.getDisabledIcon();
/* 229 */     Insets insets = jLabel.getInsets((Insets)null);
/* 230 */     Font font = jLabel.getFont();
/*     */     
/* 232 */     int i = insets.left + insets.right;
/* 233 */     int j = insets.top + insets.bottom;
/*     */     
/* 235 */     if (icon == null && (str == null || (str != null && font == null)))
/*     */     {
/*     */       
/* 238 */       return new Dimension(i, j);
/*     */     }
/* 240 */     if (str == null || (icon != null && font == null)) {
/* 241 */       return new Dimension(icon.getIconWidth() + i, icon
/* 242 */           .getIconHeight() + j);
/*     */     }
/*     */     
/* 245 */     FontMetrics fontMetrics = jLabel.getFontMetrics(font);
/* 246 */     Rectangle rectangle1 = new Rectangle();
/* 247 */     Rectangle rectangle2 = new Rectangle();
/* 248 */     Rectangle rectangle3 = new Rectangle();
/*     */     
/* 250 */     rectangle1.x = rectangle1.y = rectangle1.width = rectangle1.height = 0;
/* 251 */     rectangle2.x = rectangle2.y = rectangle2.width = rectangle2.height = 0;
/* 252 */     rectangle3.x = i;
/* 253 */     rectangle3.y = j;
/* 254 */     rectangle3.width = rectangle3.height = 32767;
/*     */     
/* 256 */     layoutCL(jLabel, fontMetrics, str, icon, rectangle3, rectangle1, rectangle2);
/* 257 */     int k = Math.min(rectangle1.x, rectangle2.x);
/* 258 */     int m = Math.max(rectangle1.x + rectangle1.width, rectangle2.x + rectangle2.width);
/* 259 */     int n = Math.min(rectangle1.y, rectangle2.y);
/* 260 */     int i1 = Math.max(rectangle1.y + rectangle1.height, rectangle2.y + rectangle2.height);
/* 261 */     Dimension dimension = new Dimension(m - k, i1 - n);
/*     */     
/* 263 */     dimension.width += i;
/* 264 */     dimension.height += j;
/* 265 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 274 */     Dimension dimension = getPreferredSize(paramJComponent);
/* 275 */     View view = (View)paramJComponent.getClientProperty("html");
/* 276 */     if (view != null) {
/* 277 */       dimension.width = (int)(dimension.width - view.getPreferredSpan(0) - view.getMinimumSpan(0));
/*     */     }
/* 279 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 286 */     Dimension dimension = getPreferredSize(paramJComponent);
/* 287 */     View view = (View)paramJComponent.getClientProperty("html");
/* 288 */     if (view != null) {
/* 289 */       dimension.width = (int)(dimension.width + view.getMaximumSpan(0) - view.getPreferredSpan(0));
/*     */     }
/* 291 */     return dimension;
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
/* 303 */     super.getBaseline(paramJComponent, paramInt1, paramInt2);
/* 304 */     JLabel jLabel = (JLabel)paramJComponent;
/* 305 */     String str = jLabel.getText();
/* 306 */     if (str == null || "".equals(str) || jLabel.getFont() == null) {
/* 307 */       return -1;
/*     */     }
/* 309 */     FontMetrics fontMetrics = jLabel.getFontMetrics(jLabel.getFont());
/* 310 */     layout(jLabel, fontMetrics, paramInt1, paramInt2);
/* 311 */     return BasicHTML.getBaseline(jLabel, this.paintTextR.y, fontMetrics.getAscent(), this.paintTextR.width, this.paintTextR.height);
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
/* 325 */     super.getBaselineResizeBehavior(paramJComponent);
/* 326 */     if (paramJComponent.getClientProperty("html") != null) {
/* 327 */       return Component.BaselineResizeBehavior.OTHER;
/*     */     }
/* 329 */     switch (((JLabel)paramJComponent).getVerticalAlignment()) {
/*     */       case 1:
/* 331 */         return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
/*     */       case 3:
/* 333 */         return Component.BaselineResizeBehavior.CONSTANT_DESCENT;
/*     */       case 0:
/* 335 */         return Component.BaselineResizeBehavior.CENTER_OFFSET;
/*     */     } 
/* 337 */     return Component.BaselineResizeBehavior.OTHER;
/*     */   }
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 342 */     installDefaults((JLabel)paramJComponent);
/* 343 */     installComponents((JLabel)paramJComponent);
/* 344 */     installListeners((JLabel)paramJComponent);
/* 345 */     installKeyboardActions((JLabel)paramJComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 350 */     uninstallDefaults((JLabel)paramJComponent);
/* 351 */     uninstallComponents((JLabel)paramJComponent);
/* 352 */     uninstallListeners((JLabel)paramJComponent);
/* 353 */     uninstallKeyboardActions((JLabel)paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults(JLabel paramJLabel) {
/* 362 */     LookAndFeel.installColorsAndFont(paramJLabel, "Label.background", "Label.foreground", "Label.font");
/* 363 */     LookAndFeel.installProperty(paramJLabel, "opaque", Boolean.FALSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners(JLabel paramJLabel) {
/* 372 */     paramJLabel.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installComponents(JLabel paramJLabel) {
/* 381 */     BasicHTML.updateRenderer(paramJLabel, paramJLabel.getText());
/* 382 */     paramJLabel.setInheritsPopupMenu(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installKeyboardActions(JLabel paramJLabel) {
/* 391 */     int i = paramJLabel.getDisplayedMnemonic();
/* 392 */     Component component = paramJLabel.getLabelFor();
/* 393 */     if (i != 0 && component != null) {
/* 394 */       LazyActionMap.installLazyActionMap(paramJLabel, BasicLabelUI.class, "Label.actionMap");
/*     */ 
/*     */       
/* 397 */       InputMap inputMap = SwingUtilities.getUIInputMap(paramJLabel, 2);
/* 398 */       if (inputMap == null) {
/* 399 */         inputMap = new ComponentInputMapUIResource(paramJLabel);
/* 400 */         SwingUtilities.replaceUIInputMap(paramJLabel, 2, inputMap);
/*     */       } 
/*     */       
/* 403 */       inputMap.clear();
/* 404 */       inputMap.put(KeyStroke.getKeyStroke(i, BasicLookAndFeel.getFocusAcceleratorKeyMask(), false), "press");
/*     */     }
/*     */     else {
/*     */       
/* 408 */       InputMap inputMap = SwingUtilities.getUIInputMap(paramJLabel, 2);
/* 409 */       if (inputMap != null) {
/* 410 */         inputMap.clear();
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
/*     */   protected void uninstallDefaults(JLabel paramJLabel) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners(JLabel paramJLabel) {
/* 429 */     paramJLabel.removePropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallComponents(JLabel paramJLabel) {
/* 438 */     BasicHTML.updateRenderer(paramJLabel, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallKeyboardActions(JLabel paramJLabel) {
/* 447 */     SwingUtilities.replaceUIInputMap(paramJLabel, 0, null);
/* 448 */     SwingUtilities.replaceUIInputMap(paramJLabel, 2, null);
/*     */     
/* 450 */     SwingUtilities.replaceUIActionMap(paramJLabel, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 460 */     if (System.getSecurityManager() != null) {
/* 461 */       AppContext appContext = AppContext.getAppContext();
/*     */       
/* 463 */       BasicLabelUI basicLabelUI = (BasicLabelUI)appContext.get(BASIC_LABEL_UI_KEY);
/* 464 */       if (basicLabelUI == null) {
/* 465 */         basicLabelUI = new BasicLabelUI();
/* 466 */         appContext.put(BASIC_LABEL_UI_KEY, basicLabelUI);
/*     */       } 
/* 468 */       return basicLabelUI;
/*     */     } 
/* 470 */     return labelUI;
/*     */   }
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 474 */     String str = paramPropertyChangeEvent.getPropertyName();
/* 475 */     if (str == "text" || "font" == str || "foreground" == str) {
/*     */ 
/*     */ 
/*     */       
/* 479 */       JLabel jLabel = (JLabel)paramPropertyChangeEvent.getSource();
/* 480 */       String str1 = jLabel.getText();
/* 481 */       BasicHTML.updateRenderer(jLabel, str1);
/*     */     }
/* 483 */     else if (str == "labelFor" || str == "displayedMnemonic") {
/* 484 */       installKeyboardActions((JLabel)paramPropertyChangeEvent.getSource());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Actions
/*     */     extends UIAction
/*     */   {
/*     */     private static final String PRESS = "press";
/*     */     
/*     */     private static final String RELEASE = "release";
/*     */     
/*     */     Actions(String param1String) {
/* 497 */       super(param1String);
/*     */     }
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 501 */       JLabel jLabel = (JLabel)param1ActionEvent.getSource();
/* 502 */       String str = getName();
/* 503 */       if (str == "press") {
/* 504 */         doPress(jLabel);
/*     */       }
/* 506 */       else if (str == "release") {
/* 507 */         doRelease(jLabel, (param1ActionEvent.getActionCommand() != null));
/*     */       } 
/*     */     }
/*     */     
/*     */     private void doPress(JLabel param1JLabel) {
/* 512 */       Component component = param1JLabel.getLabelFor();
/* 513 */       if (component != null && component.isEnabled()) {
/* 514 */         InputMap inputMap = SwingUtilities.getUIInputMap(param1JLabel, 0);
/* 515 */         if (inputMap == null) {
/* 516 */           inputMap = new InputMapUIResource();
/* 517 */           SwingUtilities.replaceUIInputMap(param1JLabel, 0, inputMap);
/*     */         } 
/* 519 */         int i = param1JLabel.getDisplayedMnemonic();
/* 520 */         putOnRelease(inputMap, i, 
/* 521 */             BasicLookAndFeel.getFocusAcceleratorKeyMask());
/*     */         
/* 523 */         putOnRelease(inputMap, i, 0);
/*     */         
/* 525 */         putOnRelease(inputMap, 18, 0);
/* 526 */         param1JLabel.requestFocus();
/*     */       } 
/*     */     }
/*     */     
/*     */     private void doRelease(JLabel param1JLabel, boolean param1Boolean) {
/* 531 */       Component component = param1JLabel.getLabelFor();
/* 532 */       if (component != null && component.isEnabled()) {
/* 533 */         if (param1JLabel.hasFocus()) {
/* 534 */           InputMap inputMap = SwingUtilities.getUIInputMap(param1JLabel, 0);
/*     */           
/* 536 */           if (inputMap != null) {
/*     */             
/* 538 */             int j = param1JLabel.getDisplayedMnemonic();
/* 539 */             removeOnRelease(inputMap, j, 
/* 540 */                 BasicLookAndFeel.getFocusAcceleratorKeyMask());
/* 541 */             removeOnRelease(inputMap, j, 0);
/* 542 */             removeOnRelease(inputMap, 18, 0);
/*     */           } 
/* 544 */           inputMap = SwingUtilities.getUIInputMap(param1JLabel, 2);
/*     */           
/* 546 */           if (inputMap == null) {
/* 547 */             inputMap = new InputMapUIResource();
/* 548 */             SwingUtilities.replaceUIInputMap(param1JLabel, 2, inputMap);
/*     */           } 
/*     */           
/* 551 */           int i = param1JLabel.getDisplayedMnemonic();
/* 552 */           if (param1Boolean) {
/* 553 */             putOnRelease(inputMap, 18, 0);
/*     */           } else {
/* 555 */             putOnRelease(inputMap, i, 
/* 556 */                 BasicLookAndFeel.getFocusAcceleratorKeyMask());
/*     */             
/* 558 */             putOnRelease(inputMap, i, 0);
/*     */           } 
/* 560 */           if (component instanceof Container && ((Container)component)
/* 561 */             .isFocusCycleRoot()) {
/* 562 */             component.requestFocus();
/*     */           } else {
/* 564 */             SwingUtilities2.compositeRequestFocus(component);
/*     */           } 
/*     */         } else {
/* 567 */           InputMap inputMap = SwingUtilities.getUIInputMap(param1JLabel, 2);
/*     */           
/* 569 */           int i = param1JLabel.getDisplayedMnemonic();
/* 570 */           if (inputMap != null) {
/* 571 */             if (param1Boolean) {
/* 572 */               removeOnRelease(inputMap, i, 
/* 573 */                   BasicLookAndFeel.getFocusAcceleratorKeyMask());
/* 574 */               removeOnRelease(inputMap, i, 0);
/*     */             } else {
/* 576 */               removeOnRelease(inputMap, 18, 0);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*     */     private void putOnRelease(InputMap param1InputMap, int param1Int1, int param1Int2) {
/* 584 */       param1InputMap.put(KeyStroke.getKeyStroke(param1Int1, param1Int2, true), "release");
/*     */     }
/*     */ 
/*     */     
/*     */     private void removeOnRelease(InputMap param1InputMap, int param1Int1, int param1Int2) {
/* 589 */       param1InputMap.remove(KeyStroke.getKeyStroke(param1Int1, param1Int2, true));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicLabelUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */