/*      */ package com.sun.org.apache.xerces.internal.jaxp.datatype;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.util.DatatypeMessageFormatter;
/*      */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*      */ import java.io.Serializable;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.Locale;
/*      */ import java.util.TimeZone;
/*      */ import javax.xml.datatype.DatatypeConstants;
/*      */ import javax.xml.datatype.Duration;
/*      */ import javax.xml.datatype.XMLGregorianCalendar;
/*      */ import javax.xml.namespace.QName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLGregorianCalendarImpl
/*      */   extends XMLGregorianCalendar
/*      */   implements Serializable, Cloneable
/*      */ {
/*  202 */   private BigInteger eon = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  207 */   private int year = Integer.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  212 */   private int month = Integer.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  217 */   private int day = Integer.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  222 */   private int timezone = Integer.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  227 */   private int hour = Integer.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  232 */   private int minute = Integer.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  237 */   private int second = Integer.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  242 */   private BigDecimal fractionalSecond = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  247 */   private static final BigInteger BILLION = new BigInteger("1000000000");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  253 */   private static final Date PURE_GREGORIAN_CHANGE = new Date(Long.MIN_VALUE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int YEAR = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MONTH = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int DAY = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int HOUR = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MINUTE = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int SECOND = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MILLISECOND = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int TIMEZONE = 7;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  300 */   private static final String[] FIELD_NAME = new String[] { "Year", "Month", "Day", "Hour", "Minute", "Second", "Millisecond", "Timezone" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  332 */   public static final XMLGregorianCalendar LEAP_YEAR_DEFAULT = createDateTime(400, 1, 1, 0, 0, 0, -2147483648, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLGregorianCalendarImpl(String lexicalRepresentation) throws IllegalArgumentException {
/*  366 */     String format = null;
/*  367 */     String lexRep = lexicalRepresentation;
/*  368 */     int NOT_FOUND = -1;
/*  369 */     int lexRepLength = lexRep.length();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  376 */     if (lexRep.indexOf('T') != -1) {
/*      */       
/*  378 */       format = "%Y-%M-%DT%h:%m:%s%z";
/*  379 */     } else if (lexRepLength >= 3 && lexRep.charAt(2) == ':') {
/*      */       
/*  381 */       format = "%h:%m:%s%z";
/*  382 */     } else if (lexRep.startsWith("--")) {
/*      */       
/*  384 */       if (lexRepLength >= 3 && lexRep.charAt(2) == '-') {
/*      */         
/*  386 */         format = "---%D%z";
/*  387 */       } else if (lexRepLength == 4 || lexRepLength == 5 || lexRepLength == 10) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  392 */         format = "--%M%z";
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  399 */         format = "--%M-%D%z";
/*      */       } 
/*      */     } else {
/*      */       
/*  403 */       int countSeparator = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  408 */       int timezoneOffset = lexRep.indexOf(':');
/*  409 */       if (timezoneOffset != -1)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  415 */         lexRepLength -= 6;
/*      */       }
/*      */       
/*  418 */       for (int i = 1; i < lexRepLength; i++) {
/*  419 */         if (lexRep.charAt(i) == '-') {
/*  420 */           countSeparator++;
/*      */         }
/*      */       } 
/*  423 */       if (countSeparator == 0) {
/*      */         
/*  425 */         format = "%Y%z";
/*  426 */       } else if (countSeparator == 1) {
/*      */         
/*  428 */         format = "%Y-%M%z";
/*      */       }
/*      */       else {
/*      */         
/*  432 */         format = "%Y-%M-%D%z";
/*      */       } 
/*      */     } 
/*  435 */     Parser p = new Parser(format, lexRep);
/*  436 */     p.parse();
/*      */ 
/*      */     
/*  439 */     if (!isValid()) {
/*  440 */       throw new IllegalArgumentException(
/*  441 */           DatatypeMessageFormatter.formatMessage(null, "InvalidXGCRepresentation", new Object[] { lexicalRepresentation }));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLGregorianCalendarImpl(BigInteger year, int month, int day, int hour, int minute, int second, BigDecimal fractionalSecond, int timezone) {
/*  483 */     setYear(year);
/*  484 */     setMonth(month);
/*  485 */     setDay(day);
/*  486 */     setTime(hour, minute, second, fractionalSecond);
/*  487 */     setTimezone(timezone);
/*      */ 
/*      */     
/*  490 */     if (!isValid())
/*      */     {
/*  492 */       throw new IllegalArgumentException(
/*  493 */           DatatypeMessageFormatter.formatMessage(null, "InvalidXGCValue-fractional", new Object[] { year, new Integer(month), new Integer(day), new Integer(hour), new Integer(minute), new Integer(second), fractionalSecond, new Integer(timezone) }));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private XMLGregorianCalendarImpl(int year, int month, int day, int hour, int minute, int second, int millisecond, int timezone) {
/*  554 */     setYear(year);
/*  555 */     setMonth(month);
/*  556 */     setDay(day);
/*  557 */     setTime(hour, minute, second);
/*  558 */     setTimezone(timezone);
/*  559 */     setMillisecond(millisecond);
/*      */     
/*  561 */     if (!isValid())
/*      */     {
/*  563 */       throw new IllegalArgumentException(
/*  564 */           DatatypeMessageFormatter.formatMessage(null, "InvalidXGCValue-milli", new Object[] { new Integer(year), new Integer(month), new Integer(day), new Integer(hour), new Integer(minute), new Integer(second), new Integer(millisecond), new Integer(timezone) }));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLGregorianCalendarImpl(GregorianCalendar cal) {
/*  646 */     int year = cal.get(1);
/*  647 */     if (cal.get(0) == 0) {
/*  648 */       year = -year;
/*      */     }
/*  650 */     setYear(year);
/*      */ 
/*      */ 
/*      */     
/*  654 */     setMonth(cal.get(2) + 1);
/*  655 */     setDay(cal.get(5));
/*  656 */     setTime(cal
/*  657 */         .get(11), cal
/*  658 */         .get(12), cal
/*  659 */         .get(13), cal
/*  660 */         .get(14));
/*      */ 
/*      */     
/*  663 */     int offsetInMinutes = (cal.get(15) + cal.get(16)) / 60000;
/*  664 */     setTimezone(offsetInMinutes);
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
/*      */   public static XMLGregorianCalendar createDateTime(BigInteger year, int month, int day, int hours, int minutes, int seconds, BigDecimal fractionalSecond, int timezone) {
/*  700 */     return new XMLGregorianCalendarImpl(year, month, day, hours, minutes, seconds, fractionalSecond, timezone);
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
/*      */   public static XMLGregorianCalendar createDateTime(int year, int month, int day, int hour, int minute, int second) {
/*  736 */     return new XMLGregorianCalendarImpl(year, month, day, hour, minute, second, -2147483648, -2147483648);
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
/*      */   public static XMLGregorianCalendar createDateTime(int year, int month, int day, int hours, int minutes, int seconds, int milliseconds, int timezone) {
/*  778 */     return new XMLGregorianCalendarImpl(year, month, day, hours, minutes, seconds, milliseconds, timezone);
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
/*      */   public static XMLGregorianCalendar createDate(int year, int month, int day, int timezone) {
/*  815 */     return new XMLGregorianCalendarImpl(year, month, day, -2147483648, -2147483648, -2147483648, -2147483648, timezone);
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
/*      */   public static XMLGregorianCalendar createTime(int hours, int minutes, int seconds, int timezone) {
/*  847 */     return new XMLGregorianCalendarImpl(-2147483648, -2147483648, -2147483648, hours, minutes, seconds, -2147483648, timezone);
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
/*      */   public static XMLGregorianCalendar createTime(int hours, int minutes, int seconds, BigDecimal fractionalSecond, int timezone) {
/*  882 */     return new XMLGregorianCalendarImpl(null, -2147483648, -2147483648, hours, minutes, seconds, fractionalSecond, timezone);
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
/*      */   public static XMLGregorianCalendar createTime(int hours, int minutes, int seconds, int milliseconds, int timezone) {
/*  917 */     return new XMLGregorianCalendarImpl(-2147483648, -2147483648, -2147483648, hours, minutes, seconds, milliseconds, timezone);
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
/*      */   public BigInteger getEon() {
/*  944 */     return this.eon;
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
/*      */   public int getYear() {
/*  960 */     return this.year;
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
/*      */   public BigInteger getEonAndYear() {
/*  981 */     if (this.year != Integer.MIN_VALUE && this.eon != null)
/*      */     {
/*      */       
/*  984 */       return this.eon.add(BigInteger.valueOf(this.year));
/*      */     }
/*      */ 
/*      */     
/*  988 */     if (this.year != Integer.MIN_VALUE && this.eon == null)
/*      */     {
/*      */       
/*  991 */       return BigInteger.valueOf(this.year);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  996 */     return null;
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
/*      */   public int getMonth() {
/* 1009 */     return this.month;
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
/*      */   public int getDay() {
/* 1021 */     return this.day;
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
/*      */   public int getTimezone() {
/* 1034 */     return this.timezone;
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
/*      */   public int getHour() {
/* 1046 */     return this.hour;
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
/*      */   public int getMinute() {
/* 1058 */     return this.minute;
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
/*      */   public int getSecond() {
/* 1080 */     return this.second;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BigDecimal getSeconds() {
/* 1087 */     if (this.second == Integer.MIN_VALUE) {
/* 1088 */       return DECIMAL_ZERO;
/*      */     }
/* 1090 */     BigDecimal result = BigDecimal.valueOf(this.second);
/* 1091 */     if (this.fractionalSecond != null) {
/* 1092 */       return result.add(this.fractionalSecond);
/*      */     }
/* 1094 */     return result;
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
/*      */   public int getMillisecond() {
/* 1119 */     if (this.fractionalSecond == null) {
/* 1120 */       return Integer.MIN_VALUE;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1125 */     return this.fractionalSecond.movePointRight(3).intValue();
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
/*      */   public BigDecimal getFractionalSecond() {
/* 1147 */     return this.fractionalSecond;
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
/*      */   public void setYear(BigInteger year) {
/* 1164 */     if (year == null) {
/* 1165 */       this.eon = null;
/* 1166 */       this.year = Integer.MIN_VALUE;
/*      */     } else {
/* 1168 */       BigInteger temp = year.remainder(BILLION);
/* 1169 */       this.year = temp.intValue();
/* 1170 */       setEon(year.subtract(temp));
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
/*      */   public void setYear(int year) {
/* 1188 */     if (year == Integer.MIN_VALUE) {
/* 1189 */       this.year = Integer.MIN_VALUE;
/* 1190 */       this.eon = null;
/* 1191 */     } else if (Math.abs(year) < BILLION.intValue()) {
/* 1192 */       this.year = year;
/* 1193 */       this.eon = null;
/*      */     } else {
/* 1195 */       BigInteger theYear = BigInteger.valueOf(year);
/* 1196 */       BigInteger remainder = theYear.remainder(BILLION);
/* 1197 */       this.year = remainder.intValue();
/* 1198 */       setEon(theYear.subtract(remainder));
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
/*      */   private void setEon(BigInteger eon) {
/* 1211 */     if (eon != null && eon.compareTo(BigInteger.ZERO) == 0) {
/*      */       
/* 1213 */       this.eon = null;
/*      */     } else {
/* 1215 */       this.eon = eon;
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
/*      */   public void setMonth(int month) {
/* 1231 */     if ((month < 1 || 12 < month) && 
/* 1232 */       month != Integer.MIN_VALUE)
/* 1233 */       invalidFieldValue(1, month); 
/* 1234 */     this.month = month;
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
/*      */   public void setDay(int day) {
/* 1249 */     if ((day < 1 || 31 < day) && 
/* 1250 */       day != Integer.MIN_VALUE)
/* 1251 */       invalidFieldValue(2, day); 
/* 1252 */     this.day = day;
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
/*      */   public void setTimezone(int offset) {
/* 1268 */     if ((offset < -840 || 840 < offset) && 
/* 1269 */       offset != Integer.MIN_VALUE)
/* 1270 */       invalidFieldValue(7, offset); 
/* 1271 */     this.timezone = offset;
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
/*      */   public void setTime(int hour, int minute, int second) {
/* 1291 */     setTime(hour, minute, second, (BigDecimal)null);
/*      */   }
/*      */   
/*      */   private void invalidFieldValue(int field, int value) {
/* 1295 */     throw new IllegalArgumentException(
/* 1296 */         DatatypeMessageFormatter.formatMessage(null, "InvalidFieldValue", new Object[] { new Integer(value), FIELD_NAME[field] }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void testHour() {
/* 1304 */     if (getHour() == 24) {
/* 1305 */       if (getMinute() != 0 || 
/* 1306 */         getSecond() != 0) {
/* 1307 */         invalidFieldValue(3, getHour());
/*      */       }
/*      */ 
/*      */       
/* 1311 */       setHour(0, false);
/* 1312 */       add(new DurationImpl(true, 0, 0, 1, 0, 0, 0));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHour(int hour) {
/* 1318 */     setHour(hour, true);
/*      */   }
/*      */ 
/*      */   
/*      */   private void setHour(int hour, boolean validate) {
/* 1323 */     if ((hour < 0 || hour > 24) && 
/* 1324 */       hour != Integer.MIN_VALUE) {
/* 1325 */       invalidFieldValue(3, hour);
/*      */     }
/*      */ 
/*      */     
/* 1329 */     this.hour = hour;
/*      */     
/* 1331 */     if (validate) {
/* 1332 */       testHour();
/*      */     }
/*      */   }
/*      */   
/*      */   public void setMinute(int minute) {
/* 1337 */     if ((minute < 0 || 59 < minute) && 
/* 1338 */       minute != Integer.MIN_VALUE)
/* 1339 */       invalidFieldValue(4, minute); 
/* 1340 */     this.minute = minute;
/*      */   }
/*      */   
/*      */   public void setSecond(int second) {
/* 1344 */     if ((second < 0 || 60 < second) && 
/* 1345 */       second != Integer.MIN_VALUE)
/* 1346 */       invalidFieldValue(5, second); 
/* 1347 */     this.second = second;
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
/*      */   public void setTime(int hour, int minute, int second, BigDecimal fractional) {
/* 1373 */     setHour(hour, false);
/*      */     
/* 1375 */     setMinute(minute);
/* 1376 */     if (second != 60) {
/* 1377 */       setSecond(second);
/* 1378 */     } else if ((hour == 23 && minute == 59) || (hour == 0 && minute == 0)) {
/* 1379 */       setSecond(second);
/*      */     } else {
/* 1381 */       invalidFieldValue(5, second);
/*      */     } 
/*      */     
/* 1384 */     setFractionalSecond(fractional);
/*      */ 
/*      */     
/* 1387 */     testHour();
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
/*      */   public void setTime(int hour, int minute, int second, int millisecond) {
/* 1409 */     setHour(hour, false);
/*      */     
/* 1411 */     setMinute(minute);
/* 1412 */     if (second != 60) {
/* 1413 */       setSecond(second);
/* 1414 */     } else if ((hour == 23 && minute == 59) || (hour == 0 && minute == 0)) {
/* 1415 */       setSecond(second);
/*      */     } else {
/* 1417 */       invalidFieldValue(5, second);
/*      */     } 
/* 1419 */     setMillisecond(millisecond);
/*      */ 
/*      */     
/* 1422 */     testHour();
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
/*      */   public int compare(XMLGregorianCalendar rhs) {
/* 1449 */     XMLGregorianCalendar lhs = this;
/*      */     
/* 1451 */     int result = 2;
/* 1452 */     XMLGregorianCalendarImpl P = (XMLGregorianCalendarImpl)lhs;
/* 1453 */     XMLGregorianCalendarImpl Q = (XMLGregorianCalendarImpl)rhs;
/*      */     
/* 1455 */     if (P.getTimezone() == Q.getTimezone())
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 1460 */       return internalCompare(P, Q);
/*      */     }
/* 1462 */     if (P.getTimezone() != Integer.MIN_VALUE && Q
/* 1463 */       .getTimezone() != Integer.MIN_VALUE) {
/*      */ 
/*      */ 
/*      */       
/* 1467 */       P = (XMLGregorianCalendarImpl)P.normalize();
/* 1468 */       Q = (XMLGregorianCalendarImpl)Q.normalize();
/* 1469 */       return internalCompare(P, Q);
/* 1470 */     }  if (P.getTimezone() != Integer.MIN_VALUE) {
/*      */       
/* 1472 */       if (P.getTimezone() != 0) {
/* 1473 */         P = (XMLGregorianCalendarImpl)P.normalize();
/*      */       }
/*      */ 
/*      */       
/* 1477 */       XMLGregorianCalendar MinQ = Q.normalizeToTimezone(840);
/* 1478 */       result = internalCompare(P, MinQ);
/* 1479 */       if (result == -1) {
/* 1480 */         return result;
/*      */       }
/*      */ 
/*      */       
/* 1484 */       XMLGregorianCalendar MaxQ = Q.normalizeToTimezone(-840);
/* 1485 */       result = internalCompare(P, MaxQ);
/* 1486 */       if (result == 1) {
/* 1487 */         return result;
/*      */       }
/*      */       
/* 1490 */       return 2;
/*      */     } 
/*      */ 
/*      */     
/* 1494 */     if (Q.getTimezone() != 0) {
/* 1495 */       Q = (XMLGregorianCalendarImpl)Q.normalizeToTimezone(Q.getTimezone());
/*      */     }
/*      */ 
/*      */     
/* 1499 */     XMLGregorianCalendar MaxP = P.normalizeToTimezone(-840);
/* 1500 */     result = internalCompare(MaxP, Q);
/* 1501 */     if (result == -1) {
/* 1502 */       return result;
/*      */     }
/*      */ 
/*      */     
/* 1506 */     XMLGregorianCalendar MinP = P.normalizeToTimezone(840);
/* 1507 */     result = internalCompare(MinP, Q);
/* 1508 */     if (result == 1) {
/* 1509 */       return result;
/*      */     }
/*      */     
/* 1512 */     return 2;
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
/*      */   public XMLGregorianCalendar normalize() {
/* 1525 */     XMLGregorianCalendar normalized = normalizeToTimezone(this.timezone);
/*      */ 
/*      */     
/* 1528 */     if (getTimezone() == Integer.MIN_VALUE) {
/* 1529 */       normalized.setTimezone(-2147483648);
/*      */     }
/*      */ 
/*      */     
/* 1533 */     if (getMillisecond() == Integer.MIN_VALUE) {
/* 1534 */       normalized.setMillisecond(-2147483648);
/*      */     }
/*      */     
/* 1537 */     return normalized;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private XMLGregorianCalendar normalizeToTimezone(int timezone) {
/* 1548 */     int minutes = timezone;
/* 1549 */     XMLGregorianCalendar result = (XMLGregorianCalendar)clone();
/*      */ 
/*      */ 
/*      */     
/* 1553 */     minutes = -minutes;
/* 1554 */     Duration d = new DurationImpl((minutes >= 0), 0, 0, 0, 0, (minutes < 0) ? -minutes : minutes, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1562 */     result.add(d);
/*      */ 
/*      */     
/* 1565 */     result.setTimezone(0);
/* 1566 */     return result;
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
/*      */   private static int internalCompare(XMLGregorianCalendar P, XMLGregorianCalendar Q) {
/* 1589 */     if (P.getEon() == Q.getEon()) {
/*      */ 
/*      */ 
/*      */       
/* 1593 */       int result = compareField(P.getYear(), Q.getYear());
/* 1594 */       if (result != 0) {
/* 1595 */         return result;
/*      */       }
/*      */     } else {
/* 1598 */       int result = compareField(P.getEonAndYear(), Q.getEonAndYear());
/* 1599 */       if (result != 0) {
/* 1600 */         return result;
/*      */       }
/*      */     } 
/*      */     
/* 1604 */     int i = compareField(P.getMonth(), Q.getMonth());
/* 1605 */     if (i != 0) {
/* 1606 */       return i;
/*      */     }
/*      */     
/* 1609 */     i = compareField(P.getDay(), Q.getDay());
/* 1610 */     if (i != 0) {
/* 1611 */       return i;
/*      */     }
/*      */     
/* 1614 */     i = compareField(P.getHour(), Q.getHour());
/* 1615 */     if (i != 0) {
/* 1616 */       return i;
/*      */     }
/*      */     
/* 1619 */     i = compareField(P.getMinute(), Q.getMinute());
/* 1620 */     if (i != 0) {
/* 1621 */       return i;
/*      */     }
/* 1623 */     i = compareField(P.getSecond(), Q.getSecond());
/* 1624 */     if (i != 0) {
/* 1625 */       return i;
/*      */     }
/*      */     
/* 1628 */     i = compareField(P.getFractionalSecond(), Q.getFractionalSecond());
/* 1629 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int compareField(int Pfield, int Qfield) {
/* 1637 */     if (Pfield == Qfield)
/*      */     {
/*      */ 
/*      */       
/* 1641 */       return 0;
/*      */     }
/* 1643 */     if (Pfield == Integer.MIN_VALUE || Qfield == Integer.MIN_VALUE)
/*      */     {
/* 1645 */       return 2;
/*      */     }
/*      */     
/* 1648 */     return (Pfield < Qfield) ? -1 : 1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int compareField(BigInteger Pfield, BigInteger Qfield) {
/* 1654 */     if (Pfield == null) {
/* 1655 */       return (Qfield == null) ? 0 : 2;
/*      */     }
/* 1657 */     if (Qfield == null) {
/* 1658 */       return 2;
/*      */     }
/* 1660 */     return Pfield.compareTo(Qfield);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int compareField(BigDecimal Pfield, BigDecimal Qfield) {
/* 1665 */     if (Pfield == Qfield) {
/* 1666 */       return 0;
/*      */     }
/*      */     
/* 1669 */     if (Pfield == null) {
/* 1670 */       Pfield = DECIMAL_ZERO;
/*      */     }
/*      */     
/* 1673 */     if (Qfield == null) {
/* 1674 */       Qfield = DECIMAL_ZERO;
/*      */     }
/*      */     
/* 1677 */     return Pfield.compareTo(Qfield);
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
/*      */   public boolean equals(Object obj) {
/* 1689 */     if (obj == null || !(obj instanceof XMLGregorianCalendar)) {
/* 1690 */       return false;
/*      */     }
/* 1692 */     return (compare((XMLGregorianCalendar)obj) == 0);
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
/*      */   public int hashCode() {
/* 1707 */     int timezone = getTimezone();
/* 1708 */     if (timezone == Integer.MIN_VALUE) {
/* 1709 */       timezone = 0;
/*      */     }
/* 1711 */     XMLGregorianCalendar gc = this;
/* 1712 */     if (timezone != 0) {
/* 1713 */       gc = normalizeToTimezone(getTimezone());
/*      */     }
/* 1715 */     return gc.getYear() + gc.getMonth() + gc.getDay() + gc
/* 1716 */       .getHour() + gc.getMinute() + gc.getSecond();
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
/*      */   public static XMLGregorianCalendar parse(String lexicalRepresentation) {
/* 1752 */     return new XMLGregorianCalendarImpl(lexicalRepresentation);
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
/*      */   public String toXMLFormat() {
/* 1771 */     QName typekind = getXMLSchemaType();
/*      */     
/* 1773 */     String formatString = null;
/*      */ 
/*      */     
/* 1776 */     if (typekind == DatatypeConstants.DATETIME) {
/* 1777 */       formatString = "%Y-%M-%DT%h:%m:%s%z";
/* 1778 */     } else if (typekind == DatatypeConstants.DATE) {
/* 1779 */       formatString = "%Y-%M-%D%z";
/* 1780 */     } else if (typekind == DatatypeConstants.TIME) {
/* 1781 */       formatString = "%h:%m:%s%z";
/* 1782 */     } else if (typekind == DatatypeConstants.GMONTH) {
/* 1783 */       formatString = "--%M%z";
/* 1784 */     } else if (typekind == DatatypeConstants.GDAY) {
/* 1785 */       formatString = "---%D%z";
/* 1786 */     } else if (typekind == DatatypeConstants.GYEAR) {
/* 1787 */       formatString = "%Y%z";
/* 1788 */     } else if (typekind == DatatypeConstants.GYEARMONTH) {
/* 1789 */       formatString = "%Y-%M%z";
/* 1790 */     } else if (typekind == DatatypeConstants.GMONTHDAY) {
/* 1791 */       formatString = "--%M-%D%z";
/*      */     } 
/* 1793 */     return format(formatString);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QName getXMLSchemaType() {
/* 1909 */     int mask = ((this.year != Integer.MIN_VALUE) ? 32 : 0) | ((this.month != Integer.MIN_VALUE) ? 16 : 0) | ((this.day != Integer.MIN_VALUE) ? 8 : 0) | ((this.hour != Integer.MIN_VALUE) ? 4 : 0) | ((this.minute != Integer.MIN_VALUE) ? 2 : 0) | ((this.second != Integer.MIN_VALUE) ? 1 : 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1917 */     switch (mask) {
/*      */       case 63:
/* 1919 */         return DatatypeConstants.DATETIME;
/*      */       case 56:
/* 1921 */         return DatatypeConstants.DATE;
/*      */       case 7:
/* 1923 */         return DatatypeConstants.TIME;
/*      */       case 48:
/* 1925 */         return DatatypeConstants.GYEARMONTH;
/*      */       case 24:
/* 1927 */         return DatatypeConstants.GMONTHDAY;
/*      */       case 32:
/* 1929 */         return DatatypeConstants.GYEAR;
/*      */       case 16:
/* 1931 */         return DatatypeConstants.GMONTH;
/*      */       case 8:
/* 1933 */         return DatatypeConstants.GDAY;
/*      */     } 
/* 1935 */     throw new IllegalStateException(
/* 1936 */         getClass().getName() + "#getXMLSchemaType() :" + 
/*      */         
/* 1938 */         DatatypeMessageFormatter.formatMessage(null, "InvalidXGCFields", null));
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
/*      */   public boolean isValid() {
/* 1955 */     if (getMonth() == 2) {
/*      */       
/* 1957 */       int maxDays = 29;
/*      */       
/* 1959 */       if (this.eon == null) {
/* 1960 */         if (this.year != Integer.MIN_VALUE)
/* 1961 */           maxDays = maximumDayInMonthFor(this.year, getMonth()); 
/*      */       } else {
/* 1963 */         BigInteger years = getEonAndYear();
/* 1964 */         if (years != null) {
/* 1965 */           maxDays = maximumDayInMonthFor(getEonAndYear(), 2);
/*      */         }
/*      */       } 
/* 1968 */       if (getDay() > maxDays) {
/* 1969 */         return false;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1974 */     if (getHour() == 24) {
/* 1975 */       if (getMinute() != 0)
/* 1976 */         return false; 
/* 1977 */       if (getSecond() != 0) {
/* 1978 */         return false;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1986 */     if (this.eon == null) {
/*      */       
/* 1988 */       if (this.year == 0) {
/* 1989 */         return false;
/*      */       }
/*      */     } else {
/* 1992 */       BigInteger yearField = getEonAndYear();
/* 1993 */       if (yearField != null) {
/* 1994 */         int result = compareField(yearField, BigInteger.ZERO);
/* 1995 */         if (result == 0) {
/* 1996 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/* 2000 */     return true;
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
/*      */   public void add(Duration duration) {
/*      */     BigDecimal startSeconds;
/*      */     BigInteger tempDays;
/* 2043 */     boolean[] fieldUndefined = { false, false, false, false, false, false };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2052 */     int signum = duration.getSign();
/*      */     
/* 2054 */     int startMonth = getMonth();
/* 2055 */     if (startMonth == Integer.MIN_VALUE) {
/* 2056 */       startMonth = 1;
/* 2057 */       fieldUndefined[1] = true;
/*      */     } 
/*      */     
/* 2060 */     BigInteger dMonths = sanitize(duration.getField(DatatypeConstants.MONTHS), signum);
/* 2061 */     BigInteger temp = BigInteger.valueOf(startMonth).add(dMonths);
/* 2062 */     setMonth(temp.subtract(BigInteger.ONE).mod(TWELVE).intValue() + 1);
/*      */     
/* 2064 */     BigInteger carry = (new BigDecimal(temp.subtract(BigInteger.ONE))).divide(new BigDecimal(TWELVE), 3).toBigInteger();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2069 */     BigInteger startYear = getEonAndYear();
/* 2070 */     if (startYear == null) {
/* 2071 */       fieldUndefined[0] = true;
/* 2072 */       startYear = BigInteger.ZERO;
/*      */     } 
/* 2074 */     BigInteger dYears = sanitize(duration.getField(DatatypeConstants.YEARS), signum);
/* 2075 */     BigInteger endYear = startYear.add(dYears).add(carry);
/* 2076 */     setYear(endYear);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2090 */     if (getSecond() == Integer.MIN_VALUE) {
/* 2091 */       fieldUndefined[5] = true;
/* 2092 */       startSeconds = DECIMAL_ZERO;
/*      */     } else {
/*      */       
/* 2095 */       startSeconds = getSeconds();
/*      */     } 
/*      */ 
/*      */     
/* 2099 */     BigDecimal dSeconds = DurationImpl.sanitize((BigDecimal)duration.getField(DatatypeConstants.SECONDS), signum);
/* 2100 */     BigDecimal tempBD = startSeconds.add(dSeconds);
/*      */     
/* 2102 */     BigDecimal fQuotient = new BigDecimal((new BigDecimal(tempBD.toBigInteger())).divide(DECIMAL_SIXTY, 3).toBigInteger());
/* 2103 */     BigDecimal endSeconds = tempBD.subtract(fQuotient.multiply(DECIMAL_SIXTY));
/*      */     
/* 2105 */     carry = fQuotient.toBigInteger();
/* 2106 */     setSecond(endSeconds.intValue());
/* 2107 */     BigDecimal tempFracSeconds = endSeconds.subtract(new BigDecimal(BigInteger.valueOf(getSecond())));
/* 2108 */     if (tempFracSeconds.compareTo(DECIMAL_ZERO) < 0) {
/* 2109 */       setFractionalSecond(DECIMAL_ONE.add(tempFracSeconds));
/* 2110 */       if (getSecond() == 0) {
/* 2111 */         setSecond(59);
/* 2112 */         carry = carry.subtract(BigInteger.ONE);
/*      */       } else {
/* 2114 */         setSecond(getSecond() - 1);
/*      */       } 
/*      */     } else {
/* 2117 */       setFractionalSecond(tempFracSeconds);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2125 */     int startMinutes = getMinute();
/* 2126 */     if (startMinutes == Integer.MIN_VALUE) {
/* 2127 */       fieldUndefined[4] = true;
/* 2128 */       startMinutes = 0;
/*      */     } 
/* 2130 */     BigInteger dMinutes = sanitize(duration.getField(DatatypeConstants.MINUTES), signum);
/*      */     
/* 2132 */     temp = BigInteger.valueOf(startMinutes).add(dMinutes).add(carry);
/* 2133 */     setMinute(temp.mod(SIXTY).intValue());
/* 2134 */     carry = (new BigDecimal(temp)).divide(DECIMAL_SIXTY, 3).toBigInteger();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2141 */     int startHours = getHour();
/* 2142 */     if (startHours == Integer.MIN_VALUE) {
/* 2143 */       fieldUndefined[3] = true;
/* 2144 */       startHours = 0;
/*      */     } 
/* 2146 */     BigInteger dHours = sanitize(duration.getField(DatatypeConstants.HOURS), signum);
/*      */     
/* 2148 */     temp = BigInteger.valueOf(startHours).add(dHours).add(carry);
/* 2149 */     setHour(temp.mod(TWENTY_FOUR).intValue(), false);
/* 2150 */     carry = (new BigDecimal(temp)).divide(new BigDecimal(TWENTY_FOUR), 3).toBigInteger();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2176 */     int startDay = getDay();
/* 2177 */     if (startDay == Integer.MIN_VALUE) {
/* 2178 */       fieldUndefined[2] = true;
/* 2179 */       startDay = 1;
/*      */     } 
/* 2181 */     BigInteger dDays = sanitize(duration.getField(DatatypeConstants.DAYS), signum);
/* 2182 */     int maxDayInMonth = maximumDayInMonthFor(getEonAndYear(), getMonth());
/* 2183 */     if (startDay > maxDayInMonth) {
/* 2184 */       tempDays = BigInteger.valueOf(maxDayInMonth);
/* 2185 */     } else if (startDay < 1) {
/* 2186 */       tempDays = BigInteger.ONE;
/*      */     } else {
/* 2188 */       tempDays = BigInteger.valueOf(startDay);
/*      */     } 
/* 2190 */     BigInteger endDays = tempDays.add(dDays).add(carry);
/*      */     
/*      */     while (true) {
/*      */       int monthCarry, quotient;
/* 2194 */       if (endDays.compareTo(BigInteger.ONE) < 0) {
/*      */         
/* 2196 */         BigInteger mdimf = null;
/* 2197 */         if (this.month >= 2) {
/* 2198 */           mdimf = BigInteger.valueOf(maximumDayInMonthFor(getEonAndYear(), getMonth() - 1));
/*      */         } else {
/*      */           
/* 2201 */           mdimf = BigInteger.valueOf(maximumDayInMonthFor(getEonAndYear().subtract(BigInteger.valueOf(1L)), 12));
/*      */         } 
/* 2203 */         endDays = endDays.add(mdimf);
/* 2204 */         monthCarry = -1;
/* 2205 */       } else if (endDays.compareTo(BigInteger.valueOf(maximumDayInMonthFor(getEonAndYear(), getMonth()))) > 0) {
/* 2206 */         endDays = endDays.add(BigInteger.valueOf(-maximumDayInMonthFor(getEonAndYear(), getMonth())));
/* 2207 */         monthCarry = 1;
/*      */       } else {
/*      */         break;
/*      */       } 
/*      */       
/* 2212 */       int intTemp = getMonth() + monthCarry;
/* 2213 */       int endMonth = (intTemp - 1) % 12;
/*      */       
/* 2215 */       if (endMonth < 0) {
/* 2216 */         endMonth = 12 + endMonth + 1;
/* 2217 */         quotient = (new BigDecimal(intTemp - 1)).divide(new BigDecimal(TWELVE), 0).intValue();
/*      */       } else {
/* 2219 */         quotient = (intTemp - 1) / 12;
/* 2220 */         endMonth++;
/*      */       } 
/* 2222 */       setMonth(endMonth);
/* 2223 */       if (quotient != 0) {
/* 2224 */         setYear(getEonAndYear().add(BigInteger.valueOf(quotient)));
/*      */       }
/*      */     } 
/* 2227 */     setDay(endDays.intValue());
/*      */ 
/*      */     
/* 2230 */     for (int i = 0; i <= 5; i++) {
/* 2231 */       if (fieldUndefined[i]) {
/* 2232 */         switch (i) {
/*      */           case 0:
/* 2234 */             setYear(-2147483648);
/*      */             break;
/*      */           case 1:
/* 2237 */             setMonth(-2147483648);
/*      */             break;
/*      */           case 2:
/* 2240 */             setDay(-2147483648);
/*      */             break;
/*      */           case 3:
/* 2243 */             setHour(-2147483648, false);
/*      */             break;
/*      */           case 4:
/* 2246 */             setMinute(-2147483648);
/*      */             break;
/*      */           case 5:
/* 2249 */             setSecond(-2147483648);
/* 2250 */             setFractionalSecond(null);
/*      */             break;
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/* 2257 */   private static final BigInteger FOUR = BigInteger.valueOf(4L);
/* 2258 */   private static final BigInteger HUNDRED = BigInteger.valueOf(100L);
/* 2259 */   private static final BigInteger FOUR_HUNDRED = BigInteger.valueOf(400L);
/* 2260 */   private static final BigInteger SIXTY = BigInteger.valueOf(60L);
/* 2261 */   private static final BigInteger TWENTY_FOUR = BigInteger.valueOf(24L);
/* 2262 */   private static final BigInteger TWELVE = BigInteger.valueOf(12L);
/* 2263 */   private static final BigDecimal DECIMAL_ZERO = new BigDecimal("0");
/* 2264 */   private static final BigDecimal DECIMAL_ONE = new BigDecimal("1");
/* 2265 */   private static final BigDecimal DECIMAL_SIXTY = new BigDecimal("60");
/*      */ 
/*      */   
/* 2268 */   private static int[] daysInMonth = new int[] { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
/*      */ 
/*      */ 
/*      */   
/*      */   private static int maximumDayInMonthFor(BigInteger year, int month) {
/* 2273 */     if (month != 2) {
/* 2274 */       return daysInMonth[month];
/*      */     }
/* 2276 */     if (year.mod(FOUR_HUNDRED).equals(BigInteger.ZERO) || (
/* 2277 */       !year.mod(HUNDRED).equals(BigInteger.ZERO) && year
/* 2278 */       .mod(FOUR).equals(BigInteger.ZERO)))
/*      */     {
/* 2280 */       return 29;
/*      */     }
/* 2282 */     return daysInMonth[month];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int maximumDayInMonthFor(int year, int month) {
/* 2288 */     if (month != 2) {
/* 2289 */       return daysInMonth[month];
/*      */     }
/* 2291 */     if (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
/*      */     {
/*      */       
/* 2294 */       return 29;
/*      */     }
/* 2296 */     return daysInMonth[2];
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
/*      */   
/*      */   public GregorianCalendar toGregorianCalendar() {
/* 2395 */     GregorianCalendar result = null;
/* 2396 */     int DEFAULT_TIMEZONE_OFFSET = Integer.MIN_VALUE;
/* 2397 */     TimeZone tz = getTimeZone(-2147483648);
/*      */ 
/*      */ 
/*      */     
/* 2401 */     Locale locale = getDefaultLocale();
/*      */     
/* 2403 */     result = new GregorianCalendar(tz, locale);
/* 2404 */     result.clear();
/* 2405 */     result.setGregorianChange(PURE_GREGORIAN_CHANGE);
/*      */ 
/*      */     
/* 2408 */     BigInteger year = getEonAndYear();
/* 2409 */     if (year != null) {
/* 2410 */       result.set(0, (year.signum() == -1) ? 0 : 1);
/* 2411 */       result.set(1, year.abs().intValue());
/*      */     } 
/*      */ 
/*      */     
/* 2415 */     if (this.month != Integer.MIN_VALUE)
/*      */     {
/* 2417 */       result.set(2, this.month - 1);
/*      */     }
/*      */ 
/*      */     
/* 2421 */     if (this.day != Integer.MIN_VALUE) {
/* 2422 */       result.set(5, this.day);
/*      */     }
/*      */ 
/*      */     
/* 2426 */     if (this.hour != Integer.MIN_VALUE) {
/* 2427 */       result.set(11, this.hour);
/*      */     }
/*      */ 
/*      */     
/* 2431 */     if (this.minute != Integer.MIN_VALUE) {
/* 2432 */       result.set(12, this.minute);
/*      */     }
/*      */ 
/*      */     
/* 2436 */     if (this.second != Integer.MIN_VALUE) {
/* 2437 */       result.set(13, this.second);
/*      */     }
/*      */ 
/*      */     
/* 2441 */     if (this.fractionalSecond != null) {
/* 2442 */       result.set(14, getMillisecond());
/*      */     }
/*      */     
/* 2445 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Locale getDefaultLocale() {
/* 2454 */     String lang = SecuritySupport.getSystemProperty("user.language.format");
/* 2455 */     String country = SecuritySupport.getSystemProperty("user.country.format");
/* 2456 */     String variant = SecuritySupport.getSystemProperty("user.variant.format");
/* 2457 */     Locale locale = null;
/* 2458 */     if (lang != null) {
/* 2459 */       if (country != null) {
/* 2460 */         if (variant != null) {
/* 2461 */           locale = new Locale(lang, country, variant);
/*      */         } else {
/* 2463 */           locale = new Locale(lang, country);
/*      */         } 
/*      */       } else {
/* 2466 */         locale = new Locale(lang);
/*      */       } 
/*      */     }
/* 2469 */     if (locale == null) {
/* 2470 */       locale = Locale.getDefault();
/*      */     }
/* 2472 */     return locale;
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
/*      */   public GregorianCalendar toGregorianCalendar(TimeZone timezone, Locale aLocale, XMLGregorianCalendar defaults) {
/* 2530 */     GregorianCalendar result = null;
/* 2531 */     TimeZone tz = timezone;
/* 2532 */     if (tz == null) {
/* 2533 */       int defaultZoneoffset = Integer.MIN_VALUE;
/* 2534 */       if (defaults != null) {
/* 2535 */         defaultZoneoffset = defaults.getTimezone();
/*      */       }
/* 2537 */       tz = getTimeZone(defaultZoneoffset);
/*      */     } 
/* 2539 */     if (aLocale == null) {
/* 2540 */       aLocale = Locale.getDefault();
/*      */     }
/* 2542 */     result = new GregorianCalendar(tz, aLocale);
/* 2543 */     result.clear();
/* 2544 */     result.setGregorianChange(PURE_GREGORIAN_CHANGE);
/*      */ 
/*      */     
/* 2547 */     BigInteger year = getEonAndYear();
/* 2548 */     if (year != null) {
/* 2549 */       result.set(0, (year.signum() == -1) ? 0 : 1);
/* 2550 */       result.set(1, year.abs().intValue());
/*      */     } else {
/*      */       
/* 2553 */       BigInteger defaultYear = (defaults != null) ? defaults.getEonAndYear() : null;
/* 2554 */       if (defaultYear != null) {
/* 2555 */         result.set(0, (defaultYear.signum() == -1) ? 0 : 1);
/* 2556 */         result.set(1, defaultYear.abs().intValue());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2561 */     if (this.month != Integer.MIN_VALUE) {
/*      */       
/* 2563 */       result.set(2, this.month - 1);
/*      */     } else {
/*      */       
/* 2566 */       int defaultMonth = (defaults != null) ? defaults.getMonth() : Integer.MIN_VALUE;
/* 2567 */       if (defaultMonth != Integer.MIN_VALUE)
/*      */       {
/* 2569 */         result.set(2, defaultMonth - 1);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2574 */     if (this.day != Integer.MIN_VALUE) {
/* 2575 */       result.set(5, this.day);
/*      */     } else {
/*      */       
/* 2578 */       int defaultDay = (defaults != null) ? defaults.getDay() : Integer.MIN_VALUE;
/* 2579 */       if (defaultDay != Integer.MIN_VALUE) {
/* 2580 */         result.set(5, defaultDay);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2585 */     if (this.hour != Integer.MIN_VALUE) {
/* 2586 */       result.set(11, this.hour);
/*      */     } else {
/*      */       
/* 2589 */       int defaultHour = (defaults != null) ? defaults.getHour() : Integer.MIN_VALUE;
/* 2590 */       if (defaultHour != Integer.MIN_VALUE) {
/* 2591 */         result.set(11, defaultHour);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2596 */     if (this.minute != Integer.MIN_VALUE) {
/* 2597 */       result.set(12, this.minute);
/*      */     } else {
/*      */       
/* 2600 */       int defaultMinute = (defaults != null) ? defaults.getMinute() : Integer.MIN_VALUE;
/* 2601 */       if (defaultMinute != Integer.MIN_VALUE) {
/* 2602 */         result.set(12, defaultMinute);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2607 */     if (this.second != Integer.MIN_VALUE) {
/* 2608 */       result.set(13, this.second);
/*      */     } else {
/*      */       
/* 2611 */       int defaultSecond = (defaults != null) ? defaults.getSecond() : Integer.MIN_VALUE;
/* 2612 */       if (defaultSecond != Integer.MIN_VALUE) {
/* 2613 */         result.set(13, defaultSecond);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2618 */     if (this.fractionalSecond != null) {
/* 2619 */       result.set(14, getMillisecond());
/*      */     } else {
/*      */       
/* 2622 */       BigDecimal defaultFractionalSecond = (defaults != null) ? defaults.getFractionalSecond() : null;
/* 2623 */       if (defaultFractionalSecond != null) {
/* 2624 */         result.set(14, defaults.getMillisecond());
/*      */       }
/*      */     } 
/*      */     
/* 2628 */     return result;
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
/*      */   public TimeZone getTimeZone(int defaultZoneoffset) {
/* 2648 */     TimeZone result = null;
/* 2649 */     int zoneoffset = getTimezone();
/*      */     
/* 2651 */     if (zoneoffset == Integer.MIN_VALUE) {
/* 2652 */       zoneoffset = defaultZoneoffset;
/*      */     }
/* 2654 */     if (zoneoffset == Integer.MIN_VALUE) {
/* 2655 */       result = TimeZone.getDefault();
/*      */     } else {
/*      */       
/* 2658 */       char sign = (zoneoffset < 0) ? '-' : '+';
/* 2659 */       if (sign == '-') {
/* 2660 */         zoneoffset = -zoneoffset;
/*      */       }
/* 2662 */       int hour = zoneoffset / 60;
/* 2663 */       int minutes = zoneoffset - hour * 60;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2670 */       StringBuffer customTimezoneId = new StringBuffer(8);
/* 2671 */       customTimezoneId.append("GMT");
/* 2672 */       customTimezoneId.append(sign);
/* 2673 */       customTimezoneId.append(hour);
/* 2674 */       if (minutes != 0) {
/* 2675 */         if (minutes < 10) {
/* 2676 */           customTimezoneId.append('0');
/*      */         }
/* 2678 */         customTimezoneId.append(minutes);
/*      */       } 
/* 2680 */       result = TimeZone.getTimeZone(customTimezoneId.toString());
/*      */     } 
/* 2682 */     return result;
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
/* 2693 */     return new XMLGregorianCalendarImpl(getEonAndYear(), this.month, this.day, this.hour, this.minute, this.second, this.fractionalSecond, this.timezone);
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
/*      */   public void clear() {
/* 2707 */     this.eon = null;
/* 2708 */     this.year = Integer.MIN_VALUE;
/* 2709 */     this.month = Integer.MIN_VALUE;
/* 2710 */     this.day = Integer.MIN_VALUE;
/* 2711 */     this.timezone = Integer.MIN_VALUE;
/* 2712 */     this.hour = Integer.MIN_VALUE;
/* 2713 */     this.minute = Integer.MIN_VALUE;
/* 2714 */     this.second = Integer.MIN_VALUE;
/* 2715 */     this.fractionalSecond = null;
/*      */   }
/*      */   
/*      */   public void setMillisecond(int millisecond) {
/* 2719 */     if (millisecond == Integer.MIN_VALUE) {
/* 2720 */       this.fractionalSecond = null;
/*      */     } else {
/* 2722 */       if ((millisecond < 0 || 999 < millisecond) && 
/* 2723 */         millisecond != Integer.MIN_VALUE)
/* 2724 */         invalidFieldValue(6, millisecond); 
/* 2725 */       this.fractionalSecond = (new BigDecimal(millisecond)).movePointLeft(3);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setFractionalSecond(BigDecimal fractional) {
/* 2730 */     if (fractional != null && (
/* 2731 */       fractional.compareTo(DECIMAL_ZERO) < 0 || fractional
/* 2732 */       .compareTo(DECIMAL_ONE) > 0)) {
/* 2733 */       throw new IllegalArgumentException(DatatypeMessageFormatter.formatMessage(null, "InvalidFractional", new Object[] { fractional
/* 2734 */               .toString() }));
/*      */     }
/*      */     
/* 2737 */     this.fractionalSecond = fractional;
/*      */   }
/*      */ 
/*      */   
/*      */   private final class Parser
/*      */   {
/*      */     private final String format;
/*      */     private final String value;
/*      */     private final int flen;
/*      */     private final int vlen;
/*      */     private int fidx;
/*      */     private int vidx;
/*      */     
/*      */     private Parser(String format, String value) {
/* 2751 */       this.format = format;
/* 2752 */       this.value = value;
/* 2753 */       this.flen = format.length();
/* 2754 */       this.vlen = value.length();
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
/*      */     public void parse() throws IllegalArgumentException {
/* 2766 */       while (this.fidx < this.flen) {
/* 2767 */         char vch, fch = this.format.charAt(this.fidx++);
/*      */         
/* 2769 */         if (fch != '%') {
/* 2770 */           skip(fch);
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 2775 */         switch (this.format.charAt(this.fidx++)) {
/*      */           case 'Y':
/* 2777 */             parseAndSetYear(4);
/*      */             continue;
/*      */           
/*      */           case 'M':
/* 2781 */             XMLGregorianCalendarImpl.this.setMonth(parseInt(2, 2));
/*      */             continue;
/*      */           
/*      */           case 'D':
/* 2785 */             XMLGregorianCalendarImpl.this.setDay(parseInt(2, 2));
/*      */             continue;
/*      */           
/*      */           case 'h':
/* 2789 */             XMLGregorianCalendarImpl.this.setHour(parseInt(2, 2), false);
/*      */             continue;
/*      */           
/*      */           case 'm':
/* 2793 */             XMLGregorianCalendarImpl.this.setMinute(parseInt(2, 2));
/*      */             continue;
/*      */           
/*      */           case 's':
/* 2797 */             XMLGregorianCalendarImpl.this.setSecond(parseInt(2, 2));
/*      */             
/* 2799 */             if (peek() == '.') {
/* 2800 */               XMLGregorianCalendarImpl.this.setFractionalSecond(parseBigDecimal());
/*      */             }
/*      */             continue;
/*      */           
/*      */           case 'z':
/* 2805 */             vch = peek();
/* 2806 */             if (vch == 'Z') {
/* 2807 */               this.vidx++;
/* 2808 */               XMLGregorianCalendarImpl.this.setTimezone(0); continue;
/* 2809 */             }  if (vch == '+' || vch == '-') {
/* 2810 */               this.vidx++;
/* 2811 */               int h = parseInt(2, 2);
/* 2812 */               skip(':');
/* 2813 */               int m = parseInt(2, 2);
/* 2814 */               XMLGregorianCalendarImpl.this.setTimezone((h * 60 + m) * ((vch == '+') ? 1 : -1));
/*      */             } 
/*      */             continue;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2821 */         throw new InternalError();
/*      */       } 
/*      */ 
/*      */       
/* 2825 */       if (this.vidx != this.vlen)
/*      */       {
/* 2827 */         throw new IllegalArgumentException(this.value);
/*      */       }
/* 2829 */       XMLGregorianCalendarImpl.this.testHour();
/*      */     }
/*      */     
/*      */     private char peek() throws IllegalArgumentException {
/* 2833 */       if (this.vidx == this.vlen) {
/* 2834 */         return Character.MAX_VALUE;
/*      */       }
/* 2836 */       return this.value.charAt(this.vidx);
/*      */     }
/*      */     
/*      */     private char read() throws IllegalArgumentException {
/* 2840 */       if (this.vidx == this.vlen) {
/* 2841 */         throw new IllegalArgumentException(this.value);
/*      */       }
/* 2843 */       return this.value.charAt(this.vidx++);
/*      */     }
/*      */     
/*      */     private void skip(char ch) throws IllegalArgumentException {
/* 2847 */       if (read() != ch) {
/* 2848 */         throw new IllegalArgumentException(this.value);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private int parseInt(int minDigits, int maxDigits) throws IllegalArgumentException {
/* 2855 */       int n = 0;
/*      */       
/* 2857 */       int vstart = this.vidx; char ch;
/* 2858 */       while (XMLGregorianCalendarImpl.isDigit(ch = peek()) && this.vidx - vstart <= maxDigits) {
/* 2859 */         this.vidx++;
/* 2860 */         n = n * 10 + ch - 48;
/*      */       } 
/* 2862 */       if (this.vidx - vstart < minDigits)
/*      */       {
/* 2864 */         throw new IllegalArgumentException(this.value);
/*      */       }
/*      */       
/* 2867 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     private void parseAndSetYear(int minDigits) throws IllegalArgumentException {
/* 2872 */       int vstart = this.vidx;
/* 2873 */       int n = 0;
/* 2874 */       boolean neg = false;
/*      */ 
/*      */       
/* 2877 */       if (peek() == '-') {
/* 2878 */         this.vidx++;
/* 2879 */         neg = true;
/*      */       } 
/*      */       while (true) {
/* 2882 */         char ch = peek();
/* 2883 */         if (!XMLGregorianCalendarImpl.isDigit(ch))
/*      */           break; 
/* 2885 */         this.vidx++;
/* 2886 */         n = n * 10 + ch - 48;
/*      */       } 
/*      */       
/* 2889 */       if (this.vidx - vstart < minDigits)
/*      */       {
/* 2891 */         throw new IllegalArgumentException(this.value);
/*      */       }
/*      */       
/* 2894 */       if (this.vidx - vstart < 7) {
/*      */ 
/*      */         
/* 2897 */         if (neg) n = -n; 
/* 2898 */         XMLGregorianCalendarImpl.this.year = n;
/* 2899 */         XMLGregorianCalendarImpl.this.eon = null;
/*      */       } else {
/* 2901 */         XMLGregorianCalendarImpl.this.setYear(new BigInteger(this.value.substring(vstart, this.vidx)));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private BigDecimal parseBigDecimal() throws IllegalArgumentException {
/* 2907 */       int vstart = this.vidx;
/*      */       
/* 2909 */       if (peek() == '.') {
/* 2910 */         this.vidx++;
/*      */       } else {
/* 2912 */         throw new IllegalArgumentException(this.value);
/*      */       } 
/* 2914 */       while (XMLGregorianCalendarImpl.isDigit(peek())) {
/* 2915 */         this.vidx++;
/*      */       }
/* 2917 */       return new BigDecimal(this.value.substring(vstart, this.vidx));
/*      */     }
/*      */   }
/*      */   
/*      */   private static boolean isDigit(char ch) {
/* 2922 */     return ('0' <= ch && ch <= '9');
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
/*      */   private String format(String format) {
/* 2939 */     char[] buf = new char[32];
/* 2940 */     int bufPtr = 0;
/*      */     
/* 2942 */     int fidx = 0, flen = format.length();
/*      */     
/* 2944 */     while (fidx < flen) {
/* 2945 */       String s; int offset; char[] n; int i; char fch = format.charAt(fidx++);
/* 2946 */       if (fch != '%') {
/* 2947 */         buf[bufPtr++] = fch;
/*      */         
/*      */         continue;
/*      */       } 
/* 2951 */       switch (format.charAt(fidx++)) {
/*      */         case 'Y':
/* 2953 */           if (this.eon == null) {
/*      */             
/* 2955 */             int y = getYear();
/* 2956 */             if (y < 0) {
/* 2957 */               buf[bufPtr++] = '-';
/* 2958 */               y = -y;
/*      */             } 
/* 2960 */             bufPtr = print4Number(buf, bufPtr, y); continue;
/*      */           } 
/* 2962 */           s = getEonAndYear().toString();
/*      */           
/* 2964 */           n = new char[buf.length + s.length()];
/* 2965 */           System.arraycopy(buf, 0, n, 0, bufPtr);
/* 2966 */           buf = n;
/* 2967 */           for (i = s.length(); i < 4; i++)
/* 2968 */             buf[bufPtr++] = '0'; 
/* 2969 */           s.getChars(0, s.length(), buf, bufPtr);
/* 2970 */           bufPtr += s.length();
/*      */           continue;
/*      */         
/*      */         case 'M':
/* 2974 */           bufPtr = print2Number(buf, bufPtr, getMonth());
/*      */           continue;
/*      */         case 'D':
/* 2977 */           bufPtr = print2Number(buf, bufPtr, getDay());
/*      */           continue;
/*      */         case 'h':
/* 2980 */           bufPtr = print2Number(buf, bufPtr, getHour());
/*      */           continue;
/*      */         case 'm':
/* 2983 */           bufPtr = print2Number(buf, bufPtr, getMinute());
/*      */           continue;
/*      */         case 's':
/* 2986 */           bufPtr = print2Number(buf, bufPtr, getSecond());
/* 2987 */           if (getFractionalSecond() != null) {
/*      */             
/* 2989 */             String frac = getFractionalSecond().toString();
/*      */             
/* 2991 */             int pos = frac.indexOf("E-");
/* 2992 */             if (pos >= 0) {
/* 2993 */               String zeros = frac.substring(pos + 2);
/* 2994 */               frac = frac.substring(0, pos);
/* 2995 */               pos = frac.indexOf(".");
/* 2996 */               if (pos >= 0) {
/* 2997 */                 frac = frac.substring(0, pos) + frac.substring(pos + 1);
/*      */               }
/* 2999 */               int count = Integer.parseInt(zeros);
/* 3000 */               if (count < 40) {
/* 3001 */                 frac = "00000000000000000000000000000000000000000".substring(0, count - 1) + frac;
/*      */               } else {
/*      */                 
/* 3004 */                 while (count > 1) {
/* 3005 */                   frac = "0" + frac;
/* 3006 */                   count--;
/*      */                 } 
/*      */               } 
/* 3009 */               frac = "0." + frac;
/*      */             } 
/*      */ 
/*      */             
/* 3013 */             char[] arrayOfChar = new char[buf.length + frac.length()];
/* 3014 */             System.arraycopy(buf, 0, arrayOfChar, 0, bufPtr);
/* 3015 */             buf = arrayOfChar;
/*      */             
/* 3017 */             frac.getChars(1, frac.length(), buf, bufPtr);
/* 3018 */             bufPtr += frac.length() - 1;
/*      */           } 
/*      */           continue;
/*      */         case 'z':
/* 3022 */           offset = getTimezone();
/* 3023 */           if (offset == 0) {
/* 3024 */             buf[bufPtr++] = 'Z'; continue;
/*      */           } 
/* 3026 */           if (offset != Integer.MIN_VALUE) {
/* 3027 */             if (offset < 0) {
/* 3028 */               buf[bufPtr++] = '-';
/* 3029 */               offset *= -1;
/*      */             } else {
/* 3031 */               buf[bufPtr++] = '+';
/*      */             } 
/* 3033 */             bufPtr = print2Number(buf, bufPtr, offset / 60);
/* 3034 */             buf[bufPtr++] = ':';
/* 3035 */             bufPtr = print2Number(buf, bufPtr, offset % 60);
/*      */           } 
/*      */           continue;
/*      */       } 
/* 3039 */       throw new InternalError();
/*      */     } 
/*      */ 
/*      */     
/* 3043 */     return new String(buf, 0, bufPtr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int print2Number(char[] out, int bufptr, int number) {
/* 3053 */     out[bufptr++] = (char)(48 + number / 10);
/* 3054 */     out[bufptr++] = (char)(48 + number % 10);
/* 3055 */     return bufptr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int print4Number(char[] out, int bufptr, int number) {
/* 3065 */     out[bufptr + 3] = (char)(48 + number % 10);
/* 3066 */     number /= 10;
/* 3067 */     out[bufptr + 2] = (char)(48 + number % 10);
/* 3068 */     number /= 10;
/* 3069 */     out[bufptr + 1] = (char)(48 + number % 10);
/* 3070 */     number /= 10;
/* 3071 */     out[bufptr] = (char)(48 + number % 10);
/* 3072 */     return bufptr + 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static BigInteger sanitize(Number value, int signum) {
/* 3081 */     if (signum == 0 || value == null) {
/* 3082 */       return BigInteger.ZERO;
/*      */     }
/* 3084 */     return (signum < 0) ? ((BigInteger)value).negate() : (BigInteger)value;
/*      */   }
/*      */   
/*      */   public void reset() {}
/*      */   
/*      */   public XMLGregorianCalendarImpl() {}
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/datatype/XMLGregorianCalendarImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */