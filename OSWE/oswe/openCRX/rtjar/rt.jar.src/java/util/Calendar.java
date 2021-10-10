/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PermissionCollection;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.text.DateFormat;
/*      */ import java.text.DateFormatSymbols;
/*      */ import java.time.Instant;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import sun.util.BuddhistCalendar;
/*      */ import sun.util.calendar.ZoneInfo;
/*      */ import sun.util.locale.provider.CalendarDataUtility;
/*      */ import sun.util.locale.provider.LocaleProviderAdapter;
/*      */ import sun.util.spi.CalendarProvider;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Calendar
/*      */   implements Serializable, Cloneable, Comparable<Calendar>
/*      */ {
/*      */   public static final int ERA = 0;
/*      */   public static final int YEAR = 1;
/*      */   public static final int MONTH = 2;
/*      */   public static final int WEEK_OF_YEAR = 3;
/*      */   public static final int WEEK_OF_MONTH = 4;
/*      */   public static final int DATE = 5;
/*      */   public static final int DAY_OF_MONTH = 5;
/*      */   public static final int DAY_OF_YEAR = 6;
/*      */   public static final int DAY_OF_WEEK = 7;
/*      */   public static final int DAY_OF_WEEK_IN_MONTH = 8;
/*      */   public static final int AM_PM = 9;
/*      */   public static final int HOUR = 10;
/*      */   public static final int HOUR_OF_DAY = 11;
/*      */   public static final int MINUTE = 12;
/*      */   public static final int SECOND = 13;
/*      */   public static final int MILLISECOND = 14;
/*      */   public static final int ZONE_OFFSET = 15;
/*      */   public static final int DST_OFFSET = 16;
/*      */   public static final int FIELD_COUNT = 17;
/*      */   public static final int SUNDAY = 1;
/*      */   public static final int MONDAY = 2;
/*      */   public static final int TUESDAY = 3;
/*      */   public static final int WEDNESDAY = 4;
/*      */   public static final int THURSDAY = 5;
/*      */   public static final int FRIDAY = 6;
/*      */   public static final int SATURDAY = 7;
/*      */   public static final int JANUARY = 0;
/*      */   public static final int FEBRUARY = 1;
/*      */   public static final int MARCH = 2;
/*      */   public static final int APRIL = 3;
/*      */   public static final int MAY = 4;
/*      */   public static final int JUNE = 5;
/*      */   public static final int JULY = 6;
/*      */   public static final int AUGUST = 7;
/*      */   public static final int SEPTEMBER = 8;
/*      */   public static final int OCTOBER = 9;
/*      */   public static final int NOVEMBER = 10;
/*      */   public static final int DECEMBER = 11;
/*      */   public static final int UNDECIMBER = 12;
/*      */   public static final int AM = 0;
/*      */   public static final int PM = 1;
/*      */   public static final int ALL_STYLES = 0;
/*      */   static final int STANDALONE_MASK = 32768;
/*      */   public static final int SHORT = 1;
/*      */   public static final int LONG = 2;
/*      */   public static final int NARROW_FORMAT = 4;
/*      */   public static final int NARROW_STANDALONE = 32772;
/*      */   public static final int SHORT_FORMAT = 1;
/*      */   public static final int LONG_FORMAT = 2;
/*      */   public static final int SHORT_STANDALONE = 32769;
/*      */   public static final int LONG_STANDALONE = 32770;
/*      */   protected int[] fields;
/*      */   protected boolean[] isSet;
/*      */   private transient int[] stamp;
/*      */   protected long time;
/*      */   protected boolean isTimeSet;
/*      */   protected boolean areFieldsSet;
/*      */   transient boolean areAllFieldsSet;
/*      */   private boolean lenient = true;
/*      */   private TimeZone zone;
/*      */   private transient boolean sharedZone = false;
/*      */   private int firstDayOfWeek;
/*      */   private int minimalDaysInFirstWeek;
/*  934 */   private static final ConcurrentMap<Locale, int[]> cachedLocaleData = (ConcurrentMap)new ConcurrentHashMap<>(3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int UNSET = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int COMPUTED = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MINIMUM_USER_STAMP = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int ALL_FIELDS = 131071;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  967 */   private int nextStamp = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int currentSerialVersion = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  999 */   private int serialVersionOnStream = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   static final long serialVersionUID = -1807547505821590642L;
/*      */ 
/*      */ 
/*      */   
/*      */   static final int ERA_MASK = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   static final int YEAR_MASK = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   static final int MONTH_MASK = 4;
/*      */ 
/*      */ 
/*      */   
/*      */   static final int WEEK_OF_YEAR_MASK = 8;
/*      */ 
/*      */ 
/*      */   
/*      */   static final int WEEK_OF_MONTH_MASK = 16;
/*      */ 
/*      */ 
/*      */   
/*      */   static final int DAY_OF_MONTH_MASK = 32;
/*      */ 
/*      */ 
/*      */   
/*      */   static final int DATE_MASK = 32;
/*      */ 
/*      */ 
/*      */   
/*      */   static final int DAY_OF_YEAR_MASK = 64;
/*      */ 
/*      */ 
/*      */   
/*      */   static final int DAY_OF_WEEK_MASK = 128;
/*      */ 
/*      */ 
/*      */   
/*      */   static final int DAY_OF_WEEK_IN_MONTH_MASK = 256;
/*      */ 
/*      */ 
/*      */   
/*      */   static final int AM_PM_MASK = 512;
/*      */ 
/*      */ 
/*      */   
/*      */   static final int HOUR_MASK = 1024;
/*      */ 
/*      */   
/*      */   static final int HOUR_OF_DAY_MASK = 2048;
/*      */ 
/*      */   
/*      */   static final int MINUTE_MASK = 4096;
/*      */ 
/*      */   
/*      */   static final int SECOND_MASK = 8192;
/*      */ 
/*      */   
/*      */   static final int MILLISECOND_MASK = 16384;
/*      */ 
/*      */   
/*      */   static final int ZONE_OFFSET_MASK = 32768;
/*      */ 
/*      */   
/*      */   static final int DST_OFFSET_MASK = 65536;
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Builder
/*      */   {
/*      */     private static final int NFIELDS = 18;
/*      */ 
/*      */     
/*      */     private static final int WEEK_YEAR = 17;
/*      */ 
/*      */     
/*      */     private long instant;
/*      */ 
/*      */     
/*      */     private int[] fields;
/*      */ 
/*      */     
/*      */     private int nextStamp;
/*      */ 
/*      */     
/*      */     private int maxFieldIndex;
/*      */ 
/*      */     
/*      */     private String type;
/*      */ 
/*      */     
/*      */     private TimeZone zone;
/*      */ 
/*      */     
/*      */     private boolean lenient = true;
/*      */ 
/*      */     
/*      */     private Locale locale;
/*      */ 
/*      */     
/*      */     private int firstDayOfWeek;
/*      */ 
/*      */     
/*      */     private int minimalDaysInFirstWeek;
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setInstant(long param1Long) {
/* 1113 */       if (this.fields != null) {
/* 1114 */         throw new IllegalStateException();
/*      */       }
/* 1116 */       this.instant = param1Long;
/* 1117 */       this.nextStamp = 1;
/* 1118 */       return this;
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setInstant(Date param1Date) {
/* 1137 */       return setInstant(param1Date.getTime());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder set(int param1Int1, int param1Int2) {
/* 1159 */       if (param1Int1 < 0 || param1Int1 >= 17) {
/* 1160 */         throw new IllegalArgumentException("field is invalid");
/*      */       }
/* 1162 */       if (isInstantSet()) {
/* 1163 */         throw new IllegalStateException("instant has been set");
/*      */       }
/* 1165 */       allocateFields();
/* 1166 */       internalSet(param1Int1, param1Int2);
/* 1167 */       return this;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setFields(int... param1VarArgs) {
/* 1195 */       int i = param1VarArgs.length;
/* 1196 */       if (i % 2 != 0) {
/* 1197 */         throw new IllegalArgumentException();
/*      */       }
/* 1199 */       if (isInstantSet()) {
/* 1200 */         throw new IllegalStateException("instant has been set");
/*      */       }
/* 1202 */       if (this.nextStamp + i / 2 < 0) {
/* 1203 */         throw new IllegalStateException("stamp counter overflow");
/*      */       }
/* 1205 */       allocateFields();
/* 1206 */       for (byte b = 0; b < i; ) {
/* 1207 */         int j = param1VarArgs[b++];
/*      */         
/* 1209 */         if (j < 0 || j >= 17) {
/* 1210 */           throw new IllegalArgumentException("field is invalid");
/*      */         }
/* 1212 */         internalSet(j, param1VarArgs[b++]);
/*      */       } 
/* 1214 */       return this;
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setDate(int param1Int1, int param1Int2, int param1Int3) {
/* 1233 */       return setFields(new int[] { 1, param1Int1, 2, param1Int2, 5, param1Int3 });
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
/*      */ 
/*      */     
/*      */     public Builder setTimeOfDay(int param1Int1, int param1Int2, int param1Int3) {
/* 1250 */       return setTimeOfDay(param1Int1, param1Int2, param1Int3, 0);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setTimeOfDay(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1271 */       return setFields(new int[] { 11, param1Int1, 12, param1Int2, 13, param1Int3, 14, param1Int4 });
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setWeekDate(int param1Int1, int param1Int2, int param1Int3) {
/* 1292 */       allocateFields();
/* 1293 */       internalSet(17, param1Int1);
/* 1294 */       internalSet(3, param1Int2);
/* 1295 */       internalSet(7, param1Int3);
/* 1296 */       return this;
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
/*      */     
/*      */     public Builder setTimeZone(TimeZone param1TimeZone) {
/* 1312 */       if (param1TimeZone == null) {
/* 1313 */         throw new NullPointerException();
/*      */       }
/* 1315 */       this.zone = param1TimeZone;
/* 1316 */       return this;
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
/*      */     public Builder setLenient(boolean param1Boolean) {
/* 1330 */       this.lenient = param1Boolean;
/* 1331 */       return this;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setCalendarType(String param1String) {
/* 1354 */       if (param1String.equals("gregorian")) {
/* 1355 */         param1String = "gregory";
/*      */       }
/* 1357 */       if (!Calendar.getAvailableCalendarTypes().contains(param1String) && 
/* 1358 */         !param1String.equals("iso8601")) {
/* 1359 */         throw new IllegalArgumentException("unknown calendar type: " + param1String);
/*      */       }
/* 1361 */       if (this.type == null) {
/* 1362 */         this.type = param1String;
/*      */       }
/* 1364 */       else if (!this.type.equals(param1String)) {
/* 1365 */         throw new IllegalStateException("calendar type override");
/*      */       } 
/*      */       
/* 1368 */       return this;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setLocale(Locale param1Locale) {
/* 1392 */       if (param1Locale == null) {
/* 1393 */         throw new NullPointerException();
/*      */       }
/* 1395 */       this.locale = param1Locale;
/* 1396 */       return this;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder setWeekDefinition(int param1Int1, int param1Int2) {
/* 1418 */       if (!isValidWeekParameter(param1Int1) || 
/* 1419 */         !isValidWeekParameter(param1Int2)) {
/* 1420 */         throw new IllegalArgumentException();
/*      */       }
/* 1422 */       this.firstDayOfWeek = param1Int1;
/* 1423 */       this.minimalDaysInFirstWeek = param1Int2;
/* 1424 */       return this;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Calendar build() {
/*      */       GregorianCalendar gregorianCalendar1;
/*      */       JapaneseImperialCalendar japaneseImperialCalendar;
/*      */       GregorianCalendar gregorianCalendar2;
/* 1462 */       if (this.locale == null) {
/* 1463 */         this.locale = Locale.getDefault();
/*      */       }
/* 1465 */       if (this.zone == null) {
/* 1466 */         this.zone = TimeZone.getDefault();
/*      */       }
/*      */       
/* 1469 */       if (this.type == null) {
/* 1470 */         this.type = this.locale.getUnicodeLocaleType("ca");
/*      */       }
/* 1472 */       if (this.type == null) {
/* 1473 */         if (this.locale.getCountry() == "TH" && this.locale
/* 1474 */           .getLanguage() == "th") {
/* 1475 */           this.type = "buddhist";
/*      */         } else {
/* 1477 */           this.type = "gregory";
/*      */         } 
/*      */       }
/* 1480 */       switch (this.type) {
/*      */         case "gregory":
/* 1482 */           gregorianCalendar1 = new GregorianCalendar(this.zone, this.locale, true);
/*      */           break;
/*      */         case "iso8601":
/* 1485 */           gregorianCalendar2 = new GregorianCalendar(this.zone, this.locale, true);
/*      */           
/* 1487 */           gregorianCalendar2.setGregorianChange(new Date(Long.MIN_VALUE));
/*      */           
/* 1489 */           setWeekDefinition(2, 4);
/* 1490 */           gregorianCalendar1 = gregorianCalendar2;
/*      */           break;
/*      */         case "buddhist":
/* 1493 */           gregorianCalendar1 = new BuddhistCalendar(this.zone, this.locale);
/* 1494 */           gregorianCalendar1.clear();
/*      */           break;
/*      */         case "japanese":
/* 1497 */           japaneseImperialCalendar = new JapaneseImperialCalendar(this.zone, this.locale, true);
/*      */           break;
/*      */         default:
/* 1500 */           throw new IllegalArgumentException("unknown calendar type: " + this.type);
/*      */       } 
/* 1502 */       japaneseImperialCalendar.setLenient(this.lenient);
/* 1503 */       if (this.firstDayOfWeek != 0) {
/* 1504 */         japaneseImperialCalendar.setFirstDayOfWeek(this.firstDayOfWeek);
/* 1505 */         japaneseImperialCalendar.setMinimalDaysInFirstWeek(this.minimalDaysInFirstWeek);
/*      */       } 
/* 1507 */       if (isInstantSet()) {
/* 1508 */         japaneseImperialCalendar.setTimeInMillis(this.instant);
/* 1509 */         japaneseImperialCalendar.complete();
/* 1510 */         return japaneseImperialCalendar;
/*      */       } 
/*      */       
/* 1513 */       if (this.fields != null) {
/* 1514 */         boolean bool = (isSet(17) && this.fields[17] > this.fields[1]) ? true : false;
/*      */         
/* 1516 */         if (bool && !japaneseImperialCalendar.isWeekDateSupported()) {
/* 1517 */           throw new IllegalArgumentException("week date is unsupported by " + this.type);
/*      */         }
/*      */         
/*      */         byte b;
/*      */         
/* 1522 */         for (b = 2; b < this.nextStamp; b++) {
/* 1523 */           for (byte b1 = 0; b1 <= this.maxFieldIndex; b1++) {
/* 1524 */             if (this.fields[b1] == b) {
/* 1525 */               japaneseImperialCalendar.set(b1, this.fields[18 + b1]);
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/* 1531 */         if (bool) {
/* 1532 */           b = isSet(3) ? this.fields[21] : 1;
/*      */           
/* 1534 */           int i = isSet(7) ? this.fields[25] : japaneseImperialCalendar.getFirstDayOfWeek();
/* 1535 */           japaneseImperialCalendar.setWeekDate(this.fields[35], b, i);
/*      */         } 
/* 1537 */         japaneseImperialCalendar.complete();
/*      */       } 
/*      */       
/* 1540 */       return japaneseImperialCalendar;
/*      */     }
/*      */     
/*      */     private void allocateFields() {
/* 1544 */       if (this.fields == null) {
/* 1545 */         this.fields = new int[36];
/* 1546 */         this.nextStamp = 2;
/* 1547 */         this.maxFieldIndex = -1;
/*      */       } 
/*      */     }
/*      */     
/*      */     private void internalSet(int param1Int1, int param1Int2) {
/* 1552 */       this.fields[param1Int1] = this.nextStamp++;
/* 1553 */       if (this.nextStamp < 0) {
/* 1554 */         throw new IllegalStateException("stamp counter overflow");
/*      */       }
/* 1556 */       this.fields[18 + param1Int1] = param1Int2;
/* 1557 */       if (param1Int1 > this.maxFieldIndex && param1Int1 < 17) {
/* 1558 */         this.maxFieldIndex = param1Int1;
/*      */       }
/*      */     }
/*      */     
/*      */     private boolean isInstantSet() {
/* 1563 */       return (this.nextStamp == 1);
/*      */     }
/*      */     
/*      */     private boolean isSet(int param1Int) {
/* 1567 */       return (this.fields != null && this.fields[param1Int] > 0);
/*      */     }
/*      */     
/*      */     private boolean isValidWeekParameter(int param1Int) {
/* 1571 */       return (param1Int > 0 && param1Int <= 7);
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
/*      */   protected Calendar() {
/* 1583 */     this(TimeZone.getDefaultRef(), Locale.getDefault(Locale.Category.FORMAT));
/* 1584 */     this.sharedZone = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Calendar(TimeZone paramTimeZone, Locale paramLocale) {
/* 1595 */     this.fields = new int[17];
/* 1596 */     this.isSet = new boolean[17];
/* 1597 */     this.stamp = new int[17];
/*      */     
/* 1599 */     this.zone = paramTimeZone;
/* 1600 */     setWeekCountData(paramLocale);
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
/*      */   public static Calendar getInstance() {
/* 1613 */     return createCalendar(TimeZone.getDefault(), Locale.getDefault(Locale.Category.FORMAT));
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
/*      */   public static Calendar getInstance(TimeZone paramTimeZone) {
/* 1627 */     return createCalendar(paramTimeZone, Locale.getDefault(Locale.Category.FORMAT));
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
/*      */   public static Calendar getInstance(Locale paramLocale) {
/* 1640 */     return createCalendar(TimeZone.getDefault(), paramLocale);
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
/*      */   public static Calendar getInstance(TimeZone paramTimeZone, Locale paramLocale) {
/* 1655 */     return createCalendar(paramTimeZone, paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Calendar createCalendar(TimeZone paramTimeZone, Locale paramLocale) {
/* 1663 */     CalendarProvider calendarProvider = LocaleProviderAdapter.getAdapter((Class)CalendarProvider.class, paramLocale).getCalendarProvider();
/* 1664 */     if (calendarProvider != null) {
/*      */       try {
/* 1666 */         return calendarProvider.getInstance(paramTimeZone, paramLocale);
/* 1667 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1672 */     GregorianCalendar gregorianCalendar = null;
/*      */     
/* 1674 */     if (paramLocale.hasExtensions()) {
/* 1675 */       String str = paramLocale.getUnicodeLocaleType("ca");
/* 1676 */       if (str != null) {
/* 1677 */         JapaneseImperialCalendar japaneseImperialCalendar; switch (str) {
/*      */           case "buddhist":
/* 1679 */             gregorianCalendar = new BuddhistCalendar(paramTimeZone, paramLocale);
/*      */             break;
/*      */           case "japanese":
/* 1682 */             japaneseImperialCalendar = new JapaneseImperialCalendar(paramTimeZone, paramLocale);
/*      */             break;
/*      */           case "gregory":
/* 1685 */             gregorianCalendar = new GregorianCalendar(paramTimeZone, paramLocale);
/*      */             break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1690 */     if (gregorianCalendar == null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1697 */       if (paramLocale.getLanguage() == "th" && paramLocale.getCountry() == "TH") {
/* 1698 */         gregorianCalendar = new BuddhistCalendar(paramTimeZone, paramLocale);
/* 1699 */       } else if (paramLocale.getVariant() == "JP" && paramLocale.getLanguage() == "ja" && paramLocale
/* 1700 */         .getCountry() == "JP") {
/* 1701 */         JapaneseImperialCalendar japaneseImperialCalendar = new JapaneseImperialCalendar(paramTimeZone, paramLocale);
/*      */       } else {
/* 1703 */         gregorianCalendar = new GregorianCalendar(paramTimeZone, paramLocale);
/*      */       } 
/*      */     }
/* 1706 */     return gregorianCalendar;
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
/*      */   public static synchronized Locale[] getAvailableLocales() {
/* 1720 */     return DateFormat.getAvailableLocales();
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
/*      */   public final Date getTime() {
/* 1755 */     return new Date(getTimeInMillis());
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
/*      */   public final void setTime(Date paramDate) {
/* 1770 */     setTimeInMillis(paramDate.getTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getTimeInMillis() {
/* 1781 */     if (!this.isTimeSet) {
/* 1782 */       updateTime();
/*      */     }
/* 1784 */     return this.time;
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
/*      */   public void setTimeInMillis(long paramLong) {
/* 1797 */     if (this.time == paramLong && this.isTimeSet && this.areFieldsSet && this.areAllFieldsSet && this.zone instanceof ZoneInfo && 
/* 1798 */       !((ZoneInfo)this.zone).isDirty()) {
/*      */       return;
/*      */     }
/* 1801 */     this.time = paramLong;
/* 1802 */     this.isTimeSet = true;
/* 1803 */     this.areFieldsSet = false;
/* 1804 */     computeFields();
/* 1805 */     this.areAllFieldsSet = this.areFieldsSet = true;
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
/*      */   public int get(int paramInt) {
/* 1826 */     complete();
/* 1827 */     return internalGet(paramInt);
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
/*      */   protected final int internalGet(int paramInt) {
/* 1840 */     return this.fields[paramInt];
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
/*      */   final void internalSet(int paramInt1, int paramInt2) {
/* 1857 */     this.fields[paramInt1] = paramInt2;
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
/*      */   public void set(int paramInt1, int paramInt2) {
/* 1878 */     if (this.areFieldsSet && !this.areAllFieldsSet) {
/* 1879 */       computeFields();
/*      */     }
/* 1881 */     internalSet(paramInt1, paramInt2);
/* 1882 */     this.isTimeSet = false;
/* 1883 */     this.areFieldsSet = false;
/* 1884 */     this.isSet[paramInt1] = true;
/* 1885 */     this.stamp[paramInt1] = this.nextStamp++;
/* 1886 */     if (this.nextStamp == Integer.MAX_VALUE) {
/* 1887 */       adjustStamp();
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(int paramInt1, int paramInt2, int paramInt3) {
/* 1907 */     set(1, paramInt1);
/* 1908 */     set(2, paramInt2);
/* 1909 */     set(5, paramInt3);
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
/*      */   public final void set(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 1931 */     set(1, paramInt1);
/* 1932 */     set(2, paramInt2);
/* 1933 */     set(5, paramInt3);
/* 1934 */     set(11, paramInt4);
/* 1935 */     set(12, paramInt5);
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
/*      */   public final void set(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 1959 */     set(1, paramInt1);
/* 1960 */     set(2, paramInt2);
/* 1961 */     set(5, paramInt3);
/* 1962 */     set(11, paramInt4);
/* 1963 */     set(12, paramInt5);
/* 1964 */     set(13, paramInt6);
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
/*      */   public final void clear() {
/* 1983 */     for (byte b = 0; b < this.fields.length; ) {
/* 1984 */       this.fields[b] = 0; this.stamp[b] = 0;
/* 1985 */       this.isSet[b++] = false;
/*      */     } 
/* 1987 */     this.areAllFieldsSet = this.areFieldsSet = false;
/* 1988 */     this.isTimeSet = false;
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
/*      */   public final void clear(int paramInt) {
/* 2014 */     this.fields[paramInt] = 0;
/* 2015 */     this.stamp[paramInt] = 0;
/* 2016 */     this.isSet[paramInt] = false;
/*      */     
/* 2018 */     this.areAllFieldsSet = this.areFieldsSet = false;
/* 2019 */     this.isTimeSet = false;
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
/*      */   public final boolean isSet(int paramInt) {
/* 2033 */     return (this.stamp[paramInt] != 0);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDisplayName(int paramInt1, int paramInt2, Locale paramLocale) {
/* 2081 */     if (!checkDisplayNameParams(paramInt1, paramInt2, 1, 4, paramLocale, 645))
/*      */     {
/* 2083 */       return null;
/*      */     }
/*      */     
/* 2086 */     String str = getCalendarType();
/* 2087 */     int i = get(paramInt1);
/*      */     
/* 2089 */     if (isStandaloneStyle(paramInt2) || isNarrowFormatStyle(paramInt2)) {
/* 2090 */       String str1 = CalendarDataUtility.retrieveFieldValueName(str, paramInt1, i, paramInt2, paramLocale);
/*      */ 
/*      */ 
/*      */       
/* 2094 */       if (str1 == null) {
/* 2095 */         if (isNarrowFormatStyle(paramInt2)) {
/* 2096 */           str1 = CalendarDataUtility.retrieveFieldValueName(str, paramInt1, i, 
/*      */               
/* 2098 */               toStandaloneStyle(paramInt2), paramLocale);
/*      */         }
/* 2100 */         else if (isStandaloneStyle(paramInt2)) {
/* 2101 */           str1 = CalendarDataUtility.retrieveFieldValueName(str, paramInt1, i, 
/*      */               
/* 2103 */               getBaseStyle(paramInt2), paramLocale);
/*      */         } 
/*      */       }
/*      */       
/* 2107 */       return str1;
/*      */     } 
/*      */     
/* 2110 */     DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance(paramLocale);
/* 2111 */     String[] arrayOfString = getFieldStrings(paramInt1, paramInt2, dateFormatSymbols);
/* 2112 */     if (arrayOfString != null && 
/* 2113 */       i < arrayOfString.length) {
/* 2114 */       return arrayOfString[i];
/*      */     }
/*      */     
/* 2117 */     return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Integer> getDisplayNames(int paramInt1, int paramInt2, Locale paramLocale) {
/* 2170 */     if (!checkDisplayNameParams(paramInt1, paramInt2, 0, 4, paramLocale, 645))
/*      */     {
/* 2172 */       return null;
/*      */     }
/*      */     
/* 2175 */     String str = getCalendarType();
/* 2176 */     if (paramInt2 == 0 || isStandaloneStyle(paramInt2) || isNarrowFormatStyle(paramInt2)) {
/*      */       
/* 2178 */       Map<String, Integer> map = CalendarDataUtility.retrieveFieldValueNames(str, paramInt1, paramInt2, paramLocale);
/*      */ 
/*      */       
/* 2181 */       if (map == null) {
/* 2182 */         if (isNarrowFormatStyle(paramInt2)) {
/* 2183 */           map = CalendarDataUtility.retrieveFieldValueNames(str, paramInt1, 
/* 2184 */               toStandaloneStyle(paramInt2), paramLocale);
/* 2185 */         } else if (paramInt2 != 0) {
/* 2186 */           map = CalendarDataUtility.retrieveFieldValueNames(str, paramInt1, 
/* 2187 */               getBaseStyle(paramInt2), paramLocale);
/*      */         } 
/*      */       }
/* 2190 */       return map;
/*      */     } 
/*      */ 
/*      */     
/* 2194 */     return getDisplayNamesImpl(paramInt1, paramInt2, paramLocale);
/*      */   }
/*      */   
/*      */   private Map<String, Integer> getDisplayNamesImpl(int paramInt1, int paramInt2, Locale paramLocale) {
/* 2198 */     DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance(paramLocale);
/* 2199 */     String[] arrayOfString = getFieldStrings(paramInt1, paramInt2, dateFormatSymbols);
/* 2200 */     if (arrayOfString != null) {
/* 2201 */       HashMap<Object, Object> hashMap = new HashMap<>();
/* 2202 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 2203 */         if (arrayOfString[b].length() != 0)
/*      */         {
/*      */           
/* 2206 */           hashMap.put(arrayOfString[b], Integer.valueOf(b)); } 
/*      */       } 
/* 2208 */       return (Map)hashMap;
/*      */     } 
/* 2210 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   boolean checkDisplayNameParams(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Locale paramLocale, int paramInt5) {
/* 2215 */     int i = getBaseStyle(paramInt2);
/* 2216 */     if (paramInt1 < 0 || paramInt1 >= this.fields.length || i < paramInt3 || i > paramInt4)
/*      */     {
/* 2218 */       throw new IllegalArgumentException();
/*      */     }
/* 2220 */     if (paramLocale == null) {
/* 2221 */       throw new NullPointerException();
/*      */     }
/* 2223 */     return isFieldSet(paramInt5, paramInt1);
/*      */   }
/*      */   
/*      */   private String[] getFieldStrings(int paramInt1, int paramInt2, DateFormatSymbols paramDateFormatSymbols) {
/* 2227 */     int i = getBaseStyle(paramInt2);
/*      */ 
/*      */     
/* 2230 */     if (i == 4) {
/* 2231 */       return null;
/*      */     }
/*      */     
/* 2234 */     String[] arrayOfString = null;
/* 2235 */     switch (paramInt1) {
/*      */       case 0:
/* 2237 */         arrayOfString = paramDateFormatSymbols.getEras();
/*      */         break;
/*      */       
/*      */       case 2:
/* 2241 */         arrayOfString = (i == 2) ? paramDateFormatSymbols.getMonths() : paramDateFormatSymbols.getShortMonths();
/*      */         break;
/*      */       
/*      */       case 7:
/* 2245 */         arrayOfString = (i == 2) ? paramDateFormatSymbols.getWeekdays() : paramDateFormatSymbols.getShortWeekdays();
/*      */         break;
/*      */       
/*      */       case 9:
/* 2249 */         arrayOfString = paramDateFormatSymbols.getAmPmStrings();
/*      */         break;
/*      */     } 
/* 2252 */     return arrayOfString;
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
/*      */   protected void complete() {
/* 2264 */     if (!this.isTimeSet) {
/* 2265 */       updateTime();
/*      */     }
/* 2267 */     if (!this.areFieldsSet || !this.areAllFieldsSet) {
/* 2268 */       computeFields();
/* 2269 */       this.areAllFieldsSet = this.areFieldsSet = true;
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
/*      */ 
/*      */   
/*      */   final boolean isExternallySet(int paramInt) {
/* 2287 */     return (this.stamp[paramInt] >= 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int getSetStateFields() {
/* 2297 */     int i = 0;
/* 2298 */     for (byte b = 0; b < this.fields.length; b++) {
/* 2299 */       if (this.stamp[b] != 0) {
/* 2300 */         i |= 1 << b;
/*      */       }
/*      */     } 
/* 2303 */     return i;
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
/*      */   final void setFieldsComputed(int paramInt) {
/* 2320 */     if (paramInt == 131071) {
/* 2321 */       for (byte b = 0; b < this.fields.length; b++) {
/* 2322 */         this.stamp[b] = 1;
/* 2323 */         this.isSet[b] = true;
/*      */       } 
/* 2325 */       this.areFieldsSet = this.areAllFieldsSet = true;
/*      */     } else {
/* 2327 */       for (byte b = 0; b < this.fields.length; b++) {
/* 2328 */         if ((paramInt & 0x1) == 1) {
/* 2329 */           this.stamp[b] = 1;
/* 2330 */           this.isSet[b] = true;
/*      */         }
/* 2332 */         else if (this.areAllFieldsSet && !this.isSet[b]) {
/* 2333 */           this.areAllFieldsSet = false;
/*      */         } 
/*      */         
/* 2336 */         paramInt >>>= 1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void setFieldsNormalized(int paramInt) {
/* 2357 */     if (paramInt != 131071) {
/* 2358 */       for (byte b = 0; b < this.fields.length; b++) {
/* 2359 */         if ((paramInt & 0x1) == 0) {
/* 2360 */           this.fields[b] = 0; this.stamp[b] = 0;
/* 2361 */           this.isSet[b] = false;
/*      */         } 
/* 2363 */         paramInt >>= 1;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2369 */     this.areFieldsSet = true;
/* 2370 */     this.areAllFieldsSet = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean isPartiallyNormalized() {
/* 2378 */     return (this.areFieldsSet && !this.areAllFieldsSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean isFullyNormalized() {
/* 2386 */     return (this.areFieldsSet && this.areAllFieldsSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void setUnnormalized() {
/* 2393 */     this.areFieldsSet = this.areAllFieldsSet = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isFieldSet(int paramInt1, int paramInt2) {
/* 2401 */     return ((paramInt1 & 1 << paramInt2) != 0);
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
/*      */   final int selectFields() {
/* 2431 */     int i = 2;
/*      */     
/* 2433 */     if (this.stamp[0] != 0) {
/* 2434 */       i |= 0x1;
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
/* 2448 */     int j = this.stamp[7];
/* 2449 */     int k = this.stamp[2];
/* 2450 */     int m = this.stamp[5];
/* 2451 */     int n = aggregateStamp(this.stamp[4], j);
/* 2452 */     int i1 = aggregateStamp(this.stamp[8], j);
/* 2453 */     int i2 = this.stamp[6];
/* 2454 */     int i3 = aggregateStamp(this.stamp[3], j);
/*      */     
/* 2456 */     int i4 = m;
/* 2457 */     if (n > i4) {
/* 2458 */       i4 = n;
/*      */     }
/* 2460 */     if (i1 > i4) {
/* 2461 */       i4 = i1;
/*      */     }
/* 2463 */     if (i2 > i4) {
/* 2464 */       i4 = i2;
/*      */     }
/* 2466 */     if (i3 > i4) {
/* 2467 */       i4 = i3;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2474 */     if (i4 == 0) {
/* 2475 */       n = this.stamp[4];
/* 2476 */       i1 = Math.max(this.stamp[8], j);
/* 2477 */       i3 = this.stamp[3];
/* 2478 */       i4 = Math.max(Math.max(n, i1), i3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2484 */       if (i4 == 0) {
/* 2485 */         i4 = m = k;
/*      */       }
/*      */     } 
/*      */     
/* 2489 */     if (i4 == m || (i4 == n && this.stamp[4] >= this.stamp[3]) || (i4 == i1 && this.stamp[8] >= this.stamp[3])) {
/*      */ 
/*      */       
/* 2492 */       i |= 0x4;
/* 2493 */       if (i4 == m) {
/* 2494 */         i |= 0x20;
/*      */       } else {
/* 2496 */         assert i4 == n || i4 == i1;
/* 2497 */         if (j != 0) {
/* 2498 */           i |= 0x80;
/*      */         }
/* 2500 */         if (n == i1) {
/*      */ 
/*      */           
/* 2503 */           if (this.stamp[4] >= this.stamp[8]) {
/* 2504 */             i |= 0x10;
/*      */           } else {
/* 2506 */             i |= 0x100;
/*      */           }
/*      */         
/* 2509 */         } else if (i4 == n) {
/* 2510 */           i |= 0x10;
/*      */         } else {
/* 2512 */           assert i4 == i1;
/* 2513 */           if (this.stamp[8] != 0) {
/* 2514 */             i |= 0x100;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 2520 */       assert i4 == i2 || i4 == i3 || i4 == 0;
/*      */       
/* 2522 */       if (i4 == i2) {
/* 2523 */         i |= 0x40;
/*      */       } else {
/* 2525 */         assert i4 == i3;
/* 2526 */         if (j != 0) {
/* 2527 */           i |= 0x80;
/*      */         }
/* 2529 */         i |= 0x8;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2536 */     int i5 = this.stamp[11];
/* 2537 */     int i6 = aggregateStamp(this.stamp[10], this.stamp[9]);
/* 2538 */     i4 = (i6 > i5) ? i6 : i5;
/*      */ 
/*      */     
/* 2541 */     if (i4 == 0) {
/* 2542 */       i4 = Math.max(this.stamp[10], this.stamp[9]);
/*      */     }
/*      */ 
/*      */     
/* 2546 */     if (i4 != 0) {
/* 2547 */       if (i4 == i5) {
/* 2548 */         i |= 0x800;
/*      */       } else {
/* 2550 */         i |= 0x400;
/* 2551 */         if (this.stamp[9] != 0) {
/* 2552 */           i |= 0x200;
/*      */         }
/*      */       } 
/*      */     }
/* 2556 */     if (this.stamp[12] != 0) {
/* 2557 */       i |= 0x1000;
/*      */     }
/* 2559 */     if (this.stamp[13] != 0) {
/* 2560 */       i |= 0x2000;
/*      */     }
/* 2562 */     if (this.stamp[14] != 0) {
/* 2563 */       i |= 0x4000;
/*      */     }
/* 2565 */     if (this.stamp[15] >= 2) {
/* 2566 */       i |= 0x8000;
/*      */     }
/* 2568 */     if (this.stamp[16] >= 2) {
/* 2569 */       i |= 0x10000;
/*      */     }
/*      */     
/* 2572 */     return i;
/*      */   }
/*      */   
/*      */   int getBaseStyle(int paramInt) {
/* 2576 */     return paramInt & 0xFFFF7FFF;
/*      */   }
/*      */   
/*      */   private int toStandaloneStyle(int paramInt) {
/* 2580 */     return paramInt | 0x8000;
/*      */   }
/*      */   
/*      */   private boolean isStandaloneStyle(int paramInt) {
/* 2584 */     return ((paramInt & 0x8000) != 0);
/*      */   }
/*      */   
/*      */   private boolean isNarrowStyle(int paramInt) {
/* 2588 */     return (paramInt == 4 || paramInt == 32772);
/*      */   }
/*      */   
/*      */   private boolean isNarrowFormatStyle(int paramInt) {
/* 2592 */     return (paramInt == 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int aggregateStamp(int paramInt1, int paramInt2) {
/* 2602 */     if (paramInt1 == 0 || paramInt2 == 0) {
/* 2603 */       return 0;
/*      */     }
/* 2605 */     return (paramInt1 > paramInt2) ? paramInt1 : paramInt2;
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
/*      */   public static Set<String> getAvailableCalendarTypes() {
/* 2624 */     return AvailableCalendarTypes.SET;
/*      */   }
/*      */   
/*      */   private static class AvailableCalendarTypes { private static final Set<String> SET;
/*      */     
/*      */     static {
/* 2630 */       HashSet<String> hashSet = new HashSet(3);
/* 2631 */       hashSet.add("gregory");
/* 2632 */       hashSet.add("buddhist");
/* 2633 */       hashSet.add("japanese");
/* 2634 */       SET = Collections.unmodifiableSet(hashSet);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCalendarType() {
/* 2658 */     return getClass().getName();
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
/*      */   public boolean equals(Object paramObject) {
/* 2686 */     if (this == paramObject) {
/* 2687 */       return true;
/*      */     }
/*      */     try {
/* 2690 */       Calendar calendar = (Calendar)paramObject;
/* 2691 */       return (compareTo(getMillisOf(calendar)) == 0 && this.lenient == calendar.lenient && this.firstDayOfWeek == calendar.firstDayOfWeek && this.minimalDaysInFirstWeek == calendar.minimalDaysInFirstWeek && this.zone
/*      */ 
/*      */ 
/*      */         
/* 2695 */         .equals(calendar.zone));
/* 2696 */     } catch (Exception exception) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2701 */       return false;
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
/*      */   public int hashCode() {
/* 2716 */     int i = (this.lenient ? 1 : 0) | this.firstDayOfWeek << 1 | this.minimalDaysInFirstWeek << 4 | this.zone.hashCode() << 7;
/* 2717 */     long l = getMillisOf(this);
/* 2718 */     return (int)l ^ (int)(l >> 32L) ^ i;
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
/*      */   public boolean before(Object paramObject) {
/* 2738 */     return (paramObject instanceof Calendar && 
/* 2739 */       compareTo((Calendar)paramObject) < 0);
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
/*      */   public boolean after(Object paramObject) {
/* 2759 */     return (paramObject instanceof Calendar && 
/* 2760 */       compareTo((Calendar)paramObject) > 0);
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
/*      */   public int compareTo(Calendar paramCalendar) {
/* 2784 */     return compareTo(getMillisOf(paramCalendar));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void roll(int paramInt1, int paramInt2) {
/* 2843 */     while (paramInt2 > 0) {
/* 2844 */       roll(paramInt1, true);
/* 2845 */       paramInt2--;
/*      */     } 
/* 2847 */     while (paramInt2 < 0) {
/* 2848 */       roll(paramInt1, false);
/* 2849 */       paramInt2++;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTimeZone(TimeZone paramTimeZone) {
/* 2860 */     this.zone = paramTimeZone;
/* 2861 */     this.sharedZone = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2871 */     this.areAllFieldsSet = this.areFieldsSet = false;
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
/*      */   public TimeZone getTimeZone() {
/* 2883 */     if (this.sharedZone) {
/* 2884 */       this.zone = (TimeZone)this.zone.clone();
/* 2885 */       this.sharedZone = false;
/*      */     } 
/* 2887 */     return this.zone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   TimeZone getZone() {
/* 2894 */     return this.zone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setZoneShared(boolean paramBoolean) {
/* 2901 */     this.sharedZone = paramBoolean;
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
/*      */   public void setLenient(boolean paramBoolean) {
/* 2918 */     this.lenient = paramBoolean;
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
/*      */   public boolean isLenient() {
/* 2930 */     return this.lenient;
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
/*      */   public void setFirstDayOfWeek(int paramInt) {
/* 2943 */     if (this.firstDayOfWeek == paramInt) {
/*      */       return;
/*      */     }
/* 2946 */     this.firstDayOfWeek = paramInt;
/* 2947 */     invalidateWeekFields();
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
/*      */   public int getFirstDayOfWeek() {
/* 2960 */     return this.firstDayOfWeek;
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
/*      */   public void setMinimalDaysInFirstWeek(int paramInt) {
/* 2975 */     if (this.minimalDaysInFirstWeek == paramInt) {
/*      */       return;
/*      */     }
/* 2978 */     this.minimalDaysInFirstWeek = paramInt;
/* 2979 */     invalidateWeekFields();
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
/*      */   public int getMinimalDaysInFirstWeek() {
/* 2994 */     return this.minimalDaysInFirstWeek;
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
/*      */   public boolean isWeekDateSupported() {
/* 3010 */     return false;
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
/*      */   public int getWeekYear() {
/* 3032 */     throw new UnsupportedOperationException();
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
/*      */   public void setWeekDate(int paramInt1, int paramInt2, int paramInt3) {
/* 3068 */     throw new UnsupportedOperationException();
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
/*      */   public int getWeeksInWeekYear() {
/* 3089 */     throw new UnsupportedOperationException();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getActualMinimum(int paramInt) {
/* 3187 */     int i = getGreatestMinimum(paramInt);
/* 3188 */     int j = getMinimum(paramInt);
/*      */ 
/*      */     
/* 3191 */     if (i == j) {
/* 3192 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3197 */     Calendar calendar = (Calendar)clone();
/* 3198 */     calendar.setLenient(true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3203 */     int k = i;
/*      */     
/*      */     do {
/* 3206 */       calendar.set(paramInt, i);
/* 3207 */       if (calendar.get(paramInt) != i) {
/*      */         break;
/*      */       }
/* 3210 */       k = i;
/* 3211 */       --i;
/*      */     }
/* 3213 */     while (i >= j);
/*      */     
/* 3215 */     return k;
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
/*      */   public int getActualMaximum(int paramInt) {
/* 3241 */     int i = getLeastMaximum(paramInt);
/* 3242 */     int j = getMaximum(paramInt);
/*      */ 
/*      */     
/* 3245 */     if (i == j) {
/* 3246 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3251 */     Calendar calendar = (Calendar)clone();
/* 3252 */     calendar.setLenient(true);
/*      */ 
/*      */ 
/*      */     
/* 3256 */     if (paramInt == 3 || paramInt == 4) {
/* 3257 */       calendar.set(7, this.firstDayOfWeek);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3263 */     int k = i;
/*      */     
/*      */     do {
/* 3266 */       calendar.set(paramInt, i);
/* 3267 */       if (calendar.get(paramInt) != i) {
/*      */         break;
/*      */       }
/* 3270 */       k = i;
/* 3271 */       ++i;
/*      */     }
/* 3273 */     while (i <= j);
/*      */     
/* 3275 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*      */     try {
/* 3287 */       Calendar calendar = (Calendar)super.clone();
/*      */       
/* 3289 */       calendar.fields = new int[17];
/* 3290 */       calendar.isSet = new boolean[17];
/* 3291 */       calendar.stamp = new int[17];
/* 3292 */       for (byte b = 0; b < 17; b++) {
/* 3293 */         calendar.fields[b] = this.fields[b];
/* 3294 */         calendar.stamp[b] = this.stamp[b];
/* 3295 */         calendar.isSet[b] = this.isSet[b];
/*      */       } 
/* 3297 */       calendar.zone = (TimeZone)this.zone.clone();
/* 3298 */       return calendar;
/*      */     }
/* 3300 */     catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/* 3302 */       throw new InternalError(cloneNotSupportedException);
/*      */     } 
/*      */   }
/*      */   
/* 3306 */   private static final String[] FIELD_NAME = new String[] { "ERA", "YEAR", "MONTH", "WEEK_OF_YEAR", "WEEK_OF_MONTH", "DAY_OF_MONTH", "DAY_OF_YEAR", "DAY_OF_WEEK", "DAY_OF_WEEK_IN_MONTH", "AM_PM", "HOUR", "HOUR_OF_DAY", "MINUTE", "SECOND", "MILLISECOND", "ZONE_OFFSET", "DST_OFFSET" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String getFieldName(int paramInt) {
/* 3322 */     return FIELD_NAME[paramInt];
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
/*      */   public String toString() {
/* 3339 */     StringBuilder stringBuilder = new StringBuilder(800);
/* 3340 */     stringBuilder.append(getClass().getName()).append('[');
/* 3341 */     appendValue(stringBuilder, "time", this.isTimeSet, this.time);
/* 3342 */     stringBuilder.append(",areFieldsSet=").append(this.areFieldsSet);
/* 3343 */     stringBuilder.append(",areAllFieldsSet=").append(this.areAllFieldsSet);
/* 3344 */     stringBuilder.append(",lenient=").append(this.lenient);
/* 3345 */     stringBuilder.append(",zone=").append(this.zone);
/* 3346 */     appendValue(stringBuilder, ",firstDayOfWeek", true, this.firstDayOfWeek);
/* 3347 */     appendValue(stringBuilder, ",minimalDaysInFirstWeek", true, this.minimalDaysInFirstWeek);
/* 3348 */     for (byte b = 0; b < 17; b++) {
/* 3349 */       stringBuilder.append(',');
/* 3350 */       appendValue(stringBuilder, FIELD_NAME[b], isSet(b), this.fields[b]);
/*      */     } 
/* 3352 */     stringBuilder.append(']');
/* 3353 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void appendValue(StringBuilder paramStringBuilder, String paramString, boolean paramBoolean, long paramLong) {
/* 3359 */     paramStringBuilder.append(paramString).append('=');
/* 3360 */     if (paramBoolean) {
/* 3361 */       paramStringBuilder.append(paramLong);
/*      */     } else {
/* 3363 */       paramStringBuilder.append('?');
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
/*      */   private void setWeekCountData(Locale paramLocale) {
/* 3376 */     int[] arrayOfInt = cachedLocaleData.get(paramLocale);
/* 3377 */     if (arrayOfInt == null) {
/* 3378 */       arrayOfInt = new int[2];
/* 3379 */       arrayOfInt[0] = CalendarDataUtility.retrieveFirstDayOfWeek(paramLocale);
/* 3380 */       arrayOfInt[1] = CalendarDataUtility.retrieveMinimalDaysInFirstWeek(paramLocale);
/* 3381 */       cachedLocaleData.putIfAbsent(paramLocale, arrayOfInt);
/*      */     } 
/* 3383 */     this.firstDayOfWeek = arrayOfInt[0];
/* 3384 */     this.minimalDaysInFirstWeek = arrayOfInt[1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateTime() {
/* 3393 */     computeTime();
/*      */ 
/*      */     
/* 3396 */     this.isTimeSet = true;
/*      */   }
/*      */   
/*      */   private int compareTo(long paramLong) {
/* 3400 */     long l = getMillisOf(this);
/* 3401 */     return (l > paramLong) ? 1 : ((l == paramLong) ? 0 : -1);
/*      */   }
/*      */   
/*      */   private static long getMillisOf(Calendar paramCalendar) {
/* 3405 */     if (paramCalendar.isTimeSet) {
/* 3406 */       return paramCalendar.time;
/*      */     }
/* 3408 */     Calendar calendar = (Calendar)paramCalendar.clone();
/* 3409 */     calendar.setLenient(true);
/* 3410 */     return calendar.getTimeInMillis();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void adjustStamp() {
/* 3418 */     int j, i = 2;
/* 3419 */     byte b = 2;
/*      */     
/*      */     do {
/* 3422 */       j = Integer.MAX_VALUE; byte b1;
/* 3423 */       for (b1 = 0; b1 < this.stamp.length; b1++) {
/* 3424 */         int k = this.stamp[b1];
/* 3425 */         if (k >= b && j > k) {
/* 3426 */           j = k;
/*      */         }
/* 3428 */         if (i < k) {
/* 3429 */           i = k;
/*      */         }
/*      */       } 
/* 3432 */       if (i != j && j == Integer.MAX_VALUE) {
/*      */         break;
/*      */       }
/* 3435 */       for (b1 = 0; b1 < this.stamp.length; b1++) {
/* 3436 */         if (this.stamp[b1] == j) {
/* 3437 */           this.stamp[b1] = b;
/*      */         }
/*      */       } 
/* 3440 */       b++;
/* 3441 */     } while (j != i);
/*      */ 
/*      */ 
/*      */     
/* 3445 */     this.nextStamp = b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void invalidateWeekFields() {
/* 3454 */     if (this.stamp[4] != 1 && this.stamp[3] != 1) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3462 */     Calendar calendar = (Calendar)clone();
/* 3463 */     calendar.setLenient(true);
/* 3464 */     calendar.clear(4);
/* 3465 */     calendar.clear(3);
/*      */     
/* 3467 */     if (this.stamp[4] == 1) {
/* 3468 */       int i = calendar.get(4);
/* 3469 */       if (this.fields[4] != i) {
/* 3470 */         this.fields[4] = i;
/*      */       }
/*      */     } 
/*      */     
/* 3474 */     if (this.stamp[3] == 1) {
/* 3475 */       int i = calendar.get(3);
/* 3476 */       if (this.fields[3] != i) {
/* 3477 */         this.fields[3] = i;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 3500 */     if (!this.isTimeSet) {
/*      */       try {
/* 3502 */         updateTime();
/*      */       }
/* 3504 */       catch (IllegalArgumentException illegalArgumentException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3510 */     TimeZone timeZone = null;
/* 3511 */     if (this.zone instanceof ZoneInfo) {
/* 3512 */       SimpleTimeZone simpleTimeZone = ((ZoneInfo)this.zone).getLastRuleInstance();
/* 3513 */       if (simpleTimeZone == null) {
/* 3514 */         simpleTimeZone = new SimpleTimeZone(this.zone.getRawOffset(), this.zone.getID());
/*      */       }
/* 3516 */       timeZone = this.zone;
/* 3517 */       this.zone = simpleTimeZone;
/*      */     } 
/*      */ 
/*      */     
/* 3521 */     paramObjectOutputStream.defaultWriteObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3526 */     paramObjectOutputStream.writeObject(timeZone);
/* 3527 */     if (timeZone != null)
/* 3528 */       this.zone = timeZone; 
/*      */   }
/*      */   
/*      */   private static class CalendarAccessControlContext {
/*      */     private static final AccessControlContext INSTANCE;
/*      */     
/*      */     static {
/* 3535 */       RuntimePermission runtimePermission = new RuntimePermission("accessClassInPackage.sun.util.calendar");
/* 3536 */       PermissionCollection permissionCollection = runtimePermission.newPermissionCollection();
/* 3537 */       permissionCollection.add(runtimePermission);
/* 3538 */       INSTANCE = new AccessControlContext(new ProtectionDomain[] { new ProtectionDomain(null, permissionCollection) });
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 3552 */     final ObjectInputStream input = paramObjectInputStream;
/* 3553 */     objectInputStream.defaultReadObject();
/*      */     
/* 3555 */     this.stamp = new int[17];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3560 */     if (this.serialVersionOnStream >= 2) {
/*      */       
/* 3562 */       this.isTimeSet = true;
/* 3563 */       if (this.fields == null) {
/* 3564 */         this.fields = new int[17];
/*      */       }
/* 3566 */       if (this.isSet == null) {
/* 3567 */         this.isSet = new boolean[17];
/*      */       }
/*      */     }
/* 3570 */     else if (this.serialVersionOnStream >= 0) {
/*      */       
/* 3572 */       for (byte b = 0; b < 17; b++) {
/* 3573 */         this.stamp[b] = this.isSet[b] ? 1 : 0;
/*      */       }
/*      */     } 
/*      */     
/* 3577 */     this.serialVersionOnStream = 1;
/*      */ 
/*      */     
/* 3580 */     ZoneInfo zoneInfo = null;
/*      */     try {
/* 3582 */       zoneInfo = AccessController.<ZoneInfo>doPrivileged(new PrivilegedExceptionAction<ZoneInfo>()
/*      */           {
/*      */             public ZoneInfo run() throws Exception
/*      */             {
/* 3586 */               return (ZoneInfo)input.readObject();
/*      */             }
/*      */           }, 
/* 3589 */           CalendarAccessControlContext.INSTANCE);
/* 3590 */     } catch (PrivilegedActionException privilegedActionException) {
/* 3591 */       Exception exception = privilegedActionException.getException();
/* 3592 */       if (!(exception instanceof java.io.OptionalDataException)) {
/* 3593 */         if (exception instanceof RuntimeException)
/* 3594 */           throw (RuntimeException)exception; 
/* 3595 */         if (exception instanceof IOException)
/* 3596 */           throw (IOException)exception; 
/* 3597 */         if (exception instanceof ClassNotFoundException) {
/* 3598 */           throw (ClassNotFoundException)exception;
/*      */         }
/* 3600 */         throw new RuntimeException(exception);
/*      */       } 
/*      */     } 
/* 3603 */     if (zoneInfo != null) {
/* 3604 */       this.zone = zoneInfo;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3611 */     if (this.zone instanceof SimpleTimeZone) {
/* 3612 */       String str = this.zone.getID();
/* 3613 */       TimeZone timeZone = TimeZone.getTimeZone(str);
/* 3614 */       if (timeZone != null && timeZone.hasSameRules(this.zone) && timeZone.getID().equals(str)) {
/* 3615 */         this.zone = timeZone;
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
/*      */   public final Instant toInstant() {
/* 3630 */     return Instant.ofEpochMilli(getTimeInMillis());
/*      */   }
/*      */   
/*      */   protected abstract void computeTime();
/*      */   
/*      */   protected abstract void computeFields();
/*      */   
/*      */   public abstract void add(int paramInt1, int paramInt2);
/*      */   
/*      */   public abstract void roll(int paramInt, boolean paramBoolean);
/*      */   
/*      */   public abstract int getMinimum(int paramInt);
/*      */   
/*      */   public abstract int getMaximum(int paramInt);
/*      */   
/*      */   public abstract int getGreatestMinimum(int paramInt);
/*      */   
/*      */   public abstract int getLeastMaximum(int paramInt);
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/Calendar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */