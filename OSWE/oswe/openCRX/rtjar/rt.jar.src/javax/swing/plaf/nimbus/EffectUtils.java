/*     */ package javax.swing.plaf.nimbus;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.WritableRaster;
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
/*     */ class EffectUtils
/*     */ {
/*     */   static void clearImage(BufferedImage paramBufferedImage) {
/*  50 */     Graphics2D graphics2D = paramBufferedImage.createGraphics();
/*  51 */     graphics2D.setComposite(AlphaComposite.Clear);
/*  52 */     graphics2D.fillRect(0, 0, paramBufferedImage.getWidth(), paramBufferedImage.getHeight());
/*  53 */     graphics2D.dispose();
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
/*     */   static BufferedImage gaussianBlur(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2, int paramInt) {
/*  68 */     int i = paramBufferedImage1.getWidth();
/*  69 */     int j = paramBufferedImage1.getHeight();
/*  70 */     if (paramBufferedImage2 == null || paramBufferedImage2.getWidth() != i || paramBufferedImage2.getHeight() != j || paramBufferedImage1.getType() != paramBufferedImage2.getType()) {
/*  71 */       paramBufferedImage2 = createColorModelCompatibleImage(paramBufferedImage1);
/*     */     }
/*  73 */     float[] arrayOfFloat = createGaussianKernel(paramInt);
/*  74 */     if (paramBufferedImage1.getType() == 2) {
/*  75 */       int[] arrayOfInt1 = new int[i * j];
/*  76 */       int[] arrayOfInt2 = new int[i * j];
/*  77 */       getPixels(paramBufferedImage1, 0, 0, i, j, arrayOfInt1);
/*     */       
/*  79 */       blur(arrayOfInt1, arrayOfInt2, i, j, arrayOfFloat, paramInt);
/*     */ 
/*     */       
/*  82 */       blur(arrayOfInt2, arrayOfInt1, j, i, arrayOfFloat, paramInt);
/*     */       
/*  84 */       setPixels(paramBufferedImage2, 0, 0, i, j, arrayOfInt1);
/*  85 */     } else if (paramBufferedImage1.getType() == 10) {
/*  86 */       byte[] arrayOfByte1 = new byte[i * j];
/*  87 */       byte[] arrayOfByte2 = new byte[i * j];
/*  88 */       getPixels(paramBufferedImage1, 0, 0, i, j, arrayOfByte1);
/*     */       
/*  90 */       blur(arrayOfByte1, arrayOfByte2, i, j, arrayOfFloat, paramInt);
/*     */ 
/*     */       
/*  93 */       blur(arrayOfByte2, arrayOfByte1, j, i, arrayOfFloat, paramInt);
/*     */       
/*  95 */       setPixels(paramBufferedImage2, 0, 0, i, j, arrayOfByte1);
/*     */     } else {
/*  97 */       throw new IllegalArgumentException("EffectUtils.gaussianBlur() src image is not a supported type, type=[" + paramBufferedImage1
/*  98 */           .getType() + "]");
/*     */     } 
/* 100 */     return paramBufferedImage2;
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
/*     */   private static void blur(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/* 129 */     for (byte b = 0; b < paramInt2; b++) {
/* 130 */       int i = b;
/* 131 */       int j = b * paramInt1;
/*     */       
/* 133 */       for (byte b1 = 0; b1 < paramInt1; b1++) {
/* 134 */         float f4 = 0.0F, f3 = f4, f2 = f3, f1 = f2;
/*     */         
/* 136 */         for (int i2 = -paramInt3; i2 <= paramInt3; i2++) {
/* 137 */           int i3 = b1 + i2;
/* 138 */           if (i3 < 0 || i3 >= paramInt1) {
/* 139 */             i3 = (b1 + paramInt1) % paramInt1;
/*     */           }
/*     */           
/* 142 */           int i4 = paramArrayOfint1[j + i3];
/* 143 */           float f = paramArrayOffloat[paramInt3 + i2];
/*     */           
/* 145 */           f1 += f * (i4 >> 24 & 0xFF);
/* 146 */           f2 += f * (i4 >> 16 & 0xFF);
/* 147 */           f3 += f * (i4 >> 8 & 0xFF);
/* 148 */           f4 += f * (i4 & 0xFF);
/*     */         } 
/*     */         
/* 151 */         int k = (int)(f1 + 0.5F);
/* 152 */         int m = (int)(f2 + 0.5F);
/* 153 */         int n = (int)(f3 + 0.5F);
/* 154 */         int i1 = (int)(f4 + 0.5F);
/*     */         
/* 156 */         paramArrayOfint2[i] = ((k > 255) ? 255 : k) << 24 | ((m > 255) ? 255 : m) << 16 | ((n > 255) ? 255 : n) << 8 | ((i1 > 255) ? 255 : i1);
/*     */ 
/*     */ 
/*     */         
/* 160 */         i += paramInt2;
/*     */       } 
/*     */     } 
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
/*     */   static void blur(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/* 183 */     for (byte b = 0; b < paramInt2; b++) {
/* 184 */       int i = b;
/* 185 */       int j = b * paramInt1;
/* 186 */       for (byte b1 = 0; b1 < paramInt1; b1++) {
/* 187 */         float f = 0.0F;
/* 188 */         for (int m = -paramInt3; m <= paramInt3; m++) {
/* 189 */           int n = b1 + m;
/*     */ 
/*     */           
/* 192 */           if (n < 0 || n >= paramInt1) {
/* 193 */             n = (b1 + paramInt1) % paramInt1;
/*     */           }
/* 195 */           int i1 = paramArrayOfbyte1[j + n] & 0xFF;
/* 196 */           float f1 = paramArrayOffloat[paramInt3 + m];
/* 197 */           f += f1 * i1;
/*     */         } 
/* 199 */         int k = (int)(f + 0.5F);
/* 200 */         paramArrayOfbyte2[i] = (byte)((k > 255) ? 255 : k);
/* 201 */         i += paramInt2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static float[] createGaussianKernel(int paramInt) {
/* 207 */     if (paramInt < 1) {
/* 208 */       throw new IllegalArgumentException("Radius must be >= 1");
/*     */     }
/*     */     
/* 211 */     float[] arrayOfFloat = new float[paramInt * 2 + 1];
/*     */     
/* 213 */     float f1 = paramInt / 3.0F;
/* 214 */     float f2 = 2.0F * f1 * f1;
/* 215 */     float f3 = (float)Math.sqrt(f2 * Math.PI);
/* 216 */     float f4 = 0.0F;
/*     */     int i;
/* 218 */     for (i = -paramInt; i <= paramInt; i++) {
/* 219 */       float f = (i * i);
/* 220 */       int j = i + paramInt;
/* 221 */       arrayOfFloat[j] = (float)Math.exp((-f / f2)) / f3;
/* 222 */       f4 += arrayOfFloat[j];
/*     */     } 
/*     */     
/* 225 */     for (i = 0; i < arrayOfFloat.length; i++) {
/* 226 */       arrayOfFloat[i] = arrayOfFloat[i] / f4;
/*     */     }
/*     */     
/* 229 */     return arrayOfFloat;
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
/*     */   static byte[] getPixels(BufferedImage paramBufferedImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfbyte) {
/* 252 */     if (paramInt3 == 0 || paramInt4 == 0) {
/* 253 */       return new byte[0];
/*     */     }
/*     */     
/* 256 */     if (paramArrayOfbyte == null) {
/* 257 */       paramArrayOfbyte = new byte[paramInt3 * paramInt4];
/* 258 */     } else if (paramArrayOfbyte.length < paramInt3 * paramInt4) {
/* 259 */       throw new IllegalArgumentException("pixels array must have a length >= w*h");
/*     */     } 
/*     */     
/* 262 */     int i = paramBufferedImage.getType();
/* 263 */     if (i == 10) {
/* 264 */       WritableRaster writableRaster = paramBufferedImage.getRaster();
/* 265 */       return (byte[])writableRaster.getDataElements(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfbyte);
/*     */     } 
/* 267 */     throw new IllegalArgumentException("Only type BYTE_GRAY is supported");
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
/*     */   static void setPixels(BufferedImage paramBufferedImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfbyte) {
/* 286 */     if (paramArrayOfbyte == null || paramInt3 == 0 || paramInt4 == 0)
/*     */       return; 
/* 288 */     if (paramArrayOfbyte.length < paramInt3 * paramInt4) {
/* 289 */       throw new IllegalArgumentException("pixels array must have a length >= w*h");
/*     */     }
/* 291 */     int i = paramBufferedImage.getType();
/* 292 */     if (i == 10) {
/* 293 */       WritableRaster writableRaster = paramBufferedImage.getRaster();
/* 294 */       writableRaster.setDataElements(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfbyte);
/*     */     } else {
/* 296 */       throw new IllegalArgumentException("Only type BYTE_GRAY is supported");
/*     */     } 
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
/*     */   public static int[] getPixels(BufferedImage paramBufferedImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
/* 320 */     if (paramInt3 == 0 || paramInt4 == 0) {
/* 321 */       return new int[0];
/*     */     }
/*     */     
/* 324 */     if (paramArrayOfint == null) {
/* 325 */       paramArrayOfint = new int[paramInt3 * paramInt4];
/* 326 */     } else if (paramArrayOfint.length < paramInt3 * paramInt4) {
/* 327 */       throw new IllegalArgumentException("pixels array must have a length >= w*h");
/*     */     } 
/*     */ 
/*     */     
/* 331 */     int i = paramBufferedImage.getType();
/* 332 */     if (i == 2 || i == 1) {
/*     */       
/* 334 */       WritableRaster writableRaster = paramBufferedImage.getRaster();
/* 335 */       return (int[])writableRaster.getDataElements(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint);
/*     */     } 
/*     */ 
/*     */     
/* 339 */     return paramBufferedImage.getRGB(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint, 0, paramInt3);
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
/*     */   public static void setPixels(BufferedImage paramBufferedImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
/* 359 */     if (paramArrayOfint == null || paramInt3 == 0 || paramInt4 == 0)
/*     */       return; 
/* 361 */     if (paramArrayOfint.length < paramInt3 * paramInt4) {
/* 362 */       throw new IllegalArgumentException("pixels array must have a length >= w*h");
/*     */     }
/*     */ 
/*     */     
/* 366 */     int i = paramBufferedImage.getType();
/* 367 */     if (i == 2 || i == 1) {
/*     */       
/* 369 */       WritableRaster writableRaster = paramBufferedImage.getRaster();
/* 370 */       writableRaster.setDataElements(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint);
/*     */     } else {
/*     */       
/* 373 */       paramBufferedImage.setRGB(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint, 0, paramInt3);
/*     */     } 
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
/*     */   public static BufferedImage createColorModelCompatibleImage(BufferedImage paramBufferedImage) {
/* 389 */     ColorModel colorModel = paramBufferedImage.getColorModel();
/* 390 */     return new BufferedImage(colorModel, colorModel
/* 391 */         .createCompatibleWritableRaster(paramBufferedImage.getWidth(), paramBufferedImage
/* 392 */           .getHeight()), colorModel
/* 393 */         .isAlphaPremultiplied(), null);
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
/*     */   public static BufferedImage createCompatibleTranslucentImage(int paramInt1, int paramInt2) {
/* 410 */     return isHeadless() ? new BufferedImage(paramInt1, paramInt2, 2) : 
/*     */       
/* 412 */       getGraphicsConfiguration().createCompatibleImage(paramInt1, paramInt2, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isHeadless() {
/* 417 */     return GraphicsEnvironment.isHeadless();
/*     */   }
/*     */ 
/*     */   
/*     */   private static GraphicsConfiguration getGraphicsConfiguration() {
/* 422 */     return GraphicsEnvironment.getLocalGraphicsEnvironment()
/* 423 */       .getDefaultScreenDevice().getDefaultConfiguration();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/EffectUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */