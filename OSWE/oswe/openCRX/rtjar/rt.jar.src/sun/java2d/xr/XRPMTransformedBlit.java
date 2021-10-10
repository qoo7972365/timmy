/*     */ package sun.java2d.xr;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Composite;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.NoninvertibleTransformException;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.SurfaceType;
/*     */ import sun.java2d.loops.TransformBlit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class XRPMTransformedBlit
/*     */   extends TransformBlit
/*     */ {
/* 232 */   final Rectangle compositeBounds = new Rectangle();
/* 233 */   final double[] srcCoords = new double[8];
/* 234 */   final double[] dstCoords = new double[8];
/*     */   
/*     */   public XRPMTransformedBlit(SurfaceType paramSurfaceType1, SurfaceType paramSurfaceType2) {
/* 237 */     super(paramSurfaceType1, CompositeType.AnyAlpha, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void adjustCompositeBounds(boolean paramBoolean, AffineTransform paramAffineTransform, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*     */     double d1, d2, d3, d4;
/* 249 */     this.srcCoords[0] = paramInt1;
/* 250 */     this.srcCoords[1] = paramInt2;
/* 251 */     this.srcCoords[2] = (paramInt1 + paramInt3);
/* 252 */     this.srcCoords[3] = (paramInt2 + paramInt4);
/*     */ 
/*     */     
/* 255 */     if (paramBoolean) {
/* 256 */       paramAffineTransform.transform(this.srcCoords, 0, this.dstCoords, 0, 2);
/*     */       
/* 258 */       d1 = Math.min(this.dstCoords[0], this.dstCoords[2]);
/* 259 */       d2 = Math.min(this.dstCoords[1], this.dstCoords[3]);
/* 260 */       d3 = Math.max(this.dstCoords[0], this.dstCoords[2]);
/* 261 */       d4 = Math.max(this.dstCoords[1], this.dstCoords[3]);
/*     */       
/* 263 */       d1 = Math.ceil(d1 - 0.5D);
/* 264 */       d2 = Math.ceil(d2 - 0.5D);
/* 265 */       d3 = Math.ceil(d3 - 0.5D);
/* 266 */       d4 = Math.ceil(d4 - 0.5D);
/*     */     } else {
/* 268 */       this.srcCoords[4] = paramInt1;
/* 269 */       this.srcCoords[5] = (paramInt2 + paramInt4);
/* 270 */       this.srcCoords[6] = (paramInt1 + paramInt3);
/* 271 */       this.srcCoords[7] = paramInt2;
/*     */       
/* 273 */       paramAffineTransform.transform(this.srcCoords, 0, this.dstCoords, 0, 4);
/*     */       
/* 275 */       d1 = Math.min(this.dstCoords[0], Math.min(this.dstCoords[2], Math.min(this.dstCoords[4], this.dstCoords[6])));
/* 276 */       d2 = Math.min(this.dstCoords[1], Math.min(this.dstCoords[3], Math.min(this.dstCoords[5], this.dstCoords[7])));
/* 277 */       d3 = Math.max(this.dstCoords[0], Math.max(this.dstCoords[2], Math.max(this.dstCoords[4], this.dstCoords[6])));
/* 278 */       d4 = Math.max(this.dstCoords[1], Math.max(this.dstCoords[3], Math.max(this.dstCoords[5], this.dstCoords[7])));
/*     */       
/* 280 */       d1 = Math.floor(d1);
/* 281 */       d2 = Math.floor(d2);
/* 282 */       d3 = Math.ceil(d3);
/* 283 */       d4 = Math.ceil(d4);
/*     */     } 
/*     */     
/* 286 */     this.compositeBounds.x = (int)d1;
/* 287 */     this.compositeBounds.y = (int)d2;
/* 288 */     this.compositeBounds.width = (int)(d3 - d1);
/* 289 */     this.compositeBounds.height = (int)(d4 - d2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void Transform(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Composite paramComposite, Region paramRegion, AffineTransform paramAffineTransform, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/*     */     try {
/* 295 */       SunToolkit.awtLock();
/*     */       
/* 297 */       XRSurfaceData xRSurfaceData1 = (XRSurfaceData)paramSurfaceData2;
/* 298 */       XRSurfaceData xRSurfaceData2 = (XRSurfaceData)paramSurfaceData1;
/* 299 */       XRCompositeManager xRCompositeManager = XRCompositeManager.getInstance(xRSurfaceData2);
/*     */       
/* 301 */       float f = ((AlphaComposite)paramComposite).getAlpha();
/* 302 */       int i = XRUtils.ATransOpToXRQuality(paramInt1);
/* 303 */       boolean bool = XRUtils.isTransformQuadrantRotated(paramAffineTransform);
/*     */       
/* 305 */       adjustCompositeBounds(bool, paramAffineTransform, paramInt4, paramInt5, paramInt6, paramInt7);
/*     */       
/* 307 */       xRSurfaceData1.validateAsDestination(null, paramRegion);
/* 308 */       xRSurfaceData1.maskBuffer.validateCompositeState(paramComposite, null, null, null);
/*     */       
/* 310 */       AffineTransform affineTransform1 = AffineTransform.getTranslateInstance(-this.compositeBounds.x, -this.compositeBounds.y);
/* 311 */       affineTransform1.concatenate(paramAffineTransform);
/* 312 */       AffineTransform affineTransform2 = (AffineTransform)affineTransform1.clone();
/* 313 */       affineTransform1.translate(-paramInt2, -paramInt3);
/*     */       
/*     */       try {
/* 316 */         affineTransform1.invert();
/* 317 */       } catch (NoninvertibleTransformException noninvertibleTransformException) {
/* 318 */         affineTransform1.setToIdentity();
/*     */       } 
/*     */       
/* 321 */       if (i != 0 && (!bool || f != 1.0F)) {
/* 322 */         XRMaskImage xRMaskImage = xRSurfaceData2.maskBuffer.getMaskImage();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 327 */         int j = bool ? xRCompositeManager.getExtraAlphaMask() : xRMaskImage.prepareBlitMask(xRSurfaceData1, affineTransform2, paramInt6, paramInt7);
/*     */         
/* 329 */         xRSurfaceData2.validateAsSource(affineTransform1, 2, i);
/* 330 */         xRSurfaceData1.maskBuffer.con.renderComposite(xRCompositeManager.getCompRule(), xRSurfaceData2.picture, j, xRSurfaceData1.picture, 0, 0, 0, 0, this.compositeBounds.x, this.compositeBounds.y, this.compositeBounds.width, this.compositeBounds.height);
/*     */       }
/*     */       else {
/*     */         
/* 334 */         boolean bool1 = (i == 0) ? false : true;
/*     */         
/* 336 */         xRSurfaceData2.validateAsSource(affineTransform1, bool1, i);
/*     */ 
/*     */         
/* 339 */         xRSurfaceData1.maskBuffer.compositeBlit(xRSurfaceData2, xRSurfaceData1, 0, 0, this.compositeBounds.x, this.compositeBounds.y, this.compositeBounds.width, this.compositeBounds.height);
/*     */       } 
/*     */     } finally {
/*     */       
/* 343 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRPMTransformedBlit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */