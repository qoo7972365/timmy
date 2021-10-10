/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.geom.Point2D;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.WeakHashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SunLayoutEngine
/*     */   implements GlyphLayout.LayoutEngine, GlyphLayout.LayoutEngineFactory
/*     */ {
/*     */   private GlyphLayout.LayoutEngineKey key;
/*     */   private static GlyphLayout.LayoutEngineFactory instance;
/*     */   private SoftReference cacheref;
/*     */   
/*     */   static {
/* 108 */     FontManagerNativeLibrary.load();
/* 109 */     initGVIDs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GlyphLayout.LayoutEngineFactory instance() {
/* 117 */     if (instance == null) {
/* 118 */       instance = new SunLayoutEngine();
/*     */     }
/* 120 */     return instance;
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
/*     */   private SunLayoutEngine()
/*     */   {
/* 147 */     this.cacheref = new SoftReference(null); } public GlyphLayout.LayoutEngine getEngine(Font2D paramFont2D, int paramInt1, int paramInt2) { return getEngine(new GlyphLayout.LayoutEngineKey(paramFont2D, paramInt1, paramInt2)); } private SunLayoutEngine(GlyphLayout.LayoutEngineKey paramLayoutEngineKey) { this.cacheref = new SoftReference(null);
/*     */ 
/*     */     
/* 150 */     this.key = paramLayoutEngineKey; }
/*     */   public GlyphLayout.LayoutEngine getEngine(GlyphLayout.LayoutEngineKey paramLayoutEngineKey) { ConcurrentHashMap<Object, Object> concurrentHashMap = this.cacheref.get(); if (concurrentHashMap == null) { concurrentHashMap = new ConcurrentHashMap<>(); this.cacheref = new SoftReference<>(concurrentHashMap); }
/*     */      GlyphLayout.LayoutEngine layoutEngine = (GlyphLayout.LayoutEngine)concurrentHashMap.get(paramLayoutEngineKey); if (layoutEngine == null) { GlyphLayout.LayoutEngineKey layoutEngineKey = paramLayoutEngineKey.copy(); layoutEngine = new SunLayoutEngine(layoutEngineKey); concurrentHashMap.put(layoutEngineKey, layoutEngine); }
/* 153 */      return layoutEngine; } static WeakHashMap<Font2D, Boolean> aatInfo = new WeakHashMap<>();
/*     */   
/*     */   private boolean isAAT(Font2D paramFont2D) {
/*     */     Boolean bool;
/* 157 */     synchronized (aatInfo) {
/* 158 */       bool = aatInfo.get(paramFont2D);
/*     */     } 
/* 160 */     if (bool != null) {
/* 161 */       return bool.booleanValue();
/*     */     }
/* 163 */     boolean bool1 = false;
/* 164 */     if (paramFont2D instanceof TrueTypeFont) {
/* 165 */       TrueTypeFont trueTypeFont = (TrueTypeFont)paramFont2D;
/*     */       
/* 167 */       bool1 = (trueTypeFont.getDirectoryEntry(1836020344) != null || trueTypeFont.getDirectoryEntry(1836020340) != null) ? true : false;
/* 168 */     } else if (paramFont2D instanceof PhysicalFont) {
/* 169 */       PhysicalFont physicalFont = (PhysicalFont)paramFont2D;
/*     */       
/* 171 */       bool1 = (physicalFont.getTableBytes(1836020344) != null || physicalFont.getTableBytes(1836020340) != null) ? true : false;
/*     */     } 
/* 173 */     synchronized (aatInfo) {
/* 174 */       aatInfo.put(paramFont2D, Boolean.valueOf(bool1));
/*     */     } 
/* 176 */     return bool1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void layout(FontStrikeDesc paramFontStrikeDesc, float[] paramArrayOffloat, int paramInt1, int paramInt2, TextRecord paramTextRecord, int paramInt3, Point2D.Float paramFloat, GlyphLayout.GVData paramGVData) {
/* 182 */     Font2D font2D = this.key.font();
/* 183 */     FontStrike fontStrike = font2D.getStrike(paramFontStrikeDesc);
/*     */ 
/*     */     
/* 186 */     long l = ((paramInt3 & Integer.MIN_VALUE) != 0 && isAAT(font2D)) ? 0L : font2D.getLayoutTableCache();
/* 187 */     nativeLayout(font2D, fontStrike, paramArrayOffloat, paramInt1, paramInt2, paramTextRecord.text, paramTextRecord.start, paramTextRecord.limit, paramTextRecord.min, paramTextRecord.max, this.key
/*     */         
/* 189 */         .script(), this.key.lang(), paramInt3, paramFloat, paramGVData, font2D
/* 190 */         .getUnitsPerEm(), l);
/*     */   }
/*     */   
/*     */   private static native void initGVIDs();
/*     */   
/*     */   private static native void nativeLayout(Font2D paramFont2D, FontStrike paramFontStrike, float[] paramArrayOffloat, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, Point2D.Float paramFloat, GlyphLayout.GVData paramGVData, long paramLong1, long paramLong2);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/SunLayoutEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */