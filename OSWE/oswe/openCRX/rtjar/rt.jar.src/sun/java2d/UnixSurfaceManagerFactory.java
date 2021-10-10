/*    */ package sun.java2d;
/*    */ 
/*    */ import java.awt.GraphicsConfiguration;
/*    */ import sun.awt.image.SunVolatileImage;
/*    */ import sun.awt.image.VolatileSurfaceManager;
/*    */ import sun.java2d.opengl.GLXVolatileSurfaceManager;
/*    */ import sun.java2d.x11.X11VolatileSurfaceManager;
/*    */ import sun.java2d.xr.XRVolatileSurfaceManager;
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
/*    */ public class UnixSurfaceManagerFactory
/*    */   extends SurfaceManagerFactory
/*    */ {
/*    */   public VolatileSurfaceManager createVolatileManager(SunVolatileImage paramSunVolatileImage, Object paramObject) {
/* 57 */     GraphicsConfiguration graphicsConfiguration = paramSunVolatileImage.getGraphicsConfig();
/*    */     
/* 59 */     if (graphicsConfiguration instanceof sun.java2d.opengl.GLXGraphicsConfig)
/* 60 */       return (VolatileSurfaceManager)new GLXVolatileSurfaceManager(paramSunVolatileImage, paramObject); 
/* 61 */     if (graphicsConfiguration instanceof sun.java2d.xr.XRGraphicsConfig) {
/* 62 */       return (VolatileSurfaceManager)new XRVolatileSurfaceManager(paramSunVolatileImage, paramObject);
/*    */     }
/* 64 */     return (VolatileSurfaceManager)new X11VolatileSurfaceManager(paramSunVolatileImage, paramObject);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/UnixSurfaceManagerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */