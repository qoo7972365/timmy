/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.HierarchyEvent;
/*      */ import java.awt.event.HierarchyListener;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import javax.swing.BoundedRangeModel;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JProgressBar;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.Timer;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.ProgressBarUI;
/*      */ import sun.swing.DefaultLookup;
/*      */ import sun.swing.SwingUtilities2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BasicProgressBarUI
/*      */   extends ProgressBarUI
/*      */ {
/*      */   private int cachedPercent;
/*      */   private int cellLength;
/*      */   private int cellSpacing;
/*      */   private Color selectionForeground;
/*      */   private Color selectionBackground;
/*      */   private Animator animator;
/*      */   protected JProgressBar progressBar;
/*      */   protected ChangeListener changeListener;
/*      */   private Handler handler;
/*   68 */   private int animationIndex = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int numFrames;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int repaintInterval;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int cycleTime;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean ADJUSTTIMER = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rectangle boxRect;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Rectangle nextPaintRect;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Rectangle componentInnards;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Rectangle oldComponentInnards;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  126 */   private double delta = 0.0D;
/*      */   
/*  128 */   private int maxPosition = 0;
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  132 */     return new BasicProgressBarUI();
/*      */   }
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/*  136 */     this.progressBar = (JProgressBar)paramJComponent;
/*  137 */     installDefaults();
/*  138 */     installListeners();
/*  139 */     if (this.progressBar.isIndeterminate()) {
/*  140 */       initIndeterminateValues();
/*      */     }
/*      */   }
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  145 */     if (this.progressBar.isIndeterminate()) {
/*  146 */       cleanUpIndeterminateValues();
/*      */     }
/*  148 */     uninstallDefaults();
/*  149 */     uninstallListeners();
/*  150 */     this.progressBar = null;
/*      */   }
/*      */   
/*      */   protected void installDefaults() {
/*  154 */     LookAndFeel.installProperty(this.progressBar, "opaque", Boolean.TRUE);
/*  155 */     LookAndFeel.installBorder(this.progressBar, "ProgressBar.border");
/*  156 */     LookAndFeel.installColorsAndFont(this.progressBar, "ProgressBar.background", "ProgressBar.foreground", "ProgressBar.font");
/*      */ 
/*      */ 
/*      */     
/*  160 */     this.cellLength = UIManager.getInt("ProgressBar.cellLength");
/*  161 */     if (this.cellLength == 0) this.cellLength = 1; 
/*  162 */     this.cellSpacing = UIManager.getInt("ProgressBar.cellSpacing");
/*  163 */     this.selectionForeground = UIManager.getColor("ProgressBar.selectionForeground");
/*  164 */     this.selectionBackground = UIManager.getColor("ProgressBar.selectionBackground");
/*      */   }
/*      */   
/*      */   protected void uninstallDefaults() {
/*  168 */     LookAndFeel.uninstallBorder(this.progressBar);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void installListeners() {
/*  173 */     this.changeListener = getHandler();
/*  174 */     this.progressBar.addChangeListener(this.changeListener);
/*      */ 
/*      */     
/*  177 */     this.progressBar.addPropertyChangeListener(getHandler());
/*      */   }
/*      */   
/*      */   private Handler getHandler() {
/*  181 */     if (this.handler == null) {
/*  182 */       this.handler = new Handler();
/*      */     }
/*  184 */     return this.handler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void startAnimationTimer() {
/*  205 */     if (this.animator == null) {
/*  206 */       this.animator = new Animator();
/*      */     }
/*      */     
/*  209 */     this.animator.start(getRepaintInterval());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void stopAnimationTimer() {
/*  228 */     if (this.animator != null) {
/*  229 */       this.animator.stop();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallListeners() {
/*  237 */     this.progressBar.removeChangeListener(this.changeListener);
/*  238 */     this.progressBar.removePropertyChangeListener(getHandler());
/*  239 */     this.handler = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/*  252 */     super.getBaseline(paramJComponent, paramInt1, paramInt2);
/*  253 */     if (this.progressBar.isStringPainted() && this.progressBar
/*  254 */       .getOrientation() == 0) {
/*      */       
/*  256 */       FontMetrics fontMetrics = this.progressBar.getFontMetrics(this.progressBar.getFont());
/*  257 */       Insets insets = this.progressBar.getInsets();
/*  258 */       int i = insets.top;
/*  259 */       paramInt2 = paramInt2 - insets.top - insets.bottom;
/*  260 */       return i + (paramInt2 + fontMetrics.getAscent() - fontMetrics
/*  261 */         .getLeading() - fontMetrics
/*  262 */         .getDescent()) / 2;
/*      */     } 
/*  264 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component.BaselineResizeBehavior getBaselineResizeBehavior(JComponent paramJComponent) {
/*  277 */     super.getBaselineResizeBehavior(paramJComponent);
/*  278 */     if (this.progressBar.isStringPainted() && this.progressBar
/*  279 */       .getOrientation() == 0) {
/*  280 */       return Component.BaselineResizeBehavior.CENTER_OFFSET;
/*      */     }
/*  282 */     return Component.BaselineResizeBehavior.OTHER;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Dimension getPreferredInnerHorizontal() {
/*  295 */     Dimension dimension = (Dimension)DefaultLookup.get(this.progressBar, this, "ProgressBar.horizontalSize");
/*      */     
/*  297 */     if (dimension == null) {
/*  298 */       dimension = new Dimension(146, 12);
/*      */     }
/*  300 */     return dimension;
/*      */   }
/*      */   
/*      */   protected Dimension getPreferredInnerVertical() {
/*  304 */     Dimension dimension = (Dimension)DefaultLookup.get(this.progressBar, this, "ProgressBar.verticalSize");
/*      */     
/*  306 */     if (dimension == null) {
/*  307 */       dimension = new Dimension(12, 146);
/*      */     }
/*  309 */     return dimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Color getSelectionForeground() {
/*  317 */     return this.selectionForeground;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Color getSelectionBackground() {
/*  325 */     return this.selectionBackground;
/*      */   }
/*      */   
/*      */   private int getCachedPercent() {
/*  329 */     return this.cachedPercent;
/*      */   }
/*      */   
/*      */   private void setCachedPercent(int paramInt) {
/*  333 */     this.cachedPercent = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getCellLength() {
/*  348 */     if (this.progressBar.isStringPainted()) {
/*  349 */       return 1;
/*      */     }
/*  351 */     return this.cellLength;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setCellLength(int paramInt) {
/*  356 */     this.cellLength = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getCellSpacing() {
/*  370 */     if (this.progressBar.isStringPainted()) {
/*  371 */       return 0;
/*      */     }
/*  373 */     return this.cellSpacing;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setCellSpacing(int paramInt) {
/*  378 */     this.cellSpacing = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getAmountFull(Insets paramInsets, int paramInt1, int paramInt2) {
/*  389 */     int i = 0;
/*  390 */     BoundedRangeModel boundedRangeModel = this.progressBar.getModel();
/*      */     
/*  392 */     if (boundedRangeModel.getMaximum() - boundedRangeModel.getMinimum() != 0) {
/*  393 */       if (this.progressBar.getOrientation() == 0) {
/*  394 */         i = (int)Math.round(paramInt1 * this.progressBar
/*  395 */             .getPercentComplete());
/*      */       } else {
/*  397 */         i = (int)Math.round(paramInt2 * this.progressBar
/*  398 */             .getPercentComplete());
/*      */       } 
/*      */     }
/*  401 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/*  409 */     if (this.progressBar.isIndeterminate()) {
/*  410 */       paintIndeterminate(paramGraphics, paramJComponent);
/*      */     } else {
/*  412 */       paintDeterminate(paramGraphics, paramJComponent);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rectangle getBox(Rectangle paramRectangle) {
/*  441 */     int i = getAnimationIndex();
/*  442 */     int j = this.numFrames / 2;
/*      */     
/*  444 */     if (sizeChanged() || this.delta == 0.0D || this.maxPosition == 0.0D) {
/*  445 */       updateSizes();
/*      */     }
/*      */     
/*  448 */     paramRectangle = getGenericBox(paramRectangle);
/*      */     
/*  450 */     if (paramRectangle == null) {
/*  451 */       return null;
/*      */     }
/*  453 */     if (j <= 0) {
/*  454 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  458 */     if (this.progressBar.getOrientation() == 0) {
/*  459 */       if (i < j) {
/*  460 */         this.componentInnards.x += 
/*  461 */           (int)Math.round(this.delta * i);
/*      */       } else {
/*  463 */         paramRectangle
/*  464 */           .x = this.maxPosition - (int)Math.round(this.delta * (i - j));
/*      */       }
/*      */     
/*      */     }
/*  468 */     else if (i < j) {
/*  469 */       this.componentInnards.y += 
/*  470 */         (int)Math.round(this.delta * i);
/*      */     } else {
/*  472 */       paramRectangle
/*  473 */         .y = this.maxPosition - (int)Math.round(this.delta * (i - j));
/*      */     } 
/*      */ 
/*      */     
/*  477 */     return paramRectangle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateSizes() {
/*  485 */     int i = 0;
/*      */     
/*  487 */     if (this.progressBar.getOrientation() == 0) {
/*  488 */       i = getBoxLength(this.componentInnards.width, this.componentInnards.height);
/*      */       
/*  490 */       this.maxPosition = this.componentInnards.x + this.componentInnards.width - i;
/*      */     }
/*      */     else {
/*      */       
/*  494 */       i = getBoxLength(this.componentInnards.height, this.componentInnards.width);
/*      */       
/*  496 */       this.maxPosition = this.componentInnards.y + this.componentInnards.height - i;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  501 */     this.delta = 2.0D * this.maxPosition / this.numFrames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Rectangle getGenericBox(Rectangle paramRectangle) {
/*  508 */     if (paramRectangle == null) {
/*  509 */       paramRectangle = new Rectangle();
/*      */     }
/*      */     
/*  512 */     if (this.progressBar.getOrientation() == 0) {
/*  513 */       paramRectangle.width = getBoxLength(this.componentInnards.width, this.componentInnards.height);
/*      */       
/*  515 */       if (paramRectangle.width < 0) {
/*  516 */         paramRectangle = null;
/*      */       } else {
/*  518 */         paramRectangle.height = this.componentInnards.height;
/*  519 */         paramRectangle.y = this.componentInnards.y;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  524 */       paramRectangle.height = getBoxLength(this.componentInnards.height, this.componentInnards.width);
/*      */       
/*  526 */       if (paramRectangle.height < 0) {
/*  527 */         paramRectangle = null;
/*      */       } else {
/*  529 */         paramRectangle.width = this.componentInnards.width;
/*  530 */         paramRectangle.x = this.componentInnards.x;
/*      */       } 
/*      */     } 
/*      */     
/*  534 */     return paramRectangle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getBoxLength(int paramInt1, int paramInt2) {
/*  571 */     return (int)Math.round(paramInt1 / 6.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintIndeterminate(Graphics paramGraphics, JComponent paramJComponent) {
/*  585 */     if (!(paramGraphics instanceof Graphics2D)) {
/*      */       return;
/*      */     }
/*      */     
/*  589 */     Insets insets = this.progressBar.getInsets();
/*  590 */     int i = this.progressBar.getWidth() - insets.right + insets.left;
/*  591 */     int j = this.progressBar.getHeight() - insets.top + insets.bottom;
/*      */     
/*  593 */     if (i <= 0 || j <= 0) {
/*      */       return;
/*      */     }
/*      */     
/*  597 */     Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*      */ 
/*      */     
/*  600 */     this.boxRect = getBox(this.boxRect);
/*  601 */     if (this.boxRect != null) {
/*  602 */       graphics2D.setColor(this.progressBar.getForeground());
/*  603 */       graphics2D.fillRect(this.boxRect.x, this.boxRect.y, this.boxRect.width, this.boxRect.height);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  608 */     if (this.progressBar.isStringPainted()) {
/*  609 */       if (this.progressBar.getOrientation() == 0) {
/*  610 */         paintString(graphics2D, insets.left, insets.top, i, j, this.boxRect.x, this.boxRect.width, insets);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  615 */         paintString(graphics2D, insets.left, insets.top, i, j, this.boxRect.y, this.boxRect.height, insets);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintDeterminate(Graphics paramGraphics, JComponent paramJComponent) {
/*  636 */     if (!(paramGraphics instanceof Graphics2D)) {
/*      */       return;
/*      */     }
/*      */     
/*  640 */     Insets insets = this.progressBar.getInsets();
/*  641 */     int i = this.progressBar.getWidth() - insets.right + insets.left;
/*  642 */     int j = this.progressBar.getHeight() - insets.top + insets.bottom;
/*      */     
/*  644 */     if (i <= 0 || j <= 0) {
/*      */       return;
/*      */     }
/*      */     
/*  648 */     int k = getCellLength();
/*  649 */     int m = getCellSpacing();
/*      */     
/*  651 */     int n = getAmountFull(insets, i, j);
/*      */     
/*  653 */     Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*  654 */     graphics2D.setColor(this.progressBar.getForeground());
/*      */     
/*  656 */     if (this.progressBar.getOrientation() == 0) {
/*      */       
/*  658 */       if (m == 0 && n > 0) {
/*      */         
/*  660 */         graphics2D.setStroke(new BasicStroke(j, 0, 2));
/*      */       }
/*      */       else {
/*      */         
/*  664 */         graphics2D.setStroke(new BasicStroke(j, 0, 2, 0.0F, new float[] { k, m }, 0.0F));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  669 */       if (BasicGraphicsUtils.isLeftToRight(paramJComponent)) {
/*  670 */         graphics2D.drawLine(insets.left, j / 2 + insets.top, n + insets.left, j / 2 + insets.top);
/*      */       } else {
/*      */         
/*  673 */         graphics2D.drawLine(i + insets.left, j / 2 + insets.top, i + insets.left - n, j / 2 + insets.top);
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  681 */       if (m == 0 && n > 0) {
/*      */         
/*  683 */         graphics2D.setStroke(new BasicStroke(i, 0, 2));
/*      */       }
/*      */       else {
/*      */         
/*  687 */         graphics2D.setStroke(new BasicStroke(i, 0, 2, 0.0F, new float[] { k, m }, 0.0F));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  692 */       graphics2D.drawLine(i / 2 + insets.left, insets.top + j, i / 2 + insets.left, insets.top + j - n);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  699 */     if (this.progressBar.isStringPainted()) {
/*  700 */       paintString(paramGraphics, insets.left, insets.top, i, j, n, insets);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintString(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Insets paramInsets) {
/*  710 */     if (this.progressBar.getOrientation() == 0) {
/*  711 */       if (BasicGraphicsUtils.isLeftToRight(this.progressBar)) {
/*  712 */         if (this.progressBar.isIndeterminate()) {
/*  713 */           this.boxRect = getBox(this.boxRect);
/*  714 */           paintString(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, this.boxRect.x, this.boxRect.width, paramInsets);
/*      */         } else {
/*      */           
/*  717 */           paintString(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, paramInt1, paramInt5, paramInsets);
/*      */         } 
/*      */       } else {
/*      */         
/*  721 */         paintString(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, paramInt1 + paramInt3 - paramInt5, paramInt5, paramInsets);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  726 */     else if (this.progressBar.isIndeterminate()) {
/*  727 */       this.boxRect = getBox(this.boxRect);
/*  728 */       paintString(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, this.boxRect.y, this.boxRect.height, paramInsets);
/*      */     } else {
/*      */       
/*  731 */       paintString(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, paramInt2 + paramInt4 - paramInt5, paramInt5, paramInsets);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void paintString(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Insets paramInsets) {
/*  753 */     if (!(paramGraphics instanceof Graphics2D)) {
/*      */       return;
/*      */     }
/*      */     
/*  757 */     Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*  758 */     String str = this.progressBar.getString();
/*  759 */     graphics2D.setFont(this.progressBar.getFont());
/*  760 */     Point point = getStringPlacement(graphics2D, str, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     
/*  762 */     Rectangle rectangle = graphics2D.getClipBounds();
/*      */     
/*  764 */     if (this.progressBar.getOrientation() == 0) {
/*  765 */       graphics2D.setColor(getSelectionBackground());
/*  766 */       SwingUtilities2.drawString(this.progressBar, graphics2D, str, point.x, point.y);
/*      */       
/*  768 */       graphics2D.setColor(getSelectionForeground());
/*  769 */       graphics2D.clipRect(paramInt5, paramInt2, paramInt6, paramInt4);
/*  770 */       SwingUtilities2.drawString(this.progressBar, graphics2D, str, point.x, point.y);
/*      */     } else {
/*      */       
/*  773 */       graphics2D.setColor(getSelectionBackground());
/*      */       
/*  775 */       AffineTransform affineTransform = AffineTransform.getRotateInstance(1.5707963267948966D);
/*  776 */       graphics2D.setFont(this.progressBar.getFont().deriveFont(affineTransform));
/*  777 */       point = getStringPlacement(graphics2D, str, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       
/*  779 */       SwingUtilities2.drawString(this.progressBar, graphics2D, str, point.x, point.y);
/*      */       
/*  781 */       graphics2D.setColor(getSelectionForeground());
/*  782 */       graphics2D.clipRect(paramInt1, paramInt5, paramInt3, paramInt6);
/*  783 */       SwingUtilities2.drawString(this.progressBar, graphics2D, str, point.x, point.y);
/*      */     } 
/*      */     
/*  786 */     graphics2D.setClip(rectangle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Point getStringPlacement(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  799 */     FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(this.progressBar, paramGraphics, this.progressBar
/*  800 */         .getFont());
/*  801 */     int i = SwingUtilities2.stringWidth(this.progressBar, fontMetrics, paramString);
/*      */ 
/*      */     
/*  804 */     if (this.progressBar.getOrientation() == 0) {
/*  805 */       return new Point(paramInt1 + Math.round((paramInt3 / 2 - i / 2)), paramInt2 + (paramInt4 + fontMetrics
/*      */           
/*  807 */           .getAscent() - fontMetrics
/*  808 */           .getLeading() - fontMetrics
/*  809 */           .getDescent()) / 2);
/*      */     }
/*  811 */     return new Point(paramInt1 + (paramInt3 - fontMetrics.getAscent() + fontMetrics
/*  812 */         .getLeading() + fontMetrics.getDescent()) / 2, paramInt2 + 
/*  813 */         Math.round((paramInt4 / 2 - i / 2)));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getPreferredSize(JComponent paramJComponent) {
/*      */     Dimension dimension;
/*  820 */     Insets insets = this.progressBar.getInsets();
/*  821 */     FontMetrics fontMetrics = this.progressBar.getFontMetrics(this.progressBar
/*  822 */         .getFont());
/*      */     
/*  824 */     if (this.progressBar.getOrientation() == 0) {
/*  825 */       dimension = new Dimension(getPreferredInnerHorizontal());
/*      */       
/*  827 */       if (this.progressBar.isStringPainted()) {
/*      */         
/*  829 */         String str = this.progressBar.getString();
/*  830 */         int i = SwingUtilities2.stringWidth(this.progressBar, fontMetrics, str);
/*      */         
/*  832 */         if (i > dimension.width) {
/*  833 */           dimension.width = i;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  841 */         int j = fontMetrics.getHeight() + fontMetrics.getDescent();
/*  842 */         if (j > dimension.height) {
/*  843 */           dimension.height = j;
/*      */         }
/*      */       } 
/*      */     } else {
/*  847 */       dimension = new Dimension(getPreferredInnerVertical());
/*      */       
/*  849 */       if (this.progressBar.isStringPainted()) {
/*  850 */         String str = this.progressBar.getString();
/*      */         
/*  852 */         int i = fontMetrics.getHeight() + fontMetrics.getDescent();
/*  853 */         if (i > dimension.width) {
/*  854 */           dimension.width = i;
/*      */         }
/*      */         
/*  857 */         int j = SwingUtilities2.stringWidth(this.progressBar, fontMetrics, str);
/*      */         
/*  859 */         if (j > dimension.height) {
/*  860 */           dimension.height = j;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  865 */     dimension.width += insets.left + insets.right;
/*  866 */     dimension.height += insets.top + insets.bottom;
/*  867 */     return dimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMinimumSize(JComponent paramJComponent) {
/*  875 */     Dimension dimension = getPreferredSize(this.progressBar);
/*  876 */     if (this.progressBar.getOrientation() == 0) {
/*  877 */       dimension.width = 10;
/*      */     } else {
/*  879 */       dimension.height = 10;
/*      */     } 
/*  881 */     return dimension;
/*      */   }
/*      */   
/*      */   public Dimension getMaximumSize(JComponent paramJComponent) {
/*  885 */     Dimension dimension = getPreferredSize(this.progressBar);
/*  886 */     if (this.progressBar.getOrientation() == 0) {
/*  887 */       dimension.width = 32767;
/*      */     } else {
/*  889 */       dimension.height = 32767;
/*      */     } 
/*  891 */     return dimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getAnimationIndex() {
/*  900 */     return this.animationIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int getFrameCount() {
/*  913 */     return this.numFrames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setAnimationIndex(int paramInt) {
/*  932 */     if (this.animationIndex != paramInt) {
/*  933 */       if (sizeChanged()) {
/*  934 */         this.animationIndex = paramInt;
/*  935 */         this.maxPosition = 0;
/*  936 */         this.delta = 0.0D;
/*  937 */         this.progressBar.repaint();
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  942 */       this.nextPaintRect = getBox(this.nextPaintRect);
/*      */ 
/*      */       
/*  945 */       this.animationIndex = paramInt;
/*      */ 
/*      */       
/*  948 */       if (this.nextPaintRect != null) {
/*  949 */         this.boxRect = getBox(this.boxRect);
/*  950 */         if (this.boxRect != null) {
/*  951 */           this.nextPaintRect.add(this.boxRect);
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       return;
/*      */     } 
/*      */     
/*  958 */     if (this.nextPaintRect != null) {
/*  959 */       this.progressBar.repaint(this.nextPaintRect);
/*      */     } else {
/*  961 */       this.progressBar.repaint();
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean sizeChanged() {
/*  966 */     if (this.oldComponentInnards == null || this.componentInnards == null) {
/*  967 */       return true;
/*      */     }
/*      */     
/*  970 */     this.oldComponentInnards.setRect(this.componentInnards);
/*  971 */     this.componentInnards = SwingUtilities.calculateInnerArea(this.progressBar, this.componentInnards);
/*      */     
/*  973 */     return !this.oldComponentInnards.equals(this.componentInnards);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void incrementAnimationIndex() {
/*  997 */     int i = getAnimationIndex() + 1;
/*      */     
/*  999 */     if (i < this.numFrames) {
/* 1000 */       setAnimationIndex(i);
/*      */     } else {
/* 1002 */       setAnimationIndex(0);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getRepaintInterval() {
/* 1021 */     return this.repaintInterval;
/*      */   }
/*      */   
/*      */   private int initRepaintInterval() {
/* 1025 */     this.repaintInterval = DefaultLookup.getInt(this.progressBar, this, "ProgressBar.repaintInterval", 50);
/*      */     
/* 1027 */     return this.repaintInterval;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getCycleTime() {
/* 1045 */     return this.cycleTime;
/*      */   }
/*      */   
/*      */   private int initCycleTime() {
/* 1049 */     this.cycleTime = DefaultLookup.getInt(this.progressBar, this, "ProgressBar.cycleTime", 3000);
/*      */     
/* 1051 */     return this.cycleTime;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void initIndeterminateDefaults() {
/* 1057 */     initRepaintInterval();
/* 1058 */     initCycleTime();
/*      */ 
/*      */     
/* 1061 */     if (this.repaintInterval <= 0) {
/* 1062 */       this.repaintInterval = 100;
/*      */     }
/*      */ 
/*      */     
/* 1066 */     if (this.repaintInterval > this.cycleTime) {
/* 1067 */       this.cycleTime = this.repaintInterval * 20;
/*      */     } else {
/*      */       
/* 1070 */       int i = (int)Math.ceil(this.cycleTime / this.repaintInterval * 2.0D);
/*      */ 
/*      */       
/* 1073 */       this.cycleTime = this.repaintInterval * i * 2;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initIndeterminateValues() {
/* 1084 */     initIndeterminateDefaults();
/*      */     
/* 1086 */     this.numFrames = this.cycleTime / this.repaintInterval;
/* 1087 */     initAnimationIndex();
/*      */     
/* 1089 */     this.boxRect = new Rectangle();
/* 1090 */     this.nextPaintRect = new Rectangle();
/* 1091 */     this.componentInnards = new Rectangle();
/* 1092 */     this.oldComponentInnards = new Rectangle();
/*      */ 
/*      */ 
/*      */     
/* 1096 */     this.progressBar.addHierarchyListener(getHandler());
/*      */ 
/*      */     
/* 1099 */     if (this.progressBar.isDisplayable()) {
/* 1100 */       startAnimationTimer();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void cleanUpIndeterminateValues() {
/* 1107 */     if (this.progressBar.isDisplayable()) {
/* 1108 */       stopAnimationTimer();
/*      */     }
/*      */     
/* 1111 */     this.cycleTime = this.repaintInterval = 0;
/* 1112 */     this.numFrames = this.animationIndex = 0;
/* 1113 */     this.maxPosition = 0;
/* 1114 */     this.delta = 0.0D;
/*      */     
/* 1116 */     this.boxRect = this.nextPaintRect = null;
/* 1117 */     this.componentInnards = this.oldComponentInnards = null;
/*      */     
/* 1119 */     this.progressBar.removeHierarchyListener(getHandler());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void initAnimationIndex() {
/* 1125 */     if (this.progressBar.getOrientation() == 0 && 
/* 1126 */       BasicGraphicsUtils.isLeftToRight(this.progressBar)) {
/*      */ 
/*      */       
/* 1129 */       setAnimationIndex(0);
/*      */     } else {
/*      */       
/* 1132 */       setAnimationIndex(this.numFrames / 2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class Animator
/*      */     implements ActionListener
/*      */   {
/*      */     private Timer timer;
/*      */ 
/*      */     
/*      */     private long previousDelay;
/*      */ 
/*      */     
/*      */     private int interval;
/*      */     
/*      */     private long lastCall;
/*      */     
/* 1151 */     private int MINIMUM_DELAY = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void start(int param1Int) {
/* 1158 */       this.previousDelay = param1Int;
/* 1159 */       this.lastCall = 0L;
/*      */       
/* 1161 */       if (this.timer == null) {
/* 1162 */         this.timer = new Timer(param1Int, this);
/*      */       } else {
/* 1164 */         this.timer.setDelay(param1Int);
/*      */       } 
/*      */       
/* 1167 */       if (BasicProgressBarUI.ADJUSTTIMER) {
/* 1168 */         this.timer.setRepeats(false);
/* 1169 */         this.timer.setCoalesce(false);
/*      */       } 
/*      */       
/* 1172 */       this.timer.start();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void stop() {
/* 1179 */       this.timer.stop();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1186 */       if (BasicProgressBarUI.ADJUSTTIMER) {
/* 1187 */         long l = System.currentTimeMillis();
/*      */         
/* 1189 */         if (this.lastCall > 0L) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1197 */           int i = (int)(this.previousDelay - l + this.lastCall + BasicProgressBarUI.this.getRepaintInterval());
/* 1198 */           if (i < this.MINIMUM_DELAY) {
/* 1199 */             i = this.MINIMUM_DELAY;
/*      */           }
/* 1201 */           this.timer.setInitialDelay(i);
/* 1202 */           this.previousDelay = i;
/*      */         } 
/* 1204 */         this.timer.start();
/* 1205 */         this.lastCall = l;
/*      */       } 
/*      */       
/* 1208 */       BasicProgressBarUI.this.incrementAnimationIndex();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Animator() {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class ChangeHandler
/*      */     implements ChangeListener
/*      */   {
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 1223 */       BasicProgressBarUI.this.getHandler().stateChanged(param1ChangeEvent);
/*      */     } }
/*      */   
/*      */   private class Handler implements ChangeListener, PropertyChangeListener, HierarchyListener {
/*      */     private Handler() {}
/*      */     
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/*      */       int j;
/* 1231 */       BoundedRangeModel boundedRangeModel = BasicProgressBarUI.this.progressBar.getModel();
/* 1232 */       int i = boundedRangeModel.getMaximum() - boundedRangeModel.getMinimum();
/*      */       
/* 1234 */       int k = BasicProgressBarUI.this.getCachedPercent();
/*      */       
/* 1236 */       if (i > 0) {
/* 1237 */         j = (int)(100L * boundedRangeModel.getValue() / i);
/*      */       } else {
/* 1239 */         j = 0;
/*      */       } 
/*      */       
/* 1242 */       if (j != k) {
/* 1243 */         BasicProgressBarUI.this.setCachedPercent(j);
/* 1244 */         BasicProgressBarUI.this.progressBar.repaint();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1250 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 1251 */       if ("indeterminate" == str) {
/* 1252 */         if (BasicProgressBarUI.this.progressBar.isIndeterminate()) {
/* 1253 */           BasicProgressBarUI.this.initIndeterminateValues();
/*      */         } else {
/*      */           
/* 1256 */           BasicProgressBarUI.this.cleanUpIndeterminateValues();
/*      */         } 
/* 1258 */         BasicProgressBarUI.this.progressBar.repaint();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void hierarchyChanged(HierarchyEvent param1HierarchyEvent) {
/* 1264 */       if ((param1HierarchyEvent.getChangeFlags() & 0x2L) != 0L && 
/* 1265 */         BasicProgressBarUI.this.progressBar.isIndeterminate())
/* 1266 */         if (BasicProgressBarUI.this.progressBar.isDisplayable()) {
/* 1267 */           BasicProgressBarUI.this.startAnimationTimer();
/*      */         } else {
/* 1269 */           BasicProgressBarUI.this.stopAnimationTimer();
/*      */         }  
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicProgressBarUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */