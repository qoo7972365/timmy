/*      */ package java.awt.image;
/*      */ 
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.Image;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Transparency;
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import sun.awt.image.ByteComponentRaster;
/*      */ import sun.awt.image.IntegerComponentRaster;
/*      */ import sun.awt.image.OffScreenImageSource;
/*      */ import sun.awt.image.ShortComponentRaster;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BufferedImage
/*      */   extends Image
/*      */   implements WritableRenderedImage, Transparency
/*      */ {
/*      */   private int imageType;
/*      */   private ColorModel colorModel;
/*      */   private final WritableRaster raster;
/*      */   private OffScreenImageSource osis;
/*      */   private Hashtable<String, Object> properties;
/*      */   public static final int TYPE_CUSTOM = 0;
/*      */   public static final int TYPE_INT_RGB = 1;
/*      */   public static final int TYPE_INT_ARGB = 2;
/*      */   public static final int TYPE_INT_ARGB_PRE = 3;
/*      */   public static final int TYPE_INT_BGR = 4;
/*      */   public static final int TYPE_3BYTE_BGR = 5;
/*      */   public static final int TYPE_4BYTE_ABGR = 6;
/*      */   public static final int TYPE_4BYTE_ABGR_PRE = 7;
/*      */   public static final int TYPE_USHORT_565_RGB = 8;
/*      */   public static final int TYPE_USHORT_555_RGB = 9;
/*      */   public static final int TYPE_BYTE_GRAY = 10;
/*      */   public static final int TYPE_USHORT_GRAY = 11;
/*      */   public static final int TYPE_BYTE_BINARY = 12;
/*      */   public static final int TYPE_BYTE_INDEXED = 13;
/*      */   private static final int DCM_RED_MASK = 16711680;
/*      */   private static final int DCM_GREEN_MASK = 65280;
/*      */   private static final int DCM_BLUE_MASK = 255;
/*      */   private static final int DCM_ALPHA_MASK = -16777216;
/*      */   private static final int DCM_565_RED_MASK = 63488;
/*      */   private static final int DCM_565_GRN_MASK = 2016;
/*      */   private static final int DCM_565_BLU_MASK = 31;
/*      */   private static final int DCM_555_RED_MASK = 31744;
/*      */   private static final int DCM_555_GRN_MASK = 992;
/*      */   private static final int DCM_555_BLU_MASK = 31;
/*      */   private static final int DCM_BGR_RED_MASK = 255;
/*      */   private static final int DCM_BGR_GRN_MASK = 65280;
/*      */   private static final int DCM_BGR_BLU_MASK = 16711680;
/*      */   
/*      */   static {
/*  286 */     ColorModel.loadLibraries();
/*  287 */     initIDs();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BufferedImage(int paramInt1, int paramInt2, int paramInt3) {
/*      */     ColorSpace colorSpace;
/*      */     byte[] arrayOfByte;
/*      */     int[] arrayOfInt1, arrayOfInt2;
/*      */     byte b;
/*      */     int arrayOfInt3[], i, j;
/*      */     this.imageType = 0;
/*  315 */     switch (paramInt3) {
/*      */       
/*      */       case 1:
/*  318 */         this.colorModel = new DirectColorModel(24, 16711680, 65280, 255, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  324 */         this.raster = this.colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  331 */         this.colorModel = ColorModel.getRGBdefault();
/*      */         
/*  333 */         this.raster = this.colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  340 */         this
/*      */           
/*  342 */           .colorModel = new DirectColorModel(ColorSpace.getInstance(1000), 32, 16711680, 65280, 255, -16777216, true, 3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  351 */         this.raster = this.colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/*  358 */         this.colorModel = new DirectColorModel(24, 255, 65280, 16711680);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  363 */         this.raster = this.colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/*  370 */         colorSpace = ColorSpace.getInstance(1000);
/*  371 */         arrayOfInt2 = new int[] { 8, 8, 8 };
/*  372 */         arrayOfInt3 = new int[] { 2, 1, 0 };
/*  373 */         this.colorModel = new ComponentColorModel(colorSpace, arrayOfInt2, false, false, 1, 0);
/*      */ 
/*      */         
/*  376 */         this.raster = Raster.createInterleavedRaster(0, paramInt1, paramInt2, paramInt1 * 3, 3, arrayOfInt3, (Point)null);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 6:
/*  385 */         colorSpace = ColorSpace.getInstance(1000);
/*  386 */         arrayOfInt2 = new int[] { 8, 8, 8, 8 };
/*  387 */         arrayOfInt3 = new int[] { 3, 2, 1, 0 };
/*  388 */         this.colorModel = new ComponentColorModel(colorSpace, arrayOfInt2, true, false, 3, 0);
/*      */ 
/*      */         
/*  391 */         this.raster = Raster.createInterleavedRaster(0, paramInt1, paramInt2, paramInt1 * 4, 4, arrayOfInt3, (Point)null);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 7:
/*  400 */         colorSpace = ColorSpace.getInstance(1000);
/*  401 */         arrayOfInt2 = new int[] { 8, 8, 8, 8 };
/*  402 */         arrayOfInt3 = new int[] { 3, 2, 1, 0 };
/*  403 */         this.colorModel = new ComponentColorModel(colorSpace, arrayOfInt2, true, true, 3, 0);
/*      */ 
/*      */         
/*  406 */         this.raster = Raster.createInterleavedRaster(0, paramInt1, paramInt2, paramInt1 * 4, 4, arrayOfInt3, (Point)null);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 10:
/*  415 */         colorSpace = ColorSpace.getInstance(1003);
/*  416 */         arrayOfInt2 = new int[] { 8 };
/*  417 */         this.colorModel = new ComponentColorModel(colorSpace, arrayOfInt2, false, true, 1, 0);
/*      */ 
/*      */         
/*  420 */         this.raster = this.colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 11:
/*  427 */         colorSpace = ColorSpace.getInstance(1003);
/*  428 */         arrayOfInt2 = new int[] { 16 };
/*  429 */         this.colorModel = new ComponentColorModel(colorSpace, arrayOfInt2, false, true, 1, 1);
/*      */ 
/*      */         
/*  432 */         this.raster = this.colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 12:
/*  439 */         arrayOfByte = new byte[] { 0, -1 };
/*      */         
/*  441 */         this.colorModel = new IndexColorModel(1, 2, arrayOfByte, arrayOfByte, arrayOfByte);
/*  442 */         this.raster = Raster.createPackedRaster(0, paramInt1, paramInt2, 1, 1, (Point)null);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 13:
/*  450 */         arrayOfInt1 = new int[256];
/*  451 */         b = 0;
/*  452 */         for (i = 0; i < 256; i += 51) {
/*  453 */           for (byte b1 = 0; b1 < 'Ā'; b1 += 51) {
/*  454 */             for (byte b2 = 0; b2 < 'Ā'; b2 += 51) {
/*  455 */               arrayOfInt1[b++] = i << 16 | b1 << 8 | b2;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  460 */         i = 256 / (256 - b);
/*      */ 
/*      */         
/*  463 */         j = i * 3;
/*  464 */         for (; b < 'Ā'; b++) {
/*  465 */           arrayOfInt1[b] = j << 16 | j << 8 | j;
/*  466 */           j += i;
/*      */         } 
/*      */         
/*  469 */         this.colorModel = new IndexColorModel(8, 256, arrayOfInt1, 0, false, -1, 0);
/*      */         
/*  471 */         this.raster = Raster.createInterleavedRaster(0, paramInt1, paramInt2, 1, null);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 8:
/*  478 */         this.colorModel = new DirectColorModel(16, 63488, 2016, 31);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  483 */         this.raster = this.colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 9:
/*  490 */         this.colorModel = new DirectColorModel(15, 31744, 992, 31);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  495 */         this.raster = this.colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       default:
/*  501 */         throw new IllegalArgumentException("Unknown image type " + paramInt3);
/*      */     } 
/*      */ 
/*      */     
/*  505 */     this.imageType = paramInt3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BufferedImage(int paramInt1, int paramInt2, int paramInt3, IndexColorModel paramIndexColorModel) {
/*      */     byte b;
/*      */     int i;
/*      */     this.imageType = 0;
/*  535 */     if (paramIndexColorModel.hasAlpha() && paramIndexColorModel.isAlphaPremultiplied()) {
/*  536 */       throw new IllegalArgumentException("This image types do not have premultiplied alpha.");
/*      */     }
/*      */ 
/*      */     
/*  540 */     switch (paramInt3) {
/*      */       
/*      */       case 12:
/*  543 */         i = paramIndexColorModel.getMapSize();
/*  544 */         if (i <= 2) {
/*  545 */           b = 1;
/*  546 */         } else if (i <= 4) {
/*  547 */           b = 2;
/*  548 */         } else if (i <= 16) {
/*  549 */           b = 4;
/*      */         } else {
/*  551 */           throw new IllegalArgumentException("Color map for TYPE_BYTE_BINARY must have no more than 16 entries");
/*      */         } 
/*      */ 
/*      */         
/*  555 */         this.raster = Raster.createPackedRaster(0, paramInt1, paramInt2, 1, b, (Point)null);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 13:
/*  560 */         this.raster = Raster.createInterleavedRaster(0, paramInt1, paramInt2, 1, null);
/*      */         break;
/*      */       
/*      */       default:
/*  564 */         throw new IllegalArgumentException("Invalid image type (" + paramInt3 + ").  Image type must be either TYPE_BYTE_BINARY or  TYPE_BYTE_INDEXED");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  570 */     if (!paramIndexColorModel.isCompatibleRaster(this.raster)) {
/*  571 */       throw new IllegalArgumentException("Incompatible image type and IndexColorModel");
/*      */     }
/*      */     
/*  574 */     this.colorModel = paramIndexColorModel;
/*  575 */     this.imageType = paramInt3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BufferedImage(ColorModel paramColorModel, WritableRaster paramWritableRaster, boolean paramBoolean, Hashtable<?, ?> paramHashtable) {
/*      */     this.imageType = 0;
/*  621 */     if (!paramColorModel.isCompatibleRaster(paramWritableRaster)) {
/*  622 */       throw new IllegalArgumentException("Raster " + paramWritableRaster + " is incompatible with ColorModel " + paramColorModel);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  628 */     if (paramWritableRaster.minX != 0 || paramWritableRaster.minY != 0) {
/*  629 */       throw new IllegalArgumentException("Raster " + paramWritableRaster + " has minX or minY not equal to zero: " + paramWritableRaster.minX + " " + paramWritableRaster.minY);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  635 */     this.colorModel = paramColorModel;
/*  636 */     this.raster = paramWritableRaster;
/*  637 */     if (paramHashtable != null && !paramHashtable.isEmpty()) {
/*  638 */       this.properties = new Hashtable<>();
/*  639 */       for (String str : paramHashtable.keySet()) {
/*  640 */         if (str instanceof String) {
/*  641 */           this.properties.put(str, paramHashtable.get(str));
/*      */         }
/*      */       } 
/*      */     } 
/*  645 */     int i = paramWritableRaster.getNumBands();
/*  646 */     boolean bool1 = paramColorModel.isAlphaPremultiplied();
/*  647 */     boolean bool2 = isStandard(paramColorModel, paramWritableRaster);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  652 */     coerceData(paramBoolean);
/*      */     
/*  654 */     SampleModel sampleModel = paramWritableRaster.getSampleModel();
/*  655 */     ColorSpace colorSpace = paramColorModel.getColorSpace();
/*  656 */     int j = colorSpace.getType();
/*  657 */     if (j != 5) {
/*  658 */       if (j == 6 && bool2 && paramColorModel instanceof ComponentColorModel) {
/*      */ 
/*      */ 
/*      */         
/*  662 */         if (sampleModel instanceof ComponentSampleModel && ((ComponentSampleModel)sampleModel)
/*  663 */           .getPixelStride() != i) {
/*  664 */           this.imageType = 0;
/*  665 */         } else if (paramWritableRaster instanceof ByteComponentRaster && paramWritableRaster
/*  666 */           .getNumBands() == 1 && paramColorModel
/*  667 */           .getComponentSize(0) == 8 && ((ByteComponentRaster)paramWritableRaster)
/*  668 */           .getPixelStride() == 1) {
/*  669 */           this.imageType = 10;
/*  670 */         } else if (paramWritableRaster instanceof ShortComponentRaster && paramWritableRaster
/*  671 */           .getNumBands() == 1 && paramColorModel
/*  672 */           .getComponentSize(0) == 16 && ((ShortComponentRaster)paramWritableRaster)
/*  673 */           .getPixelStride() == 1) {
/*  674 */           this.imageType = 11;
/*      */         } 
/*      */       } else {
/*  677 */         this.imageType = 0;
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*  682 */     if (paramWritableRaster instanceof IntegerComponentRaster && (i == 3 || i == 4)) {
/*      */       
/*  684 */       IntegerComponentRaster integerComponentRaster = (IntegerComponentRaster)paramWritableRaster;
/*      */ 
/*      */ 
/*      */       
/*  688 */       int k = paramColorModel.getPixelSize();
/*  689 */       if (integerComponentRaster.getPixelStride() == 1 && bool2 && paramColorModel instanceof DirectColorModel && (k == 32 || k == 24)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  695 */         DirectColorModel directColorModel = (DirectColorModel)paramColorModel;
/*  696 */         int m = directColorModel.getRedMask();
/*  697 */         int n = directColorModel.getGreenMask();
/*  698 */         int i1 = directColorModel.getBlueMask();
/*  699 */         if (m == 16711680 && n == 65280 && i1 == 255)
/*      */         {
/*      */           
/*  702 */           if (directColorModel.getAlphaMask() == -16777216) {
/*  703 */             this.imageType = bool1 ? 3 : 2;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*  709 */           else if (!directColorModel.hasAlpha()) {
/*  710 */             this.imageType = 1;
/*      */           }
/*      */         
/*      */         }
/*  714 */         else if (m == 255 && n == 65280 && i1 == 16711680)
/*      */         {
/*  716 */           if (!directColorModel.hasAlpha()) {
/*  717 */             this.imageType = 4;
/*      */           }
/*      */         }
/*      */       
/*      */       } 
/*  722 */     } else if (paramColorModel instanceof IndexColorModel && i == 1 && bool2 && (
/*      */       
/*  724 */       !paramColorModel.hasAlpha() || !bool1)) {
/*      */       
/*  726 */       IndexColorModel indexColorModel = (IndexColorModel)paramColorModel;
/*  727 */       int k = indexColorModel.getPixelSize();
/*      */       
/*  729 */       if (paramWritableRaster instanceof sun.awt.image.BytePackedRaster) {
/*  730 */         this.imageType = 12;
/*      */       }
/*  732 */       else if (paramWritableRaster instanceof ByteComponentRaster) {
/*  733 */         ByteComponentRaster byteComponentRaster = (ByteComponentRaster)paramWritableRaster;
/*  734 */         if (byteComponentRaster.getPixelStride() == 1 && k <= 8) {
/*  735 */           this.imageType = 13;
/*      */         }
/*      */       }
/*      */     
/*  739 */     } else if (paramWritableRaster instanceof ShortComponentRaster && paramColorModel instanceof DirectColorModel && bool2 && i == 3 && 
/*      */ 
/*      */ 
/*      */       
/*  743 */       !paramColorModel.hasAlpha()) {
/*      */       
/*  745 */       DirectColorModel directColorModel = (DirectColorModel)paramColorModel;
/*  746 */       if (directColorModel.getRedMask() == 63488) {
/*  747 */         if (directColorModel.getGreenMask() == 2016 && directColorModel
/*  748 */           .getBlueMask() == 31) {
/*  749 */           this.imageType = 8;
/*      */         }
/*      */       }
/*  752 */       else if (directColorModel.getRedMask() == 31744 && 
/*  753 */         directColorModel.getGreenMask() == 992 && directColorModel
/*  754 */         .getBlueMask() == 31) {
/*  755 */         this.imageType = 9;
/*      */       }
/*      */     
/*      */     }
/*  759 */     else if (paramWritableRaster instanceof ByteComponentRaster && paramColorModel instanceof ComponentColorModel && bool2 && paramWritableRaster
/*      */ 
/*      */       
/*  762 */       .getSampleModel() instanceof PixelInterleavedSampleModel && (i == 3 || i == 4)) {
/*      */ 
/*      */       
/*  765 */       ComponentColorModel componentColorModel = (ComponentColorModel)paramColorModel;
/*      */       
/*  767 */       PixelInterleavedSampleModel pixelInterleavedSampleModel = (PixelInterleavedSampleModel)paramWritableRaster.getSampleModel();
/*  768 */       ByteComponentRaster byteComponentRaster = (ByteComponentRaster)paramWritableRaster;
/*  769 */       int[] arrayOfInt1 = pixelInterleavedSampleModel.getBandOffsets();
/*  770 */       if (componentColorModel.getNumComponents() != i) {
/*  771 */         throw new RasterFormatException("Number of components in ColorModel (" + componentColorModel
/*      */             
/*  773 */             .getNumComponents() + ") does not match # in  Raster (" + i + ")");
/*      */       }
/*      */ 
/*      */       
/*  777 */       int[] arrayOfInt2 = componentColorModel.getComponentSize();
/*  778 */       boolean bool = true;
/*  779 */       for (byte b = 0; b < i; b++) {
/*  780 */         if (arrayOfInt2[b] != 8) {
/*  781 */           bool = false;
/*      */           break;
/*      */         } 
/*      */       } 
/*  785 */       if (bool && byteComponentRaster
/*  786 */         .getPixelStride() == i && arrayOfInt1[0] == i - 1 && arrayOfInt1[1] == i - 2 && arrayOfInt1[2] == i - 3)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  791 */         if (i == 3 && !componentColorModel.hasAlpha()) {
/*  792 */           this.imageType = 5;
/*      */         }
/*  794 */         else if (arrayOfInt1[3] == 0 && componentColorModel.hasAlpha()) {
/*  795 */           this.imageType = bool1 ? 7 : 6;
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isStandard(ColorModel paramColorModel, WritableRaster paramWritableRaster) {
/*  804 */     final Class<?> cmClass = paramColorModel.getClass();
/*  805 */     final Class<?> wrClass = paramWritableRaster.getClass();
/*  806 */     final Class<?> smClass = paramWritableRaster.getSampleModel().getClass();
/*      */     
/*  808 */     PrivilegedAction<Boolean> privilegedAction = new PrivilegedAction<Boolean>()
/*      */       {
/*      */ 
/*      */         
/*      */         public Boolean run()
/*      */         {
/*  814 */           ClassLoader classLoader = System.class.getClassLoader();
/*      */           
/*  816 */           return Boolean.valueOf((cmClass.getClassLoader() == classLoader && smClass
/*  817 */               .getClassLoader() == classLoader && wrClass
/*  818 */               .getClassLoader() == classLoader));
/*      */         }
/*      */       };
/*  821 */     return ((Boolean)AccessController.<Boolean>doPrivileged(privilegedAction)).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getType() {
/*  844 */     return this.imageType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ColorModel getColorModel() {
/*  853 */     return this.colorModel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WritableRaster getRaster() {
/*  862 */     return this.raster;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WritableRaster getAlphaRaster() {
/*  888 */     return this.colorModel.getAlphaRaster(this.raster);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRGB(int paramInt1, int paramInt2) {
/*  917 */     return this.colorModel.getRGB(this.raster.getDataElements(paramInt1, paramInt2, null));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getRGB(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, int paramInt5, int paramInt6) {
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/*      */     int[] arrayOfInt;
/*      */     float[] arrayOfFloat;
/*      */     double[] arrayOfDouble;
/*  953 */     int i = paramInt5;
/*      */ 
/*      */     
/*  956 */     int j = this.raster.getNumBands();
/*  957 */     int k = this.raster.getDataBuffer().getDataType();
/*  958 */     switch (k) {
/*      */       case 0:
/*  960 */         arrayOfByte = new byte[j];
/*      */         break;
/*      */       case 1:
/*  963 */         arrayOfShort = new short[j];
/*      */         break;
/*      */       case 3:
/*  966 */         arrayOfInt = new int[j];
/*      */         break;
/*      */       case 4:
/*  969 */         arrayOfFloat = new float[j];
/*      */         break;
/*      */       case 5:
/*  972 */         arrayOfDouble = new double[j];
/*      */         break;
/*      */       default:
/*  975 */         throw new IllegalArgumentException("Unknown data buffer type: " + k);
/*      */     } 
/*      */ 
/*      */     
/*  979 */     if (paramArrayOfint == null) {
/*  980 */       paramArrayOfint = new int[paramInt5 + paramInt4 * paramInt6];
/*      */     }
/*      */     
/*  983 */     for (int m = paramInt2; m < paramInt2 + paramInt4; m++, i += paramInt6) {
/*  984 */       int n = i;
/*  985 */       for (int i1 = paramInt1; i1 < paramInt1 + paramInt3; i1++) {
/*  986 */         paramArrayOfint[n++] = this.colorModel.getRGB(this.raster.getDataElements(i1, m, arrayOfDouble));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  992 */     return paramArrayOfint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void setRGB(int paramInt1, int paramInt2, int paramInt3) {
/* 1016 */     this.raster.setDataElements(paramInt1, paramInt2, this.colorModel.getDataElements(paramInt3, null));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRGB(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, int paramInt5, int paramInt6) {
/* 1051 */     int i = paramInt5;
/*      */     
/* 1053 */     Object object = null;
/*      */     
/* 1055 */     for (int j = paramInt2; j < paramInt2 + paramInt4; j++, i += paramInt6) {
/* 1056 */       int k = i;
/* 1057 */       for (int m = paramInt1; m < paramInt1 + paramInt3; m++) {
/* 1058 */         object = this.colorModel.getDataElements(paramArrayOfint[k++], object);
/* 1059 */         this.raster.setDataElements(m, j, object);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getWidth() {
/* 1070 */     return this.raster.getWidth();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeight() {
/* 1078 */     return this.raster.getHeight();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getWidth(ImageObserver paramImageObserver) {
/* 1087 */     return this.raster.getWidth();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeight(ImageObserver paramImageObserver) {
/* 1096 */     return this.raster.getHeight();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ImageProducer getSource() {
/* 1106 */     if (this.osis == null) {
/* 1107 */       if (this.properties == null) {
/* 1108 */         this.properties = new Hashtable<>();
/*      */       }
/* 1110 */       this.osis = new OffScreenImageSource(this, this.properties);
/*      */     } 
/* 1112 */     return this.osis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getProperty(String paramString, ImageObserver paramImageObserver) {
/* 1137 */     return getProperty(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getProperty(String paramString) {
/* 1148 */     if (paramString == null) {
/* 1149 */       throw new NullPointerException("null property name is not allowed");
/*      */     }
/* 1151 */     if (this.properties == null) {
/* 1152 */       return Image.UndefinedProperty;
/*      */     }
/* 1154 */     Object object = this.properties.get(paramString);
/* 1155 */     if (object == null) {
/* 1156 */       object = Image.UndefinedProperty;
/*      */     }
/* 1158 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Graphics getGraphics() {
/* 1170 */     return createGraphics();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Graphics2D createGraphics() {
/* 1181 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 1182 */     return graphicsEnvironment.createGraphics(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BufferedImage getSubimage(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1201 */     return new BufferedImage(this.colorModel, this.raster
/* 1202 */         .createWritableChild(paramInt1, paramInt2, paramInt3, paramInt4, 0, 0, (int[])null), this.colorModel
/*      */         
/* 1204 */         .isAlphaPremultiplied(), this.properties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAlphaPremultiplied() {
/* 1215 */     return this.colorModel.isAlphaPremultiplied();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void coerceData(boolean paramBoolean) {
/* 1227 */     if (this.colorModel.hasAlpha() && this.colorModel
/* 1228 */       .isAlphaPremultiplied() != paramBoolean)
/*      */     {
/* 1230 */       this.colorModel = this.colorModel.coerceData(this.raster, paramBoolean);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1241 */     return "BufferedImage@" + Integer.toHexString(hashCode()) + ": type = " + this.imageType + " " + this.colorModel + " " + this.raster;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector<RenderedImage> getSources() {
/* 1262 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getPropertyNames() {
/* 1274 */     if (this.properties == null || this.properties.isEmpty()) {
/* 1275 */       return null;
/*      */     }
/* 1277 */     Set<String> set = this.properties.keySet();
/* 1278 */     return set.<String>toArray(new String[set.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinX() {
/* 1288 */     return this.raster.getMinX();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinY() {
/* 1298 */     return this.raster.getMinY();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SampleModel getSampleModel() {
/* 1308 */     return this.raster.getSampleModel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumXTiles() {
/* 1317 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumYTiles() {
/* 1326 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinTileX() {
/* 1335 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinTileY() {
/* 1344 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTileWidth() {
/* 1352 */     return this.raster.getWidth();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTileHeight() {
/* 1360 */     return this.raster.getHeight();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTileGridXOffset() {
/* 1370 */     return this.raster.getSampleModelTranslateX();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTileGridYOffset() {
/* 1380 */     return this.raster.getSampleModelTranslateY();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Raster getTile(int paramInt1, int paramInt2) {
/* 1398 */     if (paramInt1 == 0 && paramInt2 == 0) {
/* 1399 */       return this.raster;
/*      */     }
/* 1401 */     throw new ArrayIndexOutOfBoundsException("BufferedImages only have one tile with index 0,0");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Raster getData() {
/* 1417 */     int i = this.raster.getWidth();
/* 1418 */     int j = this.raster.getHeight();
/* 1419 */     int k = this.raster.getMinX();
/* 1420 */     int m = this.raster.getMinY();
/*      */     
/* 1422 */     WritableRaster writableRaster = Raster.createWritableRaster(this.raster.getSampleModel(), new Point(this.raster
/* 1423 */           .getSampleModelTranslateX(), this.raster
/* 1424 */           .getSampleModelTranslateY()));
/*      */     
/* 1426 */     Object object = null;
/*      */     
/* 1428 */     for (int n = m; n < m + j; n++) {
/* 1429 */       object = this.raster.getDataElements(k, n, i, 1, object);
/* 1430 */       writableRaster.setDataElements(k, n, i, 1, object);
/*      */     } 
/* 1432 */     return writableRaster;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Raster getData(Rectangle paramRectangle) {
/* 1447 */     SampleModel sampleModel1 = this.raster.getSampleModel();
/* 1448 */     SampleModel sampleModel2 = sampleModel1.createCompatibleSampleModel(paramRectangle.width, paramRectangle.height);
/*      */     
/* 1450 */     WritableRaster writableRaster = Raster.createWritableRaster(sampleModel2, paramRectangle
/* 1451 */         .getLocation());
/* 1452 */     int i = paramRectangle.width;
/* 1453 */     int j = paramRectangle.height;
/* 1454 */     int k = paramRectangle.x;
/* 1455 */     int m = paramRectangle.y;
/*      */     
/* 1457 */     Object object = null;
/*      */     
/* 1459 */     for (int n = m; n < m + j; n++) {
/* 1460 */       object = this.raster.getDataElements(k, n, i, 1, object);
/* 1461 */       writableRaster.setDataElements(k, n, i, 1, object);
/*      */     } 
/* 1463 */     return writableRaster;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WritableRaster copyData(WritableRaster paramWritableRaster) {
/* 1482 */     if (paramWritableRaster == null) {
/* 1483 */       return (WritableRaster)getData();
/*      */     }
/* 1485 */     int i = paramWritableRaster.getWidth();
/* 1486 */     int j = paramWritableRaster.getHeight();
/* 1487 */     int k = paramWritableRaster.getMinX();
/* 1488 */     int m = paramWritableRaster.getMinY();
/*      */     
/* 1490 */     Object object = null;
/*      */     
/* 1492 */     for (int n = m; n < m + j; n++) {
/* 1493 */       object = this.raster.getDataElements(k, n, i, 1, object);
/* 1494 */       paramWritableRaster.setDataElements(k, n, i, 1, object);
/*      */     } 
/*      */     
/* 1497 */     return paramWritableRaster;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setData(Raster paramRaster) {
/* 1511 */     int i = paramRaster.getWidth();
/* 1512 */     int j = paramRaster.getHeight();
/* 1513 */     int k = paramRaster.getMinX();
/* 1514 */     int m = paramRaster.getMinY();
/*      */     
/* 1516 */     int[] arrayOfInt = null;
/*      */ 
/*      */     
/* 1519 */     Rectangle rectangle1 = new Rectangle(k, m, i, j);
/* 1520 */     Rectangle rectangle2 = new Rectangle(0, 0, this.raster.width, this.raster.height);
/* 1521 */     Rectangle rectangle3 = rectangle1.intersection(rectangle2);
/* 1522 */     if (rectangle3.isEmpty()) {
/*      */       return;
/*      */     }
/* 1525 */     i = rectangle3.width;
/* 1526 */     j = rectangle3.height;
/* 1527 */     k = rectangle3.x;
/* 1528 */     m = rectangle3.y;
/*      */ 
/*      */ 
/*      */     
/* 1532 */     for (int n = m; n < m + j; n++) {
/* 1533 */       arrayOfInt = paramRaster.getPixels(k, n, i, 1, arrayOfInt);
/* 1534 */       this.raster.setPixels(k, n, i, 1, arrayOfInt);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTileObserver(TileObserver paramTileObserver) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeTileObserver(TileObserver paramTileObserver) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTileWritable(int paramInt1, int paramInt2) {
/* 1568 */     if (paramInt1 == 0 && paramInt2 == 0) {
/* 1569 */       return true;
/*      */     }
/* 1571 */     throw new IllegalArgumentException("Only 1 tile in image");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point[] getWritableTileIndices() {
/* 1583 */     Point[] arrayOfPoint = new Point[1];
/* 1584 */     arrayOfPoint[0] = new Point(0, 0);
/*      */     
/* 1586 */     return arrayOfPoint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasTileWriters() {
/* 1599 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WritableRaster getWritableTile(int paramInt1, int paramInt2) {
/* 1612 */     return this.raster;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void releaseWritableTile(int paramInt1, int paramInt2) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTransparency() {
/* 1639 */     return this.colorModel.getTransparency();
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/BufferedImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */