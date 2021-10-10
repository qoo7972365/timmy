/*      */ package java.time;
/*      */ 
/*      */ import java.io.DataInput;
/*      */ import java.io.DataOutput;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
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
/*      */ public final class LocalTime
/*      */   implements Temporal, TemporalAdjuster, Comparable<LocalTime>, Serializable
/*      */ {
/*      */   public static final LocalTime MIN;
/*      */   public static final LocalTime MAX;
/*      */   public static final LocalTime MIDNIGHT;
/*      */   public static final LocalTime NOON;
/*  149 */   private static final LocalTime[] HOURS = new LocalTime[24];
/*      */   static {
/*  151 */     for (byte b = 0; b < HOURS.length; b++) {
/*  152 */       HOURS[b] = new LocalTime(b, 0, 0, 0);
/*      */     }
/*  154 */     MIDNIGHT = HOURS[0];
/*  155 */     NOON = HOURS[12];
/*  156 */     MIN = HOURS[0];
/*  157 */     MAX = new LocalTime(23, 59, 59, 999999999);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int HOURS_PER_DAY = 24;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int MINUTES_PER_HOUR = 60;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int MINUTES_PER_DAY = 1440;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int SECONDS_PER_MINUTE = 60;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int SECONDS_PER_HOUR = 3600;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int SECONDS_PER_DAY = 86400;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final long MILLIS_PER_DAY = 86400000L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final long MICROS_PER_DAY = 86400000000L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final long NANOS_PER_SECOND = 1000000000L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final long NANOS_PER_MINUTE = 60000000000L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final long NANOS_PER_HOUR = 3600000000000L;
/*      */ 
/*      */ 
/*      */   
/*      */   static final long NANOS_PER_DAY = 86400000000000L;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 6414437269572265201L;
/*      */ 
/*      */ 
/*      */   
/*      */   private final byte hour;
/*      */ 
/*      */ 
/*      */   
/*      */   private final byte minute;
/*      */ 
/*      */ 
/*      */   
/*      */   private final byte second;
/*      */ 
/*      */ 
/*      */   
/*      */   private final int nano;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LocalTime now() {
/*  244 */     return now(Clock.systemDefaultZone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LocalTime now(ZoneId paramZoneId) {
/*  260 */     return now(Clock.system(paramZoneId));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LocalTime now(Clock paramClock) {
/*  274 */     Objects.requireNonNull(paramClock, "clock");
/*      */     
/*  276 */     Instant instant = paramClock.instant();
/*  277 */     ZoneOffset zoneOffset = paramClock.getZone().getRules().getOffset(instant);
/*  278 */     long l = instant.getEpochSecond() + zoneOffset.getTotalSeconds();
/*  279 */     int i = (int)Math.floorMod(l, 86400L);
/*  280 */     return ofNanoOfDay(i * 1000000000L + instant.getNano());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LocalTime of(int paramInt1, int paramInt2) {
/*  296 */     ChronoField.HOUR_OF_DAY.checkValidValue(paramInt1);
/*  297 */     if (paramInt2 == 0) {
/*  298 */       return HOURS[paramInt1];
/*      */     }
/*  300 */     ChronoField.MINUTE_OF_HOUR.checkValidValue(paramInt2);
/*  301 */     return new LocalTime(paramInt1, paramInt2, 0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LocalTime of(int paramInt1, int paramInt2, int paramInt3) {
/*  317 */     ChronoField.HOUR_OF_DAY.checkValidValue(paramInt1);
/*  318 */     if ((paramInt2 | paramInt3) == 0) {
/*  319 */       return HOURS[paramInt1];
/*      */     }
/*  321 */     ChronoField.MINUTE_OF_HOUR.checkValidValue(paramInt2);
/*  322 */     ChronoField.SECOND_OF_MINUTE.checkValidValue(paramInt3);
/*  323 */     return new LocalTime(paramInt1, paramInt2, paramInt3, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LocalTime of(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  339 */     ChronoField.HOUR_OF_DAY.checkValidValue(paramInt1);
/*  340 */     ChronoField.MINUTE_OF_HOUR.checkValidValue(paramInt2);
/*  341 */     ChronoField.SECOND_OF_MINUTE.checkValidValue(paramInt3);
/*  342 */     ChronoField.NANO_OF_SECOND.checkValidValue(paramInt4);
/*  343 */     return create(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LocalTime ofSecondOfDay(long paramLong) {
/*  358 */     ChronoField.SECOND_OF_DAY.checkValidValue(paramLong);
/*  359 */     int i = (int)(paramLong / 3600L);
/*  360 */     paramLong -= (i * 3600);
/*  361 */     int j = (int)(paramLong / 60L);
/*  362 */     paramLong -= (j * 60);
/*  363 */     return create(i, j, (int)paramLong, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LocalTime ofNanoOfDay(long paramLong) {
/*  376 */     ChronoField.NANO_OF_DAY.checkValidValue(paramLong);
/*  377 */     int i = (int)(paramLong / 3600000000000L);
/*  378 */     paramLong -= i * 3600000000000L;
/*  379 */     int j = (int)(paramLong / 60000000000L);
/*  380 */     paramLong -= j * 60000000000L;
/*  381 */     int k = (int)(paramLong / 1000000000L);
/*  382 */     paramLong -= k * 1000000000L;
/*  383 */     return create(i, j, k, (int)paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LocalTime from(TemporalAccessor paramTemporalAccessor) {
/*  405 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/*  406 */     LocalTime localTime = paramTemporalAccessor.<LocalTime>query(TemporalQueries.localTime());
/*  407 */     if (localTime == null) {
/*  408 */       throw new DateTimeException("Unable to obtain LocalTime from TemporalAccessor: " + paramTemporalAccessor + " of type " + paramTemporalAccessor
/*  409 */           .getClass().getName());
/*      */     }
/*  411 */     return localTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LocalTime parse(CharSequence paramCharSequence) {
/*  426 */     return parse(paramCharSequence, DateTimeFormatter.ISO_LOCAL_TIME);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LocalTime parse(CharSequence paramCharSequence, DateTimeFormatter paramDateTimeFormatter) {
/*  440 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/*  441 */     return paramDateTimeFormatter.<LocalTime>parse(paramCharSequence, LocalTime::from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static LocalTime create(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  457 */     if ((paramInt2 | paramInt3 | paramInt4) == 0) {
/*  458 */       return HOURS[paramInt1];
/*      */     }
/*  460 */     return new LocalTime(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LocalTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  472 */     this.hour = (byte)paramInt1;
/*  473 */     this.minute = (byte)paramInt2;
/*  474 */     this.second = (byte)paramInt3;
/*  475 */     this.nano = paramInt4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  518 */     if (paramTemporalField instanceof ChronoField) {
/*  519 */       return paramTemporalField.isTimeBased();
/*      */     }
/*  521 */     return (paramTemporalField != null && paramTemporalField.isSupportedBy(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  554 */     if (paramTemporalUnit instanceof ChronoUnit) {
/*  555 */       return paramTemporalUnit.isTimeBased();
/*      */     }
/*  557 */     return (paramTemporalUnit != null && paramTemporalUnit.isSupportedBy(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  586 */     return super.range(paramTemporalField);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  618 */     if (paramTemporalField instanceof ChronoField) {
/*  619 */       return get0(paramTemporalField);
/*      */     }
/*  621 */     return super.get(paramTemporalField);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  649 */     if (paramTemporalField instanceof ChronoField) {
/*  650 */       if (paramTemporalField == ChronoField.NANO_OF_DAY) {
/*  651 */         return toNanoOfDay();
/*      */       }
/*  653 */       if (paramTemporalField == ChronoField.MICRO_OF_DAY) {
/*  654 */         return toNanoOfDay() / 1000L;
/*      */       }
/*  656 */       return get0(paramTemporalField);
/*      */     } 
/*  658 */     return paramTemporalField.getFrom(this);
/*      */   }
/*      */   private int get0(TemporalField paramTemporalField) {
/*      */     int i;
/*  662 */     switch ((ChronoField)paramTemporalField) { case NANOS:
/*  663 */         return this.nano;
/*  664 */       case MICROS: throw new UnsupportedTemporalTypeException("Invalid field 'NanoOfDay' for get() method, use getLong() instead");
/*  665 */       case MILLIS: return this.nano / 1000;
/*  666 */       case SECONDS: throw new UnsupportedTemporalTypeException("Invalid field 'MicroOfDay' for get() method, use getLong() instead");
/*  667 */       case MINUTES: return this.nano / 1000000;
/*  668 */       case HOURS: return (int)(toNanoOfDay() / 1000000L);
/*  669 */       case HALF_DAYS: return this.second;
/*  670 */       case null: return toSecondOfDay();
/*  671 */       case null: return this.minute;
/*  672 */       case null: return this.hour * 60 + this.minute;
/*  673 */       case null: return this.hour % 12;
/*  674 */       case null: i = this.hour % 12; return (i % 12 == 0) ? 12 : i;
/*  675 */       case null: return this.hour;
/*  676 */       case null: return (this.hour == 0) ? 24 : this.hour;
/*  677 */       case null: return this.hour / 12; }
/*      */     
/*  679 */     throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHour() {
/*  689 */     return this.hour;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinute() {
/*  698 */     return this.minute;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSecond() {
/*  707 */     return this.second;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNano() {
/*  716 */     return this.nano;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime with(TemporalAdjuster paramTemporalAdjuster) {
/*  744 */     if (paramTemporalAdjuster instanceof LocalTime) {
/*  745 */       return (LocalTime)paramTemporalAdjuster;
/*      */     }
/*  747 */     return (LocalTime)paramTemporalAdjuster.adjustInto(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime with(TemporalField paramTemporalField, long paramLong) {
/*  834 */     if (paramTemporalField instanceof ChronoField) {
/*  835 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/*  836 */       chronoField.checkValidValue(paramLong);
/*  837 */       switch (chronoField) { case NANOS:
/*  838 */           return withNano((int)paramLong);
/*  839 */         case MICROS: return ofNanoOfDay(paramLong);
/*  840 */         case MILLIS: return withNano((int)paramLong * 1000);
/*  841 */         case SECONDS: return ofNanoOfDay(paramLong * 1000L);
/*  842 */         case MINUTES: return withNano((int)paramLong * 1000000);
/*  843 */         case HOURS: return ofNanoOfDay(paramLong * 1000000L);
/*  844 */         case HALF_DAYS: return withSecond((int)paramLong);
/*  845 */         case null: return plusSeconds(paramLong - toSecondOfDay());
/*  846 */         case null: return withMinute((int)paramLong);
/*  847 */         case null: return plusMinutes(paramLong - (this.hour * 60 + this.minute));
/*  848 */         case null: return plusHours(paramLong - (this.hour % 12));
/*  849 */         case null: return plusHours(((paramLong == 12L) ? 0L : paramLong) - (this.hour % 12));
/*  850 */         case null: return withHour((int)paramLong);
/*  851 */         case null: return withHour((int)((paramLong == 24L) ? 0L : paramLong));
/*  852 */         case null: return plusHours((paramLong - (this.hour / 12)) * 12L); }
/*      */       
/*  854 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*      */     } 
/*  856 */     return paramTemporalField.<LocalTime>adjustInto(this, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime withHour(int paramInt) {
/*  870 */     if (this.hour == paramInt) {
/*  871 */       return this;
/*      */     }
/*  873 */     ChronoField.HOUR_OF_DAY.checkValidValue(paramInt);
/*  874 */     return create(paramInt, this.minute, this.second, this.nano);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime withMinute(int paramInt) {
/*  887 */     if (this.minute == paramInt) {
/*  888 */       return this;
/*      */     }
/*  890 */     ChronoField.MINUTE_OF_HOUR.checkValidValue(paramInt);
/*  891 */     return create(this.hour, paramInt, this.second, this.nano);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime withSecond(int paramInt) {
/*  904 */     if (this.second == paramInt) {
/*  905 */       return this;
/*      */     }
/*  907 */     ChronoField.SECOND_OF_MINUTE.checkValidValue(paramInt);
/*  908 */     return create(this.hour, this.minute, paramInt, this.nano);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime withNano(int paramInt) {
/*  921 */     if (this.nano == paramInt) {
/*  922 */       return this;
/*      */     }
/*  924 */     ChronoField.NANO_OF_SECOND.checkValidValue(paramInt);
/*  925 */     return create(this.hour, this.minute, this.second, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime truncatedTo(TemporalUnit paramTemporalUnit) {
/*  950 */     if (paramTemporalUnit == ChronoUnit.NANOS) {
/*  951 */       return this;
/*      */     }
/*  953 */     Duration duration = paramTemporalUnit.getDuration();
/*  954 */     if (duration.getSeconds() > 86400L) {
/*  955 */       throw new UnsupportedTemporalTypeException("Unit is too large to be used for truncation");
/*      */     }
/*  957 */     long l1 = duration.toNanos();
/*  958 */     if (86400000000000L % l1 != 0L) {
/*  959 */       throw new UnsupportedTemporalTypeException("Unit must divide into a standard day without remainder");
/*      */     }
/*  961 */     long l2 = toNanoOfDay();
/*  962 */     return ofNanoOfDay(l2 / l1 * l1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime plus(TemporalAmount paramTemporalAmount) {
/*  988 */     return (LocalTime)paramTemporalAmount.addTo(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime plus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 1045 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 1046 */       switch ((ChronoUnit)paramTemporalUnit) { case NANOS:
/* 1047 */           return plusNanos(paramLong);
/* 1048 */         case MICROS: return plusNanos(paramLong % 86400000000L * 1000L);
/* 1049 */         case MILLIS: return plusNanos(paramLong % 86400000L * 1000000L);
/* 1050 */         case SECONDS: return plusSeconds(paramLong);
/* 1051 */         case MINUTES: return plusMinutes(paramLong);
/* 1052 */         case HOURS: return plusHours(paramLong);
/* 1053 */         case HALF_DAYS: return plusHours(paramLong % 2L * 12L); }
/*      */       
/* 1055 */       throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
/*      */     } 
/* 1057 */     return paramTemporalUnit.<LocalTime>addTo(this, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime plusHours(long paramLong) {
/* 1073 */     if (paramLong == 0L) {
/* 1074 */       return this;
/*      */     }
/* 1076 */     int i = ((int)(paramLong % 24L) + this.hour + 24) % 24;
/* 1077 */     return create(i, this.minute, this.second, this.nano);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime plusMinutes(long paramLong) {
/* 1092 */     if (paramLong == 0L) {
/* 1093 */       return this;
/*      */     }
/* 1095 */     int i = this.hour * 60 + this.minute;
/* 1096 */     int j = ((int)(paramLong % 1440L) + i + 1440) % 1440;
/* 1097 */     if (i == j) {
/* 1098 */       return this;
/*      */     }
/* 1100 */     int k = j / 60;
/* 1101 */     int m = j % 60;
/* 1102 */     return create(k, m, this.second, this.nano);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime plusSeconds(long paramLong) {
/* 1117 */     if (paramLong == 0L) {
/* 1118 */       return this;
/*      */     }
/* 1120 */     int i = this.hour * 3600 + this.minute * 60 + this.second;
/*      */     
/* 1122 */     int j = ((int)(paramLong % 86400L) + i + 86400) % 86400;
/* 1123 */     if (i == j) {
/* 1124 */       return this;
/*      */     }
/* 1126 */     int k = j / 3600;
/* 1127 */     int m = j / 60 % 60;
/* 1128 */     int n = j % 60;
/* 1129 */     return create(k, m, n, this.nano);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime plusNanos(long paramLong) {
/* 1144 */     if (paramLong == 0L) {
/* 1145 */       return this;
/*      */     }
/* 1147 */     long l1 = toNanoOfDay();
/* 1148 */     long l2 = (paramLong % 86400000000000L + l1 + 86400000000000L) % 86400000000000L;
/* 1149 */     if (l1 == l2) {
/* 1150 */       return this;
/*      */     }
/* 1152 */     int i = (int)(l2 / 3600000000000L);
/* 1153 */     int j = (int)(l2 / 60000000000L % 60L);
/* 1154 */     int k = (int)(l2 / 1000000000L % 60L);
/* 1155 */     int m = (int)(l2 % 1000000000L);
/* 1156 */     return create(i, j, k, m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime minus(TemporalAmount paramTemporalAmount) {
/* 1182 */     return (LocalTime)paramTemporalAmount.subtractFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime minus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 1206 */     return (paramLong == Long.MIN_VALUE) ? plus(Long.MAX_VALUE, paramTemporalUnit).plus(1L, paramTemporalUnit) : plus(-paramLong, paramTemporalUnit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime minusHours(long paramLong) {
/* 1222 */     return plusHours(-(paramLong % 24L));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime minusMinutes(long paramLong) {
/* 1237 */     return plusMinutes(-(paramLong % 1440L));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime minusSeconds(long paramLong) {
/* 1252 */     return plusSeconds(-(paramLong % 86400L));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalTime minusNanos(long paramLong) {
/* 1267 */     return plusNanos(-(paramLong % 86400000000000L));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1292 */     if (paramTemporalQuery == TemporalQueries.chronology() || paramTemporalQuery == TemporalQueries.zoneId() || paramTemporalQuery == 
/* 1293 */       TemporalQueries.zone() || paramTemporalQuery == TemporalQueries.offset())
/* 1294 */       return null; 
/* 1295 */     if (paramTemporalQuery == TemporalQueries.localTime())
/* 1296 */       return (R)this; 
/* 1297 */     if (paramTemporalQuery == TemporalQueries.localDate())
/* 1298 */       return null; 
/* 1299 */     if (paramTemporalQuery == TemporalQueries.precision()) {
/* 1300 */       return (R)ChronoUnit.NANOS;
/*      */     }
/*      */ 
/*      */     
/* 1304 */     return paramTemporalQuery.queryFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1333 */     return paramTemporal.with(ChronoField.NANO_OF_DAY, toNanoOfDay());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1385 */     LocalTime localTime = from(paramTemporal);
/* 1386 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 1387 */       long l = localTime.toNanoOfDay() - toNanoOfDay();
/* 1388 */       switch ((ChronoUnit)paramTemporalUnit) { case NANOS:
/* 1389 */           return l;
/* 1390 */         case MICROS: return l / 1000L;
/* 1391 */         case MILLIS: return l / 1000000L;
/* 1392 */         case SECONDS: return l / 1000000000L;
/* 1393 */         case MINUTES: return l / 60000000000L;
/* 1394 */         case HOURS: return l / 3600000000000L;
/* 1395 */         case HALF_DAYS: return l / 43200000000000L; }
/*      */       
/* 1397 */       throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
/*      */     } 
/* 1399 */     return paramTemporalUnit.between(this, localTime);
/*      */   }
/*      */ 
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
/* 1412 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/* 1413 */     return paramDateTimeFormatter.format(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalDateTime atDate(LocalDate paramLocalDate) {
/* 1427 */     return LocalDateTime.of(paramLocalDate, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OffsetTime atOffset(ZoneOffset paramZoneOffset) {
/* 1440 */     return OffsetTime.of(this, paramZoneOffset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int toSecondOfDay() {
/* 1451 */     int i = this.hour * 3600;
/* 1452 */     i += this.minute * 60;
/* 1453 */     i += this.second;
/* 1454 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long toNanoOfDay() {
/* 1464 */     long l = this.hour * 3600000000000L;
/* 1465 */     l += this.minute * 60000000000L;
/* 1466 */     l += this.second * 1000000000L;
/* 1467 */     l += this.nano;
/* 1468 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(LocalTime paramLocalTime) {
/* 1484 */     int i = Integer.compare(this.hour, paramLocalTime.hour);
/* 1485 */     if (i == 0) {
/* 1486 */       i = Integer.compare(this.minute, paramLocalTime.minute);
/* 1487 */       if (i == 0) {
/* 1488 */         i = Integer.compare(this.second, paramLocalTime.second);
/* 1489 */         if (i == 0) {
/* 1490 */           i = Integer.compare(this.nano, paramLocalTime.nano);
/*      */         }
/*      */       } 
/*      */     } 
/* 1494 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAfter(LocalTime paramLocalTime) {
/* 1507 */     return (compareTo(paramLocalTime) > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBefore(LocalTime paramLocalTime) {
/* 1520 */     return (compareTo(paramLocalTime) < 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1538 */     if (this == paramObject) {
/* 1539 */       return true;
/*      */     }
/* 1541 */     if (paramObject instanceof LocalTime) {
/* 1542 */       LocalTime localTime = (LocalTime)paramObject;
/* 1543 */       return (this.hour == localTime.hour && this.minute == localTime.minute && this.second == localTime.second && this.nano == localTime.nano);
/*      */     } 
/*      */     
/* 1546 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1556 */     long l = toNanoOfDay();
/* 1557 */     return (int)(l ^ l >>> 32L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1579 */     StringBuilder stringBuilder = new StringBuilder(18);
/* 1580 */     byte b1 = this.hour;
/* 1581 */     byte b2 = this.minute;
/* 1582 */     byte b3 = this.second;
/* 1583 */     int i = this.nano;
/* 1584 */     stringBuilder.append((b1 < 10) ? "0" : "").append(b1)
/* 1585 */       .append((b2 < 10) ? ":0" : ":").append(b2);
/* 1586 */     if (b3 > 0 || i > 0) {
/* 1587 */       stringBuilder.append((b3 < 10) ? ":0" : ":").append(b3);
/* 1588 */       if (i > 0) {
/* 1589 */         stringBuilder.append('.');
/* 1590 */         if (i % 1000000 == 0) {
/* 1591 */           stringBuilder.append(Integer.toString(i / 1000000 + 1000).substring(1));
/* 1592 */         } else if (i % 1000 == 0) {
/* 1593 */           stringBuilder.append(Integer.toString(i / 1000 + 1000000).substring(1));
/*      */         } else {
/* 1595 */           stringBuilder.append(Integer.toString(i + 1000000000).substring(1));
/*      */         } 
/*      */       } 
/*      */     } 
/* 1599 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1635 */     return new Ser((byte)4, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 1645 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*      */   }
/*      */   
/*      */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 1649 */     if (this.nano == 0) {
/* 1650 */       if (this.second == 0) {
/* 1651 */         if (this.minute == 0) {
/* 1652 */           paramDataOutput.writeByte(this.hour ^ 0xFFFFFFFF);
/*      */         } else {
/* 1654 */           paramDataOutput.writeByte(this.hour);
/* 1655 */           paramDataOutput.writeByte(this.minute ^ 0xFFFFFFFF);
/*      */         } 
/*      */       } else {
/* 1658 */         paramDataOutput.writeByte(this.hour);
/* 1659 */         paramDataOutput.writeByte(this.minute);
/* 1660 */         paramDataOutput.writeByte(this.second ^ 0xFFFFFFFF);
/*      */       } 
/*      */     } else {
/* 1663 */       paramDataOutput.writeByte(this.hour);
/* 1664 */       paramDataOutput.writeByte(this.minute);
/* 1665 */       paramDataOutput.writeByte(this.second);
/* 1666 */       paramDataOutput.writeInt(this.nano);
/*      */     } 
/*      */   }
/*      */   
/*      */   static LocalTime readExternal(DataInput paramDataInput) throws IOException {
/* 1671 */     int i = paramDataInput.readByte();
/* 1672 */     int j = 0;
/* 1673 */     int k = 0;
/* 1674 */     int m = 0;
/* 1675 */     if (i < 0) {
/* 1676 */       i = i ^ 0xFFFFFFFF;
/*      */     } else {
/* 1678 */       j = paramDataInput.readByte();
/* 1679 */       if (j < 0) {
/* 1680 */         j ^= 0xFFFFFFFF;
/*      */       } else {
/* 1682 */         k = paramDataInput.readByte();
/* 1683 */         if (k < 0) {
/* 1684 */           k ^= 0xFFFFFFFF;
/*      */         } else {
/* 1686 */           m = paramDataInput.readInt();
/*      */         } 
/*      */       } 
/*      */     } 
/* 1690 */     return of(i, j, k, m);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/LocalTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */