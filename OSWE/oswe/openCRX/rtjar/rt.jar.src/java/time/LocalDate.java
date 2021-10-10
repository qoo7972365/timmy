/*      */ package java.time;
/*      */ 
/*      */ import java.io.DataInput;
/*      */ import java.io.DataOutput;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.time.chrono.ChronoLocalDate;
/*      */ import java.time.chrono.ChronoLocalDateTime;
/*      */ import java.time.chrono.ChronoPeriod;
/*      */ import java.time.chrono.Chronology;
/*      */ import java.time.chrono.Era;
/*      */ import java.time.chrono.IsoChronology;
/*      */ import java.time.format.DateTimeFormatter;
/*      */ import java.time.temporal.ChronoField;
/*      */ import java.time.temporal.ChronoUnit;
/*      */ import java.time.temporal.Temporal;
/*      */ import java.time.temporal.TemporalAccessor;
/*      */ import java.time.temporal.TemporalAdjuster;
/*      */ import java.time.temporal.TemporalAmount;
/*      */ import java.time.temporal.TemporalField;
/*      */ import java.time.temporal.TemporalQueries;
/*      */ import java.time.temporal.TemporalQuery;
/*      */ import java.time.temporal.TemporalUnit;
/*      */ import java.time.temporal.UnsupportedTemporalTypeException;
/*      */ import java.time.temporal.ValueRange;
/*      */ import java.time.zone.ZoneOffsetTransition;
/*      */ import java.time.zone.ZoneRules;
/*      */ import java.util.Objects;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class LocalDate
/*      */   implements Temporal, TemporalAdjuster, ChronoLocalDate, Serializable
/*      */ {
/*  144 */   public static final LocalDate MIN = of(-999999999, 1, 1);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  149 */   public static final LocalDate MAX = of(999999999, 12, 31);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 2942565459149668126L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int DAYS_PER_CYCLE = 146097;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final long DAYS_0000_TO_1970 = 719528L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int year;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final short month;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final short day;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LocalDate now() {
/*  192 */     return now(Clock.systemDefaultZone());
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
/*      */   public static LocalDate now(ZoneId paramZoneId) {
/*  208 */     return now(Clock.system(paramZoneId));
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
/*      */   public static LocalDate now(Clock paramClock) {
/*  222 */     Objects.requireNonNull(paramClock, "clock");
/*      */     
/*  224 */     Instant instant = paramClock.instant();
/*  225 */     ZoneOffset zoneOffset = paramClock.getZone().getRules().getOffset(instant);
/*  226 */     long l1 = instant.getEpochSecond() + zoneOffset.getTotalSeconds();
/*  227 */     long l2 = Math.floorDiv(l1, 86400L);
/*  228 */     return ofEpochDay(l2);
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
/*      */   public static LocalDate of(int paramInt1, Month paramMonth, int paramInt2) {
/*  246 */     ChronoField.YEAR.checkValidValue(paramInt1);
/*  247 */     Objects.requireNonNull(paramMonth, "month");
/*  248 */     ChronoField.DAY_OF_MONTH.checkValidValue(paramInt2);
/*  249 */     return create(paramInt1, paramMonth.getValue(), paramInt2);
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
/*      */   public static LocalDate of(int paramInt1, int paramInt2, int paramInt3) {
/*  266 */     ChronoField.YEAR.checkValidValue(paramInt1);
/*  267 */     ChronoField.MONTH_OF_YEAR.checkValidValue(paramInt2);
/*  268 */     ChronoField.DAY_OF_MONTH.checkValidValue(paramInt3);
/*  269 */     return create(paramInt1, paramInt2, paramInt3);
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
/*      */   public static LocalDate ofYearDay(int paramInt1, int paramInt2) {
/*  286 */     ChronoField.YEAR.checkValidValue(paramInt1);
/*  287 */     ChronoField.DAY_OF_YEAR.checkValidValue(paramInt2);
/*  288 */     boolean bool = IsoChronology.INSTANCE.isLeapYear(paramInt1);
/*  289 */     if (paramInt2 == 366 && !bool) {
/*  290 */       throw new DateTimeException("Invalid date 'DayOfYear 366' as '" + paramInt1 + "' is not a leap year");
/*      */     }
/*  292 */     Month month = Month.of((paramInt2 - 1) / 31 + 1);
/*  293 */     int i = month.firstDayOfYear(bool) + month.length(bool) - 1;
/*  294 */     if (paramInt2 > i) {
/*  295 */       month = month.plus(1L);
/*      */     }
/*  297 */     int j = paramInt2 - month.firstDayOfYear(bool) + 1;
/*  298 */     return new LocalDate(paramInt1, month.getValue(), j);
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
/*      */   public static LocalDate ofEpochDay(long paramLong) {
/*  314 */     long l1 = paramLong + 719528L;
/*      */     
/*  316 */     l1 -= 60L;
/*  317 */     long l2 = 0L;
/*  318 */     if (l1 < 0L) {
/*      */       
/*  320 */       long l = (l1 + 1L) / 146097L - 1L;
/*  321 */       l2 = l * 400L;
/*  322 */       l1 += -l * 146097L;
/*      */     } 
/*  324 */     long l3 = (400L * l1 + 591L) / 146097L;
/*  325 */     long l4 = l1 - 365L * l3 + l3 / 4L - l3 / 100L + l3 / 400L;
/*  326 */     if (l4 < 0L) {
/*      */       
/*  328 */       l3--;
/*  329 */       l4 = l1 - 365L * l3 + l3 / 4L - l3 / 100L + l3 / 400L;
/*      */     } 
/*  331 */     l3 += l2;
/*  332 */     int i = (int)l4;
/*      */ 
/*      */     
/*  335 */     int j = (i * 5 + 2) / 153;
/*  336 */     int k = (j + 2) % 12 + 1;
/*  337 */     int m = i - (j * 306 + 5) / 10 + 1;
/*  338 */     l3 += (j / 10);
/*      */ 
/*      */     
/*  341 */     int n = ChronoField.YEAR.checkValidIntValue(l3);
/*  342 */     return new LocalDate(n, k, m);
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
/*      */   public static LocalDate from(TemporalAccessor paramTemporalAccessor) {
/*  364 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/*  365 */     LocalDate localDate = paramTemporalAccessor.<LocalDate>query(TemporalQueries.localDate());
/*  366 */     if (localDate == null) {
/*  367 */       throw new DateTimeException("Unable to obtain LocalDate from TemporalAccessor: " + paramTemporalAccessor + " of type " + paramTemporalAccessor
/*  368 */           .getClass().getName());
/*      */     }
/*  370 */     return localDate;
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
/*      */   public static LocalDate parse(CharSequence paramCharSequence) {
/*  385 */     return parse(paramCharSequence, DateTimeFormatter.ISO_LOCAL_DATE);
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
/*      */   public static LocalDate parse(CharSequence paramCharSequence, DateTimeFormatter paramDateTimeFormatter) {
/*  399 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/*  400 */     return paramDateTimeFormatter.<LocalDate>parse(paramCharSequence, LocalDate::from);
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
/*      */   private static LocalDate create(int paramInt1, int paramInt2, int paramInt3) {
/*  414 */     if (paramInt3 > 28) {
/*  415 */       byte b = 31;
/*  416 */       switch (paramInt2) {
/*      */         case 2:
/*  418 */           b = IsoChronology.INSTANCE.isLeapYear(paramInt1) ? 29 : 28;
/*      */           break;
/*      */         case 4:
/*      */         case 6:
/*      */         case 9:
/*      */         case 11:
/*  424 */           b = 30;
/*      */           break;
/*      */       } 
/*  427 */       if (paramInt3 > b) {
/*  428 */         if (paramInt3 == 29) {
/*  429 */           throw new DateTimeException("Invalid date 'February 29' as '" + paramInt1 + "' is not a leap year");
/*      */         }
/*  431 */         throw new DateTimeException("Invalid date '" + Month.of(paramInt2).name() + " " + paramInt3 + "'");
/*      */       } 
/*      */     } 
/*      */     
/*  435 */     return new LocalDate(paramInt1, paramInt2, paramInt3);
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
/*      */   private static LocalDate resolvePreviousValid(int paramInt1, int paramInt2, int paramInt3) {
/*  447 */     switch (paramInt2) {
/*      */       case 2:
/*  449 */         paramInt3 = Math.min(paramInt3, IsoChronology.INSTANCE.isLeapYear(paramInt1) ? 29 : 28);
/*      */         break;
/*      */       case 4:
/*      */       case 6:
/*      */       case 9:
/*      */       case 11:
/*  455 */         paramInt3 = Math.min(paramInt3, 30);
/*      */         break;
/*      */     } 
/*  458 */     return new LocalDate(paramInt1, paramInt2, paramInt3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LocalDate(int paramInt1, int paramInt2, int paramInt3) {
/*  469 */     this.year = paramInt1;
/*  470 */     this.month = (short)paramInt2;
/*  471 */     this.day = (short)paramInt3;
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
/*      */   public boolean isSupported(TemporalField paramTemporalField) {
/*  512 */     return super.isSupported(paramTemporalField);
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
/*      */   public boolean isSupported(TemporalUnit paramTemporalUnit) {
/*  546 */     return super.isSupported(paramTemporalUnit);
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
/*      */   public ValueRange range(TemporalField paramTemporalField) {
/*  575 */     if (paramTemporalField instanceof ChronoField) {
/*  576 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/*  577 */       if (chronoField.isDateBased()) {
/*  578 */         switch (chronoField) { case DAYS:
/*  579 */             return ValueRange.of(1L, lengthOfMonth());
/*  580 */           case WEEKS: return ValueRange.of(1L, lengthOfYear());
/*  581 */           case MONTHS: return ValueRange.of(1L, (getMonth() == Month.FEBRUARY && !isLeapYear()) ? 4L : 5L);
/*      */           case YEARS:
/*  583 */             return (getYear() <= 0) ? ValueRange.of(1L, 1000000000L) : ValueRange.of(1L, 999999999L); }
/*      */         
/*  585 */         return paramTemporalField.range();
/*      */       } 
/*  587 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*      */     } 
/*  589 */     return paramTemporalField.rangeRefinedBy(this);
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
/*      */   public int get(TemporalField paramTemporalField) {
/*  621 */     if (paramTemporalField instanceof ChronoField) {
/*  622 */       return get0(paramTemporalField);
/*      */     }
/*  624 */     return super.get(paramTemporalField);
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
/*      */   public long getLong(TemporalField paramTemporalField) {
/*  652 */     if (paramTemporalField instanceof ChronoField) {
/*  653 */       if (paramTemporalField == ChronoField.EPOCH_DAY) {
/*  654 */         return toEpochDay();
/*      */       }
/*  656 */       if (paramTemporalField == ChronoField.PROLEPTIC_MONTH) {
/*  657 */         return getProlepticMonth();
/*      */       }
/*  659 */       return get0(paramTemporalField);
/*      */     } 
/*  661 */     return paramTemporalField.getFrom(this);
/*      */   }
/*      */   
/*      */   private int get0(TemporalField paramTemporalField) {
/*  665 */     switch ((ChronoField)paramTemporalField) { case DECADES:
/*  666 */         return getDayOfWeek().getValue();
/*  667 */       case CENTURIES: return (this.day - 1) % 7 + 1;
/*  668 */       case MILLENNIA: return (getDayOfYear() - 1) % 7 + 1;
/*  669 */       case DAYS: return this.day;
/*  670 */       case WEEKS: return getDayOfYear();
/*  671 */       case ERAS: throw new UnsupportedTemporalTypeException("Invalid field 'EpochDay' for get() method, use getLong() instead");
/*  672 */       case MONTHS: return (this.day - 1) / 7 + 1;
/*  673 */       case null: return (getDayOfYear() - 1) / 7 + 1;
/*  674 */       case null: return this.month;
/*  675 */       case null: throw new UnsupportedTemporalTypeException("Invalid field 'ProlepticMonth' for get() method, use getLong() instead");
/*  676 */       case YEARS: return (this.year >= 1) ? this.year : (1 - this.year);
/*  677 */       case null: return this.year;
/*  678 */       case null: return (this.year >= 1) ? 1 : 0; }
/*      */     
/*  680 */     throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*      */   }
/*      */   
/*      */   private long getProlepticMonth() {
/*  684 */     return this.year * 12L + this.month - 1L;
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
/*      */   public IsoChronology getChronology() {
/*  700 */     return IsoChronology.INSTANCE;
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
/*      */   public Era getEra() {
/*  723 */     return super.getEra();
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
/*      */   public int getYear() {
/*  737 */     return this.year;
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
/*      */   public int getMonthValue() {
/*  751 */     return this.month;
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
/*      */   public Month getMonth() {
/*  766 */     return Month.of(this.month);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDayOfMonth() {
/*  777 */     return this.day;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDayOfYear() {
/*  788 */     return getMonth().firstDayOfYear(isLeapYear()) + this.day - 1;
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
/*      */   public DayOfWeek getDayOfWeek() {
/*  805 */     int i = (int)Math.floorMod(toEpochDay() + 3L, 7L);
/*  806 */     return DayOfWeek.of(i + 1);
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
/*      */   public boolean isLeapYear() {
/*  830 */     return IsoChronology.INSTANCE.isLeapYear(this.year);
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
/*      */   public int lengthOfMonth() {
/*  843 */     switch (this.month) {
/*      */       case 2:
/*  845 */         return isLeapYear() ? 29 : 28;
/*      */       case 4:
/*      */       case 6:
/*      */       case 9:
/*      */       case 11:
/*  850 */         return 30;
/*      */     } 
/*  852 */     return 31;
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
/*      */   public int lengthOfYear() {
/*  865 */     return isLeapYear() ? 366 : 365;
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
/*      */   public LocalDate with(TemporalAdjuster paramTemporalAdjuster) {
/*  909 */     if (paramTemporalAdjuster instanceof LocalDate) {
/*  910 */       return (LocalDate)paramTemporalAdjuster;
/*      */     }
/*  912 */     return (LocalDate)paramTemporalAdjuster.adjustInto(this);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalDate with(TemporalField paramTemporalField, long paramLong) {
/* 1021 */     if (paramTemporalField instanceof ChronoField) {
/* 1022 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/* 1023 */       chronoField.checkValidValue(paramLong);
/* 1024 */       switch (chronoField) { case DECADES:
/* 1025 */           return plusDays(paramLong - getDayOfWeek().getValue());
/* 1026 */         case CENTURIES: return plusDays(paramLong - getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH));
/* 1027 */         case MILLENNIA: return plusDays(paramLong - getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR));
/* 1028 */         case DAYS: return withDayOfMonth((int)paramLong);
/* 1029 */         case WEEKS: return withDayOfYear((int)paramLong);
/* 1030 */         case ERAS: return ofEpochDay(paramLong);
/* 1031 */         case MONTHS: return plusWeeks(paramLong - getLong(ChronoField.ALIGNED_WEEK_OF_MONTH));
/* 1032 */         case null: return plusWeeks(paramLong - getLong(ChronoField.ALIGNED_WEEK_OF_YEAR));
/* 1033 */         case null: return withMonth((int)paramLong);
/* 1034 */         case null: return plusMonths(paramLong - getProlepticMonth());
/* 1035 */         case YEARS: return withYear((int)((this.year >= 1) ? paramLong : (1L - paramLong)));
/* 1036 */         case null: return withYear((int)paramLong);
/* 1037 */         case null: return (getLong(ChronoField.ERA) == paramLong) ? this : withYear(1 - this.year); }
/*      */       
/* 1039 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*      */     } 
/* 1041 */     return paramTemporalField.<LocalDate>adjustInto(this, paramLong);
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
/*      */   public LocalDate withYear(int paramInt) {
/* 1057 */     if (this.year == paramInt) {
/* 1058 */       return this;
/*      */     }
/* 1060 */     ChronoField.YEAR.checkValidValue(paramInt);
/* 1061 */     return resolvePreviousValid(paramInt, this.month, this.day);
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
/*      */   public LocalDate withMonth(int paramInt) {
/* 1076 */     if (this.month == paramInt) {
/* 1077 */       return this;
/*      */     }
/* 1079 */     ChronoField.MONTH_OF_YEAR.checkValidValue(paramInt);
/* 1080 */     return resolvePreviousValid(this.year, paramInt, this.day);
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
/*      */   public LocalDate withDayOfMonth(int paramInt) {
/* 1096 */     if (this.day == paramInt) {
/* 1097 */       return this;
/*      */     }
/* 1099 */     return of(this.year, this.month, paramInt);
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
/*      */   public LocalDate withDayOfYear(int paramInt) {
/* 1115 */     if (getDayOfYear() == paramInt) {
/* 1116 */       return this;
/*      */     }
/* 1118 */     return ofYearDay(this.year, paramInt);
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
/*      */   public LocalDate plus(TemporalAmount paramTemporalAmount) {
/* 1144 */     if (paramTemporalAmount instanceof Period) {
/* 1145 */       Period period = (Period)paramTemporalAmount;
/* 1146 */       return plusMonths(period.toTotalMonths()).plusDays(period.getDays());
/*      */     } 
/* 1148 */     Objects.requireNonNull(paramTemporalAmount, "amountToAdd");
/* 1149 */     return (LocalDate)paramTemporalAmount.addTo(this);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalDate plus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 1235 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 1236 */       ChronoUnit chronoUnit = (ChronoUnit)paramTemporalUnit;
/* 1237 */       switch (chronoUnit) { case DAYS:
/* 1238 */           return plusDays(paramLong);
/* 1239 */         case WEEKS: return plusWeeks(paramLong);
/* 1240 */         case MONTHS: return plusMonths(paramLong);
/* 1241 */         case YEARS: return plusYears(paramLong);
/* 1242 */         case DECADES: return plusYears(Math.multiplyExact(paramLong, 10L));
/* 1243 */         case CENTURIES: return plusYears(Math.multiplyExact(paramLong, 100L));
/* 1244 */         case MILLENNIA: return plusYears(Math.multiplyExact(paramLong, 1000L));
/* 1245 */         case ERAS: return with(ChronoField.ERA, Math.addExact(getLong(ChronoField.ERA), paramLong)); }
/*      */       
/* 1247 */       throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
/*      */     } 
/* 1249 */     return paramTemporalUnit.<LocalDate>addTo(this, paramLong);
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
/*      */   public LocalDate plusYears(long paramLong) {
/* 1274 */     if (paramLong == 0L) {
/* 1275 */       return this;
/*      */     }
/* 1277 */     int i = ChronoField.YEAR.checkValidIntValue(this.year + paramLong);
/* 1278 */     return resolvePreviousValid(i, this.month, this.day);
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
/*      */   public LocalDate plusMonths(long paramLong) {
/* 1302 */     if (paramLong == 0L) {
/* 1303 */       return this;
/*      */     }
/* 1305 */     long l1 = this.year * 12L + (this.month - 1);
/* 1306 */     long l2 = l1 + paramLong;
/* 1307 */     int i = ChronoField.YEAR.checkValidIntValue(Math.floorDiv(l2, 12L));
/* 1308 */     int j = (int)Math.floorMod(l2, 12L) + 1;
/* 1309 */     return resolvePreviousValid(i, j, this.day);
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
/*      */   public LocalDate plusWeeks(long paramLong) {
/* 1328 */     return plusDays(Math.multiplyExact(paramLong, 7L));
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
/*      */   public LocalDate plusDays(long paramLong) {
/* 1347 */     if (paramLong == 0L) {
/* 1348 */       return this;
/*      */     }
/* 1350 */     long l = Math.addExact(toEpochDay(), paramLong);
/* 1351 */     return ofEpochDay(l);
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
/*      */   public LocalDate minus(TemporalAmount paramTemporalAmount) {
/* 1377 */     if (paramTemporalAmount instanceof Period) {
/* 1378 */       Period period = (Period)paramTemporalAmount;
/* 1379 */       return minusMonths(period.toTotalMonths()).minusDays(period.getDays());
/*      */     } 
/* 1381 */     Objects.requireNonNull(paramTemporalAmount, "amountToSubtract");
/* 1382 */     return (LocalDate)paramTemporalAmount.subtractFrom(this);
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
/*      */   public LocalDate minus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 1406 */     return (paramLong == Long.MIN_VALUE) ? plus(Long.MAX_VALUE, paramTemporalUnit).plus(1L, paramTemporalUnit) : plus(-paramLong, paramTemporalUnit);
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
/*      */   public LocalDate minusYears(long paramLong) {
/* 1431 */     return (paramLong == Long.MIN_VALUE) ? plusYears(Long.MAX_VALUE).plusYears(1L) : plusYears(-paramLong);
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
/*      */   public LocalDate minusMonths(long paramLong) {
/* 1455 */     return (paramLong == Long.MIN_VALUE) ? plusMonths(Long.MAX_VALUE).plusMonths(1L) : plusMonths(-paramLong);
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
/*      */   public LocalDate minusWeeks(long paramLong) {
/* 1474 */     return (paramLong == Long.MIN_VALUE) ? plusWeeks(Long.MAX_VALUE).plusWeeks(1L) : plusWeeks(-paramLong);
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
/*      */   public LocalDate minusDays(long paramLong) {
/* 1493 */     return (paramLong == Long.MIN_VALUE) ? plusDays(Long.MAX_VALUE).plusDays(1L) : plusDays(-paramLong);
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
/*      */   public <R> R query(TemporalQuery<R> paramTemporalQuery) {
/* 1518 */     if (paramTemporalQuery == TemporalQueries.localDate()) {
/* 1519 */       return (R)this;
/*      */     }
/* 1521 */     return super.query(paramTemporalQuery);
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
/*      */   public Temporal adjustInto(Temporal paramTemporal) {
/* 1550 */     return super.adjustInto(paramTemporal);
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
/*      */ 
/*      */   
/*      */   public long until(Temporal paramTemporal, TemporalUnit paramTemporalUnit) {
/* 1602 */     LocalDate localDate = from(paramTemporal);
/* 1603 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 1604 */       switch ((ChronoUnit)paramTemporalUnit) { case DAYS:
/* 1605 */           return daysUntil(localDate);
/* 1606 */         case WEEKS: return daysUntil(localDate) / 7L;
/* 1607 */         case MONTHS: return monthsUntil(localDate);
/* 1608 */         case YEARS: return monthsUntil(localDate) / 12L;
/* 1609 */         case DECADES: return monthsUntil(localDate) / 120L;
/* 1610 */         case CENTURIES: return monthsUntil(localDate) / 1200L;
/* 1611 */         case MILLENNIA: return monthsUntil(localDate) / 12000L;
/* 1612 */         case ERAS: return localDate.getLong(ChronoField.ERA) - getLong(ChronoField.ERA); }
/*      */       
/* 1614 */       throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
/*      */     } 
/* 1616 */     return paramTemporalUnit.between(this, localDate);
/*      */   }
/*      */   
/*      */   long daysUntil(LocalDate paramLocalDate) {
/* 1620 */     return paramLocalDate.toEpochDay() - toEpochDay();
/*      */   }
/*      */   
/*      */   private long monthsUntil(LocalDate paramLocalDate) {
/* 1624 */     long l1 = getProlepticMonth() * 32L + getDayOfMonth();
/* 1625 */     long l2 = paramLocalDate.getProlepticMonth() * 32L + paramLocalDate.getDayOfMonth();
/* 1626 */     return (l2 - l1) / 32L;
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
/*      */   public Period until(ChronoLocalDate paramChronoLocalDate) {
/* 1663 */     LocalDate localDate = from(paramChronoLocalDate);
/* 1664 */     long l1 = localDate.getProlepticMonth() - getProlepticMonth();
/* 1665 */     int i = localDate.day - this.day;
/* 1666 */     if (l1 > 0L && i < 0) {
/* 1667 */       l1--;
/* 1668 */       LocalDate localDate1 = plusMonths(l1);
/* 1669 */       i = (int)(localDate.toEpochDay() - localDate1.toEpochDay());
/* 1670 */     } else if (l1 < 0L && i > 0) {
/* 1671 */       l1++;
/* 1672 */       i -= localDate.lengthOfMonth();
/*      */     } 
/* 1674 */     long l2 = l1 / 12L;
/* 1675 */     int j = (int)(l1 % 12L);
/* 1676 */     return Period.of(Math.toIntExact(l2), j, i);
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
/*      */   public String format(DateTimeFormatter paramDateTimeFormatter) {
/* 1690 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/* 1691 */     return paramDateTimeFormatter.format(this);
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
/*      */   public LocalDateTime atTime(LocalTime paramLocalTime) {
/* 1706 */     return LocalDateTime.of(this, paramLocalTime);
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
/*      */   public LocalDateTime atTime(int paramInt1, int paramInt2) {
/* 1724 */     return atTime(LocalTime.of(paramInt1, paramInt2));
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
/*      */   public LocalDateTime atTime(int paramInt1, int paramInt2, int paramInt3) {
/* 1743 */     return atTime(LocalTime.of(paramInt1, paramInt2, paramInt3));
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
/*      */   public LocalDateTime atTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1762 */     return atTime(LocalTime.of(paramInt1, paramInt2, paramInt3, paramInt4));
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
/*      */   public OffsetDateTime atTime(OffsetTime paramOffsetTime) {
/* 1775 */     return OffsetDateTime.of(LocalDateTime.of(this, paramOffsetTime.toLocalTime()), paramOffsetTime.getOffset());
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
/*      */   public LocalDateTime atStartOfDay() {
/* 1788 */     return LocalDateTime.of(this, LocalTime.MIDNIGHT);
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
/*      */   public ZonedDateTime atStartOfDay(ZoneId paramZoneId) {
/* 1812 */     Objects.requireNonNull(paramZoneId, "zone");
/*      */ 
/*      */     
/* 1815 */     LocalDateTime localDateTime = atTime(LocalTime.MIDNIGHT);
/* 1816 */     if (!(paramZoneId instanceof ZoneOffset)) {
/* 1817 */       ZoneRules zoneRules = paramZoneId.getRules();
/* 1818 */       ZoneOffsetTransition zoneOffsetTransition = zoneRules.getTransition(localDateTime);
/* 1819 */       if (zoneOffsetTransition != null && zoneOffsetTransition.isGap()) {
/* 1820 */         localDateTime = zoneOffsetTransition.getDateTimeAfter();
/*      */       }
/*      */     } 
/* 1823 */     return ZonedDateTime.of(localDateTime, paramZoneId);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long toEpochDay() {
/* 1829 */     long l1 = this.year;
/* 1830 */     long l2 = this.month;
/* 1831 */     long l3 = 0L;
/* 1832 */     l3 += 365L * l1;
/* 1833 */     if (l1 >= 0L) {
/* 1834 */       l3 += (l1 + 3L) / 4L - (l1 + 99L) / 100L + (l1 + 399L) / 400L;
/*      */     } else {
/* 1836 */       l3 -= l1 / -4L - l1 / -100L + l1 / -400L;
/*      */     } 
/* 1838 */     l3 += (367L * l2 - 362L) / 12L;
/* 1839 */     l3 += (this.day - 1);
/* 1840 */     if (l2 > 2L) {
/* 1841 */       l3--;
/* 1842 */       if (!isLeapYear()) {
/* 1843 */         l3--;
/*      */       }
/*      */     } 
/* 1846 */     return l3 - 719528L;
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
/*      */   public int compareTo(ChronoLocalDate paramChronoLocalDate) {
/* 1866 */     if (paramChronoLocalDate instanceof LocalDate) {
/* 1867 */       return compareTo0((LocalDate)paramChronoLocalDate);
/*      */     }
/* 1869 */     return super.compareTo(paramChronoLocalDate);
/*      */   }
/*      */   
/*      */   int compareTo0(LocalDate paramLocalDate) {
/* 1873 */     int i = this.year - paramLocalDate.year;
/* 1874 */     if (i == 0) {
/* 1875 */       i = this.month - paramLocalDate.month;
/* 1876 */       if (i == 0) {
/* 1877 */         i = this.day - paramLocalDate.day;
/*      */       }
/*      */     } 
/* 1880 */     return i;
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
/*      */   public boolean isAfter(ChronoLocalDate paramChronoLocalDate) {
/* 1906 */     if (paramChronoLocalDate instanceof LocalDate) {
/* 1907 */       return (compareTo0((LocalDate)paramChronoLocalDate) > 0);
/*      */     }
/* 1909 */     return super.isAfter(paramChronoLocalDate);
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
/*      */   public boolean isBefore(ChronoLocalDate paramChronoLocalDate) {
/* 1935 */     if (paramChronoLocalDate instanceof LocalDate) {
/* 1936 */       return (compareTo0((LocalDate)paramChronoLocalDate) < 0);
/*      */     }
/* 1938 */     return super.isBefore(paramChronoLocalDate);
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
/*      */   public boolean isEqual(ChronoLocalDate paramChronoLocalDate) {
/* 1964 */     if (paramChronoLocalDate instanceof LocalDate) {
/* 1965 */       return (compareTo0((LocalDate)paramChronoLocalDate) == 0);
/*      */     }
/* 1967 */     return super.isEqual(paramChronoLocalDate);
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
/*      */   public boolean equals(Object paramObject) {
/* 1985 */     if (this == paramObject) {
/* 1986 */       return true;
/*      */     }
/* 1988 */     if (paramObject instanceof LocalDate) {
/* 1989 */       return (compareTo0((LocalDate)paramObject) == 0);
/*      */     }
/* 1991 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 2001 */     int i = this.year;
/* 2002 */     short s1 = this.month;
/* 2003 */     short s2 = this.day;
/* 2004 */     return i & 0xFFFFF800 ^ (i << 11) + (s1 << 6) + s2;
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
/*      */   public String toString() {
/* 2017 */     int i = this.year;
/* 2018 */     short s1 = this.month;
/* 2019 */     short s2 = this.day;
/* 2020 */     int j = Math.abs(i);
/* 2021 */     StringBuilder stringBuilder = new StringBuilder(10);
/* 2022 */     if (j < 1000) {
/* 2023 */       if (i < 0) {
/* 2024 */         stringBuilder.append(i - 10000).deleteCharAt(1);
/*      */       } else {
/* 2026 */         stringBuilder.append(i + 10000).deleteCharAt(0);
/*      */       } 
/*      */     } else {
/* 2029 */       if (i > 9999) {
/* 2030 */         stringBuilder.append('+');
/*      */       }
/* 2032 */       stringBuilder.append(i);
/*      */     } 
/* 2034 */     return stringBuilder.append((s1 < 10) ? "-0" : "-")
/* 2035 */       .append(s1)
/* 2036 */       .append((s2 < 10) ? "-0" : "-")
/* 2037 */       .append(s2)
/* 2038 */       .toString();
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
/*      */   private Object writeReplace() {
/* 2056 */     return new Ser((byte)3, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 2066 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*      */   }
/*      */   
/*      */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 2070 */     paramDataOutput.writeInt(this.year);
/* 2071 */     paramDataOutput.writeByte(this.month);
/* 2072 */     paramDataOutput.writeByte(this.day);
/*      */   }
/*      */   
/*      */   static LocalDate readExternal(DataInput paramDataInput) throws IOException {
/* 2076 */     int i = paramDataInput.readInt();
/* 2077 */     byte b1 = paramDataInput.readByte();
/* 2078 */     byte b2 = paramDataInput.readByte();
/* 2079 */     return of(i, b1, b2);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/LocalDate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */