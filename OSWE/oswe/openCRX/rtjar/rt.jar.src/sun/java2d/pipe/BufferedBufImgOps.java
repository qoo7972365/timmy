/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.BufferedImageOp;
/*     */ import java.awt.image.ByteLookupTable;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ConvolveOp;
/*     */ import java.awt.image.Kernel;
/*     */ import java.awt.image.LookupOp;
/*     */ import java.awt.image.LookupTable;
/*     */ import java.awt.image.RescaleOp;
/*     */ import java.awt.image.ShortLookupTable;
/*     */ import sun.java2d.SurfaceData;
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
/*     */ public class BufferedBufImgOps
/*     */ {
/*     */   public static void enableBufImgOp(RenderQueue paramRenderQueue, SurfaceData paramSurfaceData, BufferedImage paramBufferedImage, BufferedImageOp paramBufferedImageOp) {
/*  52 */     if (paramBufferedImageOp instanceof ConvolveOp) {
/*  53 */       enableConvolveOp(paramRenderQueue, paramSurfaceData, (ConvolveOp)paramBufferedImageOp);
/*  54 */     } else if (paramBufferedImageOp instanceof RescaleOp) {
/*  55 */       enableRescaleOp(paramRenderQueue, paramSurfaceData, paramBufferedImage, (RescaleOp)paramBufferedImageOp);
/*  56 */     } else if (paramBufferedImageOp instanceof LookupOp) {
/*  57 */       enableLookupOp(paramRenderQueue, paramSurfaceData, paramBufferedImage, (LookupOp)paramBufferedImageOp);
/*     */     } else {
/*  59 */       throw new InternalError("Unknown BufferedImageOp");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void disableBufImgOp(RenderQueue paramRenderQueue, BufferedImageOp paramBufferedImageOp) {
/*  64 */     if (paramBufferedImageOp instanceof ConvolveOp) {
/*  65 */       disableConvolveOp(paramRenderQueue);
/*  66 */     } else if (paramBufferedImageOp instanceof RescaleOp) {
/*  67 */       disableRescaleOp(paramRenderQueue);
/*  68 */     } else if (paramBufferedImageOp instanceof LookupOp) {
/*  69 */       disableLookupOp(paramRenderQueue);
/*     */     } else {
/*  71 */       throw new InternalError("Unknown BufferedImageOp");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isConvolveOpValid(ConvolveOp paramConvolveOp) {
/*  78 */     Kernel kernel = paramConvolveOp.getKernel();
/*  79 */     int i = kernel.getWidth();
/*  80 */     int j = kernel.getHeight();
/*     */ 
/*     */ 
/*     */     
/*  84 */     if ((i != 3 || j != 3) && (i != 5 || j != 5)) {
/*  85 */       return false;
/*     */     }
/*  87 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void enableConvolveOp(RenderQueue paramRenderQueue, SurfaceData paramSurfaceData, ConvolveOp paramConvolveOp) {
/*  96 */     boolean bool = (paramConvolveOp.getEdgeCondition() == 0) ? true : false;
/*  97 */     Kernel kernel = paramConvolveOp.getKernel();
/*  98 */     int i = kernel.getWidth();
/*  99 */     int j = kernel.getHeight();
/* 100 */     int k = i * j;
/* 101 */     byte b = 4;
/* 102 */     int m = 24 + k * b;
/*     */     
/* 104 */     RenderBuffer renderBuffer = paramRenderQueue.getBuffer();
/* 105 */     paramRenderQueue.ensureCapacityAndAlignment(m, 4);
/* 106 */     renderBuffer.putInt(120);
/* 107 */     renderBuffer.putLong(paramSurfaceData.getNativeOps());
/* 108 */     renderBuffer.putInt(bool ? 1 : 0);
/* 109 */     renderBuffer.putInt(i);
/* 110 */     renderBuffer.putInt(j);
/* 111 */     renderBuffer.put(kernel.getKernelData(null));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void disableConvolveOp(RenderQueue paramRenderQueue) {
/* 116 */     RenderBuffer renderBuffer = paramRenderQueue.getBuffer();
/* 117 */     paramRenderQueue.ensureCapacity(4);
/* 118 */     renderBuffer.putInt(121);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRescaleOpValid(RescaleOp paramRescaleOp, BufferedImage paramBufferedImage) {
/* 126 */     int i = paramRescaleOp.getNumFactors();
/* 127 */     ColorModel colorModel = paramBufferedImage.getColorModel();
/*     */     
/* 129 */     if (colorModel instanceof java.awt.image.IndexColorModel) {
/* 130 */       throw new IllegalArgumentException("Rescaling cannot be performed on an indexed image");
/*     */     }
/*     */ 
/*     */     
/* 134 */     if (i != 1 && i != colorModel
/* 135 */       .getNumColorComponents() && i != colorModel
/* 136 */       .getNumComponents())
/*     */     {
/* 138 */       throw new IllegalArgumentException("Number of scaling constants does not equal the number of of color or color/alpha  components");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     int j = colorModel.getColorSpace().getType();
/* 145 */     if (j != 5 && j != 6)
/*     */     {
/*     */ 
/*     */       
/* 149 */       return false;
/*     */     }
/*     */     
/* 152 */     if (i == 2 || i > 4)
/*     */     {
/* 154 */       return false;
/*     */     }
/*     */     
/* 157 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void enableRescaleOp(RenderQueue paramRenderQueue, SurfaceData paramSurfaceData, BufferedImage paramBufferedImage, RescaleOp paramRescaleOp) {
/*     */     float[] arrayOfFloat3, arrayOfFloat4;
/* 166 */     ColorModel colorModel = paramBufferedImage.getColorModel();
/*     */ 
/*     */     
/* 169 */     boolean bool = (colorModel.hasAlpha() && colorModel.isAlphaPremultiplied()) ? true : false;
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
/* 186 */     int i = paramRescaleOp.getNumFactors();
/* 187 */     float[] arrayOfFloat1 = paramRescaleOp.getScaleFactors(null);
/* 188 */     float[] arrayOfFloat2 = paramRescaleOp.getOffsets(null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     if (i == 1) {
/* 196 */       arrayOfFloat3 = new float[4];
/* 197 */       arrayOfFloat4 = new float[4];
/* 198 */       for (byte b1 = 0; b1 < 3; b1++) {
/* 199 */         arrayOfFloat3[b1] = arrayOfFloat1[0];
/* 200 */         arrayOfFloat4[b1] = arrayOfFloat2[0];
/*     */       } 
/*     */       
/* 203 */       arrayOfFloat3[3] = 1.0F;
/* 204 */       arrayOfFloat4[3] = 0.0F;
/* 205 */     } else if (i == 3) {
/* 206 */       arrayOfFloat3 = new float[4];
/* 207 */       arrayOfFloat4 = new float[4];
/* 208 */       for (byte b1 = 0; b1 < 3; b1++) {
/* 209 */         arrayOfFloat3[b1] = arrayOfFloat1[b1];
/* 210 */         arrayOfFloat4[b1] = arrayOfFloat2[b1];
/*     */       } 
/*     */       
/* 213 */       arrayOfFloat3[3] = 1.0F;
/* 214 */       arrayOfFloat4[3] = 0.0F;
/*     */     } else {
/* 216 */       arrayOfFloat3 = arrayOfFloat1;
/* 217 */       arrayOfFloat4 = arrayOfFloat2;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 224 */     if (colorModel.getNumComponents() == 1) {
/*     */       
/* 226 */       int k = colorModel.getComponentSize(0);
/* 227 */       int m = (1 << k) - 1;
/* 228 */       for (byte b1 = 0; b1 < 3; b1++) {
/* 229 */         arrayOfFloat4[b1] = arrayOfFloat4[b1] / m;
/*     */       }
/*     */     } else {
/*     */       
/* 233 */       for (byte b1 = 0; b1 < colorModel.getNumComponents(); b1++) {
/* 234 */         int k = colorModel.getComponentSize(b1);
/* 235 */         int m = (1 << k) - 1;
/* 236 */         arrayOfFloat4[b1] = arrayOfFloat4[b1] / m;
/*     */       } 
/*     */     } 
/*     */     
/* 240 */     byte b = 4;
/* 241 */     int j = 16 + 4 * b * 2;
/*     */     
/* 243 */     RenderBuffer renderBuffer = paramRenderQueue.getBuffer();
/* 244 */     paramRenderQueue.ensureCapacityAndAlignment(j, 4);
/* 245 */     renderBuffer.putInt(122);
/* 246 */     renderBuffer.putLong(paramSurfaceData.getNativeOps());
/* 247 */     renderBuffer.putInt(bool ? 1 : 0);
/* 248 */     renderBuffer.put(arrayOfFloat3);
/* 249 */     renderBuffer.put(arrayOfFloat4);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void disableRescaleOp(RenderQueue paramRenderQueue) {
/* 254 */     RenderBuffer renderBuffer = paramRenderQueue.getBuffer();
/* 255 */     paramRenderQueue.ensureCapacity(4);
/* 256 */     renderBuffer.putInt(123);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isLookupOpValid(LookupOp paramLookupOp, BufferedImage paramBufferedImage) {
/* 264 */     LookupTable lookupTable = paramLookupOp.getTable();
/* 265 */     int i = lookupTable.getNumComponents();
/* 266 */     ColorModel colorModel = paramBufferedImage.getColorModel();
/*     */     
/* 268 */     if (colorModel instanceof java.awt.image.IndexColorModel) {
/* 269 */       throw new IllegalArgumentException("LookupOp cannot be performed on an indexed image");
/*     */     }
/*     */ 
/*     */     
/* 273 */     if (i != 1 && i != colorModel
/* 274 */       .getNumComponents() && i != colorModel
/* 275 */       .getNumColorComponents())
/*     */     {
/* 277 */       throw new IllegalArgumentException("Number of arrays in the  lookup table (" + i + ") is not compatible with the src image: " + paramBufferedImage);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     int j = colorModel.getColorSpace().getType();
/* 285 */     if (j != 5 && j != 6)
/*     */     {
/*     */ 
/*     */       
/* 289 */       return false;
/*     */     }
/*     */     
/* 292 */     if (i == 2 || i > 4)
/*     */     {
/* 294 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 302 */     if (lookupTable instanceof ByteLookupTable) {
/* 303 */       byte[][] arrayOfByte = ((ByteLookupTable)lookupTable).getTable();
/* 304 */       for (byte b = 1; b < arrayOfByte.length; b++) {
/* 305 */         if ((arrayOfByte[b]).length > 256 || (arrayOfByte[b]).length != (arrayOfByte[b - 1]).length)
/*     */         {
/*     */           
/* 308 */           return false;
/*     */         }
/*     */       } 
/* 311 */     } else if (lookupTable instanceof ShortLookupTable) {
/* 312 */       short[][] arrayOfShort = ((ShortLookupTable)lookupTable).getTable();
/* 313 */       for (byte b = 1; b < arrayOfShort.length; b++) {
/* 314 */         if ((arrayOfShort[b]).length > 256 || (arrayOfShort[b]).length != (arrayOfShort[b - 1]).length)
/*     */         {
/*     */           
/* 317 */           return false;
/*     */         }
/*     */       } 
/*     */     } else {
/* 321 */       return false;
/*     */     } 
/*     */     
/* 324 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void enableLookupOp(RenderQueue paramRenderQueue, SurfaceData paramSurfaceData, BufferedImage paramBufferedImage, LookupOp paramLookupOp) {
/*     */     int k;
/*     */     byte b;
/* 335 */     boolean bool2, bool1 = (paramBufferedImage.getColorModel().hasAlpha() && paramBufferedImage.isAlphaPremultiplied()) ? true : false;
/*     */     
/* 337 */     LookupTable lookupTable = paramLookupOp.getTable();
/* 338 */     int i = lookupTable.getNumComponents();
/* 339 */     int j = lookupTable.getOffset();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 344 */     if (lookupTable instanceof ShortLookupTable) {
/* 345 */       short[][] arrayOfShort = ((ShortLookupTable)lookupTable).getTable();
/* 346 */       k = (arrayOfShort[0]).length;
/* 347 */       b = 2;
/* 348 */       bool2 = true;
/*     */     } else {
/* 350 */       byte[][] arrayOfByte = ((ByteLookupTable)lookupTable).getTable();
/* 351 */       k = (arrayOfByte[0]).length;
/* 352 */       b = 1;
/* 353 */       bool2 = false;
/*     */     } 
/*     */ 
/*     */     
/* 357 */     int m = i * k * b;
/* 358 */     int n = m + 3 & 0xFFFFFFFC;
/* 359 */     int i1 = n - m;
/* 360 */     int i2 = 32 + n;
/*     */     
/* 362 */     RenderBuffer renderBuffer = paramRenderQueue.getBuffer();
/* 363 */     paramRenderQueue.ensureCapacityAndAlignment(i2, 4);
/* 364 */     renderBuffer.putInt(124);
/* 365 */     renderBuffer.putLong(paramSurfaceData.getNativeOps());
/* 366 */     renderBuffer.putInt(bool1 ? 1 : 0);
/* 367 */     renderBuffer.putInt(bool2 ? 1 : 0);
/* 368 */     renderBuffer.putInt(i);
/* 369 */     renderBuffer.putInt(k);
/* 370 */     renderBuffer.putInt(j);
/* 371 */     if (bool2) {
/* 372 */       short[][] arrayOfShort = ((ShortLookupTable)lookupTable).getTable();
/* 373 */       for (byte b1 = 0; b1 < i; b1++) {
/* 374 */         renderBuffer.put(arrayOfShort[b1]);
/*     */       }
/*     */     } else {
/* 377 */       byte[][] arrayOfByte = ((ByteLookupTable)lookupTable).getTable();
/* 378 */       for (byte b1 = 0; b1 < i; b1++) {
/* 379 */         renderBuffer.put(arrayOfByte[b1]);
/*     */       }
/*     */     } 
/* 382 */     if (i1 != 0) {
/* 383 */       renderBuffer.position((renderBuffer.position() + i1));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void disableLookupOp(RenderQueue paramRenderQueue) {
/* 389 */     RenderBuffer renderBuffer = paramRenderQueue.getBuffer();
/* 390 */     paramRenderQueue.ensureCapacity(4);
/* 391 */     renderBuffer.putInt(125);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/BufferedBufImgOps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */