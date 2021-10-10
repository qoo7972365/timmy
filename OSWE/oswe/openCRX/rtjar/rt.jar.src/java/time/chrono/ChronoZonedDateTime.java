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
/*     */ import java.time.temporal.UnsupportedTemporalTypeException;
/*     */ import java.time.temporal.ValueRange;
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
/*     */ public interface ChronoZonedDateTime<D extends ChronoLocalDate>
/*     */   extends Temporal, Comparable<ChronoZonedDateTime<?>>
/*     */ {
/*     */   static Comparator<ChronoZonedDateTime<?>> timeLineOrder() {
/* 140 */     return AbstractChronology.INSTANT_ORDER;
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
/*     */   static ChronoZonedDateTime<?> from(TemporalAccessor paramTemporalAccessor) {
/* 166 */     if (paramTemporalAccessor instanceof ChronoZonedDateTime) {
/* 167 */       return (ChronoZonedDateTime)paramTemporalAccessor;
/*     */     }
/* 169 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/* 170 */     Chronology chronology = paramTemporalAccessor.<Chronology>query(TemporalQueries.chronology());
/* 171 */     if (chronology == null) {
/* 172 */       throw new DateTimeException("Unable to obtain ChronoZonedDateTime from TemporalAccessor: " + paramTemporalAccessor.getClass());
/*     */     }
/* 174 */     return chronology.zonedDateTime(paramTemporalAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default ValueRange range(TemporalField paramTemporalField) {
/* 180 */     if (paramTemporalField instanceof ChronoField) {
/* 181 */       if (paramTemporalField == ChronoField.INSTANT_SECONDS || paramTemporalField == ChronoField.OFFSET_SECONDS) {
/* 182 */         return paramTemporalField.range();
/*     */       }
/* 184 */       return toLocalDateTime().range(paramTemporalField);
/*     */     } 
/* 186 */     return paramTemporalField.rangeRefinedBy(this);
/*     */   }
/*     */ 
/*     */   
/*     */   default int get(TemporalField paramTemporalField) {
/* 191 */     if (paramTemporalField instanceof ChronoField) {
/* 192 */       switch ((ChronoField)paramTemporalField) {
/*     */         case INSTANT_SECONDS:
/* 194 */           throw new UnsupportedTemporalTypeException("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
/*     */         case OFFSET_SECONDS:
/* 196 */           return getOffset().getTotalSeconds();
/*     */       } 
/* 198 */       return toLocalDateTime().get(paramTemporalField);
/*     */     } 
/* 200 */     return super.get(paramTemporalField);
/*     */   }
/*     */ 
/*     */   
/*     */   default long getLong(TemporalField paramTemporalField) {
/* 205 */     if (paramTemporalField instanceof ChronoField) {
/* 206 */       switch ((ChronoField)paramTemporalField) { case INSTANT_SECONDS:
/* 207 */           return toEpochSecond();
/* 208 */         case OFFSET_SECONDS: return getOffset().getTotalSeconds(); }
/*     */       
/* 210 */       return toLocalDateTime().getLong(paramTemporalField);
/*     */     } 
/* 212 */     return paramTemporalField.getFrom(this);
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
/*     */   default D toLocalDate() {
/* 224 */     return toLocalDateTime().toLocalDate();
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
/*     */   default LocalTime toLocalTime() {
/* 236 */     return toLocalDateTime().toLocalTime();
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
/*     */   default Chronology getChronology() {
/* 258 */     return toLocalDate().getChronology();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 398 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 399 */       return (paramTemporalUnit != ChronoUnit.FOREVER);
/*     */     }
/* 401 */     return (paramTemporalUnit != null && paramTemporalUnit.isSupportedBy(this));
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
/*     */   default ChronoZonedDateTime<D> with(TemporalAdjuster paramTemporalAdjuster) {
/* 413 */     return ChronoZonedDateTimeImpl.ensureValid(getChronology(), super.with(paramTemporalAdjuster));
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
/*     */   default ChronoZonedDateTime<D> plus(TemporalAmount paramTemporalAmount) {
/* 431 */     return ChronoZonedDateTimeImpl.ensureValid(getChronology(), super.plus(paramTemporalAmount));
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
/*     */   default ChronoZonedDateTime<D> minus(TemporalAmount paramTemporalAmount) {
/* 449 */     return ChronoZonedDateTimeImpl.ensureValid(getChronology(), super.minus(paramTemporalAmount));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ChronoZonedDateTime<D> minus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 459 */     return ChronoZonedDateTimeImpl.ensureValid(getChronology(), super.minus(paramLong, paramTemporalUnit));
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
/* 484 */     if (paramTemporalQuery == TemporalQueries.zone() || paramTemporalQuery == TemporalQueries.zoneId())
/* 485 */       return (R)getZone(); 
/* 486 */     if (paramTemporalQuery == TemporalQueries.offset())
/* 487 */       return (R)getOffset(); 
/* 488 */     if (paramTemporalQuery == TemporalQueries.localTime())
/* 489 */       return (R)toLocalTime(); 
/* 490 */     if (paramTemporalQuery == TemporalQueries.chronology())
/* 491 */       return (R)getChronology(); 
/* 492 */     if (paramTemporalQuery == TemporalQueries.precision()) {
/* 493 */       return (R)ChronoUnit.NANOS;
/*     */     }
/*     */ 
/*     */     
/* 497 */     return paramTemporalQuery.queryFrom(this);
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
/* 515 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/* 516 */     return paramDateTimeFormatter.format(this);
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
/*     */   default Instant toInstant() {
/* 531 */     return Instant.ofEpochSecond(toEpochSecond(), toLocalTime().getNano());
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
/*     */   default long toEpochSecond() {
/* 546 */     long l1 = toLocalDate().toEpochDay();
/* 547 */     long l2 = l1 * 86400L + toLocalTime().toSecondOfDay();
/* 548 */     l2 -= getOffset().getTotalSeconds();
/* 549 */     return l2;
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
/*     */   default int compareTo(ChronoZonedDateTime<?> paramChronoZonedDateTime) {
/* 570 */     int i = Long.compare(toEpochSecond(), paramChronoZonedDateTime.toEpochSecond());
/* 571 */     if (i == 0) {
/* 572 */       i = toLocalTime().getNano() - paramChronoZonedDateTime.toLocalTime().getNano();
/* 573 */       if (i == 0) {
/* 574 */         i = toLocalDateTime().compareTo(paramChronoZonedDateTime.toLocalDateTime());
/* 575 */         if (i == 0) {
/* 576 */           i = getZone().getId().compareTo(paramChronoZonedDateTime.getZone().getId());
/* 577 */           if (i == 0) {
/* 578 */             i = getChronology().compareTo(paramChronoZonedDateTime.getChronology());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 583 */     return i;
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
/*     */   default boolean isBefore(ChronoZonedDateTime<?> paramChronoZonedDateTime) {
/* 600 */     long l1 = toEpochSecond();
/* 601 */     long l2 = paramChronoZonedDateTime.toEpochSecond();
/* 602 */     return (l1 < l2 || (l1 == l2 && 
/* 603 */       toLocalTime().getNano() < paramChronoZonedDateTime.toLocalTime().getNano()));
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
/*     */   default boolean isAfter(ChronoZonedDateTime<?> paramChronoZonedDateTime) {
/* 620 */     long l1 = toEpochSecond();
/* 621 */     long l2 = paramChronoZonedDateTime.toEpochSecond();
/* 622 */     return (l1 > l2 || (l1 == l2 && 
/* 623 */       toLocalTime().getNano() > paramChronoZonedDateTime.toLocalTime().getNano()));
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
/*     */   default boolean isEqual(ChronoZonedDateTime<?> paramChronoZonedDateTime) {
/* 640 */     return (toEpochSecond() == paramChronoZonedDateTime.toEpochSecond() && 
/* 641 */       toLocalTime().getNano() == paramChronoZonedDateTime.toLocalTime().getNano());
/*     */   }
/*     */   
/*     */   ChronoLocalDateTime<D> toLocalDateTime();
/*     */   
/*     */   ZoneOffset getOffset();
/*     */   
/*     */   ZoneId getZone();
/*     */   
/*     */   ChronoZonedDateTime<D> withEarlierOffsetAtOverlap();
/*     */   
/*     */   ChronoZonedDateTime<D> withLaterOffsetAtOverlap();
/*     */   
/*     */   ChronoZonedDateTime<D> withZoneSameLocal(ZoneId paramZoneId);
/*     */   
/*     */   ChronoZonedDateTime<D> withZoneSameInstant(ZoneId paramZoneId);
/*     */   
/*     */   boolean isSupported(TemporalField paramTemporalField);
/*     */   
/*     */   ChronoZonedDateTime<D> with(TemporalField paramTemporalField, long paramLong);
/*     */   
/*     */   ChronoZonedDateTime<D> plus(long paramLong, TemporalUnit paramTemporalUnit);
/*     */   
/*     */   boolean equals(Object paramObject);
/*     */   
/*     */   int hashCode();
/*     */   
/*     */   String toString();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/ChronoZonedDateTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */