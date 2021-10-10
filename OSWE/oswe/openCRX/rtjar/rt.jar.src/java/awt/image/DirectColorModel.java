/*      */ package java.awt.image;
/*      */ 
/*      */ import java.awt.Point;
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.util.Arrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DirectColorModel
/*      */   extends PackedColorModel
/*      */ {
/*      */   private int red_mask;
/*      */   private int green_mask;
/*      */   private int blue_mask;
/*      */   private int alpha_mask;
/*      */   private int red_offset;
/*      */   private int green_offset;
/*      */   private int blue_offset;
/*      */   private int alpha_offset;
/*      */   private int red_scale;
/*      */   private int green_scale;
/*      */   private int blue_scale;
/*      */   private int alpha_scale;
/*      */   private boolean is_LinearRGB;
/*      */   private int lRGBprecision;
/*      */   private byte[] tosRGB8LUT;
/*      */   private byte[] fromsRGB8LUT8;
/*      */   private short[] fromsRGB8LUT16;
/*      */   
/*      */   public DirectColorModel(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  152 */     this(paramInt1, paramInt2, paramInt3, paramInt4, 0);
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
/*      */   public DirectColorModel(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  182 */     super(ColorSpace.getInstance(1000), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, false, (paramInt5 == 0) ? 1 : 3, 
/*      */ 
/*      */         
/*  185 */         ColorModel.getDefaultTransferType(paramInt1));
/*  186 */     setFields();
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
/*      */   public DirectColorModel(ColorSpace paramColorSpace, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, int paramInt6) {
/*  234 */     super(paramColorSpace, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramBoolean, (paramInt5 == 0) ? 1 : 3, paramInt6);
/*      */ 
/*      */ 
/*      */     
/*  238 */     if (ColorModel.isLinearRGBspace(this.colorSpace)) {
/*  239 */       this.is_LinearRGB = true;
/*  240 */       if (this.maxBits <= 8) {
/*  241 */         this.lRGBprecision = 8;
/*  242 */         this.tosRGB8LUT = ColorModel.getLinearRGB8TosRGB8LUT();
/*  243 */         this.fromsRGB8LUT8 = ColorModel.getsRGB8ToLinearRGB8LUT();
/*      */       } else {
/*  245 */         this.lRGBprecision = 16;
/*  246 */         this.tosRGB8LUT = ColorModel.getLinearRGB16TosRGB8LUT();
/*  247 */         this.fromsRGB8LUT16 = ColorModel.getsRGB8ToLinearRGB16LUT();
/*      */       } 
/*  249 */     } else if (!this.is_sRGB) {
/*  250 */       for (byte b = 0; b < 3; b++) {
/*      */ 
/*      */         
/*  253 */         if (paramColorSpace.getMinValue(b) != 0.0F || paramColorSpace
/*  254 */           .getMaxValue(b) != 1.0F) {
/*  255 */           throw new IllegalArgumentException("Illegal min/max RGB component value");
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  260 */     setFields();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getRedMask() {
/*  270 */     return this.maskArray[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getGreenMask() {
/*  280 */     return this.maskArray[1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getBlueMask() {
/*  290 */     return this.maskArray[2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getAlphaMask() {
/*  300 */     if (this.supportsAlpha) {
/*  301 */       return this.maskArray[3];
/*      */     }
/*  303 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float[] getDefaultRGBComponents(int paramInt) {
/*  314 */     int[] arrayOfInt = getComponents(paramInt, (int[])null, 0);
/*  315 */     float[] arrayOfFloat = getNormalizedComponents(arrayOfInt, 0, null, 0);
/*      */     
/*  317 */     return this.colorSpace.toRGB(arrayOfFloat);
/*      */   }
/*      */ 
/*      */   
/*      */   private int getsRGBComponentFromsRGB(int paramInt1, int paramInt2) {
/*  322 */     int i = (paramInt1 & this.maskArray[paramInt2]) >>> this.maskOffsets[paramInt2];
/*  323 */     if (this.isAlphaPremultiplied) {
/*  324 */       int j = (paramInt1 & this.maskArray[3]) >>> this.maskOffsets[3];
/*  325 */       i = (j == 0) ? 0 : (int)(i * this.scaleFactors[paramInt2] * 255.0F / j * this.scaleFactors[3] + 0.5F);
/*      */     
/*      */     }
/*  328 */     else if (this.scaleFactors[paramInt2] != 1.0F) {
/*  329 */       i = (int)(i * this.scaleFactors[paramInt2] + 0.5F);
/*      */     } 
/*  331 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private int getsRGBComponentFromLinearRGB(int paramInt1, int paramInt2) {
/*  336 */     int i = (paramInt1 & this.maskArray[paramInt2]) >>> this.maskOffsets[paramInt2];
/*  337 */     if (this.isAlphaPremultiplied) {
/*  338 */       float f = ((1 << this.lRGBprecision) - 1);
/*  339 */       int j = (paramInt1 & this.maskArray[3]) >>> this.maskOffsets[3];
/*  340 */       i = (j == 0) ? 0 : (int)(i * this.scaleFactors[paramInt2] * f / j * this.scaleFactors[3] + 0.5F);
/*      */     
/*      */     }
/*  343 */     else if (this.nBits[paramInt2] != this.lRGBprecision) {
/*  344 */       if (this.lRGBprecision == 16) {
/*  345 */         i = (int)(i * this.scaleFactors[paramInt2] * 257.0F + 0.5F);
/*      */       } else {
/*  347 */         i = (int)(i * this.scaleFactors[paramInt2] + 0.5F);
/*      */       } 
/*      */     } 
/*      */     
/*  351 */     return this.tosRGB8LUT[i] & 0xFF;
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
/*      */   public final int getRed(int paramInt) {
/*  369 */     if (this.is_sRGB)
/*  370 */       return getsRGBComponentFromsRGB(paramInt, 0); 
/*  371 */     if (this.is_LinearRGB) {
/*  372 */       return getsRGBComponentFromLinearRGB(paramInt, 0);
/*      */     }
/*  374 */     float[] arrayOfFloat = getDefaultRGBComponents(paramInt);
/*  375 */     return (int)(arrayOfFloat[0] * 255.0F + 0.5F);
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
/*      */   public final int getGreen(int paramInt) {
/*  392 */     if (this.is_sRGB)
/*  393 */       return getsRGBComponentFromsRGB(paramInt, 1); 
/*  394 */     if (this.is_LinearRGB) {
/*  395 */       return getsRGBComponentFromLinearRGB(paramInt, 1);
/*      */     }
/*  397 */     float[] arrayOfFloat = getDefaultRGBComponents(paramInt);
/*  398 */     return (int)(arrayOfFloat[1] * 255.0F + 0.5F);
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
/*      */   public final int getBlue(int paramInt) {
/*  415 */     if (this.is_sRGB)
/*  416 */       return getsRGBComponentFromsRGB(paramInt, 2); 
/*  417 */     if (this.is_LinearRGB) {
/*  418 */       return getsRGBComponentFromLinearRGB(paramInt, 2);
/*      */     }
/*  420 */     float[] arrayOfFloat = getDefaultRGBComponents(paramInt);
/*  421 */     return (int)(arrayOfFloat[2] * 255.0F + 0.5F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getAlpha(int paramInt) {
/*  432 */     if (!this.supportsAlpha) return 255; 
/*  433 */     int i = (paramInt & this.maskArray[3]) >>> this.maskOffsets[3];
/*  434 */     if (this.scaleFactors[3] != 1.0F) {
/*  435 */       i = (int)(i * this.scaleFactors[3] + 0.5F);
/*      */     }
/*  437 */     return i;
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
/*      */   public final int getRGB(int paramInt) {
/*  454 */     if (this.is_sRGB || this.is_LinearRGB) {
/*  455 */       return getAlpha(paramInt) << 24 | 
/*  456 */         getRed(paramInt) << 16 | 
/*  457 */         getGreen(paramInt) << 8 | 
/*  458 */         getBlue(paramInt) << 0;
/*      */     }
/*  460 */     float[] arrayOfFloat = getDefaultRGBComponents(paramInt);
/*  461 */     return getAlpha(paramInt) << 24 | (int)(arrayOfFloat[0] * 255.0F + 0.5F) << 16 | (int)(arrayOfFloat[1] * 255.0F + 0.5F) << 8 | (int)(arrayOfFloat[2] * 255.0F + 0.5F) << 0;
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
/*      */   public int getRed(Object paramObject) {
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/*  499 */     int arrayOfInt[], i = 0;
/*  500 */     switch (this.transferType) {
/*      */       case 0:
/*  502 */         arrayOfByte = (byte[])paramObject;
/*  503 */         i = arrayOfByte[0] & 0xFF;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  517 */         return getRed(i);case 1: arrayOfShort = (short[])paramObject; i = arrayOfShort[0] & 0xFFFF; return getRed(i);case 3: arrayOfInt = (int[])paramObject; i = arrayOfInt[0]; return getRed(i);
/*      */     } 
/*      */     throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
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
/*      */   public int getGreen(Object paramObject) {
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/*  552 */     int arrayOfInt[], i = 0;
/*  553 */     switch (this.transferType) {
/*      */       case 0:
/*  555 */         arrayOfByte = (byte[])paramObject;
/*  556 */         i = arrayOfByte[0] & 0xFF;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  570 */         return getGreen(i);case 1: arrayOfShort = (short[])paramObject; i = arrayOfShort[0] & 0xFFFF; return getGreen(i);case 3: arrayOfInt = (int[])paramObject; i = arrayOfInt[0]; return getGreen(i);
/*      */     } 
/*      */     throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
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
/*      */   public int getBlue(Object paramObject) {
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/*  605 */     int arrayOfInt[], i = 0;
/*  606 */     switch (this.transferType) {
/*      */       case 0:
/*  608 */         arrayOfByte = (byte[])paramObject;
/*  609 */         i = arrayOfByte[0] & 0xFF;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  623 */         return getBlue(i);case 1: arrayOfShort = (short[])paramObject; i = arrayOfShort[0] & 0xFFFF; return getBlue(i);case 3: arrayOfInt = (int[])paramObject; i = arrayOfInt[0]; return getBlue(i);
/*      */     } 
/*      */     throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
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
/*      */   public int getAlpha(Object paramObject) {
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/*  655 */     int arrayOfInt[], i = 0;
/*  656 */     switch (this.transferType) {
/*      */       case 0:
/*  658 */         arrayOfByte = (byte[])paramObject;
/*  659 */         i = arrayOfByte[0] & 0xFF;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  673 */         return getAlpha(i);case 1: arrayOfShort = (short[])paramObject; i = arrayOfShort[0] & 0xFFFF; return getAlpha(i);case 3: arrayOfInt = (int[])paramObject; i = arrayOfInt[0]; return getAlpha(i);
/*      */     } 
/*      */     throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
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
/*      */   public int getRGB(Object paramObject) {
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/*  703 */     int arrayOfInt[], i = 0;
/*  704 */     switch (this.transferType) {
/*      */       case 0:
/*  706 */         arrayOfByte = (byte[])paramObject;
/*  707 */         i = arrayOfByte[0] & 0xFF;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  721 */         return getRGB(i);case 1: arrayOfShort = (short[])paramObject; i = arrayOfShort[0] & 0xFFFF; return getRGB(i);case 3: arrayOfInt = (int[])paramObject; i = arrayOfInt[0]; return getRGB(i);
/*      */     } 
/*      */     throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
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
/*      */   public Object getDataElements(int paramInt, Object paramObject) {
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/*  761 */     int[] arrayOfInt = null;
/*  762 */     if (this.transferType == 3 && paramObject != null) {
/*      */       
/*  764 */       arrayOfInt = (int[])paramObject;
/*  765 */       arrayOfInt[0] = 0;
/*      */     } else {
/*  767 */       arrayOfInt = new int[1];
/*      */     } 
/*      */     
/*  770 */     ColorModel colorModel = ColorModel.getRGBdefault();
/*  771 */     if (this == colorModel || equals(colorModel)) {
/*  772 */       arrayOfInt[0] = paramInt;
/*  773 */       return arrayOfInt;
/*      */     } 
/*      */ 
/*      */     
/*  777 */     int i = paramInt >> 16 & 0xFF;
/*  778 */     int j = paramInt >> 8 & 0xFF;
/*  779 */     int k = paramInt & 0xFF;
/*  780 */     if (this.is_sRGB || this.is_LinearRGB) {
/*      */       byte b;
/*      */       float f;
/*  783 */       if (this.is_LinearRGB) {
/*  784 */         if (this.lRGBprecision == 8) {
/*  785 */           i = this.fromsRGB8LUT8[i] & 0xFF;
/*  786 */           j = this.fromsRGB8LUT8[j] & 0xFF;
/*  787 */           k = this.fromsRGB8LUT8[k] & 0xFF;
/*  788 */           b = 8;
/*  789 */           f = 0.003921569F;
/*      */         } else {
/*  791 */           i = this.fromsRGB8LUT16[i] & 0xFFFF;
/*  792 */           j = this.fromsRGB8LUT16[j] & 0xFFFF;
/*  793 */           k = this.fromsRGB8LUT16[k] & 0xFFFF;
/*  794 */           b = 16;
/*  795 */           f = 1.5259022E-5F;
/*      */         } 
/*      */       } else {
/*  798 */         b = 8;
/*  799 */         f = 0.003921569F;
/*      */       } 
/*  801 */       if (this.supportsAlpha) {
/*  802 */         int m = paramInt >> 24 & 0xFF;
/*  803 */         if (this.isAlphaPremultiplied) {
/*  804 */           f *= m * 0.003921569F;
/*  805 */           b = -1;
/*      */         } 
/*  807 */         if (this.nBits[3] != 8) {
/*  808 */           m = (int)(m * 0.003921569F * ((1 << this.nBits[3]) - 1) + 0.5F);
/*      */           
/*  810 */           if (m > (1 << this.nBits[3]) - 1)
/*      */           {
/*  812 */             m = (1 << this.nBits[3]) - 1;
/*      */           }
/*      */         } 
/*  815 */         arrayOfInt[0] = m << this.maskOffsets[3];
/*      */       } 
/*  817 */       if (this.nBits[0] != b) {
/*  818 */         i = (int)(i * f * ((1 << this.nBits[0]) - 1) + 0.5F);
/*      */       }
/*  820 */       if (this.nBits[1] != b) {
/*  821 */         j = (int)(j * f * ((1 << this.nBits[1]) - 1) + 0.5F);
/*      */       }
/*  823 */       if (this.nBits[2] != b) {
/*  824 */         k = (int)(k * f * ((1 << this.nBits[2]) - 1) + 0.5F);
/*      */       }
/*      */     } else {
/*      */       
/*  828 */       float[] arrayOfFloat = new float[3];
/*  829 */       float f = 0.003921569F;
/*  830 */       arrayOfFloat[0] = i * f;
/*  831 */       arrayOfFloat[1] = j * f;
/*  832 */       arrayOfFloat[2] = k * f;
/*  833 */       arrayOfFloat = this.colorSpace.fromRGB(arrayOfFloat);
/*  834 */       if (this.supportsAlpha) {
/*  835 */         int m = paramInt >> 24 & 0xFF;
/*  836 */         if (this.isAlphaPremultiplied) {
/*  837 */           f *= m;
/*  838 */           for (byte b = 0; b < 3; b++) {
/*  839 */             arrayOfFloat[b] = arrayOfFloat[b] * f;
/*      */           }
/*      */         } 
/*  842 */         if (this.nBits[3] != 8) {
/*  843 */           m = (int)(m * 0.003921569F * ((1 << this.nBits[3]) - 1) + 0.5F);
/*      */           
/*  845 */           if (m > (1 << this.nBits[3]) - 1)
/*      */           {
/*  847 */             m = (1 << this.nBits[3]) - 1;
/*      */           }
/*      */         } 
/*  850 */         arrayOfInt[0] = m << this.maskOffsets[3];
/*      */       } 
/*  852 */       i = (int)(arrayOfFloat[0] * ((1 << this.nBits[0]) - 1) + 0.5F);
/*  853 */       j = (int)(arrayOfFloat[1] * ((1 << this.nBits[1]) - 1) + 0.5F);
/*  854 */       k = (int)(arrayOfFloat[2] * ((1 << this.nBits[2]) - 1) + 0.5F);
/*      */     } 
/*      */     
/*  857 */     if (this.maxBits > 23) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  862 */       if (i > (1 << this.nBits[0]) - 1) {
/*  863 */         i = (1 << this.nBits[0]) - 1;
/*      */       }
/*  865 */       if (j > (1 << this.nBits[1]) - 1) {
/*  866 */         j = (1 << this.nBits[1]) - 1;
/*      */       }
/*  868 */       if (k > (1 << this.nBits[2]) - 1) {
/*  869 */         k = (1 << this.nBits[2]) - 1;
/*      */       }
/*      */     } 
/*      */     
/*  873 */     arrayOfInt[0] = arrayOfInt[0] | i << this.maskOffsets[0] | j << this.maskOffsets[1] | k << this.maskOffsets[2];
/*      */ 
/*      */ 
/*      */     
/*  877 */     switch (this.transferType) {
/*      */       
/*      */       case 0:
/*  880 */         if (paramObject == null) {
/*  881 */           arrayOfByte = new byte[1];
/*      */         } else {
/*  883 */           arrayOfByte = (byte[])paramObject;
/*      */         } 
/*  885 */         arrayOfByte[0] = (byte)(0xFF & arrayOfInt[0]);
/*  886 */         return arrayOfByte;
/*      */ 
/*      */       
/*      */       case 1:
/*  890 */         if (paramObject == null) {
/*  891 */           arrayOfShort = new short[1];
/*      */         } else {
/*  893 */           arrayOfShort = (short[])paramObject;
/*      */         } 
/*  895 */         arrayOfShort[0] = (short)(arrayOfInt[0] & 0xFFFF);
/*  896 */         return arrayOfShort;
/*      */       
/*      */       case 3:
/*  899 */         return arrayOfInt;
/*      */     } 
/*  901 */     throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
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
/*      */   public final int[] getComponents(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  927 */     if (paramArrayOfint == null) {
/*  928 */       paramArrayOfint = new int[paramInt2 + this.numComponents];
/*      */     }
/*      */     
/*  931 */     for (byte b = 0; b < this.numComponents; b++) {
/*  932 */       paramArrayOfint[paramInt2 + b] = (paramInt1 & this.maskArray[b]) >>> this.maskOffsets[b];
/*      */     }
/*      */     
/*  935 */     return paramArrayOfint;
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
/*      */   public final int[] getComponents(Object paramObject, int[] paramArrayOfint, int paramInt) {
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/*  979 */     int arrayOfInt[], i = 0;
/*  980 */     switch (this.transferType) {
/*      */       case 0:
/*  982 */         arrayOfByte = (byte[])paramObject;
/*  983 */         i = arrayOfByte[0] & 0xFF;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  997 */         return getComponents(i, paramArrayOfint, paramInt);case 1: arrayOfShort = (short[])paramObject; i = arrayOfShort[0] & 0xFFFF; return getComponents(i, paramArrayOfint, paramInt);case 3: arrayOfInt = (int[])paramObject; i = arrayOfInt[0]; return getComponents(i, paramArrayOfint, paramInt);
/*      */     } 
/*      */     throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
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
/*      */   public final WritableRaster createCompatibleWritableRaster(int paramInt1, int paramInt2) {
/*      */     int[] arrayOfInt;
/* 1015 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/* 1016 */       throw new IllegalArgumentException("Width (" + paramInt1 + ") and height (" + paramInt2 + ") cannot be <= 0");
/*      */     }
/*      */ 
/*      */     
/* 1020 */     if (this.supportsAlpha) {
/* 1021 */       arrayOfInt = new int[4];
/* 1022 */       arrayOfInt[3] = this.alpha_mask;
/*      */     } else {
/*      */       
/* 1025 */       arrayOfInt = new int[3];
/*      */     } 
/* 1027 */     arrayOfInt[0] = this.red_mask;
/* 1028 */     arrayOfInt[1] = this.green_mask;
/* 1029 */     arrayOfInt[2] = this.blue_mask;
/*      */     
/* 1031 */     if (this.pixel_bits > 16) {
/* 1032 */       return Raster.createPackedRaster(3, paramInt1, paramInt2, arrayOfInt, (Point)null);
/*      */     }
/*      */     
/* 1035 */     if (this.pixel_bits > 8) {
/* 1036 */       return Raster.createPackedRaster(1, paramInt1, paramInt2, arrayOfInt, (Point)null);
/*      */     }
/*      */ 
/*      */     
/* 1040 */     return Raster.createPackedRaster(0, paramInt1, paramInt2, arrayOfInt, (Point)null);
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
/*      */   public int getDataElement(int[] paramArrayOfint, int paramInt) {
/* 1064 */     int i = 0;
/* 1065 */     for (byte b = 0; b < this.numComponents; b++) {
/* 1066 */       i |= paramArrayOfint[paramInt + b] << this.maskOffsets[b] & this.maskArray[b];
/*      */     }
/* 1068 */     return i;
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
/*      */   public Object getDataElements(int[] paramArrayOfint, int paramInt, Object paramObject) {
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/* 1114 */     int i = 0;
/* 1115 */     for (byte b = 0; b < this.numComponents; b++) {
/* 1116 */       i |= paramArrayOfint[paramInt + b] << this.maskOffsets[b] & this.maskArray[b];
/*      */     }
/* 1118 */     switch (this.transferType) {
/*      */       case 0:
/* 1120 */         if (paramObject instanceof byte[]) {
/* 1121 */           byte[] arrayOfByte1 = (byte[])paramObject;
/* 1122 */           arrayOfByte1[0] = (byte)(i & 0xFF);
/* 1123 */           return arrayOfByte1;
/*      */         } 
/* 1125 */         return new byte[] { (byte)(i & 0xFF) };
/*      */ 
/*      */       
/*      */       case 1:
/* 1129 */         if (paramObject instanceof short[]) {
/* 1130 */           short[] arrayOfShort1 = (short[])paramObject;
/* 1131 */           arrayOfShort1[0] = (short)(i & 0xFFFF);
/* 1132 */           return arrayOfShort1;
/*      */         } 
/* 1134 */         return new short[] { (short)(i & 0xFFFF) };
/*      */ 
/*      */       
/*      */       case 3:
/* 1138 */         if (paramObject instanceof int[]) {
/* 1139 */           int[] arrayOfInt = (int[])paramObject;
/* 1140 */           arrayOfInt[0] = i;
/* 1141 */           return arrayOfInt;
/*      */         } 
/* 1143 */         return new int[] { i };
/*      */     } 
/*      */ 
/*      */     
/* 1147 */     throw new ClassCastException("This method has not been implemented for transferType " + this.transferType);
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
/*      */   public final ColorModel coerceData(WritableRaster paramWritableRaster, boolean paramBoolean) {
/*      */     byte b;
/* 1179 */     if (!this.supportsAlpha || 
/* 1180 */       isAlphaPremultiplied() == paramBoolean) {
/* 1181 */       return this;
/*      */     }
/*      */     
/* 1184 */     int i = paramWritableRaster.getWidth();
/* 1185 */     int j = paramWritableRaster.getHeight();
/* 1186 */     int k = this.numColorComponents;
/*      */     
/* 1188 */     float f = 1.0F / ((1 << this.nBits[k]) - 1);
/*      */     
/* 1190 */     int m = paramWritableRaster.getMinX();
/* 1191 */     int n = paramWritableRaster.getMinY();
/*      */     
/* 1193 */     int[] arrayOfInt1 = null;
/* 1194 */     int[] arrayOfInt2 = null;
/*      */     
/* 1196 */     if (paramBoolean)
/*      */     { byte b1;
/*      */       
/* 1199 */       switch (this.transferType)
/*      */       { case 0:
/* 1201 */           for (b1 = 0; b1 < j; b1++, n++) {
/* 1202 */             int i1 = m;
/* 1203 */             for (byte b2 = 0; b2 < i; b2++, i1++) {
/* 1204 */               arrayOfInt1 = paramWritableRaster.getPixel(i1, n, arrayOfInt1);
/* 1205 */               float f1 = arrayOfInt1[k] * f;
/* 1206 */               if (f1 != 0.0F) {
/* 1207 */                 for (byte b3 = 0; b3 < k; b3++) {
/* 1208 */                   arrayOfInt1[b3] = (int)(arrayOfInt1[b3] * f1 + 0.5F);
/*      */                 }
/*      */                 
/* 1211 */                 paramWritableRaster.setPixel(i1, n, arrayOfInt1);
/*      */               } else {
/* 1213 */                 if (arrayOfInt2 == null) {
/* 1214 */                   arrayOfInt2 = new int[this.numComponents];
/* 1215 */                   Arrays.fill(arrayOfInt2, 0);
/*      */                 } 
/* 1217 */                 paramWritableRaster.setPixel(i1, n, arrayOfInt2);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1338 */           return new DirectColorModel(this.colorSpace, this.pixel_bits, this.maskArray[0], this.maskArray[1], this.maskArray[2], this.maskArray[3], paramBoolean, this.transferType);case 1: for (b1 = 0; b1 < j; b1++, n++) { int i1 = m; for (byte b2 = 0; b2 < i; b2++, i1++) { arrayOfInt1 = paramWritableRaster.getPixel(i1, n, arrayOfInt1); float f1 = arrayOfInt1[k] * f; if (f1 != 0.0F) { for (byte b3 = 0; b3 < k; b3++) arrayOfInt1[b3] = (int)(arrayOfInt1[b3] * f1 + 0.5F);  paramWritableRaster.setPixel(i1, n, arrayOfInt1); } else { if (arrayOfInt2 == null) { arrayOfInt2 = new int[this.numComponents]; Arrays.fill(arrayOfInt2, 0); }  paramWritableRaster.setPixel(i1, n, arrayOfInt2); }  }  }  return new DirectColorModel(this.colorSpace, this.pixel_bits, this.maskArray[0], this.maskArray[1], this.maskArray[2], this.maskArray[3], paramBoolean, this.transferType);case 3: for (b1 = 0; b1 < j; b1++, n++) { int i1 = m; for (byte b2 = 0; b2 < i; b2++, i1++) { arrayOfInt1 = paramWritableRaster.getPixel(i1, n, arrayOfInt1); float f1 = arrayOfInt1[k] * f; if (f1 != 0.0F) { for (byte b3 = 0; b3 < k; b3++) arrayOfInt1[b3] = (int)(arrayOfInt1[b3] * f1 + 0.5F);  paramWritableRaster.setPixel(i1, n, arrayOfInt1); } else { if (arrayOfInt2 == null) { arrayOfInt2 = new int[this.numComponents]; Arrays.fill(arrayOfInt2, 0); }  paramWritableRaster.setPixel(i1, n, arrayOfInt2); }  }  }  return new DirectColorModel(this.colorSpace, this.pixel_bits, this.maskArray[0], this.maskArray[1], this.maskArray[2], this.maskArray[3], paramBoolean, this.transferType); }  throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType); }  switch (this.transferType) { case 0: for (b = 0; b < j; b++, n++) { int i1 = m; for (byte b1 = 0; b1 < i; b1++, i1++) { arrayOfInt1 = paramWritableRaster.getPixel(i1, n, arrayOfInt1); float f1 = arrayOfInt1[k] * f; if (f1 != 0.0F) { float f2 = 1.0F / f1; for (byte b2 = 0; b2 < k; b2++) arrayOfInt1[b2] = (int)(arrayOfInt1[b2] * f2 + 0.5F);  paramWritableRaster.setPixel(i1, n, arrayOfInt1); }  }  }  return new DirectColorModel(this.colorSpace, this.pixel_bits, this.maskArray[0], this.maskArray[1], this.maskArray[2], this.maskArray[3], paramBoolean, this.transferType);case 1: for (b = 0; b < j; b++, n++) { int i1 = m; for (byte b1 = 0; b1 < i; b1++, i1++) { arrayOfInt1 = paramWritableRaster.getPixel(i1, n, arrayOfInt1); float f1 = arrayOfInt1[k] * f; if (f1 != 0.0F) { float f2 = 1.0F / f1; for (byte b2 = 0; b2 < k; b2++) arrayOfInt1[b2] = (int)(arrayOfInt1[b2] * f2 + 0.5F);  paramWritableRaster.setPixel(i1, n, arrayOfInt1); }  }  }  return new DirectColorModel(this.colorSpace, this.pixel_bits, this.maskArray[0], this.maskArray[1], this.maskArray[2], this.maskArray[3], paramBoolean, this.transferType);case 3: for (b = 0; b < j; b++, n++) { int i1 = m; for (byte b1 = 0; b1 < i; b1++, i1++) { arrayOfInt1 = paramWritableRaster.getPixel(i1, n, arrayOfInt1); float f1 = arrayOfInt1[k] * f; if (f1 != 0.0F) { float f2 = 1.0F / f1; for (byte b2 = 0; b2 < k; b2++) arrayOfInt1[b2] = (int)(arrayOfInt1[b2] * f2 + 0.5F);  paramWritableRaster.setPixel(i1, n, arrayOfInt1); }  }  }  return new DirectColorModel(this.colorSpace, this.pixel_bits, this.maskArray[0], this.maskArray[1], this.maskArray[2], this.maskArray[3], paramBoolean, this.transferType); }
/*      */     
/*      */     throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
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
/*      */   public boolean isCompatibleRaster(Raster paramRaster) {
/*      */     SinglePixelPackedSampleModel singlePixelPackedSampleModel;
/* 1354 */     SampleModel sampleModel = paramRaster.getSampleModel();
/*      */     
/* 1356 */     if (sampleModel instanceof SinglePixelPackedSampleModel) {
/* 1357 */       singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)sampleModel;
/*      */     } else {
/*      */       
/* 1360 */       return false;
/*      */     } 
/* 1362 */     if (singlePixelPackedSampleModel.getNumBands() != getNumComponents()) {
/* 1363 */       return false;
/*      */     }
/*      */     
/* 1366 */     int[] arrayOfInt = singlePixelPackedSampleModel.getBitMasks();
/* 1367 */     for (byte b = 0; b < this.numComponents; b++) {
/* 1368 */       if (arrayOfInt[b] != this.maskArray[b]) {
/* 1369 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1373 */     return (paramRaster.getTransferType() == this.transferType);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setFields() {
/* 1379 */     this.red_mask = this.maskArray[0];
/* 1380 */     this.red_offset = this.maskOffsets[0];
/* 1381 */     this.green_mask = this.maskArray[1];
/* 1382 */     this.green_offset = this.maskOffsets[1];
/* 1383 */     this.blue_mask = this.maskArray[2];
/* 1384 */     this.blue_offset = this.maskOffsets[2];
/* 1385 */     if (this.nBits[0] < 8) {
/* 1386 */       this.red_scale = (1 << this.nBits[0]) - 1;
/*      */     }
/* 1388 */     if (this.nBits[1] < 8) {
/* 1389 */       this.green_scale = (1 << this.nBits[1]) - 1;
/*      */     }
/* 1391 */     if (this.nBits[2] < 8) {
/* 1392 */       this.blue_scale = (1 << this.nBits[2]) - 1;
/*      */     }
/* 1394 */     if (this.supportsAlpha) {
/* 1395 */       this.alpha_mask = this.maskArray[3];
/* 1396 */       this.alpha_offset = this.maskOffsets[3];
/* 1397 */       if (this.nBits[3] < 8) {
/* 1398 */         this.alpha_scale = (1 << this.nBits[3]) - 1;
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
/*      */   public String toString() {
/* 1410 */     return new String("DirectColorModel: rmask=" + 
/* 1411 */         Integer.toHexString(this.red_mask) + " gmask=" + 
/* 1412 */         Integer.toHexString(this.green_mask) + " bmask=" + 
/* 1413 */         Integer.toHexString(this.blue_mask) + " amask=" + 
/* 1414 */         Integer.toHexString(this.alpha_mask));
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/DirectColorModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */