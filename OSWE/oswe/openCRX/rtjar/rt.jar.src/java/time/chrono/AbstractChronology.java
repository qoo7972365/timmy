/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.lang.invoke.SerializedLambda;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.DayOfWeek;
/*     */ import java.time.format.ResolverStyle;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import java.time.temporal.TemporalAdjusters;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.ValueRange;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractChronology
/*     */   implements Chronology
/*     */ {
/*     */   static final Comparator<ChronoLocalDate> DATE_ORDER;
/*     */   static final Comparator<ChronoLocalDateTime<? extends ChronoLocalDate>> DATE_TIME_ORDER;
/*     */   static final Comparator<ChronoZonedDateTime<?>> INSTANT_ORDER;
/*     */   
/*     */   static {
/* 132 */     DATE_ORDER = ((paramChronoLocalDate1, paramChronoLocalDate2) -> Long.compare(paramChronoLocalDate1.toEpochDay(), paramChronoLocalDate2.toEpochDay()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     DATE_TIME_ORDER = ((paramChronoLocalDateTime1, paramChronoLocalDateTime2) -> {
/*     */         int i = Long.compare(paramChronoLocalDateTime1.toLocalDate().toEpochDay(), paramChronoLocalDateTime2.toLocalDate().toEpochDay());
/*     */ 
/*     */         
/*     */         if (i == 0) {
/*     */           i = Long.compare(paramChronoLocalDateTime1.toLocalTime().toNanoOfDay(), paramChronoLocalDateTime2.toLocalTime().toNanoOfDay());
/*     */         }
/*     */         
/*     */         return i;
/*     */       });
/*     */     
/* 150 */     INSTANT_ORDER = ((paramChronoZonedDateTime1, paramChronoZonedDateTime2) -> {
/*     */         int i = Long.compare(paramChronoZonedDateTime1.toEpochSecond(), paramChronoZonedDateTime2.toEpochSecond());
/*     */         if (i == 0) {
/*     */           i = Long.compare(paramChronoZonedDateTime1.toLocalTime().getNano(), paramChronoZonedDateTime2.toLocalTime().getNano());
/*     */         }
/*     */         return i;
/*     */       });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   private static final ConcurrentHashMap<String, Chronology> CHRONOS_BY_ID = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */   
/* 166 */   private static final ConcurrentHashMap<String, Chronology> CHRONOS_BY_TYPE = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Chronology registerChrono(Chronology paramChronology) {
/* 177 */     return registerChrono(paramChronology, paramChronology.getId());
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
/*     */   static Chronology registerChrono(Chronology paramChronology, String paramString) {
/* 190 */     Chronology chronology = CHRONOS_BY_ID.putIfAbsent(paramString, paramChronology);
/* 191 */     if (chronology == null) {
/* 192 */       String str = paramChronology.getCalendarType();
/* 193 */       if (str != null) {
/* 194 */         CHRONOS_BY_TYPE.putIfAbsent(str, paramChronology);
/*     */       }
/*     */     } 
/* 197 */     return chronology;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean initCache() {
/* 216 */     if (CHRONOS_BY_ID.get("ISO") == null) {
/*     */ 
/*     */ 
/*     */       
/* 220 */       registerChrono(HijrahChronology.INSTANCE);
/* 221 */       registerChrono(JapaneseChronology.INSTANCE);
/* 222 */       registerChrono(MinguoChronology.INSTANCE);
/* 223 */       registerChrono(ThaiBuddhistChronology.INSTANCE);
/*     */ 
/*     */ 
/*     */       
/* 227 */       ServiceLoader<AbstractChronology> serviceLoader = ServiceLoader.load(AbstractChronology.class, (ClassLoader)null);
/* 228 */       for (AbstractChronology abstractChronology : serviceLoader) {
/* 229 */         String str = abstractChronology.getId();
/* 230 */         if (str.equals("ISO") || registerChrono(abstractChronology) != null) {
/*     */           
/* 232 */           PlatformLogger platformLogger = PlatformLogger.getLogger("java.time.chrono");
/* 233 */           platformLogger.warning("Ignoring duplicate Chronology, from ServiceLoader configuration " + str);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 238 */       registerChrono(IsoChronology.INSTANCE);
/* 239 */       return true;
/*     */     } 
/* 241 */     return false;
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
/*     */   static Chronology ofLocale(Locale paramLocale) {
/* 255 */     Objects.requireNonNull(paramLocale, "locale");
/* 256 */     String str = paramLocale.getUnicodeLocaleType("ca");
/* 257 */     if (str == null || "iso".equals(str) || "iso8601".equals(str)) {
/* 258 */       return IsoChronology.INSTANCE;
/*     */     }
/*     */     
/*     */     do {
/* 262 */       Chronology chronology = CHRONOS_BY_TYPE.get(str);
/* 263 */       if (chronology != null) {
/* 264 */         return chronology;
/*     */       }
/*     */     }
/* 267 */     while (initCache());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     ServiceLoader<Chronology> serviceLoader = ServiceLoader.load(Chronology.class);
/* 273 */     for (Chronology chronology : serviceLoader) {
/* 274 */       if (str.equals(chronology.getCalendarType())) {
/* 275 */         return chronology;
/*     */       }
/*     */     } 
/* 278 */     throw new DateTimeException("Unknown calendar system: " + str);
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
/*     */   
/*     */   static Chronology of(String paramString) {
/* 293 */     Objects.requireNonNull(paramString, "id");
/*     */     do {
/* 295 */       Chronology chronology = of0(paramString);
/* 296 */       if (chronology != null) {
/* 297 */         return chronology;
/*     */       }
/*     */     }
/* 300 */     while (initCache());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 305 */     ServiceLoader<Chronology> serviceLoader = ServiceLoader.load(Chronology.class);
/* 306 */     for (Chronology chronology : serviceLoader) {
/* 307 */       if (paramString.equals(chronology.getId()) || paramString.equals(chronology.getCalendarType())) {
/* 308 */         return chronology;
/*     */       }
/*     */     } 
/* 311 */     throw new DateTimeException("Unknown chronology: " + paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Chronology of0(String paramString) {
/* 322 */     Chronology chronology = CHRONOS_BY_ID.get(paramString);
/* 323 */     if (chronology == null) {
/* 324 */       chronology = CHRONOS_BY_TYPE.get(paramString);
/*     */     }
/* 326 */     return chronology;
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
/*     */   static Set<Chronology> getAvailableChronologies() {
/* 340 */     initCache();
/* 341 */     HashSet<Chronology> hashSet = new HashSet(CHRONOS_BY_ID.values());
/*     */ 
/*     */ 
/*     */     
/* 345 */     ServiceLoader<Chronology> serviceLoader = ServiceLoader.load(Chronology.class);
/* 346 */     for (Chronology chronology : serviceLoader) {
/* 347 */       hashSet.add(chronology);
/*     */     }
/* 349 */     return hashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChronoLocalDate resolveDate(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 455 */     if (paramMap.containsKey(ChronoField.EPOCH_DAY)) {
/* 456 */       return dateEpochDay(((Long)paramMap.remove(ChronoField.EPOCH_DAY)).longValue());
/*     */     }
/*     */ 
/*     */     
/* 460 */     resolveProlepticMonth(paramMap, paramResolverStyle);
/*     */ 
/*     */     
/* 463 */     ChronoLocalDate chronoLocalDate = resolveYearOfEra(paramMap, paramResolverStyle);
/* 464 */     if (chronoLocalDate != null) {
/* 465 */       return chronoLocalDate;
/*     */     }
/*     */ 
/*     */     
/* 469 */     if (paramMap.containsKey(ChronoField.YEAR)) {
/* 470 */       if (paramMap.containsKey(ChronoField.MONTH_OF_YEAR)) {
/* 471 */         if (paramMap.containsKey(ChronoField.DAY_OF_MONTH)) {
/* 472 */           return resolveYMD(paramMap, paramResolverStyle);
/*     */         }
/* 474 */         if (paramMap.containsKey(ChronoField.ALIGNED_WEEK_OF_MONTH)) {
/* 475 */           if (paramMap.containsKey(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH)) {
/* 476 */             return resolveYMAA(paramMap, paramResolverStyle);
/*     */           }
/* 478 */           if (paramMap.containsKey(ChronoField.DAY_OF_WEEK)) {
/* 479 */             return resolveYMAD(paramMap, paramResolverStyle);
/*     */           }
/*     */         } 
/*     */       } 
/* 483 */       if (paramMap.containsKey(ChronoField.DAY_OF_YEAR)) {
/* 484 */         return resolveYD(paramMap, paramResolverStyle);
/*     */       }
/* 486 */       if (paramMap.containsKey(ChronoField.ALIGNED_WEEK_OF_YEAR)) {
/* 487 */         if (paramMap.containsKey(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR)) {
/* 488 */           return resolveYAA(paramMap, paramResolverStyle);
/*     */         }
/* 490 */         if (paramMap.containsKey(ChronoField.DAY_OF_WEEK)) {
/* 491 */           return resolveYAD(paramMap, paramResolverStyle);
/*     */         }
/*     */       } 
/*     */     } 
/* 495 */     return null;
/*     */   }
/*     */   
/*     */   void resolveProlepticMonth(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 499 */     Long long_ = paramMap.remove(ChronoField.PROLEPTIC_MONTH);
/* 500 */     if (long_ != null) {
/* 501 */       if (paramResolverStyle != ResolverStyle.LENIENT) {
/* 502 */         ChronoField.PROLEPTIC_MONTH.checkValidValue(long_.longValue());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 507 */       ChronoLocalDate chronoLocalDate = dateNow().with(ChronoField.DAY_OF_MONTH, 1L).with(ChronoField.PROLEPTIC_MONTH, long_.longValue());
/* 508 */       addFieldValue(paramMap, ChronoField.MONTH_OF_YEAR, chronoLocalDate.get(ChronoField.MONTH_OF_YEAR));
/* 509 */       addFieldValue(paramMap, ChronoField.YEAR, chronoLocalDate.get(ChronoField.YEAR));
/*     */     } 
/*     */   }
/*     */   
/*     */   ChronoLocalDate resolveYearOfEra(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 514 */     Long long_ = paramMap.remove(ChronoField.YEAR_OF_ERA);
/* 515 */     if (long_ != null) {
/* 516 */       int i; Long long_1 = paramMap.remove(ChronoField.ERA);
/*     */       
/* 518 */       if (paramResolverStyle != ResolverStyle.LENIENT) {
/* 519 */         i = range(ChronoField.YEAR_OF_ERA).checkValidIntValue(long_.longValue(), ChronoField.YEAR_OF_ERA);
/*     */       } else {
/* 521 */         i = Math.toIntExact(long_.longValue());
/*     */       } 
/* 523 */       if (long_1 != null) {
/* 524 */         Era era = eraOf(range(ChronoField.ERA).checkValidIntValue(long_1.longValue(), ChronoField.ERA));
/* 525 */         addFieldValue(paramMap, ChronoField.YEAR, prolepticYear(era, i));
/*     */       }
/* 527 */       else if (paramMap.containsKey(ChronoField.YEAR)) {
/* 528 */         int j = range(ChronoField.YEAR).checkValidIntValue(((Long)paramMap.get(ChronoField.YEAR)).longValue(), ChronoField.YEAR);
/* 529 */         ChronoLocalDate chronoLocalDate = dateYearDay(j, 1);
/* 530 */         addFieldValue(paramMap, ChronoField.YEAR, prolepticYear(chronoLocalDate.getEra(), i));
/* 531 */       } else if (paramResolverStyle == ResolverStyle.STRICT) {
/*     */ 
/*     */         
/* 534 */         paramMap.put(ChronoField.YEAR_OF_ERA, long_);
/*     */       } else {
/* 536 */         List<Era> list = eras();
/* 537 */         if (list.isEmpty()) {
/* 538 */           addFieldValue(paramMap, ChronoField.YEAR, i);
/*     */         } else {
/* 540 */           Era era = list.get(list.size() - 1);
/* 541 */           addFieldValue(paramMap, ChronoField.YEAR, prolepticYear(era, i));
/*     */         }
/*     */       
/*     */       } 
/* 545 */     } else if (paramMap.containsKey(ChronoField.ERA)) {
/* 546 */       range(ChronoField.ERA).checkValidValue(((Long)paramMap.get(ChronoField.ERA)).longValue(), ChronoField.ERA);
/*     */     } 
/* 548 */     return null;
/*     */   }
/*     */   
/*     */   ChronoLocalDate resolveYMD(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 552 */     int i = range(ChronoField.YEAR).checkValidIntValue(((Long)paramMap.remove(ChronoField.YEAR)).longValue(), ChronoField.YEAR);
/* 553 */     if (paramResolverStyle == ResolverStyle.LENIENT) {
/* 554 */       long l1 = Math.subtractExact(((Long)paramMap.remove(ChronoField.MONTH_OF_YEAR)).longValue(), 1L);
/* 555 */       long l2 = Math.subtractExact(((Long)paramMap.remove(ChronoField.DAY_OF_MONTH)).longValue(), 1L);
/* 556 */       return date(i, 1, 1).plus(l1, ChronoUnit.MONTHS).plus(l2, ChronoUnit.DAYS);
/*     */     } 
/* 558 */     int j = range(ChronoField.MONTH_OF_YEAR).checkValidIntValue(((Long)paramMap.remove(ChronoField.MONTH_OF_YEAR)).longValue(), ChronoField.MONTH_OF_YEAR);
/* 559 */     ValueRange valueRange = range(ChronoField.DAY_OF_MONTH);
/* 560 */     int k = valueRange.checkValidIntValue(((Long)paramMap.remove(ChronoField.DAY_OF_MONTH)).longValue(), ChronoField.DAY_OF_MONTH);
/* 561 */     if (paramResolverStyle == ResolverStyle.SMART) {
/*     */       try {
/* 563 */         return date(i, j, k);
/* 564 */       } catch (DateTimeException dateTimeException) {
/* 565 */         return date(i, j, 1).with(TemporalAdjusters.lastDayOfMonth());
/*     */       } 
/*     */     }
/* 568 */     return date(i, j, k);
/*     */   }
/*     */   
/*     */   ChronoLocalDate resolveYD(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 572 */     int i = range(ChronoField.YEAR).checkValidIntValue(((Long)paramMap.remove(ChronoField.YEAR)).longValue(), ChronoField.YEAR);
/* 573 */     if (paramResolverStyle == ResolverStyle.LENIENT) {
/* 574 */       long l = Math.subtractExact(((Long)paramMap.remove(ChronoField.DAY_OF_YEAR)).longValue(), 1L);
/* 575 */       return dateYearDay(i, 1).plus(l, ChronoUnit.DAYS);
/*     */     } 
/* 577 */     int j = range(ChronoField.DAY_OF_YEAR).checkValidIntValue(((Long)paramMap.remove(ChronoField.DAY_OF_YEAR)).longValue(), ChronoField.DAY_OF_YEAR);
/* 578 */     return dateYearDay(i, j);
/*     */   }
/*     */   
/*     */   ChronoLocalDate resolveYMAA(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 582 */     int i = range(ChronoField.YEAR).checkValidIntValue(((Long)paramMap.remove(ChronoField.YEAR)).longValue(), ChronoField.YEAR);
/* 583 */     if (paramResolverStyle == ResolverStyle.LENIENT) {
/* 584 */       long l1 = Math.subtractExact(((Long)paramMap.remove(ChronoField.MONTH_OF_YEAR)).longValue(), 1L);
/* 585 */       long l2 = Math.subtractExact(((Long)paramMap.remove(ChronoField.ALIGNED_WEEK_OF_MONTH)).longValue(), 1L);
/* 586 */       long l3 = Math.subtractExact(((Long)paramMap.remove(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH)).longValue(), 1L);
/* 587 */       return date(i, 1, 1).plus(l1, ChronoUnit.MONTHS).plus(l2, ChronoUnit.WEEKS).plus(l3, ChronoUnit.DAYS);
/*     */     } 
/* 589 */     int j = range(ChronoField.MONTH_OF_YEAR).checkValidIntValue(((Long)paramMap.remove(ChronoField.MONTH_OF_YEAR)).longValue(), ChronoField.MONTH_OF_YEAR);
/* 590 */     int k = range(ChronoField.ALIGNED_WEEK_OF_MONTH).checkValidIntValue(((Long)paramMap.remove(ChronoField.ALIGNED_WEEK_OF_MONTH)).longValue(), ChronoField.ALIGNED_WEEK_OF_MONTH);
/* 591 */     int m = range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH).checkValidIntValue(((Long)paramMap.remove(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH)).longValue(), ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH);
/* 592 */     ChronoLocalDate chronoLocalDate = date(i, j, 1).plus(((k - 1) * 7 + m - 1), ChronoUnit.DAYS);
/* 593 */     if (paramResolverStyle == ResolverStyle.STRICT && chronoLocalDate.get(ChronoField.MONTH_OF_YEAR) != j) {
/* 594 */       throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
/*     */     }
/* 596 */     return chronoLocalDate;
/*     */   }
/*     */   
/*     */   ChronoLocalDate resolveYMAD(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 600 */     int i = range(ChronoField.YEAR).checkValidIntValue(((Long)paramMap.remove(ChronoField.YEAR)).longValue(), ChronoField.YEAR);
/* 601 */     if (paramResolverStyle == ResolverStyle.LENIENT) {
/* 602 */       long l1 = Math.subtractExact(((Long)paramMap.remove(ChronoField.MONTH_OF_YEAR)).longValue(), 1L);
/* 603 */       long l2 = Math.subtractExact(((Long)paramMap.remove(ChronoField.ALIGNED_WEEK_OF_MONTH)).longValue(), 1L);
/* 604 */       long l3 = Math.subtractExact(((Long)paramMap.remove(ChronoField.DAY_OF_WEEK)).longValue(), 1L);
/* 605 */       return resolveAligned(date(i, 1, 1), l1, l2, l3);
/*     */     } 
/* 607 */     int j = range(ChronoField.MONTH_OF_YEAR).checkValidIntValue(((Long)paramMap.remove(ChronoField.MONTH_OF_YEAR)).longValue(), ChronoField.MONTH_OF_YEAR);
/* 608 */     int k = range(ChronoField.ALIGNED_WEEK_OF_MONTH).checkValidIntValue(((Long)paramMap.remove(ChronoField.ALIGNED_WEEK_OF_MONTH)).longValue(), ChronoField.ALIGNED_WEEK_OF_MONTH);
/* 609 */     int m = range(ChronoField.DAY_OF_WEEK).checkValidIntValue(((Long)paramMap.remove(ChronoField.DAY_OF_WEEK)).longValue(), ChronoField.DAY_OF_WEEK);
/* 610 */     ChronoLocalDate chronoLocalDate = date(i, j, 1).plus(((k - 1) * 7), ChronoUnit.DAYS).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(m)));
/* 611 */     if (paramResolverStyle == ResolverStyle.STRICT && chronoLocalDate.get(ChronoField.MONTH_OF_YEAR) != j) {
/* 612 */       throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
/*     */     }
/* 614 */     return chronoLocalDate;
/*     */   }
/*     */   
/*     */   ChronoLocalDate resolveYAA(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 618 */     int i = range(ChronoField.YEAR).checkValidIntValue(((Long)paramMap.remove(ChronoField.YEAR)).longValue(), ChronoField.YEAR);
/* 619 */     if (paramResolverStyle == ResolverStyle.LENIENT) {
/* 620 */       long l1 = Math.subtractExact(((Long)paramMap.remove(ChronoField.ALIGNED_WEEK_OF_YEAR)).longValue(), 1L);
/* 621 */       long l2 = Math.subtractExact(((Long)paramMap.remove(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR)).longValue(), 1L);
/* 622 */       return dateYearDay(i, 1).plus(l1, ChronoUnit.WEEKS).plus(l2, ChronoUnit.DAYS);
/*     */     } 
/* 624 */     int j = range(ChronoField.ALIGNED_WEEK_OF_YEAR).checkValidIntValue(((Long)paramMap.remove(ChronoField.ALIGNED_WEEK_OF_YEAR)).longValue(), ChronoField.ALIGNED_WEEK_OF_YEAR);
/* 625 */     int k = range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR).checkValidIntValue(((Long)paramMap.remove(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR)).longValue(), ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR);
/* 626 */     ChronoLocalDate chronoLocalDate = dateYearDay(i, 1).plus(((j - 1) * 7 + k - 1), ChronoUnit.DAYS);
/* 627 */     if (paramResolverStyle == ResolverStyle.STRICT && chronoLocalDate.get(ChronoField.YEAR) != i) {
/* 628 */       throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
/*     */     }
/* 630 */     return chronoLocalDate;
/*     */   }
/*     */   
/*     */   ChronoLocalDate resolveYAD(Map<TemporalField, Long> paramMap, ResolverStyle paramResolverStyle) {
/* 634 */     int i = range(ChronoField.YEAR).checkValidIntValue(((Long)paramMap.remove(ChronoField.YEAR)).longValue(), ChronoField.YEAR);
/* 635 */     if (paramResolverStyle == ResolverStyle.LENIENT) {
/* 636 */       long l1 = Math.subtractExact(((Long)paramMap.remove(ChronoField.ALIGNED_WEEK_OF_YEAR)).longValue(), 1L);
/* 637 */       long l2 = Math.subtractExact(((Long)paramMap.remove(ChronoField.DAY_OF_WEEK)).longValue(), 1L);
/* 638 */       return resolveAligned(dateYearDay(i, 1), 0L, l1, l2);
/*     */     } 
/* 640 */     int j = range(ChronoField.ALIGNED_WEEK_OF_YEAR).checkValidIntValue(((Long)paramMap.remove(ChronoField.ALIGNED_WEEK_OF_YEAR)).longValue(), ChronoField.ALIGNED_WEEK_OF_YEAR);
/* 641 */     int k = range(ChronoField.DAY_OF_WEEK).checkValidIntValue(((Long)paramMap.remove(ChronoField.DAY_OF_WEEK)).longValue(), ChronoField.DAY_OF_WEEK);
/* 642 */     ChronoLocalDate chronoLocalDate = dateYearDay(i, 1).plus(((j - 1) * 7), ChronoUnit.DAYS).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(k)));
/* 643 */     if (paramResolverStyle == ResolverStyle.STRICT && chronoLocalDate.get(ChronoField.YEAR) != i) {
/* 644 */       throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
/*     */     }
/* 646 */     return chronoLocalDate;
/*     */   }
/*     */   
/*     */   ChronoLocalDate resolveAligned(ChronoLocalDate paramChronoLocalDate, long paramLong1, long paramLong2, long paramLong3) {
/* 650 */     ChronoLocalDate chronoLocalDate = paramChronoLocalDate.plus(paramLong1, ChronoUnit.MONTHS).plus(paramLong2, ChronoUnit.WEEKS);
/* 651 */     if (paramLong3 > 7L) {
/* 652 */       chronoLocalDate = chronoLocalDate.plus((paramLong3 - 1L) / 7L, ChronoUnit.WEEKS);
/* 653 */       paramLong3 = (paramLong3 - 1L) % 7L + 1L;
/* 654 */     } else if (paramLong3 < 1L) {
/* 655 */       chronoLocalDate = chronoLocalDate.plus(Math.subtractExact(paramLong3, 7L) / 7L, ChronoUnit.WEEKS);
/* 656 */       paramLong3 = (paramLong3 + 6L) % 7L + 1L;
/*     */     } 
/* 658 */     return chronoLocalDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.of((int)paramLong3)));
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
/*     */ 
/*     */   
/*     */   void addFieldValue(Map<TemporalField, Long> paramMap, ChronoField paramChronoField, long paramLong) {
/* 674 */     Long long_ = paramMap.get(paramChronoField);
/* 675 */     if (long_ != null && long_.longValue() != paramLong) {
/* 676 */       throw new DateTimeException("Conflict found: " + paramChronoField + " " + long_ + " differs from " + paramChronoField + " " + paramLong);
/*     */     }
/* 678 */     paramMap.put(paramChronoField, Long.valueOf(paramLong));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Chronology paramChronology) {
/* 698 */     return getId().compareTo(paramChronology.getId());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 715 */     if (this == paramObject) {
/* 716 */       return true;
/*     */     }
/* 718 */     if (paramObject instanceof AbstractChronology) {
/* 719 */       return (compareTo((AbstractChronology)paramObject) == 0);
/*     */     }
/* 721 */     return false;
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
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 737 */     return getClass().hashCode() ^ getId().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 748 */     return getId();
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
/*     */   
/*     */   Object writeReplace() {
/* 763 */     return new Ser((byte)1, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ObjectStreamException {
/* 773 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*     */   }
/*     */   
/*     */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 777 */     paramDataOutput.writeUTF(getId());
/*     */   }
/*     */   
/*     */   static Chronology readExternal(DataInput paramDataInput) throws IOException {
/* 781 */     String str = paramDataInput.readUTF();
/* 782 */     return Chronology.of(str);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/AbstractChronology.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */