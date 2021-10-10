/*     */ package java.time.temporal;
/*     */ 
/*     */ import java.time.Duration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum ChronoUnit
/*     */   implements TemporalUnit
/*     */ {
/*  83 */   NANOS("Nanos", Duration.ofNanos(1L)),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   MICROS("Micros", Duration.ofNanos(1000L)),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   MILLIS("Millis", Duration.ofNanos(1000000L)),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   SECONDS("Seconds", Duration.ofSeconds(1L)),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   MINUTES("Minutes", Duration.ofSeconds(60L)),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   HOURS("Hours", Duration.ofSeconds(3600L)),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   HALF_DAYS("HalfDays", Duration.ofSeconds(43200L)),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   DAYS("Days", Duration.ofSeconds(86400L)),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   WEEKS("Weeks", Duration.ofSeconds(604800L)),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   MONTHS("Months", Duration.ofSeconds(2629746L)),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   YEARS("Years", Duration.ofSeconds(31556952L)),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   DECADES("Decades", Duration.ofSeconds(315569520L)),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   CENTURIES("Centuries", Duration.ofSeconds(3155695200L)),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 173 */   MILLENNIA("Millennia", Duration.ofSeconds(31556952000L)),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   ERAS("Eras", Duration.ofSeconds(31556952000000000L)),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   FOREVER("Forever", Duration.ofSeconds(Long.MAX_VALUE, 999999999L));
/*     */   
/*     */   private final String name;
/*     */   private final Duration duration;
/*     */   
/*     */   ChronoUnit(String paramString1, Duration paramDuration) {
/* 196 */     this.name = paramString1;
/* 197 */     this.duration = paramDuration;
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
/*     */   public Duration getDuration() {
/* 211 */     return this.duration;
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
/*     */   public boolean isDurationEstimated() {
/* 227 */     return (compareTo(DAYS) >= 0);
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
/*     */   public boolean isDateBased() {
/* 241 */     return (compareTo(DAYS) >= 0 && this != FOREVER);
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
/*     */   public boolean isTimeBased() {
/* 254 */     return (compareTo(DAYS) < 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportedBy(Temporal paramTemporal) {
/* 260 */     return paramTemporal.isSupported(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <R extends Temporal> R addTo(R paramR, long paramLong) {
/* 266 */     return (R)paramR.plus(paramLong, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long between(Temporal paramTemporal1, Temporal paramTemporal2) {
/* 272 */     return paramTemporal1.until(paramTemporal2, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 278 */     return this.name;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/temporal/ChronoUnit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */