/*     */ package sun.java2d.opengl;
/*     */ 
/*     */ import java.awt.BufferCapabilities;
/*     */ import java.awt.Component;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.image.ColorModel;
/*     */ import sun.awt.X11ComponentPeer;
/*     */ import sun.awt.image.SunVolatileImage;
/*     */ import sun.awt.image.VolatileSurfaceManager;
/*     */ import sun.java2d.BackBufferCapsProvider;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.pipe.hw.ExtendedBufferCapabilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GLXVolatileSurfaceManager
/*     */   extends VolatileSurfaceManager
/*     */ {
/*     */   private boolean accelerationEnabled;
/*     */   
/*     */   public GLXVolatileSurfaceManager(SunVolatileImage paramSunVolatileImage, Object paramObject) {
/*  49 */     super(paramSunVolatileImage, paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     int i = paramSunVolatileImage.getTransparency();
/*  60 */     GLXGraphicsConfig gLXGraphicsConfig = (GLXGraphicsConfig)paramSunVolatileImage.getGraphicsConfig();
/*  61 */     this
/*     */ 
/*     */ 
/*     */       
/*  65 */       .accelerationEnabled = (i == 1 || (i == 3 && (gLXGraphicsConfig.isCapPresent(12) || gLXGraphicsConfig.isCapPresent(2))));
/*     */   }
/*     */   
/*     */   protected boolean isAccelerationEnabled() {
/*  69 */     return this.accelerationEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SurfaceData initAcceleratedSurface() {
/*     */     SurfaceData surfaceData;
/*  78 */     Component component = this.vImg.getComponent();
/*     */     
/*  80 */     X11ComponentPeer x11ComponentPeer = (component != null) ? (X11ComponentPeer)component.getPeer() : null;
/*     */     
/*     */     try {
/*  83 */       boolean bool = false;
/*  84 */       boolean bool1 = false;
/*  85 */       if (this.context instanceof Boolean) {
/*  86 */         bool1 = ((Boolean)this.context).booleanValue();
/*  87 */         if (bool1 && x11ComponentPeer instanceof BackBufferCapsProvider) {
/*  88 */           BackBufferCapsProvider backBufferCapsProvider = (BackBufferCapsProvider)x11ComponentPeer;
/*     */           
/*  90 */           BufferCapabilities bufferCapabilities = backBufferCapsProvider.getBackBufferCaps();
/*  91 */           if (bufferCapabilities instanceof ExtendedBufferCapabilities) {
/*  92 */             ExtendedBufferCapabilities extendedBufferCapabilities = (ExtendedBufferCapabilities)bufferCapabilities;
/*     */             
/*  94 */             if (extendedBufferCapabilities.getVSync() == ExtendedBufferCapabilities.VSyncType.VSYNC_ON && extendedBufferCapabilities
/*  95 */               .getFlipContents() == BufferCapabilities.FlipContents.COPIED) {
/*     */               
/*  97 */               bool = true;
/*  98 */               bool1 = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 104 */       if (bool1) {
/*     */         
/* 106 */         surfaceData = GLXSurfaceData.createData(x11ComponentPeer, this.vImg, 4);
/*     */       } else {
/*     */         
/* 109 */         GLXGraphicsConfig gLXGraphicsConfig = (GLXGraphicsConfig)this.vImg.getGraphicsConfig();
/* 110 */         ColorModel colorModel = gLXGraphicsConfig.getColorModel(this.vImg.getTransparency());
/* 111 */         int i = this.vImg.getForcedAccelSurfaceType();
/*     */ 
/*     */         
/* 114 */         if (i == 0) {
/* 115 */           i = gLXGraphicsConfig.isCapPresent(12) ? 5 : 2;
/*     */         }
/*     */         
/* 118 */         if (bool) {
/* 119 */           surfaceData = GLXSurfaceData.createData(x11ComponentPeer, this.vImg, i);
/*     */         } else {
/* 121 */           surfaceData = GLXSurfaceData.createData(gLXGraphicsConfig, this.vImg
/* 122 */               .getWidth(), this.vImg
/* 123 */               .getHeight(), colorModel, this.vImg, i);
/*     */         }
/*     */       
/*     */       } 
/* 127 */     } catch (NullPointerException nullPointerException) {
/* 128 */       surfaceData = null;
/* 129 */     } catch (OutOfMemoryError outOfMemoryError) {
/* 130 */       surfaceData = null;
/*     */     } 
/*     */     
/* 133 */     return surfaceData;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isConfigValid(GraphicsConfiguration paramGraphicsConfiguration) {
/* 138 */     return (paramGraphicsConfiguration == null || paramGraphicsConfiguration == this.vImg.getGraphicsConfig());
/*     */   }
/*     */ 
/*     */   
/*     */   public void initContents() {
/* 143 */     if (this.vImg.getForcedAccelSurfaceType() != 3)
/* 144 */       super.initContents(); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/opengl/GLXVolatileSurfaceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */