/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ThemeReader
/*     */ {
/*     */   public static boolean isThemed() {
/*  45 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isXPStyleEnabled() {
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void paintBackground(int[] paramArrayOfint, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {}
/*     */ 
/*     */   
/*     */   public static Insets getThemeMargins(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/*  57 */     return null;
/*     */   }
/*     */   
/*     */   public static boolean isThemePartDefined(String paramString, int paramInt1, int paramInt2) {
/*  61 */     return false;
/*     */   }
/*     */   
/*     */   public static Color getColor(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/*  65 */     return null;
/*     */   }
/*     */   
/*     */   public static int getInt(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/*  69 */     return 0;
/*     */   }
/*     */   
/*     */   public static int getEnum(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/*  73 */     return 0;
/*     */   }
/*     */   
/*     */   public static boolean getBoolean(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/*  77 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean getSysBoolean(String paramString, int paramInt) {
/*  81 */     return false;
/*     */   }
/*     */   
/*     */   public static Point getPoint(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/*  85 */     return null;
/*     */   }
/*     */   
/*     */   public static Dimension getPosition(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/*  89 */     return null;
/*     */   }
/*     */   
/*     */   public static Dimension getPartSize(String paramString, int paramInt1, int paramInt2) {
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static long getThemeTransitionDuration(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  98 */     return 0L;
/*     */   }
/*     */   
/*     */   public static boolean isGetThemeTransitionDurationDefined() {
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Insets getThemeBackgroundContentMargins(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 107 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/windows/ThemeReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */