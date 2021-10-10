/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.text.View;
/*     */ import sun.swing.MenuItemLayoutHelper;
/*     */ import sun.swing.SwingUtilities2;
/*     */ import sun.swing.plaf.synth.SynthIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthGraphicsUtils
/*     */ {
/*  45 */   private Rectangle paintIconR = new Rectangle();
/*  46 */   private Rectangle paintTextR = new Rectangle();
/*  47 */   private Rectangle paintViewR = new Rectangle();
/*  48 */   private Insets paintInsets = new Insets(0, 0, 0, 0);
/*     */ 
/*     */ 
/*     */   
/*  52 */   private Rectangle iconR = new Rectangle();
/*  53 */   private Rectangle textR = new Rectangle();
/*  54 */   private Rectangle viewR = new Rectangle();
/*  55 */   private Insets viewSizingInsets = new Insets(0, 0, 0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawLine(SynthContext paramSynthContext, Object paramObject, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  77 */     paramGraphics.drawLine(paramInt1, paramInt2, paramInt3, paramInt4);
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
/*     */   public void drawLine(SynthContext paramSynthContext, Object paramObject1, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Object paramObject2) {
/* 102 */     if ("dashed".equals(paramObject2)) {
/*     */       
/* 104 */       if (paramInt1 == paramInt3) {
/* 105 */         paramInt2 += paramInt2 % 2;
/*     */         
/* 107 */         for (int i = paramInt2; i <= paramInt4; i += 2) {
/* 108 */           paramGraphics.drawLine(paramInt1, i, paramInt3, i);
/*     */         }
/*     */       }
/* 111 */       else if (paramInt2 == paramInt4) {
/* 112 */         paramInt1 += paramInt1 % 2;
/*     */         
/* 114 */         for (int i = paramInt1; i <= paramInt3; i += 2) {
/* 115 */           paramGraphics.drawLine(i, paramInt2, i, paramInt4);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 120 */       drawLine(paramSynthContext, paramObject1, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String layoutText(SynthContext paramSynthContext, FontMetrics paramFontMetrics, String paramString, Icon paramIcon, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Rectangle paramRectangle1, Rectangle paramRectangle2, Rectangle paramRectangle3, int paramInt5) {
/* 146 */     if (paramIcon instanceof SynthIcon) {
/* 147 */       SynthIconWrapper synthIconWrapper = SynthIconWrapper.get((SynthIcon)paramIcon, paramSynthContext);
/*     */       
/* 149 */       String str = SwingUtilities.layoutCompoundLabel(paramSynthContext
/* 150 */           .getComponent(), paramFontMetrics, paramString, synthIconWrapper, paramInt2, paramInt1, paramInt4, paramInt3, paramRectangle1, paramRectangle2, paramRectangle3, paramInt5);
/*     */ 
/*     */       
/* 153 */       SynthIconWrapper.release(synthIconWrapper);
/* 154 */       return str;
/*     */     } 
/* 156 */     return SwingUtilities.layoutCompoundLabel(paramSynthContext
/* 157 */         .getComponent(), paramFontMetrics, paramString, paramIcon, paramInt2, paramInt1, paramInt4, paramInt3, paramRectangle1, paramRectangle2, paramRectangle3, paramInt5);
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
/*     */   public int computeStringWidth(SynthContext paramSynthContext, Font paramFont, FontMetrics paramFontMetrics, String paramString) {
/* 172 */     return SwingUtilities2.stringWidth(paramSynthContext.getComponent(), paramFontMetrics, paramString);
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
/*     */   public Dimension getMinimumSize(SynthContext paramSynthContext, Font paramFont, String paramString, Icon paramIcon, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 194 */     JComponent jComponent = paramSynthContext.getComponent();
/* 195 */     Dimension dimension = getPreferredSize(paramSynthContext, paramFont, paramString, paramIcon, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */ 
/*     */     
/* 198 */     View view = (View)jComponent.getClientProperty("html");
/*     */     
/* 200 */     if (view != null) {
/* 201 */       dimension
/* 202 */         .width = (int)(dimension.width - view.getPreferredSpan(0) - view.getMinimumSpan(0));
/*     */     }
/* 204 */     return dimension;
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
/*     */   public Dimension getMaximumSize(SynthContext paramSynthContext, Font paramFont, String paramString, Icon paramIcon, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 225 */     JComponent jComponent = paramSynthContext.getComponent();
/* 226 */     Dimension dimension = getPreferredSize(paramSynthContext, paramFont, paramString, paramIcon, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */ 
/*     */     
/* 229 */     View view = (View)jComponent.getClientProperty("html");
/*     */     
/* 231 */     if (view != null) {
/* 232 */       dimension
/* 233 */         .width = (int)(dimension.width + view.getMaximumSpan(0) - view.getPreferredSpan(0));
/*     */     }
/* 235 */     return dimension;
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
/*     */   public int getMaximumCharHeight(SynthContext paramSynthContext) {
/* 247 */     FontMetrics fontMetrics = paramSynthContext.getComponent().getFontMetrics(paramSynthContext
/* 248 */         .getStyle().getFont(paramSynthContext));
/* 249 */     return fontMetrics.getAscent() + fontMetrics.getDescent();
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
/*     */   public Dimension getPreferredSize(SynthContext paramSynthContext, Font paramFont, String paramString, Icon paramIcon, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 270 */     JComponent jComponent = paramSynthContext.getComponent();
/* 271 */     Insets insets = jComponent.getInsets(this.viewSizingInsets);
/* 272 */     int i = insets.left + insets.right;
/* 273 */     int j = insets.top + insets.bottom;
/*     */     
/* 275 */     if (paramIcon == null && (paramString == null || paramFont == null)) {
/* 276 */       return new Dimension(i, j);
/*     */     }
/* 278 */     if (paramString == null || (paramIcon != null && paramFont == null)) {
/* 279 */       return new Dimension(SynthIcon.getIconWidth(paramIcon, paramSynthContext) + i, 
/* 280 */           SynthIcon.getIconHeight(paramIcon, paramSynthContext) + j);
/*     */     }
/*     */     
/* 283 */     FontMetrics fontMetrics = jComponent.getFontMetrics(paramFont);
/*     */     
/* 285 */     this.iconR.x = this.iconR.y = this.iconR.width = this.iconR.height = 0;
/* 286 */     this.textR.x = this.textR.y = this.textR.width = this.textR.height = 0;
/* 287 */     this.viewR.x = i;
/* 288 */     this.viewR.y = j;
/* 289 */     this.viewR.width = this.viewR.height = 32767;
/*     */     
/* 291 */     layoutText(paramSynthContext, fontMetrics, paramString, paramIcon, paramInt1, paramInt2, paramInt3, paramInt4, this.viewR, this.iconR, this.textR, paramInt5);
/*     */ 
/*     */     
/* 294 */     int k = Math.min(this.iconR.x, this.textR.x);
/* 295 */     int m = Math.max(this.iconR.x + this.iconR.width, this.textR.x + this.textR.width);
/* 296 */     int n = Math.min(this.iconR.y, this.textR.y);
/* 297 */     int i1 = Math.max(this.iconR.y + this.iconR.height, this.textR.y + this.textR.height);
/* 298 */     Dimension dimension = new Dimension(m - k, i1 - n);
/*     */     
/* 300 */     dimension.width += i;
/* 301 */     dimension.height += j;
/* 302 */     return dimension;
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
/*     */   public void paintText(SynthContext paramSynthContext, Graphics paramGraphics, String paramString, Rectangle paramRectangle, int paramInt) {
/* 319 */     paintText(paramSynthContext, paramGraphics, paramString, paramRectangle.x, paramRectangle.y, paramInt);
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
/*     */   public void paintText(SynthContext paramSynthContext, Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 336 */     if (paramString != null) {
/* 337 */       JComponent jComponent = paramSynthContext.getComponent();
/* 338 */       FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(jComponent, paramGraphics);
/* 339 */       paramInt2 += fontMetrics.getAscent();
/* 340 */       SwingUtilities2.drawStringUnderlineCharAt(jComponent, paramGraphics, paramString, paramInt3, paramInt1, paramInt2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintText(SynthContext paramSynthContext, Graphics paramGraphics, String paramString, Icon paramIcon, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/* 366 */     if (paramIcon == null && paramString == null) {
/*     */       return;
/*     */     }
/* 369 */     JComponent jComponent = paramSynthContext.getComponent();
/* 370 */     FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(jComponent, paramGraphics);
/* 371 */     Insets insets = SynthLookAndFeel.getPaintingInsets(paramSynthContext, this.paintInsets);
/*     */     
/* 373 */     this.paintViewR.x = insets.left;
/* 374 */     this.paintViewR.y = insets.top;
/* 375 */     this.paintViewR.width = jComponent.getWidth() - insets.left + insets.right;
/* 376 */     this.paintViewR.height = jComponent.getHeight() - insets.top + insets.bottom;
/*     */     
/* 378 */     this.paintIconR.x = this.paintIconR.y = this.paintIconR.width = this.paintIconR.height = 0;
/* 379 */     this.paintTextR.x = this.paintTextR.y = this.paintTextR.width = this.paintTextR.height = 0;
/*     */ 
/*     */     
/* 382 */     String str = layoutText(paramSynthContext, fontMetrics, paramString, paramIcon, paramInt1, paramInt2, paramInt3, paramInt4, this.paintViewR, this.paintIconR, this.paintTextR, paramInt5);
/*     */ 
/*     */ 
/*     */     
/* 386 */     if (paramIcon != null) {
/* 387 */       Color color = paramGraphics.getColor();
/*     */       
/* 389 */       if (paramSynthContext.getStyle().getBoolean(paramSynthContext, "TableHeader.alignSorterArrow", false) && "TableHeader.renderer"
/* 390 */         .equals(jComponent.getName())) {
/* 391 */         this.paintIconR.x = this.paintViewR.width - this.paintIconR.width;
/*     */       } else {
/* 393 */         this.paintIconR.x += paramInt7;
/*     */       } 
/* 395 */       this.paintIconR.y += paramInt7;
/* 396 */       SynthIcon.paintIcon(paramIcon, paramSynthContext, paramGraphics, this.paintIconR.x, this.paintIconR.y, this.paintIconR.width, this.paintIconR.height);
/*     */       
/* 398 */       paramGraphics.setColor(color);
/*     */     } 
/*     */     
/* 401 */     if (paramString != null) {
/* 402 */       View view = (View)jComponent.getClientProperty("html");
/*     */       
/* 404 */       if (view != null) {
/* 405 */         view.paint(paramGraphics, this.paintTextR);
/*     */       } else {
/* 407 */         this.paintTextR.x += paramInt7;
/* 408 */         this.paintTextR.y += paramInt7;
/*     */         
/* 410 */         paintText(paramSynthContext, paramGraphics, str, this.paintTextR, paramInt6);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Dimension getPreferredMenuItemSize(SynthContext paramSynthContext1, SynthContext paramSynthContext2, JComponent paramJComponent, Icon paramIcon1, Icon paramIcon2, int paramInt, String paramString1, boolean paramBoolean, String paramString2) {
/* 433 */     JMenuItem jMenuItem = (JMenuItem)paramJComponent;
/*     */ 
/*     */ 
/*     */     
/* 437 */     SynthMenuItemLayoutHelper synthMenuItemLayoutHelper = new SynthMenuItemLayoutHelper(paramSynthContext1, paramSynthContext2, jMenuItem, paramIcon1, paramIcon2, MenuItemLayoutHelper.createMaxRect(), paramInt, paramString1, SynthLookAndFeel.isLeftToRight(jMenuItem), paramBoolean, paramString2);
/*     */ 
/*     */     
/* 440 */     Dimension dimension = new Dimension();
/*     */ 
/*     */     
/* 443 */     int i = synthMenuItemLayoutHelper.getGap();
/* 444 */     dimension.width = 0;
/* 445 */     MenuItemLayoutHelper.addMaxWidth(synthMenuItemLayoutHelper.getCheckSize(), i, dimension);
/* 446 */     MenuItemLayoutHelper.addMaxWidth(synthMenuItemLayoutHelper.getLabelSize(), i, dimension);
/* 447 */     MenuItemLayoutHelper.addWidth(synthMenuItemLayoutHelper.getMaxAccOrArrowWidth(), 5 * i, dimension);
/*     */     
/* 449 */     dimension.width -= i;
/*     */ 
/*     */     
/* 452 */     dimension.height = MenuItemLayoutHelper.max(new int[] { synthMenuItemLayoutHelper.getCheckSize().getHeight(), synthMenuItemLayoutHelper
/* 453 */           .getLabelSize().getHeight(), synthMenuItemLayoutHelper.getAccSize().getHeight(), synthMenuItemLayoutHelper
/* 454 */           .getArrowSize().getHeight() });
/*     */ 
/*     */     
/* 457 */     Insets insets = synthMenuItemLayoutHelper.getMenuItem().getInsets();
/* 458 */     if (insets != null) {
/* 459 */       dimension.width += insets.left + insets.right;
/* 460 */       dimension.height += insets.top + insets.bottom;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 465 */     if (dimension.width % 2 == 0) {
/* 466 */       dimension.width++;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 471 */     if (dimension.height % 2 == 0) {
/* 472 */       dimension.height++;
/*     */     }
/*     */     
/* 475 */     return dimension;
/*     */   }
/*     */   
/*     */   static void applyInsets(Rectangle paramRectangle, Insets paramInsets, boolean paramBoolean) {
/* 479 */     if (paramInsets != null) {
/* 480 */       paramRectangle.x += paramBoolean ? paramInsets.left : paramInsets.right;
/* 481 */       paramRectangle.y += paramInsets.top;
/* 482 */       paramRectangle.width -= (paramBoolean ? paramInsets.right : paramInsets.left) + paramRectangle.x;
/* 483 */       paramRectangle.height -= paramInsets.bottom + paramRectangle.y;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void paint(SynthContext paramSynthContext1, SynthContext paramSynthContext2, Graphics paramGraphics, Icon paramIcon1, Icon paramIcon2, String paramString1, int paramInt, String paramString2) {
/* 490 */     JMenuItem jMenuItem = (JMenuItem)paramSynthContext1.getComponent();
/* 491 */     SynthStyle synthStyle = paramSynthContext1.getStyle();
/* 492 */     paramGraphics.setFont(synthStyle.getFont(paramSynthContext1));
/*     */     
/* 494 */     Rectangle rectangle = new Rectangle(0, 0, jMenuItem.getWidth(), jMenuItem.getHeight());
/* 495 */     boolean bool = SynthLookAndFeel.isLeftToRight(jMenuItem);
/* 496 */     applyInsets(rectangle, jMenuItem.getInsets(), bool);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 501 */     SynthMenuItemLayoutHelper synthMenuItemLayoutHelper = new SynthMenuItemLayoutHelper(paramSynthContext1, paramSynthContext2, jMenuItem, paramIcon1, paramIcon2, rectangle, paramInt, paramString1, bool, MenuItemLayoutHelper.useCheckAndArrow(jMenuItem), paramString2);
/* 502 */     MenuItemLayoutHelper.LayoutResult layoutResult = synthMenuItemLayoutHelper.layoutMenuItem();
/*     */     
/* 504 */     paintMenuItem(paramGraphics, synthMenuItemLayoutHelper, layoutResult);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void paintMenuItem(Graphics paramGraphics, SynthMenuItemLayoutHelper paramSynthMenuItemLayoutHelper, MenuItemLayoutHelper.LayoutResult paramLayoutResult) {
/* 510 */     Font font = paramGraphics.getFont();
/* 511 */     Color color = paramGraphics.getColor();
/*     */     
/* 513 */     paintCheckIcon(paramGraphics, paramSynthMenuItemLayoutHelper, paramLayoutResult);
/* 514 */     paintIcon(paramGraphics, paramSynthMenuItemLayoutHelper, paramLayoutResult);
/* 515 */     paintText(paramGraphics, paramSynthMenuItemLayoutHelper, paramLayoutResult);
/* 516 */     paintAccText(paramGraphics, paramSynthMenuItemLayoutHelper, paramLayoutResult);
/* 517 */     paintArrowIcon(paramGraphics, paramSynthMenuItemLayoutHelper, paramLayoutResult);
/*     */ 
/*     */     
/* 520 */     paramGraphics.setColor(color);
/* 521 */     paramGraphics.setFont(font);
/*     */   }
/*     */   
/*     */   static void paintBackground(Graphics paramGraphics, SynthMenuItemLayoutHelper paramSynthMenuItemLayoutHelper) {
/* 525 */     paintBackground(paramSynthMenuItemLayoutHelper.getContext(), paramGraphics, paramSynthMenuItemLayoutHelper.getMenuItem());
/*     */   }
/*     */   
/*     */   static void paintBackground(SynthContext paramSynthContext, Graphics paramGraphics, JComponent paramJComponent) {
/* 529 */     paramSynthContext.getPainter().paintMenuItemBackground(paramSynthContext, paramGraphics, 0, 0, paramJComponent
/* 530 */         .getWidth(), paramJComponent.getHeight());
/*     */   }
/*     */ 
/*     */   
/*     */   static void paintIcon(Graphics paramGraphics, SynthMenuItemLayoutHelper paramSynthMenuItemLayoutHelper, MenuItemLayoutHelper.LayoutResult paramLayoutResult) {
/* 535 */     if (paramSynthMenuItemLayoutHelper.getIcon() != null) {
/*     */       Icon icon;
/* 537 */       JMenuItem jMenuItem = paramSynthMenuItemLayoutHelper.getMenuItem();
/* 538 */       ButtonModel buttonModel = jMenuItem.getModel();
/* 539 */       if (!buttonModel.isEnabled()) {
/* 540 */         icon = jMenuItem.getDisabledIcon();
/* 541 */       } else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 542 */         icon = jMenuItem.getPressedIcon();
/* 543 */         if (icon == null)
/*     */         {
/* 545 */           icon = jMenuItem.getIcon();
/*     */         }
/*     */       } else {
/* 548 */         icon = jMenuItem.getIcon();
/*     */       } 
/*     */       
/* 551 */       if (icon != null) {
/* 552 */         Rectangle rectangle = paramLayoutResult.getIconRect();
/* 553 */         SynthIcon.paintIcon(icon, paramSynthMenuItemLayoutHelper.getContext(), paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void paintCheckIcon(Graphics paramGraphics, SynthMenuItemLayoutHelper paramSynthMenuItemLayoutHelper, MenuItemLayoutHelper.LayoutResult paramLayoutResult) {
/* 561 */     if (paramSynthMenuItemLayoutHelper.getCheckIcon() != null) {
/* 562 */       Rectangle rectangle = paramLayoutResult.getCheckRect();
/* 563 */       SynthIcon.paintIcon(paramSynthMenuItemLayoutHelper.getCheckIcon(), paramSynthMenuItemLayoutHelper.getContext(), paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void paintAccText(Graphics paramGraphics, SynthMenuItemLayoutHelper paramSynthMenuItemLayoutHelper, MenuItemLayoutHelper.LayoutResult paramLayoutResult) {
/* 570 */     String str = paramSynthMenuItemLayoutHelper.getAccText();
/* 571 */     if (str != null && !str.equals("")) {
/* 572 */       paramGraphics.setColor(paramSynthMenuItemLayoutHelper.getAccStyle().getColor(paramSynthMenuItemLayoutHelper.getAccContext(), ColorType.TEXT_FOREGROUND));
/*     */       
/* 574 */       paramGraphics.setFont(paramSynthMenuItemLayoutHelper.getAccStyle().getFont(paramSynthMenuItemLayoutHelper.getAccContext()));
/* 575 */       paramSynthMenuItemLayoutHelper.getAccGraphicsUtils().paintText(paramSynthMenuItemLayoutHelper.getAccContext(), paramGraphics, str, 
/* 576 */           (paramLayoutResult.getAccRect()).x, (paramLayoutResult.getAccRect()).y, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void paintText(Graphics paramGraphics, SynthMenuItemLayoutHelper paramSynthMenuItemLayoutHelper, MenuItemLayoutHelper.LayoutResult paramLayoutResult) {
/* 582 */     if (!paramSynthMenuItemLayoutHelper.getText().equals("")) {
/* 583 */       if (paramSynthMenuItemLayoutHelper.getHtmlView() != null) {
/*     */         
/* 585 */         paramSynthMenuItemLayoutHelper.getHtmlView().paint(paramGraphics, paramLayoutResult.getTextRect());
/*     */       } else {
/*     */         
/* 588 */         paramGraphics.setColor(paramSynthMenuItemLayoutHelper.getStyle().getColor(paramSynthMenuItemLayoutHelper
/* 589 */               .getContext(), ColorType.TEXT_FOREGROUND));
/* 590 */         paramGraphics.setFont(paramSynthMenuItemLayoutHelper.getStyle().getFont(paramSynthMenuItemLayoutHelper.getContext()));
/* 591 */         paramSynthMenuItemLayoutHelper.getGraphicsUtils().paintText(paramSynthMenuItemLayoutHelper.getContext(), paramGraphics, paramSynthMenuItemLayoutHelper.getText(), 
/* 592 */             (paramLayoutResult.getTextRect()).x, (paramLayoutResult.getTextRect()).y, paramSynthMenuItemLayoutHelper
/* 593 */             .getMenuItem().getDisplayedMnemonicIndex());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static void paintArrowIcon(Graphics paramGraphics, SynthMenuItemLayoutHelper paramSynthMenuItemLayoutHelper, MenuItemLayoutHelper.LayoutResult paramLayoutResult) {
/* 600 */     if (paramSynthMenuItemLayoutHelper.getArrowIcon() != null) {
/* 601 */       Rectangle rectangle = paramLayoutResult.getArrowRect();
/* 602 */       SynthIcon.paintIcon(paramSynthMenuItemLayoutHelper.getArrowIcon(), paramSynthMenuItemLayoutHelper.getContext(), paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SynthIconWrapper
/*     */     implements Icon
/*     */   {
/* 612 */     private static final List<SynthIconWrapper> CACHE = new ArrayList<>(1);
/*     */     
/*     */     private SynthIcon synthIcon;
/*     */     private SynthContext context;
/*     */     
/*     */     static SynthIconWrapper get(SynthIcon param1SynthIcon, SynthContext param1SynthContext) {
/* 618 */       synchronized (CACHE) {
/* 619 */         int i = CACHE.size();
/* 620 */         if (i > 0) {
/* 621 */           SynthIconWrapper synthIconWrapper = CACHE.remove(i - 1);
/* 622 */           synthIconWrapper.reset(param1SynthIcon, param1SynthContext);
/* 623 */           return synthIconWrapper;
/*     */         } 
/*     */       } 
/* 626 */       return new SynthIconWrapper(param1SynthIcon, param1SynthContext);
/*     */     }
/*     */     
/*     */     static void release(SynthIconWrapper param1SynthIconWrapper) {
/* 630 */       param1SynthIconWrapper.reset(null, null);
/* 631 */       synchronized (CACHE) {
/* 632 */         CACHE.add(param1SynthIconWrapper);
/*     */       } 
/*     */     }
/*     */     
/*     */     SynthIconWrapper(SynthIcon param1SynthIcon, SynthContext param1SynthContext) {
/* 637 */       reset(param1SynthIcon, param1SynthContext);
/*     */     }
/*     */     
/*     */     void reset(SynthIcon param1SynthIcon, SynthContext param1SynthContext) {
/* 641 */       this.synthIcon = param1SynthIcon;
/* 642 */       this.context = param1SynthContext;
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {}
/*     */ 
/*     */     
/*     */     public int getIconWidth() {
/* 650 */       return this.synthIcon.getIconWidth(this.context);
/*     */     }
/*     */     
/*     */     public int getIconHeight() {
/* 654 */       return this.synthIcon.getIconHeight(this.context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthGraphicsUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */