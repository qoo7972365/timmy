/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.spi.NumberFormatProvider;
/*     */ import java.util.Currency;
/*     */ import java.util.Locale;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NumberFormatProviderImpl
/*     */   extends NumberFormatProvider
/*     */   implements AvailableLanguageTags
/*     */ {
/*     */   private static final int NUMBERSTYLE = 0;
/*     */   private static final int CURRENCYSTYLE = 1;
/*     */   private static final int PERCENTSTYLE = 2;
/*     */   private static final int SCIENTIFICSTYLE = 3;
/*     */   private static final int INTEGERSTYLE = 4;
/*     */   private final LocaleProviderAdapter.Type type;
/*     */   private final Set<String> langtags;
/*     */   
/*     */   public NumberFormatProviderImpl(LocaleProviderAdapter.Type paramType, Set<String> paramSet) {
/*  71 */     this.type = paramType;
/*  72 */     this.langtags = paramSet;
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
/*  84 */     return LocaleProviderAdapter.forType(this.type).getAvailableLocales();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportedLocale(Locale paramLocale) {
/*  89 */     return LocaleProviderAdapter.isSupportedLocale(paramLocale, this.type, this.langtags);
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
/*     */   public NumberFormat getCurrencyInstance(Locale paramLocale) {
/* 107 */     return getInstance(paramLocale, 1);
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
/*     */   public NumberFormat getIntegerInstance(Locale paramLocale) {
/* 131 */     return getInstance(paramLocale, 4);
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
/*     */   public NumberFormat getNumberInstance(Locale paramLocale) {
/* 149 */     return getInstance(paramLocale, 0);
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
/*     */   public NumberFormat getPercentInstance(Locale paramLocale) {
/* 167 */     return getInstance(paramLocale, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   private NumberFormat getInstance(Locale paramLocale, int paramInt) {
/* 172 */     if (paramLocale == null) {
/* 173 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 176 */     LocaleProviderAdapter localeProviderAdapter = LocaleProviderAdapter.forType(this.type);
/* 177 */     String[] arrayOfString = localeProviderAdapter.getLocaleResources(paramLocale).getNumberPatterns();
/* 178 */     DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(paramLocale);
/* 179 */     boolean bool = (paramInt == 4) ? false : paramInt;
/* 180 */     DecimalFormat decimalFormat = new DecimalFormat(arrayOfString[bool], decimalFormatSymbols);
/*     */     
/* 182 */     if (paramInt == 4) {
/* 183 */       decimalFormat.setMaximumFractionDigits(0);
/* 184 */       decimalFormat.setDecimalSeparatorAlwaysShown(false);
/* 185 */       decimalFormat.setParseIntegerOnly(true);
/* 186 */     } else if (paramInt == 1) {
/* 187 */       adjustForCurrencyDefaultFractionDigits(decimalFormat, decimalFormatSymbols);
/*     */     } 
/*     */     
/* 190 */     return decimalFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void adjustForCurrencyDefaultFractionDigits(DecimalFormat paramDecimalFormat, DecimalFormatSymbols paramDecimalFormatSymbols) {
/* 199 */     Currency currency = paramDecimalFormatSymbols.getCurrency();
/* 200 */     if (currency == null) {
/*     */       try {
/* 202 */         currency = Currency.getInstance(paramDecimalFormatSymbols.getInternationalCurrencySymbol());
/* 203 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*     */     }
/*     */     
/* 206 */     if (currency != null) {
/* 207 */       int i = currency.getDefaultFractionDigits();
/* 208 */       if (i != -1) {
/* 209 */         int j = paramDecimalFormat.getMinimumFractionDigits();
/*     */ 
/*     */         
/* 212 */         if (j == paramDecimalFormat.getMaximumFractionDigits()) {
/* 213 */           paramDecimalFormat.setMinimumFractionDigits(i);
/* 214 */           paramDecimalFormat.setMaximumFractionDigits(i);
/*     */         } else {
/* 216 */           paramDecimalFormat.setMinimumFractionDigits(Math.min(i, j));
/* 217 */           paramDecimalFormat.setMaximumFractionDigits(i);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getAvailableLanguageTags() {
/* 225 */     return this.langtags;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/NumberFormatProviderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */