/*    */ package sun.java2d.xr;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Transparency;
/*    */ import sun.java2d.SurfaceData;
/*    */ import sun.java2d.SurfaceDataProxy;
/*    */ import sun.java2d.loops.CompositeType;
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
/*    */ public class XRSurfaceDataProxy
/*    */   extends SurfaceDataProxy
/*    */   implements Transparency
/*    */ {
/*    */   XRGraphicsConfig xrgc;
/*    */   int transparency;
/*    */   
/*    */   public static SurfaceDataProxy createProxy(SurfaceData paramSurfaceData, XRGraphicsConfig paramXRGraphicsConfig) {
/* 44 */     if (paramSurfaceData instanceof XRSurfaceData) {
/* 45 */       return UNCACHED;
/*    */     }
/*    */     
/* 48 */     return new XRSurfaceDataProxy(paramXRGraphicsConfig, paramSurfaceData.getTransparency());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XRSurfaceDataProxy(XRGraphicsConfig paramXRGraphicsConfig) {
/* 55 */     this.xrgc = paramXRGraphicsConfig;
/*    */   }
/*    */ 
/*    */   
/*    */   public SurfaceData validateSurfaceData(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, int paramInt1, int paramInt2) {
/*    */     XRSurfaceData.XRPixmapSurfaceData xRPixmapSurfaceData;
/* 61 */     if (paramSurfaceData2 == null) {
/*    */       try {
/* 63 */         xRPixmapSurfaceData = XRSurfaceData.createData(this.xrgc, paramInt1, paramInt2, this.xrgc
/* 64 */             .getColorModel(), null, 0L, 
/* 65 */             getTransparency());
/* 66 */       } catch (OutOfMemoryError outOfMemoryError) {}
/*    */     }
/*    */     
/* 69 */     return (SurfaceData)xRPixmapSurfaceData;
/*    */   }
/*    */   
/*    */   public XRSurfaceDataProxy(XRGraphicsConfig paramXRGraphicsConfig, int paramInt) {
/* 73 */     this.xrgc = paramXRGraphicsConfig;
/* 74 */     this.transparency = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSupportedOperation(SurfaceData paramSurfaceData, int paramInt, CompositeType paramCompositeType, Color paramColor) {
/* 81 */     return (paramColor == null || this.transparency == 3);
/*    */   }
/*    */   
/*    */   public int getTransparency() {
/* 85 */     return this.transparency;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRSurfaceDataProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */