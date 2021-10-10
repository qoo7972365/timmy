/*     */ package sun.awt.motif;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Properties;
/*     */ import java.util.Scanner;
/*     */ import sun.awt.FontConfiguration;
/*     */ import sun.awt.X11FontManager;
/*     */ import sun.font.FontUtilities;
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
/*     */ public class MFontConfiguration
/*     */   extends FontConfiguration
/*     */ {
/*  44 */   private static FontConfiguration fontConfig = null;
/*     */   private static PlatformLogger logger;
/*     */   
/*     */   public MFontConfiguration(SunFontManager paramSunFontManager) {
/*  48 */     super(paramSunFontManager);
/*  49 */     if (FontUtilities.debugFonts()) {
/*  50 */       logger = PlatformLogger.getLogger("sun.awt.FontConfiguration");
/*     */     }
/*  52 */     initTables();
/*     */   }
/*     */ 
/*     */   
/*     */   private static final String fontsDirPrefix = "$JRE_LIB_FONTS";
/*     */   
/*     */   public MFontConfiguration(SunFontManager paramSunFontManager, boolean paramBoolean1, boolean paramBoolean2) {
/*  59 */     super(paramSunFontManager, paramBoolean1, paramBoolean2);
/*  60 */     if (FontUtilities.debugFonts()) {
/*  61 */       logger = PlatformLogger.getLogger("sun.awt.FontConfiguration");
/*     */     }
/*  63 */     initTables();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initReorderMap() {
/*  70 */     this.reorderMap = new HashMap<>();
/*  71 */     if (osName == null) {
/*  72 */       initReorderMapForSolaris();
/*     */     } else {
/*  74 */       initReorderMapForLinux();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initReorderMapForSolaris() {
/*  84 */     this.reorderMap.put("UTF-8.hi", "devanagari");
/*  85 */     this.reorderMap.put("UTF-8.ja", 
/*  86 */         split("japanese-x0201,japanese-x0208,japanese-x0212"));
/*  87 */     this.reorderMap.put("UTF-8.ko", "korean-johab");
/*  88 */     this.reorderMap.put("UTF-8.th", "thai");
/*  89 */     this.reorderMap.put("UTF-8.zh.TW", "chinese-big5");
/*  90 */     this.reorderMap.put("UTF-8.zh.HK", split("chinese-big5,chinese-hkscs"));
/*  91 */     if (FontUtilities.isSolaris8) {
/*  92 */       this.reorderMap.put("UTF-8.zh.CN", split("chinese-gb2312,chinese-big5"));
/*     */     } else {
/*  94 */       this.reorderMap.put("UTF-8.zh.CN", 
/*  95 */           split("chinese-gb18030-0,chinese-gb18030-1"));
/*     */     } 
/*  97 */     this.reorderMap.put("UTF-8.zh", 
/*  98 */         split("chinese-big5,chinese-hkscs,chinese-gb18030-0,chinese-gb18030-1"));
/*  99 */     this.reorderMap.put("Big5", "chinese-big5");
/* 100 */     this.reorderMap.put("Big5-HKSCS", split("chinese-big5,chinese-hkscs"));
/* 101 */     if (!FontUtilities.isSolaris8 && !FontUtilities.isSolaris9) {
/* 102 */       this.reorderMap.put("GB2312", split("chinese-gbk,chinese-gb2312"));
/*     */     } else {
/* 104 */       this.reorderMap.put("GB2312", "chinese-gb2312");
/*     */     } 
/* 106 */     this.reorderMap.put("x-EUC-TW", 
/* 107 */         split("chinese-cns11643-1,chinese-cns11643-2,chinese-cns11643-3"));
/* 108 */     this.reorderMap.put("GBK", "chinese-gbk");
/* 109 */     this.reorderMap.put("GB18030", split("chinese-gb18030-0,chinese-gb18030-1"));
/*     */     
/* 111 */     this.reorderMap.put("TIS-620", "thai");
/* 112 */     this.reorderMap.put("x-PCK", 
/* 113 */         split("japanese-x0201,japanese-x0208,japanese-x0212"));
/* 114 */     this.reorderMap.put("x-eucJP-Open", 
/* 115 */         split("japanese-x0201,japanese-x0208,japanese-x0212"));
/* 116 */     this.reorderMap.put("EUC-KR", "korean");
/*     */ 
/*     */     
/* 119 */     this.reorderMap.put("ISO-8859-2", "latin-2");
/* 120 */     this.reorderMap.put("ISO-8859-5", "cyrillic-iso8859-5");
/* 121 */     this.reorderMap.put("windows-1251", "cyrillic-cp1251");
/* 122 */     this.reorderMap.put("KOI8-R", "cyrillic-koi8-r");
/* 123 */     this.reorderMap.put("ISO-8859-6", "arabic");
/* 124 */     this.reorderMap.put("ISO-8859-7", "greek");
/* 125 */     this.reorderMap.put("ISO-8859-8", "hebrew");
/* 126 */     this.reorderMap.put("ISO-8859-9", "latin-5");
/* 127 */     this.reorderMap.put("ISO-8859-13", "latin-7");
/* 128 */     this.reorderMap.put("ISO-8859-15", "latin-9");
/*     */   }
/*     */   
/*     */   private void initReorderMapForLinux() {
/* 132 */     this.reorderMap.put("UTF-8.ja.JP", "japanese-iso10646");
/* 133 */     this.reorderMap.put("UTF-8.ko.KR", "korean-iso10646");
/* 134 */     this.reorderMap.put("UTF-8.zh.TW", "chinese-tw-iso10646");
/* 135 */     this.reorderMap.put("UTF-8.zh.HK", "chinese-tw-iso10646");
/* 136 */     this.reorderMap.put("UTF-8.zh.CN", "chinese-cn-iso10646");
/* 137 */     this.reorderMap.put("x-euc-jp-linux", 
/* 138 */         split("japanese-x0201,japanese-x0208"));
/* 139 */     this.reorderMap.put("GB2312", "chinese-gb18030");
/* 140 */     this.reorderMap.put("Big5", "chinese-big5");
/* 141 */     this.reorderMap.put("EUC-KR", "korean");
/* 142 */     if (osName.equals("Sun")) {
/* 143 */       this.reorderMap.put("GB18030", "chinese-cn-iso10646");
/*     */     } else {
/*     */       
/* 146 */       this.reorderMap.put("GB18030", "chinese-gb18030");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setOsNameAndVersion() {
/* 154 */     super.setOsNameAndVersion();
/*     */     
/* 156 */     if (osName.equals("SunOS")) {
/*     */       
/* 158 */       osName = null;
/* 159 */     } else if (osName.equals("Linux")) {
/*     */       try {
/*     */         File file;
/* 162 */         if ((file = new File("/etc/fedora-release")).canRead()) {
/* 163 */           osName = "Fedora";
/* 164 */           osVersion = getVersionString(file);
/* 165 */         } else if ((file = new File("/etc/redhat-release")).canRead()) {
/* 166 */           osName = "RedHat";
/* 167 */           osVersion = getVersionString(file);
/* 168 */         } else if ((file = new File("/etc/turbolinux-release")).canRead()) {
/* 169 */           osName = "Turbo";
/* 170 */           osVersion = getVersionString(file);
/* 171 */         } else if ((file = new File("/etc/SuSE-release")).canRead()) {
/* 172 */           osName = "SuSE";
/* 173 */           osVersion = getVersionString(file);
/* 174 */         } else if ((file = new File("/etc/lsb-release")).canRead()) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 179 */           Properties properties = new Properties();
/* 180 */           properties.load(new FileInputStream(file));
/* 181 */           osName = properties.getProperty("DISTRIB_ID");
/* 182 */           osVersion = properties.getProperty("DISTRIB_RELEASE");
/*     */         } 
/* 184 */       } catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getVersionString(File paramFile) {
/*     */     try {
/* 195 */       Scanner scanner = new Scanner(paramFile);
/* 196 */       return scanner.findInLine("(\\d)+((\\.)(\\d)+)*");
/*     */     }
/* 198 */     catch (Exception exception) {
/*     */       
/* 200 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String mapFileName(String paramString) {
/* 206 */     if (paramString != null && paramString.startsWith("$JRE_LIB_FONTS")) {
/* 207 */       return SunFontManager.jreFontDirName + paramString
/* 208 */         .substring("$JRE_LIB_FONTS".length());
/*     */     }
/* 210 */     return paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFallbackFamilyName(String paramString1, String paramString2) {
/* 217 */     String str = getCompatibilityFamilyName(paramString1);
/* 218 */     if (str != null) {
/* 219 */       return str;
/*     */     }
/* 221 */     return paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getEncoding(String paramString1, String paramString2) {
/* 227 */     int i = 0;
/* 228 */     byte b = 13;
/* 229 */     while (b-- > 0 && i) {
/* 230 */       i = paramString1.indexOf("-", i) + 1;
/*     */     }
/* 232 */     if (i == -1) {
/* 233 */       return "default";
/*     */     }
/* 235 */     String str1 = paramString1.substring(i);
/* 236 */     if (str1.indexOf("fontspecific") > 0) {
/* 237 */       if (paramString1.indexOf("dingbats") > 0)
/* 238 */         return "sun.awt.motif.X11Dingbats"; 
/* 239 */       if (paramString1.indexOf("symbol") > 0) {
/* 240 */         return "sun.awt.Symbol";
/*     */       }
/*     */     } 
/* 243 */     String str2 = (String)encodingMap.get(str1);
/* 244 */     if (str2 == null) {
/* 245 */       str2 = "default";
/*     */     }
/* 247 */     return str2;
/*     */   }
/*     */   
/*     */   protected Charset getDefaultFontCharset(String paramString) {
/* 251 */     return Charset.forName("ISO8859_1");
/*     */   }
/*     */   
/*     */   protected String getFaceNameFromComponentFontName(String paramString) {
/* 255 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getFileNameFromComponentFontName(String paramString) {
/* 262 */     String str = getFileNameFromPlatformName(paramString);
/* 263 */     if (str != null && str.charAt(0) == '/' && 
/* 264 */       !needToSearchForFile(str)) {
/* 265 */       return str;
/*     */     }
/* 267 */     return ((X11FontManager)this.fontManager).getFileNameFromXLFD(paramString);
/*     */   }
/*     */   
/*     */   public HashSet<String> getAWTFontPathSet() {
/* 271 */     HashSet<String> hashSet = new HashSet();
/* 272 */     short[] arrayOfShort = getCoreScripts(0);
/* 273 */     for (byte b = 0; b < arrayOfShort.length; b++) {
/* 274 */       String str = getString(table_awtfontpaths[arrayOfShort[b]]);
/* 275 */       if (str != null) {
/* 276 */         int i = 0;
/* 277 */         int j = str.indexOf(':');
/* 278 */         while (j >= 0) {
/* 279 */           hashSet.add(str.substring(i, j));
/* 280 */           i = j + 1;
/* 281 */           j = str.indexOf(':', i);
/*     */         } 
/* 283 */         hashSet.add((i == 0) ? str : str.substring(i));
/*     */       } 
/*     */     } 
/* 286 */     return hashSet;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 291 */   private static HashMap encodingMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private void initTables() {
/* 296 */     encodingMap.put("iso8859-1", "ISO-8859-1");
/* 297 */     encodingMap.put("iso8859-2", "ISO-8859-2");
/* 298 */     encodingMap.put("iso8859-4", "ISO-8859-4");
/* 299 */     encodingMap.put("iso8859-5", "ISO-8859-5");
/* 300 */     encodingMap.put("iso8859-6", "ISO-8859-6");
/* 301 */     encodingMap.put("iso8859-7", "ISO-8859-7");
/* 302 */     encodingMap.put("iso8859-8", "ISO-8859-8");
/* 303 */     encodingMap.put("iso8859-9", "ISO-8859-9");
/* 304 */     encodingMap.put("iso8859-13", "ISO-8859-13");
/* 305 */     encodingMap.put("iso8859-15", "ISO-8859-15");
/* 306 */     encodingMap.put("gb2312.1980-0", "sun.awt.motif.X11GB2312");
/* 307 */     if (osName == null) {
/*     */       
/* 309 */       encodingMap.put("gbk-0", "GBK");
/*     */     } else {
/* 311 */       encodingMap.put("gbk-0", "sun.awt.motif.X11GBK");
/*     */     } 
/* 313 */     encodingMap.put("gb18030.2000-0", "sun.awt.motif.X11GB18030_0");
/* 314 */     encodingMap.put("gb18030.2000-1", "sun.awt.motif.X11GB18030_1");
/* 315 */     encodingMap.put("cns11643-1", "sun.awt.motif.X11CNS11643P1");
/* 316 */     encodingMap.put("cns11643-2", "sun.awt.motif.X11CNS11643P2");
/* 317 */     encodingMap.put("cns11643-3", "sun.awt.motif.X11CNS11643P3");
/* 318 */     encodingMap.put("big5-1", "Big5");
/* 319 */     encodingMap.put("big5-0", "Big5");
/* 320 */     encodingMap.put("hkscs-1", "Big5-HKSCS");
/* 321 */     encodingMap.put("ansi-1251", "windows-1251");
/* 322 */     encodingMap.put("koi8-r", "KOI8-R");
/* 323 */     encodingMap.put("jisx0201.1976-0", "sun.awt.motif.X11JIS0201");
/* 324 */     encodingMap.put("jisx0208.1983-0", "sun.awt.motif.X11JIS0208");
/* 325 */     encodingMap.put("jisx0212.1990-0", "sun.awt.motif.X11JIS0212");
/* 326 */     encodingMap.put("ksc5601.1987-0", "sun.awt.motif.X11KSC5601");
/* 327 */     encodingMap.put("ksc5601.1992-3", "sun.awt.motif.X11Johab");
/* 328 */     encodingMap.put("tis620.2533-0", "TIS-620");
/* 329 */     encodingMap.put("iso10646-1", "UTF-16BE");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/motif/MFontConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */