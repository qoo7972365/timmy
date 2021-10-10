/*     */ package java.time.zone;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.time.DayOfWeek;
/*     */ import java.time.LocalDate;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.LocalTime;
/*     */ import java.time.Month;
/*     */ import java.time.ZoneOffset;
/*     */ import java.time.chrono.IsoChronology;
/*     */ import java.time.temporal.TemporalAdjusters;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ZoneOffsetTransitionRule
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6889046316657758795L;
/*     */   private final Month month;
/*     */   private final byte dom;
/*     */   private final DayOfWeek dow;
/*     */   private final LocalTime time;
/*     */   private final boolean timeEndOfDay;
/*     */   private final TimeDefinition timeDefinition;
/*     */   private final ZoneOffset standardOffset;
/*     */   private final ZoneOffset offsetBefore;
/*     */   private final ZoneOffset offsetAfter;
/*     */   
/*     */   public static ZoneOffsetTransitionRule of(Month paramMonth, int paramInt, DayOfWeek paramDayOfWeek, LocalTime paramLocalTime, boolean paramBoolean, TimeDefinition paramTimeDefinition, ZoneOffset paramZoneOffset1, ZoneOffset paramZoneOffset2, ZoneOffset paramZoneOffset3) {
/* 181 */     Objects.requireNonNull(paramMonth, "month");
/* 182 */     Objects.requireNonNull(paramLocalTime, "time");
/* 183 */     Objects.requireNonNull(paramTimeDefinition, "timeDefnition");
/* 184 */     Objects.requireNonNull(paramZoneOffset1, "standardOffset");
/* 185 */     Objects.requireNonNull(paramZoneOffset2, "offsetBefore");
/* 186 */     Objects.requireNonNull(paramZoneOffset3, "offsetAfter");
/* 187 */     if (paramInt < -28 || paramInt > 31 || paramInt == 0) {
/* 188 */       throw new IllegalArgumentException("Day of month indicator must be between -28 and 31 inclusive excluding zero");
/*     */     }
/* 190 */     if (paramBoolean && !paramLocalTime.equals(LocalTime.MIDNIGHT)) {
/* 191 */       throw new IllegalArgumentException("Time must be midnight when end of day flag is true");
/*     */     }
/* 193 */     return new ZoneOffsetTransitionRule(paramMonth, paramInt, paramDayOfWeek, paramLocalTime, paramBoolean, paramTimeDefinition, paramZoneOffset1, paramZoneOffset2, paramZoneOffset3);
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
/*     */   ZoneOffsetTransitionRule(Month paramMonth, int paramInt, DayOfWeek paramDayOfWeek, LocalTime paramLocalTime, boolean paramBoolean, TimeDefinition paramTimeDefinition, ZoneOffset paramZoneOffset1, ZoneOffset paramZoneOffset2, ZoneOffset paramZoneOffset3) {
/* 223 */     this.month = paramMonth;
/* 224 */     this.dom = (byte)paramInt;
/* 225 */     this.dow = paramDayOfWeek;
/* 226 */     this.time = paramLocalTime;
/* 227 */     this.timeEndOfDay = paramBoolean;
/* 228 */     this.timeDefinition = paramTimeDefinition;
/* 229 */     this.standardOffset = paramZoneOffset1;
/* 230 */     this.offsetBefore = paramZoneOffset2;
/* 231 */     this.offsetAfter = paramZoneOffset3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 242 */     throw new InvalidObjectException("Deserialization via serialization delegate");
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
/*     */ 
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
/* 291 */     return new Ser((byte)3, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 301 */     int i = this.timeEndOfDay ? 86400 : this.time.toSecondOfDay();
/* 302 */     int j = this.standardOffset.getTotalSeconds();
/* 303 */     int k = this.offsetBefore.getTotalSeconds() - j;
/* 304 */     int m = this.offsetAfter.getTotalSeconds() - j;
/* 305 */     byte b1 = (i % 3600 == 0) ? (this.timeEndOfDay ? 24 : this.time.getHour()) : 31;
/* 306 */     byte b2 = (j % 900 == 0) ? (j / 900 + 128) : 255;
/* 307 */     byte b3 = (k == 0 || k == 1800 || k == 3600) ? (k / 1800) : 3;
/* 308 */     byte b4 = (m == 0 || m == 1800 || m == 3600) ? (m / 1800) : 3;
/* 309 */     byte b5 = (this.dow == null) ? 0 : this.dow.getValue();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 314 */     int n = (this.month.getValue() << 28) + (this.dom + 32 << 22) + (b5 << 19) + (b1 << 14) + (this.timeDefinition.ordinal() << 12) + (b2 << 4) + (b3 << 2) + b4;
/*     */ 
/*     */ 
/*     */     
/* 318 */     paramDataOutput.writeInt(n);
/* 319 */     if (b1 == 31) {
/* 320 */       paramDataOutput.writeInt(i);
/*     */     }
/* 322 */     if (b2 == 'ÿ') {
/* 323 */       paramDataOutput.writeInt(j);
/*     */     }
/* 325 */     if (b3 == 3) {
/* 326 */       paramDataOutput.writeInt(this.offsetBefore.getTotalSeconds());
/*     */     }
/* 328 */     if (b4 == 3) {
/* 329 */       paramDataOutput.writeInt(this.offsetAfter.getTotalSeconds());
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
/*     */   static ZoneOffsetTransitionRule readExternal(DataInput paramDataInput) throws IOException {
/* 341 */     int i = paramDataInput.readInt();
/* 342 */     Month month = Month.of(i >>> 28);
/* 343 */     int j = ((i & 0xFC00000) >>> 22) - 32;
/* 344 */     int k = (i & 0x380000) >>> 19;
/* 345 */     DayOfWeek dayOfWeek = (k == 0) ? null : DayOfWeek.of(k);
/* 346 */     int m = (i & 0x7C000) >>> 14;
/* 347 */     TimeDefinition timeDefinition = TimeDefinition.values()[(i & 0x3000) >>> 12];
/* 348 */     int n = (i & 0xFF0) >>> 4;
/* 349 */     int i1 = (i & 0xC) >>> 2;
/* 350 */     int i2 = i & 0x3;
/* 351 */     LocalTime localTime = (m == 31) ? LocalTime.ofSecondOfDay(paramDataInput.readInt()) : LocalTime.of(m % 24, 0);
/* 352 */     ZoneOffset zoneOffset1 = (n == 255) ? ZoneOffset.ofTotalSeconds(paramDataInput.readInt()) : ZoneOffset.ofTotalSeconds((n - 128) * 900);
/* 353 */     ZoneOffset zoneOffset2 = (i1 == 3) ? ZoneOffset.ofTotalSeconds(paramDataInput.readInt()) : ZoneOffset.ofTotalSeconds(zoneOffset1.getTotalSeconds() + i1 * 1800);
/* 354 */     ZoneOffset zoneOffset3 = (i2 == 3) ? ZoneOffset.ofTotalSeconds(paramDataInput.readInt()) : ZoneOffset.ofTotalSeconds(zoneOffset1.getTotalSeconds() + i2 * 1800);
/* 355 */     return of(month, j, dayOfWeek, localTime, (m == 24), timeDefinition, zoneOffset1, zoneOffset2, zoneOffset3);
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
/*     */   public Month getMonth() {
/* 370 */     return this.month;
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
/*     */   public int getDayOfMonthIndicator() {
/* 392 */     return this.dom;
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
/*     */   public DayOfWeek getDayOfWeek() {
/* 408 */     return this.dow;
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
/*     */   public LocalTime getLocalTime() {
/* 420 */     return this.time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMidnightEndOfDay() {
/* 431 */     return this.timeEndOfDay;
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
/*     */   public TimeDefinition getTimeDefinition() {
/* 443 */     return this.timeDefinition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZoneOffset getStandardOffset() {
/* 452 */     return this.standardOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZoneOffset getOffsetBefore() {
/* 461 */     return this.offsetBefore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZoneOffset getOffsetAfter() {
/* 470 */     return this.offsetAfter;
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
/*     */   public ZoneOffsetTransition createTransition(int paramInt) {
/*     */     LocalDate localDate;
/* 484 */     if (this.dom < 0) {
/* 485 */       localDate = LocalDate.of(paramInt, this.month, this.month.length(IsoChronology.INSTANCE.isLeapYear(paramInt)) + 1 + this.dom);
/* 486 */       if (this.dow != null) {
/* 487 */         localDate = localDate.with(TemporalAdjusters.previousOrSame(this.dow));
/*     */       }
/*     */     } else {
/* 490 */       localDate = LocalDate.of(paramInt, this.month, this.dom);
/* 491 */       if (this.dow != null) {
/* 492 */         localDate = localDate.with(TemporalAdjusters.nextOrSame(this.dow));
/*     */       }
/*     */     } 
/* 495 */     if (this.timeEndOfDay) {
/* 496 */       localDate = localDate.plusDays(1L);
/*     */     }
/* 498 */     LocalDateTime localDateTime1 = LocalDateTime.of(localDate, this.time);
/* 499 */     LocalDateTime localDateTime2 = this.timeDefinition.createDateTime(localDateTime1, this.standardOffset, this.offsetBefore);
/* 500 */     return new ZoneOffsetTransition(localDateTime2, this.offsetBefore, this.offsetAfter);
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
/*     */   public boolean equals(Object paramObject) {
/* 514 */     if (paramObject == this) {
/* 515 */       return true;
/*     */     }
/* 517 */     if (paramObject instanceof ZoneOffsetTransitionRule) {
/* 518 */       ZoneOffsetTransitionRule zoneOffsetTransitionRule = (ZoneOffsetTransitionRule)paramObject;
/* 519 */       return (this.month == zoneOffsetTransitionRule.month && this.dom == zoneOffsetTransitionRule.dom && this.dow == zoneOffsetTransitionRule.dow && this.timeDefinition == zoneOffsetTransitionRule.timeDefinition && this.time
/*     */         
/* 521 */         .equals(zoneOffsetTransitionRule.time) && this.timeEndOfDay == zoneOffsetTransitionRule.timeEndOfDay && this.standardOffset
/*     */         
/* 523 */         .equals(zoneOffsetTransitionRule.standardOffset) && this.offsetBefore
/* 524 */         .equals(zoneOffsetTransitionRule.offsetBefore) && this.offsetAfter
/* 525 */         .equals(zoneOffsetTransitionRule.offsetAfter));
/*     */     } 
/* 527 */     return false;
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
/*     */   public int hashCode() {
/* 539 */     int i = (this.time.toSecondOfDay() + (this.timeEndOfDay ? 1 : 0) << 15) + (this.month.ordinal() << 11) + (this.dom + 32 << 5) + (((this.dow == null) ? 7 : this.dow.ordinal()) << 2) + this.timeDefinition.ordinal();
/* 540 */     return i ^ this.standardOffset.hashCode() ^ this.offsetBefore
/* 541 */       .hashCode() ^ this.offsetAfter.hashCode();
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
/* 552 */     StringBuilder stringBuilder = new StringBuilder();
/* 553 */     stringBuilder.append("TransitionRule[")
/* 554 */       .append((this.offsetBefore.compareTo(this.offsetAfter) > 0) ? "Gap " : "Overlap ")
/* 555 */       .append(this.offsetBefore).append(" to ").append(this.offsetAfter).append(", ");
/* 556 */     if (this.dow != null) {
/* 557 */       if (this.dom == -1) {
/* 558 */         stringBuilder.append(this.dow.name()).append(" on or before last day of ").append(this.month.name());
/* 559 */       } else if (this.dom < 0) {
/* 560 */         stringBuilder.append(this.dow.name()).append(" on or before last day minus ").append(-this.dom - 1).append(" of ").append(this.month.name());
/*     */       } else {
/* 562 */         stringBuilder.append(this.dow.name()).append(" on or after ").append(this.month.name()).append(' ').append(this.dom);
/*     */       } 
/*     */     } else {
/* 565 */       stringBuilder.append(this.month.name()).append(' ').append(this.dom);
/*     */     } 
/* 567 */     stringBuilder.append(" at ").append(this.timeEndOfDay ? "24:00" : this.time.toString())
/* 568 */       .append(" ").append(this.timeDefinition)
/* 569 */       .append(", standard offset ").append(this.standardOffset)
/* 570 */       .append(']');
/* 571 */     return stringBuilder.toString();
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
/*     */   public enum TimeDefinition
/*     */   {
/* 588 */     UTC,
/*     */     
/* 590 */     WALL,
/*     */     
/* 592 */     STANDARD;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public LocalDateTime createDateTime(LocalDateTime param1LocalDateTime, ZoneOffset param1ZoneOffset1, ZoneOffset param1ZoneOffset2) {
/*     */       int i;
/* 612 */       switch (this) {
/*     */         case UTC:
/* 614 */           i = param1ZoneOffset2.getTotalSeconds() - ZoneOffset.UTC.getTotalSeconds();
/* 615 */           return param1LocalDateTime.plusSeconds(i);
/*     */         
/*     */         case STANDARD:
/* 618 */           i = param1ZoneOffset2.getTotalSeconds() - param1ZoneOffset1.getTotalSeconds();
/* 619 */           return param1LocalDateTime.plusSeconds(i);
/*     */       } 
/*     */       
/* 622 */       return param1LocalDateTime;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/zone/ZoneOffsetTransitionRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */