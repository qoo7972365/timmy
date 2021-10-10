/*     */ package java.time.format;
/*     */ 
/*     */ import java.time.DateTimeException;
/*     */ import java.time.Instant;
/*     */ import java.time.ZoneId;
/*     */ import java.time.chrono.ChronoLocalDate;
/*     */ import java.time.chrono.Chronology;
/*     */ import java.time.chrono.IsoChronology;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.TemporalQueries;
/*     */ import java.time.temporal.TemporalQuery;
/*     */ import java.time.temporal.ValueRange;
/*     */ import java.util.Locale;
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
/*     */ final class DateTimePrintContext
/*     */ {
/*     */   private TemporalAccessor temporal;
/*     */   private DateTimeFormatter formatter;
/*     */   private int optional;
/*     */   
/*     */   DateTimePrintContext(TemporalAccessor paramTemporalAccessor, DateTimeFormatter paramDateTimeFormatter) {
/* 119 */     this.temporal = adjust(paramTemporalAccessor, paramDateTimeFormatter);
/* 120 */     this.formatter = paramDateTimeFormatter;
/*     */   }
/*     */   
/*     */   private static TemporalAccessor adjust(final TemporalAccessor temporal, DateTimeFormatter paramDateTimeFormatter) {
/*     */     final ChronoLocalDate effectiveDate;
/* 125 */     Chronology chronology1 = paramDateTimeFormatter.getChronology();
/* 126 */     ZoneId zoneId1 = paramDateTimeFormatter.getZone();
/* 127 */     if (chronology1 == null && zoneId1 == null) {
/* 128 */       return temporal;
/*     */     }
/*     */ 
/*     */     
/* 132 */     Chronology chronology2 = temporal.<Chronology>query(TemporalQueries.chronology());
/* 133 */     ZoneId zoneId2 = temporal.<ZoneId>query(TemporalQueries.zoneId());
/* 134 */     if (Objects.equals(chronology1, chronology2)) {
/* 135 */       chronology1 = null;
/*     */     }
/* 137 */     if (Objects.equals(zoneId1, zoneId2)) {
/* 138 */       zoneId1 = null;
/*     */     }
/* 140 */     if (chronology1 == null && zoneId1 == null) {
/* 141 */       return temporal;
/*     */     }
/*     */ 
/*     */     
/* 145 */     final Chronology effectiveChrono = (chronology1 != null) ? chronology1 : chronology2;
/* 146 */     if (zoneId1 != null) {
/*     */       
/* 148 */       if (temporal.isSupported(ChronoField.INSTANT_SECONDS)) {
/* 149 */         Chronology chronology = (chronology3 != null) ? chronology3 : IsoChronology.INSTANCE;
/* 150 */         return chronology.zonedDateTime(Instant.from(temporal), zoneId1);
/*     */       } 
/*     */       
/* 153 */       if (zoneId1.normalized() instanceof java.time.ZoneOffset && temporal.isSupported(ChronoField.OFFSET_SECONDS) && temporal
/* 154 */         .get(ChronoField.OFFSET_SECONDS) != zoneId1.getRules().getOffset(Instant.EPOCH).getTotalSeconds()) {
/* 155 */         throw new DateTimeException("Unable to apply override zone '" + zoneId1 + "' because the temporal object being formatted has a different offset but does not represent an instant: " + temporal);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 160 */     final ZoneId effectiveZone = (zoneId1 != null) ? zoneId1 : zoneId2;
/*     */     
/* 162 */     if (chronology1 != null) {
/* 163 */       if (temporal.isSupported(ChronoField.EPOCH_DAY)) {
/* 164 */         chronoLocalDate = chronology3.date(temporal);
/*     */       } else {
/*     */         
/* 167 */         if (chronology1 != IsoChronology.INSTANCE || chronology2 != null) {
/* 168 */           for (ChronoField chronoField : ChronoField.values()) {
/* 169 */             if (chronoField.isDateBased() && temporal.isSupported(chronoField)) {
/* 170 */               throw new DateTimeException("Unable to apply override chronology '" + chronology1 + "' because the temporal object being formatted contains date fields but does not represent a whole date: " + temporal);
/*     */             }
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 176 */         chronoLocalDate = null;
/*     */       } 
/*     */     } else {
/* 179 */       chronoLocalDate = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     return new TemporalAccessor()
/*     */       {
/*     */         public boolean isSupported(TemporalField param1TemporalField) {
/* 188 */           if (effectiveDate != null && param1TemporalField.isDateBased()) {
/* 189 */             return effectiveDate.isSupported(param1TemporalField);
/*     */           }
/* 191 */           return temporal.isSupported(param1TemporalField);
/*     */         }
/*     */         
/*     */         public ValueRange range(TemporalField param1TemporalField) {
/* 195 */           if (effectiveDate != null && param1TemporalField.isDateBased()) {
/* 196 */             return effectiveDate.range(param1TemporalField);
/*     */           }
/* 198 */           return temporal.range(param1TemporalField);
/*     */         }
/*     */         
/*     */         public long getLong(TemporalField param1TemporalField) {
/* 202 */           if (effectiveDate != null && param1TemporalField.isDateBased()) {
/* 203 */             return effectiveDate.getLong(param1TemporalField);
/*     */           }
/* 205 */           return temporal.getLong(param1TemporalField);
/*     */         }
/*     */ 
/*     */         
/*     */         public <R> R query(TemporalQuery<R> param1TemporalQuery) {
/* 210 */           if (param1TemporalQuery == TemporalQueries.chronology()) {
/* 211 */             return (R)effectiveChrono;
/*     */           }
/* 213 */           if (param1TemporalQuery == TemporalQueries.zoneId()) {
/* 214 */             return (R)effectiveZone;
/*     */           }
/* 216 */           if (param1TemporalQuery == TemporalQueries.precision()) {
/* 217 */             return temporal.query(param1TemporalQuery);
/*     */           }
/* 219 */           return param1TemporalQuery.queryFrom(this);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TemporalAccessor getTemporal() {
/* 231 */     return this.temporal;
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
/*     */   Locale getLocale() {
/* 243 */     return this.formatter.getLocale();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DecimalStyle getDecimalStyle() {
/* 254 */     return this.formatter.getDecimalStyle();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void startOptional() {
/* 262 */     this.optional++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void endOptional() {
/* 269 */     this.optional--;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <R> R getValue(TemporalQuery<R> paramTemporalQuery) {
/* 280 */     R r = (R)this.temporal.query((TemporalQuery)paramTemporalQuery);
/* 281 */     if (r == null && this.optional == 0) {
/* 282 */       throw new DateTimeException("Unable to extract value: " + this.temporal.getClass());
/*     */     }
/* 284 */     return r;
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
/*     */   Long getValue(TemporalField paramTemporalField) {
/*     */     try {
/* 298 */       return Long.valueOf(this.temporal.getLong(paramTemporalField));
/* 299 */     } catch (DateTimeException dateTimeException) {
/* 300 */       if (this.optional > 0) {
/* 301 */         return null;
/*     */       }
/* 303 */       throw dateTimeException;
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
/*     */   public String toString() {
/* 315 */     return this.temporal.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/format/DateTimePrintContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */