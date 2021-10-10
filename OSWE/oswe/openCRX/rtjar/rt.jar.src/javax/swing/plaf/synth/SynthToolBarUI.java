/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicToolBarUI;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthToolBarUI
/*     */   extends BasicToolBarUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*  54 */   private Icon handleIcon = null;
/*  55 */   private Rectangle contentRect = new Rectangle();
/*     */ 
/*     */   
/*     */   private SynthStyle style;
/*     */ 
/*     */   
/*     */   private SynthStyle contentStyle;
/*     */ 
/*     */   
/*     */   private SynthStyle dragWindowStyle;
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  68 */     return new SynthToolBarUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  76 */     this.toolBar.setLayout(createLayout());
/*  77 */     updateStyle(this.toolBar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/*  85 */     super.installListeners();
/*  86 */     this.toolBar.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/*  94 */     super.uninstallListeners();
/*  95 */     this.toolBar.removePropertyChangeListener(this);
/*     */   }
/*     */   
/*     */   private void updateStyle(JToolBar paramJToolBar) {
/*  99 */     SynthContext synthContext = getContext(paramJToolBar, Region.TOOL_BAR_CONTENT, (SynthStyle)null, 1);
/*     */     
/* 101 */     this.contentStyle = SynthLookAndFeel.updateStyle(synthContext, this);
/* 102 */     synthContext.dispose();
/*     */     
/* 104 */     synthContext = getContext(paramJToolBar, Region.TOOL_BAR_DRAG_WINDOW, (SynthStyle)null, 1);
/* 105 */     this.dragWindowStyle = SynthLookAndFeel.updateStyle(synthContext, this);
/* 106 */     synthContext.dispose();
/*     */     
/* 108 */     synthContext = getContext(paramJToolBar, 1);
/* 109 */     SynthStyle synthStyle = this.style;
/*     */     
/* 111 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/* 112 */     if (synthStyle != this.style) {
/* 113 */       this
/* 114 */         .handleIcon = this.style.getIcon(synthContext, "ToolBar.handleIcon");
/* 115 */       if (synthStyle != null) {
/* 116 */         uninstallKeyboardActions();
/* 117 */         installKeyboardActions();
/*     */       } 
/*     */     } 
/* 120 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 128 */     SynthContext synthContext = getContext(this.toolBar, 1);
/*     */     
/* 130 */     this.style.uninstallDefaults(synthContext);
/* 131 */     synthContext.dispose();
/* 132 */     this.style = null;
/*     */     
/* 134 */     this.handleIcon = null;
/*     */     
/* 136 */     synthContext = getContext(this.toolBar, Region.TOOL_BAR_CONTENT, this.contentStyle, 1);
/*     */     
/* 138 */     this.contentStyle.uninstallDefaults(synthContext);
/* 139 */     synthContext.dispose();
/* 140 */     this.contentStyle = null;
/*     */     
/* 142 */     synthContext = getContext(this.toolBar, Region.TOOL_BAR_DRAG_WINDOW, this.dragWindowStyle, 1);
/*     */     
/* 144 */     this.dragWindowStyle.uninstallDefaults(synthContext);
/* 145 */     synthContext.dispose();
/* 146 */     this.dragWindowStyle = null;
/*     */     
/* 148 */     this.toolBar.setLayout((LayoutManager)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installComponents() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallComponents() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LayoutManager createLayout() {
/* 169 */     return new SynthToolBarLayoutManager();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 177 */     return getContext(paramJComponent, SynthLookAndFeel.getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 181 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, Region paramRegion, SynthStyle paramSynthStyle) {
/* 185 */     return SynthContext.getContext(paramJComponent, paramRegion, paramSynthStyle, 
/* 186 */         getComponentState(paramJComponent, paramRegion));
/*     */   }
/*     */ 
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, Region paramRegion, SynthStyle paramSynthStyle, int paramInt) {
/* 191 */     return SynthContext.getContext(paramJComponent, paramRegion, paramSynthStyle, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent, Region paramRegion) {
/* 195 */     return SynthLookAndFeel.getComponentState(paramJComponent);
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
/* 215 */     synthContext.getPainter().paintToolBarBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 216 */         .getWidth(), paramJComponent.getHeight(), this.toolBar
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
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 245 */     paramSynthContext.getPainter().paintToolBarBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, this.toolBar
/* 246 */         .getOrientation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setBorderToNonRollover(Component paramComponent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setBorderToRollover(Component paramComponent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setBorderToNormal(Component paramComponent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {
/* 281 */     if (this.handleIcon != null && this.toolBar.isFloatable()) {
/*     */ 
/*     */       
/* 284 */       boolean bool = this.toolBar.getComponentOrientation().isLeftToRight() ? false : (this.toolBar.getWidth() - SynthIcon.getIconWidth(this.handleIcon, paramSynthContext));
/* 285 */       SynthIcon.paintIcon(this.handleIcon, paramSynthContext, paramGraphics, bool, 0, 
/* 286 */           SynthIcon.getIconWidth(this.handleIcon, paramSynthContext), 
/* 287 */           SynthIcon.getIconHeight(this.handleIcon, paramSynthContext));
/*     */     } 
/*     */     
/* 290 */     SynthContext synthContext = getContext(this.toolBar, Region.TOOL_BAR_CONTENT, this.contentStyle);
/*     */     
/* 292 */     paintContent(synthContext, paramGraphics, this.contentRect);
/* 293 */     synthContext.dispose();
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
/*     */   protected void paintContent(SynthContext paramSynthContext, Graphics paramGraphics, Rectangle paramRectangle) {
/* 305 */     SynthLookAndFeel.updateSubregion(paramSynthContext, paramGraphics, paramRectangle);
/* 306 */     paramSynthContext.getPainter().paintToolBarContentBackground(paramSynthContext, paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, this.toolBar
/*     */         
/* 308 */         .getOrientation());
/* 309 */     paramSynthContext.getPainter().paintToolBarContentBorder(paramSynthContext, paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, this.toolBar
/*     */         
/* 311 */         .getOrientation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintDragWindow(Graphics paramGraphics) {
/* 319 */     int i = this.dragWindow.getWidth();
/* 320 */     int j = this.dragWindow.getHeight();
/* 321 */     SynthContext synthContext = getContext(this.toolBar, Region.TOOL_BAR_DRAG_WINDOW, this.dragWindowStyle);
/*     */     
/* 323 */     SynthLookAndFeel.updateSubregion(synthContext, paramGraphics, new Rectangle(0, 0, i, j));
/*     */     
/* 325 */     synthContext.getPainter().paintToolBarDragWindowBackground(synthContext, paramGraphics, 0, 0, i, j, this.dragWindow
/*     */         
/* 327 */         .getOrientation());
/* 328 */     synthContext.getPainter().paintToolBarDragWindowBorder(synthContext, paramGraphics, 0, 0, i, j, this.dragWindow
/* 329 */         .getOrientation());
/* 330 */     synthContext.dispose();
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
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 342 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent))
/* 343 */       updateStyle((JToolBar)paramPropertyChangeEvent.getSource()); 
/*     */   }
/*     */   
/*     */   class SynthToolBarLayoutManager
/*     */     implements LayoutManager
/*     */   {
/*     */     public void addLayoutComponent(String param1String, Component param1Component) {}
/*     */     
/*     */     public void removeLayoutComponent(Component param1Component) {}
/*     */     
/*     */     public Dimension minimumLayoutSize(Container param1Container) {
/* 354 */       JToolBar jToolBar = (JToolBar)param1Container;
/* 355 */       Insets insets = jToolBar.getInsets();
/* 356 */       Dimension dimension = new Dimension();
/* 357 */       SynthContext synthContext = SynthToolBarUI.this.getContext(jToolBar);
/*     */       
/* 359 */       if (jToolBar.getOrientation() == 0) {
/* 360 */         dimension
/* 361 */           .width = jToolBar.isFloatable() ? SynthIcon.getIconWidth(SynthToolBarUI.this.handleIcon, synthContext) : 0;
/*     */         
/* 363 */         for (byte b = 0; b < jToolBar.getComponentCount(); b++) {
/* 364 */           Component component = jToolBar.getComponent(b);
/* 365 */           if (component.isVisible()) {
/* 366 */             Dimension dimension1 = component.getMinimumSize();
/* 367 */             dimension.width += dimension1.width;
/* 368 */             dimension.height = Math.max(dimension.height, dimension1.height);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 372 */         dimension
/* 373 */           .height = jToolBar.isFloatable() ? SynthIcon.getIconHeight(SynthToolBarUI.this.handleIcon, synthContext) : 0;
/*     */         
/* 375 */         for (byte b = 0; b < jToolBar.getComponentCount(); b++) {
/* 376 */           Component component = jToolBar.getComponent(b);
/* 377 */           if (component.isVisible()) {
/* 378 */             Dimension dimension1 = component.getMinimumSize();
/* 379 */             dimension.width = Math.max(dimension.width, dimension1.width);
/* 380 */             dimension.height += dimension1.height;
/*     */           } 
/*     */         } 
/*     */       } 
/* 384 */       dimension.width += insets.left + insets.right;
/* 385 */       dimension.height += insets.top + insets.bottom;
/*     */       
/* 387 */       synthContext.dispose();
/* 388 */       return dimension;
/*     */     }
/*     */     
/*     */     public Dimension preferredLayoutSize(Container param1Container) {
/* 392 */       JToolBar jToolBar = (JToolBar)param1Container;
/* 393 */       Insets insets = jToolBar.getInsets();
/* 394 */       Dimension dimension = new Dimension();
/* 395 */       SynthContext synthContext = SynthToolBarUI.this.getContext(jToolBar);
/*     */       
/* 397 */       if (jToolBar.getOrientation() == 0) {
/* 398 */         dimension
/* 399 */           .width = jToolBar.isFloatable() ? SynthIcon.getIconWidth(SynthToolBarUI.this.handleIcon, synthContext) : 0;
/*     */         
/* 401 */         for (byte b = 0; b < jToolBar.getComponentCount(); b++) {
/* 402 */           Component component = jToolBar.getComponent(b);
/* 403 */           if (component.isVisible()) {
/* 404 */             Dimension dimension1 = component.getPreferredSize();
/* 405 */             dimension.width += dimension1.width;
/* 406 */             dimension.height = Math.max(dimension.height, dimension1.height);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 410 */         dimension
/* 411 */           .height = jToolBar.isFloatable() ? SynthIcon.getIconHeight(SynthToolBarUI.this.handleIcon, synthContext) : 0;
/*     */         
/* 413 */         for (byte b = 0; b < jToolBar.getComponentCount(); b++) {
/* 414 */           Component component = jToolBar.getComponent(b);
/* 415 */           if (component.isVisible()) {
/* 416 */             Dimension dimension1 = component.getPreferredSize();
/* 417 */             dimension.width = Math.max(dimension.width, dimension1.width);
/* 418 */             dimension.height += dimension1.height;
/*     */           } 
/*     */         } 
/*     */       } 
/* 422 */       dimension.width += insets.left + insets.right;
/* 423 */       dimension.height += insets.top + insets.bottom;
/*     */       
/* 425 */       synthContext.dispose();
/* 426 */       return dimension;
/*     */     }
/*     */     
/*     */     public void layoutContainer(Container param1Container) {
/* 430 */       JToolBar jToolBar = (JToolBar)param1Container;
/* 431 */       Insets insets = jToolBar.getInsets();
/* 432 */       boolean bool = jToolBar.getComponentOrientation().isLeftToRight();
/* 433 */       SynthContext synthContext = SynthToolBarUI.this.getContext(jToolBar);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 445 */       byte b1 = 0; byte b2;
/* 446 */       for (b2 = 0; b2 < jToolBar.getComponentCount(); b2++) {
/* 447 */         if (isGlue(jToolBar.getComponent(b2))) b1++;
/*     */       
/*     */       } 
/* 450 */       if (jToolBar.getOrientation() == 0) {
/*     */         
/* 452 */         b2 = jToolBar.isFloatable() ? SynthIcon.getIconWidth(SynthToolBarUI.this.handleIcon, synthContext) : 0;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 457 */         SynthToolBarUI.this.contentRect.x = bool ? b2 : 0;
/* 458 */         SynthToolBarUI.this.contentRect.y = 0;
/* 459 */         SynthToolBarUI.this.contentRect.width = jToolBar.getWidth() - b2;
/* 460 */         SynthToolBarUI.this.contentRect.height = jToolBar.getHeight();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 466 */         int i = bool ? (b2 + insets.left) : (jToolBar.getWidth() - b2 - insets.right);
/* 467 */         int j = insets.top;
/* 468 */         int k = jToolBar.getHeight() - insets.top - insets.bottom;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 473 */         int m = 0;
/* 474 */         if (b1 > 0) {
/* 475 */           int n = (minimumLayoutSize(param1Container)).width;
/* 476 */           m = (jToolBar.getWidth() - n) / b1;
/* 477 */           if (m < 0) m = 0;
/*     */         
/*     */         } 
/* 480 */         for (byte b = 0; b < jToolBar.getComponentCount(); b++) {
/* 481 */           Component component = jToolBar.getComponent(b);
/* 482 */           if (component.isVisible()) {
/* 483 */             int n, i1; Dimension dimension = component.getPreferredSize();
/*     */             
/* 485 */             if (dimension.height >= k || component instanceof javax.swing.JSeparator) {
/*     */               
/* 487 */               n = j;
/* 488 */               i1 = k;
/*     */             } else {
/*     */               
/* 491 */               n = j + k / 2 - dimension.height / 2;
/* 492 */               i1 = dimension.height;
/*     */             } 
/*     */ 
/*     */             
/* 496 */             if (isGlue(component)) dimension.width += m; 
/* 497 */             component.setBounds(bool ? i : (i - dimension.width), n, dimension.width, i1);
/* 498 */             i = bool ? (i + dimension.width) : (i - dimension.width);
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 503 */         b2 = jToolBar.isFloatable() ? SynthIcon.getIconHeight(SynthToolBarUI.this.handleIcon, synthContext) : 0;
/*     */ 
/*     */         
/* 506 */         SynthToolBarUI.this.contentRect.x = 0;
/* 507 */         SynthToolBarUI.this.contentRect.y = b2;
/* 508 */         SynthToolBarUI.this.contentRect.width = jToolBar.getWidth();
/* 509 */         SynthToolBarUI.this.contentRect.height = jToolBar.getHeight() - b2;
/*     */         
/* 511 */         int i = insets.left;
/* 512 */         int j = jToolBar.getWidth() - insets.left - insets.right;
/* 513 */         int k = b2 + insets.top;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 518 */         int m = 0;
/* 519 */         if (b1 > 0) {
/* 520 */           int n = (minimumLayoutSize(param1Container)).height;
/* 521 */           m = (jToolBar.getHeight() - n) / b1;
/* 522 */           if (m < 0) m = 0;
/*     */         
/*     */         } 
/* 525 */         for (byte b = 0; b < jToolBar.getComponentCount(); b++) {
/* 526 */           Component component = jToolBar.getComponent(b);
/* 527 */           if (component.isVisible()) {
/* 528 */             int n, i1; Dimension dimension = component.getPreferredSize();
/*     */             
/* 530 */             if (dimension.width >= j || component instanceof javax.swing.JSeparator) {
/*     */               
/* 532 */               n = i;
/* 533 */               i1 = j;
/*     */             } else {
/*     */               
/* 536 */               n = i + j / 2 - dimension.width / 2;
/* 537 */               i1 = dimension.width;
/*     */             } 
/*     */ 
/*     */             
/* 541 */             if (isGlue(component)) dimension.height += m; 
/* 542 */             component.setBounds(n, k, i1, dimension.height);
/* 543 */             k += dimension.height;
/*     */           } 
/*     */         } 
/*     */       } 
/* 547 */       synthContext.dispose();
/*     */     }
/*     */     
/*     */     private boolean isGlue(Component param1Component) {
/* 551 */       if (param1Component.isVisible() && param1Component instanceof Box.Filler) {
/* 552 */         Box.Filler filler = (Box.Filler)param1Component;
/* 553 */         Dimension dimension1 = filler.getMinimumSize();
/* 554 */         Dimension dimension2 = filler.getPreferredSize();
/* 555 */         return (dimension1.width == 0 && dimension1.height == 0 && dimension2.width == 0 && dimension2.height == 0);
/*     */       } 
/*     */       
/* 558 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthToolBarUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */