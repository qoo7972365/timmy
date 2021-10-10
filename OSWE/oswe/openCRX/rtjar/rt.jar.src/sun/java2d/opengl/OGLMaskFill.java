/*    */ package sun.java2d.opengl;
/*    */ 
/*    */ import java.awt.Composite;
/*    */ import sun.java2d.InvalidPipeException;
/*    */ import sun.java2d.SunGraphics2D;
/*    */ import sun.java2d.loops.CompositeType;
/*    */ import sun.java2d.loops.GraphicsPrimitive;
/*    */ import sun.java2d.loops.GraphicsPrimitiveMgr;
/*    */ import sun.java2d.loops.SurfaceType;
/*    */ import sun.java2d.pipe.BufferedMaskFill;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class OGLMaskFill
/*    */   extends BufferedMaskFill
/*    */ {
/*    */   static void register() {
/* 42 */     GraphicsPrimitive[] arrayOfGraphicsPrimitive = { new OGLMaskFill(SurfaceType.AnyColor, CompositeType.SrcOver), new OGLMaskFill(SurfaceType.OpaqueColor, CompositeType.SrcNoEa), new OGLMaskFill(SurfaceType.GradientPaint, CompositeType.SrcOver), new OGLMaskFill(SurfaceType.OpaqueGradientPaint, CompositeType.SrcNoEa), new OGLMaskFill(SurfaceType.LinearGradientPaint, CompositeType.SrcOver), new OGLMaskFill(SurfaceType.OpaqueLinearGradientPaint, CompositeType.SrcNoEa), new OGLMaskFill(SurfaceType.RadialGradientPaint, CompositeType.SrcOver), new OGLMaskFill(SurfaceType.OpaqueRadialGradientPaint, CompositeType.SrcNoEa), new OGLMaskFill(SurfaceType.TexturePaint, CompositeType.SrcOver), new OGLMaskFill(SurfaceType.OpaqueTexturePaint, CompositeType.SrcNoEa) };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 54 */     GraphicsPrimitiveMgr.register(arrayOfGraphicsPrimitive);
/*    */   }
/*    */   
/*    */   protected OGLMaskFill(SurfaceType paramSurfaceType, CompositeType paramCompositeType) {
/* 58 */     super(OGLRenderQueue.getInstance(), paramSurfaceType, paramCompositeType, OGLSurfaceData.OpenGLSurface);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected native void maskFill(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, byte[] paramArrayOfbyte);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void validateContext(SunGraphics2D paramSunGraphics2D, Composite paramComposite, int paramInt) {
/*    */     OGLSurfaceData oGLSurfaceData;
/*    */     try {
/* 73 */       oGLSurfaceData = (OGLSurfaceData)paramSunGraphics2D.surfaceData;
/* 74 */     } catch (ClassCastException classCastException) {
/* 75 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*    */     } 
/*    */ 
/*    */     
/* 79 */     OGLContext.validateContext(oGLSurfaceData, oGLSurfaceData, paramSunGraphics2D
/* 80 */         .getCompClip(), paramComposite, null, paramSunGraphics2D.paint, paramSunGraphics2D, paramInt);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/opengl/OGLMaskFill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */