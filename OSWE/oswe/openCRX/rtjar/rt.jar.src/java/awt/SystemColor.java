/*     */ package java.awt;
/*     */ 
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import sun.awt.AWTAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SystemColor
/*     */   extends Color
/*     */   implements Serializable
/*     */ {
/*     */   public static final int DESKTOP = 0;
/*     */   public static final int ACTIVE_CAPTION = 1;
/*     */   public static final int ACTIVE_CAPTION_TEXT = 2;
/*     */   public static final int ACTIVE_CAPTION_BORDER = 3;
/*     */   public static final int INACTIVE_CAPTION = 4;
/*     */   public static final int INACTIVE_CAPTION_TEXT = 5;
/*     */   public static final int INACTIVE_CAPTION_BORDER = 6;
/*     */   public static final int WINDOW = 7;
/*     */   public static final int WINDOW_BORDER = 8;
/*     */   public static final int WINDOW_TEXT = 9;
/*     */   public static final int MENU = 10;
/*     */   public static final int MENU_TEXT = 11;
/*     */   public static final int TEXT = 12;
/*     */   public static final int TEXT_TEXT = 13;
/*     */   public static final int TEXT_HIGHLIGHT = 14;
/*     */   public static final int TEXT_HIGHLIGHT_TEXT = 15;
/*     */   public static final int TEXT_INACTIVE_TEXT = 16;
/*     */   public static final int CONTROL = 17;
/*     */   public static final int CONTROL_TEXT = 18;
/*     */   public static final int CONTROL_HIGHLIGHT = 19;
/*     */   public static final int CONTROL_LT_HIGHLIGHT = 20;
/*     */   public static final int CONTROL_SHADOW = 21;
/*     */   public static final int CONTROL_DK_SHADOW = 22;
/*     */   public static final int SCROLLBAR = 23;
/*     */   public static final int INFO = 24;
/*     */   public static final int INFO_TEXT = 25;
/*     */   public static final int NUM_COLORS = 26;
/* 251 */   private static int[] systemColors = new int[] { -16753572, -16777088, -1, -4144960, -8355712, -4144960, -4144960, -1, -16777216, -16777216, -4144960, -16777216, -4144960, -16777216, -16777088, -1, -8355712, -4144960, -16777216, -1, -2039584, -8355712, -16777216, -2039584, -2039808, -16777216 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 283 */   public static final SystemColor desktop = new SystemColor((byte)0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 288 */   public static final SystemColor activeCaption = new SystemColor((byte)1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 293 */   public static final SystemColor activeCaptionText = new SystemColor((byte)2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 298 */   public static final SystemColor activeCaptionBorder = new SystemColor((byte)3);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 303 */   public static final SystemColor inactiveCaption = new SystemColor((byte)4);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 308 */   public static final SystemColor inactiveCaptionText = new SystemColor((byte)5);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 313 */   public static final SystemColor inactiveCaptionBorder = new SystemColor((byte)6);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 318 */   public static final SystemColor window = new SystemColor((byte)7);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 323 */   public static final SystemColor windowBorder = new SystemColor((byte)8);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 328 */   public static final SystemColor windowText = new SystemColor((byte)9);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 333 */   public static final SystemColor menu = new SystemColor((byte)10);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 338 */   public static final SystemColor menuText = new SystemColor((byte)11);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 344 */   public static final SystemColor text = new SystemColor((byte)12);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 350 */   public static final SystemColor textText = new SystemColor((byte)13);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 356 */   public static final SystemColor textHighlight = new SystemColor((byte)14);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 362 */   public static final SystemColor textHighlightText = new SystemColor((byte)15);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 367 */   public static final SystemColor textInactiveText = new SystemColor((byte)16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 373 */   public static final SystemColor control = new SystemColor((byte)17);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 379 */   public static final SystemColor controlText = new SystemColor((byte)18);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 386 */   public static final SystemColor controlHighlight = new SystemColor((byte)19);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 393 */   public static final SystemColor controlLtHighlight = new SystemColor((byte)20);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 400 */   public static final SystemColor controlShadow = new SystemColor((byte)21);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 407 */   public static final SystemColor controlDkShadow = new SystemColor((byte)22);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 412 */   public static final SystemColor scrollbar = new SystemColor((byte)23);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 417 */   public static final SystemColor info = new SystemColor((byte)24);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 422 */   public static final SystemColor infoText = new SystemColor((byte)25);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 4503142729533789064L;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient int index;
/*     */ 
/*     */ 
/*     */   
/* 434 */   private static SystemColor[] systemColorObjects = new SystemColor[] { desktop, activeCaption, activeCaptionText, activeCaptionBorder, inactiveCaption, inactiveCaptionText, inactiveCaptionBorder, window, windowBorder, windowText, menu, menuText, text, textText, textHighlight, textHighlightText, textInactiveText, control, controlText, controlHighlight, controlLtHighlight, controlShadow, controlDkShadow, scrollbar, info, infoText };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 464 */     AWTAccessor.setSystemColorAccessor(SystemColor::updateSystemColors);
/* 465 */     updateSystemColors();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void updateSystemColors() {
/* 472 */     if (!GraphicsEnvironment.isHeadless()) {
/* 473 */       Toolkit.getDefaultToolkit().loadSystemColors(systemColors);
/*     */     }
/* 475 */     for (byte b = 0; b < systemColors.length; b++) {
/* 476 */       (systemColorObjects[b]).value = systemColors[b];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SystemColor(byte paramByte) {
/* 485 */     super(systemColors[paramByte]);
/* 486 */     this.index = paramByte;
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
/*     */   public String toString() {
/* 499 */     return getClass().getName() + "[i=" + this.index + "]";
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
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 523 */     return systemColorObjects[this.value];
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
/*     */   private Object writeReplace() throws ObjectStreamException {
/* 544 */     SystemColor systemColor = new SystemColor((byte)this.index);
/* 545 */     systemColor.value = this.index;
/* 546 */     return systemColor;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/SystemColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */