/*     */ package com.sun.imageio.plugins.wbmp;
/*     */ 
/*     */ import com.sun.imageio.plugins.common.I18N;
/*     */ import com.sun.imageio.plugins.common.ReaderUtil;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.MultiPixelPackedSampleModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import javax.imageio.IIOException;
/*     */ import javax.imageio.ImageReadParam;
/*     */ import javax.imageio.ImageReader;
/*     */ import javax.imageio.ImageTypeSpecifier;
/*     */ import javax.imageio.metadata.IIOMetadata;
/*     */ import javax.imageio.spi.ImageReaderSpi;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WBMPImageReader
/*     */   extends ImageReader
/*     */ {
/*  57 */   private ImageInputStream iis = null;
/*     */ 
/*     */   
/*     */   private boolean gotHeader = false;
/*     */ 
/*     */   
/*     */   private int width;
/*     */ 
/*     */   
/*     */   private int height;
/*     */ 
/*     */   
/*     */   private int wbmpType;
/*     */ 
/*     */   
/*     */   private WBMPMetadata metadata;
/*     */ 
/*     */   
/*     */   public WBMPImageReader(ImageReaderSpi paramImageReaderSpi) {
/*  76 */     super(paramImageReaderSpi);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInput(Object paramObject, boolean paramBoolean1, boolean paramBoolean2) {
/*  83 */     super.setInput(paramObject, paramBoolean1, paramBoolean2);
/*  84 */     this.iis = (ImageInputStream)paramObject;
/*  85 */     this.gotHeader = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumImages(boolean paramBoolean) throws IOException {
/*  90 */     if (this.iis == null) {
/*  91 */       throw new IllegalStateException(I18N.getString("GetNumImages0"));
/*     */     }
/*  93 */     if (this.seekForwardOnly && paramBoolean) {
/*  94 */       throw new IllegalStateException(I18N.getString("GetNumImages1"));
/*     */     }
/*  96 */     return 1;
/*     */   }
/*     */   
/*     */   public int getWidth(int paramInt) throws IOException {
/* 100 */     checkIndex(paramInt);
/* 101 */     readHeader();
/* 102 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight(int paramInt) throws IOException {
/* 106 */     checkIndex(paramInt);
/* 107 */     readHeader();
/* 108 */     return this.height;
/*     */   }
/*     */   
/*     */   public boolean isRandomAccessEasy(int paramInt) throws IOException {
/* 112 */     checkIndex(paramInt);
/* 113 */     return true;
/*     */   }
/*     */   
/*     */   private void checkIndex(int paramInt) {
/* 117 */     if (paramInt != 0) {
/* 118 */       throw new IndexOutOfBoundsException(I18N.getString("WBMPImageReader0"));
/*     */     }
/*     */   }
/*     */   
/*     */   public void readHeader() throws IOException {
/* 123 */     if (this.gotHeader) {
/*     */       return;
/*     */     }
/* 126 */     if (this.iis == null) {
/* 127 */       throw new IllegalStateException("Input source not set!");
/*     */     }
/*     */     
/* 130 */     this.metadata = new WBMPMetadata();
/*     */     
/* 132 */     this.wbmpType = this.iis.readByte();
/* 133 */     byte b = this.iis.readByte();
/*     */ 
/*     */     
/* 136 */     if (b != 0 || 
/* 137 */       !isValidWbmpType(this.wbmpType))
/*     */     {
/* 139 */       throw new IIOException(I18N.getString("WBMPImageReader2"));
/*     */     }
/*     */     
/* 142 */     this.metadata.wbmpType = this.wbmpType;
/*     */ 
/*     */     
/* 145 */     this.width = ReaderUtil.readMultiByteInteger(this.iis);
/* 146 */     this.metadata.width = this.width;
/*     */ 
/*     */     
/* 149 */     this.height = ReaderUtil.readMultiByteInteger(this.iis);
/* 150 */     this.metadata.height = this.height;
/*     */     
/* 152 */     this.gotHeader = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator getImageTypes(int paramInt) throws IOException {
/* 157 */     checkIndex(paramInt);
/* 158 */     readHeader();
/*     */     
/* 160 */     BufferedImage bufferedImage = new BufferedImage(1, 1, 12);
/*     */     
/* 162 */     ArrayList<ImageTypeSpecifier> arrayList = new ArrayList(1);
/* 163 */     arrayList.add(new ImageTypeSpecifier(bufferedImage));
/* 164 */     return arrayList.iterator();
/*     */   }
/*     */   
/*     */   public ImageReadParam getDefaultReadParam() {
/* 168 */     return new ImageReadParam();
/*     */   }
/*     */ 
/*     */   
/*     */   public IIOMetadata getImageMetadata(int paramInt) throws IOException {
/* 173 */     checkIndex(paramInt);
/* 174 */     if (this.metadata == null) {
/* 175 */       readHeader();
/*     */     }
/* 177 */     return this.metadata;
/*     */   }
/*     */   
/*     */   public IIOMetadata getStreamMetadata() throws IOException {
/* 181 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedImage read(int paramInt, ImageReadParam paramImageReadParam) throws IOException {
/* 187 */     if (this.iis == null) {
/* 188 */       throw new IllegalStateException(I18N.getString("WBMPImageReader1"));
/*     */     }
/*     */     
/* 191 */     checkIndex(paramInt);
/* 192 */     clearAbortRequest();
/* 193 */     processImageStarted(paramInt);
/* 194 */     if (paramImageReadParam == null) {
/* 195 */       paramImageReadParam = getDefaultReadParam();
/*     */     }
/*     */     
/* 198 */     readHeader();
/*     */     
/* 200 */     Rectangle rectangle1 = new Rectangle(0, 0, 0, 0);
/* 201 */     Rectangle rectangle2 = new Rectangle(0, 0, 0, 0);
/*     */     
/* 203 */     computeRegions(paramImageReadParam, this.width, this.height, paramImageReadParam
/* 204 */         .getDestination(), rectangle1, rectangle2);
/*     */ 
/*     */ 
/*     */     
/* 208 */     int i = paramImageReadParam.getSourceXSubsampling();
/* 209 */     int j = paramImageReadParam.getSourceYSubsampling();
/* 210 */     int k = paramImageReadParam.getSubsamplingXOffset();
/* 211 */     int m = paramImageReadParam.getSubsamplingYOffset();
/*     */ 
/*     */     
/* 214 */     BufferedImage bufferedImage = paramImageReadParam.getDestination();
/*     */     
/* 216 */     if (bufferedImage == null) {
/* 217 */       bufferedImage = new BufferedImage(rectangle2.x + rectangle2.width, rectangle2.y + rectangle2.height, 12);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     boolean bool = (rectangle2.equals(new Rectangle(0, 0, this.width, this.height)) && rectangle2.equals(new Rectangle(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight()))) ? true : false;
/*     */ 
/*     */     
/* 226 */     WritableRaster writableRaster = bufferedImage.getWritableTile(0, 0);
/*     */ 
/*     */ 
/*     */     
/* 230 */     MultiPixelPackedSampleModel multiPixelPackedSampleModel = (MultiPixelPackedSampleModel)bufferedImage.getSampleModel();
/*     */     
/* 232 */     if (bool) {
/* 233 */       if (abortRequested()) {
/* 234 */         processReadAborted();
/* 235 */         return bufferedImage;
/*     */       } 
/*     */ 
/*     */       
/* 239 */       this.iis.read(((DataBufferByte)writableRaster.getDataBuffer()).getData(), 0, this.height * multiPixelPackedSampleModel
/* 240 */           .getScanlineStride());
/* 241 */       processImageUpdate(bufferedImage, 0, 0, this.width, this.height, 1, 1, new int[] { 0 });
/*     */ 
/*     */ 
/*     */       
/* 245 */       processImageProgress(100.0F);
/*     */     } else {
/* 247 */       int n = (this.width + 7) / 8;
/* 248 */       byte[] arrayOfByte1 = new byte[n];
/* 249 */       byte[] arrayOfByte2 = ((DataBufferByte)writableRaster.getDataBuffer()).getData();
/* 250 */       int i1 = multiPixelPackedSampleModel.getScanlineStride();
/* 251 */       this.iis.skipBytes(n * rectangle1.y);
/* 252 */       int i2 = n * (j - 1);
/*     */ 
/*     */       
/* 255 */       int[] arrayOfInt1 = new int[rectangle2.width];
/* 256 */       int[] arrayOfInt2 = new int[rectangle2.width];
/* 257 */       int[] arrayOfInt3 = new int[rectangle2.width];
/* 258 */       int[] arrayOfInt4 = new int[rectangle2.width];
/*     */       
/* 260 */       int i3 = rectangle2.x, i4 = rectangle1.x, i5 = 0;
/* 261 */       for (; i3 < rectangle2.x + rectangle2.width; 
/* 262 */         i3++, i5++, i4 += i) {
/* 263 */         arrayOfInt3[i5] = i4 >> 3;
/* 264 */         arrayOfInt1[i5] = 7 - (i4 & 0x7);
/* 265 */         arrayOfInt4[i5] = i3 >> 3;
/* 266 */         arrayOfInt2[i5] = 7 - (i3 & 0x7);
/*     */       } 
/*     */       
/* 269 */       i3 = 0; i4 = rectangle1.y;
/* 270 */       i5 = rectangle2.y * i1;
/* 271 */       for (; i3 < rectangle2.height; i3++, i4 += j) {
/*     */         
/* 273 */         if (abortRequested())
/*     */           break; 
/* 275 */         this.iis.read(arrayOfByte1, 0, n);
/* 276 */         for (byte b = 0; b < rectangle2.width; b++) {
/*     */           
/* 278 */           int i6 = arrayOfByte1[arrayOfInt3[b]] >> arrayOfInt1[b] & 0x1;
/* 279 */           arrayOfByte2[i5 + arrayOfInt4[b]] = (byte)(arrayOfByte2[i5 + arrayOfInt4[b]] | i6 << arrayOfInt2[b]);
/*     */         } 
/*     */         
/* 282 */         i5 += i1;
/* 283 */         this.iis.skipBytes(i2);
/* 284 */         processImageUpdate(bufferedImage, 0, i3, rectangle2.width, 1, 1, 1, new int[] { 0 });
/*     */ 
/*     */ 
/*     */         
/* 288 */         processImageProgress(100.0F * i3 / rectangle2.height);
/*     */       } 
/*     */     } 
/*     */     
/* 292 */     if (abortRequested()) {
/* 293 */       processReadAborted();
/*     */     } else {
/* 295 */       processImageComplete();
/* 296 */     }  return bufferedImage;
/*     */   }
/*     */   
/*     */   public boolean canReadRaster() {
/* 300 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Raster readRaster(int paramInt, ImageReadParam paramImageReadParam) throws IOException {
/* 305 */     BufferedImage bufferedImage = read(paramInt, paramImageReadParam);
/* 306 */     return bufferedImage.getData();
/*     */   }
/*     */   
/*     */   public void reset() {
/* 310 */     super.reset();
/* 311 */     this.iis = null;
/* 312 */     this.gotHeader = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isValidWbmpType(int paramInt) {
/* 320 */     return (paramInt == 0);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/imageio/plugins/wbmp/WBMPImageReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */