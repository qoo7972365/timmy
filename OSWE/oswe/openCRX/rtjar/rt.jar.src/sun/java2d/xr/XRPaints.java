/*     */ package sun.java2d.xr;
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
/*     */ abstract class XRPaints
/*     */ {
/*     */   static XRCompositeManager xrCompMan;
/*  39 */   static final XRGradient xrGradient = new XRGradient();
/*  40 */   static final XRLinearGradient xrLinearGradient = new XRLinearGradient();
/*  41 */   static final XRRadialGradient xrRadialGradient = new XRRadialGradient();
/*  42 */   static final XRTexture xrTexture = new XRTexture();
/*     */   
/*     */   public static void register(XRCompositeManager paramXRCompositeManager) {
/*  45 */     xrCompMan = paramXRCompositeManager;
/*     */   }
/*     */   
/*     */   private static XRPaints getXRPaint(SunGraphics2D paramSunGraphics2D) {
/*  49 */     switch (paramSunGraphics2D.paintState) {
/*     */       case 2:
/*  51 */         return xrGradient;
/*     */       
/*     */       case 3:
/*  54 */         return xrLinearGradient;
/*     */       
/*     */       case 4:
/*  57 */         return xrRadialGradient;
/*     */       
/*     */       case 5:
/*  60 */         return xrTexture;
/*     */     } 
/*     */     
/*  63 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isValid(SunGraphics2D paramSunGraphics2D) {
/*  74 */     XRPaints xRPaints = getXRPaint(paramSunGraphics2D);
/*  75 */     return (xRPaints != null && xRPaints.isPaintValid(paramSunGraphics2D));
/*     */   }
/*     */   
/*     */   static void setPaint(SunGraphics2D paramSunGraphics2D, Paint paramPaint) {
/*  79 */     XRPaints xRPaints = getXRPaint(paramSunGraphics2D);
/*  80 */     if (xRPaints != null) {
/*  81 */       xRPaints.setXRPaint(paramSunGraphics2D, paramPaint);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract boolean isPaintValid(SunGraphics2D paramSunGraphics2D);
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void setXRPaint(SunGraphics2D paramSunGraphics2D, Paint paramPaint);
/*     */ 
/*     */ 
/*     */   
/*     */   private static class XRGradient
/*     */     extends XRPaints
/*     */   {
/*     */     private XRGradient() {}
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isPaintValid(SunGraphics2D param1SunGraphics2D) {
/* 104 */       return true;
/*     */     }
/*     */     
/*     */     void setXRPaint(SunGraphics2D param1SunGraphics2D, Paint param1Paint) {
/* 108 */       GradientPaint gradientPaint = (GradientPaint)param1Paint;
/*     */       
/* 110 */       byte b = gradientPaint.isCyclic() ? 3 : 2;
/* 111 */       float[] arrayOfFloat = { 0.0F, 1.0F };
/* 112 */       int[] arrayOfInt = convertToIntArgbPixels(new Color[] { gradientPaint.getColor1(), gradientPaint.getColor2() });
/*     */       
/* 114 */       Point2D point2D1 = gradientPaint.getPoint1();
/* 115 */       Point2D point2D2 = gradientPaint.getPoint2();
/*     */       
/* 117 */       XRBackend xRBackend = xrCompMan.getBackend();
/* 118 */       int i = xRBackend.createLinearGradient(point2D1, point2D2, arrayOfFloat, arrayOfInt, b);
/* 119 */       xrCompMan.setGradientPaint(new XRSurfaceData.XRInternalSurfaceData(xRBackend, i));
/*     */     }
/*     */   }
/*     */   
/*     */   public int getGradientLength(Point2D paramPoint2D1, Point2D paramPoint2D2) {
/* 124 */     double d1 = Math.max(paramPoint2D1.getX(), paramPoint2D2.getX()) - Math.min(paramPoint2D1.getX(), paramPoint2D2.getX());
/* 125 */     double d2 = Math.max(paramPoint2D1.getY(), paramPoint2D2.getY()) - Math.min(paramPoint2D1.getY(), paramPoint2D2.getY());
/* 126 */     return (int)Math.ceil(Math.sqrt(d1 * d1 + d2 * d2));
/*     */   }
/*     */   
/*     */   private static class XRLinearGradient extends XRPaints {
/*     */     private XRLinearGradient() {}
/*     */     
/*     */     boolean isPaintValid(SunGraphics2D param1SunGraphics2D) {
/* 133 */       return (((LinearGradientPaint)param1SunGraphics2D.getPaint()).getColorSpace() == MultipleGradientPaint.ColorSpaceType.SRGB);
/*     */     }
/*     */ 
/*     */     
/*     */     void setXRPaint(SunGraphics2D param1SunGraphics2D, Paint param1Paint) {
/* 138 */       LinearGradientPaint linearGradientPaint = (LinearGradientPaint)param1Paint;
/*     */       
/* 140 */       Color[] arrayOfColor = linearGradientPaint.getColors();
/* 141 */       Point2D point2D1 = linearGradientPaint.getStartPoint();
/* 142 */       Point2D point2D2 = linearGradientPaint.getEndPoint();
/*     */       
/* 144 */       int i = XRUtils.getRepeatForCycleMethod(linearGradientPaint.getCycleMethod());
/* 145 */       float[] arrayOfFloat = linearGradientPaint.getFractions();
/* 146 */       int[] arrayOfInt = convertToIntArgbPixels(arrayOfColor);
/*     */       
/* 148 */       AffineTransform affineTransform = linearGradientPaint.getTransform();
/*     */       try {
/* 150 */         affineTransform.invert();
/* 151 */       } catch (NoninvertibleTransformException noninvertibleTransformException) {
/* 152 */         noninvertibleTransformException.printStackTrace();
/*     */       } 
/*     */       
/* 155 */       XRBackend xRBackend = xrCompMan.getBackend();
/* 156 */       int j = xRBackend.createLinearGradient(point2D1, point2D2, arrayOfFloat, arrayOfInt, i);
/* 157 */       XRSurfaceData.XRInternalSurfaceData xRInternalSurfaceData = new XRSurfaceData.XRInternalSurfaceData(xRBackend, j);
/* 158 */       xRInternalSurfaceData.setStaticSrcTx(affineTransform);
/* 159 */       xrCompMan.setGradientPaint(xRInternalSurfaceData);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class XRRadialGradient extends XRPaints {
/*     */     private XRRadialGradient() {}
/*     */     
/*     */     boolean isPaintValid(SunGraphics2D param1SunGraphics2D) {
/* 167 */       RadialGradientPaint radialGradientPaint = (RadialGradientPaint)param1SunGraphics2D.paint;
/* 168 */       return (radialGradientPaint.getFocusPoint().equals(radialGradientPaint.getCenterPoint()) && radialGradientPaint
/* 169 */         .getColorSpace() == MultipleGradientPaint.ColorSpaceType.SRGB);
/*     */     }
/*     */ 
/*     */     
/*     */     void setXRPaint(SunGraphics2D param1SunGraphics2D, Paint param1Paint) {
/* 174 */       RadialGradientPaint radialGradientPaint = (RadialGradientPaint)param1Paint;
/* 175 */       Color[] arrayOfColor = radialGradientPaint.getColors();
/* 176 */       Point2D point2D = radialGradientPaint.getCenterPoint();
/*     */       
/* 178 */       int i = XRUtils.getRepeatForCycleMethod(radialGradientPaint.getCycleMethod());
/* 179 */       float[] arrayOfFloat = radialGradientPaint.getFractions();
/* 180 */       int[] arrayOfInt = convertToIntArgbPixels(arrayOfColor);
/* 181 */       float f1 = radialGradientPaint.getRadius();
/*     */       
/* 183 */       float f2 = (float)point2D.getX();
/* 184 */       float f3 = (float)point2D.getY();
/*     */       
/* 186 */       AffineTransform affineTransform = radialGradientPaint.getTransform();
/*     */       try {
/* 188 */         affineTransform.invert();
/* 189 */       } catch (NoninvertibleTransformException noninvertibleTransformException) {
/* 190 */         noninvertibleTransformException.printStackTrace();
/*     */       } 
/*     */       
/* 193 */       XRBackend xRBackend = xrCompMan.getBackend();
/* 194 */       int j = xRBackend.createRadialGradient(f2, f3, 0.0F, f1, arrayOfFloat, arrayOfInt, i);
/* 195 */       XRSurfaceData.XRInternalSurfaceData xRInternalSurfaceData = new XRSurfaceData.XRInternalSurfaceData(xRBackend, j);
/* 196 */       xRInternalSurfaceData.setStaticSrcTx(affineTransform);
/* 197 */       xrCompMan.setGradientPaint(xRInternalSurfaceData);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class XRTexture
/*     */     extends XRPaints
/*     */   {
/*     */     private XRTexture() {}
/*     */     
/*     */     private XRSurfaceData getAccSrcSurface(XRSurfaceData param1XRSurfaceData, BufferedImage param1BufferedImage) {
/* 207 */       SurfaceData surfaceData = param1XRSurfaceData.getSourceSurfaceData(param1BufferedImage, 0, CompositeType.SrcOver, null);
/* 208 */       if (!(surfaceData instanceof XRSurfaceData)) {
/* 209 */         surfaceData = param1XRSurfaceData.getSourceSurfaceData(param1BufferedImage, 0, CompositeType.SrcOver, null);
/* 210 */         if (!(surfaceData instanceof XRSurfaceData)) {
/* 211 */           throw new InternalError("Surface not cachable");
/*     */         }
/*     */       } 
/*     */       
/* 215 */       return (XRSurfaceData)surfaceData;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isPaintValid(SunGraphics2D param1SunGraphics2D) {
/* 220 */       TexturePaint texturePaint = (TexturePaint)param1SunGraphics2D.paint;
/* 221 */       BufferedImage bufferedImage = texturePaint.getImage();
/* 222 */       XRSurfaceData xRSurfaceData = (XRSurfaceData)param1SunGraphics2D.getDestSurface();
/*     */       
/* 224 */       return (getAccSrcSurface(xRSurfaceData, bufferedImage) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     void setXRPaint(SunGraphics2D param1SunGraphics2D, Paint param1Paint) {
/* 229 */       TexturePaint texturePaint = (TexturePaint)param1Paint;
/* 230 */       BufferedImage bufferedImage = texturePaint.getImage();
/* 231 */       Rectangle2D rectangle2D = texturePaint.getAnchorRect();
/*     */       
/* 233 */       XRSurfaceData xRSurfaceData1 = (XRSurfaceData)param1SunGraphics2D.surfaceData;
/* 234 */       XRSurfaceData xRSurfaceData2 = getAccSrcSurface(xRSurfaceData1, bufferedImage);
/*     */       
/* 236 */       AffineTransform affineTransform = new AffineTransform();
/* 237 */       affineTransform.translate(rectangle2D.getX(), rectangle2D.getY());
/* 238 */       affineTransform.scale(rectangle2D.getWidth() / bufferedImage.getWidth(), rectangle2D.getHeight() / bufferedImage.getHeight());
/*     */       
/*     */       try {
/* 241 */         affineTransform.invert();
/* 242 */       } catch (NoninvertibleTransformException noninvertibleTransformException) {
/* 243 */         affineTransform.setToIdentity();
/*     */       } 
/* 245 */       xRSurfaceData2.setStaticSrcTx(affineTransform);
/*     */       
/* 247 */       xRSurfaceData2.validateAsSource(affineTransform, 1, XRUtils.ATransOpToXRQuality(param1SunGraphics2D.interpolationType));
/* 248 */       xrCompMan.setTexturePaint(xRSurfaceData2);
/*     */     }
/*     */   }
/*     */   
/*     */   public int[] convertToIntArgbPixels(Color[] paramArrayOfColor) {
/* 253 */     int[] arrayOfInt = new int[paramArrayOfColor.length];
/* 254 */     for (byte b = 0; b < paramArrayOfColor.length; b++) {
/* 255 */       arrayOfInt[b] = colorToIntArgbPixel(paramArrayOfColor[b]);
/*     */     }
/* 257 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   public int colorToIntArgbPixel(Color paramColor) {
/* 261 */     int i = paramColor.getRGB();
/* 262 */     int j = Math.round(xrCompMan.getExtraAlpha() * (i >>> 24));
/* 263 */     return j << 24 | i & 0xFFFFFF;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRPaints.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */