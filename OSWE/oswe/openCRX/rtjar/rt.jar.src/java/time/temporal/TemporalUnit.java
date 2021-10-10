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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface TemporalUnit
/*     */ {
/*     */   Duration getDuration();
/*     */   
/*     */   boolean isDurationEstimated();
/*     */   
/*     */   boolean isDateBased();
/*     */   
/*     */   boolean isTimeBased();
/*     */   
/*     */   default boolean isSupportedBy(Temporal paramTemporal) {
/* 169 */     if (paramTemporal instanceof java.time.LocalTime) {
/* 170 */       return isTimeBased();
/*     */     }
/* 172 */     if (paramTemporal instanceof java.time.chrono.ChronoLocalDate) {
/* 173 */       return isDateBased();
/*     */     }
/* 175 */     if (paramTemporal instanceof java.time.chrono.ChronoLocalDateTime || paramTemporal instanceof java.time.chrono.ChronoZonedDateTime) {
/* 176 */       return true;
/*     */     }
/*     */     try {
/* 179 */       paramTemporal.plus(1L, this);
/* 180 */       return true;
/* 181 */     } catch (UnsupportedTemporalTypeException unsupportedTemporalTypeException) {
/* 182 */       return false;
/* 183 */     } catch (RuntimeException runtimeException) {
/*     */       try {
/* 185 */         paramTemporal.plus(-1L, this);
/* 186 */         return true;
/* 187 */       } catch (RuntimeException runtimeException1) {
/* 188 */         return false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   <R extends Temporal> R addTo(R paramR, long paramLong);
/*     */   
/*     */   long between(Temporal paramTemporal1, Temporal paramTemporal2);
/*     */   
/*     */   String toString();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/temporal/TemporalUnit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */