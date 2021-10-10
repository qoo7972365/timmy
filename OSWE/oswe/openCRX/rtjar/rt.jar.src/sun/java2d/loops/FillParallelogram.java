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
/*     */ public class FillParallelogram
/*     */   extends GraphicsPrimitive
/*     */ {
/*  43 */   public static final String methodSignature = "FillParallelogram(...)"
/*  44 */     .toString();
/*     */   
/*  46 */   public static final int primTypeID = makePrimTypeID();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FillParallelogram locate(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  52 */     return 
/*  53 */       (FillParallelogram)GraphicsPrimitiveMgr.locate(primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FillParallelogram(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  61 */     super(methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FillParallelogram(long paramLong, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  70 */     super(paramLong, methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public native void FillParallelogram(SunGraphics2D paramSunGraphics2D, SurfaceData paramSurfaceData, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive makePrimitive(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  87 */     throw new InternalError("FillParallelogram not implemented for " + paramSurfaceType1 + " with " + paramCompositeType);
/*     */   }
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive traceWrap() {
/*  92 */     return new TraceFillParallelogram(this);
/*     */   }
/*     */   
/*     */   private static class TraceFillParallelogram extends FillParallelogram {
/*     */     FillParallelogram target;
/*     */     
/*     */     public TraceFillParallelogram(FillParallelogram param1FillParallelogram) {
/*  99 */       super(param1FillParallelogram.getSourceType(), param1FillParallelogram
/* 100 */           .getCompositeType(), param1FillParallelogram
/* 101 */           .getDestType());
/* 102 */       this.target = param1FillParallelogram;
/*     */     }
/*     */     
/*     */     public GraphicsPrimitive traceWrap() {
/* 106 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void FillParallelogram(SunGraphics2D param1SunGraphics2D, SurfaceData param1SurfaceData, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6) {
/* 114 */       tracePrimitive(this.target);
/* 115 */       this.target.FillParallelogram(param1SunGraphics2D, param1SurfaceData, param1Double1, param1Double2, param1Double3, param1Double4, param1Double5, param1Double6);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/FillParallelogram.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */