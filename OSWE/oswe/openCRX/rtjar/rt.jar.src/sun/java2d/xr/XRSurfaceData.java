/*     */ package sun.java2d.xr;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Image;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.X11ComponentPeer;
/*     */ import sun.font.FontManagerNativeLibrary;
/*     */ import sun.java2d.InvalidPipeException;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.SurfaceDataProxy;
/*     */ import sun.java2d.jules.JulesPathBuf;
/*     */ import sun.java2d.jules.JulesShapePipe;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.MaskFill;
/*     */ import sun.java2d.loops.RenderLoops;
/*     */ import sun.java2d.loops.SurfaceType;
/*     */ import sun.java2d.loops.XORComposite;
/*     */ import sun.java2d.pipe.PixelToShapeConverter;
/*     */ import sun.java2d.pipe.Region;
/*     */ import sun.java2d.pipe.ShapeDrawPipe;
/*     */ import sun.java2d.pipe.TextPipe;
/*     */ import sun.java2d.pipe.ValidatePipe;
/*     */ import sun.java2d.x11.XSurfaceData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class XRSurfaceData
/*     */   extends XSurfaceData
/*     */ {
/*     */   X11ComponentPeer peer;
/*     */   XRGraphicsConfig graphicsConfig;
/*     */   XRBackend renderQueue;
/*     */   private RenderLoops solidloops;
/*     */   protected int depth;
/*     */   public static final String DESC_BYTE_A8_X11 = "Byte A8 Pixmap";
/*     */   public static final String DESC_INT_RGB_X11 = "Integer RGB Pixmap";
/*     */   public static final String DESC_INT_ARGB_X11 = "Integer ARGB-Pre Pixmap";
/*  65 */   public static final SurfaceType ByteA8X11 = SurfaceType.ByteGray.deriveSubType("Byte A8 Pixmap");
/*     */   
/*  67 */   public static final SurfaceType IntRgbX11 = SurfaceType.IntRgb.deriveSubType("Integer RGB Pixmap");
/*     */   
/*  69 */   public static final SurfaceType IntArgbPreX11 = SurfaceType.IntArgbPre.deriveSubType("Integer ARGB-Pre Pixmap"); protected XRRenderer xrpipe; protected PixelToShapeConverter xrtxpipe; protected TextPipe xrtextpipe; protected XRDrawImage xrDrawImage; protected ShapeDrawPipe aaShapePipe; protected PixelToShapeConverter aaPixelToShapeConv; private long xgc; private int validatedGCForegroundPixel; private XORComposite validatedXorComp; private int xid; public int picture; public XRCompositeManager maskBuffer; private Region validatedClip; private Region validatedGCClip; private boolean validatedExposures; boolean transformInUse; AffineTransform validatedSourceTransform; AffineTransform staticSrcTx; int validatedRepeat; int validatedFilter; private static native void initIDs(); protected native void XRInitSurface(int paramInt1, int paramInt2, int paramInt3, long paramLong, int paramInt4); native void initXRPicture(long paramLong, int paramInt);
/*     */   native void freeXSDOPicture(long paramLong);
/*     */   public Raster getRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  72 */     throw new InternalError("not implemented yet");
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
/*     */   public static void initXRSurfaceData() {
/*  84 */     if (!isX11SurfaceDataInitialized()) {
/*  85 */       FontManagerNativeLibrary.load();
/*  86 */       initIDs();
/*  87 */       XRPMBlitLoops.register();
/*  88 */       XRMaskFill.register();
/*  89 */       XRMaskBlit.register();
/*     */       
/*  91 */       setX11SurfaceDataInitialized();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isXRDrawableValid() {
/*     */     try {
/* 100 */       SunToolkit.awtLock();
/* 101 */       return isDrawableValid();
/*     */     } finally {
/* 103 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SurfaceDataProxy makeProxyFor(SurfaceData paramSurfaceData) {
/* 109 */     return XRSurfaceDataProxy.createProxy(paramSurfaceData, this.graphicsConfig);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void validatePipe(SunGraphics2D paramSunGraphics2D) {
/* 115 */     boolean bool = false;
/*     */ 
/*     */     
/*     */     TextPipe textPipe;
/*     */ 
/*     */     
/* 121 */     if ((textPipe = getTextPipe(paramSunGraphics2D)) == null) {
/*     */       
/* 123 */       super.validatePipe(paramSunGraphics2D);
/* 124 */       textPipe = paramSunGraphics2D.textpipe;
/* 125 */       bool = true;
/*     */     } 
/*     */     
/* 128 */     PixelToShapeConverter pixelToShapeConverter = null;
/* 129 */     XRRenderer xRRenderer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     if (paramSunGraphics2D.antialiasHint != 2) {
/* 135 */       if (paramSunGraphics2D.paintState <= 1) {
/* 136 */         if (paramSunGraphics2D.compositeState <= 2) {
/* 137 */           pixelToShapeConverter = this.xrtxpipe;
/* 138 */           xRRenderer = this.xrpipe;
/*     */         } 
/* 140 */       } else if (paramSunGraphics2D.compositeState <= 1 && 
/* 141 */         XRPaints.isValid(paramSunGraphics2D)) {
/* 142 */         pixelToShapeConverter = this.xrtxpipe;
/* 143 */         xRRenderer = this.xrpipe;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 149 */     if (paramSunGraphics2D.antialiasHint == 2 && 
/* 150 */       JulesPathBuf.isCairoAvailable()) {
/*     */       
/* 152 */       paramSunGraphics2D.shapepipe = this.aaShapePipe;
/* 153 */       paramSunGraphics2D.drawpipe = this.aaPixelToShapeConv;
/* 154 */       paramSunGraphics2D.fillpipe = this.aaPixelToShapeConv;
/*     */     }
/* 156 */     else if (pixelToShapeConverter != null) {
/* 157 */       if (paramSunGraphics2D.transformState >= 3) {
/* 158 */         paramSunGraphics2D.drawpipe = pixelToShapeConverter;
/* 159 */         paramSunGraphics2D.fillpipe = pixelToShapeConverter;
/* 160 */       } else if (paramSunGraphics2D.strokeState != 0) {
/* 161 */         paramSunGraphics2D.drawpipe = pixelToShapeConverter;
/* 162 */         paramSunGraphics2D.fillpipe = xRRenderer;
/*     */       } else {
/* 164 */         paramSunGraphics2D.drawpipe = xRRenderer;
/* 165 */         paramSunGraphics2D.fillpipe = xRRenderer;
/*     */       } 
/* 167 */       paramSunGraphics2D.shapepipe = xRRenderer;
/*     */     }
/* 169 */     else if (!bool) {
/* 170 */       super.validatePipe(paramSunGraphics2D);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     paramSunGraphics2D.textpipe = textPipe;
/*     */ 
/*     */     
/* 179 */     paramSunGraphics2D.imagepipe = this.xrDrawImage;
/*     */   }
/*     */   
/*     */   protected TextPipe getTextPipe(SunGraphics2D paramSunGraphics2D) {
/* 183 */     boolean bool1 = (paramSunGraphics2D.compositeState <= 1 && (paramSunGraphics2D.paintState <= 1 || paramSunGraphics2D.composite == null)) ? true : false;
/*     */ 
/*     */     
/* 186 */     boolean bool2 = false;
/* 187 */     if (paramSunGraphics2D.composite instanceof AlphaComposite) {
/* 188 */       int i = ((AlphaComposite)paramSunGraphics2D.composite).getRule();
/* 189 */       bool2 = (XRUtils.isMaskEvaluated(XRUtils.j2dAlphaCompToXR(i)) || (i == 2 && paramSunGraphics2D.paintState <= 1)) ? true : false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 194 */     return (bool1 && bool2) ? this.xrtextpipe : null;
/*     */   }
/*     */   
/*     */   protected MaskFill getMaskFill(SunGraphics2D paramSunGraphics2D) {
/* 198 */     AlphaComposite alphaComposite = null;
/* 199 */     if (paramSunGraphics2D.composite != null && paramSunGraphics2D.composite instanceof AlphaComposite)
/*     */     {
/* 201 */       alphaComposite = (AlphaComposite)paramSunGraphics2D.composite;
/*     */     }
/*     */ 
/*     */     
/* 205 */     boolean bool = (paramSunGraphics2D.paintState <= 1 || XRPaints.isValid(paramSunGraphics2D)) ? true : false;
/*     */     
/* 207 */     boolean bool1 = false;
/* 208 */     if (alphaComposite != null) {
/* 209 */       int i = alphaComposite.getRule();
/* 210 */       bool1 = XRUtils.isMaskEvaluated(XRUtils.j2dAlphaCompToXR(i));
/*     */     } 
/*     */     
/* 213 */     return (bool && bool1) ? super.getMaskFill(paramSunGraphics2D) : null;
/*     */   }
/*     */   
/*     */   public RenderLoops getRenderLoops(SunGraphics2D paramSunGraphics2D) {
/* 217 */     if (paramSunGraphics2D.paintState <= 1 && paramSunGraphics2D.compositeState <= 1)
/*     */     {
/*     */       
/* 220 */       return this.solidloops;
/*     */     }
/*     */     
/* 223 */     return super.getRenderLoops(paramSunGraphics2D);
/*     */   }
/*     */   
/*     */   public GraphicsConfiguration getDeviceConfiguration() {
/* 227 */     return (GraphicsConfiguration)this.graphicsConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XRWindowSurfaceData createData(X11ComponentPeer paramX11ComponentPeer) {
/* 234 */     XRGraphicsConfig xRGraphicsConfig = getGC(paramX11ComponentPeer);
/* 235 */     return new XRWindowSurfaceData(paramX11ComponentPeer, xRGraphicsConfig, xRGraphicsConfig.getSurfaceType());
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
/*     */   public static XRPixmapSurfaceData createData(XRGraphicsConfig paramXRGraphicsConfig, int paramInt1, int paramInt2, ColorModel paramColorModel, Image paramImage, long paramLong, int paramInt3) {
/*     */     byte b;
/* 252 */     if (paramXRGraphicsConfig.getColorModel().getPixelSize() == 32) {
/* 253 */       b = 32;
/* 254 */       paramInt3 = 3;
/*     */     } else {
/* 256 */       b = (paramInt3 > 1) ? 32 : 24;
/*     */     } 
/*     */     
/* 259 */     if (b == 24) {
/* 260 */       paramColorModel = new DirectColorModel(b, 16711680, 65280, 255);
/*     */     } else {
/*     */       
/* 263 */       paramColorModel = new DirectColorModel(b, 16711680, 65280, 255, -16777216);
/*     */     } 
/*     */ 
/*     */     
/* 267 */     return new XRPixmapSurfaceData(paramXRGraphicsConfig, paramInt1, paramInt2, paramImage, 
/* 268 */         getSurfaceType(paramXRGraphicsConfig, paramInt3), paramColorModel, paramLong, paramInt3, 
/*     */         
/* 270 */         XRUtils.getPictureFormatForTransparency(paramInt3), b);
/*     */   }
/*     */ 
/*     */   
/*     */   protected XRSurfaceData(X11ComponentPeer paramX11ComponentPeer, XRGraphicsConfig paramXRGraphicsConfig, SurfaceType paramSurfaceType, ColorModel paramColorModel, int paramInt1, int paramInt2)
/*     */   {
/* 276 */     super(paramSurfaceType, paramColorModel);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 416 */     this.validatedGCForegroundPixel = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 424 */     this.validatedExposures = true;
/*     */     
/* 426 */     this.transformInUse = false;
/* 427 */     this.validatedSourceTransform = new AffineTransform();
/* 428 */     this.staticSrcTx = null;
/* 429 */     this.validatedRepeat = 0;
/* 430 */     this.validatedFilter = 0; this.peer = paramX11ComponentPeer; this.graphicsConfig = paramXRGraphicsConfig; this.solidloops = this.graphicsConfig.getSolidLoops(paramSurfaceType); this.depth = paramInt1; initOps(paramX11ComponentPeer, this.graphicsConfig, paramInt1); setBlitProxyKey(paramXRGraphicsConfig.getProxyKey()); } protected XRSurfaceData(XRBackend paramXRBackend) { super(IntRgbX11, new DirectColorModel(24, 16711680, 65280, 255)); this.validatedGCForegroundPixel = 0; this.validatedExposures = true; this.transformInUse = false; this.validatedSourceTransform = new AffineTransform(); this.staticSrcTx = null; this.validatedRepeat = 0; this.validatedFilter = 0; this.renderQueue = paramXRBackend; } public void initXRender(int paramInt) { try { SunToolkit.awtLock(); initXRPicture(getNativeOps(), paramInt); this.renderQueue = XRCompositeManager.getInstance(this).getBackend(); this.maskBuffer = XRCompositeManager.getInstance(this); }
/*     */     catch (Throwable throwable)
/*     */     { throwable.printStackTrace(); }
/*     */     finally
/*     */     { SunToolkit.awtUnlock(); }
/*     */      } public static XRGraphicsConfig getGC(X11ComponentPeer paramX11ComponentPeer) { if (paramX11ComponentPeer != null)
/*     */       return (XRGraphicsConfig)paramX11ComponentPeer.getGraphicsConfiguration();  GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment(); GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice(); return (XRGraphicsConfig)graphicsDevice.getDefaultConfiguration(); }
/*     */   public abstract boolean canSourceSendExposures(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/* 438 */   void validateAsSource(AffineTransform paramAffineTransform, int paramInt1, int paramInt2) { if (this.validatedClip != null) {
/* 439 */       this.validatedClip = null;
/* 440 */       this.renderQueue.setClipRectangles(this.picture, null);
/*     */     } 
/*     */     
/* 443 */     if (this.validatedRepeat != paramInt1 && paramInt1 != -1) {
/* 444 */       this.validatedRepeat = paramInt1;
/* 445 */       this.renderQueue.setPictureRepeat(this.picture, paramInt1);
/*     */     } 
/*     */     
/* 448 */     if (paramAffineTransform == null) {
/* 449 */       if (this.transformInUse) {
/* 450 */         this.validatedSourceTransform.setToIdentity();
/* 451 */         this.renderQueue.setPictureTransform(this.picture, this.validatedSourceTransform);
/*     */         
/* 453 */         this.transformInUse = false;
/*     */       } 
/* 455 */     } else if (!this.transformInUse || (this.transformInUse && 
/* 456 */       !paramAffineTransform.equals(this.validatedSourceTransform))) {
/*     */       
/* 458 */       this.validatedSourceTransform.setTransform(paramAffineTransform.getScaleX(), paramAffineTransform
/* 459 */           .getShearY(), paramAffineTransform
/* 460 */           .getShearX(), paramAffineTransform
/* 461 */           .getScaleY(), paramAffineTransform
/* 462 */           .getTranslateX(), paramAffineTransform
/* 463 */           .getTranslateY());
/*     */       
/* 465 */       AffineTransform affineTransform = this.validatedSourceTransform;
/* 466 */       if (this.staticSrcTx != null) {
/*     */ 
/*     */ 
/*     */         
/* 470 */         affineTransform = new AffineTransform(this.validatedSourceTransform);
/* 471 */         affineTransform.preConcatenate(this.staticSrcTx);
/*     */       } 
/*     */       
/* 474 */       this.renderQueue.setPictureTransform(this.picture, affineTransform);
/* 475 */       this.transformInUse = true;
/*     */     } 
/*     */     
/* 478 */     if (paramInt2 != this.validatedFilter && paramInt2 != -1) {
/* 479 */       this.renderQueue.setFilter(this.picture, paramInt2);
/* 480 */       this.validatedFilter = paramInt2;
/*     */     }  }
/*     */   public void validateCopyAreaGC(Region paramRegion, boolean paramBoolean) { if (this.validatedGCClip != paramRegion) { if (paramRegion != null)
/*     */         this.renderQueue.setGCClipRectangles(this.xgc, paramRegion);  this.validatedGCClip = paramRegion; }
/*     */      if (this.validatedExposures != paramBoolean) {
/*     */       this.validatedExposures = paramBoolean; this.renderQueue.setGCExposures(this.xgc, paramBoolean);
/*     */     }  if (this.validatedXorComp != null) {
/*     */       this.renderQueue.setGCMode(this.xgc, true); this.renderQueue.setGCForeground(this.xgc, this.validatedGCForegroundPixel); this.validatedXorComp = null;
/* 488 */     }  } public void validateAsDestination(SunGraphics2D paramSunGraphics2D, Region paramRegion) { if (!isValid()) {
/* 489 */       throw new InvalidPipeException("bounds changed");
/*     */     }
/*     */     
/* 492 */     boolean bool = false;
/* 493 */     if (paramRegion != this.validatedClip) {
/* 494 */       this.renderQueue.setClipRectangles(this.picture, paramRegion);
/* 495 */       this.validatedClip = paramRegion;
/* 496 */       bool = true;
/*     */     } 
/*     */     
/* 499 */     if (paramSunGraphics2D != null && paramSunGraphics2D.compositeState == 2)
/* 500 */     { if (this.validatedXorComp != paramSunGraphics2D.getComposite()) {
/* 501 */         this.validatedXorComp = (XORComposite)paramSunGraphics2D.getComposite();
/* 502 */         this.renderQueue.setGCMode(this.xgc, false);
/*     */       } 
/*     */ 
/*     */       
/* 506 */       int i = paramSunGraphics2D.pixel;
/* 507 */       if (this.validatedGCForegroundPixel != i) {
/* 508 */         int j = this.validatedXorComp.getXorPixel();
/* 509 */         this.renderQueue.setGCForeground(this.xgc, i ^ j);
/* 510 */         this.validatedGCForegroundPixel = i;
/*     */       } 
/*     */       
/* 513 */       if (bool)
/* 514 */         this.renderQueue.setGCClipRectangles(this.xgc, paramRegion);  }  }
/*     */   public boolean copyArea(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) { if (this.xrpipe == null) { if (!isXRDrawableValid())
/*     */         return true;  makePipes(); }  CompositeType compositeType = paramSunGraphics2D.imageComp; if (paramSunGraphics2D.transformState < 3 && (CompositeType.SrcOverNoEa.equals(compositeType) || CompositeType.SrcNoEa.equals(compositeType))) { paramInt1 += paramSunGraphics2D.transX; paramInt2 += paramSunGraphics2D.transY; try { SunToolkit.awtLock(); boolean bool = canSourceSendExposures(paramInt1, paramInt2, paramInt3, paramInt4); validateCopyAreaGC(paramSunGraphics2D.getCompClip(), bool); this.renderQueue.copyArea(this.xid, this.xid, this.xgc, paramInt1, paramInt2, paramInt3, paramInt4, paramInt1 + paramInt5, paramInt2 + paramInt6); } finally { SunToolkit.awtUnlock(); }  return true; }  return false; }
/*     */   public static SurfaceType getSurfaceType(XRGraphicsConfig paramXRGraphicsConfig, int paramInt) { SurfaceType surfaceType = null; switch (paramInt) { case 1:
/*     */         surfaceType = IntRgbX11; break;
/*     */       case 2:
/*     */       case 3:
/*     */         surfaceType = IntArgbPreX11; break; }  return surfaceType; }
/*     */   public void invalidate() { if (isValid()) { setInvalid(); super.invalidate(); }
/* 523 */      } public synchronized void makePipes() { if (this.xrpipe == null)
/*     */       try {
/* 525 */         SunToolkit.awtLock();
/* 526 */         this.xgc = XCreateGC(getNativeOps());
/*     */         
/* 528 */         this.xrpipe = new XRRenderer(this.maskBuffer.getMaskBuffer());
/* 529 */         this.xrtxpipe = new PixelToShapeConverter(this.xrpipe);
/* 530 */         this.xrtextpipe = (TextPipe)this.maskBuffer.getTextRenderer();
/* 531 */         this.xrDrawImage = new XRDrawImage();
/*     */         
/* 533 */         if (JulesPathBuf.isCairoAvailable()) {
/* 534 */           this
/* 535 */             .aaShapePipe = (ShapeDrawPipe)new JulesShapePipe(XRCompositeManager.getInstance(this));
/* 536 */           this.aaPixelToShapeConv = new PixelToShapeConverter(this.aaShapePipe);
/*     */         } 
/*     */       } finally {
/* 539 */         SunToolkit.awtUnlock();
/*     */       }   }
/*     */ 
/*     */   
/*     */   public static class XRWindowSurfaceData
/*     */     extends XRSurfaceData
/*     */   {
/*     */     public XRWindowSurfaceData(X11ComponentPeer param1X11ComponentPeer, XRGraphicsConfig param1XRGraphicsConfig, SurfaceType param1SurfaceType) {
/* 547 */       super(param1X11ComponentPeer, param1XRGraphicsConfig, param1SurfaceType, param1X11ComponentPeer.getColorModel(), param1X11ComponentPeer
/* 548 */           .getColorModel().getPixelSize(), 1);
/*     */       
/* 550 */       if (isXRDrawableValid()) {
/* 551 */         initXRender(
/* 552 */             XRUtils.getPictureFormatForTransparency(1));
/* 553 */         makePipes();
/*     */       } 
/*     */     }
/*     */     
/*     */     public SurfaceData getReplacement() {
/* 558 */       return this.peer.getSurfaceData();
/*     */     }
/*     */     
/*     */     public Rectangle getBounds() {
/* 562 */       Rectangle rectangle = this.peer.getBounds();
/* 563 */       rectangle.x = rectangle.y = 0;
/* 564 */       return rectangle;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canSourceSendExposures(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 569 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getDestination() {
/* 576 */       return this.peer.getTarget();
/*     */     }
/*     */     
/*     */     public void invalidate() {
/*     */       try {
/* 581 */         SunToolkit.awtLock();
/* 582 */         freeXSDOPicture(getNativeOps());
/*     */       } finally {
/* 584 */         SunToolkit.awtUnlock();
/*     */       } 
/*     */       
/* 587 */       super.invalidate();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class XRInternalSurfaceData extends XRSurfaceData {
/*     */     public XRInternalSurfaceData(XRBackend param1XRBackend, int param1Int) {
/* 593 */       super(param1XRBackend);
/* 594 */       this.picture = param1Int;
/* 595 */       this.transformInUse = false;
/*     */     }
/*     */     
/*     */     public boolean canSourceSendExposures(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 599 */       return false;
/*     */     }
/*     */     
/*     */     public Rectangle getBounds() {
/* 603 */       return null;
/*     */     }
/*     */     
/*     */     public Object getDestination() {
/* 607 */       return null;
/*     */     }
/*     */     
/*     */     public SurfaceData getReplacement() {
/* 611 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class XRPixmapSurfaceData
/*     */     extends XRSurfaceData
/*     */   {
/*     */     Image offscreenImage;
/*     */     
/*     */     int width;
/*     */     int height;
/*     */     int transparency;
/*     */     
/*     */     public XRPixmapSurfaceData(XRGraphicsConfig param1XRGraphicsConfig, int param1Int1, int param1Int2, Image param1Image, SurfaceType param1SurfaceType, ColorModel param1ColorModel, long param1Long, int param1Int3, int param1Int4, int param1Int5) {
/* 626 */       super((X11ComponentPeer)null, param1XRGraphicsConfig, param1SurfaceType, param1ColorModel, param1Int5, param1Int3);
/* 627 */       this.width = param1Int1;
/* 628 */       this.height = param1Int2;
/* 629 */       this.offscreenImage = param1Image;
/* 630 */       this.transparency = param1Int3;
/* 631 */       initSurface(param1Int5, param1Int1, param1Int2, param1Long, param1Int4);
/*     */       
/* 633 */       initXRender(param1Int4);
/* 634 */       makePipes();
/*     */     }
/*     */ 
/*     */     
/*     */     public void initSurface(int param1Int1, int param1Int2, int param1Int3, long param1Long, int param1Int4) {
/*     */       try {
/* 640 */         SunToolkit.awtLock();
/* 641 */         XRInitSurface(param1Int1, param1Int2, param1Int3, param1Long, param1Int4);
/*     */       } finally {
/* 643 */         SunToolkit.awtUnlock();
/*     */       } 
/*     */     }
/*     */     
/*     */     public SurfaceData getReplacement() {
/* 648 */       return restoreContents(this.offscreenImage);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getTransparency() {
/* 658 */       return this.transparency;
/*     */     }
/*     */     
/*     */     public Rectangle getBounds() {
/* 662 */       return new Rectangle(this.width, this.height);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canSourceSendExposures(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 667 */       return (param1Int1 < 0 || param1Int2 < 0 || param1Int1 + param1Int3 > this.width || param1Int2 + param1Int4 > this.height);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void flush() {
/* 679 */       invalidate();
/* 680 */       flushNativeSurface();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getDestination() {
/* 687 */       return this.offscreenImage;
/*     */     }
/*     */   }
/*     */   
/*     */   public long getGC() {
/* 692 */     return this.xgc;
/*     */   }
/*     */   
/*     */   public static class LazyPipe extends ValidatePipe {
/*     */     public boolean validate(SunGraphics2D param1SunGraphics2D) {
/* 697 */       XRSurfaceData xRSurfaceData = (XRSurfaceData)param1SunGraphics2D.surfaceData;
/* 698 */       if (!xRSurfaceData.isXRDrawableValid()) {
/* 699 */         return false;
/*     */       }
/* 701 */       xRSurfaceData.makePipes();
/* 702 */       return super.validate(param1SunGraphics2D);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getPicture() {
/* 707 */     return this.picture;
/*     */   }
/*     */   
/*     */   public int getXid() {
/* 711 */     return this.xid;
/*     */   }
/*     */   
/*     */   public XRGraphicsConfig getGraphicsConfig() {
/* 715 */     return this.graphicsConfig;
/*     */   }
/*     */   
/*     */   public void setStaticSrcTx(AffineTransform paramAffineTransform) {
/* 719 */     this.staticSrcTx = paramAffineTransform;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRSurfaceData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */