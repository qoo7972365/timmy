/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Image;
/*     */ import java.awt.ImageCapabilities;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.SurfaceDataProxy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SurfaceManager
/*     */ {
/*     */   private static ImageAccessor imgaccessor;
/*     */   private ConcurrentHashMap<Object, Object> cacheMap;
/*     */   
/*     */   public static abstract class ImageAccessor
/*     */   {
/*     */     public abstract SurfaceManager getSurfaceManager(Image param1Image);
/*     */     
/*     */     public abstract void setSurfaceManager(Image param1Image, SurfaceManager param1SurfaceManager);
/*     */   }
/*     */   
/*     */   public static void setImageAccessor(ImageAccessor paramImageAccessor) {
/*  62 */     if (imgaccessor != null) {
/*  63 */       throw new InternalError("Attempt to set ImageAccessor twice");
/*     */     }
/*  65 */     imgaccessor = paramImageAccessor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SurfaceManager getManager(Image paramImage) {
/*  72 */     SurfaceManager surfaceManager = imgaccessor.getSurfaceManager(paramImage);
/*  73 */     if (surfaceManager == null) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/*  78 */         BufferedImage bufferedImage = (BufferedImage)paramImage;
/*  79 */         surfaceManager = new BufImgSurfaceManager(bufferedImage);
/*  80 */         setManager(bufferedImage, surfaceManager);
/*  81 */       } catch (ClassCastException classCastException) {
/*  82 */         throw new IllegalArgumentException("Invalid Image variant");
/*     */       } 
/*     */     }
/*  85 */     return surfaceManager;
/*     */   }
/*     */   
/*     */   public static void setManager(Image paramImage, SurfaceManager paramSurfaceManager) {
/*  89 */     imgaccessor.setSurfaceManager(paramImage, paramSurfaceManager);
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
/*     */   public Object getCacheData(Object paramObject) {
/* 115 */     return (this.cacheMap == null) ? null : this.cacheMap.get(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCacheData(Object paramObject1, Object paramObject2) {
/* 124 */     if (this.cacheMap == null) {
/* 125 */       synchronized (this) {
/* 126 */         if (this.cacheMap == null) {
/* 127 */           this.cacheMap = new ConcurrentHashMap<>(2);
/*     */         }
/*     */       } 
/*     */     }
/* 131 */     this.cacheMap.put(paramObject1, paramObject2);
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
/*     */   public abstract SurfaceData getPrimarySurfaceData();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract SurfaceData restoreContents();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void acceleratedSurfaceLost() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageCapabilities getCapabilities(GraphicsConfiguration paramGraphicsConfiguration) {
/* 185 */     return new ImageCapabilitiesGc(paramGraphicsConfiguration);
/*     */   }
/*     */   
/*     */   class ImageCapabilitiesGc extends ImageCapabilities {
/*     */     GraphicsConfiguration gc;
/*     */     
/*     */     public ImageCapabilitiesGc(GraphicsConfiguration param1GraphicsConfiguration) {
/* 192 */       super(false);
/* 193 */       this.gc = param1GraphicsConfiguration;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isAccelerated() {
/* 200 */       GraphicsConfiguration graphicsConfiguration = this.gc;
/* 201 */       if (graphicsConfiguration == null)
/*     */       {
/* 203 */         graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
/*     */       }
/* 205 */       if (graphicsConfiguration instanceof SurfaceManager.ProxiedGraphicsConfig) {
/*     */         
/* 207 */         Object object = ((SurfaceManager.ProxiedGraphicsConfig)graphicsConfiguration).getProxyKey();
/* 208 */         if (object != null) {
/*     */           
/* 210 */           SurfaceDataProxy surfaceDataProxy = (SurfaceDataProxy)SurfaceManager.this.getCacheData(object);
/* 211 */           return (surfaceDataProxy != null && surfaceDataProxy.isAccelerated());
/*     */         } 
/*     */       } 
/* 214 */       return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void flush() {
/* 244 */     flush(false);
/*     */   }
/*     */   
/*     */   synchronized void flush(boolean paramBoolean) {
/* 248 */     if (this.cacheMap != null) {
/* 249 */       Iterator<Object> iterator = this.cacheMap.values().iterator();
/* 250 */       while (iterator.hasNext()) {
/* 251 */         FlushableCacheData flushableCacheData = (FlushableCacheData)iterator.next();
/* 252 */         if (flushableCacheData instanceof FlushableCacheData && (
/* 253 */           (FlushableCacheData)flushableCacheData).flush(paramBoolean)) {
/* 254 */           iterator.remove();
/*     */         }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAccelerationPriority(float paramFloat) {
/* 287 */     if (paramFloat == 0.0F) {
/* 288 */       flush(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getImageScale(Image paramImage) {
/* 299 */     if (!(paramImage instanceof java.awt.image.VolatileImage)) {
/* 300 */       return 1;
/*     */     }
/* 302 */     SurfaceManager surfaceManager = getManager(paramImage);
/* 303 */     return surfaceManager.getPrimarySurfaceData().getDefaultScale();
/*     */   }
/*     */   
/*     */   public static interface ProxiedGraphicsConfig {
/*     */     Object getProxyKey();
/*     */   }
/*     */   
/*     */   public static interface FlushableCacheData {
/*     */     boolean flush(boolean param1Boolean);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/SurfaceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */