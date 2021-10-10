/*     */ package sun.java2d.x11;
/*     */ 
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.ImageCapabilities;
/*     */ import java.awt.image.ColorModel;
/*     */ import sun.awt.X11GraphicsConfig;
/*     */ import sun.awt.image.SunVolatileImage;
/*     */ import sun.awt.image.VolatileSurfaceManager;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X11VolatileSurfaceManager
/*     */   extends VolatileSurfaceManager
/*     */ {
/*     */   private boolean accelerationEnabled;
/*     */   
/*     */   public X11VolatileSurfaceManager(SunVolatileImage paramSunVolatileImage, Object paramObject) {
/*  51 */     super(paramSunVolatileImage, paramObject);
/*     */ 
/*     */     
/*  54 */     this
/*  55 */       .accelerationEnabled = (X11SurfaceData.isAccelerationEnabled() && paramSunVolatileImage.getTransparency() == 1);
/*     */     
/*  57 */     if (paramObject != null && !this.accelerationEnabled) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  63 */       this.accelerationEnabled = true;
/*  64 */       this.sdAccel = initAcceleratedSurface();
/*  65 */       this.sdCurrent = this.sdAccel;
/*     */       
/*  67 */       if (this.sdBackup != null)
/*     */       {
/*     */         
/*  70 */         this.sdBackup = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isAccelerationEnabled() {
/*  76 */     return this.accelerationEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SurfaceData initAcceleratedSurface() {
/*     */     SurfaceData surfaceData;
/*     */     try {
/*  86 */       X11GraphicsConfig x11GraphicsConfig = (X11GraphicsConfig)this.vImg.getGraphicsConfig();
/*  87 */       ColorModel colorModel = x11GraphicsConfig.getColorModel();
/*  88 */       long l = 0L;
/*  89 */       if (this.context instanceof Long) {
/*  90 */         l = ((Long)this.context).longValue();
/*     */       }
/*  92 */       surfaceData = X11SurfaceData.createData(x11GraphicsConfig, this.vImg
/*  93 */           .getWidth(), this.vImg
/*  94 */           .getHeight(), colorModel, this.vImg, l, 1);
/*     */     
/*     */     }
/*  97 */     catch (NullPointerException nullPointerException) {
/*  98 */       surfaceData = null;
/*  99 */     } catch (OutOfMemoryError outOfMemoryError) {
/* 100 */       surfaceData = null;
/*     */     } 
/*     */     
/* 103 */     return surfaceData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isConfigValid(GraphicsConfiguration paramGraphicsConfiguration) {
/* 112 */     return (paramGraphicsConfiguration == null || paramGraphicsConfiguration == this.vImg.getGraphicsConfig());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageCapabilities getCapabilities(GraphicsConfiguration paramGraphicsConfiguration) {
/* 121 */     if (isConfigValid(paramGraphicsConfiguration) && isAccelerationEnabled())
/*     */     {
/* 123 */       return new ImageCapabilities(true);
/*     */     }
/*     */     
/* 126 */     return new ImageCapabilities(false);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/x11/X11VolatileSurfaceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */