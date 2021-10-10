/*     */ package com.sun.imageio.plugins.wbmp;
/*     */ 
/*     */ import com.sun.imageio.plugins.common.I18N;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.awt.image.MultiPixelPackedSampleModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.RenderedImage;
/*     */ import java.awt.image.SampleModel;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.IOException;
/*     */ import javax.imageio.IIOImage;
/*     */ import javax.imageio.ImageTypeSpecifier;
/*     */ import javax.imageio.ImageWriteParam;
/*     */ import javax.imageio.ImageWriter;
/*     */ import javax.imageio.metadata.IIOMetadata;
/*     */ import javax.imageio.spi.ImageWriterSpi;
/*     */ import javax.imageio.stream.ImageOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WBMPImageWriter
/*     */   extends ImageWriter
/*     */ {
/*  66 */   private ImageOutputStream stream = null;
/*     */ 
/*     */   
/*     */   private static int getNumBits(int paramInt) {
/*  70 */     byte b = 32;
/*  71 */     int i = Integer.MIN_VALUE;
/*  72 */     while (i != 0 && (paramInt & i) == 0) {
/*  73 */       b--;
/*  74 */       i >>>= 1;
/*     */     } 
/*  76 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] intToMultiByte(int paramInt) {
/*  81 */     int i = getNumBits(paramInt);
/*  82 */     byte[] arrayOfByte = new byte[(i + 6) / 7];
/*     */     
/*  84 */     int j = arrayOfByte.length - 1;
/*  85 */     for (byte b = 0; b <= j; b++) {
/*  86 */       arrayOfByte[b] = (byte)(paramInt >>> (j - b) * 7 & 0x7F);
/*  87 */       if (b != j) {
/*  88 */         arrayOfByte[b] = (byte)(arrayOfByte[b] | Byte.MIN_VALUE);
/*     */       }
/*     */     } 
/*     */     
/*  92 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WBMPImageWriter(ImageWriterSpi paramImageWriterSpi) {
/*  99 */     super(paramImageWriterSpi);
/*     */   }
/*     */   
/*     */   public void setOutput(Object paramObject) {
/* 103 */     super.setOutput(paramObject);
/* 104 */     if (paramObject != null) {
/* 105 */       if (!(paramObject instanceof ImageOutputStream))
/* 106 */         throw new IllegalArgumentException(I18N.getString("WBMPImageWriter")); 
/* 107 */       this.stream = (ImageOutputStream)paramObject;
/*     */     } else {
/* 109 */       this.stream = null;
/*     */     } 
/*     */   }
/*     */   public IIOMetadata getDefaultStreamMetadata(ImageWriteParam paramImageWriteParam) {
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public IIOMetadata getDefaultImageMetadata(ImageTypeSpecifier paramImageTypeSpecifier, ImageWriteParam paramImageWriteParam) {
/* 118 */     WBMPMetadata wBMPMetadata = new WBMPMetadata();
/* 119 */     wBMPMetadata.wbmpType = 0;
/* 120 */     return wBMPMetadata;
/*     */   }
/*     */ 
/*     */   
/*     */   public IIOMetadata convertStreamMetadata(IIOMetadata paramIIOMetadata, ImageWriteParam paramImageWriteParam) {
/* 125 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIOMetadata convertImageMetadata(IIOMetadata paramIIOMetadata, ImageTypeSpecifier paramImageTypeSpecifier, ImageWriteParam paramImageWriteParam) {
/* 131 */     return null;
/*     */   }
/*     */   
/*     */   public boolean canWriteRasters() {
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(IIOMetadata paramIIOMetadata, IIOImage paramIIOImage, ImageWriteParam paramImageWriteParam) throws IOException {
/* 142 */     if (this.stream == null) {
/* 143 */       throw new IllegalStateException(I18N.getString("WBMPImageWriter3"));
/*     */     }
/*     */     
/* 146 */     if (paramIIOImage == null) {
/* 147 */       throw new IllegalArgumentException(I18N.getString("WBMPImageWriter4"));
/*     */     }
/*     */     
/* 150 */     clearAbortRequest();
/* 151 */     processImageStarted(0);
/* 152 */     if (paramImageWriteParam == null) {
/* 153 */       paramImageWriteParam = getDefaultWriteParam();
/*     */     }
/* 155 */     RenderedImage renderedImage = null;
/* 156 */     Raster raster = null;
/* 157 */     boolean bool = paramIIOImage.hasRaster();
/* 158 */     Rectangle rectangle1 = paramImageWriteParam.getSourceRegion();
/* 159 */     SampleModel sampleModel1 = null;
/*     */     
/* 161 */     if (bool) {
/* 162 */       raster = paramIIOImage.getRaster();
/* 163 */       sampleModel1 = raster.getSampleModel();
/*     */     } else {
/* 165 */       renderedImage = paramIIOImage.getRenderedImage();
/* 166 */       sampleModel1 = renderedImage.getSampleModel();
/*     */       
/* 168 */       raster = renderedImage.getData();
/*     */     } 
/*     */     
/* 171 */     checkSampleModel(sampleModel1);
/* 172 */     if (rectangle1 == null) {
/* 173 */       rectangle1 = raster.getBounds();
/*     */     } else {
/* 175 */       rectangle1 = rectangle1.intersection(raster.getBounds());
/*     */     } 
/* 177 */     if (rectangle1.isEmpty()) {
/* 178 */       throw new RuntimeException(I18N.getString("WBMPImageWriter1"));
/*     */     }
/* 180 */     int i = paramImageWriteParam.getSourceXSubsampling();
/* 181 */     int j = paramImageWriteParam.getSourceYSubsampling();
/* 182 */     int k = paramImageWriteParam.getSubsamplingXOffset();
/* 183 */     int m = paramImageWriteParam.getSubsamplingYOffset();
/*     */     
/* 185 */     rectangle1.translate(k, m);
/* 186 */     rectangle1.width -= k;
/* 187 */     rectangle1.height -= m;
/*     */     
/* 189 */     int n = rectangle1.x / i;
/* 190 */     int i1 = rectangle1.y / j;
/* 191 */     int i2 = (rectangle1.width + i - 1) / i;
/* 192 */     int i3 = (rectangle1.height + j - 1) / j;
/*     */     
/* 194 */     Rectangle rectangle2 = new Rectangle(n, i1, i2, i3);
/* 195 */     sampleModel1 = sampleModel1.createCompatibleSampleModel(i2, i3);
/*     */     
/* 197 */     SampleModel sampleModel2 = sampleModel1;
/*     */ 
/*     */     
/* 200 */     if (sampleModel1.getDataType() != 0 || !(sampleModel1 instanceof MultiPixelPackedSampleModel) || ((MultiPixelPackedSampleModel)sampleModel1)
/*     */       
/* 202 */       .getDataBitOffset() != 0) {
/* 203 */       sampleModel2 = new MultiPixelPackedSampleModel(0, i2, i3, 1, i2 + 7 >> 3, 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     if (!rectangle2.equals(rectangle1)) {
/* 210 */       if (i == 1 && j == 1) {
/* 211 */         raster = raster.createChild(raster.getMinX(), raster
/* 212 */             .getMinY(), i2, i3, n, i1, null);
/*     */       } else {
/*     */         
/* 215 */         WritableRaster writableRaster = Raster.createWritableRaster(sampleModel2, new Point(n, i1));
/*     */ 
/*     */         
/* 218 */         byte[] arrayOfByte1 = ((DataBufferByte)writableRaster.getDataBuffer()).getData();
/*     */         
/* 220 */         int i6 = i1, i7 = rectangle1.y, i8 = 0;
/* 221 */         for (; i6 < i1 + i3; i6++, i7 += j) {
/*     */           
/* 223 */           byte b = 0; int i9 = rectangle1.x;
/* 224 */           for (; b < i2; b++, i9 += i) {
/* 225 */             int i10 = raster.getSample(i9, i7, 0);
/* 226 */             arrayOfByte1[i8 + (b >> 3)] = (byte)(arrayOfByte1[i8 + (b >> 3)] | i10 << 7 - (b & 0x7));
/*     */           } 
/* 228 */           i8 += i2 + 7 >> 3;
/*     */         } 
/* 230 */         raster = writableRaster;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 235 */     if (!sampleModel2.equals(raster.getSampleModel())) {
/*     */       
/* 237 */       WritableRaster writableRaster = Raster.createWritableRaster(sampleModel2, new Point(raster
/* 238 */             .getMinX(), raster
/* 239 */             .getMinY()));
/* 240 */       writableRaster.setRect(raster);
/* 241 */       raster = writableRaster;
/*     */     } 
/*     */ 
/*     */     
/* 245 */     boolean bool1 = false;
/* 246 */     if (!bool && renderedImage.getColorModel() instanceof IndexColorModel) {
/* 247 */       IndexColorModel indexColorModel = (IndexColorModel)renderedImage.getColorModel();
/* 248 */       bool1 = (indexColorModel.getRed(0) > indexColorModel.getRed(1)) ? true : false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 253 */     int i4 = ((MultiPixelPackedSampleModel)sampleModel2).getScanlineStride();
/* 254 */     int i5 = (i2 + 7) / 8;
/* 255 */     byte[] arrayOfByte = ((DataBufferByte)raster.getDataBuffer()).getData();
/*     */ 
/*     */     
/* 258 */     this.stream.write(0);
/* 259 */     this.stream.write(0);
/* 260 */     this.stream.write(intToMultiByte(i2));
/* 261 */     this.stream.write(intToMultiByte(i3));
/*     */ 
/*     */     
/* 264 */     if (!bool1 && i4 == i5) {
/*     */       
/* 266 */       this.stream.write(arrayOfByte, 0, i3 * i5);
/* 267 */       processImageProgress(100.0F);
/*     */     } else {
/*     */       
/* 270 */       int i6 = 0;
/* 271 */       if (!bool1) {
/*     */         
/* 273 */         for (byte b = 0; b < i3 && 
/* 274 */           !abortRequested(); b++) {
/*     */           
/* 276 */           this.stream.write(arrayOfByte, i6, i5);
/* 277 */           i6 += i4;
/* 278 */           processImageProgress(100.0F * b / i3);
/*     */         } 
/*     */       } else {
/*     */         
/* 282 */         byte[] arrayOfByte1 = new byte[i5];
/* 283 */         for (byte b = 0; b < i3 && 
/* 284 */           !abortRequested(); b++) {
/*     */           
/* 286 */           for (byte b1 = 0; b1 < i5; b1++) {
/* 287 */             arrayOfByte1[b1] = (byte)(arrayOfByte[b1 + i6] ^ 0xFFFFFFFF);
/*     */           }
/* 289 */           this.stream.write(arrayOfByte1, 0, i5);
/* 290 */           i6 += i4;
/* 291 */           processImageProgress(100.0F * b / i3);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 296 */     if (abortRequested()) {
/* 297 */       processWriteAborted();
/*     */     } else {
/* 299 */       processImageComplete();
/* 300 */       this.stream.flushBefore(this.stream.getStreamPosition());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reset() {
/* 305 */     super.reset();
/* 306 */     this.stream = null;
/*     */   }
/*     */   
/*     */   private void checkSampleModel(SampleModel paramSampleModel) {
/* 310 */     int i = paramSampleModel.getDataType();
/* 311 */     if (i < 0 || i > 3 || paramSampleModel
/* 312 */       .getNumBands() != 1 || paramSampleModel.getSampleSize(0) != 1)
/* 313 */       throw new IllegalArgumentException(I18N.getString("WBMPImageWriter2")); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/wbmp/WBMPImageWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */