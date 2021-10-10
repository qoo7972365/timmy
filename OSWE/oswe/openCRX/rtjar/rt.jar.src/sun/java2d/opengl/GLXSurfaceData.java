/*     */ package sun.java2d.opengl;
/*     */ 
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Image;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.ColorModel;
/*     */ import sun.awt.X11ComponentPeer;
/*     */ import sun.java2d.SurfaceData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GLXSurfaceData
/*     */   extends OGLSurfaceData
/*     */ {
/*     */   protected X11ComponentPeer peer;
/*     */   private GLXGraphicsConfig graphicsConfig;
/*     */   
/*     */   private native void initOps(OGLGraphicsConfig paramOGLGraphicsConfig, X11ComponentPeer paramX11ComponentPeer, long paramLong);
/*     */   
/*     */   protected native boolean initPbuffer(long paramLong1, long paramLong2, boolean paramBoolean, int paramInt1, int paramInt2);
/*     */   
/*     */   protected GLXSurfaceData(X11ComponentPeer paramX11ComponentPeer, GLXGraphicsConfig paramGLXGraphicsConfig, ColorModel paramColorModel, int paramInt) {
/*  53 */     super(paramGLXGraphicsConfig, paramColorModel, paramInt);
/*  54 */     this.peer = paramX11ComponentPeer;
/*  55 */     this.graphicsConfig = paramGLXGraphicsConfig;
/*  56 */     initOps(paramGLXGraphicsConfig, paramX11ComponentPeer, this.graphicsConfig.getAData());
/*     */   }
/*     */   
/*     */   public GraphicsConfiguration getDeviceConfiguration() {
/*  60 */     return (GraphicsConfiguration)this.graphicsConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GLXWindowSurfaceData createData(X11ComponentPeer paramX11ComponentPeer) {
/*  68 */     GLXGraphicsConfig gLXGraphicsConfig = getGC(paramX11ComponentPeer);
/*  69 */     return new GLXWindowSurfaceData(paramX11ComponentPeer, gLXGraphicsConfig);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GLXOffScreenSurfaceData createData(X11ComponentPeer paramX11ComponentPeer, Image paramImage, int paramInt) {
/*  80 */     GLXGraphicsConfig gLXGraphicsConfig = getGC(paramX11ComponentPeer);
/*  81 */     Rectangle rectangle = paramX11ComponentPeer.getBounds();
/*  82 */     if (paramInt == 4) {
/*  83 */       return new GLXOffScreenSurfaceData(paramX11ComponentPeer, gLXGraphicsConfig, rectangle.width, rectangle.height, paramImage, paramX11ComponentPeer
/*  84 */           .getColorModel(), 4);
/*     */     }
/*     */     
/*  87 */     return new GLXVSyncOffScreenSurfaceData(paramX11ComponentPeer, gLXGraphicsConfig, rectangle.width, rectangle.height, paramImage, paramX11ComponentPeer
/*  88 */         .getColorModel(), paramInt);
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
/*     */   public static GLXOffScreenSurfaceData createData(GLXGraphicsConfig paramGLXGraphicsConfig, int paramInt1, int paramInt2, ColorModel paramColorModel, Image paramImage, int paramInt3) {
/* 102 */     return new GLXOffScreenSurfaceData(null, paramGLXGraphicsConfig, paramInt1, paramInt2, paramImage, paramColorModel, paramInt3);
/*     */   }
/*     */ 
/*     */   
/*     */   public static GLXGraphicsConfig getGC(X11ComponentPeer paramX11ComponentPeer) {
/* 107 */     if (paramX11ComponentPeer != null) {
/* 108 */       return (GLXGraphicsConfig)paramX11ComponentPeer.getGraphicsConfiguration();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 113 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 114 */     GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
/* 115 */     return (GLXGraphicsConfig)graphicsDevice.getDefaultConfiguration();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class GLXWindowSurfaceData
/*     */     extends GLXSurfaceData
/*     */   {
/*     */     public GLXWindowSurfaceData(X11ComponentPeer param1X11ComponentPeer, GLXGraphicsConfig param1GLXGraphicsConfig) {
/* 124 */       super(param1X11ComponentPeer, param1GLXGraphicsConfig, param1X11ComponentPeer.getColorModel(), 1);
/*     */     }
/*     */     
/*     */     public SurfaceData getReplacement() {
/* 128 */       return this.peer.getSurfaceData();
/*     */     }
/*     */     
/*     */     public Rectangle getBounds() {
/* 132 */       Rectangle rectangle = this.peer.getBounds();
/* 133 */       rectangle.x = rectangle.y = 0;
/* 134 */       return rectangle;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getDestination() {
/* 141 */       return this.peer.getTarget();
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
/*     */   public static class GLXVSyncOffScreenSurfaceData
/*     */     extends GLXOffScreenSurfaceData
/*     */   {
/*     */     private GLXSurfaceData.GLXOffScreenSurfaceData flipSurface;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public GLXVSyncOffScreenSurfaceData(X11ComponentPeer param1X11ComponentPeer, GLXGraphicsConfig param1GLXGraphicsConfig, int param1Int1, int param1Int2, Image param1Image, ColorModel param1ColorModel, int param1Int3) {
/* 165 */       super(param1X11ComponentPeer, param1GLXGraphicsConfig, param1Int1, param1Int2, param1Image, param1ColorModel, param1Int3);
/* 166 */       this.flipSurface = GLXSurfaceData.createData(param1X11ComponentPeer, param1Image, 4);
/*     */     }
/*     */     
/*     */     public SurfaceData getFlipSurface() {
/* 170 */       return this.flipSurface;
/*     */     }
/*     */ 
/*     */     
/*     */     public void flush() {
/* 175 */       this.flipSurface.flush();
/* 176 */       super.flush();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class GLXOffScreenSurfaceData
/*     */     extends GLXSurfaceData
/*     */   {
/*     */     private Image offscreenImage;
/*     */     
/*     */     private int width;
/*     */     
/*     */     private int height;
/*     */ 
/*     */     
/*     */     public GLXOffScreenSurfaceData(X11ComponentPeer param1X11ComponentPeer, GLXGraphicsConfig param1GLXGraphicsConfig, int param1Int1, int param1Int2, Image param1Image, ColorModel param1ColorModel, int param1Int3) {
/* 192 */       super(param1X11ComponentPeer, param1GLXGraphicsConfig, param1ColorModel, param1Int3);
/*     */       
/* 194 */       this.width = param1Int1;
/* 195 */       this.height = param1Int2;
/* 196 */       this.offscreenImage = param1Image;
/*     */       
/* 198 */       initSurface(param1Int1, param1Int2);
/*     */     }
/*     */     
/*     */     public SurfaceData getReplacement() {
/* 202 */       return restoreContents(this.offscreenImage);
/*     */     }
/*     */     
/*     */     public Rectangle getBounds() {
/* 206 */       if (this.type == 4) {
/* 207 */         Rectangle rectangle = this.peer.getBounds();
/* 208 */         rectangle.x = rectangle.y = 0;
/* 209 */         return rectangle;
/*     */       } 
/* 211 */       return new Rectangle(this.width, this.height);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getDestination() {
/* 219 */       return this.offscreenImage;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/opengl/GLXSurfaceData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */