/*     */ package java.time;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum DayOfWeek
/*     */   implements TemporalAccessor, TemporalAdjuster
/*     */ {
/* 115 */   MONDAY,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   TUESDAY,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   WEDNESDAY,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   THURSDAY,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   FRIDAY,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   SATURDAY,
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   SUNDAY;
/*     */   private static final DayOfWeek[] ENUMS;
/*     */   
/*     */   static {
/* 149 */     ENUMS = values();
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
/*     */   public static DayOfWeek of(int paramInt) {
/* 164 */     if (paramInt < 1 || paramInt > 7) {
/* 165 */       throw new DateTimeException("Invalid value for DayOfWeek: " + paramInt);
/*     */     }
/* 167 */     return ENUMS[paramInt - 1];
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
/*     */   public static DayOfWeek from(TemporalAccessor paramTemporalAccessor) {
/* 188 */     if (paramTemporalAccessor instanceof DayOfWeek) {
/* 189 */       return (DayOfWeek)paramTemporalAccessor;
/*     */     }
/*     */     try {
/* 192 */       return of(paramTemporalAccessor.get(ChronoField.DAY_OF_WEEK));
/* 193 */     } catch (DateTimeException dateTimeException) {
/* 194 */       throw new DateTimeException("Unable to obtain DayOfWeek from TemporalAccessor: " + paramTemporalAccessor + " of type " + paramTemporalAccessor
/* 195 */           .getClass().getName(), dateTimeException);
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
/* 209 */     return ordinal() + 1;
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
/* 227 */     return (new DateTimeFormatterBuilder()).appendText(ChronoField.DAY_OF_WEEK, paramTextStyle).toFormatter(paramLocale).format(this);
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
/* 252 */     if (paramTemporalField instanceof ChronoField) {
/* 253 */       return (paramTemporalField == ChronoField.DAY_OF_WEEK);
/*     */     }
/* 255 */     return (paramTemporalField != null && paramTemporalField.isSupportedBy(this));
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
/* 282 */     if (paramTemporalField == ChronoField.DAY_OF_WEEK) {
/* 283 */       return paramTemporalField.range();
/*     */     }
/* 285 */     return super.range(paramTemporalField);
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
/* 315 */     if (paramTemporalField == ChronoField.DAY_OF_WEEK) {
/* 316 */       return getValue();
/*     */     }
/* 318 */     return super.get(paramTemporalField);
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
/* 345 */     if (paramTemporalField == ChronoField.DAY_OF_WEEK)
/* 346 */       return getValue(); 
/* 347 */     if (paramTemporalField instanceof ChronoField) {
/* 348 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*     */     }
/* 350 */     return paramTemporalField.getFrom(this);
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
/*     */   public DayOfWeek plus(long paramLong) {
/* 366 */     int i = (int)(paramLong % 7L);
/* 367 */     return ENUMS[(ordinal() + i + 7) % 7];
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
/*     */   public DayOfWeek minus(long paramLong) {
/* 382 */     return plus(-(paramLong % 7L));
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
/* 407 */     if (paramTemporalQuery == TemporalQueries.precision()) {
/* 408 */       return (R)ChronoUnit.DAYS;
/*     */     }
/* 410 */     return super.query(paramTemporalQuery);
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
/*     */   public Temporal adjustInto(Temporal paramTemporal) {
/* 454 */     return paramTemporal.with(ChronoField.DAY_OF_WEEK, getValue());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/DayOfWeek.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */