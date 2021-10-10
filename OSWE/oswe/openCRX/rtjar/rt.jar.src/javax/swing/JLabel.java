/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.beans.Transient;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.text.BreakIterator;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleExtendedComponent;
/*      */ import javax.accessibility.AccessibleIcon;
/*      */ import javax.accessibility.AccessibleKeyBinding;
/*      */ import javax.accessibility.AccessibleRelation;
/*      */ import javax.accessibility.AccessibleRelationSet;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleText;
/*      */ import javax.swing.plaf.LabelUI;
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
/*      */ public class JLabel
/*      */   extends JComponent
/*      */   implements SwingConstants, Accessible
/*      */ {
/*      */   private static final String uiClassID = "LabelUI";
/*  116 */   private int mnemonic = 0;
/*  117 */   private int mnemonicIndex = -1;
/*      */   
/*  119 */   private String text = "";
/*  120 */   private Icon defaultIcon = null;
/*  121 */   private Icon disabledIcon = null;
/*      */   
/*      */   private boolean disabledIconSet = false;
/*  124 */   private int verticalAlignment = 0;
/*  125 */   private int horizontalAlignment = 10;
/*  126 */   private int verticalTextPosition = 0;
/*  127 */   private int horizontalTextPosition = 11;
/*  128 */   private int iconTextGap = 4;
/*      */   
/*  130 */   protected Component labelFor = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final String LABELED_BY_PROPERTY = "labeledBy";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JLabel(String paramString, Icon paramIcon, int paramInt) {
/*  161 */     setText(paramString);
/*  162 */     setIcon(paramIcon);
/*  163 */     setHorizontalAlignment(paramInt);
/*  164 */     updateUI();
/*  165 */     setAlignmentX(0.0F);
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
/*      */   public JLabel(String paramString, int paramInt) {
/*  183 */     this(paramString, (Icon)null, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JLabel(String paramString) {
/*  194 */     this(paramString, (Icon)null, 10);
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
/*      */   public JLabel(Icon paramIcon, int paramInt) {
/*  212 */     this((String)null, paramIcon, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JLabel(Icon paramIcon) {
/*  223 */     this((String)null, paramIcon, 0);
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
/*      */   public JLabel() {
/*  235 */     this("", (Icon)null, 10);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LabelUI getUI() {
/*  245 */     return (LabelUI)this.ui;
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
/*      */   public void setUI(LabelUI paramLabelUI) {
/*  261 */     setUI(paramLabelUI);
/*      */     
/*  263 */     if (!this.disabledIconSet && this.disabledIcon != null) {
/*  264 */       setDisabledIcon((Icon)null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateUI() {
/*  275 */     setUI((LabelUI)UIManager.getUI(this));
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
/*      */   public String getUIClassID() {
/*  289 */     return "LabelUI";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getText() {
/*  300 */     return this.text;
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
/*      */   public void setText(String paramString) {
/*  323 */     String str1 = null;
/*  324 */     if (this.accessibleContext != null) {
/*  325 */       str1 = this.accessibleContext.getAccessibleName();
/*      */     }
/*      */     
/*  328 */     String str2 = this.text;
/*  329 */     this.text = paramString;
/*  330 */     firePropertyChange("text", str2, paramString);
/*      */     
/*  332 */     setDisplayedMnemonicIndex(
/*  333 */         SwingUtilities.findDisplayedMnemonicIndex(paramString, 
/*  334 */           getDisplayedMnemonic()));
/*      */     
/*  336 */     if (this.accessibleContext != null && this.accessibleContext
/*  337 */       .getAccessibleName() != str1) {
/*  338 */       this.accessibleContext.firePropertyChange("AccessibleVisibleData", str1, this.accessibleContext
/*      */ 
/*      */           
/*  341 */           .getAccessibleName());
/*      */     }
/*  343 */     if (paramString == null || str2 == null || !paramString.equals(str2)) {
/*  344 */       revalidate();
/*  345 */       repaint();
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
/*      */   public Icon getIcon() {
/*  357 */     return this.defaultIcon;
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
/*      */   public void setIcon(Icon paramIcon) {
/*  378 */     Icon icon = this.defaultIcon;
/*  379 */     this.defaultIcon = paramIcon;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  386 */     if (this.defaultIcon != icon && !this.disabledIconSet) {
/*  387 */       this.disabledIcon = null;
/*      */     }
/*      */     
/*  390 */     firePropertyChange("icon", icon, this.defaultIcon);
/*      */     
/*  392 */     if (this.accessibleContext != null && icon != this.defaultIcon) {
/*  393 */       this.accessibleContext.firePropertyChange("AccessibleVisibleData", icon, this.defaultIcon);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  402 */     if (this.defaultIcon != icon) {
/*  403 */       if (this.defaultIcon == null || icon == null || this.defaultIcon
/*      */         
/*  405 */         .getIconWidth() != icon.getIconWidth() || this.defaultIcon
/*  406 */         .getIconHeight() != icon.getIconHeight()) {
/*  407 */         revalidate();
/*      */       }
/*  409 */       repaint();
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
/*      */   @Transient
/*      */   public Icon getDisabledIcon() {
/*  429 */     if (!this.disabledIconSet && this.disabledIcon == null && this.defaultIcon != null) {
/*  430 */       this.disabledIcon = UIManager.getLookAndFeel().getDisabledIcon(this, this.defaultIcon);
/*  431 */       if (this.disabledIcon != null) {
/*  432 */         firePropertyChange("disabledIcon", (Object)null, this.disabledIcon);
/*      */       }
/*      */     } 
/*  435 */     return this.disabledIcon;
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
/*      */   public void setDisabledIcon(Icon paramIcon) {
/*  454 */     Icon icon = this.disabledIcon;
/*  455 */     this.disabledIcon = paramIcon;
/*  456 */     this.disabledIconSet = (paramIcon != null);
/*  457 */     firePropertyChange("disabledIcon", icon, paramIcon);
/*  458 */     if (paramIcon != icon) {
/*  459 */       if (paramIcon == null || icon == null || paramIcon
/*  460 */         .getIconWidth() != icon.getIconWidth() || paramIcon
/*  461 */         .getIconHeight() != icon.getIconHeight()) {
/*  462 */         revalidate();
/*      */       }
/*  464 */       if (!isEnabled()) {
/*  465 */         repaint();
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
/*      */   public void setDisplayedMnemonic(int paramInt) {
/*  486 */     int i = this.mnemonic;
/*  487 */     this.mnemonic = paramInt;
/*  488 */     firePropertyChange("displayedMnemonic", i, this.mnemonic);
/*      */     
/*  490 */     setDisplayedMnemonicIndex(
/*  491 */         SwingUtilities.findDisplayedMnemonicIndex(getText(), this.mnemonic));
/*      */     
/*  493 */     if (paramInt != i) {
/*  494 */       revalidate();
/*  495 */       repaint();
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
/*      */   public void setDisplayedMnemonic(char paramChar) {
/*  507 */     int i = KeyEvent.getExtendedKeyCodeForChar(paramChar);
/*  508 */     if (i != 0) {
/*  509 */       setDisplayedMnemonic(i);
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
/*      */   public int getDisplayedMnemonic() {
/*  527 */     return this.mnemonic;
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
/*      */   public void setDisplayedMnemonicIndex(int paramInt) throws IllegalArgumentException {
/*  559 */     int i = this.mnemonicIndex;
/*  560 */     if (paramInt == -1) {
/*  561 */       this.mnemonicIndex = -1;
/*      */     } else {
/*  563 */       String str = getText();
/*  564 */       byte b = (str == null) ? 0 : str.length();
/*  565 */       if (paramInt < -1 || paramInt >= b) {
/*  566 */         throw new IllegalArgumentException("index == " + paramInt);
/*      */       }
/*      */     } 
/*  569 */     this.mnemonicIndex = paramInt;
/*  570 */     firePropertyChange("displayedMnemonicIndex", i, paramInt);
/*  571 */     if (paramInt != i) {
/*  572 */       revalidate();
/*  573 */       repaint();
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
/*  586 */     return this.mnemonicIndex;
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
/*      */   protected int checkHorizontalKey(int paramInt, String paramString) {
/*  600 */     if (paramInt == 2 || paramInt == 0 || paramInt == 4 || paramInt == 10 || paramInt == 11)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  605 */       return paramInt;
/*      */     }
/*      */     
/*  608 */     throw new IllegalArgumentException(paramString);
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
/*      */   protected int checkVerticalKey(int paramInt, String paramString) {
/*  624 */     if (paramInt == 1 || paramInt == 0 || paramInt == 3) {
/*  625 */       return paramInt;
/*      */     }
/*      */     
/*  628 */     throw new IllegalArgumentException(paramString);
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
/*      */   public int getIconTextGap() {
/*  642 */     return this.iconTextGap;
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
/*  662 */     int i = this.iconTextGap;
/*  663 */     this.iconTextGap = paramInt;
/*  664 */     firePropertyChange("iconTextGap", i, paramInt);
/*  665 */     if (paramInt != i) {
/*  666 */       revalidate();
/*  667 */       repaint();
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
/*      */   public int getVerticalAlignment() {
/*  686 */     return this.verticalAlignment;
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
/*      */   public void setVerticalAlignment(int paramInt) {
/*  712 */     if (paramInt == this.verticalAlignment)
/*  713 */       return;  int i = this.verticalAlignment;
/*  714 */     this.verticalAlignment = checkVerticalKey(paramInt, "verticalAlignment");
/*  715 */     firePropertyChange("verticalAlignment", i, this.verticalAlignment);
/*  716 */     repaint();
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
/*  735 */     return this.horizontalAlignment;
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
/*  764 */     if (paramInt == this.horizontalAlignment)
/*  765 */       return;  int i = this.horizontalAlignment;
/*  766 */     this.horizontalAlignment = checkHorizontalKey(paramInt, "horizontalAlignment");
/*      */     
/*  768 */     firePropertyChange("horizontalAlignment", i, this.horizontalAlignment);
/*      */     
/*  770 */     repaint();
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
/*      */   public int getVerticalTextPosition() {
/*  788 */     return this.verticalTextPosition;
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
/*      */   public void setVerticalTextPosition(int paramInt) {
/*  818 */     if (paramInt == this.verticalTextPosition)
/*  819 */       return;  int i = this.verticalTextPosition;
/*  820 */     this.verticalTextPosition = checkVerticalKey(paramInt, "verticalTextPosition");
/*      */     
/*  822 */     firePropertyChange("verticalTextPosition", i, this.verticalTextPosition);
/*  823 */     revalidate();
/*  824 */     repaint();
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
/*      */   public int getHorizontalTextPosition() {
/*  843 */     return this.horizontalTextPosition;
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
/*      */   public void setHorizontalTextPosition(int paramInt) {
/*  874 */     int i = this.horizontalTextPosition;
/*  875 */     this.horizontalTextPosition = checkHorizontalKey(paramInt, "horizontalTextPosition");
/*      */     
/*  877 */     firePropertyChange("horizontalTextPosition", i, this.horizontalTextPosition);
/*      */     
/*  879 */     revalidate();
/*  880 */     repaint();
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
/*      */   public boolean imageUpdate(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  895 */     if (!isShowing() || (
/*  896 */       !SwingUtilities.doesIconReferenceImage(getIcon(), paramImage) && 
/*  897 */       !SwingUtilities.doesIconReferenceImage(this.disabledIcon, paramImage)))
/*      */     {
/*  899 */       return false;
/*      */     }
/*  901 */     return super.imageUpdate(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*  910 */     paramObjectOutputStream.defaultWriteObject();
/*  911 */     if (getUIClassID().equals("LabelUI")) {
/*  912 */       byte b = JComponent.getWriteObjCounter(this);
/*  913 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/*  914 */       if (b == 0 && this.ui != null) {
/*  915 */         this.ui.installUI(this);
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
/*      */   protected String paramString() {
/*  931 */     String str5, str6, str7, str8, str1 = (this.text != null) ? this.text : "";
/*      */ 
/*      */ 
/*      */     
/*  935 */     String str2 = (this.defaultIcon != null && this.defaultIcon != this) ? this.defaultIcon.toString() : "";
/*      */ 
/*      */     
/*  938 */     String str3 = (this.disabledIcon != null && this.disabledIcon != this) ? this.disabledIcon.toString() : "";
/*      */     
/*  940 */     String str4 = (this.labelFor != null) ? this.labelFor.toString() : "";
/*      */     
/*  942 */     if (this.verticalAlignment == 1)
/*  943 */     { str5 = "TOP"; }
/*  944 */     else if (this.verticalAlignment == 0)
/*  945 */     { str5 = "CENTER"; }
/*  946 */     else if (this.verticalAlignment == 3)
/*  947 */     { str5 = "BOTTOM"; }
/*  948 */     else { str5 = ""; }
/*      */     
/*  950 */     if (this.horizontalAlignment == 2)
/*  951 */     { str6 = "LEFT"; }
/*  952 */     else if (this.horizontalAlignment == 0)
/*  953 */     { str6 = "CENTER"; }
/*  954 */     else if (this.horizontalAlignment == 4)
/*  955 */     { str6 = "RIGHT"; }
/*  956 */     else if (this.horizontalAlignment == 10)
/*  957 */     { str6 = "LEADING"; }
/*  958 */     else if (this.horizontalAlignment == 11)
/*  959 */     { str6 = "TRAILING"; }
/*  960 */     else { str6 = ""; }
/*      */     
/*  962 */     if (this.verticalTextPosition == 1)
/*  963 */     { str7 = "TOP"; }
/*  964 */     else if (this.verticalTextPosition == 0)
/*  965 */     { str7 = "CENTER"; }
/*  966 */     else if (this.verticalTextPosition == 3)
/*  967 */     { str7 = "BOTTOM"; }
/*  968 */     else { str7 = ""; }
/*      */     
/*  970 */     if (this.horizontalTextPosition == 2)
/*  971 */     { str8 = "LEFT"; }
/*  972 */     else if (this.horizontalTextPosition == 0)
/*  973 */     { str8 = "CENTER"; }
/*  974 */     else if (this.horizontalTextPosition == 4)
/*  975 */     { str8 = "RIGHT"; }
/*  976 */     else if (this.horizontalTextPosition == 10)
/*  977 */     { str8 = "LEADING"; }
/*  978 */     else if (this.horizontalTextPosition == 11)
/*  979 */     { str8 = "TRAILING"; }
/*  980 */     else { str8 = ""; }
/*      */     
/*  982 */     return super.paramString() + ",defaultIcon=" + str2 + ",disabledIcon=" + str3 + ",horizontalAlignment=" + str6 + ",horizontalTextPosition=" + str8 + ",iconTextGap=" + this.iconTextGap + ",labelFor=" + str4 + ",text=" + str1 + ",verticalAlignment=" + str5 + ",verticalTextPosition=" + str7;
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
/*      */   public Component getLabelFor() {
/* 1011 */     return this.labelFor;
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
/*      */   public void setLabelFor(Component paramComponent) {
/* 1032 */     Component component = this.labelFor;
/* 1033 */     this.labelFor = paramComponent;
/* 1034 */     firePropertyChange("labelFor", component, paramComponent);
/*      */     
/* 1036 */     if (component instanceof JComponent) {
/* 1037 */       ((JComponent)component).putClientProperty("labeledBy", (Object)null);
/*      */     }
/* 1039 */     if (paramComponent instanceof JComponent) {
/* 1040 */       ((JComponent)paramComponent).putClientProperty("labeledBy", this);
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
/*      */   public AccessibleContext getAccessibleContext() {
/* 1053 */     if (this.accessibleContext == null) {
/* 1054 */       this.accessibleContext = new AccessibleJLabel();
/*      */     }
/* 1056 */     return this.accessibleContext;
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
/*      */   protected class AccessibleJLabel
/*      */     extends JComponent.AccessibleJComponent
/*      */     implements AccessibleText, AccessibleExtendedComponent
/*      */   {
/*      */     public String getAccessibleName() {
/* 1083 */       String str = this.accessibleName;
/*      */       
/* 1085 */       if (str == null) {
/* 1086 */         str = (String)JLabel.this.getClientProperty("AccessibleName");
/*      */       }
/* 1088 */       if (str == null) {
/* 1089 */         str = JLabel.this.getText();
/*      */       }
/* 1091 */       if (str == null) {
/* 1092 */         str = super.getAccessibleName();
/*      */       }
/* 1094 */       return str;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleRole getAccessibleRole() {
/* 1105 */       return AccessibleRole.LABEL;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleIcon[] getAccessibleIcon() {
/* 1114 */       Icon icon = JLabel.this.getIcon();
/* 1115 */       if (icon instanceof Accessible) {
/*      */         
/* 1117 */         AccessibleContext accessibleContext = ((Accessible)icon).getAccessibleContext();
/* 1118 */         if (accessibleContext != null && accessibleContext instanceof AccessibleIcon) {
/* 1119 */           return new AccessibleIcon[] { (AccessibleIcon)accessibleContext };
/*      */         }
/*      */       } 
/* 1122 */       return null;
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
/*      */     public AccessibleRelationSet getAccessibleRelationSet() {
/* 1135 */       AccessibleRelationSet accessibleRelationSet = super.getAccessibleRelationSet();
/*      */       
/* 1137 */       if (!accessibleRelationSet.contains(AccessibleRelation.LABEL_FOR)) {
/* 1138 */         Component component = JLabel.this.getLabelFor();
/* 1139 */         if (component != null) {
/* 1140 */           AccessibleRelation accessibleRelation = new AccessibleRelation(AccessibleRelation.LABEL_FOR);
/*      */           
/* 1142 */           accessibleRelation.setTarget(component);
/* 1143 */           accessibleRelationSet.add(accessibleRelation);
/*      */         } 
/*      */       } 
/* 1146 */       return accessibleRelationSet;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleText getAccessibleText() {
/* 1153 */       View view = (View)JLabel.this.getClientProperty("html");
/* 1154 */       if (view != null) {
/* 1155 */         return this;
/*      */       }
/* 1157 */       return null;
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
/*      */     public int getIndexAtPoint(Point param1Point) {
/* 1172 */       View view = (View)JLabel.this.getClientProperty("html");
/* 1173 */       if (view != null) {
/* 1174 */         Rectangle rectangle = getTextRectangle();
/* 1175 */         if (rectangle == null) {
/* 1176 */           return -1;
/*      */         }
/* 1178 */         Rectangle2D.Float float_ = new Rectangle2D.Float(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */         
/* 1180 */         Position.Bias[] arrayOfBias = new Position.Bias[1];
/* 1181 */         return view.viewToModel(param1Point.x, param1Point.y, float_, arrayOfBias);
/*      */       } 
/* 1183 */       return -1;
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
/*      */     public Rectangle getCharacterBounds(int param1Int) {
/* 1198 */       View view = (View)JLabel.this.getClientProperty("html");
/* 1199 */       if (view != null) {
/* 1200 */         Rectangle rectangle = getTextRectangle();
/* 1201 */         if (rectangle == null) {
/* 1202 */           return null;
/*      */         }
/* 1204 */         Rectangle2D.Float float_ = new Rectangle2D.Float(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */ 
/*      */         
/*      */         try {
/* 1208 */           Shape shape = view.modelToView(param1Int, float_, Position.Bias.Forward);
/* 1209 */           return shape.getBounds();
/* 1210 */         } catch (BadLocationException badLocationException) {
/* 1211 */           return null;
/*      */         } 
/*      */       } 
/* 1214 */       return null;
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
/* 1225 */       View view = (View)JLabel.this.getClientProperty("html");
/* 1226 */       if (view != null) {
/* 1227 */         Document document = view.getDocument();
/* 1228 */         if (document instanceof StyledDocument) {
/* 1229 */           StyledDocument styledDocument = (StyledDocument)document;
/* 1230 */           return styledDocument.getLength();
/*      */         } 
/*      */       } 
/* 1233 */       return JLabel.this.accessibleContext.getAccessibleName().length();
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
/* 1246 */       return -1;
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
/* 1260 */       if (param1Int2 < 0 || param1Int2 >= getCharCount()) {
/* 1261 */         return null;
/*      */       }
/* 1263 */       switch (param1Int1) {
/*      */         case 1:
/*      */           try {
/* 1266 */             return getText(param1Int2, 1);
/* 1267 */           } catch (BadLocationException badLocationException) {
/* 1268 */             return null;
/*      */           } 
/*      */         case 2:
/*      */           try {
/* 1272 */             String str = getText(0, getCharCount());
/* 1273 */             BreakIterator breakIterator = BreakIterator.getWordInstance(getLocale());
/* 1274 */             breakIterator.setText(str);
/* 1275 */             int i = breakIterator.following(param1Int2);
/* 1276 */             return str.substring(breakIterator.previous(), i);
/* 1277 */           } catch (BadLocationException badLocationException) {
/* 1278 */             return null;
/*      */           } 
/*      */         case 3:
/*      */           try {
/* 1282 */             String str = getText(0, getCharCount());
/*      */             
/* 1284 */             BreakIterator breakIterator = BreakIterator.getSentenceInstance(getLocale());
/* 1285 */             breakIterator.setText(str);
/* 1286 */             int i = breakIterator.following(param1Int2);
/* 1287 */             return str.substring(breakIterator.previous(), i);
/* 1288 */           } catch (BadLocationException badLocationException) {
/* 1289 */             return null;
/*      */           } 
/*      */       } 
/* 1292 */       return null;
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
/* 1307 */       if (param1Int2 < 0 || param1Int2 >= getCharCount()) {
/* 1308 */         return null;
/*      */       }
/* 1310 */       switch (param1Int1) {
/*      */         case 1:
/* 1312 */           if (param1Int2 + 1 >= getCharCount()) {
/* 1313 */             return null;
/*      */           }
/*      */           try {
/* 1316 */             return getText(param1Int2 + 1, 1);
/* 1317 */           } catch (BadLocationException badLocationException) {
/* 1318 */             return null;
/*      */           } 
/*      */         case 2:
/*      */           try {
/* 1322 */             String str = getText(0, getCharCount());
/* 1323 */             BreakIterator breakIterator = BreakIterator.getWordInstance(getLocale());
/* 1324 */             breakIterator.setText(str);
/* 1325 */             int i = breakIterator.following(param1Int2);
/* 1326 */             if (i == -1 || i >= str.length()) {
/* 1327 */               return null;
/*      */             }
/* 1329 */             int j = breakIterator.following(i);
/* 1330 */             if (j == -1 || j >= str.length()) {
/* 1331 */               return null;
/*      */             }
/* 1333 */             return str.substring(i, j);
/* 1334 */           } catch (BadLocationException badLocationException) {
/* 1335 */             return null;
/*      */           } 
/*      */         case 3:
/*      */           try {
/* 1339 */             String str = getText(0, getCharCount());
/*      */             
/* 1341 */             BreakIterator breakIterator = BreakIterator.getSentenceInstance(getLocale());
/* 1342 */             breakIterator.setText(str);
/* 1343 */             int i = breakIterator.following(param1Int2);
/* 1344 */             if (i == -1 || i > str.length()) {
/* 1345 */               return null;
/*      */             }
/* 1347 */             int j = breakIterator.following(i);
/* 1348 */             if (j == -1 || j > str.length()) {
/* 1349 */               return null;
/*      */             }
/* 1351 */             return str.substring(i, j);
/* 1352 */           } catch (BadLocationException badLocationException) {
/* 1353 */             return null;
/*      */           } 
/*      */       } 
/* 1356 */       return null;
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
/* 1371 */       if (param1Int2 < 0 || param1Int2 > getCharCount() - 1) {
/* 1372 */         return null;
/*      */       }
/* 1374 */       switch (param1Int1) {
/*      */         case 1:
/* 1376 */           if (param1Int2 == 0) {
/* 1377 */             return null;
/*      */           }
/*      */           try {
/* 1380 */             return getText(param1Int2 - 1, 1);
/* 1381 */           } catch (BadLocationException badLocationException) {
/* 1382 */             return null;
/*      */           } 
/*      */         case 2:
/*      */           try {
/* 1386 */             String str = getText(0, getCharCount());
/* 1387 */             BreakIterator breakIterator = BreakIterator.getWordInstance(getLocale());
/* 1388 */             breakIterator.setText(str);
/* 1389 */             int i = breakIterator.following(param1Int2);
/* 1390 */             i = breakIterator.previous();
/* 1391 */             int j = breakIterator.previous();
/* 1392 */             if (j == -1) {
/* 1393 */               return null;
/*      */             }
/* 1395 */             return str.substring(j, i);
/* 1396 */           } catch (BadLocationException badLocationException) {
/* 1397 */             return null;
/*      */           } 
/*      */         case 3:
/*      */           try {
/* 1401 */             String str = getText(0, getCharCount());
/*      */             
/* 1403 */             BreakIterator breakIterator = BreakIterator.getSentenceInstance(getLocale());
/* 1404 */             breakIterator.setText(str);
/* 1405 */             int i = breakIterator.following(param1Int2);
/* 1406 */             i = breakIterator.previous();
/* 1407 */             int j = breakIterator.previous();
/* 1408 */             if (j == -1) {
/* 1409 */               return null;
/*      */             }
/* 1411 */             return str.substring(j, i);
/* 1412 */           } catch (BadLocationException badLocationException) {
/* 1413 */             return null;
/*      */           } 
/*      */       } 
/* 1416 */       return null;
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
/* 1428 */       View view = (View)JLabel.this.getClientProperty("html");
/* 1429 */       if (view != null) {
/* 1430 */         Document document = view.getDocument();
/* 1431 */         if (document instanceof StyledDocument) {
/* 1432 */           StyledDocument styledDocument = (StyledDocument)document;
/* 1433 */           Element element = styledDocument.getCharacterElement(param1Int);
/* 1434 */           if (element != null) {
/* 1435 */             return element.getAttributes();
/*      */           }
/*      */         } 
/*      */       } 
/* 1439 */       return null;
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
/* 1452 */       return -1;
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
/* 1465 */       return -1;
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
/* 1476 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String getText(int param1Int1, int param1Int2) throws BadLocationException {
/* 1486 */       View view = (View)JLabel.this.getClientProperty("html");
/* 1487 */       if (view != null) {
/* 1488 */         Document document = view.getDocument();
/* 1489 */         if (document instanceof StyledDocument) {
/* 1490 */           StyledDocument styledDocument = (StyledDocument)document;
/* 1491 */           return styledDocument.getText(param1Int1, param1Int2);
/*      */         } 
/*      */       } 
/* 1494 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Rectangle getTextRectangle() {
/* 1502 */       String str1 = JLabel.this.getText();
/* 1503 */       Icon icon = JLabel.this.isEnabled() ? JLabel.this.getIcon() : JLabel.this.getDisabledIcon();
/*      */       
/* 1505 */       if (icon == null && str1 == null) {
/* 1506 */         return null;
/*      */       }
/*      */       
/* 1509 */       Rectangle rectangle1 = new Rectangle();
/* 1510 */       Rectangle rectangle2 = new Rectangle();
/* 1511 */       Rectangle rectangle3 = new Rectangle();
/* 1512 */       Insets insets = new Insets(0, 0, 0, 0);
/*      */       
/* 1514 */       insets = JLabel.this.getInsets(insets);
/* 1515 */       rectangle3.x = insets.left;
/* 1516 */       rectangle3.y = insets.top;
/* 1517 */       rectangle3.width = JLabel.this.getWidth() - insets.left + insets.right;
/* 1518 */       rectangle3.height = JLabel.this.getHeight() - insets.top + insets.bottom;
/*      */       
/* 1520 */       String str2 = SwingUtilities.layoutCompoundLabel(JLabel.this, 
/*      */           
/* 1522 */           getFontMetrics(getFont()), str1, icon, JLabel.this
/*      */ 
/*      */           
/* 1525 */           .getVerticalAlignment(), JLabel.this
/* 1526 */           .getHorizontalAlignment(), JLabel.this
/* 1527 */           .getVerticalTextPosition(), JLabel.this
/* 1528 */           .getHorizontalTextPosition(), rectangle3, rectangle1, rectangle2, JLabel.this
/*      */ 
/*      */ 
/*      */           
/* 1532 */           .getIconTextGap());
/*      */       
/* 1534 */       return rectangle2;
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
/* 1545 */       return this;
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
/* 1556 */       return JLabel.this.getToolTipText();
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
/* 1567 */       return super.getTitledBorderText();
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
/* 1579 */       int i = JLabel.this.getDisplayedMnemonic();
/* 1580 */       if (i == 0) {
/* 1581 */         return null;
/*      */       }
/* 1583 */       return new LabelKeyBinding(i);
/*      */     }
/*      */     
/*      */     class LabelKeyBinding implements AccessibleKeyBinding {
/*      */       int mnemonic;
/*      */       
/*      */       LabelKeyBinding(int param2Int) {
/* 1590 */         this.mnemonic = param2Int;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleKeyBindingCount() {
/* 1599 */         return 1;
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
/* 1628 */         if (param2Int != 0) {
/* 1629 */           throw new IllegalArgumentException();
/*      */         }
/* 1631 */         return KeyStroke.getKeyStroke(this.mnemonic, 0);
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JLabel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */