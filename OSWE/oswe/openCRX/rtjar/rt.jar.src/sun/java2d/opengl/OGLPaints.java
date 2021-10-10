/*     */ package sun.java2d.opengl;
/*     */ 
/*     */ import java.awt.LinearGradientPaint;
/*     */ import java.awt.MultipleGradientPaint;
/*     */ import java.awt.TexturePaint;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.CompositeType;
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
/*     */ abstract class OGLPaints
/*     */ {
/*  50 */   private static Map<Integer, OGLPaints> impls = new HashMap<>(4, 1.0F);
/*     */ 
/*     */   
/*     */   static {
/*  54 */     impls.put(Integer.valueOf(2), new Gradient());
/*  55 */     impls.put(Integer.valueOf(3), new LinearGradient());
/*  56 */     impls.put(Integer.valueOf(4), new RadialGradient());
/*  57 */     impls.put(Integer.valueOf(5), new Texture());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract boolean isPaintValid(SunGraphics2D paramSunGraphics2D);
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isValid(SunGraphics2D paramSunGraphics2D) {
/*  68 */     OGLPaints oGLPaints = impls.get(Integer.valueOf(paramSunGraphics2D.paintState));
/*  69 */     return (oGLPaints != null && oGLPaints.isPaintValid(paramSunGraphics2D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Gradient
/*     */     extends OGLPaints
/*     */   {
/*     */     private Gradient() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isPaintValid(SunGraphics2D param1SunGraphics2D) {
/*  90 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class Texture
/*     */     extends OGLPaints
/*     */   {
/*     */     private Texture() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isPaintValid(SunGraphics2D param1SunGraphics2D) {
/* 110 */       TexturePaint texturePaint = (TexturePaint)param1SunGraphics2D.paint;
/* 111 */       OGLSurfaceData oGLSurfaceData1 = (OGLSurfaceData)param1SunGraphics2D.surfaceData;
/* 112 */       BufferedImage bufferedImage = texturePaint.getImage();
/*     */ 
/*     */       
/* 115 */       if (!oGLSurfaceData1.isTexNonPow2Available()) {
/* 116 */         int i = bufferedImage.getWidth();
/* 117 */         int j = bufferedImage.getHeight();
/*     */ 
/*     */         
/* 120 */         if ((i & i - 1) != 0 || (j & j - 1) != 0) {
/* 121 */           return false;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 126 */       SurfaceData surfaceData = oGLSurfaceData1.getSourceSurfaceData(bufferedImage, 0, CompositeType.SrcOver, null);
/*     */ 
/*     */       
/* 129 */       if (!(surfaceData instanceof OGLSurfaceData)) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 134 */         surfaceData = oGLSurfaceData1.getSourceSurfaceData(bufferedImage, 0, CompositeType.SrcOver, null);
/*     */ 
/*     */         
/* 137 */         if (!(surfaceData instanceof OGLSurfaceData)) {
/* 138 */           return false;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 143 */       OGLSurfaceData oGLSurfaceData2 = (OGLSurfaceData)surfaceData;
/* 144 */       if (oGLSurfaceData2.getType() != 3) {
/* 145 */         return false;
/*     */       }
/*     */       
/* 148 */       return true;
/*     */     }
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
/*     */   
/*     */   private static abstract class MultiGradient
/*     */     extends OGLPaints
/*     */   {
/*     */     boolean isPaintValid(SunGraphics2D param1SunGraphics2D) {
/* 167 */       MultipleGradientPaint multipleGradientPaint = (MultipleGradientPaint)param1SunGraphics2D.paint;
/*     */ 
/*     */       
/* 170 */       if ((multipleGradientPaint.getFractions()).length > 12) {
/* 171 */         return false;
/*     */       }
/*     */       
/* 174 */       OGLSurfaceData oGLSurfaceData = (OGLSurfaceData)param1SunGraphics2D.surfaceData;
/* 175 */       OGLGraphicsConfig oGLGraphicsConfig = oGLSurfaceData.getOGLGraphicsConfig();
/* 176 */       if (!oGLGraphicsConfig.isCapPresent(524288)) {
/* 177 */         return false;
/*     */       }
/*     */       
/* 180 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LinearGradient
/*     */     extends MultiGradient
/*     */   {
/*     */     private LinearGradient() {}
/*     */     
/*     */     boolean isPaintValid(SunGraphics2D param1SunGraphics2D) {
/* 191 */       LinearGradientPaint linearGradientPaint = (LinearGradientPaint)param1SunGraphics2D.paint;
/*     */       
/* 193 */       if ((linearGradientPaint.getFractions()).length == 2 && linearGradientPaint
/* 194 */         .getCycleMethod() != MultipleGradientPaint.CycleMethod.REPEAT && linearGradientPaint
/* 195 */         .getColorSpace() != MultipleGradientPaint.ColorSpaceType.LINEAR_RGB)
/*     */       {
/*     */ 
/*     */         
/* 199 */         return true;
/*     */       }
/*     */       
/* 202 */       return super.isPaintValid(param1SunGraphics2D);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class RadialGradient extends MultiGradient {
/*     */     private RadialGradient() {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/opengl/OGLPaints.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */