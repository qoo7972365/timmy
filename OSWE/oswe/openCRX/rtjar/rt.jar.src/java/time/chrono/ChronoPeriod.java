/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.time.temporal.Temporal;
/*     */ import java.time.temporal.TemporalAmount;
/*     */ import java.time.temporal.TemporalUnit;
/*     */ import java.util.List;
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
/*     */ public interface ChronoPeriod
/*     */   extends TemporalAmount
/*     */ {
/*     */   static ChronoPeriod between(ChronoLocalDate paramChronoLocalDate1, ChronoLocalDate paramChronoLocalDate2) {
/* 117 */     Objects.requireNonNull(paramChronoLocalDate1, "startDateInclusive");
/* 118 */     Objects.requireNonNull(paramChronoLocalDate2, "endDateExclusive");
/* 119 */     return paramChronoLocalDate1.until(paramChronoLocalDate2);
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
/*     */   long get(TemporalUnit paramTemporalUnit);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<TemporalUnit> getUnits();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Chronology getChronology();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean isZero() {
/* 173 */     for (TemporalUnit temporalUnit : getUnits()) {
/* 174 */       if (get(temporalUnit) != 0L) {
/* 175 */         return false;
/*     */       }
/*     */     } 
/* 178 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean isNegative() {
/* 187 */     for (TemporalUnit temporalUnit : getUnits()) {
/* 188 */       if (get(temporalUnit) < 0L) {
/* 189 */         return true;
/*     */       }
/*     */     } 
/* 192 */     return false;
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
/*     */   ChronoPeriod plus(TemporalAmount paramTemporalAmount);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChronoPeriod minus(TemporalAmount paramTemporalAmount);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChronoPeriod multipliedBy(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ChronoPeriod negated() {
/* 256 */     return multipliedBy(-1);
/*     */   }
/*     */   
/*     */   ChronoPeriod normalized();
/*     */   
/*     */   Temporal addTo(Temporal paramTemporal);
/*     */   
/*     */   Temporal subtractFrom(Temporal paramTemporal);
/*     */   
/*     */   boolean equals(Object paramObject);
/*     */   
/*     */   int hashCode();
/*     */   
/*     */   String toString();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/ChronoPeriod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */