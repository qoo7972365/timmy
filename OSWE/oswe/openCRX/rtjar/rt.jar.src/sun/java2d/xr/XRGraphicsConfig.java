/*    */ package sun.java2d.xr;
/*    */ 
/*    */ import sun.awt.X11ComponentPeer;
/*    */ import sun.awt.X11GraphicsConfig;
/*    */ import sun.awt.X11GraphicsDevice;
/*    */ import sun.awt.X11GraphicsEnvironment;
/*    */ import sun.awt.image.SurfaceManager;
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
/*    */ public class XRGraphicsConfig
/*    */   extends X11GraphicsConfig
/*    */   implements SurfaceManager.ProxiedGraphicsConfig
/*    */ {
/*    */   private XRGraphicsConfig(X11GraphicsDevice paramX11GraphicsDevice, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 41 */     super(paramX11GraphicsDevice, paramInt1, paramInt2, paramInt3, paramBoolean);
/*    */   }
/*    */   
/*    */   public SurfaceData createSurfaceData(X11ComponentPeer paramX11ComponentPeer) {
/* 45 */     return (SurfaceData)XRSurfaceData.createData(paramX11ComponentPeer);
/*    */   }
/*    */ 
/*    */   
/*    */   public static XRGraphicsConfig getConfig(X11GraphicsDevice paramX11GraphicsDevice, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 50 */     if (!X11GraphicsEnvironment.isXRenderAvailable()) {
/* 51 */       return null;
/*    */     }
/*    */     
/* 54 */     return new XRGraphicsConfig(paramX11GraphicsDevice, paramInt1, paramInt2, paramInt3, paramBoolean);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getProxyKey() {
/* 59 */     return this;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRGraphicsConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */