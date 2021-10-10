/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.Serializable;
/*     */ import java.time.LocalTime;
/*     */ import java.time.ZoneId;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.time.temporal.Temporal;
/*     */ import java.time.temporal.TemporalAdjuster;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.TemporalUnit;
/*     */ import java.time.temporal.ValueRange;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ChronoLocalDateTimeImpl<D extends ChronoLocalDate>
/*     */   implements ChronoLocalDateTime<D>, Temporal, TemporalAdjuster, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4556003607393004514L;
/*     */   static final int HOURS_PER_DAY = 24;
/*     */   static final int MINUTES_PER_HOUR = 60;
/*     */   static final int MINUTES_PER_DAY = 1440;
/*     */   static final int SECONDS_PER_MINUTE = 60;
/*     */   static final int SECONDS_PER_HOUR = 3600;
/*     */   static final int SECONDS_PER_DAY = 86400;
/*     */   static final long MILLIS_PER_DAY = 86400000L;
/*     */   static final long MICROS_PER_DAY = 86400000000L;
/*     */   static final long NANOS_PER_SECOND = 1000000000L;
/*     */   static final long NANOS_PER_MINUTE = 60000000000L;
/*     */   static final long NANOS_PER_HOUR = 3600000000000L;
/*     */   static final long NANOS_PER_DAY = 86400000000000L;
/*     */   private final transient D date;
/*     */   private final transient LocalTime time;
/*     */   
/*     */   static <R extends ChronoLocalDate> ChronoLocalDateTimeImpl<R> of(R paramR, LocalTime paramLocalTime) {
/* 174 */     return new ChronoLocalDateTimeImpl<>(paramR, paramLocalTime);
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
/*     */   static <R extends ChronoLocalDate> ChronoLocalDateTimeImpl<R> ensureValid(Chronology paramChronology, Temporal paramTemporal) {
/* 188 */     ChronoLocalDateTimeImpl<R> chronoLocalDateTimeImpl = (ChronoLocalDateTimeImpl)paramTemporal;
/* 189 */     if (!paramChronology.equals(chronoLocalDateTimeImpl.getChronology())) {
/* 190 */       throw new ClassCastException("Chronology mismatch, required: " + paramChronology.getId() + ", actual: " + chronoLocalDateTimeImpl
/* 191 */           .getChronology().getId());
/*     */     }
/* 193 */     return chronoLocalDateTimeImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChronoLocalDateTimeImpl(D paramD, LocalTime paramLocalTime) {
/* 203 */     Objects.requireNonNull(paramD, "date");
/* 204 */     Objects.requireNonNull(paramLocalTime, "time");
/* 205 */     this.date = paramD;
/* 206 */     this.time = paramLocalTime;
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
/*     */   private ChronoLocalDateTimeImpl<D> with(Temporal paramTemporal, LocalTime paramLocalTime) {
/* 218 */     if (this.date == paramTemporal && this.time == paramLocalTime) {
/* 219 */       return this;
/*     */     }
/*     */     
/* 222 */     D d = (D)ChronoLocalDateImpl.ensureValid(this.date.getChronology(), paramTemporal);
/* 223 */     return new ChronoLocalDateTimeImpl(d, paramLocalTime);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public D toLocalDate() {
/* 229 */     return this.date;
/*     */   }
/*     */ 
/*     */   
/*     */   public LocalTime toLocalTime() {
/* 234 */     return this.time;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupported(TemporalField paramTemporalField) {
/* 240 */     if (paramTemporalField instanceof ChronoField) {
/* 241 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/* 242 */       return (chronoField.isDateBased() || chronoField.isTimeBased());
/*     */     } 
/* 244 */     return (paramTemporalField != null && paramTemporalField.isSupportedBy(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public ValueRange range(TemporalField paramTemporalField) {
/* 249 */     if (paramTemporalField instanceof ChronoField) {
/* 250 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/* 251 */       return chronoField.isTimeBased() ? this.time.range(paramTemporalField) : this.date.range(paramTemporalField);
/*     */     } 
/* 253 */     return paramTemporalField.rangeRefinedBy(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int get(TemporalField paramTemporalField) {
/* 258 */     if (paramTemporalField instanceof ChronoField) {
/* 259 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/* 260 */       return chronoField.isTimeBased() ? this.time.get(paramTemporalField) : this.date.get(paramTemporalField);
/*     */     } 
/* 262 */     return range(paramTemporalField).checkValidIntValue(getLong(paramTemporalField), paramTemporalField);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(TemporalField paramTemporalField) {
/* 267 */     if (paramTemporalField instanceof ChronoField) {
/* 268 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/* 269 */       return chronoField.isTimeBased() ? this.time.getLong(paramTemporalField) : this.date.getLong(paramTemporalField);
/*     */     } 
/* 271 */     return paramTemporalField.getFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoLocalDateTimeImpl<D> with(TemporalAdjuster paramTemporalAdjuster) {
/* 278 */     if (paramTemporalAdjuster instanceof ChronoLocalDate)
/*     */     {
/* 280 */       return with((ChronoLocalDate)paramTemporalAdjuster, this.time); } 
/* 281 */     if (paramTemporalAdjuster instanceof LocalTime)
/* 282 */       return with((Temporal)this.date, (LocalTime)paramTemporalAdjuster); 
/* 283 */     if (paramTemporalAdjuster instanceof ChronoLocalDateTimeImpl) {
/* 284 */       return ensureValid(this.date.getChronology(), (ChronoLocalDateTimeImpl)paramTemporalAdjuster);
/*     */     }
/* 286 */     return ensureValid(this.date.getChronology(), paramTemporalAdjuster.adjustInto(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public ChronoLocalDateTimeImpl<D> with(TemporalField paramTemporalField, long paramLong) {
/* 291 */     if (paramTemporalField instanceof ChronoField) {
/* 292 */       ChronoField chronoField = (ChronoField)paramTemporalField;
/* 293 */       if (chronoField.isTimeBased()) {
/* 294 */         return with((Temporal)this.date, this.time.with(paramTemporalField, paramLong));
/*     */       }
/* 296 */       return with(this.date.with(paramTemporalField, paramLong), this.time);
/*     */     } 
/*     */     
/* 299 */     return ensureValid(this.date.getChronology(), paramTemporalField.adjustInto(this, paramLong));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoLocalDateTimeImpl<D> plus(long paramLong, TemporalUnit paramTemporalUnit) {
/* 305 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 306 */       ChronoUnit chronoUnit = (ChronoUnit)paramTemporalUnit;
/* 307 */       switch (chronoUnit) { case NANOS:
/* 308 */           return plusNanos(paramLong);
/* 309 */         case MICROS: return plusDays(paramLong / 86400000000L).plusNanos(paramLong % 86400000000L * 1000L);
/* 310 */         case MILLIS: return plusDays(paramLong / 86400000L).plusNanos(paramLong % 86400000L * 1000000L);
/* 311 */         case SECONDS: return plusSeconds(paramLong);
/* 312 */         case MINUTES: return plusMinutes(paramLong);
/* 313 */         case HOURS: return plusHours(paramLong);
/* 314 */         case HALF_DAYS: return plusDays(paramLong / 256L).plusHours(paramLong % 256L * 12L); }
/*     */       
/* 316 */       return with(this.date.plus(paramLong, paramTemporalUnit), this.time);
/*     */     } 
/* 318 */     return ensureValid(this.date.getChronology(), paramTemporalUnit.addTo(this, paramLong));
/*     */   }
/*     */   
/*     */   private ChronoLocalDateTimeImpl<D> plusDays(long paramLong) {
/* 322 */     return with(this.date.plus(paramLong, ChronoUnit.DAYS), this.time);
/*     */   }
/*     */   
/*     */   private ChronoLocalDateTimeImpl<D> plusHours(long paramLong) {
/* 326 */     return plusWithOverflow(this.date, paramLong, 0L, 0L, 0L);
/*     */   }
/*     */   
/*     */   private ChronoLocalDateTimeImpl<D> plusMinutes(long paramLong) {
/* 330 */     return plusWithOverflow(this.date, 0L, paramLong, 0L, 0L);
/*     */   }
/*     */   
/*     */   ChronoLocalDateTimeImpl<D> plusSeconds(long paramLong) {
/* 334 */     return plusWithOverflow(this.date, 0L, 0L, paramLong, 0L);
/*     */   }
/*     */   
/*     */   private ChronoLocalDateTimeImpl<D> plusNanos(long paramLong) {
/* 338 */     return plusWithOverflow(this.date, 0L, 0L, 0L, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ChronoLocalDateTimeImpl<D> plusWithOverflow(D paramD, long paramLong1, long paramLong2, long paramLong3, long paramLong4) {
/* 344 */     if ((paramLong1 | paramLong2 | paramLong3 | paramLong4) == 0L) {
/* 345 */       return with((Temporal)paramD, this.time);
/*     */     }
/* 347 */     long l1 = paramLong4 / 86400000000000L + paramLong3 / 86400L + paramLong2 / 1440L + paramLong1 / 24L;
/*     */ 
/*     */ 
/*     */     
/* 351 */     long l2 = paramLong4 % 86400000000000L + paramLong3 % 86400L * 1000000000L + paramLong2 % 1440L * 60000000000L + paramLong1 % 24L * 3600000000000L;
/*     */ 
/*     */ 
/*     */     
/* 355 */     long l3 = this.time.toNanoOfDay();
/* 356 */     l2 += l3;
/* 357 */     l1 += Math.floorDiv(l2, 86400000000000L);
/* 358 */     long l4 = Math.floorMod(l2, 86400000000000L);
/* 359 */     LocalTime localTime = (l4 == l3) ? this.time : LocalTime.ofNanoOfDay(l4);
/* 360 */     return with(paramD.plus(l1, ChronoUnit.DAYS), localTime);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoZonedDateTime<D> atZone(ZoneId paramZoneId) {
/* 366 */     return ChronoZonedDateTimeImpl.ofBest(this, paramZoneId, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long until(Temporal paramTemporal, TemporalUnit paramTemporalUnit) {
/* 372 */     Objects.requireNonNull(paramTemporal, "endExclusive");
/*     */     
/* 374 */     ChronoLocalDateTime<? extends ChronoLocalDate> chronoLocalDateTime = getChronology().localDateTime(paramTemporal);
/* 375 */     if (paramTemporalUnit instanceof ChronoUnit) {
/* 376 */       if (paramTemporalUnit.isTimeBased()) {
/* 377 */         long l = chronoLocalDateTime.getLong(ChronoField.EPOCH_DAY) - this.date.getLong(ChronoField.EPOCH_DAY);
/* 378 */         switch ((ChronoUnit)paramTemporalUnit) { case NANOS:
/* 379 */             l = Math.multiplyExact(l, 86400000000000L); break;
/* 380 */           case MICROS: l = Math.multiplyExact(l, 86400000000L); break;
/* 381 */           case MILLIS: l = Math.multiplyExact(l, 86400000L); break;
/* 382 */           case SECONDS: l = Math.multiplyExact(l, 86400L); break;
/* 383 */           case MINUTES: l = Math.multiplyExact(l, 1440L); break;
/* 384 */           case HOURS: l = Math.multiplyExact(l, 24L); break;
/* 385 */           case HALF_DAYS: l = Math.multiplyExact(l, 2L); break; }
/*     */         
/* 387 */         return Math.addExact(l, this.time.until(chronoLocalDateTime.toLocalTime(), paramTemporalUnit));
/*     */       } 
/* 389 */       ChronoLocalDate chronoLocalDate = (ChronoLocalDate)chronoLocalDateTime.toLocalDate();
/* 390 */       if (chronoLocalDateTime.toLocalTime().isBefore(this.time)) {
/* 391 */         chronoLocalDate = chronoLocalDate.minus(1L, ChronoUnit.DAYS);
/*     */       }
/* 393 */       return this.date.until(chronoLocalDate, paramTemporalUnit);
/*     */     } 
/* 395 */     Objects.requireNonNull(paramTemporalUnit, "unit");
/* 396 */     return paramTemporalUnit.between(this, chronoLocalDateTime);
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
/*     */   private Object writeReplace() {
/* 413 */     return new Ser((byte)2, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 423 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*     */   }
/*     */   
/*     */   void writeExternal(ObjectOutput paramObjectOutput) throws IOException {
/* 427 */     paramObjectOutput.writeObject(this.date);
/* 428 */     paramObjectOutput.writeObject(this.time);
/*     */   }
/*     */   
/*     */   static ChronoLocalDateTime<?> readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException {
/* 432 */     ChronoLocalDate chronoLocalDate = (ChronoLocalDate)paramObjectInput.readObject();
/* 433 */     LocalTime localTime = (LocalTime)paramObjectInput.readObject();
/* 434 */     return chronoLocalDate.atTime(localTime);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 440 */     if (this == paramObject) {
/* 441 */       return true;
/*     */     }
/* 443 */     if (paramObject instanceof ChronoLocalDateTime) {
/* 444 */       return (compareTo((ChronoLocalDateTime)paramObject) == 0);
/*     */     }
/* 446 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 451 */     return toLocalDate().hashCode() ^ toLocalTime().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 456 */     return toLocalDate().toString() + 'T' + toLocalTime().toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/ChronoLocalDateTimeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */