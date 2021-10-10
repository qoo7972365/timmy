/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.time.Clock;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDate;
/*     */ import java.time.ZoneId;
/*     */ import java.time.format.ResolverStyle;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalAdjusters;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.UnsupportedTemporalTypeException;
/*     */ import java.time.temporal.ValueRange;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.TimeZone;
/*     */ import sun.util.calendar.CalendarSystem;
/*     */ import sun.util.calendar.LocalGregorianCalendar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JapaneseChronology
/*     */   extends AbstractChronology
/*     */   implements Serializable
/*     */ {
/* 126 */   static final LocalGregorianCalendar JCAL = (LocalGregorianCalendar)CalendarSystem.forName("japanese");
/*     */ 
/*     */   
/* 129 */   static final Locale LOCALE = Locale.forLanguageTag("ja-JP-u-ca-japanese");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   public static final JapaneseChronology INSTANCE = new JapaneseChronology();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 459996390165777884L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 160 */     return "Japanese";
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
/*     */   public String getCalendarType() {
/* 177 */     return "japanese";
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
/*     */ 
/*     */ 
/*     */   
/*     */   public JapaneseDate date(Era paramEra, int paramInt1, int paramInt2, int paramInt3) {
/* 205 */     if (!(paramEra instanceof JapaneseEra)) {
/* 206 */       throw new ClassCastException("Era must be JapaneseEra");
/*     */     }
/* 208 */     return JapaneseDate.of((JapaneseEra)paramEra, paramInt1, paramInt2, paramInt3);
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
/*     */   public JapaneseDate date(int paramInt1, int paramInt2, int paramInt3) {
/* 226 */     return new JapaneseDate(LocalDate.of(paramInt1, paramInt2, paramInt3));
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
/*     */ 
/*     */   
/*     */   public JapaneseDate dateYearDay(Era paramEra, int paramInt1, int paramInt2) {
/* 253 */     return JapaneseDate.ofYearDay((JapaneseEra)paramEra, paramInt1, paramInt2);
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
/*     */   public JapaneseDate dateYearDay(int paramInt1, int paramInt2) {
/* 271 */     return new JapaneseDate(LocalDate.ofYearDay(paramInt1, paramInt2));
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
/*     */   public JapaneseDate dateEpochDay(long paramLong) {
/* 283 */     return new JapaneseDate(LocalDate.ofEpochDay(paramLong));
/*     */   }
/*     */ 
/*     */   
/*     */   public JapaneseDate dateNow() {
/* 288 */     return dateNow(Clock.systemDefaultZone());
/*     */   }
/*     */ 
/*     */   
/*     */   public JapaneseDate dateNow(ZoneId paramZoneId) {
/* 293 */     return dateNow(Clock.system(paramZoneId));
/*     */   }
/*     */ 
/*     */   
/*     */   public JapaneseDate dateNow(Clock paramClock) {
/* 298 */     return date(LocalDate.now(paramClock));
/*     */   }
/*     */ 
/*     */   
/*     */   public JapaneseDate date(TemporalAccessor paramTemporalAccessor) {
/* 303 */     if (paramTemporalAccessor instanceof JapaneseDate) {
/* 304 */       return (JapaneseDate)paramTemporalAccessor;
/*     */     }
/* 306 */     return new JapaneseDate(LocalDate.from(paramTemporalAccessor));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoLocalDateTime<JapaneseDate> localDateTime(TemporalAccessor paramTemporalAccessor) {
/* 312 */     return (ChronoLocalDateTime)super.localDateTime(paramTemporalAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoZonedDateTime<JapaneseDate> zonedDateTime(TemporalAccessor paramTemporalAccessor) {
/* 318 */     return (ChronoZonedDateTime)super.zonedDateTime(paramTemporalAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoZonedDateTime<JapaneseDate> zonedDateTime(Instant paramInstant, ZoneId paramZoneId) {
/* 324 */     return (ChronoZonedDateTime)super.zonedDateTime(paramInstant, paramZoneId);
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
/*     */   public boolean isLeapYear(long paramLong) {
/* 340 */     return IsoChronology.INSTANCE.isLeapYear(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public int prolepticYear(Era paramEra, int paramInt) {
/* 345 */     if (!(paramEra instanceof JapaneseEra)) {
/* 346 */       throw new ClassCastException("Era must be JapaneseEra");
/*     */     }
/*     */     
/* 349 */     JapaneseEra japaneseEra = (JapaneseEra)paramEra;
/* 350 */     int i = japaneseEra.getPrivateEra().getSinceDate().getYear() + paramInt - 1;
/* 351 */     if (paramInt == 1) {
/* 352 */       return i;
/*     */     }
/* 354 */     if (i >= -999999999 && i <= 999999999) {
/* 355 */       LocalGregorianCalendar.Date date = JCAL.newCalendarDate((TimeZone)null);
/* 356 */       date.setEra(japaneseEra.getPrivateEra()).setDate(paramInt, 1, 1);
/* 357 */       if (JCAL.validate(date)) {
/* 358 */         return i;
/*     */       }
/*     */     } 
/* 361 */     throw new DateTimeException("Invalid yearOfEra value");
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
/*     */   public JapaneseEra eraOf(int paramInt) {
/* 377 */     return JapaneseEra.of(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Era> eras() {
/* 382 */     return Arrays.asList((Era[])JapaneseEra.values());
/*     */   }
/*     */ 
/*     */   
/*     */   JapaneseEra getCurrentEra() {
/* 387 */     JapaneseEra[] arrayOfJapaneseEra = JapaneseEra.values();
/* 388 */     return arrayOfJapaneseEra[arrayOfJapaneseEra.length - 1];
/*     */   }
/*     */   
/*     */   public ValueRange range(ChronoField paramChronoField) {
/*     */     Calendar calendar;
/*     */     int i;
/* 394 */     switch (paramChronoField) {
/*     */       case ALIGNED_DAY_OF_WEEK_IN_MONTH:
/*     */       case ALIGNED_DAY_OF_WEEK_IN_YEAR:
/*     */       case ALIGNED_WEEK_OF_MONTH:
/*     */       case ALIGNED_WEEK_OF_YEAR:
/* 399 */         throw new UnsupportedTemporalTypeException("Unsupported field: " + paramChronoField);
/*     */       case YEAR_OF_ERA:
/* 401 */         calendar = Calendar.getInstance(LOCALE);
/* 402 */         i = getCurrentEra().getPrivateEra().getSinceDate().getYear();
/* 403 */         return ValueRange.of(1L, calendar.getGreatestMinimum(1), (calendar
/* 404 */             .getLeastMaximum(1) + 1), (999999999 - i));
/*     */ 
/*     */       
/*     */       case DAY_OF_YEAR:
/* 408 */         calendar = Calendar.getInstance(LOCALE);
/* 409 */         i = 6;
/* 410 */         return ValueRange.of(calendar.getMinimum(i), calendar.getGreatestMinimum(i), calendar
/* 411 */             .getLeastMaximum(i), calendar.getMaximum(i));
/*     */       
/*     */       case YEAR:
/* 414 */         return ValueRange.of(JapaneseDate.MEIJI_6_ISODATE.getYear(), 999999999L);
/*     */       case ERA:
/* 416 */         return ValueRange.of(JapaneseEra.MEIJI.getValue(), getCurrentEra().getValue());
/*     */     } 
/* 418 */     return paramChronoField.range();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JapaneseDate resolveDate(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 425 */     return (JapaneseDate)super.resolveDate(paramMap, paramResolverStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   ChronoLocalDate resolveYearOfEra(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 431 */     Long long_1 = paramMap.get(ChronoField.ERA);
/* 432 */     JapaneseEra japaneseEra = null;
/* 433 */     if (long_1 != null) {
/* 434 */       japaneseEra = eraOf(range(ChronoField.ERA).checkValidIntValue(long_1.longValue(), ChronoField.ERA));
/*     */     }
/* 436 */     Long long_2 = paramMap.get(ChronoField.YEAR_OF_ERA);
/* 437 */     int i = 0;
/* 438 */     if (long_2 != null) {
/* 439 */       i = range(ChronoField.YEAR_OF_ERA).checkValidIntValue(long_2.longValue(), ChronoField.YEAR_OF_ERA);
/*     */     }
/*     */     
/* 442 */     if (japaneseEra == null && long_2 != null && !paramMap.containsKey(ChronoField.YEAR) && paramResolverStyle != ResolverStyle.STRICT) {
/* 443 */       japaneseEra = JapaneseEra.values()[(JapaneseEra.values()).length - 1];
/*     */     }
/*     */     
/* 446 */     if (long_2 != null && japaneseEra != null) {
/* 447 */       if (paramMap.containsKey(ChronoField.MONTH_OF_YEAR) && 
/* 448 */         paramMap.containsKey(ChronoField.DAY_OF_MONTH)) {
/* 449 */         return resolveYMD(japaneseEra, i, paramMap, paramResolverStyle);
/*     */       }
/*     */       
/* 452 */       if (paramMap.containsKey(ChronoField.DAY_OF_YEAR)) {
/* 453 */         return resolveYD(japaneseEra, i, paramMap, paramResolverStyle);
/*     */       }
/*     */     } 
/* 456 */     return null;
/*     */   }
/*     */   
/*     */   private int prolepticYearLenient(JapaneseEra paramJapaneseEra, int paramInt) {
/* 460 */     return paramJapaneseEra.getPrivateEra().getSinceDate().getYear() + paramInt - 1;
/*     */   }
/*     */   
/*     */   private ChronoLocalDate resolveYMD(JapaneseEra paramJapaneseEra, int paramInt, Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 464 */     paramMap.remove(ChronoField.ERA);
/* 465 */     paramMap.remove(ChronoField.YEAR_OF_ERA);
/* 466 */     if (paramResolverStyle == ResolverStyle.LENIENT) {
/* 467 */       int k = prolepticYearLenient(paramJapaneseEra, paramInt);
/* 468 */       long l1 = Math.subtractExact(((Long)paramMap.remove(ChronoField.MONTH_OF_YEAR)).longValue(), 1L);
/* 469 */       long l2 = Math.subtractExact(((Long)paramMap.remove(ChronoField.DAY_OF_MONTH)).longValue(), 1L);
/* 470 */       return date(k, 1, 1).plus(l1, ChronoUnit.MONTHS).plus(l2, ChronoUnit.DAYS);
/*     */     } 
/* 472 */     int i = range(ChronoField.MONTH_OF_YEAR).checkValidIntValue(((Long)paramMap.remove(ChronoField.MONTH_OF_YEAR)).longValue(), ChronoField.MONTH_OF_YEAR);
/* 473 */     int j = range(ChronoField.DAY_OF_MONTH).checkValidIntValue(((Long)paramMap.remove(ChronoField.DAY_OF_MONTH)).longValue(), ChronoField.DAY_OF_MONTH);
/* 474 */     if (paramResolverStyle == ResolverStyle.SMART) {
/* 475 */       JapaneseDate japaneseDate; if (paramInt < 1) {
/* 476 */         throw new DateTimeException("Invalid YearOfEra: " + paramInt);
/*     */       }
/* 478 */       int k = prolepticYearLenient(paramJapaneseEra, paramInt);
/*     */       
/*     */       try {
/* 481 */         japaneseDate = date(k, i, j);
/* 482 */       } catch (DateTimeException dateTimeException) {
/* 483 */         japaneseDate = date(k, i, 1).with(TemporalAdjusters.lastDayOfMonth());
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 488 */       if (japaneseDate.getEra() != paramJapaneseEra && japaneseDate.get(ChronoField.YEAR_OF_ERA) > 1 && paramInt > 1) {
/* 489 */         throw new DateTimeException("Invalid YearOfEra for Era: " + paramJapaneseEra + " " + paramInt);
/*     */       }
/* 491 */       return japaneseDate;
/*     */     } 
/* 493 */     return date(paramJapaneseEra, paramInt, i, j);
/*     */   }
/*     */   
/*     */   private ChronoLocalDate resolveYD(JapaneseEra paramJapaneseEra, int paramInt, Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 497 */     paramMap.remove(ChronoField.ERA);
/* 498 */     paramMap.remove(ChronoField.YEAR_OF_ERA);
/* 499 */     if (paramResolverStyle == ResolverStyle.LENIENT) {
/* 500 */       int j = prolepticYearLenient(paramJapaneseEra, paramInt);
/* 501 */       long l = Math.subtractExact(((Long)paramMap.remove(ChronoField.DAY_OF_YEAR)).longValue(), 1L);
/* 502 */       return dateYearDay(j, 1).plus(l, ChronoUnit.DAYS);
/*     */     } 
/* 504 */     int i = range(ChronoField.DAY_OF_YEAR).checkValidIntValue(((Long)paramMap.remove(ChronoField.DAY_OF_YEAR)).longValue(), ChronoField.DAY_OF_YEAR);
/* 505 */     return dateYearDay(paramJapaneseEra, paramInt, i);
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
/*     */   Object writeReplace() {
/* 522 */     return super.writeReplace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 532 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/JapaneseChronology.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */