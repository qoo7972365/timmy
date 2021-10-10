/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.Serializable;
/*     */ import java.time.Clock;
/*     */ import java.time.LocalDate;
/*     */ import java.time.LocalTime;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HijrahDate
/*     */   extends ChronoLocalDateImpl<HijrahDate>
/*     */   implements ChronoLocalDate, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5207853542612002020L;
/*     */   private final transient HijrahChronology chrono;
/*     */   private final transient int prolepticYear;
/*     */   private final transient int monthOfYear;
/*     */   private final transient int dayOfMonth;
/*     */   
/*     */   static HijrahDate of(HijrahChronology paramHijrahChronology, int paramInt1, int paramInt2, int paramInt3) {
/* 156 */     return new HijrahDate(paramHijrahChronology, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static HijrahDate ofEpochDay(HijrahChronology paramHijrahChronology, long paramLong) {
/* 166 */     return new HijrahDate(paramHijrahChronology, paramLong);
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
/*     */   public static HijrahDate now() {
/* 183 */     return now(Clock.systemDefaultZone());
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
/*     */   public static HijrahDate now(ZoneId paramZoneId) {
/* 200 */     return now(Clock.system(paramZoneId));
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
/*     */   public static HijrahDate now(Clock paramClock) {
/* 216 */     return ofEpochDay(HijrahChronology.INSTANCE, LocalDate.now(paramClock).toEpochDay());
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
/*     */   public static HijrahDate of(int paramInt1, int paramInt2, int paramInt3) {
/* 234 */     return HijrahChronology.INSTANCE.date(paramInt1, paramInt2, paramInt3);
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
/*     */   public static HijrahDate from(TemporalAccessor paramTemporalAccessor) {
/* 255 */     return HijrahChronology.INSTANCE.date(paramTemporalAccessor);
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
/*     */   private HijrahDate(HijrahChronology paramHijrahChronology, int paramInt1, int paramInt2, int paramInt3) {
/* 270 */     paramHijrahChronology.getEpochDay(paramInt1, paramInt2, paramInt3);
/*     */     
/* 272 */     this.chrono = paramHijrahChronology;
/* 273 */     this.prolepticYear = paramInt1;
/* 274 */     this.monthOfYear = paramInt2;
/* 275 */     this.dayOfMonth = paramInt3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HijrahDate(HijrahChronology paramHijrahChronology, long paramLong) {
/* 284 */     int[] arrayOfInt = paramHijrahChronology.getHijrahDateInfo((int)paramLong);
/*     */     
/* 286 */     this.chrono = paramHijrahChronology;
/* 287 */     this.prolepticYear = arrayOfInt[0];
/* 288 */     this.monthOfYear = arrayOfInt[1];
/* 289 */     this.dayOfMonth = arrayOfInt[2];
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
/*     */   public HijrahChronology getChronology() {
/* 303 */     return this.chrono;
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
/*     */   public HijrahEra getEra() {
/* 316 */     return HijrahEra.AH;
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
/* 329 */     return this.chrono.getMonthLength(this.prolepticYear, this.monthOfYear);
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
/*     */   public int lengthOfYear() {
/* 343 */     return this.chrono.getYearLength(this.prolepticYear);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueRange range(TemporalField paramTemporalField) {
/* 349 */     if (paramTemporalField instanceof ChronoField) {
/* 350 */       if (isSupported(paramTemporalField)) {
/* 351 */         ChronoField chronoField = (ChronoField)paramTemporalField;
/* 352 */         switch (chronoField) { case DAY_OF_MONTH:
/* 353 */             return ValueRange.of(1L, lengthOfMonth());
/* 354 */           case DAY_OF_YEAR: return ValueRange.of(1L, lengthOfYear());
/* 355 */           case ALIGNED_WEEK_OF_MONTH: return ValueRange.of(1L, 5L); }
/*     */ 
/*     */ 
/*     */         
/* 359 */         return getChronology().range(chronoField);
/*     */       } 
/* 361 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*     */     } 
/* 363 */     return paramTemporalField.rangeRefinedBy(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(TemporalField paramTemporalField) {
/* 368 */     if (paramTemporalField instanceof ChronoField) {
/* 369 */       switch ((ChronoField)paramTemporalField) { case DAY_OF_WEEK:
/* 370 */           return getDayOfWeek();
/* 371 */         case ALIGNED_DAY_OF_WEEK_IN_MONTH: return ((getDayOfWeek() - 1) % 7 + 1);
/* 372 */         case ALIGNED_DAY_OF_WEEK_IN_YEAR: return ((getDayOfYear() - 1) % 7 + 1);
/* 373 */         case DAY_OF_MONTH: return this.dayOfMonth;
/* 374 */         case DAY_OF_YEAR: return getDayOfYear();
/* 375 */         case EPOCH_DAY: return toEpochDay();
/* 376 */         case ALIGNED_WEEK_OF_MONTH: return ((this.dayOfMonth - 1) / 7 + 1);
/* 377 */         case ALIGNED_WEEK_OF_YEAR: return ((getDayOfYear() - 1) / 7 + 1);
/* 378 */         case MONTH_OF_YEAR: return this.monthOfYear;
/* 379 */         case PROLEPTIC_MONTH: return getProlepticMonth();
/* 380 */         case YEAR_OF_ERA: return this.prolepticYear;
/* 381 */         case YEAR: return this.prolepticYear;
/* 382 */         case ERA: return getEraValue(); }
/*     */       
/* 384 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*     */     } 
/* 386 */     return paramTemporalField.getFrom(this);
/*     */   }
/*     */   
/*     */   private long getProlepticMonth() {
/* 390 */     return this.prolepticYear * 12L + this.monthOfYear - 1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public HijrahDate with(TemporalField paramTemporalField, long paramLong) {
/* 395 */     if (paramTemporalField instanceof ChronoField) {
/* 396 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/*     */       
/* 398 */       this.chrono.range(chronoField).checkValidValue(paramLong, chronoField);
/* 399 */       int i = (int)paramLong;
/* 400 */       switch (chronoField) { case DAY_OF_WEEK:
/* 401 */           return plusDays(paramLong - getDayOfWeek());
/* 402 */         case ALIGNED_DAY_OF_WEEK_IN_MONTH: return plusDays(paramLong - getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH));
/* 403 */         case ALIGNED_DAY_OF_WEEK_IN_YEAR: return plusDays(paramLong - getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR));
/* 404 */         case DAY_OF_MONTH: return resolvePreviousValid(this.prolepticYear, this.monthOfYear, i);
/* 405 */         case DAY_OF_YEAR: return plusDays((Math.min(i, lengthOfYear()) - getDayOfYear()));
/* 406 */         case EPOCH_DAY: return new HijrahDate(this.chrono, paramLong);
/* 407 */         case ALIGNED_WEEK_OF_MONTH: return plusDays((paramLong - getLong(ChronoField.ALIGNED_WEEK_OF_MONTH)) * 7L);
/* 408 */         case ALIGNED_WEEK_OF_YEAR: return plusDays((paramLong - getLong(ChronoField.ALIGNED_WEEK_OF_YEAR)) * 7L);
/* 409 */         case MONTH_OF_YEAR: return resolvePreviousValid(this.prolepticYear, i, this.dayOfMonth);
/* 410 */         case PROLEPTIC_MONTH: return plusMonths(paramLong - getProlepticMonth());
/* 411 */         case YEAR_OF_ERA: return resolvePreviousValid((this.prolepticYear >= 1) ? i : (1 - i), this.monthOfYear, this.dayOfMonth);
/* 412 */         case YEAR: return resolvePreviousValid(i, this.monthOfYear, this.dayOfMonth);
/* 413 */         case ERA: return resolvePreviousValid(1 - this.prolepticYear, this.monthOfYear, this.dayOfMonth); }
/*     */       
/* 415 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*     */     } 
/* 417 */     return super.with(paramTemporalField, paramLong);
/*     */   }
/*     */   
/*     */   private HijrahDate resolvePreviousValid(int paramInt1, int paramInt2, int paramInt3) {
/* 421 */     int i = this.chrono.getMonthLength(paramInt1, paramInt2);
/* 422 */     if (paramInt3 > i) {
/* 423 */       paramInt3 = i;
/*     */     }
/* 425 */     return of(this.chrono, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HijrahDate with(TemporalAdjuster paramTemporalAdjuster) {
/* 436 */     return super.with(paramTemporalAdjuster);
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
/*     */   public HijrahDate withVariant(HijrahChronology paramHijrahChronology) {
/* 450 */     if (this.chrono == paramHijrahChronology) {
/* 451 */       return this;
/*     */     }
/*     */     
/* 454 */     int i = paramHijrahChronology.getDayOfYear(this.prolepticYear, this.monthOfYear);
/* 455 */     return of(paramHijrahChronology, this.prolepticYear, this.monthOfYear, (this.dayOfMonth > i) ? i : this.dayOfMonth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HijrahDate plus(TemporalAmount paramTemporalAmount) {
/* 465 */     return super.plus(paramTemporalAmount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HijrahDate minus(TemporalAmount paramTemporalAmount) {
/* 475 */     return super.minus(paramTemporalAmount);
/*     */   }
/*     */ 
/*     */   
/*     */   public long toEpochDay() {
/* 480 */     return this.chrono.getEpochDay(this.prolepticYear, this.monthOfYear, this.dayOfMonth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getDayOfYear() {
/* 491 */     return this.chrono.getDayOfYear(this.prolepticYear, this.monthOfYear) + this.dayOfMonth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getDayOfWeek() {
/* 500 */     int i = (int)Math.floorMod(toEpochDay() + 3L, 7L);
/* 501 */     return i + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getEraValue() {
/* 510 */     return (this.prolepticYear > 1) ? 1 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLeapYear() {
/* 521 */     return this.chrono.isLeapYear(this.prolepticYear);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   HijrahDate plusYears(long paramLong) {
/* 527 */     if (paramLong == 0L) {
/* 528 */       return this;
/*     */     }
/* 530 */     int i = Math.addExact(this.prolepticYear, (int)paramLong);
/* 531 */     return resolvePreviousValid(i, this.monthOfYear, this.dayOfMonth);
/*     */   }
/*     */ 
/*     */   
/*     */   HijrahDate plusMonths(long paramLong) {
/* 536 */     if (paramLong == 0L) {
/* 537 */       return this;
/*     */     }
/* 539 */     long l1 = this.prolepticYear * 12L + (this.monthOfYear - 1);
/* 540 */     long l2 = l1 + paramLong;
/* 541 */     int i = this.chrono.checkValidYear(Math.floorDiv(l2, 12L));
/* 542 */     int j = (int)Math.floorMod(l2, 12L) + 1;
/* 543 */     return resolvePreviousValid(i, j, this.dayOfMonth);
/*     */   }
/*     */ 
/*     */   
/*     */   HijrahDate plusWeeks(long paramLong) {
/* 548 */     return super.plusWeeks(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   HijrahDate plusDays(long paramLong) {
/* 553 */     return new HijrahDate(this.chrono, toEpochDay() + paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public HijrahDate plus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 558 */     return super.plus(paramLong, paramTemporalUnit);
/*     */   }
/*     */ 
/*     */   
/*     */   public HijrahDate minus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 563 */     return super.minus(paramLong, paramTemporalUnit);
/*     */   }
/*     */ 
/*     */   
/*     */   HijrahDate minusYears(long paramLong) {
/* 568 */     return super.minusYears(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   HijrahDate minusMonths(long paramLong) {
/* 573 */     return super.minusMonths(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   HijrahDate minusWeeks(long paramLong) {
/* 578 */     return super.minusWeeks(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   HijrahDate minusDays(long paramLong) {
/* 583 */     return super.minusDays(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final ChronoLocalDateTime<HijrahDate> atTime(LocalTime paramLocalTime) {
/* 589 */     return (ChronoLocalDateTime)super.atTime(paramLocalTime);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoPeriod until(ChronoLocalDate paramChronoLocalDate) {
/* 595 */     HijrahDate hijrahDate = getChronology().date(paramChronoLocalDate);
/* 596 */     long l1 = ((hijrahDate.prolepticYear - this.prolepticYear) * 12 + hijrahDate.monthOfYear - this.monthOfYear);
/* 597 */     int i = hijrahDate.dayOfMonth - this.dayOfMonth;
/* 598 */     if (l1 > 0L && i < 0) {
/* 599 */       l1--;
/* 600 */       HijrahDate hijrahDate1 = plusMonths(l1);
/* 601 */       i = (int)(hijrahDate.toEpochDay() - hijrahDate1.toEpochDay());
/* 602 */     } else if (l1 < 0L && i > 0) {
/* 603 */       l1++;
/* 604 */       i -= hijrahDate.lengthOfMonth();
/*     */     } 
/* 606 */     long l2 = l1 / 12L;
/* 607 */     int j = (int)(l1 % 12L);
/* 608 */     return getChronology().period(Math.toIntExact(l2), j, i);
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
/* 626 */     if (this == paramObject) {
/* 627 */       return true;
/*     */     }
/* 629 */     if (paramObject instanceof HijrahDate) {
/* 630 */       HijrahDate hijrahDate = (HijrahDate)paramObject;
/* 631 */       return (this.prolepticYear == hijrahDate.prolepticYear && this.monthOfYear == hijrahDate.monthOfYear && this.dayOfMonth == hijrahDate.dayOfMonth && 
/*     */ 
/*     */         
/* 634 */         getChronology().equals(hijrahDate.getChronology()));
/*     */     } 
/* 636 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 646 */     int i = this.prolepticYear;
/* 647 */     int j = this.monthOfYear;
/* 648 */     int k = this.dayOfMonth;
/* 649 */     return getChronology().getId().hashCode() ^ i & 0xFFFFF800 ^ (i << 11) + (j << 6) + k;
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 661 */     throw new InvalidObjectException("Deserialization via serialization delegate");
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
/*     */   private Object writeReplace() {
/* 679 */     return new Ser((byte)6, this);
/*     */   }
/*     */ 
/*     */   
/*     */   void writeExternal(ObjectOutput paramObjectOutput) throws IOException {
/* 684 */     paramObjectOutput.writeObject(getChronology());
/* 685 */     paramObjectOutput.writeInt(get(ChronoField.YEAR));
/* 686 */     paramObjectOutput.writeByte(get(ChronoField.MONTH_OF_YEAR));
/* 687 */     paramObjectOutput.writeByte(get(ChronoField.DAY_OF_MONTH));
/*     */   }
/*     */   
/*     */   static HijrahDate readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 691 */     HijrahChronology hijrahChronology = (HijrahChronology)paramObjectInput.readObject();
/* 692 */     int i = paramObjectInput.readInt();
/* 693 */     byte b1 = paramObjectInput.readByte();
/* 694 */     byte b2 = paramObjectInput.readByte();
/* 695 */     return hijrahChronology.date(i, b1, b2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/HijrahDate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */