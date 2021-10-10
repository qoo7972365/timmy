/*     */ package sun.java2d.loops;
/*     */ 
/*     */ import java.awt.Composite;
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
/*     */ public class ScaledBlit
/*     */   extends GraphicsPrimitive
/*     */ {
/*  48 */   public static final String methodSignature = "ScaledBlit(...)".toString();
/*     */   
/*  50 */   public static final int primTypeID = makePrimTypeID();
/*     */   
/*  52 */   private static RenderCache blitcache = new RenderCache(20);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ScaledBlit locate(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  58 */     return 
/*  59 */       (ScaledBlit)GraphicsPrimitiveMgr.locate(primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ScaledBlit getFromCache(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  67 */     Object object = blitcache.get(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*  68 */     if (object != null) {
/*  69 */       return (ScaledBlit)object;
/*     */     }
/*  71 */     ScaledBlit scaledBlit = locate(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*  72 */     if (scaledBlit != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  80 */       blitcache.put(paramSurfaceType1, paramCompositeType, paramSurfaceType2, scaledBlit);
/*     */     }
/*  82 */     return scaledBlit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ScaledBlit(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  89 */     super(methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScaledBlit(long paramLong, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  97 */     super(paramLong, methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
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
/* 109 */     GraphicsPrimitiveMgr.registerGeneral(new ScaledBlit(null, null, null));
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
/* 122 */     return null;
/*     */   }
/*     */   
/*     */   public GraphicsPrimitive traceWrap() {
/* 126 */     return new TraceScaledBlit(this);
/*     */   }
/*     */   
/*     */   public native void Scale(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Composite paramComposite, Region paramRegion, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */   
/*     */   private static class TraceScaledBlit extends ScaledBlit {
/*     */     public TraceScaledBlit(ScaledBlit param1ScaledBlit) {
/* 133 */       super(param1ScaledBlit.getSourceType(), param1ScaledBlit
/* 134 */           .getCompositeType(), param1ScaledBlit
/* 135 */           .getDestType());
/* 136 */       this.target = param1ScaledBlit;
/*     */     }
/*     */     ScaledBlit target;
/*     */     public GraphicsPrimitive traceWrap() {
/* 140 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void Scale(SurfaceData param1SurfaceData1, SurfaceData param1SurfaceData2, Composite param1Composite, Region param1Region, int param1Int1, int param1Int2, int param1Int3, int param1Int4, double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/* 150 */       tracePrimitive(this.target);
/* 151 */       this.target.Scale(param1SurfaceData1, param1SurfaceData2, param1Composite, param1Region, param1Int1, param1Int2, param1Int3, param1Int4, param1Double1, param1Double2, param1Double3, param1Double4);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/ScaledBlit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */