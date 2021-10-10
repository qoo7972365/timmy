/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyVetoException;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
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
/*     */ class SynthInternalFrameTitlePane
/*     */   extends BasicInternalFrameTitlePane
/*     */   implements SynthUI, PropertyChangeListener
/*     */ {
/*     */   protected JPopupMenu systemPopupMenu;
/*     */   protected JButton menuButton;
/*     */   private SynthStyle style;
/*     */   private int titleSpacing;
/*     */   private int buttonSpacing;
/*     */   private int titleAlignment;
/*     */   
/*     */   public SynthInternalFrameTitlePane(JInternalFrame paramJInternalFrame) {
/*  58 */     super(paramJInternalFrame);
/*     */   }
/*     */   
/*     */   public String getUIClassID() {
/*  62 */     return "InternalFrameTitlePaneUI";
/*     */   }
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/*  66 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent, int paramInt) {
/*  70 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private Region getRegion(JComponent paramJComponent) {
/*  74 */     return SynthLookAndFeel.getRegion(paramJComponent);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/*  78 */     if (this.frame != null && 
/*  79 */       this.frame.isSelected()) {
/*  80 */       return 512;
/*     */     }
/*     */     
/*  83 */     return SynthLookAndFeel.getComponentState(paramJComponent);
/*     */   }
/*     */   
/*     */   protected void addSubComponents() {
/*  87 */     this.menuButton.setName("InternalFrameTitlePane.menuButton");
/*  88 */     this.iconButton.setName("InternalFrameTitlePane.iconifyButton");
/*  89 */     this.maxButton.setName("InternalFrameTitlePane.maximizeButton");
/*  90 */     this.closeButton.setName("InternalFrameTitlePane.closeButton");
/*     */     
/*  92 */     add(this.menuButton);
/*  93 */     add(this.iconButton);
/*  94 */     add(this.maxButton);
/*  95 */     add(this.closeButton);
/*     */   }
/*     */   
/*     */   protected void installListeners() {
/*  99 */     super.installListeners();
/* 100 */     this.frame.addPropertyChangeListener(this);
/* 101 */     addPropertyChangeListener(this);
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/* 105 */     this.frame.removePropertyChangeListener(this);
/* 106 */     removePropertyChangeListener(this);
/* 107 */     super.uninstallListeners();
/*     */   }
/*     */   
/*     */   private void updateStyle(JComponent paramJComponent) {
/* 111 */     SynthContext synthContext = getContext(this, 1);
/* 112 */     SynthStyle synthStyle = this.style;
/* 113 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/* 114 */     if (this.style != synthStyle) {
/* 115 */       this
/* 116 */         .maxIcon = this.style.getIcon(synthContext, "InternalFrameTitlePane.maximizeIcon");
/* 117 */       this
/* 118 */         .minIcon = this.style.getIcon(synthContext, "InternalFrameTitlePane.minimizeIcon");
/* 119 */       this
/* 120 */         .iconIcon = this.style.getIcon(synthContext, "InternalFrameTitlePane.iconifyIcon");
/* 121 */       this
/* 122 */         .closeIcon = this.style.getIcon(synthContext, "InternalFrameTitlePane.closeIcon");
/* 123 */       this.titleSpacing = this.style.getInt(synthContext, "InternalFrameTitlePane.titleSpacing", 2);
/*     */       
/* 125 */       this.buttonSpacing = this.style.getInt(synthContext, "InternalFrameTitlePane.buttonSpacing", 2);
/*     */       
/* 127 */       String str = (String)this.style.get(synthContext, "InternalFrameTitlePane.titleAlignment");
/*     */       
/* 129 */       this.titleAlignment = 10;
/* 130 */       if (str != null) {
/* 131 */         str = str.toUpperCase();
/* 132 */         if (str.equals("TRAILING")) {
/* 133 */           this.titleAlignment = 11;
/*     */         }
/* 135 */         else if (str.equals("CENTER")) {
/* 136 */           this.titleAlignment = 0;
/*     */         } 
/*     */       } 
/*     */     } 
/* 140 */     synthContext.dispose();
/*     */   }
/*     */   
/*     */   protected void installDefaults() {
/* 144 */     super.installDefaults();
/* 145 */     updateStyle(this);
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults() {
/* 149 */     SynthContext synthContext = getContext(this, 1);
/* 150 */     this.style.uninstallDefaults(synthContext);
/* 151 */     synthContext.dispose();
/* 152 */     this.style = null;
/* 153 */     JInternalFrame.JDesktopIcon jDesktopIcon = this.frame.getDesktopIcon();
/* 154 */     if (jDesktopIcon != null && jDesktopIcon.getComponentPopupMenu() == this.systemPopupMenu)
/*     */     {
/* 156 */       jDesktopIcon.setComponentPopupMenu((JPopupMenu)null);
/*     */     }
/* 158 */     super.uninstallDefaults();
/*     */   }
/*     */   
/*     */   private static class JPopupMenuUIResource extends JPopupMenu implements UIResource {
/*     */     private JPopupMenuUIResource() {} }
/*     */   
/*     */   protected void assembleSystemMenu() {
/* 165 */     this.systemPopupMenu = new JPopupMenuUIResource();
/* 166 */     addSystemMenuItems(this.systemPopupMenu);
/* 167 */     enableActions();
/* 168 */     this.menuButton = createNoFocusButton();
/* 169 */     updateMenuIcon();
/* 170 */     this.menuButton.addMouseListener(new MouseAdapter() {
/*     */           public void mousePressed(MouseEvent param1MouseEvent) {
/*     */             try {
/* 173 */               SynthInternalFrameTitlePane.this.frame.setSelected(true);
/* 174 */             } catch (PropertyVetoException propertyVetoException) {}
/*     */             
/* 176 */             SynthInternalFrameTitlePane.this.showSystemMenu();
/*     */           }
/*     */         });
/* 179 */     JPopupMenu jPopupMenu = this.frame.getComponentPopupMenu();
/* 180 */     if (jPopupMenu == null || jPopupMenu instanceof UIResource) {
/* 181 */       this.frame.setComponentPopupMenu(this.systemPopupMenu);
/*     */     }
/* 183 */     if (this.frame.getDesktopIcon() != null) {
/* 184 */       jPopupMenu = this.frame.getDesktopIcon().getComponentPopupMenu();
/* 185 */       if (jPopupMenu == null || jPopupMenu instanceof UIResource) {
/* 186 */         this.frame.getDesktopIcon().setComponentPopupMenu(this.systemPopupMenu);
/*     */       }
/*     */     } 
/* 189 */     setInheritsPopupMenu(true);
/*     */   }
/*     */   
/*     */   protected void addSystemMenuItems(JPopupMenu paramJPopupMenu) {
/* 193 */     JMenuItem jMenuItem = paramJPopupMenu.add(this.restoreAction);
/* 194 */     jMenuItem.setMnemonic(getButtonMnemonic("restore"));
/* 195 */     jMenuItem = paramJPopupMenu.add(this.moveAction);
/* 196 */     jMenuItem.setMnemonic(getButtonMnemonic("move"));
/* 197 */     jMenuItem = paramJPopupMenu.add(this.sizeAction);
/* 198 */     jMenuItem.setMnemonic(getButtonMnemonic("size"));
/* 199 */     jMenuItem = paramJPopupMenu.add(this.iconifyAction);
/* 200 */     jMenuItem.setMnemonic(getButtonMnemonic("minimize"));
/* 201 */     jMenuItem = paramJPopupMenu.add(this.maximizeAction);
/* 202 */     jMenuItem.setMnemonic(getButtonMnemonic("maximize"));
/* 203 */     paramJPopupMenu.add(new JSeparator());
/* 204 */     jMenuItem = paramJPopupMenu.add(this.closeAction);
/* 205 */     jMenuItem.setMnemonic(getButtonMnemonic("close"));
/*     */   }
/*     */   
/*     */   private static int getButtonMnemonic(String paramString) {
/*     */     try {
/* 210 */       return Integer.parseInt(UIManager.getString("InternalFrameTitlePane." + paramString + "Button.mnemonic"));
/*     */     }
/* 212 */     catch (NumberFormatException numberFormatException) {
/* 213 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void showSystemMenu() {
/* 218 */     Insets insets = this.frame.getInsets();
/* 219 */     if (!this.frame.isIcon()) {
/* 220 */       this.systemPopupMenu.show(this.frame, this.menuButton.getX(), getY() + getHeight());
/*     */     } else {
/* 222 */       this.systemPopupMenu.show(this.menuButton, 
/* 223 */           getX() - insets.left - insets.right, 
/* 224 */           getY() - (this.systemPopupMenu.getPreferredSize()).height - insets.bottom - insets.top);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintComponent(Graphics paramGraphics) {
/* 231 */     SynthContext synthContext = getContext(this);
/* 232 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 233 */     synthContext.getPainter().paintInternalFrameTitlePaneBackground(synthContext, paramGraphics, 0, 0, 
/* 234 */         getWidth(), getHeight());
/* 235 */     paint(synthContext, paramGraphics);
/* 236 */     synthContext.dispose();
/*     */   }
/*     */   
/*     */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {
/* 240 */     String str = this.frame.getTitle();
/*     */     
/* 242 */     if (str != null) {
/* 243 */       int j, k; SynthStyle synthStyle = paramSynthContext.getStyle();
/*     */       
/* 245 */       paramGraphics.setColor(synthStyle.getColor(paramSynthContext, ColorType.TEXT_FOREGROUND));
/* 246 */       paramGraphics.setFont(synthStyle.getFont(paramSynthContext));
/*     */ 
/*     */       
/* 249 */       FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(this.frame, paramGraphics);
/*     */       
/* 251 */       int i = (getHeight() + fontMetrics.getAscent() - fontMetrics.getLeading() - fontMetrics.getDescent()) / 2;
/* 252 */       JButton jButton = null;
/* 253 */       if (this.frame.isIconifiable()) {
/* 254 */         jButton = this.iconButton;
/*     */       }
/* 256 */       else if (this.frame.isMaximizable()) {
/* 257 */         jButton = this.maxButton;
/*     */       }
/* 259 */       else if (this.frame.isClosable()) {
/* 260 */         jButton = this.closeButton;
/*     */       } 
/*     */ 
/*     */       
/* 264 */       boolean bool = SynthLookAndFeel.isLeftToRight(this.frame);
/* 265 */       int m = this.titleAlignment;
/* 266 */       if (bool) {
/* 267 */         if (jButton != null) {
/* 268 */           j = jButton.getX() - this.titleSpacing;
/*     */         } else {
/*     */           
/* 271 */           j = this.frame.getWidth() - (this.frame.getInsets()).right - this.titleSpacing;
/*     */         } 
/*     */         
/* 274 */         k = this.menuButton.getX() + this.menuButton.getWidth() + this.titleSpacing;
/*     */       }
/*     */       else {
/*     */         
/* 278 */         if (jButton != null) {
/* 279 */           k = jButton.getX() + jButton.getWidth() + this.titleSpacing;
/*     */         }
/*     */         else {
/*     */           
/* 283 */           k = (this.frame.getInsets()).left + this.titleSpacing;
/*     */         } 
/* 285 */         j = this.menuButton.getX() - this.titleSpacing;
/* 286 */         if (m == 10) {
/* 287 */           m = 11;
/*     */         }
/* 289 */         else if (m == 11) {
/* 290 */           m = 10;
/*     */         } 
/*     */       } 
/* 293 */       String str1 = getTitle(str, fontMetrics, j - k);
/* 294 */       if (str1 == str)
/*     */       {
/* 296 */         if (m == 11) {
/*     */           
/* 298 */           k = j - synthStyle.getGraphicsUtils(paramSynthContext).computeStringWidth(paramSynthContext, paramGraphics.getFont(), fontMetrics, str);
/*     */         }
/* 300 */         else if (m == 0) {
/*     */           
/* 302 */           int n = synthStyle.getGraphicsUtils(paramSynthContext).computeStringWidth(paramSynthContext, paramGraphics.getFont(), fontMetrics, str);
/* 303 */           k = Math.max(k, (getWidth() - n) / 2);
/* 304 */           k = Math.min(j - n, k);
/*     */         } 
/*     */       }
/* 307 */       synthStyle.getGraphicsUtils(paramSynthContext).paintText(paramSynthContext, paramGraphics, str1, k, i - fontMetrics
/* 308 */           .getAscent(), -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 314 */     paramSynthContext.getPainter().paintInternalFrameTitlePaneBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected LayoutManager createLayout() {
/* 319 */     SynthContext synthContext = getContext(this);
/*     */     
/* 321 */     LayoutManager layoutManager = (LayoutManager)this.style.get(synthContext, "InternalFrameTitlePane.titlePaneLayout");
/* 322 */     synthContext.dispose();
/* 323 */     return (layoutManager != null) ? layoutManager : new SynthTitlePaneLayout();
/*     */   }
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 327 */     if (paramPropertyChangeEvent.getSource() == this) {
/* 328 */       if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/* 329 */         updateStyle(this);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 334 */     else if (paramPropertyChangeEvent.getPropertyName() == "frameIcon") {
/* 335 */       updateMenuIcon();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateMenuIcon() {
/* 344 */     Icon icon = this.frame.getFrameIcon();
/* 345 */     SynthContext synthContext = getContext(this);
/* 346 */     if (icon != null) {
/* 347 */       Dimension dimension = (Dimension)synthContext.getStyle().get(synthContext, "InternalFrameTitlePane.maxFrameIconSize");
/*     */       
/* 349 */       int i = 16;
/* 350 */       int j = 16;
/* 351 */       if (dimension != null) {
/* 352 */         i = dimension.width;
/* 353 */         j = dimension.height;
/*     */       } 
/* 355 */       if ((icon.getIconWidth() > i || icon
/* 356 */         .getIconHeight() > j) && icon instanceof ImageIcon)
/*     */       {
/*     */         
/* 359 */         icon = new ImageIcon(((ImageIcon)icon).getImage().getScaledInstance(i, j, 4));
/*     */       }
/*     */     } 
/*     */     
/* 363 */     synthContext.dispose();
/* 364 */     this.menuButton.setIcon(icon);
/*     */   }
/*     */   
/*     */   class SynthTitlePaneLayout implements LayoutManager { public void addLayoutComponent(String param1String, Component param1Component) {}
/*     */     
/*     */     public void removeLayoutComponent(Component param1Component) {}
/*     */     
/*     */     public Dimension preferredLayoutSize(Container param1Container) {
/* 372 */       return minimumLayoutSize(param1Container);
/*     */     }
/*     */     
/*     */     public Dimension minimumLayoutSize(Container param1Container) {
/* 376 */       SynthContext synthContext = SynthInternalFrameTitlePane.this.getContext(SynthInternalFrameTitlePane.this);
/*     */       
/* 378 */       int i = 0;
/* 379 */       int j = 0;
/*     */       
/* 381 */       byte b1 = 0;
/*     */ 
/*     */       
/* 384 */       if (SynthInternalFrameTitlePane.this.frame.isClosable()) {
/* 385 */         Dimension dimension1 = SynthInternalFrameTitlePane.this.closeButton.getPreferredSize();
/* 386 */         i += dimension1.width;
/* 387 */         j = Math.max(dimension1.height, j);
/* 388 */         b1++;
/*     */       } 
/* 390 */       if (SynthInternalFrameTitlePane.this.frame.isMaximizable()) {
/* 391 */         Dimension dimension1 = SynthInternalFrameTitlePane.this.maxButton.getPreferredSize();
/* 392 */         i += dimension1.width;
/* 393 */         j = Math.max(dimension1.height, j);
/* 394 */         b1++;
/*     */       } 
/* 396 */       if (SynthInternalFrameTitlePane.this.frame.isIconifiable()) {
/* 397 */         Dimension dimension1 = SynthInternalFrameTitlePane.this.iconButton.getPreferredSize();
/* 398 */         i += dimension1.width;
/* 399 */         j = Math.max(dimension1.height, j);
/* 400 */         b1++;
/*     */       } 
/* 402 */       Dimension dimension = SynthInternalFrameTitlePane.this.menuButton.getPreferredSize();
/* 403 */       i += dimension.width;
/* 404 */       j = Math.max(dimension.height, j);
/*     */       
/* 406 */       i += Math.max(0, (b1 - 1) * SynthInternalFrameTitlePane.this.buttonSpacing);
/*     */       
/* 408 */       FontMetrics fontMetrics = SynthInternalFrameTitlePane.this.getFontMetrics(SynthInternalFrameTitlePane.this
/* 409 */           .getFont());
/*     */       
/* 411 */       SynthGraphicsUtils synthGraphicsUtils = synthContext.getStyle().getGraphicsUtils(synthContext);
/* 412 */       String str = SynthInternalFrameTitlePane.this.frame.getTitle();
/*     */       
/* 414 */       byte b2 = (str != null) ? synthGraphicsUtils.computeStringWidth(synthContext, fontMetrics.getFont(), fontMetrics, str) : 0;
/*     */       
/* 416 */       byte b3 = (str != null) ? str.length() : 0;
/*     */ 
/*     */       
/* 419 */       if (b3 > 3) {
/* 420 */         int k = synthGraphicsUtils.computeStringWidth(synthContext, fontMetrics
/* 421 */             .getFont(), fontMetrics, str.substring(0, 3) + "...");
/* 422 */         i += (b2 < k) ? b2 : k;
/*     */       } else {
/* 424 */         i += b2;
/*     */       } 
/*     */       
/* 427 */       j = Math.max(fontMetrics.getHeight() + 2, j);
/*     */       
/* 429 */       i += SynthInternalFrameTitlePane.this.titleSpacing + SynthInternalFrameTitlePane.this.titleSpacing;
/*     */       
/* 431 */       Insets insets = SynthInternalFrameTitlePane.this.getInsets();
/* 432 */       j += insets.top + insets.bottom;
/* 433 */       i += insets.left + insets.right;
/* 434 */       synthContext.dispose();
/* 435 */       return new Dimension(i, j);
/*     */     }
/*     */ 
/*     */     
/*     */     private int center(Component param1Component, Insets param1Insets, int param1Int, boolean param1Boolean) {
/* 440 */       Dimension dimension = param1Component.getPreferredSize();
/* 441 */       if (param1Boolean) {
/* 442 */         param1Int -= dimension.width;
/*     */       }
/* 444 */       param1Component.setBounds(param1Int, param1Insets.top + (SynthInternalFrameTitlePane.this
/* 445 */           .getHeight() - param1Insets.top - param1Insets.bottom - dimension.height) / 2, dimension.width, dimension.height);
/*     */       
/* 447 */       if (dimension.width > 0) {
/* 448 */         if (param1Boolean) {
/* 449 */           return param1Int - SynthInternalFrameTitlePane.this.buttonSpacing;
/*     */         }
/* 451 */         return param1Int + dimension.width + SynthInternalFrameTitlePane.this.buttonSpacing;
/*     */       } 
/* 453 */       return param1Int;
/*     */     }
/*     */     
/*     */     public void layoutContainer(Container param1Container) {
/* 457 */       Insets insets = param1Container.getInsets();
/*     */ 
/*     */       
/* 460 */       if (SynthLookAndFeel.isLeftToRight(SynthInternalFrameTitlePane.this.frame)) {
/* 461 */         center(SynthInternalFrameTitlePane.this.menuButton, insets, insets.left, false);
/* 462 */         int i = SynthInternalFrameTitlePane.this.getWidth() - insets.right;
/* 463 */         if (SynthInternalFrameTitlePane.this.frame.isClosable()) {
/* 464 */           i = center(SynthInternalFrameTitlePane.this.closeButton, insets, i, true);
/*     */         }
/* 466 */         if (SynthInternalFrameTitlePane.this.frame.isMaximizable()) {
/* 467 */           i = center(SynthInternalFrameTitlePane.this.maxButton, insets, i, true);
/*     */         }
/* 469 */         if (SynthInternalFrameTitlePane.this.frame.isIconifiable()) {
/* 470 */           i = center(SynthInternalFrameTitlePane.this.iconButton, insets, i, true);
/*     */         }
/*     */       } else {
/*     */         
/* 474 */         center(SynthInternalFrameTitlePane.this.menuButton, insets, SynthInternalFrameTitlePane.this.getWidth() - insets.right, true);
/*     */         
/* 476 */         int i = insets.left;
/* 477 */         if (SynthInternalFrameTitlePane.this.frame.isClosable()) {
/* 478 */           i = center(SynthInternalFrameTitlePane.this.closeButton, insets, i, false);
/*     */         }
/* 480 */         if (SynthInternalFrameTitlePane.this.frame.isMaximizable()) {
/* 481 */           i = center(SynthInternalFrameTitlePane.this.maxButton, insets, i, false);
/*     */         }
/* 483 */         if (SynthInternalFrameTitlePane.this.frame.isIconifiable()) {
/* 484 */           i = center(SynthInternalFrameTitlePane.this.iconButton, insets, i, false);
/*     */         }
/*     */       } 
/*     */     } }
/*     */ 
/*     */   
/*     */   private JButton createNoFocusButton() {
/* 491 */     JButton jButton = new JButton();
/* 492 */     jButton.setFocusable(false);
/* 493 */     jButton.setMargin(new Insets(0, 0, 0, 0));
/* 494 */     return jButton;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthInternalFrameTitlePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */