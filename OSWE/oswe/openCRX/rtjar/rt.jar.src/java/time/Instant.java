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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Instant
/*      */   implements Temporal, TemporalAdjuster, Comparable<Instant>, Serializable
/*      */ {
/*  213 */   public static final Instant EPOCH = new Instant(0L, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long MIN_SECOND = -31557014167219200L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long MAX_SECOND = 31556889864403199L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  232 */   public static final Instant MIN = ofEpochSecond(-31557014167219200L, 0L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  243 */   public static final Instant MAX = ofEpochSecond(31556889864403199L, 999999999L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = -665713676816604388L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final long seconds;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int nanos;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Instant now() {
/*  273 */     return Clock.systemUTC().instant();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Instant now(Clock paramClock) {
/*  288 */     Objects.requireNonNull(paramClock, "clock");
/*  289 */     return paramClock.instant();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Instant ofEpochSecond(long paramLong) {
/*  304 */     return create(paramLong, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Instant ofEpochSecond(long paramLong1, long paramLong2) {
/*  328 */     long l = Math.addExact(paramLong1, Math.floorDiv(paramLong2, 1000000000L));
/*  329 */     int i = (int)Math.floorMod(paramLong2, 1000000000L);
/*  330 */     return create(l, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Instant ofEpochMilli(long paramLong) {
/*  344 */     long l = Math.floorDiv(paramLong, 1000L);
/*  345 */     int i = (int)Math.floorMod(paramLong, 1000L);
/*  346 */     return create(l, i * 1000000);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Instant from(TemporalAccessor paramTemporalAccessor) {
/*  368 */     if (paramTemporalAccessor instanceof Instant) {
/*  369 */       return (Instant)paramTemporalAccessor;
/*      */     }
/*  371 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/*      */     try {
/*  373 */       long l = paramTemporalAccessor.getLong(ChronoField.INSTANT_SECONDS);
/*  374 */       int i = paramTemporalAccessor.get(ChronoField.NANO_OF_SECOND);
/*  375 */       return ofEpochSecond(l, i);
/*  376 */     } catch (DateTimeException dateTimeException) {
/*  377 */       throw new DateTimeException("Unable to obtain Instant from TemporalAccessor: " + paramTemporalAccessor + " of type " + paramTemporalAccessor
/*  378 */           .getClass().getName(), dateTimeException);
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
/*      */   public static Instant parse(CharSequence paramCharSequence) {
/*  395 */     return DateTimeFormatter.ISO_INSTANT.<Instant>parse(paramCharSequence, Instant::from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Instant create(long paramLong, int paramInt) {
/*  407 */     if ((paramLong | paramInt) == 0L) {
/*  408 */       return EPOCH;
/*      */     }
/*  410 */     if (paramLong < -31557014167219200L || paramLong > 31556889864403199L) {
/*  411 */       throw new DateTimeException("Instant exceeds minimum or maximum instant");
/*      */     }
/*  413 */     return new Instant(paramLong, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Instant(long paramLong, int paramInt) {
/*  425 */     this.seconds = paramLong;
/*  426 */     this.nanos = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  458 */     if (paramTemporalField instanceof ChronoField) {
/*  459 */       return (paramTemporalField == ChronoField.INSTANT_SECONDS || paramTemporalField == ChronoField.NANO_OF_SECOND || paramTemporalField == ChronoField.MICRO_OF_SECOND || paramTemporalField == ChronoField.MILLI_OF_SECOND);
/*      */     }
/*  461 */     return (paramTemporalField != null && paramTemporalField.isSupportedBy(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  495 */     if (paramTemporalUnit instanceof ChronoUnit) {
/*  496 */       return (paramTemporalUnit.isTimeBased() || paramTemporalUnit == ChronoUnit.DAYS);
/*      */     }
/*  498 */     return (paramTemporalUnit != null && paramTemporalUnit.isSupportedBy(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  527 */     return super.range(paramTemporalField);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  559 */     if (paramTemporalField instanceof ChronoField) {
/*  560 */       switch ((ChronoField)paramTemporalField) { case NANOS:
/*  561 */           return this.nanos;
/*  562 */         case MICROS: return this.nanos / 1000;
/*  563 */         case MILLIS: return this.nanos / 1000000;
/*  564 */         case SECONDS: ChronoField.INSTANT_SECONDS.checkValidIntValue(this.seconds); break; }
/*      */       
/*  566 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*      */     } 
/*  568 */     return range(paramTemporalField).checkValidIntValue(paramTemporalField.getFrom(this), paramTemporalField);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  596 */     if (paramTemporalField instanceof ChronoField) {
/*  597 */       switch ((ChronoField)paramTemporalField) { case NANOS:
/*  598 */           return this.nanos;
/*  599 */         case MICROS: return (this.nanos / 1000);
/*  600 */         case MILLIS: return (this.nanos / 1000000);
/*  601 */         case SECONDS: return this.seconds; }
/*      */       
/*  603 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*      */     } 
/*  605 */     return paramTemporalField.getFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getEpochSecond() {
/*  619 */     return this.seconds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNano() {
/*  632 */     return this.nanos;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Instant with(TemporalAdjuster paramTemporalAdjuster) {
/*  656 */     return (Instant)paramTemporalAdjuster.adjustInto(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Instant with(TemporalField paramTemporalField, long paramLong) {
/*  705 */     if (paramTemporalField instanceof ChronoField) {
/*  706 */       int i; ChronoField chronoField = (ChronoField)paramTemporalField;
/*  707 */       chronoField.checkValidValue(paramLong);
/*  708 */       switch (chronoField) {
/*      */         case MILLIS:
/*  710 */           i = (int)paramLong * 1000000;
/*  711 */           return (i != this.nanos) ? create(this.seconds, i) : this;
/*      */         
/*      */         case MICROS:
/*  714 */           i = (int)paramLong * 1000;
/*  715 */           return (i != this.nanos) ? create(this.seconds, i) : this;
/*      */         case NANOS:
/*  717 */           return (paramLong != this.nanos) ? create(this.seconds, (int)paramLong) : this;
/*  718 */         case SECONDS: return (paramLong != this.seconds) ? create(paramLong, this.nanos) : this;
/*      */       } 
/*  720 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*      */     } 
/*  722 */     return paramTemporalField.<Instant>adjustInto(this, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Instant truncatedTo(TemporalUnit paramTemporalUnit) {
/*  749 */     if (paramTemporalUnit == ChronoUnit.NANOS) {
/*  750 */       return this;
/*      */     }
/*  752 */     Duration duration = paramTemporalUnit.getDuration();
/*  753 */     if (duration.getSeconds() > 86400L) {
/*  754 */       throw new UnsupportedTemporalTypeException("Unit is too large to be used for truncation");
/*      */     }
/*  756 */     long l1 = duration.toNanos();
/*  757 */     if (86400000000000L % l1 != 0L) {
/*  758 */       throw new UnsupportedTemporalTypeException("Unit must divide into a standard day without remainder");
/*      */     }
/*  760 */     long l2 = this.seconds % 86400L * 1000000000L + this.nanos;
/*  761 */     long l3 = l2 / l1 * l1;
/*  762 */     return plusNanos(l3 - l2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Instant plus(TemporalAmount paramTemporalAmount) {
/*  788 */     return (Instant)paramTemporalAmount.addTo(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Instant plus(long paramLong, TemporalUnit paramTemporalUnit) {
/*  851 */     if (paramTemporalUnit instanceof ChronoUnit) {
/*  852 */       switch ((ChronoUnit)paramTemporalUnit) { case NANOS:
/*  853 */           return plusNanos(paramLong);
/*  854 */         case MICROS: return plus(paramLong / 1000000L, paramLong % 1000000L * 1000L);
/*  855 */         case MILLIS: return plusMillis(paramLong);
/*  856 */         case SECONDS: return plusSeconds(paramLong);
/*  857 */         case MINUTES: return plusSeconds(Math.multiplyExact(paramLong, 60L));
/*  858 */         case HOURS: return plusSeconds(Math.multiplyExact(paramLong, 3600L));
/*  859 */         case HALF_DAYS: return plusSeconds(Math.multiplyExact(paramLong, 43200L));
/*  860 */         case DAYS: return plusSeconds(Math.multiplyExact(paramLong, 86400L)); }
/*      */       
/*  862 */       throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
/*      */     } 
/*  864 */     return paramTemporalUnit.<Instant>addTo(this, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Instant plusSeconds(long paramLong) {
/*  879 */     return plus(paramLong, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Instant plusMillis(long paramLong) {
/*  893 */     return plus(paramLong / 1000L, paramLong % 1000L * 1000000L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Instant plusNanos(long paramLong) {
/*  907 */     return plus(0L, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Instant plus(long paramLong1, long paramLong2) {
/*  922 */     if ((paramLong1 | paramLong2) == 0L) {
/*  923 */       return this;
/*      */     }
/*  925 */     long l1 = Math.addExact(this.seconds, paramLong1);
/*  926 */     l1 = Math.addExact(l1, paramLong2 / 1000000000L);
/*  927 */     paramLong2 %= 1000000000L;
/*  928 */     long l2 = this.nanos + paramLong2;
/*  929 */     return ofEpochSecond(l1, l2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Instant minus(TemporalAmount paramTemporalAmount) {
/*  955 */     return (Instant)paramTemporalAmount.subtractFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Instant minus(long paramLong, TemporalUnit paramTemporalUnit) {
/*  979 */     return (paramLong == Long.MIN_VALUE) ? plus(Long.MAX_VALUE, paramTemporalUnit).plus(1L, paramTemporalUnit) : plus(-paramLong, paramTemporalUnit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Instant minusSeconds(long paramLong) {
/*  994 */     if (paramLong == Long.MIN_VALUE) {
/*  995 */       return plusSeconds(Long.MAX_VALUE).plusSeconds(1L);
/*      */     }
/*  997 */     return plusSeconds(-paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Instant minusMillis(long paramLong) {
/* 1011 */     if (paramLong == Long.MIN_VALUE) {
/* 1012 */       return plusMillis(Long.MAX_VALUE).plusMillis(1L);
/*      */     }
/* 1014 */     return plusMillis(-paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Instant minusNanos(long paramLong) {
/* 1028 */     if (paramLong == Long.MIN_VALUE) {
/* 1029 */       return plusNanos(Long.MAX_VALUE).plusNanos(1L);
/*      */     }
/* 1031 */     return plusNanos(-paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1056 */     if (paramTemporalQuery == TemporalQueries.precision()) {
/* 1057 */       return (R)ChronoUnit.NANOS;
/*      */     }
/*      */     
/* 1060 */     if (paramTemporalQuery == TemporalQueries.chronology() || paramTemporalQuery == TemporalQueries.zoneId() || paramTemporalQuery == 
/* 1061 */       TemporalQueries.zone() || paramTemporalQuery == TemporalQueries.offset() || paramTemporalQuery == 
/* 1062 */       TemporalQueries.localDate() || paramTemporalQuery == TemporalQueries.localTime()) {
/* 1063 */       return null;
/*      */     }
/* 1065 */     return paramTemporalQuery.queryFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1095 */     return paramTemporal.with(ChronoField.INSTANT_SECONDS, this.seconds).with(ChronoField.NANO_OF_SECOND, this.nanos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1144 */     Instant instant = from(paramTemporal);
/* 1145 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 1146 */       ChronoUnit chronoUnit = (ChronoUnit)paramTemporalUnit;
/* 1147 */       switch (chronoUnit) { case NANOS:
/* 1148 */           return nanosUntil(instant);
/* 1149 */         case MICROS: return nanosUntil(instant) / 1000L;
/* 1150 */         case MILLIS: return Math.subtractExact(instant.toEpochMilli(), toEpochMilli());
/* 1151 */         case SECONDS: return secondsUntil(instant);
/* 1152 */         case MINUTES: return secondsUntil(instant) / 60L;
/* 1153 */         case HOURS: return secondsUntil(instant) / 3600L;
/* 1154 */         case HALF_DAYS: return secondsUntil(instant) / 43200L;
/* 1155 */         case DAYS: return secondsUntil(instant) / 86400L; }
/*      */       
/* 1157 */       throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
/*      */     } 
/* 1159 */     return paramTemporalUnit.between(this, instant);
/*      */   }
/*      */   
/*      */   private long nanosUntil(Instant paramInstant) {
/* 1163 */     long l1 = Math.subtractExact(paramInstant.seconds, this.seconds);
/* 1164 */     long l2 = Math.multiplyExact(l1, 1000000000L);
/* 1165 */     return Math.addExact(l2, (paramInstant.nanos - this.nanos));
/*      */   }
/*      */   
/*      */   private long secondsUntil(Instant paramInstant) {
/* 1169 */     long l1 = Math.subtractExact(paramInstant.seconds, this.seconds);
/* 1170 */     long l2 = (paramInstant.nanos - this.nanos);
/* 1171 */     if (l1 > 0L && l2 < 0L) {
/* 1172 */       l1--;
/* 1173 */     } else if (l1 < 0L && l2 > 0L) {
/* 1174 */       l1++;
/*      */     } 
/* 1176 */     return l1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1195 */     return OffsetDateTime.ofInstant(this, paramZoneOffset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1213 */     return ZonedDateTime.ofInstant(this, paramZoneId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long toEpochMilli() {
/* 1232 */     if (this.seconds < 0L && this.nanos > 0) {
/* 1233 */       long l1 = Math.multiplyExact(this.seconds + 1L, 1000L);
/* 1234 */       long l2 = (this.nanos / 1000000 - 1000);
/* 1235 */       return Math.addExact(l1, l2);
/*      */     } 
/* 1237 */     long l = Math.multiplyExact(this.seconds, 1000L);
/* 1238 */     return Math.addExact(l, (this.nanos / 1000000));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(Instant paramInstant) {
/* 1255 */     int i = Long.compare(this.seconds, paramInstant.seconds);
/* 1256 */     if (i != 0) {
/* 1257 */       return i;
/*      */     }
/* 1259 */     return this.nanos - paramInstant.nanos;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAfter(Instant paramInstant) {
/* 1272 */     return (compareTo(paramInstant) > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBefore(Instant paramInstant) {
/* 1285 */     return (compareTo(paramInstant) < 0);
/*      */   }
/*      */ 
/*      */ 
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
/* 1299 */     if (this == paramObject) {
/* 1300 */       return true;
/*      */     }
/* 1302 */     if (paramObject instanceof Instant) {
/* 1303 */       Instant instant = (Instant)paramObject;
/* 1304 */       return (this.seconds == instant.seconds && this.nanos == instant.nanos);
/*      */     } 
/*      */     
/* 1307 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1317 */     return (int)(this.seconds ^ this.seconds >>> 32L) + 51 * this.nanos;
/*      */   }
/*      */ 
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
/* 1330 */     return DateTimeFormatter.ISO_INSTANT.format(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1347 */     return new Ser((byte)2, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 1357 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*      */   }
/*      */   
/*      */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 1361 */     paramDataOutput.writeLong(this.seconds);
/* 1362 */     paramDataOutput.writeInt(this.nanos);
/*      */   }
/*      */   
/*      */   static Instant readExternal(DataInput paramDataInput) throws IOException {
/* 1366 */     long l = paramDataInput.readLong();
/* 1367 */     int i = paramDataInput.readInt();
/* 1368 */     return ofEpochSecond(l, i);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/Instant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */