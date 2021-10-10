/*      */ package java.text;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.math.BigInteger;
/*      */ import java.math.RoundingMode;
/*      */ import java.text.spi.NumberFormatProvider;
/*      */ import java.util.Currency;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import sun.util.locale.provider.LocaleProviderAdapter;
/*      */ import sun.util.locale.provider.LocaleServiceProviderPool;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class NumberFormat
/*      */   extends Format
/*      */ {
/*      */   public static final int INTEGER_FIELD = 0;
/*      */   public static final int FRACTION_FIELD = 1;
/*      */   private static final int NUMBERSTYLE = 0;
/*      */   private static final int CURRENCYSTYLE = 1;
/*      */   private static final int PERCENTSTYLE = 2;
/*      */   private static final int SCIENTIFICSTYLE = 3;
/*      */   private static final int INTEGERSTYLE = 4;
/*      */   
/*      */   public StringBuffer format(Object paramObject, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition) {
/*  239 */     if (paramObject instanceof Long || paramObject instanceof Integer || paramObject instanceof Short || paramObject instanceof Byte || paramObject instanceof java.util.concurrent.atomic.AtomicInteger || paramObject instanceof java.util.concurrent.atomic.AtomicLong || (paramObject instanceof BigInteger && ((BigInteger)paramObject)
/*      */ 
/*      */ 
/*      */       
/*  243 */       .bitLength() < 64))
/*  244 */       return format(((Number)paramObject).longValue(), paramStringBuffer, paramFieldPosition); 
/*  245 */     if (paramObject instanceof Number) {
/*  246 */       return format(((Number)paramObject).doubleValue(), paramStringBuffer, paramFieldPosition);
/*      */     }
/*  248 */     throw new IllegalArgumentException("Cannot format given Object as a Number");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Object parseObject(String paramString, ParsePosition paramParsePosition) {
/*  278 */     return parse(paramString, paramParsePosition);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String format(double paramDouble) {
/*  292 */     String str = fastFormat(paramDouble);
/*  293 */     if (str != null) {
/*  294 */       return str;
/*      */     }
/*  296 */     return format(paramDouble, new StringBuffer(), DontCareFieldPosition.INSTANCE)
/*  297 */       .toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String fastFormat(double paramDouble) {
/*  304 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String format(long paramLong) {
/*  316 */     return format(paramLong, new StringBuffer(), DontCareFieldPosition.INSTANCE)
/*  317 */       .toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract StringBuffer format(double paramDouble, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract StringBuffer format(long paramLong, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract Number parse(String paramString, ParsePosition paramParsePosition);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Number parse(String paramString) throws ParseException {
/*  382 */     ParsePosition parsePosition = new ParsePosition(0);
/*  383 */     Number number = parse(paramString, parsePosition);
/*  384 */     if (parsePosition.index == 0) {
/*  385 */       throw new ParseException("Unparseable number: \"" + paramString + "\"", parsePosition.errorIndex);
/*      */     }
/*      */     
/*  388 */     return number;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isParseIntegerOnly() {
/*  403 */     return this.parseIntegerOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setParseIntegerOnly(boolean paramBoolean) {
/*  414 */     this.parseIntegerOnly = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final NumberFormat getInstance() {
/*  429 */     return getInstance(Locale.getDefault(Locale.Category.FORMAT), 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static NumberFormat getInstance(Locale paramLocale) {
/*  442 */     return getInstance(paramLocale, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final NumberFormat getNumberInstance() {
/*  458 */     return getInstance(Locale.getDefault(Locale.Category.FORMAT), 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static NumberFormat getNumberInstance(Locale paramLocale) {
/*  469 */     return getInstance(paramLocale, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final NumberFormat getIntegerInstance() {
/*  491 */     return getInstance(Locale.getDefault(Locale.Category.FORMAT), 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static NumberFormat getIntegerInstance(Locale paramLocale) {
/*  508 */     return getInstance(paramLocale, 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final NumberFormat getCurrencyInstance() {
/*  523 */     return getInstance(Locale.getDefault(Locale.Category.FORMAT), 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static NumberFormat getCurrencyInstance(Locale paramLocale) {
/*  533 */     return getInstance(paramLocale, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final NumberFormat getPercentInstance() {
/*  548 */     return getInstance(Locale.getDefault(Locale.Category.FORMAT), 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static NumberFormat getPercentInstance(Locale paramLocale) {
/*  558 */     return getInstance(paramLocale, 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final NumberFormat getScientificInstance() {
/*  565 */     return getInstance(Locale.getDefault(Locale.Category.FORMAT), 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static NumberFormat getScientificInstance(Locale paramLocale) {
/*  574 */     return getInstance(paramLocale, 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Locale[] getAvailableLocales() {
/*  592 */     LocaleServiceProviderPool localeServiceProviderPool = LocaleServiceProviderPool.getPool((Class)NumberFormatProvider.class);
/*  593 */     return localeServiceProviderPool.getAvailableLocales();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  601 */     return this.maximumIntegerDigits * 37 + this.maxFractionDigits;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*  610 */     if (paramObject == null) {
/*  611 */       return false;
/*      */     }
/*  613 */     if (this == paramObject) {
/*  614 */       return true;
/*      */     }
/*  616 */     if (getClass() != paramObject.getClass()) {
/*  617 */       return false;
/*      */     }
/*  619 */     NumberFormat numberFormat = (NumberFormat)paramObject;
/*  620 */     return (this.maximumIntegerDigits == numberFormat.maximumIntegerDigits && this.minimumIntegerDigits == numberFormat.minimumIntegerDigits && this.maximumFractionDigits == numberFormat.maximumFractionDigits && this.minimumFractionDigits == numberFormat.minimumFractionDigits && this.groupingUsed == numberFormat.groupingUsed && this.parseIntegerOnly == numberFormat.parseIntegerOnly);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*  633 */     return super.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isGroupingUsed() {
/*  648 */     return this.groupingUsed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGroupingUsed(boolean paramBoolean) {
/*  659 */     this.groupingUsed = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumIntegerDigits() {
/*  670 */     return this.maximumIntegerDigits;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaximumIntegerDigits(int paramInt) {
/*  686 */     this.maximumIntegerDigits = Math.max(0, paramInt);
/*  687 */     if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
/*  688 */       this.minimumIntegerDigits = this.maximumIntegerDigits;
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
/*      */   public int getMinimumIntegerDigits() {
/*  700 */     return this.minimumIntegerDigits;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinimumIntegerDigits(int paramInt) {
/*  716 */     this.minimumIntegerDigits = Math.max(0, paramInt);
/*  717 */     if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
/*  718 */       this.maximumIntegerDigits = this.minimumIntegerDigits;
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
/*      */   public int getMaximumFractionDigits() {
/*  730 */     return this.maximumFractionDigits;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaximumFractionDigits(int paramInt) {
/*  746 */     this.maximumFractionDigits = Math.max(0, paramInt);
/*  747 */     if (this.maximumFractionDigits < this.minimumFractionDigits) {
/*  748 */       this.minimumFractionDigits = this.maximumFractionDigits;
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
/*      */   public int getMinimumFractionDigits() {
/*  760 */     return this.minimumFractionDigits;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinimumFractionDigits(int paramInt) {
/*  776 */     this.minimumFractionDigits = Math.max(0, paramInt);
/*  777 */     if (this.maximumFractionDigits < this.minimumFractionDigits) {
/*  778 */       this.maximumFractionDigits = this.minimumFractionDigits;
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
/*      */   public Currency getCurrency() {
/*  798 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurrency(Currency paramCurrency) {
/*  816 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RoundingMode getRoundingMode() {
/*  833 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRoundingMode(RoundingMode paramRoundingMode) {
/*  851 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static NumberFormat getInstance(Locale paramLocale, int paramInt) {
/*  859 */     LocaleProviderAdapter localeProviderAdapter = LocaleProviderAdapter.getAdapter((Class)NumberFormatProvider.class, paramLocale);
/*      */     
/*  861 */     NumberFormat numberFormat = getInstance(localeProviderAdapter, paramLocale, paramInt);
/*  862 */     if (numberFormat == null) {
/*  863 */       numberFormat = getInstance(LocaleProviderAdapter.forJRE(), paramLocale, paramInt);
/*      */     }
/*      */     
/*  866 */     return numberFormat;
/*      */   }
/*      */ 
/*      */   
/*      */   private static NumberFormat getInstance(LocaleProviderAdapter paramLocaleProviderAdapter, Locale paramLocale, int paramInt) {
/*  871 */     NumberFormatProvider numberFormatProvider = paramLocaleProviderAdapter.getNumberFormatProvider();
/*  872 */     NumberFormat numberFormat = null;
/*  873 */     switch (paramInt) {
/*      */       case 0:
/*  875 */         numberFormat = numberFormatProvider.getNumberInstance(paramLocale);
/*      */         break;
/*      */       case 2:
/*  878 */         numberFormat = numberFormatProvider.getPercentInstance(paramLocale);
/*      */         break;
/*      */       case 1:
/*  881 */         numberFormat = numberFormatProvider.getCurrencyInstance(paramLocale);
/*      */         break;
/*      */       case 4:
/*  884 */         numberFormat = numberFormatProvider.getIntegerInstance(paramLocale);
/*      */         break;
/*      */     } 
/*  887 */     return numberFormat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  913 */     paramObjectInputStream.defaultReadObject();
/*  914 */     if (this.serialVersionOnStream < 1) {
/*      */       
/*  916 */       this.maximumIntegerDigits = this.maxIntegerDigits;
/*  917 */       this.minimumIntegerDigits = this.minIntegerDigits;
/*  918 */       this.maximumFractionDigits = this.maxFractionDigits;
/*  919 */       this.minimumFractionDigits = this.minFractionDigits;
/*      */     } 
/*  921 */     if (this.minimumIntegerDigits > this.maximumIntegerDigits || this.minimumFractionDigits > this.maximumFractionDigits || this.minimumIntegerDigits < 0 || this.minimumFractionDigits < 0)
/*      */     {
/*      */       
/*  924 */       throw new InvalidObjectException("Digit count range invalid");
/*      */     }
/*  926 */     this.serialVersionOnStream = 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*  941 */     this.maxIntegerDigits = (this.maximumIntegerDigits > 127) ? Byte.MAX_VALUE : (byte)this.maximumIntegerDigits;
/*      */     
/*  943 */     this.minIntegerDigits = (this.minimumIntegerDigits > 127) ? Byte.MAX_VALUE : (byte)this.minimumIntegerDigits;
/*      */     
/*  945 */     this.maxFractionDigits = (this.maximumFractionDigits > 127) ? Byte.MAX_VALUE : (byte)this.maximumFractionDigits;
/*      */     
/*  947 */     this.minFractionDigits = (this.minimumFractionDigits > 127) ? Byte.MAX_VALUE : (byte)this.minimumFractionDigits;
/*      */     
/*  949 */     paramObjectOutputStream.defaultWriteObject();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean groupingUsed = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  984 */   private byte maxIntegerDigits = 40;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1002 */   private byte minIntegerDigits = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1020 */   private byte maxFractionDigits = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1038 */   private byte minFractionDigits = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean parseIntegerOnly = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1059 */   private int maximumIntegerDigits = 40;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1070 */   private int minimumIntegerDigits = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1081 */   private int maximumFractionDigits = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1092 */   private int minimumFractionDigits = 0;
/*      */ 
/*      */ 
/*      */ 
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
/* 1117 */   private int serialVersionOnStream = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final long serialVersionUID = -2308460125733713944L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Field
/*      */     extends Format.Field
/*      */   {
/*      */     private static final long serialVersionUID = 7494728892700160890L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1141 */     private static final Map<String, Field> instanceMap = new HashMap<>(11);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Field(String param1String) {
/* 1150 */       super(param1String);
/* 1151 */       if (getClass() == Field.class) {
/* 1152 */         instanceMap.put(param1String, this);
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
/*      */     protected Object readResolve() throws InvalidObjectException {
/* 1164 */       if (getClass() != Field.class) {
/* 1165 */         throw new InvalidObjectException("subclass didn't correctly implement readResolve");
/*      */       }
/*      */       
/* 1168 */       Object object = instanceMap.get(getName());
/* 1169 */       if (object != null) {
/* 1170 */         return object;
/*      */       }
/* 1172 */       throw new InvalidObjectException("unknown attribute name");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1179 */     public static final Field INTEGER = new Field("integer");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1184 */     public static final Field FRACTION = new Field("fraction");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1189 */     public static final Field EXPONENT = new Field("exponent");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1194 */     public static final Field DECIMAL_SEPARATOR = new Field("decimal separator");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1200 */     public static final Field SIGN = new Field("sign");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1205 */     public static final Field GROUPING_SEPARATOR = new Field("grouping separator");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1211 */     public static final Field EXPONENT_SYMBOL = new Field("exponent symbol");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1217 */     public static final Field PERCENT = new Field("percent");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1222 */     public static final Field PERMILLE = new Field("per mille");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1227 */     public static final Field CURRENCY = new Field("currency");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1232 */     public static final Field EXPONENT_SIGN = new Field("exponent sign");
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/NumberFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */