/*     */ package java.awt;
/*     */ 
/*     */ import sun.security.util.SecurityConstants;
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
/*     */ public class MouseInfo
/*     */ {
/*     */   public static PointerInfo getPointerInfo() throws HeadlessException {
/*  73 */     if (GraphicsEnvironment.isHeadless()) {
/*  74 */       throw new HeadlessException();
/*     */     }
/*     */     
/*  77 */     SecurityManager securityManager = System.getSecurityManager();
/*  78 */     if (securityManager != null) {
/*  79 */       securityManager.checkPermission(SecurityConstants.AWT.WATCH_MOUSE_PERMISSION);
/*     */     }
/*     */     
/*  82 */     Point point = new Point(0, 0);
/*  83 */     int i = Toolkit.getDefaultToolkit().getMouseInfoPeer().fillPointWithCoords(point);
/*     */     
/*  85 */     GraphicsDevice[] arrayOfGraphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
/*  86 */     PointerInfo pointerInfo = null;
/*  87 */     if (areScreenDevicesIndependent(arrayOfGraphicsDevice)) {
/*  88 */       pointerInfo = new PointerInfo(arrayOfGraphicsDevice[i], point);
/*     */     } else {
/*  90 */       for (byte b = 0; b < arrayOfGraphicsDevice.length; b++) {
/*  91 */         GraphicsConfiguration graphicsConfiguration = arrayOfGraphicsDevice[b].getDefaultConfiguration();
/*  92 */         Rectangle rectangle = graphicsConfiguration.getBounds();
/*  93 */         if (rectangle.contains(point)) {
/*  94 */           pointerInfo = new PointerInfo(arrayOfGraphicsDevice[b], point);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  99 */     return pointerInfo;
/*     */   }
/*     */   
/*     */   private static boolean areScreenDevicesIndependent(GraphicsDevice[] paramArrayOfGraphicsDevice) {
/* 103 */     for (byte b = 0; b < paramArrayOfGraphicsDevice.length; b++) {
/* 104 */       Rectangle rectangle = paramArrayOfGraphicsDevice[b].getDefaultConfiguration().getBounds();
/* 105 */       if (rectangle.x != 0 || rectangle.y != 0) {
/* 106 */         return false;
/*     */       }
/*     */     } 
/* 109 */     return true;
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
/*     */   public static int getNumberOfButtons() throws HeadlessException {
/* 121 */     if (GraphicsEnvironment.isHeadless()) {
/* 122 */       throw new HeadlessException();
/*     */     }
/*     */     
/* 125 */     Object object = Toolkit.getDefaultToolkit().getDesktopProperty("awt.mouse.numButtons");
/* 126 */     if (object instanceof Integer) {
/* 127 */       return ((Integer)object).intValue();
/*     */     }
/*     */ 
/*     */     
/* 131 */     assert false : "awt.mouse.numButtons is not an integer property";
/* 132 */     return 0;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/MouseInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */