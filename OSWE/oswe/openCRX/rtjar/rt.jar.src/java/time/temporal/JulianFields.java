/*     */ package java.time.temporal;
/*     */ 
/*     */ import java.time.DateTimeException;
/*     */ import java.time.chrono.ChronoLocalDate;
/*     */ import java.time.chrono.Chronology;
/*     */ import java.time.format.ResolverStyle;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JulianFields
/*     */ {
/*     */   private static final long JULIAN_DAY_OFFSET = 2440588L;
/* 141 */   public static final TemporalField JULIAN_DAY = Field.JULIAN_DAY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   public static final TemporalField MODIFIED_JULIAN_DAY = Field.MODIFIED_JULIAN_DAY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 202 */   public static final TemporalField RATA_DIE = Field.RATA_DIE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JulianFields() {
/* 208 */     throw new AssertionError("Not instantiable");
/*     */   }
/*     */ 
/*     */   
/*     */   private enum Field
/*     */     implements TemporalField
/*     */   {
/* 215 */     JULIAN_DAY("JulianDay", ChronoUnit.DAYS, (String)ChronoUnit.FOREVER, 2440588L),
/* 216 */     MODIFIED_JULIAN_DAY("ModifiedJulianDay", ChronoUnit.DAYS, (String)ChronoUnit.FOREVER, 40587L),
/* 217 */     RATA_DIE("RataDie", ChronoUnit.DAYS, (String)ChronoUnit.FOREVER, 719163L);
/*     */     
/*     */     private static final long serialVersionUID = -7501623920830201812L;
/*     */     
/*     */     private final transient String name;
/*     */     private final transient TemporalUnit baseUnit;
/*     */     private final transient TemporalUnit rangeUnit;
/*     */     private final transient ValueRange range;
/*     */     private final transient long offset;
/*     */     
/*     */     Field(String param1String1, TemporalUnit param1TemporalUnit1, TemporalUnit param1TemporalUnit2, long param1Long) {
/* 228 */       this.name = param1String1;
/* 229 */       this.baseUnit = param1TemporalUnit1;
/* 230 */       this.rangeUnit = param1TemporalUnit2;
/* 231 */       this.range = ValueRange.of(-365243219162L + param1Long, 365241780471L + param1Long);
/* 232 */       this.offset = param1Long;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public TemporalUnit getBaseUnit() {
/* 238 */       return this.baseUnit;
/*     */     }
/*     */ 
/*     */     
/*     */     public TemporalUnit getRangeUnit() {
/* 243 */       return this.rangeUnit;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDateBased() {
/* 248 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isTimeBased() {
/* 253 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public ValueRange range() {
/* 258 */       return this.range;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSupportedBy(TemporalAccessor param1TemporalAccessor) {
/* 264 */       return param1TemporalAccessor.isSupported(ChronoField.EPOCH_DAY);
/*     */     }
/*     */ 
/*     */     
/*     */     public ValueRange rangeRefinedBy(TemporalAccessor param1TemporalAccessor) {
/* 269 */       if (!isSupportedBy(param1TemporalAccessor)) {
/* 270 */         throw new DateTimeException("Unsupported field: " + this);
/*     */       }
/* 272 */       return range();
/*     */     }
/*     */ 
/*     */     
/*     */     public long getFrom(TemporalAccessor param1TemporalAccessor) {
/* 277 */       return param1TemporalAccessor.getLong(ChronoField.EPOCH_DAY) + this.offset;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <R extends Temporal> R adjustInto(R param1R, long param1Long) {
/* 283 */       if (!range().isValidValue(param1Long)) {
/* 284 */         throw new DateTimeException("Invalid value: " + this.name + " " + param1Long);
/*     */       }
/* 286 */       return (R)param1R.with(ChronoField.EPOCH_DAY, Math.subtractExact(param1Long, this.offset));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ChronoLocalDate resolve(Map<TemporalField, Long> param1Map, TemporalAccessor param1TemporalAccessor, ResolverStyle param1ResolverStyle) {
/* 293 */       long l = ((Long)param1Map.remove(this)).longValue();
/* 294 */       Chronology chronology = Chronology.from(param1TemporalAccessor);
/* 295 */       if (param1ResolverStyle == ResolverStyle.LENIENT) {
/* 296 */         return chronology.dateEpochDay(Math.subtractExact(l, this.offset));
/*     */       }
/* 298 */       range().checkValidValue(l, this);
/* 299 */       return chronology.dateEpochDay(l - this.offset);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 305 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/temporal/JulianFields.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */