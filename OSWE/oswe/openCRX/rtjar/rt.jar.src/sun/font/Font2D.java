/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Font2D
/*     */ {
/*     */   public static final int FONT_CONFIG_RANK = 2;
/*     */   public static final int JRE_RANK = 2;
/*     */   public static final int TTF_RANK = 3;
/*     */   public static final int TYPE1_RANK = 4;
/*     */   public static final int NATIVE_RANK = 5;
/*     */   public static final int UNKNOWN_RANK = 6;
/*     */   public static final int DEFAULT_RANK = 4;
/*  57 */   private static final String[] boldNames = new String[] { "bold", "demibold", "demi-bold", "demi bold", "negreta", "demi" };
/*     */ 
/*     */   
/*  60 */   private static final String[] italicNames = new String[] { "italic", "cursiva", "oblique", "inclined" };
/*     */ 
/*     */   
/*  63 */   private static final String[] boldItalicNames = new String[] { "bolditalic", "bold-italic", "bold italic", "boldoblique", "bold-oblique", "bold oblique", "demibold italic", "negreta cursiva", "demi oblique" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final FontRenderContext DEFAULT_FRC = new FontRenderContext(null, false, false);
/*     */   
/*     */   public Font2DHandle handle;
/*     */   
/*     */   protected String familyName;
/*     */   protected String fullName;
/*  74 */   protected int style = 0;
/*     */   protected FontFamily family;
/*  76 */   protected int fontRank = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CharToGlyphMapper mapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   protected ConcurrentHashMap<FontStrikeDesc, Reference> strikeCache = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   protected Reference lastFontStrike = new SoftReference(null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FWIDTH_NORMAL = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FWEIGHT_NORMAL = 400;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FWEIGHT_BOLD = 700;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStyle() {
/* 132 */     return this.style;
/*     */   }
/*     */   
/*     */   protected void setStyle() {
/* 136 */     String str = this.fullName.toLowerCase();
/*     */     byte b;
/* 138 */     for (b = 0; b < boldItalicNames.length; b++) {
/* 139 */       if (str.indexOf(boldItalicNames[b]) != -1) {
/* 140 */         this.style = 3;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 145 */     for (b = 0; b < italicNames.length; b++) {
/* 146 */       if (str.indexOf(italicNames[b]) != -1) {
/* 147 */         this.style = 2;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 152 */     for (b = 0; b < boldNames.length; b++) {
/* 153 */       if (str.indexOf(boldNames[b]) != -1) {
/* 154 */         this.style = 1;
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 165 */     return 5;
/*     */   }
/*     */   
/*     */   public int getWeight() {
/* 169 */     if ((this.style & 0x1) != 0) {
/* 170 */       return 700;
/*     */     }
/* 172 */     return 400;
/*     */   }
/*     */ 
/*     */   
/*     */   int getRank() {
/* 177 */     return this.fontRank;
/*     */   }
/*     */   
/*     */   void setRank(int paramInt) {
/* 181 */     this.fontRank = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract CharToGlyphMapper getMapper();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getValidatedGlyphCode(int paramInt) {
/* 194 */     if (paramInt < 0 || paramInt >= getMapper().getNumGlyphs()) {
/* 195 */       paramInt = getMapper().getMissingGlyphCode();
/*     */     }
/* 197 */     return paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract FontStrike createStrike(FontStrikeDesc paramFontStrikeDesc);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontStrike getStrike(Font paramFont) {
/* 213 */     FontStrike fontStrike = this.lastFontStrike.get();
/* 214 */     if (fontStrike != null) {
/* 215 */       return fontStrike;
/*     */     }
/* 217 */     return getStrike(paramFont, DEFAULT_FRC);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontStrike getStrike(Font paramFont, AffineTransform paramAffineTransform, int paramInt1, int paramInt2) {
/* 253 */     double d = paramFont.getSize2D();
/* 254 */     AffineTransform affineTransform = (AffineTransform)paramAffineTransform.clone();
/* 255 */     affineTransform.scale(d, d);
/* 256 */     if (paramFont.isTransformed()) {
/* 257 */       affineTransform.concatenate(paramFont.getTransform());
/*     */     }
/* 259 */     if (affineTransform.getTranslateX() != 0.0D || affineTransform.getTranslateY() != 0.0D) {
/* 260 */       affineTransform.setTransform(affineTransform.getScaleX(), affineTransform
/* 261 */           .getShearY(), affineTransform
/* 262 */           .getShearX(), affineTransform
/* 263 */           .getScaleY(), 0.0D, 0.0D);
/*     */     }
/*     */ 
/*     */     
/* 267 */     FontStrikeDesc fontStrikeDesc = new FontStrikeDesc(paramAffineTransform, affineTransform, paramFont.getStyle(), paramInt1, paramInt2);
/* 268 */     return getStrike(fontStrikeDesc, false);
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
/*     */   public FontStrike getStrike(Font paramFont, AffineTransform paramAffineTransform1, AffineTransform paramAffineTransform2, int paramInt1, int paramInt2) {
/* 280 */     FontStrikeDesc fontStrikeDesc = new FontStrikeDesc(paramAffineTransform1, paramAffineTransform2, paramFont.getStyle(), paramInt1, paramInt2);
/* 281 */     return getStrike(fontStrikeDesc, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public FontStrike getStrike(Font paramFont, FontRenderContext paramFontRenderContext) {
/* 286 */     AffineTransform affineTransform = paramFontRenderContext.getTransform();
/* 287 */     double d = paramFont.getSize2D();
/* 288 */     affineTransform.scale(d, d);
/* 289 */     if (paramFont.isTransformed()) {
/* 290 */       affineTransform.concatenate(paramFont.getTransform());
/* 291 */       if (affineTransform.getTranslateX() != 0.0D || affineTransform.getTranslateY() != 0.0D) {
/* 292 */         affineTransform.setTransform(affineTransform.getScaleX(), affineTransform
/* 293 */             .getShearY(), affineTransform
/* 294 */             .getShearX(), affineTransform
/* 295 */             .getScaleY(), 0.0D, 0.0D);
/*     */       }
/*     */     } 
/*     */     
/* 299 */     int i = FontStrikeDesc.getAAHintIntVal(this, paramFont, paramFontRenderContext);
/* 300 */     int j = FontStrikeDesc.getFMHintIntVal(paramFontRenderContext.getFractionalMetricsHint());
/*     */     
/* 302 */     FontStrikeDesc fontStrikeDesc = new FontStrikeDesc(paramFontRenderContext.getTransform(), affineTransform, paramFont.getStyle(), i, j);
/*     */     
/* 304 */     return getStrike(fontStrikeDesc, false);
/*     */   }
/*     */   
/*     */   FontStrike getStrike(FontStrikeDesc paramFontStrikeDesc) {
/* 308 */     return getStrike(paramFontStrikeDesc, true);
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
/*     */   private FontStrike getStrike(FontStrikeDesc paramFontStrikeDesc, boolean paramBoolean) {
/* 325 */     FontStrike fontStrike = this.lastFontStrike.get();
/* 326 */     if (fontStrike != null && paramFontStrikeDesc.equals(fontStrike.desc))
/*     */     {
/* 328 */       return fontStrike;
/*     */     }
/* 330 */     Reference<FontStrike> reference = this.strikeCache.get(paramFontStrikeDesc);
/* 331 */     if (reference != null) {
/* 332 */       fontStrike = reference.get();
/* 333 */       if (fontStrike != null) {
/*     */         
/* 335 */         this.lastFontStrike = new SoftReference<>(fontStrike);
/* 336 */         StrikeCache.refStrike(fontStrike);
/* 337 */         return fontStrike;
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 356 */     if (paramBoolean) {
/* 357 */       paramFontStrikeDesc = new FontStrikeDesc(paramFontStrikeDesc);
/*     */     }
/* 359 */     fontStrike = createStrike(paramFontStrikeDesc);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 368 */     int i = paramFontStrikeDesc.glyphTx.getType();
/* 369 */     if (i == 32 || ((i & 0x10) != 0 && this.strikeCache
/*     */       
/* 371 */       .size() > 10)) {
/* 372 */       reference = StrikeCache.getStrikeRef(fontStrike, true);
/*     */     } else {
/* 374 */       reference = StrikeCache.getStrikeRef(fontStrike);
/*     */     } 
/* 376 */     this.strikeCache.put(paramFontStrikeDesc, reference);
/*     */     
/* 378 */     this.lastFontStrike = new SoftReference<>(fontStrike);
/* 379 */     StrikeCache.refStrike(fontStrike);
/* 380 */     return fontStrike;
/*     */   }
/*     */ 
/*     */   
/*     */   void removeFromCache(FontStrikeDesc paramFontStrikeDesc) {
/* 385 */     Reference<Object> reference = this.strikeCache.get(paramFontStrikeDesc);
/* 386 */     if (reference != null) {
/* 387 */       Object object = reference.get();
/* 388 */       if (object == null) {
/* 389 */         this.strikeCache.remove(paramFontStrikeDesc);
/*     */       }
/*     */     } 
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
/*     */   public void getFontMetrics(Font paramFont, AffineTransform paramAffineTransform, Object paramObject1, Object paramObject2, float[] paramArrayOffloat) {
/* 412 */     int i = FontStrikeDesc.getAAHintIntVal(paramObject1, this, paramFont.getSize());
/* 413 */     int j = FontStrikeDesc.getFMHintIntVal(paramObject2);
/* 414 */     FontStrike fontStrike = getStrike(paramFont, paramAffineTransform, i, j);
/* 415 */     StrikeMetrics strikeMetrics = fontStrike.getFontMetrics();
/* 416 */     paramArrayOffloat[0] = strikeMetrics.getAscent();
/* 417 */     paramArrayOffloat[1] = strikeMetrics.getDescent();
/* 418 */     paramArrayOffloat[2] = strikeMetrics.getLeading();
/* 419 */     paramArrayOffloat[3] = strikeMetrics.getMaxAdvance();
/*     */     
/* 421 */     getStyleMetrics(paramFont.getSize2D(), paramArrayOffloat, 4);
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
/*     */   public void getStyleMetrics(float paramFloat, float[] paramArrayOffloat, int paramInt) {
/* 437 */     paramArrayOffloat[paramInt] = -paramArrayOffloat[0] / 2.5F;
/* 438 */     paramArrayOffloat[paramInt + 1] = paramFloat / 12.0F;
/* 439 */     paramArrayOffloat[paramInt + 2] = paramArrayOffloat[paramInt + 1] / 1.5F;
/* 440 */     paramArrayOffloat[paramInt + 3] = paramArrayOffloat[paramInt + 1];
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
/*     */   public void getFontMetrics(Font paramFont, FontRenderContext paramFontRenderContext, float[] paramArrayOffloat) {
/* 453 */     StrikeMetrics strikeMetrics = getStrike(paramFont, paramFontRenderContext).getFontMetrics();
/* 454 */     paramArrayOffloat[0] = strikeMetrics.getAscent();
/* 455 */     paramArrayOffloat[1] = strikeMetrics.getDescent();
/* 456 */     paramArrayOffloat[2] = strikeMetrics.getLeading();
/* 457 */     paramArrayOffloat[3] = strikeMetrics.getMaxAdvance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] getTableBytes(int paramInt) {
/* 465 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long getLayoutTableCache() {
/* 472 */     return 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   protected long getUnitsPerEm() {
/* 477 */     return 2048L;
/*     */   }
/*     */   
/*     */   boolean supportsEncoding(String paramString) {
/* 481 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canDoStyle(int paramInt) {
/* 485 */     return (paramInt == this.style);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useAAForPtSize(int paramInt) {
/* 493 */     return true;
/*     */   }
/*     */   
/*     */   public boolean hasSupplementaryChars() {
/* 497 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPostscriptName() {
/* 502 */     return this.fullName;
/*     */   }
/*     */   
/*     */   public String getFontName(Locale paramLocale) {
/* 506 */     return this.fullName;
/*     */   }
/*     */   
/*     */   public String getFamilyName(Locale paramLocale) {
/* 510 */     return this.familyName;
/*     */   }
/*     */   
/*     */   public int getNumGlyphs() {
/* 514 */     return getMapper().getNumGlyphs();
/*     */   }
/*     */   
/*     */   public int charToGlyph(int paramInt) {
/* 518 */     return getMapper().charToGlyph(paramInt);
/*     */   }
/*     */   
/*     */   public int getMissingGlyphCode() {
/* 522 */     return getMapper().getMissingGlyphCode();
/*     */   }
/*     */   
/*     */   public boolean canDisplay(char paramChar) {
/* 526 */     return getMapper().canDisplay(paramChar);
/*     */   }
/*     */   
/*     */   public boolean canDisplay(int paramInt) {
/* 530 */     return getMapper().canDisplay(paramInt);
/*     */   }
/*     */   
/*     */   public byte getBaselineFor(char paramChar) {
/* 534 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getItalicAngle(Font paramFont, AffineTransform paramAffineTransform, Object paramObject1, Object paramObject2) {
/* 542 */     int i = FontStrikeDesc.getAAHintIntVal(paramObject1, this, 12);
/* 543 */     int j = FontStrikeDesc.getFMHintIntVal(paramObject2);
/* 544 */     FontStrike fontStrike = getStrike(paramFont, paramAffineTransform, i, j);
/* 545 */     StrikeMetrics strikeMetrics = fontStrike.getFontMetrics();
/* 546 */     if (strikeMetrics.ascentY == 0.0F || strikeMetrics.ascentX == 0.0F) {
/* 547 */       return 0.0F;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 552 */     return strikeMetrics.ascentX / -strikeMetrics.ascentY;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/Font2D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */