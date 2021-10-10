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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class XorCopyArgbToAny
/*     */   extends Blit
/*     */ {
/*     */   XorCopyArgbToAny() {
/* 221 */     super(SurfaceType.IntArgb, CompositeType.Xor, SurfaceType.Any);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Blit(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Composite paramComposite, Region paramRegion, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 230 */     Raster raster = paramSurfaceData1.getRaster(paramInt1, paramInt2, paramInt5, paramInt6);
/* 231 */     IntegerComponentRaster integerComponentRaster = (IntegerComponentRaster)raster;
/* 232 */     int[] arrayOfInt1 = integerComponentRaster.getDataStorage();
/*     */ 
/*     */     
/* 235 */     WritableRaster writableRaster = (WritableRaster)paramSurfaceData2.getRaster(paramInt3, paramInt4, paramInt5, paramInt6);
/* 236 */     ColorModel colorModel = paramSurfaceData2.getColorModel();
/*     */     
/* 238 */     Region region = CustomComponent.getRegionOfInterest(paramSurfaceData1, paramSurfaceData2, paramRegion, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */ 
/*     */     
/* 241 */     SpanIterator spanIterator = region.getSpanIterator();
/*     */     
/* 243 */     int i = ((XORComposite)paramComposite).getXorColor().getRGB();
/* 244 */     Object object1 = colorModel.getDataElements(i, null);
/*     */     
/* 246 */     Object object2 = null;
/* 247 */     Object object3 = null;
/*     */     
/* 249 */     int j = integerComponentRaster.getScanlineStride();
/*     */     
/* 251 */     paramInt1 -= paramInt3;
/* 252 */     paramInt2 -= paramInt4;
/* 253 */     int[] arrayOfInt2 = new int[4];
/* 254 */     while (spanIterator.nextSpan(arrayOfInt2)) {
/* 255 */       int k = integerComponentRaster.getDataOffset(0) + (paramInt2 + arrayOfInt2[1]) * j + paramInt1 + arrayOfInt2[0];
/*     */ 
/*     */       
/* 258 */       for (int m = arrayOfInt2[1]; m < arrayOfInt2[3]; m++) {
/* 259 */         int n = k;
/* 260 */         for (int i1 = arrayOfInt2[0]; i1 < arrayOfInt2[2]; i1++) {
/*     */           byte[] arrayOfByte1, arrayOfByte2, arrayOfByte3; byte b1; short[] arrayOfShort1, arrayOfShort2, arrayOfShort3; byte b2; int[] arrayOfInt3, arrayOfInt4, arrayOfInt5; byte b3; float[] arrayOfFloat1, arrayOfFloat2, arrayOfFloat3; byte b4;
/*     */           double[] arrayOfDouble1, arrayOfDouble2, arrayOfDouble3;
/*     */           byte b5;
/* 264 */           object2 = colorModel.getDataElements(arrayOfInt1[n++], object2);
/* 265 */           object3 = writableRaster.getDataElements(i1, m, object3);
/*     */           
/* 267 */           switch (colorModel.getTransferType()) {
/*     */             case 0:
/* 269 */               arrayOfByte1 = (byte[])object2;
/* 270 */               arrayOfByte2 = (byte[])object3;
/* 271 */               arrayOfByte3 = (byte[])object1;
/* 272 */               for (b1 = 0; b1 < arrayOfByte2.length; b1++) {
/* 273 */                 arrayOfByte2[b1] = (byte)(arrayOfByte2[b1] ^ arrayOfByte1[b1] ^ arrayOfByte3[b1]);
/*     */               }
/*     */               break;
/*     */             case 1:
/*     */             case 2:
/* 278 */               arrayOfShort1 = (short[])object2;
/* 279 */               arrayOfShort2 = (short[])object3;
/* 280 */               arrayOfShort3 = (short[])object1;
/* 281 */               for (b2 = 0; b2 < arrayOfShort2.length; b2++) {
/* 282 */                 arrayOfShort2[b2] = (short)(arrayOfShort2[b2] ^ arrayOfShort1[b2] ^ arrayOfShort3[b2]);
/*     */               }
/*     */               break;
/*     */             case 3:
/* 286 */               arrayOfInt3 = (int[])object2;
/* 287 */               arrayOfInt4 = (int[])object3;
/* 288 */               arrayOfInt5 = (int[])object1;
/* 289 */               for (b3 = 0; b3 < arrayOfInt4.length; b3++) {
/* 290 */                 arrayOfInt4[b3] = arrayOfInt4[b3] ^ arrayOfInt3[b3] ^ arrayOfInt5[b3];
/*     */               }
/*     */               break;
/*     */             case 4:
/* 294 */               arrayOfFloat1 = (float[])object2;
/* 295 */               arrayOfFloat2 = (float[])object3;
/* 296 */               arrayOfFloat3 = (float[])object1;
/* 297 */               for (b4 = 0; b4 < arrayOfFloat2.length; b4++) {
/*     */ 
/*     */                 
/* 300 */                 int i2 = Float.floatToIntBits(arrayOfFloat2[b4]) ^ Float.floatToIntBits(arrayOfFloat1[b4]) ^ Float.floatToIntBits(arrayOfFloat3[b4]);
/* 301 */                 arrayOfFloat2[b4] = Float.intBitsToFloat(i2);
/*     */               } 
/*     */               break;
/*     */             case 5:
/* 305 */               arrayOfDouble1 = (double[])object2;
/* 306 */               arrayOfDouble2 = (double[])object3;
/* 307 */               arrayOfDouble3 = (double[])object1;
/* 308 */               for (b5 = 0; b5 < arrayOfDouble2.length; b5++) {
/*     */ 
/*     */                 
/* 311 */                 long l = Double.doubleToLongBits(arrayOfDouble2[b5]) ^ Double.doubleToLongBits(arrayOfDouble1[b5]) ^ Double.doubleToLongBits(arrayOfDouble3[b5]);
/* 312 */                 arrayOfDouble2[b5] = Double.longBitsToDouble(l);
/*     */               } 
/*     */               break;
/*     */             default:
/* 316 */               throw new InternalError("Unsupported XOR pixel type");
/*     */           } 
/* 318 */           writableRaster.setDataElements(i1, m, object3);
/*     */         } 
/* 320 */         k += j;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/XorCopyArgbToAny.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */