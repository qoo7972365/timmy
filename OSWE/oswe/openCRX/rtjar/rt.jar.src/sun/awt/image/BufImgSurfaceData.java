/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.SampleModel;
/*     */ import java.awt.image.WritableRaster;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.RenderLoops;
/*     */ import sun.java2d.loops.SurfaceType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BufImgSurfaceData
/*     */   extends SurfaceData
/*     */ {
/*     */   BufferedImage bufImg;
/*     */   private BufferedImageGraphicsConfig graphicsConfig;
/*     */   RenderLoops solidloops;
/*     */   private static final int DCM_RGBX_RED_MASK = -16777216;
/*     */   private static final int DCM_RGBX_GREEN_MASK = 16711680;
/*     */   private static final int DCM_RGBX_BLUE_MASK = 65280;
/*     */   private static final int DCM_555X_RED_MASK = 63488;
/*     */   private static final int DCM_555X_GREEN_MASK = 1984;
/*     */   private static final int DCM_555X_BLUE_MASK = 62;
/*     */   private static final int DCM_4444_RED_MASK = 3840;
/*     */   private static final int DCM_4444_GREEN_MASK = 240;
/*     */   private static final int DCM_4444_BLUE_MASK = 15;
/*     */   private static final int DCM_4444_ALPHA_MASK = 61440;
/*     */   private static final int DCM_ARGBBM_ALPHA_MASK = 16777216;
/*     */   private static final int DCM_ARGBBM_RED_MASK = 16711680;
/*     */   private static final int DCM_ARGBBM_GREEN_MASK = 65280;
/*     */   private static final int DCM_ARGBBM_BLUE_MASK = 255;
/*     */   private static final int CACHE_SIZE = 5;
/*     */   
/*     */   static {
/*  68 */     initIDs(IndexColorModel.class, ICMColorData.class); } public static SurfaceData createData(BufferedImage paramBufferedImage) {
/*     */     SurfaceData surfaceData;
/*     */     SurfaceType surfaceType;
/*     */     SampleModel sampleModel;
/*  72 */     if (paramBufferedImage == null) {
/*  73 */       throw new NullPointerException("BufferedImage cannot be null");
/*     */     }
/*     */     
/*  76 */     ColorModel colorModel = paramBufferedImage.getColorModel();
/*  77 */     int i = paramBufferedImage.getType();
/*     */     
/*  79 */     switch (i)
/*     */     { case 4:
/*  81 */         surfaceData = createDataIC(paramBufferedImage, SurfaceType.IntBgr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 240 */         ((BufImgSurfaceData)surfaceData).initSolidLoops();
/* 241 */         return surfaceData;case 1: surfaceData = createDataIC(paramBufferedImage, SurfaceType.IntRgb); ((BufImgSurfaceData)surfaceData).initSolidLoops(); return surfaceData;case 2: surfaceData = createDataIC(paramBufferedImage, SurfaceType.IntArgb); ((BufImgSurfaceData)surfaceData).initSolidLoops(); return surfaceData;case 3: surfaceData = createDataIC(paramBufferedImage, SurfaceType.IntArgbPre); ((BufImgSurfaceData)surfaceData).initSolidLoops(); return surfaceData;case 5: surfaceData = createDataBC(paramBufferedImage, SurfaceType.ThreeByteBgr, 2); ((BufImgSurfaceData)surfaceData).initSolidLoops(); return surfaceData;case 6: surfaceData = createDataBC(paramBufferedImage, SurfaceType.FourByteAbgr, 3); ((BufImgSurfaceData)surfaceData).initSolidLoops(); return surfaceData;case 7: surfaceData = createDataBC(paramBufferedImage, SurfaceType.FourByteAbgrPre, 3); ((BufImgSurfaceData)surfaceData).initSolidLoops(); return surfaceData;case 8: surfaceData = createDataSC(paramBufferedImage, SurfaceType.Ushort565Rgb, (IndexColorModel)null); ((BufImgSurfaceData)surfaceData).initSolidLoops(); return surfaceData;case 9: surfaceData = createDataSC(paramBufferedImage, SurfaceType.Ushort555Rgb, (IndexColorModel)null); ((BufImgSurfaceData)surfaceData).initSolidLoops(); return surfaceData;case 13: switch (colorModel.getTransparency()) { case 1: if (isOpaqueGray((IndexColorModel)colorModel)) { SurfaceType surfaceType1 = SurfaceType.Index8Gray; break; }  surfaceType = SurfaceType.ByteIndexedOpaque; break;case 2: surfaceType = SurfaceType.ByteIndexedBm; break;case 3: surfaceType = SurfaceType.ByteIndexed; break;default: throw new InternalError("Unrecognized transparency"); }  surfaceData = createDataBC(paramBufferedImage, surfaceType, 0); ((BufImgSurfaceData)surfaceData).initSolidLoops(); return surfaceData;case 10: surfaceData = createDataBC(paramBufferedImage, SurfaceType.ByteGray, 0); ((BufImgSurfaceData)surfaceData).initSolidLoops(); return surfaceData;case 11: surfaceData = createDataSC(paramBufferedImage, SurfaceType.UshortGray, (IndexColorModel)null); ((BufImgSurfaceData)surfaceData).initSolidLoops(); return surfaceData;case 12: sampleModel = paramBufferedImage.getRaster().getSampleModel(); switch (sampleModel.getSampleSize(0)) { case 1: surfaceType = SurfaceType.ByteBinary1Bit; break;case 2: surfaceType = SurfaceType.ByteBinary2Bit; break;case 4: surfaceType = SurfaceType.ByteBinary4Bit; break;default: throw new InternalError("Unrecognized pixel size"); }  surfaceData = createDataBP(paramBufferedImage, surfaceType); ((BufImgSurfaceData)surfaceData).initSolidLoops(); return surfaceData; }  WritableRaster writableRaster = paramBufferedImage.getRaster(); int j = writableRaster.getNumBands(); if (writableRaster instanceof IntegerComponentRaster && writableRaster.getNumDataElements() == 1 && ((IntegerComponentRaster)writableRaster).getPixelStride() == 1) { SurfaceType surfaceType1 = SurfaceType.AnyInt; if (colorModel instanceof DirectColorModel) { DirectColorModel directColorModel = (DirectColorModel)colorModel; int k = directColorModel.getAlphaMask(); int m = directColorModel.getRedMask(); int n = directColorModel.getGreenMask(); int i1 = directColorModel.getBlueMask(); if (j == 3 && k == 0 && m == -16777216 && n == 16711680 && i1 == 65280) { surfaceType1 = SurfaceType.IntRgbx; } else if (j == 4 && k == 16777216 && m == 16711680 && n == 65280 && i1 == 255) { surfaceType1 = SurfaceType.IntArgbBm; } else { surfaceType1 = SurfaceType.AnyDcm; }  }  surfaceData = createDataIC(paramBufferedImage, surfaceType1); } else if (writableRaster instanceof ShortComponentRaster && writableRaster.getNumDataElements() == 1 && ((ShortComponentRaster)writableRaster).getPixelStride() == 1) { SurfaceType surfaceType1 = SurfaceType.AnyShort; IndexColorModel indexColorModel = null; if (colorModel instanceof DirectColorModel) { DirectColorModel directColorModel = (DirectColorModel)colorModel; int k = directColorModel.getAlphaMask(); int m = directColorModel.getRedMask(); int n = directColorModel.getGreenMask(); int i1 = directColorModel.getBlueMask(); if (j == 3 && k == 0 && m == 63488 && n == 1984 && i1 == 62) { surfaceType1 = SurfaceType.Ushort555Rgbx; } else if (j == 4 && k == 61440 && m == 3840 && n == 240 && i1 == 15) { surfaceType1 = SurfaceType.Ushort4444Argb; }  } else if (colorModel instanceof IndexColorModel) { indexColorModel = (IndexColorModel)colorModel; if (indexColorModel.getPixelSize() == 12) { if (isOpaqueGray(indexColorModel)) { surfaceType1 = SurfaceType.Index12Gray; } else { surfaceType1 = SurfaceType.UshortIndexed; }  } else { indexColorModel = null; }  }  surfaceData = createDataSC(paramBufferedImage, surfaceType1, indexColorModel); } else { surfaceData = new BufImgSurfaceData(writableRaster.getDataBuffer(), paramBufferedImage, SurfaceType.Custom); }  ((BufImgSurfaceData)surfaceData).initSolidLoops(); return surfaceData;
/*     */   }
/*     */   
/*     */   public static SurfaceData createData(Raster paramRaster, ColorModel paramColorModel) {
/* 245 */     throw new InternalError("SurfaceData not implemented for Raster/CM");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SurfaceData createDataIC(BufferedImage paramBufferedImage, SurfaceType paramSurfaceType) {
/* 251 */     IntegerComponentRaster integerComponentRaster = (IntegerComponentRaster)paramBufferedImage.getRaster();
/*     */     
/* 253 */     BufImgSurfaceData bufImgSurfaceData = new BufImgSurfaceData(integerComponentRaster.getDataBuffer(), paramBufferedImage, paramSurfaceType);
/* 254 */     bufImgSurfaceData.initRaster(integerComponentRaster.getDataStorage(), integerComponentRaster
/* 255 */         .getDataOffset(0) * 4, 0, integerComponentRaster
/* 256 */         .getWidth(), integerComponentRaster
/* 257 */         .getHeight(), integerComponentRaster
/* 258 */         .getPixelStride() * 4, integerComponentRaster
/* 259 */         .getScanlineStride() * 4, (IndexColorModel)null);
/*     */     
/* 261 */     return bufImgSurfaceData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SurfaceData createDataSC(BufferedImage paramBufferedImage, SurfaceType paramSurfaceType, IndexColorModel paramIndexColorModel) {
/* 268 */     ShortComponentRaster shortComponentRaster = (ShortComponentRaster)paramBufferedImage.getRaster();
/*     */     
/* 270 */     BufImgSurfaceData bufImgSurfaceData = new BufImgSurfaceData(shortComponentRaster.getDataBuffer(), paramBufferedImage, paramSurfaceType);
/* 271 */     bufImgSurfaceData.initRaster(shortComponentRaster.getDataStorage(), shortComponentRaster
/* 272 */         .getDataOffset(0) * 2, 0, shortComponentRaster
/* 273 */         .getWidth(), shortComponentRaster
/* 274 */         .getHeight(), shortComponentRaster
/* 275 */         .getPixelStride() * 2, shortComponentRaster
/* 276 */         .getScanlineStride() * 2, paramIndexColorModel);
/*     */     
/* 278 */     return bufImgSurfaceData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SurfaceData createDataBC(BufferedImage paramBufferedImage, SurfaceType paramSurfaceType, int paramInt) {
/* 285 */     ByteComponentRaster byteComponentRaster = (ByteComponentRaster)paramBufferedImage.getRaster();
/*     */     
/* 287 */     BufImgSurfaceData bufImgSurfaceData = new BufImgSurfaceData(byteComponentRaster.getDataBuffer(), paramBufferedImage, paramSurfaceType);
/* 288 */     ColorModel colorModel = paramBufferedImage.getColorModel();
/* 289 */     IndexColorModel indexColorModel = (colorModel instanceof IndexColorModel) ? (IndexColorModel)colorModel : null;
/*     */ 
/*     */     
/* 292 */     bufImgSurfaceData.initRaster(byteComponentRaster.getDataStorage(), byteComponentRaster
/* 293 */         .getDataOffset(paramInt), 0, byteComponentRaster
/* 294 */         .getWidth(), byteComponentRaster
/* 295 */         .getHeight(), byteComponentRaster
/* 296 */         .getPixelStride(), byteComponentRaster
/* 297 */         .getScanlineStride(), indexColorModel);
/*     */     
/* 299 */     return bufImgSurfaceData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SurfaceData createDataBP(BufferedImage paramBufferedImage, SurfaceType paramSurfaceType) {
/* 305 */     BytePackedRaster bytePackedRaster = (BytePackedRaster)paramBufferedImage.getRaster();
/*     */     
/* 307 */     BufImgSurfaceData bufImgSurfaceData = new BufImgSurfaceData(bytePackedRaster.getDataBuffer(), paramBufferedImage, paramSurfaceType);
/* 308 */     ColorModel colorModel = paramBufferedImage.getColorModel();
/* 309 */     IndexColorModel indexColorModel = (colorModel instanceof IndexColorModel) ? (IndexColorModel)colorModel : null;
/*     */ 
/*     */     
/* 312 */     bufImgSurfaceData.initRaster(bytePackedRaster.getDataStorage(), bytePackedRaster
/* 313 */         .getDataBitOffset() / 8, bytePackedRaster
/* 314 */         .getDataBitOffset() & 0x7, bytePackedRaster
/* 315 */         .getWidth(), bytePackedRaster
/* 316 */         .getHeight(), 0, bytePackedRaster
/*     */         
/* 318 */         .getScanlineStride(), indexColorModel);
/*     */     
/* 320 */     return bufImgSurfaceData;
/*     */   }
/*     */   
/*     */   public RenderLoops getRenderLoops(SunGraphics2D paramSunGraphics2D) {
/* 324 */     if (paramSunGraphics2D.paintState <= 1 && paramSunGraphics2D.compositeState <= 0)
/*     */     {
/*     */       
/* 327 */       return this.solidloops;
/*     */     }
/* 329 */     return super.getRenderLoops(paramSunGraphics2D);
/*     */   }
/*     */   
/*     */   public Raster getRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 333 */     return this.bufImg.getRaster();
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
/*     */   
/*     */   public BufImgSurfaceData(DataBuffer paramDataBuffer, BufferedImage paramBufferedImage, SurfaceType paramSurfaceType) {
/* 351 */     super(SunWritableRaster.stealTrackable(paramDataBuffer), paramSurfaceType, paramBufferedImage
/* 352 */         .getColorModel());
/* 353 */     this.bufImg = paramBufferedImage;
/*     */   }
/*     */   
/*     */   protected BufImgSurfaceData(SurfaceType paramSurfaceType, ColorModel paramColorModel) {
/* 357 */     super(paramSurfaceType, paramColorModel);
/*     */   }
/*     */   
/*     */   public void initSolidLoops() {
/* 361 */     this.solidloops = getSolidLoops(getSurfaceType());
/*     */   }
/*     */ 
/*     */   
/* 365 */   private static RenderLoops[] loopcache = new RenderLoops[5];
/* 366 */   private static SurfaceType[] typecache = new SurfaceType[5];
/*     */   public static synchronized RenderLoops getSolidLoops(SurfaceType paramSurfaceType) {
/* 368 */     for (byte b = 4; b >= 0; b--) {
/* 369 */       SurfaceType surfaceType = typecache[b];
/* 370 */       if (surfaceType == paramSurfaceType)
/* 371 */         return loopcache[b]; 
/* 372 */       if (surfaceType == null) {
/*     */         break;
/*     */       }
/*     */     } 
/* 376 */     RenderLoops renderLoops = makeRenderLoops(SurfaceType.OpaqueColor, CompositeType.SrcNoEa, paramSurfaceType);
/*     */ 
/*     */     
/* 379 */     System.arraycopy(loopcache, 1, loopcache, 0, 4);
/* 380 */     System.arraycopy(typecache, 1, typecache, 0, 4);
/* 381 */     loopcache[4] = renderLoops;
/* 382 */     typecache[4] = paramSurfaceType;
/* 383 */     return renderLoops;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SurfaceData getReplacement() {
/* 389 */     return restoreContents(this.bufImg);
/*     */   }
/*     */   
/*     */   public synchronized GraphicsConfiguration getDeviceConfiguration() {
/* 393 */     if (this.graphicsConfig == null) {
/* 394 */       this.graphicsConfig = BufferedImageGraphicsConfig.getConfig(this.bufImg);
/*     */     }
/* 396 */     return this.graphicsConfig;
/*     */   }
/*     */   
/*     */   public Rectangle getBounds() {
/* 400 */     return new Rectangle(this.bufImg.getWidth(), this.bufImg.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkCustomComposite() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getDestination() {
/* 412 */     return this.bufImg;
/*     */   }
/*     */   private static native void initIDs(Class paramClass1, Class paramClass2);
/*     */   protected native void initRaster(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, IndexColorModel paramIndexColorModel);
/* 416 */   public static final class ICMColorData { private long pData = 0L;
/*     */     
/*     */     private ICMColorData(long param1Long) {
/* 419 */       this.pData = param1Long;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/BufImgSurfaceData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */