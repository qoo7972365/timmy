/*      */ package java.time.format;
/*      */ 
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.math.RoundingMode;
/*      */ import java.text.ParsePosition;
/*      */ import java.time.DateTimeException;
/*      */ import java.time.Instant;
/*      */ import java.time.LocalDate;
/*      */ import java.time.LocalDateTime;
/*      */ import java.time.ZoneId;
/*      */ import java.time.ZoneOffset;
/*      */ import java.time.chrono.ChronoLocalDate;
/*      */ import java.time.chrono.Chronology;
/*      */ import java.time.chrono.IsoChronology;
/*      */ import java.time.temporal.ChronoField;
/*      */ import java.time.temporal.IsoFields;
/*      */ import java.time.temporal.TemporalAccessor;
/*      */ import java.time.temporal.TemporalField;
/*      */ import java.time.temporal.TemporalQueries;
/*      */ import java.time.temporal.TemporalQuery;
/*      */ import java.time.temporal.ValueRange;
/*      */ import java.time.temporal.WeekFields;
/*      */ import java.time.zone.ZoneRulesProvider;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import sun.util.locale.provider.LocaleProviderAdapter;
/*      */ import sun.util.locale.provider.LocaleResources;
/*      */ import sun.util.locale.provider.TimeZoneNameUtility;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class DateTimeFormatterBuilder
/*      */ {
/*      */   private static final TemporalQuery<ZoneId> QUERY_REGION_ONLY;
/*      */   
/*      */   static {
/*  157 */     QUERY_REGION_ONLY = (paramTemporalAccessor -> {
/*      */         ZoneId zoneId = paramTemporalAccessor.<ZoneId>query(TemporalQueries.zoneId());
/*  159 */         return (zoneId != null && !(zoneId instanceof ZoneOffset)) ? zoneId : null;
/*      */       });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  165 */   private DateTimeFormatterBuilder active = this;
/*      */ 
/*      */ 
/*      */   
/*      */   private final DateTimeFormatterBuilder parent;
/*      */ 
/*      */ 
/*      */   
/*  173 */   private final List<DateTimePrinterParser> printerParsers = new ArrayList<>();
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean optional;
/*      */ 
/*      */ 
/*      */   
/*      */   private int padNextWidth;
/*      */ 
/*      */ 
/*      */   
/*      */   private char padNextChar;
/*      */ 
/*      */ 
/*      */   
/*  189 */   private int valueParserIndex = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getLocalizedDateTimePattern(FormatStyle paramFormatStyle1, FormatStyle paramFormatStyle2, Chronology paramChronology, Locale paramLocale) {
/*  205 */     Objects.requireNonNull(paramLocale, "locale");
/*  206 */     Objects.requireNonNull(paramChronology, "chrono");
/*  207 */     if (paramFormatStyle1 == null && paramFormatStyle2 == null) {
/*  208 */       throw new IllegalArgumentException("Either dateStyle or timeStyle must be non-null");
/*      */     }
/*  210 */     LocaleResources localeResources = LocaleProviderAdapter.getResourceBundleBased().getLocaleResources(paramLocale);
/*  211 */     return localeResources.getJavaTimeDateTimePattern(
/*  212 */         convertStyle(paramFormatStyle2), convertStyle(paramFormatStyle1), paramChronology.getCalendarType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int convertStyle(FormatStyle paramFormatStyle) {
/*  223 */     if (paramFormatStyle == null) {
/*  224 */       return -1;
/*      */     }
/*  226 */     return paramFormatStyle.ordinal();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder() {
/*  234 */     this.parent = null;
/*  235 */     this.optional = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DateTimeFormatterBuilder(DateTimeFormatterBuilder paramDateTimeFormatterBuilder, boolean paramBoolean) {
/*  246 */     this.parent = paramDateTimeFormatterBuilder;
/*  247 */     this.optional = paramBoolean;
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
/*      */   public DateTimeFormatterBuilder parseCaseSensitive() {
/*  270 */     appendInternal(SettingsParser.SENSITIVE);
/*  271 */     return this;
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
/*      */   public DateTimeFormatterBuilder parseCaseInsensitive() {
/*  290 */     appendInternal(SettingsParser.INSENSITIVE);
/*  291 */     return this;
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
/*      */   public DateTimeFormatterBuilder parseStrict() {
/*  309 */     appendInternal(SettingsParser.STRICT);
/*  310 */     return this;
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
/*      */   public DateTimeFormatterBuilder parseLenient() {
/*  328 */     appendInternal(SettingsParser.LENIENT);
/*  329 */     return this;
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
/*      */   public DateTimeFormatterBuilder parseDefaulting(TemporalField paramTemporalField, long paramLong) {
/*  361 */     Objects.requireNonNull(paramTemporalField, "field");
/*  362 */     appendInternal(new DefaultValueParser(paramTemporalField, paramLong));
/*  363 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendValue(TemporalField paramTemporalField) {
/*  386 */     Objects.requireNonNull(paramTemporalField, "field");
/*  387 */     appendValue(new NumberPrinterParser(paramTemporalField, 1, 19, SignStyle.NORMAL));
/*  388 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendValue(TemporalField paramTemporalField, int paramInt) {
/*  440 */     Objects.requireNonNull(paramTemporalField, "field");
/*  441 */     if (paramInt < 1 || paramInt > 19) {
/*  442 */       throw new IllegalArgumentException("The width must be from 1 to 19 inclusive but was " + paramInt);
/*      */     }
/*  444 */     NumberPrinterParser numberPrinterParser = new NumberPrinterParser(paramTemporalField, paramInt, paramInt, SignStyle.NOT_NEGATIVE);
/*  445 */     appendValue(numberPrinterParser);
/*  446 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendValue(TemporalField paramTemporalField, int paramInt1, int paramInt2, SignStyle paramSignStyle) {
/*  482 */     if (paramInt1 == paramInt2 && paramSignStyle == SignStyle.NOT_NEGATIVE) {
/*  483 */       return appendValue(paramTemporalField, paramInt2);
/*      */     }
/*  485 */     Objects.requireNonNull(paramTemporalField, "field");
/*  486 */     Objects.requireNonNull(paramSignStyle, "signStyle");
/*  487 */     if (paramInt1 < 1 || paramInt1 > 19) {
/*  488 */       throw new IllegalArgumentException("The minimum width must be from 1 to 19 inclusive but was " + paramInt1);
/*      */     }
/*  490 */     if (paramInt2 < 1 || paramInt2 > 19) {
/*  491 */       throw new IllegalArgumentException("The maximum width must be from 1 to 19 inclusive but was " + paramInt2);
/*      */     }
/*  493 */     if (paramInt2 < paramInt1) {
/*  494 */       throw new IllegalArgumentException("The maximum width must exceed or equal the minimum width but " + paramInt2 + " < " + paramInt1);
/*      */     }
/*      */     
/*  497 */     NumberPrinterParser numberPrinterParser = new NumberPrinterParser(paramTemporalField, paramInt1, paramInt2, paramSignStyle);
/*  498 */     appendValue(numberPrinterParser);
/*  499 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendValueReduced(TemporalField paramTemporalField, int paramInt1, int paramInt2, int paramInt3) {
/*  543 */     Objects.requireNonNull(paramTemporalField, "field");
/*  544 */     ReducedPrinterParser reducedPrinterParser = new ReducedPrinterParser(paramTemporalField, paramInt1, paramInt2, paramInt3, null);
/*  545 */     appendValue(reducedPrinterParser);
/*  546 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendValueReduced(TemporalField paramTemporalField, int paramInt1, int paramInt2, ChronoLocalDate paramChronoLocalDate) {
/*  603 */     Objects.requireNonNull(paramTemporalField, "field");
/*  604 */     Objects.requireNonNull(paramChronoLocalDate, "baseDate");
/*  605 */     ReducedPrinterParser reducedPrinterParser = new ReducedPrinterParser(paramTemporalField, paramInt1, paramInt2, 0, paramChronoLocalDate);
/*  606 */     appendValue(reducedPrinterParser);
/*  607 */     return this;
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
/*      */   private DateTimeFormatterBuilder appendValue(NumberPrinterParser paramNumberPrinterParser) {
/*  625 */     if (this.active.valueParserIndex >= 0) {
/*  626 */       int i = this.active.valueParserIndex;
/*      */ 
/*      */       
/*  629 */       NumberPrinterParser numberPrinterParser = (NumberPrinterParser)this.active.printerParsers.get(i);
/*  630 */       if (paramNumberPrinterParser.minWidth == paramNumberPrinterParser.maxWidth && paramNumberPrinterParser.signStyle == SignStyle.NOT_NEGATIVE) {
/*      */         
/*  632 */         numberPrinterParser = numberPrinterParser.withSubsequentWidth(paramNumberPrinterParser.maxWidth);
/*      */         
/*  634 */         appendInternal(paramNumberPrinterParser.withFixedWidth());
/*      */         
/*  636 */         this.active.valueParserIndex = i;
/*      */       } else {
/*      */         
/*  639 */         numberPrinterParser = numberPrinterParser.withFixedWidth();
/*      */         
/*  641 */         this.active.valueParserIndex = appendInternal(paramNumberPrinterParser);
/*      */       } 
/*      */       
/*  644 */       this.active.printerParsers.set(i, numberPrinterParser);
/*      */     } else {
/*      */       
/*  647 */       this.active.valueParserIndex = appendInternal(paramNumberPrinterParser);
/*      */     } 
/*  649 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendFraction(TemporalField paramTemporalField, int paramInt1, int paramInt2, boolean paramBoolean) {
/*  687 */     appendInternal(new FractionPrinterParser(paramTemporalField, paramInt1, paramInt2, paramBoolean));
/*  688 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendText(TemporalField paramTemporalField) {
/*  708 */     return appendText(paramTemporalField, TextStyle.FULL);
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
/*      */   public DateTimeFormatterBuilder appendText(TemporalField paramTemporalField, TextStyle paramTextStyle) {
/*  727 */     Objects.requireNonNull(paramTemporalField, "field");
/*  728 */     Objects.requireNonNull(paramTextStyle, "textStyle");
/*  729 */     appendInternal(new TextPrinterParser(paramTemporalField, paramTextStyle, DateTimeTextProvider.getInstance()));
/*  730 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendText(TemporalField paramTemporalField, Map<Long, String> paramMap) {
/*  768 */     Objects.requireNonNull(paramTemporalField, "field");
/*  769 */     Objects.requireNonNull(paramMap, "textLookup");
/*  770 */     LinkedHashMap<Long, String> linkedHashMap = new LinkedHashMap<>(paramMap);
/*  771 */     Map<TextStyle, LinkedHashMap<Long, String>> map = Collections.singletonMap(TextStyle.FULL, linkedHashMap);
/*  772 */     final DateTimeTextProvider.LocaleStore store = new DateTimeTextProvider.LocaleStore((Map)map);
/*  773 */     DateTimeTextProvider dateTimeTextProvider = new DateTimeTextProvider()
/*      */       {
/*      */         public String getText(Chronology param1Chronology, TemporalField param1TemporalField, long param1Long, TextStyle param1TextStyle, Locale param1Locale)
/*      */         {
/*  777 */           return store.getText(param1Long, param1TextStyle);
/*      */         }
/*      */         
/*      */         public String getText(TemporalField param1TemporalField, long param1Long, TextStyle param1TextStyle, Locale param1Locale) {
/*  781 */           return store.getText(param1Long, param1TextStyle);
/*      */         }
/*      */ 
/*      */         
/*      */         public Iterator<Map.Entry<String, Long>> getTextIterator(Chronology param1Chronology, TemporalField param1TemporalField, TextStyle param1TextStyle, Locale param1Locale) {
/*  786 */           return store.getTextIterator(param1TextStyle);
/*      */         }
/*      */ 
/*      */         
/*      */         public Iterator<Map.Entry<String, Long>> getTextIterator(TemporalField param1TemporalField, TextStyle param1TextStyle, Locale param1Locale) {
/*  791 */           return store.getTextIterator(param1TextStyle);
/*      */         }
/*      */       };
/*  794 */     appendInternal(new TextPrinterParser(paramTemporalField, TextStyle.FULL, dateTimeTextProvider));
/*  795 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendInstant() {
/*  825 */     appendInternal(new InstantPrinterParser(-2));
/*  826 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendInstant(int paramInt) {
/*  865 */     if (paramInt < -1 || paramInt > 9) {
/*  866 */       throw new IllegalArgumentException("The fractional digits must be from -1 to 9 inclusive but was " + paramInt);
/*      */     }
/*  868 */     appendInternal(new InstantPrinterParser(paramInt));
/*  869 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendOffsetId() {
/*  882 */     appendInternal(OffsetIdPrinterParser.INSTANCE_ID_Z);
/*  883 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendOffset(String paramString1, String paramString2) {
/*  925 */     appendInternal(new OffsetIdPrinterParser(paramString1, paramString2));
/*  926 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendLocalizedOffset(TextStyle paramTextStyle) {
/*  959 */     Objects.requireNonNull(paramTextStyle, "style");
/*  960 */     if (paramTextStyle != TextStyle.FULL && paramTextStyle != TextStyle.SHORT) {
/*  961 */       throw new IllegalArgumentException("Style must be either full or short");
/*      */     }
/*  963 */     appendInternal(new LocalizedOffsetIdPrinterParser(paramTextStyle));
/*  964 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendZoneId() {
/* 1015 */     appendInternal(new ZoneIdPrinterParser(TemporalQueries.zoneId(), "ZoneId()"));
/* 1016 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendZoneRegionId() {
/* 1071 */     appendInternal(new ZoneIdPrinterParser(QUERY_REGION_ONLY, "ZoneRegionId()"));
/* 1072 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendZoneOrOffsetId() {
/* 1127 */     appendInternal(new ZoneIdPrinterParser(TemporalQueries.zone(), "ZoneOrOffsetId()"));
/* 1128 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendZoneText(TextStyle paramTextStyle) {
/* 1163 */     appendInternal(new ZoneTextPrinterParser(paramTextStyle, null));
/* 1164 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendZoneText(TextStyle paramTextStyle, Set<ZoneId> paramSet) {
/* 1205 */     Objects.requireNonNull(paramSet, "preferredZones");
/* 1206 */     appendInternal(new ZoneTextPrinterParser(paramTextStyle, paramSet));
/* 1207 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendChronologyId() {
/* 1231 */     appendInternal(new ChronoPrinterParser(null));
/* 1232 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendChronologyText(TextStyle paramTextStyle) {
/* 1245 */     Objects.requireNonNull(paramTextStyle, "textStyle");
/* 1246 */     appendInternal(new ChronoPrinterParser(paramTextStyle));
/* 1247 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendLocalized(FormatStyle paramFormatStyle1, FormatStyle paramFormatStyle2) {
/* 1280 */     if (paramFormatStyle1 == null && paramFormatStyle2 == null) {
/* 1281 */       throw new IllegalArgumentException("Either the date or time style must be non-null");
/*      */     }
/* 1283 */     appendInternal(new LocalizedPrinterParser(paramFormatStyle1, paramFormatStyle2));
/* 1284 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendLiteral(char paramChar) {
/* 1297 */     appendInternal(new CharLiteralPrinterParser(paramChar));
/* 1298 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendLiteral(String paramString) {
/* 1312 */     Objects.requireNonNull(paramString, "literal");
/* 1313 */     if (paramString.length() > 0) {
/* 1314 */       if (paramString.length() == 1) {
/* 1315 */         appendInternal(new CharLiteralPrinterParser(paramString.charAt(0)));
/*      */       } else {
/* 1317 */         appendInternal(new StringLiteralPrinterParser(paramString));
/*      */       } 
/*      */     }
/* 1320 */     return this;
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
/*      */   public DateTimeFormatterBuilder append(DateTimeFormatter paramDateTimeFormatter) {
/* 1334 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/* 1335 */     appendInternal(paramDateTimeFormatter.toPrinterParser(false));
/* 1336 */     return this;
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
/*      */   public DateTimeFormatterBuilder appendOptional(DateTimeFormatter paramDateTimeFormatter) {
/* 1353 */     Objects.requireNonNull(paramDateTimeFormatter, "formatter");
/* 1354 */     appendInternal(paramDateTimeFormatter.toPrinterParser(true));
/* 1355 */     return this;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatterBuilder appendPattern(String paramString) {
/* 1580 */     Objects.requireNonNull(paramString, "pattern");
/* 1581 */     parsePattern(paramString);
/* 1582 */     return this;
/*      */   }
/*      */   
/*      */   private void parsePattern(String paramString) {
/* 1586 */     for (byte b = 0; b < paramString.length(); b++) {
/* 1587 */       char c = paramString.charAt(b);
/* 1588 */       if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
/* 1589 */         byte b1 = b++;
/* 1590 */         for (; b < paramString.length() && paramString.charAt(b) == c; b++);
/* 1591 */         int i = b - b1;
/*      */         
/* 1593 */         if (c == 'p') {
/* 1594 */           int j = 0;
/* 1595 */           if (b < paramString.length()) {
/* 1596 */             c = paramString.charAt(b);
/* 1597 */             if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
/* 1598 */               j = i;
/* 1599 */               b1 = b++;
/* 1600 */               for (; b < paramString.length() && paramString.charAt(b) == c; b++);
/* 1601 */               i = b - b1;
/*      */             } 
/*      */           } 
/* 1604 */           if (j == 0) {
/* 1605 */             throw new IllegalArgumentException("Pad letter 'p' must be followed by valid pad pattern: " + paramString);
/*      */           }
/*      */           
/* 1608 */           padNext(j);
/*      */         } 
/*      */         
/* 1611 */         TemporalField temporalField = FIELD_MAP.get(Character.valueOf(c));
/* 1612 */         if (temporalField != null) {
/* 1613 */           parseField(c, i, temporalField);
/* 1614 */         } else if (c == 'z') {
/* 1615 */           if (i > 4)
/* 1616 */             throw new IllegalArgumentException("Too many pattern letters: " + c); 
/* 1617 */           if (i == 4) {
/* 1618 */             appendZoneText(TextStyle.FULL);
/*      */           } else {
/* 1620 */             appendZoneText(TextStyle.SHORT);
/*      */           } 
/* 1622 */         } else if (c == 'V') {
/* 1623 */           if (i != 2) {
/* 1624 */             throw new IllegalArgumentException("Pattern letter count must be 2: " + c);
/*      */           }
/* 1626 */           appendZoneId();
/* 1627 */         } else if (c == 'Z') {
/* 1628 */           if (i < 4) {
/* 1629 */             appendOffset("+HHMM", "+0000");
/* 1630 */           } else if (i == 4) {
/* 1631 */             appendLocalizedOffset(TextStyle.FULL);
/* 1632 */           } else if (i == 5) {
/* 1633 */             appendOffset("+HH:MM:ss", "Z");
/*      */           } else {
/* 1635 */             throw new IllegalArgumentException("Too many pattern letters: " + c);
/*      */           } 
/* 1637 */         } else if (c == 'O') {
/* 1638 */           if (i == 1) {
/* 1639 */             appendLocalizedOffset(TextStyle.SHORT);
/* 1640 */           } else if (i == 4) {
/* 1641 */             appendLocalizedOffset(TextStyle.FULL);
/*      */           } else {
/* 1643 */             throw new IllegalArgumentException("Pattern letter count must be 1 or 4: " + c);
/*      */           } 
/* 1645 */         } else if (c == 'X') {
/* 1646 */           if (i > 5) {
/* 1647 */             throw new IllegalArgumentException("Too many pattern letters: " + c);
/*      */           }
/* 1649 */           appendOffset(OffsetIdPrinterParser.PATTERNS[i + ((i == 1) ? 0 : 1)], "Z");
/* 1650 */         } else if (c == 'x') {
/* 1651 */           if (i > 5) {
/* 1652 */             throw new IllegalArgumentException("Too many pattern letters: " + c);
/*      */           }
/* 1654 */           String str = (i == 1) ? "+00" : ((i % 2 == 0) ? "+0000" : "+00:00");
/* 1655 */           appendOffset(OffsetIdPrinterParser.PATTERNS[i + ((i == 1) ? 0 : 1)], str);
/* 1656 */         } else if (c == 'W') {
/*      */           
/* 1658 */           if (i > 1) {
/* 1659 */             throw new IllegalArgumentException("Too many pattern letters: " + c);
/*      */           }
/* 1661 */           appendInternal(new WeekBasedFieldPrinterParser(c, i));
/* 1662 */         } else if (c == 'w') {
/*      */           
/* 1664 */           if (i > 2) {
/* 1665 */             throw new IllegalArgumentException("Too many pattern letters: " + c);
/*      */           }
/* 1667 */           appendInternal(new WeekBasedFieldPrinterParser(c, i));
/* 1668 */         } else if (c == 'Y') {
/*      */           
/* 1670 */           appendInternal(new WeekBasedFieldPrinterParser(c, i));
/*      */         } else {
/* 1672 */           throw new IllegalArgumentException("Unknown pattern letter: " + c);
/*      */         } 
/* 1674 */         b--;
/*      */       }
/* 1676 */       else if (c == '\'') {
/*      */         
/* 1678 */         byte b1 = b++;
/* 1679 */         for (; b < paramString.length(); b++) {
/* 1680 */           if (paramString.charAt(b) == '\'') {
/* 1681 */             if (b + 1 < paramString.length() && paramString.charAt(b + 1) == '\'') {
/* 1682 */               b++;
/*      */             } else {
/*      */               break;
/*      */             } 
/*      */           }
/*      */         } 
/* 1688 */         if (b >= paramString.length()) {
/* 1689 */           throw new IllegalArgumentException("Pattern ends with an incomplete string literal: " + paramString);
/*      */         }
/* 1691 */         String str = paramString.substring(b1 + 1, b);
/* 1692 */         if (str.length() == 0) {
/* 1693 */           appendLiteral('\'');
/*      */         } else {
/* 1695 */           appendLiteral(str.replace("''", "'"));
/*      */         }
/*      */       
/* 1698 */       } else if (c == '[') {
/* 1699 */         optionalStart();
/*      */       }
/* 1701 */       else if (c == ']') {
/* 1702 */         if (this.active.parent == null) {
/* 1703 */           throw new IllegalArgumentException("Pattern invalid as it contains ] without previous [");
/*      */         }
/* 1705 */         optionalEnd();
/*      */       } else {
/* 1707 */         if (c == '{' || c == '}' || c == '#') {
/* 1708 */           throw new IllegalArgumentException("Pattern includes reserved character: '" + c + "'");
/*      */         }
/* 1710 */         appendLiteral(c);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void parseField(char paramChar, int paramInt, TemporalField paramTemporalField) {
/* 1717 */     boolean bool = false;
/* 1718 */     switch (paramChar) {
/*      */       case 'u':
/*      */       case 'y':
/* 1721 */         if (paramInt == 2) {
/* 1722 */           appendValueReduced(paramTemporalField, 2, 2, ReducedPrinterParser.BASE_DATE);
/* 1723 */         } else if (paramInt < 4) {
/* 1724 */           appendValue(paramTemporalField, paramInt, 19, SignStyle.NORMAL);
/*      */         } else {
/* 1726 */           appendValue(paramTemporalField, paramInt, 19, SignStyle.EXCEEDS_PAD);
/*      */         } 
/*      */         return;
/*      */       case 'c':
/* 1730 */         if (paramInt == 2) {
/* 1731 */           throw new IllegalArgumentException("Invalid pattern \"cc\"");
/*      */         }
/*      */       
/*      */       case 'L':
/*      */       case 'q':
/* 1736 */         bool = true;
/*      */       
/*      */       case 'E':
/*      */       case 'M':
/*      */       case 'Q':
/*      */       case 'e':
/* 1742 */         switch (paramInt) {
/*      */           case 1:
/*      */           case 2:
/* 1745 */             if (paramChar == 'c' || paramChar == 'e') {
/* 1746 */               appendInternal(new WeekBasedFieldPrinterParser(paramChar, paramInt));
/* 1747 */             } else if (paramChar == 'E') {
/* 1748 */               appendText(paramTemporalField, TextStyle.SHORT);
/*      */             }
/* 1750 */             else if (paramInt == 1) {
/* 1751 */               appendValue(paramTemporalField);
/*      */             } else {
/* 1753 */               appendValue(paramTemporalField, 2);
/*      */             } 
/*      */             return;
/*      */           
/*      */           case 3:
/* 1758 */             appendText(paramTemporalField, bool ? TextStyle.SHORT_STANDALONE : TextStyle.SHORT);
/*      */             return;
/*      */           case 4:
/* 1761 */             appendText(paramTemporalField, bool ? TextStyle.FULL_STANDALONE : TextStyle.FULL);
/*      */             return;
/*      */           case 5:
/* 1764 */             appendText(paramTemporalField, bool ? TextStyle.NARROW_STANDALONE : TextStyle.NARROW);
/*      */             return;
/*      */         } 
/* 1767 */         throw new IllegalArgumentException("Too many pattern letters: " + paramChar);
/*      */ 
/*      */       
/*      */       case 'a':
/* 1771 */         if (paramInt == 1) {
/* 1772 */           appendText(paramTemporalField, TextStyle.SHORT);
/*      */         } else {
/* 1774 */           throw new IllegalArgumentException("Too many pattern letters: " + paramChar);
/*      */         } 
/*      */         return;
/*      */       case 'G':
/* 1778 */         switch (paramInt) {
/*      */           case 1:
/*      */           case 2:
/*      */           case 3:
/* 1782 */             appendText(paramTemporalField, TextStyle.SHORT);
/*      */             return;
/*      */           case 4:
/* 1785 */             appendText(paramTemporalField, TextStyle.FULL);
/*      */             return;
/*      */           case 5:
/* 1788 */             appendText(paramTemporalField, TextStyle.NARROW);
/*      */             return;
/*      */         } 
/* 1791 */         throw new IllegalArgumentException("Too many pattern letters: " + paramChar);
/*      */ 
/*      */       
/*      */       case 'S':
/* 1795 */         appendFraction(ChronoField.NANO_OF_SECOND, paramInt, paramInt, false);
/*      */         return;
/*      */       case 'F':
/* 1798 */         if (paramInt == 1) {
/* 1799 */           appendValue(paramTemporalField);
/*      */         } else {
/* 1801 */           throw new IllegalArgumentException("Too many pattern letters: " + paramChar);
/*      */         } 
/*      */         return;
/*      */       case 'H':
/*      */       case 'K':
/*      */       case 'd':
/*      */       case 'h':
/*      */       case 'k':
/*      */       case 'm':
/*      */       case 's':
/* 1811 */         if (paramInt == 1) {
/* 1812 */           appendValue(paramTemporalField);
/* 1813 */         } else if (paramInt == 2) {
/* 1814 */           appendValue(paramTemporalField, paramInt);
/*      */         } else {
/* 1816 */           throw new IllegalArgumentException("Too many pattern letters: " + paramChar);
/*      */         } 
/*      */         return;
/*      */       case 'D':
/* 1820 */         if (paramInt == 1) {
/* 1821 */           appendValue(paramTemporalField);
/* 1822 */         } else if (paramInt <= 3) {
/* 1823 */           appendValue(paramTemporalField, paramInt);
/*      */         } else {
/* 1825 */           throw new IllegalArgumentException("Too many pattern letters: " + paramChar);
/*      */         } 
/*      */         return;
/*      */     } 
/* 1829 */     if (paramInt == 1) {
/* 1830 */       appendValue(paramTemporalField);
/*      */     } else {
/* 1832 */       appendValue(paramTemporalField, paramInt);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1839 */   private static final Map<Character, TemporalField> FIELD_MAP = new HashMap<>();
/*      */   
/*      */   static {
/* 1842 */     FIELD_MAP.put(Character.valueOf('G'), ChronoField.ERA);
/* 1843 */     FIELD_MAP.put(Character.valueOf('y'), ChronoField.YEAR_OF_ERA);
/* 1844 */     FIELD_MAP.put(Character.valueOf('u'), ChronoField.YEAR);
/* 1845 */     FIELD_MAP.put(Character.valueOf('Q'), IsoFields.QUARTER_OF_YEAR);
/* 1846 */     FIELD_MAP.put(Character.valueOf('q'), IsoFields.QUARTER_OF_YEAR);
/* 1847 */     FIELD_MAP.put(Character.valueOf('M'), ChronoField.MONTH_OF_YEAR);
/* 1848 */     FIELD_MAP.put(Character.valueOf('L'), ChronoField.MONTH_OF_YEAR);
/* 1849 */     FIELD_MAP.put(Character.valueOf('D'), ChronoField.DAY_OF_YEAR);
/* 1850 */     FIELD_MAP.put(Character.valueOf('d'), ChronoField.DAY_OF_MONTH);
/* 1851 */     FIELD_MAP.put(Character.valueOf('F'), ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH);
/* 1852 */     FIELD_MAP.put(Character.valueOf('E'), ChronoField.DAY_OF_WEEK);
/* 1853 */     FIELD_MAP.put(Character.valueOf('c'), ChronoField.DAY_OF_WEEK);
/* 1854 */     FIELD_MAP.put(Character.valueOf('e'), ChronoField.DAY_OF_WEEK);
/* 1855 */     FIELD_MAP.put(Character.valueOf('a'), ChronoField.AMPM_OF_DAY);
/* 1856 */     FIELD_MAP.put(Character.valueOf('H'), ChronoField.HOUR_OF_DAY);
/* 1857 */     FIELD_MAP.put(Character.valueOf('k'), ChronoField.CLOCK_HOUR_OF_DAY);
/* 1858 */     FIELD_MAP.put(Character.valueOf('K'), ChronoField.HOUR_OF_AMPM);
/* 1859 */     FIELD_MAP.put(Character.valueOf('h'), ChronoField.CLOCK_HOUR_OF_AMPM);
/* 1860 */     FIELD_MAP.put(Character.valueOf('m'), ChronoField.MINUTE_OF_HOUR);
/* 1861 */     FIELD_MAP.put(Character.valueOf('s'), ChronoField.SECOND_OF_MINUTE);
/* 1862 */     FIELD_MAP.put(Character.valueOf('S'), ChronoField.NANO_OF_SECOND);
/* 1863 */     FIELD_MAP.put(Character.valueOf('A'), ChronoField.MILLI_OF_DAY);
/* 1864 */     FIELD_MAP.put(Character.valueOf('n'), ChronoField.NANO_OF_SECOND);
/* 1865 */     FIELD_MAP.put(Character.valueOf('N'), ChronoField.NANO_OF_DAY);
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
/*      */   public DateTimeFormatterBuilder padNext(int paramInt) {
/* 1900 */     return padNext(paramInt, ' ');
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
/*      */   public DateTimeFormatterBuilder padNext(int paramInt, char paramChar) {
/* 1925 */     if (paramInt < 1) {
/* 1926 */       throw new IllegalArgumentException("The pad width must be at least one but was " + paramInt);
/*      */     }
/* 1928 */     this.active.padNextWidth = paramInt;
/* 1929 */     this.active.padNextChar = paramChar;
/* 1930 */     this.active.valueParserIndex = -1;
/* 1931 */     return this;
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
/*      */   public DateTimeFormatterBuilder optionalStart() {
/* 1956 */     this.active.valueParserIndex = -1;
/* 1957 */     this.active = new DateTimeFormatterBuilder(this.active, true);
/* 1958 */     return this;
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
/*      */   public DateTimeFormatterBuilder optionalEnd() {
/* 1987 */     if (this.active.parent == null) {
/* 1988 */       throw new IllegalStateException("Cannot call optionalEnd() as there was no previous call to optionalStart()");
/*      */     }
/* 1990 */     if (this.active.printerParsers.size() > 0) {
/* 1991 */       CompositePrinterParser compositePrinterParser = new CompositePrinterParser(this.active.printerParsers, this.active.optional);
/* 1992 */       this.active = this.active.parent;
/* 1993 */       appendInternal(compositePrinterParser);
/*      */     } else {
/* 1995 */       this.active = this.active.parent;
/*      */     } 
/* 1997 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int appendInternal(DateTimePrinterParser paramDateTimePrinterParser) {
/* 2008 */     Objects.requireNonNull(paramDateTimePrinterParser, "pp");
/* 2009 */     if (this.active.padNextWidth > 0) {
/* 2010 */       if (paramDateTimePrinterParser != null) {
/* 2011 */         paramDateTimePrinterParser = new PadPrinterParserDecorator(paramDateTimePrinterParser, this.active.padNextWidth, this.active.padNextChar);
/*      */       }
/* 2013 */       this.active.padNextWidth = 0;
/* 2014 */       this.active.padNextChar = Character.MIN_VALUE;
/*      */     } 
/* 2016 */     this.active.printerParsers.add(paramDateTimePrinterParser);
/* 2017 */     this.active.valueParserIndex = -1;
/* 2018 */     return this.active.printerParsers.size() - 1;
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
/*      */   public DateTimeFormatter toFormatter() {
/* 2039 */     return toFormatter(Locale.getDefault(Locale.Category.FORMAT));
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
/*      */   public DateTimeFormatter toFormatter(Locale paramLocale) {
/* 2060 */     return toFormatter(paramLocale, ResolverStyle.SMART, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   DateTimeFormatter toFormatter(ResolverStyle paramResolverStyle, Chronology paramChronology) {
/* 2071 */     return toFormatter(Locale.getDefault(Locale.Category.FORMAT), paramResolverStyle, paramChronology);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DateTimeFormatter toFormatter(Locale paramLocale, ResolverStyle paramResolverStyle, Chronology paramChronology) {
/* 2082 */     Objects.requireNonNull(paramLocale, "locale");
/* 2083 */     while (this.active.parent != null) {
/* 2084 */       optionalEnd();
/*      */     }
/* 2086 */     CompositePrinterParser compositePrinterParser = new CompositePrinterParser(this.printerParsers, false);
/* 2087 */     return new DateTimeFormatter(compositePrinterParser, paramLocale, DecimalStyle.STANDARD, paramResolverStyle, null, paramChronology, null);
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
/*      */   static final class CompositePrinterParser
/*      */     implements DateTimePrinterParser
/*      */   {
/*      */     private final DateTimeFormatterBuilder.DateTimePrinterParser[] printerParsers;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final boolean optional;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CompositePrinterParser(List<DateTimeFormatterBuilder.DateTimePrinterParser> param1List, boolean param1Boolean) {
/* 2161 */       this(param1List.<DateTimeFormatterBuilder.DateTimePrinterParser>toArray(new DateTimeFormatterBuilder.DateTimePrinterParser[param1List.size()]), param1Boolean);
/*      */     }
/*      */     
/*      */     CompositePrinterParser(DateTimeFormatterBuilder.DateTimePrinterParser[] param1ArrayOfDateTimePrinterParser, boolean param1Boolean) {
/* 2165 */       this.printerParsers = param1ArrayOfDateTimePrinterParser;
/* 2166 */       this.optional = param1Boolean;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CompositePrinterParser withOptional(boolean param1Boolean) {
/* 2176 */       if (param1Boolean == this.optional) {
/* 2177 */         return this;
/*      */       }
/* 2179 */       return new CompositePrinterParser(this.printerParsers, param1Boolean);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) {
/* 2184 */       int i = param1StringBuilder.length();
/* 2185 */       if (this.optional) {
/* 2186 */         param1DateTimePrintContext.startOptional();
/*      */       }
/*      */       try {
/* 2189 */         for (DateTimeFormatterBuilder.DateTimePrinterParser dateTimePrinterParser : this.printerParsers) {
/* 2190 */           if (!dateTimePrinterParser.format(param1DateTimePrintContext, param1StringBuilder)) {
/* 2191 */             param1StringBuilder.setLength(i);
/* 2192 */             return true;
/*      */           } 
/*      */         } 
/*      */       } finally {
/* 2196 */         if (this.optional) {
/* 2197 */           param1DateTimePrintContext.endOptional();
/*      */         }
/*      */       } 
/* 2200 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int) {
/* 2205 */       if (this.optional) {
/* 2206 */         param1DateTimeParseContext.startOptional();
/* 2207 */         int i = param1Int;
/* 2208 */         for (DateTimeFormatterBuilder.DateTimePrinterParser dateTimePrinterParser : this.printerParsers) {
/* 2209 */           i = dateTimePrinterParser.parse(param1DateTimeParseContext, param1CharSequence, i);
/* 2210 */           if (i < 0) {
/* 2211 */             param1DateTimeParseContext.endOptional(false);
/* 2212 */             return param1Int;
/*      */           } 
/*      */         } 
/* 2215 */         param1DateTimeParseContext.endOptional(true);
/* 2216 */         return i;
/*      */       } 
/* 2218 */       for (DateTimeFormatterBuilder.DateTimePrinterParser dateTimePrinterParser : this.printerParsers) {
/* 2219 */         param1Int = dateTimePrinterParser.parse(param1DateTimeParseContext, param1CharSequence, param1Int);
/* 2220 */         if (param1Int < 0) {
/*      */           break;
/*      */         }
/*      */       } 
/* 2224 */       return param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2230 */       StringBuilder stringBuilder = new StringBuilder();
/* 2231 */       if (this.printerParsers != null) {
/* 2232 */         stringBuilder.append(this.optional ? "[" : "(");
/* 2233 */         for (DateTimeFormatterBuilder.DateTimePrinterParser dateTimePrinterParser : this.printerParsers) {
/* 2234 */           stringBuilder.append(dateTimePrinterParser);
/*      */         }
/* 2236 */         stringBuilder.append(this.optional ? "]" : ")");
/*      */       } 
/* 2238 */       return stringBuilder.toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class PadPrinterParserDecorator
/*      */     implements DateTimePrinterParser
/*      */   {
/*      */     private final DateTimeFormatterBuilder.DateTimePrinterParser printerParser;
/*      */ 
/*      */ 
/*      */     
/*      */     private final int padWidth;
/*      */ 
/*      */     
/*      */     private final char padChar;
/*      */ 
/*      */ 
/*      */     
/*      */     PadPrinterParserDecorator(DateTimeFormatterBuilder.DateTimePrinterParser param1DateTimePrinterParser, int param1Int, char param1Char) {
/* 2260 */       this.printerParser = param1DateTimePrinterParser;
/* 2261 */       this.padWidth = param1Int;
/* 2262 */       this.padChar = param1Char;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) {
/* 2267 */       int i = param1StringBuilder.length();
/* 2268 */       if (!this.printerParser.format(param1DateTimePrintContext, param1StringBuilder)) {
/* 2269 */         return false;
/*      */       }
/* 2271 */       int j = param1StringBuilder.length() - i;
/* 2272 */       if (j > this.padWidth) {
/* 2273 */         throw new DateTimeException("Cannot print as output of " + j + " characters exceeds pad width of " + this.padWidth);
/*      */       }
/*      */       
/* 2276 */       for (byte b = 0; b < this.padWidth - j; b++) {
/* 2277 */         param1StringBuilder.insert(i, this.padChar);
/*      */       }
/* 2279 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int) {
/* 2285 */       boolean bool = param1DateTimeParseContext.isStrict();
/*      */       
/* 2287 */       if (param1Int > param1CharSequence.length()) {
/* 2288 */         throw new IndexOutOfBoundsException();
/*      */       }
/* 2290 */       if (param1Int == param1CharSequence.length()) {
/* 2291 */         return param1Int ^ 0xFFFFFFFF;
/*      */       }
/* 2293 */       int i = param1Int + this.padWidth;
/* 2294 */       if (i > param1CharSequence.length()) {
/* 2295 */         if (bool) {
/* 2296 */           return param1Int ^ 0xFFFFFFFF;
/*      */         }
/* 2298 */         i = param1CharSequence.length();
/*      */       } 
/* 2300 */       int j = param1Int;
/* 2301 */       while (j < i && param1DateTimeParseContext.charEquals(param1CharSequence.charAt(j), this.padChar)) {
/* 2302 */         j++;
/*      */       }
/* 2304 */       param1CharSequence = param1CharSequence.subSequence(0, i);
/* 2305 */       int k = this.printerParser.parse(param1DateTimeParseContext, param1CharSequence, j);
/* 2306 */       if (k != i && bool) {
/* 2307 */         return param1Int + j ^ 0xFFFFFFFF;
/*      */       }
/* 2309 */       return k;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2314 */       return "Pad(" + this.printerParser + "," + this.padWidth + ((this.padChar == ' ') ? ")" : (",'" + this.padChar + "')"));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   enum SettingsParser
/*      */     implements DateTimePrinterParser
/*      */   {
/* 2323 */     SENSITIVE,
/* 2324 */     INSENSITIVE,
/* 2325 */     STRICT,
/* 2326 */     LENIENT;
/*      */ 
/*      */     
/*      */     public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) {
/* 2330 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int) {
/* 2336 */       switch (ordinal()) { case 0:
/* 2337 */           param1DateTimeParseContext.setCaseSensitive(true); break;
/* 2338 */         case 1: param1DateTimeParseContext.setCaseSensitive(false); break;
/* 2339 */         case 2: param1DateTimeParseContext.setStrict(true); break;
/* 2340 */         case 3: param1DateTimeParseContext.setStrict(false); break; }
/*      */       
/* 2342 */       return param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2348 */       switch (ordinal()) { case 0:
/* 2349 */           return "ParseCaseSensitive(true)";
/* 2350 */         case 1: return "ParseCaseSensitive(false)";
/* 2351 */         case 2: return "ParseStrict(true)";
/* 2352 */         case 3: return "ParseStrict(false)"; }
/*      */       
/* 2354 */       throw new IllegalStateException("Unreachable");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class DefaultValueParser
/*      */     implements DateTimePrinterParser
/*      */   {
/*      */     private final TemporalField field;
/*      */     
/*      */     private final long value;
/*      */     
/*      */     DefaultValueParser(TemporalField param1TemporalField, long param1Long) {
/* 2367 */       this.field = param1TemporalField;
/* 2368 */       this.value = param1Long;
/*      */     }
/*      */     
/*      */     public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) {
/* 2372 */       return true;
/*      */     }
/*      */     
/*      */     public int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int) {
/* 2376 */       if (param1DateTimeParseContext.getParsed(this.field) == null) {
/* 2377 */         param1DateTimeParseContext.setParsedField(this.field, this.value, param1Int, param1Int);
/*      */       }
/* 2379 */       return param1Int;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class CharLiteralPrinterParser
/*      */     implements DateTimePrinterParser
/*      */   {
/*      */     private final char literal;
/*      */ 
/*      */     
/*      */     CharLiteralPrinterParser(char param1Char) {
/* 2391 */       this.literal = param1Char;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) {
/* 2396 */       param1StringBuilder.append(this.literal);
/* 2397 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int) {
/* 2402 */       int i = param1CharSequence.length();
/* 2403 */       if (param1Int == i) {
/* 2404 */         return param1Int ^ 0xFFFFFFFF;
/*      */       }
/* 2406 */       char c = param1CharSequence.charAt(param1Int);
/* 2407 */       if (c != this.literal && (
/* 2408 */         param1DateTimeParseContext.isCaseSensitive() || (
/* 2409 */         Character.toUpperCase(c) != Character.toUpperCase(this.literal) && 
/* 2410 */         Character.toLowerCase(c) != Character.toLowerCase(this.literal)))) {
/* 2411 */         return param1Int ^ 0xFFFFFFFF;
/*      */       }
/*      */       
/* 2414 */       return param1Int + 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2419 */       if (this.literal == '\'') {
/* 2420 */         return "''";
/*      */       }
/* 2422 */       return "'" + this.literal + "'";
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class StringLiteralPrinterParser
/*      */     implements DateTimePrinterParser
/*      */   {
/*      */     private final String literal;
/*      */ 
/*      */     
/*      */     StringLiteralPrinterParser(String param1String) {
/* 2434 */       this.literal = param1String;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) {
/* 2439 */       param1StringBuilder.append(this.literal);
/* 2440 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int) {
/* 2445 */       int i = param1CharSequence.length();
/* 2446 */       if (param1Int > i || param1Int < 0) {
/* 2447 */         throw new IndexOutOfBoundsException();
/*      */       }
/* 2449 */       if (!param1DateTimeParseContext.subSequenceEquals(param1CharSequence, param1Int, this.literal, 0, this.literal.length())) {
/* 2450 */         return param1Int ^ 0xFFFFFFFF;
/*      */       }
/* 2452 */       return param1Int + this.literal.length();
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2457 */       String str = this.literal.replace("'", "''");
/* 2458 */       return "'" + str + "'";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class NumberPrinterParser
/*      */     implements DateTimePrinterParser
/*      */   {
/* 2471 */     static final long[] EXCEED_POINTS = new long[] { 0L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L, 10000000000L };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final TemporalField field;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int minWidth;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int maxWidth;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final SignStyle signStyle;
/*      */ 
/*      */ 
/*      */     
/*      */     final int subsequentWidth;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     NumberPrinterParser(TemporalField param1TemporalField, int param1Int1, int param1Int2, SignStyle param1SignStyle) {
/* 2501 */       this.field = param1TemporalField;
/* 2502 */       this.minWidth = param1Int1;
/* 2503 */       this.maxWidth = param1Int2;
/* 2504 */       this.signStyle = param1SignStyle;
/* 2505 */       this.subsequentWidth = 0;
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
/*      */     protected NumberPrinterParser(TemporalField param1TemporalField, int param1Int1, int param1Int2, SignStyle param1SignStyle, int param1Int3) {
/* 2520 */       this.field = param1TemporalField;
/* 2521 */       this.minWidth = param1Int1;
/* 2522 */       this.maxWidth = param1Int2;
/* 2523 */       this.signStyle = param1SignStyle;
/* 2524 */       this.subsequentWidth = param1Int3;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     NumberPrinterParser withFixedWidth() {
/* 2533 */       if (this.subsequentWidth == -1) {
/* 2534 */         return this;
/*      */       }
/* 2536 */       return new NumberPrinterParser(this.field, this.minWidth, this.maxWidth, this.signStyle, -1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     NumberPrinterParser withSubsequentWidth(int param1Int) {
/* 2546 */       return new NumberPrinterParser(this.field, this.minWidth, this.maxWidth, this.signStyle, this.subsequentWidth + param1Int);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) {
/* 2551 */       Long long_ = param1DateTimePrintContext.getValue(this.field);
/* 2552 */       if (long_ == null) {
/* 2553 */         return false;
/*      */       }
/* 2555 */       long l = getValue(param1DateTimePrintContext, long_.longValue());
/* 2556 */       DecimalStyle decimalStyle = param1DateTimePrintContext.getDecimalStyle();
/* 2557 */       String str = (l == Long.MIN_VALUE) ? "9223372036854775808" : Long.toString(Math.abs(l));
/* 2558 */       if (str.length() > this.maxWidth) {
/* 2559 */         throw new DateTimeException("Field " + this.field + " cannot be printed as the value " + l + " exceeds the maximum print width of " + this.maxWidth);
/*      */       }
/*      */ 
/*      */       
/* 2563 */       str = decimalStyle.convertNumberToI18N(str);
/*      */       
/* 2565 */       if (l >= 0L) {
/* 2566 */         switch (this.signStyle) {
/*      */           case EXCEEDS_PAD:
/* 2568 */             if (this.minWidth < 19 && l >= EXCEED_POINTS[this.minWidth]) {
/* 2569 */               param1StringBuilder.append(decimalStyle.getPositiveSign());
/*      */             }
/*      */             break;
/*      */           case ALWAYS:
/* 2573 */             param1StringBuilder.append(decimalStyle.getPositiveSign());
/*      */             break;
/*      */         } 
/*      */       } else {
/* 2577 */         switch (this.signStyle) {
/*      */           case EXCEEDS_PAD:
/*      */           case ALWAYS:
/*      */           case NORMAL:
/* 2581 */             param1StringBuilder.append(decimalStyle.getNegativeSign());
/*      */             break;
/*      */           case NOT_NEGATIVE:
/* 2584 */             throw new DateTimeException("Field " + this.field + " cannot be printed as the value " + l + " cannot be negative according to the SignStyle");
/*      */         } 
/*      */ 
/*      */       
/*      */       } 
/* 2589 */       for (byte b = 0; b < this.minWidth - str.length(); b++) {
/* 2590 */         param1StringBuilder.append(decimalStyle.getZeroDigit());
/*      */       }
/* 2592 */       param1StringBuilder.append(str);
/* 2593 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     long getValue(DateTimePrintContext param1DateTimePrintContext, long param1Long) {
/* 2604 */       return param1Long;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isFixedWidth(DateTimeParseContext param1DateTimeParseContext) {
/* 2615 */       return (this.subsequentWidth == -1 || (this.subsequentWidth > 0 && this.minWidth == this.maxWidth && this.signStyle == SignStyle.NOT_NEGATIVE));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int) {
/* 2621 */       int i = param1CharSequence.length();
/* 2622 */       if (param1Int == i) {
/* 2623 */         return param1Int ^ 0xFFFFFFFF;
/*      */       }
/* 2625 */       char c = param1CharSequence.charAt(param1Int);
/* 2626 */       boolean bool1 = false;
/* 2627 */       boolean bool2 = false;
/* 2628 */       if (c == param1DateTimeParseContext.getDecimalStyle().getPositiveSign()) {
/* 2629 */         if (!this.signStyle.parse(true, param1DateTimeParseContext.isStrict(), (this.minWidth == this.maxWidth))) {
/* 2630 */           return param1Int ^ 0xFFFFFFFF;
/*      */         }
/* 2632 */         bool2 = true;
/* 2633 */         param1Int++;
/* 2634 */       } else if (c == param1DateTimeParseContext.getDecimalStyle().getNegativeSign()) {
/* 2635 */         if (!this.signStyle.parse(false, param1DateTimeParseContext.isStrict(), (this.minWidth == this.maxWidth))) {
/* 2636 */           return param1Int ^ 0xFFFFFFFF;
/*      */         }
/* 2638 */         bool1 = true;
/* 2639 */         param1Int++;
/*      */       }
/* 2641 */       else if (this.signStyle == SignStyle.ALWAYS && param1DateTimeParseContext.isStrict()) {
/* 2642 */         return param1Int ^ 0xFFFFFFFF;
/*      */       } 
/*      */       
/* 2645 */       byte b = (param1DateTimeParseContext.isStrict() || isFixedWidth(param1DateTimeParseContext)) ? this.minWidth : 1;
/* 2646 */       int j = param1Int + b;
/* 2647 */       if (j > i) {
/* 2648 */         return param1Int ^ 0xFFFFFFFF;
/*      */       }
/* 2650 */       int k = ((param1DateTimeParseContext.isStrict() || isFixedWidth(param1DateTimeParseContext)) ? this.maxWidth : 9) + Math.max(this.subsequentWidth, 0);
/* 2651 */       long l = 0L;
/* 2652 */       BigInteger bigInteger = null;
/* 2653 */       int m = param1Int; int n;
/* 2654 */       for (n = 0; n < 2; ) {
/* 2655 */         int i1 = Math.min(m + k, i);
/* 2656 */         while (m < i1) {
/* 2657 */           char c1 = param1CharSequence.charAt(m++);
/* 2658 */           int i2 = param1DateTimeParseContext.getDecimalStyle().convertToDigit(c1);
/* 2659 */           if (i2 < 0) {
/* 2660 */             m--;
/* 2661 */             if (m < j) {
/* 2662 */               return param1Int ^ 0xFFFFFFFF;
/*      */             }
/*      */             break;
/*      */           } 
/* 2666 */           if (m - param1Int > 18) {
/* 2667 */             if (bigInteger == null) {
/* 2668 */               bigInteger = BigInteger.valueOf(l);
/*      */             }
/* 2670 */             bigInteger = bigInteger.multiply(BigInteger.TEN).add(BigInteger.valueOf(i2)); continue;
/*      */           } 
/* 2672 */           l = l * 10L + i2;
/*      */         } 
/*      */         
/* 2675 */         if (this.subsequentWidth > 0 && n == 0) {
/*      */           
/* 2677 */           int i2 = m - param1Int;
/* 2678 */           k = Math.max(b, i2 - this.subsequentWidth);
/* 2679 */           m = param1Int;
/* 2680 */           l = 0L;
/* 2681 */           bigInteger = null;
/*      */           
/*      */           n++;
/*      */         } 
/*      */       } 
/* 2686 */       if (bool1) {
/* 2687 */         if (bigInteger != null) {
/* 2688 */           if (bigInteger.equals(BigInteger.ZERO) && param1DateTimeParseContext.isStrict()) {
/* 2689 */             return param1Int - 1 ^ 0xFFFFFFFF;
/*      */           }
/* 2691 */           bigInteger = bigInteger.negate();
/*      */         } else {
/* 2693 */           if (l == 0L && param1DateTimeParseContext.isStrict()) {
/* 2694 */             return param1Int - 1 ^ 0xFFFFFFFF;
/*      */           }
/* 2696 */           l = -l;
/*      */         } 
/* 2698 */       } else if (this.signStyle == SignStyle.EXCEEDS_PAD && param1DateTimeParseContext.isStrict()) {
/* 2699 */         n = m - param1Int;
/* 2700 */         if (bool2) {
/* 2701 */           if (n <= this.minWidth) {
/* 2702 */             return param1Int - 1 ^ 0xFFFFFFFF;
/*      */           }
/*      */         }
/* 2705 */         else if (n > this.minWidth) {
/* 2706 */           return param1Int ^ 0xFFFFFFFF;
/*      */         } 
/*      */       } 
/*      */       
/* 2710 */       if (bigInteger != null) {
/* 2711 */         if (bigInteger.bitLength() > 63) {
/*      */           
/* 2713 */           bigInteger = bigInteger.divide(BigInteger.TEN);
/* 2714 */           m--;
/*      */         } 
/* 2716 */         return setValue(param1DateTimeParseContext, bigInteger.longValue(), param1Int, m);
/*      */       } 
/* 2718 */       return setValue(param1DateTimeParseContext, l, param1Int, m);
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
/*      */     int setValue(DateTimeParseContext param1DateTimeParseContext, long param1Long, int param1Int1, int param1Int2) {
/* 2731 */       return param1DateTimeParseContext.setParsedField(this.field, param1Long, param1Int1, param1Int2);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2736 */       if (this.minWidth == 1 && this.maxWidth == 19 && this.signStyle == SignStyle.NORMAL) {
/* 2737 */         return "Value(" + this.field + ")";
/*      */       }
/* 2739 */       if (this.minWidth == this.maxWidth && this.signStyle == SignStyle.NOT_NEGATIVE) {
/* 2740 */         return "Value(" + this.field + "," + this.minWidth + ")";
/*      */       }
/* 2742 */       return "Value(" + this.field + "," + this.minWidth + "," + this.maxWidth + "," + this.signStyle + ")";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class ReducedPrinterParser
/*      */     extends NumberPrinterParser
/*      */   {
/* 2754 */     static final LocalDate BASE_DATE = LocalDate.of(2000, 1, 1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int baseValue;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final ChronoLocalDate baseDate;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ReducedPrinterParser(TemporalField param1TemporalField, int param1Int1, int param1Int2, int param1Int3, ChronoLocalDate param1ChronoLocalDate) {
/* 2770 */       this(param1TemporalField, param1Int1, param1Int2, param1Int3, param1ChronoLocalDate, 0);
/* 2771 */       if (param1Int1 < 1 || param1Int1 > 10) {
/* 2772 */         throw new IllegalArgumentException("The minWidth must be from 1 to 10 inclusive but was " + param1Int1);
/*      */       }
/* 2774 */       if (param1Int2 < 1 || param1Int2 > 10) {
/* 2775 */         throw new IllegalArgumentException("The maxWidth must be from 1 to 10 inclusive but was " + param1Int1);
/*      */       }
/* 2777 */       if (param1Int2 < param1Int1) {
/* 2778 */         throw new IllegalArgumentException("Maximum width must exceed or equal the minimum width but " + param1Int2 + " < " + param1Int1);
/*      */       }
/*      */       
/* 2781 */       if (param1ChronoLocalDate == null) {
/* 2782 */         if (!param1TemporalField.range().isValidValue(param1Int3)) {
/* 2783 */           throw new IllegalArgumentException("The base value must be within the range of the field");
/*      */         }
/* 2785 */         if (param1Int3 + EXCEED_POINTS[param1Int2] > 2147483647L) {
/* 2786 */           throw new DateTimeException("Unable to add printer-parser as the range exceeds the capacity of an int");
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
/*      */ 
/*      */     
/*      */     private ReducedPrinterParser(TemporalField param1TemporalField, int param1Int1, int param1Int2, int param1Int3, ChronoLocalDate param1ChronoLocalDate, int param1Int4) {
/* 2804 */       super(param1TemporalField, param1Int1, param1Int2, SignStyle.NOT_NEGATIVE, param1Int4);
/* 2805 */       this.baseValue = param1Int3;
/* 2806 */       this.baseDate = param1ChronoLocalDate;
/*      */     }
/*      */ 
/*      */     
/*      */     long getValue(DateTimePrintContext param1DateTimePrintContext, long param1Long) {
/* 2811 */       long l = Math.abs(param1Long);
/* 2812 */       int i = this.baseValue;
/* 2813 */       if (this.baseDate != null) {
/* 2814 */         Chronology chronology = Chronology.from(param1DateTimePrintContext.getTemporal());
/* 2815 */         i = chronology.date(this.baseDate).get(this.field);
/*      */       } 
/* 2817 */       if (param1Long >= i && param1Long < i + EXCEED_POINTS[this.minWidth])
/*      */       {
/* 2819 */         return l % EXCEED_POINTS[this.minWidth];
/*      */       }
/*      */       
/* 2822 */       return l % EXCEED_POINTS[this.maxWidth];
/*      */     }
/*      */ 
/*      */     
/*      */     int setValue(DateTimeParseContext param1DateTimeParseContext, long param1Long, int param1Int1, int param1Int2) {
/* 2827 */       int i = this.baseValue;
/* 2828 */       if (this.baseDate != null) {
/* 2829 */         Chronology chronology = param1DateTimeParseContext.getEffectiveChronology();
/* 2830 */         i = chronology.date(this.baseDate).get(this.field);
/*      */ 
/*      */         
/* 2833 */         long l = param1Long;
/* 2834 */         param1DateTimeParseContext.addChronoChangedListener(param1Chronology -> setValue(param1DateTimeParseContext, param1Long, param1Int1, param1Int2));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2843 */       int j = param1Int2 - param1Int1;
/* 2844 */       if (j == this.minWidth && param1Long >= 0L) {
/* 2845 */         long l1 = EXCEED_POINTS[this.minWidth];
/* 2846 */         long l2 = i % l1;
/* 2847 */         long l3 = i - l2;
/* 2848 */         if (i > 0) {
/* 2849 */           param1Long = l3 + param1Long;
/*      */         } else {
/* 2851 */           param1Long = l3 - param1Long;
/*      */         } 
/* 2853 */         if (param1Long < i) {
/* 2854 */           param1Long += l1;
/*      */         }
/*      */       } 
/* 2857 */       return param1DateTimeParseContext.setParsedField(this.field, param1Long, param1Int1, param1Int2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ReducedPrinterParser withFixedWidth() {
/* 2867 */       if (this.subsequentWidth == -1) {
/* 2868 */         return this;
/*      */       }
/* 2870 */       return new ReducedPrinterParser(this.field, this.minWidth, this.maxWidth, this.baseValue, this.baseDate, -1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ReducedPrinterParser withSubsequentWidth(int param1Int) {
/* 2881 */       return new ReducedPrinterParser(this.field, this.minWidth, this.maxWidth, this.baseValue, this.baseDate, this.subsequentWidth + param1Int);
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
/*      */     boolean isFixedWidth(DateTimeParseContext param1DateTimeParseContext) {
/* 2894 */       if (!param1DateTimeParseContext.isStrict()) {
/* 2895 */         return false;
/*      */       }
/* 2897 */       return super.isFixedWidth(param1DateTimeParseContext);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2902 */       return "ReducedValue(" + this.field + "," + this.minWidth + "," + this.maxWidth + "," + ((this.baseDate != null) ? (String)this.baseDate : (String)Integer.valueOf(this.baseValue)) + ")";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class FractionPrinterParser
/*      */     implements DateTimePrinterParser
/*      */   {
/*      */     private final TemporalField field;
/*      */ 
/*      */     
/*      */     private final int minWidth;
/*      */ 
/*      */     
/*      */     private final int maxWidth;
/*      */ 
/*      */     
/*      */     private final boolean decimalPoint;
/*      */ 
/*      */ 
/*      */     
/*      */     FractionPrinterParser(TemporalField param1TemporalField, int param1Int1, int param1Int2, boolean param1Boolean) {
/* 2925 */       Objects.requireNonNull(param1TemporalField, "field");
/* 2926 */       if (!param1TemporalField.range().isFixed()) {
/* 2927 */         throw new IllegalArgumentException("Field must have a fixed set of values: " + param1TemporalField);
/*      */       }
/* 2929 */       if (param1Int1 < 0 || param1Int1 > 9) {
/* 2930 */         throw new IllegalArgumentException("Minimum width must be from 0 to 9 inclusive but was " + param1Int1);
/*      */       }
/* 2932 */       if (param1Int2 < 1 || param1Int2 > 9) {
/* 2933 */         throw new IllegalArgumentException("Maximum width must be from 1 to 9 inclusive but was " + param1Int2);
/*      */       }
/* 2935 */       if (param1Int2 < param1Int1) {
/* 2936 */         throw new IllegalArgumentException("Maximum width must exceed or equal the minimum width but " + param1Int2 + " < " + param1Int1);
/*      */       }
/*      */       
/* 2939 */       this.field = param1TemporalField;
/* 2940 */       this.minWidth = param1Int1;
/* 2941 */       this.maxWidth = param1Int2;
/* 2942 */       this.decimalPoint = param1Boolean;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) {
/* 2947 */       Long long_ = param1DateTimePrintContext.getValue(this.field);
/* 2948 */       if (long_ == null) {
/* 2949 */         return false;
/*      */       }
/* 2951 */       DecimalStyle decimalStyle = param1DateTimePrintContext.getDecimalStyle();
/* 2952 */       BigDecimal bigDecimal = convertToFraction(long_.longValue());
/* 2953 */       if (bigDecimal.scale() == 0) {
/* 2954 */         if (this.minWidth > 0) {
/* 2955 */           if (this.decimalPoint) {
/* 2956 */             param1StringBuilder.append(decimalStyle.getDecimalSeparator());
/*      */           }
/* 2958 */           for (byte b = 0; b < this.minWidth; b++) {
/* 2959 */             param1StringBuilder.append(decimalStyle.getZeroDigit());
/*      */           }
/*      */         } 
/*      */       } else {
/* 2963 */         int i = Math.min(Math.max(bigDecimal.scale(), this.minWidth), this.maxWidth);
/* 2964 */         bigDecimal = bigDecimal.setScale(i, RoundingMode.FLOOR);
/* 2965 */         String str = bigDecimal.toPlainString().substring(2);
/* 2966 */         str = decimalStyle.convertNumberToI18N(str);
/* 2967 */         if (this.decimalPoint) {
/* 2968 */           param1StringBuilder.append(decimalStyle.getDecimalSeparator());
/*      */         }
/* 2970 */         param1StringBuilder.append(str);
/*      */       } 
/* 2972 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int) {
/* 2977 */       byte b1 = param1DateTimeParseContext.isStrict() ? this.minWidth : 0;
/* 2978 */       byte b2 = param1DateTimeParseContext.isStrict() ? this.maxWidth : 9;
/* 2979 */       int i = param1CharSequence.length();
/* 2980 */       if (param1Int == i)
/*      */       {
/* 2982 */         return b1 ? (param1Int ^ 0xFFFFFFFF) : param1Int;
/*      */       }
/* 2984 */       if (this.decimalPoint) {
/* 2985 */         if (param1CharSequence.charAt(param1Int) != param1DateTimeParseContext.getDecimalStyle().getDecimalSeparator())
/*      */         {
/* 2987 */           return b1 ? (param1Int ^ 0xFFFFFFFF) : param1Int;
/*      */         }
/* 2989 */         param1Int++;
/*      */       } 
/* 2991 */       int j = param1Int + b1;
/* 2992 */       if (j > i) {
/* 2993 */         return param1Int ^ 0xFFFFFFFF;
/*      */       }
/* 2995 */       int k = Math.min(param1Int + b2, i);
/* 2996 */       int m = 0;
/* 2997 */       int n = param1Int;
/* 2998 */       while (n < k) {
/* 2999 */         char c = param1CharSequence.charAt(n++);
/* 3000 */         int i1 = param1DateTimeParseContext.getDecimalStyle().convertToDigit(c);
/* 3001 */         if (i1 < 0) {
/* 3002 */           if (n < j) {
/* 3003 */             return param1Int ^ 0xFFFFFFFF;
/*      */           }
/* 3005 */           n--;
/*      */           break;
/*      */         } 
/* 3008 */         m = m * 10 + i1;
/*      */       } 
/* 3010 */       BigDecimal bigDecimal = (new BigDecimal(m)).movePointLeft(n - param1Int);
/* 3011 */       long l = convertFromFraction(bigDecimal);
/* 3012 */       return param1DateTimeParseContext.setParsedField(this.field, l, param1Int, n);
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
/*      */     private BigDecimal convertToFraction(long param1Long) {
/* 3032 */       ValueRange valueRange = this.field.range();
/* 3033 */       valueRange.checkValidValue(param1Long, this.field);
/* 3034 */       BigDecimal bigDecimal1 = BigDecimal.valueOf(valueRange.getMinimum());
/* 3035 */       BigDecimal bigDecimal2 = BigDecimal.valueOf(valueRange.getMaximum()).subtract(bigDecimal1).add(BigDecimal.ONE);
/* 3036 */       BigDecimal bigDecimal3 = BigDecimal.valueOf(param1Long).subtract(bigDecimal1);
/* 3037 */       BigDecimal bigDecimal4 = bigDecimal3.divide(bigDecimal2, 9, RoundingMode.FLOOR);
/*      */       
/* 3039 */       return (bigDecimal4.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ZERO : bigDecimal4.stripTrailingZeros();
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
/*      */     private long convertFromFraction(BigDecimal param1BigDecimal) {
/* 3059 */       ValueRange valueRange = this.field.range();
/* 3060 */       BigDecimal bigDecimal1 = BigDecimal.valueOf(valueRange.getMinimum());
/* 3061 */       BigDecimal bigDecimal2 = BigDecimal.valueOf(valueRange.getMaximum()).subtract(bigDecimal1).add(BigDecimal.ONE);
/* 3062 */       BigDecimal bigDecimal3 = param1BigDecimal.multiply(bigDecimal2).setScale(0, RoundingMode.FLOOR).add(bigDecimal1);
/* 3063 */       return bigDecimal3.longValueExact();
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 3068 */       String str = this.decimalPoint ? ",DecimalPoint" : "";
/* 3069 */       return "Fraction(" + this.field + "," + this.minWidth + "," + this.maxWidth + str + ")";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class TextPrinterParser
/*      */     implements DateTimePrinterParser
/*      */   {
/*      */     private final TemporalField field;
/*      */ 
/*      */ 
/*      */     
/*      */     private final TextStyle textStyle;
/*      */ 
/*      */ 
/*      */     
/*      */     private final DateTimeTextProvider provider;
/*      */ 
/*      */ 
/*      */     
/*      */     private volatile DateTimeFormatterBuilder.NumberPrinterParser numberPrinterParser;
/*      */ 
/*      */ 
/*      */     
/*      */     TextPrinterParser(TemporalField param1TemporalField, TextStyle param1TextStyle, DateTimeTextProvider param1DateTimeTextProvider) {
/* 3096 */       this.field = param1TemporalField;
/* 3097 */       this.textStyle = param1TextStyle;
/* 3098 */       this.provider = param1DateTimeTextProvider;
/*      */     }
/*      */     
/*      */     public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) {
/*      */       String str;
/* 3103 */       Long long_ = param1DateTimePrintContext.getValue(this.field);
/* 3104 */       if (long_ == null) {
/* 3105 */         return false;
/*      */       }
/*      */       
/* 3108 */       Chronology chronology = param1DateTimePrintContext.getTemporal().<Chronology>query(TemporalQueries.chronology());
/* 3109 */       if (chronology == null || chronology == IsoChronology.INSTANCE) {
/* 3110 */         str = this.provider.getText(this.field, long_.longValue(), this.textStyle, param1DateTimePrintContext.getLocale());
/*      */       } else {
/* 3112 */         str = this.provider.getText(chronology, this.field, long_.longValue(), this.textStyle, param1DateTimePrintContext.getLocale());
/*      */       } 
/* 3114 */       if (str == null) {
/* 3115 */         return numberPrinterParser().format(param1DateTimePrintContext, param1StringBuilder);
/*      */       }
/* 3117 */       param1StringBuilder.append(str);
/* 3118 */       return true;
/*      */     }
/*      */     
/*      */     public int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int) {
/*      */       Iterator<Map.Entry<String, Long>> iterator;
/* 3123 */       int i = param1CharSequence.length();
/* 3124 */       if (param1Int < 0 || param1Int > i) {
/* 3125 */         throw new IndexOutOfBoundsException();
/*      */       }
/* 3127 */       TextStyle textStyle = param1DateTimeParseContext.isStrict() ? this.textStyle : null;
/* 3128 */       Chronology chronology = param1DateTimeParseContext.getEffectiveChronology();
/*      */       
/* 3130 */       if (chronology == null || chronology == IsoChronology.INSTANCE) {
/* 3131 */         iterator = this.provider.getTextIterator(this.field, textStyle, param1DateTimeParseContext.getLocale());
/*      */       } else {
/* 3133 */         iterator = this.provider.getTextIterator(chronology, this.field, textStyle, param1DateTimeParseContext.getLocale());
/*      */       } 
/* 3135 */       if (iterator != null) {
/* 3136 */         while (iterator.hasNext()) {
/* 3137 */           Map.Entry entry = iterator.next();
/* 3138 */           String str = (String)entry.getKey();
/* 3139 */           if (param1DateTimeParseContext.subSequenceEquals(str, 0, param1CharSequence, param1Int, str.length())) {
/* 3140 */             return param1DateTimeParseContext.setParsedField(this.field, ((Long)entry.getValue()).longValue(), param1Int, param1Int + str.length());
/*      */           }
/*      */         } 
/* 3143 */         if (param1DateTimeParseContext.isStrict()) {
/* 3144 */           return param1Int ^ 0xFFFFFFFF;
/*      */         }
/*      */       } 
/* 3147 */       return numberPrinterParser().parse(param1DateTimeParseContext, param1CharSequence, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private DateTimeFormatterBuilder.NumberPrinterParser numberPrinterParser() {
/* 3155 */       if (this.numberPrinterParser == null) {
/* 3156 */         this.numberPrinterParser = new DateTimeFormatterBuilder.NumberPrinterParser(this.field, 1, 19, SignStyle.NORMAL);
/*      */       }
/* 3158 */       return this.numberPrinterParser;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 3163 */       if (this.textStyle == TextStyle.FULL) {
/* 3164 */         return "Text(" + this.field + ")";
/*      */       }
/* 3166 */       return "Text(" + this.field + "," + this.textStyle + ")";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class InstantPrinterParser
/*      */     implements DateTimePrinterParser
/*      */   {
/*      */     private static final long SECONDS_PER_10000_YEARS = 315569520000L;
/*      */     
/*      */     private static final long SECONDS_0000_TO_1970 = 62167219200L;
/*      */     
/*      */     private final int fractionalDigits;
/*      */ 
/*      */     
/*      */     InstantPrinterParser(int param1Int) {
/* 3183 */       this.fractionalDigits = param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) {
/* 3189 */       Long long_1 = param1DateTimePrintContext.getValue(ChronoField.INSTANT_SECONDS);
/* 3190 */       Long long_2 = null;
/* 3191 */       if (param1DateTimePrintContext.getTemporal().isSupported(ChronoField.NANO_OF_SECOND)) {
/* 3192 */         long_2 = Long.valueOf(param1DateTimePrintContext.getTemporal().getLong(ChronoField.NANO_OF_SECOND));
/*      */       }
/* 3194 */       if (long_1 == null) {
/* 3195 */         return false;
/*      */       }
/* 3197 */       long l = long_1.longValue();
/* 3198 */       int i = ChronoField.NANO_OF_SECOND.checkValidIntValue((long_2 != null) ? long_2.longValue() : 0L);
/*      */       
/* 3200 */       if (l >= -62167219200L) {
/*      */         
/* 3202 */         long l1 = l - 315569520000L + 62167219200L;
/* 3203 */         long l2 = Math.floorDiv(l1, 315569520000L) + 1L;
/* 3204 */         long l3 = Math.floorMod(l1, 315569520000L);
/* 3205 */         LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(l3 - 62167219200L, 0, ZoneOffset.UTC);
/* 3206 */         if (l2 > 0L) {
/* 3207 */           param1StringBuilder.append('+').append(l2);
/*      */         }
/* 3209 */         param1StringBuilder.append(localDateTime);
/* 3210 */         if (localDateTime.getSecond() == 0) {
/* 3211 */           param1StringBuilder.append(":00");
/*      */         }
/*      */       } else {
/*      */         
/* 3215 */         long l1 = l + 62167219200L;
/* 3216 */         long l2 = l1 / 315569520000L;
/* 3217 */         long l3 = l1 % 315569520000L;
/* 3218 */         LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(l3 - 62167219200L, 0, ZoneOffset.UTC);
/* 3219 */         int j = param1StringBuilder.length();
/* 3220 */         param1StringBuilder.append(localDateTime);
/* 3221 */         if (localDateTime.getSecond() == 0) {
/* 3222 */           param1StringBuilder.append(":00");
/*      */         }
/* 3224 */         if (l2 < 0L) {
/* 3225 */           if (localDateTime.getYear() == -10000) {
/* 3226 */             param1StringBuilder.replace(j, j + 2, Long.toString(l2 - 1L));
/* 3227 */           } else if (l3 == 0L) {
/* 3228 */             param1StringBuilder.insert(j, l2);
/*      */           } else {
/* 3230 */             param1StringBuilder.insert(j + 1, Math.abs(l2));
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/* 3235 */       if ((this.fractionalDigits < 0 && i > 0) || this.fractionalDigits > 0) {
/* 3236 */         param1StringBuilder.append('.');
/* 3237 */         int j = 100000000;
/* 3238 */         for (byte b = 0; (this.fractionalDigits == -1 && i > 0) || (this.fractionalDigits == -2 && (i > 0 || b % 3 != 0)) || b < this.fractionalDigits; 
/*      */           
/* 3240 */           b++) {
/* 3241 */           int k = i / j;
/* 3242 */           param1StringBuilder.append((char)(k + 48));
/* 3243 */           i -= k * j;
/* 3244 */           j /= 10;
/*      */         } 
/*      */       } 
/* 3247 */       param1StringBuilder.append('Z');
/* 3248 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int) {
/*      */       long l2;
/* 3254 */       boolean bool1 = (this.fractionalDigits < 0) ? false : this.fractionalDigits;
/* 3255 */       boolean bool2 = (this.fractionalDigits < 0) ? true : this.fractionalDigits;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3263 */       DateTimeFormatterBuilder.CompositePrinterParser compositePrinterParser = (new DateTimeFormatterBuilder()).append(DateTimeFormatter.ISO_LOCAL_DATE).appendLiteral('T').appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral(':').appendValue(ChronoField.MINUTE_OF_HOUR, 2).appendLiteral(':').appendValue(ChronoField.SECOND_OF_MINUTE, 2).appendFraction(ChronoField.NANO_OF_SECOND, bool1, bool2, true).appendLiteral('Z').toFormatter().toPrinterParser(false);
/* 3264 */       DateTimeParseContext dateTimeParseContext = param1DateTimeParseContext.copy();
/* 3265 */       int i = compositePrinterParser.parse(dateTimeParseContext, param1CharSequence, param1Int);
/* 3266 */       if (i < 0) {
/* 3267 */         return i;
/*      */       }
/*      */ 
/*      */       
/* 3271 */       long l1 = dateTimeParseContext.getParsed(ChronoField.YEAR).longValue();
/* 3272 */       int j = dateTimeParseContext.getParsed(ChronoField.MONTH_OF_YEAR).intValue();
/* 3273 */       int k = dateTimeParseContext.getParsed(ChronoField.DAY_OF_MONTH).intValue();
/* 3274 */       int m = dateTimeParseContext.getParsed(ChronoField.HOUR_OF_DAY).intValue();
/* 3275 */       int n = dateTimeParseContext.getParsed(ChronoField.MINUTE_OF_HOUR).intValue();
/* 3276 */       Long long_1 = dateTimeParseContext.getParsed(ChronoField.SECOND_OF_MINUTE);
/* 3277 */       Long long_2 = dateTimeParseContext.getParsed(ChronoField.NANO_OF_SECOND);
/* 3278 */       byte b = (long_1 != null) ? long_1.intValue() : 0;
/* 3279 */       boolean bool3 = (long_2 != null) ? long_2.intValue() : false;
/* 3280 */       boolean bool4 = false;
/* 3281 */       if (m == 24 && n == 0 && !b && !bool3) {
/* 3282 */         m = 0;
/* 3283 */         bool4 = true;
/* 3284 */       } else if (m == 23 && n == 59 && b == 60) {
/* 3285 */         param1DateTimeParseContext.setParsedLeapSecond();
/* 3286 */         b = 59;
/*      */       } 
/* 3288 */       int i1 = (int)l1 % 10000;
/*      */       
/*      */       try {
/* 3291 */         LocalDateTime localDateTime = LocalDateTime.of(i1, j, k, m, n, b, 0).plusDays(bool4);
/* 3292 */         l2 = localDateTime.toEpochSecond(ZoneOffset.UTC);
/* 3293 */         l2 += Math.multiplyExact(l1 / 10000L, 315569520000L);
/* 3294 */       } catch (RuntimeException runtimeException) {
/* 3295 */         return param1Int ^ 0xFFFFFFFF;
/*      */       } 
/* 3297 */       int i2 = i;
/* 3298 */       i2 = param1DateTimeParseContext.setParsedField(ChronoField.INSTANT_SECONDS, l2, param1Int, i2);
/* 3299 */       return param1DateTimeParseContext.setParsedField(ChronoField.NANO_OF_SECOND, bool3, param1Int, i2);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 3304 */       return "Instant()";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class OffsetIdPrinterParser
/*      */     implements DateTimePrinterParser
/*      */   {
/* 3313 */     static final String[] PATTERNS = new String[] { "+HH", "+HHmm", "+HH:mm", "+HHMM", "+HH:MM", "+HHMMss", "+HH:MM:ss", "+HHMMSS", "+HH:MM:SS" };
/*      */ 
/*      */     
/* 3316 */     static final OffsetIdPrinterParser INSTANCE_ID_Z = new OffsetIdPrinterParser("+HH:MM:ss", "Z");
/* 3317 */     static final OffsetIdPrinterParser INSTANCE_ID_ZERO = new OffsetIdPrinterParser("+HH:MM:ss", "0");
/*      */ 
/*      */ 
/*      */     
/*      */     private final String noOffsetText;
/*      */ 
/*      */     
/*      */     private final int type;
/*      */ 
/*      */ 
/*      */     
/*      */     OffsetIdPrinterParser(String param1String1, String param1String2) {
/* 3329 */       Objects.requireNonNull(param1String1, "pattern");
/* 3330 */       Objects.requireNonNull(param1String2, "noOffsetText");
/* 3331 */       this.type = checkPattern(param1String1);
/* 3332 */       this.noOffsetText = param1String2;
/*      */     }
/*      */     
/*      */     private int checkPattern(String param1String) {
/* 3336 */       for (byte b = 0; b < PATTERNS.length; b++) {
/* 3337 */         if (PATTERNS[b].equals(param1String)) {
/* 3338 */           return b;
/*      */         }
/*      */       } 
/* 3341 */       throw new IllegalArgumentException("Invalid zone offset pattern: " + param1String);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) {
/* 3346 */       Long long_ = param1DateTimePrintContext.getValue(ChronoField.OFFSET_SECONDS);
/* 3347 */       if (long_ == null) {
/* 3348 */         return false;
/*      */       }
/* 3350 */       int i = Math.toIntExact(long_.longValue());
/* 3351 */       if (i == 0) {
/* 3352 */         param1StringBuilder.append(this.noOffsetText);
/*      */       } else {
/* 3354 */         int j = Math.abs(i / 3600 % 100);
/* 3355 */         int k = Math.abs(i / 60 % 60);
/* 3356 */         int m = Math.abs(i % 60);
/* 3357 */         int n = param1StringBuilder.length();
/* 3358 */         int i1 = j;
/* 3359 */         param1StringBuilder.append((i < 0) ? "-" : "+")
/* 3360 */           .append((char)(j / 10 + 48)).append((char)(j % 10 + 48));
/* 3361 */         if (this.type >= 3 || (this.type >= 1 && k > 0)) {
/* 3362 */           param1StringBuilder.append((this.type % 2 == 0) ? ":" : "")
/* 3363 */             .append((char)(k / 10 + 48)).append((char)(k % 10 + 48));
/* 3364 */           i1 += k;
/* 3365 */           if (this.type >= 7 || (this.type >= 5 && m > 0)) {
/* 3366 */             param1StringBuilder.append((this.type % 2 == 0) ? ":" : "")
/* 3367 */               .append((char)(m / 10 + 48)).append((char)(m % 10 + 48));
/* 3368 */             i1 += m;
/*      */           } 
/*      */         } 
/* 3371 */         if (i1 == 0) {
/* 3372 */           param1StringBuilder.setLength(n);
/* 3373 */           param1StringBuilder.append(this.noOffsetText);
/*      */         } 
/*      */       } 
/* 3376 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int) {
/* 3381 */       int i = param1CharSequence.length();
/* 3382 */       int j = this.noOffsetText.length();
/* 3383 */       if (j == 0) {
/* 3384 */         if (param1Int == i) {
/* 3385 */           return param1DateTimeParseContext.setParsedField(ChronoField.OFFSET_SECONDS, 0L, param1Int, param1Int);
/*      */         }
/*      */       } else {
/* 3388 */         if (param1Int == i) {
/* 3389 */           return param1Int ^ 0xFFFFFFFF;
/*      */         }
/* 3391 */         if (param1DateTimeParseContext.subSequenceEquals(param1CharSequence, param1Int, this.noOffsetText, 0, j)) {
/* 3392 */           return param1DateTimeParseContext.setParsedField(ChronoField.OFFSET_SECONDS, 0L, param1Int, param1Int + j);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 3397 */       char c = param1CharSequence.charAt(param1Int);
/* 3398 */       if (c == '+' || c == '-') {
/*      */         
/* 3400 */         boolean bool = (c == '-') ? true : true;
/* 3401 */         int[] arrayOfInt = new int[4];
/* 3402 */         arrayOfInt[0] = param1Int + 1;
/* 3403 */         if (!((parseNumber(arrayOfInt, 1, param1CharSequence, true) || 
/* 3404 */           parseNumber(arrayOfInt, 2, param1CharSequence, (this.type >= 3)) || 
/* 3405 */           parseNumber(arrayOfInt, 3, param1CharSequence, false)) ? 1 : 0)) {
/*      */           
/* 3407 */           long l = bool * (arrayOfInt[1] * 3600L + arrayOfInt[2] * 60L + arrayOfInt[3]);
/* 3408 */           return param1DateTimeParseContext.setParsedField(ChronoField.OFFSET_SECONDS, l, param1Int, arrayOfInt[0]);
/*      */         } 
/*      */       } 
/*      */       
/* 3412 */       if (j == 0) {
/* 3413 */         return param1DateTimeParseContext.setParsedField(ChronoField.OFFSET_SECONDS, 0L, param1Int, param1Int + j);
/*      */       }
/* 3415 */       return param1Int ^ 0xFFFFFFFF;
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
/*      */     private boolean parseNumber(int[] param1ArrayOfint, int param1Int, CharSequence param1CharSequence, boolean param1Boolean) {
/* 3428 */       if ((this.type + 3) / 2 < param1Int) {
/* 3429 */         return false;
/*      */       }
/* 3431 */       int i = param1ArrayOfint[0];
/* 3432 */       if (this.type % 2 == 0 && param1Int > 1) {
/* 3433 */         if (i + 1 > param1CharSequence.length() || param1CharSequence.charAt(i) != ':') {
/* 3434 */           return param1Boolean;
/*      */         }
/* 3436 */         i++;
/*      */       } 
/* 3438 */       if (i + 2 > param1CharSequence.length()) {
/* 3439 */         return param1Boolean;
/*      */       }
/* 3441 */       char c1 = param1CharSequence.charAt(i++);
/* 3442 */       char c2 = param1CharSequence.charAt(i++);
/* 3443 */       if (c1 < '0' || c1 > '9' || c2 < '0' || c2 > '9') {
/* 3444 */         return param1Boolean;
/*      */       }
/* 3446 */       int j = (c1 - 48) * 10 + c2 - 48;
/* 3447 */       if (j < 0 || j > 59) {
/* 3448 */         return param1Boolean;
/*      */       }
/* 3450 */       param1ArrayOfint[param1Int] = j;
/* 3451 */       param1ArrayOfint[0] = i;
/* 3452 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 3457 */       String str = this.noOffsetText.replace("'", "''");
/* 3458 */       return "Offset(" + PATTERNS[this.type] + ",'" + str + "')";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class LocalizedOffsetIdPrinterParser
/*      */     implements DateTimePrinterParser
/*      */   {
/*      */     private final TextStyle style;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     LocalizedOffsetIdPrinterParser(TextStyle param1TextStyle) {
/* 3475 */       this.style = param1TextStyle;
/*      */     }
/*      */     
/*      */     private static StringBuilder appendHMS(StringBuilder param1StringBuilder, int param1Int) {
/* 3479 */       return param1StringBuilder.append((char)(param1Int / 10 + 48))
/* 3480 */         .append((char)(param1Int % 10 + 48));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) {
/* 3485 */       Long long_ = param1DateTimePrintContext.getValue(ChronoField.OFFSET_SECONDS);
/* 3486 */       if (long_ == null) {
/* 3487 */         return false;
/*      */       }
/* 3489 */       String str = "GMT";
/* 3490 */       if (str != null) {
/* 3491 */         param1StringBuilder.append(str);
/*      */       }
/* 3493 */       int i = Math.toIntExact(long_.longValue());
/* 3494 */       if (i != 0) {
/* 3495 */         int j = Math.abs(i / 3600 % 100);
/* 3496 */         int k = Math.abs(i / 60 % 60);
/* 3497 */         int m = Math.abs(i % 60);
/* 3498 */         param1StringBuilder.append((i < 0) ? "-" : "+");
/* 3499 */         if (this.style == TextStyle.FULL) {
/* 3500 */           appendHMS(param1StringBuilder, j);
/* 3501 */           param1StringBuilder.append(':');
/* 3502 */           appendHMS(param1StringBuilder, k);
/* 3503 */           if (m != 0) {
/* 3504 */             param1StringBuilder.append(':');
/* 3505 */             appendHMS(param1StringBuilder, m);
/*      */           } 
/*      */         } else {
/* 3508 */           if (j >= 10) {
/* 3509 */             param1StringBuilder.append((char)(j / 10 + 48));
/*      */           }
/* 3511 */           param1StringBuilder.append((char)(j % 10 + 48));
/* 3512 */           if (k != 0 || m != 0) {
/* 3513 */             param1StringBuilder.append(':');
/* 3514 */             appendHMS(param1StringBuilder, k);
/* 3515 */             if (m != 0) {
/* 3516 */               param1StringBuilder.append(':');
/* 3517 */               appendHMS(param1StringBuilder, m);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 3522 */       return true;
/*      */     }
/*      */     
/*      */     int getDigit(CharSequence param1CharSequence, int param1Int) {
/* 3526 */       char c = param1CharSequence.charAt(param1Int);
/* 3527 */       if (c < '0' || c > '9') {
/* 3528 */         return -1;
/*      */       }
/* 3530 */       return c - 48;
/*      */     }
/*      */ 
/*      */     
/*      */     public int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int) {
/* 3535 */       int i = param1Int;
/* 3536 */       int j = i + param1CharSequence.length();
/* 3537 */       String str = "GMT";
/* 3538 */       if (str != null) {
/* 3539 */         if (!param1DateTimeParseContext.subSequenceEquals(param1CharSequence, i, str, 0, str.length())) {
/* 3540 */           return param1Int ^ 0xFFFFFFFF;
/*      */         }
/* 3542 */         i += str.length();
/*      */       } 
/*      */       
/* 3545 */       byte b = 0;
/* 3546 */       if (i == j) {
/* 3547 */         return param1DateTimeParseContext.setParsedField(ChronoField.OFFSET_SECONDS, 0L, param1Int, i);
/*      */       }
/* 3549 */       char c = param1CharSequence.charAt(i);
/* 3550 */       if (c == '+') {
/* 3551 */         b = 1;
/* 3552 */       } else if (c == '-') {
/* 3553 */         b = -1;
/*      */       } else {
/* 3555 */         return param1DateTimeParseContext.setParsedField(ChronoField.OFFSET_SECONDS, 0L, param1Int, i);
/*      */       } 
/* 3557 */       i++;
/* 3558 */       int k = 0;
/* 3559 */       int m = 0;
/* 3560 */       int n = 0;
/* 3561 */       if (this.style == TextStyle.FULL) {
/* 3562 */         int i1 = getDigit(param1CharSequence, i++);
/* 3563 */         int i2 = getDigit(param1CharSequence, i++);
/* 3564 */         if (i1 < 0 || i2 < 0 || param1CharSequence.charAt(i++) != ':') {
/* 3565 */           return param1Int ^ 0xFFFFFFFF;
/*      */         }
/* 3567 */         k = i1 * 10 + i2;
/* 3568 */         int i3 = getDigit(param1CharSequence, i++);
/* 3569 */         int i4 = getDigit(param1CharSequence, i++);
/* 3570 */         if (i3 < 0 || i4 < 0) {
/* 3571 */           return param1Int ^ 0xFFFFFFFF;
/*      */         }
/* 3573 */         m = i3 * 10 + i4;
/* 3574 */         if (i + 2 < j && param1CharSequence.charAt(i) == ':') {
/* 3575 */           int i5 = getDigit(param1CharSequence, i + 1);
/* 3576 */           int i6 = getDigit(param1CharSequence, i + 2);
/* 3577 */           if (i5 >= 0 && i6 >= 0) {
/* 3578 */             n = i5 * 10 + i6;
/* 3579 */             i += 3;
/*      */           } 
/*      */         } 
/*      */       } else {
/* 3583 */         k = getDigit(param1CharSequence, i++);
/* 3584 */         if (k < 0) {
/* 3585 */           return param1Int ^ 0xFFFFFFFF;
/*      */         }
/* 3587 */         if (i < j) {
/* 3588 */           int i1 = getDigit(param1CharSequence, i);
/* 3589 */           if (i1 >= 0) {
/* 3590 */             k = k * 10 + i1;
/* 3591 */             i++;
/*      */           } 
/* 3593 */           if (i + 2 < j && param1CharSequence.charAt(i) == ':' && 
/* 3594 */             i + 2 < j && param1CharSequence.charAt(i) == ':') {
/* 3595 */             int i2 = getDigit(param1CharSequence, i + 1);
/* 3596 */             int i3 = getDigit(param1CharSequence, i + 2);
/* 3597 */             if (i2 >= 0 && i3 >= 0) {
/* 3598 */               m = i2 * 10 + i3;
/* 3599 */               i += 3;
/* 3600 */               if (i + 2 < j && param1CharSequence.charAt(i) == ':') {
/* 3601 */                 int i4 = getDigit(param1CharSequence, i + 1);
/* 3602 */                 int i5 = getDigit(param1CharSequence, i + 2);
/* 3603 */                 if (i4 >= 0 && i5 >= 0) {
/* 3604 */                   n = i4 * 10 + i5;
/* 3605 */                   i += 3;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 3613 */       long l = b * (k * 3600L + m * 60L + n);
/* 3614 */       return param1DateTimeParseContext.setParsedField(ChronoField.OFFSET_SECONDS, l, param1Int, i);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 3619 */       return "LocalizedOffset(" + this.style + ")";
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class ZoneTextPrinterParser
/*      */     extends ZoneIdPrinterParser {
/*      */     private final TextStyle textStyle;
/*      */     
/*      */     private Set<String> preferredZones;
/*      */     
/*      */     private static final int STD = 0;
/*      */     private static final int DST = 1;
/*      */     private static final int GENERIC = 2;
/*      */     
/*      */     ZoneTextPrinterParser(TextStyle param1TextStyle, Set<ZoneId> param1Set)
/*      */     {
/* 3636 */       super(TemporalQueries.zone(), "ZoneText(" + param1TextStyle + ")");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3714 */       this.cachedTree = new HashMap<>();
/*      */       
/* 3716 */       this.cachedTreeCI = new HashMap<>(); this.textStyle = Objects.<TextStyle>requireNonNull(param1TextStyle, "textStyle");
/*      */       if (param1Set != null && param1Set.size() != 0) {
/*      */         this.preferredZones = new HashSet<>();
/*      */         for (ZoneId zoneId : param1Set)
/*      */           this.preferredZones.add(zoneId.getId()); 
/* 3721 */       }  } private static final Map<String, SoftReference<Map<Locale, String[]>>> cache = new ConcurrentHashMap<>(); private final Map<Locale, Map.Entry<Integer, SoftReference<DateTimeFormatterBuilder.PrefixTree>>> cachedTree; protected DateTimeFormatterBuilder.PrefixTree getTree(DateTimeParseContext param1DateTimeParseContext) { if (this.textStyle == TextStyle.NARROW) {
/* 3722 */         return super.getTree(param1DateTimeParseContext);
/*      */       }
/* 3724 */       Locale locale = param1DateTimeParseContext.getLocale();
/* 3725 */       boolean bool = param1DateTimeParseContext.isCaseSensitive();
/* 3726 */       Set<String> set = ZoneRulesProvider.getAvailableZoneIds();
/* 3727 */       int i = set.size();
/*      */       
/* 3729 */       Map<Locale, Map.Entry<Integer, SoftReference<DateTimeFormatterBuilder.PrefixTree>>> map = bool ? this.cachedTree : this.cachedTreeCI;
/*      */ 
/*      */       
/* 3732 */       Map.Entry entry = null;
/* 3733 */       DateTimeFormatterBuilder.PrefixTree prefixTree = null;
/* 3734 */       String[][] arrayOfString = (String[][])null;
/* 3735 */       if ((entry = map.get(locale)) == null || ((Integer)entry
/* 3736 */         .getKey()).intValue() != i || (
/* 3737 */         prefixTree = ((SoftReference<DateTimeFormatterBuilder.PrefixTree>)entry.getValue()).get()) == null) {
/* 3738 */         prefixTree = DateTimeFormatterBuilder.PrefixTree.newTree(param1DateTimeParseContext);
/* 3739 */         arrayOfString = TimeZoneNameUtility.getZoneStrings(locale);
/* 3740 */         for (String[] arrayOfString1 : arrayOfString) {
/* 3741 */           String str = arrayOfString1[0];
/* 3742 */           if (set.contains(str)) {
/*      */ 
/*      */             
/* 3745 */             prefixTree.add(str, str);
/* 3746 */             str = ZoneName.toZid(str, locale);
/* 3747 */             byte b = (this.textStyle == TextStyle.FULL) ? 1 : 2;
/* 3748 */             for (; b < arrayOfString1.length; b += 2) {
/* 3749 */               prefixTree.add(arrayOfString1[b], str);
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 3754 */         if (this.preferredZones != null)
/* 3755 */           for (String[] arrayOfString1 : arrayOfString) {
/* 3756 */             String str = arrayOfString1[0];
/* 3757 */             if (this.preferredZones.contains(str) && set.contains(str)) {
/*      */ 
/*      */               
/* 3760 */               byte b = (this.textStyle == TextStyle.FULL) ? 1 : 2;
/* 3761 */               for (; b < arrayOfString1.length; b += 2) {
/* 3762 */                 prefixTree.add(arrayOfString1[b], str);
/*      */               }
/*      */             } 
/*      */           }  
/* 3766 */         map.put(locale, new AbstractMap.SimpleImmutableEntry<>(Integer.valueOf(i), new SoftReference<>(prefixTree)));
/*      */       } 
/* 3768 */       return prefixTree; } private final Map<Locale, Map.Entry<Integer, SoftReference<DateTimeFormatterBuilder.PrefixTree>>> cachedTreeCI; private String getDisplayName(String param1String, int param1Int, Locale param1Locale) { if (this.textStyle == TextStyle.NARROW)
/*      */         return null;  SoftReference<Map> softReference = (SoftReference)cache.get(param1String); Map<Object, Object> map = null; String[] arrayOfString; if (softReference == null || (map = softReference.get()) == null || (arrayOfString = (String[])map.get(param1Locale)) == null) { arrayOfString = TimeZoneNameUtility.retrieveDisplayNames(param1String, param1Locale); if (arrayOfString == null)
/*      */           return null;  arrayOfString = Arrays.<String>copyOfRange(arrayOfString, 0, 7); arrayOfString[5] = TimeZoneNameUtility.retrieveGenericDisplayName(param1String, 1, param1Locale); if (arrayOfString[5] == null)
/*      */           arrayOfString[5] = arrayOfString[0];  arrayOfString[6] = TimeZoneNameUtility.retrieveGenericDisplayName(param1String, 0, param1Locale); if (arrayOfString[6] == null)
/*      */           arrayOfString[6] = arrayOfString[0];  if (map == null)
/*      */           map = new ConcurrentHashMap<>();  map.put(param1Locale, arrayOfString); cache.put(param1String, new SoftReference(map)); }  switch (param1Int) { case 0:
/*      */           return arrayOfString[this.textStyle.zoneNameStyleIndex() + 1];
/*      */         case 1:
/*      */           return arrayOfString[this.textStyle.zoneNameStyleIndex() + 3]; }  return arrayOfString[this.textStyle.zoneNameStyleIndex() + 5]; } public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) { ZoneId zoneId = param1DateTimePrintContext.<ZoneId>getValue(TemporalQueries.zoneId()); if (zoneId == null)
/*      */         return false;  String str = zoneId.getId(); if (!(zoneId instanceof ZoneOffset)) { TemporalAccessor temporalAccessor = param1DateTimePrintContext.getTemporal(); String str1 = getDisplayName(str, temporalAccessor.isSupported(ChronoField.INSTANT_SECONDS) ? (zoneId.getRules().isDaylightSavings(Instant.from(temporalAccessor)) ? 1 : 0) : 2, param1DateTimePrintContext.getLocale()); if (str1 != null)
/*      */           str = str1;  }
/*      */        param1StringBuilder.append(str); return true; }
/*      */   } static class ZoneIdPrinterParser implements DateTimePrinterParser {
/* 3781 */     private final TemporalQuery<ZoneId> query; private final String description; private static volatile Map.Entry<Integer, DateTimeFormatterBuilder.PrefixTree> cachedPrefixTree; private static volatile Map.Entry<Integer, DateTimeFormatterBuilder.PrefixTree> cachedPrefixTreeCI; ZoneIdPrinterParser(TemporalQuery<ZoneId> param1TemporalQuery, String param1String) { this.query = param1TemporalQuery;
/* 3782 */       this.description = param1String; }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) {
/* 3787 */       ZoneId zoneId = param1DateTimePrintContext.<ZoneId>getValue(this.query);
/* 3788 */       if (zoneId == null) {
/* 3789 */         return false;
/*      */       }
/* 3791 */       param1StringBuilder.append(zoneId.getId());
/* 3792 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected DateTimeFormatterBuilder.PrefixTree getTree(DateTimeParseContext param1DateTimeParseContext) {
/* 3803 */       Set<String> set = ZoneRulesProvider.getAvailableZoneIds();
/* 3804 */       int i = set.size();
/* 3805 */       Map.Entry<Integer, DateTimeFormatterBuilder.PrefixTree> entry = param1DateTimeParseContext.isCaseSensitive() ? cachedPrefixTree : cachedPrefixTreeCI;
/*      */       
/* 3807 */       if (entry == null || ((Integer)entry.getKey()).intValue() != i) {
/* 3808 */         synchronized (this) {
/* 3809 */           entry = param1DateTimeParseContext.isCaseSensitive() ? cachedPrefixTree : cachedPrefixTreeCI;
/* 3810 */           if (entry == null || ((Integer)entry.getKey()).intValue() != i) {
/* 3811 */             entry = new AbstractMap.SimpleImmutableEntry<>(Integer.valueOf(i), DateTimeFormatterBuilder.PrefixTree.newTree(set, param1DateTimeParseContext));
/* 3812 */             if (param1DateTimeParseContext.isCaseSensitive()) {
/* 3813 */               cachedPrefixTree = entry;
/*      */             } else {
/* 3815 */               cachedPrefixTreeCI = entry;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/* 3820 */       return entry.getValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int) {
/* 3830 */       int i = param1CharSequence.length();
/* 3831 */       if (param1Int > i) {
/* 3832 */         throw new IndexOutOfBoundsException();
/*      */       }
/* 3834 */       if (param1Int == i) {
/* 3835 */         return param1Int ^ 0xFFFFFFFF;
/*      */       }
/*      */ 
/*      */       
/* 3839 */       char c = param1CharSequence.charAt(param1Int);
/* 3840 */       if (c == '+' || c == '-')
/* 3841 */         return parseOffsetBased(param1DateTimeParseContext, param1CharSequence, param1Int, param1Int, DateTimeFormatterBuilder.OffsetIdPrinterParser.INSTANCE_ID_Z); 
/* 3842 */       if (i >= param1Int + 2) {
/* 3843 */         char c1 = param1CharSequence.charAt(param1Int + 1);
/* 3844 */         if (param1DateTimeParseContext.charEquals(c, 'U') && param1DateTimeParseContext.charEquals(c1, 'T')) {
/* 3845 */           if (i >= param1Int + 3 && param1DateTimeParseContext.charEquals(param1CharSequence.charAt(param1Int + 2), 'C')) {
/* 3846 */             return parseOffsetBased(param1DateTimeParseContext, param1CharSequence, param1Int, param1Int + 3, DateTimeFormatterBuilder.OffsetIdPrinterParser.INSTANCE_ID_ZERO);
/*      */           }
/* 3848 */           return parseOffsetBased(param1DateTimeParseContext, param1CharSequence, param1Int, param1Int + 2, DateTimeFormatterBuilder.OffsetIdPrinterParser.INSTANCE_ID_ZERO);
/* 3849 */         }  if (param1DateTimeParseContext.charEquals(c, 'G') && i >= param1Int + 3 && param1DateTimeParseContext
/* 3850 */           .charEquals(c1, 'M') && param1DateTimeParseContext.charEquals(param1CharSequence.charAt(param1Int + 2), 'T')) {
/* 3851 */           return parseOffsetBased(param1DateTimeParseContext, param1CharSequence, param1Int, param1Int + 3, DateTimeFormatterBuilder.OffsetIdPrinterParser.INSTANCE_ID_ZERO);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 3856 */       DateTimeFormatterBuilder.PrefixTree prefixTree = getTree(param1DateTimeParseContext);
/* 3857 */       ParsePosition parsePosition = new ParsePosition(param1Int);
/* 3858 */       String str = prefixTree.match(param1CharSequence, parsePosition);
/* 3859 */       if (str == null) {
/* 3860 */         if (param1DateTimeParseContext.charEquals(c, 'Z')) {
/* 3861 */           param1DateTimeParseContext.setParsed(ZoneOffset.UTC);
/* 3862 */           return param1Int + 1;
/*      */         } 
/* 3864 */         return param1Int ^ 0xFFFFFFFF;
/*      */       } 
/* 3866 */       param1DateTimeParseContext.setParsed(ZoneId.of(str));
/* 3867 */       return parsePosition.getIndex();
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
/*      */     private int parseOffsetBased(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int1, int param1Int2, DateTimeFormatterBuilder.OffsetIdPrinterParser param1OffsetIdPrinterParser) {
/* 3883 */       String str = param1CharSequence.toString().substring(param1Int1, param1Int2).toUpperCase();
/* 3884 */       if (param1Int2 >= param1CharSequence.length()) {
/* 3885 */         param1DateTimeParseContext.setParsed(ZoneId.of(str));
/* 3886 */         return param1Int2;
/*      */       } 
/*      */ 
/*      */       
/* 3890 */       if (param1CharSequence.charAt(param1Int2) == '0' || param1DateTimeParseContext
/* 3891 */         .charEquals(param1CharSequence.charAt(param1Int2), 'Z')) {
/* 3892 */         param1DateTimeParseContext.setParsed(ZoneId.of(str));
/* 3893 */         return param1Int2;
/*      */       } 
/*      */       
/* 3896 */       DateTimeParseContext dateTimeParseContext = param1DateTimeParseContext.copy();
/* 3897 */       int i = param1OffsetIdPrinterParser.parse(dateTimeParseContext, param1CharSequence, param1Int2);
/*      */       try {
/* 3899 */         if (i < 0) {
/* 3900 */           if (param1OffsetIdPrinterParser == DateTimeFormatterBuilder.OffsetIdPrinterParser.INSTANCE_ID_Z) {
/* 3901 */             return param1Int1 ^ 0xFFFFFFFF;
/*      */           }
/* 3903 */           param1DateTimeParseContext.setParsed(ZoneId.of(str));
/* 3904 */           return param1Int2;
/*      */         } 
/* 3906 */         int j = (int)dateTimeParseContext.getParsed(ChronoField.OFFSET_SECONDS).longValue();
/* 3907 */         ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(j);
/* 3908 */         param1DateTimeParseContext.setParsed(ZoneId.ofOffset(str, zoneOffset));
/* 3909 */         return i;
/* 3910 */       } catch (DateTimeException dateTimeException) {
/* 3911 */         return param1Int1 ^ 0xFFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 3917 */       return this.description;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class PrefixTree
/*      */   {
/*      */     protected String key;
/*      */     
/*      */     protected String value;
/*      */     
/*      */     protected char c0;
/*      */     
/*      */     protected PrefixTree child;
/*      */     protected PrefixTree sibling;
/*      */     
/*      */     private PrefixTree(String param1String1, String param1String2, PrefixTree param1PrefixTree) {
/* 3934 */       this.key = param1String1;
/* 3935 */       this.value = param1String2;
/* 3936 */       this.child = param1PrefixTree;
/* 3937 */       if (param1String1.length() == 0) {
/* 3938 */         this.c0 = Character.MAX_VALUE;
/*      */       } else {
/* 3940 */         this.c0 = this.key.charAt(0);
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
/*      */     public static PrefixTree newTree(DateTimeParseContext param1DateTimeParseContext) {
/* 3954 */       if (param1DateTimeParseContext.isCaseSensitive()) {
/* 3955 */         return new PrefixTree("", null, null);
/*      */       }
/* 3957 */       return new CI("", null, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static PrefixTree newTree(Set<String> param1Set, DateTimeParseContext param1DateTimeParseContext) {
/* 3968 */       PrefixTree prefixTree = newTree(param1DateTimeParseContext);
/* 3969 */       for (String str : param1Set) {
/* 3970 */         prefixTree.add0(str, str);
/*      */       }
/* 3972 */       return prefixTree;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PrefixTree copyTree() {
/* 3979 */       PrefixTree prefixTree = new PrefixTree(this.key, this.value, null);
/* 3980 */       if (this.child != null) {
/* 3981 */         prefixTree.child = this.child.copyTree();
/*      */       }
/* 3983 */       if (this.sibling != null) {
/* 3984 */         prefixTree.sibling = this.sibling.copyTree();
/*      */       }
/* 3986 */       return prefixTree;
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
/*      */     public boolean add(String param1String1, String param1String2) {
/* 3998 */       return add0(param1String1, param1String2);
/*      */     }
/*      */     
/*      */     private boolean add0(String param1String1, String param1String2) {
/* 4002 */       param1String1 = toKey(param1String1);
/* 4003 */       int i = prefixLength(param1String1);
/* 4004 */       if (i == this.key.length()) {
/* 4005 */         if (i < param1String1.length()) {
/* 4006 */           String str = param1String1.substring(i);
/* 4007 */           PrefixTree prefixTree1 = this.child;
/* 4008 */           while (prefixTree1 != null) {
/* 4009 */             if (isEqual(prefixTree1.c0, str.charAt(0))) {
/* 4010 */               return prefixTree1.add0(str, param1String2);
/*      */             }
/* 4012 */             prefixTree1 = prefixTree1.sibling;
/*      */           } 
/*      */           
/* 4015 */           prefixTree1 = newNode(str, param1String2, null);
/* 4016 */           prefixTree1.sibling = this.child;
/* 4017 */           this.child = prefixTree1;
/* 4018 */           return true;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4024 */         this.value = param1String2;
/* 4025 */         return true;
/*      */       } 
/*      */       
/* 4028 */       PrefixTree prefixTree = newNode(this.key.substring(i), this.value, this.child);
/* 4029 */       this.key = param1String1.substring(0, i);
/* 4030 */       this.child = prefixTree;
/* 4031 */       if (i < param1String1.length()) {
/* 4032 */         PrefixTree prefixTree1 = newNode(param1String1.substring(i), param1String2, null);
/* 4033 */         this.child.sibling = prefixTree1;
/* 4034 */         this.value = null;
/*      */       } else {
/* 4036 */         this.value = param1String2;
/*      */       } 
/* 4038 */       return true;
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
/*      */     public String match(CharSequence param1CharSequence, int param1Int1, int param1Int2) {
/* 4050 */       if (!prefixOf(param1CharSequence, param1Int1, param1Int2)) {
/* 4051 */         return null;
/*      */       }
/* 4053 */       if (this.child != null && (param1Int1 += this.key.length()) != param1Int2) {
/* 4054 */         PrefixTree prefixTree = this.child;
/*      */         do {
/* 4056 */           if (isEqual(prefixTree.c0, param1CharSequence.charAt(param1Int1))) {
/* 4057 */             String str = prefixTree.match(param1CharSequence, param1Int1, param1Int2);
/* 4058 */             if (str != null) {
/* 4059 */               return str;
/*      */             }
/* 4061 */             return this.value;
/*      */           } 
/* 4063 */           prefixTree = prefixTree.sibling;
/* 4064 */         } while (prefixTree != null);
/*      */       } 
/* 4066 */       return this.value;
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
/*      */     public String match(CharSequence param1CharSequence, ParsePosition param1ParsePosition) {
/* 4079 */       int i = param1ParsePosition.getIndex();
/* 4080 */       int j = param1CharSequence.length();
/* 4081 */       if (!prefixOf(param1CharSequence, i, j)) {
/* 4082 */         return null;
/*      */       }
/* 4084 */       i += this.key.length();
/* 4085 */       if (this.child != null && i != j) {
/* 4086 */         PrefixTree prefixTree = this.child;
/*      */         do {
/* 4088 */           if (isEqual(prefixTree.c0, param1CharSequence.charAt(i))) {
/* 4089 */             param1ParsePosition.setIndex(i);
/* 4090 */             String str = prefixTree.match(param1CharSequence, param1ParsePosition);
/* 4091 */             if (str != null) {
/* 4092 */               return str;
/*      */             }
/*      */             break;
/*      */           } 
/* 4096 */           prefixTree = prefixTree.sibling;
/* 4097 */         } while (prefixTree != null);
/*      */       } 
/* 4099 */       param1ParsePosition.setIndex(i);
/* 4100 */       return this.value;
/*      */     }
/*      */     
/*      */     protected String toKey(String param1String) {
/* 4104 */       return param1String;
/*      */     }
/*      */     
/*      */     protected PrefixTree newNode(String param1String1, String param1String2, PrefixTree param1PrefixTree) {
/* 4108 */       return new PrefixTree(param1String1, param1String2, param1PrefixTree);
/*      */     }
/*      */     
/*      */     protected boolean isEqual(char param1Char1, char param1Char2) {
/* 4112 */       return (param1Char1 == param1Char2);
/*      */     }
/*      */     
/*      */     protected boolean prefixOf(CharSequence param1CharSequence, int param1Int1, int param1Int2) {
/* 4116 */       if (param1CharSequence instanceof String) {
/* 4117 */         return ((String)param1CharSequence).startsWith(this.key, param1Int1);
/*      */       }
/* 4119 */       int i = this.key.length();
/* 4120 */       if (i > param1Int2 - param1Int1) {
/* 4121 */         return false;
/*      */       }
/* 4123 */       byte b = 0;
/* 4124 */       while (i-- > 0) {
/* 4125 */         if (!isEqual(this.key.charAt(b++), param1CharSequence.charAt(param1Int1++))) {
/* 4126 */           return false;
/*      */         }
/*      */       } 
/* 4129 */       return true;
/*      */     }
/*      */     
/*      */     private int prefixLength(String param1String) {
/* 4133 */       byte b = 0;
/* 4134 */       while (b < param1String.length() && b < this.key.length()) {
/* 4135 */         if (!isEqual(param1String.charAt(b), this.key.charAt(b))) {
/* 4136 */           return b;
/*      */         }
/* 4138 */         b++;
/*      */       } 
/* 4140 */       return b;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static class CI
/*      */       extends PrefixTree
/*      */     {
/*      */       private CI(String param2String1, String param2String2, DateTimeFormatterBuilder.PrefixTree param2PrefixTree) {
/* 4149 */         super(param2String1, param2String2, param2PrefixTree);
/*      */       }
/*      */ 
/*      */       
/*      */       protected CI newNode(String param2String1, String param2String2, DateTimeFormatterBuilder.PrefixTree param2PrefixTree) {
/* 4154 */         return new CI(param2String1, param2String2, param2PrefixTree);
/*      */       }
/*      */ 
/*      */       
/*      */       protected boolean isEqual(char param2Char1, char param2Char2) {
/* 4159 */         return DateTimeParseContext.charEqualsIgnoreCase(param2Char1, param2Char2);
/*      */       }
/*      */ 
/*      */       
/*      */       protected boolean prefixOf(CharSequence param2CharSequence, int param2Int1, int param2Int2) {
/* 4164 */         int i = this.key.length();
/* 4165 */         if (i > param2Int2 - param2Int1) {
/* 4166 */           return false;
/*      */         }
/* 4168 */         byte b = 0;
/* 4169 */         while (i-- > 0) {
/* 4170 */           if (!isEqual(this.key.charAt(b++), param2CharSequence.charAt(param2Int1++))) {
/* 4171 */             return false;
/*      */           }
/*      */         } 
/* 4174 */         return true;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static class LENIENT
/*      */       extends CI
/*      */     {
/*      */       private LENIENT(String param2String1, String param2String2, DateTimeFormatterBuilder.PrefixTree param2PrefixTree) {
/* 4185 */         super(param2String1, param2String2, param2PrefixTree);
/*      */       }
/*      */ 
/*      */       
/*      */       protected DateTimeFormatterBuilder.PrefixTree.CI newNode(String param2String1, String param2String2, DateTimeFormatterBuilder.PrefixTree param2PrefixTree) {
/* 4190 */         return new LENIENT(param2String1, param2String2, param2PrefixTree);
/*      */       }
/*      */       
/*      */       private boolean isLenientChar(char param2Char) {
/* 4194 */         return (param2Char == ' ' || param2Char == '_' || param2Char == '/');
/*      */       }
/*      */       
/*      */       protected String toKey(String param2String) {
/* 4198 */         for (byte b = 0; b < param2String.length(); b++) {
/* 4199 */           if (isLenientChar(param2String.charAt(b))) {
/* 4200 */             StringBuilder stringBuilder = new StringBuilder(param2String.length());
/* 4201 */             stringBuilder.append(param2String, 0, b);
/* 4202 */             b++;
/* 4203 */             while (b < param2String.length()) {
/* 4204 */               if (!isLenientChar(param2String.charAt(b))) {
/* 4205 */                 stringBuilder.append(param2String.charAt(b));
/*      */               }
/* 4207 */               b++;
/*      */             } 
/* 4209 */             return stringBuilder.toString();
/*      */           } 
/*      */         } 
/* 4212 */         return param2String;
/*      */       }
/*      */ 
/*      */       
/*      */       public String match(CharSequence param2CharSequence, ParsePosition param2ParsePosition) {
/* 4217 */         int i = param2ParsePosition.getIndex();
/* 4218 */         int j = param2CharSequence.length();
/* 4219 */         int k = this.key.length();
/* 4220 */         byte b = 0;
/* 4221 */         while (b < k && i < j) {
/* 4222 */           if (isLenientChar(param2CharSequence.charAt(i))) {
/* 4223 */             i++;
/*      */             continue;
/*      */           } 
/* 4226 */           if (!isEqual(this.key.charAt(b++), param2CharSequence.charAt(i++))) {
/* 4227 */             return null;
/*      */           }
/*      */         } 
/* 4230 */         if (b != k) {
/* 4231 */           return null;
/*      */         }
/* 4233 */         if (this.child != null && i != j) {
/* 4234 */           int m = i;
/* 4235 */           while (m < j && isLenientChar(param2CharSequence.charAt(m))) {
/* 4236 */             m++;
/*      */           }
/* 4238 */           if (m < j) {
/* 4239 */             DateTimeFormatterBuilder.PrefixTree prefixTree = this.child;
/*      */             do {
/* 4241 */               if (isEqual(prefixTree.c0, param2CharSequence.charAt(m))) {
/* 4242 */                 param2ParsePosition.setIndex(m);
/* 4243 */                 String str = prefixTree.match(param2CharSequence, param2ParsePosition);
/* 4244 */                 if (str != null) {
/* 4245 */                   return str;
/*      */                 }
/*      */                 break;
/*      */               } 
/* 4249 */               prefixTree = prefixTree.sibling;
/* 4250 */             } while (prefixTree != null);
/*      */           } 
/*      */         } 
/* 4253 */         param2ParsePosition.setIndex(i);
/* 4254 */         return this.value;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class ChronoPrinterParser
/*      */     implements DateTimePrinterParser
/*      */   {
/*      */     private final TextStyle textStyle;
/*      */ 
/*      */ 
/*      */     
/*      */     ChronoPrinterParser(TextStyle param1TextStyle) {
/* 4269 */       this.textStyle = param1TextStyle;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) {
/* 4274 */       Chronology chronology = param1DateTimePrintContext.<Chronology>getValue(TemporalQueries.chronology());
/* 4275 */       if (chronology == null) {
/* 4276 */         return false;
/*      */       }
/* 4278 */       if (this.textStyle == null) {
/* 4279 */         param1StringBuilder.append(chronology.getId());
/*      */       } else {
/* 4281 */         param1StringBuilder.append(getChronologyName(chronology, param1DateTimePrintContext.getLocale()));
/*      */       } 
/* 4283 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int) {
/* 4289 */       if (param1Int < 0 || param1Int > param1CharSequence.length()) {
/* 4290 */         throw new IndexOutOfBoundsException();
/*      */       }
/* 4292 */       Set<Chronology> set = Chronology.getAvailableChronologies();
/* 4293 */       Chronology chronology = null;
/* 4294 */       int i = -1;
/* 4295 */       for (Chronology chronology1 : set) {
/*      */         String str;
/* 4297 */         if (this.textStyle == null) {
/* 4298 */           str = chronology1.getId();
/*      */         } else {
/* 4300 */           str = getChronologyName(chronology1, param1DateTimeParseContext.getLocale());
/*      */         } 
/* 4302 */         int j = str.length();
/* 4303 */         if (j > i && param1DateTimeParseContext.subSequenceEquals(param1CharSequence, param1Int, str, 0, j)) {
/* 4304 */           chronology = chronology1;
/* 4305 */           i = j;
/*      */         } 
/*      */       } 
/* 4308 */       if (chronology == null) {
/* 4309 */         return param1Int ^ 0xFFFFFFFF;
/*      */       }
/* 4311 */       param1DateTimeParseContext.setParsed(chronology);
/* 4312 */       return param1Int + i;
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
/*      */     private String getChronologyName(Chronology param1Chronology, Locale param1Locale) {
/* 4326 */       String str1 = "calendarname." + param1Chronology.getCalendarType();
/* 4327 */       String str2 = DateTimeTextProvider.<String>getLocalizedResource(str1, param1Locale);
/* 4328 */       return (str2 != null) ? str2 : param1Chronology.getId();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class LocalizedPrinterParser
/*      */     implements DateTimePrinterParser
/*      */   {
/* 4338 */     private static final ConcurrentMap<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>(16, 0.75F, 2);
/*      */ 
/*      */ 
/*      */     
/*      */     private final FormatStyle dateStyle;
/*      */ 
/*      */ 
/*      */     
/*      */     private final FormatStyle timeStyle;
/*      */ 
/*      */ 
/*      */     
/*      */     LocalizedPrinterParser(FormatStyle param1FormatStyle1, FormatStyle param1FormatStyle2) {
/* 4351 */       this.dateStyle = param1FormatStyle1;
/* 4352 */       this.timeStyle = param1FormatStyle2;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) {
/* 4357 */       Chronology chronology = Chronology.from(param1DateTimePrintContext.getTemporal());
/* 4358 */       return formatter(param1DateTimePrintContext.getLocale(), chronology).toPrinterParser(false).format(param1DateTimePrintContext, param1StringBuilder);
/*      */     }
/*      */ 
/*      */     
/*      */     public int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int) {
/* 4363 */       Chronology chronology = param1DateTimeParseContext.getEffectiveChronology();
/* 4364 */       return formatter(param1DateTimeParseContext.getLocale(), chronology).toPrinterParser(false).parse(param1DateTimeParseContext, param1CharSequence, param1Int);
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
/*      */     private DateTimeFormatter formatter(Locale param1Locale, Chronology param1Chronology) {
/* 4379 */       String str = param1Chronology.getId() + '|' + param1Locale.toString() + '|' + this.dateStyle + this.timeStyle;
/* 4380 */       DateTimeFormatter dateTimeFormatter = FORMATTER_CACHE.get(str);
/* 4381 */       if (dateTimeFormatter == null) {
/* 4382 */         String str1 = DateTimeFormatterBuilder.getLocalizedDateTimePattern(this.dateStyle, this.timeStyle, param1Chronology, param1Locale);
/* 4383 */         dateTimeFormatter = (new DateTimeFormatterBuilder()).appendPattern(str1).toFormatter(param1Locale);
/* 4384 */         DateTimeFormatter dateTimeFormatter1 = FORMATTER_CACHE.putIfAbsent(str, dateTimeFormatter);
/* 4385 */         if (dateTimeFormatter1 != null) {
/* 4386 */           dateTimeFormatter = dateTimeFormatter1;
/*      */         }
/*      */       } 
/* 4389 */       return dateTimeFormatter;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 4394 */       return "Localized(" + ((this.dateStyle != null) ? (String)this.dateStyle : "") + "," + ((this.timeStyle != null) ? (String)this.timeStyle : "") + ")";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class WeekBasedFieldPrinterParser
/*      */     implements DateTimePrinterParser
/*      */   {
/*      */     private char chr;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int count;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     WeekBasedFieldPrinterParser(char param1Char, int param1Int) {
/* 4418 */       this.chr = param1Char;
/* 4419 */       this.count = param1Int;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder) {
/* 4424 */       return printerParser(param1DateTimePrintContext.getLocale()).format(param1DateTimePrintContext, param1StringBuilder);
/*      */     }
/*      */ 
/*      */     
/*      */     public int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int) {
/* 4429 */       return printerParser(param1DateTimeParseContext.getLocale()).parse(param1DateTimeParseContext, param1CharSequence, param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private DateTimeFormatterBuilder.DateTimePrinterParser printerParser(Locale param1Locale) {
/* 4440 */       WeekFields weekFields = WeekFields.of(param1Locale);
/* 4441 */       TemporalField temporalField = null;
/* 4442 */       switch (this.chr) {
/*      */         case 'Y':
/* 4444 */           temporalField = weekFields.weekBasedYear();
/* 4445 */           if (this.count == 2) {
/* 4446 */             return new DateTimeFormatterBuilder.ReducedPrinterParser(temporalField, 2, 2, 0, DateTimeFormatterBuilder.ReducedPrinterParser.BASE_DATE, 0);
/*      */           }
/* 4448 */           return new DateTimeFormatterBuilder.NumberPrinterParser(temporalField, this.count, 19, (this.count < 4) ? SignStyle.NORMAL : SignStyle.EXCEEDS_PAD, -1);
/*      */ 
/*      */         
/*      */         case 'c':
/*      */         case 'e':
/* 4453 */           temporalField = weekFields.dayOfWeek();
/*      */           break;
/*      */         case 'w':
/* 4456 */           temporalField = weekFields.weekOfWeekBasedYear();
/*      */           break;
/*      */         case 'W':
/* 4459 */           temporalField = weekFields.weekOfMonth();
/*      */           break;
/*      */         default:
/* 4462 */           throw new IllegalStateException("unreachable");
/*      */       } 
/* 4464 */       return new DateTimeFormatterBuilder.NumberPrinterParser(temporalField, (this.count == 2) ? 2 : 1, 2, SignStyle.NOT_NEGATIVE);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 4469 */       StringBuilder stringBuilder = new StringBuilder(30);
/* 4470 */       stringBuilder.append("Localized(");
/* 4471 */       if (this.chr == 'Y') {
/* 4472 */         if (this.count == 1) {
/* 4473 */           stringBuilder.append("WeekBasedYear");
/* 4474 */         } else if (this.count == 2) {
/* 4475 */           stringBuilder.append("ReducedValue(WeekBasedYear,2,2,2000-01-01)");
/*      */         } else {
/* 4477 */           stringBuilder.append("WeekBasedYear,").append(this.count).append(",")
/* 4478 */             .append(19).append(",")
/* 4479 */             .append((this.count < 4) ? SignStyle.NORMAL : SignStyle.EXCEEDS_PAD);
/*      */         } 
/*      */       } else {
/* 4482 */         switch (this.chr) {
/*      */           case 'c':
/*      */           case 'e':
/* 4485 */             stringBuilder.append("DayOfWeek");
/*      */             break;
/*      */           case 'w':
/* 4488 */             stringBuilder.append("WeekOfWeekBasedYear");
/*      */             break;
/*      */           case 'W':
/* 4491 */             stringBuilder.append("WeekOfMonth");
/*      */             break;
/*      */         } 
/*      */ 
/*      */         
/* 4496 */         stringBuilder.append(",");
/* 4497 */         stringBuilder.append(this.count);
/*      */       } 
/* 4499 */       stringBuilder.append(")");
/* 4500 */       return stringBuilder.toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4508 */   static final Comparator<String> LENGTH_SORT = new Comparator<String>()
/*      */     {
/*      */       public int compare(String param1String1, String param1String2) {
/* 4511 */         return (param1String1.length() == param1String2.length()) ? param1String1.compareTo(param1String2) : (param1String1.length() - param1String2.length());
/*      */       }
/*      */     };
/*      */   
/*      */   static interface DateTimePrinterParser {
/*      */     boolean format(DateTimePrintContext param1DateTimePrintContext, StringBuilder param1StringBuilder);
/*      */     
/*      */     int parse(DateTimeParseContext param1DateTimeParseContext, CharSequence param1CharSequence, int param1Int);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/format/DateTimeFormatterBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */