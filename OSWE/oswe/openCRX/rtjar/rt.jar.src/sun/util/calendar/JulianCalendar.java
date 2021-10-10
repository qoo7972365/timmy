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
/*     */ public class JulianCalendar
/*     */   extends BaseCalendar
/*     */ {
/*     */   private static final int BCE = 0;
/*     */   private static final int CE = 1;
/*  41 */   private static final Era[] eras = new Era[] { new Era("BeforeCommonEra", "B.C.E.", Long.MIN_VALUE, false), new Era("CommonEra", "C.E.", -62135709175808L, true) };
/*     */   
/*     */   private static final int JULIAN_EPOCH = -1;
/*     */ 
/*     */   
/*     */   private static class Date
/*     */     extends BaseCalendar.Date
/*     */   {
/*     */     protected Date() {
/*  50 */       setCache(1, -1L, 365);
/*     */     }
/*     */     
/*     */     protected Date(TimeZone param1TimeZone) {
/*  54 */       super(param1TimeZone);
/*  55 */       setCache(1, -1L, 365);
/*     */     }
/*     */     
/*     */     public Date setEra(Era param1Era) {
/*  59 */       if (param1Era == null) {
/*  60 */         throw new NullPointerException();
/*     */       }
/*  62 */       if (param1Era != JulianCalendar.eras[0] || param1Era != JulianCalendar.eras[1]) {
/*  63 */         throw new IllegalArgumentException("unknown era: " + param1Era);
/*     */       }
/*  65 */       super.setEra(param1Era);
/*  66 */       return this;
/*     */     }
/*     */     
/*     */     protected void setKnownEra(Era param1Era) {
/*  70 */       super.setEra(param1Era);
/*     */     }
/*     */     
/*     */     public int getNormalizedYear() {
/*  74 */       if (getEra() == JulianCalendar.eras[0]) {
/*  75 */         return 1 - getYear();
/*     */       }
/*  77 */       return getYear();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setNormalizedYear(int param1Int) {
/*  85 */       if (param1Int <= 0) {
/*  86 */         setYear(1 - param1Int);
/*  87 */         setKnownEra(JulianCalendar.eras[0]);
/*     */       } else {
/*  89 */         setYear(param1Int);
/*  90 */         setKnownEra(JulianCalendar.eras[1]);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/*  95 */       String str = super.toString();
/*  96 */       str = str.substring(str.indexOf('T'));
/*  97 */       StringBuffer stringBuffer = new StringBuffer();
/*  98 */       Era era = getEra();
/*  99 */       if (era != null) {
/* 100 */         String str1 = era.getAbbreviation();
/* 101 */         if (str1 != null) {
/* 102 */           stringBuffer.append(str1).append(' ');
/*     */         }
/*     */       } 
/* 105 */       stringBuffer.append(getYear()).append('-');
/* 106 */       CalendarUtils.sprintf0d(stringBuffer, getMonth(), 2).append('-');
/* 107 */       CalendarUtils.sprintf0d(stringBuffer, getDayOfMonth(), 2);
/* 108 */       stringBuffer.append(str);
/* 109 */       return stringBuffer.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   JulianCalendar() {
/* 114 */     setEras(eras);
/*     */   }
/*     */   
/*     */   public String getName() {
/* 118 */     return "julian";
/*     */   }
/*     */   
/*     */   public Date getCalendarDate() {
/* 122 */     return getCalendarDate(System.currentTimeMillis(), newCalendarDate());
/*     */   }
/*     */   
/*     */   public Date getCalendarDate(long paramLong) {
/* 126 */     return getCalendarDate(paramLong, newCalendarDate());
/*     */   }
/*     */   
/*     */   public Date getCalendarDate(long paramLong, CalendarDate paramCalendarDate) {
/* 130 */     return (Date)super.getCalendarDate(paramLong, paramCalendarDate);
/*     */   }
/*     */   
/*     */   public Date getCalendarDate(long paramLong, TimeZone paramTimeZone) {
/* 134 */     return getCalendarDate(paramLong, newCalendarDate(paramTimeZone));
/*     */   }
/*     */   
/*     */   public Date newCalendarDate() {
/* 138 */     return new Date();
/*     */   }
/*     */   
/*     */   public Date newCalendarDate(TimeZone paramTimeZone) {
/* 142 */     return new Date(paramTimeZone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getFixedDate(int paramInt1, int paramInt2, int paramInt3, BaseCalendar.Date paramDate) {
/* 149 */     boolean bool = (paramInt2 == 1 && paramInt3 == 1) ? true : false;
/*     */ 
/*     */     
/* 152 */     if (paramDate != null && paramDate.hit(paramInt1)) {
/* 153 */       if (bool) {
/* 154 */         return paramDate.getCachedJan1();
/*     */       }
/* 156 */       return paramDate.getCachedJan1() + getDayOfYear(paramInt1, paramInt2, paramInt3) - 1L;
/*     */     } 
/*     */     
/* 159 */     long l1 = paramInt1;
/* 160 */     long l2 = -2L + 365L * (l1 - 1L) + paramInt3;
/* 161 */     if (l1 > 0L) {
/*     */       
/* 163 */       l2 += (l1 - 1L) / 4L;
/*     */     } else {
/*     */       
/* 166 */       l2 += CalendarUtils.floorDivide(l1 - 1L, 4L);
/*     */     } 
/* 168 */     if (paramInt2 > 0) {
/* 169 */       l2 += (367L * paramInt2 - 362L) / 12L;
/*     */     } else {
/* 171 */       l2 += CalendarUtils.floorDivide(367L * paramInt2 - 362L, 12L);
/*     */     } 
/* 173 */     if (paramInt2 > 2) {
/* 174 */       l2 -= CalendarUtils.isJulianLeapYear(paramInt1) ? 1L : 2L;
/*     */     }
/*     */ 
/*     */     
/* 178 */     if (paramDate != null && bool) {
/* 179 */       paramDate.setCache(paramInt1, l2, CalendarUtils.isJulianLeapYear(paramInt1) ? 366 : 365);
/*     */     }
/*     */     
/* 182 */     return l2;
/*     */   }
/*     */   public void getCalendarDateFromFixedDate(CalendarDate paramCalendarDate, long paramLong) {
/*     */     int i;
/* 186 */     Date date = (Date)paramCalendarDate;
/* 187 */     long l = 4L * (paramLong - -1L) + 1464L;
/*     */     
/* 189 */     if (l >= 0L) {
/* 190 */       i = (int)(l / 1461L);
/*     */     } else {
/* 192 */       i = (int)CalendarUtils.floorDivide(l, 1461L);
/*     */     } 
/* 194 */     int j = (int)(paramLong - getFixedDate(i, 1, 1, date));
/* 195 */     boolean bool = CalendarUtils.isJulianLeapYear(i);
/* 196 */     if (paramLong >= getFixedDate(i, 3, 1, date)) {
/* 197 */       j += bool ? 1 : 2;
/*     */     }
/* 199 */     int k = 12 * j + 373;
/* 200 */     if (k > 0) {
/* 201 */       k /= 367;
/*     */     } else {
/* 203 */       k = CalendarUtils.floorDivide(k, 367);
/*     */     } 
/* 205 */     int m = (int)(paramLong - getFixedDate(i, k, 1, date)) + 1;
/* 206 */     int n = getDayOfWeekFromFixedDate(paramLong);
/* 207 */     assert n > 0 : "negative day of week " + n;
/* 208 */     date.setNormalizedYear(i);
/* 209 */     date.setMonth(k);
/* 210 */     date.setDayOfMonth(m);
/* 211 */     date.setDayOfWeek(n);
/* 212 */     date.setLeapYear(bool);
/* 213 */     date.setNormalized(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYearFromFixedDate(long paramLong) {
/* 220 */     return (int)CalendarUtils.floorDivide(4L * (paramLong - -1L) + 1464L, 1461L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDayOfWeek(CalendarDate paramCalendarDate) {
/* 227 */     long l = getFixedDate(paramCalendarDate);
/* 228 */     return getDayOfWeekFromFixedDate(l);
/*     */   }
/*     */   
/*     */   boolean isLeapYear(int paramInt) {
/* 232 */     return CalendarUtils.isJulianLeapYear(paramInt);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/calendar/JulianCalendar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */