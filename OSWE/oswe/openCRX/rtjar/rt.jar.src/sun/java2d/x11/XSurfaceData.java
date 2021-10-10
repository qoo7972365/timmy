/*    */ package sun.java2d.x11;
/*    */ 
/*    */ import java.awt.image.ColorModel;
/*    */ import sun.awt.X11ComponentPeer;
/*    */ import sun.awt.X11GraphicsConfig;
/*    */ import sun.java2d.SurfaceData;
/*    */ import sun.java2d.loops.SurfaceType;
/*    */ import sun.java2d.pipe.Region;
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
/*    */ public abstract class XSurfaceData
/*    */   extends SurfaceData
/*    */ {
/*    */   static boolean isX11SurfaceDataInitialized = false;
/*    */   
/*    */   public static boolean isX11SurfaceDataInitialized() {
/* 39 */     return isX11SurfaceDataInitialized;
/*    */   }
/*    */   
/*    */   public static void setX11SurfaceDataInitialized() {
/* 43 */     isX11SurfaceDataInitialized = true;
/*    */   }
/*    */   
/*    */   public XSurfaceData(SurfaceType paramSurfaceType, ColorModel paramColorModel) {
/* 47 */     super(paramSurfaceType, paramColorModel);
/*    */   }
/*    */   
/*    */   protected native void initOps(X11ComponentPeer paramX11ComponentPeer, X11GraphicsConfig paramX11GraphicsConfig, int paramInt);
/*    */   
/*    */   protected static native long XCreateGC(long paramLong);
/*    */   
/*    */   protected static native void XResetClip(long paramLong);
/*    */   
/*    */   protected static native void XSetClip(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Region paramRegion);
/*    */   
/*    */   protected native void flushNativeSurface();
/*    */   
/*    */   protected native boolean isDrawableValid();
/*    */   
/*    */   protected native void setInvalid();
/*    */   
/*    */   protected static native void XSetGraphicsExposures(long paramLong, boolean paramBoolean);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/x11/XSurfaceData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */