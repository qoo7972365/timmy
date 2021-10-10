/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.MenuElement;
/*     */ import javax.swing.MenuSelectionManager;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.event.MenuDragMouseEvent;
/*     */ import javax.swing.event.MenuDragMouseListener;
/*     */ import javax.swing.event.MenuKeyListener;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ import javax.swing.plaf.ComponentInputMapUIResource;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.MenuItemUI;
/*     */ import javax.swing.text.View;
/*     */ import sun.swing.MenuItemCheckIconFactory;
/*     */ import sun.swing.MenuItemLayoutHelper;
/*     */ import sun.swing.SwingUtilities2;
/*     */ import sun.swing.UIAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicMenuItemUI
/*     */   extends MenuItemUI
/*     */ {
/*  50 */   protected JMenuItem menuItem = null;
/*     */ 
/*     */   
/*     */   protected Color selectionBackground;
/*     */ 
/*     */   
/*     */   protected Color selectionForeground;
/*     */ 
/*     */   
/*     */   protected Color disabledForeground;
/*     */ 
/*     */   
/*     */   protected Color acceleratorForeground;
/*     */ 
/*     */   
/*     */   protected Color acceleratorSelectionForeground;
/*     */   
/*     */   protected String acceleratorDelimiter;
/*     */   
/*     */   protected int defaultTextIconGap;
/*     */   
/*     */   protected Font acceleratorFont;
/*     */   
/*     */   protected MouseInputListener mouseInputListener;
/*     */   
/*     */   protected MenuDragMouseListener menuDragMouseListener;
/*     */   
/*     */   protected MenuKeyListener menuKeyListener;
/*     */   
/*     */   protected PropertyChangeListener propertyChangeListener;
/*     */   
/*     */   Handler handler;
/*     */   
/*  83 */   protected Icon arrowIcon = null;
/*  84 */   protected Icon checkIcon = null;
/*     */   
/*     */   protected boolean oldBorderPainted;
/*     */   
/*     */   private static final boolean TRACE = false;
/*     */   
/*     */   private static final boolean VERBOSE = false;
/*     */   
/*     */   private static final boolean DEBUG = false;
/*     */ 
/*     */   
/*     */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*  96 */     paramLazyActionMap.put(new Actions("doClick"));
/*  97 */     BasicLookAndFeel.installAudioActionMap(paramLazyActionMap);
/*     */   }
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 101 */     return new BasicMenuItemUI();
/*     */   }
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 105 */     this.menuItem = (JMenuItem)paramJComponent;
/*     */     
/* 107 */     installDefaults();
/* 108 */     installComponents(this.menuItem);
/* 109 */     installListeners();
/* 110 */     installKeyboardActions();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/* 115 */     String str = getPropertyPrefix();
/*     */     
/* 117 */     this.acceleratorFont = UIManager.getFont("MenuItem.acceleratorFont");
/*     */ 
/*     */     
/* 120 */     if (this.acceleratorFont == null) {
/* 121 */       this.acceleratorFont = UIManager.getFont("MenuItem.font");
/*     */     }
/*     */     
/* 124 */     Object object = UIManager.get(getPropertyPrefix() + ".opaque");
/* 125 */     if (object != null) {
/* 126 */       LookAndFeel.installProperty(this.menuItem, "opaque", object);
/*     */     } else {
/*     */       
/* 129 */       LookAndFeel.installProperty(this.menuItem, "opaque", Boolean.TRUE);
/*     */     } 
/* 131 */     if (this.menuItem.getMargin() == null || this.menuItem
/* 132 */       .getMargin() instanceof javax.swing.plaf.UIResource) {
/* 133 */       this.menuItem.setMargin(UIManager.getInsets(str + ".margin"));
/*     */     }
/*     */     
/* 136 */     LookAndFeel.installProperty(this.menuItem, "iconTextGap", Integer.valueOf(4));
/* 137 */     this.defaultTextIconGap = this.menuItem.getIconTextGap();
/*     */     
/* 139 */     LookAndFeel.installBorder(this.menuItem, str + ".border");
/* 140 */     this.oldBorderPainted = this.menuItem.isBorderPainted();
/* 141 */     LookAndFeel.installProperty(this.menuItem, "borderPainted", 
/* 142 */         Boolean.valueOf(UIManager.getBoolean(str + ".borderPainted")));
/* 143 */     LookAndFeel.installColorsAndFont(this.menuItem, str + ".background", str + ".foreground", str + ".font");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     if (this.selectionBackground == null || this.selectionBackground instanceof javax.swing.plaf.UIResource)
/*     */     {
/* 151 */       this
/* 152 */         .selectionBackground = UIManager.getColor(str + ".selectionBackground");
/*     */     }
/* 154 */     if (this.selectionForeground == null || this.selectionForeground instanceof javax.swing.plaf.UIResource)
/*     */     {
/* 156 */       this
/* 157 */         .selectionForeground = UIManager.getColor(str + ".selectionForeground");
/*     */     }
/* 159 */     if (this.disabledForeground == null || this.disabledForeground instanceof javax.swing.plaf.UIResource)
/*     */     {
/* 161 */       this
/* 162 */         .disabledForeground = UIManager.getColor(str + ".disabledForeground");
/*     */     }
/* 164 */     if (this.acceleratorForeground == null || this.acceleratorForeground instanceof javax.swing.plaf.UIResource)
/*     */     {
/* 166 */       this
/* 167 */         .acceleratorForeground = UIManager.getColor(str + ".acceleratorForeground");
/*     */     }
/* 169 */     if (this.acceleratorSelectionForeground == null || this.acceleratorSelectionForeground instanceof javax.swing.plaf.UIResource)
/*     */     {
/* 171 */       this
/* 172 */         .acceleratorSelectionForeground = UIManager.getColor(str + ".acceleratorSelectionForeground");
/*     */     }
/*     */     
/* 175 */     this
/* 176 */       .acceleratorDelimiter = UIManager.getString("MenuItem.acceleratorDelimiter");
/* 177 */     if (this.acceleratorDelimiter == null) this.acceleratorDelimiter = "+";
/*     */     
/* 179 */     if (this.arrowIcon == null || this.arrowIcon instanceof javax.swing.plaf.UIResource)
/*     */     {
/* 181 */       this.arrowIcon = UIManager.getIcon(str + ".arrowIcon");
/*     */     }
/* 183 */     updateCheckIcon();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateCheckIcon() {
/* 190 */     String str = getPropertyPrefix();
/*     */     
/* 192 */     if (this.checkIcon == null || this.checkIcon instanceof javax.swing.plaf.UIResource) {
/*     */       
/* 194 */       this.checkIcon = UIManager.getIcon(str + ".checkIcon");
/*     */ 
/*     */ 
/*     */       
/* 198 */       boolean bool = MenuItemLayoutHelper.isColumnLayout(
/* 199 */           BasicGraphicsUtils.isLeftToRight(this.menuItem), this.menuItem);
/* 200 */       if (bool) {
/*     */         
/* 202 */         MenuItemCheckIconFactory menuItemCheckIconFactory = (MenuItemCheckIconFactory)UIManager.get(str + ".checkIconFactory");
/*     */         
/* 204 */         if (menuItemCheckIconFactory != null && 
/* 205 */           MenuItemLayoutHelper.useCheckAndArrow(this.menuItem) && menuItemCheckIconFactory
/* 206 */           .isCompatible(this.checkIcon, str)) {
/* 207 */           this.checkIcon = menuItemCheckIconFactory.getIcon(this.menuItem);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installComponents(JMenuItem paramJMenuItem) {
/* 217 */     BasicHTML.updateRenderer(paramJMenuItem, paramJMenuItem.getText());
/*     */   }
/*     */   
/*     */   protected String getPropertyPrefix() {
/* 221 */     return "MenuItem";
/*     */   }
/*     */   
/*     */   protected void installListeners() {
/* 225 */     if ((this.mouseInputListener = createMouseInputListener(this.menuItem)) != null) {
/* 226 */       this.menuItem.addMouseListener(this.mouseInputListener);
/* 227 */       this.menuItem.addMouseMotionListener(this.mouseInputListener);
/*     */     } 
/* 229 */     if ((this.menuDragMouseListener = createMenuDragMouseListener(this.menuItem)) != null) {
/* 230 */       this.menuItem.addMenuDragMouseListener(this.menuDragMouseListener);
/*     */     }
/* 232 */     if ((this.menuKeyListener = createMenuKeyListener(this.menuItem)) != null) {
/* 233 */       this.menuItem.addMenuKeyListener(this.menuKeyListener);
/*     */     }
/* 235 */     if ((this.propertyChangeListener = createPropertyChangeListener(this.menuItem)) != null) {
/* 236 */       this.menuItem.addPropertyChangeListener(this.propertyChangeListener);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void installKeyboardActions() {
/* 241 */     installLazyActionMap();
/* 242 */     updateAcceleratorBinding();
/*     */   }
/*     */   
/*     */   void installLazyActionMap() {
/* 246 */     LazyActionMap.installLazyActionMap(this.menuItem, BasicMenuItemUI.class, 
/* 247 */         getPropertyPrefix() + ".actionMap");
/*     */   }
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 251 */     this.menuItem = (JMenuItem)paramJComponent;
/* 252 */     uninstallDefaults();
/* 253 */     uninstallComponents(this.menuItem);
/* 254 */     uninstallListeners();
/* 255 */     uninstallKeyboardActions();
/* 256 */     MenuItemLayoutHelper.clearUsedParentClientProperties(this.menuItem);
/* 257 */     this.menuItem = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 262 */     LookAndFeel.uninstallBorder(this.menuItem);
/* 263 */     LookAndFeel.installProperty(this.menuItem, "borderPainted", Boolean.valueOf(this.oldBorderPainted));
/* 264 */     if (this.menuItem.getMargin() instanceof javax.swing.plaf.UIResource)
/* 265 */       this.menuItem.setMargin((Insets)null); 
/* 266 */     if (this.arrowIcon instanceof javax.swing.plaf.UIResource)
/* 267 */       this.arrowIcon = null; 
/* 268 */     if (this.checkIcon instanceof javax.swing.plaf.UIResource) {
/* 269 */       this.checkIcon = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallComponents(JMenuItem paramJMenuItem) {
/* 276 */     BasicHTML.updateRenderer(paramJMenuItem, "");
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/* 280 */     if (this.mouseInputListener != null) {
/* 281 */       this.menuItem.removeMouseListener(this.mouseInputListener);
/* 282 */       this.menuItem.removeMouseMotionListener(this.mouseInputListener);
/*     */     } 
/* 284 */     if (this.menuDragMouseListener != null) {
/* 285 */       this.menuItem.removeMenuDragMouseListener(this.menuDragMouseListener);
/*     */     }
/* 287 */     if (this.menuKeyListener != null) {
/* 288 */       this.menuItem.removeMenuKeyListener(this.menuKeyListener);
/*     */     }
/* 290 */     if (this.propertyChangeListener != null) {
/* 291 */       this.menuItem.removePropertyChangeListener(this.propertyChangeListener);
/*     */     }
/*     */     
/* 294 */     this.mouseInputListener = null;
/* 295 */     this.menuDragMouseListener = null;
/* 296 */     this.menuKeyListener = null;
/* 297 */     this.propertyChangeListener = null;
/* 298 */     this.handler = null;
/*     */   }
/*     */   
/*     */   protected void uninstallKeyboardActions() {
/* 302 */     SwingUtilities.replaceUIActionMap(this.menuItem, null);
/* 303 */     SwingUtilities.replaceUIInputMap(this.menuItem, 2, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected MouseInputListener createMouseInputListener(JComponent paramJComponent) {
/* 308 */     return getHandler();
/*     */   }
/*     */   
/*     */   protected MenuDragMouseListener createMenuDragMouseListener(JComponent paramJComponent) {
/* 312 */     return getHandler();
/*     */   }
/*     */   
/*     */   protected MenuKeyListener createMenuKeyListener(JComponent paramJComponent) {
/* 316 */     return null;
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
/*     */   protected PropertyChangeListener createPropertyChangeListener(JComponent paramJComponent) {
/* 329 */     return getHandler();
/*     */   }
/*     */   
/*     */   Handler getHandler() {
/* 333 */     if (this.handler == null) {
/* 334 */       this.handler = new Handler();
/*     */     }
/* 336 */     return this.handler;
/*     */   }
/*     */   
/*     */   InputMap createInputMap(int paramInt) {
/* 340 */     if (paramInt == 2) {
/* 341 */       return new ComponentInputMapUIResource(this.menuItem);
/*     */     }
/* 343 */     return null;
/*     */   }
/*     */   
/*     */   void updateAcceleratorBinding() {
/* 347 */     KeyStroke keyStroke = this.menuItem.getAccelerator();
/* 348 */     InputMap inputMap = SwingUtilities.getUIInputMap(this.menuItem, 2);
/*     */ 
/*     */     
/* 351 */     if (inputMap != null) {
/* 352 */       inputMap.clear();
/*     */     }
/* 354 */     if (keyStroke != null) {
/* 355 */       if (inputMap == null) {
/* 356 */         inputMap = createInputMap(2);
/*     */         
/* 358 */         SwingUtilities.replaceUIInputMap(this.menuItem, 2, inputMap);
/*     */       } 
/*     */       
/* 361 */       inputMap.put(keyStroke, "doClick");
/*     */     } 
/*     */   }
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 366 */     Dimension dimension = null;
/* 367 */     View view = (View)paramJComponent.getClientProperty("html");
/* 368 */     if (view != null) {
/* 369 */       dimension = getPreferredSize(paramJComponent);
/* 370 */       dimension.width = (int)(dimension.width - view.getPreferredSpan(0) - view.getMinimumSpan(0));
/*     */     } 
/* 372 */     return dimension;
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 376 */     return getPreferredMenuItemSize(paramJComponent, this.checkIcon, this.arrowIcon, this.defaultTextIconGap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 383 */     Dimension dimension = null;
/* 384 */     View view = (View)paramJComponent.getClientProperty("html");
/* 385 */     if (view != null) {
/* 386 */       dimension = getPreferredSize(paramJComponent);
/* 387 */       dimension.width = (int)(dimension.width + view.getMaximumSpan(0) - view.getPreferredSpan(0));
/*     */     } 
/* 389 */     return dimension;
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
/*     */   protected Dimension getPreferredMenuItemSize(JComponent paramJComponent, Icon paramIcon1, Icon paramIcon2, int paramInt) {
/* 420 */     JMenuItem jMenuItem = (JMenuItem)paramJComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 426 */     MenuItemLayoutHelper menuItemLayoutHelper = new MenuItemLayoutHelper(jMenuItem, paramIcon1, paramIcon2, MenuItemLayoutHelper.createMaxRect(), paramInt, this.acceleratorDelimiter, BasicGraphicsUtils.isLeftToRight(jMenuItem), jMenuItem.getFont(), this.acceleratorFont, MenuItemLayoutHelper.useCheckAndArrow(this.menuItem), getPropertyPrefix());
/*     */     
/* 428 */     Dimension dimension = new Dimension();
/*     */ 
/*     */     
/* 431 */     dimension.width = menuItemLayoutHelper.getLeadingGap();
/* 432 */     MenuItemLayoutHelper.addMaxWidth(menuItemLayoutHelper.getCheckSize(), menuItemLayoutHelper
/* 433 */         .getAfterCheckIconGap(), dimension);
/*     */     
/* 435 */     if (!menuItemLayoutHelper.isTopLevelMenu() && menuItemLayoutHelper
/* 436 */       .getMinTextOffset() > 0 && dimension.width < menuItemLayoutHelper
/* 437 */       .getMinTextOffset()) {
/* 438 */       dimension.width = menuItemLayoutHelper.getMinTextOffset();
/*     */     }
/* 440 */     MenuItemLayoutHelper.addMaxWidth(menuItemLayoutHelper.getLabelSize(), menuItemLayoutHelper.getGap(), dimension);
/* 441 */     MenuItemLayoutHelper.addMaxWidth(menuItemLayoutHelper.getAccSize(), menuItemLayoutHelper.getGap(), dimension);
/* 442 */     MenuItemLayoutHelper.addMaxWidth(menuItemLayoutHelper.getArrowSize(), menuItemLayoutHelper.getGap(), dimension);
/*     */ 
/*     */     
/* 445 */     dimension.height = MenuItemLayoutHelper.max(new int[] { menuItemLayoutHelper.getCheckSize().getHeight(), menuItemLayoutHelper
/* 446 */           .getLabelSize().getHeight(), menuItemLayoutHelper.getAccSize().getHeight(), menuItemLayoutHelper
/* 447 */           .getArrowSize().getHeight() });
/*     */ 
/*     */     
/* 450 */     Insets insets = menuItemLayoutHelper.getMenuItem().getInsets();
/* 451 */     if (insets != null) {
/* 452 */       dimension.width += insets.left + insets.right;
/* 453 */       dimension.height += insets.top + insets.bottom;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 458 */     if (dimension.width % 2 == 0) {
/* 459 */       dimension.width++;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 464 */     if (dimension.height % 2 == 0 && Boolean.TRUE != 
/*     */       
/* 466 */       UIManager.get(getPropertyPrefix() + ".evenHeight")) {
/* 467 */       dimension.height++;
/*     */     }
/*     */     
/* 470 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 480 */     paint(paramGraphics, paramJComponent);
/*     */   }
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 484 */     paintMenuItem(paramGraphics, paramJComponent, this.checkIcon, this.arrowIcon, this.selectionBackground, this.selectionForeground, this.defaultTextIconGap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintMenuItem(Graphics paramGraphics, JComponent paramJComponent, Icon paramIcon1, Icon paramIcon2, Color paramColor1, Color paramColor2, int paramInt) {
/* 494 */     Font font = paramGraphics.getFont();
/* 495 */     Color color = paramGraphics.getColor();
/*     */     
/* 497 */     JMenuItem jMenuItem = (JMenuItem)paramJComponent;
/* 498 */     paramGraphics.setFont(jMenuItem.getFont());
/*     */     
/* 500 */     Rectangle rectangle = new Rectangle(0, 0, jMenuItem.getWidth(), jMenuItem.getHeight());
/* 501 */     applyInsets(rectangle, jMenuItem.getInsets());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 507 */     MenuItemLayoutHelper menuItemLayoutHelper = new MenuItemLayoutHelper(jMenuItem, paramIcon1, paramIcon2, rectangle, paramInt, this.acceleratorDelimiter, BasicGraphicsUtils.isLeftToRight(jMenuItem), jMenuItem.getFont(), this.acceleratorFont, MenuItemLayoutHelper.useCheckAndArrow(this.menuItem), getPropertyPrefix());
/* 508 */     MenuItemLayoutHelper.LayoutResult layoutResult = menuItemLayoutHelper.layoutMenuItem();
/*     */     
/* 510 */     paintBackground(paramGraphics, jMenuItem, paramColor1);
/* 511 */     paintCheckIcon(paramGraphics, menuItemLayoutHelper, layoutResult, color, paramColor2);
/* 512 */     paintIcon(paramGraphics, menuItemLayoutHelper, layoutResult, color);
/* 513 */     paintText(paramGraphics, menuItemLayoutHelper, layoutResult);
/* 514 */     paintAccText(paramGraphics, menuItemLayoutHelper, layoutResult);
/* 515 */     paintArrowIcon(paramGraphics, menuItemLayoutHelper, layoutResult, paramColor2);
/*     */ 
/*     */     
/* 518 */     paramGraphics.setColor(color);
/* 519 */     paramGraphics.setFont(font);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintIcon(Graphics paramGraphics, MenuItemLayoutHelper paramMenuItemLayoutHelper, MenuItemLayoutHelper.LayoutResult paramLayoutResult, Color paramColor) {
/* 524 */     if (paramMenuItemLayoutHelper.getIcon() != null) {
/*     */       Icon icon;
/* 526 */       ButtonModel buttonModel = paramMenuItemLayoutHelper.getMenuItem().getModel();
/* 527 */       if (!buttonModel.isEnabled()) {
/* 528 */         icon = paramMenuItemLayoutHelper.getMenuItem().getDisabledIcon();
/* 529 */       } else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 530 */         icon = paramMenuItemLayoutHelper.getMenuItem().getPressedIcon();
/* 531 */         if (icon == null)
/*     */         {
/* 533 */           icon = paramMenuItemLayoutHelper.getMenuItem().getIcon();
/*     */         }
/*     */       } else {
/* 536 */         icon = paramMenuItemLayoutHelper.getMenuItem().getIcon();
/*     */       } 
/*     */       
/* 539 */       if (icon != null) {
/* 540 */         icon.paintIcon(paramMenuItemLayoutHelper.getMenuItem(), paramGraphics, (paramLayoutResult.getIconRect()).x, 
/* 541 */             (paramLayoutResult.getIconRect()).y);
/* 542 */         paramGraphics.setColor(paramColor);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintCheckIcon(Graphics paramGraphics, MenuItemLayoutHelper paramMenuItemLayoutHelper, MenuItemLayoutHelper.LayoutResult paramLayoutResult, Color paramColor1, Color paramColor2) {
/* 550 */     if (paramMenuItemLayoutHelper.getCheckIcon() != null) {
/* 551 */       ButtonModel buttonModel = paramMenuItemLayoutHelper.getMenuItem().getModel();
/* 552 */       if (buttonModel.isArmed() || (paramMenuItemLayoutHelper.getMenuItem() instanceof javax.swing.JMenu && buttonModel
/* 553 */         .isSelected())) {
/* 554 */         paramGraphics.setColor(paramColor2);
/*     */       } else {
/* 556 */         paramGraphics.setColor(paramColor1);
/*     */       } 
/* 558 */       if (paramMenuItemLayoutHelper.useCheckAndArrow()) {
/* 559 */         paramMenuItemLayoutHelper.getCheckIcon().paintIcon(paramMenuItemLayoutHelper.getMenuItem(), paramGraphics, 
/* 560 */             (paramLayoutResult.getCheckRect()).x, (paramLayoutResult.getCheckRect()).y);
/*     */       }
/* 562 */       paramGraphics.setColor(paramColor1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintAccText(Graphics paramGraphics, MenuItemLayoutHelper paramMenuItemLayoutHelper, MenuItemLayoutHelper.LayoutResult paramLayoutResult) {
/* 568 */     if (!paramMenuItemLayoutHelper.getAccText().equals("")) {
/* 569 */       ButtonModel buttonModel = paramMenuItemLayoutHelper.getMenuItem().getModel();
/* 570 */       paramGraphics.setFont(paramMenuItemLayoutHelper.getAccFontMetrics().getFont());
/* 571 */       if (!buttonModel.isEnabled()) {
/*     */         
/* 573 */         if (this.disabledForeground != null) {
/* 574 */           paramGraphics.setColor(this.disabledForeground);
/* 575 */           SwingUtilities2.drawString(paramMenuItemLayoutHelper.getMenuItem(), paramGraphics, paramMenuItemLayoutHelper
/* 576 */               .getAccText(), (paramLayoutResult.getAccRect()).x, 
/* 577 */               (paramLayoutResult.getAccRect()).y + paramMenuItemLayoutHelper.getAccFontMetrics().getAscent());
/*     */         } else {
/* 579 */           paramGraphics.setColor(paramMenuItemLayoutHelper.getMenuItem().getBackground().brighter());
/* 580 */           SwingUtilities2.drawString(paramMenuItemLayoutHelper.getMenuItem(), paramGraphics, paramMenuItemLayoutHelper
/* 581 */               .getAccText(), (paramLayoutResult.getAccRect()).x, 
/* 582 */               (paramLayoutResult.getAccRect()).y + paramMenuItemLayoutHelper.getAccFontMetrics().getAscent());
/* 583 */           paramGraphics.setColor(paramMenuItemLayoutHelper.getMenuItem().getBackground().darker());
/* 584 */           SwingUtilities2.drawString(paramMenuItemLayoutHelper.getMenuItem(), paramGraphics, paramMenuItemLayoutHelper
/* 585 */               .getAccText(), (paramLayoutResult.getAccRect()).x - 1, 
/* 586 */               (paramLayoutResult.getAccRect()).y + paramMenuItemLayoutHelper.getFontMetrics().getAscent() - 1);
/*     */         } 
/*     */       } else {
/*     */         
/* 590 */         if (buttonModel.isArmed() || (paramMenuItemLayoutHelper
/* 591 */           .getMenuItem() instanceof javax.swing.JMenu && buttonModel
/* 592 */           .isSelected())) {
/* 593 */           paramGraphics.setColor(this.acceleratorSelectionForeground);
/*     */         } else {
/* 595 */           paramGraphics.setColor(this.acceleratorForeground);
/*     */         } 
/* 597 */         SwingUtilities2.drawString(paramMenuItemLayoutHelper.getMenuItem(), paramGraphics, paramMenuItemLayoutHelper.getAccText(), 
/* 598 */             (paramLayoutResult.getAccRect()).x, (paramLayoutResult.getAccRect()).y + paramMenuItemLayoutHelper
/* 599 */             .getAccFontMetrics().getAscent());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintText(Graphics paramGraphics, MenuItemLayoutHelper paramMenuItemLayoutHelper, MenuItemLayoutHelper.LayoutResult paramLayoutResult) {
/* 606 */     if (!paramMenuItemLayoutHelper.getText().equals("")) {
/* 607 */       if (paramMenuItemLayoutHelper.getHtmlView() != null) {
/*     */         
/* 609 */         paramMenuItemLayoutHelper.getHtmlView().paint(paramGraphics, paramLayoutResult.getTextRect());
/*     */       } else {
/*     */         
/* 612 */         paintText(paramGraphics, paramMenuItemLayoutHelper.getMenuItem(), paramLayoutResult.getTextRect(), paramMenuItemLayoutHelper.getText());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintArrowIcon(Graphics paramGraphics, MenuItemLayoutHelper paramMenuItemLayoutHelper, MenuItemLayoutHelper.LayoutResult paramLayoutResult, Color paramColor) {
/* 620 */     if (paramMenuItemLayoutHelper.getArrowIcon() != null) {
/* 621 */       ButtonModel buttonModel = paramMenuItemLayoutHelper.getMenuItem().getModel();
/* 622 */       if (buttonModel.isArmed() || (paramMenuItemLayoutHelper.getMenuItem() instanceof javax.swing.JMenu && buttonModel
/* 623 */         .isSelected())) {
/* 624 */         paramGraphics.setColor(paramColor);
/*     */       }
/* 626 */       if (paramMenuItemLayoutHelper.useCheckAndArrow()) {
/* 627 */         paramMenuItemLayoutHelper.getArrowIcon().paintIcon(paramMenuItemLayoutHelper.getMenuItem(), paramGraphics, 
/* 628 */             (paramLayoutResult.getArrowRect()).x, (paramLayoutResult.getArrowRect()).y);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void applyInsets(Rectangle paramRectangle, Insets paramInsets) {
/* 634 */     if (paramInsets != null) {
/* 635 */       paramRectangle.x += paramInsets.left;
/* 636 */       paramRectangle.y += paramInsets.top;
/* 637 */       paramRectangle.width -= paramInsets.right + paramRectangle.x;
/* 638 */       paramRectangle.height -= paramInsets.bottom + paramRectangle.y;
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
/*     */   protected void paintBackground(Graphics paramGraphics, JMenuItem paramJMenuItem, Color paramColor) {
/* 651 */     ButtonModel buttonModel = paramJMenuItem.getModel();
/* 652 */     Color color = paramGraphics.getColor();
/* 653 */     int i = paramJMenuItem.getWidth();
/* 654 */     int j = paramJMenuItem.getHeight();
/*     */     
/* 656 */     if (paramJMenuItem.isOpaque()) {
/* 657 */       if (buttonModel.isArmed() || (paramJMenuItem instanceof javax.swing.JMenu && buttonModel.isSelected())) {
/* 658 */         paramGraphics.setColor(paramColor);
/* 659 */         paramGraphics.fillRect(0, 0, i, j);
/*     */       } else {
/* 661 */         paramGraphics.setColor(paramJMenuItem.getBackground());
/* 662 */         paramGraphics.fillRect(0, 0, i, j);
/*     */       } 
/* 664 */       paramGraphics.setColor(color);
/*     */     }
/* 666 */     else if (buttonModel.isArmed() || (paramJMenuItem instanceof javax.swing.JMenu && buttonModel
/* 667 */       .isSelected())) {
/* 668 */       paramGraphics.setColor(paramColor);
/* 669 */       paramGraphics.fillRect(0, 0, i, j);
/* 670 */       paramGraphics.setColor(color);
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
/*     */   protected void paintText(Graphics paramGraphics, JMenuItem paramJMenuItem, Rectangle paramRectangle, String paramString) {
/* 684 */     ButtonModel buttonModel = paramJMenuItem.getModel();
/* 685 */     FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(paramJMenuItem, paramGraphics);
/* 686 */     int i = paramJMenuItem.getDisplayedMnemonicIndex();
/*     */     
/* 688 */     if (!buttonModel.isEnabled()) {
/*     */       
/* 690 */       if (UIManager.get("MenuItem.disabledForeground") instanceof Color) {
/* 691 */         paramGraphics.setColor(UIManager.getColor("MenuItem.disabledForeground"));
/* 692 */         SwingUtilities2.drawStringUnderlineCharAt(paramJMenuItem, paramGraphics, paramString, i, paramRectangle.x, paramRectangle.y + fontMetrics
/* 693 */             .getAscent());
/*     */       } else {
/* 695 */         paramGraphics.setColor(paramJMenuItem.getBackground().brighter());
/* 696 */         SwingUtilities2.drawStringUnderlineCharAt(paramJMenuItem, paramGraphics, paramString, i, paramRectangle.x, paramRectangle.y + fontMetrics
/* 697 */             .getAscent());
/* 698 */         paramGraphics.setColor(paramJMenuItem.getBackground().darker());
/* 699 */         SwingUtilities2.drawStringUnderlineCharAt(paramJMenuItem, paramGraphics, paramString, i, paramRectangle.x - 1, paramRectangle.y + fontMetrics
/*     */             
/* 701 */             .getAscent() - 1);
/*     */       } 
/*     */     } else {
/*     */       
/* 705 */       if (buttonModel.isArmed() || (paramJMenuItem instanceof javax.swing.JMenu && buttonModel.isSelected())) {
/* 706 */         paramGraphics.setColor(this.selectionForeground);
/*     */       }
/* 708 */       SwingUtilities2.drawStringUnderlineCharAt(paramJMenuItem, paramGraphics, paramString, i, paramRectangle.x, paramRectangle.y + fontMetrics
/* 709 */           .getAscent());
/*     */     } 
/*     */   }
/*     */   public MenuElement[] getPath() {
/*     */     MenuElement[] arrayOfMenuElement2;
/* 714 */     MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/* 715 */     MenuElement[] arrayOfMenuElement1 = menuSelectionManager.getSelectedPath();
/*     */     
/* 717 */     int i = arrayOfMenuElement1.length;
/* 718 */     if (i == 0)
/* 719 */       return new MenuElement[0]; 
/* 720 */     Container container = this.menuItem.getParent();
/* 721 */     if (arrayOfMenuElement1[i - 1].getComponent() == container) {
/*     */       
/* 723 */       arrayOfMenuElement2 = new MenuElement[i + 1];
/* 724 */       System.arraycopy(arrayOfMenuElement1, 0, arrayOfMenuElement2, 0, i);
/* 725 */       arrayOfMenuElement2[i] = this.menuItem;
/*     */     } else {
/*     */       int j;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 734 */       for (j = arrayOfMenuElement1.length - 1; j >= 0 && 
/* 735 */         arrayOfMenuElement1[j].getComponent() != container; j--);
/*     */ 
/*     */       
/* 738 */       arrayOfMenuElement2 = new MenuElement[j + 2];
/* 739 */       System.arraycopy(arrayOfMenuElement1, 0, arrayOfMenuElement2, 0, j + 1);
/* 740 */       arrayOfMenuElement2[j + 1] = this.menuItem;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 749 */     return arrayOfMenuElement2;
/*     */   }
/*     */   
/*     */   void printMenuElementArray(MenuElement[] paramArrayOfMenuElement, boolean paramBoolean) {
/* 753 */     System.out.println("Path is("); byte b;
/*     */     int i;
/* 755 */     for (b = 0, i = paramArrayOfMenuElement.length; b < i; b++) {
/* 756 */       for (byte b1 = 0; b1 <= b; b1++)
/* 757 */         System.out.print("  "); 
/* 758 */       MenuElement menuElement = paramArrayOfMenuElement[b];
/* 759 */       if (menuElement instanceof JMenuItem) {
/* 760 */         System.out.println(((JMenuItem)menuElement).getText() + ", ");
/* 761 */       } else if (menuElement == null) {
/* 762 */         System.out.println("NULL , ");
/*     */       } else {
/* 764 */         System.out.println("" + menuElement + ", ");
/*     */       } 
/* 766 */     }  System.out.println(")");
/*     */     
/* 768 */     if (paramBoolean == true) {
/* 769 */       Thread.dumpStack();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected class MouseInputHandler
/*     */     implements MouseInputListener
/*     */   {
/*     */     public void mouseClicked(MouseEvent param1MouseEvent) {
/* 778 */       BasicMenuItemUI.this.getHandler().mouseClicked(param1MouseEvent);
/*     */     }
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 781 */       BasicMenuItemUI.this.getHandler().mousePressed(param1MouseEvent);
/*     */     }
/*     */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 784 */       BasicMenuItemUI.this.getHandler().mouseReleased(param1MouseEvent);
/*     */     }
/*     */     public void mouseEntered(MouseEvent param1MouseEvent) {
/* 787 */       BasicMenuItemUI.this.getHandler().mouseEntered(param1MouseEvent);
/*     */     }
/*     */     public void mouseExited(MouseEvent param1MouseEvent) {
/* 790 */       BasicMenuItemUI.this.getHandler().mouseExited(param1MouseEvent);
/*     */     }
/*     */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 793 */       BasicMenuItemUI.this.getHandler().mouseDragged(param1MouseEvent);
/*     */     }
/*     */     public void mouseMoved(MouseEvent param1MouseEvent) {
/* 796 */       BasicMenuItemUI.this.getHandler().mouseMoved(param1MouseEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Actions
/*     */     extends UIAction {
/*     */     private static final String CLICK = "doClick";
/*     */     
/*     */     Actions(String param1String) {
/* 805 */       super(param1String);
/*     */     }
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 809 */       JMenuItem jMenuItem = (JMenuItem)param1ActionEvent.getSource();
/* 810 */       MenuSelectionManager.defaultManager().clearSelectedPath();
/* 811 */       jMenuItem.doClick();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doClick(MenuSelectionManager paramMenuSelectionManager) {
/* 833 */     if (!isInternalFrameSystemMenu()) {
/* 834 */       BasicLookAndFeel.playSound(this.menuItem, getPropertyPrefix() + ".commandSound");
/*     */     }
/*     */ 
/*     */     
/* 838 */     if (paramMenuSelectionManager == null) {
/* 839 */       paramMenuSelectionManager = MenuSelectionManager.defaultManager();
/*     */     }
/* 841 */     paramMenuSelectionManager.clearSelectedPath();
/* 842 */     this.menuItem.doClick(0);
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
/*     */   private boolean isInternalFrameSystemMenu() {
/* 856 */     String str = this.menuItem.getActionCommand();
/* 857 */     if (str == "Close" || str == "Minimize" || str == "Restore" || str == "Maximize")
/*     */     {
/*     */ 
/*     */       
/* 861 */       return true;
/*     */     }
/* 863 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class Handler
/*     */     implements MenuDragMouseListener, MouseInputListener, PropertyChangeListener
/*     */   {
/*     */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*     */ 
/*     */     
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {}
/*     */ 
/*     */     
/*     */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 878 */       if (!BasicMenuItemUI.this.menuItem.isEnabled()) {
/*     */         return;
/*     */       }
/*     */       
/* 882 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/* 883 */       Point point = param1MouseEvent.getPoint();
/* 884 */       if (point.x >= 0 && point.x < BasicMenuItemUI.this.menuItem.getWidth() && point.y >= 0 && point.y < BasicMenuItemUI.this.menuItem
/* 885 */         .getHeight()) {
/* 886 */         BasicMenuItemUI.this.doClick(menuSelectionManager);
/*     */       } else {
/* 888 */         menuSelectionManager.processMouseEvent(param1MouseEvent);
/*     */       } 
/*     */     }
/*     */     public void mouseEntered(MouseEvent param1MouseEvent) {
/* 892 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/* 893 */       int i = param1MouseEvent.getModifiers();
/*     */       
/* 895 */       if ((i & 0x1C) != 0) {
/*     */         
/* 897 */         MenuSelectionManager.defaultManager().processMouseEvent(param1MouseEvent);
/*     */       } else {
/* 899 */         menuSelectionManager.setSelectedPath(BasicMenuItemUI.this.getPath());
/*     */       } 
/*     */     }
/*     */     public void mouseExited(MouseEvent param1MouseEvent) {
/* 903 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*     */       
/* 905 */       int i = param1MouseEvent.getModifiers();
/*     */       
/* 907 */       if ((i & 0x1C) != 0) {
/*     */         
/* 909 */         MenuSelectionManager.defaultManager().processMouseEvent(param1MouseEvent);
/*     */       } else {
/*     */         
/* 912 */         MenuElement[] arrayOfMenuElement = menuSelectionManager.getSelectedPath();
/* 913 */         if (arrayOfMenuElement.length > 1 && arrayOfMenuElement[arrayOfMenuElement.length - 1] == BasicMenuItemUI.this.menuItem) {
/* 914 */           MenuElement[] arrayOfMenuElement1 = new MenuElement[arrayOfMenuElement.length - 1]; byte b;
/*     */           int j;
/* 916 */           for (b = 0, j = arrayOfMenuElement.length - 1; b < j; b++)
/* 917 */             arrayOfMenuElement1[b] = arrayOfMenuElement[b]; 
/* 918 */           menuSelectionManager.setSelectedPath(arrayOfMenuElement1);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 924 */       MenuSelectionManager.defaultManager().processMouseEvent(param1MouseEvent);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseMoved(MouseEvent param1MouseEvent) {}
/*     */ 
/*     */     
/*     */     public void menuDragMouseEntered(MenuDragMouseEvent param1MenuDragMouseEvent) {
/* 933 */       MenuSelectionManager menuSelectionManager = param1MenuDragMouseEvent.getMenuSelectionManager();
/* 934 */       MenuElement[] arrayOfMenuElement = param1MenuDragMouseEvent.getPath();
/* 935 */       menuSelectionManager.setSelectedPath(arrayOfMenuElement);
/*     */     }
/*     */     public void menuDragMouseDragged(MenuDragMouseEvent param1MenuDragMouseEvent) {
/* 938 */       MenuSelectionManager menuSelectionManager = param1MenuDragMouseEvent.getMenuSelectionManager();
/* 939 */       MenuElement[] arrayOfMenuElement = param1MenuDragMouseEvent.getPath();
/* 940 */       menuSelectionManager.setSelectedPath(arrayOfMenuElement);
/*     */     }
/*     */     
/*     */     public void menuDragMouseReleased(MenuDragMouseEvent param1MenuDragMouseEvent) {
/* 944 */       if (!BasicMenuItemUI.this.menuItem.isEnabled()) {
/*     */         return;
/*     */       }
/* 947 */       MenuSelectionManager menuSelectionManager = param1MenuDragMouseEvent.getMenuSelectionManager();
/* 948 */       MenuElement[] arrayOfMenuElement = param1MenuDragMouseEvent.getPath();
/* 949 */       Point point = param1MenuDragMouseEvent.getPoint();
/* 950 */       if (point.x >= 0 && point.x < BasicMenuItemUI.this.menuItem.getWidth() && point.y >= 0 && point.y < BasicMenuItemUI.this.menuItem
/* 951 */         .getHeight()) {
/* 952 */         BasicMenuItemUI.this.doClick(menuSelectionManager);
/*     */       } else {
/* 954 */         menuSelectionManager.clearSelectedPath();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void menuDragMouseExited(MenuDragMouseEvent param1MenuDragMouseEvent) {}
/*     */ 
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 963 */       String str = param1PropertyChangeEvent.getPropertyName();
/*     */       
/* 965 */       if (str == "labelFor" || str == "displayedMnemonic" || str == "accelerator") {
/*     */         
/* 967 */         BasicMenuItemUI.this.updateAcceleratorBinding();
/* 968 */       } else if (str == "text" || "font" == str || "foreground" == str) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 973 */         JMenuItem jMenuItem = (JMenuItem)param1PropertyChangeEvent.getSource();
/* 974 */         String str1 = jMenuItem.getText();
/* 975 */         BasicHTML.updateRenderer(jMenuItem, str1);
/* 976 */       } else if (str == "iconTextGap") {
/* 977 */         BasicMenuItemUI.this.defaultTextIconGap = ((Number)param1PropertyChangeEvent.getNewValue()).intValue();
/* 978 */       } else if (str == "horizontalTextPosition") {
/* 979 */         BasicMenuItemUI.this.updateCheckIcon();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicMenuItemUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */