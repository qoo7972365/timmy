/*     */ package java.time.temporal;
/*     */ 
/*     */ import java.time.LocalDate;
/*     */ import java.time.LocalTime;
/*     */ import java.time.ZoneId;
/*     */ import java.time.ZoneOffset;
/*     */ import java.time.chrono.Chronology;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TemporalQueries
/*     */ {
/*     */   static final TemporalQuery<ZoneId> ZONE_ID;
/*     */   static final TemporalQuery<Chronology> CHRONO;
/*     */   static final TemporalQuery<TemporalUnit> PRECISION;
/*     */   static final TemporalQuery<ZoneOffset> OFFSET;
/*     */   static final TemporalQuery<ZoneId> ZONE;
/*     */   static final TemporalQuery<LocalDate> LOCAL_DATE;
/*     */   static final TemporalQuery<LocalTime> LOCAL_TIME;
/*     */   
/*     */   public static TemporalQuery<ZoneId> zoneId() {
/* 168 */     return ZONE_ID;
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
/*     */   public static TemporalQuery<Chronology> chronology() {
/* 207 */     return CHRONO;
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
/*     */   public static TemporalQuery<TemporalUnit> precision() {
/* 244 */     return PRECISION;
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
/*     */   public static TemporalQuery<ZoneId> zone() {
/* 268 */     return ZONE;
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
/*     */   public static TemporalQuery<ZoneOffset> offset() {
/* 291 */     return OFFSET;
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
/*     */   public static TemporalQuery<LocalDate> localDate() {
/* 314 */     return LOCAL_DATE;
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
/*     */   public static TemporalQuery<LocalTime> localTime() {
/* 337 */     return LOCAL_TIME;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 344 */     ZONE_ID = (paramTemporalAccessor -> (ZoneId)paramTemporalAccessor.<ZoneId>query(ZONE_ID));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 350 */     CHRONO = (paramTemporalAccessor -> (Chronology)paramTemporalAccessor.<Chronology>query(CHRONO));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 356 */     PRECISION = (paramTemporalAccessor -> (TemporalUnit)paramTemporalAccessor.<TemporalUnit>query(PRECISION));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 363 */     OFFSET = (paramTemporalAccessor -> paramTemporalAccessor.isSupported(ChronoField.OFFSET_SECONDS) ? ZoneOffset.ofTotalSeconds(paramTemporalAccessor.get(ChronoField.OFFSET_SECONDS)) : null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 373 */     ZONE = (paramTemporalAccessor -> {
/*     */         ZoneId zoneId = paramTemporalAccessor.<ZoneId>query(ZONE_ID);
/*     */ 
/*     */         
/*     */         return (zoneId != null) ? zoneId : paramTemporalAccessor.<ZoneId>query((TemporalQuery)OFFSET);
/*     */       });
/*     */ 
/*     */     
/* 381 */     LOCAL_DATE = (paramTemporalAccessor -> paramTemporalAccessor.isSupported(ChronoField.EPOCH_DAY) ? LocalDate.ofEpochDay(paramTemporalAccessor.getLong(ChronoField.EPOCH_DAY)) : null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 391 */     LOCAL_TIME = (paramTemporalAccessor -> paramTemporalAccessor.isSupported(ChronoField.NANO_OF_DAY) ? LocalTime.ofNanoOfDay(paramTemporalAccessor.getLong(ChronoField.NANO_OF_DAY)) : null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/temporal/TemporalQueries.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */