/*     */ package sun.java2d.x11;
/*     */ 
/*     */ import java.awt.Composite;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.BlitBg;
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
/*     */ public class X11PMBlitBgLoops
/*     */   extends BlitBg
/*     */ {
/*     */   public static void register() {
/*  52 */     GraphicsPrimitive[] arrayOfGraphicsPrimitive = { new X11PMBlitBgLoops(X11SurfaceData.IntBgrX11_BM, X11SurfaceData.IntBgrX11), new X11PMBlitBgLoops(X11SurfaceData.IntRgbX11_BM, X11SurfaceData.IntRgbX11), new X11PMBlitBgLoops(X11SurfaceData.ThreeByteBgrX11_BM, X11SurfaceData.ThreeByteBgrX11), new X11PMBlitBgLoops(X11SurfaceData.ThreeByteRgbX11_BM, X11SurfaceData.ThreeByteRgbX11), new X11PMBlitBgLoops(X11SurfaceData.ByteIndexedX11_BM, X11SurfaceData.ByteIndexedOpaqueX11), new X11PMBlitBgLoops(X11SurfaceData.ByteGrayX11_BM, X11SurfaceData.ByteGrayX11), new X11PMBlitBgLoops(X11SurfaceData.Index8GrayX11_BM, X11SurfaceData.Index8GrayX11), new X11PMBlitBgLoops(X11SurfaceData.UShort555RgbX11_BM, X11SurfaceData.UShort555RgbX11), new X11PMBlitBgLoops(X11SurfaceData.UShort565RgbX11_BM, X11SurfaceData.UShort565RgbX11), new X11PMBlitBgLoops(X11SurfaceData.UShortIndexedX11_BM, X11SurfaceData.UShortIndexedX11), new X11PMBlitBgLoops(X11SurfaceData.IntRgbX11_BM, X11SurfaceData.IntArgbPreX11), new X11PMBlitBgLoops(X11SurfaceData.IntBgrX11_BM, X11SurfaceData.FourByteAbgrPreX11) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     GraphicsPrimitiveMgr.register(arrayOfGraphicsPrimitive);
/*     */   }
/*     */ 
/*     */   
/*     */   public X11PMBlitBgLoops(SurfaceType paramSurfaceType1, SurfaceType paramSurfaceType2) {
/*  83 */     super(paramSurfaceType1, CompositeType.SrcNoEa, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void BlitBg(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Composite paramComposite, Region paramRegion, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/*  93 */     SunToolkit.awtLock();
/*     */     try {
/*  95 */       int i = paramSurfaceData2.pixelFor(paramInt1);
/*  96 */       X11SurfaceData x11SurfaceData = (X11SurfaceData)paramSurfaceData2;
/*     */       
/*  98 */       long l = x11SurfaceData.getBlitGC(paramRegion, false);
/*  99 */       nativeBlitBg(paramSurfaceData1.getNativeOps(), paramSurfaceData2.getNativeOps(), l, i, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*     */     }
/*     */     finally {
/*     */       
/* 103 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private native void nativeBlitBg(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/x11/X11PMBlitBgLoops.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */