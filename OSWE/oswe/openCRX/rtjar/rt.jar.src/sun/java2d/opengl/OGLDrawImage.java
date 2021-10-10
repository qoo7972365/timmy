/*     */ package sun.java2d.opengl;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Image;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.AffineTransformOp;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.BufferedImageOp;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.SurfaceType;
/*     */ import sun.java2d.loops.TransformBlit;
/*     */ import sun.java2d.pipe.DrawImage;
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
/*     */ 
/*     */ 
/*     */ public class OGLDrawImage
/*     */   extends DrawImage
/*     */ {
/*     */   protected void renderImageXform(SunGraphics2D paramSunGraphics2D, Image paramImage, AffineTransform paramAffineTransform, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Color paramColor) {
/*  59 */     if (paramInt1 != 3) {
/*  60 */       SurfaceData surfaceData1 = paramSunGraphics2D.surfaceData;
/*     */       
/*  62 */       SurfaceData surfaceData2 = surfaceData1.getSourceSurfaceData(paramImage, 4, paramSunGraphics2D.imageComp, paramColor);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  67 */       if (surfaceData2 != null && 
/*  68 */         !isBgOperation(surfaceData2, paramColor) && (surfaceData2
/*  69 */         .getSurfaceType() == OGLSurfaceData.OpenGLTexture || surfaceData2
/*  70 */         .getSurfaceType() == OGLSurfaceData.OpenGLSurfaceRTT || paramInt1 == 1)) {
/*     */ 
/*     */         
/*  73 */         SurfaceType surfaceType1 = surfaceData2.getSurfaceType();
/*  74 */         SurfaceType surfaceType2 = surfaceData1.getSurfaceType();
/*  75 */         TransformBlit transformBlit = TransformBlit.getFromCache(surfaceType1, paramSunGraphics2D.imageComp, surfaceType2);
/*     */ 
/*     */ 
/*     */         
/*  79 */         if (transformBlit != null) {
/*  80 */           transformBlit.Transform(surfaceData2, surfaceData1, paramSunGraphics2D.composite, paramSunGraphics2D
/*  81 */               .getCompClip(), paramAffineTransform, paramInt1, paramInt2, paramInt3, 0, 0, paramInt4 - paramInt2, paramInt5 - paramInt3);
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     super.renderImageXform(paramSunGraphics2D, paramImage, paramAffineTransform, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transformImage(SunGraphics2D paramSunGraphics2D, BufferedImage paramBufferedImage, BufferedImageOp paramBufferedImageOp, int paramInt1, int paramInt2) {
/*  97 */     if (paramBufferedImageOp != null) {
/*  98 */       if (paramBufferedImageOp instanceof AffineTransformOp) {
/*  99 */         AffineTransformOp affineTransformOp = (AffineTransformOp)paramBufferedImageOp;
/* 100 */         transformImage(paramSunGraphics2D, paramBufferedImage, paramInt1, paramInt2, affineTransformOp
/* 101 */             .getTransform(), affineTransformOp
/* 102 */             .getInterpolationType());
/*     */         return;
/*     */       } 
/* 105 */       if (OGLBufImgOps.renderImageWithOp(paramSunGraphics2D, paramBufferedImage, paramBufferedImageOp, paramInt1, paramInt2)) {
/*     */         return;
/*     */       }
/*     */       
/* 109 */       paramBufferedImage = paramBufferedImageOp.filter(paramBufferedImage, null);
/*     */     } 
/* 111 */     copyImage(paramSunGraphics2D, paramBufferedImage, paramInt1, paramInt2, null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/opengl/OGLDrawImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */