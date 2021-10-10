/*      */ package java.time;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInput;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutput;
/*      */ import java.io.Serializable;
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
/*      */ import java.time.zone.ZoneRules;
/*      */ import java.util.Comparator;
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
/*      */ public final class OffsetDateTime
/*      */   implements Temporal, TemporalAdjuster, Comparable<OffsetDateTime>, Serializable
/*      */ {
/*  138 */   public static final OffsetDateTime MIN = LocalDateTime.MIN.atOffset(ZoneOffset.MAX);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  146 */   public static final OffsetDateTime MAX = LocalDateTime.MAX.atOffset(ZoneOffset.MIN);
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 2287754244819255394L;
/*      */ 
/*      */ 
/*      */   
/*      */   private final LocalDateTime dateTime;
/*      */ 
/*      */   
/*      */   private final ZoneOffset offset;
/*      */ 
/*      */ 
/*      */   
/*      */   public static Comparator<OffsetDateTime> timeLineOrder() {
/*  162 */     return OffsetDateTime::compareInstant;
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
/*      */   private static int compareInstant(OffsetDateTime paramOffsetDateTime1, OffsetDateTime paramOffsetDateTime2) {
/*  174 */     if (paramOffsetDateTime1.getOffset().equals(paramOffsetDateTime2.getOffset())) {
/*  175 */       return paramOffsetDateTime1.toLocalDateTime().compareTo(paramOffsetDateTime2.toLocalDateTime());
/*      */     }
/*  177 */     int i = Long.compare(paramOffsetDateTime1.toEpochSecond(), paramOffsetDateTime2.toEpochSecond());
/*  178 */     if (i == 0) {
/*  179 */       i = paramOffsetDateTime1.toLocalTime().getNano() - paramOffsetDateTime2.toLocalTime().getNano();
/*      */     }
/*  181 */     return i;
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
/*      */   public static OffsetDateTime now() {
/*  212 */     return now(Clock.systemDefaultZone());
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
/*      */   public static OffsetDateTime now(ZoneId paramZoneId) {
/*  229 */     return now(Clock.system(paramZoneId));
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
/*      */   public static OffsetDateTime now(Clock paramClock) {
/*  245 */     Objects.requireNonNull(paramClock, "clock");
/*  246 */     Instant instant = paramClock.instant();
/*  247 */     return ofInstant(instant, paramClock.getZone().getRules().getOffset(instant));
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
/*      */   public static OffsetDateTime of(LocalDate paramLocalDate, LocalTime paramLocalTime, ZoneOffset paramZoneOffset) {
/*  262 */     LocalDateTime localDateTime = LocalDateTime.of(paramLocalDate, paramLocalTime);
/*  263 */     return new OffsetDateTime(localDateTime, paramZoneOffset);
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
/*      */   public static OffsetDateTime of(LocalDateTime paramLocalDateTime, ZoneOffset paramZoneOffset) {
/*  276 */     return new OffsetDateTime(paramLocalDateTime, paramZoneOffset);
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
/*      */   public static OffsetDateTime of(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, ZoneOffset paramZoneOffset) {
/*  306 */     LocalDateTime localDateTime = LocalDateTime.of(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*  307 */     return new OffsetDateTime(localDateTime, paramZoneOffset);
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
/*      */   public static OffsetDateTime ofInstant(Instant paramInstant, ZoneId paramZoneId) {
/*  324 */     Objects.requireNonNull(paramInstant, "instant");
/*  325 */     Objects.requireNonNull(paramZoneId, "zone");
/*  326 */     ZoneRules zoneRules = paramZoneId.getRules();
/*  327 */     ZoneOffset zoneOffset = zoneRules.getOffset(paramInstant);
/*  328 */     LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(paramInstant.getEpochSecond(), paramInstant.getNano(), zoneOffset);
/*  329 */     return new OffsetDateTime(localDateTime, zoneOffset);
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
/*      */   public static OffsetDateTime from(TemporalAccessor paramTemporalAccessor) {
/*  355 */     if (paramTemporalAccessor instanceof OffsetDateTime) {
/*  356 */       return (OffsetDateTime)paramTemporalAccessor;
/*      */     }
/*      */     try {
/*  359 */       ZoneOffset zoneOffset = ZoneOffset.from(paramTemporalAccessor);
/*  360 */       LocalDate localDate = paramTemporalAccessor.<LocalDate>query(TemporalQueries.localDate());
/*  361 */       LocalTime localTime = paramTemporalAccessor.<LocalTime>query(TemporalQueries.localTime());
/*  362 */       if (localDate != null && localTime != null) {
/*  363 */         return of(localDate, localTime, zoneOffset);
/*      */       }
/*  365 */       Instant instant = Instant.from(paramTemporalAccessor);
/*  366 */       return ofInstant(instant, zoneOffset);
/*      */     }
/*  368 */     catch (DateTimeException dateTimeException) {
/*  369 */       throw new DateTimeException("Unable to obtain OffsetDateTime from TemporalAccessor: " + paramTemporalAccessor + " of type " + paramTemporalAccessor
/*  370 */           .getClass().getName(), dateTimeException);
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
/*      */   public static OffsetDateTime parse(CharSequence paramCharSequence) {
/*  387 */     return parse(paramCharSequence, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
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
/*      */   public static OffsetDateTime parse(CharSequence paramCharSequence, DateTimeFormatter paramDateTimeFormatter) {
/*  401 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/*  402 */     return paramDateTimeFormatter.<OffsetDateTime>parse(paramCharSequence, OffsetDateTime::from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private OffsetDateTime(LocalDateTime paramLocalDateTime, ZoneOffset paramZoneOffset) {
/*  413 */     this.dateTime = Objects.<LocalDateTime>requireNonNull(paramLocalDateTime, "dateTime");
/*  414 */     this.offset = Objects.<ZoneOffset>requireNonNull(paramZoneOffset, "offset");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private OffsetDateTime with(LocalDateTime paramLocalDateTime, ZoneOffset paramZoneOffset) {
/*  424 */     if (this.dateTime == paramLocalDateTime && this.offset.equals(paramZoneOffset)) {
/*  425 */       return this;
/*      */     }
/*  427 */     return new OffsetDateTime(paramLocalDateTime, paramZoneOffset);
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
/*  485 */     return (paramTemporalField instanceof ChronoField || (paramTemporalField != null && paramTemporalField.isSupportedBy(this)));
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
/*  526 */     if (paramTemporalUnit instanceof ChronoUnit) {
/*  527 */       return (paramTemporalUnit != ChronoUnit.FOREVER);
/*      */     }
/*  529 */     return (paramTemporalUnit != null && paramTemporalUnit.isSupportedBy(this));
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
/*  558 */     if (paramTemporalField instanceof ChronoField) {
/*  559 */       if (paramTemporalField == ChronoField.INSTANT_SECONDS || paramTemporalField == ChronoField.OFFSET_SECONDS) {
/*  560 */         return paramTemporalField.range();
/*      */       }
/*  562 */       return this.dateTime.range(paramTemporalField);
/*      */     } 
/*  564 */     return paramTemporalField.rangeRefinedBy(this);
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
/*  597 */     if (paramTemporalField instanceof ChronoField) {
/*  598 */       switch ((ChronoField)paramTemporalField) {
/*      */         case INSTANT_SECONDS:
/*  600 */           throw new UnsupportedTemporalTypeException("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
/*      */         case OFFSET_SECONDS:
/*  602 */           return getOffset().getTotalSeconds();
/*      */       } 
/*  604 */       return this.dateTime.get(paramTemporalField);
/*      */     } 
/*  606 */     return super.get(paramTemporalField);
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
/*  634 */     if (paramTemporalField instanceof ChronoField) {
/*  635 */       switch ((ChronoField)paramTemporalField) { case INSTANT_SECONDS:
/*  636 */           return toEpochSecond();
/*  637 */         case OFFSET_SECONDS: return getOffset().getTotalSeconds(); }
/*      */       
/*  639 */       return this.dateTime.getLong(paramTemporalField);
/*      */     } 
/*  641 */     return paramTemporalField.getFrom(this);
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
/*      */   public ZoneOffset getOffset() {
/*  653 */     return this.offset;
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
/*      */   public OffsetDateTime withOffsetSameLocal(ZoneOffset paramZoneOffset) {
/*  674 */     return with(this.dateTime, paramZoneOffset);
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
/*      */   public OffsetDateTime withOffsetSameInstant(ZoneOffset paramZoneOffset) {
/*  697 */     if (paramZoneOffset.equals(this.offset)) {
/*  698 */       return this;
/*      */     }
/*  700 */     int i = paramZoneOffset.getTotalSeconds() - this.offset.getTotalSeconds();
/*  701 */     LocalDateTime localDateTime = this.dateTime.plusSeconds(i);
/*  702 */     return new OffsetDateTime(localDateTime, paramZoneOffset);
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
/*      */   public LocalDateTime toLocalDateTime() {
/*  715 */     return this.dateTime;
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
/*      */   public LocalDate toLocalDate() {
/*  728 */     return this.dateTime.toLocalDate();
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
/*  742 */     return this.dateTime.getYear();
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
/*  756 */     return this.dateTime.getMonthValue();
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
/*  771 */     return this.dateTime.getMonth();
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
/*  782 */     return this.dateTime.getDayOfMonth();
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
/*  793 */     return this.dateTime.getDayOfYear();
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
/*  810 */     return this.dateTime.getDayOfWeek();
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
/*      */   public LocalTime toLocalTime() {
/*  823 */     return this.dateTime.toLocalTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHour() {
/*  832 */     return this.dateTime.getHour();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinute() {
/*  841 */     return this.dateTime.getMinute();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSecond() {
/*  850 */     return this.dateTime.getSecond();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNano() {
/*  859 */     return this.dateTime.getNano();
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
/*      */   public OffsetDateTime with(TemporalAdjuster paramTemporalAdjuster) {
/*  910 */     if (paramTemporalAdjuster instanceof LocalDate || paramTemporalAdjuster instanceof LocalTime || paramTemporalAdjuster instanceof LocalDateTime)
/*  911 */       return with(this.dateTime.with(paramTemporalAdjuster), this.offset); 
/*  912 */     if (paramTemporalAdjuster instanceof Instant)
/*  913 */       return ofInstant((Instant)paramTemporalAdjuster, this.offset); 
/*  914 */     if (paramTemporalAdjuster instanceof ZoneOffset)
/*  915 */       return with(this.dateTime, (ZoneOffset)paramTemporalAdjuster); 
/*  916 */     if (paramTemporalAdjuster instanceof OffsetDateTime) {
/*  917 */       return (OffsetDateTime)paramTemporalAdjuster;
/*      */     }
/*  919 */     return (OffsetDateTime)paramTemporalAdjuster.adjustInto(this);
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
/*      */   public OffsetDateTime with(TemporalField paramTemporalField, long paramLong) {
/*  968 */     if (paramTemporalField instanceof ChronoField) {
/*  969 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/*  970 */       switch (chronoField) { case INSTANT_SECONDS:
/*  971 */           return ofInstant(Instant.ofEpochSecond(paramLong, getNano()), this.offset);
/*      */         case OFFSET_SECONDS:
/*  973 */           return with(this.dateTime, ZoneOffset.ofTotalSeconds(chronoField.checkValidIntValue(paramLong))); }
/*      */ 
/*      */       
/*  976 */       return with(this.dateTime.with(paramTemporalField, paramLong), this.offset);
/*      */     } 
/*  978 */     return paramTemporalField.<OffsetDateTime>adjustInto(this, paramLong);
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
/*      */   public OffsetDateTime withYear(int paramInt) {
/*  995 */     return with(this.dateTime.withYear(paramInt), this.offset);
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
/*      */   public OffsetDateTime withMonth(int paramInt) {
/* 1011 */     return with(this.dateTime.withMonth(paramInt), this.offset);
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
/*      */   public OffsetDateTime withDayOfMonth(int paramInt) {
/* 1028 */     return with(this.dateTime.withDayOfMonth(paramInt), this.offset);
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
/*      */   public OffsetDateTime withDayOfYear(int paramInt) {
/* 1045 */     return with(this.dateTime.withDayOfYear(paramInt), this.offset);
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
/*      */   public OffsetDateTime withHour(int paramInt) {
/* 1061 */     return with(this.dateTime.withHour(paramInt), this.offset);
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
/*      */   public OffsetDateTime withMinute(int paramInt) {
/* 1076 */     return with(this.dateTime.withMinute(paramInt), this.offset);
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
/*      */   public OffsetDateTime withSecond(int paramInt) {
/* 1091 */     return with(this.dateTime.withSecond(paramInt), this.offset);
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
/*      */   public OffsetDateTime withNano(int paramInt) {
/* 1106 */     return with(this.dateTime.withNano(paramInt), this.offset);
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
/*      */   public OffsetDateTime truncatedTo(TemporalUnit paramTemporalUnit) {
/* 1133 */     return with(this.dateTime.truncatedTo(paramTemporalUnit), this.offset);
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
/*      */   public OffsetDateTime plus(TemporalAmount paramTemporalAmount) {
/* 1159 */     return (OffsetDateTime)paramTemporalAmount.addTo(this);
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
/*      */   public OffsetDateTime plus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 1189 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 1190 */       return with(this.dateTime.plus(paramLong, paramTemporalUnit), this.offset);
/*      */     }
/* 1192 */     return paramTemporalUnit.<OffsetDateTime>addTo(this, paramLong);
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
/*      */   public OffsetDateTime plusYears(long paramLong) {
/* 1217 */     return with(this.dateTime.plusYears(paramLong), this.offset);
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
/*      */   public OffsetDateTime plusMonths(long paramLong) {
/* 1241 */     return with(this.dateTime.plusMonths(paramLong), this.offset);
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
/*      */   public OffsetDateTime plusWeeks(long paramLong) {
/* 1260 */     return with(this.dateTime.plusWeeks(paramLong), this.offset);
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
/*      */   public OffsetDateTime plusDays(long paramLong) {
/* 1279 */     return with(this.dateTime.plusDays(paramLong), this.offset);
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
/*      */   public OffsetDateTime plusHours(long paramLong) {
/* 1292 */     return with(this.dateTime.plusHours(paramLong), this.offset);
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
/*      */   public OffsetDateTime plusMinutes(long paramLong) {
/* 1305 */     return with(this.dateTime.plusMinutes(paramLong), this.offset);
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
/*      */   public OffsetDateTime plusSeconds(long paramLong) {
/* 1318 */     return with(this.dateTime.plusSeconds(paramLong), this.offset);
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
/*      */   public OffsetDateTime plusNanos(long paramLong) {
/* 1331 */     return with(this.dateTime.plusNanos(paramLong), this.offset);
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
/*      */   public OffsetDateTime minus(TemporalAmount paramTemporalAmount) {
/* 1357 */     return (OffsetDateTime)paramTemporalAmount.subtractFrom(this);
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
/*      */   public OffsetDateTime minus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 1381 */     return (paramLong == Long.MIN_VALUE) ? plus(Long.MAX_VALUE, paramTemporalUnit).plus(1L, paramTemporalUnit) : plus(-paramLong, paramTemporalUnit);
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
/*      */   public OffsetDateTime minusYears(long paramLong) {
/* 1406 */     return (paramLong == Long.MIN_VALUE) ? plusYears(Long.MAX_VALUE).plusYears(1L) : plusYears(-paramLong);
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
/*      */   public OffsetDateTime minusMonths(long paramLong) {
/* 1430 */     return (paramLong == Long.MIN_VALUE) ? plusMonths(Long.MAX_VALUE).plusMonths(1L) : plusMonths(-paramLong);
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
/*      */   public OffsetDateTime minusWeeks(long paramLong) {
/* 1449 */     return (paramLong == Long.MIN_VALUE) ? plusWeeks(Long.MAX_VALUE).plusWeeks(1L) : plusWeeks(-paramLong);
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
/*      */   public OffsetDateTime minusDays(long paramLong) {
/* 1468 */     return (paramLong == Long.MIN_VALUE) ? plusDays(Long.MAX_VALUE).plusDays(1L) : plusDays(-paramLong);
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
/*      */   public OffsetDateTime minusHours(long paramLong) {
/* 1481 */     return (paramLong == Long.MIN_VALUE) ? plusHours(Long.MAX_VALUE).plusHours(1L) : plusHours(-paramLong);
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
/*      */   public OffsetDateTime minusMinutes(long paramLong) {
/* 1494 */     return (paramLong == Long.MIN_VALUE) ? plusMinutes(Long.MAX_VALUE).plusMinutes(1L) : plusMinutes(-paramLong);
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
/*      */   public OffsetDateTime minusSeconds(long paramLong) {
/* 1507 */     return (paramLong == Long.MIN_VALUE) ? plusSeconds(Long.MAX_VALUE).plusSeconds(1L) : plusSeconds(-paramLong);
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
/*      */   public OffsetDateTime minusNanos(long paramLong) {
/* 1520 */     return (paramLong == Long.MIN_VALUE) ? plusNanos(Long.MAX_VALUE).plusNanos(1L) : plusNanos(-paramLong);
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
/* 1545 */     if (paramTemporalQuery == TemporalQueries.offset() || paramTemporalQuery == TemporalQueries.zone())
/* 1546 */       return (R)getOffset(); 
/* 1547 */     if (paramTemporalQuery == TemporalQueries.zoneId())
/* 1548 */       return null; 
/* 1549 */     if (paramTemporalQuery == TemporalQueries.localDate())
/* 1550 */       return (R)toLocalDate(); 
/* 1551 */     if (paramTemporalQuery == TemporalQueries.localTime())
/* 1552 */       return (R)toLocalTime(); 
/* 1553 */     if (paramTemporalQuery == TemporalQueries.chronology())
/* 1554 */       return (R)IsoChronology.INSTANCE; 
/* 1555 */     if (paramTemporalQuery == TemporalQueries.precision()) {
/* 1556 */       return (R)ChronoUnit.NANOS;
/*      */     }
/*      */ 
/*      */     
/* 1560 */     return paramTemporalQuery.queryFrom(this);
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
/*      */   public Temporal adjustInto(Temporal paramTemporal) {
/* 1595 */     return paramTemporal
/* 1596 */       .with(ChronoField.EPOCH_DAY, toLocalDate().toEpochDay())
/* 1597 */       .with(ChronoField.NANO_OF_DAY, toLocalTime().toNanoOfDay())
/* 1598 */       .with(ChronoField.OFFSET_SECONDS, getOffset().getTotalSeconds());
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
/*      */   public long until(Temporal paramTemporal, TemporalUnit paramTemporalUnit) {
/* 1655 */     OffsetDateTime offsetDateTime = from(paramTemporal);
/* 1656 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 1657 */       offsetDateTime = offsetDateTime.withOffsetSameInstant(this.offset);
/* 1658 */       return this.dateTime.until(offsetDateTime.dateTime, paramTemporalUnit);
/*      */     } 
/* 1660 */     return paramTemporalUnit.between(this, offsetDateTime);
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
/* 1673 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/* 1674 */     return paramDateTimeFormatter.format(this);
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
/*      */   public ZonedDateTime atZoneSameInstant(ZoneId paramZoneId) {
/* 1694 */     return ZonedDateTime.ofInstant(this.dateTime, this.offset, paramZoneId);
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
/*      */   public ZonedDateTime atZoneSimilarLocal(ZoneId paramZoneId) {
/* 1722 */     return ZonedDateTime.ofLocal(this.dateTime, paramZoneId, this.offset);
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
/*      */   public OffsetTime toOffsetTime() {
/* 1734 */     return OffsetTime.of(this.dateTime.toLocalTime(), this.offset);
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
/*      */   public ZonedDateTime toZonedDateTime() {
/* 1749 */     return ZonedDateTime.of(this.dateTime, this.offset);
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
/*      */   public Instant toInstant() {
/* 1761 */     return this.dateTime.toInstant(this.offset);
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
/*      */   public long toEpochSecond() {
/* 1774 */     return this.dateTime.toEpochSecond(this.offset);
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
/*      */   public int compareTo(OffsetDateTime paramOffsetDateTime) {
/* 1803 */     int i = compareInstant(this, paramOffsetDateTime);
/* 1804 */     if (i == 0) {
/* 1805 */       i = toLocalDateTime().compareTo(paramOffsetDateTime.toLocalDateTime());
/*      */     }
/* 1807 */     return i;
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
/*      */   public boolean isAfter(OffsetDateTime paramOffsetDateTime) {
/* 1822 */     long l1 = toEpochSecond();
/* 1823 */     long l2 = paramOffsetDateTime.toEpochSecond();
/* 1824 */     return (l1 > l2 || (l1 == l2 && 
/* 1825 */       toLocalTime().getNano() > paramOffsetDateTime.toLocalTime().getNano()));
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
/*      */   public boolean isBefore(OffsetDateTime paramOffsetDateTime) {
/* 1839 */     long l1 = toEpochSecond();
/* 1840 */     long l2 = paramOffsetDateTime.toEpochSecond();
/* 1841 */     return (l1 < l2 || (l1 == l2 && 
/* 1842 */       toLocalTime().getNano() < paramOffsetDateTime.toLocalTime().getNano()));
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
/*      */   public boolean isEqual(OffsetDateTime paramOffsetDateTime) {
/* 1856 */     return (toEpochSecond() == paramOffsetDateTime.toEpochSecond() && 
/* 1857 */       toLocalTime().getNano() == paramOffsetDateTime.toLocalTime().getNano());
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
/*      */   public boolean equals(Object paramObject) {
/* 1873 */     if (this == paramObject) {
/* 1874 */       return true;
/*      */     }
/* 1876 */     if (paramObject instanceof OffsetDateTime) {
/* 1877 */       OffsetDateTime offsetDateTime = (OffsetDateTime)paramObject;
/* 1878 */       return (this.dateTime.equals(offsetDateTime.dateTime) && this.offset.equals(offsetDateTime.offset));
/*      */     } 
/* 1880 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1890 */     return this.dateTime.hashCode() ^ this.offset.hashCode();
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
/*      */   public String toString() {
/* 1912 */     return this.dateTime.toString() + this.offset.toString();
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
/* 1929 */     return new Ser((byte)10, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 1939 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*      */   }
/*      */   
/*      */   void writeExternal(ObjectOutput paramObjectOutput) throws IOException {
/* 1943 */     this.dateTime.writeExternal(paramObjectOutput);
/* 1944 */     this.offset.writeExternal(paramObjectOutput);
/*      */   }
/*      */   
/*      */   static OffsetDateTime readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 1948 */     LocalDateTime localDateTime = LocalDateTime.readExternal(paramObjectInput);
/* 1949 */     ZoneOffset zoneOffset = ZoneOffset.readExternal(paramObjectInput);
/* 1950 */     return of(localDateTime, zoneOffset);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/OffsetDateTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */