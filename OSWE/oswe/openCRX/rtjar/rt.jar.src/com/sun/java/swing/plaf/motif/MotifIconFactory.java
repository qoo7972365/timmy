/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MotifIconFactory
/*     */   implements Serializable
/*     */ {
/*     */   private static Icon checkBoxIcon;
/*     */   private static Icon radioButtonIcon;
/*     */   private static Icon menuItemCheckIcon;
/*     */   private static Icon menuItemArrowIcon;
/*     */   private static Icon menuArrowIcon;
/*     */   
/*     */   public static Icon getMenuItemCheckIcon() {
/*  62 */     return null;
/*     */   }
/*     */   
/*     */   public static Icon getMenuItemArrowIcon() {
/*  66 */     if (menuItemArrowIcon == null) {
/*  67 */       menuItemArrowIcon = new MenuItemArrowIcon();
/*     */     }
/*  69 */     return menuItemArrowIcon;
/*     */   }
/*     */   
/*     */   public static Icon getMenuArrowIcon() {
/*  73 */     if (menuArrowIcon == null) {
/*  74 */       menuArrowIcon = new MenuArrowIcon();
/*     */     }
/*  76 */     return menuArrowIcon;
/*     */   }
/*     */   
/*     */   public static Icon getCheckBoxIcon() {
/*  80 */     if (checkBoxIcon == null) {
/*  81 */       checkBoxIcon = new CheckBoxIcon();
/*     */     }
/*  83 */     return checkBoxIcon;
/*     */   }
/*     */   
/*     */   public static Icon getRadioButtonIcon() {
/*  87 */     if (radioButtonIcon == null) {
/*  88 */       radioButtonIcon = new RadioButtonIcon();
/*     */     }
/*  90 */     return radioButtonIcon;
/*     */   }
/*     */   
/*     */   private static class CheckBoxIcon
/*     */     implements Icon, UIResource, Serializable {
/*     */     static final int csize = 13;
/*  96 */     private Color control = UIManager.getColor("control");
/*  97 */     private Color foreground = UIManager.getColor("CheckBox.foreground");
/*  98 */     private Color shadow = UIManager.getColor("controlShadow");
/*  99 */     private Color highlight = UIManager.getColor("controlHighlight");
/* 100 */     private Color lightShadow = UIManager.getColor("controlLightShadow");
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 103 */       AbstractButton abstractButton = (AbstractButton)param1Component;
/* 104 */       ButtonModel buttonModel = abstractButton.getModel();
/*     */       
/* 106 */       boolean bool1 = false;
/*     */       
/* 108 */       if (abstractButton instanceof JCheckBox) {
/* 109 */         bool1 = ((JCheckBox)abstractButton).isBorderPaintedFlat();
/*     */       }
/*     */       
/* 112 */       boolean bool2 = buttonModel.isPressed();
/* 113 */       boolean bool3 = buttonModel.isArmed();
/* 114 */       boolean bool4 = buttonModel.isEnabled();
/* 115 */       boolean bool5 = buttonModel.isSelected();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 122 */       boolean bool6 = ((bool2 && !bool3 && bool5) || (bool2 && bool3 && !bool5)) ? true : false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 128 */       boolean bool7 = ((bool2 && !bool3 && !bool5) || (bool2 && bool3 && bool5)) ? true : false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 135 */       boolean bool8 = ((!bool2 && bool3 && bool5) || (!bool2 && !bool3 && bool5)) ? true : false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 143 */       if (bool1) {
/* 144 */         param1Graphics.setColor(this.shadow);
/* 145 */         param1Graphics.drawRect(param1Int1 + 2, param1Int2, 12, 12);
/* 146 */         if (bool7 || bool6) {
/* 147 */           param1Graphics.setColor(this.control);
/* 148 */           param1Graphics.fillRect(param1Int1 + 3, param1Int2 + 1, 11, 11);
/*     */         } 
/*     */       } 
/*     */       
/* 152 */       if (bool6) {
/*     */         
/* 154 */         drawCheckBezel(param1Graphics, param1Int1, param1Int2, 13, true, false, false, bool1);
/* 155 */       } else if (bool7) {
/*     */         
/* 157 */         drawCheckBezel(param1Graphics, param1Int1, param1Int2, 13, true, true, false, bool1);
/* 158 */       } else if (bool8) {
/*     */         
/* 160 */         drawCheckBezel(param1Graphics, param1Int1, param1Int2, 13, false, false, true, bool1);
/* 161 */       } else if (!bool1) {
/*     */         
/* 163 */         drawCheckBezelOut(param1Graphics, param1Int1, param1Int2, 13);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int getIconWidth() {
/* 168 */       return 13;
/*     */     }
/*     */     
/*     */     public int getIconHeight() {
/* 172 */       return 13;
/*     */     }
/*     */     
/*     */     public void drawCheckBezelOut(Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3) {
/* 176 */       Color color1 = UIManager.getColor("controlShadow");
/*     */       
/* 178 */       int i = param1Int3;
/* 179 */       int j = param1Int3;
/* 180 */       Color color2 = param1Graphics.getColor();
/*     */       
/* 182 */       param1Graphics.translate(param1Int1, param1Int2);
/* 183 */       param1Graphics.setColor(this.highlight);
/* 184 */       param1Graphics.drawLine(0, 0, 0, j - 1);
/* 185 */       param1Graphics.drawLine(1, 0, i - 1, 0);
/*     */       
/* 187 */       param1Graphics.setColor(this.shadow);
/* 188 */       param1Graphics.drawLine(1, j - 1, i - 1, j - 1);
/* 189 */       param1Graphics.drawLine(i - 1, j - 1, i - 1, 1);
/* 190 */       param1Graphics.translate(-param1Int1, -param1Int2);
/* 191 */       param1Graphics.setColor(color2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void drawCheckBezel(Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3, boolean param1Boolean4) {
/* 199 */       Color color = param1Graphics.getColor();
/* 200 */       param1Graphics.translate(param1Int1, param1Int2);
/*     */ 
/*     */ 
/*     */       
/* 204 */       if (!param1Boolean4) {
/* 205 */         if (param1Boolean2) {
/* 206 */           param1Graphics.setColor(this.control);
/* 207 */           param1Graphics.fillRect(1, 1, param1Int3 - 2, param1Int3 - 2);
/* 208 */           param1Graphics.setColor(this.shadow);
/*     */         } else {
/* 210 */           param1Graphics.setColor(this.lightShadow);
/* 211 */           param1Graphics.fillRect(0, 0, param1Int3, param1Int3);
/* 212 */           param1Graphics.setColor(this.highlight);
/*     */         } 
/*     */         
/* 215 */         param1Graphics.drawLine(1, param1Int3 - 1, param1Int3 - 2, param1Int3 - 1);
/* 216 */         if (param1Boolean1) {
/* 217 */           param1Graphics.drawLine(2, param1Int3 - 2, param1Int3 - 3, param1Int3 - 2);
/* 218 */           param1Graphics.drawLine(param1Int3 - 2, 2, param1Int3 - 2, param1Int3 - 1);
/* 219 */           if (param1Boolean2) {
/* 220 */             param1Graphics.setColor(this.highlight);
/*     */           } else {
/* 222 */             param1Graphics.setColor(this.shadow);
/*     */           } 
/* 224 */           param1Graphics.drawLine(1, 2, 1, param1Int3 - 2);
/* 225 */           param1Graphics.drawLine(1, 1, param1Int3 - 3, 1);
/* 226 */           if (param1Boolean2) {
/* 227 */             param1Graphics.setColor(this.shadow);
/*     */           } else {
/* 229 */             param1Graphics.setColor(this.highlight);
/*     */           } 
/*     */         } 
/*     */         
/* 233 */         param1Graphics.drawLine(param1Int3 - 1, 1, param1Int3 - 1, param1Int3 - 1);
/*     */ 
/*     */         
/* 236 */         if (param1Boolean2) {
/* 237 */           param1Graphics.setColor(this.highlight);
/*     */         } else {
/* 239 */           param1Graphics.setColor(this.shadow);
/*     */         } 
/* 241 */         param1Graphics.drawLine(0, 1, 0, param1Int3 - 1);
/*     */ 
/*     */         
/* 244 */         param1Graphics.drawLine(0, 0, param1Int3 - 1, 0);
/*     */       } 
/*     */       
/* 247 */       if (param1Boolean3) {
/*     */         
/* 249 */         param1Graphics.setColor(this.foreground);
/* 250 */         param1Graphics.drawLine(param1Int3 - 2, 1, param1Int3 - 2, 2);
/* 251 */         param1Graphics.drawLine(param1Int3 - 3, 2, param1Int3 - 3, 3);
/* 252 */         param1Graphics.drawLine(param1Int3 - 4, 3, param1Int3 - 4, 4);
/* 253 */         param1Graphics.drawLine(param1Int3 - 5, 4, param1Int3 - 5, 6);
/* 254 */         param1Graphics.drawLine(param1Int3 - 6, 5, param1Int3 - 6, 8);
/* 255 */         param1Graphics.drawLine(param1Int3 - 7, 6, param1Int3 - 7, 10);
/* 256 */         param1Graphics.drawLine(param1Int3 - 8, 7, param1Int3 - 8, 10);
/* 257 */         param1Graphics.drawLine(param1Int3 - 9, 6, param1Int3 - 9, 9);
/* 258 */         param1Graphics.drawLine(param1Int3 - 10, 5, param1Int3 - 10, 8);
/* 259 */         param1Graphics.drawLine(param1Int3 - 11, 5, param1Int3 - 11, 7);
/* 260 */         param1Graphics.drawLine(param1Int3 - 12, 6, param1Int3 - 12, 6);
/*     */       } 
/* 262 */       param1Graphics.translate(-param1Int1, -param1Int2);
/* 263 */       param1Graphics.setColor(color);
/*     */     }
/*     */     
/*     */     private CheckBoxIcon() {} }
/*     */   
/* 268 */   private static class RadioButtonIcon implements Icon, UIResource, Serializable { private Color dot = UIManager.getColor("activeCaptionBorder");
/* 269 */     private Color highlight = UIManager.getColor("controlHighlight");
/* 270 */     private Color shadow = UIManager.getColor("controlShadow");
/*     */ 
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 274 */       AbstractButton abstractButton = (AbstractButton)param1Component;
/* 275 */       ButtonModel buttonModel = abstractButton.getModel();
/*     */       
/* 277 */       int i = getIconWidth();
/* 278 */       int j = getIconHeight();
/*     */       
/* 280 */       boolean bool1 = buttonModel.isPressed();
/* 281 */       boolean bool2 = buttonModel.isArmed();
/* 282 */       boolean bool3 = buttonModel.isEnabled();
/* 283 */       boolean bool4 = buttonModel.isSelected();
/*     */       
/* 285 */       boolean bool = ((bool1 && !bool2 && bool4) || (bool1 && bool2 && !bool4) || (!bool1 && bool2 && bool4) || (!bool1 && !bool2 && bool4)) ? true : false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 299 */       if (bool) {
/* 300 */         param1Graphics.setColor(this.shadow);
/* 301 */         param1Graphics.drawLine(param1Int1 + 5, param1Int2 + 0, param1Int1 + 8, param1Int2 + 0);
/* 302 */         param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 1, param1Int1 + 4, param1Int2 + 1);
/* 303 */         param1Graphics.drawLine(param1Int1 + 9, param1Int2 + 1, param1Int1 + 9, param1Int2 + 1);
/* 304 */         param1Graphics.drawLine(param1Int1 + 2, param1Int2 + 2, param1Int1 + 2, param1Int2 + 2);
/* 305 */         param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 3, param1Int1 + 1, param1Int2 + 3);
/* 306 */         param1Graphics.drawLine(param1Int1, param1Int2 + 4, param1Int1, param1Int2 + 9);
/* 307 */         param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 10, param1Int1 + 1, param1Int2 + 10);
/* 308 */         param1Graphics.drawLine(param1Int1 + 2, param1Int2 + 11, param1Int1 + 2, param1Int2 + 11);
/* 309 */         param1Graphics.setColor(this.highlight);
/* 310 */         param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 12, param1Int1 + 4, param1Int2 + 12);
/* 311 */         param1Graphics.drawLine(param1Int1 + 5, param1Int2 + 13, param1Int1 + 8, param1Int2 + 13);
/* 312 */         param1Graphics.drawLine(param1Int1 + 9, param1Int2 + 12, param1Int1 + 10, param1Int2 + 12);
/* 313 */         param1Graphics.drawLine(param1Int1 + 11, param1Int2 + 11, param1Int1 + 11, param1Int2 + 11);
/* 314 */         param1Graphics.drawLine(param1Int1 + 12, param1Int2 + 10, param1Int1 + 12, param1Int2 + 10);
/* 315 */         param1Graphics.drawLine(param1Int1 + 13, param1Int2 + 9, param1Int1 + 13, param1Int2 + 4);
/* 316 */         param1Graphics.drawLine(param1Int1 + 12, param1Int2 + 3, param1Int1 + 12, param1Int2 + 3);
/* 317 */         param1Graphics.drawLine(param1Int1 + 11, param1Int2 + 2, param1Int1 + 11, param1Int2 + 2);
/* 318 */         param1Graphics.drawLine(param1Int1 + 10, param1Int2 + 1, param1Int1 + 10, param1Int2 + 1);
/* 319 */         param1Graphics.setColor(this.dot);
/* 320 */         param1Graphics.fillRect(param1Int1 + 4, param1Int2 + 5, 6, 4);
/* 321 */         param1Graphics.drawLine(param1Int1 + 5, param1Int2 + 4, param1Int1 + 8, param1Int2 + 4);
/* 322 */         param1Graphics.drawLine(param1Int1 + 5, param1Int2 + 9, param1Int1 + 8, param1Int2 + 9);
/*     */       } else {
/*     */         
/* 325 */         param1Graphics.setColor(this.highlight);
/* 326 */         param1Graphics.drawLine(param1Int1 + 5, param1Int2 + 0, param1Int1 + 8, param1Int2 + 0);
/* 327 */         param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 1, param1Int1 + 4, param1Int2 + 1);
/* 328 */         param1Graphics.drawLine(param1Int1 + 9, param1Int2 + 1, param1Int1 + 9, param1Int2 + 1);
/* 329 */         param1Graphics.drawLine(param1Int1 + 2, param1Int2 + 2, param1Int1 + 2, param1Int2 + 2);
/* 330 */         param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 3, param1Int1 + 1, param1Int2 + 3);
/* 331 */         param1Graphics.drawLine(param1Int1, param1Int2 + 4, param1Int1, param1Int2 + 9);
/* 332 */         param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 10, param1Int1 + 1, param1Int2 + 10);
/* 333 */         param1Graphics.drawLine(param1Int1 + 2, param1Int2 + 11, param1Int1 + 2, param1Int2 + 11);
/*     */         
/* 335 */         param1Graphics.setColor(this.shadow);
/* 336 */         param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 12, param1Int1 + 4, param1Int2 + 12);
/* 337 */         param1Graphics.drawLine(param1Int1 + 5, param1Int2 + 13, param1Int1 + 8, param1Int2 + 13);
/* 338 */         param1Graphics.drawLine(param1Int1 + 9, param1Int2 + 12, param1Int1 + 10, param1Int2 + 12);
/* 339 */         param1Graphics.drawLine(param1Int1 + 11, param1Int2 + 11, param1Int1 + 11, param1Int2 + 11);
/* 340 */         param1Graphics.drawLine(param1Int1 + 12, param1Int2 + 10, param1Int1 + 12, param1Int2 + 10);
/* 341 */         param1Graphics.drawLine(param1Int1 + 13, param1Int2 + 9, param1Int1 + 13, param1Int2 + 4);
/* 342 */         param1Graphics.drawLine(param1Int1 + 12, param1Int2 + 3, param1Int1 + 12, param1Int2 + 3);
/* 343 */         param1Graphics.drawLine(param1Int1 + 11, param1Int2 + 2, param1Int1 + 11, param1Int2 + 2);
/* 344 */         param1Graphics.drawLine(param1Int1 + 10, param1Int2 + 1, param1Int1 + 10, param1Int2 + 1);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int getIconWidth() {
/* 350 */       return 14;
/*     */     }
/*     */     
/*     */     public int getIconHeight() {
/* 354 */       return 14;
/*     */     }
/*     */     
/*     */     private RadioButtonIcon() {} }
/*     */ 
/*     */   
/*     */   private static class MenuItemCheckIcon implements Icon, UIResource, Serializable {
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {}
/*     */     
/* 363 */     public int getIconWidth() { return 0; } public int getIconHeight() {
/* 364 */       return 0;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MenuItemArrowIcon implements Icon, UIResource, Serializable {
/*     */     private MenuItemArrowIcon() {}
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {}
/*     */     
/* 373 */     public int getIconWidth() { return 0; } public int getIconHeight() {
/* 374 */       return 0;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MenuArrowIcon implements Icon, UIResource, Serializable {
/* 379 */     private Color focus = UIManager.getColor("windowBorder");
/* 380 */     private Color shadow = UIManager.getColor("controlShadow");
/* 381 */     private Color highlight = UIManager.getColor("controlHighlight");
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 384 */       AbstractButton abstractButton = (AbstractButton)param1Component;
/* 385 */       ButtonModel buttonModel = abstractButton.getModel();
/*     */ 
/*     */ 
/*     */       
/* 389 */       int i = getIconWidth();
/* 390 */       int j = getIconHeight();
/*     */       
/* 392 */       Color color = param1Graphics.getColor();
/*     */       
/* 394 */       if (buttonModel.isSelected()) {
/* 395 */         if (MotifGraphicsUtils.isLeftToRight(param1Component)) {
/* 396 */           param1Graphics.setColor(this.shadow);
/* 397 */           param1Graphics.fillRect(param1Int1 + 1, param1Int2 + 1, 2, j);
/* 398 */           param1Graphics.drawLine(param1Int1 + 4, param1Int2 + 2, param1Int1 + 4, param1Int2 + 2);
/* 399 */           param1Graphics.drawLine(param1Int1 + 6, param1Int2 + 3, param1Int1 + 6, param1Int2 + 3);
/* 400 */           param1Graphics.drawLine(param1Int1 + 8, param1Int2 + 4, param1Int1 + 8, param1Int2 + 5);
/* 401 */           param1Graphics.setColor(this.focus);
/* 402 */           param1Graphics.fillRect(param1Int1 + 2, param1Int2 + 2, 2, j - 2);
/* 403 */           param1Graphics.fillRect(param1Int1 + 4, param1Int2 + 3, 2, j - 4);
/* 404 */           param1Graphics.fillRect(param1Int1 + 6, param1Int2 + 4, 2, j - 6);
/* 405 */           param1Graphics.setColor(this.highlight);
/* 406 */           param1Graphics.drawLine(param1Int1 + 2, param1Int2 + j, param1Int1 + 2, param1Int2 + j);
/* 407 */           param1Graphics.drawLine(param1Int1 + 4, param1Int2 + j - 1, param1Int1 + 4, param1Int2 + j - 1);
/* 408 */           param1Graphics.drawLine(param1Int1 + 6, param1Int2 + j - 2, param1Int1 + 6, param1Int2 + j - 2);
/* 409 */           param1Graphics.drawLine(param1Int1 + 8, param1Int2 + j - 4, param1Int1 + 8, param1Int2 + j - 3);
/*     */         } else {
/* 411 */           param1Graphics.setColor(this.highlight);
/* 412 */           param1Graphics.fillRect(param1Int1 + 7, param1Int2 + 1, 2, 10);
/* 413 */           param1Graphics.drawLine(param1Int1 + 5, param1Int2 + 9, param1Int1 + 5, param1Int2 + 9);
/* 414 */           param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 8, param1Int1 + 3, param1Int2 + 8);
/* 415 */           param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 6, param1Int1 + 1, param1Int2 + 7);
/* 416 */           param1Graphics.setColor(this.focus);
/* 417 */           param1Graphics.fillRect(param1Int1 + 6, param1Int2 + 2, 2, 8);
/* 418 */           param1Graphics.fillRect(param1Int1 + 4, param1Int2 + 3, 2, 6);
/* 419 */           param1Graphics.fillRect(param1Int1 + 2, param1Int2 + 4, 2, 4);
/* 420 */           param1Graphics.setColor(this.shadow);
/* 421 */           param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 4, param1Int1 + 1, param1Int2 + 5);
/* 422 */           param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 3, param1Int1 + 3, param1Int2 + 3);
/* 423 */           param1Graphics.drawLine(param1Int1 + 5, param1Int2 + 2, param1Int1 + 5, param1Int2 + 2);
/* 424 */           param1Graphics.drawLine(param1Int1 + 7, param1Int2 + 1, param1Int1 + 7, param1Int2 + 1);
/*     */         }
/*     */       
/* 427 */       } else if (MotifGraphicsUtils.isLeftToRight(param1Component)) {
/* 428 */         param1Graphics.setColor(this.highlight);
/* 429 */         param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 1, param1Int1 + 1, param1Int2 + j);
/* 430 */         param1Graphics.drawLine(param1Int1 + 2, param1Int2 + 1, param1Int1 + 2, param1Int2 + j - 2);
/* 431 */         param1Graphics.fillRect(param1Int1 + 3, param1Int2 + 2, 2, 2);
/* 432 */         param1Graphics.fillRect(param1Int1 + 5, param1Int2 + 3, 2, 2);
/* 433 */         param1Graphics.fillRect(param1Int1 + 7, param1Int2 + 4, 2, 2);
/* 434 */         param1Graphics.setColor(this.shadow);
/* 435 */         param1Graphics.drawLine(param1Int1 + 2, param1Int2 + j - 1, param1Int1 + 2, param1Int2 + j);
/* 436 */         param1Graphics.fillRect(param1Int1 + 3, param1Int2 + j - 2, 2, 2);
/* 437 */         param1Graphics.fillRect(param1Int1 + 5, param1Int2 + j - 3, 2, 2);
/* 438 */         param1Graphics.fillRect(param1Int1 + 7, param1Int2 + j - 4, 2, 2);
/* 439 */         param1Graphics.setColor(color);
/*     */       } else {
/* 441 */         param1Graphics.setColor(this.highlight);
/* 442 */         param1Graphics.fillRect(param1Int1 + 1, param1Int2 + 4, 2, 2);
/* 443 */         param1Graphics.fillRect(param1Int1 + 3, param1Int2 + 3, 2, 2);
/* 444 */         param1Graphics.fillRect(param1Int1 + 5, param1Int2 + 2, 2, 2);
/* 445 */         param1Graphics.drawLine(param1Int1 + 7, param1Int2 + 1, param1Int1 + 7, param1Int2 + 2);
/* 446 */         param1Graphics.setColor(this.shadow);
/* 447 */         param1Graphics.fillRect(param1Int1 + 1, param1Int2 + j - 4, 2, 2);
/* 448 */         param1Graphics.fillRect(param1Int1 + 3, param1Int2 + j - 3, 2, 2);
/* 449 */         param1Graphics.fillRect(param1Int1 + 5, param1Int2 + j - 2, 2, 2);
/* 450 */         param1Graphics.drawLine(param1Int1 + 7, param1Int2 + 3, param1Int1 + 7, param1Int2 + j);
/* 451 */         param1Graphics.drawLine(param1Int1 + 8, param1Int2 + 1, param1Int1 + 8, param1Int2 + j);
/* 452 */         param1Graphics.setColor(color);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int getIconWidth() {
/* 457 */       return 10; } public int getIconHeight() {
/* 458 */       return 10;
/*     */     }
/*     */     
/*     */     private MenuArrowIcon() {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifIconFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */