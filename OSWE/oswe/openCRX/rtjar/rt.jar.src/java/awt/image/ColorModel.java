/*      */ package java.awt.image;
/*      */ 
/*      */ import java.awt.Transparency;
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.awt.color.ICC_ColorSpace;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Collections;
/*      */ import java.util.Map;
/*      */ import java.util.WeakHashMap;
/*      */ import sun.java2d.cmm.CMSManager;
/*      */ import sun.java2d.cmm.ColorTransform;
/*      */ import sun.java2d.cmm.PCMM;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ColorModel
/*      */   implements Transparency
/*      */ {
/*      */   private long pData;
/*      */   protected int pixel_bits;
/*      */   int[] nBits;
/*  164 */   int transparency = 3;
/*      */   boolean supportsAlpha = true;
/*      */   boolean isAlphaPremultiplied = false;
/*  167 */   int numComponents = -1;
/*  168 */   int numColorComponents = -1;
/*  169 */   ColorSpace colorSpace = ColorSpace.getInstance(1000);
/*  170 */   int colorSpaceType = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int maxBits;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean is_sRGB = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int transferType;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean loaded = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ColorModel RGBdefault;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadLibraries() {
/*  205 */     if (!loaded) {
/*  206 */       AccessController.doPrivileged(new PrivilegedAction<Void>()
/*      */           {
/*      */             public Void run() {
/*  209 */               System.loadLibrary("awt");
/*  210 */               return null;
/*      */             }
/*      */           });
/*  213 */       loaded = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static {
/*  219 */     loadLibraries();
/*  220 */     initIDs();
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
/*      */   public static ColorModel getRGBdefault() {
/*  241 */     if (RGBdefault == null) {
/*  242 */       RGBdefault = new DirectColorModel(32, 16711680, 65280, 255, -16777216);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  249 */     return RGBdefault;
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
/*      */   public ColorModel(int paramInt) {
/*  273 */     this.pixel_bits = paramInt;
/*  274 */     if (paramInt < 1) {
/*  275 */       throw new IllegalArgumentException("Number of bits must be > 0");
/*      */     }
/*  277 */     this.numComponents = 4;
/*  278 */     this.numColorComponents = 3;
/*  279 */     this.maxBits = paramInt;
/*      */     
/*  281 */     this.transferType = getDefaultTransferType(paramInt);
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
/*      */ 
/*      */   
/*      */   protected ColorModel(int paramInt1, int[] paramArrayOfint, ColorSpace paramColorSpace, boolean paramBoolean1, boolean paramBoolean2, int paramInt2, int paramInt3) {
/*  335 */     this.colorSpace = paramColorSpace;
/*  336 */     this.colorSpaceType = paramColorSpace.getType();
/*  337 */     this.numColorComponents = paramColorSpace.getNumComponents();
/*  338 */     this.numComponents = this.numColorComponents + (paramBoolean1 ? 1 : 0);
/*  339 */     this.supportsAlpha = paramBoolean1;
/*  340 */     if (paramArrayOfint.length < this.numComponents) {
/*  341 */       throw new IllegalArgumentException("Number of color/alpha components should be " + this.numComponents + " but length of bits array is " + paramArrayOfint.length);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  349 */     if (paramInt2 < 1 || paramInt2 > 3)
/*      */     {
/*      */       
/*  352 */       throw new IllegalArgumentException("Unknown transparency: " + paramInt2);
/*      */     }
/*      */ 
/*      */     
/*  356 */     if (!this.supportsAlpha) {
/*  357 */       this.isAlphaPremultiplied = false;
/*  358 */       this.transparency = 1;
/*      */     } else {
/*      */       
/*  361 */       this.isAlphaPremultiplied = paramBoolean2;
/*  362 */       this.transparency = paramInt2;
/*      */     } 
/*      */     
/*  365 */     this.nBits = (int[])paramArrayOfint.clone();
/*  366 */     this.pixel_bits = paramInt1;
/*  367 */     if (paramInt1 <= 0) {
/*  368 */       throw new IllegalArgumentException("Number of pixel bits must be > 0");
/*      */     }
/*      */ 
/*      */     
/*  372 */     this.maxBits = 0;
/*  373 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/*      */       
/*  375 */       if (paramArrayOfint[b] < 0) {
/*  376 */         throw new IllegalArgumentException("Number of bits must be >= 0");
/*      */       }
/*      */       
/*  379 */       if (this.maxBits < paramArrayOfint[b]) {
/*  380 */         this.maxBits = paramArrayOfint[b];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  385 */     if (this.maxBits == 0) {
/*  386 */       throw new IllegalArgumentException("There must be at least one component with > 0 pixel bits.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  392 */     if (paramColorSpace != ColorSpace.getInstance(1000)) {
/*  393 */       this.is_sRGB = false;
/*      */     }
/*      */ 
/*      */     
/*  397 */     this.transferType = paramInt3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean hasAlpha() {
/*  407 */     return this.supportsAlpha;
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
/*      */   public final boolean isAlphaPremultiplied() {
/*  423 */     return this.isAlphaPremultiplied;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getTransferType() {
/*  434 */     return this.transferType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPixelSize() {
/*  443 */     return this.pixel_bits;
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
/*      */   public int getComponentSize(int paramInt) {
/*  466 */     if (this.nBits == null) {
/*  467 */       throw new NullPointerException("Number of bits array is null.");
/*      */     }
/*      */     
/*  470 */     return this.nBits[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getComponentSize() {
/*  481 */     if (this.nBits != null) {
/*  482 */       return (int[])this.nBits.clone();
/*      */     }
/*      */     
/*  485 */     return null;
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
/*      */   public int getTransparency() {
/*  497 */     return this.transparency;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumComponents() {
/*  507 */     return this.numComponents;
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
/*      */   public int getNumColorComponents() {
/*  520 */     return this.numColorComponents;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  595 */     return getAlpha(paramInt) << 24 | 
/*  596 */       getRed(paramInt) << 16 | 
/*  597 */       getGreen(paramInt) << 8 | 
/*  598 */       getBlue(paramInt) << 0;
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
/*      */   public int getRed(Object paramObject) {
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/*  638 */     int arrayOfInt[], i = 0, j = 0;
/*  639 */     switch (this.transferType) {
/*      */       case 0:
/*  641 */         arrayOfByte = (byte[])paramObject;
/*  642 */         i = arrayOfByte[0] & 0xFF;
/*  643 */         j = arrayOfByte.length;
/*      */         break;
/*      */       case 1:
/*  646 */         arrayOfShort = (short[])paramObject;
/*  647 */         i = arrayOfShort[0] & 0xFFFF;
/*  648 */         j = arrayOfShort.length;
/*      */         break;
/*      */       case 3:
/*  651 */         arrayOfInt = (int[])paramObject;
/*  652 */         i = arrayOfInt[0];
/*  653 */         j = arrayOfInt.length;
/*      */         break;
/*      */       default:
/*  656 */         throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */     } 
/*      */     
/*  659 */     if (j == 1) {
/*  660 */       return getRed(i);
/*      */     }
/*      */     
/*  663 */     throw new UnsupportedOperationException("This method is not supported by this color model");
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
/*      */   public int getGreen(Object paramObject) {
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/*  705 */     int arrayOfInt[], i = 0, j = 0;
/*  706 */     switch (this.transferType) {
/*      */       case 0:
/*  708 */         arrayOfByte = (byte[])paramObject;
/*  709 */         i = arrayOfByte[0] & 0xFF;
/*  710 */         j = arrayOfByte.length;
/*      */         break;
/*      */       case 1:
/*  713 */         arrayOfShort = (short[])paramObject;
/*  714 */         i = arrayOfShort[0] & 0xFFFF;
/*  715 */         j = arrayOfShort.length;
/*      */         break;
/*      */       case 3:
/*  718 */         arrayOfInt = (int[])paramObject;
/*  719 */         i = arrayOfInt[0];
/*  720 */         j = arrayOfInt.length;
/*      */         break;
/*      */       default:
/*  723 */         throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */     } 
/*      */     
/*  726 */     if (j == 1) {
/*  727 */       return getGreen(i);
/*      */     }
/*      */     
/*  730 */     throw new UnsupportedOperationException("This method is not supported by this color model");
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
/*      */   public int getBlue(Object paramObject) {
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/*  772 */     int arrayOfInt[], i = 0, j = 0;
/*  773 */     switch (this.transferType) {
/*      */       case 0:
/*  775 */         arrayOfByte = (byte[])paramObject;
/*  776 */         i = arrayOfByte[0] & 0xFF;
/*  777 */         j = arrayOfByte.length;
/*      */         break;
/*      */       case 1:
/*  780 */         arrayOfShort = (short[])paramObject;
/*  781 */         i = arrayOfShort[0] & 0xFFFF;
/*  782 */         j = arrayOfShort.length;
/*      */         break;
/*      */       case 3:
/*  785 */         arrayOfInt = (int[])paramObject;
/*  786 */         i = arrayOfInt[0];
/*  787 */         j = arrayOfInt.length;
/*      */         break;
/*      */       default:
/*  790 */         throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */     } 
/*      */     
/*  793 */     if (j == 1) {
/*  794 */       return getBlue(i);
/*      */     }
/*      */     
/*  797 */     throw new UnsupportedOperationException("This method is not supported by this color model");
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
/*      */   public int getAlpha(Object paramObject) {
/*      */     byte[] arrayOfByte;
/*      */     short[] arrayOfShort;
/*  835 */     int arrayOfInt[], i = 0, j = 0;
/*  836 */     switch (this.transferType) {
/*      */       case 0:
/*  838 */         arrayOfByte = (byte[])paramObject;
/*  839 */         i = arrayOfByte[0] & 0xFF;
/*  840 */         j = arrayOfByte.length;
/*      */         break;
/*      */       case 1:
/*  843 */         arrayOfShort = (short[])paramObject;
/*  844 */         i = arrayOfShort[0] & 0xFFFF;
/*  845 */         j = arrayOfShort.length;
/*      */         break;
/*      */       case 3:
/*  848 */         arrayOfInt = (int[])paramObject;
/*  849 */         i = arrayOfInt[0];
/*  850 */         j = arrayOfInt.length;
/*      */         break;
/*      */       default:
/*  853 */         throw new UnsupportedOperationException("This method has not been implemented for transferType " + this.transferType);
/*      */     } 
/*      */     
/*  856 */     if (j == 1) {
/*  857 */       return getAlpha(i);
/*      */     }
/*      */     
/*  860 */     throw new UnsupportedOperationException("This method is not supported by this color model");
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
/*      */   public int getRGB(Object paramObject) {
/*  883 */     return getAlpha(paramObject) << 24 | 
/*  884 */       getRed(paramObject) << 16 | 
/*  885 */       getGreen(paramObject) << 8 | 
/*  886 */       getBlue(paramObject) << 0;
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
/*      */   public Object getDataElements(int paramInt, Object paramObject) {
/*  927 */     throw new UnsupportedOperationException("This method is not supported by this color model.");
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
/*      */   public int[] getComponents(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  964 */     throw new UnsupportedOperationException("This method is not supported by this color model.");
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
/*      */   public int[] getComponents(Object paramObject, int[] paramArrayOfint, int paramInt) {
/* 1006 */     throw new UnsupportedOperationException("This method is not supported by this color model.");
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
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getUnnormalizedComponents(float[] paramArrayOffloat, int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 1061 */     if (this.colorSpace == null) {
/* 1062 */       throw new UnsupportedOperationException("This method is not supported by this color model.");
/*      */     }
/*      */ 
/*      */     
/* 1066 */     if (this.nBits == null) {
/* 1067 */       throw new UnsupportedOperationException("This method is not supported.  Unable to determine #bits per component.");
/*      */     }
/*      */ 
/*      */     
/* 1071 */     if (paramArrayOffloat.length - paramInt1 < this.numComponents) {
/* 1072 */       throw new IllegalArgumentException("Incorrect number of components.  Expecting " + this.numComponents);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1078 */     if (paramArrayOfint == null) {
/* 1079 */       paramArrayOfint = new int[paramInt2 + this.numComponents];
/*      */     }
/*      */     
/* 1082 */     if (this.supportsAlpha && this.isAlphaPremultiplied) {
/* 1083 */       float f = paramArrayOffloat[paramInt1 + this.numColorComponents];
/* 1084 */       for (byte b = 0; b < this.numColorComponents; b++) {
/* 1085 */         paramArrayOfint[paramInt2 + b] = (int)(paramArrayOffloat[paramInt1 + b] * ((1 << this.nBits[b]) - 1) * f + 0.5F);
/*      */       }
/*      */ 
/*      */       
/* 1089 */       paramArrayOfint[paramInt2 + this.numColorComponents] = (int)(f * ((1 << this.nBits[this.numColorComponents]) - 1) + 0.5F);
/*      */     }
/*      */     else {
/*      */       
/* 1093 */       for (byte b = 0; b < this.numComponents; b++) {
/* 1094 */         paramArrayOfint[paramInt2 + b] = (int)(paramArrayOffloat[paramInt1 + b] * ((1 << this.nBits[b]) - 1) + 0.5F);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1099 */     return paramArrayOfint;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[] getNormalizedComponents(int[] paramArrayOfint, int paramInt1, float[] paramArrayOffloat, int paramInt2) {
/* 1157 */     if (this.colorSpace == null) {
/* 1158 */       throw new UnsupportedOperationException("This method is not supported by this color model.");
/*      */     }
/*      */     
/* 1161 */     if (this.nBits == null) {
/* 1162 */       throw new UnsupportedOperationException("This method is not supported.  Unable to determine #bits per component.");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1167 */     if (paramArrayOfint.length - paramInt1 < this.numComponents) {
/* 1168 */       throw new IllegalArgumentException("Incorrect number of components.  Expecting " + this.numComponents);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1174 */     if (paramArrayOffloat == null) {
/* 1175 */       paramArrayOffloat = new float[this.numComponents + paramInt2];
/*      */     }
/*      */     
/* 1178 */     if (this.supportsAlpha && this.isAlphaPremultiplied) {
/*      */       
/* 1180 */       float f = paramArrayOfint[paramInt1 + this.numColorComponents];
/* 1181 */       f /= ((1 << this.nBits[this.numColorComponents]) - 1);
/* 1182 */       if (f != 0.0F) {
/* 1183 */         for (byte b = 0; b < this.numColorComponents; b++) {
/* 1184 */           paramArrayOffloat[paramInt2 + b] = paramArrayOfint[paramInt1 + b] / f * ((1 << this.nBits[b]) - 1);
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1189 */         for (byte b = 0; b < this.numColorComponents; b++) {
/* 1190 */           paramArrayOffloat[paramInt2 + b] = 0.0F;
/*      */         }
/*      */       } 
/* 1193 */       paramArrayOffloat[paramInt2 + this.numColorComponents] = f;
/*      */     } else {
/*      */       
/* 1196 */       for (byte b = 0; b < this.numComponents; b++) {
/* 1197 */         paramArrayOffloat[paramInt2 + b] = paramArrayOfint[paramInt1 + b] / ((1 << this.nBits[b]) - 1);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1202 */     return paramArrayOffloat;
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
/*      */   public int getDataElement(int[] paramArrayOfint, int paramInt) {
/* 1241 */     throw new UnsupportedOperationException("This method is not supported by this color model.");
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
/*      */   public Object getDataElements(int[] paramArrayOfint, int paramInt, Object paramObject) {
/* 1292 */     throw new UnsupportedOperationException("This method has not been implemented for this color model.");
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
/*      */   public int getDataElement(float[] paramArrayOffloat, int paramInt) {
/* 1329 */     int[] arrayOfInt = getUnnormalizedComponents(paramArrayOffloat, paramInt, null, 0);
/*      */     
/* 1331 */     return getDataElement(arrayOfInt, 0);
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
/*      */   public Object getDataElements(float[] paramArrayOffloat, int paramInt, Object paramObject) {
/* 1377 */     int[] arrayOfInt = getUnnormalizedComponents(paramArrayOffloat, paramInt, null, 0);
/*      */     
/* 1379 */     return getDataElements(arrayOfInt, 0, paramObject);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[] getNormalizedComponents(Object paramObject, float[] paramArrayOffloat, int paramInt) {
/* 1438 */     int[] arrayOfInt = getComponents(paramObject, (int[])null, 0);
/* 1439 */     return getNormalizedComponents(arrayOfInt, 0, paramArrayOffloat, paramInt);
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
/*      */   public boolean equals(Object paramObject) {
/* 1453 */     if (!(paramObject instanceof ColorModel)) {
/* 1454 */       return false;
/*      */     }
/* 1456 */     ColorModel colorModel = (ColorModel)paramObject;
/*      */     
/* 1458 */     if (this == colorModel) {
/* 1459 */       return true;
/*      */     }
/* 1461 */     if (this.supportsAlpha != colorModel.hasAlpha() || this.isAlphaPremultiplied != colorModel
/* 1462 */       .isAlphaPremultiplied() || this.pixel_bits != colorModel
/* 1463 */       .getPixelSize() || this.transparency != colorModel
/* 1464 */       .getTransparency() || this.numComponents != colorModel
/* 1465 */       .getNumComponents())
/*      */     {
/* 1467 */       return false;
/*      */     }
/*      */     
/* 1470 */     int[] arrayOfInt = colorModel.getComponentSize();
/*      */     
/* 1472 */     if (this.nBits != null && arrayOfInt != null) {
/* 1473 */       for (byte b = 0; b < this.numComponents; b++) {
/* 1474 */         if (this.nBits[b] != arrayOfInt[b]) {
/* 1475 */           return false;
/*      */         }
/*      */       } 
/*      */     } else {
/* 1479 */       return (this.nBits == null && arrayOfInt == null);
/*      */     } 
/*      */     
/* 1482 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1492 */     int i = 0;
/*      */     
/* 1494 */     i = (this.supportsAlpha ? 2 : 3) + (this.isAlphaPremultiplied ? 4 : 5) + this.pixel_bits * 6 + this.transparency * 7 + this.numComponents * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1500 */     if (this.nBits != null) {
/* 1501 */       for (byte b = 0; b < this.numComponents; b++) {
/* 1502 */         i += this.nBits[b] * (b + 9);
/*      */       }
/*      */     }
/*      */     
/* 1506 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final ColorSpace getColorSpace() {
/* 1516 */     return this.colorSpace;
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
/*      */   public ColorModel coerceData(WritableRaster paramWritableRaster, boolean paramBoolean) {
/* 1542 */     throw new UnsupportedOperationException("This method is not supported by this color model");
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
/*      */   public boolean isCompatibleRaster(Raster paramRaster) {
/* 1562 */     throw new UnsupportedOperationException("This method has not been implemented for this ColorModel.");
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
/*      */   public WritableRaster createCompatibleWritableRaster(int paramInt1, int paramInt2) {
/* 1584 */     throw new UnsupportedOperationException("This method is not supported by this color model");
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
/*      */   public SampleModel createCompatibleSampleModel(int paramInt1, int paramInt2) {
/* 1605 */     throw new UnsupportedOperationException("This method is not supported by this color model");
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
/*      */   public boolean isCompatibleSampleModel(SampleModel paramSampleModel) {
/* 1624 */     throw new UnsupportedOperationException("This method is not supported by this color model");
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
/*      */   public void finalize() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1663 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1673 */     return new String("ColorModel: #pixelBits = " + this.pixel_bits + " numComponents = " + this.numComponents + " color space = " + this.colorSpace + " transparency = " + this.transparency + " has alpha = " + this.supportsAlpha + " isAlphaPre = " + this.isAlphaPremultiplied);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int getDefaultTransferType(int paramInt) {
/* 1683 */     if (paramInt <= 8)
/* 1684 */       return 0; 
/* 1685 */     if (paramInt <= 16)
/* 1686 */       return 1; 
/* 1687 */     if (paramInt <= 32) {
/* 1688 */       return 3;
/*      */     }
/* 1690 */     return 32;
/*      */   }
/*      */ 
/*      */   
/* 1694 */   static byte[] l8Tos8 = null;
/* 1695 */   static byte[] s8Tol8 = null;
/* 1696 */   static byte[] l16Tos8 = null;
/* 1697 */   static short[] s8Tol16 = null;
/*      */ 
/*      */   
/* 1700 */   static Map<ICC_ColorSpace, byte[]> g8Tos8Map = null;
/* 1701 */   static Map<ICC_ColorSpace, byte[]> lg16Toog8Map = null;
/* 1702 */   static Map<ICC_ColorSpace, byte[]> g16Tos8Map = null;
/* 1703 */   static Map<ICC_ColorSpace, short[]> lg16Toog16Map = null;
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isLinearRGBspace(ColorSpace paramColorSpace) {
/* 1708 */     return (paramColorSpace == CMSManager.LINEAR_RGBspace);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isLinearGRAYspace(ColorSpace paramColorSpace) {
/* 1714 */     return (paramColorSpace == CMSManager.GRAYspace);
/*      */   }
/*      */   
/*      */   static byte[] getLinearRGB8TosRGB8LUT() {
/* 1718 */     if (l8Tos8 == null) {
/* 1719 */       l8Tos8 = new byte[256];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1726 */       for (byte b = 0; b <= 'ÿ'; b++) {
/* 1727 */         float f2, f1 = b / 255.0F;
/* 1728 */         if (f1 <= 0.0031308F) {
/* 1729 */           f2 = f1 * 12.92F;
/*      */         } else {
/* 1731 */           f2 = 1.055F * (float)Math.pow(f1, 0.4166666666666667D) - 0.055F;
/*      */         } 
/*      */         
/* 1734 */         l8Tos8[b] = (byte)Math.round(f2 * 255.0F);
/*      */       } 
/*      */     } 
/* 1737 */     return l8Tos8;
/*      */   }
/*      */   
/*      */   static byte[] getsRGB8ToLinearRGB8LUT() {
/* 1741 */     if (s8Tol8 == null) {
/* 1742 */       s8Tol8 = new byte[256];
/*      */ 
/*      */       
/* 1745 */       for (byte b = 0; b <= 'ÿ'; b++) {
/* 1746 */         float f2, f1 = b / 255.0F;
/* 1747 */         if (f1 <= 0.04045F) {
/* 1748 */           f2 = f1 / 12.92F;
/*      */         } else {
/* 1750 */           f2 = (float)Math.pow(((f1 + 0.055F) / 1.055F), 2.4D);
/*      */         } 
/* 1752 */         s8Tol8[b] = (byte)Math.round(f2 * 255.0F);
/*      */       } 
/*      */     } 
/* 1755 */     return s8Tol8;
/*      */   }
/*      */   
/*      */   static byte[] getLinearRGB16TosRGB8LUT() {
/* 1759 */     if (l16Tos8 == null) {
/* 1760 */       l16Tos8 = new byte[65536];
/*      */ 
/*      */       
/* 1763 */       for (byte b = 0; b <= '￿'; b++) {
/* 1764 */         float f2, f1 = b / 65535.0F;
/* 1765 */         if (f1 <= 0.0031308F) {
/* 1766 */           f2 = f1 * 12.92F;
/*      */         } else {
/* 1768 */           f2 = 1.055F * (float)Math.pow(f1, 0.4166666666666667D) - 0.055F;
/*      */         } 
/*      */         
/* 1771 */         l16Tos8[b] = (byte)Math.round(f2 * 255.0F);
/*      */       } 
/*      */     } 
/* 1774 */     return l16Tos8;
/*      */   }
/*      */   
/*      */   static short[] getsRGB8ToLinearRGB16LUT() {
/* 1778 */     if (s8Tol16 == null) {
/* 1779 */       s8Tol16 = new short[256];
/*      */ 
/*      */       
/* 1782 */       for (byte b = 0; b <= 'ÿ'; b++) {
/* 1783 */         float f2, f1 = b / 255.0F;
/* 1784 */         if (f1 <= 0.04045F) {
/* 1785 */           f2 = f1 / 12.92F;
/*      */         } else {
/* 1787 */           f2 = (float)Math.pow(((f1 + 0.055F) / 1.055F), 2.4D);
/*      */         } 
/* 1789 */         s8Tol16[b] = (short)Math.round(f2 * 65535.0F);
/*      */       } 
/*      */     } 
/* 1792 */     return s8Tol16;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static byte[] getGray8TosRGB8LUT(ICC_ColorSpace paramICC_ColorSpace) {
/* 1803 */     if (isLinearGRAYspace(paramICC_ColorSpace)) {
/* 1804 */       return getLinearRGB8TosRGB8LUT();
/*      */     }
/* 1806 */     if (g8Tos8Map != null) {
/* 1807 */       byte[] arrayOfByte = g8Tos8Map.get(paramICC_ColorSpace);
/* 1808 */       if (arrayOfByte != null) {
/* 1809 */         return arrayOfByte;
/*      */       }
/*      */     } 
/* 1812 */     byte[] arrayOfByte1 = new byte[256];
/* 1813 */     for (byte b1 = 0; b1 <= 'ÿ'; b1++) {
/* 1814 */       arrayOfByte1[b1] = (byte)b1;
/*      */     }
/* 1816 */     ColorTransform[] arrayOfColorTransform = new ColorTransform[2];
/* 1817 */     PCMM pCMM = CMSManager.getModule();
/*      */     
/* 1819 */     ICC_ColorSpace iCC_ColorSpace = (ICC_ColorSpace)ColorSpace.getInstance(1000);
/* 1820 */     arrayOfColorTransform[0] = pCMM.createTransform(paramICC_ColorSpace
/* 1821 */         .getProfile(), -1, 1);
/* 1822 */     arrayOfColorTransform[1] = pCMM.createTransform(iCC_ColorSpace
/* 1823 */         .getProfile(), -1, 2);
/* 1824 */     ColorTransform colorTransform = pCMM.createTransform(arrayOfColorTransform);
/* 1825 */     byte[] arrayOfByte2 = colorTransform.colorConvert(arrayOfByte1, (byte[])null);
/* 1826 */     for (byte b2 = 0, b3 = 2; b2 <= 'ÿ'; b2++, b3 += 3)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1832 */       arrayOfByte1[b2] = arrayOfByte2[b3];
/*      */     }
/* 1834 */     if (g8Tos8Map == null) {
/* 1835 */       g8Tos8Map = Collections.synchronizedMap(new WeakHashMap<>(2));
/*      */     }
/* 1837 */     g8Tos8Map.put(paramICC_ColorSpace, arrayOfByte1);
/* 1838 */     return arrayOfByte1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static byte[] getLinearGray16ToOtherGray8LUT(ICC_ColorSpace paramICC_ColorSpace) {
/* 1847 */     if (lg16Toog8Map != null) {
/* 1848 */       byte[] arrayOfByte1 = lg16Toog8Map.get(paramICC_ColorSpace);
/* 1849 */       if (arrayOfByte1 != null) {
/* 1850 */         return arrayOfByte1;
/*      */       }
/*      */     } 
/* 1853 */     short[] arrayOfShort = new short[65536];
/* 1854 */     for (byte b1 = 0; b1 <= '￿'; b1++) {
/* 1855 */       arrayOfShort[b1] = (short)b1;
/*      */     }
/* 1857 */     ColorTransform[] arrayOfColorTransform = new ColorTransform[2];
/* 1858 */     PCMM pCMM = CMSManager.getModule();
/*      */     
/* 1860 */     ICC_ColorSpace iCC_ColorSpace = (ICC_ColorSpace)ColorSpace.getInstance(1003);
/* 1861 */     arrayOfColorTransform[0] = pCMM.createTransform(iCC_ColorSpace
/* 1862 */         .getProfile(), -1, 1);
/* 1863 */     arrayOfColorTransform[1] = pCMM.createTransform(paramICC_ColorSpace
/* 1864 */         .getProfile(), -1, 2);
/* 1865 */     ColorTransform colorTransform = pCMM.createTransform(arrayOfColorTransform);
/* 1866 */     arrayOfShort = colorTransform.colorConvert(arrayOfShort, (short[])null);
/* 1867 */     byte[] arrayOfByte = new byte[65536];
/* 1868 */     for (byte b2 = 0; b2 <= '￿'; b2++)
/*      */     {
/* 1870 */       arrayOfByte[b2] = (byte)(int)((arrayOfShort[b2] & 0xFFFF) * 0.0038910506F + 0.5F);
/*      */     }
/*      */     
/* 1873 */     if (lg16Toog8Map == null) {
/* 1874 */       lg16Toog8Map = Collections.synchronizedMap(new WeakHashMap<>(2));
/*      */     }
/* 1876 */     lg16Toog8Map.put(paramICC_ColorSpace, arrayOfByte);
/* 1877 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static byte[] getGray16TosRGB8LUT(ICC_ColorSpace paramICC_ColorSpace) {
/* 1888 */     if (isLinearGRAYspace(paramICC_ColorSpace)) {
/* 1889 */       return getLinearRGB16TosRGB8LUT();
/*      */     }
/* 1891 */     if (g16Tos8Map != null) {
/* 1892 */       byte[] arrayOfByte1 = g16Tos8Map.get(paramICC_ColorSpace);
/* 1893 */       if (arrayOfByte1 != null) {
/* 1894 */         return arrayOfByte1;
/*      */       }
/*      */     } 
/* 1897 */     short[] arrayOfShort = new short[65536];
/* 1898 */     for (byte b1 = 0; b1 <= '￿'; b1++) {
/* 1899 */       arrayOfShort[b1] = (short)b1;
/*      */     }
/* 1901 */     ColorTransform[] arrayOfColorTransform = new ColorTransform[2];
/* 1902 */     PCMM pCMM = CMSManager.getModule();
/*      */     
/* 1904 */     ICC_ColorSpace iCC_ColorSpace = (ICC_ColorSpace)ColorSpace.getInstance(1000);
/* 1905 */     arrayOfColorTransform[0] = pCMM.createTransform(paramICC_ColorSpace
/* 1906 */         .getProfile(), -1, 1);
/* 1907 */     arrayOfColorTransform[1] = pCMM.createTransform(iCC_ColorSpace
/* 1908 */         .getProfile(), -1, 2);
/* 1909 */     ColorTransform colorTransform = pCMM.createTransform(arrayOfColorTransform);
/* 1910 */     arrayOfShort = colorTransform.colorConvert(arrayOfShort, (short[])null);
/* 1911 */     byte[] arrayOfByte = new byte[65536];
/* 1912 */     for (byte b2 = 0, b3 = 2; b2 <= '￿'; b2++, b3 += 3)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1920 */       arrayOfByte[b2] = (byte)(int)((arrayOfShort[b3] & 0xFFFF) * 0.0038910506F + 0.5F);
/*      */     }
/*      */     
/* 1923 */     if (g16Tos8Map == null) {
/* 1924 */       g16Tos8Map = Collections.synchronizedMap(new WeakHashMap<>(2));
/*      */     }
/* 1926 */     g16Tos8Map.put(paramICC_ColorSpace, arrayOfByte);
/* 1927 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static short[] getLinearGray16ToOtherGray16LUT(ICC_ColorSpace paramICC_ColorSpace) {
/* 1936 */     if (lg16Toog16Map != null) {
/* 1937 */       short[] arrayOfShort = lg16Toog16Map.get(paramICC_ColorSpace);
/* 1938 */       if (arrayOfShort != null) {
/* 1939 */         return arrayOfShort;
/*      */       }
/*      */     } 
/* 1942 */     short[] arrayOfShort1 = new short[65536];
/* 1943 */     for (byte b = 0; b <= '￿'; b++) {
/* 1944 */       arrayOfShort1[b] = (short)b;
/*      */     }
/* 1946 */     ColorTransform[] arrayOfColorTransform = new ColorTransform[2];
/* 1947 */     PCMM pCMM = CMSManager.getModule();
/*      */     
/* 1949 */     ICC_ColorSpace iCC_ColorSpace = (ICC_ColorSpace)ColorSpace.getInstance(1003);
/* 1950 */     arrayOfColorTransform[0] = pCMM.createTransform(iCC_ColorSpace
/* 1951 */         .getProfile(), -1, 1);
/* 1952 */     arrayOfColorTransform[1] = pCMM.createTransform(paramICC_ColorSpace
/* 1953 */         .getProfile(), -1, 2);
/* 1954 */     ColorTransform colorTransform = pCMM.createTransform(arrayOfColorTransform);
/*      */     
/* 1956 */     short[] arrayOfShort2 = colorTransform.colorConvert(arrayOfShort1, (short[])null);
/* 1957 */     if (lg16Toog16Map == null) {
/* 1958 */       lg16Toog16Map = Collections.synchronizedMap(new WeakHashMap<>(2));
/*      */     }
/* 1960 */     lg16Toog16Map.put(paramICC_ColorSpace, arrayOfShort2);
/* 1961 */     return arrayOfShort2;
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   public abstract int getRed(int paramInt);
/*      */   
/*      */   public abstract int getGreen(int paramInt);
/*      */   
/*      */   public abstract int getBlue(int paramInt);
/*      */   
/*      */   public abstract int getAlpha(int paramInt);
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/ColorModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */