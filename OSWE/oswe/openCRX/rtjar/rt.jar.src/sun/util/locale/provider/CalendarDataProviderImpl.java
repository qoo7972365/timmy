/*    */ package sun.util.locale.provider;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.Set;
/*    */ import java.util.spi.CalendarDataProvider;
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
/*    */ public class CalendarDataProviderImpl
/*    */   extends CalendarDataProvider
/*    */   implements AvailableLanguageTags
/*    */ {
/*    */   private final LocaleProviderAdapter.Type type;
/*    */   private final Set<String> langtags;
/*    */   
/*    */   public CalendarDataProviderImpl(LocaleProviderAdapter.Type paramType, Set<String> paramSet) {
/* 43 */     this.type = paramType;
/* 44 */     this.langtags = paramSet;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFirstDayOfWeek(Locale paramLocale) {
/* 49 */     return LocaleProviderAdapter.forType(this.type).getLocaleResources(paramLocale)
/* 50 */       .getCalendarData("firstDayOfWeek");
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinimalDaysInFirstWeek(Locale paramLocale) {
/* 55 */     return LocaleProviderAdapter.forType(this.type).getLocaleResources(paramLocale)
/* 56 */       .getCalendarData("minimalDaysInFirstWeek");
/*    */   }
/*    */ 
/*    */   
/*    */   public Locale[] getAvailableLocales() {
/* 61 */     return LocaleProviderAdapter.toLocaleArray(this.langtags);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> getAvailableLanguageTags() {
/* 66 */     return this.langtags;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/CalendarDataProviderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */