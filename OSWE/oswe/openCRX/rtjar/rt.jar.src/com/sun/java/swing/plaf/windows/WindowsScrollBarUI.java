/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.HashMap;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicArrowButton;
/*     */ import javax.swing.plaf.basic.BasicScrollBarUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsScrollBarUI
/*     */   extends BasicScrollBarUI
/*     */ {
/*     */   private Grid thumbGrid;
/*     */   private Grid highlightGrid;
/*     */   private Dimension horizontalThumbSize;
/*     */   private Dimension verticalThumbSize;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  64 */     return new WindowsScrollBarUI();
/*     */   }
/*     */   
/*     */   protected void installDefaults() {
/*  68 */     super.installDefaults();
/*     */     
/*  70 */     XPStyle xPStyle = XPStyle.getXP();
/*  71 */     if (xPStyle != null) {
/*  72 */       this.scrollbar.setBorder((Border)null);
/*  73 */       this.horizontalThumbSize = getSize(this.scrollbar, xPStyle, TMSchema.Part.SBP_THUMBBTNHORZ);
/*  74 */       this.verticalThumbSize = getSize(this.scrollbar, xPStyle, TMSchema.Part.SBP_THUMBBTNVERT);
/*     */     } else {
/*  76 */       this.horizontalThumbSize = null;
/*  77 */       this.verticalThumbSize = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Dimension getSize(Component paramComponent, XPStyle paramXPStyle, TMSchema.Part paramPart) {
/*  82 */     XPStyle.Skin skin = paramXPStyle.getSkin(paramComponent, paramPart);
/*  83 */     return new Dimension(skin.getWidth(), skin.getHeight());
/*     */   }
/*     */ 
/*     */   
/*     */   protected Dimension getMinimumThumbSize() {
/*  88 */     if (this.horizontalThumbSize == null || this.verticalThumbSize == null) {
/*  89 */       return super.getMinimumThumbSize();
/*     */     }
/*  91 */     return (0 == this.scrollbar.getOrientation()) ? this.horizontalThumbSize : this.verticalThumbSize;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/*  97 */     super.uninstallUI(paramJComponent);
/*  98 */     this.thumbGrid = this.highlightGrid = null;
/*     */   }
/*     */   
/*     */   protected void configureScrollBarColors() {
/* 102 */     super.configureScrollBarColors();
/* 103 */     Color color = UIManager.getColor("ScrollBar.trackForeground");
/* 104 */     if (color != null && this.trackColor != null) {
/* 105 */       this.thumbGrid = Grid.getGrid(color, this.trackColor);
/*     */     }
/*     */     
/* 108 */     color = UIManager.getColor("ScrollBar.trackHighlightForeground");
/* 109 */     if (color != null && this.trackHighlightColor != null) {
/* 110 */       this.highlightGrid = Grid.getGrid(color, this.trackHighlightColor);
/*     */     }
/*     */   }
/*     */   
/*     */   protected JButton createDecreaseButton(int paramInt) {
/* 115 */     return new WindowsArrowButton(paramInt, 
/* 116 */         UIManager.getColor("ScrollBar.thumb"), 
/* 117 */         UIManager.getColor("ScrollBar.thumbShadow"), 
/* 118 */         UIManager.getColor("ScrollBar.thumbDarkShadow"), 
/* 119 */         UIManager.getColor("ScrollBar.thumbHighlight"));
/*     */   }
/*     */   
/*     */   protected JButton createIncreaseButton(int paramInt) {
/* 123 */     return new WindowsArrowButton(paramInt, 
/* 124 */         UIManager.getColor("ScrollBar.thumb"), 
/* 125 */         UIManager.getColor("ScrollBar.thumbShadow"), 
/* 126 */         UIManager.getColor("ScrollBar.thumbDarkShadow"), 
/* 127 */         UIManager.getColor("ScrollBar.thumbHighlight"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BasicScrollBarUI.ArrowButtonListener createArrowButtonListener() {
/* 138 */     if (XPStyle.isVista()) {
/* 139 */       return new BasicScrollBarUI.ArrowButtonListener() {
/*     */           public void mouseEntered(MouseEvent param1MouseEvent) {
/* 141 */             repaint();
/* 142 */             super.mouseEntered(param1MouseEvent);
/*     */           }
/*     */           public void mouseExited(MouseEvent param1MouseEvent) {
/* 145 */             repaint();
/* 146 */             super.mouseExited(param1MouseEvent);
/*     */           }
/*     */           private void repaint() {
/* 149 */             WindowsScrollBarUI.this.scrollbar.repaint();
/*     */           }
/*     */         };
/*     */     }
/* 153 */     return super.createArrowButtonListener();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintTrack(Graphics paramGraphics, JComponent paramJComponent, Rectangle paramRectangle) {
/* 158 */     boolean bool = (this.scrollbar.getOrientation() == 1) ? true : false;
/*     */     
/* 160 */     XPStyle xPStyle = XPStyle.getXP();
/* 161 */     if (xPStyle != null) {
/* 162 */       JScrollBar jScrollBar = (JScrollBar)paramJComponent;
/* 163 */       TMSchema.State state = TMSchema.State.NORMAL;
/*     */       
/* 165 */       if (!jScrollBar.isEnabled()) {
/* 166 */         state = TMSchema.State.DISABLED;
/*     */       }
/* 168 */       TMSchema.Part part = bool ? TMSchema.Part.SBP_LOWERTRACKVERT : TMSchema.Part.SBP_LOWERTRACKHORZ;
/* 169 */       xPStyle.getSkin(jScrollBar, part).paintSkin(paramGraphics, paramRectangle, state);
/* 170 */     } else if (this.thumbGrid == null) {
/* 171 */       super.paintTrack(paramGraphics, paramJComponent, paramRectangle);
/*     */     } else {
/*     */       
/* 174 */       this.thumbGrid.paint(paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*     */       
/* 176 */       if (this.trackHighlight == 1) {
/* 177 */         paintDecreaseHighlight(paramGraphics);
/*     */       }
/* 179 */       else if (this.trackHighlight == 2) {
/* 180 */         paintIncreaseHighlight(paramGraphics);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void paintThumb(Graphics paramGraphics, JComponent paramJComponent, Rectangle paramRectangle) {
/* 186 */     boolean bool = (this.scrollbar.getOrientation() == 1) ? true : false;
/*     */     
/* 188 */     XPStyle xPStyle = XPStyle.getXP();
/* 189 */     if (xPStyle != null) {
/* 190 */       JScrollBar jScrollBar = (JScrollBar)paramJComponent;
/* 191 */       TMSchema.State state = TMSchema.State.NORMAL;
/* 192 */       if (!jScrollBar.isEnabled()) {
/* 193 */         state = TMSchema.State.DISABLED;
/* 194 */       } else if (this.isDragging) {
/* 195 */         state = TMSchema.State.PRESSED;
/* 196 */       } else if (isThumbRollover()) {
/* 197 */         state = TMSchema.State.HOT;
/* 198 */       } else if (XPStyle.isVista() && ((
/* 199 */         this.incrButton != null && this.incrButton.getModel().isRollover()) || (this.decrButton != null && this.decrButton
/* 200 */         .getModel().isRollover()))) {
/* 201 */         state = TMSchema.State.HOVER;
/*     */       } 
/*     */ 
/*     */       
/* 205 */       TMSchema.Part part1 = bool ? TMSchema.Part.SBP_THUMBBTNVERT : TMSchema.Part.SBP_THUMBBTNHORZ;
/* 206 */       xPStyle.getSkin(jScrollBar, part1).paintSkin(paramGraphics, paramRectangle, state);
/*     */       
/* 208 */       TMSchema.Part part2 = bool ? TMSchema.Part.SBP_GRIPPERVERT : TMSchema.Part.SBP_GRIPPERHORZ;
/* 209 */       XPStyle.Skin skin = xPStyle.getSkin(jScrollBar, part2);
/* 210 */       Insets insets = xPStyle.getMargin(paramJComponent, part1, null, TMSchema.Prop.CONTENTMARGINS);
/* 211 */       if (insets == null || (bool && paramRectangle.height - insets.top - insets.bottom >= skin
/*     */         
/* 213 */         .getHeight()) || (!bool && paramRectangle.width - insets.left - insets.right >= skin
/*     */         
/* 215 */         .getWidth())) {
/* 216 */         skin.paintSkin(paramGraphics, paramRectangle.x + (paramRectangle.width - skin
/* 217 */             .getWidth()) / 2, paramRectangle.y + (paramRectangle.height - skin
/* 218 */             .getHeight()) / 2, skin
/* 219 */             .getWidth(), skin.getHeight(), state);
/*     */       }
/*     */     } else {
/* 222 */       super.paintThumb(paramGraphics, paramJComponent, paramRectangle);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintDecreaseHighlight(Graphics paramGraphics) {
/* 228 */     if (this.highlightGrid == null) {
/* 229 */       super.paintDecreaseHighlight(paramGraphics);
/*     */     } else {
/*     */       int i, j, k, m;
/* 232 */       Insets insets = this.scrollbar.getInsets();
/* 233 */       Rectangle rectangle = getThumbBounds();
/*     */ 
/*     */       
/* 236 */       if (this.scrollbar.getOrientation() == 1) {
/* 237 */         i = insets.left;
/* 238 */         j = this.decrButton.getY() + this.decrButton.getHeight();
/* 239 */         k = this.scrollbar.getWidth() - insets.left + insets.right;
/* 240 */         m = rectangle.y - j;
/*     */       } else {
/*     */         
/* 243 */         i = this.decrButton.getX() + this.decrButton.getHeight();
/* 244 */         j = insets.top;
/* 245 */         k = rectangle.x - i;
/* 246 */         m = this.scrollbar.getHeight() - insets.top + insets.bottom;
/*     */       } 
/* 248 */       this.highlightGrid.paint(paramGraphics, i, j, k, m);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintIncreaseHighlight(Graphics paramGraphics) {
/* 254 */     if (this.highlightGrid == null) {
/* 255 */       super.paintDecreaseHighlight(paramGraphics);
/*     */     } else {
/*     */       int i, j, k, m;
/* 258 */       Insets insets = this.scrollbar.getInsets();
/* 259 */       Rectangle rectangle = getThumbBounds();
/*     */ 
/*     */       
/* 262 */       if (this.scrollbar.getOrientation() == 1) {
/* 263 */         i = insets.left;
/* 264 */         j = rectangle.y + rectangle.height;
/* 265 */         k = this.scrollbar.getWidth() - insets.left + insets.right;
/* 266 */         m = this.incrButton.getY() - j;
/*     */       } else {
/*     */         
/* 269 */         i = rectangle.x + rectangle.width;
/* 270 */         j = insets.top;
/* 271 */         k = this.incrButton.getX() - i;
/* 272 */         m = this.scrollbar.getHeight() - insets.top + insets.bottom;
/*     */       } 
/* 274 */       this.highlightGrid.paint(paramGraphics, i, j, k, m);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setThumbRollover(boolean paramBoolean) {
/* 285 */     boolean bool = isThumbRollover();
/* 286 */     super.setThumbRollover(paramBoolean);
/*     */ 
/*     */     
/* 289 */     if (XPStyle.isVista() && paramBoolean != bool) {
/* 290 */       this.scrollbar.repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class WindowsArrowButton
/*     */     extends BasicArrowButton
/*     */   {
/*     */     public WindowsArrowButton(int param1Int, Color param1Color1, Color param1Color2, Color param1Color3, Color param1Color4) {
/* 303 */       super(param1Int, param1Color1, param1Color2, param1Color3, param1Color4);
/*     */     }
/*     */     
/*     */     public WindowsArrowButton(int param1Int) {
/* 307 */       super(param1Int);
/*     */     }
/*     */     
/*     */     public void paint(Graphics param1Graphics) {
/* 311 */       XPStyle xPStyle = XPStyle.getXP();
/* 312 */       if (xPStyle != null) {
/* 313 */         ButtonModel buttonModel = getModel();
/* 314 */         XPStyle.Skin skin = xPStyle.getSkin(this, TMSchema.Part.SBP_ARROWBTN);
/* 315 */         TMSchema.State state = null;
/*     */ 
/*     */ 
/*     */         
/* 319 */         boolean bool = (XPStyle.isVista() && (WindowsScrollBarUI.this.isThumbRollover() || (this == WindowsScrollBarUI.this.incrButton && WindowsScrollBarUI.this.decrButton.getModel().isRollover()) || (this == WindowsScrollBarUI.this.decrButton && WindowsScrollBarUI.this.incrButton.getModel().isRollover()))) ? true : false;
/*     */ 
/*     */         
/* 322 */         if (buttonModel.isArmed() && buttonModel.isPressed()) {
/* 323 */           switch (this.direction) { case 1:
/* 324 */               state = TMSchema.State.UPPRESSED; break;
/* 325 */             case 5: state = TMSchema.State.DOWNPRESSED; break;
/* 326 */             case 7: state = TMSchema.State.LEFTPRESSED; break;
/* 327 */             case 3: state = TMSchema.State.RIGHTPRESSED; break; }
/*     */         
/* 329 */         } else if (!buttonModel.isEnabled()) {
/* 330 */           switch (this.direction) { case 1:
/* 331 */               state = TMSchema.State.UPDISABLED; break;
/* 332 */             case 5: state = TMSchema.State.DOWNDISABLED; break;
/* 333 */             case 7: state = TMSchema.State.LEFTDISABLED; break;
/* 334 */             case 3: state = TMSchema.State.RIGHTDISABLED; break; }
/*     */         
/* 336 */         } else if (buttonModel.isRollover() || buttonModel.isPressed()) {
/* 337 */           switch (this.direction) { case 1:
/* 338 */               state = TMSchema.State.UPHOT; break;
/* 339 */             case 5: state = TMSchema.State.DOWNHOT; break;
/* 340 */             case 7: state = TMSchema.State.LEFTHOT; break;
/* 341 */             case 3: state = TMSchema.State.RIGHTHOT; break; }
/*     */         
/* 343 */         } else if (bool) {
/* 344 */           switch (this.direction) { case 1:
/* 345 */               state = TMSchema.State.UPHOVER; break;
/* 346 */             case 5: state = TMSchema.State.DOWNHOVER; break;
/* 347 */             case 7: state = TMSchema.State.LEFTHOVER; break;
/* 348 */             case 3: state = TMSchema.State.RIGHTHOVER; break; }
/*     */         
/*     */         } else {
/* 351 */           switch (this.direction) { case 1:
/* 352 */               state = TMSchema.State.UPNORMAL; break;
/* 353 */             case 5: state = TMSchema.State.DOWNNORMAL; break;
/* 354 */             case 7: state = TMSchema.State.LEFTNORMAL; break;
/* 355 */             case 3: state = TMSchema.State.RIGHTNORMAL;
/*     */               break; }
/*     */         
/*     */         } 
/* 359 */         skin.paintSkin(param1Graphics, 0, 0, getWidth(), getHeight(), state);
/*     */       } else {
/* 361 */         super.paint(param1Graphics);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Dimension getPreferredSize() {
/* 366 */       int i = 16;
/* 367 */       if (WindowsScrollBarUI.this.scrollbar != null) {
/* 368 */         switch (WindowsScrollBarUI.this.scrollbar.getOrientation()) {
/*     */           case 1:
/* 370 */             i = WindowsScrollBarUI.this.scrollbar.getWidth();
/*     */             break;
/*     */           case 0:
/* 373 */             i = WindowsScrollBarUI.this.scrollbar.getHeight();
/*     */             break;
/*     */         } 
/* 376 */         i = Math.max(i, 5);
/*     */       } 
/* 378 */       return new Dimension(i, i);
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
/*     */   private static class Grid
/*     */   {
/*     */     private static final int BUFFER_SIZE = 64;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 400 */     private static HashMap<String, WeakReference<Grid>> map = new HashMap<>();
/*     */     private BufferedImage image;
/*     */     
/*     */     public static Grid getGrid(Color param1Color1, Color param1Color2) {
/* 404 */       String str = param1Color1.getRGB() + " " + param1Color2.getRGB();
/* 405 */       WeakReference<Grid> weakReference = map.get(str);
/* 406 */       Grid grid = (weakReference == null) ? null : weakReference.get();
/* 407 */       if (grid == null) {
/* 408 */         grid = new Grid(param1Color1, param1Color2);
/* 409 */         map.put(str, new WeakReference<>(grid));
/*     */       } 
/* 411 */       return grid;
/*     */     }
/*     */     
/*     */     public Grid(Color param1Color1, Color param1Color2) {
/* 415 */       int[] arrayOfInt = { param1Color1.getRGB(), param1Color2.getRGB() };
/* 416 */       IndexColorModel indexColorModel = new IndexColorModel(8, 2, arrayOfInt, 0, false, -1, 0);
/*     */       
/* 418 */       this.image = new BufferedImage(64, 64, 13, indexColorModel);
/*     */       
/* 420 */       Graphics graphics = this.image.getGraphics();
/*     */       try {
/* 422 */         graphics.setClip(0, 0, 64, 64);
/* 423 */         paintGrid(graphics, param1Color1, param1Color2);
/*     */       } finally {
/*     */         
/* 426 */         graphics.dispose();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void paint(Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 435 */       Rectangle rectangle = param1Graphics.getClipBounds();
/* 436 */       int i = Math.max(param1Int1, rectangle.x);
/* 437 */       int j = Math.max(param1Int2, rectangle.y);
/* 438 */       int k = Math.min(rectangle.x + rectangle.width, param1Int1 + param1Int3);
/* 439 */       int m = Math.min(rectangle.y + rectangle.height, param1Int2 + param1Int4);
/*     */       
/* 441 */       if (k <= i || m <= j) {
/*     */         return;
/*     */       }
/* 444 */       int n = (i - param1Int1) % 2;
/* 445 */       for (int i1 = i; i1 < k; 
/* 446 */         i1 += 64) {
/* 447 */         int i2 = (j - param1Int2) % 2;
/* 448 */         int i3 = Math.min(64 - n, k - i1);
/*     */ 
/*     */         
/* 451 */         for (int i4 = j; i4 < m; 
/* 452 */           i4 += 64) {
/* 453 */           int i5 = Math.min(64 - i2, m - i4);
/*     */ 
/*     */           
/* 456 */           param1Graphics.drawImage(this.image, i1, i4, i1 + i3, i4 + i5, n, i2, n + i3, i2 + i5, null);
/*     */ 
/*     */ 
/*     */           
/* 460 */           if (i2 != 0) {
/* 461 */             i4 -= i2;
/* 462 */             i2 = 0;
/*     */           } 
/*     */         } 
/* 465 */         if (n != 0) {
/* 466 */           i1 -= n;
/* 467 */           n = 0;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void paintGrid(Graphics param1Graphics, Color param1Color1, Color param1Color2) {
/* 476 */       Rectangle rectangle = param1Graphics.getClipBounds();
/* 477 */       param1Graphics.setColor(param1Color2);
/* 478 */       param1Graphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */       
/* 480 */       param1Graphics.setColor(param1Color1);
/* 481 */       param1Graphics.translate(rectangle.x, rectangle.y);
/* 482 */       int i = rectangle.width;
/* 483 */       int j = rectangle.height;
/* 484 */       int k = rectangle.x % 2; int m;
/* 485 */       for (m = i - j; k < m; k += 2) {
/* 486 */         param1Graphics.drawLine(k, 0, k + j, j);
/*     */       }
/* 488 */       for (m = i; k < m; k += 2) {
/* 489 */         param1Graphics.drawLine(k, 0, i, i - k);
/*     */       }
/*     */       
/* 492 */       m = (rectangle.x % 2 == 0) ? 2 : 1; int n;
/* 493 */       for (n = j - i; m < n; m += 2) {
/* 494 */         param1Graphics.drawLine(0, m, i, m + i);
/*     */       }
/* 496 */       for (n = j; m < n; m += 2) {
/* 497 */         param1Graphics.drawLine(0, m, j - m, j);
/*     */       }
/* 499 */       param1Graphics.translate(-rectangle.x, -rectangle.y);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsScrollBarUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */