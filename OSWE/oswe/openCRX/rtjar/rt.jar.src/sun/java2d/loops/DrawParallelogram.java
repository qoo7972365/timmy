/*     */ package sun.java2d.loops;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DrawParallelogram
/*     */   extends GraphicsPrimitive
/*     */ {
/*  45 */   public static final String methodSignature = "DrawParallelogram(...)"
/*  46 */     .toString();
/*     */   
/*  48 */   public static final int primTypeID = makePrimTypeID();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DrawParallelogram locate(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  54 */     return 
/*  55 */       (DrawParallelogram)GraphicsPrimitiveMgr.locate(primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DrawParallelogram(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  63 */     super(methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DrawParallelogram(long paramLong, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  72 */     super(paramLong, methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public native void DrawParallelogram(SunGraphics2D paramSunGraphics2D, SurfaceData paramSurfaceData, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive makePrimitive(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  90 */     throw new InternalError("DrawParallelogram not implemented for " + paramSurfaceType1 + " with " + paramCompositeType);
/*     */   }
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive traceWrap() {
/*  95 */     return new TraceDrawParallelogram(this);
/*     */   }
/*     */   
/*     */   private static class TraceDrawParallelogram extends DrawParallelogram {
/*     */     DrawParallelogram target;
/*     */     
/*     */     public TraceDrawParallelogram(DrawParallelogram param1DrawParallelogram) {
/* 102 */       super(param1DrawParallelogram.getSourceType(), param1DrawParallelogram
/* 103 */           .getCompositeType(), param1DrawParallelogram
/* 104 */           .getDestType());
/* 105 */       this.target = param1DrawParallelogram;
/*     */     }
/*     */     
/*     */     public GraphicsPrimitive traceWrap() {
/* 109 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void DrawParallelogram(SunGraphics2D param1SunGraphics2D, SurfaceData param1SurfaceData, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8) {
/* 118 */       tracePrimitive(this.target);
/* 119 */       this.target.DrawParallelogram(param1SunGraphics2D, param1SurfaceData, param1Double1, param1Double2, param1Double3, param1Double4, param1Double5, param1Double6, param1Double7, param1Double8);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/DrawParallelogram.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */