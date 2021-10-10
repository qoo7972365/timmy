/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.plaf.ColorUIResource;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalInternalFrameTitlePane
/*     */   extends BasicInternalFrameTitlePane
/*     */ {
/*     */   protected boolean isPalette = false;
/*     */   protected Icon paletteCloseIcon;
/*     */   protected int paletteTitleHeight;
/*  53 */   private static final Border handyEmptyBorder = new EmptyBorder(0, 0, 0, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String selectedBackgroundKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String selectedForegroundKey;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String selectedShadowKey;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean wasClosable;
/*     */ 
/*     */ 
/*     */   
/*  76 */   int buttonsWidth = 0;
/*     */   
/*  78 */   MetalBumps activeBumps = new MetalBumps(0, 0, 
/*     */       
/*  80 */       MetalLookAndFeel.getPrimaryControlHighlight(), 
/*  81 */       MetalLookAndFeel.getPrimaryControlDarkShadow(), 
/*  82 */       (UIManager.get("InternalFrame.activeTitleGradient") != null) ? null : 
/*  83 */       MetalLookAndFeel.getPrimaryControl());
/*  84 */   MetalBumps inactiveBumps = new MetalBumps(0, 0, 
/*     */       
/*  86 */       MetalLookAndFeel.getControlHighlight(), 
/*  87 */       MetalLookAndFeel.getControlDarkShadow(), 
/*  88 */       (UIManager.get("InternalFrame.inactiveTitleGradient") != null) ? null : 
/*  89 */       MetalLookAndFeel.getControl());
/*     */   
/*     */   MetalBumps paletteBumps;
/*     */   
/*  93 */   private Color activeBumpsHighlight = MetalLookAndFeel.getPrimaryControlHighlight();
/*     */   
/*  95 */   private Color activeBumpsShadow = MetalLookAndFeel.getPrimaryControlDarkShadow();
/*     */   
/*     */   public MetalInternalFrameTitlePane(JInternalFrame paramJInternalFrame) {
/*  98 */     super(paramJInternalFrame);
/*     */   }
/*     */   
/*     */   public void addNotify() {
/* 102 */     super.addNotify();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     updateOptionPaneState();
/*     */   }
/*     */   
/*     */   protected void installDefaults() {
/* 111 */     super.installDefaults();
/* 112 */     setFont(UIManager.getFont("InternalFrame.titleFont"));
/* 113 */     this
/* 114 */       .paletteTitleHeight = UIManager.getInt("InternalFrame.paletteTitleHeight");
/* 115 */     this.paletteCloseIcon = UIManager.getIcon("InternalFrame.paletteCloseIcon");
/* 116 */     this.wasClosable = this.frame.isClosable();
/* 117 */     this.selectedForegroundKey = this.selectedBackgroundKey = null;
/* 118 */     if (MetalLookAndFeel.usingOcean()) {
/* 119 */       setOpaque(true);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults() {
/* 124 */     super.uninstallDefaults();
/* 125 */     if (this.wasClosable != this.frame.isClosable()) {
/* 126 */       this.frame.setClosable(this.wasClosable);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void createButtons() {
/* 131 */     super.createButtons();
/*     */     
/* 133 */     Boolean bool = this.frame.isSelected() ? Boolean.TRUE : Boolean.FALSE;
/* 134 */     this.iconButton.putClientProperty("paintActive", bool);
/* 135 */     this.iconButton.setBorder(handyEmptyBorder);
/*     */     
/* 137 */     this.maxButton.putClientProperty("paintActive", bool);
/* 138 */     this.maxButton.setBorder(handyEmptyBorder);
/*     */     
/* 140 */     this.closeButton.putClientProperty("paintActive", bool);
/* 141 */     this.closeButton.setBorder(handyEmptyBorder);
/*     */ 
/*     */ 
/*     */     
/* 145 */     this.closeButton.setBackground(MetalLookAndFeel.getPrimaryControlShadow());
/*     */     
/* 147 */     if (MetalLookAndFeel.usingOcean()) {
/* 148 */       this.iconButton.setContentAreaFilled(false);
/* 149 */       this.maxButton.setContentAreaFilled(false);
/* 150 */       this.closeButton.setContentAreaFilled(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void assembleSystemMenu() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addSystemMenuItems(JMenu paramJMenu) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void showSystemMenu() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addSubComponents() {
/* 177 */     add(this.iconButton);
/* 178 */     add(this.maxButton);
/* 179 */     add(this.closeButton);
/*     */   }
/*     */   
/*     */   protected PropertyChangeListener createPropertyChangeListener() {
/* 183 */     return new MetalPropertyChangeHandler();
/*     */   }
/*     */   
/*     */   protected LayoutManager createLayout() {
/* 187 */     return new MetalTitlePaneLayout();
/*     */   }
/*     */   
/*     */   class MetalPropertyChangeHandler
/*     */     extends BasicInternalFrameTitlePane.PropertyChangeHandler
/*     */   {
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 194 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 195 */       if (str.equals("selected")) {
/* 196 */         Boolean bool = (Boolean)param1PropertyChangeEvent.getNewValue();
/* 197 */         MetalInternalFrameTitlePane.this.iconButton.putClientProperty("paintActive", bool);
/* 198 */         MetalInternalFrameTitlePane.this.closeButton.putClientProperty("paintActive", bool);
/* 199 */         MetalInternalFrameTitlePane.this.maxButton.putClientProperty("paintActive", bool);
/*     */       }
/* 201 */       else if ("JInternalFrame.messageType".equals(str)) {
/* 202 */         MetalInternalFrameTitlePane.this.updateOptionPaneState();
/* 203 */         MetalInternalFrameTitlePane.this.frame.repaint();
/*     */       } 
/* 205 */       super.propertyChange(param1PropertyChangeEvent);
/*     */     } }
/*     */   
/*     */   class MetalTitlePaneLayout extends BasicInternalFrameTitlePane.TitlePaneLayout { public void addLayoutComponent(String param1String, Component param1Component) {}
/*     */     
/*     */     public void removeLayoutComponent(Component param1Component) {}
/*     */     
/*     */     public Dimension preferredLayoutSize(Container param1Container) {
/* 213 */       return minimumLayoutSize(param1Container);
/*     */     }
/*     */ 
/*     */     
/*     */     public Dimension minimumLayoutSize(Container param1Container) {
/* 218 */       int j, i = 30;
/* 219 */       if (MetalInternalFrameTitlePane.this.frame.isClosable()) {
/* 220 */         i += 21;
/*     */       }
/* 222 */       if (MetalInternalFrameTitlePane.this.frame.isMaximizable()) {
/* 223 */         i += 16 + (MetalInternalFrameTitlePane.this.frame.isClosable() ? 10 : 4);
/*     */       }
/* 225 */       if (MetalInternalFrameTitlePane.this.frame.isIconifiable()) {
/* 226 */         i += 16 + (MetalInternalFrameTitlePane.this.frame.isMaximizable() ? 2 : (
/* 227 */           MetalInternalFrameTitlePane.this.frame.isClosable() ? 10 : 4));
/*     */       }
/* 229 */       FontMetrics fontMetrics = MetalInternalFrameTitlePane.this.frame.getFontMetrics(MetalInternalFrameTitlePane.this.getFont());
/* 230 */       String str = MetalInternalFrameTitlePane.this.frame.getTitle();
/* 231 */       byte b1 = (str != null) ? SwingUtilities2.stringWidth(MetalInternalFrameTitlePane.this
/* 232 */           .frame, fontMetrics, str) : 0;
/* 233 */       byte b2 = (str != null) ? str.length() : 0;
/*     */       
/* 235 */       if (b2 > 2) {
/* 236 */         j = SwingUtilities2.stringWidth(MetalInternalFrameTitlePane.this.frame, fontMetrics, MetalInternalFrameTitlePane.this
/* 237 */             .frame.getTitle().substring(0, 2) + "...");
/* 238 */         i += (b1 < j) ? b1 : j;
/*     */       } else {
/*     */         
/* 241 */         i += b1;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 246 */       if (MetalInternalFrameTitlePane.this.isPalette) {
/* 247 */         j = MetalInternalFrameTitlePane.this.paletteTitleHeight;
/*     */       } else {
/* 249 */         int k = fontMetrics.getHeight();
/* 250 */         k += 7;
/* 251 */         Icon icon = MetalInternalFrameTitlePane.this.frame.getFrameIcon();
/* 252 */         int m = 0;
/* 253 */         if (icon != null)
/*     */         {
/* 255 */           m = Math.min(icon.getIconHeight(), 16);
/*     */         }
/* 257 */         m += 5;
/* 258 */         j = Math.max(k, m);
/*     */       } 
/*     */       
/* 261 */       return new Dimension(i, j);
/*     */     }
/*     */     
/*     */     public void layoutContainer(Container param1Container) {
/* 265 */       boolean bool = MetalUtils.isLeftToRight(MetalInternalFrameTitlePane.this.frame);
/*     */       
/* 267 */       int i = MetalInternalFrameTitlePane.this.getWidth();
/* 268 */       int j = bool ? i : 0;
/* 269 */       byte b = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 274 */       int k = MetalInternalFrameTitlePane.this.closeButton.getIcon().getIconHeight();
/* 275 */       int m = MetalInternalFrameTitlePane.this.closeButton.getIcon().getIconWidth();
/*     */       
/* 277 */       if (MetalInternalFrameTitlePane.this.frame.isClosable()) {
/* 278 */         if (MetalInternalFrameTitlePane.this.isPalette) {
/* 279 */           byte b1 = 3;
/* 280 */           j += bool ? (-b1 - m + 2) : b1;
/* 281 */           MetalInternalFrameTitlePane.this.closeButton.setBounds(j, b, m + 2, MetalInternalFrameTitlePane.this.getHeight() - 4);
/* 282 */           if (!bool) j += m + 2; 
/*     */         } else {
/* 284 */           byte b1 = 4;
/* 285 */           j += bool ? (-b1 - m) : b1;
/* 286 */           MetalInternalFrameTitlePane.this.closeButton.setBounds(j, b, m, k);
/* 287 */           if (!bool) j += m;
/*     */         
/*     */         } 
/*     */       }
/* 291 */       if (MetalInternalFrameTitlePane.this.frame.isMaximizable() && !MetalInternalFrameTitlePane.this.isPalette) {
/* 292 */         byte b1 = MetalInternalFrameTitlePane.this.frame.isClosable() ? 10 : 4;
/* 293 */         j += bool ? (-b1 - m) : b1;
/* 294 */         MetalInternalFrameTitlePane.this.maxButton.setBounds(j, b, m, k);
/* 295 */         if (!bool) j += m;
/*     */       
/*     */       } 
/* 298 */       if (MetalInternalFrameTitlePane.this.frame.isIconifiable() && !MetalInternalFrameTitlePane.this.isPalette) {
/*     */         
/* 300 */         byte b1 = MetalInternalFrameTitlePane.this.frame.isMaximizable() ? 2 : (MetalInternalFrameTitlePane.this.frame.isClosable() ? 10 : 4);
/* 301 */         j += bool ? (-b1 - m) : b1;
/* 302 */         MetalInternalFrameTitlePane.this.iconButton.setBounds(j, b, m, k);
/* 303 */         if (!bool) j += m;
/*     */       
/*     */       } 
/* 306 */       MetalInternalFrameTitlePane.this.buttonsWidth = bool ? (i - j) : j;
/*     */     } }
/*     */ 
/*     */   
/*     */   public void paintPalette(Graphics paramGraphics) {
/* 311 */     boolean bool = MetalUtils.isLeftToRight(this.frame);
/*     */     
/* 313 */     int i = getWidth();
/* 314 */     int j = getHeight();
/*     */     
/* 316 */     if (this.paletteBumps == null) {
/* 317 */       this
/*     */ 
/*     */ 
/*     */         
/* 321 */         .paletteBumps = new MetalBumps(0, 0, MetalLookAndFeel.getPrimaryControlHighlight(), MetalLookAndFeel.getPrimaryControlInfo(), MetalLookAndFeel.getPrimaryControlShadow());
/*     */     }
/*     */     
/* 324 */     ColorUIResource colorUIResource1 = MetalLookAndFeel.getPrimaryControlShadow();
/* 325 */     ColorUIResource colorUIResource2 = MetalLookAndFeel.getPrimaryControlDarkShadow();
/*     */     
/* 327 */     paramGraphics.setColor(colorUIResource1);
/* 328 */     paramGraphics.fillRect(0, 0, i, j);
/*     */     
/* 330 */     paramGraphics.setColor(colorUIResource2);
/* 331 */     paramGraphics.drawLine(0, j - 1, i, j - 1);
/*     */     
/* 333 */     boolean bool1 = bool ? true : (this.buttonsWidth + 4);
/* 334 */     int k = i - this.buttonsWidth - 8;
/* 335 */     int m = getHeight() - 4;
/* 336 */     this.paletteBumps.setBumpArea(k, m);
/* 337 */     this.paletteBumps.paintIcon(this, paramGraphics, bool1, 2); } public void paintComponent(Graphics paramGraphics) {
/*     */     MetalBumps metalBumps;
/*     */     String str1;
/*     */     int m, n;
/* 341 */     if (this.isPalette) {
/* 342 */       paintPalette(paramGraphics);
/*     */       
/*     */       return;
/*     */     } 
/* 346 */     boolean bool1 = MetalUtils.isLeftToRight(this.frame);
/* 347 */     boolean bool2 = this.frame.isSelected();
/*     */     
/* 349 */     int i = getWidth();
/* 350 */     int j = getHeight();
/*     */     
/* 352 */     Color color1 = null;
/* 353 */     Color color2 = null;
/* 354 */     Color color3 = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 359 */     if (bool2) {
/* 360 */       if (!MetalLookAndFeel.usingOcean()) {
/* 361 */         this.closeButton.setContentAreaFilled(true);
/* 362 */         this.maxButton.setContentAreaFilled(true);
/* 363 */         this.iconButton.setContentAreaFilled(true);
/*     */       } 
/* 365 */       if (this.selectedBackgroundKey != null) {
/* 366 */         color1 = UIManager.getColor(this.selectedBackgroundKey);
/*     */       }
/* 368 */       if (color1 == null) {
/* 369 */         color1 = MetalLookAndFeel.getWindowTitleBackground();
/*     */       }
/* 371 */       if (this.selectedForegroundKey != null) {
/* 372 */         color2 = UIManager.getColor(this.selectedForegroundKey);
/*     */       }
/* 374 */       if (this.selectedShadowKey != null) {
/* 375 */         color3 = UIManager.getColor(this.selectedShadowKey);
/*     */       }
/* 377 */       if (color3 == null) {
/* 378 */         color3 = MetalLookAndFeel.getPrimaryControlDarkShadow();
/*     */       }
/* 380 */       if (color2 == null) {
/* 381 */         color2 = MetalLookAndFeel.getWindowTitleForeground();
/*     */       }
/* 383 */       this.activeBumps.setBumpColors(this.activeBumpsHighlight, this.activeBumpsShadow, 
/* 384 */           (UIManager.get("InternalFrame.activeTitleGradient") != null) ? null : color1);
/*     */       
/* 386 */       metalBumps = this.activeBumps;
/* 387 */       str1 = "InternalFrame.activeTitleGradient";
/*     */     } else {
/* 389 */       if (!MetalLookAndFeel.usingOcean()) {
/* 390 */         this.closeButton.setContentAreaFilled(false);
/* 391 */         this.maxButton.setContentAreaFilled(false);
/* 392 */         this.iconButton.setContentAreaFilled(false);
/*     */       } 
/* 394 */       color1 = MetalLookAndFeel.getWindowTitleInactiveBackground();
/* 395 */       color2 = MetalLookAndFeel.getWindowTitleInactiveForeground();
/* 396 */       color3 = MetalLookAndFeel.getControlDarkShadow();
/* 397 */       metalBumps = this.inactiveBumps;
/* 398 */       str1 = "InternalFrame.inactiveTitleGradient";
/*     */     } 
/*     */     
/* 401 */     if (!MetalUtils.drawGradient(this, paramGraphics, str1, 0, 0, i, j, true)) {
/*     */       
/* 403 */       paramGraphics.setColor(color1);
/* 404 */       paramGraphics.fillRect(0, 0, i, j);
/*     */     } 
/*     */     
/* 407 */     paramGraphics.setColor(color3);
/* 408 */     paramGraphics.drawLine(0, j - 1, i, j - 1);
/* 409 */     paramGraphics.drawLine(0, 0, 0, 0);
/* 410 */     paramGraphics.drawLine(i - 1, 0, i - 1, 0);
/*     */ 
/*     */ 
/*     */     
/* 414 */     int k = bool1 ? 5 : (i - 5);
/* 415 */     String str2 = this.frame.getTitle();
/*     */     
/* 417 */     Icon icon = this.frame.getFrameIcon();
/* 418 */     if (icon != null) {
/* 419 */       if (!bool1)
/* 420 */         k -= icon.getIconWidth(); 
/* 421 */       m = j / 2 - icon.getIconHeight() / 2;
/* 422 */       icon.paintIcon(this.frame, paramGraphics, k, m);
/* 423 */       k += bool1 ? (icon.getIconWidth() + 5) : -5;
/*     */     } 
/*     */     
/* 426 */     if (str2 != null) {
/* 427 */       Font font = getFont();
/* 428 */       paramGraphics.setFont(font);
/* 429 */       FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(this.frame, paramGraphics, font);
/* 430 */       int i3 = fontMetrics.getHeight();
/*     */       
/* 432 */       paramGraphics.setColor(color2);
/*     */       
/* 434 */       int i4 = (j - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
/*     */       
/* 436 */       Rectangle rectangle = new Rectangle(0, 0, 0, 0);
/* 437 */       if (this.frame.isIconifiable()) { rectangle = this.iconButton.getBounds(); }
/* 438 */       else if (this.frame.isMaximizable()) { rectangle = this.maxButton.getBounds(); }
/* 439 */       else if (this.frame.isClosable()) { rectangle = this.closeButton.getBounds(); }
/*     */ 
/*     */       
/* 442 */       if (bool1) {
/* 443 */         if (rectangle.x == 0) {
/* 444 */           rectangle.x = this.frame.getWidth() - (this.frame.getInsets()).right - 2;
/*     */         }
/* 446 */         int i5 = rectangle.x - k - 4;
/* 447 */         str2 = getTitle(str2, fontMetrics, i5);
/*     */       } else {
/* 449 */         int i5 = k - rectangle.x - rectangle.width - 4;
/* 450 */         str2 = getTitle(str2, fontMetrics, i5);
/* 451 */         k -= SwingUtilities2.stringWidth(this.frame, fontMetrics, str2);
/*     */       } 
/*     */       
/* 454 */       int i2 = SwingUtilities2.stringWidth(this.frame, fontMetrics, str2);
/* 455 */       SwingUtilities2.drawString(this.frame, paramGraphics, str2, k, i4);
/* 456 */       k += bool1 ? (i2 + 5) : -5;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 461 */     if (bool1) {
/* 462 */       n = i - this.buttonsWidth - k - 5;
/* 463 */       m = k;
/*     */     } else {
/* 465 */       n = k - this.buttonsWidth - 5;
/* 466 */       m = this.buttonsWidth + 5;
/*     */     } 
/* 468 */     byte b = 3;
/* 469 */     int i1 = getHeight() - 2 * b;
/* 470 */     metalBumps.setBumpArea(n, i1);
/* 471 */     metalBumps.paintIcon(this, paramGraphics, m, b);
/*     */   }
/*     */   
/*     */   public void setPalette(boolean paramBoolean) {
/* 475 */     this.isPalette = paramBoolean;
/*     */     
/* 477 */     if (this.isPalette) {
/* 478 */       this.closeButton.setIcon(this.paletteCloseIcon);
/* 479 */       if (this.frame.isMaximizable())
/* 480 */         remove(this.maxButton); 
/* 481 */       if (this.frame.isIconifiable())
/* 482 */         remove(this.iconButton); 
/*     */     } else {
/* 484 */       this.closeButton.setIcon(this.closeIcon);
/* 485 */       if (this.frame.isMaximizable())
/* 486 */         add(this.maxButton); 
/* 487 */       if (this.frame.isIconifiable())
/* 488 */         add(this.iconButton); 
/*     */     } 
/* 490 */     revalidate();
/* 491 */     repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateOptionPaneState() {
/* 499 */     int i = -2;
/* 500 */     boolean bool = this.wasClosable;
/* 501 */     Object object = this.frame.getClientProperty("JInternalFrame.messageType");
/*     */     
/* 503 */     if (object == null) {
/*     */       return;
/*     */     }
/*     */     
/* 507 */     if (object instanceof Integer) {
/* 508 */       i = ((Integer)object).intValue();
/*     */     }
/* 510 */     switch (i) {
/*     */       case 0:
/* 512 */         this.selectedBackgroundKey = "OptionPane.errorDialog.titlePane.background";
/*     */         
/* 514 */         this.selectedForegroundKey = "OptionPane.errorDialog.titlePane.foreground";
/*     */         
/* 516 */         this.selectedShadowKey = "OptionPane.errorDialog.titlePane.shadow";
/* 517 */         bool = false;
/*     */         break;
/*     */       case 3:
/* 520 */         this.selectedBackgroundKey = "OptionPane.questionDialog.titlePane.background";
/*     */         
/* 522 */         this.selectedForegroundKey = "OptionPane.questionDialog.titlePane.foreground";
/*     */         
/* 524 */         this.selectedShadowKey = "OptionPane.questionDialog.titlePane.shadow";
/*     */         
/* 526 */         bool = false;
/*     */         break;
/*     */       case 2:
/* 529 */         this.selectedBackgroundKey = "OptionPane.warningDialog.titlePane.background";
/*     */         
/* 531 */         this.selectedForegroundKey = "OptionPane.warningDialog.titlePane.foreground";
/*     */         
/* 533 */         this.selectedShadowKey = "OptionPane.warningDialog.titlePane.shadow";
/* 534 */         bool = false;
/*     */         break;
/*     */       case -1:
/*     */       case 1:
/* 538 */         this.selectedBackgroundKey = this.selectedForegroundKey = this.selectedShadowKey = null;
/*     */         
/* 540 */         bool = false;
/*     */         break;
/*     */       default:
/* 543 */         this.selectedBackgroundKey = this.selectedForegroundKey = this.selectedShadowKey = null;
/*     */         break;
/*     */     } 
/*     */     
/* 547 */     if (bool != this.frame.isClosable())
/* 548 */       this.frame.setClosable(bool); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalInternalFrameTitlePane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */