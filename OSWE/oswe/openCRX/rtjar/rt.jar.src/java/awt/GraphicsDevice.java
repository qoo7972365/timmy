/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.image.ColorModel;
/*     */ import sun.awt.AppContext;
/*     */ import sun.awt.SunToolkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GraphicsDevice
/*     */ {
/*     */   private Window fullScreenWindow;
/*     */   private AppContext fullScreenAppContext;
/*  85 */   private final Object fsAppContextLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rectangle windowedModeBounds;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int TYPE_RASTER_SCREEN = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int TYPE_PRINTER = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int TYPE_IMAGE_BUFFER = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getType();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getIDstring();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract GraphicsConfiguration[] getConfigurations();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract GraphicsConfiguration getDefaultConfiguration();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum WindowTranslucency
/*     */   {
/* 129 */     PERPIXEL_TRANSPARENT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     TRANSLUCENT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     PERPIXEL_TRANSLUCENT;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsConfiguration getBestConfiguration(GraphicsConfigTemplate paramGraphicsConfigTemplate) {
/* 206 */     GraphicsConfiguration[] arrayOfGraphicsConfiguration = getConfigurations();
/* 207 */     return paramGraphicsConfigTemplate.getBestConfiguration(arrayOfGraphicsConfiguration);
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
/*     */   public boolean isFullScreenSupported() {
/* 224 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFullScreenWindow(Window paramWindow) {
/* 286 */     if (paramWindow != null) {
/* 287 */       if (paramWindow.getShape() != null) {
/* 288 */         paramWindow.setShape((Shape)null);
/*     */       }
/* 290 */       if (paramWindow.getOpacity() < 1.0F) {
/* 291 */         paramWindow.setOpacity(1.0F);
/*     */       }
/* 293 */       if (!paramWindow.isOpaque()) {
/* 294 */         Color color = paramWindow.getBackground();
/*     */         
/* 296 */         color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 255);
/* 297 */         paramWindow.setBackground(color);
/*     */       } 
/*     */       
/* 300 */       GraphicsConfiguration graphicsConfiguration = paramWindow.getGraphicsConfiguration();
/* 301 */       if (graphicsConfiguration != null && graphicsConfiguration.getDevice() != this && graphicsConfiguration
/* 302 */         .getDevice().getFullScreenWindow() == paramWindow) {
/* 303 */         graphicsConfiguration.getDevice().setFullScreenWindow(null);
/*     */       }
/*     */     } 
/* 306 */     if (this.fullScreenWindow != null && this.windowedModeBounds != null) {
/*     */ 
/*     */       
/* 309 */       if (this.windowedModeBounds.width == 0) this.windowedModeBounds.width = 1; 
/* 310 */       if (this.windowedModeBounds.height == 0) this.windowedModeBounds.height = 1; 
/* 311 */       this.fullScreenWindow.setBounds(this.windowedModeBounds);
/*     */     } 
/*     */     
/* 314 */     synchronized (this.fsAppContextLock) {
/*     */       
/* 316 */       if (paramWindow == null) {
/* 317 */         this.fullScreenAppContext = null;
/*     */       } else {
/* 319 */         this.fullScreenAppContext = AppContext.getAppContext();
/*     */       } 
/* 321 */       this.fullScreenWindow = paramWindow;
/*     */     } 
/* 323 */     if (this.fullScreenWindow != null) {
/* 324 */       this.windowedModeBounds = this.fullScreenWindow.getBounds();
/*     */ 
/*     */ 
/*     */       
/* 328 */       GraphicsConfiguration graphicsConfiguration = getDefaultConfiguration();
/* 329 */       Rectangle rectangle = graphicsConfiguration.getBounds();
/* 330 */       if (SunToolkit.isDispatchThreadForAppContext(this.fullScreenWindow))
/*     */       {
/*     */ 
/*     */         
/* 334 */         this.fullScreenWindow.setGraphicsConfiguration(graphicsConfiguration);
/*     */       }
/* 336 */       this.fullScreenWindow.setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */       
/* 338 */       this.fullScreenWindow.setVisible(true);
/* 339 */       this.fullScreenWindow.toFront();
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
/*     */   public Window getFullScreenWindow() {
/* 353 */     Window window = null;
/* 354 */     synchronized (this.fsAppContextLock) {
/*     */ 
/*     */       
/* 357 */       if (this.fullScreenAppContext == AppContext.getAppContext()) {
/* 358 */         window = this.fullScreenWindow;
/*     */       }
/*     */     } 
/* 361 */     return window;
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
/*     */   public boolean isDisplayChangeSupported() {
/* 379 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayMode(DisplayMode paramDisplayMode) {
/* 434 */     throw new UnsupportedOperationException("Cannot change display mode");
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
/*     */   public DisplayMode getDisplayMode() {
/* 450 */     GraphicsConfiguration graphicsConfiguration = getDefaultConfiguration();
/* 451 */     Rectangle rectangle = graphicsConfiguration.getBounds();
/* 452 */     ColorModel colorModel = graphicsConfiguration.getColorModel();
/* 453 */     return new DisplayMode(rectangle.width, rectangle.height, colorModel.getPixelSize(), 0);
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
/*     */   public DisplayMode[] getDisplayModes() {
/* 468 */     return new DisplayMode[] { getDisplayMode() };
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
/*     */   public int getAvailableAcceleratedMemory() {
/* 500 */     return -1;
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
/*     */   public boolean isWindowTranslucencySupported(WindowTranslucency paramWindowTranslucency) {
/* 513 */     switch (paramWindowTranslucency) {
/*     */       case PERPIXEL_TRANSPARENT:
/* 515 */         return isWindowShapingSupported();
/*     */       case TRANSLUCENT:
/* 517 */         return isWindowOpacitySupported();
/*     */       case PERPIXEL_TRANSLUCENT:
/* 519 */         return isWindowPerpixelTranslucencySupported();
/*     */     } 
/* 521 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isWindowShapingSupported() {
/* 532 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 533 */     if (!(toolkit instanceof SunToolkit)) {
/* 534 */       return false;
/*     */     }
/* 536 */     return ((SunToolkit)toolkit).isWindowShapingSupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isWindowOpacitySupported() {
/* 547 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 548 */     if (!(toolkit instanceof SunToolkit)) {
/* 549 */       return false;
/*     */     }
/* 551 */     return ((SunToolkit)toolkit).isWindowOpacitySupported();
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
/*     */   boolean isWindowPerpixelTranslucencySupported() {
/* 563 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 564 */     if (!(toolkit instanceof SunToolkit)) {
/* 565 */       return false;
/*     */     }
/* 567 */     if (!((SunToolkit)toolkit).isWindowTranslucencySupported()) {
/* 568 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 572 */     return (getTranslucencyCapableGC() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GraphicsConfiguration getTranslucencyCapableGC() {
/* 579 */     GraphicsConfiguration graphicsConfiguration = getDefaultConfiguration();
/* 580 */     if (graphicsConfiguration.isTranslucencyCapable()) {
/* 581 */       return graphicsConfiguration;
/*     */     }
/*     */ 
/*     */     
/* 585 */     GraphicsConfiguration[] arrayOfGraphicsConfiguration = getConfigurations();
/* 586 */     for (byte b = 0; b < arrayOfGraphicsConfiguration.length; b++) {
/* 587 */       if (arrayOfGraphicsConfiguration[b].isTranslucencyCapable()) {
/* 588 */         return arrayOfGraphicsConfiguration[b];
/*     */       }
/*     */     } 
/*     */     
/* 592 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/GraphicsDevice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */