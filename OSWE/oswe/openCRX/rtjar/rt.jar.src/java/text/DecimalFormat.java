/*      */ package java.text;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.math.RoundingMode;
/*      */ import java.text.spi.NumberFormatProvider;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Currency;
/*      */ import java.util.Locale;
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
/*      */ public class DecimalFormat
/*      */   extends NumberFormat
/*      */ {
/*      */   private transient BigInteger bigIntegerMultiplier;
/*      */   private transient BigDecimal bigDecimalMultiplier;
/*      */   private static final int STATUS_INFINITE = 0;
/*      */   private static final int STATUS_POSITIVE = 1;
/*      */   private static final int STATUS_LENGTH = 2;
/*      */   
/*      */   public DecimalFormat() {
/*  401 */     Locale locale = Locale.getDefault(Locale.Category.FORMAT);
/*  402 */     LocaleProviderAdapter localeProviderAdapter = LocaleProviderAdapter.getAdapter((Class)NumberFormatProvider.class, locale);
/*  403 */     if (!(localeProviderAdapter instanceof sun.util.locale.provider.ResourceBundleBasedAdapter)) {
/*  404 */       localeProviderAdapter = LocaleProviderAdapter.getResourceBundleBased();
/*      */     }
/*  406 */     String[] arrayOfString = localeProviderAdapter.getLocaleResources(locale).getNumberPatterns();
/*      */ 
/*      */     
/*  409 */     this.symbols = DecimalFormatSymbols.getInstance(locale);
/*  410 */     applyPattern(arrayOfString[0], false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DecimalFormat(String paramString) {
/*  435 */     this.symbols = DecimalFormatSymbols.getInstance(Locale.getDefault(Locale.Category.FORMAT));
/*  436 */     applyPattern(paramString, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DecimalFormat(String paramString, DecimalFormatSymbols paramDecimalFormatSymbols) {
/*  463 */     this.symbols = (DecimalFormatSymbols)paramDecimalFormatSymbols.clone();
/*  464 */     applyPattern(paramString, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final StringBuffer format(Object paramObject, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition) {
/*  493 */     if (paramObject instanceof Long || paramObject instanceof Integer || paramObject instanceof Short || paramObject instanceof Byte || paramObject instanceof java.util.concurrent.atomic.AtomicInteger || paramObject instanceof java.util.concurrent.atomic.AtomicLong || (paramObject instanceof BigInteger && ((BigInteger)paramObject)
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  498 */       .bitLength() < 64))
/*  499 */       return format(((Number)paramObject).longValue(), paramStringBuffer, paramFieldPosition); 
/*  500 */     if (paramObject instanceof BigDecimal)
/*  501 */       return format((BigDecimal)paramObject, paramStringBuffer, paramFieldPosition); 
/*  502 */     if (paramObject instanceof BigInteger)
/*  503 */       return format((BigInteger)paramObject, paramStringBuffer, paramFieldPosition); 
/*  504 */     if (paramObject instanceof Number) {
/*  505 */       return format(((Number)paramObject).doubleValue(), paramStringBuffer, paramFieldPosition);
/*      */     }
/*  507 */     throw new IllegalArgumentException("Cannot format given Object as a Number");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuffer format(double paramDouble, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition) {
/*  527 */     boolean bool = false;
/*  528 */     if (paramFieldPosition == DontCareFieldPosition.INSTANCE) {
/*  529 */       bool = true;
/*      */     } else {
/*  531 */       paramFieldPosition.setBeginIndex(0);
/*  532 */       paramFieldPosition.setEndIndex(0);
/*      */     } 
/*      */     
/*  535 */     if (bool) {
/*  536 */       String str = fastFormat(paramDouble);
/*  537 */       if (str != null) {
/*  538 */         paramStringBuffer.append(str);
/*  539 */         return paramStringBuffer;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  544 */     return format(paramDouble, paramStringBuffer, paramFieldPosition.getFieldDelegate());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StringBuffer format(double paramDouble, StringBuffer paramStringBuffer, Format.FieldDelegate paramFieldDelegate) {
/*  558 */     if (Double.isNaN(paramDouble) || (
/*  559 */       Double.isInfinite(paramDouble) && this.multiplier == 0)) {
/*  560 */       int j = paramStringBuffer.length();
/*  561 */       paramStringBuffer.append(this.symbols.getNaN());
/*  562 */       paramFieldDelegate.formatted(0, NumberFormat.Field.INTEGER, NumberFormat.Field.INTEGER, j, paramStringBuffer
/*  563 */           .length(), paramStringBuffer);
/*  564 */       return paramStringBuffer;
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
/*  577 */     int i = ((paramDouble < 0.0D || (paramDouble == 0.0D && 1.0D / paramDouble < 0.0D)) ? 1 : 0) ^ ((this.multiplier < 0) ? 1 : 0);
/*      */     
/*  579 */     if (this.multiplier != 1) {
/*  580 */       paramDouble *= this.multiplier;
/*      */     }
/*      */     
/*  583 */     if (Double.isInfinite(paramDouble)) {
/*  584 */       if (i != 0) {
/*  585 */         append(paramStringBuffer, this.negativePrefix, paramFieldDelegate, 
/*  586 */             getNegativePrefixFieldPositions(), NumberFormat.Field.SIGN);
/*      */       } else {
/*  588 */         append(paramStringBuffer, this.positivePrefix, paramFieldDelegate, 
/*  589 */             getPositivePrefixFieldPositions(), NumberFormat.Field.SIGN);
/*      */       } 
/*      */       
/*  592 */       int j = paramStringBuffer.length();
/*  593 */       paramStringBuffer.append(this.symbols.getInfinity());
/*  594 */       paramFieldDelegate.formatted(0, NumberFormat.Field.INTEGER, NumberFormat.Field.INTEGER, j, paramStringBuffer
/*  595 */           .length(), paramStringBuffer);
/*      */       
/*  597 */       if (i != 0) {
/*  598 */         append(paramStringBuffer, this.negativeSuffix, paramFieldDelegate, 
/*  599 */             getNegativeSuffixFieldPositions(), NumberFormat.Field.SIGN);
/*      */       } else {
/*  601 */         append(paramStringBuffer, this.positiveSuffix, paramFieldDelegate, 
/*  602 */             getPositiveSuffixFieldPositions(), NumberFormat.Field.SIGN);
/*      */       } 
/*      */       
/*  605 */       return paramStringBuffer;
/*      */     } 
/*      */     
/*  608 */     if (i != 0) {
/*  609 */       paramDouble = -paramDouble;
/*      */     }
/*      */ 
/*      */     
/*  613 */     assert paramDouble >= 0.0D && !Double.isInfinite(paramDouble);
/*      */     
/*  615 */     synchronized (this.digitList) {
/*  616 */       int j = super.getMaximumIntegerDigits();
/*  617 */       int k = super.getMinimumIntegerDigits();
/*  618 */       int m = super.getMaximumFractionDigits();
/*  619 */       int n = super.getMinimumFractionDigits();
/*      */       
/*  621 */       this.digitList.set(i, paramDouble, this.useExponentialNotation ? (j + m) : m, !this.useExponentialNotation);
/*      */ 
/*      */       
/*  624 */       return subformat(paramStringBuffer, paramFieldDelegate, i, false, j, k, m, n);
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
/*      */   public StringBuffer format(long paramLong, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition) {
/*  643 */     paramFieldPosition.setBeginIndex(0);
/*  644 */     paramFieldPosition.setEndIndex(0);
/*      */     
/*  646 */     return format(paramLong, paramStringBuffer, paramFieldPosition.getFieldDelegate());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StringBuffer format(long paramLong, StringBuffer paramStringBuffer, Format.FieldDelegate paramFieldDelegate) {
/*  661 */     boolean bool1 = (paramLong < 0L) ? true : false;
/*  662 */     if (bool1) {
/*  663 */       paramLong = -paramLong;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  672 */     boolean bool2 = false;
/*  673 */     if (paramLong < 0L) {
/*  674 */       if (this.multiplier != 0) {
/*  675 */         bool2 = true;
/*      */       }
/*  677 */     } else if (this.multiplier != 1 && this.multiplier != 0) {
/*  678 */       long l = Long.MAX_VALUE / this.multiplier;
/*  679 */       if (l < 0L) {
/*  680 */         l = -l;
/*      */       }
/*  682 */       bool2 = (paramLong > l) ? true : false;
/*      */     } 
/*      */     
/*  685 */     if (bool2) {
/*  686 */       if (bool1) {
/*  687 */         paramLong = -paramLong;
/*      */       }
/*  689 */       BigInteger bigInteger = BigInteger.valueOf(paramLong);
/*  690 */       return format(bigInteger, paramStringBuffer, paramFieldDelegate, true);
/*      */     } 
/*      */     
/*  693 */     paramLong *= this.multiplier;
/*  694 */     if (paramLong == 0L) {
/*  695 */       bool1 = false;
/*      */     }
/*  697 */     else if (this.multiplier < 0) {
/*  698 */       paramLong = -paramLong;
/*  699 */       bool1 = !bool1 ? true : false;
/*      */     } 
/*      */ 
/*      */     
/*  703 */     synchronized (this.digitList) {
/*  704 */       int i = super.getMaximumIntegerDigits();
/*  705 */       int j = super.getMinimumIntegerDigits();
/*  706 */       int k = super.getMaximumFractionDigits();
/*  707 */       int m = super.getMinimumFractionDigits();
/*      */       
/*  709 */       this.digitList.set(bool1, paramLong, this.useExponentialNotation ? (i + k) : 0);
/*      */ 
/*      */       
/*  712 */       return subformat(paramStringBuffer, paramFieldDelegate, bool1, true, i, j, k, m);
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
/*      */   private StringBuffer format(BigDecimal paramBigDecimal, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition) {
/*  730 */     paramFieldPosition.setBeginIndex(0);
/*  731 */     paramFieldPosition.setEndIndex(0);
/*  732 */     return format(paramBigDecimal, paramStringBuffer, paramFieldPosition.getFieldDelegate());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StringBuffer format(BigDecimal paramBigDecimal, StringBuffer paramStringBuffer, Format.FieldDelegate paramFieldDelegate) {
/*  746 */     if (this.multiplier != 1) {
/*  747 */       paramBigDecimal = paramBigDecimal.multiply(getBigDecimalMultiplier());
/*      */     }
/*  749 */     boolean bool = (paramBigDecimal.signum() == -1) ? true : false;
/*  750 */     if (bool) {
/*  751 */       paramBigDecimal = paramBigDecimal.negate();
/*      */     }
/*      */     
/*  754 */     synchronized (this.digitList) {
/*  755 */       int i = getMaximumIntegerDigits();
/*  756 */       int j = getMinimumIntegerDigits();
/*  757 */       int k = getMaximumFractionDigits();
/*  758 */       int m = getMinimumFractionDigits();
/*  759 */       int n = i + k;
/*      */       
/*  761 */       this.digitList.set(bool, paramBigDecimal, this.useExponentialNotation ? ((n < 0) ? Integer.MAX_VALUE : n) : k, !this.useExponentialNotation);
/*      */ 
/*      */ 
/*      */       
/*  765 */       return subformat(paramStringBuffer, paramFieldDelegate, bool, false, i, j, k, m);
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
/*      */   private StringBuffer format(BigInteger paramBigInteger, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition) {
/*  783 */     paramFieldPosition.setBeginIndex(0);
/*  784 */     paramFieldPosition.setEndIndex(0);
/*      */     
/*  786 */     return format(paramBigInteger, paramStringBuffer, paramFieldPosition.getFieldDelegate(), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StringBuffer format(BigInteger paramBigInteger, StringBuffer paramStringBuffer, Format.FieldDelegate paramFieldDelegate, boolean paramBoolean) {
/*  801 */     if (this.multiplier != 1) {
/*  802 */       paramBigInteger = paramBigInteger.multiply(getBigIntegerMultiplier());
/*      */     }
/*  804 */     boolean bool = (paramBigInteger.signum() == -1) ? true : false;
/*  805 */     if (bool) {
/*  806 */       paramBigInteger = paramBigInteger.negate();
/*      */     }
/*      */     
/*  809 */     synchronized (this.digitList) {
/*      */       int i, j, k, m, n;
/*  811 */       if (paramBoolean) {
/*  812 */         i = super.getMaximumIntegerDigits();
/*  813 */         j = super.getMinimumIntegerDigits();
/*  814 */         k = super.getMaximumFractionDigits();
/*  815 */         m = super.getMinimumFractionDigits();
/*  816 */         n = i + k;
/*      */       } else {
/*  818 */         i = getMaximumIntegerDigits();
/*  819 */         j = getMinimumIntegerDigits();
/*  820 */         k = getMaximumFractionDigits();
/*  821 */         m = getMinimumFractionDigits();
/*  822 */         n = i + k;
/*  823 */         if (n < 0) {
/*  824 */           n = Integer.MAX_VALUE;
/*      */         }
/*      */       } 
/*      */       
/*  828 */       this.digitList.set(bool, paramBigInteger, this.useExponentialNotation ? n : 0);
/*      */ 
/*      */       
/*  831 */       return subformat(paramStringBuffer, paramFieldDelegate, bool, true, i, j, k, m);
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
/*      */   public AttributedCharacterIterator formatToCharacterIterator(Object paramObject) {
/*  857 */     CharacterIteratorFieldDelegate characterIteratorFieldDelegate = new CharacterIteratorFieldDelegate();
/*      */     
/*  859 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/*  861 */     if (paramObject instanceof Double || paramObject instanceof Float)
/*  862 */     { format(((Number)paramObject).doubleValue(), stringBuffer, characterIteratorFieldDelegate); }
/*  863 */     else if (paramObject instanceof Long || paramObject instanceof Integer || paramObject instanceof Short || paramObject instanceof Byte || paramObject instanceof java.util.concurrent.atomic.AtomicInteger || paramObject instanceof java.util.concurrent.atomic.AtomicLong)
/*      */     
/*      */     { 
/*  866 */       format(((Number)paramObject).longValue(), stringBuffer, characterIteratorFieldDelegate); }
/*  867 */     else if (paramObject instanceof BigDecimal)
/*  868 */     { format((BigDecimal)paramObject, stringBuffer, characterIteratorFieldDelegate); }
/*  869 */     else if (paramObject instanceof BigInteger)
/*  870 */     { format((BigInteger)paramObject, stringBuffer, characterIteratorFieldDelegate, false); }
/*  871 */     else { if (paramObject == null) {
/*  872 */         throw new NullPointerException("formatToCharacterIterator must be passed non-null object");
/*      */       }
/*      */       
/*  875 */       throw new IllegalArgumentException("Cannot format given Object as a Number"); }
/*      */ 
/*      */     
/*  878 */     return characterIteratorFieldDelegate.getIterator(stringBuffer.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkAndSetFastPathStatus() {
/*  973 */     boolean bool = this.isFastPath;
/*      */     
/*  975 */     if (this.roundingMode == RoundingMode.HALF_EVEN && 
/*  976 */       isGroupingUsed() && this.groupingSize == 3 && this.multiplier == 1 && !this.decimalSeparatorAlwaysShown && !this.useExponentialNotation) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  984 */       this.isFastPath = (this.minimumIntegerDigits == 1 && this.maximumIntegerDigits >= 10);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  989 */       if (this.isFastPath)
/*  990 */         if (this.isCurrencyFormat) {
/*  991 */           if (this.minimumFractionDigits != 2 || this.maximumFractionDigits != 2)
/*      */           {
/*  993 */             this.isFastPath = false; } 
/*  994 */         } else if (this.minimumFractionDigits != 0 || this.maximumFractionDigits != 3) {
/*      */           
/*  996 */           this.isFastPath = false;
/*      */         }  
/*      */     } else {
/*  999 */       this.isFastPath = false;
/*      */     } 
/* 1001 */     resetFastPathData(bool);
/* 1002 */     this.fastPathCheckNeeded = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1011 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetFastPathData(boolean paramBoolean) {
/* 1017 */     if (this.isFastPath) {
/*      */       
/* 1019 */       if (this.fastPathData == null) {
/* 1020 */         this.fastPathData = new FastPathData();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1025 */       this.fastPathData.zeroDelta = this.symbols.getZeroDigit() - 48;
/* 1026 */       this.fastPathData.groupingChar = this.symbols.getGroupingSeparator();
/*      */ 
/*      */       
/* 1029 */       this.fastPathData.fractionalMaxIntBound = this.isCurrencyFormat ? 99 : 999;
/*      */       
/* 1031 */       this.fastPathData.fractionalScaleFactor = this.isCurrencyFormat ? 100.0D : 1000.0D;
/*      */ 
/*      */ 
/*      */       
/* 1035 */       this.fastPathData
/*      */         
/* 1037 */         .positiveAffixesRequired = (this.positivePrefix.length() != 0 || this.positiveSuffix.length() != 0);
/* 1038 */       this.fastPathData
/*      */         
/* 1040 */         .negativeAffixesRequired = (this.negativePrefix.length() != 0 || this.negativeSuffix.length() != 0);
/*      */ 
/*      */       
/* 1043 */       byte b1 = 10;
/* 1044 */       byte b2 = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1049 */       int i = Math.max(this.positivePrefix.length(), this.negativePrefix.length()) + b1 + b2 + 1 + this.maximumFractionDigits + Math.max(this.positiveSuffix.length(), this.negativeSuffix.length());
/*      */       
/* 1051 */       this.fastPathData.fastPathContainer = new char[i];
/*      */ 
/*      */       
/* 1054 */       this.fastPathData.charsPositiveSuffix = this.positiveSuffix.toCharArray();
/* 1055 */       this.fastPathData.charsNegativeSuffix = this.negativeSuffix.toCharArray();
/* 1056 */       this.fastPathData.charsPositivePrefix = this.positivePrefix.toCharArray();
/* 1057 */       this.fastPathData.charsNegativePrefix = this.negativePrefix.toCharArray();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1062 */       int j = Math.max(this.positivePrefix.length(), this.negativePrefix
/* 1063 */           .length());
/* 1064 */       int k = b1 + b2 + j;
/*      */ 
/*      */       
/* 1067 */       this.fastPathData.integralLastIndex = k - 1;
/* 1068 */       this.fastPathData.fractionalFirstIndex = k + 1;
/* 1069 */       this.fastPathData.fastPathContainer[k] = this.isCurrencyFormat ? this.symbols
/*      */         
/* 1071 */         .getMonetaryDecimalSeparator() : this.symbols
/* 1072 */         .getDecimalSeparator();
/*      */     }
/* 1074 */     else if (paramBoolean) {
/*      */ 
/*      */       
/* 1077 */       this.fastPathData.fastPathContainer = null;
/* 1078 */       this.fastPathData.charsPositiveSuffix = null;
/* 1079 */       this.fastPathData.charsNegativeSuffix = null;
/* 1080 */       this.fastPathData.charsPositivePrefix = null;
/* 1081 */       this.fastPathData.charsNegativePrefix = null;
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
/*      */ 
/*      */   
/*      */   private boolean exactRoundUp(double paramDouble, int paramInt) {
/* 1165 */     double d1, d2, d3, d4 = 0.0D;
/* 1166 */     double d5 = 0.0D;
/* 1167 */     double d6 = 0.0D;
/*      */     
/* 1169 */     if (this.isCurrencyFormat) {
/*      */ 
/*      */       
/* 1172 */       d1 = paramDouble * 128.0D;
/* 1173 */       d2 = -(paramDouble * 32.0D);
/* 1174 */       d3 = paramDouble * 4.0D;
/*      */     }
/*      */     else {
/*      */       
/* 1178 */       d1 = paramDouble * 1024.0D;
/* 1179 */       d2 = -(paramDouble * 16.0D);
/* 1180 */       d3 = -(paramDouble * 8.0D);
/*      */     } 
/*      */ 
/*      */     
/* 1184 */     assert -d2 >= Math.abs(d3);
/* 1185 */     d4 = d2 + d3;
/* 1186 */     d6 = d4 - d2;
/* 1187 */     d5 = d3 - d6;
/* 1188 */     double d7 = d4;
/* 1189 */     double d8 = d5;
/*      */ 
/*      */     
/* 1192 */     assert d1 >= Math.abs(d7);
/* 1193 */     d4 = d1 + d7;
/* 1194 */     d6 = d4 - d1;
/* 1195 */     d5 = d7 - d6;
/* 1196 */     double d9 = d5;
/* 1197 */     double d10 = d4;
/* 1198 */     double d11 = d8 + d9;
/*      */ 
/*      */     
/* 1201 */     assert d10 >= Math.abs(d11);
/* 1202 */     d4 = d10 + d11;
/* 1203 */     d6 = d4 - d10;
/*      */ 
/*      */     
/* 1206 */     double d12 = d11 - d6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1230 */     if (d12 > 0.0D)
/* 1231 */       return true; 
/* 1232 */     if (d12 < 0.0D)
/* 1233 */       return false; 
/* 1234 */     if ((paramInt & 0x1) != 0) {
/* 1235 */       return true;
/*      */     }
/*      */     
/* 1238 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void collectIntegralDigits(int paramInt1, char[] paramArrayOfchar, int paramInt2) {
/* 1259 */     int i = paramInt2;
/*      */ 
/*      */     
/* 1262 */     while (paramInt1 > 999) {
/*      */       
/* 1264 */       int j = paramInt1 / 1000;
/* 1265 */       int k = paramInt1 - (j << 10) + (j << 4) + (j << 3);
/* 1266 */       paramInt1 = j;
/*      */       
/* 1268 */       paramArrayOfchar[i--] = DigitArrays.DigitOnes1000[k];
/* 1269 */       paramArrayOfchar[i--] = DigitArrays.DigitTens1000[k];
/* 1270 */       paramArrayOfchar[i--] = DigitArrays.DigitHundreds1000[k];
/* 1271 */       paramArrayOfchar[i--] = this.fastPathData.groupingChar;
/*      */     } 
/*      */ 
/*      */     
/* 1275 */     paramArrayOfchar[i] = DigitArrays.DigitOnes1000[paramInt1];
/* 1276 */     if (paramInt1 > 9) {
/* 1277 */       paramArrayOfchar[--i] = DigitArrays.DigitTens1000[paramInt1];
/* 1278 */       if (paramInt1 > 99) {
/* 1279 */         paramArrayOfchar[--i] = DigitArrays.DigitHundreds1000[paramInt1];
/*      */       }
/*      */     } 
/* 1282 */     this.fastPathData.firstUsedIndex = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void collectFractionalDigits(int paramInt1, char[] paramArrayOfchar, int paramInt2) {
/* 1301 */     int i = paramInt2;
/*      */     
/* 1303 */     char c1 = DigitArrays.DigitOnes1000[paramInt1];
/* 1304 */     char c2 = DigitArrays.DigitTens1000[paramInt1];
/*      */     
/* 1306 */     if (this.isCurrencyFormat) {
/*      */       
/* 1308 */       paramArrayOfchar[i++] = c2;
/* 1309 */       paramArrayOfchar[i++] = c1;
/* 1310 */     } else if (paramInt1 != 0) {
/*      */       
/* 1312 */       paramArrayOfchar[i++] = DigitArrays.DigitHundreds1000[paramInt1];
/*      */ 
/*      */       
/* 1315 */       if (c1 != '0') {
/* 1316 */         paramArrayOfchar[i++] = c2;
/* 1317 */         paramArrayOfchar[i++] = c1;
/* 1318 */       } else if (c2 != '0') {
/* 1319 */         paramArrayOfchar[i++] = c2;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1324 */       i--;
/*      */     } 
/* 1326 */     this.fastPathData.lastFreeIndex = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addAffixes(char[] paramArrayOfchar1, char[] paramArrayOfchar2, char[] paramArrayOfchar3) {
/* 1343 */     int i = paramArrayOfchar2.length;
/* 1344 */     int j = paramArrayOfchar3.length;
/* 1345 */     if (i != 0) prependPrefix(paramArrayOfchar2, i, paramArrayOfchar1); 
/* 1346 */     if (j != 0) appendSuffix(paramArrayOfchar3, j, paramArrayOfchar1);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void prependPrefix(char[] paramArrayOfchar1, int paramInt, char[] paramArrayOfchar2) {
/* 1363 */     this.fastPathData.firstUsedIndex -= paramInt;
/* 1364 */     int i = this.fastPathData.firstUsedIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1370 */     if (paramInt == 1) {
/* 1371 */       paramArrayOfchar2[i] = paramArrayOfchar1[0];
/* 1372 */     } else if (paramInt <= 4) {
/* 1373 */       int j = i;
/* 1374 */       int k = j + paramInt - 1;
/* 1375 */       int m = paramInt - 1;
/* 1376 */       paramArrayOfchar2[j] = paramArrayOfchar1[0];
/* 1377 */       paramArrayOfchar2[k] = paramArrayOfchar1[m];
/*      */       
/* 1379 */       if (paramInt > 2)
/* 1380 */         paramArrayOfchar2[++j] = paramArrayOfchar1[1]; 
/* 1381 */       if (paramInt == 4)
/* 1382 */         paramArrayOfchar2[--k] = paramArrayOfchar1[2]; 
/*      */     } else {
/* 1384 */       System.arraycopy(paramArrayOfchar1, 0, paramArrayOfchar2, i, paramInt);
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
/*      */   private void appendSuffix(char[] paramArrayOfchar1, int paramInt, char[] paramArrayOfchar2) {
/* 1400 */     int i = this.fastPathData.lastFreeIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1406 */     if (paramInt == 1) {
/* 1407 */       paramArrayOfchar2[i] = paramArrayOfchar1[0];
/* 1408 */     } else if (paramInt <= 4) {
/* 1409 */       int j = i;
/* 1410 */       int k = j + paramInt - 1;
/* 1411 */       int m = paramInt - 1;
/* 1412 */       paramArrayOfchar2[j] = paramArrayOfchar1[0];
/* 1413 */       paramArrayOfchar2[k] = paramArrayOfchar1[m];
/*      */       
/* 1415 */       if (paramInt > 2)
/* 1416 */         paramArrayOfchar2[++j] = paramArrayOfchar1[1]; 
/* 1417 */       if (paramInt == 4)
/* 1418 */         paramArrayOfchar2[--k] = paramArrayOfchar1[2]; 
/*      */     } else {
/* 1420 */       System.arraycopy(paramArrayOfchar1, 0, paramArrayOfchar2, i, paramInt);
/*      */     } 
/* 1422 */     this.fastPathData.lastFreeIndex += paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void localizeDigits(char[] paramArrayOfchar) {
/* 1442 */     int i = this.fastPathData.lastFreeIndex - this.fastPathData.fractionalFirstIndex;
/*      */ 
/*      */ 
/*      */     
/* 1446 */     if (i < 0) {
/* 1447 */       i = this.groupingSize;
/*      */     }
/*      */     
/* 1450 */     int j = this.fastPathData.lastFreeIndex - 1;
/* 1451 */     for (; j >= this.fastPathData.firstUsedIndex; 
/* 1452 */       j--) {
/* 1453 */       if (i != 0) {
/*      */         
/* 1455 */         paramArrayOfchar[j] = (char)(paramArrayOfchar[j] + this.fastPathData.zeroDelta);
/* 1456 */         i--;
/*      */       } else {
/*      */         
/* 1459 */         i = this.groupingSize;
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
/*      */ 
/*      */   
/*      */   private void fastDoubleFormat(double paramDouble, boolean paramBoolean) {
/* 1477 */     char[] arrayOfChar = this.fastPathData.fastPathContainer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1500 */     int i = (int)paramDouble;
/*      */ 
/*      */     
/* 1503 */     double d1 = paramDouble - i;
/*      */ 
/*      */     
/* 1506 */     double d2 = d1 * this.fastPathData.fractionalScaleFactor;
/*      */ 
/*      */ 
/*      */     
/* 1510 */     int j = (int)d2;
/*      */ 
/*      */     
/* 1513 */     d2 -= j;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1519 */     boolean bool = false;
/* 1520 */     if (d2 >= 0.5D) {
/* 1521 */       if (d2 == 0.5D) {
/*      */         
/* 1523 */         bool = exactRoundUp(d1, j);
/*      */       } else {
/* 1525 */         bool = true;
/*      */       } 
/* 1527 */       if (bool)
/*      */       {
/* 1529 */         if (j < this.fastPathData.fractionalMaxIntBound) {
/* 1530 */           j++;
/*      */         } else {
/*      */           
/* 1533 */           j = 0;
/* 1534 */           i++;
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1540 */     collectFractionalDigits(j, arrayOfChar, this.fastPathData.fractionalFirstIndex);
/*      */     
/* 1542 */     collectIntegralDigits(i, arrayOfChar, this.fastPathData.integralLastIndex);
/*      */ 
/*      */ 
/*      */     
/* 1546 */     if (this.fastPathData.zeroDelta != 0) {
/* 1547 */       localizeDigits(arrayOfChar);
/*      */     }
/*      */     
/* 1550 */     if (paramBoolean) {
/* 1551 */       if (this.fastPathData.negativeAffixesRequired) {
/* 1552 */         addAffixes(arrayOfChar, this.fastPathData.charsNegativePrefix, this.fastPathData.charsNegativeSuffix);
/*      */       }
/*      */     }
/* 1555 */     else if (this.fastPathData.positiveAffixesRequired) {
/* 1556 */       addAffixes(arrayOfChar, this.fastPathData.charsPositivePrefix, this.fastPathData.charsPositiveSuffix);
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
/*      */   String fastFormat(double paramDouble) {
/* 1576 */     boolean bool = false;
/*      */     
/* 1578 */     if (this.fastPathCheckNeeded) {
/* 1579 */       bool = checkAndSetFastPathStatus();
/*      */     }
/*      */     
/* 1582 */     if (!this.isFastPath)
/*      */     {
/* 1584 */       return null;
/*      */     }
/* 1586 */     if (!Double.isFinite(paramDouble))
/*      */     {
/* 1588 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1592 */     boolean bool1 = false;
/* 1593 */     if (paramDouble < 0.0D) {
/* 1594 */       bool1 = true;
/* 1595 */       paramDouble = -paramDouble;
/* 1596 */     } else if (paramDouble == 0.0D) {
/* 1597 */       bool1 = (Math.copySign(1.0D, paramDouble) == -1.0D) ? true : false;
/* 1598 */       paramDouble = 0.0D;
/*      */     } 
/*      */     
/* 1601 */     if (paramDouble > 2.147483647E9D)
/*      */     {
/* 1603 */       return null;
/*      */     }
/* 1605 */     if (!bool)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1612 */       resetFastPathData(this.isFastPath);
/*      */     }
/* 1614 */     fastDoubleFormat(paramDouble, bool1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1620 */     return new String(this.fastPathData.fastPathContainer, this.fastPathData.firstUsedIndex, this.fastPathData.lastFreeIndex - this.fastPathData.firstUsedIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StringBuffer subformat(StringBuffer paramStringBuffer, Format.FieldDelegate paramFieldDelegate, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1650 */     char c1 = this.symbols.getZeroDigit();
/* 1651 */     int i = c1 - 48;
/* 1652 */     char c2 = this.symbols.getGroupingSeparator();
/*      */ 
/*      */     
/* 1655 */     char c3 = this.isCurrencyFormat ? this.symbols.getMonetaryDecimalSeparator() : this.symbols.getDecimalSeparator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1663 */     if (this.digitList.isZero()) {
/* 1664 */       this.digitList.decimalAt = 0;
/*      */     }
/*      */     
/* 1667 */     if (paramBoolean1) {
/* 1668 */       append(paramStringBuffer, this.negativePrefix, paramFieldDelegate, 
/* 1669 */           getNegativePrefixFieldPositions(), NumberFormat.Field.SIGN);
/*      */     } else {
/* 1671 */       append(paramStringBuffer, this.positivePrefix, paramFieldDelegate, 
/* 1672 */           getPositivePrefixFieldPositions(), NumberFormat.Field.SIGN);
/*      */     } 
/*      */     
/* 1675 */     if (this.useExponentialNotation) {
/* 1676 */       int j = paramStringBuffer.length();
/* 1677 */       int k = -1;
/* 1678 */       int m = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1691 */       int n = this.digitList.decimalAt;
/* 1692 */       int i1 = paramInt1;
/* 1693 */       int i2 = paramInt2;
/* 1694 */       if (i1 > 1 && i1 > paramInt2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1700 */         if (n >= 1) {
/* 1701 */           n = (n - 1) / i1 * i1;
/*      */         } else {
/*      */           
/* 1704 */           n = (n - i1) / i1 * i1;
/*      */         } 
/* 1706 */         i2 = 1;
/*      */       } else {
/*      */         
/* 1709 */         n -= i2;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1716 */       int i3 = paramInt2 + paramInt4;
/* 1717 */       if (i3 < 0) {
/* 1718 */         i3 = Integer.MAX_VALUE;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1723 */       int i4 = this.digitList.isZero() ? i2 : (this.digitList.decimalAt - n);
/*      */       
/* 1725 */       if (i3 < i4) {
/* 1726 */         i3 = i4;
/*      */       }
/* 1728 */       int i5 = this.digitList.count;
/* 1729 */       if (i3 > i5) {
/* 1730 */         i5 = i3;
/*      */       }
/* 1732 */       boolean bool1 = false;
/*      */       int i6;
/* 1734 */       for (i6 = 0; i6 < i5; i6++) {
/* 1735 */         if (i6 == i4) {
/*      */           
/* 1737 */           k = paramStringBuffer.length();
/*      */           
/* 1739 */           paramStringBuffer.append(c3);
/* 1740 */           bool1 = true;
/*      */ 
/*      */           
/* 1743 */           m = paramStringBuffer.length();
/*      */         } 
/* 1745 */         paramStringBuffer.append((i6 < this.digitList.count) ? (char)(this.digitList.digits[i6] + i) : c1);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1750 */       if (this.decimalSeparatorAlwaysShown && i5 == i4) {
/*      */         
/* 1752 */         k = paramStringBuffer.length();
/*      */         
/* 1754 */         paramStringBuffer.append(c3);
/* 1755 */         bool1 = true;
/*      */ 
/*      */         
/* 1758 */         m = paramStringBuffer.length();
/*      */       } 
/*      */ 
/*      */       
/* 1762 */       if (k == -1) {
/* 1763 */         k = paramStringBuffer.length();
/*      */       }
/* 1765 */       paramFieldDelegate.formatted(0, NumberFormat.Field.INTEGER, NumberFormat.Field.INTEGER, j, k, paramStringBuffer);
/*      */       
/* 1767 */       if (bool1) {
/* 1768 */         paramFieldDelegate.formatted(NumberFormat.Field.DECIMAL_SEPARATOR, NumberFormat.Field.DECIMAL_SEPARATOR, k, m, paramStringBuffer);
/*      */       }
/*      */ 
/*      */       
/* 1772 */       if (m == -1) {
/* 1773 */         m = paramStringBuffer.length();
/*      */       }
/* 1775 */       paramFieldDelegate.formatted(1, NumberFormat.Field.FRACTION, NumberFormat.Field.FRACTION, m, paramStringBuffer
/* 1776 */           .length(), paramStringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1782 */       i6 = paramStringBuffer.length();
/*      */       
/* 1784 */       paramStringBuffer.append(this.symbols.getExponentSeparator());
/*      */       
/* 1786 */       paramFieldDelegate.formatted(NumberFormat.Field.EXPONENT_SYMBOL, NumberFormat.Field.EXPONENT_SYMBOL, i6, paramStringBuffer
/* 1787 */           .length(), paramStringBuffer);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1792 */       if (this.digitList.isZero()) {
/* 1793 */         n = 0;
/*      */       }
/*      */       
/* 1796 */       boolean bool2 = (n < 0) ? true : false;
/* 1797 */       if (bool2) {
/* 1798 */         n = -n;
/* 1799 */         i6 = paramStringBuffer.length();
/* 1800 */         paramStringBuffer.append(this.symbols.getMinusSign());
/* 1801 */         paramFieldDelegate.formatted(NumberFormat.Field.EXPONENT_SIGN, NumberFormat.Field.EXPONENT_SIGN, i6, paramStringBuffer
/* 1802 */             .length(), paramStringBuffer);
/*      */       } 
/* 1804 */       this.digitList.set(bool2, n);
/*      */       
/* 1806 */       int i7 = paramStringBuffer.length();
/*      */       int i8;
/* 1808 */       for (i8 = this.digitList.decimalAt; i8 < this.minExponentDigits; i8++) {
/* 1809 */         paramStringBuffer.append(c1);
/*      */       }
/* 1811 */       for (i8 = 0; i8 < this.digitList.decimalAt; i8++) {
/* 1812 */         paramStringBuffer.append((i8 < this.digitList.count) ? (char)(this.digitList.digits[i8] + i) : c1);
/*      */       }
/*      */       
/* 1815 */       paramFieldDelegate.formatted(NumberFormat.Field.EXPONENT, NumberFormat.Field.EXPONENT, i7, paramStringBuffer
/* 1816 */           .length(), paramStringBuffer);
/*      */     } else {
/* 1818 */       int j = paramStringBuffer.length();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1824 */       int k = paramInt2;
/* 1825 */       int m = 0;
/* 1826 */       if (this.digitList.decimalAt > 0 && k < this.digitList.decimalAt) {
/* 1827 */         k = this.digitList.decimalAt;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1834 */       if (k > paramInt1) {
/* 1835 */         k = paramInt1;
/* 1836 */         m = this.digitList.decimalAt - k;
/*      */       } 
/*      */       
/* 1839 */       int n = paramStringBuffer.length(); int i1;
/* 1840 */       for (i1 = k - 1; i1 >= 0; i1--) {
/* 1841 */         if (i1 < this.digitList.decimalAt && m < this.digitList.count) {
/*      */           
/* 1843 */           paramStringBuffer.append((char)(this.digitList.digits[m++] + i));
/*      */         } else {
/*      */           
/* 1846 */           paramStringBuffer.append(c1);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1852 */         if (isGroupingUsed() && i1 > 0 && this.groupingSize != 0 && i1 % this.groupingSize == 0) {
/*      */           
/* 1854 */           int i4 = paramStringBuffer.length();
/* 1855 */           paramStringBuffer.append(c2);
/* 1856 */           paramFieldDelegate.formatted(NumberFormat.Field.GROUPING_SEPARATOR, NumberFormat.Field.GROUPING_SEPARATOR, i4, paramStringBuffer
/*      */               
/* 1858 */               .length(), paramStringBuffer);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1864 */       i1 = (paramInt4 > 0 || (!paramBoolean2 && m < this.digitList.count)) ? 1 : 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1870 */       if (i1 == 0 && paramStringBuffer.length() == n) {
/* 1871 */         paramStringBuffer.append(c1);
/*      */       }
/*      */       
/* 1874 */       paramFieldDelegate.formatted(0, NumberFormat.Field.INTEGER, NumberFormat.Field.INTEGER, j, paramStringBuffer
/* 1875 */           .length(), paramStringBuffer);
/*      */ 
/*      */       
/* 1878 */       int i2 = paramStringBuffer.length();
/* 1879 */       if (this.decimalSeparatorAlwaysShown || i1 != 0) {
/* 1880 */         paramStringBuffer.append(c3);
/*      */       }
/*      */       
/* 1883 */       if (i2 != paramStringBuffer.length()) {
/* 1884 */         paramFieldDelegate.formatted(NumberFormat.Field.DECIMAL_SEPARATOR, NumberFormat.Field.DECIMAL_SEPARATOR, i2, paramStringBuffer
/*      */             
/* 1886 */             .length(), paramStringBuffer);
/*      */       }
/* 1888 */       int i3 = paramStringBuffer.length();
/*      */       
/* 1890 */       for (byte b = 0; b < paramInt3; b++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1897 */         if (b >= paramInt4 && (paramBoolean2 || m >= this.digitList.count)) {
/*      */           break;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1905 */         if (-1 - b > this.digitList.decimalAt - 1) {
/* 1906 */           paramStringBuffer.append(c1);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 1912 */         else if (!paramBoolean2 && m < this.digitList.count) {
/* 1913 */           paramStringBuffer.append((char)(this.digitList.digits[m++] + i));
/*      */         } else {
/* 1915 */           paramStringBuffer.append(c1);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1920 */       paramFieldDelegate.formatted(1, NumberFormat.Field.FRACTION, NumberFormat.Field.FRACTION, i3, paramStringBuffer
/* 1921 */           .length(), paramStringBuffer);
/*      */     } 
/*      */     
/* 1924 */     if (paramBoolean1) {
/* 1925 */       append(paramStringBuffer, this.negativeSuffix, paramFieldDelegate, 
/* 1926 */           getNegativeSuffixFieldPositions(), NumberFormat.Field.SIGN);
/*      */     } else {
/* 1928 */       append(paramStringBuffer, this.positiveSuffix, paramFieldDelegate, 
/* 1929 */           getPositiveSuffixFieldPositions(), NumberFormat.Field.SIGN);
/*      */     } 
/*      */     
/* 1932 */     return paramStringBuffer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void append(StringBuffer paramStringBuffer, String paramString, Format.FieldDelegate paramFieldDelegate, FieldPosition[] paramArrayOfFieldPosition, Format.Field paramField) {
/* 1952 */     int i = paramStringBuffer.length();
/*      */     
/* 1954 */     if (paramString.length() > 0) {
/* 1955 */       paramStringBuffer.append(paramString); byte b; int j;
/* 1956 */       for (b = 0, j = paramArrayOfFieldPosition.length; b < j; 
/* 1957 */         b++) {
/* 1958 */         FieldPosition fieldPosition = paramArrayOfFieldPosition[b];
/* 1959 */         Format.Field field = fieldPosition.getFieldAttribute();
/*      */         
/* 1961 */         if (field == NumberFormat.Field.SIGN) {
/* 1962 */           field = paramField;
/*      */         }
/* 1964 */         paramFieldDelegate.formatted(field, field, i + fieldPosition
/* 1965 */             .getBeginIndex(), i + fieldPosition
/* 1966 */             .getEndIndex(), paramStringBuffer);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Number parse(String paramString, ParsePosition paramParsePosition) {
/* 2030 */     if (paramString.regionMatches(paramParsePosition.index, this.symbols.getNaN(), 0, this.symbols.getNaN().length())) {
/* 2031 */       paramParsePosition.index += this.symbols.getNaN().length();
/* 2032 */       return new Double(Double.NaN);
/*      */     } 
/*      */     
/* 2035 */     boolean[] arrayOfBoolean = new boolean[2];
/* 2036 */     if (!subparse(paramString, paramParsePosition, this.positivePrefix, this.negativePrefix, this.digitList, false, arrayOfBoolean)) {
/* 2037 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 2041 */     if (arrayOfBoolean[0]) {
/* 2042 */       if (arrayOfBoolean[1] == ((this.multiplier >= 0))) {
/* 2043 */         return new Double(Double.POSITIVE_INFINITY);
/*      */       }
/* 2045 */       return new Double(Double.NEGATIVE_INFINITY);
/*      */     } 
/*      */ 
/*      */     
/* 2049 */     if (this.multiplier == 0) {
/* 2050 */       if (this.digitList.isZero())
/* 2051 */         return new Double(Double.NaN); 
/* 2052 */       if (arrayOfBoolean[1]) {
/* 2053 */         return new Double(Double.POSITIVE_INFINITY);
/*      */       }
/* 2055 */       return new Double(Double.NEGATIVE_INFINITY);
/*      */     } 
/*      */ 
/*      */     
/* 2059 */     if (isParseBigDecimal()) {
/* 2060 */       BigDecimal bigDecimal = this.digitList.getBigDecimal();
/*      */       
/* 2062 */       if (this.multiplier != 1) {
/*      */         try {
/* 2064 */           bigDecimal = bigDecimal.divide(getBigDecimalMultiplier());
/*      */         }
/* 2066 */         catch (ArithmeticException arithmeticException) {
/* 2067 */           bigDecimal = bigDecimal.divide(getBigDecimalMultiplier(), this.roundingMode);
/*      */         } 
/*      */       }
/*      */       
/* 2071 */       if (!arrayOfBoolean[1]) {
/* 2072 */         bigDecimal = bigDecimal.negate();
/*      */       }
/* 2074 */       return bigDecimal;
/*      */     } 
/* 2076 */     boolean bool1 = true;
/* 2077 */     boolean bool2 = false;
/* 2078 */     double d = 0.0D;
/* 2079 */     long l = 0L;
/*      */ 
/*      */     
/* 2082 */     if (this.digitList.fitsIntoLong(arrayOfBoolean[1], isParseIntegerOnly())) {
/* 2083 */       bool1 = false;
/* 2084 */       l = this.digitList.getLong();
/* 2085 */       if (l < 0L) {
/* 2086 */         bool2 = true;
/*      */       }
/*      */     } else {
/* 2089 */       d = this.digitList.getDouble();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2094 */     if (this.multiplier != 1) {
/* 2095 */       if (bool1) {
/* 2096 */         d /= this.multiplier;
/*      */       
/*      */       }
/* 2099 */       else if (l % this.multiplier == 0L) {
/* 2100 */         l /= this.multiplier;
/*      */       } else {
/* 2102 */         d = l / this.multiplier;
/* 2103 */         bool1 = true;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 2108 */     if (!arrayOfBoolean[1] && !bool2) {
/* 2109 */       d = -d;
/* 2110 */       l = -l;
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
/* 2122 */     if (this.multiplier != 1 && bool1) {
/* 2123 */       l = (long)d;
/*      */ 
/*      */       
/* 2126 */       bool1 = ((d != l || (d == 0.0D && 1.0D / d < 0.0D)) && !isParseIntegerOnly()) ? true : false;
/*      */     } 
/*      */     
/* 2129 */     return bool1 ? new Double(d) : new Long(l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BigInteger getBigIntegerMultiplier() {
/* 2138 */     if (this.bigIntegerMultiplier == null) {
/* 2139 */       this.bigIntegerMultiplier = BigInteger.valueOf(this.multiplier);
/*      */     }
/* 2141 */     return this.bigIntegerMultiplier;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BigDecimal getBigDecimalMultiplier() {
/* 2149 */     if (this.bigDecimalMultiplier == null) {
/* 2150 */       this.bigDecimalMultiplier = new BigDecimal(this.multiplier);
/*      */     }
/* 2152 */     return this.bigDecimalMultiplier;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean subparse(String paramString1, ParsePosition paramParsePosition, String paramString2, String paramString3, DigitList paramDigitList, boolean paramBoolean, boolean[] paramArrayOfboolean) {
/* 2176 */     int i = paramParsePosition.index;
/* 2177 */     int j = paramParsePosition.index;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2182 */     boolean bool1 = paramString1.regionMatches(i, paramString2, 0, paramString2
/* 2183 */         .length());
/* 2184 */     boolean bool2 = paramString1.regionMatches(i, paramString3, 0, paramString3
/* 2185 */         .length());
/*      */     
/* 2187 */     if (bool1 && bool2) {
/* 2188 */       if (paramString2.length() > paramString3.length()) {
/* 2189 */         bool2 = false;
/* 2190 */       } else if (paramString2.length() < paramString3.length()) {
/* 2191 */         bool1 = false;
/*      */       } 
/*      */     }
/*      */     
/* 2195 */     if (bool1) {
/* 2196 */       i += paramString2.length();
/* 2197 */     } else if (bool2) {
/* 2198 */       i += paramString3.length();
/*      */     } else {
/* 2200 */       paramParsePosition.errorIndex = i;
/* 2201 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 2205 */     paramArrayOfboolean[0] = false;
/* 2206 */     if (!paramBoolean && paramString1.regionMatches(i, this.symbols.getInfinity(), 0, this.symbols
/* 2207 */         .getInfinity().length())) {
/* 2208 */       i += this.symbols.getInfinity().length();
/* 2209 */       paramArrayOfboolean[0] = true;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2218 */       paramDigitList.decimalAt = paramDigitList.count = 0;
/* 2219 */       char c1 = this.symbols.getZeroDigit();
/*      */ 
/*      */       
/* 2222 */       char c2 = this.isCurrencyFormat ? this.symbols.getMonetaryDecimalSeparator() : this.symbols.getDecimalSeparator();
/* 2223 */       char c3 = this.symbols.getGroupingSeparator();
/* 2224 */       String str = this.symbols.getExponentSeparator();
/* 2225 */       boolean bool3 = false;
/* 2226 */       boolean bool4 = false;
/* 2227 */       boolean bool5 = false;
/* 2228 */       int m = 0;
/*      */ 
/*      */ 
/*      */       
/* 2232 */       byte b = 0;
/*      */       
/* 2234 */       int k = -1;
/* 2235 */       for (; i < paramString1.length(); i++) {
/* 2236 */         char c = paramString1.charAt(i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2249 */         int n = c - c1;
/* 2250 */         if (n < 0 || n > 9) {
/* 2251 */           n = Character.digit(c, 10);
/*      */         }
/*      */         
/* 2254 */         if (n == 0)
/*      */         
/* 2256 */         { k = -1;
/* 2257 */           bool5 = true;
/*      */ 
/*      */           
/* 2260 */           if (paramDigitList.count == 0) {
/*      */             
/* 2262 */             if (bool3)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2270 */               paramDigitList.decimalAt--; } 
/*      */           } else {
/* 2272 */             b++;
/* 2273 */             paramDigitList.append((char)(n + 48));
/*      */           }  }
/* 2275 */         else if (n > 0 && n <= 9)
/* 2276 */         { bool5 = true;
/* 2277 */           b++;
/* 2278 */           paramDigitList.append((char)(n + 48));
/*      */ 
/*      */           
/* 2281 */           k = -1; }
/* 2282 */         else if (!paramBoolean && c == c2)
/*      */         
/*      */         { 
/* 2285 */           if (isParseIntegerOnly() || bool3) {
/*      */             break;
/*      */           }
/* 2288 */           paramDigitList.decimalAt = b;
/* 2289 */           bool3 = true; }
/* 2290 */         else if (!paramBoolean && c == c3 && isGroupingUsed())
/* 2291 */         { if (bool3) {
/*      */             break;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 2297 */           k = i; }
/* 2298 */         else { if (!paramBoolean && paramString1.regionMatches(i, str, 0, str.length()) && !bool4) {
/*      */ 
/*      */             
/* 2301 */             ParsePosition parsePosition = new ParsePosition(i + str.length());
/* 2302 */             boolean[] arrayOfBoolean = new boolean[2];
/* 2303 */             DigitList digitList = new DigitList();
/*      */             
/* 2305 */             if (subparse(paramString1, parsePosition, "", Character.toString(this.symbols.getMinusSign()), digitList, true, arrayOfBoolean) && digitList
/* 2306 */               .fitsIntoLong(arrayOfBoolean[1], true)) {
/* 2307 */               i = parsePosition.index;
/* 2308 */               m = (int)digitList.getLong();
/* 2309 */               if (!arrayOfBoolean[1]) {
/* 2310 */                 m = -m;
/*      */               }
/* 2312 */               bool4 = true;
/*      */             } 
/*      */           } 
/*      */           
/*      */           break; }
/*      */       
/*      */       } 
/*      */       
/* 2320 */       if (k != -1) {
/* 2321 */         i = k;
/*      */       }
/*      */ 
/*      */       
/* 2325 */       if (!bool3) {
/* 2326 */         paramDigitList.decimalAt = b;
/*      */       }
/*      */ 
/*      */       
/* 2330 */       paramDigitList.decimalAt += m;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2336 */       if (!bool5 && b == 0) {
/* 2337 */         paramParsePosition.index = j;
/* 2338 */         paramParsePosition.errorIndex = j;
/* 2339 */         return false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2344 */     if (!paramBoolean) {
/* 2345 */       if (bool1) {
/* 2346 */         bool1 = paramString1.regionMatches(i, this.positiveSuffix, 0, this.positiveSuffix
/* 2347 */             .length());
/*      */       }
/* 2349 */       if (bool2) {
/* 2350 */         bool2 = paramString1.regionMatches(i, this.negativeSuffix, 0, this.negativeSuffix
/* 2351 */             .length());
/*      */       }
/*      */ 
/*      */       
/* 2355 */       if (bool1 && bool2) {
/* 2356 */         if (this.positiveSuffix.length() > this.negativeSuffix.length()) {
/* 2357 */           bool2 = false;
/* 2358 */         } else if (this.positiveSuffix.length() < this.negativeSuffix.length()) {
/* 2359 */           bool1 = false;
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/* 2364 */       if (bool1 == bool2) {
/* 2365 */         paramParsePosition.errorIndex = i;
/* 2366 */         return false;
/*      */       } 
/*      */       
/* 2369 */       paramParsePosition
/* 2370 */         .index = i + (bool1 ? this.positiveSuffix.length() : this.negativeSuffix.length());
/*      */     } else {
/* 2372 */       paramParsePosition.index = i;
/*      */     } 
/*      */     
/* 2375 */     paramArrayOfboolean[1] = bool1;
/* 2376 */     if (paramParsePosition.index == j) {
/* 2377 */       paramParsePosition.errorIndex = i;
/* 2378 */       return false;
/*      */     } 
/* 2380 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DecimalFormatSymbols getDecimalFormatSymbols() {
/*      */     try {
/* 2392 */       return (DecimalFormatSymbols)this.symbols.clone();
/* 2393 */     } catch (Exception exception) {
/* 2394 */       return null;
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
/*      */   public void setDecimalFormatSymbols(DecimalFormatSymbols paramDecimalFormatSymbols) {
/*      */     try {
/* 2408 */       this.symbols = (DecimalFormatSymbols)paramDecimalFormatSymbols.clone();
/* 2409 */       expandAffixes();
/* 2410 */       this.fastPathCheckNeeded = true;
/* 2411 */     } catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPositivePrefix() {
/* 2423 */     return this.positivePrefix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositivePrefix(String paramString) {
/* 2433 */     this.positivePrefix = paramString;
/* 2434 */     this.posPrefixPattern = null;
/* 2435 */     this.positivePrefixFieldPositions = null;
/* 2436 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FieldPosition[] getPositivePrefixFieldPositions() {
/* 2448 */     if (this.positivePrefixFieldPositions == null) {
/* 2449 */       if (this.posPrefixPattern != null) {
/* 2450 */         this.positivePrefixFieldPositions = expandAffix(this.posPrefixPattern);
/*      */       } else {
/* 2452 */         this.positivePrefixFieldPositions = EmptyFieldPositionArray;
/*      */       } 
/*      */     }
/* 2455 */     return this.positivePrefixFieldPositions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNegativePrefix() {
/* 2465 */     return this.negativePrefix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNegativePrefix(String paramString) {
/* 2475 */     this.negativePrefix = paramString;
/* 2476 */     this.negPrefixPattern = null;
/* 2477 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FieldPosition[] getNegativePrefixFieldPositions() {
/* 2489 */     if (this.negativePrefixFieldPositions == null) {
/* 2490 */       if (this.negPrefixPattern != null) {
/* 2491 */         this.negativePrefixFieldPositions = expandAffix(this.negPrefixPattern);
/*      */       } else {
/* 2493 */         this.negativePrefixFieldPositions = EmptyFieldPositionArray;
/*      */       } 
/*      */     }
/* 2496 */     return this.negativePrefixFieldPositions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPositiveSuffix() {
/* 2506 */     return this.positiveSuffix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositiveSuffix(String paramString) {
/* 2516 */     this.positiveSuffix = paramString;
/* 2517 */     this.posSuffixPattern = null;
/* 2518 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FieldPosition[] getPositiveSuffixFieldPositions() {
/* 2530 */     if (this.positiveSuffixFieldPositions == null) {
/* 2531 */       if (this.posSuffixPattern != null) {
/* 2532 */         this.positiveSuffixFieldPositions = expandAffix(this.posSuffixPattern);
/*      */       } else {
/* 2534 */         this.positiveSuffixFieldPositions = EmptyFieldPositionArray;
/*      */       } 
/*      */     }
/* 2537 */     return this.positiveSuffixFieldPositions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNegativeSuffix() {
/* 2547 */     return this.negativeSuffix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNegativeSuffix(String paramString) {
/* 2557 */     this.negativeSuffix = paramString;
/* 2558 */     this.negSuffixPattern = null;
/* 2559 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FieldPosition[] getNegativeSuffixFieldPositions() {
/* 2571 */     if (this.negativeSuffixFieldPositions == null) {
/* 2572 */       if (this.negSuffixPattern != null) {
/* 2573 */         this.negativeSuffixFieldPositions = expandAffix(this.negSuffixPattern);
/*      */       } else {
/* 2575 */         this.negativeSuffixFieldPositions = EmptyFieldPositionArray;
/*      */       } 
/*      */     }
/* 2578 */     return this.negativeSuffixFieldPositions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMultiplier() {
/* 2589 */     return this.multiplier;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMultiplier(int paramInt) {
/* 2607 */     this.multiplier = paramInt;
/* 2608 */     this.bigDecimalMultiplier = null;
/* 2609 */     this.bigIntegerMultiplier = null;
/* 2610 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGroupingUsed(boolean paramBoolean) {
/* 2618 */     super.setGroupingUsed(paramBoolean);
/* 2619 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getGroupingSize() {
/* 2633 */     return this.groupingSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGroupingSize(int paramInt) {
/* 2649 */     this.groupingSize = (byte)paramInt;
/* 2650 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDecimalSeparatorAlwaysShown() {
/* 2662 */     return this.decimalSeparatorAlwaysShown;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDecimalSeparatorAlwaysShown(boolean paramBoolean) {
/* 2674 */     this.decimalSeparatorAlwaysShown = paramBoolean;
/* 2675 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isParseBigDecimal() {
/* 2688 */     return this.parseBigDecimal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setParseBigDecimal(boolean paramBoolean) {
/* 2701 */     this.parseBigDecimal = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/* 2709 */     DecimalFormat decimalFormat = (DecimalFormat)super.clone();
/* 2710 */     decimalFormat.symbols = (DecimalFormatSymbols)this.symbols.clone();
/* 2711 */     decimalFormat.digitList = (DigitList)this.digitList.clone();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2722 */     decimalFormat.fastPathCheckNeeded = true;
/* 2723 */     decimalFormat.isFastPath = false;
/* 2724 */     decimalFormat.fastPathData = null;
/*      */     
/* 2726 */     return decimalFormat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/* 2735 */     if (paramObject == null)
/* 2736 */       return false; 
/* 2737 */     if (!super.equals(paramObject))
/* 2738 */       return false; 
/* 2739 */     DecimalFormat decimalFormat = (DecimalFormat)paramObject;
/* 2740 */     return (((this.posPrefixPattern == decimalFormat.posPrefixPattern && this.positivePrefix
/* 2741 */       .equals(decimalFormat.positivePrefix)) || (this.posPrefixPattern != null && this.posPrefixPattern
/*      */       
/* 2743 */       .equals(decimalFormat.posPrefixPattern))) && ((this.posSuffixPattern == decimalFormat.posSuffixPattern && this.positiveSuffix
/*      */       
/* 2745 */       .equals(decimalFormat.positiveSuffix)) || (this.posSuffixPattern != null && this.posSuffixPattern
/*      */       
/* 2747 */       .equals(decimalFormat.posSuffixPattern))) && ((this.negPrefixPattern == decimalFormat.negPrefixPattern && this.negativePrefix
/*      */       
/* 2749 */       .equals(decimalFormat.negativePrefix)) || (this.negPrefixPattern != null && this.negPrefixPattern
/*      */       
/* 2751 */       .equals(decimalFormat.negPrefixPattern))) && ((this.negSuffixPattern == decimalFormat.negSuffixPattern && this.negativeSuffix
/*      */       
/* 2753 */       .equals(decimalFormat.negativeSuffix)) || (this.negSuffixPattern != null && this.negSuffixPattern
/*      */       
/* 2755 */       .equals(decimalFormat.negSuffixPattern))) && this.multiplier == decimalFormat.multiplier && this.groupingSize == decimalFormat.groupingSize && this.decimalSeparatorAlwaysShown == decimalFormat.decimalSeparatorAlwaysShown && this.parseBigDecimal == decimalFormat.parseBigDecimal && this.useExponentialNotation == decimalFormat.useExponentialNotation && (!this.useExponentialNotation || this.minExponentDigits == decimalFormat.minExponentDigits) && this.maximumIntegerDigits == decimalFormat.maximumIntegerDigits && this.minimumIntegerDigits == decimalFormat.minimumIntegerDigits && this.maximumFractionDigits == decimalFormat.maximumFractionDigits && this.minimumFractionDigits == decimalFormat.minimumFractionDigits && this.roundingMode == decimalFormat.roundingMode && this.symbols
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2768 */       .equals(decimalFormat.symbols));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 2776 */     return super.hashCode() * 37 + this.positivePrefix.hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toPattern() {
/* 2788 */     return toPattern(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toLocalizedPattern() {
/* 2799 */     return toPattern(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void expandAffixes() {
/* 2810 */     StringBuffer stringBuffer = new StringBuffer();
/* 2811 */     if (this.posPrefixPattern != null) {
/* 2812 */       this.positivePrefix = expandAffix(this.posPrefixPattern, stringBuffer);
/* 2813 */       this.positivePrefixFieldPositions = null;
/*      */     } 
/* 2815 */     if (this.posSuffixPattern != null) {
/* 2816 */       this.positiveSuffix = expandAffix(this.posSuffixPattern, stringBuffer);
/* 2817 */       this.positiveSuffixFieldPositions = null;
/*      */     } 
/* 2819 */     if (this.negPrefixPattern != null) {
/* 2820 */       this.negativePrefix = expandAffix(this.negPrefixPattern, stringBuffer);
/* 2821 */       this.negativePrefixFieldPositions = null;
/*      */     } 
/* 2823 */     if (this.negSuffixPattern != null) {
/* 2824 */       this.negativeSuffix = expandAffix(this.negSuffixPattern, stringBuffer);
/* 2825 */       this.negativeSuffixFieldPositions = null;
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
/*      */   private String expandAffix(String paramString, StringBuffer paramStringBuffer) {
/* 2844 */     paramStringBuffer.setLength(0);
/* 2845 */     for (byte b = 0; b < paramString.length(); ) {
/* 2846 */       char c = paramString.charAt(b++);
/* 2847 */       if (c == '\'') {
/* 2848 */         c = paramString.charAt(b++);
/* 2849 */         switch (c) {
/*      */           case '¤':
/* 2851 */             if (b < paramString.length() && paramString
/* 2852 */               .charAt(b) == '¤') {
/* 2853 */               b++;
/* 2854 */               paramStringBuffer.append(this.symbols.getInternationalCurrencySymbol()); continue;
/*      */             } 
/* 2856 */             paramStringBuffer.append(this.symbols.getCurrencySymbol());
/*      */             continue;
/*      */           
/*      */           case '%':
/* 2860 */             c = this.symbols.getPercent();
/*      */             break;
/*      */           case '‰':
/* 2863 */             c = this.symbols.getPerMill();
/*      */             break;
/*      */           case '-':
/* 2866 */             c = this.symbols.getMinusSign();
/*      */             break;
/*      */         } 
/*      */       } 
/* 2870 */       paramStringBuffer.append(c);
/*      */     } 
/* 2872 */     return paramStringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FieldPosition[] expandAffix(String paramString) {
/* 2891 */     ArrayList<FieldPosition> arrayList = null;
/* 2892 */     int i = 0;
/* 2893 */     for (byte b = 0; b < paramString.length(); ) {
/* 2894 */       char c = paramString.charAt(b++);
/* 2895 */       if (c == '\'') {
/* 2896 */         String str; byte b1 = -1;
/* 2897 */         NumberFormat.Field field = null;
/* 2898 */         c = paramString.charAt(b++);
/* 2899 */         switch (c) {
/*      */           
/*      */           case '¤':
/* 2902 */             if (b < paramString.length() && paramString
/* 2903 */               .charAt(b) == '¤') {
/* 2904 */               b++;
/* 2905 */               str = this.symbols.getInternationalCurrencySymbol();
/*      */             } else {
/* 2907 */               str = this.symbols.getCurrencySymbol();
/*      */             } 
/* 2909 */             if (str.length() > 0) {
/* 2910 */               if (arrayList == null) {
/* 2911 */                 arrayList = new ArrayList(2);
/*      */               }
/* 2913 */               FieldPosition fieldPosition = new FieldPosition(NumberFormat.Field.CURRENCY);
/* 2914 */               fieldPosition.setBeginIndex(i);
/* 2915 */               fieldPosition.setEndIndex(i + str.length());
/* 2916 */               arrayList.add(fieldPosition);
/* 2917 */               i += str.length();
/*      */             } 
/*      */             continue;
/*      */           case '%':
/* 2921 */             c = this.symbols.getPercent();
/* 2922 */             b1 = -1;
/* 2923 */             field = NumberFormat.Field.PERCENT;
/*      */             break;
/*      */           case '‰':
/* 2926 */             c = this.symbols.getPerMill();
/* 2927 */             b1 = -1;
/* 2928 */             field = NumberFormat.Field.PERMILLE;
/*      */             break;
/*      */           case '-':
/* 2931 */             c = this.symbols.getMinusSign();
/* 2932 */             b1 = -1;
/* 2933 */             field = NumberFormat.Field.SIGN;
/*      */             break;
/*      */         } 
/* 2936 */         if (field != null) {
/* 2937 */           if (arrayList == null) {
/* 2938 */             arrayList = new ArrayList<>(2);
/*      */           }
/* 2940 */           FieldPosition fieldPosition = new FieldPosition(field, b1);
/* 2941 */           fieldPosition.setBeginIndex(i);
/* 2942 */           fieldPosition.setEndIndex(i + 1);
/* 2943 */           arrayList.add(fieldPosition);
/*      */         } 
/*      */       } 
/* 2946 */       i++;
/*      */     } 
/* 2948 */     if (arrayList != null) {
/* 2949 */       return arrayList.<FieldPosition>toArray(EmptyFieldPositionArray);
/*      */     }
/* 2951 */     return EmptyFieldPositionArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void appendAffix(StringBuffer paramStringBuffer, String paramString1, String paramString2, boolean paramBoolean) {
/* 2971 */     if (paramString1 == null) {
/* 2972 */       appendAffix(paramStringBuffer, paramString2, paramBoolean);
/*      */     } else {
/*      */       int i;
/* 2975 */       for (i = 0; i < paramString1.length(); i = j) {
/* 2976 */         int j = paramString1.indexOf('\'', i);
/* 2977 */         if (j < 0) {
/* 2978 */           appendAffix(paramStringBuffer, paramString1.substring(i), paramBoolean);
/*      */           break;
/*      */         } 
/* 2981 */         if (j > i) {
/* 2982 */           appendAffix(paramStringBuffer, paramString1.substring(i, j), paramBoolean);
/*      */         }
/* 2984 */         char c = paramString1.charAt(++j);
/* 2985 */         j++;
/* 2986 */         if (c == '\'') {
/* 2987 */           paramStringBuffer.append(c);
/*      */         }
/* 2989 */         else if (c == '¤' && j < paramString1
/* 2990 */           .length() && paramString1
/* 2991 */           .charAt(j) == '¤') {
/* 2992 */           j++;
/* 2993 */           paramStringBuffer.append(c);
/*      */         }
/* 2995 */         else if (paramBoolean) {
/* 2996 */           switch (c) {
/*      */             case '%':
/* 2998 */               c = this.symbols.getPercent();
/*      */               break;
/*      */             case '‰':
/* 3001 */               c = this.symbols.getPerMill();
/*      */               break;
/*      */             case '-':
/* 3004 */               c = this.symbols.getMinusSign();
/*      */               break;
/*      */           } 
/*      */         } 
/* 3008 */         paramStringBuffer.append(c);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void appendAffix(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean) {
/*      */     boolean bool;
/* 3020 */     if (paramBoolean) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3029 */       bool = (paramString.indexOf(this.symbols.getZeroDigit()) >= 0 || paramString.indexOf(this.symbols.getGroupingSeparator()) >= 0 || paramString.indexOf(this.symbols.getDecimalSeparator()) >= 0 || paramString.indexOf(this.symbols.getPercent()) >= 0 || paramString.indexOf(this.symbols.getPerMill()) >= 0 || paramString.indexOf(this.symbols.getDigit()) >= 0 || paramString.indexOf(this.symbols.getPatternSeparator()) >= 0 || paramString.indexOf(this.symbols.getMinusSign()) >= 0 || paramString.indexOf('¤') >= 0) ? true : false;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3039 */       bool = (paramString.indexOf('0') >= 0 || paramString.indexOf(',') >= 0 || paramString.indexOf('.') >= 0 || paramString.indexOf('%') >= 0 || paramString.indexOf('‰') >= 0 || paramString.indexOf('#') >= 0 || paramString.indexOf(';') >= 0 || paramString.indexOf('-') >= 0 || paramString.indexOf('¤') >= 0) ? true : false;
/*      */     } 
/* 3041 */     if (bool) paramStringBuffer.append('\''); 
/* 3042 */     if (paramString.indexOf('\'') < 0) { paramStringBuffer.append(paramString); }
/*      */     else
/* 3044 */     { for (byte b = 0; b < paramString.length(); b++) {
/* 3045 */         char c = paramString.charAt(b);
/* 3046 */         paramStringBuffer.append(c);
/* 3047 */         if (c == '\'') paramStringBuffer.append(c); 
/*      */       }  }
/*      */     
/* 3050 */     if (bool) paramStringBuffer.append('\'');
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   private String toPattern(boolean paramBoolean) {
/* 3056 */     StringBuffer stringBuffer = new StringBuffer();
/* 3057 */     for (byte b = 1; b; b--) {
/* 3058 */       if (b == 1)
/* 3059 */       { appendAffix(stringBuffer, this.posPrefixPattern, this.positivePrefix, paramBoolean); }
/* 3060 */       else { appendAffix(stringBuffer, this.negPrefixPattern, this.negativePrefix, paramBoolean); }
/*      */ 
/*      */ 
/*      */       
/* 3064 */       int j = this.useExponentialNotation ? getMaximumIntegerDigits() : (Math.max(this.groupingSize, getMinimumIntegerDigits()) + 1); int i;
/* 3065 */       for (i = j; i > 0; i--) {
/* 3066 */         if (i != j && isGroupingUsed() && this.groupingSize != 0 && i % this.groupingSize == 0)
/*      */         {
/* 3068 */           stringBuffer.append(paramBoolean ? this.symbols.getGroupingSeparator() : 44);
/*      */         }
/*      */         
/* 3071 */         stringBuffer.append((i <= getMinimumIntegerDigits()) ? (paramBoolean ? this.symbols
/* 3072 */             .getZeroDigit() : 48) : (paramBoolean ? this.symbols
/* 3073 */             .getDigit() : 35));
/*      */       } 
/* 3075 */       if (getMaximumFractionDigits() > 0 || this.decimalSeparatorAlwaysShown) {
/* 3076 */         stringBuffer.append(paramBoolean ? this.symbols.getDecimalSeparator() : 46);
/*      */       }
/* 3078 */       for (i = 0; i < getMaximumFractionDigits(); i++) {
/* 3079 */         if (i < getMinimumFractionDigits()) {
/* 3080 */           stringBuffer.append(paramBoolean ? this.symbols.getZeroDigit() : 48);
/*      */         } else {
/*      */           
/* 3083 */           stringBuffer.append(paramBoolean ? this.symbols.getDigit() : 35);
/*      */         } 
/*      */       } 
/*      */       
/* 3087 */       if (this.useExponentialNotation) {
/*      */         
/* 3089 */         stringBuffer.append(paramBoolean ? this.symbols.getExponentSeparator() : "E");
/*      */         
/* 3091 */         for (i = 0; i < this.minExponentDigits; i++) {
/* 3092 */           stringBuffer.append(paramBoolean ? this.symbols.getZeroDigit() : 48);
/*      */         }
/*      */       } 
/* 3095 */       if (b == 1) {
/* 3096 */         appendAffix(stringBuffer, this.posSuffixPattern, this.positiveSuffix, paramBoolean);
/* 3097 */         if (((this.negSuffixPattern == this.posSuffixPattern && this.negativeSuffix
/* 3098 */           .equals(this.positiveSuffix)) || (this.negSuffixPattern != null && this.negSuffixPattern
/*      */           
/* 3100 */           .equals(this.posSuffixPattern))) && ((
/* 3101 */           this.negPrefixPattern != null && this.posPrefixPattern != null && this.negPrefixPattern
/* 3102 */           .equals("'-" + this.posPrefixPattern)) || (this.negPrefixPattern == this.posPrefixPattern && this.negativePrefix
/*      */           
/* 3104 */           .equals(this.symbols.getMinusSign() + this.positivePrefix)))) {
/*      */           break;
/*      */         }
/* 3107 */         stringBuffer.append(paramBoolean ? this.symbols.getPatternSeparator() : 59);
/*      */       } else {
/* 3109 */         appendAffix(stringBuffer, this.negSuffixPattern, this.negativeSuffix, paramBoolean);
/*      */       } 
/* 3111 */     }  return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 3137 */     applyPattern(paramString, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 3164 */     applyPattern(paramString, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void applyPattern(String paramString, boolean paramBoolean) {
/* 3171 */     char c1 = '0';
/* 3172 */     char c2 = ',';
/* 3173 */     char c3 = '.';
/* 3174 */     char c4 = '%';
/* 3175 */     char c5 = '‰';
/* 3176 */     char c6 = '#';
/* 3177 */     char c7 = ';';
/* 3178 */     String str = "E";
/* 3179 */     char c8 = '-';
/* 3180 */     if (paramBoolean) {
/* 3181 */       c1 = this.symbols.getZeroDigit();
/* 3182 */       c2 = this.symbols.getGroupingSeparator();
/* 3183 */       c3 = this.symbols.getDecimalSeparator();
/* 3184 */       c4 = this.symbols.getPercent();
/* 3185 */       c5 = this.symbols.getPerMill();
/* 3186 */       c6 = this.symbols.getDigit();
/* 3187 */       c7 = this.symbols.getPatternSeparator();
/* 3188 */       str = this.symbols.getExponentSeparator();
/* 3189 */       c8 = this.symbols.getMinusSign();
/*      */     } 
/* 3191 */     boolean bool = false;
/* 3192 */     this.decimalSeparatorAlwaysShown = false;
/* 3193 */     this.isCurrencyFormat = false;
/* 3194 */     this.useExponentialNotation = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3200 */     int i = 0;
/* 3201 */     byte b1 = 0;
/*      */     
/* 3203 */     int j = 0;
/* 3204 */     for (byte b2 = 1; b2 && j < paramString.length(); b2--) {
/* 3205 */       boolean bool1 = false;
/* 3206 */       StringBuffer stringBuffer1 = new StringBuffer();
/* 3207 */       StringBuffer stringBuffer2 = new StringBuffer();
/* 3208 */       int k = -1;
/* 3209 */       char c = '\001';
/* 3210 */       int m = 0; byte b3 = 0; int n = 0;
/* 3211 */       byte b = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3220 */       byte b4 = 0;
/*      */ 
/*      */       
/* 3223 */       StringBuffer stringBuffer3 = stringBuffer1;
/*      */       int i1;
/* 3225 */       for (i1 = j; i1 < paramString.length(); i1++) {
/* 3226 */         char c9 = paramString.charAt(i1);
/* 3227 */         switch (b4) {
/*      */           
/*      */           case false:
/*      */           case true:
/* 3231 */             if (bool1) {
/*      */ 
/*      */ 
/*      */               
/* 3235 */               if (c9 == '\'') {
/* 3236 */                 if (i1 + 1 < paramString.length() && paramString
/* 3237 */                   .charAt(i1 + 1) == '\'') {
/* 3238 */                   i1++;
/* 3239 */                   stringBuffer3.append("''"); break;
/*      */                 } 
/* 3241 */                 bool1 = false;
/*      */ 
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } else {
/* 3248 */               if (c9 == c6 || c9 == c1 || c9 == c2 || c9 == c3) {
/*      */ 
/*      */ 
/*      */                 
/* 3252 */                 b4 = 1;
/* 3253 */                 if (b2 == 1) {
/* 3254 */                   i = i1;
/*      */                 }
/* 3256 */                 i1--; break;
/*      */               } 
/* 3258 */               if (c9 == '¤') {
/*      */ 
/*      */ 
/*      */                 
/* 3262 */                 boolean bool2 = (i1 + 1 < paramString.length() && paramString.charAt(i1 + 1) == '¤') ? true : false;
/* 3263 */                 if (bool2) {
/* 3264 */                   i1++;
/*      */                 }
/* 3266 */                 this.isCurrencyFormat = true;
/* 3267 */                 stringBuffer3.append(bool2 ? "'¤¤" : "'¤"); break;
/*      */               } 
/* 3269 */               if (c9 == '\'') {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 3274 */                 if (c9 == '\'') {
/* 3275 */                   if (i1 + 1 < paramString.length() && paramString
/* 3276 */                     .charAt(i1 + 1) == '\'') {
/* 3277 */                     i1++;
/* 3278 */                     stringBuffer3.append("''"); break;
/*      */                   } 
/* 3280 */                   bool1 = true;
/*      */                   break;
/*      */                 } 
/*      */               } else {
/* 3284 */                 if (c9 == c7) {
/*      */ 
/*      */ 
/*      */                   
/* 3288 */                   if (!b4 || !b2) {
/* 3289 */                     throw new IllegalArgumentException("Unquoted special character '" + c9 + "' in pattern \"" + paramString + '"');
/*      */                   }
/*      */                   
/* 3292 */                   j = i1 + 1;
/* 3293 */                   i1 = paramString.length();
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */                 
/* 3298 */                 if (c9 == c4) {
/* 3299 */                   if (c != '\001') {
/* 3300 */                     throw new IllegalArgumentException("Too many percent/per mille characters in pattern \"" + paramString + '"');
/*      */                   }
/*      */                   
/* 3303 */                   c = 'd';
/* 3304 */                   stringBuffer3.append("'%"); break;
/*      */                 } 
/* 3306 */                 if (c9 == c5) {
/* 3307 */                   if (c != '\001') {
/* 3308 */                     throw new IllegalArgumentException("Too many percent/per mille characters in pattern \"" + paramString + '"');
/*      */                   }
/*      */                   
/* 3311 */                   c = 'Ϩ';
/* 3312 */                   stringBuffer3.append("'‰"); break;
/*      */                 } 
/* 3314 */                 if (c9 == c8) {
/* 3315 */                   stringBuffer3.append("'-");
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */             
/* 3322 */             stringBuffer3.append(c9);
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case true:
/* 3331 */             if (b2 == 1) {
/* 3332 */               b1++;
/*      */             } else {
/* 3334 */               if (--b1 == 0) {
/* 3335 */                 b4 = 2;
/* 3336 */                 stringBuffer3 = stringBuffer2;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3350 */             if (c9 == c6) {
/* 3351 */               if (b3) {
/* 3352 */                 n++;
/*      */               } else {
/* 3354 */                 m++;
/*      */               } 
/* 3356 */               if (b >= 0 && k < 0)
/* 3357 */                 b = (byte)(b + 1);  break;
/*      */             } 
/* 3359 */             if (c9 == c1) {
/* 3360 */               if (n > 0) {
/* 3361 */                 throw new IllegalArgumentException("Unexpected '0' in pattern \"" + paramString + '"');
/*      */               }
/*      */               
/* 3364 */               b3++;
/* 3365 */               if (b >= 0 && k < 0)
/* 3366 */                 b = (byte)(b + 1);  break;
/*      */             } 
/* 3368 */             if (c9 == c2) {
/* 3369 */               b = 0; break;
/* 3370 */             }  if (c9 == c3) {
/* 3371 */               if (k >= 0) {
/* 3372 */                 throw new IllegalArgumentException("Multiple decimal separators in pattern \"" + paramString + '"');
/*      */               }
/*      */               
/* 3375 */               k = m + b3 + n; break;
/* 3376 */             }  if (paramString.regionMatches(i1, str, 0, str.length())) {
/* 3377 */               if (this.useExponentialNotation) {
/* 3378 */                 throw new IllegalArgumentException("Multiple exponential symbols in pattern \"" + paramString + '"');
/*      */               }
/*      */               
/* 3381 */               this.useExponentialNotation = true;
/* 3382 */               this.minExponentDigits = 0;
/*      */ 
/*      */ 
/*      */               
/* 3386 */               i1 += str.length();
/* 3387 */               while (i1 < paramString.length() && paramString
/* 3388 */                 .charAt(i1) == c1) {
/* 3389 */                 this.minExponentDigits = (byte)(this.minExponentDigits + 1);
/* 3390 */                 b1++;
/* 3391 */                 i1++;
/*      */               } 
/*      */               
/* 3394 */               if (m + b3 < 1 || this.minExponentDigits < 1)
/*      */               {
/* 3396 */                 throw new IllegalArgumentException("Malformed exponential pattern \"" + paramString + '"');
/*      */               }
/*      */ 
/*      */ 
/*      */               
/* 3401 */               b4 = 2;
/* 3402 */               stringBuffer3 = stringBuffer2;
/* 3403 */               i1--;
/*      */               break;
/*      */             } 
/* 3406 */             b4 = 2;
/* 3407 */             stringBuffer3 = stringBuffer2;
/* 3408 */             i1--;
/* 3409 */             b1--;
/*      */             break;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       } 
/* 3428 */       if (b3 == 0 && m > 0 && k >= 0) {
/*      */         
/* 3430 */         i1 = k;
/* 3431 */         if (i1 == 0) {
/* 3432 */           i1++;
/*      */         }
/* 3434 */         n = m - i1;
/* 3435 */         m = i1 - 1;
/* 3436 */         b3 = 1;
/*      */       } 
/*      */ 
/*      */       
/* 3440 */       if ((k < 0 && n > 0) || (k >= 0 && (k < m || k > m + b3)) || b == 0 || bool1)
/*      */       {
/*      */ 
/*      */         
/* 3444 */         throw new IllegalArgumentException("Malformed pattern \"" + paramString + '"');
/*      */       }
/*      */ 
/*      */       
/* 3448 */       if (b2 == 1) {
/* 3449 */         this.posPrefixPattern = stringBuffer1.toString();
/* 3450 */         this.posSuffixPattern = stringBuffer2.toString();
/* 3451 */         this.negPrefixPattern = this.posPrefixPattern;
/* 3452 */         this.negSuffixPattern = this.posSuffixPattern;
/* 3453 */         i1 = m + b3 + n;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3458 */         int i2 = (k >= 0) ? k : i1;
/*      */         
/* 3460 */         setMinimumIntegerDigits(i2 - m);
/* 3461 */         setMaximumIntegerDigits(this.useExponentialNotation ? (m + 
/* 3462 */             getMinimumIntegerDigits()) : Integer.MAX_VALUE);
/*      */         
/* 3464 */         setMaximumFractionDigits((k >= 0) ? (i1 - k) : 0);
/*      */         
/* 3466 */         setMinimumFractionDigits((k >= 0) ? (m + b3 - k) : 0);
/*      */         
/* 3468 */         setGroupingUsed((b > 0));
/* 3469 */         this.groupingSize = (b > 0) ? b : 0;
/* 3470 */         this.multiplier = c;
/* 3471 */         setDecimalSeparatorAlwaysShown((k == 0 || k == i1));
/*      */       } else {
/*      */         
/* 3474 */         this.negPrefixPattern = stringBuffer1.toString();
/* 3475 */         this.negSuffixPattern = stringBuffer2.toString();
/* 3476 */         bool = true;
/*      */       } 
/*      */     } 
/*      */     
/* 3480 */     if (paramString.length() == 0) {
/* 3481 */       this.posPrefixPattern = this.posSuffixPattern = "";
/* 3482 */       setMinimumIntegerDigits(0);
/* 3483 */       setMaximumIntegerDigits(2147483647);
/* 3484 */       setMinimumFractionDigits(0);
/* 3485 */       setMaximumFractionDigits(2147483647);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3491 */     if (!bool || (this.negPrefixPattern
/* 3492 */       .equals(this.posPrefixPattern) && this.negSuffixPattern
/* 3493 */       .equals(this.posSuffixPattern))) {
/* 3494 */       this.negSuffixPattern = this.posSuffixPattern;
/* 3495 */       this.negPrefixPattern = "'-" + this.posPrefixPattern;
/*      */     } 
/*      */     
/* 3498 */     expandAffixes();
/*      */   }
/*      */ 
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
/* 3511 */     this.maximumIntegerDigits = Math.min(Math.max(0, paramInt), 2147483647);
/* 3512 */     super.setMaximumIntegerDigits((this.maximumIntegerDigits > 309) ? 309 : this.maximumIntegerDigits);
/*      */     
/* 3514 */     if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
/* 3515 */       this.minimumIntegerDigits = this.maximumIntegerDigits;
/* 3516 */       super.setMinimumIntegerDigits((this.minimumIntegerDigits > 309) ? 309 : this.minimumIntegerDigits);
/*      */     } 
/*      */     
/* 3519 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */ 
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
/* 3532 */     this.minimumIntegerDigits = Math.min(Math.max(0, paramInt), 2147483647);
/* 3533 */     super.setMinimumIntegerDigits((this.minimumIntegerDigits > 309) ? 309 : this.minimumIntegerDigits);
/*      */     
/* 3535 */     if (this.minimumIntegerDigits > this.maximumIntegerDigits) {
/* 3536 */       this.maximumIntegerDigits = this.minimumIntegerDigits;
/* 3537 */       super.setMaximumIntegerDigits((this.maximumIntegerDigits > 309) ? 309 : this.maximumIntegerDigits);
/*      */     } 
/*      */     
/* 3540 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */ 
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
/* 3553 */     this.maximumFractionDigits = Math.min(Math.max(0, paramInt), 2147483647);
/* 3554 */     super.setMaximumFractionDigits((this.maximumFractionDigits > 340) ? 340 : this.maximumFractionDigits);
/*      */     
/* 3556 */     if (this.minimumFractionDigits > this.maximumFractionDigits) {
/* 3557 */       this.minimumFractionDigits = this.maximumFractionDigits;
/* 3558 */       super.setMinimumFractionDigits((this.minimumFractionDigits > 340) ? 340 : this.minimumFractionDigits);
/*      */     } 
/*      */     
/* 3561 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */ 
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
/* 3574 */     this.minimumFractionDigits = Math.min(Math.max(0, paramInt), 2147483647);
/* 3575 */     super.setMinimumFractionDigits((this.minimumFractionDigits > 340) ? 340 : this.minimumFractionDigits);
/*      */     
/* 3577 */     if (this.minimumFractionDigits > this.maximumFractionDigits) {
/* 3578 */       this.maximumFractionDigits = this.minimumFractionDigits;
/* 3579 */       super.setMaximumFractionDigits((this.maximumFractionDigits > 340) ? 340 : this.maximumFractionDigits);
/*      */     } 
/*      */     
/* 3582 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumIntegerDigits() {
/* 3595 */     return this.maximumIntegerDigits;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinimumIntegerDigits() {
/* 3608 */     return this.minimumIntegerDigits;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumFractionDigits() {
/* 3621 */     return this.maximumFractionDigits;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinimumFractionDigits() {
/* 3634 */     return this.minimumFractionDigits;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 3649 */     return this.symbols.getCurrency();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 3666 */     if (paramCurrency != this.symbols.getCurrency()) {
/* 3667 */       this.symbols.setCurrency(paramCurrency);
/* 3668 */       if (this.isCurrencyFormat) {
/* 3669 */         expandAffixes();
/*      */       }
/*      */     } 
/* 3672 */     this.fastPathCheckNeeded = true;
/*      */   }
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
/* 3684 */     return this.roundingMode;
/*      */   }
/*      */ 
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
/* 3697 */     if (paramRoundingMode == null) {
/* 3698 */       throw new NullPointerException();
/*      */     }
/*      */     
/* 3701 */     this.roundingMode = paramRoundingMode;
/* 3702 */     this.digitList.setRoundingMode(paramRoundingMode);
/* 3703 */     this.fastPathCheckNeeded = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 3752 */     paramObjectInputStream.defaultReadObject();
/* 3753 */     this.digitList = new DigitList();
/*      */ 
/*      */ 
/*      */     
/* 3757 */     this.fastPathCheckNeeded = true;
/* 3758 */     this.isFastPath = false;
/* 3759 */     this.fastPathData = null;
/*      */     
/* 3761 */     if (this.serialVersionOnStream < 4) {
/* 3762 */       setRoundingMode(RoundingMode.HALF_EVEN);
/*      */     } else {
/* 3764 */       setRoundingMode(getRoundingMode());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3770 */     if (super.getMaximumIntegerDigits() > 309 || super
/* 3771 */       .getMaximumFractionDigits() > 340) {
/* 3772 */       throw new InvalidObjectException("Digit count out of range");
/*      */     }
/* 3774 */     if (this.serialVersionOnStream < 3) {
/* 3775 */       setMaximumIntegerDigits(super.getMaximumIntegerDigits());
/* 3776 */       setMinimumIntegerDigits(super.getMinimumIntegerDigits());
/* 3777 */       setMaximumFractionDigits(super.getMaximumFractionDigits());
/* 3778 */       setMinimumFractionDigits(super.getMinimumFractionDigits());
/*      */     } 
/* 3780 */     if (this.serialVersionOnStream < 1)
/*      */     {
/* 3782 */       this.useExponentialNotation = false;
/*      */     }
/* 3784 */     this.serialVersionOnStream = 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3791 */   private transient DigitList digitList = new DigitList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3799 */   private String positivePrefix = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3808 */   private String positiveSuffix = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3816 */   private String negativePrefix = "-";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3825 */   private String negativeSuffix = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String posPrefixPattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String posSuffixPattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String negPrefixPattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String negSuffixPattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3883 */   private int multiplier = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3894 */   private byte groupingSize = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean decimalSeparatorAlwaysShown = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean parseBigDecimal = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean isCurrencyFormat = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3930 */   private DecimalFormatSymbols symbols = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useExponentialNotation;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient FieldPosition[] positivePrefixFieldPositions;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient FieldPosition[] positiveSuffixFieldPositions;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient FieldPosition[] negativePrefixFieldPositions;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient FieldPosition[] negativeSuffixFieldPositions;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte minExponentDigits;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3989 */   private int maximumIntegerDigits = super.getMaximumIntegerDigits();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4001 */   private int minimumIntegerDigits = super.getMinimumIntegerDigits();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4013 */   private int maximumFractionDigits = super.getMaximumFractionDigits();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4025 */   private int minimumFractionDigits = super.getMinimumFractionDigits();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4033 */   private RoundingMode roundingMode = RoundingMode.HALF_EVEN;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class FastPathData
/*      */   {
/*      */     int lastFreeIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int firstUsedIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int zeroDelta;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     char groupingChar;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int integralLastIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int fractionalFirstIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     double fractionalScaleFactor;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int fractionalMaxIntBound;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     char[] fastPathContainer;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     char[] charsPositivePrefix;
/*      */ 
/*      */ 
/*      */     
/*      */     char[] charsNegativePrefix;
/*      */ 
/*      */ 
/*      */     
/*      */     char[] charsPositiveSuffix;
/*      */ 
/*      */ 
/*      */     
/*      */     char[] charsNegativeSuffix;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private FastPathData() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean positiveAffixesRequired = true;
/*      */ 
/*      */ 
/*      */     
/*      */     boolean negativeAffixesRequired = true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean isFastPath = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean fastPathCheckNeeded = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient FastPathData fastPathData;
/*      */ 
/*      */ 
/*      */   
/*      */   static final int currentSerialVersion = 4;
/*      */ 
/*      */ 
/*      */   
/* 4133 */   private int serialVersionOnStream = 4;
/*      */   private static final double MAX_INT_AS_DOUBLE = 2.147483647E9D;
/*      */   private static final char PATTERN_ZERO_DIGIT = '0';
/*      */   private static final char PATTERN_GROUPING_SEPARATOR = ',';
/*      */   private static final char PATTERN_DECIMAL_SEPARATOR = '.';
/*      */   private static final char PATTERN_PER_MILLE = '‰';
/*      */   private static final char PATTERN_PERCENT = '%';
/*      */   private static final char PATTERN_DIGIT = '#';
/*      */   private static final char PATTERN_SEPARATOR = ';';
/*      */   private static final String PATTERN_EXPONENT = "E";
/*      */   private static final char PATTERN_MINUS = '-';
/*      */   private static final char CURRENCY_SIGN = '¤';
/*      */   private static final char QUOTE = '\'';
/*      */   
/*      */   private static class DigitArrays
/*      */   {
/* 4149 */     static final char[] DigitOnes1000 = new char[1000];
/* 4150 */     static final char[] DigitTens1000 = new char[1000];
/* 4151 */     static final char[] DigitHundreds1000 = new char[1000];
/*      */ 
/*      */     
/*      */     static {
/* 4155 */       byte b1 = 0;
/* 4156 */       byte b2 = 0;
/* 4157 */       char c1 = '0';
/* 4158 */       char c2 = '0';
/* 4159 */       char c3 = '0';
/* 4160 */       for (byte b3 = 0; b3 < 'Ϩ'; b3++) {
/*      */         
/* 4162 */         DigitOnes1000[b3] = c1;
/* 4163 */         if (c1 == '9') {
/* 4164 */           c1 = '0';
/*      */         } else {
/* 4166 */           c1 = (char)(c1 + 1);
/*      */         } 
/* 4168 */         DigitTens1000[b3] = c2;
/* 4169 */         if (b3 == b1 + 9) {
/* 4170 */           b1 += 10;
/* 4171 */           if (c2 == '9') {
/* 4172 */             c2 = '0';
/*      */           } else {
/* 4174 */             c2 = (char)(c2 + 1);
/*      */           } 
/*      */         } 
/* 4177 */         DigitHundreds1000[b3] = c3;
/* 4178 */         if (b3 == b2 + 99) {
/* 4179 */           c3 = (char)(c3 + 1);
/* 4180 */           b2 += 100;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 4211 */   private static FieldPosition[] EmptyFieldPositionArray = new FieldPosition[0];
/*      */   static final int DOUBLE_INTEGER_DIGITS = 309;
/*      */   static final int DOUBLE_FRACTION_DIGITS = 340;
/*      */   static final int MAXIMUM_INTEGER_DIGITS = 2147483647;
/*      */   static final int MAXIMUM_FRACTION_DIGITS = 2147483647;
/*      */   static final long serialVersionUID = 864413376551465018L;
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/DecimalFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */