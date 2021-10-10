/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicProgressBarUI;
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
/*     */ public class SynthProgressBarUI
/*     */   extends BasicProgressBarUI
/*     */   implements SynthUI, PropertyChangeListener
/*     */ {
/*     */   private SynthStyle style;
/*     */   private int progressPadding;
/*     */   private boolean rotateText;
/*     */   private boolean paintOutsideClip;
/*     */   private boolean tileWhenIndeterminate;
/*     */   private int tileWidth;
/*     */   private Dimension minBarSize;
/*     */   private int glowWidth;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  62 */     return new SynthProgressBarUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/*  70 */     super.installListeners();
/*  71 */     this.progressBar.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/*  79 */     super.uninstallListeners();
/*  80 */     this.progressBar.removePropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  88 */     updateStyle(this.progressBar);
/*     */   }
/*     */   
/*     */   private void updateStyle(JProgressBar paramJProgressBar) {
/*  92 */     SynthContext synthContext = getContext(paramJProgressBar, 1);
/*  93 */     SynthStyle synthStyle = this.style;
/*  94 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*  95 */     setCellLength(this.style.getInt(synthContext, "ProgressBar.cellLength", 1));
/*  96 */     setCellSpacing(this.style.getInt(synthContext, "ProgressBar.cellSpacing", 0));
/*  97 */     this.progressPadding = this.style.getInt(synthContext, "ProgressBar.progressPadding", 0);
/*     */     
/*  99 */     this.paintOutsideClip = this.style.getBoolean(synthContext, "ProgressBar.paintOutsideClip", false);
/*     */     
/* 101 */     this.rotateText = this.style.getBoolean(synthContext, "ProgressBar.rotateText", false);
/*     */     
/* 103 */     this.tileWhenIndeterminate = this.style.getBoolean(synthContext, "ProgressBar.tileWhenIndeterminate", false);
/* 104 */     this.tileWidth = this.style.getInt(synthContext, "ProgressBar.tileWidth", 15);
/*     */ 
/*     */ 
/*     */     
/* 108 */     String str = (String)this.progressBar.getClientProperty("JComponent.sizeVariant");
/*     */     
/* 110 */     if (str != null) {
/* 111 */       if ("large".equals(str)) {
/* 112 */         this.tileWidth = (int)(this.tileWidth * 1.15D);
/* 113 */       } else if ("small".equals(str)) {
/* 114 */         this.tileWidth = (int)(this.tileWidth * 0.857D);
/* 115 */       } else if ("mini".equals(str)) {
/* 116 */         this.tileWidth = (int)(this.tileWidth * 0.784D);
/*     */       } 
/*     */     }
/* 119 */     this.minBarSize = (Dimension)this.style.get(synthContext, "ProgressBar.minBarSize");
/* 120 */     this.glowWidth = this.style.getInt(synthContext, "ProgressBar.glowWidth", 0);
/* 121 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 129 */     SynthContext synthContext = getContext(this.progressBar, 1);
/*     */     
/* 131 */     this.style.uninstallDefaults(synthContext);
/* 132 */     synthContext.dispose();
/* 133 */     this.style = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 141 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 145 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/* 149 */     return SynthLookAndFeel.getComponentState(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/* 157 */     super.getBaseline(paramJComponent, paramInt1, paramInt2);
/* 158 */     if (this.progressBar.isStringPainted() && this.progressBar
/* 159 */       .getOrientation() == 0) {
/* 160 */       SynthContext synthContext = getContext(paramJComponent);
/* 161 */       Font font = synthContext.getStyle().getFont(synthContext);
/* 162 */       FontMetrics fontMetrics = this.progressBar.getFontMetrics(font);
/* 163 */       synthContext.dispose();
/* 164 */       return (paramInt2 - fontMetrics.getAscent() - fontMetrics.getDescent()) / 2 + fontMetrics
/* 165 */         .getAscent();
/*     */     } 
/* 167 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Rectangle getBox(Rectangle paramRectangle) {
/* 175 */     if (this.tileWhenIndeterminate) {
/* 176 */       return SwingUtilities.calculateInnerArea(this.progressBar, paramRectangle);
/*     */     }
/* 178 */     return super.getBox(paramRectangle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setAnimationIndex(int paramInt) {
/* 187 */     if (this.paintOutsideClip) {
/* 188 */       if (getAnimationIndex() == paramInt) {
/*     */         return;
/*     */       }
/* 191 */       super.setAnimationIndex(paramInt);
/* 192 */       this.progressBar.repaint();
/*     */     } else {
/* 194 */       super.setAnimationIndex(paramInt);
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
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 212 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 214 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 215 */     synthContext.getPainter().paintProgressBarBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 216 */         .getWidth(), paramJComponent.getHeight(), this.progressBar
/* 217 */         .getOrientation());
/* 218 */     paint(synthContext, paramGraphics);
/* 219 */     synthContext.dispose();
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
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 233 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 235 */     paint(synthContext, paramGraphics);
/* 236 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {
/* 247 */     JProgressBar jProgressBar = (JProgressBar)paramSynthContext.getComponent();
/* 248 */     int i = 0, j = 0, k = 0, m = 0;
/* 249 */     if (!jProgressBar.isIndeterminate()) {
/* 250 */       Insets insets = jProgressBar.getInsets();
/* 251 */       double d = jProgressBar.getPercentComplete();
/* 252 */       if (d != 0.0D) {
/* 253 */         if (jProgressBar.getOrientation() == 0) {
/* 254 */           i = insets.left + this.progressPadding;
/* 255 */           j = insets.top + this.progressPadding;
/* 256 */           k = (int)(d * (jProgressBar.getWidth() - insets.left + this.progressPadding + insets.right + this.progressPadding));
/*     */ 
/*     */           
/* 259 */           m = jProgressBar.getHeight() - insets.top + this.progressPadding + insets.bottom + this.progressPadding;
/*     */ 
/*     */ 
/*     */           
/* 263 */           if (!SynthLookAndFeel.isLeftToRight(jProgressBar)) {
/* 264 */             i = jProgressBar.getWidth() - insets.right - k - this.progressPadding - this.glowWidth;
/*     */           }
/*     */         } else {
/*     */           
/* 268 */           i = insets.left + this.progressPadding;
/* 269 */           k = jProgressBar.getWidth() - insets.left + this.progressPadding + insets.right + this.progressPadding;
/*     */ 
/*     */           
/* 272 */           m = (int)(d * (jProgressBar.getHeight() - insets.top + this.progressPadding + insets.bottom + this.progressPadding));
/*     */ 
/*     */           
/* 275 */           j = jProgressBar.getHeight() - insets.bottom - m - this.progressPadding;
/*     */ 
/*     */           
/* 278 */           if (SynthLookAndFeel.isLeftToRight(jProgressBar)) {
/* 279 */             j -= this.glowWidth;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } else {
/* 284 */       this.boxRect = getBox(this.boxRect);
/* 285 */       i = this.boxRect.x + this.progressPadding;
/* 286 */       j = this.boxRect.y + this.progressPadding;
/* 287 */       k = this.boxRect.width - this.progressPadding - this.progressPadding;
/* 288 */       m = this.boxRect.height - this.progressPadding - this.progressPadding;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     if (this.tileWhenIndeterminate && jProgressBar.isIndeterminate()) {
/* 295 */       double d = getAnimationIndex() / getFrameCount();
/* 296 */       int n = (int)(d * this.tileWidth);
/* 297 */       Shape shape = paramGraphics.getClip();
/* 298 */       paramGraphics.clipRect(i, j, k, m);
/* 299 */       if (jProgressBar.getOrientation() == 0) {
/*     */         int i1;
/* 301 */         for (i1 = i - this.tileWidth + n; i1 <= k; i1 += this.tileWidth) {
/* 302 */           paramSynthContext.getPainter().paintProgressBarForeground(paramSynthContext, paramGraphics, i1, j, this.tileWidth, m, jProgressBar
/* 303 */               .getOrientation());
/*     */         }
/*     */       } else {
/*     */         int i1;
/* 307 */         for (i1 = j - n; i1 < m + this.tileWidth; i1 += this.tileWidth) {
/* 308 */           paramSynthContext.getPainter().paintProgressBarForeground(paramSynthContext, paramGraphics, i, i1, k, this.tileWidth, jProgressBar
/* 309 */               .getOrientation());
/*     */         }
/*     */       } 
/* 312 */       paramGraphics.setClip(shape);
/*     */     }
/* 314 */     else if (this.minBarSize == null || (k >= this.minBarSize.width && m >= this.minBarSize.height)) {
/*     */       
/* 316 */       paramSynthContext.getPainter().paintProgressBarForeground(paramSynthContext, paramGraphics, i, j, k, m, jProgressBar
/* 317 */           .getOrientation());
/*     */     } 
/*     */ 
/*     */     
/* 321 */     if (jProgressBar.isStringPainted()) {
/* 322 */       paintText(paramSynthContext, paramGraphics, jProgressBar.getString());
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
/*     */   protected void paintText(SynthContext paramSynthContext, Graphics paramGraphics, String paramString) {
/* 334 */     if (this.progressBar.isStringPainted()) {
/* 335 */       SynthStyle synthStyle = paramSynthContext.getStyle();
/* 336 */       Font font = synthStyle.getFont(paramSynthContext);
/* 337 */       FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(this.progressBar, paramGraphics, font);
/*     */ 
/*     */       
/* 340 */       int i = synthStyle.getGraphicsUtils(paramSynthContext).computeStringWidth(paramSynthContext, font, fontMetrics, paramString);
/* 341 */       Rectangle rectangle = this.progressBar.getBounds();
/*     */       
/* 343 */       if (this.rotateText && this.progressBar
/* 344 */         .getOrientation() == 1) {
/* 345 */         Point point; AffineTransform affineTransform; Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*     */ 
/*     */ 
/*     */         
/* 349 */         if (this.progressBar.getComponentOrientation().isLeftToRight()) {
/* 350 */           affineTransform = AffineTransform.getRotateInstance(-1.5707963267948966D);
/*     */           
/* 352 */           point = new Point((rectangle.width + fontMetrics.getAscent() - fontMetrics.getDescent()) / 2, (rectangle.height + i) / 2);
/*     */         } else {
/*     */           
/* 355 */           affineTransform = AffineTransform.getRotateInstance(1.5707963267948966D);
/*     */           
/* 357 */           point = new Point((rectangle.width - fontMetrics.getAscent() + fontMetrics.getDescent()) / 2, (rectangle.height - i) / 2);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 362 */         if (point.x < 0) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 367 */         font = font.deriveFont(affineTransform);
/* 368 */         graphics2D.setFont(font);
/* 369 */         graphics2D.setColor(synthStyle.getColor(paramSynthContext, ColorType.TEXT_FOREGROUND));
/* 370 */         synthStyle.getGraphicsUtils(paramSynthContext).paintText(paramSynthContext, paramGraphics, paramString, point.x, point.y, -1);
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 377 */         Rectangle rectangle1 = new Rectangle(rectangle.width / 2 - i / 2, (rectangle.height - fontMetrics.getAscent() + fontMetrics.getDescent()) / 2, 0, 0);
/*     */ 
/*     */ 
/*     */         
/* 381 */         if (rectangle1.y < 0) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 386 */         paramGraphics.setColor(synthStyle.getColor(paramSynthContext, ColorType.TEXT_FOREGROUND));
/* 387 */         paramGraphics.setFont(font);
/* 388 */         synthStyle.getGraphicsUtils(paramSynthContext).paintText(paramSynthContext, paramGraphics, paramString, rectangle1.x, rectangle1.y, -1);
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
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 400 */     paramSynthContext.getPainter().paintProgressBarBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, this.progressBar
/* 401 */         .getOrientation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 409 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent) || "indeterminate"
/* 410 */       .equals(paramPropertyChangeEvent.getPropertyName())) {
/* 411 */       updateStyle((JProgressBar)paramPropertyChangeEvent.getSource());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 420 */     Dimension dimension = null;
/* 421 */     Insets insets = this.progressBar.getInsets();
/* 422 */     FontMetrics fontMetrics = this.progressBar.getFontMetrics(this.progressBar.getFont());
/* 423 */     String str1 = this.progressBar.getString();
/* 424 */     int i = fontMetrics.getHeight() + fontMetrics.getDescent();
/*     */     
/* 426 */     if (this.progressBar.getOrientation() == 0) {
/* 427 */       dimension = new Dimension(getPreferredInnerHorizontal());
/* 428 */       if (this.progressBar.isStringPainted()) {
/*     */         
/* 430 */         if (i > dimension.height) {
/* 431 */           dimension.height = i;
/*     */         }
/*     */ 
/*     */         
/* 435 */         int j = SwingUtilities2.stringWidth(this.progressBar, fontMetrics, str1);
/*     */         
/* 437 */         if (j > dimension.width) {
/* 438 */           dimension.width = j;
/*     */         }
/*     */       } 
/*     */     } else {
/* 442 */       dimension = new Dimension(getPreferredInnerVertical());
/* 443 */       if (this.progressBar.isStringPainted()) {
/*     */         
/* 445 */         if (i > dimension.width) {
/* 446 */           dimension.width = i;
/*     */         }
/*     */ 
/*     */         
/* 450 */         int j = SwingUtilities2.stringWidth(this.progressBar, fontMetrics, str1);
/*     */         
/* 452 */         if (j > dimension.height) {
/* 453 */           dimension.height = j;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 461 */     String str2 = (String)this.progressBar.getClientProperty("JComponent.sizeVariant");
/*     */     
/* 463 */     if (str2 != null) {
/* 464 */       if ("large".equals(str2)) {
/* 465 */         dimension.width = (int)(dimension.width * 1.15F);
/* 466 */         dimension.height = (int)(dimension.height * 1.15F);
/* 467 */       } else if ("small".equals(str2)) {
/* 468 */         dimension.width = (int)(dimension.width * 0.9F);
/* 469 */         dimension.height = (int)(dimension.height * 0.9F);
/* 470 */       } else if ("mini".equals(str2)) {
/* 471 */         dimension.width = (int)(dimension.width * 0.784F);
/* 472 */         dimension.height = (int)(dimension.height * 0.784F);
/*     */       } 
/*     */     }
/*     */     
/* 476 */     dimension.width += insets.left + insets.right;
/* 477 */     dimension.height += insets.top + insets.bottom;
/*     */     
/* 479 */     return dimension;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthProgressBarUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */