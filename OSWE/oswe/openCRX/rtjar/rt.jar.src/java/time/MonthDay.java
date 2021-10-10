/*     */ package java.time;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.time.chrono.Chronology;
/*     */ import java.time.chrono.IsoChronology;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.time.format.DateTimeFormatterBuilder;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.Temporal;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalAdjuster;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.TemporalQueries;
/*     */ import java.time.temporal.TemporalQuery;
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
/*     */ public final class MonthDay
/*     */   implements TemporalAccessor, TemporalAdjuster, Comparable<MonthDay>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -939150713474957432L;
/* 137 */   private static final DateTimeFormatter PARSER = (new DateTimeFormatterBuilder())
/* 138 */     .appendLiteral("--")
/* 139 */     .appendValue(ChronoField.MONTH_OF_YEAR, 2)
/* 140 */     .appendLiteral('-')
/* 141 */     .appendValue(ChronoField.DAY_OF_MONTH, 2)
/* 142 */     .toFormatter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int month;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int day;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MonthDay now() {
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
/*     */   public static MonthDay now(ZoneId paramZoneId) {
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
/*     */   public static MonthDay now(Clock paramClock) {
/* 196 */     LocalDate localDate = LocalDate.now(paramClock);
/* 197 */     return of(localDate.getMonth(), localDate.getDayOfMonth());
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
/*     */   public static MonthDay of(Month paramMonth, int paramInt) {
/* 218 */     Objects.requireNonNull(paramMonth, "month");
/* 219 */     ChronoField.DAY_OF_MONTH.checkValidValue(paramInt);
/* 220 */     if (paramInt > paramMonth.maxLength()) {
/* 221 */       throw new DateTimeException("Illegal value for DayOfMonth field, value " + paramInt + " is not valid for month " + paramMonth
/* 222 */           .name());
/*     */     }
/* 224 */     return new MonthDay(paramMonth.getValue(), paramInt);
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
/*     */   public static MonthDay of(int paramInt1, int paramInt2) {
/* 244 */     return of(Month.of(paramInt1), paramInt2);
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
/*     */   public static MonthDay from(TemporalAccessor paramTemporalAccessor) {
/* 268 */     if (paramTemporalAccessor instanceof MonthDay) {
/* 269 */       return (MonthDay)paramTemporalAccessor;
/*     */     }
/*     */     try {
/* 272 */       if (!IsoChronology.INSTANCE.equals(Chronology.from(paramTemporalAccessor))) {
/* 273 */         paramTemporalAccessor = LocalDate.from(paramTemporalAccessor);
/*     */       }
/* 275 */       return of(paramTemporalAccessor.get(ChronoField.MONTH_OF_YEAR), paramTemporalAccessor.get(ChronoField.DAY_OF_MONTH));
/* 276 */     } catch (DateTimeException dateTimeException) {
/* 277 */       throw new DateTimeException("Unable to obtain MonthDay from TemporalAccessor: " + paramTemporalAccessor + " of type " + paramTemporalAccessor
/* 278 */           .getClass().getName(), dateTimeException);
/*     */     } 
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
/*     */   public static MonthDay parse(CharSequence paramCharSequence) {
/* 294 */     return parse(paramCharSequence, PARSER);
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
/*     */   public static MonthDay parse(CharSequence paramCharSequence, DateTimeFormatter paramDateTimeFormatter) {
/* 308 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/* 309 */     return paramDateTimeFormatter.<MonthDay>parse(paramCharSequence, MonthDay::from);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MonthDay(int paramInt1, int paramInt2) {
/* 320 */     this.month = paramInt1;
/* 321 */     this.day = paramInt2;
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
/*     */   public boolean isSupported(TemporalField paramTemporalField) {
/* 350 */     if (paramTemporalField instanceof ChronoField) {
/* 351 */       return (paramTemporalField == ChronoField.MONTH_OF_YEAR || paramTemporalField == ChronoField.DAY_OF_MONTH);
/*     */     }
/* 353 */     return (paramTemporalField != null && paramTemporalField.isSupportedBy(this));
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
/*     */   public ValueRange range(TemporalField paramTemporalField) {
/* 381 */     if (paramTemporalField == ChronoField.MONTH_OF_YEAR)
/* 382 */       return paramTemporalField.range(); 
/* 383 */     if (paramTemporalField == ChronoField.DAY_OF_MONTH) {
/* 384 */       return ValueRange.of(1L, getMonth().minLength(), getMonth().maxLength());
/*     */     }
/* 386 */     return super.range(paramTemporalField);
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
/*     */   public int get(TemporalField paramTemporalField) {
/* 417 */     return range(paramTemporalField).checkValidIntValue(getLong(paramTemporalField), paramTemporalField);
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
/*     */   public long getLong(TemporalField paramTemporalField) {
/* 445 */     if (paramTemporalField instanceof ChronoField) {
/* 446 */       switch ((ChronoField)paramTemporalField) {
/*     */         case DAY_OF_MONTH:
/* 448 */           return this.day;
/* 449 */         case MONTH_OF_YEAR: return this.month;
/*     */       } 
/* 451 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*     */     } 
/* 453 */     return paramTemporalField.getFrom(this);
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
/*     */   public int getMonthValue() {
/* 468 */     return this.month;
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
/*     */   public Month getMonth() {
/* 483 */     return Month.of(this.month);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDayOfMonth() {
/* 494 */     return this.day;
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
/*     */   public boolean isValidYear(int paramInt) {
/* 509 */     return !((this.day == 29 && this.month == 2 && !Year.isLeap(paramInt)) ? 1 : 0);
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
/*     */   public MonthDay withMonth(int paramInt) {
/* 527 */     return with(Month.of(paramInt));
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
/*     */   public MonthDay with(Month paramMonth) {
/* 543 */     Objects.requireNonNull(paramMonth, "month");
/* 544 */     if (paramMonth.getValue() == this.month) {
/* 545 */       return this;
/*     */     }
/* 547 */     int i = Math.min(this.day, paramMonth.maxLength());
/* 548 */     return new MonthDay(paramMonth.getValue(), i);
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
/*     */   public MonthDay withDayOfMonth(int paramInt) {
/* 565 */     if (paramInt == this.day) {
/* 566 */       return this;
/*     */     }
/* 568 */     return of(this.month, paramInt);
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
/*     */   public <R> R query(TemporalQuery<R> paramTemporalQuery) {
/* 593 */     if (paramTemporalQuery == TemporalQueries.chronology()) {
/* 594 */       return (R)IsoChronology.INSTANCE;
/*     */     }
/* 596 */     return super.query(paramTemporalQuery);
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
/*     */   public Temporal adjustInto(Temporal paramTemporal) {
/* 628 */     if (!Chronology.from(paramTemporal).equals(IsoChronology.INSTANCE)) {
/* 629 */       throw new DateTimeException("Adjustment only supported on ISO date-time");
/*     */     }
/* 631 */     paramTemporal = paramTemporal.with(ChronoField.MONTH_OF_YEAR, this.month);
/* 632 */     return paramTemporal.with(ChronoField.DAY_OF_MONTH, Math.min(paramTemporal.range(ChronoField.DAY_OF_MONTH).getMaximum(), this.day));
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
/*     */   public String format(DateTimeFormatter paramDateTimeFormatter) {
/* 645 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/* 646 */     return paramDateTimeFormatter.format(this);
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
/*     */   public LocalDate atYear(int paramInt) {
/* 665 */     return LocalDate.of(paramInt, this.month, isValidYear(paramInt) ? this.day : 28);
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
/*     */   public int compareTo(MonthDay paramMonthDay) {
/* 680 */     int i = this.month - paramMonthDay.month;
/* 681 */     if (i == 0) {
/* 682 */       i = this.day - paramMonthDay.day;
/*     */     }
/* 684 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAfter(MonthDay paramMonthDay) {
/* 694 */     return (compareTo(paramMonthDay) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBefore(MonthDay paramMonthDay) {
/* 704 */     return (compareTo(paramMonthDay) < 0);
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
/*     */   public boolean equals(Object paramObject) {
/* 718 */     if (this == paramObject) {
/* 719 */       return true;
/*     */     }
/* 721 */     if (paramObject instanceof MonthDay) {
/* 722 */       MonthDay monthDay = (MonthDay)paramObject;
/* 723 */       return (this.month == monthDay.month && this.day == monthDay.day);
/*     */     } 
/* 725 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 735 */     return (this.month << 6) + this.day;
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
/*     */   public String toString() {
/* 748 */     return (new StringBuilder(10)).append("--")
/* 749 */       .append((this.month < 10) ? "0" : "").append(this.month)
/* 750 */       .append((this.day < 10) ? "-0" : "-").append(this.day)
/* 751 */       .toString();
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
/* 768 */     return new Ser((byte)13, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 778 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*     */   }
/*     */   
/*     */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 782 */     paramDataOutput.writeByte(this.month);
/* 783 */     paramDataOutput.writeByte(this.day);
/*     */   }
/*     */   
/*     */   static MonthDay readExternal(DataInput paramDataInput) throws IOException {
/* 787 */     byte b1 = paramDataInput.readByte();
/* 788 */     byte b2 = paramDataInput.readByte();
/* 789 */     return of(b1, b2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/MonthDay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */