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
/*     */ public class TransformHelper
/*     */   extends GraphicsPrimitive
/*     */ {
/*  48 */   public static final String methodSignature = "TransformHelper(...)"
/*  49 */     .toString();
/*     */   
/*  51 */   public static final int primTypeID = makePrimTypeID();
/*     */   
/*  53 */   private static RenderCache helpercache = new RenderCache(10);
/*     */   
/*     */   public static TransformHelper locate(SurfaceType paramSurfaceType) {
/*  56 */     return 
/*  57 */       (TransformHelper)GraphicsPrimitiveMgr.locate(primTypeID, paramSurfaceType, CompositeType.SrcNoEa, SurfaceType.IntArgbPre);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized TransformHelper getFromCache(SurfaceType paramSurfaceType) {
/*  64 */     Object object = helpercache.get(paramSurfaceType, null, null);
/*  65 */     if (object != null) {
/*  66 */       return (TransformHelper)object;
/*     */     }
/*  68 */     TransformHelper transformHelper = locate(paramSurfaceType);
/*  69 */     if (transformHelper != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  75 */       helpercache.put(paramSurfaceType, null, null, transformHelper);
/*     */     }
/*  77 */     return transformHelper;
/*     */   }
/*     */   
/*     */   protected TransformHelper(SurfaceType paramSurfaceType) {
/*  81 */     super(methodSignature, primTypeID, paramSurfaceType, CompositeType.SrcNoEa, SurfaceType.IntArgbPre);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransformHelper(long paramLong, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  90 */     super(paramLong, methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public native void Transform(MaskBlit paramMaskBlit, SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Composite paramComposite, Region paramRegion, AffineTransform paramAffineTransform, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int[] paramArrayOfint, int paramInt10, int paramInt11);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive makePrimitive(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 106 */     return null;
/*     */   }
/*     */   
/*     */   public GraphicsPrimitive traceWrap() {
/* 110 */     return new TraceTransformHelper(this);
/*     */   }
/*     */   
/*     */   private static class TraceTransformHelper extends TransformHelper {
/*     */     TransformHelper target;
/*     */     
/*     */     public TraceTransformHelper(TransformHelper param1TransformHelper) {
/* 117 */       super(param1TransformHelper.getSourceType());
/* 118 */       this.target = param1TransformHelper;
/*     */     }
/*     */     
/*     */     public GraphicsPrimitive traceWrap() {
/* 122 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void Transform(MaskBlit param1MaskBlit, SurfaceData param1SurfaceData1, SurfaceData param1SurfaceData2, Composite param1Composite, Region param1Region, AffineTransform param1AffineTransform, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, int param1Int8, int param1Int9, int[] param1ArrayOfint, int param1Int10, int param1Int11) {
/* 133 */       tracePrimitive(this.target);
/* 134 */       this.target.Transform(param1MaskBlit, param1SurfaceData1, param1SurfaceData2, param1Composite, param1Region, param1AffineTransform, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6, param1Int7, param1Int8, param1Int9, param1ArrayOfint, param1Int10, param1Int11);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/TransformHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */