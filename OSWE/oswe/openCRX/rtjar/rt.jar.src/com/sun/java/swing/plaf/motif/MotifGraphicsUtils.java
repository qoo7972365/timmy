/*     */ package com.sun.java.swing.plaf.motif;
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
/*     */ import java.awt.event.KeyEvent;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.SwingConstants;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.text.View;
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
/*     */ public class MotifGraphicsUtils
/*     */   implements SwingConstants
/*     */ {
/*     */   private static final String MAX_ACC_WIDTH = "maxAccWidth";
/*     */   
/*     */   static void drawPoint(Graphics paramGraphics, int paramInt1, int paramInt2) {
/*  58 */     paramGraphics.drawLine(paramInt1, paramInt2, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawGroove(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor1, Color paramColor2) {
/*  68 */     Color color = paramGraphics.getColor();
/*  69 */     paramGraphics.translate(paramInt1, paramInt2);
/*     */     
/*  71 */     paramGraphics.setColor(paramColor1);
/*  72 */     paramGraphics.drawRect(0, 0, paramInt3 - 2, paramInt4 - 2);
/*     */     
/*  74 */     paramGraphics.setColor(paramColor2);
/*  75 */     paramGraphics.drawLine(1, paramInt4 - 3, 1, 1);
/*  76 */     paramGraphics.drawLine(1, 1, paramInt3 - 3, 1);
/*     */     
/*  78 */     paramGraphics.drawLine(0, paramInt4 - 1, paramInt3 - 1, paramInt4 - 1);
/*  79 */     paramGraphics.drawLine(paramInt3 - 1, paramInt4 - 1, paramInt3 - 1, 0);
/*     */     
/*  81 */     paramGraphics.translate(-paramInt1, -paramInt2);
/*  82 */     paramGraphics.setColor(color);
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
/*     */   public static void drawStringInRect(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  96 */     drawStringInRect(null, paramGraphics, paramString, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawStringInRect(JComponent paramJComponent, Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*     */     int i;
/* 105 */     if (paramGraphics.getFont() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 109 */     FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(paramJComponent, paramGraphics);
/* 110 */     if (fontMetrics == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 115 */     if (paramInt5 == 0) {
/* 116 */       int m = SwingUtilities2.stringWidth(paramJComponent, fontMetrics, paramString);
/* 117 */       if (m > paramInt3) {
/* 118 */         m = paramInt3;
/*     */       }
/* 120 */       i = paramInt1 + (paramInt3 - m) / 2;
/* 121 */     } else if (paramInt5 == 4) {
/* 122 */       int m = SwingUtilities2.stringWidth(paramJComponent, fontMetrics, paramString);
/* 123 */       if (m > paramInt3) {
/* 124 */         m = paramInt3;
/*     */       }
/* 126 */       i = paramInt1 + paramInt3 - m;
/*     */     } else {
/* 128 */       i = paramInt1;
/*     */     } 
/*     */     
/* 131 */     int k = (paramInt4 - fontMetrics.getAscent() - fontMetrics.getDescent()) / 2;
/* 132 */     if (k < 0) {
/* 133 */       k = 0;
/*     */     }
/*     */     
/* 136 */     int j = paramInt2 + paramInt4 - k - fontMetrics.getDescent();
/*     */     
/* 138 */     SwingUtilities2.drawString(paramJComponent, paramGraphics, paramString, i, j);
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
/*     */   public static void paintMenuItem(Graphics paramGraphics, JComponent paramJComponent, Icon paramIcon1, Icon paramIcon2, Color paramColor1, Color paramColor2, int paramInt) {
/* 152 */     JMenuItem jMenuItem = (JMenuItem)paramJComponent;
/* 153 */     ButtonModel buttonModel = jMenuItem.getModel();
/*     */     
/* 155 */     Dimension dimension = jMenuItem.getSize();
/* 156 */     Insets insets = paramJComponent.getInsets();
/*     */     
/* 158 */     Rectangle rectangle1 = new Rectangle(dimension);
/*     */     
/* 160 */     rectangle1.x += insets.left;
/* 161 */     rectangle1.y += insets.top;
/* 162 */     rectangle1.width -= insets.right + rectangle1.x;
/* 163 */     rectangle1.height -= insets.bottom + rectangle1.y;
/*     */     
/* 165 */     Rectangle rectangle2 = new Rectangle();
/* 166 */     Rectangle rectangle3 = new Rectangle();
/* 167 */     Rectangle rectangle4 = new Rectangle();
/* 168 */     Rectangle rectangle5 = new Rectangle();
/* 169 */     Rectangle rectangle6 = new Rectangle();
/*     */     
/* 171 */     Font font1 = paramGraphics.getFont();
/* 172 */     Font font2 = paramJComponent.getFont();
/* 173 */     paramGraphics.setFont(font2);
/* 174 */     FontMetrics fontMetrics1 = SwingUtilities2.getFontMetrics(paramJComponent, paramGraphics, font2);
/* 175 */     FontMetrics fontMetrics2 = SwingUtilities2.getFontMetrics(paramJComponent, paramGraphics, 
/* 176 */         UIManager.getFont("MenuItem.acceleratorFont"));
/*     */     
/* 178 */     if (paramJComponent.isOpaque()) {
/* 179 */       if (buttonModel.isArmed() || (paramJComponent instanceof javax.swing.JMenu && buttonModel.isSelected())) {
/* 180 */         paramGraphics.setColor(paramColor1);
/*     */       } else {
/* 182 */         paramGraphics.setColor(paramJComponent.getBackground());
/*     */       } 
/* 184 */       paramGraphics.fillRect(0, 0, dimension.width, dimension.height);
/*     */     } 
/*     */ 
/*     */     
/* 188 */     KeyStroke keyStroke = jMenuItem.getAccelerator();
/* 189 */     String str1 = "";
/* 190 */     if (keyStroke != null) {
/* 191 */       int i = keyStroke.getModifiers();
/* 192 */       if (i > 0) {
/* 193 */         str1 = KeyEvent.getKeyModifiersText(i);
/* 194 */         str1 = str1 + "+";
/*     */       } 
/* 196 */       str1 = str1 + KeyEvent.getKeyText(keyStroke.getKeyCode());
/*     */     } 
/*     */ 
/*     */     
/* 200 */     String str2 = layoutMenuItem(paramJComponent, fontMetrics1, jMenuItem.getText(), fontMetrics2, str1, jMenuItem
/* 201 */         .getIcon(), paramIcon1, paramIcon2, jMenuItem
/*     */         
/* 203 */         .getVerticalAlignment(), jMenuItem
/* 204 */         .getHorizontalAlignment(), jMenuItem
/* 205 */         .getVerticalTextPosition(), jMenuItem
/* 206 */         .getHorizontalTextPosition(), rectangle1, rectangle2, rectangle3, rectangle4, rectangle5, rectangle6, 
/*     */ 
/*     */ 
/*     */         
/* 210 */         (jMenuItem.getText() == null) ? 0 : paramInt, paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     Color color = paramGraphics.getColor();
/* 217 */     if (paramIcon1 != null) {
/* 218 */       if (buttonModel.isArmed() || (paramJComponent instanceof javax.swing.JMenu && buttonModel.isSelected()))
/* 219 */         paramGraphics.setColor(paramColor2); 
/* 220 */       paramIcon1.paintIcon(paramJComponent, paramGraphics, rectangle5.x, rectangle5.y);
/* 221 */       paramGraphics.setColor(color);
/*     */     } 
/*     */ 
/*     */     
/* 225 */     if (jMenuItem.getIcon() != null) {
/*     */       Icon icon;
/* 227 */       if (!buttonModel.isEnabled()) {
/* 228 */         icon = jMenuItem.getDisabledIcon();
/* 229 */       } else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 230 */         icon = jMenuItem.getPressedIcon();
/* 231 */         if (icon == null)
/*     */         {
/* 233 */           icon = jMenuItem.getIcon();
/*     */         }
/*     */       } else {
/* 236 */         icon = jMenuItem.getIcon();
/*     */       } 
/*     */       
/* 239 */       if (icon != null) {
/* 240 */         icon.paintIcon(paramJComponent, paramGraphics, rectangle2.x, rectangle2.y);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 245 */     if (str2 != null && !str2.equals("")) {
/*     */ 
/*     */       
/* 248 */       View view = (View)paramJComponent.getClientProperty("html");
/* 249 */       if (view != null) {
/* 250 */         view.paint(paramGraphics, rectangle3);
/*     */       } else {
/* 252 */         int i = jMenuItem.getDisplayedMnemonicIndex();
/*     */         
/* 254 */         if (!buttonModel.isEnabled()) {
/*     */           
/* 256 */           paramGraphics.setColor(jMenuItem.getBackground().brighter());
/* 257 */           SwingUtilities2.drawStringUnderlineCharAt(jMenuItem, paramGraphics, str2, i, rectangle3.x, rectangle3.y + fontMetrics2
/*     */               
/* 259 */               .getAscent());
/* 260 */           paramGraphics.setColor(jMenuItem.getBackground().darker());
/* 261 */           SwingUtilities2.drawStringUnderlineCharAt(jMenuItem, paramGraphics, str2, i, rectangle3.x - 1, rectangle3.y + fontMetrics2
/*     */               
/* 263 */               .getAscent() - 1);
/*     */         }
/*     */         else {
/*     */           
/* 267 */           if (buttonModel.isArmed() || (paramJComponent instanceof javax.swing.JMenu && buttonModel.isSelected())) {
/* 268 */             paramGraphics.setColor(paramColor2);
/*     */           } else {
/* 270 */             paramGraphics.setColor(jMenuItem.getForeground());
/*     */           } 
/* 272 */           SwingUtilities2.drawStringUnderlineCharAt(jMenuItem, paramGraphics, str2, i, rectangle3.x, rectangle3.y + fontMetrics1
/*     */ 
/*     */               
/* 275 */               .getAscent());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 281 */     if (str1 != null && !str1.equals("")) {
/*     */ 
/*     */       
/* 284 */       int i = 0;
/* 285 */       Container container = jMenuItem.getParent();
/* 286 */       if (container != null && container instanceof JComponent) {
/* 287 */         JComponent jComponent = (JComponent)container;
/* 288 */         Integer integer = (Integer)jComponent.getClientProperty("maxAccWidth");
/*     */         
/* 290 */         int j = (integer != null) ? integer.intValue() : rectangle4.width;
/*     */ 
/*     */         
/* 293 */         i = j - rectangle4.width;
/*     */       } 
/*     */       
/* 296 */       paramGraphics.setFont(UIManager.getFont("MenuItem.acceleratorFont"));
/* 297 */       if (!buttonModel.isEnabled()) {
/*     */         
/* 299 */         paramGraphics.setColor(jMenuItem.getBackground().brighter());
/* 300 */         SwingUtilities2.drawString(paramJComponent, paramGraphics, str1, rectangle4.x - i, rectangle4.y + fontMetrics1
/* 301 */             .getAscent());
/* 302 */         paramGraphics.setColor(jMenuItem.getBackground().darker());
/* 303 */         SwingUtilities2.drawString(paramJComponent, paramGraphics, str1, rectangle4.x - i - 1, rectangle4.y + fontMetrics1
/* 304 */             .getAscent() - 1);
/*     */       } else {
/*     */         
/* 307 */         if (buttonModel.isArmed() || (paramJComponent instanceof javax.swing.JMenu && buttonModel.isSelected())) {
/*     */           
/* 309 */           paramGraphics.setColor(paramColor2);
/*     */         } else {
/* 311 */           paramGraphics.setColor(jMenuItem.getForeground());
/*     */         } 
/* 313 */         SwingUtilities2.drawString(paramJComponent, paramGraphics, str1, rectangle4.x - i, rectangle4.y + fontMetrics2
/*     */             
/* 315 */             .getAscent());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 320 */     if (paramIcon2 != null) {
/* 321 */       if (buttonModel.isArmed() || (paramJComponent instanceof javax.swing.JMenu && buttonModel.isSelected()))
/* 322 */         paramGraphics.setColor(paramColor2); 
/* 323 */       if (!(jMenuItem.getParent() instanceof javax.swing.JMenuBar)) {
/* 324 */         paramIcon2.paintIcon(paramJComponent, paramGraphics, rectangle6.x, rectangle6.y);
/*     */       }
/*     */     } 
/* 327 */     paramGraphics.setColor(color);
/* 328 */     paramGraphics.setFont(font1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String layoutMenuItem(JComponent paramJComponent, FontMetrics paramFontMetrics1, String paramString1, FontMetrics paramFontMetrics2, String paramString2, Icon paramIcon1, Icon paramIcon2, Icon paramIcon3, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Rectangle paramRectangle1, Rectangle paramRectangle2, Rectangle paramRectangle3, Rectangle paramRectangle4, Rectangle paramRectangle5, Rectangle paramRectangle6, int paramInt5, int paramInt6) {
/* 363 */     SwingUtilities.layoutCompoundLabel(paramJComponent, paramFontMetrics1, paramString1, paramIcon1, paramInt1, paramInt2, paramInt3, paramInt4, paramRectangle1, paramRectangle2, paramRectangle3, paramInt5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 380 */     if (paramString2 == null || paramString2.equals("")) {
/* 381 */       paramRectangle4.width = paramRectangle4.height = 0;
/* 382 */       paramString2 = "";
/*     */     } else {
/*     */       
/* 385 */       paramRectangle4
/* 386 */         .width = SwingUtilities2.stringWidth(paramJComponent, paramFontMetrics2, paramString2);
/* 387 */       paramRectangle4.height = paramFontMetrics2.getHeight();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 393 */     if (paramIcon2 != null) {
/* 394 */       paramRectangle5.width = paramIcon2.getIconWidth();
/* 395 */       paramRectangle5.height = paramIcon2.getIconHeight();
/*     */     } else {
/*     */       
/* 398 */       paramRectangle5.width = paramRectangle5.height = 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 404 */     if (paramIcon3 != null) {
/* 405 */       paramRectangle6.width = paramIcon3.getIconWidth();
/* 406 */       paramRectangle6.height = paramIcon3.getIconHeight();
/*     */     } else {
/*     */       
/* 409 */       paramRectangle6.width = paramRectangle6.height = 0;
/*     */     } 
/*     */ 
/*     */     
/* 413 */     Rectangle rectangle = paramRectangle2.union(paramRectangle3);
/* 414 */     if (isLeftToRight(paramJComponent)) {
/* 415 */       paramRectangle3.x += paramRectangle5.width + paramInt6;
/* 416 */       paramRectangle2.x += paramRectangle5.width + paramInt6;
/*     */ 
/*     */       
/* 419 */       paramRectangle4.x = paramRectangle1.x + paramRectangle1.width - paramRectangle6.width - paramInt6 - paramRectangle4.width;
/*     */ 
/*     */ 
/*     */       
/* 423 */       paramRectangle5.x = paramRectangle1.x;
/* 424 */       paramRectangle6.x = paramRectangle1.x + paramRectangle1.width - paramInt6 - paramRectangle6.width;
/*     */     } else {
/*     */       
/* 427 */       paramRectangle3.x -= paramRectangle5.width + paramInt6;
/* 428 */       paramRectangle2.x -= paramRectangle5.width + paramInt6;
/*     */ 
/*     */       
/* 431 */       paramRectangle4.x = paramRectangle1.x + paramRectangle6.width + paramInt6;
/*     */ 
/*     */       
/* 434 */       paramRectangle5.x = paramRectangle1.x + paramRectangle1.width - paramRectangle5.width;
/* 435 */       paramRectangle1.x += paramInt6;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 440 */     paramRectangle4.y = rectangle.y + rectangle.height / 2 - paramRectangle4.height / 2;
/* 441 */     paramRectangle6.y = rectangle.y + rectangle.height / 2 - paramRectangle6.height / 2;
/* 442 */     paramRectangle5.y = rectangle.y + rectangle.height / 2 - paramRectangle5.height / 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 448 */     return paramString1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void drawMenuBezel(Graphics paramGraphics, Color paramColor, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 456 */     paramGraphics.setColor(paramColor);
/* 457 */     paramGraphics.fillRect(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     
/* 459 */     paramGraphics.setColor(paramColor.brighter().brighter());
/* 460 */     paramGraphics.drawLine(paramInt1 + 1, paramInt2 + paramInt4 - 1, paramInt1 + paramInt3 - 1, paramInt2 + paramInt4 - 1);
/* 461 */     paramGraphics.drawLine(paramInt1 + paramInt3 - 1, paramInt2 + paramInt4 - 2, paramInt1 + paramInt3 - 1, paramInt2 + 1);
/*     */     
/* 463 */     paramGraphics.setColor(paramColor.darker().darker());
/* 464 */     paramGraphics.drawLine(paramInt1, paramInt2, paramInt1 + paramInt3 - 2, paramInt2);
/* 465 */     paramGraphics.drawLine(paramInt1, paramInt2 + 1, paramInt1, paramInt2 + paramInt4 - 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isLeftToRight(Component paramComponent) {
/* 474 */     return paramComponent.getComponentOrientation().isLeftToRight();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifGraphicsUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */