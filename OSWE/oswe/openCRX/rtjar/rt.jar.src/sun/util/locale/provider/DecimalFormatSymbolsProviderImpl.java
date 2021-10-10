/*    */ package sun.util.locale.provider;
/*    */ 
/*    */ import java.text.DecimalFormatSymbols;
/*    */ import java.text.spi.DecimalFormatSymbolsProvider;
/*    */ import java.util.Locale;
/*    */ import java.util.Set;
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
/*    */ public class DecimalFormatSymbolsProviderImpl
/*    */   extends DecimalFormatSymbolsProvider
/*    */   implements AvailableLanguageTags
/*    */ {
/*    */   private final LocaleProviderAdapter.Type type;
/*    */   private final Set<String> langtags;
/*    */   
/*    */   public DecimalFormatSymbolsProviderImpl(LocaleProviderAdapter.Type paramType, Set<String> paramSet) {
/* 45 */     this.type = paramType;
/* 46 */     this.langtags = paramSet;
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
/* 58 */     return LocaleProviderAdapter.toLocaleArray(this.langtags);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSupportedLocale(Locale paramLocale) {
/* 63 */     return LocaleProviderAdapter.isSupportedLocale(paramLocale, this.type, this.langtags);
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
/*    */   public DecimalFormatSymbols getInstance(Locale paramLocale) {
/* 81 */     if (paramLocale == null) {
/* 82 */       throw new NullPointerException();
/*    */     }
/*    */     
/* 85 */     return new DecimalFormatSymbols(paramLocale);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> getAvailableLanguageTags() {
/* 90 */     return this.langtags;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/DecimalFormatSymbolsProviderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */