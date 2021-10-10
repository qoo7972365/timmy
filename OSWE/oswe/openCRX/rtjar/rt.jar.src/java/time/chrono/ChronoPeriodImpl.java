/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.time.temporal.Temporal;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalAmount;
/*     */ import java.time.temporal.TemporalQueries;
/*     */ import java.time.temporal.TemporalUnit;
/*     */ import java.time.temporal.UnsupportedTemporalTypeException;
/*     */ import java.time.temporal.ValueRange;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
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
/*     */ final class ChronoPeriodImpl
/*     */   implements ChronoPeriod, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 57387258289L;
/* 109 */   private static final List<TemporalUnit> SUPPORTED_UNITS = Collections.unmodifiableList(Arrays.asList(new TemporalUnit[] { ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS }));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Chronology chrono;
/*     */ 
/*     */ 
/*     */   
/*     */   final int years;
/*     */ 
/*     */ 
/*     */   
/*     */   final int months;
/*     */ 
/*     */ 
/*     */   
/*     */   final int days;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ChronoPeriodImpl(Chronology paramChronology, int paramInt1, int paramInt2, int paramInt3) {
/* 132 */     Objects.requireNonNull(paramChronology, "chrono");
/* 133 */     this.chrono = paramChronology;
/* 134 */     this.years = paramInt1;
/* 135 */     this.months = paramInt2;
/* 136 */     this.days = paramInt3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long get(TemporalUnit paramTemporalUnit) {
/* 142 */     if (paramTemporalUnit == ChronoUnit.YEARS)
/* 143 */       return this.years; 
/* 144 */     if (paramTemporalUnit == ChronoUnit.MONTHS)
/* 145 */       return this.months; 
/* 146 */     if (paramTemporalUnit == ChronoUnit.DAYS) {
/* 147 */       return this.days;
/*     */     }
/* 149 */     throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TemporalUnit> getUnits() {
/* 155 */     return SUPPORTED_UNITS;
/*     */   }
/*     */ 
/*     */   
/*     */   public Chronology getChronology() {
/* 160 */     return this.chrono;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isZero() {
/* 166 */     return (this.years == 0 && this.months == 0 && this.days == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNegative() {
/* 171 */     return (this.years < 0 || this.months < 0 || this.days < 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoPeriod plus(TemporalAmount paramTemporalAmount) {
/* 177 */     ChronoPeriodImpl chronoPeriodImpl = validateAmount(paramTemporalAmount);
/* 178 */     return new ChronoPeriodImpl(this.chrono, 
/*     */         
/* 180 */         Math.addExact(this.years, chronoPeriodImpl.years), 
/* 181 */         Math.addExact(this.months, chronoPeriodImpl.months), 
/* 182 */         Math.addExact(this.days, chronoPeriodImpl.days));
/*     */   }
/*     */ 
/*     */   
/*     */   public ChronoPeriod minus(TemporalAmount paramTemporalAmount) {
/* 187 */     ChronoPeriodImpl chronoPeriodImpl = validateAmount(paramTemporalAmount);
/* 188 */     return new ChronoPeriodImpl(this.chrono, 
/*     */         
/* 190 */         Math.subtractExact(this.years, chronoPeriodImpl.years), 
/* 191 */         Math.subtractExact(this.months, chronoPeriodImpl.months), 
/* 192 */         Math.subtractExact(this.days, chronoPeriodImpl.days));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChronoPeriodImpl validateAmount(TemporalAmount paramTemporalAmount) {
/* 202 */     Objects.requireNonNull(paramTemporalAmount, "amount");
/* 203 */     if (!(paramTemporalAmount instanceof ChronoPeriodImpl)) {
/* 204 */       throw new DateTimeException("Unable to obtain ChronoPeriod from TemporalAmount: " + paramTemporalAmount.getClass());
/*     */     }
/* 206 */     ChronoPeriodImpl chronoPeriodImpl = (ChronoPeriodImpl)paramTemporalAmount;
/* 207 */     if (!this.chrono.equals(chronoPeriodImpl.getChronology())) {
/* 208 */       throw new ClassCastException("Chronology mismatch, expected: " + this.chrono.getId() + ", actual: " + chronoPeriodImpl.getChronology().getId());
/*     */     }
/* 210 */     return chronoPeriodImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoPeriod multipliedBy(int paramInt) {
/* 216 */     if (isZero() || paramInt == 1) {
/* 217 */       return this;
/*     */     }
/* 219 */     return new ChronoPeriodImpl(this.chrono, 
/*     */         
/* 221 */         Math.multiplyExact(this.years, paramInt), 
/* 222 */         Math.multiplyExact(this.months, paramInt), 
/* 223 */         Math.multiplyExact(this.days, paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoPeriod normalized() {
/* 229 */     long l = monthRange();
/* 230 */     if (l > 0L) {
/* 231 */       long l1 = this.years * l + this.months;
/* 232 */       long l2 = l1 / l;
/* 233 */       int i = (int)(l1 % l);
/* 234 */       if (l2 == this.years && i == this.months) {
/* 235 */         return this;
/*     */       }
/* 237 */       return new ChronoPeriodImpl(this.chrono, Math.toIntExact(l2), i, this.days);
/*     */     } 
/*     */     
/* 240 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long monthRange() {
/* 249 */     ValueRange valueRange = this.chrono.range(ChronoField.MONTH_OF_YEAR);
/* 250 */     if (valueRange.isFixed() && valueRange.isIntValue()) {
/* 251 */       return valueRange.getMaximum() - valueRange.getMinimum() + 1L;
/*     */     }
/* 253 */     return -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Temporal addTo(Temporal paramTemporal) {
/* 259 */     validateChrono(paramTemporal);
/* 260 */     if (this.months == 0) {
/* 261 */       if (this.years != 0) {
/* 262 */         paramTemporal = paramTemporal.plus(this.years, ChronoUnit.YEARS);
/*     */       }
/*     */     } else {
/* 265 */       long l = monthRange();
/* 266 */       if (l > 0L) {
/* 267 */         paramTemporal = paramTemporal.plus(this.years * l + this.months, ChronoUnit.MONTHS);
/*     */       } else {
/* 269 */         if (this.years != 0) {
/* 270 */           paramTemporal = paramTemporal.plus(this.years, ChronoUnit.YEARS);
/*     */         }
/* 272 */         paramTemporal = paramTemporal.plus(this.months, ChronoUnit.MONTHS);
/*     */       } 
/*     */     } 
/* 275 */     if (this.days != 0) {
/* 276 */       paramTemporal = paramTemporal.plus(this.days, ChronoUnit.DAYS);
/*     */     }
/* 278 */     return paramTemporal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Temporal subtractFrom(Temporal paramTemporal) {
/* 285 */     validateChrono(paramTemporal);
/* 286 */     if (this.months == 0) {
/* 287 */       if (this.years != 0) {
/* 288 */         paramTemporal = paramTemporal.minus(this.years, ChronoUnit.YEARS);
/*     */       }
/*     */     } else {
/* 291 */       long l = monthRange();
/* 292 */       if (l > 0L) {
/* 293 */         paramTemporal = paramTemporal.minus(this.years * l + this.months, ChronoUnit.MONTHS);
/*     */       } else {
/* 295 */         if (this.years != 0) {
/* 296 */           paramTemporal = paramTemporal.minus(this.years, ChronoUnit.YEARS);
/*     */         }
/* 298 */         paramTemporal = paramTemporal.minus(this.months, ChronoUnit.MONTHS);
/*     */       } 
/*     */     } 
/* 301 */     if (this.days != 0) {
/* 302 */       paramTemporal = paramTemporal.minus(this.days, ChronoUnit.DAYS);
/*     */     }
/* 304 */     return paramTemporal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateChrono(TemporalAccessor paramTemporalAccessor) {
/* 311 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/* 312 */     Chronology chronology = paramTemporalAccessor.<Chronology>query(TemporalQueries.chronology());
/* 313 */     if (chronology != null && !this.chrono.equals(chronology)) {
/* 314 */       throw new DateTimeException("Chronology mismatch, expected: " + this.chrono.getId() + ", actual: " + chronology.getId());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 321 */     if (this == paramObject) {
/* 322 */       return true;
/*     */     }
/* 324 */     if (paramObject instanceof ChronoPeriodImpl) {
/* 325 */       ChronoPeriodImpl chronoPeriodImpl = (ChronoPeriodImpl)paramObject;
/* 326 */       return (this.years == chronoPeriodImpl.years && this.months == chronoPeriodImpl.months && this.days == chronoPeriodImpl.days && this.chrono
/* 327 */         .equals(chronoPeriodImpl.chrono));
/*     */     } 
/* 329 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 334 */     return this.years + Integer.rotateLeft(this.months, 8) + Integer.rotateLeft(this.days, 16) ^ this.chrono.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 340 */     if (isZero()) {
/* 341 */       return getChronology().toString() + " P0D";
/*     */     }
/* 343 */     StringBuilder stringBuilder = new StringBuilder();
/* 344 */     stringBuilder.append(getChronology().toString()).append(' ').append('P');
/* 345 */     if (this.years != 0) {
/* 346 */       stringBuilder.append(this.years).append('Y');
/*     */     }
/* 348 */     if (this.months != 0) {
/* 349 */       stringBuilder.append(this.months).append('M');
/*     */     }
/* 351 */     if (this.days != 0) {
/* 352 */       stringBuilder.append(this.days).append('D');
/*     */     }
/* 354 */     return stringBuilder.toString();
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
/*     */   protected Object writeReplace() {
/* 373 */     return new Ser((byte)9, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ObjectStreamException {
/* 383 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*     */   }
/*     */   
/*     */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 387 */     paramDataOutput.writeUTF(this.chrono.getId());
/* 388 */     paramDataOutput.writeInt(this.years);
/* 389 */     paramDataOutput.writeInt(this.months);
/* 390 */     paramDataOutput.writeInt(this.days);
/*     */   }
/*     */   
/*     */   static ChronoPeriodImpl readExternal(DataInput paramDataInput) throws IOException {
/* 394 */     Chronology chronology = Chronology.of(paramDataInput.readUTF());
/* 395 */     int i = paramDataInput.readInt();
/* 396 */     int j = paramDataInput.readInt();
/* 397 */     int k = paramDataInput.readInt();
/* 398 */     return new ChronoPeriodImpl(chronology, i, j, k);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/ChronoPeriodImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */