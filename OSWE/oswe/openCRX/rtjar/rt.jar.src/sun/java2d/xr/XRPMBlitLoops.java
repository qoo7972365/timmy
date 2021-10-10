/*     */ package sun.java2d.xr;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.lang.ref.WeakReference;
/*     */ import sun.awt.image.SunVolatileImage;
/*     */ import sun.java2d.InvalidPipeException;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.Blit;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.GraphicsPrimitive;
/*     */ import sun.java2d.loops.GraphicsPrimitiveMgr;
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
/*     */ public class XRPMBlitLoops
/*     */ {
/*  39 */   static WeakReference<SunVolatileImage> argbTmpPM = new WeakReference<>(null);
/*  40 */   static WeakReference<SunVolatileImage> rgbTmpPM = new WeakReference<>(null);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() {
/*  46 */     GraphicsPrimitive[] arrayOfGraphicsPrimitive = { new XRPMBlit(XRSurfaceData.IntRgbX11, XRSurfaceData.IntRgbX11), new XRPMBlit(XRSurfaceData.IntRgbX11, XRSurfaceData.IntArgbPreX11), new XRPMBlit(XRSurfaceData.IntArgbPreX11, XRSurfaceData.IntRgbX11), new XRPMBlit(XRSurfaceData.IntArgbPreX11, XRSurfaceData.IntArgbPreX11), new XRPMScaledBlit(XRSurfaceData.IntRgbX11, XRSurfaceData.IntRgbX11), new XRPMScaledBlit(XRSurfaceData.IntRgbX11, XRSurfaceData.IntArgbPreX11), new XRPMScaledBlit(XRSurfaceData.IntArgbPreX11, XRSurfaceData.IntRgbX11), new XRPMScaledBlit(XRSurfaceData.IntArgbPreX11, XRSurfaceData.IntArgbPreX11), new XRPMTransformedBlit(XRSurfaceData.IntRgbX11, XRSurfaceData.IntRgbX11), new XRPMTransformedBlit(XRSurfaceData.IntRgbX11, XRSurfaceData.IntArgbPreX11), new XRPMTransformedBlit(XRSurfaceData.IntArgbPreX11, XRSurfaceData.IntRgbX11), new XRPMTransformedBlit(XRSurfaceData.IntArgbPreX11, XRSurfaceData.IntArgbPreX11), new XrSwToPMBlit(SurfaceType.IntArgb, XRSurfaceData.IntRgbX11), new XrSwToPMBlit(SurfaceType.IntRgb, XRSurfaceData.IntRgbX11), new XrSwToPMBlit(SurfaceType.IntBgr, XRSurfaceData.IntRgbX11), new XrSwToPMBlit(SurfaceType.ThreeByteBgr, XRSurfaceData.IntRgbX11), new XrSwToPMBlit(SurfaceType.Ushort565Rgb, XRSurfaceData.IntRgbX11), new XrSwToPMBlit(SurfaceType.Ushort555Rgb, XRSurfaceData.IntRgbX11), new XrSwToPMBlit(SurfaceType.ByteIndexed, XRSurfaceData.IntRgbX11), new XrSwToPMBlit(SurfaceType.IntArgb, XRSurfaceData.IntArgbPreX11), new XrSwToPMBlit(SurfaceType.IntRgb, XRSurfaceData.IntArgbPreX11), new XrSwToPMBlit(SurfaceType.IntBgr, XRSurfaceData.IntArgbPreX11), new XrSwToPMBlit(SurfaceType.ThreeByteBgr, XRSurfaceData.IntArgbPreX11), new XrSwToPMBlit(SurfaceType.Ushort565Rgb, XRSurfaceData.IntArgbPreX11), new XrSwToPMBlit(SurfaceType.Ushort555Rgb, XRSurfaceData.IntArgbPreX11), new XrSwToPMBlit(SurfaceType.ByteIndexed, XRSurfaceData.IntArgbPreX11), new XrSwToPMScaledBlit(SurfaceType.IntArgb, XRSurfaceData.IntRgbX11), new XrSwToPMScaledBlit(SurfaceType.IntRgb, XRSurfaceData.IntRgbX11), new XrSwToPMScaledBlit(SurfaceType.IntBgr, XRSurfaceData.IntRgbX11), new XrSwToPMScaledBlit(SurfaceType.ThreeByteBgr, XRSurfaceData.IntRgbX11), new XrSwToPMScaledBlit(SurfaceType.Ushort565Rgb, XRSurfaceData.IntRgbX11), new XrSwToPMScaledBlit(SurfaceType.Ushort555Rgb, XRSurfaceData.IntRgbX11), new XrSwToPMScaledBlit(SurfaceType.ByteIndexed, XRSurfaceData.IntRgbX11), new XrSwToPMScaledBlit(SurfaceType.IntArgb, XRSurfaceData.IntArgbPreX11), new XrSwToPMScaledBlit(SurfaceType.IntRgb, XRSurfaceData.IntArgbPreX11), new XrSwToPMScaledBlit(SurfaceType.IntBgr, XRSurfaceData.IntArgbPreX11), new XrSwToPMScaledBlit(SurfaceType.ThreeByteBgr, XRSurfaceData.IntArgbPreX11), new XrSwToPMScaledBlit(SurfaceType.Ushort565Rgb, XRSurfaceData.IntArgbPreX11), new XrSwToPMScaledBlit(SurfaceType.Ushort555Rgb, XRSurfaceData.IntArgbPreX11), new XrSwToPMScaledBlit(SurfaceType.ByteIndexed, XRSurfaceData.IntArgbPreX11), new XrSwToPMTransformedBlit(SurfaceType.IntArgb, XRSurfaceData.IntRgbX11), new XrSwToPMTransformedBlit(SurfaceType.IntRgb, XRSurfaceData.IntRgbX11), new XrSwToPMTransformedBlit(SurfaceType.IntBgr, XRSurfaceData.IntRgbX11), new XrSwToPMTransformedBlit(SurfaceType.ThreeByteBgr, XRSurfaceData.IntRgbX11), new XrSwToPMTransformedBlit(SurfaceType.Ushort565Rgb, XRSurfaceData.IntRgbX11), new XrSwToPMTransformedBlit(SurfaceType.Ushort555Rgb, XRSurfaceData.IntRgbX11), new XrSwToPMTransformedBlit(SurfaceType.ByteIndexed, XRSurfaceData.IntRgbX11), new XrSwToPMTransformedBlit(SurfaceType.IntArgb, XRSurfaceData.IntArgbPreX11), new XrSwToPMTransformedBlit(SurfaceType.IntRgb, XRSurfaceData.IntArgbPreX11), new XrSwToPMTransformedBlit(SurfaceType.IntBgr, XRSurfaceData.IntArgbPreX11), new XrSwToPMTransformedBlit(SurfaceType.ThreeByteBgr, XRSurfaceData.IntArgbPreX11), new XrSwToPMTransformedBlit(SurfaceType.Ushort565Rgb, XRSurfaceData.IntArgbPreX11), new XrSwToPMTransformedBlit(SurfaceType.Ushort555Rgb, XRSurfaceData.IntArgbPreX11), new XrSwToPMTransformedBlit(SurfaceType.ByteIndexed, XRSurfaceData.IntArgbPreX11) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     GraphicsPrimitiveMgr.register(arrayOfGraphicsPrimitive);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static XRSurfaceData cacheToTmpSurface(SurfaceData paramSurfaceData, XRSurfaceData paramXRSurfaceData, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*     */     SunVolatileImage sunVolatileImage;
/*     */     SurfaceType surfaceType;
/* 122 */     if (paramSurfaceData.getTransparency() == 1) {
/* 123 */       sunVolatileImage = rgbTmpPM.get();
/* 124 */       surfaceType = SurfaceType.IntRgb;
/*     */     } else {
/* 126 */       sunVolatileImage = argbTmpPM.get();
/* 127 */       surfaceType = SurfaceType.IntArgbPre;
/*     */     } 
/*     */     
/* 130 */     if (sunVolatileImage == null || sunVolatileImage.getWidth() < paramInt1 || sunVolatileImage.getHeight() < paramInt2 || 
/*     */ 
/*     */       
/* 133 */       !(sunVolatileImage.getDestSurface() instanceof XRSurfaceData)) {
/*     */       
/* 135 */       if (sunVolatileImage != null) {
/* 136 */         sunVolatileImage.flush();
/*     */       }
/* 138 */       sunVolatileImage = (SunVolatileImage)paramXRSurfaceData.getGraphicsConfig().createCompatibleVolatileImage(paramInt1, paramInt2, paramSurfaceData.getTransparency());
/* 139 */       sunVolatileImage.setAccelerationPriority(1.0F);
/*     */       
/* 141 */       if (!(sunVolatileImage.getDestSurface() instanceof XRSurfaceData)) {
/* 142 */         throw new InvalidPipeException("Could not create XRSurfaceData");
/*     */       }
/* 144 */       if (paramSurfaceData.getTransparency() == 1) {
/* 145 */         rgbTmpPM = new WeakReference<>(sunVolatileImage);
/*     */       } else {
/* 147 */         argbTmpPM = new WeakReference<>(sunVolatileImage);
/*     */       } 
/*     */     } 
/*     */     
/* 151 */     Blit blit = Blit.getFromCache(paramSurfaceData.getSurfaceType(), CompositeType.SrcNoEa, surfaceType);
/*     */     
/* 153 */     if (!(sunVolatileImage.getDestSurface() instanceof XRSurfaceData)) {
/* 154 */       throw new InvalidPipeException("wrong surface data type: " + sunVolatileImage.getDestSurface());
/*     */     }
/*     */     
/* 157 */     XRSurfaceData xRSurfaceData = (XRSurfaceData)sunVolatileImage.getDestSurface();
/* 158 */     blit.Blit(paramSurfaceData, (SurfaceData)xRSurfaceData, AlphaComposite.Src, null, paramInt3, paramInt4, 0, 0, paramInt1, paramInt2);
/*     */ 
/*     */     
/* 161 */     return xRSurfaceData;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRPMBlitLoops.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */