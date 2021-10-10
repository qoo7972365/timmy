/*      */ package java.time;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInput;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutput;
/*      */ import java.io.Serializable;
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
/*      */ public final class OffsetTime
/*      */   implements Temporal, TemporalAdjuster, Comparable<OffsetTime>, Serializable
/*      */ {
/*  128 */   public static final OffsetTime MIN = LocalTime.MIN.atOffset(ZoneOffset.MAX);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  136 */   public static final OffsetTime MAX = LocalTime.MAX.atOffset(ZoneOffset.MIN);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 7264499704384272492L;
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
/*      */   private final ZoneOffset offset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static OffsetTime now() {
/*  166 */     return now(Clock.systemDefaultZone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static OffsetTime now(ZoneId paramZoneId) {
/*  183 */     return now(Clock.system(paramZoneId));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static OffsetTime now(Clock paramClock) {
/*  199 */     Objects.requireNonNull(paramClock, "clock");
/*  200 */     Instant instant = paramClock.instant();
/*  201 */     return ofInstant(instant, paramClock.getZone().getRules().getOffset(instant));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static OffsetTime of(LocalTime paramLocalTime, ZoneOffset paramZoneOffset) {
/*  213 */     return new OffsetTime(paramLocalTime, paramZoneOffset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static OffsetTime of(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ZoneOffset paramZoneOffset) {
/*  236 */     return new OffsetTime(LocalTime.of(paramInt1, paramInt2, paramInt3, paramInt4), paramZoneOffset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static OffsetTime ofInstant(Instant paramInstant, ZoneId paramZoneId) {
/*  256 */     Objects.requireNonNull(paramInstant, "instant");
/*  257 */     Objects.requireNonNull(paramZoneId, "zone");
/*  258 */     ZoneRules zoneRules = paramZoneId.getRules();
/*  259 */     ZoneOffset zoneOffset = zoneRules.getOffset(paramInstant);
/*  260 */     long l = paramInstant.getEpochSecond() + zoneOffset.getTotalSeconds();
/*  261 */     int i = (int)Math.floorMod(l, 86400L);
/*  262 */     LocalTime localTime = LocalTime.ofNanoOfDay(i * 1000000000L + paramInstant.getNano());
/*  263 */     return new OffsetTime(localTime, zoneOffset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static OffsetTime from(TemporalAccessor paramTemporalAccessor) {
/*  287 */     if (paramTemporalAccessor instanceof OffsetTime) {
/*  288 */       return (OffsetTime)paramTemporalAccessor;
/*      */     }
/*      */     try {
/*  291 */       LocalTime localTime = LocalTime.from(paramTemporalAccessor);
/*  292 */       ZoneOffset zoneOffset = ZoneOffset.from(paramTemporalAccessor);
/*  293 */       return new OffsetTime(localTime, zoneOffset);
/*  294 */     } catch (DateTimeException dateTimeException) {
/*  295 */       throw new DateTimeException("Unable to obtain OffsetTime from TemporalAccessor: " + paramTemporalAccessor + " of type " + paramTemporalAccessor
/*  296 */           .getClass().getName(), dateTimeException);
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
/*      */   public static OffsetTime parse(CharSequence paramCharSequence) {
/*  312 */     return parse(paramCharSequence, DateTimeFormatter.ISO_OFFSET_TIME);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static OffsetTime parse(CharSequence paramCharSequence, DateTimeFormatter paramDateTimeFormatter) {
/*  326 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/*  327 */     return paramDateTimeFormatter.<OffsetTime>parse(paramCharSequence, OffsetTime::from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private OffsetTime(LocalTime paramLocalTime, ZoneOffset paramZoneOffset) {
/*  338 */     this.time = Objects.<LocalTime>requireNonNull(paramLocalTime, "time");
/*  339 */     this.offset = Objects.<ZoneOffset>requireNonNull(paramZoneOffset, "offset");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private OffsetTime with(LocalTime paramLocalTime, ZoneOffset paramZoneOffset) {
/*  349 */     if (this.time == paramLocalTime && this.offset.equals(paramZoneOffset)) {
/*  350 */       return this;
/*      */     }
/*  352 */     return new OffsetTime(paramLocalTime, paramZoneOffset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  396 */     if (paramTemporalField instanceof ChronoField) {
/*  397 */       return (paramTemporalField.isTimeBased() || paramTemporalField == ChronoField.OFFSET_SECONDS);
/*      */     }
/*  399 */     return (paramTemporalField != null && paramTemporalField.isSupportedBy(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  432 */     if (paramTemporalUnit instanceof ChronoUnit) {
/*  433 */       return paramTemporalUnit.isTimeBased();
/*      */     }
/*  435 */     return (paramTemporalUnit != null && paramTemporalUnit.isSupportedBy(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  464 */     if (paramTemporalField instanceof ChronoField) {
/*  465 */       if (paramTemporalField == ChronoField.OFFSET_SECONDS) {
/*  466 */         return paramTemporalField.range();
/*      */       }
/*  468 */       return this.time.range(paramTemporalField);
/*      */     } 
/*  470 */     return paramTemporalField.rangeRefinedBy(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  502 */     return super.get(paramTemporalField);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  530 */     if (paramTemporalField instanceof ChronoField) {
/*  531 */       if (paramTemporalField == ChronoField.OFFSET_SECONDS) {
/*  532 */         return this.offset.getTotalSeconds();
/*      */       }
/*  534 */       return this.time.getLong(paramTemporalField);
/*      */     } 
/*  536 */     return paramTemporalField.getFrom(this);
/*      */   }
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
/*  548 */     return this.offset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime withOffsetSameLocal(ZoneOffset paramZoneOffset) {
/*  569 */     return (paramZoneOffset != null && paramZoneOffset.equals(this.offset)) ? this : new OffsetTime(this.time, paramZoneOffset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime withOffsetSameInstant(ZoneOffset paramZoneOffset) {
/*  591 */     if (paramZoneOffset.equals(this.offset)) {
/*  592 */       return this;
/*      */     }
/*  594 */     int i = paramZoneOffset.getTotalSeconds() - this.offset.getTotalSeconds();
/*  595 */     LocalTime localTime = this.time.plusSeconds(i);
/*  596 */     return new OffsetTime(localTime, paramZoneOffset);
/*      */   }
/*      */ 
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
/*  609 */     return this.time;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHour() {
/*  619 */     return this.time.getHour();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinute() {
/*  628 */     return this.time.getMinute();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSecond() {
/*  637 */     return this.time.getSecond();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNano() {
/*  646 */     return this.time.getNano();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime with(TemporalAdjuster paramTemporalAdjuster) {
/*  681 */     if (paramTemporalAdjuster instanceof LocalTime)
/*  682 */       return with((LocalTime)paramTemporalAdjuster, this.offset); 
/*  683 */     if (paramTemporalAdjuster instanceof ZoneOffset)
/*  684 */       return with(this.time, (ZoneOffset)paramTemporalAdjuster); 
/*  685 */     if (paramTemporalAdjuster instanceof OffsetTime) {
/*  686 */       return (OffsetTime)paramTemporalAdjuster;
/*      */     }
/*  688 */     return (OffsetTime)paramTemporalAdjuster.adjustInto(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime with(TemporalField paramTemporalField, long paramLong) {
/*  728 */     if (paramTemporalField instanceof ChronoField) {
/*  729 */       if (paramTemporalField == ChronoField.OFFSET_SECONDS) {
/*  730 */         ChronoField chronoField = (ChronoField)paramTemporalField;
/*  731 */         return with(this.time, ZoneOffset.ofTotalSeconds(chronoField.checkValidIntValue(paramLong)));
/*      */       } 
/*  733 */       return with(this.time.with(paramTemporalField, paramLong), this.offset);
/*      */     } 
/*  735 */     return paramTemporalField.<OffsetTime>adjustInto(this, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime withHour(int paramInt) {
/*  751 */     return with(this.time.withHour(paramInt), this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime withMinute(int paramInt) {
/*  766 */     return with(this.time.withMinute(paramInt), this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime withSecond(int paramInt) {
/*  781 */     return with(this.time.withSecond(paramInt), this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime withNano(int paramInt) {
/*  796 */     return with(this.time.withNano(paramInt), this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime truncatedTo(TemporalUnit paramTemporalUnit) {
/*  823 */     return with(this.time.truncatedTo(paramTemporalUnit), this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime plus(TemporalAmount paramTemporalAmount) {
/*  849 */     return (OffsetTime)paramTemporalAmount.addTo(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime plus(long paramLong, TemporalUnit paramTemporalUnit) {
/*  879 */     if (paramTemporalUnit instanceof ChronoUnit) {
/*  880 */       return with(this.time.plus(paramLong, paramTemporalUnit), this.offset);
/*      */     }
/*  882 */     return paramTemporalUnit.<OffsetTime>addTo(this, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime plusHours(long paramLong) {
/*  898 */     return with(this.time.plusHours(paramLong), this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime plusMinutes(long paramLong) {
/*  913 */     return with(this.time.plusMinutes(paramLong), this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime plusSeconds(long paramLong) {
/*  928 */     return with(this.time.plusSeconds(paramLong), this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime plusNanos(long paramLong) {
/*  943 */     return with(this.time.plusNanos(paramLong), this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime minus(TemporalAmount paramTemporalAmount) {
/*  969 */     return (OffsetTime)paramTemporalAmount.subtractFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime minus(long paramLong, TemporalUnit paramTemporalUnit) {
/*  993 */     return (paramLong == Long.MIN_VALUE) ? plus(Long.MAX_VALUE, paramTemporalUnit).plus(1L, paramTemporalUnit) : plus(-paramLong, paramTemporalUnit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime minusHours(long paramLong) {
/* 1009 */     return with(this.time.minusHours(paramLong), this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime minusMinutes(long paramLong) {
/* 1024 */     return with(this.time.minusMinutes(paramLong), this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime minusSeconds(long paramLong) {
/* 1039 */     return with(this.time.minusSeconds(paramLong), this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime minusNanos(long paramLong) {
/* 1054 */     return with(this.time.minusNanos(paramLong), this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1079 */     if (paramTemporalQuery == TemporalQueries.offset() || paramTemporalQuery == TemporalQueries.zone())
/* 1080 */       return (R)this.offset; 
/* 1081 */     if ((((paramTemporalQuery == TemporalQueries.zoneId()) ? 1 : 0) | ((paramTemporalQuery == TemporalQueries.chronology()) ? 1 : 0)) != 0 || paramTemporalQuery == TemporalQueries.localDate())
/* 1082 */       return null; 
/* 1083 */     if (paramTemporalQuery == TemporalQueries.localTime())
/* 1084 */       return (R)this.time; 
/* 1085 */     if (paramTemporalQuery == TemporalQueries.precision()) {
/* 1086 */       return (R)ChronoUnit.NANOS;
/*      */     }
/*      */ 
/*      */     
/* 1090 */     return paramTemporalQuery.queryFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1121 */     return paramTemporal
/* 1122 */       .with(ChronoField.NANO_OF_DAY, this.time.toNanoOfDay())
/* 1123 */       .with(ChronoField.OFFSET_SECONDS, this.offset.getTotalSeconds());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1178 */     OffsetTime offsetTime = from(paramTemporal);
/* 1179 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 1180 */       long l = offsetTime.toEpochNano() - toEpochNano();
/* 1181 */       switch ((ChronoUnit)paramTemporalUnit) { case NANOS:
/* 1182 */           return l;
/* 1183 */         case MICROS: return l / 1000L;
/* 1184 */         case MILLIS: return l / 1000000L;
/* 1185 */         case SECONDS: return l / 1000000000L;
/* 1186 */         case MINUTES: return l / 60000000000L;
/* 1187 */         case HOURS: return l / 3600000000000L;
/* 1188 */         case HALF_DAYS: return l / 43200000000000L; }
/*      */       
/* 1190 */       throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
/*      */     } 
/* 1192 */     return paramTemporalUnit.between(this, offsetTime);
/*      */   }
/*      */ 
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
/* 1205 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/* 1206 */     return paramDateTimeFormatter.format(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetDateTime atDate(LocalDate paramLocalDate) {
/* 1220 */     return OffsetDateTime.of(paramLocalDate, this.time, this.offset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private long toEpochNano() {
/* 1230 */     long l1 = this.time.toNanoOfDay();
/* 1231 */     long l2 = this.offset.getTotalSeconds() * 1000000000L;
/* 1232 */     return l1 - l2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(OffsetTime paramOffsetTime) {
/* 1265 */     if (this.offset.equals(paramOffsetTime.offset)) {
/* 1266 */       return this.time.compareTo(paramOffsetTime.time);
/*      */     }
/* 1268 */     int i = Long.compare(toEpochNano(), paramOffsetTime.toEpochNano());
/* 1269 */     if (i == 0) {
/* 1270 */       i = this.time.compareTo(paramOffsetTime.time);
/*      */     }
/* 1272 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAfter(OffsetTime paramOffsetTime) {
/* 1288 */     return (toEpochNano() > paramOffsetTime.toEpochNano());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBefore(OffsetTime paramOffsetTime) {
/* 1303 */     return (toEpochNano() < paramOffsetTime.toEpochNano());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEqual(OffsetTime paramOffsetTime) {
/* 1318 */     return (toEpochNano() == paramOffsetTime.toEpochNano());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1337 */     if (this == paramObject) {
/* 1338 */       return true;
/*      */     }
/* 1340 */     if (paramObject instanceof OffsetTime) {
/* 1341 */       OffsetTime offsetTime = (OffsetTime)paramObject;
/* 1342 */       return (this.time.equals(offsetTime.time) && this.offset.equals(offsetTime.offset));
/*      */     } 
/* 1344 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1354 */     return this.time.hashCode() ^ this.offset.hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1376 */     return this.time.toString() + this.offset.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1393 */     return new Ser((byte)9, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 1403 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*      */   }
/*      */   
/*      */   void writeExternal(ObjectOutput paramObjectOutput) throws IOException {
/* 1407 */     this.time.writeExternal(paramObjectOutput);
/* 1408 */     this.offset.writeExternal(paramObjectOutput);
/*      */   }
/*      */   
/*      */   static OffsetTime readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 1412 */     LocalTime localTime = LocalTime.readExternal(paramObjectInput);
/* 1413 */     ZoneOffset zoneOffset = ZoneOffset.readExternal(paramObjectInput);
/* 1414 */     return of(localTime, zoneOffset);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/OffsetTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */