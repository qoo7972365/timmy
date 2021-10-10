/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JCheckBoxMenuItem;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JRadioButtonMenuItem;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ButtonUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import sun.swing.MenuItemCheckIconFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsIconFactory
/*     */   implements Serializable
/*     */ {
/*     */   private static Icon frame_closeIcon;
/*     */   private static Icon frame_iconifyIcon;
/*     */   private static Icon frame_maxIcon;
/*     */   private static Icon frame_minIcon;
/*     */   private static Icon frame_resizeIcon;
/*     */   private static Icon checkBoxIcon;
/*     */   private static Icon radioButtonIcon;
/*     */   private static Icon checkBoxMenuItemIcon;
/*     */   private static Icon radioButtonMenuItemIcon;
/*     */   private static Icon menuItemCheckIcon;
/*     */   private static Icon menuItemArrowIcon;
/*     */   private static Icon menuArrowIcon;
/*     */   private static VistaMenuItemCheckIconFactory menuItemCheckIconFactory;
/*     */   
/*     */   public static Icon getMenuItemCheckIcon() {
/*  71 */     if (menuItemCheckIcon == null) {
/*  72 */       menuItemCheckIcon = new MenuItemCheckIcon();
/*     */     }
/*  74 */     return menuItemCheckIcon;
/*     */   }
/*     */   
/*     */   public static Icon getMenuItemArrowIcon() {
/*  78 */     if (menuItemArrowIcon == null) {
/*  79 */       menuItemArrowIcon = new MenuItemArrowIcon();
/*     */     }
/*  81 */     return menuItemArrowIcon;
/*     */   }
/*     */   
/*     */   public static Icon getMenuArrowIcon() {
/*  85 */     if (menuArrowIcon == null) {
/*  86 */       menuArrowIcon = new MenuArrowIcon();
/*     */     }
/*  88 */     return menuArrowIcon;
/*     */   }
/*     */   
/*     */   public static Icon getCheckBoxIcon() {
/*  92 */     if (checkBoxIcon == null) {
/*  93 */       checkBoxIcon = new CheckBoxIcon();
/*     */     }
/*  95 */     return checkBoxIcon;
/*     */   }
/*     */   
/*     */   public static Icon getRadioButtonIcon() {
/*  99 */     if (radioButtonIcon == null) {
/* 100 */       radioButtonIcon = new RadioButtonIcon();
/*     */     }
/* 102 */     return radioButtonIcon;
/*     */   }
/*     */   
/*     */   public static Icon getCheckBoxMenuItemIcon() {
/* 106 */     if (checkBoxMenuItemIcon == null) {
/* 107 */       checkBoxMenuItemIcon = new CheckBoxMenuItemIcon();
/*     */     }
/* 109 */     return checkBoxMenuItemIcon;
/*     */   }
/*     */   
/*     */   public static Icon getRadioButtonMenuItemIcon() {
/* 113 */     if (radioButtonMenuItemIcon == null) {
/* 114 */       radioButtonMenuItemIcon = new RadioButtonMenuItemIcon();
/*     */     }
/* 116 */     return radioButtonMenuItemIcon;
/*     */   }
/*     */ 
/*     */   
/*     */   static synchronized VistaMenuItemCheckIconFactory getMenuItemCheckIconFactory() {
/* 121 */     if (menuItemCheckIconFactory == null) {
/* 122 */       menuItemCheckIconFactory = new VistaMenuItemCheckIconFactory();
/*     */     }
/*     */     
/* 125 */     return menuItemCheckIconFactory;
/*     */   }
/*     */   
/*     */   public static Icon createFrameCloseIcon() {
/* 129 */     if (frame_closeIcon == null) {
/* 130 */       frame_closeIcon = new FrameButtonIcon(TMSchema.Part.WP_CLOSEBUTTON);
/*     */     }
/* 132 */     return frame_closeIcon;
/*     */   }
/*     */   
/*     */   public static Icon createFrameIconifyIcon() {
/* 136 */     if (frame_iconifyIcon == null) {
/* 137 */       frame_iconifyIcon = new FrameButtonIcon(TMSchema.Part.WP_MINBUTTON);
/*     */     }
/* 139 */     return frame_iconifyIcon;
/*     */   }
/*     */   
/*     */   public static Icon createFrameMaximizeIcon() {
/* 143 */     if (frame_maxIcon == null) {
/* 144 */       frame_maxIcon = new FrameButtonIcon(TMSchema.Part.WP_MAXBUTTON);
/*     */     }
/* 146 */     return frame_maxIcon;
/*     */   }
/*     */   
/*     */   public static Icon createFrameMinimizeIcon() {
/* 150 */     if (frame_minIcon == null) {
/* 151 */       frame_minIcon = new FrameButtonIcon(TMSchema.Part.WP_RESTOREBUTTON);
/*     */     }
/* 153 */     return frame_minIcon;
/*     */   }
/*     */   
/*     */   public static Icon createFrameResizeIcon() {
/* 157 */     if (frame_resizeIcon == null)
/* 158 */       frame_resizeIcon = new ResizeIcon(); 
/* 159 */     return frame_resizeIcon;
/*     */   }
/*     */   
/*     */   private static class FrameButtonIcon
/*     */     implements Icon, Serializable {
/*     */     private TMSchema.Part part;
/*     */     
/*     */     private FrameButtonIcon(TMSchema.Part param1Part) {
/* 167 */       this.part = param1Part;
/*     */     }
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 171 */       int i = getIconWidth();
/* 172 */       int j = getIconHeight();
/*     */       
/* 174 */       XPStyle xPStyle = XPStyle.getXP();
/* 175 */       if (xPStyle != null) {
/* 176 */         TMSchema.State state; XPStyle.Skin skin = xPStyle.getSkin(param1Component, this.part);
/* 177 */         AbstractButton abstractButton = (AbstractButton)param1Component;
/* 178 */         ButtonModel buttonModel = abstractButton.getModel();
/*     */ 
/*     */ 
/*     */         
/* 182 */         JInternalFrame jInternalFrame = (JInternalFrame)SwingUtilities.getAncestorOfClass(JInternalFrame.class, abstractButton);
/* 183 */         boolean bool = (jInternalFrame != null && jInternalFrame.isSelected()) ? true : false;
/*     */ 
/*     */         
/* 186 */         if (bool) {
/* 187 */           if (!buttonModel.isEnabled()) {
/* 188 */             state = TMSchema.State.DISABLED;
/* 189 */           } else if (buttonModel.isArmed() && buttonModel.isPressed()) {
/* 190 */             state = TMSchema.State.PUSHED;
/* 191 */           } else if (buttonModel.isRollover()) {
/* 192 */             state = TMSchema.State.HOT;
/*     */           } else {
/* 194 */             state = TMSchema.State.NORMAL;
/*     */           }
/*     */         
/* 197 */         } else if (!buttonModel.isEnabled()) {
/* 198 */           state = TMSchema.State.INACTIVEDISABLED;
/* 199 */         } else if (buttonModel.isArmed() && buttonModel.isPressed()) {
/* 200 */           state = TMSchema.State.INACTIVEPUSHED;
/* 201 */         } else if (buttonModel.isRollover()) {
/* 202 */           state = TMSchema.State.INACTIVEHOT;
/*     */         } else {
/* 204 */           state = TMSchema.State.INACTIVENORMAL;
/*     */         } 
/*     */         
/* 207 */         skin.paintSkin(param1Graphics, 0, 0, i, j, state);
/*     */       } else {
/* 209 */         param1Graphics.setColor(Color.black);
/* 210 */         int k = i / 12 + 2;
/* 211 */         int m = j / 5;
/* 212 */         int n = j - m * 2 - 1;
/* 213 */         int i1 = i * 3 / 4 - 3;
/* 214 */         int i2 = Math.max(j / 8, 2);
/* 215 */         int i3 = Math.max(i / 15, 1);
/* 216 */         if (this.part == TMSchema.Part.WP_CLOSEBUTTON) {
/*     */           byte b;
/* 218 */           if (i > 47) { b = 6; }
/* 219 */           else if (i > 37) { b = 5; }
/* 220 */           else if (i > 26) { b = 4; }
/* 221 */           else if (i > 16) { b = 3; }
/* 222 */           else if (i > 12) { b = 2; }
/* 223 */           else { b = 1; }
/* 224 */            m = j / 12 + 2;
/* 225 */           if (b == 1) {
/* 226 */             if (i1 % 2 == 1) { k++; i1++; }
/* 227 */              param1Graphics.drawLine(k, m, k + i1 - 2, m + i1 - 2);
/* 228 */             param1Graphics.drawLine(k + i1 - 2, m, k, m + i1 - 2);
/* 229 */           } else if (b == 2) {
/* 230 */             if (i1 > 6) { k++; i1--; }
/* 231 */              param1Graphics.drawLine(k, m, k + i1 - 2, m + i1 - 2);
/* 232 */             param1Graphics.drawLine(k + i1 - 2, m, k, m + i1 - 2);
/* 233 */             param1Graphics.drawLine(k + 1, m, k + i1 - 1, m + i1 - 2);
/* 234 */             param1Graphics.drawLine(k + i1 - 1, m, k + 1, m + i1 - 2);
/*     */           } else {
/* 236 */             k += 2; m++; i1 -= 2;
/* 237 */             param1Graphics.drawLine(k, m, k + i1 - 1, m + i1 - 1);
/* 238 */             param1Graphics.drawLine(k + i1 - 1, m, k, m + i1 - 1);
/* 239 */             param1Graphics.drawLine(k + 1, m, k + i1 - 1, m + i1 - 2);
/* 240 */             param1Graphics.drawLine(k + i1 - 2, m, k, m + i1 - 2);
/* 241 */             param1Graphics.drawLine(k, m + 1, k + i1 - 2, m + i1 - 1);
/* 242 */             param1Graphics.drawLine(k + i1 - 1, m + 1, k + 1, m + i1 - 1);
/* 243 */             for (byte b1 = 4; b1 <= b; b1++) {
/* 244 */               param1Graphics.drawLine(k + b1 - 2, m, k + i1 - 1, m + i1 - b1 + 1);
/* 245 */               param1Graphics.drawLine(k, m + b1 - 2, k + i1 - b1 + 1, m + i1 - 1);
/* 246 */               param1Graphics.drawLine(k + i1 - b1 + 1, m, k, m + i1 - b1 + 1);
/* 247 */               param1Graphics.drawLine(k + i1 - 1, m + b1 - 2, k + b1 - 2, m + i1 - 1);
/*     */             } 
/*     */           } 
/* 250 */         } else if (this.part == TMSchema.Part.WP_MINBUTTON) {
/* 251 */           param1Graphics.fillRect(k, m + n - i2, i1 - i1 / 3, i2);
/* 252 */         } else if (this.part == TMSchema.Part.WP_MAXBUTTON) {
/* 253 */           param1Graphics.fillRect(k, m, i1, i2);
/* 254 */           param1Graphics.fillRect(k, m, i3, n);
/* 255 */           param1Graphics.fillRect(k + i1 - i3, m, i3, n);
/* 256 */           param1Graphics.fillRect(k, m + n - i3, i1, i3);
/* 257 */         } else if (this.part == TMSchema.Part.WP_RESTOREBUTTON) {
/* 258 */           param1Graphics.fillRect(k + i1 / 3, m, i1 - i1 / 3, i2);
/* 259 */           param1Graphics.fillRect(k + i1 / 3, m, i3, n / 3);
/* 260 */           param1Graphics.fillRect(k + i1 - i3, m, i3, n - n / 3);
/* 261 */           param1Graphics.fillRect(k + i1 - i1 / 3, m + n - n / 3 - i3, i1 / 3, i3);
/*     */           
/* 263 */           param1Graphics.fillRect(k, m + n / 3, i1 - i1 / 3, i2);
/* 264 */           param1Graphics.fillRect(k, m + n / 3, i3, n - n / 3);
/* 265 */           param1Graphics.fillRect(k + i1 - i1 / 3 - i3, m + n / 3, i3, n - n / 3);
/* 266 */           param1Graphics.fillRect(k, m + n - i3, i1 - i1 / 3, i3);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public int getIconWidth() {
/*     */       int i;
/* 273 */       if (XPStyle.getXP() != null) {
/*     */ 
/*     */ 
/*     */         
/* 277 */         i = UIManager.getInt("InternalFrame.titleButtonHeight") - 2;
/* 278 */         Dimension dimension = XPStyle.getPartSize(TMSchema.Part.WP_CLOSEBUTTON, TMSchema.State.NORMAL);
/* 279 */         if (dimension != null && dimension.width != 0 && dimension.height != 0) {
/* 280 */           i = (int)(i * dimension.width / dimension.height);
/*     */         }
/*     */       } else {
/* 283 */         i = UIManager.getInt("InternalFrame.titleButtonWidth") - 2;
/*     */       } 
/* 285 */       if (XPStyle.getXP() != null) {
/* 286 */         i -= 2;
/*     */       }
/* 288 */       return i;
/*     */     }
/*     */     
/*     */     public int getIconHeight() {
/* 292 */       return UIManager.getInt("InternalFrame.titleButtonHeight") - 4;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ResizeIcon
/*     */     implements Icon, Serializable {
/*     */     private ResizeIcon() {}
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 301 */       param1Graphics.setColor(UIManager.getColor("InternalFrame.resizeIconHighlight"));
/* 302 */       param1Graphics.drawLine(0, 11, 11, 0);
/* 303 */       param1Graphics.drawLine(4, 11, 11, 4);
/* 304 */       param1Graphics.drawLine(8, 11, 11, 8);
/*     */       
/* 306 */       param1Graphics.setColor(UIManager.getColor("InternalFrame.resizeIconShadow"));
/* 307 */       param1Graphics.drawLine(1, 11, 11, 1);
/* 308 */       param1Graphics.drawLine(2, 11, 11, 2);
/* 309 */       param1Graphics.drawLine(5, 11, 11, 5);
/* 310 */       param1Graphics.drawLine(6, 11, 11, 6);
/* 311 */       param1Graphics.drawLine(9, 11, 11, 9);
/* 312 */       param1Graphics.drawLine(10, 11, 11, 10);
/*     */     }
/* 314 */     public int getIconWidth() { return 13; } public int getIconHeight() {
/* 315 */       return 13;
/*     */     } }
/*     */   private static class CheckBoxIcon implements Icon, Serializable { static final int csize = 13;
/*     */     
/*     */     private CheckBoxIcon() {}
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 322 */       JCheckBox jCheckBox = (JCheckBox)param1Component;
/* 323 */       ButtonModel buttonModel = jCheckBox.getModel();
/* 324 */       XPStyle xPStyle = XPStyle.getXP();
/*     */       
/* 326 */       if (xPStyle != null) {
/*     */         TMSchema.State state;
/* 328 */         if (buttonModel.isSelected()) {
/* 329 */           state = TMSchema.State.CHECKEDNORMAL;
/* 330 */           if (!buttonModel.isEnabled()) {
/* 331 */             state = TMSchema.State.CHECKEDDISABLED;
/* 332 */           } else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 333 */             state = TMSchema.State.CHECKEDPRESSED;
/* 334 */           } else if (buttonModel.isRollover()) {
/* 335 */             state = TMSchema.State.CHECKEDHOT;
/*     */           } 
/*     */         } else {
/* 338 */           state = TMSchema.State.UNCHECKEDNORMAL;
/* 339 */           if (!buttonModel.isEnabled()) {
/* 340 */             state = TMSchema.State.UNCHECKEDDISABLED;
/* 341 */           } else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 342 */             state = TMSchema.State.UNCHECKEDPRESSED;
/* 343 */           } else if (buttonModel.isRollover()) {
/* 344 */             state = TMSchema.State.UNCHECKEDHOT;
/*     */           } 
/*     */         } 
/* 347 */         TMSchema.Part part = TMSchema.Part.BP_CHECKBOX;
/* 348 */         xPStyle.getSkin(param1Component, part).paintSkin(param1Graphics, param1Int1, param1Int2, state);
/*     */       } else {
/*     */         
/* 351 */         if (!jCheckBox.isBorderPaintedFlat()) {
/*     */           
/* 353 */           param1Graphics.setColor(UIManager.getColor("CheckBox.shadow"));
/* 354 */           param1Graphics.drawLine(param1Int1, param1Int2, param1Int1 + 11, param1Int2);
/* 355 */           param1Graphics.drawLine(param1Int1, param1Int2 + 1, param1Int1, param1Int2 + 11);
/*     */ 
/*     */           
/* 358 */           param1Graphics.setColor(UIManager.getColor("CheckBox.highlight"));
/* 359 */           param1Graphics.drawLine(param1Int1 + 12, param1Int2, param1Int1 + 12, param1Int2 + 12);
/* 360 */           param1Graphics.drawLine(param1Int1, param1Int2 + 12, param1Int1 + 11, param1Int2 + 12);
/*     */ 
/*     */           
/* 363 */           param1Graphics.setColor(UIManager.getColor("CheckBox.darkShadow"));
/* 364 */           param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 1, param1Int1 + 10, param1Int2 + 1);
/* 365 */           param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 2, param1Int1 + 1, param1Int2 + 10);
/*     */ 
/*     */           
/* 368 */           param1Graphics.setColor(UIManager.getColor("CheckBox.light"));
/* 369 */           param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 11, param1Int1 + 11, param1Int2 + 11);
/* 370 */           param1Graphics.drawLine(param1Int1 + 11, param1Int2 + 1, param1Int1 + 11, param1Int2 + 10);
/*     */ 
/*     */           
/* 373 */           if ((buttonModel.isPressed() && buttonModel.isArmed()) || !buttonModel.isEnabled()) {
/* 374 */             param1Graphics.setColor(UIManager.getColor("CheckBox.background"));
/*     */           } else {
/* 376 */             param1Graphics.setColor(UIManager.getColor("CheckBox.interiorBackground"));
/*     */           } 
/* 378 */           param1Graphics.fillRect(param1Int1 + 2, param1Int2 + 2, 9, 9);
/*     */         } else {
/* 380 */           param1Graphics.setColor(UIManager.getColor("CheckBox.shadow"));
/* 381 */           param1Graphics.drawRect(param1Int1 + 1, param1Int2 + 1, 10, 10);
/*     */           
/* 383 */           if ((buttonModel.isPressed() && buttonModel.isArmed()) || !buttonModel.isEnabled()) {
/* 384 */             param1Graphics.setColor(UIManager.getColor("CheckBox.background"));
/*     */           } else {
/* 386 */             param1Graphics.setColor(UIManager.getColor("CheckBox.interiorBackground"));
/*     */           } 
/* 388 */           param1Graphics.fillRect(param1Int1 + 2, param1Int2 + 2, 9, 9);
/*     */         } 
/*     */         
/* 391 */         if (buttonModel.isEnabled()) {
/* 392 */           param1Graphics.setColor(UIManager.getColor("CheckBox.foreground"));
/*     */         } else {
/* 394 */           param1Graphics.setColor(UIManager.getColor("CheckBox.shadow"));
/*     */         } 
/*     */ 
/*     */         
/* 398 */         if (buttonModel.isSelected()) {
/* 399 */           param1Graphics.drawLine(param1Int1 + 9, param1Int2 + 3, param1Int1 + 9, param1Int2 + 3);
/* 400 */           param1Graphics.drawLine(param1Int1 + 8, param1Int2 + 4, param1Int1 + 9, param1Int2 + 4);
/* 401 */           param1Graphics.drawLine(param1Int1 + 7, param1Int2 + 5, param1Int1 + 9, param1Int2 + 5);
/* 402 */           param1Graphics.drawLine(param1Int1 + 6, param1Int2 + 6, param1Int1 + 8, param1Int2 + 6);
/* 403 */           param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 7, param1Int1 + 7, param1Int2 + 7);
/* 404 */           param1Graphics.drawLine(param1Int1 + 4, param1Int2 + 8, param1Int1 + 6, param1Int2 + 8);
/* 405 */           param1Graphics.drawLine(param1Int1 + 5, param1Int2 + 9, param1Int1 + 5, param1Int2 + 9);
/* 406 */           param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 5, param1Int1 + 3, param1Int2 + 5);
/* 407 */           param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 6, param1Int1 + 4, param1Int2 + 6);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public int getIconWidth() {
/* 413 */       XPStyle xPStyle = XPStyle.getXP();
/* 414 */       if (xPStyle != null) {
/* 415 */         return xPStyle.getSkin(null, TMSchema.Part.BP_CHECKBOX).getWidth();
/*     */       }
/* 417 */       return 13;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getIconHeight() {
/* 422 */       XPStyle xPStyle = XPStyle.getXP();
/* 423 */       if (xPStyle != null) {
/* 424 */         return xPStyle.getSkin(null, TMSchema.Part.BP_CHECKBOX).getHeight();
/*     */       }
/* 426 */       return 13;
/*     */     } }
/*     */ 
/*     */   
/*     */   private static class RadioButtonIcon implements Icon, UIResource, Serializable {
/*     */     private RadioButtonIcon() {}
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 434 */       AbstractButton abstractButton = (AbstractButton)param1Component;
/* 435 */       ButtonModel buttonModel = abstractButton.getModel();
/* 436 */       XPStyle xPStyle = XPStyle.getXP();
/*     */       
/* 438 */       if (xPStyle != null) {
/* 439 */         TMSchema.State state; TMSchema.Part part = TMSchema.Part.BP_RADIOBUTTON;
/* 440 */         XPStyle.Skin skin = xPStyle.getSkin(abstractButton, part);
/*     */         
/* 442 */         boolean bool = false;
/* 443 */         if (buttonModel.isSelected()) {
/* 444 */           state = TMSchema.State.CHECKEDNORMAL;
/* 445 */           if (!buttonModel.isEnabled()) {
/* 446 */             state = TMSchema.State.CHECKEDDISABLED;
/* 447 */           } else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 448 */             state = TMSchema.State.CHECKEDPRESSED;
/* 449 */           } else if (buttonModel.isRollover()) {
/* 450 */             state = TMSchema.State.CHECKEDHOT;
/*     */           } 
/*     */         } else {
/* 453 */           state = TMSchema.State.UNCHECKEDNORMAL;
/* 454 */           if (!buttonModel.isEnabled()) {
/* 455 */             state = TMSchema.State.UNCHECKEDDISABLED;
/* 456 */           } else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 457 */             state = TMSchema.State.UNCHECKEDPRESSED;
/* 458 */           } else if (buttonModel.isRollover()) {
/* 459 */             state = TMSchema.State.UNCHECKEDHOT;
/*     */           } 
/*     */         } 
/* 462 */         skin.paintSkin(param1Graphics, param1Int1, param1Int2, state);
/*     */       } else {
/*     */         
/* 465 */         if ((buttonModel.isPressed() && buttonModel.isArmed()) || !buttonModel.isEnabled()) {
/* 466 */           param1Graphics.setColor(UIManager.getColor("RadioButton.background"));
/*     */         } else {
/* 468 */           param1Graphics.setColor(UIManager.getColor("RadioButton.interiorBackground"));
/*     */         } 
/* 470 */         param1Graphics.fillRect(param1Int1 + 2, param1Int2 + 2, 8, 8);
/*     */ 
/*     */ 
/*     */         
/* 474 */         param1Graphics.setColor(UIManager.getColor("RadioButton.shadow"));
/* 475 */         param1Graphics.drawLine(param1Int1 + 4, param1Int2 + 0, param1Int1 + 7, param1Int2 + 0);
/* 476 */         param1Graphics.drawLine(param1Int1 + 2, param1Int2 + 1, param1Int1 + 3, param1Int2 + 1);
/* 477 */         param1Graphics.drawLine(param1Int1 + 8, param1Int2 + 1, param1Int1 + 9, param1Int2 + 1);
/* 478 */         param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 2, param1Int1 + 1, param1Int2 + 3);
/* 479 */         param1Graphics.drawLine(param1Int1 + 0, param1Int2 + 4, param1Int1 + 0, param1Int2 + 7);
/* 480 */         param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 8, param1Int1 + 1, param1Int2 + 9);
/*     */ 
/*     */         
/* 483 */         param1Graphics.setColor(UIManager.getColor("RadioButton.highlight"));
/* 484 */         param1Graphics.drawLine(param1Int1 + 2, param1Int2 + 10, param1Int1 + 3, param1Int2 + 10);
/* 485 */         param1Graphics.drawLine(param1Int1 + 4, param1Int2 + 11, param1Int1 + 7, param1Int2 + 11);
/* 486 */         param1Graphics.drawLine(param1Int1 + 8, param1Int2 + 10, param1Int1 + 9, param1Int2 + 10);
/* 487 */         param1Graphics.drawLine(param1Int1 + 10, param1Int2 + 9, param1Int1 + 10, param1Int2 + 8);
/* 488 */         param1Graphics.drawLine(param1Int1 + 11, param1Int2 + 7, param1Int1 + 11, param1Int2 + 4);
/* 489 */         param1Graphics.drawLine(param1Int1 + 10, param1Int2 + 3, param1Int1 + 10, param1Int2 + 2);
/*     */ 
/*     */ 
/*     */         
/* 493 */         param1Graphics.setColor(UIManager.getColor("RadioButton.darkShadow"));
/* 494 */         param1Graphics.drawLine(param1Int1 + 4, param1Int2 + 1, param1Int1 + 7, param1Int2 + 1);
/* 495 */         param1Graphics.drawLine(param1Int1 + 2, param1Int2 + 2, param1Int1 + 3, param1Int2 + 2);
/* 496 */         param1Graphics.drawLine(param1Int1 + 8, param1Int2 + 2, param1Int1 + 9, param1Int2 + 2);
/* 497 */         param1Graphics.drawLine(param1Int1 + 2, param1Int2 + 3, param1Int1 + 2, param1Int2 + 3);
/* 498 */         param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 4, param1Int1 + 1, param1Int2 + 7);
/* 499 */         param1Graphics.drawLine(param1Int1 + 2, param1Int2 + 8, param1Int1 + 2, param1Int2 + 8);
/*     */ 
/*     */ 
/*     */         
/* 503 */         param1Graphics.setColor(UIManager.getColor("RadioButton.light"));
/* 504 */         param1Graphics.drawLine(param1Int1 + 2, param1Int2 + 9, param1Int1 + 3, param1Int2 + 9);
/* 505 */         param1Graphics.drawLine(param1Int1 + 4, param1Int2 + 10, param1Int1 + 7, param1Int2 + 10);
/* 506 */         param1Graphics.drawLine(param1Int1 + 8, param1Int2 + 9, param1Int1 + 9, param1Int2 + 9);
/* 507 */         param1Graphics.drawLine(param1Int1 + 9, param1Int2 + 8, param1Int1 + 9, param1Int2 + 8);
/* 508 */         param1Graphics.drawLine(param1Int1 + 10, param1Int2 + 7, param1Int1 + 10, param1Int2 + 4);
/* 509 */         param1Graphics.drawLine(param1Int1 + 9, param1Int2 + 3, param1Int1 + 9, param1Int2 + 3);
/*     */ 
/*     */ 
/*     */         
/* 513 */         if (buttonModel.isSelected()) {
/* 514 */           if (buttonModel.isEnabled()) {
/* 515 */             param1Graphics.setColor(UIManager.getColor("RadioButton.foreground"));
/*     */           } else {
/* 517 */             param1Graphics.setColor(UIManager.getColor("RadioButton.shadow"));
/*     */           } 
/* 519 */           param1Graphics.fillRect(param1Int1 + 4, param1Int2 + 5, 4, 2);
/* 520 */           param1Graphics.fillRect(param1Int1 + 5, param1Int2 + 4, 2, 4);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public int getIconWidth() {
/* 526 */       XPStyle xPStyle = XPStyle.getXP();
/* 527 */       if (xPStyle != null) {
/* 528 */         return xPStyle.getSkin(null, TMSchema.Part.BP_RADIOBUTTON).getWidth();
/*     */       }
/* 530 */       return 13;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getIconHeight() {
/* 535 */       XPStyle xPStyle = XPStyle.getXP();
/* 536 */       if (xPStyle != null) {
/* 537 */         return xPStyle.getSkin(null, TMSchema.Part.BP_RADIOBUTTON).getHeight();
/*     */       }
/* 539 */       return 13;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class CheckBoxMenuItemIcon
/*     */     implements Icon, UIResource, Serializable {
/*     */     private CheckBoxMenuItemIcon() {}
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 548 */       AbstractButton abstractButton = (AbstractButton)param1Component;
/* 549 */       ButtonModel buttonModel = abstractButton.getModel();
/* 550 */       boolean bool = buttonModel.isSelected();
/* 551 */       if (bool) {
/* 552 */         param1Int2 -= getIconHeight() / 2;
/* 553 */         param1Graphics.drawLine(param1Int1 + 9, param1Int2 + 3, param1Int1 + 9, param1Int2 + 3);
/* 554 */         param1Graphics.drawLine(param1Int1 + 8, param1Int2 + 4, param1Int1 + 9, param1Int2 + 4);
/* 555 */         param1Graphics.drawLine(param1Int1 + 7, param1Int2 + 5, param1Int1 + 9, param1Int2 + 5);
/* 556 */         param1Graphics.drawLine(param1Int1 + 6, param1Int2 + 6, param1Int1 + 8, param1Int2 + 6);
/* 557 */         param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 7, param1Int1 + 7, param1Int2 + 7);
/* 558 */         param1Graphics.drawLine(param1Int1 + 4, param1Int2 + 8, param1Int1 + 6, param1Int2 + 8);
/* 559 */         param1Graphics.drawLine(param1Int1 + 5, param1Int2 + 9, param1Int1 + 5, param1Int2 + 9);
/* 560 */         param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 5, param1Int1 + 3, param1Int2 + 5);
/* 561 */         param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 6, param1Int1 + 4, param1Int2 + 6);
/*     */       } 
/*     */     }
/* 564 */     public int getIconWidth() { return 9; } public int getIconHeight() {
/* 565 */       return 9;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class RadioButtonMenuItemIcon implements Icon, UIResource, Serializable {
/*     */     private RadioButtonMenuItemIcon() {}
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 573 */       AbstractButton abstractButton = (AbstractButton)param1Component;
/* 574 */       ButtonModel buttonModel = abstractButton.getModel();
/* 575 */       if (abstractButton.isSelected() == true)
/* 576 */         param1Graphics.fillRoundRect(param1Int1 + 3, param1Int2 + 3, getIconWidth() - 6, getIconHeight() - 6, 4, 4); 
/*     */     }
/*     */     
/*     */     public int getIconWidth() {
/* 580 */       return 12; } public int getIconHeight() {
/* 581 */       return 12;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class MenuItemCheckIcon
/*     */     implements Icon, UIResource, Serializable
/*     */   {
/*     */     private MenuItemCheckIcon() {}
/*     */ 
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {}
/*     */     
/*     */     public int getIconWidth() {
/* 595 */       return 9; } public int getIconHeight() {
/* 596 */       return 9;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class MenuItemArrowIcon
/*     */     implements Icon, UIResource, Serializable
/*     */   {
/*     */     private MenuItemArrowIcon() {}
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {}
/*     */     
/*     */     public int getIconWidth() {
/* 609 */       return 4; } public int getIconHeight() {
/* 610 */       return 8;
/*     */     } }
/*     */   
/*     */   private static class MenuArrowIcon implements Icon, UIResource, Serializable { private MenuArrowIcon() {}
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 616 */       XPStyle xPStyle = XPStyle.getXP();
/* 617 */       if (WindowsMenuItemUI.isVistaPainting(xPStyle)) {
/* 618 */         TMSchema.State state = TMSchema.State.NORMAL;
/* 619 */         if (param1Component instanceof JMenuItem) {
/* 620 */           state = ((JMenuItem)param1Component).getModel().isEnabled() ? TMSchema.State.NORMAL : TMSchema.State.DISABLED;
/*     */         }
/*     */         
/* 623 */         XPStyle.Skin skin = xPStyle.getSkin(param1Component, TMSchema.Part.MP_POPUPSUBMENU);
/* 624 */         if (WindowsGraphicsUtils.isLeftToRight(param1Component)) {
/* 625 */           skin.paintSkin(param1Graphics, param1Int1, param1Int2, state);
/*     */         } else {
/* 627 */           Graphics2D graphics2D = (Graphics2D)param1Graphics.create();
/* 628 */           graphics2D.translate(param1Int1 + skin.getWidth(), param1Int2);
/* 629 */           graphics2D.scale(-1.0D, 1.0D);
/* 630 */           skin.paintSkin(graphics2D, 0, 0, state);
/* 631 */           graphics2D.dispose();
/*     */         } 
/*     */       } else {
/* 634 */         param1Graphics.translate(param1Int1, param1Int2);
/* 635 */         if (WindowsGraphicsUtils.isLeftToRight(param1Component)) {
/* 636 */           param1Graphics.drawLine(0, 0, 0, 7);
/* 637 */           param1Graphics.drawLine(1, 1, 1, 6);
/* 638 */           param1Graphics.drawLine(2, 2, 2, 5);
/* 639 */           param1Graphics.drawLine(3, 3, 3, 4);
/*     */         } else {
/* 641 */           param1Graphics.drawLine(4, 0, 4, 7);
/* 642 */           param1Graphics.drawLine(3, 1, 3, 6);
/* 643 */           param1Graphics.drawLine(2, 2, 2, 5);
/* 644 */           param1Graphics.drawLine(1, 3, 1, 4);
/*     */         } 
/* 646 */         param1Graphics.translate(-param1Int1, -param1Int2);
/*     */       } 
/*     */     }
/*     */     public int getIconWidth() {
/* 650 */       XPStyle xPStyle = XPStyle.getXP();
/* 651 */       if (WindowsMenuItemUI.isVistaPainting(xPStyle)) {
/* 652 */         XPStyle.Skin skin = xPStyle.getSkin(null, TMSchema.Part.MP_POPUPSUBMENU);
/* 653 */         return skin.getWidth();
/*     */       } 
/* 655 */       return 4;
/*     */     }
/*     */     
/*     */     public int getIconHeight() {
/* 659 */       XPStyle xPStyle = XPStyle.getXP();
/* 660 */       if (WindowsMenuItemUI.isVistaPainting(xPStyle)) {
/* 661 */         XPStyle.Skin skin = xPStyle.getSkin(null, TMSchema.Part.MP_POPUPSUBMENU);
/* 662 */         return skin.getHeight();
/*     */       } 
/* 664 */       return 8;
/*     */     } }
/*     */ 
/*     */   
/*     */   static class VistaMenuItemCheckIconFactory
/*     */     implements MenuItemCheckIconFactory
/*     */   {
/*     */     private static final int OFFSET = 3;
/*     */     
/*     */     public Icon getIcon(JMenuItem param1JMenuItem) {
/* 674 */       return new VistaMenuItemCheckIcon(param1JMenuItem);
/*     */     }
/*     */     
/*     */     public boolean isCompatible(Object param1Object, String param1String) {
/* 678 */       return (param1Object instanceof VistaMenuItemCheckIcon && ((VistaMenuItemCheckIcon)param1Object)
/* 679 */         .type == getType(param1String));
/*     */     }
/*     */     
/*     */     public Icon getIcon(String param1String) {
/* 683 */       return new VistaMenuItemCheckIcon(param1String);
/*     */     }
/*     */     
/*     */     static int getIconWidth() {
/* 687 */       XPStyle xPStyle = XPStyle.getXP();
/* 688 */       return ((xPStyle != null) ? xPStyle.getSkin(null, TMSchema.Part.MP_POPUPCHECK).getWidth() : 16) + 6;
/*     */     }
/*     */     
/*     */     private static Class<? extends JMenuItem> getType(Component param1Component) {
/*     */       Class<JMenuItem> clazz;
/* 693 */       Class<JCheckBoxMenuItem> clazz1 = null;
/* 694 */       if (param1Component instanceof JCheckBoxMenuItem) {
/* 695 */         clazz1 = JCheckBoxMenuItem.class;
/* 696 */       } else if (param1Component instanceof JRadioButtonMenuItem) {
/* 697 */         Class<JRadioButtonMenuItem> clazz2 = JRadioButtonMenuItem.class;
/* 698 */       } else if (param1Component instanceof JMenu) {
/* 699 */         Class<JMenu> clazz2 = JMenu.class;
/* 700 */       } else if (param1Component instanceof JMenuItem) {
/* 701 */         clazz = JMenuItem.class;
/*     */       } 
/* 703 */       return clazz;
/*     */     }
/*     */     private static Class<? extends JMenuItem> getType(String param1String) {
/*     */       Class<JMenuItem> clazz;
/* 707 */       Class<JCheckBoxMenuItem> clazz1 = null;
/* 708 */       if (param1String == "CheckBoxMenuItem") {
/* 709 */         clazz1 = JCheckBoxMenuItem.class;
/* 710 */       } else if (param1String == "RadioButtonMenuItem") {
/* 711 */         Class<JRadioButtonMenuItem> clazz2 = JRadioButtonMenuItem.class;
/* 712 */       } else if (param1String == "Menu") {
/* 713 */         Class<JMenu> clazz2 = JMenu.class;
/* 714 */       } else if (param1String == "MenuItem") {
/* 715 */         clazz = JMenuItem.class;
/*     */       } else {
/*     */         
/* 718 */         clazz = JMenuItem.class;
/*     */       } 
/* 720 */       return clazz;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static class VistaMenuItemCheckIcon
/*     */       implements Icon, UIResource, Serializable
/*     */     {
/*     */       private final JMenuItem menuItem;
/*     */ 
/*     */       
/*     */       private final Class<? extends JMenuItem> type;
/*     */ 
/*     */       
/*     */       VistaMenuItemCheckIcon(JMenuItem param2JMenuItem) {
/* 735 */         this.type = WindowsIconFactory.VistaMenuItemCheckIconFactory.getType(param2JMenuItem);
/* 736 */         this.menuItem = param2JMenuItem;
/*     */       }
/*     */       VistaMenuItemCheckIcon(String param2String) {
/* 739 */         this.type = WindowsIconFactory.VistaMenuItemCheckIconFactory.getType(param2String);
/* 740 */         this.menuItem = null;
/*     */       }
/*     */       
/*     */       public int getIconHeight() {
/* 744 */         Icon icon1 = getLaFIcon();
/* 745 */         if (icon1 != null) {
/* 746 */           return icon1.getIconHeight();
/*     */         }
/* 748 */         Icon icon2 = getIcon();
/* 749 */         int i = 0;
/* 750 */         if (icon2 != null) {
/* 751 */           i = icon2.getIconHeight();
/*     */         } else {
/* 753 */           XPStyle xPStyle = XPStyle.getXP();
/* 754 */           if (xPStyle != null) {
/* 755 */             XPStyle.Skin skin = xPStyle.getSkin(null, TMSchema.Part.MP_POPUPCHECK);
/* 756 */             i = skin.getHeight();
/*     */           } else {
/* 758 */             i = 16;
/*     */           } 
/*     */         } 
/* 761 */         i += 6;
/* 762 */         return i;
/*     */       }
/*     */       
/*     */       public int getIconWidth() {
/* 766 */         Icon icon1 = getLaFIcon();
/* 767 */         if (icon1 != null) {
/* 768 */           return icon1.getIconWidth();
/*     */         }
/* 770 */         Icon icon2 = getIcon();
/* 771 */         int i = 0;
/* 772 */         if (icon2 != null) {
/* 773 */           i = icon2.getIconWidth() + 6;
/*     */         } else {
/* 775 */           i = WindowsIconFactory.VistaMenuItemCheckIconFactory.getIconWidth();
/*     */         } 
/* 777 */         return i;
/*     */       }
/*     */       
/*     */       public void paintIcon(Component param2Component, Graphics param2Graphics, int param2Int1, int param2Int2) {
/* 781 */         Icon icon1 = getLaFIcon();
/* 782 */         if (icon1 != null) {
/* 783 */           icon1.paintIcon(param2Component, param2Graphics, param2Int1, param2Int2);
/*     */           return;
/*     */         } 
/* 786 */         assert this.menuItem == null || param2Component == this.menuItem;
/* 787 */         Icon icon2 = getIcon();
/* 788 */         if (this.type == JCheckBoxMenuItem.class || this.type == JRadioButtonMenuItem.class) {
/*     */           
/* 790 */           AbstractButton abstractButton = (AbstractButton)param2Component;
/* 791 */           if (abstractButton.isSelected()) {
/* 792 */             TMSchema.State state1, state2; TMSchema.Part part1 = TMSchema.Part.MP_POPUPCHECKBACKGROUND;
/* 793 */             TMSchema.Part part2 = TMSchema.Part.MP_POPUPCHECK;
/*     */ 
/*     */             
/* 796 */             if (isEnabled(param2Component, null)) {
/* 797 */               state1 = (icon2 != null) ? TMSchema.State.BITMAP : TMSchema.State.NORMAL;
/*     */               
/* 799 */               state2 = (this.type == JRadioButtonMenuItem.class) ? TMSchema.State.BULLETNORMAL : TMSchema.State.CHECKMARKNORMAL;
/*     */             }
/*     */             else {
/*     */               
/* 803 */               state1 = TMSchema.State.DISABLEDPUSHED;
/* 804 */               state2 = (this.type == JRadioButtonMenuItem.class) ? TMSchema.State.BULLETDISABLED : TMSchema.State.CHECKMARKDISABLED;
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 809 */             XPStyle xPStyle = XPStyle.getXP();
/* 810 */             if (xPStyle != null) {
/*     */               
/* 812 */               XPStyle.Skin skin = xPStyle.getSkin(param2Component, part1);
/* 813 */               skin.paintSkin(param2Graphics, param2Int1, param2Int2, 
/* 814 */                   getIconWidth(), getIconHeight(), state1);
/* 815 */               if (icon2 == null) {
/* 816 */                 skin = xPStyle.getSkin(param2Component, part2);
/* 817 */                 skin.paintSkin(param2Graphics, param2Int1 + 3, param2Int2 + 3, state2);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 822 */         if (icon2 != null) {
/* 823 */           icon2.paintIcon(param2Component, param2Graphics, param2Int1 + 3, param2Int2 + 3);
/*     */         }
/*     */       }
/*     */       
/*     */       private static WindowsMenuItemUIAccessor getAccessor(JMenuItem param2JMenuItem) {
/* 828 */         WindowsMenuItemUIAccessor windowsMenuItemUIAccessor = null;
/* 829 */         ButtonUI buttonUI = (param2JMenuItem != null) ? param2JMenuItem.getUI() : null;
/*     */         
/* 831 */         if (buttonUI instanceof WindowsMenuItemUI) {
/* 832 */           windowsMenuItemUIAccessor = ((WindowsMenuItemUI)buttonUI).accessor;
/* 833 */         } else if (buttonUI instanceof WindowsMenuUI) {
/* 834 */           windowsMenuItemUIAccessor = ((WindowsMenuUI)buttonUI).accessor;
/* 835 */         } else if (buttonUI instanceof WindowsCheckBoxMenuItemUI) {
/* 836 */           windowsMenuItemUIAccessor = ((WindowsCheckBoxMenuItemUI)buttonUI).accessor;
/* 837 */         } else if (buttonUI instanceof WindowsRadioButtonMenuItemUI) {
/* 838 */           windowsMenuItemUIAccessor = ((WindowsRadioButtonMenuItemUI)buttonUI).accessor;
/*     */         } 
/* 840 */         return windowsMenuItemUIAccessor;
/*     */       }
/*     */       
/*     */       private static boolean isEnabled(Component param2Component, TMSchema.State param2State) {
/* 844 */         if (param2State == null && param2Component instanceof JMenuItem) {
/*     */           
/* 846 */           WindowsMenuItemUIAccessor windowsMenuItemUIAccessor = getAccessor((JMenuItem)param2Component);
/* 847 */           if (windowsMenuItemUIAccessor != null) {
/* 848 */             param2State = windowsMenuItemUIAccessor.getState((JMenuItem)param2Component);
/*     */           }
/*     */         } 
/* 851 */         if (param2State == null) {
/* 852 */           if (param2Component != null) {
/* 853 */             return param2Component.isEnabled();
/*     */           }
/* 855 */           return true;
/*     */         } 
/*     */         
/* 858 */         return (param2State != TMSchema.State.DISABLED && param2State != TMSchema.State.DISABLEDHOT && param2State != TMSchema.State.DISABLEDPUSHED);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       private Icon getIcon() {
/* 864 */         Icon icon = null;
/* 865 */         if (this.menuItem == null) {
/* 866 */           return icon;
/*     */         }
/*     */         
/* 869 */         WindowsMenuItemUIAccessor windowsMenuItemUIAccessor = getAccessor(this.menuItem);
/* 870 */         TMSchema.State state = (windowsMenuItemUIAccessor != null) ? windowsMenuItemUIAccessor.getState(this.menuItem) : null;
/*     */         
/* 872 */         if (isEnabled(this.menuItem, null)) {
/* 873 */           if (state == TMSchema.State.PUSHED) {
/* 874 */             icon = this.menuItem.getPressedIcon();
/*     */           } else {
/* 876 */             icon = this.menuItem.getIcon();
/*     */           } 
/*     */         } else {
/* 879 */           icon = this.menuItem.getDisabledIcon();
/*     */         } 
/* 881 */         return icon;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private Icon getLaFIcon() {
/* 891 */         Icon icon = (Icon)UIManager.getDefaults().get(typeToString(this.type));
/* 892 */         if (icon instanceof VistaMenuItemCheckIcon && ((VistaMenuItemCheckIcon)icon).type == this.type)
/*     */         {
/* 894 */           icon = null;
/*     */         }
/* 896 */         return icon;
/*     */       }
/*     */ 
/*     */       
/*     */       private static String typeToString(Class<? extends JMenuItem> param2Class) {
/* 901 */         assert param2Class == JMenuItem.class || param2Class == JMenu.class || param2Class == JCheckBoxMenuItem.class || param2Class == JRadioButtonMenuItem.class;
/*     */ 
/*     */ 
/*     */         
/* 905 */         StringBuilder stringBuilder = new StringBuilder(param2Class.getName());
/*     */         
/* 907 */         stringBuilder.delete(0, stringBuilder.lastIndexOf("J") + 1);
/* 908 */         stringBuilder.append(".checkIcon");
/* 909 */         return stringBuilder.toString();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsIconFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */