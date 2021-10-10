/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.text.spi.BreakIteratorProvider;
/*     */ import java.text.spi.CollatorProvider;
/*     */ import java.text.spi.DateFormatProvider;
/*     */ import java.text.spi.DateFormatSymbolsProvider;
/*     */ import java.text.spi.DecimalFormatSymbolsProvider;
/*     */ import java.text.spi.NumberFormatProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.spi.CalendarDataProvider;
/*     */ import java.util.spi.CalendarNameProvider;
/*     */ import java.util.spi.CurrencyNameProvider;
/*     */ import java.util.spi.LocaleNameProvider;
/*     */ import java.util.spi.LocaleServiceProvider;
/*     */ import java.util.spi.TimeZoneNameProvider;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.util.cldr.CLDRLocaleProviderAdapter;
/*     */ import sun.util.spi.CalendarProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LocaleProviderAdapter
/*     */ {
/*     */   private static final List<Type> adapterPreference;
/*     */   
/*     */   public enum Type
/*     */   {
/*  63 */     JRE("sun.util.resources", "sun.text.resources"),
/*  64 */     CLDR("sun.util.resources.cldr", "sun.text.resources.cldr"),
/*  65 */     SPI,
/*  66 */     HOST,
/*  67 */     FALLBACK("sun.util.resources", "sun.text.resources");
/*     */ 
/*     */     
/*     */     private final String UTIL_RESOURCES_PACKAGE;
/*     */ 
/*     */     
/*     */     private final String TEXT_RESOURCES_PACKAGE;
/*     */ 
/*     */     
/*     */     Type(String param1String1, String param1String2) {
/*  77 */       this.UTIL_RESOURCES_PACKAGE = param1String1;
/*  78 */       this.TEXT_RESOURCES_PACKAGE = param1String2;
/*     */     }
/*     */     
/*     */     public String getUtilResourcesPackage() {
/*  82 */       return this.UTIL_RESOURCES_PACKAGE;
/*     */     }
/*     */     
/*     */     public String getTextResourcesPackage() {
/*  86 */       return this.TEXT_RESOURCES_PACKAGE;
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
/*  99 */   private static LocaleProviderAdapter jreLocaleProviderAdapter = new JRELocaleProviderAdapter();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   private static LocaleProviderAdapter spiLocaleProviderAdapter = new SPILocaleProviderAdapter();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private static LocaleProviderAdapter cldrLocaleProviderAdapter = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   private static LocaleProviderAdapter hostLocaleProviderAdapter = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   private static LocaleProviderAdapter fallbackLocaleProviderAdapter = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   static Type defaultLocaleProviderAdapter = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   private static ConcurrentMap<Class<? extends LocaleServiceProvider>, ConcurrentMap<Locale, LocaleProviderAdapter>> adapterCache = new ConcurrentHashMap<>();
/*     */   
/*     */   static {
/* 135 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("java.locale.providers"));
/*     */     
/* 137 */     ArrayList<Type> arrayList = new ArrayList();
/*     */ 
/*     */     
/* 140 */     if (str != null && str.length() != 0) {
/* 141 */       String[] arrayOfString = str.split(",");
/* 142 */       for (String str1 : arrayOfString) {
/*     */         try {
/* 144 */           Type type = Type.valueOf(str1.trim().toUpperCase(Locale.ROOT));
/*     */ 
/*     */           
/* 147 */           switch (type) {
/*     */             case CLDR:
/* 149 */               if (cldrLocaleProviderAdapter == null) {
/* 150 */                 cldrLocaleProviderAdapter = new CLDRLocaleProviderAdapter();
/*     */               }
/*     */               break;
/*     */             case HOST:
/* 154 */               if (hostLocaleProviderAdapter == null) {
/* 155 */                 hostLocaleProviderAdapter = new HostLocaleProviderAdapter();
/*     */               }
/*     */               break;
/*     */           } 
/* 159 */           if (!arrayList.contains(type)) {
/* 160 */             arrayList.add(type);
/*     */           }
/* 162 */         } catch (IllegalArgumentException|UnsupportedOperationException illegalArgumentException) {
/*     */ 
/*     */           
/* 165 */           LocaleServiceProviderPool.config((Class)LocaleProviderAdapter.class, illegalArgumentException.toString());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 170 */     if (!arrayList.isEmpty()) {
/* 171 */       if (!arrayList.contains(Type.JRE)) {
/*     */         
/* 173 */         fallbackLocaleProviderAdapter = new FallbackLocaleProviderAdapter();
/* 174 */         arrayList.add(Type.FALLBACK);
/* 175 */         defaultLocaleProviderAdapter = Type.FALLBACK;
/*     */       } else {
/* 177 */         defaultLocaleProviderAdapter = Type.JRE;
/*     */       } 
/*     */     } else {
/*     */       
/* 181 */       arrayList.add(Type.JRE);
/* 182 */       arrayList.add(Type.SPI);
/* 183 */       defaultLocaleProviderAdapter = Type.JRE;
/*     */     } 
/*     */     
/* 186 */     adapterPreference = Collections.unmodifiableList(arrayList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LocaleProviderAdapter forType(Type paramType) {
/* 193 */     switch (paramType) {
/*     */       case JRE:
/* 195 */         return jreLocaleProviderAdapter;
/*     */       case CLDR:
/* 197 */         return cldrLocaleProviderAdapter;
/*     */       case SPI:
/* 199 */         return spiLocaleProviderAdapter;
/*     */       case HOST:
/* 201 */         return hostLocaleProviderAdapter;
/*     */       case FALLBACK:
/* 203 */         return fallbackLocaleProviderAdapter;
/*     */     } 
/* 205 */     throw new InternalError("unknown locale data adapter type");
/*     */   }
/*     */ 
/*     */   
/*     */   public static LocaleProviderAdapter forJRE() {
/* 210 */     return jreLocaleProviderAdapter;
/*     */   }
/*     */   
/*     */   public static LocaleProviderAdapter getResourceBundleBased() {
/* 214 */     for (Type type : getAdapterPreference()) {
/* 215 */       if (type == Type.JRE || type == Type.CLDR || type == Type.FALLBACK) {
/* 216 */         return forType(type);
/*     */       }
/*     */     } 
/*     */     
/* 220 */     throw new InternalError();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Type> getAdapterPreference() {
/* 226 */     return adapterPreference;
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
/*     */   public static LocaleProviderAdapter getAdapter(Class<? extends LocaleServiceProvider> paramClass, Locale paramLocale) {
/* 243 */     ConcurrentMap<Object, Object> concurrentMap = (ConcurrentMap)adapterCache.get(paramClass);
/* 244 */     if (concurrentMap != null) {
/* 245 */       LocaleProviderAdapter localeProviderAdapter1; if ((localeProviderAdapter1 = (LocaleProviderAdapter)concurrentMap.get(paramLocale)) != null) {
/* 246 */         return localeProviderAdapter1;
/*     */       }
/*     */     } else {
/* 249 */       concurrentMap = new ConcurrentHashMap<>();
/* 250 */       adapterCache.putIfAbsent(paramClass, concurrentMap);
/*     */     } 
/*     */ 
/*     */     
/* 254 */     LocaleProviderAdapter localeProviderAdapter = findAdapter(paramClass, paramLocale);
/* 255 */     if (localeProviderAdapter != null) {
/* 256 */       concurrentMap.putIfAbsent(paramLocale, localeProviderAdapter);
/* 257 */       return localeProviderAdapter;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 262 */     List<Locale> list = ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_DEFAULT).getCandidateLocales("", paramLocale);
/* 263 */     for (Locale locale : list) {
/* 264 */       if (locale.equals(paramLocale)) {
/*     */         continue;
/*     */       }
/*     */       
/* 268 */       localeProviderAdapter = findAdapter(paramClass, locale);
/* 269 */       if (localeProviderAdapter != null) {
/* 270 */         concurrentMap.putIfAbsent(paramLocale, localeProviderAdapter);
/* 271 */         return localeProviderAdapter;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 276 */     concurrentMap.putIfAbsent(paramLocale, fallbackLocaleProviderAdapter);
/* 277 */     return fallbackLocaleProviderAdapter;
/*     */   }
/*     */ 
/*     */   
/*     */   private static LocaleProviderAdapter findAdapter(Class<? extends LocaleServiceProvider> paramClass, Locale paramLocale) {
/* 282 */     for (Type type : getAdapterPreference()) {
/* 283 */       LocaleProviderAdapter localeProviderAdapter = forType(type);
/* 284 */       LocaleServiceProvider localeServiceProvider = (LocaleServiceProvider)localeProviderAdapter.getLocaleServiceProvider((Class)paramClass);
/* 285 */       if (localeServiceProvider != null && 
/* 286 */         localeServiceProvider.isSupportedLocale(paramLocale)) {
/* 287 */         return localeProviderAdapter;
/*     */       }
/*     */     } 
/*     */     
/* 291 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSupportedLocale(Locale paramLocale, Type paramType, Set<String> paramSet) {
/* 299 */     assert paramType == Type.JRE || paramType == Type.CLDR || paramType == Type.FALLBACK;
/* 300 */     if (Locale.ROOT.equals(paramLocale)) {
/* 301 */       return true;
/*     */     }
/*     */     
/* 304 */     if (paramType == Type.FALLBACK)
/*     */     {
/* 306 */       return false;
/*     */     }
/*     */     
/* 309 */     paramLocale = paramLocale.stripExtensions();
/* 310 */     if (paramSet.contains(paramLocale.toLanguageTag())) {
/* 311 */       return true;
/*     */     }
/* 313 */     if (paramType == Type.JRE) {
/* 314 */       String str = paramLocale.toString().replace('_', '-');
/* 315 */       return (paramSet.contains(str) || "ja-JP-JP"
/* 316 */         .equals(str) || "th-TH-TH"
/* 317 */         .equals(str) || "no-NO-NY"
/* 318 */         .equals(str));
/*     */     } 
/* 320 */     return false;
/*     */   }
/*     */   
/*     */   public static Locale[] toLocaleArray(Set<String> paramSet) {
/* 324 */     Locale[] arrayOfLocale = new Locale[paramSet.size() + 1];
/* 325 */     byte b = 0;
/* 326 */     arrayOfLocale[b++] = Locale.ROOT;
/* 327 */     for (String str : paramSet) {
/* 328 */       switch (str) {
/*     */         case "ja-JP-JP":
/* 330 */           arrayOfLocale[b++] = JRELocaleConstants.JA_JP_JP;
/*     */           continue;
/*     */         case "th-TH-TH":
/* 333 */           arrayOfLocale[b++] = JRELocaleConstants.TH_TH_TH;
/*     */           continue;
/*     */       } 
/* 336 */       arrayOfLocale[b++] = Locale.forLanguageTag(str);
/*     */     } 
/*     */ 
/*     */     
/* 340 */     return arrayOfLocale;
/*     */   }
/*     */   
/*     */   public abstract Type getAdapterType();
/*     */   
/*     */   public abstract <P extends LocaleServiceProvider> P getLocaleServiceProvider(Class<P> paramClass);
/*     */   
/*     */   public abstract BreakIteratorProvider getBreakIteratorProvider();
/*     */   
/*     */   public abstract CollatorProvider getCollatorProvider();
/*     */   
/*     */   public abstract DateFormatProvider getDateFormatProvider();
/*     */   
/*     */   public abstract DateFormatSymbolsProvider getDateFormatSymbolsProvider();
/*     */   
/*     */   public abstract DecimalFormatSymbolsProvider getDecimalFormatSymbolsProvider();
/*     */   
/*     */   public abstract NumberFormatProvider getNumberFormatProvider();
/*     */   
/*     */   public abstract CurrencyNameProvider getCurrencyNameProvider();
/*     */   
/*     */   public abstract LocaleNameProvider getLocaleNameProvider();
/*     */   
/*     */   public abstract TimeZoneNameProvider getTimeZoneNameProvider();
/*     */   
/*     */   public abstract CalendarDataProvider getCalendarDataProvider();
/*     */   
/*     */   public abstract CalendarNameProvider getCalendarNameProvider();
/*     */   
/*     */   public abstract CalendarProvider getCalendarProvider();
/*     */   
/*     */   public abstract LocaleResources getLocaleResources(Locale paramLocale);
/*     */   
/*     */   public abstract Locale[] getAvailableLocales();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/LocaleProviderAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */