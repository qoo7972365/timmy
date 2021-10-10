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
/*     */ public class DrawLine
/*     */   extends GraphicsPrimitive
/*     */ {
/*  47 */   public static final String methodSignature = "DrawLine(...)".toString();
/*     */   
/*  49 */   public static final int primTypeID = makePrimTypeID();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DrawLine locate(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  55 */     return (DrawLine)GraphicsPrimitiveMgr.locate(primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DrawLine(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  63 */     super(methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DrawLine(long paramLong, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  71 */     super(paramLong, methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public native void DrawLine(SunGraphics2D paramSunGraphics2D, SurfaceData paramSurfaceData, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive makePrimitive(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  85 */     throw new InternalError("DrawLine not implemented for " + paramSurfaceType1 + " with " + paramCompositeType);
/*     */   }
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive traceWrap() {
/*  90 */     return new TraceDrawLine(this);
/*     */   }
/*     */   
/*     */   private static class TraceDrawLine extends DrawLine {
/*     */     DrawLine target;
/*     */     
/*     */     public TraceDrawLine(DrawLine param1DrawLine) {
/*  97 */       super(param1DrawLine.getSourceType(), param1DrawLine
/*  98 */           .getCompositeType(), param1DrawLine
/*  99 */           .getDestType());
/* 100 */       this.target = param1DrawLine;
/*     */     }
/*     */     
/*     */     public GraphicsPrimitive traceWrap() {
/* 104 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void DrawLine(SunGraphics2D param1SunGraphics2D, SurfaceData param1SurfaceData, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 110 */       tracePrimitive(this.target);
/* 111 */       this.target.DrawLine(param1SunGraphics2D, param1SurfaceData, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/DrawLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */