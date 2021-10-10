/*      */ package java.time.temporal;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.time.DateTimeException;
/*      */ import java.time.DayOfWeek;
/*      */ import java.time.chrono.ChronoLocalDate;
/*      */ import java.time.chrono.Chronology;
/*      */ import java.time.format.ResolverStyle;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import sun.util.locale.provider.CalendarDataUtility;
/*      */ import sun.util.locale.provider.LocaleProviderAdapter;
/*      */ import sun.util.locale.provider.LocaleResources;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class WeekFields
/*      */   implements Serializable
/*      */ {
/*  191 */   private static final ConcurrentMap<String, WeekFields> CACHE = new ConcurrentHashMap<>(4, 0.75F, 2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  205 */   public static final WeekFields ISO = new WeekFields(DayOfWeek.MONDAY, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  214 */   public static final WeekFields SUNDAY_START = of(DayOfWeek.SUNDAY, 1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  232 */   public static final TemporalUnit WEEK_BASED_YEARS = IsoFields.WEEK_BASED_YEARS;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = -1177360819670808121L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final DayOfWeek firstDayOfWeek;
/*      */ 
/*      */ 
/*      */   
/*      */   private final int minimalDays;
/*      */ 
/*      */ 
/*      */   
/*  250 */   private final transient TemporalField dayOfWeek = ComputedDayOfField.ofDayOfWeekField(this);
/*      */ 
/*      */ 
/*      */   
/*  254 */   private final transient TemporalField weekOfMonth = ComputedDayOfField.ofWeekOfMonthField(this);
/*      */ 
/*      */ 
/*      */   
/*  258 */   private final transient TemporalField weekOfYear = ComputedDayOfField.ofWeekOfYearField(this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  266 */   private final transient TemporalField weekOfWeekBasedYear = ComputedDayOfField.ofWeekOfWeekBasedYearField(this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  274 */   private final transient TemporalField weekBasedYear = ComputedDayOfField.ofWeekBasedYearField(this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static WeekFields of(Locale paramLocale) {
/*  286 */     Objects.requireNonNull(paramLocale, "locale");
/*  287 */     paramLocale = new Locale(paramLocale.getLanguage(), paramLocale.getCountry());
/*      */     
/*  289 */     int i = CalendarDataUtility.retrieveFirstDayOfWeek(paramLocale);
/*  290 */     DayOfWeek dayOfWeek = DayOfWeek.SUNDAY.plus((i - 1));
/*  291 */     int j = CalendarDataUtility.retrieveMinimalDaysInFirstWeek(paramLocale);
/*  292 */     return of(dayOfWeek, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static WeekFields of(DayOfWeek paramDayOfWeek, int paramInt) {
/*  316 */     String str = paramDayOfWeek.toString() + paramInt;
/*  317 */     WeekFields weekFields = CACHE.get(str);
/*  318 */     if (weekFields == null) {
/*  319 */       weekFields = new WeekFields(paramDayOfWeek, paramInt);
/*  320 */       CACHE.putIfAbsent(str, weekFields);
/*  321 */       weekFields = CACHE.get(str);
/*      */     } 
/*  323 */     return weekFields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private WeekFields(DayOfWeek paramDayOfWeek, int paramInt) {
/*  335 */     Objects.requireNonNull(paramDayOfWeek, "firstDayOfWeek");
/*  336 */     if (paramInt < 1 || paramInt > 7) {
/*  337 */       throw new IllegalArgumentException("Minimal number of days is invalid");
/*      */     }
/*  339 */     this.firstDayOfWeek = paramDayOfWeek;
/*  340 */     this.minimalDays = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException, InvalidObjectException {
/*  356 */     paramObjectInputStream.defaultReadObject();
/*  357 */     if (this.firstDayOfWeek == null) {
/*  358 */       throw new InvalidObjectException("firstDayOfWeek is null");
/*      */     }
/*      */     
/*  361 */     if (this.minimalDays < 1 || this.minimalDays > 7) {
/*  362 */       throw new InvalidObjectException("Minimal number of days is invalid");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object readResolve() throws InvalidObjectException {
/*      */     try {
/*  375 */       return of(this.firstDayOfWeek, this.minimalDays);
/*  376 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  377 */       throw new InvalidObjectException("Invalid serialized WeekFields: " + illegalArgumentException.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DayOfWeek getFirstDayOfWeek() {
/*  392 */     return this.firstDayOfWeek;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinimalDaysInFirstWeek() {
/*  406 */     return this.minimalDays;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TemporalField dayOfWeek() {
/*  429 */     return this.dayOfWeek;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TemporalField weekOfMonth() {
/*  475 */     return this.weekOfMonth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TemporalField weekOfYear() {
/*  520 */     return this.weekOfYear;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TemporalField weekOfWeekBasedYear() {
/*  570 */     return this.weekOfWeekBasedYear;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TemporalField weekBasedYear() {
/*  612 */     return this.weekBasedYear;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*  627 */     if (this == paramObject) {
/*  628 */       return true;
/*      */     }
/*  630 */     if (paramObject instanceof WeekFields) {
/*  631 */       return (hashCode() == paramObject.hashCode());
/*      */     }
/*  633 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  643 */     return this.firstDayOfWeek.ordinal() * 7 + this.minimalDays;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  654 */     return "WeekFields[" + this.firstDayOfWeek + ',' + this.minimalDays + ']';
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class ComputedDayOfField
/*      */     implements TemporalField
/*      */   {
/*      */     private final String name;
/*      */ 
/*      */     
/*      */     private final WeekFields weekDef;
/*      */ 
/*      */     
/*      */     private final TemporalUnit baseUnit;
/*      */     
/*      */     private final TemporalUnit rangeUnit;
/*      */     
/*      */     private final ValueRange range;
/*      */ 
/*      */     
/*      */     static ComputedDayOfField ofDayOfWeekField(WeekFields param1WeekFields) {
/*  676 */       return new ComputedDayOfField("DayOfWeek", param1WeekFields, ChronoUnit.DAYS, ChronoUnit.WEEKS, DAY_OF_WEEK_RANGE);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static ComputedDayOfField ofWeekOfMonthField(WeekFields param1WeekFields) {
/*  685 */       return new ComputedDayOfField("WeekOfMonth", param1WeekFields, ChronoUnit.WEEKS, ChronoUnit.MONTHS, WEEK_OF_MONTH_RANGE);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static ComputedDayOfField ofWeekOfYearField(WeekFields param1WeekFields) {
/*  694 */       return new ComputedDayOfField("WeekOfYear", param1WeekFields, ChronoUnit.WEEKS, ChronoUnit.YEARS, WEEK_OF_YEAR_RANGE);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static ComputedDayOfField ofWeekOfWeekBasedYearField(WeekFields param1WeekFields) {
/*  703 */       return new ComputedDayOfField("WeekOfWeekBasedYear", param1WeekFields, ChronoUnit.WEEKS, IsoFields.WEEK_BASED_YEARS, WEEK_OF_WEEK_BASED_YEAR_RANGE);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static ComputedDayOfField ofWeekBasedYearField(WeekFields param1WeekFields) {
/*  712 */       return new ComputedDayOfField("WeekBasedYear", param1WeekFields, IsoFields.WEEK_BASED_YEARS, ChronoUnit.FOREVER, ChronoField.YEAR.range());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ChronoLocalDate ofWeekBasedYear(Chronology param1Chronology, int param1Int1, int param1Int2, int param1Int3) {
/*  726 */       ChronoLocalDate chronoLocalDate = param1Chronology.date(param1Int1, 1, 1);
/*  727 */       int i = localizedDayOfWeek(chronoLocalDate);
/*  728 */       int j = startOfWeekOffset(1, i);
/*      */ 
/*      */       
/*  731 */       int k = chronoLocalDate.lengthOfYear();
/*  732 */       int m = computeWeek(j, k + this.weekDef.getMinimalDaysInFirstWeek());
/*  733 */       param1Int2 = Math.min(param1Int2, m - 1);
/*      */       
/*  735 */       int n = -j + param1Int3 - 1 + (param1Int2 - 1) * 7;
/*  736 */       return chronoLocalDate.plus(n, ChronoUnit.DAYS);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ComputedDayOfField(String param1String, WeekFields param1WeekFields, TemporalUnit param1TemporalUnit1, TemporalUnit param1TemporalUnit2, ValueRange param1ValueRange) {
/*  746 */       this.name = param1String;
/*  747 */       this.weekDef = param1WeekFields;
/*  748 */       this.baseUnit = param1TemporalUnit1;
/*  749 */       this.rangeUnit = param1TemporalUnit2;
/*  750 */       this.range = param1ValueRange;
/*      */     }
/*      */     
/*  753 */     private static final ValueRange DAY_OF_WEEK_RANGE = ValueRange.of(1L, 7L);
/*  754 */     private static final ValueRange WEEK_OF_MONTH_RANGE = ValueRange.of(0L, 1L, 4L, 6L);
/*  755 */     private static final ValueRange WEEK_OF_YEAR_RANGE = ValueRange.of(0L, 1L, 52L, 54L);
/*  756 */     private static final ValueRange WEEK_OF_WEEK_BASED_YEAR_RANGE = ValueRange.of(1L, 52L, 53L);
/*      */ 
/*      */     
/*      */     public long getFrom(TemporalAccessor param1TemporalAccessor) {
/*  760 */       if (this.rangeUnit == ChronoUnit.WEEKS)
/*  761 */         return localizedDayOfWeek(param1TemporalAccessor); 
/*  762 */       if (this.rangeUnit == ChronoUnit.MONTHS)
/*  763 */         return localizedWeekOfMonth(param1TemporalAccessor); 
/*  764 */       if (this.rangeUnit == ChronoUnit.YEARS)
/*  765 */         return localizedWeekOfYear(param1TemporalAccessor); 
/*  766 */       if (this.rangeUnit == WeekFields.WEEK_BASED_YEARS)
/*  767 */         return localizedWeekOfWeekBasedYear(param1TemporalAccessor); 
/*  768 */       if (this.rangeUnit == ChronoUnit.FOREVER) {
/*  769 */         return localizedWeekBasedYear(param1TemporalAccessor);
/*      */       }
/*  771 */       throw new IllegalStateException("unreachable, rangeUnit: " + this.rangeUnit + ", this: " + this);
/*      */     }
/*      */ 
/*      */     
/*      */     private int localizedDayOfWeek(TemporalAccessor param1TemporalAccessor) {
/*  776 */       int i = this.weekDef.getFirstDayOfWeek().getValue();
/*  777 */       int j = param1TemporalAccessor.get(ChronoField.DAY_OF_WEEK);
/*  778 */       return Math.floorMod(j - i, 7) + 1;
/*      */     }
/*      */     
/*      */     private int localizedDayOfWeek(int param1Int) {
/*  782 */       int i = this.weekDef.getFirstDayOfWeek().getValue();
/*  783 */       return Math.floorMod(param1Int - i, 7) + 1;
/*      */     }
/*      */     
/*      */     private long localizedWeekOfMonth(TemporalAccessor param1TemporalAccessor) {
/*  787 */       int i = localizedDayOfWeek(param1TemporalAccessor);
/*  788 */       int j = param1TemporalAccessor.get(ChronoField.DAY_OF_MONTH);
/*  789 */       int k = startOfWeekOffset(j, i);
/*  790 */       return computeWeek(k, j);
/*      */     }
/*      */     
/*      */     private long localizedWeekOfYear(TemporalAccessor param1TemporalAccessor) {
/*  794 */       int i = localizedDayOfWeek(param1TemporalAccessor);
/*  795 */       int j = param1TemporalAccessor.get(ChronoField.DAY_OF_YEAR);
/*  796 */       int k = startOfWeekOffset(j, i);
/*  797 */       return computeWeek(k, j);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int localizedWeekBasedYear(TemporalAccessor param1TemporalAccessor) {
/*  807 */       int i = localizedDayOfWeek(param1TemporalAccessor);
/*  808 */       int j = param1TemporalAccessor.get(ChronoField.YEAR);
/*  809 */       int k = param1TemporalAccessor.get(ChronoField.DAY_OF_YEAR);
/*  810 */       int m = startOfWeekOffset(k, i);
/*  811 */       int n = computeWeek(m, k);
/*  812 */       if (n == 0)
/*      */       {
/*  814 */         return j - 1;
/*      */       }
/*      */ 
/*      */       
/*  818 */       ValueRange valueRange = param1TemporalAccessor.range(ChronoField.DAY_OF_YEAR);
/*  819 */       int i1 = (int)valueRange.getMaximum();
/*  820 */       int i2 = computeWeek(m, i1 + this.weekDef.getMinimalDaysInFirstWeek());
/*  821 */       if (n >= i2) {
/*  822 */         return j + 1;
/*      */       }
/*      */       
/*  825 */       return j;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int localizedWeekOfWeekBasedYear(TemporalAccessor param1TemporalAccessor) {
/*  838 */       int i = localizedDayOfWeek(param1TemporalAccessor);
/*  839 */       int j = param1TemporalAccessor.get(ChronoField.DAY_OF_YEAR);
/*  840 */       int k = startOfWeekOffset(j, i);
/*  841 */       int m = computeWeek(k, j);
/*  842 */       if (m == 0) {
/*      */ 
/*      */         
/*  845 */         ChronoLocalDate chronoLocalDate = Chronology.from(param1TemporalAccessor).date(param1TemporalAccessor);
/*  846 */         chronoLocalDate = chronoLocalDate.minus(j, ChronoUnit.DAYS);
/*  847 */         return localizedWeekOfWeekBasedYear(chronoLocalDate);
/*  848 */       }  if (m > 50) {
/*      */ 
/*      */         
/*  851 */         ValueRange valueRange = param1TemporalAccessor.range(ChronoField.DAY_OF_YEAR);
/*  852 */         int n = (int)valueRange.getMaximum();
/*  853 */         int i1 = computeWeek(k, n + this.weekDef.getMinimalDaysInFirstWeek());
/*  854 */         if (m >= i1)
/*      */         {
/*  856 */           m = m - i1 + 1;
/*      */         }
/*      */       } 
/*  859 */       return m;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int startOfWeekOffset(int param1Int1, int param1Int2) {
/*  871 */       int i = Math.floorMod(param1Int1 - param1Int2, 7);
/*  872 */       int j = -i;
/*  873 */       if (i + 1 > this.weekDef.getMinimalDaysInFirstWeek())
/*      */       {
/*  875 */         j = 7 - i;
/*      */       }
/*  877 */       return j;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int computeWeek(int param1Int1, int param1Int2) {
/*  889 */       return (7 + param1Int1 + param1Int2 - 1) / 7;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public <R extends Temporal> R adjustInto(R param1R, long param1Long) {
/*  896 */       int i = this.range.checkValidIntValue(param1Long, this);
/*  897 */       int j = param1R.get(this);
/*  898 */       if (i == j) {
/*  899 */         return param1R;
/*      */       }
/*      */       
/*  902 */       if (this.rangeUnit == ChronoUnit.FOREVER) {
/*      */ 
/*      */         
/*  905 */         int k = param1R.get(this.weekDef.dayOfWeek);
/*  906 */         int m = param1R.get(this.weekDef.weekOfWeekBasedYear);
/*  907 */         return (R)ofWeekBasedYear(Chronology.from((TemporalAccessor)param1R), (int)param1Long, m, k);
/*      */       } 
/*      */       
/*  910 */       return (R)param1R.plus((i - j), this.baseUnit);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ChronoLocalDate resolve(Map<TemporalField, Long> param1Map, TemporalAccessor param1TemporalAccessor, ResolverStyle param1ResolverStyle) {
/*  917 */       long l = ((Long)param1Map.get(this)).longValue();
/*  918 */       int i = Math.toIntExact(l);
/*      */ 
/*      */ 
/*      */       
/*  922 */       if (this.rangeUnit == ChronoUnit.WEEKS) {
/*  923 */         int m = this.range.checkValidIntValue(l, this);
/*  924 */         int n = this.weekDef.getFirstDayOfWeek().getValue();
/*  925 */         long l1 = (Math.floorMod(n - 1 + m - 1, 7) + 1);
/*  926 */         param1Map.remove(this);
/*  927 */         param1Map.put(ChronoField.DAY_OF_WEEK, Long.valueOf(l1));
/*  928 */         return null;
/*      */       } 
/*      */ 
/*      */       
/*  932 */       if (!param1Map.containsKey(ChronoField.DAY_OF_WEEK)) {
/*  933 */         return null;
/*      */       }
/*  935 */       int j = ChronoField.DAY_OF_WEEK.checkValidIntValue(((Long)param1Map.get(ChronoField.DAY_OF_WEEK)).longValue());
/*  936 */       int k = localizedDayOfWeek(j);
/*      */ 
/*      */       
/*  939 */       Chronology chronology = Chronology.from(param1TemporalAccessor);
/*  940 */       if (param1Map.containsKey(ChronoField.YEAR)) {
/*  941 */         int m = ChronoField.YEAR.checkValidIntValue(((Long)param1Map.get(ChronoField.YEAR)).longValue());
/*  942 */         if (this.rangeUnit == ChronoUnit.MONTHS && param1Map.containsKey(ChronoField.MONTH_OF_YEAR)) {
/*  943 */           long l1 = ((Long)param1Map.get(ChronoField.MONTH_OF_YEAR)).longValue();
/*  944 */           return resolveWoM(param1Map, chronology, m, l1, i, k, param1ResolverStyle);
/*      */         } 
/*  946 */         if (this.rangeUnit == ChronoUnit.YEARS) {
/*  947 */           return resolveWoY(param1Map, chronology, m, i, k, param1ResolverStyle);
/*      */         }
/*  949 */       } else if ((this.rangeUnit == WeekFields.WEEK_BASED_YEARS || this.rangeUnit == ChronoUnit.FOREVER) && param1Map
/*  950 */         .containsKey(this.weekDef.weekBasedYear) && param1Map
/*  951 */         .containsKey(this.weekDef.weekOfWeekBasedYear)) {
/*  952 */         return resolveWBY(param1Map, chronology, k, param1ResolverStyle);
/*      */       } 
/*  954 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     private ChronoLocalDate resolveWoM(Map<TemporalField, Long> param1Map, Chronology param1Chronology, int param1Int1, long param1Long1, long param1Long2, int param1Int2, ResolverStyle param1ResolverStyle) {
/*      */       ChronoLocalDate chronoLocalDate;
/*  960 */       if (param1ResolverStyle == ResolverStyle.LENIENT) {
/*  961 */         chronoLocalDate = param1Chronology.date(param1Int1, 1, 1).plus(Math.subtractExact(param1Long1, 1L), ChronoUnit.MONTHS);
/*  962 */         long l = Math.subtractExact(param1Long2, localizedWeekOfMonth(chronoLocalDate));
/*  963 */         int i = param1Int2 - localizedDayOfWeek(chronoLocalDate);
/*  964 */         chronoLocalDate = chronoLocalDate.plus(Math.addExact(Math.multiplyExact(l, 7L), i), ChronoUnit.DAYS);
/*      */       } else {
/*  966 */         int i = ChronoField.MONTH_OF_YEAR.checkValidIntValue(param1Long1);
/*  967 */         chronoLocalDate = param1Chronology.date(param1Int1, i, 1);
/*  968 */         int j = this.range.checkValidIntValue(param1Long2, this);
/*  969 */         int k = (int)(j - localizedWeekOfMonth(chronoLocalDate));
/*  970 */         int m = param1Int2 - localizedDayOfWeek(chronoLocalDate);
/*  971 */         chronoLocalDate = chronoLocalDate.plus((k * 7 + m), ChronoUnit.DAYS);
/*  972 */         if (param1ResolverStyle == ResolverStyle.STRICT && chronoLocalDate.getLong(ChronoField.MONTH_OF_YEAR) != param1Long1) {
/*  973 */           throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
/*      */         }
/*      */       } 
/*  976 */       param1Map.remove(this);
/*  977 */       param1Map.remove(ChronoField.YEAR);
/*  978 */       param1Map.remove(ChronoField.MONTH_OF_YEAR);
/*  979 */       param1Map.remove(ChronoField.DAY_OF_WEEK);
/*  980 */       return chronoLocalDate;
/*      */     }
/*      */ 
/*      */     
/*      */     private ChronoLocalDate resolveWoY(Map<TemporalField, Long> param1Map, Chronology param1Chronology, int param1Int1, long param1Long, int param1Int2, ResolverStyle param1ResolverStyle) {
/*  985 */       ChronoLocalDate chronoLocalDate = param1Chronology.date(param1Int1, 1, 1);
/*  986 */       if (param1ResolverStyle == ResolverStyle.LENIENT) {
/*  987 */         long l = Math.subtractExact(param1Long, localizedWeekOfYear(chronoLocalDate));
/*  988 */         int i = param1Int2 - localizedDayOfWeek(chronoLocalDate);
/*  989 */         chronoLocalDate = chronoLocalDate.plus(Math.addExact(Math.multiplyExact(l, 7L), i), ChronoUnit.DAYS);
/*      */       } else {
/*  991 */         int i = this.range.checkValidIntValue(param1Long, this);
/*  992 */         int j = (int)(i - localizedWeekOfYear(chronoLocalDate));
/*  993 */         int k = param1Int2 - localizedDayOfWeek(chronoLocalDate);
/*  994 */         chronoLocalDate = chronoLocalDate.plus((j * 7 + k), ChronoUnit.DAYS);
/*  995 */         if (param1ResolverStyle == ResolverStyle.STRICT && chronoLocalDate.getLong(ChronoField.YEAR) != param1Int1) {
/*  996 */           throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
/*      */         }
/*      */       } 
/*  999 */       param1Map.remove(this);
/* 1000 */       param1Map.remove(ChronoField.YEAR);
/* 1001 */       param1Map.remove(ChronoField.DAY_OF_WEEK);
/* 1002 */       return chronoLocalDate;
/*      */     }
/*      */     
/*      */     private ChronoLocalDate resolveWBY(Map<TemporalField, Long> param1Map, Chronology param1Chronology, int param1Int, ResolverStyle param1ResolverStyle) {
/*      */       ChronoLocalDate chronoLocalDate;
/* 1007 */       int i = this.weekDef.weekBasedYear.range().checkValidIntValue(((Long)param1Map
/* 1008 */           .get(this.weekDef.weekBasedYear)).longValue(), this.weekDef.weekBasedYear);
/*      */       
/* 1010 */       if (param1ResolverStyle == ResolverStyle.LENIENT) {
/* 1011 */         chronoLocalDate = ofWeekBasedYear(param1Chronology, i, 1, param1Int);
/* 1012 */         long l1 = ((Long)param1Map.get(this.weekDef.weekOfWeekBasedYear)).longValue();
/* 1013 */         long l2 = Math.subtractExact(l1, 1L);
/* 1014 */         chronoLocalDate = chronoLocalDate.plus(l2, ChronoUnit.WEEKS);
/*      */       } else {
/* 1016 */         int j = this.weekDef.weekOfWeekBasedYear.range().checkValidIntValue(((Long)param1Map
/* 1017 */             .get(this.weekDef.weekOfWeekBasedYear)).longValue(), this.weekDef.weekOfWeekBasedYear);
/* 1018 */         chronoLocalDate = ofWeekBasedYear(param1Chronology, i, j, param1Int);
/* 1019 */         if (param1ResolverStyle == ResolverStyle.STRICT && localizedWeekBasedYear(chronoLocalDate) != i) {
/* 1020 */           throw new DateTimeException("Strict mode rejected resolved date as it is in a different week-based-year");
/*      */         }
/*      */       } 
/* 1023 */       param1Map.remove(this);
/* 1024 */       param1Map.remove(this.weekDef.weekBasedYear);
/* 1025 */       param1Map.remove(this.weekDef.weekOfWeekBasedYear);
/* 1026 */       param1Map.remove(ChronoField.DAY_OF_WEEK);
/* 1027 */       return chronoLocalDate;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getDisplayName(Locale param1Locale) {
/* 1033 */       Objects.requireNonNull(param1Locale, "locale");
/* 1034 */       if (this.rangeUnit == ChronoUnit.YEARS) {
/*      */         
/* 1036 */         LocaleResources localeResources = LocaleProviderAdapter.getResourceBundleBased().getLocaleResources(param1Locale);
/* 1037 */         ResourceBundle resourceBundle = localeResources.getJavaTimeFormatData();
/* 1038 */         return resourceBundle.containsKey("field.week") ? resourceBundle.getString("field.week") : this.name;
/*      */       } 
/* 1040 */       return this.name;
/*      */     }
/*      */ 
/*      */     
/*      */     public TemporalUnit getBaseUnit() {
/* 1045 */       return this.baseUnit;
/*      */     }
/*      */ 
/*      */     
/*      */     public TemporalUnit getRangeUnit() {
/* 1050 */       return this.rangeUnit;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isDateBased() {
/* 1055 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isTimeBased() {
/* 1060 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public ValueRange range() {
/* 1065 */       return this.range;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isSupportedBy(TemporalAccessor param1TemporalAccessor) {
/* 1071 */       if (param1TemporalAccessor.isSupported(ChronoField.DAY_OF_WEEK)) {
/* 1072 */         if (this.rangeUnit == ChronoUnit.WEEKS)
/* 1073 */           return true; 
/* 1074 */         if (this.rangeUnit == ChronoUnit.MONTHS)
/* 1075 */           return param1TemporalAccessor.isSupported(ChronoField.DAY_OF_MONTH); 
/* 1076 */         if (this.rangeUnit == ChronoUnit.YEARS)
/* 1077 */           return param1TemporalAccessor.isSupported(ChronoField.DAY_OF_YEAR); 
/* 1078 */         if (this.rangeUnit == WeekFields.WEEK_BASED_YEARS)
/* 1079 */           return param1TemporalAccessor.isSupported(ChronoField.DAY_OF_YEAR); 
/* 1080 */         if (this.rangeUnit == ChronoUnit.FOREVER) {
/* 1081 */           return param1TemporalAccessor.isSupported(ChronoField.YEAR);
/*      */         }
/*      */       } 
/* 1084 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public ValueRange rangeRefinedBy(TemporalAccessor param1TemporalAccessor) {
/* 1089 */       if (this.rangeUnit == ChronoUnit.WEEKS)
/* 1090 */         return this.range; 
/* 1091 */       if (this.rangeUnit == ChronoUnit.MONTHS)
/* 1092 */         return rangeByWeek(param1TemporalAccessor, ChronoField.DAY_OF_MONTH); 
/* 1093 */       if (this.rangeUnit == ChronoUnit.YEARS)
/* 1094 */         return rangeByWeek(param1TemporalAccessor, ChronoField.DAY_OF_YEAR); 
/* 1095 */       if (this.rangeUnit == WeekFields.WEEK_BASED_YEARS)
/* 1096 */         return rangeWeekOfWeekBasedYear(param1TemporalAccessor); 
/* 1097 */       if (this.rangeUnit == ChronoUnit.FOREVER) {
/* 1098 */         return ChronoField.YEAR.range();
/*      */       }
/* 1100 */       throw new IllegalStateException("unreachable, rangeUnit: " + this.rangeUnit + ", this: " + this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ValueRange rangeByWeek(TemporalAccessor param1TemporalAccessor, TemporalField param1TemporalField) {
/* 1111 */       int i = localizedDayOfWeek(param1TemporalAccessor);
/* 1112 */       int j = startOfWeekOffset(param1TemporalAccessor.get(param1TemporalField), i);
/* 1113 */       ValueRange valueRange = param1TemporalAccessor.range(param1TemporalField);
/* 1114 */       return ValueRange.of(computeWeek(j, (int)valueRange.getMinimum()), 
/* 1115 */           computeWeek(j, (int)valueRange.getMaximum()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ValueRange rangeWeekOfWeekBasedYear(TemporalAccessor param1TemporalAccessor) {
/* 1124 */       if (!param1TemporalAccessor.isSupported(ChronoField.DAY_OF_YEAR)) {
/* 1125 */         return WEEK_OF_YEAR_RANGE;
/*      */       }
/* 1127 */       int i = localizedDayOfWeek(param1TemporalAccessor);
/* 1128 */       int j = param1TemporalAccessor.get(ChronoField.DAY_OF_YEAR);
/* 1129 */       int k = startOfWeekOffset(j, i);
/* 1130 */       int m = computeWeek(k, j);
/* 1131 */       if (m == 0) {
/*      */ 
/*      */         
/* 1134 */         ChronoLocalDate chronoLocalDate = Chronology.from(param1TemporalAccessor).date(param1TemporalAccessor);
/* 1135 */         chronoLocalDate = chronoLocalDate.minus((j + 7), ChronoUnit.DAYS);
/* 1136 */         return rangeWeekOfWeekBasedYear(chronoLocalDate);
/*      */       } 
/*      */       
/* 1139 */       ValueRange valueRange = param1TemporalAccessor.range(ChronoField.DAY_OF_YEAR);
/* 1140 */       int n = (int)valueRange.getMaximum();
/* 1141 */       int i1 = computeWeek(k, n + this.weekDef.getMinimalDaysInFirstWeek());
/*      */       
/* 1143 */       if (m >= i1) {
/*      */         
/* 1145 */         ChronoLocalDate chronoLocalDate = Chronology.from(param1TemporalAccessor).date(param1TemporalAccessor);
/* 1146 */         chronoLocalDate = chronoLocalDate.plus((n - j + 1 + 7), ChronoUnit.DAYS);
/* 1147 */         return rangeWeekOfWeekBasedYear(chronoLocalDate);
/*      */       } 
/* 1149 */       return ValueRange.of(1L, (i1 - 1));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1155 */       return this.name + "[" + this.weekDef.toString() + "]";
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/temporal/WeekFields.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */