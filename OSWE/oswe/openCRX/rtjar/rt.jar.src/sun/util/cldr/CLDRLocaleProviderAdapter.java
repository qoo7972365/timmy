/*     */ package sun.util.cldr;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.text.spi.BreakIteratorProvider;
/*     */ import java.text.spi.CollatorProvider;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.util.locale.provider.JRELocaleProviderAdapter;
/*     */ import sun.util.locale.provider.LocaleProviderAdapter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CLDRLocaleProviderAdapter
/*     */   extends JRELocaleProviderAdapter
/*     */ {
/*     */   private static final String LOCALE_DATA_JAR_NAME = "cldrdata.jar";
/*     */   
/*     */   public CLDRLocaleProviderAdapter() {
/*  53 */     String str1 = File.separator;
/*  54 */     String str2 = (String)AccessController.<String>doPrivileged(new GetPropertyAction("java.home")) + str1 + "lib" + str1 + "ext" + str1 + "cldrdata.jar";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     final File f = new File(str2);
/*  61 */     boolean bool = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */         {
/*     */           public Boolean run()
/*     */           {
/*  65 */             return Boolean.valueOf(f.exists());
/*     */           }
/*     */         })).booleanValue();
/*  68 */     if (!bool) {
/*  69 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocaleProviderAdapter.Type getAdapterType() {
/*  79 */     return LocaleProviderAdapter.Type.CLDR;
/*     */   }
/*     */ 
/*     */   
/*     */   public BreakIteratorProvider getBreakIteratorProvider() {
/*  84 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public CollatorProvider getCollatorProvider() {
/*  89 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Locale[] getAvailableLocales() {
/*  94 */     Set<String> set = createLanguageTagSet("All");
/*  95 */     Locale[] arrayOfLocale = new Locale[set.size()];
/*  96 */     byte b = 0;
/*  97 */     for (String str : set) {
/*  98 */       arrayOfLocale[b++] = Locale.forLanguageTag(str);
/*     */     }
/* 100 */     return arrayOfLocale;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<String> createLanguageTagSet(String paramString) {
/* 105 */     ResourceBundle resourceBundle = ResourceBundle.getBundle("sun.util.cldr.CLDRLocaleDataMetaInfo", Locale.ROOT);
/* 106 */     String str = resourceBundle.getString(paramString);
/* 107 */     if (str == null) {
/* 108 */       return Collections.emptySet();
/*     */     }
/* 110 */     HashSet<String> hashSet = new HashSet();
/* 111 */     StringTokenizer stringTokenizer = new StringTokenizer(str);
/* 112 */     while (stringTokenizer.hasMoreTokens()) {
/* 113 */       hashSet.add(stringTokenizer.nextToken());
/*     */     }
/* 115 */     return hashSet;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/cldr/CLDRLocaleProviderAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */