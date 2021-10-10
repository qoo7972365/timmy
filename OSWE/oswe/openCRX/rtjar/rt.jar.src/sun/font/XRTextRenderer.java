/*     */ package sun.font;
/*     */ 
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.pipe.GlyphListPipe;
/*     */ import sun.java2d.xr.GrowableEltArray;
/*     */ import sun.java2d.xr.XRBackend;
/*     */ import sun.java2d.xr.XRCompositeManager;
/*     */ import sun.java2d.xr.XRSurfaceData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XRTextRenderer
/*     */   extends GlyphListPipe
/*     */ {
/*     */   static final int MAX_ELT_GLYPH_COUNT = 253;
/*     */   XRGlyphCache glyphCache;
/*     */   XRCompositeManager maskBuffer;
/*     */   XRBackend backend;
/*     */   GrowableEltArray eltList;
/*     */   
/*     */   public XRTextRenderer(XRCompositeManager paramXRCompositeManager) {
/*  51 */     this.glyphCache = new XRGlyphCache(paramXRCompositeManager);
/*  52 */     this.maskBuffer = paramXRCompositeManager;
/*  53 */     this.backend = paramXRCompositeManager.getBackend();
/*  54 */     this.eltList = new GrowableEltArray(64);
/*     */   }
/*     */   
/*     */   protected void drawGlyphList(SunGraphics2D paramSunGraphics2D, GlyphList paramGlyphList) {
/*  58 */     if (paramGlyphList.getNumGlyphs() == 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/*  63 */       SunToolkit.awtLock();
/*     */       
/*  65 */       XRSurfaceData xRSurfaceData = (XRSurfaceData)paramSunGraphics2D.surfaceData;
/*  66 */       xRSurfaceData.validateAsDestination(null, paramSunGraphics2D.getCompClip());
/*  67 */       xRSurfaceData.maskBuffer.validateCompositeState(paramSunGraphics2D.composite, paramSunGraphics2D.transform, paramSunGraphics2D.paint, paramSunGraphics2D);
/*     */       
/*  69 */       float f1 = paramGlyphList.getX();
/*  70 */       float f2 = paramGlyphList.getY();
/*  71 */       int i = 0, j = 0;
/*     */       
/*  73 */       if (paramGlyphList.isSubPixPos()) {
/*  74 */         f1 += 0.1666667F;
/*  75 */         f2 += 0.1666667F;
/*     */       } else {
/*  77 */         f1 += 0.5F;
/*  78 */         f2 += 0.5F;
/*     */       } 
/*     */       
/*  81 */       XRGlyphCacheEntry[] arrayOfXRGlyphCacheEntry = this.glyphCache.cacheGlyphs(paramGlyphList);
/*  82 */       int k = 0;
/*  83 */       int m = arrayOfXRGlyphCacheEntry[0].getGlyphSet();
/*     */       
/*  85 */       int n = -1;
/*  86 */       paramGlyphList.getBounds();
/*  87 */       float[] arrayOfFloat = paramGlyphList.getPositions(); byte b;
/*  88 */       for (b = 0; b < paramGlyphList.getNumGlyphs(); b++) {
/*  89 */         paramGlyphList.setGlyphIndex(b);
/*  90 */         XRGlyphCacheEntry xRGlyphCacheEntry = arrayOfXRGlyphCacheEntry[b];
/*  91 */         if (xRGlyphCacheEntry != null) {
/*     */ 
/*     */ 
/*     */           
/*  95 */           this.eltList.getGlyphs().addInt(xRGlyphCacheEntry.getGlyphID());
/*  96 */           int i1 = xRGlyphCacheEntry.getGlyphSet();
/*     */           
/*  98 */           k |= (i1 == this.glyphCache.lcdGlyphSet) ? 1 : 0;
/*     */           
/* 100 */           int i2 = 0, i3 = 0;
/* 101 */           if (paramGlyphList.usePositions() || xRGlyphCacheEntry
/* 102 */             .getXAdvance() != xRGlyphCacheEntry.getXOff() || xRGlyphCacheEntry
/* 103 */             .getYAdvance() != xRGlyphCacheEntry.getYOff() || i1 != m || n < 0 || this.eltList
/*     */ 
/*     */             
/* 106 */             .getCharCnt(n) == 253) {
/*     */             
/* 108 */             n = this.eltList.getNextIndex();
/* 109 */             this.eltList.setCharCnt(n, 1);
/* 110 */             m = i1;
/* 111 */             this.eltList.setGlyphSet(n, i1);
/*     */             
/* 113 */             if (paramGlyphList.usePositions()) {
/*     */               
/* 115 */               float f3 = arrayOfFloat[b * 2] + f1;
/* 116 */               float f4 = arrayOfFloat[b * 2 + 1] + f2;
/* 117 */               i2 = (int)Math.floor(f3);
/* 118 */               i3 = (int)Math.floor(f4);
/* 119 */               f1 -= xRGlyphCacheEntry.getXOff();
/* 120 */               f2 -= xRGlyphCacheEntry.getYOff();
/*     */ 
/*     */ 
/*     */             
/*     */             }
/*     */             else {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 130 */               i2 = (int)Math.floor(f1);
/* 131 */               i3 = (int)Math.floor(f2);
/*     */ 
/*     */ 
/*     */               
/* 135 */               f1 += xRGlyphCacheEntry.getXAdvance() - xRGlyphCacheEntry.getXOff();
/* 136 */               f2 += xRGlyphCacheEntry.getYAdvance() - xRGlyphCacheEntry.getYOff();
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 141 */             this.eltList.setXOff(n, i2 - i);
/* 142 */             this.eltList.setYOff(n, i3 - j);
/*     */             
/* 144 */             i = i2;
/* 145 */             j = i3;
/*     */           } else {
/*     */             
/* 148 */             this.eltList.setCharCnt(n, this.eltList.getCharCnt(n) + 1);
/*     */           } 
/*     */         } 
/*     */       } 
/* 152 */       b = (k != 0) ? 0 : 2;
/* 153 */       this.maskBuffer.compositeText(xRSurfaceData, (int)paramGlyphList.getX(), (int)paramGlyphList.getY(), 0, b, this.eltList);
/*     */       
/* 155 */       this.eltList.clear();
/*     */     } finally {
/* 157 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/XRTextRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */