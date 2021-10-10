/*     */ package java.time.temporal;
/*     */ 
/*     */ import java.time.DayOfWeek;
/*     */ import java.time.LocalDate;
/*     */ import java.util.Objects;
/*     */ import java.util.function.UnaryOperator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TemporalAdjusters
/*     */ {
/*     */   public static TemporalAdjuster ofDateAdjuster(UnaryOperator<LocalDate> paramUnaryOperator) {
/* 140 */     Objects.requireNonNull(paramUnaryOperator, "dateBasedAdjuster");
/* 141 */     return paramTemporal -> {
/*     */         LocalDate localDate1 = LocalDate.from(paramTemporal);
/*     */         LocalDate localDate2 = paramUnaryOperator.apply(localDate1);
/*     */         return paramTemporal.with(localDate2);
/*     */       };
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
/*     */   public static TemporalAdjuster firstDayOfMonth() {
/* 166 */     return paramTemporal -> paramTemporal.with(ChronoField.DAY_OF_MONTH, 1L);
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
/*     */   public static TemporalAdjuster lastDayOfMonth() {
/* 189 */     return paramTemporal -> paramTemporal.with(ChronoField.DAY_OF_MONTH, paramTemporal.range(ChronoField.DAY_OF_MONTH).getMaximum());
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
/*     */   public static TemporalAdjuster firstDayOfNextMonth() {
/* 209 */     return paramTemporal -> paramTemporal.with(ChronoField.DAY_OF_MONTH, 1L).plus(1L, ChronoUnit.MONTHS);
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
/*     */   public static TemporalAdjuster firstDayOfYear() {
/* 230 */     return paramTemporal -> paramTemporal.with(ChronoField.DAY_OF_YEAR, 1L);
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
/*     */   public static TemporalAdjuster lastDayOfYear() {
/* 251 */     return paramTemporal -> paramTemporal.with(ChronoField.DAY_OF_YEAR, paramTemporal.range(ChronoField.DAY_OF_YEAR).getMaximum());
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
/*     */   public static TemporalAdjuster firstDayOfNextYear() {
/* 270 */     return paramTemporal -> paramTemporal.with(ChronoField.DAY_OF_YEAR, 1L).plus(1L, ChronoUnit.YEARS);
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
/*     */   public static TemporalAdjuster firstInMonth(DayOfWeek paramDayOfWeek) {
/* 291 */     return dayOfWeekInMonth(1, paramDayOfWeek);
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
/*     */   public static TemporalAdjuster lastInMonth(DayOfWeek paramDayOfWeek) {
/* 311 */     return dayOfWeekInMonth(-1, paramDayOfWeek);
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
/*     */   public static TemporalAdjuster dayOfWeekInMonth(int paramInt, DayOfWeek paramDayOfWeek) {
/* 347 */     Objects.requireNonNull(paramDayOfWeek, "dayOfWeek");
/* 348 */     int i = paramDayOfWeek.getValue();
/* 349 */     if (paramInt >= 0) {
/* 350 */       return paramTemporal -> {
/*     */           Temporal temporal = paramTemporal.with(ChronoField.DAY_OF_MONTH, 1L);
/*     */           int i = temporal.get(ChronoField.DAY_OF_WEEK);
/*     */           int j = (paramInt1 - i + 7) % 7;
/*     */           j = (int)(j + (paramInt2 - 1L) * 7L);
/*     */           return temporal.plus(j, ChronoUnit.DAYS);
/*     */         };
/*     */     }
/* 358 */     return paramTemporal -> {
/*     */         Temporal temporal = paramTemporal.with(ChronoField.DAY_OF_MONTH, paramTemporal.range(ChronoField.DAY_OF_MONTH).getMaximum());
/*     */         int i = temporal.get(ChronoField.DAY_OF_WEEK);
/*     */         int j = paramInt1 - i;
/*     */         j = (j == 0) ? 0 : ((j > 0) ? (j - 7) : j);
/*     */         j = (int)(j - (-paramInt2 - 1L) * 7L);
/*     */         return temporal.plus(j, ChronoUnit.DAYS);
/*     */       };
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
/*     */   public static TemporalAdjuster next(DayOfWeek paramDayOfWeek) {
/* 387 */     int i = paramDayOfWeek.getValue();
/* 388 */     return paramTemporal -> {
/*     */         int i = paramTemporal.get(ChronoField.DAY_OF_WEEK);
/*     */         int j = i - paramInt;
/*     */         return paramTemporal.plus((j >= 0) ? (7 - j) : -j, ChronoUnit.DAYS);
/*     */       };
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
/*     */   public static TemporalAdjuster nextOrSame(DayOfWeek paramDayOfWeek) {
/* 413 */     int i = paramDayOfWeek.getValue();
/* 414 */     return paramTemporal -> {
/*     */         int i = paramTemporal.get(ChronoField.DAY_OF_WEEK);
/*     */         if (i == paramInt) {
/*     */           return paramTemporal;
/*     */         }
/*     */         int j = i - paramInt;
/*     */         return paramTemporal.plus((j >= 0) ? (7 - j) : -j, ChronoUnit.DAYS);
/*     */       };
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
/*     */   public static TemporalAdjuster previous(DayOfWeek paramDayOfWeek) {
/* 441 */     int i = paramDayOfWeek.getValue();
/* 442 */     return paramTemporal -> {
/*     */         int i = paramTemporal.get(ChronoField.DAY_OF_WEEK);
/*     */         int j = paramInt - i;
/*     */         return paramTemporal.minus((j >= 0) ? (7 - j) : -j, ChronoUnit.DAYS);
/*     */       };
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
/*     */   public static TemporalAdjuster previousOrSame(DayOfWeek paramDayOfWeek) {
/* 467 */     int i = paramDayOfWeek.getValue();
/* 468 */     return paramTemporal -> {
/*     */         int i = paramTemporal.get(ChronoField.DAY_OF_WEEK);
/*     */         if (i == paramInt)
/*     */           return paramTemporal; 
/*     */         int j = paramInt - i;
/*     */         return paramTemporal.minus((j >= 0) ? (7 - j) : -j, ChronoUnit.DAYS);
/*     */       };
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/temporal/TemporalAdjusters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */