/*     */ package java.text;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.text.spi.DateFormatSymbolsProvider;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import sun.util.locale.provider.LocaleProviderAdapter;
/*     */ import sun.util.locale.provider.LocaleServiceProviderPool;
/*     */ import sun.util.locale.provider.ResourceBundleBasedAdapter;
/*     */ import sun.util.locale.provider.TimeZoneNameUtility;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DateFormatSymbols
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   String[] eras;
/*     */   String[] months;
/*     */   String[] shortMonths;
/*     */   String[] weekdays;
/*     */   String[] shortWeekdays;
/*     */   String[] ampms;
/*     */   String[][] zoneStrings;
/*     */   transient boolean isZoneStringsSet;
/*     */   static final String patternChars = "GyMdkHmsSEDFwWahKzZYuXL";
/*     */   static final int PATTERN_ERA = 0;
/*     */   static final int PATTERN_YEAR = 1;
/*     */   static final int PATTERN_MONTH = 2;
/*     */   static final int PATTERN_DAY_OF_MONTH = 3;
/*     */   static final int PATTERN_HOUR_OF_DAY1 = 4;
/*     */   static final int PATTERN_HOUR_OF_DAY0 = 5;
/*     */   static final int PATTERN_MINUTE = 6;
/*     */   static final int PATTERN_SECOND = 7;
/*     */   static final int PATTERN_MILLISECOND = 8;
/*     */   static final int PATTERN_DAY_OF_WEEK = 9;
/*     */   static final int PATTERN_DAY_OF_YEAR = 10;
/*     */   static final int PATTERN_DAY_OF_WEEK_IN_MONTH = 11;
/*     */   static final int PATTERN_WEEK_OF_YEAR = 12;
/*     */   static final int PATTERN_WEEK_OF_MONTH = 13;
/*     */   static final int PATTERN_AM_PM = 14;
/*     */   static final int PATTERN_HOUR1 = 15;
/*     */   static final int PATTERN_HOUR0 = 16;
/*     */   static final int PATTERN_ZONE_NAME = 17;
/*     */   static final int PATTERN_ZONE_VALUE = 18;
/*     */   static final int PATTERN_WEEK_YEAR = 19;
/*     */   static final int PATTERN_ISO_DAY_OF_WEEK = 20;
/*     */   static final int PATTERN_ISO_ZONE = 21;
/*     */   static final int PATTERN_MONTH_STANDALONE = 22;
/*     */   String localPatternChars;
/*     */   Locale locale;
/*     */   static final long serialVersionUID = -5987973545549424702L;
/*     */   static final int millisPerHour = 3600000;
/*     */   
/*     */   public DateFormatSymbols() {
/* 159 */     this.eras = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     this.months = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     this.shortMonths = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     this.weekdays = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     this.shortWeekdays = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     this.ampms = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     this.zoneStrings = (String[][])null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     this.isZoneStringsSet = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 276 */     this.localPatternChars = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     this.locale = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 678 */     this.lastZoneIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 683 */     this.cachedHashCode = 0; initializeData(Locale.getDefault(Locale.Category.FORMAT)); } public DateFormatSymbols(Locale paramLocale) { this.eras = null; this.months = null; this.shortMonths = null; this.weekdays = null; this.shortWeekdays = null; this.ampms = null; this.zoneStrings = (String[][])null; this.isZoneStringsSet = false; this.localPatternChars = null; this.locale = null; this.lastZoneIndex = 0; this.cachedHashCode = 0; initializeData(paramLocale); } private DateFormatSymbols(boolean paramBoolean) { this.eras = null; this.months = null; this.shortMonths = null; this.weekdays = null; this.shortWeekdays = null; this.ampms = null; this.zoneStrings = (String[][])null; this.isZoneStringsSet = false; this.localPatternChars = null; this.locale = null; this.lastZoneIndex = 0; this.cachedHashCode = 0; }
/*     */   public static Locale[] getAvailableLocales() { LocaleServiceProviderPool localeServiceProviderPool = LocaleServiceProviderPool.getPool((Class)DateFormatSymbolsProvider.class); return localeServiceProviderPool.getAvailableLocales(); }
/*     */   public static final DateFormatSymbols getInstance() { return getInstance(Locale.getDefault(Locale.Category.FORMAT)); }
/*     */   public static final DateFormatSymbols getInstance(Locale paramLocale) { DateFormatSymbols dateFormatSymbols = getProviderInstance(paramLocale); if (dateFormatSymbols != null) return dateFormatSymbols;  throw new RuntimeException("DateFormatSymbols instance creation failed."); }
/*     */   static final DateFormatSymbols getInstanceRef(Locale paramLocale) { DateFormatSymbols dateFormatSymbols = getProviderInstance(paramLocale); if (dateFormatSymbols != null) return dateFormatSymbols;  throw new RuntimeException("DateFormatSymbols instance creation failed."); }
/*     */   private static DateFormatSymbols getProviderInstance(Locale paramLocale) { LocaleProviderAdapter localeProviderAdapter = LocaleProviderAdapter.getAdapter((Class)DateFormatSymbolsProvider.class, paramLocale); DateFormatSymbolsProvider dateFormatSymbolsProvider = localeProviderAdapter.getDateFormatSymbolsProvider(); DateFormatSymbols dateFormatSymbols = dateFormatSymbolsProvider.getInstance(paramLocale); if (dateFormatSymbols == null) { dateFormatSymbolsProvider = LocaleProviderAdapter.forJRE().getDateFormatSymbolsProvider(); dateFormatSymbols = dateFormatSymbolsProvider.getInstance(paramLocale); }  return dateFormatSymbols; }
/*     */   public String[] getEras() { return Arrays.<String>copyOf(this.eras, this.eras.length); }
/*     */   public void setEras(String[] paramArrayOfString) { this.eras = Arrays.<String>copyOf(paramArrayOfString, paramArrayOfString.length); this.cachedHashCode = 0; }
/*     */   public String[] getMonths() { return Arrays.<String>copyOf(this.months, this.months.length); }
/*     */   public void setMonths(String[] paramArrayOfString) { this.months = Arrays.<String>copyOf(paramArrayOfString, paramArrayOfString.length); this.cachedHashCode = 0; }
/* 693 */   public String[] getShortMonths() { return Arrays.<String>copyOf(this.shortMonths, this.shortMonths.length); } public void setShortMonths(String[] paramArrayOfString) { this.shortMonths = Arrays.<String>copyOf(paramArrayOfString, paramArrayOfString.length); this.cachedHashCode = 0; } public String[] getWeekdays() { return Arrays.<String>copyOf(this.weekdays, this.weekdays.length); } public void setWeekdays(String[] paramArrayOfString) { this.weekdays = Arrays.<String>copyOf(paramArrayOfString, paramArrayOfString.length); this.cachedHashCode = 0; } private void initializeData(Locale paramLocale) { SoftReference<DateFormatSymbols> softReference = cachedInstances.get(paramLocale);
/*     */     DateFormatSymbols dateFormatSymbols;
/* 695 */     if (softReference == null || (dateFormatSymbols = softReference.get()) == null) {
/* 696 */       if (softReference != null)
/*     */       {
/* 698 */         cachedInstances.remove(paramLocale, softReference);
/*     */       }
/* 700 */       dateFormatSymbols = new DateFormatSymbols(false);
/*     */ 
/*     */ 
/*     */       
/* 704 */       LocaleProviderAdapter localeProviderAdapter = LocaleProviderAdapter.getAdapter((Class)DateFormatSymbolsProvider.class, paramLocale);
/*     */       
/* 706 */       if (!(localeProviderAdapter instanceof ResourceBundleBasedAdapter)) {
/* 707 */         localeProviderAdapter = LocaleProviderAdapter.getResourceBundleBased();
/*     */       }
/*     */       
/* 710 */       ResourceBundle resourceBundle = ((ResourceBundleBasedAdapter)localeProviderAdapter).getLocaleData().getDateFormatData(paramLocale);
/*     */       
/* 712 */       dateFormatSymbols.locale = paramLocale;
/*     */ 
/*     */ 
/*     */       
/* 716 */       if (resourceBundle.containsKey("Eras")) {
/* 717 */         dateFormatSymbols.eras = resourceBundle.getStringArray("Eras");
/* 718 */       } else if (resourceBundle.containsKey("long.Eras")) {
/* 719 */         dateFormatSymbols.eras = resourceBundle.getStringArray("long.Eras");
/* 720 */       } else if (resourceBundle.containsKey("short.Eras")) {
/* 721 */         dateFormatSymbols.eras = resourceBundle.getStringArray("short.Eras");
/*     */       } 
/* 723 */       dateFormatSymbols.months = resourceBundle.getStringArray("MonthNames");
/* 724 */       dateFormatSymbols.shortMonths = resourceBundle.getStringArray("MonthAbbreviations");
/* 725 */       dateFormatSymbols.ampms = resourceBundle.getStringArray("AmPmMarkers");
/* 726 */       dateFormatSymbols.localPatternChars = resourceBundle.getString("DateTimePatternChars");
/*     */ 
/*     */       
/* 729 */       dateFormatSymbols.weekdays = toOneBasedArray(resourceBundle.getStringArray("DayNames"));
/* 730 */       dateFormatSymbols.shortWeekdays = toOneBasedArray(resourceBundle.getStringArray("DayAbbreviations"));
/*     */ 
/*     */       
/* 733 */       softReference = new SoftReference<>(dateFormatSymbols);
/* 734 */       SoftReference<DateFormatSymbols> softReference1 = cachedInstances.putIfAbsent(paramLocale, softReference);
/* 735 */       if (softReference1 != null) {
/* 736 */         DateFormatSymbols dateFormatSymbols1 = softReference1.get();
/* 737 */         if (dateFormatSymbols1 == null) {
/*     */           
/* 739 */           cachedInstances.replace(paramLocale, softReference1, softReference);
/*     */         } else {
/* 741 */           softReference = softReference1;
/* 742 */           dateFormatSymbols = dateFormatSymbols1;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 747 */       Locale locale = resourceBundle.getLocale();
/* 748 */       if (!locale.equals(paramLocale)) {
/*     */         
/* 750 */         SoftReference<DateFormatSymbols> softReference2 = cachedInstances.putIfAbsent(locale, softReference);
/* 751 */         if (softReference2 != null && softReference2.get() == null) {
/* 752 */           cachedInstances.replace(locale, softReference2, softReference);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 758 */     copyMembers(dateFormatSymbols, this); }
/*     */   public String[] getShortWeekdays() { return Arrays.<String>copyOf(this.shortWeekdays, this.shortWeekdays.length); }
/*     */   public void setShortWeekdays(String[] paramArrayOfString) { this.shortWeekdays = Arrays.<String>copyOf(paramArrayOfString, paramArrayOfString.length); this.cachedHashCode = 0; }
/*     */   public String[] getAmPmStrings() { return Arrays.<String>copyOf(this.ampms, this.ampms.length); }
/* 762 */   public void setAmPmStrings(String[] paramArrayOfString) { this.ampms = Arrays.<String>copyOf(paramArrayOfString, paramArrayOfString.length); this.cachedHashCode = 0; } public String[][] getZoneStrings() { return getZoneStringsImpl(true); } public void setZoneStrings(String[][] paramArrayOfString) { String[][] arrayOfString = new String[paramArrayOfString.length][]; for (byte b = 0; b < paramArrayOfString.length; b++) { int i = (paramArrayOfString[b]).length; if (i < 5) throw new IllegalArgumentException();  arrayOfString[b] = Arrays.<String>copyOf(paramArrayOfString[b], i); }  this.zoneStrings = arrayOfString; this.isZoneStringsSet = true; this.cachedHashCode = 0; } public String getLocalPatternChars() { return this.localPatternChars; } public void setLocalPatternChars(String paramString) { this.localPatternChars = paramString.toString(); this.cachedHashCode = 0; } public Object clone() { try { DateFormatSymbols dateFormatSymbols = (DateFormatSymbols)super.clone(); copyMembers(this, dateFormatSymbols); return dateFormatSymbols; } catch (CloneNotSupportedException cloneNotSupportedException) { throw new InternalError(cloneNotSupportedException); }  } public int hashCode() { int i = this.cachedHashCode; if (i == 0) { i = 5; i = 11 * i + Arrays.hashCode((Object[])this.eras); i = 11 * i + Arrays.hashCode((Object[])this.months); i = 11 * i + Arrays.hashCode((Object[])this.shortMonths); i = 11 * i + Arrays.hashCode((Object[])this.weekdays); i = 11 * i + Arrays.hashCode((Object[])this.shortWeekdays); i = 11 * i + Arrays.hashCode((Object[])this.ampms); i = 11 * i + Arrays.deepHashCode((Object[])getZoneStringsWrapper()); i = 11 * i + Objects.hashCode(this.localPatternChars); this.cachedHashCode = i; }  return i; } public boolean equals(Object paramObject) { if (this == paramObject) return true;  if (paramObject == null || getClass() != paramObject.getClass()) return false;  DateFormatSymbols dateFormatSymbols = (DateFormatSymbols)paramObject; return (Arrays.equals((Object[])this.eras, (Object[])dateFormatSymbols.eras) && Arrays.equals((Object[])this.months, (Object[])dateFormatSymbols.months) && Arrays.equals((Object[])this.shortMonths, (Object[])dateFormatSymbols.shortMonths) && Arrays.equals((Object[])this.weekdays, (Object[])dateFormatSymbols.weekdays) && Arrays.equals((Object[])this.shortWeekdays, (Object[])dateFormatSymbols.shortWeekdays) && Arrays.equals((Object[])this.ampms, (Object[])dateFormatSymbols.ampms) && Arrays.deepEquals((Object[])getZoneStringsWrapper(), (Object[])dateFormatSymbols.getZoneStringsWrapper()) && ((this.localPatternChars != null && this.localPatternChars.equals(dateFormatSymbols.localPatternChars)) || (this.localPatternChars == null && dateFormatSymbols.localPatternChars == null))); } private static final ConcurrentMap<Locale, SoftReference<DateFormatSymbols>> cachedInstances = new ConcurrentHashMap<>(3); private transient int lastZoneIndex; volatile transient int cachedHashCode; private static String[] toOneBasedArray(String[] paramArrayOfString) { int i = paramArrayOfString.length;
/* 763 */     String[] arrayOfString = new String[i + 1];
/* 764 */     arrayOfString[0] = "";
/* 765 */     for (byte b = 0; b < i; b++) {
/* 766 */       arrayOfString[b + 1] = paramArrayOfString[b];
/*     */     }
/* 768 */     return arrayOfString; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int getZoneIndex(String paramString) {
/* 782 */     String[][] arrayOfString = getZoneStringsWrapper();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 789 */     if (this.lastZoneIndex < arrayOfString.length && paramString.equals(arrayOfString[this.lastZoneIndex][0])) {
/* 790 */       return this.lastZoneIndex;
/*     */     }
/*     */ 
/*     */     
/* 794 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 795 */       if (paramString.equals(arrayOfString[b][0])) {
/* 796 */         this.lastZoneIndex = b;
/* 797 */         return b;
/*     */       } 
/*     */     } 
/*     */     
/* 801 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final String[][] getZoneStringsWrapper() {
/* 810 */     if (isSubclassObject()) {
/* 811 */       return getZoneStrings();
/*     */     }
/* 813 */     return getZoneStringsImpl(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private String[][] getZoneStringsImpl(boolean paramBoolean) {
/* 818 */     if (this.zoneStrings == null) {
/* 819 */       this.zoneStrings = TimeZoneNameUtility.getZoneStrings(this.locale);
/*     */     }
/*     */     
/* 822 */     if (!paramBoolean) {
/* 823 */       return this.zoneStrings;
/*     */     }
/*     */     
/* 826 */     int i = this.zoneStrings.length;
/* 827 */     String[][] arrayOfString = new String[i][];
/* 828 */     for (byte b = 0; b < i; b++) {
/* 829 */       arrayOfString[b] = Arrays.<String>copyOf(this.zoneStrings[b], (this.zoneStrings[b]).length);
/*     */     }
/* 831 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   private boolean isSubclassObject() {
/* 835 */     return !getClass().getName().equals("java.text.DateFormatSymbols");
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
/*     */   private void copyMembers(DateFormatSymbols paramDateFormatSymbols1, DateFormatSymbols paramDateFormatSymbols2) {
/* 847 */     paramDateFormatSymbols2.locale = paramDateFormatSymbols1.locale;
/* 848 */     paramDateFormatSymbols2.eras = Arrays.<String>copyOf(paramDateFormatSymbols1.eras, paramDateFormatSymbols1.eras.length);
/* 849 */     paramDateFormatSymbols2.months = Arrays.<String>copyOf(paramDateFormatSymbols1.months, paramDateFormatSymbols1.months.length);
/* 850 */     paramDateFormatSymbols2.shortMonths = Arrays.<String>copyOf(paramDateFormatSymbols1.shortMonths, paramDateFormatSymbols1.shortMonths.length);
/* 851 */     paramDateFormatSymbols2.weekdays = Arrays.<String>copyOf(paramDateFormatSymbols1.weekdays, paramDateFormatSymbols1.weekdays.length);
/* 852 */     paramDateFormatSymbols2.shortWeekdays = Arrays.<String>copyOf(paramDateFormatSymbols1.shortWeekdays, paramDateFormatSymbols1.shortWeekdays.length);
/* 853 */     paramDateFormatSymbols2.ampms = Arrays.<String>copyOf(paramDateFormatSymbols1.ampms, paramDateFormatSymbols1.ampms.length);
/* 854 */     if (paramDateFormatSymbols1.zoneStrings != null) {
/* 855 */       paramDateFormatSymbols2.zoneStrings = paramDateFormatSymbols1.getZoneStringsImpl(true);
/*     */     } else {
/* 857 */       paramDateFormatSymbols2.zoneStrings = (String[][])null;
/*     */     } 
/* 859 */     paramDateFormatSymbols2.localPatternChars = paramDateFormatSymbols1.localPatternChars;
/* 860 */     paramDateFormatSymbols2.cachedHashCode = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 871 */     if (this.zoneStrings == null) {
/* 872 */       this.zoneStrings = TimeZoneNameUtility.getZoneStrings(this.locale);
/*     */     }
/* 874 */     paramObjectOutputStream.defaultWriteObject();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/DateFormatSymbols.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */