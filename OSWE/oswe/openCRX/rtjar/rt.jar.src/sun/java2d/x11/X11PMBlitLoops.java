/*     */ package sun.java2d.x11;
/*     */ 
/*     */ import java.awt.Composite;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.Blit;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.GraphicsPrimitive;
/*     */ import sun.java2d.loops.GraphicsPrimitiveMgr;
/*     */ import sun.java2d.loops.SurfaceType;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X11PMBlitLoops
/*     */   extends Blit
/*     */ {
/*     */   public static void register() {
/*  54 */     GraphicsPrimitive[] arrayOfGraphicsPrimitive = { new X11PMBlitLoops(X11SurfaceData.IntBgrX11, X11SurfaceData.IntBgrX11, false), new X11PMBlitLoops(X11SurfaceData.IntRgbX11, X11SurfaceData.IntRgbX11, false), new X11PMBlitLoops(X11SurfaceData.ThreeByteBgrX11, X11SurfaceData.ThreeByteBgrX11, false), new X11PMBlitLoops(X11SurfaceData.ThreeByteRgbX11, X11SurfaceData.ThreeByteRgbX11, false), new X11PMBlitLoops(X11SurfaceData.ByteIndexedOpaqueX11, X11SurfaceData.ByteIndexedOpaqueX11, false), new X11PMBlitLoops(X11SurfaceData.ByteGrayX11, X11SurfaceData.ByteGrayX11, false), new X11PMBlitLoops(X11SurfaceData.Index8GrayX11, X11SurfaceData.Index8GrayX11, false), new X11PMBlitLoops(X11SurfaceData.UShort555RgbX11, X11SurfaceData.UShort555RgbX11, false), new X11PMBlitLoops(X11SurfaceData.UShort565RgbX11, X11SurfaceData.UShort565RgbX11, false), new X11PMBlitLoops(X11SurfaceData.UShortIndexedX11, X11SurfaceData.UShortIndexedX11, false), new X11PMBlitLoops(X11SurfaceData.IntBgrX11_BM, X11SurfaceData.IntBgrX11, true), new X11PMBlitLoops(X11SurfaceData.IntRgbX11_BM, X11SurfaceData.IntRgbX11, true), new X11PMBlitLoops(X11SurfaceData.ThreeByteBgrX11_BM, X11SurfaceData.ThreeByteBgrX11, true), new X11PMBlitLoops(X11SurfaceData.ThreeByteRgbX11_BM, X11SurfaceData.ThreeByteRgbX11, true), new X11PMBlitLoops(X11SurfaceData.ByteIndexedX11_BM, X11SurfaceData.ByteIndexedOpaqueX11, true), new X11PMBlitLoops(X11SurfaceData.ByteGrayX11_BM, X11SurfaceData.ByteGrayX11, true), new X11PMBlitLoops(X11SurfaceData.Index8GrayX11_BM, X11SurfaceData.Index8GrayX11, true), new X11PMBlitLoops(X11SurfaceData.UShort555RgbX11_BM, X11SurfaceData.UShort555RgbX11, true), new X11PMBlitLoops(X11SurfaceData.UShort565RgbX11_BM, X11SurfaceData.UShort565RgbX11, true), new X11PMBlitLoops(X11SurfaceData.UShortIndexedX11_BM, X11SurfaceData.UShortIndexedX11, true), new X11PMBlitLoops(X11SurfaceData.IntRgbX11, X11SurfaceData.IntArgbPreX11, true), new X11PMBlitLoops(X11SurfaceData.IntRgbX11, X11SurfaceData.IntArgbPreX11, false), new X11PMBlitLoops(X11SurfaceData.IntRgbX11_BM, X11SurfaceData.IntArgbPreX11, true), new X11PMBlitLoops(X11SurfaceData.IntBgrX11, X11SurfaceData.FourByteAbgrPreX11, true), new X11PMBlitLoops(X11SurfaceData.IntBgrX11, X11SurfaceData.FourByteAbgrPreX11, false), new X11PMBlitLoops(X11SurfaceData.IntBgrX11_BM, X11SurfaceData.FourByteAbgrPreX11, true), new DelegateBlitLoop(X11SurfaceData.IntBgrX11_BM, X11SurfaceData.IntBgrX11), new DelegateBlitLoop(X11SurfaceData.IntRgbX11_BM, X11SurfaceData.IntRgbX11), new DelegateBlitLoop(X11SurfaceData.ThreeByteBgrX11_BM, X11SurfaceData.ThreeByteBgrX11), new DelegateBlitLoop(X11SurfaceData.ThreeByteRgbX11_BM, X11SurfaceData.ThreeByteRgbX11), new DelegateBlitLoop(X11SurfaceData.ByteIndexedX11_BM, X11SurfaceData.ByteIndexedOpaqueX11), new DelegateBlitLoop(X11SurfaceData.ByteGrayX11_BM, X11SurfaceData.ByteGrayX11), new DelegateBlitLoop(X11SurfaceData.Index8GrayX11_BM, X11SurfaceData.Index8GrayX11), new DelegateBlitLoop(X11SurfaceData.UShort555RgbX11_BM, X11SurfaceData.UShort555RgbX11), new DelegateBlitLoop(X11SurfaceData.UShort565RgbX11_BM, X11SurfaceData.UShort565RgbX11), new DelegateBlitLoop(X11SurfaceData.UShortIndexedX11_BM, X11SurfaceData.UShortIndexedX11) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     GraphicsPrimitiveMgr.register(arrayOfGraphicsPrimitive);
/*     */   }
/*     */ 
/*     */   
/*     */   public X11PMBlitLoops(SurfaceType paramSurfaceType1, SurfaceType paramSurfaceType2, boolean paramBoolean) {
/* 142 */     super(paramSurfaceType1, paramBoolean ? CompositeType.SrcOverNoEa : CompositeType.SrcNoEa, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Blit(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Composite paramComposite, Region paramRegion, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 153 */     SunToolkit.awtLock();
/*     */     try {
/* 155 */       X11SurfaceData x11SurfaceData = (X11SurfaceData)paramSurfaceData2;
/*     */ 
/*     */       
/* 158 */       long l = x11SurfaceData.getBlitGC(null, false);
/* 159 */       nativeBlit(paramSurfaceData1.getNativeOps(), paramSurfaceData2.getNativeOps(), l, paramRegion, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     } finally {
/*     */       
/* 162 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native void nativeBlit(long paramLong1, long paramLong2, long paramLong3, Region paramRegion, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void updateBitmask(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class DelegateBlitLoop
/*     */     extends Blit
/*     */   {
/*     */     SurfaceType dstType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DelegateBlitLoop(SurfaceType param1SurfaceType1, SurfaceType param1SurfaceType2) {
/* 193 */       super(SurfaceType.Any, CompositeType.SrcNoEa, param1SurfaceType1);
/* 194 */       this.dstType = param1SurfaceType2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void Blit(SurfaceData param1SurfaceData1, SurfaceData param1SurfaceData2, Composite param1Composite, Region param1Region, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/* 201 */       Blit blit = Blit.getFromCache(param1SurfaceData1.getSurfaceType(), CompositeType.SrcNoEa, this.dstType);
/*     */ 
/*     */       
/* 204 */       blit.Blit(param1SurfaceData1, param1SurfaceData2, param1Composite, param1Region, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
/* 205 */       X11PMBlitLoops.updateBitmask(param1SurfaceData1, param1SurfaceData2, param1SurfaceData1
/* 206 */           .getColorModel() instanceof java.awt.image.IndexColorModel);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/x11/X11PMBlitLoops.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */