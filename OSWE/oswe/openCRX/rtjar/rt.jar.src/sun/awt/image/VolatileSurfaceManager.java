/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.ImageCapabilities;
/*     */ import java.awt.image.BufferedImage;
/*     */ import sun.awt.DisplayChangedListener;
/*     */ import sun.java2d.InvalidPipeException;
/*     */ import sun.java2d.SunGraphicsEnvironment;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class VolatileSurfaceManager
/*     */   extends SurfaceManager
/*     */   implements DisplayChangedListener
/*     */ {
/*     */   protected SunVolatileImage vImg;
/*     */   protected SurfaceData sdAccel;
/*     */   protected SurfaceData sdBackup;
/*     */   protected SurfaceData sdCurrent;
/*     */   protected SurfaceData sdPrevious;
/*     */   protected boolean lostSurface;
/*     */   protected Object context;
/*     */   
/*     */   protected VolatileSurfaceManager(SunVolatileImage paramSunVolatileImage, Object paramObject) {
/*  96 */     this.vImg = paramSunVolatileImage;
/*  97 */     this.context = paramObject;
/*     */ 
/*     */     
/* 100 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*     */ 
/*     */     
/* 103 */     if (graphicsEnvironment instanceof SunGraphicsEnvironment) {
/* 104 */       ((SunGraphicsEnvironment)graphicsEnvironment).addDisplayChangedListener(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() {
/* 115 */     if (isAccelerationEnabled()) {
/* 116 */       this.sdAccel = initAcceleratedSurface();
/* 117 */       if (this.sdAccel != null) {
/* 118 */         this.sdCurrent = this.sdAccel;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 123 */     if (this.sdCurrent == null && this.vImg
/* 124 */       .getForcedAccelSurfaceType() == 0)
/*     */     {
/* 126 */       this.sdCurrent = getBackupSurface();
/*     */     }
/*     */   }
/*     */   
/*     */   public SurfaceData getPrimarySurfaceData() {
/* 131 */     return this.sdCurrent;
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
/*     */   protected abstract boolean isAccelerationEnabled();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int validate(GraphicsConfiguration paramGraphicsConfiguration) {
/* 160 */     byte b = 0;
/* 161 */     boolean bool = this.lostSurface;
/* 162 */     this.lostSurface = false;
/*     */     
/* 164 */     if (isAccelerationEnabled()) {
/* 165 */       if (!isConfigValid(paramGraphicsConfiguration)) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 170 */         b = 2;
/* 171 */       } else if (this.sdAccel == null) {
/*     */ 
/*     */         
/* 174 */         this.sdAccel = initAcceleratedSurface();
/* 175 */         if (this.sdAccel != null) {
/*     */           
/* 177 */           this.sdCurrent = this.sdAccel;
/*     */ 
/*     */           
/* 180 */           this.sdBackup = null;
/* 181 */           b = 1;
/*     */         } else {
/* 183 */           this.sdCurrent = getBackupSurface();
/*     */         } 
/* 185 */       } else if (this.sdAccel.isSurfaceLost()) {
/*     */         try {
/* 187 */           restoreAcceleratedSurface();
/*     */           
/* 189 */           this.sdCurrent = this.sdAccel;
/*     */           
/* 191 */           this.sdAccel.setSurfaceLost(false);
/*     */ 
/*     */           
/* 194 */           this.sdBackup = null;
/* 195 */           b = 1;
/* 196 */         } catch (InvalidPipeException invalidPipeException) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 201 */           this.sdCurrent = getBackupSurface();
/*     */         } 
/* 203 */       } else if (bool) {
/*     */ 
/*     */ 
/*     */         
/* 207 */         b = 1;
/*     */       } 
/* 209 */     } else if (this.sdAccel != null) {
/*     */ 
/*     */       
/* 212 */       this.sdCurrent = getBackupSurface();
/* 213 */       this.sdAccel = null;
/* 214 */       b = 1;
/*     */     } 
/*     */     
/* 217 */     if (b != 2 && this.sdCurrent != this.sdPrevious) {
/*     */ 
/*     */ 
/*     */       
/* 221 */       this.sdPrevious = this.sdCurrent;
/* 222 */       b = 1;
/*     */     } 
/*     */     
/* 225 */     if (b == 1)
/*     */     {
/*     */       
/* 228 */       initContents();
/*     */     }
/*     */     
/* 231 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contentsLost() {
/* 240 */     return this.lostSurface;
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
/*     */   protected abstract SurfaceData initAcceleratedSurface();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SurfaceData getBackupSurface() {
/* 262 */     if (this.sdBackup == null) {
/* 263 */       BufferedImage bufferedImage = this.vImg.getBackupImage();
/*     */       
/* 265 */       SunWritableRaster.stealTrackable(bufferedImage
/* 266 */           .getRaster()
/* 267 */           .getDataBuffer()).setUntrackable();
/* 268 */       this.sdBackup = BufImgSurfaceData.createData(bufferedImage);
/*     */     } 
/* 270 */     return this.sdBackup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initContents() {
/* 280 */     if (this.sdCurrent != null) {
/* 281 */       Graphics2D graphics2D = this.vImg.createGraphics();
/* 282 */       graphics2D.clearRect(0, 0, this.vImg.getWidth(), this.vImg.getHeight());
/* 283 */       graphics2D.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SurfaceData restoreContents() {
/* 294 */     return getBackupSurface();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void acceleratedSurfaceLost() {
/* 305 */     if (isAccelerationEnabled() && this.sdCurrent == this.sdAccel) {
/* 306 */       this.lostSurface = true;
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
/*     */   protected void restoreAcceleratedSurface() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayChanged() {
/* 327 */     if (!isAccelerationEnabled()) {
/*     */       return;
/*     */     }
/* 330 */     this.lostSurface = true;
/* 331 */     if (this.sdAccel != null) {
/*     */ 
/*     */ 
/*     */       
/* 335 */       this.sdBackup = null;
/*     */ 
/*     */       
/* 338 */       SurfaceData surfaceData = this.sdAccel;
/* 339 */       this.sdAccel = null;
/* 340 */       surfaceData.invalidate();
/* 341 */       this.sdCurrent = getBackupSurface();
/*     */     } 
/*     */ 
/*     */     
/* 345 */     this.vImg.updateGraphicsConfig();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paletteChanged() {
/* 354 */     this.lostSurface = true;
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
/*     */   protected boolean isConfigValid(GraphicsConfiguration paramGraphicsConfiguration) {
/* 366 */     return (paramGraphicsConfiguration == null || paramGraphicsConfiguration
/* 367 */       .getDevice() == this.vImg.getGraphicsConfig().getDevice());
/*     */   }
/*     */ 
/*     */   
/*     */   public ImageCapabilities getCapabilities(GraphicsConfiguration paramGraphicsConfiguration) {
/* 372 */     if (isConfigValid(paramGraphicsConfiguration)) {
/* 373 */       return isAccelerationEnabled() ? new AcceleratedImageCapabilities() : new ImageCapabilities(false);
/*     */     }
/*     */ 
/*     */     
/* 377 */     return super.getCapabilities(paramGraphicsConfiguration);
/*     */   }
/*     */   
/*     */   private class AcceleratedImageCapabilities
/*     */     extends ImageCapabilities
/*     */   {
/*     */     AcceleratedImageCapabilities() {
/* 384 */       super(false);
/*     */     }
/*     */     
/*     */     public boolean isAccelerated() {
/* 388 */       return (VolatileSurfaceManager.this.sdCurrent == VolatileSurfaceManager.this.sdAccel);
/*     */     }
/*     */     
/*     */     public boolean isTrueVolatile() {
/* 392 */       return isAccelerated();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 403 */     this.lostSurface = true;
/* 404 */     SurfaceData surfaceData = this.sdAccel;
/* 405 */     this.sdAccel = null;
/* 406 */     if (surfaceData != null)
/* 407 */       surfaceData.flush(); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/VolatileSurfaceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */