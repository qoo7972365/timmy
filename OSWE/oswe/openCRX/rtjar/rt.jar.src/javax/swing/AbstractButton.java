/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.ItemSelectable;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.ItemEvent;
/*      */ import java.awt.event.ItemListener;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.beans.Transient;
/*      */ import java.io.Serializable;
/*      */ import java.text.BreakIterator;
/*      */ import java.util.Enumeration;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleAction;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleExtendedComponent;
/*      */ import javax.accessibility.AccessibleIcon;
/*      */ import javax.accessibility.AccessibleKeyBinding;
/*      */ import javax.accessibility.AccessibleRelation;
/*      */ import javax.accessibility.AccessibleRelationSet;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.accessibility.AccessibleText;
/*      */ import javax.accessibility.AccessibleValue;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.plaf.ButtonUI;
/*      */ import javax.swing.text.AttributeSet;
/*      */ import javax.swing.text.BadLocationException;
/*      */ import javax.swing.text.Document;
/*      */ import javax.swing.text.Element;
/*      */ import javax.swing.text.Position;
/*      */ import javax.swing.text.StyledDocument;
/*      */ import javax.swing.text.View;
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
/*      */ 
/*      */ 
/*      */ public abstract class AbstractButton
/*      */   extends JComponent
/*      */   implements ItemSelectable, SwingConstants
/*      */ {
/*      */   public static final String MODEL_CHANGED_PROPERTY = "model";
/*      */   public static final String TEXT_CHANGED_PROPERTY = "text";
/*      */   public static final String MNEMONIC_CHANGED_PROPERTY = "mnemonic";
/*      */   public static final String MARGIN_CHANGED_PROPERTY = "margin";
/*      */   public static final String VERTICAL_ALIGNMENT_CHANGED_PROPERTY = "verticalAlignment";
/*      */   public static final String HORIZONTAL_ALIGNMENT_CHANGED_PROPERTY = "horizontalAlignment";
/*      */   public static final String VERTICAL_TEXT_POSITION_CHANGED_PROPERTY = "verticalTextPosition";
/*      */   public static final String HORIZONTAL_TEXT_POSITION_CHANGED_PROPERTY = "horizontalTextPosition";
/*      */   public static final String BORDER_PAINTED_CHANGED_PROPERTY = "borderPainted";
/*      */   public static final String FOCUS_PAINTED_CHANGED_PROPERTY = "focusPainted";
/*      */   public static final String ROLLOVER_ENABLED_CHANGED_PROPERTY = "rolloverEnabled";
/*      */   public static final String CONTENT_AREA_FILLED_CHANGED_PROPERTY = "contentAreaFilled";
/*      */   public static final String ICON_CHANGED_PROPERTY = "icon";
/*      */   public static final String PRESSED_ICON_CHANGED_PROPERTY = "pressedIcon";
/*      */   public static final String SELECTED_ICON_CHANGED_PROPERTY = "selectedIcon";
/*      */   public static final String ROLLOVER_ICON_CHANGED_PROPERTY = "rolloverIcon";
/*      */   public static final String ROLLOVER_SELECTED_ICON_CHANGED_PROPERTY = "rolloverSelectedIcon";
/*      */   public static final String DISABLED_ICON_CHANGED_PROPERTY = "disabledIcon";
/*      */   public static final String DISABLED_SELECTED_ICON_CHANGED_PROPERTY = "disabledSelectedIcon";
/*  161 */   protected ButtonModel model = null;
/*      */   
/*  163 */   private String text = "";
/*  164 */   private Insets margin = null;
/*  165 */   private Insets defaultMargin = null;
/*      */ 
/*      */ 
/*      */   
/*  169 */   private Icon defaultIcon = null;
/*  170 */   private Icon pressedIcon = null;
/*  171 */   private Icon disabledIcon = null;
/*      */   
/*  173 */   private Icon selectedIcon = null;
/*  174 */   private Icon disabledSelectedIcon = null;
/*      */   
/*  176 */   private Icon rolloverIcon = null;
/*  177 */   private Icon rolloverSelectedIcon = null;
/*      */   
/*      */   private boolean paintBorder = true;
/*      */   
/*      */   private boolean paintFocus = true;
/*      */   
/*      */   private boolean rolloverEnabled = false;
/*      */   
/*      */   private boolean contentAreaFilled = true;
/*  186 */   private int verticalAlignment = 0;
/*  187 */   private int horizontalAlignment = 0;
/*      */   
/*  189 */   private int verticalTextPosition = 0;
/*  190 */   private int horizontalTextPosition = 11;
/*      */   
/*  192 */   private int iconTextGap = 4;
/*      */   
/*      */   private int mnemonic;
/*  195 */   private int mnemonicIndex = -1;
/*      */   
/*  197 */   private long multiClickThreshhold = 0L;
/*      */ 
/*      */   
/*      */   private boolean borderPaintedSet = false;
/*      */ 
/*      */   
/*      */   private boolean rolloverEnabledSet = false;
/*      */ 
/*      */   
/*      */   private boolean iconTextGapSet = false;
/*      */ 
/*      */   
/*      */   private boolean contentAreaFilledSet = false;
/*      */ 
/*      */   
/*      */   private boolean setLayout = false;
/*      */ 
/*      */   
/*      */   boolean defaultCapable = true;
/*      */   
/*      */   private Handler handler;
/*      */   
/*  219 */   protected ChangeListener changeListener = null;
/*      */ 
/*      */ 
/*      */   
/*  223 */   protected ActionListener actionListener = null;
/*      */ 
/*      */ 
/*      */   
/*  227 */   protected ItemListener itemListener = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ChangeEvent changeEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hideActionText = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Action action;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PropertyChangeListener actionPropertyChangeListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHideActionText(boolean paramBoolean) {
/*  259 */     if (paramBoolean != this.hideActionText) {
/*  260 */       this.hideActionText = paramBoolean;
/*  261 */       if (getAction() != null) {
/*  262 */         setTextFromAction(getAction(), false);
/*      */       }
/*  264 */       firePropertyChange("hideActionText", !paramBoolean, paramBoolean);
/*      */     } 
/*      */   }
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
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getHideActionText() {
/*  281 */     return this.hideActionText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getText() {
/*  290 */     return this.text;
/*      */   }
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
/*      */   
/*      */   public void setText(String paramString) {
/*  304 */     String str = this.text;
/*  305 */     this.text = paramString;
/*  306 */     firePropertyChange("text", str, paramString);
/*  307 */     updateDisplayedMnemonicIndex(paramString, getMnemonic());
/*      */     
/*  309 */     if (this.accessibleContext != null) {
/*  310 */       this.accessibleContext.firePropertyChange("AccessibleVisibleData", str, paramString);
/*      */     }
/*      */ 
/*      */     
/*  314 */     if (paramString == null || str == null || !paramString.equals(str)) {
/*  315 */       revalidate();
/*  316 */       repaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSelected() {
/*  327 */     return this.model.isSelected();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelected(boolean paramBoolean) {
/*  338 */     boolean bool = isSelected();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  348 */     this.model.setSelected(paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void doClick() {
/*  356 */     doClick(68);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void doClick(int paramInt) {
/*  368 */     Dimension dimension = getSize();
/*  369 */     this.model.setArmed(true);
/*  370 */     this.model.setPressed(true);
/*  371 */     paintImmediately(new Rectangle(0, 0, dimension.width, dimension.height));
/*      */     try {
/*  373 */       Thread.currentThread(); Thread.sleep(paramInt);
/*  374 */     } catch (InterruptedException interruptedException) {}
/*      */     
/*  376 */     this.model.setPressed(false);
/*  377 */     this.model.setArmed(false);
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMargin(Insets paramInsets) {
/*  399 */     if (paramInsets instanceof javax.swing.plaf.UIResource) {
/*  400 */       this.defaultMargin = paramInsets;
/*  401 */     } else if (this.margin instanceof javax.swing.plaf.UIResource) {
/*  402 */       this.defaultMargin = this.margin;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  407 */     if (paramInsets == null && this.defaultMargin != null) {
/*  408 */       paramInsets = this.defaultMargin;
/*      */     }
/*      */     
/*  411 */     Insets insets = this.margin;
/*  412 */     this.margin = paramInsets;
/*  413 */     firePropertyChange("margin", insets, paramInsets);
/*  414 */     if (insets == null || !insets.equals(paramInsets)) {
/*  415 */       revalidate();
/*  416 */       repaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Insets getMargin() {
/*  429 */     return (this.margin == null) ? null : (Insets)this.margin.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Icon getIcon() {
/*  438 */     return this.defaultIcon;
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIcon(Icon paramIcon) {
/*  455 */     Icon icon = this.defaultIcon;
/*  456 */     this.defaultIcon = paramIcon;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  463 */     if (paramIcon != icon && this.disabledIcon instanceof javax.swing.plaf.UIResource) {
/*  464 */       this.disabledIcon = null;
/*      */     }
/*      */     
/*  467 */     firePropertyChange("icon", icon, paramIcon);
/*  468 */     if (this.accessibleContext != null) {
/*  469 */       this.accessibleContext.firePropertyChange("AccessibleVisibleData", icon, paramIcon);
/*      */     }
/*      */ 
/*      */     
/*  473 */     if (paramIcon != icon) {
/*  474 */       if (paramIcon == null || icon == null || paramIcon
/*  475 */         .getIconWidth() != icon.getIconWidth() || paramIcon
/*  476 */         .getIconHeight() != icon.getIconHeight()) {
/*  477 */         revalidate();
/*      */       }
/*  479 */       repaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Icon getPressedIcon() {
/*  489 */     return this.pressedIcon;
/*      */   }
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
/*      */   public void setPressedIcon(Icon paramIcon) {
/*  502 */     Icon icon = this.pressedIcon;
/*  503 */     this.pressedIcon = paramIcon;
/*  504 */     firePropertyChange("pressedIcon", icon, paramIcon);
/*  505 */     if (this.accessibleContext != null) {
/*  506 */       this.accessibleContext.firePropertyChange("AccessibleVisibleData", icon, paramIcon);
/*      */     }
/*      */ 
/*      */     
/*  510 */     if (paramIcon != icon && 
/*  511 */       getModel().isPressed()) {
/*  512 */       repaint();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Icon getSelectedIcon() {
/*  523 */     return this.selectedIcon;
/*      */   }
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
/*      */   public void setSelectedIcon(Icon paramIcon) {
/*  536 */     Icon icon = this.selectedIcon;
/*  537 */     this.selectedIcon = paramIcon;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  544 */     if (paramIcon != icon && this.disabledSelectedIcon instanceof javax.swing.plaf.UIResource)
/*      */     {
/*      */       
/*  547 */       this.disabledSelectedIcon = null;
/*      */     }
/*      */     
/*  550 */     firePropertyChange("selectedIcon", icon, paramIcon);
/*  551 */     if (this.accessibleContext != null) {
/*  552 */       this.accessibleContext.firePropertyChange("AccessibleVisibleData", icon, paramIcon);
/*      */     }
/*      */ 
/*      */     
/*  556 */     if (paramIcon != icon && 
/*  557 */       isSelected()) {
/*  558 */       repaint();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Icon getRolloverIcon() {
/*  569 */     return this.rolloverIcon;
/*      */   }
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
/*      */   public void setRolloverIcon(Icon paramIcon) {
/*  582 */     Icon icon = this.rolloverIcon;
/*  583 */     this.rolloverIcon = paramIcon;
/*  584 */     firePropertyChange("rolloverIcon", icon, paramIcon);
/*  585 */     if (this.accessibleContext != null) {
/*  586 */       this.accessibleContext.firePropertyChange("AccessibleVisibleData", icon, paramIcon);
/*      */     }
/*      */ 
/*      */     
/*  590 */     setRolloverEnabled(true);
/*  591 */     if (paramIcon != icon)
/*      */     {
/*      */       
/*  594 */       repaint();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Icon getRolloverSelectedIcon() {
/*  605 */     return this.rolloverSelectedIcon;
/*      */   }
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
/*      */   
/*      */   public void setRolloverSelectedIcon(Icon paramIcon) {
/*  619 */     Icon icon = this.rolloverSelectedIcon;
/*  620 */     this.rolloverSelectedIcon = paramIcon;
/*  621 */     firePropertyChange("rolloverSelectedIcon", icon, paramIcon);
/*  622 */     if (this.accessibleContext != null) {
/*  623 */       this.accessibleContext.firePropertyChange("AccessibleVisibleData", icon, paramIcon);
/*      */     }
/*      */ 
/*      */     
/*  627 */     setRolloverEnabled(true);
/*  628 */     if (paramIcon != icon)
/*      */     {
/*      */       
/*  631 */       if (isSelected()) {
/*  632 */         repaint();
/*      */       }
/*      */     }
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Transient
/*      */   public Icon getDisabledIcon() {
/*  652 */     if (this.disabledIcon == null) {
/*  653 */       this.disabledIcon = UIManager.getLookAndFeel().getDisabledIcon(this, getIcon());
/*  654 */       if (this.disabledIcon != null) {
/*  655 */         firePropertyChange("disabledIcon", (Object)null, this.disabledIcon);
/*      */       }
/*      */     } 
/*  658 */     return this.disabledIcon;
/*      */   }
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
/*      */   public void setDisabledIcon(Icon paramIcon) {
/*  671 */     Icon icon = this.disabledIcon;
/*  672 */     this.disabledIcon = paramIcon;
/*  673 */     firePropertyChange("disabledIcon", icon, paramIcon);
/*  674 */     if (this.accessibleContext != null) {
/*  675 */       this.accessibleContext.firePropertyChange("AccessibleVisibleData", icon, paramIcon);
/*      */     }
/*      */ 
/*      */     
/*  679 */     if (paramIcon != icon && 
/*  680 */       !isEnabled()) {
/*  681 */       repaint();
/*      */     }
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Icon getDisabledSelectedIcon() {
/*  702 */     if (this.disabledSelectedIcon == null) {
/*  703 */       if (this.selectedIcon != null) {
/*  704 */         this
/*  705 */           .disabledSelectedIcon = UIManager.getLookAndFeel().getDisabledSelectedIcon(this, getSelectedIcon());
/*      */       } else {
/*  707 */         return getDisabledIcon();
/*      */       } 
/*      */     }
/*  710 */     return this.disabledSelectedIcon;
/*      */   }
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
/*      */   
/*      */   public void setDisabledSelectedIcon(Icon paramIcon) {
/*  724 */     Icon icon = this.disabledSelectedIcon;
/*  725 */     this.disabledSelectedIcon = paramIcon;
/*  726 */     firePropertyChange("disabledSelectedIcon", icon, paramIcon);
/*  727 */     if (this.accessibleContext != null) {
/*  728 */       this.accessibleContext.firePropertyChange("AccessibleVisibleData", icon, paramIcon);
/*      */     }
/*      */ 
/*      */     
/*  732 */     if (paramIcon != icon) {
/*  733 */       if (paramIcon == null || icon == null || paramIcon
/*  734 */         .getIconWidth() != icon.getIconWidth() || paramIcon
/*  735 */         .getIconHeight() != icon.getIconHeight()) {
/*  736 */         revalidate();
/*      */       }
/*  738 */       if (!isEnabled() && isSelected()) {
/*  739 */         repaint();
/*      */       }
/*      */     } 
/*      */   }
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
/*      */ 
/*      */   
/*      */   public int getVerticalAlignment() {
/*  756 */     return this.verticalAlignment;
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVerticalAlignment(int paramInt) {
/*  778 */     if (paramInt == this.verticalAlignment)
/*  779 */       return;  int i = this.verticalAlignment;
/*  780 */     this.verticalAlignment = checkVerticalKey(paramInt, "verticalAlignment");
/*  781 */     firePropertyChange("verticalAlignment", i, this.verticalAlignment); repaint();
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHorizontalAlignment() {
/*  800 */     return this.horizontalAlignment;
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHorizontalAlignment(int paramInt) {
/*  829 */     if (paramInt == this.horizontalAlignment)
/*  830 */       return;  int i = this.horizontalAlignment;
/*  831 */     this.horizontalAlignment = checkHorizontalKey(paramInt, "horizontalAlignment");
/*      */     
/*  833 */     firePropertyChange("horizontalAlignment", i, this.horizontalAlignment);
/*      */     
/*  835 */     repaint();
/*      */   }
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
/*      */ 
/*      */   
/*      */   public int getVerticalTextPosition() {
/*  850 */     return this.verticalTextPosition;
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVerticalTextPosition(int paramInt) {
/*  870 */     if (paramInt == this.verticalTextPosition)
/*  871 */       return;  int i = this.verticalTextPosition;
/*  872 */     this.verticalTextPosition = checkVerticalKey(paramInt, "verticalTextPosition");
/*  873 */     firePropertyChange("verticalTextPosition", i, this.verticalTextPosition);
/*  874 */     revalidate();
/*  875 */     repaint();
/*      */   }
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
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHorizontalTextPosition() {
/*  891 */     return this.horizontalTextPosition;
/*      */   }
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHorizontalTextPosition(int paramInt) {
/*  917 */     if (paramInt == this.horizontalTextPosition)
/*  918 */       return;  int i = this.horizontalTextPosition;
/*  919 */     this.horizontalTextPosition = checkHorizontalKey(paramInt, "horizontalTextPosition");
/*      */     
/*  921 */     firePropertyChange("horizontalTextPosition", i, this.horizontalTextPosition);
/*      */ 
/*      */     
/*  924 */     revalidate();
/*  925 */     repaint();
/*      */   }
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
/*      */   public int getIconTextGap() {
/*  938 */     return this.iconTextGap;
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIconTextGap(int paramInt) {
/*  958 */     int i = this.iconTextGap;
/*  959 */     this.iconTextGap = paramInt;
/*  960 */     this.iconTextGapSet = true;
/*  961 */     firePropertyChange("iconTextGap", i, paramInt);
/*  962 */     if (paramInt != i) {
/*  963 */       revalidate();
/*  964 */       repaint();
/*      */     } 
/*      */   }
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
/*      */ 
/*      */ 
/*      */   
/*      */   protected int checkHorizontalKey(int paramInt, String paramString) {
/*  991 */     if (paramInt == 2 || paramInt == 0 || paramInt == 4 || paramInt == 10 || paramInt == 11)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  996 */       return paramInt;
/*      */     }
/*  998 */     throw new IllegalArgumentException(paramString);
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int checkVerticalKey(int paramInt, String paramString) {
/* 1020 */     if (paramInt == 1 || paramInt == 0 || paramInt == 3) {
/* 1021 */       return paramInt;
/*      */     }
/* 1023 */     throw new IllegalArgumentException(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNotify() {
/* 1033 */     super.removeNotify();
/* 1034 */     if (isRolloverEnabled()) {
/* 1035 */       getModel().setRollover(false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setActionCommand(String paramString) {
/* 1044 */     getModel().setActionCommand(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getActionCommand() {
/* 1052 */     String str = getModel().getActionCommand();
/* 1053 */     if (str == null) {
/* 1054 */       str = getText();
/*      */     }
/* 1056 */     return str;
/*      */   }
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
/*      */ 
/*      */   
/*      */   public void setAction(Action paramAction) {
/* 1101 */     Action action = getAction();
/* 1102 */     if (this.action == null || !this.action.equals(paramAction)) {
/* 1103 */       this.action = paramAction;
/* 1104 */       if (action != null) {
/* 1105 */         removeActionListener(action);
/* 1106 */         action.removePropertyChangeListener(this.actionPropertyChangeListener);
/* 1107 */         this.actionPropertyChangeListener = null;
/*      */       } 
/* 1109 */       configurePropertiesFromAction(this.action);
/* 1110 */       if (this.action != null) {
/*      */         
/* 1112 */         if (!isListener(ActionListener.class, this.action)) {
/* 1113 */           addActionListener(this.action);
/*      */         }
/*      */         
/* 1116 */         this.actionPropertyChangeListener = createActionPropertyChangeListener(this.action);
/* 1117 */         this.action.addPropertyChangeListener(this.actionPropertyChangeListener);
/*      */       } 
/* 1119 */       firePropertyChange("action", action, this.action);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isListener(Class paramClass, ActionListener paramActionListener) {
/* 1124 */     boolean bool = false;
/* 1125 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 1126 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1127 */       if (arrayOfObject[i] == paramClass && arrayOfObject[i + 1] == paramActionListener) {
/* 1128 */         bool = true;
/*      */       }
/*      */     } 
/* 1131 */     return bool;
/*      */   }
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
/*      */ 
/*      */   
/*      */   public Action getAction() {
/* 1146 */     return this.action;
/*      */   }
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
/*      */ 
/*      */ 
/*      */   
/*      */   protected void configurePropertiesFromAction(Action paramAction) {
/* 1162 */     setMnemonicFromAction(paramAction);
/* 1163 */     setTextFromAction(paramAction, false);
/* 1164 */     AbstractAction.setToolTipTextFromAction(this, paramAction);
/* 1165 */     setIconFromAction(paramAction);
/* 1166 */     setActionCommandFromAction(paramAction);
/* 1167 */     AbstractAction.setEnabledFromAction(this, paramAction);
/* 1168 */     if (AbstractAction.hasSelectedKey(paramAction) && 
/* 1169 */       shouldUpdateSelectedStateFromAction()) {
/* 1170 */       setSelectedFromAction(paramAction);
/*      */     }
/* 1172 */     setDisplayedMnemonicIndexFromAction(paramAction, false);
/*      */   }
/*      */ 
/*      */   
/*      */   void clientPropertyChanged(Object paramObject1, Object paramObject2, Object paramObject3) {
/* 1177 */     if (paramObject1 == "hideActionText") {
/*      */       
/* 1179 */       boolean bool = (paramObject3 instanceof Boolean) ? ((Boolean)paramObject3).booleanValue() : false;
/* 1180 */       if (getHideActionText() != bool) {
/* 1181 */         setHideActionText(bool);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean shouldUpdateSelectedStateFromAction() {
/* 1192 */     return false;
/*      */   }
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
/*      */   protected void actionPropertyChanged(Action paramAction, String paramString) {
/* 1215 */     if (paramString == "Name") {
/* 1216 */       setTextFromAction(paramAction, true);
/* 1217 */     } else if (paramString == "enabled") {
/* 1218 */       AbstractAction.setEnabledFromAction(this, paramAction);
/* 1219 */     } else if (paramString == "ShortDescription") {
/* 1220 */       AbstractAction.setToolTipTextFromAction(this, paramAction);
/* 1221 */     } else if (paramString == "SmallIcon") {
/* 1222 */       smallIconChanged(paramAction);
/* 1223 */     } else if (paramString == "MnemonicKey") {
/* 1224 */       setMnemonicFromAction(paramAction);
/* 1225 */     } else if (paramString == "ActionCommandKey") {
/* 1226 */       setActionCommandFromAction(paramAction);
/* 1227 */     } else if (paramString == "SwingSelectedKey" && 
/* 1228 */       AbstractAction.hasSelectedKey(paramAction) && 
/* 1229 */       shouldUpdateSelectedStateFromAction()) {
/* 1230 */       setSelectedFromAction(paramAction);
/* 1231 */     } else if (paramString == "SwingDisplayedMnemonicIndexKey") {
/* 1232 */       setDisplayedMnemonicIndexFromAction(paramAction, true);
/* 1233 */     } else if (paramString == "SwingLargeIconKey") {
/* 1234 */       largeIconChanged(paramAction);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setDisplayedMnemonicIndexFromAction(Action paramAction, boolean paramBoolean) {
/* 1241 */     Integer integer = (paramAction == null) ? null : (Integer)paramAction.getValue("SwingDisplayedMnemonicIndexKey");
/* 1242 */     if (paramBoolean || integer != null) {
/*      */       int i;
/* 1244 */       if (integer == null) {
/* 1245 */         i = -1;
/*      */       } else {
/* 1247 */         i = integer.intValue();
/* 1248 */         String str = getText();
/* 1249 */         if (str == null || i >= str.length()) {
/* 1250 */           i = -1;
/*      */         }
/*      */       } 
/* 1253 */       setDisplayedMnemonicIndex(i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setMnemonicFromAction(Action paramAction) {
/* 1259 */     Integer integer = (paramAction == null) ? null : (Integer)paramAction.getValue("MnemonicKey");
/* 1260 */     setMnemonic((integer == null) ? 0 : integer.intValue());
/*      */   }
/*      */   
/*      */   private void setTextFromAction(Action paramAction, boolean paramBoolean) {
/* 1264 */     boolean bool = getHideActionText();
/* 1265 */     if (!paramBoolean) {
/* 1266 */       setText((paramAction != null && !bool) ? (String)paramAction
/* 1267 */           .getValue("Name") : null);
/*      */     }
/* 1269 */     else if (!bool) {
/* 1270 */       setText((String)paramAction.getValue("Name"));
/*      */     } 
/*      */   }
/*      */   
/*      */   void setIconFromAction(Action paramAction) {
/* 1275 */     Icon icon = null;
/* 1276 */     if (paramAction != null) {
/* 1277 */       icon = (Icon)paramAction.getValue("SwingLargeIconKey");
/* 1278 */       if (icon == null) {
/* 1279 */         icon = (Icon)paramAction.getValue("SmallIcon");
/*      */       }
/*      */     } 
/* 1282 */     setIcon(icon);
/*      */   }
/*      */   
/*      */   void smallIconChanged(Action paramAction) {
/* 1286 */     if (paramAction.getValue("SwingLargeIconKey") == null) {
/* 1287 */       setIconFromAction(paramAction);
/*      */     }
/*      */   }
/*      */   
/*      */   void largeIconChanged(Action paramAction) {
/* 1292 */     setIconFromAction(paramAction);
/*      */   }
/*      */   
/*      */   private void setActionCommandFromAction(Action paramAction) {
/* 1296 */     setActionCommand((paramAction != null) ? (String)paramAction
/* 1297 */         .getValue("ActionCommandKey") : null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setSelectedFromAction(Action paramAction) {
/* 1309 */     boolean bool = false;
/* 1310 */     if (paramAction != null) {
/* 1311 */       bool = AbstractAction.isSelected(paramAction);
/*      */     }
/* 1313 */     if (bool != isSelected()) {
/*      */ 
/*      */       
/* 1316 */       setSelected(bool);
/*      */       
/* 1318 */       if (!bool && isSelected() && 
/* 1319 */         getModel() instanceof DefaultButtonModel) {
/* 1320 */         ButtonGroup buttonGroup = ((DefaultButtonModel)getModel()).getGroup();
/* 1321 */         if (buttonGroup != null) {
/* 1322 */           buttonGroup.clearSelection();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertyChangeListener createActionPropertyChangeListener(Action paramAction) {
/* 1344 */     return createActionPropertyChangeListener0(paramAction);
/*      */   }
/*      */ 
/*      */   
/*      */   PropertyChangeListener createActionPropertyChangeListener0(Action paramAction) {
/* 1349 */     return new ButtonActionPropertyChangeListener(this, paramAction);
/*      */   }
/*      */   
/*      */   private static class ButtonActionPropertyChangeListener
/*      */     extends ActionPropertyChangeListener<AbstractButton>
/*      */   {
/*      */     ButtonActionPropertyChangeListener(AbstractButton param1AbstractButton, Action param1Action) {
/* 1356 */       super(param1AbstractButton, param1Action);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void actionPropertyChanged(AbstractButton param1AbstractButton, Action param1Action, PropertyChangeEvent param1PropertyChangeEvent) {
/* 1361 */       if (AbstractAction.shouldReconfigure(param1PropertyChangeEvent)) {
/* 1362 */         param1AbstractButton.configurePropertiesFromAction(param1Action);
/*      */       } else {
/* 1364 */         param1AbstractButton.actionPropertyChanged(param1Action, param1PropertyChangeEvent.getPropertyName());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBorderPainted() {
/* 1376 */     return this.paintBorder;
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBorderPainted(boolean paramBoolean) {
/* 1398 */     boolean bool = this.paintBorder;
/* 1399 */     this.paintBorder = paramBoolean;
/* 1400 */     this.borderPaintedSet = true;
/* 1401 */     firePropertyChange("borderPainted", bool, this.paintBorder);
/* 1402 */     if (paramBoolean != bool) {
/* 1403 */       revalidate();
/* 1404 */       repaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintBorder(Graphics paramGraphics) {
/* 1417 */     if (isBorderPainted()) {
/* 1418 */       super.paintBorder(paramGraphics);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFocusPainted() {
/* 1429 */     return this.paintFocus;
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFocusPainted(boolean paramBoolean) {
/* 1448 */     boolean bool = this.paintFocus;
/* 1449 */     this.paintFocus = paramBoolean;
/* 1450 */     firePropertyChange("focusPainted", bool, this.paintFocus);
/* 1451 */     if (paramBoolean != bool && isFocusOwner()) {
/* 1452 */       revalidate();
/* 1453 */       repaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isContentAreaFilled() {
/* 1464 */     return this.contentAreaFilled;
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContentAreaFilled(boolean paramBoolean) {
/* 1492 */     boolean bool = this.contentAreaFilled;
/* 1493 */     this.contentAreaFilled = paramBoolean;
/* 1494 */     this.contentAreaFilledSet = true;
/* 1495 */     firePropertyChange("contentAreaFilled", bool, this.contentAreaFilled);
/* 1496 */     if (paramBoolean != bool) {
/* 1497 */       repaint();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRolloverEnabled() {
/* 1508 */     return this.rolloverEnabled;
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRolloverEnabled(boolean paramBoolean) {
/* 1527 */     boolean bool = this.rolloverEnabled;
/* 1528 */     this.rolloverEnabled = paramBoolean;
/* 1529 */     this.rolloverEnabledSet = true;
/* 1530 */     firePropertyChange("rolloverEnabled", bool, this.rolloverEnabled);
/* 1531 */     if (paramBoolean != bool) {
/* 1532 */       repaint();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMnemonic() {
/* 1541 */     return this.mnemonic;
/*      */   }
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
/*      */   
/*      */   public void setMnemonic(int paramInt) {
/* 1575 */     int i = getMnemonic();
/* 1576 */     this.model.setMnemonic(paramInt);
/* 1577 */     updateMnemonicProperties();
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMnemonic(char paramChar) {
/* 1594 */     char c = paramChar;
/* 1595 */     if (c >= 'a' && c <= 'z')
/* 1596 */       c -= ' '; 
/* 1597 */     setMnemonic(c);
/*      */   }
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
/*      */   public void setDisplayedMnemonicIndex(int paramInt) throws IllegalArgumentException {
/* 1630 */     int i = this.mnemonicIndex;
/* 1631 */     if (paramInt == -1) {
/* 1632 */       this.mnemonicIndex = -1;
/*      */     } else {
/* 1634 */       String str = getText();
/* 1635 */       byte b = (str == null) ? 0 : str.length();
/* 1636 */       if (paramInt < -1 || paramInt >= b) {
/* 1637 */         throw new IllegalArgumentException("index == " + paramInt);
/*      */       }
/*      */     } 
/* 1640 */     this.mnemonicIndex = paramInt;
/* 1641 */     firePropertyChange("displayedMnemonicIndex", i, paramInt);
/* 1642 */     if (paramInt != i) {
/* 1643 */       revalidate();
/* 1644 */       repaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDisplayedMnemonicIndex() {
/* 1657 */     return this.mnemonicIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateDisplayedMnemonicIndex(String paramString, int paramInt) {
/* 1667 */     setDisplayedMnemonicIndex(
/* 1668 */         SwingUtilities.findDisplayedMnemonicIndex(paramString, paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateMnemonicProperties() {
/* 1677 */     int i = this.model.getMnemonic();
/* 1678 */     if (this.mnemonic != i) {
/* 1679 */       int j = this.mnemonic;
/* 1680 */       this.mnemonic = i;
/* 1681 */       firePropertyChange("mnemonic", j, this.mnemonic);
/*      */       
/* 1683 */       updateDisplayedMnemonicIndex(getText(), this.mnemonic);
/* 1684 */       revalidate();
/* 1685 */       repaint();
/*      */     } 
/*      */   }
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
/*      */   public void setMultiClickThreshhold(long paramLong) {
/* 1709 */     if (paramLong < 0L) {
/* 1710 */       throw new IllegalArgumentException("threshhold must be >= 0");
/*      */     }
/* 1712 */     this.multiClickThreshhold = paramLong;
/*      */   }
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
/*      */   
/*      */   public long getMultiClickThreshhold() {
/* 1726 */     return this.multiClickThreshhold;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ButtonModel getModel() {
/* 1735 */     return this.model;
/*      */   }
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
/*      */   public void setModel(ButtonModel paramButtonModel) {
/* 1748 */     ButtonModel buttonModel = getModel();
/*      */     
/* 1750 */     if (buttonModel != null) {
/* 1751 */       buttonModel.removeChangeListener(this.changeListener);
/* 1752 */       buttonModel.removeActionListener(this.actionListener);
/* 1753 */       buttonModel.removeItemListener(this.itemListener);
/* 1754 */       this.changeListener = null;
/* 1755 */       this.actionListener = null;
/* 1756 */       this.itemListener = null;
/*      */     } 
/*      */     
/* 1759 */     this.model = paramButtonModel;
/*      */     
/* 1761 */     if (paramButtonModel != null) {
/* 1762 */       this.changeListener = createChangeListener();
/* 1763 */       this.actionListener = createActionListener();
/* 1764 */       this.itemListener = createItemListener();
/* 1765 */       paramButtonModel.addChangeListener(this.changeListener);
/* 1766 */       paramButtonModel.addActionListener(this.actionListener);
/* 1767 */       paramButtonModel.addItemListener(this.itemListener);
/*      */       
/* 1769 */       updateMnemonicProperties();
/*      */ 
/*      */ 
/*      */       
/* 1773 */       super.setEnabled(paramButtonModel.isEnabled());
/*      */     } else {
/*      */       
/* 1776 */       this.mnemonic = 0;
/*      */     } 
/*      */     
/* 1779 */     updateDisplayedMnemonicIndex(getText(), this.mnemonic);
/*      */     
/* 1781 */     firePropertyChange("model", buttonModel, paramButtonModel);
/* 1782 */     if (paramButtonModel != buttonModel) {
/* 1783 */       revalidate();
/* 1784 */       repaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ButtonUI getUI() {
/* 1795 */     return (ButtonUI)this.ui;
/*      */   }
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
/*      */ 
/*      */   
/*      */   public void setUI(ButtonUI paramButtonUI) {
/* 1810 */     setUI(paramButtonUI);
/*      */     
/* 1812 */     if (this.disabledIcon instanceof javax.swing.plaf.UIResource) {
/* 1813 */       setDisabledIcon((Icon)null);
/*      */     }
/* 1815 */     if (this.disabledSelectedIcon instanceof javax.swing.plaf.UIResource) {
/* 1816 */       setDisabledSelectedIcon((Icon)null);
/*      */     }
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateUI() {}
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addImpl(Component paramComponent, Object paramObject, int paramInt) {
/* 1853 */     if (!this.setLayout) {
/* 1854 */       setLayout(new OverlayLayout(this));
/*      */     }
/* 1856 */     super.addImpl(paramComponent, paramObject, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLayout(LayoutManager paramLayoutManager) {
/* 1868 */     this.setLayout = true;
/* 1869 */     super.setLayout(paramLayoutManager);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChangeListener(ChangeListener paramChangeListener) {
/* 1877 */     this.listenerList.add(ChangeListener.class, paramChangeListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeChangeListener(ChangeListener paramChangeListener) {
/* 1885 */     this.listenerList.remove(ChangeListener.class, paramChangeListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChangeListener[] getChangeListeners() {
/* 1897 */     return this.listenerList.<ChangeListener>getListeners(ChangeListener.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fireStateChanged() {
/* 1908 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*      */ 
/*      */     
/* 1911 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1912 */       if (arrayOfObject[i] == ChangeListener.class) {
/*      */         
/* 1914 */         if (this.changeEvent == null)
/* 1915 */           this.changeEvent = new ChangeEvent(this); 
/* 1916 */         ((ChangeListener)arrayOfObject[i + 1]).stateChanged(this.changeEvent);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addActionListener(ActionListener paramActionListener) {
/* 1926 */     this.listenerList.add(ActionListener.class, paramActionListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeActionListener(ActionListener paramActionListener) {
/* 1938 */     if (paramActionListener != null && getAction() == paramActionListener) {
/* 1939 */       setAction((Action)null);
/*      */     } else {
/* 1941 */       this.listenerList.remove(ActionListener.class, paramActionListener);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ActionListener[] getActionListeners() {
/* 1954 */     return this.listenerList.<ActionListener>getListeners(ActionListener.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ChangeListener createChangeListener() {
/* 1965 */     return getHandler();
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class ButtonChangeListener
/*      */     implements ChangeListener, Serializable
/*      */   {
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 1988 */       AbstractButton.this.getHandler().stateChanged(param1ChangeEvent);
/*      */     }
/*      */   }
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
/*      */ 
/*      */   
/*      */   protected void fireActionPerformed(ActionEvent paramActionEvent) {
/* 2004 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 2005 */     ActionEvent actionEvent = null;
/*      */ 
/*      */     
/* 2008 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 2009 */       if (arrayOfObject[i] == ActionListener.class) {
/*      */         
/* 2011 */         if (actionEvent == null) {
/* 2012 */           String str = paramActionEvent.getActionCommand();
/* 2013 */           if (str == null) {
/* 2014 */             str = getActionCommand();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2020 */           actionEvent = new ActionEvent(this, 1001, str, paramActionEvent.getWhen(), paramActionEvent.getModifiers());
/*      */         } 
/* 2022 */         ((ActionListener)arrayOfObject[i + 1]).actionPerformed(actionEvent);
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   protected void fireItemStateChanged(ItemEvent paramItemEvent) {
/* 2037 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 2038 */     ItemEvent itemEvent = null;
/*      */ 
/*      */     
/* 2041 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 2042 */       if (arrayOfObject[i] == ItemListener.class) {
/*      */         
/* 2044 */         if (itemEvent == null)
/*      */         {
/*      */ 
/*      */           
/* 2048 */           itemEvent = new ItemEvent(this, 701, this, paramItemEvent.getStateChange());
/*      */         }
/* 2050 */         ((ItemListener)arrayOfObject[i + 1]).itemStateChanged(itemEvent);
/*      */       } 
/*      */     } 
/* 2053 */     if (this.accessibleContext != null) {
/* 2054 */       if (paramItemEvent.getStateChange() == 1) {
/* 2055 */         this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.SELECTED);
/*      */ 
/*      */         
/* 2058 */         this.accessibleContext.firePropertyChange("AccessibleValue", 
/*      */             
/* 2060 */             Integer.valueOf(0), Integer.valueOf(1));
/*      */       } else {
/* 2062 */         this.accessibleContext.firePropertyChange("AccessibleState", AccessibleState.SELECTED, null);
/*      */ 
/*      */         
/* 2065 */         this.accessibleContext.firePropertyChange("AccessibleValue", 
/*      */             
/* 2067 */             Integer.valueOf(1), Integer.valueOf(0));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected ActionListener createActionListener() {
/* 2074 */     return getHandler();
/*      */   }
/*      */ 
/*      */   
/*      */   protected ItemListener createItemListener() {
/* 2079 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnabled(boolean paramBoolean) {
/* 2088 */     if (!paramBoolean && this.model.isRollover()) {
/* 2089 */       this.model.setRollover(false);
/*      */     }
/* 2091 */     super.setEnabled(paramBoolean);
/* 2092 */     this.model.setEnabled(paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public String getLabel() {
/* 2105 */     return getText();
/*      */   }
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
/*      */   @Deprecated
/*      */   public void setLabel(String paramString) {
/* 2119 */     setText(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addItemListener(ItemListener paramItemListener) {
/* 2127 */     this.listenerList.add(ItemListener.class, paramItemListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeItemListener(ItemListener paramItemListener) {
/* 2135 */     this.listenerList.remove(ItemListener.class, paramItemListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemListener[] getItemListeners() {
/* 2147 */     return this.listenerList.<ItemListener>getListeners(ItemListener.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object[] getSelectedObjects() {
/* 2158 */     if (!isSelected()) {
/* 2159 */       return null;
/*      */     }
/* 2161 */     Object[] arrayOfObject = new Object[1];
/* 2162 */     arrayOfObject[0] = getText();
/* 2163 */     return arrayOfObject;
/*      */   }
/*      */   
/*      */   protected void init(String paramString, Icon paramIcon) {
/* 2167 */     if (paramString != null) {
/* 2168 */       setText(paramString);
/*      */     }
/*      */     
/* 2171 */     if (paramIcon != null) {
/* 2172 */       setIcon(paramIcon);
/*      */     }
/*      */ 
/*      */     
/* 2176 */     updateUI();
/*      */     
/* 2178 */     setAlignmentX(0.0F);
/* 2179 */     setAlignmentY(0.5F);
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean imageUpdate(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 2200 */     Icon icon = null;
/*      */     
/* 2202 */     if (!this.model.isEnabled()) {
/* 2203 */       if (this.model.isSelected()) {
/* 2204 */         icon = getDisabledSelectedIcon();
/*      */       } else {
/* 2206 */         icon = getDisabledIcon();
/*      */       } 
/* 2208 */     } else if (this.model.isPressed() && this.model.isArmed()) {
/* 2209 */       icon = getPressedIcon();
/* 2210 */     } else if (isRolloverEnabled() && this.model.isRollover()) {
/* 2211 */       if (this.model.isSelected()) {
/* 2212 */         icon = getRolloverSelectedIcon();
/*      */       } else {
/* 2214 */         icon = getRolloverIcon();
/*      */       } 
/* 2216 */     } else if (this.model.isSelected()) {
/* 2217 */       icon = getSelectedIcon();
/*      */     } 
/*      */     
/* 2220 */     if (icon == null) {
/* 2221 */       icon = getIcon();
/*      */     }
/*      */     
/* 2224 */     if (icon == null || 
/* 2225 */       !SwingUtilities.doesIconReferenceImage(icon, paramImage))
/*      */     {
/*      */       
/* 2228 */       return false;
/*      */     }
/* 2230 */     return super.imageUpdate(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*      */   }
/*      */   
/*      */   void setUIProperty(String paramString, Object paramObject) {
/* 2234 */     if (paramString == "borderPainted") {
/* 2235 */       if (!this.borderPaintedSet) {
/* 2236 */         setBorderPainted(((Boolean)paramObject).booleanValue());
/* 2237 */         this.borderPaintedSet = false;
/*      */       } 
/* 2239 */     } else if (paramString == "rolloverEnabled") {
/* 2240 */       if (!this.rolloverEnabledSet) {
/* 2241 */         setRolloverEnabled(((Boolean)paramObject).booleanValue());
/* 2242 */         this.rolloverEnabledSet = false;
/*      */       } 
/* 2244 */     } else if (paramString == "iconTextGap") {
/* 2245 */       if (!this.iconTextGapSet) {
/* 2246 */         setIconTextGap(((Number)paramObject).intValue());
/* 2247 */         this.iconTextGapSet = false;
/*      */       } 
/* 2249 */     } else if (paramString == "contentAreaFilled") {
/* 2250 */       if (!this.contentAreaFilledSet) {
/* 2251 */         setContentAreaFilled(((Boolean)paramObject).booleanValue());
/* 2252 */         this.contentAreaFilledSet = false;
/*      */       } 
/*      */     } else {
/* 2255 */       super.setUIProperty(paramString, paramObject);
/*      */     } 
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String paramString() {
/* 2275 */     String str1 = (this.defaultIcon != null && this.defaultIcon != this) ? this.defaultIcon.toString() : "";
/*      */ 
/*      */     
/* 2278 */     String str2 = (this.pressedIcon != null && this.pressedIcon != this) ? this.pressedIcon.toString() : "";
/*      */ 
/*      */     
/* 2281 */     String str3 = (this.disabledIcon != null && this.disabledIcon != this) ? this.disabledIcon.toString() : "";
/*      */ 
/*      */     
/* 2284 */     String str4 = (this.selectedIcon != null && this.selectedIcon != this) ? this.selectedIcon.toString() : "";
/*      */ 
/*      */     
/* 2287 */     String str5 = (this.disabledSelectedIcon != null && this.disabledSelectedIcon != this) ? this.disabledSelectedIcon.toString() : "";
/*      */ 
/*      */ 
/*      */     
/* 2291 */     String str6 = (this.rolloverIcon != null && this.rolloverIcon != this) ? this.rolloverIcon.toString() : "";
/*      */ 
/*      */     
/* 2294 */     String str7 = (this.rolloverSelectedIcon != null && this.rolloverSelectedIcon != this) ? this.rolloverSelectedIcon.toString() : "";
/*      */     
/* 2296 */     String str8 = this.paintBorder ? "true" : "false";
/* 2297 */     String str9 = this.paintFocus ? "true" : "false";
/* 2298 */     String str10 = this.rolloverEnabled ? "true" : "false";
/*      */     
/* 2300 */     return super.paramString() + ",defaultIcon=" + str1 + ",disabledIcon=" + str3 + ",disabledSelectedIcon=" + str5 + ",margin=" + this.margin + ",paintBorder=" + str8 + ",paintFocus=" + str9 + ",pressedIcon=" + str2 + ",rolloverEnabled=" + str10 + ",rolloverIcon=" + str6 + ",rolloverSelectedIcon=" + str7 + ",selectedIcon=" + str4 + ",text=" + this.text;
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Handler getHandler() {
/* 2317 */     if (this.handler == null) {
/* 2318 */       this.handler = new Handler();
/*      */     }
/* 2320 */     return this.handler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class Handler
/*      */     implements ActionListener, ChangeListener, ItemListener, Serializable
/*      */   {
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 2334 */       Object object = param1ChangeEvent.getSource();
/*      */       
/* 2336 */       AbstractButton.this.updateMnemonicProperties();
/* 2337 */       if (AbstractButton.this.isEnabled() != AbstractButton.this.model.isEnabled()) {
/* 2338 */         AbstractButton.this.setEnabled(AbstractButton.this.model.isEnabled());
/*      */       }
/* 2340 */       AbstractButton.this.fireStateChanged();
/* 2341 */       AbstractButton.this.repaint();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2348 */       AbstractButton.this.fireActionPerformed(param1ActionEvent);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void itemStateChanged(ItemEvent param1ItemEvent) {
/* 2355 */       AbstractButton.this.fireItemStateChanged(param1ItemEvent);
/* 2356 */       if (AbstractButton.this.shouldUpdateSelectedStateFromAction()) {
/* 2357 */         Action action = AbstractButton.this.getAction();
/* 2358 */         if (action != null && AbstractAction.hasSelectedKey(action)) {
/* 2359 */           boolean bool1 = AbstractButton.this.isSelected();
/* 2360 */           boolean bool2 = AbstractAction.isSelected(action);
/*      */           
/* 2362 */           if (bool2 != bool1) {
/* 2363 */             action.putValue("SwingSelectedKey", Boolean.valueOf(bool1));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract class AccessibleAbstractButton
/*      */     extends JComponent.AccessibleJComponent
/*      */     implements AccessibleAction, AccessibleValue, AccessibleText, AccessibleExtendedComponent
/*      */   {
/*      */     public String getAccessibleName() {
/* 2401 */       String str = this.accessibleName;
/*      */       
/* 2403 */       if (str == null) {
/* 2404 */         str = (String)AbstractButton.this.getClientProperty("AccessibleName");
/*      */       }
/* 2406 */       if (str == null) {
/* 2407 */         str = AbstractButton.this.getText();
/*      */       }
/* 2409 */       if (str == null) {
/* 2410 */         str = super.getAccessibleName();
/*      */       }
/* 2412 */       return str;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleIcon[] getAccessibleIcon() {
/* 2421 */       Icon icon = AbstractButton.this.getIcon();
/*      */       
/* 2423 */       if (icon instanceof Accessible) {
/*      */         
/* 2425 */         AccessibleContext accessibleContext = ((Accessible)icon).getAccessibleContext();
/* 2426 */         if (accessibleContext != null && accessibleContext instanceof AccessibleIcon) {
/* 2427 */           return new AccessibleIcon[] { (AccessibleIcon)accessibleContext };
/*      */         }
/*      */       } 
/* 2430 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleStateSet getAccessibleStateSet() {
/* 2441 */       AccessibleStateSet accessibleStateSet = super.getAccessibleStateSet();
/* 2442 */       if (AbstractButton.this.getModel().isArmed()) {
/* 2443 */         accessibleStateSet.add(AccessibleState.ARMED);
/*      */       }
/* 2445 */       if (AbstractButton.this.isFocusOwner()) {
/* 2446 */         accessibleStateSet.add(AccessibleState.FOCUSED);
/*      */       }
/* 2448 */       if (AbstractButton.this.getModel().isPressed()) {
/* 2449 */         accessibleStateSet.add(AccessibleState.PRESSED);
/*      */       }
/* 2451 */       if (AbstractButton.this.isSelected()) {
/* 2452 */         accessibleStateSet.add(AccessibleState.CHECKED);
/*      */       }
/* 2454 */       return accessibleStateSet;
/*      */     }
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
/*      */     
/*      */     public AccessibleRelationSet getAccessibleRelationSet() {
/* 2468 */       AccessibleRelationSet accessibleRelationSet = super.getAccessibleRelationSet();
/*      */       
/* 2470 */       if (!accessibleRelationSet.contains(AccessibleRelation.MEMBER_OF)) {
/*      */         
/* 2472 */         ButtonModel buttonModel = AbstractButton.this.getModel();
/* 2473 */         if (buttonModel != null && buttonModel instanceof DefaultButtonModel) {
/* 2474 */           ButtonGroup buttonGroup = ((DefaultButtonModel)buttonModel).getGroup();
/* 2475 */           if (buttonGroup != null) {
/*      */ 
/*      */             
/* 2478 */             int i = buttonGroup.getButtonCount();
/* 2479 */             Object[] arrayOfObject = new Object[i];
/* 2480 */             Enumeration<AbstractButton> enumeration = buttonGroup.getElements();
/* 2481 */             for (byte b = 0; b < i; b++) {
/* 2482 */               if (enumeration.hasMoreElements()) {
/* 2483 */                 arrayOfObject[b] = enumeration.nextElement();
/*      */               }
/*      */             } 
/* 2486 */             AccessibleRelation accessibleRelation = new AccessibleRelation(AccessibleRelation.MEMBER_OF);
/*      */             
/* 2488 */             accessibleRelation.setTarget(arrayOfObject);
/* 2489 */             accessibleRelationSet.add(accessibleRelation);
/*      */           } 
/*      */         } 
/*      */       } 
/* 2493 */       return accessibleRelationSet;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleAction getAccessibleAction() {
/* 2505 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleValue getAccessibleValue() {
/* 2517 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleActionCount() {
/* 2528 */       return 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getAccessibleActionDescription(int param1Int) {
/* 2537 */       if (param1Int == 0) {
/* 2538 */         return UIManager.getString("AbstractButton.clickText");
/*      */       }
/* 2540 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean doAccessibleAction(int param1Int) {
/* 2551 */       if (param1Int == 0) {
/* 2552 */         AbstractButton.this.doClick();
/* 2553 */         return true;
/*      */       } 
/* 2555 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Number getCurrentAccessibleValue() {
/* 2567 */       if (AbstractButton.this.isSelected()) {
/* 2568 */         return Integer.valueOf(1);
/*      */       }
/* 2570 */       return Integer.valueOf(0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean setCurrentAccessibleValue(Number param1Number) {
/* 2581 */       if (param1Number == null) {
/* 2582 */         return false;
/*      */       }
/* 2584 */       int i = param1Number.intValue();
/* 2585 */       if (i == 0) {
/* 2586 */         AbstractButton.this.setSelected(false);
/*      */       } else {
/* 2588 */         AbstractButton.this.setSelected(true);
/*      */       } 
/* 2590 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Number getMinimumAccessibleValue() {
/* 2599 */       return Integer.valueOf(0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Number getMaximumAccessibleValue() {
/* 2608 */       return Integer.valueOf(1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleText getAccessibleText() {
/* 2615 */       View view = (View)AbstractButton.this.getClientProperty("html");
/* 2616 */       if (view != null) {
/* 2617 */         return this;
/*      */       }
/* 2619 */       return null;
/*      */     }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getIndexAtPoint(Point param1Point) {
/* 2639 */       View view = (View)AbstractButton.this.getClientProperty("html");
/* 2640 */       if (view != null) {
/* 2641 */         Rectangle rectangle = getTextRectangle();
/* 2642 */         if (rectangle == null) {
/* 2643 */           return -1;
/*      */         }
/* 2645 */         Rectangle2D.Float float_ = new Rectangle2D.Float(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */         
/* 2647 */         Position.Bias[] arrayOfBias = new Position.Bias[1];
/* 2648 */         return view.viewToModel(param1Point.x, param1Point.y, float_, arrayOfBias);
/*      */       } 
/* 2650 */       return -1;
/*      */     }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Rectangle getCharacterBounds(int param1Int) {
/* 2671 */       View view = (View)AbstractButton.this.getClientProperty("html");
/* 2672 */       if (view != null) {
/* 2673 */         Rectangle rectangle = getTextRectangle();
/* 2674 */         if (rectangle == null) {
/* 2675 */           return null;
/*      */         }
/* 2677 */         Rectangle2D.Float float_ = new Rectangle2D.Float(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */ 
/*      */         
/*      */         try {
/* 2681 */           Shape shape = view.modelToView(param1Int, float_, Position.Bias.Forward);
/* 2682 */           return shape.getBounds();
/* 2683 */         } catch (BadLocationException badLocationException) {
/* 2684 */           return null;
/*      */         } 
/*      */       } 
/* 2687 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getCharCount() {
/* 2698 */       View view = (View)AbstractButton.this.getClientProperty("html");
/* 2699 */       if (view != null) {
/* 2700 */         Document document = view.getDocument();
/* 2701 */         if (document instanceof StyledDocument) {
/* 2702 */           StyledDocument styledDocument = (StyledDocument)document;
/* 2703 */           return styledDocument.getLength();
/*      */         } 
/*      */       } 
/* 2706 */       return AbstractButton.this.accessibleContext.getAccessibleName().length();
/*      */     }
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
/*      */     public int getCaretPosition() {
/* 2719 */       return -1;
/*      */     }
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
/*      */     
/*      */     public String getAtIndex(int param1Int1, int param1Int2) {
/* 2733 */       if (param1Int2 < 0 || param1Int2 >= getCharCount()) {
/* 2734 */         return null;
/*      */       }
/* 2736 */       switch (param1Int1) {
/*      */         case 1:
/*      */           try {
/* 2739 */             return getText(param1Int2, 1);
/* 2740 */           } catch (BadLocationException badLocationException) {
/* 2741 */             return null;
/*      */           } 
/*      */         case 2:
/*      */           try {
/* 2745 */             String str = getText(0, getCharCount());
/* 2746 */             BreakIterator breakIterator = BreakIterator.getWordInstance(getLocale());
/* 2747 */             breakIterator.setText(str);
/* 2748 */             int i = breakIterator.following(param1Int2);
/* 2749 */             return str.substring(breakIterator.previous(), i);
/* 2750 */           } catch (BadLocationException badLocationException) {
/* 2751 */             return null;
/*      */           } 
/*      */         case 3:
/*      */           try {
/* 2755 */             String str = getText(0, getCharCount());
/*      */             
/* 2757 */             BreakIterator breakIterator = BreakIterator.getSentenceInstance(getLocale());
/* 2758 */             breakIterator.setText(str);
/* 2759 */             int i = breakIterator.following(param1Int2);
/* 2760 */             return str.substring(breakIterator.previous(), i);
/* 2761 */           } catch (BadLocationException badLocationException) {
/* 2762 */             return null;
/*      */           } 
/*      */       } 
/* 2765 */       return null;
/*      */     }
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
/*      */ 
/*      */     
/*      */     public String getAfterIndex(int param1Int1, int param1Int2) {
/* 2780 */       if (param1Int2 < 0 || param1Int2 >= getCharCount()) {
/* 2781 */         return null;
/*      */       }
/* 2783 */       switch (param1Int1) {
/*      */         case 1:
/* 2785 */           if (param1Int2 + 1 >= getCharCount()) {
/* 2786 */             return null;
/*      */           }
/*      */           try {
/* 2789 */             return getText(param1Int2 + 1, 1);
/* 2790 */           } catch (BadLocationException badLocationException) {
/* 2791 */             return null;
/*      */           } 
/*      */         case 2:
/*      */           try {
/* 2795 */             String str = getText(0, getCharCount());
/* 2796 */             BreakIterator breakIterator = BreakIterator.getWordInstance(getLocale());
/* 2797 */             breakIterator.setText(str);
/* 2798 */             int i = breakIterator.following(param1Int2);
/* 2799 */             if (i == -1 || i >= str.length()) {
/* 2800 */               return null;
/*      */             }
/* 2802 */             int j = breakIterator.following(i);
/* 2803 */             if (j == -1 || j >= str.length()) {
/* 2804 */               return null;
/*      */             }
/* 2806 */             return str.substring(i, j);
/* 2807 */           } catch (BadLocationException badLocationException) {
/* 2808 */             return null;
/*      */           } 
/*      */         case 3:
/*      */           try {
/* 2812 */             String str = getText(0, getCharCount());
/*      */             
/* 2814 */             BreakIterator breakIterator = BreakIterator.getSentenceInstance(getLocale());
/* 2815 */             breakIterator.setText(str);
/* 2816 */             int i = breakIterator.following(param1Int2);
/* 2817 */             if (i == -1 || i > str.length()) {
/* 2818 */               return null;
/*      */             }
/* 2820 */             int j = breakIterator.following(i);
/* 2821 */             if (j == -1 || j > str.length()) {
/* 2822 */               return null;
/*      */             }
/* 2824 */             return str.substring(i, j);
/* 2825 */           } catch (BadLocationException badLocationException) {
/* 2826 */             return null;
/*      */           } 
/*      */       } 
/* 2829 */       return null;
/*      */     }
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
/*      */ 
/*      */     
/*      */     public String getBeforeIndex(int param1Int1, int param1Int2) {
/* 2844 */       if (param1Int2 < 0 || param1Int2 > getCharCount() - 1) {
/* 2845 */         return null;
/*      */       }
/* 2847 */       switch (param1Int1) {
/*      */         case 1:
/* 2849 */           if (param1Int2 == 0) {
/* 2850 */             return null;
/*      */           }
/*      */           try {
/* 2853 */             return getText(param1Int2 - 1, 1);
/* 2854 */           } catch (BadLocationException badLocationException) {
/* 2855 */             return null;
/*      */           } 
/*      */         case 2:
/*      */           try {
/* 2859 */             String str = getText(0, getCharCount());
/* 2860 */             BreakIterator breakIterator = BreakIterator.getWordInstance(getLocale());
/* 2861 */             breakIterator.setText(str);
/* 2862 */             int i = breakIterator.following(param1Int2);
/* 2863 */             i = breakIterator.previous();
/* 2864 */             int j = breakIterator.previous();
/* 2865 */             if (j == -1) {
/* 2866 */               return null;
/*      */             }
/* 2868 */             return str.substring(j, i);
/* 2869 */           } catch (BadLocationException badLocationException) {
/* 2870 */             return null;
/*      */           } 
/*      */         case 3:
/*      */           try {
/* 2874 */             String str = getText(0, getCharCount());
/*      */             
/* 2876 */             BreakIterator breakIterator = BreakIterator.getSentenceInstance(getLocale());
/* 2877 */             breakIterator.setText(str);
/* 2878 */             int i = breakIterator.following(param1Int2);
/* 2879 */             i = breakIterator.previous();
/* 2880 */             int j = breakIterator.previous();
/* 2881 */             if (j == -1) {
/* 2882 */               return null;
/*      */             }
/* 2884 */             return str.substring(j, i);
/* 2885 */           } catch (BadLocationException badLocationException) {
/* 2886 */             return null;
/*      */           } 
/*      */       } 
/* 2889 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeSet getCharacterAttribute(int param1Int) {
/* 2901 */       View view = (View)AbstractButton.this.getClientProperty("html");
/* 2902 */       if (view != null) {
/* 2903 */         Document document = view.getDocument();
/* 2904 */         if (document instanceof StyledDocument) {
/* 2905 */           StyledDocument styledDocument = (StyledDocument)document;
/* 2906 */           Element element = styledDocument.getCharacterElement(param1Int);
/* 2907 */           if (element != null) {
/* 2908 */             return element.getAttributes();
/*      */           }
/*      */         } 
/*      */       } 
/* 2912 */       return null;
/*      */     }
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
/*      */     public int getSelectionStart() {
/* 2925 */       return -1;
/*      */     }
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
/*      */     public int getSelectionEnd() {
/* 2938 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getSelectedText() {
/* 2949 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String getText(int param1Int1, int param1Int2) throws BadLocationException {
/* 2959 */       View view = (View)AbstractButton.this.getClientProperty("html");
/* 2960 */       if (view != null) {
/* 2961 */         Document document = view.getDocument();
/* 2962 */         if (document instanceof StyledDocument) {
/* 2963 */           StyledDocument styledDocument = (StyledDocument)document;
/* 2964 */           return styledDocument.getText(param1Int1, param1Int2);
/*      */         } 
/*      */       } 
/* 2967 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Rectangle getTextRectangle() {
/* 2975 */       String str1 = AbstractButton.this.getText();
/* 2976 */       Icon icon = AbstractButton.this.isEnabled() ? AbstractButton.this.getIcon() : AbstractButton.this.getDisabledIcon();
/*      */       
/* 2978 */       if (icon == null && str1 == null) {
/* 2979 */         return null;
/*      */       }
/*      */       
/* 2982 */       Rectangle rectangle1 = new Rectangle();
/* 2983 */       Rectangle rectangle2 = new Rectangle();
/* 2984 */       Rectangle rectangle3 = new Rectangle();
/* 2985 */       Insets insets = new Insets(0, 0, 0, 0);
/*      */       
/* 2987 */       insets = AbstractButton.this.getInsets(insets);
/* 2988 */       rectangle3.x = insets.left;
/* 2989 */       rectangle3.y = insets.top;
/* 2990 */       rectangle3.width = AbstractButton.this.getWidth() - insets.left + insets.right;
/* 2991 */       rectangle3.height = AbstractButton.this.getHeight() - insets.top + insets.bottom;
/*      */       
/* 2993 */       String str2 = SwingUtilities.layoutCompoundLabel(AbstractButton.this, 
/*      */           
/* 2995 */           getFontMetrics(getFont()), str1, icon, AbstractButton.this
/*      */ 
/*      */           
/* 2998 */           .getVerticalAlignment(), AbstractButton.this
/* 2999 */           .getHorizontalAlignment(), AbstractButton.this
/* 3000 */           .getVerticalTextPosition(), AbstractButton.this
/* 3001 */           .getHorizontalTextPosition(), rectangle3, rectangle1, rectangle2, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3007 */       return rectangle2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     AccessibleExtendedComponent getAccessibleExtendedComponent() {
/* 3018 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getToolTipText() {
/* 3029 */       return AbstractButton.this.getToolTipText();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getTitledBorderText() {
/* 3040 */       return super.getTitledBorderText();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleKeyBinding getAccessibleKeyBinding() {
/* 3052 */       int i = AbstractButton.this.getMnemonic();
/* 3053 */       if (i == 0) {
/* 3054 */         return null;
/*      */       }
/* 3056 */       return new ButtonKeyBinding(i);
/*      */     }
/*      */     
/*      */     class ButtonKeyBinding implements AccessibleKeyBinding {
/*      */       int mnemonic;
/*      */       
/*      */       ButtonKeyBinding(int param2Int) {
/* 3063 */         this.mnemonic = param2Int;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleKeyBindingCount() {
/* 3072 */         return 1;
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Object getAccessibleKeyBinding(int param2Int) {
/* 3101 */         if (param2Int != 0) {
/* 3102 */           throw new IllegalArgumentException();
/*      */         }
/* 3104 */         return KeyStroke.getKeyStroke(this.mnemonic, 0);
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/AbstractButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */