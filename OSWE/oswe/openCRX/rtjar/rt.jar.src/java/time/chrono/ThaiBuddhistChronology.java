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
/*     */ import java.util.HashMap;
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
/*     */ public final class ThaiBuddhistChronology
/*     */   extends AbstractChronology
/*     */   implements Serializable
/*     */ {
/* 114 */   public static final ThaiBuddhistChronology INSTANCE = new ThaiBuddhistChronology();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 2775954514031616474L;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int YEARS_DIFFERENCE = 543;
/*     */ 
/*     */ 
/*     */   
/* 127 */   private static final HashMap<String, String[]> ERA_NARROW_NAMES = (HashMap)new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/* 131 */   private static final HashMap<String, String[]> ERA_SHORT_NAMES = (HashMap)new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/* 135 */   private static final HashMap<String, String[]> ERA_FULL_NAMES = (HashMap)new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String FALLBACK_LANGUAGE = "en";
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String TARGET_LANGUAGE = "th";
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 148 */     ERA_NARROW_NAMES.put("en", new String[] { "BB", "BE" });
/* 149 */     ERA_NARROW_NAMES.put("th", new String[] { "BB", "BE" });
/* 150 */     ERA_SHORT_NAMES.put("en", new String[] { "B.B.", "B.E." });
/* 151 */     ERA_SHORT_NAMES.put("th", new String[] { "พ.ศ.", "ปีก่อนคริสต์กาลที่" });
/*     */ 
/*     */     
/* 154 */     ERA_FULL_NAMES.put("en", new String[] { "Before Buddhist", "Budhhist Era" });
/* 155 */     ERA_FULL_NAMES.put("th", new String[] { "พุทธศักราช", "ปีก่อนคริสต์กาลที่" });
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
/*     */   public String getId() {
/* 178 */     return "ThaiBuddhist";
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
/* 195 */     return "buddhist";
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
/*     */   public ThaiBuddhistDate date(Era paramEra, int paramInt1, int paramInt2, int paramInt3) {
/* 213 */     return date(prolepticYear(paramEra, paramInt1), paramInt2, paramInt3);
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
/*     */   public ThaiBuddhistDate date(int paramInt1, int paramInt2, int paramInt3) {
/* 228 */     return new ThaiBuddhistDate(LocalDate.of(paramInt1 - 543, paramInt2, paramInt3));
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
/*     */   public ThaiBuddhistDate dateYearDay(Era paramEra, int paramInt1, int paramInt2) {
/* 244 */     return dateYearDay(prolepticYear(paramEra, paramInt1), paramInt2);
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
/*     */   public ThaiBuddhistDate dateYearDay(int paramInt1, int paramInt2) {
/* 258 */     return new ThaiBuddhistDate(LocalDate.ofYearDay(paramInt1 - 543, paramInt2));
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
/*     */   public ThaiBuddhistDate dateEpochDay(long paramLong) {
/* 270 */     return new ThaiBuddhistDate(LocalDate.ofEpochDay(paramLong));
/*     */   }
/*     */ 
/*     */   
/*     */   public ThaiBuddhistDate dateNow() {
/* 275 */     return dateNow(Clock.systemDefaultZone());
/*     */   }
/*     */ 
/*     */   
/*     */   public ThaiBuddhistDate dateNow(ZoneId paramZoneId) {
/* 280 */     return dateNow(Clock.system(paramZoneId));
/*     */   }
/*     */ 
/*     */   
/*     */   public ThaiBuddhistDate dateNow(Clock paramClock) {
/* 285 */     return date(LocalDate.now(paramClock));
/*     */   }
/*     */ 
/*     */   
/*     */   public ThaiBuddhistDate date(TemporalAccessor paramTemporalAccessor) {
/* 290 */     if (paramTemporalAccessor instanceof ThaiBuddhistDate) {
/* 291 */       return (ThaiBuddhistDate)paramTemporalAccessor;
/*     */     }
/* 293 */     return new ThaiBuddhistDate(LocalDate.from(paramTemporalAccessor));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoLocalDateTime<ThaiBuddhistDate> localDateTime(TemporalAccessor paramTemporalAccessor) {
/* 299 */     return (ChronoLocalDateTime)super.localDateTime(paramTemporalAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoZonedDateTime<ThaiBuddhistDate> zonedDateTime(TemporalAccessor paramTemporalAccessor) {
/* 305 */     return (ChronoZonedDateTime)super.zonedDateTime(paramTemporalAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoZonedDateTime<ThaiBuddhistDate> zonedDateTime(Instant paramInstant, ZoneId paramZoneId) {
/* 311 */     return (ChronoZonedDateTime)super.zonedDateTime(paramInstant, paramZoneId);
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
/* 327 */     return IsoChronology.INSTANCE.isLeapYear(paramLong - 543L);
/*     */   }
/*     */ 
/*     */   
/*     */   public int prolepticYear(Era paramEra, int paramInt) {
/* 332 */     if (!(paramEra instanceof ThaiBuddhistEra)) {
/* 333 */       throw new ClassCastException("Era must be BuddhistEra");
/*     */     }
/* 335 */     return (paramEra == ThaiBuddhistEra.BE) ? paramInt : (1 - paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public ThaiBuddhistEra eraOf(int paramInt) {
/* 340 */     return ThaiBuddhistEra.of(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Era> eras() {
/* 345 */     return Arrays.asList((Era[])ThaiBuddhistEra.values());
/*     */   }
/*     */ 
/*     */   
/*     */   public ValueRange range(ChronoField paramChronoField) {
/*     */     ValueRange valueRange;
/* 351 */     switch (paramChronoField) {
/*     */       case PROLEPTIC_MONTH:
/* 353 */         valueRange = ChronoField.PROLEPTIC_MONTH.range();
/* 354 */         return ValueRange.of(valueRange.getMinimum() + 6516L, valueRange.getMaximum() + 6516L);
/*     */       
/*     */       case YEAR_OF_ERA:
/* 357 */         valueRange = ChronoField.YEAR.range();
/* 358 */         return ValueRange.of(1L, -(valueRange.getMinimum() + 543L) + 1L, valueRange.getMaximum() + 543L);
/*     */       
/*     */       case YEAR:
/* 361 */         valueRange = ChronoField.YEAR.range();
/* 362 */         return ValueRange.of(valueRange.getMinimum() + 543L, valueRange.getMaximum() + 543L);
/*     */     } 
/*     */     
/* 365 */     return paramChronoField.range();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ThaiBuddhistDate resolveDate(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 371 */     return (ThaiBuddhistDate)super.resolveDate(paramMap, paramResolverStyle);
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
/* 388 */     return super.writeReplace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 398 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/ThaiBuddhistChronology.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */