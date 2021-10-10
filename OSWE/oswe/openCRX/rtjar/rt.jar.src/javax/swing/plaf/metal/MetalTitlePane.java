/*      */ package javax.swing.plaf.metal;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dialog;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Frame;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Image;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.event.WindowListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JMenuBar;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JRootPane;
/*      */ import javax.swing.JSeparator;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.border.EmptyBorder;
/*      */ import sun.awt.SunToolkit;
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
/*      */ class MetalTitlePane
/*      */   extends JComponent
/*      */ {
/*   53 */   private static final Border handyEmptyBorder = new EmptyBorder(0, 0, 0, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int IMAGE_HEIGHT = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int IMAGE_WIDTH = 16;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PropertyChangeListener propertyChangeListener;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JMenuBar menuBar;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Action closeAction;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Action iconifyAction;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Action restoreAction;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Action maximizeAction;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JButton toggleButton;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JButton iconifyButton;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JButton closeButton;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Icon maximizeIcon;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Icon minimizeIcon;
/*      */ 
/*      */ 
/*      */   
/*      */   private Image systemIcon;
/*      */ 
/*      */ 
/*      */   
/*      */   private WindowListener windowListener;
/*      */ 
/*      */ 
/*      */   
/*      */   private Window window;
/*      */ 
/*      */ 
/*      */   
/*      */   private JRootPane rootPane;
/*      */ 
/*      */ 
/*      */   
/*      */   private int buttonsWidth;
/*      */ 
/*      */ 
/*      */   
/*      */   private int state;
/*      */ 
/*      */ 
/*      */   
/*      */   private MetalRootPaneUI rootPaneUI;
/*      */ 
/*      */ 
/*      */   
/*  150 */   private Color inactiveBackground = UIManager.getColor("inactiveCaption");
/*  151 */   private Color inactiveForeground = UIManager.getColor("inactiveCaptionText");
/*  152 */   private Color inactiveShadow = UIManager.getColor("inactiveCaptionBorder");
/*  153 */   private Color activeBumpsHighlight = MetalLookAndFeel.getPrimaryControlHighlight();
/*  154 */   private Color activeBumpsShadow = MetalLookAndFeel.getPrimaryControlDarkShadow();
/*  155 */   private Color activeBackground = null;
/*  156 */   private Color activeForeground = null;
/*  157 */   private Color activeShadow = null;
/*      */ 
/*      */   
/*  160 */   private MetalBumps activeBumps = new MetalBumps(0, 0, this.activeBumpsHighlight, this.activeBumpsShadow, 
/*      */ 
/*      */ 
/*      */       
/*  164 */       MetalLookAndFeel.getPrimaryControl());
/*  165 */   private MetalBumps inactiveBumps = new MetalBumps(0, 0, 
/*      */       
/*  167 */       MetalLookAndFeel.getControlHighlight(), 
/*  168 */       MetalLookAndFeel.getControlDarkShadow(), 
/*  169 */       MetalLookAndFeel.getControl());
/*      */ 
/*      */   
/*      */   public MetalTitlePane(JRootPane paramJRootPane, MetalRootPaneUI paramMetalRootPaneUI) {
/*  173 */     this.rootPane = paramJRootPane;
/*  174 */     this.rootPaneUI = paramMetalRootPaneUI;
/*      */     
/*  176 */     this.state = -1;
/*      */     
/*  178 */     installSubcomponents();
/*  179 */     determineColors();
/*  180 */     installDefaults();
/*      */     
/*  182 */     setLayout(createLayout());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void uninstall() {
/*  189 */     uninstallListeners();
/*  190 */     this.window = null;
/*  191 */     removeAll();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void installListeners() {
/*  198 */     if (this.window != null) {
/*  199 */       this.windowListener = createWindowListener();
/*  200 */       this.window.addWindowListener(this.windowListener);
/*  201 */       this.propertyChangeListener = createWindowPropertyChangeListener();
/*  202 */       this.window.addPropertyChangeListener(this.propertyChangeListener);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void uninstallListeners() {
/*  210 */     if (this.window != null) {
/*  211 */       this.window.removeWindowListener(this.windowListener);
/*  212 */       this.window.removePropertyChangeListener(this.propertyChangeListener);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private WindowListener createWindowListener() {
/*  221 */     return new WindowHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PropertyChangeListener createWindowPropertyChangeListener() {
/*  229 */     return new PropertyChangeHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JRootPane getRootPane() {
/*  236 */     return this.rootPane;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getWindowDecorationStyle() {
/*  243 */     return getRootPane().getWindowDecorationStyle();
/*      */   }
/*      */   
/*      */   public void addNotify() {
/*  247 */     super.addNotify();
/*      */     
/*  249 */     uninstallListeners();
/*      */     
/*  251 */     this.window = SwingUtilities.getWindowAncestor(this);
/*  252 */     if (this.window != null) {
/*  253 */       if (this.window instanceof Frame) {
/*  254 */         setState(((Frame)this.window).getExtendedState());
/*      */       } else {
/*      */         
/*  257 */         setState(0);
/*      */       } 
/*  259 */       setActive(this.window.isActive());
/*  260 */       installListeners();
/*  261 */       updateSystemIcon();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void removeNotify() {
/*  266 */     super.removeNotify();
/*      */     
/*  268 */     uninstallListeners();
/*  269 */     this.window = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void installSubcomponents() {
/*  276 */     int i = getWindowDecorationStyle();
/*  277 */     if (i == 1) {
/*  278 */       createActions();
/*  279 */       this.menuBar = createMenuBar();
/*  280 */       add(this.menuBar);
/*  281 */       createButtons();
/*  282 */       add(this.iconifyButton);
/*  283 */       add(this.toggleButton);
/*  284 */       add(this.closeButton);
/*  285 */     } else if (i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  292 */       createActions();
/*  293 */       createButtons();
/*  294 */       add(this.closeButton);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void determineColors() {
/*  302 */     switch (getWindowDecorationStyle()) {
/*      */       case 1:
/*  304 */         this.activeBackground = UIManager.getColor("activeCaption");
/*  305 */         this.activeForeground = UIManager.getColor("activeCaptionText");
/*  306 */         this.activeShadow = UIManager.getColor("activeCaptionBorder");
/*      */         break;
/*      */       case 4:
/*  309 */         this.activeBackground = UIManager.getColor("OptionPane.errorDialog.titlePane.background");
/*      */         
/*  311 */         this.activeForeground = UIManager.getColor("OptionPane.errorDialog.titlePane.foreground");
/*      */         
/*  313 */         this.activeShadow = UIManager.getColor("OptionPane.errorDialog.titlePane.shadow");
/*      */         break;
/*      */       
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*  319 */         this.activeBackground = UIManager.getColor("OptionPane.questionDialog.titlePane.background");
/*      */         
/*  321 */         this.activeForeground = UIManager.getColor("OptionPane.questionDialog.titlePane.foreground");
/*      */         
/*  323 */         this.activeShadow = UIManager.getColor("OptionPane.questionDialog.titlePane.shadow");
/*      */         break;
/*      */       
/*      */       case 8:
/*  327 */         this.activeBackground = UIManager.getColor("OptionPane.warningDialog.titlePane.background");
/*      */         
/*  329 */         this.activeForeground = UIManager.getColor("OptionPane.warningDialog.titlePane.foreground");
/*      */         
/*  331 */         this.activeShadow = UIManager.getColor("OptionPane.warningDialog.titlePane.shadow");
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       default:
/*  337 */         this.activeBackground = UIManager.getColor("activeCaption");
/*  338 */         this.activeForeground = UIManager.getColor("activeCaptionText");
/*  339 */         this.activeShadow = UIManager.getColor("activeCaptionBorder");
/*      */         break;
/*      */     } 
/*  342 */     this.activeBumps.setBumpColors(this.activeBumpsHighlight, this.activeBumpsShadow, this.activeBackground);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void installDefaults() {
/*  350 */     setFont(UIManager.getFont("InternalFrame.titleFont", getLocale()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void uninstallDefaults() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JMenuBar createMenuBar() {
/*  364 */     this.menuBar = new SystemMenuBar();
/*  365 */     this.menuBar.setFocusable(false);
/*  366 */     this.menuBar.setBorderPainted(true);
/*  367 */     this.menuBar.add(createMenu());
/*  368 */     return this.menuBar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void close() {
/*  375 */     Window window = getWindow();
/*      */     
/*  377 */     if (window != null) {
/*  378 */       window.dispatchEvent(new WindowEvent(window, 201));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void iconify() {
/*  387 */     Frame frame = getFrame();
/*  388 */     if (frame != null) {
/*  389 */       frame.setExtendedState(this.state | 0x1);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void maximize() {
/*  397 */     Frame frame = getFrame();
/*  398 */     if (frame != null) {
/*  399 */       frame.setExtendedState(this.state | 0x6);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void restore() {
/*  407 */     Frame frame = getFrame();
/*      */     
/*  409 */     if (frame == null) {
/*      */       return;
/*      */     }
/*      */     
/*  413 */     if ((this.state & 0x1) != 0) {
/*  414 */       frame.setExtendedState(this.state & 0xFFFFFFFE);
/*      */     } else {
/*  416 */       frame.setExtendedState(this.state & 0xFFFFFFF9);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createActions() {
/*  425 */     this.closeAction = new CloseAction();
/*  426 */     if (getWindowDecorationStyle() == 1) {
/*  427 */       this.iconifyAction = new IconifyAction();
/*  428 */       this.restoreAction = new RestoreAction();
/*  429 */       this.maximizeAction = new MaximizeAction();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JMenu createMenu() {
/*  438 */     JMenu jMenu = new JMenu("");
/*  439 */     if (getWindowDecorationStyle() == 1) {
/*  440 */       addMenuItems(jMenu);
/*      */     }
/*  442 */     return jMenu;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMenuItems(JMenu paramJMenu) {
/*  449 */     Locale locale = getRootPane().getLocale();
/*  450 */     JMenuItem jMenuItem = paramJMenu.add(this.restoreAction);
/*  451 */     int i = MetalUtils.getInt("MetalTitlePane.restoreMnemonic", -1);
/*      */     
/*  453 */     if (i != -1) {
/*  454 */       jMenuItem.setMnemonic(i);
/*      */     }
/*      */     
/*  457 */     jMenuItem = paramJMenu.add(this.iconifyAction);
/*  458 */     i = MetalUtils.getInt("MetalTitlePane.iconifyMnemonic", -1);
/*  459 */     if (i != -1) {
/*  460 */       jMenuItem.setMnemonic(i);
/*      */     }
/*      */     
/*  463 */     if (Toolkit.getDefaultToolkit().isFrameStateSupported(6)) {
/*      */       
/*  465 */       jMenuItem = paramJMenu.add(this.maximizeAction);
/*      */       
/*  467 */       i = MetalUtils.getInt("MetalTitlePane.maximizeMnemonic", -1);
/*  468 */       if (i != -1) {
/*  469 */         jMenuItem.setMnemonic(i);
/*      */       }
/*      */     } 
/*      */     
/*  473 */     paramJMenu.add(new JSeparator());
/*      */     
/*  475 */     jMenuItem = paramJMenu.add(this.closeAction);
/*  476 */     i = MetalUtils.getInt("MetalTitlePane.closeMnemonic", -1);
/*  477 */     if (i != -1) {
/*  478 */       jMenuItem.setMnemonic(i);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JButton createTitleButton() {
/*  487 */     JButton jButton = new JButton();
/*      */     
/*  489 */     jButton.setFocusPainted(false);
/*  490 */     jButton.setFocusable(false);
/*  491 */     jButton.setOpaque(true);
/*  492 */     return jButton;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createButtons() {
/*  499 */     this.closeButton = createTitleButton();
/*  500 */     this.closeButton.setAction(this.closeAction);
/*  501 */     this.closeButton.setText((String)null);
/*  502 */     this.closeButton.putClientProperty("paintActive", Boolean.TRUE);
/*  503 */     this.closeButton.setBorder(handyEmptyBorder);
/*  504 */     this.closeButton.putClientProperty("AccessibleName", "Close");
/*      */     
/*  506 */     this.closeButton.setIcon(UIManager.getIcon("InternalFrame.closeIcon"));
/*      */     
/*  508 */     if (getWindowDecorationStyle() == 1) {
/*  509 */       this.maximizeIcon = UIManager.getIcon("InternalFrame.maximizeIcon");
/*  510 */       this.minimizeIcon = UIManager.getIcon("InternalFrame.minimizeIcon");
/*      */       
/*  512 */       this.iconifyButton = createTitleButton();
/*  513 */       this.iconifyButton.setAction(this.iconifyAction);
/*  514 */       this.iconifyButton.setText((String)null);
/*  515 */       this.iconifyButton.putClientProperty("paintActive", Boolean.TRUE);
/*  516 */       this.iconifyButton.setBorder(handyEmptyBorder);
/*  517 */       this.iconifyButton.putClientProperty("AccessibleName", "Iconify");
/*      */       
/*  519 */       this.iconifyButton.setIcon(UIManager.getIcon("InternalFrame.iconifyIcon"));
/*      */       
/*  521 */       this.toggleButton = createTitleButton();
/*  522 */       this.toggleButton.setAction(this.restoreAction);
/*  523 */       this.toggleButton.putClientProperty("paintActive", Boolean.TRUE);
/*  524 */       this.toggleButton.setBorder(handyEmptyBorder);
/*  525 */       this.toggleButton.putClientProperty("AccessibleName", "Maximize");
/*      */       
/*  527 */       this.toggleButton.setIcon(this.maximizeIcon);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LayoutManager createLayout() {
/*  536 */     return new TitlePaneLayout();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setActive(boolean paramBoolean) {
/*  543 */     Boolean bool = paramBoolean ? Boolean.TRUE : Boolean.FALSE;
/*      */     
/*  545 */     this.closeButton.putClientProperty("paintActive", bool);
/*  546 */     if (getWindowDecorationStyle() == 1) {
/*  547 */       this.iconifyButton.putClientProperty("paintActive", bool);
/*  548 */       this.toggleButton.putClientProperty("paintActive", bool);
/*      */     } 
/*      */ 
/*      */     
/*  552 */     getRootPane().repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setState(int paramInt) {
/*  559 */     setState(paramInt, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setState(int paramInt, boolean paramBoolean) {
/*  567 */     Window window = getWindow();
/*      */     
/*  569 */     if (window != null && getWindowDecorationStyle() == 1) {
/*  570 */       if (this.state == paramInt && !paramBoolean) {
/*      */         return;
/*      */       }
/*  573 */       Frame frame = getFrame();
/*      */       
/*  575 */       if (frame != null) {
/*  576 */         JRootPane jRootPane = getRootPane();
/*      */         
/*  578 */         if ((paramInt & 0x6) != 0 && (jRootPane
/*  579 */           .getBorder() == null || jRootPane
/*  580 */           .getBorder() instanceof javax.swing.plaf.UIResource) && frame
/*  581 */           .isShowing()) {
/*  582 */           jRootPane.setBorder((Border)null);
/*      */         }
/*  584 */         else if ((paramInt & 0x6) == 0) {
/*      */ 
/*      */           
/*  587 */           this.rootPaneUI.installBorder(jRootPane);
/*      */         } 
/*  589 */         if (frame.isResizable()) {
/*  590 */           if ((paramInt & 0x6) != 0) {
/*  591 */             updateToggleButton(this.restoreAction, this.minimizeIcon);
/*  592 */             this.maximizeAction.setEnabled(false);
/*  593 */             this.restoreAction.setEnabled(true);
/*      */           } else {
/*      */             
/*  596 */             updateToggleButton(this.maximizeAction, this.maximizeIcon);
/*  597 */             this.maximizeAction.setEnabled(true);
/*  598 */             this.restoreAction.setEnabled(false);
/*      */           } 
/*  600 */           if (this.toggleButton.getParent() == null || this.iconifyButton
/*  601 */             .getParent() == null) {
/*  602 */             add(this.toggleButton);
/*  603 */             add(this.iconifyButton);
/*  604 */             revalidate();
/*  605 */             repaint();
/*      */           } 
/*  607 */           this.toggleButton.setText((String)null);
/*      */         } else {
/*      */           
/*  610 */           this.maximizeAction.setEnabled(false);
/*  611 */           this.restoreAction.setEnabled(false);
/*  612 */           if (this.toggleButton.getParent() != null) {
/*  613 */             remove(this.toggleButton);
/*  614 */             revalidate();
/*  615 */             repaint();
/*      */           }
/*      */         
/*      */         } 
/*      */       } else {
/*      */         
/*  621 */         this.maximizeAction.setEnabled(false);
/*  622 */         this.restoreAction.setEnabled(false);
/*  623 */         this.iconifyAction.setEnabled(false);
/*  624 */         remove(this.toggleButton);
/*  625 */         remove(this.iconifyButton);
/*  626 */         revalidate();
/*  627 */         repaint();
/*      */       } 
/*  629 */       this.closeAction.setEnabled(true);
/*  630 */       this.state = paramInt;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateToggleButton(Action paramAction, Icon paramIcon) {
/*  639 */     this.toggleButton.setAction(paramAction);
/*  640 */     this.toggleButton.setIcon(paramIcon);
/*  641 */     this.toggleButton.setText((String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Frame getFrame() {
/*  649 */     Window window = getWindow();
/*      */     
/*  651 */     if (window instanceof Frame) {
/*  652 */       return (Frame)window;
/*      */     }
/*  654 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Window getWindow() {
/*  663 */     return this.window;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getTitle() {
/*  670 */     Window window = getWindow();
/*      */     
/*  672 */     if (window instanceof Frame) {
/*  673 */       return ((Frame)window).getTitle();
/*      */     }
/*  675 */     if (window instanceof Dialog) {
/*  676 */       return ((Dialog)window).getTitle();
/*      */     }
/*  678 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintComponent(Graphics paramGraphics) {
/*      */     Color color1, color2, color3;
/*      */     MetalBumps metalBumps;
/*      */     int m, n;
/*  687 */     if (getFrame() != null) {
/*  688 */       setState(getFrame().getExtendedState());
/*      */     }
/*  690 */     JRootPane jRootPane = getRootPane();
/*  691 */     Window window = getWindow();
/*      */ 
/*      */     
/*  694 */     boolean bool = (window == null) ? jRootPane.getComponentOrientation().isLeftToRight() : window.getComponentOrientation().isLeftToRight();
/*  695 */     boolean bool1 = (window == null) ? true : window.isActive();
/*  696 */     int i = getWidth();
/*  697 */     int j = getHeight();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  705 */     if (bool1) {
/*  706 */       color1 = this.activeBackground;
/*  707 */       color2 = this.activeForeground;
/*  708 */       color3 = this.activeShadow;
/*  709 */       metalBumps = this.activeBumps;
/*      */     } else {
/*  711 */       color1 = this.inactiveBackground;
/*  712 */       color2 = this.inactiveForeground;
/*  713 */       color3 = this.inactiveShadow;
/*  714 */       metalBumps = this.inactiveBumps;
/*      */     } 
/*      */     
/*  717 */     paramGraphics.setColor(color1);
/*  718 */     paramGraphics.fillRect(0, 0, i, j);
/*      */     
/*  720 */     paramGraphics.setColor(color3);
/*  721 */     paramGraphics.drawLine(0, j - 1, i, j - 1);
/*  722 */     paramGraphics.drawLine(0, 0, 0, 0);
/*  723 */     paramGraphics.drawLine(i - 1, 0, i - 1, 0);
/*      */     
/*  725 */     int k = bool ? 5 : (i - 5);
/*      */     
/*  727 */     if (getWindowDecorationStyle() == 1) {
/*  728 */       k += bool ? 21 : -21;
/*      */     }
/*      */     
/*  731 */     String str = getTitle();
/*  732 */     if (str != null) {
/*  733 */       FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(jRootPane, paramGraphics);
/*      */       
/*  735 */       paramGraphics.setColor(color2);
/*      */       
/*  737 */       n = (j - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
/*      */       
/*  739 */       Rectangle rectangle = new Rectangle(0, 0, 0, 0);
/*  740 */       if (this.iconifyButton != null && this.iconifyButton.getParent() != null) {
/*  741 */         rectangle = this.iconifyButton.getBounds();
/*      */       }
/*      */ 
/*      */       
/*  745 */       if (bool) {
/*  746 */         if (rectangle.x == 0) {
/*  747 */           rectangle.x = window.getWidth() - (window.getInsets()).right - 2;
/*      */         }
/*  749 */         int i3 = rectangle.x - k - 4;
/*  750 */         str = SwingUtilities2.clipStringIfNecessary(jRootPane, fontMetrics, str, i3);
/*      */       } else {
/*      */         
/*  753 */         int i3 = k - rectangle.x - rectangle.width - 4;
/*  754 */         str = SwingUtilities2.clipStringIfNecessary(jRootPane, fontMetrics, str, i3);
/*      */         
/*  756 */         k -= SwingUtilities2.stringWidth(jRootPane, fontMetrics, str);
/*      */       } 
/*      */       
/*  759 */       int i2 = SwingUtilities2.stringWidth(jRootPane, fontMetrics, str);
/*      */       
/*  761 */       SwingUtilities2.drawString(jRootPane, paramGraphics, str, k, n);
/*      */       
/*  763 */       k += bool ? (i2 + 5) : -5;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  768 */     if (bool) {
/*  769 */       n = i - this.buttonsWidth - k - 5;
/*  770 */       m = k;
/*      */     } else {
/*  772 */       n = k - this.buttonsWidth - 5;
/*  773 */       m = this.buttonsWidth + 5;
/*      */     } 
/*  775 */     byte b = 3;
/*  776 */     int i1 = getHeight() - 2 * b;
/*  777 */     metalBumps.setBumpArea(n, i1);
/*  778 */     metalBumps.paintIcon(this, paramGraphics, m, b);
/*      */   }
/*      */ 
/*      */   
/*      */   private class CloseAction
/*      */     extends AbstractAction
/*      */   {
/*      */     public CloseAction() {
/*  786 */       super(UIManager.getString("MetalTitlePane.closeTitle", MetalTitlePane.this
/*  787 */             .getLocale()));
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  791 */       MetalTitlePane.this.close();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class IconifyAction
/*      */     extends AbstractAction
/*      */   {
/*      */     public IconifyAction() {
/*  801 */       super(UIManager.getString("MetalTitlePane.iconifyTitle", MetalTitlePane.this
/*  802 */             .getLocale()));
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  806 */       MetalTitlePane.this.iconify();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class RestoreAction
/*      */     extends AbstractAction
/*      */   {
/*      */     public RestoreAction() {
/*  816 */       super(
/*  817 */           UIManager.getString("MetalTitlePane.restoreTitle", MetalTitlePane.this.getLocale()));
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  821 */       MetalTitlePane.this.restore();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class MaximizeAction
/*      */     extends AbstractAction
/*      */   {
/*      */     public MaximizeAction() {
/*  831 */       super(UIManager.getString("MetalTitlePane.maximizeTitle", MetalTitlePane.this
/*  832 */             .getLocale()));
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  836 */       MetalTitlePane.this.maximize();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class SystemMenuBar
/*      */     extends JMenuBar
/*      */   {
/*      */     private SystemMenuBar() {}
/*      */ 
/*      */     
/*      */     public void paint(Graphics param1Graphics) {
/*  848 */       if (isOpaque()) {
/*  849 */         param1Graphics.setColor(getBackground());
/*  850 */         param1Graphics.fillRect(0, 0, getWidth(), getHeight());
/*      */       } 
/*      */       
/*  853 */       if (MetalTitlePane.this.systemIcon != null) {
/*  854 */         param1Graphics.drawImage(MetalTitlePane.this.systemIcon, 0, 0, 16, 16, null);
/*      */       } else {
/*  856 */         Icon icon = UIManager.getIcon("InternalFrame.icon");
/*      */         
/*  858 */         if (icon != null)
/*  859 */           icon.paintIcon(this, param1Graphics, 0, 0); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public Dimension getMinimumSize() {
/*  864 */       return getPreferredSize();
/*      */     }
/*      */     public Dimension getPreferredSize() {
/*  867 */       Dimension dimension = super.getPreferredSize();
/*      */       
/*  869 */       return new Dimension(Math.max(16, dimension.width), 
/*  870 */           Math.max(dimension.height, 16));
/*      */     }
/*      */   }
/*      */   
/*      */   private class TitlePaneLayout implements LayoutManager {
/*      */     private TitlePaneLayout() {}
/*      */     
/*      */     public Dimension preferredLayoutSize(Container param1Container) {
/*  878 */       int i = computeHeight();
/*  879 */       return new Dimension(i, i);
/*      */     }
/*      */     public void addLayoutComponent(String param1String, Component param1Component) {}
/*      */     public Dimension minimumLayoutSize(Container param1Container) {
/*  883 */       return preferredLayoutSize(param1Container);
/*      */     }
/*      */     public void removeLayoutComponent(Component param1Component) {}
/*      */     private int computeHeight() {
/*  887 */       FontMetrics fontMetrics = MetalTitlePane.this.rootPane.getFontMetrics(MetalTitlePane.this.getFont());
/*  888 */       int i = fontMetrics.getHeight();
/*  889 */       i += 7;
/*  890 */       byte b = 0;
/*  891 */       if (MetalTitlePane.this.getWindowDecorationStyle() == 1) {
/*  892 */         b = 16;
/*      */       }
/*      */       
/*  895 */       return Math.max(i, b);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void layoutContainer(Container param1Container) {
/*      */       byte b3, b4;
/*  902 */       boolean bool = (MetalTitlePane.this.window == null) ? MetalTitlePane.this.getRootPane().getComponentOrientation().isLeftToRight() : MetalTitlePane.this.window.getComponentOrientation().isLeftToRight();
/*      */       
/*  904 */       int i = MetalTitlePane.this.getWidth();
/*      */       
/*  906 */       byte b1 = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  911 */       if (MetalTitlePane.this.closeButton != null && MetalTitlePane.this.closeButton.getIcon() != null) {
/*  912 */         b3 = MetalTitlePane.this.closeButton.getIcon().getIconHeight();
/*  913 */         b4 = MetalTitlePane.this.closeButton.getIcon().getIconWidth();
/*      */       } else {
/*      */         
/*  916 */         b3 = 16;
/*  917 */         b4 = 16;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  923 */       int j = bool ? i : 0;
/*      */       
/*  925 */       byte b2 = 5;
/*  926 */       j = bool ? b2 : (i - b4 - b2);
/*  927 */       if (MetalTitlePane.this.menuBar != null) {
/*  928 */         MetalTitlePane.this.menuBar.setBounds(j, b1, b4, b3);
/*      */       }
/*      */       
/*  931 */       j = bool ? i : 0;
/*  932 */       b2 = 4;
/*  933 */       j += bool ? (-b2 - b4) : b2;
/*  934 */       if (MetalTitlePane.this.closeButton != null) {
/*  935 */         MetalTitlePane.this.closeButton.setBounds(j, b1, b4, b3);
/*      */       }
/*      */       
/*  938 */       if (!bool) j += b4;
/*      */       
/*  940 */       if (MetalTitlePane.this.getWindowDecorationStyle() == 1) {
/*  941 */         if (Toolkit.getDefaultToolkit().isFrameStateSupported(6))
/*      */         {
/*  943 */           if (MetalTitlePane.this.toggleButton.getParent() != null) {
/*  944 */             b2 = 10;
/*  945 */             j += bool ? (-b2 - b4) : b2;
/*  946 */             MetalTitlePane.this.toggleButton.setBounds(j, b1, b4, b3);
/*  947 */             if (!bool) {
/*  948 */               j += b4;
/*      */             }
/*      */           } 
/*      */         }
/*      */         
/*  953 */         if (MetalTitlePane.this.iconifyButton != null && MetalTitlePane.this.iconifyButton.getParent() != null) {
/*  954 */           b2 = 2;
/*  955 */           j += bool ? (-b2 - b4) : b2;
/*  956 */           MetalTitlePane.this.iconifyButton.setBounds(j, b1, b4, b3);
/*  957 */           if (!bool) {
/*  958 */             j += b4;
/*      */           }
/*      */         } 
/*      */       } 
/*  962 */       MetalTitlePane.this.buttonsWidth = bool ? (i - j) : j;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class PropertyChangeHandler
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     private PropertyChangeHandler() {}
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  974 */       String str = param1PropertyChangeEvent.getPropertyName();
/*      */ 
/*      */       
/*  977 */       if ("resizable".equals(str) || "state".equals(str)) {
/*  978 */         Frame frame = MetalTitlePane.this.getFrame();
/*      */         
/*  980 */         if (frame != null) {
/*  981 */           MetalTitlePane.this.setState(frame.getExtendedState(), true);
/*      */         }
/*  983 */         if ("resizable".equals(str)) {
/*  984 */           MetalTitlePane.this.getRootPane().repaint();
/*      */         }
/*      */       }
/*  987 */       else if ("title".equals(str)) {
/*  988 */         MetalTitlePane.this.repaint();
/*      */       }
/*  990 */       else if ("componentOrientation" == str) {
/*  991 */         MetalTitlePane.this.revalidate();
/*  992 */         MetalTitlePane.this.repaint();
/*      */       }
/*  994 */       else if ("iconImage" == str) {
/*  995 */         MetalTitlePane.this.updateSystemIcon();
/*  996 */         MetalTitlePane.this.revalidate();
/*  997 */         MetalTitlePane.this.repaint();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateSystemIcon() {
/* 1006 */     Window window = getWindow();
/* 1007 */     if (window == null) {
/* 1008 */       this.systemIcon = null;
/*      */       return;
/*      */     } 
/* 1011 */     List<Image> list = window.getIconImages();
/* 1012 */     assert list != null;
/*      */     
/* 1014 */     if (list.size() == 0) {
/* 1015 */       this.systemIcon = null;
/*      */     }
/* 1017 */     else if (list.size() == 1) {
/* 1018 */       this.systemIcon = list.get(0);
/*      */     } else {
/*      */       
/* 1021 */       this.systemIcon = SunToolkit.getScaledIconImage(list, 16, 16);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private class WindowHandler
/*      */     extends WindowAdapter
/*      */   {
/*      */     private WindowHandler() {}
/*      */ 
/*      */     
/*      */     public void windowActivated(WindowEvent param1WindowEvent) {
/* 1033 */       MetalTitlePane.this.setActive(true);
/*      */     }
/*      */     
/*      */     public void windowDeactivated(WindowEvent param1WindowEvent) {
/* 1037 */       MetalTitlePane.this.setActive(false);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalTitlePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */