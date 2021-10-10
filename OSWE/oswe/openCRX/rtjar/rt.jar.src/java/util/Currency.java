/*     */ package java.util;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.spi.CurrencyNameProvider;
/*     */ import java.util.spi.LocaleServiceProvider;
/*     */ import sun.util.locale.provider.LocaleServiceProviderPool;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Currency
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -158308464356906721L;
/*     */   private final String currencyCode;
/*     */   private final transient int defaultFractionDigits;
/*     */   private final transient int numericCode;
/* 123 */   private static ConcurrentMap<String, Currency> instances = new ConcurrentHashMap<>(7);
/*     */ 
/*     */   
/*     */   private static HashSet<Currency> available;
/*     */ 
/*     */   
/*     */   static int formatVersion;
/*     */ 
/*     */   
/*     */   static int dataVersion;
/*     */ 
/*     */   
/*     */   static int[] mainTable;
/*     */ 
/*     */   
/*     */   static long[] scCutOverTimes;
/*     */ 
/*     */   
/*     */   static String[] scOldCurrencies;
/*     */ 
/*     */   
/*     */   static String[] scNewCurrencies;
/*     */ 
/*     */   
/*     */   static int[] scOldCurrenciesDFD;
/*     */ 
/*     */   
/*     */   static int[] scNewCurrenciesDFD;
/*     */ 
/*     */   
/*     */   static int[] scOldCurrenciesNumericCode;
/*     */ 
/*     */   
/*     */   static int[] scNewCurrenciesNumericCode;
/*     */ 
/*     */   
/*     */   static String otherCurrencies;
/*     */ 
/*     */   
/*     */   static int[] otherCurrenciesDFD;
/*     */ 
/*     */   
/*     */   static int[] otherCurrenciesNumericCode;
/*     */ 
/*     */   
/*     */   private static final int MAGIC_NUMBER = 1131770436;
/*     */ 
/*     */   
/*     */   private static final int A_TO_Z = 26;
/*     */ 
/*     */   
/*     */   private static final int INVALID_COUNTRY_ENTRY = 127;
/*     */ 
/*     */   
/*     */   private static final int COUNTRY_WITHOUT_CURRENCY_ENTRY = 512;
/*     */ 
/*     */   
/*     */   private static final int SIMPLE_CASE_COUNTRY_MASK = 0;
/*     */ 
/*     */   
/*     */   private static final int SIMPLE_CASE_COUNTRY_FINAL_CHAR_MASK = 31;
/*     */ 
/*     */   
/*     */   private static final int SIMPLE_CASE_COUNTRY_DEFAULT_DIGITS_MASK = 480;
/*     */ 
/*     */   
/*     */   private static final int SIMPLE_CASE_COUNTRY_DEFAULT_DIGITS_SHIFT = 5;
/*     */ 
/*     */   
/*     */   private static final int SIMPLE_CASE_COUNTRY_MAX_DEFAULT_DIGITS = 9;
/*     */   
/*     */   private static final int SPECIAL_CASE_COUNTRY_MASK = 512;
/*     */   
/*     */   private static final int SPECIAL_CASE_COUNTRY_INDEX_MASK = 31;
/*     */   
/*     */   private static final int SPECIAL_CASE_COUNTRY_INDEX_DELTA = 1;
/*     */   
/*     */   private static final int COUNTRY_TYPE_MASK = 512;
/*     */   
/*     */   private static final int NUMERIC_CODE_MASK = 1047552;
/*     */   
/*     */   private static final int NUMERIC_CODE_SHIFT = 10;
/*     */   
/*     */   private static final int VALID_FORMAT_VERSION = 2;
/*     */   
/*     */   private static final int SYMBOL = 0;
/*     */   
/*     */   private static final int DISPLAYNAME = 1;
/*     */ 
/*     */   
/*     */   static {
/* 214 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 217 */             String str1 = System.getProperty("java.home");
/*     */             try {
/* 219 */               String str = str1 + File.separator + "lib" + File.separator + "currency.data";
/*     */               
/* 221 */               try (DataInputStream null = new DataInputStream(new BufferedInputStream(new FileInputStream(str)))) {
/*     */ 
/*     */                 
/* 224 */                 if (dataInputStream.readInt() != 1131770436) {
/* 225 */                   throw new InternalError("Currency data is possibly corrupted");
/*     */                 }
/* 227 */                 Currency.formatVersion = dataInputStream.readInt();
/* 228 */                 if (Currency.formatVersion != 2) {
/* 229 */                   throw new InternalError("Currency data format is incorrect");
/*     */                 }
/* 231 */                 Currency.dataVersion = dataInputStream.readInt();
/* 232 */                 Currency.mainTable = Currency.readIntArray(dataInputStream, 676);
/* 233 */                 int i = dataInputStream.readInt();
/* 234 */                 Currency.scCutOverTimes = Currency.readLongArray(dataInputStream, i);
/* 235 */                 Currency.scOldCurrencies = Currency.readStringArray(dataInputStream, i);
/* 236 */                 Currency.scNewCurrencies = Currency.readStringArray(dataInputStream, i);
/* 237 */                 Currency.scOldCurrenciesDFD = Currency.readIntArray(dataInputStream, i);
/* 238 */                 Currency.scNewCurrenciesDFD = Currency.readIntArray(dataInputStream, i);
/* 239 */                 Currency.scOldCurrenciesNumericCode = Currency.readIntArray(dataInputStream, i);
/* 240 */                 Currency.scNewCurrenciesNumericCode = Currency.readIntArray(dataInputStream, i);
/* 241 */                 int j = dataInputStream.readInt();
/* 242 */                 Currency.otherCurrencies = dataInputStream.readUTF();
/* 243 */                 Currency.otherCurrenciesDFD = Currency.readIntArray(dataInputStream, j);
/* 244 */                 Currency.otherCurrenciesNumericCode = Currency.readIntArray(dataInputStream, j);
/*     */               } 
/* 246 */             } catch (IOException iOException) {
/* 247 */               throw new InternalError(iOException);
/*     */             } 
/*     */ 
/*     */             
/* 251 */             String str2 = System.getProperty("java.util.currency.data");
/* 252 */             if (str2 == null) {
/* 253 */               str2 = str1 + File.separator + "lib" + File.separator + "currency.properties";
/*     */             }
/*     */             
/*     */             try {
/* 257 */               File file = new File(str2);
/* 258 */               if (file.exists()) {
/* 259 */                 Properties properties = new Properties();
/* 260 */                 try (FileReader null = new FileReader(file)) {
/* 261 */                   properties.load(fileReader);
/*     */                 } 
/* 263 */                 Set<String> set = properties.stringPropertyNames();
/*     */                 
/* 265 */                 Pattern pattern = Pattern.compile("([A-Z]{3})\\s*,\\s*(\\d{3})\\s*,\\s*(\\d+)\\s*,?\\s*(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})?");
/*     */ 
/*     */                 
/* 268 */                 for (String str : set) {
/* 269 */                   Currency.replaceCurrencyData(pattern, str
/* 270 */                       .toUpperCase(Locale.ROOT), properties
/* 271 */                       .getProperty(str).toUpperCase(Locale.ROOT));
/*     */                 }
/*     */               } 
/* 274 */             } catch (IOException iOException) {
/* 275 */               Currency.info("currency.properties is ignored because of an IOException", iOException);
/*     */             } 
/* 277 */             return null;
/*     */           }
/*     */         });
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
/*     */   private Currency(String paramString, int paramInt1, int paramInt2) {
/* 295 */     this.currencyCode = paramString;
/* 296 */     this.defaultFractionDigits = paramInt1;
/* 297 */     this.numericCode = paramInt2;
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
/*     */   public static Currency getInstance(String paramString) {
/* 310 */     return getInstance(paramString, -2147483648, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Currency getInstance(String paramString, int paramInt1, int paramInt2) {
/* 318 */     Currency currency1 = instances.get(paramString);
/* 319 */     if (currency1 != null) {
/* 320 */       return currency1;
/*     */     }
/*     */     
/* 323 */     if (paramInt1 == Integer.MIN_VALUE) {
/*     */ 
/*     */ 
/*     */       
/* 327 */       if (paramString.length() != 3) {
/* 328 */         throw new IllegalArgumentException();
/*     */       }
/* 330 */       char c1 = paramString.charAt(0);
/* 331 */       char c2 = paramString.charAt(1);
/* 332 */       int i = getMainTableEntry(c1, c2);
/* 333 */       if ((i & 0x200) == 0 && i != 127 && paramString
/*     */         
/* 335 */         .charAt(2) - 65 == (i & 0x1F)) {
/* 336 */         paramInt1 = (i & 0x1E0) >> 5;
/* 337 */         paramInt2 = (i & 0xFFC00) >> 10;
/*     */       } else {
/*     */         
/* 340 */         if (paramString.charAt(2) == '-') {
/* 341 */           throw new IllegalArgumentException();
/*     */         }
/* 343 */         int j = otherCurrencies.indexOf(paramString);
/* 344 */         if (j == -1) {
/* 345 */           throw new IllegalArgumentException();
/*     */         }
/* 347 */         paramInt1 = otherCurrenciesDFD[j / 4];
/* 348 */         paramInt2 = otherCurrenciesNumericCode[j / 4];
/*     */       } 
/*     */     } 
/*     */     
/* 352 */     Currency currency2 = new Currency(paramString, paramInt1, paramInt2);
/*     */     
/* 354 */     currency1 = instances.putIfAbsent(paramString, currency2);
/* 355 */     return (currency1 != null) ? currency1 : currency2;
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
/*     */   public static Currency getInstance(Locale paramLocale) {
/* 380 */     String str = paramLocale.getCountry();
/* 381 */     if (str == null) {
/* 382 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 385 */     if (str.length() != 2) {
/* 386 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 389 */     char c1 = str.charAt(0);
/* 390 */     char c2 = str.charAt(1);
/* 391 */     int i = getMainTableEntry(c1, c2);
/* 392 */     if ((i & 0x200) == 0 && i != 127) {
/*     */       
/* 394 */       char c = (char)((i & 0x1F) + 65);
/* 395 */       int k = (i & 0x1E0) >> 5;
/* 396 */       int m = (i & 0xFFC00) >> 10;
/* 397 */       StringBuilder stringBuilder = new StringBuilder(str);
/* 398 */       stringBuilder.append(c);
/* 399 */       return getInstance(stringBuilder.toString(), k, m);
/*     */     } 
/*     */     
/* 402 */     if (i == 127) {
/* 403 */       throw new IllegalArgumentException();
/*     */     }
/* 405 */     if (i == 512) {
/* 406 */       return null;
/*     */     }
/* 408 */     int j = (i & 0x1F) - 1;
/* 409 */     if (scCutOverTimes[j] == Long.MAX_VALUE || System.currentTimeMillis() < scCutOverTimes[j]) {
/* 410 */       return getInstance(scOldCurrencies[j], scOldCurrenciesDFD[j], scOldCurrenciesNumericCode[j]);
/*     */     }
/*     */     
/* 413 */     return getInstance(scNewCurrencies[j], scNewCurrenciesDFD[j], scNewCurrenciesNumericCode[j]);
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
/*     */   public static Set<Currency> getAvailableCurrencies() {
/* 431 */     synchronized (Currency.class) {
/* 432 */       if (available == null) {
/* 433 */         available = new HashSet<>(256);
/*     */ 
/*     */         
/* 436 */         for (char c = 'A'; c <= 'Z'; c = (char)(c + 1)) {
/* 437 */           for (char c1 = 'A'; c1 <= 'Z'; c1 = (char)(c1 + 1)) {
/* 438 */             int i = getMainTableEntry(c, c1);
/* 439 */             if ((i & 0x200) == 0 && i != 127) {
/*     */               
/* 441 */               char c2 = (char)((i & 0x1F) + 65);
/* 442 */               int j = (i & 0x1E0) >> 5;
/* 443 */               int k = (i & 0xFFC00) >> 10;
/* 444 */               StringBuilder stringBuilder = new StringBuilder();
/* 445 */               stringBuilder.append(c);
/* 446 */               stringBuilder.append(c1);
/* 447 */               stringBuilder.append(c2);
/* 448 */               available.add(getInstance(stringBuilder.toString(), j, k));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 454 */         StringTokenizer stringTokenizer = new StringTokenizer(otherCurrencies, "-");
/* 455 */         while (stringTokenizer.hasMoreElements()) {
/* 456 */           available.add(getInstance((String)stringTokenizer.nextElement()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 462 */     return (Set)available.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCurrencyCode() {
/* 472 */     return this.currencyCode;
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
/*     */   public String getSymbol() {
/* 490 */     return getSymbol(Locale.getDefault(Locale.Category.DISPLAY));
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
/*     */   public String getSymbol(Locale paramLocale) {
/* 506 */     LocaleServiceProviderPool localeServiceProviderPool = LocaleServiceProviderPool.getPool((Class)CurrencyNameProvider.class);
/* 507 */     String str = localeServiceProviderPool.<LocaleServiceProvider, String>getLocalizedObject(CurrencyNameGetter
/* 508 */         .INSTANCE, paramLocale, this.currencyCode, new Object[] {
/* 509 */           Integer.valueOf(0) });
/* 510 */     if (str != null) {
/* 511 */       return str;
/*     */     }
/*     */ 
/*     */     
/* 515 */     return this.currencyCode;
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
/*     */   public int getDefaultFractionDigits() {
/* 528 */     return this.defaultFractionDigits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumericCode() {
/* 538 */     return this.numericCode;
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
/*     */   public String getDisplayName() {
/* 556 */     return getDisplayName(Locale.getDefault(Locale.Category.DISPLAY));
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
/*     */   public String getDisplayName(Locale paramLocale) {
/* 572 */     LocaleServiceProviderPool localeServiceProviderPool = LocaleServiceProviderPool.getPool((Class)CurrencyNameProvider.class);
/* 573 */     String str = localeServiceProviderPool.<LocaleServiceProvider, String>getLocalizedObject(CurrencyNameGetter
/* 574 */         .INSTANCE, paramLocale, this.currencyCode, new Object[] {
/* 575 */           Integer.valueOf(1) });
/* 576 */     if (str != null) {
/* 577 */       return str;
/*     */     }
/*     */ 
/*     */     
/* 581 */     return this.currencyCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 591 */     return this.currencyCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 598 */     return getInstance(this.currencyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getMainTableEntry(char paramChar1, char paramChar2) {
/* 606 */     if (paramChar1 < 'A' || paramChar1 > 'Z' || paramChar2 < 'A' || paramChar2 > 'Z') {
/* 607 */       throw new IllegalArgumentException();
/*     */     }
/* 609 */     return mainTable[(paramChar1 - 65) * 26 + paramChar2 - 65];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setMainTableEntry(char paramChar1, char paramChar2, int paramInt) {
/* 617 */     if (paramChar1 < 'A' || paramChar1 > 'Z' || paramChar2 < 'A' || paramChar2 > 'Z') {
/* 618 */       throw new IllegalArgumentException();
/*     */     }
/* 620 */     mainTable[(paramChar1 - 65) * 26 + paramChar2 - 65] = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CurrencyNameGetter
/*     */     implements LocaleServiceProviderPool.LocalizedObjectGetter<CurrencyNameProvider, String>
/*     */   {
/* 630 */     private static final CurrencyNameGetter INSTANCE = new CurrencyNameGetter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getObject(CurrencyNameProvider param1CurrencyNameProvider, Locale param1Locale, String param1String, Object... param1VarArgs) {
/* 637 */       assert param1VarArgs.length == 1;
/* 638 */       int i = ((Integer)param1VarArgs[0]).intValue();
/*     */       
/* 640 */       switch (i) {
/*     */         case 0:
/* 642 */           return param1CurrencyNameProvider.getSymbol(param1String, param1Locale);
/*     */         case 1:
/* 644 */           return param1CurrencyNameProvider.getDisplayName(param1String, param1Locale);
/*     */       } 
/*     */ 
/*     */       
/*     */       assert false;
/* 649 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   private static int[] readIntArray(DataInputStream paramDataInputStream, int paramInt) throws IOException {
/* 654 */     int[] arrayOfInt = new int[paramInt];
/* 655 */     for (byte b = 0; b < paramInt; b++) {
/* 656 */       arrayOfInt[b] = paramDataInputStream.readInt();
/*     */     }
/*     */     
/* 659 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   private static long[] readLongArray(DataInputStream paramDataInputStream, int paramInt) throws IOException {
/* 663 */     long[] arrayOfLong = new long[paramInt];
/* 664 */     for (byte b = 0; b < paramInt; b++) {
/* 665 */       arrayOfLong[b] = paramDataInputStream.readLong();
/*     */     }
/*     */     
/* 668 */     return arrayOfLong;
/*     */   }
/*     */   
/*     */   private static String[] readStringArray(DataInputStream paramDataInputStream, int paramInt) throws IOException {
/* 672 */     String[] arrayOfString = new String[paramInt];
/* 673 */     for (byte b = 0; b < paramInt; b++) {
/* 674 */       arrayOfString[b] = paramDataInputStream.readUTF();
/*     */     }
/*     */     
/* 677 */     return arrayOfString;
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
/*     */   private static void replaceCurrencyData(Pattern paramPattern, String paramString1, String paramString2) {
/* 696 */     if (paramString1.length() != 2) {
/*     */       
/* 698 */       info("currency.properties entry for " + paramString1 + " is ignored because of the invalid country code.", null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 703 */     Matcher matcher = paramPattern.matcher(paramString2);
/* 704 */     if (!matcher.find() || (matcher.group(4) == null && countOccurrences(paramString2, ',') >= 3)) {
/*     */ 
/*     */       
/* 707 */       info("currency.properties entry for " + paramString1 + " ignored because the value format is not recognized.", null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*     */     try {
/* 713 */       if (matcher.group(4) != null && !isPastCutoverDate(matcher.group(4))) {
/* 714 */         info("currency.properties entry for " + paramString1 + " ignored since cutover date has not passed :" + paramString2, null);
/*     */         
/*     */         return;
/*     */       } 
/* 718 */     } catch (ParseException parseException) {
/* 719 */       info("currency.properties entry for " + paramString1 + " ignored since exception encountered :" + parseException
/* 720 */           .getMessage(), null);
/*     */       
/*     */       return;
/*     */     } 
/* 724 */     String str = matcher.group(1);
/* 725 */     int i = Integer.parseInt(matcher.group(2));
/* 726 */     int j = i << 10;
/* 727 */     int k = Integer.parseInt(matcher.group(3));
/* 728 */     if (k > 9) {
/* 729 */       info("currency.properties entry for " + paramString1 + " ignored since the fraction is more than " + '\t' + ":" + paramString2, null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*     */     byte b;
/*     */     
/* 736 */     for (b = 0; b < scOldCurrencies.length && 
/* 737 */       !scOldCurrencies[b].equals(str); b++);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 742 */     if (b == scOldCurrencies.length) {
/*     */       
/* 744 */       j |= k << 5 | str
/* 745 */         .charAt(2) - 65;
/*     */     } else {
/*     */       
/* 748 */       j |= 0x200 | b + 1;
/*     */     } 
/*     */     
/* 751 */     setMainTableEntry(paramString1.charAt(0), paramString1.charAt(1), j);
/*     */   }
/*     */   
/*     */   private static boolean isPastCutoverDate(String paramString) throws ParseException {
/* 755 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ROOT);
/* 756 */     simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
/* 757 */     simpleDateFormat.setLenient(false);
/* 758 */     long l = simpleDateFormat.parse(paramString.trim()).getTime();
/* 759 */     return (System.currentTimeMillis() > l);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int countOccurrences(String paramString, char paramChar) {
/* 764 */     byte b = 0;
/* 765 */     for (char c : paramString.toCharArray()) {
/* 766 */       if (c == paramChar) {
/* 767 */         b++;
/*     */       }
/*     */     } 
/* 770 */     return b;
/*     */   }
/*     */   
/*     */   private static void info(String paramString, Throwable paramThrowable) {
/* 774 */     PlatformLogger platformLogger = PlatformLogger.getLogger("java.util.Currency");
/* 775 */     if (platformLogger.isLoggable(PlatformLogger.Level.INFO))
/* 776 */       if (paramThrowable != null) {
/* 777 */         platformLogger.info(paramString, paramThrowable);
/*     */       } else {
/* 779 */         platformLogger.info(paramString);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Currency.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */