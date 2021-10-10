/*      */ package java.time;
/*      */ 
/*      */ import java.io.DataInput;
/*      */ import java.io.DataOutput;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.time.chrono.ChronoLocalDate;
/*      */ import java.time.chrono.ChronoLocalDateTime;
/*      */ import java.time.chrono.ChronoZonedDateTime;
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
/*      */ import java.time.temporal.ValueRange;
/*      */ import java.time.zone.ZoneRules;
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
/*      */ public final class LocalDateTime
/*      */   implements Temporal, TemporalAdjuster, ChronoLocalDateTime<LocalDate>, Serializable
/*      */ {
/*  144 */   public static final LocalDateTime MIN = of(LocalDate.MIN, LocalTime.MIN);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  151 */   public static final LocalDateTime MAX = of(LocalDate.MAX, LocalTime.MAX);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 6207766400415563566L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final LocalDate date;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final LocalTime time;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LocalDateTime now() {
/*  180 */     return now(Clock.systemDefaultZone());
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
/*      */   public static LocalDateTime now(ZoneId paramZoneId) {
/*  196 */     return now(Clock.system(paramZoneId));
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
/*      */   public static LocalDateTime now(Clock paramClock) {
/*  210 */     Objects.requireNonNull(paramClock, "clock");
/*  211 */     Instant instant = paramClock.instant();
/*  212 */     ZoneOffset zoneOffset = paramClock.getZone().getRules().getOffset(instant);
/*  213 */     return ofEpochSecond(instant.getEpochSecond(), instant.getNano(), zoneOffset);
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
/*      */   public static LocalDateTime of(int paramInt1, Month paramMonth, int paramInt2, int paramInt3, int paramInt4) {
/*  236 */     LocalDate localDate = LocalDate.of(paramInt1, paramMonth, paramInt2);
/*  237 */     LocalTime localTime = LocalTime.of(paramInt3, paramInt4);
/*  238 */     return new LocalDateTime(localDate, localTime);
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
/*      */   public static LocalDateTime of(int paramInt1, Month paramMonth, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  261 */     LocalDate localDate = LocalDate.of(paramInt1, paramMonth, paramInt2);
/*  262 */     LocalTime localTime = LocalTime.of(paramInt3, paramInt4, paramInt5);
/*  263 */     return new LocalDateTime(localDate, localTime);
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
/*      */   public static LocalDateTime of(int paramInt1, Month paramMonth, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  286 */     LocalDate localDate = LocalDate.of(paramInt1, paramMonth, paramInt2);
/*  287 */     LocalTime localTime = LocalTime.of(paramInt3, paramInt4, paramInt5, paramInt6);
/*  288 */     return new LocalDateTime(localDate, localTime);
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
/*      */   public static LocalDateTime of(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  311 */     LocalDate localDate = LocalDate.of(paramInt1, paramInt2, paramInt3);
/*  312 */     LocalTime localTime = LocalTime.of(paramInt4, paramInt5);
/*  313 */     return new LocalDateTime(localDate, localTime);
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
/*      */   public static LocalDateTime of(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  336 */     LocalDate localDate = LocalDate.of(paramInt1, paramInt2, paramInt3);
/*  337 */     LocalTime localTime = LocalTime.of(paramInt4, paramInt5, paramInt6);
/*  338 */     return new LocalDateTime(localDate, localTime);
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
/*      */   public static LocalDateTime of(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/*  361 */     LocalDate localDate = LocalDate.of(paramInt1, paramInt2, paramInt3);
/*  362 */     LocalTime localTime = LocalTime.of(paramInt4, paramInt5, paramInt6, paramInt7);
/*  363 */     return new LocalDateTime(localDate, localTime);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LocalDateTime of(LocalDate paramLocalDate, LocalTime paramLocalTime) {
/*  374 */     Objects.requireNonNull(paramLocalDate, "date");
/*  375 */     Objects.requireNonNull(paramLocalTime, "time");
/*  376 */     return new LocalDateTime(paramLocalDate, paramLocalTime);
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
/*      */   public static LocalDateTime ofInstant(Instant paramInstant, ZoneId paramZoneId) {
/*  394 */     Objects.requireNonNull(paramInstant, "instant");
/*  395 */     Objects.requireNonNull(paramZoneId, "zone");
/*  396 */     ZoneRules zoneRules = paramZoneId.getRules();
/*  397 */     ZoneOffset zoneOffset = zoneRules.getOffset(paramInstant);
/*  398 */     return ofEpochSecond(paramInstant.getEpochSecond(), paramInstant.getNano(), zoneOffset);
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
/*      */   public static LocalDateTime ofEpochSecond(long paramLong, int paramInt, ZoneOffset paramZoneOffset) {
/*  417 */     Objects.requireNonNull(paramZoneOffset, "offset");
/*  418 */     ChronoField.NANO_OF_SECOND.checkValidValue(paramInt);
/*  419 */     long l1 = paramLong + paramZoneOffset.getTotalSeconds();
/*  420 */     long l2 = Math.floorDiv(l1, 86400L);
/*  421 */     int i = (int)Math.floorMod(l1, 86400L);
/*  422 */     LocalDate localDate = LocalDate.ofEpochDay(l2);
/*  423 */     LocalTime localTime = LocalTime.ofNanoOfDay(i * 1000000000L + paramInt);
/*  424 */     return new LocalDateTime(localDate, localTime);
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
/*      */   public static LocalDateTime from(TemporalAccessor paramTemporalAccessor) {
/*  448 */     if (paramTemporalAccessor instanceof LocalDateTime)
/*  449 */       return (LocalDateTime)paramTemporalAccessor; 
/*  450 */     if (paramTemporalAccessor instanceof ZonedDateTime)
/*  451 */       return ((ZonedDateTime)paramTemporalAccessor).toLocalDateTime(); 
/*  452 */     if (paramTemporalAccessor instanceof OffsetDateTime) {
/*  453 */       return ((OffsetDateTime)paramTemporalAccessor).toLocalDateTime();
/*      */     }
/*      */     try {
/*  456 */       LocalDate localDate = LocalDate.from(paramTemporalAccessor);
/*  457 */       LocalTime localTime = LocalTime.from(paramTemporalAccessor);
/*  458 */       return new LocalDateTime(localDate, localTime);
/*  459 */     } catch (DateTimeException dateTimeException) {
/*  460 */       throw new DateTimeException("Unable to obtain LocalDateTime from TemporalAccessor: " + paramTemporalAccessor + " of type " + paramTemporalAccessor
/*  461 */           .getClass().getName(), dateTimeException);
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
/*      */   public static LocalDateTime parse(CharSequence paramCharSequence) {
/*  477 */     return parse(paramCharSequence, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
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
/*      */   public static LocalDateTime parse(CharSequence paramCharSequence, DateTimeFormatter paramDateTimeFormatter) {
/*  491 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/*  492 */     return paramDateTimeFormatter.<LocalDateTime>parse(paramCharSequence, LocalDateTime::from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LocalDateTime(LocalDate paramLocalDate, LocalTime paramLocalTime) {
/*  503 */     this.date = paramLocalDate;
/*  504 */     this.time = paramLocalTime;
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
/*      */   private LocalDateTime with(LocalDate paramLocalDate, LocalTime paramLocalTime) {
/*  516 */     if (this.date == paramLocalDate && this.time == paramLocalTime) {
/*  517 */       return this;
/*      */     }
/*  519 */     return new LocalDateTime(paramLocalDate, paramLocalTime);
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
/*      */   public boolean isSupported(TemporalField paramTemporalField) {
/*  575 */     if (paramTemporalField instanceof ChronoField) {
/*  576 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/*  577 */       return (chronoField.isDateBased() || chronoField.isTimeBased());
/*      */     } 
/*  579 */     return (paramTemporalField != null && paramTemporalField.isSupportedBy(this));
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
/*  620 */     return super.isSupported(paramTemporalUnit);
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
/*  649 */     if (paramTemporalField instanceof ChronoField) {
/*  650 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/*  651 */       return chronoField.isTimeBased() ? this.time.range(paramTemporalField) : this.date.range(paramTemporalField);
/*      */     } 
/*  653 */     return paramTemporalField.rangeRefinedBy(this);
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
/*  686 */     if (paramTemporalField instanceof ChronoField) {
/*  687 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/*  688 */       return chronoField.isTimeBased() ? this.time.get(paramTemporalField) : this.date.get(paramTemporalField);
/*      */     } 
/*  690 */     return super.get(paramTemporalField);
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
/*  718 */     if (paramTemporalField instanceof ChronoField) {
/*  719 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/*  720 */       return chronoField.isTimeBased() ? this.time.getLong(paramTemporalField) : this.date.getLong(paramTemporalField);
/*      */     } 
/*  722 */     return paramTemporalField.getFrom(this);
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
/*  736 */     return this.date;
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
/*  750 */     return this.date.getYear();
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
/*  764 */     return this.date.getMonthValue();
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
/*  779 */     return this.date.getMonth();
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
/*  790 */     return this.date.getDayOfMonth();
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
/*  801 */     return this.date.getDayOfYear();
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
/*  818 */     return this.date.getDayOfWeek();
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
/*  832 */     return this.time;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHour() {
/*  841 */     return this.time.getHour();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinute() {
/*  850 */     return this.time.getMinute();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSecond() {
/*  859 */     return this.time.getSecond();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNano() {
/*  868 */     return this.time.getNano();
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
/*      */   public LocalDateTime with(TemporalAdjuster paramTemporalAdjuster) {
/*  919 */     if (paramTemporalAdjuster instanceof LocalDate)
/*  920 */       return with((LocalDate)paramTemporalAdjuster, this.time); 
/*  921 */     if (paramTemporalAdjuster instanceof LocalTime)
/*  922 */       return with(this.date, (LocalTime)paramTemporalAdjuster); 
/*  923 */     if (paramTemporalAdjuster instanceof LocalDateTime) {
/*  924 */       return (LocalDateTime)paramTemporalAdjuster;
/*      */     }
/*  926 */     return (LocalDateTime)paramTemporalAdjuster.adjustInto(this);
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
/*      */   public LocalDateTime with(TemporalField paramTemporalField, long paramLong) {
/*  965 */     if (paramTemporalField instanceof ChronoField) {
/*  966 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/*  967 */       if (chronoField.isTimeBased()) {
/*  968 */         return with(this.date, this.time.with(paramTemporalField, paramLong));
/*      */       }
/*  970 */       return with(this.date.with(paramTemporalField, paramLong), this.time);
/*      */     } 
/*      */     
/*  973 */     return paramTemporalField.<LocalDateTime>adjustInto(this, paramLong);
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
/*      */   public LocalDateTime withYear(int paramInt) {
/*  990 */     return with(this.date.withYear(paramInt), this.time);
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
/*      */   public LocalDateTime withMonth(int paramInt) {
/* 1006 */     return with(this.date.withMonth(paramInt), this.time);
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
/*      */   public LocalDateTime withDayOfMonth(int paramInt) {
/* 1023 */     return with(this.date.withDayOfMonth(paramInt), this.time);
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
/*      */   public LocalDateTime withDayOfYear(int paramInt) {
/* 1039 */     return with(this.date.withDayOfYear(paramInt), this.time);
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
/*      */   public LocalDateTime withHour(int paramInt) {
/* 1053 */     LocalTime localTime = this.time.withHour(paramInt);
/* 1054 */     return with(this.date, localTime);
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
/*      */   public LocalDateTime withMinute(int paramInt) {
/* 1067 */     LocalTime localTime = this.time.withMinute(paramInt);
/* 1068 */     return with(this.date, localTime);
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
/*      */   public LocalDateTime withSecond(int paramInt) {
/* 1081 */     LocalTime localTime = this.time.withSecond(paramInt);
/* 1082 */     return with(this.date, localTime);
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
/*      */   public LocalDateTime withNano(int paramInt) {
/* 1095 */     LocalTime localTime = this.time.withNano(paramInt);
/* 1096 */     return with(this.date, localTime);
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
/*      */   public LocalDateTime truncatedTo(TemporalUnit paramTemporalUnit) {
/* 1121 */     return with(this.date, this.time.truncatedTo(paramTemporalUnit));
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
/*      */   public LocalDateTime plus(TemporalAmount paramTemporalAmount) {
/* 1147 */     if (paramTemporalAmount instanceof Period) {
/* 1148 */       Period period = (Period)paramTemporalAmount;
/* 1149 */       return with(this.date.plus(period), this.time);
/*      */     } 
/* 1151 */     Objects.requireNonNull(paramTemporalAmount, "amountToAdd");
/* 1152 */     return (LocalDateTime)paramTemporalAmount.addTo(this);
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
/*      */   public LocalDateTime plus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 1183 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 1184 */       ChronoUnit chronoUnit = (ChronoUnit)paramTemporalUnit;
/* 1185 */       switch (chronoUnit) { case NANOS:
/* 1186 */           return plusNanos(paramLong);
/* 1187 */         case MICROS: return plusDays(paramLong / 86400000000L).plusNanos(paramLong % 86400000000L * 1000L);
/* 1188 */         case MILLIS: return plusDays(paramLong / 86400000L).plusNanos(paramLong % 86400000L * 1000000L);
/* 1189 */         case SECONDS: return plusSeconds(paramLong);
/* 1190 */         case MINUTES: return plusMinutes(paramLong);
/* 1191 */         case HOURS: return plusHours(paramLong);
/* 1192 */         case HALF_DAYS: return plusDays(paramLong / 256L).plusHours(paramLong % 256L * 12L); }
/*      */       
/* 1194 */       return with(this.date.plus(paramLong, paramTemporalUnit), this.time);
/*      */     } 
/* 1196 */     return paramTemporalUnit.<LocalDateTime>addTo(this, paramLong);
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
/*      */   public LocalDateTime plusYears(long paramLong) {
/* 1221 */     LocalDate localDate = this.date.plusYears(paramLong);
/* 1222 */     return with(localDate, this.time);
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
/*      */   public LocalDateTime plusMonths(long paramLong) {
/* 1246 */     LocalDate localDate = this.date.plusMonths(paramLong);
/* 1247 */     return with(localDate, this.time);
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
/*      */   public LocalDateTime plusWeeks(long paramLong) {
/* 1266 */     LocalDate localDate = this.date.plusWeeks(paramLong);
/* 1267 */     return with(localDate, this.time);
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
/*      */   public LocalDateTime plusDays(long paramLong) {
/* 1286 */     LocalDate localDate = this.date.plusDays(paramLong);
/* 1287 */     return with(localDate, this.time);
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
/*      */   public LocalDateTime plusHours(long paramLong) {
/* 1301 */     return plusWithOverflow(this.date, paramLong, 0L, 0L, 0L, 1);
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
/*      */   public LocalDateTime plusMinutes(long paramLong) {
/* 1314 */     return plusWithOverflow(this.date, 0L, paramLong, 0L, 0L, 1);
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
/*      */   public LocalDateTime plusSeconds(long paramLong) {
/* 1327 */     return plusWithOverflow(this.date, 0L, 0L, paramLong, 0L, 1);
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
/*      */   public LocalDateTime plusNanos(long paramLong) {
/* 1340 */     return plusWithOverflow(this.date, 0L, 0L, 0L, paramLong, 1);
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
/*      */   public LocalDateTime minus(TemporalAmount paramTemporalAmount) {
/* 1366 */     if (paramTemporalAmount instanceof Period) {
/* 1367 */       Period period = (Period)paramTemporalAmount;
/* 1368 */       return with(this.date.minus(period), this.time);
/*      */     } 
/* 1370 */     Objects.requireNonNull(paramTemporalAmount, "amountToSubtract");
/* 1371 */     return (LocalDateTime)paramTemporalAmount.subtractFrom(this);
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
/*      */   public LocalDateTime minus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 1395 */     return (paramLong == Long.MIN_VALUE) ? plus(Long.MAX_VALUE, paramTemporalUnit).plus(1L, paramTemporalUnit) : plus(-paramLong, paramTemporalUnit);
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
/*      */   public LocalDateTime minusYears(long paramLong) {
/* 1420 */     return (paramLong == Long.MIN_VALUE) ? plusYears(Long.MAX_VALUE).plusYears(1L) : plusYears(-paramLong);
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
/*      */   public LocalDateTime minusMonths(long paramLong) {
/* 1444 */     return (paramLong == Long.MIN_VALUE) ? plusMonths(Long.MAX_VALUE).plusMonths(1L) : plusMonths(-paramLong);
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
/*      */   public LocalDateTime minusWeeks(long paramLong) {
/* 1463 */     return (paramLong == Long.MIN_VALUE) ? plusWeeks(Long.MAX_VALUE).plusWeeks(1L) : plusWeeks(-paramLong);
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
/*      */   public LocalDateTime minusDays(long paramLong) {
/* 1482 */     return (paramLong == Long.MIN_VALUE) ? plusDays(Long.MAX_VALUE).plusDays(1L) : plusDays(-paramLong);
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
/*      */   public LocalDateTime minusHours(long paramLong) {
/* 1496 */     return plusWithOverflow(this.date, paramLong, 0L, 0L, 0L, -1);
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
/*      */   public LocalDateTime minusMinutes(long paramLong) {
/* 1509 */     return plusWithOverflow(this.date, 0L, paramLong, 0L, 0L, -1);
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
/*      */   public LocalDateTime minusSeconds(long paramLong) {
/* 1522 */     return plusWithOverflow(this.date, 0L, 0L, paramLong, 0L, -1);
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
/*      */   public LocalDateTime minusNanos(long paramLong) {
/* 1535 */     return plusWithOverflow(this.date, 0L, 0L, 0L, paramLong, -1);
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
/*      */   private LocalDateTime plusWithOverflow(LocalDate paramLocalDate, long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt) {
/* 1554 */     if ((paramLong1 | paramLong2 | paramLong3 | paramLong4) == 0L) {
/* 1555 */       return with(paramLocalDate, this.time);
/*      */     }
/* 1557 */     long l1 = paramLong4 / 86400000000000L + paramLong3 / 86400L + paramLong2 / 1440L + paramLong1 / 24L;
/*      */ 
/*      */ 
/*      */     
/* 1561 */     l1 *= paramInt;
/* 1562 */     long l2 = paramLong4 % 86400000000000L + paramLong3 % 86400L * 1000000000L + paramLong2 % 1440L * 60000000000L + paramLong1 % 24L * 3600000000000L;
/*      */ 
/*      */ 
/*      */     
/* 1566 */     long l3 = this.time.toNanoOfDay();
/* 1567 */     l2 = l2 * paramInt + l3;
/* 1568 */     l1 += Math.floorDiv(l2, 86400000000000L);
/* 1569 */     long l4 = Math.floorMod(l2, 86400000000000L);
/* 1570 */     LocalTime localTime = (l4 == l3) ? this.time : LocalTime.ofNanoOfDay(l4);
/* 1571 */     return with(paramLocalDate.plusDays(l1), localTime);
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
/* 1596 */     if (paramTemporalQuery == TemporalQueries.localDate()) {
/* 1597 */       return (R)this.date;
/*      */     }
/* 1599 */     return super.query(paramTemporalQuery);
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
/*      */   public Temporal adjustInto(Temporal paramTemporal) {
/* 1629 */     return super.adjustInto(paramTemporal);
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
/*      */   public long until(Temporal paramTemporal, TemporalUnit paramTemporalUnit) {
/* 1683 */     LocalDateTime localDateTime = from(paramTemporal);
/* 1684 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 1685 */       if (paramTemporalUnit.isTimeBased()) {
/* 1686 */         long l1 = this.date.daysUntil(localDateTime.date);
/* 1687 */         if (l1 == 0L) {
/* 1688 */           return this.time.until(localDateTime.time, paramTemporalUnit);
/*      */         }
/* 1690 */         long l2 = localDateTime.time.toNanoOfDay() - this.time.toNanoOfDay();
/* 1691 */         if (l1 > 0L) {
/* 1692 */           l1--;
/* 1693 */           l2 += 86400000000000L;
/*      */         } else {
/* 1695 */           l1++;
/* 1696 */           l2 -= 86400000000000L;
/*      */         } 
/* 1698 */         switch ((ChronoUnit)paramTemporalUnit) {
/*      */           case NANOS:
/* 1700 */             l1 = Math.multiplyExact(l1, 86400000000000L);
/*      */             break;
/*      */           case MICROS:
/* 1703 */             l1 = Math.multiplyExact(l1, 86400000000L);
/* 1704 */             l2 /= 1000L;
/*      */             break;
/*      */           case MILLIS:
/* 1707 */             l1 = Math.multiplyExact(l1, 86400000L);
/* 1708 */             l2 /= 1000000L;
/*      */             break;
/*      */           case SECONDS:
/* 1711 */             l1 = Math.multiplyExact(l1, 86400L);
/* 1712 */             l2 /= 1000000000L;
/*      */             break;
/*      */           case MINUTES:
/* 1715 */             l1 = Math.multiplyExact(l1, 1440L);
/* 1716 */             l2 /= 60000000000L;
/*      */             break;
/*      */           case HOURS:
/* 1719 */             l1 = Math.multiplyExact(l1, 24L);
/* 1720 */             l2 /= 3600000000000L;
/*      */             break;
/*      */           case HALF_DAYS:
/* 1723 */             l1 = Math.multiplyExact(l1, 2L);
/* 1724 */             l2 /= 43200000000000L;
/*      */             break;
/*      */         } 
/* 1727 */         return Math.addExact(l1, l2);
/*      */       } 
/* 1729 */       LocalDate localDate = localDateTime.date;
/* 1730 */       if (localDate.isAfter(this.date) && localDateTime.time.isBefore(this.time)) {
/* 1731 */         localDate = localDate.minusDays(1L);
/* 1732 */       } else if (localDate.isBefore(this.date) && localDateTime.time.isAfter(this.time)) {
/* 1733 */         localDate = localDate.plusDays(1L);
/*      */       } 
/* 1735 */       return this.date.until(localDate, paramTemporalUnit);
/*      */     } 
/* 1737 */     return paramTemporalUnit.between(this, localDateTime);
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
/* 1751 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/* 1752 */     return paramDateTimeFormatter.format(this);
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
/*      */   public OffsetDateTime atOffset(ZoneOffset paramZoneOffset) {
/* 1766 */     return OffsetDateTime.of(this, paramZoneOffset);
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
/*      */   public ZonedDateTime atZone(ZoneId paramZoneId) {
/* 1800 */     return ZonedDateTime.of(this, paramZoneId);
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
/*      */   public int compareTo(ChronoLocalDateTime<?> paramChronoLocalDateTime) {
/* 1820 */     if (paramChronoLocalDateTime instanceof LocalDateTime) {
/* 1821 */       return compareTo0((LocalDateTime)paramChronoLocalDateTime);
/*      */     }
/* 1823 */     return super.compareTo(paramChronoLocalDateTime);
/*      */   }
/*      */   
/*      */   private int compareTo0(LocalDateTime paramLocalDateTime) {
/* 1827 */     int i = this.date.compareTo0(paramLocalDateTime.toLocalDate());
/* 1828 */     if (i == 0) {
/* 1829 */       i = this.time.compareTo(paramLocalDateTime.toLocalTime());
/*      */     }
/* 1831 */     return i;
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
/*      */   public boolean isAfter(ChronoLocalDateTime<?> paramChronoLocalDateTime) {
/* 1857 */     if (paramChronoLocalDateTime instanceof LocalDateTime) {
/* 1858 */       return (compareTo0((LocalDateTime)paramChronoLocalDateTime) > 0);
/*      */     }
/* 1860 */     return super.isAfter(paramChronoLocalDateTime);
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
/*      */   public boolean isBefore(ChronoLocalDateTime<?> paramChronoLocalDateTime) {
/* 1886 */     if (paramChronoLocalDateTime instanceof LocalDateTime) {
/* 1887 */       return (compareTo0((LocalDateTime)paramChronoLocalDateTime) < 0);
/*      */     }
/* 1889 */     return super.isBefore(paramChronoLocalDateTime);
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
/*      */   public boolean isEqual(ChronoLocalDateTime<?> paramChronoLocalDateTime) {
/* 1915 */     if (paramChronoLocalDateTime instanceof LocalDateTime) {
/* 1916 */       return (compareTo0((LocalDateTime)paramChronoLocalDateTime) == 0);
/*      */     }
/* 1918 */     return super.isEqual(paramChronoLocalDateTime);
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
/* 1933 */     if (this == paramObject) {
/* 1934 */       return true;
/*      */     }
/* 1936 */     if (paramObject instanceof LocalDateTime) {
/* 1937 */       LocalDateTime localDateTime = (LocalDateTime)paramObject;
/* 1938 */       return (this.date.equals(localDateTime.date) && this.time.equals(localDateTime.time));
/*      */     } 
/* 1940 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1950 */     return this.date.hashCode() ^ this.time.hashCode();
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
/* 1972 */     return this.date.toString() + 'T' + this.time.toString();
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
/* 1989 */     return new Ser((byte)5, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 1999 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*      */   }
/*      */   
/*      */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 2003 */     this.date.writeExternal(paramDataOutput);
/* 2004 */     this.time.writeExternal(paramDataOutput);
/*      */   }
/*      */   
/*      */   static LocalDateTime readExternal(DataInput paramDataInput) throws IOException {
/* 2008 */     LocalDate localDate = LocalDate.readExternal(paramDataInput);
/* 2009 */     LocalTime localTime = LocalTime.readExternal(paramDataInput);
/* 2010 */     return of(localDate, localTime);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/LocalDateTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */