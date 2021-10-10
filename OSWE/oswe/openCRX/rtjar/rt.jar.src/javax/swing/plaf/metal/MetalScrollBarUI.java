/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicScrollBarUI;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalScrollBarUI
/*     */   extends BasicScrollBarUI
/*     */ {
/*     */   private static Color shadowColor;
/*     */   private static Color highlightColor;
/*     */   private static Color darkShadowColor;
/*     */   private static Color thumbColor;
/*     */   private static Color thumbShadow;
/*     */   private static Color thumbHighlightColor;
/*     */   protected MetalBumps bumps;
/*     */   protected MetalScrollButton increaseButton;
/*     */   protected MetalScrollButton decreaseButton;
/*     */   protected int scrollBarWidth;
/*     */   public static final String FREE_STANDING_PROP = "JScrollBar.isFreeStanding";
/*     */   protected boolean isFreeStanding = true;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  76 */     return new MetalScrollBarUI();
/*     */   }
/*     */   
/*     */   protected void installDefaults() {
/*  80 */     this.scrollBarWidth = ((Integer)UIManager.get("ScrollBar.width")).intValue();
/*  81 */     super.installDefaults();
/*  82 */     this.bumps = new MetalBumps(10, 10, thumbHighlightColor, thumbShadow, thumbColor);
/*     */   }
/*     */   
/*     */   protected void installListeners() {
/*  86 */     super.installListeners();
/*  87 */     ((ScrollBarListener)this.propertyChangeListener).handlePropertyChange(this.scrollbar.getClientProperty("JScrollBar.isFreeStanding"));
/*     */   }
/*     */   
/*     */   protected PropertyChangeListener createPropertyChangeListener() {
/*  91 */     return new ScrollBarListener();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configureScrollBarColors() {
/*  96 */     super.configureScrollBarColors();
/*  97 */     shadowColor = UIManager.getColor("ScrollBar.shadow");
/*  98 */     highlightColor = UIManager.getColor("ScrollBar.highlight");
/*  99 */     darkShadowColor = UIManager.getColor("ScrollBar.darkShadow");
/* 100 */     thumbColor = UIManager.getColor("ScrollBar.thumb");
/* 101 */     thumbShadow = UIManager.getColor("ScrollBar.thumbShadow");
/* 102 */     thumbHighlightColor = UIManager.getColor("ScrollBar.thumbHighlight");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 109 */     if (this.scrollbar.getOrientation() == 1)
/*     */     {
/* 111 */       return new Dimension(this.scrollBarWidth, this.scrollBarWidth * 3 + 10);
/*     */     }
/*     */ 
/*     */     
/* 115 */     return new Dimension(this.scrollBarWidth * 3 + 10, this.scrollBarWidth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JButton createDecreaseButton(int paramInt) {
/* 124 */     this.decreaseButton = new MetalScrollButton(paramInt, this.scrollBarWidth, this.isFreeStanding);
/* 125 */     return this.decreaseButton;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected JButton createIncreaseButton(int paramInt) {
/* 131 */     this.increaseButton = new MetalScrollButton(paramInt, this.scrollBarWidth, this.isFreeStanding);
/* 132 */     return this.increaseButton;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintTrack(Graphics paramGraphics, JComponent paramJComponent, Rectangle paramRectangle) {
/* 137 */     paramGraphics.translate(paramRectangle.x, paramRectangle.y);
/*     */     
/* 139 */     boolean bool = MetalUtils.isLeftToRight(paramJComponent);
/*     */     
/* 141 */     if (this.scrollbar.getOrientation() == 1) {
/*     */       
/* 143 */       if (!this.isFreeStanding) {
/* 144 */         paramRectangle.width += 2;
/* 145 */         if (!bool) {
/* 146 */           paramGraphics.translate(-1, 0);
/*     */         }
/*     */       } 
/*     */       
/* 150 */       if (paramJComponent.isEnabled()) {
/* 151 */         paramGraphics.setColor(darkShadowColor);
/* 152 */         SwingUtilities2.drawVLine(paramGraphics, 0, 0, paramRectangle.height - 1);
/* 153 */         SwingUtilities2.drawVLine(paramGraphics, paramRectangle.width - 2, 0, paramRectangle.height - 1);
/* 154 */         SwingUtilities2.drawHLine(paramGraphics, 2, paramRectangle.width - 1, paramRectangle.height - 1);
/* 155 */         SwingUtilities2.drawHLine(paramGraphics, 2, paramRectangle.width - 2, 0);
/*     */         
/* 157 */         paramGraphics.setColor(shadowColor);
/*     */         
/* 159 */         SwingUtilities2.drawVLine(paramGraphics, 1, 1, paramRectangle.height - 2);
/* 160 */         SwingUtilities2.drawHLine(paramGraphics, 1, paramRectangle.width - 3, 1);
/* 161 */         if (this.scrollbar.getValue() != this.scrollbar.getMaximum()) {
/* 162 */           int i = this.thumbRect.y + this.thumbRect.height - paramRectangle.y;
/* 163 */           SwingUtilities2.drawHLine(paramGraphics, 1, paramRectangle.width - 1, i);
/*     */         } 
/* 165 */         paramGraphics.setColor(highlightColor);
/* 166 */         SwingUtilities2.drawVLine(paramGraphics, paramRectangle.width - 1, 0, paramRectangle.height - 1);
/*     */       } else {
/* 168 */         MetalUtils.drawDisabledBorder(paramGraphics, 0, 0, paramRectangle.width, paramRectangle.height);
/*     */       } 
/*     */       
/* 171 */       if (!this.isFreeStanding) {
/* 172 */         paramRectangle.width -= 2;
/* 173 */         if (!bool) {
/* 174 */           paramGraphics.translate(1, 0);
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 180 */       if (!this.isFreeStanding) {
/* 181 */         paramRectangle.height += 2;
/*     */       }
/*     */       
/* 184 */       if (paramJComponent.isEnabled()) {
/* 185 */         paramGraphics.setColor(darkShadowColor);
/* 186 */         SwingUtilities2.drawHLine(paramGraphics, 0, paramRectangle.width - 1, 0);
/* 187 */         SwingUtilities2.drawVLine(paramGraphics, 0, 2, paramRectangle.height - 2);
/* 188 */         SwingUtilities2.drawHLine(paramGraphics, 0, paramRectangle.width - 1, paramRectangle.height - 2);
/* 189 */         SwingUtilities2.drawVLine(paramGraphics, paramRectangle.width - 1, 2, paramRectangle.height - 1);
/*     */         
/* 191 */         paramGraphics.setColor(shadowColor);
/*     */         
/* 193 */         SwingUtilities2.drawHLine(paramGraphics, 1, paramRectangle.width - 2, 1);
/* 194 */         SwingUtilities2.drawVLine(paramGraphics, 1, 1, paramRectangle.height - 3);
/* 195 */         SwingUtilities2.drawHLine(paramGraphics, 0, paramRectangle.width - 1, paramRectangle.height - 1);
/* 196 */         if (this.scrollbar.getValue() != this.scrollbar.getMaximum()) {
/* 197 */           int i = this.thumbRect.x + this.thumbRect.width - paramRectangle.x;
/* 198 */           SwingUtilities2.drawVLine(paramGraphics, i, 1, paramRectangle.height - 1);
/*     */         } 
/*     */       } else {
/* 201 */         MetalUtils.drawDisabledBorder(paramGraphics, 0, 0, paramRectangle.width, paramRectangle.height);
/*     */       } 
/*     */       
/* 204 */       if (!this.isFreeStanding) {
/* 205 */         paramRectangle.height -= 2;
/*     */       }
/*     */     } 
/*     */     
/* 209 */     paramGraphics.translate(-paramRectangle.x, -paramRectangle.y);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintThumb(Graphics paramGraphics, JComponent paramJComponent, Rectangle paramRectangle) {
/* 214 */     if (!paramJComponent.isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/* 218 */     if (MetalLookAndFeel.usingOcean()) {
/* 219 */       oceanPaintThumb(paramGraphics, paramJComponent, paramRectangle);
/*     */       
/*     */       return;
/*     */     } 
/* 223 */     boolean bool = MetalUtils.isLeftToRight(paramJComponent);
/*     */     
/* 225 */     paramGraphics.translate(paramRectangle.x, paramRectangle.y);
/*     */     
/* 227 */     if (this.scrollbar.getOrientation() == 1) {
/*     */       
/* 229 */       if (!this.isFreeStanding) {
/* 230 */         paramRectangle.width += 2;
/* 231 */         if (!bool) {
/* 232 */           paramGraphics.translate(-1, 0);
/*     */         }
/*     */       } 
/*     */       
/* 236 */       paramGraphics.setColor(thumbColor);
/* 237 */       paramGraphics.fillRect(0, 0, paramRectangle.width - 2, paramRectangle.height - 1);
/*     */       
/* 239 */       paramGraphics.setColor(thumbShadow);
/* 240 */       SwingUtilities2.drawRect(paramGraphics, 0, 0, paramRectangle.width - 2, paramRectangle.height - 1);
/*     */       
/* 242 */       paramGraphics.setColor(thumbHighlightColor);
/* 243 */       SwingUtilities2.drawHLine(paramGraphics, 1, paramRectangle.width - 3, 1);
/* 244 */       SwingUtilities2.drawVLine(paramGraphics, 1, 1, paramRectangle.height - 2);
/*     */       
/* 246 */       this.bumps.setBumpArea(paramRectangle.width - 6, paramRectangle.height - 7);
/* 247 */       this.bumps.paintIcon(paramJComponent, paramGraphics, 3, 4);
/*     */       
/* 249 */       if (!this.isFreeStanding) {
/* 250 */         paramRectangle.width -= 2;
/* 251 */         if (!bool) {
/* 252 */           paramGraphics.translate(1, 0);
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 258 */       if (!this.isFreeStanding) {
/* 259 */         paramRectangle.height += 2;
/*     */       }
/*     */       
/* 262 */       paramGraphics.setColor(thumbColor);
/* 263 */       paramGraphics.fillRect(0, 0, paramRectangle.width - 1, paramRectangle.height - 2);
/*     */       
/* 265 */       paramGraphics.setColor(thumbShadow);
/* 266 */       SwingUtilities2.drawRect(paramGraphics, 0, 0, paramRectangle.width - 1, paramRectangle.height - 2);
/*     */       
/* 268 */       paramGraphics.setColor(thumbHighlightColor);
/* 269 */       SwingUtilities2.drawHLine(paramGraphics, 1, paramRectangle.width - 3, 1);
/* 270 */       SwingUtilities2.drawVLine(paramGraphics, 1, 1, paramRectangle.height - 3);
/*     */       
/* 272 */       this.bumps.setBumpArea(paramRectangle.width - 7, paramRectangle.height - 6);
/* 273 */       this.bumps.paintIcon(paramJComponent, paramGraphics, 4, 3);
/*     */       
/* 275 */       if (!this.isFreeStanding) {
/* 276 */         paramRectangle.height -= 2;
/*     */       }
/*     */     } 
/*     */     
/* 280 */     paramGraphics.translate(-paramRectangle.x, -paramRectangle.y);
/*     */   }
/*     */ 
/*     */   
/*     */   private void oceanPaintThumb(Graphics paramGraphics, JComponent paramJComponent, Rectangle paramRectangle) {
/* 285 */     boolean bool = MetalUtils.isLeftToRight(paramJComponent);
/*     */     
/* 287 */     paramGraphics.translate(paramRectangle.x, paramRectangle.y);
/*     */     
/* 289 */     if (this.scrollbar.getOrientation() == 1) {
/* 290 */       if (!this.isFreeStanding) {
/* 291 */         paramRectangle.width += 2;
/* 292 */         if (!bool) {
/* 293 */           paramGraphics.translate(-1, 0);
/*     */         }
/*     */       } 
/*     */       
/* 297 */       if (thumbColor != null) {
/* 298 */         paramGraphics.setColor(thumbColor);
/* 299 */         paramGraphics.fillRect(0, 0, paramRectangle.width - 2, paramRectangle.height - 1);
/*     */       } 
/*     */       
/* 302 */       paramGraphics.setColor(thumbShadow);
/* 303 */       SwingUtilities2.drawRect(paramGraphics, 0, 0, paramRectangle.width - 2, paramRectangle.height - 1);
/*     */       
/* 305 */       paramGraphics.setColor(thumbHighlightColor);
/* 306 */       SwingUtilities2.drawHLine(paramGraphics, 1, paramRectangle.width - 3, 1);
/* 307 */       SwingUtilities2.drawVLine(paramGraphics, 1, 1, paramRectangle.height - 2);
/*     */       
/* 309 */       MetalUtils.drawGradient(paramJComponent, paramGraphics, "ScrollBar.gradient", 2, 2, paramRectangle.width - 4, paramRectangle.height - 3, false);
/*     */ 
/*     */ 
/*     */       
/* 313 */       int i = paramRectangle.width - 8;
/* 314 */       if (i > 2 && paramRectangle.height >= 10) {
/* 315 */         paramGraphics.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/* 316 */         int j = paramRectangle.height / 2 - 2; byte b;
/* 317 */         for (b = 0; b < 6; b += 2) {
/* 318 */           paramGraphics.fillRect(4, b + j, i, 1);
/*     */         }
/*     */         
/* 321 */         paramGraphics.setColor(MetalLookAndFeel.getWhite());
/* 322 */         j++;
/* 323 */         for (b = 0; b < 6; b += 2) {
/* 324 */           paramGraphics.fillRect(5, b + j, i, 1);
/*     */         }
/*     */       } 
/* 327 */       if (!this.isFreeStanding) {
/* 328 */         paramRectangle.width -= 2;
/* 329 */         if (!bool) {
/* 330 */           paramGraphics.translate(1, 0);
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 335 */       if (!this.isFreeStanding) {
/* 336 */         paramRectangle.height += 2;
/*     */       }
/*     */       
/* 339 */       if (thumbColor != null) {
/* 340 */         paramGraphics.setColor(thumbColor);
/* 341 */         paramGraphics.fillRect(0, 0, paramRectangle.width - 1, paramRectangle.height - 2);
/*     */       } 
/*     */       
/* 344 */       paramGraphics.setColor(thumbShadow);
/* 345 */       SwingUtilities2.drawRect(paramGraphics, 0, 0, paramRectangle.width - 1, paramRectangle.height - 2);
/*     */       
/* 347 */       paramGraphics.setColor(thumbHighlightColor);
/* 348 */       SwingUtilities2.drawHLine(paramGraphics, 1, paramRectangle.width - 2, 1);
/* 349 */       SwingUtilities2.drawVLine(paramGraphics, 1, 1, paramRectangle.height - 3);
/*     */       
/* 351 */       MetalUtils.drawGradient(paramJComponent, paramGraphics, "ScrollBar.gradient", 2, 2, paramRectangle.width - 3, paramRectangle.height - 4, true);
/*     */ 
/*     */ 
/*     */       
/* 355 */       int i = paramRectangle.height - 8;
/* 356 */       if (i > 2 && paramRectangle.width >= 10) {
/* 357 */         paramGraphics.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
/* 358 */         int j = paramRectangle.width / 2 - 2; byte b;
/* 359 */         for (b = 0; b < 6; b += 2) {
/* 360 */           paramGraphics.fillRect(j + b, 4, 1, i);
/*     */         }
/*     */         
/* 363 */         paramGraphics.setColor(MetalLookAndFeel.getWhite());
/* 364 */         j++;
/* 365 */         for (b = 0; b < 6; b += 2) {
/* 366 */           paramGraphics.fillRect(j + b, 5, 1, i);
/*     */         }
/*     */       } 
/*     */       
/* 370 */       if (!this.isFreeStanding) {
/* 371 */         paramRectangle.height -= 2;
/*     */       }
/*     */     } 
/*     */     
/* 375 */     paramGraphics.translate(-paramRectangle.x, -paramRectangle.y);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Dimension getMinimumThumbSize() {
/* 380 */     return new Dimension(this.scrollBarWidth, this.scrollBarWidth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setThumbBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 391 */     if (this.thumbRect.x == paramInt1 && this.thumbRect.y == paramInt2 && this.thumbRect.width == paramInt3 && this.thumbRect.height == paramInt4) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 401 */     int i = Math.min(paramInt1, this.thumbRect.x);
/* 402 */     int j = Math.min(paramInt2, this.thumbRect.y);
/* 403 */     int k = Math.max(paramInt1 + paramInt3, this.thumbRect.x + this.thumbRect.width);
/* 404 */     int m = Math.max(paramInt2 + paramInt4, this.thumbRect.y + this.thumbRect.height);
/*     */     
/* 406 */     this.thumbRect.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
/* 407 */     this.scrollbar.repaint(i, j, k - i + 1, m - j + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class ScrollBarListener
/*     */     extends BasicScrollBarUI.PropertyChangeHandler
/*     */   {
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 416 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 417 */       if (str.equals("JScrollBar.isFreeStanding")) {
/*     */         
/* 419 */         handlePropertyChange(param1PropertyChangeEvent.getNewValue());
/*     */       } else {
/*     */         
/* 422 */         super.propertyChange(param1PropertyChangeEvent);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void handlePropertyChange(Object param1Object) {
/* 428 */       if (param1Object != null) {
/*     */         
/* 430 */         boolean bool = ((Boolean)param1Object).booleanValue();
/* 431 */         boolean bool1 = (!bool && MetalScrollBarUI.this.isFreeStanding == true) ? true : false;
/* 432 */         boolean bool2 = (bool == true && !MetalScrollBarUI.this.isFreeStanding) ? true : false;
/*     */         
/* 434 */         MetalScrollBarUI.this.isFreeStanding = bool;
/*     */         
/* 436 */         if (bool1) {
/* 437 */           toFlush();
/*     */         }
/* 439 */         else if (bool2) {
/* 440 */           toFreeStanding();
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 446 */       else if (!MetalScrollBarUI.this.isFreeStanding) {
/* 447 */         MetalScrollBarUI.this.isFreeStanding = true;
/* 448 */         toFreeStanding();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 460 */       if (MetalScrollBarUI.this.increaseButton != null)
/*     */       {
/* 462 */         MetalScrollBarUI.this.increaseButton.setFreeStanding(MetalScrollBarUI.this.isFreeStanding);
/*     */       }
/* 464 */       if (MetalScrollBarUI.this.decreaseButton != null)
/*     */       {
/* 466 */         MetalScrollBarUI.this.decreaseButton.setFreeStanding(MetalScrollBarUI.this.isFreeStanding);
/*     */       }
/*     */     }
/*     */     
/*     */     protected void toFlush() {
/* 471 */       MetalScrollBarUI.this.scrollBarWidth -= 2;
/*     */     }
/*     */     
/*     */     protected void toFreeStanding() {
/* 475 */       MetalScrollBarUI.this.scrollBarWidth += 2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalScrollBarUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */