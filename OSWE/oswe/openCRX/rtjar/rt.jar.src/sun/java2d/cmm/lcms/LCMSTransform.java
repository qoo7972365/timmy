/*     */ package sun.java2d.cmm.lcms;
/*     */ 
/*     */ import java.awt.color.CMMException;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.color.ICC_Profile;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.SampleModel;
/*     */ import java.awt.image.WritableRaster;
/*     */ import sun.java2d.cmm.ColorTransform;
/*     */ import sun.java2d.cmm.ProfileDeferralMgr;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LCMSTransform
/*     */   implements ColorTransform
/*     */ {
/*     */   long ID;
/*  59 */   private int inFormatter = 0;
/*     */   private boolean isInIntPacked = false;
/*  61 */   private int outFormatter = 0;
/*     */   
/*     */   private boolean isOutIntPacked = false;
/*     */   
/*     */   ICC_Profile[] profiles;
/*     */   LCMSProfile[] lcmsProfiles;
/*     */   int renderType;
/*     */   int transformType;
/*  69 */   private int numInComponents = -1;
/*  70 */   private int numOutComponents = -1;
/*     */   
/*  72 */   private Object disposerReferent = new Object();
/*     */ 
/*     */   
/*     */   static {
/*  76 */     if (ProfileDeferralMgr.deferring) {
/*  77 */       ProfileDeferralMgr.activateProfiles();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LCMSTransform(ICC_Profile paramICC_Profile, int paramInt1, int paramInt2) {
/*  85 */     this.profiles = new ICC_Profile[1];
/*  86 */     this.profiles[0] = paramICC_Profile;
/*  87 */     this.lcmsProfiles = new LCMSProfile[1];
/*  88 */     this.lcmsProfiles[0] = LCMS.getProfileID(paramICC_Profile);
/*  89 */     this.renderType = (paramInt1 == -1) ? 0 : paramInt1;
/*     */     
/*  91 */     this.transformType = paramInt2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     this.numInComponents = this.profiles[0].getNumComponents();
/*  99 */     this.numOutComponents = this.profiles[this.profiles.length - 1].getNumComponents();
/*     */   }
/*     */   
/*     */   public LCMSTransform(ColorTransform[] paramArrayOfColorTransform) {
/* 103 */     int i = 0; int j;
/* 104 */     for (j = 0; j < paramArrayOfColorTransform.length; j++) {
/* 105 */       i += ((LCMSTransform)paramArrayOfColorTransform[j]).profiles.length;
/*     */     }
/* 107 */     this.profiles = new ICC_Profile[i];
/* 108 */     this.lcmsProfiles = new LCMSProfile[i];
/* 109 */     j = 0;
/* 110 */     for (byte b = 0; b < paramArrayOfColorTransform.length; b++) {
/* 111 */       LCMSTransform lCMSTransform = (LCMSTransform)paramArrayOfColorTransform[b];
/* 112 */       System.arraycopy(lCMSTransform.profiles, 0, this.profiles, j, lCMSTransform.profiles.length);
/*     */       
/* 114 */       System.arraycopy(lCMSTransform.lcmsProfiles, 0, this.lcmsProfiles, j, lCMSTransform.lcmsProfiles.length);
/*     */       
/* 116 */       j += lCMSTransform.profiles.length;
/*     */     } 
/* 118 */     this.renderType = ((LCMSTransform)paramArrayOfColorTransform[0]).renderType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     this.numInComponents = this.profiles[0].getNumComponents();
/* 126 */     this.numOutComponents = this.profiles[this.profiles.length - 1].getNumComponents();
/*     */   }
/*     */   
/*     */   public int getNumInComponents() {
/* 130 */     return this.numInComponents;
/*     */   }
/*     */   
/*     */   public int getNumOutComponents() {
/* 134 */     return this.numOutComponents;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void doTransform(LCMSImageLayout paramLCMSImageLayout1, LCMSImageLayout paramLCMSImageLayout2) {
/* 140 */     if (this.ID == 0L || this.inFormatter != paramLCMSImageLayout1.pixelType || this.isInIntPacked != paramLCMSImageLayout1.isIntPacked || this.outFormatter != paramLCMSImageLayout2.pixelType || this.isOutIntPacked != paramLCMSImageLayout2.isIntPacked) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 145 */       if (this.ID != 0L)
/*     */       {
/* 147 */         this.disposerReferent = new Object();
/*     */       }
/* 149 */       this.inFormatter = paramLCMSImageLayout1.pixelType;
/* 150 */       this.isInIntPacked = paramLCMSImageLayout1.isIntPacked;
/*     */       
/* 152 */       this.outFormatter = paramLCMSImageLayout2.pixelType;
/* 153 */       this.isOutIntPacked = paramLCMSImageLayout2.isIntPacked;
/*     */       
/* 155 */       this.ID = LCMS.createTransform(this.lcmsProfiles, this.renderType, this.inFormatter, this.isInIntPacked, this.outFormatter, this.isOutIntPacked, this.disposerReferent);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     LCMS.colorConvert(this, paramLCMSImageLayout1, paramLCMSImageLayout2);
/*     */   }
/*     */   
/*     */   public void colorConvert(BufferedImage paramBufferedImage1, BufferedImage paramBufferedImage2) {
/*     */     float[] arrayOfFloat5;
/*     */     try {
/* 167 */       if (!paramBufferedImage2.getColorModel().hasAlpha()) {
/* 168 */         LCMSImageLayout lCMSImageLayout = LCMSImageLayout.createImageLayout(paramBufferedImage2);
/*     */         
/* 170 */         if (lCMSImageLayout != null) {
/* 171 */           LCMSImageLayout lCMSImageLayout1 = LCMSImageLayout.createImageLayout(paramBufferedImage1);
/* 172 */           if (lCMSImageLayout1 != null) {
/* 173 */             doTransform(lCMSImageLayout1, lCMSImageLayout);
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/* 178 */     } catch (ImageLayoutException imageLayoutException) {
/* 179 */       throw new CMMException("Unable to convert images");
/*     */     } 
/*     */     
/* 182 */     WritableRaster writableRaster1 = paramBufferedImage1.getRaster();
/* 183 */     WritableRaster writableRaster2 = paramBufferedImage2.getRaster();
/* 184 */     ColorModel colorModel1 = paramBufferedImage1.getColorModel();
/* 185 */     ColorModel colorModel2 = paramBufferedImage2.getColorModel();
/* 186 */     int i = paramBufferedImage1.getWidth();
/* 187 */     int j = paramBufferedImage1.getHeight();
/* 188 */     int k = colorModel1.getNumColorComponents();
/* 189 */     int m = colorModel2.getNumColorComponents();
/* 190 */     byte b1 = 8;
/* 191 */     float f = 255.0F; byte b2;
/* 192 */     for (b2 = 0; b2 < k; b2++) {
/* 193 */       if (colorModel1.getComponentSize(b2) > 8) {
/* 194 */         b1 = 16;
/* 195 */         f = 65535.0F;
/*     */       } 
/*     */     } 
/* 198 */     for (b2 = 0; b2 < m; b2++) {
/* 199 */       if (colorModel2.getComponentSize(b2) > 8) {
/* 200 */         b1 = 16;
/* 201 */         f = 65535.0F;
/*     */       } 
/*     */     } 
/* 204 */     float[] arrayOfFloat1 = new float[k];
/* 205 */     float[] arrayOfFloat2 = new float[k];
/* 206 */     ColorSpace colorSpace = colorModel1.getColorSpace();
/* 207 */     for (byte b3 = 0; b3 < k; b3++) {
/* 208 */       arrayOfFloat1[b3] = colorSpace.getMinValue(b3);
/* 209 */       arrayOfFloat2[b3] = f / (colorSpace.getMaxValue(b3) - arrayOfFloat1[b3]);
/*     */     } 
/* 211 */     colorSpace = colorModel2.getColorSpace();
/* 212 */     float[] arrayOfFloat3 = new float[m];
/* 213 */     float[] arrayOfFloat4 = new float[m];
/* 214 */     for (byte b4 = 0; b4 < m; b4++) {
/* 215 */       arrayOfFloat3[b4] = colorSpace.getMinValue(b4);
/* 216 */       arrayOfFloat4[b4] = (colorSpace.getMaxValue(b4) - arrayOfFloat3[b4]) / f;
/*     */     } 
/* 218 */     boolean bool = colorModel2.hasAlpha();
/* 219 */     boolean bool1 = (colorModel1.hasAlpha() && bool) ? true : false;
/*     */     
/* 221 */     if (bool) {
/* 222 */       arrayOfFloat5 = new float[m + 1];
/*     */     } else {
/* 224 */       arrayOfFloat5 = new float[m];
/*     */     } 
/* 226 */     if (b1 == 8) {
/* 227 */       LCMSImageLayout lCMSImageLayout1, lCMSImageLayout2; byte[] arrayOfByte1 = new byte[i * k];
/* 228 */       byte[] arrayOfByte2 = new byte[i * m];
/*     */ 
/*     */       
/* 231 */       float[] arrayOfFloat = null;
/* 232 */       if (bool1) {
/* 233 */         arrayOfFloat = new float[i];
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 241 */         lCMSImageLayout1 = new LCMSImageLayout(arrayOfByte1, arrayOfByte1.length / getNumInComponents(), LCMSImageLayout.CHANNELS_SH(getNumInComponents()) | LCMSImageLayout.BYTES_SH(1), getNumInComponents());
/*     */ 
/*     */ 
/*     */         
/* 245 */         lCMSImageLayout2 = new LCMSImageLayout(arrayOfByte2, arrayOfByte2.length / getNumOutComponents(), LCMSImageLayout.CHANNELS_SH(getNumOutComponents()) | LCMSImageLayout.BYTES_SH(1), getNumOutComponents());
/* 246 */       } catch (ImageLayoutException imageLayoutException) {
/* 247 */         throw new CMMException("Unable to convert images");
/*     */       } 
/*     */       
/* 250 */       for (byte b = 0; b < j; b++) {
/*     */         
/* 252 */         Object object = null;
/* 253 */         float[] arrayOfFloat6 = null;
/* 254 */         byte b5 = 0; byte b6;
/* 255 */         for (b6 = 0; b6 < i; b6++) {
/* 256 */           object = writableRaster1.getDataElements(b6, b, object);
/* 257 */           arrayOfFloat6 = colorModel1.getNormalizedComponents(object, arrayOfFloat6, 0);
/* 258 */           for (byte b7 = 0; b7 < k; b7++) {
/* 259 */             arrayOfByte1[b5++] = (byte)(int)((arrayOfFloat6[b7] - arrayOfFloat1[b7]) * arrayOfFloat2[b7] + 0.5F);
/*     */           }
/*     */ 
/*     */           
/* 263 */           if (bool1) {
/* 264 */             arrayOfFloat[b6] = arrayOfFloat6[k];
/*     */           }
/*     */         } 
/*     */         
/* 268 */         doTransform(lCMSImageLayout1, lCMSImageLayout2);
/*     */ 
/*     */         
/* 271 */         object = null;
/* 272 */         b5 = 0;
/* 273 */         for (b6 = 0; b6 < i; b6++) {
/* 274 */           for (byte b7 = 0; b7 < m; b7++) {
/* 275 */             arrayOfFloat5[b7] = (arrayOfByte2[b5++] & 0xFF) * arrayOfFloat4[b7] + arrayOfFloat3[b7];
/*     */           }
/*     */           
/* 278 */           if (bool1) {
/* 279 */             arrayOfFloat5[m] = arrayOfFloat[b6];
/* 280 */           } else if (bool) {
/* 281 */             arrayOfFloat5[m] = 1.0F;
/*     */           } 
/* 283 */           object = colorModel2.getDataElements(arrayOfFloat5, 0, object);
/* 284 */           writableRaster2.setDataElements(b6, b, object);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 288 */       LCMSImageLayout lCMSImageLayout1, lCMSImageLayout2; short[] arrayOfShort1 = new short[i * k];
/* 289 */       short[] arrayOfShort2 = new short[i * m];
/*     */ 
/*     */       
/* 292 */       float[] arrayOfFloat = null;
/* 293 */       if (bool1) {
/* 294 */         arrayOfFloat = new float[i];
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 301 */         lCMSImageLayout1 = new LCMSImageLayout(arrayOfShort1, arrayOfShort1.length / getNumInComponents(), LCMSImageLayout.CHANNELS_SH(getNumInComponents()) | LCMSImageLayout.BYTES_SH(2), getNumInComponents() * 2);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 306 */         lCMSImageLayout2 = new LCMSImageLayout(arrayOfShort2, arrayOfShort2.length / getNumOutComponents(), LCMSImageLayout.CHANNELS_SH(getNumOutComponents()) | LCMSImageLayout.BYTES_SH(2), getNumOutComponents() * 2);
/* 307 */       } catch (ImageLayoutException imageLayoutException) {
/* 308 */         throw new CMMException("Unable to convert images");
/*     */       } 
/*     */       
/* 311 */       for (byte b = 0; b < j; b++) {
/*     */         
/* 313 */         Object object = null;
/* 314 */         float[] arrayOfFloat6 = null;
/* 315 */         byte b5 = 0; byte b6;
/* 316 */         for (b6 = 0; b6 < i; b6++) {
/* 317 */           object = writableRaster1.getDataElements(b6, b, object);
/* 318 */           arrayOfFloat6 = colorModel1.getNormalizedComponents(object, arrayOfFloat6, 0);
/* 319 */           for (byte b7 = 0; b7 < k; b7++) {
/* 320 */             arrayOfShort1[b5++] = (short)(int)((arrayOfFloat6[b7] - arrayOfFloat1[b7]) * arrayOfFloat2[b7] + 0.5F);
/*     */           }
/*     */ 
/*     */           
/* 324 */           if (bool1) {
/* 325 */             arrayOfFloat[b6] = arrayOfFloat6[k];
/*     */           }
/*     */         } 
/*     */         
/* 329 */         doTransform(lCMSImageLayout1, lCMSImageLayout2);
/*     */ 
/*     */         
/* 332 */         object = null;
/* 333 */         b5 = 0;
/* 334 */         for (b6 = 0; b6 < i; b6++) {
/* 335 */           for (byte b7 = 0; b7 < m; b7++) {
/* 336 */             arrayOfFloat5[b7] = (arrayOfShort2[b5++] & 0xFFFF) * arrayOfFloat4[b7] + arrayOfFloat3[b7];
/*     */           }
/*     */           
/* 339 */           if (bool1) {
/* 340 */             arrayOfFloat5[m] = arrayOfFloat[b6];
/* 341 */           } else if (bool) {
/* 342 */             arrayOfFloat5[m] = 1.0F;
/*     */           } 
/* 344 */           object = colorModel2.getDataElements(arrayOfFloat5, 0, object);
/* 345 */           writableRaster2.setDataElements(b6, b, object);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void colorConvert(Raster paramRaster, WritableRaster paramWritableRaster, float[] paramArrayOffloat1, float[] paramArrayOffloat2, float[] paramArrayOffloat3, float[] paramArrayOffloat4) {
/*     */     LCMSImageLayout lCMSImageLayout1, lCMSImageLayout2;
/*     */     boolean bool1, bool2;
/* 357 */     SampleModel sampleModel1 = paramRaster.getSampleModel();
/* 358 */     SampleModel sampleModel2 = paramWritableRaster.getSampleModel();
/* 359 */     int i = paramRaster.getTransferType();
/* 360 */     int j = paramWritableRaster.getTransferType();
/*     */     
/* 362 */     if (i == 4 || i == 5) {
/*     */       
/* 364 */       bool1 = true;
/*     */     } else {
/* 366 */       bool1 = false;
/*     */     } 
/* 368 */     if (j == 4 || j == 5) {
/*     */       
/* 370 */       bool2 = true;
/*     */     } else {
/* 372 */       bool2 = false;
/*     */     } 
/* 374 */     int k = paramRaster.getWidth();
/* 375 */     int m = paramRaster.getHeight();
/* 376 */     int n = paramRaster.getNumBands();
/* 377 */     int i1 = paramWritableRaster.getNumBands();
/* 378 */     float[] arrayOfFloat1 = new float[n];
/* 379 */     float[] arrayOfFloat2 = new float[i1];
/* 380 */     float[] arrayOfFloat3 = new float[n];
/* 381 */     float[] arrayOfFloat4 = new float[i1]; int i2;
/* 382 */     for (i2 = 0; i2 < n; i2++) {
/* 383 */       if (bool1) {
/* 384 */         arrayOfFloat1[i2] = 65535.0F / (paramArrayOffloat2[i2] - paramArrayOffloat1[i2]);
/* 385 */         arrayOfFloat3[i2] = paramArrayOffloat1[i2];
/*     */       } else {
/* 387 */         if (i == 2) {
/* 388 */           arrayOfFloat1[i2] = 2.0000305F;
/*     */         } else {
/* 390 */           arrayOfFloat1[i2] = 65535.0F / ((1 << sampleModel1
/* 391 */             .getSampleSize(i2)) - 1);
/*     */         } 
/* 393 */         arrayOfFloat3[i2] = 0.0F;
/*     */       } 
/*     */     } 
/* 396 */     for (i2 = 0; i2 < i1; i2++) {
/* 397 */       if (bool2) {
/* 398 */         arrayOfFloat2[i2] = (paramArrayOffloat4[i2] - paramArrayOffloat3[i2]) / 65535.0F;
/* 399 */         arrayOfFloat4[i2] = paramArrayOffloat3[i2];
/*     */       } else {
/* 401 */         if (j == 2) {
/* 402 */           arrayOfFloat2[i2] = 0.49999237F;
/*     */         } else {
/* 404 */           arrayOfFloat2[i2] = ((1 << sampleModel2
/* 405 */             .getSampleSize(i2)) - 1) / 65535.0F;
/*     */         } 
/*     */         
/* 408 */         arrayOfFloat4[i2] = 0.0F;
/*     */       } 
/*     */     } 
/* 411 */     i2 = paramRaster.getMinY();
/* 412 */     int i3 = paramWritableRaster.getMinY();
/*     */ 
/*     */     
/* 415 */     short[] arrayOfShort1 = new short[k * n];
/* 416 */     short[] arrayOfShort2 = new short[k * i1];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 422 */       lCMSImageLayout1 = new LCMSImageLayout(arrayOfShort1, arrayOfShort1.length / getNumInComponents(), LCMSImageLayout.CHANNELS_SH(getNumInComponents()) | LCMSImageLayout.BYTES_SH(2), getNumInComponents() * 2);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 427 */       lCMSImageLayout2 = new LCMSImageLayout(arrayOfShort2, arrayOfShort2.length / getNumOutComponents(), LCMSImageLayout.CHANNELS_SH(getNumOutComponents()) | LCMSImageLayout.BYTES_SH(2), getNumOutComponents() * 2);
/* 428 */     } catch (ImageLayoutException imageLayoutException) {
/* 429 */       throw new CMMException("Unable to convert rasters");
/*     */     } 
/*     */     
/* 432 */     for (byte b = 0; b < m; b++, i2++, i3++) {
/*     */       
/* 434 */       int i4 = paramRaster.getMinX();
/* 435 */       byte b1 = 0; byte b2;
/* 436 */       for (b2 = 0; b2 < k; b2++, i4++) {
/* 437 */         for (byte b3 = 0; b3 < n; b3++) {
/* 438 */           float f = paramRaster.getSampleFloat(i4, i2, b3);
/* 439 */           arrayOfShort1[b1++] = (short)(int)((f - arrayOfFloat3[b3]) * arrayOfFloat1[b3] + 0.5F);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 445 */       doTransform(lCMSImageLayout1, lCMSImageLayout2);
/*     */ 
/*     */       
/* 448 */       int i5 = paramWritableRaster.getMinX();
/* 449 */       b1 = 0;
/* 450 */       for (b2 = 0; b2 < k; b2++, i5++) {
/* 451 */         for (byte b3 = 0; b3 < i1; b3++) {
/* 452 */           float f = (arrayOfShort2[b1++] & 0xFFFF) * arrayOfFloat2[b3] + arrayOfFloat4[b3];
/*     */           
/* 454 */           paramWritableRaster.setSample(i5, i3, b3, f);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void colorConvert(Raster paramRaster, WritableRaster paramWritableRaster) {
/* 463 */     LCMSImageLayout lCMSImageLayout = LCMSImageLayout.createImageLayout(paramWritableRaster);
/* 464 */     if (lCMSImageLayout != null) {
/* 465 */       LCMSImageLayout lCMSImageLayout1 = LCMSImageLayout.createImageLayout(paramRaster);
/* 466 */       if (lCMSImageLayout1 != null) {
/* 467 */         doTransform(lCMSImageLayout1, lCMSImageLayout);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 472 */     SampleModel sampleModel1 = paramRaster.getSampleModel();
/* 473 */     SampleModel sampleModel2 = paramWritableRaster.getSampleModel();
/* 474 */     int i = paramRaster.getTransferType();
/* 475 */     int j = paramWritableRaster.getTransferType();
/* 476 */     int k = paramRaster.getWidth();
/* 477 */     int m = paramRaster.getHeight();
/* 478 */     int n = paramRaster.getNumBands();
/* 479 */     int i1 = paramWritableRaster.getNumBands();
/* 480 */     byte b1 = 8;
/* 481 */     float f = 255.0F; byte b2;
/* 482 */     for (b2 = 0; b2 < n; b2++) {
/* 483 */       if (sampleModel1.getSampleSize(b2) > 8) {
/* 484 */         b1 = 16;
/* 485 */         f = 65535.0F;
/*     */       } 
/*     */     } 
/* 488 */     for (b2 = 0; b2 < i1; b2++) {
/* 489 */       if (sampleModel2.getSampleSize(b2) > 8) {
/* 490 */         b1 = 16;
/* 491 */         f = 65535.0F;
/*     */       } 
/*     */     } 
/* 494 */     float[] arrayOfFloat1 = new float[n];
/* 495 */     float[] arrayOfFloat2 = new float[i1]; int i2;
/* 496 */     for (i2 = 0; i2 < n; i2++) {
/* 497 */       if (i == 2) {
/* 498 */         arrayOfFloat1[i2] = f / 32767.0F;
/*     */       } else {
/* 500 */         arrayOfFloat1[i2] = f / ((1 << sampleModel1
/* 501 */           .getSampleSize(i2)) - 1);
/*     */       } 
/*     */     } 
/* 504 */     for (i2 = 0; i2 < i1; i2++) {
/* 505 */       if (j == 2) {
/* 506 */         arrayOfFloat2[i2] = 32767.0F / f;
/*     */       } else {
/* 508 */         arrayOfFloat2[i2] = ((1 << sampleModel2
/* 509 */           .getSampleSize(i2)) - 1) / f;
/*     */       } 
/*     */     } 
/* 512 */     i2 = paramRaster.getMinY();
/* 513 */     int i3 = paramWritableRaster.getMinY();
/*     */ 
/*     */     
/* 516 */     if (b1 == 8) {
/* 517 */       LCMSImageLayout lCMSImageLayout1; byte[] arrayOfByte1 = new byte[k * n];
/* 518 */       byte[] arrayOfByte2 = new byte[k * i1];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 525 */         lCMSImageLayout1 = new LCMSImageLayout(arrayOfByte1, arrayOfByte1.length / getNumInComponents(), LCMSImageLayout.CHANNELS_SH(getNumInComponents()) | LCMSImageLayout.BYTES_SH(1), getNumInComponents());
/*     */ 
/*     */ 
/*     */         
/* 529 */         lCMSImageLayout = new LCMSImageLayout(arrayOfByte2, arrayOfByte2.length / getNumOutComponents(), LCMSImageLayout.CHANNELS_SH(getNumOutComponents()) | LCMSImageLayout.BYTES_SH(1), getNumOutComponents());
/* 530 */       } catch (ImageLayoutException imageLayoutException) {
/* 531 */         throw new CMMException("Unable to convert rasters");
/*     */       } 
/*     */       
/* 534 */       for (byte b = 0; b < m; b++, i2++, i3++) {
/*     */         
/* 536 */         int i4 = paramRaster.getMinX();
/* 537 */         byte b3 = 0; byte b4;
/* 538 */         for (b4 = 0; b4 < k; b4++, i4++) {
/* 539 */           for (byte b5 = 0; b5 < n; b5++) {
/* 540 */             int i6 = paramRaster.getSample(i4, i2, b5);
/* 541 */             arrayOfByte1[b3++] = (byte)(int)(i6 * arrayOfFloat1[b5] + 0.5F);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 547 */         doTransform(lCMSImageLayout1, lCMSImageLayout);
/*     */ 
/*     */         
/* 550 */         int i5 = paramWritableRaster.getMinX();
/* 551 */         b3 = 0;
/* 552 */         for (b4 = 0; b4 < k; b4++, i5++) {
/* 553 */           for (byte b5 = 0; b5 < i1; b5++) {
/* 554 */             int i6 = (int)((arrayOfByte2[b3++] & 0xFF) * arrayOfFloat2[b5] + 0.5F);
/*     */             
/* 556 */             paramWritableRaster.setSample(i5, i3, b5, i6);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 561 */       LCMSImageLayout lCMSImageLayout1; short[] arrayOfShort1 = new short[k * n];
/* 562 */       short[] arrayOfShort2 = new short[k * i1];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 569 */         lCMSImageLayout1 = new LCMSImageLayout(arrayOfShort1, arrayOfShort1.length / getNumInComponents(), LCMSImageLayout.CHANNELS_SH(getNumInComponents()) | LCMSImageLayout.BYTES_SH(2), getNumInComponents() * 2);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 574 */         lCMSImageLayout = new LCMSImageLayout(arrayOfShort2, arrayOfShort2.length / getNumOutComponents(), LCMSImageLayout.CHANNELS_SH(getNumOutComponents()) | LCMSImageLayout.BYTES_SH(2), getNumOutComponents() * 2);
/* 575 */       } catch (ImageLayoutException imageLayoutException) {
/* 576 */         throw new CMMException("Unable to convert rasters");
/*     */       } 
/*     */       
/* 579 */       for (byte b = 0; b < m; b++, i2++, i3++) {
/*     */         
/* 581 */         int i4 = paramRaster.getMinX();
/* 582 */         byte b3 = 0; byte b4;
/* 583 */         for (b4 = 0; b4 < k; b4++, i4++) {
/* 584 */           for (byte b5 = 0; b5 < n; b5++) {
/* 585 */             int i6 = paramRaster.getSample(i4, i2, b5);
/* 586 */             arrayOfShort1[b3++] = (short)(int)(i6 * arrayOfFloat1[b5] + 0.5F);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 592 */         doTransform(lCMSImageLayout1, lCMSImageLayout);
/*     */ 
/*     */         
/* 595 */         int i5 = paramWritableRaster.getMinX();
/* 596 */         b3 = 0;
/* 597 */         for (b4 = 0; b4 < k; b4++, i5++) {
/* 598 */           for (byte b5 = 0; b5 < i1; b5++) {
/* 599 */             int i6 = (int)((arrayOfShort2[b3++] & 0xFFFF) * arrayOfFloat2[b5] + 0.5F);
/*     */             
/* 601 */             paramWritableRaster.setSample(i5, i3, b5, i6);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short[] colorConvert(short[] paramArrayOfshort1, short[] paramArrayOfshort2) {
/* 614 */     if (paramArrayOfshort2 == null) {
/* 615 */       paramArrayOfshort2 = new short[paramArrayOfshort1.length / getNumInComponents() * getNumOutComponents()];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 622 */       LCMSImageLayout lCMSImageLayout1 = new LCMSImageLayout(paramArrayOfshort1, paramArrayOfshort1.length / getNumInComponents(), LCMSImageLayout.CHANNELS_SH(getNumInComponents()) | LCMSImageLayout.BYTES_SH(2), getNumInComponents() * 2);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 627 */       LCMSImageLayout lCMSImageLayout2 = new LCMSImageLayout(paramArrayOfshort2, paramArrayOfshort2.length / getNumOutComponents(), LCMSImageLayout.CHANNELS_SH(getNumOutComponents()) | LCMSImageLayout.BYTES_SH(2), getNumOutComponents() * 2);
/*     */       
/* 629 */       doTransform(lCMSImageLayout1, lCMSImageLayout2);
/*     */       
/* 631 */       return paramArrayOfshort2;
/* 632 */     } catch (ImageLayoutException imageLayoutException) {
/* 633 */       throw new CMMException("Unable to convert data");
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] colorConvert(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 638 */     if (paramArrayOfbyte2 == null) {
/* 639 */       paramArrayOfbyte2 = new byte[paramArrayOfbyte1.length / getNumInComponents() * getNumOutComponents()];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 646 */       LCMSImageLayout lCMSImageLayout1 = new LCMSImageLayout(paramArrayOfbyte1, paramArrayOfbyte1.length / getNumInComponents(), LCMSImageLayout.CHANNELS_SH(getNumInComponents()) | LCMSImageLayout.BYTES_SH(1), getNumInComponents());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 651 */       LCMSImageLayout lCMSImageLayout2 = new LCMSImageLayout(paramArrayOfbyte2, paramArrayOfbyte2.length / getNumOutComponents(), LCMSImageLayout.CHANNELS_SH(getNumOutComponents()) | LCMSImageLayout.BYTES_SH(1), getNumOutComponents());
/*     */       
/* 653 */       doTransform(lCMSImageLayout1, lCMSImageLayout2);
/*     */       
/* 655 */       return paramArrayOfbyte2;
/* 656 */     } catch (ImageLayoutException imageLayoutException) {
/* 657 */       throw new CMMException("Unable to convert data");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/cmm/lcms/LCMSTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */