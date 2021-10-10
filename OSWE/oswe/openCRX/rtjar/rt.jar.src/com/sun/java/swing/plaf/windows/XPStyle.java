/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Image;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.security.AccessController;
/*     */ import java.util.HashMap;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.CellRendererPane;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import javax.swing.border.LineBorder;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import sun.awt.image.SunWritableRaster;
/*     */ import sun.awt.windows.ThemeReader;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.swing.CachedPainter;
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
/*     */ class XPStyle
/*     */ {
/*     */   private static XPStyle xp;
/*  71 */   private static SkinPainter skinPainter = new SkinPainter();
/*     */   
/*  73 */   private static Boolean themeActive = null;
/*     */   
/*     */   private HashMap<String, Border> borderMap;
/*     */   
/*     */   private HashMap<String, Color> colorMap;
/*     */   private boolean flatMenus;
/*     */   
/*     */   static {
/*  81 */     invalidateStyle();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized void invalidateStyle() {
/*  88 */     xp = null;
/*  89 */     themeActive = null;
/*  90 */     skinPainter.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized XPStyle getXP() {
/*  99 */     if (themeActive == null) {
/* 100 */       Toolkit toolkit = Toolkit.getDefaultToolkit();
/*     */       
/* 102 */       themeActive = (Boolean)toolkit.getDesktopProperty("win.xpstyle.themeActive");
/* 103 */       if (themeActive == null) {
/* 104 */         themeActive = Boolean.FALSE;
/*     */       }
/* 106 */       if (themeActive.booleanValue()) {
/* 107 */         GetPropertyAction getPropertyAction = new GetPropertyAction("swing.noxp");
/*     */         
/* 109 */         if (AccessController.doPrivileged(getPropertyAction) == null && 
/* 110 */           ThemeReader.isThemed() && 
/* 111 */           !(UIManager.getLookAndFeel() instanceof WindowsClassicLookAndFeel))
/*     */         {
/*     */           
/* 114 */           xp = new XPStyle();
/*     */         }
/*     */       } 
/*     */     } 
/* 118 */     return ThemeReader.isXPStyleEnabled() ? xp : null;
/*     */   }
/*     */   
/*     */   static boolean isVista() {
/* 122 */     XPStyle xPStyle = getXP();
/* 123 */     return (xPStyle != null && xPStyle.isSkinDefined(null, TMSchema.Part.CP_DROPDOWNBUTTONRIGHT));
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
/*     */   String getString(Component paramComponent, TMSchema.Part paramPart, TMSchema.State paramState, TMSchema.Prop paramProp) {
/* 138 */     return getTypeEnumName(paramComponent, paramPart, paramState, paramProp);
/*     */   }
/*     */   
/*     */   TMSchema.TypeEnum getTypeEnum(Component paramComponent, TMSchema.Part paramPart, TMSchema.State paramState, TMSchema.Prop paramProp) {
/* 142 */     int i = ThemeReader.getEnum(paramPart.getControlName(paramComponent), paramPart.getValue(), 
/* 143 */         TMSchema.State.getValue(paramPart, paramState), paramProp
/* 144 */         .getValue());
/* 145 */     return TMSchema.TypeEnum.getTypeEnum(paramProp, i);
/*     */   }
/*     */   
/*     */   private static String getTypeEnumName(Component paramComponent, TMSchema.Part paramPart, TMSchema.State paramState, TMSchema.Prop paramProp) {
/* 149 */     int i = ThemeReader.getEnum(paramPart.getControlName(paramComponent), paramPart.getValue(), 
/* 150 */         TMSchema.State.getValue(paramPart, paramState), paramProp
/* 151 */         .getValue());
/* 152 */     if (i == -1) {
/* 153 */       return null;
/*     */     }
/* 155 */     return TMSchema.TypeEnum.getTypeEnum(paramProp, i).getName();
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
/*     */   int getInt(Component paramComponent, TMSchema.Part paramPart, TMSchema.State paramState, TMSchema.Prop paramProp, int paramInt) {
/* 168 */     return ThemeReader.getInt(paramPart.getControlName(paramComponent), paramPart.getValue(), 
/* 169 */         TMSchema.State.getValue(paramPart, paramState), paramProp
/* 170 */         .getValue());
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
/*     */   Dimension getDimension(Component paramComponent, TMSchema.Part paramPart, TMSchema.State paramState, TMSchema.Prop paramProp) {
/* 183 */     Dimension dimension = ThemeReader.getPosition(paramPart.getControlName(paramComponent), paramPart.getValue(), 
/* 184 */         TMSchema.State.getValue(paramPart, paramState), paramProp
/* 185 */         .getValue());
/* 186 */     return (dimension != null) ? dimension : new Dimension();
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
/*     */   Point getPoint(Component paramComponent, TMSchema.Part paramPart, TMSchema.State paramState, TMSchema.Prop paramProp) {
/* 200 */     Dimension dimension = ThemeReader.getPosition(paramPart.getControlName(paramComponent), paramPart.getValue(), 
/* 201 */         TMSchema.State.getValue(paramPart, paramState), paramProp
/* 202 */         .getValue());
/* 203 */     return (dimension != null) ? new Point(dimension.width, dimension.height) : new Point();
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
/*     */   Insets getMargin(Component paramComponent, TMSchema.Part paramPart, TMSchema.State paramState, TMSchema.Prop paramProp) {
/* 217 */     Insets insets = ThemeReader.getThemeMargins(paramPart.getControlName(paramComponent), paramPart.getValue(), 
/* 218 */         TMSchema.State.getValue(paramPart, paramState), paramProp
/* 219 */         .getValue());
/* 220 */     return (insets != null) ? insets : new Insets(0, 0, 0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized Color getColor(Skin paramSkin, TMSchema.Prop paramProp, Color paramColor) {
/* 231 */     String str = paramSkin.toString() + "." + paramProp.name();
/* 232 */     TMSchema.Part part = paramSkin.part;
/* 233 */     Color color = this.colorMap.get(str);
/* 234 */     if (color == null) {
/* 235 */       color = ThemeReader.getColor(part.getControlName((Component)null), part.getValue(), 
/* 236 */           TMSchema.State.getValue(part, paramSkin.state), paramProp
/* 237 */           .getValue());
/* 238 */       if (color != null) {
/* 239 */         color = new ColorUIResource(color);
/* 240 */         this.colorMap.put(str, color);
/*     */       } 
/*     */     } 
/* 243 */     return (color != null) ? color : paramColor;
/*     */   }
/*     */   
/*     */   Color getColor(Component paramComponent, TMSchema.Part paramPart, TMSchema.State paramState, TMSchema.Prop paramProp, Color paramColor) {
/* 247 */     return getColor(new Skin(paramComponent, paramPart, paramState), paramProp, paramColor);
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
/*     */   synchronized Border getBorder(Component paramComponent, TMSchema.Part paramPart) {
/* 260 */     if (paramPart == TMSchema.Part.MENU) {
/*     */       
/* 262 */       if (this.flatMenus)
/*     */       {
/*     */ 
/*     */         
/* 266 */         return new XPFillBorder(UIManager.getColor("InternalFrame.borderShadow"), 1);
/*     */       }
/*     */       
/* 269 */       return null;
/*     */     } 
/*     */     
/* 272 */     Skin skin = new Skin(paramComponent, paramPart, null);
/* 273 */     Border border = this.borderMap.get(skin.string);
/* 274 */     if (border == null) {
/* 275 */       String str = getTypeEnumName(paramComponent, paramPart, null, TMSchema.Prop.BGTYPE);
/* 276 */       if ("borderfill".equalsIgnoreCase(str)) {
/* 277 */         int i = getInt(paramComponent, paramPart, null, TMSchema.Prop.BORDERSIZE, 1);
/* 278 */         Color color = getColor(skin, TMSchema.Prop.BORDERCOLOR, Color.black);
/* 279 */         border = new XPFillBorder(color, i);
/* 280 */         if (paramPart == TMSchema.Part.CP_COMBOBOX) {
/* 281 */           border = new XPStatefulFillBorder(color, i, paramPart, TMSchema.Prop.BORDERCOLOR);
/*     */         }
/* 283 */       } else if ("imagefile".equalsIgnoreCase(str)) {
/* 284 */         Insets insets = getMargin(paramComponent, paramPart, null, TMSchema.Prop.SIZINGMARGINS);
/* 285 */         if (insets != null) {
/* 286 */           if (getBoolean(paramComponent, paramPart, null, TMSchema.Prop.BORDERONLY)) {
/* 287 */             border = new XPImageBorder(paramComponent, paramPart);
/* 288 */           } else if (paramPart == TMSchema.Part.CP_COMBOBOX) {
/* 289 */             border = new EmptyBorder(1, 1, 1, 1);
/*     */           }
/* 291 */           else if (paramPart == TMSchema.Part.TP_BUTTON) {
/* 292 */             border = new XPEmptyBorder(new Insets(3, 3, 3, 3));
/*     */           } else {
/* 294 */             border = new XPEmptyBorder(insets);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 299 */       if (border != null) {
/* 300 */         this.borderMap.put(skin.string, border);
/*     */       }
/*     */     } 
/* 303 */     return border;
/*     */   }
/*     */   
/*     */   private class XPFillBorder extends LineBorder implements UIResource {
/*     */     XPFillBorder(Color param1Color, int param1Int) {
/* 308 */       super(param1Color, param1Int);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 312 */       Insets insets = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 318 */       if (param1Component instanceof AbstractButton) {
/* 319 */         insets = ((AbstractButton)param1Component).getMargin();
/* 320 */       } else if (param1Component instanceof JToolBar) {
/* 321 */         insets = ((JToolBar)param1Component).getMargin();
/* 322 */       } else if (param1Component instanceof JTextComponent) {
/* 323 */         insets = ((JTextComponent)param1Component).getMargin();
/*     */       } 
/* 325 */       param1Insets.top = ((insets != null) ? insets.top : 0) + this.thickness;
/* 326 */       param1Insets.left = ((insets != null) ? insets.left : 0) + this.thickness;
/* 327 */       param1Insets.bottom = ((insets != null) ? insets.bottom : 0) + this.thickness;
/* 328 */       param1Insets.right = ((insets != null) ? insets.right : 0) + this.thickness;
/*     */       
/* 330 */       return param1Insets;
/*     */     } }
/*     */   
/*     */   private class XPStatefulFillBorder extends XPFillBorder {
/*     */     private final TMSchema.Part part;
/*     */     private final TMSchema.Prop prop;
/*     */     
/*     */     XPStatefulFillBorder(Color param1Color, int param1Int, TMSchema.Part param1Part, TMSchema.Prop param1Prop) {
/* 338 */       super(param1Color, param1Int);
/* 339 */       this.part = param1Part;
/* 340 */       this.prop = param1Prop;
/*     */     }
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 344 */       TMSchema.State state = TMSchema.State.NORMAL;
/*     */ 
/*     */       
/* 347 */       if (param1Component instanceof JComboBox) {
/* 348 */         JComboBox jComboBox = (JComboBox)param1Component;
/*     */ 
/*     */         
/* 351 */         if (jComboBox.getUI() instanceof WindowsComboBoxUI) {
/* 352 */           WindowsComboBoxUI windowsComboBoxUI = (WindowsComboBoxUI)jComboBox.getUI();
/* 353 */           state = windowsComboBoxUI.getXPComboBoxState(jComboBox);
/*     */         } 
/*     */       } 
/* 356 */       this.lineColor = XPStyle.this.getColor(param1Component, this.part, state, this.prop, Color.black);
/* 357 */       super.paintBorder(param1Component, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */   }
/*     */   
/*     */   private class XPImageBorder extends AbstractBorder implements UIResource {
/*     */     XPStyle.Skin skin;
/*     */     
/*     */     XPImageBorder(Component param1Component, TMSchema.Part param1Part) {
/* 365 */       this.skin = XPStyle.this.getSkin(param1Component, param1Part);
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 370 */       this.skin.paintSkin(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, null);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 374 */       Insets insets1 = null;
/* 375 */       Insets insets2 = this.skin.getContentMargin();
/* 376 */       if (insets2 == null) {
/* 377 */         insets2 = new Insets(0, 0, 0, 0);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 384 */       if (param1Component instanceof AbstractButton) {
/* 385 */         insets1 = ((AbstractButton)param1Component).getMargin();
/* 386 */       } else if (param1Component instanceof JToolBar) {
/* 387 */         insets1 = ((JToolBar)param1Component).getMargin();
/* 388 */       } else if (param1Component instanceof JTextComponent) {
/* 389 */         insets1 = ((JTextComponent)param1Component).getMargin();
/*     */       } 
/* 391 */       param1Insets.top = ((insets1 != null) ? insets1.top : 0) + insets2.top;
/* 392 */       param1Insets.left = ((insets1 != null) ? insets1.left : 0) + insets2.left;
/* 393 */       param1Insets.bottom = ((insets1 != null) ? insets1.bottom : 0) + insets2.bottom;
/* 394 */       param1Insets.right = ((insets1 != null) ? insets1.right : 0) + insets2.right;
/*     */       
/* 396 */       return param1Insets;
/*     */     }
/*     */   }
/*     */   
/*     */   private class XPEmptyBorder extends EmptyBorder implements UIResource {
/*     */     XPEmptyBorder(Insets param1Insets) {
/* 402 */       super(param1Insets.top + 2, param1Insets.left + 2, param1Insets.bottom + 2, param1Insets.right + 2);
/*     */     }
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 406 */       param1Insets = super.getBorderInsets(param1Component, param1Insets);
/*     */       
/* 408 */       Insets insets = null;
/* 409 */       if (param1Component instanceof AbstractButton) {
/* 410 */         Insets insets1 = ((AbstractButton)param1Component).getMargin();
/*     */ 
/*     */         
/* 413 */         if (param1Component.getParent() instanceof JToolBar && !(param1Component instanceof javax.swing.JRadioButton) && !(param1Component instanceof javax.swing.JCheckBox) && insets1 instanceof javax.swing.plaf.InsetsUIResource) {
/*     */ 
/*     */ 
/*     */           
/* 417 */           param1Insets.top -= 2;
/* 418 */           param1Insets.left -= 2;
/* 419 */           param1Insets.bottom -= 2;
/* 420 */           param1Insets.right -= 2;
/*     */         } else {
/* 422 */           insets = insets1;
/*     */         } 
/* 424 */       } else if (param1Component instanceof JToolBar) {
/* 425 */         insets = ((JToolBar)param1Component).getMargin();
/* 426 */       } else if (param1Component instanceof JTextComponent) {
/* 427 */         insets = ((JTextComponent)param1Component).getMargin();
/*     */       } 
/* 429 */       if (insets != null) {
/* 430 */         insets.top += 2;
/* 431 */         insets.left += 2;
/* 432 */         insets.bottom += 2;
/* 433 */         insets.right += 2;
/*     */       } 
/* 435 */       return param1Insets;
/*     */     } }
/*     */   
/*     */   boolean isSkinDefined(Component paramComponent, TMSchema.Part paramPart) {
/* 439 */     return (paramPart.getValue() == 0 || 
/* 440 */       ThemeReader.isThemePartDefined(paramPart
/* 441 */         .getControlName(paramComponent), paramPart.getValue(), 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized Skin getSkin(Component paramComponent, TMSchema.Part paramPart) {
/* 452 */     assert isSkinDefined(paramComponent, paramPart) : "part " + paramPart + " is not defined";
/* 453 */     return new Skin(paramComponent, paramPart, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   long getThemeTransitionDuration(Component paramComponent, TMSchema.Part paramPart, TMSchema.State paramState1, TMSchema.State paramState2, TMSchema.Prop paramProp) {
/* 459 */     return ThemeReader.getThemeTransitionDuration(paramPart.getControlName(paramComponent), paramPart
/* 460 */         .getValue(), 
/* 461 */         TMSchema.State.getValue(paramPart, paramState1), 
/* 462 */         TMSchema.State.getValue(paramPart, paramState2), (paramProp != null) ? paramProp
/* 463 */         .getValue() : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   static class Skin
/*     */   {
/*     */     final Component component;
/*     */     
/*     */     final TMSchema.Part part;
/*     */     
/*     */     final TMSchema.State state;
/*     */     
/*     */     private final String string;
/*     */     
/* 477 */     private Dimension size = null;
/*     */     
/*     */     Skin(Component param1Component, TMSchema.Part param1Part) {
/* 480 */       this(param1Component, param1Part, null);
/*     */     }
/*     */     
/*     */     Skin(TMSchema.Part param1Part, TMSchema.State param1State) {
/* 484 */       this(null, param1Part, param1State);
/*     */     }
/*     */     
/*     */     Skin(Component param1Component, TMSchema.Part param1Part, TMSchema.State param1State) {
/* 488 */       this.component = param1Component;
/* 489 */       this.part = param1Part;
/* 490 */       this.state = param1State;
/*     */       
/* 492 */       String str = param1Part.getControlName(param1Component) + "." + param1Part.name();
/* 493 */       if (param1State != null) {
/* 494 */         str = str + "(" + param1State.name() + ")";
/*     */       }
/* 496 */       this.string = str;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Insets getContentMargin() {
/* 503 */       byte b1 = 100;
/* 504 */       byte b2 = 100;
/*     */       
/* 506 */       Insets insets = ThemeReader.getThemeBackgroundContentMargins(this.part
/* 507 */           .getControlName((Component)null), this.part.getValue(), 0, b1, b2);
/*     */       
/* 509 */       return (insets != null) ? insets : new Insets(0, 0, 0, 0);
/*     */     }
/*     */     
/*     */     private int getWidth(TMSchema.State param1State) {
/* 513 */       if (this.size == null) {
/* 514 */         this.size = XPStyle.getPartSize(this.part, param1State);
/*     */       }
/* 516 */       return (this.size != null) ? this.size.width : 0;
/*     */     }
/*     */     
/*     */     int getWidth() {
/* 520 */       return getWidth((this.state != null) ? this.state : TMSchema.State.NORMAL);
/*     */     }
/*     */     
/*     */     private int getHeight(TMSchema.State param1State) {
/* 524 */       if (this.size == null) {
/* 525 */         this.size = XPStyle.getPartSize(this.part, param1State);
/*     */       }
/* 527 */       return (this.size != null) ? this.size.height : 0;
/*     */     }
/*     */     
/*     */     int getHeight() {
/* 531 */       return getHeight((this.state != null) ? this.state : TMSchema.State.NORMAL);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 535 */       return this.string;
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 539 */       return (param1Object instanceof Skin && ((Skin)param1Object).string.equals(this.string));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 543 */       return this.string.hashCode();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void paintSkin(Graphics param1Graphics, int param1Int1, int param1Int2, TMSchema.State param1State) {
/* 554 */       if (param1State == null) {
/* 555 */         param1State = this.state;
/*     */       }
/* 557 */       paintSkin(param1Graphics, param1Int1, param1Int2, getWidth(param1State), getHeight(param1State), param1State);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void paintSkin(Graphics param1Graphics, Rectangle param1Rectangle, TMSchema.State param1State) {
/* 568 */       paintSkin(param1Graphics, param1Rectangle.x, param1Rectangle.y, param1Rectangle.width, param1Rectangle.height, param1State);
/*     */     }
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
/*     */     void paintSkin(Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, TMSchema.State param1State) {
/* 584 */       if (XPStyle.getXP() == null) {
/*     */         return;
/*     */       }
/* 587 */       if (ThemeReader.isGetThemeTransitionDurationDefined() && this.component instanceof JComponent && 
/*     */         
/* 589 */         SwingUtilities.getAncestorOfClass(CellRendererPane.class, this.component) == null) {
/*     */         
/* 591 */         AnimationController.paintSkin((JComponent)this.component, this, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1State);
/*     */       } else {
/*     */         
/* 594 */         paintSkinRaw(param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, param1State);
/*     */       } 
/*     */     }
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
/*     */     void paintSkinRaw(Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, TMSchema.State param1State) {
/* 612 */       if (XPStyle.getXP() == null) {
/*     */         return;
/*     */       }
/* 615 */       XPStyle.skinPainter.paint(null, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, new Object[] { this, param1State });
/*     */     }
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
/*     */     void paintSkin(Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, TMSchema.State param1State, boolean param1Boolean) {
/* 633 */       if (XPStyle.getXP() == null) {
/*     */         return;
/*     */       }
/* 636 */       if (param1Boolean && "borderfill".equals(XPStyle.getTypeEnumName(this.component, this.part, param1State, TMSchema.Prop.BGTYPE))) {
/*     */         return;
/*     */       }
/*     */       
/* 640 */       XPStyle.skinPainter.paint(null, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4, new Object[] { this, param1State });
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SkinPainter extends CachedPainter {
/*     */     SkinPainter() {
/* 646 */       super(30);
/* 647 */       flush();
/*     */     }
/*     */     
/*     */     public void flush() {
/* 651 */       super.flush();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void paintToImage(Component param1Component, Image param1Image, Graphics param1Graphics, int param1Int1, int param1Int2, Object[] param1ArrayOfObject) {
/* 656 */       boolean bool = false;
/* 657 */       XPStyle.Skin skin = (XPStyle.Skin)param1ArrayOfObject[0];
/* 658 */       TMSchema.Part part = skin.part;
/* 659 */       TMSchema.State state = (TMSchema.State)param1ArrayOfObject[1];
/* 660 */       if (state == null) {
/* 661 */         state = skin.state;
/*     */       }
/* 663 */       if (param1Component == null) {
/* 664 */         param1Component = skin.component;
/*     */       }
/* 666 */       BufferedImage bufferedImage = (BufferedImage)param1Image;
/*     */       
/* 668 */       WritableRaster writableRaster = bufferedImage.getRaster();
/* 669 */       DataBufferInt dataBufferInt = (DataBufferInt)writableRaster.getDataBuffer();
/*     */ 
/*     */       
/* 672 */       ThemeReader.paintBackground(SunWritableRaster.stealData(dataBufferInt, 0), part
/* 673 */           .getControlName(param1Component), part.getValue(), 
/* 674 */           TMSchema.State.getValue(part, state), 0, 0, param1Int1, param1Int2, param1Int1);
/*     */       
/* 676 */       SunWritableRaster.markDirty(dataBufferInt);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Image createImage(Component param1Component, int param1Int1, int param1Int2, GraphicsConfiguration param1GraphicsConfiguration, Object[] param1ArrayOfObject) {
/* 681 */       return new BufferedImage(param1Int1, param1Int2, 2);
/*     */     }
/*     */   }
/*     */   
/*     */   static class GlyphButton extends JButton {
/*     */     private XPStyle.Skin skin;
/*     */     
/*     */     public GlyphButton(Component param1Component, TMSchema.Part param1Part) {
/* 689 */       XPStyle xPStyle = XPStyle.getXP();
/* 690 */       this.skin = (xPStyle != null) ? xPStyle.getSkin(param1Component, param1Part) : null;
/* 691 */       setBorder((Border)null);
/* 692 */       setContentAreaFilled(false);
/* 693 */       setMinimumSize(new Dimension(5, 5));
/* 694 */       setPreferredSize(new Dimension(16, 16));
/* 695 */       setMaximumSize(new Dimension(2147483647, 2147483647));
/*     */     }
/*     */     
/*     */     public boolean isFocusTraversable() {
/* 699 */       return false;
/*     */     }
/*     */     
/*     */     protected TMSchema.State getState() {
/* 703 */       TMSchema.State state = TMSchema.State.NORMAL;
/* 704 */       if (!isEnabled()) {
/* 705 */         state = TMSchema.State.DISABLED;
/* 706 */       } else if (getModel().isPressed()) {
/* 707 */         state = TMSchema.State.PRESSED;
/* 708 */       } else if (getModel().isRollover()) {
/* 709 */         state = TMSchema.State.HOT;
/*     */       } 
/* 711 */       return state;
/*     */     }
/*     */     
/*     */     public void paintComponent(Graphics param1Graphics) {
/* 715 */       if (XPStyle.getXP() == null || this.skin == null) {
/*     */         return;
/*     */       }
/* 718 */       Dimension dimension = getSize();
/* 719 */       this.skin.paintSkin(param1Graphics, 0, 0, dimension.width, dimension.height, getState());
/*     */     }
/*     */     
/*     */     public void setPart(Component param1Component, TMSchema.Part param1Part) {
/* 723 */       XPStyle xPStyle = XPStyle.getXP();
/* 724 */       this.skin = (xPStyle != null) ? xPStyle.getSkin(param1Component, param1Part) : null;
/* 725 */       revalidate();
/* 726 */       repaint();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void paintBorder(Graphics param1Graphics) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private XPStyle() {
/* 737 */     this.flatMenus = getSysBoolean(TMSchema.Prop.FLATMENUS);
/*     */     
/* 739 */     this.colorMap = new HashMap<>();
/* 740 */     this.borderMap = new HashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean getBoolean(Component paramComponent, TMSchema.Part paramPart, TMSchema.State paramState, TMSchema.Prop paramProp) {
/* 746 */     return ThemeReader.getBoolean(paramPart.getControlName(paramComponent), paramPart.getValue(), 
/* 747 */         TMSchema.State.getValue(paramPart, paramState), paramProp
/* 748 */         .getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static Dimension getPartSize(TMSchema.Part paramPart, TMSchema.State paramState) {
/* 754 */     return ThemeReader.getPartSize(paramPart.getControlName((Component)null), paramPart.getValue(), 
/* 755 */         TMSchema.State.getValue(paramPart, paramState));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean getSysBoolean(TMSchema.Prop paramProp) {
/* 760 */     return ThemeReader.getSysBoolean("window", paramProp.getValue());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/XPStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */