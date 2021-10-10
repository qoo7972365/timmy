/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.geom.Point2D;
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
/*     */ public abstract class PhysicalStrike
/*     */   extends FontStrike
/*     */ {
/*     */   static final long INTMASK = 4294967295L;
/*     */   static boolean longAddresses;
/*     */   private PhysicalFont physicalFont;
/*     */   protected CharToGlyphMapper mapper;
/*     */   protected long pScalerContext;
/*     */   protected long[] longGlyphImages;
/*     */   protected int[] intGlyphImages;
/*     */   ConcurrentHashMap<Integer, Point2D.Float> glyphPointMapCache;
/*     */   protected boolean getImageWithAdvance;
/*     */   protected static final int complexTX = 124;
/*     */   
/*     */   static {
/*  41 */     switch (StrikeCache.nativeAddressSize) { case 8:
/*  42 */         longAddresses = true; return;
/*  43 */       case 4: longAddresses = false; return; }
/*  44 */      throw new RuntimeException("Unexpected address size");
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PhysicalStrike(PhysicalFont paramPhysicalFont, FontStrikeDesc paramFontStrikeDesc) {
/*  84 */     this.physicalFont = paramPhysicalFont;
/*  85 */     this.desc = paramFontStrikeDesc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PhysicalStrike() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumGlyphs() {
/*  95 */     return this.physicalFont.getNumGlyphs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   StrikeMetrics getFontMetrics() {
/* 102 */     if (this.strikeMetrics == null) {
/* 103 */       this
/* 104 */         .strikeMetrics = this.physicalFont.getFontMetrics(this.pScalerContext);
/*     */     }
/* 106 */     return this.strikeMetrics;
/*     */   }
/*     */   
/*     */   float getCodePointAdvance(int paramInt) {
/* 110 */     return getGlyphAdvance(this.physicalFont.getMapper().charToGlyph(paramInt));
/*     */   }
/*     */   
/*     */   Point2D.Float getCharMetrics(char paramChar) {
/* 114 */     return getGlyphMetrics(this.physicalFont.getMapper().charToGlyph(paramChar));
/*     */   }
/*     */   
/*     */   int getSlot0GlyphImagePtrs(int[] paramArrayOfint, long[] paramArrayOflong, int paramInt) {
/* 118 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Point2D.Float getGlyphPoint(int paramInt1, int paramInt2) {
/* 124 */     Point2D.Float float_ = null;
/* 125 */     Integer integer = Integer.valueOf(paramInt1 << 16 | paramInt2);
/* 126 */     if (this.glyphPointMapCache == null) {
/* 127 */       synchronized (this) {
/* 128 */         if (this.glyphPointMapCache == null) {
/* 129 */           this.glyphPointMapCache = new ConcurrentHashMap<>();
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 134 */       float_ = this.glyphPointMapCache.get(integer);
/*     */     } 
/*     */     
/* 137 */     if (float_ == null) {
/* 138 */       float_ = this.physicalFont.getGlyphPoint(this.pScalerContext, paramInt1, paramInt2);
/* 139 */       adjustPoint(float_);
/* 140 */       this.glyphPointMapCache.put(integer, float_);
/*     */     } 
/* 142 */     return float_;
/*     */   }
/*     */   
/*     */   protected void adjustPoint(Point2D.Float paramFloat) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/PhysicalStrike.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */