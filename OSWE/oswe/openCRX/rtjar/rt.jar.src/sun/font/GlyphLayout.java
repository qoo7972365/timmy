/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.NoninvertibleTransformException;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GlyphLayout
/*     */ {
/*     */   private GVData _gvdata;
/*     */   private static volatile GlyphLayout cache;
/*     */   private LayoutEngineFactory _lef;
/*     */   private TextRecord _textRecord;
/*     */   private ScriptRun _scriptRuns;
/*     */   private FontRunIterator _fontRuns;
/*     */   private int _ercount;
/*     */   private ArrayList _erecords;
/*     */   private Point2D.Float _pt;
/*     */   private FontStrikeDesc _sd;
/*     */   private float[] _mat;
/*     */   private int _typo_flags;
/*     */   private int _offset;
/*     */   
/*     */   public static final class LayoutEngineKey
/*     */   {
/*     */     private Font2D font;
/*     */     private int script;
/*     */     private int lang;
/*     */     
/*     */     LayoutEngineKey() {}
/*     */     
/*     */     LayoutEngineKey(Font2D param1Font2D, int param1Int1, int param1Int2) {
/* 111 */       init(param1Font2D, param1Int1, param1Int2);
/*     */     }
/*     */     
/*     */     void init(Font2D param1Font2D, int param1Int1, int param1Int2) {
/* 115 */       this.font = param1Font2D;
/* 116 */       this.script = param1Int1;
/* 117 */       this.lang = param1Int2;
/*     */     }
/*     */     
/*     */     LayoutEngineKey copy() {
/* 121 */       return new LayoutEngineKey(this.font, this.script, this.lang);
/*     */     }
/*     */     
/*     */     Font2D font() {
/* 125 */       return this.font;
/*     */     }
/*     */     
/*     */     int script() {
/* 129 */       return this.script;
/*     */     }
/*     */     
/*     */     int lang() {
/* 133 */       return this.lang;
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 137 */       if (this == param1Object) return true; 
/* 138 */       if (param1Object == null) return false; 
/*     */       try {
/* 140 */         LayoutEngineKey layoutEngineKey = (LayoutEngineKey)param1Object;
/* 141 */         return (this.script == layoutEngineKey.script && this.lang == layoutEngineKey.lang && this.font
/*     */           
/* 143 */           .equals(layoutEngineKey.font));
/*     */       }
/* 145 */       catch (ClassCastException classCastException) {
/* 146 */         return false;
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 151 */       return this.script ^ this.lang ^ this.font.hashCode();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GlyphLayout get(LayoutEngineFactory paramLayoutEngineFactory) {
/* 184 */     if (paramLayoutEngineFactory == null) {
/* 185 */       paramLayoutEngineFactory = SunLayoutEngine.instance();
/*     */     }
/* 187 */     GlyphLayout glyphLayout = null;
/* 188 */     synchronized (GlyphLayout.class) {
/* 189 */       if (cache != null) {
/* 190 */         glyphLayout = cache;
/* 191 */         cache = null;
/*     */       } 
/*     */     } 
/* 194 */     if (glyphLayout == null) {
/* 195 */       glyphLayout = new GlyphLayout();
/*     */     }
/* 197 */     glyphLayout._lef = paramLayoutEngineFactory;
/* 198 */     return glyphLayout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void done(GlyphLayout paramGlyphLayout) {
/* 206 */     paramGlyphLayout._lef = null;
/* 207 */     cache = paramGlyphLayout;
/*     */   }
/*     */   public static interface LayoutEngineFactory {
/*     */     GlyphLayout.LayoutEngine getEngine(Font2D param1Font2D, int param1Int1, int param1Int2);
/*     */     GlyphLayout.LayoutEngine getEngine(GlyphLayout.LayoutEngineKey param1LayoutEngineKey); }
/*     */   
/*     */   public static interface LayoutEngine {
/*     */     void layout(FontStrikeDesc param1FontStrikeDesc, float[] param1ArrayOffloat, int param1Int1, int param1Int2, TextRecord param1TextRecord, int param1Int3, Point2D.Float param1Float, GlyphLayout.GVData param1GVData); }
/*     */   
/*     */   private static final class SDCache { public Font key_font;
/*     */     public FontRenderContext key_frc;
/*     */     public AffineTransform dtx;
/*     */     
/*     */     private SDCache(Font param1Font, FontRenderContext param1FontRenderContext) {
/* 221 */       this.key_font = param1Font;
/* 222 */       this.key_frc = param1FontRenderContext;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 227 */       this.dtx = param1FontRenderContext.getTransform();
/* 228 */       this.dtx.setTransform(this.dtx.getScaleX(), this.dtx.getShearY(), this.dtx
/* 229 */           .getShearX(), this.dtx.getScaleY(), 0.0D, 0.0D);
/*     */       
/* 231 */       if (!this.dtx.isIdentity()) {
/*     */         try {
/* 233 */           this.invdtx = this.dtx.createInverse();
/*     */         }
/* 235 */         catch (NoninvertibleTransformException noninvertibleTransformException) {
/* 236 */           throw new InternalError(noninvertibleTransformException);
/*     */         } 
/*     */       }
/*     */       
/* 240 */       float f = param1Font.getSize2D();
/* 241 */       if (param1Font.isTransformed()) {
/* 242 */         this.gtx = param1Font.getTransform();
/* 243 */         this.gtx.scale(f, f);
/* 244 */         this
/* 245 */           .delta = new Point2D.Float((float)this.gtx.getTranslateX(), (float)this.gtx.getTranslateY());
/* 246 */         this.gtx.setTransform(this.gtx.getScaleX(), this.gtx.getShearY(), this.gtx
/* 247 */             .getShearX(), this.gtx.getScaleY(), 0.0D, 0.0D);
/*     */         
/* 249 */         this.gtx.preConcatenate(this.dtx);
/*     */       } else {
/* 251 */         this.delta = ZERO_DELTA;
/* 252 */         this.gtx = new AffineTransform(this.dtx);
/* 253 */         this.gtx.scale(f, f);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 261 */       int i = FontStrikeDesc.getAAHintIntVal(param1FontRenderContext.getAntiAliasingHint(), 
/* 262 */           FontUtilities.getFont2D(param1Font), 
/* 263 */           (int)Math.abs(f));
/*     */       
/* 265 */       int j = FontStrikeDesc.getFMHintIntVal(param1FontRenderContext.getFractionalMetricsHint());
/* 266 */       this.sd = new FontStrikeDesc(this.dtx, this.gtx, param1Font.getStyle(), i, j);
/*     */     }
/*     */     public AffineTransform invdtx; public AffineTransform gtx; public Point2D.Float delta; public FontStrikeDesc sd;
/* 269 */     private static final Point2D.Float ZERO_DELTA = new Point2D.Float();
/*     */     
/*     */     private static SoftReference<ConcurrentHashMap<SDKey, SDCache>> cacheRef;
/*     */     
/*     */     private static final class SDKey
/*     */     {
/*     */       private final Font font;
/*     */       private final FontRenderContext frc;
/*     */       private final int hash;
/*     */       
/*     */       SDKey(Font param2Font, FontRenderContext param2FontRenderContext) {
/* 280 */         this.font = param2Font;
/* 281 */         this.frc = param2FontRenderContext;
/* 282 */         this.hash = param2Font.hashCode() ^ param2FontRenderContext.hashCode();
/*     */       }
/*     */       
/*     */       public int hashCode() {
/* 286 */         return this.hash;
/*     */       }
/*     */       
/*     */       public boolean equals(Object param2Object) {
/*     */         try {
/* 291 */           SDKey sDKey = (SDKey)param2Object;
/* 292 */           return (this.hash == sDKey.hash && this.font
/*     */             
/* 294 */             .equals(sDKey.font) && this.frc
/* 295 */             .equals(sDKey.frc));
/*     */         }
/* 297 */         catch (ClassCastException classCastException) {
/*     */           
/* 299 */           return false;
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static SDCache get(Font param1Font, FontRenderContext param1FontRenderContext) {
/* 310 */       if (param1FontRenderContext.isTransformed()) {
/* 311 */         AffineTransform affineTransform = param1FontRenderContext.getTransform();
/* 312 */         if (affineTransform.getTranslateX() != 0.0D || affineTransform
/* 313 */           .getTranslateY() != 0.0D) {
/*     */ 
/*     */ 
/*     */           
/* 317 */           affineTransform = new AffineTransform(affineTransform.getScaleX(), affineTransform.getShearY(), affineTransform.getShearX(), affineTransform.getScaleY(), 0.0D, 0.0D);
/*     */ 
/*     */ 
/*     */           
/* 321 */           param1FontRenderContext = new FontRenderContext(affineTransform, param1FontRenderContext.getAntiAliasingHint(), param1FontRenderContext.getFractionalMetricsHint());
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 326 */       SDKey sDKey = new SDKey(param1Font, param1FontRenderContext);
/* 327 */       ConcurrentHashMap<Object, Object> concurrentHashMap = null;
/* 328 */       SDCache sDCache = null;
/* 329 */       if (cacheRef != null) {
/* 330 */         concurrentHashMap = (ConcurrentHashMap)cacheRef.get();
/* 331 */         if (concurrentHashMap != null) {
/* 332 */           sDCache = (SDCache)concurrentHashMap.get(sDKey);
/*     */         }
/*     */       } 
/* 335 */       if (sDCache == null) {
/* 336 */         sDCache = new SDCache(param1Font, param1FontRenderContext);
/* 337 */         if (concurrentHashMap == null) {
/* 338 */           concurrentHashMap = new ConcurrentHashMap<>(10);
/* 339 */           cacheRef = new SoftReference(concurrentHashMap);
/*     */         }
/* 341 */         else if (concurrentHashMap.size() >= 512) {
/* 342 */           concurrentHashMap.clear();
/*     */         } 
/* 344 */         concurrentHashMap.put(sDKey, sDCache);
/*     */       } 
/* 346 */       return sDCache;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StandardGlyphVector layout(Font paramFont, FontRenderContext paramFontRenderContext, char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, StandardGlyphVector paramStandardGlyphVector) {
/*     */     StandardGlyphVector standardGlyphVector;
/* 365 */     if (paramArrayOfchar == null || paramInt1 < 0 || paramInt2 < 0 || paramInt2 > paramArrayOfchar.length - paramInt1) {
/* 366 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 369 */     init(paramInt2);
/*     */ 
/*     */ 
/*     */     
/* 373 */     if (paramFont.hasLayoutAttributes()) {
/* 374 */       AttributeValues attributeValues = ((AttributeMap)paramFont.getAttributes()).getValues();
/* 375 */       if (attributeValues.getKerning() != 0) this._typo_flags |= 0x1; 
/* 376 */       if (attributeValues.getLigatures() != 0) this._typo_flags |= 0x2;
/*     */     
/*     */     } 
/* 379 */     this._offset = paramInt1;
/*     */ 
/*     */ 
/*     */     
/* 383 */     SDCache sDCache = SDCache.get(paramFont, paramFontRenderContext);
/* 384 */     this._mat[0] = (float)sDCache.gtx.getScaleX();
/* 385 */     this._mat[1] = (float)sDCache.gtx.getShearY();
/* 386 */     this._mat[2] = (float)sDCache.gtx.getShearX();
/* 387 */     this._mat[3] = (float)sDCache.gtx.getScaleY();
/* 388 */     this._pt.setLocation(sDCache.delta);
/*     */     
/* 390 */     int i = paramInt1 + paramInt2;
/*     */     
/* 392 */     int j = 0;
/* 393 */     int k = paramArrayOfchar.length;
/* 394 */     if (paramInt3 != 0) {
/* 395 */       if ((paramInt3 & 0x1) != 0) {
/* 396 */         this._typo_flags |= Integer.MIN_VALUE;
/*     */       }
/*     */       
/* 399 */       if ((paramInt3 & 0x2) != 0) {
/* 400 */         j = paramInt1;
/*     */       }
/*     */       
/* 403 */       if ((paramInt3 & 0x4) != 0) {
/* 404 */         k = i;
/*     */       }
/*     */     } 
/*     */     
/* 408 */     byte b1 = -1;
/*     */     
/* 410 */     Font2D font2D = FontUtilities.getFont2D(paramFont);
/* 411 */     if (font2D instanceof FontSubstitution) {
/* 412 */       font2D = ((FontSubstitution)font2D).getCompositeFont2D();
/*     */     }
/*     */     
/* 415 */     this._textRecord.init(paramArrayOfchar, paramInt1, i, j, k);
/* 416 */     int m = paramInt1;
/* 417 */     if (font2D instanceof CompositeFont) {
/* 418 */       this._scriptRuns.init(paramArrayOfchar, paramInt1, paramInt2);
/* 419 */       this._fontRuns.init((CompositeFont)font2D, paramArrayOfchar, paramInt1, i);
/* 420 */       while (this._scriptRuns.next()) {
/* 421 */         int i2 = this._scriptRuns.getScriptLimit();
/* 422 */         int i3 = this._scriptRuns.getScriptCode();
/* 423 */         while (this._fontRuns.next(i3, i2)) {
/* 424 */           PhysicalFont physicalFont = this._fontRuns.getFont();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 431 */           if (physicalFont instanceof NativeFont) {
/* 432 */             physicalFont = ((NativeFont)physicalFont).getDelegateFont();
/*     */           }
/* 434 */           int i4 = this._fontRuns.getGlyphMask();
/* 435 */           int i5 = this._fontRuns.getPos();
/* 436 */           nextEngineRecord(m, i5, i3, b1, physicalFont, i4);
/* 437 */           m = i5;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 441 */       this._scriptRuns.init(paramArrayOfchar, paramInt1, paramInt2);
/* 442 */       while (this._scriptRuns.next()) {
/* 443 */         int i2 = this._scriptRuns.getScriptLimit();
/* 444 */         int i3 = this._scriptRuns.getScriptCode();
/* 445 */         nextEngineRecord(m, i2, i3, b1, font2D, 0);
/* 446 */         m = i2;
/*     */       } 
/*     */     } 
/*     */     
/* 450 */     int n = 0;
/* 451 */     int i1 = this._ercount;
/* 452 */     byte b2 = 1;
/*     */     
/* 454 */     if (this._typo_flags < 0) {
/* 455 */       n = i1 - 1;
/* 456 */       i1 = -1;
/* 457 */       b2 = -1;
/*     */     } 
/*     */ 
/*     */     
/* 461 */     this._sd = sDCache.sd;
/* 462 */     for (; n != i1; n += b2) {
/* 463 */       EngineRecord engineRecord = this._erecords.get(n);
/*     */       while (true) {
/*     */         try {
/* 466 */           engineRecord.layout();
/*     */           
/*     */           break;
/* 469 */         } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 470 */           if (this._gvdata._count >= 0) {
/* 471 */             this._gvdata.grow();
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 476 */       if (this._gvdata._count < 0) {
/*     */         break;
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
/* 489 */     if (this._gvdata._count < 0) {
/* 490 */       standardGlyphVector = new StandardGlyphVector(paramFont, paramArrayOfchar, paramInt1, paramInt2, paramFontRenderContext);
/* 491 */       if (FontUtilities.debugFonts()) {
/* 492 */         FontUtilities.getLogger().warning("OpenType layout failed on font: " + paramFont);
/*     */       }
/*     */     } else {
/*     */       
/* 496 */       standardGlyphVector = this._gvdata.createGlyphVector(paramFont, paramFontRenderContext, paramStandardGlyphVector);
/*     */     } 
/*     */     
/* 499 */     return standardGlyphVector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GlyphLayout() {
/* 507 */     this._gvdata = new GVData();
/* 508 */     this._textRecord = new TextRecord();
/* 509 */     this._scriptRuns = new ScriptRun();
/* 510 */     this._fontRuns = new FontRunIterator();
/* 511 */     this._erecords = new ArrayList(10);
/* 512 */     this._pt = new Point2D.Float();
/* 513 */     this._sd = new FontStrikeDesc();
/* 514 */     this._mat = new float[4];
/*     */   }
/*     */   
/*     */   private void init(int paramInt) {
/* 518 */     this._typo_flags = 0;
/* 519 */     this._ercount = 0;
/* 520 */     this._gvdata.init(paramInt);
/*     */   }
/*     */   
/*     */   private void nextEngineRecord(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Font2D paramFont2D, int paramInt5) {
/* 524 */     EngineRecord engineRecord = null;
/* 525 */     if (this._ercount == this._erecords.size()) {
/* 526 */       engineRecord = new EngineRecord();
/* 527 */       this._erecords.add(engineRecord);
/*     */     } else {
/* 529 */       engineRecord = this._erecords.get(this._ercount);
/*     */     } 
/* 531 */     engineRecord.init(paramInt1, paramInt2, paramFont2D, paramInt3, paramInt4, paramInt5);
/* 532 */     this._ercount++;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class GVData
/*     */   {
/*     */     public int _count;
/*     */     
/*     */     public int _flags;
/*     */     
/*     */     public int[] _glyphs;
/*     */     public float[] _positions;
/*     */     public int[] _indices;
/*     */     private static final int UNINITIALIZED_FLAGS = -1;
/*     */     
/*     */     public void init(int param1Int) {
/* 548 */       this._count = 0;
/* 549 */       this._flags = -1;
/*     */       
/* 551 */       if (this._glyphs == null || this._glyphs.length < param1Int) {
/* 552 */         if (param1Int < 20) {
/* 553 */           param1Int = 20;
/*     */         }
/* 555 */         this._glyphs = new int[param1Int];
/* 556 */         this._positions = new float[param1Int * 2 + 2];
/* 557 */         this._indices = new int[param1Int];
/*     */       } 
/*     */     }
/*     */     
/*     */     public void grow() {
/* 562 */       grow(this._glyphs.length / 4);
/*     */     }
/*     */     
/*     */     public void grow(int param1Int) {
/* 566 */       int i = this._glyphs.length + param1Int;
/* 567 */       int[] arrayOfInt1 = new int[i];
/* 568 */       System.arraycopy(this._glyphs, 0, arrayOfInt1, 0, this._count);
/* 569 */       this._glyphs = arrayOfInt1;
/*     */       
/* 571 */       float[] arrayOfFloat = new float[i * 2 + 2];
/* 572 */       System.arraycopy(this._positions, 0, arrayOfFloat, 0, this._count * 2 + 2);
/* 573 */       this._positions = arrayOfFloat;
/*     */       
/* 575 */       int[] arrayOfInt2 = new int[i];
/* 576 */       System.arraycopy(this._indices, 0, arrayOfInt2, 0, this._count);
/* 577 */       this._indices = arrayOfInt2;
/*     */     }
/*     */     
/*     */     public void adjustPositions(AffineTransform param1AffineTransform) {
/* 581 */       param1AffineTransform.transform(this._positions, 0, this._positions, 0, this._count);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public StandardGlyphVector createGlyphVector(Font param1Font, FontRenderContext param1FontRenderContext, StandardGlyphVector param1StandardGlyphVector) {
/* 587 */       if (this._flags == -1) {
/* 588 */         this._flags = 0;
/*     */         
/* 590 */         if (this._count > 1) {
/* 591 */           boolean bool1 = true;
/* 592 */           boolean bool2 = true;
/*     */           
/* 594 */           int i = this._count;
/* 595 */           for (byte b = 0; b < this._count && (bool1 || bool2); b++) {
/* 596 */             int j = this._indices[b];
/*     */             
/* 598 */             bool1 = (bool1 && j == b) ? true : false;
/* 599 */             bool2 = (bool2 && j == --i) ? true : false;
/*     */           } 
/*     */           
/* 602 */           if (bool2) this._flags |= 0x4; 
/* 603 */           if (!bool2 && !bool1) this._flags |= 0x8;
/*     */         
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 609 */         this._flags |= 0x2;
/*     */       } 
/*     */       
/* 612 */       int[] arrayOfInt1 = new int[this._count];
/* 613 */       System.arraycopy(this._glyphs, 0, arrayOfInt1, 0, this._count);
/*     */       
/* 615 */       float[] arrayOfFloat = null;
/* 616 */       if ((this._flags & 0x2) != 0) {
/* 617 */         arrayOfFloat = new float[this._count * 2 + 2];
/* 618 */         System.arraycopy(this._positions, 0, arrayOfFloat, 0, arrayOfFloat.length);
/*     */       } 
/*     */       
/* 621 */       int[] arrayOfInt2 = null;
/* 622 */       if ((this._flags & 0x8) != 0) {
/* 623 */         arrayOfInt2 = new int[this._count];
/* 624 */         System.arraycopy(this._indices, 0, arrayOfInt2, 0, this._count);
/*     */       } 
/*     */       
/* 627 */       if (param1StandardGlyphVector == null) {
/* 628 */         param1StandardGlyphVector = new StandardGlyphVector(param1Font, param1FontRenderContext, arrayOfInt1, arrayOfFloat, arrayOfInt2, this._flags);
/*     */       } else {
/* 630 */         param1StandardGlyphVector.initGlyphVector(param1Font, param1FontRenderContext, arrayOfInt1, arrayOfFloat, arrayOfInt2, this._flags);
/*     */       } 
/*     */       
/* 633 */       return param1StandardGlyphVector;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final class EngineRecord
/*     */   {
/*     */     private int start;
/*     */ 
/*     */     
/*     */     private int limit;
/*     */     
/*     */     private int gmask;
/*     */     
/*     */     private int eflags;
/*     */     
/* 650 */     private GlyphLayout.LayoutEngineKey key = new GlyphLayout.LayoutEngineKey();
/*     */     private GlyphLayout.LayoutEngine engine;
/*     */     
/*     */     void init(int param1Int1, int param1Int2, Font2D param1Font2D, int param1Int3, int param1Int4, int param1Int5) {
/* 654 */       this.start = param1Int1;
/* 655 */       this.limit = param1Int2;
/* 656 */       this.gmask = param1Int5;
/* 657 */       this.key.init(param1Font2D, param1Int3, param1Int4);
/* 658 */       this.eflags = 0;
/*     */ 
/*     */       
/* 661 */       for (int i = param1Int1; i < param1Int2; i++) {
/* 662 */         int j = GlyphLayout.this._textRecord.text[i];
/* 663 */         if (Character.isHighSurrogate((char)j) && i < param1Int2 - 1 && 
/*     */           
/* 665 */           Character.isLowSurrogate(GlyphLayout.this._textRecord.text[i + 1]))
/*     */         {
/* 667 */           j = Character.toCodePoint((char)j, GlyphLayout.this._textRecord.text[++i]);
/*     */         }
/* 669 */         int k = Character.getType(j);
/* 670 */         if (k == 6 || k == 7 || k == 8) {
/*     */ 
/*     */ 
/*     */           
/* 674 */           this.eflags = 4;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 679 */       this.engine = GlyphLayout.this._lef.getEngine(this.key);
/*     */     }
/*     */     
/*     */     void layout() {
/* 683 */       GlyphLayout.this._textRecord.start = this.start;
/* 684 */       GlyphLayout.this._textRecord.limit = this.limit;
/* 685 */       this.engine.layout(GlyphLayout.this._sd, GlyphLayout.this._mat, this.gmask, this.start - GlyphLayout.this._offset, GlyphLayout.this._textRecord, GlyphLayout.this
/* 686 */           ._typo_flags | this.eflags, GlyphLayout.this._pt, GlyphLayout.this._gvdata);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/GlyphLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */