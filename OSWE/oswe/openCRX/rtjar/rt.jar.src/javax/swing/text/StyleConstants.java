/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import javax.swing.Icon;
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
/*     */ public class StyleConstants
/*     */ {
/*     */   public static final String ComponentElementName = "component";
/*     */   public static final String IconElementName = "icon";
/*  65 */   public static final Object NameAttribute = new StyleConstants("name");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public static final Object ResolveAttribute = new StyleConstants("resolver");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static final Object ModelAttribute = new StyleConstants("model");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  85 */     return this.representation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   public static final Object BidiLevel = new CharacterConstants("bidiLevel");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public static final Object FontFamily = new FontConstants("family");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public static final Object Family = FontFamily;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public static final Object FontSize = new FontConstants("size");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public static final Object Size = FontSize;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public static final Object Bold = new FontConstants("bold");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   public static final Object Italic = new FontConstants("italic");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   public static final Object Underline = new CharacterConstants("underline");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   public static final Object StrikeThrough = new CharacterConstants("strikethrough");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public static final Object Superscript = new CharacterConstants("superscript");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   public static final Object Subscript = new CharacterConstants("subscript");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   public static final Object Foreground = new ColorConstants("foreground");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   public static final Object Background = new ColorConstants("background");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 163 */   public static final Object ComponentAttribute = new CharacterConstants("component");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   public static final Object IconAttribute = new CharacterConstants("icon");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public static final Object ComposedTextAttribute = new StyleConstants("composed text");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 184 */   public static final Object FirstLineIndent = new ParagraphConstants("FirstLineIndent");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 191 */   public static final Object LeftIndent = new ParagraphConstants("LeftIndent");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   public static final Object RightIndent = new ParagraphConstants("RightIndent");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 205 */   public static final Object LineSpacing = new ParagraphConstants("LineSpacing");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 211 */   public static final Object SpaceAbove = new ParagraphConstants("SpaceAbove");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 217 */   public static final Object SpaceBelow = new ParagraphConstants("SpaceBelow");
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
/* 230 */   public static final Object Alignment = new ParagraphConstants("Alignment");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 236 */   public static final Object TabSet = new ParagraphConstants("TabSet");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 241 */   public static final Object Orientation = new ParagraphConstants("Orientation");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ALIGN_LEFT = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ALIGN_CENTER = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ALIGN_RIGHT = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ALIGN_JUSTIFIED = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getBidiLevel(AttributeSet paramAttributeSet) {
/* 285 */     Integer integer = (Integer)paramAttributeSet.getAttribute(BidiLevel);
/* 286 */     if (integer != null) {
/* 287 */       return integer.intValue();
/*     */     }
/* 289 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setBidiLevel(MutableAttributeSet paramMutableAttributeSet, int paramInt) {
/* 299 */     paramMutableAttributeSet.addAttribute(BidiLevel, Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Component getComponent(AttributeSet paramAttributeSet) {
/* 309 */     return (Component)paramAttributeSet.getAttribute(ComponentAttribute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setComponent(MutableAttributeSet paramMutableAttributeSet, Component paramComponent) {
/* 319 */     paramMutableAttributeSet.addAttribute("$ename", "component");
/* 320 */     paramMutableAttributeSet.addAttribute(ComponentAttribute, paramComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Icon getIcon(AttributeSet paramAttributeSet) {
/* 330 */     return (Icon)paramAttributeSet.getAttribute(IconAttribute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setIcon(MutableAttributeSet paramMutableAttributeSet, Icon paramIcon) {
/* 340 */     paramMutableAttributeSet.addAttribute("$ename", "icon");
/* 341 */     paramMutableAttributeSet.addAttribute(IconAttribute, paramIcon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getFontFamily(AttributeSet paramAttributeSet) {
/* 351 */     String str = (String)paramAttributeSet.getAttribute(FontFamily);
/* 352 */     if (str == null) {
/* 353 */       str = "Monospaced";
/*     */     }
/* 355 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setFontFamily(MutableAttributeSet paramMutableAttributeSet, String paramString) {
/* 365 */     paramMutableAttributeSet.addAttribute(FontFamily, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFontSize(AttributeSet paramAttributeSet) {
/* 375 */     Integer integer = (Integer)paramAttributeSet.getAttribute(FontSize);
/* 376 */     if (integer != null) {
/* 377 */       return integer.intValue();
/*     */     }
/* 379 */     return 12;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setFontSize(MutableAttributeSet paramMutableAttributeSet, int paramInt) {
/* 389 */     paramMutableAttributeSet.addAttribute(FontSize, Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isBold(AttributeSet paramAttributeSet) {
/* 399 */     Boolean bool = (Boolean)paramAttributeSet.getAttribute(Bold);
/* 400 */     if (bool != null) {
/* 401 */       return bool.booleanValue();
/*     */     }
/* 403 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setBold(MutableAttributeSet paramMutableAttributeSet, boolean paramBoolean) {
/* 413 */     paramMutableAttributeSet.addAttribute(Bold, Boolean.valueOf(paramBoolean));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isItalic(AttributeSet paramAttributeSet) {
/* 423 */     Boolean bool = (Boolean)paramAttributeSet.getAttribute(Italic);
/* 424 */     if (bool != null) {
/* 425 */       return bool.booleanValue();
/*     */     }
/* 427 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setItalic(MutableAttributeSet paramMutableAttributeSet, boolean paramBoolean) {
/* 437 */     paramMutableAttributeSet.addAttribute(Italic, Boolean.valueOf(paramBoolean));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isUnderline(AttributeSet paramAttributeSet) {
/* 447 */     Boolean bool = (Boolean)paramAttributeSet.getAttribute(Underline);
/* 448 */     if (bool != null) {
/* 449 */       return bool.booleanValue();
/*     */     }
/* 451 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isStrikeThrough(AttributeSet paramAttributeSet) {
/* 461 */     Boolean bool = (Boolean)paramAttributeSet.getAttribute(StrikeThrough);
/* 462 */     if (bool != null) {
/* 463 */       return bool.booleanValue();
/*     */     }
/* 465 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSuperscript(AttributeSet paramAttributeSet) {
/* 476 */     Boolean bool = (Boolean)paramAttributeSet.getAttribute(Superscript);
/* 477 */     if (bool != null) {
/* 478 */       return bool.booleanValue();
/*     */     }
/* 480 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSubscript(AttributeSet paramAttributeSet) {
/* 491 */     Boolean bool = (Boolean)paramAttributeSet.getAttribute(Subscript);
/* 492 */     if (bool != null) {
/* 493 */       return bool.booleanValue();
/*     */     }
/* 495 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setUnderline(MutableAttributeSet paramMutableAttributeSet, boolean paramBoolean) {
/* 506 */     paramMutableAttributeSet.addAttribute(Underline, Boolean.valueOf(paramBoolean));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setStrikeThrough(MutableAttributeSet paramMutableAttributeSet, boolean paramBoolean) {
/* 516 */     paramMutableAttributeSet.addAttribute(StrikeThrough, Boolean.valueOf(paramBoolean));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setSuperscript(MutableAttributeSet paramMutableAttributeSet, boolean paramBoolean) {
/* 526 */     paramMutableAttributeSet.addAttribute(Superscript, Boolean.valueOf(paramBoolean));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setSubscript(MutableAttributeSet paramMutableAttributeSet, boolean paramBoolean) {
/* 536 */     paramMutableAttributeSet.addAttribute(Subscript, Boolean.valueOf(paramBoolean));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color getForeground(AttributeSet paramAttributeSet) {
/* 547 */     Color color = (Color)paramAttributeSet.getAttribute(Foreground);
/* 548 */     if (color == null) {
/* 549 */       color = Color.black;
/*     */     }
/* 551 */     return color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setForeground(MutableAttributeSet paramMutableAttributeSet, Color paramColor) {
/* 561 */     paramMutableAttributeSet.addAttribute(Foreground, paramColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color getBackground(AttributeSet paramAttributeSet) {
/* 571 */     Color color = (Color)paramAttributeSet.getAttribute(Background);
/* 572 */     if (color == null) {
/* 573 */       color = Color.black;
/*     */     }
/* 575 */     return color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setBackground(MutableAttributeSet paramMutableAttributeSet, Color paramColor) {
/* 585 */     paramMutableAttributeSet.addAttribute(Background, paramColor);
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
/*     */   public static float getFirstLineIndent(AttributeSet paramAttributeSet) {
/* 598 */     Float float_ = (Float)paramAttributeSet.getAttribute(FirstLineIndent);
/* 599 */     if (float_ != null) {
/* 600 */       return float_.floatValue();
/*     */     }
/* 602 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setFirstLineIndent(MutableAttributeSet paramMutableAttributeSet, float paramFloat) {
/* 612 */     paramMutableAttributeSet.addAttribute(FirstLineIndent, new Float(paramFloat));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getRightIndent(AttributeSet paramAttributeSet) {
/* 622 */     Float float_ = (Float)paramAttributeSet.getAttribute(RightIndent);
/* 623 */     if (float_ != null) {
/* 624 */       return float_.floatValue();
/*     */     }
/* 626 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setRightIndent(MutableAttributeSet paramMutableAttributeSet, float paramFloat) {
/* 636 */     paramMutableAttributeSet.addAttribute(RightIndent, new Float(paramFloat));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getLeftIndent(AttributeSet paramAttributeSet) {
/* 646 */     Float float_ = (Float)paramAttributeSet.getAttribute(LeftIndent);
/* 647 */     if (float_ != null) {
/* 648 */       return float_.floatValue();
/*     */     }
/* 650 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setLeftIndent(MutableAttributeSet paramMutableAttributeSet, float paramFloat) {
/* 660 */     paramMutableAttributeSet.addAttribute(LeftIndent, new Float(paramFloat));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getLineSpacing(AttributeSet paramAttributeSet) {
/* 670 */     Float float_ = (Float)paramAttributeSet.getAttribute(LineSpacing);
/* 671 */     if (float_ != null) {
/* 672 */       return float_.floatValue();
/*     */     }
/* 674 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setLineSpacing(MutableAttributeSet paramMutableAttributeSet, float paramFloat) {
/* 684 */     paramMutableAttributeSet.addAttribute(LineSpacing, new Float(paramFloat));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getSpaceAbove(AttributeSet paramAttributeSet) {
/* 694 */     Float float_ = (Float)paramAttributeSet.getAttribute(SpaceAbove);
/* 695 */     if (float_ != null) {
/* 696 */       return float_.floatValue();
/*     */     }
/* 698 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setSpaceAbove(MutableAttributeSet paramMutableAttributeSet, float paramFloat) {
/* 708 */     paramMutableAttributeSet.addAttribute(SpaceAbove, new Float(paramFloat));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getSpaceBelow(AttributeSet paramAttributeSet) {
/* 718 */     Float float_ = (Float)paramAttributeSet.getAttribute(SpaceBelow);
/* 719 */     if (float_ != null) {
/* 720 */       return float_.floatValue();
/*     */     }
/* 722 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setSpaceBelow(MutableAttributeSet paramMutableAttributeSet, float paramFloat) {
/* 732 */     paramMutableAttributeSet.addAttribute(SpaceBelow, new Float(paramFloat));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getAlignment(AttributeSet paramAttributeSet) {
/* 742 */     Integer integer = (Integer)paramAttributeSet.getAttribute(Alignment);
/* 743 */     if (integer != null) {
/* 744 */       return integer.intValue();
/*     */     }
/* 746 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setAlignment(MutableAttributeSet paramMutableAttributeSet, int paramInt) {
/* 756 */     paramMutableAttributeSet.addAttribute(Alignment, Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TabSet getTabSet(AttributeSet paramAttributeSet) {
/* 766 */     return (TabSet)paramAttributeSet.getAttribute(TabSet);
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
/*     */   public static void setTabSet(MutableAttributeSet paramMutableAttributeSet, TabSet paramTabSet) {
/* 778 */     paramMutableAttributeSet.addAttribute(TabSet, paramTabSet);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 783 */   static Object[] keys = new Object[] { NameAttribute, ResolveAttribute, BidiLevel, FontFamily, FontSize, Bold, Italic, Underline, StrikeThrough, Superscript, Subscript, Foreground, Background, ComponentAttribute, IconAttribute, FirstLineIndent, LeftIndent, RightIndent, LineSpacing, SpaceAbove, SpaceBelow, Alignment, TabSet, Orientation, ModelAttribute, ComposedTextAttribute };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String representation;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   StyleConstants(String paramString) {
/* 794 */     this.representation = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ParagraphConstants
/*     */     extends StyleConstants
/*     */     implements AttributeSet.ParagraphAttribute
/*     */   {
/*     */     private ParagraphConstants(String param1String) {
/* 808 */       super(param1String);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CharacterConstants
/*     */     extends StyleConstants
/*     */     implements AttributeSet.CharacterAttribute
/*     */   {
/*     */     private CharacterConstants(String param1String) {
/* 821 */       super(param1String);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ColorConstants
/*     */     extends StyleConstants
/*     */     implements AttributeSet.ColorAttribute, AttributeSet.CharacterAttribute
/*     */   {
/*     */     private ColorConstants(String param1String) {
/* 834 */       super(param1String);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class FontConstants
/*     */     extends StyleConstants
/*     */     implements AttributeSet.FontAttribute, AttributeSet.CharacterAttribute
/*     */   {
/*     */     private FontConstants(String param1String) {
/* 847 */       super(param1String);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/StyleConstants.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */