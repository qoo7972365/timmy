/*      */ package java.time;
/*      */ 
/*      */ import java.io.DataOutput;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInput;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.time.chrono.ChronoLocalDate;
/*      */ import java.time.chrono.ChronoLocalDateTime;
/*      */ import java.time.chrono.ChronoZonedDateTime;
/*      */ import java.time.format.DateTimeFormatter;
/*      */ import java.time.temporal.ChronoField;
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
/*      */ import java.util.List;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ZonedDateTime
/*      */   implements Temporal, ChronoZonedDateTime<LocalDate>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -6260982410461394882L;
/*      */   private final LocalDateTime dateTime;
/*      */   private final ZoneOffset offset;
/*      */   private final ZoneId zone;
/*      */   
/*      */   public static ZonedDateTime now() {
/*  199 */     return now(Clock.systemDefaultZone());
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
/*      */   public static ZonedDateTime now(ZoneId paramZoneId) {
/*  216 */     return now(Clock.system(paramZoneId));
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
/*      */   public static ZonedDateTime now(Clock paramClock) {
/*  232 */     Objects.requireNonNull(paramClock, "clock");
/*  233 */     Instant instant = paramClock.instant();
/*  234 */     return ofInstant(instant, paramClock.getZone());
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
/*      */   public static ZonedDateTime of(LocalDate paramLocalDate, LocalTime paramLocalTime, ZoneId paramZoneId) {
/*  265 */     return of(LocalDateTime.of(paramLocalDate, paramLocalTime), paramZoneId);
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
/*      */   public static ZonedDateTime of(LocalDateTime paramLocalDateTime, ZoneId paramZoneId) {
/*  293 */     return ofLocal(paramLocalDateTime, paramZoneId, null);
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
/*      */   public static ZonedDateTime of(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, ZoneId paramZoneId) {
/*  339 */     LocalDateTime localDateTime = LocalDateTime.of(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*  340 */     return ofLocal(localDateTime, paramZoneId, null);
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
/*      */   public static ZonedDateTime ofLocal(LocalDateTime paramLocalDateTime, ZoneId paramZoneId, ZoneOffset paramZoneOffset) {
/*      */     ZoneOffset zoneOffset;
/*  367 */     Objects.requireNonNull(paramLocalDateTime, "localDateTime");
/*  368 */     Objects.requireNonNull(paramZoneId, "zone");
/*  369 */     if (paramZoneId instanceof ZoneOffset) {
/*  370 */       return new ZonedDateTime(paramLocalDateTime, (ZoneOffset)paramZoneId, paramZoneId);
/*      */     }
/*  372 */     ZoneRules zoneRules = paramZoneId.getRules();
/*  373 */     List<ZoneOffset> list = zoneRules.getValidOffsets(paramLocalDateTime);
/*      */     
/*  375 */     if (list.size() == 1) {
/*  376 */       zoneOffset = list.get(0);
/*  377 */     } else if (list.size() == 0) {
/*  378 */       ZoneOffsetTransition zoneOffsetTransition = zoneRules.getTransition(paramLocalDateTime);
/*  379 */       paramLocalDateTime = paramLocalDateTime.plusSeconds(zoneOffsetTransition.getDuration().getSeconds());
/*  380 */       zoneOffset = zoneOffsetTransition.getOffsetAfter();
/*      */     }
/*  382 */     else if (paramZoneOffset != null && list.contains(paramZoneOffset)) {
/*  383 */       zoneOffset = paramZoneOffset;
/*      */     } else {
/*  385 */       zoneOffset = Objects.<ZoneOffset>requireNonNull(list.get(0), "offset");
/*      */     } 
/*      */     
/*  388 */     return new ZonedDateTime(paramLocalDateTime, zoneOffset, paramZoneId);
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
/*      */   public static ZonedDateTime ofInstant(Instant paramInstant, ZoneId paramZoneId) {
/*  407 */     Objects.requireNonNull(paramInstant, "instant");
/*  408 */     Objects.requireNonNull(paramZoneId, "zone");
/*  409 */     return create(paramInstant.getEpochSecond(), paramInstant.getNano(), paramZoneId);
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
/*      */   public static ZonedDateTime ofInstant(LocalDateTime paramLocalDateTime, ZoneOffset paramZoneOffset, ZoneId paramZoneId) {
/*  433 */     Objects.requireNonNull(paramLocalDateTime, "localDateTime");
/*  434 */     Objects.requireNonNull(paramZoneOffset, "offset");
/*  435 */     Objects.requireNonNull(paramZoneId, "zone");
/*  436 */     if (paramZoneId.getRules().isValidOffset(paramLocalDateTime, paramZoneOffset)) {
/*  437 */       return new ZonedDateTime(paramLocalDateTime, paramZoneOffset, paramZoneId);
/*      */     }
/*  439 */     return create(paramLocalDateTime.toEpochSecond(paramZoneOffset), paramLocalDateTime.getNano(), paramZoneId);
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
/*      */   private static ZonedDateTime create(long paramLong, int paramInt, ZoneId paramZoneId) {
/*  453 */     ZoneRules zoneRules = paramZoneId.getRules();
/*  454 */     Instant instant = Instant.ofEpochSecond(paramLong, paramInt);
/*  455 */     ZoneOffset zoneOffset = zoneRules.getOffset(instant);
/*  456 */     LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(paramLong, paramInt, zoneOffset);
/*  457 */     return new ZonedDateTime(localDateTime, zoneOffset, paramZoneId);
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
/*      */   public static ZonedDateTime ofStrict(LocalDateTime paramLocalDateTime, ZoneOffset paramZoneOffset, ZoneId paramZoneId) {
/*  475 */     Objects.requireNonNull(paramLocalDateTime, "localDateTime");
/*  476 */     Objects.requireNonNull(paramZoneOffset, "offset");
/*  477 */     Objects.requireNonNull(paramZoneId, "zone");
/*  478 */     ZoneRules zoneRules = paramZoneId.getRules();
/*  479 */     if (!zoneRules.isValidOffset(paramLocalDateTime, paramZoneOffset)) {
/*  480 */       ZoneOffsetTransition zoneOffsetTransition = zoneRules.getTransition(paramLocalDateTime);
/*  481 */       if (zoneOffsetTransition != null && zoneOffsetTransition.isGap())
/*      */       {
/*      */         
/*  484 */         throw new DateTimeException("LocalDateTime '" + paramLocalDateTime + "' does not exist in zone '" + paramZoneId + "' due to a gap in the local time-line, typically caused by daylight savings");
/*      */       }
/*      */ 
/*      */       
/*  488 */       throw new DateTimeException("ZoneOffset '" + paramZoneOffset + "' is not valid for LocalDateTime '" + paramLocalDateTime + "' in zone '" + paramZoneId + "'");
/*      */     } 
/*      */     
/*  491 */     return new ZonedDateTime(paramLocalDateTime, paramZoneOffset, paramZoneId);
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
/*      */   private static ZonedDateTime ofLenient(LocalDateTime paramLocalDateTime, ZoneOffset paramZoneOffset, ZoneId paramZoneId) {
/*  516 */     Objects.requireNonNull(paramLocalDateTime, "localDateTime");
/*  517 */     Objects.requireNonNull(paramZoneOffset, "offset");
/*  518 */     Objects.requireNonNull(paramZoneId, "zone");
/*  519 */     if (paramZoneId instanceof ZoneOffset && !paramZoneOffset.equals(paramZoneId)) {
/*  520 */       throw new IllegalArgumentException("ZoneId must match ZoneOffset");
/*      */     }
/*  522 */     return new ZonedDateTime(paramLocalDateTime, paramZoneOffset, paramZoneId);
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
/*      */   public static ZonedDateTime from(TemporalAccessor paramTemporalAccessor) {
/*  549 */     if (paramTemporalAccessor instanceof ZonedDateTime) {
/*  550 */       return (ZonedDateTime)paramTemporalAccessor;
/*      */     }
/*      */     try {
/*  553 */       ZoneId zoneId = ZoneId.from(paramTemporalAccessor);
/*  554 */       if (paramTemporalAccessor.isSupported(ChronoField.INSTANT_SECONDS)) {
/*  555 */         long l = paramTemporalAccessor.getLong(ChronoField.INSTANT_SECONDS);
/*  556 */         int i = paramTemporalAccessor.get(ChronoField.NANO_OF_SECOND);
/*  557 */         return create(l, i, zoneId);
/*      */       } 
/*  559 */       LocalDate localDate = LocalDate.from(paramTemporalAccessor);
/*  560 */       LocalTime localTime = LocalTime.from(paramTemporalAccessor);
/*  561 */       return of(localDate, localTime, zoneId);
/*      */     }
/*  563 */     catch (DateTimeException dateTimeException) {
/*  564 */       throw new DateTimeException("Unable to obtain ZonedDateTime from TemporalAccessor: " + paramTemporalAccessor + " of type " + paramTemporalAccessor
/*  565 */           .getClass().getName(), dateTimeException);
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
/*      */   public static ZonedDateTime parse(CharSequence paramCharSequence) {
/*  582 */     return parse(paramCharSequence, DateTimeFormatter.ISO_ZONED_DATE_TIME);
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
/*      */   public static ZonedDateTime parse(CharSequence paramCharSequence, DateTimeFormatter paramDateTimeFormatter) {
/*  596 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/*  597 */     return paramDateTimeFormatter.<ZonedDateTime>parse(paramCharSequence, ZonedDateTime::from);
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
/*      */   private ZonedDateTime(LocalDateTime paramLocalDateTime, ZoneOffset paramZoneOffset, ZoneId paramZoneId) {
/*  609 */     this.dateTime = paramLocalDateTime;
/*  610 */     this.offset = paramZoneOffset;
/*  611 */     this.zone = paramZoneId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ZonedDateTime resolveLocal(LocalDateTime paramLocalDateTime) {
/*  621 */     return ofLocal(paramLocalDateTime, this.zone, this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ZonedDateTime resolveInstant(LocalDateTime paramLocalDateTime) {
/*  631 */     return ofInstant(paramLocalDateTime, this.offset, this.zone);
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
/*      */   private ZonedDateTime resolveOffset(ZoneOffset paramZoneOffset) {
/*  643 */     if (!paramZoneOffset.equals(this.offset) && this.zone.getRules().isValidOffset(this.dateTime, paramZoneOffset)) {
/*  644 */       return new ZonedDateTime(this.dateTime, paramZoneOffset, this.zone);
/*      */     }
/*  646 */     return this;
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
/*      */   public boolean isSupported(TemporalField paramTemporalField) {
/*  704 */     return (paramTemporalField instanceof ChronoField || (paramTemporalField != null && paramTemporalField.isSupportedBy(this)));
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
/*      */   public boolean isSupported(TemporalUnit paramTemporalUnit) {
/*  745 */     return super.isSupported(paramTemporalUnit);
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
/*  774 */     if (paramTemporalField instanceof ChronoField) {
/*  775 */       if (paramTemporalField == ChronoField.INSTANT_SECONDS || paramTemporalField == ChronoField.OFFSET_SECONDS) {
/*  776 */         return paramTemporalField.range();
/*      */       }
/*  778 */       return this.dateTime.range(paramTemporalField);
/*      */     } 
/*  780 */     return paramTemporalField.rangeRefinedBy(this);
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
/*      */   public int get(TemporalField paramTemporalField) {
/*  813 */     if (paramTemporalField instanceof ChronoField) {
/*  814 */       switch ((ChronoField)paramTemporalField) {
/*      */         case INSTANT_SECONDS:
/*  816 */           throw new UnsupportedTemporalTypeException("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
/*      */         case OFFSET_SECONDS:
/*  818 */           return getOffset().getTotalSeconds();
/*      */       } 
/*  820 */       return this.dateTime.get(paramTemporalField);
/*      */     } 
/*  822 */     return super.get(paramTemporalField);
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
/*  850 */     if (paramTemporalField instanceof ChronoField) {
/*  851 */       switch ((ChronoField)paramTemporalField) { case INSTANT_SECONDS:
/*  852 */           return toEpochSecond();
/*  853 */         case OFFSET_SECONDS: return getOffset().getTotalSeconds(); }
/*      */       
/*  855 */       return this.dateTime.getLong(paramTemporalField);
/*      */     } 
/*  857 */     return paramTemporalField.getFrom(this);
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
/*      */   public ZoneOffset getOffset() {
/*  870 */     return this.offset;
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
/*      */   public ZonedDateTime withEarlierOffsetAtOverlap() {
/*  891 */     ZoneOffsetTransition zoneOffsetTransition = getZone().getRules().getTransition(this.dateTime);
/*  892 */     if (zoneOffsetTransition != null && zoneOffsetTransition.isOverlap()) {
/*  893 */       ZoneOffset zoneOffset = zoneOffsetTransition.getOffsetBefore();
/*  894 */       if (!zoneOffset.equals(this.offset)) {
/*  895 */         return new ZonedDateTime(this.dateTime, zoneOffset, this.zone);
/*      */       }
/*      */     } 
/*  898 */     return this;
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
/*      */   public ZonedDateTime withLaterOffsetAtOverlap() {
/*  919 */     ZoneOffsetTransition zoneOffsetTransition = getZone().getRules().getTransition(toLocalDateTime());
/*  920 */     if (zoneOffsetTransition != null) {
/*  921 */       ZoneOffset zoneOffset = zoneOffsetTransition.getOffsetAfter();
/*  922 */       if (!zoneOffset.equals(this.offset)) {
/*  923 */         return new ZonedDateTime(this.dateTime, zoneOffset, this.zone);
/*      */       }
/*      */     } 
/*  926 */     return this;
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
/*      */   public ZoneId getZone() {
/*  945 */     return this.zone;
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
/*      */   public ZonedDateTime withZoneSameLocal(ZoneId paramZoneId) {
/*  967 */     Objects.requireNonNull(paramZoneId, "zone");
/*  968 */     return this.zone.equals(paramZoneId) ? this : ofLocal(this.dateTime, paramZoneId, this.offset);
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
/*      */   public ZonedDateTime withZoneSameInstant(ZoneId paramZoneId) {
/*  990 */     Objects.requireNonNull(paramZoneId, "zone");
/*  991 */     return this.zone.equals(paramZoneId) ? this : 
/*  992 */       create(this.dateTime.toEpochSecond(this.offset), this.dateTime.getNano(), paramZoneId);
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
/*      */   public ZonedDateTime withFixedOffsetZone() {
/* 1013 */     return this.zone.equals(this.offset) ? this : new ZonedDateTime(this.dateTime, this.offset, this.offset);
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
/*      */   public LocalDateTime toLocalDateTime() {
/* 1027 */     return this.dateTime;
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
/*      */   public LocalDate toLocalDate() {
/* 1041 */     return this.dateTime.toLocalDate();
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
/* 1055 */     return this.dateTime.getYear();
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
/* 1069 */     return this.dateTime.getMonthValue();
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
/* 1084 */     return this.dateTime.getMonth();
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
/* 1095 */     return this.dateTime.getDayOfMonth();
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
/* 1106 */     return this.dateTime.getDayOfYear();
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
/* 1123 */     return this.dateTime.getDayOfWeek();
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
/*      */   public LocalTime toLocalTime() {
/* 1137 */     return this.dateTime.toLocalTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHour() {
/* 1146 */     return this.dateTime.getHour();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinute() {
/* 1155 */     return this.dateTime.getMinute();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSecond() {
/* 1164 */     return this.dateTime.getSecond();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNano() {
/* 1173 */     return this.dateTime.getNano();
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
/*      */   public ZonedDateTime with(TemporalAdjuster paramTemporalAdjuster) {
/* 1230 */     if (paramTemporalAdjuster instanceof LocalDate)
/* 1231 */       return resolveLocal(LocalDateTime.of((LocalDate)paramTemporalAdjuster, this.dateTime.toLocalTime())); 
/* 1232 */     if (paramTemporalAdjuster instanceof LocalTime)
/* 1233 */       return resolveLocal(LocalDateTime.of(this.dateTime.toLocalDate(), (LocalTime)paramTemporalAdjuster)); 
/* 1234 */     if (paramTemporalAdjuster instanceof LocalDateTime)
/* 1235 */       return resolveLocal((LocalDateTime)paramTemporalAdjuster); 
/* 1236 */     if (paramTemporalAdjuster instanceof OffsetDateTime) {
/* 1237 */       OffsetDateTime offsetDateTime = (OffsetDateTime)paramTemporalAdjuster;
/* 1238 */       return ofLocal(offsetDateTime.toLocalDateTime(), this.zone, offsetDateTime.getOffset());
/* 1239 */     }  if (paramTemporalAdjuster instanceof Instant) {
/* 1240 */       Instant instant = (Instant)paramTemporalAdjuster;
/* 1241 */       return create(instant.getEpochSecond(), instant.getNano(), this.zone);
/* 1242 */     }  if (paramTemporalAdjuster instanceof ZoneOffset) {
/* 1243 */       return resolveOffset((ZoneOffset)paramTemporalAdjuster);
/*      */     }
/* 1245 */     return (ZonedDateTime)paramTemporalAdjuster.adjustInto(this);
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
/*      */   public ZonedDateTime with(TemporalField paramTemporalField, long paramLong) {
/* 1302 */     if (paramTemporalField instanceof ChronoField) {
/* 1303 */       ZoneOffset zoneOffset; ChronoField chronoField = (ChronoField)paramTemporalField;
/* 1304 */       switch (chronoField) {
/*      */         case INSTANT_SECONDS:
/* 1306 */           return create(paramLong, getNano(), this.zone);
/*      */         case OFFSET_SECONDS:
/* 1308 */           zoneOffset = ZoneOffset.ofTotalSeconds(chronoField.checkValidIntValue(paramLong));
/* 1309 */           return resolveOffset(zoneOffset);
/*      */       } 
/* 1311 */       return resolveLocal(this.dateTime.with(paramTemporalField, paramLong));
/*      */     } 
/* 1313 */     return paramTemporalField.<ZonedDateTime>adjustInto(this, paramLong);
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
/*      */   public ZonedDateTime withYear(int paramInt) {
/* 1336 */     return resolveLocal(this.dateTime.withYear(paramInt));
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
/*      */   public ZonedDateTime withMonth(int paramInt) {
/* 1358 */     return resolveLocal(this.dateTime.withMonth(paramInt));
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
/*      */   public ZonedDateTime withDayOfMonth(int paramInt) {
/* 1381 */     return resolveLocal(this.dateTime.withDayOfMonth(paramInt));
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
/*      */   public ZonedDateTime withDayOfYear(int paramInt) {
/* 1404 */     return resolveLocal(this.dateTime.withDayOfYear(paramInt));
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
/*      */   public ZonedDateTime withHour(int paramInt) {
/* 1427 */     return resolveLocal(this.dateTime.withHour(paramInt));
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
/*      */   public ZonedDateTime withMinute(int paramInt) {
/* 1449 */     return resolveLocal(this.dateTime.withMinute(paramInt));
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
/*      */   public ZonedDateTime withSecond(int paramInt) {
/* 1471 */     return resolveLocal(this.dateTime.withSecond(paramInt));
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
/*      */   public ZonedDateTime withNano(int paramInt) {
/* 1493 */     return resolveLocal(this.dateTime.withNano(paramInt));
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
/*      */   public ZonedDateTime truncatedTo(TemporalUnit paramTemporalUnit) {
/* 1527 */     return resolveLocal(this.dateTime.truncatedTo(paramTemporalUnit));
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
/*      */   public ZonedDateTime plus(TemporalAmount paramTemporalAmount) {
/* 1553 */     if (paramTemporalAmount instanceof Period) {
/* 1554 */       Period period = (Period)paramTemporalAmount;
/* 1555 */       return resolveLocal(this.dateTime.plus(period));
/*      */     } 
/* 1557 */     Objects.requireNonNull(paramTemporalAmount, "amountToAdd");
/* 1558 */     return (ZonedDateTime)paramTemporalAmount.addTo(this);
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
/*      */   public ZonedDateTime plus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 1600 */     if (paramTemporalUnit instanceof java.time.temporal.ChronoUnit) {
/* 1601 */       if (paramTemporalUnit.isDateBased()) {
/* 1602 */         return resolveLocal(this.dateTime.plus(paramLong, paramTemporalUnit));
/*      */       }
/* 1604 */       return resolveInstant(this.dateTime.plus(paramLong, paramTemporalUnit));
/*      */     } 
/*      */     
/* 1607 */     return paramTemporalUnit.<ZonedDateTime>addTo(this, paramLong);
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
/*      */   public ZonedDateTime plusYears(long paramLong) {
/* 1630 */     return resolveLocal(this.dateTime.plusYears(paramLong));
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
/*      */   public ZonedDateTime plusMonths(long paramLong) {
/* 1652 */     return resolveLocal(this.dateTime.plusMonths(paramLong));
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
/*      */   public ZonedDateTime plusWeeks(long paramLong) {
/* 1674 */     return resolveLocal(this.dateTime.plusWeeks(paramLong));
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
/*      */   public ZonedDateTime plusDays(long paramLong) {
/* 1696 */     return resolveLocal(this.dateTime.plusDays(paramLong));
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
/*      */   public ZonedDateTime plusHours(long paramLong) {
/* 1725 */     return resolveInstant(this.dateTime.plusHours(paramLong));
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
/*      */   public ZonedDateTime plusMinutes(long paramLong) {
/* 1743 */     return resolveInstant(this.dateTime.plusMinutes(paramLong));
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
/*      */   public ZonedDateTime plusSeconds(long paramLong) {
/* 1761 */     return resolveInstant(this.dateTime.plusSeconds(paramLong));
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
/*      */   public ZonedDateTime plusNanos(long paramLong) {
/* 1779 */     return resolveInstant(this.dateTime.plusNanos(paramLong));
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
/*      */   public ZonedDateTime minus(TemporalAmount paramTemporalAmount) {
/* 1805 */     if (paramTemporalAmount instanceof Period) {
/* 1806 */       Period period = (Period)paramTemporalAmount;
/* 1807 */       return resolveLocal(this.dateTime.minus(period));
/*      */     } 
/* 1809 */     Objects.requireNonNull(paramTemporalAmount, "amountToSubtract");
/* 1810 */     return (ZonedDateTime)paramTemporalAmount.subtractFrom(this);
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
/*      */   public ZonedDateTime minus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 1848 */     return (paramLong == Long.MIN_VALUE) ? plus(Long.MAX_VALUE, paramTemporalUnit).plus(1L, paramTemporalUnit) : plus(-paramLong, paramTemporalUnit);
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
/*      */   public ZonedDateTime minusYears(long paramLong) {
/* 1871 */     return (paramLong == Long.MIN_VALUE) ? plusYears(Long.MAX_VALUE).plusYears(1L) : plusYears(-paramLong);
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
/*      */   public ZonedDateTime minusMonths(long paramLong) {
/* 1893 */     return (paramLong == Long.MIN_VALUE) ? plusMonths(Long.MAX_VALUE).plusMonths(1L) : plusMonths(-paramLong);
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
/*      */   public ZonedDateTime minusWeeks(long paramLong) {
/* 1915 */     return (paramLong == Long.MIN_VALUE) ? plusWeeks(Long.MAX_VALUE).plusWeeks(1L) : plusWeeks(-paramLong);
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
/*      */   public ZonedDateTime minusDays(long paramLong) {
/* 1937 */     return (paramLong == Long.MIN_VALUE) ? plusDays(Long.MAX_VALUE).plusDays(1L) : plusDays(-paramLong);
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
/*      */   public ZonedDateTime minusHours(long paramLong) {
/* 1966 */     return (paramLong == Long.MIN_VALUE) ? plusHours(Long.MAX_VALUE).plusHours(1L) : plusHours(-paramLong);
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
/*      */   public ZonedDateTime minusMinutes(long paramLong) {
/* 1984 */     return (paramLong == Long.MIN_VALUE) ? plusMinutes(Long.MAX_VALUE).plusMinutes(1L) : plusMinutes(-paramLong);
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
/*      */   public ZonedDateTime minusSeconds(long paramLong) {
/* 2002 */     return (paramLong == Long.MIN_VALUE) ? plusSeconds(Long.MAX_VALUE).plusSeconds(1L) : plusSeconds(-paramLong);
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
/*      */   public ZonedDateTime minusNanos(long paramLong) {
/* 2020 */     return (paramLong == Long.MIN_VALUE) ? plusNanos(Long.MAX_VALUE).plusNanos(1L) : plusNanos(-paramLong);
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
/* 2045 */     if (paramTemporalQuery == TemporalQueries.localDate()) {
/* 2046 */       return (R)toLocalDate();
/*      */     }
/* 2048 */     return super.query(paramTemporalQuery);
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
/*      */   public long until(Temporal paramTemporal, TemporalUnit paramTemporalUnit) {
/* 2119 */     ZonedDateTime zonedDateTime = from(paramTemporal);
/* 2120 */     if (paramTemporalUnit instanceof java.time.temporal.ChronoUnit) {
/* 2121 */       zonedDateTime = zonedDateTime.withZoneSameInstant(this.zone);
/* 2122 */       if (paramTemporalUnit.isDateBased()) {
/* 2123 */         return this.dateTime.until(zonedDateTime.dateTime, paramTemporalUnit);
/*      */       }
/* 2125 */       return toOffsetDateTime().until(zonedDateTime.toOffsetDateTime(), paramTemporalUnit);
/*      */     } 
/*      */     
/* 2128 */     return paramTemporalUnit.between(this, zonedDateTime);
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
/* 2142 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/* 2143 */     return paramDateTimeFormatter.format(this);
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
/*      */   public OffsetDateTime toOffsetDateTime() {
/* 2156 */     return OffsetDateTime.of(this.dateTime, this.offset);
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
/* 2171 */     if (this == paramObject) {
/* 2172 */       return true;
/*      */     }
/* 2174 */     if (paramObject instanceof ZonedDateTime) {
/* 2175 */       ZonedDateTime zonedDateTime = (ZonedDateTime)paramObject;
/* 2176 */       return (this.dateTime.equals(zonedDateTime.dateTime) && this.offset
/* 2177 */         .equals(zonedDateTime.offset) && this.zone
/* 2178 */         .equals(zonedDateTime.zone));
/*      */     } 
/* 2180 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 2190 */     return this.dateTime.hashCode() ^ this.offset.hashCode() ^ Integer.rotateLeft(this.zone.hashCode(), 3);
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
/*      */   public String toString() {
/* 2206 */     String str = this.dateTime.toString() + this.offset.toString();
/* 2207 */     if (this.offset != this.zone) {
/* 2208 */       str = str + '[' + this.zone.toString() + ']';
/*      */     }
/* 2210 */     return str;
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
/* 2228 */     return new Ser((byte)6, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 2238 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*      */   }
/*      */   
/*      */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 2242 */     this.dateTime.writeExternal(paramDataOutput);
/* 2243 */     this.offset.writeExternal(paramDataOutput);
/* 2244 */     this.zone.write(paramDataOutput);
/*      */   }
/*      */   
/*      */   static ZonedDateTime readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 2248 */     LocalDateTime localDateTime = LocalDateTime.readExternal(paramObjectInput);
/* 2249 */     ZoneOffset zoneOffset = ZoneOffset.readExternal(paramObjectInput);
/* 2250 */     ZoneId zoneId = (ZoneId)Ser.read(paramObjectInput);
/* 2251 */     return ofLenient(localDateTime, zoneOffset, zoneId);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/ZonedDateTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */