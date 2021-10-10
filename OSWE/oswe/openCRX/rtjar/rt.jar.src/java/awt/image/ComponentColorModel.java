/*      */ package java.awt.image;
/*      */ 
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.awt.color.ICC_ColorSpace;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ComponentColorModel
/*      */   extends ColorModel
/*      */ {
/*      */   private boolean signed;
/*      */   private boolean is_sRGB_stdScale;
/*      */   private boolean is_LinearRGB_stdScale;
/*      */   private boolean is_LinearGray_stdScale;
/*      */   private boolean is_ICCGray_stdScale;
/*      */   private byte[] tosRGB8LUT;
/*      */   private byte[] fromsRGB8LUT8;
/*      */   private short[] fromsRGB8LUT16;
/*      */   private byte[] fromLinearGray16ToOtherGray8LUT;
/*      */   private short[] fromLinearGray16ToOtherGray16LUT;
/*      */   private boolean needScaleInit;
/*      */   private boolean noUnnorm;
/*      */   private boolean nonStdScale;
/*      */   private float[] min;
/*      */   private float[] diffMinMax;
/*      */   private float[] compOffset;
/*      */   private float[] compScale;
/*      */   
/*      */   public ComponentColorModel(ColorSpace paramColorSpace, int[] paramArrayOfint, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
/*  273 */     super(bitsHelper(paramInt2, paramColorSpace, paramBoolean1), 
/*  274 */         bitsArrayHelper(paramArrayOfint, paramInt2, paramColorSpace, paramBoolean1), paramColorSpace, paramBoolean1, paramBoolean2, paramInt1, paramInt2);
/*      */ 
/*      */     
/*  277 */     switch (paramInt2) {
/*      */       case 0:
/*      */       case 1:
/*      */       case 3:
/*  281 */         this.signed = false;
/*  282 */         this.needScaleInit = true;
/*      */         break;
/*      */       case 2:
/*  285 */         this.signed = true;
/*  286 */         this.needScaleInit = true;
/*      */         break;
/*      */       case 4:
/*      */       case 5:
/*  290 */         this.signed = true;
/*  291 */         this.needScaleInit = false;
/*  292 */         this.noUnnorm = true;
/*  293 */         this.nonStdScale = false;
/*      */         break;
/*      */       default:
/*  296 */         throw new IllegalArgumentException("This constructor is not compatible with transferType " + paramInt2);
/*      */     } 
/*      */     
/*  299 */     setupLUTs();
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
/*      */   
/*      */   public ComponentColorModel(ColorSpace paramColorSpace, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2) {
/*  351 */     this(paramColorSpace, (int[])null, paramBoolean1, paramBoolean2, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int bitsHelper(int paramInt, ColorSpace paramColorSpace, boolean paramBoolean) {
/*  358 */     int i = DataBuffer.getDataTypeSize(paramInt);
/*  359 */     int j = paramColorSpace.getNumComponents();
/*  360 */     if (paramBoolean) {
/*  361 */       j++;
/*      */     }
/*  363 */     return i * j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int[] bitsArrayHelper(int[] paramArrayOfint, int paramInt, ColorSpace paramColorSpace, boolean paramBoolean) {
/*  370 */     switch (paramInt) {
/*      */       case 0:
/*      */       case 1:
/*      */       case 3:
/*  374 */         if (paramArrayOfint != null) {
/*  375 */           return paramArrayOfint;
/*      */         }
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  381 */     int i = DataBuffer.getDataTypeSize(paramInt);
/*  382 */     int j = paramColorSpace.getNumComponents();
/*  383 */     if (paramBoolean) {
/*  384 */       j++;
/*      */     }
/*  386 */     int[] arrayOfInt = new int[j];
/*  387 */     for (byte b = 0; b < j; b++) {
/*  388 */       arrayOfInt[b] = i;
/*      */     }
/*  390 */     return arrayOfInt;
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
/*      */   private void setupLUTs() {
/*  411 */     if (this.is_sRGB) {
/*  412 */       this.is_sRGB_stdScale = true;
/*  413 */       this.nonStdScale = false;
/*  414 */     } else if (ColorModel.isLinearRGBspace(this.colorSpace)) {
/*      */ 
/*      */ 
/*      */       
/*  418 */       this.is_LinearRGB_stdScale = true;
/*  419 */       this.nonStdScale = false;
/*  420 */       if (this.transferType == 0) {
/*  421 */         this.tosRGB8LUT = ColorModel.getLinearRGB8TosRGB8LUT();
/*  422 */         this.fromsRGB8LUT8 = ColorModel.getsRGB8ToLinearRGB8LUT();
/*      */       } else {
/*  424 */         this.tosRGB8LUT = ColorModel.getLinearRGB16TosRGB8LUT();
/*  425 */         this.fromsRGB8LUT16 = ColorModel.getsRGB8ToLinearRGB16LUT();
/*      */       } 
/*  427 */     } else if (this.colorSpaceType == 6 && this.colorSpace instanceof ICC_ColorSpace && this.colorSpace
/*      */       
/*  429 */       .getMinValue(0) == 0.0F && this.colorSpace
/*  430 */       .getMaxValue(0) == 1.0F) {
/*      */ 
/*      */ 
/*      */       
/*  434 */       ICC_ColorSpace iCC_ColorSpace = (ICC_ColorSpace)this.colorSpace;
/*  435 */       this.is_ICCGray_stdScale = true;
/*  436 */       this.nonStdScale = false;
/*  437 */       this.fromsRGB8LUT16 = ColorModel.getsRGB8ToLinearRGB16LUT();
/*  438 */       if (ColorModel.isLinearGRAYspace(iCC_ColorSpace)) {
/*  439 */         this.is_LinearGray_stdScale = true;
/*  440 */         if (this.transferType == 0) {
/*  441 */           this.tosRGB8LUT = ColorModel.getGray8TosRGB8LUT(iCC_ColorSpace);
/*      */         } else {
/*  443 */           this.tosRGB8LUT = ColorModel.getGray16TosRGB8LUT(iCC_ColorSpace);
/*      */         }
/*      */       
/*  446 */       } else if (this.transferType == 0) {
/*  447 */         this.tosRGB8LUT = ColorModel.getGray8TosRGB8LUT(iCC_ColorSpace);
/*  448 */         this
/*  449 */           .fromLinearGray16ToOtherGray8LUT = ColorModel.getLinearGray16ToOtherGray8LUT(iCC_ColorSpace);
/*      */       } else {
/*  451 */         this.tosRGB8LUT = ColorModel.getGray16TosRGB8LUT(iCC_ColorSpace);
/*  452 */         this
/*  453 */           .fromLinearGray16ToOtherGray16LUT = ColorModel.getLinearGray16ToOtherGray16LUT(iCC_ColorSpace);
/*      */       }
/*      */     
/*  456 */     } else if (this.needScaleInit) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  461 */       this.nonStdScale = false; byte b;
/*  462 */       for (b = 0; b < this.numColorComponents; b++) {
/*  463 */         if (this.colorSpace.getMinValue(b) != 0.0F || this.colorSpace
/*  464 */           .getMaxValue(b) != 1.0F) {
/*  465 */           this.nonStdScale = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*  469 */       if (this.nonStdScale) {
/*  470 */         this.min = new float[this.numColorComponents];
/*  471 */         this.diffMinMax = new float[this.numColorComponents];
/*  472 */         for (b = 0; b < this.numColorComponents; b++) {
/*  473 */           this.min[b] = this.colorSpace.getMinValue(b);
/*  474 */           this.diffMinMax[b] = this.colorSpace.getMaxValue(b) - this.min[b];
/*      */         } 
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
/*      */   private void initScale() {
/*      */     float[] arrayOfFloat1, arrayOfFloat2;
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort2;
/*      */     int[] arrayOfInt;
/*      */     short[] arrayOfShort1;
/*      */     byte b2;
/*  500 */     this.needScaleInit = false;
/*  501 */     if (this.nonStdScale || this.signed) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  510 */       this.noUnnorm = true;
/*      */     } else {
/*  512 */       this.noUnnorm = false;
/*      */     } 
/*      */     
/*  515 */     switch (this.transferType) {
/*      */       
/*      */       case 0:
/*  518 */         arrayOfByte = new byte[this.numComponents];
/*  519 */         for (b2 = 0; b2 < this.numColorComponents; b2++) {
/*  520 */           arrayOfByte[b2] = 0;
/*      */         }
/*  522 */         if (this.supportsAlpha) {
/*  523 */           arrayOfByte[this.numColorComponents] = (byte)((1 << this.nBits[this.numColorComponents]) - 1);
/*      */         }
/*      */         
/*  526 */         arrayOfFloat1 = getNormalizedComponents(arrayOfByte, (float[])null, 0);
/*  527 */         for (b2 = 0; b2 < this.numColorComponents; b2++) {
/*  528 */           arrayOfByte[b2] = (byte)((1 << this.nBits[b2]) - 1);
/*      */         }
/*  530 */         arrayOfFloat2 = getNormalizedComponents(arrayOfByte, (float[])null, 0);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/*  535 */         arrayOfShort2 = new short[this.numComponents];
/*  536 */         for (b2 = 0; b2 < this.numColorComponents; b2++) {
/*  537 */           arrayOfShort2[b2] = 0;
/*      */         }
/*  539 */         if (this.supportsAlpha) {
/*  540 */           arrayOfShort2[this.numColorComponents] = (short)((1 << this.nBits[this.numColorComponents]) - 1);
/*      */         }
/*      */         
/*  543 */         arrayOfFloat1 = getNormalizedComponents(arrayOfShort2, (float[])null, 0);
/*  544 */         for (b2 = 0; b2 < this.numColorComponents; b2++) {
/*  545 */           arrayOfShort2[b2] = (short)((1 << this.nBits[b2]) - 1);
/*      */         }
/*  547 */         arrayOfFloat2 = getNormalizedComponents(arrayOfShort2, (float[])null, 0);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/*  552 */         arrayOfInt = new int[this.numComponents];
/*  553 */         for (b2 = 0; b2 < this.numColorComponents; b2++) {
/*  554 */           arrayOfInt[b2] = 0;
/*      */         }
/*  556 */         if (this.supportsAlpha) {
/*  557 */           arrayOfInt[this.numColorComponents] = (1 << this.nBits[this.numColorComponents]) - 1;
/*      */         }
/*      */         
/*  560 */         arrayOfFloat1 = getNormalizedComponents(arrayOfInt, (float[])null, 0);
/*  561 */         for (b2 = 0; b2 < this.numColorComponents; b2++) {
/*  562 */           arrayOfInt[b2] = (1 << this.nBits[b2]) - 1;
/*      */         }
/*  564 */         arrayOfFloat2 = getNormalizedComponents(arrayOfInt, (float[])null, 0);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/*  569 */         arrayOfShort1 = new short[this.numComponents];
/*  570 */         for (b2 = 0; b2 < this.numColorComponents; b2++) {
/*  571 */           arrayOfShort1[b2] = 0;
/*      */         }
/*  573 */         if (this.supportsAlpha) {
/*  574 */           arrayOfShort1[this.numColorComponents] = Short.MAX_VALUE;
/*      */         }
/*  576 */         arrayOfFloat1 = getNormalizedComponents(arrayOfShort1, (float[])null, 0);
/*  577 */         for (b2 = 0; b2 < this.numColorComponents; b2++) {
/*  578 */           arrayOfShort1[b2] = Short.MAX_VALUE;
/*      */         }
/*  580 */         arrayOfFloat2 = getNormalizedComponents(arrayOfShort1, (float[])null, 0);
/*      */         break;
/*      */       
/*      */       default:
/*  584 */         arrayOfFloat1 = arrayOfFloat2 = null;
/*      */         break;
/*      */     } 
/*  587 */     this.nonStdScale = false; byte b1;
/*  588 */     for (b1 = 0; b1 < this.numColorComponents; b1++) {
/*  589 */       if (arrayOfFloat1[b1] != 0.0F || arrayOfFloat2[b1] != 1.0F) {
/*  590 */         this.nonStdScale = true;
/*      */         break;
/*      */       } 
/*      */     } 
/*  594 */     if (this.nonStdScale) {
/*  595 */       this.noUnnorm = true;
/*  596 */       this.is_sRGB_stdScale = false;
/*  597 */       this.is_LinearRGB_stdScale = false;
/*  598 */       this.is_LinearGray_stdScale = false;
/*  599 */       this.is_ICCGray_stdScale = false;
/*  600 */       this.compOffset = new float[this.numColorComponents];
/*  601 */       this.compScale = new float[this.numColorComponents];
/*  602 */       for (b1 = 0; b1 < this.numColorComponents; b1++) {
/*  603 */         this.compOffset[b1] = arrayOfFloat1[b1];
/*  604 */         this.compScale[b1] = 1.0F / (arrayOfFloat2[b1] - arrayOfFloat1[b1]);
/*      */       } 
/*      */     }  } private int getRGBComponent(int paramInt1, int paramInt2) { short[] arrayOfShort1; int[] arrayOfInt1;
/*      */     byte[] arrayOfByte2;
/*      */     short[] arrayOfShort2;
/*      */     int[] arrayOfInt2;
/*  610 */     if (this.numComponents > 1) {
/*  611 */       throw new IllegalArgumentException("More than one component per pixel");
/*      */     }
/*      */     
/*  614 */     if (this.signed) {
/*  615 */       throw new IllegalArgumentException("Component value is signed");
/*      */     }
/*      */     
/*  618 */     if (this.needScaleInit) {
/*  619 */       initScale();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  624 */     byte[] arrayOfByte1 = null;
/*  625 */     switch (this.transferType) {
/*      */       
/*      */       case 0:
/*  628 */         arrayOfByte2 = new byte[] { (byte)paramInt1 };
/*  629 */         arrayOfByte1 = arrayOfByte2;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/*  634 */         arrayOfShort2 = new short[] { (short)paramInt1 };
/*  635 */         arrayOfShort1 = arrayOfShort2;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/*  640 */         arrayOfInt2 = new int[] { paramInt1 };
/*  641 */         arrayOfInt1 = arrayOfInt2;
/*      */         break;
/*      */     } 
/*      */     
/*  645 */     float[] arrayOfFloat1 = getNormalizedComponents(arrayOfInt1, (float[])null, 0);
/*  646 */     float[] arrayOfFloat2 = this.colorSpace.toRGB(arrayOfFloat1);
/*      */     
/*  648 */     return (int)(arrayOfFloat2[paramInt2] * 255.0F + 0.5F); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRed(int paramInt) {
/*  670 */     return getRGBComponent(paramInt, 0);
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
/*      */   public int getGreen(int paramInt) {
/*  692 */     return getRGBComponent(paramInt, 1);
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
/*      */   public int getBlue(int paramInt) {
/*  714 */     return getRGBComponent(paramInt, 2);
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
/*      */   public int getAlpha(int paramInt) {
/*  731 */     if (!this.supportsAlpha) {
/*  732 */       return 255;
/*      */     }
/*  734 */     if (this.numComponents > 1) {
/*  735 */       throw new IllegalArgumentException("More than one component per pixel");
/*      */     }
/*      */     
/*  738 */     if (this.signed) {
/*  739 */       throw new IllegalArgumentException("Component value is signed");
/*      */     }
/*      */ 
/*      */     
/*  743 */     return (int)(paramInt / ((1 << this.nBits[0]) - 1) * 255.0F + 0.5F);
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
/*      */   public int getRGB(int paramInt) {
/*  763 */     if (this.numComponents > 1) {
/*  764 */       throw new IllegalArgumentException("More than one component per pixel");
/*      */     }
/*      */     
/*  767 */     if (this.signed) {
/*  768 */       throw new IllegalArgumentException("Component value is signed");
/*      */     }
/*      */ 
/*      */     
/*  772 */     return getAlpha(paramInt) << 24 | 
/*  773 */       getRed(paramInt) << 16 | 
/*  774 */       getGreen(paramInt) << 8 | 
/*  775 */       getBlue(paramInt) << 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int extractComponent(Object paramObject, int paramInt1, int paramInt2) {
/*      */     int j;
/*      */     short[] arrayOfShort1;
/*      */     float[] arrayOfFloat;
/*      */     double[] arrayOfDouble;
/*      */     byte[] arrayOfByte;
/*      */     float f;
/*      */     double d;
/*      */     short[] arrayOfShort2;
/*      */     int[] arrayOfInt;
/*  794 */     boolean bool = (this.supportsAlpha && this.isAlphaPremultiplied) ? true : false;
/*  795 */     int i = 0;
/*      */     
/*  797 */     int k = (1 << this.nBits[paramInt1]) - 1;
/*      */     
/*  799 */     switch (this.transferType) {
/*      */ 
/*      */       
/*      */       case 2:
/*  803 */         arrayOfShort1 = (short[])paramObject;
/*  804 */         f = ((1 << paramInt2) - 1);
/*  805 */         if (bool) {
/*  806 */           short s = arrayOfShort1[this.numColorComponents];
/*  807 */           if (s != 0) {
/*  808 */             return (int)(arrayOfShort1[paramInt1] / s * f + 0.5F);
/*      */           }
/*      */           
/*  811 */           return 0;
/*      */         } 
/*      */         
/*  814 */         return (int)(arrayOfShort1[paramInt1] / 32767.0F * f + 0.5F);
/*      */ 
/*      */       
/*      */       case 4:
/*  818 */         arrayOfFloat = (float[])paramObject;
/*  819 */         f = ((1 << paramInt2) - 1);
/*  820 */         if (bool) {
/*  821 */           float f1 = arrayOfFloat[this.numColorComponents];
/*  822 */           if (f1 != 0.0F) {
/*  823 */             return (int)(arrayOfFloat[paramInt1] / f1 * f + 0.5F);
/*      */           }
/*  825 */           return 0;
/*      */         } 
/*      */         
/*  828 */         return (int)(arrayOfFloat[paramInt1] * f + 0.5F);
/*      */ 
/*      */       
/*      */       case 5:
/*  832 */         arrayOfDouble = (double[])paramObject;
/*  833 */         d = ((1 << paramInt2) - 1);
/*  834 */         if (bool) {
/*  835 */           double d1 = arrayOfDouble[this.numColorComponents];
/*  836 */           if (d1 != 0.0D) {
/*  837 */             return (int)(arrayOfDouble[paramInt1] / d1 * d + 0.5D);
/*      */           }
/*  839 */           return 0;
/*      */         } 
/*      */         
/*  842 */         return (int)(arrayOfDouble[paramInt1] * d + 0.5D);
/*      */ 
/*      */       
/*      */       case 0:
/*  846 */         arrayOfByte = (byte[])paramObject;
/*  847 */         j = arrayOfByte[paramInt1] & k;
/*  848 */         paramInt2 = 8;
/*  849 */         if (bool) {
/*  850 */           i = arrayOfByte[this.numColorComponents] & k;
/*      */         }
/*      */         break;
/*      */       case 1:
/*  854 */         arrayOfShort2 = (short[])paramObject;
/*  855 */         j = arrayOfShort2[paramInt1] & k;
/*  856 */         if (bool) {
/*  857 */           i = arrayOfShort2[this.numColorComponents] & k;
/*      */         }
/*      */         break;
/*      */       case 3:
/*  861 */         arrayOfInt = (int[])paramObject;
/*  862 */         j = arrayOfInt[paramInt1];
/*  863 */         if (bool) {
/*  864 */           i = arrayOfInt[this.numColorComponents];
/*      */         }
/*      */         break;
/*      */       default:
/*  868 */         throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */     } 
/*      */ 
/*      */     
/*  872 */     if (bool) {
/*  873 */       if (i != 0) {
/*  874 */         float f1 = ((1 << paramInt2) - 1);
/*  875 */         float f2 = j / k;
/*  876 */         float f3 = ((1 << this.nBits[this.numColorComponents]) - 1) / i;
/*      */         
/*  878 */         return (int)(f2 * f3 * f1 + 0.5F);
/*      */       } 
/*  880 */       return 0;
/*      */     } 
/*      */     
/*  883 */     if (this.nBits[paramInt1] != paramInt2) {
/*  884 */       float f1 = ((1 << paramInt2) - 1);
/*  885 */       float f2 = j / k;
/*  886 */       return (int)(f2 * f1 + 0.5F);
/*      */     } 
/*  888 */     return j;
/*      */   }
/*      */ 
/*      */   
/*      */   private int getRGBComponent(Object paramObject, int paramInt) {
/*  893 */     if (this.needScaleInit) {
/*  894 */       initScale();
/*      */     }
/*  896 */     if (this.is_sRGB_stdScale)
/*  897 */       return extractComponent(paramObject, paramInt, 8); 
/*  898 */     if (this.is_LinearRGB_stdScale) {
/*  899 */       int i = extractComponent(paramObject, paramInt, 16);
/*  900 */       return this.tosRGB8LUT[i] & 0xFF;
/*  901 */     }  if (this.is_ICCGray_stdScale) {
/*  902 */       int i = extractComponent(paramObject, 0, 16);
/*  903 */       return this.tosRGB8LUT[i] & 0xFF;
/*      */     } 
/*      */ 
/*      */     
/*  907 */     float[] arrayOfFloat1 = getNormalizedComponents(paramObject, (float[])null, 0);
/*      */     
/*  909 */     float[] arrayOfFloat2 = this.colorSpace.toRGB(arrayOfFloat1);
/*  910 */     return (int)(arrayOfFloat2[paramInt] * 255.0F + 0.5F);
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
/*      */   public int getRed(Object paramObject) {
/*  944 */     return getRGBComponent(paramObject, 0);
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
/*      */   public int getGreen(Object paramObject) {
/*  979 */     return getRGBComponent(paramObject, 1);
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
/*      */   public int getBlue(Object paramObject) {
/* 1014 */     return getRGBComponent(paramObject, 2);
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
/*      */   public int getAlpha(Object paramObject) {
/*      */     short[] arrayOfShort1;
/*      */     float[] arrayOfFloat;
/*      */     double[] arrayOfDouble;
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort2;
/*      */     int[] arrayOfInt;
/* 1045 */     if (!this.supportsAlpha) {
/* 1046 */       return 255;
/*      */     }
/*      */     
/* 1049 */     int i = 0;
/* 1050 */     int j = this.numColorComponents;
/* 1051 */     int k = (1 << this.nBits[j]) - 1;
/*      */     
/* 1053 */     switch (this.transferType) {
/*      */       case 2:
/* 1055 */         arrayOfShort1 = (short[])paramObject;
/* 1056 */         i = (int)(arrayOfShort1[j] / 32767.0F * 255.0F + 0.5F);
/* 1057 */         return i;
/*      */       case 4:
/* 1059 */         arrayOfFloat = (float[])paramObject;
/* 1060 */         i = (int)(arrayOfFloat[j] * 255.0F + 0.5F);
/* 1061 */         return i;
/*      */       case 5:
/* 1063 */         arrayOfDouble = (double[])paramObject;
/* 1064 */         i = (int)(arrayOfDouble[j] * 255.0D + 0.5D);
/* 1065 */         return i;
/*      */       case 0:
/* 1067 */         arrayOfByte = (byte[])paramObject;
/* 1068 */         i = arrayOfByte[j] & k;
/*      */         break;
/*      */       case 1:
/* 1071 */         arrayOfShort2 = (short[])paramObject;
/* 1072 */         i = arrayOfShort2[j] & k;
/*      */         break;
/*      */       case 3:
/* 1075 */         arrayOfInt = (int[])paramObject;
/* 1076 */         i = arrayOfInt[j];
/*      */         break;
/*      */       default:
/* 1079 */         throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1084 */     if (this.nBits[j] == 8) {
/* 1085 */       return i;
/*      */     }
/* 1087 */     return (int)(i / ((1 << this.nBits[j]) - 1) * 255.0F + 0.5F);
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
/*      */   public int getRGB(Object paramObject) {
/* 1126 */     if (this.needScaleInit) {
/* 1127 */       initScale();
/*      */     }
/* 1129 */     if (this.is_sRGB_stdScale || this.is_LinearRGB_stdScale)
/* 1130 */       return getAlpha(paramObject) << 24 | 
/* 1131 */         getRed(paramObject) << 16 | 
/* 1132 */         getGreen(paramObject) << 8 | 
/* 1133 */         getBlue(paramObject); 
/* 1134 */     if (this.colorSpaceType == 6) {
/* 1135 */       int i = getRed(paramObject);
/*      */       
/* 1137 */       return getAlpha(paramObject) << 24 | i << 16 | i << 8 | i;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1142 */     float[] arrayOfFloat1 = getNormalizedComponents(paramObject, (float[])null, 0);
/*      */     
/* 1144 */     float[] arrayOfFloat2 = this.colorSpace.toRGB(arrayOfFloat1);
/* 1145 */     return getAlpha(paramObject) << 24 | (int)(arrayOfFloat2[0] * 255.0F + 0.5F) << 16 | (int)(arrayOfFloat2[1] * 255.0F + 0.5F) << 8 | (int)(arrayOfFloat2[2] * 255.0F + 0.5F) << 0;
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
/*      */   public Object getDataElements(int paramInt, Object paramObject) {
/*      */     int[] arrayOfInt;
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/*      */     byte b;
/* 1189 */     int i = paramInt >> 16 & 0xFF;
/* 1190 */     int j = paramInt >> 8 & 0xFF;
/* 1191 */     int k = paramInt & 0xFF;
/*      */     
/* 1193 */     if (this.needScaleInit) {
/* 1194 */       initScale();
/*      */     }
/* 1196 */     if (this.signed) {
/*      */       short[] arrayOfShort1; float[] arrayOfFloat;
/*      */       double[] arrayOfDouble;
/* 1199 */       switch (this.transferType) {
/*      */ 
/*      */         
/*      */         case 2:
/* 1203 */           if (paramObject == null) {
/* 1204 */             arrayOfShort1 = new short[this.numComponents];
/*      */           } else {
/* 1206 */             arrayOfShort1 = (short[])paramObject;
/*      */           } 
/*      */           
/* 1209 */           if (this.is_sRGB_stdScale || this.is_LinearRGB_stdScale) {
/* 1210 */             float f = 128.49803F;
/* 1211 */             if (this.is_LinearRGB_stdScale) {
/* 1212 */               i = this.fromsRGB8LUT16[i] & 0xFFFF;
/* 1213 */               j = this.fromsRGB8LUT16[j] & 0xFFFF;
/* 1214 */               k = this.fromsRGB8LUT16[k] & 0xFFFF;
/* 1215 */               f = 0.49999237F;
/*      */             } 
/* 1217 */             if (this.supportsAlpha) {
/* 1218 */               int m = paramInt >> 24 & 0xFF;
/* 1219 */               arrayOfShort1[3] = (short)(int)(m * 128.49803F + 0.5F);
/*      */               
/* 1221 */               if (this.isAlphaPremultiplied) {
/* 1222 */                 f = m * f * 0.003921569F;
/*      */               }
/*      */             } 
/* 1225 */             arrayOfShort1[0] = (short)(int)(i * f + 0.5F);
/* 1226 */             arrayOfShort1[1] = (short)(int)(j * f + 0.5F);
/* 1227 */             arrayOfShort1[2] = (short)(int)(k * f + 0.5F);
/* 1228 */           } else if (this.is_LinearGray_stdScale) {
/* 1229 */             i = this.fromsRGB8LUT16[i] & 0xFFFF;
/* 1230 */             j = this.fromsRGB8LUT16[j] & 0xFFFF;
/* 1231 */             k = this.fromsRGB8LUT16[k] & 0xFFFF;
/* 1232 */             float f2 = (0.2125F * i + 0.7154F * j + 0.0721F * k) / 65535.0F;
/*      */ 
/*      */             
/* 1235 */             float f1 = 32767.0F;
/* 1236 */             if (this.supportsAlpha) {
/* 1237 */               int m = paramInt >> 24 & 0xFF;
/* 1238 */               arrayOfShort1[1] = (short)(int)(m * 128.49803F + 0.5F);
/*      */               
/* 1240 */               if (this.isAlphaPremultiplied) {
/* 1241 */                 f1 = m * f1 * 0.003921569F;
/*      */               }
/*      */             } 
/* 1244 */             arrayOfShort1[0] = (short)(int)(f2 * f1 + 0.5F);
/* 1245 */           } else if (this.is_ICCGray_stdScale) {
/* 1246 */             i = this.fromsRGB8LUT16[i] & 0xFFFF;
/* 1247 */             j = this.fromsRGB8LUT16[j] & 0xFFFF;
/* 1248 */             k = this.fromsRGB8LUT16[k] & 0xFFFF;
/* 1249 */             int m = (int)(0.2125F * i + 0.7154F * j + 0.0721F * k + 0.5F);
/*      */ 
/*      */             
/* 1252 */             m = this.fromLinearGray16ToOtherGray16LUT[m] & 0xFFFF;
/* 1253 */             float f = 0.49999237F;
/* 1254 */             if (this.supportsAlpha) {
/* 1255 */               int n = paramInt >> 24 & 0xFF;
/* 1256 */               arrayOfShort1[1] = (short)(int)(n * 128.49803F + 0.5F);
/*      */               
/* 1258 */               if (this.isAlphaPremultiplied) {
/* 1259 */                 f = n * f * 0.003921569F;
/*      */               }
/*      */             } 
/* 1262 */             arrayOfShort1[0] = (short)(int)(m * f + 0.5F);
/*      */           } else {
/* 1264 */             float f = 0.003921569F;
/* 1265 */             float[] arrayOfFloat1 = new float[3];
/* 1266 */             arrayOfFloat1[0] = i * f;
/* 1267 */             arrayOfFloat1[1] = j * f;
/* 1268 */             arrayOfFloat1[2] = k * f;
/* 1269 */             arrayOfFloat1 = this.colorSpace.fromRGB(arrayOfFloat1);
/* 1270 */             if (this.nonStdScale) {
/* 1271 */               for (byte b2 = 0; b2 < this.numColorComponents; b2++) {
/* 1272 */                 arrayOfFloat1[b2] = (arrayOfFloat1[b2] - this.compOffset[b2]) * this.compScale[b2];
/*      */ 
/*      */ 
/*      */                 
/* 1276 */                 if (arrayOfFloat1[b2] < 0.0F) {
/* 1277 */                   arrayOfFloat1[b2] = 0.0F;
/*      */                 }
/* 1279 */                 if (arrayOfFloat1[b2] > 1.0F) {
/* 1280 */                   arrayOfFloat1[b2] = 1.0F;
/*      */                 }
/*      */               } 
/*      */             }
/* 1284 */             f = 32767.0F;
/* 1285 */             if (this.supportsAlpha) {
/* 1286 */               int m = paramInt >> 24 & 0xFF;
/* 1287 */               arrayOfShort1[this.numColorComponents] = (short)(int)(m * 128.49803F + 0.5F);
/*      */               
/* 1289 */               if (this.isAlphaPremultiplied) {
/* 1290 */                 f *= m * 0.003921569F;
/*      */               }
/*      */             } 
/* 1293 */             for (byte b1 = 0; b1 < this.numColorComponents; b1++) {
/* 1294 */               arrayOfShort1[b1] = (short)(int)(arrayOfFloat1[b1] * f + 0.5F);
/*      */             }
/*      */           } 
/* 1297 */           return arrayOfShort1;
/*      */ 
/*      */ 
/*      */         
/*      */         case 4:
/* 1302 */           if (paramObject == null) {
/* 1303 */             arrayOfFloat = new float[this.numComponents];
/*      */           } else {
/* 1305 */             arrayOfFloat = (float[])paramObject;
/*      */           } 
/*      */           
/* 1308 */           if (this.is_sRGB_stdScale || this.is_LinearRGB_stdScale) {
/* 1309 */             float f; if (this.is_LinearRGB_stdScale) {
/* 1310 */               i = this.fromsRGB8LUT16[i] & 0xFFFF;
/* 1311 */               j = this.fromsRGB8LUT16[j] & 0xFFFF;
/* 1312 */               k = this.fromsRGB8LUT16[k] & 0xFFFF;
/* 1313 */               f = 1.5259022E-5F;
/*      */             } else {
/* 1315 */               f = 0.003921569F;
/*      */             } 
/* 1317 */             if (this.supportsAlpha) {
/* 1318 */               int m = paramInt >> 24 & 0xFF;
/* 1319 */               arrayOfFloat[3] = m * 0.003921569F;
/* 1320 */               if (this.isAlphaPremultiplied) {
/* 1321 */                 f *= arrayOfFloat[3];
/*      */               }
/*      */             } 
/* 1324 */             arrayOfFloat[0] = i * f;
/* 1325 */             arrayOfFloat[1] = j * f;
/* 1326 */             arrayOfFloat[2] = k * f;
/* 1327 */           } else if (this.is_LinearGray_stdScale) {
/* 1328 */             i = this.fromsRGB8LUT16[i] & 0xFFFF;
/* 1329 */             j = this.fromsRGB8LUT16[j] & 0xFFFF;
/* 1330 */             k = this.fromsRGB8LUT16[k] & 0xFFFF;
/* 1331 */             arrayOfFloat[0] = (0.2125F * i + 0.7154F * j + 0.0721F * k) / 65535.0F;
/*      */ 
/*      */             
/* 1334 */             if (this.supportsAlpha) {
/* 1335 */               int m = paramInt >> 24 & 0xFF;
/* 1336 */               arrayOfFloat[1] = m * 0.003921569F;
/* 1337 */               if (this.isAlphaPremultiplied) {
/* 1338 */                 arrayOfFloat[0] = arrayOfFloat[0] * arrayOfFloat[1];
/*      */               }
/*      */             } 
/* 1341 */           } else if (this.is_ICCGray_stdScale) {
/* 1342 */             i = this.fromsRGB8LUT16[i] & 0xFFFF;
/* 1343 */             j = this.fromsRGB8LUT16[j] & 0xFFFF;
/* 1344 */             k = this.fromsRGB8LUT16[k] & 0xFFFF;
/* 1345 */             int m = (int)(0.2125F * i + 0.7154F * j + 0.0721F * k + 0.5F);
/*      */ 
/*      */             
/* 1348 */             arrayOfFloat[0] = (this.fromLinearGray16ToOtherGray16LUT[m] & 0xFFFF) / 65535.0F;
/*      */             
/* 1350 */             if (this.supportsAlpha) {
/* 1351 */               int n = paramInt >> 24 & 0xFF;
/* 1352 */               arrayOfFloat[1] = n * 0.003921569F;
/* 1353 */               if (this.isAlphaPremultiplied) {
/* 1354 */                 arrayOfFloat[0] = arrayOfFloat[0] * arrayOfFloat[1];
/*      */               }
/*      */             } 
/*      */           } else {
/* 1358 */             float[] arrayOfFloat1 = new float[3];
/* 1359 */             float f = 0.003921569F;
/* 1360 */             arrayOfFloat1[0] = i * f;
/* 1361 */             arrayOfFloat1[1] = j * f;
/* 1362 */             arrayOfFloat1[2] = k * f;
/* 1363 */             arrayOfFloat1 = this.colorSpace.fromRGB(arrayOfFloat1);
/* 1364 */             if (this.supportsAlpha) {
/* 1365 */               int m = paramInt >> 24 & 0xFF;
/* 1366 */               arrayOfFloat[this.numColorComponents] = m * f;
/* 1367 */               if (this.isAlphaPremultiplied) {
/* 1368 */                 f *= m;
/* 1369 */                 for (byte b2 = 0; b2 < this.numColorComponents; b2++) {
/* 1370 */                   arrayOfFloat1[b2] = arrayOfFloat1[b2] * f;
/*      */                 }
/*      */               } 
/*      */             } 
/* 1374 */             for (byte b1 = 0; b1 < this.numColorComponents; b1++) {
/* 1375 */               arrayOfFloat[b1] = arrayOfFloat1[b1];
/*      */             }
/*      */           } 
/* 1378 */           return arrayOfFloat;
/*      */ 
/*      */ 
/*      */         
/*      */         case 5:
/* 1383 */           if (paramObject == null) {
/* 1384 */             arrayOfDouble = new double[this.numComponents];
/*      */           } else {
/* 1386 */             arrayOfDouble = (double[])paramObject;
/*      */           } 
/* 1388 */           if (this.is_sRGB_stdScale || this.is_LinearRGB_stdScale) {
/*      */             double d;
/* 1390 */             if (this.is_LinearRGB_stdScale) {
/* 1391 */               i = this.fromsRGB8LUT16[i] & 0xFFFF;
/* 1392 */               j = this.fromsRGB8LUT16[j] & 0xFFFF;
/* 1393 */               k = this.fromsRGB8LUT16[k] & 0xFFFF;
/* 1394 */               d = 1.5259021896696422E-5D;
/*      */             } else {
/* 1396 */               d = 0.00392156862745098D;
/*      */             } 
/* 1398 */             if (this.supportsAlpha) {
/* 1399 */               int m = paramInt >> 24 & 0xFF;
/* 1400 */               arrayOfDouble[3] = m * 0.00392156862745098D;
/* 1401 */               if (this.isAlphaPremultiplied) {
/* 1402 */                 d *= arrayOfDouble[3];
/*      */               }
/*      */             } 
/* 1405 */             arrayOfDouble[0] = i * d;
/* 1406 */             arrayOfDouble[1] = j * d;
/* 1407 */             arrayOfDouble[2] = k * d;
/* 1408 */           } else if (this.is_LinearGray_stdScale) {
/* 1409 */             i = this.fromsRGB8LUT16[i] & 0xFFFF;
/* 1410 */             j = this.fromsRGB8LUT16[j] & 0xFFFF;
/* 1411 */             k = this.fromsRGB8LUT16[k] & 0xFFFF;
/* 1412 */             arrayOfDouble[0] = (0.2125D * i + 0.7154D * j + 0.0721D * k) / 65535.0D;
/*      */ 
/*      */             
/* 1415 */             if (this.supportsAlpha) {
/* 1416 */               int m = paramInt >> 24 & 0xFF;
/* 1417 */               arrayOfDouble[1] = m * 0.00392156862745098D;
/* 1418 */               if (this.isAlphaPremultiplied) {
/* 1419 */                 arrayOfDouble[0] = arrayOfDouble[0] * arrayOfDouble[1];
/*      */               }
/*      */             } 
/* 1422 */           } else if (this.is_ICCGray_stdScale) {
/* 1423 */             i = this.fromsRGB8LUT16[i] & 0xFFFF;
/* 1424 */             j = this.fromsRGB8LUT16[j] & 0xFFFF;
/* 1425 */             k = this.fromsRGB8LUT16[k] & 0xFFFF;
/* 1426 */             int m = (int)(0.2125F * i + 0.7154F * j + 0.0721F * k + 0.5F);
/*      */ 
/*      */             
/* 1429 */             arrayOfDouble[0] = (this.fromLinearGray16ToOtherGray16LUT[m] & 0xFFFF) / 65535.0D;
/*      */             
/* 1431 */             if (this.supportsAlpha) {
/* 1432 */               int n = paramInt >> 24 & 0xFF;
/* 1433 */               arrayOfDouble[1] = n * 0.00392156862745098D;
/* 1434 */               if (this.isAlphaPremultiplied) {
/* 1435 */                 arrayOfDouble[0] = arrayOfDouble[0] * arrayOfDouble[1];
/*      */               }
/*      */             } 
/*      */           } else {
/* 1439 */             float f = 0.003921569F;
/* 1440 */             float[] arrayOfFloat1 = new float[3];
/* 1441 */             arrayOfFloat1[0] = i * f;
/* 1442 */             arrayOfFloat1[1] = j * f;
/* 1443 */             arrayOfFloat1[2] = k * f;
/* 1444 */             arrayOfFloat1 = this.colorSpace.fromRGB(arrayOfFloat1);
/* 1445 */             if (this.supportsAlpha) {
/* 1446 */               int m = paramInt >> 24 & 0xFF;
/* 1447 */               arrayOfDouble[this.numColorComponents] = m * 0.00392156862745098D;
/* 1448 */               if (this.isAlphaPremultiplied) {
/* 1449 */                 f *= m;
/* 1450 */                 for (byte b2 = 0; b2 < this.numColorComponents; b2++) {
/* 1451 */                   arrayOfFloat1[b2] = arrayOfFloat1[b2] * f;
/*      */                 }
/*      */               } 
/*      */             } 
/* 1455 */             for (byte b1 = 0; b1 < this.numColorComponents; b1++) {
/* 1456 */               arrayOfDouble[b1] = arrayOfFloat1[b1];
/*      */             }
/*      */           } 
/* 1459 */           return arrayOfDouble;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/* 1468 */     if (this.transferType == 3 && paramObject != null) {
/*      */       
/* 1470 */       arrayOfInt = (int[])paramObject;
/*      */     } else {
/* 1472 */       arrayOfInt = new int[this.numComponents];
/*      */     } 
/*      */     
/* 1475 */     if (this.is_sRGB_stdScale || this.is_LinearRGB_stdScale) {
/*      */       byte b1;
/*      */       float f;
/* 1478 */       if (this.is_LinearRGB_stdScale) {
/* 1479 */         if (this.transferType == 0) {
/* 1480 */           i = this.fromsRGB8LUT8[i] & 0xFF;
/* 1481 */           j = this.fromsRGB8LUT8[j] & 0xFF;
/* 1482 */           k = this.fromsRGB8LUT8[k] & 0xFF;
/* 1483 */           b1 = 8;
/* 1484 */           f = 0.003921569F;
/*      */         } else {
/* 1486 */           i = this.fromsRGB8LUT16[i] & 0xFFFF;
/* 1487 */           j = this.fromsRGB8LUT16[j] & 0xFFFF;
/* 1488 */           k = this.fromsRGB8LUT16[k] & 0xFFFF;
/* 1489 */           b1 = 16;
/* 1490 */           f = 1.5259022E-5F;
/*      */         } 
/*      */       } else {
/* 1493 */         b1 = 8;
/* 1494 */         f = 0.003921569F;
/*      */       } 
/* 1496 */       if (this.supportsAlpha) {
/* 1497 */         int m = paramInt >> 24 & 0xFF;
/* 1498 */         if (this.nBits[3] == 8) {
/* 1499 */           arrayOfInt[3] = m;
/*      */         } else {
/*      */           
/* 1502 */           arrayOfInt[3] = (int)(m * 0.003921569F * ((1 << this.nBits[3]) - 1) + 0.5F);
/*      */         } 
/*      */         
/* 1505 */         if (this.isAlphaPremultiplied) {
/* 1506 */           f *= m * 0.003921569F;
/* 1507 */           b1 = -1;
/*      */         } 
/*      */       } 
/* 1510 */       if (this.nBits[0] == b1) {
/* 1511 */         arrayOfInt[0] = i;
/*      */       } else {
/*      */         
/* 1514 */         arrayOfInt[0] = (int)(i * f * ((1 << this.nBits[0]) - 1) + 0.5F);
/*      */       } 
/* 1516 */       if (this.nBits[1] == b1) {
/* 1517 */         arrayOfInt[1] = j;
/*      */       } else {
/*      */         
/* 1520 */         arrayOfInt[1] = (int)(j * f * ((1 << this.nBits[1]) - 1) + 0.5F);
/*      */       } 
/* 1522 */       if (this.nBits[2] == b1) {
/* 1523 */         arrayOfInt[2] = k;
/*      */       } else {
/*      */         
/* 1526 */         arrayOfInt[2] = (int)(k * f * ((1 << this.nBits[2]) - 1) + 0.5F);
/*      */       } 
/* 1528 */     } else if (this.is_LinearGray_stdScale) {
/* 1529 */       i = this.fromsRGB8LUT16[i] & 0xFFFF;
/* 1530 */       j = this.fromsRGB8LUT16[j] & 0xFFFF;
/* 1531 */       k = this.fromsRGB8LUT16[k] & 0xFFFF;
/* 1532 */       float f = (0.2125F * i + 0.7154F * j + 0.0721F * k) / 65535.0F;
/*      */ 
/*      */       
/* 1535 */       if (this.supportsAlpha) {
/* 1536 */         int m = paramInt >> 24 & 0xFF;
/* 1537 */         if (this.nBits[1] == 8) {
/* 1538 */           arrayOfInt[1] = m;
/*      */         } else {
/* 1540 */           arrayOfInt[1] = (int)(m * 0.003921569F * ((1 << this.nBits[1]) - 1) + 0.5F);
/*      */         } 
/*      */         
/* 1543 */         if (this.isAlphaPremultiplied) {
/* 1544 */           f *= m * 0.003921569F;
/*      */         }
/*      */       } 
/* 1547 */       arrayOfInt[0] = (int)(f * ((1 << this.nBits[0]) - 1) + 0.5F);
/* 1548 */     } else if (this.is_ICCGray_stdScale) {
/* 1549 */       i = this.fromsRGB8LUT16[i] & 0xFFFF;
/* 1550 */       j = this.fromsRGB8LUT16[j] & 0xFFFF;
/* 1551 */       k = this.fromsRGB8LUT16[k] & 0xFFFF;
/* 1552 */       int m = (int)(0.2125F * i + 0.7154F * j + 0.0721F * k + 0.5F);
/*      */ 
/*      */       
/* 1555 */       float f = (this.fromLinearGray16ToOtherGray16LUT[m] & 0xFFFF) / 65535.0F;
/*      */       
/* 1557 */       if (this.supportsAlpha) {
/* 1558 */         int n = paramInt >> 24 & 0xFF;
/* 1559 */         if (this.nBits[1] == 8) {
/* 1560 */           arrayOfInt[1] = n;
/*      */         } else {
/* 1562 */           arrayOfInt[1] = (int)(n * 0.003921569F * ((1 << this.nBits[1]) - 1) + 0.5F);
/*      */         } 
/*      */         
/* 1565 */         if (this.isAlphaPremultiplied) {
/* 1566 */           f *= n * 0.003921569F;
/*      */         }
/*      */       } 
/* 1569 */       arrayOfInt[0] = (int)(f * ((1 << this.nBits[0]) - 1) + 0.5F);
/*      */     } else {
/*      */       
/* 1572 */       float[] arrayOfFloat = new float[3];
/* 1573 */       float f = 0.003921569F;
/* 1574 */       arrayOfFloat[0] = i * f;
/* 1575 */       arrayOfFloat[1] = j * f;
/* 1576 */       arrayOfFloat[2] = k * f;
/* 1577 */       arrayOfFloat = this.colorSpace.fromRGB(arrayOfFloat);
/* 1578 */       if (this.nonStdScale) {
/* 1579 */         for (byte b2 = 0; b2 < this.numColorComponents; b2++) {
/* 1580 */           arrayOfFloat[b2] = (arrayOfFloat[b2] - this.compOffset[b2]) * this.compScale[b2];
/*      */ 
/*      */ 
/*      */           
/* 1584 */           if (arrayOfFloat[b2] < 0.0F) {
/* 1585 */             arrayOfFloat[b2] = 0.0F;
/*      */           }
/* 1587 */           if (arrayOfFloat[b2] > 1.0F) {
/* 1588 */             arrayOfFloat[b2] = 1.0F;
/*      */           }
/*      */         } 
/*      */       }
/* 1592 */       if (this.supportsAlpha) {
/* 1593 */         int m = paramInt >> 24 & 0xFF;
/* 1594 */         if (this.nBits[this.numColorComponents] == 8) {
/* 1595 */           arrayOfInt[this.numColorComponents] = m;
/*      */         } else {
/*      */           
/* 1598 */           arrayOfInt[this.numColorComponents] = (int)(m * f * ((1 << this.nBits[this.numColorComponents]) - 1) + 0.5F);
/*      */         } 
/*      */ 
/*      */         
/* 1602 */         if (this.isAlphaPremultiplied) {
/* 1603 */           f *= m;
/* 1604 */           for (byte b2 = 0; b2 < this.numColorComponents; b2++) {
/* 1605 */             arrayOfFloat[b2] = arrayOfFloat[b2] * f;
/*      */           }
/*      */         } 
/*      */       } 
/* 1609 */       for (byte b1 = 0; b1 < this.numColorComponents; b1++) {
/* 1610 */         arrayOfInt[b1] = (int)(arrayOfFloat[b1] * ((1 << this.nBits[b1]) - 1) + 0.5F);
/*      */       }
/*      */     } 
/*      */     
/* 1614 */     switch (this.transferType) {
/*      */       
/*      */       case 0:
/* 1617 */         if (paramObject == null) {
/* 1618 */           arrayOfByte = new byte[this.numComponents];
/*      */         } else {
/* 1620 */           arrayOfByte = (byte[])paramObject;
/*      */         } 
/* 1622 */         for (b = 0; b < this.numComponents; b++) {
/* 1623 */           arrayOfByte[b] = (byte)(0xFF & arrayOfInt[b]);
/*      */         }
/* 1625 */         return arrayOfByte;
/*      */ 
/*      */       
/*      */       case 1:
/* 1629 */         if (paramObject == null) {
/* 1630 */           arrayOfShort = new short[this.numComponents];
/*      */         } else {
/* 1632 */           arrayOfShort = (short[])paramObject;
/*      */         } 
/* 1634 */         for (b = 0; b < this.numComponents; b++) {
/* 1635 */           arrayOfShort[b] = (short)(arrayOfInt[b] & 0xFFFF);
/*      */         }
/* 1637 */         return arrayOfShort;
/*      */       
/*      */       case 3:
/* 1640 */         if (this.maxBits > 23)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1645 */           for (byte b1 = 0; b1 < this.numComponents; b1++) {
/* 1646 */             if (arrayOfInt[b1] > (1 << this.nBits[b1]) - 1) {
/* 1647 */               arrayOfInt[b1] = (1 << this.nBits[b1]) - 1;
/*      */             }
/*      */           } 
/*      */         }
/* 1651 */         return arrayOfInt;
/*      */     } 
/* 1653 */     throw new IllegalArgumentException("This method has not been implemented for transferType " + this.transferType);
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
/*      */   public int[] getComponents(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 1682 */     if (this.numComponents > 1) {
/* 1683 */       throw new IllegalArgumentException("More than one component per pixel");
/*      */     }
/*      */     
/* 1686 */     if (this.needScaleInit) {
/* 1687 */       initScale();
/*      */     }
/* 1689 */     if (this.noUnnorm) {
/* 1690 */       throw new IllegalArgumentException("This ColorModel does not support the unnormalized form");
/*      */     }
/*      */ 
/*      */     
/* 1694 */     if (paramArrayOfint == null) {
/* 1695 */       paramArrayOfint = new int[paramInt2 + 1];
/*      */     }
/*      */     
/* 1698 */     paramArrayOfint[paramInt2 + 0] = paramInt1 & (1 << this.nBits[0]) - 1;
/* 1699 */     return paramArrayOfint;
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
/*      */   public int[] getComponents(Object paramObject, int[] paramArrayOfint, int paramInt) {
/*      */     int[] arrayOfInt;
/* 1743 */     if (this.needScaleInit) {
/* 1744 */       initScale();
/*      */     }
/* 1746 */     if (this.noUnnorm) {
/* 1747 */       throw new IllegalArgumentException("This ColorModel does not support the unnormalized form");
/*      */     }
/*      */ 
/*      */     
/* 1751 */     if (paramObject instanceof int[]) {
/* 1752 */       arrayOfInt = (int[])paramObject;
/*      */     } else {
/* 1754 */       arrayOfInt = DataBuffer.toIntArray(paramObject);
/* 1755 */       if (arrayOfInt == null) {
/* 1756 */         throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */       }
/*      */     } 
/*      */     
/* 1760 */     if (arrayOfInt.length < this.numComponents) {
/* 1761 */       throw new IllegalArgumentException("Length of pixel array < number of components in model");
/*      */     }
/*      */     
/* 1764 */     if (paramArrayOfint == null) {
/* 1765 */       paramArrayOfint = new int[paramInt + this.numComponents];
/*      */     }
/* 1767 */     else if (paramArrayOfint.length - paramInt < this.numComponents) {
/* 1768 */       throw new IllegalArgumentException("Length of components array < number of components in model");
/*      */     } 
/*      */     
/* 1771 */     System.arraycopy(arrayOfInt, 0, paramArrayOfint, paramInt, this.numComponents);
/*      */     
/* 1773 */     return paramArrayOfint;
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
/*      */   public int[] getUnnormalizedComponents(float[] paramArrayOffloat, int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 1819 */     if (this.needScaleInit) {
/* 1820 */       initScale();
/*      */     }
/* 1822 */     if (this.noUnnorm) {
/* 1823 */       throw new IllegalArgumentException("This ColorModel does not support the unnormalized form");
/*      */     }
/*      */ 
/*      */     
/* 1827 */     return super.getUnnormalizedComponents(paramArrayOffloat, paramInt1, paramArrayOfint, paramInt2);
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
/*      */   public float[] getNormalizedComponents(int[] paramArrayOfint, int paramInt1, float[] paramArrayOffloat, int paramInt2) {
/* 1868 */     if (this.needScaleInit) {
/* 1869 */       initScale();
/*      */     }
/* 1871 */     if (this.noUnnorm) {
/* 1872 */       throw new IllegalArgumentException("This ColorModel does not support the unnormalized form");
/*      */     }
/*      */ 
/*      */     
/* 1876 */     return super.getNormalizedComponents(paramArrayOfint, paramInt1, paramArrayOffloat, paramInt2);
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
/*      */   public int getDataElement(int[] paramArrayOfint, int paramInt) {
/* 1895 */     if (this.needScaleInit) {
/* 1896 */       initScale();
/*      */     }
/* 1898 */     if (this.numComponents == 1) {
/* 1899 */       if (this.noUnnorm) {
/* 1900 */         throw new IllegalArgumentException("This ColorModel does not support the unnormalized form");
/*      */       }
/*      */ 
/*      */       
/* 1904 */       return paramArrayOfint[paramInt + 0];
/*      */     } 
/* 1906 */     throw new IllegalArgumentException("This model returns " + this.numComponents + " elements in the pixel array.");
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
/*      */   public Object getDataElements(int[] paramArrayOfint, int paramInt, Object paramObject) {
/*      */     int[] arrayOfInt;
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/*      */     byte b;
/* 1953 */     if (this.needScaleInit) {
/* 1954 */       initScale();
/*      */     }
/* 1956 */     if (this.noUnnorm) {
/* 1957 */       throw new IllegalArgumentException("This ColorModel does not support the unnormalized form");
/*      */     }
/*      */ 
/*      */     
/* 1961 */     if (paramArrayOfint.length - paramInt < this.numComponents) {
/* 1962 */       throw new IllegalArgumentException("Component array too small (should be " + this.numComponents);
/*      */     }
/*      */     
/* 1965 */     switch (this.transferType) {
/*      */ 
/*      */       
/*      */       case 3:
/* 1969 */         if (paramObject == null) {
/* 1970 */           arrayOfInt = new int[this.numComponents];
/*      */         } else {
/*      */           
/* 1973 */           arrayOfInt = (int[])paramObject;
/*      */         } 
/* 1975 */         System.arraycopy(paramArrayOfint, paramInt, arrayOfInt, 0, this.numComponents);
/*      */         
/* 1977 */         return arrayOfInt;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 0:
/* 1983 */         if (paramObject == null) {
/* 1984 */           arrayOfByte = new byte[this.numComponents];
/*      */         } else {
/*      */           
/* 1987 */           arrayOfByte = (byte[])paramObject;
/*      */         } 
/* 1989 */         for (b = 0; b < this.numComponents; b++) {
/* 1990 */           arrayOfByte[b] = (byte)(paramArrayOfint[paramInt + b] & 0xFF);
/*      */         }
/* 1992 */         return arrayOfByte;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/* 1998 */         if (paramObject == null) {
/* 1999 */           arrayOfShort = new short[this.numComponents];
/*      */         } else {
/*      */           
/* 2002 */           arrayOfShort = (short[])paramObject;
/*      */         } 
/* 2004 */         for (b = 0; b < this.numComponents; b++) {
/* 2005 */           arrayOfShort[b] = (short)(paramArrayOfint[paramInt + b] & 0xFFFF);
/*      */         }
/* 2007 */         return arrayOfShort;
/*      */     } 
/*      */ 
/*      */     
/* 2011 */     throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
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
/*      */   public int getDataElement(float[] paramArrayOffloat, int paramInt) {
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/*      */     int[] arrayOfInt;
/* 2043 */     if (this.numComponents > 1) {
/* 2044 */       throw new IllegalArgumentException("More than one component per pixel");
/*      */     }
/*      */     
/* 2047 */     if (this.signed) {
/* 2048 */       throw new IllegalArgumentException("Component value is signed");
/*      */     }
/*      */     
/* 2051 */     if (this.needScaleInit) {
/* 2052 */       initScale();
/*      */     }
/* 2054 */     Object object = getDataElements(paramArrayOffloat, paramInt, (Object)null);
/* 2055 */     switch (this.transferType) {
/*      */       
/*      */       case 0:
/* 2058 */         arrayOfByte = (byte[])object;
/* 2059 */         return arrayOfByte[0] & 0xFF;
/*      */ 
/*      */       
/*      */       case 1:
/* 2063 */         arrayOfShort = (short[])object;
/* 2064 */         return arrayOfShort[0] & 0xFFFF;
/*      */ 
/*      */       
/*      */       case 3:
/* 2068 */         arrayOfInt = (int[])object;
/* 2069 */         return arrayOfInt[0];
/*      */     } 
/*      */     
/* 2072 */     throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
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
/*      */   public Object getDataElements(float[] paramArrayOffloat, int paramInt, Object paramObject) {
/*      */     float[] arrayOfFloat1;
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort1;
/*      */     int[] arrayOfInt;
/*      */     short[] arrayOfShort2;
/*      */     float[] arrayOfFloat2;
/*      */     double[] arrayOfDouble;
/* 2113 */     boolean bool = (this.supportsAlpha && this.isAlphaPremultiplied) ? true : false;
/*      */     
/* 2115 */     if (this.needScaleInit) {
/* 2116 */       initScale();
/*      */     }
/* 2118 */     if (this.nonStdScale) {
/* 2119 */       arrayOfFloat1 = new float[this.numComponents]; byte b; int i;
/* 2120 */       for (b = 0, i = paramInt; b < this.numColorComponents; 
/* 2121 */         b++, i++) {
/* 2122 */         arrayOfFloat1[b] = (paramArrayOffloat[i] - this.compOffset[b]) * this.compScale[b];
/*      */ 
/*      */ 
/*      */         
/* 2126 */         if (arrayOfFloat1[b] < 0.0F) {
/* 2127 */           arrayOfFloat1[b] = 0.0F;
/*      */         }
/* 2129 */         if (arrayOfFloat1[b] > 1.0F) {
/* 2130 */           arrayOfFloat1[b] = 1.0F;
/*      */         }
/*      */       } 
/* 2133 */       if (this.supportsAlpha) {
/* 2134 */         arrayOfFloat1[this.numColorComponents] = paramArrayOffloat[this.numColorComponents + paramInt];
/*      */       }
/*      */       
/* 2137 */       paramInt = 0;
/*      */     } else {
/* 2139 */       arrayOfFloat1 = paramArrayOffloat;
/*      */     } 
/* 2141 */     switch (this.transferType) {
/*      */       
/*      */       case 0:
/* 2144 */         if (paramObject == null) {
/* 2145 */           arrayOfByte = new byte[this.numComponents];
/*      */         } else {
/* 2147 */           arrayOfByte = (byte[])paramObject;
/*      */         } 
/* 2149 */         if (bool) {
/* 2150 */           float f = arrayOfFloat1[this.numColorComponents + paramInt]; byte b;
/*      */           int i;
/* 2152 */           for (b = 0, i = paramInt; b < this.numColorComponents; 
/* 2153 */             b++, i++) {
/* 2154 */             arrayOfByte[b] = (byte)(int)(arrayOfFloat1[i] * f * ((1 << this.nBits[b]) - 1) + 0.5F);
/*      */           }
/*      */           
/* 2157 */           arrayOfByte[this.numColorComponents] = (byte)(int)(f * ((1 << this.nBits[this.numColorComponents]) - 1) + 0.5F);
/*      */         } else {
/*      */           byte b;
/*      */           
/*      */           int i;
/* 2162 */           for (b = 0, i = paramInt; b < this.numComponents; 
/* 2163 */             b++, i++) {
/* 2164 */             arrayOfByte[b] = (byte)(int)(arrayOfFloat1[i] * ((1 << this.nBits[b]) - 1) + 0.5F);
/*      */           }
/*      */         } 
/*      */         
/* 2168 */         return arrayOfByte;
/*      */       
/*      */       case 1:
/* 2171 */         if (paramObject == null) {
/* 2172 */           arrayOfShort1 = new short[this.numComponents];
/*      */         } else {
/* 2174 */           arrayOfShort1 = (short[])paramObject;
/*      */         } 
/* 2176 */         if (bool) {
/* 2177 */           float f = arrayOfFloat1[this.numColorComponents + paramInt]; byte b;
/*      */           int i;
/* 2179 */           for (b = 0, i = paramInt; b < this.numColorComponents; 
/* 2180 */             b++, i++) {
/* 2181 */             arrayOfShort1[b] = (short)(int)(arrayOfFloat1[i] * f * ((1 << this.nBits[b]) - 1) + 0.5F);
/*      */           }
/*      */ 
/*      */           
/* 2185 */           arrayOfShort1[this.numColorComponents] = (short)(int)(f * ((1 << this.nBits[this.numColorComponents]) - 1) + 0.5F);
/*      */         } else {
/*      */           byte b;
/*      */           
/*      */           int i;
/* 2190 */           for (b = 0, i = paramInt; b < this.numComponents; 
/* 2191 */             b++, i++) {
/* 2192 */             arrayOfShort1[b] = (short)(int)(arrayOfFloat1[i] * ((1 << this.nBits[b]) - 1) + 0.5F);
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 2197 */         return arrayOfShort1;
/*      */       
/*      */       case 3:
/* 2200 */         if (paramObject == null) {
/* 2201 */           arrayOfInt = new int[this.numComponents];
/*      */         } else {
/* 2203 */           arrayOfInt = (int[])paramObject;
/*      */         } 
/* 2205 */         if (bool) {
/* 2206 */           float f = arrayOfFloat1[this.numColorComponents + paramInt]; byte b;
/*      */           int i;
/* 2208 */           for (b = 0, i = paramInt; b < this.numColorComponents; 
/* 2209 */             b++, i++) {
/* 2210 */             arrayOfInt[b] = (int)(arrayOfFloat1[i] * f * ((1 << this.nBits[b]) - 1) + 0.5F);
/*      */           }
/*      */           
/* 2213 */           arrayOfInt[this.numColorComponents] = (int)(f * ((1 << this.nBits[this.numColorComponents]) - 1) + 0.5F);
/*      */         } else {
/*      */           byte b;
/*      */           
/*      */           int i;
/* 2218 */           for (b = 0, i = paramInt; b < this.numComponents; 
/* 2219 */             b++, i++) {
/* 2220 */             arrayOfInt[b] = (int)(arrayOfFloat1[i] * ((1 << this.nBits[b]) - 1) + 0.5F);
/*      */           }
/*      */         } 
/*      */         
/* 2224 */         return arrayOfInt;
/*      */       
/*      */       case 2:
/* 2227 */         if (paramObject == null) {
/* 2228 */           arrayOfShort2 = new short[this.numComponents];
/*      */         } else {
/* 2230 */           arrayOfShort2 = (short[])paramObject;
/*      */         } 
/* 2232 */         if (bool) {
/* 2233 */           float f = arrayOfFloat1[this.numColorComponents + paramInt]; byte b;
/*      */           int i;
/* 2235 */           for (b = 0, i = paramInt; b < this.numColorComponents; 
/* 2236 */             b++, i++) {
/* 2237 */             arrayOfShort2[b] = (short)(int)(arrayOfFloat1[i] * f * 32767.0F + 0.5F);
/*      */           }
/*      */           
/* 2240 */           arrayOfShort2[this.numColorComponents] = (short)(int)(f * 32767.0F + 0.5F);
/*      */         } else {
/* 2242 */           byte b; int i; for (b = 0, i = paramInt; b < this.numComponents; 
/* 2243 */             b++, i++) {
/* 2244 */             arrayOfShort2[b] = (short)(int)(arrayOfFloat1[i] * 32767.0F + 0.5F);
/*      */           }
/*      */         } 
/*      */         
/* 2248 */         return arrayOfShort2;
/*      */       
/*      */       case 4:
/* 2251 */         if (paramObject == null) {
/* 2252 */           arrayOfFloat2 = new float[this.numComponents];
/*      */         } else {
/* 2254 */           arrayOfFloat2 = (float[])paramObject;
/*      */         } 
/* 2256 */         if (bool) {
/* 2257 */           float f = paramArrayOffloat[this.numColorComponents + paramInt]; byte b; int i;
/* 2258 */           for (b = 0, i = paramInt; b < this.numColorComponents; 
/* 2259 */             b++, i++) {
/* 2260 */             arrayOfFloat2[b] = paramArrayOffloat[i] * f;
/*      */           }
/* 2262 */           arrayOfFloat2[this.numColorComponents] = f;
/*      */         } else {
/* 2264 */           byte b; int i; for (b = 0, i = paramInt; b < this.numComponents; 
/* 2265 */             b++, i++) {
/* 2266 */             arrayOfFloat2[b] = paramArrayOffloat[i];
/*      */           }
/*      */         } 
/* 2269 */         return arrayOfFloat2;
/*      */       
/*      */       case 5:
/* 2272 */         if (paramObject == null) {
/* 2273 */           arrayOfDouble = new double[this.numComponents];
/*      */         } else {
/* 2275 */           arrayOfDouble = (double[])paramObject;
/*      */         } 
/* 2277 */         if (bool) {
/* 2278 */           double d = paramArrayOffloat[this.numColorComponents + paramInt]; byte b;
/*      */           int i;
/* 2280 */           for (b = 0, i = paramInt; b < this.numColorComponents; 
/* 2281 */             b++, i++) {
/* 2282 */             arrayOfDouble[b] = paramArrayOffloat[i] * d;
/*      */           }
/* 2284 */           arrayOfDouble[this.numColorComponents] = d;
/*      */         } else {
/* 2286 */           byte b; int i; for (b = 0, i = paramInt; b < this.numComponents; 
/* 2287 */             b++, i++) {
/* 2288 */             arrayOfDouble[b] = paramArrayOffloat[i];
/*      */           }
/*      */         } 
/* 2291 */         return arrayOfDouble;
/*      */     } 
/* 2293 */     throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
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
/*      */   public float[] getNormalizedComponents(Object paramObject, float[] paramArrayOffloat, int paramInt) {
/*      */     byte[] arrayOfByte;
/*      */     byte b;
/*      */     short[] arrayOfShort1;
/*      */     int i;
/*      */     int[] arrayOfInt;
/*      */     int j;
/*      */     short[] arrayOfShort2;
/*      */     int k;
/*      */     float[] arrayOfFloat;
/*      */     int m;
/*      */     double[] arrayOfDouble;
/*      */     int n;
/*      */     int i1;
/* 2346 */     if (paramArrayOffloat == null) {
/* 2347 */       paramArrayOffloat = new float[this.numComponents + paramInt];
/*      */     }
/* 2349 */     switch (this.transferType) {
/*      */       case 0:
/* 2351 */         arrayOfByte = (byte[])paramObject;
/* 2352 */         for (b = 0, i = paramInt; b < this.numComponents; b++, i++) {
/* 2353 */           paramArrayOffloat[i] = (arrayOfByte[b] & 0xFF) / ((1 << this.nBits[b]) - 1);
/*      */         }
/*      */         break;
/*      */       
/*      */       case 1:
/* 2358 */         arrayOfShort1 = (short[])paramObject;
/* 2359 */         for (i = 0, j = paramInt; i < this.numComponents; i++, j++) {
/* 2360 */           paramArrayOffloat[j] = (arrayOfShort1[i] & 0xFFFF) / ((1 << this.nBits[i]) - 1);
/*      */         }
/*      */         break;
/*      */       
/*      */       case 3:
/* 2365 */         arrayOfInt = (int[])paramObject;
/* 2366 */         for (j = 0, k = paramInt; j < this.numComponents; j++, k++) {
/* 2367 */           paramArrayOffloat[k] = arrayOfInt[j] / ((1 << this.nBits[j]) - 1);
/*      */         }
/*      */         break;
/*      */       
/*      */       case 2:
/* 2372 */         arrayOfShort2 = (short[])paramObject;
/* 2373 */         for (k = 0, m = paramInt; k < this.numComponents; k++, m++) {
/* 2374 */           paramArrayOffloat[m] = arrayOfShort2[k] / 32767.0F;
/*      */         }
/*      */         break;
/*      */       case 4:
/* 2378 */         arrayOfFloat = (float[])paramObject;
/* 2379 */         for (m = 0, n = paramInt; m < this.numComponents; m++, n++) {
/* 2380 */           paramArrayOffloat[n] = arrayOfFloat[m];
/*      */         }
/*      */         break;
/*      */       case 5:
/* 2384 */         arrayOfDouble = (double[])paramObject;
/* 2385 */         for (n = 0, i1 = paramInt; n < this.numComponents; n++, i1++) {
/* 2386 */           paramArrayOffloat[i1] = (float)arrayOfDouble[n];
/*      */         }
/*      */         break;
/*      */       default:
/* 2390 */         throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2395 */     if (this.supportsAlpha && this.isAlphaPremultiplied) {
/* 2396 */       float f = paramArrayOffloat[this.numColorComponents + paramInt];
/* 2397 */       if (f != 0.0F) {
/* 2398 */         float f1 = 1.0F / f;
/* 2399 */         for (int i2 = paramInt; i2 < this.numColorComponents + paramInt; 
/* 2400 */           i2++) {
/* 2401 */           paramArrayOffloat[i2] = paramArrayOffloat[i2] * f1;
/*      */         }
/*      */       } 
/*      */     } 
/* 2405 */     if (this.min != null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2419 */       for (byte b1 = 0; b1 < this.numColorComponents; b1++) {
/* 2420 */         paramArrayOffloat[b1 + paramInt] = this.min[b1] + this.diffMinMax[b1] * paramArrayOffloat[b1 + paramInt];
/*      */       }
/*      */     }
/*      */     
/* 2424 */     return paramArrayOffloat;
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
/*      */   public ColorModel coerceData(WritableRaster paramWritableRaster, boolean paramBoolean) {
/* 2452 */     if (!this.supportsAlpha || this.isAlphaPremultiplied == paramBoolean)
/*      */     {
/*      */ 
/*      */       
/* 2456 */       return this;
/*      */     }
/*      */     
/* 2459 */     int i = paramWritableRaster.getWidth();
/* 2460 */     int j = paramWritableRaster.getHeight();
/* 2461 */     int k = paramWritableRaster.getNumBands() - 1;
/*      */     
/* 2463 */     int m = paramWritableRaster.getMinX();
/* 2464 */     int n = paramWritableRaster.getMinY();
/*      */     
/* 2466 */     if (paramBoolean) {
/* 2467 */       byte[] arrayOfByte1; byte[] arrayOfByte2; float f; byte b1; byte b2; switch (this.transferType) {
/*      */         case 0:
/* 2469 */           arrayOfByte1 = null;
/* 2470 */           arrayOfByte2 = null;
/* 2471 */           f = 1.0F / ((1 << this.nBits[k]) - 1);
/* 2472 */           for (b2 = 0; b2 < j; b2++, n++) {
/* 2473 */             int i1 = m;
/* 2474 */             for (byte b = 0; b < i; b++, i1++) {
/* 2475 */               arrayOfByte1 = (byte[])paramWritableRaster.getDataElements(i1, n, arrayOfByte1);
/*      */               
/* 2477 */               float f1 = (arrayOfByte1[k] & 0xFF) * f;
/* 2478 */               if (f1 != 0.0F) {
/* 2479 */                 for (byte b3 = 0; b3 < k; b3++) {
/* 2480 */                   arrayOfByte1[b3] = (byte)(int)((arrayOfByte1[b3] & 0xFF) * f1 + 0.5F);
/*      */                 }
/*      */                 
/* 2483 */                 paramWritableRaster.setDataElements(i1, n, arrayOfByte1);
/*      */               } else {
/* 2485 */                 if (arrayOfByte2 == null) {
/* 2486 */                   arrayOfByte2 = new byte[this.numComponents];
/* 2487 */                   Arrays.fill(arrayOfByte2, (byte)0);
/*      */                 } 
/* 2489 */                 paramWritableRaster.setDataElements(i1, n, arrayOfByte2);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 1:
/* 2496 */           arrayOfByte1 = null;
/* 2497 */           arrayOfByte2 = null;
/* 2498 */           f = 1.0F / ((1 << this.nBits[k]) - 1);
/* 2499 */           for (b2 = 0; b2 < j; b2++, n++) {
/* 2500 */             int i1 = m;
/* 2501 */             for (byte b = 0; b < i; b++, i1++) {
/* 2502 */               short[] arrayOfShort = (short[])paramWritableRaster.getDataElements(i1, n, arrayOfByte1);
/*      */               
/* 2504 */               float f1 = (arrayOfShort[k] & 0xFFFF) * f;
/* 2505 */               if (f1 != 0.0F) {
/* 2506 */                 for (byte b3 = 0; b3 < k; b3++) {
/* 2507 */                   arrayOfShort[b3] = (short)(int)((arrayOfShort[b3] & 0xFFFF) * f1 + 0.5F);
/*      */                 }
/*      */ 
/*      */                 
/* 2511 */                 paramWritableRaster.setDataElements(i1, n, arrayOfShort);
/*      */               } else {
/* 2513 */                 short[] arrayOfShort1; if (arrayOfByte2 == null) {
/* 2514 */                   arrayOfShort1 = new short[this.numComponents];
/* 2515 */                   Arrays.fill(arrayOfShort1, (short)0);
/*      */                 } 
/* 2517 */                 paramWritableRaster.setDataElements(i1, n, arrayOfShort1);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 3:
/* 2524 */           arrayOfByte1 = null;
/* 2525 */           arrayOfByte2 = null;
/* 2526 */           f = 1.0F / ((1 << this.nBits[k]) - 1);
/* 2527 */           for (b2 = 0; b2 < j; b2++, n++) {
/* 2528 */             int i1 = m;
/* 2529 */             for (byte b = 0; b < i; b++, i1++) {
/* 2530 */               int[] arrayOfInt = (int[])paramWritableRaster.getDataElements(i1, n, arrayOfByte1);
/*      */               
/* 2532 */               float f1 = arrayOfInt[k] * f;
/* 2533 */               if (f1 != 0.0F) {
/* 2534 */                 for (byte b3 = 0; b3 < k; b3++) {
/* 2535 */                   arrayOfInt[b3] = (int)(arrayOfInt[b3] * f1 + 0.5F);
/*      */                 }
/*      */                 
/* 2538 */                 paramWritableRaster.setDataElements(i1, n, arrayOfInt);
/*      */               } else {
/* 2540 */                 int[] arrayOfInt1; if (arrayOfByte2 == null) {
/* 2541 */                   arrayOfInt1 = new int[this.numComponents];
/* 2542 */                   Arrays.fill(arrayOfInt1, 0);
/*      */                 } 
/* 2544 */                 paramWritableRaster.setDataElements(i1, n, arrayOfInt1);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 2:
/* 2551 */           arrayOfByte1 = null;
/* 2552 */           arrayOfByte2 = null;
/* 2553 */           f = 3.051851E-5F;
/* 2554 */           for (b2 = 0; b2 < j; b2++, n++) {
/* 2555 */             int i1 = m;
/* 2556 */             for (byte b = 0; b < i; b++, i1++) {
/* 2557 */               short[] arrayOfShort = (short[])paramWritableRaster.getDataElements(i1, n, arrayOfByte1);
/*      */               
/* 2559 */               float f1 = arrayOfShort[k] * f;
/* 2560 */               if (f1 != 0.0F) {
/* 2561 */                 for (byte b3 = 0; b3 < k; b3++) {
/* 2562 */                   arrayOfShort[b3] = (short)(int)(arrayOfShort[b3] * f1 + 0.5F);
/*      */                 }
/*      */                 
/* 2565 */                 paramWritableRaster.setDataElements(i1, n, arrayOfShort);
/*      */               } else {
/* 2567 */                 short[] arrayOfShort1; if (arrayOfByte2 == null) {
/* 2568 */                   arrayOfShort1 = new short[this.numComponents];
/* 2569 */                   Arrays.fill(arrayOfShort1, (short)0);
/*      */                 } 
/* 2571 */                 paramWritableRaster.setDataElements(i1, n, arrayOfShort1);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 4:
/* 2578 */           arrayOfByte1 = null;
/* 2579 */           arrayOfByte2 = null;
/* 2580 */           for (b1 = 0; b1 < j; b1++, n++) {
/* 2581 */             int i1 = m;
/* 2582 */             for (b2 = 0; b2 < i; b2++, i1++) {
/* 2583 */               float[] arrayOfFloat = (float[])paramWritableRaster.getDataElements(i1, n, arrayOfByte1);
/*      */               
/* 2585 */               float f1 = arrayOfFloat[k];
/* 2586 */               if (f1 != 0.0F) {
/* 2587 */                 for (byte b = 0; b < k; b++) {
/* 2588 */                   arrayOfFloat[b] = arrayOfFloat[b] * f1;
/*      */                 }
/* 2590 */                 paramWritableRaster.setDataElements(i1, n, arrayOfFloat);
/*      */               } else {
/* 2592 */                 float[] arrayOfFloat1; if (arrayOfByte2 == null) {
/* 2593 */                   arrayOfFloat1 = new float[this.numComponents];
/* 2594 */                   Arrays.fill(arrayOfFloat1, 0.0F);
/*      */                 } 
/* 2596 */                 paramWritableRaster.setDataElements(i1, n, arrayOfFloat1);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 5:
/* 2603 */           arrayOfByte1 = null;
/* 2604 */           arrayOfByte2 = null;
/* 2605 */           for (b1 = 0; b1 < j; b1++, n++) {
/* 2606 */             int i1 = m;
/* 2607 */             for (b2 = 0; b2 < i; b2++, i1++) {
/* 2608 */               double[] arrayOfDouble = (double[])paramWritableRaster.getDataElements(i1, n, arrayOfByte1);
/*      */               
/* 2610 */               double d = arrayOfDouble[k];
/* 2611 */               if (d != 0.0D) {
/* 2612 */                 for (byte b = 0; b < k; b++) {
/* 2613 */                   arrayOfDouble[b] = arrayOfDouble[b] * d;
/*      */                 }
/* 2615 */                 paramWritableRaster.setDataElements(i1, n, arrayOfDouble);
/*      */               } else {
/* 2617 */                 double[] arrayOfDouble1; if (arrayOfByte2 == null) {
/* 2618 */                   arrayOfDouble1 = new double[this.numComponents];
/* 2619 */                   Arrays.fill(arrayOfDouble1, 0.0D);
/*      */                 } 
/* 2621 */                 paramWritableRaster.setDataElements(i1, n, arrayOfDouble1);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         
/*      */         default:
/* 2628 */           throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */       } 
/*      */     } else {
/*      */       byte[] arrayOfByte; float f;
/*      */       byte b1;
/*      */       byte b2;
/* 2634 */       switch (this.transferType) {
/*      */         case 0:
/* 2636 */           arrayOfByte = null;
/* 2637 */           f = 1.0F / ((1 << this.nBits[k]) - 1);
/* 2638 */           for (b2 = 0; b2 < j; b2++, n++) {
/* 2639 */             int i1 = m;
/* 2640 */             for (byte b = 0; b < i; b++, i1++) {
/* 2641 */               arrayOfByte = (byte[])paramWritableRaster.getDataElements(i1, n, arrayOfByte);
/*      */               
/* 2643 */               float f1 = (arrayOfByte[k] & 0xFF) * f;
/* 2644 */               if (f1 != 0.0F) {
/* 2645 */                 float f2 = 1.0F / f1;
/* 2646 */                 for (byte b3 = 0; b3 < k; b3++) {
/* 2647 */                   arrayOfByte[b3] = (byte)(int)((arrayOfByte[b3] & 0xFF) * f2 + 0.5F);
/*      */                 }
/*      */                 
/* 2650 */                 paramWritableRaster.setDataElements(i1, n, arrayOfByte);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 1:
/* 2657 */           arrayOfByte = null;
/* 2658 */           f = 1.0F / ((1 << this.nBits[k]) - 1);
/* 2659 */           for (b2 = 0; b2 < j; b2++, n++) {
/* 2660 */             int i1 = m;
/* 2661 */             for (byte b = 0; b < i; b++, i1++) {
/* 2662 */               short[] arrayOfShort = (short[])paramWritableRaster.getDataElements(i1, n, arrayOfByte);
/*      */               
/* 2664 */               float f1 = (arrayOfShort[k] & 0xFFFF) * f;
/* 2665 */               if (f1 != 0.0F) {
/* 2666 */                 float f2 = 1.0F / f1;
/* 2667 */                 for (byte b3 = 0; b3 < k; b3++) {
/* 2668 */                   arrayOfShort[b3] = (short)(int)((arrayOfShort[b3] & 0xFFFF) * f2 + 0.5F);
/*      */                 }
/*      */                 
/* 2671 */                 paramWritableRaster.setDataElements(i1, n, arrayOfShort);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 3:
/* 2678 */           arrayOfByte = null;
/* 2679 */           f = 1.0F / ((1 << this.nBits[k]) - 1);
/* 2680 */           for (b2 = 0; b2 < j; b2++, n++) {
/* 2681 */             int i1 = m;
/* 2682 */             for (byte b = 0; b < i; b++, i1++) {
/* 2683 */               int[] arrayOfInt = (int[])paramWritableRaster.getDataElements(i1, n, arrayOfByte);
/*      */               
/* 2685 */               float f1 = arrayOfInt[k] * f;
/* 2686 */               if (f1 != 0.0F) {
/* 2687 */                 float f2 = 1.0F / f1;
/* 2688 */                 for (byte b3 = 0; b3 < k; b3++) {
/* 2689 */                   arrayOfInt[b3] = (int)(arrayOfInt[b3] * f2 + 0.5F);
/*      */                 }
/*      */                 
/* 2692 */                 paramWritableRaster.setDataElements(i1, n, arrayOfInt);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 2:
/* 2699 */           arrayOfByte = null;
/* 2700 */           f = 3.051851E-5F;
/* 2701 */           for (b2 = 0; b2 < j; b2++, n++) {
/* 2702 */             int i1 = m;
/* 2703 */             for (byte b = 0; b < i; b++, i1++) {
/* 2704 */               short[] arrayOfShort = (short[])paramWritableRaster.getDataElements(i1, n, arrayOfByte);
/*      */               
/* 2706 */               float f1 = arrayOfShort[k] * f;
/* 2707 */               if (f1 != 0.0F) {
/* 2708 */                 float f2 = 1.0F / f1;
/* 2709 */                 for (byte b3 = 0; b3 < k; b3++) {
/* 2710 */                   arrayOfShort[b3] = (short)(int)(arrayOfShort[b3] * f2 + 0.5F);
/*      */                 }
/*      */                 
/* 2713 */                 paramWritableRaster.setDataElements(i1, n, arrayOfShort);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 4:
/* 2720 */           arrayOfByte = null;
/* 2721 */           for (b1 = 0; b1 < j; b1++, n++) {
/* 2722 */             int i1 = m;
/* 2723 */             for (b2 = 0; b2 < i; b2++, i1++) {
/* 2724 */               float[] arrayOfFloat = (float[])paramWritableRaster.getDataElements(i1, n, arrayOfByte);
/*      */               
/* 2726 */               float f1 = arrayOfFloat[k];
/* 2727 */               if (f1 != 0.0F) {
/* 2728 */                 float f2 = 1.0F / f1;
/* 2729 */                 for (byte b = 0; b < k; b++) {
/* 2730 */                   arrayOfFloat[b] = arrayOfFloat[b] * f2;
/*      */                 }
/* 2732 */                 paramWritableRaster.setDataElements(i1, n, arrayOfFloat);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 5:
/* 2739 */           arrayOfByte = null;
/* 2740 */           for (b1 = 0; b1 < j; b1++, n++) {
/* 2741 */             int i1 = m;
/* 2742 */             for (b2 = 0; b2 < i; b2++, i1++) {
/* 2743 */               double[] arrayOfDouble = (double[])paramWritableRaster.getDataElements(i1, n, arrayOfByte);
/*      */               
/* 2745 */               double d = arrayOfDouble[k];
/* 2746 */               if (d != 0.0D) {
/* 2747 */                 double d1 = 1.0D / d;
/* 2748 */                 for (byte b = 0; b < k; b++) {
/* 2749 */                   arrayOfDouble[b] = arrayOfDouble[b] * d1;
/*      */                 }
/* 2751 */                 paramWritableRaster.setDataElements(i1, n, arrayOfDouble);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         
/*      */         default:
/* 2758 */           throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */       } 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/* 2764 */     if (!this.signed) {
/* 2765 */       return new ComponentColorModel(this.colorSpace, this.nBits, this.supportsAlpha, paramBoolean, this.transparency, this.transferType);
/*      */     }
/*      */ 
/*      */     
/* 2769 */     return new ComponentColorModel(this.colorSpace, this.supportsAlpha, paramBoolean, this.transparency, this.transferType);
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
/*      */   public boolean isCompatibleRaster(Raster paramRaster) {
/* 2787 */     SampleModel sampleModel = paramRaster.getSampleModel();
/*      */     
/* 2789 */     if (sampleModel instanceof ComponentSampleModel) {
/* 2790 */       if (sampleModel.getNumBands() != getNumComponents()) {
/* 2791 */         return false;
/*      */       }
/* 2793 */       for (byte b = 0; b < this.nBits.length; b++) {
/* 2794 */         if (sampleModel.getSampleSize(b) < this.nBits[b]) {
/* 2795 */           return false;
/*      */         }
/*      */       } 
/* 2798 */       return (paramRaster.getTransferType() == this.transferType);
/*      */     } 
/*      */     
/* 2801 */     return false;
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
/*      */   public WritableRaster createCompatibleWritableRaster(int paramInt1, int paramInt2) {
/* 2819 */     int i = paramInt1 * paramInt2 * this.numComponents;
/* 2820 */     WritableRaster writableRaster = null;
/*      */     
/* 2822 */     switch (this.transferType)
/*      */     { case 0:
/*      */       case 1:
/* 2825 */         writableRaster = Raster.createInterleavedRaster(this.transferType, paramInt1, paramInt2, this.numComponents, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2835 */         return writableRaster; }  SampleModel sampleModel = createCompatibleSampleModel(paramInt1, paramInt2); DataBuffer dataBuffer = sampleModel.createDataBuffer(); writableRaster = Raster.createWritableRaster(sampleModel, dataBuffer, null); return writableRaster;
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
/*      */   public SampleModel createCompatibleSampleModel(int paramInt1, int paramInt2) {
/* 2851 */     int[] arrayOfInt = new int[this.numComponents];
/* 2852 */     for (byte b = 0; b < this.numComponents; b++) {
/* 2853 */       arrayOfInt[b] = b;
/*      */     }
/* 2855 */     switch (this.transferType) {
/*      */       case 0:
/*      */       case 1:
/* 2858 */         return new PixelInterleavedSampleModel(this.transferType, paramInt1, paramInt2, this.numComponents, paramInt1 * this.numComponents, arrayOfInt);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2863 */     return new ComponentSampleModel(this.transferType, paramInt1, paramInt2, this.numComponents, paramInt1 * this.numComponents, arrayOfInt);
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
/*      */   public boolean isCompatibleSampleModel(SampleModel paramSampleModel) {
/* 2883 */     if (!(paramSampleModel instanceof ComponentSampleModel)) {
/* 2884 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2888 */     if (this.numComponents != paramSampleModel.getNumBands()) {
/* 2889 */       return false;
/*      */     }
/*      */     
/* 2892 */     if (paramSampleModel.getTransferType() != this.transferType) {
/* 2893 */       return false;
/*      */     }
/*      */     
/* 2896 */     return true;
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
/*      */   public WritableRaster getAlphaRaster(WritableRaster paramWritableRaster) {
/* 2916 */     if (!hasAlpha()) {
/* 2917 */       return null;
/*      */     }
/*      */     
/* 2920 */     int i = paramWritableRaster.getMinX();
/* 2921 */     int j = paramWritableRaster.getMinY();
/* 2922 */     int[] arrayOfInt = new int[1];
/* 2923 */     arrayOfInt[0] = paramWritableRaster.getNumBands() - 1;
/* 2924 */     return paramWritableRaster.createWritableChild(i, j, paramWritableRaster.getWidth(), paramWritableRaster
/* 2925 */         .getHeight(), i, j, arrayOfInt);
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
/*      */   public boolean equals(Object paramObject) {
/* 2937 */     if (!super.equals(paramObject)) {
/* 2938 */       return false;
/*      */     }
/*      */     
/* 2941 */     if (paramObject.getClass() != getClass()) {
/* 2942 */       return false;
/*      */     }
/*      */     
/* 2945 */     return true;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/ComponentColorModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */