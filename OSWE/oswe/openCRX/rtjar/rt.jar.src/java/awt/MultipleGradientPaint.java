/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MultipleGradientPaint
/*     */   implements Paint
/*     */ {
/*     */   final int transparency;
/*     */   final float[] fractions;
/*     */   final Color[] colors;
/*     */   final AffineTransform gradientTransform;
/*     */   final CycleMethod cycleMethod;
/*     */   final ColorSpaceType colorSpace;
/*     */   ColorModel model;
/*     */   float[] normalizedIntervals;
/*     */   boolean isSimpleLookup;
/*     */   SoftReference<int[][]> gradients;
/*     */   SoftReference<int[]> gradient;
/*     */   int fastGradientArraySize;
/*     */   
/*     */   public enum CycleMethod
/*     */   {
/*  51 */     NO_CYCLE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     REFLECT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     REPEAT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum ColorSpaceType
/*     */   {
/*  73 */     SRGB,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     LINEAR_RGB;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MultipleGradientPaint(float[] paramArrayOffloat, Color[] paramArrayOfColor, CycleMethod paramCycleMethod, ColorSpaceType paramColorSpaceType, AffineTransform paramAffineTransform) {
/* 142 */     if (paramArrayOffloat == null) {
/* 143 */       throw new NullPointerException("Fractions array cannot be null");
/*     */     }
/*     */     
/* 146 */     if (paramArrayOfColor == null) {
/* 147 */       throw new NullPointerException("Colors array cannot be null");
/*     */     }
/*     */     
/* 150 */     if (paramCycleMethod == null) {
/* 151 */       throw new NullPointerException("Cycle method cannot be null");
/*     */     }
/*     */     
/* 154 */     if (paramColorSpaceType == null) {
/* 155 */       throw new NullPointerException("Color space cannot be null");
/*     */     }
/*     */     
/* 158 */     if (paramAffineTransform == null) {
/* 159 */       throw new NullPointerException("Gradient transform cannot be null");
/*     */     }
/*     */ 
/*     */     
/* 163 */     if (paramArrayOffloat.length != paramArrayOfColor.length) {
/* 164 */       throw new IllegalArgumentException("Colors and fractions must have equal size");
/*     */     }
/*     */ 
/*     */     
/* 168 */     if (paramArrayOfColor.length < 2) {
/* 169 */       throw new IllegalArgumentException("User must specify at least 2 colors");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     float f = -1.0F;
/* 176 */     for (float f1 : paramArrayOffloat) {
/* 177 */       if (f1 < 0.0F || f1 > 1.0F) {
/* 178 */         throw new IllegalArgumentException("Fraction values must be in the range 0 to 1: " + f1);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 183 */       if (f1 <= f) {
/* 184 */         throw new IllegalArgumentException("Keyframe fractions must be increasing: " + f1);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 189 */       f = f1;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 196 */     boolean bool1 = false;
/* 197 */     boolean bool2 = false;
/* 198 */     int i = paramArrayOffloat.length;
/* 199 */     byte b1 = 0;
/*     */     
/* 201 */     if (paramArrayOffloat[0] != 0.0F) {
/*     */       
/* 203 */       bool1 = true;
/* 204 */       i++;
/* 205 */       b1++;
/*     */     } 
/* 207 */     if (paramArrayOffloat[paramArrayOffloat.length - 1] != 1.0F) {
/*     */       
/* 209 */       bool2 = true;
/* 210 */       i++;
/*     */     } 
/*     */     
/* 213 */     this.fractions = new float[i];
/* 214 */     System.arraycopy(paramArrayOffloat, 0, this.fractions, b1, paramArrayOffloat.length);
/* 215 */     this.colors = new Color[i];
/* 216 */     System.arraycopy(paramArrayOfColor, 0, this.colors, b1, paramArrayOfColor.length);
/*     */     
/* 218 */     if (bool1) {
/* 219 */       this.fractions[0] = 0.0F;
/* 220 */       this.colors[0] = paramArrayOfColor[0];
/*     */     } 
/* 222 */     if (bool2) {
/* 223 */       this.fractions[i - 1] = 1.0F;
/* 224 */       this.colors[i - 1] = paramArrayOfColor[paramArrayOfColor.length - 1];
/*     */     } 
/*     */ 
/*     */     
/* 228 */     this.colorSpace = paramColorSpaceType;
/* 229 */     this.cycleMethod = paramCycleMethod;
/*     */ 
/*     */     
/* 232 */     this.gradientTransform = new AffineTransform(paramAffineTransform);
/*     */ 
/*     */     
/* 235 */     boolean bool3 = true;
/* 236 */     for (byte b2 = 0; b2 < paramArrayOfColor.length; b2++) {
/* 237 */       bool3 = (bool3 && paramArrayOfColor[b2].getAlpha() == 255) ? true : false;
/*     */     }
/* 239 */     this.transparency = bool3 ? 1 : 3;
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
/*     */   public final float[] getFractions() {
/* 252 */     return Arrays.copyOf(this.fractions, this.fractions.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Color[] getColors() {
/* 263 */     return Arrays.<Color>copyOf(this.colors, this.colors.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final CycleMethod getCycleMethod() {
/* 272 */     return this.cycleMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ColorSpaceType getColorSpace() {
/* 283 */     return this.colorSpace;
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
/*     */   public final AffineTransform getTransform() {
/* 296 */     return new AffineTransform(this.gradientTransform);
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
/*     */   public final int getTransparency() {
/* 309 */     return this.transparency;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/MultipleGradientPaint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */