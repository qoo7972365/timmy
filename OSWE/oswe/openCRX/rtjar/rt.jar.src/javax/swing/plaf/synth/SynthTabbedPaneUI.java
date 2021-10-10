/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicTabbedPaneUI;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthTabbedPaneUI
/*     */   extends BasicTabbedPaneUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*  73 */   private int tabOverlap = 0;
/*     */ 
/*     */   
/*     */   private boolean extendTabsToBase = false;
/*     */ 
/*     */   
/*     */   private SynthContext tabAreaContext;
/*     */ 
/*     */   
/*     */   private SynthContext tabContext;
/*     */ 
/*     */   
/*     */   private SynthContext tabContentContext;
/*     */   
/*     */   private SynthStyle style;
/*     */   
/*     */   private SynthStyle tabStyle;
/*     */   
/*     */   private SynthStyle tabAreaStyle;
/*     */   
/*     */   private SynthStyle tabContentStyle;
/*     */   
/*  95 */   private Rectangle textRect = new Rectangle();
/*  96 */   private Rectangle iconRect = new Rectangle();
/*     */   
/*  98 */   private Rectangle tabAreaBounds = new Rectangle();
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean tabAreaStatesMatchSelectedTab = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean nudgeSelectedLabel = true;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean selectedTabIsPressed = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 115 */     return new SynthTabbedPaneUI();
/*     */   }
/*     */   
/*     */   private boolean scrollableTabLayoutEnabled() {
/* 119 */     return (this.tabPane.getTabLayoutPolicy() == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/* 127 */     updateStyle(this.tabPane);
/*     */   }
/*     */   
/*     */   private void updateStyle(JTabbedPane paramJTabbedPane) {
/* 131 */     SynthContext synthContext = getContext(paramJTabbedPane, 1);
/* 132 */     SynthStyle synthStyle = this.style;
/* 133 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*     */ 
/*     */     
/* 136 */     if (this.style != synthStyle) {
/* 137 */       this
/* 138 */         .tabRunOverlay = this.style.getInt(synthContext, "TabbedPane.tabRunOverlay", 0);
/* 139 */       this.tabOverlap = this.style.getInt(synthContext, "TabbedPane.tabOverlap", 0);
/* 140 */       this.extendTabsToBase = this.style.getBoolean(synthContext, "TabbedPane.extendTabsToBase", false);
/*     */       
/* 142 */       this.textIconGap = this.style.getInt(synthContext, "TabbedPane.textIconGap", 0);
/* 143 */       this.selectedTabPadInsets = (Insets)this.style.get(synthContext, "TabbedPane.selectedTabPadInsets");
/*     */       
/* 145 */       if (this.selectedTabPadInsets == null) {
/* 146 */         this.selectedTabPadInsets = new Insets(0, 0, 0, 0);
/*     */       }
/* 148 */       this.tabAreaStatesMatchSelectedTab = this.style.getBoolean(synthContext, "TabbedPane.tabAreaStatesMatchSelectedTab", false);
/*     */       
/* 150 */       this.nudgeSelectedLabel = this.style.getBoolean(synthContext, "TabbedPane.nudgeSelectedLabel", true);
/*     */       
/* 152 */       if (synthStyle != null) {
/* 153 */         uninstallKeyboardActions();
/* 154 */         installKeyboardActions();
/*     */       } 
/*     */     } 
/* 157 */     synthContext.dispose();
/*     */     
/* 159 */     if (this.tabContext != null) {
/* 160 */       this.tabContext.dispose();
/*     */     }
/* 162 */     this.tabContext = getContext(paramJTabbedPane, Region.TABBED_PANE_TAB, 1);
/* 163 */     this.tabStyle = SynthLookAndFeel.updateStyle(this.tabContext, this);
/* 164 */     this.tabInsets = this.tabStyle.getInsets(this.tabContext, null);
/*     */ 
/*     */     
/* 167 */     if (this.tabAreaContext != null) {
/* 168 */       this.tabAreaContext.dispose();
/*     */     }
/* 170 */     this.tabAreaContext = getContext(paramJTabbedPane, Region.TABBED_PANE_TAB_AREA, 1);
/* 171 */     this.tabAreaStyle = SynthLookAndFeel.updateStyle(this.tabAreaContext, this);
/* 172 */     this.tabAreaInsets = this.tabAreaStyle.getInsets(this.tabAreaContext, null);
/*     */ 
/*     */     
/* 175 */     if (this.tabContentContext != null) {
/* 176 */       this.tabContentContext.dispose();
/*     */     }
/* 178 */     this.tabContentContext = getContext(paramJTabbedPane, Region.TABBED_PANE_CONTENT, 1);
/* 179 */     this.tabContentStyle = SynthLookAndFeel.updateStyle(this.tabContentContext, this);
/*     */     
/* 181 */     this
/* 182 */       .contentBorderInsets = this.tabContentStyle.getInsets(this.tabContentContext, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/* 190 */     super.installListeners();
/* 191 */     this.tabPane.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 199 */     super.uninstallListeners();
/* 200 */     this.tabPane.removePropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 208 */     SynthContext synthContext = getContext(this.tabPane, 1);
/* 209 */     this.style.uninstallDefaults(synthContext);
/* 210 */     synthContext.dispose();
/* 211 */     this.style = null;
/*     */     
/* 213 */     this.tabStyle.uninstallDefaults(this.tabContext);
/* 214 */     this.tabContext.dispose();
/* 215 */     this.tabContext = null;
/* 216 */     this.tabStyle = null;
/*     */     
/* 218 */     this.tabAreaStyle.uninstallDefaults(this.tabAreaContext);
/* 219 */     this.tabAreaContext.dispose();
/* 220 */     this.tabAreaContext = null;
/* 221 */     this.tabAreaStyle = null;
/*     */     
/* 223 */     this.tabContentStyle.uninstallDefaults(this.tabContentContext);
/* 224 */     this.tabContentContext.dispose();
/* 225 */     this.tabContentContext = null;
/* 226 */     this.tabContentStyle = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 234 */     return getContext(paramJComponent, SynthLookAndFeel.getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 238 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, Region paramRegion, int paramInt) {
/* 242 */     SynthStyle synthStyle = null;
/*     */     
/* 244 */     if (paramRegion == Region.TABBED_PANE_TAB) {
/* 245 */       synthStyle = this.tabStyle;
/*     */     }
/* 247 */     else if (paramRegion == Region.TABBED_PANE_TAB_AREA) {
/* 248 */       synthStyle = this.tabAreaStyle;
/*     */     }
/* 250 */     else if (paramRegion == Region.TABBED_PANE_CONTENT) {
/* 251 */       synthStyle = this.tabContentStyle;
/*     */     } 
/* 253 */     return SynthContext.getContext(paramJComponent, paramRegion, synthStyle, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JButton createScrollButton(int paramInt) {
/* 264 */     if (UIManager.getBoolean("TabbedPane.useBasicArrows")) {
/* 265 */       JButton jButton = super.createScrollButton(paramInt);
/* 266 */       jButton.setBorder(BorderFactory.createEmptyBorder());
/* 267 */       return jButton;
/*     */     } 
/* 269 */     return new SynthScrollableTabButton(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 277 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/* 278 */       updateStyle(this.tabPane);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MouseListener createMouseListener() {
/* 289 */     final MouseListener delegate = super.createMouseListener();
/* 290 */     final MouseMotionListener delegate2 = (MouseMotionListener)mouseListener;
/* 291 */     return new MouseListener() {
/* 292 */         public void mouseClicked(MouseEvent param1MouseEvent) { delegate.mouseClicked(param1MouseEvent); }
/* 293 */         public void mouseEntered(MouseEvent param1MouseEvent) { delegate.mouseEntered(param1MouseEvent); } public void mouseExited(MouseEvent param1MouseEvent) {
/* 294 */           delegate.mouseExited(param1MouseEvent);
/*     */         }
/*     */         public void mousePressed(MouseEvent param1MouseEvent) {
/* 297 */           if (!SynthTabbedPaneUI.this.tabPane.isEnabled()) {
/*     */             return;
/*     */           }
/*     */           
/* 301 */           int i = SynthTabbedPaneUI.this.tabForCoordinate(SynthTabbedPaneUI.this.tabPane, param1MouseEvent.getX(), param1MouseEvent.getY());
/* 302 */           if (i >= 0 && SynthTabbedPaneUI.this.tabPane.isEnabledAt(i) && 
/* 303 */             i == SynthTabbedPaneUI.this.tabPane.getSelectedIndex()) {
/*     */             
/* 305 */             SynthTabbedPaneUI.this.selectedTabIsPressed = true;
/*     */             
/* 307 */             SynthTabbedPaneUI.this.tabPane.repaint();
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 312 */           delegate.mousePressed(param1MouseEvent);
/*     */         }
/*     */         
/*     */         public void mouseReleased(MouseEvent param1MouseEvent) {
/* 316 */           if (SynthTabbedPaneUI.this.selectedTabIsPressed) {
/* 317 */             SynthTabbedPaneUI.this.selectedTabIsPressed = false;
/*     */             
/* 319 */             SynthTabbedPaneUI.this.tabPane.repaint();
/*     */           } 
/*     */           
/* 322 */           delegate.mouseReleased(param1MouseEvent);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 328 */           delegate2.mouseMoved(param1MouseEvent);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getTabLabelShiftX(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 338 */     if (this.nudgeSelectedLabel) {
/* 339 */       return super.getTabLabelShiftX(paramInt1, paramInt2, paramBoolean);
/*     */     }
/* 341 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getTabLabelShiftY(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 350 */     if (this.nudgeSelectedLabel) {
/* 351 */       return super.getTabLabelShiftY(paramInt1, paramInt2, paramBoolean);
/*     */     }
/* 353 */     return 0;
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
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 371 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 373 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 374 */     synthContext.getPainter().paintTabbedPaneBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 375 */         .getWidth(), paramJComponent.getHeight());
/* 376 */     paint(synthContext, paramGraphics);
/* 377 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getBaseline(int paramInt) {
/* 385 */     if (this.tabPane.getTabComponentAt(paramInt) != null || 
/* 386 */       getTextViewForTab(paramInt) != null) {
/* 387 */       return super.getBaseline(paramInt);
/*     */     }
/* 389 */     String str = this.tabPane.getTitleAt(paramInt);
/* 390 */     Font font = this.tabContext.getStyle().getFont(this.tabContext);
/* 391 */     FontMetrics fontMetrics = getFontMetrics(font);
/* 392 */     Icon icon = getIconForTab(paramInt);
/* 393 */     this.textRect.setBounds(0, 0, 0, 0);
/* 394 */     this.iconRect.setBounds(0, 0, 0, 0);
/* 395 */     this.calcRect.setBounds(0, 0, 32767, this.maxTabHeight);
/* 396 */     this.tabContext.getStyle().getGraphicsUtils(this.tabContext).layoutText(this.tabContext, fontMetrics, str, icon, 0, 0, 10, 0, this.calcRect, this.iconRect, this.textRect, this.textIconGap);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 401 */     return this.textRect.y + fontMetrics.getAscent() + getBaselineOffset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 410 */     paramSynthContext.getPainter().paintTabbedPaneBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
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
/* 424 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 426 */     paint(synthContext, paramGraphics);
/* 427 */     synthContext.dispose();
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
/* 438 */     int i = this.tabPane.getSelectedIndex();
/* 439 */     int j = this.tabPane.getTabPlacement();
/*     */     
/* 441 */     ensureCurrentLayout();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 447 */     if (!scrollableTabLayoutEnabled()) {
/* 448 */       int i2; Insets insets = this.tabPane.getInsets();
/* 449 */       int k = insets.left;
/* 450 */       int m = insets.top;
/* 451 */       int n = this.tabPane.getWidth() - insets.left - insets.right;
/* 452 */       int i1 = this.tabPane.getHeight() - insets.top - insets.bottom;
/*     */       
/* 454 */       switch (j) {
/*     */         case 2:
/* 456 */           n = calculateTabAreaWidth(j, this.runCount, this.maxTabWidth);
/*     */           break;
/*     */         
/*     */         case 4:
/* 460 */           i2 = calculateTabAreaWidth(j, this.runCount, this.maxTabWidth);
/*     */           
/* 462 */           k = k + n - i2;
/* 463 */           n = i2;
/*     */           break;
/*     */         case 3:
/* 466 */           i2 = calculateTabAreaHeight(j, this.runCount, this.maxTabHeight);
/*     */           
/* 468 */           m = m + i1 - i2;
/* 469 */           i1 = i2;
/*     */           break;
/*     */         
/*     */         default:
/* 473 */           i1 = calculateTabAreaHeight(j, this.runCount, this.maxTabHeight);
/*     */           break;
/*     */       } 
/*     */       
/* 477 */       this.tabAreaBounds.setBounds(k, m, n, i1);
/*     */       
/* 479 */       if (paramGraphics.getClipBounds().intersects(this.tabAreaBounds)) {
/* 480 */         paintTabArea(this.tabAreaContext, paramGraphics, j, i, this.tabAreaBounds);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 486 */     paintContentBorder(this.tabContentContext, paramGraphics, j, i);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintTabArea(Graphics paramGraphics, int paramInt1, int paramInt2) {
/* 492 */     Insets insets = this.tabPane.getInsets();
/* 493 */     int i = insets.left;
/* 494 */     int j = insets.top;
/* 495 */     int k = this.tabPane.getWidth() - insets.left - insets.right;
/* 496 */     int m = this.tabPane.getHeight() - insets.top - insets.bottom;
/*     */     
/* 498 */     paintTabArea(this.tabAreaContext, paramGraphics, paramInt1, paramInt2, new Rectangle(i, j, k, m));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintTabArea(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, Rectangle paramRectangle) {
/* 505 */     Rectangle rectangle = paramGraphics.getClipBounds();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 512 */     if (this.tabAreaStatesMatchSelectedTab && paramInt2 >= 0) {
/* 513 */       updateTabContext(paramInt2, true, this.selectedTabIsPressed, 
/* 514 */           (getRolloverTab() == paramInt2), 
/* 515 */           (getFocusIndex() == paramInt2));
/* 516 */       paramSynthContext.setComponentState(this.tabContext.getComponentState());
/*     */     } else {
/* 518 */       paramSynthContext.setComponentState(1);
/*     */     } 
/*     */ 
/*     */     
/* 522 */     SynthLookAndFeel.updateSubregion(paramSynthContext, paramGraphics, paramRectangle);
/* 523 */     paramSynthContext.getPainter().paintTabbedPaneTabAreaBackground(paramSynthContext, paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, paramInt1);
/*     */ 
/*     */     
/* 526 */     paramSynthContext.getPainter().paintTabbedPaneTabAreaBorder(paramSynthContext, paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, paramInt1);
/*     */ 
/*     */ 
/*     */     
/* 530 */     int i = this.tabPane.getTabCount();
/*     */     
/* 532 */     this.iconRect.setBounds(0, 0, 0, 0);
/* 533 */     this.textRect.setBounds(0, 0, 0, 0);
/*     */ 
/*     */     
/* 536 */     for (int j = this.runCount - 1; j >= 0; j--) {
/* 537 */       int k = this.tabRuns[j];
/* 538 */       int m = this.tabRuns[(j == this.runCount - 1) ? 0 : (j + 1)];
/* 539 */       int n = (m != 0) ? (m - 1) : (i - 1);
/* 540 */       for (int i1 = k; i1 <= n; i1++) {
/* 541 */         if (this.rects[i1].intersects(rectangle) && paramInt2 != i1) {
/* 542 */           paintTab(this.tabContext, paramGraphics, paramInt1, this.rects, i1, this.iconRect, this.textRect);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 548 */     if (paramInt2 >= 0 && 
/* 549 */       this.rects[paramInt2].intersects(rectangle)) {
/* 550 */       paintTab(this.tabContext, paramGraphics, paramInt1, this.rects, paramInt2, this.iconRect, this.textRect);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setRolloverTab(int paramInt) {
/* 561 */     int i = getRolloverTab();
/* 562 */     super.setRolloverTab(paramInt);
/*     */     
/* 564 */     Rectangle rectangle = null;
/*     */     
/* 566 */     if (i != paramInt && this.tabAreaStatesMatchSelectedTab) {
/*     */       
/* 568 */       this.tabPane.repaint();
/*     */     } else {
/* 570 */       if (i >= 0 && i < this.tabPane.getTabCount()) {
/* 571 */         rectangle = getTabBounds(this.tabPane, i);
/* 572 */         if (rectangle != null) {
/* 573 */           this.tabPane.repaint(rectangle);
/*     */         }
/*     */       } 
/*     */       
/* 577 */       if (paramInt >= 0) {
/* 578 */         rectangle = getTabBounds(this.tabPane, paramInt);
/* 579 */         if (rectangle != null) {
/* 580 */           this.tabPane.repaint(rectangle);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintTab(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, Rectangle[] paramArrayOfRectangle, int paramInt2, Rectangle paramRectangle1, Rectangle paramRectangle2) {
/* 589 */     Rectangle rectangle = paramArrayOfRectangle[paramInt2];
/* 590 */     int i = this.tabPane.getSelectedIndex();
/* 591 */     boolean bool = (i == paramInt2) ? true : false;
/* 592 */     updateTabContext(paramInt2, bool, (bool && this.selectedTabIsPressed), 
/* 593 */         (getRolloverTab() == paramInt2), 
/* 594 */         (getFocusIndex() == paramInt2));
/*     */     
/* 596 */     SynthLookAndFeel.updateSubregion(paramSynthContext, paramGraphics, rectangle);
/* 597 */     int j = rectangle.x;
/* 598 */     int k = rectangle.y;
/* 599 */     int m = rectangle.height;
/* 600 */     int n = rectangle.width;
/* 601 */     int i1 = this.tabPane.getTabPlacement();
/* 602 */     if (this.extendTabsToBase && this.runCount > 1)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 609 */       if (i >= 0) {
/* 610 */         int i2, i3, i4, i5; Rectangle rectangle1 = paramArrayOfRectangle[i];
/* 611 */         switch (i1) {
/*     */           case 1:
/* 613 */             i2 = rectangle1.y + rectangle1.height;
/* 614 */             m = i2 - rectangle.y;
/*     */             break;
/*     */           case 2:
/* 617 */             i3 = rectangle1.x + rectangle1.width;
/* 618 */             n = i3 - rectangle.x;
/*     */             break;
/*     */           case 3:
/* 621 */             i4 = rectangle1.y;
/* 622 */             m = rectangle.y + rectangle.height - i4;
/* 623 */             k = i4;
/*     */             break;
/*     */           case 4:
/* 626 */             i5 = rectangle1.x;
/* 627 */             n = rectangle.x + rectangle.width - i5;
/* 628 */             j = i5;
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     }
/* 633 */     this.tabContext.getPainter().paintTabbedPaneTabBackground(this.tabContext, paramGraphics, j, k, n, m, paramInt2, i1);
/*     */     
/* 635 */     this.tabContext.getPainter().paintTabbedPaneTabBorder(this.tabContext, paramGraphics, j, k, n, m, paramInt2, i1);
/*     */ 
/*     */     
/* 638 */     if (this.tabPane.getTabComponentAt(paramInt2) == null) {
/* 639 */       String str = this.tabPane.getTitleAt(paramInt2);
/* 640 */       Font font = paramSynthContext.getStyle().getFont(paramSynthContext);
/* 641 */       FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(this.tabPane, paramGraphics, font);
/* 642 */       Icon icon = getIconForTab(paramInt2);
/*     */       
/* 644 */       layoutLabel(paramSynthContext, paramInt1, fontMetrics, paramInt2, str, icon, rectangle, paramRectangle1, paramRectangle2, bool);
/*     */ 
/*     */       
/* 647 */       paintText(paramSynthContext, paramGraphics, paramInt1, font, fontMetrics, paramInt2, str, paramRectangle2, bool);
/*     */ 
/*     */       
/* 650 */       paintIcon(paramGraphics, paramInt1, paramInt2, icon, paramRectangle1, bool);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void layoutLabel(SynthContext paramSynthContext, int paramInt1, FontMetrics paramFontMetrics, int paramInt2, String paramString, Icon paramIcon, Rectangle paramRectangle1, Rectangle paramRectangle2, Rectangle paramRectangle3, boolean paramBoolean) {
/* 659 */     View view = getTextViewForTab(paramInt2);
/* 660 */     if (view != null) {
/* 661 */       this.tabPane.putClientProperty("html", view);
/*     */     }
/*     */     
/* 664 */     paramRectangle3.x = paramRectangle3.y = paramRectangle2.x = paramRectangle2.y = 0;
/*     */     
/* 666 */     paramSynthContext.getStyle().getGraphicsUtils(paramSynthContext).layoutText(paramSynthContext, paramFontMetrics, paramString, paramIcon, 0, 0, 10, 0, paramRectangle1, paramRectangle2, paramRectangle3, this.textIconGap);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 671 */     this.tabPane.putClientProperty("html", (Object)null);
/*     */     
/* 673 */     int i = getTabLabelShiftX(paramInt1, paramInt2, paramBoolean);
/* 674 */     int j = getTabLabelShiftY(paramInt1, paramInt2, paramBoolean);
/* 675 */     paramRectangle2.x += i;
/* 676 */     paramRectangle2.y += j;
/* 677 */     paramRectangle3.x += i;
/* 678 */     paramRectangle3.y += j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintText(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, Font paramFont, FontMetrics paramFontMetrics, int paramInt2, String paramString, Rectangle paramRectangle, boolean paramBoolean) {
/* 686 */     paramGraphics.setFont(paramFont);
/*     */     
/* 688 */     View view = getTextViewForTab(paramInt2);
/* 689 */     if (view != null) {
/*     */       
/* 691 */       view.paint(paramGraphics, paramRectangle);
/*     */     } else {
/*     */       
/* 694 */       int i = this.tabPane.getDisplayedMnemonicIndexAt(paramInt2);
/*     */       
/* 696 */       paramGraphics.setColor(paramSynthContext.getStyle().getColor(paramSynthContext, ColorType.TEXT_FOREGROUND));
/* 697 */       paramSynthContext.getStyle().getGraphicsUtils(paramSynthContext).paintText(paramSynthContext, paramGraphics, paramString, paramRectangle, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintContentBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2) {
/* 705 */     int i = this.tabPane.getWidth();
/* 706 */     int j = this.tabPane.getHeight();
/* 707 */     Insets insets = this.tabPane.getInsets();
/*     */     
/* 709 */     int k = insets.left;
/* 710 */     int m = insets.top;
/* 711 */     int n = i - insets.right - insets.left;
/* 712 */     int i1 = j - insets.top - insets.bottom;
/*     */     
/* 714 */     switch (paramInt1) {
/*     */       case 2:
/* 716 */         k += calculateTabAreaWidth(paramInt1, this.runCount, this.maxTabWidth);
/* 717 */         n -= k - insets.left;
/*     */         break;
/*     */       case 4:
/* 720 */         n -= calculateTabAreaWidth(paramInt1, this.runCount, this.maxTabWidth);
/*     */         break;
/*     */       case 3:
/* 723 */         i1 -= calculateTabAreaHeight(paramInt1, this.runCount, this.maxTabHeight);
/*     */         break;
/*     */       
/*     */       default:
/* 727 */         m += calculateTabAreaHeight(paramInt1, this.runCount, this.maxTabHeight);
/* 728 */         i1 -= m - insets.top; break;
/*     */     } 
/* 730 */     SynthLookAndFeel.updateSubregion(paramSynthContext, paramGraphics, new Rectangle(k, m, n, i1));
/* 731 */     paramSynthContext.getPainter().paintTabbedPaneContentBackground(paramSynthContext, paramGraphics, k, m, n, i1);
/*     */     
/* 733 */     paramSynthContext.getPainter().paintTabbedPaneContentBorder(paramSynthContext, paramGraphics, k, m, n, i1);
/*     */   }
/*     */   
/*     */   private void ensureCurrentLayout() {
/* 737 */     if (!this.tabPane.isValid()) {
/* 738 */       this.tabPane.validate();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 744 */     if (!this.tabPane.isValid()) {
/* 745 */       BasicTabbedPaneUI.TabbedPaneLayout tabbedPaneLayout = (BasicTabbedPaneUI.TabbedPaneLayout)this.tabPane.getLayout();
/* 746 */       tabbedPaneLayout.calculateLayoutInfo();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int calculateMaxTabHeight(int paramInt) {
/* 755 */     FontMetrics fontMetrics = getFontMetrics(this.tabContext.getStyle().getFont(this.tabContext));
/*     */     
/* 757 */     int i = this.tabPane.getTabCount();
/* 758 */     int j = 0;
/* 759 */     int k = fontMetrics.getHeight();
/* 760 */     for (byte b = 0; b < i; b++) {
/* 761 */       j = Math.max(calculateTabHeight(paramInt, b, k), j);
/*     */     }
/* 763 */     return j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int calculateTabWidth(int paramInt1, int paramInt2, FontMetrics paramFontMetrics) {
/* 772 */     Icon icon = getIconForTab(paramInt2);
/* 773 */     Insets insets = getTabInsets(paramInt1, paramInt2);
/* 774 */     int i = insets.left + insets.right;
/* 775 */     Component component = this.tabPane.getTabComponentAt(paramInt2);
/* 776 */     if (component != null) {
/* 777 */       i += (component.getPreferredSize()).width;
/*     */     } else {
/* 779 */       if (icon != null) {
/* 780 */         i += icon.getIconWidth() + this.textIconGap;
/*     */       }
/* 782 */       View view = getTextViewForTab(paramInt2);
/* 783 */       if (view != null) {
/*     */         
/* 785 */         i += (int)view.getPreferredSpan(0);
/*     */       } else {
/*     */         
/* 788 */         String str = this.tabPane.getTitleAt(paramInt2);
/* 789 */         i += this.tabContext.getStyle().getGraphicsUtils(this.tabContext)
/* 790 */           .computeStringWidth(this.tabContext, paramFontMetrics.getFont(), paramFontMetrics, str);
/*     */       } 
/*     */     } 
/*     */     
/* 794 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int calculateMaxTabWidth(int paramInt) {
/* 802 */     FontMetrics fontMetrics = getFontMetrics(this.tabContext.getStyle().getFont(this.tabContext));
/*     */     
/* 804 */     int i = this.tabPane.getTabCount();
/* 805 */     int j = 0;
/* 806 */     for (byte b = 0; b < i; b++) {
/* 807 */       j = Math.max(calculateTabWidth(paramInt, b, fontMetrics), j);
/*     */     }
/*     */     
/* 810 */     return j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Insets getTabInsets(int paramInt1, int paramInt2) {
/* 818 */     updateTabContext(paramInt2, false, false, false, 
/* 819 */         (getFocusIndex() == paramInt2));
/* 820 */     return this.tabInsets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FontMetrics getFontMetrics() {
/* 828 */     return getFontMetrics(this.tabContext.getStyle().getFont(this.tabContext));
/*     */   }
/*     */   
/*     */   private FontMetrics getFontMetrics(Font paramFont) {
/* 832 */     return this.tabPane.getFontMetrics(paramFont);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateTabContext(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
/* 837 */     int i = 0;
/* 838 */     if (!this.tabPane.isEnabled() || !this.tabPane.isEnabledAt(paramInt)) {
/* 839 */       i |= 0x8;
/* 840 */       if (paramBoolean1) {
/* 841 */         i |= 0x200;
/*     */       }
/*     */     }
/* 844 */     else if (paramBoolean1) {
/* 845 */       i |= 0x201;
/* 846 */       if (paramBoolean3 && UIManager.getBoolean("TabbedPane.isTabRollover")) {
/* 847 */         i |= 0x2;
/*     */       }
/*     */     }
/* 850 */     else if (paramBoolean3) {
/* 851 */       i |= 0x3;
/*     */     } else {
/*     */       
/* 854 */       i = SynthLookAndFeel.getComponentState(this.tabPane);
/* 855 */       i &= 0xFFFFFEFF;
/*     */     } 
/* 857 */     if (paramBoolean4 && this.tabPane.hasFocus()) {
/* 858 */       i |= 0x100;
/*     */     }
/* 860 */     if (paramBoolean2) {
/* 861 */       i |= 0x4;
/*     */     }
/*     */     
/* 864 */     this.tabContext.setComponentState(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LayoutManager createLayoutManager() {
/* 875 */     if (this.tabPane.getTabLayoutPolicy() == 1) {
/* 876 */       return super.createLayoutManager();
/*     */     }
/* 878 */     return new BasicTabbedPaneUI.TabbedPaneLayout()
/*     */       {
/*     */         public void calculateLayoutInfo() {
/* 881 */           super.calculateLayoutInfo();
/*     */           
/* 883 */           if (SynthTabbedPaneUI.this.tabOverlap != 0) {
/* 884 */             int i = SynthTabbedPaneUI.this.tabPane.getTabCount();
/*     */ 
/*     */             
/* 887 */             boolean bool = SynthTabbedPaneUI.this.tabPane.getComponentOrientation().isLeftToRight();
/* 888 */             for (int j = SynthTabbedPaneUI.this.runCount - 1; j >= 0; j--) {
/* 889 */               int k = SynthTabbedPaneUI.this.tabRuns[j];
/* 890 */               int m = SynthTabbedPaneUI.this.tabRuns[(j == SynthTabbedPaneUI.this.runCount - 1) ? 0 : (j + 1)];
/* 891 */               int n = (m != 0) ? (m - 1) : (i - 1);
/* 892 */               for (int i1 = k + 1; i1 <= n; i1++) {
/*     */ 
/*     */ 
/*     */                 
/* 896 */                 int i2 = 0;
/* 897 */                 int i3 = 0;
/*     */ 
/*     */                 
/* 900 */                 switch (SynthTabbedPaneUI.this.tabPane.getTabPlacement()) {
/*     */                   case 1:
/*     */                   case 3:
/* 903 */                     i2 = bool ? SynthTabbedPaneUI.this.tabOverlap : -SynthTabbedPaneUI.this.tabOverlap;
/*     */                     break;
/*     */                   case 2:
/*     */                   case 4:
/* 907 */                     i3 = SynthTabbedPaneUI.this.tabOverlap;
/*     */                     break;
/*     */                 } 
/*     */                 
/* 911 */                 (SynthTabbedPaneUI.this.rects[i1]).x += i2;
/* 912 */                 (SynthTabbedPaneUI.this.rects[i1]).y += i3;
/* 913 */                 (SynthTabbedPaneUI.this.rects[i1]).width += Math.abs(i2);
/* 914 */                 (SynthTabbedPaneUI.this.rects[i1]).height += Math.abs(i3);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private class SynthScrollableTabButton
/*     */     extends SynthArrowButton
/*     */     implements UIResource {
/*     */     public SynthScrollableTabButton(int param1Int) {
/* 926 */       super(param1Int);
/* 927 */       setName("TabbedPane.button");
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthTabbedPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */