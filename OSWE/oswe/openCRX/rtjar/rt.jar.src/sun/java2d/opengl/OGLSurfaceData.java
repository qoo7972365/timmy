/*     */ package sun.java2d.opengl;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Composite;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.security.AccessController;
/*     */ import sun.awt.image.PixelConverter;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.SurfaceDataProxy;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.GraphicsPrimitive;
/*     */ import sun.java2d.loops.MaskFill;
/*     */ import sun.java2d.loops.SurfaceType;
/*     */ import sun.java2d.pipe.BufferedContext;
/*     */ import sun.java2d.pipe.ParallelogramPipe;
/*     */ import sun.java2d.pipe.PixelToParallelogramConverter;
/*     */ import sun.java2d.pipe.RenderBuffer;
/*     */ import sun.java2d.pipe.TextPipe;
/*     */ import sun.java2d.pipe.hw.AccelSurface;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class OGLSurfaceData
/*     */   extends SurfaceData
/*     */   implements AccelSurface
/*     */ {
/*     */   public static final int PBUFFER = 2;
/*     */   public static final int FBOBJECT = 5;
/*     */   public static final int PF_INT_ARGB = 0;
/*     */   public static final int PF_INT_ARGB_PRE = 1;
/*     */   public static final int PF_INT_RGB = 2;
/*     */   public static final int PF_INT_RGBX = 3;
/*     */   public static final int PF_INT_BGR = 4;
/*     */   public static final int PF_INT_BGRX = 5;
/*     */   public static final int PF_USHORT_565_RGB = 6;
/*     */   public static final int PF_USHORT_555_RGB = 7;
/*     */   public static final int PF_USHORT_555_RGBX = 8;
/*     */   public static final int PF_BYTE_GRAY = 9;
/*     */   public static final int PF_USHORT_GRAY = 10;
/*     */   public static final int PF_3BYTE_BGR = 11;
/*     */   private static final String DESC_OPENGL_SURFACE = "OpenGL Surface";
/*     */   private static final String DESC_OPENGL_SURFACE_RTT = "OpenGL Surface (render-to-texture)";
/*     */   private static final String DESC_OPENGL_TEXTURE = "OpenGL Texture";
/* 135 */   static final SurfaceType OpenGLSurface = SurfaceType.Any
/* 136 */     .deriveSubType("OpenGL Surface", PixelConverter.ArgbPre.instance);
/*     */   
/* 138 */   static final SurfaceType OpenGLSurfaceRTT = OpenGLSurface
/* 139 */     .deriveSubType("OpenGL Surface (render-to-texture)");
/* 140 */   static final SurfaceType OpenGLTexture = SurfaceType.Any
/* 141 */     .deriveSubType("OpenGL Texture");
/*     */ 
/*     */   
/*     */   private static boolean isFBObjectEnabled;
/*     */ 
/*     */   
/*     */   private static boolean isLCDShaderEnabled;
/*     */ 
/*     */   
/*     */   private static boolean isBIOpShaderEnabled;
/*     */ 
/*     */   
/*     */   private static boolean isGradShaderEnabled;
/*     */ 
/*     */   
/*     */   private OGLGraphicsConfig graphicsConfig;
/*     */ 
/*     */   
/*     */   protected int type;
/*     */ 
/*     */   
/*     */   private int nativeWidth;
/*     */ 
/*     */   
/*     */   private int nativeHeight;
/*     */ 
/*     */   
/*     */   protected static OGLRenderer oglRenderPipe;
/*     */ 
/*     */   
/*     */   protected static PixelToParallelogramConverter oglTxRenderPipe;
/*     */ 
/*     */   
/*     */   protected static ParallelogramPipe oglAAPgramPipe;
/*     */ 
/*     */   
/*     */   protected static OGLTextRenderer oglTextPipe;
/*     */ 
/*     */   
/*     */   protected static OGLDrawImage oglImagePipe;
/*     */ 
/*     */   
/*     */   static {
/* 184 */     if (!GraphicsEnvironment.isHeadless()) {
/*     */       
/* 186 */       String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.opengl.fbobject"));
/*     */ 
/*     */       
/* 189 */       isFBObjectEnabled = !"false".equals(str1);
/*     */ 
/*     */       
/* 192 */       String str2 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.opengl.lcdshader"));
/*     */ 
/*     */       
/* 195 */       isLCDShaderEnabled = !"false".equals(str2);
/*     */ 
/*     */       
/* 198 */       String str3 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.opengl.biopshader"));
/*     */ 
/*     */       
/* 201 */       isBIOpShaderEnabled = !"false".equals(str3);
/*     */ 
/*     */       
/* 204 */       String str4 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.opengl.gradshader"));
/*     */ 
/*     */       
/* 207 */       isGradShaderEnabled = !"false".equals(str4);
/*     */       
/* 209 */       OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 210 */       oglImagePipe = new OGLDrawImage();
/* 211 */       oglTextPipe = new OGLTextRenderer(oGLRenderQueue);
/* 212 */       oglRenderPipe = new OGLRenderer(oGLRenderQueue);
/* 213 */       if (GraphicsPrimitive.tracingEnabled()) {
/* 214 */         oglTextPipe = oglTextPipe.traceWrap();
/*     */       }
/*     */ 
/*     */       
/* 218 */       oglAAPgramPipe = oglRenderPipe.getAAParallelogramPipe();
/* 219 */       oglTxRenderPipe = new PixelToParallelogramConverter(oglRenderPipe, oglRenderPipe, 1.0D, 0.25D, true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 224 */       OGLBlitLoops.register();
/* 225 */       OGLMaskFill.register();
/* 226 */       OGLMaskBlit.register();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected OGLSurfaceData(OGLGraphicsConfig paramOGLGraphicsConfig, ColorModel paramColorModel, int paramInt) {
/* 233 */     super(getCustomSurfaceType(paramInt), paramColorModel);
/* 234 */     this.graphicsConfig = paramOGLGraphicsConfig;
/* 235 */     this.type = paramInt;
/* 236 */     setBlitProxyKey(paramOGLGraphicsConfig.getProxyKey());
/*     */   }
/*     */ 
/*     */   
/*     */   public SurfaceDataProxy makeProxyFor(SurfaceData paramSurfaceData) {
/* 241 */     return OGLSurfaceDataProxy.createProxy(paramSurfaceData, this.graphicsConfig);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SurfaceType getCustomSurfaceType(int paramInt) {
/* 249 */     switch (paramInt) {
/*     */       case 3:
/* 251 */         return OpenGLTexture;
/*     */       case 5:
/* 253 */         return OpenGLSurfaceRTT;
/*     */     } 
/*     */     
/* 256 */     return OpenGLSurface;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initSurfaceNow(int paramInt1, int paramInt2) {
/* 266 */     boolean bool = (getTransparency() == 1) ? true : false;
/* 267 */     boolean bool1 = false;
/*     */     
/* 269 */     switch (this.type) {
/*     */       case 2:
/* 271 */         bool1 = initPbuffer(getNativeOps(), this.graphicsConfig
/* 272 */             .getNativeConfigInfo(), bool, paramInt1, paramInt2);
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 278 */         bool1 = initTexture(getNativeOps(), bool, 
/* 279 */             isTexNonPow2Available(), 
/* 280 */             isTexRectAvailable(), paramInt1, paramInt2);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 285 */         bool1 = initFBObject(getNativeOps(), bool, 
/* 286 */             isTexNonPow2Available(), 
/* 287 */             isTexRectAvailable(), paramInt1, paramInt2);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 292 */         bool1 = initFlipBackbuffer(getNativeOps());
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 299 */     if (!bool1) {
/* 300 */       throw new OutOfMemoryError("can't create offscreen surface");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initSurface(final int width, final int height) {
/* 310 */     OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 311 */     oGLRenderQueue.lock();
/*     */     try {
/* 313 */       switch (this.type) {
/*     */ 
/*     */         
/*     */         case 2:
/*     */         case 3:
/*     */         case 5:
/* 319 */           OGLContext.setScratchSurface(this.graphicsConfig);
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 324 */       oGLRenderQueue.flushAndInvokeNow(new Runnable() {
/*     */             public void run() {
/* 326 */               OGLSurfaceData.this.initSurfaceNow(width, height);
/*     */             }
/*     */           });
/*     */     } finally {
/* 330 */       oGLRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final OGLContext getContext() {
/* 339 */     return this.graphicsConfig.getContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final OGLGraphicsConfig getOGLGraphicsConfig() {
/* 346 */     return this.graphicsConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getType() {
/* 353 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getTextureTarget() {
/* 362 */     return getTextureTarget(getNativeOps());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getTextureID() {
/* 371 */     return getTextureID(getNativeOps());
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
/*     */   public long getNativeResource(int paramInt) {
/* 392 */     if (paramInt == 3) {
/* 393 */       return getTextureID();
/*     */     }
/* 395 */     return 0L;
/*     */   }
/*     */   
/*     */   public Raster getRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 399 */     throw new InternalError("not implemented yet");
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
/*     */   public boolean canRenderLCDText(SunGraphics2D paramSunGraphics2D) {
/* 414 */     return (this.graphicsConfig
/* 415 */       .isCapPresent(131072) && paramSunGraphics2D.surfaceData
/* 416 */       .getTransparency() == 1 && paramSunGraphics2D.paintState <= 0 && (paramSunGraphics2D.compositeState <= 0 || (paramSunGraphics2D.compositeState <= 1 && 
/*     */ 
/*     */       
/* 419 */       canHandleComposite(paramSunGraphics2D.composite))));
/*     */   }
/*     */   
/*     */   private boolean canHandleComposite(Composite paramComposite) {
/* 423 */     if (paramComposite instanceof AlphaComposite) {
/* 424 */       AlphaComposite alphaComposite = (AlphaComposite)paramComposite;
/*     */       
/* 426 */       return (alphaComposite.getRule() == 3 && alphaComposite.getAlpha() >= 1.0F);
/*     */     } 
/* 428 */     return false;
/*     */   }
/*     */   
/*     */   public void validatePipe(SunGraphics2D paramSunGraphics2D) {
/*     */     TextPipe textPipe;
/* 433 */     boolean bool = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 442 */     if ((paramSunGraphics2D.compositeState <= 0 && paramSunGraphics2D.paintState <= 1) || (paramSunGraphics2D.compositeState == 1 && paramSunGraphics2D.paintState <= 1 && ((AlphaComposite)paramSunGraphics2D.composite)
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 449 */       .getRule() == 3) || (paramSunGraphics2D.compositeState == 2 && paramSunGraphics2D.paintState <= 1)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 456 */       textPipe = oglTextPipe;
/*     */     }
/*     */     else {
/*     */       
/* 460 */       super.validatePipe(paramSunGraphics2D);
/* 461 */       textPipe = paramSunGraphics2D.textpipe;
/* 462 */       bool = true;
/*     */     } 
/*     */     
/* 465 */     PixelToParallelogramConverter pixelToParallelogramConverter = null;
/* 466 */     OGLRenderer oGLRenderer = null;
/*     */     
/* 468 */     if (paramSunGraphics2D.antialiasHint != 2) {
/* 469 */       if (paramSunGraphics2D.paintState <= 1) {
/* 470 */         if (paramSunGraphics2D.compositeState <= 2) {
/* 471 */           pixelToParallelogramConverter = oglTxRenderPipe;
/* 472 */           oGLRenderer = oglRenderPipe;
/*     */         } 
/* 474 */       } else if (paramSunGraphics2D.compositeState <= 1 && 
/* 475 */         OGLPaints.isValid(paramSunGraphics2D)) {
/* 476 */         pixelToParallelogramConverter = oglTxRenderPipe;
/* 477 */         oGLRenderer = oglRenderPipe;
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 482 */     else if (paramSunGraphics2D.paintState <= 1) {
/* 483 */       if (this.graphicsConfig.isCapPresent(256) && (paramSunGraphics2D.imageComp == CompositeType.SrcOverNoEa || paramSunGraphics2D.imageComp == CompositeType.SrcOver)) {
/*     */ 
/*     */ 
/*     */         
/* 487 */         if (!bool) {
/* 488 */           super.validatePipe(paramSunGraphics2D);
/* 489 */           bool = true;
/*     */         } 
/* 491 */         PixelToParallelogramConverter pixelToParallelogramConverter1 = new PixelToParallelogramConverter(paramSunGraphics2D.shapepipe, oglAAPgramPipe, 0.125D, 0.499D, false);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 496 */         paramSunGraphics2D.drawpipe = pixelToParallelogramConverter1;
/* 497 */         paramSunGraphics2D.fillpipe = pixelToParallelogramConverter1;
/* 498 */         paramSunGraphics2D.shapepipe = pixelToParallelogramConverter1;
/* 499 */       } else if (paramSunGraphics2D.compositeState == 2) {
/*     */         
/* 501 */         pixelToParallelogramConverter = oglTxRenderPipe;
/* 502 */         oGLRenderer = oglRenderPipe;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 508 */     if (pixelToParallelogramConverter != null) {
/* 509 */       if (paramSunGraphics2D.transformState >= 3) {
/* 510 */         paramSunGraphics2D.drawpipe = pixelToParallelogramConverter;
/* 511 */         paramSunGraphics2D.fillpipe = pixelToParallelogramConverter;
/* 512 */       } else if (paramSunGraphics2D.strokeState != 0) {
/* 513 */         paramSunGraphics2D.drawpipe = pixelToParallelogramConverter;
/* 514 */         paramSunGraphics2D.fillpipe = oGLRenderer;
/*     */       } else {
/* 516 */         paramSunGraphics2D.drawpipe = oGLRenderer;
/* 517 */         paramSunGraphics2D.fillpipe = oGLRenderer;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 523 */       paramSunGraphics2D.shapepipe = pixelToParallelogramConverter;
/*     */     }
/* 525 */     else if (!bool) {
/* 526 */       super.validatePipe(paramSunGraphics2D);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 531 */     paramSunGraphics2D.textpipe = textPipe;
/*     */ 
/*     */     
/* 534 */     paramSunGraphics2D.imagepipe = oglImagePipe;
/*     */   }
/*     */ 
/*     */   
/*     */   protected MaskFill getMaskFill(SunGraphics2D paramSunGraphics2D) {
/* 539 */     if (paramSunGraphics2D.paintState > 1)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 551 */       if (!OGLPaints.isValid(paramSunGraphics2D) || 
/* 552 */         !this.graphicsConfig.isCapPresent(16))
/*     */       {
/* 554 */         return null;
/*     */       }
/*     */     }
/* 557 */     return super.getMaskFill(paramSunGraphics2D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean copyArea(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 563 */     if (paramSunGraphics2D.transformState < 3 && paramSunGraphics2D.compositeState < 2) {
/*     */ 
/*     */       
/* 566 */       paramInt1 += paramSunGraphics2D.transX;
/* 567 */       paramInt2 += paramSunGraphics2D.transY;
/*     */       
/* 569 */       oglRenderPipe.copyArea(paramSunGraphics2D, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */       
/* 571 */       return true;
/*     */     } 
/* 573 */     return false;
/*     */   }
/*     */   
/*     */   public void flush() {
/* 577 */     invalidate();
/* 578 */     OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 579 */     oGLRenderQueue.lock();
/*     */ 
/*     */     
/*     */     try {
/* 583 */       OGLContext.setScratchSurface(this.graphicsConfig);
/*     */       
/* 585 */       RenderBuffer renderBuffer = oGLRenderQueue.getBuffer();
/* 586 */       oGLRenderQueue.ensureCapacityAndAlignment(12, 4);
/* 587 */       renderBuffer.putInt(72);
/* 588 */       renderBuffer.putLong(getNativeOps());
/*     */ 
/*     */       
/* 591 */       oGLRenderQueue.flushNow();
/*     */     } finally {
/* 593 */       oGLRenderQueue.unlock();
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
/*     */   static void dispose(long paramLong, OGLGraphicsConfig paramOGLGraphicsConfig) {
/* 606 */     OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 607 */     oGLRenderQueue.lock();
/*     */ 
/*     */     
/*     */     try {
/* 611 */       OGLContext.setScratchSurface(paramOGLGraphicsConfig);
/*     */       
/* 613 */       RenderBuffer renderBuffer = oGLRenderQueue.getBuffer();
/* 614 */       oGLRenderQueue.ensureCapacityAndAlignment(12, 4);
/* 615 */       renderBuffer.putInt(73);
/* 616 */       renderBuffer.putLong(paramLong);
/*     */ 
/*     */       
/* 619 */       oGLRenderQueue.flushNow();
/*     */     } finally {
/* 621 */       oGLRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   static void swapBuffers(long paramLong) {
/* 626 */     OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 627 */     oGLRenderQueue.lock();
/*     */     try {
/* 629 */       RenderBuffer renderBuffer = oGLRenderQueue.getBuffer();
/* 630 */       oGLRenderQueue.ensureCapacityAndAlignment(12, 4);
/* 631 */       renderBuffer.putInt(80);
/* 632 */       renderBuffer.putLong(paramLong);
/* 633 */       oGLRenderQueue.flushNow();
/*     */     } finally {
/* 635 */       oGLRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isTexNonPow2Available() {
/* 644 */     return this.graphicsConfig.isCapPresent(32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isTexRectAvailable() {
/* 653 */     return this.graphicsConfig.isCapPresent(1048576);
/*     */   }
/*     */   
/*     */   public Rectangle getNativeBounds() {
/* 657 */     OGLRenderQueue oGLRenderQueue = OGLRenderQueue.getInstance();
/* 658 */     oGLRenderQueue.lock();
/*     */     try {
/* 660 */       return new Rectangle(this.nativeWidth, this.nativeHeight);
/*     */     } finally {
/* 662 */       oGLRenderQueue.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isOnScreen() {
/* 673 */     return (getType() == 1);
/*     */   }
/*     */   
/*     */   protected native boolean initTexture(long paramLong, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt1, int paramInt2);
/*     */   
/*     */   protected native boolean initFBObject(long paramLong, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt1, int paramInt2);
/*     */   
/*     */   protected native boolean initFlipBackbuffer(long paramLong);
/*     */   
/*     */   protected abstract boolean initPbuffer(long paramLong1, long paramLong2, boolean paramBoolean, int paramInt1, int paramInt2);
/*     */   
/*     */   private native int getTextureTarget(long paramLong);
/*     */   
/*     */   private native int getTextureID(long paramLong);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/opengl/OGLSurfaceData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */