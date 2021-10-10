/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.AWTError;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Enumeration;
/*     */ import sun.java2d.SunGraphicsEnvironment;
/*     */ import sun.java2d.SurfaceManagerFactory;
/*     */ import sun.java2d.UnixSurfaceManagerFactory;
/*     */ import sun.java2d.xr.XRSurfaceData;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.util.logging.PlatformLogger;
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
/*     */ public class X11GraphicsEnvironment
/*     */   extends SunGraphicsEnvironment
/*     */ {
/*  68 */   private static final PlatformLogger log = PlatformLogger.getLogger("sun.awt.X11GraphicsEnvironment");
/*  69 */   private static final PlatformLogger screenLog = PlatformLogger.getLogger("sun.awt.screen.X11GraphicsEnvironment");
/*     */   
/*     */   private static Boolean xinerState;
/*     */   
/*     */   static {
/*  74 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run() {
/*  77 */             System.loadLibrary("awt");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  84 */             if (!GraphicsEnvironment.isHeadless()) {
/*     */               
/*  86 */               boolean bool1 = false;
/*  87 */               String str1 = System.getProperty("sun.java2d.opengl");
/*  88 */               if (str1 != null) {
/*  89 */                 if (str1.equals("true") || str1.equals("t")) {
/*  90 */                   bool1 = true;
/*  91 */                 } else if (str1.equals("True") || str1.equals("T")) {
/*  92 */                   bool1 = true;
/*  93 */                   X11GraphicsEnvironment.glxVerbose = true;
/*     */                 } 
/*     */               }
/*     */ 
/*     */               
/*  98 */               boolean bool2 = true;
/*  99 */               boolean bool3 = false;
/* 100 */               String str2 = System.getProperty("sun.java2d.xrender");
/* 101 */               if (str2 != null) {
/* 102 */                 if (str2.equals("false") || str2.equals("f")) {
/* 103 */                   bool2 = false;
/* 104 */                 } else if (str2.equals("True") || str2.equals("T")) {
/* 105 */                   bool2 = true;
/* 106 */                   X11GraphicsEnvironment.xRenderVerbose = true;
/*     */                 } 
/*     */                 
/* 109 */                 if (str2.equalsIgnoreCase("t") || str2.equalsIgnoreCase("true")) {
/* 110 */                   bool3 = true;
/*     */                 }
/*     */               } 
/*     */ 
/*     */               
/* 115 */               X11GraphicsEnvironment.initDisplay(bool1);
/*     */ 
/*     */               
/* 118 */               if (bool1) {
/* 119 */                 X11GraphicsEnvironment.glxAvailable = X11GraphicsEnvironment.initGLX();
/* 120 */                 if (X11GraphicsEnvironment.glxVerbose && !X11GraphicsEnvironment.glxAvailable) {
/* 121 */                   System.out.println("Could not enable OpenGL pipeline (GLX 1.3 not available)");
/*     */                 }
/*     */               } 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 128 */               if (bool2) {
/* 129 */                 X11GraphicsEnvironment.xRenderAvailable = X11GraphicsEnvironment.initXRender(X11GraphicsEnvironment.xRenderVerbose, bool3);
/* 130 */                 if (X11GraphicsEnvironment.xRenderVerbose && !X11GraphicsEnvironment.xRenderAvailable) {
/* 131 */                   System.out.println("Could not enable XRender pipeline");
/*     */                 }
/*     */               } 
/*     */ 
/*     */               
/* 136 */               if (X11GraphicsEnvironment.xRenderAvailable) {
/* 137 */                 XRSurfaceData.initXRSurfaceData();
/*     */               }
/*     */             } 
/*     */             
/* 141 */             return null;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 146 */     SurfaceManagerFactory.setInstance((SurfaceManagerFactory)new UnixSurfaceManagerFactory());
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean glxAvailable;
/*     */   private static boolean glxVerbose;
/*     */   private static boolean xRenderVerbose;
/*     */   private static boolean xRenderAvailable;
/*     */   private Boolean isDisplayLocal;
/*     */   
/*     */   public static boolean isGLXAvailable() {
/* 157 */     return glxAvailable;
/*     */   }
/*     */   
/*     */   public static boolean isGLXVerbose() {
/* 161 */     return glxVerbose;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isXRenderAvailable() {
/* 169 */     return xRenderAvailable;
/*     */   }
/*     */   
/*     */   public static boolean isXRenderVerbose() {
/* 173 */     return xRenderVerbose;
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
/*     */   protected GraphicsDevice makeScreenDevice(int paramInt) {
/* 200 */     return new X11GraphicsDevice(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsDevice getDefaultScreenDevice() {
/* 208 */     GraphicsDevice[] arrayOfGraphicsDevice = getScreenDevices();
/* 209 */     if (arrayOfGraphicsDevice.length == 0) {
/* 210 */       throw new AWTError("no screen devices");
/*     */     }
/* 212 */     int i = getDefaultScreenNum();
/* 213 */     return arrayOfGraphicsDevice[(0 < i && i < arrayOfGraphicsDevice.length) ? i : 0];
/*     */   }
/*     */   
/*     */   public boolean isDisplayLocal() {
/* 217 */     if (this.isDisplayLocal == null) {
/* 218 */       SunToolkit.awtLock();
/*     */       try {
/* 220 */         if (this.isDisplayLocal == null) {
/* 221 */           this.isDisplayLocal = Boolean.valueOf(_isDisplayLocal());
/*     */         }
/*     */       } finally {
/* 224 */         SunToolkit.awtUnlock();
/*     */       } 
/*     */     } 
/* 227 */     return this.isDisplayLocal.booleanValue();
/*     */   }
/*     */   
/*     */   private static boolean _isDisplayLocal() {
/* 231 */     if (isHeadless()) {
/* 232 */       return true;
/*     */     }
/*     */     
/* 235 */     String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.remote"));
/*     */     
/* 237 */     if (str1 != null) {
/* 238 */       return str1.equals("false");
/*     */     }
/*     */     
/* 241 */     int i = checkShmExt();
/* 242 */     if (i != -1) {
/* 243 */       return (i == 1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     String str2 = getDisplayString();
/* 250 */     int j = str2.indexOf(':');
/* 251 */     final String hostName = str2.substring(0, j);
/* 252 */     if (j <= 0)
/*     */     {
/* 254 */       return true;
/*     */     }
/*     */     
/* 257 */     Boolean bool = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */         {
/*     */           public Object run() {
/* 260 */             InetAddress[] arrayOfInetAddress = null;
/* 261 */             Enumeration<InetAddress> enumeration = null;
/* 262 */             Enumeration<NetworkInterface> enumeration1 = null;
/*     */             try {
/* 264 */               enumeration1 = NetworkInterface.getNetworkInterfaces();
/* 265 */               arrayOfInetAddress = InetAddress.getAllByName(hostName);
/* 266 */               if (arrayOfInetAddress == null) {
/* 267 */                 return Boolean.FALSE;
/*     */               }
/* 269 */             } catch (UnknownHostException unknownHostException) {
/* 270 */               System.err.println("Unknown host: " + hostName);
/* 271 */               return Boolean.FALSE;
/* 272 */             } catch (SocketException socketException) {
/* 273 */               System.err.println(socketException.getMessage());
/* 274 */               return Boolean.FALSE;
/*     */             } 
/*     */             
/* 277 */             while (enumeration1.hasMoreElements()) {
/* 278 */               enumeration = ((NetworkInterface)enumeration1.nextElement()).getInetAddresses();
/* 279 */               while (enumeration.hasMoreElements()) {
/* 280 */                 for (byte b = 0; b < arrayOfInetAddress.length; b++) {
/* 281 */                   if (enumeration.nextElement().equals(arrayOfInetAddress[b])) {
/* 282 */                     return Boolean.TRUE;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/* 287 */             return Boolean.FALSE; }
/*     */         });
/* 289 */     return bool.booleanValue();
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
/*     */   public String getDefaultFontFaceName() {
/* 301 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getCenterPoint() {
/* 312 */     if (runningXinerama()) {
/* 313 */       Point point = getXineramaCenterPoint();
/* 314 */       if (point != null) {
/* 315 */         return point;
/*     */       }
/*     */     } 
/* 318 */     return super.getCenterPoint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getMaximumWindowBounds() {
/* 325 */     if (runningXinerama()) {
/* 326 */       return getXineramaWindowBounds();
/*     */     }
/* 328 */     return super.getMaximumWindowBounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean runningXinerama() {
/* 333 */     if (xinerState == null) {
/*     */ 
/*     */       
/* 336 */       xinerState = Boolean.valueOf(pRunningXinerama());
/* 337 */       if (screenLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 338 */         screenLog.finer("Running Xinerama: " + xinerState);
/*     */       }
/*     */     } 
/* 341 */     return xinerState.booleanValue();
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
/*     */ 
/*     */   
/*     */   protected Rectangle getXineramaWindowBounds() {
/* 373 */     Point point = getCenterPoint();
/*     */     
/* 375 */     GraphicsDevice[] arrayOfGraphicsDevice = getScreenDevices();
/* 376 */     Rectangle rectangle2 = null;
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
/* 399 */     Rectangle rectangle1 = getUsableBounds(arrayOfGraphicsDevice[0]);
/*     */     
/* 401 */     for (byte b = 0; b < arrayOfGraphicsDevice.length; b++) {
/* 402 */       Rectangle rectangle = getUsableBounds(arrayOfGraphicsDevice[b]);
/* 403 */       if (rectangle2 == null && rectangle.width / 2 + rectangle.x > point.x - 1 && rectangle.height / 2 + rectangle.y > point.y - 1 && rectangle.width / 2 + rectangle.x < point.x + 1 && rectangle.height / 2 + rectangle.y < point.y + 1)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 409 */         rectangle2 = rectangle;
/*     */       }
/* 411 */       rectangle1 = rectangle1.union(rectangle);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 416 */     if (rectangle1.width / 2 + rectangle1.x > point.x - 1 && rectangle1.height / 2 + rectangle1.y > point.y - 1 && rectangle1.width / 2 + rectangle1.x < point.x + 1 && rectangle1.height / 2 + rectangle1.y < point.y + 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 421 */       if (screenLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 422 */         screenLog.finer("Video Wall: center point is at center of all displays.");
/*     */       }
/* 424 */       return rectangle1;
/*     */     } 
/*     */ 
/*     */     
/* 428 */     if (rectangle2 != null) {
/* 429 */       if (screenLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 430 */         screenLog.finer("Center point at center of a particular monitor, but not of the entire virtual display.");
/*     */       }
/*     */       
/* 433 */       return rectangle2;
/*     */     } 
/*     */ 
/*     */     
/* 437 */     if (screenLog.isLoggable(PlatformLogger.Level.FINER)) {
/* 438 */       screenLog.finer("Center point is somewhere strange - return union of all bounds.");
/*     */     }
/* 440 */     return rectangle1;
/*     */   }
/*     */   
/*     */   public void paletteChanged() {}
/*     */   
/*     */   private static native boolean initGLX();
/*     */   
/*     */   private static native boolean initXRender(boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   private static native int checkShmExt();
/*     */   
/*     */   private static native String getDisplayString();
/*     */   
/*     */   private static native void initDisplay(boolean paramBoolean);
/*     */   
/*     */   protected native int getNumScreens();
/*     */   
/*     */   protected native int getDefaultScreenNum();
/*     */   
/*     */   private static native boolean pRunningXinerama();
/*     */   
/*     */   private static native Point getXineramaCenterPoint();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11GraphicsEnvironment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */