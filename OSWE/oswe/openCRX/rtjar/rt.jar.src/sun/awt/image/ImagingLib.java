/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.AffineTransformOp;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.BufferedImageOp;
/*     */ import java.awt.image.ByteLookupTable;
/*     */ import java.awt.image.ConvolveOp;
/*     */ import java.awt.image.Kernel;
/*     */ import java.awt.image.LookupOp;
/*     */ import java.awt.image.LookupTable;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.RasterOp;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImagingLib
/*     */ {
/*     */   static boolean useLib = true;
/*     */   static boolean verbose = false;
/*     */   private static final int NUM_NATIVE_OPS = 3;
/*     */   private static final int LOOKUP_OP = 0;
/*     */   private static final int AFFINE_OP = 1;
/*     */   private static final int CONVOLVE_OP = 2;
/*  64 */   private static Class[] nativeOpClass = new Class[3];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  92 */     PrivilegedAction<Boolean> privilegedAction = new PrivilegedAction<Boolean>()
/*     */       {
/*     */         public Boolean run() {
/*  95 */           String str = System.getProperty("os.arch");
/*     */           
/*  97 */           if (str == null || !str.startsWith("sparc")) {
/*     */             try {
/*  99 */               System.loadLibrary("mlib_image");
/* 100 */             } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
/* 101 */               return Boolean.FALSE;
/*     */             } 
/*     */           }
/*     */           
/* 105 */           boolean bool = ImagingLib.init();
/* 106 */           return Boolean.valueOf(bool);
/*     */         }
/*     */       };
/*     */     
/* 110 */     useLib = ((Boolean)AccessController.<Boolean>doPrivileged(privilegedAction)).booleanValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 117 */       nativeOpClass[0] = 
/* 118 */         Class.forName("java.awt.image.LookupOp");
/* 119 */     } catch (ClassNotFoundException classNotFoundException) {
/* 120 */       System.err.println("Could not find class: " + classNotFoundException);
/*     */     } 
/*     */     try {
/* 123 */       nativeOpClass[1] = 
/* 124 */         Class.forName("java.awt.image.AffineTransformOp");
/* 125 */     } catch (ClassNotFoundException classNotFoundException) {
/* 126 */       System.err.println("Could not find class: " + classNotFoundException);
/*     */     } 
/*     */     try {
/* 129 */       nativeOpClass[2] = 
/* 130 */         Class.forName("java.awt.image.ConvolveOp");
/* 131 */     } catch (ClassNotFoundException classNotFoundException) {
/* 132 */       System.err.println("Could not find class: " + classNotFoundException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getNativeOpIndex(Class paramClass) {
/* 142 */     byte b = -1;
/* 143 */     for (byte b1 = 0; b1 < 3; b1++) {
/* 144 */       if (paramClass == nativeOpClass[b1]) {
/* 145 */         b = b1;
/*     */         break;
/*     */       } 
/*     */     } 
/* 149 */     return b;
/*     */   } public static WritableRaster filter(RasterOp paramRasterOp, Raster paramRaster, WritableRaster paramWritableRaster) {
/*     */     LookupTable lookupTable;
/*     */     AffineTransformOp affineTransformOp;
/*     */     double[] arrayOfDouble;
/*     */     ConvolveOp convolveOp;
/* 155 */     if (!useLib) {
/* 156 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 160 */     if (paramWritableRaster == null) {
/* 161 */       paramWritableRaster = paramRasterOp.createCompatibleDestRaster(paramRaster);
/*     */     }
/*     */ 
/*     */     
/* 165 */     WritableRaster writableRaster = null;
/* 166 */     switch (getNativeOpIndex(paramRasterOp.getClass())) {
/*     */ 
/*     */       
/*     */       case 0:
/* 170 */         lookupTable = ((LookupOp)paramRasterOp).getTable();
/* 171 */         if (lookupTable.getOffset() != 0)
/*     */         {
/* 173 */           return null;
/*     */         }
/* 175 */         if (lookupTable instanceof ByteLookupTable) {
/* 176 */           ByteLookupTable byteLookupTable = (ByteLookupTable)lookupTable;
/* 177 */           if (lookupByteRaster(paramRaster, paramWritableRaster, byteLookupTable.getTable()) > 0) {
/* 178 */             writableRaster = paramWritableRaster;
/*     */           }
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 1:
/* 184 */         affineTransformOp = (AffineTransformOp)paramRasterOp;
/* 185 */         arrayOfDouble = new double[6];
/* 186 */         affineTransformOp.getTransform().getMatrix(arrayOfDouble);
/* 187 */         if (transformRaster(paramRaster, paramWritableRaster, arrayOfDouble, affineTransformOp
/* 188 */             .getInterpolationType()) > 0) {
/* 189 */           writableRaster = paramWritableRaster;
/*     */         }
/*     */         break;
/*     */       
/*     */       case 2:
/* 194 */         convolveOp = (ConvolveOp)paramRasterOp;
/* 195 */         if (convolveRaster(paramRaster, paramWritableRaster, convolveOp
/* 196 */             .getKernel(), convolveOp.getEdgeCondition()) > 0) {
/* 197 */           writableRaster = paramWritableRaster;
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     if (writableRaster != null) {
/* 206 */       SunWritableRaster.markDirty(writableRaster);
/*     */     }
/*     */     
/* 209 */     return writableRaster;
/*     */   } public static BufferedImage filter(BufferedImageOp paramBufferedImageOp, BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2) {
/*     */     LookupTable lookupTable;
/*     */     AffineTransformOp affineTransformOp;
/*     */     double[] arrayOfDouble;
/*     */     AffineTransform affineTransform;
/*     */     ConvolveOp convolveOp;
/* 216 */     if (verbose) {
/* 217 */       System.out.println("in filter and op is " + paramBufferedImageOp + "bufimage is " + paramBufferedImage1 + " and " + paramBufferedImage2);
/*     */     }
/*     */ 
/*     */     
/* 221 */     if (!useLib) {
/* 222 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 226 */     if (paramBufferedImage2 == null) {
/* 227 */       paramBufferedImage2 = paramBufferedImageOp.createCompatibleDestImage(paramBufferedImage1, null);
/*     */     }
/*     */     
/* 230 */     BufferedImage bufferedImage = null;
/* 231 */     switch (getNativeOpIndex(paramBufferedImageOp.getClass())) {
/*     */ 
/*     */       
/*     */       case 0:
/* 235 */         lookupTable = ((LookupOp)paramBufferedImageOp).getTable();
/* 236 */         if (lookupTable.getOffset() != 0)
/*     */         {
/* 238 */           return null;
/*     */         }
/* 240 */         if (lookupTable instanceof ByteLookupTable) {
/* 241 */           ByteLookupTable byteLookupTable = (ByteLookupTable)lookupTable;
/* 242 */           if (lookupByteBI(paramBufferedImage1, paramBufferedImage2, byteLookupTable.getTable()) > 0) {
/* 243 */             bufferedImage = paramBufferedImage2;
/*     */           }
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 1:
/* 249 */         affineTransformOp = (AffineTransformOp)paramBufferedImageOp;
/* 250 */         arrayOfDouble = new double[6];
/* 251 */         affineTransform = affineTransformOp.getTransform();
/* 252 */         affineTransformOp.getTransform().getMatrix(arrayOfDouble);
/*     */         
/* 254 */         if (transformBI(paramBufferedImage1, paramBufferedImage2, arrayOfDouble, affineTransformOp
/* 255 */             .getInterpolationType()) > 0) {
/* 256 */           bufferedImage = paramBufferedImage2;
/*     */         }
/*     */         break;
/*     */       
/*     */       case 2:
/* 261 */         convolveOp = (ConvolveOp)paramBufferedImageOp;
/* 262 */         if (convolveBI(paramBufferedImage1, paramBufferedImage2, convolveOp.getKernel(), convolveOp
/* 263 */             .getEdgeCondition()) > 0) {
/* 264 */           bufferedImage = paramBufferedImage2;
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     if (bufferedImage != null) {
/* 273 */       SunWritableRaster.markDirty(bufferedImage);
/*     */     }
/*     */     
/* 276 */     return bufferedImage;
/*     */   }
/*     */   
/*     */   private static native boolean init();
/*     */   
/*     */   public static native int transformBI(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2, double[] paramArrayOfdouble, int paramInt);
/*     */   
/*     */   public static native int transformRaster(Raster paramRaster1, Raster paramRaster2, double[] paramArrayOfdouble, int paramInt);
/*     */   
/*     */   public static native int convolveBI(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2, Kernel paramKernel, int paramInt);
/*     */   
/*     */   public static native int convolveRaster(Raster paramRaster1, Raster paramRaster2, Kernel paramKernel, int paramInt);
/*     */   
/*     */   public static native int lookupByteBI(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2, byte[][] paramArrayOfbyte);
/*     */   
/*     */   public static native int lookupByteRaster(Raster paramRaster1, Raster paramRaster2, byte[][] paramArrayOfbyte);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/ImagingLib.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */