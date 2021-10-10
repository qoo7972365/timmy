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
/*     */ public class DrawGlyphList
/*     */   extends GraphicsPrimitive
/*     */ {
/*  42 */   public static final String methodSignature = "DrawGlyphList(...)".toString();
/*     */   
/*  44 */   public static final int primTypeID = makePrimTypeID();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DrawGlyphList locate(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  50 */     return 
/*  51 */       (DrawGlyphList)GraphicsPrimitiveMgr.locate(primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DrawGlyphList(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  59 */     super(methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DrawGlyphList(long paramLong, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  67 */     super(paramLong, methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  76 */     GraphicsPrimitiveMgr.registerGeneral(new DrawGlyphList(null, null, null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive makePrimitive(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  83 */     return new General(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class General
/*     */     extends DrawGlyphList
/*     */   {
/*     */     MaskFill maskop;
/*     */     
/*     */     public General(SurfaceType param1SurfaceType1, CompositeType param1CompositeType, SurfaceType param1SurfaceType2) {
/*  93 */       super(param1SurfaceType1, param1CompositeType, param1SurfaceType2);
/*  94 */       this.maskop = MaskFill.locate(param1SurfaceType1, param1CompositeType, param1SurfaceType2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void DrawGlyphList(SunGraphics2D param1SunGraphics2D, SurfaceData param1SurfaceData, GlyphList param1GlyphList) {
/* 100 */       int[] arrayOfInt = param1GlyphList.getBounds();
/* 101 */       int i = param1GlyphList.getNumGlyphs();
/* 102 */       Region region = param1SunGraphics2D.getCompClip();
/* 103 */       int j = region.getLoX();
/* 104 */       int k = region.getLoY();
/* 105 */       int m = region.getHiX();
/* 106 */       int n = region.getHiY();
/* 107 */       for (byte b = 0; b < i; b++) {
/* 108 */         param1GlyphList.setGlyphIndex(b);
/* 109 */         int[] arrayOfInt1 = param1GlyphList.getMetrics();
/* 110 */         int i1 = arrayOfInt1[0];
/* 111 */         int i2 = arrayOfInt1[1];
/* 112 */         int i3 = arrayOfInt1[2];
/* 113 */         int i4 = i1 + i3;
/* 114 */         int i5 = i2 + arrayOfInt1[3];
/* 115 */         int i6 = 0;
/* 116 */         if (i1 < j) {
/* 117 */           i6 = j - i1;
/* 118 */           i1 = j;
/*     */         } 
/* 120 */         if (i2 < k) {
/* 121 */           i6 += (k - i2) * i3;
/* 122 */           i2 = k;
/*     */         } 
/* 124 */         if (i4 > m) i4 = m; 
/* 125 */         if (i5 > n) i5 = n; 
/* 126 */         if (i4 > i1 && i5 > i2) {
/* 127 */           byte[] arrayOfByte = param1GlyphList.getGrayBits();
/* 128 */           this.maskop.MaskFill(param1SunGraphics2D, param1SurfaceData, param1SunGraphics2D.composite, i1, i2, i4 - i1, i5 - i2, arrayOfByte, i6, i3);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive traceWrap() {
/* 137 */     return new TraceDrawGlyphList(this);
/*     */   }
/*     */   
/*     */   public native void DrawGlyphList(SunGraphics2D paramSunGraphics2D, SurfaceData paramSurfaceData, GlyphList paramGlyphList);
/*     */   
/*     */   private static class TraceDrawGlyphList extends DrawGlyphList {
/*     */     public TraceDrawGlyphList(DrawGlyphList param1DrawGlyphList) {
/* 144 */       super(param1DrawGlyphList.getSourceType(), param1DrawGlyphList
/* 145 */           .getCompositeType(), param1DrawGlyphList
/* 146 */           .getDestType());
/* 147 */       this.target = param1DrawGlyphList;
/*     */     }
/*     */     DrawGlyphList target;
/*     */     public GraphicsPrimitive traceWrap() {
/* 151 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void DrawGlyphList(SunGraphics2D param1SunGraphics2D, SurfaceData param1SurfaceData, GlyphList param1GlyphList) {
/* 157 */       tracePrimitive(this.target);
/* 158 */       this.target.DrawGlyphList(param1SunGraphics2D, param1SurfaceData, param1GlyphList);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/DrawGlyphList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */