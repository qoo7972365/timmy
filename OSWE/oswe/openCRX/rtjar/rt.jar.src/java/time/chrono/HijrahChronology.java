/*      */ package java.time.chrono;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.time.Clock;
/*      */ import java.time.DateTimeException;
/*      */ import java.time.Instant;
/*      */ import java.time.LocalDate;
/*      */ import java.time.ZoneId;
/*      */ import java.time.format.ResolverStyle;
/*      */ import java.time.temporal.ChronoField;
/*      */ import java.time.temporal.TemporalAccessor;
/*      */ import java.time.temporal.TemporalField;
/*      */ import java.time.temporal.ValueRange;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Properties;
/*      */ import sun.util.calendar.BaseCalendar;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class HijrahChronology
/*      */   extends AbstractChronology
/*      */   implements Serializable
/*      */ {
/*      */   private final transient String typeId;
/*      */   private final transient String calendarType;
/*      */   private static final long serialVersionUID = 3127340209035924785L;
/*      */   public static final HijrahChronology INSTANCE;
/*      */   private volatile transient boolean initComplete;
/*      */   private transient int[] hijrahEpochMonthStartDays;
/*      */   private transient int minEpochDay;
/*      */   private transient int maxEpochDay;
/*      */   private transient int hijrahStartEpochMonth;
/*      */   private transient int minMonthLength;
/*      */   private transient int maxMonthLength;
/*      */   private transient int minYearLength;
/*      */   private transient int maxYearLength;
/*      */   private static final transient Properties calendarProperties;
/*      */   private static final String PROP_PREFIX = "calendar.hijrah.";
/*      */   private static final String PROP_TYPE_SUFFIX = ".type";
/*      */   private static final String KEY_ID = "id";
/*      */   private static final String KEY_TYPE = "type";
/*      */   private static final String KEY_VERSION = "version";
/*      */   private static final String KEY_ISO_START = "iso-start";
/*      */   
/*      */   static {
/*      */     try {
/*  303 */       calendarProperties = BaseCalendar.getCalendarProperties();
/*  304 */     } catch (IOException iOException) {
/*  305 */       throw new InternalError("Can't initialize lib/calendars.properties", iOException);
/*      */     } 
/*      */     
/*      */     try {
/*  309 */       INSTANCE = new HijrahChronology("Hijrah-umalqura");
/*      */       
/*  311 */       AbstractChronology.registerChrono(INSTANCE, "Hijrah");
/*  312 */       AbstractChronology.registerChrono(INSTANCE, "islamic");
/*  313 */     } catch (DateTimeException dateTimeException) {
/*      */       
/*  315 */       PlatformLogger platformLogger = PlatformLogger.getLogger("java.time.chrono");
/*  316 */       platformLogger.severe("Unable to initialize Hijrah calendar: Hijrah-umalqura", dateTimeException);
/*  317 */       throw new RuntimeException("Unable to initialize Hijrah-umalqura calendar", dateTimeException.getCause());
/*      */     } 
/*  319 */     registerVariants();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void registerVariants() {
/*  327 */     for (String str : calendarProperties.stringPropertyNames()) {
/*  328 */       if (str.startsWith("calendar.hijrah.")) {
/*  329 */         String str1 = str.substring("calendar.hijrah.".length());
/*  330 */         if (str1.indexOf('.') >= 0) {
/*      */           continue;
/*      */         }
/*  333 */         if (str1.equals(INSTANCE.getId())) {
/*      */           continue;
/*      */         }
/*      */         
/*      */         try {
/*  338 */           HijrahChronology hijrahChronology = new HijrahChronology(str1);
/*  339 */           AbstractChronology.registerChrono(hijrahChronology);
/*  340 */         } catch (DateTimeException dateTimeException) {
/*      */           
/*  342 */           PlatformLogger platformLogger = PlatformLogger.getLogger("java.time.chrono");
/*  343 */           platformLogger.severe("Unable to initialize Hijrah calendar: " + str1, dateTimeException);
/*      */         } 
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
/*      */   private HijrahChronology(String paramString) throws DateTimeException {
/*  360 */     if (paramString.isEmpty()) {
/*  361 */       throw new IllegalArgumentException("calendar id is empty");
/*      */     }
/*  363 */     String str1 = "calendar.hijrah." + paramString + ".type";
/*  364 */     String str2 = calendarProperties.getProperty(str1);
/*  365 */     if (str2 == null || str2.isEmpty()) {
/*  366 */       throw new DateTimeException("calendarType is missing or empty for: " + str1);
/*      */     }
/*  368 */     this.typeId = paramString;
/*  369 */     this.calendarType = str2;
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
/*      */   private void checkCalendarInit() {
/*  386 */     if (!this.initComplete) {
/*  387 */       loadCalendarData();
/*  388 */       this.initComplete = true;
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
/*      */   public String getId() {
/*  404 */     return this.typeId;
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
/*      */   public String getCalendarType() {
/*  420 */     return this.calendarType;
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
/*      */   public HijrahDate date(Era paramEra, int paramInt1, int paramInt2, int paramInt3) {
/*  438 */     return date(prolepticYear(paramEra, paramInt1), paramInt2, paramInt3);
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
/*      */   public HijrahDate date(int paramInt1, int paramInt2, int paramInt3) {
/*  453 */     return HijrahDate.of(this, paramInt1, paramInt2, paramInt3);
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
/*      */   public HijrahDate dateYearDay(Era paramEra, int paramInt1, int paramInt2) {
/*  469 */     return dateYearDay(prolepticYear(paramEra, paramInt1), paramInt2);
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
/*      */   public HijrahDate dateYearDay(int paramInt1, int paramInt2) {
/*  484 */     HijrahDate hijrahDate = HijrahDate.of(this, paramInt1, 1, 1);
/*  485 */     if (paramInt2 > hijrahDate.lengthOfYear()) {
/*  486 */       throw new DateTimeException("Invalid dayOfYear: " + paramInt2);
/*      */     }
/*  488 */     return hijrahDate.plusDays((paramInt2 - 1));
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
/*      */   public HijrahDate dateEpochDay(long paramLong) {
/*  500 */     return HijrahDate.ofEpochDay(this, paramLong);
/*      */   }
/*      */ 
/*      */   
/*      */   public HijrahDate dateNow() {
/*  505 */     return dateNow(Clock.systemDefaultZone());
/*      */   }
/*      */ 
/*      */   
/*      */   public HijrahDate dateNow(ZoneId paramZoneId) {
/*  510 */     return dateNow(Clock.system(paramZoneId));
/*      */   }
/*      */ 
/*      */   
/*      */   public HijrahDate dateNow(Clock paramClock) {
/*  515 */     return date(LocalDate.now(paramClock));
/*      */   }
/*      */ 
/*      */   
/*      */   public HijrahDate date(TemporalAccessor paramTemporalAccessor) {
/*  520 */     if (paramTemporalAccessor instanceof HijrahDate) {
/*  521 */       return (HijrahDate)paramTemporalAccessor;
/*      */     }
/*  523 */     return HijrahDate.ofEpochDay(this, paramTemporalAccessor.getLong(ChronoField.EPOCH_DAY));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ChronoLocalDateTime<HijrahDate> localDateTime(TemporalAccessor paramTemporalAccessor) {
/*  529 */     return (ChronoLocalDateTime)super.localDateTime(paramTemporalAccessor);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ChronoZonedDateTime<HijrahDate> zonedDateTime(TemporalAccessor paramTemporalAccessor) {
/*  535 */     return (ChronoZonedDateTime)super.zonedDateTime(paramTemporalAccessor);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ChronoZonedDateTime<HijrahDate> zonedDateTime(Instant paramInstant, ZoneId paramZoneId) {
/*  541 */     return (ChronoZonedDateTime)super.zonedDateTime(paramInstant, paramZoneId);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLeapYear(long paramLong) {
/*  547 */     checkCalendarInit();
/*  548 */     if (paramLong < getMinimumYear() || paramLong > getMaximumYear()) {
/*  549 */       return false;
/*      */     }
/*  551 */     int i = getYearLength((int)paramLong);
/*  552 */     return (i > 354);
/*      */   }
/*      */ 
/*      */   
/*      */   public int prolepticYear(Era paramEra, int paramInt) {
/*  557 */     if (!(paramEra instanceof HijrahEra)) {
/*  558 */       throw new ClassCastException("Era must be HijrahEra");
/*      */     }
/*  560 */     return paramInt;
/*      */   }
/*      */ 
/*      */   
/*      */   public HijrahEra eraOf(int paramInt) {
/*  565 */     switch (paramInt) {
/*      */       case 1:
/*  567 */         return HijrahEra.AH;
/*      */     } 
/*  569 */     throw new DateTimeException("invalid Hijrah era");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Era> eras() {
/*  575 */     return Arrays.asList((Era[])HijrahEra.values());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ValueRange range(ChronoField paramChronoField) {
/*  581 */     checkCalendarInit();
/*  582 */     if (paramChronoField instanceof ChronoField) {
/*  583 */       ChronoField chronoField = paramChronoField;
/*  584 */       switch (chronoField) {
/*      */         case DAY_OF_MONTH:
/*  586 */           return ValueRange.of(1L, 1L, getMinimumMonthLength(), getMaximumMonthLength());
/*      */         case DAY_OF_YEAR:
/*  588 */           return ValueRange.of(1L, getMaximumDayOfYear());
/*      */         case ALIGNED_WEEK_OF_MONTH:
/*  590 */           return ValueRange.of(1L, 5L);
/*      */         case YEAR:
/*      */         case YEAR_OF_ERA:
/*  593 */           return ValueRange.of(getMinimumYear(), getMaximumYear());
/*      */         case ERA:
/*  595 */           return ValueRange.of(1L, 1L);
/*      */       } 
/*  597 */       return paramChronoField.range();
/*      */     } 
/*      */     
/*  600 */     return paramChronoField.range();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public HijrahDate resolveDate(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/*  606 */     return (HijrahDate)super.resolveDate(paramMap, paramResolverStyle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int checkValidYear(long paramLong) {
/*  616 */     if (paramLong < getMinimumYear() || paramLong > getMaximumYear()) {
/*  617 */       throw new DateTimeException("Invalid Hijrah year: " + paramLong);
/*      */     }
/*  619 */     return (int)paramLong;
/*      */   }
/*      */   
/*      */   void checkValidDayOfYear(int paramInt) {
/*  623 */     if (paramInt < 1 || paramInt > getMaximumDayOfYear()) {
/*  624 */       throw new DateTimeException("Invalid Hijrah day of year: " + paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */   void checkValidMonth(int paramInt) {
/*  629 */     if (paramInt < 1 || paramInt > 12) {
/*  630 */       throw new DateTimeException("Invalid Hijrah month: " + paramInt);
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
/*      */   int[] getHijrahDateInfo(int paramInt) {
/*  643 */     checkCalendarInit();
/*  644 */     if (paramInt < this.minEpochDay || paramInt >= this.maxEpochDay) {
/*  645 */       throw new DateTimeException("Hijrah date out of range");
/*      */     }
/*      */     
/*  648 */     int i = epochDayToEpochMonth(paramInt);
/*  649 */     int j = epochMonthToYear(i);
/*  650 */     int k = epochMonthToMonth(i);
/*  651 */     int m = epochMonthToEpochDay(i);
/*  652 */     int n = paramInt - m;
/*      */     
/*  654 */     int[] arrayOfInt = new int[3];
/*  655 */     arrayOfInt[0] = j;
/*  656 */     arrayOfInt[1] = k + 1;
/*  657 */     arrayOfInt[2] = n + 1;
/*  658 */     return arrayOfInt;
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
/*      */   long getEpochDay(int paramInt1, int paramInt2, int paramInt3) {
/*  670 */     checkCalendarInit();
/*  671 */     checkValidMonth(paramInt2);
/*  672 */     int i = yearToEpochMonth(paramInt1) + paramInt2 - 1;
/*  673 */     if (i < 0 || i >= this.hijrahEpochMonthStartDays.length) {
/*  674 */       throw new DateTimeException("Invalid Hijrah date, year: " + paramInt1 + ", month: " + paramInt2);
/*      */     }
/*      */     
/*  677 */     if (paramInt3 < 1 || paramInt3 > getMonthLength(paramInt1, paramInt2)) {
/*  678 */       throw new DateTimeException("Invalid Hijrah day of month: " + paramInt3);
/*      */     }
/*  680 */     return (epochMonthToEpochDay(i) + paramInt3 - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getDayOfYear(int paramInt1, int paramInt2) {
/*  691 */     return yearMonthToDayOfYear(paramInt1, paramInt2 - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getMonthLength(int paramInt1, int paramInt2) {
/*  702 */     int i = yearToEpochMonth(paramInt1) + paramInt2 - 1;
/*  703 */     if (i < 0 || i >= this.hijrahEpochMonthStartDays.length) {
/*  704 */       throw new DateTimeException("Invalid Hijrah date, year: " + paramInt1 + ", month: " + paramInt2);
/*      */     }
/*      */     
/*  707 */     return epochMonthLength(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getYearLength(int paramInt) {
/*  718 */     return yearMonthToDayOfYear(paramInt, 12);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getMinimumYear() {
/*  727 */     return epochMonthToYear(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getMaximumYear() {
/*  736 */     return epochMonthToYear(this.hijrahEpochMonthStartDays.length - 1) - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getMaximumMonthLength() {
/*  745 */     return this.maxMonthLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getMinimumMonthLength() {
/*  754 */     return this.minMonthLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getMaximumDayOfYear() {
/*  763 */     return this.maxYearLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getSmallestMaximumDayOfYear() {
/*  772 */     return this.minYearLength;
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
/*      */   private int epochDayToEpochMonth(int paramInt) {
/*  785 */     int i = Arrays.binarySearch(this.hijrahEpochMonthStartDays, paramInt);
/*  786 */     if (i < 0) {
/*  787 */       i = -i - 2;
/*      */     }
/*  789 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int epochMonthToYear(int paramInt) {
/*  799 */     return (paramInt + this.hijrahStartEpochMonth) / 12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int yearToEpochMonth(int paramInt) {
/*  809 */     return paramInt * 12 - this.hijrahStartEpochMonth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int epochMonthToMonth(int paramInt) {
/*  819 */     return (paramInt + this.hijrahStartEpochMonth) % 12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int epochMonthToEpochDay(int paramInt) {
/*  829 */     return this.hijrahEpochMonthStartDays[paramInt];
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
/*      */   private int yearMonthToDayOfYear(int paramInt1, int paramInt2) {
/*  841 */     int i = yearToEpochMonth(paramInt1);
/*  842 */     return epochMonthToEpochDay(i + paramInt2) - 
/*  843 */       epochMonthToEpochDay(i);
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
/*      */   private int epochMonthLength(int paramInt) {
/*  855 */     return this.hijrahEpochMonthStartDays[paramInt + 1] - this.hijrahEpochMonthStartDays[paramInt];
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
/*      */   private static Properties readConfigProperties(String paramString) throws Exception {
/*      */     try {
/*  879 */       return 
/*  880 */         AccessController.<Properties>doPrivileged(() -> {
/*      */             String str = System.getProperty("java.home") + File.separator + "lib";
/*      */             
/*      */             File file = new File(str, paramString);
/*      */             Properties properties = new Properties();
/*      */             try (FileInputStream null = new FileInputStream(file)) {
/*      */               properties.load(fileInputStream);
/*      */             } 
/*      */             return properties;
/*      */           });
/*  890 */     } catch (PrivilegedActionException privilegedActionException) {
/*  891 */       throw privilegedActionException.getException();
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
/*      */   private void loadCalendarData() {
/*      */     try {
/*  908 */       String str1 = calendarProperties.getProperty("calendar.hijrah." + this.typeId);
/*  909 */       Objects.requireNonNull(str1, "Resource missing for calendar: calendar.hijrah." + this.typeId);
/*  910 */       Properties properties = readConfigProperties(str1);
/*      */       
/*  912 */       HashMap<Object, Object> hashMap = new HashMap<>();
/*  913 */       int i = Integer.MAX_VALUE;
/*  914 */       int j = Integer.MIN_VALUE;
/*  915 */       String str2 = null;
/*  916 */       String str3 = null;
/*  917 */       String str4 = null;
/*  918 */       int k = 0;
/*  919 */       for (Map.Entry<Object, Object> entry : properties.entrySet()) {
/*  920 */         int[] arrayOfInt; String str = (String)entry.getKey();
/*  921 */         switch (str) {
/*      */           case "id":
/*  923 */             str2 = (String)entry.getValue();
/*      */             continue;
/*      */           case "type":
/*  926 */             str3 = (String)entry.getValue();
/*      */             continue;
/*      */           case "version":
/*  929 */             str4 = (String)entry.getValue();
/*      */             continue;
/*      */           case "iso-start":
/*  932 */             arrayOfInt = parseYMD((String)entry.getValue());
/*  933 */             k = (int)LocalDate.of(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2]).toEpochDay();
/*      */             continue;
/*      */         } 
/*      */ 
/*      */         
/*      */         try {
/*  939 */           int n = Integer.valueOf(str).intValue();
/*  940 */           int[] arrayOfInt1 = parseMonths((String)entry.getValue());
/*  941 */           hashMap.put(Integer.valueOf(n), arrayOfInt1);
/*  942 */           j = Math.max(j, n);
/*  943 */           i = Math.min(i, n);
/*  944 */         } catch (NumberFormatException numberFormatException) {
/*  945 */           throw new IllegalArgumentException("bad key: " + str);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  950 */       if (!getId().equals(str2)) {
/*  951 */         throw new IllegalArgumentException("Configuration is for a different calendar: " + str2);
/*      */       }
/*  953 */       if (!getCalendarType().equals(str3)) {
/*  954 */         throw new IllegalArgumentException("Configuration is for a different calendar type: " + str3);
/*      */       }
/*  956 */       if (str4 == null || str4.isEmpty()) {
/*  957 */         throw new IllegalArgumentException("Configuration does not contain a version");
/*      */       }
/*  959 */       if (k == 0) {
/*  960 */         throw new IllegalArgumentException("Configuration does not contain a ISO start date");
/*      */       }
/*      */ 
/*      */       
/*  964 */       this.hijrahStartEpochMonth = i * 12;
/*  965 */       this.minEpochDay = k;
/*  966 */       this.hijrahEpochMonthStartDays = createEpochMonths(this.minEpochDay, i, j, (Map)hashMap);
/*  967 */       this.maxEpochDay = this.hijrahEpochMonthStartDays[this.hijrahEpochMonthStartDays.length - 1];
/*      */ 
/*      */       
/*  970 */       for (int m = i; m < j; m++) {
/*  971 */         int n = getYearLength(m);
/*  972 */         this.minYearLength = Math.min(this.minYearLength, n);
/*  973 */         this.maxYearLength = Math.max(this.maxYearLength, n);
/*      */       } 
/*  975 */     } catch (Exception exception) {
/*      */       
/*  977 */       PlatformLogger platformLogger = PlatformLogger.getLogger("java.time.chrono");
/*  978 */       platformLogger.severe("Unable to initialize Hijrah calendar proxy: " + this.typeId, exception);
/*  979 */       throw new DateTimeException("Unable to initialize HijrahCalendar: " + this.typeId, exception);
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
/*      */   private int[] createEpochMonths(int paramInt1, int paramInt2, int paramInt3, Map<Integer, int[]> paramMap) {
/*  996 */     int i = (paramInt3 - paramInt2 + 1) * 12 + 1;
/*      */ 
/*      */     
/*  999 */     byte b = 0;
/* 1000 */     int[] arrayOfInt = new int[i];
/* 1001 */     this.minMonthLength = Integer.MAX_VALUE;
/* 1002 */     this.maxMonthLength = Integer.MIN_VALUE;
/*      */ 
/*      */     
/* 1005 */     for (int j = paramInt2; j <= paramInt3; j++) {
/* 1006 */       int[] arrayOfInt1 = paramMap.get(Integer.valueOf(j));
/* 1007 */       for (byte b1 = 0; b1 < 12; b1++) {
/* 1008 */         int k = arrayOfInt1[b1];
/* 1009 */         arrayOfInt[b++] = paramInt1;
/*      */         
/* 1011 */         if (k < 29 || k > 32) {
/* 1012 */           throw new IllegalArgumentException("Invalid month length in year: " + paramInt2);
/*      */         }
/* 1014 */         paramInt1 += k;
/* 1015 */         this.minMonthLength = Math.min(this.minMonthLength, k);
/* 1016 */         this.maxMonthLength = Math.max(this.maxMonthLength, k);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1021 */     arrayOfInt[b++] = paramInt1;
/*      */     
/* 1023 */     if (b != arrayOfInt.length) {
/* 1024 */       throw new IllegalStateException("Did not fill epochMonths exactly: ndx = " + b + " should be " + arrayOfInt.length);
/*      */     }
/*      */ 
/*      */     
/* 1028 */     return arrayOfInt;
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
/*      */   private int[] parseMonths(String paramString) {
/* 1040 */     int[] arrayOfInt = new int[12];
/* 1041 */     String[] arrayOfString = paramString.split("\\s");
/* 1042 */     if (arrayOfString.length != 12) {
/* 1043 */       throw new IllegalArgumentException("wrong number of months on line: " + Arrays.toString(arrayOfString) + "; count: " + arrayOfString.length);
/*      */     }
/* 1045 */     for (byte b = 0; b < 12; b++) {
/*      */       try {
/* 1047 */         arrayOfInt[b] = Integer.valueOf(arrayOfString[b]).intValue();
/* 1048 */       } catch (NumberFormatException numberFormatException) {
/* 1049 */         throw new IllegalArgumentException("bad key: " + arrayOfString[b]);
/*      */       } 
/*      */     } 
/* 1052 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] parseYMD(String paramString) {
/* 1063 */     paramString = paramString.trim();
/*      */     try {
/* 1065 */       if (paramString.charAt(4) != '-' || paramString.charAt(7) != '-') {
/* 1066 */         throw new IllegalArgumentException("date must be yyyy-MM-dd");
/*      */       }
/* 1068 */       int[] arrayOfInt = new int[3];
/* 1069 */       arrayOfInt[0] = Integer.valueOf(paramString.substring(0, 4)).intValue();
/* 1070 */       arrayOfInt[1] = Integer.valueOf(paramString.substring(5, 7)).intValue();
/* 1071 */       arrayOfInt[2] = Integer.valueOf(paramString.substring(8, 10)).intValue();
/* 1072 */       return arrayOfInt;
/* 1073 */     } catch (NumberFormatException numberFormatException) {
/* 1074 */       throw new IllegalArgumentException("date must be yyyy-MM-dd", numberFormatException);
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
/*      */   Object writeReplace() {
/* 1092 */     return super.writeReplace();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 1102 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/HijrahChronology.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */