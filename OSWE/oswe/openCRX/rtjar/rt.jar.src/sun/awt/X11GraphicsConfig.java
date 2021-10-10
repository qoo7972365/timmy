/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.AWTException;
/*     */ import java.awt.BufferCapabilities;
/*     */ import java.awt.Component;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.Image;
/*     */ import java.awt.ImageCapabilities;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.SystemColor;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ComponentColorModel;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.VolatileImage;
/*     */ import java.awt.image.WritableRaster;
/*     */ import sun.awt.image.OffScreenImage;
/*     */ import sun.awt.image.SunVolatileImage;
/*     */ import sun.awt.image.SurfaceManager;
/*     */ import sun.java2d.Disposer;
/*     */ import sun.java2d.DisposerRecord;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.RenderLoops;
/*     */ import sun.java2d.loops.SurfaceType;
/*     */ import sun.java2d.x11.X11SurfaceData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X11GraphicsConfig
/*     */   extends GraphicsConfiguration
/*     */   implements SurfaceManager.ProxiedGraphicsConfig
/*     */ {
/*     */   protected X11GraphicsDevice screen;
/*     */   protected int visual;
/*     */   int depth;
/*     */   int colormap;
/*     */   ColorModel colorModel;
/*     */   long aData;
/*     */   boolean doubleBuffer;
/*  77 */   private Object disposerReferent = new Object();
/*     */   private BufferCapabilities bufferCaps;
/*  79 */   private static ImageCapabilities imageCaps = new ImageCapabilities(
/*  80 */       X11SurfaceData.isAccelerationEnabled());
/*     */ 
/*     */   
/*     */   protected int bitsPerPixel;
/*     */ 
/*     */   
/*     */   protected SurfaceType surfaceType;
/*     */ 
/*     */   
/*     */   public RenderLoops solidloops;
/*     */ 
/*     */ 
/*     */   
/*     */   public static X11GraphicsConfig getConfig(X11GraphicsDevice paramX11GraphicsDevice, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/*  94 */     return new X11GraphicsConfig(paramX11GraphicsDevice, paramInt1, paramInt2, paramInt3, paramBoolean);
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
/*     */   public static X11GraphicsConfig getConfig(X11GraphicsDevice paramX11GraphicsDevice, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 110 */     return new X11GraphicsConfig(paramX11GraphicsDevice, paramInt1, paramInt2, paramInt3, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected X11GraphicsConfig(X11GraphicsDevice paramX11GraphicsDevice, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 121 */     this.screen = paramX11GraphicsDevice;
/* 122 */     this.visual = paramInt1;
/* 123 */     this.doubleBuffer = paramBoolean;
/* 124 */     this.depth = paramInt2;
/* 125 */     this.colormap = paramInt3;
/* 126 */     init(paramInt1, this.screen.getScreen());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     long l = getAData();
/* 132 */     Disposer.addRecord(this.disposerReferent, new X11GCDisposerRecord(l));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsDevice getDevice() {
/* 140 */     return this.screen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVisual() {
/* 147 */     return this.visual;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDepth() {
/* 155 */     return this.depth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColormap() {
/* 162 */     return this.colormap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBitsPerPixel() {
/* 170 */     return this.bitsPerPixel;
/*     */   }
/*     */   
/*     */   public synchronized SurfaceType getSurfaceType() {
/* 174 */     if (this.surfaceType != null) {
/* 175 */       return this.surfaceType;
/*     */     }
/*     */     
/* 178 */     this.surfaceType = X11SurfaceData.getSurfaceType(this, 1);
/* 179 */     return this.surfaceType;
/*     */   }
/*     */   
/*     */   public Object getProxyKey() {
/* 183 */     return this.screen.getProxyKeyFor(getSurfaceType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized RenderLoops getSolidLoops(SurfaceType paramSurfaceType) {
/* 191 */     if (this.solidloops == null) {
/* 192 */       this.solidloops = SurfaceData.makeRenderLoops(SurfaceType.OpaqueColor, CompositeType.SrcNoEa, paramSurfaceType);
/*     */     }
/*     */ 
/*     */     
/* 196 */     return this.solidloops;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ColorModel getColorModel() {
/* 203 */     if (this.colorModel == null) {
/*     */       
/* 205 */       SystemColor.window.getRGB();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 211 */       this.colorModel = makeColorModel();
/* 212 */       if (this.colorModel == null) {
/* 213 */         this.colorModel = Toolkit.getDefaultToolkit().getColorModel();
/*     */       }
/*     */     } 
/* 216 */     return this.colorModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorModel getColorModel(int paramInt) {
/* 224 */     switch (paramInt) {
/*     */       case 1:
/* 226 */         return getColorModel();
/*     */       case 2:
/* 228 */         return new DirectColorModel(25, 16711680, 65280, 255, 16777216);
/*     */       case 3:
/* 230 */         return ColorModel.getRGBdefault();
/*     */     } 
/* 232 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DirectColorModel createDCM32(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/* 238 */     return new DirectColorModel(
/* 239 */         ColorSpace.getInstance(1000), 32, paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ComponentColorModel createABGRCCM() {
/* 244 */     ColorSpace colorSpace = ColorSpace.getInstance(1000);
/* 245 */     int[] arrayOfInt1 = { 8, 8, 8, 8 };
/* 246 */     int[] arrayOfInt2 = { 3, 2, 1, 0 };
/* 247 */     return new ComponentColorModel(colorSpace, arrayOfInt1, true, true, 3, 0);
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
/*     */   public AffineTransform getDefaultTransform() {
/* 262 */     return new AffineTransform();
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
/*     */   public AffineTransform getNormalizingTransform() {
/* 285 */     double d1 = getXResolution(this.screen.getScreen()) / 72.0D;
/* 286 */     double d2 = getYResolution(this.screen.getScreen()) / 72.0D;
/* 287 */     return new AffineTransform(d1, 0.0D, 0.0D, d2, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getAData() {
/* 294 */     return this.aData;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 298 */     return "X11GraphicsConfig[dev=" + this.screen + ",vis=0x" + 
/* 299 */       Integer.toHexString(this.visual) + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 310 */     initIDs();
/*     */   }
/*     */   
/*     */   public Rectangle getBounds() {
/* 314 */     return pGetBounds(this.screen.getScreen());
/*     */   }
/*     */   
/*     */   private static class XDBECapabilities
/*     */     extends BufferCapabilities
/*     */   {
/*     */     public XDBECapabilities() {
/* 321 */       super(X11GraphicsConfig.imageCaps, X11GraphicsConfig.imageCaps, BufferCapabilities.FlipContents.UNDEFINED);
/*     */     }
/*     */   }
/*     */   
/*     */   public BufferCapabilities getBufferCapabilities() {
/* 326 */     if (this.bufferCaps == null) {
/* 327 */       if (this.doubleBuffer) {
/* 328 */         this.bufferCaps = new XDBECapabilities();
/*     */       } else {
/* 330 */         this.bufferCaps = super.getBufferCapabilities();
/*     */       } 
/*     */     }
/* 333 */     return this.bufferCaps;
/*     */   }
/*     */   
/*     */   public ImageCapabilities getImageCapabilities() {
/* 337 */     return imageCaps;
/*     */   }
/*     */   
/*     */   public boolean isDoubleBuffered() {
/* 341 */     return this.doubleBuffer;
/*     */   }
/*     */   
/*     */   private static class X11GCDisposerRecord
/*     */     implements DisposerRecord {
/*     */     private long x11ConfigData;
/*     */     
/*     */     public X11GCDisposerRecord(long param1Long) {
/* 349 */       this.x11ConfigData = param1Long;
/*     */     }
/*     */     public synchronized void dispose() {
/* 352 */       if (this.x11ConfigData != 0L) {
/* 353 */         X11GraphicsConfig.dispose(this.x11ConfigData);
/* 354 */         this.x11ConfigData = 0L;
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
/*     */ 
/*     */   
/*     */   public SurfaceData createSurfaceData(X11ComponentPeer paramX11ComponentPeer) {
/* 372 */     return (SurfaceData)X11SurfaceData.createData(paramX11ComponentPeer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image createAcceleratedImage(Component paramComponent, int paramInt1, int paramInt2) {
/* 383 */     ColorModel colorModel = getColorModel(1);
/*     */     
/* 385 */     WritableRaster writableRaster = colorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/* 386 */     return new OffScreenImage(paramComponent, colorModel, writableRaster, colorModel
/* 387 */         .isAlphaPremultiplied());
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
/*     */   public long createBackBuffer(X11ComponentPeer paramX11ComponentPeer, int paramInt, BufferCapabilities paramBufferCapabilities) throws AWTException {
/* 408 */     if (!X11GraphicsDevice.isDBESupported()) {
/* 409 */       throw new AWTException("Page flipping is not supported");
/*     */     }
/* 411 */     if (paramInt > 2) {
/* 412 */       throw new AWTException("Only double or single buffering is supported");
/*     */     }
/*     */     
/* 415 */     BufferCapabilities bufferCapabilities = getBufferCapabilities();
/* 416 */     if (!bufferCapabilities.isPageFlipping()) {
/* 417 */       throw new AWTException("Page flipping is not supported");
/*     */     }
/*     */     
/* 420 */     long l = paramX11ComponentPeer.getContentWindow();
/* 421 */     int i = getSwapAction(paramBufferCapabilities.getFlipContents());
/*     */     
/* 423 */     return createBackBuffer(l, i);
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
/*     */   public VolatileImage createBackBufferImage(Component paramComponent, long paramLong) {
/* 440 */     int i = Math.max(1, paramComponent.getWidth());
/* 441 */     int j = Math.max(1, paramComponent.getHeight());
/* 442 */     return new SunVolatileImage(paramComponent, i, j, 
/*     */         
/* 444 */         Long.valueOf(paramLong));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flip(X11ComponentPeer paramX11ComponentPeer, Component paramComponent, VolatileImage paramVolatileImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, BufferCapabilities.FlipContents paramFlipContents) {
/* 455 */     long l = paramX11ComponentPeer.getContentWindow();
/* 456 */     int i = getSwapAction(paramFlipContents);
/* 457 */     swapBuffers(l, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getSwapAction(BufferCapabilities.FlipContents paramFlipContents) {
/* 466 */     if (paramFlipContents == BufferCapabilities.FlipContents.BACKGROUND)
/* 467 */       return 1; 
/* 468 */     if (paramFlipContents == BufferCapabilities.FlipContents.PRIOR)
/* 469 */       return 2; 
/* 470 */     if (paramFlipContents == BufferCapabilities.FlipContents.COPIED) {
/* 471 */       return 3;
/*     */     }
/* 473 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTranslucencyCapable() {
/* 479 */     return isTranslucencyCapable(getAData());
/*     */   }
/*     */   
/*     */   private native int getNumColors();
/*     */   
/*     */   private native void init(int paramInt1, int paramInt2);
/*     */   
/*     */   private native ColorModel makeColorModel();
/*     */   
/*     */   private native double getXResolution(int paramInt);
/*     */   
/*     */   private native double getYResolution(int paramInt);
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   public native Rectangle pGetBounds(int paramInt);
/*     */   
/*     */   private static native void dispose(long paramLong);
/*     */   
/*     */   private native long createBackBuffer(long paramLong, int paramInt);
/*     */   
/*     */   private native void swapBuffers(long paramLong, int paramInt);
/*     */   
/*     */   public native void destroyBackBuffer(long paramLong);
/*     */   
/*     */   private native boolean isTranslucencyCapable(long paramLong);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11GraphicsConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */