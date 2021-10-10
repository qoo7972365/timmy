/*     */ package java.time.format;
/*     */ 
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DecimalStyle
/*     */ {
/*  91 */   public static final DecimalStyle STANDARD = new DecimalStyle('0', '+', '-', '.');
/*     */ 
/*     */ 
/*     */   
/*  95 */   private static final ConcurrentMap<Locale, DecimalStyle> CACHE = new ConcurrentHashMap<>(16, 0.75F, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final char zeroDigit;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final char positiveSign;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final char negativeSign;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final char decimalSeparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<Locale> getAvailableLocales() {
/* 123 */     Locale[] arrayOfLocale = DecimalFormatSymbols.getAvailableLocales();
/* 124 */     HashSet<? super Locale> hashSet = new HashSet(arrayOfLocale.length);
/* 125 */     Collections.addAll(hashSet, arrayOfLocale);
/* 126 */     return (Set)hashSet;
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
/*     */   public static DecimalStyle ofDefaultLocale() {
/* 143 */     return of(Locale.getDefault(Locale.Category.FORMAT));
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
/*     */   public static DecimalStyle of(Locale paramLocale) {
/* 155 */     Objects.requireNonNull(paramLocale, "locale");
/* 156 */     DecimalStyle decimalStyle = CACHE.get(paramLocale);
/* 157 */     if (decimalStyle == null) {
/* 158 */       decimalStyle = create(paramLocale);
/* 159 */       CACHE.putIfAbsent(paramLocale, decimalStyle);
/* 160 */       decimalStyle = CACHE.get(paramLocale);
/*     */     } 
/* 162 */     return decimalStyle;
/*     */   }
/*     */   
/*     */   private static DecimalStyle create(Locale paramLocale) {
/* 166 */     DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(paramLocale);
/* 167 */     char c1 = decimalFormatSymbols.getZeroDigit();
/* 168 */     byte b = 43;
/* 169 */     char c2 = decimalFormatSymbols.getMinusSign();
/* 170 */     char c3 = decimalFormatSymbols.getDecimalSeparator();
/* 171 */     if (c1 == '0' && c2 == '-' && c3 == '.') {
/* 172 */       return STANDARD;
/*     */     }
/* 174 */     return new DecimalStyle(c1, b, c2, c3);
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
/*     */   private DecimalStyle(char paramChar1, char paramChar2, char paramChar3, char paramChar4) {
/* 187 */     this.zeroDigit = paramChar1;
/* 188 */     this.positiveSign = paramChar2;
/* 189 */     this.negativeSign = paramChar3;
/* 190 */     this.decimalSeparator = paramChar4;
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
/*     */   public char getZeroDigit() {
/* 203 */     return this.zeroDigit;
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
/*     */   public DecimalStyle withZeroDigit(char paramChar) {
/* 217 */     if (paramChar == this.zeroDigit) {
/* 218 */       return this;
/*     */     }
/* 220 */     return new DecimalStyle(paramChar, this.positiveSign, this.negativeSign, this.decimalSeparator);
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
/*     */   public char getPositiveSign() {
/* 233 */     return this.positiveSign;
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
/*     */   public DecimalStyle withPositiveSign(char paramChar) {
/* 246 */     if (paramChar == this.positiveSign) {
/* 247 */       return this;
/*     */     }
/* 249 */     return new DecimalStyle(this.zeroDigit, paramChar, this.negativeSign, this.decimalSeparator);
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
/*     */   public char getNegativeSign() {
/* 262 */     return this.negativeSign;
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
/*     */   public DecimalStyle withNegativeSign(char paramChar) {
/* 275 */     if (paramChar == this.negativeSign) {
/* 276 */       return this;
/*     */     }
/* 278 */     return new DecimalStyle(this.zeroDigit, this.positiveSign, paramChar, this.decimalSeparator);
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
/*     */   public char getDecimalSeparator() {
/* 291 */     return this.decimalSeparator;
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
/*     */   public DecimalStyle withDecimalSeparator(char paramChar) {
/* 304 */     if (paramChar == this.decimalSeparator) {
/* 305 */       return this;
/*     */     }
/* 307 */     return new DecimalStyle(this.zeroDigit, this.positiveSign, this.negativeSign, paramChar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int convertToDigit(char paramChar) {
/* 318 */     int i = paramChar - this.zeroDigit;
/* 319 */     return (i >= 0 && i <= 9) ? i : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String convertNumberToI18N(String paramString) {
/* 329 */     if (this.zeroDigit == '0') {
/* 330 */       return paramString;
/*     */     }
/* 332 */     int i = this.zeroDigit - 48;
/* 333 */     char[] arrayOfChar = paramString.toCharArray();
/* 334 */     for (byte b = 0; b < arrayOfChar.length; b++) {
/* 335 */       arrayOfChar[b] = (char)(arrayOfChar[b] + i);
/*     */     }
/* 337 */     return new String(arrayOfChar);
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
/*     */   public boolean equals(Object paramObject) {
/* 349 */     if (this == paramObject) {
/* 350 */       return true;
/*     */     }
/* 352 */     if (paramObject instanceof DecimalStyle) {
/* 353 */       DecimalStyle decimalStyle = (DecimalStyle)paramObject;
/* 354 */       return (this.zeroDigit == decimalStyle.zeroDigit && this.positiveSign == decimalStyle.positiveSign && this.negativeSign == decimalStyle.negativeSign && this.decimalSeparator == decimalStyle.decimalSeparator);
/*     */     } 
/*     */     
/* 357 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 367 */     return this.zeroDigit + this.positiveSign + this.negativeSign + this.decimalSeparator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 378 */     return "DecimalStyle[" + this.zeroDigit + this.positiveSign + this.negativeSign + this.decimalSeparator + "]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/format/DecimalStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */