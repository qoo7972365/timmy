/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.PixelInterleavedSampleModel;
/*     */ import java.awt.image.SampleModel;
/*     */ import java.awt.image.SinglePixelPackedSampleModel;
/*     */ import java.awt.image.WritableRaster;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WritableRasterNative
/*     */   extends WritableRaster
/*     */ {
/*     */   public static WritableRasterNative createNativeRaster(SampleModel paramSampleModel, DataBuffer paramDataBuffer) {
/*  58 */     return new WritableRasterNative(paramSampleModel, paramDataBuffer);
/*     */   }
/*     */   
/*     */   protected WritableRasterNative(SampleModel paramSampleModel, DataBuffer paramDataBuffer) {
/*  62 */     super(paramSampleModel, paramDataBuffer, new Point(0, 0));
/*     */   }
/*     */   public static WritableRasterNative createNativeRaster(ColorModel paramColorModel, SurfaceData paramSurfaceData, int paramInt1, int paramInt2) {
/*     */     SinglePixelPackedSampleModel singlePixelPackedSampleModel;
/*     */     int[] arrayOfInt1;
/*     */     DataBufferNative dataBufferNative;
/*     */     int[] arrayOfInt2;
/*     */     DirectColorModel directColorModel;
/*  70 */     PixelInterleavedSampleModel pixelInterleavedSampleModel = null;
/*  71 */     byte b = 0;
/*  72 */     int i = paramInt1;
/*     */     
/*  74 */     switch (paramColorModel.getPixelSize()) {
/*     */       
/*     */       case 8:
/*     */       case 12:
/*  78 */         if (paramColorModel.getPixelSize() == 8) {
/*  79 */           b = 0;
/*     */         } else {
/*  81 */           b = 1;
/*     */         } 
/*  83 */         arrayOfInt1 = new int[1];
/*  84 */         arrayOfInt1[0] = 0;
/*  85 */         pixelInterleavedSampleModel = new PixelInterleavedSampleModel(b, paramInt1, paramInt2, 1, i, arrayOfInt1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 121 */         dataBufferNative = new DataBufferNative(paramSurfaceData, b, paramInt1, paramInt2);
/*     */         
/* 123 */         return new WritableRasterNative(pixelInterleavedSampleModel, dataBufferNative);case 15: case 16: b = 1; arrayOfInt2 = new int[3]; directColorModel = (DirectColorModel)paramColorModel; arrayOfInt2[0] = directColorModel.getRedMask(); arrayOfInt2[1] = directColorModel.getGreenMask(); arrayOfInt2[2] = directColorModel.getBlueMask(); singlePixelPackedSampleModel = new SinglePixelPackedSampleModel(b, paramInt1, paramInt2, i, arrayOfInt2); dataBufferNative = new DataBufferNative(paramSurfaceData, b, paramInt1, paramInt2); return new WritableRasterNative(singlePixelPackedSampleModel, dataBufferNative);case 24: case 32: b = 3; arrayOfInt2 = new int[3]; directColorModel = (DirectColorModel)paramColorModel; arrayOfInt2[0] = directColorModel.getRedMask(); arrayOfInt2[1] = directColorModel.getGreenMask(); arrayOfInt2[2] = directColorModel.getBlueMask(); singlePixelPackedSampleModel = new SinglePixelPackedSampleModel(b, paramInt1, paramInt2, i, arrayOfInt2); dataBufferNative = new DataBufferNative(paramSurfaceData, b, paramInt1, paramInt2); return new WritableRasterNative(singlePixelPackedSampleModel, dataBufferNative);
/*     */     } 
/*     */     throw new InternalError("Unsupported depth " + paramColorModel.getPixelSize());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/WritableRasterNative.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */