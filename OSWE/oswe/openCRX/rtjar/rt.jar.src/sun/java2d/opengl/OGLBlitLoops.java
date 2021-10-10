/*     */ package sun.java2d.opengl;
/*     */ 
/*     */ import java.awt.Composite;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.BufferedImageOp;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.GraphicsPrimitive;
/*     */ import sun.java2d.loops.GraphicsPrimitiveMgr;
/*     */ import sun.java2d.loops.SurfaceType;
/*     */ import sun.java2d.pipe.Region;
/*     */ import sun.java2d.pipe.RenderBuffer;
/*     */ import sun.java2d.pipe.RenderQueue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class OGLBlitLoops
/*     */ {
/*     */   private static final int OFFSET_SRCTYPE = 16;
/*     */   private static final int OFFSET_HINT = 8;
/*     */   private static final int OFFSET_TEXTURE = 3;
/*     */   private static final int OFFSET_RTT = 2;
/*     */   private static final int OFFSET_XFORM = 1;
/*     */   private static final int OFFSET_ISOBLIT = 0;
/*     */   
/*     */   static void register() {
/*  53 */     OGLSwToSurfaceBlit oGLSwToSurfaceBlit = new OGLSwToSurfaceBlit(SurfaceType.IntArgbPre, 1);
/*     */ 
/*     */     
/*  56 */     OGLSwToTextureBlit oGLSwToTextureBlit = new OGLSwToTextureBlit(SurfaceType.IntArgbPre, 1);
/*     */ 
/*     */     
/*  59 */     OGLSwToSurfaceTransform oGLSwToSurfaceTransform = new OGLSwToSurfaceTransform(SurfaceType.IntArgbPre, 1);
/*     */ 
/*     */     
/*  62 */     OGLSurfaceToSwBlit oGLSurfaceToSwBlit = new OGLSurfaceToSwBlit(SurfaceType.IntArgbPre, 1);
/*     */ 
/*     */ 
/*     */     
/*  66 */     GraphicsPrimitive[] arrayOfGraphicsPrimitive = { new OGLSurfaceToSurfaceBlit(), new OGLSurfaceToSurfaceScale(), new OGLSurfaceToSurfaceTransform(), new OGLRTTSurfaceToSurfaceBlit(), new OGLRTTSurfaceToSurfaceScale(), new OGLRTTSurfaceToSurfaceTransform(), new OGLSurfaceToSwBlit(SurfaceType.IntArgb, 0), oGLSurfaceToSwBlit, oGLSwToSurfaceBlit, new OGLSwToSurfaceBlit(SurfaceType.IntRgb, 2), new OGLSwToSurfaceBlit(SurfaceType.IntRgbx, 3), new OGLSwToSurfaceBlit(SurfaceType.IntBgr, 4), new OGLSwToSurfaceBlit(SurfaceType.IntBgrx, 5), new OGLSwToSurfaceBlit(SurfaceType.ThreeByteBgr, 11), new OGLSwToSurfaceBlit(SurfaceType.Ushort565Rgb, 6), new OGLSwToSurfaceBlit(SurfaceType.Ushort555Rgb, 7), new OGLSwToSurfaceBlit(SurfaceType.Ushort555Rgbx, 8), new OGLSwToSurfaceBlit(SurfaceType.ByteGray, 9), new OGLSwToSurfaceBlit(SurfaceType.UshortGray, 10), new OGLGeneralBlit(OGLSurfaceData.OpenGLSurface, CompositeType.AnyAlpha, oGLSwToSurfaceBlit), new OGLAnyCompositeBlit(OGLSurfaceData.OpenGLSurface, oGLSurfaceToSwBlit, oGLSurfaceToSwBlit, oGLSwToSurfaceBlit), new OGLAnyCompositeBlit(SurfaceType.Any, null, oGLSurfaceToSwBlit, oGLSwToSurfaceBlit), new OGLSwToSurfaceScale(SurfaceType.IntRgb, 2), new OGLSwToSurfaceScale(SurfaceType.IntRgbx, 3), new OGLSwToSurfaceScale(SurfaceType.IntBgr, 4), new OGLSwToSurfaceScale(SurfaceType.IntBgrx, 5), new OGLSwToSurfaceScale(SurfaceType.ThreeByteBgr, 11), new OGLSwToSurfaceScale(SurfaceType.Ushort565Rgb, 6), new OGLSwToSurfaceScale(SurfaceType.Ushort555Rgb, 7), new OGLSwToSurfaceScale(SurfaceType.Ushort555Rgbx, 8), new OGLSwToSurfaceScale(SurfaceType.ByteGray, 9), new OGLSwToSurfaceScale(SurfaceType.UshortGray, 10), new OGLSwToSurfaceScale(SurfaceType.IntArgbPre, 1), new OGLSwToSurfaceTransform(SurfaceType.IntRgb, 2), new OGLSwToSurfaceTransform(SurfaceType.IntRgbx, 3), new OGLSwToSurfaceTransform(SurfaceType.IntBgr, 4), new OGLSwToSurfaceTransform(SurfaceType.IntBgrx, 5), new OGLSwToSurfaceTransform(SurfaceType.ThreeByteBgr, 11), new OGLSwToSurfaceTransform(SurfaceType.Ushort565Rgb, 6), new OGLSwToSurfaceTransform(SurfaceType.Ushort555Rgb, 7), new OGLSwToSurfaceTransform(SurfaceType.Ushort555Rgbx, 8), new OGLSwToSurfaceTransform(SurfaceType.ByteGray, 9), new OGLSwToSurfaceTransform(SurfaceType.UshortGray, 10), oGLSwToSurfaceTransform, new OGLGeneralTransformedBlit(oGLSwToSurfaceTransform), new OGLTextureToSurfaceBlit(), new OGLTextureToSurfaceScale(), new OGLTextureToSurfaceTransform(), oGLSwToTextureBlit, new OGLSwToTextureBlit(SurfaceType.IntRgb, 2), new OGLSwToTextureBlit(SurfaceType.IntRgbx, 3), new OGLSwToTextureBlit(SurfaceType.IntBgr, 4), new OGLSwToTextureBlit(SurfaceType.IntBgrx, 5), new OGLSwToTextureBlit(SurfaceType.ThreeByteBgr, 11), new OGLSwToTextureBlit(SurfaceType.Ushort565Rgb, 6), new OGLSwToTextureBlit(SurfaceType.Ushort555Rgb, 7), new OGLSwToTextureBlit(SurfaceType.Ushort555Rgbx, 8), new OGLSwToTextureBlit(SurfaceType.ByteGray, 9), new OGLSwToTextureBlit(SurfaceType.UshortGray, 10), new OGLGeneralBlit(OGLSurfaceData.OpenGLTexture, CompositeType.SrcNoEa, oGLSwToTextureBlit) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     GraphicsPrimitiveMgr.register(arrayOfGraphicsPrimitive);
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
/*     */   private static int createPackedParams(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt1, int paramInt2) {
/* 218 */     return paramInt2 << 16 | paramInt1 << 8 | (paramBoolean2 ? 1 : 0) << 3 | (paramBoolean3 ? 1 : 0) << 2 | (paramBoolean4 ? 1 : 0) << 1 | (paramBoolean1 ? 1 : 0) << 0;
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
/*     */   private static void enqueueBlit(RenderQueue paramRenderQueue, SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 240 */     RenderBuffer renderBuffer = paramRenderQueue.getBuffer();
/* 241 */     paramRenderQueue.ensureCapacityAndAlignment(72, 24);
/* 242 */     renderBuffer.putInt(31);
/* 243 */     renderBuffer.putInt(paramInt1);
/* 244 */     renderBuffer.putInt(paramInt2).putInt(paramInt3);
/* 245 */     renderBuffer.putInt(paramInt4).putInt(paramInt5);
/* 246 */     renderBuffer.putDouble(paramDouble1).putDouble(paramDouble2);
/* 247 */     renderBuffer.putDouble(paramDouble3).putDouble(paramDouble4);
/* 248 */     renderBuffer.putLong(paramSurfaceData1.getNativeOps());
/* 249 */     renderBuffer.putLong(paramSurfaceData2.getNativeOps());
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
/*     */   static void Blit(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Composite paramComposite, Region paramRegion, AffineTransform paramAffineTransform, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt6, boolean paramBoolean) {
/* 261 */     int i = 0;
/* 262 */     if (paramSurfaceData1.getTransparency() == 1) {
/* 263 */       i |= 0x1;
/*     */     }
/*     */     
/* 266 */     OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 267 */     oGLRenderQueue.lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 272 */       oGLRenderQueue.addReference(paramSurfaceData1);
/*     */       
/* 274 */       OGLSurfaceData oGLSurfaceData = (OGLSurfaceData)paramSurfaceData2;
/* 275 */       if (paramBoolean) {
/*     */ 
/*     */         
/* 278 */         OGLGraphicsConfig oGLGraphicsConfig = oGLSurfaceData.getOGLGraphicsConfig();
/* 279 */         OGLContext.setScratchSurface(oGLGraphicsConfig);
/*     */       } else {
/* 281 */         OGLContext.validateContext(oGLSurfaceData, oGLSurfaceData, paramRegion, paramComposite, paramAffineTransform, null, null, i);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 286 */       int j = createPackedParams(false, paramBoolean, false, (paramAffineTransform != null), paramInt1, paramInt6);
/*     */ 
/*     */       
/* 289 */       enqueueBlit(oGLRenderQueue, paramSurfaceData1, paramSurfaceData2, j, paramInt2, paramInt3, paramInt4, paramInt5, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 296 */       oGLRenderQueue.flushNow();
/*     */     } finally {
/* 298 */       oGLRenderQueue.unlock();
/*     */     } 
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
/*     */   static void IsoBlit(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, BufferedImage paramBufferedImage, BufferedImageOp paramBufferedImageOp, Composite paramComposite, Region paramRegion, AffineTransform paramAffineTransform, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean) {
/* 318 */     int i = 0;
/* 319 */     if (paramSurfaceData1.getTransparency() == 1) {
/* 320 */       i |= 0x1;
/*     */     }
/*     */     
/* 323 */     OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 324 */     oGLRenderQueue.lock(); try {
/*     */       boolean bool;
/* 326 */       OGLSurfaceData oGLSurfaceData3, oGLSurfaceData1 = (OGLSurfaceData)paramSurfaceData1;
/* 327 */       OGLSurfaceData oGLSurfaceData2 = (OGLSurfaceData)paramSurfaceData2;
/* 328 */       int j = oGLSurfaceData1.getType();
/*     */ 
/*     */       
/* 331 */       if (j == 3) {
/*     */ 
/*     */ 
/*     */         
/* 335 */         bool = false;
/* 336 */         oGLSurfaceData3 = oGLSurfaceData2;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 341 */         bool = true;
/* 342 */         if (j == 5) {
/* 343 */           oGLSurfaceData3 = oGLSurfaceData2;
/*     */         } else {
/* 345 */           oGLSurfaceData3 = oGLSurfaceData1;
/*     */         } 
/*     */       } 
/*     */       
/* 349 */       OGLContext.validateContext(oGLSurfaceData3, oGLSurfaceData2, paramRegion, paramComposite, paramAffineTransform, null, null, i);
/*     */ 
/*     */ 
/*     */       
/* 353 */       if (paramBufferedImageOp != null) {
/* 354 */         OGLBufImgOps.enableBufImgOp(oGLRenderQueue, oGLSurfaceData1, paramBufferedImage, paramBufferedImageOp);
/*     */       }
/*     */       
/* 357 */       int k = createPackedParams(true, paramBoolean, bool, (paramAffineTransform != null), paramInt1, 0);
/*     */ 
/*     */       
/* 360 */       enqueueBlit(oGLRenderQueue, paramSurfaceData1, paramSurfaceData2, k, paramInt2, paramInt3, paramInt4, paramInt5, paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 365 */       if (paramBufferedImageOp != null) {
/* 366 */         OGLBufImgOps.disableBufImgOp(oGLRenderQueue, paramBufferedImageOp);
/*     */       }
/*     */       
/* 369 */       if (bool && oGLSurfaceData2.isOnScreen())
/*     */       {
/*     */ 
/*     */         
/* 373 */         oGLRenderQueue.flushNow();
/*     */       }
/*     */     } finally {
/* 376 */       oGLRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/opengl/OGLBlitLoops.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */