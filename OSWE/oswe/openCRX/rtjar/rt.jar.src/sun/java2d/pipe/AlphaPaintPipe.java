/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import java.awt.PaintContext;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.lang.ref.WeakReference;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AlphaPaintPipe
/*     */   implements CompositePipe
/*     */ {
/*     */   static WeakReference cachedLastRaster;
/*     */   static WeakReference cachedLastColorModel;
/*     */   static WeakReference cachedLastData;
/*     */   private static final int TILE_SIZE = 32;
/*     */   
/*     */   static class TileContext
/*     */   {
/*     */     SunGraphics2D sunG2D;
/*     */     PaintContext paintCtxt;
/*     */     ColorModel paintModel;
/*     */     WeakReference lastRaster;
/*     */     WeakReference lastData;
/*     */     MaskBlit lastMask;
/*     */     Blit lastBlit;
/*     */     SurfaceData dstData;
/*     */     
/*     */     public TileContext(SunGraphics2D param1SunGraphics2D, PaintContext param1PaintContext) {
/*  66 */       this.sunG2D = param1SunGraphics2D;
/*  67 */       this.paintCtxt = param1PaintContext;
/*  68 */       this.paintModel = param1PaintContext.getColorModel();
/*  69 */       this.dstData = param1SunGraphics2D.getSurfaceData();
/*  70 */       synchronized (AlphaPaintPipe.class) {
/*  71 */         if (AlphaPaintPipe.cachedLastColorModel != null && AlphaPaintPipe.cachedLastColorModel
/*  72 */           .get() == this.paintModel) {
/*     */           
/*  74 */           this.lastRaster = AlphaPaintPipe.cachedLastRaster;
/*  75 */           this.lastData = AlphaPaintPipe.cachedLastData;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object startSequence(SunGraphics2D paramSunGraphics2D, Shape paramShape, Rectangle paramRectangle, int[] paramArrayOfint) {
/*  84 */     PaintContext paintContext = paramSunGraphics2D.paint.createContext(paramSunGraphics2D.getDeviceColorModel(), paramRectangle, paramShape
/*     */         
/*  86 */         .getBounds2D(), paramSunGraphics2D
/*  87 */         .cloneTransform(), paramSunGraphics2D
/*  88 */         .getRenderingHints());
/*  89 */     return new TileContext(paramSunGraphics2D, paintContext);
/*     */   }
/*     */   
/*     */   public boolean needTile(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderPathTile(Object paramObject, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 101 */     TileContext tileContext = (TileContext)paramObject;
/* 102 */     PaintContext paintContext = tileContext.paintCtxt;
/* 103 */     SunGraphics2D sunGraphics2D = tileContext.sunG2D;
/* 104 */     SurfaceData surfaceData1 = tileContext.dstData;
/* 105 */     SurfaceData surfaceData2 = null;
/* 106 */     Raster raster = null;
/* 107 */     if (tileContext.lastData != null && tileContext.lastRaster != null) {
/* 108 */       surfaceData2 = tileContext.lastData.get();
/* 109 */       raster = tileContext.lastRaster.get();
/* 110 */       if (surfaceData2 == null || raster == null) {
/* 111 */         surfaceData2 = null;
/* 112 */         raster = null;
/*     */       } 
/*     */     } 
/* 115 */     ColorModel colorModel = tileContext.paintModel;
/*     */     
/* 117 */     for (byte b = 0; b < paramInt6; b += 32) {
/* 118 */       int i = paramInt4 + b;
/* 119 */       int j = Math.min(paramInt6 - b, 32);
/* 120 */       for (byte b1 = 0; b1 < paramInt5; b1 += 32) {
/* 121 */         int k = paramInt3 + b1;
/* 122 */         int m = Math.min(paramInt5 - b1, 32);
/*     */         
/* 124 */         Raster raster1 = paintContext.getRaster(k, i, m, j);
/* 125 */         if (raster1.getMinX() != 0 || raster1.getMinY() != 0) {
/* 126 */           raster1 = raster1.createTranslatedChild(0, 0);
/*     */         }
/* 128 */         if (raster != raster1) {
/* 129 */           raster = raster1;
/* 130 */           tileContext.lastRaster = new WeakReference<>(raster);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 135 */           BufferedImage bufferedImage = new BufferedImage(colorModel, (WritableRaster)raster1, colorModel.isAlphaPremultiplied(), null);
/*     */           
/* 137 */           surfaceData2 = BufImgSurfaceData.createData(bufferedImage);
/* 138 */           tileContext.lastData = new WeakReference<>(surfaceData2);
/* 139 */           tileContext.lastMask = null;
/* 140 */           tileContext.lastBlit = null;
/*     */         } 
/*     */         
/* 143 */         if (paramArrayOfbyte == null) {
/* 144 */           if (tileContext.lastBlit == null) {
/* 145 */             CompositeType compositeType = sunGraphics2D.imageComp;
/* 146 */             if (CompositeType.SrcOverNoEa.equals(compositeType) && colorModel
/* 147 */               .getTransparency() == 1)
/*     */             {
/* 149 */               compositeType = CompositeType.SrcNoEa;
/*     */             }
/* 151 */             tileContext
/* 152 */               .lastBlit = Blit.getFromCache(surfaceData2.getSurfaceType(), compositeType, surfaceData1
/*     */                 
/* 154 */                 .getSurfaceType());
/*     */           } 
/* 156 */           tileContext.lastBlit.Blit(surfaceData2, surfaceData1, sunGraphics2D.composite, null, 0, 0, k, i, m, j);
/*     */         }
/*     */         else {
/*     */           
/* 160 */           if (tileContext.lastMask == null) {
/* 161 */             CompositeType compositeType = sunGraphics2D.imageComp;
/* 162 */             if (CompositeType.SrcOverNoEa.equals(compositeType) && colorModel
/* 163 */               .getTransparency() == 1)
/*     */             {
/* 165 */               compositeType = CompositeType.SrcNoEa;
/*     */             }
/* 167 */             tileContext
/* 168 */               .lastMask = MaskBlit.getFromCache(surfaceData2.getSurfaceType(), compositeType, surfaceData1
/*     */                 
/* 170 */                 .getSurfaceType());
/*     */           } 
/*     */           
/* 173 */           int n = paramInt1 + b * paramInt2 + b1;
/* 174 */           tileContext.lastMask.MaskBlit(surfaceData2, surfaceData1, sunGraphics2D.composite, null, 0, 0, k, i, m, j, paramArrayOfbyte, n, paramInt2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skipTile(Object paramObject, int paramInt1, int paramInt2) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void endSequence(Object paramObject) {
/* 188 */     TileContext tileContext = (TileContext)paramObject;
/* 189 */     if (tileContext.paintCtxt != null) {
/* 190 */       tileContext.paintCtxt.dispose();
/*     */     }
/* 192 */     synchronized (AlphaPaintPipe.class) {
/* 193 */       if (tileContext.lastData != null) {
/* 194 */         cachedLastRaster = tileContext.lastRaster;
/* 195 */         if (cachedLastColorModel == null || cachedLastColorModel
/* 196 */           .get() != tileContext.paintModel)
/*     */         {
/*     */           
/* 199 */           cachedLastColorModel = new WeakReference<>(tileContext.paintModel);
/*     */         }
/*     */         
/* 202 */         cachedLastData = tileContext.lastData;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/AlphaPaintPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */