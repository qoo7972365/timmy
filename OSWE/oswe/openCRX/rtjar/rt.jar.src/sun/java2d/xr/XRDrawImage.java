/*    */ package sun.java2d.xr;
/*    */ 
/*    */ import java.awt.AlphaComposite;
/*    */ import java.awt.Color;
/*    */ import java.awt.Image;
/*    */ import java.awt.geom.AffineTransform;
/*    */ import sun.java2d.SunGraphics2D;
/*    */ import sun.java2d.SurfaceData;
/*    */ import sun.java2d.loops.SurfaceType;
/*    */ import sun.java2d.loops.TransformBlit;
/*    */ import sun.java2d.pipe.DrawImage;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XRDrawImage
/*    */   extends DrawImage
/*    */ {
/*    */   protected void renderImageXform(SunGraphics2D paramSunGraphics2D, Image paramImage, AffineTransform paramAffineTransform, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Color paramColor) {
/* 46 */     SurfaceData surfaceData1 = paramSunGraphics2D.surfaceData;
/* 47 */     SurfaceData surfaceData2 = surfaceData1.getSourceSurfaceData(paramImage, 4, paramSunGraphics2D.imageComp, paramColor);
/*    */ 
/*    */     
/* 50 */     if (paramSunGraphics2D.composite instanceof AlphaComposite) {
/* 51 */       int i = ((AlphaComposite)paramSunGraphics2D.composite).getRule();
/* 52 */       float f = ((AlphaComposite)paramSunGraphics2D.composite).getAlpha();
/*    */       
/* 54 */       if (surfaceData2 != null && !isBgOperation(surfaceData2, paramColor) && paramInt1 <= 2 && (
/*    */         
/* 56 */         XRUtils.isMaskEvaluated(XRUtils.j2dAlphaCompToXR(i)) || (
/* 57 */         XRUtils.isTransformQuadrantRotated(paramAffineTransform) && f == 1.0F))) {
/*    */ 
/*    */         
/* 60 */         SurfaceType surfaceType1 = surfaceData2.getSurfaceType();
/* 61 */         SurfaceType surfaceType2 = surfaceData1.getSurfaceType();
/*    */         
/* 63 */         TransformBlit transformBlit = TransformBlit.getFromCache(surfaceType1, paramSunGraphics2D.imageComp, surfaceType2);
/*    */         
/* 65 */         if (transformBlit != null) {
/* 66 */           transformBlit.Transform(surfaceData2, surfaceData1, paramSunGraphics2D.composite, paramSunGraphics2D
/* 67 */               .getCompClip(), paramAffineTransform, paramInt1, paramInt2, paramInt3, 0, 0, paramInt4 - paramInt2, paramInt5 - paramInt3);
/*    */           
/*    */           return;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 74 */     super.renderImageXform(paramSunGraphics2D, paramImage, paramAffineTransform, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramColor);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRDrawImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */