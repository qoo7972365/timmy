/*     */ package com.sun.awt;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import sun.awt.AWTAccessor;
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
/*     */ public final class AWTUtilities
/*     */ {
/*     */   public enum Translucency
/*     */   {
/*  83 */     PERPIXEL_TRANSPARENT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     TRANSLUCENT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     PERPIXEL_TRANSLUCENT;
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
/*     */   public static boolean isTranslucencySupported(Translucency paramTranslucency) {
/* 117 */     switch (paramTranslucency) {
/*     */       case PERPIXEL_TRANSPARENT:
/* 119 */         return isWindowShapingSupported();
/*     */       case TRANSLUCENT:
/* 121 */         return isWindowOpacitySupported();
/*     */       case PERPIXEL_TRANSLUCENT:
/* 123 */         return isWindowTranslucencySupported();
/*     */     } 
/* 125 */     return false;
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
/*     */   private static boolean isWindowOpacitySupported() {
/* 137 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 138 */     if (!(toolkit instanceof SunToolkit)) {
/* 139 */       return false;
/*     */     }
/* 141 */     return ((SunToolkit)toolkit).isWindowOpacitySupported();
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
/*     */   public static void setWindowOpacity(Window paramWindow, float paramFloat) {
/* 169 */     if (paramWindow == null) {
/* 170 */       throw new NullPointerException("The window argument should not be null.");
/*     */     }
/*     */ 
/*     */     
/* 174 */     AWTAccessor.getWindowAccessor().setOpacity(paramWindow, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getWindowOpacity(Window paramWindow) {
/* 185 */     if (paramWindow == null) {
/* 186 */       throw new NullPointerException("The window argument should not be null.");
/*     */     }
/*     */ 
/*     */     
/* 190 */     return AWTAccessor.getWindowAccessor().getOpacity(paramWindow);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isWindowShapingSupported() {
/* 201 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 202 */     if (!(toolkit instanceof SunToolkit)) {
/* 203 */       return false;
/*     */     }
/* 205 */     return ((SunToolkit)toolkit).isWindowShapingSupported();
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
/*     */   public static Shape getWindowShape(Window paramWindow) {
/* 219 */     if (paramWindow == null) {
/* 220 */       throw new NullPointerException("The window argument should not be null.");
/*     */     }
/*     */     
/* 223 */     return AWTAccessor.getWindowAccessor().getShape(paramWindow);
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
/*     */   public static void setWindowShape(Window paramWindow, Shape paramShape) {
/* 250 */     if (paramWindow == null) {
/* 251 */       throw new NullPointerException("The window argument should not be null.");
/*     */     }
/*     */     
/* 254 */     AWTAccessor.getWindowAccessor().setShape(paramWindow, paramShape);
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
/*     */   private static boolean isWindowTranslucencySupported() {
/* 267 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 268 */     if (!(toolkit instanceof SunToolkit)) {
/* 269 */       return false;
/*     */     }
/*     */     
/* 272 */     if (!((SunToolkit)toolkit).isWindowTranslucencySupported()) {
/* 273 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 277 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 282 */     if (isTranslucencyCapable(graphicsEnvironment.getDefaultScreenDevice()
/* 283 */         .getDefaultConfiguration()))
/*     */     {
/* 285 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 289 */     GraphicsDevice[] arrayOfGraphicsDevice = graphicsEnvironment.getScreenDevices();
/*     */     
/* 291 */     for (byte b = 0; b < arrayOfGraphicsDevice.length; b++) {
/* 292 */       GraphicsConfiguration[] arrayOfGraphicsConfiguration = arrayOfGraphicsDevice[b].getConfigurations();
/* 293 */       for (byte b1 = 0; b1 < arrayOfGraphicsConfiguration.length; b1++) {
/* 294 */         if (isTranslucencyCapable(arrayOfGraphicsConfiguration[b1])) {
/* 295 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 300 */     return false;
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
/*     */   public static void setWindowOpaque(Window paramWindow, boolean paramBoolean) {
/* 352 */     if (paramWindow == null) {
/* 353 */       throw new NullPointerException("The window argument should not be null.");
/*     */     }
/*     */     
/* 356 */     if (!paramBoolean && !isTranslucencySupported(Translucency.PERPIXEL_TRANSLUCENT)) {
/* 357 */       throw new UnsupportedOperationException("The PERPIXEL_TRANSLUCENT translucency kind is not supported");
/*     */     }
/*     */     
/* 360 */     AWTAccessor.getWindowAccessor().setOpaque(paramWindow, paramBoolean);
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
/*     */   public static boolean isWindowOpaque(Window paramWindow) {
/* 372 */     if (paramWindow == null) {
/* 373 */       throw new NullPointerException("The window argument should not be null.");
/*     */     }
/*     */ 
/*     */     
/* 377 */     return paramWindow.isOpaque();
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
/*     */   public static boolean isTranslucencyCapable(GraphicsConfiguration paramGraphicsConfiguration) {
/* 398 */     if (paramGraphicsConfiguration == null) {
/* 399 */       throw new NullPointerException("The gc argument should not be null");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 404 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 405 */     if (!(toolkit instanceof SunToolkit)) {
/* 406 */       return false;
/*     */     }
/* 408 */     return ((SunToolkit)toolkit).isTranslucencyCapable(paramGraphicsConfiguration);
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
/*     */   public static void setComponentMixingCutoutShape(Component paramComponent, Shape paramShape) {
/* 453 */     if (paramComponent == null) {
/* 454 */       throw new NullPointerException("The component argument should not be null.");
/*     */     }
/*     */ 
/*     */     
/* 458 */     AWTAccessor.getComponentAccessor().setMixingCutoutShape(paramComponent, paramShape);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/awt/AWTUtilities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */