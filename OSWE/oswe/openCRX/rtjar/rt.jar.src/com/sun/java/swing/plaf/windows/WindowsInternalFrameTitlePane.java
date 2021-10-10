/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyVetoException;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
/*     */ import sun.swing.SwingUtilities2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsInternalFrameTitlePane
/*     */   extends BasicInternalFrameTitlePane
/*     */ {
/*     */   private Color selectedTitleGradientColor;
/*     */   private Color notSelectedTitleGradientColor;
/*     */   private JPopupMenu systemPopupMenu;
/*     */   private JLabel systemLabel;
/*     */   private Font titleFont;
/*     */   private int titlePaneHeight;
/*     */   private int buttonWidth;
/*     */   private int buttonHeight;
/*     */   private boolean hotTrackingOn;
/*     */   
/*     */   public WindowsInternalFrameTitlePane(JInternalFrame paramJInternalFrame) {
/*  56 */     super(paramJInternalFrame);
/*     */   }
/*     */   
/*     */   protected void addSubComponents() {
/*  60 */     add(this.systemLabel);
/*  61 */     add(this.iconButton);
/*  62 */     add(this.maxButton);
/*  63 */     add(this.closeButton);
/*     */   }
/*     */   
/*     */   protected void installDefaults() {
/*  67 */     super.installDefaults();
/*     */     
/*  69 */     this.titlePaneHeight = UIManager.getInt("InternalFrame.titlePaneHeight");
/*  70 */     this.buttonWidth = UIManager.getInt("InternalFrame.titleButtonWidth") - 4;
/*  71 */     this.buttonHeight = UIManager.getInt("InternalFrame.titleButtonHeight") - 4;
/*     */     
/*  73 */     Object object = UIManager.get("InternalFrame.titleButtonToolTipsOn");
/*  74 */     this.hotTrackingOn = (object instanceof Boolean) ? ((Boolean)object).booleanValue() : true;
/*     */ 
/*     */     
/*  77 */     if (XPStyle.getXP() != null) {
/*     */ 
/*     */ 
/*     */       
/*  81 */       this.buttonWidth = this.buttonHeight;
/*  82 */       Dimension dimension = XPStyle.getPartSize(TMSchema.Part.WP_CLOSEBUTTON, TMSchema.State.NORMAL);
/*  83 */       if (dimension != null && dimension.width != 0 && dimension.height != 0) {
/*  84 */         this.buttonWidth = (int)(this.buttonWidth * dimension.width / dimension.height);
/*     */       }
/*     */     } else {
/*  87 */       this.buttonWidth += 2;
/*     */       
/*  89 */       Color color = UIManager.getColor("InternalFrame.activeBorderColor");
/*  90 */       setBorder(BorderFactory.createLineBorder(color, 1));
/*     */     } 
/*     */     
/*  93 */     this
/*  94 */       .selectedTitleGradientColor = UIManager.getColor("InternalFrame.activeTitleGradient");
/*  95 */     this
/*  96 */       .notSelectedTitleGradientColor = UIManager.getColor("InternalFrame.inactiveTitleGradient");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 101 */     super.uninstallListeners();
/*     */   }
/*     */   
/*     */   protected void createButtons() {
/* 105 */     super.createButtons();
/* 106 */     if (XPStyle.getXP() != null) {
/* 107 */       this.iconButton.setContentAreaFilled(false);
/* 108 */       this.maxButton.setContentAreaFilled(false);
/* 109 */       this.closeButton.setContentAreaFilled(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setButtonIcons() {
/* 114 */     super.setButtonIcons();
/*     */     
/* 116 */     if (!this.hotTrackingOn) {
/* 117 */       this.iconButton.setToolTipText((String)null);
/* 118 */       this.maxButton.setToolTipText((String)null);
/* 119 */       this.closeButton.setToolTipText((String)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintComponent(Graphics paramGraphics) {
/* 125 */     XPStyle xPStyle = XPStyle.getXP();
/*     */     
/* 127 */     paintTitleBackground(paramGraphics);
/*     */     
/* 129 */     String str = this.frame.getTitle();
/* 130 */     if (str != null) {
/* 131 */       int j, k; boolean bool = this.frame.isSelected();
/* 132 */       Font font1 = paramGraphics.getFont();
/* 133 */       Font font2 = (this.titleFont != null) ? this.titleFont : getFont();
/* 134 */       paramGraphics.setFont(font2);
/*     */ 
/*     */       
/* 137 */       FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(this.frame, paramGraphics, font2);
/*     */       
/* 139 */       int i = (getHeight() + fontMetrics.getAscent() - fontMetrics.getLeading() - fontMetrics.getDescent()) / 2;
/*     */       
/* 141 */       Rectangle rectangle = new Rectangle(0, 0, 0, 0);
/* 142 */       if (this.frame.isIconifiable()) {
/* 143 */         rectangle = this.iconButton.getBounds();
/* 144 */       } else if (this.frame.isMaximizable()) {
/* 145 */         rectangle = this.maxButton.getBounds();
/* 146 */       } else if (this.frame.isClosable()) {
/* 147 */         rectangle = this.closeButton.getBounds();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 152 */       byte b = 2;
/* 153 */       if (WindowsGraphicsUtils.isLeftToRight(this.frame)) {
/* 154 */         if (rectangle.x == 0) {
/* 155 */           rectangle.x = this.frame.getWidth() - (this.frame.getInsets()).right;
/*     */         }
/* 157 */         j = this.systemLabel.getX() + this.systemLabel.getWidth() + b;
/* 158 */         if (xPStyle != null) {
/* 159 */           j += 2;
/*     */         }
/* 161 */         k = rectangle.x - j - b;
/*     */       } else {
/* 163 */         if (rectangle.x == 0) {
/* 164 */           rectangle.x = (this.frame.getInsets()).left;
/*     */         }
/* 166 */         k = SwingUtilities2.stringWidth(this.frame, fontMetrics, str);
/* 167 */         int m = rectangle.x + rectangle.width + b;
/* 168 */         if (xPStyle != null) {
/* 169 */           m += 2;
/*     */         }
/* 171 */         int n = this.systemLabel.getX() - b - m;
/* 172 */         if (n > k) {
/* 173 */           j = this.systemLabel.getX() - b - k;
/*     */         } else {
/* 175 */           j = m;
/* 176 */           k = n;
/*     */         } 
/*     */       } 
/* 179 */       str = getTitle(this.frame.getTitle(), fontMetrics, k);
/*     */       
/* 181 */       if (xPStyle != null) {
/* 182 */         String str1 = null;
/* 183 */         if (bool) {
/* 184 */           str1 = xPStyle.getString(this, TMSchema.Part.WP_CAPTION, TMSchema.State.ACTIVE, TMSchema.Prop.TEXTSHADOWTYPE);
/*     */         }
/*     */         
/* 187 */         if ("single".equalsIgnoreCase(str1)) {
/* 188 */           Point point = xPStyle.getPoint(this, TMSchema.Part.WP_WINDOW, TMSchema.State.ACTIVE, TMSchema.Prop.TEXTSHADOWOFFSET);
/*     */           
/* 190 */           Color color = xPStyle.getColor(this, TMSchema.Part.WP_WINDOW, TMSchema.State.ACTIVE, TMSchema.Prop.TEXTSHADOWCOLOR, null);
/*     */           
/* 192 */           if (point != null && color != null) {
/* 193 */             paramGraphics.setColor(color);
/* 194 */             SwingUtilities2.drawString(this.frame, paramGraphics, str, j + point.x, i + point.y);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 200 */       paramGraphics.setColor(bool ? this.selectedTextColor : this.notSelectedTextColor);
/* 201 */       SwingUtilities2.drawString(this.frame, paramGraphics, str, j, i);
/* 202 */       paramGraphics.setFont(font1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 207 */     return getMinimumSize();
/*     */   }
/*     */   
/*     */   public Dimension getMinimumSize() {
/* 211 */     Dimension dimension = new Dimension(super.getMinimumSize());
/* 212 */     dimension.height = this.titlePaneHeight + 2;
/*     */     
/* 214 */     XPStyle xPStyle = XPStyle.getXP();
/* 215 */     if (xPStyle != null)
/*     */     {
/*     */       
/* 218 */       if (this.frame.isMaximum()) {
/* 219 */         dimension.height--;
/*     */       } else {
/* 221 */         dimension.height += 3;
/*     */       } 
/*     */     }
/* 224 */     return dimension;
/*     */   }
/*     */   
/*     */   protected void paintTitleBackground(Graphics paramGraphics) {
/* 228 */     XPStyle xPStyle = XPStyle.getXP();
/* 229 */     if (xPStyle != null) {
/*     */       
/* 231 */       TMSchema.Part part = this.frame.isIcon() ? TMSchema.Part.WP_MINCAPTION : (this.frame.isMaximum() ? TMSchema.Part.WP_MAXCAPTION : TMSchema.Part.WP_CAPTION);
/*     */       
/* 233 */       TMSchema.State state = this.frame.isSelected() ? TMSchema.State.ACTIVE : TMSchema.State.INACTIVE;
/* 234 */       XPStyle.Skin skin = xPStyle.getSkin(this, part);
/* 235 */       skin.paintSkin(paramGraphics, 0, 0, getWidth(), getHeight(), state);
/*     */     } else {
/* 237 */       Boolean bool = (Boolean)LookAndFeel.getDesktopPropertyValue("win.frame.captionGradientsOn", 
/* 238 */           Boolean.valueOf(false));
/* 239 */       if (bool.booleanValue() && paramGraphics instanceof Graphics2D) {
/* 240 */         Graphics2D graphics2D = (Graphics2D)paramGraphics;
/* 241 */         Paint paint = graphics2D.getPaint();
/*     */         
/* 243 */         boolean bool1 = this.frame.isSelected();
/* 244 */         int i = getWidth();
/*     */         
/* 246 */         if (bool1) {
/* 247 */           GradientPaint gradientPaint = new GradientPaint(0.0F, 0.0F, this.selectedTitleColor, (int)(i * 0.75D), 0.0F, this.selectedTitleGradientColor);
/*     */ 
/*     */ 
/*     */           
/* 251 */           graphics2D.setPaint(gradientPaint);
/*     */         } else {
/* 253 */           GradientPaint gradientPaint = new GradientPaint(0.0F, 0.0F, this.notSelectedTitleColor, (int)(i * 0.75D), 0.0F, this.notSelectedTitleGradientColor);
/*     */ 
/*     */ 
/*     */           
/* 257 */           graphics2D.setPaint(gradientPaint);
/*     */         } 
/* 259 */         graphics2D.fillRect(0, 0, getWidth(), getHeight());
/* 260 */         graphics2D.setPaint(paint);
/*     */       } else {
/* 262 */         super.paintTitleBackground(paramGraphics);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void assembleSystemMenu() {
/* 268 */     this.systemPopupMenu = new JPopupMenu();
/* 269 */     addSystemMenuItems(this.systemPopupMenu);
/* 270 */     enableActions();
/* 271 */     this.systemLabel = new JLabel(this.frame.getFrameIcon()) {
/*     */         protected void paintComponent(Graphics param1Graphics) {
/* 273 */           int i = 0;
/* 274 */           int j = 0;
/* 275 */           int k = getWidth();
/* 276 */           int m = getHeight();
/* 277 */           param1Graphics = param1Graphics.create();
/* 278 */           if (isOpaque()) {
/* 279 */             param1Graphics.setColor(getBackground());
/* 280 */             param1Graphics.fillRect(0, 0, k, m);
/*     */           } 
/* 282 */           Icon icon = getIcon();
/*     */           
/*     */           int n, i1;
/* 285 */           if (icon != null && (
/* 286 */             n = icon.getIconWidth()) > 0 && (
/* 287 */             i1 = icon.getIconHeight()) > 0) {
/*     */             double d;
/*     */ 
/*     */             
/* 291 */             if (n > i1) {
/*     */               
/* 293 */               j = (m - k * i1 / n) / 2;
/* 294 */               d = k / n;
/*     */             } else {
/*     */               
/* 297 */               i = (k - m * n / i1) / 2;
/* 298 */               d = m / i1;
/*     */             } 
/* 300 */             ((Graphics2D)param1Graphics).translate(i, j);
/* 301 */             ((Graphics2D)param1Graphics).scale(d, d);
/* 302 */             icon.paintIcon(this, param1Graphics, 0, 0);
/*     */           } 
/* 304 */           param1Graphics.dispose();
/*     */         }
/*     */       };
/* 307 */     this.systemLabel.addMouseListener(new MouseAdapter() {
/*     */           public void mouseClicked(MouseEvent param1MouseEvent) {
/* 309 */             if (param1MouseEvent.getClickCount() == 2 && WindowsInternalFrameTitlePane.this.frame.isClosable() && 
/* 310 */               !WindowsInternalFrameTitlePane.this.frame.isIcon()) {
/* 311 */               WindowsInternalFrameTitlePane.this.systemPopupMenu.setVisible(false);
/* 312 */               WindowsInternalFrameTitlePane.this.frame.doDefaultCloseAction();
/*     */             } else {
/*     */               
/* 315 */               super.mouseClicked(param1MouseEvent);
/*     */             } 
/*     */           }
/*     */           public void mousePressed(MouseEvent param1MouseEvent) {
/*     */             try {
/* 320 */               WindowsInternalFrameTitlePane.this.frame.setSelected(true);
/* 321 */             } catch (PropertyVetoException propertyVetoException) {}
/*     */             
/* 323 */             WindowsInternalFrameTitlePane.this.showSystemPopupMenu(param1MouseEvent.getComponent());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   protected void addSystemMenuItems(JPopupMenu paramJPopupMenu) {
/* 329 */     JMenuItem jMenuItem = paramJPopupMenu.add(this.restoreAction);
/* 330 */     jMenuItem.setMnemonic(getButtonMnemonic("restore"));
/* 331 */     jMenuItem = paramJPopupMenu.add(this.moveAction);
/* 332 */     jMenuItem.setMnemonic(getButtonMnemonic("move"));
/* 333 */     jMenuItem = paramJPopupMenu.add(this.sizeAction);
/* 334 */     jMenuItem.setMnemonic(getButtonMnemonic("size"));
/* 335 */     jMenuItem = paramJPopupMenu.add(this.iconifyAction);
/* 336 */     jMenuItem.setMnemonic(getButtonMnemonic("minimize"));
/* 337 */     jMenuItem = paramJPopupMenu.add(this.maximizeAction);
/* 338 */     jMenuItem.setMnemonic(getButtonMnemonic("maximize"));
/* 339 */     paramJPopupMenu.add(new JSeparator());
/* 340 */     jMenuItem = paramJPopupMenu.add(this.closeAction);
/* 341 */     jMenuItem.setMnemonic(getButtonMnemonic("close"));
/*     */   }
/*     */   
/*     */   private static int getButtonMnemonic(String paramString) {
/*     */     try {
/* 346 */       return Integer.parseInt(UIManager.getString("InternalFrameTitlePane." + paramString + "Button.mnemonic"));
/*     */     }
/* 348 */     catch (NumberFormatException numberFormatException) {
/* 349 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void showSystemMenu() {
/* 354 */     showSystemPopupMenu(this.systemLabel);
/*     */   }
/*     */   
/*     */   private void showSystemPopupMenu(Component paramComponent) {
/* 358 */     Dimension dimension = new Dimension();
/* 359 */     Border border = this.frame.getBorder();
/* 360 */     if (border != null) {
/* 361 */       dimension.width += (border.getBorderInsets(this.frame)).left + 
/* 362 */         (border.getBorderInsets(this.frame)).right;
/* 363 */       dimension.height += (border.getBorderInsets(this.frame)).bottom + 
/* 364 */         (border.getBorderInsets(this.frame)).top;
/*     */     } 
/* 366 */     if (!this.frame.isIcon()) {
/* 367 */       this.systemPopupMenu.show(paramComponent, 
/* 368 */           getX() - dimension.width, 
/* 369 */           getY() + getHeight() - dimension.height);
/*     */     } else {
/* 371 */       this.systemPopupMenu.show(paramComponent, 
/* 372 */           getX() - dimension.width, 
/* 373 */           getY() - (this.systemPopupMenu.getPreferredSize()).height - dimension.height);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected PropertyChangeListener createPropertyChangeListener() {
/* 379 */     return new WindowsPropertyChangeHandler();
/*     */   }
/*     */   
/*     */   protected LayoutManager createLayout() {
/* 383 */     return new WindowsTitlePaneLayout();
/*     */   }
/*     */   
/*     */   public class WindowsTitlePaneLayout extends BasicInternalFrameTitlePane.TitlePaneLayout {
/* 387 */     private Insets captionMargin = null;
/* 388 */     private Insets contentMargin = null;
/* 389 */     private XPStyle xp = XPStyle.getXP();
/*     */     
/*     */     WindowsTitlePaneLayout() {
/* 392 */       if (this.xp != null) {
/* 393 */         WindowsInternalFrameTitlePane windowsInternalFrameTitlePane = WindowsInternalFrameTitlePane.this;
/* 394 */         this.captionMargin = this.xp.getMargin(windowsInternalFrameTitlePane, TMSchema.Part.WP_CAPTION, null, TMSchema.Prop.CAPTIONMARGINS);
/* 395 */         this.contentMargin = this.xp.getMargin(windowsInternalFrameTitlePane, TMSchema.Part.WP_CAPTION, null, TMSchema.Prop.CONTENTMARGINS);
/*     */       } 
/* 397 */       if (this.captionMargin == null) {
/* 398 */         this.captionMargin = new Insets(0, 2, 0, 2);
/*     */       }
/* 400 */       if (this.contentMargin == null) {
/* 401 */         this.contentMargin = new Insets(0, 0, 0, 0);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private int layoutButton(JComponent param1JComponent, TMSchema.Part param1Part, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, boolean param1Boolean) {
/* 408 */       if (!param1Boolean) {
/* 409 */         param1Int1 -= param1Int3;
/*     */       }
/* 411 */       param1JComponent.setBounds(param1Int1, param1Int2, param1Int3, param1Int4);
/* 412 */       if (param1Boolean) {
/* 413 */         param1Int1 += param1Int3 + 2;
/*     */       } else {
/* 415 */         param1Int1 -= 2;
/*     */       } 
/* 417 */       return param1Int1;
/*     */     }
/*     */     public void layoutContainer(Container param1Container) {
/*     */       int i;
/* 421 */       boolean bool = WindowsGraphicsUtils.isLeftToRight(WindowsInternalFrameTitlePane.this.frame);
/*     */       
/* 423 */       int k = WindowsInternalFrameTitlePane.this.getWidth();
/* 424 */       int m = WindowsInternalFrameTitlePane.this.getHeight();
/*     */ 
/*     */ 
/*     */       
/* 428 */       int n = (this.xp != null) ? ((m - 2) * 6 / 10) : (m - 4);
/* 429 */       if (this.xp != null) {
/* 430 */         i = bool ? (this.captionMargin.left + 2) : (k - this.captionMargin.right - 2);
/*     */       } else {
/* 432 */         i = bool ? this.captionMargin.left : (k - this.captionMargin.right);
/*     */       } 
/* 434 */       int j = (m - n) / 2;
/* 435 */       layoutButton(WindowsInternalFrameTitlePane.this.systemLabel, TMSchema.Part.WP_SYSBUTTON, i, j, n, n, 0, bool);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 440 */       if (this.xp != null) {
/* 441 */         i = bool ? (k - this.captionMargin.right - 2) : (this.captionMargin.left + 2);
/* 442 */         j = 1;
/* 443 */         if (WindowsInternalFrameTitlePane.this.frame.isMaximum()) {
/* 444 */           j++;
/*     */         } else {
/* 446 */           j += 5;
/*     */         } 
/*     */       } else {
/* 449 */         i = bool ? (k - this.captionMargin.right) : this.captionMargin.left;
/* 450 */         j = (m - WindowsInternalFrameTitlePane.this.buttonHeight) / 2;
/*     */       } 
/*     */       
/* 453 */       if (WindowsInternalFrameTitlePane.this.frame.isClosable()) {
/* 454 */         i = layoutButton(WindowsInternalFrameTitlePane.this.closeButton, TMSchema.Part.WP_CLOSEBUTTON, i, j, WindowsInternalFrameTitlePane.this
/* 455 */             .buttonWidth, WindowsInternalFrameTitlePane.this.buttonHeight, 2, !bool);
/*     */       }
/*     */ 
/*     */       
/* 459 */       if (WindowsInternalFrameTitlePane.this.frame.isMaximizable()) {
/* 460 */         i = layoutButton(WindowsInternalFrameTitlePane.this.maxButton, TMSchema.Part.WP_MAXBUTTON, i, j, WindowsInternalFrameTitlePane.this
/* 461 */             .buttonWidth, WindowsInternalFrameTitlePane.this.buttonHeight, (this.xp != null) ? 2 : 0, !bool);
/*     */       }
/*     */ 
/*     */       
/* 465 */       if (WindowsInternalFrameTitlePane.this.frame.isIconifiable())
/* 466 */         layoutButton(WindowsInternalFrameTitlePane.this.iconButton, TMSchema.Part.WP_MINBUTTON, i, j, WindowsInternalFrameTitlePane.this
/* 467 */             .buttonWidth, WindowsInternalFrameTitlePane.this.buttonHeight, 0, !bool); 
/*     */     }
/*     */   }
/*     */   
/*     */   public class WindowsPropertyChangeHandler
/*     */     extends BasicInternalFrameTitlePane.PropertyChangeHandler
/*     */   {
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 475 */       String str = param1PropertyChangeEvent.getPropertyName();
/*     */ 
/*     */       
/* 478 */       if ("frameIcon".equals(str) && WindowsInternalFrameTitlePane.this
/* 479 */         .systemLabel != null) {
/* 480 */         WindowsInternalFrameTitlePane.this.systemLabel.setIcon(WindowsInternalFrameTitlePane.this.frame.getFrameIcon());
/*     */       }
/*     */       
/* 483 */       super.propertyChange(param1PropertyChangeEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ScalableIconUIResource
/*     */     implements Icon, UIResource
/*     */   {
/*     */     private static final int SIZE = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Icon[] icons;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ScalableIconUIResource(Object[] param1ArrayOfObject) {
/* 508 */       this.icons = new Icon[param1ArrayOfObject.length];
/*     */       
/* 510 */       for (byte b = 0; b < param1ArrayOfObject.length; b++) {
/* 511 */         if (param1ArrayOfObject[b] instanceof UIDefaults.LazyValue) {
/* 512 */           this.icons[b] = (Icon)((UIDefaults.LazyValue)param1ArrayOfObject[b]).createValue(null);
/*     */         } else {
/* 514 */           this.icons[b] = (Icon)param1ArrayOfObject[b];
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Icon getBestIcon(int param1Int) {
/* 523 */       if (this.icons != null && this.icons.length > 0) {
/* 524 */         byte b1 = 0;
/* 525 */         int i = Integer.MAX_VALUE;
/* 526 */         for (byte b2 = 0; b2 < this.icons.length; b2++) {
/* 527 */           Icon icon = this.icons[b2];
/*     */           int j;
/* 529 */           if (icon != null && (j = icon.getIconWidth()) > 0) {
/* 530 */             int k = Math.abs(j - param1Int);
/* 531 */             if (k < i) {
/* 532 */               i = k;
/* 533 */               b1 = b2;
/*     */             } 
/*     */           } 
/*     */         } 
/* 537 */         return this.icons[b1];
/*     */       } 
/* 539 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 544 */       Graphics2D graphics2D = (Graphics2D)param1Graphics.create();
/*     */ 
/*     */       
/* 547 */       int i = getIconWidth();
/* 548 */       double d = graphics2D.getTransform().getScaleX();
/* 549 */       Icon icon = getBestIcon((int)(i * d));
/*     */       int j;
/* 551 */       if (icon != null && (j = icon.getIconWidth()) > 0) {
/*     */         
/* 553 */         double d1 = i / j;
/* 554 */         graphics2D.translate(param1Int1, param1Int2);
/* 555 */         graphics2D.scale(d1, d1);
/* 556 */         icon.paintIcon(param1Component, graphics2D, 0, 0);
/*     */       } 
/* 558 */       graphics2D.dispose();
/*     */     }
/*     */     
/*     */     public int getIconWidth() {
/* 562 */       return 16;
/*     */     }
/*     */     
/*     */     public int getIconHeight() {
/* 566 */       return 16;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsInternalFrameTitlePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */