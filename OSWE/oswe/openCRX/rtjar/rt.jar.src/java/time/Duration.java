/*      */ package java.time;
/*      */ 
/*      */ import java.io.DataInput;
/*      */ import java.io.DataOutput;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.math.RoundingMode;
/*      */ import java.time.format.DateTimeParseException;
/*      */ import java.time.temporal.ChronoField;
/*      */ import java.time.temporal.ChronoUnit;
/*      */ import java.time.temporal.Temporal;
/*      */ import java.time.temporal.TemporalAmount;
/*      */ import java.time.temporal.TemporalUnit;
/*      */ import java.time.temporal.UnsupportedTemporalTypeException;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Duration
/*      */   implements TemporalAmount, Comparable<Duration>, Serializable
/*      */ {
/*  139 */   public static final Duration ZERO = new Duration(0L, 0);
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 3078945930695997490L;
/*      */ 
/*      */ 
/*      */   
/*  147 */   private static final BigInteger BI_NANOS_PER_SECOND = BigInteger.valueOf(1000000000L);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  152 */   private static final Pattern PATTERN = Pattern.compile("([-+]?)P(?:([-+]?[0-9]+)D)?(T(?:([-+]?[0-9]+)H)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)(?:[.,]([0-9]{0,9}))?S)?)?", 2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final long seconds;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int nanos;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Duration ofDays(long paramLong) {
/*  179 */     return create(Math.multiplyExact(paramLong, 86400L), 0);
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
/*      */   public static Duration ofHours(long paramLong) {
/*  194 */     return create(Math.multiplyExact(paramLong, 3600L), 0);
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
/*      */   public static Duration ofMinutes(long paramLong) {
/*  209 */     return create(Math.multiplyExact(paramLong, 60L), 0);
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
/*      */   public static Duration ofSeconds(long paramLong) {
/*  222 */     return create(paramLong, 0);
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
/*      */   public static Duration ofSeconds(long paramLong1, long paramLong2) {
/*  245 */     long l = Math.addExact(paramLong1, Math.floorDiv(paramLong2, 1000000000L));
/*  246 */     int i = (int)Math.floorMod(paramLong2, 1000000000L);
/*  247 */     return create(l, i);
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
/*      */   public static Duration ofMillis(long paramLong) {
/*  260 */     long l = paramLong / 1000L;
/*  261 */     int i = (int)(paramLong % 1000L);
/*  262 */     if (i < 0) {
/*  263 */       i += 1000;
/*  264 */       l--;
/*      */     } 
/*  266 */     return create(l, i * 1000000);
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
/*      */   public static Duration ofNanos(long paramLong) {
/*  279 */     long l = paramLong / 1000000000L;
/*  280 */     int i = (int)(paramLong % 1000000000L);
/*  281 */     if (i < 0) {
/*  282 */       i = (int)(i + 1000000000L);
/*  283 */       l--;
/*      */     } 
/*  285 */     return create(l, i);
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
/*      */   public static Duration of(long paramLong, TemporalUnit paramTemporalUnit) {
/*  308 */     return ZERO.plus(paramLong, paramTemporalUnit);
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
/*      */   public static Duration from(TemporalAmount paramTemporalAmount) {
/*  333 */     Objects.requireNonNull(paramTemporalAmount, "amount");
/*  334 */     Duration duration = ZERO;
/*  335 */     for (TemporalUnit temporalUnit : paramTemporalAmount.getUnits()) {
/*  336 */       duration = duration.plus(paramTemporalAmount.get(temporalUnit), temporalUnit);
/*      */     }
/*  338 */     return duration;
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
/*      */   public static Duration parse(CharSequence paramCharSequence) {
/*  387 */     Objects.requireNonNull(paramCharSequence, "text");
/*  388 */     Matcher matcher = PATTERN.matcher(paramCharSequence);
/*  389 */     if (matcher.matches())
/*      */     {
/*  391 */       if (!"T".equals(matcher.group(3))) {
/*  392 */         boolean bool = "-".equals(matcher.group(1));
/*  393 */         String str1 = matcher.group(2);
/*  394 */         String str2 = matcher.group(4);
/*  395 */         String str3 = matcher.group(5);
/*  396 */         String str4 = matcher.group(6);
/*  397 */         String str5 = matcher.group(7);
/*  398 */         if (str1 != null || str2 != null || str3 != null || str4 != null) {
/*  399 */           long l1 = parseNumber(paramCharSequence, str1, 86400, "days");
/*  400 */           long l2 = parseNumber(paramCharSequence, str2, 3600, "hours");
/*  401 */           long l3 = parseNumber(paramCharSequence, str3, 60, "minutes");
/*  402 */           long l4 = parseNumber(paramCharSequence, str4, 1, "seconds");
/*  403 */           int i = parseFraction(paramCharSequence, str5, (l4 < 0L) ? -1 : 1);
/*      */           try {
/*  405 */             return create(bool, l1, l2, l3, l4, i);
/*  406 */           } catch (ArithmeticException arithmeticException) {
/*  407 */             throw (DateTimeParseException)(new DateTimeParseException("Text cannot be parsed to a Duration: overflow", paramCharSequence, 0)).initCause(arithmeticException);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*  412 */     throw new DateTimeParseException("Text cannot be parsed to a Duration", paramCharSequence, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private static long parseNumber(CharSequence paramCharSequence, String paramString1, int paramInt, String paramString2) {
/*  417 */     if (paramString1 == null) {
/*  418 */       return 0L;
/*      */     }
/*      */     try {
/*  421 */       long l = Long.parseLong(paramString1);
/*  422 */       return Math.multiplyExact(l, paramInt);
/*  423 */     } catch (NumberFormatException|ArithmeticException numberFormatException) {
/*  424 */       throw (DateTimeParseException)(new DateTimeParseException("Text cannot be parsed to a Duration: " + paramString2, paramCharSequence, 0)).initCause(numberFormatException);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int parseFraction(CharSequence paramCharSequence, String paramString, int paramInt) {
/*  430 */     if (paramString == null || paramString.length() == 0) {
/*  431 */       return 0;
/*      */     }
/*      */     try {
/*  434 */       paramString = (paramString + "000000000").substring(0, 9);
/*  435 */       return Integer.parseInt(paramString) * paramInt;
/*  436 */     } catch (NumberFormatException|ArithmeticException numberFormatException) {
/*  437 */       throw (DateTimeParseException)(new DateTimeParseException("Text cannot be parsed to a Duration: fraction", paramCharSequence, 0)).initCause(numberFormatException);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static Duration create(boolean paramBoolean, long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt) {
/*  442 */     long l = Math.addExact(paramLong1, Math.addExact(paramLong2, Math.addExact(paramLong3, paramLong4)));
/*  443 */     if (paramBoolean) {
/*  444 */       return ofSeconds(l, paramInt).negated();
/*      */     }
/*  446 */     return ofSeconds(l, paramInt);
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
/*      */   public static Duration between(Temporal paramTemporal1, Temporal paramTemporal2) {
/*      */     try {
/*  473 */       return ofNanos(paramTemporal1.until(paramTemporal2, ChronoUnit.NANOS));
/*  474 */     } catch (DateTimeException|ArithmeticException dateTimeException) {
/*  475 */       long l2, l1 = paramTemporal1.until(paramTemporal2, ChronoUnit.SECONDS);
/*      */       
/*      */       try {
/*  478 */         l2 = paramTemporal2.getLong(ChronoField.NANO_OF_SECOND) - paramTemporal1.getLong(ChronoField.NANO_OF_SECOND);
/*  479 */         if (l1 > 0L && l2 < 0L) {
/*  480 */           l1++;
/*  481 */         } else if (l1 < 0L && l2 > 0L) {
/*  482 */           l1--;
/*      */         } 
/*  484 */       } catch (DateTimeException dateTimeException1) {
/*  485 */         l2 = 0L;
/*      */       } 
/*  487 */       return ofSeconds(l1, l2);
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
/*      */   private static Duration create(long paramLong, int paramInt) {
/*  499 */     if ((paramLong | paramInt) == 0L) {
/*  500 */       return ZERO;
/*      */     }
/*  502 */     return new Duration(paramLong, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Duration(long paramLong, int paramInt) {
/*  513 */     this.seconds = paramLong;
/*  514 */     this.nanos = paramInt;
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
/*      */   public long get(TemporalUnit paramTemporalUnit) {
/*  532 */     if (paramTemporalUnit == ChronoUnit.SECONDS)
/*  533 */       return this.seconds; 
/*  534 */     if (paramTemporalUnit == ChronoUnit.NANOS) {
/*  535 */       return this.nanos;
/*      */     }
/*  537 */     throw new UnsupportedTemporalTypeException("Unsupported unit: " + paramTemporalUnit);
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
/*      */   public List<TemporalUnit> getUnits() {
/*  555 */     return DurationUnits.UNITS;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class DurationUnits
/*      */   {
/*  565 */     static final List<TemporalUnit> UNITS = Collections.unmodifiableList(Arrays.asList(new TemporalUnit[] { ChronoUnit.SECONDS, ChronoUnit.NANOS }));
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
/*      */   public boolean isZero() {
/*  579 */     return ((this.seconds | this.nanos) == 0L);
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
/*      */   public boolean isNegative() {
/*  592 */     return (this.seconds < 0L);
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
/*      */   public long getSeconds() {
/*  611 */     return this.seconds;
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
/*      */   public int getNano() {
/*  629 */     return this.nanos;
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
/*      */   public Duration withSeconds(long paramLong) {
/*  645 */     return create(paramLong, this.nanos);
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
/*      */   public Duration withNanos(int paramInt) {
/*  661 */     ChronoField.NANO_OF_SECOND.checkValidIntValue(paramInt);
/*  662 */     return create(this.seconds, paramInt);
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
/*      */   public Duration plus(Duration paramDuration) {
/*  676 */     return plus(paramDuration.getSeconds(), paramDuration.getNano());
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
/*      */   public Duration plus(long paramLong, TemporalUnit paramTemporalUnit) {
/*  696 */     Objects.requireNonNull(paramTemporalUnit, "unit");
/*  697 */     if (paramTemporalUnit == ChronoUnit.DAYS) {
/*  698 */       return plus(Math.multiplyExact(paramLong, 86400L), 0L);
/*      */     }
/*  700 */     if (paramTemporalUnit.isDurationEstimated()) {
/*  701 */       throw new UnsupportedTemporalTypeException("Unit must not have an estimated duration");
/*      */     }
/*  703 */     if (paramLong == 0L) {
/*  704 */       return this;
/*      */     }
/*  706 */     if (paramTemporalUnit instanceof ChronoUnit) {
/*  707 */       switch ((ChronoUnit)paramTemporalUnit) { case NANOS:
/*  708 */           return plusNanos(paramLong);
/*  709 */         case MICROS: return plusSeconds(paramLong / 1000000000L * 1000L).plusNanos(paramLong % 1000000000L * 1000L);
/*  710 */         case MILLIS: return plusMillis(paramLong);
/*  711 */         case SECONDS: return plusSeconds(paramLong); }
/*      */       
/*  713 */       return plusSeconds(Math.multiplyExact((paramTemporalUnit.getDuration()).seconds, paramLong));
/*      */     } 
/*  715 */     Duration duration = paramTemporalUnit.getDuration().multipliedBy(paramLong);
/*  716 */     return plusSeconds(duration.getSeconds()).plusNanos(duration.getNano());
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
/*      */   public Duration plusDays(long paramLong) {
/*  733 */     return plus(Math.multiplyExact(paramLong, 86400L), 0L);
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
/*      */   public Duration plusHours(long paramLong) {
/*  746 */     return plus(Math.multiplyExact(paramLong, 3600L), 0L);
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
/*      */   public Duration plusMinutes(long paramLong) {
/*  759 */     return plus(Math.multiplyExact(paramLong, 60L), 0L);
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
/*      */   public Duration plusSeconds(long paramLong) {
/*  772 */     return plus(paramLong, 0L);
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
/*      */   public Duration plusMillis(long paramLong) {
/*  785 */     return plus(paramLong / 1000L, paramLong % 1000L * 1000000L);
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
/*      */   public Duration plusNanos(long paramLong) {
/*  798 */     return plus(0L, paramLong);
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
/*      */   private Duration plus(long paramLong1, long paramLong2) {
/*  812 */     if ((paramLong1 | paramLong2) == 0L) {
/*  813 */       return this;
/*      */     }
/*  815 */     long l1 = Math.addExact(this.seconds, paramLong1);
/*  816 */     l1 = Math.addExact(l1, paramLong2 / 1000000000L);
/*  817 */     paramLong2 %= 1000000000L;
/*  818 */     long l2 = this.nanos + paramLong2;
/*  819 */     return ofSeconds(l1, l2);
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
/*      */   public Duration minus(Duration paramDuration) {
/*  833 */     long l = paramDuration.getSeconds();
/*  834 */     int i = paramDuration.getNano();
/*  835 */     if (l == Long.MIN_VALUE) {
/*  836 */       return plus(Long.MAX_VALUE, -i).plus(1L, 0L);
/*      */     }
/*  838 */     return plus(-l, -i);
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
/*      */   public Duration minus(long paramLong, TemporalUnit paramTemporalUnit) {
/*  857 */     return (paramLong == Long.MIN_VALUE) ? plus(Long.MAX_VALUE, paramTemporalUnit).plus(1L, paramTemporalUnit) : plus(-paramLong, paramTemporalUnit);
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
/*      */   public Duration minusDays(long paramLong) {
/*  874 */     return (paramLong == Long.MIN_VALUE) ? plusDays(Long.MAX_VALUE).plusDays(1L) : plusDays(-paramLong);
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
/*      */   public Duration minusHours(long paramLong) {
/*  889 */     return (paramLong == Long.MIN_VALUE) ? plusHours(Long.MAX_VALUE).plusHours(1L) : plusHours(-paramLong);
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
/*      */   public Duration minusMinutes(long paramLong) {
/*  904 */     return (paramLong == Long.MIN_VALUE) ? plusMinutes(Long.MAX_VALUE).plusMinutes(1L) : plusMinutes(-paramLong);
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
/*      */   public Duration minusSeconds(long paramLong) {
/*  917 */     return (paramLong == Long.MIN_VALUE) ? plusSeconds(Long.MAX_VALUE).plusSeconds(1L) : plusSeconds(-paramLong);
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
/*      */   public Duration minusMillis(long paramLong) {
/*  930 */     return (paramLong == Long.MIN_VALUE) ? plusMillis(Long.MAX_VALUE).plusMillis(1L) : plusMillis(-paramLong);
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
/*      */   public Duration minusNanos(long paramLong) {
/*  943 */     return (paramLong == Long.MIN_VALUE) ? plusNanos(Long.MAX_VALUE).plusNanos(1L) : plusNanos(-paramLong);
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
/*      */   public Duration multipliedBy(long paramLong) {
/*  957 */     if (paramLong == 0L) {
/*  958 */       return ZERO;
/*      */     }
/*  960 */     if (paramLong == 1L) {
/*  961 */       return this;
/*      */     }
/*  963 */     return create(toSeconds().multiply(BigDecimal.valueOf(paramLong)));
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
/*      */   public Duration dividedBy(long paramLong) {
/*  976 */     if (paramLong == 0L) {
/*  977 */       throw new ArithmeticException("Cannot divide by zero");
/*      */     }
/*  979 */     if (paramLong == 1L) {
/*  980 */       return this;
/*      */     }
/*  982 */     return create(toSeconds().divide(BigDecimal.valueOf(paramLong), RoundingMode.DOWN));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BigDecimal toSeconds() {
/*  992 */     return BigDecimal.valueOf(this.seconds).add(BigDecimal.valueOf(this.nanos, 9));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Duration create(BigDecimal paramBigDecimal) {
/* 1003 */     BigInteger bigInteger = paramBigDecimal.movePointRight(9).toBigIntegerExact();
/* 1004 */     BigInteger[] arrayOfBigInteger = bigInteger.divideAndRemainder(BI_NANOS_PER_SECOND);
/* 1005 */     if (arrayOfBigInteger[0].bitLength() > 63) {
/* 1006 */       throw new ArithmeticException("Exceeds capacity of Duration: " + bigInteger);
/*      */     }
/* 1008 */     return ofSeconds(arrayOfBigInteger[0].longValue(), arrayOfBigInteger[1].intValue());
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
/*      */   public Duration negated() {
/* 1024 */     return multipliedBy(-1L);
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
/*      */   public Duration abs() {
/* 1039 */     return isNegative() ? negated() : this;
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
/*      */   public Temporal addTo(Temporal paramTemporal) {
/* 1069 */     if (this.seconds != 0L) {
/* 1070 */       paramTemporal = paramTemporal.plus(this.seconds, ChronoUnit.SECONDS);
/*      */     }
/* 1072 */     if (this.nanos != 0) {
/* 1073 */       paramTemporal = paramTemporal.plus(this.nanos, ChronoUnit.NANOS);
/*      */     }
/* 1075 */     return paramTemporal;
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
/*      */   public Temporal subtractFrom(Temporal paramTemporal) {
/* 1104 */     if (this.seconds != 0L) {
/* 1105 */       paramTemporal = paramTemporal.minus(this.seconds, ChronoUnit.SECONDS);
/*      */     }
/* 1107 */     if (this.nanos != 0) {
/* 1108 */       paramTemporal = paramTemporal.minus(this.nanos, ChronoUnit.NANOS);
/*      */     }
/* 1110 */     return paramTemporal;
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
/*      */   public long toDays() {
/* 1126 */     return this.seconds / 86400L;
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
/*      */   public long toHours() {
/* 1140 */     return this.seconds / 3600L;
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
/*      */   public long toMinutes() {
/* 1154 */     return this.seconds / 60L;
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
/*      */   public long toMillis() {
/* 1171 */     long l = Math.multiplyExact(this.seconds, 1000L);
/* 1172 */     l = Math.addExact(l, (this.nanos / 1000000));
/* 1173 */     return l;
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
/*      */   public long toNanos() {
/* 1186 */     long l = Math.multiplyExact(this.seconds, 1000000000L);
/* 1187 */     l = Math.addExact(l, this.nanos);
/* 1188 */     return l;
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
/*      */   public int compareTo(Duration paramDuration) {
/* 1203 */     int i = Long.compare(this.seconds, paramDuration.seconds);
/* 1204 */     if (i != 0) {
/* 1205 */       return i;
/*      */     }
/* 1207 */     return this.nanos - paramDuration.nanos;
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
/*      */   public boolean equals(Object paramObject) {
/* 1221 */     if (this == paramObject) {
/* 1222 */       return true;
/*      */     }
/* 1224 */     if (paramObject instanceof Duration) {
/* 1225 */       Duration duration = (Duration)paramObject;
/* 1226 */       return (this.seconds == duration.seconds && this.nanos == duration.nanos);
/*      */     } 
/*      */     
/* 1229 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1239 */     return (int)(this.seconds ^ this.seconds >>> 32L) + 51 * this.nanos;
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
/*      */   public String toString() {
/* 1267 */     if (this == ZERO) {
/* 1268 */       return "PT0S";
/*      */     }
/* 1270 */     long l = this.seconds / 3600L;
/* 1271 */     int i = (int)(this.seconds % 3600L / 60L);
/* 1272 */     int j = (int)(this.seconds % 60L);
/* 1273 */     StringBuilder stringBuilder = new StringBuilder(24);
/* 1274 */     stringBuilder.append("PT");
/* 1275 */     if (l != 0L) {
/* 1276 */       stringBuilder.append(l).append('H');
/*      */     }
/* 1278 */     if (i != 0) {
/* 1279 */       stringBuilder.append(i).append('M');
/*      */     }
/* 1281 */     if (j == 0 && this.nanos == 0 && stringBuilder.length() > 2) {
/* 1282 */       return stringBuilder.toString();
/*      */     }
/* 1284 */     if (j < 0 && this.nanos > 0) {
/* 1285 */       if (j == -1) {
/* 1286 */         stringBuilder.append("-0");
/*      */       } else {
/* 1288 */         stringBuilder.append(j + 1);
/*      */       } 
/*      */     } else {
/* 1291 */       stringBuilder.append(j);
/*      */     } 
/* 1293 */     if (this.nanos > 0) {
/* 1294 */       int k = stringBuilder.length();
/* 1295 */       if (j < 0) {
/* 1296 */         stringBuilder.append(2000000000L - this.nanos);
/*      */       } else {
/* 1298 */         stringBuilder.append(this.nanos + 1000000000L);
/*      */       } 
/* 1300 */       while (stringBuilder.charAt(stringBuilder.length() - 1) == '0') {
/* 1301 */         stringBuilder.setLength(stringBuilder.length() - 1);
/*      */       }
/* 1303 */       stringBuilder.setCharAt(k, '.');
/*      */     } 
/* 1305 */     stringBuilder.append('S');
/* 1306 */     return stringBuilder.toString();
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
/*      */   private Object writeReplace() {
/* 1323 */     return new Ser((byte)1, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 1333 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*      */   }
/*      */   
/*      */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 1337 */     paramDataOutput.writeLong(this.seconds);
/* 1338 */     paramDataOutput.writeInt(this.nanos);
/*      */   }
/*      */   
/*      */   static Duration readExternal(DataInput paramDataInput) throws IOException {
/* 1342 */     long l = paramDataInput.readLong();
/* 1343 */     int i = paramDataInput.readInt();
/* 1344 */     return ofSeconds(l, i);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/Duration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */