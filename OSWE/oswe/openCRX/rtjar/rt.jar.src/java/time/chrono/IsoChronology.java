/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.time.Clock;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDate;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.Month;
/*     */ import java.time.Period;
/*     */ import java.time.Year;
/*     */ import java.time.ZoneId;
/*     */ import java.time.ZonedDateTime;
/*     */ import java.time.format.ResolverStyle;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.ValueRange;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IsoChronology
/*     */   extends AbstractChronology
/*     */   implements Serializable
/*     */ {
/* 128 */   public static final IsoChronology INSTANCE = new IsoChronology();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -1440403870442975015L;
/*     */ 
/*     */ 
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
/* 153 */     return "ISO";
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
/* 170 */     return "iso8601";
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
/*     */   public LocalDate date(Era paramEra, int paramInt1, int paramInt2, int paramInt3) {
/* 188 */     return date(prolepticYear(paramEra, paramInt1), paramInt2, paramInt3);
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
/*     */   public LocalDate date(int paramInt1, int paramInt2, int paramInt3) {
/* 205 */     return LocalDate.of(paramInt1, paramInt2, paramInt3);
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
/*     */   public LocalDate dateYearDay(Era paramEra, int paramInt1, int paramInt2) {
/* 219 */     return dateYearDay(prolepticYear(paramEra, paramInt1), paramInt2);
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
/*     */   public LocalDate dateYearDay(int paramInt1, int paramInt2) {
/* 234 */     return LocalDate.ofYearDay(paramInt1, paramInt2);
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
/*     */   public LocalDate dateEpochDay(long paramLong) {
/* 248 */     return LocalDate.ofEpochDay(paramLong);
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
/*     */   public LocalDate date(TemporalAccessor paramTemporalAccessor) {
/* 263 */     return LocalDate.from(paramTemporalAccessor);
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
/*     */   public LocalDateTime localDateTime(TemporalAccessor paramTemporalAccessor) {
/* 277 */     return LocalDateTime.from(paramTemporalAccessor);
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
/*     */   public ZonedDateTime zonedDateTime(TemporalAccessor paramTemporalAccessor) {
/* 291 */     return ZonedDateTime.from(paramTemporalAccessor);
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
/*     */   public ZonedDateTime zonedDateTime(Instant paramInstant, ZoneId paramZoneId) {
/* 306 */     return ZonedDateTime.ofInstant(paramInstant, paramZoneId);
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
/*     */   public LocalDate dateNow() {
/* 324 */     return dateNow(Clock.systemDefaultZone());
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
/*     */   public LocalDate dateNow(ZoneId paramZoneId) {
/* 341 */     return dateNow(Clock.system(paramZoneId));
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
/*     */   public LocalDate dateNow(Clock paramClock) {
/* 357 */     Objects.requireNonNull(paramClock, "clock");
/* 358 */     return date(LocalDate.now(paramClock));
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
/*     */   public boolean isLeapYear(long paramLong) {
/* 383 */     return ((paramLong & 0x3L) == 0L && (paramLong % 100L != 0L || paramLong % 400L == 0L));
/*     */   }
/*     */ 
/*     */   
/*     */   public int prolepticYear(Era paramEra, int paramInt) {
/* 388 */     if (!(paramEra instanceof IsoEra)) {
/* 389 */       throw new ClassCastException("Era must be IsoEra");
/*     */     }
/* 391 */     return (paramEra == IsoEra.CE) ? paramInt : (1 - paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public IsoEra eraOf(int paramInt) {
/* 396 */     return IsoEra.of(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Era> eras() {
/* 401 */     return Arrays.asList((Era[])IsoEra.values());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalDate resolveDate(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 492 */     return (LocalDate)super.resolveDate(paramMap, paramResolverStyle);
/*     */   }
/*     */ 
/*     */   
/*     */   void resolveProlepticMonth(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 497 */     Long long_ = paramMap.remove(ChronoField.PROLEPTIC_MONTH);
/* 498 */     if (long_ != null) {
/* 499 */       if (paramResolverStyle != ResolverStyle.LENIENT) {
/* 500 */         ChronoField.PROLEPTIC_MONTH.checkValidValue(long_.longValue());
/*     */       }
/* 502 */       addFieldValue(paramMap, ChronoField.MONTH_OF_YEAR, Math.floorMod(long_.longValue(), 12L) + 1L);
/* 503 */       addFieldValue(paramMap, ChronoField.YEAR, Math.floorDiv(long_.longValue(), 12L));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   LocalDate resolveYearOfEra(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 509 */     Long long_ = paramMap.remove(ChronoField.YEAR_OF_ERA);
/* 510 */     if (long_ != null) {
/* 511 */       if (paramResolverStyle != ResolverStyle.LENIENT) {
/* 512 */         ChronoField.YEAR_OF_ERA.checkValidValue(long_.longValue());
/*     */       }
/* 514 */       Long long_1 = paramMap.remove(ChronoField.ERA);
/* 515 */       if (long_1 == null) {
/* 516 */         Long long_2 = paramMap.get(ChronoField.YEAR);
/* 517 */         if (paramResolverStyle == ResolverStyle.STRICT) {
/*     */           
/* 519 */           if (long_2 != null) {
/* 520 */             addFieldValue(paramMap, ChronoField.YEAR, (long_2.longValue() > 0L) ? long_.longValue() : Math.subtractExact(1L, long_.longValue()));
/*     */           } else {
/*     */             
/* 523 */             paramMap.put(ChronoField.YEAR_OF_ERA, long_);
/*     */           } 
/*     */         } else {
/*     */           
/* 527 */           addFieldValue(paramMap, ChronoField.YEAR, (long_2 == null || long_2.longValue() > 0L) ? long_.longValue() : Math.subtractExact(1L, long_.longValue()));
/*     */         } 
/* 529 */       } else if (long_1.longValue() == 1L) {
/* 530 */         addFieldValue(paramMap, ChronoField.YEAR, long_.longValue());
/* 531 */       } else if (long_1.longValue() == 0L) {
/* 532 */         addFieldValue(paramMap, ChronoField.YEAR, Math.subtractExact(1L, long_.longValue()));
/*     */       } else {
/* 534 */         throw new DateTimeException("Invalid value for era: " + long_1);
/*     */       } 
/* 536 */     } else if (paramMap.containsKey(ChronoField.ERA)) {
/* 537 */       ChronoField.ERA.checkValidValue(((Long)paramMap.get(ChronoField.ERA)).longValue());
/*     */     } 
/* 539 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   LocalDate resolveYMD(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 544 */     int i = ChronoField.YEAR.checkValidIntValue(((Long)paramMap.remove(ChronoField.YEAR)).longValue());
/* 545 */     if (paramResolverStyle == ResolverStyle.LENIENT) {
/* 546 */       long l1 = Math.subtractExact(((Long)paramMap.remove(ChronoField.MONTH_OF_YEAR)).longValue(), 1L);
/* 547 */       long l2 = Math.subtractExact(((Long)paramMap.remove(ChronoField.DAY_OF_MONTH)).longValue(), 1L);
/* 548 */       return LocalDate.of(i, 1, 1).plusMonths(l1).plusDays(l2);
/*     */     } 
/* 550 */     int j = ChronoField.MONTH_OF_YEAR.checkValidIntValue(((Long)paramMap.remove(ChronoField.MONTH_OF_YEAR)).longValue());
/* 551 */     int k = ChronoField.DAY_OF_MONTH.checkValidIntValue(((Long)paramMap.remove(ChronoField.DAY_OF_MONTH)).longValue());
/* 552 */     if (paramResolverStyle == ResolverStyle.SMART) {
/* 553 */       if (j == 4 || j == 6 || j == 9 || j == 11) {
/* 554 */         k = Math.min(k, 30);
/* 555 */       } else if (j == 2) {
/* 556 */         k = Math.min(k, Month.FEBRUARY.length(Year.isLeap(i)));
/*     */       } 
/*     */     }
/*     */     
/* 560 */     return LocalDate.of(i, j, k);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueRange range(ChronoField paramChronoField) {
/* 566 */     return paramChronoField.range();
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
/*     */   public Period period(int paramInt1, int paramInt2, int paramInt3) {
/* 584 */     return Period.of(paramInt1, paramInt2, paramInt3);
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
/* 601 */     return super.writeReplace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 611 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/IsoChronology.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */