/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.spi.CurrencyNameProvider;
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
/*     */ public class CurrencyNameProviderImpl
/*     */   extends CurrencyNameProvider
/*     */   implements AvailableLanguageTags
/*     */ {
/*     */   private final LocaleProviderAdapter.Type type;
/*     */   private final Set<String> langtags;
/*     */   
/*     */   public CurrencyNameProviderImpl(LocaleProviderAdapter.Type paramType, Set<String> paramSet) {
/*  46 */     this.type = paramType;
/*  47 */     this.langtags = paramSet;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getAvailableLanguageTags() {
/*  52 */     return this.langtags;
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
/*  64 */     return LocaleProviderAdapter.toLocaleArray(this.langtags);
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
/*     */   public String getSymbol(String paramString, Locale paramLocale) {
/*  90 */     return getString(paramString.toUpperCase(Locale.ROOT), paramLocale);
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
/*     */   public String getDisplayName(String paramString, Locale paramLocale) {
/* 114 */     return getString(paramString.toLowerCase(Locale.ROOT), paramLocale);
/*     */   }
/*     */   
/*     */   private String getString(String paramString, Locale paramLocale) {
/* 118 */     if (paramLocale == null) {
/* 119 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 122 */     return LocaleProviderAdapter.forType(this.type).getLocaleResources(paramLocale).getCurrencyName(paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/CurrencyNameProviderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */