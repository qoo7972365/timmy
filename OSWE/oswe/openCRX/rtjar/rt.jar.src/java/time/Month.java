/*     */ package java.time;
/*     */ 
/*     */ import java.time.chrono.Chronology;
/*     */ import java.time.chrono.IsoChronology;
/*     */ import java.time.format.DateTimeFormatterBuilder;
/*     */ import java.time.format.TextStyle;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.time.temporal.Temporal;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalAdjuster;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.TemporalQueries;
/*     */ import java.time.temporal.TemporalQuery;
/*     */ import java.time.temporal.UnsupportedTemporalTypeException;
/*     */ import java.time.temporal.ValueRange;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Month
/*     */   implements TemporalAccessor, TemporalAdjuster
/*     */ {
/* 112 */   JANUARY,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   FEBRUARY,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   MARCH,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   APRIL,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   MAY,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   JUNE,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   JULY,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   AUGUST,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   SEPTEMBER,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   OCTOBER,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   NOVEMBER,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   DECEMBER;
/*     */   private static final Month[] ENUMS;
/*     */   
/*     */   static {
/* 171 */     ENUMS = values();
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
/*     */   public static Month of(int paramInt) {
/* 186 */     if (paramInt < 1 || paramInt > 12) {
/* 187 */       throw new DateTimeException("Invalid value for MonthOfYear: " + paramInt);
/*     */     }
/* 189 */     return ENUMS[paramInt - 1];
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
/*     */   public static Month from(TemporalAccessor paramTemporalAccessor) {
/* 212 */     if (paramTemporalAccessor instanceof Month) {
/* 213 */       return (Month)paramTemporalAccessor;
/*     */     }
/*     */     try {
/* 216 */       if (!IsoChronology.INSTANCE.equals(Chronology.from(paramTemporalAccessor))) {
/* 217 */         paramTemporalAccessor = LocalDate.from(paramTemporalAccessor);
/*     */       }
/* 219 */       return of(paramTemporalAccessor.get(ChronoField.MONTH_OF_YEAR));
/* 220 */     } catch (DateTimeException dateTimeException) {
/* 221 */       throw new DateTimeException("Unable to obtain Month from TemporalAccessor: " + paramTemporalAccessor + " of type " + paramTemporalAccessor
/* 222 */           .getClass().getName(), dateTimeException);
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
/*     */   public int getValue() {
/* 236 */     return ordinal() + 1;
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
/*     */   public String getDisplayName(TextStyle paramTextStyle, Locale paramLocale) {
/* 254 */     return (new DateTimeFormatterBuilder()).appendText(ChronoField.MONTH_OF_YEAR, paramTextStyle).toFormatter(paramLocale).format(this);
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
/*     */   public boolean isSupported(TemporalField paramTemporalField) {
/* 279 */     if (paramTemporalField instanceof ChronoField) {
/* 280 */       return (paramTemporalField == ChronoField.MONTH_OF_YEAR);
/*     */     }
/* 282 */     return (paramTemporalField != null && paramTemporalField.isSupportedBy(this));
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
/*     */   public ValueRange range(TemporalField paramTemporalField) {
/* 309 */     if (paramTemporalField == ChronoField.MONTH_OF_YEAR) {
/* 310 */       return paramTemporalField.range();
/*     */     }
/* 312 */     return super.range(paramTemporalField);
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
/*     */   public int get(TemporalField paramTemporalField) {
/* 342 */     if (paramTemporalField == ChronoField.MONTH_OF_YEAR) {
/* 343 */       return getValue();
/*     */     }
/* 345 */     return super.get(paramTemporalField);
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
/*     */   public long getLong(TemporalField paramTemporalField) {
/* 372 */     if (paramTemporalField == ChronoField.MONTH_OF_YEAR)
/* 373 */       return getValue(); 
/* 374 */     if (paramTemporalField instanceof ChronoField) {
/* 375 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*     */     }
/* 377 */     return paramTemporalField.getFrom(this);
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
/*     */   public Month plus(long paramLong) {
/* 393 */     int i = (int)(paramLong % 12L);
/* 394 */     return ENUMS[(ordinal() + i + 12) % 12];
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
/*     */   public Month minus(long paramLong) {
/* 409 */     return plus(-(paramLong % 12L));
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
/*     */   public int length(boolean paramBoolean) {
/* 426 */     switch (this) {
/*     */       case FEBRUARY:
/* 428 */         return paramBoolean ? 29 : 28;
/*     */       case APRIL:
/*     */       case JUNE:
/*     */       case SEPTEMBER:
/*     */       case NOVEMBER:
/* 433 */         return 30;
/*     */     } 
/* 435 */     return 31;
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
/*     */   public int minLength() {
/* 449 */     switch (this) {
/*     */       case FEBRUARY:
/* 451 */         return 28;
/*     */       case APRIL:
/*     */       case JUNE:
/*     */       case SEPTEMBER:
/*     */       case NOVEMBER:
/* 456 */         return 30;
/*     */     } 
/* 458 */     return 31;
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
/*     */   public int maxLength() {
/* 472 */     switch (this) {
/*     */       case FEBRUARY:
/* 474 */         return 29;
/*     */       case APRIL:
/*     */       case JUNE:
/*     */       case SEPTEMBER:
/*     */       case NOVEMBER:
/* 479 */         return 30;
/*     */     } 
/* 481 */     return 31;
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
/*     */   public int firstDayOfYear(boolean paramBoolean) {
/* 496 */     byte b = paramBoolean ? 1 : 0;
/* 497 */     switch (this) {
/*     */       case JANUARY:
/* 499 */         return 1;
/*     */       case FEBRUARY:
/* 501 */         return 32;
/*     */       case MARCH:
/* 503 */         return 60 + b;
/*     */       case APRIL:
/* 505 */         return 91 + b;
/*     */       case MAY:
/* 507 */         return 121 + b;
/*     */       case JUNE:
/* 509 */         return 152 + b;
/*     */       case JULY:
/* 511 */         return 182 + b;
/*     */       case AUGUST:
/* 513 */         return 213 + b;
/*     */       case SEPTEMBER:
/* 515 */         return 244 + b;
/*     */       case OCTOBER:
/* 517 */         return 274 + b;
/*     */       case NOVEMBER:
/* 519 */         return 305 + b;
/*     */     } 
/*     */     
/* 522 */     return 335 + b;
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
/*     */   public Month firstMonthOfQuarter() {
/* 539 */     return ENUMS[ordinal() / 3 * 3];
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
/* 564 */     if (paramTemporalQuery == TemporalQueries.chronology())
/* 565 */       return (R)IsoChronology.INSTANCE; 
/* 566 */     if (paramTemporalQuery == TemporalQueries.precision()) {
/* 567 */       return (R)ChronoUnit.MONTHS;
/*     */     }
/* 569 */     return super.query(paramTemporalQuery);
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
/*     */   public Temporal adjustInto(Temporal paramTemporal) {
/* 609 */     if (!Chronology.from(paramTemporal).equals(IsoChronology.INSTANCE)) {
/* 610 */       throw new DateTimeException("Adjustment only supported on ISO date-time");
/*     */     }
/* 612 */     return paramTemporal.with(ChronoField.MONTH_OF_YEAR, getValue());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/Month.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */