/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.time.temporal.Temporal;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class ChronoLocalDateImpl<D extends ChronoLocalDate>
/*     */   implements ChronoLocalDate, Temporal, TemporalAdjuster, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6282433883239719096L;
/*     */   
/*     */   static <D extends ChronoLocalDate> D ensureValid(Chronology paramChronology, Temporal paramTemporal) {
/* 162 */     ChronoLocalDate chronoLocalDate = (ChronoLocalDate)paramTemporal;
/* 163 */     if (!paramChronology.equals(chronoLocalDate.getChronology())) {
/* 164 */       throw new ClassCastException("Chronology mismatch, expected: " + paramChronology.getId() + ", actual: " + chronoLocalDate.getChronology().getId());
/*     */     }
/* 166 */     return (D)chronoLocalDate;
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
/*     */   public D with(TemporalAdjuster paramTemporalAdjuster) {
/* 179 */     return (D)super.with(paramTemporalAdjuster);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public D with(TemporalField paramTemporalField, long paramLong) {
/* 185 */     return (D)super.with(paramTemporalField, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public D plus(TemporalAmount paramTemporalAmount) {
/* 192 */     return (D)super.plus(paramTemporalAmount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public D plus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 199 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 200 */       ChronoUnit chronoUnit = (ChronoUnit)paramTemporalUnit;
/* 201 */       switch (chronoUnit) { case DAYS:
/* 202 */           return plusDays(paramLong);
/* 203 */         case WEEKS: return plusDays(Math.multiplyExact(paramLong, 7L));
/* 204 */         case MONTHS: return plusMonths(paramLong);
/* 205 */         case YEARS: return plusYears(paramLong);
/* 206 */         case DECADES: return plusYears(Math.multiplyExact(paramLong, 10L));
/* 207 */         case CENTURIES: return plusYears(Math.multiplyExact(paramLong, 100L));
/* 208 */         case MILLENNIA: return plusYears(Math.multiplyExact(paramLong, 1000L));
/* 209 */         case ERAS: return with(ChronoField.ERA, Math.addExact(getLong(ChronoField.ERA), paramLong)); }
/*     */       
/* 211 */       throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
/*     */     } 
/* 213 */     return (D)super.plus(paramLong, paramTemporalUnit);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public D minus(TemporalAmount paramTemporalAmount) {
/* 219 */     return (D)super.minus(paramTemporalAmount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public D minus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 225 */     return (D)super.minus(paramLong, paramTemporalUnit);
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
/*     */   D plusWeeks(long paramLong) {
/* 277 */     return plusDays(Math.multiplyExact(paramLong, 7L));
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
/*     */   D minusYears(long paramLong) {
/* 312 */     return (paramLong == Long.MIN_VALUE) ? ((ChronoLocalDateImpl<D>)plusYears(Long.MAX_VALUE)).plusYears(1L) : plusYears(-paramLong);
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
/*     */   D minusMonths(long paramLong) {
/* 333 */     return (paramLong == Long.MIN_VALUE) ? ((ChronoLocalDateImpl<D>)plusMonths(Long.MAX_VALUE)).plusMonths(1L) : plusMonths(-paramLong);
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
/*     */   D minusWeeks(long paramLong) {
/* 353 */     return (paramLong == Long.MIN_VALUE) ? ((ChronoLocalDateImpl<D>)plusWeeks(Long.MAX_VALUE)).plusWeeks(1L) : plusWeeks(-paramLong);
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
/*     */   D minusDays(long paramLong) {
/* 371 */     return (paramLong == Long.MIN_VALUE) ? ((ChronoLocalDateImpl<D>)plusDays(Long.MAX_VALUE)).plusDays(1L) : plusDays(-paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long until(Temporal paramTemporal, TemporalUnit paramTemporalUnit) {
/* 377 */     Objects.requireNonNull(paramTemporal, "endExclusive");
/* 378 */     ChronoLocalDate chronoLocalDate = getChronology().date(paramTemporal);
/* 379 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 380 */       switch ((ChronoUnit)paramTemporalUnit) { case DAYS:
/* 381 */           return daysUntil(chronoLocalDate);
/* 382 */         case WEEKS: return daysUntil(chronoLocalDate) / 7L;
/* 383 */         case MONTHS: return monthsUntil(chronoLocalDate);
/* 384 */         case YEARS: return monthsUntil(chronoLocalDate) / 12L;
/* 385 */         case DECADES: return monthsUntil(chronoLocalDate) / 120L;
/* 386 */         case CENTURIES: return monthsUntil(chronoLocalDate) / 1200L;
/* 387 */         case MILLENNIA: return monthsUntil(chronoLocalDate) / 12000L;
/* 388 */         case ERAS: return chronoLocalDate.getLong(ChronoField.ERA) - getLong(ChronoField.ERA); }
/*     */       
/* 390 */       throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
/*     */     } 
/* 392 */     Objects.requireNonNull(paramTemporalUnit, "unit");
/* 393 */     return paramTemporalUnit.between(this, chronoLocalDate);
/*     */   }
/*     */   
/*     */   private long daysUntil(ChronoLocalDate paramChronoLocalDate) {
/* 397 */     return paramChronoLocalDate.toEpochDay() - toEpochDay();
/*     */   }
/*     */   
/*     */   private long monthsUntil(ChronoLocalDate paramChronoLocalDate) {
/* 401 */     ValueRange valueRange = getChronology().range(ChronoField.MONTH_OF_YEAR);
/* 402 */     if (valueRange.getMaximum() != 12L) {
/* 403 */       throw new IllegalStateException("ChronoLocalDateImpl only supports Chronologies with 12 months per year");
/*     */     }
/* 405 */     long l1 = getLong(ChronoField.PROLEPTIC_MONTH) * 32L + get(ChronoField.DAY_OF_MONTH);
/* 406 */     long l2 = paramChronoLocalDate.getLong(ChronoField.PROLEPTIC_MONTH) * 32L + paramChronoLocalDate.get(ChronoField.DAY_OF_MONTH);
/* 407 */     return (l2 - l1) / 32L;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 412 */     if (this == paramObject) {
/* 413 */       return true;
/*     */     }
/* 415 */     if (paramObject instanceof ChronoLocalDate) {
/* 416 */       return (compareTo((ChronoLocalDate)paramObject) == 0);
/*     */     }
/* 418 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 423 */     long l = toEpochDay();
/* 424 */     return getChronology().hashCode() ^ (int)(l ^ l >>> 32L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 430 */     long l1 = getLong(ChronoField.YEAR_OF_ERA);
/* 431 */     long l2 = getLong(ChronoField.MONTH_OF_YEAR);
/* 432 */     long l3 = getLong(ChronoField.DAY_OF_MONTH);
/* 433 */     StringBuilder stringBuilder = new StringBuilder(30);
/* 434 */     stringBuilder.append(getChronology().toString())
/* 435 */       .append(" ")
/* 436 */       .append(getEra())
/* 437 */       .append(" ")
/* 438 */       .append(l1)
/* 439 */       .append((l2 < 10L) ? "-0" : "-").append(l2)
/* 440 */       .append((l3 < 10L) ? "-0" : "-").append(l3);
/* 441 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   abstract D plusYears(long paramLong);
/*     */   
/*     */   abstract D plusMonths(long paramLong);
/*     */   
/*     */   abstract D plusDays(long paramLong);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/ChronoLocalDateImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */