/*     */ package sun.awt;
/*     */ 
/*     */ import sun.font.FcFontConfiguration;
/*     */ import sun.font.FontConfigManager;
/*     */ import sun.font.SunFontManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FcFontManager
/*     */   extends SunFontManager
/*     */ {
/*  37 */   private FontConfigManager fcManager = null;
/*     */ 
/*     */   
/*     */   public synchronized FontConfigManager getFontConfigManager() {
/*  41 */     if (this.fcManager == null) {
/*  42 */       this.fcManager = new FontConfigManager();
/*     */     }
/*     */     
/*  45 */     return this.fcManager;
/*     */   }
/*     */ 
/*     */   
/*     */   protected FontConfiguration createFontConfiguration() {
/*  50 */     FcFontConfiguration fcFontConfiguration = new FcFontConfiguration(this);
/*  51 */     if (fcFontConfiguration.init()) {
/*  52 */       return (FontConfiguration)fcFontConfiguration;
/*     */     }
/*  54 */     throw new InternalError("failed to initialize fontconfig");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontConfiguration createFontConfiguration(boolean paramBoolean1, boolean paramBoolean2) {
/*  61 */     FcFontConfiguration fcFontConfiguration = new FcFontConfiguration(this, paramBoolean1, paramBoolean2);
/*     */     
/*  63 */     if (fcFontConfiguration.init()) {
/*  64 */       return (FontConfiguration)fcFontConfiguration;
/*     */     }
/*  66 */     throw new InternalError("failed to initialize fontconfig");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String[] getDefaultPlatformFont() {
/*  72 */     String[] arrayOfString = new String[2];
/*  73 */     getFontConfigManager().initFontConfigFonts(false);
/*     */     
/*  75 */     FontConfigManager.FcCompFont[] arrayOfFcCompFont = getFontConfigManager().getFontConfigFonts();
/*  76 */     for (byte b = 0; b < arrayOfFcCompFont.length; b++) {
/*  77 */       if ("sans".equals((arrayOfFcCompFont[b]).fcFamily) && 0 == (arrayOfFcCompFont[b]).style) {
/*     */         
/*  79 */         arrayOfString[0] = (arrayOfFcCompFont[b]).firstFont.familyName;
/*  80 */         arrayOfString[1] = (arrayOfFcCompFont[b]).firstFont.fontFile;
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  88 */     if (arrayOfString[0] == null) {
/*  89 */       if (arrayOfFcCompFont.length > 0 && (arrayOfFcCompFont[0]).firstFont.fontFile != null) {
/*     */         
/*  91 */         arrayOfString[0] = (arrayOfFcCompFont[0]).firstFont.familyName;
/*  92 */         arrayOfString[1] = (arrayOfFcCompFont[0]).firstFont.fontFile;
/*     */       } else {
/*  94 */         arrayOfString[0] = "Dialog";
/*  95 */         arrayOfString[1] = "/dialog.ttf";
/*     */       } 
/*     */     }
/*  98 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */   
/*     */   protected native String getFontPathNative(boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   protected synchronized String getFontPath(boolean paramBoolean) {
/* 105 */     return getFontPathNative(paramBoolean, false);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/FcFontManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */