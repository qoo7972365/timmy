/*      */ package java.time;
/*      */ 
/*      */ import java.io.DataInput;
/*      */ import java.io.DataOutput;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.time.chrono.ChronoPeriod;
/*      */ import java.time.chrono.Chronology;
/*      */ import java.time.chrono.IsoChronology;
/*      */ import java.time.format.DateTimeParseException;
/*      */ import java.time.temporal.ChronoUnit;
/*      */ import java.time.temporal.Temporal;
/*      */ import java.time.temporal.TemporalAccessor;
/*      */ import java.time.temporal.TemporalAmount;
/*      */ import java.time.temporal.TemporalQueries;
/*      */ import java.time.temporal.TemporalUnit;
/*      */ import java.time.temporal.UnsupportedTemporalTypeException;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Period
/*      */   implements ChronoPeriod, Serializable
/*      */ {
/*  141 */   public static final Period ZERO = new Period(0, 0, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = -3587258372562876L;
/*      */ 
/*      */ 
/*      */   
/*  150 */   private static final Pattern PATTERN = Pattern.compile("([-+]?)P(?:([-+]?[0-9]+)Y)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)W)?(?:([-+]?[0-9]+)D)?", 2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  156 */   private static final List<TemporalUnit> SUPPORTED_UNITS = Collections.unmodifiableList(Arrays.asList(new TemporalUnit[] { ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS }));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int years;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int months;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int days;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Period ofYears(int paramInt) {
/*  182 */     return create(paramInt, 0, 0);
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
/*      */   public static Period ofMonths(int paramInt) {
/*  195 */     return create(0, paramInt, 0);
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
/*      */   public static Period ofWeeks(int paramInt) {
/*  209 */     return create(0, 0, Math.multiplyExact(paramInt, 7));
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
/*      */   public static Period ofDays(int paramInt) {
/*  222 */     return create(0, 0, paramInt);
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
/*      */   public static Period of(int paramInt1, int paramInt2, int paramInt3) {
/*  237 */     return create(paramInt1, paramInt2, paramInt3);
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
/*      */   public static Period from(TemporalAmount paramTemporalAmount) {
/*  261 */     if (paramTemporalAmount instanceof Period) {
/*  262 */       return (Period)paramTemporalAmount;
/*      */     }
/*  264 */     if (paramTemporalAmount instanceof ChronoPeriod && 
/*  265 */       !IsoChronology.INSTANCE.equals(((ChronoPeriod)paramTemporalAmount).getChronology())) {
/*  266 */       throw new DateTimeException("Period requires ISO chronology: " + paramTemporalAmount);
/*      */     }
/*      */     
/*  269 */     Objects.requireNonNull(paramTemporalAmount, "amount");
/*  270 */     int i = 0;
/*  271 */     int j = 0;
/*  272 */     int k = 0;
/*  273 */     for (TemporalUnit temporalUnit : paramTemporalAmount.getUnits()) {
/*  274 */       long l = paramTemporalAmount.get(temporalUnit);
/*  275 */       if (temporalUnit == ChronoUnit.YEARS) {
/*  276 */         i = Math.toIntExact(l); continue;
/*  277 */       }  if (temporalUnit == ChronoUnit.MONTHS) {
/*  278 */         j = Math.toIntExact(l); continue;
/*  279 */       }  if (temporalUnit == ChronoUnit.DAYS) {
/*  280 */         k = Math.toIntExact(l); continue;
/*      */       } 
/*  282 */       throw new DateTimeException("Unit must be Years, Months or Days, but was " + temporalUnit);
/*      */     } 
/*      */     
/*  285 */     return create(i, j, k);
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
/*      */   public static Period parse(CharSequence paramCharSequence) {
/*  329 */     Objects.requireNonNull(paramCharSequence, "text");
/*  330 */     Matcher matcher = PATTERN.matcher(paramCharSequence);
/*  331 */     if (matcher.matches()) {
/*  332 */       boolean bool = "-".equals(matcher.group(1)) ? true : true;
/*  333 */       String str1 = matcher.group(2);
/*  334 */       String str2 = matcher.group(3);
/*  335 */       String str3 = matcher.group(4);
/*  336 */       String str4 = matcher.group(5);
/*  337 */       if (str1 != null || str2 != null || str4 != null || str3 != null) {
/*      */         try {
/*  339 */           int i = parseNumber(paramCharSequence, str1, bool);
/*  340 */           int j = parseNumber(paramCharSequence, str2, bool);
/*  341 */           int k = parseNumber(paramCharSequence, str3, bool);
/*  342 */           int m = parseNumber(paramCharSequence, str4, bool);
/*  343 */           m = Math.addExact(m, Math.multiplyExact(k, 7));
/*  344 */           return create(i, j, m);
/*  345 */         } catch (NumberFormatException numberFormatException) {
/*  346 */           throw new DateTimeParseException("Text cannot be parsed to a Period", paramCharSequence, 0, numberFormatException);
/*      */         } 
/*      */       }
/*      */     } 
/*  350 */     throw new DateTimeParseException("Text cannot be parsed to a Period", paramCharSequence, 0);
/*      */   }
/*      */   
/*      */   private static int parseNumber(CharSequence paramCharSequence, String paramString, int paramInt) {
/*  354 */     if (paramString == null) {
/*  355 */       return 0;
/*      */     }
/*  357 */     int i = Integer.parseInt(paramString);
/*      */     try {
/*  359 */       return Math.multiplyExact(i, paramInt);
/*  360 */     } catch (ArithmeticException arithmeticException) {
/*  361 */       throw new DateTimeParseException("Text cannot be parsed to a Period", paramCharSequence, 0, arithmeticException);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Period between(LocalDate paramLocalDate1, LocalDate paramLocalDate2) {
/*  386 */     return paramLocalDate1.until(paramLocalDate2);
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
/*      */   private static Period create(int paramInt1, int paramInt2, int paramInt3) {
/*  398 */     if ((paramInt1 | paramInt2 | paramInt3) == 0) {
/*  399 */       return ZERO;
/*      */     }
/*  401 */     return new Period(paramInt1, paramInt2, paramInt3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Period(int paramInt1, int paramInt2, int paramInt3) {
/*  412 */     this.years = paramInt1;
/*  413 */     this.months = paramInt2;
/*  414 */     this.days = paramInt3;
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
/*      */   public long get(TemporalUnit paramTemporalUnit) {
/*  433 */     if (paramTemporalUnit == ChronoUnit.YEARS)
/*  434 */       return getYears(); 
/*  435 */     if (paramTemporalUnit == ChronoUnit.MONTHS)
/*  436 */       return getMonths(); 
/*  437 */     if (paramTemporalUnit == ChronoUnit.DAYS) {
/*  438 */       return getDays();
/*      */     }
/*  440 */     throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
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
/*      */   public List<TemporalUnit> getUnits() {
/*  458 */     return SUPPORTED_UNITS;
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
/*      */   public IsoChronology getChronology() {
/*  473 */     return IsoChronology.INSTANCE;
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
/*      */   public boolean isZero() {
/*  485 */     return (this == ZERO);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNegative() {
/*  496 */     return (this.years < 0 || this.months < 0 || this.days < 0);
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
/*      */   public int getYears() {
/*  512 */     return this.years;
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
/*      */   public int getMonths() {
/*  527 */     return this.months;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDays() {
/*  538 */     return this.days;
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
/*      */   public Period withYears(int paramInt) {
/*  558 */     if (paramInt == this.years) {
/*  559 */       return this;
/*      */     }
/*  561 */     return create(paramInt, this.months, this.days);
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
/*      */   public Period withMonths(int paramInt) {
/*  580 */     if (paramInt == this.months) {
/*  581 */       return this;
/*      */     }
/*  583 */     return create(this.years, paramInt, this.days);
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
/*      */   public Period withDays(int paramInt) {
/*  598 */     if (paramInt == this.days) {
/*  599 */       return this;
/*      */     }
/*  601 */     return create(this.years, this.months, paramInt);
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
/*      */   public Period plus(TemporalAmount paramTemporalAmount) {
/*  626 */     Period period = from(paramTemporalAmount);
/*  627 */     return create(
/*  628 */         Math.addExact(this.years, period.years), 
/*  629 */         Math.addExact(this.months, period.months), 
/*  630 */         Math.addExact(this.days, period.days));
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
/*      */   public Period plusYears(long paramLong) {
/*  647 */     if (paramLong == 0L) {
/*  648 */       return this;
/*      */     }
/*  650 */     return create(Math.toIntExact(Math.addExact(this.years, paramLong)), this.months, this.days);
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
/*      */   public Period plusMonths(long paramLong) {
/*  667 */     if (paramLong == 0L) {
/*  668 */       return this;
/*      */     }
/*  670 */     return create(this.years, Math.toIntExact(Math.addExact(this.months, paramLong)), this.days);
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
/*      */   public Period plusDays(long paramLong) {
/*  687 */     if (paramLong == 0L) {
/*  688 */       return this;
/*      */     }
/*  690 */     return create(this.years, this.months, Math.toIntExact(Math.addExact(this.days, paramLong)));
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
/*      */   public Period minus(TemporalAmount paramTemporalAmount) {
/*  715 */     Period period = from(paramTemporalAmount);
/*  716 */     return create(
/*  717 */         Math.subtractExact(this.years, period.years), 
/*  718 */         Math.subtractExact(this.months, period.months), 
/*  719 */         Math.subtractExact(this.days, period.days));
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
/*      */   public Period minusYears(long paramLong) {
/*  736 */     return (paramLong == Long.MIN_VALUE) ? plusYears(Long.MAX_VALUE).plusYears(1L) : plusYears(-paramLong);
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
/*      */   public Period minusMonths(long paramLong) {
/*  753 */     return (paramLong == Long.MIN_VALUE) ? plusMonths(Long.MAX_VALUE).plusMonths(1L) : plusMonths(-paramLong);
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
/*      */   public Period minusDays(long paramLong) {
/*  770 */     return (paramLong == Long.MIN_VALUE) ? plusDays(Long.MAX_VALUE).plusDays(1L) : plusDays(-paramLong);
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
/*      */   public Period multipliedBy(int paramInt) {
/*  789 */     if (this == ZERO || paramInt == 1) {
/*  790 */       return this;
/*      */     }
/*  792 */     return create(
/*  793 */         Math.multiplyExact(this.years, paramInt), 
/*  794 */         Math.multiplyExact(this.months, paramInt), 
/*  795 */         Math.multiplyExact(this.days, paramInt));
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
/*      */   public Period negated() {
/*  812 */     return multipliedBy(-1);
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
/*      */   public Period normalized() {
/*  834 */     long l1 = toTotalMonths();
/*  835 */     long l2 = l1 / 12L;
/*  836 */     int i = (int)(l1 % 12L);
/*  837 */     if (l2 == this.years && i == this.months) {
/*  838 */       return this;
/*      */     }
/*  840 */     return create(Math.toIntExact(l2), i, this.days);
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
/*      */   public long toTotalMonths() {
/*  854 */     return this.years * 12L + this.months;
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
/*      */   public Temporal addTo(Temporal paramTemporal) {
/*  894 */     validateChrono(paramTemporal);
/*  895 */     if (this.months == 0) {
/*  896 */       if (this.years != 0) {
/*  897 */         paramTemporal = paramTemporal.plus(this.years, ChronoUnit.YEARS);
/*      */       }
/*      */     } else {
/*  900 */       long l = toTotalMonths();
/*  901 */       if (l != 0L) {
/*  902 */         paramTemporal = paramTemporal.plus(l, ChronoUnit.MONTHS);
/*      */       }
/*      */     } 
/*  905 */     if (this.days != 0) {
/*  906 */       paramTemporal = paramTemporal.plus(this.days, ChronoUnit.DAYS);
/*      */     }
/*  908 */     return paramTemporal;
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
/*      */   public Temporal subtractFrom(Temporal paramTemporal) {
/*  947 */     validateChrono(paramTemporal);
/*  948 */     if (this.months == 0) {
/*  949 */       if (this.years != 0) {
/*  950 */         paramTemporal = paramTemporal.minus(this.years, ChronoUnit.YEARS);
/*      */       }
/*      */     } else {
/*  953 */       long l = toTotalMonths();
/*  954 */       if (l != 0L) {
/*  955 */         paramTemporal = paramTemporal.minus(l, ChronoUnit.MONTHS);
/*      */       }
/*      */     } 
/*  958 */     if (this.days != 0) {
/*  959 */       paramTemporal = paramTemporal.minus(this.days, ChronoUnit.DAYS);
/*      */     }
/*  961 */     return paramTemporal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateChrono(TemporalAccessor paramTemporalAccessor) {
/*  968 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/*  969 */     Chronology chronology = paramTemporalAccessor.<Chronology>query(TemporalQueries.chronology());
/*  970 */     if (chronology != null && !IsoChronology.INSTANCE.equals(chronology)) {
/*  971 */       throw new DateTimeException("Chronology mismatch, expected: ISO, actual: " + chronology.getId());
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
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*  989 */     if (this == paramObject) {
/*  990 */       return true;
/*      */     }
/*  992 */     if (paramObject instanceof Period) {
/*  993 */       Period period = (Period)paramObject;
/*  994 */       return (this.years == period.years && this.months == period.months && this.days == period.days);
/*      */     } 
/*      */ 
/*      */     
/*  998 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1008 */     return this.years + Integer.rotateLeft(this.months, 8) + Integer.rotateLeft(this.days, 16);
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
/*      */   public String toString() {
/* 1022 */     if (this == ZERO) {
/* 1023 */       return "P0D";
/*      */     }
/* 1025 */     StringBuilder stringBuilder = new StringBuilder();
/* 1026 */     stringBuilder.append('P');
/* 1027 */     if (this.years != 0) {
/* 1028 */       stringBuilder.append(this.years).append('Y');
/*      */     }
/* 1030 */     if (this.months != 0) {
/* 1031 */       stringBuilder.append(this.months).append('M');
/*      */     }
/* 1033 */     if (this.days != 0) {
/* 1034 */       stringBuilder.append(this.days).append('D');
/*      */     }
/* 1036 */     return stringBuilder.toString();
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
/*      */   private Object writeReplace() {
/* 1055 */     return new Ser((byte)14, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 1065 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*      */   }
/*      */   
/*      */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 1069 */     paramDataOutput.writeInt(this.years);
/* 1070 */     paramDataOutput.writeInt(this.months);
/* 1071 */     paramDataOutput.writeInt(this.days);
/*      */   }
/*      */   
/*      */   static Period readExternal(DataInput paramDataInput) throws IOException {
/* 1075 */     int i = paramDataInput.readInt();
/* 1076 */     int j = paramDataInput.readInt();
/* 1077 */     int k = paramDataInput.readInt();
/* 1078 */     return of(i, j, k);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/Period.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */