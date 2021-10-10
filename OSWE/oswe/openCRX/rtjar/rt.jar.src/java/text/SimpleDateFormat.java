/*      */ package java.text;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.TimeZone;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import sun.util.calendar.CalendarUtils;
/*      */ import sun.util.calendar.ZoneInfoFile;
/*      */ import sun.util.locale.provider.LocaleProviderAdapter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SimpleDateFormat
/*      */   extends DateFormat
/*      */ {
/*      */   static final long serialVersionUID = 4774881970558875024L;
/*      */   static final int currentSerialVersion = 1;
/*  446 */   private int serialVersionOnStream = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient NumberFormat originalNumberFormat;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient String originalNumberPattern;
/*      */ 
/*      */ 
/*      */   
/*  465 */   private transient char minusSign = '-';
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean hasFollowingMinusSign = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean forceStandaloneForm = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient char[] compiledPattern;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int TAG_QUOTE_ASCII_CHAR = 100;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int TAG_QUOTE_CHARS = 101;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient char zeroDigit;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DateFormatSymbols formatData;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Date defaultCenturyStart;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient int defaultCenturyStartYear;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MILLIS_PER_MINUTE = 60000;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String GMT = "GMT";
/*      */ 
/*      */ 
/*      */   
/*  524 */   private static final ConcurrentMap<Locale, NumberFormat> cachedNumberFormatData = new ConcurrentHashMap<>(3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Locale locale;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   transient boolean useDateFormatSymbols;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SimpleDateFormat() {
/*  556 */     this("", Locale.getDefault(Locale.Category.FORMAT));
/*  557 */     applyPatternImpl(LocaleProviderAdapter.getResourceBundleBased().getLocaleResources(this.locale)
/*  558 */         .getDateTimePattern(3, 3, this.calendar));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SimpleDateFormat(String paramString) {
/*  580 */     this(paramString, Locale.getDefault(Locale.Category.FORMAT));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SimpleDateFormat(String paramString, Locale paramLocale) {
/*  597 */     if (paramString == null || paramLocale == null) {
/*  598 */       throw new NullPointerException();
/*      */     }
/*      */     
/*  601 */     initializeCalendar(paramLocale);
/*  602 */     this.pattern = paramString;
/*  603 */     this.formatData = DateFormatSymbols.getInstanceRef(paramLocale);
/*  604 */     this.locale = paramLocale;
/*  605 */     initialize(paramLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SimpleDateFormat(String paramString, DateFormatSymbols paramDateFormatSymbols) {
/*  619 */     if (paramString == null || paramDateFormatSymbols == null) {
/*  620 */       throw new NullPointerException();
/*      */     }
/*      */     
/*  623 */     this.pattern = paramString;
/*  624 */     this.formatData = (DateFormatSymbols)paramDateFormatSymbols.clone();
/*  625 */     this.locale = Locale.getDefault(Locale.Category.FORMAT);
/*  626 */     initializeCalendar(this.locale);
/*  627 */     initialize(this.locale);
/*  628 */     this.useDateFormatSymbols = true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void initialize(Locale paramLocale) {
/*  634 */     this.compiledPattern = compile(this.pattern);
/*      */ 
/*      */     
/*  637 */     this.numberFormat = cachedNumberFormatData.get(paramLocale);
/*  638 */     if (this.numberFormat == null) {
/*  639 */       this.numberFormat = NumberFormat.getIntegerInstance(paramLocale);
/*  640 */       this.numberFormat.setGroupingUsed(false);
/*      */ 
/*      */       
/*  643 */       cachedNumberFormatData.putIfAbsent(paramLocale, this.numberFormat);
/*      */     } 
/*  645 */     this.numberFormat = (NumberFormat)this.numberFormat.clone();
/*      */     
/*  647 */     initializeDefaultCentury();
/*      */   }
/*      */   
/*      */   private void initializeCalendar(Locale paramLocale) {
/*  651 */     if (this.calendar == null) {
/*  652 */       assert paramLocale != null;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  657 */       this.calendar = Calendar.getInstance(TimeZone.getDefault(), paramLocale);
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
/*      */   private char[] compile(String paramString) {
/*  726 */     int i = paramString.length();
/*  727 */     boolean bool = false;
/*  728 */     StringBuilder stringBuilder1 = new StringBuilder(i * 2);
/*  729 */     StringBuilder stringBuilder2 = null;
/*  730 */     byte b1 = 0, b2 = 0;
/*  731 */     int j = -1, k = -1;
/*      */     int m;
/*  733 */     for (m = 0; m < i; m++) {
/*  734 */       char c = paramString.charAt(m);
/*      */       
/*  736 */       if (c == '\'') {
/*      */ 
/*      */         
/*  739 */         if (m + 1 < i) {
/*  740 */           c = paramString.charAt(m + 1);
/*  741 */           if (c == '\'') {
/*  742 */             m++;
/*  743 */             if (b1) {
/*  744 */               encode(j, b1, stringBuilder1);
/*  745 */               b2++;
/*  746 */               k = j;
/*  747 */               j = -1;
/*  748 */               b1 = 0;
/*      */             } 
/*  750 */             if (bool) {
/*  751 */               stringBuilder2.append(c);
/*      */             } else {
/*  753 */               stringBuilder1.append((char)(0x6400 | c));
/*      */             } 
/*      */             continue;
/*      */           } 
/*      */         } 
/*  758 */         if (!bool) {
/*  759 */           if (b1) {
/*  760 */             encode(j, b1, stringBuilder1);
/*  761 */             b2++;
/*  762 */             k = j;
/*  763 */             j = -1;
/*  764 */             b1 = 0;
/*      */           } 
/*  766 */           if (stringBuilder2 == null) {
/*  767 */             stringBuilder2 = new StringBuilder(i);
/*      */           } else {
/*  769 */             stringBuilder2.setLength(0);
/*      */           } 
/*  771 */           bool = true;
/*      */         } else {
/*  773 */           int n = stringBuilder2.length();
/*  774 */           if (n == 1) {
/*  775 */             char c1 = stringBuilder2.charAt(0);
/*  776 */             if (c1 < '') {
/*  777 */               stringBuilder1.append((char)(0x6400 | c1));
/*      */             } else {
/*  779 */               stringBuilder1.append('攁');
/*  780 */               stringBuilder1.append(c1);
/*      */             } 
/*      */           } else {
/*  783 */             encode(101, n, stringBuilder1);
/*  784 */             stringBuilder1.append(stringBuilder2);
/*      */           } 
/*  786 */           bool = false;
/*      */         } 
/*      */         continue;
/*      */       } 
/*  790 */       if (bool) {
/*  791 */         stringBuilder2.append(c);
/*      */       
/*      */       }
/*  794 */       else if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
/*  795 */         if (b1) {
/*  796 */           encode(j, b1, stringBuilder1);
/*  797 */           b2++;
/*  798 */           k = j;
/*  799 */           j = -1;
/*  800 */           b1 = 0;
/*      */         } 
/*  802 */         if (c < '') {
/*      */           
/*  804 */           stringBuilder1.append((char)(0x6400 | c));
/*      */         } else {
/*      */           int n;
/*      */ 
/*      */           
/*  809 */           for (n = m + 1; n < i; n++) {
/*  810 */             char c1 = paramString.charAt(n);
/*  811 */             if (c1 == '\'' || (c1 >= 'a' && c1 <= 'z') || (c1 >= 'A' && c1 <= 'Z')) {
/*      */               break;
/*      */             }
/*      */           } 
/*  815 */           stringBuilder1.append((char)(0x6500 | n - m));
/*  816 */           for (; m < n; m++) {
/*  817 */             stringBuilder1.append(paramString.charAt(m));
/*      */           }
/*  819 */           m--;
/*      */         } 
/*      */       } else {
/*      */         int n;
/*      */ 
/*      */         
/*  825 */         if ((n = "GyMdkHmsSEDFwWahKzZYuXL".indexOf(c)) == -1) {
/*  826 */           throw new IllegalArgumentException("Illegal pattern character '" + c + "'");
/*      */         }
/*      */         
/*  829 */         if (j == -1 || j == n) {
/*  830 */           j = n;
/*  831 */           b1++;
/*      */         } else {
/*      */           
/*  834 */           encode(j, b1, stringBuilder1);
/*  835 */           b2++;
/*  836 */           k = j;
/*  837 */           j = n;
/*  838 */           b1 = 1;
/*      */         } 
/*      */       }  continue;
/*  841 */     }  if (bool) {
/*  842 */       throw new IllegalArgumentException("Unterminated quote");
/*      */     }
/*      */     
/*  845 */     if (b1 != 0) {
/*  846 */       encode(j, b1, stringBuilder1);
/*  847 */       b2++;
/*  848 */       k = j;
/*      */     } 
/*      */     
/*  851 */     this.forceStandaloneForm = (b2 == 1 && k == 2);
/*      */ 
/*      */     
/*  854 */     m = stringBuilder1.length();
/*  855 */     char[] arrayOfChar = new char[m];
/*  856 */     stringBuilder1.getChars(0, m, arrayOfChar, 0);
/*  857 */     return arrayOfChar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void encode(int paramInt1, int paramInt2, StringBuilder paramStringBuilder) {
/*  864 */     if (paramInt1 == 21 && paramInt2 >= 4) {
/*  865 */       throw new IllegalArgumentException("invalid ISO 8601 format: length=" + paramInt2);
/*      */     }
/*  867 */     if (paramInt2 < 255) {
/*  868 */       paramStringBuilder.append((char)(paramInt1 << 8 | paramInt2));
/*      */     } else {
/*  870 */       paramStringBuilder.append((char)(paramInt1 << 8 | 0xFF));
/*  871 */       paramStringBuilder.append((char)(paramInt2 >>> 16));
/*  872 */       paramStringBuilder.append((char)(paramInt2 & 0xFFFF));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initializeDefaultCentury() {
/*  880 */     this.calendar.setTimeInMillis(System.currentTimeMillis());
/*  881 */     this.calendar.add(1, -80);
/*  882 */     parseAmbiguousDatesAsAfter(this.calendar.getTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseAmbiguousDatesAsAfter(Date paramDate) {
/*  889 */     this.defaultCenturyStart = paramDate;
/*  890 */     this.calendar.setTime(paramDate);
/*  891 */     this.defaultCenturyStartYear = this.calendar.get(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set2DigitYearStart(Date paramDate) {
/*  904 */     parseAmbiguousDatesAsAfter(new Date(paramDate.getTime()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date get2DigitYearStart() {
/*  917 */     return (Date)this.defaultCenturyStart.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuffer format(Date paramDate, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition) {
/*  935 */     paramFieldPosition.beginIndex = paramFieldPosition.endIndex = 0;
/*  936 */     return format(paramDate, paramStringBuffer, paramFieldPosition.getFieldDelegate());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StringBuffer format(Date paramDate, StringBuffer paramStringBuffer, Format.FieldDelegate paramFieldDelegate) {
/*  943 */     this.calendar.setTime(paramDate);
/*      */     
/*  945 */     boolean bool = useDateFormatSymbols();
/*      */     
/*  947 */     for (int i = 0; i < this.compiledPattern.length; ) {
/*  948 */       int j = this.compiledPattern[i] >>> 8;
/*  949 */       int k = this.compiledPattern[i++] & 0xFF;
/*  950 */       if (k == 255) {
/*  951 */         k = this.compiledPattern[i++] << 16;
/*  952 */         k |= this.compiledPattern[i++];
/*      */       } 
/*      */       
/*  955 */       switch (j) {
/*      */         case 100:
/*  957 */           paramStringBuffer.append((char)k);
/*      */           continue;
/*      */         
/*      */         case 101:
/*  961 */           paramStringBuffer.append(this.compiledPattern, i, k);
/*  962 */           i += k;
/*      */           continue;
/*      */       } 
/*      */       
/*  966 */       subFormat(j, k, paramFieldDelegate, paramStringBuffer, bool);
/*      */     } 
/*      */ 
/*      */     
/*  970 */     return paramStringBuffer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributedCharacterIterator formatToCharacterIterator(Object paramObject) {
/*  992 */     StringBuffer stringBuffer = new StringBuffer();
/*  993 */     CharacterIteratorFieldDelegate characterIteratorFieldDelegate = new CharacterIteratorFieldDelegate();
/*      */ 
/*      */     
/*  996 */     if (paramObject instanceof Date) {
/*  997 */       format((Date)paramObject, stringBuffer, characterIteratorFieldDelegate);
/*      */     }
/*  999 */     else if (paramObject instanceof Number) {
/* 1000 */       format(new Date(((Number)paramObject).longValue()), stringBuffer, characterIteratorFieldDelegate);
/*      */     } else {
/* 1002 */       if (paramObject == null) {
/* 1003 */         throw new NullPointerException("formatToCharacterIterator must be passed non-null object");
/*      */       }
/*      */ 
/*      */       
/* 1007 */       throw new IllegalArgumentException("Cannot format given Object as a Date");
/*      */     } 
/*      */     
/* 1010 */     return characterIteratorFieldDelegate.getIterator(stringBuffer.toString());
/*      */   }
/*      */ 
/*      */   
/* 1014 */   private static final int[] PATTERN_INDEX_TO_CALENDAR_FIELD = new int[] { 0, 1, 2, 5, 11, 11, 12, 13, 14, 7, 6, 8, 3, 4, 9, 10, 10, 15, 15, 17, 1000, 15, 2 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1041 */   private static final int[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 17, 1, 9, 17, 2 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1068 */   private static final DateFormat.Field[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD_ID = new DateFormat.Field[] { DateFormat.Field.ERA, DateFormat.Field.YEAR, DateFormat.Field.MONTH, DateFormat.Field.DAY_OF_MONTH, DateFormat.Field.HOUR_OF_DAY1, DateFormat.Field.HOUR_OF_DAY0, DateFormat.Field.MINUTE, DateFormat.Field.SECOND, DateFormat.Field.MILLISECOND, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.DAY_OF_YEAR, DateFormat.Field.DAY_OF_WEEK_IN_MONTH, DateFormat.Field.WEEK_OF_YEAR, DateFormat.Field.WEEK_OF_MONTH, DateFormat.Field.AM_PM, DateFormat.Field.HOUR1, DateFormat.Field.HOUR0, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.YEAR, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.TIME_ZONE, DateFormat.Field.MONTH };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void subFormat(int paramInt1, int paramInt2, Format.FieldDelegate paramFieldDelegate, StringBuffer paramStringBuffer, boolean paramBoolean) {
/* 1101 */     int m, i1, i = Integer.MAX_VALUE;
/* 1102 */     String str = null;
/* 1103 */     int j = paramStringBuffer.length();
/*      */     
/* 1105 */     int k = PATTERN_INDEX_TO_CALENDAR_FIELD[paramInt1];
/*      */     
/* 1107 */     if (k == 17) {
/* 1108 */       if (this.calendar.isWeekDateSupported()) {
/* 1109 */         m = this.calendar.getWeekYear();
/*      */       } else {
/*      */         
/* 1112 */         paramInt1 = 1;
/* 1113 */         k = PATTERN_INDEX_TO_CALENDAR_FIELD[paramInt1];
/* 1114 */         m = this.calendar.get(k);
/*      */       } 
/* 1116 */     } else if (k == 1000) {
/* 1117 */       m = CalendarBuilder.toISODayOfWeek(this.calendar.get(7));
/*      */     } else {
/* 1119 */       m = this.calendar.get(k);
/*      */     } 
/*      */     
/* 1122 */     boolean bool = (paramInt2 >= 4) ? true : true;
/* 1123 */     if (!paramBoolean && k < 15 && paramInt1 != 22)
/*      */     {
/* 1125 */       str = this.calendar.getDisplayName(k, bool, this.locale);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1132 */     switch (paramInt1) {
/*      */       case 0:
/* 1134 */         if (paramBoolean) {
/* 1135 */           String[] arrayOfString = this.formatData.getEras();
/* 1136 */           if (m < arrayOfString.length) {
/* 1137 */             str = arrayOfString[m];
/*      */           }
/*      */         } 
/* 1140 */         if (str == null) {
/* 1141 */           str = "";
/*      */         }
/*      */         break;
/*      */       
/*      */       case 1:
/*      */       case 19:
/* 1147 */         if (this.calendar instanceof java.util.GregorianCalendar) {
/* 1148 */           if (paramInt2 != 2) {
/* 1149 */             zeroPaddingNumber(m, paramInt2, i, paramStringBuffer); break;
/*      */           } 
/* 1151 */           zeroPaddingNumber(m, 2, 2, paramStringBuffer);
/*      */           break;
/*      */         } 
/* 1154 */         if (str == null) {
/* 1155 */           zeroPaddingNumber(m, (bool == 2) ? 1 : paramInt2, i, paramStringBuffer);
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 1162 */         if (paramBoolean) {
/*      */           
/* 1164 */           if (paramInt2 >= 4) {
/* 1165 */             String[] arrayOfString = this.formatData.getMonths();
/* 1166 */             str = arrayOfString[m];
/* 1167 */           } else if (paramInt2 == 3) {
/* 1168 */             String[] arrayOfString = this.formatData.getShortMonths();
/* 1169 */             str = arrayOfString[m];
/*      */           }
/*      */         
/* 1172 */         } else if (paramInt2 < 3) {
/* 1173 */           str = null;
/* 1174 */         } else if (this.forceStandaloneForm) {
/* 1175 */           str = this.calendar.getDisplayName(k, bool | 0x8000, this.locale);
/* 1176 */           if (str == null) {
/* 1177 */             str = this.calendar.getDisplayName(k, bool, this.locale);
/*      */           }
/*      */         } 
/*      */         
/* 1181 */         if (str == null) {
/* 1182 */           zeroPaddingNumber(m + 1, paramInt2, i, paramStringBuffer);
/*      */         }
/*      */         break;
/*      */       
/*      */       case 22:
/* 1187 */         assert str == null;
/* 1188 */         if (this.locale == null) {
/*      */           
/* 1190 */           if (paramInt2 >= 4) {
/* 1191 */             String[] arrayOfString = this.formatData.getMonths();
/* 1192 */             str = arrayOfString[m];
/* 1193 */           } else if (paramInt2 == 3) {
/* 1194 */             String[] arrayOfString = this.formatData.getShortMonths();
/* 1195 */             str = arrayOfString[m];
/*      */           }
/*      */         
/* 1198 */         } else if (paramInt2 >= 3) {
/* 1199 */           str = this.calendar.getDisplayName(k, bool | 0x8000, this.locale);
/*      */         } 
/*      */         
/* 1202 */         if (str == null) {
/* 1203 */           zeroPaddingNumber(m + 1, paramInt2, i, paramStringBuffer);
/*      */         }
/*      */         break;
/*      */       
/*      */       case 4:
/* 1208 */         if (str == null) {
/* 1209 */           if (m == 0) {
/* 1210 */             zeroPaddingNumber(this.calendar.getMaximum(11) + 1, paramInt2, i, paramStringBuffer);
/*      */             break;
/*      */           } 
/* 1213 */           zeroPaddingNumber(m, paramInt2, i, paramStringBuffer);
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 9:
/* 1219 */         if (paramBoolean) {
/*      */           
/* 1221 */           if (paramInt2 >= 4) {
/* 1222 */             String[] arrayOfString1 = this.formatData.getWeekdays();
/* 1223 */             str = arrayOfString1[m]; break;
/*      */           } 
/* 1225 */           String[] arrayOfString = this.formatData.getShortWeekdays();
/* 1226 */           str = arrayOfString[m];
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 14:
/* 1232 */         if (paramBoolean) {
/* 1233 */           String[] arrayOfString = this.formatData.getAmPmStrings();
/* 1234 */           str = arrayOfString[m];
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 15:
/* 1239 */         if (str == null) {
/* 1240 */           if (m == 0) {
/* 1241 */             zeroPaddingNumber(this.calendar.getLeastMaximum(10) + 1, paramInt2, i, paramStringBuffer);
/*      */             break;
/*      */           } 
/* 1244 */           zeroPaddingNumber(m, paramInt2, i, paramStringBuffer);
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 17:
/* 1250 */         if (str == null) {
/* 1251 */           if (this.formatData.locale == null || this.formatData.isZoneStringsSet) {
/*      */             
/* 1253 */             int i2 = this.formatData.getZoneIndex(this.calendar.getTimeZone().getID());
/* 1254 */             if (i2 == -1) {
/*      */               
/* 1256 */               m = this.calendar.get(15) + this.calendar.get(16);
/* 1257 */               paramStringBuffer.append(ZoneInfoFile.toCustomID(m)); break;
/*      */             } 
/* 1259 */             byte b = (this.calendar.get(16) == 0) ? 1 : 3;
/* 1260 */             if (paramInt2 < 4)
/*      */             {
/* 1262 */               b++;
/*      */             }
/* 1264 */             String[][] arrayOfString = this.formatData.getZoneStringsWrapper();
/* 1265 */             paramStringBuffer.append(arrayOfString[i2][b]);
/*      */             break;
/*      */           } 
/* 1268 */           TimeZone timeZone = this.calendar.getTimeZone();
/* 1269 */           boolean bool1 = (this.calendar.get(16) != 0) ? true : false;
/* 1270 */           boolean bool2 = (paramInt2 < 4) ? false : true;
/* 1271 */           paramStringBuffer.append(timeZone.getDisplayName(bool1, bool2, this.formatData.locale));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 18:
/* 1278 */         m = (this.calendar.get(15) + this.calendar.get(16)) / 60000;
/*      */         
/* 1280 */         n = 4;
/* 1281 */         if (m >= 0) {
/* 1282 */           paramStringBuffer.append('+');
/*      */         } else {
/* 1284 */           n++;
/*      */         } 
/*      */         
/* 1287 */         i1 = m / 60 * 100 + m % 60;
/* 1288 */         CalendarUtils.sprintf0d(paramStringBuffer, i1, n);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 21:
/* 1293 */         m = this.calendar.get(15) + this.calendar.get(16);
/*      */         
/* 1295 */         if (m == 0) {
/* 1296 */           paramStringBuffer.append('Z');
/*      */           
/*      */           break;
/*      */         } 
/* 1300 */         m /= 60000;
/* 1301 */         if (m >= 0) {
/* 1302 */           paramStringBuffer.append('+');
/*      */         } else {
/* 1304 */           paramStringBuffer.append('-');
/* 1305 */           m = -m;
/*      */         } 
/*      */         
/* 1308 */         CalendarUtils.sprintf0d(paramStringBuffer, m / 60, 2);
/* 1309 */         if (paramInt2 == 1) {
/*      */           break;
/*      */         }
/*      */         
/* 1313 */         if (paramInt2 == 3) {
/* 1314 */           paramStringBuffer.append(':');
/*      */         }
/* 1316 */         CalendarUtils.sprintf0d(paramStringBuffer, m % 60, 2);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       default:
/* 1331 */         if (str == null) {
/* 1332 */           zeroPaddingNumber(m, paramInt2, i, paramStringBuffer);
/*      */         }
/*      */         break;
/*      */     } 
/*      */     
/* 1337 */     if (str != null) {
/* 1338 */       paramStringBuffer.append(str);
/*      */     }
/*      */     
/* 1341 */     int n = PATTERN_INDEX_TO_DATE_FORMAT_FIELD[paramInt1];
/* 1342 */     DateFormat.Field field = PATTERN_INDEX_TO_DATE_FORMAT_FIELD_ID[paramInt1];
/*      */     
/* 1344 */     paramFieldDelegate.formatted(n, field, field, j, paramStringBuffer.length(), paramStringBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void zeroPaddingNumber(int paramInt1, int paramInt2, int paramInt3, StringBuffer paramStringBuffer) {
/*      */     try {
/* 1357 */       if (this.zeroDigit == '\000') {
/* 1358 */         this.zeroDigit = ((DecimalFormat)this.numberFormat).getDecimalFormatSymbols().getZeroDigit();
/*      */       }
/* 1360 */       if (paramInt1 >= 0) {
/* 1361 */         if (paramInt1 < 100 && paramInt2 >= 1 && paramInt2 <= 2) {
/* 1362 */           if (paramInt1 < 10) {
/* 1363 */             if (paramInt2 == 2) {
/* 1364 */               paramStringBuffer.append(this.zeroDigit);
/*      */             }
/* 1366 */             paramStringBuffer.append((char)(this.zeroDigit + paramInt1));
/*      */           } else {
/* 1368 */             paramStringBuffer.append((char)(this.zeroDigit + paramInt1 / 10));
/* 1369 */             paramStringBuffer.append((char)(this.zeroDigit + paramInt1 % 10));
/*      */           }  return;
/*      */         } 
/* 1372 */         if (paramInt1 >= 1000 && paramInt1 < 10000) {
/* 1373 */           if (paramInt2 == 4) {
/* 1374 */             paramStringBuffer.append((char)(this.zeroDigit + paramInt1 / 1000));
/* 1375 */             paramInt1 %= 1000;
/* 1376 */             paramStringBuffer.append((char)(this.zeroDigit + paramInt1 / 100));
/* 1377 */             paramInt1 %= 100;
/* 1378 */             paramStringBuffer.append((char)(this.zeroDigit + paramInt1 / 10));
/* 1379 */             paramStringBuffer.append((char)(this.zeroDigit + paramInt1 % 10));
/*      */             return;
/*      */           } 
/* 1382 */           if (paramInt2 == 2 && paramInt3 == 2) {
/* 1383 */             zeroPaddingNumber(paramInt1 % 100, 2, 2, paramStringBuffer);
/*      */             return;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1388 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/* 1391 */     this.numberFormat.setMinimumIntegerDigits(paramInt2);
/* 1392 */     this.numberFormat.setMaximumIntegerDigits(paramInt3);
/* 1393 */     this.numberFormat.format(paramInt1, paramStringBuffer, DontCareFieldPosition.INSTANCE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date parse(String paramString, ParsePosition paramParsePosition) {
/*      */     Date date;
/* 1435 */     checkNegativeNumberExpression();
/*      */     
/* 1437 */     int i = paramParsePosition.index;
/* 1438 */     int j = i;
/* 1439 */     int k = paramString.length();
/*      */     
/* 1441 */     boolean[] arrayOfBoolean = { false };
/*      */     
/* 1443 */     CalendarBuilder calendarBuilder = new CalendarBuilder();
/*      */     
/* 1445 */     for (byte b = 0; b < this.compiledPattern.length; ) {
/* 1446 */       int m = this.compiledPattern[b] >>> 8;
/* 1447 */       int n = this.compiledPattern[b++] & 0xFF;
/* 1448 */       if (n == 255) {
/* 1449 */         n = this.compiledPattern[b++] << 16;
/* 1450 */         n |= this.compiledPattern[b++];
/*      */       } 
/*      */       
/* 1453 */       switch (m) {
/*      */         case 100:
/* 1455 */           if (i >= k || paramString.charAt(i) != (char)n) {
/* 1456 */             paramParsePosition.index = j;
/* 1457 */             paramParsePosition.errorIndex = i;
/* 1458 */             return null;
/*      */           } 
/* 1460 */           i++;
/*      */           continue;
/*      */         
/*      */         case 101:
/* 1464 */           while (n-- > 0) {
/* 1465 */             if (i >= k || paramString.charAt(i) != this.compiledPattern[b++]) {
/* 1466 */               paramParsePosition.index = j;
/* 1467 */               paramParsePosition.errorIndex = i;
/* 1468 */               return null;
/*      */             } 
/* 1470 */             i++;
/*      */           } 
/*      */           continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1480 */       boolean bool1 = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1490 */       boolean bool2 = false;
/*      */       
/* 1492 */       if (b < this.compiledPattern.length) {
/* 1493 */         int i1 = this.compiledPattern[b] >>> 8;
/* 1494 */         if (i1 != 100 && i1 != 101)
/*      */         {
/* 1496 */           bool1 = true;
/*      */         }
/*      */         
/* 1499 */         if (this.hasFollowingMinusSign && (i1 == 100 || i1 == 101)) {
/*      */           int i2;
/*      */ 
/*      */           
/* 1503 */           if (i1 == 100) {
/* 1504 */             i2 = this.compiledPattern[b] & 0xFF;
/*      */           } else {
/* 1506 */             i2 = this.compiledPattern[b + 1];
/*      */           } 
/*      */           
/* 1509 */           if (i2 == this.minusSign) {
/* 1510 */             bool2 = true;
/*      */           }
/*      */         } 
/*      */       } 
/* 1514 */       i = subParse(paramString, i, m, n, bool1, arrayOfBoolean, paramParsePosition, bool2, calendarBuilder);
/*      */ 
/*      */       
/* 1517 */       if (i < 0) {
/* 1518 */         paramParsePosition.index = j;
/* 1519 */         return null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1528 */     paramParsePosition.index = i;
/*      */ 
/*      */     
/*      */     try {
/* 1532 */       date = calendarBuilder.establish(this.calendar).getTime();
/*      */ 
/*      */       
/* 1535 */       if (arrayOfBoolean[0] && 
/* 1536 */         date.before(this.defaultCenturyStart)) {
/* 1537 */         date = calendarBuilder.addYear(100).establish(this.calendar).getTime();
/*      */ 
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1543 */     catch (IllegalArgumentException illegalArgumentException) {
/* 1544 */       paramParsePosition.errorIndex = i;
/* 1545 */       paramParsePosition.index = j;
/* 1546 */       return null;
/*      */     } 
/*      */     
/* 1549 */     return date;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int matchString(String paramString, int paramInt1, int paramInt2, String[] paramArrayOfString, CalendarBuilder paramCalendarBuilder) {
/* 1563 */     byte b = 0;
/* 1564 */     int i = paramArrayOfString.length;
/*      */     
/* 1566 */     if (paramInt2 == 7) {
/* 1567 */       b = 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1574 */     int j = 0; byte b1 = -1;
/* 1575 */     for (; b < i; b++) {
/*      */       
/* 1577 */       int k = paramArrayOfString[b].length();
/*      */ 
/*      */       
/* 1580 */       if (k > j && paramString
/* 1581 */         .regionMatches(true, paramInt1, paramArrayOfString[b], 0, k)) {
/*      */         
/* 1583 */         b1 = b;
/* 1584 */         j = k;
/*      */       } 
/*      */     } 
/* 1587 */     if (b1 >= 0) {
/*      */       
/* 1589 */       paramCalendarBuilder.set(paramInt2, b1);
/* 1590 */       return paramInt1 + j;
/*      */     } 
/* 1592 */     return -paramInt1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int matchString(String paramString, int paramInt1, int paramInt2, Map<String, Integer> paramMap, CalendarBuilder paramCalendarBuilder) {
/* 1602 */     if (paramMap != null) {
/*      */       
/* 1604 */       if (paramMap instanceof java.util.SortedMap) {
/* 1605 */         for (String str1 : paramMap.keySet()) {
/* 1606 */           if (paramString.regionMatches(true, paramInt1, str1, 0, str1.length())) {
/* 1607 */             paramCalendarBuilder.set(paramInt2, ((Integer)paramMap.get(str1)).intValue());
/* 1608 */             return paramInt1 + str1.length();
/*      */           } 
/*      */         } 
/* 1611 */         return -paramInt1;
/*      */       } 
/*      */       
/* 1614 */       String str = null;
/*      */       
/* 1616 */       for (String str1 : paramMap.keySet()) {
/* 1617 */         int i = str1.length();
/* 1618 */         if ((str == null || i > str.length()) && 
/* 1619 */           paramString.regionMatches(true, paramInt1, str1, 0, i)) {
/* 1620 */           str = str1;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1625 */       if (str != null) {
/* 1626 */         paramCalendarBuilder.set(paramInt2, ((Integer)paramMap.get(str)).intValue());
/* 1627 */         return paramInt1 + str.length();
/*      */       } 
/*      */     } 
/* 1630 */     return -paramInt1;
/*      */   }
/*      */   
/*      */   private int matchZoneString(String paramString, int paramInt, String[] paramArrayOfString) {
/* 1634 */     for (byte b = 1; b <= 4; b++) {
/*      */ 
/*      */       
/* 1637 */       String str = paramArrayOfString[b];
/* 1638 */       if (paramString.regionMatches(true, paramInt, str, 0, str
/* 1639 */           .length())) {
/* 1640 */         return b;
/*      */       }
/*      */     } 
/* 1643 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchDSTString(String paramString, int paramInt1, int paramInt2, int paramInt3, String[][] paramArrayOfString) {
/* 1648 */     int i = paramInt3 + 2;
/* 1649 */     String str = paramArrayOfString[paramInt2][i];
/* 1650 */     if (paramString.regionMatches(true, paramInt1, str, 0, str
/* 1651 */         .length())) {
/* 1652 */       return true;
/*      */     }
/* 1654 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int subParseZoneString(String paramString, int paramInt, CalendarBuilder paramCalendarBuilder) {
/* 1662 */     boolean bool = false;
/* 1663 */     TimeZone timeZone1 = getTimeZone();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1668 */     int i = this.formatData.getZoneIndex(timeZone1.getID());
/* 1669 */     TimeZone timeZone2 = null;
/* 1670 */     String[][] arrayOfString = this.formatData.getZoneStringsWrapper();
/* 1671 */     String[] arrayOfString1 = null;
/* 1672 */     int j = 0;
/*      */     
/* 1674 */     arrayOfString1 = arrayOfString[i];
/* 1675 */     if (i != -1 && (j = matchZoneString(paramString, paramInt, arrayOfString1)) > 0) {
/* 1676 */       if (j <= 2)
/*      */       {
/* 1678 */         bool = arrayOfString1[j].equalsIgnoreCase(arrayOfString1[j + 2]);
/*      */       }
/* 1680 */       timeZone2 = TimeZone.getTimeZone(arrayOfString1[0]);
/*      */     } 
/*      */     
/* 1683 */     if (timeZone2 == null) {
/* 1684 */       i = this.formatData.getZoneIndex(TimeZone.getDefault().getID());
/*      */       
/* 1686 */       arrayOfString1 = arrayOfString[i];
/* 1687 */       if (i != -1 && (j = matchZoneString(paramString, paramInt, arrayOfString1)) > 0) {
/* 1688 */         if (j <= 2) {
/* 1689 */           bool = arrayOfString1[j].equalsIgnoreCase(arrayOfString1[j + 2]);
/*      */         }
/* 1691 */         timeZone2 = TimeZone.getTimeZone(arrayOfString1[0]);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1696 */     if (timeZone2 == null) {
/* 1697 */       int k = arrayOfString.length;
/* 1698 */       for (byte b = 0; b < k; b++) {
/* 1699 */         arrayOfString1 = arrayOfString[b];
/* 1700 */         if ((j = matchZoneString(paramString, paramInt, arrayOfString1)) > 0) {
/* 1701 */           if (j <= 2) {
/* 1702 */             bool = arrayOfString1[j].equalsIgnoreCase(arrayOfString1[j + 2]);
/*      */           }
/* 1704 */           timeZone2 = TimeZone.getTimeZone(arrayOfString1[0]);
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1709 */     if (timeZone2 != null) {
/* 1710 */       if (!timeZone2.equals(timeZone1)) {
/* 1711 */         setTimeZone(timeZone2);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1719 */       boolean bool1 = (j >= 3) ? timeZone2.getDSTSavings() : false;
/* 1720 */       if (!bool && (j < 3 || bool1)) {
/* 1721 */         paramCalendarBuilder.clear(15).set(16, bool1);
/*      */       }
/* 1723 */       return paramInt + arrayOfString1[j].length();
/*      */     } 
/* 1725 */     return -paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int subParseNumericZone(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, CalendarBuilder paramCalendarBuilder) {
/* 1742 */     int i = paramInt1;
/*      */ 
/*      */ 
/*      */     
/* 1746 */     try { char c = paramString.charAt(i++);
/*      */ 
/*      */       
/* 1749 */       if (isDigit(c))
/*      */       
/*      */       { 
/* 1752 */         int j = c - 48;
/* 1753 */         c = paramString.charAt(i++);
/* 1754 */         if (isDigit(c))
/* 1755 */         { j = j * 10 + c - 48; }
/*      */         
/*      */         else
/*      */         
/* 1759 */         { if (paramInt3 > 0 || !paramBoolean)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1796 */             return 1 - i; }  i--; }  if (j <= 23) { int k = 0; if (paramInt3 != 1) { c = paramString.charAt(i++); if (paramBoolean) { if (c != ':') return 1 - i;  c = paramString.charAt(i++); }  if (!isDigit(c)) return 1 - i;  k = c - 48; c = paramString.charAt(i++); if (!isDigit(c)) return 1 - i;  k = k * 10 + c - 48; if (k > 59) return 1 - i;  }  k += j * 60; paramCalendarBuilder.set(15, k * 60000 * paramInt2).set(16, 0); return i; }  }  } catch (IndexOutOfBoundsException indexOutOfBoundsException) {} return 1 - i;
/*      */   }
/*      */   
/*      */   private boolean isDigit(char paramChar) {
/* 1800 */     return (paramChar >= '0' && paramChar <= '9');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int subParse(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean[] paramArrayOfboolean, ParsePosition paramParsePosition, boolean paramBoolean2, CalendarBuilder paramCalendarBuilder) {
/*      */     Number number;
/*      */     byte b;
/* 1825 */     int i = 0;
/* 1826 */     ParsePosition parsePosition = new ParsePosition(0);
/* 1827 */     parsePosition.index = paramInt1;
/* 1828 */     if (paramInt2 == 19 && !this.calendar.isWeekDateSupported())
/*      */     {
/* 1830 */       paramInt2 = 1;
/*      */     }
/* 1832 */     int j = PATTERN_INDEX_TO_CALENDAR_FIELD[paramInt2];
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 1837 */       if (parsePosition.index >= paramString.length()) {
/* 1838 */         paramParsePosition.errorIndex = paramInt1;
/* 1839 */         return -1;
/*      */       } 
/* 1841 */       char c = paramString.charAt(parsePosition.index);
/* 1842 */       if (c != ' ' && c != '\t') {
/*      */         break;
/*      */       }
/* 1845 */       parsePosition.index++;
/*      */     } 
/*      */     
/* 1848 */     int k = parsePosition.index;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1856 */     if (paramInt2 == 4 || paramInt2 == 15 || (paramInt2 == 2 && paramInt3 <= 2) || paramInt2 == 1 || paramInt2 == 19)
/*      */     
/*      */     { 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1863 */       if (paramBoolean1)
/* 1864 */       { if (paramInt1 + paramInt3 > paramString.length())
/*      */         
/*      */         { 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2184 */           paramParsePosition.errorIndex = parsePosition.index;
/* 2185 */           return -1; }  number = this.numberFormat.parse(paramString.substring(0, paramInt1 + paramInt3), parsePosition); } else { number = this.numberFormat.parse(paramString, parsePosition); }  if (number == null) { if (paramInt2 != 1 || this.calendar instanceof java.util.GregorianCalendar) { paramParsePosition.errorIndex = parsePosition.index; return -1; }  } else { i = number.intValue(); if (paramBoolean2 && i < 0 && ((parsePosition.index < paramString.length() && paramString.charAt(parsePosition.index) != this.minusSign) || (parsePosition.index == paramString.length() && paramString.charAt(parsePosition.index - 1) == this.minusSign))) { i = -i; parsePosition.index--; }  }  }  boolean bool = useDateFormatSymbols(); switch (paramInt2) { case 0: if (bool) { int m; if ((m = matchString(paramString, paramInt1, 0, this.formatData.getEras(), paramCalendarBuilder)) > 0) return m;  } else { Map<String, Integer> map = getDisplayNamesMap(j, this.locale); int m; if ((m = matchString(paramString, paramInt1, j, map, paramCalendarBuilder)) > 0) return m;  }  paramParsePosition.errorIndex = parsePosition.index; return -1;case 1: case 19: if (!(this.calendar instanceof java.util.GregorianCalendar)) { boolean bool1 = (paramInt3 >= 4) ? true : true; Map<String, Integer> map = this.calendar.getDisplayNames(j, bool1, this.locale); int m; if (map != null && (m = matchString(paramString, paramInt1, j, map, paramCalendarBuilder)) > 0) return m;  paramCalendarBuilder.set(j, i); return parsePosition.index; }  if (paramInt3 <= 2 && parsePosition.index - k == 2 && Character.isDigit(paramString.charAt(k)) && Character.isDigit(paramString.charAt(k + 1))) { int m = this.defaultCenturyStartYear % 100; paramArrayOfboolean[0] = (i == m); i += this.defaultCenturyStartYear / 100 * 100 + ((i < m) ? 100 : 0); }  paramCalendarBuilder.set(j, i); return parsePosition.index;case 2: if (paramInt3 <= 2) { paramCalendarBuilder.set(2, i - 1); return parsePosition.index; }  if (bool) { int n; if ((n = matchString(paramString, paramInt1, 2, this.formatData.getMonths(), paramCalendarBuilder)) > 0) return n;  int m; if ((m = matchString(paramString, paramInt1, 2, this.formatData.getShortMonths(), paramCalendarBuilder)) > 0) return m;  } else { Map<String, Integer> map = getDisplayNamesMap(j, this.locale); int m; if ((m = matchString(paramString, paramInt1, j, map, paramCalendarBuilder)) > 0) return m;  }  paramParsePosition.errorIndex = parsePosition.index; return -1;case 4: if (!isLenient()) if (i < 1 || i > 24) { paramParsePosition.errorIndex = parsePosition.index; return -1; }   if (i == this.calendar.getMaximum(11) + 1) i = 0;  paramCalendarBuilder.set(11, i); return parsePosition.index;case 9: if (bool) { int n; if ((n = matchString(paramString, paramInt1, 7, this.formatData.getWeekdays(), paramCalendarBuilder)) > 0) return n;  int m; if ((m = matchString(paramString, paramInt1, 7, this.formatData.getShortWeekdays(), paramCalendarBuilder)) > 0) return m;  } else { int[] arrayOfInt = { 2, 1 }; for (int n : arrayOfInt) { Map<String, Integer> map = this.calendar.getDisplayNames(j, n, this.locale); int m; if ((m = matchString(paramString, paramInt1, j, map, paramCalendarBuilder)) > 0) return m;  }  }  paramParsePosition.errorIndex = parsePosition.index; return -1;case 14: if (bool) { int m; if ((m = matchString(paramString, paramInt1, 9, this.formatData.getAmPmStrings(), paramCalendarBuilder)) > 0) return m;  } else { Map<String, Integer> map = getDisplayNamesMap(j, this.locale); int m; if ((m = matchString(paramString, paramInt1, j, map, paramCalendarBuilder)) > 0) return m;  }  paramParsePosition.errorIndex = parsePosition.index; return -1;case 15: if (!isLenient()) if (i < 1 || i > 12) { paramParsePosition.errorIndex = parsePosition.index; return -1; }   if (i == this.calendar.getLeastMaximum(10) + 1) i = 0;  paramCalendarBuilder.set(10, i); return parsePosition.index;case 17: case 18: b = 0; try { char c = paramString.charAt(parsePosition.index); if (c == '+') { b = 1; } else if (c == '-') { b = -1; }  if (b == 0) { if ((c == 'G' || c == 'g') && paramString.length() - paramInt1 >= "GMT".length() && paramString.regionMatches(true, paramInt1, "GMT", 0, "GMT".length())) { parsePosition.index = paramInt1 + "GMT".length(); if (paramString.length() - parsePosition.index > 0) { c = paramString.charAt(parsePosition.index); if (c == '+') { b = 1; } else if (c == '-') { b = -1; }  }  if (b == 0) { paramCalendarBuilder.set(15, 0).set(16, 0); return parsePosition.index; }  int m = subParseNumericZone(paramString, ++parsePosition.index, b, 0, true, paramCalendarBuilder); if (m > 0) return m;  parsePosition.index = -m; } else { int m = subParseZoneString(paramString, parsePosition.index, paramCalendarBuilder); if (m > 0) return m;  parsePosition.index = -m; }  } else { int m = subParseNumericZone(paramString, ++parsePosition.index, b, 0, false, paramCalendarBuilder); if (m > 0) return m;  parsePosition.index = -m; }  } catch (IndexOutOfBoundsException indexOutOfBoundsException) {} paramParsePosition.errorIndex = parsePosition.index; return -1;case 21: if (paramString.length() - parsePosition.index > 0) { char c = paramString.charAt(parsePosition.index); if (c == 'Z') { paramCalendarBuilder.set(15, 0).set(16, 0); return ++parsePosition.index; }  if (c == '+') { b = 1; } else if (c == '-') { b = -1; } else { paramParsePosition.errorIndex = ++parsePosition.index; return -1; }  int m = subParseNumericZone(paramString, ++parsePosition.index, b, paramInt3, (paramInt3 == 3), paramCalendarBuilder); if (m > 0) return m;  parsePosition.index = -m; }  paramParsePosition.errorIndex = parsePosition.index; return -1; }  if (paramBoolean1) { if (paramInt1 + paramInt3 > paramString.length()) { paramParsePosition.errorIndex = parsePosition.index; return -1; }  number = this.numberFormat.parse(paramString.substring(0, paramInt1 + paramInt3), parsePosition); } else { number = this.numberFormat.parse(paramString, parsePosition); }  if (number != null) { i = number.intValue(); if (paramBoolean2 && i < 0 && ((parsePosition.index < paramString.length() && paramString.charAt(parsePosition.index) != this.minusSign) || (parsePosition.index == paramString.length() && paramString.charAt(parsePosition.index - 1) == this.minusSign))) { i = -i; parsePosition.index--; }  paramCalendarBuilder.set(j, i); return parsePosition.index; }  paramParsePosition.errorIndex = parsePosition.index; return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useDateFormatSymbols() {
/* 2193 */     return (this.useDateFormatSymbols || this.locale == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String translatePattern(String paramString1, String paramString2, String paramString3) {
/* 2203 */     StringBuilder stringBuilder = new StringBuilder();
/* 2204 */     boolean bool = false;
/* 2205 */     for (byte b = 0; b < paramString1.length(); b++) {
/* 2206 */       char c = paramString1.charAt(b);
/* 2207 */       if (bool) {
/* 2208 */         if (c == '\'') {
/* 2209 */           bool = false;
/*      */         
/*      */         }
/*      */       }
/* 2213 */       else if (c == '\'') {
/* 2214 */         bool = true;
/* 2215 */       } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
/* 2216 */         int i = paramString2.indexOf(c);
/* 2217 */         if (i >= 0) {
/*      */ 
/*      */ 
/*      */           
/* 2221 */           if (i < paramString3.length()) {
/* 2222 */             c = paramString3.charAt(i);
/*      */           }
/*      */         } else {
/* 2225 */           throw new IllegalArgumentException("Illegal pattern  character '" + c + "'");
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2231 */       stringBuilder.append(c);
/*      */     } 
/* 2233 */     if (bool) {
/* 2234 */       throw new IllegalArgumentException("Unfinished quote in pattern");
/*      */     }
/* 2236 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toPattern() {
/* 2245 */     return this.pattern;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toLocalizedPattern() {
/* 2254 */     return translatePattern(this.pattern, "GyMdkHmsSEDFwWahKzZYuXL", this.formatData
/*      */         
/* 2256 */         .getLocalPatternChars());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyPattern(String paramString) {
/* 2268 */     applyPatternImpl(paramString);
/*      */   }
/*      */   
/*      */   private void applyPatternImpl(String paramString) {
/* 2272 */     this.compiledPattern = compile(paramString);
/* 2273 */     this.pattern = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyLocalizedPattern(String paramString) {
/* 2285 */     String str = translatePattern(paramString, this.formatData
/* 2286 */         .getLocalPatternChars(), "GyMdkHmsSEDFwWahKzZYuXL");
/*      */     
/* 2288 */     this.compiledPattern = compile(str);
/* 2289 */     this.pattern = str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DateFormatSymbols getDateFormatSymbols() {
/* 2300 */     return (DateFormatSymbols)this.formatData.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDateFormatSymbols(DateFormatSymbols paramDateFormatSymbols) {
/* 2312 */     this.formatData = (DateFormatSymbols)paramDateFormatSymbols.clone();
/* 2313 */     this.useDateFormatSymbols = true;
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
/* 2324 */     SimpleDateFormat simpleDateFormat = (SimpleDateFormat)super.clone();
/* 2325 */     simpleDateFormat.formatData = (DateFormatSymbols)this.formatData.clone();
/* 2326 */     return simpleDateFormat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 2337 */     return this.pattern.hashCode();
/*      */   }
/*      */ 
/*      */ 
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
/* 2351 */     if (!super.equals(paramObject)) {
/* 2352 */       return false;
/*      */     }
/* 2354 */     SimpleDateFormat simpleDateFormat = (SimpleDateFormat)paramObject;
/* 2355 */     return (this.pattern.equals(simpleDateFormat.pattern) && this.formatData
/* 2356 */       .equals(simpleDateFormat.formatData));
/*      */   }
/*      */   
/* 2359 */   private static final int[] REST_OF_STYLES = new int[] { 32769, 2, 32770 };
/*      */ 
/*      */   
/*      */   private Map<String, Integer> getDisplayNamesMap(int paramInt, Locale paramLocale) {
/* 2363 */     Map<String, Integer> map = this.calendar.getDisplayNames(paramInt, 1, paramLocale);
/*      */     
/* 2365 */     for (int i : REST_OF_STYLES) {
/* 2366 */       Map<String, Integer> map1 = this.calendar.getDisplayNames(paramInt, i, paramLocale);
/* 2367 */       if (map1 != null) {
/* 2368 */         map.putAll(map1);
/*      */       }
/*      */     } 
/* 2371 */     return map;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 2382 */     paramObjectInputStream.defaultReadObject();
/*      */     
/*      */     try {
/* 2385 */       this.compiledPattern = compile(this.pattern);
/* 2386 */     } catch (Exception exception) {
/* 2387 */       throw new InvalidObjectException("invalid pattern");
/*      */     } 
/*      */     
/* 2390 */     if (this.serialVersionOnStream < 1) {
/*      */       
/* 2392 */       initializeDefaultCentury();
/*      */     }
/*      */     else {
/*      */       
/* 2396 */       parseAmbiguousDatesAsAfter(this.defaultCenturyStart);
/*      */     } 
/* 2398 */     this.serialVersionOnStream = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2404 */     TimeZone timeZone = getTimeZone();
/* 2405 */     if (timeZone instanceof java.util.SimpleTimeZone) {
/* 2406 */       String str = timeZone.getID();
/* 2407 */       TimeZone timeZone1 = TimeZone.getTimeZone(str);
/* 2408 */       if (timeZone1 != null && timeZone1.hasSameRules(timeZone) && timeZone1.getID().equals(str)) {
/* 2409 */         setTimeZone(timeZone1);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkNegativeNumberExpression() {
/* 2419 */     if (this.numberFormat instanceof DecimalFormat && 
/* 2420 */       !this.numberFormat.equals(this.originalNumberFormat)) {
/* 2421 */       String str = ((DecimalFormat)this.numberFormat).toPattern();
/* 2422 */       if (!str.equals(this.originalNumberPattern)) {
/* 2423 */         this.hasFollowingMinusSign = false;
/*      */         
/* 2425 */         int i = str.indexOf(';');
/*      */ 
/*      */         
/* 2428 */         if (i > -1) {
/* 2429 */           int j = str.indexOf('-', i);
/* 2430 */           if (j > str.lastIndexOf('0') && j > str
/* 2431 */             .lastIndexOf('#')) {
/* 2432 */             this.hasFollowingMinusSign = true;
/* 2433 */             this.minusSign = ((DecimalFormat)this.numberFormat).getDecimalFormatSymbols().getMinusSign();
/*      */           } 
/*      */         } 
/* 2436 */         this.originalNumberPattern = str;
/*      */       } 
/* 2438 */       this.originalNumberFormat = this.numberFormat;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/SimpleDateFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */