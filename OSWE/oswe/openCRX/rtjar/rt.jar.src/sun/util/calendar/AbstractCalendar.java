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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractCalendar
/*     */   extends CalendarSystem
/*     */ {
/*     */   static final int SECOND_IN_MILLIS = 1000;
/*     */   static final int MINUTE_IN_MILLIS = 60000;
/*     */   static final int HOUR_IN_MILLIS = 3600000;
/*     */   static final int DAY_IN_MILLIS = 86400000;
/*     */   static final int EPOCH_OFFSET = 719163;
/*     */   private Era[] eras;
/*     */   
/*     */   public Era getEra(String paramString) {
/*  65 */     if (this.eras != null) {
/*  66 */       for (byte b = 0; b < this.eras.length; b++) {
/*  67 */         if (this.eras[b].equals(paramString)) {
/*  68 */           return this.eras[b];
/*     */         }
/*     */       } 
/*     */     }
/*  72 */     return null;
/*     */   }
/*     */   
/*     */   public Era[] getEras() {
/*  76 */     Era[] arrayOfEra = null;
/*  77 */     if (this.eras != null) {
/*  78 */       arrayOfEra = new Era[this.eras.length];
/*  79 */       System.arraycopy(this.eras, 0, arrayOfEra, 0, this.eras.length);
/*     */     } 
/*  81 */     return arrayOfEra;
/*     */   }
/*     */   
/*     */   public void setEra(CalendarDate paramCalendarDate, String paramString) {
/*  85 */     if (this.eras == null) {
/*     */       return;
/*     */     }
/*  88 */     for (byte b = 0; b < this.eras.length; b++) {
/*  89 */       Era era = this.eras[b];
/*  90 */       if (era != null && era.getName().equals(paramString)) {
/*  91 */         paramCalendarDate.setEra(era);
/*     */         return;
/*     */       } 
/*     */     } 
/*  95 */     throw new IllegalArgumentException("unknown era name: " + paramString);
/*     */   }
/*     */   
/*     */   protected void setEras(Era[] paramArrayOfEra) {
/*  99 */     this.eras = paramArrayOfEra;
/*     */   }
/*     */   
/*     */   public CalendarDate getCalendarDate() {
/* 103 */     return getCalendarDate(System.currentTimeMillis(), newCalendarDate());
/*     */   }
/*     */   
/*     */   public CalendarDate getCalendarDate(long paramLong) {
/* 107 */     return getCalendarDate(paramLong, newCalendarDate());
/*     */   }
/*     */   
/*     */   public CalendarDate getCalendarDate(long paramLong, TimeZone paramTimeZone) {
/* 111 */     CalendarDate calendarDate = newCalendarDate(paramTimeZone);
/* 112 */     return getCalendarDate(paramLong, calendarDate);
/*     */   }
/*     */   
/*     */   public CalendarDate getCalendarDate(long paramLong, CalendarDate paramCalendarDate) {
/* 116 */     int i = 0;
/* 117 */     int j = 0;
/* 118 */     int k = 0;
/* 119 */     long l = 0L;
/*     */ 
/*     */     
/* 122 */     TimeZone timeZone = paramCalendarDate.getZone();
/* 123 */     if (timeZone != null) {
/* 124 */       int[] arrayOfInt = new int[2];
/* 125 */       if (timeZone instanceof ZoneInfo) {
/* 126 */         j = ((ZoneInfo)timeZone).getOffsets(paramLong, arrayOfInt);
/*     */       } else {
/* 128 */         j = timeZone.getOffset(paramLong);
/* 129 */         arrayOfInt[0] = timeZone.getRawOffset();
/* 130 */         arrayOfInt[1] = j - arrayOfInt[0];
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       l = (j / 86400000);
/* 140 */       i = j % 86400000;
/* 141 */       k = arrayOfInt[1];
/*     */     } 
/* 143 */     paramCalendarDate.setZoneOffset(j);
/* 144 */     paramCalendarDate.setDaylightSaving(k);
/*     */     
/* 146 */     l += paramLong / 86400000L;
/* 147 */     i += (int)(paramLong % 86400000L);
/* 148 */     if (i >= 86400000) {
/*     */       
/* 150 */       i -= 86400000;
/* 151 */       l++;
/*     */     }
/*     */     else {
/*     */       
/* 155 */       while (i < 0) {
/* 156 */         i += 86400000;
/* 157 */         l--;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 162 */     l += 719163L;
/*     */ 
/*     */     
/* 165 */     getCalendarDateFromFixedDate(paramCalendarDate, l);
/*     */ 
/*     */     
/* 168 */     setTimeOfDay(paramCalendarDate, i);
/* 169 */     paramCalendarDate.setLeapYear(isLeapYear(paramCalendarDate));
/* 170 */     paramCalendarDate.setNormalized(true);
/* 171 */     return paramCalendarDate;
/*     */   }
/*     */   
/*     */   public long getTime(CalendarDate paramCalendarDate) {
/* 175 */     long l1 = getFixedDate(paramCalendarDate);
/* 176 */     long l2 = (l1 - 719163L) * 86400000L + getTimeOfDay(paramCalendarDate);
/* 177 */     int i = 0;
/* 178 */     TimeZone timeZone = paramCalendarDate.getZone();
/* 179 */     if (timeZone != null) {
/* 180 */       if (paramCalendarDate.isNormalized()) {
/* 181 */         return l2 - paramCalendarDate.getZoneOffset();
/*     */       }
/*     */       
/* 184 */       int[] arrayOfInt = new int[2];
/* 185 */       if (paramCalendarDate.isStandardTime()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 191 */         if (timeZone instanceof ZoneInfo) {
/* 192 */           ((ZoneInfo)timeZone).getOffsetsByStandard(l2, arrayOfInt);
/* 193 */           i = arrayOfInt[0];
/*     */         } else {
/* 195 */           i = timeZone.getOffset(l2 - timeZone.getRawOffset());
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 203 */       else if (timeZone instanceof ZoneInfo) {
/* 204 */         i = ((ZoneInfo)timeZone).getOffsetsByWall(l2, arrayOfInt);
/*     */       } else {
/* 206 */         i = timeZone.getOffset(l2 - timeZone.getRawOffset());
/*     */       } 
/*     */     } 
/*     */     
/* 210 */     l2 -= i;
/* 211 */     getCalendarDate(l2, paramCalendarDate);
/* 212 */     return l2;
/*     */   }
/*     */   
/*     */   protected long getTimeOfDay(CalendarDate paramCalendarDate) {
/* 216 */     long l = paramCalendarDate.getTimeOfDay();
/* 217 */     if (l != Long.MIN_VALUE) {
/* 218 */       return l;
/*     */     }
/* 220 */     l = getTimeOfDayValue(paramCalendarDate);
/* 221 */     paramCalendarDate.setTimeOfDay(l);
/* 222 */     return l;
/*     */   }
/*     */   
/*     */   public long getTimeOfDayValue(CalendarDate paramCalendarDate) {
/* 226 */     long l = paramCalendarDate.getHours();
/* 227 */     l *= 60L;
/* 228 */     l += paramCalendarDate.getMinutes();
/* 229 */     l *= 60L;
/* 230 */     l += paramCalendarDate.getSeconds();
/* 231 */     l *= 1000L;
/* 232 */     l += paramCalendarDate.getMillis();
/* 233 */     return l;
/*     */   }
/*     */   
/*     */   public CalendarDate setTimeOfDay(CalendarDate paramCalendarDate, int paramInt) {
/* 237 */     if (paramInt < 0) {
/* 238 */       throw new IllegalArgumentException();
/*     */     }
/* 240 */     boolean bool = paramCalendarDate.isNormalized();
/* 241 */     int i = paramInt;
/* 242 */     int j = i / 3600000;
/* 243 */     i %= 3600000;
/* 244 */     int k = i / 60000;
/* 245 */     i %= 60000;
/* 246 */     int m = i / 1000;
/* 247 */     i %= 1000;
/* 248 */     paramCalendarDate.setHours(j);
/* 249 */     paramCalendarDate.setMinutes(k);
/* 250 */     paramCalendarDate.setSeconds(m);
/* 251 */     paramCalendarDate.setMillis(i);
/* 252 */     paramCalendarDate.setTimeOfDay(paramInt);
/* 253 */     if (j < 24 && bool)
/*     */     {
/*     */       
/* 256 */       paramCalendarDate.setNormalized(bool);
/*     */     }
/* 258 */     return paramCalendarDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWeekLength() {
/* 267 */     return 7;
/*     */   }
/*     */ 
/*     */   
/*     */   public CalendarDate getNthDayOfWeek(int paramInt1, int paramInt2, CalendarDate paramCalendarDate) {
/*     */     long l2;
/* 273 */     CalendarDate calendarDate = (CalendarDate)paramCalendarDate.clone();
/* 274 */     normalize(calendarDate);
/* 275 */     long l1 = getFixedDate(calendarDate);
/*     */     
/* 277 */     if (paramInt1 > 0) {
/* 278 */       l2 = (7 * paramInt1) + getDayOfWeekDateBefore(l1, paramInt2);
/*     */     } else {
/* 280 */       l2 = (7 * paramInt1) + getDayOfWeekDateAfter(l1, paramInt2);
/*     */     } 
/* 282 */     getCalendarDateFromFixedDate(calendarDate, l2);
/* 283 */     return calendarDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isLeapYear(CalendarDate paramCalendarDate);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long getDayOfWeekDateBefore(long paramLong, int paramInt) {
/* 295 */     return getDayOfWeekDateOnOrBefore(paramLong - 1L, paramInt);
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
/*     */   static long getDayOfWeekDateAfter(long paramLong, int paramInt) {
/* 307 */     return getDayOfWeekDateOnOrBefore(paramLong + 7L, paramInt);
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
/*     */   public static long getDayOfWeekDateOnOrBefore(long paramLong, int paramInt) {
/* 320 */     long l = paramLong - (paramInt - 1);
/* 321 */     if (l >= 0L) {
/* 322 */       return paramLong - l % 7L;
/*     */     }
/* 324 */     return paramLong - CalendarUtils.mod(l, 7L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract long getFixedDate(CalendarDate paramCalendarDate);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void getCalendarDateFromFixedDate(CalendarDate paramCalendarDate, long paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateTime(CalendarDate paramCalendarDate) {
/* 353 */     int i = paramCalendarDate.getHours();
/* 354 */     if (i < 0 || i >= 24) {
/* 355 */       return false;
/*     */     }
/* 357 */     i = paramCalendarDate.getMinutes();
/* 358 */     if (i < 0 || i >= 60) {
/* 359 */       return false;
/*     */     }
/* 361 */     i = paramCalendarDate.getSeconds();
/*     */     
/* 363 */     if (i < 0 || i >= 60) {
/* 364 */       return false;
/*     */     }
/* 366 */     i = paramCalendarDate.getMillis();
/* 367 */     if (i < 0 || i >= 1000) {
/* 368 */       return false;
/*     */     }
/* 370 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   int normalizeTime(CalendarDate paramCalendarDate) {
/* 375 */     long l1 = getTimeOfDay(paramCalendarDate);
/* 376 */     long l2 = 0L;
/*     */     
/* 378 */     if (l1 >= 86400000L) {
/* 379 */       l2 = l1 / 86400000L;
/* 380 */       l1 %= 86400000L;
/* 381 */     } else if (l1 < 0L) {
/* 382 */       l2 = CalendarUtils.floorDivide(l1, 86400000L);
/* 383 */       if (l2 != 0L) {
/* 384 */         l1 -= 86400000L * l2;
/*     */       }
/*     */     } 
/* 387 */     if (l2 != 0L) {
/* 388 */       paramCalendarDate.setTimeOfDay(l1);
/*     */     }
/* 390 */     paramCalendarDate.setMillis((int)(l1 % 1000L));
/* 391 */     l1 /= 1000L;
/* 392 */     paramCalendarDate.setSeconds((int)(l1 % 60L));
/* 393 */     l1 /= 60L;
/* 394 */     paramCalendarDate.setMinutes((int)(l1 % 60L));
/* 395 */     paramCalendarDate.setHours((int)(l1 / 60L));
/* 396 */     return (int)l2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/calendar/AbstractCalendar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */