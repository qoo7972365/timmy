/*      */ package sun.java2d.pipe;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.Color;
/*      */ import java.awt.Image;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.NoninvertibleTransformException;
/*      */ import java.awt.image.AffineTransformOp;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.BufferedImageOp;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.awt.image.IndexColorModel;
/*      */ import java.awt.image.VolatileImage;
/*      */ import java.awt.image.WritableRaster;
/*      */ import sun.awt.image.ImageRepresentation;
/*      */ import sun.awt.image.SurfaceManager;
/*      */ import sun.awt.image.ToolkitImage;
/*      */ import sun.java2d.InvalidPipeException;
/*      */ import sun.java2d.SunGraphics2D;
/*      */ import sun.java2d.SurfaceData;
/*      */ import sun.java2d.loops.Blit;
/*      */ import sun.java2d.loops.BlitBg;
/*      */ import sun.java2d.loops.CompositeType;
/*      */ import sun.java2d.loops.MaskBlit;
/*      */ import sun.java2d.loops.ScaledBlit;
/*      */ import sun.java2d.loops.SurfaceType;
/*      */ import sun.java2d.loops.TransformHelper;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DrawImage
/*      */   implements DrawImagePipe
/*      */ {
/*      */   private static final double MAX_TX_ERROR = 1.0E-4D;
/*      */   
/*      */   public boolean copyImage(SunGraphics2D paramSunGraphics2D, Image paramImage, int paramInt1, int paramInt2, Color paramColor) {
/*   64 */     int i = paramImage.getWidth(null);
/*   65 */     int j = paramImage.getHeight(null);
/*   66 */     if (isSimpleTranslate(paramSunGraphics2D)) {
/*   67 */       return renderImageCopy(paramSunGraphics2D, paramImage, paramColor, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, 0, 0, i, j);
/*      */     }
/*      */ 
/*      */     
/*   71 */     AffineTransform affineTransform = paramSunGraphics2D.transform;
/*   72 */     if ((paramInt1 | paramInt2) != 0) {
/*   73 */       affineTransform = new AffineTransform(affineTransform);
/*   74 */       affineTransform.translate(paramInt1, paramInt2);
/*      */     } 
/*   76 */     transformImage(paramSunGraphics2D, paramImage, affineTransform, paramSunGraphics2D.interpolationType, 0, 0, i, j, paramColor);
/*      */     
/*   78 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean copyImage(SunGraphics2D paramSunGraphics2D, Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Color paramColor) {
/*   85 */     if (isSimpleTranslate(paramSunGraphics2D)) {
/*   86 */       return renderImageCopy(paramSunGraphics2D, paramImage, paramColor, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4, paramInt5, paramInt6);
/*      */     }
/*      */ 
/*      */     
/*   90 */     scaleImage(paramSunGraphics2D, paramImage, paramInt1, paramInt2, paramInt1 + paramInt5, paramInt2 + paramInt6, paramInt3, paramInt4, paramInt3 + paramInt5, paramInt4 + paramInt6, paramColor);
/*      */     
/*   92 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean scaleImage(SunGraphics2D paramSunGraphics2D, Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor) {
/*   99 */     int i = paramImage.getWidth(null);
/*  100 */     int j = paramImage.getHeight(null);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  105 */     if (paramInt3 > 0 && paramInt4 > 0 && isSimpleTranslate(paramSunGraphics2D)) {
/*  106 */       double d1 = (paramInt1 + paramSunGraphics2D.transX);
/*  107 */       double d2 = (paramInt2 + paramSunGraphics2D.transY);
/*  108 */       double d3 = d1 + paramInt3;
/*  109 */       double d4 = d2 + paramInt4;
/*  110 */       if (renderImageScale(paramSunGraphics2D, paramImage, paramColor, paramSunGraphics2D.interpolationType, 0, 0, i, j, d1, d2, d3, d4))
/*      */       {
/*      */ 
/*      */         
/*  114 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  118 */     AffineTransform affineTransform = paramSunGraphics2D.transform;
/*  119 */     if ((paramInt1 | paramInt2) != 0 || paramInt3 != i || paramInt4 != j) {
/*  120 */       affineTransform = new AffineTransform(affineTransform);
/*  121 */       affineTransform.translate(paramInt1, paramInt2);
/*  122 */       affineTransform.scale(paramInt3 / i, paramInt4 / j);
/*      */     } 
/*  124 */     transformImage(paramSunGraphics2D, paramImage, affineTransform, paramSunGraphics2D.interpolationType, 0, 0, i, j, paramColor);
/*      */     
/*  126 */     return true;
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
/*      */   protected void transformImage(SunGraphics2D paramSunGraphics2D, Image paramImage, int paramInt1, int paramInt2, AffineTransform paramAffineTransform, int paramInt3) {
/*      */     boolean bool;
/*  140 */     int i = paramAffineTransform.getType();
/*  141 */     int j = paramImage.getWidth(null);
/*  142 */     int k = paramImage.getHeight(null);
/*      */ 
/*      */     
/*  145 */     if (paramSunGraphics2D.transformState <= 2 && (i == 0 || i == 1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  152 */       double d1 = paramAffineTransform.getTranslateX();
/*  153 */       double d2 = paramAffineTransform.getTranslateY();
/*  154 */       d1 += paramSunGraphics2D.transform.getTranslateX();
/*  155 */       d2 += paramSunGraphics2D.transform.getTranslateY();
/*  156 */       int m = (int)Math.floor(d1 + 0.5D);
/*  157 */       int n = (int)Math.floor(d2 + 0.5D);
/*  158 */       if (paramInt3 == 1 || (
/*  159 */         closeToInteger(m, d1) && closeToInteger(n, d2))) {
/*      */         
/*  161 */         renderImageCopy(paramSunGraphics2D, paramImage, null, paramInt1 + m, paramInt2 + n, 0, 0, j, k);
/*      */         return;
/*      */       } 
/*  164 */       bool = false;
/*  165 */     } else if (paramSunGraphics2D.transformState <= 3 && (i & 0x78) == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  177 */       double[] arrayOfDouble = { 0.0D, 0.0D, j, k };
/*      */ 
/*      */       
/*  180 */       paramAffineTransform.transform(arrayOfDouble, 0, arrayOfDouble, 0, 2);
/*  181 */       arrayOfDouble[0] = arrayOfDouble[0] + paramInt1;
/*  182 */       arrayOfDouble[1] = arrayOfDouble[1] + paramInt2;
/*  183 */       arrayOfDouble[2] = arrayOfDouble[2] + paramInt1;
/*  184 */       arrayOfDouble[3] = arrayOfDouble[3] + paramInt2;
/*  185 */       paramSunGraphics2D.transform.transform(arrayOfDouble, 0, arrayOfDouble, 0, 2);
/*      */       
/*  187 */       if (tryCopyOrScale(paramSunGraphics2D, paramImage, 0, 0, j, k, null, paramInt3, arrayOfDouble)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  192 */       bool = false;
/*      */     } else {
/*  194 */       bool = true;
/*      */     } 
/*      */ 
/*      */     
/*  198 */     AffineTransform affineTransform = new AffineTransform(paramSunGraphics2D.transform);
/*  199 */     affineTransform.translate(paramInt1, paramInt2);
/*  200 */     affineTransform.concatenate(paramAffineTransform);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  205 */     if (bool) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  210 */       transformImage(paramSunGraphics2D, paramImage, affineTransform, paramInt3, 0, 0, j, k, null);
/*      */     } else {
/*  212 */       renderImageXform(paramSunGraphics2D, paramImage, affineTransform, paramInt3, 0, 0, j, k, null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void transformImage(SunGraphics2D paramSunGraphics2D, Image paramImage, AffineTransform paramAffineTransform, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Color paramColor) {
/*  244 */     double[] arrayOfDouble = new double[6];
/*      */ 
/*      */     
/*  247 */     arrayOfDouble[2] = (paramInt4 - paramInt2);
/*  248 */     arrayOfDouble[5] = (paramInt5 - paramInt3); arrayOfDouble[3] = (paramInt5 - paramInt3);
/*  249 */     paramAffineTransform.transform(arrayOfDouble, 0, arrayOfDouble, 0, 3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  256 */     if (Math.abs(arrayOfDouble[0] - arrayOfDouble[4]) < 1.0E-4D && 
/*  257 */       Math.abs(arrayOfDouble[3] - arrayOfDouble[5]) < 1.0E-4D && 
/*  258 */       tryCopyOrScale(paramSunGraphics2D, paramImage, paramInt2, paramInt3, paramInt4, paramInt5, paramColor, paramInt1, arrayOfDouble)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  264 */     renderImageXform(paramSunGraphics2D, paramImage, paramAffineTransform, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramColor);
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
/*      */   protected boolean tryCopyOrScale(SunGraphics2D paramSunGraphics2D, Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor, int paramInt5, double[] paramArrayOfdouble) {
/*  281 */     double d1 = paramArrayOfdouble[0];
/*  282 */     double d2 = paramArrayOfdouble[1];
/*  283 */     double d3 = paramArrayOfdouble[2];
/*  284 */     double d4 = paramArrayOfdouble[3];
/*  285 */     double d5 = d3 - d1;
/*  286 */     double d6 = d4 - d2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  293 */     if (d1 < -2.147483648E9D || d1 > 2.147483647E9D || d2 < -2.147483648E9D || d2 > 2.147483647E9D || d3 < -2.147483648E9D || d3 > 2.147483647E9D || d4 < -2.147483648E9D || d4 > 2.147483647E9D)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  298 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  302 */     if (closeToInteger(paramInt3 - paramInt1, d5) && closeToInteger(paramInt4 - paramInt2, d6)) {
/*      */ 
/*      */       
/*  305 */       int i = (int)Math.floor(d1 + 0.5D);
/*  306 */       int j = (int)Math.floor(d2 + 0.5D);
/*  307 */       if (paramInt5 == 1 || (
/*  308 */         closeToInteger(i, d1) && closeToInteger(j, d2))) {
/*      */         
/*  310 */         renderImageCopy(paramSunGraphics2D, paramImage, paramColor, i, j, paramInt1, paramInt2, paramInt3 - paramInt1, paramInt4 - paramInt2);
/*      */ 
/*      */         
/*  313 */         return true;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  318 */     if (d5 > 0.0D && d6 > 0.0D && 
/*  319 */       renderImageScale(paramSunGraphics2D, paramImage, paramColor, paramInt5, paramInt1, paramInt2, paramInt3, paramInt4, d1, d2, d3, d4))
/*      */     {
/*      */ 
/*      */       
/*  323 */       return true;
/*      */     }
/*      */     
/*  326 */     return false;
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
/*      */   BufferedImage makeBufferedImage(Image paramImage, Color paramColor, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  346 */     int i = paramInt4 - paramInt2;
/*  347 */     int j = paramInt5 - paramInt3;
/*  348 */     BufferedImage bufferedImage = new BufferedImage(i, j, paramInt1);
/*  349 */     SunGraphics2D sunGraphics2D = (SunGraphics2D)bufferedImage.createGraphics();
/*  350 */     sunGraphics2D.setComposite(AlphaComposite.Src);
/*  351 */     bufferedImage.setAccelerationPriority(0.0F);
/*  352 */     if (paramColor != null) {
/*  353 */       sunGraphics2D.setColor(paramColor);
/*  354 */       sunGraphics2D.fillRect(0, 0, i, j);
/*  355 */       sunGraphics2D.setComposite(AlphaComposite.SrcOver);
/*      */     } 
/*  357 */     sunGraphics2D.copyImage(paramImage, 0, 0, paramInt2, paramInt3, i, j, null, null);
/*  358 */     sunGraphics2D.dispose();
/*  359 */     return bufferedImage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void renderImageXform(SunGraphics2D paramSunGraphics2D, Image paramImage, AffineTransform paramAffineTransform, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Color paramColor) {
/*      */     AffineTransform affineTransform;
/*      */     try {
/*  369 */       affineTransform = paramAffineTransform.createInverse();
/*  370 */     } catch (NoninvertibleTransformException noninvertibleTransformException) {
/*      */       return;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  384 */     double[] arrayOfDouble = new double[8];
/*      */ 
/*      */ 
/*      */     
/*  388 */     arrayOfDouble[6] = (paramInt4 - paramInt2); arrayOfDouble[2] = (paramInt4 - paramInt2);
/*  389 */     arrayOfDouble[7] = (paramInt5 - paramInt3); arrayOfDouble[5] = (paramInt5 - paramInt3);
/*  390 */     paramAffineTransform.transform(arrayOfDouble, 0, arrayOfDouble, 0, 4);
/*      */     
/*  392 */     double d3 = arrayOfDouble[0], d1 = d3;
/*  393 */     double d4 = arrayOfDouble[1], d2 = d4;
/*  394 */     for (byte b = 2; b < arrayOfDouble.length; b += 2) {
/*  395 */       double d = arrayOfDouble[b];
/*  396 */       if (d1 > d) { d1 = d; }
/*  397 */       else if (d3 < d) { d3 = d; }
/*  398 */        d = arrayOfDouble[b + 1];
/*  399 */       if (d2 > d) { d2 = d; }
/*  400 */       else if (d4 < d) { d4 = d; }
/*      */     
/*      */     } 
/*  403 */     Region region1 = paramSunGraphics2D.getCompClip();
/*  404 */     int i = Math.max((int)Math.floor(d1), region1.lox);
/*  405 */     int j = Math.max((int)Math.floor(d2), region1.loy);
/*  406 */     int k = Math.min((int)Math.ceil(d3), region1.hix);
/*  407 */     int m = Math.min((int)Math.ceil(d4), region1.hiy);
/*  408 */     if (k <= i || m <= j) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  413 */     SurfaceData surfaceData1 = paramSunGraphics2D.surfaceData;
/*  414 */     SurfaceData surfaceData2 = surfaceData1.getSourceSurfaceData(paramImage, 4, paramSunGraphics2D.imageComp, paramColor);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  419 */     if (surfaceData2 == null) {
/*  420 */       paramImage = getBufferedImage(paramImage);
/*  421 */       surfaceData2 = surfaceData1.getSourceSurfaceData(paramImage, 4, paramSunGraphics2D.imageComp, paramColor);
/*      */ 
/*      */ 
/*      */       
/*  425 */       if (surfaceData2 == null) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  431 */     if (isBgOperation(surfaceData2, paramColor)) {
/*      */ 
/*      */ 
/*      */       
/*  435 */       paramImage = makeBufferedImage(paramImage, paramColor, 1, paramInt2, paramInt3, paramInt4, paramInt5);
/*      */ 
/*      */       
/*  438 */       paramInt4 -= paramInt2;
/*  439 */       paramInt5 -= paramInt3;
/*  440 */       paramInt2 = paramInt3 = 0;
/*      */       
/*  442 */       surfaceData2 = surfaceData1.getSourceSurfaceData(paramImage, 4, paramSunGraphics2D.imageComp, paramColor);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  448 */     SurfaceType surfaceType1 = surfaceData2.getSurfaceType();
/*  449 */     TransformHelper transformHelper = TransformHelper.getFromCache(surfaceType1);
/*      */     
/*  451 */     if (transformHelper == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  459 */       boolean bool = (surfaceData2.getTransparency() == 1) ? true : true;
/*      */ 
/*      */       
/*  462 */       paramImage = makeBufferedImage(paramImage, null, bool, paramInt2, paramInt3, paramInt4, paramInt5);
/*      */       
/*  464 */       paramInt4 -= paramInt2;
/*  465 */       paramInt5 -= paramInt3;
/*  466 */       paramInt2 = paramInt3 = 0;
/*      */       
/*  468 */       surfaceData2 = surfaceData1.getSourceSurfaceData(paramImage, 4, paramSunGraphics2D.imageComp, null);
/*      */ 
/*      */ 
/*      */       
/*  472 */       surfaceType1 = surfaceData2.getSurfaceType();
/*  473 */       transformHelper = TransformHelper.getFromCache(surfaceType1);
/*      */     } 
/*      */ 
/*      */     
/*  477 */     SurfaceType surfaceType2 = surfaceData1.getSurfaceType();
/*  478 */     if (paramSunGraphics2D.compositeState <= 1) {
/*      */ 
/*      */ 
/*      */       
/*  482 */       MaskBlit maskBlit1 = MaskBlit.getFromCache(SurfaceType.IntArgbPre, paramSunGraphics2D.imageComp, surfaceType2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  491 */       if (maskBlit1.getNativePrim() != 0L) {
/*      */         
/*  493 */         transformHelper.Transform(maskBlit1, surfaceData2, surfaceData1, paramSunGraphics2D.composite, region1, affineTransform, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, i, j, k, m, null, 0, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  505 */     int n = k - i;
/*  506 */     int i1 = m - j;
/*  507 */     BufferedImage bufferedImage = new BufferedImage(n, i1, 3);
/*      */     
/*  509 */     SurfaceData surfaceData3 = SurfaceData.getPrimarySurfaceData(bufferedImage);
/*  510 */     SurfaceType surfaceType3 = surfaceData3.getSurfaceType();
/*  511 */     MaskBlit maskBlit = MaskBlit.getFromCache(SurfaceType.IntArgbPre, CompositeType.SrcNoEa, surfaceType3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  528 */     int[] arrayOfInt = new int[i1 * 2 + 2];
/*      */ 
/*      */ 
/*      */     
/*  532 */     transformHelper.Transform(maskBlit, surfaceData2, surfaceData3, AlphaComposite.Src, null, affineTransform, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, 0, 0, n, i1, arrayOfInt, i, j);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  539 */     Region region2 = Region.getInstance(i, j, k, m, arrayOfInt);
/*  540 */     region1 = region1.getIntersection(region2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  545 */     Blit blit = Blit.getFromCache(surfaceType3, paramSunGraphics2D.imageComp, surfaceType2);
/*  546 */     blit.Blit(surfaceData3, surfaceData1, paramSunGraphics2D.composite, region1, 0, 0, i, j, n, i1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean renderImageCopy(SunGraphics2D paramSunGraphics2D, Image paramImage, Color paramColor, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  557 */     Region region = paramSunGraphics2D.getCompClip();
/*  558 */     SurfaceData surfaceData = paramSunGraphics2D.surfaceData;
/*      */     
/*  560 */     byte b = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  566 */       SurfaceData surfaceData1 = surfaceData.getSourceSurfaceData(paramImage, 0, paramSunGraphics2D.imageComp, paramColor);
/*      */ 
/*      */ 
/*      */       
/*  570 */       if (surfaceData1 == null) {
/*  571 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  575 */       try { SurfaceType surfaceType1 = surfaceData1.getSurfaceType();
/*  576 */         SurfaceType surfaceType2 = surfaceData.getSurfaceType();
/*  577 */         blitSurfaceData(paramSunGraphics2D, region, surfaceData1, surfaceData, surfaceType1, surfaceType2, paramInt3, paramInt4, paramInt1, paramInt2, paramInt5, paramInt6, paramColor);
/*      */ 
/*      */         
/*  580 */         return true; }
/*  581 */       catch (NullPointerException nullPointerException)
/*  582 */       { if (!SurfaceData.isNull(surfaceData) && 
/*  583 */           !SurfaceData.isNull(surfaceData1))
/*      */         {
/*      */           
/*  586 */           throw nullPointerException;
/*      */         }
/*  588 */         return false; }
/*      */       
/*  590 */       catch (InvalidPipeException invalidPipeException)
/*      */       
/*      */       { 
/*      */         
/*  594 */         b++;
/*  595 */         region = paramSunGraphics2D.getCompClip();
/*  596 */         surfaceData = paramSunGraphics2D.surfaceData;
/*  597 */         if (SurfaceData.isNull(surfaceData) || 
/*  598 */           SurfaceData.isNull(surfaceData1) || b > 1)
/*      */           break;  } 
/*  600 */     }  return false;
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
/*      */   protected boolean renderImageScale(SunGraphics2D paramSunGraphics2D, Image paramImage, Color paramColor, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  616 */     if (paramInt1 != 1) {
/*  617 */       return false;
/*      */     }
/*      */     
/*  620 */     Region region = paramSunGraphics2D.getCompClip();
/*  621 */     SurfaceData surfaceData = paramSunGraphics2D.surfaceData;
/*      */     
/*  623 */     byte b = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  629 */       SurfaceData surfaceData1 = surfaceData.getSourceSurfaceData(paramImage, 3, paramSunGraphics2D.imageComp, paramColor);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  634 */       if (surfaceData1 == null || isBgOperation(surfaceData1, paramColor)) {
/*  635 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  639 */       try { SurfaceType surfaceType1 = surfaceData1.getSurfaceType();
/*  640 */         SurfaceType surfaceType2 = surfaceData.getSurfaceType();
/*  641 */         return scaleSurfaceData(paramSunGraphics2D, region, surfaceData1, surfaceData, surfaceType1, surfaceType2, paramInt2, paramInt3, paramInt4, paramInt5, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*      */         
/*      */          }
/*      */       
/*  645 */       catch (NullPointerException nullPointerException)
/*  646 */       { if (!SurfaceData.isNull(surfaceData))
/*      */         {
/*  648 */           throw nullPointerException;
/*      */         }
/*  650 */         return false; }
/*      */       
/*  652 */       catch (InvalidPipeException invalidPipeException)
/*      */       
/*      */       { 
/*      */         
/*  656 */         b++;
/*  657 */         region = paramSunGraphics2D.getCompClip();
/*  658 */         surfaceData = paramSunGraphics2D.surfaceData;
/*  659 */         if (SurfaceData.isNull(surfaceData) || 
/*  660 */           SurfaceData.isNull(surfaceData1) || b > 1)
/*      */           break;  } 
/*  662 */     }  return false;
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
/*      */   public boolean scaleImage(SunGraphics2D paramSunGraphics2D, Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Color paramColor) {
/*      */     int i, j, k, m, n, i1, i2, i3;
/*  675 */     boolean bool1 = false;
/*  676 */     boolean bool2 = false;
/*  677 */     boolean bool3 = false;
/*  678 */     boolean bool4 = false;
/*      */     
/*  680 */     if (paramInt7 > paramInt5) {
/*  681 */       i = paramInt7 - paramInt5;
/*  682 */       n = paramInt5;
/*      */     } else {
/*  684 */       bool1 = true;
/*  685 */       i = paramInt5 - paramInt7;
/*  686 */       n = paramInt7;
/*      */     } 
/*  688 */     if (paramInt8 > paramInt6) {
/*  689 */       j = paramInt8 - paramInt6;
/*  690 */       i1 = paramInt6;
/*      */     } else {
/*  692 */       bool2 = true;
/*  693 */       j = paramInt6 - paramInt8;
/*  694 */       i1 = paramInt8;
/*      */     } 
/*  696 */     if (paramInt3 > paramInt1) {
/*  697 */       k = paramInt3 - paramInt1;
/*  698 */       i2 = paramInt1;
/*      */     } else {
/*  700 */       k = paramInt1 - paramInt3;
/*  701 */       bool3 = true;
/*  702 */       i2 = paramInt3;
/*      */     } 
/*  704 */     if (paramInt4 > paramInt2) {
/*  705 */       m = paramInt4 - paramInt2;
/*  706 */       i3 = paramInt2;
/*      */     } else {
/*  708 */       m = paramInt2 - paramInt4;
/*  709 */       bool4 = true;
/*  710 */       i3 = paramInt4;
/*      */     } 
/*  712 */     if (i <= 0 || j <= 0) {
/*  713 */       return true;
/*      */     }
/*      */     
/*  716 */     if (bool1 == bool3 && bool2 == bool4 && 
/*      */       
/*  718 */       isSimpleTranslate(paramSunGraphics2D)) {
/*      */       
/*  720 */       double d3 = (i2 + paramSunGraphics2D.transX);
/*  721 */       double d4 = (i3 + paramSunGraphics2D.transY);
/*  722 */       double d5 = d3 + k;
/*  723 */       double d6 = d4 + m;
/*  724 */       if (renderImageScale(paramSunGraphics2D, paramImage, paramColor, paramSunGraphics2D.interpolationType, n, i1, n + i, i1 + j, d3, d4, d5, d6))
/*      */       {
/*      */ 
/*      */         
/*  728 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  732 */     AffineTransform affineTransform = new AffineTransform(paramSunGraphics2D.transform);
/*  733 */     affineTransform.translate(paramInt1, paramInt2);
/*  734 */     double d1 = (paramInt3 - paramInt1) / (paramInt7 - paramInt5);
/*  735 */     double d2 = (paramInt4 - paramInt2) / (paramInt8 - paramInt6);
/*  736 */     affineTransform.scale(d1, d2);
/*  737 */     affineTransform.translate((n - paramInt5), (i1 - paramInt6));
/*      */     
/*  739 */     int i4 = SurfaceManager.getImageScale(paramImage);
/*  740 */     int i5 = paramImage.getWidth(null) * i4;
/*  741 */     int i6 = paramImage.getHeight(null) * i4;
/*  742 */     i += n;
/*  743 */     j += i1;
/*      */     
/*  745 */     if (i > i5) {
/*  746 */       i = i5;
/*      */     }
/*  748 */     if (j > i6) {
/*  749 */       j = i6;
/*      */     }
/*  751 */     if (n < 0) {
/*  752 */       affineTransform.translate(-n, 0.0D);
/*  753 */       n = 0;
/*      */     } 
/*  755 */     if (i1 < 0) {
/*  756 */       affineTransform.translate(0.0D, -i1);
/*  757 */       i1 = 0;
/*      */     } 
/*  759 */     if (n >= i || i1 >= j) {
/*  760 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  770 */     transformImage(paramSunGraphics2D, paramImage, affineTransform, paramSunGraphics2D.interpolationType, n, i1, i, j, paramColor);
/*      */     
/*  772 */     return true;
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
/*      */   public static boolean closeToInteger(int paramInt, double paramDouble) {
/*  798 */     return (Math.abs(paramDouble - paramInt) < 1.0E-4D);
/*      */   }
/*      */   
/*      */   public static boolean isSimpleTranslate(SunGraphics2D paramSunGraphics2D) {
/*  802 */     int i = paramSunGraphics2D.transformState;
/*  803 */     if (i <= 1)
/*      */     {
/*  805 */       return true;
/*      */     }
/*  807 */     if (i >= 3)
/*      */     {
/*  809 */       return false;
/*      */     }
/*      */     
/*  812 */     if (paramSunGraphics2D.interpolationType == 1) {
/*  813 */       return true;
/*      */     }
/*  815 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static boolean isBgOperation(SurfaceData paramSurfaceData, Color paramColor) {
/*  821 */     return (paramSurfaceData == null || (paramColor != null && paramSurfaceData
/*      */       
/*  823 */       .getTransparency() != 1));
/*      */   }
/*      */   
/*      */   protected BufferedImage getBufferedImage(Image paramImage) {
/*  827 */     if (paramImage instanceof BufferedImage) {
/*  828 */       return (BufferedImage)paramImage;
/*      */     }
/*      */     
/*  831 */     return ((VolatileImage)paramImage).getSnapshot();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ColorModel getTransformColorModel(SunGraphics2D paramSunGraphics2D, BufferedImage paramBufferedImage, AffineTransform paramAffineTransform) {
/*  841 */     ColorModel colorModel1 = paramBufferedImage.getColorModel();
/*  842 */     ColorModel colorModel2 = colorModel1;
/*      */     
/*  844 */     if (paramAffineTransform.isIdentity()) {
/*  845 */       return colorModel2;
/*      */     }
/*  847 */     int i = paramAffineTransform.getType();
/*  848 */     boolean bool = ((i & 0x38) != 0) ? true : false;
/*      */ 
/*      */     
/*  851 */     if (!bool && i != 1 && i != 0) {
/*      */ 
/*      */ 
/*      */       
/*  855 */       double[] arrayOfDouble = new double[4];
/*  856 */       paramAffineTransform.getMatrix(arrayOfDouble);
/*      */ 
/*      */       
/*  859 */       bool = (arrayOfDouble[0] != (int)arrayOfDouble[0] || arrayOfDouble[3] != (int)arrayOfDouble[3]) ? true : false;
/*      */     } 
/*      */     
/*  862 */     if (paramSunGraphics2D.renderHint != 2) {
/*  863 */       if (colorModel1 instanceof IndexColorModel) {
/*  864 */         WritableRaster writableRaster = paramBufferedImage.getRaster();
/*  865 */         IndexColorModel indexColorModel = (IndexColorModel)colorModel1;
/*      */         
/*  867 */         if (bool && colorModel1.getTransparency() == 1)
/*      */         {
/*  869 */           if (writableRaster instanceof sun.awt.image.BytePackedRaster) {
/*  870 */             colorModel2 = ColorModel.getRGBdefault();
/*      */           } else {
/*      */             
/*  873 */             double[] arrayOfDouble = new double[6];
/*  874 */             paramAffineTransform.getMatrix(arrayOfDouble);
/*  875 */             if (arrayOfDouble[1] != 0.0D || arrayOfDouble[2] != 0.0D || arrayOfDouble[4] != 0.0D || arrayOfDouble[5] != 0.0D) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  880 */               int j = indexColorModel.getMapSize();
/*  881 */               if (j < 256) {
/*  882 */                 int[] arrayOfInt = new int[j + 1];
/*  883 */                 indexColorModel.getRGBs(arrayOfInt);
/*  884 */                 arrayOfInt[j] = 0;
/*      */                 
/*  886 */                 colorModel2 = new IndexColorModel(indexColorModel.getPixelSize(), j + 1, arrayOfInt, 0, true, j, 0);
/*      */               
/*      */               }
/*      */               else {
/*      */ 
/*      */                 
/*  892 */                 colorModel2 = ColorModel.getRGBdefault();
/*      */               }
/*      */             
/*      */             } 
/*      */           } 
/*      */         }
/*  898 */       } else if (bool && colorModel1.getTransparency() == 1) {
/*      */ 
/*      */ 
/*      */         
/*  902 */         colorModel2 = ColorModel.getRGBdefault();
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  907 */     else if (colorModel1 instanceof IndexColorModel || (bool && colorModel1
/*  908 */       .getTransparency() == 1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  913 */       colorModel2 = ColorModel.getRGBdefault();
/*      */     } 
/*      */ 
/*      */     
/*  917 */     return colorModel2;
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
/*      */   protected void blitSurfaceData(SunGraphics2D paramSunGraphics2D, Region paramRegion, SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, SurfaceType paramSurfaceType1, SurfaceType paramSurfaceType2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Color paramColor) {
/*  930 */     if (paramInt5 <= 0 || paramInt6 <= 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  949 */     CompositeType compositeType = paramSunGraphics2D.imageComp;
/*  950 */     if (CompositeType.SrcOverNoEa.equals(compositeType) && (paramSurfaceData1
/*  951 */       .getTransparency() == 1 || (paramColor != null && paramColor
/*      */       
/*  953 */       .getTransparency() == 1)))
/*      */     {
/*  955 */       compositeType = CompositeType.SrcNoEa;
/*      */     }
/*  957 */     if (!isBgOperation(paramSurfaceData1, paramColor)) {
/*  958 */       Blit blit = Blit.getFromCache(paramSurfaceType1, compositeType, paramSurfaceType2);
/*  959 */       blit.Blit(paramSurfaceData1, paramSurfaceData2, paramSunGraphics2D.composite, paramRegion, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*      */     } else {
/*      */       
/*  962 */       BlitBg blitBg = BlitBg.getFromCache(paramSurfaceType1, compositeType, paramSurfaceType2);
/*  963 */       blitBg.BlitBg(paramSurfaceData1, paramSurfaceData2, paramSunGraphics2D.composite, paramRegion, paramColor
/*  964 */           .getRGB(), paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
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
/*      */   protected boolean scaleSurfaceData(SunGraphics2D paramSunGraphics2D, Region paramRegion, SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, SurfaceType paramSurfaceType1, SurfaceType paramSurfaceType2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  979 */     CompositeType compositeType = paramSunGraphics2D.imageComp;
/*  980 */     if (CompositeType.SrcOverNoEa.equals(compositeType) && paramSurfaceData1
/*  981 */       .getTransparency() == 1)
/*      */     {
/*  983 */       compositeType = CompositeType.SrcNoEa;
/*      */     }
/*      */     
/*  986 */     ScaledBlit scaledBlit = ScaledBlit.getFromCache(paramSurfaceType1, compositeType, paramSurfaceType2);
/*  987 */     if (scaledBlit != null) {
/*  988 */       scaledBlit.Scale(paramSurfaceData1, paramSurfaceData2, paramSunGraphics2D.composite, paramRegion, paramInt1, paramInt2, paramInt3, paramInt4, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*      */       
/*  990 */       return true;
/*      */     } 
/*  992 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static boolean imageReady(ToolkitImage paramToolkitImage, ImageObserver paramImageObserver) {
/*  998 */     if (paramToolkitImage.hasError()) {
/*  999 */       if (paramImageObserver != null) {
/* 1000 */         paramImageObserver.imageUpdate(paramToolkitImage, 192, -1, -1, -1, -1);
/*      */       }
/*      */ 
/*      */       
/* 1004 */       return false;
/*      */     } 
/* 1006 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean copyImage(SunGraphics2D paramSunGraphics2D, Image paramImage, int paramInt1, int paramInt2, Color paramColor, ImageObserver paramImageObserver) {
/* 1013 */     if (!(paramImage instanceof ToolkitImage)) {
/* 1014 */       return copyImage(paramSunGraphics2D, paramImage, paramInt1, paramInt2, paramColor);
/*      */     }
/* 1016 */     ToolkitImage toolkitImage = (ToolkitImage)paramImage;
/* 1017 */     if (!imageReady(toolkitImage, paramImageObserver)) {
/* 1018 */       return false;
/*      */     }
/* 1020 */     ImageRepresentation imageRepresentation = toolkitImage.getImageRep();
/* 1021 */     return imageRepresentation.drawToBufImage(paramSunGraphics2D, toolkitImage, paramInt1, paramInt2, paramColor, paramImageObserver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean copyImage(SunGraphics2D paramSunGraphics2D, Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Color paramColor, ImageObserver paramImageObserver) {
/* 1029 */     if (!(paramImage instanceof ToolkitImage)) {
/* 1030 */       return copyImage(paramSunGraphics2D, paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramColor);
/*      */     }
/* 1032 */     ToolkitImage toolkitImage = (ToolkitImage)paramImage;
/* 1033 */     if (!imageReady(toolkitImage, paramImageObserver)) {
/* 1034 */       return false;
/*      */     }
/* 1036 */     ImageRepresentation imageRepresentation = toolkitImage.getImageRep();
/* 1037 */     return imageRepresentation.drawToBufImage(paramSunGraphics2D, toolkitImage, paramInt1, paramInt2, paramInt1 + paramInt5, paramInt2 + paramInt6, paramInt3, paramInt4, paramInt3 + paramInt5, paramInt4 + paramInt6, paramColor, paramImageObserver);
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
/*      */   public boolean scaleImage(SunGraphics2D paramSunGraphics2D, Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor, ImageObserver paramImageObserver) {
/* 1049 */     if (!(paramImage instanceof ToolkitImage)) {
/* 1050 */       return scaleImage(paramSunGraphics2D, paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramColor);
/*      */     }
/* 1052 */     ToolkitImage toolkitImage = (ToolkitImage)paramImage;
/* 1053 */     if (!imageReady(toolkitImage, paramImageObserver)) {
/* 1054 */       return false;
/*      */     }
/* 1056 */     ImageRepresentation imageRepresentation = toolkitImage.getImageRep();
/* 1057 */     return imageRepresentation.drawToBufImage(paramSunGraphics2D, toolkitImage, paramInt1, paramInt2, paramInt3, paramInt4, paramColor, paramImageObserver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean scaleImage(SunGraphics2D paramSunGraphics2D, Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Color paramColor, ImageObserver paramImageObserver) {
/* 1067 */     if (!(paramImage instanceof ToolkitImage)) {
/* 1068 */       return scaleImage(paramSunGraphics2D, paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramColor);
/*      */     }
/*      */     
/* 1071 */     ToolkitImage toolkitImage = (ToolkitImage)paramImage;
/* 1072 */     if (!imageReady(toolkitImage, paramImageObserver)) {
/* 1073 */       return false;
/*      */     }
/* 1075 */     ImageRepresentation imageRepresentation = toolkitImage.getImageRep();
/* 1076 */     return imageRepresentation.drawToBufImage(paramSunGraphics2D, toolkitImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramColor, paramImageObserver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean transformImage(SunGraphics2D paramSunGraphics2D, Image paramImage, AffineTransform paramAffineTransform, ImageObserver paramImageObserver) {
/* 1084 */     if (!(paramImage instanceof ToolkitImage)) {
/* 1085 */       transformImage(paramSunGraphics2D, paramImage, 0, 0, paramAffineTransform, paramSunGraphics2D.interpolationType);
/* 1086 */       return true;
/*      */     } 
/* 1088 */     ToolkitImage toolkitImage = (ToolkitImage)paramImage;
/* 1089 */     if (!imageReady(toolkitImage, paramImageObserver)) {
/* 1090 */       return false;
/*      */     }
/* 1092 */     ImageRepresentation imageRepresentation = toolkitImage.getImageRep();
/* 1093 */     return imageRepresentation.drawToBufImage(paramSunGraphics2D, toolkitImage, paramAffineTransform, paramImageObserver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transformImage(SunGraphics2D paramSunGraphics2D, BufferedImage paramBufferedImage, BufferedImageOp paramBufferedImageOp, int paramInt1, int paramInt2) {
/* 1100 */     if (paramBufferedImageOp != null) {
/* 1101 */       if (paramBufferedImageOp instanceof AffineTransformOp) {
/* 1102 */         AffineTransformOp affineTransformOp = (AffineTransformOp)paramBufferedImageOp;
/* 1103 */         transformImage(paramSunGraphics2D, paramBufferedImage, paramInt1, paramInt2, affineTransformOp
/* 1104 */             .getTransform(), affineTransformOp
/* 1105 */             .getInterpolationType());
/*      */         return;
/*      */       } 
/* 1108 */       paramBufferedImage = paramBufferedImageOp.filter(paramBufferedImage, null);
/*      */     } 
/*      */     
/* 1111 */     copyImage(paramSunGraphics2D, paramBufferedImage, paramInt1, paramInt2, null);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/DrawImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */