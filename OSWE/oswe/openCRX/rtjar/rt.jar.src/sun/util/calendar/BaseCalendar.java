/*     */ package sun.util.calendar;
/*     */ 
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
/*     */ public abstract class BaseCalendar
/*     */   extends AbstractCalendar
/*     */ {
/*     */   public static final int JANUARY = 1;
/*     */   public static final int FEBRUARY = 2;
/*     */   public static final int MARCH = 3;
/*     */   public static final int APRIL = 4;
/*     */   public static final int MAY = 5;
/*     */   public static final int JUNE = 6;
/*     */   public static final int JULY = 7;
/*     */   public static final int AUGUST = 8;
/*     */   public static final int SEPTEMBER = 9;
/*     */   public static final int OCTOBER = 10;
/*     */   public static final int NOVEMBER = 11;
/*     */   public static final int DECEMBER = 12;
/*     */   public static final int SUNDAY = 1;
/*     */   public static final int MONDAY = 2;
/*     */   public static final int TUESDAY = 3;
/*     */   public static final int WEDNESDAY = 4;
/*     */   public static final int THURSDAY = 5;
/*     */   public static final int FRIDAY = 6;
/*     */   public static final int SATURDAY = 7;
/*     */   private static final int BASE_YEAR = 1970;
/*  70 */   private static final int[] FIXED_DATES = new int[] { 719163, 719528, 719893, 720259, 720624, 720989, 721354, 721720, 722085, 722450, 722815, 723181, 723546, 723911, 724276, 724642, 725007, 725372, 725737, 726103, 726468, 726833, 727198, 727564, 727929, 728294, 728659, 729025, 729390, 729755, 730120, 730486, 730851, 731216, 731581, 731947, 732312, 732677, 733042, 733408, 733773, 734138, 734503, 734869, 735234, 735599, 735964, 736330, 736695, 737060, 737425, 737791, 738156, 738521, 738886, 739252, 739617, 739982, 740347, 740713, 741078, 741443, 741808, 742174, 742539, 742904, 743269, 743635, 744000, 744365 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class Date
/*     */     extends CalendarDate
/*     */   {
/*     */     protected Date() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Date(TimeZone param1TimeZone) {
/* 148 */       super(param1TimeZone);
/*     */     }
/*     */     
/*     */     public Date setNormalizedDate(int param1Int1, int param1Int2, int param1Int3) {
/* 152 */       setNormalizedYear(param1Int1);
/* 153 */       setMonth(param1Int2).setDayOfMonth(param1Int3);
/* 154 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     int cachedYear = 2004;
/* 165 */     long cachedFixedDateJan1 = 731581L;
/* 166 */     long cachedFixedDateNextJan1 = this.cachedFixedDateJan1 + 366L;
/*     */     public abstract int getNormalizedYear();
/*     */     protected final boolean hit(int param1Int) {
/* 169 */       return (param1Int == this.cachedYear);
/*     */     }
/*     */     public abstract void setNormalizedYear(int param1Int);
/*     */     protected final boolean hit(long param1Long) {
/* 173 */       return (param1Long >= this.cachedFixedDateJan1 && param1Long < this.cachedFixedDateNextJan1);
/*     */     }
/*     */     
/*     */     protected int getCachedYear() {
/* 177 */       return this.cachedYear;
/*     */     }
/*     */     
/*     */     protected long getCachedJan1() {
/* 181 */       return this.cachedFixedDateJan1;
/*     */     }
/*     */     
/*     */     protected void setCache(int param1Int1, long param1Long, int param1Int2) {
/* 185 */       this.cachedYear = param1Int1;
/* 186 */       this.cachedFixedDateJan1 = param1Long;
/* 187 */       this.cachedFixedDateNextJan1 = param1Long + param1Int2;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean validate(CalendarDate paramCalendarDate) {
/* 192 */     Date date = (Date)paramCalendarDate;
/* 193 */     if (date.isNormalized()) {
/* 194 */       return true;
/*     */     }
/* 196 */     int i = date.getMonth();
/* 197 */     if (i < 1 || i > 12) {
/* 198 */       return false;
/*     */     }
/* 200 */     int j = date.getDayOfMonth();
/* 201 */     if (j <= 0 || j > getMonthLength(date.getNormalizedYear(), i)) {
/* 202 */       return false;
/*     */     }
/* 204 */     int k = date.getDayOfWeek();
/* 205 */     if (k != Integer.MIN_VALUE && k != getDayOfWeek(date)) {
/* 206 */       return false;
/*     */     }
/*     */     
/* 209 */     if (!validateTime(paramCalendarDate)) {
/* 210 */       return false;
/*     */     }
/*     */     
/* 213 */     date.setNormalized(true);
/* 214 */     return true;
/*     */   }
/*     */   
/*     */   public boolean normalize(CalendarDate paramCalendarDate) {
/* 218 */     if (paramCalendarDate.isNormalized()) {
/* 219 */       return true;
/*     */     }
/*     */     
/* 222 */     Date date = (Date)paramCalendarDate;
/* 223 */     TimeZone timeZone = date.getZone();
/*     */ 
/*     */ 
/*     */     
/* 227 */     if (timeZone != null) {
/* 228 */       getTime(paramCalendarDate);
/* 229 */       return true;
/*     */     } 
/*     */     
/* 232 */     int i = normalizeTime(date);
/* 233 */     normalizeMonth(date);
/* 234 */     long l = date.getDayOfMonth() + i;
/* 235 */     int j = date.getMonth();
/* 236 */     int k = date.getNormalizedYear();
/* 237 */     int m = getMonthLength(k, j);
/*     */     
/* 239 */     if (l <= 0L || l > m) {
/* 240 */       if (l <= 0L && l > -28L) {
/* 241 */         m = getMonthLength(k, --j);
/* 242 */         l += m;
/* 243 */         date.setDayOfMonth((int)l);
/* 244 */         if (j == 0) {
/* 245 */           j = 12;
/* 246 */           date.setNormalizedYear(k - 1);
/*     */         } 
/* 248 */         date.setMonth(j);
/* 249 */       } else if (l > m && l < (m + 28)) {
/* 250 */         l -= m;
/* 251 */         j++;
/* 252 */         date.setDayOfMonth((int)l);
/* 253 */         if (j > 12) {
/* 254 */           date.setNormalizedYear(k + 1);
/* 255 */           j = 1;
/*     */         } 
/* 257 */         date.setMonth(j);
/*     */       } else {
/* 259 */         long l1 = l + getFixedDate(k, j, 1, date) - 1L;
/* 260 */         getCalendarDateFromFixedDate(date, l1);
/*     */       } 
/*     */     } else {
/* 263 */       date.setDayOfWeek(getDayOfWeek(date));
/*     */     } 
/* 265 */     paramCalendarDate.setLeapYear(isLeapYear(date.getNormalizedYear()));
/* 266 */     paramCalendarDate.setZoneOffset(0);
/* 267 */     paramCalendarDate.setDaylightSaving(0);
/* 268 */     date.setNormalized(true);
/* 269 */     return true;
/*     */   }
/*     */   
/*     */   void normalizeMonth(CalendarDate paramCalendarDate) {
/* 273 */     Date date = (Date)paramCalendarDate;
/* 274 */     int i = date.getNormalizedYear();
/* 275 */     long l = date.getMonth();
/* 276 */     if (l <= 0L) {
/* 277 */       long l1 = 1L - l;
/* 278 */       i -= (int)(l1 / 12L + 1L);
/* 279 */       l = 13L - l1 % 12L;
/* 280 */       date.setNormalizedYear(i);
/* 281 */       date.setMonth((int)l);
/* 282 */     } else if (l > 12L) {
/* 283 */       i += (int)((l - 1L) / 12L);
/* 284 */       l = (l - 1L) % 12L + 1L;
/* 285 */       date.setNormalizedYear(i);
/* 286 */       date.setMonth((int)l);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYearLength(CalendarDate paramCalendarDate) {
/* 303 */     return isLeapYear(((Date)paramCalendarDate).getNormalizedYear()) ? 366 : 365;
/*     */   }
/*     */   
/*     */   public int getYearLengthInMonths(CalendarDate paramCalendarDate) {
/* 307 */     return 12;
/*     */   }
/*     */   
/* 310 */   static final int[] DAYS_IN_MONTH = new int[] { 31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
/*     */ 
/*     */   
/* 313 */   static final int[] ACCUMULATED_DAYS_IN_MONTH = new int[] { -30, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334 };
/*     */ 
/*     */ 
/*     */   
/* 317 */   static final int[] ACCUMULATED_DAYS_IN_MONTH_LEAP = new int[] { -30, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335 };
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMonthLength(CalendarDate paramCalendarDate) {
/* 322 */     Date date = (Date)paramCalendarDate;
/* 323 */     int i = date.getMonth();
/* 324 */     if (i < 1 || i > 12) {
/* 325 */       throw new IllegalArgumentException("Illegal month value: " + i);
/*     */     }
/* 327 */     return getMonthLength(date.getNormalizedYear(), i);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getMonthLength(int paramInt1, int paramInt2) {
/* 332 */     int i = DAYS_IN_MONTH[paramInt2];
/* 333 */     if (paramInt2 == 2 && isLeapYear(paramInt1)) {
/* 334 */       i++;
/*     */     }
/* 336 */     return i;
/*     */   }
/*     */   
/*     */   public long getDayOfYear(CalendarDate paramCalendarDate) {
/* 340 */     return getDayOfYear(((Date)paramCalendarDate).getNormalizedYear(), paramCalendarDate
/* 341 */         .getMonth(), paramCalendarDate
/* 342 */         .getDayOfMonth());
/*     */   }
/*     */   
/*     */   final long getDayOfYear(int paramInt1, int paramInt2, int paramInt3) {
/* 346 */     return paramInt3 + (
/* 347 */       isLeapYear(paramInt1) ? ACCUMULATED_DAYS_IN_MONTH_LEAP[paramInt2] : ACCUMULATED_DAYS_IN_MONTH[paramInt2]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getFixedDate(CalendarDate paramCalendarDate) {
/* 353 */     if (!paramCalendarDate.isNormalized()) {
/* 354 */       normalizeMonth(paramCalendarDate);
/*     */     }
/* 356 */     return getFixedDate(((Date)paramCalendarDate).getNormalizedYear(), paramCalendarDate
/* 357 */         .getMonth(), paramCalendarDate
/* 358 */         .getDayOfMonth(), (Date)paramCalendarDate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getFixedDate(int paramInt1, int paramInt2, int paramInt3, Date paramDate) {
/* 364 */     boolean bool = (paramInt2 == 1 && paramInt3 == 1) ? true : false;
/*     */ 
/*     */     
/* 367 */     if (paramDate != null && paramDate.hit(paramInt1)) {
/* 368 */       if (bool) {
/* 369 */         return paramDate.getCachedJan1();
/*     */       }
/* 371 */       return paramDate.getCachedJan1() + getDayOfYear(paramInt1, paramInt2, paramInt3) - 1L;
/*     */     } 
/*     */ 
/*     */     
/* 375 */     int i = paramInt1 - 1970;
/* 376 */     if (i >= 0 && i < FIXED_DATES.length) {
/* 377 */       long l = FIXED_DATES[i];
/* 378 */       if (paramDate != null) {
/* 379 */         paramDate.setCache(paramInt1, l, isLeapYear(paramInt1) ? 366 : 365);
/*     */       }
/* 381 */       return bool ? l : (l + getDayOfYear(paramInt1, paramInt2, paramInt3) - 1L);
/*     */     } 
/*     */     
/* 384 */     long l1 = paramInt1 - 1L;
/* 385 */     long l2 = paramInt3;
/*     */     
/* 387 */     if (l1 >= 0L) {
/* 388 */       l2 += 365L * l1 + l1 / 4L - l1 / 100L + l1 / 400L + ((367 * paramInt2 - 362) / 12);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 394 */       l2 += 365L * l1 + 
/* 395 */         CalendarUtils.floorDivide(l1, 4L) - 
/* 396 */         CalendarUtils.floorDivide(l1, 100L) + 
/* 397 */         CalendarUtils.floorDivide(l1, 400L) + 
/* 398 */         CalendarUtils.floorDivide(367 * paramInt2 - 362, 12);
/*     */     } 
/*     */     
/* 401 */     if (paramInt2 > 2) {
/* 402 */       l2 -= isLeapYear(paramInt1) ? 1L : 2L;
/*     */     }
/*     */ 
/*     */     
/* 406 */     if (paramDate != null && bool) {
/* 407 */       paramDate.setCache(paramInt1, l2, isLeapYear(paramInt1) ? 366 : 365);
/*     */     }
/*     */     
/* 410 */     return l2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getCalendarDateFromFixedDate(CalendarDate paramCalendarDate, long paramLong) {
/*     */     int i;
/*     */     long l1;
/*     */     boolean bool;
/* 420 */     Date date = (Date)paramCalendarDate;
/*     */ 
/*     */ 
/*     */     
/* 424 */     if (date.hit(paramLong)) {
/* 425 */       i = date.getCachedYear();
/* 426 */       l1 = date.getCachedJan1();
/* 427 */       bool = isLeapYear(i);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 432 */       i = getGregorianYearFromFixedDate(paramLong);
/* 433 */       l1 = getFixedDate(i, 1, 1, (Date)null);
/* 434 */       bool = isLeapYear(i);
/*     */       
/* 436 */       date.setCache(i, l1, bool ? 366 : 365);
/*     */     } 
/*     */     
/* 439 */     int j = (int)(paramLong - l1);
/* 440 */     long l2 = l1 + 31L + 28L;
/* 441 */     if (bool) {
/* 442 */       l2++;
/*     */     }
/* 444 */     if (paramLong >= l2) {
/* 445 */       j += bool ? 1 : 2;
/*     */     }
/* 447 */     int k = 12 * j + 373;
/* 448 */     if (k > 0) {
/* 449 */       k /= 367;
/*     */     } else {
/* 451 */       k = CalendarUtils.floorDivide(k, 367);
/*     */     } 
/* 453 */     long l3 = l1 + ACCUMULATED_DAYS_IN_MONTH[k];
/* 454 */     if (bool && k >= 3) {
/* 455 */       l3++;
/*     */     }
/* 457 */     int m = (int)(paramLong - l3) + 1;
/* 458 */     int n = getDayOfWeekFromFixedDate(paramLong);
/* 459 */     assert n > 0 : "negative day of week " + n;
/* 460 */     date.setNormalizedYear(i);
/* 461 */     date.setMonth(k);
/* 462 */     date.setDayOfMonth(m);
/* 463 */     date.setDayOfWeek(n);
/* 464 */     date.setLeapYear(bool);
/* 465 */     date.setNormalized(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDayOfWeek(CalendarDate paramCalendarDate) {
/* 472 */     long l = getFixedDate(paramCalendarDate);
/* 473 */     return getDayOfWeekFromFixedDate(l);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int getDayOfWeekFromFixedDate(long paramLong) {
/* 478 */     if (paramLong >= 0L) {
/* 479 */       return (int)(paramLong % 7L) + 1;
/*     */     }
/* 481 */     return (int)CalendarUtils.mod(paramLong, 7L) + 1;
/*     */   }
/*     */   
/*     */   public int getYearFromFixedDate(long paramLong) {
/* 485 */     return getGregorianYearFromFixedDate(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int getGregorianYearFromFixedDate(long paramLong) {
/*     */     int i, j, k, m;
/* 497 */     if (paramLong > 0L) {
/* 498 */       long l = paramLong - 1L;
/* 499 */       i = (int)(l / 146097L);
/* 500 */       int i1 = (int)(l % 146097L);
/* 501 */       j = i1 / 36524;
/* 502 */       int i2 = i1 % 36524;
/* 503 */       k = i2 / 1461;
/* 504 */       int i3 = i2 % 1461;
/* 505 */       m = i3 / 365;
/* 506 */       int i4 = i3 % 365 + 1;
/*     */     } else {
/* 508 */       long l = paramLong - 1L;
/* 509 */       i = (int)CalendarUtils.floorDivide(l, 146097L);
/* 510 */       int i1 = (int)CalendarUtils.mod(l, 146097L);
/* 511 */       j = CalendarUtils.floorDivide(i1, 36524);
/* 512 */       int i2 = CalendarUtils.mod(i1, 36524);
/* 513 */       k = CalendarUtils.floorDivide(i2, 1461);
/* 514 */       int i3 = CalendarUtils.mod(i2, 1461);
/* 515 */       m = CalendarUtils.floorDivide(i3, 365);
/* 516 */       int i4 = CalendarUtils.mod(i3, 365) + 1;
/*     */     } 
/* 518 */     int n = 400 * i + 100 * j + 4 * k + m;
/* 519 */     if (j != 4 && m != 4) {
/* 520 */       n++;
/*     */     }
/* 522 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isLeapYear(CalendarDate paramCalendarDate) {
/* 531 */     return isLeapYear(((Date)paramCalendarDate).getNormalizedYear());
/*     */   }
/*     */   
/*     */   boolean isLeapYear(int paramInt) {
/* 535 */     return CalendarUtils.isGregorianLeapYear(paramInt);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/calendar/BaseCalendar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */