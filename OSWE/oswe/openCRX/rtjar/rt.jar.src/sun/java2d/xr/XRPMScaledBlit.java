/*     */ package sun.java2d.xr;
/*     */ 
/*     */ import java.awt.Composite;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.ScaledBlit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class XRPMScaledBlit
/*     */   extends ScaledBlit
/*     */ {
/*     */   public XRPMScaledBlit(SurfaceType paramSurfaceType1, SurfaceType paramSurfaceType2) {
/* 190 */     super(paramSurfaceType1, CompositeType.AnyAlpha, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void Scale(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Composite paramComposite, Region paramRegion, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*     */     try {
/* 196 */       SunToolkit.awtLock();
/*     */       
/* 198 */       XRSurfaceData xRSurfaceData1 = (XRSurfaceData)paramSurfaceData2;
/* 199 */       xRSurfaceData1.validateAsDestination(null, paramRegion);
/* 200 */       XRSurfaceData xRSurfaceData2 = (XRSurfaceData)paramSurfaceData1;
/* 201 */       xRSurfaceData1.maskBuffer.validateCompositeState(paramComposite, null, null, null);
/*     */       
/* 203 */       double d1 = (paramDouble3 - paramDouble1) / (paramInt3 - paramInt1);
/* 204 */       double d2 = (paramDouble4 - paramDouble2) / (paramInt4 - paramInt2);
/*     */       
/* 206 */       paramInt1 = (int)(paramInt1 * d1);
/* 207 */       paramInt3 = (int)(paramInt3 * d1);
/* 208 */       paramInt2 = (int)(paramInt2 * d2);
/* 209 */       paramInt4 = (int)(paramInt4 * d2);
/*     */       
/* 211 */       paramDouble1 = Math.ceil(paramDouble1 - 0.5D);
/* 212 */       paramDouble2 = Math.ceil(paramDouble2 - 0.5D);
/* 213 */       paramDouble3 = Math.ceil(paramDouble3 - 0.5D);
/* 214 */       paramDouble4 = Math.ceil(paramDouble4 - 0.5D);
/*     */       
/* 216 */       AffineTransform affineTransform = AffineTransform.getScaleInstance(1.0D / d1, 1.0D / d2);
/*     */       
/* 218 */       xRSurfaceData2.validateAsSource(affineTransform, 0, 0);
/* 219 */       xRSurfaceData1.maskBuffer.compositeBlit(xRSurfaceData2, xRSurfaceData1, paramInt1, paramInt2, (int)paramDouble1, (int)paramDouble2, (int)(paramDouble3 - paramDouble1), (int)(paramDouble4 - paramDouble2));
/*     */     } finally {
/* 221 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRPMScaledBlit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */