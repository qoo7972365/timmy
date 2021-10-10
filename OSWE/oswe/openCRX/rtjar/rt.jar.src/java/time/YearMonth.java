/*      */ package java.time;
/*      */ 
/*      */ import java.io.DataInput;
/*      */ import java.io.DataOutput;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.time.chrono.Chronology;
/*      */ import java.time.chrono.IsoChronology;
/*      */ import java.time.format.DateTimeFormatter;
/*      */ import java.time.format.DateTimeFormatterBuilder;
/*      */ import java.time.format.SignStyle;
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
/*      */ public final class YearMonth
/*      */   implements Temporal, TemporalAdjuster, Comparable<YearMonth>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 4183400860270640070L;
/*  141 */   private static final DateTimeFormatter PARSER = (new DateTimeFormatterBuilder())
/*  142 */     .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
/*  143 */     .appendLiteral('-')
/*  144 */     .appendValue(ChronoField.MONTH_OF_YEAR, 2)
/*  145 */     .toFormatter();
/*      */ 
/*      */ 
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
/*      */   
/*      */   private final int month;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static YearMonth now() {
/*  169 */     return now(Clock.systemDefaultZone());
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
/*      */   public static YearMonth now(ZoneId paramZoneId) {
/*  185 */     return now(Clock.system(paramZoneId));
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
/*      */   public static YearMonth now(Clock paramClock) {
/*  199 */     LocalDate localDate = LocalDate.now(paramClock);
/*  200 */     return of(localDate.getYear(), localDate.getMonth());
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
/*      */   public static YearMonth of(int paramInt, Month paramMonth) {
/*  213 */     Objects.requireNonNull(paramMonth, "month");
/*  214 */     return of(paramInt, paramMonth.getValue());
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
/*      */   public static YearMonth of(int paramInt1, int paramInt2) {
/*  226 */     ChronoField.YEAR.checkValidValue(paramInt1);
/*  227 */     ChronoField.MONTH_OF_YEAR.checkValidValue(paramInt2);
/*  228 */     return new YearMonth(paramInt1, paramInt2);
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
/*      */   public static YearMonth from(TemporalAccessor paramTemporalAccessor) {
/*  252 */     if (paramTemporalAccessor instanceof YearMonth) {
/*  253 */       return (YearMonth)paramTemporalAccessor;
/*      */     }
/*  255 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/*      */     try {
/*  257 */       if (!IsoChronology.INSTANCE.equals(Chronology.from(paramTemporalAccessor))) {
/*  258 */         paramTemporalAccessor = LocalDate.from(paramTemporalAccessor);
/*      */       }
/*  260 */       return of(paramTemporalAccessor.get(ChronoField.YEAR), paramTemporalAccessor.get(ChronoField.MONTH_OF_YEAR));
/*  261 */     } catch (DateTimeException dateTimeException) {
/*  262 */       throw new DateTimeException("Unable to obtain YearMonth from TemporalAccessor: " + paramTemporalAccessor + " of type " + paramTemporalAccessor
/*  263 */           .getClass().getName(), dateTimeException);
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
/*      */ 
/*      */   
/*      */   public static YearMonth parse(CharSequence paramCharSequence) {
/*  280 */     return parse(paramCharSequence, PARSER);
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
/*      */   public static YearMonth parse(CharSequence paramCharSequence, DateTimeFormatter paramDateTimeFormatter) {
/*  294 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/*  295 */     return paramDateTimeFormatter.<YearMonth>parse(paramCharSequence, YearMonth::from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private YearMonth(int paramInt1, int paramInt2) {
/*  306 */     this.year = paramInt1;
/*  307 */     this.month = paramInt2;
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
/*      */   private YearMonth with(int paramInt1, int paramInt2) {
/*  319 */     if (this.year == paramInt1 && this.month == paramInt2) {
/*  320 */       return this;
/*      */     }
/*  322 */     return new YearMonth(paramInt1, paramInt2);
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
/*      */   public boolean isSupported(TemporalField paramTemporalField) {
/*  355 */     if (paramTemporalField instanceof ChronoField) {
/*  356 */       return (paramTemporalField == ChronoField.YEAR || paramTemporalField == ChronoField.MONTH_OF_YEAR || paramTemporalField == ChronoField.PROLEPTIC_MONTH || paramTemporalField == ChronoField.YEAR_OF_ERA || paramTemporalField == ChronoField.ERA);
/*      */     }
/*      */     
/*  359 */     return (paramTemporalField != null && paramTemporalField.isSupportedBy(this));
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
/*      */   public boolean isSupported(TemporalUnit paramTemporalUnit) {
/*  391 */     if (paramTemporalUnit instanceof ChronoUnit) {
/*  392 */       return (paramTemporalUnit == ChronoUnit.MONTHS || paramTemporalUnit == ChronoUnit.YEARS || paramTemporalUnit == ChronoUnit.DECADES || paramTemporalUnit == ChronoUnit.CENTURIES || paramTemporalUnit == ChronoUnit.MILLENNIA || paramTemporalUnit == ChronoUnit.ERAS);
/*      */     }
/*  394 */     return (paramTemporalUnit != null && paramTemporalUnit.isSupportedBy(this));
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
/*  423 */     if (paramTemporalField == ChronoField.YEAR_OF_ERA) {
/*  424 */       return (getYear() <= 0) ? ValueRange.of(1L, 1000000000L) : ValueRange.of(1L, 999999999L);
/*      */     }
/*  426 */     return super.range(paramTemporalField);
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
/*  458 */     return range(paramTemporalField).checkValidIntValue(getLong(paramTemporalField), paramTemporalField);
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
/*  486 */     if (paramTemporalField instanceof ChronoField) {
/*  487 */       switch ((ChronoField)paramTemporalField) { case MONTHS:
/*  488 */           return this.month;
/*  489 */         case YEARS: return getProlepticMonth();
/*  490 */         case DECADES: return ((this.year < 1) ? (1 - this.year) : this.year);
/*  491 */         case CENTURIES: return this.year;
/*  492 */         case MILLENNIA: return ((this.year < 1) ? 0L : 1L); }
/*      */       
/*  494 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*      */     } 
/*  496 */     return paramTemporalField.getFrom(this);
/*      */   }
/*      */   
/*      */   private long getProlepticMonth() {
/*  500 */     return this.year * 12L + this.month - 1L;
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
/*  514 */     return this.year;
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
/*  528 */     return this.month;
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
/*  543 */     return Month.of(this.month);
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
/*      */   public boolean isLeapYear() {
/*  566 */     return IsoChronology.INSTANCE.isLeapYear(this.year);
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
/*      */   public boolean isValidDay(int paramInt) {
/*  579 */     return (paramInt >= 1 && paramInt <= lengthOfMonth());
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
/*      */   public int lengthOfMonth() {
/*  591 */     return getMonth().length(isLeapYear());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lengthOfYear() {
/*  602 */     return isLeapYear() ? 366 : 365;
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
/*      */   public YearMonth with(TemporalAdjuster paramTemporalAdjuster) {
/*  630 */     return (YearMonth)paramTemporalAdjuster.adjustInto(this);
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
/*      */   public YearMonth with(TemporalField paramTemporalField, long paramLong) {
/*  683 */     if (paramTemporalField instanceof ChronoField) {
/*  684 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/*  685 */       chronoField.checkValidValue(paramLong);
/*  686 */       switch (chronoField) { case MONTHS:
/*  687 */           return withMonth((int)paramLong);
/*  688 */         case YEARS: return plusMonths(paramLong - getProlepticMonth());
/*  689 */         case DECADES: return withYear((int)((this.year < 1) ? (1L - paramLong) : paramLong));
/*  690 */         case CENTURIES: return withYear((int)paramLong);
/*  691 */         case MILLENNIA: return (getLong(ChronoField.ERA) == paramLong) ? this : withYear(1 - this.year); }
/*      */       
/*  693 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*      */     } 
/*  695 */     return paramTemporalField.<YearMonth>adjustInto(this, paramLong);
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
/*      */   public YearMonth withYear(int paramInt) {
/*  709 */     ChronoField.YEAR.checkValidValue(paramInt);
/*  710 */     return with(paramInt, this.month);
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
/*      */   public YearMonth withMonth(int paramInt) {
/*  723 */     ChronoField.MONTH_OF_YEAR.checkValidValue(paramInt);
/*  724 */     return with(this.year, paramInt);
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
/*      */   public YearMonth plus(TemporalAmount paramTemporalAmount) {
/*  750 */     return (YearMonth)paramTemporalAmount.addTo(this);
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
/*      */   public YearMonth plus(long paramLong, TemporalUnit paramTemporalUnit) {
/*  806 */     if (paramTemporalUnit instanceof ChronoUnit) {
/*  807 */       switch ((ChronoUnit)paramTemporalUnit) { case MONTHS:
/*  808 */           return plusMonths(paramLong);
/*  809 */         case YEARS: return plusYears(paramLong);
/*  810 */         case DECADES: return plusYears(Math.multiplyExact(paramLong, 10L));
/*  811 */         case CENTURIES: return plusYears(Math.multiplyExact(paramLong, 100L));
/*  812 */         case MILLENNIA: return plusYears(Math.multiplyExact(paramLong, 1000L));
/*  813 */         case ERAS: return with(ChronoField.ERA, Math.addExact(getLong(ChronoField.ERA), paramLong)); }
/*      */       
/*  815 */       throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
/*      */     } 
/*  817 */     return paramTemporalUnit.<YearMonth>addTo(this, paramLong);
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
/*      */   public YearMonth plusYears(long paramLong) {
/*  830 */     if (paramLong == 0L) {
/*  831 */       return this;
/*      */     }
/*  833 */     int i = ChronoField.YEAR.checkValidIntValue(this.year + paramLong);
/*  834 */     return with(i, this.month);
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
/*      */   public YearMonth plusMonths(long paramLong) {
/*  847 */     if (paramLong == 0L) {
/*  848 */       return this;
/*      */     }
/*  850 */     long l1 = this.year * 12L + (this.month - 1);
/*  851 */     long l2 = l1 + paramLong;
/*  852 */     int i = ChronoField.YEAR.checkValidIntValue(Math.floorDiv(l2, 12L));
/*  853 */     int j = (int)Math.floorMod(l2, 12L) + 1;
/*  854 */     return with(i, j);
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
/*      */   public YearMonth minus(TemporalAmount paramTemporalAmount) {
/*  880 */     return (YearMonth)paramTemporalAmount.subtractFrom(this);
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
/*      */   public YearMonth minus(long paramLong, TemporalUnit paramTemporalUnit) {
/*  904 */     return (paramLong == Long.MIN_VALUE) ? plus(Long.MAX_VALUE, paramTemporalUnit).plus(1L, paramTemporalUnit) : plus(-paramLong, paramTemporalUnit);
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
/*      */   public YearMonth minusYears(long paramLong) {
/*  917 */     return (paramLong == Long.MIN_VALUE) ? plusYears(Long.MAX_VALUE).plusYears(1L) : plusYears(-paramLong);
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
/*      */   public YearMonth minusMonths(long paramLong) {
/*  930 */     return (paramLong == Long.MIN_VALUE) ? plusMonths(Long.MAX_VALUE).plusMonths(1L) : plusMonths(-paramLong);
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
/*  955 */     if (paramTemporalQuery == TemporalQueries.chronology())
/*  956 */       return (R)IsoChronology.INSTANCE; 
/*  957 */     if (paramTemporalQuery == TemporalQueries.precision()) {
/*  958 */       return (R)ChronoUnit.MONTHS;
/*      */     }
/*  960 */     return super.query(paramTemporalQuery);
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
/*      */   public Temporal adjustInto(Temporal paramTemporal) {
/*  991 */     if (!Chronology.from(paramTemporal).equals(IsoChronology.INSTANCE)) {
/*  992 */       throw new DateTimeException("Adjustment only supported on ISO date-time");
/*      */     }
/*  994 */     return paramTemporal.with(ChronoField.PROLEPTIC_MONTH, getProlepticMonth());
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
/* 1046 */     YearMonth yearMonth = from(paramTemporal);
/* 1047 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 1048 */       long l = yearMonth.getProlepticMonth() - getProlepticMonth();
/* 1049 */       switch ((ChronoUnit)paramTemporalUnit) { case MONTHS:
/* 1050 */           return l;
/* 1051 */         case YEARS: return l / 12L;
/* 1052 */         case DECADES: return l / 120L;
/* 1053 */         case CENTURIES: return l / 1200L;
/* 1054 */         case MILLENNIA: return l / 12000L;
/* 1055 */         case ERAS: return yearMonth.getLong(ChronoField.ERA) - getLong(ChronoField.ERA); }
/*      */       
/* 1057 */       throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
/*      */     } 
/* 1059 */     return paramTemporalUnit.between(this, yearMonth);
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
/*      */   public String format(DateTimeFormatter paramDateTimeFormatter) {
/* 1072 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/* 1073 */     return paramDateTimeFormatter.format(this);
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
/*      */   public LocalDate atDay(int paramInt) {
/* 1095 */     return LocalDate.of(this.year, this.month, paramInt);
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
/*      */   public LocalDate atEndOfMonth() {
/* 1113 */     return LocalDate.of(this.year, this.month, lengthOfMonth());
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
/*      */   public int compareTo(YearMonth paramYearMonth) {
/* 1128 */     int i = this.year - paramYearMonth.year;
/* 1129 */     if (i == 0) {
/* 1130 */       i = this.month - paramYearMonth.month;
/*      */     }
/* 1132 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAfter(YearMonth paramYearMonth) {
/* 1142 */     return (compareTo(paramYearMonth) > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBefore(YearMonth paramYearMonth) {
/* 1152 */     return (compareTo(paramYearMonth) < 0);
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
/*      */   public boolean equals(Object paramObject) {
/* 1166 */     if (this == paramObject) {
/* 1167 */       return true;
/*      */     }
/* 1169 */     if (paramObject instanceof YearMonth) {
/* 1170 */       YearMonth yearMonth = (YearMonth)paramObject;
/* 1171 */       return (this.year == yearMonth.year && this.month == yearMonth.month);
/*      */     } 
/* 1173 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1183 */     return this.year ^ this.month << 27;
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
/* 1196 */     int i = Math.abs(this.year);
/* 1197 */     StringBuilder stringBuilder = new StringBuilder(9);
/* 1198 */     if (i < 1000) {
/* 1199 */       if (this.year < 0) {
/* 1200 */         stringBuilder.append(this.year - 10000).deleteCharAt(1);
/*      */       } else {
/* 1202 */         stringBuilder.append(this.year + 10000).deleteCharAt(0);
/*      */       } 
/*      */     } else {
/* 1205 */       stringBuilder.append(this.year);
/*      */     } 
/* 1207 */     return stringBuilder.append((this.month < 10) ? "-0" : "-")
/* 1208 */       .append(this.month)
/* 1209 */       .toString();
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
/*      */   private Object writeReplace() {
/* 1226 */     return new Ser((byte)12, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 1236 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*      */   }
/*      */   
/*      */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 1240 */     paramDataOutput.writeInt(this.year);
/* 1241 */     paramDataOutput.writeByte(this.month);
/*      */   }
/*      */   
/*      */   static YearMonth readExternal(DataInput paramDataInput) throws IOException {
/* 1245 */     int i = paramDataInput.readInt();
/* 1246 */     byte b = paramDataInput.readByte();
/* 1247 */     return of(i, b);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/YearMonth.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */