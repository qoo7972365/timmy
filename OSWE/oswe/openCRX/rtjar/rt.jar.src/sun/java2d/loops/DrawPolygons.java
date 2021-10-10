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
/*     */ public class DrawPolygons
/*     */   extends GraphicsPrimitive
/*     */ {
/*  44 */   public static final String methodSignature = "DrawPolygons(...)".toString();
/*     */   
/*  46 */   public static final int primTypeID = makePrimTypeID();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DrawPolygons locate(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  52 */     return 
/*  53 */       (DrawPolygons)GraphicsPrimitiveMgr.locate(primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DrawPolygons(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  61 */     super(methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DrawPolygons(long paramLong, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  69 */     super(paramLong, methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public native void DrawPolygons(SunGraphics2D paramSunGraphics2D, SurfaceData paramSurfaceData, int[] paramArrayOfint1, int[] paramArrayOfint2, int[] paramArrayOfint3, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive makePrimitive(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  86 */     throw new InternalError("DrawPolygons not implemented for " + paramSurfaceType1 + " with " + paramCompositeType);
/*     */   }
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive traceWrap() {
/*  91 */     return new TraceDrawPolygons(this);
/*     */   }
/*     */   
/*     */   private static class TraceDrawPolygons extends DrawPolygons {
/*     */     DrawPolygons target;
/*     */     
/*     */     public TraceDrawPolygons(DrawPolygons param1DrawPolygons) {
/*  98 */       super(param1DrawPolygons.getSourceType(), param1DrawPolygons
/*  99 */           .getCompositeType(), param1DrawPolygons
/* 100 */           .getDestType());
/* 101 */       this.target = param1DrawPolygons;
/*     */     }
/*     */     
/*     */     public GraphicsPrimitive traceWrap() {
/* 105 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void DrawPolygons(SunGraphics2D param1SunGraphics2D, SurfaceData param1SurfaceData, int[] param1ArrayOfint1, int[] param1ArrayOfint2, int[] param1ArrayOfint3, int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean) {
/* 114 */       tracePrimitive(this.target);
/* 115 */       this.target.DrawPolygons(param1SunGraphics2D, param1SurfaceData, param1ArrayOfint1, param1ArrayOfint2, param1ArrayOfint3, param1Int1, param1Int2, param1Int3, param1Boolean);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/DrawPolygons.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */