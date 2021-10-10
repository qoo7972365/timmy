/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicProgressBarUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsProgressBarUI
/*     */   extends BasicProgressBarUI
/*     */ {
/*     */   private Rectangle previousFullBox;
/*     */   private Insets indeterminateInsets;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  56 */     return new WindowsProgressBarUI();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  61 */     super.installDefaults();
/*     */     
/*  63 */     if (XPStyle.getXP() != null) {
/*  64 */       LookAndFeel.installProperty(this.progressBar, "opaque", Boolean.FALSE);
/*  65 */       this.progressBar.setBorder((Border)null);
/*  66 */       this.indeterminateInsets = UIManager.getInsets("ProgressBar.indeterminateInsets");
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
/*     */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/*  79 */     int i = super.getBaseline(paramJComponent, paramInt1, paramInt2);
/*  80 */     if (XPStyle.getXP() != null && this.progressBar.isStringPainted() && this.progressBar
/*  81 */       .getOrientation() == 0) {
/*     */       
/*  83 */       FontMetrics fontMetrics = this.progressBar.getFontMetrics(this.progressBar.getFont());
/*  84 */       int j = (this.progressBar.getInsets()).top;
/*  85 */       if (this.progressBar.isIndeterminate()) {
/*  86 */         j = -1;
/*  87 */         paramInt2--;
/*     */       } else {
/*     */         
/*  90 */         j = 0;
/*  91 */         paramInt2 -= 3;
/*     */       } 
/*     */ 
/*     */       
/*  95 */       i = j + (paramInt2 + fontMetrics.getAscent() - fontMetrics.getLeading() - fontMetrics.getDescent()) / 2;
/*     */     } 
/*  97 */     return i;
/*     */   }
/*     */   
/*     */   protected Dimension getPreferredInnerHorizontal() {
/* 101 */     XPStyle xPStyle = XPStyle.getXP();
/* 102 */     if (xPStyle != null) {
/* 103 */       XPStyle.Skin skin = xPStyle.getSkin(this.progressBar, TMSchema.Part.PP_BAR);
/* 104 */       return new Dimension(
/* 105 */           (int)super.getPreferredInnerHorizontal().getWidth(), skin
/* 106 */           .getHeight());
/*     */     } 
/* 108 */     return super.getPreferredInnerHorizontal();
/*     */   }
/*     */   
/*     */   protected Dimension getPreferredInnerVertical() {
/* 112 */     XPStyle xPStyle = XPStyle.getXP();
/* 113 */     if (xPStyle != null) {
/* 114 */       XPStyle.Skin skin = xPStyle.getSkin(this.progressBar, TMSchema.Part.PP_BARVERT);
/* 115 */       return new Dimension(skin
/* 116 */           .getWidth(), 
/* 117 */           (int)super.getPreferredInnerVertical().getHeight());
/*     */     } 
/* 119 */     return super.getPreferredInnerVertical();
/*     */   }
/*     */   
/*     */   protected void paintDeterminate(Graphics paramGraphics, JComponent paramJComponent) {
/* 123 */     XPStyle xPStyle = XPStyle.getXP();
/* 124 */     if (xPStyle != null) {
/* 125 */       boolean bool = (this.progressBar.getOrientation() == 1) ? true : false;
/* 126 */       boolean bool1 = WindowsGraphicsUtils.isLeftToRight(paramJComponent);
/* 127 */       int i = this.progressBar.getWidth();
/* 128 */       int j = this.progressBar.getHeight() - 1;
/*     */       
/* 130 */       int k = getAmountFull(null, i, j);
/*     */       
/* 132 */       paintXPBackground(paramGraphics, bool, i, j);
/*     */       
/* 134 */       if (this.progressBar.isStringPainted()) {
/*     */ 
/*     */         
/* 137 */         paramGraphics.setColor(this.progressBar.getForeground());
/* 138 */         j -= 2;
/* 139 */         i -= 2;
/*     */         
/* 141 */         if (i <= 0 || j <= 0) {
/*     */           return;
/*     */         }
/*     */         
/* 145 */         Graphics2D graphics2D = (Graphics2D)paramGraphics;
/* 146 */         graphics2D.setStroke(new BasicStroke((bool ? i : j), 0, 2));
/*     */         
/* 148 */         if (!bool) {
/* 149 */           if (bool1) {
/* 150 */             graphics2D.drawLine(2, j / 2 + 1, k - 2, j / 2 + 1);
/*     */           } else {
/*     */             
/* 153 */             graphics2D.drawLine(2 + i, j / 2 + 1, 2 + i - k - 2, j / 2 + 1);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 158 */           paintString(paramGraphics, 0, 0, i, j, k, null);
/*     */         } else {
/* 160 */           graphics2D.drawLine(i / 2 + 1, j + 1, i / 2 + 1, j + 1 - k + 2);
/*     */           
/* 162 */           paintString(paramGraphics, 2, 2, i, j, k, null);
/*     */         } 
/*     */       } else {
/*     */         int m;
/* 166 */         XPStyle.Skin skin = xPStyle.getSkin(this.progressBar, bool ? TMSchema.Part.PP_CHUNKVERT : TMSchema.Part.PP_CHUNK);
/*     */         
/* 168 */         if (bool) {
/* 169 */           m = i - 5;
/*     */         } else {
/* 171 */           m = j - 5;
/*     */         } 
/*     */         
/* 174 */         int n = xPStyle.getInt(this.progressBar, TMSchema.Part.PP_PROGRESS, null, TMSchema.Prop.PROGRESSCHUNKSIZE, 2);
/* 175 */         int i1 = xPStyle.getInt(this.progressBar, TMSchema.Part.PP_PROGRESS, null, TMSchema.Prop.PROGRESSSPACESIZE, 0);
/* 176 */         int i2 = (k - 4) / (n + i1);
/*     */ 
/*     */         
/* 179 */         if (i1 > 0 && i2 * (n + i1) + n < k - 4) {
/* 180 */           i2++;
/*     */         }
/*     */         
/* 183 */         for (byte b = 0; b < i2; b++) {
/* 184 */           if (bool) {
/* 185 */             skin.paintSkin(paramGraphics, 3, j - b * (n + i1) - n - 2, m, n, null);
/*     */ 
/*     */           
/*     */           }
/* 189 */           else if (bool1) {
/* 190 */             skin.paintSkin(paramGraphics, 4 + b * (n + i1), 2, n, m, null);
/*     */           }
/*     */           else {
/*     */             
/* 194 */             skin.paintSkin(paramGraphics, i - 2 + (b + 1) * (n + i1), 2, n, m, null);
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 202 */       super.paintDeterminate(paramGraphics, paramJComponent);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setAnimationIndex(int paramInt) {
/* 212 */     super.setAnimationIndex(paramInt);
/* 213 */     XPStyle xPStyle = XPStyle.getXP();
/* 214 */     if (xPStyle != null) {
/* 215 */       if (this.boxRect != null) {
/*     */ 
/*     */         
/* 218 */         Rectangle rectangle = getFullChunkBounds(this.boxRect);
/* 219 */         if (this.previousFullBox != null) {
/* 220 */           rectangle.add(this.previousFullBox);
/*     */         }
/* 222 */         this.progressBar.repaint(rectangle);
/*     */       } else {
/* 224 */         this.progressBar.repaint();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getBoxLength(int paramInt1, int paramInt2) {
/* 235 */     XPStyle xPStyle = XPStyle.getXP();
/* 236 */     if (xPStyle != null) {
/* 237 */       return 6;
/*     */     }
/* 239 */     return super.getBoxLength(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Rectangle getBox(Rectangle paramRectangle) {
/* 247 */     Rectangle rectangle = super.getBox(paramRectangle);
/*     */     
/* 249 */     XPStyle xPStyle = XPStyle.getXP();
/* 250 */     if (xPStyle != null) {
/* 251 */       boolean bool = (this.progressBar.getOrientation() == 1) ? true : false;
/*     */       
/* 253 */       TMSchema.Part part = bool ? TMSchema.Part.PP_BARVERT : TMSchema.Part.PP_BAR;
/* 254 */       Insets insets = this.indeterminateInsets;
/*     */       
/* 256 */       int i = getAnimationIndex();
/* 257 */       int j = getFrameCount() / 2;
/*     */       
/* 259 */       int k = xPStyle.getInt(this.progressBar, TMSchema.Part.PP_PROGRESS, null, TMSchema.Prop.PROGRESSSPACESIZE, 0);
/*     */       
/* 261 */       i %= j;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 271 */       if (!bool) {
/* 272 */         rectangle.y += insets.top;
/* 273 */         rectangle.height = this.progressBar.getHeight() - insets.top - insets.bottom;
/* 274 */         int m = this.progressBar.getWidth() - insets.left - insets.right;
/* 275 */         m += (rectangle.width + k) * 2;
/* 276 */         double d = m / j;
/* 277 */         rectangle.x = (int)(d * i) + insets.left;
/*     */       } else {
/* 279 */         rectangle.x += insets.left;
/* 280 */         rectangle.width = this.progressBar.getWidth() - insets.left - insets.right;
/* 281 */         int m = this.progressBar.getHeight() - insets.top - insets.bottom;
/* 282 */         m += (rectangle.height + k) * 2;
/* 283 */         double d = m / j;
/* 284 */         rectangle.y = (int)(d * i) + insets.top;
/*     */       } 
/*     */     } 
/* 287 */     return rectangle;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintIndeterminate(Graphics paramGraphics, JComponent paramJComponent) {
/* 292 */     XPStyle xPStyle = XPStyle.getXP();
/* 293 */     if (xPStyle != null) {
/* 294 */       boolean bool = (this.progressBar.getOrientation() == 1) ? true : false;
/*     */       
/* 296 */       int i = this.progressBar.getWidth();
/* 297 */       int j = this.progressBar.getHeight();
/* 298 */       paintXPBackground(paramGraphics, bool, i, j);
/*     */ 
/*     */       
/* 301 */       this.boxRect = getBox(this.boxRect);
/* 302 */       if (this.boxRect != null) {
/* 303 */         paramGraphics.setColor(this.progressBar.getForeground());
/* 304 */         if (!(paramGraphics instanceof Graphics2D)) {
/*     */           return;
/*     */         }
/* 307 */         paintIndeterminateFrame(this.boxRect, (Graphics2D)paramGraphics, bool, i, j);
/*     */         
/* 309 */         if (this.progressBar.isStringPainted()) {
/* 310 */           if (!bool) {
/* 311 */             paintString(paramGraphics, -1, -1, i, j, 0, null);
/*     */           } else {
/* 313 */             paintString(paramGraphics, 1, 1, i, j, 0, null);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } else {
/* 318 */       super.paintIndeterminate(paramGraphics, paramJComponent);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Rectangle getFullChunkBounds(Rectangle paramRectangle) {
/* 323 */     boolean bool = (this.progressBar.getOrientation() == 1) ? true : false;
/* 324 */     XPStyle xPStyle = XPStyle.getXP();
/* 325 */     byte b = (xPStyle != null) ? xPStyle.getInt(this.progressBar, TMSchema.Part.PP_PROGRESS, null, TMSchema.Prop.PROGRESSSPACESIZE, 0) : 0;
/*     */ 
/*     */ 
/*     */     
/* 329 */     if (!bool) {
/* 330 */       int j = paramRectangle.width + b;
/* 331 */       return new Rectangle(paramRectangle.x - j * 2, paramRectangle.y, j * 3, paramRectangle.height);
/*     */     } 
/* 333 */     int i = paramRectangle.height + b;
/* 334 */     return new Rectangle(paramRectangle.x, paramRectangle.y - i * 2, paramRectangle.width, i * 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintIndeterminateFrame(Rectangle paramRectangle, Graphics2D paramGraphics2D, boolean paramBoolean, int paramInt1, int paramInt2) {
/* 341 */     XPStyle xPStyle = XPStyle.getXP();
/* 342 */     if (xPStyle == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 347 */     Graphics2D graphics2D = (Graphics2D)paramGraphics2D.create();
/*     */     
/* 349 */     TMSchema.Part part1 = paramBoolean ? TMSchema.Part.PP_BARVERT : TMSchema.Part.PP_BAR;
/* 350 */     TMSchema.Part part2 = paramBoolean ? TMSchema.Part.PP_CHUNKVERT : TMSchema.Part.PP_CHUNK;
/*     */ 
/*     */     
/* 353 */     int i = xPStyle.getInt(this.progressBar, TMSchema.Part.PP_PROGRESS, null, TMSchema.Prop.PROGRESSSPACESIZE, 0);
/*     */     
/* 355 */     int j = 0;
/* 356 */     int k = 0;
/* 357 */     if (!paramBoolean) {
/* 358 */       j = -paramRectangle.width - i;
/* 359 */       k = 0;
/*     */     } else {
/* 361 */       j = 0;
/* 362 */       k = -paramRectangle.height - i;
/*     */     } 
/*     */ 
/*     */     
/* 366 */     Rectangle rectangle1 = getFullChunkBounds(paramRectangle);
/*     */ 
/*     */     
/* 369 */     this.previousFullBox = rectangle1;
/*     */ 
/*     */     
/* 372 */     Insets insets = this.indeterminateInsets;
/* 373 */     Rectangle rectangle2 = new Rectangle(insets.left, insets.top, paramInt1 - insets.left - insets.right, paramInt2 - insets.top - insets.bottom);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 378 */     Rectangle rectangle3 = rectangle2.intersection(rectangle1);
/*     */ 
/*     */     
/* 381 */     graphics2D.clip(rectangle3);
/*     */ 
/*     */     
/* 384 */     XPStyle.Skin skin = xPStyle.getSkin(this.progressBar, part2);
/*     */ 
/*     */     
/* 387 */     graphics2D.setComposite(AlphaComposite.getInstance(3, 0.8F));
/* 388 */     skin.paintSkin(graphics2D, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, null);
/* 389 */     paramRectangle.translate(j, k);
/* 390 */     graphics2D.setComposite(AlphaComposite.getInstance(3, 0.5F));
/* 391 */     skin.paintSkin(graphics2D, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, null);
/* 392 */     paramRectangle.translate(j, k);
/* 393 */     graphics2D.setComposite(AlphaComposite.getInstance(3, 0.2F));
/* 394 */     skin.paintSkin(graphics2D, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, null);
/*     */ 
/*     */     
/* 397 */     graphics2D.dispose();
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintXPBackground(Graphics paramGraphics, boolean paramBoolean, int paramInt1, int paramInt2) {
/* 402 */     XPStyle xPStyle = XPStyle.getXP();
/* 403 */     if (xPStyle == null) {
/*     */       return;
/*     */     }
/* 406 */     TMSchema.Part part = paramBoolean ? TMSchema.Part.PP_BARVERT : TMSchema.Part.PP_BAR;
/* 407 */     XPStyle.Skin skin = xPStyle.getSkin(this.progressBar, part);
/*     */ 
/*     */     
/* 410 */     skin.paintSkin(paramGraphics, 0, 0, paramInt1, paramInt2, null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsProgressBarUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */