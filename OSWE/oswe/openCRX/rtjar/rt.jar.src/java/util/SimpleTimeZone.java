/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import sun.util.calendar.BaseCalendar;
/*      */ import sun.util.calendar.CalendarSystem;
/*      */ import sun.util.calendar.CalendarUtils;
/*      */ import sun.util.calendar.Gregorian;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SimpleTimeZone
/*      */   extends TimeZone
/*      */ {
/*      */   private int startMonth;
/*      */   private int startDay;
/*      */   private int startDayOfWeek;
/*      */   private int startTime;
/*      */   private int startTimeMode;
/*      */   private int endMonth;
/*      */   private int endDay;
/*      */   private int endDayOfWeek;
/*      */   private int endTime;
/*      */   private int endTimeMode;
/*      */   private int startYear;
/*      */   private int rawOffset;
/*      */   private boolean useDaylight;
/*      */   private static final int millisPerHour = 3600000;
/*      */   private static final int millisPerDay = 86400000;
/*      */   private final byte[] monthLength;
/*      */   
/*      */   public SimpleTimeZone(int paramInt, String paramString) {
/* 1115 */     this.useDaylight = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1128 */     this.monthLength = staticMonthLength;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1280 */     this.serialVersionOnStream = 2; this.rawOffset = paramInt; setID(paramString); this.dstSavings = 3600000; } public SimpleTimeZone(int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9) { this(paramInt1, paramString, paramInt2, paramInt3, paramInt4, paramInt5, 0, paramInt6, paramInt7, paramInt8, paramInt9, 0, 3600000); } public SimpleTimeZone(int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10) { this(paramInt1, paramString, paramInt2, paramInt3, paramInt4, paramInt5, 0, paramInt6, paramInt7, paramInt8, paramInt9, 0, paramInt10); } public void setStartYear(int paramInt) { this.startYear = paramInt; invalidateCache(); } public void setStartRule(int paramInt1, int paramInt2, int paramInt3, int paramInt4) { this.startMonth = paramInt1; this.startDay = paramInt2; this.startDayOfWeek = paramInt3; this.startTime = paramInt4; this.startTimeMode = 0; decodeStartRule(); invalidateCache(); } public void setStartRule(int paramInt1, int paramInt2, int paramInt3) { setStartRule(paramInt1, paramInt2, 0, paramInt3); } public void setStartRule(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) { if (paramBoolean) { setStartRule(paramInt1, paramInt2, -paramInt3, paramInt4); } else { setStartRule(paramInt1, -paramInt2, -paramInt3, paramInt4); }  } public void setEndRule(int paramInt1, int paramInt2, int paramInt3, int paramInt4) { this.endMonth = paramInt1; this.endDay = paramInt2; this.endDayOfWeek = paramInt3; this.endTime = paramInt4; this.endTimeMode = 0; decodeEndRule(); invalidateCache(); } public void setEndRule(int paramInt1, int paramInt2, int paramInt3) { setEndRule(paramInt1, paramInt2, 0, paramInt3); } public SimpleTimeZone(int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12) { this.useDaylight = false; this.monthLength = staticMonthLength; this.serialVersionOnStream = 2; setID(paramString); this.rawOffset = paramInt1; this.startMonth = paramInt2; this.startDay = paramInt3; this.startDayOfWeek = paramInt4; this.startTime = paramInt5; this.startTimeMode = paramInt6; this.endMonth = paramInt7; this.endDay = paramInt8; this.endDayOfWeek = paramInt9; this.endTime = paramInt10; this.endTimeMode = paramInt11; this.dstSavings = paramInt12; decodeRules(); if (paramInt12 <= 0) throw new IllegalArgumentException("Illegal daylight saving value: " + paramInt12);  }
/*      */   public void setEndRule(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) { if (paramBoolean) { setEndRule(paramInt1, paramInt2, -paramInt3, paramInt4); } else { setEndRule(paramInt1, -paramInt2, -paramInt3, paramInt4); }  }
/*      */   public int getOffset(long paramLong) { return getOffsets(paramLong, (int[])null); }
/*      */   int getOffsets(long paramLong, int[] paramArrayOfint) { int i = this.rawOffset; if (this.useDaylight) { SimpleTimeZone simpleTimeZone; BaseCalendar baseCalendar; synchronized (this) { if (this.cacheStart != 0L && paramLong >= this.cacheStart && paramLong < this.cacheEnd) { i += this.dstSavings; } else { baseCalendar = (paramLong >= -12219292800000L) ? gcal : (BaseCalendar)CalendarSystem.forName("julian"); BaseCalendar.Date date = (BaseCalendar.Date)baseCalendar.newCalendarDate(TimeZone.NO_TIMEZONE); baseCalendar.getCalendarDate(paramLong + this.rawOffset, date); int j = date.getNormalizedYear(); if (j >= this.startYear) { date.setTimeOfDay(0, 0, 0, 0); i = getOffset(baseCalendar, date, j, paramLong); }  }  }  }  if (paramArrayOfint != null) { paramArrayOfint[0] = this.rawOffset; paramArrayOfint[1] = i - this.rawOffset; }  return i; }
/*      */   public int getOffset(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) { if (paramInt1 != 1 && paramInt1 != 0) throw new IllegalArgumentException("Illegal era " + paramInt1);  int i = paramInt2; if (paramInt1 == 0) i = 1 - i;  if (i >= 292278994) { i = 2800 + i % 2800; } else if (i <= -292269054) { i = (int)CalendarUtils.mod(i, 28L); }  int j = paramInt3 + 1; BaseCalendar baseCalendar = gcal; BaseCalendar.Date date = (BaseCalendar.Date)baseCalendar.newCalendarDate(TimeZone.NO_TIMEZONE); date.setDate(i, j, paramInt4); long l = baseCalendar.getTime(date); l += (paramInt6 - this.rawOffset); if (l < -12219292800000L) { baseCalendar = (BaseCalendar)CalendarSystem.forName("julian"); date = (BaseCalendar.Date)baseCalendar.newCalendarDate(TimeZone.NO_TIMEZONE); date.setNormalizedDate(i, j, paramInt4); l = baseCalendar.getTime(date) + paramInt6 - this.rawOffset; }  if (date.getNormalizedYear() != i || date.getMonth() != j || date.getDayOfMonth() != paramInt4 || paramInt5 < 1 || paramInt5 > 7 || paramInt6 < 0 || paramInt6 >= 86400000) throw new IllegalArgumentException();  if (!this.useDaylight || paramInt2 < this.startYear || paramInt1 != 1) return this.rawOffset;  return getOffset(baseCalendar, date, i, l); }
/*      */   private int getOffset(BaseCalendar paramBaseCalendar, BaseCalendar.Date paramDate, int paramInt, long paramLong) { synchronized (this) { if (this.cacheStart != 0L) { if (paramLong >= this.cacheStart && paramLong < this.cacheEnd) return this.rawOffset + this.dstSavings;  if (paramInt == this.cacheYear) return this.rawOffset;  }  }  long l1 = getStart(paramBaseCalendar, paramDate, paramInt); long l2 = getEnd(paramBaseCalendar, paramDate, paramInt); int i = this.rawOffset; if (l1 <= l2) { if (paramLong >= l1 && paramLong < l2) i += this.dstSavings;  synchronized (this) { this.cacheYear = paramInt; this.cacheStart = l1; this.cacheEnd = l2; }  } else { if (paramLong < l2) { l1 = getStart(paramBaseCalendar, paramDate, paramInt - 1); if (paramLong >= l1) i += this.dstSavings;  } else if (paramLong >= l1) { l2 = getEnd(paramBaseCalendar, paramDate, paramInt + 1); if (paramLong < l2) i += this.dstSavings;  }  if (l1 <= l2)
/* 1286 */         synchronized (this) { this.cacheYear = this.startYear - 1L; this.cacheStart = l1; this.cacheEnd = l2; }   }  return i; } private synchronized void invalidateCache() { this.cacheYear = (this.startYear - 1);
/* 1287 */     this.cacheStart = this.cacheEnd = 0L; } private long getStart(BaseCalendar paramBaseCalendar, BaseCalendar.Date paramDate, int paramInt) { int i = this.startTime;
/*      */     if (this.startTimeMode != 2)
/*      */       i -= this.rawOffset; 
/*      */     return getTransition(paramBaseCalendar, paramDate, this.startMode, paramInt, this.startMonth, this.startDay, this.startDayOfWeek, i); } private long getEnd(BaseCalendar paramBaseCalendar, BaseCalendar.Date paramDate, int paramInt) { int i = this.endTime;
/*      */     if (this.endTimeMode != 2)
/*      */       i -= this.rawOffset; 
/*      */     if (this.endTimeMode == 0)
/*      */       i -= this.dstSavings; 
/*      */     return getTransition(paramBaseCalendar, paramDate, this.endMode, paramInt, this.endMonth, this.endDay, this.endDayOfWeek, i); } private long getTransition(BaseCalendar paramBaseCalendar, BaseCalendar.Date paramDate, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) { paramDate.setNormalizedYear(paramInt2);
/*      */     paramDate.setMonth(paramInt3 + 1);
/*      */     switch (paramInt1) {
/*      */       case 1:
/*      */         paramDate.setDayOfMonth(paramInt4);
/*      */         break;
/*      */       case 2:
/*      */         paramDate.setDayOfMonth(1);
/*      */         if (paramInt4 < 0)
/*      */           paramDate.setDayOfMonth(paramBaseCalendar.getMonthLength(paramDate)); 
/*      */         paramDate = (BaseCalendar.Date)paramBaseCalendar.getNthDayOfWeek(paramInt4, paramInt5, paramDate);
/*      */         break;
/*      */       case 3:
/*      */         paramDate.setDayOfMonth(paramInt4);
/*      */         paramDate = (BaseCalendar.Date)paramBaseCalendar.getNthDayOfWeek(1, paramInt5, paramDate);
/*      */         break;
/*      */       case 4:
/*      */         paramDate.setDayOfMonth(paramInt4);
/*      */         paramDate = (BaseCalendar.Date)paramBaseCalendar.getNthDayOfWeek(-1, paramInt5, paramDate);
/*      */         break;
/*      */     } 
/*      */     return paramBaseCalendar.getTime(paramDate) + paramInt6; } public int getRawOffset() { return this.rawOffset; } public void setRawOffset(int paramInt) { this.rawOffset = paramInt; } public void setDSTSavings(int paramInt) { if (paramInt <= 0)
/*      */       throw new IllegalArgumentException("Illegal daylight saving value: " + paramInt); 
/*      */     this.dstSavings = paramInt; } public int getDSTSavings() { return this.useDaylight ? this.dstSavings : 0; } public boolean useDaylightTime() { return this.useDaylight; } public boolean observesDaylightTime() {
/*      */     return useDaylightTime();
/*      */   }
/*      */   public boolean inDaylightTime(Date paramDate) {
/*      */     return (getOffset(paramDate.getTime()) != this.rawOffset);
/*      */   }
/*      */   public Object clone() {
/*      */     return super.clone();
/*      */   }
/*      */   public synchronized int hashCode() {
/*      */     return this.startMonth ^ this.startDay ^ this.startDayOfWeek ^ this.startTime ^ this.endMonth ^ this.endDay ^ this.endDayOfWeek ^ this.endTime ^ this.rawOffset;
/*      */   }
/*      */   public boolean equals(Object paramObject) {
/*      */     if (this == paramObject)
/*      */       return true; 
/*      */     if (!(paramObject instanceof SimpleTimeZone))
/*      */       return false; 
/*      */     SimpleTimeZone simpleTimeZone = (SimpleTimeZone)paramObject;
/*      */     return (getID().equals(simpleTimeZone.getID()) && hasSameRules(simpleTimeZone));
/*      */   }
/*      */   public boolean hasSameRules(TimeZone paramTimeZone) {
/*      */     if (this == paramTimeZone)
/*      */       return true; 
/*      */     if (!(paramTimeZone instanceof SimpleTimeZone))
/*      */       return false; 
/*      */     SimpleTimeZone simpleTimeZone = (SimpleTimeZone)paramTimeZone;
/*      */     return (this.rawOffset == simpleTimeZone.rawOffset && this.useDaylight == simpleTimeZone.useDaylight && (!this.useDaylight || (this.dstSavings == simpleTimeZone.dstSavings && this.startMode == simpleTimeZone.startMode && this.startMonth == simpleTimeZone.startMonth && this.startDay == simpleTimeZone.startDay && this.startDayOfWeek == simpleTimeZone.startDayOfWeek && this.startTime == simpleTimeZone.startTime && this.startTimeMode == simpleTimeZone.startTimeMode && this.endMode == simpleTimeZone.endMode && this.endMonth == simpleTimeZone.endMonth && this.endDay == simpleTimeZone.endDay && this.endDayOfWeek == simpleTimeZone.endDayOfWeek && this.endTime == simpleTimeZone.endTime && this.endTimeMode == simpleTimeZone.endTimeMode && this.startYear == simpleTimeZone.startYear)));
/*      */   }
/*      */   public String toString() {
/*      */     return getClass().getName() + "[id=" + getID() + ",offset=" + this.rawOffset + ",dstSavings=" + this.dstSavings + ",useDaylight=" + this.useDaylight + ",startYear=" + this.startYear + ",startMode=" + this.startMode + ",startMonth=" + this.startMonth + ",startDay=" + this.startDay + ",startDayOfWeek=" + this.startDayOfWeek + ",startTime=" + this.startTime + ",startTimeMode=" + this.startTimeMode + ",endMode=" + this.endMode + ",endMonth=" + this.endMonth + ",endDay=" + this.endDay + ",endDayOfWeek=" + this.endDayOfWeek + ",endTime=" + this.endTime + ",endTimeMode=" + this.endTimeMode + ']';
/*      */   }
/*      */   private static final byte[] staticMonthLength = new byte[] { 
/*      */       31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 
/*      */       30, 31 }; private static final byte[] staticLeapMonthLength = new byte[] { 
/*      */       31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 
/*      */       30, 31 }; private int startMode; private int endMode; private int dstSavings; private static final Gregorian gcal = CalendarSystem.getGregorianCalendar(); private transient long cacheYear; private transient long cacheStart; private transient long cacheEnd; private static final int DOM_MODE = 1; private static final int DOW_IN_MONTH_MODE = 2; private static final int DOW_GE_DOM_MODE = 3; private static final int DOW_LE_DOM_MODE = 4; public static final int WALL_TIME = 0; public static final int STANDARD_TIME = 1; public static final int UTC_TIME = 2; static final long serialVersionUID = -403250971215465050L; static final int currentSerialVersion = 2; private int serialVersionOnStream; private static final int MAX_RULE_NUM = 6;
/*      */   private void decodeRules() {
/* 1355 */     decodeStartRule();
/* 1356 */     decodeEndRule();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void decodeStartRule() {
/* 1384 */     this.useDaylight = (this.startDay != 0 && this.endDay != 0);
/* 1385 */     if (this.startDay != 0) {
/* 1386 */       if (this.startMonth < 0 || this.startMonth > 11) {
/* 1387 */         throw new IllegalArgumentException("Illegal start month " + this.startMonth);
/*      */       }
/*      */       
/* 1390 */       if (this.startTime < 0 || this.startTime > 86400000) {
/* 1391 */         throw new IllegalArgumentException("Illegal start time " + this.startTime);
/*      */       }
/*      */       
/* 1394 */       if (this.startDayOfWeek == 0) {
/* 1395 */         this.startMode = 1;
/*      */       } else {
/* 1397 */         if (this.startDayOfWeek > 0) {
/* 1398 */           this.startMode = 2;
/*      */         } else {
/* 1400 */           this.startDayOfWeek = -this.startDayOfWeek;
/* 1401 */           if (this.startDay > 0) {
/* 1402 */             this.startMode = 3;
/*      */           } else {
/* 1404 */             this.startDay = -this.startDay;
/* 1405 */             this.startMode = 4;
/*      */           } 
/*      */         } 
/* 1408 */         if (this.startDayOfWeek > 7) {
/* 1409 */           throw new IllegalArgumentException("Illegal start day of week " + this.startDayOfWeek);
/*      */         }
/*      */       } 
/*      */       
/* 1413 */       if (this.startMode == 2) {
/* 1414 */         if (this.startDay < -5 || this.startDay > 5) {
/* 1415 */           throw new IllegalArgumentException("Illegal start day of week in month " + this.startDay);
/*      */         }
/*      */       }
/* 1418 */       else if (this.startDay < 1 || this.startDay > staticMonthLength[this.startMonth]) {
/* 1419 */         throw new IllegalArgumentException("Illegal start day " + this.startDay);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void decodeEndRule() {
/* 1431 */     this.useDaylight = (this.startDay != 0 && this.endDay != 0);
/* 1432 */     if (this.endDay != 0) {
/* 1433 */       if (this.endMonth < 0 || this.endMonth > 11) {
/* 1434 */         throw new IllegalArgumentException("Illegal end month " + this.endMonth);
/*      */       }
/*      */       
/* 1437 */       if (this.endTime < 0 || this.endTime > 86400000) {
/* 1438 */         throw new IllegalArgumentException("Illegal end time " + this.endTime);
/*      */       }
/*      */       
/* 1441 */       if (this.endDayOfWeek == 0) {
/* 1442 */         this.endMode = 1;
/*      */       } else {
/* 1444 */         if (this.endDayOfWeek > 0) {
/* 1445 */           this.endMode = 2;
/*      */         } else {
/* 1447 */           this.endDayOfWeek = -this.endDayOfWeek;
/* 1448 */           if (this.endDay > 0) {
/* 1449 */             this.endMode = 3;
/*      */           } else {
/* 1451 */             this.endDay = -this.endDay;
/* 1452 */             this.endMode = 4;
/*      */           } 
/*      */         } 
/* 1455 */         if (this.endDayOfWeek > 7) {
/* 1456 */           throw new IllegalArgumentException("Illegal end day of week " + this.endDayOfWeek);
/*      */         }
/*      */       } 
/*      */       
/* 1460 */       if (this.endMode == 2) {
/* 1461 */         if (this.endDay < -5 || this.endDay > 5) {
/* 1462 */           throw new IllegalArgumentException("Illegal end day of week in month " + this.endDay);
/*      */         }
/*      */       }
/* 1465 */       else if (this.endDay < 1 || this.endDay > staticMonthLength[this.endMonth]) {
/* 1466 */         throw new IllegalArgumentException("Illegal end day " + this.endDay);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void makeRulesCompatible() {
/* 1483 */     switch (this.startMode) {
/*      */       case 1:
/* 1485 */         this.startDay = 1 + this.startDay / 7;
/* 1486 */         this.startDayOfWeek = 1;
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/* 1492 */         if (this.startDay != 1) {
/* 1493 */           this.startDay = 1 + this.startDay / 7;
/*      */         }
/*      */         break;
/*      */       
/*      */       case 4:
/* 1498 */         if (this.startDay >= 30) {
/* 1499 */           this.startDay = -1; break;
/*      */         } 
/* 1501 */         this.startDay = 1 + this.startDay / 7;
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 1506 */     switch (this.endMode) {
/*      */       case 1:
/* 1508 */         this.endDay = 1 + this.endDay / 7;
/* 1509 */         this.endDayOfWeek = 1;
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/* 1515 */         if (this.endDay != 1) {
/* 1516 */           this.endDay = 1 + this.endDay / 7;
/*      */         }
/*      */         break;
/*      */       
/*      */       case 4:
/* 1521 */         if (this.endDay >= 30) {
/* 1522 */           this.endDay = -1; break;
/*      */         } 
/* 1524 */         this.endDay = 1 + this.endDay / 7;
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1539 */     switch (this.startTimeMode) {
/*      */       case 2:
/* 1541 */         this.startTime += this.rawOffset;
/*      */         break;
/*      */     } 
/* 1544 */     while (this.startTime < 0) {
/* 1545 */       this.startTime += 86400000;
/* 1546 */       this.startDayOfWeek = 1 + (this.startDayOfWeek + 5) % 7;
/*      */     } 
/* 1548 */     while (this.startTime >= 86400000) {
/* 1549 */       this.startTime -= 86400000;
/* 1550 */       this.startDayOfWeek = 1 + this.startDayOfWeek % 7;
/*      */     } 
/*      */     
/* 1553 */     switch (this.endTimeMode) {
/*      */       case 2:
/* 1555 */         this.endTime += this.rawOffset + this.dstSavings;
/*      */         break;
/*      */       case 1:
/* 1558 */         this.endTime += this.dstSavings; break;
/*      */     } 
/* 1560 */     while (this.endTime < 0) {
/* 1561 */       this.endTime += 86400000;
/* 1562 */       this.endDayOfWeek = 1 + (this.endDayOfWeek + 5) % 7;
/*      */     } 
/* 1564 */     while (this.endTime >= 86400000) {
/* 1565 */       this.endTime -= 86400000;
/* 1566 */       this.endDayOfWeek = 1 + this.endDayOfWeek % 7;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] packRules() {
/* 1576 */     byte[] arrayOfByte = new byte[6];
/* 1577 */     arrayOfByte[0] = (byte)this.startDay;
/* 1578 */     arrayOfByte[1] = (byte)this.startDayOfWeek;
/* 1579 */     arrayOfByte[2] = (byte)this.endDay;
/* 1580 */     arrayOfByte[3] = (byte)this.endDayOfWeek;
/*      */ 
/*      */     
/* 1583 */     arrayOfByte[4] = (byte)this.startTimeMode;
/* 1584 */     arrayOfByte[5] = (byte)this.endTimeMode;
/*      */     
/* 1586 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void unpackRules(byte[] paramArrayOfbyte) {
/* 1595 */     this.startDay = paramArrayOfbyte[0];
/* 1596 */     this.startDayOfWeek = paramArrayOfbyte[1];
/* 1597 */     this.endDay = paramArrayOfbyte[2];
/* 1598 */     this.endDayOfWeek = paramArrayOfbyte[3];
/*      */ 
/*      */     
/* 1601 */     if (paramArrayOfbyte.length >= 6) {
/* 1602 */       this.startTimeMode = paramArrayOfbyte[4];
/* 1603 */       this.endTimeMode = paramArrayOfbyte[5];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] packTimes() {
/* 1612 */     int[] arrayOfInt = new int[2];
/* 1613 */     arrayOfInt[0] = this.startTime;
/* 1614 */     arrayOfInt[1] = this.endTime;
/* 1615 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void unpackTimes(int[] paramArrayOfint) {
/* 1623 */     this.startTime = paramArrayOfint[0];
/* 1624 */     this.endTime = paramArrayOfint[1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1647 */     byte[] arrayOfByte = packRules();
/* 1648 */     int[] arrayOfInt = packTimes();
/*      */ 
/*      */     
/* 1651 */     makeRulesCompatible();
/*      */ 
/*      */     
/* 1654 */     paramObjectOutputStream.defaultWriteObject();
/*      */ 
/*      */     
/* 1657 */     paramObjectOutputStream.writeInt(arrayOfByte.length);
/* 1658 */     paramObjectOutputStream.write(arrayOfByte);
/* 1659 */     paramObjectOutputStream.writeObject(arrayOfInt);
/*      */ 
/*      */ 
/*      */     
/* 1663 */     unpackRules(arrayOfByte);
/* 1664 */     unpackTimes(arrayOfInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1676 */     paramObjectInputStream.defaultReadObject();
/*      */     
/* 1678 */     if (this.serialVersionOnStream < 1) {
/*      */ 
/*      */ 
/*      */       
/* 1682 */       if (this.startDayOfWeek == 0) {
/* 1683 */         this.startDayOfWeek = 1;
/*      */       }
/* 1685 */       if (this.endDayOfWeek == 0) {
/* 1686 */         this.endDayOfWeek = 1;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1691 */       this.startMode = this.endMode = 2;
/* 1692 */       this.dstSavings = 3600000;
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1697 */       int i = paramObjectInputStream.readInt();
/* 1698 */       if (i <= 6) {
/* 1699 */         byte[] arrayOfByte = new byte[i];
/* 1700 */         paramObjectInputStream.readFully(arrayOfByte);
/* 1701 */         unpackRules(arrayOfByte);
/*      */       } else {
/* 1703 */         throw new InvalidObjectException("Too many rules: " + i);
/*      */       } 
/*      */     } 
/*      */     
/* 1707 */     if (this.serialVersionOnStream >= 2) {
/* 1708 */       int[] arrayOfInt = (int[])paramObjectInputStream.readObject();
/* 1709 */       unpackTimes(arrayOfInt);
/*      */     } 
/*      */     
/* 1712 */     this.serialVersionOnStream = 2;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/SimpleTimeZone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */