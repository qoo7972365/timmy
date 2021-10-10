/*     */ package java.util.spi;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CurrencyNameProvider
/*     */   extends LocaleServiceProvider
/*     */ {
/*     */   public abstract String getSymbol(String paramString, Locale paramLocale);
/*     */   
/*     */   public String getDisplayName(String paramString, Locale paramLocale) {
/*  97 */     if (paramString == null || paramLocale == null) {
/*  98 */       throw new NullPointerException();
/*     */     }
/*     */ 
/*     */     
/* 102 */     char[] arrayOfChar = paramString.toCharArray();
/* 103 */     if (arrayOfChar.length != 3) {
/* 104 */       throw new IllegalArgumentException("The currencyCode is not in the form of three upper-case letters.");
/*     */     }
/* 106 */     for (char c : arrayOfChar) {
/* 107 */       if (c < 'A' || c > 'Z') {
/* 108 */         throw new IllegalArgumentException("The currencyCode is not in the form of three upper-case letters.");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 113 */     ResourceBundle.Control control = ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT);
/* 114 */     for (Locale locale : getAvailableLocales()) {
/* 115 */       if (control.getCandidateLocales("", locale).contains(paramLocale)) {
/* 116 */         return null;
/*     */       }
/*     */     } 
/*     */     
/* 120 */     throw new IllegalArgumentException("The locale is not available");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/spi/CurrencyNameProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */