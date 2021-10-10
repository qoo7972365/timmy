/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.time.Clock;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDate;
/*     */ import java.time.ZoneId;
/*     */ import java.time.format.ResolverStyle;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.ValueRange;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MinguoChronology
/*     */   extends AbstractChronology
/*     */   implements Serializable
/*     */ {
/* 113 */   public static final MinguoChronology INSTANCE = new MinguoChronology();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 1039765215346859963L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int YEARS_DIFFERENCE = 1911;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 142 */     return "Minguo";
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
/*     */   public String getCalendarType() {
/* 159 */     return "roc";
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
/*     */   public MinguoDate date(Era paramEra, int paramInt1, int paramInt2, int paramInt3) {
/* 177 */     return date(prolepticYear(paramEra, paramInt1), paramInt2, paramInt3);
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
/*     */   public MinguoDate date(int paramInt1, int paramInt2, int paramInt3) {
/* 192 */     return new MinguoDate(LocalDate.of(paramInt1 + 1911, paramInt2, paramInt3));
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
/*     */   public MinguoDate dateYearDay(Era paramEra, int paramInt1, int paramInt2) {
/* 208 */     return dateYearDay(prolepticYear(paramEra, paramInt1), paramInt2);
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
/*     */   public MinguoDate dateYearDay(int paramInt1, int paramInt2) {
/* 222 */     return new MinguoDate(LocalDate.ofYearDay(paramInt1 + 1911, paramInt2));
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
/*     */   public MinguoDate dateEpochDay(long paramLong) {
/* 234 */     return new MinguoDate(LocalDate.ofEpochDay(paramLong));
/*     */   }
/*     */ 
/*     */   
/*     */   public MinguoDate dateNow() {
/* 239 */     return dateNow(Clock.systemDefaultZone());
/*     */   }
/*     */ 
/*     */   
/*     */   public MinguoDate dateNow(ZoneId paramZoneId) {
/* 244 */     return dateNow(Clock.system(paramZoneId));
/*     */   }
/*     */ 
/*     */   
/*     */   public MinguoDate dateNow(Clock paramClock) {
/* 249 */     return date(LocalDate.now(paramClock));
/*     */   }
/*     */ 
/*     */   
/*     */   public MinguoDate date(TemporalAccessor paramTemporalAccessor) {
/* 254 */     if (paramTemporalAccessor instanceof MinguoDate) {
/* 255 */       return (MinguoDate)paramTemporalAccessor;
/*     */     }
/* 257 */     return new MinguoDate(LocalDate.from(paramTemporalAccessor));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoLocalDateTime<MinguoDate> localDateTime(TemporalAccessor paramTemporalAccessor) {
/* 263 */     return (ChronoLocalDateTime)super.localDateTime(paramTemporalAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoZonedDateTime<MinguoDate> zonedDateTime(TemporalAccessor paramTemporalAccessor) {
/* 269 */     return (ChronoZonedDateTime)super.zonedDateTime(paramTemporalAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoZonedDateTime<MinguoDate> zonedDateTime(Instant paramInstant, ZoneId paramZoneId) {
/* 275 */     return (ChronoZonedDateTime)super.zonedDateTime(paramInstant, paramZoneId);
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
/*     */   public boolean isLeapYear(long paramLong) {
/* 291 */     return IsoChronology.INSTANCE.isLeapYear(paramLong + 1911L);
/*     */   }
/*     */ 
/*     */   
/*     */   public int prolepticYear(Era paramEra, int paramInt) {
/* 296 */     if (!(paramEra instanceof MinguoEra)) {
/* 297 */       throw new ClassCastException("Era must be MinguoEra");
/*     */     }
/* 299 */     return (paramEra == MinguoEra.ROC) ? paramInt : (1 - paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public MinguoEra eraOf(int paramInt) {
/* 304 */     return MinguoEra.of(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Era> eras() {
/* 309 */     return Arrays.asList((Era[])MinguoEra.values());
/*     */   }
/*     */ 
/*     */   
/*     */   public ValueRange range(ChronoField paramChronoField) {
/*     */     ValueRange valueRange;
/* 315 */     switch (paramChronoField) {
/*     */       case PROLEPTIC_MONTH:
/* 317 */         valueRange = ChronoField.PROLEPTIC_MONTH.range();
/* 318 */         return ValueRange.of(valueRange.getMinimum() - 22932L, valueRange.getMaximum() - 22932L);
/*     */       
/*     */       case YEAR_OF_ERA:
/* 321 */         valueRange = ChronoField.YEAR.range();
/* 322 */         return ValueRange.of(1L, valueRange.getMaximum() - 1911L, -valueRange.getMinimum() + 1L + 1911L);
/*     */       
/*     */       case YEAR:
/* 325 */         valueRange = ChronoField.YEAR.range();
/* 326 */         return ValueRange.of(valueRange.getMinimum() - 1911L, valueRange.getMaximum() - 1911L);
/*     */     } 
/*     */     
/* 329 */     return paramChronoField.range();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MinguoDate resolveDate(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 335 */     return (MinguoDate)super.resolveDate(paramMap, paramResolverStyle);
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
/*     */   Object writeReplace() {
/* 352 */     return super.writeReplace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 362 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/MinguoChronology.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */