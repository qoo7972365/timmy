/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.time.Clock;
/*     */ import java.time.LocalDate;
/*     */ import java.time.LocalTime;
/*     */ import java.time.Period;
/*     */ import java.time.ZoneId;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.Temporal;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalAdjuster;
/*     */ import java.time.temporal.TemporalAmount;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.TemporalUnit;
/*     */ import java.time.temporal.UnsupportedTemporalTypeException;
/*     */ import java.time.temporal.ValueRange;
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
/*     */ public final class MinguoDate
/*     */   extends ChronoLocalDateImpl<MinguoDate>
/*     */   implements ChronoLocalDate, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1300372329181994526L;
/*     */   private final transient LocalDate isoDate;
/*     */   
/*     */   public static MinguoDate now() {
/* 133 */     return now(Clock.systemDefaultZone());
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
/*     */   public static MinguoDate now(ZoneId paramZoneId) {
/* 149 */     return now(Clock.system(paramZoneId));
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
/*     */   public static MinguoDate now(Clock paramClock) {
/* 164 */     return new MinguoDate(LocalDate.now(paramClock));
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
/*     */   public static MinguoDate of(int paramInt1, int paramInt2, int paramInt3) {
/* 182 */     return new MinguoDate(LocalDate.of(paramInt1 + 1911, paramInt2, paramInt3));
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
/*     */   public static MinguoDate from(TemporalAccessor paramTemporalAccessor) {
/* 203 */     return MinguoChronology.INSTANCE.date(paramTemporalAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MinguoDate(LocalDate paramLocalDate) {
/* 213 */     Objects.requireNonNull(paramLocalDate, "isoDate");
/* 214 */     this.isoDate = paramLocalDate;
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
/*     */   public MinguoChronology getChronology() {
/* 228 */     return MinguoChronology.INSTANCE;
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
/*     */   public MinguoEra getEra() {
/* 241 */     return (getProlepticYear() >= 1) ? MinguoEra.ROC : MinguoEra.BEFORE_ROC;
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
/*     */   public int lengthOfMonth() {
/* 254 */     return this.isoDate.lengthOfMonth();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueRange range(TemporalField paramTemporalField) {
/* 260 */     if (paramTemporalField instanceof ChronoField) {
/* 261 */       if (isSupported(paramTemporalField)) {
/* 262 */         ValueRange valueRange; long l; ChronoField chronoField = (ChronoField)paramTemporalField;
/* 263 */         switch (chronoField) {
/*     */           case DAY_OF_MONTH:
/*     */           case DAY_OF_YEAR:
/*     */           case ALIGNED_WEEK_OF_MONTH:
/* 267 */             return this.isoDate.range(paramTemporalField);
/*     */           case YEAR_OF_ERA:
/* 269 */             valueRange = ChronoField.YEAR.range();
/* 270 */             l = (getProlepticYear() <= 0) ? (-valueRange.getMinimum() + 1L + 1911L) : (valueRange.getMaximum() - 1911L);
/* 271 */             return ValueRange.of(1L, l);
/*     */         } 
/*     */         
/* 274 */         return getChronology().range(chronoField);
/*     */       } 
/* 276 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*     */     } 
/* 278 */     return paramTemporalField.rangeRefinedBy(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(TemporalField paramTemporalField) {
/* 283 */     if (paramTemporalField instanceof ChronoField) {
/* 284 */       int i; switch ((ChronoField)paramTemporalField) {
/*     */         case PROLEPTIC_MONTH:
/* 286 */           return getProlepticMonth();
/*     */         case YEAR_OF_ERA:
/* 288 */           i = getProlepticYear();
/* 289 */           return ((i >= 1) ? i : (1 - i));
/*     */         
/*     */         case YEAR:
/* 292 */           return getProlepticYear();
/*     */         case ERA:
/* 294 */           return ((getProlepticYear() >= 1) ? 1L : 0L);
/*     */       } 
/* 296 */       return this.isoDate.getLong(paramTemporalField);
/*     */     } 
/* 298 */     return paramTemporalField.getFrom(this);
/*     */   }
/*     */   
/*     */   private long getProlepticMonth() {
/* 302 */     return getProlepticYear() * 12L + this.isoDate.getMonthValue() - 1L;
/*     */   }
/*     */   
/*     */   private int getProlepticYear() {
/* 306 */     return this.isoDate.getYear() - 1911;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MinguoDate with(TemporalField paramTemporalField, long paramLong) {
/* 312 */     if (paramTemporalField instanceof ChronoField) {
/* 313 */       int i; ChronoField chronoField = (ChronoField)paramTemporalField;
/* 314 */       if (getLong(chronoField) == paramLong) {
/* 315 */         return this;
/*     */       }
/* 317 */       switch (chronoField) {
/*     */         case PROLEPTIC_MONTH:
/* 319 */           getChronology().range(chronoField).checkValidValue(paramLong, chronoField);
/* 320 */           return plusMonths(paramLong - getProlepticMonth());
/*     */         case YEAR_OF_ERA:
/*     */         case YEAR:
/*     */         case ERA:
/* 324 */           i = getChronology().range(chronoField).checkValidIntValue(paramLong, chronoField);
/* 325 */           switch (chronoField) {
/*     */             case YEAR_OF_ERA:
/* 327 */               return with(this.isoDate.withYear((getProlepticYear() >= 1) ? (i + 1911) : (1 - i + 1911)));
/*     */             case YEAR:
/* 329 */               return with(this.isoDate.withYear(i + 1911));
/*     */             case ERA:
/* 331 */               return with(this.isoDate.withYear(1 - getProlepticYear() + 1911));
/*     */           } 
/*     */           break;
/*     */       } 
/* 335 */       return with(this.isoDate.with(paramTemporalField, paramLong));
/*     */     } 
/* 337 */     return super.with(paramTemporalField, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MinguoDate with(TemporalAdjuster paramTemporalAdjuster) {
/* 347 */     return super.with(paramTemporalAdjuster);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MinguoDate plus(TemporalAmount paramTemporalAmount) {
/* 357 */     return super.plus(paramTemporalAmount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MinguoDate minus(TemporalAmount paramTemporalAmount) {
/* 367 */     return super.minus(paramTemporalAmount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   MinguoDate plusYears(long paramLong) {
/* 373 */     return with(this.isoDate.plusYears(paramLong));
/*     */   }
/*     */ 
/*     */   
/*     */   MinguoDate plusMonths(long paramLong) {
/* 378 */     return with(this.isoDate.plusMonths(paramLong));
/*     */   }
/*     */ 
/*     */   
/*     */   MinguoDate plusWeeks(long paramLong) {
/* 383 */     return super.plusWeeks(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   MinguoDate plusDays(long paramLong) {
/* 388 */     return with(this.isoDate.plusDays(paramLong));
/*     */   }
/*     */ 
/*     */   
/*     */   public MinguoDate plus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 393 */     return super.plus(paramLong, paramTemporalUnit);
/*     */   }
/*     */ 
/*     */   
/*     */   public MinguoDate minus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 398 */     return super.minus(paramLong, paramTemporalUnit);
/*     */   }
/*     */ 
/*     */   
/*     */   MinguoDate minusYears(long paramLong) {
/* 403 */     return super.minusYears(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   MinguoDate minusMonths(long paramLong) {
/* 408 */     return super.minusMonths(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   MinguoDate minusWeeks(long paramLong) {
/* 413 */     return super.minusWeeks(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   MinguoDate minusDays(long paramLong) {
/* 418 */     return super.minusDays(paramLong);
/*     */   }
/*     */   
/*     */   private MinguoDate with(LocalDate paramLocalDate) {
/* 422 */     return paramLocalDate.equals(this.isoDate) ? this : new MinguoDate(paramLocalDate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final ChronoLocalDateTime<MinguoDate> atTime(LocalTime paramLocalTime) {
/* 428 */     return (ChronoLocalDateTime)super.atTime(paramLocalTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChronoPeriod until(ChronoLocalDate paramChronoLocalDate) {
/* 433 */     Period period = this.isoDate.until(paramChronoLocalDate);
/* 434 */     return getChronology().period(period.getYears(), period.getMonths(), period.getDays());
/*     */   }
/*     */ 
/*     */   
/*     */   public long toEpochDay() {
/* 439 */     return this.isoDate.toEpochDay();
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
/*     */   public boolean equals(Object paramObject) {
/* 457 */     if (this == paramObject) {
/* 458 */       return true;
/*     */     }
/* 460 */     if (paramObject instanceof MinguoDate) {
/* 461 */       MinguoDate minguoDate = (MinguoDate)paramObject;
/* 462 */       return this.isoDate.equals(minguoDate.isoDate);
/*     */     } 
/* 464 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 474 */     return getChronology().getId().hashCode() ^ this.isoDate.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 485 */     throw new InvalidObjectException("Deserialization via serialization delegate");
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
/*     */   private Object writeReplace() {
/* 502 */     return new Ser((byte)7, this);
/*     */   }
/*     */ 
/*     */   
/*     */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 507 */     paramDataOutput.writeInt(get(ChronoField.YEAR));
/* 508 */     paramDataOutput.writeByte(get(ChronoField.MONTH_OF_YEAR));
/* 509 */     paramDataOutput.writeByte(get(ChronoField.DAY_OF_MONTH));
/*     */   }
/*     */   
/*     */   static MinguoDate readExternal(DataInput paramDataInput) throws IOException {
/* 513 */     int i = paramDataInput.readInt();
/* 514 */     byte b1 = paramDataInput.readByte();
/* 515 */     byte b2 = paramDataInput.readByte();
/* 516 */     return MinguoChronology.INSTANCE.date(i, b1, b2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/MinguoDate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */