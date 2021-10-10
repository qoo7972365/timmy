/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicButtonUI;
/*     */ import javax.swing.plaf.basic.BasicHTML;
/*     */ import javax.swing.text.View;
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
/*     */ public class SynthButtonUI
/*     */   extends BasicButtonUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  54 */     return new SynthButtonUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults(AbstractButton paramAbstractButton) {
/*  62 */     updateStyle(paramAbstractButton);
/*     */     
/*  64 */     LookAndFeel.installProperty(paramAbstractButton, "rolloverEnabled", Boolean.TRUE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners(AbstractButton paramAbstractButton) {
/*  72 */     super.installListeners(paramAbstractButton);
/*  73 */     paramAbstractButton.addPropertyChangeListener(this);
/*     */   }
/*     */   
/*     */   void updateStyle(AbstractButton paramAbstractButton) {
/*  77 */     SynthContext synthContext = getContext(paramAbstractButton, 1);
/*  78 */     SynthStyle synthStyle = this.style;
/*  79 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*  80 */     if (this.style != synthStyle) {
/*  81 */       if (paramAbstractButton.getMargin() == null || paramAbstractButton
/*  82 */         .getMargin() instanceof javax.swing.plaf.UIResource) {
/*  83 */         Insets insets = (Insets)this.style.get(synthContext, getPropertyPrefix() + "margin");
/*     */ 
/*     */         
/*  86 */         if (insets == null)
/*     */         {
/*  88 */           insets = SynthLookAndFeel.EMPTY_UIRESOURCE_INSETS;
/*     */         }
/*  90 */         paramAbstractButton.setMargin(insets);
/*     */       } 
/*     */       
/*  93 */       Object object = this.style.get(synthContext, getPropertyPrefix() + "iconTextGap");
/*  94 */       if (object != null) {
/*  95 */         LookAndFeel.installProperty(paramAbstractButton, "iconTextGap", object);
/*     */       }
/*     */       
/*  98 */       object = this.style.get(synthContext, getPropertyPrefix() + "contentAreaFilled");
/*  99 */       LookAndFeel.installProperty(paramAbstractButton, "contentAreaFilled", (object != null) ? object : Boolean.TRUE);
/*     */ 
/*     */       
/* 102 */       if (synthStyle != null) {
/* 103 */         uninstallKeyboardActions(paramAbstractButton);
/* 104 */         installKeyboardActions(paramAbstractButton);
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners(AbstractButton paramAbstractButton) {
/* 116 */     super.uninstallListeners(paramAbstractButton);
/* 117 */     paramAbstractButton.removePropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults(AbstractButton paramAbstractButton) {
/* 125 */     SynthContext synthContext = getContext(paramAbstractButton, 1);
/*     */     
/* 127 */     this.style.uninstallDefaults(synthContext);
/* 128 */     synthContext.dispose();
/* 129 */     this.style = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 137 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 141 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/* 148 */     int i = 1;
/*     */     
/* 150 */     if (!paramJComponent.isEnabled()) {
/* 151 */       i = 8;
/*     */     }
/* 153 */     if (SynthLookAndFeel.getSelectedUI() == this) {
/* 154 */       return SynthLookAndFeel.getSelectedUIState() | 0x1;
/*     */     }
/* 156 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 157 */     ButtonModel buttonModel = abstractButton.getModel();
/*     */     
/* 159 */     if (buttonModel.isPressed()) {
/* 160 */       if (buttonModel.isArmed()) {
/* 161 */         i = 4;
/*     */       } else {
/*     */         
/* 164 */         i = 2;
/*     */       } 
/*     */     }
/* 167 */     if (buttonModel.isRollover()) {
/* 168 */       i |= 0x2;
/*     */     }
/* 170 */     if (buttonModel.isSelected()) {
/* 171 */       i |= 0x200;
/*     */     }
/* 173 */     if (paramJComponent.isFocusOwner() && abstractButton.isFocusPainted()) {
/* 174 */       i |= 0x100;
/*     */     }
/* 176 */     if (paramJComponent instanceof JButton && ((JButton)paramJComponent).isDefaultButton()) {
/* 177 */       i |= 0x400;
/*     */     }
/* 179 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/*     */     int i;
/* 187 */     if (paramJComponent == null) {
/* 188 */       throw new NullPointerException("Component must be non-null");
/*     */     }
/* 190 */     if (paramInt1 < 0 || paramInt2 < 0) {
/* 191 */       throw new IllegalArgumentException("Width and height must be >= 0");
/*     */     }
/*     */     
/* 194 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 195 */     String str = abstractButton.getText();
/* 196 */     if (str == null || "".equals(str)) {
/* 197 */       return -1;
/*     */     }
/* 199 */     Insets insets = abstractButton.getInsets();
/* 200 */     Rectangle rectangle1 = new Rectangle();
/* 201 */     Rectangle rectangle2 = new Rectangle();
/* 202 */     Rectangle rectangle3 = new Rectangle();
/* 203 */     rectangle1.x = insets.left;
/* 204 */     rectangle1.y = insets.top;
/* 205 */     rectangle1.width = paramInt1 - insets.right + rectangle1.x;
/* 206 */     rectangle1.height = paramInt2 - insets.bottom + rectangle1.y;
/*     */ 
/*     */     
/* 209 */     SynthContext synthContext = getContext(abstractButton);
/* 210 */     FontMetrics fontMetrics = synthContext.getComponent().getFontMetrics(synthContext
/* 211 */         .getStyle().getFont(synthContext));
/* 212 */     synthContext.getStyle().getGraphicsUtils(synthContext).layoutText(synthContext, fontMetrics, abstractButton
/* 213 */         .getText(), abstractButton.getIcon(), abstractButton
/* 214 */         .getHorizontalAlignment(), abstractButton.getVerticalAlignment(), abstractButton
/* 215 */         .getHorizontalTextPosition(), abstractButton.getVerticalTextPosition(), rectangle1, rectangle3, rectangle2, abstractButton
/* 216 */         .getIconTextGap());
/* 217 */     View view = (View)abstractButton.getClientProperty("html");
/*     */     
/* 219 */     if (view != null) {
/* 220 */       i = BasicHTML.getHTMLBaseline(view, rectangle2.width, rectangle2.height);
/*     */       
/* 222 */       if (i >= 0) {
/* 223 */         i += rectangle2.y;
/*     */       }
/*     */     } else {
/*     */       
/* 227 */       i = rectangle2.y + fontMetrics.getAscent();
/*     */     } 
/* 229 */     synthContext.dispose();
/* 230 */     return i;
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
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 251 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 253 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 254 */     paintBackground(synthContext, paramGraphics, paramJComponent);
/* 255 */     paint(synthContext, paramGraphics);
/* 256 */     synthContext.dispose();
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
/* 270 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 272 */     paint(synthContext, paramGraphics);
/* 273 */     synthContext.dispose();
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
/* 284 */     AbstractButton abstractButton = (AbstractButton)paramSynthContext.getComponent();
/*     */     
/* 286 */     paramGraphics.setColor(paramSynthContext.getStyle().getColor(paramSynthContext, ColorType.TEXT_FOREGROUND));
/*     */     
/* 288 */     paramGraphics.setFont(this.style.getFont(paramSynthContext));
/* 289 */     paramSynthContext.getStyle().getGraphicsUtils(paramSynthContext).paintText(paramSynthContext, paramGraphics, abstractButton
/* 290 */         .getText(), getIcon(abstractButton), abstractButton
/* 291 */         .getHorizontalAlignment(), abstractButton.getVerticalAlignment(), abstractButton
/* 292 */         .getHorizontalTextPosition(), abstractButton.getVerticalTextPosition(), abstractButton
/* 293 */         .getIconTextGap(), abstractButton.getDisplayedMnemonicIndex(), 
/* 294 */         getTextShiftOffset(paramSynthContext));
/*     */   }
/*     */   
/*     */   void paintBackground(SynthContext paramSynthContext, Graphics paramGraphics, JComponent paramJComponent) {
/* 298 */     if (((AbstractButton)paramJComponent).isContentAreaFilled()) {
/* 299 */       paramSynthContext.getPainter().paintButtonBackground(paramSynthContext, paramGraphics, 0, 0, paramJComponent
/* 300 */           .getWidth(), paramJComponent
/* 301 */           .getHeight());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 311 */     paramSynthContext.getPainter().paintButtonBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Icon getDefaultIcon(AbstractButton paramAbstractButton) {
/* 322 */     SynthContext synthContext = getContext(paramAbstractButton);
/* 323 */     Icon icon = synthContext.getStyle().getIcon(synthContext, getPropertyPrefix() + "icon");
/* 324 */     synthContext.dispose();
/* 325 */     return icon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Icon getIcon(AbstractButton paramAbstractButton) {
/* 336 */     Icon icon = paramAbstractButton.getIcon();
/* 337 */     ButtonModel buttonModel = paramAbstractButton.getModel();
/*     */     
/* 339 */     if (!buttonModel.isEnabled()) {
/* 340 */       icon = getSynthDisabledIcon(paramAbstractButton, icon);
/* 341 */     } else if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 342 */       icon = getPressedIcon(paramAbstractButton, getSelectedIcon(paramAbstractButton, icon));
/* 343 */     } else if (paramAbstractButton.isRolloverEnabled() && buttonModel.isRollover()) {
/* 344 */       icon = getRolloverIcon(paramAbstractButton, getSelectedIcon(paramAbstractButton, icon));
/* 345 */     } else if (buttonModel.isSelected()) {
/* 346 */       icon = getSelectedIcon(paramAbstractButton, icon);
/*     */     } else {
/* 348 */       icon = getEnabledIcon(paramAbstractButton, icon);
/*     */     } 
/* 350 */     if (icon == null) {
/* 351 */       return getDefaultIcon(paramAbstractButton);
/*     */     }
/* 353 */     return icon;
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
/*     */   private Icon getIcon(AbstractButton paramAbstractButton, Icon paramIcon1, Icon paramIcon2, int paramInt) {
/* 369 */     Icon icon = paramIcon1;
/* 370 */     if (icon == null) {
/* 371 */       if (paramIcon2 instanceof javax.swing.plaf.UIResource) {
/* 372 */         icon = getSynthIcon(paramAbstractButton, paramInt);
/* 373 */         if (icon == null) {
/* 374 */           icon = paramIcon2;
/*     */         }
/*     */       } else {
/* 377 */         icon = paramIcon2;
/*     */       } 
/*     */     }
/* 380 */     return icon;
/*     */   }
/*     */   
/*     */   private Icon getSynthIcon(AbstractButton paramAbstractButton, int paramInt) {
/* 384 */     return this.style.getIcon(getContext(paramAbstractButton, paramInt), getPropertyPrefix() + "icon");
/*     */   }
/*     */   
/*     */   private Icon getEnabledIcon(AbstractButton paramAbstractButton, Icon paramIcon) {
/* 388 */     if (paramIcon == null) {
/* 389 */       paramIcon = getSynthIcon(paramAbstractButton, 1);
/*     */     }
/* 391 */     return paramIcon;
/*     */   }
/*     */   
/*     */   private Icon getSelectedIcon(AbstractButton paramAbstractButton, Icon paramIcon) {
/* 395 */     return getIcon(paramAbstractButton, paramAbstractButton.getSelectedIcon(), paramIcon, 512);
/*     */   }
/*     */   
/*     */   private Icon getRolloverIcon(AbstractButton paramAbstractButton, Icon paramIcon) {
/*     */     Icon icon;
/* 400 */     ButtonModel buttonModel = paramAbstractButton.getModel();
/*     */     
/* 402 */     if (buttonModel.isSelected()) {
/* 403 */       icon = getIcon(paramAbstractButton, paramAbstractButton.getRolloverSelectedIcon(), paramIcon, 514);
/*     */     } else {
/*     */       
/* 406 */       icon = getIcon(paramAbstractButton, paramAbstractButton.getRolloverIcon(), paramIcon, 2);
/*     */     } 
/*     */     
/* 409 */     return icon;
/*     */   }
/*     */   
/*     */   private Icon getPressedIcon(AbstractButton paramAbstractButton, Icon paramIcon) {
/* 413 */     return getIcon(paramAbstractButton, paramAbstractButton.getPressedIcon(), paramIcon, 4);
/*     */   }
/*     */   
/*     */   private Icon getSynthDisabledIcon(AbstractButton paramAbstractButton, Icon paramIcon) {
/*     */     Icon icon;
/* 418 */     ButtonModel buttonModel = paramAbstractButton.getModel();
/*     */     
/* 420 */     if (buttonModel.isSelected()) {
/* 421 */       icon = getIcon(paramAbstractButton, paramAbstractButton.getDisabledSelectedIcon(), paramIcon, 520);
/*     */     } else {
/*     */       
/* 424 */       icon = getIcon(paramAbstractButton, paramAbstractButton.getDisabledIcon(), paramIcon, 8);
/*     */     } 
/*     */     
/* 427 */     return icon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getTextShiftOffset(SynthContext paramSynthContext) {
/* 434 */     AbstractButton abstractButton = (AbstractButton)paramSynthContext.getComponent();
/* 435 */     ButtonModel buttonModel = abstractButton.getModel();
/*     */     
/* 437 */     if (buttonModel.isArmed() && buttonModel.isPressed() && abstractButton
/* 438 */       .getPressedIcon() == null) {
/* 439 */       return paramSynthContext.getStyle().getInt(paramSynthContext, getPropertyPrefix() + "textShiftOffset", 0);
/*     */     }
/*     */     
/* 442 */     return 0;
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
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 454 */     if (paramJComponent.getComponentCount() > 0 && paramJComponent.getLayout() != null) {
/* 455 */       return null;
/*     */     }
/* 457 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 458 */     SynthContext synthContext = getContext(paramJComponent);
/* 459 */     Dimension dimension = synthContext.getStyle().getGraphicsUtils(synthContext).getMinimumSize(synthContext, synthContext
/* 460 */         .getStyle().getFont(synthContext), abstractButton.getText(), getSizingIcon(abstractButton), abstractButton
/* 461 */         .getHorizontalAlignment(), abstractButton.getVerticalAlignment(), abstractButton
/* 462 */         .getHorizontalTextPosition(), abstractButton
/* 463 */         .getVerticalTextPosition(), abstractButton.getIconTextGap(), abstractButton
/* 464 */         .getDisplayedMnemonicIndex());
/*     */     
/* 466 */     synthContext.dispose();
/* 467 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 475 */     if (paramJComponent.getComponentCount() > 0 && paramJComponent.getLayout() != null) {
/* 476 */       return null;
/*     */     }
/* 478 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 479 */     SynthContext synthContext = getContext(paramJComponent);
/* 480 */     Dimension dimension = synthContext.getStyle().getGraphicsUtils(synthContext).getPreferredSize(synthContext, synthContext
/* 481 */         .getStyle().getFont(synthContext), abstractButton.getText(), getSizingIcon(abstractButton), abstractButton
/* 482 */         .getHorizontalAlignment(), abstractButton.getVerticalAlignment(), abstractButton
/* 483 */         .getHorizontalTextPosition(), abstractButton
/* 484 */         .getVerticalTextPosition(), abstractButton.getIconTextGap(), abstractButton
/* 485 */         .getDisplayedMnemonicIndex());
/*     */     
/* 487 */     synthContext.dispose();
/* 488 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 496 */     if (paramJComponent.getComponentCount() > 0 && paramJComponent.getLayout() != null) {
/* 497 */       return null;
/*     */     }
/*     */     
/* 500 */     AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 501 */     SynthContext synthContext = getContext(paramJComponent);
/* 502 */     Dimension dimension = synthContext.getStyle().getGraphicsUtils(synthContext).getMaximumSize(synthContext, synthContext
/* 503 */         .getStyle().getFont(synthContext), abstractButton.getText(), getSizingIcon(abstractButton), abstractButton
/* 504 */         .getHorizontalAlignment(), abstractButton.getVerticalAlignment(), abstractButton
/* 505 */         .getHorizontalTextPosition(), abstractButton
/* 506 */         .getVerticalTextPosition(), abstractButton.getIconTextGap(), abstractButton
/* 507 */         .getDisplayedMnemonicIndex());
/*     */     
/* 509 */     synthContext.dispose();
/* 510 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Icon getSizingIcon(AbstractButton paramAbstractButton) {
/* 518 */     Icon icon = getEnabledIcon(paramAbstractButton, paramAbstractButton.getIcon());
/* 519 */     if (icon == null) {
/* 520 */       icon = getDefaultIcon(paramAbstractButton);
/*     */     }
/* 522 */     return icon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 530 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent))
/* 531 */       updateStyle((AbstractButton)paramPropertyChangeEvent.getSource()); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthButtonUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */