/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.text.spi.BreakIteratorProvider;
/*     */ import java.text.spi.CollatorProvider;
/*     */ import java.text.spi.DateFormatProvider;
/*     */ import java.text.spi.DateFormatSymbolsProvider;
/*     */ import java.text.spi.DecimalFormatSymbolsProvider;
/*     */ import java.text.spi.NumberFormatProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.IllformedLocaleException;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.spi.CalendarDataProvider;
/*     */ import java.util.spi.CurrencyNameProvider;
/*     */ import java.util.spi.LocaleNameProvider;
/*     */ import java.util.spi.LocaleServiceProvider;
/*     */ import java.util.spi.TimeZoneNameProvider;
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
/*     */ public final class LocaleServiceProviderPool
/*     */ {
/*  56 */   private static ConcurrentMap<Class<? extends LocaleServiceProvider>, LocaleServiceProviderPool> poolOfPools = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private ConcurrentMap<LocaleProviderAdapter.Type, LocaleServiceProvider> providers = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private ConcurrentMap<Locale, List<LocaleProviderAdapter.Type>> providersCache = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private Set<Locale> availableLocales = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<? extends LocaleServiceProvider> providerClass;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   static final Class<LocaleServiceProvider>[] spiClasses = new Class[] { BreakIteratorProvider.class, CollatorProvider.class, DateFormatProvider.class, DateFormatSymbolsProvider.class, DecimalFormatSymbolsProvider.class, NumberFormatProvider.class, CurrencyNameProvider.class, LocaleNameProvider.class, TimeZoneNameProvider.class, CalendarDataProvider.class };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LocaleServiceProviderPool getPool(Class<? extends LocaleServiceProvider> paramClass) {
/* 109 */     LocaleServiceProviderPool localeServiceProviderPool = poolOfPools.get(paramClass);
/* 110 */     if (localeServiceProviderPool == null) {
/* 111 */       LocaleServiceProviderPool localeServiceProviderPool1 = new LocaleServiceProviderPool(paramClass);
/*     */       
/* 113 */       localeServiceProviderPool = poolOfPools.putIfAbsent(paramClass, localeServiceProviderPool1);
/* 114 */       if (localeServiceProviderPool == null) {
/* 115 */         localeServiceProviderPool = localeServiceProviderPool1;
/*     */       }
/*     */     } 
/*     */     
/* 119 */     return localeServiceProviderPool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LocaleServiceProviderPool(Class<? extends LocaleServiceProvider> paramClass) {
/* 128 */     this.providerClass = paramClass;
/*     */     
/* 130 */     for (LocaleProviderAdapter.Type type : LocaleProviderAdapter.getAdapterPreference()) {
/* 131 */       LocaleProviderAdapter localeProviderAdapter = LocaleProviderAdapter.forType(type);
/* 132 */       if (localeProviderAdapter != null) {
/* 133 */         LocaleServiceProvider localeServiceProvider = (LocaleServiceProvider)localeProviderAdapter.getLocaleServiceProvider((Class)paramClass);
/* 134 */         if (localeServiceProvider != null) {
/* 135 */           this.providers.putIfAbsent(type, localeServiceProvider);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static void config(Class<? extends Object> paramClass, String paramString) {
/* 142 */     PlatformLogger platformLogger = PlatformLogger.getLogger(paramClass.getCanonicalName());
/* 143 */     platformLogger.config(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface LocalizedObjectGetter<P extends LocaleServiceProvider, S>
/*     */   {
/*     */     S getObject(P param1P, Locale param1Locale, String param1String, Object... param1VarArgs);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class AllAvailableLocales
/*     */   {
/*     */     static final Locale[] allAvailableLocales;
/*     */     
/*     */     static {
/* 158 */       HashSet hashSet = new HashSet();
/* 159 */       for (Class<LocaleServiceProvider> clazz : LocaleServiceProviderPool.spiClasses) {
/*     */         
/* 161 */         LocaleServiceProviderPool localeServiceProviderPool = LocaleServiceProviderPool.getPool(clazz);
/* 162 */         hashSet.addAll(localeServiceProviderPool.getAvailableLocaleSet());
/*     */       } 
/*     */       
/* 165 */       allAvailableLocales = (Locale[])hashSet.toArray((Object[])new Locale[0]);
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
/*     */ 
/*     */   
/*     */   public static Locale[] getAllAvailableLocales() {
/* 181 */     return (Locale[])AllAvailableLocales.allAvailableLocales.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Locale[] getAvailableLocales() {
/* 192 */     HashSet<Locale> hashSet = new HashSet();
/* 193 */     hashSet.addAll(getAvailableLocaleSet());
/*     */     
/* 195 */     hashSet.addAll(Arrays.asList(LocaleProviderAdapter.forJRE().getAvailableLocales()));
/* 196 */     Locale[] arrayOfLocale = new Locale[hashSet.size()];
/* 197 */     hashSet.toArray(arrayOfLocale);
/* 198 */     return arrayOfLocale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized Set<Locale> getAvailableLocaleSet() {
/* 209 */     if (this.availableLocales == null) {
/* 210 */       this.availableLocales = new HashSet<>();
/* 211 */       for (LocaleServiceProvider localeServiceProvider : this.providers.values()) {
/* 212 */         Locale[] arrayOfLocale = localeServiceProvider.getAvailableLocales();
/* 213 */         for (Locale locale : arrayOfLocale) {
/* 214 */           this.availableLocales.add(getLookupLocale(locale));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 219 */     return this.availableLocales;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean hasProviders() {
/* 229 */     return (this.providers.size() != 1 || (this.providers
/* 230 */       .get(LocaleProviderAdapter.Type.JRE) == null && this.providers
/* 231 */       .get(LocaleProviderAdapter.Type.FALLBACK) == null));
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
/*     */   public <P extends LocaleServiceProvider, S> S getLocalizedObject(LocalizedObjectGetter<P, S> paramLocalizedObjectGetter, Locale paramLocale, Object... paramVarArgs) {
/* 247 */     return getLocalizedObjectImpl(paramLocalizedObjectGetter, paramLocale, true, null, paramVarArgs);
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
/*     */   public <P extends LocaleServiceProvider, S> S getLocalizedObject(LocalizedObjectGetter<P, S> paramLocalizedObjectGetter, Locale paramLocale, String paramString, Object... paramVarArgs) {
/* 265 */     return getLocalizedObjectImpl(paramLocalizedObjectGetter, paramLocale, false, paramString, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <P extends LocaleServiceProvider, S> S getLocalizedObjectImpl(LocalizedObjectGetter<P, S> paramLocalizedObjectGetter, Locale paramLocale, boolean paramBoolean, String paramString, Object... paramVarArgs) {
/* 274 */     if (paramLocale == null) {
/* 275 */       throw new NullPointerException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 280 */     if (!hasProviders()) {
/* 281 */       return paramLocalizedObjectGetter.getObject((P)this.providers.get(LocaleProviderAdapter.defaultLocaleProviderAdapter), paramLocale, paramString, paramVarArgs);
/*     */     }
/*     */ 
/*     */     
/* 285 */     List<Locale> list = getLookupLocales(paramLocale);
/*     */     
/* 287 */     Set<Locale> set = getAvailableLocaleSet();
/* 288 */     for (Locale locale : list) {
/* 289 */       if (set.contains(locale))
/*     */       {
/*     */         
/* 292 */         for (LocaleProviderAdapter.Type type : findProviders(locale)) {
/* 293 */           LocaleServiceProvider localeServiceProvider = this.providers.get(type);
/* 294 */           S s = paramLocalizedObjectGetter.getObject((P)localeServiceProvider, paramLocale, paramString, paramVarArgs);
/* 295 */           if (s != null)
/* 296 */             return s; 
/* 297 */           if (paramBoolean) {
/* 298 */             config((Class)LocaleServiceProviderPool.class, "A locale sensitive service provider returned null for a localized objects,  which should not happen.  provider: " + localeServiceProvider + " locale: " + paramLocale);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<LocaleProviderAdapter.Type> findProviders(Locale paramLocale) {
/* 318 */     List<LocaleProviderAdapter.Type> list = this.providersCache.get(paramLocale);
/* 319 */     if (list == null) {
/* 320 */       for (LocaleProviderAdapter.Type type : LocaleProviderAdapter.getAdapterPreference()) {
/* 321 */         LocaleServiceProvider localeServiceProvider = this.providers.get(type);
/* 322 */         if (localeServiceProvider != null && 
/* 323 */           localeServiceProvider.isSupportedLocale(paramLocale)) {
/* 324 */           if (list == null) {
/* 325 */             list = new ArrayList(2);
/*     */           }
/* 327 */           list.add(type);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 332 */       if (list == null) {
/* 333 */         list = NULL_LIST;
/*     */       }
/* 335 */       List<LocaleProviderAdapter.Type> list1 = this.providersCache.putIfAbsent(paramLocale, list);
/* 336 */       if (list1 != null) {
/* 337 */         list = list1;
/*     */       }
/*     */     } 
/* 340 */     return list;
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
/*     */   static List<Locale> getLookupLocales(Locale paramLocale) {
/* 354 */     return ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT).getCandidateLocales("", paramLocale);
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
/*     */   static Locale getLookupLocale(Locale paramLocale) {
/* 367 */     Locale locale = paramLocale;
/* 368 */     if (paramLocale.hasExtensions() && 
/* 369 */       !paramLocale.equals(JRELocaleConstants.JA_JP_JP) && 
/* 370 */       !paramLocale.equals(JRELocaleConstants.TH_TH_TH)) {
/*     */       
/* 372 */       Locale.Builder builder = new Locale.Builder();
/*     */       try {
/* 374 */         builder.setLocale(paramLocale);
/* 375 */         builder.clearExtensions();
/* 376 */         locale = builder.build();
/* 377 */       } catch (IllformedLocaleException illformedLocaleException) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 382 */         config((Class)LocaleServiceProviderPool.class, "A locale(" + paramLocale + ") has non-empty extensions, but has illformed fields.");
/*     */ 
/*     */ 
/*     */         
/* 386 */         locale = new Locale(paramLocale.getLanguage(), paramLocale.getCountry(), paramLocale.getVariant());
/*     */       } 
/*     */     } 
/* 389 */     return locale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 397 */   private static List<LocaleProviderAdapter.Type> NULL_LIST = Collections.emptyList();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/LocaleServiceProviderPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */