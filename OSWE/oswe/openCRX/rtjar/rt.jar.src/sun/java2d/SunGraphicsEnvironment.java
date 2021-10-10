/*     */ package sun.java2d;
/*     */ 
/*     */ import java.awt.AWTError;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import java.util.TreeMap;
/*     */ import sun.awt.DisplayChangedListener;
/*     */ import sun.awt.SunDisplayChanger;
/*     */ import sun.font.FontManager;
/*     */ import sun.font.FontManagerFactory;
/*     */ import sun.font.FontManagerForSGE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SunGraphicsEnvironment
/*     */   extends GraphicsEnvironment
/*     */   implements DisplayChangedListener
/*     */ {
/*     */   public static boolean isOpenSolaris;
/*     */   private static Font defaultFont;
/*     */   protected GraphicsDevice[] screens;
/*     */   protected SunDisplayChanger displayChanger;
/*     */   
/*     */   public synchronized GraphicsDevice[] getScreenDevices() {
/*     */     GraphicsDevice[] arrayOfGraphicsDevice = this.screens;
/*     */     if (arrayOfGraphicsDevice == null) {
/*     */       int i = getNumScreens();
/*     */       arrayOfGraphicsDevice = new GraphicsDevice[i];
/*     */       for (byte b = 0; b < i; b++)
/*     */         arrayOfGraphicsDevice[b] = makeScreenDevice(b); 
/*     */       this.screens = arrayOfGraphicsDevice;
/*     */     } 
/*     */     return arrayOfGraphicsDevice;
/*     */   }
/*     */   
/*     */   public GraphicsDevice getDefaultScreenDevice() {
/*     */     GraphicsDevice[] arrayOfGraphicsDevice = getScreenDevices();
/*     */     if (arrayOfGraphicsDevice.length == 0)
/*     */       throw new AWTError("no screen devices"); 
/*     */     return arrayOfGraphicsDevice[0];
/*     */   }
/*     */   
/*     */   public Graphics2D createGraphics(BufferedImage paramBufferedImage) {
/*     */     if (paramBufferedImage == null)
/*     */       throw new NullPointerException("BufferedImage cannot be null"); 
/*     */     SurfaceData surfaceData = SurfaceData.getPrimarySurfaceData(paramBufferedImage);
/*     */     return new SunGraphics2D(surfaceData, Color.white, Color.black, defaultFont);
/*     */   }
/*     */   
/*     */   public static FontManagerForSGE getFontManagerForSGE() {
/*     */     FontManager fontManager = FontManagerFactory.getInstance();
/*     */     return (FontManagerForSGE)fontManager;
/*     */   }
/*     */   
/*     */   public SunGraphicsEnvironment() {
/* 308 */     this.displayChanger = new SunDisplayChanger(); AccessController.doPrivileged(new PrivilegedAction() { public Object run() { String str = System.getProperty("os.version", "0.0"); try { float f = Float.parseFloat(str); if (f > 5.1F) { File file = new File("/etc/release"); FileInputStream fileInputStream = new FileInputStream(file); InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "ISO-8859-1"); BufferedReader bufferedReader = new BufferedReader(inputStreamReader); String str1 = bufferedReader.readLine(); if (str1.indexOf("OpenSolaris") >= 0) { SunGraphicsEnvironment.isOpenSolaris = true; }
/*     */                 else
/*     */                 { String str2 = "/usr/openwin/lib/X11/fonts/TrueType/CourierNew.ttf"; File file1 = new File(str2); SunGraphicsEnvironment.isOpenSolaris = !file1.exists(); }
/*     */                  fileInputStream.close(); }
/*     */                }
/*     */             catch (Exception exception) {} SunGraphicsEnvironment.defaultFont = new Font("Dialog", 0, 12); return null; } }
/*     */       );
/* 315 */   } public static void useAlternateFontforJALocales() { getFontManagerForSGE().useAlternateFontforJALocales(); } public void addDisplayChangedListener(DisplayChangedListener paramDisplayChangedListener) { this.displayChanger.add(paramDisplayChangedListener); }
/*     */   public Font[] getAllFonts() { FontManagerForSGE fontManagerForSGE = getFontManagerForSGE(); Font[] arrayOfFont1 = fontManagerForSGE.getAllInstalledFonts(); Font[] arrayOfFont2 = fontManagerForSGE.getCreatedFonts();
/*     */     if (arrayOfFont2 == null || arrayOfFont2.length == 0)
/*     */       return arrayOfFont1; 
/*     */     int i = arrayOfFont1.length + arrayOfFont2.length;
/*     */     Font[] arrayOfFont3 = Arrays.<Font>copyOf(arrayOfFont1, i);
/*     */     System.arraycopy(arrayOfFont2, 0, arrayOfFont3, arrayOfFont1.length, arrayOfFont2.length);
/* 322 */     return arrayOfFont3; } public void removeDisplayChangedListener(DisplayChangedListener paramDisplayChangedListener) { this.displayChanger.remove(paramDisplayChangedListener); }
/*     */   public String[] getAvailableFontFamilyNames(Locale paramLocale) { FontManagerForSGE fontManagerForSGE = getFontManagerForSGE(); String[] arrayOfString1 = fontManagerForSGE.getInstalledFontFamilyNames(paramLocale); TreeMap<String, String> treeMap = fontManagerForSGE.getCreatedFontFamilyNames(); if (treeMap == null || treeMap.size() == 0)
/*     */       return arrayOfString1;  for (byte b1 = 0; b1 < arrayOfString1.length; b1++)
/*     */       treeMap.put(arrayOfString1[b1].toLowerCase(paramLocale), arrayOfString1[b1]);  String[] arrayOfString2 = new String[treeMap.size()]; Object[] arrayOfObject = treeMap.keySet().toArray(); for (byte b2 = 0; b2 < arrayOfObject.length; b2++)
/*     */       arrayOfString2[b2] = treeMap.get(arrayOfObject[b2]);  return arrayOfString2; }
/*     */   public String[] getAvailableFontFamilyNames() { return getAvailableFontFamilyNames(Locale.getDefault()); }
/*     */   public static Rectangle getUsableBounds(GraphicsDevice paramGraphicsDevice) { GraphicsConfiguration graphicsConfiguration = paramGraphicsDevice.getDefaultConfiguration(); Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(graphicsConfiguration);
/*     */     Rectangle rectangle = graphicsConfiguration.getBounds();
/*     */     rectangle.x += insets.left;
/*     */     rectangle.y += insets.top;
/*     */     rectangle.width -= insets.left + insets.right;
/*     */     rectangle.height -= insets.top + insets.bottom;
/*     */     return rectangle; }
/*     */   public void displayChanged() { for (GraphicsDevice graphicsDevice : getScreenDevices()) {
/*     */       if (graphicsDevice instanceof DisplayChangedListener)
/*     */         ((DisplayChangedListener)graphicsDevice).displayChanged(); 
/*     */     } 
/*     */     this.displayChanger.notifyListeners(); }
/* 340 */   public void paletteChanged() { this.displayChanger.notifyPaletteChanged(); } public boolean isFlipStrategyPreferred(ComponentPeer paramComponentPeer) { return false; }
/*     */ 
/*     */   
/*     */   protected abstract int getNumScreens();
/*     */   
/*     */   protected abstract GraphicsDevice makeScreenDevice(int paramInt);
/*     */   
/*     */   public abstract boolean isDisplayLocal();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/SunGraphicsEnvironment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */