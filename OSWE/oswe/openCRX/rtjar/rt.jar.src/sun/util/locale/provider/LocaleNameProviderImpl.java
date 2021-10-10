/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.spi.LocaleNameProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocaleNameProviderImpl
/*     */   extends LocaleNameProvider
/*     */   implements AvailableLanguageTags
/*     */ {
/*     */   private final LocaleProviderAdapter.Type type;
/*     */   private final Set<String> langtags;
/*     */   
/*     */   public LocaleNameProviderImpl(LocaleProviderAdapter.Type paramType, Set<String> paramSet) {
/*  45 */     this.type = paramType;
/*  46 */     this.langtags = paramSet;
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
/*  58 */     return LocaleProviderAdapter.toLocaleArray(this.langtags);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportedLocale(Locale paramLocale) {
/*  63 */     return LocaleProviderAdapter.isSupportedLocale(paramLocale, this.type, this.langtags);
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
/*     */   public String getDisplayLanguage(String paramString, Locale paramLocale) {
/*  90 */     return getDisplayString(paramString, paramLocale);
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
/*     */   public String getDisplayScript(String paramString, Locale paramLocale) {
/* 120 */     return getDisplayString(paramString, paramLocale);
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
/*     */   public String getDisplayCountry(String paramString, Locale paramLocale) {
/* 147 */     return getDisplayString(paramString, paramLocale);
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
/*     */   public String getDisplayVariant(String paramString, Locale paramLocale) {
/* 168 */     return getDisplayString("%%" + paramString, paramLocale);
/*     */   }
/*     */   
/*     */   private String getDisplayString(String paramString, Locale paramLocale) {
/* 172 */     if (paramString == null || paramLocale == null) {
/* 173 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 176 */     return LocaleProviderAdapter.forType(this.type).getLocaleResources(paramLocale).getLocaleName(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getAvailableLanguageTags() {
/* 181 */     return this.langtags;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/LocaleNameProviderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */