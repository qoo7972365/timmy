/*     */ package sun.util.calendar;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CalendarDate
/*     */   implements Cloneable
/*     */ {
/*     */   public static final int FIELD_UNDEFINED = -2147483648;
/*     */   public static final long TIME_UNDEFINED = -9223372036854775808L;
/*     */   private Era era;
/*     */   private int year;
/*     */   private int month;
/*     */   private int dayOfMonth;
/*  71 */   private int dayOfWeek = Integer.MIN_VALUE;
/*     */   
/*     */   private boolean leapYear;
/*     */   
/*     */   private int hours;
/*     */   
/*     */   private int minutes;
/*     */   
/*     */   private int seconds;
/*     */   private int millis;
/*     */   private long fraction;
/*     */   private boolean normalized;
/*     */   private TimeZone zoneinfo;
/*     */   private int zoneOffset;
/*     */   private int daylightSaving;
/*     */   private boolean forceStandardTime;
/*     */   private Locale locale;
/*     */   
/*     */   protected CalendarDate() {
/*  90 */     this(TimeZone.getDefault());
/*     */   }
/*     */   
/*     */   protected CalendarDate(TimeZone paramTimeZone) {
/*  94 */     this.zoneinfo = paramTimeZone;
/*     */   }
/*     */   
/*     */   public Era getEra() {
/*  98 */     return this.era;
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
/*     */   public CalendarDate setEra(Era paramEra) {
/* 114 */     if (this.era == paramEra) {
/* 115 */       return this;
/*     */     }
/* 117 */     this.era = paramEra;
/* 118 */     this.normalized = false;
/* 119 */     return this;
/*     */   }
/*     */   
/*     */   public int getYear() {
/* 123 */     return this.year;
/*     */   }
/*     */   
/*     */   public CalendarDate setYear(int paramInt) {
/* 127 */     if (this.year != paramInt) {
/* 128 */       this.year = paramInt;
/* 129 */       this.normalized = false;
/*     */     } 
/* 131 */     return this;
/*     */   }
/*     */   
/*     */   public CalendarDate addYear(int paramInt) {
/* 135 */     if (paramInt != 0) {
/* 136 */       this.year += paramInt;
/* 137 */       this.normalized = false;
/*     */     } 
/* 139 */     return this;
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
/*     */   public boolean isLeapYear() {
/* 158 */     return this.leapYear;
/*     */   }
/*     */   
/*     */   void setLeapYear(boolean paramBoolean) {
/* 162 */     this.leapYear = paramBoolean;
/*     */   }
/*     */   
/*     */   public int getMonth() {
/* 166 */     return this.month;
/*     */   }
/*     */   
/*     */   public CalendarDate setMonth(int paramInt) {
/* 170 */     if (this.month != paramInt) {
/* 171 */       this.month = paramInt;
/* 172 */       this.normalized = false;
/*     */     } 
/* 174 */     return this;
/*     */   }
/*     */   
/*     */   public CalendarDate addMonth(int paramInt) {
/* 178 */     if (paramInt != 0) {
/* 179 */       this.month += paramInt;
/* 180 */       this.normalized = false;
/*     */     } 
/* 182 */     return this;
/*     */   }
/*     */   
/*     */   public int getDayOfMonth() {
/* 186 */     return this.dayOfMonth;
/*     */   }
/*     */   
/*     */   public CalendarDate setDayOfMonth(int paramInt) {
/* 190 */     if (this.dayOfMonth != paramInt) {
/* 191 */       this.dayOfMonth = paramInt;
/* 192 */       this.normalized = false;
/*     */     } 
/* 194 */     return this;
/*     */   }
/*     */   
/*     */   public CalendarDate addDayOfMonth(int paramInt) {
/* 198 */     if (paramInt != 0) {
/* 199 */       this.dayOfMonth += paramInt;
/* 200 */       this.normalized = false;
/*     */     } 
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDayOfWeek() {
/* 212 */     if (!isNormalized()) {
/* 213 */       this.dayOfWeek = Integer.MIN_VALUE;
/*     */     }
/* 215 */     return this.dayOfWeek;
/*     */   }
/*     */   
/*     */   public int getHours() {
/* 219 */     return this.hours;
/*     */   }
/*     */   
/*     */   public CalendarDate setHours(int paramInt) {
/* 223 */     if (this.hours != paramInt) {
/* 224 */       this.hours = paramInt;
/* 225 */       this.normalized = false;
/*     */     } 
/* 227 */     return this;
/*     */   }
/*     */   
/*     */   public CalendarDate addHours(int paramInt) {
/* 231 */     if (paramInt != 0) {
/* 232 */       this.hours += paramInt;
/* 233 */       this.normalized = false;
/*     */     } 
/* 235 */     return this;
/*     */   }
/*     */   
/*     */   public int getMinutes() {
/* 239 */     return this.minutes;
/*     */   }
/*     */   
/*     */   public CalendarDate setMinutes(int paramInt) {
/* 243 */     if (this.minutes != paramInt) {
/* 244 */       this.minutes = paramInt;
/* 245 */       this.normalized = false;
/*     */     } 
/* 247 */     return this;
/*     */   }
/*     */   
/*     */   public CalendarDate addMinutes(int paramInt) {
/* 251 */     if (paramInt != 0) {
/* 252 */       this.minutes += paramInt;
/* 253 */       this.normalized = false;
/*     */     } 
/* 255 */     return this;
/*     */   }
/*     */   
/*     */   public int getSeconds() {
/* 259 */     return this.seconds;
/*     */   }
/*     */   
/*     */   public CalendarDate setSeconds(int paramInt) {
/* 263 */     if (this.seconds != paramInt) {
/* 264 */       this.seconds = paramInt;
/* 265 */       this.normalized = false;
/*     */     } 
/* 267 */     return this;
/*     */   }
/*     */   
/*     */   public CalendarDate addSeconds(int paramInt) {
/* 271 */     if (paramInt != 0) {
/* 272 */       this.seconds += paramInt;
/* 273 */       this.normalized = false;
/*     */     } 
/* 275 */     return this;
/*     */   }
/*     */   
/*     */   public int getMillis() {
/* 279 */     return this.millis;
/*     */   }
/*     */   
/*     */   public CalendarDate setMillis(int paramInt) {
/* 283 */     if (this.millis != paramInt) {
/* 284 */       this.millis = paramInt;
/* 285 */       this.normalized = false;
/*     */     } 
/* 287 */     return this;
/*     */   }
/*     */   
/*     */   public CalendarDate addMillis(int paramInt) {
/* 291 */     if (paramInt != 0) {
/* 292 */       this.millis += paramInt;
/* 293 */       this.normalized = false;
/*     */     } 
/* 295 */     return this;
/*     */   }
/*     */   
/*     */   public long getTimeOfDay() {
/* 299 */     if (!isNormalized()) {
/* 300 */       return this.fraction = Long.MIN_VALUE;
/*     */     }
/* 302 */     return this.fraction;
/*     */   }
/*     */   
/*     */   public CalendarDate setDate(int paramInt1, int paramInt2, int paramInt3) {
/* 306 */     setYear(paramInt1);
/* 307 */     setMonth(paramInt2);
/* 308 */     setDayOfMonth(paramInt3);
/* 309 */     return this;
/*     */   }
/*     */   
/*     */   public CalendarDate addDate(int paramInt1, int paramInt2, int paramInt3) {
/* 313 */     addYear(paramInt1);
/* 314 */     addMonth(paramInt2);
/* 315 */     addDayOfMonth(paramInt3);
/* 316 */     return this;
/*     */   }
/*     */   
/*     */   public CalendarDate setTimeOfDay(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 320 */     setHours(paramInt1);
/* 321 */     setMinutes(paramInt2);
/* 322 */     setSeconds(paramInt3);
/* 323 */     setMillis(paramInt4);
/* 324 */     return this;
/*     */   }
/*     */   
/*     */   public CalendarDate addTimeOfDay(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 328 */     addHours(paramInt1);
/* 329 */     addMinutes(paramInt2);
/* 330 */     addSeconds(paramInt3);
/* 331 */     addMillis(paramInt4);
/* 332 */     return this;
/*     */   }
/*     */   
/*     */   protected void setTimeOfDay(long paramLong) {
/* 336 */     this.fraction = paramLong;
/*     */   }
/*     */   
/*     */   public boolean isNormalized() {
/* 340 */     return this.normalized;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStandardTime() {
/* 345 */     return this.forceStandardTime;
/*     */   }
/*     */   
/*     */   public void setStandardTime(boolean paramBoolean) {
/* 349 */     this.forceStandardTime = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean isDaylightTime() {
/* 353 */     if (isStandardTime()) {
/* 354 */       return false;
/*     */     }
/* 356 */     return (this.daylightSaving != 0);
/*     */   }
/*     */   
/*     */   protected void setLocale(Locale paramLocale) {
/* 360 */     this.locale = paramLocale;
/*     */   }
/*     */   
/*     */   public TimeZone getZone() {
/* 364 */     return this.zoneinfo;
/*     */   }
/*     */   
/*     */   public CalendarDate setZone(TimeZone paramTimeZone) {
/* 368 */     this.zoneinfo = paramTimeZone;
/* 369 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSameDate(CalendarDate paramCalendarDate) {
/* 378 */     return (getDayOfWeek() == paramCalendarDate.getDayOfWeek() && 
/* 379 */       getMonth() == paramCalendarDate.getMonth() && 
/* 380 */       getYear() == paramCalendarDate.getYear() && 
/* 381 */       getEra() == paramCalendarDate.getEra());
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 385 */     if (!(paramObject instanceof CalendarDate)) {
/* 386 */       return false;
/*     */     }
/* 388 */     CalendarDate calendarDate = (CalendarDate)paramObject;
/* 389 */     if (isNormalized() != calendarDate.isNormalized()) {
/* 390 */       return false;
/*     */     }
/* 392 */     boolean bool1 = (this.zoneinfo != null) ? true : false;
/* 393 */     boolean bool2 = (calendarDate.zoneinfo != null) ? true : false;
/* 394 */     if (bool1 != bool2) {
/* 395 */       return false;
/*     */     }
/* 397 */     if (bool1 && !this.zoneinfo.equals(calendarDate.zoneinfo)) {
/* 398 */       return false;
/*     */     }
/* 400 */     return (getEra() == calendarDate.getEra() && this.year == calendarDate.year && this.month == calendarDate.month && this.dayOfMonth == calendarDate.dayOfMonth && this.hours == calendarDate.hours && this.minutes == calendarDate.minutes && this.seconds == calendarDate.seconds && this.millis == calendarDate.millis && this.zoneOffset == calendarDate.zoneOffset);
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
/*     */   public int hashCode() {
/* 414 */     long l = (((this.year - 1970L) * 12L + (this.month - 1)) * 30L + this.dayOfMonth) * 24L;
/* 415 */     l = (((l + this.hours) * 60L + this.minutes) * 60L + this.seconds) * 1000L + this.millis;
/* 416 */     l -= this.zoneOffset;
/* 417 */     boolean bool1 = isNormalized() ? true : false;
/* 418 */     int i = 0;
/* 419 */     Era era = getEra();
/* 420 */     if (era != null) {
/* 421 */       i = era.hashCode();
/*     */     }
/* 423 */     boolean bool2 = (this.zoneinfo != null) ? this.zoneinfo.hashCode() : false;
/* 424 */     return (int)l * (int)(l >> 32L) ^ i ^ bool1 ^ bool2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 435 */       return super.clone();
/* 436 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 438 */       throw new InternalError(cloneNotSupportedException);
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
/*     */ 
/*     */   
/*     */   public String toString() {
/* 452 */     StringBuilder stringBuilder = new StringBuilder();
/* 453 */     CalendarUtils.sprintf0d(stringBuilder, this.year, 4).append('-');
/* 454 */     CalendarUtils.sprintf0d(stringBuilder, this.month, 2).append('-');
/* 455 */     CalendarUtils.sprintf0d(stringBuilder, this.dayOfMonth, 2).append('T');
/* 456 */     CalendarUtils.sprintf0d(stringBuilder, this.hours, 2).append(':');
/* 457 */     CalendarUtils.sprintf0d(stringBuilder, this.minutes, 2).append(':');
/* 458 */     CalendarUtils.sprintf0d(stringBuilder, this.seconds, 2).append('.');
/* 459 */     CalendarUtils.sprintf0d(stringBuilder, this.millis, 3);
/* 460 */     if (this.zoneOffset == 0) {
/* 461 */       stringBuilder.append('Z');
/* 462 */     } else if (this.zoneOffset != Integer.MIN_VALUE) {
/*     */       int i;
/*     */       byte b;
/* 465 */       if (this.zoneOffset > 0) {
/* 466 */         i = this.zoneOffset;
/* 467 */         b = 43;
/*     */       } else {
/* 469 */         i = -this.zoneOffset;
/* 470 */         b = 45;
/*     */       } 
/* 472 */       i /= 60000;
/* 473 */       stringBuilder.append(b);
/* 474 */       CalendarUtils.sprintf0d(stringBuilder, i / 60, 2);
/* 475 */       CalendarUtils.sprintf0d(stringBuilder, i % 60, 2);
/*     */     } else {
/* 477 */       stringBuilder.append(" local time");
/*     */     } 
/* 479 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   protected void setDayOfWeek(int paramInt) {
/* 483 */     this.dayOfWeek = paramInt;
/*     */   }
/*     */   
/*     */   protected void setNormalized(boolean paramBoolean) {
/* 487 */     this.normalized = paramBoolean;
/*     */   }
/*     */   
/*     */   public int getZoneOffset() {
/* 491 */     return this.zoneOffset;
/*     */   }
/*     */   
/*     */   protected void setZoneOffset(int paramInt) {
/* 495 */     this.zoneOffset = paramInt;
/*     */   }
/*     */   
/*     */   public int getDaylightSaving() {
/* 499 */     return this.daylightSaving;
/*     */   }
/*     */   
/*     */   protected void setDaylightSaving(int paramInt) {
/* 503 */     this.daylightSaving = paramInt;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/calendar/CalendarDate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */