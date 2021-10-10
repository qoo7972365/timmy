/*     */ package sun.font;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import sun.awt.SunHints;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontConfigManager
/*     */ {
/*     */   static boolean fontConfigFailed = false;
/*  42 */   private static final FontConfigInfo fcInfo = new FontConfigInfo();
/*     */ 
/*     */   
/*     */   public static class FontConfigFont
/*     */   {
/*     */     public String familyName;
/*     */     
/*     */     public String styleStr;
/*     */     
/*     */     public String fullName;
/*     */     
/*     */     public String fontFile;
/*     */   }
/*     */   
/*     */   public static class FcCompFont
/*     */   {
/*     */     public String fcName;
/*     */     public String fcFamily;
/*     */     public String jdkName;
/*     */     public int style;
/*     */     public FontConfigManager.FontConfigFont firstFont;
/*     */     public FontConfigManager.FontConfigFont[] allFonts;
/*     */     public CompositeFont compFont;
/*     */   }
/*     */   
/*     */   public static class FontConfigInfo
/*     */   {
/*     */     public int fcVersion;
/*  70 */     public String[] cacheDirs = new String[4];
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
/*  82 */   private static String[] fontConfigNames = new String[] { "sans:regular:roman", "sans:bold:roman", "sans:regular:italic", "sans:bold:italic", "serif:regular:roman", "serif:bold:roman", "serif:regular:italic", "serif:bold:italic", "monospace:regular:roman", "monospace:bold:roman", "monospace:regular:italic", "monospace:bold:italic" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FcCompFont[] fontConfigFonts;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getFontConfigAAHint() {
/* 119 */     return getFontConfigAAHint("sans");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getFontConfigAAHint(String paramString) {
/* 128 */     if (FontUtilities.isWindows) {
/* 129 */       return null;
/*     */     }
/* 131 */     int i = getFontConfigAASettings(getFCLocaleStr(), paramString);
/* 132 */     if (i < 0) {
/* 133 */       return null;
/*     */     }
/* 135 */     return SunHints.Value.get(2, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getFCLocaleStr() {
/* 143 */     Locale locale = SunToolkit.getStartupLocale();
/* 144 */     String str1 = locale.getLanguage();
/* 145 */     String str2 = locale.getCountry();
/* 146 */     if (!str2.equals("")) {
/* 147 */       str1 = str1 + "-" + str2;
/*     */     }
/* 149 */     return str1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static native int getFontConfigVersion();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void initFontConfigFonts(boolean paramBoolean) {
/* 166 */     if (this.fontConfigFonts != null && (
/* 167 */       !paramBoolean || (this.fontConfigFonts[0]).allFonts != null)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 172 */     if (FontUtilities.isWindows || fontConfigFailed) {
/*     */       return;
/*     */     }
/*     */     
/* 176 */     long l = 0L;
/* 177 */     if (FontUtilities.isLogging()) {
/* 178 */       l = System.nanoTime();
/*     */     }
/*     */     
/* 181 */     FcCompFont[] arrayOfFcCompFont = new FcCompFont[fontConfigNames.length];
/*     */     
/* 183 */     for (byte b1 = 0; b1 < arrayOfFcCompFont.length; b1++) {
/* 184 */       arrayOfFcCompFont[b1] = new FcCompFont();
/* 185 */       (arrayOfFcCompFont[b1]).fcName = fontConfigNames[b1];
/* 186 */       int i = (arrayOfFcCompFont[b1]).fcName.indexOf(':');
/* 187 */       (arrayOfFcCompFont[b1]).fcFamily = (arrayOfFcCompFont[b1]).fcName.substring(0, i);
/* 188 */       (arrayOfFcCompFont[b1]).jdkName = FontUtilities.mapFcName((arrayOfFcCompFont[b1]).fcFamily);
/* 189 */       (arrayOfFcCompFont[b1]).style = b1 % 4;
/*     */     } 
/* 191 */     getFontConfig(getFCLocaleStr(), fcInfo, arrayOfFcCompFont, paramBoolean);
/* 192 */     FontConfigFont fontConfigFont = null;
/*     */     byte b2;
/* 194 */     for (b2 = 0; b2 < arrayOfFcCompFont.length; b2++) {
/* 195 */       FcCompFont fcCompFont = arrayOfFcCompFont[b2];
/* 196 */       if (fcCompFont.firstFont == null) {
/* 197 */         if (FontUtilities.isLogging()) {
/* 198 */           PlatformLogger platformLogger = FontUtilities.getLogger();
/* 199 */           platformLogger.info("Fontconfig returned no font for " + (arrayOfFcCompFont[b2]).fcName);
/*     */         } 
/*     */         
/* 202 */         fontConfigFailed = true;
/* 203 */       } else if (fontConfigFont == null) {
/* 204 */         fontConfigFont = fcCompFont.firstFont;
/*     */       } 
/*     */     } 
/*     */     
/* 208 */     if (fontConfigFont == null) {
/* 209 */       if (FontUtilities.isLogging()) {
/* 210 */         PlatformLogger platformLogger = FontUtilities.getLogger();
/* 211 */         platformLogger.info("Fontconfig returned no fonts at all.");
/*     */       } 
/* 213 */       fontConfigFailed = true; return;
/*     */     } 
/* 215 */     if (fontConfigFailed) {
/* 216 */       for (b2 = 0; b2 < arrayOfFcCompFont.length; b2++) {
/* 217 */         if ((arrayOfFcCompFont[b2]).firstFont == null) {
/* 218 */           (arrayOfFcCompFont[b2]).firstFont = fontConfigFont;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 223 */     this.fontConfigFonts = arrayOfFcCompFont;
/*     */     
/* 225 */     if (FontUtilities.isLogging()) {
/*     */       
/* 227 */       PlatformLogger platformLogger = FontUtilities.getLogger();
/*     */       
/* 229 */       long l1 = System.nanoTime();
/* 230 */       platformLogger.info("Time spent accessing fontconfig=" + ((l1 - l) / 1000000L) + "ms.");
/*     */ 
/*     */       
/* 233 */       for (byte b = 0; b < this.fontConfigFonts.length; b++) {
/* 234 */         FcCompFont fcCompFont = this.fontConfigFonts[b];
/* 235 */         platformLogger.info("FC font " + fcCompFont.fcName + " maps to family " + fcCompFont.firstFont.familyName + " in file " + fcCompFont.firstFont.fontFile);
/*     */ 
/*     */         
/* 238 */         if (fcCompFont.allFonts != null) {
/* 239 */           for (byte b3 = 0; b3 < fcCompFont.allFonts.length; b3++) {
/* 240 */             FontConfigFont fontConfigFont1 = fcCompFont.allFonts[b3];
/* 241 */             platformLogger.info("Family=" + fontConfigFont1.familyName + " Style=" + fontConfigFont1.styleStr + " Fullname=" + fontConfigFont1.fullName + " File=" + fontConfigFont1.fontFile);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PhysicalFont registerFromFcInfo(FcCompFont paramFcCompFont) {
/* 253 */     SunFontManager sunFontManager = SunFontManager.getInstance();
/*     */ 
/*     */ 
/*     */     
/* 257 */     String str1 = paramFcCompFont.firstFont.fontFile;
/* 258 */     int i = str1.length() - 4;
/* 259 */     if (i <= 0) {
/* 260 */       return null;
/*     */     }
/* 262 */     String str2 = str1.substring(i).toLowerCase();
/* 263 */     boolean bool = str2.equals(".ttc");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 270 */     PhysicalFont physicalFont = sunFontManager.getRegisteredFontFile(str1);
/* 271 */     if (physicalFont != null) {
/* 272 */       if (bool) {
/* 273 */         Font2D font2D = sunFontManager.findFont2D(paramFcCompFont.firstFont.familyName, paramFcCompFont.style, 0);
/*     */ 
/*     */         
/* 276 */         if (font2D instanceof PhysicalFont) {
/* 277 */           return (PhysicalFont)font2D;
/*     */         }
/* 279 */         return null;
/*     */       } 
/*     */       
/* 282 */       return physicalFont;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 290 */     physicalFont = sunFontManager.findJREDeferredFont(paramFcCompFont.firstFont.familyName, paramFcCompFont.style);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 296 */     if (physicalFont == null && sunFontManager
/* 297 */       .isDeferredFont(str1) == true) {
/* 298 */       physicalFont = sunFontManager.initialiseDeferredFont(paramFcCompFont.firstFont.fontFile);
/*     */       
/* 300 */       if (physicalFont != null) {
/* 301 */         if (bool) {
/* 302 */           Font2D font2D = sunFontManager.findFont2D(paramFcCompFont.firstFont.familyName, paramFcCompFont.style, 0);
/*     */ 
/*     */           
/* 305 */           if (font2D instanceof PhysicalFont) {
/* 306 */             return (PhysicalFont)font2D;
/*     */           }
/* 308 */           return null;
/*     */         } 
/*     */         
/* 311 */         return physicalFont;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 319 */     if (physicalFont == null) {
/* 320 */       byte b = -1;
/* 321 */       byte b1 = 6;
/*     */       
/* 323 */       if (str2.equals(".ttf") || bool) {
/* 324 */         b = 0;
/* 325 */         b1 = 3;
/* 326 */       } else if (str2.equals(".pfa") || str2.equals(".pfb")) {
/* 327 */         b = 1;
/* 328 */         b1 = 4;
/*     */       } 
/* 330 */       physicalFont = sunFontManager.registerFontFile(paramFcCompFont.firstFont.fontFile, null, b, true, b1);
/*     */     } 
/*     */     
/* 333 */     return physicalFont;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompositeFont getFontConfigFont(String paramString, int paramInt) {
/* 342 */     paramString = paramString.toLowerCase();
/*     */     
/* 344 */     initFontConfigFonts(false);
/* 345 */     if (this.fontConfigFonts == null)
/*     */     {
/*     */       
/* 348 */       return null;
/*     */     }
/*     */     
/* 351 */     FcCompFont fcCompFont = null;
/* 352 */     for (byte b1 = 0; b1 < this.fontConfigFonts.length; b1++) {
/* 353 */       if (paramString.equals((this.fontConfigFonts[b1]).fcFamily) && paramInt == (this.fontConfigFonts[b1]).style) {
/*     */         
/* 355 */         fcCompFont = this.fontConfigFonts[b1];
/*     */         break;
/*     */       } 
/*     */     } 
/* 359 */     if (fcCompFont == null) {
/* 360 */       fcCompFont = this.fontConfigFonts[0];
/*     */     }
/*     */     
/* 363 */     if (FontUtilities.isLogging()) {
/* 364 */       FontUtilities.getLogger()
/* 365 */         .info("FC name=" + paramString + " style=" + paramInt + " uses " + fcCompFont.firstFont.familyName + " in file: " + fcCompFont.firstFont.fontFile);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 370 */     if (fcCompFont.compFont != null) {
/* 371 */       return fcCompFont.compFont;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 377 */     FontManager fontManager = FontManagerFactory.getInstance();
/*     */     
/* 379 */     CompositeFont compositeFont = (CompositeFont)fontManager.findFont2D(fcCompFont.jdkName, paramInt, 2);
/*     */     
/* 381 */     if (fcCompFont.firstFont.familyName == null || fcCompFont.firstFont.fontFile == null)
/*     */     {
/* 383 */       return fcCompFont.compFont = compositeFont;
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
/* 394 */     FontFamily fontFamily = FontFamily.getFamily(fcCompFont.firstFont.familyName);
/* 395 */     PhysicalFont physicalFont = null;
/* 396 */     if (fontFamily != null) {
/* 397 */       Font2D font2D = fontFamily.getFontWithExactStyleMatch(fcCompFont.style);
/* 398 */       if (font2D instanceof PhysicalFont) {
/* 399 */         physicalFont = (PhysicalFont)font2D;
/*     */       }
/*     */     } 
/*     */     
/* 403 */     if (physicalFont == null || 
/* 404 */       !fcCompFont.firstFont.fontFile.equals(physicalFont.platName)) {
/* 405 */       physicalFont = registerFromFcInfo(fcCompFont);
/* 406 */       if (physicalFont == null) {
/* 407 */         return fcCompFont.compFont = compositeFont;
/*     */       }
/* 409 */       fontFamily = FontFamily.getFamily(physicalFont.getFamilyName(null));
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
/* 421 */     for (byte b2 = 0; b2 < this.fontConfigFonts.length; b2++) {
/* 422 */       FcCompFont fcCompFont1 = this.fontConfigFonts[b2];
/* 423 */       if (fcCompFont1 != fcCompFont && physicalFont
/* 424 */         .getFamilyName(null).equals(fcCompFont1.firstFont.familyName) && 
/* 425 */         !fcCompFont1.firstFont.fontFile.equals(physicalFont.platName) && fontFamily
/* 426 */         .getFontWithExactStyleMatch(fcCompFont1.style) == null)
/*     */       {
/* 428 */         registerFromFcInfo(this.fontConfigFonts[b2]);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 436 */     return fcCompFont.compFont = new CompositeFont(physicalFont, compositeFont);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FcCompFont[] getFontConfigFonts() {
/* 446 */     return this.fontConfigFonts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void getFontConfig(String paramString, FontConfigInfo paramFontConfigInfo, FcCompFont[] paramArrayOfFcCompFont, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void populateFontConfig(FcCompFont[] paramArrayOfFcCompFont) {
/* 458 */     this.fontConfigFonts = paramArrayOfFcCompFont;
/*     */   }
/*     */   
/*     */   FcCompFont[] loadFontConfig() {
/* 462 */     initFontConfigFonts(true);
/* 463 */     return this.fontConfigFonts;
/*     */   }
/*     */   
/*     */   FontConfigInfo getFontConfigInfo() {
/* 467 */     initFontConfigFonts(true);
/* 468 */     return fcInfo;
/*     */   }
/*     */   
/*     */   private static native int getFontConfigAASettings(String paramString1, String paramString2);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/FontConfigManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */