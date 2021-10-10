/*     */ package sun.java2d.loops;
/*     */ 
/*     */ import java.awt.Composite;
/*     */ import java.awt.geom.AffineTransform;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransformBlit
/*     */   extends GraphicsPrimitive
/*     */ {
/*  49 */   public static final String methodSignature = "TransformBlit(...)"
/*  50 */     .toString();
/*     */   
/*  52 */   public static final int primTypeID = makePrimTypeID();
/*     */   
/*  54 */   private static RenderCache blitcache = new RenderCache(10);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TransformBlit locate(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  60 */     return 
/*  61 */       (TransformBlit)GraphicsPrimitiveMgr.locate(primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TransformBlit getFromCache(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  69 */     Object object = blitcache.get(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*  70 */     if (object != null) {
/*  71 */       return (TransformBlit)object;
/*     */     }
/*  73 */     TransformBlit transformBlit = locate(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*  74 */     if (transformBlit != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  82 */       blitcache.put(paramSurfaceType1, paramCompositeType, paramSurfaceType2, transformBlit);
/*     */     }
/*  84 */     return transformBlit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TransformBlit(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  91 */     super(methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransformBlit(long paramLong, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  99 */     super(paramLong, methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
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
/*     */   static {
/* 111 */     GraphicsPrimitiveMgr.registerGeneral(new TransformBlit(null, null, null));
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
/*     */   public GraphicsPrimitive makePrimitive(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 125 */     return null;
/*     */   }
/*     */   
/*     */   public GraphicsPrimitive traceWrap() {
/* 129 */     return new TraceTransformBlit(this);
/*     */   }
/*     */   
/*     */   public native void Transform(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Composite paramComposite, Region paramRegion, AffineTransform paramAffineTransform, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
/*     */   
/*     */   private static class TraceTransformBlit extends TransformBlit {
/*     */     public TraceTransformBlit(TransformBlit param1TransformBlit) {
/* 136 */       super(param1TransformBlit.getSourceType(), param1TransformBlit
/* 137 */           .getCompositeType(), param1TransformBlit
/* 138 */           .getDestType());
/* 139 */       this.target = param1TransformBlit;
/*     */     }
/*     */     TransformBlit target;
/*     */     public GraphicsPrimitive traceWrap() {
/* 143 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void Transform(SurfaceData param1SurfaceData1, SurfaceData param1SurfaceData2, Composite param1Composite, Region param1Region, AffineTransform param1AffineTransform, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7) {
/* 152 */       tracePrimitive(this.target);
/* 153 */       this.target.Transform(param1SurfaceData1, param1SurfaceData2, param1Composite, param1Region, param1AffineTransform, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6, param1Int7);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/TransformBlit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */