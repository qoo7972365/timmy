/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.text.spi.DateFormatProvider;
/*     */ import java.util.Calendar;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DateFormatProviderImpl
/*     */   extends DateFormatProvider
/*     */   implements AvailableLanguageTags
/*     */ {
/*     */   private final LocaleProviderAdapter.Type type;
/*     */   private final Set<String> langtags;
/*     */   
/*     */   public DateFormatProviderImpl(LocaleProviderAdapter.Type paramType, Set<String> paramSet) {
/*  48 */     this.type = paramType;
/*  49 */     this.langtags = paramSet;
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
/*     */   public Locale[] getAvailableLocales() {
/*  61 */     return LocaleProviderAdapter.toLocaleArray(this.langtags);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportedLocale(Locale paramLocale) {
/*  66 */     return LocaleProviderAdapter.isSupportedLocale(paramLocale, this.type, this.langtags);
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
/*     */   public DateFormat getTimeInstance(int paramInt, Locale paramLocale) {
/*  89 */     return getInstance(-1, paramInt, paramLocale);
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
/*     */   public DateFormat getDateInstance(int paramInt, Locale paramLocale) {
/* 112 */     return getInstance(paramInt, -1, paramLocale);
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
/*     */   public DateFormat getDateTimeInstance(int paramInt1, int paramInt2, Locale paramLocale) {
/* 142 */     return getInstance(paramInt1, paramInt2, paramLocale);
/*     */   }
/*     */   
/*     */   private DateFormat getInstance(int paramInt1, int paramInt2, Locale paramLocale) {
/* 146 */     if (paramLocale == null) {
/* 147 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 150 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("", paramLocale);
/* 151 */     Calendar calendar = simpleDateFormat.getCalendar();
/*     */     
/*     */     try {
/* 154 */       String str = LocaleProviderAdapter.forType(this.type).getLocaleResources(paramLocale).getDateTimePattern(paramInt2, paramInt1, calendar);
/*     */       
/* 156 */       simpleDateFormat.applyPattern(str);
/* 157 */     } catch (MissingResourceException missingResourceException) {
/*     */       
/* 159 */       simpleDateFormat.applyPattern("M/d/yy h:mm a");
/*     */     } 
/*     */     
/* 162 */     return simpleDateFormat;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getAvailableLanguageTags() {
/* 167 */     return this.langtags;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/DateFormatProviderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */