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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Year
/*      */   implements Temporal, TemporalAdjuster, Comparable<Year>, Serializable
/*      */ {
/*      */   public static final int MIN_VALUE = -999999999;
/*      */   public static final int MAX_VALUE = 999999999;
/*      */   private static final long serialVersionUID = -23038383694477807L;
/*  154 */   private static final DateTimeFormatter PARSER = (new DateTimeFormatterBuilder())
/*  155 */     .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
/*  156 */     .toFormatter();
/*      */ 
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static Year now() {
/*  176 */     return now(Clock.systemDefaultZone());
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
/*      */   public static Year now(ZoneId paramZoneId) {
/*  192 */     return now(Clock.system(paramZoneId));
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
/*      */   public static Year now(Clock paramClock) {
/*  206 */     LocalDate localDate = LocalDate.now(paramClock);
/*  207 */     return of(localDate.getYear());
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
/*      */   public static Year of(int paramInt) {
/*  226 */     ChronoField.YEAR.checkValidValue(paramInt);
/*  227 */     return new Year(paramInt);
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
/*      */   public static Year from(TemporalAccessor paramTemporalAccessor) {
/*  250 */     if (paramTemporalAccessor instanceof Year) {
/*  251 */       return (Year)paramTemporalAccessor;
/*      */     }
/*  253 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/*      */     try {
/*  255 */       if (!IsoChronology.INSTANCE.equals(Chronology.from(paramTemporalAccessor))) {
/*  256 */         paramTemporalAccessor = LocalDate.from(paramTemporalAccessor);
/*      */       }
/*  258 */       return of(paramTemporalAccessor.get(ChronoField.YEAR));
/*  259 */     } catch (DateTimeException dateTimeException) {
/*  260 */       throw new DateTimeException("Unable to obtain Year from TemporalAccessor: " + paramTemporalAccessor + " of type " + paramTemporalAccessor
/*  261 */           .getClass().getName(), dateTimeException);
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
/*      */   public static Year parse(CharSequence paramCharSequence) {
/*  277 */     return parse(paramCharSequence, PARSER);
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
/*      */   public static Year parse(CharSequence paramCharSequence, DateTimeFormatter paramDateTimeFormatter) {
/*  291 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/*  292 */     return paramDateTimeFormatter.<Year>parse(paramCharSequence, Year::from);
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
/*      */   public static boolean isLeap(long paramLong) {
/*  316 */     return ((paramLong & 0x3L) == 0L && (paramLong % 100L != 0L || paramLong % 400L == 0L));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Year(int paramInt) {
/*  326 */     this.year = paramInt;
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
/*      */   public int getValue() {
/*  338 */     return this.year;
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
/*      */   public boolean isSupported(TemporalField paramTemporalField) {
/*  369 */     if (paramTemporalField instanceof ChronoField) {
/*  370 */       return (paramTemporalField == ChronoField.YEAR || paramTemporalField == ChronoField.YEAR_OF_ERA || paramTemporalField == ChronoField.ERA);
/*      */     }
/*  372 */     return (paramTemporalField != null && paramTemporalField.isSupportedBy(this));
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
/*      */   public boolean isSupported(TemporalUnit paramTemporalUnit) {
/*  403 */     if (paramTemporalUnit instanceof ChronoUnit) {
/*  404 */       return (paramTemporalUnit == ChronoUnit.YEARS || paramTemporalUnit == ChronoUnit.DECADES || paramTemporalUnit == ChronoUnit.CENTURIES || paramTemporalUnit == ChronoUnit.MILLENNIA || paramTemporalUnit == ChronoUnit.ERAS);
/*      */     }
/*  406 */     return (paramTemporalUnit != null && paramTemporalUnit.isSupportedBy(this));
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
/*  435 */     if (paramTemporalField == ChronoField.YEAR_OF_ERA) {
/*  436 */       return (this.year <= 0) ? ValueRange.of(1L, 1000000000L) : ValueRange.of(1L, 999999999L);
/*      */     }
/*  438 */     return super.range(paramTemporalField);
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
/*      */   public int get(TemporalField paramTemporalField) {
/*  469 */     return range(paramTemporalField).checkValidIntValue(getLong(paramTemporalField), paramTemporalField);
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
/*  497 */     if (paramTemporalField instanceof ChronoField) {
/*  498 */       switch ((ChronoField)paramTemporalField) { case YEARS:
/*  499 */           return ((this.year < 1) ? (1 - this.year) : this.year);
/*  500 */         case DECADES: return this.year;
/*  501 */         case CENTURIES: return ((this.year < 1) ? 0L : 1L); }
/*      */       
/*  503 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*      */     } 
/*  505 */     return paramTemporalField.getFrom(this);
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
/*      */   public boolean isLeap() {
/*  528 */     return isLeap(this.year);
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
/*      */   public boolean isValidMonthDay(MonthDay paramMonthDay) {
/*  541 */     return (paramMonthDay != null && paramMonthDay.isValidYear(this.year));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int length() {
/*  550 */     return isLeap() ? 366 : 365;
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
/*      */   public Year with(TemporalAdjuster paramTemporalAdjuster) {
/*  574 */     return (Year)paramTemporalAdjuster.adjustInto(this);
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
/*      */   public Year with(TemporalField paramTemporalField, long paramLong) {
/*  620 */     if (paramTemporalField instanceof ChronoField) {
/*  621 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/*  622 */       chronoField.checkValidValue(paramLong);
/*  623 */       switch (chronoField) { case YEARS:
/*  624 */           return of((int)((this.year < 1) ? (1L - paramLong) : paramLong));
/*  625 */         case DECADES: return of((int)paramLong);
/*  626 */         case CENTURIES: return (getLong(ChronoField.ERA) == paramLong) ? this : of(1 - this.year); }
/*      */       
/*  628 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*      */     } 
/*  630 */     return paramTemporalField.<Year>adjustInto(this, paramLong);
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
/*      */   public Year plus(TemporalAmount paramTemporalAmount) {
/*  656 */     return (Year)paramTemporalAmount.addTo(this);
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
/*      */   public Year plus(long paramLong, TemporalUnit paramTemporalUnit) {
/*  709 */     if (paramTemporalUnit instanceof ChronoUnit) {
/*  710 */       switch ((ChronoUnit)paramTemporalUnit) { case YEARS:
/*  711 */           return plusYears(paramLong);
/*  712 */         case DECADES: return plusYears(Math.multiplyExact(paramLong, 10L));
/*  713 */         case CENTURIES: return plusYears(Math.multiplyExact(paramLong, 100L));
/*  714 */         case MILLENNIA: return plusYears(Math.multiplyExact(paramLong, 1000L));
/*  715 */         case ERAS: return with(ChronoField.ERA, Math.addExact(getLong(ChronoField.ERA), paramLong)); }
/*      */       
/*  717 */       throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
/*      */     } 
/*  719 */     return paramTemporalUnit.<Year>addTo(this, paramLong);
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
/*      */   public Year plusYears(long paramLong) {
/*  732 */     if (paramLong == 0L) {
/*  733 */       return this;
/*      */     }
/*  735 */     return of(ChronoField.YEAR.checkValidIntValue(this.year + paramLong));
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
/*      */   public Year minus(TemporalAmount paramTemporalAmount) {
/*  761 */     return (Year)paramTemporalAmount.subtractFrom(this);
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
/*      */   public Year minus(long paramLong, TemporalUnit paramTemporalUnit) {
/*  785 */     return (paramLong == Long.MIN_VALUE) ? plus(Long.MAX_VALUE, paramTemporalUnit).plus(1L, paramTemporalUnit) : plus(-paramLong, paramTemporalUnit);
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
/*      */   public Year minusYears(long paramLong) {
/*  798 */     return (paramLong == Long.MIN_VALUE) ? plusYears(Long.MAX_VALUE).plusYears(1L) : plusYears(-paramLong);
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
/*  823 */     if (paramTemporalQuery == TemporalQueries.chronology())
/*  824 */       return (R)IsoChronology.INSTANCE; 
/*  825 */     if (paramTemporalQuery == TemporalQueries.precision()) {
/*  826 */       return (R)ChronoUnit.YEARS;
/*      */     }
/*  828 */     return super.query(paramTemporalQuery);
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
/*  859 */     if (!Chronology.from(paramTemporal).equals(IsoChronology.INSTANCE)) {
/*  860 */       throw new DateTimeException("Adjustment only supported on ISO date-time");
/*      */     }
/*  862 */     return paramTemporal.with(ChronoField.YEAR, this.year);
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
/*  914 */     Year year = from(paramTemporal);
/*  915 */     if (paramTemporalUnit instanceof ChronoUnit) {
/*  916 */       long l = year.year - this.year;
/*  917 */       switch ((ChronoUnit)paramTemporalUnit) { case YEARS:
/*  918 */           return l;
/*  919 */         case DECADES: return l / 10L;
/*  920 */         case CENTURIES: return l / 100L;
/*  921 */         case MILLENNIA: return l / 1000L;
/*  922 */         case ERAS: return year.getLong(ChronoField.ERA) - getLong(ChronoField.ERA); }
/*      */       
/*  924 */       throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
/*      */     } 
/*  926 */     return paramTemporalUnit.between(this, year);
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
/*  939 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/*  940 */     return paramDateTimeFormatter.format(this);
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
/*      */   public LocalDate atDay(int paramInt) {
/*  957 */     return LocalDate.ofYearDay(this.year, paramInt);
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
/*      */   public YearMonth atMonth(Month paramMonth) {
/*  975 */     return YearMonth.of(this.year, paramMonth);
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
/*      */   public YearMonth atMonth(int paramInt) {
/*  994 */     return YearMonth.of(this.year, paramInt);
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
/*      */   public LocalDate atMonthDay(MonthDay paramMonthDay) {
/* 1009 */     return paramMonthDay.atYear(this.year);
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
/*      */   public int compareTo(Year paramYear) {
/* 1024 */     return this.year - paramYear.year;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAfter(Year paramYear) {
/* 1034 */     return (this.year > paramYear.year);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBefore(Year paramYear) {
/* 1044 */     return (this.year < paramYear.year);
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
/* 1058 */     if (this == paramObject) {
/* 1059 */       return true;
/*      */     }
/* 1061 */     if (paramObject instanceof Year) {
/* 1062 */       return (this.year == ((Year)paramObject).year);
/*      */     }
/* 1064 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1074 */     return this.year;
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
/* 1085 */     return Integer.toString(this.year);
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
/*      */   private Object writeReplace() {
/* 1101 */     return new Ser((byte)11, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 1111 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*      */   }
/*      */   
/*      */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 1115 */     paramDataOutput.writeInt(this.year);
/*      */   }
/*      */   
/*      */   static Year readExternal(DataInput paramDataInput) throws IOException {
/* 1119 */     return of(paramDataInput.readInt());
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/Year.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */