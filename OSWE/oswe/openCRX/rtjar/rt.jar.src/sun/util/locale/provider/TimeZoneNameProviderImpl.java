/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
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
/*     */ public class TimeZoneNameProviderImpl
/*     */   extends TimeZoneNameProvider
/*     */ {
/*     */   private final LocaleProviderAdapter.Type type;
/*     */   private final Set<String> langtags;
/*     */   
/*     */   TimeZoneNameProviderImpl(LocaleProviderAdapter.Type paramType, Set<String> paramSet) {
/*  47 */     this.type = paramType;
/*  48 */     this.langtags = paramSet;
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
/*  60 */     return LocaleProviderAdapter.toLocaleArray(this.langtags);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportedLocale(Locale paramLocale) {
/*  65 */     return LocaleProviderAdapter.isSupportedLocale(paramLocale, this.type, this.langtags);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName(String paramString, boolean paramBoolean, int paramInt, Locale paramLocale) {
/*  99 */     String[] arrayOfString = getDisplayNameArray(paramString, paramLocale);
/* 100 */     if (Objects.nonNull(arrayOfString)) {
/* 101 */       assert arrayOfString.length >= 7;
/* 102 */       byte b = paramBoolean ? 3 : 1;
/* 103 */       if (paramInt == 0) {
/* 104 */         b++;
/*     */       }
/* 106 */       return arrayOfString[b];
/*     */     } 
/* 108 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGenericDisplayName(String paramString, int paramInt, Locale paramLocale) {
/* 113 */     String[] arrayOfString = getDisplayNameArray(paramString, paramLocale);
/* 114 */     if (Objects.nonNull(arrayOfString)) {
/* 115 */       assert arrayOfString.length >= 7;
/* 116 */       return arrayOfString[(paramInt == 1) ? 5 : 6];
/*     */     } 
/* 118 */     return null;
/*     */   }
/*     */   
/*     */   private String[] getDisplayNameArray(String paramString, Locale paramLocale) {
/* 122 */     Objects.requireNonNull(paramString);
/* 123 */     Objects.requireNonNull(paramLocale);
/* 124 */     return LocaleProviderAdapter.forType(this.type).getLocaleResources(paramLocale).getTimeZoneNames(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String[][] getZoneStrings(Locale paramLocale) {
/* 135 */     return LocaleProviderAdapter.forType(this.type).getLocaleResources(paramLocale).getZoneStrings();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/TimeZoneNameProviderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */