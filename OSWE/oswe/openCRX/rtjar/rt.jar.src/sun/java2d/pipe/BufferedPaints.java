/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.LinearGradientPaint;
/*     */ import java.awt.MultipleGradientPaint;
/*     */ import java.awt.Paint;
/*     */ import java.awt.RadialGradientPaint;
/*     */ import java.awt.TexturePaint;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.NoninvertibleTransformException;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import sun.awt.image.PixelConverter;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BufferedPaints
/*     */ {
/*     */   public static final int MULTI_MAX_FRACTIONS = 12;
/*     */   
/*     */   static void setPaint(RenderQueue paramRenderQueue, SunGraphics2D paramSunGraphics2D, Paint paramPaint, int paramInt) {
/*  56 */     if (paramSunGraphics2D.paintState <= 1) {
/*  57 */       setColor(paramRenderQueue, paramSunGraphics2D.pixel);
/*     */     } else {
/*  59 */       boolean bool = ((paramInt & 0x2) != 0) ? true : false;
/*  60 */       switch (paramSunGraphics2D.paintState) {
/*     */         case 2:
/*  62 */           setGradientPaint(paramRenderQueue, paramSunGraphics2D, (GradientPaint)paramPaint, bool);
/*     */           break;
/*     */         
/*     */         case 3:
/*  66 */           setLinearGradientPaint(paramRenderQueue, paramSunGraphics2D, (LinearGradientPaint)paramPaint, bool);
/*     */           break;
/*     */         
/*     */         case 4:
/*  70 */           setRadialGradientPaint(paramRenderQueue, paramSunGraphics2D, (RadialGradientPaint)paramPaint, bool);
/*     */           break;
/*     */         
/*     */         case 5:
/*  74 */           setTexturePaint(paramRenderQueue, paramSunGraphics2D, (TexturePaint)paramPaint, bool);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void resetPaint(RenderQueue paramRenderQueue) {
/*  85 */     paramRenderQueue.ensureCapacity(4);
/*  86 */     RenderBuffer renderBuffer = paramRenderQueue.getBuffer();
/*  87 */     renderBuffer.putInt(100);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setColor(RenderQueue paramRenderQueue, int paramInt) {
/*  94 */     paramRenderQueue.ensureCapacity(8);
/*  95 */     RenderBuffer renderBuffer = paramRenderQueue.getBuffer();
/*  96 */     renderBuffer.putInt(101);
/*  97 */     renderBuffer.putInt(paramInt);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setGradientPaint(RenderQueue paramRenderQueue, AffineTransform paramAffineTransform, Color paramColor1, Color paramColor2, Point2D paramPoint2D1, Point2D paramPoint2D2, boolean paramBoolean1, boolean paramBoolean2) {
/*     */     double d4, d5, d6;
/* 164 */     PixelConverter pixelConverter = PixelConverter.ArgbPre.instance;
/* 165 */     int i = pixelConverter.rgbToPixel(paramColor1.getRGB(), null);
/* 166 */     int j = pixelConverter.rgbToPixel(paramColor2.getRGB(), null);
/*     */ 
/*     */     
/* 169 */     double d1 = paramPoint2D1.getX();
/* 170 */     double d2 = paramPoint2D1.getY();
/* 171 */     paramAffineTransform.translate(d1, d2);
/*     */     
/* 173 */     d1 = paramPoint2D2.getX() - d1;
/* 174 */     d2 = paramPoint2D2.getY() - d2;
/* 175 */     double d3 = Math.sqrt(d1 * d1 + d2 * d2);
/* 176 */     paramAffineTransform.rotate(d1, d2);
/*     */     
/* 178 */     paramAffineTransform.scale(2.0D * d3, 1.0D);
/*     */     
/* 180 */     paramAffineTransform.translate(-0.25D, 0.0D);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 185 */       paramAffineTransform.invert();
/* 186 */       d4 = paramAffineTransform.getScaleX();
/* 187 */       d5 = paramAffineTransform.getShearX();
/* 188 */       d6 = paramAffineTransform.getTranslateX();
/* 189 */     } catch (NoninvertibleTransformException noninvertibleTransformException) {
/* 190 */       d4 = d5 = d6 = 0.0D;
/*     */     } 
/*     */ 
/*     */     
/* 194 */     paramRenderQueue.ensureCapacityAndAlignment(44, 12);
/* 195 */     RenderBuffer renderBuffer = paramRenderQueue.getBuffer();
/* 196 */     renderBuffer.putInt(102);
/* 197 */     renderBuffer.putInt(paramBoolean2 ? 1 : 0);
/* 198 */     renderBuffer.putInt(paramBoolean1 ? 1 : 0);
/* 199 */     renderBuffer.putDouble(d4).putDouble(d5).putDouble(d6);
/* 200 */     renderBuffer.putInt(i).putInt(j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setGradientPaint(RenderQueue paramRenderQueue, SunGraphics2D paramSunGraphics2D, GradientPaint paramGradientPaint, boolean paramBoolean) {
/* 208 */     setGradientPaint(paramRenderQueue, (AffineTransform)paramSunGraphics2D.transform.clone(), paramGradientPaint
/* 209 */         .getColor1(), paramGradientPaint.getColor2(), paramGradientPaint
/* 210 */         .getPoint1(), paramGradientPaint.getPoint2(), paramGradientPaint
/* 211 */         .isCyclic(), paramBoolean);
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
/*     */   private static void setTexturePaint(RenderQueue paramRenderQueue, SunGraphics2D paramSunGraphics2D, TexturePaint paramTexturePaint, boolean paramBoolean) {
/*     */     double d1, d2, d3, d4, d5, d6;
/* 249 */     BufferedImage bufferedImage = paramTexturePaint.getImage();
/* 250 */     SurfaceData surfaceData1 = paramSunGraphics2D.surfaceData;
/*     */     
/* 252 */     SurfaceData surfaceData2 = surfaceData1.getSourceSurfaceData(bufferedImage, 0, CompositeType.SrcOver, null);
/*     */     
/* 254 */     boolean bool = (paramSunGraphics2D.interpolationType != 1) ? true : false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     AffineTransform affineTransform = (AffineTransform)paramSunGraphics2D.transform.clone();
/* 260 */     Rectangle2D rectangle2D = paramTexturePaint.getAnchorRect();
/* 261 */     affineTransform.translate(rectangle2D.getX(), rectangle2D.getY());
/* 262 */     affineTransform.scale(rectangle2D.getWidth(), rectangle2D.getHeight());
/*     */ 
/*     */     
/*     */     try {
/* 266 */       affineTransform.invert();
/* 267 */       d1 = affineTransform.getScaleX();
/* 268 */       d2 = affineTransform.getShearX();
/* 269 */       d3 = affineTransform.getTranslateX();
/* 270 */       d4 = affineTransform.getShearY();
/* 271 */       d5 = affineTransform.getScaleY();
/* 272 */       d6 = affineTransform.getTranslateY();
/* 273 */     } catch (NoninvertibleTransformException noninvertibleTransformException) {
/* 274 */       d1 = d2 = d3 = d4 = d5 = d6 = 0.0D;
/*     */     } 
/*     */ 
/*     */     
/* 278 */     paramRenderQueue.ensureCapacityAndAlignment(68, 12);
/* 279 */     RenderBuffer renderBuffer = paramRenderQueue.getBuffer();
/* 280 */     renderBuffer.putInt(105);
/* 281 */     renderBuffer.putInt(paramBoolean ? 1 : 0);
/* 282 */     renderBuffer.putInt(bool ? 1 : 0);
/* 283 */     renderBuffer.putLong(surfaceData2.getNativeOps());
/* 284 */     renderBuffer.putDouble(d1).putDouble(d2).putDouble(d3);
/* 285 */     renderBuffer.putDouble(d4).putDouble(d5).putDouble(d6);
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
/*     */   public static int convertSRGBtoLinearRGB(int paramInt) {
/* 315 */     float f2, f1 = paramInt / 255.0F;
/* 316 */     if (f1 <= 0.04045F) {
/* 317 */       f2 = f1 / 12.92F;
/*     */     } else {
/* 319 */       f2 = (float)Math.pow((f1 + 0.055D) / 1.055D, 2.4D);
/*     */     } 
/*     */     
/* 322 */     return Math.round(f2 * 255.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int colorToIntArgbPrePixel(Color paramColor, boolean paramBoolean) {
/* 331 */     int i = paramColor.getRGB();
/* 332 */     if (!paramBoolean && i >> 24 == -1) {
/* 333 */       return i;
/*     */     }
/* 335 */     int j = i >>> 24;
/* 336 */     int k = i >> 16 & 0xFF;
/* 337 */     int m = i >> 8 & 0xFF;
/* 338 */     int n = i & 0xFF;
/* 339 */     if (paramBoolean) {
/* 340 */       k = convertSRGBtoLinearRGB(k);
/* 341 */       m = convertSRGBtoLinearRGB(m);
/* 342 */       n = convertSRGBtoLinearRGB(n);
/*     */     } 
/* 344 */     int i1 = j + (j >> 7);
/* 345 */     k = k * i1 >> 8;
/* 346 */     m = m * i1 >> 8;
/* 347 */     n = n * i1 >> 8;
/* 348 */     return j << 24 | k << 16 | m << 8 | n;
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
/*     */   private static int[] convertToIntArgbPrePixels(Color[] paramArrayOfColor, boolean paramBoolean) {
/* 360 */     int[] arrayOfInt = new int[paramArrayOfColor.length];
/* 361 */     for (byte b = 0; b < paramArrayOfColor.length; b++) {
/* 362 */       arrayOfInt[b] = colorToIntArgbPrePixel(paramArrayOfColor[b], paramBoolean);
/*     */     }
/* 364 */     return arrayOfInt;
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
/*     */   private static void setLinearGradientPaint(RenderQueue paramRenderQueue, SunGraphics2D paramSunGraphics2D, LinearGradientPaint paramLinearGradientPaint, boolean paramBoolean) {
/*     */     float f1, f2, f3;
/* 394 */     boolean bool = (paramLinearGradientPaint.getColorSpace() == MultipleGradientPaint.ColorSpaceType.LINEAR_RGB) ? true : false;
/* 395 */     Color[] arrayOfColor = paramLinearGradientPaint.getColors();
/* 396 */     int i = arrayOfColor.length;
/* 397 */     Point2D point2D1 = paramLinearGradientPaint.getStartPoint();
/* 398 */     Point2D point2D2 = paramLinearGradientPaint.getEndPoint();
/* 399 */     AffineTransform affineTransform = paramLinearGradientPaint.getTransform();
/* 400 */     affineTransform.preConcatenate(paramSunGraphics2D.transform);
/*     */     
/* 402 */     if (!bool && i == 2 && paramLinearGradientPaint
/* 403 */       .getCycleMethod() != MultipleGradientPaint.CycleMethod.REPEAT) {
/*     */ 
/*     */ 
/*     */       
/* 407 */       boolean bool1 = (paramLinearGradientPaint.getCycleMethod() != MultipleGradientPaint.CycleMethod.NO_CYCLE) ? true : false;
/* 408 */       setGradientPaint(paramRenderQueue, affineTransform, arrayOfColor[0], arrayOfColor[1], point2D1, point2D2, bool1, paramBoolean);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 415 */     int j = paramLinearGradientPaint.getCycleMethod().ordinal();
/* 416 */     float[] arrayOfFloat = paramLinearGradientPaint.getFractions();
/* 417 */     int[] arrayOfInt = convertToIntArgbPrePixels(arrayOfColor, bool);
/*     */ 
/*     */     
/* 420 */     double d1 = point2D1.getX();
/* 421 */     double d2 = point2D1.getY();
/* 422 */     affineTransform.translate(d1, d2);
/*     */     
/* 424 */     d1 = point2D2.getX() - d1;
/* 425 */     d2 = point2D2.getY() - d2;
/* 426 */     double d3 = Math.sqrt(d1 * d1 + d2 * d2);
/* 427 */     affineTransform.rotate(d1, d2);
/*     */     
/* 429 */     affineTransform.scale(d3, 1.0D);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 434 */       affineTransform.invert();
/* 435 */       f1 = (float)affineTransform.getScaleX();
/* 436 */       f2 = (float)affineTransform.getShearX();
/* 437 */       f3 = (float)affineTransform.getTranslateX();
/* 438 */     } catch (NoninvertibleTransformException noninvertibleTransformException) {
/* 439 */       f1 = f2 = f3 = 0.0F;
/*     */     } 
/*     */ 
/*     */     
/* 443 */     paramRenderQueue.ensureCapacity(32 + i * 4 * 2);
/* 444 */     RenderBuffer renderBuffer = paramRenderQueue.getBuffer();
/* 445 */     renderBuffer.putInt(103);
/* 446 */     renderBuffer.putInt(paramBoolean ? 1 : 0);
/* 447 */     renderBuffer.putInt(bool ? 1 : 0);
/* 448 */     renderBuffer.putInt(j);
/* 449 */     renderBuffer.putInt(i);
/* 450 */     renderBuffer.putFloat(f1);
/* 451 */     renderBuffer.putFloat(f2);
/* 452 */     renderBuffer.putFloat(f3);
/* 453 */     renderBuffer.put(arrayOfFloat);
/* 454 */     renderBuffer.put(arrayOfInt);
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
/*     */   private static void setRadialGradientPaint(RenderQueue paramRenderQueue, SunGraphics2D paramSunGraphics2D, RadialGradientPaint paramRadialGradientPaint, boolean paramBoolean) {
/* 477 */     boolean bool = (paramRadialGradientPaint.getColorSpace() == MultipleGradientPaint.ColorSpaceType.LINEAR_RGB) ? true : false;
/* 478 */     int i = paramRadialGradientPaint.getCycleMethod().ordinal();
/* 479 */     float[] arrayOfFloat = paramRadialGradientPaint.getFractions();
/* 480 */     Color[] arrayOfColor = paramRadialGradientPaint.getColors();
/* 481 */     int j = arrayOfColor.length;
/* 482 */     int[] arrayOfInt = convertToIntArgbPrePixels(arrayOfColor, bool);
/* 483 */     Point2D point2D1 = paramRadialGradientPaint.getCenterPoint();
/* 484 */     Point2D point2D2 = paramRadialGradientPaint.getFocusPoint();
/* 485 */     float f = paramRadialGradientPaint.getRadius();
/*     */ 
/*     */     
/* 488 */     double d1 = point2D1.getX();
/* 489 */     double d2 = point2D1.getY();
/* 490 */     double d3 = point2D2.getX();
/* 491 */     double d4 = point2D2.getY();
/*     */ 
/*     */     
/* 494 */     AffineTransform affineTransform = paramRadialGradientPaint.getTransform();
/* 495 */     affineTransform.preConcatenate(paramSunGraphics2D.transform);
/* 496 */     point2D2 = affineTransform.transform(point2D2, point2D2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 501 */     affineTransform.translate(d1, d2);
/* 502 */     affineTransform.rotate(d3 - d1, d4 - d2);
/* 503 */     affineTransform.scale(f, f);
/*     */ 
/*     */     
/*     */     try {
/* 507 */       affineTransform.invert();
/* 508 */     } catch (Exception exception) {
/* 509 */       affineTransform.setToScale(0.0D, 0.0D);
/*     */     } 
/* 511 */     point2D2 = affineTransform.transform(point2D2, point2D2);
/*     */ 
/*     */ 
/*     */     
/* 515 */     d3 = Math.min(point2D2.getX(), 0.99D);
/*     */ 
/*     */     
/* 518 */     paramRenderQueue.ensureCapacity(48 + j * 4 * 2);
/* 519 */     RenderBuffer renderBuffer = paramRenderQueue.getBuffer();
/* 520 */     renderBuffer.putInt(104);
/* 521 */     renderBuffer.putInt(paramBoolean ? 1 : 0);
/* 522 */     renderBuffer.putInt(bool ? 1 : 0);
/* 523 */     renderBuffer.putInt(j);
/* 524 */     renderBuffer.putInt(i);
/* 525 */     renderBuffer.putFloat((float)affineTransform.getScaleX());
/* 526 */     renderBuffer.putFloat((float)affineTransform.getShearX());
/* 527 */     renderBuffer.putFloat((float)affineTransform.getTranslateX());
/* 528 */     renderBuffer.putFloat((float)affineTransform.getShearY());
/* 529 */     renderBuffer.putFloat((float)affineTransform.getScaleY());
/* 530 */     renderBuffer.putFloat((float)affineTransform.getTranslateY());
/* 531 */     renderBuffer.putFloat((float)d3);
/* 532 */     renderBuffer.put(arrayOfFloat);
/* 533 */     renderBuffer.put(arrayOfInt);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/BufferedPaints.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */