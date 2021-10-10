/*     */ package sun.java2d.loops;
/*     */ 
/*     */ import sun.font.GlyphList;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DrawGlyphListLCD
/*     */   extends GraphicsPrimitive
/*     */ {
/*  43 */   public static final String methodSignature = "DrawGlyphListLCD(...)".toString();
/*     */   
/*  45 */   public static final int primTypeID = makePrimTypeID();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DrawGlyphListLCD locate(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  51 */     return 
/*  52 */       (DrawGlyphListLCD)GraphicsPrimitiveMgr.locate(primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DrawGlyphListLCD(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  60 */     super(methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DrawGlyphListLCD(long paramLong, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  68 */     super(paramLong, methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  76 */     GraphicsPrimitiveMgr.registerGeneral(new DrawGlyphListLCD(null, null, null));
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
/*     */   public GraphicsPrimitive makePrimitive(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  89 */     return null;
/*     */   }
/*     */   
/*     */   public GraphicsPrimitive traceWrap() {
/*  93 */     return new TraceDrawGlyphListLCD(this);
/*     */   }
/*     */   
/*     */   public native void DrawGlyphListLCD(SunGraphics2D paramSunGraphics2D, SurfaceData paramSurfaceData, GlyphList paramGlyphList);
/*     */   
/*     */   private static class TraceDrawGlyphListLCD extends DrawGlyphListLCD {
/*     */     public TraceDrawGlyphListLCD(DrawGlyphListLCD param1DrawGlyphListLCD) {
/* 100 */       super(param1DrawGlyphListLCD.getSourceType(), param1DrawGlyphListLCD
/* 101 */           .getCompositeType(), param1DrawGlyphListLCD
/* 102 */           .getDestType());
/* 103 */       this.target = param1DrawGlyphListLCD;
/*     */     }
/*     */     DrawGlyphListLCD target;
/*     */     public GraphicsPrimitive traceWrap() {
/* 107 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void DrawGlyphListLCD(SunGraphics2D param1SunGraphics2D, SurfaceData param1SurfaceData, GlyphList param1GlyphList) {
/* 113 */       tracePrimitive(this.target);
/* 114 */       this.target.DrawGlyphListLCD(param1SunGraphics2D, param1SurfaceData, param1GlyphList);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/DrawGlyphListLCD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */