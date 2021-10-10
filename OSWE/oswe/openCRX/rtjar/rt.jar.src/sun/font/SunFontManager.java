/*      */ package sun.font;
/*      */ 
/*      */ import java.awt.Font;
/*      */ import java.awt.FontFormatException;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FilenameFilter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeMap;
/*      */ import java.util.Vector;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import javax.swing.plaf.FontUIResource;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.FontConfiguration;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.java2d.FontSupport;
/*      */ import sun.misc.ThreadGroupUtils;
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
/*      */ public abstract class SunFontManager
/*      */   implements FontSupport, FontManagerForSGE
/*      */ {
/*      */   public static final int FONTFORMAT_NONE = -1;
/*      */   public static final int FONTFORMAT_TRUETYPE = 0;
/*      */   public static final int FONTFORMAT_TYPE1 = 1;
/*      */   public static final int FONTFORMAT_T2K = 2;
/*      */   public static final int FONTFORMAT_TTC = 3;
/*      */   public static final int FONTFORMAT_COMPOSITE = 4;
/*      */   public static final int FONTFORMAT_NATIVE = 5;
/*      */   protected static final int CHANNELPOOLSIZE = 20;
/*      */   
/*      */   private static class TTFilter
/*      */     implements FilenameFilter
/*      */   {
/*      */     private TTFilter() {}
/*      */     
/*      */     public boolean accept(File param1File, String param1String) {
/*   70 */       int i = param1String.length() - 4;
/*   71 */       if (i <= 0) {
/*   72 */         return false;
/*      */       }
/*   74 */       return (param1String.startsWith(".ttf", i) || param1String
/*   75 */         .startsWith(".TTF", i) || param1String
/*   76 */         .startsWith(".ttc", i) || param1String
/*   77 */         .startsWith(".TTC", i) || param1String
/*   78 */         .startsWith(".otf", i) || param1String
/*   79 */         .startsWith(".OTF", i));
/*      */     } }
/*      */   
/*      */   private static class T1Filter implements FilenameFilter {
/*      */     private T1Filter() {}
/*      */     
/*      */     public boolean accept(File param1File, String param1String) {
/*   86 */       if (SunFontManager.noType1Font) {
/*   87 */         return false;
/*      */       }
/*      */       
/*   90 */       int i = param1String.length() - 4;
/*   91 */       if (i <= 0) {
/*   92 */         return false;
/*      */       }
/*   94 */       return (param1String.startsWith(".pfa", i) || param1String
/*   95 */         .startsWith(".pfb", i) || param1String
/*   96 */         .startsWith(".PFA", i) || param1String
/*   97 */         .startsWith(".PFB", i));
/*      */     }
/*      */   }
/*      */   
/*      */   private static class TTorT1Filter
/*      */     implements FilenameFilter {
/*      */     private TTorT1Filter() {}
/*      */     
/*      */     public boolean accept(File param1File, String param1String) {
/*  106 */       int i = param1String.length() - 4;
/*  107 */       if (i <= 0) {
/*  108 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  116 */       boolean bool = (param1String.startsWith(".ttf", i) || param1String.startsWith(".TTF", i) || param1String.startsWith(".ttc", i) || param1String.startsWith(".TTC", i) || param1String.startsWith(".otf", i) || param1String.startsWith(".OTF", i)) ? true : false;
/*  117 */       if (bool)
/*  118 */         return true; 
/*  119 */       if (SunFontManager.noType1Font) {
/*  120 */         return false;
/*      */       }
/*  122 */       return (param1String.startsWith(".pfa", i) || param1String
/*  123 */         .startsWith(".pfb", i) || param1String
/*  124 */         .startsWith(".PFA", i) || param1String
/*  125 */         .startsWith(".PFB", i));
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
/*  148 */   protected FileFont[] fontFileCache = new FileFont[20];
/*      */   
/*  150 */   private int lastPoolIndex = 0;
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
/*  161 */   private int maxCompFont = 0;
/*  162 */   private CompositeFont[] compFonts = new CompositeFont[20];
/*  163 */   private ConcurrentHashMap<String, CompositeFont> compositeFonts = new ConcurrentHashMap<>();
/*      */   
/*  165 */   private ConcurrentHashMap<String, PhysicalFont> physicalFonts = new ConcurrentHashMap<>();
/*      */   
/*  167 */   private ConcurrentHashMap<String, PhysicalFont> registeredFonts = new ConcurrentHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  175 */   protected ConcurrentHashMap<String, Font2D> fullNameToFont = new ConcurrentHashMap<>();
/*      */   
/*      */   private HashMap<String, TrueTypeFont> localeFullNamesToFont;
/*      */   
/*      */   private PhysicalFont defaultPhysicalFont;
/*      */   
/*      */   static boolean longAddresses;
/*      */   
/*      */   private boolean loaded1dot0Fonts = false;
/*      */   
/*      */   boolean loadedAllFonts = false;
/*      */   
/*      */   boolean loadedAllFontFiles = false;
/*      */   
/*      */   HashMap<String, String> jreFontMap;
/*      */   
/*      */   HashSet<String> jreLucidaFontFiles;
/*      */   
/*      */   String[] jreOtherFontFiles;
/*      */   boolean noOtherJREFontFiles = false;
/*      */   public static final String lucidaFontName = "Lucida Sans Regular";
/*      */   public static String jreLibDirName;
/*      */   public static String jreFontDirName;
/*  198 */   private static HashSet<String> missingFontFiles = null;
/*      */   private String defaultFontName;
/*      */   private String defaultFontFileName;
/*  201 */   protected HashSet registeredFontFiles = new HashSet();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ArrayList badFonts;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String fontPath;
/*      */ 
/*      */ 
/*      */   
/*      */   private FontConfiguration fontConfig;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean discoveredAllFonts = false;
/*      */ 
/*      */ 
/*      */   
/*  223 */   private static final FilenameFilter ttFilter = new TTFilter();
/*  224 */   private static final FilenameFilter t1Filter = new T1Filter();
/*      */   
/*      */   private Font[] allFonts;
/*      */   
/*      */   private String[] allFamilies;
/*      */   
/*      */   private Locale lastDefaultLocale;
/*      */   
/*      */   public static boolean noType1Font;
/*  233 */   private static String[] STR_ARRAY = new String[0];
/*      */   
/*      */   private boolean usePlatformFontMetrics = false;
/*      */   
/*      */   private final ConcurrentHashMap<String, FontRegistrationInfo> deferredFontFiles;
/*      */   private final ConcurrentHashMap<String, Font2DHandle> initialisedFonts;
/*      */   private HashMap<String, String> fontToFileMap;
/*      */   private HashMap<String, String> fontToFamilyNameMap;
/*      */   private HashMap<String, ArrayList<String>> familyToFontListMap;
/*      */   private String[] pathDirs;
/*      */   private boolean haveCheckedUnreferencedFontFiles;
/*      */   static HashMap<String, FamilyDescription> platformFontMap;
/*      */   private ConcurrentHashMap<String, Font2D> fontNameCache;
/*      */   protected Thread fileCloser;
/*      */   Vector<File> tmpFontFiles;
/*      */   
/*      */   public static SunFontManager getInstance() {
/*  250 */     FontManager fontManager = FontManagerFactory.getInstance();
/*  251 */     return (SunFontManager)fontManager;
/*      */   }
/*      */   
/*      */   public FilenameFilter getTrueTypeFilter() {
/*  255 */     return ttFilter;
/*      */   }
/*      */   
/*      */   public FilenameFilter getType1Filter() {
/*  259 */     return t1Filter;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean usingPerAppContextComposites() {
/*  264 */     return this._usingPerAppContextComposites;
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
/*      */   private void initJREFontMap() {
/*  280 */     this.jreFontMap = new HashMap<>();
/*  281 */     this.jreLucidaFontFiles = new HashSet<>();
/*  282 */     if (isOpenJDK()) {
/*      */       return;
/*      */     }
/*      */     
/*  286 */     this.jreFontMap.put("lucida sans0", "LucidaSansRegular.ttf");
/*  287 */     this.jreFontMap.put("lucida sans1", "LucidaSansDemiBold.ttf");
/*      */     
/*  289 */     this.jreFontMap.put("lucida sans regular0", "LucidaSansRegular.ttf");
/*  290 */     this.jreFontMap.put("lucida sans regular1", "LucidaSansDemiBold.ttf");
/*  291 */     this.jreFontMap.put("lucida sans bold1", "LucidaSansDemiBold.ttf");
/*  292 */     this.jreFontMap.put("lucida sans demibold1", "LucidaSansDemiBold.ttf");
/*      */ 
/*      */     
/*  295 */     this.jreFontMap.put("lucida sans typewriter0", "LucidaTypewriterRegular.ttf");
/*      */     
/*  297 */     this.jreFontMap.put("lucida sans typewriter1", "LucidaTypewriterBold.ttf");
/*      */     
/*  299 */     this.jreFontMap.put("lucida sans typewriter regular0", "LucidaTypewriter.ttf");
/*      */     
/*  301 */     this.jreFontMap.put("lucida sans typewriter regular1", "LucidaTypewriterBold.ttf");
/*      */     
/*  303 */     this.jreFontMap.put("lucida sans typewriter bold1", "LucidaTypewriterBold.ttf");
/*      */     
/*  305 */     this.jreFontMap.put("lucida sans typewriter demibold1", "LucidaTypewriterBold.ttf");
/*      */ 
/*      */ 
/*      */     
/*  309 */     this.jreFontMap.put("lucida bright0", "LucidaBrightRegular.ttf");
/*  310 */     this.jreFontMap.put("lucida bright1", "LucidaBrightDemiBold.ttf");
/*  311 */     this.jreFontMap.put("lucida bright2", "LucidaBrightItalic.ttf");
/*  312 */     this.jreFontMap.put("lucida bright3", "LucidaBrightDemiItalic.ttf");
/*      */     
/*  314 */     this.jreFontMap.put("lucida bright regular0", "LucidaBrightRegular.ttf");
/*  315 */     this.jreFontMap.put("lucida bright regular1", "LucidaBrightDemiBold.ttf");
/*  316 */     this.jreFontMap.put("lucida bright regular2", "LucidaBrightItalic.ttf");
/*  317 */     this.jreFontMap.put("lucida bright regular3", "LucidaBrightDemiItalic.ttf");
/*  318 */     this.jreFontMap.put("lucida bright bold1", "LucidaBrightDemiBold.ttf");
/*  319 */     this.jreFontMap.put("lucida bright bold3", "LucidaBrightDemiItalic.ttf");
/*  320 */     this.jreFontMap.put("lucida bright demibold1", "LucidaBrightDemiBold.ttf");
/*  321 */     this.jreFontMap.put("lucida bright demibold3", "LucidaBrightDemiItalic.ttf");
/*  322 */     this.jreFontMap.put("lucida bright italic2", "LucidaBrightItalic.ttf");
/*  323 */     this.jreFontMap.put("lucida bright italic3", "LucidaBrightDemiItalic.ttf");
/*  324 */     this.jreFontMap.put("lucida bright bold italic3", "LucidaBrightDemiItalic.ttf");
/*      */     
/*  326 */     this.jreFontMap.put("lucida bright demibold italic3", "LucidaBrightDemiItalic.ttf");
/*      */     
/*  328 */     for (String str : this.jreFontMap.values()) {
/*  329 */       this.jreLucidaFontFiles.add(str);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static {
/*  335 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run() {
/*      */             File file;
/*  339 */             FontManagerNativeLibrary.load();
/*      */ 
/*      */ 
/*      */             
/*  343 */             SunFontManager.initIDs();
/*      */             
/*  345 */             switch (StrikeCache.nativeAddressSize) { case 8:
/*  346 */                 SunFontManager.longAddresses = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  352 */                 SunFontManager.noType1Font = "true".equals(System.getProperty("sun.java2d.noType1Font"));
/*      */                 
/*  354 */                 SunFontManager.jreLibDirName = System.getProperty("java.home", "") + File.separator + "lib";
/*  355 */                 SunFontManager.jreFontDirName = SunFontManager.jreLibDirName + File.separator + "fonts";
/*  356 */                 file = new File(SunFontManager.jreFontDirName + File.separator + "LucidaSansRegular.ttf");
/*      */ 
/*      */                 
/*  359 */                 return null;case 4: SunFontManager.longAddresses = false; SunFontManager.noType1Font = "true".equals(System.getProperty("sun.java2d.noType1Font")); SunFontManager.jreLibDirName = System.getProperty("java.home", "") + File.separator + "lib"; SunFontManager.jreFontDirName = SunFontManager.jreLibDirName + File.separator + "fonts"; file = new File(SunFontManager.jreFontDirName + File.separator + "LucidaSansRegular.ttf"); return null; }
/*      */             
/*      */             throw new RuntimeException("Unexpected address size");
/*      */           }
/*      */         });
/*      */   }
/*      */   public TrueTypeFont getEUDCFont() {
/*  366 */     return null;
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
/*      */   public Font2DHandle getNewComposite(String paramString, int paramInt, Font2DHandle paramFont2DHandle) {
/*  603 */     if (!(paramFont2DHandle.font2D instanceof CompositeFont)) {
/*  604 */       return paramFont2DHandle;
/*      */     }
/*      */     
/*  607 */     CompositeFont compositeFont1 = (CompositeFont)paramFont2DHandle.font2D;
/*  608 */     PhysicalFont physicalFont1 = compositeFont1.getSlotFont(0);
/*      */     
/*  610 */     if (paramString == null) {
/*  611 */       paramString = physicalFont1.getFamilyName(null);
/*      */     }
/*  613 */     if (paramInt == -1) {
/*  614 */       paramInt = compositeFont1.getStyle();
/*      */     }
/*      */     
/*  617 */     Font2D font2D = findFont2D(paramString, paramInt, 0);
/*  618 */     if (!(font2D instanceof PhysicalFont)) {
/*  619 */       font2D = physicalFont1;
/*      */     }
/*  621 */     PhysicalFont physicalFont2 = (PhysicalFont)font2D;
/*      */     
/*  623 */     CompositeFont compositeFont2 = (CompositeFont)findFont2D("dialog", paramInt, 0);
/*  624 */     if (compositeFont2 == null) {
/*  625 */       return paramFont2DHandle;
/*      */     }
/*  627 */     CompositeFont compositeFont3 = new CompositeFont(physicalFont2, compositeFont2);
/*  628 */     return new Font2DHandle(compositeFont3);
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
/*      */   protected void registerCompositeFont(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, int paramInt, int[] paramArrayOfint1, int[] paramArrayOfint2, boolean paramBoolean) {
/*  640 */     CompositeFont compositeFont = new CompositeFont(paramString, paramArrayOfString1, paramArrayOfString2, paramInt, paramArrayOfint1, paramArrayOfint2, paramBoolean, this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  646 */     addCompositeToFontList(compositeFont, 2);
/*  647 */     synchronized (this.compFonts) {
/*  648 */       this.compFonts[this.maxCompFont++] = compositeFont;
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
/*      */   protected static void registerCompositeFont(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, int paramInt, int[] paramArrayOfint1, int[] paramArrayOfint2, boolean paramBoolean, ConcurrentHashMap<String, Font2D> paramConcurrentHashMap) {
/*  672 */     CompositeFont compositeFont = new CompositeFont(paramString, paramArrayOfString1, paramArrayOfString2, paramInt, paramArrayOfint1, paramArrayOfint2, paramBoolean, getInstance());
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
/*  688 */     Font2D font2D = paramConcurrentHashMap.get(paramString.toLowerCase(Locale.ENGLISH));
/*  689 */     if (font2D instanceof CompositeFont) {
/*  690 */       font2D.handle.font2D = compositeFont;
/*      */     }
/*  692 */     paramConcurrentHashMap.put(paramString.toLowerCase(Locale.ENGLISH), compositeFont);
/*      */   }
/*      */ 
/*      */   
/*      */   private void addCompositeToFontList(CompositeFont paramCompositeFont, int paramInt) {
/*  697 */     if (FontUtilities.isLogging()) {
/*  698 */       FontUtilities.getLogger().info("Add to Family " + paramCompositeFont.familyName + ", Font " + paramCompositeFont.fullName + " rank=" + paramInt);
/*      */     }
/*      */     
/*  701 */     paramCompositeFont.setRank(paramInt);
/*  702 */     this.compositeFonts.put(paramCompositeFont.fullName, paramCompositeFont);
/*  703 */     this.fullNameToFont.put(paramCompositeFont.fullName.toLowerCase(Locale.ENGLISH), paramCompositeFont);
/*      */     
/*  705 */     FontFamily fontFamily = FontFamily.getFamily(paramCompositeFont.familyName);
/*  706 */     if (fontFamily == null) {
/*  707 */       fontFamily = new FontFamily(paramCompositeFont.familyName, true, paramInt);
/*      */     }
/*  709 */     fontFamily.setFont(paramCompositeFont, paramCompositeFont.style);
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
/*      */   protected PhysicalFont addToFontList(PhysicalFont paramPhysicalFont, int paramInt) {
/*  747 */     String str1 = paramPhysicalFont.fullName;
/*  748 */     String str2 = paramPhysicalFont.familyName;
/*  749 */     if (str1 == null || "".equals(str1)) {
/*  750 */       return null;
/*      */     }
/*  752 */     if (this.compositeFonts.containsKey(str1))
/*      */     {
/*  754 */       return null;
/*      */     }
/*  756 */     paramPhysicalFont.setRank(paramInt);
/*  757 */     if (!this.physicalFonts.containsKey(str1)) {
/*  758 */       if (FontUtilities.isLogging()) {
/*  759 */         FontUtilities.getLogger().info("Add to Family " + str2 + ", Font " + str1 + " rank=" + paramInt);
/*      */       }
/*      */       
/*  762 */       this.physicalFonts.put(str1, paramPhysicalFont);
/*  763 */       FontFamily fontFamily = FontFamily.getFamily(str2);
/*  764 */       if (fontFamily == null) {
/*  765 */         fontFamily = new FontFamily(str2, false, paramInt);
/*  766 */         fontFamily.setFont(paramPhysicalFont, paramPhysicalFont.style);
/*      */       } else {
/*  768 */         fontFamily.setFont(paramPhysicalFont, paramPhysicalFont.style);
/*      */       } 
/*  770 */       this.fullNameToFont.put(str1.toLowerCase(Locale.ENGLISH), paramPhysicalFont);
/*  771 */       return paramPhysicalFont;
/*      */     } 
/*  773 */     PhysicalFont physicalFont1 = paramPhysicalFont;
/*  774 */     PhysicalFont physicalFont2 = this.physicalFonts.get(str1);
/*  775 */     if (physicalFont2 == null) {
/*  776 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  781 */     if (physicalFont2.getRank() >= paramInt) {
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
/*  801 */       if (physicalFont2.mapper != null && paramInt > 2) {
/*  802 */         return physicalFont2;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  810 */       if (physicalFont2.getRank() == paramInt) {
/*  811 */         if (physicalFont2 instanceof TrueTypeFont && physicalFont1 instanceof TrueTypeFont) {
/*      */           
/*  813 */           TrueTypeFont trueTypeFont1 = (TrueTypeFont)physicalFont2;
/*  814 */           TrueTypeFont trueTypeFont2 = (TrueTypeFont)physicalFont1;
/*  815 */           if (trueTypeFont1.fileSize >= trueTypeFont2.fileSize) {
/*  816 */             return physicalFont2;
/*      */           }
/*      */         } else {
/*  819 */           return physicalFont2;
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  831 */       if (physicalFont2.platName.startsWith(jreFontDirName)) {
/*  832 */         if (FontUtilities.isLogging()) {
/*  833 */           FontUtilities.getLogger()
/*  834 */             .warning("Unexpected attempt to replace a JRE  font " + str1 + " from " + physicalFont2.platName + " with " + physicalFont1.platName);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  839 */         return physicalFont2;
/*      */       } 
/*      */       
/*  842 */       if (FontUtilities.isLogging()) {
/*  843 */         FontUtilities.getLogger()
/*  844 */           .info("Replace in Family " + str2 + ",Font " + str1 + " new rank=" + paramInt + " from " + physicalFont2.platName + " with " + physicalFont1.platName);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  849 */       replaceFont(physicalFont2, physicalFont1);
/*  850 */       this.physicalFonts.put(str1, physicalFont1);
/*  851 */       this.fullNameToFont.put(str1.toLowerCase(Locale.ENGLISH), physicalFont1);
/*      */ 
/*      */       
/*  854 */       FontFamily fontFamily = FontFamily.getFamily(str2);
/*  855 */       if (fontFamily == null) {
/*  856 */         fontFamily = new FontFamily(str2, false, paramInt);
/*  857 */         fontFamily.setFont(physicalFont1, physicalFont1.style);
/*      */       } else {
/*  859 */         fontFamily.setFont(physicalFont1, physicalFont1.style);
/*      */       } 
/*  861 */       return physicalFont1;
/*      */     } 
/*  863 */     return physicalFont2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Font2D[] getRegisteredFonts() {
/*  869 */     PhysicalFont[] arrayOfPhysicalFont = getPhysicalFonts();
/*  870 */     int i = this.maxCompFont;
/*  871 */     Font2D[] arrayOfFont2D = new Font2D[arrayOfPhysicalFont.length + i];
/*  872 */     System.arraycopy(this.compFonts, 0, arrayOfFont2D, 0, i);
/*  873 */     System.arraycopy(arrayOfPhysicalFont, 0, arrayOfFont2D, i, arrayOfPhysicalFont.length);
/*  874 */     return arrayOfFont2D;
/*      */   }
/*      */   
/*      */   protected PhysicalFont[] getPhysicalFonts() {
/*  878 */     return (PhysicalFont[])this.physicalFonts.values().toArray((Object[])new PhysicalFont[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class FontRegistrationInfo
/*      */   {
/*      */     String fontFilePath;
/*      */ 
/*      */ 
/*      */     
/*      */     String[] nativeNames;
/*      */ 
/*      */ 
/*      */     
/*      */     int fontFormat;
/*      */ 
/*      */     
/*      */     boolean javaRasterizer;
/*      */ 
/*      */     
/*      */     int fontRank;
/*      */ 
/*      */ 
/*      */     
/*      */     FontRegistrationInfo(String param1String, String[] param1ArrayOfString, int param1Int1, boolean param1Boolean, int param1Int2) {
/*  905 */       this.fontFilePath = param1String;
/*  906 */       this.nativeNames = param1ArrayOfString;
/*  907 */       this.fontFormat = param1Int1;
/*  908 */       this.javaRasterizer = param1Boolean;
/*  909 */       this.fontRank = param1Int2;
/*      */     }
/*      */   } protected synchronized void initialiseDeferredFonts() { for (String str : this.deferredFontFiles.keySet()) initialiseDeferredFont(str);  } protected synchronized void registerDeferredJREFonts(String paramString) { for (FontRegistrationInfo fontRegistrationInfo : this.deferredFontFiles.values()) { if (fontRegistrationInfo.fontFilePath != null && fontRegistrationInfo.fontFilePath.startsWith(paramString)) initialiseDeferredFont(fontRegistrationInfo.fontFilePath);  }  } public boolean isDeferredFont(String paramString) { return this.deferredFontFiles.containsKey(paramString); } public PhysicalFont findJREDeferredFont(String paramString, int paramInt) { String str1 = paramString.toLowerCase(Locale.ENGLISH) + paramInt; String str2 = this.jreFontMap.get(str1); if (str2 != null) { str2 = jreFontDirName + File.separator + str2; if (this.deferredFontFiles.get(str2) != null) { PhysicalFont physicalFont = initialiseDeferredFont(str2); if (physicalFont != null && (physicalFont.getFontName(null).equalsIgnoreCase(paramString) || physicalFont.getFamilyName(null).equalsIgnoreCase(paramString)) && physicalFont.style == paramInt) return physicalFont;  }  }  if (this.noOtherJREFontFiles) return null;  synchronized (this.jreLucidaFontFiles) { if (this.jreOtherFontFiles == null) { HashSet<String> hashSet = new HashSet(); for (String str3 : this.deferredFontFiles.keySet()) { File file = new File(str3); String str4 = file.getParent(); String str5 = file.getName(); if (str4 == null || !str4.equals(jreFontDirName) || this.jreLucidaFontFiles.contains(str5)) continue;  hashSet.add(str3); }  this.jreOtherFontFiles = hashSet.<String>toArray(STR_ARRAY); if (this.jreOtherFontFiles.length == 0) this.noOtherJREFontFiles = true;  }  for (byte b = 0; b < this.jreOtherFontFiles.length; b++) { str2 = this.jreOtherFontFiles[b]; if (str2 != null) { this.jreOtherFontFiles[b] = null; PhysicalFont physicalFont = initialiseDeferredFont(str2); if (physicalFont != null && (physicalFont.getFontName(null).equalsIgnoreCase(paramString) || physicalFont.getFamilyName(null).equalsIgnoreCase(paramString)) && physicalFont.style == paramInt) return physicalFont;  }  }  }  return null; } private PhysicalFont findOtherDeferredFont(String paramString, int paramInt) { for (String str1 : this.deferredFontFiles.keySet()) { File file = new File(str1); String str2 = file.getParent(); String str3 = file.getName(); if (str2 != null && str2.equals(jreFontDirName) && this.jreLucidaFontFiles.contains(str3)) continue;  PhysicalFont physicalFont = initialiseDeferredFont(str1); if (physicalFont != null && (physicalFont.getFontName(null).equalsIgnoreCase(paramString) || physicalFont.getFamilyName(null).equalsIgnoreCase(paramString)) && physicalFont.style == paramInt) return physicalFont;  }  return null; } private PhysicalFont findDeferredFont(String paramString, int paramInt) { PhysicalFont physicalFont = findJREDeferredFont(paramString, paramInt); if (physicalFont != null) return physicalFont;  return findOtherDeferredFont(paramString, paramInt); } public void registerDeferredFont(String paramString1, String paramString2, String[] paramArrayOfString, int paramInt1, boolean paramBoolean, int paramInt2) { FontRegistrationInfo fontRegistrationInfo = new FontRegistrationInfo(paramString2, paramArrayOfString, paramInt1, paramBoolean, paramInt2); this.deferredFontFiles.put(paramString1, fontRegistrationInfo); } public synchronized PhysicalFont initialiseDeferredFont(String paramString) { PhysicalFont physicalFont; if (paramString == null) return null;  if (FontUtilities.isLogging()) FontUtilities.getLogger().info("Opening deferred font file " + paramString);  FontRegistrationInfo fontRegistrationInfo = this.deferredFontFiles.get(paramString); if (fontRegistrationInfo != null) { this.deferredFontFiles.remove(paramString); physicalFont = registerFontFile(fontRegistrationInfo.fontFilePath, fontRegistrationInfo.nativeNames, fontRegistrationInfo.fontFormat, fontRegistrationInfo.javaRasterizer, fontRegistrationInfo.fontRank); if (physicalFont != null) { this.initialisedFonts.put(paramString, physicalFont.handle); } else { this.initialisedFonts.put(paramString, (getDefaultPhysicalFont()).handle); }  } else { Font2DHandle font2DHandle = this.initialisedFonts.get(paramString); if (font2DHandle == null) { physicalFont = getDefaultPhysicalFont(); } else { physicalFont = (PhysicalFont)font2DHandle.font2D; }  }  return physicalFont; } public boolean isRegisteredFontFile(String paramString) { return this.registeredFonts.containsKey(paramString); } public PhysicalFont getRegisteredFontFile(String paramString) { return this.registeredFonts.get(paramString); } public PhysicalFont registerFontFile(String paramString, String[] paramArrayOfString, int paramInt1, boolean paramBoolean, int paramInt2) { PhysicalFont physicalFont1 = this.registeredFonts.get(paramString); if (physicalFont1 != null) return physicalFont1;  PhysicalFont physicalFont2 = null; try { byte b; TrueTypeFont trueTypeFont; Type1Font type1Font; NativeFont nativeFont; switch (paramInt1) { case 0: b = 0; do { trueTypeFont = new TrueTypeFont(paramString, paramArrayOfString, b++, paramBoolean); PhysicalFont physicalFont = addToFontList(trueTypeFont, paramInt2); if (physicalFont2 != null) continue;  physicalFont2 = physicalFont; } while (b < trueTypeFont.getFontCount()); break;case 1: type1Font = new Type1Font(paramString, paramArrayOfString); physicalFont2 = addToFontList(type1Font, paramInt2); break;case 5: nativeFont = new NativeFont(paramString, false); physicalFont2 = addToFontList(nativeFont, paramInt2); break; }  if (FontUtilities.isLogging()) FontUtilities.getLogger().info("Registered file " + paramString + " as font " + physicalFont2 + " rank=" + paramInt2);  } catch (FontFormatException fontFormatException) { if (FontUtilities.isLogging()) FontUtilities.getLogger().warning("Unusable font: " + paramString + " " + fontFormatException.toString());  }  if (physicalFont2 != null && paramInt1 != 5) this.registeredFonts.put(paramString, physicalFont2);  return physicalFont2; } public void registerFonts(String[] paramArrayOfString, String[][] paramArrayOfString1, int paramInt1, int paramInt2, boolean paramBoolean1, int paramInt3, boolean paramBoolean2) { for (byte b = 0; b < paramInt1; b++) { if (paramBoolean2) { registerDeferredFont(paramArrayOfString[b], paramArrayOfString[b], paramArrayOfString1[b], paramInt2, paramBoolean1, paramInt3); } else { registerFontFile(paramArrayOfString[b], paramArrayOfString1[b], paramInt2, paramBoolean1, paramInt3); }  }  } public PhysicalFont getDefaultPhysicalFont() { if (this.defaultPhysicalFont == null) { this.defaultPhysicalFont = (PhysicalFont)findFont2D("Lucida Sans Regular", 0, 0); if (this.defaultPhysicalFont == null) this.defaultPhysicalFont = (PhysicalFont)findFont2D("Arial", 0, 0);  if (this.defaultPhysicalFont == null) { Iterator<PhysicalFont> iterator = this.physicalFonts.values().iterator(); if (iterator.hasNext()) { this.defaultPhysicalFont = iterator.next(); } else { throw new Error("Probable fatal error:No fonts found."); }  }  }  return this.defaultPhysicalFont; } public Font2D getDefaultLogicalFont(int paramInt) { return findFont2D("dialog", paramInt, 0); } private static String dotStyleStr(int paramInt) { switch (paramInt) { case 1: return ".bold";case 2: return ".italic";case 3: return ".bolditalic"; }  return ".plain"; } protected void populateFontFileNameMap(HashMap<String, String> paramHashMap1, HashMap<String, String> paramHashMap2, HashMap<String, ArrayList<String>> paramHashMap, Locale paramLocale) {} private String[] getFontFilesFromPath(boolean paramBoolean) { final FilenameFilter filter; if (paramBoolean) { filenameFilter = ttFilter; } else { filenameFilter = new TTorT1Filter(); }  return AccessController.<String[]>doPrivileged(new PrivilegedAction<String>() {
/*      */           public Object run() { if (SunFontManager.this.pathDirs.length == 1) { File file = new File(SunFontManager.this.pathDirs[0]); String[] arrayOfString = file.list(filter); if (arrayOfString == null) return new String[0];  for (byte b1 = 0; b1 < arrayOfString.length; b1++) arrayOfString[b1] = arrayOfString[b1].toLowerCase();  return arrayOfString; }  ArrayList<String> arrayList = new ArrayList(); for (byte b = 0; b < SunFontManager.this.pathDirs.length; b++) { File file = new File(SunFontManager.this.pathDirs[b]); String[] arrayOfString = file.list(filter); if (arrayOfString != null) for (byte b1 = 0; b1 < arrayOfString.length; b1++) arrayList.add(arrayOfString[b1].toLowerCase());   }  return arrayList.toArray(SunFontManager.STR_ARRAY); }
/*  913 */         }); } protected SunFontManager() { this.deferredFontFiles = new ConcurrentHashMap<>();
/*      */ 
/*      */     
/*  916 */     this.initialisedFonts = new ConcurrentHashMap<>();
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
/* 1281 */     this.fontToFileMap = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1287 */     this.fontToFamilyNameMap = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1294 */     this.familyToFontListMap = null;
/*      */ 
/*      */     
/* 1297 */     this.pathDirs = null;
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
/* 2063 */     this.fontNameCache = new ConcurrentHashMap<>();
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
/* 2446 */     this.fileCloser = null;
/*      */     
/* 2448 */     this.tmpFontFiles = null;
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
/* 2828 */     this._usingPerAppContextComposites = false;
/* 2829 */     this._usingAlternateComposites = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2835 */     this.gLocalePref = false;
/* 2836 */     this.gPropPref = false;
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
/* 2998 */     this.fontsAreRegistered = false;
/* 2999 */     this.fontsAreRegisteredPerAppContext = false; initJREFontMap(); AccessController.doPrivileged(new PrivilegedAction() { public Object run() { File file = new File(SunFontManager.jreFontDirName + File.separator + "badfonts.txt"); if (file.exists()) { FileInputStream fileInputStream = null; try { SunFontManager.this.badFonts = new ArrayList(); fileInputStream = new FileInputStream(file); InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream); BufferedReader bufferedReader = new BufferedReader(inputStreamReader); while (true) { String str = bufferedReader.readLine(); if (str == null) break;  if (FontUtilities.debugFonts()) FontUtilities.getLogger().warning("read bad font: " + str);  SunFontManager.this.badFonts.add(str); }  } catch (IOException iOException) { try { if (fileInputStream != null) fileInputStream.close();  } catch (IOException iOException1) {} }  }  if (FontUtilities.isLinux) SunFontManager.this.registerFontDir(SunFontManager.jreFontDirName);  SunFontManager.this.registerFontsInDir(SunFontManager.jreFontDirName, true, 2, true, false); SunFontManager.this.fontConfig = SunFontManager.this.createFontConfiguration(); if (SunFontManager.isOpenJDK()) { String[] arrayOfString = SunFontManager.this.getDefaultPlatformFont(); SunFontManager.this.defaultFontName = arrayOfString[0]; SunFontManager.this.defaultFontFileName = arrayOfString[1]; }  String str1 = SunFontManager.this.fontConfig.getExtraFontPath(); boolean bool1 = false; boolean bool2 = false; String str2 = System.getProperty("sun.java2d.fontpath"); if (str2 != null) if (str2.startsWith("prepend:")) { bool1 = true; str2 = str2.substring("prepend:".length()); } else if (str2.startsWith("append:")) { bool2 = true; str2 = str2.substring("append:".length()); }   if (FontUtilities.debugFonts()) { PlatformLogger platformLogger = FontUtilities.getLogger(); platformLogger.info("JRE font directory: " + SunFontManager.jreFontDirName); platformLogger.info("Extra font path: " + str1); platformLogger.info("Debug font path: " + str2); }  if (str2 != null) { SunFontManager.this.fontPath = SunFontManager.this.getPlatformFontPath(SunFontManager.noType1Font); if (str1 != null) SunFontManager.this.fontPath = str1 + File.pathSeparator + SunFontManager.this.fontPath;  if (bool2) { SunFontManager.this.fontPath += File.pathSeparator + str2; } else if (bool1) { SunFontManager.this.fontPath = str2 + File.pathSeparator + SunFontManager.this.fontPath; } else { SunFontManager.this.fontPath = str2; }  SunFontManager.this.registerFontDirs(SunFontManager.this.fontPath); } else if (str1 != null) { SunFontManager.this.registerFontDirs(str1); }  if (FontUtilities.isSolaris && Locale.JAPAN.equals(Locale.getDefault())) SunFontManager.this.registerFontDir("/usr/openwin/lib/locale/ja/X11/fonts/TT");  SunFontManager.this.initCompositeFonts(SunFontManager.this.fontConfig, null); return null; } }
/*      */       ); boolean bool = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() { public Boolean run() { String str1 = System.getProperty("java2d.font.usePlatformFont"); String str2 = System.getenv("JAVA2D_USEPLATFORMFONT"); return Boolean.valueOf(("true".equals(str1) || str2 != null)); } }
/*      */       )).booleanValue(); if (bool) { this.usePlatformFontMetrics = true; System.out.println("Enabling platform font metrics for win32. This is an unsupported option."); System.out.println("This yields incorrect composite font metrics as reported by 1.1.x releases."); System.out.println("It is appropriate only for use by applications which do not use any Java 2"); System.out.println("functionality. This property will be removed in a later release."); }  } private void resolveWindowsFonts() { ArrayList<String> arrayList = null; for (String str1 : this.fontToFamilyNameMap.keySet()) { String str2 = this.fontToFileMap.get(str1); if (str2 == null) { if (str1.indexOf("  ") > 0) { String str = str1.replaceFirst("  ", " "); str2 = this.fontToFileMap.get(str); if (str2 != null && !this.fontToFamilyNameMap.containsKey(str)) { this.fontToFileMap.remove(str); this.fontToFileMap.put(str1, str2); }  continue; }  if (str1.equals("marlett")) { this.fontToFileMap.put(str1, "marlett.ttf"); continue; }  if (str1.equals("david")) { str2 = this.fontToFileMap.get("david regular"); if (str2 != null) { this.fontToFileMap.remove("david regular"); this.fontToFileMap.put("david", str2); }  continue; }  if (arrayList == null) arrayList = new ArrayList();  arrayList.add(str1); }  }  if (arrayList != null) { HashSet<String> hashSet = new HashSet(); HashMap hashMap = (HashMap)this.fontToFileMap.clone(); for (String str : this.fontToFamilyNameMap.keySet()) hashMap.remove(str);  for (String str : hashMap.keySet()) { hashSet.add(hashMap.get(str)); this.fontToFileMap.remove(str); }  resolveFontFiles(hashSet, arrayList); if (arrayList.size() > 0) { ArrayList<String> arrayList1 = new ArrayList(); for (String str : this.fontToFileMap.values()) arrayList1.add(str.toLowerCase());  for (String str : getFontFilesFromPath(true)) { if (!arrayList1.contains(str)) hashSet.add(str);  }  resolveFontFiles(hashSet, arrayList); }  if (arrayList.size() > 0) { int i = arrayList.size(); for (byte b = 0; b < i; b++) { String str1 = arrayList.get(b); String str2 = this.fontToFamilyNameMap.get(str1); if (str2 != null) { ArrayList arrayList1 = this.familyToFontListMap.get(str2); if (arrayList1 != null && arrayList1.size() <= 1) this.familyToFontListMap.remove(str2);  }  this.fontToFamilyNameMap.remove(str1); if (FontUtilities.isLogging()) FontUtilities.getLogger().info("No file for font:" + str1);  }  }  }  } private synchronized void checkForUnreferencedFontFiles() { if (this.haveCheckedUnreferencedFontFiles) return;  this.haveCheckedUnreferencedFontFiles = true; if (!FontUtilities.isWindows) return;  ArrayList<String> arrayList = new ArrayList(); for (String str : this.fontToFileMap.values()) arrayList.add(str.toLowerCase());  HashMap<String, String> hashMap1 = null; HashMap<String, String> hashMap2 = null; HashMap<String, ArrayList<String>> hashMap = null; for (String str : getFontFilesFromPath(false)) { if (!arrayList.contains(str)) { if (FontUtilities.isLogging()) FontUtilities.getLogger().info("Found non-registry file : " + str);  PhysicalFont physicalFont = registerFontFile(getPathName(str)); if (physicalFont != null) { if (hashMap1 == null) { hashMap1 = new HashMap<>(this.fontToFileMap); hashMap2 = new HashMap<>(this.fontToFamilyNameMap); hashMap = new HashMap<>(this.familyToFontListMap); }  String str1 = physicalFont.getFontName(null); String str2 = physicalFont.getFamilyName(null); String str3 = str2.toLowerCase(); hashMap2.put(str1, str2); hashMap1.put(str1, str); ArrayList<?> arrayList1 = hashMap.get(str3); if (arrayList1 == null) { arrayList1 = new ArrayList(); } else { arrayList1 = new ArrayList(arrayList1); }  arrayList1.add(str1); hashMap.put(str3, arrayList1); }  }  }  if (hashMap1 != null) { this.fontToFileMap = hashMap1; this.familyToFontListMap = hashMap; this.fontToFamilyNameMap = hashMap2; }  } private void resolveFontFiles(HashSet<String> paramHashSet, ArrayList<String> paramArrayList) { Locale locale = SunToolkit.getStartupLocale(); label20: for (String str : paramHashSet) { try { byte b = 0; String str1 = getPathName(str); if (FontUtilities.isLogging()) FontUtilities.getLogger().info("Trying to resolve file " + str1);  while (true) { TrueTypeFont trueTypeFont = new TrueTypeFont(str1, null, b++, false); String str2 = trueTypeFont.getFontName(locale).toLowerCase(); if (paramArrayList.contains(str2)) { this.fontToFileMap.put(str2, str); paramArrayList.remove(str2); if (FontUtilities.isLogging()) FontUtilities.getLogger().info("Resolved absent registry entry for " + str2 + " located in " + str1);  }  if (b >= trueTypeFont.getFontCount()) continue label20;  }  } catch (Exception exception) {} }  } public static class FamilyDescription {
/*      */     public String familyName; public String plainFullName; public String boldFullName; public String italicFullName; public String boldItalicFullName; public String plainFileName; public String boldFileName; public String italicFileName; public String boldItalicFileName; } public HashMap<String, FamilyDescription> populateHardcodedFileNameMap() { return new HashMap<>(0); } Font2D findFontFromPlatformMap(String paramString, int paramInt) { if (platformFontMap == null) platformFontMap = populateHardcodedFileNameMap();  if (platformFontMap == null || platformFontMap.size() == 0) return null;  int i = paramString.indexOf(' '); String str1 = paramString; if (i > 0) str1 = paramString.substring(0, i);  FamilyDescription familyDescription = platformFontMap.get(str1); if (familyDescription == null) return null;  byte b = -1; if (paramString.equalsIgnoreCase(familyDescription.plainFullName)) { b = 0; } else if (paramString.equalsIgnoreCase(familyDescription.boldFullName)) { b = 1; } else if (paramString.equalsIgnoreCase(familyDescription.italicFullName)) { b = 2; } else if (paramString.equalsIgnoreCase(familyDescription.boldItalicFullName)) { b = 3; }  if (b == -1 && !paramString.equalsIgnoreCase(familyDescription.familyName)) return null;  String str2 = null, str3 = null; String str4 = null, str5 = null; boolean bool = false; getPlatformFontDirs(noType1Font); if (familyDescription.plainFileName != null) { str2 = getPathName(familyDescription.plainFileName); if (str2 == null) bool = true;  }  if (familyDescription.boldFileName != null) { str3 = getPathName(familyDescription.boldFileName); if (str3 == null) bool = true;  }  if (familyDescription.italicFileName != null) { str4 = getPathName(familyDescription.italicFileName); if (str4 == null) bool = true;  }  if (familyDescription.boldItalicFileName != null) { str5 = getPathName(familyDescription.boldItalicFileName); if (str5 == null) bool = true;  }  if (bool) { if (FontUtilities.isLogging()) FontUtilities.getLogger().info("Hardcoded file missing looking for " + paramString);  platformFontMap.remove(str1); return null; }  final String[] files = { str2, str3, str4, str5 }; bool = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() { public Boolean run() { for (byte b = 0; b < files.length; b++) { if (files[b] != null) { File file = new File(files[b]); if (!file.exists()) return Boolean.TRUE;  }  }  return Boolean.FALSE; } }
/*      */       )).booleanValue(); if (bool) { if (FontUtilities.isLogging()) FontUtilities.getLogger().info("Hardcoded file missing looking for " + paramString);  platformFontMap.remove(str1); return null; }  Font2D font2D = null; for (byte b1 = 0; b1 < arrayOfString.length; b1++) { if (arrayOfString[b1] != null) { PhysicalFont physicalFont = registerFontFile(arrayOfString[b1], null, 0, false, 3); if (b1 == b) font2D = physicalFont;  }  }  FontFamily fontFamily = FontFamily.getFamily(familyDescription.familyName); if (fontFamily != null) if (font2D == null) { font2D = fontFamily.getFont(paramInt); if (font2D == null) font2D = fontFamily.getClosestStyle(paramInt);  } else if (paramInt > 0 && paramInt != font2D.style) { paramInt |= font2D.style; font2D = fontFamily.getFont(paramInt); if (font2D == null) font2D = fontFamily.getClosestStyle(paramInt);  }   return font2D; } private synchronized HashMap<String, String> getFullNameToFileMap() { if (this.fontToFileMap == null) { this.pathDirs = getPlatformFontDirs(noType1Font); this.fontToFileMap = new HashMap<>(100); this.fontToFamilyNameMap = new HashMap<>(100); this.familyToFontListMap = new HashMap<>(50); populateFontFileNameMap(this.fontToFileMap, this.fontToFamilyNameMap, this.familyToFontListMap, Locale.ENGLISH); if (FontUtilities.isWindows) resolveWindowsFonts();  if (FontUtilities.isLogging()) logPlatformFontInfo();  }  return this.fontToFileMap; } private void logPlatformFontInfo() { PlatformLogger platformLogger = FontUtilities.getLogger(); for (byte b = 0; b < this.pathDirs.length; b++) platformLogger.info("fontdir=" + this.pathDirs[b]);  for (String str : this.fontToFileMap.keySet()) platformLogger.info("font=" + str + " file=" + (String)this.fontToFileMap.get(str));  for (String str : this.fontToFamilyNameMap.keySet()) platformLogger.info("font=" + str + " family=" + (String)this.fontToFamilyNameMap.get(str));  for (String str : this.familyToFontListMap.keySet()) platformLogger.info("family=" + str + " fonts=" + this.familyToFontListMap.get(str));  } protected String[] getFontNamesFromPlatform() { if (getFullNameToFileMap().size() == 0) return null;  checkForUnreferencedFontFiles(); ArrayList<String> arrayList = new ArrayList(); for (ArrayList<String> arrayList1 : this.familyToFontListMap.values()) { for (String str : arrayList1) arrayList.add(str);  }  return arrayList.<String>toArray(STR_ARRAY); } public boolean gotFontsFromPlatform() { return (getFullNameToFileMap().size() != 0); } public String getFileNameForFontName(String paramString) { String str = paramString.toLowerCase(Locale.ENGLISH); return this.fontToFileMap.get(str); } private PhysicalFont registerFontFile(String paramString) { if ((new File(paramString)).isAbsolute() && !this.registeredFonts.contains(paramString)) { byte b = -1; byte b1 = 6; if (ttFilter.accept(null, paramString)) { b = 0; b1 = 3; } else if (t1Filter.accept(null, paramString)) { b = 1; b1 = 4; }  if (b == -1) return null;  return registerFontFile(paramString, null, b, false, b1); }  return null; } protected void registerOtherFontFiles(HashSet paramHashSet) { if (getFullNameToFileMap().size() == 0) return;  for (String str : this.fontToFileMap.values()) registerFontFile(str);  } public boolean getFamilyNamesFromPlatform(TreeMap<String, String> paramTreeMap, Locale paramLocale) { if (getFullNameToFileMap().size() == 0) return false;  checkForUnreferencedFontFiles(); for (String str : this.fontToFamilyNameMap.values()) paramTreeMap.put(str.toLowerCase(paramLocale), str);  return true; } private String getPathName(final String s) { File file = new File(s); if (file.isAbsolute()) return s;  if (this.pathDirs.length == 1) return this.pathDirs[0] + File.separator + s;  String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*      */           public String run() { for (byte b = 0; b < SunFontManager.this.pathDirs.length; b++) { File file = new File(SunFontManager.this.pathDirs[b] + File.separator + s); if (file.exists()) return file.getAbsolutePath();  }  return null; }
/* 3005 */         }); if (str != null) return str;  return s; } private Font2D findFontFromPlatform(String paramString, int paramInt) { if (getFullNameToFileMap().size() == 0) return null;  ArrayList<String> arrayList = null; String str1 = null; String str2 = this.fontToFamilyNameMap.get(paramString); if (str2 != null) { str1 = this.fontToFileMap.get(paramString); arrayList = this.familyToFontListMap.get(str2.toLowerCase(Locale.ENGLISH)); } else { arrayList = this.familyToFontListMap.get(paramString); if (arrayList != null && arrayList.size() > 0) { String str = ((String)arrayList.get(0)).toLowerCase(Locale.ENGLISH); if (str != null) str2 = this.fontToFamilyNameMap.get(str);  }  }  if (arrayList == null || str2 == null) return null;  String[] arrayOfString = arrayList.<String>toArray(STR_ARRAY); if (arrayOfString.length == 0) return null;  for (byte b1 = 0; b1 < arrayOfString.length; b1++) { String str3 = arrayOfString[b1].toLowerCase(Locale.ENGLISH); String str4 = this.fontToFileMap.get(str3); if (str4 == null) { if (FontUtilities.isLogging()) FontUtilities.getLogger().info("Platform lookup : No file for font " + arrayOfString[b1] + " in family " + str2);  return null; }  }  PhysicalFont physicalFont = null; if (str1 != null) physicalFont = registerFontFile(getPathName(str1), null, 0, false, 3);  for (byte b2 = 0; b2 < arrayOfString.length; b2++) { String str3 = arrayOfString[b2].toLowerCase(Locale.ENGLISH); String str4 = this.fontToFileMap.get(str3); if (str1 == null || !str1.equals(str4)) registerFontFile(getPathName(str4), null, 0, false, 3);  }  Font2D font2D = null; FontFamily fontFamily = FontFamily.getFamily(str2); if (physicalFont != null) paramInt |= physicalFont.style;  if (fontFamily != null) { font2D = fontFamily.getFont(paramInt); if (font2D == null) font2D = fontFamily.getClosestStyle(paramInt);  }  return font2D; } public boolean registerFont(Font paramFont) { Hashtable<Object, Object> hashtable1, hashtable2; if (paramFont == null) {
/* 3006 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 3010 */     synchronized (regFamilyKey) {
/* 3011 */       if (this.createdByFamilyName == null) {
/* 3012 */         this.createdByFamilyName = new Hashtable<>();
/* 3013 */         this.createdByFullName = new Hashtable<>();
/*      */       } 
/*      */     } 
/*      */     
/* 3017 */     if (!FontAccess.getFontAccess().isCreatedFont(paramFont)) {
/* 3018 */       return false;
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
/* 3038 */     HashSet<String> hashSet = getInstalledNames();
/* 3039 */     Locale locale = getSystemStartupLocale();
/* 3040 */     String str1 = paramFont.getFamily(locale).toLowerCase();
/* 3041 */     String str2 = paramFont.getFontName(locale).toLowerCase();
/* 3042 */     if (hashSet.contains(str1) || hashSet.contains(str2)) {
/* 3043 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3049 */     if (!maybeMultiAppContext()) {
/* 3050 */       Hashtable<String, FontFamily> hashtable = this.createdByFamilyName;
/* 3051 */       Hashtable<String, Font2D> hashtable3 = this.createdByFullName;
/* 3052 */       this.fontsAreRegistered = true;
/*      */     } else {
/* 3054 */       AppContext appContext = AppContext.getAppContext();
/*      */       
/* 3056 */       hashtable1 = (Hashtable)appContext.get(regFamilyKey);
/*      */       
/* 3058 */       hashtable2 = (Hashtable)appContext.get(regFullNameKey);
/* 3059 */       if (hashtable1 == null) {
/* 3060 */         hashtable1 = new Hashtable<>();
/* 3061 */         hashtable2 = new Hashtable<>();
/* 3062 */         appContext.put(regFamilyKey, hashtable1);
/* 3063 */         appContext.put(regFullNameKey, hashtable2);
/*      */       } 
/* 3065 */       this.fontsAreRegisteredPerAppContext = true;
/*      */     } 
/*      */     
/* 3068 */     Font2D font2D = FontUtilities.getFont2D(paramFont);
/* 3069 */     int i = font2D.getStyle();
/* 3070 */     FontFamily fontFamily = (FontFamily)hashtable1.get(str1);
/* 3071 */     if (fontFamily == null) {
/* 3072 */       fontFamily = new FontFamily(paramFont.getFamily(locale));
/* 3073 */       hashtable1.put(str1, fontFamily);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3081 */     if (this.fontsAreRegistered) {
/* 3082 */       removeFromCache(fontFamily.getFont(0));
/* 3083 */       removeFromCache(fontFamily.getFont(1));
/* 3084 */       removeFromCache(fontFamily.getFont(2));
/* 3085 */       removeFromCache(fontFamily.getFont(3));
/* 3086 */       removeFromCache((Font2D)hashtable2.get(str2));
/*      */     } 
/* 3088 */     fontFamily.setFont(font2D, i);
/* 3089 */     hashtable2.put(str2, font2D);
/* 3090 */     return true; } public Font2D findFont2D(String paramString, int paramInt1, int paramInt2) { String str1 = paramString.toLowerCase(Locale.ENGLISH); String str2 = str1 + dotStyleStr(paramInt1); if (this._usingPerAppContextComposites) { ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap)AppContext.getAppContext().get(CompositeFont.class); if (concurrentHashMap != null) { font2D = (Font2D)concurrentHashMap.get(str2); } else { font2D = null; }  } else { font2D = this.fontNameCache.get(str2); }  if (font2D != null) return font2D;  if (FontUtilities.isLogging()) FontUtilities.getLogger().info("Search for font: " + paramString);  if (FontUtilities.isWindows) if (str1.equals("ms sans serif")) { paramString = "sansserif"; } else if (str1.equals("ms serif")) { paramString = "serif"; }   if (str1.equals("default")) paramString = "dialog";  FontFamily fontFamily = FontFamily.getFamily(paramString); if (fontFamily != null) { font2D = fontFamily.getFontWithExactStyleMatch(paramInt1); if (font2D == null) font2D = findDeferredFont(paramString, paramInt1);  if (font2D == null) font2D = fontFamily.getFont(paramInt1);  if (font2D == null) font2D = fontFamily.getClosestStyle(paramInt1);  if (font2D != null) { this.fontNameCache.put(str2, font2D); return font2D; }  }  Font2D font2D = this.fullNameToFont.get(str1); if (font2D != null) { if (font2D.style == paramInt1 || paramInt1 == 0) { this.fontNameCache.put(str2, font2D); return font2D; }  fontFamily = FontFamily.getFamily(font2D.getFamilyName(null)); if (fontFamily != null) { Font2D font2D1 = fontFamily.getFont(paramInt1 | font2D.style); if (font2D1 != null) { this.fontNameCache.put(str2, font2D1); return font2D1; }  font2D1 = fontFamily.getClosestStyle(paramInt1 | font2D.style); if (font2D1 != null) if (font2D1.canDoStyle(paramInt1 | font2D.style)) { this.fontNameCache.put(str2, font2D1); return font2D1; }   }  }  if (FontUtilities.isWindows) { font2D = findFontFromPlatformMap(str1, paramInt1); if (FontUtilities.isLogging()) FontUtilities.getLogger().info("findFontFromPlatformMap returned " + font2D);  if (font2D != null) { this.fontNameCache.put(str2, font2D); return font2D; }  if (this.deferredFontFiles.size() > 0) { font2D = findJREDeferredFont(str1, paramInt1); if (font2D != null) { this.fontNameCache.put(str2, font2D); return font2D; }  }  font2D = findFontFromPlatform(str1, paramInt1); if (font2D != null) { if (FontUtilities.isLogging()) FontUtilities.getLogger().info("Found font via platform API for request:\"" + paramString + "\":, style=" + paramInt1 + " found font: " + font2D);  this.fontNameCache.put(str2, font2D); return font2D; }  }  if (this.deferredFontFiles.size() > 0) { font2D = findDeferredFont(paramString, paramInt1); if (font2D != null) { this.fontNameCache.put(str2, font2D); return font2D; }  }  if (FontUtilities.isSolaris && !this.loaded1dot0Fonts) { if (str1.equals("timesroman")) { font2D = findFont2D("serif", paramInt1, paramInt2); this.fontNameCache.put(str2, font2D); }  register1dot0Fonts(); this.loaded1dot0Fonts = true; return findFont2D(paramString, paramInt1, paramInt2); }  if (this.fontsAreRegistered || this.fontsAreRegisteredPerAppContext) { Hashtable hashtable1; Hashtable<String, FontFamily> hashtable = null; if (this.fontsAreRegistered) { hashtable = this.createdByFamilyName; hashtable1 = this.createdByFullName; } else { AppContext appContext = AppContext.getAppContext(); hashtable = (Hashtable<String, FontFamily>)appContext.get(regFamilyKey); hashtable1 = (Hashtable)appContext.get(regFullNameKey); }  fontFamily = hashtable.get(str1); if (fontFamily != null) { font2D = fontFamily.getFontWithExactStyleMatch(paramInt1); if (font2D == null) font2D = fontFamily.getFont(paramInt1);  if (font2D == null) font2D = fontFamily.getClosestStyle(paramInt1);  if (font2D != null) { if (this.fontsAreRegistered) this.fontNameCache.put(str2, font2D);  return font2D; }  }  font2D = (Font2D)hashtable1.get(str1); if (font2D != null) { if (this.fontsAreRegistered) this.fontNameCache.put(str2, font2D);  return font2D; }  }  if (!this.loadedAllFonts) { if (FontUtilities.isLogging()) FontUtilities.getLogger().info("Load fonts looking for:" + paramString);  loadFonts(); this.loadedAllFonts = true; return findFont2D(paramString, paramInt1, paramInt2); }  if (!this.loadedAllFontFiles) { if (FontUtilities.isLogging()) FontUtilities.getLogger().info("Load font files looking for:" + paramString);  loadFontFiles(); this.loadedAllFontFiles = true; return findFont2D(paramString, paramInt1, paramInt2); }  if ((font2D = findFont2DAllLocales(paramString, paramInt1)) != null) { this.fontNameCache.put(str2, font2D); return font2D; }  if (FontUtilities.isWindows) { String str = getFontConfiguration().getFallbackFamilyName(paramString, null); if (str != null) { font2D = findFont2D(str, paramInt1, paramInt2); this.fontNameCache.put(str2, font2D); return font2D; }  } else { if (str1.equals("timesroman")) { font2D = findFont2D("serif", paramInt1, paramInt2); this.fontNameCache.put(str2, font2D); return font2D; }  if (str1.equals("helvetica")) { font2D = findFont2D("sansserif", paramInt1, paramInt2); this.fontNameCache.put(str2, font2D); return font2D; }  if (str1.equals("courier")) { font2D = findFont2D("monospaced", paramInt1, paramInt2); this.fontNameCache.put(str2, font2D); return font2D; }  }  if (FontUtilities.isLogging()) FontUtilities.getLogger().info("No font found for:" + paramString);  switch (paramInt2) { case 1: return getDefaultPhysicalFont();case 2: return getDefaultLogicalFont(paramInt1); }  return null; } public boolean usePlatformFontMetrics() { return this.usePlatformFontMetrics; } public int getNumFonts() { return this.physicalFonts.size() + this.maxCompFont; } private static boolean fontSupportsEncoding(Font paramFont, String paramString) { return FontUtilities.getFont2D(paramFont).supportsEncoding(paramString); } public Font2D createFont2D(File paramFile, int paramInt, boolean paramBoolean, CreatedFontTracker paramCreatedFontTracker) throws FontFormatException { Type1Font type1Font; String str = paramFile.getPath(); TrueTypeFont trueTypeFont = null; final File fFile = paramFile; final CreatedFontTracker _tracker = paramCreatedFontTracker; try { switch (paramInt) { case 0: trueTypeFont = new TrueTypeFont(str, null, 0, true); break;case 1: type1Font = new Type1Font(str, null, paramBoolean); break;default: throw new FontFormatException("Unrecognised Font Format"); }  } catch (FontFormatException fontFormatException) { if (paramBoolean) AccessController.doPrivileged(new PrivilegedAction() { public Object run() { if (_tracker != null) _tracker.subBytes((int)fFile.length());  fFile.delete(); return null; } }
/*      */           );  throw fontFormatException; }  if (paramBoolean) { type1Font.setFileToRemove(paramFile, paramCreatedFontTracker); synchronized (FontManager.class) { if (this.tmpFontFiles == null) this.tmpFontFiles = new Vector<>();  this.tmpFontFiles.add(paramFile); if (this.fileCloser == null) { Runnable runnable = new Runnable() { public void run() { AccessController.doPrivileged(new PrivilegedAction() { public Object run() { for (byte b = 0; b < 20; b++) { if (SunFontManager.this.fontFileCache[b] != null) try { SunFontManager.this.fontFileCache[b].close(); } catch (Exception exception) {}  }  if (SunFontManager.this.tmpFontFiles != null) { File[] arrayOfFile = new File[SunFontManager.this.tmpFontFiles.size()]; arrayOfFile = SunFontManager.this.tmpFontFiles.<File>toArray(arrayOfFile); for (byte b1 = 0; b1 < arrayOfFile.length; b1++) { try { arrayOfFile[b1].delete(); } catch (Exception exception) {} }  }  return null; } }
/*      */                   ); } }
/*      */             ; AccessController.doPrivileged(() -> { ThreadGroup threadGroup = ThreadGroupUtils.getRootThreadGroup(); this.fileCloser = new Thread(threadGroup, paramRunnable); this.fileCloser.setContextClassLoader(null); Runtime.getRuntime().addShutdownHook(this.fileCloser); return null; }); }  }  }  return type1Font; } public synchronized String getFullNameByFileName(String paramString) { PhysicalFont[] arrayOfPhysicalFont = getPhysicalFonts(); for (byte b = 0; b < arrayOfPhysicalFont.length; b++) { if ((arrayOfPhysicalFont[b]).platName.equals(paramString)) return arrayOfPhysicalFont[b].getFontName(null);  }  return null; } public synchronized void deRegisterBadFont(Font2D paramFont2D) { if (!(paramFont2D instanceof PhysicalFont)) return;  if (FontUtilities.isLogging()) FontUtilities.getLogger().severe("Deregister bad font: " + paramFont2D);  replaceFont((PhysicalFont)paramFont2D, getDefaultPhysicalFont()); } public synchronized void replaceFont(PhysicalFont paramPhysicalFont1, PhysicalFont paramPhysicalFont2) { if (paramPhysicalFont1.handle.font2D != paramPhysicalFont1) return;  if (paramPhysicalFont1 == paramPhysicalFont2) { if (FontUtilities.isLogging()) FontUtilities.getLogger().severe("Can't replace bad font with itself " + paramPhysicalFont1);  PhysicalFont[] arrayOfPhysicalFont = getPhysicalFonts(); for (byte b1 = 0; b1 < arrayOfPhysicalFont.length; b1++) { if (arrayOfPhysicalFont[b1] != paramPhysicalFont2) { paramPhysicalFont2 = arrayOfPhysicalFont[b1]; break; }  }  if (paramPhysicalFont1 == paramPhysicalFont2) { if (FontUtilities.isLogging()) FontUtilities.getLogger().severe("This is bad. No good physicalFonts found.");  return; }  }  paramPhysicalFont1.handle.font2D = paramPhysicalFont2; this.physicalFonts.remove(paramPhysicalFont1.fullName); this.fullNameToFont.remove(paramPhysicalFont1.fullName.toLowerCase(Locale.ENGLISH)); FontFamily.remove(paramPhysicalFont1); if (this.localeFullNamesToFont != null) { Map.Entry[] arrayOfEntry = (Map.Entry[])this.localeFullNamesToFont.entrySet().toArray((Object[])new Map.Entry[0]); for (byte b1 = 0; b1 < arrayOfEntry.length; b1++) { if (arrayOfEntry[b1].getValue() == paramPhysicalFont1) try { arrayOfEntry[b1].setValue(paramPhysicalFont2); } catch (Exception exception) { this.localeFullNamesToFont.remove(arrayOfEntry[b1].getKey()); }   }  }  for (byte b = 0; b < this.maxCompFont; b++) { if (paramPhysicalFont2.getRank() > 2) this.compFonts[b].replaceComponentFont(paramPhysicalFont1, paramPhysicalFont2);  }  } private synchronized void loadLocaleNames() { if (this.localeFullNamesToFont != null) return;  this.localeFullNamesToFont = new HashMap<>(); Font2D[] arrayOfFont2D = getRegisteredFonts(); for (byte b = 0; b < arrayOfFont2D.length; b++) { if (arrayOfFont2D[b] instanceof TrueTypeFont) { TrueTypeFont trueTypeFont = (TrueTypeFont)arrayOfFont2D[b]; String[] arrayOfString = trueTypeFont.getAllFullNames(); for (byte b1 = 0; b1 < arrayOfString.length; b1++) this.localeFullNamesToFont.put(arrayOfString[b1], trueTypeFont);  FontFamily fontFamily = FontFamily.getFamily(trueTypeFont.familyName); if (fontFamily != null) FontFamily.addLocaleNames(fontFamily, trueTypeFont.getAllFamilyNames());  }  }  } private Font2D findFont2DAllLocales(String paramString, int paramInt) { if (FontUtilities.isLogging()) FontUtilities.getLogger().info("Searching localised font names for:" + paramString);  if (this.localeFullNamesToFont == null) loadLocaleNames();  String str = paramString.toLowerCase(); Font2D font2D = null; FontFamily fontFamily = FontFamily.getLocaleFamily(str); if (fontFamily != null) { font2D = fontFamily.getFont(paramInt); if (font2D == null) font2D = fontFamily.getClosestStyle(paramInt);  if (font2D != null) return font2D;  }  synchronized (this) { font2D = this.localeFullNamesToFont.get(paramString); }  if (font2D != null) { if (font2D.style == paramInt || paramInt == 0) return font2D;  fontFamily = FontFamily.getFamily(font2D.getFamilyName(null)); if (fontFamily != null) { Font2D font2D1 = fontFamily.getFont(paramInt); if (font2D1 != null) return font2D1;  font2D1 = fontFamily.getClosestStyle(paramInt); if (font2D1 != null) { if (!font2D1.canDoStyle(paramInt)) font2D1 = null;  return font2D1; }  }  }  return font2D; } private static final Object altJAFontKey = new Object(); private static final Object localeFontKey = new Object(); private static final Object proportionalFontKey = new Object(); private boolean _usingPerAppContextComposites; private boolean _usingAlternateComposites; private static boolean gAltJAFont = false; private boolean gLocalePref; private boolean gPropPref; public boolean maybeUsingAlternateCompositeFonts() { return (this._usingAlternateComposites || this._usingPerAppContextComposites); } public boolean usingAlternateCompositeFonts() { if (!this._usingAlternateComposites) { if (this._usingPerAppContextComposites) if (AppContext.getAppContext().get(CompositeFont.class) != null);  return false; }  } private static boolean maybeMultiAppContext() { Boolean bool = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() { public Object run() { SecurityManager securityManager = System.getSecurityManager(); return new Boolean(securityManager instanceof sun.applet.AppletSecurity); } }
/*      */       ); return bool.booleanValue(); } public synchronized void useAlternateFontforJALocales() { if (FontUtilities.isLogging()) FontUtilities.getLogger().info("Entered useAlternateFontforJALocales().");  if (!FontUtilities.isWindows) return;  if (!maybeMultiAppContext()) { gAltJAFont = true; } else { AppContext appContext = AppContext.getAppContext(); appContext.put(altJAFontKey, altJAFontKey); }  } public boolean usingAlternateFontforJALocales() { if (!maybeMultiAppContext()) return gAltJAFont;  AppContext appContext = AppContext.getAppContext(); return (appContext.get(altJAFontKey) == altJAFontKey); } public synchronized void preferLocaleFonts() { if (FontUtilities.isLogging()) FontUtilities.getLogger().info("Entered preferLocaleFonts().");  if (!FontConfiguration.willReorderForStartupLocale()) return;  if (!maybeMultiAppContext()) { if (this.gLocalePref == true) return;  this.gLocalePref = true; createCompositeFonts(this.fontNameCache, this.gLocalePref, this.gPropPref); this._usingAlternateComposites = true; } else { AppContext appContext = AppContext.getAppContext(); if (appContext.get(localeFontKey) == localeFontKey) return;  appContext.put(localeFontKey, localeFontKey); boolean bool = (appContext.get(proportionalFontKey) == proportionalFontKey) ? true : false; ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>(); appContext.put(CompositeFont.class, concurrentHashMap); this._usingPerAppContextComposites = true; createCompositeFonts((ConcurrentHashMap)concurrentHashMap, true, bool); }  } public synchronized void preferProportionalFonts() { if (FontUtilities.isLogging()) FontUtilities.getLogger().info("Entered preferProportionalFonts().");  if (!FontConfiguration.hasMonoToPropMap()) return;  if (!maybeMultiAppContext()) { if (this.gPropPref == true) return;  this.gPropPref = true; createCompositeFonts(this.fontNameCache, this.gLocalePref, this.gPropPref); this._usingAlternateComposites = true; } else { AppContext appContext = AppContext.getAppContext(); if (appContext.get(proportionalFontKey) == proportionalFontKey) return;  appContext.put(proportionalFontKey, proportionalFontKey); boolean bool = (appContext.get(localeFontKey) == localeFontKey) ? true : false; ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>(); appContext.put(CompositeFont.class, concurrentHashMap); this._usingPerAppContextComposites = true; createCompositeFonts((ConcurrentHashMap)concurrentHashMap, bool, true); }  } private static HashSet<String> installedNames = null; private static HashSet<String> getInstalledNames() { if (installedNames == null) { Locale locale = getSystemStartupLocale(); SunFontManager sunFontManager = getInstance(); String[] arrayOfString = sunFontManager.getInstalledFontFamilyNames(locale); Font[] arrayOfFont = sunFontManager.getAllInstalledFonts(); HashSet<String> hashSet = new HashSet(); byte b; for (b = 0; b < arrayOfString.length; b++) hashSet.add(arrayOfString[b].toLowerCase(locale));  for (b = 0; b < arrayOfFont.length; b++) hashSet.add(arrayOfFont[b].getFontName(locale).toLowerCase(locale));  installedNames = hashSet; }  return installedNames; } private static final Object regFamilyKey = new Object(); private static final Object regFullNameKey = new Object(); private Hashtable<String, FontFamily> createdByFamilyName; private Hashtable<String, Font2D> createdByFullName; private boolean fontsAreRegistered; private boolean fontsAreRegisteredPerAppContext;
/* 3095 */   private void removeFromCache(Font2D paramFont2D) { if (paramFont2D == null) {
/*      */       return;
/*      */     }
/* 3098 */     String[] arrayOfString = (String[])this.fontNameCache.keySet().toArray((Object[])STR_ARRAY);
/* 3099 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 3100 */       if (this.fontNameCache.get(arrayOfString[b]) == paramFont2D) {
/* 3101 */         this.fontNameCache.remove(arrayOfString[b]);
/*      */       }
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreeMap<String, String> getCreatedFontFamilyNames() {
/*      */     Hashtable hashtable;
/* 3110 */     if (this.fontsAreRegistered) {
/* 3111 */       hashtable = this.createdByFamilyName;
/* 3112 */     } else if (this.fontsAreRegisteredPerAppContext) {
/* 3113 */       AppContext appContext = AppContext.getAppContext();
/*      */       
/* 3115 */       hashtable = (Hashtable)appContext.get(regFamilyKey);
/*      */     } else {
/* 3117 */       return null;
/*      */     } 
/*      */     
/* 3120 */     Locale locale = getSystemStartupLocale();
/* 3121 */     synchronized (hashtable) {
/* 3122 */       TreeMap<Object, Object> treeMap = new TreeMap<>();
/* 3123 */       for (FontFamily fontFamily : hashtable.values()) {
/* 3124 */         Font2D font2D = fontFamily.getFont(0);
/* 3125 */         if (font2D == null) {
/* 3126 */           font2D = fontFamily.getClosestStyle(0);
/*      */         }
/* 3128 */         String str = font2D.getFamilyName(locale);
/* 3129 */         treeMap.put(str.toLowerCase(locale), str);
/*      */       } 
/* 3131 */       return (TreeMap)treeMap;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Font[] getCreatedFonts() {
/*      */     Hashtable hashtable;
/* 3138 */     if (this.fontsAreRegistered) {
/* 3139 */       hashtable = this.createdByFullName;
/* 3140 */     } else if (this.fontsAreRegisteredPerAppContext) {
/* 3141 */       AppContext appContext = AppContext.getAppContext();
/*      */       
/* 3143 */       hashtable = (Hashtable)appContext.get(regFullNameKey);
/*      */     } else {
/* 3145 */       return null;
/*      */     } 
/*      */     
/* 3148 */     Locale locale = getSystemStartupLocale();
/* 3149 */     synchronized (hashtable) {
/* 3150 */       Font[] arrayOfFont = new Font[hashtable.size()];
/* 3151 */       byte b = 0;
/* 3152 */       for (Font2D font2D : hashtable.values()) {
/* 3153 */         arrayOfFont[b++] = new Font(font2D.getFontName(locale), 0, 1);
/*      */       }
/* 3155 */       return arrayOfFont;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String[] getPlatformFontDirs(boolean paramBoolean) {
/* 3163 */     if (this.pathDirs != null) {
/* 3164 */       return this.pathDirs;
/*      */     }
/*      */     
/* 3167 */     String str = getPlatformFontPath(paramBoolean);
/* 3168 */     StringTokenizer stringTokenizer = new StringTokenizer(str, File.pathSeparator);
/*      */     
/* 3170 */     ArrayList<String> arrayList = new ArrayList();
/*      */     try {
/* 3172 */       while (stringTokenizer.hasMoreTokens()) {
/* 3173 */         arrayList.add(stringTokenizer.nextToken());
/*      */       }
/* 3175 */     } catch (NoSuchElementException noSuchElementException) {}
/*      */     
/* 3177 */     this.pathDirs = arrayList.<String>toArray(new String[0]);
/* 3178 */     return this.pathDirs;
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
/*      */   private void addDirFonts(String paramString, File paramFile, FilenameFilter paramFilenameFilter, int paramInt1, boolean paramBoolean1, int paramInt2, boolean paramBoolean2, boolean paramBoolean3) {
/* 3197 */     String[] arrayOfString1 = paramFile.list(paramFilenameFilter);
/* 3198 */     if (arrayOfString1 == null || arrayOfString1.length == 0) {
/*      */       return;
/*      */     }
/* 3201 */     String[] arrayOfString2 = new String[arrayOfString1.length];
/* 3202 */     String[][] arrayOfString = new String[arrayOfString1.length][];
/* 3203 */     byte b1 = 0;
/*      */     
/* 3205 */     for (byte b2 = 0; b2 < arrayOfString1.length; b2++) {
/* 3206 */       File file = new File(paramFile, arrayOfString1[b2]);
/* 3207 */       String str = null;
/* 3208 */       if (paramBoolean3) {
/*      */         try {
/* 3210 */           str = file.getCanonicalPath();
/* 3211 */         } catch (IOException iOException) {}
/*      */       }
/*      */       
/* 3214 */       if (str == null) {
/* 3215 */         str = paramString + File.separator + arrayOfString1[b2];
/*      */       }
/*      */ 
/*      */       
/* 3219 */       if (!this.registeredFontFiles.contains(str))
/*      */       {
/*      */ 
/*      */         
/* 3223 */         if (this.badFonts != null && this.badFonts.contains(str)) {
/* 3224 */           if (FontUtilities.debugFonts()) {
/* 3225 */             FontUtilities.getLogger()
/* 3226 */               .warning("skip bad font " + str);
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/* 3231 */           this.registeredFontFiles.add(str);
/*      */           
/* 3233 */           if (FontUtilities.debugFonts() && 
/* 3234 */             FontUtilities.getLogger().isLoggable(PlatformLogger.Level.INFO)) {
/* 3235 */             String str1 = "Registering font " + str;
/* 3236 */             String[] arrayOfString3 = getNativeNames(str, null);
/* 3237 */             if (arrayOfString3 == null) {
/* 3238 */               str1 = str1 + " with no native name";
/*      */             } else {
/* 3240 */               str1 = str1 + " with native name(s) " + arrayOfString3[0];
/* 3241 */               for (byte b = 1; b < arrayOfString3.length; b++) {
/* 3242 */                 str1 = str1 + ", " + arrayOfString3[b];
/*      */               }
/*      */             } 
/* 3245 */             FontUtilities.getLogger().info(str1);
/*      */           } 
/* 3247 */           arrayOfString2[b1] = str;
/* 3248 */           arrayOfString[b1++] = getNativeNames(str, null);
/*      */         }  } 
/* 3250 */     }  registerFonts(arrayOfString2, arrayOfString, b1, paramInt1, paramBoolean1, paramInt2, paramBoolean2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String[] getNativeNames(String paramString1, String paramString2) {
/* 3257 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getFileNameFromPlatformName(String paramString) {
/* 3267 */     return this.fontConfig.getFileNameFromPlatformName(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FontConfiguration getFontConfiguration() {
/* 3274 */     return this.fontConfig;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPlatformFontPath(boolean paramBoolean) {
/* 3281 */     if (this.fontPath == null) {
/* 3282 */       this.fontPath = getFontPath(paramBoolean);
/*      */     }
/* 3284 */     return this.fontPath;
/*      */   }
/*      */   
/*      */   public static boolean isOpenJDK() {
/* 3288 */     return FontUtilities.isOpenJDK;
/*      */   }
/*      */   
/*      */   protected void loadFonts() {
/* 3292 */     if (this.discoveredAllFonts) {
/*      */       return;
/*      */     }
/*      */     
/* 3296 */     synchronized (this) {
/* 3297 */       if (FontUtilities.debugFonts()) {
/* 3298 */         Thread.dumpStack();
/* 3299 */         FontUtilities.getLogger()
/* 3300 */           .info("SunGraphicsEnvironment.loadFonts() called");
/*      */       } 
/* 3302 */       initialiseDeferredFonts();
/*      */       
/* 3304 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */           {
/*      */             public Object run() {
/* 3307 */               if (SunFontManager.this.fontPath == null) {
/* 3308 */                 SunFontManager.this.fontPath = SunFontManager.this.getPlatformFontPath(SunFontManager.noType1Font);
/* 3309 */                 SunFontManager.this.registerFontDirs(SunFontManager.this.fontPath);
/*      */               } 
/* 3311 */               if (SunFontManager.this.fontPath != null)
/*      */               {
/*      */ 
/*      */                 
/* 3315 */                 if (!SunFontManager.this.gotFontsFromPlatform()) {
/* 3316 */                   SunFontManager.this.registerFontsOnPath(SunFontManager.this.fontPath, false, 6, false, true);
/*      */ 
/*      */                   
/* 3319 */                   SunFontManager.this.loadedAllFontFiles = true;
/*      */                 } 
/*      */               }
/* 3322 */               SunFontManager.this.registerOtherFontFiles(SunFontManager.this.registeredFontFiles);
/* 3323 */               SunFontManager.this.discoveredAllFonts = true;
/* 3324 */               return null;
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void registerFontDirs(String paramString) {}
/*      */ 
/*      */ 
/*      */   
/*      */   private void registerFontsOnPath(String paramString, boolean paramBoolean1, int paramInt, boolean paramBoolean2, boolean paramBoolean3) {
/* 3338 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, File.pathSeparator);
/*      */     
/*      */     try {
/* 3341 */       while (stringTokenizer.hasMoreTokens()) {
/* 3342 */         registerFontsInDir(stringTokenizer.nextToken(), paramBoolean1, paramInt, paramBoolean2, paramBoolean3);
/*      */       
/*      */       }
/*      */     }
/* 3346 */     catch (NoSuchElementException noSuchElementException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerFontsInDir(String paramString) {
/* 3352 */     registerFontsInDir(paramString, true, 2, true, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void registerFontsInDir(String paramString, boolean paramBoolean1, int paramInt, boolean paramBoolean2, boolean paramBoolean3) {
/* 3360 */     File file = new File(paramString);
/* 3361 */     addDirFonts(paramString, file, ttFilter, 0, paramBoolean1, (paramInt == 6) ? 3 : paramInt, paramBoolean2, paramBoolean3);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3366 */     addDirFonts(paramString, file, t1Filter, 1, paramBoolean1, (paramInt == 6) ? 4 : paramInt, paramBoolean2, paramBoolean3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void registerFontDir(String paramString) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized String getDefaultFontFile() {
/* 3381 */     if (this.defaultFontFileName == null) {
/* 3382 */       initDefaultFonts();
/*      */     }
/* 3384 */     return this.defaultFontFileName;
/*      */   }
/*      */   
/*      */   private void initDefaultFonts() {
/* 3388 */     if (!isOpenJDK()) {
/* 3389 */       this.defaultFontName = "Lucida Sans Regular";
/* 3390 */       if (useAbsoluteFontFileNames()) {
/* 3391 */         this.defaultFontFileName = jreFontDirName + File.separator + "LucidaSansRegular.ttf";
/*      */       } else {
/*      */         
/* 3394 */         this.defaultFontFileName = "LucidaSansRegular.ttf";
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean useAbsoluteFontFileNames() {
/* 3404 */     return true;
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
/*      */   public synchronized String getDefaultFontFaceName() {
/* 3422 */     if (this.defaultFontName == null) {
/* 3423 */       initDefaultFonts();
/*      */     }
/* 3425 */     return this.defaultFontName;
/*      */   }
/*      */   
/*      */   public void loadFontFiles() {
/* 3429 */     loadFonts();
/* 3430 */     if (this.loadedAllFontFiles) {
/*      */       return;
/*      */     }
/*      */     
/* 3434 */     synchronized (this) {
/* 3435 */       if (FontUtilities.debugFonts()) {
/* 3436 */         Thread.dumpStack();
/* 3437 */         FontUtilities.getLogger().info("loadAllFontFiles() called");
/*      */       } 
/* 3439 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */           {
/*      */             public Object run() {
/* 3442 */               if (SunFontManager.this.fontPath == null) {
/* 3443 */                 SunFontManager.this.fontPath = SunFontManager.this.getPlatformFontPath(SunFontManager.noType1Font);
/*      */               }
/* 3445 */               if (SunFontManager.this.fontPath != null)
/*      */               {
/*      */ 
/*      */                 
/* 3449 */                 SunFontManager.this.registerFontsOnPath(SunFontManager.this.fontPath, false, 6, false, true);
/*      */               }
/*      */ 
/*      */               
/* 3453 */               SunFontManager.this.loadedAllFontFiles = true;
/* 3454 */               return null;
/*      */             }
/*      */           });
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
/*      */ 
/*      */ 
/*      */   
/*      */   private void initCompositeFonts(FontConfiguration paramFontConfiguration, ConcurrentHashMap<String, Font2D> paramConcurrentHashMap) {
/* 3471 */     if (FontUtilities.isLogging()) {
/* 3472 */       FontUtilities.getLogger()
/* 3473 */         .info("Initialising composite fonts");
/*      */     }
/*      */     
/* 3476 */     int i = paramFontConfiguration.getNumberCoreFonts();
/* 3477 */     String[] arrayOfString = paramFontConfiguration.getPlatformFontNames();
/* 3478 */     for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/* 3479 */       String str1 = arrayOfString[b1];
/*      */       
/* 3481 */       String str2 = getFileNameFromPlatformName(str1);
/* 3482 */       String[] arrayOfString1 = null;
/* 3483 */       if (str2 == null || str2
/* 3484 */         .equals(str1)) {
/*      */ 
/*      */ 
/*      */         
/* 3488 */         str2 = str1;
/*      */       } else {
/* 3490 */         if (b1 < i)
/*      */         {
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
/* 3505 */           addFontToPlatformFontPath(str1);
/*      */         }
/* 3507 */         arrayOfString1 = getNativeNames(str2, str1);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3516 */       registerFontFile(str2, arrayOfString1, 2, true);
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
/* 3528 */     registerPlatformFontsUsedByFontConfiguration();
/*      */ 
/*      */     
/* 3531 */     CompositeFontDescriptor[] arrayOfCompositeFontDescriptor = paramFontConfiguration.get2DCompositeFontInfo();
/* 3532 */     for (byte b2 = 0; b2 < arrayOfCompositeFontDescriptor.length; b2++) {
/* 3533 */       CompositeFontDescriptor compositeFontDescriptor = arrayOfCompositeFontDescriptor[b2];
/* 3534 */       String[] arrayOfString1 = compositeFontDescriptor.getComponentFileNames();
/* 3535 */       String[] arrayOfString2 = compositeFontDescriptor.getComponentFaceNames();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3540 */       if (missingFontFiles != null) {
/* 3541 */         for (byte b = 0; b < arrayOfString1.length; b++) {
/* 3542 */           if (missingFontFiles.contains(arrayOfString1[b])) {
/* 3543 */             arrayOfString1[b] = getDefaultFontFile();
/* 3544 */             arrayOfString2[b] = getDefaultFontFaceName();
/*      */           } 
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3555 */       if (paramConcurrentHashMap != null) {
/* 3556 */         registerCompositeFont(compositeFontDescriptor
/* 3557 */             .getFaceName(), arrayOfString1, arrayOfString2, compositeFontDescriptor
/*      */             
/* 3559 */             .getCoreComponentCount(), compositeFontDescriptor
/* 3560 */             .getExclusionRanges(), compositeFontDescriptor
/* 3561 */             .getExclusionRangeLimits(), true, paramConcurrentHashMap);
/*      */       }
/*      */       else {
/*      */         
/* 3565 */         registerCompositeFont(compositeFontDescriptor.getFaceName(), arrayOfString1, arrayOfString2, compositeFontDescriptor
/*      */             
/* 3567 */             .getCoreComponentCount(), compositeFontDescriptor
/* 3568 */             .getExclusionRanges(), compositeFontDescriptor
/* 3569 */             .getExclusionRangeLimits(), true);
/*      */       } 
/*      */       
/* 3572 */       if (FontUtilities.debugFonts()) {
/* 3573 */         FontUtilities.getLogger()
/* 3574 */           .info("registered " + compositeFontDescriptor.getFaceName());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addFontToPlatformFontPath(String paramString) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void registerFontFile(String paramString, String[] paramArrayOfString, int paramInt, boolean paramBoolean) {
/*      */     byte b;
/* 3590 */     if (this.registeredFontFiles.contains(paramString)) {
/*      */       return;
/*      */     }
/*      */     
/* 3594 */     if (ttFilter.accept(null, paramString)) {
/* 3595 */       b = 0;
/* 3596 */     } else if (t1Filter.accept(null, paramString)) {
/* 3597 */       b = 1;
/*      */     } else {
/* 3599 */       b = 5;
/*      */     } 
/* 3601 */     this.registeredFontFiles.add(paramString);
/* 3602 */     if (paramBoolean) {
/* 3603 */       registerDeferredFont(paramString, paramString, paramArrayOfString, b, false, paramInt);
/*      */     } else {
/*      */       
/* 3606 */       registerFontFile(paramString, paramArrayOfString, b, false, paramInt);
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
/*      */   
/*      */   protected void registerPlatformFontsUsedByFontConfiguration() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addToMissingFontFileList(String paramString) {
/* 3629 */     if (missingFontFiles == null) {
/* 3630 */       missingFontFiles = new HashSet<>();
/*      */     }
/* 3632 */     missingFontFiles.add(paramString);
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
/*      */   private boolean isNameForRegisteredFile(String paramString) {
/* 3654 */     String str = getFileNameForFontName(paramString);
/* 3655 */     if (str == null) {
/* 3656 */       return false;
/*      */     }
/* 3658 */     return this.registeredFontFiles.contains(str);
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
/*      */   public void createCompositeFonts(ConcurrentHashMap<String, Font2D> paramConcurrentHashMap, boolean paramBoolean1, boolean paramBoolean2) {
/* 3672 */     FontConfiguration fontConfiguration = createFontConfiguration(paramBoolean1, paramBoolean2);
/* 3673 */     initCompositeFonts(fontConfiguration, paramConcurrentHashMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font[] getAllInstalledFonts() {
/* 3680 */     if (this.allFonts == null) {
/* 3681 */       loadFonts();
/* 3682 */       TreeMap<Object, Object> treeMap = new TreeMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3687 */       Font2D[] arrayOfFont2D = getRegisteredFonts();
/* 3688 */       for (byte b1 = 0; b1 < arrayOfFont2D.length; b1++) {
/* 3689 */         if (!(arrayOfFont2D[b1] instanceof NativeFont)) {
/* 3690 */           treeMap.put(arrayOfFont2D[b1].getFontName(null), arrayOfFont2D[b1]);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 3695 */       String[] arrayOfString1 = getFontNamesFromPlatform();
/* 3696 */       if (arrayOfString1 != null) {
/* 3697 */         for (byte b = 0; b < arrayOfString1.length; b++) {
/* 3698 */           if (!isNameForRegisteredFile(arrayOfString1[b])) {
/* 3699 */             treeMap.put(arrayOfString1[b], null);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/* 3704 */       String[] arrayOfString2 = null;
/* 3705 */       if (treeMap.size() > 0) {
/* 3706 */         arrayOfString2 = new String[treeMap.size()];
/* 3707 */         Object[] arrayOfObject = treeMap.keySet().toArray();
/* 3708 */         for (byte b = 0; b < arrayOfObject.length; b++) {
/* 3709 */           arrayOfString2[b] = (String)arrayOfObject[b];
/*      */         }
/*      */       } 
/* 3712 */       Font[] arrayOfFont1 = new Font[arrayOfString2.length];
/* 3713 */       for (byte b2 = 0; b2 < arrayOfString2.length; b2++) {
/* 3714 */         arrayOfFont1[b2] = new Font(arrayOfString2[b2], 0, 1);
/* 3715 */         Font2D font2D = (Font2D)treeMap.get(arrayOfString2[b2]);
/* 3716 */         if (font2D != null) {
/* 3717 */           FontAccess.getFontAccess().setFont2D(arrayOfFont1[b2], font2D.handle);
/*      */         }
/*      */       } 
/* 3720 */       this.allFonts = arrayOfFont1;
/*      */     } 
/*      */     
/* 3723 */     Font[] arrayOfFont = new Font[this.allFonts.length];
/* 3724 */     System.arraycopy(this.allFonts, 0, arrayOfFont, 0, this.allFonts.length);
/* 3725 */     return arrayOfFont;
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
/*      */   public String[] getInstalledFontFamilyNames(Locale paramLocale) {
/* 3737 */     if (paramLocale == null) {
/* 3738 */       paramLocale = Locale.getDefault();
/*      */     }
/* 3740 */     if (this.allFamilies != null && this.lastDefaultLocale != null && paramLocale
/* 3741 */       .equals(this.lastDefaultLocale)) {
/* 3742 */       String[] arrayOfString1 = new String[this.allFamilies.length];
/* 3743 */       System.arraycopy(this.allFamilies, 0, arrayOfString1, 0, this.allFamilies.length);
/*      */       
/* 3745 */       return arrayOfString1;
/*      */     } 
/*      */     
/* 3748 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/*      */ 
/*      */     
/* 3751 */     String str = "Serif"; treeMap.put(str.toLowerCase(), str);
/* 3752 */     str = "SansSerif"; treeMap.put(str.toLowerCase(), str);
/* 3753 */     str = "Monospaced"; treeMap.put(str.toLowerCase(), str);
/* 3754 */     str = "Dialog"; treeMap.put(str.toLowerCase(), str);
/* 3755 */     str = "DialogInput"; treeMap.put(str.toLowerCase(), str);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3761 */     if (paramLocale.equals(getSystemStartupLocale()) && 
/* 3762 */       getFamilyNamesFromPlatform((TreeMap)treeMap, paramLocale)) {
/*      */       
/* 3764 */       getJREFontFamilyNames((TreeMap)treeMap, paramLocale);
/*      */     } else {
/* 3766 */       loadFontFiles();
/* 3767 */       PhysicalFont[] arrayOfPhysicalFont = getPhysicalFonts();
/* 3768 */       for (byte b1 = 0; b1 < arrayOfPhysicalFont.length; b1++) {
/* 3769 */         if (!(arrayOfPhysicalFont[b1] instanceof NativeFont)) {
/*      */           
/* 3771 */           String str1 = arrayOfPhysicalFont[b1].getFamilyName(paramLocale);
/* 3772 */           treeMap.put(str1.toLowerCase(paramLocale), str1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3778 */     addNativeFontFamilyNames((TreeMap)treeMap, paramLocale);
/*      */     
/* 3780 */     String[] arrayOfString = new String[treeMap.size()];
/* 3781 */     Object[] arrayOfObject = treeMap.keySet().toArray();
/* 3782 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/* 3783 */       arrayOfString[b] = (String)treeMap.get(arrayOfObject[b]);
/*      */     }
/* 3785 */     if (paramLocale.equals(Locale.getDefault())) {
/* 3786 */       this.lastDefaultLocale = paramLocale;
/* 3787 */       this.allFamilies = new String[arrayOfString.length];
/* 3788 */       System.arraycopy(arrayOfString, 0, this.allFamilies, 0, this.allFamilies.length);
/*      */     } 
/* 3790 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addNativeFontFamilyNames(TreeMap<String, String> paramTreeMap, Locale paramLocale) {}
/*      */   
/*      */   public void register1dot0Fonts() {
/* 3797 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Object run() {
/* 3800 */             String str = "/usr/openwin/lib/X11/fonts/Type1";
/* 3801 */             SunFontManager.this.registerFontsInDir(str, true, 4, false, false);
/*      */             
/* 3803 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void getJREFontFamilyNames(TreeMap<String, String> paramTreeMap, Locale paramLocale) {
/* 3814 */     registerDeferredJREFonts(jreFontDirName);
/* 3815 */     PhysicalFont[] arrayOfPhysicalFont = getPhysicalFonts();
/* 3816 */     for (byte b = 0; b < arrayOfPhysicalFont.length; b++) {
/* 3817 */       if (!(arrayOfPhysicalFont[b] instanceof NativeFont)) {
/*      */         
/* 3819 */         String str = arrayOfPhysicalFont[b].getFamilyName(paramLocale);
/* 3820 */         paramTreeMap.put(str.toLowerCase(paramLocale), str);
/*      */       } 
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
/*      */ 
/*      */ 
/*      */   
/* 3835 */   private static Locale systemLocale = null;
/*      */   private static Locale getSystemStartupLocale() {
/* 3837 */     if (systemLocale == null)
/*      */     {
/* 3839 */       systemLocale = AccessController.<Locale>doPrivileged(new PrivilegedAction<Locale>()
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             public Object run()
/*      */             {
/* 3852 */               String str1 = System.getProperty("file.encoding", "");
/* 3853 */               String str2 = System.getProperty("sun.jnu.encoding");
/* 3854 */               if (str2 != null && !str2.equals(str1)) {
/* 3855 */                 return Locale.ROOT;
/*      */               }
/*      */               
/* 3858 */               String str3 = System.getProperty("user.language", "en");
/* 3859 */               String str4 = System.getProperty("user.country", "");
/* 3860 */               String str5 = System.getProperty("user.variant", "");
/* 3861 */               return new Locale(str3, str4, str5);
/*      */             }
/*      */           });
/*      */     }
/* 3865 */     return systemLocale;
/*      */   }
/*      */ 
/*      */   
/*      */   void addToPool(FileFont paramFileFont) {
/* 3870 */     FileFont fileFont = null;
/* 3871 */     byte b = -1;
/*      */     
/* 3873 */     synchronized (this.fontFileCache) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3881 */       for (byte b1 = 0; b1 < 20; b1++) {
/* 3882 */         if (this.fontFileCache[b1] == paramFileFont) {
/*      */           return;
/*      */         }
/* 3885 */         if (this.fontFileCache[b1] == null && b < 0) {
/* 3886 */           b = b1;
/*      */         }
/*      */       } 
/* 3889 */       if (b >= 0) {
/* 3890 */         this.fontFileCache[b] = paramFileFont;
/*      */         
/*      */         return;
/*      */       } 
/* 3894 */       fileFont = this.fontFileCache[this.lastPoolIndex];
/* 3895 */       this.fontFileCache[this.lastPoolIndex] = paramFileFont;
/*      */ 
/*      */ 
/*      */       
/* 3899 */       this.lastPoolIndex = (this.lastPoolIndex + 1) % 20;
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
/* 3912 */     if (fileFont != null) {
/* 3913 */       fileFont.close();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected FontUIResource getFontConfigFUIR(String paramString, int paramInt1, int paramInt2) {
/* 3920 */     return new FontUIResource(paramString, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   protected abstract String getFontPath(boolean paramBoolean);
/*      */   
/*      */   protected abstract String[] getDefaultPlatformFont();
/*      */   
/*      */   protected abstract FontConfiguration createFontConfiguration();
/*      */   
/*      */   public abstract FontConfiguration createFontConfiguration(boolean paramBoolean1, boolean paramBoolean2);
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/SunFontManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */