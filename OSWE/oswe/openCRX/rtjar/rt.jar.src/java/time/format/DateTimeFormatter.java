/*      */ package java.time.format;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.text.FieldPosition;
/*      */ import java.text.Format;
/*      */ import java.text.ParseException;
/*      */ import java.text.ParsePosition;
/*      */ import java.time.DateTimeException;
/*      */ import java.time.Period;
/*      */ import java.time.ZoneId;
/*      */ import java.time.chrono.Chronology;
/*      */ import java.time.chrono.IsoChronology;
/*      */ import java.time.temporal.ChronoField;
/*      */ import java.time.temporal.IsoFields;
/*      */ import java.time.temporal.TemporalAccessor;
/*      */ import java.time.temporal.TemporalField;
/*      */ import java.time.temporal.TemporalQuery;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class DateTimeFormatter
/*      */ {
/*      */   private final DateTimeFormatterBuilder.CompositePrinterParser printerParser;
/*      */   private final Locale locale;
/*      */   private final DecimalStyle decimalStyle;
/*      */   private final ResolverStyle resolverStyle;
/*      */   private final Set<TemporalField> resolverFields;
/*      */   private final Chronology chrono;
/*      */   private final ZoneId zone;
/*      */   
/*      */   public static DateTimeFormatter ofPattern(String paramString) {
/*  536 */     return (new DateTimeFormatterBuilder()).appendPattern(paramString).toFormatter();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DateTimeFormatter ofPattern(String paramString, Locale paramLocale) {
/*  560 */     return (new DateTimeFormatterBuilder()).appendPattern(paramString).toFormatter(paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DateTimeFormatter ofLocalizedDate(FormatStyle paramFormatStyle) {
/*  587 */     Objects.requireNonNull(paramFormatStyle, "dateStyle");
/*  588 */     return (new DateTimeFormatterBuilder()).appendLocalized(paramFormatStyle, null)
/*  589 */       .toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DateTimeFormatter ofLocalizedTime(FormatStyle paramFormatStyle) {
/*  615 */     Objects.requireNonNull(paramFormatStyle, "timeStyle");
/*  616 */     return (new DateTimeFormatterBuilder()).appendLocalized(null, paramFormatStyle)
/*  617 */       .toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DateTimeFormatter ofLocalizedDateTime(FormatStyle paramFormatStyle) {
/*  643 */     Objects.requireNonNull(paramFormatStyle, "dateTimeStyle");
/*  644 */     return (new DateTimeFormatterBuilder()).appendLocalized(paramFormatStyle, paramFormatStyle)
/*  645 */       .toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DateTimeFormatter ofLocalizedDateTime(FormatStyle paramFormatStyle1, FormatStyle paramFormatStyle2) {
/*  672 */     Objects.requireNonNull(paramFormatStyle1, "dateStyle");
/*  673 */     Objects.requireNonNull(paramFormatStyle2, "timeStyle");
/*  674 */     return (new DateTimeFormatterBuilder()).appendLocalized(paramFormatStyle1, paramFormatStyle2)
/*  675 */       .toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  704 */   public static final DateTimeFormatter ISO_LOCAL_DATE = (new DateTimeFormatterBuilder())
/*  705 */     .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
/*  706 */     .appendLiteral('-')
/*  707 */     .appendValue(ChronoField.MONTH_OF_YEAR, 2)
/*  708 */     .appendLiteral('-')
/*  709 */     .appendValue(ChronoField.DAY_OF_MONTH, 2)
/*  710 */     .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  734 */   public static final DateTimeFormatter ISO_OFFSET_DATE = (new DateTimeFormatterBuilder())
/*  735 */     .parseCaseInsensitive()
/*  736 */     .append(ISO_LOCAL_DATE)
/*  737 */     .appendOffsetId()
/*  738 */     .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  766 */   public static final DateTimeFormatter ISO_DATE = (new DateTimeFormatterBuilder())
/*  767 */     .parseCaseInsensitive()
/*  768 */     .append(ISO_LOCAL_DATE)
/*  769 */     .optionalStart()
/*  770 */     .appendOffsetId()
/*  771 */     .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  803 */   public static final DateTimeFormatter ISO_LOCAL_TIME = (new DateTimeFormatterBuilder())
/*  804 */     .appendValue(ChronoField.HOUR_OF_DAY, 2)
/*  805 */     .appendLiteral(':')
/*  806 */     .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
/*  807 */     .optionalStart()
/*  808 */     .appendLiteral(':')
/*  809 */     .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
/*  810 */     .optionalStart()
/*  811 */     .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
/*  812 */     .toFormatter(ResolverStyle.STRICT, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  835 */   public static final DateTimeFormatter ISO_OFFSET_TIME = (new DateTimeFormatterBuilder())
/*  836 */     .parseCaseInsensitive()
/*  837 */     .append(ISO_LOCAL_TIME)
/*  838 */     .appendOffsetId()
/*  839 */     .toFormatter(ResolverStyle.STRICT, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  866 */   public static final DateTimeFormatter ISO_TIME = (new DateTimeFormatterBuilder())
/*  867 */     .parseCaseInsensitive()
/*  868 */     .append(ISO_LOCAL_TIME)
/*  869 */     .optionalStart()
/*  870 */     .appendOffsetId()
/*  871 */     .toFormatter(ResolverStyle.STRICT, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  894 */   public static final DateTimeFormatter ISO_LOCAL_DATE_TIME = (new DateTimeFormatterBuilder())
/*  895 */     .parseCaseInsensitive()
/*  896 */     .append(ISO_LOCAL_DATE)
/*  897 */     .appendLiteral('T')
/*  898 */     .append(ISO_LOCAL_TIME)
/*  899 */     .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  923 */   public static final DateTimeFormatter ISO_OFFSET_DATE_TIME = (new DateTimeFormatterBuilder())
/*  924 */     .parseCaseInsensitive()
/*  925 */     .append(ISO_LOCAL_DATE_TIME)
/*  926 */     .appendOffsetId()
/*  927 */     .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  955 */   public static final DateTimeFormatter ISO_ZONED_DATE_TIME = (new DateTimeFormatterBuilder())
/*  956 */     .append(ISO_OFFSET_DATE_TIME)
/*  957 */     .optionalStart()
/*  958 */     .appendLiteral('[')
/*  959 */     .parseCaseSensitive()
/*  960 */     .appendZoneRegionId()
/*  961 */     .appendLiteral(']')
/*  962 */     .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  996 */   public static final DateTimeFormatter ISO_DATE_TIME = (new DateTimeFormatterBuilder())
/*  997 */     .append(ISO_LOCAL_DATE_TIME)
/*  998 */     .optionalStart()
/*  999 */     .appendOffsetId()
/* 1000 */     .optionalStart()
/* 1001 */     .appendLiteral('[')
/* 1002 */     .parseCaseSensitive()
/* 1003 */     .appendZoneRegionId()
/* 1004 */     .appendLiteral(']')
/* 1005 */     .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1038 */   public static final DateTimeFormatter ISO_ORDINAL_DATE = (new DateTimeFormatterBuilder())
/* 1039 */     .parseCaseInsensitive()
/* 1040 */     .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
/* 1041 */     .appendLiteral('-')
/* 1042 */     .appendValue(ChronoField.DAY_OF_YEAR, 3)
/* 1043 */     .optionalStart()
/* 1044 */     .appendOffsetId()
/* 1045 */     .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1082 */   public static final DateTimeFormatter ISO_WEEK_DATE = (new DateTimeFormatterBuilder())
/* 1083 */     .parseCaseInsensitive()
/* 1084 */     .appendValue(IsoFields.WEEK_BASED_YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
/* 1085 */     .appendLiteral("-W")
/* 1086 */     .appendValue(IsoFields.WEEK_OF_WEEK_BASED_YEAR, 2)
/* 1087 */     .appendLiteral('-')
/* 1088 */     .appendValue(ChronoField.DAY_OF_WEEK, 1)
/* 1089 */     .optionalStart()
/* 1090 */     .appendOffsetId()
/* 1091 */     .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1127 */   public static final DateTimeFormatter ISO_INSTANT = (new DateTimeFormatterBuilder())
/* 1128 */     .parseCaseInsensitive()
/* 1129 */     .appendInstant()
/* 1130 */     .toFormatter(ResolverStyle.STRICT, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1163 */   public static final DateTimeFormatter BASIC_ISO_DATE = (new DateTimeFormatterBuilder())
/* 1164 */     .parseCaseInsensitive()
/* 1165 */     .appendValue(ChronoField.YEAR, 4)
/* 1166 */     .appendValue(ChronoField.MONTH_OF_YEAR, 2)
/* 1167 */     .appendValue(ChronoField.DAY_OF_MONTH, 2)
/* 1168 */     .optionalStart()
/* 1169 */     .appendOffset("+HHMMss", "Z")
/* 1170 */     .toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final DateTimeFormatter RFC_1123_DATE_TIME;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final TemporalQuery<Period> PARSED_EXCESS_DAYS;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final TemporalQuery<Boolean> PARSED_LEAP_SECOND;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/* 1221 */     HashMap<Object, Object> hashMap1 = new HashMap<>();
/* 1222 */     hashMap1.put(Long.valueOf(1L), "Mon");
/* 1223 */     hashMap1.put(Long.valueOf(2L), "Tue");
/* 1224 */     hashMap1.put(Long.valueOf(3L), "Wed");
/* 1225 */     hashMap1.put(Long.valueOf(4L), "Thu");
/* 1226 */     hashMap1.put(Long.valueOf(5L), "Fri");
/* 1227 */     hashMap1.put(Long.valueOf(6L), "Sat");
/* 1228 */     hashMap1.put(Long.valueOf(7L), "Sun");
/* 1229 */     HashMap<Object, Object> hashMap2 = new HashMap<>();
/* 1230 */     hashMap2.put(Long.valueOf(1L), "Jan");
/* 1231 */     hashMap2.put(Long.valueOf(2L), "Feb");
/* 1232 */     hashMap2.put(Long.valueOf(3L), "Mar");
/* 1233 */     hashMap2.put(Long.valueOf(4L), "Apr");
/* 1234 */     hashMap2.put(Long.valueOf(5L), "May");
/* 1235 */     hashMap2.put(Long.valueOf(6L), "Jun");
/* 1236 */     hashMap2.put(Long.valueOf(7L), "Jul");
/* 1237 */     hashMap2.put(Long.valueOf(8L), "Aug");
/* 1238 */     hashMap2.put(Long.valueOf(9L), "Sep");
/* 1239 */     hashMap2.put(Long.valueOf(10L), "Oct");
/* 1240 */     hashMap2.put(Long.valueOf(11L), "Nov");
/* 1241 */     hashMap2.put(Long.valueOf(12L), "Dec");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1264 */     RFC_1123_DATE_TIME = (new DateTimeFormatterBuilder()).parseCaseInsensitive().parseLenient().optionalStart().appendText(ChronoField.DAY_OF_WEEK, (Map)hashMap1).appendLiteral(", ").optionalEnd().appendValue(ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NOT_NEGATIVE).appendLiteral(' ').appendText(ChronoField.MONTH_OF_YEAR, (Map)hashMap2).appendLiteral(' ').appendValue(ChronoField.YEAR, 4).appendLiteral(' ').appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral(':').appendValue(ChronoField.MINUTE_OF_HOUR, 2).optionalStart().appendLiteral(':').appendValue(ChronoField.SECOND_OF_MINUTE, 2).optionalEnd().appendLiteral(' ').appendOffset("+HHMM", "GMT").toFormatter(ResolverStyle.SMART, IsoChronology.INSTANCE);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1311 */     PARSED_EXCESS_DAYS = (paramTemporalAccessor -> (paramTemporalAccessor instanceof Parsed) ? ((Parsed)paramTemporalAccessor).excessDays : Period.ZERO);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1352 */     PARSED_LEAP_SECOND = (paramTemporalAccessor -> (paramTemporalAccessor instanceof Parsed) ? Boolean.valueOf(((Parsed)paramTemporalAccessor).leapSecond) : Boolean.FALSE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final TemporalQuery<Period> parsedExcessDays() {
/*      */     return PARSED_EXCESS_DAYS;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final TemporalQuery<Boolean> parsedLeapSecond() {
/*      */     return PARSED_LEAP_SECOND;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   DateTimeFormatter(DateTimeFormatterBuilder.CompositePrinterParser paramCompositePrinterParser, Locale paramLocale, DecimalStyle paramDecimalStyle, ResolverStyle paramResolverStyle, Set<TemporalField> paramSet, Chronology paramChronology, ZoneId paramZoneId) {
/* 1376 */     this.printerParser = Objects.<DateTimeFormatterBuilder.CompositePrinterParser>requireNonNull(paramCompositePrinterParser, "printerParser");
/* 1377 */     this.resolverFields = paramSet;
/* 1378 */     this.locale = Objects.<Locale>requireNonNull(paramLocale, "locale");
/* 1379 */     this.decimalStyle = Objects.<DecimalStyle>requireNonNull(paramDecimalStyle, "decimalStyle");
/* 1380 */     this.resolverStyle = Objects.<ResolverStyle>requireNonNull(paramResolverStyle, "resolverStyle");
/* 1381 */     this.chrono = paramChronology;
/* 1382 */     this.zone = paramZoneId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/* 1395 */     return this.locale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatter withLocale(Locale paramLocale) {
/* 1410 */     if (this.locale.equals(paramLocale)) {
/* 1411 */       return this;
/*      */     }
/* 1413 */     return new DateTimeFormatter(this.printerParser, paramLocale, this.decimalStyle, this.resolverStyle, this.resolverFields, this.chrono, this.zone);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DecimalStyle getDecimalStyle() {
/* 1423 */     return this.decimalStyle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatter withDecimalStyle(DecimalStyle paramDecimalStyle) {
/* 1435 */     if (this.decimalStyle.equals(paramDecimalStyle)) {
/* 1436 */       return this;
/*      */     }
/* 1438 */     return new DateTimeFormatter(this.printerParser, this.locale, paramDecimalStyle, this.resolverStyle, this.resolverFields, this.chrono, this.zone);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Chronology getChronology() {
/* 1452 */     return this.chrono;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatter withChronology(Chronology paramChronology) {
/* 1489 */     if (Objects.equals(this.chrono, paramChronology)) {
/* 1490 */       return this;
/*      */     }
/* 1492 */     return new DateTimeFormatter(this.printerParser, this.locale, this.decimalStyle, this.resolverStyle, this.resolverFields, paramChronology, this.zone);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ZoneId getZone() {
/* 1506 */     return this.zone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatter withZone(ZoneId paramZoneId) {
/* 1546 */     if (Objects.equals(this.zone, paramZoneId)) {
/* 1547 */       return this;
/*      */     }
/* 1549 */     return new DateTimeFormatter(this.printerParser, this.locale, this.decimalStyle, this.resolverStyle, this.resolverFields, this.chrono, paramZoneId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ResolverStyle getResolverStyle() {
/* 1564 */     return this.resolverStyle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatter withResolverStyle(ResolverStyle paramResolverStyle) {
/* 1587 */     Objects.requireNonNull(paramResolverStyle, "resolverStyle");
/* 1588 */     if (Objects.equals(this.resolverStyle, paramResolverStyle)) {
/* 1589 */       return this;
/*      */     }
/* 1591 */     return new DateTimeFormatter(this.printerParser, this.locale, this.decimalStyle, paramResolverStyle, this.resolverFields, this.chrono, this.zone);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<TemporalField> getResolverFields() {
/* 1606 */     return this.resolverFields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatter withResolverFields(TemporalField... paramVarArgs) {
/* 1649 */     Set<?> set = null;
/* 1650 */     if (paramVarArgs != null) {
/* 1651 */       set = Collections.unmodifiableSet(new HashSet(Arrays.asList((Object[])paramVarArgs)));
/*      */     }
/* 1653 */     if (Objects.equals(this.resolverFields, set)) {
/* 1654 */       return this;
/*      */     }
/* 1656 */     return new DateTimeFormatter(this.printerParser, this.locale, this.decimalStyle, this.resolverStyle, (Set)set, this.chrono, this.zone);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateTimeFormatter withResolverFields(Set<TemporalField> paramSet) {
/* 1699 */     if (Objects.equals(this.resolverFields, paramSet)) {
/* 1700 */       return this;
/*      */     }
/* 1702 */     if (paramSet != null) {
/* 1703 */       paramSet = Collections.unmodifiableSet(new HashSet<>(paramSet));
/*      */     }
/* 1705 */     return new DateTimeFormatter(this.printerParser, this.locale, this.decimalStyle, this.resolverStyle, paramSet, this.chrono, this.zone);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String format(TemporalAccessor paramTemporalAccessor) {
/* 1719 */     StringBuilder stringBuilder = new StringBuilder(32);
/* 1720 */     formatTo(paramTemporalAccessor, stringBuilder);
/* 1721 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void formatTo(TemporalAccessor paramTemporalAccessor, Appendable paramAppendable) {
/* 1741 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/* 1742 */     Objects.requireNonNull(paramAppendable, "appendable");
/*      */     try {
/* 1744 */       DateTimePrintContext dateTimePrintContext = new DateTimePrintContext(paramTemporalAccessor, this);
/* 1745 */       if (paramAppendable instanceof StringBuilder) {
/* 1746 */         this.printerParser.format(dateTimePrintContext, (StringBuilder)paramAppendable);
/*      */       } else {
/*      */         
/* 1749 */         StringBuilder stringBuilder = new StringBuilder(32);
/* 1750 */         this.printerParser.format(dateTimePrintContext, stringBuilder);
/* 1751 */         paramAppendable.append(stringBuilder);
/*      */       } 
/* 1753 */     } catch (IOException iOException) {
/* 1754 */       throw new DateTimeException(iOException.getMessage(), iOException);
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
/*      */   public TemporalAccessor parse(CharSequence paramCharSequence) {
/* 1775 */     Objects.requireNonNull(paramCharSequence, "text");
/*      */     try {
/* 1777 */       return parseResolved0(paramCharSequence, null);
/* 1778 */     } catch (DateTimeParseException dateTimeParseException) {
/* 1779 */       throw dateTimeParseException;
/* 1780 */     } catch (RuntimeException runtimeException) {
/* 1781 */       throw createError(paramCharSequence, runtimeException);
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
/*      */   public TemporalAccessor parse(CharSequence paramCharSequence, ParsePosition paramParsePosition) {
/* 1816 */     Objects.requireNonNull(paramCharSequence, "text");
/* 1817 */     Objects.requireNonNull(paramParsePosition, "position");
/*      */     try {
/* 1819 */       return parseResolved0(paramCharSequence, paramParsePosition);
/* 1820 */     } catch (DateTimeParseException|IndexOutOfBoundsException dateTimeParseException) {
/* 1821 */       throw dateTimeParseException;
/* 1822 */     } catch (RuntimeException runtimeException) {
/* 1823 */       throw createError(paramCharSequence, runtimeException);
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
/*      */   public <T> T parse(CharSequence paramCharSequence, TemporalQuery<T> paramTemporalQuery) {
/* 1848 */     Objects.requireNonNull(paramCharSequence, "text");
/* 1849 */     Objects.requireNonNull(paramTemporalQuery, "query");
/*      */     try {
/* 1851 */       return parseResolved0(paramCharSequence, null).query(paramTemporalQuery);
/* 1852 */     } catch (DateTimeParseException dateTimeParseException) {
/* 1853 */       throw dateTimeParseException;
/* 1854 */     } catch (RuntimeException runtimeException) {
/* 1855 */       throw createError(paramCharSequence, runtimeException);
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
/*      */   public TemporalAccessor parseBest(CharSequence paramCharSequence, TemporalQuery<?>... paramVarArgs) {
/* 1891 */     Objects.requireNonNull(paramCharSequence, "text");
/* 1892 */     Objects.requireNonNull(paramVarArgs, "queries");
/* 1893 */     if (paramVarArgs.length < 2) {
/* 1894 */       throw new IllegalArgumentException("At least two queries must be specified");
/*      */     }
/*      */     try {
/* 1897 */       TemporalAccessor temporalAccessor = parseResolved0(paramCharSequence, null);
/* 1898 */       for (TemporalQuery<?> temporalQuery : paramVarArgs) {
/*      */         try {
/* 1900 */           return (TemporalAccessor)temporalAccessor.query(temporalQuery);
/* 1901 */         } catch (RuntimeException runtimeException) {}
/*      */       } 
/*      */ 
/*      */       
/* 1905 */       throw new DateTimeException("Unable to convert parsed text using any of the specified queries");
/* 1906 */     } catch (DateTimeParseException dateTimeParseException) {
/* 1907 */       throw dateTimeParseException;
/* 1908 */     } catch (RuntimeException runtimeException) {
/* 1909 */       throw createError(paramCharSequence, runtimeException);
/*      */     } 
/*      */   }
/*      */   
/*      */   private DateTimeParseException createError(CharSequence paramCharSequence, RuntimeException paramRuntimeException) {
/*      */     String str;
/* 1915 */     if (paramCharSequence.length() > 64) {
/* 1916 */       str = paramCharSequence.subSequence(0, 64).toString() + "...";
/*      */     } else {
/* 1918 */       str = paramCharSequence.toString();
/*      */     } 
/* 1920 */     return new DateTimeParseException("Text '" + str + "' could not be parsed: " + paramRuntimeException.getMessage(), paramCharSequence, 0, paramRuntimeException);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TemporalAccessor parseResolved0(CharSequence paramCharSequence, ParsePosition paramParsePosition) {
/* 1938 */     ParsePosition parsePosition = (paramParsePosition != null) ? paramParsePosition : new ParsePosition(0);
/* 1939 */     DateTimeParseContext dateTimeParseContext = parseUnresolved0(paramCharSequence, parsePosition);
/* 1940 */     if (dateTimeParseContext == null || parsePosition.getErrorIndex() >= 0 || (paramParsePosition == null && parsePosition.getIndex() < paramCharSequence.length())) {
/*      */       String str;
/* 1942 */       if (paramCharSequence.length() > 64) {
/* 1943 */         str = paramCharSequence.subSequence(0, 64).toString() + "...";
/*      */       } else {
/* 1945 */         str = paramCharSequence.toString();
/*      */       } 
/* 1947 */       if (parsePosition.getErrorIndex() >= 0) {
/* 1948 */         throw new DateTimeParseException("Text '" + str + "' could not be parsed at index " + parsePosition
/* 1949 */             .getErrorIndex(), paramCharSequence, parsePosition.getErrorIndex());
/*      */       }
/* 1951 */       throw new DateTimeParseException("Text '" + str + "' could not be parsed, unparsed text found at index " + parsePosition
/* 1952 */           .getIndex(), paramCharSequence, parsePosition.getIndex());
/*      */     } 
/*      */     
/* 1955 */     return dateTimeParseContext.toResolved(this.resolverStyle, this.resolverFields);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TemporalAccessor parseUnresolved(CharSequence paramCharSequence, ParsePosition paramParsePosition) {
/* 1998 */     DateTimeParseContext dateTimeParseContext = parseUnresolved0(paramCharSequence, paramParsePosition);
/* 1999 */     if (dateTimeParseContext == null) {
/* 2000 */       return null;
/*      */     }
/* 2002 */     return dateTimeParseContext.toUnresolved();
/*      */   }
/*      */   
/*      */   private DateTimeParseContext parseUnresolved0(CharSequence paramCharSequence, ParsePosition paramParsePosition) {
/* 2006 */     Objects.requireNonNull(paramCharSequence, "text");
/* 2007 */     Objects.requireNonNull(paramParsePosition, "position");
/* 2008 */     DateTimeParseContext dateTimeParseContext = new DateTimeParseContext(this);
/* 2009 */     int i = paramParsePosition.getIndex();
/* 2010 */     i = this.printerParser.parse(dateTimeParseContext, paramCharSequence, i);
/* 2011 */     if (i < 0) {
/* 2012 */       paramParsePosition.setErrorIndex(i ^ 0xFFFFFFFF);
/* 2013 */       return null;
/*      */     } 
/* 2015 */     paramParsePosition.setIndex(i);
/* 2016 */     return dateTimeParseContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   DateTimeFormatterBuilder.CompositePrinterParser toPrinterParser(boolean paramBoolean) {
/* 2027 */     return this.printerParser.withOptional(paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Format toFormat() {
/* 2044 */     return new ClassicFormat(this, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Format toFormat(TemporalQuery<?> paramTemporalQuery) {
/* 2064 */     Objects.requireNonNull(paramTemporalQuery, "parseQuery");
/* 2065 */     return new ClassicFormat(this, paramTemporalQuery);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2076 */     String str = this.printerParser.toString();
/* 2077 */     str = str.startsWith("[") ? str : str.substring(1, str.length() - 1);
/* 2078 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class ClassicFormat
/*      */     extends Format
/*      */   {
/*      */     private final DateTimeFormatter formatter;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final TemporalQuery<?> parseType;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ClassicFormat(DateTimeFormatter param1DateTimeFormatter, TemporalQuery<?> param1TemporalQuery) {
/* 2099 */       this.formatter = param1DateTimeFormatter;
/* 2100 */       this.parseType = param1TemporalQuery;
/*      */     }
/*      */ 
/*      */     
/*      */     public StringBuffer format(Object param1Object, StringBuffer param1StringBuffer, FieldPosition param1FieldPosition) {
/* 2105 */       Objects.requireNonNull(param1Object, "obj");
/* 2106 */       Objects.requireNonNull(param1StringBuffer, "toAppendTo");
/* 2107 */       Objects.requireNonNull(param1FieldPosition, "pos");
/* 2108 */       if (!(param1Object instanceof TemporalAccessor)) {
/* 2109 */         throw new IllegalArgumentException("Format target must implement TemporalAccessor");
/*      */       }
/* 2111 */       param1FieldPosition.setBeginIndex(0);
/* 2112 */       param1FieldPosition.setEndIndex(0);
/*      */       try {
/* 2114 */         this.formatter.formatTo((TemporalAccessor)param1Object, param1StringBuffer);
/* 2115 */       } catch (RuntimeException runtimeException) {
/* 2116 */         throw new IllegalArgumentException(runtimeException.getMessage(), runtimeException);
/*      */       } 
/* 2118 */       return param1StringBuffer;
/*      */     }
/*      */     
/*      */     public Object parseObject(String param1String) throws ParseException {
/* 2122 */       Objects.requireNonNull(param1String, "text");
/*      */       try {
/* 2124 */         if (this.parseType == null) {
/* 2125 */           return this.formatter.parseResolved0(param1String, null);
/*      */         }
/* 2127 */         return this.formatter.parse(param1String, this.parseType);
/* 2128 */       } catch (DateTimeParseException dateTimeParseException) {
/* 2129 */         throw new ParseException(dateTimeParseException.getMessage(), dateTimeParseException.getErrorIndex());
/* 2130 */       } catch (RuntimeException runtimeException) {
/* 2131 */         throw (ParseException)(new ParseException(runtimeException.getMessage(), 0)).initCause(runtimeException);
/*      */       } 
/*      */     }
/*      */     public Object parseObject(String param1String, ParsePosition param1ParsePosition) {
/*      */       DateTimeParseContext dateTimeParseContext;
/* 2136 */       Objects.requireNonNull(param1String, "text");
/*      */       
/*      */       try {
/* 2139 */         dateTimeParseContext = this.formatter.parseUnresolved0(param1String, param1ParsePosition);
/* 2140 */       } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 2141 */         if (param1ParsePosition.getErrorIndex() < 0) {
/* 2142 */           param1ParsePosition.setErrorIndex(0);
/*      */         }
/* 2144 */         return null;
/*      */       } 
/* 2146 */       if (dateTimeParseContext == null) {
/* 2147 */         if (param1ParsePosition.getErrorIndex() < 0) {
/* 2148 */           param1ParsePosition.setErrorIndex(0);
/*      */         }
/* 2150 */         return null;
/*      */       } 
/*      */       try {
/* 2153 */         TemporalAccessor temporalAccessor = dateTimeParseContext.toResolved(this.formatter.resolverStyle, this.formatter.resolverFields);
/* 2154 */         if (this.parseType == null) {
/* 2155 */           return temporalAccessor;
/*      */         }
/* 2157 */         return temporalAccessor.query(this.parseType);
/* 2158 */       } catch (RuntimeException runtimeException) {
/* 2159 */         param1ParsePosition.setErrorIndex(0);
/* 2160 */         return null;
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/format/DateTimeFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */