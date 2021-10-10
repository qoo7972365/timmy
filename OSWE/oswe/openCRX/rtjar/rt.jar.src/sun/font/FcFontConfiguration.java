/*     */ package sun.font;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Properties;
/*     */ import java.util.Scanner;
/*     */ import sun.awt.FcFontManager;
/*     */ import sun.awt.FontConfiguration;
/*     */ import sun.awt.FontDescriptor;
/*     */ import sun.awt.SunToolkit;
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
/*     */ public class FcFontConfiguration
/*     */   extends FontConfiguration
/*     */ {
/*     */   private static final String fileVersion = "1";
/*  70 */   private String fcInfoFileName = null;
/*     */   
/*  72 */   private FontConfigManager.FcCompFont[] fcCompFonts = null;
/*     */   
/*     */   public FcFontConfiguration(SunFontManager paramSunFontManager) {
/*  75 */     super(paramSunFontManager);
/*  76 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FcFontConfiguration(SunFontManager paramSunFontManager, boolean paramBoolean1, boolean paramBoolean2) {
/*  83 */     super(paramSunFontManager, paramBoolean1, paramBoolean2);
/*  84 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean init() {
/*  89 */     if (this.fcCompFonts != null) {
/*  90 */       return true;
/*     */     }
/*     */     
/*  93 */     setFontConfiguration();
/*  94 */     readFcInfo();
/*  95 */     FcFontManager fcFontManager = (FcFontManager)this.fontManager;
/*  96 */     FontConfigManager fontConfigManager = fcFontManager.getFontConfigManager();
/*  97 */     if (this.fcCompFonts == null) {
/*  98 */       this.fcCompFonts = fontConfigManager.loadFontConfig();
/*  99 */       if (this.fcCompFonts != null) {
/*     */         try {
/* 101 */           writeFcInfo();
/* 102 */         } catch (Exception exception) {
/* 103 */           if (FontUtilities.debugFonts()) {
/* 104 */             warning("Exception writing fcInfo " + exception);
/*     */           }
/*     */         } 
/* 107 */       } else if (FontUtilities.debugFonts()) {
/* 108 */         warning("Failed to get info from libfontconfig");
/*     */       } 
/*     */     } else {
/* 111 */       fontConfigManager.populateFontConfig(this.fcCompFonts);
/*     */     } 
/*     */     
/* 114 */     if (this.fcCompFonts == null) {
/* 115 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 119 */     String str1 = System.getProperty("java.home");
/* 120 */     if (str1 == null) {
/* 121 */       throw new Error("java.home property not set");
/*     */     }
/* 123 */     String str2 = str1 + File.separator + "lib";
/* 124 */     getInstalledFallbackFonts(str2);
/*     */     
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFallbackFamilyName(String paramString1, String paramString2) {
/* 134 */     String str = getCompatibilityFamilyName(paramString1);
/* 135 */     if (str != null) {
/* 136 */       return str;
/*     */     }
/* 138 */     return paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getFaceNameFromComponentFontName(String paramString) {
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getFileNameFromComponentFontName(String paramString) {
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFileNameFromPlatformName(String paramString) {
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Charset getDefaultFontCharset(String paramString) {
/* 162 */     return Charset.forName("ISO8859_1");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getEncoding(String paramString1, String paramString2) {
/* 168 */     return "default";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initReorderMap() {
/* 173 */     this.reorderMap = new HashMap<>();
/*     */   }
/*     */ 
/*     */   
/*     */   protected FontDescriptor[] buildFontDescriptors(int paramInt1, int paramInt2) {
/* 178 */     CompositeFontDescriptor[] arrayOfCompositeFontDescriptor = get2DCompositeFontInfo();
/* 179 */     int i = paramInt1 * 4 + paramInt2;
/* 180 */     String[] arrayOfString = arrayOfCompositeFontDescriptor[i].getComponentFaceNames();
/* 181 */     FontDescriptor[] arrayOfFontDescriptor = new FontDescriptor[arrayOfString.length];
/* 182 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 183 */       arrayOfFontDescriptor[b] = new FontDescriptor(arrayOfString[b], StandardCharsets.ISO_8859_1.newEncoder(), new int[0]);
/*     */     }
/*     */     
/* 186 */     return arrayOfFontDescriptor;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumberCoreFonts() {
/* 191 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getPlatformFontNames() {
/* 196 */     HashSet<String> hashSet = new HashSet();
/* 197 */     FcFontManager fcFontManager = (FcFontManager)this.fontManager;
/* 198 */     FontConfigManager fontConfigManager = fcFontManager.getFontConfigManager();
/* 199 */     FontConfigManager.FcCompFont[] arrayOfFcCompFont = fontConfigManager.loadFontConfig();
/* 200 */     for (byte b = 0; b < arrayOfFcCompFont.length; b++) {
/* 201 */       for (byte b1 = 0; b1 < (arrayOfFcCompFont[b]).allFonts.length; b1++) {
/* 202 */         hashSet.add(((arrayOfFcCompFont[b]).allFonts[b1]).fontFile);
/*     */       }
/*     */     } 
/* 205 */     return hashSet.<String>toArray(new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getExtraFontPath() {
/* 210 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needToSearchForFile(String paramString) {
/* 215 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private FontConfigManager.FontConfigFont[] getFcFontList(FontConfigManager.FcCompFont[] paramArrayOfFcCompFont, String paramString, int paramInt) {
/* 221 */     if (paramString.equals("dialog")) {
/* 222 */       paramString = "sansserif";
/* 223 */     } else if (paramString.equals("dialoginput")) {
/* 224 */       paramString = "monospaced";
/*     */     } 
/* 226 */     for (byte b = 0; b < paramArrayOfFcCompFont.length; b++) {
/* 227 */       if (paramString.equals((paramArrayOfFcCompFont[b]).jdkName) && paramInt == (paramArrayOfFcCompFont[b]).style)
/*     */       {
/* 229 */         return (paramArrayOfFcCompFont[b]).allFonts;
/*     */       }
/*     */     } 
/* 232 */     return (paramArrayOfFcCompFont[0]).allFonts;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CompositeFontDescriptor[] get2DCompositeFontInfo() {
/* 238 */     FcFontManager fcFontManager = (FcFontManager)this.fontManager;
/* 239 */     FontConfigManager fontConfigManager = fcFontManager.getFontConfigManager();
/* 240 */     FontConfigManager.FcCompFont[] arrayOfFcCompFont = fontConfigManager.loadFontConfig();
/*     */     
/* 242 */     CompositeFontDescriptor[] arrayOfCompositeFontDescriptor = new CompositeFontDescriptor[20];
/*     */ 
/*     */     
/* 245 */     for (byte b = 0; b < 5; b++) {
/* 246 */       String str = publicFontNames[b];
/*     */       
/* 248 */       for (byte b1 = 0; b1 < 4; b1++) {
/*     */         
/* 250 */         String str1 = str + "." + styleNames[b1];
/*     */         
/* 252 */         FontConfigManager.FontConfigFont[] arrayOfFontConfigFont = getFcFontList(arrayOfFcCompFont, fontNames[b], b1);
/*     */ 
/*     */         
/* 255 */         int i = arrayOfFontConfigFont.length;
/*     */         
/* 257 */         if (installedFallbackFontFiles != null) {
/* 258 */           i += installedFallbackFontFiles.length;
/*     */         }
/*     */         
/* 261 */         String[] arrayOfString1 = new String[i];
/* 262 */         String[] arrayOfString2 = new String[i];
/*     */ 
/*     */         
/* 265 */         for (byte b2 = 0; b2 < arrayOfFontConfigFont.length; b2++) {
/* 266 */           arrayOfString1[b2] = (arrayOfFontConfigFont[b2]).fontFile;
/* 267 */           arrayOfString2[b2] = (arrayOfFontConfigFont[b2]).familyName;
/*     */         } 
/*     */         
/* 270 */         if (installedFallbackFontFiles != null) {
/* 271 */           System.arraycopy(installedFallbackFontFiles, 0, arrayOfString1, arrayOfFontConfigFont.length, installedFallbackFontFiles.length);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 276 */         arrayOfCompositeFontDescriptor[b * 4 + b1] = new CompositeFontDescriptor(str1, 1, arrayOfString2, arrayOfString1, null, null);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 285 */     return arrayOfCompositeFontDescriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getVersionString(File paramFile) {
/*     */     try {
/* 293 */       Scanner scanner = new Scanner(paramFile);
/* 294 */       return scanner.findInLine("(\\d)+((\\.)(\\d)+)*");
/*     */     }
/* 296 */     catch (Exception exception) {
/*     */       
/* 298 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setOsNameAndVersion() {
/* 307 */     super.setOsNameAndVersion();
/*     */     
/* 309 */     if (!osName.equals("Linux")) {
/*     */       return;
/*     */     }
/*     */     try {
/*     */       File file;
/* 314 */       if ((file = new File("/etc/lsb-release")).canRead()) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 319 */         Properties properties = new Properties();
/* 320 */         properties.load(new FileInputStream(file));
/* 321 */         osName = properties.getProperty("DISTRIB_ID");
/* 322 */         osVersion = properties.getProperty("DISTRIB_RELEASE");
/* 323 */       } else if ((file = new File("/etc/redhat-release")).canRead()) {
/* 324 */         osName = "RedHat";
/* 325 */         osVersion = getVersionString(file);
/* 326 */       } else if ((file = new File("/etc/SuSE-release")).canRead()) {
/* 327 */         osName = "SuSE";
/* 328 */         osVersion = getVersionString(file);
/* 329 */       } else if ((file = new File("/etc/turbolinux-release")).canRead()) {
/* 330 */         osName = "Turbo";
/* 331 */         osVersion = getVersionString(file);
/* 332 */       } else if ((file = new File("/etc/fedora-release")).canRead()) {
/* 333 */         osName = "Fedora";
/* 334 */         osVersion = getVersionString(file);
/*     */       } 
/* 336 */     } catch (Exception exception) {
/* 337 */       if (FontUtilities.debugFonts()) {
/* 338 */         warning("Exception identifying Linux distro.");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private File getFcInfoFile() {
/* 344 */     if (this.fcInfoFileName == null) {
/*     */       String str1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 352 */         str1 = InetAddress.getLocalHost().getHostName();
/* 353 */       } catch (UnknownHostException unknownHostException) {
/* 354 */         str1 = "localhost";
/*     */       } 
/* 356 */       String str2 = System.getProperty("user.home");
/* 357 */       String str3 = System.getProperty("java.version");
/* 358 */       String str4 = File.separator;
/* 359 */       String str5 = str2 + str4 + ".java" + str4 + "fonts" + str4 + str3;
/* 360 */       String str6 = SunToolkit.getStartupLocale().getLanguage();
/* 361 */       String str7 = "fcinfo-1-" + str1 + "-" + osName + "-" + osVersion + "-" + str6 + ".properties";
/*     */       
/* 363 */       this.fcInfoFileName = str5 + str4 + str7;
/*     */     } 
/* 365 */     return new File(this.fcInfoFileName);
/*     */   }
/*     */   
/*     */   private void writeFcInfo() {
/* 369 */     Properties properties = new Properties();
/* 370 */     properties.setProperty("version", "1");
/* 371 */     FcFontManager fcFontManager = (FcFontManager)this.fontManager;
/* 372 */     FontConfigManager fontConfigManager = fcFontManager.getFontConfigManager();
/* 373 */     FontConfigManager.FontConfigInfo fontConfigInfo = fontConfigManager.getFontConfigInfo();
/* 374 */     properties.setProperty("fcversion", Integer.toString(fontConfigInfo.fcVersion));
/* 375 */     if (fontConfigInfo.cacheDirs != null) {
/* 376 */       for (byte b1 = 0; b1 < fontConfigInfo.cacheDirs.length; b1++) {
/* 377 */         if (fontConfigInfo.cacheDirs[b1] != null) {
/* 378 */           properties.setProperty("cachedir." + b1, fontConfigInfo.cacheDirs[b1]);
/*     */         }
/*     */       } 
/*     */     }
/* 382 */     for (byte b = 0; b < this.fcCompFonts.length; b++) {
/* 383 */       FontConfigManager.FcCompFont fcCompFont = this.fcCompFonts[b];
/* 384 */       String str = fcCompFont.jdkName + "." + fcCompFont.style;
/* 385 */       properties.setProperty(str + ".length", 
/* 386 */           Integer.toString(fcCompFont.allFonts.length));
/* 387 */       for (byte b1 = 0; b1 < fcCompFont.allFonts.length; b1++) {
/* 388 */         properties.setProperty(str + "." + b1 + ".family", (fcCompFont.allFonts[b1]).familyName);
/*     */         
/* 390 */         properties.setProperty(str + "." + b1 + ".file", (fcCompFont.allFonts[b1]).fontFile);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 399 */       File file1 = getFcInfoFile();
/* 400 */       File file2 = file1.getParentFile();
/* 401 */       file2.mkdirs();
/* 402 */       File file3 = Files.createTempFile(file2.toPath(), "fcinfo", null, (FileAttribute<?>[])new FileAttribute[0]).toFile();
/* 403 */       FileOutputStream fileOutputStream = new FileOutputStream(file3);
/* 404 */       properties.store(fileOutputStream, "JDK Font Configuration Generated File: *Do Not Edit*");
/*     */       
/* 406 */       fileOutputStream.close();
/* 407 */       boolean bool = file3.renameTo(file1);
/* 408 */       if (!bool && FontUtilities.debugFonts()) {
/* 409 */         System.out.println("rename failed");
/* 410 */         warning("Failed renaming file to " + getFcInfoFile());
/*     */       } 
/* 412 */     } catch (Exception exception) {
/* 413 */       if (FontUtilities.debugFonts()) {
/* 414 */         warning("IOException writing to " + getFcInfoFile());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readFcInfo() {
/* 425 */     File file = getFcInfoFile();
/* 426 */     if (!file.exists()) {
/*     */       return;
/*     */     }
/* 429 */     Properties properties = new Properties();
/* 430 */     FcFontManager fcFontManager = (FcFontManager)this.fontManager;
/* 431 */     FontConfigManager fontConfigManager = fcFontManager.getFontConfigManager();
/*     */     try {
/* 433 */       FileInputStream fileInputStream = new FileInputStream(file);
/* 434 */       properties.load(fileInputStream);
/* 435 */       fileInputStream.close();
/* 436 */     } catch (IOException iOException) {
/* 437 */       if (FontUtilities.debugFonts()) {
/* 438 */         warning("IOException reading from " + file.toString());
/*     */       }
/*     */       return;
/*     */     } 
/* 442 */     String str1 = (String)properties.get("version");
/* 443 */     if (str1 == null || !str1.equals("1")) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 449 */     String str2 = (String)properties.get("fcversion");
/* 450 */     if (str2 != null) {
/*     */       
/*     */       try {
/* 453 */         int j = Integer.parseInt(str2);
/* 454 */         if (j != 0 && j != 
/* 455 */           FontConfigManager.getFontConfigVersion()) {
/*     */           return;
/*     */         }
/* 458 */       } catch (Exception exception) {
/* 459 */         if (FontUtilities.debugFonts()) {
/* 460 */           warning("Exception parsing version " + str2);
/*     */         }
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 469 */     long l = file.lastModified();
/* 470 */     byte b1 = 0;
/* 471 */     while (b1 < 4) {
/* 472 */       String str = (String)properties.get("cachedir." + b1);
/* 473 */       if (str == null) {
/*     */         break;
/*     */       }
/* 476 */       File file1 = new File(str);
/* 477 */       if (file1.exists() && file1.lastModified() > l) {
/*     */         return;
/*     */       }
/* 480 */       b1++;
/*     */     } 
/*     */     
/* 483 */     String[] arrayOfString1 = { "sansserif", "serif", "monospaced" };
/* 484 */     String[] arrayOfString2 = { "sans", "serif", "monospace" };
/* 485 */     int i = arrayOfString1.length;
/* 486 */     byte b2 = 4;
/* 487 */     FontConfigManager.FcCompFont[] arrayOfFcCompFont = new FontConfigManager.FcCompFont[i * b2];
/*     */     
/*     */     try {
/* 490 */       for (byte b = 0; b < i; b++) {
/* 491 */         for (byte b3 = 0; b3 < b2; b3++) {
/* 492 */           int j = b * b2 + b3;
/* 493 */           arrayOfFcCompFont[j] = new FontConfigManager.FcCompFont();
/* 494 */           String str3 = arrayOfString1[b] + "." + b3;
/* 495 */           (arrayOfFcCompFont[j]).jdkName = arrayOfString1[b];
/* 496 */           (arrayOfFcCompFont[j]).fcFamily = arrayOfString2[b];
/* 497 */           (arrayOfFcCompFont[j]).style = b3;
/* 498 */           String str4 = (String)properties.get(str3 + ".length");
/* 499 */           int k = Integer.parseInt(str4);
/* 500 */           if (k <= 0) {
/*     */             return;
/*     */           }
/* 503 */           (arrayOfFcCompFont[j]).allFonts = new FontConfigManager.FontConfigFont[k];
/* 504 */           for (byte b4 = 0; b4 < k; b4++) {
/* 505 */             (arrayOfFcCompFont[j]).allFonts[b4] = new FontConfigManager.FontConfigFont();
/* 506 */             String str5 = str3 + "." + b4 + ".family";
/* 507 */             String str6 = (String)properties.get(str5);
/* 508 */             ((arrayOfFcCompFont[j]).allFonts[b4]).familyName = str6;
/* 509 */             str5 = str3 + "." + b4 + ".file";
/* 510 */             String str7 = (String)properties.get(str5);
/* 511 */             if (str7 == null) {
/*     */               return;
/*     */             }
/* 514 */             ((arrayOfFcCompFont[j]).allFonts[b4]).fontFile = str7;
/*     */           } 
/* 516 */           (arrayOfFcCompFont[j]).firstFont = (arrayOfFcCompFont[j]).allFonts[0];
/*     */         } 
/*     */       } 
/*     */       
/* 520 */       this.fcCompFonts = arrayOfFcCompFont;
/* 521 */     } catch (Throwable throwable) {
/* 522 */       if (FontUtilities.debugFonts()) {
/* 523 */         warning(throwable.toString());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void warning(String paramString) {
/* 529 */     PlatformLogger platformLogger = PlatformLogger.getLogger("sun.awt.FontConfiguration");
/* 530 */     platformLogger.warning(paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/FcFontConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */