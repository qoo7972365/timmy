/*      */ package sun.awt;
/*      */ 
/*      */ import java.io.DataInputStream;
/*      */ import java.io.DataOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.charset.Charset;
/*      */ import java.nio.charset.CharsetEncoder;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import sun.font.CompositeFontDescriptor;
/*      */ import sun.font.FontUtilities;
/*      */ import sun.font.SunFontManager;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class FontConfiguration
/*      */ {
/*      */   protected static String osVersion;
/*      */   protected static String osName;
/*      */   protected static String encoding;
/*   66 */   protected static Locale startupLocale = null;
/*   67 */   protected static Hashtable localeMap = null;
/*      */   
/*      */   private static FontConfiguration fontConfig;
/*      */   private static PlatformLogger logger;
/*      */   protected static boolean isProperties = true;
/*      */   protected SunFontManager fontManager;
/*      */   protected boolean preferLocaleFonts;
/*      */   protected boolean preferPropFonts;
/*      */   private File fontConfigFile;
/*      */   private boolean foundOsSpecificFile;
/*      */   private boolean inited;
/*      */   private String javaLib;
/*      */   private static short stringIDNum;
/*      */   private static short[] stringIDs;
/*      */   private static StringBuilder stringTable;
/*      */   public static boolean verbose;
/*      */   
/*      */   public FontConfiguration(SunFontManager paramSunFontManager) {
/*   85 */     if (FontUtilities.debugFonts()) {
/*   86 */       FontUtilities.getLogger()
/*   87 */         .info("Creating standard Font Configuration");
/*      */     }
/*   89 */     if (FontUtilities.debugFonts() && logger == null) {
/*   90 */       logger = PlatformLogger.getLogger("sun.awt.FontConfiguration");
/*      */     }
/*   92 */     this.fontManager = paramSunFontManager;
/*   93 */     setOsNameAndVersion();
/*   94 */     setEncoding();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   99 */     findFontConfigFile();
/*      */   }
/*      */   
/*      */   public synchronized boolean init() {
/*  103 */     if (!this.inited) {
/*  104 */       this.preferLocaleFonts = false;
/*  105 */       this.preferPropFonts = false;
/*  106 */       setFontConfiguration();
/*  107 */       readFontConfigFile(this.fontConfigFile);
/*  108 */       initFontConfig();
/*  109 */       this.inited = true;
/*      */     } 
/*  111 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public FontConfiguration(SunFontManager paramSunFontManager, boolean paramBoolean1, boolean paramBoolean2) {
/*  117 */     this.fontManager = paramSunFontManager;
/*  118 */     if (FontUtilities.debugFonts()) {
/*  119 */       FontUtilities.getLogger()
/*  120 */         .info("Creating alternate Font Configuration");
/*      */     }
/*  122 */     this.preferLocaleFonts = paramBoolean1;
/*  123 */     this.preferPropFonts = paramBoolean2;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  128 */     initFontConfig();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setOsNameAndVersion() {
/*  137 */     osName = System.getProperty("os.name");
/*  138 */     osVersion = System.getProperty("os.version");
/*      */   }
/*      */   
/*      */   private void setEncoding() {
/*  142 */     encoding = Charset.defaultCharset().name();
/*  143 */     startupLocale = SunToolkit.getStartupLocale();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean foundOsSpecificFile() {
/*  151 */     return this.foundOsSpecificFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean fontFilesArePresent() {
/*  158 */     init();
/*  159 */     short s1 = this.compFontNameIDs[0][0][0];
/*  160 */     short s2 = getComponentFileID(s1);
/*  161 */     final String fileName = mapFileName(getComponentFileName(s2));
/*  162 */     Boolean bool = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*      */         {
/*      */           public Object run() {
/*      */             try {
/*  166 */               File file = new File(fileName);
/*  167 */               return Boolean.valueOf(file.exists());
/*      */             }
/*  169 */             catch (Exception exception) {
/*  170 */               return Boolean.valueOf(false);
/*      */             } 
/*      */           }
/*      */         });
/*  174 */     return bool.booleanValue();
/*      */   }
/*      */ 
/*      */   
/*      */   private void findFontConfigFile() {
/*  179 */     this.foundOsSpecificFile = true;
/*  180 */     String str1 = System.getProperty("java.home");
/*  181 */     if (str1 == null) {
/*  182 */       throw new Error("java.home property not set");
/*      */     }
/*  184 */     this.javaLib = str1 + File.separator + "lib";
/*  185 */     String str2 = System.getProperty("sun.awt.fontconfig");
/*  186 */     if (str2 != null) {
/*  187 */       this.fontConfigFile = new File(str2);
/*      */     } else {
/*  189 */       this.fontConfigFile = findFontConfigFile(this.javaLib);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readFontConfigFile(File paramFile) {
/*  199 */     getInstalledFallbackFonts(this.javaLib);
/*      */     
/*  201 */     if (paramFile != null) {
/*      */       try {
/*  203 */         FileInputStream fileInputStream = new FileInputStream(paramFile.getPath());
/*  204 */         if (isProperties) {
/*  205 */           loadProperties(fileInputStream);
/*      */         } else {
/*  207 */           loadBinary(fileInputStream);
/*      */         } 
/*  209 */         fileInputStream.close();
/*  210 */         if (FontUtilities.debugFonts()) {
/*  211 */           logger.config("Read logical font configuration from " + paramFile);
/*      */         }
/*  213 */       } catch (IOException iOException) {
/*  214 */         if (FontUtilities.debugFonts()) {
/*  215 */           logger.config("Failed to read logical font configuration from " + paramFile);
/*      */         }
/*      */       } 
/*      */     }
/*  219 */     String str = getVersion();
/*  220 */     if (!"1".equals(str) && FontUtilities.debugFonts()) {
/*  221 */       logger.config("Unsupported fontconfig version: " + str);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void getInstalledFallbackFonts(String paramString) {
/*  226 */     String str = paramString + File.separator + "fonts" + File.separator + "fallback";
/*      */ 
/*      */     
/*  229 */     File file = new File(str);
/*  230 */     if (file.exists() && file.isDirectory()) {
/*  231 */       String[] arrayOfString1 = file.list(this.fontManager.getTrueTypeFilter());
/*  232 */       String[] arrayOfString2 = file.list(this.fontManager.getType1Filter());
/*  233 */       byte b1 = (arrayOfString1 == null) ? 0 : arrayOfString1.length;
/*  234 */       byte b2 = (arrayOfString2 == null) ? 0 : arrayOfString2.length;
/*  235 */       int i = b1 + b2;
/*  236 */       if (b1 + b2 == 0) {
/*      */         return;
/*      */       }
/*  239 */       installedFallbackFontFiles = new String[i]; byte b3;
/*  240 */       for (b3 = 0; b3 < b1; b3++) {
/*  241 */         installedFallbackFontFiles[b3] = file + File.separator + arrayOfString1[b3];
/*      */       }
/*      */       
/*  244 */       for (b3 = 0; b3 < b2; b3++) {
/*  245 */         installedFallbackFontFiles[b3 + b1] = file + File.separator + arrayOfString2[b3];
/*      */       }
/*      */       
/*  248 */       this.fontManager.registerFontsInDir(str);
/*      */     } 
/*      */   }
/*      */   
/*      */   private File findImpl(String paramString) {
/*  253 */     File file = new File(paramString + ".properties");
/*  254 */     if (file.canRead()) {
/*  255 */       isProperties = true;
/*  256 */       return file;
/*      */     } 
/*  258 */     file = new File(paramString + ".bfc");
/*  259 */     if (file.canRead()) {
/*  260 */       isProperties = false;
/*  261 */       return file;
/*      */     } 
/*  263 */     return null;
/*      */   }
/*      */   
/*      */   private File findFontConfigFile(String paramString) {
/*  267 */     String str1 = paramString + File.separator + "fontconfig";
/*      */     
/*  269 */     String str2 = null;
/*  270 */     if (osVersion != null && osName != null) {
/*  271 */       File file1 = findImpl(str1 + "." + osName + "." + osVersion);
/*  272 */       if (file1 != null) {
/*  273 */         return file1;
/*      */       }
/*  275 */       int i = osVersion.indexOf(".");
/*  276 */       if (i != -1) {
/*  277 */         str2 = osVersion.substring(0, osVersion.indexOf("."));
/*  278 */         file1 = findImpl(str1 + "." + osName + "." + str2);
/*  279 */         if (file1 != null) {
/*  280 */           return file1;
/*      */         }
/*      */       } 
/*      */     } 
/*  284 */     if (osName != null) {
/*  285 */       File file1 = findImpl(str1 + "." + osName);
/*  286 */       if (file1 != null) {
/*  287 */         return file1;
/*      */       }
/*      */     } 
/*  290 */     if (osVersion != null) {
/*  291 */       File file1 = findImpl(str1 + "." + osVersion);
/*  292 */       if (file1 != null) {
/*  293 */         return file1;
/*      */       }
/*  295 */       if (str2 != null) {
/*  296 */         file1 = findImpl(str1 + "." + str2);
/*  297 */         if (file1 != null) {
/*  298 */           return file1;
/*      */         }
/*      */       } 
/*      */     } 
/*  302 */     this.foundOsSpecificFile = false;
/*      */     
/*  304 */     File file = findImpl(str1);
/*  305 */     if (file != null) {
/*  306 */       return file;
/*      */     }
/*  308 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void loadBinary(InputStream paramInputStream) throws IOException {
/*  315 */     DataInputStream dataInputStream = new DataInputStream(paramInputStream);
/*  316 */     head = readShortTable(dataInputStream, 20);
/*  317 */     int[] arrayOfInt = new int[14]; int i;
/*  318 */     for (i = 0; i < 14; i++) {
/*  319 */       arrayOfInt[i] = head[i + 1] - head[i];
/*      */     }
/*  321 */     table_scriptIDs = readShortTable(dataInputStream, arrayOfInt[0]);
/*  322 */     table_scriptFonts = readShortTable(dataInputStream, arrayOfInt[1]);
/*  323 */     table_elcIDs = readShortTable(dataInputStream, arrayOfInt[2]);
/*  324 */     table_sequences = readShortTable(dataInputStream, arrayOfInt[3]);
/*  325 */     table_fontfileNameIDs = readShortTable(dataInputStream, arrayOfInt[4]);
/*  326 */     table_componentFontNameIDs = readShortTable(dataInputStream, arrayOfInt[5]);
/*  327 */     table_filenames = readShortTable(dataInputStream, arrayOfInt[6]);
/*  328 */     table_awtfontpaths = readShortTable(dataInputStream, arrayOfInt[7]);
/*  329 */     table_exclusions = readShortTable(dataInputStream, arrayOfInt[8]);
/*  330 */     table_proportionals = readShortTable(dataInputStream, arrayOfInt[9]);
/*  331 */     table_scriptFontsMotif = readShortTable(dataInputStream, arrayOfInt[10]);
/*  332 */     table_alphabeticSuffix = readShortTable(dataInputStream, arrayOfInt[11]);
/*  333 */     table_stringIDs = readShortTable(dataInputStream, arrayOfInt[12]);
/*      */ 
/*      */     
/*  336 */     stringCache = new String[table_stringIDs.length + 1];
/*      */     
/*  338 */     i = arrayOfInt[13];
/*  339 */     byte[] arrayOfByte = new byte[i * 2];
/*  340 */     table_stringTable = new char[i];
/*  341 */     dataInputStream.read(arrayOfByte);
/*  342 */     byte b1 = 0, b2 = 0;
/*  343 */     while (b1 < i) {
/*  344 */       table_stringTable[b1++] = (char)(arrayOfByte[b2++] << 8 | arrayOfByte[b2++] & 0xFF);
/*      */     }
/*  346 */     if (verbose) {
/*  347 */       dump();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveBinary(OutputStream paramOutputStream) throws IOException {
/*  355 */     sanityCheck();
/*      */     
/*  357 */     DataOutputStream dataOutputStream = new DataOutputStream(paramOutputStream);
/*  358 */     writeShortTable(dataOutputStream, head);
/*  359 */     writeShortTable(dataOutputStream, table_scriptIDs);
/*  360 */     writeShortTable(dataOutputStream, table_scriptFonts);
/*  361 */     writeShortTable(dataOutputStream, table_elcIDs);
/*  362 */     writeShortTable(dataOutputStream, table_sequences);
/*  363 */     writeShortTable(dataOutputStream, table_fontfileNameIDs);
/*  364 */     writeShortTable(dataOutputStream, table_componentFontNameIDs);
/*  365 */     writeShortTable(dataOutputStream, table_filenames);
/*  366 */     writeShortTable(dataOutputStream, table_awtfontpaths);
/*  367 */     writeShortTable(dataOutputStream, table_exclusions);
/*  368 */     writeShortTable(dataOutputStream, table_proportionals);
/*  369 */     writeShortTable(dataOutputStream, table_scriptFontsMotif);
/*  370 */     writeShortTable(dataOutputStream, table_alphabeticSuffix);
/*  371 */     writeShortTable(dataOutputStream, table_stringIDs);
/*      */     
/*  373 */     dataOutputStream.writeChars(new String(table_stringTable));
/*  374 */     paramOutputStream.close();
/*  375 */     if (verbose) {
/*  376 */       dump();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void loadProperties(InputStream paramInputStream) throws IOException {
/*  388 */     stringIDNum = 1;
/*  389 */     stringIDs = new short[1000];
/*  390 */     stringTable = new StringBuilder(4096);
/*      */     
/*  392 */     if (verbose && logger == null) {
/*  393 */       logger = PlatformLogger.getLogger("sun.awt.FontConfiguration");
/*      */     }
/*  395 */     (new PropertiesHandler()).load(paramInputStream);
/*      */ 
/*      */     
/*  398 */     stringIDs = null;
/*  399 */     stringTable = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initFontConfig() {
/*  412 */     this.initLocale = startupLocale;
/*  413 */     this.initEncoding = encoding;
/*  414 */     if (this.preferLocaleFonts && !willReorderForStartupLocale()) {
/*  415 */       this.preferLocaleFonts = false;
/*      */     }
/*  417 */     this.initELC = getInitELC();
/*  418 */     initAllComponentFonts();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private short getInitELC() {
/*  426 */     if (this.initELC != -1) {
/*  427 */       return this.initELC;
/*      */     }
/*  429 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*  430 */     for (byte b1 = 0; b1 < table_elcIDs.length; b1++) {
/*  431 */       hashMap.put(getString(table_elcIDs[b1]), Integer.valueOf(b1));
/*      */     }
/*  433 */     String str1 = this.initLocale.getLanguage();
/*  434 */     String str2 = this.initLocale.getCountry();
/*      */     String str3;
/*  436 */     if (hashMap.containsKey(str3 = this.initEncoding + "." + str1 + "." + str2) || hashMap
/*  437 */       .containsKey(str3 = this.initEncoding + "." + str1) || hashMap
/*  438 */       .containsKey(str3 = this.initEncoding)) {
/*  439 */       this.initELC = ((Integer)hashMap.get(str3)).shortValue();
/*      */     } else {
/*  441 */       this.initELC = ((Integer)hashMap.get("NULL.NULL.NULL")).shortValue();
/*      */     } 
/*  443 */     byte b2 = 0;
/*  444 */     while (b2 < table_alphabeticSuffix.length) {
/*  445 */       if (this.initELC == table_alphabeticSuffix[b2]) {
/*  446 */         this.alphabeticSuffix = getString(table_alphabeticSuffix[b2 + 1]);
/*  447 */         return this.initELC;
/*      */       } 
/*  449 */       b2 += 2;
/*      */     } 
/*  451 */     return this.initELC;
/*      */   }
/*      */ 
/*      */   
/*  455 */   private short initELC = -1;
/*      */   
/*      */   private Locale initLocale;
/*      */   private String initEncoding;
/*      */   private String alphabeticSuffix;
/*  460 */   private short[][][] compFontNameIDs = new short[5][4][];
/*  461 */   private int[][][] compExclusions = new int[5][][];
/*  462 */   private int[] compCoreNum = new int[5];
/*      */   
/*  464 */   private Set<Short> coreFontNameIDs = new HashSet<>();
/*  465 */   private Set<Short> fallbackFontNameIDs = new HashSet<>(); protected static final int NUM_FONTS = 5; protected static final int NUM_STYLES = 4;
/*      */   
/*      */   private void initAllComponentFonts() {
/*  468 */     short[] arrayOfShort = getFallbackScripts();
/*  469 */     for (byte b = 0; b < 5; b++) {
/*  470 */       short[] arrayOfShort1 = getCoreScripts(b);
/*  471 */       this.compCoreNum[b] = arrayOfShort1.length;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  479 */       int[][] arrayOfInt = new int[arrayOfShort1.length][]; byte b1;
/*  480 */       for (b1 = 0; b1 < arrayOfShort1.length; b1++) {
/*  481 */         arrayOfInt[b1] = getExclusionRanges(arrayOfShort1[b1]);
/*      */       }
/*  483 */       this.compExclusions[b] = arrayOfInt;
/*      */       
/*  485 */       for (b1 = 0; b1 < 4; b1++) {
/*      */         
/*  487 */         short[] arrayOfShort2 = new short[arrayOfShort1.length + arrayOfShort.length];
/*      */         byte b2;
/*  489 */         for (b2 = 0; b2 < arrayOfShort1.length; b2++) {
/*  490 */           arrayOfShort2[b2] = getComponentFontID(arrayOfShort1[b2], b, b1);
/*      */           
/*  492 */           if (this.preferLocaleFonts && localeMap != null && this.fontManager
/*  493 */             .usingAlternateFontforJALocales()) {
/*  494 */             arrayOfShort2[b2] = remapLocaleMap(b, b1, arrayOfShort1[b2], arrayOfShort2[b2]);
/*      */           }
/*      */           
/*  497 */           if (this.preferPropFonts) {
/*  498 */             arrayOfShort2[b2] = remapProportional(b, arrayOfShort2[b2]);
/*      */           }
/*      */           
/*  501 */           this.coreFontNameIDs.add(Short.valueOf(arrayOfShort2[b2]));
/*      */         } 
/*      */         
/*  504 */         for (byte b3 = 0; b3 < arrayOfShort.length; b3++) {
/*  505 */           short s = getComponentFontID(arrayOfShort[b3], b, b1);
/*      */           
/*  507 */           if (this.preferLocaleFonts && localeMap != null && this.fontManager
/*  508 */             .usingAlternateFontforJALocales()) {
/*  509 */             s = remapLocaleMap(b, b1, arrayOfShort[b3], s);
/*      */           }
/*  511 */           if (this.preferPropFonts) {
/*  512 */             s = remapProportional(b, s);
/*      */           }
/*  514 */           if (!contains(arrayOfShort2, s, b2)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  521 */             this.fallbackFontNameIDs.add(Short.valueOf(s));
/*  522 */             arrayOfShort2[b2++] = s;
/*      */           } 
/*  524 */         }  if (b2 < arrayOfShort2.length) {
/*  525 */           short[] arrayOfShort3 = new short[b2];
/*  526 */           System.arraycopy(arrayOfShort2, 0, arrayOfShort3, 0, b2);
/*  527 */           arrayOfShort2 = arrayOfShort3;
/*      */         } 
/*  529 */         this.compFontNameIDs[b][b1] = arrayOfShort2;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private short remapLocaleMap(int paramInt1, int paramInt2, short paramShort1, short paramShort2) {
/*  535 */     String str1 = getString(table_scriptIDs[paramShort1]);
/*      */     
/*  537 */     String str2 = (String)localeMap.get(str1);
/*  538 */     if (str2 == null) {
/*  539 */       String str3 = fontNames[paramInt1];
/*  540 */       String str4 = styleNames[paramInt2];
/*  541 */       str2 = (String)localeMap.get(str3 + "." + str4 + "." + str1);
/*      */     } 
/*  543 */     if (str2 == null) {
/*  544 */       return paramShort2;
/*      */     }
/*      */     
/*  547 */     for (byte b = 0; b < table_componentFontNameIDs.length; b++) {
/*  548 */       String str = getString(table_componentFontNameIDs[b]);
/*  549 */       if (str2.equalsIgnoreCase(str)) {
/*  550 */         paramShort2 = (short)b;
/*      */         break;
/*      */       } 
/*      */     } 
/*  554 */     return paramShort2;
/*      */   }
/*      */   
/*      */   public static boolean hasMonoToPropMap() {
/*  558 */     return (table_proportionals != null && table_proportionals.length != 0);
/*      */   }
/*      */   
/*      */   private short remapProportional(int paramInt, short paramShort) {
/*  562 */     if (this.preferPropFonts && table_proportionals.length != 0 && paramInt != 2 && paramInt != 4) {
/*      */ 
/*      */ 
/*      */       
/*  566 */       byte b = 0;
/*  567 */       while (b < table_proportionals.length) {
/*  568 */         if (table_proportionals[b] == paramShort) {
/*  569 */           return table_proportionals[b + 1];
/*      */         }
/*  571 */         b += 2;
/*      */       } 
/*      */     } 
/*  574 */     return paramShort;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  582 */   protected static final String[] fontNames = new String[] { "serif", "sansserif", "monospaced", "dialog", "dialoginput" };
/*      */   
/*  584 */   protected static final String[] publicFontNames = new String[] { "Serif", "SansSerif", "Monospaced", "Dialog", "DialogInput" };
/*      */ 
/*      */   
/*  587 */   protected static final String[] styleNames = new String[] { "plain", "bold", "italic", "bolditalic" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLogicalFontFamilyName(String paramString) {
/*  595 */     return isLogicalFontFamilyNameLC(paramString.toLowerCase(Locale.ENGLISH));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLogicalFontFamilyNameLC(String paramString) {
/*  603 */     for (byte b = 0; b < fontNames.length; b++) {
/*  604 */       if (paramString.equals(fontNames[b])) {
/*  605 */         return true;
/*      */       }
/*      */     } 
/*  608 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isLogicalFontStyleName(String paramString) {
/*  615 */     for (byte b = 0; b < styleNames.length; b++) {
/*  616 */       if (paramString.equals(styleNames[b])) {
/*  617 */         return true;
/*      */       }
/*      */     } 
/*  620 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLogicalFontFaceName(String paramString) {
/*  628 */     return isLogicalFontFaceNameLC(paramString.toLowerCase(Locale.ENGLISH));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLogicalFontFaceNameLC(String paramString) {
/*  636 */     int i = paramString.indexOf('.');
/*  637 */     if (i >= 0) {
/*  638 */       String str1 = paramString.substring(0, i);
/*  639 */       String str2 = paramString.substring(i + 1);
/*  640 */       return (isLogicalFontFamilyName(str1) && 
/*  641 */         isLogicalFontStyleName(str2));
/*      */     } 
/*  643 */     return isLogicalFontFamilyName(paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   protected static int getFontIndex(String paramString) {
/*  648 */     return getArrayIndex(fontNames, paramString);
/*      */   }
/*      */   
/*      */   protected static int getStyleIndex(String paramString) {
/*  652 */     return getArrayIndex(styleNames, paramString);
/*      */   }
/*      */   
/*      */   private static int getArrayIndex(String[] paramArrayOfString, String paramString) {
/*  656 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  657 */       if (paramString.equals(paramArrayOfString[b])) {
/*  658 */         return b;
/*      */       }
/*      */     } 
/*      */     assert false;
/*  662 */     return 0;
/*      */   }
/*      */   
/*      */   protected static int getStyleIndex(int paramInt) {
/*  666 */     switch (paramInt) {
/*      */       case 0:
/*  668 */         return 0;
/*      */       case 1:
/*  670 */         return 1;
/*      */       case 2:
/*  672 */         return 2;
/*      */       case 3:
/*  674 */         return 3;
/*      */     } 
/*  676 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected static String getFontName(int paramInt) {
/*  681 */     return fontNames[paramInt];
/*      */   }
/*      */   
/*      */   protected static String getStyleName(int paramInt) {
/*  685 */     return styleNames[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getLogicalFontFaceName(String paramString, int paramInt) {
/*  694 */     assert isLogicalFontFamilyName(paramString);
/*  695 */     return paramString.toLowerCase(Locale.ENGLISH) + "." + getStyleString(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getStyleString(int paramInt) {
/*  704 */     return getStyleName(getStyleIndex(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getCompatibilityFamilyName(String paramString) {
/*  721 */     paramString = paramString.toLowerCase(Locale.ENGLISH);
/*  722 */     if (paramString.equals("timesroman"))
/*  723 */       return "serif"; 
/*  724 */     if (paramString.equals("helvetica"))
/*  725 */       return "sansserif"; 
/*  726 */     if (paramString.equals("courier")) {
/*  727 */       return "monospaced";
/*      */     }
/*  729 */     return null;
/*      */   }
/*      */   
/*  732 */   protected static String[] installedFallbackFontFiles = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String mapFileName(String paramString) {
/*  739 */     return paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  749 */   protected HashMap reorderMap = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void shuffle(String[] paramArrayOfString, int paramInt1, int paramInt2) {
/*  758 */     if (paramInt2 >= paramInt1) {
/*      */       return;
/*      */     }
/*  761 */     String str = paramArrayOfString[paramInt1];
/*  762 */     for (int i = paramInt1; i > paramInt2; i--) {
/*  763 */       paramArrayOfString[i] = paramArrayOfString[i - 1];
/*      */     }
/*  765 */     paramArrayOfString[paramInt2] = str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean willReorderForStartupLocale() {
/*  773 */     return (getReorderSequence() != null);
/*      */   }
/*      */   
/*      */   private static Object getReorderSequence() {
/*  777 */     if (fontConfig.reorderMap == null) {
/*  778 */       fontConfig.initReorderMap();
/*      */     }
/*  780 */     HashMap hashMap = fontConfig.reorderMap;
/*      */ 
/*      */     
/*  783 */     String str1 = startupLocale.getLanguage();
/*  784 */     String str2 = startupLocale.getCountry();
/*  785 */     Object object = hashMap.get(encoding + "." + str1 + "." + str2);
/*  786 */     if (object == null) {
/*  787 */       object = hashMap.get(encoding + "." + str1);
/*      */     }
/*  789 */     if (object == null) {
/*  790 */       object = hashMap.get(encoding);
/*      */     }
/*  792 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void reorderSequenceForLocale(String[] paramArrayOfString) {
/*  800 */     Object object = getReorderSequence();
/*  801 */     if (object instanceof String) {
/*  802 */       for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  803 */         if (paramArrayOfString[b].equals(object)) {
/*  804 */           shuffle(paramArrayOfString, b, 0);
/*      */           return;
/*      */         } 
/*      */       } 
/*  808 */     } else if (object instanceof String[]) {
/*  809 */       String[] arrayOfString = (String[])object;
/*  810 */       for (byte b = 0; b < arrayOfString.length; b++) {
/*  811 */         for (byte b1 = 0; b1 < paramArrayOfString.length; b1++) {
/*  812 */           if (paramArrayOfString[b1].equals(arrayOfString[b])) {
/*  813 */             shuffle(paramArrayOfString, b1, b);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static Vector splitSequence(String paramString) {
/*  822 */     Vector<String> vector = new Vector();
/*  823 */     int i = 0;
/*      */     int j;
/*  825 */     while ((j = paramString.indexOf(',', i)) >= 0) {
/*  826 */       vector.add(paramString.substring(i, j));
/*  827 */       i = j + 1;
/*      */     } 
/*  829 */     if (paramString.length() > i) {
/*  830 */       vector.add(paramString.substring(i, paramString.length()));
/*      */     }
/*  832 */     return vector;
/*      */   }
/*      */   
/*      */   protected String[] split(String paramString) {
/*  836 */     Vector vector = splitSequence(paramString);
/*  837 */     return (String[])vector.toArray((Object[])new String[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  843 */   private Hashtable charsetRegistry = new Hashtable<>(5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FontDescriptor[] getFontDescriptors(String paramString, int paramInt) {
/*  852 */     assert isLogicalFontFamilyName(paramString);
/*  853 */     paramString = paramString.toLowerCase(Locale.ENGLISH);
/*  854 */     int i = getFontIndex(paramString);
/*  855 */     int j = getStyleIndex(paramInt);
/*  856 */     return getFontDescriptors(i, j);
/*      */   }
/*  858 */   private FontDescriptor[][][] fontDescriptors = new FontDescriptor[5][4][];
/*      */   HashMap<String, Boolean> existsMap;
/*      */   
/*      */   private FontDescriptor[] getFontDescriptors(int paramInt1, int paramInt2) {
/*  862 */     FontDescriptor[] arrayOfFontDescriptor = this.fontDescriptors[paramInt1][paramInt2];
/*  863 */     if (arrayOfFontDescriptor == null) {
/*  864 */       arrayOfFontDescriptor = buildFontDescriptors(paramInt1, paramInt2);
/*  865 */       this.fontDescriptors[paramInt1][paramInt2] = arrayOfFontDescriptor;
/*      */     } 
/*  867 */     return arrayOfFontDescriptor;
/*      */   }
/*      */   
/*      */   protected FontDescriptor[] buildFontDescriptors(int paramInt1, int paramInt2) {
/*  871 */     String str1 = fontNames[paramInt1];
/*  872 */     String str2 = styleNames[paramInt2];
/*      */     
/*  874 */     short[] arrayOfShort1 = getCoreScripts(paramInt1);
/*  875 */     short[] arrayOfShort2 = this.compFontNameIDs[paramInt1][paramInt2];
/*  876 */     String[] arrayOfString1 = new String[arrayOfShort1.length];
/*  877 */     String[] arrayOfString2 = new String[arrayOfShort1.length];
/*  878 */     for (byte b1 = 0; b1 < arrayOfString1.length; b1++) {
/*  879 */       arrayOfString2[b1] = getComponentFontName(arrayOfShort2[b1]);
/*  880 */       arrayOfString1[b1] = getScriptName(arrayOfShort1[b1]);
/*  881 */       if (this.alphabeticSuffix != null && "alphabetic".equals(arrayOfString1[b1])) {
/*  882 */         arrayOfString1[b1] = arrayOfString1[b1] + "/" + this.alphabeticSuffix;
/*      */       }
/*      */     } 
/*  885 */     int[][] arrayOfInt = this.compExclusions[paramInt1];
/*      */     
/*  887 */     FontDescriptor[] arrayOfFontDescriptor = new FontDescriptor[arrayOfString2.length];
/*      */     
/*  889 */     for (byte b2 = 0; b2 < arrayOfString2.length; b2++) {
/*      */ 
/*      */ 
/*      */       
/*  893 */       String str3 = makeAWTFontName(arrayOfString2[b2], arrayOfString1[b2]);
/*      */ 
/*      */       
/*  896 */       String str4 = getEncoding(arrayOfString2[b2], arrayOfString1[b2]);
/*  897 */       if (str4 == null) {
/*  898 */         str4 = "default";
/*      */       }
/*      */       
/*  901 */       CharsetEncoder charsetEncoder = getFontCharsetEncoder(str4.trim(), str3);
/*      */ 
/*      */       
/*  904 */       int[] arrayOfInt1 = arrayOfInt[b2];
/*      */ 
/*      */       
/*  907 */       arrayOfFontDescriptor[b2] = new FontDescriptor(str3, charsetEncoder, arrayOfInt1);
/*      */     } 
/*  909 */     return arrayOfFontDescriptor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String makeAWTFontName(String paramString1, String paramString2) {
/*  918 */     return paramString1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CharsetEncoder getFontCharsetEncoder(final String charsetName, String paramString2) {
/*  933 */     Charset charset = null;
/*  934 */     if (charsetName.equals("default")) {
/*  935 */       charset = (Charset)this.charsetRegistry.get(paramString2);
/*      */     } else {
/*  937 */       charset = (Charset)this.charsetRegistry.get(charsetName);
/*      */     } 
/*  939 */     if (charset != null) {
/*  940 */       return charset.newEncoder();
/*      */     }
/*      */     
/*  943 */     if (!charsetName.startsWith("sun.awt.") && !charsetName.equals("default")) {
/*  944 */       charset = Charset.forName(charsetName);
/*      */     } else {
/*  946 */       Class<Charset> clazz = (Class)AccessController.<Class<?>>doPrivileged(new PrivilegedAction<Class<?>>() {
/*      */             public Object run() {
/*      */               try {
/*  949 */                 return Class.forName(charsetName, true, 
/*  950 */                     ClassLoader.getSystemClassLoader());
/*  951 */               } catch (ClassNotFoundException classNotFoundException) {
/*      */                 
/*  953 */                 return null;
/*      */               } 
/*      */             }
/*      */           });
/*  957 */       if (clazz != null) {
/*      */         try {
/*  959 */           charset = clazz.newInstance();
/*  960 */         } catch (Exception exception) {}
/*      */       }
/*      */     } 
/*      */     
/*  964 */     if (charset == null) {
/*  965 */       charset = getDefaultFontCharset(paramString2);
/*      */     }
/*      */     
/*  968 */     if (charsetName.equals("default")) {
/*  969 */       this.charsetRegistry.put(paramString2, charset);
/*      */     } else {
/*  971 */       this.charsetRegistry.put(charsetName, charset);
/*      */     } 
/*  973 */     return charset.newEncoder();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HashSet<String> getAWTFontPathSet() {
/*  985 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompositeFontDescriptor[] get2DCompositeFontInfo() {
/*  999 */     CompositeFontDescriptor[] arrayOfCompositeFontDescriptor = new CompositeFontDescriptor[20];
/*      */     
/* 1001 */     String str1 = this.fontManager.getDefaultFontFile();
/* 1002 */     String str2 = this.fontManager.getDefaultFontFaceName();
/*      */     
/* 1004 */     for (byte b = 0; b < 5; b++) {
/* 1005 */       String str = publicFontNames[b];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1011 */       int[][] arrayOfInt = this.compExclusions[b];
/* 1012 */       int i = 0;
/* 1013 */       for (byte b1 = 0; b1 < arrayOfInt.length; b1++) {
/* 1014 */         i += (arrayOfInt[b1]).length;
/*      */       }
/* 1016 */       int[] arrayOfInt1 = new int[i];
/* 1017 */       int[] arrayOfInt2 = new int[arrayOfInt.length];
/* 1018 */       byte b2 = 0;
/* 1019 */       boolean bool = false; byte b3;
/* 1020 */       for (b3 = 0; b3 < arrayOfInt.length; b3++) {
/* 1021 */         int[] arrayOfInt3 = arrayOfInt[b3];
/* 1022 */         for (byte b4 = 0; b4 < arrayOfInt3.length; ) {
/* 1023 */           int j = arrayOfInt3[b4];
/* 1024 */           arrayOfInt1[b2++] = arrayOfInt3[b4++];
/* 1025 */           arrayOfInt1[b2++] = arrayOfInt3[b4++];
/*      */         } 
/* 1027 */         arrayOfInt2[b3] = b2;
/*      */       } 
/*      */       
/* 1030 */       for (b3 = 0; b3 < 4; b3++) {
/* 1031 */         int j = (this.compFontNameIDs[b][b3]).length;
/* 1032 */         boolean bool1 = false;
/*      */         
/* 1034 */         if (installedFallbackFontFiles != null) {
/* 1035 */           j += installedFallbackFontFiles.length;
/*      */         }
/* 1037 */         String str3 = str + "." + styleNames[b3];
/*      */ 
/*      */         
/* 1040 */         String[] arrayOfString1 = new String[j];
/* 1041 */         String[] arrayOfString2 = new String[j];
/*      */         
/*      */         byte b4;
/* 1044 */         for (b4 = 0; b4 < (this.compFontNameIDs[b][b3]).length; b4++) {
/* 1045 */           short s1 = this.compFontNameIDs[b][b3][b4];
/* 1046 */           short s2 = getComponentFileID(s1);
/* 1047 */           arrayOfString1[b4] = getFaceNameFromComponentFontName(getComponentFontName(s1));
/* 1048 */           arrayOfString2[b4] = mapFileName(getComponentFileName(s2));
/* 1049 */           if (arrayOfString2[b4] == null || 
/* 1050 */             needToSearchForFile(arrayOfString2[b4])) {
/* 1051 */             arrayOfString2[b4] = getFileNameFromComponentFontName(getComponentFontName(s1));
/*      */           }
/* 1053 */           if (!bool1 && str1
/* 1054 */             .equals(arrayOfString2[b4])) {
/* 1055 */             bool1 = true;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1064 */         if (!bool1) {
/* 1065 */           int k = 0;
/* 1066 */           if (installedFallbackFontFiles != null) {
/* 1067 */             k = installedFallbackFontFiles.length;
/*      */           }
/* 1069 */           if (b4 + k == j) {
/* 1070 */             String[] arrayOfString3 = new String[j + 1];
/* 1071 */             System.arraycopy(arrayOfString1, 0, arrayOfString3, 0, b4);
/* 1072 */             arrayOfString1 = arrayOfString3;
/* 1073 */             String[] arrayOfString4 = new String[j + 1];
/* 1074 */             System.arraycopy(arrayOfString2, 0, arrayOfString4, 0, b4);
/* 1075 */             arrayOfString2 = arrayOfString4;
/*      */           } 
/* 1077 */           arrayOfString1[b4] = str2;
/* 1078 */           arrayOfString2[b4] = str1;
/* 1079 */           b4++;
/*      */         } 
/*      */         
/* 1082 */         if (installedFallbackFontFiles != null) {
/* 1083 */           for (byte b5 = 0; b5 < installedFallbackFontFiles.length; b5++) {
/* 1084 */             arrayOfString1[b4] = null;
/* 1085 */             arrayOfString2[b4] = installedFallbackFontFiles[b5];
/* 1086 */             b4++;
/*      */           } 
/*      */         }
/*      */         
/* 1090 */         if (b4 < j) {
/* 1091 */           String[] arrayOfString3 = new String[b4];
/* 1092 */           System.arraycopy(arrayOfString1, 0, arrayOfString3, 0, b4);
/* 1093 */           arrayOfString1 = arrayOfString3;
/* 1094 */           String[] arrayOfString4 = new String[b4];
/* 1095 */           System.arraycopy(arrayOfString2, 0, arrayOfString4, 0, b4);
/* 1096 */           arrayOfString2 = arrayOfString4;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1101 */         int[] arrayOfInt3 = arrayOfInt2;
/* 1102 */         if (b4 != arrayOfInt3.length) {
/* 1103 */           int k = arrayOfInt2.length;
/* 1104 */           arrayOfInt3 = new int[b4];
/* 1105 */           System.arraycopy(arrayOfInt2, 0, arrayOfInt3, 0, k);
/*      */           
/* 1107 */           for (int m = k; m < b4; m++) {
/* 1108 */             arrayOfInt3[m] = arrayOfInt1.length;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1118 */         arrayOfCompositeFontDescriptor[b * 4 + b3] = new CompositeFontDescriptor(str3, this.compCoreNum[b], arrayOfString1, arrayOfString2, arrayOfInt1, arrayOfInt3);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1128 */     return arrayOfCompositeFontDescriptor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean needToSearchForFile(String paramString) {
/* 1149 */     if (!FontUtilities.isLinux)
/* 1150 */       return false; 
/* 1151 */     if (this.existsMap == null) {
/* 1152 */       this.existsMap = new HashMap<>();
/*      */     }
/* 1154 */     Boolean bool = this.existsMap.get(paramString);
/* 1155 */     if (bool == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1161 */       getNumberCoreFonts();
/* 1162 */       if (!this.coreFontFileNames.contains(paramString)) {
/* 1163 */         bool = Boolean.TRUE;
/*      */       } else {
/* 1165 */         bool = Boolean.valueOf((new File(paramString)).exists());
/* 1166 */         this.existsMap.put(paramString, bool);
/* 1167 */         if (FontUtilities.debugFonts() && bool == Boolean.FALSE)
/*      */         {
/* 1169 */           logger.warning("Couldn't locate font file " + paramString);
/*      */         }
/*      */       } 
/*      */     } 
/* 1173 */     return (bool == Boolean.FALSE);
/*      */   }
/*      */   
/* 1176 */   private int numCoreFonts = -1;
/* 1177 */   private String[] componentFonts = null;
/* 1178 */   HashMap<String, String> filenamesMap = new HashMap<>();
/* 1179 */   HashSet<String> coreFontFileNames = new HashSet<>(); private static final int HEAD_LENGTH = 20; private static final int INDEX_scriptIDs = 0; private static final int INDEX_scriptFonts = 1; private static final int INDEX_elcIDs = 2; private static final int INDEX_sequences = 3; private static final int INDEX_fontfileNameIDs = 4; private static final int INDEX_componentFontNameIDs = 5; private static final int INDEX_filenames = 6; private static final int INDEX_awtfontpaths = 7; private static final int INDEX_exclusions = 8; private static final int INDEX_proportionals = 9; private static final int INDEX_scriptFontsMotif = 10; private static final int INDEX_alphabeticSuffix = 11; private static final int INDEX_stringIDs = 12;
/*      */   private static final int INDEX_stringTable = 13;
/*      */   private static final int INDEX_TABLEEND = 14;
/*      */   private static final int INDEX_fallbackScripts = 15;
/*      */   private static final int INDEX_appendedfontpath = 16;
/*      */   
/*      */   public int getNumberCoreFonts() {
/* 1186 */     if (this.numCoreFonts == -1) {
/* 1187 */       this.numCoreFonts = this.coreFontNameIDs.size();
/* 1188 */       Short[] arrayOfShort1 = new Short[0];
/* 1189 */       Short[] arrayOfShort2 = this.coreFontNameIDs.<Short>toArray(arrayOfShort1);
/* 1190 */       Short[] arrayOfShort3 = this.fallbackFontNameIDs.<Short>toArray(arrayOfShort1);
/*      */       
/* 1192 */       byte b1 = 0;
/*      */       byte b2;
/* 1194 */       for (b2 = 0; b2 < arrayOfShort3.length; b2++) {
/* 1195 */         if (this.coreFontNameIDs.contains(arrayOfShort3[b2])) {
/* 1196 */           arrayOfShort3[b2] = null;
/*      */         } else {
/*      */           
/* 1199 */           b1++;
/*      */         } 
/* 1201 */       }  this.componentFonts = new String[this.numCoreFonts + b1];
/* 1202 */       Object object = null;
/* 1203 */       for (b2 = 0; b2 < arrayOfShort2.length; b2++) {
/* 1204 */         short s1 = arrayOfShort2[b2].shortValue();
/* 1205 */         short s2 = getComponentFileID(s1);
/* 1206 */         this.componentFonts[b2] = getComponentFontName(s1);
/* 1207 */         String str = getComponentFileName(s2);
/* 1208 */         if (str != null) {
/* 1209 */           this.coreFontFileNames.add(str);
/*      */         }
/* 1211 */         this.filenamesMap.put(this.componentFonts[b2], mapFileName(str));
/*      */       } 
/* 1213 */       for (byte b3 = 0; b3 < arrayOfShort3.length; b3++) {
/* 1214 */         if (arrayOfShort3[b3] != null) {
/* 1215 */           short s1 = arrayOfShort3[b3].shortValue();
/* 1216 */           short s2 = getComponentFileID(s1);
/* 1217 */           this.componentFonts[b2] = getComponentFontName(s1);
/* 1218 */           this.filenamesMap.put(this.componentFonts[b2], 
/* 1219 */               mapFileName(getComponentFileName(s2)));
/* 1220 */           b2++;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1224 */     return this.numCoreFonts;
/*      */   }
/*      */   private static final int INDEX_version = 17; private static short[] head; private static short[] table_scriptIDs; private static short[] table_scriptFonts; private static short[] table_elcIDs; private static short[] table_sequences; private static short[] table_fontfileNameIDs; private static short[] table_componentFontNameIDs; private static short[] table_filenames; protected static short[] table_awtfontpaths; private static short[] table_exclusions; private static short[] table_proportionals; private static short[] table_scriptFontsMotif; private static short[] table_alphabeticSuffix; private static short[] table_stringIDs;
/*      */   private static char[] table_stringTable;
/*      */   private HashMap<String, Short> reorderScripts;
/*      */   private static String[] stringCache;
/*      */   
/*      */   public String[] getPlatformFontNames() {
/* 1232 */     if (this.numCoreFonts == -1) {
/* 1233 */       getNumberCoreFonts();
/*      */     }
/* 1235 */     return this.componentFonts;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFileNameFromPlatformName(String paramString) {
/* 1252 */     return this.filenamesMap.get(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getExtraFontPath() {
/* 1260 */     return getString(head[16]);
/*      */   }
/*      */   
/*      */   public String getVersion() {
/* 1264 */     return getString(head[17]);
/*      */   }
/*      */ 
/*      */   
/*      */   protected static FontConfiguration getFontConfiguration() {
/* 1269 */     return fontConfig;
/*      */   }
/*      */   
/*      */   protected void setFontConfiguration() {
/* 1273 */     fontConfig = this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void sanityCheck() {
/* 1376 */     byte b1 = 0;
/*      */ 
/*      */ 
/*      */     
/* 1380 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*      */         {
/*      */           public Object run() {
/* 1383 */             return System.getProperty("os.name");
/*      */           }
/*      */         });
/*      */     
/*      */     byte b2;
/* 1388 */     for (b2 = 1; b2 < table_filenames.length; b2++) {
/* 1389 */       if (table_filenames[b2] == -1)
/*      */       {
/*      */ 
/*      */         
/* 1393 */         if (str.contains("Windows")) {
/* 1394 */           System.err.println("\n Error: <filename." + 
/* 1395 */               getString(table_componentFontNameIDs[b2]) + "> entry is missing!!!");
/*      */           
/* 1397 */           b1++;
/*      */         }
/* 1399 */         else if (verbose && !isEmpty(table_filenames)) {
/* 1400 */           System.err.println("\n Note: 'filename' entry is undefined for \"" + 
/* 1401 */               getString(table_componentFontNameIDs[b2]) + "\"");
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1407 */     for (b2 = 0; b2 < table_scriptIDs.length; b2++) {
/* 1408 */       short s = table_scriptFonts[b2];
/* 1409 */       if (s == 0) {
/* 1410 */         System.out.println("\n Error: <allfonts." + 
/* 1411 */             getString(table_scriptIDs[b2]) + "> entry is missing!!!");
/*      */         
/* 1413 */         b1++;
/*      */       }
/* 1415 */       else if (s < 0) {
/* 1416 */         s = (short)-s;
/* 1417 */         for (byte b = 0; b < 5; b++) {
/* 1418 */           for (byte b3 = 0; b3 < 4; b3++) {
/* 1419 */             int i = b * 4 + b3;
/* 1420 */             short s1 = table_scriptFonts[s + i];
/* 1421 */             if (s1 == 0) {
/* 1422 */               System.err.println("\n Error: <" + 
/* 1423 */                   getFontName(b) + "." + 
/* 1424 */                   getStyleName(b3) + "." + 
/* 1425 */                   getString(table_scriptIDs[b2]) + "> entry is missing!!!");
/*      */               
/* 1427 */               b1++;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1433 */     if ("SunOS".equals(str))
/* 1434 */       for (b2 = 0; b2 < table_awtfontpaths.length; b2++) {
/* 1435 */         if (table_awtfontpaths[b2] == 0) {
/* 1436 */           String str1 = getString(table_scriptIDs[b2]);
/* 1437 */           if (!str1.contains("lucida") && 
/* 1438 */             !str1.contains("dingbats") && 
/* 1439 */             !str1.contains("symbol")) {
/*      */ 
/*      */             
/* 1442 */             System.err.println("\nError: <awtfontpath." + str1 + "> entry is missing!!!");
/*      */ 
/*      */ 
/*      */             
/* 1446 */             b1++;
/*      */           } 
/*      */         } 
/*      */       }  
/* 1450 */     if (b1 != 0) {
/* 1451 */       System.err.println("!!THERE ARE " + b1 + " ERROR(S) IN THE FONTCONFIG FILE, PLEASE CHECK ITS CONTENT!!\n");
/*      */       
/* 1453 */       System.exit(1);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static boolean isEmpty(short[] paramArrayOfshort) {
/* 1458 */     for (short s : paramArrayOfshort) {
/* 1459 */       if (s != -1) {
/* 1460 */         return false;
/*      */       }
/*      */     } 
/* 1463 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void dump() {
/* 1468 */     System.out.println("\n----Head Table------------"); byte b1;
/* 1469 */     for (b1 = 0; b1 < 20; b1++) {
/* 1470 */       System.out.println("  " + b1 + " : " + head[b1]);
/*      */     }
/* 1472 */     System.out.println("\n----scriptIDs-------------");
/* 1473 */     printTable(table_scriptIDs, 0);
/* 1474 */     System.out.println("\n----scriptFonts----------------");
/* 1475 */     for (b1 = 0; b1 < table_scriptIDs.length; b1++) {
/* 1476 */       short s = table_scriptFonts[b1];
/* 1477 */       if (s >= 0) {
/* 1478 */         System.out.println("  allfonts." + 
/* 1479 */             getString(table_scriptIDs[b1]) + "=" + 
/*      */             
/* 1481 */             getString(table_componentFontNameIDs[s]));
/*      */       }
/*      */     } 
/* 1484 */     for (b1 = 0; b1 < table_scriptIDs.length; b1++) {
/* 1485 */       short s = table_scriptFonts[b1];
/* 1486 */       if (s < 0) {
/* 1487 */         s = (short)-s;
/* 1488 */         for (byte b = 0; b < 5; b++) {
/* 1489 */           for (byte b3 = 0; b3 < 4; b3++) {
/* 1490 */             int i = b * 4 + b3;
/* 1491 */             short s1 = table_scriptFonts[s + i];
/* 1492 */             System.out.println("  " + 
/* 1493 */                 getFontName(b) + "." + 
/* 1494 */                 getStyleName(b3) + "." + 
/* 1495 */                 getString(table_scriptIDs[b1]) + "=" + 
/*      */                 
/* 1497 */                 getString(table_componentFontNameIDs[s1]));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1503 */     System.out.println("\n----elcIDs----------------");
/* 1504 */     printTable(table_elcIDs, 0);
/* 1505 */     System.out.println("\n----sequences-------------");
/* 1506 */     for (b1 = 0; b1 < table_elcIDs.length; b1++) {
/* 1507 */       System.out.println("  " + b1 + "/" + getString(table_elcIDs[b1]));
/* 1508 */       short[] arrayOfShort1 = getShortArray(table_sequences[b1 * 5 + 0]);
/* 1509 */       for (byte b = 0; b < arrayOfShort1.length; b++) {
/* 1510 */         System.out.println("     " + getString(table_scriptIDs[arrayOfShort1[b]]));
/*      */       }
/*      */     } 
/* 1513 */     System.out.println("\n----fontfileNameIDs-------");
/* 1514 */     printTable(table_fontfileNameIDs, 0);
/*      */     
/* 1516 */     System.out.println("\n----componentFontNameIDs--");
/* 1517 */     printTable(table_componentFontNameIDs, 1);
/* 1518 */     System.out.println("\n----filenames-------------");
/* 1519 */     for (b1 = 0; b1 < table_filenames.length; b1++) {
/* 1520 */       if (table_filenames[b1] == -1) {
/* 1521 */         System.out.println("  " + b1 + " : null");
/*      */       } else {
/* 1523 */         System.out.println("  " + b1 + " : " + 
/* 1524 */             getString(table_fontfileNameIDs[table_filenames[b1]]));
/*      */       } 
/*      */     } 
/* 1527 */     System.out.println("\n----awtfontpaths---------");
/* 1528 */     for (b1 = 0; b1 < table_awtfontpaths.length; b1++) {
/* 1529 */       System.out.println("  " + getString(table_scriptIDs[b1]) + " : " + 
/*      */           
/* 1531 */           getString(table_awtfontpaths[b1]));
/*      */     }
/* 1533 */     System.out.println("\n----proportionals--------");
/* 1534 */     for (b1 = 0; b1 < table_proportionals.length; b1++) {
/* 1535 */       System.out.println("  " + 
/* 1536 */           getString(table_componentFontNameIDs[table_proportionals[b1++]]) + " -> " + 
/*      */           
/* 1538 */           getString(table_componentFontNameIDs[table_proportionals[b1]]));
/*      */     }
/* 1540 */     b1 = 0;
/* 1541 */     System.out.println("\n----alphabeticSuffix----");
/* 1542 */     while (b1 < table_alphabeticSuffix.length) {
/* 1543 */       System.out.println("    " + getString(table_elcIDs[table_alphabeticSuffix[b1++]]) + " -> " + 
/* 1544 */           getString(table_alphabeticSuffix[b1++]));
/*      */     }
/* 1546 */     System.out.println("\n----String Table---------");
/* 1547 */     System.out.println("    stringID:    Num =" + table_stringIDs.length);
/* 1548 */     System.out.println("    stringTable: Size=" + (table_stringTable.length * 2));
/*      */     
/* 1550 */     System.out.println("\n----fallbackScriptIDs---");
/* 1551 */     short[] arrayOfShort = getShortArray(head[15]);
/* 1552 */     for (byte b2 = 0; b2 < arrayOfShort.length; b2++) {
/* 1553 */       System.out.println("  " + getString(table_scriptIDs[arrayOfShort[b2]]));
/*      */     }
/* 1555 */     System.out.println("\n----appendedfontpath-----");
/* 1556 */     System.out.println("  " + getString(head[16]));
/* 1557 */     System.out.println("\n----Version--------------");
/* 1558 */     System.out.println("  " + getString(head[17]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static short getComponentFontID(short paramShort, int paramInt1, int paramInt2) {
/* 1571 */     short s = table_scriptFonts[paramShort];
/*      */     
/* 1573 */     if (s >= 0)
/*      */     {
/* 1575 */       return s;
/*      */     }
/* 1577 */     return table_scriptFonts[-s + paramInt1 * 4 + paramInt2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static short getComponentFontIDMotif(short paramShort, int paramInt1, int paramInt2) {
/* 1585 */     if (table_scriptFontsMotif.length == 0) {
/* 1586 */       return 0;
/*      */     }
/* 1588 */     short s = table_scriptFontsMotif[paramShort];
/* 1589 */     if (s >= 0)
/*      */     {
/* 1591 */       return s;
/*      */     }
/* 1593 */     return table_scriptFontsMotif[-s + paramInt1 * 4 + paramInt2];
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] getExclusionRanges(short paramShort) {
/* 1598 */     short s = table_exclusions[paramShort];
/* 1599 */     if (s == 0) {
/* 1600 */       return EMPTY_INT_ARRAY;
/*      */     }
/* 1602 */     char[] arrayOfChar = getString(s).toCharArray();
/* 1603 */     int[] arrayOfInt = new int[arrayOfChar.length / 2];
/* 1604 */     byte b1 = 0;
/* 1605 */     for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
/* 1606 */       arrayOfInt[b2] = (arrayOfChar[b1++] << 16) + (arrayOfChar[b1++] & Character.MAX_VALUE);
/*      */     }
/* 1608 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean contains(short[] paramArrayOfshort, short paramShort, int paramInt) {
/* 1613 */     for (byte b = 0; b < paramInt; b++) {
/* 1614 */       if (paramArrayOfshort[b] == paramShort) {
/* 1615 */         return true;
/*      */       }
/*      */     } 
/* 1618 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected static String getComponentFontName(short paramShort) {
/* 1623 */     if (paramShort < 0) {
/* 1624 */       return null;
/*      */     }
/* 1626 */     return getString(table_componentFontNameIDs[paramShort]);
/*      */   }
/*      */   
/*      */   private static String getComponentFileName(short paramShort) {
/* 1630 */     if (paramShort < 0) {
/* 1631 */       return null;
/*      */     }
/* 1633 */     return getString(table_fontfileNameIDs[paramShort]);
/*      */   }
/*      */ 
/*      */   
/*      */   private static short getComponentFileID(short paramShort) {
/* 1638 */     return table_filenames[paramShort];
/*      */   }
/*      */   
/*      */   private static String getScriptName(short paramShort) {
/* 1642 */     return getString(table_scriptIDs[paramShort]);
/*      */   }
/*      */ 
/*      */   
/*      */   protected short[] getCoreScripts(int paramInt) {
/* 1647 */     short s = getInitELC();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1655 */     short[] arrayOfShort = getShortArray(table_sequences[s * 5 + paramInt]);
/* 1656 */     if (this.preferLocaleFonts) {
/* 1657 */       if (this.reorderScripts == null) {
/* 1658 */         this.reorderScripts = new HashMap<>();
/*      */       }
/* 1660 */       String[] arrayOfString = new String[arrayOfShort.length]; byte b;
/* 1661 */       for (b = 0; b < arrayOfString.length; b++) {
/* 1662 */         arrayOfString[b] = getScriptName(arrayOfShort[b]);
/* 1663 */         this.reorderScripts.put(arrayOfString[b], Short.valueOf(arrayOfShort[b]));
/*      */       } 
/* 1665 */       reorderSequenceForLocale(arrayOfString);
/* 1666 */       for (b = 0; b < arrayOfString.length; b++) {
/* 1667 */         arrayOfShort[b] = ((Short)this.reorderScripts.get(arrayOfString[b])).shortValue();
/*      */       }
/*      */     } 
/* 1670 */     return arrayOfShort;
/*      */   }
/*      */   
/*      */   private static short[] getFallbackScripts() {
/* 1674 */     return getShortArray(head[15]);
/*      */   }
/*      */   
/*      */   private static void printTable(short[] paramArrayOfshort, int paramInt) {
/* 1678 */     for (int i = paramInt; i < paramArrayOfshort.length; i++) {
/* 1679 */       System.out.println("  " + i + " : " + getString(paramArrayOfshort[i]));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static short[] readShortTable(DataInputStream paramDataInputStream, int paramInt) throws IOException {
/* 1685 */     if (paramInt == 0) {
/* 1686 */       return EMPTY_SHORT_ARRAY;
/*      */     }
/* 1688 */     short[] arrayOfShort = new short[paramInt];
/* 1689 */     byte[] arrayOfByte = new byte[paramInt * 2];
/* 1690 */     paramDataInputStream.read(arrayOfByte);
/* 1691 */     byte b1 = 0, b2 = 0;
/* 1692 */     while (b1 < paramInt) {
/* 1693 */       arrayOfShort[b1++] = (short)(arrayOfByte[b2++] << 8 | arrayOfByte[b2++] & 0xFF);
/*      */     }
/* 1695 */     return arrayOfShort;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void writeShortTable(DataOutputStream paramDataOutputStream, short[] paramArrayOfshort) throws IOException {
/* 1700 */     for (short s : paramArrayOfshort) {
/* 1701 */       paramDataOutputStream.writeShort(s);
/*      */     }
/*      */   }
/*      */   
/*      */   private static short[] toList(HashMap<String, Short> paramHashMap) {
/* 1706 */     short[] arrayOfShort = new short[paramHashMap.size()];
/* 1707 */     Arrays.fill(arrayOfShort, (short)-1);
/* 1708 */     for (Map.Entry<String, Short> entry : paramHashMap.entrySet()) {
/* 1709 */       arrayOfShort[((Short)entry.getValue()).shortValue()] = getStringID((String)entry.getKey());
/*      */     }
/* 1711 */     return arrayOfShort;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String getString(short paramShort) {
/* 1717 */     if (paramShort == 0) {
/* 1718 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1726 */     if (stringCache[paramShort] == null) {
/* 1727 */       stringCache[paramShort] = new String(table_stringTable, table_stringIDs[paramShort], table_stringIDs[paramShort + 1] - table_stringIDs[paramShort]);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1732 */     return stringCache[paramShort];
/*      */   }
/*      */   
/*      */   private static short[] getShortArray(short paramShort) {
/* 1736 */     String str = getString(paramShort);
/* 1737 */     char[] arrayOfChar = str.toCharArray();
/* 1738 */     short[] arrayOfShort = new short[arrayOfChar.length];
/* 1739 */     for (byte b = 0; b < arrayOfChar.length; b++) {
/* 1740 */       arrayOfShort[b] = (short)(arrayOfChar[b] & Character.MAX_VALUE);
/*      */     }
/* 1742 */     return arrayOfShort;
/*      */   }
/*      */   
/*      */   private static short getStringID(String paramString) {
/* 1746 */     if (paramString == null) {
/* 1747 */       return 0;
/*      */     }
/* 1749 */     short s1 = (short)stringTable.length();
/* 1750 */     stringTable.append(paramString);
/* 1751 */     short s2 = (short)stringTable.length();
/*      */     
/* 1753 */     stringIDs[stringIDNum] = s1;
/* 1754 */     stringIDs[stringIDNum + 1] = s2;
/* 1755 */     stringIDNum = (short)(stringIDNum + 1);
/* 1756 */     if (stringIDNum + 1 >= stringIDs.length) {
/* 1757 */       short[] arrayOfShort = new short[stringIDNum + 1000];
/* 1758 */       System.arraycopy(stringIDs, 0, arrayOfShort, 0, stringIDNum);
/* 1759 */       stringIDs = arrayOfShort;
/*      */     } 
/* 1761 */     return (short)(stringIDNum - 1);
/*      */   }
/*      */   
/*      */   private static short getShortArrayID(short[] paramArrayOfshort) {
/* 1765 */     char[] arrayOfChar = new char[paramArrayOfshort.length];
/* 1766 */     for (byte b = 0; b < paramArrayOfshort.length; b++) {
/* 1767 */       arrayOfChar[b] = (char)paramArrayOfshort[b];
/*      */     }
/* 1769 */     String str = new String(arrayOfChar);
/* 1770 */     return getStringID(str);
/*      */   }
/*      */ 
/*      */   
/* 1774 */   private static final int[] EMPTY_INT_ARRAY = new int[0];
/* 1775 */   private static final String[] EMPTY_STRING_ARRAY = new String[0];
/* 1776 */   private static final short[] EMPTY_SHORT_ARRAY = new short[0]; private static final String UNDEFINED_COMPONENT_FONT = "unknown"; public abstract String getFallbackFamilyName(String paramString1, String paramString2);
/*      */   protected abstract void initReorderMap();
/*      */   protected abstract String getEncoding(String paramString1, String paramString2);
/*      */   protected abstract Charset getDefaultFontCharset(String paramString);
/*      */   protected abstract String getFaceNameFromComponentFontName(String paramString);
/*      */   protected abstract String getFileNameFromComponentFontName(String paramString);
/*      */   static class PropertiesHandler { private HashMap<String, Short> scriptIDs; private HashMap<String, Short> elcIDs; private HashMap<String, Short> componentFontNameIDs; private HashMap<String, Short> fontfileNameIDs; private HashMap<String, Integer> logicalFontIDs; private HashMap<String, Integer> fontStyleIDs; private HashMap<Short, Short> filenames; private HashMap<Short, short[]> sequences; private HashMap<Short, Short[]> scriptFonts;
/*      */     public void load(InputStream param1InputStream) throws IOException {
/* 1784 */       initLogicalNameStyle();
/* 1785 */       initHashMaps();
/* 1786 */       FontProperties fontProperties = new FontProperties();
/* 1787 */       fontProperties.load(param1InputStream);
/* 1788 */       initBinaryTable();
/*      */     }
/*      */     private HashMap<Short, Short> scriptAllfonts; private HashMap<Short, int[]> exclusions; private HashMap<Short, Short> awtfontpaths; private HashMap<Short, Short> proportionals; private HashMap<Short, Short> scriptAllfontsMotif; private HashMap<Short, Short[]> scriptFontsMotif; private HashMap<Short, Short> alphabeticSuffix; private short[] fallbackScriptIDs; private String version; private String appendedfontpath;
/*      */     
/*      */     private void initBinaryTable() {
/* 1793 */       FontConfiguration.head = new short[20];
/* 1794 */       FontConfiguration.head[0] = 20;
/*      */       
/* 1796 */       FontConfiguration.table_scriptIDs = FontConfiguration.toList(this.scriptIDs);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1803 */       FontConfiguration.head[1] = (short)(FontConfiguration.head[0] + FontConfiguration.table_scriptIDs.length);
/* 1804 */       int i = FontConfiguration.table_scriptIDs.length + this.scriptFonts.size() * 20;
/* 1805 */       FontConfiguration.table_scriptFonts = new short[i];
/*      */       
/* 1807 */       for (Map.Entry<Short, Short> entry : this.scriptAllfonts.entrySet()) {
/* 1808 */         FontConfiguration.table_scriptFonts[((Short)entry.getKey()).intValue()] = ((Short)entry.getValue()).shortValue();
/*      */       }
/* 1810 */       int j = FontConfiguration.table_scriptIDs.length;
/* 1811 */       for (Map.Entry<Short, Short> entry : this.scriptFonts.entrySet()) {
/* 1812 */         FontConfiguration.table_scriptFonts[((Short)entry.getKey()).intValue()] = (short)-j;
/* 1813 */         Short[] arrayOfShort = (Short[])entry.getValue();
/* 1814 */         for (byte b1 = 0; b1 < 20; b1++) {
/* 1815 */           if (arrayOfShort[b1] != null) {
/* 1816 */             FontConfiguration.table_scriptFonts[j++] = arrayOfShort[b1].shortValue();
/*      */           } else {
/* 1818 */             FontConfiguration.table_scriptFonts[j++] = 0;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1824 */       FontConfiguration.head[2] = (short)(FontConfiguration.head[1] + FontConfiguration.table_scriptFonts.length);
/* 1825 */       FontConfiguration.table_elcIDs = FontConfiguration.toList(this.elcIDs);
/*      */ 
/*      */       
/* 1828 */       FontConfiguration.head[3] = (short)(FontConfiguration.head[2] + FontConfiguration.table_elcIDs.length);
/* 1829 */       FontConfiguration.table_sequences = new short[this.elcIDs.size() * 5];
/* 1830 */       for (Map.Entry<Short, short> entry : this.sequences.entrySet()) {
/*      */         
/* 1832 */         int k = ((Short)entry.getKey()).intValue();
/* 1833 */         short[] arrayOfShort = (short[])entry.getValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1841 */         if (arrayOfShort.length == 1) {
/*      */           
/* 1843 */           for (byte b2 = 0; b2 < 5; b2++)
/* 1844 */             FontConfiguration.table_sequences[k * 5 + b2] = arrayOfShort[0]; 
/*      */           continue;
/*      */         } 
/* 1847 */         for (byte b1 = 0; b1 < 5; b1++) {
/* 1848 */           FontConfiguration.table_sequences[k * 5 + b1] = arrayOfShort[b1];
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1853 */       FontConfiguration.head[4] = (short)(FontConfiguration.head[3] + FontConfiguration.table_sequences.length);
/* 1854 */       FontConfiguration.table_fontfileNameIDs = FontConfiguration.toList(this.fontfileNameIDs);
/*      */ 
/*      */       
/* 1857 */       FontConfiguration.head[5] = (short)(FontConfiguration.head[4] + FontConfiguration.table_fontfileNameIDs.length);
/* 1858 */       FontConfiguration.table_componentFontNameIDs = FontConfiguration.toList(this.componentFontNameIDs);
/*      */ 
/*      */       
/* 1861 */       FontConfiguration.head[6] = (short)(FontConfiguration.head[5] + FontConfiguration.table_componentFontNameIDs.length);
/* 1862 */       FontConfiguration.table_filenames = new short[FontConfiguration.table_componentFontNameIDs.length];
/* 1863 */       Arrays.fill(FontConfiguration.table_filenames, (short)-1);
/*      */       
/* 1865 */       for (Map.Entry<Short, Short> entry : this.filenames.entrySet()) {
/* 1866 */         FontConfiguration.table_filenames[((Short)entry.getKey()).shortValue()] = ((Short)entry.getValue()).shortValue();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1871 */       FontConfiguration.head[7] = (short)(FontConfiguration.head[6] + FontConfiguration.table_filenames.length);
/* 1872 */       FontConfiguration.table_awtfontpaths = new short[FontConfiguration.table_scriptIDs.length];
/* 1873 */       for (Map.Entry<Short, Short> entry : this.awtfontpaths.entrySet()) {
/* 1874 */         FontConfiguration.table_awtfontpaths[((Short)entry.getKey()).shortValue()] = ((Short)entry.getValue()).shortValue();
/*      */       }
/*      */ 
/*      */       
/* 1878 */       FontConfiguration.head[8] = (short)(FontConfiguration.head[7] + FontConfiguration.table_awtfontpaths.length);
/* 1879 */       FontConfiguration.table_exclusions = new short[this.scriptIDs.size()];
/* 1880 */       for (Map.Entry<Short, int> entry : this.exclusions.entrySet()) {
/* 1881 */         int[] arrayOfInt = (int[])entry.getValue();
/* 1882 */         char[] arrayOfChar = new char[arrayOfInt.length * 2];
/* 1883 */         byte b1 = 0;
/* 1884 */         for (byte b2 = 0; b2 < arrayOfInt.length; b2++) {
/* 1885 */           arrayOfChar[b1++] = (char)(arrayOfInt[b2] >> 16);
/* 1886 */           arrayOfChar[b1++] = (char)(arrayOfInt[b2] & 0xFFFF);
/*      */         } 
/* 1888 */         FontConfiguration.table_exclusions[((Short)entry.getKey()).shortValue()] = FontConfiguration.getStringID(new String(arrayOfChar));
/*      */       } 
/*      */       
/* 1891 */       FontConfiguration.head[9] = (short)(FontConfiguration.head[8] + FontConfiguration.table_exclusions.length);
/* 1892 */       FontConfiguration.table_proportionals = new short[this.proportionals.size() * 2];
/* 1893 */       byte b = 0;
/* 1894 */       for (Map.Entry<Short, Short> entry : this.proportionals.entrySet()) {
/* 1895 */         FontConfiguration.table_proportionals[b++] = ((Short)entry.getKey()).shortValue();
/* 1896 */         FontConfiguration.table_proportionals[b++] = ((Short)entry.getValue()).shortValue();
/*      */       } 
/*      */ 
/*      */       
/* 1900 */       FontConfiguration.head[10] = (short)(FontConfiguration.head[9] + FontConfiguration.table_proportionals.length);
/* 1901 */       if (this.scriptAllfontsMotif.size() != 0 || this.scriptFontsMotif.size() != 0) {
/* 1902 */         i = FontConfiguration.table_scriptIDs.length + this.scriptFontsMotif.size() * 20;
/* 1903 */         FontConfiguration.table_scriptFontsMotif = new short[i];
/*      */         
/* 1905 */         for (Map.Entry<Short, Short> entry : this.scriptAllfontsMotif.entrySet()) {
/* 1906 */           FontConfiguration.table_scriptFontsMotif[((Short)entry.getKey()).intValue()] = ((Short)entry
/* 1907 */             .getValue()).shortValue();
/*      */         }
/* 1909 */         j = FontConfiguration.table_scriptIDs.length;
/* 1910 */         for (Map.Entry<Short, Short> entry : this.scriptFontsMotif.entrySet()) {
/* 1911 */           FontConfiguration.table_scriptFontsMotif[((Short)entry.getKey()).intValue()] = (short)-j;
/* 1912 */           Short[] arrayOfShort = (Short[])entry.getValue();
/* 1913 */           byte b1 = 0;
/* 1914 */           while (b1 < 20) {
/* 1915 */             if (arrayOfShort[b1] != null) {
/* 1916 */               FontConfiguration.table_scriptFontsMotif[j++] = arrayOfShort[b1].shortValue();
/*      */             } else {
/* 1918 */               FontConfiguration.table_scriptFontsMotif[j++] = 0;
/*      */             } 
/* 1920 */             b1++;
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1924 */         FontConfiguration.table_scriptFontsMotif = FontConfiguration.EMPTY_SHORT_ARRAY;
/*      */       } 
/*      */ 
/*      */       
/* 1928 */       FontConfiguration.head[11] = (short)(FontConfiguration.head[10] + FontConfiguration.table_scriptFontsMotif.length);
/* 1929 */       FontConfiguration.table_alphabeticSuffix = new short[this.alphabeticSuffix.size() * 2];
/* 1930 */       b = 0;
/* 1931 */       for (Map.Entry<Short, Short> entry : this.alphabeticSuffix.entrySet()) {
/* 1932 */         FontConfiguration.table_alphabeticSuffix[b++] = ((Short)entry.getKey()).shortValue();
/* 1933 */         FontConfiguration.table_alphabeticSuffix[b++] = ((Short)entry.getValue()).shortValue();
/*      */       } 
/*      */ 
/*      */       
/* 1937 */       FontConfiguration.head[15] = FontConfiguration.getShortArrayID(this.fallbackScriptIDs);
/*      */ 
/*      */       
/* 1940 */       FontConfiguration.head[16] = FontConfiguration.getStringID(this.appendedfontpath);
/*      */ 
/*      */       
/* 1943 */       FontConfiguration.head[17] = FontConfiguration.getStringID(this.version);
/*      */ 
/*      */       
/* 1946 */       FontConfiguration.head[12] = (short)(FontConfiguration.head[11] + FontConfiguration.table_alphabeticSuffix.length);
/* 1947 */       FontConfiguration.table_stringIDs = new short[FontConfiguration.stringIDNum + 1];
/* 1948 */       System.arraycopy(FontConfiguration.stringIDs, 0, FontConfiguration.table_stringIDs, 0, FontConfiguration.stringIDNum + 1);
/*      */ 
/*      */       
/* 1951 */       FontConfiguration.head[13] = (short)(FontConfiguration.head[12] + FontConfiguration.stringIDNum + 1);
/* 1952 */       FontConfiguration.table_stringTable = FontConfiguration.stringTable.toString().toCharArray();
/*      */       
/* 1954 */       FontConfiguration.head[14] = (short)(FontConfiguration.head[13] + FontConfiguration.stringTable.length());
/*      */ 
/*      */       
/* 1957 */       FontConfiguration.stringCache = new String[FontConfiguration.table_stringIDs.length];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void initLogicalNameStyle() {
/* 2010 */       this.logicalFontIDs = new HashMap<>();
/* 2011 */       this.fontStyleIDs = new HashMap<>();
/* 2012 */       this.logicalFontIDs.put("serif", Integer.valueOf(0));
/* 2013 */       this.logicalFontIDs.put("sansserif", Integer.valueOf(1));
/* 2014 */       this.logicalFontIDs.put("monospaced", Integer.valueOf(2));
/* 2015 */       this.logicalFontIDs.put("dialog", Integer.valueOf(3));
/* 2016 */       this.logicalFontIDs.put("dialoginput", Integer.valueOf(4));
/* 2017 */       this.fontStyleIDs.put("plain", Integer.valueOf(0));
/* 2018 */       this.fontStyleIDs.put("bold", Integer.valueOf(1));
/* 2019 */       this.fontStyleIDs.put("italic", Integer.valueOf(2));
/* 2020 */       this.fontStyleIDs.put("bolditalic", Integer.valueOf(3));
/*      */     }
/*      */     
/*      */     private void initHashMaps() {
/* 2024 */       this.scriptIDs = new HashMap<>();
/* 2025 */       this.elcIDs = new HashMap<>();
/* 2026 */       this.componentFontNameIDs = new HashMap<>();
/*      */ 
/*      */ 
/*      */       
/* 2030 */       this.componentFontNameIDs.put("", Short.valueOf((short)0));
/*      */       
/* 2032 */       this.fontfileNameIDs = new HashMap<>();
/* 2033 */       this.filenames = new HashMap<>();
/* 2034 */       this.sequences = (HashMap)new HashMap<>();
/* 2035 */       this.scriptFonts = (HashMap)new HashMap<>();
/* 2036 */       this.scriptAllfonts = new HashMap<>();
/* 2037 */       this.exclusions = (HashMap)new HashMap<>();
/* 2038 */       this.awtfontpaths = new HashMap<>();
/* 2039 */       this.proportionals = new HashMap<>();
/* 2040 */       this.scriptFontsMotif = (HashMap)new HashMap<>();
/* 2041 */       this.scriptAllfontsMotif = new HashMap<>();
/* 2042 */       this.alphabeticSuffix = new HashMap<>();
/* 2043 */       this.fallbackScriptIDs = FontConfiguration.EMPTY_SHORT_ARRAY;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int[] parseExclusions(String param1String1, String param1String2) {
/* 2051 */       if (param1String2 == null) {
/* 2052 */         return FontConfiguration.EMPTY_INT_ARRAY;
/*      */       }
/*      */       
/* 2055 */       byte b1 = 1;
/* 2056 */       int i = 0;
/* 2057 */       while ((i = param1String2.indexOf(',', i)) != -1) {
/* 2058 */         b1++;
/* 2059 */         i++;
/*      */       } 
/* 2061 */       int[] arrayOfInt = new int[b1 * 2];
/* 2062 */       i = 0;
/* 2063 */       int j = 0;
/* 2064 */       for (byte b2 = 0; b2 < b1 * 2; ) {
/*      */         
/* 2066 */         int k = 0, m = 0;
/*      */         try {
/* 2068 */           j = param1String2.indexOf('-', i);
/* 2069 */           String str1 = param1String2.substring(i, j);
/* 2070 */           i = j + 1;
/* 2071 */           j = param1String2.indexOf(',', i);
/* 2072 */           if (j == -1) {
/* 2073 */             j = param1String2.length();
/*      */           }
/* 2075 */           String str2 = param1String2.substring(i, j);
/* 2076 */           i = j + 1;
/* 2077 */           int n = str1.length();
/* 2078 */           int i1 = str2.length();
/* 2079 */           if ((n != 4 && n != 6) || (i1 != 4 && i1 != 6))
/*      */           {
/* 2081 */             throw new Exception();
/*      */           }
/* 2083 */           k = Integer.parseInt(str1, 16);
/* 2084 */           m = Integer.parseInt(str2, 16);
/* 2085 */           if (k > m) {
/* 2086 */             throw new Exception();
/*      */           }
/* 2088 */         } catch (Exception exception) {
/* 2089 */           if (FontUtilities.debugFonts() && FontConfiguration
/* 2090 */             .logger != null) {
/* 2091 */             FontConfiguration.logger.config("Failed parsing " + param1String1 + " property of font configuration.");
/*      */           }
/*      */ 
/*      */           
/* 2095 */           return FontConfiguration.EMPTY_INT_ARRAY;
/*      */         } 
/* 2097 */         arrayOfInt[b2++] = k;
/* 2098 */         arrayOfInt[b2++] = m;
/*      */       } 
/* 2100 */       return arrayOfInt;
/*      */     }
/*      */     
/*      */     private Short getID(HashMap<String, Short> param1HashMap, String param1String) {
/* 2104 */       Short short_ = param1HashMap.get(param1String);
/* 2105 */       if (short_ == null) {
/* 2106 */         param1HashMap.put(param1String, Short.valueOf((short)param1HashMap.size()));
/* 2107 */         return param1HashMap.get(param1String);
/*      */       } 
/* 2109 */       return short_;
/*      */     }
/*      */     
/*      */     class FontProperties extends Properties {
/*      */       public synchronized Object put(Object param2Object1, Object param2Object2) {
/* 2114 */         FontConfiguration.PropertiesHandler.this.parseProperty((String)param2Object1, (String)param2Object2);
/* 2115 */         return null;
/*      */       }
/*      */     }
/*      */     
/*      */     private void parseProperty(String param1String1, String param1String2) {
/* 2120 */       if (param1String1.startsWith("filename.")) {
/*      */ 
/*      */         
/* 2123 */         param1String1 = param1String1.substring(9);
/* 2124 */         if (!"MingLiU_HKSCS".equals(param1String1)) {
/* 2125 */           param1String1 = param1String1.replace('_', ' ');
/*      */         }
/* 2127 */         Short short_1 = getID(this.componentFontNameIDs, param1String1);
/* 2128 */         Short short_2 = getID(this.fontfileNameIDs, param1String2);
/*      */ 
/*      */         
/* 2131 */         this.filenames.put(short_1, short_2);
/* 2132 */       } else if (param1String1.startsWith("exclusion.")) {
/* 2133 */         param1String1 = param1String1.substring(10);
/* 2134 */         this.exclusions.put(getID(this.scriptIDs, param1String1), parseExclusions(param1String1, param1String2));
/* 2135 */       } else if (param1String1.startsWith("sequence.")) {
/* 2136 */         param1String1 = param1String1.substring(9);
/* 2137 */         boolean bool1 = false;
/* 2138 */         boolean bool2 = false;
/*      */ 
/*      */         
/* 2141 */         String[] arrayOfString = (String[])FontConfiguration.splitSequence(param1String2).toArray((Object[])FontConfiguration.EMPTY_STRING_ARRAY);
/* 2142 */         short[] arrayOfShort1 = new short[arrayOfString.length]; short s;
/* 2143 */         for (s = 0; s < arrayOfString.length; s++) {
/* 2144 */           if ("alphabetic/default".equals(arrayOfString[s])) {
/*      */             
/* 2146 */             arrayOfString[s] = "alphabetic";
/* 2147 */             bool1 = true;
/* 2148 */           } else if ("alphabetic/1252".equals(arrayOfString[s])) {
/*      */             
/* 2150 */             arrayOfString[s] = "alphabetic";
/* 2151 */             bool2 = true;
/*      */           } 
/* 2153 */           arrayOfShort1[s] = getID(this.scriptIDs, arrayOfString[s]).shortValue();
/*      */         } 
/*      */ 
/*      */         
/* 2157 */         s = FontConfiguration.getShortArrayID(arrayOfShort1);
/* 2158 */         Short short_ = null;
/* 2159 */         int i = param1String1.indexOf('.');
/* 2160 */         if (i == -1) {
/* 2161 */           if ("fallback".equals(param1String1)) {
/* 2162 */             this.fallbackScriptIDs = arrayOfShort1;
/*      */             return;
/*      */           } 
/* 2165 */           if ("allfonts".equals(param1String1)) {
/* 2166 */             short_ = getID(this.elcIDs, "NULL.NULL.NULL");
/*      */           } else {
/* 2168 */             if (FontConfiguration.logger != null) {
/* 2169 */               FontConfiguration.logger.config("Error sequence def: <sequence." + param1String1 + ">");
/*      */             }
/*      */             return;
/*      */           } 
/*      */         } else {
/* 2174 */           short_ = getID(this.elcIDs, param1String1.substring(i + 1));
/*      */           
/* 2176 */           param1String1 = param1String1.substring(0, i);
/*      */         } 
/* 2178 */         short[] arrayOfShort2 = null;
/* 2179 */         if ("allfonts".equals(param1String1)) {
/* 2180 */           arrayOfShort2 = new short[1];
/* 2181 */           arrayOfShort2[0] = s;
/*      */         } else {
/* 2183 */           arrayOfShort2 = this.sequences.get(short_);
/* 2184 */           if (arrayOfShort2 == null) {
/* 2185 */             arrayOfShort2 = new short[5];
/*      */           }
/* 2187 */           Integer integer = this.logicalFontIDs.get(param1String1);
/* 2188 */           if (integer == null) {
/* 2189 */             if (FontConfiguration.logger != null) {
/* 2190 */               FontConfiguration.logger.config("Unrecognizable logicfont name " + param1String1);
/*      */             }
/*      */             
/*      */             return;
/*      */           } 
/* 2195 */           arrayOfShort2[integer.intValue()] = s;
/*      */         } 
/* 2197 */         this.sequences.put(short_, arrayOfShort2);
/* 2198 */         if (bool1) {
/* 2199 */           this.alphabeticSuffix.put(short_, Short.valueOf(FontConfiguration.getStringID("default")));
/*      */         }
/* 2201 */         else if (bool2) {
/* 2202 */           this.alphabeticSuffix.put(short_, Short.valueOf(FontConfiguration.getStringID("1252")));
/*      */         } 
/* 2204 */       } else if (param1String1.startsWith("allfonts.")) {
/* 2205 */         param1String1 = param1String1.substring(9);
/* 2206 */         if (param1String1.endsWith(".motif")) {
/* 2207 */           param1String1 = param1String1.substring(0, param1String1.length() - 6);
/*      */           
/* 2209 */           this.scriptAllfontsMotif.put(getID(this.scriptIDs, param1String1), getID(this.componentFontNameIDs, param1String2));
/*      */         } else {
/* 2211 */           this.scriptAllfonts.put(getID(this.scriptIDs, param1String1), getID(this.componentFontNameIDs, param1String2));
/*      */         } 
/* 2213 */       } else if (param1String1.startsWith("awtfontpath.")) {
/* 2214 */         param1String1 = param1String1.substring(12);
/*      */         
/* 2216 */         this.awtfontpaths.put(getID(this.scriptIDs, param1String1), Short.valueOf(FontConfiguration.getStringID(param1String2)));
/* 2217 */       } else if ("version".equals(param1String1)) {
/* 2218 */         this.version = param1String2;
/* 2219 */       } else if ("appendedfontpath".equals(param1String1)) {
/* 2220 */         this.appendedfontpath = param1String2;
/* 2221 */       } else if (param1String1.startsWith("proportional.")) {
/* 2222 */         param1String1 = param1String1.substring(13).replace('_', ' ');
/*      */         
/* 2224 */         this.proportionals.put(getID(this.componentFontNameIDs, param1String1), 
/* 2225 */             getID(this.componentFontNameIDs, param1String2));
/*      */       } else {
/*      */         Short[] arrayOfShort;
/*      */         
/* 2229 */         boolean bool = false;
/*      */         
/* 2231 */         int i = param1String1.indexOf('.');
/* 2232 */         if (i == -1) {
/* 2233 */           if (FontConfiguration.logger != null) {
/* 2234 */             FontConfiguration.logger.config("Failed parsing " + param1String1 + " property of font configuration.");
/*      */           }
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/* 2240 */         int j = param1String1.indexOf('.', i + 1);
/* 2241 */         if (j == -1) {
/* 2242 */           if (FontConfiguration.logger != null) {
/* 2243 */             FontConfiguration.logger.config("Failed parsing " + param1String1 + " property of font configuration.");
/*      */           }
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/* 2249 */         if (param1String1.endsWith(".motif")) {
/* 2250 */           param1String1 = param1String1.substring(0, param1String1.length() - 6);
/* 2251 */           bool = true;
/*      */         } 
/*      */         
/* 2254 */         Integer integer1 = this.logicalFontIDs.get(param1String1.substring(0, i));
/* 2255 */         Integer integer2 = this.fontStyleIDs.get(param1String1.substring(i + 1, j));
/* 2256 */         Short short_ = getID(this.scriptIDs, param1String1.substring(j + 1));
/* 2257 */         if (integer1 == null || integer2 == null) {
/* 2258 */           if (FontConfiguration.logger != null) {
/* 2259 */             FontConfiguration.logger.config("unrecognizable logicfont name/style at " + param1String1);
/*      */           }
/*      */           
/*      */           return;
/*      */         } 
/* 2264 */         if (bool) {
/* 2265 */           arrayOfShort = this.scriptFontsMotif.get(short_);
/*      */         } else {
/* 2267 */           arrayOfShort = this.scriptFonts.get(short_);
/*      */         } 
/* 2269 */         if (arrayOfShort == null) {
/* 2270 */           arrayOfShort = new Short[20];
/*      */         }
/* 2272 */         arrayOfShort[integer1.intValue() * 4 + integer2.intValue()] = 
/* 2273 */           getID(this.componentFontNameIDs, param1String2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2279 */         if (bool) {
/* 2280 */           this.scriptFontsMotif.put(short_, arrayOfShort);
/*      */         } else {
/* 2282 */           this.scriptFonts.put(short_, arrayOfShort);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */ 
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/FontConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */