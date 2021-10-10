/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.time.Clock;
/*     */ import java.time.DateTimeException;
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
/*     */ import java.util.Calendar;
/*     */ import java.util.Objects;
/*     */ import java.util.TimeZone;
/*     */ import sun.util.calendar.CalendarDate;
/*     */ import sun.util.calendar.Era;
/*     */ import sun.util.calendar.LocalGregorianCalendar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JapaneseDate
/*     */   extends ChronoLocalDateImpl<JapaneseDate>
/*     */   implements ChronoLocalDate, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -305327627230580483L;
/*     */   private final transient LocalDate isoDate;
/*     */   private transient JapaneseEra era;
/*     */   private transient int yearOfEra;
/* 151 */   static final LocalDate MEIJI_6_ISODATE = LocalDate.of(1873, 1, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JapaneseDate now() {
/* 166 */     return now(Clock.systemDefaultZone());
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
/*     */   public static JapaneseDate now(ZoneId paramZoneId) {
/* 182 */     return now(Clock.system(paramZoneId));
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
/*     */   public static JapaneseDate now(Clock paramClock) {
/* 197 */     return new JapaneseDate(LocalDate.now(paramClock));
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
/*     */   public static JapaneseDate of(JapaneseEra paramJapaneseEra, int paramInt1, int paramInt2, int paramInt3) {
/* 227 */     Objects.requireNonNull(paramJapaneseEra, "era");
/* 228 */     LocalGregorianCalendar.Date date = JapaneseChronology.JCAL.newCalendarDate((TimeZone)null);
/* 229 */     date.setEra(paramJapaneseEra.getPrivateEra()).setDate(paramInt1, paramInt2, paramInt3);
/* 230 */     if (!JapaneseChronology.JCAL.validate(date)) {
/* 231 */       throw new DateTimeException("year, month, and day not valid for Era");
/*     */     }
/* 233 */     LocalDate localDate = LocalDate.of(date.getNormalizedYear(), paramInt2, paramInt3);
/* 234 */     return new JapaneseDate(paramJapaneseEra, paramInt1, localDate);
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
/*     */   public static JapaneseDate of(int paramInt1, int paramInt2, int paramInt3) {
/* 255 */     return new JapaneseDate(LocalDate.of(paramInt1, paramInt2, paramInt3));
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
/*     */   static JapaneseDate ofYearDay(JapaneseEra paramJapaneseEra, int paramInt1, int paramInt2) {
/* 284 */     Objects.requireNonNull(paramJapaneseEra, "era");
/* 285 */     CalendarDate calendarDate = paramJapaneseEra.getPrivateEra().getSinceDate();
/* 286 */     LocalGregorianCalendar.Date date = JapaneseChronology.JCAL.newCalendarDate((TimeZone)null);
/* 287 */     date.setEra(paramJapaneseEra.getPrivateEra());
/* 288 */     if (paramInt1 == 1) {
/* 289 */       date.setDate(paramInt1, calendarDate.getMonth(), calendarDate.getDayOfMonth() + paramInt2 - 1);
/*     */     } else {
/* 291 */       date.setDate(paramInt1, 1, paramInt2);
/*     */     } 
/* 293 */     JapaneseChronology.JCAL.normalize(date);
/* 294 */     if (paramJapaneseEra.getPrivateEra() != date.getEra() || paramInt1 != date.getYear()) {
/* 295 */       throw new DateTimeException("Invalid parameters");
/*     */     }
/* 297 */     LocalDate localDate = LocalDate.of(date.getNormalizedYear(), date
/* 298 */         .getMonth(), date.getDayOfMonth());
/* 299 */     return new JapaneseDate(paramJapaneseEra, paramInt1, localDate);
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
/*     */   public static JapaneseDate from(TemporalAccessor paramTemporalAccessor) {
/* 320 */     return JapaneseChronology.INSTANCE.date(paramTemporalAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JapaneseDate(LocalDate paramLocalDate) {
/* 330 */     if (paramLocalDate.isBefore(MEIJI_6_ISODATE)) {
/* 331 */       throw new DateTimeException("JapaneseDate before Meiji 6 is not supported");
/*     */     }
/* 333 */     LocalGregorianCalendar.Date date = toPrivateJapaneseDate(paramLocalDate);
/* 334 */     this.era = JapaneseEra.toJapaneseEra(date.getEra());
/* 335 */     this.yearOfEra = date.getYear();
/* 336 */     this.isoDate = paramLocalDate;
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
/*     */   JapaneseDate(JapaneseEra paramJapaneseEra, int paramInt, LocalDate paramLocalDate) {
/* 348 */     if (paramLocalDate.isBefore(MEIJI_6_ISODATE)) {
/* 349 */       throw new DateTimeException("JapaneseDate before Meiji 6 is not supported");
/*     */     }
/* 351 */     this.era = paramJapaneseEra;
/* 352 */     this.yearOfEra = paramInt;
/* 353 */     this.isoDate = paramLocalDate;
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
/*     */   public JapaneseChronology getChronology() {
/* 367 */     return JapaneseChronology.INSTANCE;
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
/*     */   public JapaneseEra getEra() {
/* 379 */     return this.era;
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
/* 392 */     return this.isoDate.lengthOfMonth();
/*     */   }
/*     */ 
/*     */   
/*     */   public int lengthOfYear() {
/* 397 */     Calendar calendar = Calendar.getInstance(JapaneseChronology.LOCALE);
/* 398 */     calendar.set(0, this.era.getValue() + 2);
/* 399 */     calendar.set(this.yearOfEra, this.isoDate.getMonthValue() - 1, this.isoDate.getDayOfMonth());
/* 400 */     return calendar.getActualMaximum(6);
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
/*     */   public boolean isSupported(TemporalField paramTemporalField) {
/* 436 */     if (paramTemporalField == ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH || paramTemporalField == ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR || paramTemporalField == ChronoField.ALIGNED_WEEK_OF_MONTH || paramTemporalField == ChronoField.ALIGNED_WEEK_OF_YEAR)
/*     */     {
/* 438 */       return false;
/*     */     }
/* 440 */     return super.isSupported(paramTemporalField);
/*     */   }
/*     */ 
/*     */   
/*     */   public ValueRange range(TemporalField paramTemporalField) {
/* 445 */     if (paramTemporalField instanceof ChronoField) {
/* 446 */       if (isSupported(paramTemporalField)) {
/* 447 */         Calendar calendar; ChronoField chronoField = (ChronoField)paramTemporalField;
/* 448 */         switch (chronoField) { case DAY_OF_MONTH:
/* 449 */             return ValueRange.of(1L, lengthOfMonth());
/* 450 */           case DAY_OF_YEAR: return ValueRange.of(1L, lengthOfYear());
/*     */           case YEAR_OF_ERA:
/* 452 */             calendar = Calendar.getInstance(JapaneseChronology.LOCALE);
/* 453 */             calendar.set(0, this.era.getValue() + 2);
/* 454 */             calendar.set(this.yearOfEra, this.isoDate.getMonthValue() - 1, this.isoDate.getDayOfMonth());
/* 455 */             return ValueRange.of(1L, calendar.getActualMaximum(1)); }
/*     */ 
/*     */         
/* 458 */         return getChronology().range(chronoField);
/*     */       } 
/* 460 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*     */     } 
/* 462 */     return paramTemporalField.rangeRefinedBy(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(TemporalField paramTemporalField) {
/* 467 */     if (paramTemporalField instanceof ChronoField) {
/*     */       Calendar calendar;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 473 */       switch ((ChronoField)paramTemporalField) {
/*     */         case ALIGNED_DAY_OF_WEEK_IN_MONTH:
/*     */         case ALIGNED_DAY_OF_WEEK_IN_YEAR:
/*     */         case ALIGNED_WEEK_OF_MONTH:
/*     */         case ALIGNED_WEEK_OF_YEAR:
/* 478 */           throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*     */         case YEAR_OF_ERA:
/* 480 */           return this.yearOfEra;
/*     */         case ERA:
/* 482 */           return this.era.getValue();
/*     */         case DAY_OF_YEAR:
/* 484 */           calendar = Calendar.getInstance(JapaneseChronology.LOCALE);
/* 485 */           calendar.set(0, this.era.getValue() + 2);
/* 486 */           calendar.set(this.yearOfEra, this.isoDate.getMonthValue() - 1, this.isoDate.getDayOfMonth());
/* 487 */           return calendar.get(6);
/*     */       } 
/* 489 */       return this.isoDate.getLong(paramTemporalField);
/*     */     } 
/* 491 */     return paramTemporalField.getFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static LocalGregorianCalendar.Date toPrivateJapaneseDate(LocalDate paramLocalDate) {
/* 501 */     LocalGregorianCalendar.Date date = JapaneseChronology.JCAL.newCalendarDate((TimeZone)null);
/* 502 */     Era era = JapaneseEra.privateEraFrom(paramLocalDate);
/* 503 */     int i = paramLocalDate.getYear();
/* 504 */     if (era != null) {
/* 505 */       i -= era.getSinceDate().getYear() - 1;
/*     */     }
/* 507 */     date.setEra(era).setYear(i).setMonth(paramLocalDate.getMonthValue()).setDayOfMonth(paramLocalDate.getDayOfMonth());
/* 508 */     JapaneseChronology.JCAL.normalize(date);
/* 509 */     return date;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JapaneseDate with(TemporalField paramTemporalField, long paramLong) {
/* 515 */     if (paramTemporalField instanceof ChronoField) {
/* 516 */       int i; ChronoField chronoField = (ChronoField)paramTemporalField;
/* 517 */       if (getLong(chronoField) == paramLong) {
/* 518 */         return this;
/*     */       }
/* 520 */       switch (chronoField) {
/*     */         case YEAR_OF_ERA:
/*     */         case ERA:
/*     */         case YEAR:
/* 524 */           i = getChronology().range(chronoField).checkValidIntValue(paramLong, chronoField);
/* 525 */           switch (chronoField) {
/*     */             case YEAR_OF_ERA:
/* 527 */               return withYear(i);
/*     */             case YEAR:
/* 529 */               return with(this.isoDate.withYear(i));
/*     */             case ERA:
/* 531 */               return withYear(JapaneseEra.of(i), this.yearOfEra);
/*     */           } 
/*     */           
/*     */           break;
/*     */       } 
/*     */       
/* 537 */       return with(this.isoDate.with(paramTemporalField, paramLong));
/*     */     } 
/* 539 */     return super.with(paramTemporalField, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JapaneseDate with(TemporalAdjuster paramTemporalAdjuster) {
/* 549 */     return super.with(paramTemporalAdjuster);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JapaneseDate plus(TemporalAmount paramTemporalAmount) {
/* 559 */     return super.plus(paramTemporalAmount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JapaneseDate minus(TemporalAmount paramTemporalAmount) {
/* 569 */     return super.minus(paramTemporalAmount);
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
/*     */   private JapaneseDate withYear(JapaneseEra paramJapaneseEra, int paramInt) {
/* 587 */     int i = JapaneseChronology.INSTANCE.prolepticYear(paramJapaneseEra, paramInt);
/* 588 */     return with(this.isoDate.withYear(i));
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
/*     */   private JapaneseDate withYear(int paramInt) {
/* 605 */     return withYear(getEra(), paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   JapaneseDate plusYears(long paramLong) {
/* 611 */     return with(this.isoDate.plusYears(paramLong));
/*     */   }
/*     */ 
/*     */   
/*     */   JapaneseDate plusMonths(long paramLong) {
/* 616 */     return with(this.isoDate.plusMonths(paramLong));
/*     */   }
/*     */ 
/*     */   
/*     */   JapaneseDate plusWeeks(long paramLong) {
/* 621 */     return with(this.isoDate.plusWeeks(paramLong));
/*     */   }
/*     */ 
/*     */   
/*     */   JapaneseDate plusDays(long paramLong) {
/* 626 */     return with(this.isoDate.plusDays(paramLong));
/*     */   }
/*     */ 
/*     */   
/*     */   public JapaneseDate plus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 631 */     return super.plus(paramLong, paramTemporalUnit);
/*     */   }
/*     */ 
/*     */   
/*     */   public JapaneseDate minus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 636 */     return super.minus(paramLong, paramTemporalUnit);
/*     */   }
/*     */ 
/*     */   
/*     */   JapaneseDate minusYears(long paramLong) {
/* 641 */     return super.minusYears(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   JapaneseDate minusMonths(long paramLong) {
/* 646 */     return super.minusMonths(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   JapaneseDate minusWeeks(long paramLong) {
/* 651 */     return super.minusWeeks(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   JapaneseDate minusDays(long paramLong) {
/* 656 */     return super.minusDays(paramLong);
/*     */   }
/*     */   
/*     */   private JapaneseDate with(LocalDate paramLocalDate) {
/* 660 */     return paramLocalDate.equals(this.isoDate) ? this : new JapaneseDate(paramLocalDate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final ChronoLocalDateTime<JapaneseDate> atTime(LocalTime paramLocalTime) {
/* 666 */     return (ChronoLocalDateTime)super.atTime(paramLocalTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChronoPeriod until(ChronoLocalDate paramChronoLocalDate) {
/* 671 */     Period period = this.isoDate.until(paramChronoLocalDate);
/* 672 */     return getChronology().period(period.getYears(), period.getMonths(), period.getDays());
/*     */   }
/*     */ 
/*     */   
/*     */   public long toEpochDay() {
/* 677 */     return this.isoDate.toEpochDay();
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
/* 695 */     if (this == paramObject) {
/* 696 */       return true;
/*     */     }
/* 698 */     if (paramObject instanceof JapaneseDate) {
/* 699 */       JapaneseDate japaneseDate = (JapaneseDate)paramObject;
/* 700 */       return this.isoDate.equals(japaneseDate.isoDate);
/*     */     } 
/* 702 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 712 */     return getChronology().getId().hashCode() ^ this.isoDate.hashCode();
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
/* 723 */     throw new InvalidObjectException("Deserialization via serialization delegate");
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
/* 740 */     return new Ser((byte)4, this);
/*     */   }
/*     */ 
/*     */   
/*     */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 745 */     paramDataOutput.writeInt(get(ChronoField.YEAR));
/* 746 */     paramDataOutput.writeByte(get(ChronoField.MONTH_OF_YEAR));
/* 747 */     paramDataOutput.writeByte(get(ChronoField.DAY_OF_MONTH));
/*     */   }
/*     */   
/*     */   static JapaneseDate readExternal(DataInput paramDataInput) throws IOException {
/* 751 */     int i = paramDataInput.readInt();
/* 752 */     byte b1 = paramDataInput.readByte();
/* 753 */     byte b2 = paramDataInput.readByte();
/* 754 */     return JapaneseChronology.INSTANCE.date(i, b1, b2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/JapaneseDate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */