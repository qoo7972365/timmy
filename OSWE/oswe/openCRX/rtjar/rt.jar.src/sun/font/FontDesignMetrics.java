/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.TextLayout;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import sun.java2d.Disposer;
/*     */ import sun.java2d.DisposerRecord;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FontDesignMetrics
/*     */   extends FontMetrics
/*     */ {
/*     */   static final long serialVersionUID = 4480069578560887773L;
/*     */   private static final float UNKNOWN_WIDTH = -1.0F;
/*     */   private static final int CURRENT_VERSION = 1;
/* 118 */   private static float roundingUpValue = 0.95F;
/*     */   
/*     */   private Font font;
/*     */   
/*     */   private float ascent;
/*     */   
/*     */   private float descent;
/*     */   
/*     */   private float leading;
/*     */   private float maxAdvance;
/*     */   private double[] matrix;
/*     */   private int[] cache;
/* 130 */   private int serVersion = 0;
/*     */   
/*     */   private boolean isAntiAliased;
/*     */   private boolean usesFractionalMetrics;
/*     */   private AffineTransform frcTx;
/*     */   private transient float[] advCache;
/* 136 */   private transient int height = -1;
/*     */   
/*     */   private transient FontRenderContext frc;
/*     */   
/* 140 */   private transient double[] devmatrix = null;
/*     */   
/*     */   private transient FontStrike fontStrike;
/*     */   
/* 144 */   private static FontRenderContext DEFAULT_FRC = null;
/*     */ 
/*     */   
/*     */   private static FontRenderContext getDefaultFrc() {
/* 148 */     if (DEFAULT_FRC == null) {
/*     */       AffineTransform affineTransform;
/* 150 */       if (GraphicsEnvironment.isHeadless()) {
/* 151 */         affineTransform = new AffineTransform();
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 157 */         affineTransform = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getDefaultTransform();
/*     */       } 
/* 159 */       DEFAULT_FRC = new FontRenderContext(affineTransform, false, false);
/*     */     } 
/* 161 */     return DEFAULT_FRC;
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
/*     */   private static class KeyReference
/*     */     extends SoftReference
/*     */     implements DisposerRecord, Disposer.PollDisposable
/*     */   {
/* 176 */     static ReferenceQueue queue = Disposer.getQueue();
/*     */     
/*     */     Object key;
/*     */     
/*     */     KeyReference(Object param1Object1, Object param1Object2) {
/* 181 */       super((T)param1Object2, queue);
/* 182 */       this.key = param1Object1;
/* 183 */       Disposer.addReference(this, this);
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
/*     */     public void dispose() {
/* 196 */       if (FontDesignMetrics.metricsCache.get(this.key) == this) {
/* 197 */         FontDesignMetrics.metricsCache.remove(this.key);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MetricsKey
/*     */   {
/*     */     Font font;
/*     */     FontRenderContext frc;
/*     */     int hash;
/*     */     
/*     */     MetricsKey() {}
/*     */     
/*     */     MetricsKey(Font param1Font, FontRenderContext param1FontRenderContext) {
/* 211 */       init(param1Font, param1FontRenderContext);
/*     */     }
/*     */     
/*     */     void init(Font param1Font, FontRenderContext param1FontRenderContext) {
/* 215 */       this.font = param1Font;
/* 216 */       this.frc = param1FontRenderContext;
/* 217 */       this.hash = param1Font.hashCode() + param1FontRenderContext.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 221 */       if (!(param1Object instanceof MetricsKey)) {
/* 222 */         return false;
/*     */       }
/* 224 */       return (this.font
/* 225 */         .equals(((MetricsKey)param1Object).font) && this.frc
/* 226 */         .equals(((MetricsKey)param1Object).frc));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 230 */       return this.hash;
/*     */     }
/*     */ 
/*     */     
/* 234 */     static final MetricsKey key = new MetricsKey();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 242 */   private static final ConcurrentHashMap<Object, KeyReference> metricsCache = new ConcurrentHashMap<>();
/*     */   
/*     */   private static final int MAXRECENT = 5;
/*     */   
/* 246 */   private static final FontDesignMetrics[] recentMetrics = new FontDesignMetrics[5];
/* 247 */   private static int recentIndex = 0;
/*     */   
/*     */   public static FontDesignMetrics getMetrics(Font paramFont) {
/* 250 */     return getMetrics(paramFont, getDefaultFrc());
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
/*     */   public static FontDesignMetrics getMetrics(Font paramFont, FontRenderContext paramFontRenderContext) {
/*     */     KeyReference keyReference;
/* 264 */     SunFontManager sunFontManager = SunFontManager.getInstance();
/* 265 */     if (sunFontManager.maybeUsingAlternateCompositeFonts() && 
/* 266 */       FontUtilities.getFont2D(paramFont) instanceof CompositeFont) {
/* 267 */       return new FontDesignMetrics(paramFont, paramFontRenderContext);
/*     */     }
/*     */     
/* 270 */     FontDesignMetrics fontDesignMetrics = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 278 */     boolean bool = paramFontRenderContext.equals(getDefaultFrc());
/*     */     
/* 280 */     if (bool) {
/* 281 */       keyReference = metricsCache.get(paramFont);
/*     */     }
/*     */     else {
/*     */       
/* 285 */       synchronized (MetricsKey.class) {
/* 286 */         MetricsKey.key.init(paramFont, paramFontRenderContext);
/* 287 */         keyReference = metricsCache.get(MetricsKey.key);
/*     */       } 
/*     */     } 
/*     */     
/* 291 */     if (keyReference != null) {
/* 292 */       fontDesignMetrics = (FontDesignMetrics)keyReference.get();
/*     */     }
/*     */     
/* 295 */     if (fontDesignMetrics == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 302 */       fontDesignMetrics = new FontDesignMetrics(paramFont, paramFontRenderContext);
/* 303 */       if (bool) {
/* 304 */         metricsCache.put(paramFont, new KeyReference(paramFont, fontDesignMetrics));
/*     */       } else {
/* 306 */         MetricsKey metricsKey = new MetricsKey(paramFont, paramFontRenderContext);
/* 307 */         metricsCache.put(metricsKey, new KeyReference(metricsKey, fontDesignMetrics));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 312 */     for (byte b = 0; b < recentMetrics.length; b++) {
/* 313 */       if (recentMetrics[b] == fontDesignMetrics) {
/* 314 */         return fontDesignMetrics;
/*     */       }
/*     */     } 
/*     */     
/* 318 */     synchronized (recentMetrics) {
/* 319 */       recentMetrics[recentIndex++] = fontDesignMetrics;
/* 320 */       if (recentIndex == 5) {
/* 321 */         recentIndex = 0;
/*     */       }
/*     */     } 
/* 324 */     return fontDesignMetrics;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FontDesignMetrics(Font paramFont) {
/* 335 */     this(paramFont, getDefaultFrc());
/*     */   }
/*     */ 
/*     */   
/*     */   private FontDesignMetrics(Font paramFont, FontRenderContext paramFontRenderContext) {
/* 340 */     super(paramFont);
/* 341 */     this.font = paramFont;
/* 342 */     this.frc = paramFontRenderContext;
/*     */     
/* 344 */     this.isAntiAliased = paramFontRenderContext.isAntiAliased();
/* 345 */     this.usesFractionalMetrics = paramFontRenderContext.usesFractionalMetrics();
/*     */     
/* 347 */     this.frcTx = paramFontRenderContext.getTransform();
/*     */     
/* 349 */     this.matrix = new double[4];
/* 350 */     initMatrixAndMetrics();
/*     */     
/* 352 */     initAdvCache();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initMatrixAndMetrics() {
/* 357 */     Font2D font2D = FontUtilities.getFont2D(this.font);
/* 358 */     this.fontStrike = font2D.getStrike(this.font, this.frc);
/* 359 */     StrikeMetrics strikeMetrics = this.fontStrike.getFontMetrics();
/* 360 */     this.ascent = strikeMetrics.getAscent();
/* 361 */     this.descent = strikeMetrics.getDescent();
/* 362 */     this.leading = strikeMetrics.getLeading();
/* 363 */     this.maxAdvance = strikeMetrics.getMaxAdvance();
/*     */     
/* 365 */     this.devmatrix = new double[4];
/* 366 */     this.frcTx.getMatrix(this.devmatrix);
/*     */   }
/*     */   
/*     */   private void initAdvCache() {
/* 370 */     this.advCache = new float[256];
/*     */     
/* 372 */     for (byte b = 0; b < 'Ā'; b++) {
/* 373 */       this.advCache[b] = -1.0F;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 380 */     paramObjectInputStream.defaultReadObject();
/* 381 */     if (this.serVersion != 1) {
/* 382 */       this.frc = getDefaultFrc();
/* 383 */       this.isAntiAliased = this.frc.isAntiAliased();
/* 384 */       this.usesFractionalMetrics = this.frc.usesFractionalMetrics();
/* 385 */       this.frcTx = this.frc.getTransform();
/*     */     } else {
/*     */       
/* 388 */       this.frc = new FontRenderContext(this.frcTx, this.isAntiAliased, this.usesFractionalMetrics);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 394 */     this.height = -1;
/*     */     
/* 396 */     this.cache = null;
/*     */     
/* 398 */     initMatrixAndMetrics();
/* 399 */     initAdvCache();
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 404 */     this.cache = new int[256];
/* 405 */     for (byte b = 0; b < 'Ā'; b++) {
/* 406 */       this.cache[b] = -1;
/*     */     }
/* 408 */     this.serVersion = 1;
/*     */     
/* 410 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 412 */     this.cache = null;
/*     */   }
/*     */   
/*     */   private float handleCharWidth(int paramInt) {
/* 416 */     return this.fontStrike.getCodePointAdvance(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getLatinCharWidth(char paramChar) {
/* 423 */     float f = this.advCache[paramChar];
/* 424 */     if (f == -1.0F) {
/* 425 */       f = handleCharWidth(paramChar);
/* 426 */       this.advCache[paramChar] = f;
/*     */     } 
/* 428 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FontRenderContext getFontRenderContext() {
/* 434 */     return this.frc;
/*     */   }
/*     */ 
/*     */   
/*     */   public int charWidth(char paramChar) {
/*     */     float f;
/* 440 */     if (paramChar < 'Ā') {
/* 441 */       f = getLatinCharWidth(paramChar);
/*     */     } else {
/*     */       
/* 444 */       f = handleCharWidth(paramChar);
/*     */     } 
/* 446 */     return (int)(0.5D + f);
/*     */   }
/*     */   
/*     */   public int charWidth(int paramInt) {
/* 450 */     if (!Character.isValidCodePoint(paramInt)) {
/* 451 */       paramInt = 65535;
/*     */     }
/*     */     
/* 454 */     float f = handleCharWidth(paramInt);
/*     */     
/* 456 */     return (int)(0.5D + f);
/*     */   }
/*     */ 
/*     */   
/*     */   public int stringWidth(String paramString) {
/* 461 */     float f = 0.0F;
/* 462 */     if (this.font.hasLayoutAttributes()) {
/*     */       
/* 464 */       if (paramString == null) {
/* 465 */         throw new NullPointerException("str is null");
/*     */       }
/* 467 */       if (paramString.length() == 0) {
/* 468 */         return 0;
/*     */       }
/* 470 */       f = (new TextLayout(paramString, this.font, this.frc)).getAdvance();
/*     */     } else {
/* 472 */       int i = paramString.length();
/* 473 */       for (byte b = 0; b < i; b++) {
/* 474 */         char c = paramString.charAt(b);
/* 475 */         if (c < 'Ā')
/* 476 */         { f += getLatinCharWidth(c); }
/* 477 */         else { if (FontUtilities.isNonSimpleChar(c)) {
/* 478 */             f = (new TextLayout(paramString, this.font, this.frc)).getAdvance();
/*     */             break;
/*     */           } 
/* 481 */           f += handleCharWidth(c); }
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 486 */     return (int)(0.5D + f);
/*     */   }
/*     */ 
/*     */   
/*     */   public int charsWidth(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 491 */     float f = 0.0F;
/* 492 */     if (this.font.hasLayoutAttributes()) {
/* 493 */       if (paramInt2 == 0) {
/* 494 */         return 0;
/*     */       }
/* 496 */       String str = new String(paramArrayOfchar, paramInt1, paramInt2);
/* 497 */       f = (new TextLayout(str, this.font, this.frc)).getAdvance();
/*     */     } else {
/*     */       
/* 500 */       if (paramInt2 < 0) {
/* 501 */         throw new IndexOutOfBoundsException("len=" + paramInt2);
/*     */       }
/* 503 */       int i = paramInt1 + paramInt2;
/* 504 */       for (int j = paramInt1; j < i; j++) {
/* 505 */         char c = paramArrayOfchar[j];
/* 506 */         if (c < 'Ā')
/* 507 */         { f += getLatinCharWidth(c); }
/* 508 */         else { if (FontUtilities.isNonSimpleChar(c)) {
/* 509 */             String str = new String(paramArrayOfchar, paramInt1, paramInt2);
/* 510 */             f = (new TextLayout(str, this.font, this.frc)).getAdvance();
/*     */             break;
/*     */           } 
/* 513 */           f += handleCharWidth(c); }
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 518 */     return (int)(0.5D + f);
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
/*     */   public int[] getWidths() {
/* 534 */     int[] arrayOfInt = new int[256];
/* 535 */     for (char c = Character.MIN_VALUE; c < 'Ā'; c = (char)(c + 1)) {
/* 536 */       float f = this.advCache[c];
/* 537 */       if (f == -1.0F) {
/* 538 */         f = this.advCache[c] = handleCharWidth(c);
/*     */       }
/* 540 */       arrayOfInt[c] = (int)(0.5D + f);
/*     */     } 
/* 542 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   public int getMaxAdvance() {
/* 546 */     return (int)(0.99F + this.maxAdvance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAscent() {
/* 555 */     return (int)(roundingUpValue + this.ascent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDescent() {
/* 563 */     return (int)(roundingUpValue + this.descent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLeading() {
/* 574 */     return (int)(roundingUpValue + this.descent + this.leading) - (int)(roundingUpValue + this.descent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 585 */     if (this.height < 0) {
/* 586 */       this.height = getAscent() + (int)(roundingUpValue + this.descent + this.leading);
/*     */     }
/* 588 */     return this.height;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/FontDesignMetrics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */