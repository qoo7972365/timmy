/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.text.BreakIterator;
/*     */ import java.text.Collator;
/*     */ import java.text.DateFormat;
/*     */ import java.text.DateFormatSymbols;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.spi.BreakIteratorProvider;
/*     */ import java.text.spi.CollatorProvider;
/*     */ import java.text.spi.DateFormatProvider;
/*     */ import java.text.spi.DateFormatSymbolsProvider;
/*     */ import java.text.spi.DecimalFormatSymbolsProvider;
/*     */ import java.text.spi.NumberFormatProvider;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.spi.CalendarDataProvider;
/*     */ import java.util.spi.CalendarNameProvider;
/*     */ import java.util.spi.CurrencyNameProvider;
/*     */ import java.util.spi.LocaleNameProvider;
/*     */ import java.util.spi.LocaleServiceProvider;
/*     */ import java.util.spi.TimeZoneNameProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPILocaleProviderAdapter
/*     */   extends AuxLocaleProviderAdapter
/*     */ {
/*     */   public LocaleProviderAdapter.Type getAdapterType() {
/*  68 */     return LocaleProviderAdapter.Type.SPI;
/*     */   }
/*     */ 
/*     */   
/*     */   protected <P extends LocaleServiceProvider> P findInstalledProvider(final Class<P> c) {
/*     */     try {
/*  74 */       return (P)AccessController.<LocaleServiceProvider>doPrivileged(new PrivilegedExceptionAction<P>()
/*     */           {
/*     */             public P run()
/*     */             {
/*  78 */               LocaleServiceProvider localeServiceProvider = null;
/*     */               
/*  80 */               for (LocaleServiceProvider localeServiceProvider1 : ServiceLoader.loadInstalled(c)) {
/*  81 */                 if (localeServiceProvider == null) {
/*     */                   
/*     */                   try {
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/*  88 */                     localeServiceProvider = (LocaleServiceProvider)Class.forName(SPILocaleProviderAdapter.class.getCanonicalName() + "$" + c.getSimpleName() + "Delegate").newInstance();
/*  89 */                   } catch (ClassNotFoundException|InstantiationException|IllegalAccessException classNotFoundException) {
/*     */ 
/*     */                     
/*  92 */                     LocaleServiceProviderPool.config((Class)SPILocaleProviderAdapter.class, classNotFoundException.toString());
/*  93 */                     return null;
/*     */                   } 
/*     */                 }
/*     */                 
/*  97 */                 ((SPILocaleProviderAdapter.Delegate<LocaleServiceProvider>)localeServiceProvider).addImpl(localeServiceProvider1);
/*     */               } 
/*  99 */               return (P)localeServiceProvider;
/*     */             }
/*     */           });
/* 102 */     } catch (PrivilegedActionException privilegedActionException) {
/* 103 */       LocaleServiceProviderPool.config((Class)SPILocaleProviderAdapter.class, privilegedActionException.toString());
/*     */       
/* 105 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static interface Delegate<P extends LocaleServiceProvider>
/*     */   {
/*     */     void addImpl(P param1P);
/*     */ 
/*     */     
/*     */     P getImpl(Locale param1Locale);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <P extends LocaleServiceProvider> P getImpl(Map<Locale, P> paramMap, Locale paramLocale) {
/* 121 */     for (Locale locale : LocaleServiceProviderPool.getLookupLocales(paramLocale)) {
/* 122 */       LocaleServiceProvider localeServiceProvider = (LocaleServiceProvider)paramMap.get(locale);
/* 123 */       if (localeServiceProvider != null) {
/* 124 */         return (P)localeServiceProvider;
/*     */       }
/*     */     } 
/* 127 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   static class BreakIteratorProviderDelegate
/*     */     extends BreakIteratorProvider
/*     */     implements Delegate<BreakIteratorProvider>
/*     */   {
/* 135 */     private ConcurrentMap<Locale, BreakIteratorProvider> map = new ConcurrentHashMap<>();
/*     */ 
/*     */     
/*     */     public void addImpl(BreakIteratorProvider param1BreakIteratorProvider) {
/* 139 */       for (Locale locale : param1BreakIteratorProvider.getAvailableLocales()) {
/* 140 */         this.map.putIfAbsent(locale, param1BreakIteratorProvider);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public BreakIteratorProvider getImpl(Locale param1Locale) {
/* 146 */       return (BreakIteratorProvider)SPILocaleProviderAdapter.getImpl((Map)this.map, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public Locale[] getAvailableLocales() {
/* 151 */       return (Locale[])this.map.keySet().toArray((Object[])new Locale[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSupportedLocale(Locale param1Locale) {
/* 156 */       return this.map.containsKey(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public BreakIterator getWordInstance(Locale param1Locale) {
/* 161 */       BreakIteratorProvider breakIteratorProvider = getImpl(param1Locale);
/* 162 */       assert breakIteratorProvider != null;
/* 163 */       return breakIteratorProvider.getWordInstance(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public BreakIterator getLineInstance(Locale param1Locale) {
/* 168 */       BreakIteratorProvider breakIteratorProvider = getImpl(param1Locale);
/* 169 */       assert breakIteratorProvider != null;
/* 170 */       return breakIteratorProvider.getLineInstance(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public BreakIterator getCharacterInstance(Locale param1Locale) {
/* 175 */       BreakIteratorProvider breakIteratorProvider = getImpl(param1Locale);
/* 176 */       assert breakIteratorProvider != null;
/* 177 */       return breakIteratorProvider.getCharacterInstance(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public BreakIterator getSentenceInstance(Locale param1Locale) {
/* 182 */       BreakIteratorProvider breakIteratorProvider = getImpl(param1Locale);
/* 183 */       assert breakIteratorProvider != null;
/* 184 */       return breakIteratorProvider.getSentenceInstance(param1Locale);
/*     */     }
/*     */   }
/*     */   
/*     */   static class CollatorProviderDelegate
/*     */     extends CollatorProvider implements Delegate<CollatorProvider> {
/* 190 */     private ConcurrentMap<Locale, CollatorProvider> map = new ConcurrentHashMap<>();
/*     */ 
/*     */     
/*     */     public void addImpl(CollatorProvider param1CollatorProvider) {
/* 194 */       for (Locale locale : param1CollatorProvider.getAvailableLocales()) {
/* 195 */         this.map.putIfAbsent(locale, param1CollatorProvider);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public CollatorProvider getImpl(Locale param1Locale) {
/* 201 */       return (CollatorProvider)SPILocaleProviderAdapter.getImpl((Map)this.map, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public Locale[] getAvailableLocales() {
/* 206 */       return (Locale[])this.map.keySet().toArray((Object[])new Locale[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSupportedLocale(Locale param1Locale) {
/* 211 */       return this.map.containsKey(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public Collator getInstance(Locale param1Locale) {
/* 216 */       CollatorProvider collatorProvider = getImpl(param1Locale);
/* 217 */       assert collatorProvider != null;
/* 218 */       return collatorProvider.getInstance(param1Locale);
/*     */     }
/*     */   }
/*     */   
/*     */   static class DateFormatProviderDelegate
/*     */     extends DateFormatProvider implements Delegate<DateFormatProvider> {
/* 224 */     private ConcurrentMap<Locale, DateFormatProvider> map = new ConcurrentHashMap<>();
/*     */ 
/*     */     
/*     */     public void addImpl(DateFormatProvider param1DateFormatProvider) {
/* 228 */       for (Locale locale : param1DateFormatProvider.getAvailableLocales()) {
/* 229 */         this.map.putIfAbsent(locale, param1DateFormatProvider);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public DateFormatProvider getImpl(Locale param1Locale) {
/* 235 */       return (DateFormatProvider)SPILocaleProviderAdapter.getImpl((Map)this.map, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public Locale[] getAvailableLocales() {
/* 240 */       return (Locale[])this.map.keySet().toArray((Object[])new Locale[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSupportedLocale(Locale param1Locale) {
/* 245 */       return this.map.containsKey(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public DateFormat getTimeInstance(int param1Int, Locale param1Locale) {
/* 250 */       DateFormatProvider dateFormatProvider = getImpl(param1Locale);
/* 251 */       assert dateFormatProvider != null;
/* 252 */       return dateFormatProvider.getTimeInstance(param1Int, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public DateFormat getDateInstance(int param1Int, Locale param1Locale) {
/* 257 */       DateFormatProvider dateFormatProvider = getImpl(param1Locale);
/* 258 */       assert dateFormatProvider != null;
/* 259 */       return dateFormatProvider.getDateInstance(param1Int, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public DateFormat getDateTimeInstance(int param1Int1, int param1Int2, Locale param1Locale) {
/* 264 */       DateFormatProvider dateFormatProvider = getImpl(param1Locale);
/* 265 */       assert dateFormatProvider != null;
/* 266 */       return dateFormatProvider.getDateTimeInstance(param1Int1, param1Int2, param1Locale);
/*     */     }
/*     */   }
/*     */   
/*     */   static class DateFormatSymbolsProviderDelegate
/*     */     extends DateFormatSymbolsProvider implements Delegate<DateFormatSymbolsProvider> {
/* 272 */     private ConcurrentMap<Locale, DateFormatSymbolsProvider> map = new ConcurrentHashMap<>();
/*     */ 
/*     */     
/*     */     public void addImpl(DateFormatSymbolsProvider param1DateFormatSymbolsProvider) {
/* 276 */       for (Locale locale : param1DateFormatSymbolsProvider.getAvailableLocales()) {
/* 277 */         this.map.putIfAbsent(locale, param1DateFormatSymbolsProvider);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public DateFormatSymbolsProvider getImpl(Locale param1Locale) {
/* 283 */       return (DateFormatSymbolsProvider)SPILocaleProviderAdapter.getImpl((Map)this.map, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public Locale[] getAvailableLocales() {
/* 288 */       return (Locale[])this.map.keySet().toArray((Object[])new Locale[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSupportedLocale(Locale param1Locale) {
/* 293 */       return this.map.containsKey(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public DateFormatSymbols getInstance(Locale param1Locale) {
/* 298 */       DateFormatSymbolsProvider dateFormatSymbolsProvider = getImpl(param1Locale);
/* 299 */       assert dateFormatSymbolsProvider != null;
/* 300 */       return dateFormatSymbolsProvider.getInstance(param1Locale);
/*     */     }
/*     */   }
/*     */   
/*     */   static class DecimalFormatSymbolsProviderDelegate
/*     */     extends DecimalFormatSymbolsProvider implements Delegate<DecimalFormatSymbolsProvider> {
/* 306 */     private ConcurrentMap<Locale, DecimalFormatSymbolsProvider> map = new ConcurrentHashMap<>();
/*     */ 
/*     */     
/*     */     public void addImpl(DecimalFormatSymbolsProvider param1DecimalFormatSymbolsProvider) {
/* 310 */       for (Locale locale : param1DecimalFormatSymbolsProvider.getAvailableLocales()) {
/* 311 */         this.map.putIfAbsent(locale, param1DecimalFormatSymbolsProvider);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public DecimalFormatSymbolsProvider getImpl(Locale param1Locale) {
/* 317 */       return (DecimalFormatSymbolsProvider)SPILocaleProviderAdapter.getImpl((Map)this.map, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public Locale[] getAvailableLocales() {
/* 322 */       return (Locale[])this.map.keySet().toArray((Object[])new Locale[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSupportedLocale(Locale param1Locale) {
/* 327 */       return this.map.containsKey(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public DecimalFormatSymbols getInstance(Locale param1Locale) {
/* 332 */       DecimalFormatSymbolsProvider decimalFormatSymbolsProvider = getImpl(param1Locale);
/* 333 */       assert decimalFormatSymbolsProvider != null;
/* 334 */       return decimalFormatSymbolsProvider.getInstance(param1Locale);
/*     */     }
/*     */   }
/*     */   
/*     */   static class NumberFormatProviderDelegate
/*     */     extends NumberFormatProvider implements Delegate<NumberFormatProvider> {
/* 340 */     private ConcurrentMap<Locale, NumberFormatProvider> map = new ConcurrentHashMap<>();
/*     */ 
/*     */     
/*     */     public void addImpl(NumberFormatProvider param1NumberFormatProvider) {
/* 344 */       for (Locale locale : param1NumberFormatProvider.getAvailableLocales()) {
/* 345 */         this.map.putIfAbsent(locale, param1NumberFormatProvider);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public NumberFormatProvider getImpl(Locale param1Locale) {
/* 351 */       return (NumberFormatProvider)SPILocaleProviderAdapter.getImpl((Map)this.map, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public Locale[] getAvailableLocales() {
/* 356 */       return (Locale[])this.map.keySet().toArray((Object[])new Locale[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSupportedLocale(Locale param1Locale) {
/* 361 */       return this.map.containsKey(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public NumberFormat getCurrencyInstance(Locale param1Locale) {
/* 366 */       NumberFormatProvider numberFormatProvider = getImpl(param1Locale);
/* 367 */       assert numberFormatProvider != null;
/* 368 */       return numberFormatProvider.getCurrencyInstance(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public NumberFormat getIntegerInstance(Locale param1Locale) {
/* 373 */       NumberFormatProvider numberFormatProvider = getImpl(param1Locale);
/* 374 */       assert numberFormatProvider != null;
/* 375 */       return numberFormatProvider.getIntegerInstance(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public NumberFormat getNumberInstance(Locale param1Locale) {
/* 380 */       NumberFormatProvider numberFormatProvider = getImpl(param1Locale);
/* 381 */       assert numberFormatProvider != null;
/* 382 */       return numberFormatProvider.getNumberInstance(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public NumberFormat getPercentInstance(Locale param1Locale) {
/* 387 */       NumberFormatProvider numberFormatProvider = getImpl(param1Locale);
/* 388 */       assert numberFormatProvider != null;
/* 389 */       return numberFormatProvider.getPercentInstance(param1Locale);
/*     */     }
/*     */   }
/*     */   
/*     */   static class CalendarDataProviderDelegate
/*     */     extends CalendarDataProvider implements Delegate<CalendarDataProvider> {
/* 395 */     private ConcurrentMap<Locale, CalendarDataProvider> map = new ConcurrentHashMap<>();
/*     */ 
/*     */     
/*     */     public void addImpl(CalendarDataProvider param1CalendarDataProvider) {
/* 399 */       for (Locale locale : param1CalendarDataProvider.getAvailableLocales()) {
/* 400 */         this.map.putIfAbsent(locale, param1CalendarDataProvider);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public CalendarDataProvider getImpl(Locale param1Locale) {
/* 406 */       return (CalendarDataProvider)SPILocaleProviderAdapter.getImpl((Map)this.map, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public Locale[] getAvailableLocales() {
/* 411 */       return (Locale[])this.map.keySet().toArray((Object[])new Locale[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSupportedLocale(Locale param1Locale) {
/* 416 */       return this.map.containsKey(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getFirstDayOfWeek(Locale param1Locale) {
/* 421 */       CalendarDataProvider calendarDataProvider = getImpl(param1Locale);
/* 422 */       assert calendarDataProvider != null;
/* 423 */       return calendarDataProvider.getFirstDayOfWeek(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMinimalDaysInFirstWeek(Locale param1Locale) {
/* 428 */       CalendarDataProvider calendarDataProvider = getImpl(param1Locale);
/* 429 */       assert calendarDataProvider != null;
/* 430 */       return calendarDataProvider.getMinimalDaysInFirstWeek(param1Locale);
/*     */     }
/*     */   }
/*     */   
/*     */   static class CalendarNameProviderDelegate
/*     */     extends CalendarNameProvider implements Delegate<CalendarNameProvider> {
/* 436 */     private ConcurrentMap<Locale, CalendarNameProvider> map = new ConcurrentHashMap<>();
/*     */ 
/*     */     
/*     */     public void addImpl(CalendarNameProvider param1CalendarNameProvider) {
/* 440 */       for (Locale locale : param1CalendarNameProvider.getAvailableLocales()) {
/* 441 */         this.map.putIfAbsent(locale, param1CalendarNameProvider);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public CalendarNameProvider getImpl(Locale param1Locale) {
/* 447 */       return (CalendarNameProvider)SPILocaleProviderAdapter.getImpl((Map)this.map, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public Locale[] getAvailableLocales() {
/* 452 */       return (Locale[])this.map.keySet().toArray((Object[])new Locale[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSupportedLocale(Locale param1Locale) {
/* 457 */       return this.map.containsKey(param1Locale);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getDisplayName(String param1String, int param1Int1, int param1Int2, int param1Int3, Locale param1Locale) {
/* 464 */       CalendarNameProvider calendarNameProvider = getImpl(param1Locale);
/* 465 */       assert calendarNameProvider != null;
/* 466 */       return calendarNameProvider.getDisplayName(param1String, param1Int1, param1Int2, param1Int3, param1Locale);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<String, Integer> getDisplayNames(String param1String, int param1Int1, int param1Int2, Locale param1Locale) {
/* 473 */       CalendarNameProvider calendarNameProvider = getImpl(param1Locale);
/* 474 */       assert calendarNameProvider != null;
/* 475 */       return calendarNameProvider.getDisplayNames(param1String, param1Int1, param1Int2, param1Locale);
/*     */     }
/*     */   }
/*     */   
/*     */   static class CurrencyNameProviderDelegate
/*     */     extends CurrencyNameProvider implements Delegate<CurrencyNameProvider> {
/* 481 */     private ConcurrentMap<Locale, CurrencyNameProvider> map = new ConcurrentHashMap<>();
/*     */ 
/*     */     
/*     */     public void addImpl(CurrencyNameProvider param1CurrencyNameProvider) {
/* 485 */       for (Locale locale : param1CurrencyNameProvider.getAvailableLocales()) {
/* 486 */         this.map.putIfAbsent(locale, param1CurrencyNameProvider);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public CurrencyNameProvider getImpl(Locale param1Locale) {
/* 492 */       return (CurrencyNameProvider)SPILocaleProviderAdapter.getImpl((Map)this.map, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public Locale[] getAvailableLocales() {
/* 497 */       return (Locale[])this.map.keySet().toArray((Object[])new Locale[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSupportedLocale(Locale param1Locale) {
/* 502 */       return this.map.containsKey(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSymbol(String param1String, Locale param1Locale) {
/* 507 */       CurrencyNameProvider currencyNameProvider = getImpl(param1Locale);
/* 508 */       assert currencyNameProvider != null;
/* 509 */       return currencyNameProvider.getSymbol(param1String, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDisplayName(String param1String, Locale param1Locale) {
/* 514 */       CurrencyNameProvider currencyNameProvider = getImpl(param1Locale);
/* 515 */       assert currencyNameProvider != null;
/* 516 */       return currencyNameProvider.getDisplayName(param1String, param1Locale);
/*     */     }
/*     */   }
/*     */   
/*     */   static class LocaleNameProviderDelegate
/*     */     extends LocaleNameProvider implements Delegate<LocaleNameProvider> {
/* 522 */     private ConcurrentMap<Locale, LocaleNameProvider> map = new ConcurrentHashMap<>();
/*     */ 
/*     */     
/*     */     public void addImpl(LocaleNameProvider param1LocaleNameProvider) {
/* 526 */       for (Locale locale : param1LocaleNameProvider.getAvailableLocales()) {
/* 527 */         this.map.putIfAbsent(locale, param1LocaleNameProvider);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public LocaleNameProvider getImpl(Locale param1Locale) {
/* 533 */       return (LocaleNameProvider)SPILocaleProviderAdapter.getImpl((Map)this.map, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public Locale[] getAvailableLocales() {
/* 538 */       return (Locale[])this.map.keySet().toArray((Object[])new Locale[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSupportedLocale(Locale param1Locale) {
/* 543 */       return this.map.containsKey(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDisplayLanguage(String param1String, Locale param1Locale) {
/* 548 */       LocaleNameProvider localeNameProvider = getImpl(param1Locale);
/* 549 */       assert localeNameProvider != null;
/* 550 */       return localeNameProvider.getDisplayLanguage(param1String, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDisplayScript(String param1String, Locale param1Locale) {
/* 555 */       LocaleNameProvider localeNameProvider = getImpl(param1Locale);
/* 556 */       assert localeNameProvider != null;
/* 557 */       return localeNameProvider.getDisplayScript(param1String, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDisplayCountry(String param1String, Locale param1Locale) {
/* 562 */       LocaleNameProvider localeNameProvider = getImpl(param1Locale);
/* 563 */       assert localeNameProvider != null;
/* 564 */       return localeNameProvider.getDisplayCountry(param1String, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDisplayVariant(String param1String, Locale param1Locale) {
/* 569 */       LocaleNameProvider localeNameProvider = getImpl(param1Locale);
/* 570 */       assert localeNameProvider != null;
/* 571 */       return localeNameProvider.getDisplayVariant(param1String, param1Locale);
/*     */     }
/*     */   }
/*     */   
/*     */   static class TimeZoneNameProviderDelegate
/*     */     extends TimeZoneNameProvider implements Delegate<TimeZoneNameProvider> {
/* 577 */     private ConcurrentMap<Locale, TimeZoneNameProvider> map = new ConcurrentHashMap<>();
/*     */ 
/*     */     
/*     */     public void addImpl(TimeZoneNameProvider param1TimeZoneNameProvider) {
/* 581 */       for (Locale locale : param1TimeZoneNameProvider.getAvailableLocales()) {
/* 582 */         this.map.putIfAbsent(locale, param1TimeZoneNameProvider);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public TimeZoneNameProvider getImpl(Locale param1Locale) {
/* 588 */       return (TimeZoneNameProvider)SPILocaleProviderAdapter.getImpl((Map)this.map, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public Locale[] getAvailableLocales() {
/* 593 */       return (Locale[])this.map.keySet().toArray((Object[])new Locale[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSupportedLocale(Locale param1Locale) {
/* 598 */       return this.map.containsKey(param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDisplayName(String param1String, boolean param1Boolean, int param1Int, Locale param1Locale) {
/* 603 */       TimeZoneNameProvider timeZoneNameProvider = getImpl(param1Locale);
/* 604 */       assert timeZoneNameProvider != null;
/* 605 */       return timeZoneNameProvider.getDisplayName(param1String, param1Boolean, param1Int, param1Locale);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getGenericDisplayName(String param1String, int param1Int, Locale param1Locale) {
/* 610 */       TimeZoneNameProvider timeZoneNameProvider = getImpl(param1Locale);
/* 611 */       assert timeZoneNameProvider != null;
/* 612 */       return timeZoneNameProvider.getGenericDisplayName(param1String, param1Int, param1Locale);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/SPILocaleProviderAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */