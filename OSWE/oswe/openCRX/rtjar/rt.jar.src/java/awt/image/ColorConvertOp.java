/*      */ package java.awt.image;
/*      */ 
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Point;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.awt.color.ICC_ColorSpace;
/*      */ import java.awt.color.ICC_Profile;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import sun.java2d.cmm.CMSManager;
/*      */ import sun.java2d.cmm.ColorTransform;
/*      */ import sun.java2d.cmm.PCMM;
/*      */ import sun.java2d.cmm.ProfileDeferralMgr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ColorConvertOp
/*      */   implements BufferedImageOp, RasterOp
/*      */ {
/*      */   ICC_Profile[] profileList;
/*      */   ColorSpace[] CSList;
/*      */   ColorTransform thisTransform;
/*      */   ColorTransform thisRasterTransform;
/*      */   ICC_Profile thisSrcProfile;
/*      */   ICC_Profile thisDestProfile;
/*      */   RenderingHints hints;
/*      */   boolean gotProfiles;
/*      */   float[] srcMinVals;
/*      */   float[] srcMaxVals;
/*      */   float[] dstMinVals;
/*      */   float[] dstMaxVals;
/*      */   
/*      */   static {
/*   82 */     if (ProfileDeferralMgr.deferring) {
/*   83 */       ProfileDeferralMgr.activateProfiles();
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
/*      */ 
/*      */ 
/*      */   
/*      */   public ColorConvertOp(RenderingHints paramRenderingHints) {
/*  100 */     this.profileList = new ICC_Profile[0];
/*  101 */     this.hints = paramRenderingHints;
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
/*      */   public ColorConvertOp(ColorSpace paramColorSpace, RenderingHints paramRenderingHints) {
/*  122 */     if (paramColorSpace == null) {
/*  123 */       throw new NullPointerException("ColorSpace cannot be null");
/*      */     }
/*  125 */     if (paramColorSpace instanceof ICC_ColorSpace) {
/*  126 */       this.profileList = new ICC_Profile[1];
/*      */       
/*  128 */       this.profileList[0] = ((ICC_ColorSpace)paramColorSpace).getProfile();
/*      */     } else {
/*      */       
/*  131 */       this.CSList = new ColorSpace[1];
/*  132 */       this.CSList[0] = paramColorSpace;
/*      */     } 
/*  134 */     this.hints = paramRenderingHints;
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
/*      */   public ColorConvertOp(ColorSpace paramColorSpace1, ColorSpace paramColorSpace2, RenderingHints paramRenderingHints) {
/*  158 */     if (paramColorSpace1 == null || paramColorSpace2 == null) {
/*  159 */       throw new NullPointerException("ColorSpaces cannot be null");
/*      */     }
/*  161 */     if (paramColorSpace1 instanceof ICC_ColorSpace && paramColorSpace2 instanceof ICC_ColorSpace) {
/*      */       
/*  163 */       this.profileList = new ICC_Profile[2];
/*      */       
/*  165 */       this.profileList[0] = ((ICC_ColorSpace)paramColorSpace1).getProfile();
/*  166 */       this.profileList[1] = ((ICC_ColorSpace)paramColorSpace2).getProfile();
/*      */       
/*  168 */       getMinMaxValsFromColorSpaces(paramColorSpace1, paramColorSpace2);
/*      */     } else {
/*      */       
/*  171 */       this.CSList = new ColorSpace[2];
/*  172 */       this.CSList[0] = paramColorSpace1;
/*  173 */       this.CSList[1] = paramColorSpace2;
/*      */     } 
/*  175 */     this.hints = paramRenderingHints;
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
/*      */   public ColorConvertOp(ICC_Profile[] paramArrayOfICC_Profile, RenderingHints paramRenderingHints) {
/*  208 */     if (paramArrayOfICC_Profile == null) {
/*  209 */       throw new NullPointerException("Profiles cannot be null");
/*      */     }
/*  211 */     this.gotProfiles = true;
/*  212 */     this.profileList = new ICC_Profile[paramArrayOfICC_Profile.length];
/*  213 */     for (byte b = 0; b < paramArrayOfICC_Profile.length; b++) {
/*  214 */       this.profileList[b] = paramArrayOfICC_Profile[b];
/*      */     }
/*  216 */     this.hints = paramRenderingHints;
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
/*      */   public final ICC_Profile[] getICC_Profiles() {
/*  230 */     if (this.gotProfiles) {
/*  231 */       ICC_Profile[] arrayOfICC_Profile = new ICC_Profile[this.profileList.length];
/*  232 */       for (byte b = 0; b < this.profileList.length; b++) {
/*  233 */         arrayOfICC_Profile[b] = this.profileList[b];
/*      */       }
/*  235 */       return arrayOfICC_Profile;
/*      */     } 
/*  237 */     return null;
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
/*      */   public final BufferedImage filter(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2) {
/*      */     ColorSpace colorSpace2;
/*  256 */     BufferedImage bufferedImage = null;
/*      */     
/*  258 */     if (paramBufferedImage1.getColorModel() instanceof IndexColorModel) {
/*  259 */       IndexColorModel indexColorModel = (IndexColorModel)paramBufferedImage1.getColorModel();
/*  260 */       paramBufferedImage1 = indexColorModel.convertToIntDiscrete(paramBufferedImage1.getRaster(), true);
/*      */     } 
/*  262 */     ColorSpace colorSpace1 = paramBufferedImage1.getColorModel().getColorSpace();
/*  263 */     if (paramBufferedImage2 != null) {
/*  264 */       if (paramBufferedImage2.getColorModel() instanceof IndexColorModel) {
/*  265 */         bufferedImage = paramBufferedImage2;
/*  266 */         paramBufferedImage2 = null;
/*  267 */         colorSpace2 = null;
/*      */       } else {
/*  269 */         colorSpace2 = paramBufferedImage2.getColorModel().getColorSpace();
/*      */       } 
/*      */     } else {
/*  272 */       colorSpace2 = null;
/*      */     } 
/*      */     
/*  275 */     if (this.CSList != null || !(colorSpace1 instanceof ICC_ColorSpace) || (paramBufferedImage2 != null && !(colorSpace2 instanceof ICC_ColorSpace))) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  280 */       paramBufferedImage2 = nonICCBIFilter(paramBufferedImage1, colorSpace1, paramBufferedImage2, colorSpace2);
/*      */     } else {
/*  282 */       paramBufferedImage2 = ICCBIFilter(paramBufferedImage1, colorSpace1, paramBufferedImage2, colorSpace2);
/*      */     } 
/*      */     
/*  285 */     if (bufferedImage != null) {
/*  286 */       Graphics2D graphics2D = bufferedImage.createGraphics();
/*      */       try {
/*  288 */         graphics2D.drawImage(paramBufferedImage2, 0, 0, (ImageObserver)null);
/*      */       } finally {
/*  290 */         graphics2D.dispose();
/*      */       } 
/*  292 */       return bufferedImage;
/*      */     } 
/*  294 */     return paramBufferedImage2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final BufferedImage ICCBIFilter(BufferedImage paramBufferedImage1, ColorSpace paramColorSpace1, BufferedImage paramBufferedImage2, ColorSpace paramColorSpace2) {
/*  302 */     int i = this.profileList.length;
/*  303 */     ICC_Profile iCC_Profile1 = null, iCC_Profile2 = null;
/*      */     
/*  305 */     iCC_Profile1 = ((ICC_ColorSpace)paramColorSpace1).getProfile();
/*      */     
/*  307 */     if (paramBufferedImage2 == null) {
/*      */       
/*  309 */       if (i == 0) {
/*  310 */         throw new IllegalArgumentException("Destination ColorSpace is undefined");
/*      */       }
/*      */       
/*  313 */       iCC_Profile2 = this.profileList[i - 1];
/*  314 */       paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/*      */     } else {
/*      */       
/*  317 */       if (paramBufferedImage1.getHeight() != paramBufferedImage2.getHeight() || paramBufferedImage1
/*  318 */         .getWidth() != paramBufferedImage2.getWidth()) {
/*  319 */         throw new IllegalArgumentException("Width or height of BufferedImages do not match");
/*      */       }
/*      */       
/*  322 */       iCC_Profile2 = ((ICC_ColorSpace)paramColorSpace2).getProfile();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  328 */     if (iCC_Profile1 == iCC_Profile2) {
/*  329 */       boolean bool = true;
/*  330 */       for (byte b = 0; b < i; b++) {
/*  331 */         if (iCC_Profile1 != this.profileList[b]) {
/*  332 */           bool = false;
/*      */           break;
/*      */         } 
/*      */       } 
/*  336 */       if (bool) {
/*  337 */         Graphics2D graphics2D = paramBufferedImage2.createGraphics();
/*      */         try {
/*  339 */           graphics2D.drawImage(paramBufferedImage1, 0, 0, (ImageObserver)null);
/*      */         } finally {
/*  341 */           graphics2D.dispose();
/*      */         } 
/*      */         
/*  344 */         return paramBufferedImage2;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  349 */     if (this.thisTransform == null || this.thisSrcProfile != iCC_Profile1 || this.thisDestProfile != iCC_Profile2)
/*      */     {
/*  351 */       updateBITransform(iCC_Profile1, iCC_Profile2);
/*      */     }
/*      */ 
/*      */     
/*  355 */     this.thisTransform.colorConvert(paramBufferedImage1, paramBufferedImage2);
/*      */     
/*  357 */     return paramBufferedImage2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateBITransform(ICC_Profile paramICC_Profile1, ICC_Profile paramICC_Profile2) {
/*  365 */     boolean bool1 = false, bool2 = false;
/*      */     
/*  367 */     int i = this.profileList.length;
/*  368 */     int j = i;
/*  369 */     if (i == 0 || paramICC_Profile1 != this.profileList[0]) {
/*  370 */       j++;
/*  371 */       bool1 = true;
/*      */     } 
/*  373 */     if (i == 0 || paramICC_Profile2 != this.profileList[i - 1] || j < 2) {
/*      */       
/*  375 */       j++;
/*  376 */       bool2 = true;
/*      */     } 
/*      */ 
/*      */     
/*  380 */     ICC_Profile[] arrayOfICC_Profile = new ICC_Profile[j];
/*      */ 
/*      */     
/*  383 */     byte b3 = 0;
/*  384 */     if (bool1)
/*      */     {
/*  386 */       arrayOfICC_Profile[b3++] = paramICC_Profile1;
/*      */     }
/*      */     byte b1;
/*  389 */     for (b1 = 0; b1 < i; b1++)
/*      */     {
/*  391 */       arrayOfICC_Profile[b3++] = this.profileList[b1];
/*      */     }
/*      */     
/*  394 */     if (bool2)
/*      */     {
/*  396 */       arrayOfICC_Profile[b3] = paramICC_Profile2;
/*      */     }
/*      */ 
/*      */     
/*  400 */     ColorTransform[] arrayOfColorTransform = new ColorTransform[j];
/*      */ 
/*      */     
/*  403 */     if (arrayOfICC_Profile[0].getProfileClass() == 2) {
/*      */ 
/*      */       
/*  406 */       boolean bool = true;
/*      */     } else {
/*      */       
/*  409 */       boolean bool = false;
/*      */     } 
/*      */ 
/*      */     
/*  413 */     byte b2 = 1;
/*      */     
/*  415 */     PCMM pCMM = CMSManager.getModule();
/*      */ 
/*      */     
/*  418 */     for (b1 = 0; b1 < j; b1++) {
/*  419 */       if (b1 == j - 1) {
/*  420 */         b2 = 2;
/*      */       
/*      */       }
/*  423 */       else if (b2 == 4 && arrayOfICC_Profile[b1]
/*  424 */         .getProfileClass() == 5) {
/*      */         
/*  426 */         k = 0;
/*  427 */         b2 = 1;
/*      */       } 
/*      */ 
/*      */       
/*  431 */       arrayOfColorTransform[b1] = pCMM.createTransform(arrayOfICC_Profile[b1], k, b2);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  436 */       int k = getRenderingIntent(arrayOfICC_Profile[b1]);
/*      */ 
/*      */       
/*  439 */       b2 = 4;
/*      */     } 
/*      */ 
/*      */     
/*  443 */     this.thisTransform = pCMM.createTransform(arrayOfColorTransform);
/*      */ 
/*      */     
/*  446 */     this.thisSrcProfile = paramICC_Profile1;
/*  447 */     this.thisDestProfile = paramICC_Profile2;
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
/*      */   public final WritableRaster filter(Raster paramRaster, WritableRaster paramWritableRaster) {
/*  472 */     if (this.CSList != null)
/*      */     {
/*  474 */       return nonICCRasterFilter(paramRaster, paramWritableRaster);
/*      */     }
/*  476 */     int i = this.profileList.length;
/*  477 */     if (i < 2) {
/*  478 */       throw new IllegalArgumentException("Source or Destination ColorSpace is undefined");
/*      */     }
/*      */     
/*  481 */     if (paramRaster.getNumBands() != this.profileList[0].getNumComponents()) {
/*  482 */       throw new IllegalArgumentException("Numbers of source Raster bands and source color space components do not match");
/*      */     }
/*      */ 
/*      */     
/*  486 */     if (paramWritableRaster == null) {
/*  487 */       paramWritableRaster = createCompatibleDestRaster(paramRaster);
/*      */     } else {
/*      */       
/*  490 */       if (paramRaster.getHeight() != paramWritableRaster.getHeight() || paramRaster
/*  491 */         .getWidth() != paramWritableRaster.getWidth()) {
/*  492 */         throw new IllegalArgumentException("Width or height of Rasters do not match");
/*      */       }
/*      */       
/*  495 */       if (paramWritableRaster.getNumBands() != this.profileList[i - 1]
/*  496 */         .getNumComponents()) {
/*  497 */         throw new IllegalArgumentException("Numbers of destination Raster bands and destination color space components do not match");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  504 */     if (this.thisRasterTransform == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  509 */       ColorTransform[] arrayOfColorTransform = new ColorTransform[i];
/*      */ 
/*      */       
/*  512 */       if (this.profileList[0].getProfileClass() == 2) {
/*      */ 
/*      */         
/*  515 */         boolean bool = true;
/*      */       } else {
/*      */         
/*  518 */         boolean bool = false;
/*      */       } 
/*      */ 
/*      */       
/*  522 */       byte b2 = 1;
/*      */       
/*  524 */       PCMM pCMM = CMSManager.getModule();
/*      */ 
/*      */       
/*  527 */       for (byte b1 = 0; b1 < i; b1++) {
/*  528 */         if (b1 == i - 1) {
/*  529 */           b2 = 2;
/*      */         
/*      */         }
/*  532 */         else if (b2 == 4 && this.profileList[b1]
/*  533 */           .getProfileClass() == 5) {
/*      */           
/*  535 */           m = 0;
/*  536 */           b2 = 1;
/*      */         } 
/*      */ 
/*      */         
/*  540 */         arrayOfColorTransform[b1] = pCMM.createTransform(this.profileList[b1], m, b2);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  545 */         int m = getRenderingIntent(this.profileList[b1]);
/*      */ 
/*      */         
/*  548 */         b2 = 4;
/*      */       } 
/*      */ 
/*      */       
/*  552 */       this.thisRasterTransform = pCMM.createTransform(arrayOfColorTransform);
/*      */     } 
/*      */     
/*  555 */     int j = paramRaster.getTransferType();
/*  556 */     int k = paramWritableRaster.getTransferType();
/*  557 */     if (j == 4 || j == 5 || k == 4 || k == 5) {
/*      */ 
/*      */ 
/*      */       
/*  561 */       if (this.srcMinVals == null) {
/*  562 */         getMinMaxValsFromProfiles(this.profileList[0], this.profileList[i - 1]);
/*      */       }
/*      */ 
/*      */       
/*  566 */       this.thisRasterTransform.colorConvert(paramRaster, paramWritableRaster, this.srcMinVals, this.srcMaxVals, this.dstMinVals, this.dstMaxVals);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  571 */       this.thisRasterTransform.colorConvert(paramRaster, paramWritableRaster);
/*      */     } 
/*      */ 
/*      */     
/*  575 */     return paramWritableRaster;
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
/*      */   public final Rectangle2D getBounds2D(BufferedImage paramBufferedImage) {
/*  587 */     return getBounds2D(paramBufferedImage.getRaster());
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
/*      */   public final Rectangle2D getBounds2D(Raster paramRaster) {
/*  602 */     return paramRaster.getBounds();
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
/*      */   public BufferedImage createCompatibleDestImage(BufferedImage paramBufferedImage, ColorModel paramColorModel) {
/*  620 */     ColorSpace colorSpace = null;
/*  621 */     if (paramColorModel == null) {
/*  622 */       if (this.CSList == null) {
/*      */         
/*  624 */         int i = this.profileList.length;
/*  625 */         if (i == 0) {
/*  626 */           throw new IllegalArgumentException("Destination ColorSpace is undefined");
/*      */         }
/*      */         
/*  629 */         ICC_Profile iCC_Profile = this.profileList[i - 1];
/*  630 */         colorSpace = new ICC_ColorSpace(iCC_Profile);
/*      */       } else {
/*      */         
/*  633 */         int i = this.CSList.length;
/*  634 */         colorSpace = this.CSList[i - 1];
/*      */       } 
/*      */     }
/*  637 */     return createCompatibleDestImage(paramBufferedImage, paramColorModel, colorSpace);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BufferedImage createCompatibleDestImage(BufferedImage paramBufferedImage, ColorModel paramColorModel, ColorSpace paramColorSpace) {
/*  644 */     if (paramColorModel == null) {
/*  645 */       ColorModel colorModel = paramBufferedImage.getColorModel();
/*  646 */       int k = paramColorSpace.getNumComponents();
/*  647 */       boolean bool = colorModel.hasAlpha();
/*  648 */       if (bool) {
/*  649 */         k++;
/*      */       }
/*  651 */       int[] arrayOfInt = new int[k];
/*  652 */       for (byte b = 0; b < k; b++) {
/*  653 */         arrayOfInt[b] = 8;
/*      */       }
/*      */ 
/*      */       
/*  657 */       paramColorModel = new ComponentColorModel(paramColorSpace, arrayOfInt, bool, colorModel.isAlphaPremultiplied(), colorModel.getTransparency(), 0);
/*      */     } 
/*      */     
/*  660 */     int i = paramBufferedImage.getWidth();
/*  661 */     int j = paramBufferedImage.getHeight();
/*  662 */     return new BufferedImage(paramColorModel, paramColorModel
/*  663 */         .createCompatibleWritableRaster(i, j), paramColorModel
/*  664 */         .isAlphaPremultiplied(), null);
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
/*      */   public WritableRaster createCompatibleDestRaster(Raster paramRaster) {
/*      */     int i;
/*  682 */     if (this.CSList != null) {
/*      */       
/*  684 */       if (this.CSList.length != 2) {
/*  685 */         throw new IllegalArgumentException("Destination ColorSpace is undefined");
/*      */       }
/*      */       
/*  688 */       i = this.CSList[1].getNumComponents();
/*      */     } else {
/*      */       
/*  691 */       int j = this.profileList.length;
/*  692 */       if (j < 2) {
/*  693 */         throw new IllegalArgumentException("Destination ColorSpace is undefined");
/*      */       }
/*      */       
/*  696 */       i = this.profileList[j - 1].getNumComponents();
/*      */     } 
/*      */ 
/*      */     
/*  700 */     return Raster.createInterleavedRaster(0, paramRaster
/*  701 */         .getWidth(), paramRaster
/*  702 */         .getHeight(), i, new Point(paramRaster
/*      */           
/*  704 */           .getMinX(), paramRaster.getMinY()));
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
/*      */   public final Point2D getPoint2D(Point2D paramPoint2D1, Point2D paramPoint2D2) {
/*  720 */     if (paramPoint2D2 == null) {
/*  721 */       paramPoint2D2 = new Point2D.Float();
/*      */     }
/*  723 */     paramPoint2D2.setLocation(paramPoint2D1.getX(), paramPoint2D1.getY());
/*      */     
/*  725 */     return paramPoint2D2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getRenderingIntent(ICC_Profile paramICC_Profile) {
/*  733 */     byte[] arrayOfByte = paramICC_Profile.getData(1751474532);
/*  734 */     byte b = 64;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  743 */     return (arrayOfByte[b + 2] & 0xFF) << 8 | arrayOfByte[b + 3] & 0xFF;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final RenderingHints getRenderingHints() {
/*  753 */     return this.hints;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final BufferedImage nonICCBIFilter(BufferedImage paramBufferedImage1, ColorSpace paramColorSpace1, BufferedImage paramBufferedImage2, ColorSpace paramColorSpace2) {
/*  761 */     int i = paramBufferedImage1.getWidth();
/*  762 */     int j = paramBufferedImage1.getHeight();
/*      */     
/*  764 */     ICC_ColorSpace iCC_ColorSpace = (ICC_ColorSpace)ColorSpace.getInstance(1001);
/*  765 */     if (paramBufferedImage2 == null) {
/*  766 */       paramBufferedImage2 = createCompatibleDestImage(paramBufferedImage1, null);
/*  767 */       paramColorSpace2 = paramBufferedImage2.getColorModel().getColorSpace();
/*      */     }
/*  769 */     else if (j != paramBufferedImage2.getHeight() || i != paramBufferedImage2.getWidth()) {
/*  770 */       throw new IllegalArgumentException("Width or height of BufferedImages do not match");
/*      */     } 
/*      */ 
/*      */     
/*  774 */     WritableRaster writableRaster1 = paramBufferedImage1.getRaster();
/*  775 */     WritableRaster writableRaster2 = paramBufferedImage2.getRaster();
/*  776 */     ColorModel colorModel1 = paramBufferedImage1.getColorModel();
/*  777 */     ColorModel colorModel2 = paramBufferedImage2.getColorModel();
/*  778 */     int k = colorModel1.getNumColorComponents();
/*  779 */     int m = colorModel2.getNumColorComponents();
/*  780 */     boolean bool = colorModel2.hasAlpha();
/*  781 */     boolean bool1 = (colorModel1.hasAlpha() && bool) ? true : false;
/*      */     
/*  783 */     if (this.CSList == null && this.profileList.length != 0) {
/*      */       boolean bool2, bool3; ICC_Profile iCC_Profile1, iCC_Profile2; ColorSpace colorSpace;
/*      */       int n;
/*      */       float[] arrayOfFloat5;
/*  787 */       if (!(paramColorSpace1 instanceof ICC_ColorSpace)) {
/*  788 */         bool2 = true;
/*  789 */         iCC_Profile1 = iCC_ColorSpace.getProfile();
/*      */       } else {
/*  791 */         bool2 = false;
/*  792 */         iCC_Profile1 = ((ICC_ColorSpace)paramColorSpace1).getProfile();
/*      */       } 
/*  794 */       if (!(paramColorSpace2 instanceof ICC_ColorSpace)) {
/*  795 */         bool3 = true;
/*  796 */         iCC_Profile2 = iCC_ColorSpace.getProfile();
/*      */       } else {
/*  798 */         bool3 = false;
/*  799 */         iCC_Profile2 = ((ICC_ColorSpace)paramColorSpace2).getProfile();
/*      */       } 
/*      */       
/*  802 */       if (this.thisTransform == null || this.thisSrcProfile != iCC_Profile1 || this.thisDestProfile != iCC_Profile2)
/*      */       {
/*  804 */         updateBITransform(iCC_Profile1, iCC_Profile2);
/*      */       }
/*      */       
/*  807 */       float f = 65535.0F;
/*      */ 
/*      */       
/*  810 */       if (bool2) {
/*  811 */         colorSpace = iCC_ColorSpace;
/*  812 */         n = 3;
/*      */       } else {
/*  814 */         colorSpace = paramColorSpace1;
/*  815 */         n = k;
/*      */       } 
/*  817 */       float[] arrayOfFloat1 = new float[n];
/*  818 */       float[] arrayOfFloat2 = new float[n]; int i1;
/*  819 */       for (i1 = 0; i1 < k; i1++) {
/*  820 */         arrayOfFloat1[i1] = colorSpace.getMinValue(i1);
/*  821 */         arrayOfFloat2[i1] = f / (colorSpace.getMaxValue(i1) - arrayOfFloat1[i1]);
/*      */       } 
/*      */       
/*  824 */       if (bool3) {
/*  825 */         colorSpace = iCC_ColorSpace;
/*  826 */         i1 = 3;
/*      */       } else {
/*  828 */         colorSpace = paramColorSpace2;
/*  829 */         i1 = m;
/*      */       } 
/*  831 */       float[] arrayOfFloat3 = new float[i1];
/*  832 */       float[] arrayOfFloat4 = new float[i1];
/*  833 */       for (byte b1 = 0; b1 < m; b1++) {
/*  834 */         arrayOfFloat3[b1] = colorSpace.getMinValue(b1);
/*  835 */         arrayOfFloat4[b1] = (colorSpace.getMaxValue(b1) - arrayOfFloat3[b1]) / f;
/*      */       } 
/*      */       
/*  838 */       if (bool) {
/*  839 */         boolean bool4 = (m + 1 > 3) ? (m + 1) : true;
/*  840 */         arrayOfFloat5 = new float[bool4];
/*      */       } else {
/*  842 */         boolean bool4 = (m > 3) ? m : true;
/*  843 */         arrayOfFloat5 = new float[bool4];
/*      */       } 
/*  845 */       short[] arrayOfShort1 = new short[i * n];
/*  846 */       short[] arrayOfShort2 = new short[i * i1];
/*      */ 
/*      */       
/*  849 */       float[] arrayOfFloat6 = null;
/*  850 */       if (bool1) {
/*  851 */         arrayOfFloat6 = new float[i];
/*      */       }
/*      */ 
/*      */       
/*  855 */       for (byte b2 = 0; b2 < j; b2++) {
/*      */         
/*  857 */         Object object = null;
/*  858 */         float[] arrayOfFloat = null;
/*  859 */         byte b3 = 0; byte b4;
/*  860 */         for (b4 = 0; b4 < i; b4++) {
/*  861 */           object = writableRaster1.getDataElements(b4, b2, object);
/*  862 */           arrayOfFloat = colorModel1.getNormalizedComponents(object, arrayOfFloat, 0);
/*  863 */           if (bool1) {
/*  864 */             arrayOfFloat6[b4] = arrayOfFloat[k];
/*      */           }
/*  866 */           if (bool2) {
/*  867 */             arrayOfFloat = paramColorSpace1.toCIEXYZ(arrayOfFloat);
/*      */           }
/*  869 */           for (byte b = 0; b < n; b++) {
/*  870 */             arrayOfShort1[b3++] = (short)(int)((arrayOfFloat[b] - arrayOfFloat1[b]) * arrayOfFloat2[b] + 0.5F);
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  876 */         this.thisTransform.colorConvert(arrayOfShort1, arrayOfShort2);
/*      */         
/*  878 */         object = null;
/*  879 */         b3 = 0;
/*  880 */         for (b4 = 0; b4 < i; b4++) {
/*  881 */           byte b; for (b = 0; b < i1; b++) {
/*  882 */             arrayOfFloat5[b] = (arrayOfShort2[b3++] & 0xFFFF) * arrayOfFloat4[b] + arrayOfFloat3[b];
/*      */           }
/*      */           
/*  885 */           if (bool3) {
/*  886 */             arrayOfFloat = paramColorSpace1.fromCIEXYZ(arrayOfFloat5);
/*  887 */             for (b = 0; b < m; b++) {
/*  888 */               arrayOfFloat5[b] = arrayOfFloat[b];
/*      */             }
/*      */           } 
/*  891 */           if (bool1) {
/*  892 */             arrayOfFloat5[m] = arrayOfFloat6[b4];
/*  893 */           } else if (bool) {
/*  894 */             arrayOfFloat5[m] = 1.0F;
/*      */           } 
/*  896 */           object = colorModel2.getDataElements(arrayOfFloat5, 0, object);
/*  897 */           writableRaster2.setDataElements(b4, b2, object);
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       int n;
/*      */       
/*      */       float[] arrayOfFloat1;
/*  904 */       if (this.CSList == null) {
/*  905 */         n = 0;
/*      */       } else {
/*  907 */         n = this.CSList.length;
/*      */       } 
/*      */       
/*  910 */       if (bool) {
/*  911 */         arrayOfFloat1 = new float[m + 1];
/*      */       } else {
/*  913 */         arrayOfFloat1 = new float[m];
/*      */       } 
/*  915 */       Object object1 = null;
/*  916 */       Object object2 = null;
/*  917 */       float[] arrayOfFloat2 = null;
/*      */ 
/*      */       
/*  920 */       for (byte b = 0; b < j; b++) {
/*  921 */         for (byte b1 = 0; b1 < i; b1++) {
/*  922 */           object1 = writableRaster1.getDataElements(b1, b, object1);
/*  923 */           arrayOfFloat2 = colorModel1.getNormalizedComponents(object1, arrayOfFloat2, 0);
/*  924 */           float[] arrayOfFloat = paramColorSpace1.toCIEXYZ(arrayOfFloat2); byte b2;
/*  925 */           for (b2 = 0; b2 < n; b2++) {
/*  926 */             arrayOfFloat = this.CSList[b2].fromCIEXYZ(arrayOfFloat);
/*  927 */             arrayOfFloat = this.CSList[b2].toCIEXYZ(arrayOfFloat);
/*      */           } 
/*  929 */           arrayOfFloat = paramColorSpace2.fromCIEXYZ(arrayOfFloat);
/*  930 */           for (b2 = 0; b2 < m; b2++) {
/*  931 */             arrayOfFloat1[b2] = arrayOfFloat[b2];
/*      */           }
/*  933 */           if (bool1) {
/*  934 */             arrayOfFloat1[m] = arrayOfFloat2[k];
/*  935 */           } else if (bool) {
/*  936 */             arrayOfFloat1[m] = 1.0F;
/*      */           } 
/*  938 */           object2 = colorModel2.getDataElements(arrayOfFloat1, 0, object2);
/*  939 */           writableRaster2.setDataElements(b1, b, object2);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  945 */     return paramBufferedImage2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final WritableRaster nonICCRasterFilter(Raster paramRaster, WritableRaster paramWritableRaster) {
/*      */     boolean bool1, bool2;
/*  953 */     if (this.CSList.length != 2) {
/*  954 */       throw new IllegalArgumentException("Destination ColorSpace is undefined");
/*      */     }
/*      */     
/*  957 */     if (paramRaster.getNumBands() != this.CSList[0].getNumComponents()) {
/*  958 */       throw new IllegalArgumentException("Numbers of source Raster bands and source color space components do not match");
/*      */     }
/*      */ 
/*      */     
/*  962 */     if (paramWritableRaster == null) {
/*  963 */       paramWritableRaster = createCompatibleDestRaster(paramRaster);
/*      */     } else {
/*  965 */       if (paramRaster.getHeight() != paramWritableRaster.getHeight() || paramRaster
/*  966 */         .getWidth() != paramWritableRaster.getWidth()) {
/*  967 */         throw new IllegalArgumentException("Width or height of Rasters do not match");
/*      */       }
/*      */       
/*  970 */       if (paramWritableRaster.getNumBands() != this.CSList[1].getNumComponents()) {
/*  971 */         throw new IllegalArgumentException("Numbers of destination Raster bands and destination color space components do not match");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  977 */     if (this.srcMinVals == null) {
/*  978 */       getMinMaxValsFromColorSpaces(this.CSList[0], this.CSList[1]);
/*      */     }
/*      */     
/*  981 */     SampleModel sampleModel1 = paramRaster.getSampleModel();
/*  982 */     SampleModel sampleModel2 = paramWritableRaster.getSampleModel();
/*      */     
/*  984 */     int i = paramRaster.getTransferType();
/*  985 */     int j = paramWritableRaster.getTransferType();
/*  986 */     if (i == 4 || i == 5) {
/*      */       
/*  988 */       bool1 = true;
/*      */     } else {
/*  990 */       bool1 = false;
/*      */     } 
/*  992 */     if (j == 4 || j == 5) {
/*      */       
/*  994 */       bool2 = true;
/*      */     } else {
/*  996 */       bool2 = false;
/*      */     } 
/*  998 */     int k = paramRaster.getWidth();
/*  999 */     int m = paramRaster.getHeight();
/* 1000 */     int n = paramRaster.getNumBands();
/* 1001 */     int i1 = paramWritableRaster.getNumBands();
/* 1002 */     float[] arrayOfFloat1 = null;
/* 1003 */     float[] arrayOfFloat2 = null;
/* 1004 */     if (!bool1) {
/* 1005 */       arrayOfFloat1 = new float[n];
/* 1006 */       for (byte b1 = 0; b1 < n; b1++) {
/* 1007 */         if (i == 2) {
/* 1008 */           arrayOfFloat1[b1] = (this.srcMaxVals[b1] - this.srcMinVals[b1]) / 32767.0F;
/*      */         } else {
/*      */           
/* 1011 */           arrayOfFloat1[b1] = (this.srcMaxVals[b1] - this.srcMinVals[b1]) / ((1 << sampleModel1
/* 1012 */             .getSampleSize(b1)) - 1);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1016 */     if (!bool2) {
/* 1017 */       arrayOfFloat2 = new float[i1];
/* 1018 */       for (byte b1 = 0; b1 < i1; b1++) {
/* 1019 */         if (j == 2) {
/* 1020 */           arrayOfFloat2[b1] = 32767.0F / (this.dstMaxVals[b1] - this.dstMinVals[b1]);
/*      */         } else {
/*      */           
/* 1023 */           arrayOfFloat2[b1] = ((1 << sampleModel2
/* 1024 */             .getSampleSize(b1)) - 1) / (this.dstMaxVals[b1] - this.dstMinVals[b1]);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1029 */     int i2 = paramRaster.getMinY();
/* 1030 */     int i3 = paramWritableRaster.getMinY();
/*      */ 
/*      */     
/* 1033 */     float[] arrayOfFloat3 = new float[n];
/*      */     
/* 1035 */     ColorSpace colorSpace1 = this.CSList[0];
/* 1036 */     ColorSpace colorSpace2 = this.CSList[1];
/*      */     
/* 1038 */     for (byte b = 0; b < m; b++, i2++, i3++) {
/*      */       
/* 1040 */       int i4 = paramRaster.getMinX();
/* 1041 */       int i5 = paramWritableRaster.getMinX();
/* 1042 */       for (byte b1 = 0; b1 < k; b1++, i4++, i5++) {
/* 1043 */         byte b2; for (b2 = 0; b2 < n; b2++) {
/* 1044 */           float f = paramRaster.getSampleFloat(i4, i2, b2);
/* 1045 */           if (!bool1) {
/* 1046 */             f = f * arrayOfFloat1[b2] + this.srcMinVals[b2];
/*      */           }
/* 1048 */           arrayOfFloat3[b2] = f;
/*      */         } 
/* 1050 */         float[] arrayOfFloat = colorSpace1.toCIEXYZ(arrayOfFloat3);
/* 1051 */         arrayOfFloat = colorSpace2.fromCIEXYZ(arrayOfFloat);
/* 1052 */         for (b2 = 0; b2 < i1; b2++) {
/* 1053 */           float f = arrayOfFloat[b2];
/* 1054 */           if (!bool2) {
/* 1055 */             f = (f - this.dstMinVals[b2]) * arrayOfFloat2[b2];
/*      */           }
/* 1057 */           paramWritableRaster.setSample(i5, i3, b2, f);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1061 */     return paramWritableRaster;
/*      */   }
/*      */ 
/*      */   
/*      */   private void getMinMaxValsFromProfiles(ICC_Profile paramICC_Profile1, ICC_Profile paramICC_Profile2) {
/* 1066 */     int i = paramICC_Profile1.getColorSpaceType();
/* 1067 */     int j = paramICC_Profile1.getNumComponents();
/* 1068 */     this.srcMinVals = new float[j];
/* 1069 */     this.srcMaxVals = new float[j];
/* 1070 */     setMinMax(i, j, this.srcMinVals, this.srcMaxVals);
/* 1071 */     i = paramICC_Profile2.getColorSpaceType();
/* 1072 */     j = paramICC_Profile2.getNumComponents();
/* 1073 */     this.dstMinVals = new float[j];
/* 1074 */     this.dstMaxVals = new float[j];
/* 1075 */     setMinMax(i, j, this.dstMinVals, this.dstMaxVals);
/*      */   }
/*      */   
/*      */   private void setMinMax(int paramInt1, int paramInt2, float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
/* 1079 */     if (paramInt1 == 1) {
/* 1080 */       paramArrayOffloat1[0] = 0.0F;
/* 1081 */       paramArrayOffloat2[0] = 100.0F;
/* 1082 */       paramArrayOffloat1[1] = -128.0F;
/* 1083 */       paramArrayOffloat2[1] = 127.0F;
/* 1084 */       paramArrayOffloat1[2] = -128.0F;
/* 1085 */       paramArrayOffloat2[2] = 127.0F;
/* 1086 */     } else if (paramInt1 == 0) {
/* 1087 */       paramArrayOffloat1[2] = 0.0F; paramArrayOffloat1[1] = 0.0F; paramArrayOffloat1[0] = 0.0F;
/* 1088 */       paramArrayOffloat2[2] = 1.9999695F; paramArrayOffloat2[1] = 1.9999695F; paramArrayOffloat2[0] = 1.9999695F;
/*      */     } else {
/* 1090 */       for (byte b = 0; b < paramInt2; b++) {
/* 1091 */         paramArrayOffloat1[b] = 0.0F;
/* 1092 */         paramArrayOffloat2[b] = 1.0F;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void getMinMaxValsFromColorSpaces(ColorSpace paramColorSpace1, ColorSpace paramColorSpace2) {
/* 1099 */     int i = paramColorSpace1.getNumComponents();
/* 1100 */     this.srcMinVals = new float[i];
/* 1101 */     this.srcMaxVals = new float[i]; byte b;
/* 1102 */     for (b = 0; b < i; b++) {
/* 1103 */       this.srcMinVals[b] = paramColorSpace1.getMinValue(b);
/* 1104 */       this.srcMaxVals[b] = paramColorSpace1.getMaxValue(b);
/*      */     } 
/* 1106 */     i = paramColorSpace2.getNumComponents();
/* 1107 */     this.dstMinVals = new float[i];
/* 1108 */     this.dstMaxVals = new float[i];
/* 1109 */     for (b = 0; b < i; b++) {
/* 1110 */       this.dstMinVals[b] = paramColorSpace2.getMinValue(b);
/* 1111 */       this.dstMaxVals[b] = paramColorSpace2.getMaxValue(b);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/ColorConvertOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */