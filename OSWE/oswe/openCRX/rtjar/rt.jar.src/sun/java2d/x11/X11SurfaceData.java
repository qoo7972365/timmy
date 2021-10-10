/*     */ package sun.java2d.x11;
/*     */ 
/*     */ import java.awt.Composite;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Image;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.security.AccessController;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.awt.X11ComponentPeer;
/*     */ import sun.awt.X11GraphicsConfig;
/*     */ import sun.awt.image.PixelConverter;
/*     */ import sun.font.X11TextRenderer;
/*     */ import sun.java2d.InvalidPipeException;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SunGraphicsEnvironment;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.SurfaceDataProxy;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.GraphicsPrimitive;
/*     */ import sun.java2d.loops.RenderLoops;
/*     */ import sun.java2d.loops.SurfaceType;
/*     */ import sun.java2d.loops.XORComposite;
/*     */ import sun.java2d.pipe.PixelToShapeConverter;
/*     */ import sun.java2d.pipe.Region;
/*     */ import sun.java2d.pipe.TextPipe;
/*     */ import sun.java2d.pipe.ValidatePipe;
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
/*     */ public abstract class X11SurfaceData
/*     */   extends XSurfaceData
/*     */ {
/*     */   X11ComponentPeer peer;
/*     */   X11GraphicsConfig graphicsConfig;
/*     */   private RenderLoops solidloops;
/*     */   protected int depth;
/*     */   public static final String DESC_INT_BGR_X11 = "Integer BGR Pixmap";
/*     */   public static final String DESC_INT_RGB_X11 = "Integer RGB Pixmap";
/*     */   public static final String DESC_4BYTE_ABGR_PRE_X11 = "4 byte ABGR Pixmap with pre-multplied alpha";
/*     */   public static final String DESC_INT_ARGB_PRE_X11 = "Integer ARGB Pixmap with pre-multiplied alpha";
/*     */   public static final String DESC_BYTE_IND_OPQ_X11 = "Byte Indexed Opaque Pixmap";
/*     */   public static final String DESC_INT_BGR_X11_BM = "Integer BGR Pixmap with 1-bit transp";
/*     */   public static final String DESC_INT_RGB_X11_BM = "Integer RGB Pixmap with 1-bit transp";
/*     */   public static final String DESC_BYTE_IND_X11_BM = "Byte Indexed Pixmap with 1-bit transp";
/*     */   public static final String DESC_BYTE_GRAY_X11 = "Byte Gray Opaque Pixmap";
/*     */   public static final String DESC_INDEX8_GRAY_X11 = "Index8 Gray Opaque Pixmap";
/*     */   public static final String DESC_BYTE_GRAY_X11_BM = "Byte Gray Opaque Pixmap with 1-bit transp";
/*     */   public static final String DESC_INDEX8_GRAY_X11_BM = "Index8 Gray Opaque Pixmap with 1-bit transp";
/*     */   public static final String DESC_3BYTE_RGB_X11 = "3 Byte RGB Pixmap";
/*     */   public static final String DESC_3BYTE_BGR_X11 = "3 Byte BGR Pixmap";
/*     */   public static final String DESC_3BYTE_RGB_X11_BM = "3 Byte RGB Pixmap with 1-bit transp";
/*     */   public static final String DESC_3BYTE_BGR_X11_BM = "3 Byte BGR Pixmap with 1-bit transp";
/*     */   public static final String DESC_USHORT_555_RGB_X11 = "Ushort 555 RGB Pixmap";
/*     */   public static final String DESC_USHORT_565_RGB_X11 = "Ushort 565 RGB Pixmap";
/*     */   public static final String DESC_USHORT_555_RGB_X11_BM = "Ushort 555 RGB Pixmap with 1-bit transp";
/*     */   public static final String DESC_USHORT_565_RGB_X11_BM = "Ushort 565 RGB Pixmap with 1-bit transp";
/*     */   public static final String DESC_USHORT_INDEXED_X11 = "Ushort Indexed Pixmap";
/*     */   public static final String DESC_USHORT_INDEXED_X11_BM = "Ushort Indexed Pixmap with 1-bit transp";
/* 137 */   public static final SurfaceType IntBgrX11 = SurfaceType.IntBgr
/* 138 */     .deriveSubType("Integer BGR Pixmap");
/* 139 */   public static final SurfaceType IntRgbX11 = SurfaceType.IntRgb
/* 140 */     .deriveSubType("Integer RGB Pixmap");
/*     */   
/* 142 */   public static final SurfaceType FourByteAbgrPreX11 = SurfaceType.FourByteAbgrPre
/* 143 */     .deriveSubType("4 byte ABGR Pixmap with pre-multplied alpha");
/* 144 */   public static final SurfaceType IntArgbPreX11 = SurfaceType.IntArgbPre
/* 145 */     .deriveSubType("Integer ARGB Pixmap with pre-multiplied alpha");
/*     */   
/* 147 */   public static final SurfaceType ThreeByteRgbX11 = SurfaceType.ThreeByteRgb
/* 148 */     .deriveSubType("3 Byte RGB Pixmap");
/* 149 */   public static final SurfaceType ThreeByteBgrX11 = SurfaceType.ThreeByteBgr
/* 150 */     .deriveSubType("3 Byte BGR Pixmap");
/*     */   
/* 152 */   public static final SurfaceType UShort555RgbX11 = SurfaceType.Ushort555Rgb
/* 153 */     .deriveSubType("Ushort 555 RGB Pixmap");
/* 154 */   public static final SurfaceType UShort565RgbX11 = SurfaceType.Ushort565Rgb
/* 155 */     .deriveSubType("Ushort 565 RGB Pixmap");
/*     */   
/* 157 */   public static final SurfaceType UShortIndexedX11 = SurfaceType.UshortIndexed
/* 158 */     .deriveSubType("Ushort Indexed Pixmap");
/*     */   
/* 160 */   public static final SurfaceType ByteIndexedOpaqueX11 = SurfaceType.ByteIndexedOpaque
/* 161 */     .deriveSubType("Byte Indexed Opaque Pixmap");
/*     */   
/* 163 */   public static final SurfaceType ByteGrayX11 = SurfaceType.ByteGray
/* 164 */     .deriveSubType("Byte Gray Opaque Pixmap");
/* 165 */   public static final SurfaceType Index8GrayX11 = SurfaceType.Index8Gray
/* 166 */     .deriveSubType("Index8 Gray Opaque Pixmap");
/*     */ 
/*     */   
/* 169 */   public static final SurfaceType IntBgrX11_BM = SurfaceType.Custom
/* 170 */     .deriveSubType("Integer BGR Pixmap with 1-bit transp", PixelConverter.Xbgr.instance);
/*     */   
/* 172 */   public static final SurfaceType IntRgbX11_BM = SurfaceType.Custom
/* 173 */     .deriveSubType("Integer RGB Pixmap with 1-bit transp", PixelConverter.Xrgb.instance);
/*     */ 
/*     */   
/* 176 */   public static final SurfaceType ThreeByteRgbX11_BM = SurfaceType.Custom
/* 177 */     .deriveSubType("3 Byte RGB Pixmap with 1-bit transp", PixelConverter.Xbgr.instance);
/*     */   
/* 179 */   public static final SurfaceType ThreeByteBgrX11_BM = SurfaceType.Custom
/* 180 */     .deriveSubType("3 Byte BGR Pixmap with 1-bit transp", PixelConverter.Xrgb.instance);
/*     */ 
/*     */   
/* 183 */   public static final SurfaceType UShort555RgbX11_BM = SurfaceType.Custom
/* 184 */     .deriveSubType("Ushort 555 RGB Pixmap with 1-bit transp", PixelConverter.Ushort555Rgb.instance);
/*     */   
/* 186 */   public static final SurfaceType UShort565RgbX11_BM = SurfaceType.Custom
/* 187 */     .deriveSubType("Ushort 565 RGB Pixmap with 1-bit transp", PixelConverter.Ushort565Rgb.instance);
/*     */ 
/*     */   
/* 190 */   public static final SurfaceType UShortIndexedX11_BM = SurfaceType.Custom
/* 191 */     .deriveSubType("Ushort Indexed Pixmap with 1-bit transp");
/*     */   
/* 193 */   public static final SurfaceType ByteIndexedX11_BM = SurfaceType.Custom
/* 194 */     .deriveSubType("Byte Indexed Pixmap with 1-bit transp");
/*     */   
/* 196 */   public static final SurfaceType ByteGrayX11_BM = SurfaceType.Custom
/* 197 */     .deriveSubType("Byte Gray Opaque Pixmap with 1-bit transp");
/* 198 */   public static final SurfaceType Index8GrayX11_BM = SurfaceType.Custom
/* 199 */     .deriveSubType("Index8 Gray Opaque Pixmap with 1-bit transp");
/*     */ 
/*     */   
/* 202 */   private static Boolean accelerationEnabled = null; protected X11Renderer x11pipe; protected PixelToShapeConverter x11txpipe; protected static TextPipe x11textpipe; protected static boolean dgaAvailable; private long xgc; private Region validatedClip; private XORComposite validatedXorComp; private int xorpixelmod; private int validatedPixel; private boolean validatedExposures;
/*     */   
/*     */   public Raster getRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 205 */     throw new InternalError("not implemented yet");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 214 */     if (!isX11SurfaceDataInitialized() && 
/* 215 */       !GraphicsEnvironment.isHeadless()) {
/*     */ 
/*     */       
/* 218 */       String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("javax.accessibility.screen_magnifier_present"));
/* 219 */       boolean bool = (str1 == null || !"true".equals(str1)) ? true : false;
/*     */       
/* 221 */       initIDs(XORComposite.class, bool);
/*     */ 
/*     */       
/* 224 */       String str2 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.xtextpipe"));
/* 225 */       if (str2 == null || "true".startsWith(str2)) {
/* 226 */         if ("true".equals(str2))
/*     */         {
/* 228 */           System.out.println("using X11 text renderer");
/*     */         }
/* 230 */         x11textpipe = (TextPipe)new X11TextRenderer();
/* 231 */         if (GraphicsPrimitive.tracingEnabled()) {
/* 232 */           x11textpipe = (TextPipe)((X11TextRenderer)x11textpipe).traceWrap();
/*     */         }
/*     */       } else {
/* 235 */         if ("false".equals(str2))
/*     */         {
/* 237 */           System.out.println("using DGA text renderer");
/*     */         }
/* 239 */         x11textpipe = solidTextRenderer;
/*     */       } 
/*     */       
/* 242 */       dgaAvailable = isDgaAvailable();
/*     */       
/* 244 */       if (isAccelerationEnabled()) {
/* 245 */         X11PMBlitLoops.register();
/* 246 */         X11PMBlitBgLoops.register();
/*     */       } 
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
/*     */   public static boolean isAccelerationEnabled() {
/* 262 */     if (accelerationEnabled == null)
/*     */     {
/* 264 */       if (GraphicsEnvironment.isHeadless()) {
/* 265 */         accelerationEnabled = Boolean.FALSE;
/*     */       } else {
/*     */         
/* 268 */         String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.pmoffscreen"));
/*     */         
/* 270 */         if (str != null) {
/*     */           
/* 272 */           accelerationEnabled = Boolean.valueOf(str);
/*     */         } else {
/* 274 */           boolean bool = false;
/* 275 */           GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 276 */           if (graphicsEnvironment instanceof SunGraphicsEnvironment) {
/* 277 */             bool = ((SunGraphicsEnvironment)graphicsEnvironment).isDisplayLocal();
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 284 */           accelerationEnabled = Boolean.valueOf((!isDgaAvailable() && (!bool || isShmPMAvailable())));
/*     */         } 
/*     */       } 
/*     */     }
/* 288 */     return accelerationEnabled.booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public SurfaceDataProxy makeProxyFor(SurfaceData paramSurfaceData) {
/* 293 */     return X11SurfaceDataProxy.createProxy(paramSurfaceData, this.graphicsConfig);
/*     */   }
/*     */   
/*     */   public void validatePipe(SunGraphics2D paramSunGraphics2D) {
/* 297 */     if (paramSunGraphics2D.antialiasHint != 2 && paramSunGraphics2D.paintState <= 1 && (paramSunGraphics2D.compositeState <= 0 || paramSunGraphics2D.compositeState == 2)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 302 */       if (this.x11txpipe == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 310 */         paramSunGraphics2D.drawpipe = lazypipe;
/* 311 */         paramSunGraphics2D.fillpipe = lazypipe;
/* 312 */         paramSunGraphics2D.shapepipe = lazypipe;
/* 313 */         paramSunGraphics2D.imagepipe = lazypipe;
/* 314 */         paramSunGraphics2D.textpipe = lazypipe;
/*     */         
/*     */         return;
/*     */       } 
/* 318 */       if (paramSunGraphics2D.clipState == 2) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 323 */         super.validatePipe(paramSunGraphics2D);
/*     */       } else {
/* 325 */         switch (paramSunGraphics2D.textAntialiasHint) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 0:
/*     */           case 1:
/* 332 */             if (paramSunGraphics2D.compositeState == 0) {
/* 333 */               paramSunGraphics2D.textpipe = x11textpipe; break;
/*     */             } 
/* 335 */             paramSunGraphics2D.textpipe = solidTextRenderer;
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 2:
/* 342 */             paramSunGraphics2D.textpipe = aaTextRenderer;
/*     */             break;
/*     */           
/*     */           default:
/* 346 */             switch ((paramSunGraphics2D.getFontInfo()).aaHint) {
/*     */               
/*     */               case 4:
/*     */               case 6:
/* 350 */                 paramSunGraphics2D.textpipe = lcdTextRenderer;
/*     */                 break;
/*     */ 
/*     */ 
/*     */               
/*     */               case 1:
/* 356 */                 if (paramSunGraphics2D.compositeState == 0) {
/* 357 */                   paramSunGraphics2D.textpipe = x11textpipe; break;
/*     */                 } 
/* 359 */                 paramSunGraphics2D.textpipe = solidTextRenderer;
/*     */                 break;
/*     */ 
/*     */               
/*     */               case 2:
/* 364 */                 paramSunGraphics2D.textpipe = aaTextRenderer;
/*     */                 break;
/*     */             } 
/*     */             
/* 368 */             paramSunGraphics2D.textpipe = solidTextRenderer;
/*     */             break;
/*     */         } 
/*     */       
/*     */       } 
/* 373 */       if (paramSunGraphics2D.transformState >= 3) {
/* 374 */         paramSunGraphics2D.drawpipe = this.x11txpipe;
/* 375 */         paramSunGraphics2D.fillpipe = this.x11txpipe;
/* 376 */       } else if (paramSunGraphics2D.strokeState != 0) {
/* 377 */         paramSunGraphics2D.drawpipe = this.x11txpipe;
/* 378 */         paramSunGraphics2D.fillpipe = this.x11pipe;
/*     */       } else {
/* 380 */         paramSunGraphics2D.drawpipe = this.x11pipe;
/* 381 */         paramSunGraphics2D.fillpipe = this.x11pipe;
/*     */       } 
/* 383 */       paramSunGraphics2D.shapepipe = this.x11pipe;
/* 384 */       paramSunGraphics2D.imagepipe = imagepipe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 391 */       if (paramSunGraphics2D.loops == null)
/*     */       {
/* 393 */         paramSunGraphics2D.loops = getRenderLoops(paramSunGraphics2D);
/*     */       }
/*     */     } else {
/* 396 */       super.validatePipe(paramSunGraphics2D);
/*     */     } 
/*     */   }
/*     */   
/*     */   public RenderLoops getRenderLoops(SunGraphics2D paramSunGraphics2D) {
/* 401 */     if (paramSunGraphics2D.paintState <= 1 && paramSunGraphics2D.compositeState <= 0)
/*     */     {
/*     */       
/* 404 */       return this.solidloops;
/*     */     }
/* 406 */     return super.getRenderLoops(paramSunGraphics2D);
/*     */   }
/*     */   
/*     */   public GraphicsConfiguration getDeviceConfiguration() {
/* 410 */     return (GraphicsConfiguration)this.graphicsConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static X11WindowSurfaceData createData(X11ComponentPeer paramX11ComponentPeer) {
/* 417 */     X11GraphicsConfig x11GraphicsConfig = getGC(paramX11ComponentPeer);
/* 418 */     return new X11WindowSurfaceData(paramX11ComponentPeer, x11GraphicsConfig, x11GraphicsConfig.getSurfaceType());
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
/*     */   public static X11PixmapSurfaceData createData(X11GraphicsConfig paramX11GraphicsConfig, int paramInt1, int paramInt2, ColorModel paramColorModel, Image paramImage, long paramLong, int paramInt3) {
/* 430 */     return new X11PixmapSurfaceData(paramX11GraphicsConfig, paramInt1, paramInt2, paramImage, 
/* 431 */         getSurfaceType(paramX11GraphicsConfig, paramInt3, true), paramColorModel, paramLong, paramInt3);
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
/*     */   protected X11SurfaceData(X11ComponentPeer paramX11ComponentPeer, X11GraphicsConfig paramX11GraphicsConfig, SurfaceType paramSurfaceType, ColorModel paramColorModel) {
/* 445 */     super(paramSurfaceType, paramColorModel);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 638 */     this.validatedExposures = true; this.peer = paramX11ComponentPeer; this.graphicsConfig = paramX11GraphicsConfig; this.solidloops = this.graphicsConfig.getSolidLoops(paramSurfaceType); this.depth = paramColorModel.getPixelSize(); initOps(paramX11ComponentPeer, this.graphicsConfig, this.depth); if (isAccelerationEnabled())
/*     */       setBlitProxyKey(paramX11GraphicsConfig.getProxyKey()); 
/*     */   }
/*     */   public static X11GraphicsConfig getGC(X11ComponentPeer paramX11ComponentPeer) { if (paramX11ComponentPeer != null)
/*     */       return (X11GraphicsConfig)paramX11ComponentPeer.getGraphicsConfiguration();  GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment(); GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice(); return (X11GraphicsConfig)graphicsDevice.getDefaultConfiguration(); } public boolean copyArea(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) { if (this.x11pipe == null) { if (!isDrawableValid())
/*     */         return true;  makePipes(); }  CompositeType compositeType = paramSunGraphics2D.imageComp; if (paramSunGraphics2D.transformState < 3 && (CompositeType.SrcOverNoEa.equals(compositeType) || CompositeType.SrcNoEa.equals(compositeType))) { paramInt1 += paramSunGraphics2D.transX; paramInt2 += paramSunGraphics2D.transY; SunToolkit.awtLock(); try { boolean bool = canSourceSendExposures(paramInt1, paramInt2, paramInt3, paramInt4); long l = getBlitGC(paramSunGraphics2D.getCompClip(), bool); this.x11pipe.devCopyArea(getNativeOps(), l, paramInt1, paramInt2, paramInt1 + paramInt5, paramInt2 + paramInt6, paramInt3, paramInt4); } finally { SunToolkit.awtUnlock(); }  return true; }
/* 644 */      return false; } public final long getRenderGC(Region paramRegion, int paramInt1, Composite paramComposite, int paramInt2) { return getGC(paramRegion, paramInt1, paramComposite, paramInt2, this.validatedExposures); }
/*     */   public static SurfaceType getSurfaceType(X11GraphicsConfig paramX11GraphicsConfig, int paramInt) { return getSurfaceType(paramX11GraphicsConfig, paramInt, false); }
/*     */   public static SurfaceType getSurfaceType(X11GraphicsConfig paramX11GraphicsConfig, int paramInt, boolean paramBoolean) { SurfaceType surfaceType; boolean bool = (paramInt == 2) ? true : false; ColorModel colorModel = paramX11GraphicsConfig.getColorModel(); switch (colorModel.getPixelSize()) { case 24: if (paramX11GraphicsConfig.getBitsPerPixel() == 24) { if (colorModel instanceof DirectColorModel) { surfaceType = bool ? ThreeByteBgrX11_BM : ThreeByteBgrX11; } else { throw new InvalidPipeException("Unsupported bit depth/cm combo: " + colorModel.getPixelSize() + ", " + colorModel); }  return surfaceType; } case 32: if (colorModel instanceof DirectColorModel) { if (((SunToolkit)Toolkit.getDefaultToolkit()).isTranslucencyCapable((GraphicsConfiguration)paramX11GraphicsConfig) && !paramBoolean) { surfaceType = IntArgbPreX11; } else if (((DirectColorModel)colorModel).getRedMask() == 16711680) { surfaceType = bool ? IntRgbX11_BM : IntRgbX11; } else { surfaceType = bool ? IntBgrX11_BM : IntBgrX11; }  } else if (colorModel instanceof java.awt.image.ComponentColorModel) { surfaceType = FourByteAbgrPreX11; } else { throw new InvalidPipeException("Unsupported bit depth/cm combo: " + colorModel.getPixelSize() + ", " + colorModel); }  return surfaceType;case 15: surfaceType = bool ? UShort555RgbX11_BM : UShort555RgbX11; return surfaceType;case 16: if (colorModel instanceof DirectColorModel && ((DirectColorModel)colorModel).getGreenMask() == 992) { surfaceType = bool ? UShort555RgbX11_BM : UShort555RgbX11; } else { surfaceType = bool ? UShort565RgbX11_BM : UShort565RgbX11; }  return surfaceType;case 12: if (colorModel instanceof IndexColorModel) { surfaceType = bool ? UShortIndexedX11_BM : UShortIndexedX11; } else { throw new InvalidPipeException("Unsupported bit depth: " + colorModel.getPixelSize() + " cm=" + colorModel); }  return surfaceType;
/*     */       case 8: if (colorModel.getColorSpace().getType() == 6 && colorModel instanceof java.awt.image.ComponentColorModel) { surfaceType = bool ? ByteGrayX11_BM : ByteGrayX11; } else if (colorModel instanceof IndexColorModel && isOpaqueGray((IndexColorModel)colorModel)) { surfaceType = bool ? Index8GrayX11_BM : Index8GrayX11; } else { surfaceType = bool ? ByteIndexedX11_BM : ByteIndexedOpaqueX11; }  return surfaceType; }  throw new InvalidPipeException("Unsupported bit depth: " + colorModel.getPixelSize()); }
/* 648 */   public void invalidate() { if (isValid()) { setInvalid(); super.invalidate(); }  } public final long getBlitGC(Region paramRegion, boolean paramBoolean) { return getGC(paramRegion, 0, (Composite)null, this.validatedPixel, paramBoolean); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final long getGC(Region paramRegion, int paramInt1, Composite paramComposite, int paramInt2, boolean paramBoolean) {
/* 658 */     if (!isValid()) {
/* 659 */       throw new InvalidPipeException("bounds changed");
/*     */     }
/*     */ 
/*     */     
/* 663 */     if (paramRegion != this.validatedClip) {
/* 664 */       this.validatedClip = paramRegion;
/* 665 */       if (paramRegion != null) {
/* 666 */         XSetClip(this.xgc, paramRegion
/* 667 */             .getLoX(), paramRegion.getLoY(), paramRegion
/* 668 */             .getHiX(), paramRegion.getHiY(), 
/* 669 */             paramRegion.isRectangular() ? null : paramRegion);
/*     */       } else {
/* 671 */         XResetClip(this.xgc);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 676 */     if (paramInt1 == 0) {
/* 677 */       if (this.validatedXorComp != null) {
/* 678 */         this.validatedXorComp = null;
/* 679 */         this.xorpixelmod = 0;
/* 680 */         XSetCopyMode(this.xgc);
/*     */       }
/*     */     
/* 683 */     } else if (this.validatedXorComp != paramComposite) {
/* 684 */       this.validatedXorComp = (XORComposite)paramComposite;
/* 685 */       this.xorpixelmod = this.validatedXorComp.getXorPixel();
/* 686 */       XSetXorMode(this.xgc);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 691 */     paramInt2 ^= this.xorpixelmod;
/* 692 */     if (paramInt2 != this.validatedPixel) {
/* 693 */       this.validatedPixel = paramInt2;
/* 694 */       XSetForeground(this.xgc, paramInt2);
/*     */     } 
/*     */     
/* 697 */     if (this.validatedExposures != paramBoolean) {
/* 698 */       this.validatedExposures = paramBoolean;
/* 699 */       XSetGraphicsExposures(this.xgc, paramBoolean);
/*     */     } 
/*     */     
/* 702 */     return this.xgc;
/*     */   }
/*     */   
/*     */   public synchronized void makePipes() {
/* 706 */     if (this.x11pipe == null) {
/* 707 */       SunToolkit.awtLock();
/*     */       try {
/* 709 */         this.xgc = XCreateGC(getNativeOps());
/*     */       } finally {
/* 711 */         SunToolkit.awtUnlock();
/*     */       } 
/* 713 */       this.x11pipe = X11Renderer.getInstance();
/* 714 */       this.x11txpipe = new PixelToShapeConverter(this.x11pipe);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class X11WindowSurfaceData
/*     */     extends X11SurfaceData
/*     */   {
/*     */     public X11WindowSurfaceData(X11ComponentPeer param1X11ComponentPeer, X11GraphicsConfig param1X11GraphicsConfig, SurfaceType param1SurfaceType) {
/* 722 */       super(param1X11ComponentPeer, param1X11GraphicsConfig, param1SurfaceType, param1X11ComponentPeer.getColorModel());
/* 723 */       if (isDrawableValid()) {
/* 724 */         makePipes();
/*     */       }
/*     */     }
/*     */     
/*     */     public SurfaceData getReplacement() {
/* 729 */       return this.peer.getSurfaceData();
/*     */     }
/*     */     
/*     */     public Rectangle getBounds() {
/* 733 */       Rectangle rectangle = this.peer.getBounds();
/* 734 */       rectangle.x = rectangle.y = 0;
/* 735 */       return rectangle;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canSourceSendExposures(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 740 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getDestination() {
/* 747 */       return this.peer.getTarget();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class X11PixmapSurfaceData
/*     */     extends X11SurfaceData
/*     */   {
/*     */     Image offscreenImage;
/*     */     
/*     */     int width;
/*     */     
/*     */     int height;
/*     */     
/*     */     int transparency;
/*     */     
/*     */     public X11PixmapSurfaceData(X11GraphicsConfig param1X11GraphicsConfig, int param1Int1, int param1Int2, Image param1Image, SurfaceType param1SurfaceType, ColorModel param1ColorModel, long param1Long, int param1Int3) {
/* 764 */       super((X11ComponentPeer)null, param1X11GraphicsConfig, param1SurfaceType, param1ColorModel);
/* 765 */       this.width = param1Int1;
/* 766 */       this.height = param1Int2;
/* 767 */       this.offscreenImage = param1Image;
/* 768 */       this.transparency = param1Int3;
/* 769 */       initSurface(this.depth, param1Int1, param1Int2, param1Long);
/* 770 */       makePipes();
/*     */     }
/*     */     
/*     */     public SurfaceData getReplacement() {
/* 774 */       return restoreContents(this.offscreenImage);
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
/*     */     public int getTransparency() {
/* 786 */       return this.transparency;
/*     */     }
/*     */     
/*     */     public Rectangle getBounds() {
/* 790 */       return new Rectangle(this.width, this.height);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canSourceSendExposures(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 795 */       return (param1Int1 < 0 || param1Int2 < 0 || param1Int1 + param1Int3 > this.width || param1Int2 + param1Int4 > this.height);
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
/* 807 */       invalidate();
/* 808 */       flushNativeSurface();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getDestination() {
/* 815 */       return this.offscreenImage;
/*     */     }
/*     */   }
/*     */   
/* 819 */   private static LazyPipe lazypipe = new LazyPipe(); private static native void initIDs(Class paramClass, boolean paramBoolean); protected native void initSurface(int paramInt1, int paramInt2, int paramInt3, long paramLong); public static native boolean isDgaAvailable(); private static native boolean isShmPMAvailable(); public abstract boolean canSourceSendExposures(int paramInt1, int paramInt2, int paramInt3, int paramInt4); private static native void XSetCopyMode(long paramLong);
/*     */   private static native void XSetXorMode(long paramLong);
/*     */   private static native void XSetForeground(long paramLong, int paramInt);
/*     */   public static class LazyPipe extends ValidatePipe { public boolean validate(SunGraphics2D param1SunGraphics2D) {
/* 823 */       X11SurfaceData x11SurfaceData = (X11SurfaceData)param1SunGraphics2D.surfaceData;
/* 824 */       if (!x11SurfaceData.isDrawableValid()) {
/* 825 */         return false;
/*     */       }
/* 827 */       x11SurfaceData.makePipes();
/* 828 */       return super.validate(param1SunGraphics2D);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/x11/X11SurfaceData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */