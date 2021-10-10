/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import sun.util.calendar.BaseCalendar;
/*      */ import sun.util.calendar.CalendarDate;
/*      */ import sun.util.calendar.CalendarSystem;
/*      */ import sun.util.calendar.CalendarUtils;
/*      */ import sun.util.calendar.Era;
/*      */ import sun.util.calendar.Gregorian;
/*      */ import sun.util.calendar.LocalGregorianCalendar;
/*      */ import sun.util.calendar.ZoneInfo;
/*      */ import sun.util.locale.provider.CalendarDataUtility;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class JapaneseImperialCalendar
/*      */   extends Calendar
/*      */ {
/*      */   public static final int BEFORE_MEIJI = 0;
/*      */   public static final int MEIJI = 1;
/*      */   public static final int TAISHO = 2;
/*      */   public static final int SHOWA = 3;
/*      */   public static final int HEISEI = 4;
/*      */   private static final int REIWA = 5;
/*      */   private static final int EPOCH_OFFSET = 719163;
/*      */   private static final int ONE_SECOND = 1000;
/*      */   private static final int ONE_MINUTE = 60000;
/*      */   private static final int ONE_HOUR = 3600000;
/*      */   private static final long ONE_DAY = 86400000L;
/*      */   private static final long ONE_WEEK = 604800000L;
/*  123 */   private static final LocalGregorianCalendar jcal = (LocalGregorianCalendar)CalendarSystem.forName("japanese");
/*      */ 
/*      */ 
/*      */   
/*  127 */   private static final Gregorian gcal = CalendarSystem.getGregorianCalendar();
/*      */ 
/*      */   
/*  130 */   private static final Era BEFORE_MEIJI_ERA = new Era("BeforeMeiji", "BM", Long.MIN_VALUE, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Era[] eras;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long[] sinceFixedDates;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int currentEra;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  169 */   static final int[] MIN_VALUES = new int[] { 0, -292275055, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, -46800000, 0 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  188 */   static final int[] LEAST_MAX_VALUES = new int[] { 0, 0, 0, 0, 4, 28, 0, 7, 4, 1, 11, 23, 59, 59, 999, 50400000, 1200000 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  207 */   static final int[] MAX_VALUES = new int[] { 0, 292278994, 11, 53, 6, 31, 366, 7, 6, 1, 11, 23, 59, 59, 999, 50400000, 7200000 };
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = -3364572813905467929L;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient LocalGregorianCalendar.Date jdate;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient int[] zoneOffsets;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient int[] originalFields;
/*      */ 
/*      */   
/*      */   private transient long cachedFixedDate;
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  231 */     Era[] arrayOfEra = jcal.getEras();
/*  232 */     int i = arrayOfEra.length + 1;
/*  233 */     eras = new Era[i];
/*  234 */     sinceFixedDates = new long[i];
/*      */ 
/*      */ 
/*      */     
/*  238 */     byte b1 = 0;
/*  239 */     byte b2 = b1;
/*  240 */     sinceFixedDates[b1] = gcal.getFixedDate(BEFORE_MEIJI_ERA.getSinceDate());
/*  241 */     eras[b1++] = BEFORE_MEIJI_ERA;
/*  242 */     for (Era era : arrayOfEra) {
/*  243 */       if (era.getSince(TimeZone.NO_TIMEZONE) < System.currentTimeMillis()) {
/*  244 */         b2 = b1;
/*      */       }
/*  246 */       CalendarDate calendarDate = era.getSinceDate();
/*  247 */       sinceFixedDates[b1] = gcal.getFixedDate(calendarDate);
/*  248 */       eras[b1++] = era;
/*      */     } 
/*  250 */     currentEra = b2;
/*      */     
/*  252 */     MAX_VALUES[0] = eras.length - 1; LEAST_MAX_VALUES[0] = eras.length - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  257 */     int j = Integer.MAX_VALUE;
/*  258 */     int k = Integer.MAX_VALUE;
/*  259 */     Gregorian.Date date = gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
/*  260 */     for (byte b3 = 1; b3 < eras.length; b3++) {
/*  261 */       long l1 = sinceFixedDates[b3];
/*  262 */       CalendarDate calendarDate = eras[b3].getSinceDate();
/*  263 */       date.setDate(calendarDate.getYear(), 1, 1);
/*  264 */       long l2 = gcal.getFixedDate(date);
/*  265 */       if (l1 != l2) {
/*  266 */         k = Math.min((int)(l1 - l2) + 1, k);
/*      */       }
/*  268 */       date.setDate(calendarDate.getYear(), 12, 31);
/*  269 */       l2 = gcal.getFixedDate(date);
/*  270 */       if (l1 != l2) {
/*  271 */         k = Math.min((int)(l2 - l1) + 1, k);
/*      */       }
/*  273 */       LocalGregorianCalendar.Date date1 = getCalendarDate(l1 - 1L);
/*  274 */       int m = date1.getYear();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  279 */       if (date1.getMonth() != 1 || date1.getDayOfMonth() != 1) {
/*  280 */         m--;
/*      */       }
/*  282 */       j = Math.min(m, j);
/*      */     } 
/*  284 */     LEAST_MAX_VALUES[1] = j;
/*  285 */     LEAST_MAX_VALUES[6] = k;
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
/*      */   
/*      */   JapaneseImperialCalendar(TimeZone paramTimeZone, Locale paramLocale)
/*      */   {
/*  315 */     super(paramTimeZone, paramLocale);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1546 */     this.cachedFixedDate = Long.MIN_VALUE; this.jdate = jcal.newCalendarDate(paramTimeZone); setTimeInMillis(System.currentTimeMillis()); } public String getCalendarType() { return "japanese"; } public boolean equals(Object paramObject) { return (paramObject instanceof JapaneseImperialCalendar && super.equals(paramObject)); } public int hashCode() { return super.hashCode() ^ this.jdate.hashCode(); } public void add(int paramInt1, int paramInt2) { if (paramInt2 == 0) return;  if (paramInt1 < 0 || paramInt1 >= 15) throw new IllegalArgumentException();  complete(); if (paramInt1 == 1) { LocalGregorianCalendar.Date date = (LocalGregorianCalendar.Date)this.jdate.clone(); date.addYear(paramInt2); pinDayOfMonth(date); set(0, getEraIndex(date)); set(1, date.getYear()); set(2, date.getMonth() - 1); set(5, date.getDayOfMonth()); } else if (paramInt1 == 2) { LocalGregorianCalendar.Date date = (LocalGregorianCalendar.Date)this.jdate.clone(); date.addMonth(paramInt2); pinDayOfMonth(date); set(0, getEraIndex(date)); set(1, date.getYear()); set(2, date.getMonth() - 1); set(5, date.getDayOfMonth()); } else if (paramInt1 == 0) { int i = internalGet(0) + paramInt2; if (i < 0) { i = 0; } else if (i > eras.length - 1) { i = eras.length - 1; }  set(0, i); } else { long l1 = paramInt2; long l2 = 0L; switch (paramInt1) { case 10: case 11: l1 *= 3600000L; break;case 12: l1 *= 60000L; break;case 13: l1 *= 1000L; break;case 3: case 4: case 8: l1 *= 7L; break;case 9: l1 = (paramInt2 / 2); l2 = (12 * paramInt2 % 2); break; }  if (paramInt1 >= 10) { setTimeInMillis(this.time + l1); return; }  long l3 = this.cachedFixedDate; l2 += internalGet(11); l2 *= 60L; l2 += internalGet(12); l2 *= 60L; l2 += internalGet(13); l2 *= 1000L; l2 += internalGet(14); if (l2 >= 86400000L) { l3++; l2 -= 86400000L; } else if (l2 < 0L) { l3--; l2 += 86400000L; }  l3 += l1; int i = internalGet(15) + internalGet(16); setTimeInMillis((l3 - 719163L) * 86400000L + l2 - i); i -= internalGet(15) + internalGet(16); if (i != 0) { setTimeInMillis(this.time + i); long l = this.cachedFixedDate; if (l != l3) setTimeInMillis(this.time - i);  }  }  } public void roll(int paramInt, boolean paramBoolean) { roll(paramInt, paramBoolean ? 1 : -1); } JapaneseImperialCalendar(TimeZone paramTimeZone, Locale paramLocale, boolean paramBoolean) { super(paramTimeZone, paramLocale); this.cachedFixedDate = Long.MIN_VALUE; this.jdate = jcal.newCalendarDate(paramTimeZone); }
/*      */   public void roll(int paramInt1, int paramInt2) { int m; boolean bool; long l2; int k; long l1; int n; long l3; int i2; long l5; int i1; long l4; LocalGregorianCalendar.Date date2; long l7; LocalGregorianCalendar.Date date1; long l6; int i4; long l8; int i3, i6; long l9; LocalGregorianCalendar.Date date3; int i5, i7; LocalGregorianCalendar.Date date4; long l10; int i8; LocalGregorianCalendar.Date date5; int i9; long l11; if (paramInt2 == 0) return;  if (paramInt1 < 0 || paramInt1 >= 15) throw new IllegalArgumentException();  complete(); int i = getMinimum(paramInt1); int j = getMaximum(paramInt1); switch (paramInt1) { case 10: case 11: m = j + 1; n = internalGet(paramInt1); i2 = (n + paramInt2) % m; if (i2 < 0) i2 += m;  this.time += (3600000 * (i2 - n)); date2 = jcal.getCalendarDate(this.time, getZone()); if (internalGet(5) != date2.getDayOfMonth()) { date2.setEra(this.jdate.getEra()); date2.setDate(internalGet(1), internalGet(2) + 1, internalGet(5)); if (paramInt1 == 10) { assert internalGet(9) == 1; date2.addHours(12); }  this.time = jcal.getTime(date2); }  i4 = date2.getHours(); internalSet(paramInt1, i4 % m); if (paramInt1 == 10) { internalSet(11, i4); } else { internalSet(9, i4 / 12); internalSet(10, i4 % 12); }  i6 = date2.getZoneOffset(); i7 = date2.getDaylightSaving(); internalSet(15, i6 - i7); internalSet(16, i7); return;case 1: i = getActualMinimum(paramInt1); j = getActualMaximum(paramInt1); break;case 2: if (!isTransitionYear(this.jdate.getNormalizedYear())) { m = this.jdate.getYear(); if (m == getMaximum(1)) { LocalGregorianCalendar.Date date6 = jcal.getCalendarDate(this.time, getZone()); LocalGregorianCalendar.Date date7 = jcal.getCalendarDate(Long.MAX_VALUE, getZone()); j = date7.getMonth() - 1; int i10 = getRolledValue(internalGet(paramInt1), paramInt2, i, j); if (i10 == j) { date6.addYear(-400); date6.setMonth(i10 + 1); if (date6.getDayOfMonth() > date7.getDayOfMonth()) { date6.setDayOfMonth(date7.getDayOfMonth()); jcal.normalize(date6); }  if (date6.getDayOfMonth() == date7.getDayOfMonth() && date6.getTimeOfDay() > date7.getTimeOfDay()) { date6.setMonth(i10 + 1); date6.setDayOfMonth(date7.getDayOfMonth() - 1); jcal.normalize(date6); i10 = date6.getMonth() - 1; }  set(5, date6.getDayOfMonth()); }  set(2, i10); } else if (m == getMinimum(1)) { LocalGregorianCalendar.Date date6 = jcal.getCalendarDate(this.time, getZone()); LocalGregorianCalendar.Date date7 = jcal.getCalendarDate(Long.MIN_VALUE, getZone()); i = date7.getMonth() - 1; int i10 = getRolledValue(internalGet(paramInt1), paramInt2, i, j); if (i10 == i) { date6.addYear(400); date6.setMonth(i10 + 1); if (date6.getDayOfMonth() < date7.getDayOfMonth()) { date6.setDayOfMonth(date7.getDayOfMonth()); jcal.normalize(date6); }  if (date6.getDayOfMonth() == date7.getDayOfMonth() && date6.getTimeOfDay() < date7.getTimeOfDay()) { date6.setMonth(i10 + 1); date6.setDayOfMonth(date7.getDayOfMonth() + 1); jcal.normalize(date6); i10 = date6.getMonth() - 1; }  set(5, date6.getDayOfMonth()); }  set(2, i10); } else { n = (internalGet(2) + paramInt2) % 12; if (n < 0) n += 12;  set(2, n); i2 = monthLength(n); if (internalGet(5) > i2) set(5, i2);  }  } else { m = getEraIndex(this.jdate); CalendarDate calendarDate = null; if (this.jdate.getYear() == 1) { calendarDate = eras[m].getSinceDate(); i = calendarDate.getMonth() - 1; } else if (m < eras.length - 1) { calendarDate = eras[m + 1].getSinceDate(); if (calendarDate.getYear() == this.jdate.getNormalizedYear()) { j = calendarDate.getMonth() - 1; if (calendarDate.getDayOfMonth() == 1) j--;  }  }  if (i == j) return;  i2 = getRolledValue(internalGet(paramInt1), paramInt2, i, j); set(2, i2); if (i2 == i) { if ((calendarDate.getMonth() != 1 || calendarDate.getDayOfMonth() != 1) && this.jdate.getDayOfMonth() < calendarDate.getDayOfMonth()) set(5, calendarDate.getDayOfMonth());  } else if (i2 == j && calendarDate.getMonth() - 1 == i2) { int i10 = calendarDate.getDayOfMonth(); if (this.jdate.getDayOfMonth() >= i10) set(5, i10 - 1);  }  }  return;case 3: m = this.jdate.getNormalizedYear(); j = getActualMaximum(3); set(7, internalGet(7)); n = internalGet(3); i2 = n + paramInt2; if (!isTransitionYear(this.jdate.getNormalizedYear())) { int i10 = this.jdate.getYear(); if (i10 == getMaximum(1)) { j = getActualMaximum(3); } else if (i10 == getMinimum(1)) { i = getActualMinimum(3); j = getActualMaximum(3); if (i2 > i && i2 < j) { set(3, i2); return; }  }  if (i2 > i && i2 < j) { set(3, i2); return; }  l8 = this.cachedFixedDate; long l = l8 - (7 * (n - i)); if (i10 != getMinimum(1)) { if (gcal.getYearFromFixedDate(l) != m) i++;  } else { LocalGregorianCalendar.Date date = jcal.getCalendarDate(Long.MIN_VALUE, getZone()); if (l < jcal.getFixedDate(date)) i++;  }  l8 += (7 * (j - internalGet(3))); if (gcal.getYearFromFixedDate(l8) != m) j--;  break; }  l7 = this.cachedFixedDate; l9 = l7 - (7 * (n - i)); date4 = getCalendarDate(l9); if (date4.getEra() != this.jdate.getEra() || date4.getYear() != this.jdate.getYear()) i++;  l7 += (7 * (j - n)); jcal.getCalendarDateFromFixedDate(date4, l7); if (date4.getEra() != this.jdate.getEra() || date4.getYear() != this.jdate.getYear()) j--;  i2 = getRolledValue(n, paramInt2, i, j) - 1; date4 = getCalendarDate(l9 + (i2 * 7)); set(2, date4.getMonth() - 1); set(5, date4.getDayOfMonth()); return;case 4: bool = isTransitionYear(this.jdate.getNormalizedYear()); n = internalGet(7) - getFirstDayOfWeek(); if (n < 0) n += 7;  l5 = this.cachedFixedDate; if (bool) { l8 = getFixedDateMonth1(this.jdate, l5); i7 = actualMonthLength(); } else { l8 = l5 - internalGet(5) + 1L; i7 = jcal.getMonthLength(this.jdate); }  l10 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l8 + 6L, getFirstDayOfWeek()); if ((int)(l10 - l8) >= getMinimalDaysInFirstWeek()) l10 -= 7L;  j = getActualMaximum(paramInt1); i9 = getRolledValue(internalGet(paramInt1), paramInt2, 1, j) - 1; l11 = l10 + (i9 * 7) + n; if (l11 < l8) { l11 = l8; } else if (l11 >= l8 + i7) { l11 = l8 + i7 - 1L; }  set(5, (int)(l11 - l8) + 1); return;case 5: if (!isTransitionYear(this.jdate.getNormalizedYear())) { j = jcal.getMonthLength(this.jdate); break; }  l2 = getFixedDateMonth1(this.jdate, this.cachedFixedDate); i1 = getRolledValue((int)(this.cachedFixedDate - l2), paramInt2, 0, actualMonthLength() - 1); date1 = getCalendarDate(l2 + i1); assert getEraIndex(date1) == internalGetEra() && date1.getYear() == internalGet(1) && date1.getMonth() - 1 == internalGet(2); set(5, date1.getDayOfMonth()); return;case 6: j = getActualMaximum(paramInt1); if (!isTransitionYear(this.jdate.getNormalizedYear())) break;  k = getRolledValue(internalGet(6), paramInt2, i, j); l3 = this.cachedFixedDate - internalGet(6); date1 = getCalendarDate(l3 + k); assert getEraIndex(date1) == internalGetEra() && date1.getYear() == internalGet(1); set(2, date1.getMonth() - 1); set(5, date1.getDayOfMonth()); return;case 7: k = this.jdate.getNormalizedYear(); if (!isTransitionYear(k) && !isTransitionYear(k - 1)) { int i10 = internalGet(3); if (i10 > 1 && i10 < 52) { set(3, internalGet(3)); j = 7; break; }  }  paramInt2 %= 7; if (paramInt2 == 0) return;  l3 = this.cachedFixedDate; l6 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l3, getFirstDayOfWeek()); l3 += paramInt2; if (l3 < l6) { l3 += 7L; } else if (l3 >= l6 + 7L) { l3 -= 7L; }  date3 = getCalendarDate(l3); set(0, getEraIndex(date3)); set(date3.getYear(), date3.getMonth() - 1, date3.getDayOfMonth()); return;case 8: i = 1; if (!isTransitionYear(this.jdate.getNormalizedYear())) { k = internalGet(5); int i10 = jcal.getMonthLength(this.jdate); i1 = i10 % 7; j = i10 / 7; int i11 = (k - 1) % 7; if (i11 < i1)
/*      */             j++;  set(7, internalGet(7)); break; }  l1 = this.cachedFixedDate; l4 = getFixedDateMonth1(this.jdate, l1); i3 = actualMonthLength(); i5 = i3 % 7; j = i3 / 7; i7 = (int)(l1 - l4) % 7; if (i7 < i5)
/*      */           j++;  i8 = getRolledValue(internalGet(paramInt1), paramInt2, i, j) - 1; l1 = l4 + (i8 * 7) + i7; date5 = getCalendarDate(l1); set(5, date5.getDayOfMonth()); return; }  set(paramInt1, getRolledValue(internalGet(paramInt1), paramInt2, i, j)); }
/*      */   public String getDisplayName(int paramInt1, int paramInt2, Locale paramLocale) { if (!checkDisplayNameParams(paramInt1, paramInt2, 1, 4, paramLocale, 647))
/*      */       return null;  int i = get(paramInt1); if (paramInt1 == 1 && (getBaseStyle(paramInt2) != 2 || i != 1 || get(0) == 0))
/*      */       return null;  String str = CalendarDataUtility.retrieveFieldValueName(getCalendarType(), paramInt1, i, paramInt2, paramLocale); if ((str == null || str.isEmpty()) && paramInt1 == 0 && i < eras.length) { Era era = eras[i]; str = (paramInt2 == 1) ? era.getAbbreviation() : era.getName(); }  return str; }
/*      */   public Map<String, Integer> getDisplayNames(int paramInt1, int paramInt2, Locale paramLocale) { if (!checkDisplayNameParams(paramInt1, paramInt2, 0, 4, paramLocale, 647))
/*      */       return null;  Map<String, Integer> map = CalendarDataUtility.retrieveFieldValueNames(getCalendarType(), paramInt1, paramInt2, paramLocale); if (map != null && paramInt1 == 0) { int i = map.size(); if (paramInt2 == 0) { HashSet hashSet = new HashSet(); for (String str : map.keySet())
/*      */           hashSet.add(map.get(str));  i = hashSet.size(); }  if (i < eras.length) { int j = getBaseStyle(paramInt2); for (int k = i; k < eras.length; k++) { Era era = eras[k]; if (j == 0 || j == 1 || j == 4)
/*      */             map.put(era.getAbbreviation(), Integer.valueOf(k));  if (j == 0 || j == 2)
/*      */             map.put(era.getName(), Integer.valueOf(k));  }  }  }  return map; }
/* 1558 */   public int getMinimum(int paramInt) { return MIN_VALUES[paramInt]; } protected void computeFields() { int i = 0;
/* 1559 */     if (isPartiallyNormalized()) {
/*      */       
/* 1561 */       i = getSetStateFields();
/* 1562 */       int j = (i ^ 0xFFFFFFFF) & 0x1FFFF;
/* 1563 */       if (j != 0 || this.cachedFixedDate == Long.MIN_VALUE) {
/* 1564 */         i |= computeFields(j, i & 0x18000);
/*      */         
/* 1566 */         assert i == 131071;
/*      */       } 
/*      */     } else {
/*      */       
/* 1570 */       i = 131071;
/* 1571 */       computeFields(i, 0);
/*      */     } 
/*      */     
/* 1574 */     setFieldsComputed(i); } public int getMaximum(int paramInt) { LocalGregorianCalendar.Date date; switch (paramInt) { case 1: date = jcal.getCalendarDate(Long.MAX_VALUE, getZone()); return Math.max(LEAST_MAX_VALUES[1], date.getYear()); }  return MAX_VALUES[paramInt]; }
/*      */   public int getGreatestMinimum(int paramInt) { return (paramInt == 1) ? 1 : MIN_VALUES[paramInt]; }
/*      */   public int getLeastMaximum(int paramInt) { switch (paramInt) { case 1: return Math.min(LEAST_MAX_VALUES[1], getMaximum(1)); }  return LEAST_MAX_VALUES[paramInt]; }
/*      */   public int getActualMinimum(int paramInt) { LocalGregorianCalendar.Date date2; int k; long l1, l2; int m; long l3; if (!isFieldSet(14, paramInt)) return getMinimum(paramInt);  int i = 0; JapaneseImperialCalendar japaneseImperialCalendar = getNormalizedCalendar(); LocalGregorianCalendar.Date date1 = jcal.getCalendarDate(japaneseImperialCalendar.getTimeInMillis(), getZone()); int j = getEraIndex(date1); switch (paramInt) { case 1: if (j > 0) { i = 1; long l = eras[j].getSince(getZone()); LocalGregorianCalendar.Date date = jcal.getCalendarDate(l, getZone()); date1.setYear(date.getYear()); jcal.normalize(date1); assert date1.isLeapYear() == date.isLeapYear(); if (getYearOffsetInMillis(date1) < getYearOffsetInMillis(date)) i++;  break; }  i = getMinimum(paramInt); date2 = jcal.getCalendarDate(Long.MIN_VALUE, getZone()); k = date2.getYear(); if (k > 400) k -= 400;  date1.setYear(k); jcal.normalize(date1); if (getYearOffsetInMillis(date1) < getYearOffsetInMillis(date2)) i++;  break;case 2: if (j > 1 && date1.getYear() == 1) { long l = eras[j].getSince(getZone()); LocalGregorianCalendar.Date date = jcal.getCalendarDate(l, getZone()); i = date.getMonth() - 1; if (date1.getDayOfMonth() < date.getDayOfMonth()) i++;  }  break;case 3: i = 1; date2 = jcal.getCalendarDate(Long.MIN_VALUE, getZone()); date2.addYear(400); jcal.normalize(date2); date1.setEra(date2.getEra()); date1.setYear(date2.getYear()); jcal.normalize(date1); l1 = jcal.getFixedDate(date2); l2 = jcal.getFixedDate(date1); m = getWeekNumber(l1, l2); l3 = l2 - (7 * (m - 1)); if (l3 < l1 || (l3 == l1 && date1.getTimeOfDay() < date2.getTimeOfDay())) i++;  break; }  return i; }
/*      */   public int getActualMaximum(int paramInt) { LocalGregorianCalendar.Date date3; int k; LocalGregorianCalendar.Date date2; int m; LocalGregorianCalendar.Date date4; int n; BaseCalendar.Date date; int i1; if ((0x1FE81 & 1 << paramInt) != 0) return getMaximum(paramInt);  JapaneseImperialCalendar japaneseImperialCalendar = getNormalizedCalendar(); LocalGregorianCalendar.Date date1 = japaneseImperialCalendar.jdate; int i = date1.getNormalizedYear(); int j = -1; switch (paramInt) { case 2: j = 11; if (isTransitionYear(date1.getNormalizedYear())) { int i2 = getEraIndex(date1); if (date1.getYear() != 1) { i2++; assert i2 < eras.length; }  long l1 = sinceFixedDates[i2]; long l2 = japaneseImperialCalendar.cachedFixedDate; if (l2 < l1) { LocalGregorianCalendar.Date date5 = (LocalGregorianCalendar.Date)date1.clone(); jcal.getCalendarDateFromFixedDate(date5, l1 - 1L); j = date5.getMonth() - 1; }  } else { LocalGregorianCalendar.Date date5 = jcal.getCalendarDate(Long.MAX_VALUE, getZone()); if (date1.getEra() == date5.getEra() && date1.getYear() == date5.getYear()) j = date5.getMonth() - 1;  }  return j;case 5: j = jcal.getMonthLength(date1); return j;case 6: if (isTransitionYear(date1.getNormalizedYear())) { int i2 = getEraIndex(date1); if (date1.getYear() != 1) { i2++; assert i2 < eras.length; }  long l1 = sinceFixedDates[i2]; long l2 = japaneseImperialCalendar.cachedFixedDate; Gregorian.Date date5 = gcal.newCalendarDate(TimeZone.NO_TIMEZONE); date5.setDate(date1.getNormalizedYear(), 1, 1); if (l2 < l1) { j = (int)(l1 - gcal.getFixedDate(date5)); } else { date5.addYear(1); j = (int)(gcal.getFixedDate(date5) - l1); }  } else { LocalGregorianCalendar.Date date5 = jcal.getCalendarDate(Long.MAX_VALUE, getZone()); if (date1.getEra() == date5.getEra() && date1.getYear() == date5.getYear()) { long l1 = jcal.getFixedDate(date5); long l2 = getFixedDateJan1(date5, l1); j = (int)(l1 - l2) + 1; } else if (date1.getYear() == getMinimum(1)) { date4 = jcal.getCalendarDate(Long.MIN_VALUE, getZone()); long l1 = jcal.getFixedDate(date4); date4.addYear(1); date4.setMonth(1).setDayOfMonth(1); jcal.normalize(date4); long l2 = jcal.getFixedDate(date4); j = (int)(l2 - l1); } else { j = jcal.getYearLength(date1); }  }  return j;case 3: if (!isTransitionYear(date1.getNormalizedYear())) { LocalGregorianCalendar.Date date5 = jcal.getCalendarDate(Long.MAX_VALUE, getZone()); if (date1.getEra() == date5.getEra() && date1.getYear() == date5.getYear()) { long l1 = jcal.getFixedDate(date5); long l2 = getFixedDateJan1(date5, l1); j = getWeekNumber(l2, l1); } else if (date1.getEra() == null && date1.getYear() == getMinimum(1)) { date4 = jcal.getCalendarDate(Long.MIN_VALUE, getZone()); date4.addYear(400); jcal.normalize(date4); date5.setEra(date4.getEra()); date5.setDate(date4.getYear() + 1, 1, 1); jcal.normalize(date5); long l1 = jcal.getFixedDate(date4); long l2 = jcal.getFixedDate(date5); long l3 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l2 + 6L, getFirstDayOfWeek()); int i2 = (int)(l3 - l2); if (i2 >= getMinimalDaysInFirstWeek()) l3 -= 7L;  j = getWeekNumber(l1, l3); } else { Gregorian.Date date6 = gcal.newCalendarDate(TimeZone.NO_TIMEZONE); date6.setDate(date1.getNormalizedYear(), 1, 1); int i2 = gcal.getDayOfWeek(date6); i2 -= getFirstDayOfWeek(); if (i2 < 0)
/*      */               i2 += 7;  j = 52; int i3 = i2 + getMinimalDaysInFirstWeek() - 1; if (i3 == 6 || (date1.isLeapYear() && (i3 == 5 || i3 == 12)))
/*      */               j++;  }  } else { if (japaneseImperialCalendar == this)
/*      */             japaneseImperialCalendar = (JapaneseImperialCalendar)japaneseImperialCalendar.clone();  int i2 = getActualMaximum(6); japaneseImperialCalendar.set(6, i2); j = japaneseImperialCalendar.get(3); if (j == 1 && i2 > 7) { japaneseImperialCalendar.add(3, -1); j = japaneseImperialCalendar.get(3); }  }  return j;case 4: date3 = jcal.getCalendarDate(Long.MAX_VALUE, getZone()); if (date1.getEra() != date3.getEra() || date1.getYear() != date3.getYear()) { Gregorian.Date date5 = gcal.newCalendarDate(TimeZone.NO_TIMEZONE); date5.setDate(date1.getNormalizedYear(), date1.getMonth(), 1); int i2 = gcal.getDayOfWeek(date5); int i3 = gcal.getMonthLength(date5); i2 -= getFirstDayOfWeek(); if (i2 < 0)
/*      */             i2 += 7;  int i4 = 7 - i2; j = 3; if (i4 >= getMinimalDaysInFirstWeek())
/*      */             j++;  i3 -= i4 + 21; if (i3 > 0) { j++; if (i3 > 7)
/*      */               j++;  }  } else { long l1 = jcal.getFixedDate(date3); long l2 = l1 - date3.getDayOfMonth() + 1L; j = getWeekNumber(l2, l1); }  return j;case 8: n = date1.getDayOfWeek(); date = (BaseCalendar.Date)date1.clone(); k = jcal.getMonthLength(date); date.setDayOfMonth(1); jcal.normalize(date); m = date.getDayOfWeek(); i1 = n - m; if (i1 < 0)
/*      */           i1 += 7;  k -= i1; j = (k + 6) / 7; return j;case 1: date2 = jcal.getCalendarDate(japaneseImperialCalendar.getTimeInMillis(), getZone()); n = getEraIndex(date1); if (n == eras.length - 1) { date4 = jcal.getCalendarDate(Long.MAX_VALUE, getZone()); j = date4.getYear(); if (j > 400)
/*      */             date2.setYear(j - 400);  } else { date4 = jcal.getCalendarDate(eras[n + 1].getSince(getZone()) - 1L, getZone()); j = date4.getYear(); date2.setYear(j); }  jcal.normalize(date2); if (getYearOffsetInMillis(date2) > getYearOffsetInMillis(date4))
/*      */           j--;  return j; }  throw new ArrayIndexOutOfBoundsException(paramInt); }
/*      */   private long getYearOffsetInMillis(CalendarDate paramCalendarDate) { long l = (jcal.getDayOfYear(paramCalendarDate) - 1L) * 86400000L; return l + paramCalendarDate.getTimeOfDay() - paramCalendarDate.getZoneOffset(); }
/*      */   public Object clone() { JapaneseImperialCalendar japaneseImperialCalendar = (JapaneseImperialCalendar)super.clone(); japaneseImperialCalendar.jdate = (LocalGregorianCalendar.Date)this.jdate.clone(); japaneseImperialCalendar.originalFields = null; japaneseImperialCalendar.zoneOffsets = null; return japaneseImperialCalendar; }
/*      */   public TimeZone getTimeZone() { TimeZone timeZone = super.getTimeZone(); this.jdate.setZone(timeZone); return timeZone; }
/*      */   public void setTimeZone(TimeZone paramTimeZone) { super.setTimeZone(paramTimeZone); this.jdate.setZone(paramTimeZone); }
/* 1592 */   private int computeFields(int paramInt1, int paramInt2) { int i = 0;
/* 1593 */     TimeZone timeZone = getZone();
/* 1594 */     if (this.zoneOffsets == null) {
/* 1595 */       this.zoneOffsets = new int[2];
/*      */     }
/* 1597 */     if (paramInt2 != 98304) {
/* 1598 */       if (timeZone instanceof ZoneInfo) {
/* 1599 */         i = ((ZoneInfo)timeZone).getOffsets(this.time, this.zoneOffsets);
/*      */       } else {
/* 1601 */         i = timeZone.getOffset(this.time);
/* 1602 */         this.zoneOffsets[0] = timeZone.getRawOffset();
/* 1603 */         this.zoneOffsets[1] = i - this.zoneOffsets[0];
/*      */       } 
/*      */     }
/* 1606 */     if (paramInt2 != 0) {
/* 1607 */       if (isFieldSet(paramInt2, 15)) {
/* 1608 */         this.zoneOffsets[0] = internalGet(15);
/*      */       }
/* 1610 */       if (isFieldSet(paramInt2, 16)) {
/* 1611 */         this.zoneOffsets[1] = internalGet(16);
/*      */       }
/* 1613 */       i = this.zoneOffsets[0] + this.zoneOffsets[1];
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1619 */     long l = i / 86400000L;
/* 1620 */     int j = i % 86400000;
/* 1621 */     l += this.time / 86400000L;
/* 1622 */     j += (int)(this.time % 86400000L);
/* 1623 */     if (j >= 86400000L) {
/* 1624 */       j = (int)(j - 86400000L);
/* 1625 */       l++;
/*      */     } else {
/* 1627 */       while (j < 0) {
/* 1628 */         j = (int)(j + 86400000L);
/* 1629 */         l--;
/*      */       } 
/*      */     } 
/* 1632 */     l += 719163L;
/*      */ 
/*      */     
/* 1635 */     if (l != this.cachedFixedDate || l < 0L) {
/* 1636 */       jcal.getCalendarDateFromFixedDate(this.jdate, l);
/* 1637 */       this.cachedFixedDate = l;
/*      */     } 
/* 1639 */     int k = getEraIndex(this.jdate);
/* 1640 */     int m = this.jdate.getYear();
/*      */ 
/*      */     
/* 1643 */     internalSet(0, k);
/* 1644 */     internalSet(1, m);
/* 1645 */     int n = paramInt1 | 0x3;
/*      */     
/* 1647 */     int i1 = this.jdate.getMonth() - 1;
/* 1648 */     int i2 = this.jdate.getDayOfMonth();
/*      */ 
/*      */     
/* 1651 */     if ((paramInt1 & 0xA4) != 0) {
/*      */       
/* 1653 */       internalSet(2, i1);
/* 1654 */       internalSet(5, i2);
/* 1655 */       internalSet(7, this.jdate.getDayOfWeek());
/* 1656 */       n |= 0xA4;
/*      */     } 
/*      */     
/* 1659 */     if ((paramInt1 & 0x7E00) != 0) {
/*      */       
/* 1661 */       if (j != 0) {
/* 1662 */         int i3 = j / 3600000;
/* 1663 */         internalSet(11, i3);
/* 1664 */         internalSet(9, i3 / 12);
/* 1665 */         internalSet(10, i3 % 12);
/* 1666 */         int i4 = j % 3600000;
/* 1667 */         internalSet(12, i4 / 60000);
/* 1668 */         i4 %= 60000;
/* 1669 */         internalSet(13, i4 / 1000);
/* 1670 */         internalSet(14, i4 % 1000);
/*      */       } else {
/* 1672 */         internalSet(11, 0);
/* 1673 */         internalSet(9, 0);
/* 1674 */         internalSet(10, 0);
/* 1675 */         internalSet(12, 0);
/* 1676 */         internalSet(13, 0);
/* 1677 */         internalSet(14, 0);
/*      */       } 
/* 1679 */       n |= 0x7E00;
/*      */     } 
/*      */ 
/*      */     
/* 1683 */     if ((paramInt1 & 0x18000) != 0) {
/* 1684 */       internalSet(15, this.zoneOffsets[0]);
/* 1685 */       internalSet(16, this.zoneOffsets[1]);
/* 1686 */       n |= 0x18000;
/*      */     } 
/*      */     
/* 1689 */     if ((paramInt1 & 0x158) != 0) {
/*      */       int i4; long l1;
/* 1691 */       int i3 = this.jdate.getNormalizedYear();
/*      */ 
/*      */       
/* 1694 */       boolean bool = isTransitionYear(this.jdate.getNormalizedYear());
/*      */ 
/*      */       
/* 1697 */       if (bool) {
/* 1698 */         l1 = getFixedDateJan1(this.jdate, l);
/* 1699 */         i4 = (int)(l - l1) + 1;
/* 1700 */       } else if (i3 == MIN_VALUES[1]) {
/* 1701 */         LocalGregorianCalendar.Date date = jcal.getCalendarDate(Long.MIN_VALUE, getZone());
/* 1702 */         l1 = jcal.getFixedDate(date);
/* 1703 */         i4 = (int)(l - l1) + 1;
/*      */       } else {
/* 1705 */         i4 = (int)jcal.getDayOfYear(this.jdate);
/* 1706 */         l1 = l - i4 + 1L;
/*      */       } 
/*      */       
/* 1709 */       long l2 = bool ? getFixedDateMonth1(this.jdate, l) : (l - i2 + 1L);
/*      */       
/* 1711 */       internalSet(6, i4);
/* 1712 */       internalSet(8, (i2 - 1) / 7 + 1);
/*      */       
/* 1714 */       int i5 = getWeekNumber(l1, l);
/*      */ 
/*      */ 
/*      */       
/* 1718 */       if (i5 == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1726 */         long l4, l3 = l1 - 1L;
/*      */         
/* 1728 */         LocalGregorianCalendar.Date date = getCalendarDate(l3);
/* 1729 */         if (!bool && !isTransitionYear(date.getNormalizedYear())) {
/* 1730 */           l4 = l1 - 365L;
/* 1731 */           if (date.isLeapYear()) {
/* 1732 */             l4--;
/*      */           }
/* 1734 */         } else if (bool) {
/* 1735 */           if (this.jdate.getYear() == 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1741 */             if (k > 5) {
/* 1742 */               CalendarDate calendarDate = eras[k - 1].getSinceDate();
/* 1743 */               if (i3 == calendarDate.getYear()) {
/* 1744 */                 date.setMonth(calendarDate.getMonth()).setDayOfMonth(calendarDate.getDayOfMonth());
/*      */               }
/*      */             } else {
/* 1747 */               date.setMonth(1).setDayOfMonth(1);
/*      */             } 
/* 1749 */             jcal.normalize(date);
/* 1750 */             l4 = jcal.getFixedDate(date);
/*      */           } else {
/* 1752 */             l4 = l1 - 365L;
/* 1753 */             if (date.isLeapYear()) {
/* 1754 */               l4--;
/*      */             }
/*      */           } 
/*      */         } else {
/* 1758 */           CalendarDate calendarDate = eras[getEraIndex(this.jdate)].getSinceDate();
/* 1759 */           date.setMonth(calendarDate.getMonth()).setDayOfMonth(calendarDate.getDayOfMonth());
/* 1760 */           jcal.normalize(date);
/* 1761 */           l4 = jcal.getFixedDate(date);
/*      */         } 
/* 1763 */         i5 = getWeekNumber(l4, l3);
/*      */       }
/* 1765 */       else if (!bool) {
/*      */         
/* 1767 */         if (i5 >= 52) {
/* 1768 */           long l3 = l1 + 365L;
/* 1769 */           if (this.jdate.isLeapYear()) {
/* 1770 */             l3++;
/*      */           }
/* 1772 */           long l4 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l3 + 6L, 
/* 1773 */               getFirstDayOfWeek());
/* 1774 */           int i6 = (int)(l4 - l3);
/* 1775 */           if (i6 >= getMinimalDaysInFirstWeek() && l >= l4 - 7L)
/*      */           {
/* 1777 */             i5 = 1; } 
/*      */         } 
/*      */       } else {
/*      */         long l3;
/* 1781 */         LocalGregorianCalendar.Date date = (LocalGregorianCalendar.Date)this.jdate.clone();
/*      */         
/* 1783 */         if (this.jdate.getYear() == 1) {
/* 1784 */           date.addYear(1);
/* 1785 */           date.setMonth(1).setDayOfMonth(1);
/* 1786 */           l3 = jcal.getFixedDate(date);
/*      */         } else {
/* 1788 */           int i7 = getEraIndex(date) + 1;
/* 1789 */           CalendarDate calendarDate = eras[i7].getSinceDate();
/* 1790 */           date.setEra(eras[i7]);
/* 1791 */           date.setDate(1, calendarDate.getMonth(), calendarDate.getDayOfMonth());
/* 1792 */           jcal.normalize(date);
/* 1793 */           l3 = jcal.getFixedDate(date);
/*      */         } 
/* 1795 */         long l4 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l3 + 6L, 
/* 1796 */             getFirstDayOfWeek());
/* 1797 */         int i6 = (int)(l4 - l3);
/* 1798 */         if (i6 >= getMinimalDaysInFirstWeek() && l >= l4 - 7L)
/*      */         {
/* 1800 */           i5 = 1;
/*      */         }
/*      */       } 
/*      */       
/* 1804 */       internalSet(3, i5);
/* 1805 */       internalSet(4, getWeekNumber(l2, l));
/* 1806 */       n |= 0x158;
/*      */     } 
/* 1808 */     return n; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getWeekNumber(long paramLong1, long paramLong2) {
/* 1823 */     long l = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(paramLong1 + 6L, 
/* 1824 */         getFirstDayOfWeek());
/* 1825 */     int i = (int)(l - paramLong1);
/* 1826 */     assert i <= 7;
/* 1827 */     if (i >= getMinimalDaysInFirstWeek()) {
/* 1828 */       l -= 7L;
/*      */     }
/* 1830 */     int j = (int)(paramLong2 - l);
/* 1831 */     if (j >= 0) {
/* 1832 */       return j / 7 + 1;
/*      */     }
/* 1834 */     return CalendarUtils.floorDivide(j, 7) + 1;
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
/*      */   protected void computeTime() {
/*      */     byte b1, b2;
/* 1848 */     if (!isLenient()) {
/* 1849 */       if (this.originalFields == null) {
/* 1850 */         this.originalFields = new int[17];
/*      */       }
/* 1852 */       for (byte b = 0; b < 17; b++) {
/* 1853 */         b1 = internalGet(b);
/* 1854 */         if (isExternallySet(b))
/*      */         {
/* 1856 */           if (b1 < getMinimum(b) || b1 > getMaximum(b)) {
/* 1857 */             throw new IllegalArgumentException(getFieldName(b));
/*      */           }
/*      */         }
/* 1860 */         this.originalFields[b] = b1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1866 */     int i = selectFields();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1871 */     if (isSet(0)) {
/* 1872 */       b2 = internalGet(0);
/* 1873 */       b1 = isSet(1) ? internalGet(1) : 1;
/*      */     }
/* 1875 */     else if (isSet(1)) {
/* 1876 */       b2 = currentEra;
/* 1877 */       b1 = internalGet(1);
/*      */     } else {
/*      */       
/* 1880 */       b2 = 3;
/* 1881 */       b1 = 45;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1887 */     long l1 = 0L;
/* 1888 */     if (isFieldSet(i, 11)) {
/* 1889 */       l1 += internalGet(11);
/*      */     } else {
/* 1891 */       l1 += internalGet(10);
/*      */       
/* 1893 */       if (isFieldSet(i, 9)) {
/* 1894 */         l1 += (12 * internalGet(9));
/*      */       }
/*      */     } 
/* 1897 */     l1 *= 60L;
/* 1898 */     l1 += internalGet(12);
/* 1899 */     l1 *= 60L;
/* 1900 */     l1 += internalGet(13);
/* 1901 */     l1 *= 1000L;
/* 1902 */     l1 += internalGet(14);
/*      */ 
/*      */ 
/*      */     
/* 1906 */     long l2 = l1 / 86400000L;
/* 1907 */     l1 %= 86400000L;
/* 1908 */     while (l1 < 0L) {
/* 1909 */       l1 += 86400000L;
/* 1910 */       l2--;
/*      */     } 
/*      */ 
/*      */     
/* 1914 */     l2 += getFixedDate(b2, b1, i);
/*      */ 
/*      */     
/* 1917 */     long l3 = (l2 - 719163L) * 86400000L + l1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1932 */     TimeZone timeZone = getZone();
/* 1933 */     if (this.zoneOffsets == null) {
/* 1934 */       this.zoneOffsets = new int[2];
/*      */     }
/* 1936 */     int j = i & 0x18000;
/* 1937 */     if (j != 98304) {
/* 1938 */       if (timeZone instanceof ZoneInfo) {
/* 1939 */         ((ZoneInfo)timeZone).getOffsetsByWall(l3, this.zoneOffsets);
/*      */       } else {
/* 1941 */         timeZone.getOffsets(l3 - timeZone.getRawOffset(), this.zoneOffsets);
/*      */       } 
/*      */     }
/* 1944 */     if (j != 0) {
/* 1945 */       if (isFieldSet(j, 15)) {
/* 1946 */         this.zoneOffsets[0] = internalGet(15);
/*      */       }
/* 1948 */       if (isFieldSet(j, 16)) {
/* 1949 */         this.zoneOffsets[1] = internalGet(16);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1954 */     l3 -= (this.zoneOffsets[0] + this.zoneOffsets[1]);
/*      */ 
/*      */     
/* 1957 */     this.time = l3;
/*      */     
/* 1959 */     int k = computeFields(i | getSetStateFields(), j);
/*      */     
/* 1961 */     if (!isLenient()) {
/* 1962 */       for (byte b = 0; b < 17; b++) {
/* 1963 */         if (isExternallySet(b))
/*      */         {
/*      */           
/* 1966 */           if (this.originalFields[b] != internalGet(b)) {
/* 1967 */             int m = internalGet(b);
/*      */             
/* 1969 */             System.arraycopy(this.originalFields, 0, this.fields, 0, this.fields.length);
/* 1970 */             throw new IllegalArgumentException(getFieldName(b) + "=" + m + ", expected " + this.originalFields[b]);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/* 1975 */     setFieldsNormalized(k);
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
/*      */   private long getFixedDate(int paramInt1, int paramInt2, int paramInt3) {
/* 1990 */     int i = 0;
/* 1991 */     int j = 1;
/* 1992 */     if (isFieldSet(paramInt3, 2)) {
/*      */ 
/*      */       
/* 1995 */       i = internalGet(2);
/*      */ 
/*      */       
/* 1998 */       if (i > 11) {
/* 1999 */         paramInt2 += i / 12;
/* 2000 */         i %= 12;
/* 2001 */       } else if (i < 0) {
/* 2002 */         int[] arrayOfInt = new int[1];
/* 2003 */         paramInt2 += CalendarUtils.floorDivide(i, 12, arrayOfInt);
/* 2004 */         i = arrayOfInt[0];
/*      */       }
/*      */     
/* 2007 */     } else if (paramInt2 == 1 && paramInt1 != 0) {
/* 2008 */       CalendarDate calendarDate = eras[paramInt1].getSinceDate();
/* 2009 */       i = calendarDate.getMonth() - 1;
/* 2010 */       j = calendarDate.getDayOfMonth();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2015 */     if (paramInt2 == MIN_VALUES[1]) {
/* 2016 */       LocalGregorianCalendar.Date date1 = jcal.getCalendarDate(Long.MIN_VALUE, getZone());
/* 2017 */       int k = date1.getMonth() - 1;
/* 2018 */       if (i < k) {
/* 2019 */         i = k;
/*      */       }
/* 2021 */       if (i == k) {
/* 2022 */         j = date1.getDayOfMonth();
/*      */       }
/*      */     } 
/*      */     
/* 2026 */     LocalGregorianCalendar.Date date = jcal.newCalendarDate(TimeZone.NO_TIMEZONE);
/* 2027 */     date.setEra((paramInt1 > 0) ? eras[paramInt1] : null);
/* 2028 */     date.setDate(paramInt2, i + 1, j);
/* 2029 */     jcal.normalize(date);
/*      */ 
/*      */ 
/*      */     
/* 2033 */     long l = jcal.getFixedDate(date);
/*      */     
/* 2035 */     if (isFieldSet(paramInt3, 2)) {
/*      */       
/* 2037 */       if (isFieldSet(paramInt3, 5)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2044 */         if (isSet(5))
/*      */         {
/*      */           
/* 2047 */           l += internalGet(5);
/* 2048 */           l -= j;
/*      */         }
/*      */       
/* 2051 */       } else if (isFieldSet(paramInt3, 4)) {
/* 2052 */         long l1 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l + 6L, 
/* 2053 */             getFirstDayOfWeek());
/*      */ 
/*      */         
/* 2056 */         if (l1 - l >= getMinimalDaysInFirstWeek()) {
/* 2057 */           l1 -= 7L;
/*      */         }
/* 2059 */         if (isFieldSet(paramInt3, 7)) {
/* 2060 */           l1 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l1 + 6L, 
/* 2061 */               internalGet(7));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2066 */         l = l1 + (7 * (internalGet(4) - 1));
/*      */       } else {
/*      */         int k; byte b;
/* 2069 */         if (isFieldSet(paramInt3, 7)) {
/* 2070 */           k = internalGet(7);
/*      */         } else {
/* 2072 */           k = getFirstDayOfWeek();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2078 */         if (isFieldSet(paramInt3, 8)) {
/* 2079 */           b = internalGet(8);
/*      */         } else {
/* 2081 */           b = 1;
/*      */         } 
/* 2083 */         if (b) {
/* 2084 */           l = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l + (7 * b) - 1L, k);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 2089 */           int m = monthLength(i, paramInt2) + 7 * (b + 1);
/*      */           
/* 2091 */           l = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l + m - 1L, k);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 2098 */     else if (isFieldSet(paramInt3, 6)) {
/* 2099 */       if (isTransitionYear(date.getNormalizedYear())) {
/* 2100 */         l = getFixedDateJan1(date, l);
/*      */       }
/*      */       
/* 2103 */       l += internalGet(6);
/* 2104 */       l--;
/*      */     } else {
/* 2106 */       long l1 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l + 6L, 
/* 2107 */           getFirstDayOfWeek());
/*      */ 
/*      */       
/* 2110 */       if (l1 - l >= getMinimalDaysInFirstWeek()) {
/* 2111 */         l1 -= 7L;
/*      */       }
/* 2113 */       if (isFieldSet(paramInt3, 7)) {
/* 2114 */         int k = internalGet(7);
/* 2115 */         if (k != getFirstDayOfWeek()) {
/* 2116 */           l1 = LocalGregorianCalendar.getDayOfWeekDateOnOrBefore(l1 + 6L, k);
/*      */         }
/*      */       } 
/*      */       
/* 2120 */       l = l1 + 7L * (internalGet(3) - 1L);
/*      */     } 
/*      */     
/* 2123 */     return l;
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
/*      */   private long getFixedDateJan1(LocalGregorianCalendar.Date paramDate, long paramLong) {
/* 2135 */     Era era = paramDate.getEra();
/* 2136 */     if (paramDate.getEra() != null && paramDate.getYear() == 1) {
/* 2137 */       for (int i = getEraIndex(paramDate); i > 0; ) {
/* 2138 */         CalendarDate calendarDate = eras[i].getSinceDate();
/* 2139 */         long l = gcal.getFixedDate(calendarDate);
/*      */         
/* 2141 */         if (l > paramLong) {
/*      */           i--; continue;
/*      */         } 
/* 2144 */         return l;
/*      */       } 
/*      */     }
/* 2147 */     Gregorian.Date date = gcal.newCalendarDate(TimeZone.NO_TIMEZONE);
/* 2148 */     date.setDate(paramDate.getNormalizedYear(), 1, 1);
/* 2149 */     return gcal.getFixedDate(date);
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
/*      */   private long getFixedDateMonth1(LocalGregorianCalendar.Date paramDate, long paramLong) {
/* 2162 */     int i = getTransitionEraIndex(paramDate);
/* 2163 */     if (i != -1) {
/* 2164 */       long l = sinceFixedDates[i];
/*      */ 
/*      */       
/* 2167 */       if (l <= paramLong) {
/* 2168 */         return l;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2173 */     return paramLong - paramDate.getDayOfMonth() + 1L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static LocalGregorianCalendar.Date getCalendarDate(long paramLong) {
/* 2182 */     LocalGregorianCalendar.Date date = jcal.newCalendarDate(TimeZone.NO_TIMEZONE);
/* 2183 */     jcal.getCalendarDateFromFixedDate(date, paramLong);
/* 2184 */     return date;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int monthLength(int paramInt1, int paramInt2) {
/* 2194 */     return CalendarUtils.isGregorianLeapYear(paramInt2) ? GregorianCalendar.LEAP_MONTH_LENGTH[paramInt1] : GregorianCalendar.MONTH_LENGTH[paramInt1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int monthLength(int paramInt) {
/* 2205 */     assert this.jdate.isNormalized();
/* 2206 */     return this.jdate.isLeapYear() ? GregorianCalendar.LEAP_MONTH_LENGTH[paramInt] : GregorianCalendar.MONTH_LENGTH[paramInt];
/*      */   }
/*      */ 
/*      */   
/*      */   private int actualMonthLength() {
/* 2211 */     int i = jcal.getMonthLength(this.jdate);
/* 2212 */     int j = getTransitionEraIndex(this.jdate);
/* 2213 */     if (j == -1) {
/* 2214 */       long l = sinceFixedDates[j];
/* 2215 */       CalendarDate calendarDate = eras[j].getSinceDate();
/* 2216 */       if (l <= this.cachedFixedDate) {
/* 2217 */         i -= calendarDate.getDayOfMonth() - 1;
/*      */       } else {
/* 2219 */         i = calendarDate.getDayOfMonth() - 1;
/*      */       } 
/*      */     } 
/* 2222 */     return i;
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
/*      */   private static int getTransitionEraIndex(LocalGregorianCalendar.Date paramDate) {
/* 2234 */     int i = getEraIndex(paramDate);
/* 2235 */     CalendarDate calendarDate = eras[i].getSinceDate();
/* 2236 */     if (calendarDate.getYear() == paramDate.getNormalizedYear() && calendarDate
/* 2237 */       .getMonth() == paramDate.getMonth()) {
/* 2238 */       return i;
/*      */     }
/* 2240 */     if (i < eras.length - 1) {
/* 2241 */       calendarDate = eras[++i].getSinceDate();
/* 2242 */       if (calendarDate.getYear() == paramDate.getNormalizedYear() && calendarDate
/* 2243 */         .getMonth() == paramDate.getMonth()) {
/* 2244 */         return i;
/*      */       }
/*      */     } 
/* 2247 */     return -1;
/*      */   }
/*      */   
/*      */   private boolean isTransitionYear(int paramInt) {
/* 2251 */     for (int i = eras.length - 1; i > 0; i--) {
/* 2252 */       int j = eras[i].getSinceDate().getYear();
/* 2253 */       if (paramInt == j) {
/* 2254 */         return true;
/*      */       }
/* 2256 */       if (paramInt > j) {
/*      */         break;
/*      */       }
/*      */     } 
/* 2260 */     return false;
/*      */   }
/*      */   
/*      */   private static int getEraIndex(LocalGregorianCalendar.Date paramDate) {
/* 2264 */     Era era = paramDate.getEra();
/* 2265 */     for (int i = eras.length - 1; i > 0; i--) {
/* 2266 */       if (eras[i] == era) {
/* 2267 */         return i;
/*      */       }
/*      */     } 
/* 2270 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JapaneseImperialCalendar getNormalizedCalendar() {
/*      */     JapaneseImperialCalendar japaneseImperialCalendar;
/* 2280 */     if (isFullyNormalized()) {
/* 2281 */       japaneseImperialCalendar = this;
/*      */     } else {
/*      */       
/* 2284 */       japaneseImperialCalendar = (JapaneseImperialCalendar)clone();
/* 2285 */       japaneseImperialCalendar.setLenient(true);
/* 2286 */       japaneseImperialCalendar.complete();
/*      */     } 
/* 2288 */     return japaneseImperialCalendar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void pinDayOfMonth(LocalGregorianCalendar.Date paramDate) {
/* 2298 */     int i = paramDate.getYear();
/* 2299 */     int j = paramDate.getDayOfMonth();
/* 2300 */     if (i != getMinimum(1)) {
/* 2301 */       paramDate.setDayOfMonth(1);
/* 2302 */       jcal.normalize(paramDate);
/* 2303 */       int k = jcal.getMonthLength(paramDate);
/* 2304 */       if (j > k) {
/* 2305 */         paramDate.setDayOfMonth(k);
/*      */       } else {
/* 2307 */         paramDate.setDayOfMonth(j);
/*      */       } 
/* 2309 */       jcal.normalize(paramDate);
/*      */     } else {
/* 2311 */       LocalGregorianCalendar.Date date1 = jcal.getCalendarDate(Long.MIN_VALUE, getZone());
/* 2312 */       LocalGregorianCalendar.Date date2 = jcal.getCalendarDate(this.time, getZone());
/* 2313 */       long l = date2.getTimeOfDay();
/*      */       
/* 2315 */       date2.addYear(400);
/* 2316 */       date2.setMonth(paramDate.getMonth());
/* 2317 */       date2.setDayOfMonth(1);
/* 2318 */       jcal.normalize(date2);
/* 2319 */       int k = jcal.getMonthLength(date2);
/* 2320 */       if (j > k) {
/* 2321 */         date2.setDayOfMonth(k);
/*      */       }
/* 2323 */       else if (j < date1.getDayOfMonth()) {
/* 2324 */         date2.setDayOfMonth(date1.getDayOfMonth());
/*      */       } else {
/* 2326 */         date2.setDayOfMonth(j);
/*      */       } 
/*      */       
/* 2329 */       if (date2.getDayOfMonth() == date1.getDayOfMonth() && l < date1.getTimeOfDay()) {
/* 2330 */         date2.setDayOfMonth(Math.min(j + 1, k));
/*      */       }
/*      */       
/* 2333 */       paramDate.setDate(i, date2.getMonth(), date2.getDayOfMonth());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getRolledValue(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 2342 */     assert paramInt1 >= paramInt3 && paramInt1 <= paramInt4;
/* 2343 */     int i = paramInt4 - paramInt3 + 1;
/* 2344 */     paramInt2 %= i;
/* 2345 */     int j = paramInt1 + paramInt2;
/* 2346 */     if (j > paramInt4) {
/* 2347 */       j -= i;
/* 2348 */     } else if (j < paramInt3) {
/* 2349 */       j += i;
/*      */     } 
/* 2351 */     assert j >= paramInt3 && j <= paramInt4;
/* 2352 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int internalGetEra() {
/* 2360 */     return isSet(0) ? internalGet(0) : currentEra;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 2368 */     paramObjectInputStream.defaultReadObject();
/* 2369 */     if (this.jdate == null) {
/* 2370 */       this.jdate = jcal.newCalendarDate(getZone());
/* 2371 */       this.cachedFixedDate = Long.MIN_VALUE;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/JapaneseImperialCalendar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */