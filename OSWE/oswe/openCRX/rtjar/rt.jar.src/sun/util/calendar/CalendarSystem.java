/*     */ package sun.util.calendar;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Properties;
/*     */ import java.util.TimeZone;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CalendarSystem
/*     */ {
/*     */   private static volatile boolean initialized = false;
/*     */   private static ConcurrentMap<String, String> names;
/*     */   private static ConcurrentMap<String, CalendarSystem> calendars;
/*     */   private static final String PACKAGE_NAME = "sun.util.calendar.";
/*  89 */   private static final String[] namePairs = new String[] { "gregorian", "Gregorian", "japanese", "LocalGregorianCalendar", "julian", "JulianCalendar" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initNames() {
/* 102 */     ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */     
/* 106 */     StringBuilder stringBuilder = new StringBuilder();
/* 107 */     for (byte b = 0; b < namePairs.length; b += 2) {
/* 108 */       stringBuilder.setLength(0);
/* 109 */       String str = stringBuilder.append("sun.util.calendar.").append(namePairs[b + 1]).toString();
/* 110 */       concurrentHashMap.put(namePairs[b], str);
/*     */     } 
/* 112 */     synchronized (CalendarSystem.class) {
/* 113 */       if (!initialized) {
/* 114 */         names = (ConcurrentMap)concurrentHashMap;
/* 115 */         calendars = new ConcurrentHashMap<>();
/* 116 */         initialized = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/* 121 */   private static final Gregorian GREGORIAN_INSTANCE = new Gregorian();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Gregorian getGregorianCalendar() {
/* 130 */     return GREGORIAN_INSTANCE;
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
/*     */   public static CalendarSystem forName(String paramString) {
/* 144 */     if ("gregorian".equals(paramString)) {
/* 145 */       return GREGORIAN_INSTANCE;
/*     */     }
/*     */     
/* 148 */     if (!initialized) {
/* 149 */       initNames();
/*     */     }
/*     */     
/* 152 */     CalendarSystem calendarSystem1 = calendars.get(paramString);
/* 153 */     if (calendarSystem1 != null) {
/* 154 */       return calendarSystem1;
/*     */     }
/*     */     
/* 157 */     String str = names.get(paramString);
/* 158 */     if (str == null) {
/* 159 */       return null;
/*     */     }
/*     */     
/* 162 */     if (str.endsWith("LocalGregorianCalendar")) {
/*     */       
/* 164 */       calendarSystem1 = LocalGregorianCalendar.getLocalGregorianCalendar(paramString);
/*     */     } else {
/*     */       try {
/* 167 */         Class<?> clazz = Class.forName(str);
/* 168 */         calendarSystem1 = (CalendarSystem)clazz.newInstance();
/* 169 */       } catch (Exception exception) {
/* 170 */         throw new InternalError(exception);
/*     */       } 
/*     */     } 
/* 173 */     if (calendarSystem1 == null) {
/* 174 */       return null;
/*     */     }
/* 176 */     CalendarSystem calendarSystem2 = calendars.putIfAbsent(paramString, calendarSystem1);
/* 177 */     return (calendarSystem2 == null) ? calendarSystem1 : calendarSystem2;
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
/*     */   public static Properties getCalendarProperties() throws IOException {
/* 189 */     Properties properties = null;
/*     */     try {
/* 191 */       String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("java.home"));
/*     */       
/* 193 */       final String fname = str1 + File.separator + "lib" + File.separator + "calendars.properties";
/*     */       
/* 195 */       properties = AccessController.<Properties>doPrivileged(new PrivilegedExceptionAction<Properties>()
/*     */           {
/*     */             public Properties run() throws IOException {
/* 198 */               Properties properties = new Properties();
/* 199 */               try (FileInputStream null = new FileInputStream(fname)) {
/* 200 */                 properties.load(fileInputStream);
/*     */               } 
/* 202 */               return properties;
/*     */             }
/*     */           });
/* 205 */     } catch (PrivilegedActionException privilegedActionException) {
/* 206 */       Throwable throwable = privilegedActionException.getCause();
/* 207 */       if (throwable instanceof IOException)
/* 208 */         throw (IOException)throwable; 
/* 209 */       if (throwable instanceof IllegalArgumentException) {
/* 210 */         throw (IllegalArgumentException)throwable;
/*     */       }
/*     */       
/* 213 */       throw new InternalError(throwable);
/*     */     } 
/* 215 */     return properties;
/*     */   }
/*     */   
/*     */   public abstract String getName();
/*     */   
/*     */   public abstract CalendarDate getCalendarDate();
/*     */   
/*     */   public abstract CalendarDate getCalendarDate(long paramLong);
/*     */   
/*     */   public abstract CalendarDate getCalendarDate(long paramLong, CalendarDate paramCalendarDate);
/*     */   
/*     */   public abstract CalendarDate getCalendarDate(long paramLong, TimeZone paramTimeZone);
/*     */   
/*     */   public abstract CalendarDate newCalendarDate();
/*     */   
/*     */   public abstract CalendarDate newCalendarDate(TimeZone paramTimeZone);
/*     */   
/*     */   public abstract long getTime(CalendarDate paramCalendarDate);
/*     */   
/*     */   public abstract int getYearLength(CalendarDate paramCalendarDate);
/*     */   
/*     */   public abstract int getYearLengthInMonths(CalendarDate paramCalendarDate);
/*     */   
/*     */   public abstract int getMonthLength(CalendarDate paramCalendarDate);
/*     */   
/*     */   public abstract int getWeekLength();
/*     */   
/*     */   public abstract Era getEra(String paramString);
/*     */   
/*     */   public abstract Era[] getEras();
/*     */   
/*     */   public abstract void setEra(CalendarDate paramCalendarDate, String paramString);
/*     */   
/*     */   public abstract CalendarDate getNthDayOfWeek(int paramInt1, int paramInt2, CalendarDate paramCalendarDate);
/*     */   
/*     */   public abstract CalendarDate setTimeOfDay(CalendarDate paramCalendarDate, int paramInt);
/*     */   
/*     */   public abstract boolean validate(CalendarDate paramCalendarDate);
/*     */   
/*     */   public abstract boolean normalize(CalendarDate paramCalendarDate);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/calendar/CalendarSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */