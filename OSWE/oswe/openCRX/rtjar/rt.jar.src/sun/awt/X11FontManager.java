/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.StreamTokenizer;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ import sun.awt.motif.MFontConfiguration;
/*     */ import sun.font.CompositeFont;
/*     */ import sun.font.FcFontConfiguration;
/*     */ import sun.font.FontAccess;
/*     */ import sun.font.FontUtilities;
/*     */ import sun.font.NativeFont;
/*     */ import sun.font.SunFontManager;
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
/*     */ 
/*     */ public final class X11FontManager
/*     */   extends FcFontManager
/*     */ {
/*     */   private static final int FOUNDRY_FIELD = 1;
/*     */   private static final int FAMILY_NAME_FIELD = 2;
/*     */   private static final int WEIGHT_NAME_FIELD = 3;
/*     */   private static final int SLANT_FIELD = 4;
/*     */   private static final int SETWIDTH_NAME_FIELD = 5;
/*     */   private static final int ADD_STYLE_NAME_FIELD = 6;
/*     */   private static final int PIXEL_SIZE_FIELD = 7;
/*     */   private static final int POINT_SIZE_FIELD = 8;
/*     */   private static final int RESOLUTION_X_FIELD = 9;
/*     */   private static final int RESOLUTION_Y_FIELD = 10;
/*     */   private static final int SPACING_FIELD = 11;
/*     */   private static final int AVERAGE_WIDTH_FIELD = 12;
/*     */   private static final int CHARSET_REGISTRY_FIELD = 13;
/*     */   private static final int CHARSET_ENCODING_FIELD = 14;
/*  87 */   private static Map fontNameMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   private static Map xlfdMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map xFontDirsMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   private static HashSet<String> fontConfigDirs = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   HashMap<String, String> oblmap = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   private static HashMap registeredDirs = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   private static String[] fontdirs = null;
/*     */   
/*     */   public static X11FontManager getInstance() {
/* 158 */     return (X11FontManager)SunFontManager.getInstance();
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
/*     */   public String getFileNameFromPlatformName(String paramString) {
/* 174 */     if (paramString.startsWith("/")) {
/* 175 */       return paramString;
/*     */     }
/*     */     
/* 178 */     String str1 = null;
/* 179 */     String str2 = specificFontIDForName(paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     str1 = super.getFileNameFromPlatformName(paramString);
/* 188 */     if (str1 != null) {
/* 189 */       if (isHeadless() && str1.startsWith("-"))
/*     */       {
/* 191 */         return null;
/*     */       }
/* 193 */       if (str1.startsWith("/")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 207 */         Vector<String> vector = (Vector)xlfdMap.get(str1);
/* 208 */         if (vector == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 214 */           if (getFontConfiguration().needToSearchForFile(str1)) {
/* 215 */             str1 = null;
/*     */           }
/* 217 */           if (str1 != null) {
/* 218 */             vector = new Vector();
/* 219 */             vector.add(paramString);
/* 220 */             xlfdMap.put(str1, vector);
/*     */           }
/*     */         
/* 223 */         } else if (!vector.contains(paramString)) {
/* 224 */           vector.add(paramString);
/*     */         } 
/*     */       } 
/*     */       
/* 228 */       if (str1 != null) {
/* 229 */         fontNameMap.put(str2, str1);
/* 230 */         return str1;
/*     */       } 
/*     */     } 
/*     */     
/* 234 */     if (str2 != null) {
/* 235 */       str1 = (String)fontNameMap.get(str2);
/*     */       
/* 237 */       if (str1 == null && FontUtilities.isLinux && !isOpenJDK()) {
/* 238 */         if (this.oblmap == null) {
/* 239 */           initObliqueLucidaFontMap();
/*     */         }
/* 241 */         String str = getObliqueLucidaFontID(str2);
/* 242 */         if (str != null) {
/* 243 */           str1 = this.oblmap.get(str);
/*     */         }
/*     */       } 
/* 246 */       if (this.fontPath == null && (str1 == null || 
/* 247 */         !str1.startsWith("/"))) {
/* 248 */         if (FontUtilities.debugFonts()) {
/* 249 */           FontUtilities.getLogger()
/* 250 */             .warning("** Registering all font paths because can't find file for " + paramString);
/*     */         }
/*     */         
/* 253 */         this.fontPath = getPlatformFontPath(noType1Font);
/* 254 */         registerFontDirs(this.fontPath);
/* 255 */         if (FontUtilities.debugFonts()) {
/* 256 */           FontUtilities.getLogger()
/* 257 */             .warning("** Finished registering all font paths");
/*     */         }
/* 259 */         str1 = (String)fontNameMap.get(str2);
/*     */       } 
/* 261 */       if (str1 == null && !isHeadless())
/*     */       {
/*     */ 
/*     */         
/* 265 */         str1 = getX11FontName(paramString);
/*     */       }
/* 267 */       if (str1 == null) {
/* 268 */         str2 = switchFontIDForName(paramString);
/* 269 */         str1 = (String)fontNameMap.get(str2);
/*     */       } 
/* 271 */       if (str1 != null) {
/* 272 */         fontNameMap.put(str2, str1);
/*     */       }
/*     */     } 
/* 275 */     return str1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String[] getNativeNames(String paramString1, String paramString2) {
/*     */     Vector vector;
/* 282 */     if ((vector = (Vector)xlfdMap.get(paramString1)) == null) {
/* 283 */       if (paramString2 == null) {
/* 284 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 289 */       String[] arrayOfString = new String[1];
/* 290 */       arrayOfString[0] = paramString2;
/* 291 */       return arrayOfString;
/*     */     } 
/*     */     
/* 294 */     int i = vector.size();
/* 295 */     return (String[])vector.toArray((Object[])new String[i]);
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
/*     */   protected void registerFontDir(String paramString) {
/* 313 */     if (FontUtilities.debugFonts()) {
/* 314 */       FontUtilities.getLogger().info("ParseFontDir " + paramString);
/*     */     }
/* 316 */     File file = new File(paramString + File.separator + "fonts.dir");
/* 317 */     FileReader fileReader = null;
/*     */     
/* 319 */     try { if (file.canRead()) {
/* 320 */         fileReader = new FileReader(file);
/* 321 */         BufferedReader bufferedReader = new BufferedReader(fileReader, 8192);
/* 322 */         StreamTokenizer streamTokenizer = new StreamTokenizer(bufferedReader);
/* 323 */         streamTokenizer.eolIsSignificant(true);
/* 324 */         int i = streamTokenizer.nextToken();
/* 325 */         if (i == -2) {
/* 326 */           int j = (int)streamTokenizer.nval;
/* 327 */           i = streamTokenizer.nextToken();
/* 328 */           if (i == 10) {
/* 329 */             streamTokenizer.resetSyntax();
/* 330 */             streamTokenizer.wordChars(32, 127);
/* 331 */             streamTokenizer.wordChars(160, 255);
/* 332 */             streamTokenizer.whitespaceChars(0, 31);
/*     */             
/* 334 */             for (byte b = 0; b < j; b++) {
/* 335 */               i = streamTokenizer.nextToken();
/* 336 */               if (i == -1) {
/*     */                 break;
/*     */               }
/* 339 */               if (i != -3) {
/*     */                 break;
/*     */               }
/* 342 */               int k = streamTokenizer.sval.indexOf(' ');
/* 343 */               if (k <= 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 354 */                 j++;
/* 355 */                 i = streamTokenizer.nextToken();
/* 356 */                 if (i != 10) {
/*     */                   break;
/*     */                 }
/*     */                 
/*     */                 continue;
/*     */               } 
/* 362 */               if (streamTokenizer.sval.charAt(0) == '!') {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 368 */                 j++;
/* 369 */                 i = streamTokenizer.nextToken();
/* 370 */                 if (i != 10) {
/*     */                   break;
/*     */                 }
/*     */                 continue;
/*     */               } 
/* 375 */               String str1 = streamTokenizer.sval.substring(0, k);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 381 */               int m = str1.lastIndexOf(':');
/* 382 */               if (m > 0) {
/* 383 */                 if (m + 1 >= str1.length()) {
/*     */                   continue;
/*     */                 }
/* 386 */                 str1 = str1.substring(m + 1);
/*     */               } 
/* 388 */               String str2 = streamTokenizer.sval.substring(k + 1);
/* 389 */               String str3 = specificFontIDForName(str2);
/* 390 */               String str4 = (String)fontNameMap.get(str3);
/*     */               
/* 392 */               if (FontUtilities.debugFonts()) {
/* 393 */                 PlatformLogger platformLogger = FontUtilities.getLogger();
/* 394 */                 platformLogger.info("file=" + str1 + " xlfd=" + str2);
/*     */                 
/* 396 */                 platformLogger.info("fontID=" + str3 + " sVal=" + str4);
/*     */               } 
/*     */               
/* 399 */               String str5 = null;
/*     */               try {
/* 401 */                 File file1 = new File(paramString, str1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 409 */                 if (xFontDirsMap == null) {
/* 410 */                   xFontDirsMap = new HashMap<>();
/*     */                 }
/* 412 */                 xFontDirsMap.put(str3, paramString);
/* 413 */                 str5 = file1.getCanonicalPath();
/* 414 */               } catch (IOException iOException) {
/* 415 */                 str5 = paramString + File.separator + str1;
/*     */               } 
/* 417 */               Vector<String> vector = (Vector)xlfdMap.get(str5);
/* 418 */               if (FontUtilities.debugFonts()) {
/* 419 */                 FontUtilities.getLogger()
/* 420 */                   .info("fullPath=" + str5 + " xVal=" + vector);
/*     */               }
/*     */               
/* 423 */               if (((vector == null || !vector.contains(str2)) && str4 == null) || 
/* 424 */                 !str4.startsWith("/")) {
/* 425 */                 if (FontUtilities.debugFonts()) {
/* 426 */                   FontUtilities.getLogger()
/* 427 */                     .info("Map fontID:" + str3 + "to file:" + str5);
/*     */                 }
/*     */                 
/* 430 */                 fontNameMap.put(str3, str5);
/* 431 */                 if (vector == null) {
/* 432 */                   vector = new Vector();
/* 433 */                   xlfdMap.put(str5, vector);
/*     */                 } 
/* 435 */                 vector.add(str2);
/*     */               } 
/*     */               
/* 438 */               i = streamTokenizer.nextToken();
/* 439 */               if (i != 10)
/*     */                 break; 
/*     */               continue;
/*     */             } 
/*     */           } 
/*     */         } 
/* 445 */         fileReader.close();
/*     */       }  }
/* 447 */     catch (IOException iOException) {  }
/*     */     finally
/* 449 */     { if (fileReader != null) {
/*     */         try {
/* 451 */           fileReader.close();
/* 452 */         } catch (IOException iOException) {}
/*     */       } }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadFonts() {
/* 460 */     super.loadFonts();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 470 */     xFontDirsMap = null;
/* 471 */     xlfdMap = new HashMap<>(1);
/* 472 */     fontNameMap = new HashMap<>(1);
/*     */   }
/*     */   
/*     */   private String getObliqueLucidaFontID(String paramString) {
/* 476 */     if (paramString.startsWith("-lucidasans-medium-i-normal") || paramString
/* 477 */       .startsWith("-lucidasans-bold-i-normal") || paramString
/* 478 */       .startsWith("-lucidatypewriter-medium-i-normal") || paramString
/* 479 */       .startsWith("-lucidatypewriter-bold-i-normal")) {
/* 480 */       return paramString.substring(0, paramString.indexOf("-i-"));
/*     */     }
/* 482 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getX11FontName(String paramString) {
/* 487 */     String str = paramString.replaceAll("%d", "*");
/* 488 */     if (NativeFont.fontExists(str)) {
/* 489 */       return str;
/*     */     }
/* 491 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initObliqueLucidaFontMap() {
/* 496 */     this.oblmap = new HashMap<>();
/* 497 */     this.oblmap.put("-lucidasans-medium", jreLibDirName + "/fonts/LucidaSansRegular.ttf");
/*     */     
/* 499 */     this.oblmap.put("-lucidasans-bold", jreLibDirName + "/fonts/LucidaSansDemiBold.ttf");
/*     */     
/* 501 */     this.oblmap.put("-lucidatypewriter-medium", jreLibDirName + "/fonts/LucidaTypewriterRegular.ttf");
/*     */     
/* 503 */     this.oblmap.put("-lucidatypewriter-bold", jreLibDirName + "/fonts/LucidaTypewriterBold.ttf");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isHeadless() {
/* 509 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 510 */     return GraphicsEnvironment.isHeadless();
/*     */   }
/*     */ 
/*     */   
/*     */   private String specificFontIDForName(String paramString) {
/* 515 */     int[] arrayOfInt = new int[14];
/* 516 */     byte b = 1;
/* 517 */     int i = 1;
/*     */     
/* 519 */     while (i != -1 && b < 14) {
/* 520 */       i = paramString.indexOf('-', i);
/* 521 */       if (i != -1) {
/* 522 */         arrayOfInt[b++] = i;
/* 523 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 527 */     if (b != 14) {
/* 528 */       if (FontUtilities.debugFonts()) {
/* 529 */         FontUtilities.getLogger()
/* 530 */           .severe("Font Configuration Font ID is malformed:" + paramString);
/*     */       }
/* 532 */       return paramString;
/*     */     } 
/*     */ 
/*     */     
/* 536 */     StringBuffer stringBuffer = new StringBuffer(paramString.substring(arrayOfInt[1], arrayOfInt[5]));
/*     */     
/* 538 */     stringBuffer.append(paramString.substring(arrayOfInt[12]));
/* 539 */     return stringBuffer.toString().toLowerCase(Locale.ENGLISH);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String switchFontIDForName(String paramString) {
/* 545 */     int[] arrayOfInt = new int[14];
/* 546 */     byte b = 1;
/* 547 */     int i = 1;
/*     */     
/* 549 */     while (i != -1 && b < 14) {
/* 550 */       i = paramString.indexOf('-', i);
/* 551 */       if (i != -1) {
/* 552 */         arrayOfInt[b++] = i;
/* 553 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 557 */     if (b != 14) {
/* 558 */       if (FontUtilities.debugFonts()) {
/* 559 */         FontUtilities.getLogger()
/* 560 */           .severe("Font Configuration Font ID is malformed:" + paramString);
/*     */       }
/* 562 */       return paramString;
/*     */     } 
/*     */     
/* 565 */     String str1 = paramString.substring(arrayOfInt[3] + 1, arrayOfInt[4]);
/*     */     
/* 567 */     String str2 = paramString.substring(arrayOfInt[1] + 1, arrayOfInt[2]);
/*     */     
/* 569 */     String str3 = paramString.substring(arrayOfInt[12] + 1, arrayOfInt[13]);
/*     */     
/* 571 */     String str4 = paramString.substring(arrayOfInt[13] + 1);
/*     */     
/* 573 */     if (str1.equals("i")) {
/* 574 */       str1 = "o";
/* 575 */     } else if (str1.equals("o")) {
/* 576 */       str1 = "i";
/*     */     } 
/*     */     
/* 579 */     if (str2.equals("itc zapfdingbats") && str3
/* 580 */       .equals("sun") && str4
/* 581 */       .equals("fontspecific")) {
/* 582 */       str3 = "adobe";
/*     */     }
/*     */     
/* 585 */     StringBuffer stringBuffer = new StringBuffer(paramString.substring(arrayOfInt[1], arrayOfInt[3] + 1));
/*     */     
/* 587 */     stringBuffer.append(str1);
/* 588 */     stringBuffer.append(paramString.substring(arrayOfInt[4], arrayOfInt[5] + 1));
/*     */     
/* 590 */     stringBuffer.append(str3);
/* 591 */     stringBuffer.append(paramString.substring(arrayOfInt[13]));
/* 592 */     return stringBuffer.toString().toLowerCase(Locale.ENGLISH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFileNameFromXLFD(String paramString) {
/* 600 */     String str1 = null;
/* 601 */     String str2 = specificFontIDForName(paramString);
/* 602 */     if (str2 != null) {
/* 603 */       str1 = (String)fontNameMap.get(str2);
/* 604 */       if (str1 == null) {
/* 605 */         str2 = switchFontIDForName(paramString);
/* 606 */         str1 = (String)fontNameMap.get(str2);
/*     */       } 
/* 608 */       if (str1 == null) {
/* 609 */         str1 = getDefaultFontFile();
/*     */       }
/*     */     } 
/* 612 */     return str1;
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
/*     */   protected void registerFontDirs(String paramString) {
/* 666 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, File.pathSeparator);
/*     */     
/*     */     try {
/* 669 */       while (stringTokenizer.hasMoreTokens()) {
/* 670 */         String str = stringTokenizer.nextToken();
/* 671 */         if (str != null && !registeredDirs.containsKey(str)) {
/* 672 */           registeredDirs.put(str, null);
/* 673 */           registerFontDir(str);
/*     */         } 
/*     */       } 
/* 676 */     } catch (NoSuchElementException noSuchElementException) {}
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
/*     */   protected void addFontToPlatformFontPath(String paramString) {
/* 706 */     getPlatformFontPathFromFontConfig();
/* 707 */     if (xFontDirsMap != null) {
/* 708 */       String str1 = specificFontIDForName(paramString);
/* 709 */       String str2 = (String)xFontDirsMap.get(str1);
/* 710 */       if (str2 != null) {
/* 711 */         fontConfigDirs.add(str2);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void getPlatformFontPathFromFontConfig() {
/* 718 */     if (fontConfigDirs == null) {
/* 719 */       fontConfigDirs = getFontConfiguration().getAWTFontPathSet();
/* 720 */       if (FontUtilities.debugFonts() && fontConfigDirs != null) {
/* 721 */         String[] arrayOfString = fontConfigDirs.<String>toArray(new String[0]);
/* 722 */         for (byte b = 0; b < arrayOfString.length; b++) {
/* 723 */           FontUtilities.getLogger().info("awtfontpath : " + arrayOfString[b]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerPlatformFontsUsedByFontConfiguration() {
/* 732 */     getPlatformFontPathFromFontConfig();
/* 733 */     if (fontConfigDirs == null) {
/*     */       return;
/*     */     }
/* 736 */     if (FontUtilities.isLinux) {
/* 737 */       fontConfigDirs.add(jreLibDirName + File.separator + "oblique-fonts");
/*     */     }
/* 739 */     fontdirs = fontConfigDirs.<String>toArray(new String[0]);
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
/*     */   protected FontConfiguration createFontConfiguration() {
/* 762 */     MFontConfiguration mFontConfiguration = new MFontConfiguration(this);
/* 763 */     if (FontUtilities.isOpenSolaris || (FontUtilities.isLinux && (
/*     */       
/* 765 */       !mFontConfiguration.foundOsSpecificFile() || 
/* 766 */       !mFontConfiguration.fontFilesArePresent())) || (FontUtilities.isSolaris && 
/* 767 */       !mFontConfiguration.fontFilesArePresent())) {
/* 768 */       FcFontConfiguration fcFontConfiguration = new FcFontConfiguration(this);
/*     */       
/* 770 */       if (fcFontConfiguration.init()) {
/* 771 */         return (FontConfiguration)fcFontConfiguration;
/*     */       }
/*     */     } 
/* 774 */     mFontConfiguration.init();
/* 775 */     return (FontConfiguration)mFontConfiguration;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FontConfiguration createFontConfiguration(boolean paramBoolean1, boolean paramBoolean2) {
/* 781 */     return (FontConfiguration)new MFontConfiguration(this, paramBoolean1, paramBoolean2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected synchronized String getFontPath(boolean paramBoolean) {
/* 786 */     isHeadless();
/* 787 */     return getFontPathNative(paramBoolean, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected FontUIResource getFontConfigFUIR(String paramString, int paramInt1, int paramInt2) {
/* 793 */     CompositeFont compositeFont = getFontConfigManager().getFontConfigFont(paramString, paramInt1);
/*     */     
/* 795 */     if (compositeFont == null) {
/* 796 */       return new FontUIResource(paramString, paramInt1, paramInt2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 807 */     FontUIResource fontUIResource = new FontUIResource(compositeFont.getFamilyName((Locale)null), paramInt1, paramInt2);
/* 808 */     FontAccess.getFontAccess().setFont2D(fontUIResource, compositeFont.handle);
/* 809 */     FontAccess.getFontAccess().setCreatedFont(fontUIResource);
/* 810 */     return fontUIResource;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11FontManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */