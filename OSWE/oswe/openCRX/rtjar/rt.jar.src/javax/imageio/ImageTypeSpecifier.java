/*      */ package javax.imageio;
/*      */ 
/*      */ import java.awt.Point;
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.awt.image.BandedSampleModel;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.ComponentColorModel;
/*      */ import java.awt.image.DataBuffer;
/*      */ import java.awt.image.DirectColorModel;
/*      */ import java.awt.image.IndexColorModel;
/*      */ import java.awt.image.MultiPixelPackedSampleModel;
/*      */ import java.awt.image.PixelInterleavedSampleModel;
/*      */ import java.awt.image.Raster;
/*      */ import java.awt.image.RenderedImage;
/*      */ import java.awt.image.SampleModel;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.util.Hashtable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ImageTypeSpecifier
/*      */ {
/*      */   protected ColorModel colorModel;
/*      */   protected SampleModel sampleModel;
/*      */   private static ImageTypeSpecifier[] BISpecifier;
/*   73 */   private static ColorSpace sRGB = ColorSpace.getInstance(1000);
/*      */   static {
/*   75 */     BISpecifier = new ImageTypeSpecifier[14];
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
/*      */   private ImageTypeSpecifier() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ImageTypeSpecifier(ColorModel paramColorModel, SampleModel paramSampleModel) {
/*   99 */     if (paramColorModel == null) {
/*  100 */       throw new IllegalArgumentException("colorModel == null!");
/*      */     }
/*  102 */     if (paramSampleModel == null) {
/*  103 */       throw new IllegalArgumentException("sampleModel == null!");
/*      */     }
/*  105 */     if (!paramColorModel.isCompatibleSampleModel(paramSampleModel)) {
/*  106 */       throw new IllegalArgumentException("sampleModel is incompatible with colorModel!");
/*      */     }
/*      */     
/*  109 */     this.colorModel = paramColorModel;
/*  110 */     this.sampleModel = paramSampleModel;
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
/*      */   public ImageTypeSpecifier(RenderedImage paramRenderedImage) {
/*  127 */     if (paramRenderedImage == null) {
/*  128 */       throw new IllegalArgumentException("image == null!");
/*      */     }
/*  130 */     this.colorModel = paramRenderedImage.getColorModel();
/*  131 */     this.sampleModel = paramRenderedImage.getSampleModel();
/*      */   }
/*      */ 
/*      */   
/*      */   static class Packed
/*      */     extends ImageTypeSpecifier
/*      */   {
/*      */     ColorSpace colorSpace;
/*      */     
/*      */     int redMask;
/*      */     
/*      */     int greenMask;
/*      */     
/*      */     int blueMask;
/*      */     
/*      */     int alphaMask;
/*      */     
/*      */     int transferType;
/*      */     boolean isAlphaPremultiplied;
/*      */     
/*      */     public Packed(ColorSpace param1ColorSpace, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, boolean param1Boolean) {
/*  152 */       if (param1ColorSpace == null) {
/*  153 */         throw new IllegalArgumentException("colorSpace == null!");
/*      */       }
/*  155 */       if (param1ColorSpace.getType() != 5) {
/*  156 */         throw new IllegalArgumentException("colorSpace is not of type TYPE_RGB!");
/*      */       }
/*      */       
/*  159 */       if (param1Int5 != 0 && param1Int5 != 1 && param1Int5 != 3)
/*      */       {
/*      */         
/*  162 */         throw new IllegalArgumentException("Bad value for transferType!");
/*      */       }
/*      */       
/*  165 */       if (param1Int1 == 0 && param1Int2 == 0 && param1Int3 == 0 && param1Int4 == 0)
/*      */       {
/*  167 */         throw new IllegalArgumentException("No mask has at least 1 bit set!");
/*      */       }
/*      */       
/*  170 */       this.colorSpace = param1ColorSpace;
/*  171 */       this.redMask = param1Int1;
/*  172 */       this.greenMask = param1Int2;
/*  173 */       this.blueMask = param1Int3;
/*  174 */       this.alphaMask = param1Int4;
/*  175 */       this.transferType = param1Int5;
/*  176 */       this.isAlphaPremultiplied = param1Boolean;
/*      */       
/*  178 */       byte b = 32;
/*  179 */       this.colorModel = new DirectColorModel(param1ColorSpace, b, param1Int1, param1Int2, param1Int3, param1Int4, param1Boolean, param1Int5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  185 */       this.sampleModel = this.colorModel.createCompatibleSampleModel(1, 1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ImageTypeSpecifier createPacked(ColorSpace paramColorSpace, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean) {
/*  231 */     return new Packed(paramColorSpace, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramBoolean);
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
/*      */   static ColorModel createComponentCM(ColorSpace paramColorSpace, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {
/*  245 */     boolean bool = paramBoolean1 ? true : true;
/*      */ 
/*      */     
/*  248 */     int[] arrayOfInt = new int[paramInt1];
/*  249 */     int i = DataBuffer.getDataTypeSize(paramInt2);
/*      */     
/*  251 */     for (byte b = 0; b < paramInt1; b++) {
/*  252 */       arrayOfInt[b] = i;
/*      */     }
/*      */     
/*  255 */     return new ComponentColorModel(paramColorSpace, arrayOfInt, paramBoolean1, paramBoolean2, bool, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class Interleaved
/*      */     extends ImageTypeSpecifier
/*      */   {
/*      */     ColorSpace colorSpace;
/*      */ 
/*      */     
/*      */     int[] bandOffsets;
/*      */ 
/*      */     
/*      */     int dataType;
/*      */     
/*      */     boolean hasAlpha;
/*      */     
/*      */     boolean isAlphaPremultiplied;
/*      */ 
/*      */     
/*      */     public Interleaved(ColorSpace param1ColorSpace, int[] param1ArrayOfint, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/*  277 */       if (param1ColorSpace == null) {
/*  278 */         throw new IllegalArgumentException("colorSpace == null!");
/*      */       }
/*  280 */       if (param1ArrayOfint == null) {
/*  281 */         throw new IllegalArgumentException("bandOffsets == null!");
/*      */       }
/*  283 */       int i = param1ColorSpace.getNumComponents() + (param1Boolean1 ? 1 : 0);
/*      */       
/*  285 */       if (param1ArrayOfint.length != i) {
/*  286 */         throw new IllegalArgumentException("bandOffsets.length is wrong!");
/*      */       }
/*      */       
/*  289 */       if (param1Int != 0 && param1Int != 2 && param1Int != 1 && param1Int != 3 && param1Int != 4 && param1Int != 5)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  295 */         throw new IllegalArgumentException("Bad value for dataType!");
/*      */       }
/*      */       
/*  298 */       this.colorSpace = param1ColorSpace;
/*  299 */       this.bandOffsets = (int[])param1ArrayOfint.clone();
/*  300 */       this.dataType = param1Int;
/*  301 */       this.hasAlpha = param1Boolean1;
/*  302 */       this.isAlphaPremultiplied = param1Boolean2;
/*      */       
/*  304 */       this
/*  305 */         .colorModel = ImageTypeSpecifier.createComponentCM(param1ColorSpace, param1ArrayOfint.length, param1Int, param1Boolean1, param1Boolean2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  311 */       int j = param1ArrayOfint[0];
/*  312 */       int k = j; int m;
/*  313 */       for (m = 0; m < param1ArrayOfint.length; m++) {
/*  314 */         int n = param1ArrayOfint[m];
/*  315 */         j = Math.min(n, j);
/*  316 */         k = Math.max(n, k);
/*      */       } 
/*  318 */       m = k - j + 1;
/*      */       
/*  320 */       byte b = 1;
/*  321 */       boolean bool = true;
/*  322 */       this.sampleModel = new PixelInterleavedSampleModel(param1Int, b, bool, m, b * m, param1ArrayOfint);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/*  331 */       if (param1Object == null || !(param1Object instanceof Interleaved))
/*      */       {
/*  333 */         return false;
/*      */       }
/*      */       
/*  336 */       Interleaved interleaved = (Interleaved)param1Object;
/*      */ 
/*      */       
/*  339 */       if (!this.colorSpace.equals(interleaved.colorSpace) || this.dataType != interleaved.dataType || this.hasAlpha != interleaved.hasAlpha || this.isAlphaPremultiplied != interleaved.isAlphaPremultiplied || this.bandOffsets.length != interleaved.bandOffsets.length)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  344 */         return false;
/*      */       }
/*      */       
/*  347 */       for (byte b = 0; b < this.bandOffsets.length; b++) {
/*  348 */         if (this.bandOffsets[b] != interleaved.bandOffsets[b]) {
/*  349 */           return false;
/*      */         }
/*      */       } 
/*      */       
/*  353 */       return true;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  357 */       return super.hashCode() + 4 * this.bandOffsets.length + 25 * this.dataType + (this.hasAlpha ? 17 : 18);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ImageTypeSpecifier createInterleaved(ColorSpace paramColorSpace, int[] paramArrayOfint, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/*  399 */     return new Interleaved(paramColorSpace, paramArrayOfint, paramInt, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class Banded
/*      */     extends ImageTypeSpecifier
/*      */   {
/*      */     ColorSpace colorSpace;
/*      */ 
/*      */     
/*      */     int[] bankIndices;
/*      */     
/*      */     int[] bandOffsets;
/*      */     
/*      */     int dataType;
/*      */     
/*      */     boolean hasAlpha;
/*      */     
/*      */     boolean isAlphaPremultiplied;
/*      */ 
/*      */     
/*      */     public Banded(ColorSpace param1ColorSpace, int[] param1ArrayOfint1, int[] param1ArrayOfint2, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/*  422 */       if (param1ColorSpace == null) {
/*  423 */         throw new IllegalArgumentException("colorSpace == null!");
/*      */       }
/*  425 */       if (param1ArrayOfint1 == null) {
/*  426 */         throw new IllegalArgumentException("bankIndices == null!");
/*      */       }
/*  428 */       if (param1ArrayOfint2 == null) {
/*  429 */         throw new IllegalArgumentException("bandOffsets == null!");
/*      */       }
/*  431 */       if (param1ArrayOfint1.length != param1ArrayOfint2.length) {
/*  432 */         throw new IllegalArgumentException("bankIndices.length != bandOffsets.length!");
/*      */       }
/*      */       
/*  435 */       if (param1Int != 0 && param1Int != 2 && param1Int != 1 && param1Int != 3 && param1Int != 4 && param1Int != 5)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  441 */         throw new IllegalArgumentException("Bad value for dataType!");
/*      */       }
/*      */       
/*  444 */       int i = param1ColorSpace.getNumComponents() + (param1Boolean1 ? 1 : 0);
/*      */       
/*  446 */       if (param1ArrayOfint2.length != i) {
/*  447 */         throw new IllegalArgumentException("bandOffsets.length is wrong!");
/*      */       }
/*      */ 
/*      */       
/*  451 */       this.colorSpace = param1ColorSpace;
/*  452 */       this.bankIndices = (int[])param1ArrayOfint1.clone();
/*  453 */       this.bandOffsets = (int[])param1ArrayOfint2.clone();
/*  454 */       this.dataType = param1Int;
/*  455 */       this.hasAlpha = param1Boolean1;
/*  456 */       this.isAlphaPremultiplied = param1Boolean2;
/*      */       
/*  458 */       this
/*  459 */         .colorModel = ImageTypeSpecifier.createComponentCM(param1ColorSpace, param1ArrayOfint1.length, param1Int, param1Boolean1, param1Boolean2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  465 */       boolean bool1 = true;
/*  466 */       boolean bool2 = true;
/*  467 */       this.sampleModel = new BandedSampleModel(param1Int, bool1, bool2, bool1, param1ArrayOfint1, param1ArrayOfint2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/*  475 */       if (param1Object == null || !(param1Object instanceof Banded))
/*      */       {
/*  477 */         return false;
/*      */       }
/*      */       
/*  480 */       Banded banded = (Banded)param1Object;
/*      */ 
/*      */       
/*  483 */       if (!this.colorSpace.equals(banded.colorSpace) || this.dataType != banded.dataType || this.hasAlpha != banded.hasAlpha || this.isAlphaPremultiplied != banded.isAlphaPremultiplied || this.bankIndices.length != banded.bankIndices.length || this.bandOffsets.length != banded.bandOffsets.length)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  489 */         return false;
/*      */       }
/*      */       byte b;
/*  492 */       for (b = 0; b < this.bankIndices.length; b++) {
/*  493 */         if (this.bankIndices[b] != banded.bankIndices[b]) {
/*  494 */           return false;
/*      */         }
/*      */       } 
/*      */       
/*  498 */       for (b = 0; b < this.bandOffsets.length; b++) {
/*  499 */         if (this.bandOffsets[b] != banded.bandOffsets[b]) {
/*  500 */           return false;
/*      */         }
/*      */       } 
/*      */       
/*  504 */       return true;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  508 */       return super.hashCode() + 3 * this.bandOffsets.length + 7 * this.bankIndices.length + 21 * this.dataType + (this.hasAlpha ? 19 : 29);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ImageTypeSpecifier createBanded(ColorSpace paramColorSpace, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/*  558 */     return new Banded(paramColorSpace, paramArrayOfint1, paramArrayOfint2, paramInt, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class Grayscale
/*      */     extends ImageTypeSpecifier
/*      */   {
/*      */     int bits;
/*      */ 
/*      */     
/*      */     int dataType;
/*      */ 
/*      */     
/*      */     boolean isSigned;
/*      */ 
/*      */     
/*      */     boolean hasAlpha;
/*      */     
/*      */     boolean isAlphaPremultiplied;
/*      */ 
/*      */     
/*      */     public Grayscale(int param1Int1, int param1Int2, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3) {
/*  581 */       if (param1Int1 != 1 && param1Int1 != 2 && param1Int1 != 4 && param1Int1 != 8 && param1Int1 != 16)
/*      */       {
/*      */         
/*  584 */         throw new IllegalArgumentException("Bad value for bits!");
/*      */       }
/*  586 */       if (param1Int2 != 0 && param1Int2 != 2 && param1Int2 != 1)
/*      */       {
/*      */ 
/*      */         
/*  590 */         throw new IllegalArgumentException("Bad value for dataType!");
/*      */       }
/*      */       
/*  593 */       if (param1Int1 > 8 && param1Int2 == 0) {
/*  594 */         throw new IllegalArgumentException("Too many bits for dataType!");
/*      */       }
/*      */ 
/*      */       
/*  598 */       this.bits = param1Int1;
/*  599 */       this.dataType = param1Int2;
/*  600 */       this.isSigned = param1Boolean1;
/*  601 */       this.hasAlpha = param1Boolean2;
/*  602 */       this.isAlphaPremultiplied = param1Boolean3;
/*      */       
/*  604 */       ColorSpace colorSpace = ColorSpace.getInstance(1003);
/*      */       
/*  606 */       if ((param1Int1 == 8 && param1Int2 == 0) || (param1Int1 == 16 && (param1Int2 == 2 || param1Int2 == 1))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  612 */         byte b1 = param1Boolean2 ? 2 : 1;
/*  613 */         boolean bool1 = param1Boolean2 ? true : true;
/*      */ 
/*      */ 
/*      */         
/*  617 */         int[] arrayOfInt1 = new int[b1];
/*  618 */         arrayOfInt1[0] = param1Int1;
/*  619 */         if (b1 == 2) {
/*  620 */           arrayOfInt1[1] = param1Int1;
/*      */         }
/*  622 */         this.colorModel = new ComponentColorModel(colorSpace, arrayOfInt1, param1Boolean2, param1Boolean3, bool1, param1Int2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  630 */         int[] arrayOfInt2 = new int[b1];
/*  631 */         arrayOfInt2[0] = 0;
/*  632 */         if (b1 == 2) {
/*  633 */           arrayOfInt2[1] = 1;
/*      */         }
/*      */         
/*  636 */         byte b2 = 1;
/*  637 */         boolean bool2 = true;
/*  638 */         this.sampleModel = new PixelInterleavedSampleModel(param1Int2, b2, bool2, b1, b2 * b1, arrayOfInt2);
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  644 */         int i = 1 << param1Int1;
/*  645 */         byte[] arrayOfByte = new byte[i];
/*  646 */         for (byte b = 0; b < i; b++) {
/*  647 */           arrayOfByte[b] = (byte)(b * 255 / (i - 1));
/*      */         }
/*  649 */         this.colorModel = new IndexColorModel(param1Int1, i, arrayOfByte, arrayOfByte, arrayOfByte);
/*      */ 
/*      */         
/*  652 */         this.sampleModel = new MultiPixelPackedSampleModel(param1Int2, 1, 1, param1Int1);
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ImageTypeSpecifier createGrayscale(int paramInt1, int paramInt2, boolean paramBoolean) {
/*  685 */     return new Grayscale(paramInt1, paramInt2, paramBoolean, false, false);
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
/*      */   public static ImageTypeSpecifier createGrayscale(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {
/*  722 */     return new Grayscale(paramInt1, paramInt2, paramBoolean1, true, paramBoolean2);
/*      */   }
/*      */ 
/*      */   
/*      */   static class Indexed
/*      */     extends ImageTypeSpecifier
/*      */   {
/*      */     byte[] redLUT;
/*      */     
/*      */     byte[] greenLUT;
/*      */     
/*      */     byte[] blueLUT;
/*      */     
/*  735 */     byte[] alphaLUT = null;
/*      */ 
/*      */     
/*      */     int bits;
/*      */ 
/*      */     
/*      */     int dataType;
/*      */ 
/*      */     
/*      */     public Indexed(byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2, byte[] param1ArrayOfbyte3, byte[] param1ArrayOfbyte4, int param1Int1, int param1Int2) {
/*  745 */       if (param1ArrayOfbyte1 == null || param1ArrayOfbyte2 == null || param1ArrayOfbyte3 == null) {
/*  746 */         throw new IllegalArgumentException("LUT is null!");
/*      */       }
/*  748 */       if (param1Int1 != 1 && param1Int1 != 2 && param1Int1 != 4 && param1Int1 != 8 && param1Int1 != 16)
/*      */       {
/*  750 */         throw new IllegalArgumentException("Bad value for bits!");
/*      */       }
/*  752 */       if (param1Int2 != 0 && param1Int2 != 2 && param1Int2 != 1 && param1Int2 != 3)
/*      */       {
/*      */ 
/*      */         
/*  756 */         throw new IllegalArgumentException("Bad value for dataType!");
/*      */       }
/*      */       
/*  759 */       if ((param1Int1 > 8 && param1Int2 == 0) || (param1Int1 > 16 && param1Int2 != 3))
/*      */       {
/*  761 */         throw new IllegalArgumentException("Too many bits for dataType!");
/*      */       }
/*      */ 
/*      */       
/*  765 */       int i = 1 << param1Int1;
/*  766 */       if (param1ArrayOfbyte1.length != i || param1ArrayOfbyte2.length != i || param1ArrayOfbyte3.length != i || (param1ArrayOfbyte4 != null && param1ArrayOfbyte4.length != i))
/*      */       {
/*      */ 
/*      */         
/*  770 */         throw new IllegalArgumentException("LUT has improper length!");
/*      */       }
/*  772 */       this.redLUT = (byte[])param1ArrayOfbyte1.clone();
/*  773 */       this.greenLUT = (byte[])param1ArrayOfbyte2.clone();
/*  774 */       this.blueLUT = (byte[])param1ArrayOfbyte3.clone();
/*  775 */       if (param1ArrayOfbyte4 != null) {
/*  776 */         this.alphaLUT = (byte[])param1ArrayOfbyte4.clone();
/*      */       }
/*  778 */       this.bits = param1Int1;
/*  779 */       this.dataType = param1Int2;
/*      */       
/*  781 */       if (param1ArrayOfbyte4 == null) {
/*  782 */         this.colorModel = new IndexColorModel(param1Int1, param1ArrayOfbyte1.length, param1ArrayOfbyte1, param1ArrayOfbyte2, param1ArrayOfbyte3);
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  788 */         this.colorModel = new IndexColorModel(param1Int1, param1ArrayOfbyte1.length, param1ArrayOfbyte1, param1ArrayOfbyte2, param1ArrayOfbyte3, param1ArrayOfbyte4);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  796 */       if ((param1Int1 == 8 && param1Int2 == 0) || (param1Int1 == 16 && (param1Int2 == 2 || param1Int2 == 1))) {
/*      */ 
/*      */ 
/*      */         
/*  800 */         int[] arrayOfInt = { 0 };
/*  801 */         this.sampleModel = new PixelInterleavedSampleModel(param1Int2, 1, 1, 1, 1, arrayOfInt);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  806 */         this.sampleModel = new MultiPixelPackedSampleModel(param1Int2, 1, 1, param1Int1);
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ImageTypeSpecifier createIndexed(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, byte[] paramArrayOfbyte4, int paramInt1, int paramInt2) {
/*  859 */     return new Indexed(paramArrayOfbyte1, paramArrayOfbyte2, paramArrayOfbyte3, paramArrayOfbyte4, paramInt1, paramInt2);
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
/*      */   public static ImageTypeSpecifier createFromBufferedImageType(int paramInt) {
/*  899 */     if (paramInt >= 1 && paramInt <= 13)
/*      */     {
/*  901 */       return getSpecifier(paramInt); } 
/*  902 */     if (paramInt == 0) {
/*  903 */       throw new IllegalArgumentException("Cannot create from TYPE_CUSTOM!");
/*      */     }
/*  905 */     throw new IllegalArgumentException("Invalid BufferedImage type!");
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
/*      */   public static ImageTypeSpecifier createFromRenderedImage(RenderedImage paramRenderedImage) {
/*  924 */     if (paramRenderedImage == null) {
/*  925 */       throw new IllegalArgumentException("image == null!");
/*      */     }
/*      */     
/*  928 */     if (paramRenderedImage instanceof BufferedImage) {
/*  929 */       int i = ((BufferedImage)paramRenderedImage).getType();
/*  930 */       if (i != 0) {
/*  931 */         return getSpecifier(i);
/*      */       }
/*      */     } 
/*      */     
/*  935 */     return new ImageTypeSpecifier(paramRenderedImage);
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
/*      */   public int getBufferedImageType() {
/*  962 */     BufferedImage bufferedImage = createBufferedImage(1, 1);
/*  963 */     return bufferedImage.getType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumComponents() {
/*  974 */     return this.colorModel.getNumComponents();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumBands() {
/*  985 */     return this.sampleModel.getNumBands();
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
/*      */   public int getBitsPerBand(int paramInt) {
/* 1000 */     if ((((paramInt < 0) ? 1 : 0) | ((paramInt >= getNumBands()) ? 1 : 0)) != 0) {
/* 1001 */       throw new IllegalArgumentException("band out of range!");
/*      */     }
/* 1003 */     return this.sampleModel.getSampleSize(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SampleModel getSampleModel() {
/* 1014 */     return this.sampleModel;
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
/*      */   public SampleModel getSampleModel(int paramInt1, int paramInt2) {
/* 1035 */     if (paramInt1 * paramInt2 > 2147483647L) {
/* 1036 */       throw new IllegalArgumentException("width*height > Integer.MAX_VALUE!");
/*      */     }
/*      */     
/* 1039 */     return this.sampleModel.createCompatibleSampleModel(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ColorModel getColorModel() {
/* 1048 */     return this.colorModel;
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
/*      */   public BufferedImage createBufferedImage(int paramInt1, int paramInt2) {
/*      */     try {
/* 1072 */       SampleModel sampleModel = getSampleModel(paramInt1, paramInt2);
/*      */       
/* 1074 */       WritableRaster writableRaster = Raster.createWritableRaster(sampleModel, new Point(0, 0));
/*      */       
/* 1076 */       return new BufferedImage(this.colorModel, writableRaster, this.colorModel
/* 1077 */           .isAlphaPremultiplied(), new Hashtable<>());
/*      */     }
/* 1079 */     catch (NegativeArraySizeException negativeArraySizeException) {
/*      */       
/* 1081 */       throw new IllegalArgumentException("Array size > Integer.MAX_VALUE!");
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
/*      */   public boolean equals(Object paramObject) {
/* 1098 */     if (paramObject == null || !(paramObject instanceof ImageTypeSpecifier)) {
/* 1099 */       return false;
/*      */     }
/*      */     
/* 1102 */     ImageTypeSpecifier imageTypeSpecifier = (ImageTypeSpecifier)paramObject;
/* 1103 */     return (this.colorModel.equals(imageTypeSpecifier.colorModel) && this.sampleModel
/* 1104 */       .equals(imageTypeSpecifier.sampleModel));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1113 */     return 9 * this.colorModel.hashCode() + 14 * this.sampleModel.hashCode();
/*      */   }
/*      */   
/*      */   private static ImageTypeSpecifier getSpecifier(int paramInt) {
/* 1117 */     if (BISpecifier[paramInt] == null) {
/* 1118 */       BISpecifier[paramInt] = createSpecifier(paramInt);
/*      */     }
/* 1120 */     return BISpecifier[paramInt]; } private static ImageTypeSpecifier createSpecifier(int paramInt) { BufferedImage bufferedImage; IndexColorModel indexColorModel; int i; byte[] arrayOfByte1;
/*      */     byte[] arrayOfByte2;
/*      */     byte[] arrayOfByte3;
/*      */     byte[] arrayOfByte4;
/* 1124 */     switch (paramInt) {
/*      */       case 1:
/* 1126 */         return createPacked(sRGB, 16711680, 65280, 255, 0, 3, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 1135 */         return createPacked(sRGB, 16711680, 65280, 255, -16777216, 3, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/* 1144 */         return createPacked(sRGB, 16711680, 65280, 255, -16777216, 3, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/* 1153 */         return createPacked(sRGB, 255, 65280, 16711680, 0, 3, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/* 1162 */         return createInterleaved(sRGB, new int[] { 2, 1, 0 }, 0, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 6:
/* 1169 */         return createInterleaved(sRGB, new int[] { 3, 2, 1, 0 }, 0, true, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 7:
/* 1176 */         return createInterleaved(sRGB, new int[] { 3, 2, 1, 0 }, 0, true, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 8:
/* 1183 */         return createPacked(sRGB, 63488, 2016, 31, 0, 1, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 9:
/* 1192 */         return createPacked(sRGB, 31744, 992, 31, 0, 1, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 10:
/* 1201 */         return createGrayscale(8, 0, false);
/*      */ 
/*      */ 
/*      */       
/*      */       case 11:
/* 1206 */         return createGrayscale(16, 1, false);
/*      */ 
/*      */ 
/*      */       
/*      */       case 12:
/* 1211 */         return createGrayscale(1, 0, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 13:
/* 1218 */         bufferedImage = new BufferedImage(1, 1, 13);
/*      */         
/* 1220 */         indexColorModel = (IndexColorModel)bufferedImage.getColorModel();
/* 1221 */         i = indexColorModel.getMapSize();
/* 1222 */         arrayOfByte1 = new byte[i];
/* 1223 */         arrayOfByte2 = new byte[i];
/* 1224 */         arrayOfByte3 = new byte[i];
/* 1225 */         arrayOfByte4 = new byte[i];
/*      */         
/* 1227 */         indexColorModel.getReds(arrayOfByte1);
/* 1228 */         indexColorModel.getGreens(arrayOfByte2);
/* 1229 */         indexColorModel.getBlues(arrayOfByte3);
/* 1230 */         indexColorModel.getAlphas(arrayOfByte4);
/*      */         
/* 1232 */         return createIndexed(arrayOfByte1, arrayOfByte2, arrayOfByte3, arrayOfByte4, 8, 0);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1237 */     throw new IllegalArgumentException("Invalid BufferedImage type!"); }
/*      */ 
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/ImageTypeSpecifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */