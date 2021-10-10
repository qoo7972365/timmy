/*     */ package java.awt;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
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
/*     */ public final class ComponentOrientation
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4113291392143563828L;
/*     */   private static final int UNK_BIT = 1;
/*     */   private static final int HORIZ_BIT = 2;
/*     */   private static final int LTR_BIT = 4;
/* 107 */   public static final ComponentOrientation LEFT_TO_RIGHT = new ComponentOrientation(6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public static final ComponentOrientation RIGHT_TO_LEFT = new ComponentOrientation(2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public static final ComponentOrientation UNKNOWN = new ComponentOrientation(7);
/*     */ 
/*     */ 
/*     */   
/*     */   private int orientation;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHorizontal() {
/* 131 */     return ((this.orientation & 0x2) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLeftToRight() {
/* 141 */     return ((this.orientation & 0x4) != 0);
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
/*     */   public static ComponentOrientation getOrientation(Locale paramLocale) {
/* 153 */     String str = paramLocale.getLanguage();
/* 154 */     if ("iw".equals(str) || "ar".equals(str) || "fa"
/* 155 */       .equals(str) || "ur".equals(str))
/*     */     {
/* 157 */       return RIGHT_TO_LEFT;
/*     */     }
/* 159 */     return LEFT_TO_RIGHT;
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
/*     */   @Deprecated
/*     */   public static ComponentOrientation getOrientation(ResourceBundle paramResourceBundle) {
/* 179 */     ComponentOrientation componentOrientation = null;
/*     */     
/*     */     try {
/* 182 */       componentOrientation = (ComponentOrientation)paramResourceBundle.getObject("Orientation");
/*     */     }
/* 184 */     catch (Exception exception) {}
/*     */ 
/*     */     
/* 187 */     if (componentOrientation == null) {
/* 188 */       componentOrientation = getOrientation(paramResourceBundle.getLocale());
/*     */     }
/* 190 */     if (componentOrientation == null) {
/* 191 */       componentOrientation = getOrientation(Locale.getDefault());
/*     */     }
/* 193 */     return componentOrientation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ComponentOrientation(int paramInt) {
/* 200 */     this.orientation = paramInt;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/ComponentOrientation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */