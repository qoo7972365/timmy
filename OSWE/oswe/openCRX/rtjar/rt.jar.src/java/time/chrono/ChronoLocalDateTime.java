/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.time.DateTimeException;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalTime;
/*     */ import java.time.ZoneId;
/*     */ import java.time.ZoneOffset;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.time.temporal.Temporal;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalAdjuster;
/*     */ import java.time.temporal.TemporalAmount;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.TemporalQueries;
/*     */ import java.time.temporal.TemporalQuery;
/*     */ import java.time.temporal.TemporalUnit;
/*     */ import java.util.Comparator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ChronoLocalDateTime<D extends ChronoLocalDate>
/*     */   extends Temporal, TemporalAdjuster, Comparable<ChronoLocalDateTime<?>>
/*     */ {
/*     */   static Comparator<ChronoLocalDateTime<?>> timeLineOrder() {
/* 139 */     return AbstractChronology.DATE_TIME_ORDER;
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
/*     */   static ChronoLocalDateTime<?> from(TemporalAccessor paramTemporalAccessor) {
/* 165 */     if (paramTemporalAccessor instanceof ChronoLocalDateTime) {
/* 166 */       return (ChronoLocalDateTime)paramTemporalAccessor;
/*     */     }
/* 168 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/* 169 */     Chronology chronology = paramTemporalAccessor.<Chronology>query(TemporalQueries.chronology());
/* 170 */     if (chronology == null) {
/* 171 */       throw new DateTimeException("Unable to obtain ChronoLocalDateTime from TemporalAccessor: " + paramTemporalAccessor.getClass());
/*     */     }
/* 173 */     return chronology.localDateTime(paramTemporalAccessor);
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
/*     */   default Chronology getChronology() {
/* 186 */     return toLocalDate().getChronology();
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
/*     */   default boolean isSupported(TemporalUnit paramTemporalUnit) {
/* 251 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 252 */       return (paramTemporalUnit != ChronoUnit.FOREVER);
/*     */     }
/* 254 */     return (paramTemporalUnit != null && paramTemporalUnit.isSupportedBy(this));
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
/*     */   default ChronoLocalDateTime<D> with(TemporalAdjuster paramTemporalAdjuster) {
/* 266 */     return ChronoLocalDateTimeImpl.ensureValid(getChronology(), super.with(paramTemporalAdjuster));
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
/*     */   default ChronoLocalDateTime<D> plus(TemporalAmount paramTemporalAmount) {
/* 284 */     return ChronoLocalDateTimeImpl.ensureValid(getChronology(), super.plus(paramTemporalAmount));
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
/*     */   default ChronoLocalDateTime<D> minus(TemporalAmount paramTemporalAmount) {
/* 302 */     return ChronoLocalDateTimeImpl.ensureValid(getChronology(), super.minus(paramTemporalAmount));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ChronoLocalDateTime<D> minus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 312 */     return ChronoLocalDateTimeImpl.ensureValid(getChronology(), super.minus(paramLong, paramTemporalUnit));
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
/*     */   default <R> R query(TemporalQuery<R> paramTemporalQuery) {
/* 337 */     if (paramTemporalQuery == TemporalQueries.zoneId() || paramTemporalQuery == TemporalQueries.zone() || paramTemporalQuery == TemporalQueries.offset())
/* 338 */       return null; 
/* 339 */     if (paramTemporalQuery == TemporalQueries.localTime())
/* 340 */       return (R)toLocalTime(); 
/* 341 */     if (paramTemporalQuery == TemporalQueries.chronology())
/* 342 */       return (R)getChronology(); 
/* 343 */     if (paramTemporalQuery == TemporalQueries.precision()) {
/* 344 */       return (R)ChronoUnit.NANOS;
/*     */     }
/*     */ 
/*     */     
/* 348 */     return paramTemporalQuery.queryFrom(this);
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
/*     */   default Temporal adjustInto(Temporal paramTemporal) {
/* 378 */     return paramTemporal
/* 379 */       .with(ChronoField.EPOCH_DAY, toLocalDate().toEpochDay())
/* 380 */       .with(ChronoField.NANO_OF_DAY, toLocalTime().toNanoOfDay());
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
/*     */   default String format(DateTimeFormatter paramDateTimeFormatter) {
/* 398 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/* 399 */     return paramDateTimeFormatter.format(this);
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
/*     */   default Instant toInstant(ZoneOffset paramZoneOffset) {
/* 446 */     return Instant.ofEpochSecond(toEpochSecond(paramZoneOffset), toLocalTime().getNano());
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
/*     */   default long toEpochSecond(ZoneOffset paramZoneOffset) {
/* 464 */     Objects.requireNonNull(paramZoneOffset, "offset");
/* 465 */     long l1 = toLocalDate().toEpochDay();
/* 466 */     long l2 = l1 * 86400L + toLocalTime().toSecondOfDay();
/* 467 */     l2 -= paramZoneOffset.getTotalSeconds();
/* 468 */     return l2;
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
/*     */   default int compareTo(ChronoLocalDateTime<?> paramChronoLocalDateTime) {
/* 500 */     int i = toLocalDate().compareTo((ChronoLocalDate)paramChronoLocalDateTime.toLocalDate());
/* 501 */     if (i == 0) {
/* 502 */       i = toLocalTime().compareTo(paramChronoLocalDateTime.toLocalTime());
/* 503 */       if (i == 0) {
/* 504 */         i = getChronology().compareTo(paramChronoLocalDateTime.getChronology());
/*     */       }
/*     */     } 
/* 507 */     return i;
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
/*     */   default boolean isAfter(ChronoLocalDateTime<?> paramChronoLocalDateTime) {
/* 525 */     long l1 = toLocalDate().toEpochDay();
/* 526 */     long l2 = paramChronoLocalDateTime.toLocalDate().toEpochDay();
/* 527 */     return (l1 > l2 || (l1 == l2 && 
/* 528 */       toLocalTime().toNanoOfDay() > paramChronoLocalDateTime.toLocalTime().toNanoOfDay()));
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
/*     */   default boolean isBefore(ChronoLocalDateTime<?> paramChronoLocalDateTime) {
/* 546 */     long l1 = toLocalDate().toEpochDay();
/* 547 */     long l2 = paramChronoLocalDateTime.toLocalDate().toEpochDay();
/* 548 */     return (l1 < l2 || (l1 == l2 && 
/* 549 */       toLocalTime().toNanoOfDay() < paramChronoLocalDateTime.toLocalTime().toNanoOfDay()));
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
/*     */   default boolean isEqual(ChronoLocalDateTime<?> paramChronoLocalDateTime) {
/* 568 */     return (toLocalTime().toNanoOfDay() == paramChronoLocalDateTime.toLocalTime().toNanoOfDay() && 
/* 569 */       toLocalDate().toEpochDay() == paramChronoLocalDateTime.toLocalDate().toEpochDay());
/*     */   }
/*     */   
/*     */   D toLocalDate();
/*     */   
/*     */   LocalTime toLocalTime();
/*     */   
/*     */   boolean isSupported(TemporalField paramTemporalField);
/*     */   
/*     */   ChronoLocalDateTime<D> with(TemporalField paramTemporalField, long paramLong);
/*     */   
/*     */   ChronoLocalDateTime<D> plus(long paramLong, TemporalUnit paramTemporalUnit);
/*     */   
/*     */   ChronoZonedDateTime<D> atZone(ZoneId paramZoneId);
/*     */   
/*     */   boolean equals(Object paramObject);
/*     */   
/*     */   int hashCode();
/*     */   
/*     */   String toString();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/ChronoLocalDateTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */