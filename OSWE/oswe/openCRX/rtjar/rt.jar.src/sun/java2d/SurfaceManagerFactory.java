/*    */ package sun.java2d;
/*    */ 
/*    */ import sun.awt.image.SunVolatileImage;
/*    */ import sun.awt.image.VolatileSurfaceManager;
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
/*    */ 
/*    */ public abstract class SurfaceManagerFactory
/*    */ {
/*    */   private static SurfaceManagerFactory instance;
/*    */   
/*    */   public static synchronized SurfaceManagerFactory getInstance() {
/* 55 */     if (instance == null) {
/* 56 */       throw new IllegalStateException("No SurfaceManagerFactory set.");
/*    */     }
/* 58 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized void setInstance(SurfaceManagerFactory paramSurfaceManagerFactory) {
/* 70 */     if (paramSurfaceManagerFactory == null)
/*    */     {
/* 72 */       throw new IllegalArgumentException("factory must be non-null");
/*    */     }
/*    */     
/* 75 */     if (instance != null)
/*    */     {
/* 77 */       throw new IllegalStateException("The surface manager factory is already initialized");
/*    */     }
/*    */     
/* 80 */     instance = paramSurfaceManagerFactory;
/*    */   }
/*    */   
/*    */   public abstract VolatileSurfaceManager createVolatileManager(SunVolatileImage paramSunVolatileImage, Object paramObject);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/SurfaceManagerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */