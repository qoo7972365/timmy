/*     */ package sun.java2d.xr;
/*     */ 
/*     */ import java.awt.MultipleGradientPaint;
/*     */ import java.awt.geom.AffineTransform;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XRUtils
/*     */ {
/*     */   public static final int None = 0;
/*     */   public static final byte PictOpClear = 0;
/*     */   public static final byte PictOpSrc = 1;
/*     */   public static final byte PictOpDst = 2;
/*     */   public static final byte PictOpOver = 3;
/*     */   public static final byte PictOpOverReverse = 4;
/*     */   public static final byte PictOpIn = 5;
/*     */   public static final byte PictOpInReverse = 6;
/*     */   public static final byte PictOpOut = 7;
/*     */   public static final byte PictOpOutReverse = 8;
/*     */   public static final byte PictOpAtop = 9;
/*     */   public static final byte PictOpAtopReverse = 10;
/*     */   public static final byte PictOpXor = 11;
/*     */   public static final byte PictOpAdd = 12;
/*     */   public static final byte PictOpSaturate = 13;
/*     */   public static final int RepeatNone = 0;
/*     */   public static final int RepeatNormal = 1;
/*     */   public static final int RepeatPad = 2;
/*     */   public static final int RepeatReflect = 3;
/*     */   public static final int FAST = 0;
/*     */   public static final int GOOD = 1;
/*     */   public static final int BEST = 2;
/*  70 */   public static final byte[] FAST_NAME = "fast".getBytes();
/*  71 */   public static final byte[] GOOD_NAME = "good".getBytes();
/*  72 */   public static final byte[] BEST_NAME = "best".getBytes();
/*     */ 
/*     */   
/*     */   public static final int PictStandardARGB32 = 0;
/*     */   
/*     */   public static final int PictStandardRGB24 = 1;
/*     */   
/*     */   public static final int PictStandardA8 = 2;
/*     */   
/*     */   public static final int PictStandardA4 = 3;
/*     */   
/*     */   public static final int PictStandardA1 = 4;
/*     */ 
/*     */   
/*     */   public static int ATransOpToXRQuality(int paramInt) {
/*  87 */     switch (paramInt) {
/*     */       case 1:
/*  89 */         return 0;
/*     */       
/*     */       case 2:
/*  92 */         return 1;
/*     */       
/*     */       case 3:
/*  95 */         return 2;
/*     */     } 
/*     */     
/*  98 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] ATransOpToXRQualityName(int paramInt) {
/* 107 */     switch (paramInt) {
/*     */       case 1:
/* 109 */         return FAST_NAME;
/*     */       
/*     */       case 2:
/* 112 */         return GOOD_NAME;
/*     */       
/*     */       case 3:
/* 115 */         return BEST_NAME;
/*     */     } 
/*     */     
/* 118 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] getFilterName(int paramInt) {
/* 123 */     switch (paramInt) {
/*     */       case 0:
/* 125 */         return FAST_NAME;
/*     */       case 1:
/* 127 */         return GOOD_NAME;
/*     */       case 2:
/* 129 */         return BEST_NAME;
/*     */     } 
/*     */     
/* 132 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPictureFormatForTransparency(int paramInt) {
/* 141 */     switch (paramInt) {
/*     */       case 1:
/* 143 */         return 1;
/*     */       
/*     */       case 2:
/*     */       case 3:
/* 147 */         return 0;
/*     */     } 
/*     */     
/* 150 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static SurfaceType getXRSurfaceTypeForTransparency(int paramInt) {
/* 155 */     if (paramInt == 1) {
/* 156 */       return SurfaceType.IntRgb;
/*     */     }
/* 158 */     return SurfaceType.IntArgbPre;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getRepeatForCycleMethod(MultipleGradientPaint.CycleMethod paramCycleMethod) {
/* 166 */     if (paramCycleMethod.equals(MultipleGradientPaint.CycleMethod.NO_CYCLE))
/* 167 */       return 2; 
/* 168 */     if (paramCycleMethod.equals(MultipleGradientPaint.CycleMethod.REFLECT))
/* 169 */       return 3; 
/* 170 */     if (paramCycleMethod.equals(MultipleGradientPaint.CycleMethod.REPEAT)) {
/* 171 */       return 1;
/*     */     }
/*     */     
/* 174 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int XDoubleToFixed(double paramDouble) {
/* 181 */     return (int)(paramDouble * 65536.0D);
/*     */   }
/*     */   
/*     */   public static double XFixedToDouble(int paramInt) {
/* 185 */     return paramInt / 65536.0D;
/*     */   }
/*     */   
/*     */   public static int[] convertFloatsToFixed(float[] paramArrayOffloat) {
/* 189 */     int[] arrayOfInt = new int[paramArrayOffloat.length];
/*     */     
/* 191 */     for (byte b = 0; b < paramArrayOffloat.length; b++) {
/* 192 */       arrayOfInt[b] = XDoubleToFixed(paramArrayOffloat[b]);
/*     */     }
/*     */     
/* 195 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   public static long intToULong(int paramInt) {
/* 199 */     if (paramInt < 0) {
/* 200 */       return paramInt + 4294967296L;
/*     */     }
/*     */ 
/*     */     
/* 204 */     return paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte j2dAlphaCompToXR(int paramInt) {
/* 212 */     switch (paramInt) {
/*     */       case 1:
/* 214 */         return 0;
/*     */       
/*     */       case 2:
/* 217 */         return 1;
/*     */       
/*     */       case 9:
/* 220 */         return 2;
/*     */       
/*     */       case 3:
/* 223 */         return 3;
/*     */       
/*     */       case 4:
/* 226 */         return 4;
/*     */       
/*     */       case 5:
/* 229 */         return 5;
/*     */       
/*     */       case 6:
/* 232 */         return 6;
/*     */       
/*     */       case 7:
/* 235 */         return 7;
/*     */       
/*     */       case 8:
/* 238 */         return 8;
/*     */       
/*     */       case 10:
/* 241 */         return 9;
/*     */       
/*     */       case 11:
/* 244 */         return 10;
/*     */       
/*     */       case 12:
/* 247 */         return 11;
/*     */     } 
/*     */     
/* 250 */     throw new InternalError("No XRender equivalent available for requested java2d composition rule: " + paramInt);
/*     */   }
/*     */   
/*     */   public static short clampToShort(int paramInt) {
/* 254 */     return (short)((paramInt > 32767) ? Short.MAX_VALUE : ((paramInt < -32768) ? Short.MIN_VALUE : paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int clampToUShort(int paramInt) {
/* 260 */     return (paramInt > 65535) ? 65535 : ((paramInt < 0) ? 0 : paramInt);
/*     */   }
/*     */   
/*     */   public static boolean isTransformQuadrantRotated(AffineTransform paramAffineTransform) {
/* 264 */     return ((paramAffineTransform.getType() & 0x30) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isMaskEvaluated(byte paramByte) {
/* 269 */     switch (paramByte) {
/*     */       case 3:
/*     */       case 4:
/*     */       case 9:
/*     */       case 11:
/* 274 */         return true;
/*     */     } 
/*     */     
/* 277 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */