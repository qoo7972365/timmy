/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.color.ColorSpace;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.beans.ConstructorProperties;
/*      */ import java.io.Serializable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Color
/*      */   implements Paint, Serializable
/*      */ {
/*   65 */   public static final Color white = new Color(255, 255, 255);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   71 */   public static final Color WHITE = white;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   76 */   public static final Color lightGray = new Color(192, 192, 192);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   82 */   public static final Color LIGHT_GRAY = lightGray;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   87 */   public static final Color gray = new Color(128, 128, 128);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   93 */   public static final Color GRAY = gray;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   98 */   public static final Color darkGray = new Color(64, 64, 64);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  104 */   public static final Color DARK_GRAY = darkGray;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  109 */   public static final Color black = new Color(0, 0, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  115 */   public static final Color BLACK = black;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  120 */   public static final Color red = new Color(255, 0, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  126 */   public static final Color RED = red;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  131 */   public static final Color pink = new Color(255, 175, 175);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  137 */   public static final Color PINK = pink;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  142 */   public static final Color orange = new Color(255, 200, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  148 */   public static final Color ORANGE = orange;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  153 */   public static final Color yellow = new Color(255, 255, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  159 */   public static final Color YELLOW = yellow;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  164 */   public static final Color green = new Color(0, 255, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  170 */   public static final Color GREEN = green;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  175 */   public static final Color magenta = new Color(255, 0, 255);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  181 */   public static final Color MAGENTA = magenta;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  186 */   public static final Color cyan = new Color(0, 255, 255);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  192 */   public static final Color CYAN = cyan;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  197 */   public static final Color blue = new Color(0, 0, 255);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  203 */   public static final Color BLUE = blue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int value;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  222 */   private float[] frgbvalue = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  234 */   private float[] fvalue = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  244 */   private float falpha = 0.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  254 */   private ColorSpace cs = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 118526816881161077L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final double FACTOR = 0.7D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  275 */     Toolkit.loadLibraries();
/*  276 */     if (!GraphicsEnvironment.isHeadless()) {
/*  277 */       initIDs();
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
/*      */   private static void testColorValueRange(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  290 */     boolean bool = false;
/*  291 */     String str = "";
/*      */     
/*  293 */     if (paramInt4 < 0 || paramInt4 > 255) {
/*  294 */       bool = true;
/*  295 */       str = str + " Alpha";
/*      */     } 
/*  297 */     if (paramInt1 < 0 || paramInt1 > 255) {
/*  298 */       bool = true;
/*  299 */       str = str + " Red";
/*      */     } 
/*  301 */     if (paramInt2 < 0 || paramInt2 > 255) {
/*  302 */       bool = true;
/*  303 */       str = str + " Green";
/*      */     } 
/*  305 */     if (paramInt3 < 0 || paramInt3 > 255) {
/*  306 */       bool = true;
/*  307 */       str = str + " Blue";
/*      */     } 
/*  309 */     if (bool == true) {
/*  310 */       throw new IllegalArgumentException("Color parameter outside of expected range:" + str);
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
/*      */   private static void testColorValueRange(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  325 */     boolean bool = false;
/*  326 */     String str = "";
/*  327 */     if (paramFloat4 < 0.0D || paramFloat4 > 1.0D) {
/*  328 */       bool = true;
/*  329 */       str = str + " Alpha";
/*      */     } 
/*  331 */     if (paramFloat1 < 0.0D || paramFloat1 > 1.0D) {
/*  332 */       bool = true;
/*  333 */       str = str + " Red";
/*      */     } 
/*  335 */     if (paramFloat2 < 0.0D || paramFloat2 > 1.0D) {
/*  336 */       bool = true;
/*  337 */       str = str + " Green";
/*      */     } 
/*  339 */     if (paramFloat3 < 0.0D || paramFloat3 > 1.0D) {
/*  340 */       bool = true;
/*  341 */       str = str + " Blue";
/*      */     } 
/*  343 */     if (bool == true) {
/*  344 */       throw new IllegalArgumentException("Color parameter outside of expected range:" + str);
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
/*      */   public Color(int paramInt1, int paramInt2, int paramInt3) {
/*  369 */     this(paramInt1, paramInt2, paramInt3, 255);
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
/*      */   @ConstructorProperties({"red", "green", "blue", "alpha"})
/*      */   public Color(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  391 */     this.value = (paramInt4 & 0xFF) << 24 | (paramInt1 & 0xFF) << 16 | (paramInt2 & 0xFF) << 8 | (paramInt3 & 0xFF) << 0;
/*      */ 
/*      */ 
/*      */     
/*  395 */     testColorValueRange(paramInt1, paramInt2, paramInt3, paramInt4);
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
/*      */   public Color(int paramInt) {
/*  414 */     this.value = 0xFF000000 | paramInt;
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
/*      */   public Color(int paramInt, boolean paramBoolean) {
/*  435 */     if (paramBoolean) {
/*  436 */       this.value = paramInt;
/*      */     } else {
/*  438 */       this.value = 0xFF000000 | paramInt;
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
/*      */   public Color(float paramFloat1, float paramFloat2, float paramFloat3) {
/*  461 */     this((int)((paramFloat1 * 255.0F) + 0.5D), (int)((paramFloat2 * 255.0F) + 0.5D), (int)((paramFloat3 * 255.0F) + 0.5D));
/*  462 */     testColorValueRange(paramFloat1, paramFloat2, paramFloat3, 1.0F);
/*  463 */     this.frgbvalue = new float[3];
/*  464 */     this.frgbvalue[0] = paramFloat1;
/*  465 */     this.frgbvalue[1] = paramFloat2;
/*  466 */     this.frgbvalue[2] = paramFloat3;
/*  467 */     this.falpha = 1.0F;
/*  468 */     this.fvalue = this.frgbvalue;
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
/*      */   public Color(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  490 */     this((int)((paramFloat1 * 255.0F) + 0.5D), (int)((paramFloat2 * 255.0F) + 0.5D), (int)((paramFloat3 * 255.0F) + 0.5D), (int)((paramFloat4 * 255.0F) + 0.5D));
/*  491 */     this.frgbvalue = new float[3];
/*  492 */     this.frgbvalue[0] = paramFloat1;
/*  493 */     this.frgbvalue[1] = paramFloat2;
/*  494 */     this.frgbvalue[2] = paramFloat3;
/*  495 */     this.falpha = paramFloat4;
/*  496 */     this.fvalue = this.frgbvalue;
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
/*      */   public Color(ColorSpace paramColorSpace, float[] paramArrayOffloat, float paramFloat) {
/*  518 */     boolean bool = false;
/*  519 */     String str = "";
/*  520 */     int i = paramColorSpace.getNumComponents();
/*  521 */     this.fvalue = new float[i];
/*  522 */     for (byte b = 0; b < i; b++) {
/*  523 */       if (paramArrayOffloat[b] < 0.0D || paramArrayOffloat[b] > 1.0D) {
/*  524 */         bool = true;
/*  525 */         str = str + "Component " + b + " ";
/*      */       } else {
/*      */         
/*  528 */         this.fvalue[b] = paramArrayOffloat[b];
/*      */       } 
/*      */     } 
/*  531 */     if (paramFloat < 0.0D || paramFloat > 1.0D) {
/*  532 */       bool = true;
/*  533 */       str = str + "Alpha";
/*      */     } else {
/*  535 */       this.falpha = paramFloat;
/*      */     } 
/*  537 */     if (bool) {
/*  538 */       throw new IllegalArgumentException("Color parameter outside of expected range: " + str);
/*      */     }
/*      */ 
/*      */     
/*  542 */     this.frgbvalue = paramColorSpace.toRGB(this.fvalue);
/*  543 */     this.cs = paramColorSpace;
/*  544 */     this.value = ((int)(this.falpha * 255.0F) & 0xFF) << 24 | ((int)(this.frgbvalue[0] * 255.0F) & 0xFF) << 16 | ((int)(this.frgbvalue[1] * 255.0F) & 0xFF) << 8 | ((int)(this.frgbvalue[2] * 255.0F) & 0xFF) << 0;
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
/*      */   public int getRed() {
/*  557 */     return getRGB() >> 16 & 0xFF;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getGreen() {
/*  567 */     return getRGB() >> 8 & 0xFF;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBlue() {
/*  577 */     return getRGB() >> 0 & 0xFF;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getAlpha() {
/*  586 */     return getRGB() >> 24 & 0xFF;
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
/*      */   public int getRGB() {
/*  603 */     return this.value;
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
/*      */   public Color brighter() {
/*  627 */     int i = getRed();
/*  628 */     int j = getGreen();
/*  629 */     int k = getBlue();
/*  630 */     int m = getAlpha();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  637 */     byte b = 3;
/*  638 */     if (i == 0 && j == 0 && k == 0) {
/*  639 */       return new Color(b, b, b, m);
/*      */     }
/*  641 */     if (i > 0 && i < b) i = b; 
/*  642 */     if (j > 0 && j < b) j = b; 
/*  643 */     if (k > 0 && k < b) k = b;
/*      */     
/*  645 */     return new Color(Math.min((int)(i / 0.7D), 255), 
/*  646 */         Math.min((int)(j / 0.7D), 255), 
/*  647 */         Math.min((int)(k / 0.7D), 255), m);
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
/*      */   public Color darker() {
/*  670 */     return new Color(Math.max((int)(getRed() * 0.7D), 0), 
/*  671 */         Math.max((int)(getGreen() * 0.7D), 0), 
/*  672 */         Math.max((int)(getBlue() * 0.7D), 0), 
/*  673 */         getAlpha());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  682 */     return this.value;
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
/*      */   public boolean equals(Object paramObject) {
/*  699 */     return (paramObject instanceof Color && ((Color)paramObject).getRGB() == getRGB());
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
/*      */   public String toString() {
/*  712 */     return getClass().getName() + "[r=" + getRed() + ",g=" + getGreen() + ",b=" + getBlue() + "]";
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
/*      */   public static Color decode(String paramString) throws NumberFormatException {
/*  729 */     Integer integer = Integer.decode(paramString);
/*  730 */     int i = integer.intValue();
/*  731 */     return new Color(i >> 16 & 0xFF, i >> 8 & 0xFF, i & 0xFF);
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
/*      */   public static Color getColor(String paramString) {
/*  753 */     return getColor(paramString, (Color)null);
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
/*      */   public static Color getColor(String paramString, Color paramColor) {
/*  777 */     Integer integer = Integer.getInteger(paramString);
/*  778 */     if (integer == null) {
/*  779 */       return paramColor;
/*      */     }
/*  781 */     int i = integer.intValue();
/*  782 */     return new Color(i >> 16 & 0xFF, i >> 8 & 0xFF, i & 0xFF);
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
/*      */   public static Color getColor(String paramString, int paramInt) {
/*  807 */     Integer integer = Integer.getInteger(paramString);
/*  808 */     int i = (integer != null) ? integer.intValue() : paramInt;
/*  809 */     return new Color(i >> 16 & 0xFF, i >> 8 & 0xFF, i >> 0 & 0xFF);
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
/*      */   public static int HSBtoRGB(float paramFloat1, float paramFloat2, float paramFloat3) {
/*  840 */     int i = 0, j = 0, k = 0;
/*  841 */     if (paramFloat2 == 0.0F) {
/*  842 */       i = j = k = (int)(paramFloat3 * 255.0F + 0.5F);
/*      */     } else {
/*  844 */       float f1 = (paramFloat1 - (float)Math.floor(paramFloat1)) * 6.0F;
/*  845 */       float f2 = f1 - (float)Math.floor(f1);
/*  846 */       float f3 = paramFloat3 * (1.0F - paramFloat2);
/*  847 */       float f4 = paramFloat3 * (1.0F - paramFloat2 * f2);
/*  848 */       float f5 = paramFloat3 * (1.0F - paramFloat2 * (1.0F - f2));
/*  849 */       switch ((int)f1) {
/*      */         case 0:
/*  851 */           i = (int)(paramFloat3 * 255.0F + 0.5F);
/*  852 */           j = (int)(f5 * 255.0F + 0.5F);
/*  853 */           k = (int)(f3 * 255.0F + 0.5F);
/*      */           break;
/*      */         case 1:
/*  856 */           i = (int)(f4 * 255.0F + 0.5F);
/*  857 */           j = (int)(paramFloat3 * 255.0F + 0.5F);
/*  858 */           k = (int)(f3 * 255.0F + 0.5F);
/*      */           break;
/*      */         case 2:
/*  861 */           i = (int)(f3 * 255.0F + 0.5F);
/*  862 */           j = (int)(paramFloat3 * 255.0F + 0.5F);
/*  863 */           k = (int)(f5 * 255.0F + 0.5F);
/*      */           break;
/*      */         case 3:
/*  866 */           i = (int)(f3 * 255.0F + 0.5F);
/*  867 */           j = (int)(f4 * 255.0F + 0.5F);
/*  868 */           k = (int)(paramFloat3 * 255.0F + 0.5F);
/*      */           break;
/*      */         case 4:
/*  871 */           i = (int)(f5 * 255.0F + 0.5F);
/*  872 */           j = (int)(f3 * 255.0F + 0.5F);
/*  873 */           k = (int)(paramFloat3 * 255.0F + 0.5F);
/*      */           break;
/*      */         case 5:
/*  876 */           i = (int)(paramFloat3 * 255.0F + 0.5F);
/*  877 */           j = (int)(f3 * 255.0F + 0.5F);
/*  878 */           k = (int)(f4 * 255.0F + 0.5F);
/*      */           break;
/*      */       } 
/*      */     } 
/*  882 */     return 0xFF000000 | i << 16 | j << 8 | k << 0;
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
/*      */   public static float[] RGBtoHSB(int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOffloat) {
/*      */     float f1, f2;
/*  909 */     if (paramArrayOffloat == null) {
/*  910 */       paramArrayOffloat = new float[3];
/*      */     }
/*  912 */     int i = (paramInt1 > paramInt2) ? paramInt1 : paramInt2;
/*  913 */     if (paramInt3 > i) i = paramInt3; 
/*  914 */     int j = (paramInt1 < paramInt2) ? paramInt1 : paramInt2;
/*  915 */     if (paramInt3 < j) j = paramInt3;
/*      */     
/*  917 */     float f3 = i / 255.0F;
/*  918 */     if (i != 0) {
/*  919 */       f2 = (i - j) / i;
/*      */     } else {
/*  921 */       f2 = 0.0F;
/*  922 */     }  if (f2 == 0.0F) {
/*  923 */       f1 = 0.0F;
/*      */     } else {
/*  925 */       float f4 = (i - paramInt1) / (i - j);
/*  926 */       float f5 = (i - paramInt2) / (i - j);
/*  927 */       float f6 = (i - paramInt3) / (i - j);
/*  928 */       if (paramInt1 == i) {
/*  929 */         f1 = f6 - f5;
/*  930 */       } else if (paramInt2 == i) {
/*  931 */         f1 = 2.0F + f4 - f6;
/*      */       } else {
/*  933 */         f1 = 4.0F + f5 - f4;
/*  934 */       }  f1 /= 6.0F;
/*  935 */       if (f1 < 0.0F)
/*  936 */         f1++; 
/*      */     } 
/*  938 */     paramArrayOffloat[0] = f1;
/*  939 */     paramArrayOffloat[1] = f2;
/*  940 */     paramArrayOffloat[2] = f3;
/*  941 */     return paramArrayOffloat;
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
/*      */   public static Color getHSBColor(float paramFloat1, float paramFloat2, float paramFloat3) {
/*  963 */     return new Color(HSBtoRGB(paramFloat1, paramFloat2, paramFloat3));
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
/*      */   public float[] getRGBComponents(float[] paramArrayOffloat) {
/*      */     float[] arrayOfFloat;
/*  980 */     if (paramArrayOffloat == null) {
/*  981 */       arrayOfFloat = new float[4];
/*      */     } else {
/*  983 */       arrayOfFloat = paramArrayOffloat;
/*      */     } 
/*  985 */     if (this.frgbvalue == null) {
/*  986 */       arrayOfFloat[0] = getRed() / 255.0F;
/*  987 */       arrayOfFloat[1] = getGreen() / 255.0F;
/*  988 */       arrayOfFloat[2] = getBlue() / 255.0F;
/*  989 */       arrayOfFloat[3] = getAlpha() / 255.0F;
/*      */     } else {
/*  991 */       arrayOfFloat[0] = this.frgbvalue[0];
/*  992 */       arrayOfFloat[1] = this.frgbvalue[1];
/*  993 */       arrayOfFloat[2] = this.frgbvalue[2];
/*  994 */       arrayOfFloat[3] = this.falpha;
/*      */     } 
/*  996 */     return arrayOfFloat;
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
/*      */   public float[] getRGBColorComponents(float[] paramArrayOffloat) {
/*      */     float[] arrayOfFloat;
/* 1012 */     if (paramArrayOffloat == null) {
/* 1013 */       arrayOfFloat = new float[3];
/*      */     } else {
/* 1015 */       arrayOfFloat = paramArrayOffloat;
/*      */     } 
/* 1017 */     if (this.frgbvalue == null) {
/* 1018 */       arrayOfFloat[0] = getRed() / 255.0F;
/* 1019 */       arrayOfFloat[1] = getGreen() / 255.0F;
/* 1020 */       arrayOfFloat[2] = getBlue() / 255.0F;
/*      */     } else {
/* 1022 */       arrayOfFloat[0] = this.frgbvalue[0];
/* 1023 */       arrayOfFloat[1] = this.frgbvalue[1];
/* 1024 */       arrayOfFloat[2] = this.frgbvalue[2];
/*      */     } 
/* 1026 */     return arrayOfFloat;
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
/*      */   public float[] getComponents(float[] paramArrayOffloat) {
/*      */     float[] arrayOfFloat;
/* 1046 */     if (this.fvalue == null) {
/* 1047 */       return getRGBComponents(paramArrayOffloat);
/*      */     }
/* 1049 */     int i = this.fvalue.length;
/* 1050 */     if (paramArrayOffloat == null) {
/* 1051 */       arrayOfFloat = new float[i + 1];
/*      */     } else {
/* 1053 */       arrayOfFloat = paramArrayOffloat;
/*      */     } 
/* 1055 */     for (byte b = 0; b < i; b++) {
/* 1056 */       arrayOfFloat[b] = this.fvalue[b];
/*      */     }
/* 1058 */     arrayOfFloat[i] = this.falpha;
/* 1059 */     return arrayOfFloat;
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
/*      */   public float[] getColorComponents(float[] paramArrayOffloat) {
/*      */     float[] arrayOfFloat;
/* 1078 */     if (this.fvalue == null) {
/* 1079 */       return getRGBColorComponents(paramArrayOffloat);
/*      */     }
/* 1081 */     int i = this.fvalue.length;
/* 1082 */     if (paramArrayOffloat == null) {
/* 1083 */       arrayOfFloat = new float[i];
/*      */     } else {
/* 1085 */       arrayOfFloat = paramArrayOffloat;
/*      */     } 
/* 1087 */     for (byte b = 0; b < i; b++) {
/* 1088 */       arrayOfFloat[b] = this.fvalue[b];
/*      */     }
/* 1090 */     return arrayOfFloat;
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
/*      */   public float[] getComponents(ColorSpace paramColorSpace, float[] paramArrayOffloat) {
/*      */     float[] arrayOfFloat1;
/* 1110 */     if (this.cs == null) {
/* 1111 */       this.cs = ColorSpace.getInstance(1000);
/*      */     }
/*      */     
/* 1114 */     if (this.fvalue == null) {
/* 1115 */       arrayOfFloat1 = new float[3];
/* 1116 */       arrayOfFloat1[0] = getRed() / 255.0F;
/* 1117 */       arrayOfFloat1[1] = getGreen() / 255.0F;
/* 1118 */       arrayOfFloat1[2] = getBlue() / 255.0F;
/*      */     } else {
/* 1120 */       arrayOfFloat1 = this.fvalue;
/*      */     } 
/* 1122 */     float[] arrayOfFloat2 = this.cs.toCIEXYZ(arrayOfFloat1);
/* 1123 */     float[] arrayOfFloat3 = paramColorSpace.fromCIEXYZ(arrayOfFloat2);
/* 1124 */     if (paramArrayOffloat == null) {
/* 1125 */       paramArrayOffloat = new float[arrayOfFloat3.length + 1];
/*      */     }
/* 1127 */     for (byte b = 0; b < arrayOfFloat3.length; b++) {
/* 1128 */       paramArrayOffloat[b] = arrayOfFloat3[b];
/*      */     }
/* 1130 */     if (this.fvalue == null) {
/* 1131 */       paramArrayOffloat[arrayOfFloat3.length] = getAlpha() / 255.0F;
/*      */     } else {
/* 1133 */       paramArrayOffloat[arrayOfFloat3.length] = this.falpha;
/*      */     } 
/* 1135 */     return paramArrayOffloat;
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
/*      */   public float[] getColorComponents(ColorSpace paramColorSpace, float[] paramArrayOffloat) {
/*      */     float[] arrayOfFloat1;
/* 1154 */     if (this.cs == null) {
/* 1155 */       this.cs = ColorSpace.getInstance(1000);
/*      */     }
/*      */     
/* 1158 */     if (this.fvalue == null) {
/* 1159 */       arrayOfFloat1 = new float[3];
/* 1160 */       arrayOfFloat1[0] = getRed() / 255.0F;
/* 1161 */       arrayOfFloat1[1] = getGreen() / 255.0F;
/* 1162 */       arrayOfFloat1[2] = getBlue() / 255.0F;
/*      */     } else {
/* 1164 */       arrayOfFloat1 = this.fvalue;
/*      */     } 
/* 1166 */     float[] arrayOfFloat2 = this.cs.toCIEXYZ(arrayOfFloat1);
/* 1167 */     float[] arrayOfFloat3 = paramColorSpace.fromCIEXYZ(arrayOfFloat2);
/* 1168 */     if (paramArrayOffloat == null) {
/* 1169 */       return arrayOfFloat3;
/*      */     }
/* 1171 */     for (byte b = 0; b < arrayOfFloat3.length; b++) {
/* 1172 */       paramArrayOffloat[b] = arrayOfFloat3[b];
/*      */     }
/* 1174 */     return paramArrayOffloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ColorSpace getColorSpace() {
/* 1182 */     if (this.cs == null) {
/* 1183 */       this.cs = ColorSpace.getInstance(1000);
/*      */     }
/* 1185 */     return this.cs;
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
/*      */   public synchronized PaintContext createContext(ColorModel paramColorModel, Rectangle paramRectangle, Rectangle2D paramRectangle2D, AffineTransform paramAffineTransform, RenderingHints paramRenderingHints) {
/* 1220 */     return new ColorPaintContext(getRGB(), paramColorModel);
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
/* 1232 */     int i = getAlpha();
/* 1233 */     if (i == 255) {
/* 1234 */       return 1;
/*      */     }
/* 1236 */     if (i == 0) {
/* 1237 */       return 2;
/*      */     }
/*      */     
/* 1240 */     return 3;
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/Color.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */