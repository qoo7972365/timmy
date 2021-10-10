/*    */ package sun.java2d.opengl;
/*    */ 
/*    */ import java.awt.Composite;
/*    */ import sun.font.GlyphList;
/*    */ import sun.java2d.SunGraphics2D;
/*    */ import sun.java2d.loops.GraphicsPrimitive;
/*    */ import sun.java2d.pipe.BufferedTextPipe;
/*    */ import sun.java2d.pipe.RenderQueue;
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
/*    */ class OGLTextRenderer
/*    */   extends BufferedTextPipe
/*    */ {
/*    */   OGLTextRenderer(RenderQueue paramRenderQueue) {
/* 38 */     super(paramRenderQueue);
/*    */   }
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
/*    */   protected void validateContext(SunGraphics2D paramSunGraphics2D, Composite paramComposite) {
/* 51 */     OGLSurfaceData oGLSurfaceData = (OGLSurfaceData)paramSunGraphics2D.surfaceData;
/* 52 */     OGLContext.validateContext(oGLSurfaceData, oGLSurfaceData, paramSunGraphics2D
/* 53 */         .getCompClip(), paramComposite, null, paramSunGraphics2D.paint, paramSunGraphics2D, 0);
/*    */   }
/*    */   
/*    */   protected native void drawGlyphList(int paramInt1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt2, float paramFloat1, float paramFloat2, long[] paramArrayOflong, float[] paramArrayOffloat);
/*    */   
/*    */   OGLTextRenderer traceWrap() {
/* 59 */     return new Tracer(this);
/*    */   }
/*    */   
/*    */   private static class Tracer extends OGLTextRenderer {
/*    */     Tracer(OGLTextRenderer param1OGLTextRenderer) {
/* 64 */       super(param1OGLTextRenderer.rq);
/*    */     }
/*    */     protected void drawGlyphList(SunGraphics2D param1SunGraphics2D, GlyphList param1GlyphList) {
/* 67 */       GraphicsPrimitive.tracePrimitive("OGLDrawGlyphs");
/* 68 */       super.drawGlyphList(param1SunGraphics2D, param1GlyphList);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/opengl/OGLTextRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */