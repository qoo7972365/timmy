/*     */ package sun.java2d.cmm.lcms;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ComponentColorModel;
/*     */ import java.awt.image.ComponentSampleModel;
/*     */ import java.awt.image.Raster;
/*     */ import sun.awt.image.ByteComponentRaster;
/*     */ import sun.awt.image.IntegerComponentRaster;
/*     */ import sun.awt.image.ShortComponentRaster;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class LCMSImageLayout
/*     */ {
/*     */   public static final int SWAPFIRST = 16384;
/*     */   public static final int DOSWAP = 1024;
/*     */   
/*     */   public static int BYTES_SH(int paramInt) {
/*  41 */     return paramInt;
/*     */   }
/*     */   
/*     */   public static int EXTRA_SH(int paramInt) {
/*  45 */     return paramInt << 7;
/*     */   }
/*     */   
/*     */   public static int CHANNELS_SH(int paramInt) {
/*  49 */     return paramInt << 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  54 */   public static final int PT_RGB_8 = CHANNELS_SH(3) | BYTES_SH(1);
/*     */   
/*  56 */   public static final int PT_GRAY_8 = CHANNELS_SH(1) | BYTES_SH(1);
/*     */   
/*  58 */   public static final int PT_GRAY_16 = CHANNELS_SH(1) | BYTES_SH(2);
/*     */   
/*  60 */   public static final int PT_RGBA_8 = EXTRA_SH(1) | CHANNELS_SH(3) | BYTES_SH(1);
/*     */   
/*  62 */   public static final int PT_ARGB_8 = EXTRA_SH(1) | CHANNELS_SH(3) | BYTES_SH(1) | 0x4000;
/*  63 */   public static final int PT_BGR_8 = 0x400 | 
/*  64 */     CHANNELS_SH(3) | BYTES_SH(1);
/*  65 */   public static final int PT_ABGR_8 = 0x400 | 
/*  66 */     EXTRA_SH(1) | CHANNELS_SH(3) | BYTES_SH(1);
/*  67 */   public static final int PT_BGRA_8 = EXTRA_SH(1) | CHANNELS_SH(3) | 
/*  68 */     BYTES_SH(1) | 0x400 | 0x4000;
/*     */   
/*     */   public static final int DT_BYTE = 0;
/*     */   
/*     */   public static final int DT_SHORT = 1;
/*     */   
/*     */   public static final int DT_INT = 2;
/*     */   
/*     */   public static final int DT_DOUBLE = 3;
/*     */   
/*     */   boolean isIntPacked = false;
/*     */   
/*     */   int pixelType;
/*     */   
/*     */   int dataType;
/*     */   
/*     */   int width;
/*     */   int height;
/*     */   int nextRowOffset;
/*     */   private int nextPixelOffset;
/*     */   int offset;
/*     */   private boolean imageAtOnce = false;
/*     */   Object dataArray;
/*     */   private int dataArrayLength;
/*     */   
/*     */   private LCMSImageLayout(int paramInt1, int paramInt2, int paramInt3) throws ImageLayoutException {
/*  94 */     this.pixelType = paramInt2;
/*  95 */     this.width = paramInt1;
/*  96 */     this.height = 1;
/*  97 */     this.nextPixelOffset = paramInt3;
/*  98 */     this.nextRowOffset = safeMult(paramInt3, paramInt1);
/*  99 */     this.offset = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LCMSImageLayout(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws ImageLayoutException {
/* 106 */     this.pixelType = paramInt3;
/* 107 */     this.width = paramInt1;
/* 108 */     this.height = paramInt2;
/* 109 */     this.nextPixelOffset = paramInt4;
/* 110 */     this.nextRowOffset = safeMult(paramInt4, paramInt1);
/* 111 */     this.offset = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LCMSImageLayout(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) throws ImageLayoutException {
/* 118 */     this(paramInt1, paramInt2, paramInt3);
/* 119 */     this.dataType = 0;
/* 120 */     this.dataArray = paramArrayOfbyte;
/* 121 */     this.dataArrayLength = paramArrayOfbyte.length;
/*     */     
/* 123 */     verify();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LCMSImageLayout(short[] paramArrayOfshort, int paramInt1, int paramInt2, int paramInt3) throws ImageLayoutException {
/* 129 */     this(paramInt1, paramInt2, paramInt3);
/* 130 */     this.dataType = 1;
/* 131 */     this.dataArray = paramArrayOfshort;
/* 132 */     this.dataArrayLength = 2 * paramArrayOfshort.length;
/*     */     
/* 134 */     verify();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LCMSImageLayout(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3) throws ImageLayoutException {
/* 140 */     this(paramInt1, paramInt2, paramInt3);
/* 141 */     this.dataType = 2;
/* 142 */     this.dataArray = paramArrayOfint;
/* 143 */     this.dataArrayLength = 4 * paramArrayOfint.length;
/*     */     
/* 145 */     verify();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LCMSImageLayout(double[] paramArrayOfdouble, int paramInt1, int paramInt2, int paramInt3) throws ImageLayoutException {
/* 151 */     this(paramInt1, paramInt2, paramInt3);
/* 152 */     this.dataType = 3;
/* 153 */     this.dataArray = paramArrayOfdouble;
/* 154 */     this.dataArrayLength = 8 * paramArrayOfdouble.length;
/*     */     
/* 156 */     verify();
/*     */   }
/*     */   private LCMSImageLayout() {}
/*     */   
/*     */   public static LCMSImageLayout createImageLayout(BufferedImage paramBufferedImage) throws ImageLayoutException {
/*     */     ColorModel colorModel;
/*     */     IntegerComponentRaster integerComponentRaster;
/*     */     ByteComponentRaster byteComponentRaster;
/*     */     ShortComponentRaster shortComponentRaster;
/*     */     int i;
/* 166 */     LCMSImageLayout lCMSImageLayout = new LCMSImageLayout();
/*     */     
/* 168 */     switch (paramBufferedImage.getType()) {
/*     */       case 1:
/* 170 */         lCMSImageLayout.pixelType = PT_ARGB_8;
/* 171 */         lCMSImageLayout.isIntPacked = true;
/*     */         break;
/*     */       case 2:
/* 174 */         lCMSImageLayout.pixelType = PT_ARGB_8;
/* 175 */         lCMSImageLayout.isIntPacked = true;
/*     */         break;
/*     */       case 4:
/* 178 */         lCMSImageLayout.pixelType = PT_ABGR_8;
/* 179 */         lCMSImageLayout.isIntPacked = true;
/*     */         break;
/*     */       case 5:
/* 182 */         lCMSImageLayout.pixelType = PT_BGR_8;
/*     */         break;
/*     */       case 6:
/* 185 */         lCMSImageLayout.pixelType = PT_ABGR_8;
/*     */         break;
/*     */       case 10:
/* 188 */         lCMSImageLayout.pixelType = PT_GRAY_8;
/*     */         break;
/*     */       case 11:
/* 191 */         lCMSImageLayout.pixelType = PT_GRAY_16;
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 198 */         colorModel = paramBufferedImage.getColorModel();
/* 199 */         if (colorModel instanceof ComponentColorModel) {
/* 200 */           ComponentColorModel componentColorModel = (ComponentColorModel)colorModel;
/*     */ 
/*     */           
/* 203 */           int[] arrayOfInt = componentColorModel.getComponentSize();
/* 204 */           for (int j : arrayOfInt) {
/* 205 */             if (j != 8) {
/* 206 */               return null;
/*     */             }
/*     */           } 
/*     */           
/* 210 */           return createImageLayout(paramBufferedImage.getRaster());
/*     */         } 
/*     */         
/* 213 */         return null;
/*     */     } 
/*     */     
/* 216 */     lCMSImageLayout.width = paramBufferedImage.getWidth();
/* 217 */     lCMSImageLayout.height = paramBufferedImage.getHeight();
/*     */     
/* 219 */     switch (paramBufferedImage.getType()) {
/*     */ 
/*     */       
/*     */       case 1:
/*     */       case 2:
/*     */       case 4:
/* 225 */         integerComponentRaster = (IntegerComponentRaster)paramBufferedImage.getRaster();
/* 226 */         lCMSImageLayout.nextRowOffset = safeMult(4, integerComponentRaster.getScanlineStride());
/* 227 */         lCMSImageLayout.nextPixelOffset = safeMult(4, integerComponentRaster.getPixelStride());
/* 228 */         lCMSImageLayout.offset = safeMult(4, integerComponentRaster.getDataOffset(0));
/* 229 */         lCMSImageLayout.dataArray = integerComponentRaster.getDataStorage();
/* 230 */         lCMSImageLayout.dataArrayLength = 4 * (integerComponentRaster.getDataStorage()).length;
/* 231 */         lCMSImageLayout.dataType = 2;
/*     */         
/* 233 */         if (lCMSImageLayout.nextRowOffset == lCMSImageLayout.width * 4 * integerComponentRaster.getPixelStride()) {
/* 234 */           lCMSImageLayout.imageAtOnce = true;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 296 */         lCMSImageLayout.verify();
/* 297 */         return lCMSImageLayout;case 5: case 6: byteComponentRaster = (ByteComponentRaster)paramBufferedImage.getRaster(); lCMSImageLayout.nextRowOffset = byteComponentRaster.getScanlineStride(); lCMSImageLayout.nextPixelOffset = byteComponentRaster.getPixelStride(); i = paramBufferedImage.getSampleModel().getNumBands() - 1; lCMSImageLayout.offset = byteComponentRaster.getDataOffset(i); lCMSImageLayout.dataArray = byteComponentRaster.getDataStorage(); lCMSImageLayout.dataArrayLength = (byteComponentRaster.getDataStorage()).length; lCMSImageLayout.dataType = 0; if (lCMSImageLayout.nextRowOffset == lCMSImageLayout.width * byteComponentRaster.getPixelStride()) lCMSImageLayout.imageAtOnce = true;  lCMSImageLayout.verify(); return lCMSImageLayout;case 10: byteComponentRaster = (ByteComponentRaster)paramBufferedImage.getRaster(); lCMSImageLayout.nextRowOffset = byteComponentRaster.getScanlineStride(); lCMSImageLayout.nextPixelOffset = byteComponentRaster.getPixelStride(); lCMSImageLayout.dataArrayLength = (byteComponentRaster.getDataStorage()).length; lCMSImageLayout.offset = byteComponentRaster.getDataOffset(0); lCMSImageLayout.dataArray = byteComponentRaster.getDataStorage(); lCMSImageLayout.dataType = 0; if (lCMSImageLayout.nextRowOffset == lCMSImageLayout.width * byteComponentRaster.getPixelStride()) lCMSImageLayout.imageAtOnce = true;  lCMSImageLayout.verify(); return lCMSImageLayout;case 11: shortComponentRaster = (ShortComponentRaster)paramBufferedImage.getRaster(); lCMSImageLayout.nextRowOffset = safeMult(2, shortComponentRaster.getScanlineStride()); lCMSImageLayout.nextPixelOffset = safeMult(2, shortComponentRaster.getPixelStride()); lCMSImageLayout.offset = safeMult(2, shortComponentRaster.getDataOffset(0)); lCMSImageLayout.dataArray = shortComponentRaster.getDataStorage(); lCMSImageLayout.dataArrayLength = 2 * (shortComponentRaster.getDataStorage()).length; lCMSImageLayout.dataType = 1; if (lCMSImageLayout.nextRowOffset == lCMSImageLayout.width * 2 * shortComponentRaster.getPixelStride()) lCMSImageLayout.imageAtOnce = true;  lCMSImageLayout.verify(); return lCMSImageLayout;
/*     */     } 
/*     */     return null;
/*     */   }
/* 301 */   private enum BandOrder { DIRECT,
/* 302 */     INVERTED,
/* 303 */     ARBITRARY,
/* 304 */     UNKNOWN;
/*     */     
/*     */     public static BandOrder getBandOrder(int[] param1ArrayOfint) {
/* 307 */       BandOrder bandOrder = UNKNOWN;
/*     */       
/* 309 */       int i = param1ArrayOfint.length;
/*     */       
/* 311 */       for (byte b = 0; bandOrder != ARBITRARY && b < param1ArrayOfint.length; b++) {
/* 312 */         switch (bandOrder) {
/*     */           case UNKNOWN:
/* 314 */             if (param1ArrayOfint[b] == b) {
/* 315 */               bandOrder = DIRECT; break;
/* 316 */             }  if (param1ArrayOfint[b] == i - 1 - b) {
/* 317 */               bandOrder = INVERTED; break;
/*     */             } 
/* 319 */             bandOrder = ARBITRARY;
/*     */             break;
/*     */           
/*     */           case DIRECT:
/* 323 */             if (param1ArrayOfint[b] != b) {
/* 324 */               bandOrder = ARBITRARY;
/*     */             }
/*     */             break;
/*     */           case INVERTED:
/* 328 */             if (param1ArrayOfint[b] != i - 1 - b) {
/* 329 */               bandOrder = ARBITRARY;
/*     */             }
/*     */             break;
/*     */         } 
/*     */       } 
/* 334 */       return bandOrder;
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   private void verify() throws ImageLayoutException {
/* 340 */     if (this.offset < 0 || this.offset >= this.dataArrayLength) {
/* 341 */       throw new ImageLayoutException("Invalid image layout");
/*     */     }
/*     */     
/* 344 */     if (this.nextPixelOffset != getBytesPerPixel(this.pixelType)) {
/* 345 */       throw new ImageLayoutException("Invalid image layout");
/*     */     }
/*     */     
/* 348 */     int i = safeMult(this.nextRowOffset, this.height - 1);
/*     */     
/* 350 */     int j = safeMult(this.nextPixelOffset, this.width - 1);
/*     */     
/* 352 */     j = safeAdd(j, i);
/*     */     
/* 354 */     int k = safeAdd(this.offset, j);
/*     */     
/* 356 */     if (k < 0 || k >= this.dataArrayLength) {
/* 357 */       throw new ImageLayoutException("Invalid image layout");
/*     */     }
/*     */   }
/*     */   
/*     */   static int safeAdd(int paramInt1, int paramInt2) throws ImageLayoutException {
/* 362 */     long l = paramInt1;
/* 363 */     l += paramInt2;
/* 364 */     if (l < -2147483648L || l > 2147483647L) {
/* 365 */       throw new ImageLayoutException("Invalid image layout");
/*     */     }
/* 367 */     return (int)l;
/*     */   }
/*     */   
/*     */   static int safeMult(int paramInt1, int paramInt2) throws ImageLayoutException {
/* 371 */     long l = paramInt1;
/* 372 */     l *= paramInt2;
/* 373 */     if (l < -2147483648L || l > 2147483647L) {
/* 374 */       throw new ImageLayoutException("Invalid image layout");
/*     */     }
/* 376 */     return (int)l;
/*     */   }
/*     */   
/*     */   public static class ImageLayoutException extends Exception {
/*     */     public ImageLayoutException(String param1String) {
/* 381 */       super(param1String);
/*     */     } }
/*     */   
/*     */   public static LCMSImageLayout createImageLayout(Raster paramRaster) {
/* 385 */     LCMSImageLayout lCMSImageLayout = new LCMSImageLayout();
/* 386 */     if (paramRaster instanceof ByteComponentRaster && paramRaster
/* 387 */       .getSampleModel() instanceof ComponentSampleModel) {
/* 388 */       ByteComponentRaster byteComponentRaster = (ByteComponentRaster)paramRaster;
/*     */       
/* 390 */       ComponentSampleModel componentSampleModel = (ComponentSampleModel)paramRaster.getSampleModel();
/*     */       
/* 392 */       lCMSImageLayout.pixelType = CHANNELS_SH(byteComponentRaster.getNumBands()) | BYTES_SH(1);
/*     */       
/* 394 */       int[] arrayOfInt = componentSampleModel.getBandOffsets();
/* 395 */       BandOrder bandOrder = BandOrder.getBandOrder(arrayOfInt);
/*     */       
/* 397 */       int i = 0;
/* 398 */       switch (bandOrder) {
/*     */         case INVERTED:
/* 400 */           lCMSImageLayout.pixelType |= 0x400;
/* 401 */           i = componentSampleModel.getNumBands() - 1;
/*     */           break;
/*     */         
/*     */         case DIRECT:
/*     */           break;
/*     */         
/*     */         default:
/* 408 */           return null;
/*     */       } 
/*     */       
/* 411 */       lCMSImageLayout.nextRowOffset = byteComponentRaster.getScanlineStride();
/* 412 */       lCMSImageLayout.nextPixelOffset = byteComponentRaster.getPixelStride();
/*     */       
/* 414 */       lCMSImageLayout.offset = byteComponentRaster.getDataOffset(i);
/* 415 */       lCMSImageLayout.dataArray = byteComponentRaster.getDataStorage();
/* 416 */       lCMSImageLayout.dataType = 0;
/*     */       
/* 418 */       lCMSImageLayout.width = byteComponentRaster.getWidth();
/* 419 */       lCMSImageLayout.height = byteComponentRaster.getHeight();
/*     */       
/* 421 */       if (lCMSImageLayout.nextRowOffset == lCMSImageLayout.width * byteComponentRaster.getPixelStride()) {
/* 422 */         lCMSImageLayout.imageAtOnce = true;
/*     */       }
/* 424 */       return lCMSImageLayout;
/*     */     } 
/* 426 */     return null;
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
/*     */ 
/*     */   
/*     */   private static int getBytesPerPixel(int paramInt) {
/* 443 */     int i = 0x7 & paramInt;
/* 444 */     int j = 0xF & paramInt >> 3;
/* 445 */     int k = 0x7 & paramInt >> 7;
/*     */     
/* 447 */     return i * (j + k);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/cmm/lcms/LCMSImageLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */