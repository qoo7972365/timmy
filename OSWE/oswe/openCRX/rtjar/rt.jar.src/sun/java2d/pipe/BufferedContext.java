/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Composite;
/*     */ import java.awt.Paint;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import sun.java2d.InvalidPipeException;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.loops.XORComposite;
/*     */ import sun.java2d.pipe.hw.AccelSurface;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BufferedContext
/*     */ {
/*     */   public static final int NO_CONTEXT_FLAGS = 0;
/*     */   public static final int SRC_IS_OPAQUE = 1;
/*     */   public static final int USE_MASK = 2;
/*     */   protected RenderQueue rq;
/*     */   protected RenderBuffer buf;
/*     */   protected static BufferedContext currentContext;
/*  92 */   private Reference<AccelSurface> validSrcDataRef = new WeakReference<>(null);
/*  93 */   private Reference<AccelSurface> validDstDataRef = new WeakReference<>(null);
/*  94 */   private Reference<Region> validClipRef = new WeakReference<>(null);
/*  95 */   private Reference<Composite> validCompRef = new WeakReference<>(null);
/*  96 */   private Reference<Paint> validPaintRef = new WeakReference<>(null);
/*     */   
/*     */   private boolean isValidatedPaintJustAColor;
/*     */   private int validatedRGB;
/*     */   private int validatedFlags;
/*     */   private boolean xformInUse;
/*     */   private AffineTransform transform;
/*     */   
/*     */   protected BufferedContext(RenderQueue paramRenderQueue) {
/* 105 */     this.rq = paramRenderQueue;
/* 106 */     this.buf = paramRenderQueue.getBuffer();
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
/*     */   public static void validateContext(AccelSurface paramAccelSurface1, AccelSurface paramAccelSurface2, Region paramRegion, Composite paramComposite, AffineTransform paramAffineTransform, Paint paramPaint, SunGraphics2D paramSunGraphics2D, int paramInt) {
/* 132 */     BufferedContext bufferedContext = paramAccelSurface2.getContext();
/* 133 */     bufferedContext.validate(paramAccelSurface1, paramAccelSurface2, paramRegion, paramComposite, paramAffineTransform, paramPaint, paramSunGraphics2D, paramInt);
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
/*     */   public static void validateContext(AccelSurface paramAccelSurface) {
/* 152 */     validateContext(paramAccelSurface, paramAccelSurface, null, null, null, null, null, 0);
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
/*     */   public void validate(AccelSurface paramAccelSurface1, AccelSurface paramAccelSurface2, Region paramRegion, Composite paramComposite, AffineTransform paramAffineTransform, Paint paramPaint, SunGraphics2D paramSunGraphics2D, int paramInt) {
/* 182 */     boolean bool1 = false;
/* 183 */     boolean bool2 = false;
/*     */     
/* 185 */     if (!paramAccelSurface2.isValid() || paramAccelSurface2
/* 186 */       .isSurfaceLost() || paramAccelSurface1.isSurfaceLost()) {
/*     */       
/* 188 */       invalidateContext();
/* 189 */       throw new InvalidPipeException("bounds changed or surface lost");
/*     */     } 
/*     */     
/* 192 */     if (paramPaint instanceof Color) {
/*     */       
/* 194 */       int i = ((Color)paramPaint).getRGB();
/* 195 */       if (this.isValidatedPaintJustAColor) {
/* 196 */         if (i != this.validatedRGB) {
/* 197 */           this.validatedRGB = i;
/* 198 */           bool2 = true;
/*     */         } 
/*     */       } else {
/* 201 */         this.validatedRGB = i;
/* 202 */         bool2 = true;
/* 203 */         this.isValidatedPaintJustAColor = true;
/*     */       } 
/* 205 */     } else if (this.validPaintRef.get() != paramPaint) {
/* 206 */       bool2 = true;
/*     */ 
/*     */       
/* 209 */       this.isValidatedPaintJustAColor = false;
/*     */     } 
/*     */     
/* 212 */     AccelSurface accelSurface1 = this.validSrcDataRef.get();
/* 213 */     AccelSurface accelSurface2 = this.validDstDataRef.get();
/* 214 */     if (currentContext != this || paramAccelSurface1 != accelSurface1 || paramAccelSurface2 != accelSurface2) {
/*     */ 
/*     */ 
/*     */       
/* 218 */       if (paramAccelSurface2 != accelSurface2)
/*     */       {
/*     */         
/* 221 */         bool1 = true;
/*     */       }
/*     */       
/* 224 */       if (paramPaint == null)
/*     */       {
/*     */ 
/*     */         
/* 228 */         bool2 = true;
/*     */       }
/*     */ 
/*     */       
/* 232 */       setSurfaces(paramAccelSurface1, paramAccelSurface2);
/*     */       
/* 234 */       currentContext = this;
/* 235 */       this.validSrcDataRef = new WeakReference<>(paramAccelSurface1);
/* 236 */       this.validDstDataRef = new WeakReference<>(paramAccelSurface2);
/*     */     } 
/*     */ 
/*     */     
/* 240 */     Region region = this.validClipRef.get();
/* 241 */     if (paramRegion != region || bool1) {
/* 242 */       if (paramRegion != null) {
/* 243 */         if (bool1 || region == null || 
/*     */           
/* 245 */           !region.isRectangular() || !paramRegion.isRectangular() || paramRegion
/* 246 */           .getLoX() != region.getLoX() || paramRegion
/* 247 */           .getLoY() != region.getLoY() || paramRegion
/* 248 */           .getHiX() != region.getHiX() || paramRegion
/* 249 */           .getHiY() != region.getHiY())
/*     */         {
/* 251 */           setClip(paramRegion);
/*     */         }
/*     */       } else {
/* 254 */         resetClip();
/*     */       } 
/* 256 */       this.validClipRef = new WeakReference<>(paramRegion);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 262 */     if (paramComposite != this.validCompRef.get() || paramInt != this.validatedFlags) {
/* 263 */       if (paramComposite != null) {
/* 264 */         setComposite(paramComposite, paramInt);
/*     */       } else {
/* 266 */         resetComposite();
/*     */       } 
/*     */ 
/*     */       
/* 270 */       bool2 = true;
/* 271 */       this.validCompRef = new WeakReference<>(paramComposite);
/* 272 */       this.validatedFlags = paramInt;
/*     */     } 
/*     */ 
/*     */     
/* 276 */     boolean bool3 = false;
/* 277 */     if (paramAffineTransform == null) {
/* 278 */       if (this.xformInUse) {
/* 279 */         resetTransform();
/* 280 */         this.xformInUse = false;
/* 281 */         bool3 = true;
/* 282 */       } else if (paramSunGraphics2D != null && !paramSunGraphics2D.transform.equals(this.transform)) {
/* 283 */         bool3 = true;
/*     */       } 
/* 285 */       if (paramSunGraphics2D != null && bool3) {
/* 286 */         this.transform = new AffineTransform(paramSunGraphics2D.transform);
/*     */       }
/*     */     } else {
/* 289 */       setTransform(paramAffineTransform);
/* 290 */       this.xformInUse = true;
/* 291 */       bool3 = true;
/*     */     } 
/*     */     
/* 294 */     if (!this.isValidatedPaintJustAColor && bool3) {
/* 295 */       bool2 = true;
/*     */     }
/*     */ 
/*     */     
/* 299 */     if (bool2) {
/* 300 */       if (paramPaint != null) {
/* 301 */         BufferedPaints.setPaint(this.rq, paramSunGraphics2D, paramPaint, paramInt);
/*     */       } else {
/* 303 */         BufferedPaints.resetPaint(this.rq);
/*     */       } 
/* 305 */       this.validPaintRef = new WeakReference<>(paramPaint);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 310 */     paramAccelSurface2.markDirty();
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
/*     */   private void invalidateSurfaces() {
/* 324 */     this.validSrcDataRef.clear();
/* 325 */     this.validDstDataRef.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setSurfaces(AccelSurface paramAccelSurface1, AccelSurface paramAccelSurface2) {
/* 332 */     this.rq.ensureCapacityAndAlignment(20, 4);
/* 333 */     this.buf.putInt(70);
/* 334 */     this.buf.putLong(paramAccelSurface1.getNativeOps());
/* 335 */     this.buf.putLong(paramAccelSurface2.getNativeOps());
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetClip() {
/* 340 */     this.rq.ensureCapacity(4);
/* 341 */     this.buf.putInt(55);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setClip(Region paramRegion) {
/* 346 */     if (paramRegion.isRectangular()) {
/* 347 */       this.rq.ensureCapacity(20);
/* 348 */       this.buf.putInt(51);
/* 349 */       this.buf.putInt(paramRegion.getLoX()).putInt(paramRegion.getLoY());
/* 350 */       this.buf.putInt(paramRegion.getHiX()).putInt(paramRegion.getHiY());
/*     */     } else {
/* 352 */       this.rq.ensureCapacity(28);
/* 353 */       this.buf.putInt(52);
/* 354 */       this.buf.putInt(53);
/*     */       
/* 356 */       int i = this.buf.position();
/* 357 */       this.buf.putInt(0);
/* 358 */       byte b = 0;
/* 359 */       int j = this.buf.remaining() / 16;
/* 360 */       int[] arrayOfInt = new int[4];
/* 361 */       SpanIterator spanIterator = paramRegion.getSpanIterator();
/* 362 */       while (spanIterator.nextSpan(arrayOfInt)) {
/* 363 */         if (j == 0) {
/* 364 */           this.buf.putInt(i, b);
/* 365 */           this.rq.flushNow();
/* 366 */           this.buf.putInt(53);
/* 367 */           i = this.buf.position();
/* 368 */           this.buf.putInt(0);
/* 369 */           b = 0;
/* 370 */           j = this.buf.remaining() / 16;
/*     */         } 
/* 372 */         this.buf.putInt(arrayOfInt[0]);
/* 373 */         this.buf.putInt(arrayOfInt[1]);
/* 374 */         this.buf.putInt(arrayOfInt[2]);
/* 375 */         this.buf.putInt(arrayOfInt[3]);
/* 376 */         b++;
/* 377 */         j--;
/*     */       } 
/* 379 */       this.buf.putInt(i, b);
/* 380 */       this.rq.ensureCapacity(4);
/* 381 */       this.buf.putInt(54);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetComposite() {
/* 387 */     this.rq.ensureCapacity(4);
/* 388 */     this.buf.putInt(58);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setComposite(Composite paramComposite, int paramInt) {
/* 393 */     if (paramComposite instanceof AlphaComposite) {
/* 394 */       AlphaComposite alphaComposite = (AlphaComposite)paramComposite;
/* 395 */       this.rq.ensureCapacity(16);
/* 396 */       this.buf.putInt(56);
/* 397 */       this.buf.putInt(alphaComposite.getRule());
/* 398 */       this.buf.putFloat(alphaComposite.getAlpha());
/* 399 */       this.buf.putInt(paramInt);
/* 400 */     } else if (paramComposite instanceof XORComposite) {
/* 401 */       int i = ((XORComposite)paramComposite).getXorPixel();
/* 402 */       this.rq.ensureCapacity(8);
/* 403 */       this.buf.putInt(57);
/* 404 */       this.buf.putInt(i);
/*     */     } else {
/* 406 */       throw new InternalError("not yet implemented");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetTransform() {
/* 412 */     this.rq.ensureCapacity(4);
/* 413 */     this.buf.putInt(60);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setTransform(AffineTransform paramAffineTransform) {
/* 418 */     this.rq.ensureCapacityAndAlignment(52, 4);
/* 419 */     this.buf.putInt(59);
/* 420 */     this.buf.putDouble(paramAffineTransform.getScaleX());
/* 421 */     this.buf.putDouble(paramAffineTransform.getShearY());
/* 422 */     this.buf.putDouble(paramAffineTransform.getShearX());
/* 423 */     this.buf.putDouble(paramAffineTransform.getScaleY());
/* 424 */     this.buf.putDouble(paramAffineTransform.getTranslateX());
/* 425 */     this.buf.putDouble(paramAffineTransform.getTranslateY());
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
/*     */   public void invalidateContext() {
/* 437 */     resetTransform();
/* 438 */     resetComposite();
/* 439 */     resetClip();
/* 440 */     BufferedPaints.resetPaint(this.rq);
/* 441 */     invalidateSurfaces();
/* 442 */     this.validCompRef.clear();
/* 443 */     this.validClipRef.clear();
/* 444 */     this.validPaintRef.clear();
/* 445 */     this.isValidatedPaintJustAColor = false;
/* 446 */     this.xformInUse = false;
/*     */   }
/*     */   
/*     */   public abstract RenderQueue getRenderQueue();
/*     */   
/*     */   public abstract void saveState();
/*     */   
/*     */   public abstract void restoreState();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/BufferedContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */