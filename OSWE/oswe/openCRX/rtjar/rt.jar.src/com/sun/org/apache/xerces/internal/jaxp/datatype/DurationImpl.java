/*      */ package com.sun.org.apache.xerces.internal.jaxp.datatype;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.util.DatatypeMessageFormatter;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectStreamException;
/*      */ import java.io.Serializable;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.TimeZone;
/*      */ import javax.xml.datatype.DatatypeConstants;
/*      */ import javax.xml.datatype.Duration;
/*      */ import javax.xml.datatype.XMLGregorianCalendar;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class DurationImpl
/*      */   extends Duration
/*      */   implements Serializable
/*      */ {
/*      */   private static final int FIELD_NUM = 6;
/*  117 */   private static final DatatypeConstants.Field[] FIELDS = new DatatypeConstants.Field[] { DatatypeConstants.YEARS, DatatypeConstants.MONTHS, DatatypeConstants.DAYS, DatatypeConstants.HOURS, DatatypeConstants.MINUTES, DatatypeConstants.SECONDS };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  129 */   private static final int[] FIELD_IDS = new int[] { DatatypeConstants.YEARS
/*  130 */       .getId(), DatatypeConstants.MONTHS
/*  131 */       .getId(), DatatypeConstants.DAYS
/*  132 */       .getId(), DatatypeConstants.HOURS
/*  133 */       .getId(), DatatypeConstants.MINUTES
/*  134 */       .getId(), DatatypeConstants.SECONDS
/*  135 */       .getId() };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  141 */   private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  146 */   private static final BigDecimal ZERO = BigDecimal.valueOf(0L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int signum;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BigInteger years;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BigInteger months;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BigInteger days;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BigInteger hours;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BigInteger minutes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BigDecimal seconds;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSign() {
/*  198 */     return this.signum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int calcSignum(boolean isPositive) {
/*  208 */     if ((this.years == null || this.years.signum() == 0) && (this.months == null || this.months
/*  209 */       .signum() == 0) && (this.days == null || this.days
/*  210 */       .signum() == 0) && (this.hours == null || this.hours
/*  211 */       .signum() == 0) && (this.minutes == null || this.minutes
/*  212 */       .signum() == 0) && (this.seconds == null || this.seconds
/*  213 */       .signum() == 0)) {
/*  214 */       return 0;
/*      */     }
/*      */     
/*  217 */     if (isPositive) {
/*  218 */       return 1;
/*      */     }
/*  220 */     return -1;
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
/*      */   protected DurationImpl(boolean isPositive, BigInteger years, BigInteger months, BigInteger days, BigInteger hours, BigInteger minutes, BigDecimal seconds) {
/*  254 */     this.years = years;
/*  255 */     this.months = months;
/*  256 */     this.days = days;
/*  257 */     this.hours = hours;
/*  258 */     this.minutes = minutes;
/*  259 */     this.seconds = seconds;
/*      */     
/*  261 */     this.signum = calcSignum(isPositive);
/*      */ 
/*      */     
/*  264 */     if (years == null && months == null && days == null && hours == null && minutes == null && seconds == null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  270 */       throw new IllegalArgumentException(
/*      */           
/*  272 */           DatatypeMessageFormatter.formatMessage(null, "AllFieldsNull", null));
/*      */     }
/*      */     
/*  275 */     testNonNegative(years, DatatypeConstants.YEARS);
/*  276 */     testNonNegative(months, DatatypeConstants.MONTHS);
/*  277 */     testNonNegative(days, DatatypeConstants.DAYS);
/*  278 */     testNonNegative(hours, DatatypeConstants.HOURS);
/*  279 */     testNonNegative(minutes, DatatypeConstants.MINUTES);
/*  280 */     testNonNegative(seconds, DatatypeConstants.SECONDS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void testNonNegative(BigInteger n, DatatypeConstants.Field f) {
/*  291 */     if (n != null && n.signum() < 0) {
/*  292 */       throw new IllegalArgumentException(
/*  293 */           DatatypeMessageFormatter.formatMessage(null, "NegativeField", new Object[] { f.toString() }));
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
/*      */   protected static void testNonNegative(BigDecimal n, DatatypeConstants.Field f) {
/*  306 */     if (n != null && n.signum() < 0)
/*      */     {
/*  308 */       throw new IllegalArgumentException(
/*  309 */           DatatypeMessageFormatter.formatMessage(null, "NegativeField", new Object[] { f.toString() }));
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
/*      */   protected DurationImpl(boolean isPositive, int years, int months, int days, int hours, int minutes, int seconds) {
/*  335 */     this(isPositive, 
/*      */         
/*  337 */         wrap(years), 
/*  338 */         wrap(months), 
/*  339 */         wrap(days), 
/*  340 */         wrap(hours), 
/*  341 */         wrap(minutes), (seconds != Integer.MIN_VALUE) ? new BigDecimal(
/*  342 */           String.valueOf(seconds)) : null);
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
/*      */   protected static BigInteger wrap(int i) {
/*  355 */     if (i == Integer.MIN_VALUE) {
/*  356 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  360 */     return new BigInteger(String.valueOf(i));
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
/*      */   protected DurationImpl(long durationInMilliSeconds) {
/*  372 */     long l = durationInMilliSeconds;
/*      */     
/*  374 */     if (l > 0L) {
/*  375 */       this.signum = 1;
/*  376 */     } else if (l < 0L) {
/*  377 */       this.signum = -1;
/*  378 */       if (l == Long.MIN_VALUE)
/*      */       {
/*  380 */         l++;
/*      */       }
/*  382 */       l *= -1L;
/*      */     } else {
/*  384 */       this.signum = 0;
/*      */     } 
/*      */ 
/*      */     
/*  388 */     GregorianCalendar gregorianCalendar = new GregorianCalendar(GMT);
/*      */ 
/*      */     
/*  391 */     gregorianCalendar.setTimeInMillis(l);
/*      */ 
/*      */     
/*  394 */     long int2long = 0L;
/*      */ 
/*      */     
/*  397 */     int2long = (gregorianCalendar.get(1) - 1970);
/*  398 */     this.years = BigInteger.valueOf(int2long);
/*      */ 
/*      */     
/*  401 */     int2long = gregorianCalendar.get(2);
/*  402 */     this.months = BigInteger.valueOf(int2long);
/*      */ 
/*      */     
/*  405 */     int2long = (gregorianCalendar.get(5) - 1);
/*  406 */     this.days = BigInteger.valueOf(int2long);
/*      */ 
/*      */     
/*  409 */     int2long = gregorianCalendar.get(11);
/*  410 */     this.hours = BigInteger.valueOf(int2long);
/*      */ 
/*      */     
/*  413 */     int2long = gregorianCalendar.get(12);
/*  414 */     this.minutes = BigInteger.valueOf(int2long);
/*      */ 
/*      */ 
/*      */     
/*  418 */     int2long = (gregorianCalendar.get(13) * 1000 + gregorianCalendar.get(14));
/*  419 */     this.seconds = BigDecimal.valueOf(int2long, 3);
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
/*      */   protected DurationImpl(String lexicalRepresentation) throws IllegalArgumentException {
/*      */     boolean positive;
/*  457 */     String s = lexicalRepresentation;
/*      */     
/*  459 */     int[] idx = new int[1];
/*  460 */     int length = s.length();
/*  461 */     boolean timeRequired = false;
/*      */     
/*  463 */     if (lexicalRepresentation == null) {
/*  464 */       throw new NullPointerException();
/*      */     }
/*      */     
/*  467 */     idx[0] = 0;
/*  468 */     if (length != idx[0] && s.charAt(idx[0]) == '-') {
/*  469 */       idx[0] = idx[0] + 1;
/*  470 */       positive = false;
/*      */     } else {
/*  472 */       positive = true;
/*      */     } 
/*      */     
/*  475 */     idx[0] = idx[0] + 1; if (length != idx[0] && s.charAt(idx[0]) != 'P') {
/*  476 */       throw new IllegalArgumentException(s);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  483 */     int dateLen = 0;
/*  484 */     String[] dateParts = new String[3];
/*  485 */     int[] datePartsIndex = new int[3];
/*  486 */     while (length != idx[0] && 
/*  487 */       isDigit(s.charAt(idx[0])) && dateLen < 3) {
/*      */       
/*  489 */       datePartsIndex[dateLen] = idx[0];
/*  490 */       dateParts[dateLen++] = parsePiece(s, idx);
/*      */     } 
/*      */     
/*  493 */     if (length != idx[0]) {
/*  494 */       idx[0] = idx[0] + 1; if (s.charAt(idx[0]) == 'T') {
/*  495 */         timeRequired = true;
/*      */       } else {
/*  497 */         throw new IllegalArgumentException(s);
/*      */       } 
/*      */     } 
/*      */     
/*  501 */     int timeLen = 0;
/*  502 */     String[] timeParts = new String[3];
/*  503 */     int[] timePartsIndex = new int[3];
/*  504 */     while (length != idx[0] && 
/*  505 */       isDigitOrPeriod(s.charAt(idx[0])) && timeLen < 3) {
/*      */       
/*  507 */       timePartsIndex[timeLen] = idx[0];
/*  508 */       timeParts[timeLen++] = parsePiece(s, idx);
/*      */     } 
/*      */     
/*  511 */     if (timeRequired && timeLen == 0) {
/*  512 */       throw new IllegalArgumentException(s);
/*      */     }
/*      */     
/*  515 */     if (length != idx[0]) {
/*  516 */       throw new IllegalArgumentException(s);
/*      */     }
/*  518 */     if (dateLen == 0 && timeLen == 0) {
/*  519 */       throw new IllegalArgumentException(s);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  524 */     organizeParts(s, dateParts, datePartsIndex, dateLen, "YMD");
/*  525 */     organizeParts(s, timeParts, timePartsIndex, timeLen, "HMS");
/*      */ 
/*      */     
/*  528 */     this.years = parseBigInteger(s, dateParts[0], datePartsIndex[0]);
/*  529 */     this.months = parseBigInteger(s, dateParts[1], datePartsIndex[1]);
/*  530 */     this.days = parseBigInteger(s, dateParts[2], datePartsIndex[2]);
/*  531 */     this.hours = parseBigInteger(s, timeParts[0], timePartsIndex[0]);
/*  532 */     this.minutes = parseBigInteger(s, timeParts[1], timePartsIndex[1]);
/*  533 */     this.seconds = parseBigDecimal(s, timeParts[2], timePartsIndex[2]);
/*  534 */     this.signum = calcSignum(positive);
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
/*      */   private static boolean isDigit(char ch) {
/*  546 */     return ('0' <= ch && ch <= '9');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isDigitOrPeriod(char ch) {
/*  557 */     return (isDigit(ch) || ch == '.');
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
/*      */   private static String parsePiece(String whole, int[] idx) throws IllegalArgumentException {
/*  572 */     int start = idx[0];
/*  573 */     while (idx[0] < whole.length() && 
/*  574 */       isDigitOrPeriod(whole.charAt(idx[0]))) {
/*  575 */       idx[0] = idx[0] + 1;
/*      */     }
/*  577 */     if (idx[0] == whole.length()) {
/*  578 */       throw new IllegalArgumentException(whole);
/*      */     }
/*      */     
/*  581 */     idx[0] = idx[0] + 1;
/*      */     
/*  583 */     return whole.substring(start, idx[0]);
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
/*      */   private static void organizeParts(String whole, String[] parts, int[] partsIndex, int len, String tokens) throws IllegalArgumentException {
/*  605 */     int idx = tokens.length();
/*  606 */     for (int i = len - 1; i >= 0; i--) {
/*      */       
/*  608 */       int nidx = tokens.lastIndexOf(parts[i]
/*  609 */           .charAt(parts[i].length() - 1), idx - 1);
/*      */       
/*  611 */       if (nidx == -1) {
/*  612 */         throw new IllegalArgumentException(whole);
/*      */       }
/*      */ 
/*      */       
/*  616 */       for (int j = nidx + 1; j < idx; j++) {
/*  617 */         parts[j] = null;
/*      */       }
/*  619 */       idx = nidx;
/*  620 */       parts[idx] = parts[i];
/*  621 */       partsIndex[idx] = partsIndex[i];
/*      */     } 
/*  623 */     for (; --idx >= 0; idx--) {
/*  624 */       parts[idx] = null;
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
/*      */   private static BigInteger parseBigInteger(String whole, String part, int index) throws IllegalArgumentException {
/*  644 */     if (part == null) {
/*  645 */       return null;
/*      */     }
/*  647 */     part = part.substring(0, part.length() - 1);
/*      */     
/*  649 */     return new BigInteger(part);
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
/*      */   private static BigDecimal parseBigDecimal(String whole, String part, int index) throws IllegalArgumentException {
/*  671 */     if (part == null) {
/*  672 */       return null;
/*      */     }
/*  674 */     part = part.substring(0, part.length() - 1);
/*      */ 
/*      */     
/*  677 */     return new BigDecimal(part);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  686 */   private static final XMLGregorianCalendar[] TEST_POINTS = new XMLGregorianCalendar[] {
/*  687 */       XMLGregorianCalendarImpl.parse("1696-09-01T00:00:00Z"), 
/*  688 */       XMLGregorianCalendarImpl.parse("1697-02-01T00:00:00Z"), 
/*  689 */       XMLGregorianCalendarImpl.parse("1903-03-01T00:00:00Z"), 
/*  690 */       XMLGregorianCalendarImpl.parse("1903-07-01T00:00:00Z")
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compare(Duration rhs) {
/*  725 */     BigInteger maxintAsBigInteger = BigInteger.valueOf(2147483647L);
/*  726 */     BigInteger minintAsBigInteger = BigInteger.valueOf(-2147483648L);
/*      */ 
/*      */     
/*  729 */     if (this.years != null && this.years.compareTo(maxintAsBigInteger) == 1) {
/*  730 */       throw new UnsupportedOperationException(
/*  731 */           DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[] {
/*  732 */               getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.YEARS.toString(), this.years.toString()
/*      */             }));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  738 */     if (this.months != null && this.months.compareTo(maxintAsBigInteger) == 1) {
/*  739 */       throw new UnsupportedOperationException(
/*  740 */           DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[] {
/*  741 */               getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.MONTHS.toString(), this.months.toString()
/*      */             }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  748 */     if (this.days != null && this.days.compareTo(maxintAsBigInteger) == 1) {
/*  749 */       throw new UnsupportedOperationException(
/*  750 */           DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[] {
/*  751 */               getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.DAYS.toString(), this.days.toString()
/*      */             }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  758 */     if (this.hours != null && this.hours.compareTo(maxintAsBigInteger) == 1) {
/*  759 */       throw new UnsupportedOperationException(
/*  760 */           DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[] {
/*  761 */               getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.HOURS.toString(), this.hours.toString()
/*      */             }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  768 */     if (this.minutes != null && this.minutes.compareTo(maxintAsBigInteger) == 1) {
/*  769 */       throw new UnsupportedOperationException(
/*  770 */           DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[] {
/*  771 */               getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.MINUTES.toString(), this.minutes.toString()
/*      */             }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  778 */     if (this.seconds != null && this.seconds.toBigInteger().compareTo(maxintAsBigInteger) == 1) {
/*  779 */       throw new UnsupportedOperationException(
/*  780 */           DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[] {
/*  781 */               getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.SECONDS.toString(), this.seconds.toString()
/*      */             }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  790 */     BigInteger rhsYears = (BigInteger)rhs.getField(DatatypeConstants.YEARS);
/*  791 */     if (rhsYears != null && rhsYears.compareTo(maxintAsBigInteger) == 1) {
/*  792 */       throw new UnsupportedOperationException(
/*  793 */           DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[] {
/*  794 */               getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.YEARS.toString(), rhsYears.toString()
/*      */             }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  801 */     BigInteger rhsMonths = (BigInteger)rhs.getField(DatatypeConstants.MONTHS);
/*  802 */     if (rhsMonths != null && rhsMonths.compareTo(maxintAsBigInteger) == 1) {
/*  803 */       throw new UnsupportedOperationException(
/*  804 */           DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[] {
/*  805 */               getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.MONTHS.toString(), rhsMonths.toString()
/*      */             }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  812 */     BigInteger rhsDays = (BigInteger)rhs.getField(DatatypeConstants.DAYS);
/*  813 */     if (rhsDays != null && rhsDays.compareTo(maxintAsBigInteger) == 1) {
/*  814 */       throw new UnsupportedOperationException(
/*  815 */           DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[] {
/*  816 */               getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.DAYS.toString(), rhsDays.toString()
/*      */             }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  823 */     BigInteger rhsHours = (BigInteger)rhs.getField(DatatypeConstants.HOURS);
/*  824 */     if (rhsHours != null && rhsHours.compareTo(maxintAsBigInteger) == 1) {
/*  825 */       throw new UnsupportedOperationException(
/*  826 */           DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[] {
/*  827 */               getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.HOURS.toString(), rhsHours.toString()
/*      */             }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  834 */     BigInteger rhsMinutes = (BigInteger)rhs.getField(DatatypeConstants.MINUTES);
/*  835 */     if (rhsMinutes != null && rhsMinutes.compareTo(maxintAsBigInteger) == 1) {
/*  836 */       throw new UnsupportedOperationException(
/*  837 */           DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[] {
/*  838 */               getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.MINUTES.toString(), rhsMinutes.toString()
/*      */             }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  845 */     BigDecimal rhsSecondsAsBigDecimal = (BigDecimal)rhs.getField(DatatypeConstants.SECONDS);
/*  846 */     BigInteger rhsSeconds = null;
/*  847 */     if (rhsSecondsAsBigDecimal != null) {
/*  848 */       rhsSeconds = rhsSecondsAsBigDecimal.toBigInteger();
/*      */     }
/*  850 */     if (rhsSeconds != null && rhsSeconds.compareTo(maxintAsBigInteger) == 1) {
/*  851 */       throw new UnsupportedOperationException(
/*  852 */           DatatypeMessageFormatter.formatMessage(null, "TooLarge", new Object[] {
/*  853 */               getClass().getName() + "#compare(Duration duration)" + DatatypeConstants.SECONDS.toString(), rhsSeconds.toString()
/*      */             }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  862 */     GregorianCalendar lhsCalendar = new GregorianCalendar(1970, 1, 1, 0, 0, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  869 */     lhsCalendar.add(1, getYears() * getSign());
/*  870 */     lhsCalendar.add(2, getMonths() * getSign());
/*  871 */     lhsCalendar.add(6, getDays() * getSign());
/*  872 */     lhsCalendar.add(11, getHours() * getSign());
/*  873 */     lhsCalendar.add(12, getMinutes() * getSign());
/*  874 */     lhsCalendar.add(13, getSeconds() * getSign());
/*      */ 
/*      */     
/*  877 */     GregorianCalendar rhsCalendar = new GregorianCalendar(1970, 1, 1, 0, 0, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  884 */     rhsCalendar.add(1, rhs.getYears() * rhs.getSign());
/*  885 */     rhsCalendar.add(2, rhs.getMonths() * rhs.getSign());
/*  886 */     rhsCalendar.add(6, rhs.getDays() * rhs.getSign());
/*  887 */     rhsCalendar.add(11, rhs.getHours() * rhs.getSign());
/*  888 */     rhsCalendar.add(12, rhs.getMinutes() * rhs.getSign());
/*  889 */     rhsCalendar.add(13, rhs.getSeconds() * rhs.getSign());
/*      */ 
/*      */     
/*  892 */     if (lhsCalendar.equals(rhsCalendar)) {
/*  893 */       return 0;
/*      */     }
/*      */     
/*  896 */     return compareDates(this, rhs);
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
/*      */   private int compareDates(Duration duration1, Duration duration2) {
/*  913 */     int resultA = 2;
/*  914 */     int resultB = 2;
/*      */     
/*  916 */     XMLGregorianCalendar tempA = (XMLGregorianCalendar)TEST_POINTS[0].clone();
/*  917 */     XMLGregorianCalendar tempB = (XMLGregorianCalendar)TEST_POINTS[0].clone();
/*      */ 
/*      */     
/*  920 */     tempA.add(duration1);
/*  921 */     tempB.add(duration2);
/*  922 */     resultA = tempA.compare(tempB);
/*  923 */     if (resultA == 2) {
/*  924 */       return 2;
/*      */     }
/*      */     
/*  927 */     tempA = (XMLGregorianCalendar)TEST_POINTS[1].clone();
/*  928 */     tempB = (XMLGregorianCalendar)TEST_POINTS[1].clone();
/*      */     
/*  930 */     tempA.add(duration1);
/*  931 */     tempB.add(duration2);
/*  932 */     resultB = tempA.compare(tempB);
/*  933 */     resultA = compareResults(resultA, resultB);
/*  934 */     if (resultA == 2) {
/*  935 */       return 2;
/*      */     }
/*      */     
/*  938 */     tempA = (XMLGregorianCalendar)TEST_POINTS[2].clone();
/*  939 */     tempB = (XMLGregorianCalendar)TEST_POINTS[2].clone();
/*      */     
/*  941 */     tempA.add(duration1);
/*  942 */     tempB.add(duration2);
/*  943 */     resultB = tempA.compare(tempB);
/*  944 */     resultA = compareResults(resultA, resultB);
/*  945 */     if (resultA == 2) {
/*  946 */       return 2;
/*      */     }
/*      */     
/*  949 */     tempA = (XMLGregorianCalendar)TEST_POINTS[3].clone();
/*  950 */     tempB = (XMLGregorianCalendar)TEST_POINTS[3].clone();
/*      */     
/*  952 */     tempA.add(duration1);
/*  953 */     tempB.add(duration2);
/*  954 */     resultB = tempA.compare(tempB);
/*  955 */     resultA = compareResults(resultA, resultB);
/*      */     
/*  957 */     return resultA;
/*      */   }
/*      */ 
/*      */   
/*      */   private int compareResults(int resultA, int resultB) {
/*  962 */     if (resultB == 2) {
/*  963 */       return 2;
/*      */     }
/*  965 */     if (resultA != resultB) {
/*  966 */       return 2;
/*      */     }
/*  968 */     return resultA;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  978 */     Calendar cal = TEST_POINTS[0].toGregorianCalendar();
/*  979 */     addTo(cal);
/*  980 */     return (int)getCalendarTimeInMillis(cal);
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
/*      */   public String toString() {
/* 1003 */     StringBuffer buf = new StringBuffer();
/* 1004 */     if (this.signum < 0) {
/* 1005 */       buf.append('-');
/*      */     }
/* 1007 */     buf.append('P');
/*      */     
/* 1009 */     if (this.years != null) {
/* 1010 */       buf.append(this.years + "Y");
/*      */     }
/* 1012 */     if (this.months != null) {
/* 1013 */       buf.append(this.months + "M");
/*      */     }
/* 1015 */     if (this.days != null) {
/* 1016 */       buf.append(this.days + "D");
/*      */     }
/*      */     
/* 1019 */     if (this.hours != null || this.minutes != null || this.seconds != null) {
/* 1020 */       buf.append('T');
/* 1021 */       if (this.hours != null) {
/* 1022 */         buf.append(this.hours + "H");
/*      */       }
/* 1024 */       if (this.minutes != null) {
/* 1025 */         buf.append(this.minutes + "M");
/*      */       }
/* 1027 */       if (this.seconds != null) {
/* 1028 */         buf.append(toString(this.seconds) + "S");
/*      */       }
/*      */     } 
/*      */     
/* 1032 */     return buf.toString();
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
/*      */   private String toString(BigDecimal bd) {
/*      */     StringBuffer buf;
/* 1046 */     String intString = bd.unscaledValue().toString();
/* 1047 */     int scale = bd.scale();
/*      */     
/* 1049 */     if (scale == 0) {
/* 1050 */       return intString;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1055 */     int insertionPoint = intString.length() - scale;
/* 1056 */     if (insertionPoint == 0)
/* 1057 */       return "0." + intString; 
/* 1058 */     if (insertionPoint > 0) {
/* 1059 */       buf = new StringBuffer(intString);
/* 1060 */       buf.insert(insertionPoint, '.');
/*      */     } else {
/* 1062 */       buf = new StringBuffer(3 - insertionPoint + intString.length());
/* 1063 */       buf.append("0.");
/* 1064 */       for (int i = 0; i < -insertionPoint; i++) {
/* 1065 */         buf.append('0');
/*      */       }
/* 1067 */       buf.append(intString);
/*      */     } 
/* 1069 */     return buf.toString();
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
/*      */   public boolean isSet(DatatypeConstants.Field field) {
/* 1089 */     if (field == null) {
/* 1090 */       String str = "javax.xml.datatype.Duration#isSet(DatatypeConstants.Field field)";
/* 1091 */       throw new NullPointerException(
/*      */           
/* 1093 */           DatatypeMessageFormatter.formatMessage(null, "FieldCannotBeNull", new Object[] { str }));
/*      */     } 
/*      */ 
/*      */     
/* 1097 */     if (field == DatatypeConstants.YEARS) {
/* 1098 */       return (this.years != null);
/*      */     }
/*      */     
/* 1101 */     if (field == DatatypeConstants.MONTHS) {
/* 1102 */       return (this.months != null);
/*      */     }
/*      */     
/* 1105 */     if (field == DatatypeConstants.DAYS) {
/* 1106 */       return (this.days != null);
/*      */     }
/*      */     
/* 1109 */     if (field == DatatypeConstants.HOURS) {
/* 1110 */       return (this.hours != null);
/*      */     }
/*      */     
/* 1113 */     if (field == DatatypeConstants.MINUTES) {
/* 1114 */       return (this.minutes != null);
/*      */     }
/*      */     
/* 1117 */     if (field == DatatypeConstants.SECONDS) {
/* 1118 */       return (this.seconds != null);
/*      */     }
/* 1120 */     String methodName = "javax.xml.datatype.Duration#isSet(DatatypeConstants.Field field)";
/*      */     
/* 1122 */     throw new IllegalArgumentException(
/* 1123 */         DatatypeMessageFormatter.formatMessage(null, "UnknownField", new Object[] { methodName, field.toString() }));
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
/*      */   public Number getField(DatatypeConstants.Field field) {
/* 1154 */     if (field == null) {
/* 1155 */       String str = "javax.xml.datatype.Duration#isSet(DatatypeConstants.Field field) ";
/*      */       
/* 1157 */       throw new NullPointerException(
/* 1158 */           DatatypeMessageFormatter.formatMessage(null, "FieldCannotBeNull", new Object[] { str }));
/*      */     } 
/*      */ 
/*      */     
/* 1162 */     if (field == DatatypeConstants.YEARS) {
/* 1163 */       return this.years;
/*      */     }
/*      */     
/* 1166 */     if (field == DatatypeConstants.MONTHS) {
/* 1167 */       return this.months;
/*      */     }
/*      */     
/* 1170 */     if (field == DatatypeConstants.DAYS) {
/* 1171 */       return this.days;
/*      */     }
/*      */     
/* 1174 */     if (field == DatatypeConstants.HOURS) {
/* 1175 */       return this.hours;
/*      */     }
/*      */     
/* 1178 */     if (field == DatatypeConstants.MINUTES) {
/* 1179 */       return this.minutes;
/*      */     }
/*      */     
/* 1182 */     if (field == DatatypeConstants.SECONDS) {
/* 1183 */       return this.seconds;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1192 */     String methodName = "javax.xml.datatype.Duration#(getSet(DatatypeConstants.Field field)";
/*      */     
/* 1194 */     throw new IllegalArgumentException(
/* 1195 */         DatatypeMessageFormatter.formatMessage(null, "UnknownField", new Object[] { methodName, field.toString() }));
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
/*      */   public int getYears() {
/* 1220 */     return getInt(DatatypeConstants.YEARS);
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
/*      */   public int getMonths() {
/* 1233 */     return getInt(DatatypeConstants.MONTHS);
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
/*      */   public int getDays() {
/* 1246 */     return getInt(DatatypeConstants.DAYS);
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
/*      */   public int getHours() {
/* 1260 */     return getInt(DatatypeConstants.HOURS);
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
/*      */   public int getMinutes() {
/* 1274 */     return getInt(DatatypeConstants.MINUTES);
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
/*      */   public int getSeconds() {
/* 1289 */     return getInt(DatatypeConstants.SECONDS);
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
/*      */   private int getInt(DatatypeConstants.Field field) {
/* 1302 */     Number n = getField(field);
/* 1303 */     if (n == null) {
/* 1304 */       return 0;
/*      */     }
/* 1306 */     return n.intValue();
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
/*      */   public long getTimeInMillis(Calendar startInstant) {
/* 1341 */     Calendar cal = (Calendar)startInstant.clone();
/* 1342 */     addTo(cal);
/* 1343 */     return getCalendarTimeInMillis(cal) - 
/* 1344 */       getCalendarTimeInMillis(startInstant);
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
/*      */   public long getTimeInMillis(Date startInstant) {
/* 1379 */     Calendar cal = new GregorianCalendar();
/* 1380 */     cal.setTime(startInstant);
/* 1381 */     addTo(cal);
/* 1382 */     return getCalendarTimeInMillis(cal) - startInstant.getTime();
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
/*      */   public Duration normalizeWith(Calendar startTimeInstant) {
/* 1441 */     Calendar c = (Calendar)startTimeInstant.clone();
/*      */ 
/*      */ 
/*      */     
/* 1445 */     c.add(1, getYears() * this.signum);
/* 1446 */     c.add(2, getMonths() * this.signum);
/* 1447 */     c.add(5, getDays() * this.signum);
/*      */ 
/*      */     
/* 1450 */     long diff = getCalendarTimeInMillis(c) - getCalendarTimeInMillis(startTimeInstant);
/* 1451 */     int days = (int)(diff / 86400000L);
/*      */     
/* 1453 */     return new DurationImpl((days >= 0), null, null, 
/*      */ 
/*      */ 
/*      */         
/* 1457 */         wrap(Math.abs(days)), (BigInteger)
/* 1458 */         getField(DatatypeConstants.HOURS), (BigInteger)
/* 1459 */         getField(DatatypeConstants.MINUTES), (BigDecimal)
/* 1460 */         getField(DatatypeConstants.SECONDS));
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
/*      */   public Duration multiply(int factor) {
/* 1480 */     return multiply(BigDecimal.valueOf(factor));
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
/*      */   public Duration multiply(BigDecimal factor) {
/* 1531 */     BigDecimal carry = ZERO;
/* 1532 */     int factorSign = factor.signum();
/* 1533 */     factor = factor.abs();
/*      */     
/* 1535 */     BigDecimal[] buf = new BigDecimal[6];
/*      */     
/* 1537 */     for (int i = 0; i < 5; i++) {
/* 1538 */       BigDecimal bd = getFieldAsBigDecimal(FIELDS[i]);
/* 1539 */       bd = bd.multiply(factor).add(carry);
/*      */       
/* 1541 */       buf[i] = bd.setScale(0, 1);
/*      */       
/* 1543 */       bd = bd.subtract(buf[i]);
/* 1544 */       if (i == 1) {
/* 1545 */         if (bd.signum() != 0) {
/* 1546 */           throw new IllegalStateException();
/*      */         }
/* 1548 */         carry = ZERO;
/*      */       } else {
/*      */         
/* 1551 */         carry = bd.multiply(FACTORS[i]);
/*      */       } 
/*      */     } 
/*      */     
/* 1555 */     if (this.seconds != null) {
/* 1556 */       buf[5] = this.seconds.multiply(factor).add(carry);
/*      */     } else {
/* 1558 */       buf[5] = carry;
/*      */     } 
/*      */     
/* 1561 */     return new DurationImpl((this.signum * factorSign >= 0), 
/*      */         
/* 1563 */         toBigInteger(buf[0], (null == this.years)), 
/* 1564 */         toBigInteger(buf[1], (null == this.months)), 
/* 1565 */         toBigInteger(buf[2], (null == this.days)), 
/* 1566 */         toBigInteger(buf[3], (null == this.hours)), 
/* 1567 */         toBigInteger(buf[4], (null == this.minutes)), (buf[5]
/* 1568 */         .signum() == 0 && this.seconds == null) ? null : buf[5]);
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
/*      */   private BigDecimal getFieldAsBigDecimal(DatatypeConstants.Field f) {
/* 1581 */     if (f == DatatypeConstants.SECONDS) {
/* 1582 */       if (this.seconds != null) {
/* 1583 */         return this.seconds;
/*      */       }
/* 1585 */       return ZERO;
/*      */     } 
/*      */     
/* 1588 */     BigInteger bi = (BigInteger)getField(f);
/* 1589 */     if (bi == null) {
/* 1590 */       return ZERO;
/*      */     }
/* 1592 */     return new BigDecimal(bi);
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
/*      */   private static BigInteger toBigInteger(BigDecimal value, boolean canBeNull) {
/* 1608 */     if (canBeNull && value.signum() == 0) {
/* 1609 */       return null;
/*      */     }
/* 1611 */     return value.unscaledValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1619 */   private static final BigDecimal[] FACTORS = new BigDecimal[] {
/* 1620 */       BigDecimal.valueOf(12L), null, 
/*      */       
/* 1622 */       BigDecimal.valueOf(24L), 
/* 1623 */       BigDecimal.valueOf(60L), 
/* 1624 */       BigDecimal.valueOf(60L)
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Duration add(Duration rhs) {
/* 1678 */     Duration lhs = this;
/* 1679 */     BigDecimal[] buf = new BigDecimal[6];
/*      */     
/* 1681 */     buf[0] = sanitize((BigInteger)lhs.getField(DatatypeConstants.YEARS), lhs
/* 1682 */         .getSign()).add(sanitize((BigInteger)rhs.getField(DatatypeConstants.YEARS), rhs.getSign()));
/* 1683 */     buf[1] = sanitize((BigInteger)lhs.getField(DatatypeConstants.MONTHS), lhs
/* 1684 */         .getSign()).add(sanitize((BigInteger)rhs.getField(DatatypeConstants.MONTHS), rhs.getSign()));
/* 1685 */     buf[2] = sanitize((BigInteger)lhs.getField(DatatypeConstants.DAYS), lhs
/* 1686 */         .getSign()).add(sanitize((BigInteger)rhs.getField(DatatypeConstants.DAYS), rhs.getSign()));
/* 1687 */     buf[3] = sanitize((BigInteger)lhs.getField(DatatypeConstants.HOURS), lhs
/* 1688 */         .getSign()).add(sanitize((BigInteger)rhs.getField(DatatypeConstants.HOURS), rhs.getSign()));
/* 1689 */     buf[4] = sanitize((BigInteger)lhs.getField(DatatypeConstants.MINUTES), lhs
/* 1690 */         .getSign()).add(sanitize((BigInteger)rhs.getField(DatatypeConstants.MINUTES), rhs.getSign()));
/* 1691 */     buf[5] = sanitize((BigDecimal)lhs.getField(DatatypeConstants.SECONDS), lhs
/* 1692 */         .getSign()).add(sanitize((BigDecimal)rhs.getField(DatatypeConstants.SECONDS), rhs.getSign()));
/*      */ 
/*      */     
/* 1695 */     alignSigns(buf, 0, 2);
/* 1696 */     alignSigns(buf, 2, 6);
/*      */ 
/*      */     
/* 1699 */     int s = 0;
/* 1700 */     for (int i = 0; i < 6; i++) {
/* 1701 */       if (s * buf[i].signum() < 0) {
/* 1702 */         throw new IllegalStateException();
/*      */       }
/* 1704 */       if (s == 0) {
/* 1705 */         s = buf[i].signum();
/*      */       }
/*      */     } 
/*      */     
/* 1709 */     return new DurationImpl((s >= 0), 
/*      */         
/* 1711 */         toBigInteger(sanitize(buf[0], s), (lhs
/* 1712 */           .getField(DatatypeConstants.YEARS) == null && rhs.getField(DatatypeConstants.YEARS) == null)), 
/* 1713 */         toBigInteger(sanitize(buf[1], s), (lhs
/* 1714 */           .getField(DatatypeConstants.MONTHS) == null && rhs.getField(DatatypeConstants.MONTHS) == null)), 
/* 1715 */         toBigInteger(sanitize(buf[2], s), (lhs
/* 1716 */           .getField(DatatypeConstants.DAYS) == null && rhs.getField(DatatypeConstants.DAYS) == null)), 
/* 1717 */         toBigInteger(sanitize(buf[3], s), (lhs
/* 1718 */           .getField(DatatypeConstants.HOURS) == null && rhs.getField(DatatypeConstants.HOURS) == null)), 
/* 1719 */         toBigInteger(sanitize(buf[4], s), (lhs
/* 1720 */           .getField(DatatypeConstants.MINUTES) == null && rhs.getField(DatatypeConstants.MINUTES) == null)), (buf[5]
/* 1721 */         .signum() == 0 && lhs
/* 1722 */         .getField(DatatypeConstants.SECONDS) == null && rhs
/* 1723 */         .getField(DatatypeConstants.SECONDS) == null) ? null : sanitize(buf[5], s));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void alignSigns(BigDecimal[] buf, int start, int end) {
/*      */     boolean touched;
/*      */     do {
/* 1731 */       touched = false;
/* 1732 */       int s = 0;
/*      */       
/* 1734 */       for (int i = start; i < end; i++) {
/* 1735 */         if (s * buf[i].signum() < 0) {
/*      */           
/* 1737 */           touched = true;
/*      */ 
/*      */ 
/*      */           
/* 1741 */           BigDecimal borrow = buf[i].abs().divide(FACTORS[i - 1], 0);
/*      */ 
/*      */           
/* 1744 */           if (buf[i].signum() > 0) {
/* 1745 */             borrow = borrow.negate();
/*      */           }
/*      */ 
/*      */           
/* 1749 */           buf[i - 1] = buf[i - 1].subtract(borrow);
/* 1750 */           buf[i] = buf[i].add(borrow.multiply(FACTORS[i - 1]));
/*      */         } 
/* 1752 */         if (buf[i].signum() != 0) {
/* 1753 */           s = buf[i].signum();
/*      */         }
/*      */       } 
/* 1756 */     } while (touched);
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
/*      */   private static BigDecimal sanitize(BigInteger value, int signum) {
/* 1768 */     if (signum == 0 || value == null) {
/* 1769 */       return ZERO;
/*      */     }
/* 1771 */     if (signum > 0) {
/* 1772 */       return new BigDecimal(value);
/*      */     }
/* 1774 */     return new BigDecimal(value.negate());
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
/*      */   static BigDecimal sanitize(BigDecimal value, int signum) {
/* 1786 */     if (signum == 0 || value == null) {
/* 1787 */       return ZERO;
/*      */     }
/* 1789 */     if (signum > 0) {
/* 1790 */       return value;
/*      */     }
/* 1792 */     return value.negate();
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
/*      */   public Duration subtract(Duration rhs) {
/* 1845 */     return add(rhs.negate());
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
/*      */   public Duration negate() {
/* 1861 */     return new DurationImpl((this.signum <= 0), this.years, this.months, this.days, this.hours, this.minutes, this.seconds);
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
/*      */   public int signum() {
/* 1879 */     return this.signum;
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
/*      */   public void addTo(Calendar calendar) {
/* 1923 */     calendar.add(1, getYears() * this.signum);
/* 1924 */     calendar.add(2, getMonths() * this.signum);
/* 1925 */     calendar.add(5, getDays() * this.signum);
/* 1926 */     calendar.add(10, getHours() * this.signum);
/* 1927 */     calendar.add(12, getMinutes() * this.signum);
/* 1928 */     calendar.add(13, getSeconds() * this.signum);
/*      */     
/* 1930 */     if (this.seconds != null) {
/*      */       
/* 1932 */       BigDecimal fraction = this.seconds.subtract(this.seconds.setScale(0, 1));
/* 1933 */       int millisec = fraction.movePointRight(3).intValue();
/* 1934 */       calendar.add(14, millisec * this.signum);
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
/*      */   public void addTo(Date date) {
/* 1960 */     Calendar cal = new GregorianCalendar();
/* 1961 */     cal.setTime(date);
/* 1962 */     addTo(cal);
/* 1963 */     date.setTime(getCalendarTimeInMillis(cal));
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
/*      */   private Object writeReplace() throws IOException {
/* 1983 */     return new DurationStream(toString());
/*      */   }
/*      */ 
/*      */   
/*      */   private static class DurationStream
/*      */     implements Serializable
/*      */   {
/*      */     private final String lexical;
/*      */     
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     private DurationStream(String _lexical) {
/* 1995 */       this.lexical = _lexical;
/*      */     }
/*      */ 
/*      */     
/*      */     private Object readResolve() throws ObjectStreamException {
/* 2000 */       return new DurationImpl(this.lexical);
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
/*      */   private static long getCalendarTimeInMillis(Calendar cal) {
/* 2018 */     return cal.getTime().getTime();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/datatype/DurationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */