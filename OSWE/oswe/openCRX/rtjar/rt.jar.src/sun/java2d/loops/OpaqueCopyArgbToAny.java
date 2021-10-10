/*     */ package sun.java2d.loops;
/*     */ 
/*     */ import java.awt.Composite;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import sun.awt.image.IntegerComponentRaster;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.pipe.Region;
/*     */ import sun.java2d.pipe.SpanIterator;
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
/*     */ class OpaqueCopyArgbToAny
/*     */   extends Blit
/*     */ {
/*     */   OpaqueCopyArgbToAny() {
/* 166 */     super(SurfaceType.IntArgb, CompositeType.SrcNoEa, SurfaceType.Any);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Blit(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Composite paramComposite, Region paramRegion, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 175 */     Raster raster = paramSurfaceData1.getRaster(paramInt1, paramInt2, paramInt5, paramInt6);
/* 176 */     IntegerComponentRaster integerComponentRaster = (IntegerComponentRaster)raster;
/* 177 */     int[] arrayOfInt1 = integerComponentRaster.getDataStorage();
/*     */ 
/*     */     
/* 180 */     WritableRaster writableRaster = (WritableRaster)paramSurfaceData2.getRaster(paramInt3, paramInt4, paramInt5, paramInt6);
/* 181 */     ColorModel colorModel = paramSurfaceData2.getColorModel();
/*     */     
/* 183 */     Region region = CustomComponent.getRegionOfInterest(paramSurfaceData1, paramSurfaceData2, paramRegion, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */ 
/*     */     
/* 186 */     SpanIterator spanIterator = region.getSpanIterator();
/*     */     
/* 188 */     Object object = null;
/*     */     
/* 190 */     int i = integerComponentRaster.getScanlineStride();
/*     */     
/* 192 */     paramInt1 -= paramInt3;
/* 193 */     paramInt2 -= paramInt4;
/* 194 */     int[] arrayOfInt2 = new int[4];
/* 195 */     while (spanIterator.nextSpan(arrayOfInt2)) {
/* 196 */       int j = integerComponentRaster.getDataOffset(0) + (paramInt2 + arrayOfInt2[1]) * i + paramInt1 + arrayOfInt2[0];
/*     */ 
/*     */       
/* 199 */       for (int k = arrayOfInt2[1]; k < arrayOfInt2[3]; k++) {
/* 200 */         int m = j;
/* 201 */         for (int n = arrayOfInt2[0]; n < arrayOfInt2[2]; n++) {
/* 202 */           object = colorModel.getDataElements(arrayOfInt1[m++], object);
/* 203 */           writableRaster.setDataElements(n, k, object);
/*     */         } 
/* 205 */         j += i;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/OpaqueCopyArgbToAny.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */