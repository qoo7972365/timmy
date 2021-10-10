/*     */ package sun.java2d.loops;
/*     */ 
/*     */ import sun.font.GlyphList;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.pipe.Region;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DrawGlyphListAA
/*     */   extends GraphicsPrimitive
/*     */ {
/*  42 */   public static final String methodSignature = "DrawGlyphListAA(...)".toString();
/*     */   
/*  44 */   public static final int primTypeID = makePrimTypeID();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DrawGlyphListAA locate(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  50 */     return 
/*  51 */       (DrawGlyphListAA)GraphicsPrimitiveMgr.locate(primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DrawGlyphListAA(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  59 */     super(methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DrawGlyphListAA(long paramLong, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  67 */     super(paramLong, methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  74 */     GraphicsPrimitiveMgr.registerGeneral(new DrawGlyphListAA(null, null, null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive makePrimitive(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  81 */     return new General(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class General
/*     */     extends DrawGlyphListAA
/*     */   {
/*     */     MaskFill maskop;
/*     */     
/*     */     public General(SurfaceType param1SurfaceType1, CompositeType param1CompositeType, SurfaceType param1SurfaceType2) {
/*  91 */       super(param1SurfaceType1, param1CompositeType, param1SurfaceType2);
/*  92 */       this.maskop = MaskFill.locate(param1SurfaceType1, param1CompositeType, param1SurfaceType2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void DrawGlyphListAA(SunGraphics2D param1SunGraphics2D, SurfaceData param1SurfaceData, GlyphList param1GlyphList) {
/*  98 */       param1GlyphList.getBounds();
/*  99 */       int i = param1GlyphList.getNumGlyphs();
/* 100 */       Region region = param1SunGraphics2D.getCompClip();
/* 101 */       int j = region.getLoX();
/* 102 */       int k = region.getLoY();
/* 103 */       int m = region.getHiX();
/* 104 */       int n = region.getHiY();
/* 105 */       for (byte b = 0; b < i; b++) {
/* 106 */         param1GlyphList.setGlyphIndex(b);
/* 107 */         int[] arrayOfInt = param1GlyphList.getMetrics();
/* 108 */         int i1 = arrayOfInt[0];
/* 109 */         int i2 = arrayOfInt[1];
/* 110 */         int i3 = arrayOfInt[2];
/* 111 */         int i4 = i1 + i3;
/* 112 */         int i5 = i2 + arrayOfInt[3];
/* 113 */         int i6 = 0;
/* 114 */         if (i1 < j) {
/* 115 */           i6 = j - i1;
/* 116 */           i1 = j;
/*     */         } 
/* 118 */         if (i2 < k) {
/* 119 */           i6 += (k - i2) * i3;
/* 120 */           i2 = k;
/*     */         } 
/* 122 */         if (i4 > m) i4 = m; 
/* 123 */         if (i5 > n) i5 = n; 
/* 124 */         if (i4 > i1 && i5 > i2) {
/* 125 */           byte[] arrayOfByte = param1GlyphList.getGrayBits();
/* 126 */           this.maskop.MaskFill(param1SunGraphics2D, param1SurfaceData, param1SunGraphics2D.composite, i1, i2, i4 - i1, i5 - i2, arrayOfByte, i6, i3);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive traceWrap() {
/* 135 */     return new TraceDrawGlyphListAA(this);
/*     */   }
/*     */   
/*     */   public native void DrawGlyphListAA(SunGraphics2D paramSunGraphics2D, SurfaceData paramSurfaceData, GlyphList paramGlyphList);
/*     */   
/*     */   private static class TraceDrawGlyphListAA extends DrawGlyphListAA {
/*     */     public TraceDrawGlyphListAA(DrawGlyphListAA param1DrawGlyphListAA) {
/* 142 */       super(param1DrawGlyphListAA.getSourceType(), param1DrawGlyphListAA
/* 143 */           .getCompositeType(), param1DrawGlyphListAA
/* 144 */           .getDestType());
/* 145 */       this.target = param1DrawGlyphListAA;
/*     */     }
/*     */     DrawGlyphListAA target;
/*     */     public GraphicsPrimitive traceWrap() {
/* 149 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void DrawGlyphListAA(SunGraphics2D param1SunGraphics2D, SurfaceData param1SurfaceData, GlyphList param1GlyphList) {
/* 155 */       tracePrimitive(this.target);
/* 156 */       this.target.DrawGlyphListAA(param1SunGraphics2D, param1SurfaceData, param1GlyphList);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/DrawGlyphListAA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */