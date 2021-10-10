/*     */ package sun.swing;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.LayoutStyle;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
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
/*     */ public class DefaultLayoutStyle
/*     */   extends LayoutStyle
/*     */ {
/*  41 */   private static final DefaultLayoutStyle INSTANCE = new DefaultLayoutStyle();
/*     */ 
/*     */   
/*     */   public static LayoutStyle getInstance() {
/*  45 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPreferredGap(JComponent paramJComponent1, JComponent paramJComponent2, LayoutStyle.ComponentPlacement paramComponentPlacement, int paramInt, Container paramContainer) {
/*  51 */     if (paramJComponent1 == null || paramJComponent2 == null || paramComponentPlacement == null) {
/*  52 */       throw new NullPointerException();
/*     */     }
/*     */     
/*  55 */     checkPosition(paramInt);
/*     */     
/*  57 */     if (paramComponentPlacement == LayoutStyle.ComponentPlacement.INDENT && (paramInt == 3 || paramInt == 7)) {
/*     */ 
/*     */       
/*  60 */       int i = getIndent(paramJComponent1, paramInt);
/*  61 */       if (i > 0) {
/*  62 */         return i;
/*     */       }
/*     */     } 
/*  65 */     return (paramComponentPlacement == LayoutStyle.ComponentPlacement.UNRELATED) ? 12 : 6;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getContainerGap(JComponent paramJComponent, int paramInt, Container paramContainer) {
/*  71 */     if (paramJComponent == null) {
/*  72 */       throw new NullPointerException();
/*     */     }
/*  74 */     checkPosition(paramInt);
/*  75 */     return 6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isLabelAndNonlabel(JComponent paramJComponent1, JComponent paramJComponent2, int paramInt) {
/*  84 */     if (paramInt == 3 || paramInt == 7) {
/*     */       
/*  86 */       boolean bool1 = paramJComponent1 instanceof javax.swing.JLabel;
/*  87 */       boolean bool2 = paramJComponent2 instanceof javax.swing.JLabel;
/*  88 */       return ((bool1 || bool2) && bool1 != bool2);
/*     */     } 
/*  90 */     return false;
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
/*     */   protected int getButtonGap(JComponent paramJComponent1, JComponent paramJComponent2, int paramInt1, int paramInt2) {
/* 108 */     paramInt2 -= getButtonGap(paramJComponent1, paramInt1);
/* 109 */     if (paramInt2 > 0) {
/* 110 */       paramInt2 -= getButtonGap(paramJComponent2, flipDirection(paramInt1));
/*     */     }
/* 112 */     if (paramInt2 < 0) {
/* 113 */       return 0;
/*     */     }
/* 115 */     return paramInt2;
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
/*     */   protected int getButtonGap(JComponent paramJComponent, int paramInt1, int paramInt2) {
/* 131 */     paramInt2 -= getButtonGap(paramJComponent, paramInt1);
/* 132 */     return Math.max(paramInt2, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getButtonGap(JComponent paramJComponent, int paramInt) {
/* 140 */     String str = paramJComponent.getUIClassID();
/* 141 */     if ((str == "CheckBoxUI" || str == "RadioButtonUI") && 
/* 142 */       !((AbstractButton)paramJComponent).isBorderPainted()) {
/* 143 */       Border border = paramJComponent.getBorder();
/* 144 */       if (border instanceof javax.swing.plaf.UIResource) {
/* 145 */         return getInset(paramJComponent, paramInt);
/*     */       }
/*     */     } 
/* 148 */     return 0;
/*     */   }
/*     */   
/*     */   private void checkPosition(int paramInt) {
/* 152 */     if (paramInt != 1 && paramInt != 5 && paramInt != 7 && paramInt != 3)
/*     */     {
/*     */ 
/*     */       
/* 156 */       throw new IllegalArgumentException();
/*     */     }
/*     */   }
/*     */   
/*     */   protected int flipDirection(int paramInt) {
/* 161 */     switch (paramInt) {
/*     */       case 1:
/* 163 */         return 5;
/*     */       case 5:
/* 165 */         return 1;
/*     */       case 3:
/* 167 */         return 7;
/*     */       case 7:
/* 169 */         return 3;
/*     */     } 
/*     */     assert false;
/* 172 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getIndent(JComponent paramJComponent, int paramInt) {
/* 181 */     String str = paramJComponent.getUIClassID();
/* 182 */     if (str == "CheckBoxUI" || str == "RadioButtonUI") {
/* 183 */       AbstractButton abstractButton = (AbstractButton)paramJComponent;
/* 184 */       Insets insets = paramJComponent.getInsets();
/* 185 */       Icon icon = getIcon(abstractButton);
/* 186 */       int i = abstractButton.getIconTextGap();
/* 187 */       if (isLeftAligned(abstractButton, paramInt))
/* 188 */         return insets.left + icon.getIconWidth() + i; 
/* 189 */       if (isRightAligned(abstractButton, paramInt)) {
/* 190 */         return insets.right + icon.getIconWidth() + i;
/*     */       }
/*     */     } 
/* 193 */     return 0;
/*     */   }
/*     */   
/*     */   private Icon getIcon(AbstractButton paramAbstractButton) {
/* 197 */     Icon icon = paramAbstractButton.getIcon();
/* 198 */     if (icon != null) {
/* 199 */       return icon;
/*     */     }
/* 201 */     String str = null;
/* 202 */     if (paramAbstractButton instanceof javax.swing.JCheckBox) {
/* 203 */       str = "CheckBox.icon";
/* 204 */     } else if (paramAbstractButton instanceof javax.swing.JRadioButton) {
/* 205 */       str = "RadioButton.icon";
/*     */     } 
/* 207 */     if (str != null) {
/* 208 */       Object object = UIManager.get(str);
/* 209 */       if (object instanceof Icon) {
/* 210 */         return (Icon)object;
/*     */       }
/*     */     } 
/* 213 */     return null;
/*     */   }
/*     */   
/*     */   private boolean isLeftAligned(AbstractButton paramAbstractButton, int paramInt) {
/* 217 */     if (paramInt == 7) {
/* 218 */       boolean bool = paramAbstractButton.getComponentOrientation().isLeftToRight();
/* 219 */       int i = paramAbstractButton.getHorizontalAlignment();
/* 220 */       return ((bool && (i == 2 || i == 10)) || (!bool && i == 11));
/*     */     } 
/*     */ 
/*     */     
/* 224 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isRightAligned(AbstractButton paramAbstractButton, int paramInt) {
/* 228 */     if (paramInt == 3) {
/* 229 */       boolean bool = paramAbstractButton.getComponentOrientation().isLeftToRight();
/* 230 */       int i = paramAbstractButton.getHorizontalAlignment();
/* 231 */       return ((bool && (i == 4 || i == 11)) || (!bool && i == 10));
/*     */     } 
/*     */ 
/*     */     
/* 235 */     return false;
/*     */   }
/*     */   
/*     */   private int getInset(JComponent paramJComponent, int paramInt) {
/* 239 */     return getInset(paramJComponent.getInsets(), paramInt);
/*     */   }
/*     */   
/*     */   private int getInset(Insets paramInsets, int paramInt) {
/* 243 */     if (paramInsets == null) {
/* 244 */       return 0;
/*     */     }
/* 246 */     switch (paramInt) {
/*     */       case 1:
/* 248 */         return paramInsets.top;
/*     */       case 5:
/* 250 */         return paramInsets.bottom;
/*     */       case 3:
/* 252 */         return paramInsets.right;
/*     */       case 7:
/* 254 */         return paramInsets.left;
/*     */     } 
/*     */     assert false;
/* 257 */     return 0;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/DefaultLayoutStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */