/*      */ package java.text;
/*      */ 
/*      */ import java.io.InvalidObjectException;
/*      */ import java.text.spi.DateFormatProvider;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.TimeZone;
/*      */ import sun.util.locale.provider.LocaleProviderAdapter;
/*      */ import sun.util.locale.provider.LocaleServiceProviderPool;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class DateFormat
/*      */   extends Format
/*      */ {
/*      */   protected Calendar calendar;
/*      */   protected NumberFormat numberFormat;
/*      */   public static final int ERA_FIELD = 0;
/*      */   public static final int YEAR_FIELD = 1;
/*      */   public static final int MONTH_FIELD = 2;
/*      */   public static final int DATE_FIELD = 3;
/*      */   public static final int HOUR_OF_DAY1_FIELD = 4;
/*      */   public static final int HOUR_OF_DAY0_FIELD = 5;
/*      */   public static final int MINUTE_FIELD = 6;
/*      */   public static final int SECOND_FIELD = 7;
/*      */   public static final int MILLISECOND_FIELD = 8;
/*      */   public static final int DAY_OF_WEEK_FIELD = 9;
/*      */   public static final int DAY_OF_YEAR_FIELD = 10;
/*      */   public static final int DAY_OF_WEEK_IN_MONTH_FIELD = 11;
/*      */   public static final int WEEK_OF_YEAR_FIELD = 12;
/*      */   public static final int WEEK_OF_MONTH_FIELD = 13;
/*      */   public static final int AM_PM_FIELD = 14;
/*      */   public static final int HOUR1_FIELD = 15;
/*      */   public static final int HOUR0_FIELD = 16;
/*      */   public static final int TIMEZONE_FIELD = 17;
/*      */   private static final long serialVersionUID = 7218322306649953788L;
/*      */   public static final int FULL = 0;
/*      */   public static final int LONG = 1;
/*      */   public static final int MEDIUM = 2;
/*      */   public static final int SHORT = 3;
/*      */   public static final int DEFAULT = 2;
/*      */   
/*      */   public final StringBuffer format(Object paramObject, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition) {
/*  304 */     if (paramObject instanceof Date)
/*  305 */       return format((Date)paramObject, paramStringBuffer, paramFieldPosition); 
/*  306 */     if (paramObject instanceof Number) {
/*  307 */       return format(new Date(((Number)paramObject).longValue()), paramStringBuffer, paramFieldPosition);
/*      */     }
/*      */     
/*  310 */     throw new IllegalArgumentException("Cannot format given Object as a Date");
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
/*      */   public abstract StringBuffer format(Date paramDate, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String format(Date paramDate) {
/*  345 */     return format(paramDate, new StringBuffer(), DontCareFieldPosition.INSTANCE)
/*  346 */       .toString();
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
/*      */   public Date parse(String paramString) throws ParseException {
/*  363 */     ParsePosition parsePosition = new ParsePosition(0);
/*  364 */     Date date = parse(paramString, parsePosition);
/*  365 */     if (parsePosition.index == 0) {
/*  366 */       throw new ParseException("Unparseable date: \"" + paramString + "\"", parsePosition.errorIndex);
/*      */     }
/*  368 */     return date;
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
/*      */   public abstract Date parse(String paramString, ParsePosition paramParsePosition);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object parseObject(String paramString, ParsePosition paramParsePosition) {
/*  424 */     return parse(paramString, paramParsePosition);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final DateFormat getTimeInstance() {
/*  460 */     return get(2, 0, 1, Locale.getDefault(Locale.Category.FORMAT));
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
/*      */   public static final DateFormat getTimeInstance(int paramInt) {
/*  477 */     return get(paramInt, 0, 1, Locale.getDefault(Locale.Category.FORMAT));
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
/*      */   public static final DateFormat getTimeInstance(int paramInt, Locale paramLocale) {
/*  491 */     return get(paramInt, 0, 1, paramLocale);
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
/*      */   public static final DateFormat getDateInstance() {
/*  506 */     return get(0, 2, 2, Locale.getDefault(Locale.Category.FORMAT));
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
/*      */   public static final DateFormat getDateInstance(int paramInt) {
/*  523 */     return get(0, paramInt, 2, Locale.getDefault(Locale.Category.FORMAT));
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
/*      */   public static final DateFormat getDateInstance(int paramInt, Locale paramLocale) {
/*  537 */     return get(0, paramInt, 2, paramLocale);
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
/*      */   public static final DateFormat getDateTimeInstance() {
/*  552 */     return get(2, 2, 3, Locale.getDefault(Locale.Category.FORMAT));
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
/*      */   public static final DateFormat getDateTimeInstance(int paramInt1, int paramInt2) {
/*  572 */     return get(paramInt2, paramInt1, 3, Locale.getDefault(Locale.Category.FORMAT));
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
/*      */   public static final DateFormat getDateTimeInstance(int paramInt1, int paramInt2, Locale paramLocale) {
/*  586 */     return get(paramInt2, paramInt1, 3, paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final DateFormat getInstance() {
/*  596 */     return getDateTimeInstance(3, 3);
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
/*      */   public static Locale[] getAvailableLocales() {
/*  615 */     LocaleServiceProviderPool localeServiceProviderPool = LocaleServiceProviderPool.getPool((Class)DateFormatProvider.class);
/*  616 */     return localeServiceProviderPool.getAvailableLocales();
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
/*      */   public void setCalendar(Calendar paramCalendar) {
/*  631 */     this.calendar = paramCalendar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Calendar getCalendar() {
/*  641 */     return this.calendar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNumberFormat(NumberFormat paramNumberFormat) {
/*  650 */     this.numberFormat = paramNumberFormat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NumberFormat getNumberFormat() {
/*  660 */     return this.numberFormat;
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
/*      */   public void setTimeZone(TimeZone paramTimeZone) {
/*  680 */     this.calendar.setTimeZone(paramTimeZone);
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
/*      */   public TimeZone getTimeZone() {
/*  694 */     return this.calendar.getTimeZone();
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
/*      */   public void setLenient(boolean paramBoolean) {
/*  716 */     this.calendar.setLenient(paramBoolean);
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
/*      */   public boolean isLenient() {
/*  732 */     return this.calendar.isLenient();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  739 */     return this.numberFormat.hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*  747 */     if (this == paramObject) return true; 
/*  748 */     if (paramObject == null || getClass() != paramObject.getClass()) return false; 
/*  749 */     DateFormat dateFormat = (DateFormat)paramObject;
/*  750 */     return (this.calendar
/*  751 */       .getFirstDayOfWeek() == dateFormat.calendar.getFirstDayOfWeek() && this.calendar
/*  752 */       .getMinimalDaysInFirstWeek() == dateFormat.calendar.getMinimalDaysInFirstWeek() && this.calendar
/*  753 */       .isLenient() == dateFormat.calendar.isLenient() && this.calendar
/*  754 */       .getTimeZone().equals(dateFormat.calendar.getTimeZone()) && this.numberFormat
/*  755 */       .equals(dateFormat.numberFormat));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*  763 */     DateFormat dateFormat = (DateFormat)super.clone();
/*  764 */     dateFormat.calendar = (Calendar)this.calendar.clone();
/*  765 */     dateFormat.numberFormat = (NumberFormat)this.numberFormat.clone();
/*  766 */     return dateFormat;
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
/*      */   private static DateFormat get(int paramInt1, int paramInt2, int paramInt3, Locale paramLocale) {
/*  782 */     if ((paramInt3 & 0x1) != 0) {
/*  783 */       if (paramInt1 < 0 || paramInt1 > 3) {
/*  784 */         throw new IllegalArgumentException("Illegal time style " + paramInt1);
/*      */       }
/*      */     } else {
/*  787 */       paramInt1 = -1;
/*      */     } 
/*  789 */     if ((paramInt3 & 0x2) != 0) {
/*  790 */       if (paramInt2 < 0 || paramInt2 > 3) {
/*  791 */         throw new IllegalArgumentException("Illegal date style " + paramInt2);
/*      */       }
/*      */     } else {
/*  794 */       paramInt2 = -1;
/*      */     } 
/*      */     
/*  797 */     LocaleProviderAdapter localeProviderAdapter = LocaleProviderAdapter.getAdapter((Class)DateFormatProvider.class, paramLocale);
/*  798 */     DateFormat dateFormat = get(localeProviderAdapter, paramInt1, paramInt2, paramLocale);
/*  799 */     if (dateFormat == null) {
/*  800 */       dateFormat = get(LocaleProviderAdapter.forJRE(), paramInt1, paramInt2, paramLocale);
/*      */     }
/*  802 */     return dateFormat;
/*      */   }
/*      */   private static DateFormat get(LocaleProviderAdapter paramLocaleProviderAdapter, int paramInt1, int paramInt2, Locale paramLocale) {
/*      */     DateFormat dateFormat;
/*  806 */     DateFormatProvider dateFormatProvider = paramLocaleProviderAdapter.getDateFormatProvider();
/*      */     
/*  808 */     if (paramInt1 == -1) {
/*  809 */       dateFormat = dateFormatProvider.getDateInstance(paramInt2, paramLocale);
/*      */     }
/*  811 */     else if (paramInt2 == -1) {
/*  812 */       dateFormat = dateFormatProvider.getTimeInstance(paramInt1, paramLocale);
/*      */     } else {
/*  814 */       dateFormat = dateFormatProvider.getDateTimeInstance(paramInt2, paramInt1, paramLocale);
/*      */     } 
/*      */     
/*  817 */     return dateFormat;
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
/*      */   public static class Field
/*      */     extends Format.Field
/*      */   {
/*      */     private static final long serialVersionUID = 7441350119349544720L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  843 */     private static final Map<String, Field> instanceMap = new HashMap<>(18);
/*      */ 
/*      */     
/*  846 */     private static final Field[] calendarToFieldMapping = new Field[17];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int calendarField;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static Field ofCalendarField(int param1Int) {
/*  865 */       if (param1Int < 0 || param1Int >= calendarToFieldMapping.length)
/*      */       {
/*  867 */         throw new IllegalArgumentException("Unknown Calendar constant " + param1Int);
/*      */       }
/*      */       
/*  870 */       return calendarToFieldMapping[param1Int];
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
/*      */     protected Field(String param1String, int param1Int) {
/*  884 */       super(param1String);
/*  885 */       this.calendarField = param1Int;
/*  886 */       if (getClass() == Field.class) {
/*  887 */         instanceMap.put(param1String, this);
/*  888 */         if (param1Int >= 0)
/*      */         {
/*  890 */           calendarToFieldMapping[param1Int] = this;
/*      */         }
/*      */       } 
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
/*      */     public int getCalendarField() {
/*  906 */       return this.calendarField;
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
/*      */     protected Object readResolve() throws InvalidObjectException {
/*  918 */       if (getClass() != Field.class) {
/*  919 */         throw new InvalidObjectException("subclass didn't correctly implement readResolve");
/*      */       }
/*      */       
/*  922 */       Object object = instanceMap.get(getName());
/*  923 */       if (object != null) {
/*  924 */         return object;
/*      */       }
/*  926 */       throw new InvalidObjectException("unknown attribute name");
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
/*  937 */     public static final Field ERA = new Field("era", 0);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  942 */     public static final Field YEAR = new Field("year", 1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  947 */     public static final Field MONTH = new Field("month", 2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  952 */     public static final Field DAY_OF_MONTH = new Field("day of month", 5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  959 */     public static final Field HOUR_OF_DAY1 = new Field("hour of day 1", -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  965 */     public static final Field HOUR_OF_DAY0 = new Field("hour of day", 11);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  971 */     public static final Field MINUTE = new Field("minute", 12);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  976 */     public static final Field SECOND = new Field("second", 13);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  981 */     public static final Field MILLISECOND = new Field("millisecond", 14);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  987 */     public static final Field DAY_OF_WEEK = new Field("day of week", 7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  993 */     public static final Field DAY_OF_YEAR = new Field("day of year", 6);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  999 */     public static final Field DAY_OF_WEEK_IN_MONTH = new Field("day of week in month", 8);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1006 */     public static final Field WEEK_OF_YEAR = new Field("week of year", 3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1012 */     public static final Field WEEK_OF_MONTH = new Field("week of month", 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1019 */     public static final Field AM_PM = new Field("am pm", 9);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1026 */     public static final Field HOUR1 = new Field("hour 1", -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1032 */     public static final Field HOUR0 = new Field("hour", 10);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1038 */     public static final Field TIME_ZONE = new Field("time zone", -1);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/DateFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */