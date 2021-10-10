/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.time.Clock;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDate;
/*     */ import java.time.LocalTime;
/*     */ import java.time.ZoneId;
/*     */ import java.time.format.DateTimeFormatterBuilder;
/*     */ import java.time.format.ResolverStyle;
/*     */ import java.time.format.TextStyle;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.TemporalQueries;
/*     */ import java.time.temporal.TemporalQuery;
/*     */ import java.time.temporal.UnsupportedTemporalTypeException;
/*     */ import java.time.temporal.ValueRange;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Chronology
/*     */   extends Comparable<Chronology>
/*     */ {
/*     */   static Chronology from(TemporalAccessor paramTemporalAccessor) {
/* 178 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/* 179 */     Chronology chronology = paramTemporalAccessor.<Chronology>query(TemporalQueries.chronology());
/* 180 */     return (chronology != null) ? chronology : IsoChronology.INSTANCE;
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
/*     */   static Chronology ofLocale(Locale paramLocale) {
/* 225 */     return AbstractChronology.ofLocale(paramLocale);
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
/*     */   static Chronology of(String paramString) {
/* 250 */     return AbstractChronology.of(paramString);
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
/*     */   static Set<Chronology> getAvailableChronologies() {
/* 264 */     return AbstractChronology.getAvailableChronologies();
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
/*     */   default ChronoLocalDate date(Era paramEra, int paramInt1, int paramInt2, int paramInt3) {
/* 312 */     return date(prolepticYear(paramEra, paramInt1), paramInt2, paramInt3);
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
/*     */   default ChronoLocalDate dateYearDay(Era paramEra, int paramInt1, int paramInt2) {
/* 343 */     return dateYearDay(prolepticYear(paramEra, paramInt1), paramInt2);
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
/*     */   default ChronoLocalDate dateNow() {
/* 386 */     return dateNow(Clock.systemDefaultZone());
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
/*     */   default ChronoLocalDate dateNow(ZoneId paramZoneId) {
/* 406 */     return dateNow(Clock.system(paramZoneId));
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
/*     */   default ChronoLocalDate dateNow(Clock paramClock) {
/* 424 */     Objects.requireNonNull(paramClock, "clock");
/* 425 */     return date(LocalDate.now(paramClock));
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
/*     */   default ChronoLocalDateTime<? extends ChronoLocalDate> localDateTime(TemporalAccessor paramTemporalAccessor) {
/*     */     try {
/* 472 */       return (ChronoLocalDateTime)date(paramTemporalAccessor).atTime(LocalTime.from(paramTemporalAccessor));
/* 473 */     } catch (DateTimeException dateTimeException) {
/* 474 */       throw new DateTimeException("Unable to obtain ChronoLocalDateTime from TemporalAccessor: " + paramTemporalAccessor.getClass(), dateTimeException);
/*     */     } 
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
/*     */   default ChronoZonedDateTime<? extends ChronoLocalDate> zonedDateTime(TemporalAccessor paramTemporalAccessor) {
/*     */     try {
/* 504 */       ZoneId zoneId = ZoneId.from(paramTemporalAccessor);
/*     */       try {
/* 506 */         Instant instant = Instant.from(paramTemporalAccessor);
/* 507 */         return zonedDateTime(instant, zoneId);
/*     */       }
/* 509 */       catch (DateTimeException dateTimeException) {
/* 510 */         ChronoLocalDateTimeImpl<ChronoLocalDate> chronoLocalDateTimeImpl = ChronoLocalDateTimeImpl.ensureValid(this, localDateTime(paramTemporalAccessor));
/* 511 */         return ChronoZonedDateTimeImpl.ofBest(chronoLocalDateTimeImpl, zoneId, null);
/*     */       } 
/* 513 */     } catch (DateTimeException dateTimeException) {
/* 514 */       throw new DateTimeException("Unable to obtain ChronoZonedDateTime from TemporalAccessor: " + paramTemporalAccessor.getClass(), dateTimeException);
/*     */     } 
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
/*     */   default ChronoZonedDateTime<? extends ChronoLocalDate> zonedDateTime(Instant paramInstant, ZoneId paramZoneId) {
/* 529 */     return (ChronoZonedDateTime)ChronoZonedDateTimeImpl.ofInstant(this, paramInstant, paramZoneId);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default String getDisplayName(TextStyle paramTextStyle, Locale paramLocale) {
/* 634 */     TemporalAccessor temporalAccessor = new TemporalAccessor()
/*     */       {
/*     */         public boolean isSupported(TemporalField param1TemporalField) {
/* 637 */           return false;
/*     */         }
/*     */         
/*     */         public long getLong(TemporalField param1TemporalField) {
/* 641 */           throw new UnsupportedTemporalTypeException("Unsupported field: " + param1TemporalField);
/*     */         }
/*     */ 
/*     */         
/*     */         public <R> R query(TemporalQuery<R> param1TemporalQuery) {
/* 646 */           if (param1TemporalQuery == TemporalQueries.chronology()) {
/* 647 */             return (R)Chronology.this;
/*     */           }
/* 649 */           return super.query(param1TemporalQuery);
/*     */         }
/*     */       };
/* 652 */     return (new DateTimeFormatterBuilder()).appendChronologyText(paramTextStyle).toFormatter(paramLocale).format(temporalAccessor);
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
/*     */   default ChronoPeriod period(int paramInt1, int paramInt2, int paramInt3) {
/* 705 */     return new ChronoPeriodImpl(this, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   String getId();
/*     */   
/*     */   String getCalendarType();
/*     */   
/*     */   ChronoLocalDate date(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   ChronoLocalDate dateYearDay(int paramInt1, int paramInt2);
/*     */   
/*     */   ChronoLocalDate dateEpochDay(long paramLong);
/*     */   
/*     */   ChronoLocalDate date(TemporalAccessor paramTemporalAccessor);
/*     */   
/*     */   boolean isLeapYear(long paramLong);
/*     */   
/*     */   int prolepticYear(Era paramEra, int paramInt);
/*     */   
/*     */   Era eraOf(int paramInt);
/*     */   
/*     */   List<Era> eras();
/*     */   
/*     */   ValueRange range(ChronoField paramChronoField);
/*     */   
/*     */   ChronoLocalDate resolveDate(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle);
/*     */   
/*     */   int compareTo(Chronology paramChronology);
/*     */   
/*     */   boolean equals(Object paramObject);
/*     */   
/*     */   int hashCode();
/*     */   
/*     */   String toString();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/Chronology.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */