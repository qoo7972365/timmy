/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.text.spi.BreakIteratorProvider;
/*     */ import java.text.spi.CollatorProvider;
/*     */ import java.text.spi.DateFormatProvider;
/*     */ import java.text.spi.DateFormatSymbolsProvider;
/*     */ import java.text.spi.DecimalFormatSymbolsProvider;
/*     */ import java.text.spi.NumberFormatProvider;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.spi.CalendarDataProvider;
/*     */ import java.util.spi.CalendarNameProvider;
/*     */ import java.util.spi.CurrencyNameProvider;
/*     */ import java.util.spi.LocaleNameProvider;
/*     */ import java.util.spi.LocaleServiceProvider;
/*     */ import java.util.spi.TimeZoneNameProvider;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AuxLocaleProviderAdapter
/*     */   extends LocaleProviderAdapter
/*     */ {
/*  59 */   private ConcurrentMap<Class<? extends LocaleServiceProvider>, LocaleServiceProvider> providersMap = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <P extends LocaleServiceProvider> P getLocaleServiceProvider(Class<P> paramClass) {
/*  68 */     LocaleServiceProvider localeServiceProvider = this.providersMap.get(paramClass);
/*  69 */     if (localeServiceProvider == null) {
/*  70 */       localeServiceProvider = findInstalledProvider(paramClass);
/*  71 */       this.providersMap.putIfAbsent(paramClass, (localeServiceProvider == null) ? NULL_PROVIDER : localeServiceProvider);
/*     */     } 
/*     */     
/*  74 */     return (P)localeServiceProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract <P extends LocaleServiceProvider> P findInstalledProvider(Class<P> paramClass);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BreakIteratorProvider getBreakIteratorProvider() {
/*  88 */     return getLocaleServiceProvider(BreakIteratorProvider.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public CollatorProvider getCollatorProvider() {
/*  93 */     return getLocaleServiceProvider(CollatorProvider.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public DateFormatProvider getDateFormatProvider() {
/*  98 */     return getLocaleServiceProvider(DateFormatProvider.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public DateFormatSymbolsProvider getDateFormatSymbolsProvider() {
/* 103 */     return getLocaleServiceProvider(DateFormatSymbolsProvider.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public DecimalFormatSymbolsProvider getDecimalFormatSymbolsProvider() {
/* 108 */     return getLocaleServiceProvider(DecimalFormatSymbolsProvider.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public NumberFormatProvider getNumberFormatProvider() {
/* 113 */     return getLocaleServiceProvider(NumberFormatProvider.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CurrencyNameProvider getCurrencyNameProvider() {
/* 121 */     return getLocaleServiceProvider(CurrencyNameProvider.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public LocaleNameProvider getLocaleNameProvider() {
/* 126 */     return getLocaleServiceProvider(LocaleNameProvider.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public TimeZoneNameProvider getTimeZoneNameProvider() {
/* 131 */     return getLocaleServiceProvider(TimeZoneNameProvider.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public CalendarDataProvider getCalendarDataProvider() {
/* 136 */     return getLocaleServiceProvider(CalendarDataProvider.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public CalendarNameProvider getCalendarNameProvider() {
/* 141 */     return getLocaleServiceProvider(CalendarNameProvider.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CalendarProvider getCalendarProvider() {
/* 149 */     return getLocaleServiceProvider(CalendarProvider.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public LocaleResources getLocaleResources(Locale paramLocale) {
/* 154 */     return null;
/*     */   }
/*     */   
/* 157 */   private static Locale[] availableLocales = null;
/*     */ 
/*     */   
/*     */   public Locale[] getAvailableLocales() {
/* 161 */     if (availableLocales == null) {
/* 162 */       HashSet hashSet = new HashSet();
/*     */       
/* 164 */       for (Class<LocaleServiceProvider> clazz : LocaleServiceProviderPool.spiClasses) {
/* 165 */         LocaleServiceProvider localeServiceProvider = (LocaleServiceProvider)getLocaleServiceProvider((Class)clazz);
/* 166 */         if (localeServiceProvider != null) {
/* 167 */           hashSet.addAll(Arrays.asList(localeServiceProvider.getAvailableLocales()));
/*     */         }
/*     */       } 
/* 170 */       availableLocales = (Locale[])hashSet.toArray((Object[])new Locale[0]);
/*     */     } 
/*     */ 
/*     */     
/* 174 */     return availableLocales;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   private static NullProvider NULL_PROVIDER = new NullProvider();
/*     */   
/*     */   private static class NullProvider extends LocaleServiceProvider {
/*     */     public Locale[] getAvailableLocales() {
/* 185 */       return new Locale[0];
/*     */     }
/*     */     
/*     */     private NullProvider() {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/AuxLocaleProviderAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */