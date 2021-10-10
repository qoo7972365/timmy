/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.security.AccessController;
/*     */ import java.util.Locale;
/*     */ import sun.font.FontManager;
/*     */ import sun.font.FontManagerFactory;
/*     */ import sun.java2d.HeadlessGraphicsEnvironment;
/*     */ import sun.java2d.SunGraphicsEnvironment;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GraphicsEnvironment
/*     */ {
/*     */   private static GraphicsEnvironment localEnv;
/*     */   private static Boolean headless;
/*     */   private static Boolean defaultHeadless;
/*     */   
/*     */   public static synchronized GraphicsEnvironment getLocalGraphicsEnvironment() {
/*  81 */     if (localEnv == null) {
/*  82 */       localEnv = createGE();
/*     */     }
/*     */     
/*  85 */     return localEnv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static GraphicsEnvironment createGE() {
/*     */     GraphicsEnvironment graphicsEnvironment;
/*  96 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("java.awt.graphicsenv", null));
/*     */ 
/*     */     
/*     */     try {
/*     */       Class<?> clazz;
/*     */       
/*     */       try {
/* 103 */         clazz = Class.forName(str);
/* 104 */       } catch (ClassNotFoundException classNotFoundException) {
/*     */ 
/*     */         
/* 107 */         ClassLoader classLoader = ClassLoader.getSystemClassLoader();
/* 108 */         clazz = Class.forName(str, true, classLoader);
/*     */       } 
/* 110 */       graphicsEnvironment = (GraphicsEnvironment)clazz.newInstance();
/*     */ 
/*     */       
/* 113 */       if (isHeadless()) {
/* 114 */         graphicsEnvironment = new HeadlessGraphicsEnvironment(graphicsEnvironment);
/*     */       }
/* 116 */     } catch (ClassNotFoundException classNotFoundException) {
/* 117 */       throw new Error("Could not find class: " + str);
/* 118 */     } catch (InstantiationException instantiationException) {
/* 119 */       throw new Error("Could not instantiate Graphics Environment: " + str);
/*     */     }
/* 121 */     catch (IllegalAccessException illegalAccessException) {
/* 122 */       throw new Error("Could not access Graphics Environment: " + str);
/*     */     } 
/*     */     
/* 125 */     return graphicsEnvironment;
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
/*     */   public static boolean isHeadless() {
/* 141 */     return getHeadlessProperty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getHeadlessMessage() {
/* 150 */     if (headless == null) {
/* 151 */       getHeadlessProperty();
/*     */     }
/* 153 */     return (defaultHeadless != Boolean.TRUE) ? null : "\nNo X11 DISPLAY variable was set, but this program performed an operation which requires it.";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean getHeadlessProperty() {
/* 163 */     if (headless == null) {
/* 164 */       AccessController.doPrivileged(() -> {
/*     */             String str = System.getProperty("java.awt.headless");
/*     */ 
/*     */             
/*     */             if (str == null) {
/*     */               if (System.getProperty("javaplugin.version") != null) {
/*     */                 headless = defaultHeadless = Boolean.FALSE;
/*     */               } else {
/*     */                 String str1 = System.getProperty("os.name");
/*     */                 
/*     */                 if (str1.contains("OS X") && "sun.awt.HToolkit".equals(System.getProperty("awt.toolkit"))) {
/*     */                   headless = defaultHeadless = Boolean.TRUE;
/*     */                 } else {
/*     */                   String str2 = System.getenv("DISPLAY");
/*     */                   
/* 179 */                   headless = defaultHeadless = Boolean.valueOf((("Linux".equals(str1) || "SunOS".equals(str1) || "FreeBSD".equals(str1) || "NetBSD".equals(str1) || "OpenBSD".equals(str1) || "AIX".equals(str1)) && (str2 == null || str2.trim().isEmpty())));
/*     */                 } 
/*     */               } 
/*     */             } else {
/*     */               headless = Boolean.valueOf(str);
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             return null;
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 195 */     return headless.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void checkHeadless() throws HeadlessException {
/* 203 */     if (isHeadless()) {
/* 204 */       throw new HeadlessException();
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
/*     */   public boolean isHeadlessInstance() {
/* 224 */     return getHeadlessProperty();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean registerFont(Font paramFont) {
/* 363 */     if (paramFont == null) {
/* 364 */       throw new NullPointerException("font cannot be null.");
/*     */     }
/* 366 */     FontManager fontManager = FontManagerFactory.getInstance();
/* 367 */     return fontManager.registerFont(paramFont);
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
/*     */   public void preferLocaleFonts() {
/* 391 */     FontManager fontManager = FontManagerFactory.getInstance();
/* 392 */     fontManager.preferLocaleFonts();
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
/*     */   public void preferProportionalFonts() {
/* 412 */     FontManager fontManager = FontManagerFactory.getInstance();
/* 413 */     fontManager.preferProportionalFonts();
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
/*     */   public Point getCenterPoint() throws HeadlessException {
/* 430 */     Rectangle rectangle = SunGraphicsEnvironment.getUsableBounds(getDefaultScreenDevice());
/* 431 */     return new Point(rectangle.width / 2 + rectangle.x, rectangle.height / 2 + rectangle.y);
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
/*     */   public Rectangle getMaximumWindowBounds() throws HeadlessException {
/* 457 */     return SunGraphicsEnvironment.getUsableBounds(getDefaultScreenDevice());
/*     */   }
/*     */   
/*     */   public abstract GraphicsDevice[] getScreenDevices() throws HeadlessException;
/*     */   
/*     */   public abstract GraphicsDevice getDefaultScreenDevice() throws HeadlessException;
/*     */   
/*     */   public abstract Graphics2D createGraphics(BufferedImage paramBufferedImage);
/*     */   
/*     */   public abstract Font[] getAllFonts();
/*     */   
/*     */   public abstract String[] getAvailableFontFamilyNames();
/*     */   
/*     */   public abstract String[] getAvailableFontFamilyNames(Locale paramLocale);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/GraphicsEnvironment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */