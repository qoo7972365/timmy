/*    */ package sun.util.locale.provider;
/*    */ 
/*    */ import java.util.Calendar;
/*    */ import java.util.Locale;
/*    */ import java.util.Set;
/*    */ import java.util.TimeZone;
/*    */ import sun.util.spi.CalendarProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CalendarProviderImpl
/*    */   extends CalendarProvider
/*    */   implements AvailableLanguageTags
/*    */ {
/*    */   private final LocaleProviderAdapter.Type type;
/*    */   private final Set<String> langtags;
/*    */   
/*    */   public CalendarProviderImpl(LocaleProviderAdapter.Type paramType, Set<String> paramSet) {
/* 46 */     this.type = paramType;
/* 47 */     this.langtags = paramSet;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Locale[] getAvailableLocales() {
/* 59 */     return LocaleProviderAdapter.toLocaleArray(this.langtags);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSupportedLocale(Locale paramLocale) {
/* 65 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Calendar getInstance(TimeZone paramTimeZone, Locale paramLocale) {
/* 84 */     return (new Calendar.Builder())
/* 85 */       .setLocale(paramLocale)
/* 86 */       .setTimeZone(paramTimeZone)
/* 87 */       .setInstant(System.currentTimeMillis())
/* 88 */       .build();
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> getAvailableLanguageTags() {
/* 93 */     return this.langtags;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/CalendarProviderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */