/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyVetoException;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.event.InternalFrameEvent;
/*     */ import javax.swing.plaf.ActionMapUIResource;
/*     */ import sun.swing.DefaultLookup;
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
/*     */ public class BasicInternalFrameTitlePane
/*     */   extends JComponent
/*     */ {
/*     */   protected JMenuBar menuBar;
/*     */   protected JButton iconButton;
/*     */   protected JButton maxButton;
/*     */   protected JButton closeButton;
/*     */   protected JMenu windowMenu;
/*     */   protected JInternalFrame frame;
/*     */   protected Color selectedTitleColor;
/*     */   protected Color selectedTextColor;
/*     */   protected Color notSelectedTitleColor;
/*     */   protected Color notSelectedTextColor;
/*     */   protected Icon maxIcon;
/*     */   protected Icon minIcon;
/*     */   protected Icon iconIcon;
/*     */   protected Icon closeIcon;
/*     */   protected PropertyChangeListener propertyChangeListener;
/*     */   protected Action closeAction;
/*     */   protected Action maximizeAction;
/*     */   protected Action iconifyAction;
/*     */   protected Action restoreAction;
/*     */   protected Action moveAction;
/*     */   protected Action sizeAction;
/*  89 */   protected static final String CLOSE_CMD = UIManager.getString("InternalFrameTitlePane.closeButtonText");
/*     */   
/*  91 */   protected static final String ICONIFY_CMD = UIManager.getString("InternalFrameTitlePane.minimizeButtonText");
/*     */   
/*  93 */   protected static final String RESTORE_CMD = UIManager.getString("InternalFrameTitlePane.restoreButtonText");
/*     */   
/*  95 */   protected static final String MAXIMIZE_CMD = UIManager.getString("InternalFrameTitlePane.maximizeButtonText");
/*     */   
/*  97 */   protected static final String MOVE_CMD = UIManager.getString("InternalFrameTitlePane.moveButtonText");
/*     */   
/*  99 */   protected static final String SIZE_CMD = UIManager.getString("InternalFrameTitlePane.sizeButtonText");
/*     */   
/*     */   private String closeButtonToolTip;
/*     */   private String iconButtonToolTip;
/*     */   private String restoreButtonToolTip;
/*     */   private String maxButtonToolTip;
/*     */   private Handler handler;
/*     */   
/*     */   public BasicInternalFrameTitlePane(JInternalFrame paramJInternalFrame) {
/* 108 */     this.frame = paramJInternalFrame;
/* 109 */     installTitlePane();
/*     */   }
/*     */   
/*     */   protected void installTitlePane() {
/* 113 */     installDefaults();
/* 114 */     installListeners();
/*     */     
/* 116 */     createActions();
/* 117 */     enableActions();
/* 118 */     createActionMap();
/*     */     
/* 120 */     setLayout(createLayout());
/*     */     
/* 122 */     assembleSystemMenu();
/* 123 */     createButtons();
/* 124 */     addSubComponents();
/*     */     
/* 126 */     updateProperties();
/*     */   }
/*     */   
/*     */   private void updateProperties() {
/* 130 */     Object object = this.frame.getClientProperty(SwingUtilities2.AA_TEXT_PROPERTY_KEY);
/* 131 */     putClientProperty(SwingUtilities2.AA_TEXT_PROPERTY_KEY, object);
/*     */   }
/*     */   
/*     */   protected void addSubComponents() {
/* 135 */     add(this.menuBar);
/* 136 */     add(this.iconButton);
/* 137 */     add(this.maxButton);
/* 138 */     add(this.closeButton);
/*     */   }
/*     */   
/*     */   protected void createActions() {
/* 142 */     this.maximizeAction = new MaximizeAction();
/* 143 */     this.iconifyAction = new IconifyAction();
/* 144 */     this.closeAction = new CloseAction();
/* 145 */     this.restoreAction = new RestoreAction();
/* 146 */     this.moveAction = new MoveAction();
/* 147 */     this.sizeAction = new SizeAction();
/*     */   }
/*     */   
/*     */   ActionMap createActionMap() {
/* 151 */     ActionMapUIResource actionMapUIResource = new ActionMapUIResource();
/* 152 */     actionMapUIResource.put("showSystemMenu", new ShowSystemMenuAction(true));
/* 153 */     actionMapUIResource.put("hideSystemMenu", new ShowSystemMenuAction(false));
/* 154 */     return actionMapUIResource;
/*     */   }
/*     */   
/*     */   protected void installListeners() {
/* 158 */     if (this.propertyChangeListener == null) {
/* 159 */       this.propertyChangeListener = createPropertyChangeListener();
/*     */     }
/* 161 */     this.frame.addPropertyChangeListener(this.propertyChangeListener);
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/* 165 */     this.frame.removePropertyChangeListener(this.propertyChangeListener);
/* 166 */     this.handler = null;
/*     */   }
/*     */   
/*     */   protected void installDefaults() {
/* 170 */     this.maxIcon = UIManager.getIcon("InternalFrame.maximizeIcon");
/* 171 */     this.minIcon = UIManager.getIcon("InternalFrame.minimizeIcon");
/* 172 */     this.iconIcon = UIManager.getIcon("InternalFrame.iconifyIcon");
/* 173 */     this.closeIcon = UIManager.getIcon("InternalFrame.closeIcon");
/*     */     
/* 175 */     this.selectedTitleColor = UIManager.getColor("InternalFrame.activeTitleBackground");
/* 176 */     this.selectedTextColor = UIManager.getColor("InternalFrame.activeTitleForeground");
/* 177 */     this.notSelectedTitleColor = UIManager.getColor("InternalFrame.inactiveTitleBackground");
/* 178 */     this.notSelectedTextColor = UIManager.getColor("InternalFrame.inactiveTitleForeground");
/* 179 */     setFont(UIManager.getFont("InternalFrame.titleFont"));
/* 180 */     this
/* 181 */       .closeButtonToolTip = UIManager.getString("InternalFrame.closeButtonToolTip");
/* 182 */     this
/* 183 */       .iconButtonToolTip = UIManager.getString("InternalFrame.iconButtonToolTip");
/* 184 */     this
/* 185 */       .restoreButtonToolTip = UIManager.getString("InternalFrame.restoreButtonToolTip");
/* 186 */     this
/* 187 */       .maxButtonToolTip = UIManager.getString("InternalFrame.maxButtonToolTip");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {}
/*     */ 
/*     */   
/*     */   protected void createButtons() {
/* 195 */     this.iconButton = new NoFocusButton("InternalFrameTitlePane.iconifyButtonAccessibleName", "InternalFrameTitlePane.iconifyButtonOpacity");
/*     */ 
/*     */     
/* 198 */     this.iconButton.addActionListener(this.iconifyAction);
/* 199 */     if (this.iconButtonToolTip != null && this.iconButtonToolTip.length() != 0) {
/* 200 */       this.iconButton.setToolTipText(this.iconButtonToolTip);
/*     */     }
/*     */     
/* 203 */     this.maxButton = new NoFocusButton("InternalFrameTitlePane.maximizeButtonAccessibleName", "InternalFrameTitlePane.maximizeButtonOpacity");
/*     */ 
/*     */     
/* 206 */     this.maxButton.addActionListener(this.maximizeAction);
/*     */     
/* 208 */     this.closeButton = new NoFocusButton("InternalFrameTitlePane.closeButtonAccessibleName", "InternalFrameTitlePane.closeButtonOpacity");
/*     */ 
/*     */     
/* 211 */     this.closeButton.addActionListener(this.closeAction);
/* 212 */     if (this.closeButtonToolTip != null && this.closeButtonToolTip.length() != 0) {
/* 213 */       this.closeButton.setToolTipText(this.closeButtonToolTip);
/*     */     }
/*     */     
/* 216 */     setButtonIcons();
/*     */   }
/*     */   
/*     */   protected void setButtonIcons() {
/* 220 */     if (this.frame.isIcon()) {
/* 221 */       if (this.minIcon != null) {
/* 222 */         this.iconButton.setIcon(this.minIcon);
/*     */       }
/* 224 */       if (this.restoreButtonToolTip != null && this.restoreButtonToolTip
/* 225 */         .length() != 0) {
/* 226 */         this.iconButton.setToolTipText(this.restoreButtonToolTip);
/*     */       }
/* 228 */       if (this.maxIcon != null) {
/* 229 */         this.maxButton.setIcon(this.maxIcon);
/*     */       }
/* 231 */       if (this.maxButtonToolTip != null && this.maxButtonToolTip.length() != 0) {
/* 232 */         this.maxButton.setToolTipText(this.maxButtonToolTip);
/*     */       }
/* 234 */     } else if (this.frame.isMaximum()) {
/* 235 */       if (this.iconIcon != null) {
/* 236 */         this.iconButton.setIcon(this.iconIcon);
/*     */       }
/* 238 */       if (this.iconButtonToolTip != null && this.iconButtonToolTip.length() != 0) {
/* 239 */         this.iconButton.setToolTipText(this.iconButtonToolTip);
/*     */       }
/* 241 */       if (this.minIcon != null) {
/* 242 */         this.maxButton.setIcon(this.minIcon);
/*     */       }
/* 244 */       if (this.restoreButtonToolTip != null && this.restoreButtonToolTip
/* 245 */         .length() != 0) {
/* 246 */         this.maxButton.setToolTipText(this.restoreButtonToolTip);
/*     */       }
/*     */     } else {
/* 249 */       if (this.iconIcon != null) {
/* 250 */         this.iconButton.setIcon(this.iconIcon);
/*     */       }
/* 252 */       if (this.iconButtonToolTip != null && this.iconButtonToolTip.length() != 0) {
/* 253 */         this.iconButton.setToolTipText(this.iconButtonToolTip);
/*     */       }
/* 255 */       if (this.maxIcon != null) {
/* 256 */         this.maxButton.setIcon(this.maxIcon);
/*     */       }
/* 258 */       if (this.maxButtonToolTip != null && this.maxButtonToolTip.length() != 0) {
/* 259 */         this.maxButton.setToolTipText(this.maxButtonToolTip);
/*     */       }
/*     */     } 
/* 262 */     if (this.closeIcon != null) {
/* 263 */       this.closeButton.setIcon(this.closeIcon);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void assembleSystemMenu() {
/* 268 */     this.menuBar = createSystemMenuBar();
/* 269 */     this.windowMenu = createSystemMenu();
/* 270 */     this.menuBar.add(this.windowMenu);
/* 271 */     addSystemMenuItems(this.windowMenu);
/* 272 */     enableActions();
/*     */   }
/*     */   
/*     */   protected void addSystemMenuItems(JMenu paramJMenu) {
/* 276 */     JMenuItem jMenuItem = paramJMenu.add(this.restoreAction);
/* 277 */     jMenuItem.setMnemonic(getButtonMnemonic("restore"));
/* 278 */     jMenuItem = paramJMenu.add(this.moveAction);
/* 279 */     jMenuItem.setMnemonic(getButtonMnemonic("move"));
/* 280 */     jMenuItem = paramJMenu.add(this.sizeAction);
/* 281 */     jMenuItem.setMnemonic(getButtonMnemonic("size"));
/* 282 */     jMenuItem = paramJMenu.add(this.iconifyAction);
/* 283 */     jMenuItem.setMnemonic(getButtonMnemonic("minimize"));
/* 284 */     jMenuItem = paramJMenu.add(this.maximizeAction);
/* 285 */     jMenuItem.setMnemonic(getButtonMnemonic("maximize"));
/* 286 */     paramJMenu.add(new JSeparator());
/* 287 */     jMenuItem = paramJMenu.add(this.closeAction);
/* 288 */     jMenuItem.setMnemonic(getButtonMnemonic("close"));
/*     */   }
/*     */   
/*     */   private static int getButtonMnemonic(String paramString) {
/*     */     try {
/* 293 */       return Integer.parseInt(UIManager.getString("InternalFrameTitlePane." + paramString + "Button.mnemonic"));
/*     */     }
/* 295 */     catch (NumberFormatException numberFormatException) {
/* 296 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected JMenu createSystemMenu() {
/* 301 */     return new JMenu("    ");
/*     */   }
/*     */   
/*     */   protected JMenuBar createSystemMenuBar() {
/* 305 */     this.menuBar = new SystemMenuBar();
/* 306 */     this.menuBar.setBorderPainted(false);
/* 307 */     return this.menuBar;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void showSystemMenu() {
/* 313 */     this.windowMenu.doClick();
/*     */   }
/*     */   
/*     */   public void paintComponent(Graphics paramGraphics) {
/* 317 */     paintTitleBackground(paramGraphics);
/*     */     
/* 319 */     if (this.frame.getTitle() != null) {
/* 320 */       int j; boolean bool = this.frame.isSelected();
/* 321 */       Font font = paramGraphics.getFont();
/* 322 */       paramGraphics.setFont(getFont());
/* 323 */       if (bool) {
/* 324 */         paramGraphics.setColor(this.selectedTextColor);
/*     */       } else {
/* 326 */         paramGraphics.setColor(this.notSelectedTextColor);
/*     */       } 
/*     */       
/* 329 */       FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(this.frame, paramGraphics);
/*     */       
/* 331 */       int i = (getHeight() + fontMetrics.getAscent() - fontMetrics.getLeading() - fontMetrics.getDescent()) / 2;
/*     */ 
/*     */       
/* 334 */       Rectangle rectangle = new Rectangle(0, 0, 0, 0);
/* 335 */       if (this.frame.isIconifiable()) { rectangle = this.iconButton.getBounds(); }
/* 336 */       else if (this.frame.isMaximizable()) { rectangle = this.maxButton.getBounds(); }
/* 337 */       else if (this.frame.isClosable()) { rectangle = this.closeButton.getBounds(); }
/*     */ 
/*     */       
/* 340 */       String str = this.frame.getTitle();
/* 341 */       if (BasicGraphicsUtils.isLeftToRight(this.frame)) {
/* 342 */         if (rectangle.x == 0) rectangle.x = this.frame.getWidth() - (this.frame.getInsets()).right; 
/* 343 */         j = this.menuBar.getX() + this.menuBar.getWidth() + 2;
/* 344 */         int k = rectangle.x - j - 3;
/* 345 */         str = getTitle(this.frame.getTitle(), fontMetrics, k);
/*     */       } else {
/*     */         
/* 348 */         j = this.menuBar.getX() - 2 - SwingUtilities2.stringWidth(this.frame, fontMetrics, str);
/*     */       } 
/*     */       
/* 351 */       SwingUtilities2.drawString(this.frame, paramGraphics, str, j, i);
/* 352 */       paramGraphics.setFont(font);
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
/*     */   protected void paintTitleBackground(Graphics paramGraphics) {
/* 364 */     boolean bool = this.frame.isSelected();
/*     */     
/* 366 */     if (bool) {
/* 367 */       paramGraphics.setColor(this.selectedTitleColor);
/*     */     } else {
/* 369 */       paramGraphics.setColor(this.notSelectedTitleColor);
/* 370 */     }  paramGraphics.fillRect(0, 0, getWidth(), getHeight());
/*     */   }
/*     */   
/*     */   protected String getTitle(String paramString, FontMetrics paramFontMetrics, int paramInt) {
/* 374 */     return SwingUtilities2.clipStringIfNecessary(this.frame, paramFontMetrics, paramString, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postClosingEvent(JInternalFrame paramJInternalFrame) {
/* 383 */     InternalFrameEvent internalFrameEvent = new InternalFrameEvent(paramJInternalFrame, 25550);
/*     */ 
/*     */     
/*     */     try {
/* 387 */       Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(internalFrameEvent);
/* 388 */     } catch (SecurityException securityException) {
/* 389 */       paramJInternalFrame.dispatchEvent(internalFrameEvent);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void enableActions() {
/* 395 */     this.restoreAction.setEnabled((this.frame.isMaximum() || this.frame.isIcon()));
/* 396 */     this.maximizeAction.setEnabled(((this.frame
/* 397 */         .isMaximizable() && !this.frame.isMaximum() && !this.frame.isIcon()) || (this.frame
/* 398 */         .isMaximizable() && this.frame.isIcon())));
/* 399 */     this.iconifyAction.setEnabled((this.frame.isIconifiable() && !this.frame.isIcon()));
/* 400 */     this.closeAction.setEnabled(this.frame.isClosable());
/* 401 */     this.sizeAction.setEnabled(false);
/* 402 */     this.moveAction.setEnabled(false);
/*     */   }
/*     */   
/*     */   private Handler getHandler() {
/* 406 */     if (this.handler == null) {
/* 407 */       this.handler = new Handler();
/*     */     }
/* 409 */     return this.handler;
/*     */   }
/*     */   
/*     */   protected PropertyChangeListener createPropertyChangeListener() {
/* 413 */     return getHandler();
/*     */   }
/*     */   
/*     */   protected LayoutManager createLayout() {
/* 417 */     return getHandler();
/*     */   }
/*     */   
/*     */   private class Handler
/*     */     implements LayoutManager, PropertyChangeListener
/*     */   {
/*     */     private Handler() {}
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 426 */       String str = param1PropertyChangeEvent.getPropertyName();
/*     */       
/* 428 */       if (str == "selected") {
/* 429 */         BasicInternalFrameTitlePane.this.repaint();
/*     */         
/*     */         return;
/*     */       } 
/* 433 */       if (str == "icon" || str == "maximum") {
/*     */         
/* 435 */         BasicInternalFrameTitlePane.this.setButtonIcons();
/* 436 */         BasicInternalFrameTitlePane.this.enableActions();
/*     */         
/*     */         return;
/*     */       } 
/* 440 */       if ("closable" == str) {
/* 441 */         if (param1PropertyChangeEvent.getNewValue() == Boolean.TRUE) {
/* 442 */           BasicInternalFrameTitlePane.this.add(BasicInternalFrameTitlePane.this.closeButton);
/*     */         } else {
/* 444 */           BasicInternalFrameTitlePane.this.remove(BasicInternalFrameTitlePane.this.closeButton);
/*     */         } 
/* 446 */       } else if ("maximizable" == str) {
/* 447 */         if (param1PropertyChangeEvent.getNewValue() == Boolean.TRUE) {
/* 448 */           BasicInternalFrameTitlePane.this.add(BasicInternalFrameTitlePane.this.maxButton);
/*     */         } else {
/* 450 */           BasicInternalFrameTitlePane.this.remove(BasicInternalFrameTitlePane.this.maxButton);
/*     */         } 
/* 452 */       } else if ("iconable" == str) {
/* 453 */         if (param1PropertyChangeEvent.getNewValue() == Boolean.TRUE) {
/* 454 */           BasicInternalFrameTitlePane.this.add(BasicInternalFrameTitlePane.this.iconButton);
/*     */         } else {
/* 456 */           BasicInternalFrameTitlePane.this.remove(BasicInternalFrameTitlePane.this.iconButton);
/*     */         } 
/*     */       } 
/* 459 */       BasicInternalFrameTitlePane.this.enableActions();
/*     */       
/* 461 */       BasicInternalFrameTitlePane.this.revalidate();
/* 462 */       BasicInternalFrameTitlePane.this.repaint();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addLayoutComponent(String param1String, Component param1Component) {}
/*     */ 
/*     */     
/*     */     public void removeLayoutComponent(Component param1Component) {}
/*     */     
/*     */     public Dimension preferredLayoutSize(Container param1Container) {
/* 472 */       return minimumLayoutSize(param1Container);
/*     */     }
/*     */ 
/*     */     
/*     */     public Dimension minimumLayoutSize(Container param1Container) {
/* 477 */       int i = 22;
/*     */       
/* 479 */       if (BasicInternalFrameTitlePane.this.frame.isClosable()) {
/* 480 */         i += 19;
/*     */       }
/* 482 */       if (BasicInternalFrameTitlePane.this.frame.isMaximizable()) {
/* 483 */         i += 19;
/*     */       }
/* 485 */       if (BasicInternalFrameTitlePane.this.frame.isIconifiable()) {
/* 486 */         i += 19;
/*     */       }
/*     */       
/* 489 */       FontMetrics fontMetrics = BasicInternalFrameTitlePane.this.frame.getFontMetrics(BasicInternalFrameTitlePane.this.getFont());
/* 490 */       String str = BasicInternalFrameTitlePane.this.frame.getTitle();
/* 491 */       byte b1 = (str != null) ? SwingUtilities2.stringWidth(BasicInternalFrameTitlePane.this.frame, fontMetrics, str) : 0;
/*     */       
/* 493 */       byte b2 = (str != null) ? str.length() : 0;
/*     */ 
/*     */       
/* 496 */       if (b2 > 3) {
/* 497 */         int n = SwingUtilities2.stringWidth(BasicInternalFrameTitlePane.this.frame, fontMetrics, str
/* 498 */             .substring(0, 3) + "...");
/* 499 */         i += (b1 < n) ? b1 : n;
/*     */       } else {
/* 501 */         i += b1;
/*     */       } 
/*     */ 
/*     */       
/* 505 */       Icon icon = BasicInternalFrameTitlePane.this.frame.getFrameIcon();
/* 506 */       int j = fontMetrics.getHeight();
/* 507 */       j += 2;
/* 508 */       int k = 0;
/* 509 */       if (icon != null)
/*     */       {
/* 511 */         k = Math.min(icon.getIconHeight(), 16);
/*     */       }
/* 513 */       k += 2;
/*     */       
/* 515 */       int m = Math.max(j, k);
/*     */       
/* 517 */       Dimension dimension = new Dimension(i, m);
/*     */ 
/*     */       
/* 520 */       if (BasicInternalFrameTitlePane.this.getBorder() != null) {
/* 521 */         Insets insets = BasicInternalFrameTitlePane.this.getBorder().getBorderInsets(param1Container);
/* 522 */         dimension.height += insets.top + insets.bottom;
/* 523 */         dimension.width += insets.left + insets.right;
/*     */       } 
/* 525 */       return dimension;
/*     */     }
/*     */     
/*     */     public void layoutContainer(Container param1Container) {
/* 529 */       boolean bool = BasicGraphicsUtils.isLeftToRight(BasicInternalFrameTitlePane.this.frame);
/*     */       
/* 531 */       int i = BasicInternalFrameTitlePane.this.getWidth();
/* 532 */       int j = BasicInternalFrameTitlePane.this.getHeight();
/*     */ 
/*     */       
/* 535 */       int m = BasicInternalFrameTitlePane.this.closeButton.getIcon().getIconHeight();
/*     */       
/* 537 */       Icon icon = BasicInternalFrameTitlePane.this.frame.getFrameIcon();
/* 538 */       int n = 0;
/* 539 */       if (icon != null) {
/* 540 */         n = icon.getIconHeight();
/*     */       }
/* 542 */       int k = bool ? 2 : (i - 16 - 2);
/* 543 */       BasicInternalFrameTitlePane.this.menuBar.setBounds(k, (j - n) / 2, 16, 16);
/*     */       
/* 545 */       k = bool ? (i - 16 - 2) : 2;
/*     */       
/* 547 */       if (BasicInternalFrameTitlePane.this.frame.isClosable()) {
/* 548 */         BasicInternalFrameTitlePane.this.closeButton.setBounds(k, (j - m) / 2, 16, 14);
/* 549 */         k += bool ? -18 : 18;
/*     */       } 
/*     */       
/* 552 */       if (BasicInternalFrameTitlePane.this.frame.isMaximizable()) {
/* 553 */         BasicInternalFrameTitlePane.this.maxButton.setBounds(k, (j - m) / 2, 16, 14);
/* 554 */         k += bool ? -18 : 18;
/*     */       } 
/*     */       
/* 557 */       if (BasicInternalFrameTitlePane.this.frame.isIconifiable()) {
/* 558 */         BasicInternalFrameTitlePane.this.iconButton.setBounds(k, (j - m) / 2, 16, 14);
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
/*     */   public class PropertyChangeHandler
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 573 */       BasicInternalFrameTitlePane.this.getHandler().propertyChange(param1PropertyChangeEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class TitlePaneLayout
/*     */     implements LayoutManager
/*     */   {
/*     */     public void addLayoutComponent(String param1String, Component param1Component) {
/* 587 */       BasicInternalFrameTitlePane.this.getHandler().addLayoutComponent(param1String, param1Component);
/*     */     }
/*     */     
/*     */     public void removeLayoutComponent(Component param1Component) {
/* 591 */       BasicInternalFrameTitlePane.this.getHandler().removeLayoutComponent(param1Component);
/*     */     }
/*     */     
/*     */     public Dimension preferredLayoutSize(Container param1Container) {
/* 595 */       return BasicInternalFrameTitlePane.this.getHandler().preferredLayoutSize(param1Container);
/*     */     }
/*     */     
/*     */     public Dimension minimumLayoutSize(Container param1Container) {
/* 599 */       return BasicInternalFrameTitlePane.this.getHandler().minimumLayoutSize(param1Container);
/*     */     }
/*     */     
/*     */     public void layoutContainer(Container param1Container) {
/* 603 */       BasicInternalFrameTitlePane.this.getHandler().layoutContainer(param1Container);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class CloseAction
/*     */     extends AbstractAction
/*     */   {
/*     */     public CloseAction() {
/* 613 */       super(UIManager.getString("InternalFrameTitlePane.closeButtonText"));
/*     */     }
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 618 */       if (BasicInternalFrameTitlePane.this.frame.isClosable()) {
/* 619 */         BasicInternalFrameTitlePane.this.frame.doDefaultCloseAction();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class MaximizeAction
/*     */     extends AbstractAction
/*     */   {
/*     */     public MaximizeAction() {
/* 630 */       super(UIManager.getString("InternalFrameTitlePane.maximizeButtonText"));
/*     */     }
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 635 */       if (BasicInternalFrameTitlePane.this.frame.isMaximizable()) {
/* 636 */         if (BasicInternalFrameTitlePane.this.frame.isMaximum() && BasicInternalFrameTitlePane.this.frame.isIcon()) {
/*     */           try {
/* 638 */             BasicInternalFrameTitlePane.this.frame.setIcon(false);
/* 639 */           } catch (PropertyVetoException propertyVetoException) {}
/* 640 */         } else if (!BasicInternalFrameTitlePane.this.frame.isMaximum()) {
/*     */           try {
/* 642 */             BasicInternalFrameTitlePane.this.frame.setMaximum(true);
/* 643 */           } catch (PropertyVetoException propertyVetoException) {}
/*     */         } else {
/*     */           try {
/* 646 */             BasicInternalFrameTitlePane.this.frame.setMaximum(false);
/* 647 */           } catch (PropertyVetoException propertyVetoException) {}
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class IconifyAction
/*     */     extends AbstractAction
/*     */   {
/*     */     public IconifyAction() {
/* 659 */       super(UIManager.getString("InternalFrameTitlePane.minimizeButtonText"));
/*     */     }
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 664 */       if (BasicInternalFrameTitlePane.this.frame.isIconifiable()) {
/* 665 */         if (!BasicInternalFrameTitlePane.this.frame.isIcon()) { 
/* 666 */           try { BasicInternalFrameTitlePane.this.frame.setIcon(true); } catch (PropertyVetoException propertyVetoException) {} }
/*     */         else { 
/* 668 */           try { BasicInternalFrameTitlePane.this.frame.setIcon(false); } catch (PropertyVetoException propertyVetoException) {} }
/*     */       
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class RestoreAction
/*     */     extends AbstractAction
/*     */   {
/*     */     public RestoreAction() {
/* 680 */       super(UIManager.getString("InternalFrameTitlePane.restoreButtonText"));
/*     */     }
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 685 */       if (BasicInternalFrameTitlePane.this.frame.isMaximizable() && BasicInternalFrameTitlePane.this.frame.isMaximum() && BasicInternalFrameTitlePane.this.frame.isIcon()) {
/*     */         try {
/* 687 */           BasicInternalFrameTitlePane.this.frame.setIcon(false);
/* 688 */         } catch (PropertyVetoException propertyVetoException) {}
/* 689 */       } else if (BasicInternalFrameTitlePane.this.frame.isMaximizable() && BasicInternalFrameTitlePane.this.frame.isMaximum()) {
/*     */         try {
/* 691 */           BasicInternalFrameTitlePane.this.frame.setMaximum(false);
/* 692 */         } catch (PropertyVetoException propertyVetoException) {}
/* 693 */       } else if (BasicInternalFrameTitlePane.this.frame.isIconifiable() && BasicInternalFrameTitlePane.this.frame.isIcon()) {
/*     */         try {
/* 695 */           BasicInternalFrameTitlePane.this.frame.setIcon(false);
/* 696 */         } catch (PropertyVetoException propertyVetoException) {}
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class MoveAction
/*     */     extends AbstractAction
/*     */   {
/*     */     public MoveAction() {
/* 707 */       super(UIManager.getString("InternalFrameTitlePane.moveButtonText"));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private class ShowSystemMenuAction
/*     */     extends AbstractAction
/*     */   {
/*     */     private boolean show;
/*     */ 
/*     */     
/*     */     public ShowSystemMenuAction(boolean param1Boolean) {
/* 723 */       this.show = param1Boolean;
/*     */     }
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 727 */       if (this.show) {
/* 728 */         BasicInternalFrameTitlePane.this.windowMenu.doClick();
/*     */       } else {
/* 730 */         BasicInternalFrameTitlePane.this.windowMenu.setVisible(false);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class SizeAction
/*     */     extends AbstractAction
/*     */   {
/*     */     public SizeAction() {
/* 741 */       super(UIManager.getString("InternalFrameTitlePane.sizeButtonText"));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class SystemMenuBar
/*     */     extends JMenuBar
/*     */   {
/*     */     public boolean isFocusTraversable() {
/* 756 */       return false;
/*     */     }
/*     */     public void paint(Graphics param1Graphics) {
/* 759 */       Icon icon = BasicInternalFrameTitlePane.this.frame.getFrameIcon();
/* 760 */       if (icon == null) {
/* 761 */         icon = (Icon)DefaultLookup.get(BasicInternalFrameTitlePane.this.frame, BasicInternalFrameTitlePane.this.frame.getUI(), "InternalFrame.icon");
/*     */       }
/*     */       
/* 764 */       if (icon != null) {
/*     */         
/* 766 */         if (icon instanceof ImageIcon && (icon.getIconWidth() > 16 || icon.getIconHeight() > 16)) {
/* 767 */           Image image = ((ImageIcon)icon).getImage();
/* 768 */           ((ImageIcon)icon).setImage(image.getScaledInstance(16, 16, 4));
/*     */         } 
/* 770 */         icon.paintIcon(this, param1Graphics, 0, 0);
/*     */       } 
/*     */     }
/*     */     public void requestFocus() {}
/*     */     public boolean isOpaque() {
/* 775 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private class NoFocusButton extends JButton {
/*     */     private String uiKey;
/*     */     
/*     */     public NoFocusButton(String param1String1, String param1String2) {
/* 783 */       setFocusPainted(false);
/* 784 */       setMargin(new Insets(0, 0, 0, 0));
/* 785 */       this.uiKey = param1String1;
/*     */       
/* 787 */       Object object = UIManager.get(param1String2);
/* 788 */       if (object instanceof Boolean)
/* 789 */         setOpaque(((Boolean)object).booleanValue()); 
/*     */     }
/*     */     public boolean isFocusTraversable() {
/* 792 */       return false;
/*     */     } public void requestFocus() {}
/*     */     public AccessibleContext getAccessibleContext() {
/* 795 */       AccessibleContext accessibleContext = super.getAccessibleContext();
/* 796 */       if (this.uiKey != null) {
/* 797 */         accessibleContext.setAccessibleName(UIManager.getString(this.uiKey));
/* 798 */         this.uiKey = null;
/*     */       } 
/* 800 */       return accessibleContext;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicInternalFrameTitlePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */