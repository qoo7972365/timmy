/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JSplitPane;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.border.BevelBorder;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.plaf.BorderUIResource;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.text.JTextComponent;
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
/*     */ public class BasicBorders
/*     */ {
/*     */   public static Border getButtonBorder() {
/*  51 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/*  52 */     return new BorderUIResource.CompoundBorderUIResource(new ButtonBorder(uIDefaults
/*     */           
/*  54 */           .getColor("Button.shadow"), uIDefaults
/*  55 */           .getColor("Button.darkShadow"), uIDefaults
/*  56 */           .getColor("Button.light"), uIDefaults
/*  57 */           .getColor("Button.highlight")), new MarginBorder());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border getRadioButtonBorder() {
/*  63 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/*  64 */     return new BorderUIResource.CompoundBorderUIResource(new RadioButtonBorder(uIDefaults
/*     */           
/*  66 */           .getColor("RadioButton.shadow"), uIDefaults
/*  67 */           .getColor("RadioButton.darkShadow"), uIDefaults
/*  68 */           .getColor("RadioButton.light"), uIDefaults
/*  69 */           .getColor("RadioButton.highlight")), new MarginBorder());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border getToggleButtonBorder() {
/*  75 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/*  76 */     return new BorderUIResource.CompoundBorderUIResource(new ToggleButtonBorder(uIDefaults
/*     */           
/*  78 */           .getColor("ToggleButton.shadow"), uIDefaults
/*  79 */           .getColor("ToggleButton.darkShadow"), uIDefaults
/*  80 */           .getColor("ToggleButton.light"), uIDefaults
/*  81 */           .getColor("ToggleButton.highlight")), new MarginBorder());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border getMenuBarBorder() {
/*  87 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/*  88 */     return new MenuBarBorder(uIDefaults
/*  89 */         .getColor("MenuBar.shadow"), uIDefaults
/*  90 */         .getColor("MenuBar.highlight"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border getSplitPaneBorder() {
/*  96 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/*  97 */     return new SplitPaneBorder(uIDefaults
/*  98 */         .getColor("SplitPane.highlight"), uIDefaults
/*  99 */         .getColor("SplitPane.darkShadow"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Border getSplitPaneDividerBorder() {
/* 108 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/* 109 */     return new SplitPaneDividerBorder(uIDefaults
/* 110 */         .getColor("SplitPane.highlight"), uIDefaults
/* 111 */         .getColor("SplitPane.darkShadow"));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Border getTextFieldBorder() {
/* 116 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/* 117 */     return new FieldBorder(uIDefaults
/* 118 */         .getColor("TextField.shadow"), uIDefaults
/* 119 */         .getColor("TextField.darkShadow"), uIDefaults
/* 120 */         .getColor("TextField.light"), uIDefaults
/* 121 */         .getColor("TextField.highlight"));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Border getProgressBarBorder() {
/* 126 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/* 127 */     return new BorderUIResource.LineBorderUIResource(Color.green, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Border getInternalFrameBorder() {
/* 132 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/* 133 */     return new BorderUIResource.CompoundBorderUIResource(new BevelBorder(0, uIDefaults
/*     */           
/* 135 */           .getColor("InternalFrame.borderLight"), uIDefaults
/* 136 */           .getColor("InternalFrame.borderHighlight"), uIDefaults
/* 137 */           .getColor("InternalFrame.borderDarkShadow"), uIDefaults
/* 138 */           .getColor("InternalFrame.borderShadow")), 
/* 139 */         BorderFactory.createLineBorder(uIDefaults
/* 140 */           .getColor("InternalFrame.borderColor"), 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class RolloverButtonBorder
/*     */     extends ButtonBorder
/*     */   {
/*     */     public RolloverButtonBorder(Color param1Color1, Color param1Color2, Color param1Color3, Color param1Color4) {
/* 153 */       super(param1Color1, param1Color2, param1Color3, param1Color4);
/*     */     }
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 157 */       AbstractButton abstractButton = (AbstractButton)param1Component;
/* 158 */       ButtonModel buttonModel = abstractButton.getModel();
/*     */       
/* 160 */       Color color = this.shadow;
/* 161 */       Container container = abstractButton.getParent();
/* 162 */       if (container != null && container.getBackground().equals(this.shadow)) {
/* 163 */         color = this.darkShadow;
/*     */       }
/*     */       
/* 166 */       if ((buttonModel.isRollover() && (!buttonModel.isPressed() || buttonModel.isArmed())) || buttonModel
/* 167 */         .isSelected()) {
/*     */         
/* 169 */         Color color1 = param1Graphics.getColor();
/* 170 */         param1Graphics.translate(param1Int1, param1Int2);
/*     */         
/* 172 */         if ((buttonModel.isPressed() && buttonModel.isArmed()) || buttonModel.isSelected()) {
/*     */           
/* 174 */           param1Graphics.setColor(color);
/* 175 */           param1Graphics.drawRect(0, 0, param1Int3 - 1, param1Int4 - 1);
/* 176 */           param1Graphics.setColor(this.lightHighlight);
/* 177 */           param1Graphics.drawLine(param1Int3 - 1, 0, param1Int3 - 1, param1Int4 - 1);
/* 178 */           param1Graphics.drawLine(0, param1Int4 - 1, param1Int3 - 1, param1Int4 - 1);
/*     */         } else {
/*     */           
/* 181 */           param1Graphics.setColor(this.lightHighlight);
/* 182 */           param1Graphics.drawRect(0, 0, param1Int3 - 1, param1Int4 - 1);
/* 183 */           param1Graphics.setColor(color);
/* 184 */           param1Graphics.drawLine(param1Int3 - 1, 0, param1Int3 - 1, param1Int4 - 1);
/* 185 */           param1Graphics.drawLine(0, param1Int4 - 1, param1Int3 - 1, param1Int4 - 1);
/*     */         } 
/* 187 */         param1Graphics.translate(-param1Int1, -param1Int2);
/* 188 */         param1Graphics.setColor(color1);
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
/*     */   
/*     */   static class RolloverMarginBorder
/*     */     extends EmptyBorder
/*     */   {
/*     */     public RolloverMarginBorder() {
/* 204 */       super(3, 3, 3, 3);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 208 */       Insets insets = null;
/*     */       
/* 210 */       if (param1Component instanceof AbstractButton) {
/* 211 */         insets = ((AbstractButton)param1Component).getMargin();
/*     */       }
/* 213 */       if (insets == null || insets instanceof UIResource) {
/*     */         
/* 215 */         param1Insets.left = this.left;
/* 216 */         param1Insets.top = this.top;
/* 217 */         param1Insets.right = this.right;
/* 218 */         param1Insets.bottom = this.bottom;
/*     */       } else {
/*     */         
/* 221 */         param1Insets.left = insets.left;
/* 222 */         param1Insets.top = insets.top;
/* 223 */         param1Insets.right = insets.right;
/* 224 */         param1Insets.bottom = insets.bottom;
/*     */       } 
/* 226 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ButtonBorder
/*     */     extends AbstractBorder implements UIResource {
/*     */     protected Color shadow;
/*     */     protected Color darkShadow;
/*     */     protected Color highlight;
/*     */     protected Color lightHighlight;
/*     */     
/*     */     public ButtonBorder(Color param1Color1, Color param1Color2, Color param1Color3, Color param1Color4) {
/* 238 */       this.shadow = param1Color1;
/* 239 */       this.darkShadow = param1Color2;
/* 240 */       this.highlight = param1Color3;
/* 241 */       this.lightHighlight = param1Color4;
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 246 */       boolean bool = false;
/* 247 */       boolean bool1 = false;
/*     */       
/* 249 */       if (param1Component instanceof AbstractButton) {
/* 250 */         AbstractButton abstractButton = (AbstractButton)param1Component;
/* 251 */         ButtonModel buttonModel = abstractButton.getModel();
/*     */         
/* 253 */         bool = (buttonModel.isPressed() && buttonModel.isArmed()) ? true : false;
/*     */         
/* 255 */         if (param1Component instanceof JButton) {
/* 256 */           bool1 = ((JButton)param1Component).isDefaultButton();
/*     */         }
/*     */       } 
/* 259 */       BasicGraphicsUtils.drawBezel(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, bool, bool1, this.shadow, this.darkShadow, this.highlight, this.lightHighlight);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 266 */       param1Insets.set(2, 3, 3, 3);
/* 267 */       return param1Insets;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ToggleButtonBorder
/*     */     extends ButtonBorder
/*     */   {
/*     */     public ToggleButtonBorder(Color param1Color1, Color param1Color2, Color param1Color3, Color param1Color4) {
/* 276 */       super(param1Color1, param1Color2, param1Color3, param1Color4);
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 281 */       BasicGraphicsUtils.drawBezel(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, false, false, this.shadow, this.darkShadow, this.highlight, this.lightHighlight);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 288 */       param1Insets.set(2, 2, 2, 2);
/* 289 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RadioButtonBorder
/*     */     extends ButtonBorder
/*     */   {
/*     */     public RadioButtonBorder(Color param1Color1, Color param1Color2, Color param1Color3, Color param1Color4) {
/* 297 */       super(param1Color1, param1Color2, param1Color3, param1Color4);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 303 */       if (param1Component instanceof AbstractButton) {
/* 304 */         AbstractButton abstractButton = (AbstractButton)param1Component;
/* 305 */         ButtonModel buttonModel = abstractButton.getModel();
/*     */         
/* 307 */         if ((buttonModel.isArmed() && buttonModel.isPressed()) || buttonModel.isSelected()) {
/* 308 */           BasicGraphicsUtils.drawLoweredBezel(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, this.shadow, this.darkShadow, this.highlight, this.lightHighlight);
/*     */         }
/*     */         else {
/*     */           
/* 312 */           BasicGraphicsUtils.drawBezel(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, false, (abstractButton
/* 313 */               .isFocusPainted() && abstractButton.hasFocus()), this.shadow, this.darkShadow, this.highlight, this.lightHighlight);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 318 */         BasicGraphicsUtils.drawBezel(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, false, false, this.shadow, this.darkShadow, this.highlight, this.lightHighlight);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 324 */       param1Insets.set(2, 2, 2, 2);
/* 325 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MenuBarBorder extends AbstractBorder implements UIResource {
/*     */     private Color shadow;
/*     */     private Color highlight;
/*     */     
/*     */     public MenuBarBorder(Color param1Color1, Color param1Color2) {
/* 334 */       this.shadow = param1Color1;
/* 335 */       this.highlight = param1Color2;
/*     */     }
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 339 */       Color color = param1Graphics.getColor();
/* 340 */       param1Graphics.translate(param1Int1, param1Int2);
/* 341 */       param1Graphics.setColor(this.shadow);
/* 342 */       SwingUtilities2.drawHLine(param1Graphics, 0, param1Int3 - 1, param1Int4 - 2);
/* 343 */       param1Graphics.setColor(this.highlight);
/* 344 */       SwingUtilities2.drawHLine(param1Graphics, 0, param1Int3 - 1, param1Int4 - 1);
/* 345 */       param1Graphics.translate(-param1Int1, -param1Int2);
/* 346 */       param1Graphics.setColor(color);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 350 */       param1Insets.set(0, 0, 2, 0);
/* 351 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MarginBorder extends AbstractBorder implements UIResource {
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 357 */       Insets insets = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 363 */       if (param1Component instanceof AbstractButton) {
/* 364 */         AbstractButton abstractButton = (AbstractButton)param1Component;
/* 365 */         insets = abstractButton.getMargin();
/* 366 */       } else if (param1Component instanceof JToolBar) {
/* 367 */         JToolBar jToolBar = (JToolBar)param1Component;
/* 368 */         insets = jToolBar.getMargin();
/* 369 */       } else if (param1Component instanceof JTextComponent) {
/* 370 */         JTextComponent jTextComponent = (JTextComponent)param1Component;
/* 371 */         insets = jTextComponent.getMargin();
/*     */       } 
/* 373 */       param1Insets.top = (insets != null) ? insets.top : 0;
/* 374 */       param1Insets.left = (insets != null) ? insets.left : 0;
/* 375 */       param1Insets.bottom = (insets != null) ? insets.bottom : 0;
/* 376 */       param1Insets.right = (insets != null) ? insets.right : 0;
/*     */       
/* 378 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class FieldBorder
/*     */     extends AbstractBorder implements UIResource {
/*     */     protected Color shadow;
/*     */     protected Color darkShadow;
/*     */     protected Color highlight;
/*     */     protected Color lightHighlight;
/*     */     
/*     */     public FieldBorder(Color param1Color1, Color param1Color2, Color param1Color3, Color param1Color4) {
/* 390 */       this.shadow = param1Color1;
/* 391 */       this.highlight = param1Color3;
/* 392 */       this.darkShadow = param1Color2;
/* 393 */       this.lightHighlight = param1Color4;
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 398 */       BasicGraphicsUtils.drawEtchedRect(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, this.shadow, this.darkShadow, this.highlight, this.lightHighlight);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 404 */       Insets insets = null;
/* 405 */       if (param1Component instanceof JTextComponent) {
/* 406 */         insets = ((JTextComponent)param1Component).getMargin();
/*     */       }
/* 408 */       param1Insets.top = (insets != null) ? (2 + insets.top) : 2;
/* 409 */       param1Insets.left = (insets != null) ? (2 + insets.left) : 2;
/* 410 */       param1Insets.bottom = (insets != null) ? (2 + insets.bottom) : 2;
/* 411 */       param1Insets.right = (insets != null) ? (2 + insets.right) : 2;
/*     */       
/* 413 */       return param1Insets;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class SplitPaneDividerBorder
/*     */     implements Border, UIResource
/*     */   {
/*     */     Color highlight;
/*     */     
/*     */     Color shadow;
/*     */ 
/*     */     
/*     */     SplitPaneDividerBorder(Color param1Color1, Color param1Color2) {
/* 428 */       this.highlight = param1Color1;
/* 429 */       this.shadow = param1Color2;
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 434 */       if (!(param1Component instanceof BasicSplitPaneDivider)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 440 */       JSplitPane jSplitPane = ((BasicSplitPaneDivider)param1Component).getBasicSplitPaneUI().getSplitPane();
/* 441 */       Dimension dimension = param1Component.getSize();
/*     */       
/* 443 */       Component component = jSplitPane.getLeftComponent();
/*     */ 
/*     */       
/* 446 */       param1Graphics.setColor(param1Component.getBackground());
/* 447 */       param1Graphics.drawRect(param1Int1, param1Int2, param1Int3 - 1, param1Int4 - 1);
/* 448 */       if (jSplitPane.getOrientation() == 1) {
/* 449 */         if (component != null) {
/* 450 */           param1Graphics.setColor(this.highlight);
/* 451 */           param1Graphics.drawLine(0, 0, 0, dimension.height);
/*     */         } 
/* 453 */         component = jSplitPane.getRightComponent();
/* 454 */         if (component != null) {
/* 455 */           param1Graphics.setColor(this.shadow);
/* 456 */           param1Graphics.drawLine(dimension.width - 1, 0, dimension.width - 1, dimension.height);
/*     */         } 
/*     */       } else {
/* 459 */         if (component != null) {
/* 460 */           param1Graphics.setColor(this.highlight);
/* 461 */           param1Graphics.drawLine(0, 0, dimension.width, 0);
/*     */         } 
/* 463 */         component = jSplitPane.getRightComponent();
/* 464 */         if (component != null) {
/* 465 */           param1Graphics.setColor(this.shadow);
/* 466 */           param1Graphics.drawLine(0, dimension.height - 1, dimension.width, dimension.height - 1);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component) {
/* 472 */       Insets insets = new Insets(0, 0, 0, 0);
/* 473 */       if (param1Component instanceof BasicSplitPaneDivider) {
/*     */         
/* 475 */         BasicSplitPaneUI basicSplitPaneUI = ((BasicSplitPaneDivider)param1Component).getBasicSplitPaneUI();
/*     */         
/* 477 */         if (basicSplitPaneUI != null) {
/* 478 */           JSplitPane jSplitPane = basicSplitPaneUI.getSplitPane();
/*     */           
/* 480 */           if (jSplitPane != null) {
/* 481 */             if (jSplitPane.getOrientation() == 1) {
/*     */               
/* 483 */               insets.top = insets.bottom = 0;
/* 484 */               insets.left = insets.right = 1;
/* 485 */               return insets;
/*     */             } 
/*     */             
/* 488 */             insets.top = insets.bottom = 1;
/* 489 */             insets.left = insets.right = 0;
/* 490 */             return insets;
/*     */           } 
/*     */         } 
/*     */       } 
/* 494 */       insets.top = insets.bottom = insets.left = insets.right = 1;
/* 495 */       return insets;
/*     */     } public boolean isBorderOpaque() {
/* 497 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SplitPaneBorder
/*     */     implements Border, UIResource
/*     */   {
/*     */     protected Color highlight;
/*     */     
/*     */     protected Color shadow;
/*     */     
/*     */     public SplitPaneBorder(Color param1Color1, Color param1Color2) {
/* 510 */       this.highlight = param1Color1;
/* 511 */       this.shadow = param1Color2;
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 516 */       if (!(param1Component instanceof JSplitPane)) {
/*     */         return;
/*     */       }
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
/* 538 */       JSplitPane jSplitPane = (JSplitPane)param1Component;
/*     */       
/* 540 */       Component component = jSplitPane.getLeftComponent();
/*     */ 
/*     */       
/* 543 */       param1Graphics.setColor(param1Component.getBackground());
/* 544 */       param1Graphics.drawRect(param1Int1, param1Int2, param1Int3 - 1, param1Int4 - 1);
/* 545 */       if (jSplitPane.getOrientation() == 1) {
/* 546 */         if (component != null) {
/* 547 */           Rectangle rectangle = component.getBounds();
/* 548 */           param1Graphics.setColor(this.shadow);
/* 549 */           param1Graphics.drawLine(0, 0, rectangle.width + 1, 0);
/* 550 */           param1Graphics.drawLine(0, 1, 0, rectangle.height + 1);
/*     */           
/* 552 */           param1Graphics.setColor(this.highlight);
/* 553 */           param1Graphics.drawLine(0, rectangle.height + 1, rectangle.width + 1, rectangle.height + 1);
/*     */         } 
/*     */         
/* 556 */         component = jSplitPane.getRightComponent();
/* 557 */         if (component != null) {
/* 558 */           Rectangle rectangle = component.getBounds();
/*     */           
/* 560 */           int i = rectangle.x + rectangle.width;
/* 561 */           int j = rectangle.y + rectangle.height;
/*     */           
/* 563 */           param1Graphics.setColor(this.shadow);
/* 564 */           param1Graphics.drawLine(rectangle.x - 1, 0, i, 0);
/* 565 */           param1Graphics.setColor(this.highlight);
/* 566 */           param1Graphics.drawLine(rectangle.x - 1, j, i, j);
/* 567 */           param1Graphics.drawLine(i, 0, i, j + 1);
/*     */         } 
/*     */       } else {
/* 570 */         if (component != null) {
/* 571 */           Rectangle rectangle = component.getBounds();
/* 572 */           param1Graphics.setColor(this.shadow);
/* 573 */           param1Graphics.drawLine(0, 0, rectangle.width + 1, 0);
/* 574 */           param1Graphics.drawLine(0, 1, 0, rectangle.height);
/* 575 */           param1Graphics.setColor(this.highlight);
/* 576 */           param1Graphics.drawLine(1 + rectangle.width, 0, 1 + rectangle.width, rectangle.height + 1);
/*     */           
/* 578 */           param1Graphics.drawLine(0, rectangle.height + 1, 0, rectangle.height + 1);
/*     */         } 
/* 580 */         component = jSplitPane.getRightComponent();
/* 581 */         if (component != null) {
/* 582 */           Rectangle rectangle = component.getBounds();
/*     */           
/* 584 */           int i = rectangle.x + rectangle.width;
/* 585 */           int j = rectangle.y + rectangle.height;
/*     */           
/* 587 */           param1Graphics.setColor(this.shadow);
/* 588 */           param1Graphics.drawLine(0, rectangle.y - 1, 0, j);
/* 589 */           param1Graphics.drawLine(i, rectangle.y - 1, i, rectangle.y - 1);
/* 590 */           param1Graphics.setColor(this.highlight);
/* 591 */           param1Graphics.drawLine(0, j, rectangle.width + 1, j);
/* 592 */           param1Graphics.drawLine(i, rectangle.y, i, j);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     public Insets getBorderInsets(Component param1Component) {
/* 597 */       return new Insets(1, 1, 1, 1);
/*     */     } public boolean isBorderOpaque() {
/* 599 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicBorders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */