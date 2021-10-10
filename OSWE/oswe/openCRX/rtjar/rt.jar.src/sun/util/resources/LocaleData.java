/*     */ package sun.util.resources;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocaleData
/*     */ {
/*     */   private final LocaleProviderAdapter.Type type;
/*     */   
/*     */   public LocaleData(LocaleProviderAdapter.Type paramType) {
/*  71 */     this.type = paramType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceBundle getCalendarData(Locale paramLocale) {
/*  79 */     return getBundle(this.type.getUtilResourcesPackage() + ".CalendarData", paramLocale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenListResourceBundle getCurrencyNames(Locale paramLocale) {
/*  87 */     return (OpenListResourceBundle)getBundle(this.type.getUtilResourcesPackage() + ".CurrencyNames", paramLocale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenListResourceBundle getLocaleNames(Locale paramLocale) {
/*  95 */     return (OpenListResourceBundle)getBundle(this.type.getUtilResourcesPackage() + ".LocaleNames", paramLocale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeZoneNamesBundle getTimeZoneNames(Locale paramLocale) {
/* 103 */     return (TimeZoneNamesBundle)getBundle(this.type.getUtilResourcesPackage() + ".TimeZoneNames", paramLocale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceBundle getBreakIteratorInfo(Locale paramLocale) {
/* 111 */     return getBundle(this.type.getTextResourcesPackage() + ".BreakIteratorInfo", paramLocale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceBundle getCollationData(Locale paramLocale) {
/* 119 */     return getBundle(this.type.getTextResourcesPackage() + ".CollationData", paramLocale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceBundle getDateFormatData(Locale paramLocale) {
/* 127 */     return getBundle(this.type.getTextResourcesPackage() + ".FormatData", paramLocale);
/*     */   }
/*     */   
/*     */   public void setSupplementary(ParallelListResourceBundle paramParallelListResourceBundle) {
/* 131 */     if (!paramParallelListResourceBundle.areParallelContentsComplete()) {
/* 132 */       String str = this.type.getTextResourcesPackage() + ".JavaTimeSupplementary";
/* 133 */       setSupplementary(str, paramParallelListResourceBundle);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean setSupplementary(String paramString, ParallelListResourceBundle paramParallelListResourceBundle) {
/* 138 */     ParallelListResourceBundle parallelListResourceBundle = (ParallelListResourceBundle)paramParallelListResourceBundle.getParent();
/* 139 */     boolean bool = false;
/* 140 */     if (parallelListResourceBundle != null) {
/* 141 */       bool = setSupplementary(paramString, parallelListResourceBundle);
/*     */     }
/* 143 */     OpenListResourceBundle openListResourceBundle = getSupplementary(paramString, paramParallelListResourceBundle.getLocale());
/* 144 */     paramParallelListResourceBundle.setParallelContents(openListResourceBundle);
/* 145 */     int i = bool | ((openListResourceBundle != null) ? 1 : 0);
/*     */ 
/*     */     
/* 148 */     if (i != 0) {
/* 149 */       paramParallelListResourceBundle.resetKeySet();
/*     */     }
/* 151 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceBundle getNumberFormatData(Locale paramLocale) {
/* 159 */     return getBundle(this.type.getTextResourcesPackage() + ".FormatData", paramLocale);
/*     */   }
/*     */   
/*     */   public static ResourceBundle getBundle(final String baseName, final Locale locale) {
/* 163 */     return AccessController.<ResourceBundle>doPrivileged(new PrivilegedAction<ResourceBundle>()
/*     */         {
/*     */           public ResourceBundle run() {
/* 166 */             return 
/* 167 */               ResourceBundle.getBundle(baseName, locale, LocaleData.LocaleDataResourceBundleControl.INSTANCE);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static OpenListResourceBundle getSupplementary(final String baseName, final Locale locale) {
/* 173 */     return AccessController.<OpenListResourceBundle>doPrivileged(new PrivilegedAction<OpenListResourceBundle>()
/*     */         {
/*     */           public OpenListResourceBundle run() {
/* 176 */             OpenListResourceBundle openListResourceBundle = null;
/*     */             try {
/* 178 */               openListResourceBundle = (OpenListResourceBundle)ResourceBundle.getBundle(baseName, locale, LocaleData.SupplementaryResourceBundleControl
/* 179 */                   .INSTANCE);
/*     */             }
/* 181 */             catch (MissingResourceException missingResourceException) {}
/*     */ 
/*     */             
/* 184 */             return openListResourceBundle;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static class LocaleDataResourceBundleControl
/*     */     extends ResourceBundle.Control {
/* 191 */     private static final LocaleDataResourceBundleControl INSTANCE = new LocaleDataResourceBundleControl();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final String DOTCLDR = ".cldr";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private LocaleDataResourceBundleControl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<Locale> getCandidateLocales(String param1String, Locale param1Locale) {
/* 209 */       List<Locale> list = super.getCandidateLocales(param1String, param1Locale);
/*     */       
/* 211 */       int i = param1String.lastIndexOf('.');
/* 212 */       String str = (i >= 0) ? param1String.substring(i + 1) : param1String;
/* 213 */       LocaleProviderAdapter.Type type = param1String.contains(".cldr") ? LocaleProviderAdapter.Type.CLDR : LocaleProviderAdapter.Type.JRE;
/* 214 */       LocaleProviderAdapter localeProviderAdapter = LocaleProviderAdapter.forType(type);
/* 215 */       Set<String> set = ((JRELocaleProviderAdapter)localeProviderAdapter).getLanguageTagSet(str);
/* 216 */       if (!set.isEmpty()) {
/* 217 */         for (Iterator<Locale> iterator = list.iterator(); iterator.hasNext();) {
/* 218 */           if (!LocaleProviderAdapter.isSupportedLocale(iterator.next(), type, set)) {
/* 219 */             iterator.remove();
/*     */           }
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 225 */       if (param1Locale.getLanguage() != "en" && type == LocaleProviderAdapter.Type.CLDR && str
/* 226 */         .equals("TimeZoneNames")) {
/* 227 */         list.add(list.size() - 1, Locale.ENGLISH);
/*     */       }
/* 229 */       return list;
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
/*     */     
/*     */     public Locale getFallbackLocale(String param1String, Locale param1Locale) {
/* 242 */       if (param1String == null || param1Locale == null) {
/* 243 */         throw new NullPointerException();
/*     */       }
/* 245 */       return null;
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
/*     */ 
/*     */ 
/*     */     
/*     */     public String toBundleName(String param1String, Locale param1Locale) {
/* 260 */       String str1 = param1String;
/* 261 */       String str2 = param1Locale.getLanguage();
/* 262 */       if (str2.length() > 0 && (
/* 263 */         param1String.startsWith(LocaleProviderAdapter.Type.JRE.getUtilResourcesPackage()) || param1String
/* 264 */         .startsWith(LocaleProviderAdapter.Type.JRE.getTextResourcesPackage()))) {
/*     */         
/* 266 */         assert LocaleProviderAdapter.Type.JRE.getUtilResourcesPackage().length() == LocaleProviderAdapter.Type.JRE
/* 267 */           .getTextResourcesPackage().length();
/* 268 */         int i = LocaleProviderAdapter.Type.JRE.getUtilResourcesPackage().length();
/* 269 */         if (param1String.indexOf(".cldr", i) > 0) {
/* 270 */           i += ".cldr".length();
/*     */         }
/*     */         
/* 273 */         str1 = param1String.substring(0, i + 1) + str2 + param1String.substring(i);
/*     */       } 
/*     */       
/* 276 */       return super.toBundleName(str1, param1Locale);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SupplementaryResourceBundleControl extends LocaleDataResourceBundleControl {
/* 281 */     private static final SupplementaryResourceBundleControl INSTANCE = new SupplementaryResourceBundleControl();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<Locale> getCandidateLocales(String param1String, Locale param1Locale) {
/* 290 */       return Arrays.asList(new Locale[] { param1Locale });
/*     */     }
/*     */ 
/*     */     
/*     */     public long getTimeToLive(String param1String, Locale param1Locale) {
/* 295 */       assert param1String.contains("JavaTimeSupplementary");
/* 296 */       return -1L;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/resources/LocaleData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */