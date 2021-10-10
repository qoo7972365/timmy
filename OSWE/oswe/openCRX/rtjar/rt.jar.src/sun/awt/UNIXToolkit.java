/*     */ package sun.awt;
/*     */ 
/*     */ import com.sun.java.swing.plaf.gtk.GTKConstants;
/*     */ import java.awt.Point;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ComponentColorModel;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.security.AccessController;
/*     */ import sun.java2d.opengl.OGLRenderQueue;
/*     */ import sun.security.action.GetIntegerAction;
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
/*     */ public abstract class UNIXToolkit
/*     */   extends SunToolkit
/*     */ {
/*  41 */   public static final Object GTK_LOCK = new Object();
/*     */   
/*  43 */   private static final int[] BAND_OFFSETS = new int[] { 0, 1, 2 }; private static final int DEFAULT_DATATRANSFER_TIMEOUT = 10000; private Boolean nativeGTKAvailable;
/*  44 */   private static final int[] BAND_OFFSETS_ALPHA = new int[] { 0, 1, 2, 3 };
/*     */   private Boolean nativeGTKLoaded;
/*     */   
/*     */   public enum GtkVersions
/*     */   {
/*  49 */     ANY(0),
/*  50 */     GTK2(2),
/*  51 */     GTK3(3);
/*     */     final int number;
/*     */     
/*     */     static class Constants
/*     */     {
/*     */       static final int GTK2_MAJOR_NUMBER = 2;
/*     */       static final int GTK3_MAJOR_NUMBER = 3;
/*     */     }
/*     */     
/*     */     GtkVersions(int param1Int1) {
/*  61 */       this.number = param1Int1;
/*     */     }
/*     */     
/*     */     public static GtkVersions getVersion(int param1Int) {
/*  65 */       switch (param1Int) {
/*     */         case 2:
/*  67 */           return GTK2;
/*     */         case 3:
/*  69 */           return GTK3;
/*     */       } 
/*  71 */       return ANY;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getNumber() {
/*  77 */       return this.number;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  83 */   private BufferedImage tmpImage = null; public static final String FONTCONFIGAAHINT = "fontconfig/Antialias";
/*     */   
/*     */   public static int getDatatransferTimeout() {
/*  86 */     Integer integer = AccessController.<Integer>doPrivileged(new GetIntegerAction("sun.awt.datatransfer.timeout"));
/*     */     
/*  88 */     if (integer == null || integer.intValue() <= 0) {
/*  89 */       return 10000;
/*     */     }
/*  91 */     return integer.intValue();
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
/*     */   public boolean isNativeGTKAvailable() {
/* 106 */     synchronized (GTK_LOCK) {
/* 107 */       if (this.nativeGTKLoaded != null)
/*     */       {
/*     */         
/* 110 */         return this.nativeGTKLoaded.booleanValue();
/*     */       }
/* 112 */       if (this.nativeGTKAvailable != null)
/*     */       {
/*     */         
/* 115 */         return this.nativeGTKAvailable.booleanValue();
/*     */       }
/*     */       
/* 118 */       boolean bool = check_gtk(getEnabledGtkVersion().getNumber());
/* 119 */       this.nativeGTKAvailable = Boolean.valueOf(bool);
/* 120 */       return bool;
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
/*     */   public boolean loadGTK() {
/* 134 */     synchronized (GTK_LOCK) {
/* 135 */       if (this.nativeGTKLoaded == null) {
/* 136 */         this.nativeGTKLoaded = Boolean.valueOf(load_gtk(getEnabledGtkVersion().getNumber(), 
/* 137 */               isGtkVerbose()));
/*     */       }
/*     */     } 
/* 140 */     return this.nativeGTKLoaded.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object lazilyLoadDesktopProperty(String paramString) {
/* 147 */     if (paramString.startsWith("gtk.icon.")) {
/* 148 */       return lazilyLoadGTKIcon(paramString);
/*     */     }
/* 150 */     return super.lazilyLoadDesktopProperty(paramString);
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
/*     */   protected Object lazilyLoadGTKIcon(String paramString) {
/* 163 */     Object object = this.desktopProperties.get(paramString);
/* 164 */     if (object != null) {
/* 165 */       return object;
/*     */     }
/*     */ 
/*     */     
/* 169 */     String[] arrayOfString = paramString.split("\\.");
/* 170 */     if (arrayOfString.length != 5) {
/* 171 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 175 */     int i = 0;
/*     */     try {
/* 177 */       i = Integer.parseInt(arrayOfString[3]);
/* 178 */     } catch (NumberFormatException numberFormatException) {
/* 179 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 183 */     GTKConstants.TextDirection textDirection = "ltr".equals(arrayOfString[4]) ? GTKConstants.TextDirection.LTR : GTKConstants.TextDirection.RTL;
/*     */ 
/*     */ 
/*     */     
/* 187 */     BufferedImage bufferedImage = getStockIcon(-1, arrayOfString[2], i, textDirection.ordinal(), (String)null);
/* 188 */     if (bufferedImage != null)
/*     */     {
/* 190 */       setDesktopProperty(paramString, bufferedImage);
/*     */     }
/* 192 */     return bufferedImage;
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
/*     */   public BufferedImage getGTKIcon(String paramString) {
/* 204 */     if (!loadGTK()) {
/* 205 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 209 */     synchronized (GTK_LOCK) {
/* 210 */       if (!load_gtk_icon(paramString)) {
/* 211 */         this.tmpImage = null;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 216 */     return this.tmpImage;
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
/*     */   public BufferedImage getStockIcon(int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2) {
/* 237 */     if (!loadGTK()) {
/* 238 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 242 */     synchronized (GTK_LOCK) {
/* 243 */       if (!load_stock_icon(paramInt1, paramString1, paramInt2, paramInt3, paramString2)) {
/* 244 */         this.tmpImage = null;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 249 */     return this.tmpImage;
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
/*     */   public void loadIconCallback(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean) {
/* 262 */     this.tmpImage = null;
/*     */ 
/*     */ 
/*     */     
/* 266 */     DataBufferByte dataBufferByte = new DataBufferByte(paramArrayOfbyte, paramInt3 * paramInt2);
/*     */     
/* 268 */     WritableRaster writableRaster = Raster.createInterleavedRaster(dataBufferByte, paramInt1, paramInt2, paramInt3, paramInt5, paramBoolean ? BAND_OFFSETS_ALPHA : BAND_OFFSETS, (Point)null);
/*     */ 
/*     */ 
/*     */     
/* 272 */     ComponentColorModel componentColorModel = new ComponentColorModel(ColorSpace.getInstance(1000), paramBoolean, false, 3, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     this.tmpImage = new BufferedImage(componentColorModel, writableRaster, false, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static native boolean check_gtk(int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   private static native boolean load_gtk(int paramInt, boolean paramBoolean);
/*     */ 
/*     */   
/*     */   private static native boolean unload_gtk();
/*     */ 
/*     */   
/*     */   public void sync() {
/* 293 */     nativeSync();
/*     */     
/* 295 */     OGLRenderQueue.sync();
/*     */   }
/*     */ 
/*     */   
/*     */   private native boolean load_gtk_icon(String paramString);
/*     */ 
/*     */   
/*     */   private native boolean load_stock_icon(int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2);
/*     */   
/*     */   private native void nativeSync();
/*     */   
/*     */   private static native int get_gtk_version();
/*     */   
/*     */   protected RenderingHints getDesktopAAHints() {
/* 309 */     Object object2, object1 = getDesktopProperty("gnome.Xft/Antialias");
/*     */     
/* 311 */     if (object1 == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 316 */       object1 = getDesktopProperty("fontconfig/Antialias");
/* 317 */       if (object1 != null) {
/* 318 */         return new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, object1);
/*     */       }
/* 320 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     boolean bool = (object1 instanceof Number && ((Number)object1).intValue() != 0) ? true : false;
/*     */     
/* 332 */     if (bool) {
/*     */       
/* 334 */       String str = (String)getDesktopProperty("gnome.Xft/RGBA");
/*     */       
/* 336 */       if (str == null || str.equals("none")) {
/* 337 */         object2 = RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
/* 338 */       } else if (str.equals("rgb")) {
/* 339 */         object2 = RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB;
/* 340 */       } else if (str.equals("bgr")) {
/* 341 */         object2 = RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR;
/* 342 */       } else if (str.equals("vrgb")) {
/* 343 */         object2 = RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB;
/* 344 */       } else if (str.equals("vbgr")) {
/* 345 */         object2 = RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VBGR;
/*     */       } else {
/*     */         
/* 348 */         object2 = RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
/*     */       } 
/*     */     } else {
/* 351 */       object2 = RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT;
/*     */     } 
/* 353 */     return new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, object2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native boolean gtkCheckVersionImpl(int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkGtkVersion(int paramInt1, int paramInt2, int paramInt3) {
/* 373 */     if (loadGTK()) {
/* 374 */       return gtkCheckVersionImpl(paramInt1, paramInt2, paramInt3);
/*     */     }
/* 376 */     return false;
/*     */   }
/*     */   
/*     */   public static GtkVersions getEnabledGtkVersion() {
/* 380 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("jdk.gtk.version"));
/*     */     
/* 382 */     if (str == null)
/* 383 */       return GtkVersions.ANY; 
/* 384 */     if (str.startsWith("2"))
/* 385 */       return GtkVersions.GTK2; 
/* 386 */     if ("3".equals(str)) {
/* 387 */       return GtkVersions.GTK3;
/*     */     }
/* 389 */     return GtkVersions.ANY;
/*     */   }
/*     */   
/*     */   public static GtkVersions getGtkVersion() {
/* 393 */     return GtkVersions.getVersion(get_gtk_version());
/*     */   }
/*     */   
/*     */   public static boolean isGtkVerbose() {
/* 397 */     return ((Boolean)AccessController.<Boolean>doPrivileged(() -> Boolean.valueOf(Boolean.getBoolean("jdk.gtk.verbose")))).booleanValue();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/UNIXToolkit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */