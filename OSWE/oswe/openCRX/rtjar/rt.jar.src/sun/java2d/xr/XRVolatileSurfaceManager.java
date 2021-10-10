/*    */ package sun.java2d.xr;
/*    */ 
/*    */ import java.awt.GraphicsConfiguration;
/*    */ import java.awt.ImageCapabilities;
/*    */ import java.awt.image.ColorModel;
/*    */ import sun.awt.image.SunVolatileImage;
/*    */ import sun.awt.image.VolatileSurfaceManager;
/*    */ import sun.java2d.SurfaceData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XRVolatileSurfaceManager
/*    */   extends VolatileSurfaceManager
/*    */ {
/*    */   public XRVolatileSurfaceManager(SunVolatileImage paramSunVolatileImage, Object paramObject) {
/* 41 */     super(paramSunVolatileImage, paramObject);
/*    */   }
/*    */   
/*    */   protected boolean isAccelerationEnabled() {
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected SurfaceData initAcceleratedSurface() {
/*    */     SurfaceData surfaceData;
/*    */     try {
/* 55 */       XRGraphicsConfig xRGraphicsConfig = (XRGraphicsConfig)this.vImg.getGraphicsConfig();
/* 56 */       ColorModel colorModel = xRGraphicsConfig.getColorModel();
/* 57 */       long l = 0L;
/* 58 */       if (this.context instanceof Long) {
/* 59 */         l = ((Long)this.context).longValue();
/*    */       }
/* 61 */       surfaceData = (SurfaceData)XRSurfaceData.createData(xRGraphicsConfig, this.vImg
/* 62 */           .getWidth(), this.vImg
/* 63 */           .getHeight(), colorModel, this.vImg, l, this.vImg
/*    */           
/* 65 */           .getTransparency());
/* 66 */     } catch (NullPointerException nullPointerException) {
/* 67 */       surfaceData = null;
/* 68 */     } catch (OutOfMemoryError outOfMemoryError) {
/* 69 */       surfaceData = null;
/*    */     } 
/*    */     
/* 72 */     return surfaceData;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isConfigValid(GraphicsConfiguration paramGraphicsConfiguration) {
/* 80 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ImageCapabilities getCapabilities(GraphicsConfiguration paramGraphicsConfiguration) {
/* 89 */     if (isConfigValid(paramGraphicsConfiguration) && isAccelerationEnabled()) {
/* 90 */       return new ImageCapabilities(true);
/*    */     }
/* 92 */     return new ImageCapabilities(false);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRVolatileSurfaceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */