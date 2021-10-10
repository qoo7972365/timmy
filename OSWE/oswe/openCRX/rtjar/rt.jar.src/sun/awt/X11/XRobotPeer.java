/*    */ package sun.awt.X11;
/*    */ 
/*    */ import java.awt.GraphicsConfiguration;
/*    */ import java.awt.Rectangle;
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.peer.RobotPeer;
/*    */ import sun.awt.AWTAccessor;
/*    */ import sun.awt.SunToolkit;
/*    */ import sun.awt.X11GraphicsConfig;
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
/*    */ class XRobotPeer
/*    */   implements RobotPeer
/*    */ {
/*    */   static {
/* 38 */     loadNativeLibraries();
/*    */   }
/*    */   
/* 41 */   private X11GraphicsConfig xgc = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   static Object robotLock = new Object();
/*    */   
/*    */   XRobotPeer(GraphicsConfiguration paramGraphicsConfiguration) {
/* 49 */     this.xgc = (X11GraphicsConfig)paramGraphicsConfiguration;
/* 50 */     SunToolkit sunToolkit = (SunToolkit)Toolkit.getDefaultToolkit();
/* 51 */     setup(sunToolkit.getNumberOfButtons(), AWTAccessor.getInputEventAccessor().getButtonDownMasks());
/*    */   }
/*    */ 
/*    */   
/*    */   public void dispose() {}
/*    */ 
/*    */   
/*    */   public void mouseMove(int paramInt1, int paramInt2) {
/* 59 */     mouseMoveImpl(this.xgc, paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   public void mousePress(int paramInt) {
/* 63 */     mousePressImpl(paramInt);
/*    */   }
/*    */   
/*    */   public void mouseRelease(int paramInt) {
/* 67 */     mouseReleaseImpl(paramInt);
/*    */   }
/*    */   
/*    */   public void mouseWheel(int paramInt) {
/* 71 */     mouseWheelImpl(paramInt);
/*    */   }
/*    */   
/*    */   public void keyPress(int paramInt) {
/* 75 */     keyPressImpl(paramInt);
/*    */   }
/*    */   
/*    */   public void keyRelease(int paramInt) {
/* 79 */     keyReleaseImpl(paramInt);
/*    */   }
/*    */   
/*    */   public int getRGBPixel(int paramInt1, int paramInt2) {
/* 83 */     int[] arrayOfInt = new int[1];
/* 84 */     getRGBPixelsImpl(this.xgc, paramInt1, paramInt2, 1, 1, arrayOfInt);
/* 85 */     return arrayOfInt[0];
/*    */   }
/*    */   
/*    */   public int[] getRGBPixels(Rectangle paramRectangle) {
/* 89 */     int[] arrayOfInt = new int[paramRectangle.width * paramRectangle.height];
/* 90 */     getRGBPixelsImpl(this.xgc, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, arrayOfInt);
/* 91 */     return arrayOfInt;
/*    */   }
/*    */   
/*    */   private static synchronized native void setup(int paramInt, int[] paramArrayOfint);
/*    */   
/*    */   private static synchronized native void mouseMoveImpl(X11GraphicsConfig paramX11GraphicsConfig, int paramInt1, int paramInt2);
/*    */   
/*    */   private static synchronized native void mousePressImpl(int paramInt);
/*    */   
/*    */   private static synchronized native void mouseReleaseImpl(int paramInt);
/*    */   
/*    */   private static synchronized native void mouseWheelImpl(int paramInt);
/*    */   
/*    */   private static synchronized native void keyPressImpl(int paramInt);
/*    */   
/*    */   private static synchronized native void keyReleaseImpl(int paramInt);
/*    */   
/*    */   private static synchronized native void getRGBPixelsImpl(X11GraphicsConfig paramX11GraphicsConfig, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint);
/*    */   
/*    */   private static native void loadNativeLibraries();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XRobotPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */