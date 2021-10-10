/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.CompositeContext;
/*     */ import java.awt.PaintContext;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Shape;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import sun.awt.image.BufImgSurfaceData;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.Blit;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.MaskBlit;
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
/*     */ public class GeneralCompositePipe
/*     */   implements CompositePipe
/*     */ {
/*     */   class TileContext
/*     */   {
/*     */     SunGraphics2D sunG2D;
/*     */     PaintContext paintCtxt;
/*     */     CompositeContext compCtxt;
/*     */     ColorModel compModel;
/*     */     Object pipeState;
/*     */     
/*     */     public TileContext(SunGraphics2D param1SunGraphics2D, PaintContext param1PaintContext, CompositeContext param1CompositeContext, ColorModel param1ColorModel) {
/*  55 */       this.sunG2D = param1SunGraphics2D;
/*  56 */       this.paintCtxt = param1PaintContext;
/*  57 */       this.compCtxt = param1CompositeContext;
/*  58 */       this.compModel = param1ColorModel;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Object startSequence(SunGraphics2D paramSunGraphics2D, Shape paramShape, Rectangle paramRectangle, int[] paramArrayOfint) {
/*  64 */     RenderingHints renderingHints = paramSunGraphics2D.getRenderingHints();
/*  65 */     ColorModel colorModel = paramSunGraphics2D.getDeviceColorModel();
/*     */     
/*  67 */     PaintContext paintContext = paramSunGraphics2D.paint.createContext(colorModel, paramRectangle, paramShape.getBounds2D(), paramSunGraphics2D
/*  68 */         .cloneTransform(), renderingHints);
/*     */ 
/*     */     
/*  71 */     CompositeContext compositeContext = paramSunGraphics2D.composite.createContext(paintContext.getColorModel(), colorModel, renderingHints);
/*     */     
/*  73 */     return new TileContext(paramSunGraphics2D, paintContext, compositeContext, colorModel);
/*     */   }
/*     */   
/*     */   public boolean needTile(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  77 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderPathTile(Object paramObject, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*     */     Raster raster3;
/*     */     WritableRaster writableRaster;
/*  87 */     TileContext tileContext = (TileContext)paramObject;
/*  88 */     PaintContext paintContext = tileContext.paintCtxt;
/*  89 */     CompositeContext compositeContext = tileContext.compCtxt;
/*  90 */     SunGraphics2D sunGraphics2D = tileContext.sunG2D;
/*     */     
/*  92 */     Raster raster1 = paintContext.getRaster(paramInt3, paramInt4, paramInt5, paramInt6);
/*  93 */     ColorModel colorModel = paintContext.getColorModel();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     SurfaceData surfaceData = sunGraphics2D.getSurfaceData();
/* 100 */     Raster raster2 = surfaceData.getRaster(paramInt3, paramInt4, paramInt5, paramInt6);
/* 101 */     if (raster2 instanceof WritableRaster && paramArrayOfbyte == null) {
/* 102 */       writableRaster = (WritableRaster)raster2;
/* 103 */       writableRaster = writableRaster.createWritableChild(paramInt3, paramInt4, paramInt5, paramInt6, 0, 0, (int[])null);
/* 104 */       raster3 = writableRaster;
/*     */     } else {
/* 106 */       raster3 = raster2.createChild(paramInt3, paramInt4, paramInt5, paramInt6, 0, 0, null);
/* 107 */       writableRaster = raster3.createCompatibleWritableRaster();
/*     */     } 
/*     */     
/* 110 */     compositeContext.compose(raster1, raster3, writableRaster);
/*     */     
/* 112 */     if (raster2 != writableRaster && writableRaster.getParent() != raster2) {
/* 113 */       if (raster2 instanceof WritableRaster && paramArrayOfbyte == null) {
/* 114 */         ((WritableRaster)raster2).setDataElements(paramInt3, paramInt4, writableRaster);
/*     */       } else {
/* 116 */         ColorModel colorModel1 = sunGraphics2D.getDeviceColorModel();
/*     */ 
/*     */         
/* 119 */         BufferedImage bufferedImage = new BufferedImage(colorModel1, writableRaster, colorModel1.isAlphaPremultiplied(), null);
/*     */         
/* 121 */         SurfaceData surfaceData1 = BufImgSurfaceData.createData(bufferedImage);
/* 122 */         if (paramArrayOfbyte == null) {
/* 123 */           Blit blit = Blit.getFromCache(surfaceData1.getSurfaceType(), CompositeType.SrcNoEa, surfaceData
/*     */               
/* 125 */               .getSurfaceType());
/* 126 */           blit.Blit(surfaceData1, surfaceData, AlphaComposite.Src, null, 0, 0, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */         } else {
/*     */           
/* 129 */           MaskBlit maskBlit = MaskBlit.getFromCache(surfaceData1.getSurfaceType(), CompositeType.SrcNoEa, surfaceData
/*     */               
/* 131 */               .getSurfaceType());
/* 132 */           maskBlit.MaskBlit(surfaceData1, surfaceData, AlphaComposite.Src, null, 0, 0, paramInt3, paramInt4, paramInt5, paramInt6, paramArrayOfbyte, paramInt1, paramInt2);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void skipTile(Object paramObject, int paramInt1, int paramInt2) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void endSequence(Object paramObject) {
/* 145 */     TileContext tileContext = (TileContext)paramObject;
/* 146 */     if (tileContext.paintCtxt != null) {
/* 147 */       tileContext.paintCtxt.dispose();
/*     */     }
/* 149 */     if (tileContext.compCtxt != null)
/* 150 */       tileContext.compCtxt.dispose(); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/GeneralCompositePipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */