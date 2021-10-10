/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.plaf.UIResource;
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
/*     */ public class MotifBorders
/*     */ {
/*     */   public static class BevelBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/*  58 */     private Color darkShadow = UIManager.getColor("controlShadow");
/*  59 */     private Color lightShadow = UIManager.getColor("controlLtHighlight");
/*     */     private boolean isRaised;
/*     */     
/*     */     public BevelBorder(boolean param1Boolean, Color param1Color1, Color param1Color2) {
/*  63 */       this.isRaised = param1Boolean;
/*  64 */       this.darkShadow = param1Color1;
/*  65 */       this.lightShadow = param1Color2;
/*     */     }
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  69 */       param1Graphics.setColor(this.isRaised ? this.lightShadow : this.darkShadow);
/*  70 */       param1Graphics.drawLine(param1Int1, param1Int2, param1Int1 + param1Int3 - 1, param1Int2);
/*  71 */       param1Graphics.drawLine(param1Int1, param1Int2 + param1Int4 - 1, param1Int1, param1Int2 + 1);
/*     */       
/*  73 */       param1Graphics.setColor(this.isRaised ? this.darkShadow : this.lightShadow);
/*  74 */       param1Graphics.drawLine(param1Int1 + 1, param1Int2 + param1Int4 - 1, param1Int1 + param1Int3 - 1, param1Int2 + param1Int4 - 1);
/*  75 */       param1Graphics.drawLine(param1Int1 + param1Int3 - 1, param1Int2 + param1Int4 - 1, param1Int1 + param1Int3 - 1, param1Int2 + 1);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/*  79 */       param1Insets.set(1, 1, 1, 1);
/*  80 */       return param1Insets;
/*     */     }
/*     */     
/*     */     public boolean isOpaque(Component param1Component) {
/*  84 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class FocusBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource {
/*     */     private Color focus;
/*     */     private Color control;
/*     */     
/*     */     public FocusBorder(Color param1Color1, Color param1Color2) {
/*  95 */       this.control = param1Color1;
/*  96 */       this.focus = param1Color2;
/*     */     }
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 100 */       if (param1Component.hasFocus()) {
/* 101 */         param1Graphics.setColor(this.focus);
/* 102 */         param1Graphics.drawRect(param1Int1, param1Int2, param1Int3 - 1, param1Int4 - 1);
/*     */       } else {
/* 104 */         param1Graphics.setColor(this.control);
/* 105 */         param1Graphics.drawRect(param1Int1, param1Int2, param1Int3 - 1, param1Int4 - 1);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 110 */       param1Insets.set(1, 1, 1, 1);
/* 111 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ButtonBorder
/*     */     extends AbstractBorder implements UIResource {
/* 117 */     protected Color focus = UIManager.getColor("activeCaptionBorder");
/* 118 */     protected Color shadow = UIManager.getColor("Button.shadow");
/* 119 */     protected Color highlight = UIManager.getColor("Button.light");
/*     */     protected Color darkShadow;
/*     */     
/*     */     public ButtonBorder(Color param1Color1, Color param1Color2, Color param1Color3, Color param1Color4) {
/* 123 */       this.shadow = param1Color1;
/* 124 */       this.highlight = param1Color2;
/* 125 */       this.darkShadow = param1Color3;
/* 126 */       this.focus = param1Color4;
/*     */     }
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 130 */       boolean bool1 = false;
/* 131 */       boolean bool2 = false;
/* 132 */       boolean bool3 = false;
/* 133 */       boolean bool4 = false;
/*     */       
/* 135 */       if (param1Component instanceof AbstractButton) {
/* 136 */         AbstractButton abstractButton = (AbstractButton)param1Component;
/* 137 */         ButtonModel buttonModel = abstractButton.getModel();
/*     */         
/* 139 */         bool1 = (buttonModel.isArmed() && buttonModel.isPressed()) ? true : false;
/*     */         
/* 141 */         bool2 = ((buttonModel.isArmed() && bool1) || (abstractButton.isFocusPainted() && abstractButton.hasFocus())) ? true : false;
/* 142 */         if (abstractButton instanceof JButton) {
/* 143 */           bool3 = ((JButton)abstractButton).isDefaultCapable();
/* 144 */           bool4 = ((JButton)abstractButton).isDefaultButton();
/*     */         } 
/*     */       } 
/* 147 */       int i = param1Int1 + 1;
/* 148 */       int j = param1Int2 + 1;
/* 149 */       int k = param1Int1 + param1Int3 - 2;
/* 150 */       int m = param1Int2 + param1Int4 - 2;
/*     */       
/* 152 */       if (bool3) {
/* 153 */         if (bool4) {
/* 154 */           param1Graphics.setColor(this.shadow);
/* 155 */           param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 3, param1Int1 + 3, param1Int2 + param1Int4 - 4);
/* 156 */           param1Graphics.drawLine(param1Int1 + 3, param1Int2 + 3, param1Int1 + param1Int3 - 4, param1Int2 + 3);
/*     */           
/* 158 */           param1Graphics.setColor(this.highlight);
/* 159 */           param1Graphics.drawLine(param1Int1 + 4, param1Int2 + param1Int4 - 4, param1Int1 + param1Int3 - 4, param1Int2 + param1Int4 - 4);
/* 160 */           param1Graphics.drawLine(param1Int1 + param1Int3 - 4, param1Int2 + 3, param1Int1 + param1Int3 - 4, param1Int2 + param1Int4 - 4);
/*     */         } 
/* 162 */         i += 6;
/* 163 */         j += 6;
/* 164 */         k -= 6;
/* 165 */         m -= 6;
/*     */       } 
/*     */       
/* 168 */       if (bool2) {
/* 169 */         param1Graphics.setColor(this.focus);
/* 170 */         if (bool4) {
/* 171 */           param1Graphics.drawRect(param1Int1, param1Int2, param1Int3 - 1, param1Int4 - 1);
/*     */         } else {
/* 173 */           param1Graphics.drawRect(i - 1, j - 1, k - i + 2, m - j + 2);
/*     */         } 
/*     */       } 
/*     */       
/* 177 */       param1Graphics.setColor(bool1 ? this.shadow : this.highlight);
/* 178 */       param1Graphics.drawLine(i, j, k, j);
/* 179 */       param1Graphics.drawLine(i, j, i, m);
/*     */       
/* 181 */       param1Graphics.setColor(bool1 ? this.highlight : this.shadow);
/* 182 */       param1Graphics.drawLine(k, j + 1, k, m);
/* 183 */       param1Graphics.drawLine(i + 1, m, k, m);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 187 */       byte b = (param1Component instanceof JButton && ((JButton)param1Component).isDefaultCapable()) ? 8 : 2;
/* 188 */       param1Insets.set(b, b, b, b);
/* 189 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ToggleButtonBorder
/*     */     extends ButtonBorder
/*     */   {
/*     */     public ToggleButtonBorder(Color param1Color1, Color param1Color2, Color param1Color3, Color param1Color4) {
/* 197 */       super(param1Color1, param1Color2, param1Color3, param1Color4);
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 202 */       if (param1Component instanceof AbstractButton) {
/* 203 */         AbstractButton abstractButton = (AbstractButton)param1Component;
/* 204 */         ButtonModel buttonModel = abstractButton.getModel();
/*     */         
/* 206 */         if ((buttonModel.isArmed() && buttonModel.isPressed()) || buttonModel.isSelected()) {
/* 207 */           MotifBorders.drawBezel(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, (buttonModel
/* 208 */               .isPressed() || buttonModel.isSelected()), (abstractButton
/* 209 */               .isFocusPainted() && abstractButton.hasFocus()), this.shadow, this.highlight, this.darkShadow, this.focus);
/*     */         } else {
/* 211 */           MotifBorders.drawBezel(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, false, (abstractButton
/* 212 */               .isFocusPainted() && abstractButton.hasFocus()), this.shadow, this.highlight, this.darkShadow, this.focus);
/*     */         } 
/*     */       } else {
/*     */         
/* 216 */         MotifBorders.drawBezel(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, false, false, this.shadow, this.highlight, this.darkShadow, this.focus);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 222 */       param1Insets.set(2, 2, 3, 3);
/* 223 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MenuBarBorder
/*     */     extends ButtonBorder {
/*     */     public MenuBarBorder(Color param1Color1, Color param1Color2, Color param1Color3, Color param1Color4) {
/* 230 */       super(param1Color1, param1Color2, param1Color3, param1Color4);
/*     */     }
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 234 */       if (!(param1Component instanceof JMenuBar)) {
/*     */         return;
/*     */       }
/* 237 */       JMenuBar jMenuBar = (JMenuBar)param1Component;
/* 238 */       if (jMenuBar.isBorderPainted() == true) {
/*     */         
/* 240 */         Dimension dimension = jMenuBar.getSize();
/* 241 */         MotifBorders.drawBezel(param1Graphics, param1Int1, param1Int2, dimension.width, dimension.height, false, false, this.shadow, this.highlight, this.darkShadow, this.focus);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 247 */       param1Insets.set(6, 6, 6, 6);
/* 248 */       return param1Insets;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class FrameBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/*     */     JComponent jcomp;
/*     */     
/*     */     Color frameHighlight;
/*     */     Color frameColor;
/*     */     Color frameShadow;
/*     */     public static final int BORDER_SIZE = 5;
/*     */     
/*     */     public FrameBorder(JComponent param1JComponent) {
/* 265 */       this.jcomp = param1JComponent;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setComponent(JComponent param1JComponent) {
/* 271 */       this.jcomp = param1JComponent;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JComponent component() {
/* 278 */       return this.jcomp;
/*     */     }
/*     */     
/*     */     protected Color getFrameHighlight() {
/* 282 */       return this.frameHighlight;
/*     */     }
/*     */     
/*     */     protected Color getFrameColor() {
/* 286 */       return this.frameColor;
/*     */     }
/*     */     
/*     */     protected Color getFrameShadow() {
/* 290 */       return this.frameShadow;
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 294 */       param1Insets.set(5, 5, 5, 5);
/* 295 */       return param1Insets;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean drawTopBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 302 */       Rectangle rectangle = new Rectangle(param1Int1, param1Int2, param1Int3, 5);
/* 303 */       if (!param1Graphics.getClipBounds().intersects(rectangle)) {
/* 304 */         return false;
/*     */       }
/*     */       
/* 307 */       int i = param1Int3 - 1;
/* 308 */       byte b = 4;
/*     */ 
/*     */       
/* 311 */       param1Graphics.setColor(this.frameColor);
/* 312 */       param1Graphics.drawLine(param1Int1, param1Int2 + 2, i - 2, param1Int2 + 2);
/* 313 */       param1Graphics.drawLine(param1Int1, param1Int2 + 3, i - 2, param1Int2 + 3);
/* 314 */       param1Graphics.drawLine(param1Int1, param1Int2 + 4, i - 2, param1Int2 + 4);
/*     */ 
/*     */       
/* 317 */       param1Graphics.setColor(this.frameHighlight);
/* 318 */       param1Graphics.drawLine(param1Int1, param1Int2, i, param1Int2);
/* 319 */       param1Graphics.drawLine(param1Int1, param1Int2 + 1, i, param1Int2 + 1);
/* 320 */       param1Graphics.drawLine(param1Int1, param1Int2 + 2, param1Int1, param1Int2 + 4);
/* 321 */       param1Graphics.drawLine(param1Int1 + 1, param1Int2 + 2, param1Int1 + 1, param1Int2 + 4);
/*     */ 
/*     */       
/* 324 */       param1Graphics.setColor(this.frameShadow);
/* 325 */       param1Graphics.drawLine(param1Int1 + 4, param1Int2 + 4, i - 4, param1Int2 + 4);
/* 326 */       param1Graphics.drawLine(i, param1Int2 + 1, i, b);
/* 327 */       param1Graphics.drawLine(i - 1, param1Int2 + 2, i - 1, b);
/*     */       
/* 329 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean drawLeftBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 337 */       Rectangle rectangle = new Rectangle(0, 0, (getBorderInsets(param1Component)).left, param1Int4);
/* 338 */       if (!param1Graphics.getClipBounds().intersects(rectangle)) {
/* 339 */         return false;
/*     */       }
/*     */       
/* 342 */       byte b = 5;
/*     */       
/* 344 */       param1Graphics.setColor(this.frameHighlight);
/* 345 */       param1Graphics.drawLine(param1Int1, b, param1Int1, param1Int4 - 1);
/* 346 */       param1Graphics.drawLine(param1Int1 + 1, b, param1Int1 + 1, param1Int4 - 2);
/*     */       
/* 348 */       param1Graphics.setColor(this.frameColor);
/* 349 */       param1Graphics.fillRect(param1Int1 + 2, b, param1Int1 + 2, param1Int4 - 3);
/*     */       
/* 351 */       param1Graphics.setColor(this.frameShadow);
/* 352 */       param1Graphics.drawLine(param1Int1 + 4, b, param1Int1 + 4, param1Int4 - 5);
/*     */       
/* 354 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean drawRightBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 363 */       Rectangle rectangle = new Rectangle(param1Int3 - (getBorderInsets(param1Component)).right, 0, (getBorderInsets(param1Component)).right, param1Int4);
/* 364 */       if (!param1Graphics.getClipBounds().intersects(rectangle)) {
/* 365 */         return false;
/*     */       }
/*     */       
/* 368 */       int i = param1Int3 - (getBorderInsets(param1Component)).right;
/* 369 */       byte b = 5;
/*     */       
/* 371 */       param1Graphics.setColor(this.frameColor);
/* 372 */       param1Graphics.fillRect(i + 1, b, 2, param1Int4 - 1);
/*     */       
/* 374 */       param1Graphics.setColor(this.frameShadow);
/* 375 */       param1Graphics.fillRect(i + 3, b, 2, param1Int4 - 1);
/*     */       
/* 377 */       param1Graphics.setColor(this.frameHighlight);
/* 378 */       param1Graphics.drawLine(i, b, i, param1Int4 - 1);
/*     */       
/* 380 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean drawBottomBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 391 */       Rectangle rectangle = new Rectangle(0, param1Int4 - (getBorderInsets(param1Component)).bottom, param1Int3, (getBorderInsets(param1Component)).bottom);
/* 392 */       if (!param1Graphics.getClipBounds().intersects(rectangle)) {
/* 393 */         return false;
/*     */       }
/*     */       
/* 396 */       int i = param1Int4 - (getBorderInsets(param1Component)).bottom;
/*     */       
/* 398 */       param1Graphics.setColor(this.frameShadow);
/* 399 */       param1Graphics.drawLine(param1Int1 + 1, param1Int4 - 1, param1Int3 - 1, param1Int4 - 1);
/* 400 */       param1Graphics.drawLine(param1Int1 + 2, param1Int4 - 2, param1Int3 - 2, param1Int4 - 2);
/*     */       
/* 402 */       param1Graphics.setColor(this.frameColor);
/* 403 */       param1Graphics.fillRect(param1Int1 + 2, i + 1, param1Int3 - 4, 2);
/*     */       
/* 405 */       param1Graphics.setColor(this.frameHighlight);
/* 406 */       param1Graphics.drawLine(param1Int1 + 5, i, param1Int3 - 5, i);
/*     */       
/* 408 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isActiveFrame() {
/* 413 */       return this.jcomp.hasFocus();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 422 */       if (isActiveFrame()) {
/* 423 */         this.frameColor = UIManager.getColor("activeCaptionBorder");
/*     */       } else {
/* 425 */         this.frameColor = UIManager.getColor("inactiveCaptionBorder");
/*     */       } 
/* 427 */       this.frameHighlight = this.frameColor.brighter();
/* 428 */       this.frameShadow = this.frameColor.darker().darker();
/*     */       
/* 430 */       drawTopBorder(param1Component, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/* 431 */       drawLeftBorder(param1Component, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/* 432 */       drawRightBorder(param1Component, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/* 433 */       drawBottomBorder(param1Component, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class InternalFrameBorder
/*     */     extends FrameBorder
/*     */   {
/*     */     JInternalFrame frame;
/*     */     
/*     */     public static final int CORNER_SIZE = 24;
/*     */ 
/*     */     
/*     */     public InternalFrameBorder(JInternalFrame param1JInternalFrame) {
/* 448 */       super(param1JInternalFrame);
/* 449 */       this.frame = param1JInternalFrame;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setFrame(JInternalFrame param1JInternalFrame) {
/* 455 */       this.frame = param1JInternalFrame;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JInternalFrame frame() {
/* 462 */       return this.frame;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int resizePartWidth() {
/* 474 */       if (!this.frame.isResizable()) {
/* 475 */         return 0;
/*     */       }
/* 477 */       return 5;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean drawTopBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 484 */       if (super.drawTopBorder(param1Component, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4) && this.frame
/* 485 */         .isResizable()) {
/* 486 */         param1Graphics.setColor(getFrameShadow());
/* 487 */         param1Graphics.drawLine(23, param1Int2 + 1, 23, param1Int2 + 4);
/* 488 */         param1Graphics.drawLine(param1Int3 - 24 - 1, param1Int2 + 1, param1Int3 - 24 - 1, param1Int2 + 4);
/*     */ 
/*     */         
/* 491 */         param1Graphics.setColor(getFrameHighlight());
/* 492 */         param1Graphics.drawLine(24, param1Int2, 24, param1Int2 + 4);
/* 493 */         param1Graphics.drawLine(param1Int3 - 24, param1Int2, param1Int3 - 24, param1Int2 + 4);
/* 494 */         return true;
/*     */       } 
/* 496 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean drawLeftBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 503 */       if (super.drawLeftBorder(param1Component, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4) && this.frame
/* 504 */         .isResizable()) {
/* 505 */         param1Graphics.setColor(getFrameHighlight());
/* 506 */         int i = param1Int2 + 24;
/* 507 */         param1Graphics.drawLine(param1Int1, i, param1Int1 + 4, i);
/* 508 */         int j = param1Int4 - 24;
/* 509 */         param1Graphics.drawLine(param1Int1 + 1, j, param1Int1 + 5, j);
/* 510 */         param1Graphics.setColor(getFrameShadow());
/* 511 */         param1Graphics.drawLine(param1Int1 + 1, i - 1, param1Int1 + 5, i - 1);
/* 512 */         param1Graphics.drawLine(param1Int1 + 1, j - 1, param1Int1 + 5, j - 1);
/* 513 */         return true;
/*     */       } 
/* 515 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean drawRightBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 522 */       if (super.drawRightBorder(param1Component, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4) && this.frame
/* 523 */         .isResizable()) {
/* 524 */         int i = param1Int3 - (getBorderInsets(param1Component)).right;
/* 525 */         param1Graphics.setColor(getFrameHighlight());
/* 526 */         int j = param1Int2 + 24;
/* 527 */         param1Graphics.drawLine(i, j, param1Int3 - 2, j);
/* 528 */         int k = param1Int4 - 24;
/* 529 */         param1Graphics.drawLine(i + 1, k, i + 3, k);
/* 530 */         param1Graphics.setColor(getFrameShadow());
/* 531 */         param1Graphics.drawLine(i + 1, j - 1, param1Int3 - 2, j - 1);
/* 532 */         param1Graphics.drawLine(i + 1, k - 1, i + 3, k - 1);
/* 533 */         return true;
/*     */       } 
/* 535 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean drawBottomBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 542 */       if (super.drawBottomBorder(param1Component, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4) && this.frame
/* 543 */         .isResizable()) {
/* 544 */         int i = param1Int4 - (getBorderInsets(param1Component)).bottom;
/*     */         
/* 546 */         param1Graphics.setColor(getFrameShadow());
/* 547 */         param1Graphics.drawLine(23, i + 1, 23, param1Int4 - 1);
/*     */         
/* 549 */         param1Graphics.drawLine(param1Int3 - 24, i + 1, param1Int3 - 24, param1Int4 - 1);
/*     */ 
/*     */         
/* 552 */         param1Graphics.setColor(getFrameHighlight());
/* 553 */         param1Graphics.drawLine(24, i, 24, param1Int4 - 2);
/* 554 */         param1Graphics.drawLine(param1Int3 - 24 + 1, i, param1Int3 - 24 + 1, param1Int4 - 2);
/*     */         
/* 556 */         return true;
/*     */       } 
/* 558 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isActiveFrame() {
/* 563 */       return this.frame.isSelected();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawBezel(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2, Color paramColor1, Color paramColor2, Color paramColor3, Color paramColor4) {
/* 572 */     Color color = paramGraphics.getColor();
/* 573 */     paramGraphics.translate(paramInt1, paramInt2);
/*     */     
/* 575 */     if (paramBoolean1) {
/* 576 */       if (paramBoolean2) {
/* 577 */         paramGraphics.setColor(paramColor4);
/* 578 */         paramGraphics.drawRect(0, 0, paramInt3 - 1, paramInt4 - 1);
/*     */       } 
/* 580 */       paramGraphics.setColor(paramColor1);
/* 581 */       paramGraphics.drawRect(1, 1, paramInt3 - 3, paramInt4 - 3);
/*     */       
/* 583 */       paramGraphics.setColor(paramColor2);
/* 584 */       paramGraphics.drawLine(2, paramInt4 - 3, paramInt3 - 3, paramInt4 - 3);
/* 585 */       paramGraphics.drawLine(paramInt3 - 3, 2, paramInt3 - 3, paramInt4 - 4);
/*     */     } else {
/*     */       
/* 588 */       if (paramBoolean2) {
/* 589 */         paramGraphics.setColor(paramColor4);
/* 590 */         paramGraphics.drawRect(0, 0, paramInt3 - 1, paramInt4 - 1);
/*     */         
/* 592 */         paramGraphics.setColor(paramColor2);
/* 593 */         paramGraphics.drawLine(1, 1, 1, paramInt4 - 3);
/* 594 */         paramGraphics.drawLine(2, 1, paramInt3 - 4, 1);
/*     */         
/* 596 */         paramGraphics.setColor(paramColor1);
/* 597 */         paramGraphics.drawLine(2, paramInt4 - 3, paramInt3 - 3, paramInt4 - 3);
/* 598 */         paramGraphics.drawLine(paramInt3 - 3, 1, paramInt3 - 3, paramInt4 - 4);
/*     */         
/* 600 */         paramGraphics.setColor(paramColor3);
/* 601 */         paramGraphics.drawLine(1, paramInt4 - 2, paramInt3 - 2, paramInt4 - 2);
/* 602 */         paramGraphics.drawLine(paramInt3 - 2, paramInt4 - 2, paramInt3 - 2, 1);
/*     */       } else {
/* 604 */         paramGraphics.setColor(paramColor2);
/* 605 */         paramGraphics.drawLine(1, 1, 1, paramInt4 - 3);
/* 606 */         paramGraphics.drawLine(2, 1, paramInt3 - 4, 1);
/* 607 */         paramGraphics.setColor(paramColor1);
/* 608 */         paramGraphics.drawLine(2, paramInt4 - 3, paramInt3 - 3, paramInt4 - 3);
/* 609 */         paramGraphics.drawLine(paramInt3 - 3, 1, paramInt3 - 3, paramInt4 - 4);
/*     */         
/* 611 */         paramGraphics.setColor(paramColor3);
/* 612 */         paramGraphics.drawLine(1, paramInt4 - 2, paramInt3 - 2, paramInt4 - 2);
/* 613 */         paramGraphics.drawLine(paramInt3 - 2, paramInt4 - 2, paramInt3 - 2, 0);
/*     */       } 
/*     */       
/* 616 */       paramGraphics.translate(-paramInt1, -paramInt2);
/*     */     } 
/* 618 */     paramGraphics.setColor(color);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MotifPopupMenuBorder
/*     */     extends AbstractBorder
/*     */     implements UIResource
/*     */   {
/*     */     protected Font font;
/*     */ 
/*     */     
/*     */     protected Color background;
/*     */     
/*     */     protected Color foreground;
/*     */     
/*     */     protected Color shadowColor;
/*     */     
/*     */     protected Color highlightColor;
/*     */     
/*     */     protected static final int TEXT_SPACING = 2;
/*     */     
/*     */     protected static final int GROOVE_HEIGHT = 2;
/*     */ 
/*     */     
/*     */     public MotifPopupMenuBorder(Font param1Font, Color param1Color1, Color param1Color2, Color param1Color3, Color param1Color4) {
/* 644 */       this.font = param1Font;
/* 645 */       this.background = param1Color1;
/* 646 */       this.foreground = param1Color2;
/* 647 */       this.shadowColor = param1Color3;
/* 648 */       this.highlightColor = param1Color4;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 662 */       if (!(param1Component instanceof JPopupMenu)) {
/*     */         return;
/*     */       }
/*     */       
/* 666 */       Font font = param1Graphics.getFont();
/* 667 */       Color color = param1Graphics.getColor();
/* 668 */       JPopupMenu jPopupMenu = (JPopupMenu)param1Component;
/*     */       
/* 670 */       String str = jPopupMenu.getLabel();
/* 671 */       if (str == null) {
/*     */         return;
/*     */       }
/*     */       
/* 675 */       param1Graphics.setFont(this.font);
/*     */       
/* 677 */       FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(jPopupMenu, param1Graphics, this.font);
/* 678 */       int i = fontMetrics.getHeight();
/* 679 */       int j = fontMetrics.getDescent();
/* 680 */       int k = fontMetrics.getAscent();
/* 681 */       Point point = new Point();
/* 682 */       int m = SwingUtilities2.stringWidth(jPopupMenu, fontMetrics, str);
/*     */ 
/*     */       
/* 685 */       point.y = param1Int2 + k + 2;
/* 686 */       point.x = param1Int1 + (param1Int3 - m) / 2;
/*     */       
/* 688 */       param1Graphics.setColor(this.background);
/* 689 */       param1Graphics.fillRect(point.x - 2, point.y - i - j, m + 4, i - j);
/*     */       
/* 691 */       param1Graphics.setColor(this.foreground);
/* 692 */       SwingUtilities2.drawString(jPopupMenu, param1Graphics, str, point.x, point.y);
/*     */       
/* 694 */       MotifGraphicsUtils.drawGroove(param1Graphics, param1Int1, point.y + 2, param1Int3, 2, this.shadowColor, this.highlightColor);
/*     */ 
/*     */ 
/*     */       
/* 698 */       param1Graphics.setFont(font);
/* 699 */       param1Graphics.setColor(color);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 708 */       if (!(param1Component instanceof JPopupMenu)) {
/* 709 */         return param1Insets;
/*     */       }
/*     */       
/* 712 */       int i = 0;
/* 713 */       int j = 16;
/*     */       
/* 715 */       String str = ((JPopupMenu)param1Component).getLabel();
/* 716 */       if (str == null) {
/* 717 */         param1Insets.left = param1Insets.top = param1Insets.right = param1Insets.bottom = 0;
/* 718 */         return param1Insets;
/*     */       } 
/*     */       
/* 721 */       FontMetrics fontMetrics = param1Component.getFontMetrics(this.font);
/*     */       
/* 723 */       if (fontMetrics != null) {
/* 724 */         i = fontMetrics.getDescent();
/* 725 */         j = fontMetrics.getAscent();
/*     */       } 
/*     */       
/* 728 */       param1Insets.top += j + i + 2 + 2;
/* 729 */       return param1Insets;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifBorders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */