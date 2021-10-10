/*    */ package sun.java2d.xr;
/*    */ 
/*    */ import java.awt.Composite;
/*    */ import sun.awt.SunToolkit;
/*    */ import sun.java2d.SurfaceData;
/*    */ import sun.java2d.loops.CompositeType;
/*    */ import sun.java2d.loops.GraphicsPrimitive;
/*    */ import sun.java2d.loops.GraphicsPrimitiveMgr;
/*    */ import sun.java2d.loops.MaskBlit;
/*    */ import sun.java2d.loops.SurfaceType;
/*    */ import sun.java2d.pipe.Region;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XRMaskBlit
/*    */   extends MaskBlit
/*    */ {
/*    */   static void register() {
/* 46 */     GraphicsPrimitive[] arrayOfGraphicsPrimitive = { new XRMaskBlit(XRSurfaceData.IntArgbPreX11, CompositeType.SrcOver, XRSurfaceData.IntArgbPreX11), new XRMaskBlit(XRSurfaceData.IntRgbX11, CompositeType.SrcOver, XRSurfaceData.IntRgbX11), new XRMaskBlit(XRSurfaceData.IntArgbPreX11, CompositeType.SrcNoEa, XRSurfaceData.IntRgbX11), new XRMaskBlit(XRSurfaceData.IntRgbX11, CompositeType.SrcNoEa, XRSurfaceData.IntArgbPreX11) };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 56 */     GraphicsPrimitiveMgr.register(arrayOfGraphicsPrimitive);
/*    */   }
/*    */ 
/*    */   
/*    */   public XRMaskBlit(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 61 */     super(paramSurfaceType1, CompositeType.AnyAlpha, paramSurfaceType2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected native void maskBlit(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, byte[] paramArrayOfbyte);
/*    */ 
/*    */ 
/*    */   
/*    */   public void MaskBlit(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Composite paramComposite, Region paramRegion, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, byte[] paramArrayOfbyte, int paramInt7, int paramInt8) {
/* 71 */     if (paramInt5 <= 0 || paramInt6 <= 0) {
/*    */       return;
/*    */     }
/*    */     
/*    */     try {
/* 76 */       SunToolkit.awtLock();
/*    */       
/* 78 */       XRSurfaceData xRSurfaceData1 = (XRSurfaceData)paramSurfaceData1;
/* 79 */       xRSurfaceData1.validateAsSource(null, 0, 0);
/*    */       
/* 81 */       XRCompositeManager xRCompositeManager = xRSurfaceData1.maskBuffer;
/* 82 */       XRSurfaceData xRSurfaceData2 = (XRSurfaceData)paramSurfaceData2;
/* 83 */       xRSurfaceData2.validateAsDestination(null, paramRegion);
/*    */ 
/*    */       
/* 86 */       int i = xRCompositeManager.getMaskBuffer().uploadMask(paramInt5, paramInt6, paramInt8, paramInt7, paramArrayOfbyte);
/* 87 */       xRCompositeManager.XRComposite(xRSurfaceData1.getPicture(), i, xRSurfaceData2.getPicture(), paramInt1, paramInt2, 0, 0, paramInt3, paramInt4, paramInt5, paramInt6);
/*    */       
/* 89 */       xRCompositeManager.getMaskBuffer().clearUploadMask(i, paramInt5, paramInt6);
/*    */     } finally {
/* 91 */       SunToolkit.awtUnlock();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRMaskBlit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */