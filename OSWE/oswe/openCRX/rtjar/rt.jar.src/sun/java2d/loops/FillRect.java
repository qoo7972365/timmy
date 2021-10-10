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
/*     */ 
/*     */ 
/*     */ public class FillRect
/*     */   extends GraphicsPrimitive
/*     */ {
/*  47 */   public static final String methodSignature = "FillRect(...)".toString();
/*     */   
/*  49 */   public static final int primTypeID = makePrimTypeID();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FillRect locate(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  55 */     return 
/*  56 */       (FillRect)GraphicsPrimitiveMgr.locate(primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FillRect(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  64 */     super(methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FillRect(long paramLong, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  72 */     super(paramLong, methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  82 */     GraphicsPrimitiveMgr.registerGeneral(new FillRect(null, null, null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive makePrimitive(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  89 */     return new General(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class General
/*     */     extends FillRect
/*     */   {
/*     */     public MaskFill fillop;
/*     */     
/*     */     public General(SurfaceType param1SurfaceType1, CompositeType param1CompositeType, SurfaceType param1SurfaceType2) {
/*  99 */       super(param1SurfaceType1, param1CompositeType, param1SurfaceType2);
/* 100 */       this.fillop = MaskFill.locate(param1SurfaceType1, param1CompositeType, param1SurfaceType2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void FillRect(SunGraphics2D param1SunGraphics2D, SurfaceData param1SurfaceData, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 106 */       this.fillop.MaskFill(param1SunGraphics2D, param1SurfaceData, param1SunGraphics2D.composite, param1Int1, param1Int2, param1Int3, param1Int4, null, 0, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public GraphicsPrimitive traceWrap() {
/* 111 */     return new TraceFillRect(this);
/*     */   }
/*     */   
/*     */   public native void FillRect(SunGraphics2D paramSunGraphics2D, SurfaceData paramSurfaceData, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   private static class TraceFillRect extends FillRect {
/*     */     public TraceFillRect(FillRect param1FillRect) {
/* 118 */       super(param1FillRect.getSourceType(), param1FillRect
/* 119 */           .getCompositeType(), param1FillRect
/* 120 */           .getDestType());
/* 121 */       this.target = param1FillRect;
/*     */     }
/*     */     FillRect target;
/*     */     public GraphicsPrimitive traceWrap() {
/* 125 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void FillRect(SunGraphics2D param1SunGraphics2D, SurfaceData param1SurfaceData, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 131 */       tracePrimitive(this.target);
/* 132 */       this.target.FillRect(param1SunGraphics2D, param1SurfaceData, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/FillRect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */