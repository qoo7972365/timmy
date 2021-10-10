/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.AWTPermission;
/*     */ import java.awt.DisplayMode;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Window;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import sun.java2d.loops.SurfaceType;
/*     */ import sun.java2d.opengl.GLXGraphicsConfig;
/*     */ import sun.java2d.xr.XRGraphicsConfig;
/*     */ import sun.misc.ThreadGroupUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X11GraphicsDevice
/*     */   extends GraphicsDevice
/*     */   implements DisplayChangedListener
/*     */ {
/*     */   int screen;
/*  59 */   HashMap x11ProxyKeyMap = new HashMap<>();
/*     */   
/*     */   private static AWTPermission fullScreenExclusivePermission;
/*     */   private static Boolean xrandrExtSupported;
/*  63 */   private final Object configLock = new Object();
/*  64 */   private SunDisplayChanger topLevels = new SunDisplayChanger();
/*     */   private DisplayMode origDisplayMode;
/*     */   private boolean shutdownHookRegistered;
/*     */   
/*     */   public X11GraphicsDevice(int paramInt) {
/*  69 */     this.screen = paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   GraphicsConfiguration[] configs;
/*     */   
/*     */   GraphicsConfiguration defaultConfig;
/*     */   HashSet doubleBufferVisuals;
/*     */   
/*     */   static {
/*  79 */     if (!GraphicsEnvironment.isHeadless()) {
/*  80 */       initIDs();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScreen() {
/*  88 */     return this.screen;
/*     */   }
/*     */   
/*     */   public Object getProxyKeyFor(SurfaceType paramSurfaceType) {
/*  92 */     synchronized (this.x11ProxyKeyMap) {
/*  93 */       Object object = this.x11ProxyKeyMap.get(paramSurfaceType);
/*  94 */       if (object == null) {
/*  95 */         object = new Object();
/*  96 */         this.x11ProxyKeyMap.put(paramSurfaceType, object);
/*     */       } 
/*  98 */       return object;
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
/*     */   public int getType() {
/* 116 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIDstring() {
/* 124 */     return ":0." + this.screen;
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
/*     */   public GraphicsConfiguration[] getConfigurations() {
/* 137 */     if (this.configs == null) {
/* 138 */       synchronized (this.configLock) {
/* 139 */         makeConfigurations();
/*     */       } 
/*     */     }
/* 142 */     return (GraphicsConfiguration[])this.configs.clone();
/*     */   }
/*     */   
/*     */   private void makeConfigurations() {
/* 146 */     if (this.configs == null) {
/* 147 */       byte b = 1;
/* 148 */       int i = getNumConfigs(this.screen);
/* 149 */       GraphicsConfiguration[] arrayOfGraphicsConfiguration = new GraphicsConfiguration[i];
/* 150 */       if (this.defaultConfig == null) {
/* 151 */         arrayOfGraphicsConfiguration[0] = getDefaultConfiguration();
/*     */       } else {
/*     */         
/* 154 */         arrayOfGraphicsConfiguration[0] = this.defaultConfig;
/*     */       } 
/*     */       
/* 157 */       boolean bool1 = X11GraphicsEnvironment.isGLXAvailable();
/* 158 */       boolean bool2 = X11GraphicsEnvironment.isXRenderAvailable();
/*     */       
/* 160 */       boolean bool3 = isDBESupported();
/* 161 */       if (bool3 && this.doubleBufferVisuals == null) {
/* 162 */         this.doubleBufferVisuals = new HashSet();
/* 163 */         getDoubleBufferVisuals(this.screen);
/*     */       } 
/* 165 */       for (; b < i; b++) {
/* 166 */         int j = getConfigVisualId(b, this.screen);
/* 167 */         int k = getConfigDepth(b, this.screen);
/* 168 */         if (bool1) {
/* 169 */           arrayOfGraphicsConfiguration[b] = (GraphicsConfiguration)GLXGraphicsConfig.getConfig(this, j);
/*     */         }
/* 171 */         if (arrayOfGraphicsConfiguration[b] == null) {
/*     */ 
/*     */           
/* 174 */           boolean bool = (bool3 && this.doubleBufferVisuals.contains(Integer.valueOf(j))) ? true : false;
/*     */           
/* 176 */           if (bool2) {
/* 177 */             arrayOfGraphicsConfiguration[b] = (GraphicsConfiguration)XRGraphicsConfig.getConfig(this, j, k, getConfigColormap(b, this.screen), bool);
/*     */           } else {
/*     */             
/* 180 */             arrayOfGraphicsConfiguration[b] = X11GraphicsConfig.getConfig(this, j, k, 
/* 181 */                 getConfigColormap(b, this.screen), bool);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 186 */       this.configs = arrayOfGraphicsConfiguration;
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
/*     */   private void addDoubleBufferVisual(int paramInt) {
/* 215 */     this.doubleBufferVisuals.add(Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsConfiguration getDefaultConfiguration() {
/* 225 */     if (this.defaultConfig == null) {
/* 226 */       synchronized (this.configLock) {
/* 227 */         makeDefaultConfiguration();
/*     */       } 
/*     */     }
/* 230 */     return this.defaultConfig;
/*     */   }
/*     */   
/*     */   private void makeDefaultConfiguration() {
/* 234 */     if (this.defaultConfig == null) {
/* 235 */       int i = getConfigVisualId(0, this.screen);
/* 236 */       if (X11GraphicsEnvironment.isGLXAvailable()) {
/* 237 */         this.defaultConfig = (GraphicsConfiguration)GLXGraphicsConfig.getConfig(this, i);
/* 238 */         if (X11GraphicsEnvironment.isGLXVerbose()) {
/* 239 */           if (this.defaultConfig != null) {
/* 240 */             System.out.print("OpenGL pipeline enabled");
/*     */           } else {
/* 242 */             System.out.print("Could not enable OpenGL pipeline");
/*     */           } 
/* 244 */           System.out.println(" for default config on screen " + this.screen);
/*     */         } 
/*     */       } 
/*     */       
/* 248 */       if (this.defaultConfig == null) {
/* 249 */         int j = getConfigDepth(0, this.screen);
/* 250 */         boolean bool = false;
/* 251 */         if (isDBESupported() && this.doubleBufferVisuals == null) {
/* 252 */           this.doubleBufferVisuals = new HashSet();
/* 253 */           getDoubleBufferVisuals(this.screen);
/*     */           
/* 255 */           bool = this.doubleBufferVisuals.contains(Integer.valueOf(i));
/*     */         } 
/*     */         
/* 258 */         if (X11GraphicsEnvironment.isXRenderAvailable()) {
/* 259 */           if (X11GraphicsEnvironment.isXRenderVerbose()) {
/* 260 */             System.out.println("XRender pipeline enabled");
/*     */           }
/* 262 */           this.defaultConfig = (GraphicsConfiguration)XRGraphicsConfig.getConfig(this, i, j, 
/* 263 */               getConfigColormap(0, this.screen), bool);
/*     */         } else {
/*     */           
/* 266 */           this.defaultConfig = X11GraphicsConfig.getConfig(this, i, j, 
/* 267 */               getConfigColormap(0, this.screen), bool);
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
/*     */   private static synchronized boolean isXrandrExtensionSupported() {
/* 291 */     if (xrandrExtSupported == null)
/*     */     {
/* 293 */       xrandrExtSupported = Boolean.valueOf(initXrandrExtension());
/*     */     }
/* 295 */     return xrandrExtSupported.booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullScreenSupported() {
/* 300 */     boolean bool = isXrandrExtensionSupported();
/* 301 */     if (bool) {
/* 302 */       SecurityManager securityManager = System.getSecurityManager();
/* 303 */       if (securityManager != null) {
/* 304 */         if (fullScreenExclusivePermission == null) {
/* 305 */           fullScreenExclusivePermission = new AWTPermission("fullScreenExclusive");
/*     */         }
/*     */         
/*     */         try {
/* 309 */           securityManager.checkPermission(fullScreenExclusivePermission);
/* 310 */         } catch (SecurityException securityException) {
/* 311 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/* 315 */     return bool;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDisplayChangeSupported() {
/* 320 */     if (isFullScreenSupported() && 
/* 321 */       getFullScreenWindow() != null)
/*     */     {
/* 323 */       if (!((X11GraphicsEnvironment)GraphicsEnvironment.getLocalGraphicsEnvironment()).runningXinerama()); } 
/*     */     return false;
/*     */   }
/*     */   private static void enterFullScreenExclusive(Window paramWindow) {
/* 327 */     X11ComponentPeer x11ComponentPeer = (X11ComponentPeer)paramWindow.getPeer();
/* 328 */     if (x11ComponentPeer != null) {
/* 329 */       enterFullScreenExclusive(x11ComponentPeer.getWindow());
/* 330 */       x11ComponentPeer.setFullScreenExclusiveModeState(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void exitFullScreenExclusive(Window paramWindow) {
/* 335 */     X11ComponentPeer x11ComponentPeer = (X11ComponentPeer)paramWindow.getPeer();
/* 336 */     if (x11ComponentPeer != null) {
/* 337 */       x11ComponentPeer.setFullScreenExclusiveModeState(false);
/* 338 */       exitFullScreenExclusive(x11ComponentPeer.getWindow());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setFullScreenWindow(Window paramWindow) {
/* 344 */     Window window = getFullScreenWindow();
/* 345 */     if (paramWindow == window) {
/*     */       return;
/*     */     }
/*     */     
/* 349 */     boolean bool = isFullScreenSupported();
/* 350 */     if (bool && window != null) {
/*     */       
/* 352 */       exitFullScreenExclusive(window);
/* 353 */       if (isDisplayChangeSupported()) {
/* 354 */         setDisplayMode(this.origDisplayMode);
/*     */       }
/*     */     } 
/*     */     
/* 358 */     super.setFullScreenWindow(paramWindow);
/*     */     
/* 360 */     if (bool && paramWindow != null) {
/*     */       
/* 362 */       if (this.origDisplayMode == null) {
/* 363 */         this.origDisplayMode = getDisplayMode();
/*     */       }
/*     */ 
/*     */       
/* 367 */       enterFullScreenExclusive(paramWindow);
/*     */     } 
/*     */   }
/*     */   
/*     */   private DisplayMode getDefaultDisplayMode() {
/* 372 */     GraphicsConfiguration graphicsConfiguration = getDefaultConfiguration();
/* 373 */     Rectangle rectangle = graphicsConfiguration.getBounds();
/* 374 */     return new DisplayMode(rectangle.width, rectangle.height, -1, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized DisplayMode getDisplayMode() {
/* 381 */     if (isFullScreenSupported()) {
/* 382 */       DisplayMode displayMode = getCurrentDisplayMode(this.screen);
/* 383 */       if (displayMode == null) {
/* 384 */         displayMode = getDefaultDisplayMode();
/*     */       }
/* 386 */       return displayMode;
/*     */     } 
/* 388 */     if (this.origDisplayMode == null) {
/* 389 */       this.origDisplayMode = getDefaultDisplayMode();
/*     */     }
/* 391 */     return this.origDisplayMode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized DisplayMode[] getDisplayModes() {
/* 397 */     if (!isFullScreenSupported()) {
/* 398 */       return super.getDisplayModes();
/*     */     }
/* 400 */     ArrayList<DisplayMode> arrayList = new ArrayList();
/* 401 */     enumDisplayModes(this.screen, arrayList);
/* 402 */     DisplayMode[] arrayOfDisplayMode = new DisplayMode[arrayList.size()];
/* 403 */     return arrayList.<DisplayMode>toArray(arrayOfDisplayMode);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setDisplayMode(DisplayMode paramDisplayMode) {
/* 408 */     if (!isDisplayChangeSupported()) {
/* 409 */       super.setDisplayMode(paramDisplayMode);
/*     */       return;
/*     */     } 
/* 412 */     Window window = getFullScreenWindow();
/* 413 */     if (window == null) {
/* 414 */       throw new IllegalStateException("Must be in fullscreen mode in order to set display mode");
/*     */     }
/*     */     
/* 417 */     if (getDisplayMode().equals(paramDisplayMode)) {
/*     */       return;
/*     */     }
/* 420 */     if (paramDisplayMode == null || (
/* 421 */       paramDisplayMode = getMatchingDisplayMode(paramDisplayMode)) == null)
/*     */     {
/* 423 */       throw new IllegalArgumentException("Invalid display mode");
/*     */     }
/*     */     
/* 426 */     if (!this.shutdownHookRegistered) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 431 */       this.shutdownHookRegistered = true;
/* 432 */       PrivilegedAction<?> privilegedAction = () -> {
/*     */           ThreadGroup threadGroup = ThreadGroupUtils.getRootThreadGroup();
/*     */ 
/*     */           
/*     */           Runnable runnable = ();
/*     */ 
/*     */           
/*     */           Thread thread = new Thread(threadGroup, runnable, "Display-Change-Shutdown-Thread-" + this.screen);
/*     */           
/*     */           thread.setContextClassLoader(null);
/*     */           
/*     */           Runtime.getRuntime().addShutdownHook(thread);
/*     */           
/*     */           return null;
/*     */         };
/*     */       
/* 448 */       AccessController.doPrivileged(privilegedAction);
/*     */     } 
/*     */ 
/*     */     
/* 452 */     configDisplayMode(this.screen, paramDisplayMode
/* 453 */         .getWidth(), paramDisplayMode.getHeight(), paramDisplayMode
/* 454 */         .getRefreshRate());
/*     */ 
/*     */     
/* 457 */     window.setBounds(0, 0, paramDisplayMode.getWidth(), paramDisplayMode.getHeight());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 463 */     ((X11GraphicsEnvironment)GraphicsEnvironment.getLocalGraphicsEnvironment()).displayChanged();
/*     */   }
/*     */   
/*     */   private synchronized DisplayMode getMatchingDisplayMode(DisplayMode paramDisplayMode) {
/* 467 */     if (!isDisplayChangeSupported()) {
/* 468 */       return null;
/*     */     }
/* 470 */     DisplayMode[] arrayOfDisplayMode = getDisplayModes();
/* 471 */     for (DisplayMode displayMode : arrayOfDisplayMode) {
/* 472 */       if (paramDisplayMode.equals(displayMode) || (paramDisplayMode
/* 473 */         .getRefreshRate() == 0 && paramDisplayMode
/* 474 */         .getWidth() == displayMode.getWidth() && paramDisplayMode
/* 475 */         .getHeight() == displayMode.getHeight() && paramDisplayMode
/* 476 */         .getBitDepth() == displayMode.getBitDepth()))
/*     */       {
/* 478 */         return displayMode;
/*     */       }
/*     */     } 
/* 481 */     return null;
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
/*     */   public synchronized void displayChanged() {
/* 494 */     this.topLevels.notifyListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paletteChanged() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDisplayChangedListener(DisplayChangedListener paramDisplayChangedListener) {
/* 510 */     this.topLevels.add(paramDisplayChangedListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeDisplayChangedListener(DisplayChangedListener paramDisplayChangedListener) {
/* 517 */     this.topLevels.remove(paramDisplayChangedListener);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 521 */     return "X11GraphicsDevice[screen=" + this.screen + "]";
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   public native long getDisplay();
/*     */   
/*     */   public native int getNumConfigs(int paramInt);
/*     */   
/*     */   public native int getConfigVisualId(int paramInt1, int paramInt2);
/*     */   
/*     */   public native int getConfigDepth(int paramInt1, int paramInt2);
/*     */   
/*     */   public native int getConfigColormap(int paramInt1, int paramInt2);
/*     */   
/*     */   public static native boolean isDBESupported();
/*     */   
/*     */   private native void getDoubleBufferVisuals(int paramInt);
/*     */   
/*     */   private static native void enterFullScreenExclusive(long paramLong);
/*     */   
/*     */   private static native void exitFullScreenExclusive(long paramLong);
/*     */   
/*     */   private static native boolean initXrandrExtension();
/*     */   
/*     */   private static native DisplayMode getCurrentDisplayMode(int paramInt);
/*     */   
/*     */   private static native void enumDisplayModes(int paramInt, ArrayList<DisplayMode> paramArrayList);
/*     */   
/*     */   private static native void configDisplayMode(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   private static native void resetNativeData(int paramInt);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11GraphicsDevice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */