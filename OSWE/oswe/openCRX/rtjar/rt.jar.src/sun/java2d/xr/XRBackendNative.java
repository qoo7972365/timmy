/*     */ package sun.java2d.xr;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.util.List;
/*     */ import sun.font.GlyphList;
/*     */ import sun.font.XRGlyphCacheEntry;
/*     */ import sun.java2d.jules.TrapezoidList;
/*     */ import sun.java2d.pipe.Region;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XRBackendNative
/*     */   implements XRBackend
/*     */ {
/*     */   private static long FMTPTR_A8;
/*     */   private static long FMTPTR_ARGB32;
/*     */   private static long MASK_XIMG;
/*     */   
/*     */   static {
/*  47 */     initIDs();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void GCRectangles(int paramInt, long paramLong, GrowableRectArray paramGrowableRectArray) {
/* 124 */     GCRectanglesNative(paramInt, paramLong, paramGrowableRectArray.getArray(), paramGrowableRectArray.getSize());
/*     */   }
/*     */   
/*     */   public int createPicture(int paramInt1, int paramInt2) {
/* 128 */     return createPictureNative(paramInt1, getFormatPtr(paramInt2));
/*     */   }
/*     */   
/*     */   public void setPictureTransform(int paramInt, AffineTransform paramAffineTransform) {
/* 132 */     XRSetTransformNative(paramInt, 
/* 133 */         XRUtils.XDoubleToFixed(paramAffineTransform.getScaleX()), 
/* 134 */         XRUtils.XDoubleToFixed(paramAffineTransform.getShearX()), 
/* 135 */         XRUtils.XDoubleToFixed(paramAffineTransform.getTranslateX()), 
/* 136 */         XRUtils.XDoubleToFixed(paramAffineTransform.getShearY()), 
/* 137 */         XRUtils.XDoubleToFixed(paramAffineTransform.getScaleY()), 
/* 138 */         XRUtils.XDoubleToFixed(paramAffineTransform.getTranslateY()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderRectangle(int paramInt1, byte paramByte, XRColor paramXRColor, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 143 */     renderRectangle(paramInt1, paramByte, (short)paramXRColor.red, (short)paramXRColor.green, (short)paramXRColor.blue, (short)paramXRColor.alpha, paramInt2, paramInt3, paramInt4, paramInt5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private short[] getRenderColors(int[] paramArrayOfint) {
/* 149 */     short[] arrayOfShort = new short[paramArrayOfint.length * 4];
/*     */     
/* 151 */     XRColor xRColor = new XRColor();
/* 152 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 153 */       xRColor.setColorValues(paramArrayOfint[b], true);
/* 154 */       arrayOfShort[b * 4 + 0] = (short)xRColor.alpha;
/* 155 */       arrayOfShort[b * 4 + 1] = (short)xRColor.red;
/* 156 */       arrayOfShort[b * 4 + 2] = (short)xRColor.green;
/* 157 */       arrayOfShort[b * 4 + 3] = (short)xRColor.blue;
/*     */     } 
/*     */     
/* 160 */     return arrayOfShort;
/*     */   }
/*     */   
/*     */   private static long getFormatPtr(int paramInt) {
/* 164 */     switch (paramInt) {
/*     */       case 2:
/* 166 */         return FMTPTR_A8;
/*     */       case 0:
/* 168 */         return FMTPTR_ARGB32;
/*     */     } 
/*     */     
/* 171 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int createLinearGradient(Point2D paramPoint2D1, Point2D paramPoint2D2, float[] paramArrayOffloat, int[] paramArrayOfint, int paramInt) {
/* 177 */     short[] arrayOfShort = getRenderColors(paramArrayOfint);
/*     */     
/* 179 */     return XRCreateLinearGradientPaintNative(paramArrayOffloat, arrayOfShort, 
/* 180 */         XRUtils.XDoubleToFixed(paramPoint2D1.getX()), XRUtils.XDoubleToFixed(paramPoint2D1.getY()), 
/* 181 */         XRUtils.XDoubleToFixed(paramPoint2D2.getX()), XRUtils.XDoubleToFixed(paramPoint2D2.getY()), paramArrayOffloat.length, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int createRadialGradient(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOffloat, int[] paramArrayOfint, int paramInt) {
/* 190 */     short[] arrayOfShort = getRenderColors(paramArrayOfint);
/* 191 */     return 
/* 192 */       XRCreateRadialGradientPaintNative(paramArrayOffloat, arrayOfShort, paramArrayOffloat.length, 
/* 193 */         XRUtils.XDoubleToFixed(paramFloat1), 
/* 194 */         XRUtils.XDoubleToFixed(paramFloat2), 
/* 195 */         XRUtils.XDoubleToFixed(paramFloat3), 
/* 196 */         XRUtils.XDoubleToFixed(paramFloat4), paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGCClipRectangles(long paramLong, Region paramRegion) {
/* 201 */     XRSetClipNative(paramLong, paramRegion.getLoX(), paramRegion.getLoY(), paramRegion
/* 202 */         .getHiX(), paramRegion.getHiY(), 
/* 203 */         paramRegion.isRectangular() ? null : paramRegion, true);
/*     */   }
/*     */   
/*     */   public void setClipRectangles(int paramInt, Region paramRegion) {
/* 207 */     if (paramRegion != null) {
/* 208 */       XRSetClipNative(paramInt, paramRegion.getLoX(), paramRegion.getLoY(), paramRegion
/* 209 */           .getHiX(), paramRegion.getHiY(), 
/* 210 */           paramRegion.isRectangular() ? null : paramRegion, false);
/*     */     } else {
/* 212 */       XRSetClipNative(paramInt, 0, 0, 32767, 32767, null, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderRectangles(int paramInt, byte paramByte, XRColor paramXRColor, GrowableRectArray paramGrowableRectArray) {
/* 218 */     XRenderRectanglesNative(paramInt, paramByte, (short)paramXRColor.red, (short)paramXRColor.green, (short)paramXRColor.blue, (short)paramXRColor.alpha, paramGrowableRectArray
/*     */ 
/*     */         
/* 221 */         .getArray(), paramGrowableRectArray
/* 222 */         .getSize());
/*     */   }
/*     */   
/*     */   private static long[] getGlyphInfoPtrs(List<XRGlyphCacheEntry> paramList) {
/* 226 */     long[] arrayOfLong = new long[paramList.size()];
/* 227 */     for (byte b = 0; b < paramList.size(); b++) {
/* 228 */       arrayOfLong[b] = ((XRGlyphCacheEntry)paramList.get(b)).getGlyphInfoPtr();
/*     */     }
/* 230 */     return arrayOfLong;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void XRenderAddGlyphs(int paramInt, GlyphList paramGlyphList, List<XRGlyphCacheEntry> paramList, byte[] paramArrayOfbyte) {
/* 236 */     long[] arrayOfLong = getGlyphInfoPtrs(paramList);
/* 237 */     XRAddGlyphsNative(paramInt, arrayOfLong, arrayOfLong.length, paramArrayOfbyte, paramArrayOfbyte.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void XRenderFreeGlyphs(int paramInt, int[] paramArrayOfint) {
/* 242 */     XRFreeGlyphsNative(paramInt, paramArrayOfint, paramArrayOfint.length);
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
/*     */   public int XRenderCreateGlyphSet(int paramInt) {
/* 261 */     return XRenderCreateGlyphSetNative(getFormatPtr(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void XRenderCompositeText(byte paramByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, GrowableEltArray paramGrowableEltArray) {
/* 271 */     GrowableIntArray growableIntArray = paramGrowableEltArray.getGlyphs();
/* 272 */     XRenderCompositeTextNative(paramByte, paramInt1, paramInt2, paramInt4, paramInt5, 0L, paramGrowableEltArray.getArray(), growableIntArray
/* 273 */         .getArray(), paramGrowableEltArray.getSize(), growableIntArray
/* 274 */         .getSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putMaskImage(int paramInt1, long paramLong, byte[] paramArrayOfbyte, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, float paramFloat) {
/* 281 */     putMaskNative(paramInt1, paramLong, paramArrayOfbyte, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramFloat, MASK_XIMG);
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
/*     */   public void padBlit(byte paramByte, int paramInt1, int paramInt2, int paramInt3, AffineTransform paramAffineTransform, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13) {
/* 297 */     padBlitNative(paramByte, paramInt1, paramInt2, paramInt3, 
/* 298 */         XRUtils.XDoubleToFixed(paramAffineTransform.getScaleX()), 
/* 299 */         XRUtils.XDoubleToFixed(paramAffineTransform.getShearX()), 
/* 300 */         XRUtils.XDoubleToFixed(paramAffineTransform.getTranslateX()), 
/* 301 */         XRUtils.XDoubleToFixed(paramAffineTransform.getShearY()), 
/* 302 */         XRUtils.XDoubleToFixed(paramAffineTransform.getScaleY()), 
/* 303 */         XRUtils.XDoubleToFixed(paramAffineTransform.getTranslateY()), paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10, paramInt11, paramInt12, paramInt13);
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
/*     */   public void renderCompositeTrapezoids(byte paramByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, TrapezoidList paramTrapezoidList) {
/* 321 */     renderCompositeTrapezoidsNative(paramByte, paramInt1, getFormatPtr(paramInt2), paramInt3, paramInt4, paramInt5, paramTrapezoidList
/*     */         
/* 323 */         .getTrapArray());
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   public native long createGC(int paramInt);
/*     */   
/*     */   public native void freeGC(long paramLong);
/*     */   
/*     */   public native int createPixmap(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   private native int createPictureNative(int paramInt, long paramLong);
/*     */   
/*     */   public native void freePicture(int paramInt);
/*     */   
/*     */   public native void freePixmap(int paramInt);
/*     */   
/*     */   public native void setGCExposures(long paramLong, boolean paramBoolean);
/*     */   
/*     */   public native void setGCForeground(long paramLong, int paramInt);
/*     */   
/*     */   public native void setPictureRepeat(int paramInt1, int paramInt2);
/*     */   
/*     */   public native void copyArea(int paramInt1, int paramInt2, long paramLong, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);
/*     */   
/*     */   public native void setGCMode(long paramLong, boolean paramBoolean);
/*     */   
/*     */   private static native void GCRectanglesNative(int paramInt1, long paramLong, int[] paramArrayOfint, int paramInt2);
/*     */   
/*     */   public native void renderComposite(byte paramByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11);
/*     */   
/*     */   private native void renderRectangle(int paramInt1, byte paramByte, short paramShort1, short paramShort2, short paramShort3, short paramShort4, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */   
/*     */   private static native void XRenderRectanglesNative(int paramInt1, byte paramByte, short paramShort1, short paramShort2, short paramShort3, short paramShort4, int[] paramArrayOfint, int paramInt2);
/*     */   
/*     */   private native void XRSetTransformNative(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
/*     */   
/*     */   private static native int XRCreateLinearGradientPaintNative(float[] paramArrayOffloat, short[] paramArrayOfshort, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */   
/*     */   private static native int XRCreateRadialGradientPaintNative(float[] paramArrayOffloat, short[] paramArrayOfshort, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */   
/*     */   public native void setFilter(int paramInt1, int paramInt2);
/*     */   
/*     */   private static native void XRSetClipNative(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Region paramRegion, boolean paramBoolean);
/*     */   
/*     */   private static native void XRAddGlyphsNative(int paramInt1, long[] paramArrayOflong, int paramInt2, byte[] paramArrayOfbyte, int paramInt3);
/*     */   
/*     */   private static native void XRFreeGlyphsNative(int paramInt1, int[] paramArrayOfint, int paramInt2);
/*     */   
/*     */   private static native void XRenderCompositeTextNative(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, long paramLong, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt6, int paramInt7);
/*     */   
/*     */   private static native int XRenderCreateGlyphSetNative(long paramLong);
/*     */   
/*     */   private static native void putMaskNative(int paramInt1, long paramLong1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, float paramFloat, long paramLong2);
/*     */   
/*     */   private static native void padBlitNative(byte paramByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13, int paramInt14, int paramInt15, int paramInt16, int paramInt17, int paramInt18, int paramInt19);
/*     */   
/*     */   private static native void renderCompositeTrapezoidsNative(byte paramByte, int paramInt1, long paramLong, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRBackendNative.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */