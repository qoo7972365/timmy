/*     */ package java.time.temporal;
/*     */ 
/*     */ import java.time.DateTimeException;
/*     */ import java.time.DayOfWeek;
/*     */ import java.time.Duration;
/*     */ import java.time.LocalDate;
/*     */ import java.time.chrono.ChronoLocalDate;
/*     */ import java.time.chrono.Chronology;
/*     */ import java.time.chrono.IsoChronology;
/*     */ import java.time.format.ResolverStyle;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.ResourceBundle;
/*     */ import sun.util.locale.provider.LocaleProviderAdapter;
/*     */ import sun.util.locale.provider.LocaleResources;
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
/*     */ public final class IsoFields
/*     */ {
/* 195 */   public static final TemporalField DAY_OF_QUARTER = Field.DAY_OF_QUARTER;
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
/* 210 */   public static final TemporalField QUARTER_OF_YEAR = Field.QUARTER_OF_YEAR;
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
/* 240 */   public static final TemporalField WEEK_OF_WEEK_BASED_YEAR = Field.WEEK_OF_WEEK_BASED_YEAR;
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
/* 254 */   public static final TemporalField WEEK_BASED_YEAR = Field.WEEK_BASED_YEAR;
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
/* 269 */   public static final TemporalUnit WEEK_BASED_YEARS = Unit.WEEK_BASED_YEARS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 277 */   public static final TemporalUnit QUARTER_YEARS = Unit.QUARTER_YEARS;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IsoFields() {
/* 283 */     throw new AssertionError("Not instantiable");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private enum Field
/*     */     implements TemporalField
/*     */   {
/* 291 */     DAY_OF_QUARTER
/*     */     {
/*     */       public TemporalUnit getBaseUnit() {
/* 294 */         return ChronoUnit.DAYS;
/*     */       }
/*     */       
/*     */       public TemporalUnit getRangeUnit() {
/* 298 */         return IsoFields.QUARTER_YEARS;
/*     */       }
/*     */       
/*     */       public ValueRange range() {
/* 302 */         return ValueRange.of(1L, 90L, 92L);
/*     */       }
/*     */       
/*     */       public boolean isSupportedBy(TemporalAccessor param2TemporalAccessor) {
/* 306 */         return (param2TemporalAccessor.isSupported(ChronoField.DAY_OF_YEAR) && param2TemporalAccessor.isSupported(ChronoField.MONTH_OF_YEAR) && param2TemporalAccessor
/* 307 */           .isSupported(ChronoField.YEAR) && isIso(param2TemporalAccessor));
/*     */       }
/*     */       
/*     */       public ValueRange rangeRefinedBy(TemporalAccessor param2TemporalAccessor) {
/* 311 */         if (!isSupportedBy(param2TemporalAccessor)) {
/* 312 */           throw new UnsupportedTemporalTypeException("Unsupported field: DayOfQuarter");
/*     */         }
/* 314 */         long l = param2TemporalAccessor.getLong(QUARTER_OF_YEAR);
/* 315 */         if (l == 1L) {
/* 316 */           long l1 = param2TemporalAccessor.getLong(ChronoField.YEAR);
/* 317 */           return IsoChronology.INSTANCE.isLeapYear(l1) ? ValueRange.of(1L, 91L) : ValueRange.of(1L, 90L);
/* 318 */         }  if (l == 2L)
/* 319 */           return ValueRange.of(1L, 91L); 
/* 320 */         if (l == 3L || l == 4L) {
/* 321 */           return ValueRange.of(1L, 92L);
/*     */         }
/* 323 */         return range();
/*     */       }
/*     */       
/*     */       public long getFrom(TemporalAccessor param2TemporalAccessor) {
/* 327 */         if (!isSupportedBy(param2TemporalAccessor)) {
/* 328 */           throw new UnsupportedTemporalTypeException("Unsupported field: DayOfQuarter");
/*     */         }
/* 330 */         int i = param2TemporalAccessor.get(ChronoField.DAY_OF_YEAR);
/* 331 */         int j = param2TemporalAccessor.get(ChronoField.MONTH_OF_YEAR);
/* 332 */         long l = param2TemporalAccessor.getLong(ChronoField.YEAR);
/* 333 */         return (i - Field.QUARTER_DAYS[(j - 1) / 3 + (IsoChronology.INSTANCE.isLeapYear(l) ? 4 : 0)]);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public <R extends Temporal> R adjustInto(R param2R, long param2Long) {
/* 339 */         long l = getFrom((TemporalAccessor)param2R);
/* 340 */         range().checkValidValue(param2Long, this);
/* 341 */         return (R)param2R.with(ChronoField.DAY_OF_YEAR, param2R.getLong(ChronoField.DAY_OF_YEAR) + param2Long - l);
/*     */       }
/*     */       
/*     */       public ChronoLocalDate resolve(Map<TemporalField, Long> param2Map, TemporalAccessor param2TemporalAccessor, ResolverStyle param2ResolverStyle) {
/*     */         LocalDate localDate;
/* 346 */         Long long_1 = param2Map.get(ChronoField.YEAR);
/* 347 */         Long long_2 = param2Map.get(QUARTER_OF_YEAR);
/* 348 */         if (long_1 == null || long_2 == null) {
/* 349 */           return null;
/*     */         }
/* 351 */         int i = ChronoField.YEAR.checkValidIntValue(long_1.longValue());
/* 352 */         long l = ((Long)param2Map.get(DAY_OF_QUARTER)).longValue();
/* 353 */         ensureIso(param2TemporalAccessor);
/*     */         
/* 355 */         if (param2ResolverStyle == ResolverStyle.LENIENT) {
/* 356 */           localDate = LocalDate.of(i, 1, 1).plusMonths(Math.multiplyExact(Math.subtractExact(long_2.longValue(), 1L), 3L));
/* 357 */           l = Math.subtractExact(l, 1L);
/*     */         } else {
/* 359 */           int j = QUARTER_OF_YEAR.range().checkValidIntValue(long_2.longValue(), QUARTER_OF_YEAR);
/* 360 */           localDate = LocalDate.of(i, (j - 1) * 3 + 1, 1);
/* 361 */           if (l < 1L || l > 90L) {
/* 362 */             if (param2ResolverStyle == ResolverStyle.STRICT) {
/* 363 */               rangeRefinedBy(localDate).checkValidValue(l, this);
/*     */             } else {
/* 365 */               range().checkValidValue(l, this);
/*     */             } 
/*     */           }
/* 368 */           l--;
/*     */         } 
/* 370 */         param2Map.remove(this);
/* 371 */         param2Map.remove(ChronoField.YEAR);
/* 372 */         param2Map.remove(QUARTER_OF_YEAR);
/* 373 */         return localDate.plusDays(l);
/*     */       }
/*     */       
/*     */       public String toString() {
/* 377 */         return "DayOfQuarter";
/*     */       }
/*     */     },
/* 380 */     QUARTER_OF_YEAR
/*     */     {
/*     */       public TemporalUnit getBaseUnit() {
/* 383 */         return IsoFields.QUARTER_YEARS;
/*     */       }
/*     */       
/*     */       public TemporalUnit getRangeUnit() {
/* 387 */         return ChronoUnit.YEARS;
/*     */       }
/*     */       
/*     */       public ValueRange range() {
/* 391 */         return ValueRange.of(1L, 4L);
/*     */       }
/*     */       
/*     */       public boolean isSupportedBy(TemporalAccessor param2TemporalAccessor) {
/* 395 */         return (param2TemporalAccessor.isSupported(ChronoField.MONTH_OF_YEAR) && isIso(param2TemporalAccessor));
/*     */       }
/*     */       
/*     */       public long getFrom(TemporalAccessor param2TemporalAccessor) {
/* 399 */         if (!isSupportedBy(param2TemporalAccessor)) {
/* 400 */           throw new UnsupportedTemporalTypeException("Unsupported field: QuarterOfYear");
/*     */         }
/* 402 */         long l = param2TemporalAccessor.getLong(ChronoField.MONTH_OF_YEAR);
/* 403 */         return (l + 2L) / 3L;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public <R extends Temporal> R adjustInto(R param2R, long param2Long) {
/* 409 */         long l = getFrom((TemporalAccessor)param2R);
/* 410 */         range().checkValidValue(param2Long, this);
/* 411 */         return (R)param2R.with(ChronoField.MONTH_OF_YEAR, param2R.getLong(ChronoField.MONTH_OF_YEAR) + (param2Long - l) * 3L);
/*     */       }
/*     */       
/*     */       public String toString() {
/* 415 */         return "QuarterOfYear";
/*     */       }
/*     */     },
/* 418 */     WEEK_OF_WEEK_BASED_YEAR
/*     */     {
/*     */       public String getDisplayName(Locale param2Locale) {
/* 421 */         Objects.requireNonNull(param2Locale, "locale");
/*     */         
/* 423 */         LocaleResources localeResources = LocaleProviderAdapter.getResourceBundleBased().getLocaleResources(param2Locale);
/* 424 */         ResourceBundle resourceBundle = localeResources.getJavaTimeFormatData();
/* 425 */         return resourceBundle.containsKey("field.week") ? resourceBundle.getString("field.week") : toString();
/*     */       }
/*     */ 
/*     */       
/*     */       public TemporalUnit getBaseUnit() {
/* 430 */         return ChronoUnit.WEEKS;
/*     */       }
/*     */       
/*     */       public TemporalUnit getRangeUnit() {
/* 434 */         return IsoFields.WEEK_BASED_YEARS;
/*     */       }
/*     */       
/*     */       public ValueRange range() {
/* 438 */         return ValueRange.of(1L, 52L, 53L);
/*     */       }
/*     */       
/*     */       public boolean isSupportedBy(TemporalAccessor param2TemporalAccessor) {
/* 442 */         return (param2TemporalAccessor.isSupported(ChronoField.EPOCH_DAY) && isIso(param2TemporalAccessor));
/*     */       }
/*     */       
/*     */       public ValueRange rangeRefinedBy(TemporalAccessor param2TemporalAccessor) {
/* 446 */         if (!isSupportedBy(param2TemporalAccessor)) {
/* 447 */           throw new UnsupportedTemporalTypeException("Unsupported field: WeekOfWeekBasedYear");
/*     */         }
/* 449 */         return getWeekRange(LocalDate.from(param2TemporalAccessor));
/*     */       }
/*     */       
/*     */       public long getFrom(TemporalAccessor param2TemporalAccessor) {
/* 453 */         if (!isSupportedBy(param2TemporalAccessor)) {
/* 454 */           throw new UnsupportedTemporalTypeException("Unsupported field: WeekOfWeekBasedYear");
/*     */         }
/* 456 */         return getWeek(LocalDate.from(param2TemporalAccessor));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public <R extends Temporal> R adjustInto(R param2R, long param2Long) {
/* 462 */         range().checkValidValue(param2Long, this);
/* 463 */         return (R)param2R.plus(Math.subtractExact(param2Long, getFrom((TemporalAccessor)param2R)), ChronoUnit.WEEKS);
/*     */       }
/*     */ 
/*     */       
/*     */       public ChronoLocalDate resolve(Map<TemporalField, Long> param2Map, TemporalAccessor param2TemporalAccessor, ResolverStyle param2ResolverStyle) {
/* 468 */         Long long_1 = param2Map.get(WEEK_BASED_YEAR);
/* 469 */         Long long_2 = param2Map.get(ChronoField.DAY_OF_WEEK);
/* 470 */         if (long_1 == null || long_2 == null) {
/* 471 */           return null;
/*     */         }
/* 473 */         int i = WEEK_BASED_YEAR.range().checkValidIntValue(long_1.longValue(), WEEK_BASED_YEAR);
/* 474 */         long l = ((Long)param2Map.get(WEEK_OF_WEEK_BASED_YEAR)).longValue();
/* 475 */         ensureIso(param2TemporalAccessor);
/* 476 */         LocalDate localDate = LocalDate.of(i, 1, 4);
/* 477 */         if (param2ResolverStyle == ResolverStyle.LENIENT) {
/* 478 */           long l1 = long_2.longValue();
/* 479 */           if (l1 > 7L) {
/* 480 */             localDate = localDate.plusWeeks((l1 - 1L) / 7L);
/* 481 */             l1 = (l1 - 1L) % 7L + 1L;
/* 482 */           } else if (l1 < 1L) {
/* 483 */             localDate = localDate.plusWeeks(Math.subtractExact(l1, 7L) / 7L);
/* 484 */             l1 = (l1 + 6L) % 7L + 1L;
/*     */           } 
/* 486 */           localDate = localDate.plusWeeks(Math.subtractExact(l, 1L)).with(ChronoField.DAY_OF_WEEK, l1);
/*     */         } else {
/* 488 */           int j = ChronoField.DAY_OF_WEEK.checkValidIntValue(long_2.longValue());
/* 489 */           if (l < 1L || l > 52L) {
/* 490 */             if (param2ResolverStyle == ResolverStyle.STRICT) {
/* 491 */               getWeekRange(localDate).checkValidValue(l, this);
/*     */             } else {
/* 493 */               range().checkValidValue(l, this);
/*     */             } 
/*     */           }
/* 496 */           localDate = localDate.plusWeeks(l - 1L).with(ChronoField.DAY_OF_WEEK, j);
/*     */         } 
/* 498 */         param2Map.remove(this);
/* 499 */         param2Map.remove(WEEK_BASED_YEAR);
/* 500 */         param2Map.remove(ChronoField.DAY_OF_WEEK);
/* 501 */         return localDate;
/*     */       }
/*     */       
/*     */       public String toString() {
/* 505 */         return "WeekOfWeekBasedYear";
/*     */       }
/*     */     },
/* 508 */     WEEK_BASED_YEAR
/*     */     {
/*     */       public TemporalUnit getBaseUnit() {
/* 511 */         return IsoFields.WEEK_BASED_YEARS;
/*     */       }
/*     */       
/*     */       public TemporalUnit getRangeUnit() {
/* 515 */         return ChronoUnit.FOREVER;
/*     */       }
/*     */       
/*     */       public ValueRange range() {
/* 519 */         return ChronoField.YEAR.range();
/*     */       }
/*     */       
/*     */       public boolean isSupportedBy(TemporalAccessor param2TemporalAccessor) {
/* 523 */         return (param2TemporalAccessor.isSupported(ChronoField.EPOCH_DAY) && isIso(param2TemporalAccessor));
/*     */       }
/*     */       
/*     */       public long getFrom(TemporalAccessor param2TemporalAccessor) {
/* 527 */         if (!isSupportedBy(param2TemporalAccessor)) {
/* 528 */           throw new UnsupportedTemporalTypeException("Unsupported field: WeekBasedYear");
/*     */         }
/* 530 */         return getWeekBasedYear(LocalDate.from(param2TemporalAccessor));
/*     */       }
/*     */ 
/*     */       
/*     */       public <R extends Temporal> R adjustInto(R param2R, long param2Long) {
/* 535 */         if (!isSupportedBy((TemporalAccessor)param2R)) {
/* 536 */           throw new UnsupportedTemporalTypeException("Unsupported field: WeekBasedYear");
/*     */         }
/* 538 */         int i = range().checkValidIntValue(param2Long, WEEK_BASED_YEAR);
/* 539 */         LocalDate localDate1 = LocalDate.from((TemporalAccessor)param2R);
/* 540 */         int j = localDate1.get(ChronoField.DAY_OF_WEEK);
/* 541 */         int k = getWeek(localDate1);
/* 542 */         if (k == 53 && getWeekRange(i) == 52) {
/* 543 */           k = 52;
/*     */         }
/* 545 */         LocalDate localDate2 = LocalDate.of(i, 1, 4);
/* 546 */         int m = j - localDate2.get(ChronoField.DAY_OF_WEEK) + (k - 1) * 7;
/* 547 */         localDate2 = localDate2.plusDays(m);
/* 548 */         return (R)param2R.with(localDate2);
/*     */       }
/*     */       
/*     */       public String toString() {
/* 552 */         return "WeekBasedYear";
/*     */       }
/*     */     };
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
/* 572 */     private static final int[] QUARTER_DAYS = new int[] { 0, 90, 181, 273, 0, 91, 182, 274 };
/*     */     public boolean isDateBased() { return true; }
/*     */     public boolean isTimeBased() { return false; }
/* 575 */     private static boolean isIso(TemporalAccessor param1TemporalAccessor) { return Chronology.from(param1TemporalAccessor).equals(IsoChronology.INSTANCE); }
/*     */     public ValueRange rangeRefinedBy(TemporalAccessor param1TemporalAccessor) { return range(); } static {
/*     */     
/*     */     } private static void ensureIso(TemporalAccessor param1TemporalAccessor) {
/* 579 */       if (!isIso(param1TemporalAccessor)) {
/* 580 */         throw new DateTimeException("Resolve requires IsoChronology");
/*     */       }
/*     */     }
/*     */     
/*     */     private static ValueRange getWeekRange(LocalDate param1LocalDate) {
/* 585 */       int i = getWeekBasedYear(param1LocalDate);
/* 586 */       return ValueRange.of(1L, getWeekRange(i));
/*     */     }
/*     */     
/*     */     private static int getWeekRange(int param1Int) {
/* 590 */       LocalDate localDate = LocalDate.of(param1Int, 1, 1);
/*     */       
/* 592 */       if (localDate.getDayOfWeek() == DayOfWeek.THURSDAY || (localDate.getDayOfWeek() == DayOfWeek.WEDNESDAY && localDate.isLeapYear())) {
/* 593 */         return 53;
/*     */       }
/* 595 */       return 52;
/*     */     }
/*     */     
/*     */     private static int getWeek(LocalDate param1LocalDate) {
/* 599 */       int i = param1LocalDate.getDayOfWeek().ordinal();
/* 600 */       int j = param1LocalDate.getDayOfYear() - 1;
/* 601 */       int k = j + 3 - i;
/* 602 */       int m = k / 7;
/* 603 */       int n = k - m * 7;
/* 604 */       int i1 = n - 3;
/* 605 */       if (i1 < -3) {
/* 606 */         i1 += 7;
/*     */       }
/* 608 */       if (j < i1) {
/* 609 */         return (int)getWeekRange(param1LocalDate.withDayOfYear(180).minusYears(1L)).getMaximum();
/*     */       }
/* 611 */       int i2 = (j - i1) / 7 + 1;
/* 612 */       if (i2 == 53 && 
/* 613 */         !((i1 == -3 || (i1 == -2 && param1LocalDate.isLeapYear())) ? 1 : 0)) {
/* 614 */         i2 = 1;
/*     */       }
/*     */       
/* 617 */       return i2;
/*     */     }
/*     */     
/*     */     private static int getWeekBasedYear(LocalDate param1LocalDate) {
/* 621 */       int i = param1LocalDate.getYear();
/* 622 */       int j = param1LocalDate.getDayOfYear();
/* 623 */       if (j <= 3) {
/* 624 */         int k = param1LocalDate.getDayOfWeek().ordinal();
/* 625 */         if (j - k < -2) {
/* 626 */           i--;
/*     */         }
/* 628 */       } else if (j >= 363) {
/* 629 */         int k = param1LocalDate.getDayOfWeek().ordinal();
/* 630 */         j = j - 363 - (param1LocalDate.isLeapYear() ? 1 : 0);
/* 631 */         if (j - k >= 0) {
/* 632 */           i++;
/*     */         }
/*     */       } 
/* 635 */       return i;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private enum Unit
/*     */     implements TemporalUnit
/*     */   {
/* 648 */     WEEK_BASED_YEARS("WeekBasedYears", Duration.ofSeconds(31556952L)),
/*     */ 
/*     */ 
/*     */     
/* 652 */     QUARTER_YEARS("QuarterYears", Duration.ofSeconds(7889238L));
/*     */     
/*     */     private final String name;
/*     */     private final Duration duration;
/*     */     
/*     */     Unit(String param1String1, Duration param1Duration) {
/* 658 */       this.name = param1String1;
/* 659 */       this.duration = param1Duration;
/*     */     }
/*     */ 
/*     */     
/*     */     public Duration getDuration() {
/* 664 */       return this.duration;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDurationEstimated() {
/* 669 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDateBased() {
/* 674 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isTimeBased() {
/* 679 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSupportedBy(Temporal param1Temporal) {
/* 684 */       return param1Temporal.isSupported(ChronoField.EPOCH_DAY);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <R extends Temporal> R addTo(R param1R, long param1Long) {
/* 690 */       switch (this) {
/*     */         case WEEK_BASED_YEARS:
/* 692 */           return (R)param1R.with(IsoFields.WEEK_BASED_YEAR, 
/* 693 */               Math.addExact(param1R.get(IsoFields.WEEK_BASED_YEAR), param1Long));
/*     */         case QUARTER_YEARS:
/* 695 */           return (R)param1R.plus(param1Long / 4L, ChronoUnit.YEARS)
/* 696 */             .plus(param1Long % 4L * 3L, ChronoUnit.MONTHS);
/*     */       } 
/* 698 */       throw new IllegalStateException("Unreachable");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long between(Temporal param1Temporal1, Temporal param1Temporal2) {
/* 704 */       if (param1Temporal1.getClass() != param1Temporal2.getClass()) {
/* 705 */         return param1Temporal1.until(param1Temporal2, this);
/*     */       }
/* 707 */       switch (this) {
/*     */         case WEEK_BASED_YEARS:
/* 709 */           return Math.subtractExact(param1Temporal2.getLong(IsoFields.WEEK_BASED_YEAR), param1Temporal1
/* 710 */               .getLong(IsoFields.WEEK_BASED_YEAR));
/*     */         case QUARTER_YEARS:
/* 712 */           return param1Temporal1.until(param1Temporal2, ChronoUnit.MONTHS) / 3L;
/*     */       } 
/* 714 */       throw new IllegalStateException("Unreachable");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 720 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/temporal/IsoFields.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */